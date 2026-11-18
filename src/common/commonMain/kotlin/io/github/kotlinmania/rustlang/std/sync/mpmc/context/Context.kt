// port-lint: source sync/mpmc/context.rs
package io.github.kotlinmania.rustlang.std.sync.mpmc.context

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.compareExchange
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.std.sync.mpmc.select.Selected
import io.github.kotlinmania.rustlang.std.sync.mpmc.waker.currentThreadId
import io.github.kotlinmania.rustlang.std.thread.current.currentOrUnnamed
import io.github.kotlinmania.rustlang.std.thread.thread.Thread
import io.github.kotlinmania.rustlang.std.time.Instant

// Thread-local channel context.

/**
 * Thread-local context.
 */
public open class Context(
    public var inner: Inner,
) {
    public open fun clone(): Context = Context(inner)

    /**
     * Resets `select` and `packet`.
     */
    public open fun reset() {
        this.inner.select.store(Selected.Waiting.into(), Ordering.Release)
        this.inner.packet.store(MutPtr(0L), Ordering.Release)
    }

    /**
     * Attempts to select an operation.
     *
     * On failure, the previously selected operation is returned.
     */
    public open fun trySelect(select: Selected): Result<Unit, Selected> = this.inner.select.compareExchange(Selected.Waiting.into(), select.into(), Ordering.AcqRel, Ordering.Acquire).map({ Unit }).mapErr({ e -> e.into() })

    /**
     * Stores a packet.
     *
     * This method must be called after `trySelect` succeeds and there is a packet to provide.
     */
    public open fun storePacket(packet: MutPtr) {
        if (!((packet).rawAddress == 0L)) {
            this.inner.packet.store(packet, Ordering.Release)
        }
    }

    /**
     * Waits until an operation is selected and returns it.
     *
     * If the deadline is reached, `Selected.Aborted` will be selected.
     *
     * # Safety
     * This may only be called from the thread this `Context` belongs to.
     */
    public open fun waitUntil(deadline: Instant?): Selected {
        while (true) {
            // Check whether an operation has been selected.
            val sel = Selected.from(this.inner.select.load(Ordering.Acquire))
            if (sel != Selected.Waiting) {
                return sel
            }
            // If there's a deadline, park the current thread until the deadline is reached.
            if (deadline != null) {
                val end = deadline
                val now = Instant.now()
                if (now < end) {
                    // SAFETY: guaranteed by caller.
                    this.inner.thread.parkTimeout(end - now)
                } else {
                    // The deadline has been reached. Try aborting select.
                    return run {
                        val matchVal = this.trySelect(Selected.Aborted)
                        when (matchVal) { is Result.Ok<*, *> -> {
                            Selected.Aborted
                        }; is Result.Err<*, *> -> {
                            val s = matchVal.errValue()
                            s
                        } }
                    }
                }
            } else {
                // SAFETY: guaranteed by caller.
                this.inner.thread.park()
            }
        }
    }

    /**
     * Unparks the thread this context belongs to.
     */
    public open fun unpark() {
        this.inner.thread.unpark()
    }

    /**
     * Returns the id of the thread this context belongs to.
     */
    public open fun threadId(): Int = this.inner.threadId

    public companion object {
        /**
         * Creates a new context for the duration of the closure.
         */
        public fun <R> with(f: (Context) -> R): R {
            threadLocalProcessAttrs(doc, " Cached thread-local context.", CONTEXT, Cell, Option, Context, Cell, new, Some, Context, new)
            var f1 = f
            var f2 =
                { cx: Context ->
                    val f2 = (f1.also { f1 = null })!!
                    f2(cx)
                }
            return CONTEXT.tryWith({ cell -> when (val matchVal = cell.take()) {
                null -> f2(Context.new())
                else -> {
                    val cx = matchVal
                    cx.reset()
                    val res = f2(cx)
                    cell.set(cx)
                    res
                }
            } }).unwrapOrElse({ f2(Context.new()) })
        }

        /**
         * Creates a new `Context`.
         */
        public fun new(): Context = Context(inner = Inner(select = AtomicUsize.new(Selected.Waiting.into()), packet = AtomicPtr.new(MutPtr(0L)), thread = currentOrUnnamed(), threadId = currentThreadId()))
    }
}

/**
 * Inner representation of `Context`.
 */
public open class Inner internal constructor(
    selectCtorArg: Atomic<Int, Align8<Int>>,
    packetCtorArg: Atomic<MutPtr, Align8<MutPtr>>,
    /**
     * Thread handle.
     */
    public var thread: Thread,
    /**
     * Thread id.
     */
    public var threadId: Int,
) {
    /**
     * Selected operation.
     */
    public var select: Atomic<Int, Align8<Int>> = selectCtorArg
        internal set

    /**
     * A slot into which another thread may store a pointer to its `Packet`.
     */
    public var packet: Atomic<MutPtr, Align8<MutPtr>> = packetCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            select: Atomic<Int, Align8<Int>>,
            packet: Atomic<MutPtr, Align8<MutPtr>>,
            thread: Thread,
            threadId: Int,
        ): Inner = Inner(
            selectCtorArg = select,
            packetCtorArg = packet,
            thread = thread,
            threadId = threadId,
        )
    }
}
