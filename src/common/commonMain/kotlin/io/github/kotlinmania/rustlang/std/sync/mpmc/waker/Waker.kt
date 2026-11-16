// port-lint: source sync/mpmc/waker.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.waker

import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sync.mpmc.context.Context
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Operation
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Selected
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.thread.LocalKey
import io.github.kotlinmania.rustlang.std.thread.localimpl.threadLocalInner

// Waking mechanism for threads blocked on channel operations.

/**
 * Represents a thread blocked on a specific channel operation.
 */
public open class Entry internal constructor(
    /**
     * The operation.
     */
    public var oper: Operation,
    packetCtorArg: MutPtr,
    /**
     * Context associated with the thread owning this operation.
     */
    public var cx: Context,
) {
    /**
     * Optional packet.
     */
    public var packet: MutPtr = packetCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            oper: Operation,
            packet: MutPtr,
            cx: Context,
        ): Entry = Entry(
            oper = oper,
            packetCtorArg = packet,
            cx = cx,
        )
    }
}

/**
 * A queue of threads blocked on channel operations.
 *
 * This data structure is used by threads to register blocking operations and get woken up once
 * an operation becomes ready.
 */
public open class Waker(
    /**
     * A list of select operations.
     */
    public var selectors: MutableList<Entry>,
    /**
     * A list of operations waiting to be ready.
     */
    public var observers: MutableList<Entry>,
) {
    /**
     * Registers a select operation.
     */
    public open fun register(oper: Operation, cx: Context) {
        this.registerWithPacket(oper, MutPtr(0L), cx)
    }

    /**
     * Registers a select operation and a packet.
     */
    public open fun registerWithPacket(oper: Operation, packet: MutPtr, cx: Context) {
        this.selectors.add(Entry(oper = oper, packet = packet, cx = cx.clone()))
    }

    /**
     * Unregisters a select operation.
     */
    public open fun unregister(oper: Operation): Entry? =
        when (val matchVal = this.selectors.mapIndexed { index, item -> Pair(index, item) }.indexOf({ (_, entry) -> entry.oper == oper }).takeIf { it >= 0 }) {
            null -> {
                null
            }
            else -> {
                val i = matchVal.first
                val entry = this.selectors.remove(i)
                entry
            }
        }

    /**
     * Attempts to find another thread's entry, select the operation, and wake it up.
     */
    public open fun trySelect(): Entry? =
        if (this.selectors.isEmpty()) {
            null
        } else {
            val threadId = currentThreadId()
            (this.selectors.indexOfFirst({ selector ->
                // Does the entry belong to a different thread?
                selector.cx.threadId() != threadId && selector.cx.trySelect(Selected.Operation(selector.oper)).isOk() &&
                    run {
                        // Provide the packet.
                        selector.cx.storePacket(selector.packet)
                        // Wake the thread up.
                        selector.cx.unpark()
                        true
                    }
            }).takeIf { it >= 0 })?.let({ pos -> this.selectors.remove(pos) })
        }

    /**
     * Notifies all operations waiting to be ready.
     */
    public open fun notify() {
        for (entry in this.observers.drain(0 until 0)) {
            if (entry.cx.trySelect(Selected.Operation(entry.oper)).isOk()) {
                entry.cx.unpark()
            }
        }
    }

    /**
     * Notifies all registered operations that the channel is disconnected.
     */
    public open fun disconnect() {
        for (entry in ArrayDeque(this.selectors)) {
            if (entry.cx.trySelect(Selected.Disconnected).isOk()) {
                // Wake the thread up.
                //
                // Here we don't remove the entry from the queue. Registered threads must
                // unregister from the waker by themselves. They might also want to recover the
                // packet value and destroy it, if necessary.
                entry.cx.unpark()
            }
        }
        this.notify()
    }

    public open fun drop() {
        check(this.selectors.size == 0)
        check(this.observers.size == 0)
    }

    public companion object {
        /**
         * Creates a new `Waker`.
         */
        public fun new(): Waker = Waker(selectors = mutableListOf(), observers = mutableListOf())
    }
}

/**
 * A waker that can be shared among threads without locking.
 *
 * This is a simple wrapper around `Waker` that internally uses a mutex for synchronization.
 */
public open class SyncWaker internal constructor(
    innerCtorArg: Mutex<Waker>,
    isEmptyCtorArg: Atomic<Boolean, Align1<UByte>>,
) {
    /**
     * The inner `Waker`.
     */
    public var inner: Mutex<Waker> = innerCtorArg
        internal set

    /**
     * `true` if the waker is empty.
     */
    public var isEmpty: Atomic<Boolean, Align1<UByte>> = isEmptyCtorArg
        internal set

    /**
     * Registers the current thread with an operation.
     */
    public open fun register(oper: Operation, cx: Context) {
        val inner = this.inner.lock().unwrap()
        inner.register(oper, cx)
        this.isEmpty.store(inner.selectors.isEmpty() && inner.observers.isEmpty(), Ordering.SeqCst)
    }

    /**
     * Unregisters an operation previously registered by the current thread.
     */
    public open fun unregister(oper: Operation): Entry? {
        val inner = this.inner.lock().unwrap()
        val entry = inner.unregister(oper)
        this.isEmpty.store(inner.selectors.isEmpty() && inner.observers.isEmpty(), Ordering.SeqCst)
        return entry
    }

    /**
     * Attempts to find one thread (not the current one), select its operation, and wake it up.
     */
    public open fun notify() {
        if (!this.isEmpty.load(Ordering.SeqCst)) {
            val inner = this.inner.lock().unwrap()
            if (!this.isEmpty.load(Ordering.SeqCst)) {
                inner.trySelect()
                inner.notify()
                this.isEmpty.store(inner.selectors.isEmpty() && inner.observers.isEmpty(), Ordering.SeqCst)
            }
        }
    }

    /**
     * Notifies all threads that the channel is disconnected.
     */
    public open fun disconnect() {
        val inner = this.inner.lock().unwrap()
        inner.disconnect()
        this.isEmpty.store(inner.selectors.isEmpty() && inner.observers.isEmpty(), Ordering.SeqCst)
    }

    public open fun drop() {
        check(this.isEmpty.load(Ordering.SeqCst))
    }

    public companion object {
        public operator fun invoke(
            inner: Mutex<Waker>,
            isEmpty: Atomic<Boolean, Align1<UByte>>,
        ): SyncWaker = SyncWaker(
            innerCtorArg = inner,
            isEmptyCtorArg = isEmpty,
        )

        /**
         * Creates a new `SyncWaker`.
         */
        public fun new(): SyncWaker = SyncWaker(inner = Mutex.new(Waker.new()), isEmpty = AtomicBool.new(true))
    }
}

/**
 * Returns a unique id for the current thread.
 */
public fun currentThreadId(): Int {
    val DUMMY: LocalKey<UByte> = threadLocalInner(key, 0)
    return DUMMY.with({ x -> ((constPtrFromRustCast(x))).rawAddress.toInt() })
}
