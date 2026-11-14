// port-lint: source sync/mpmc/array.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.array

import io.github.kotlinmania.atomic.fence
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sync.mpmc.SendTimeoutError
import io.github.kotlinmania.rustlang.std.sync.mpmc.context.Context
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Operation
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Selected
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Token
import io.github.kotlinmania.rustlang.std.sync.mpmc.utils.Backoff
import io.github.kotlinmania.rustlang.std.sync.mpmc.utils.CachePadded
import io.github.kotlinmania.rustlang.std.sync.mpmc.waker.SyncWaker
import io.github.kotlinmania.rustlang.std.sync.mpsc.RecvTimeoutError
import io.github.kotlinmania.rustlang.std.sync.mpsc.TryRecvError
import io.github.kotlinmania.rustlang.std.sync.mpsc.TrySendError
import io.github.kotlinmania.rustlang.std.time.Instant

// Bounded channel based on a preallocated array.
//
// This flavor has a fixed, positive capacity.
//
// The implementation is based on Dmitry Vyukov's bounded MPMC queue.
//
// Source:
//   - <http://www.1024cores.net/home/lock-free-algorithms/queues/bounded-mpmc-queue>
//   - <https://docs.google.com/document/d/1yIAYmbvL3JxOKOjuCyon7JhW4cSv1wy5hC0ApeGMV9s/pub>

/**
 * A slot in a channel.
 */
public open class Slot<T> internal constructor(
    stampCtorArg: Atomic<Int>,
    msgCtorArg: UnsafeCell<MaybeUninit<T>>,
) {
    /**
     * The current stamp.
     */
    public var stamp: Atomic<Int> = stampCtorArg
        internal set

    /**
     * The message in this slot. Either read out in `read` or dropped through
     * `discardAllMessages`.
     */
    public var msg: UnsafeCell<MaybeUninit<T>> = msgCtorArg
        internal set

    public companion object {
        public operator fun <T> invoke(
            stamp: Atomic<Int>,
            msg: UnsafeCell<MaybeUninit<T>>,
        ): Slot<T> = Slot<T>(
            stampCtorArg = stamp,
            msgCtorArg = msg,
        )
    }
}

/**
 * The token type for the array flavor.
 */
internal open class ArrayToken(
    /**
     * Slot to read from or write to.
     */
    public var slot: ConstPtr,
    /**
     * Stamp to store into the slot after reading or writing.
     */
    public var stamp: Int,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ArrayToken> =
            object : TypeLayout<ArrayToken> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ArrayToken =
                    ArrayToken(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: ArrayToken) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.slot)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.stamp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ArrayToken =
                    ArrayToken(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ArrayToken) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.slot)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.stamp)
                }
            }

        public fun default(): ArrayToken = ArrayToken(slot = ConstPtr(0L), stamp = 0)
    }
}

/**
 * Bounded channel based on a preallocated array.
 */
internal open class Channel<T>(
    /**
     * The head of the channel.
     *
     * This value is a "stamp" consisting of an index into the buffer, a mark bit, and a lap, but
     * packed into a single `Int`. The lower bits represent the index, while the upper bits
     * represent the lap. The mark bit in the head is always zero.
     *
     * Messages are popped from the head of the channel.
     */
    public var head: CachePadded<Atomic<Int>>,
    /**
     * The tail of the channel.
     *
     * This value is a "stamp" consisting of an index into the buffer, a mark bit, and a lap, but
     * packed into a single `Int`. The lower bits represent the index, while the upper bits
     * represent the lap. The mark bit indicates that the channel is disconnected.
     *
     * Messages are pushed into the tail of the channel.
     */
    public var tail: CachePadded<Atomic<Int>>,
    /**
     * The buffer holding slots.
     */
    public var buffer: List<Slot<T>>,
    /**
     * The channel capacity.
     */
    public var cap: Int,
    /**
     * A stamp with the value of `{ lap: 1, mark: 0, index: 0 }`.
     */
    public var oneLap: Int,
    /**
     * If this bit is set in the tail, that means the channel is disconnected.
     */
    public var markBit: Int,
    /**
     * Senders waiting while the channel is full.
     */
    public var senders: SyncWaker,
    /**
     * Receivers waiting while the channel is empty and not disconnected.
     */
    public var receivers: SyncWaker,
) {
    /**
     * Attempts to reserve a slot for sending a message.
     */
    public open fun startSend(token: Token): Boolean {
        val backoff = Backoff.new()
        var tail = this.tail.load(Ordering.Relaxed)
        while (true) {
            // Check if the channel is disconnected.
            if (tail and this.markBit != 0) {
                token.array.slot = ConstPtr(0L)
                token.array.stamp = 0
                return true
            }
            // Deconstruct the tail.
            val index = tail and (this.markBit - 1)
            val lap = tail and (this.oneLap - 1).inv()
            // Inspect the corresponding slot.
            check(index < this.buffer.size)
            val slot = this.buffer.getUnchecked(index)
            val stamp = slot.stamp.load(Ordering.Acquire)
            // If the tail and the stamp match, we may attempt to push.
            if (tail == stamp) {
                val newTail =
                    if (index + 1 < this.cap) {
                        // Same lap, incremented index.
                        // Set to `{ lap: lap, mark: 0, index: index + 1 }`.
                        tail + 1
                    } else {
                        // One lap forward, index wraps around to zero.
                        // Set to `{ lap: lap.wrappingAdd(1), mark: 0, index: 0 }`.
                        (lap + (this.oneLap.toInt()))
                    }
                // Try moving the tail.
                run {
                    val matchVal = this.tail.compareExchangeWeak(tail, newTail, Ordering.SeqCst, Ordering.Relaxed)
                    when (matchVal) { is Result.Ok<*> -> {
                        // Prepare the token for the follow-up call to `write`.
                        token.array.slot = (constPtrFromRustCast(slot)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
                        token.array.stamp = tail + 1
                        return true
                    }; is Result.Err<*> -> {
                        backoff.spinLight()
                        tail = this.tail.load(Ordering.Relaxed)
                    } }
                }
            } else if (stamp.wrappingAdd(this.oneLap) == tail + 1) {
                fence(Ordering.SeqCst)
                val head = this.head.load(Ordering.Relaxed)
                // If the head lags one lap behind the tail as well...
                if (head.wrappingAdd(this.oneLap) == tail) {
                    // ...then the channel is full.
                    return false
                }
                backoff.spinLight()
                tail = this.tail.load(Ordering.Relaxed)
            } else {
                // Snooze because we need to wait for the stamp to get updated.
                backoff.spinHeavy()
                tail = this.tail.load(Ordering.Relaxed)
            }
        }
    }

    /**
     * Writes a message into the channel.
     */
    internal open fun write(token: Token, msg: T, typeLayoutT: TypeLayout<T>): Result<Unit, T> {
        // If there is no slot, the channel is disconnected.
        if (((token.array.slot).rawAddress == 0L)) {
            return Result.Err(msg)
        }
        // Write the message into the slot and update the stamp.
        run {
            val slot: Slot<T> = (ObjectSlotLayout<Slot<T>>()).readNative((((token.array.slot).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
            slot.msg.get().write(MaybeUninit.new(msg, typeLayoutT))
            slot.stamp.store(token.array.stamp, Ordering.Release)
        }
        // Wake a sleeping receiver.
        this.receivers.notify()
        return Result.Ok(Unit)
    }

    /**
     * Attempts to reserve a slot for receiving a message.
     */
    public open fun startRecv(token: Token): Boolean {
        val backoff = Backoff.new()
        var head = this.head.load(Ordering.Relaxed)
        while (true) {
            // Deconstruct the head.
            val index = head and (this.markBit - 1)
            val lap = head and (this.oneLap - 1).inv()
            // Inspect the corresponding slot.
            check(index < this.buffer.size)
            val slot = this.buffer.getUnchecked(index)
            val stamp = slot.stamp.load(Ordering.Acquire)
            // If the stamp is ahead of the head by 1, we may attempt to pop.
            if (head + 1 == stamp) {
                val new =
                    if (index + 1 < this.cap) {
                        // Same lap, incremented index.
                        // Set to `{ lap: lap, mark: 0, index: index + 1 }`.
                        head + 1
                    } else {
                        // One lap forward, index wraps around to zero.
                        // Set to `{ lap: lap.wrappingAdd(1), mark: 0, index: 0 }`.
                        (lap + (this.oneLap.toInt()))
                    }
                // Try moving the head.
                run {
                    val matchVal = this.head.compareExchangeWeak(head, new, Ordering.SeqCst, Ordering.Relaxed)
                    when (matchVal) { is Result.Ok<*> -> {
                        // Prepare the token for the follow-up call to `read`.
                        token.array.slot = (constPtrFromRustCast(slot)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
                        token.array.stamp = head.wrappingAdd(this.oneLap)
                        return true
                    }; is Result.Err<*> -> {
                        backoff.spinLight()
                        head = this.head.load(Ordering.Relaxed)
                    } }
                }
            } else if (stamp == head) {
                fence(Ordering.SeqCst)
                val tail = this.tail.load(Ordering.Relaxed)
                // If the tail equals the head, that means the channel is empty.
                if ((tail and this.markBit.inv()) == head) {
                    // If the channel is disconnected...
                    if (tail and this.markBit != 0) {
                        // ...then receive an error.
                        token.array.slot = ConstPtr(0L)
                        token.array.stamp = 0
                        return true
                    } else {
                        // Otherwise, the receive operation is not ready.
                        return false
                    }
                }
                backoff.spinLight()
                head = this.head.load(Ordering.Relaxed)
            } else {
                // Snooze because we need to wait for the stamp to get updated.
                backoff.spinHeavy()
                head = this.head.load(Ordering.Relaxed)
            }
        }
    }

    /**
     * Reads a message from the channel.
     */
    internal open fun read(token: Token, typeLayoutT: TypeLayout<T>): Result<T, Unit> {
        if (((token.array.slot).rawAddress == 0L)) {
            // The channel is disconnected.
            return Result.Err(Unit)
        }
        // Read the message from the slot and update the stamp.
        val msg1 =
            run {
                val slot: Slot<T> = (ObjectSlotLayout<Slot<T>>()).readNative((((token.array.slot).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
                val msg = slot.msg.get().read().assumeInit()
                slot.stamp.store(token.array.stamp, Ordering.Release)
                msg
            }
        // Wake a sleeping sender.
        this.senders.notify()
        return Result.Ok(msg1)
    }

    /**
     * Attempts to send a message into the channel.
     */
    internal open fun trySend(msg: T): Result<Unit, TrySendError<T>> {
        val token = Token.default()
        return if (this.startSend(token)) {
            this.write(token, msg).mapErr({ TrySendError.Disconnected(it) })
        } else {
            Result.Err(TrySendError.Full(msg))
        }
    }

    /**
     * Sends a message into the channel.
     */
    internal open fun send(msg: T, deadline: Instant?): Result<Unit, SendTimeoutError<T>> {
        val token = Token.default()
        while (true) {
            // Try sending a message.
            if (this.startSend(token)) {
                val res = this.write(token, msg)
                return res.mapErr({ SendTimeoutError.Disconnected(it) })
            }
            if (deadline != null) {
                val d = deadline
                if (Instant.now() >= d) {
                    return Result.Err(SendTimeoutError.Timeout(msg))
                }
            }
            Context.with({ cx ->
                // Prepare for blocking until a receiver wakes us up.
                val oper = Operation.hook(token)
                this.senders.register(oper, cx)
                // Has the channel become ready just now?
                if (!this.isFull() || this.isDisconnected()) {
                    cx.trySelect(Selected.Aborted)
                }
                // Block the current thread.
                // SAFETY: the context belongs to the current thread.
                val sel = cx.waitUntil(deadline)
                when (sel) {
                    Selected.Waiting -> error("unreachable")
                    Selected.Aborted, Selected.Disconnected -> {
                        this.senders.unregister(oper).unwrap()
                    }
                    is Selected.Operation -> {}
                }
            })
        }
    }

    /**
     * Attempts to receive a message without blocking.
     */
    internal open fun tryRecv(): Result<T, TryRecvError> {
        val token = Token.default()
        return if (this.startRecv(token)) {
            this.read(token).mapErr({ TryRecvError.Disconnected })
        } else {
            Result.Err(TryRecvError.Empty)
        }
    }

    /**
     * Receives a message from the channel.
     */
    internal open fun recv(deadline: Instant?): Result<T, RecvTimeoutError> {
        val token = Token.default()
        while (true) {
            // Try receiving a message.
            if (this.startRecv(token)) {
                val res = this.read(token)
                return res.mapErr({ RecvTimeoutError.Disconnected })
            }
            if (deadline != null) {
                val d = deadline
                if (Instant.now() >= d) {
                    return Result.Err(RecvTimeoutError.Timeout)
                }
            }
            Context.with({ cx ->
                // Prepare for blocking until a sender wakes us up.
                val oper = Operation.hook(token)
                this.receivers.register(oper, cx)
                // Has the channel become ready just now?
                if (!this.isEmpty() || this.isDisconnected()) {
                    cx.trySelect(Selected.Aborted)
                }
                // Block the current thread.
                // SAFETY: the context belongs to the current thread.
                val sel = cx.waitUntil(deadline)
                when (sel) {
                    Selected.Waiting -> error("unreachable")
                    Selected.Aborted, Selected.Disconnected -> {
                        this.receivers.unregister(oper).unwrap()
                        // If the channel was disconnected, we still have to check for remaining
                        // messages.
                    }
                    is Selected.Operation -> {}
                }
            })
        }
    }

    /**
     * Returns the current number of messages inside the channel.
     */
    internal open fun len(): Int {
        while (true) {
            // Load the tail, then load the head.
            val tail = this.tail.load(Ordering.SeqCst)
            val head = this.head.load(Ordering.SeqCst)
            // If the tail didn't change, we've got consistent values to work with.
            if (this.tail.load(Ordering.SeqCst) == tail) {
                val hix = head and (this.markBit - 1)
                val tix = tail and (this.markBit - 1)
                return if (hix < tix) {
                    tix - hix
                } else if (hix > tix) {
                    this.cap - hix + tix
                } else if ((tail and this.markBit.inv()) == head) {
                    0
                } else {
                    this.cap
                }
            }
        }
    }

    // This is intentional.

    /**
     * Returns the capacity of the channel.
     */
    internal open fun capacity(): Int? = this.cap

    /**
     * Disconnects senders and wakes up all blocked receivers.
     *
     * Returns `true` if this call disconnected the channel.
     */
    internal open fun disconnectSenders(): Boolean {
        val tail = this.tail.fetchOr(this.markBit, Ordering.SeqCst)
        return if (tail and this.markBit == 0) {
            this.receivers.disconnect()
            true
        } else {
            false
        }
    }

    /**
     * Disconnects receivers and wakes up all blocked senders.
     *
     * Returns `true` if this call disconnected the channel.
     *
     * # Safety
     * May only be called once upon dropping the last receiver. The
     * destruction of all other receivers must have been observed with acquire
     * ordering or stronger.
     */
    internal open fun disconnectReceivers(): Boolean {
        val tail = this.tail.fetchOr(this.markBit, Ordering.SeqCst)
        val disconnected =
            if (tail and this.markBit == 0) {
                this.senders.disconnect()
                true
            } else {
                false
            }
        this.discardAllMessages(tail)
        return disconnected
    }

    /**
     * Discards all messages.
     *
     * `tail` should be the current (and therefore last) value of `tail`.
     *
     * # Panicking
     * If a destructor panics, the remaining messages are leaked, matching the
     * behavior of the unbounded channel.
     *
     * # Safety
     * This method must only be called when dropping the last receiver. The
     * destruction of all other receivers must have been observed with acquire
     * ordering or stronger.
     */
    public open fun discardAllMessages(tail: Int) {
        check(this.isDisconnected())
        // Only receivers modify `head`, so since we are the last one,
        // this value will not change and will not be observed (since
        // no new messages can be sent after disconnection).
        var head = this.head.load(Ordering.Relaxed)
        val tail1 = tail and this.markBit.inv()
        val backoff = Backoff.new()
        while (true) {
            // Deconstruct the head.
            val index = head and (this.markBit - 1)
            val lap = head and (this.oneLap - 1).inv()
            // Inspect the corresponding slot.
            check(index < this.buffer.size)
            val slot = this.buffer.getUnchecked(index)
            val stamp = slot.stamp.load(Ordering.Acquire)
            // If the stamp is ahead of the head by 1, we may drop the message.
            if (head + 1 == stamp) {
                head = if (index + 1 < this.cap) {
                    // Same lap, incremented index.
                    // Set to `{ lap: lap, mark: 0, index: index + 1 }`.
                    head + 1
                } else {
                    // One lap forward, index wraps around to zero.
                    // Set to `{ lap: lap.wrappingAdd(1), mark: 0, index: 0 }`.
                    (lap + (this.oneLap.toInt()))
                }
                run {
                    slot.msg.get().assumeInitDrop()
                }
                // If the tail equals the head, that means the channel is empty.
            } else if (tail1 == head) {
                return
                // Otherwise, a sender is about to write into the slot, so we need
                // to wait for it to update the stamp.
            } else {
                backoff.spinHeavy()
            }
        }
    }

    /**
     * Returns `true` if the channel is disconnected.
     */
    internal open fun isDisconnected(): Boolean = (this.tail.load(Ordering.SeqCst) and this.markBit != 0).toUByte()

    /**
     * Returns `true` if the channel is empty.
     */
    internal open fun isEmpty(): Boolean {
        val head = this.head.load(Ordering.SeqCst)
        val tail = this.tail.load(Ordering.SeqCst)
        // Is the tail equal to the head?
        //
        // Note: If the head changes just before we load the tail, that means there was a moment
        // when the channel was not empty, so it is safe to just return `false`.
        return ((tail and this.markBit.inv()) == head).toUByte()
    }

    /**
     * Returns `true` if the channel is full.
     */
    internal open fun isFull(): Boolean {
        val tail = this.tail.load(Ordering.SeqCst)
        val head = this.head.load(Ordering.SeqCst)
        // Is the head lagging one lap behind tail?
        //
        // Note: If the tail changes just before we load the head, that means there was a moment
        // when the channel was not full, so it is safe to just return `false`.
        return (head.wrappingAdd(this.oneLap) == tail and this.markBit.inv()).toUByte()
    }

    public companion object {
        /**
         * Creates a bounded channel of capacity `cap`.
         */
        internal fun <T> withCapacity(cap: Int): Channel<T> {
            check(cap > 0) { "capacity must be positive" }
            // Compute constants `markBit` and `oneLap`.
            val markBit = (cap + 1).nextPowerOfTwo()
            val oneLap = markBit * 2
            // Head is initialized to `{ lap: 0, mark: 0, index: 0 }`.
            val head = 0
            // Tail is initialized to `{ lap: 0, mark: 0, index: 0 }`.
            val tail = 0
            // Allocate a buffer of `cap` slots initialized
            // with stamps.
            val buffer: List<Slot<T>> =
                (0 until cap).map({ i ->
                    // Set the stamp to `{ lap: 0, mark: 0, index: i }`.
                    Slot(stamp = AtomicUsize.new(i), msg = UnsafeCell.new(MaybeUninit.uninit(PrimitiveLayouts.U8)))
                }).toMutableList()
            return Channel(buffer = buffer, cap = cap, oneLap = oneLap, markBit = markBit, head = CachePadded.new(AtomicUsize.new(head)), tail = CachePadded.new(AtomicUsize.new(tail)), senders = SyncWaker.new(), receivers = SyncWaker.new())
        }
    }
}
