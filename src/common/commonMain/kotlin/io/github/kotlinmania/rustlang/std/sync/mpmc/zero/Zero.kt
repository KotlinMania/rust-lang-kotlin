// port-lint: source sync/mpmc/zero.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.zero

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sync.mpmc.SendTimeoutError
import io.github.kotlinmania.rustlang.std.sync.mpmc.context.Context
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Operation
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Selected
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Token
import io.github.kotlinmania.rustlang.std.sync.mpmc.utils.Backoff
import io.github.kotlinmania.rustlang.std.sync.mpmc.waker.Waker
import io.github.kotlinmania.rustlang.std.sync.mpsc.RecvTimeoutError
import io.github.kotlinmania.rustlang.std.sync.mpsc.TryRecvError
import io.github.kotlinmania.rustlang.std.sync.mpsc.TrySendError
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.time.Instant

// Zero-capacity channel.
//
// This kind of channel is also known as *rendezvous* channel.

/**
 * A pointer to a packet.
 */
public open class ZeroToken internal constructor(
    valueCtorArg: MutPtr,
) {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append(((this.value).rawAddress.toInt()).toString()); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<ZeroToken> =
            object : TypeLayout<ZeroToken> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): ZeroToken =
                    ZeroToken(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ZeroToken) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ZeroToken =
                    ZeroToken(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ZeroToken) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): ZeroToken = ZeroToken(
            valueCtorArg = value,
        )

        public fun default(): ZeroToken = ZeroToken(MutPtr(0L))
    }
}

/**
 * A slot for passing one message from a sender to a receiver.
 */
public open class Packet<T> internal constructor(
    /**
     * Equals `true` if the packet is allocated on the stack.
     */
    public var onStack: Boolean,
    readyCtorArg: Atomic<Boolean, Align1<UByte>>,
    /**
     * The message.
     */
    public var msg: UnsafeCell<T?>,
) {
    /**
     * Equals `true` once the packet is ready for reading or writing.
     */
    public var ready: Atomic<Boolean, Align1<UByte>> = readyCtorArg
        internal set

    /**
     * Waits until the packet becomes ready for reading or writing.
     */
    public open fun waitReady() {
        val backoff = Backoff.new()
        while (!this.ready.load(Ordering.Acquire)) {
            backoff.spinHeavy()
        }
    }

    public companion object {
        public operator fun <T> invoke(
            onStack: Boolean,
            ready: Atomic<Boolean, Align1<UByte>>,
            msg: UnsafeCell<T?>,
        ): Packet<T> = Packet<T>(
            onStack = onStack,
            readyCtorArg = ready,
            msg = msg,
        )

        /**
         * Creates an empty packet on the stack.
         */
        public fun <T> emptyOnStack(): Packet<T> = Packet(onStack = true, ready = AtomicBool.new(false), msg = UnsafeCell.new(null))

        /**
         * Creates a packet on the stack, containing a message.
         */
        public fun <T> messageOnStack(msg: T): Packet<T> = Packet(onStack = true, ready = AtomicBool.new(false), msg = UnsafeCell.new(msg))
    }
}

/**
 * Inner representation of a zero-capacity channel.
 */
public open class Inner(
    /**
     * Senders waiting to pair up with a receive operation.
     */
    public var senders: Waker,
    /**
     * Receivers waiting to pair up with a send operation.
     */
    public var receivers: Waker,
    /**
     * Equals `true` when the channel is disconnected.
     */
    public var isDisconnected: Boolean,
)

/**
 * Zero-capacity channel.
 */
public open class Channel<T> internal constructor(
    innerCtorArg: Mutex<Inner>,
    /**
     * Indicates that dropping a `Channel<T>` may drop values of type `T`.
     */
    public var marker: Unit,
) {
    /**
     * Inner representation of the channel.
     */
    public var inner: Mutex<Inner> = innerCtorArg
        internal set

    /**
     * Writes a message into the packet.
     */
    public open fun write(token: Token, msg: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Unit, T> {
        // If there is no packet, the channel is disconnected.
        if (((token.zero.value).rawAddress == 0L)) {
            return Result.Err<Unit, T>(msg)
        }
        run {
            val packet = (UnresolvedTypeLayout<Packet<T>>()).readNative((((token.zero.value).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
            packet.msg.get().write(msg)
            packet.ready.store(true, Ordering.Release)
        }
        return Result.Ok<Unit, T>(Unit)
    }

    /**
     * Reads a message from the packet.
     */
    public open fun read(token: Token, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<T, Unit> {
        // If there is no packet, the channel is disconnected.
        if (((token.zero.value).rawAddress == 0L)) {
            return Result.Err<T, Unit>(Unit)
        }
        val packet = (UnresolvedTypeLayout<Packet<T>>()).readNative((((token.zero.value).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        return if (packet.onStack) {
            // The message has been in the packet from the beginning, so there is no need to wait
            // for it. However, after reading the message, we need to set `ready` to `true` in
            // order to signal that the packet can be destroyed.
            val msg = packet.msg.get().replace(null).unwrap()
            packet.ready.store(true, Ordering.Release)
            Result.Ok<T, Unit>(msg)
        } else {
            // Wait until the message becomes available, then read it and destroy the
            // heap-allocated packet.
            packet.waitReady()
            run {
                val msg1 = packet.msg.get().replace(null).unwrap()
                (Box.fromRaw((token.zero.value).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).let { }
                Result.Ok<T, Unit>(msg1)
            }
        }
    }

    /**
     * Attempts to send a message into the channel.
     */
    public open fun trySend(msg: T): Result<Unit, TrySendError<T>> {
        val token = Token.default()
        val inner = this.inner.lock().unwrap()
        // If there's a waiting receiver, pair up with it.
        return when (val matchVal = inner.receivers.trySelect()) {
            null -> {
                if (inner.isDisconnected) {
                    Result.Err<Unit, TrySendError<T>>(TrySendError.Disconnected(msg))
                } else {
                    Result.Err<Unit, TrySendError<T>>(TrySendError.Full(msg))
                }
            }
            else -> {
                val operation = matchVal
                token.zero.first = operation.packet
                (inner).let { }
                run {
                    this.write(token, msg).ok()!!
                }
                Result.Ok<Unit, TrySendError<T>>(Unit)
            }
        }
    }

    /**
     * Sends a message into the channel.
     */
    public open fun send(msg: T, deadline: Instant?): Result<Unit, SendTimeoutError<T>> {
        val token = Token.default()
        val inner = this.inner.lock().unwrap()
        // If there's a waiting receiver, pair up with it.
        when (val matchVal = inner.receivers.trySelect()) {
            null -> {}
            else -> {
                val operation = matchVal
                token.zero.first = operation.packet
                (inner).let { }
                run {
                    this.write(token, msg).ok()!!
                }
                return Result.Ok<Unit, SendTimeoutError<T>>(Unit)
            }
        }
        if (inner.isDisconnected) {
            return Result.Err<Unit, SendTimeoutError<T>>(SendTimeoutError.Disconnected(msg))
        }
        return Context.with({ cx ->
            // Prepare for blocking until a receiver wakes us up.
            val oper = Operation.hook(token)
            var packet = Packet.messageOnStack(msg)
            inner.senders.registerWithPacket(oper, ((addrOfMut(packet))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, cx)
            inner.receivers.notify()
            (inner).let { }
            // Block the current thread.
            // SAFETY: the context belongs to the current thread.
            val sel = cx.waitUntil(deadline)
            when (sel) {
                Selected.Waiting -> error("unreachable")
                Selected.Aborted -> {
                    this.inner.lock().unwrap().senders.unregister(oper).unwrap()
                    val msg1 = packet.msg.get().replace(null).unwrap()
                    Result.Err(SendTimeoutError.Timeout(msg1))
                }
                Selected.Disconnected -> {
                    this.inner.lock().unwrap().senders.unregister(oper).unwrap()
                    val msg2 = packet.msg.get().replace(null).unwrap()
                    Result.Err(SendTimeoutError.Disconnected(msg2))
                }
                is Selected.Operation -> {
                    // Wait until the message is read, then drop the packet.
                    packet.waitReady()
                    Result.Ok(Unit)
                }
            }
        })
    }

    /**
     * Attempts to receive a message without blocking.
     */
    public open fun tryRecv(): Result<T, TryRecvError> {
        val token = Token.default()
        val inner = this.inner.lock().unwrap()
        // If there's a waiting sender, pair up with it.
        return when (val matchVal = inner.senders.trySelect()) {
            null -> {
                if (inner.isDisconnected) {
                    Result.Err<T, TryRecvError>(TryRecvError.Disconnected)
                } else {
                    Result.Err<T, TryRecvError>(TryRecvError.Empty)
                }
            }
            else -> {
                val operation = matchVal
                token.zero.first = operation.packet
                (inner).let { }
                this.read(token).mapErr({ TryRecvError.Disconnected })
            }
        }
    }

    /**
     * Receives a message from the channel.
     */
    public open fun recv(deadline: Instant?): Result<T, RecvTimeoutError> {
        val token = Token.default()
        val inner = this.inner.lock().unwrap()
        // If there's a waiting sender, pair up with it.
        when (val matchVal = inner.senders.trySelect()) {
            null -> {}
            else -> {
                val operation = matchVal
                token.zero.first = operation.packet
                (inner).let { }
                run {
                    return this.read(token).mapErr({ RecvTimeoutError.Disconnected })
                }
            }
        }
        if (inner.isDisconnected) {
            return Result.Err<T, RecvTimeoutError>(RecvTimeoutError.Disconnected)
        }
        return Context.with({ cx ->
            // Prepare for blocking until a sender wakes us up.
            val oper = Operation.hook(token)
            var packet = Packet.emptyOnStack()
            inner.receivers.registerWithPacket(oper, ((addrOfMut(packet))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, cx)
            inner.senders.notify()
            (inner).let { }
            // Block the current thread.
            // SAFETY: the context belongs to the current thread.
            val sel = cx.waitUntil(deadline)
            when (sel) {
                Selected.Waiting -> error("unreachable")
                Selected.Aborted -> {
                    this.inner.lock().unwrap().receivers.unregister(oper).unwrap()
                    Result.Err(RecvTimeoutError.Timeout)
                }
                Selected.Disconnected -> {
                    this.inner.lock().unwrap().receivers.unregister(oper).unwrap()
                    Result.Err(RecvTimeoutError.Disconnected)
                }
                is Selected.Operation -> {
                    // Wait until the message is provided, then read it.
                    packet.waitReady()
                    Result.Ok(packet.msg.get().replace(null).unwrap())
                }
            }
        })
    }

    /**
     * Disconnects the channel and wakes up all blocked senders and receivers.
     *
     * Returns `true` if this call disconnected the channel.
     */
    public open fun disconnect(): Boolean {
        val inner = this.inner.lock().unwrap()
        return if (!inner.isDisconnected) {
            inner.isDisconnected = true
            inner.senders.disconnect()
            inner.receivers.disconnect()
            true
        } else {
            false
        }
    }

    /**
     * Returns the current number of messages inside the channel.
     */
    public open fun len(): Int = 0

    // This is intentional.

    /**
     * Returns the capacity of the channel.
     */
    public open fun capacity(): Int? = 0

    /**
     * Returns `true` if the channel is empty.
     */
    public open fun isEmpty(): Boolean = true

    /**
     * Returns `true` if the channel is full.
     */
    public open fun isFull(): Boolean = true

    /**
     * Returns `true` if the channel is disconnected.
     */
    public open fun isDisconnected(): Boolean {
        val inner = this.inner.lock().unwrap()
        return inner.isDisconnected
    }

    public companion object {
        public operator fun <T> invoke(
            inner: Mutex<Inner>,
            marker: Unit,
        ): Channel<T> = Channel<T>(
            innerCtorArg = inner,
            marker = marker,
        )

        /**
         * Constructs a new zero-capacity channel.
         */
        public fun <T> new(): Channel<T> = Channel(inner = Mutex.new(Inner(senders = Waker.new(), receivers = Waker.new(), isDisconnected = false)), marker = Unit)
    }
}
