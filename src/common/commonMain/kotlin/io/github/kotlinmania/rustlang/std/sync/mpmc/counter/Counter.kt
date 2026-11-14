// port-lint: source sync/mpmc/counter.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.counter

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sys.process.unix.unix.Process

/**
 * Reference counter internals.
 */
public open class Counter<C> internal constructor(
    sendersCtorArg: Atomic<Int>,
    receiversCtorArg: Atomic<Int>,
    destroyCtorArg: Atomic<Boolean>,
    /**
     * The internal channel.
     */
    public var chan: C,
) {
    /**
     * The number of senders associated with the channel.
     */
    public var senders: Atomic<Int> = sendersCtorArg
        internal set

    /**
     * The number of receivers associated with the channel.
     */
    public var receivers: Atomic<Int> = receiversCtorArg
        internal set

    /**
     * Set to `true` if the last sender or the last receiver reference deallocates the channel.
     */
    public var destroy: Atomic<Boolean> = destroyCtorArg
        internal set

    public companion object {
        public operator fun <C> invoke(
            senders: Atomic<Int>,
            receivers: Atomic<Int>,
            destroy: Atomic<Boolean>,
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
internal fun <C> new(chan: C): Pair<Sender<C>, Receiver<C>> {
    val counter = Box.intoRaw(Counter(senders = AtomicUsize.new(1), receivers = AtomicUsize.new(1), destroy = AtomicBool.new(false), chan = chan))
    val s = Sender(counter = counter)
    val r = Receiver(counter = counter)
    return Pair(s, r)
}

/**
 * The sending side.
 */
internal open class Sender<C>(
    public var counter: MutPtr,
) {
    /**
     * Returns the internal `Counter`.
     */
    public open fun counter(): Counter<C> = (ObjectSlotLayout<Counter<C>>()).readNative((this.counter).rawAddress)

    /**
     * Acquires another sender reference.
     */
    internal open fun acquire(): Sender<C> {
        val count = this.counter().senders.fetchAdd(1, Ordering.Relaxed)
        // Cloning senders and calling `mem.forget` on the clones could potentially overflow the
        // counter. It's very difficult to recover sensibly from such degenerate scenarios so we
        // just abort when the count becomes very large.
        if (count > Int.MAX_VALUE) {
            Process.abort()
        }
        return Sender(counter = this.counter)
    }

    /**
     * Releases the sender reference.
     *
     * Function `disconnect` will be called if this is the last sender reference.
     */
    internal open fun release(disconnect: (C) -> Boolean) {
        if (this.counter().senders.fetchSub(1, Ordering.AcqRel) == 1) {
            disconnect(this.counter().chan)
            if (this.counter().destroy.swap(true, Ordering.AcqRel)) {
                drop(Box.fromRaw(this.counter))
            }
        }
    }

    public open fun deref(): C = this.counter().chan

    public open fun eq(other: Sender<C>): Boolean = (((this.counter).rawAddress.toULong() == (other.counter).rawAddress.toULong())).toUByte()

    public companion object {
        public fun <C> typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sender<C>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sender<C>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Sender<C> =
                    Sender(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Sender<C>) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.counter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sender<C> =
                    Sender(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sender<C>) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.counter)
                }
            }
    }
}

/**
 * The receiving side.
 */
internal open class Receiver<C>(
    public var counter: MutPtr,
) {
    /**
     * Returns the internal `Counter`.
     */
    public open fun counter(): Counter<C> = (ObjectSlotLayout<Counter<C>>()).readNative((this.counter).rawAddress)

    /**
     * Acquires another receiver reference.
     */
    internal open fun acquire(): Receiver<C> {
        val count = this.counter().receivers.fetchAdd(1, Ordering.Relaxed)
        // Cloning receivers and calling `mem.forget` on the clones could potentially overflow the
        // counter. It's very difficult to recover sensibly from such degenerate scenarios so we
        // just abort when the count becomes very large.
        if (count > Int.MAX_VALUE) {
            Process.abort()
        }
        return Receiver(counter = this.counter)
    }

    /**
     * Releases the receiver reference.
     *
     * Function `disconnect` will be called if this is the last receiver reference.
     */
    internal open fun release(disconnect: (C) -> Boolean) {
        if (this.counter().receivers.fetchSub(1, Ordering.AcqRel) == 1) {
            disconnect(this.counter().chan)
            if (this.counter().destroy.swap(true, Ordering.AcqRel)) {
                drop(Box.fromRaw(this.counter))
            }
        }
    }

    public open fun deref(): C = this.counter().chan

    public open fun eq(other: Receiver<C>): Boolean = (((this.counter).rawAddress.toULong() == (other.counter).rawAddress.toULong())).toUByte()

    public companion object {
        public fun <C> typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Receiver<C>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Receiver<C>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Receiver<C> =
                    Receiver(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Receiver<C>) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.counter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Receiver<C> =
                    Receiver(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Receiver<C>) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.counter)
                }
            }
    }
}
