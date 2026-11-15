// port-lint: source sync/mpmc/list.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.list

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sync.mpmc.context.Context
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Operation
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Selected
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Token
import io.github.kotlinmania.rustlang.std.sync.mpmc.utils.Backoff
import io.github.kotlinmania.rustlang.std.sync.mpmc.utils.CachePadded
import io.github.kotlinmania.rustlang.std.sync.mpmc.waker.SyncWaker
import io.github.kotlinmania.rustlang.sync.mpmc.SendTimeoutError
import io.github.kotlinmania.rustlang.sync.mpsc.RecvTimeoutError
import io.github.kotlinmania.rustlang.sync.mpsc.TryRecvError
import io.github.kotlinmania.rustlang.sync.mpsc.TrySendError
import io.github.kotlinmania.rustlang.time.Instant

// Unbounded channel implemented as a linked list.

// Bits indicating the state of a slot:
// * If a message has been written into the slot, `WRITE` is set.
// * If a message has been read from the slot, `READ` is set.
// * If the block is being destroyed, `DESTROY` is set.
public val WRITE: Int = 1

public val READ: Int = 2

public val DESTROY: Int = 4

// Each block covers one "lap" of indices.
public val LAP: Int = 32

// The maximum number of messages a block can hold.
public val BLOCK_CAP: Int = LAP - 1

// How many lower bits are reserved for metadata.
public val SHIFT: Int = 1

// Has two different purposes:
// * If set in head, indicates that the block is not the last one.
// * If set in tail, indicates that the channel is disconnected.
public val MARK_BIT: Int = 1

/**
 * A slot in a block.
 */
public open class Slot<T> internal constructor(
    msgCtorArg: UnsafeCell<MaybeUninit<T>>,
    stateCtorArg: Atomic<Int>,
) {
    /**
     * The message.
     */
    public var msg: UnsafeCell<MaybeUninit<T>> = msgCtorArg
        internal set

    /**
     * The state of the slot.
     */
    public var state: Atomic<Int> = stateCtorArg
        internal set

    /**
     * Waits until a message is written into the slot.
     */
    public open fun waitWrite() {
        val backoff = Backoff.new()
        while (this.state.load(Ordering.Acquire) and WRITE == 0) {
            backoff.spinHeavy()
        }
    }

    public companion object {
        public operator fun <T> invoke(
            msg: UnsafeCell<MaybeUninit<T>>,
            state: Atomic<Int>,
        ): Slot<T> = Slot<T>(
            msgCtorArg = msg,
            stateCtorArg = state,
        )
    }
}

/**
 * A block in a linked list.
 *
 * Each block in the list can hold up to `BLOCK_CAP` messages.
 */
public open class Block<T> internal constructor(
    nextCtorArg: Atomic<MutPtr>,
    /**
     * Slots for messages.
     */
    public var slots: List<Slot<T>>,
) {
    /**
     * The next block in the linked list.
     */
    public var next: Atomic<MutPtr> = nextCtorArg
        internal set

    /**
     * Waits until the next pointer is set.
     */
    public open fun waitNext(): MutPtr {
        val backoff = Backoff.new()
        while (true) {
            val next = this.next.load(Ordering.Acquire)
            if (!next.isNull()) {
                return next
            }
            backoff.spinHeavy()
        }
    }

    public companion object {
        public operator fun <T> invoke(
            next: Atomic<MutPtr>,
            slots: List<Slot<T>>,
        ): Block<T> = Block<T>(
            nextCtorArg = next,
            slots = slots,
        )

        /**
         * Creates an empty block.
         */
        public fun <T> new(): Block<T> {
            // SAFETY: This is safe because:
            // [1] `Block.next` (Atomic<*mut _>) may be safely zero initialized.
            // [2] `Block.slots` (Array) may be safely zero initialized because of [3, 4].
            // [3] `Slot.msg` (UnsafeCell) may be safely zero initialized because it
            // holds a MaybeUninit.
            // [4] `Slot.state` (Atomic<Int>) may be safely zero initialized.
            return Box.newZeroed().assumeInit()
        }

        /**
         * Sets the `DESTROY` bit in slots starting from `start` and destroys the block.
         */
        public fun <T> destroy(thisRef: MutPtr, start: Int, typeLayoutT: TypeLayout<T>) {
            // It is not necessary to set the `DESTROY` bit in the last slot because that slot has
            // begun destruction of the block.
            for (i in start until BLOCK_CAP - 1) {
                val slot = (ObjectSlotLayout<Block<T>>()).readNative((thisRef).rawAddress).slots.getUnchecked(i)
                // Mark the `DESTROY` bit if a thread is still using the slot.
                if (slot.state.load(Ordering.Acquire) and READ == 0 && slot.state.fetchOr(DESTROY, Ordering.AcqRel) and READ == 0) {
                    // If a thread is still using the slot, it will continue destruction of the block.
                    return
                }
            }
            // No thread is using the block, now it is safe to destroy it.
            (Box.fromRaw(thisRef)).let { }
        }
    }
}

/**
 * A position in a channel.
 */
public open class Position<T> internal constructor(
    indexCtorArg: Atomic<Int>,
    blockCtorArg: Atomic<MutPtr>,
) {
    /**
     * The index in the channel.
     */
    public var index: Atomic<Int> = indexCtorArg
        internal set

    /**
     * The block in the linked list.
     */
    public var block: Atomic<MutPtr> = blockCtorArg
        internal set

    public companion object {
        public operator fun <T> invoke(
            index: Atomic<Int>,
            block: Atomic<MutPtr>,
        ): Position<T> = Position<T>(
            indexCtorArg = index,
            blockCtorArg = block,
        )
    }
}

/**
 * The token type for the list flavor.
 */
public open class ListToken internal constructor(
    blockCtorArg: ConstPtr,
    /**
     * The offset into the block.
     */
    public var offset: Int,
) {
    /**
     * The block of slots.
     */
    public var block: ConstPtr = blockCtorArg
        internal set

    public companion object {
        public fun typeLayout(): TypeLayout<ListToken> =
            object : TypeLayout<ListToken> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ListToken =
                    ListToken(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: ListToken) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.block)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.offset)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ListToken =
                    ListToken(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ListToken) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.block)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.offset)
                }
            }

        public operator fun invoke(
            block: ConstPtr,
            offset: Int,
        ): ListToken = ListToken(
            blockCtorArg = block,
            offset = offset,
        )

        public fun default(): ListToken = ListToken(block = ConstPtr(0L), offset = 0)
    }
}

/**
 * Unbounded channel implemented as a linked list.
 *
 * Each message sent into the channel is assigned a sequence number, i.e. an index. Indices are
 * represented as numbers of type `Int` and wrap on overflow.
 *
 * Consecutive messages are grouped into blocks in order to put less pressure on the allocator and
 * improve cache efficiency.
 */
public open class Channel<T> internal constructor(
    headCtorArg: CachePadded<Position<T>>,
    tailCtorArg: CachePadded<Position<T>>,
    /**
     * Receivers waiting while the channel is empty and not disconnected.
     */
    public var receivers: SyncWaker,
    /**
     * Indicates that dropping a `Channel<T>` may drop messages of type `T`.
     */
    public var marker: Unit,
) {
    /**
     * The head of the channel.
     */
    public var head: CachePadded<Position<T>> = headCtorArg
        internal set

    /**
     * The tail of the channel.
     */
    public var tail: CachePadded<Position<T>> = tailCtorArg
        internal set

    /**
     * Attempts to reserve a slot for sending a message.
     */
    public open fun startSend(token: Token): Boolean {
        val backoff = Backoff.new()
        var tail = this.tail.index.load(Ordering.Acquire)
        var block = this.tail.block.load(Ordering.Acquire)
        var nextBlock = null
        while (true) {
            // Check if the channel is disconnected.
            if (tail and MARK_BIT != 0) {
                token.list.block = ConstPtr(0L)
                return true
            }
            // Calculate the offset of the index into the block.
            val offset = (tail shr SHIFT) % LAP
            // If we reached the end of the block, wait until the next one is installed.
            if (offset == BLOCK_CAP) {
                backoff.spinHeavy()
                tail = this.tail.index.load(Ordering.Acquire)
                block = this.tail.block.load(Ordering.Acquire)
                continue
            }
            // If we're going to have to install the next block, allocate it in advance in order to
            // make the wait for other threads as short as possible.
            if (offset + 1 == BLOCK_CAP && nextBlock == null) {
                nextBlock = Block.new()
            }
            // If this is the first message to be sent into the channel, we need to allocate the
            // first block and install it.
            if (block.isNull()) {
                val new = Box.intoRaw(Block.new())
                if (this.tail.block.compareExchange(block, new, Ordering.Release, Ordering.Relaxed).isOk()) {
                    this.head.block.store(new, Ordering.Release)
                    block = new
                } else {
                    nextBlock = Box.fromRaw(new)
                    tail = this.tail.index.load(Ordering.Acquire)
                    block = this.tail.block.load(Ordering.Acquire)
                    continue
                }
            }
            val newTail = tail + (1 shl SHIFT)
            // Try advancing the tail forward.
            run {
                val matchVal = this.tail.index.compareExchangeWeak(tail, newTail, Ordering.SeqCst, Ordering.Acquire)
                when (matchVal) { is Result.Ok<*, *> -> {
                    run {
                        // If we've reached the end of the block, install the next one.
                        if (offset + 1 == BLOCK_CAP) {
                            val nextBlock1 = Box.intoRaw(nextBlock!!)
                            this.tail.block.store(nextBlock1, Ordering.Release)
                            this.tail.index.fetchAdd(1 shl SHIFT, Ordering.Release)
                            block.next.store(nextBlock1, Ordering.Release)
                        }
                        token.list.block = constPtrFromRustCast(block)
                        token.list.offset = offset
                        return true
                    }
                }; is Result.Err<*, *> -> {
                    backoff.spinLight()
                    tail = this.tail.index.load(Ordering.Acquire)
                    block = this.tail.block.load(Ordering.Acquire)
                } }
            }
        }
    }

    /**
     * Writes a message into the channel.
     */
    public open fun write(token: Token, msg: T): Result<Unit, T> {
        // If there is no slot, the channel is disconnected.
        if (((token.list.block).rawAddress == 0L)) {
            return Result.Err(msg)
        }
        // Write the message into the slot.
        val block = (token.list.block).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        val offset = token.list.offset
        run {
            val slot = (ObjectSlotLayout<Block<T>>()).readNative((block).rawAddress).slots.getUnchecked(offset)
            slot.msg.get().write(MaybeUninit.new(msg, ObjectSlotLayout<T>()))
            slot.state.fetchOr(WRITE, Ordering.Release)
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
        var head = this.head.index.load(Ordering.Acquire)
        var block = this.head.block.load(Ordering.Acquire)
        while (true) {
            // Calculate the offset of the index into the block.
            val offset = (head shr SHIFT) % LAP
            // If we reached the end of the block, wait until the next one is installed.
            if (offset == BLOCK_CAP) {
                backoff.spinHeavy()
                head = this.head.index.load(Ordering.Acquire)
                block = this.head.block.load(Ordering.Acquire)
                continue
            }
            var newHead = head + (1 shl SHIFT)
            if (newHead and MARK_BIT == 0) {
                fence(Ordering.SeqCst)
                val tail = this.tail.index.load(Ordering.Relaxed)
                // If the tail equals the head, that means the channel is empty.
                if (head shr SHIFT == tail shr SHIFT) {
                    // If the channel is disconnected...
                    if (tail and MARK_BIT != 0) {
                        // ...then receive an error.
                        token.list.block = ConstPtr(0L)
                        return true
                    } else {
                        // Otherwise, the receive operation is not ready.
                        return false
                    }
                }
                // If head and tail are not in the same block, set `MARK_BIT` in head.
                if ((head shr SHIFT) / LAP != (tail shr SHIFT) / LAP) {
                    newHead = newHead or MARK_BIT
                }
            }
            // The block can be null here only if the first message is being sent into the channel.
            // In that case, just wait until it gets initialized.
            if (block.isNull()) {
                backoff.spinHeavy()
                head = this.head.index.load(Ordering.Acquire)
                block = this.head.block.load(Ordering.Acquire)
                continue
            }
            // Try moving the head index forward.
            run {
                val matchVal = this.head.index.compareExchangeWeak(head, newHead, Ordering.SeqCst, Ordering.Acquire)
                when (matchVal) { is Result.Ok<*, *> -> {
                    run {
                        // If we've reached the end of the block, move to the next one.
                        if (offset + 1 == BLOCK_CAP) {
                            val next = block.waitNext()
                            var nextIndex = ((newHead and MARK_BIT.inv()) + ((1 shl SHIFT).toInt()))
                            if (!next.next.load(Ordering.Relaxed).isNull()) {
                                nextIndex = nextIndex or MARK_BIT
                            }
                            this.head.block.store(next, Ordering.Release)
                            this.head.index.store(nextIndex, Ordering.Release)
                        }
                        token.list.block = constPtrFromRustCast(block)
                        token.list.offset = offset
                        return true
                    }
                }; is Result.Err<*, *> -> {
                    backoff.spinLight()
                    head = this.head.index.load(Ordering.Acquire)
                    block = this.head.block.load(Ordering.Acquire)
                } }
            }
        }
    }

    /**
     * Reads a message from the channel.
     */
    public open fun read(token: Token): Result<T, Unit> {
        if (((token.list.block).rawAddress == 0L)) {
            // The channel is disconnected.
            return Result.Err(Unit)
        }
        // Read the message.
        val block = (token.list.block).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        val offset = token.list.offset
        return run {
            val slot = (ObjectSlotLayout<Block<T>>()).readNative((block).rawAddress).slots.getUnchecked(offset)
            slot.waitWrite()
            val msg = slot.msg.get().read().assumeInit()
            // Destroy the block if we've reached the end, or if another thread wanted to destroy but
            // couldn't because we were busy reading from the slot.
            if (offset + 1 == BLOCK_CAP) {
                Block.destroy(block, 0)
            } else if (slot.state.fetchOr(READ, Ordering.AcqRel) and DESTROY != 0) {
                Block.destroy(block, offset + 1)
            }
            Result.Ok(msg)
        }
    }

    /**
     * Attempts to send a message into the channel.
     */
    public open fun trySend(msg: T): Result<Unit, TrySendError<T>> =
        this.send(msg, null).mapErr({ err -> when (err) {
            is SendTimeoutError.Disconnected -> {
                val (msg1) = err
                TrySendError.Disconnected(msg1)
            }
            is SendTimeoutError.Timeout -> error("unreachable")
        } })

    /**
     * Sends a message into the channel.
     */
    public open fun send(msg: T, deadline: Instant?): Result<Unit, SendTimeoutError<T>> {
        val token = Token.default()
        check(this.startSend(token))
        return this.write(token, msg).mapErr({ SendTimeoutError.Disconnected(it) })
    }

    /**
     * Attempts to receive a message without blocking.
     */
    public open fun tryRecv(): Result<T, TryRecvError> {
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
    public open fun recv(deadline: Instant?): Result<T, RecvTimeoutError> {
        val token = Token.default()
        while (true) {
            if (this.startRecv(token)) {
                run {
                    return this.read(token).mapErr({ RecvTimeoutError.Disconnected })
                }
            }
            if (deadline != null) {
                val d = deadline
                if (Instant.now() >= d) {
                    return Result.Err(RecvTimeoutError.Timeout)
                }
            }
            // Prepare for blocking until a sender wakes us up.
            Context.with({ cx ->
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
    public open fun len(): Int {
        while (true) {
            // Load the tail index, then load the head index.
            var tail = this.tail.index.load(Ordering.SeqCst)
            var head = this.head.index.load(Ordering.SeqCst)
            // If the tail index didn't change, we've got consistent indices to work with.
            if (this.tail.index.load(Ordering.SeqCst) == tail) {
                // Erase the lower bits.
                tail = tail and ((1 shl SHIFT) - 1).inv()
                head = head and ((1 shl SHIFT) - 1).inv()
                // Fix up indices if they fall onto block ends.
                if ((tail shr SHIFT) and (LAP - 1) == LAP - 1) {
                    tail = tail.wrappingAdd(1 shl SHIFT)
                }
                if ((head shr SHIFT) and (LAP - 1) == LAP - 1) {
                    head = head.wrappingAdd(1 shl SHIFT)
                }
                // Rotate indices so that head falls into the first block.
                val lap = (head shr SHIFT) / LAP
                tail = tail.wrappingSub((lap * LAP) shl SHIFT)
                head = head.wrappingSub((lap * LAP) shl SHIFT)
                // Remove the lower bits.
                tail = tail shr SHIFT
                head = head shr SHIFT
                // Return the difference minus the number of blocks between tail and head.
                return tail - head - tail / LAP
            }
        }
    }

    /**
     * Returns the capacity of the channel.
     */
    public open fun capacity(): Int? = null

    /**
     * Disconnects senders and wakes up all blocked receivers.
     *
     * Returns `true` if this call disconnected the channel.
     */
    public open fun disconnectSenders(): Boolean {
        val tail = this.tail.index.fetchOr(MARK_BIT, Ordering.SeqCst)
        return if (tail and MARK_BIT == 0) {
            this.receivers.disconnect()
            true
        } else {
            false
        }
    }

    /**
     * Disconnects receivers.
     *
     * Returns `true` if this call disconnected the channel.
     */
    public open fun disconnectReceivers(): Boolean {
        val tail = this.tail.index.fetchOr(MARK_BIT, Ordering.SeqCst)
        return if (tail and MARK_BIT == 0) {
            // If receivers are dropped first, discard all messages to free
            // memory eagerly.
            this.discardAllMessages()
            true
        } else {
            false
        }
    }

    /**
     * Discards all messages.
     *
     * This method should only be called when all receivers are dropped.
     */
    public open fun discardAllMessages() {
        val backoff = Backoff.new()
        var tail = this.tail.index.load(Ordering.Acquire)
        while (true) {
            val offset = (tail shr SHIFT) % LAP
            if (offset != BLOCK_CAP) {
                break
            }
            // New updates to tail will be rejected by MARK_BIT and aborted unless it's
            // at boundary. We need to wait for the updates take affect otherwise there
            // can be memory leaks.
            backoff.spinHeavy()
            tail = this.tail.index.load(Ordering.Acquire)
        }
        var head = this.head.index.load(Ordering.Acquire)
        // The channel may be uninitialized, so we have to swap to avoid overwriting any sender's attempts
        // to initialize the first block before noticing that the receivers disconnected. Late allocations
        // will be deallocated by the sender in Drop.
        var block = this.head.block.swap(MutPtr(0L), Ordering.AcqRel)
        // If we're going to be dropping messages we need to synchronize with initialization
        if (head shr SHIFT != tail shr SHIFT) {
            // The block can be null here only if a sender is in the process of initializing the
            // channel while another sender managed to send a message by inserting it into the
            // semi-initialized channel and advanced the tail.
            // In that case, just wait until it gets initialized.
            while (block.isNull()) {
                backoff.spinHeavy()
                block = this.head.block.swap(MutPtr(0L), Ordering.AcqRel)
            }
        }
        // After this point `head.block` is not modified again and it will be deallocated if it's
        // non-null. The `Drop` code of the channel, which runs after this function, also attempts
        // to deallocate `head.block` if it's non-null. Therefore this function must maintain the
        // invariant that if a deallocation of head.block is attempted then it must also be set to
        // NULL. Failing to do so will lead to the Drop code attempting a double free. For this
        // reason both reads above do an atomic swap instead of a simple atomic load.
        run {
            // Drop all messages between head and tail and deallocate the heap-allocated blocks.
            while (head shr SHIFT != tail shr SHIFT) {
                val offset1 = (head shr SHIFT) % LAP
                if (offset1 < BLOCK_CAP) {
                    // Drop the message in the slot.
                    val slot = block.slots.getUnchecked(offset1)
                    slot.waitWrite()
                    val p = slot.msg.get()
                    p.asMutPtr().dropInPlace()
                } else {
                    block.waitNext()
                    // Deallocate the block and move to the next one.
                    val next = block.next.load(Ordering.Acquire)
                    (Box.fromRaw(block)).let { }
                    block = next
                }
                head = head.wrappingAdd(1 shl SHIFT)
            }
            // Deallocate the last remaining block.
            if (!block.isNull()) {
                (Box.fromRaw(block)).let { }
            }
        }
        head = head and MARK_BIT.inv()
        this.head.index.store(head, Ordering.Release)
    }

    /**
     * Returns `true` if the channel is disconnected.
     */
    public open fun isDisconnected(): Boolean = (this.tail.index.load(Ordering.SeqCst) and MARK_BIT != 0).toUByte()

    /**
     * Returns `true` if the channel is empty.
     */
    public open fun isEmpty(): Boolean {
        val head = this.head.index.load(Ordering.SeqCst)
        val tail = this.tail.index.load(Ordering.SeqCst)
        return (head shr SHIFT == tail shr SHIFT).toUByte()
    }

    /**
     * Returns `true` if the channel is full.
     */
    public open fun isFull(): Boolean = false

    public open fun drop() {
        var head = this.head.index.load(Ordering.Relaxed)
        var tail = this.tail.index.load(Ordering.Relaxed)
        var block = this.head.block.load(Ordering.Relaxed)
        // Erase the lower bits.
        head = head and ((1 shl SHIFT) - 1).inv()
        tail = tail and ((1 shl SHIFT) - 1).inv()
        run {
            // Drop all messages between head and tail and deallocate the heap-allocated blocks.
            while (head != tail) {
                val offset = (head shr SHIFT) % LAP
                if (offset < BLOCK_CAP) {
                    // Drop the message in the slot.
                    val slot = block.slots.getUnchecked(offset)
                    val p = slot.msg.get()
                    p.asMutPtr().dropInPlace()
                } else {
                    // Deallocate the block and move to the next one.
                    val next = block.next.load(Ordering.Relaxed)
                    (Box.fromRaw(block)).let { }
                    block = next
                }
                head = head.wrappingAdd(1 shl SHIFT)
            }
            // Deallocate the last remaining block.
            if (!block.isNull()) {
                (Box.fromRaw(block)).let { }
            }
        }
    }

    public companion object {
        public operator fun <T> invoke(
            head: CachePadded<Position<T>>,
            tail: CachePadded<Position<T>>,
            receivers: SyncWaker,
            marker: Unit,
        ): Channel<T> = Channel<T>(
            headCtorArg = head,
            tailCtorArg = tail,
            receivers = receivers,
            marker = marker,
        )

        /**
         * Creates a new unbounded channel.
         */
        public fun <T> new(): Channel<T> = Channel(head = CachePadded.new(Position(block = AtomicPtr.new(MutPtr(0L)), index = AtomicUsize.new(0))), tail = CachePadded.new(Position(block = AtomicPtr.new(MutPtr(0L)), index = AtomicUsize.new(0))), receivers = SyncWaker.new(), marker = Unit)
    }
}
