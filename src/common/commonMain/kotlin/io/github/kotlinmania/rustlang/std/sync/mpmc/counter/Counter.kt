// port-lint: source sync/mpmc/counter.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.counter

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.swap
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

/**
 * Reference counter internals.
 */
public open class Counter<C> internal constructor(
    sendersCtorArg: Atomic<Int, Align8<Int>>,
    receiversCtorArg: Atomic<Int, Align8<Int>>,
    destroyCtorArg: Atomic<Boolean, Align1<UByte>>,
    /**
     * The internal channel.
     */
    public var chan: C,
) {
    /**
     * The number of senders associated with the channel.
     */
    public var senders: Atomic<Int, Align8<Int>> = sendersCtorArg
        internal set

    /**
     * The number of receivers associated with the channel.
     */
    public var receivers: Atomic<Int, Align8<Int>> = receiversCtorArg
        internal set

    /**
     * Set to `true` if the last sender or the last receiver reference deallocates the channel.
     */
    public var destroy: Atomic<Boolean, Align1<UByte>> = destroyCtorArg
        internal set

    public companion object {
        public operator fun <C> invoke(
            senders: Atomic<Int, Align8<Int>>,
            receivers: Atomic<Int, Align8<Int>>,
            destroy: Atomic<Boolean, Align1<UByte>>,
            chan: C,
        ): Counter<C> = Counter<C>(
            sendersCtorArg = senders,
            receiversCtorArg = receivers,
            destroyCtorArg = destroy,
            chan = chan,
        )
    }
}

/**
 * Wraps a channel into the reference counter.
 */
public fun <C> new(chan: C): Pair<Sender<C>, Receiver<C>> {
    val counter = Box.intoRaw(Counter(senders = AtomicUsize.new(1), receivers = AtomicUsize.new(1), destroy = AtomicBool.new(false), chan = chan))
    val s = Sender(counter = counter)
    val r = Receiver(counter = counter)
    return Pair(s, r)
}

/**
 * The sending side.
 */
public open class Sender<C> internal constructor(
    counterCtorArg: MutPtr,
) {
    public var counter: MutPtr = counterCtorArg
        internal set

    /**
     * Returns the internal `Counter`.
     */
    public open fun counter(): Counter<C> = (UnresolvedTypeLayout<Counter<C>>()).readNative((this.counter).rawAddress)

    /**
     * Acquires another sender reference.
     */
    public open fun acquire(): Sender<C> {
        val count = this.counter().senders.fetchAdd(1, Ordering.Relaxed)
        // Cloning senders and calling `mem.forget` on the clones could potentially overflow the
        // counter. It's very difficult to recover sensibly from such degenerate scenarios so we
        // just abort when the count becomes very large.
        if (count > Int.MAX_VALUE) {
            process.abort()
        }
        return Sender(counter = this.counter)
    }

    /**
     * Releases the sender reference.
     *
     * Function `disconnect` will be called if this is the last sender reference.
     */
    public open fun release(disconnect: (C) -> Boolean) {
        if (this.counter().senders.fetchSub(1, Ordering.AcqRel) == 1) {
            disconnect(this.counter().chan)
            if (this.counter().destroy.swap(true, Ordering.AcqRel)) {
                (Box.fromRaw(this.counter)).let { }
            }
        }
    }

    public open fun deref(): C = this.counter().chan

    public open fun eq(other: Sender<C>): Boolean = (((this.counter).rawAddress.toULong() == (other.counter).rawAddress.toULong())).toUByte()

    public companion object {
        public fun <C> typeLayout(): TypeLayout<Sender<C>> =
            object : TypeLayout<Sender<C>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Sender<C> =
                    Sender(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Sender<C>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.counter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sender<C> =
                    Sender(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sender<C>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.counter)
                }
            }

        public operator fun <C> invoke(
            counter: MutPtr,
        ): Sender<C> = Sender<C>(
            counterCtorArg = counter,
        )
    }
}

/**
 * The receiving side.
 */
public open class Receiver<C> internal constructor(
    counterCtorArg: MutPtr,
) {
    public var counter: MutPtr = counterCtorArg
        internal set

    /**
     * Returns the internal `Counter`.
     */
    public open fun counter(): Counter<C> = (UnresolvedTypeLayout<Counter<C>>()).readNative((this.counter).rawAddress)

    /**
     * Acquires another receiver reference.
     */
    public open fun acquire(): Receiver<C> {
        val count = this.counter().receivers.fetchAdd(1, Ordering.Relaxed)
        // Cloning receivers and calling `mem.forget` on the clones could potentially overflow the
        // counter. It's very difficult to recover sensibly from such degenerate scenarios so we
        // just abort when the count becomes very large.
        if (count > Int.MAX_VALUE) {
            process.abort()
        }
        return Receiver(counter = this.counter)
    }

    /**
     * Releases the receiver reference.
     *
     * Function `disconnect` will be called if this is the last receiver reference.
     */
    public open fun release(disconnect: (C) -> Boolean) {
        if (this.counter().receivers.fetchSub(1, Ordering.AcqRel) == 1) {
            disconnect(this.counter().chan)
            if (this.counter().destroy.swap(true, Ordering.AcqRel)) {
                (Box.fromRaw(this.counter)).let { }
            }
        }
    }

    public open fun deref(): C = this.counter().chan

    public open fun eq(other: Receiver<C>): Boolean = (((this.counter).rawAddress.toULong() == (other.counter).rawAddress.toULong())).toUByte()

    public companion object {
        public fun <C> typeLayout(): TypeLayout<Receiver<C>> =
            object : TypeLayout<Receiver<C>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Receiver<C> =
                    Receiver(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Receiver<C>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.counter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Receiver<C> =
                    Receiver(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Receiver<C>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.counter)
                }
            }

        public operator fun <C> invoke(
            counter: MutPtr,
        ): Receiver<C> = Receiver<C>(
            counterCtorArg = counter,
        )
    }
}
