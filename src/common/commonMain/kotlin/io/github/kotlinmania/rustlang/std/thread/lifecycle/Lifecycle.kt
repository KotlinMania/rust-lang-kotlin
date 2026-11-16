// port-lint: source thread/lifecycle.rs
package io.github.kotlinmania.rustlang.std.thread.lifecycle

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybedangling.MaybeDangling
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.panic.AssertUnwindSafe
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.backtrace.rustBeginShortBacktrace
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.thread.unix.DEFAULT_MIN_STACK_SIZE
import io.github.kotlinmania.rustlang.std.sys.thread.unix.setName
import io.github.kotlinmania.rustlang.std.thread.Result
import io.github.kotlinmania.rustlang.std.thread.current.setCurrent
import io.github.kotlinmania.rustlang.std.thread.id.ThreadId
import io.github.kotlinmania.rustlang.std.thread.scoped.ScopeData
import io.github.kotlinmania.rustlang.std.thread.spawnhook.ChildSpawnHooks
import io.github.kotlinmania.rustlang.std.thread.spawnhook.runSpawnHooks
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

// The inner logic for thread spawning and joining.

// even without panics, this helps for Miri backtraces
public fun <T> spawnUnchecked(name: String?, stackSize: Int?, noHooks: Boolean, scopeData: ScopeData?, f: () -> T): Result<JoinInner<T>, Error> {
    val stackSize1 =
        (stackSize ?: closure1@{
            var MIN: Atomic<Int, Align8<Int>> = AtomicUsize.new(0)
            when (val matchVal = MIN.load(Ordering.Relaxed)) {
                0 -> {}
                else -> {
                    val n = matchVal
                    return@closure1 n - 1
                }
            }
            val amt = Env.varOs("RUST_MIN_STACK").andThen({ s -> s.toStr().andThen({ s1 -> s1.parse().ok() }) }).unwrapOr(DEFAULT_MIN_STACK_SIZE)
            MIN.store(amt + 1, Ordering.Relaxed)
            amt
        }())
    val id = ThreadId.new()
    val thread = Thread.new(id, name)
    val hooks =
        if (noHooks) {
            ChildSpawnHooks.default()
        } else {
            runSpawnHooks(thread)
        }
    val myPacket: Packet<T> = Packet(scope = scopeData, result = UnsafeCell.new(null), marker = Unit)
    val theirPacket = myPacket.clone()
    // Pass `f` in `MaybeDangling` because actually that closure might *run longer than the scope of `F`*.
    // See <https://github.com/rust-lang/rust/issues/101983> for more details.
    val f1 = MaybeDangling.new(f)
    // The entrypoint of the Rust thread, after platform-specific thread
    // initialization is done.
    val rustStart =
        {
            val f2 = f1.intoInner()
            val tryResult =
                catchUnwind(AssertUnwindSafe({
                    rustBeginShortBacktrace({ hooks.inheritAndRun() })
                    rustBeginShortBacktrace(f2)
                }))
            // SAFETY: `theirPacket` as been built just above and moved by the
            // closure (it is an Arc<...>) and `myPacket` will be stored in the
            // same `JoinInner` as this closure meaning the mutation will be
            // safe (not modify it and affect a value far away).
            theirPacket.result.get() = tryResult
            // Here `theirPacket` gets dropped, and if this is the last `Arc` for that packet that
            // will call `decrementNumRunningThreads` and therefore signal that this thread is
            // done.
            (theirPacket).let { }
        }
    when (val matchVal = myPacket.scope) {
        null -> {}
        else -> {
            val scopeData1 = matchVal
            scopeData1.incrementNumRunningThreads()
        }
    }
    // SAFETY: dynamic size and alignment of the Holder remain the same. See below for why the
    // scope change is justified.
    val rustStart1 =
        run {
            val ptr = Box.intoRaw(rustStart)
            val ptr1 = transmuteWithLayouts(ptr, PrimitiveLayouts.mutPtrLayout(64L), PrimitiveLayouts.mutPtrLayout(64L))
            Box.fromRaw(ptr1)
        }
    val init = ThreadInit(handle = thread.clone(), rustStart = rustStart1)
    return Result.Ok<JoinInner<T>, Error>(JoinInner(native = when (val tryResult = io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread.new(stackSize1, init)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<JoinInner<T>, Error>(tryResult.error) }, thread = thread, packet = myPacket))
}

/**
 * The data passed to the spawned thread for thread initialization. Any thread
 * implementation should start a new thread by calling .init() on this before
 * doing anything else to ensure the current thread is properly initialized and
 * the global allocator works.
 */
public open class ThreadInit(
    public var handle: Thread,
    public var rustStart: () -> Unit,
) {
    /**
     * Initialize the current thread' mechanism on this thread, returning the
     * Rust entry point.
     */
    public open fun init(): () -> Unit {
        // Set the current thread before any (de)allocations on the global allocator occur,
        // so that it may call std.thread.current() in its implementation. This is also
        // why we take Holder<Self>, to ensure the Holder is not destroyed until after this point.
        // Cloning the handle does not invoke the global allocator, it is an Arc.
        when (val matchVal = setCurrent(this.handle.clone())) {
            is Err -> {
                run {
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal1
                            Write.writeFmt(out, "fatal runtime error: " + ("current thread handle already set during thread spawn").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("current thread handle already set during thread spawn").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
            else -> {}
        }
        // The handle was created by the spawning thread, so only now that we are
        // running can the OS id be filled in.
        this.handle.setOsIdToCurrent()
        when (val matchVal2 = this.handle.cname()) {
            null -> {}
            else -> {
                val name = matchVal2
                setName(name)
            }
        }
        return this.rustStart
    }
}

// This packet is used to communicate the return value between the spawned
// thread and the rest of the program. It is shared through an `Arc` and
// there's no need for a mutex here because synchronization happens with `join()`
// (the caller will never read this packet until the thread has exited).
//
// An Arc to the packet is stored into a `JoinInner` which in turns is placed
// in `JoinHandle`.
public open class Packet<T> internal constructor(
    public var scope: ScopeData?,
    resultCtorArg: UnsafeCell<Result<T>?>,
    public var marker: Unit,
) {
    public var result: UnsafeCell<Result<T>?> = resultCtorArg
        internal set

    // Due to the usage of `UnsafeCell` we need to manually implement Sync.
    // The type `T` should already always be Send (otherwise the thread could not
    // have been created) and the Packet is Sync because all access to the
    // `UnsafeCell` synchronized (by the `join()` boundary), and `ScopeData` is Sync.

    public open fun drop() {
        // If this packet was for a thread that ran in a scope, the thread
        // panicked, and nobody consumed the panic payload, we make sure
        // the scope function will panic.
        val unhandledPanic =
            when (val matchVal = this.result.getMut()) {
                is Result.Err -> true
                else -> false
            }
        // Drop the result without causing unwinding.
        // This is only relevant for threads that aren't join()ed, as
        // join() will take the `result` and set it to None, such that
        // there is nothing left to drop here.
        // If this panics, we should handle that, because we're outside the
        // outermost `catchUnwind` of our thread.
        // We just abort in that case, since there's nothing else we can do.
        // (And even if we tried to handle it somehow, we'd also need to handle
        // the case where the panic payload we get out of it also panics on
        // drop, and so on. See issue #86027.)
        when (val matchVal1 = catchUnwind(AssertUnwindSafe({
            this.result.getMut() = null
        }))) {
            is Err -> {
                run {
                    when (val matchVal2 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal2
                            Write.writeFmt(out, "fatal runtime error: " + ("thread result panicked on drop").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("thread result panicked on drop").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
            else -> {}
        }
        // Book-keeping so the scope knows when it's done.
        when (val matchVal3 = this.scope) {
            null -> {}
            else -> {
                val scope = matchVal3
                // Now that there will be no more user code running on this thread
                // that can use scope, mark the thread as 'finished'.
                // It's important we only do this after the `result` has been dropped,
                // since dropping it might still use things it borrowed from scope.
                scope.decrementNumRunningThreads(unhandledPanic)
            }
        }
    }

    public companion object {
        public operator fun <T> invoke(
            scope: ScopeData?,
            result: UnsafeCell<Result<T>?>,
            marker: Unit,
        ): Packet<T> = Packet<T>(
            scope = scope,
            resultCtorArg = result,
            marker = marker,
        )
    }
}

/**
 * Inner representation for JoinHandle
 */
public open class JoinInner<T>(
    public var native: io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread,
    public var thread: Thread,
    public var packet: Packet<T>,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread>, IntoInner<io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread> {
    public open fun isFinished(): Boolean = (Arc.strongCount(this.packet) == 1).toUByte()

    public open fun thread(): Thread = this.thread

    public open fun join(): Result<T> {
        this.native.join()
        return Arc.getMut(this.packet).expect("threads should not terminate unexpectedly").result.getMut().take()!!
    }

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread = this.native

    public override fun intoInner(): io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread = this.native
}
