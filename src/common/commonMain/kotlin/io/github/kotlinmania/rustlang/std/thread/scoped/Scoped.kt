// port-lint: source thread/scoped.rs
package io.github.kotlinmania.rustlang.std.thread.scoped

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.panic.resumeUnwind
import io.github.kotlinmania.rustlang.std.thread.Result
import io.github.kotlinmania.rustlang.std.thread.builder.Builder
import io.github.kotlinmania.rustlang.std.thread.current.currentOrUnnamed
import io.github.kotlinmania.rustlang.std.thread.lifecycle.JoinInner
import io.github.kotlinmania.rustlang.std.thread.lifecycle.spawnUnchecked
import io.github.kotlinmania.rustlang.std.thread.thread.Thread
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * A scope to spawn scoped threads in.
 *
 * See [scope] for details.
 */
public open class Scope(
    public var data: ScopeData,
    /**
     * Invariance over scope, to make sure scope cannot shrink,
     * which is necessary for soundness.
     *
     * Without invariance, this would compile fine but be unsound:
     *
     * ```compile_fail,E0373
     * std.thread.scope(|s| {
     *     s.spawn(|| {
     *         let a = String.from("abcd");
     *         s.spawn(|| println("{a:?}")); // might run after `a` is dropped
     *     });
     * });
     * ```
     */
    public var scope: Unit,
    public var env: Unit,
) {
    /**
     * Spawns a new thread within a scope, returning a [ScopedJoinHandle] for it.
     *
     * Unlike non-scoped threads, threads spawned with this function may
     * borrow non-`static` data from outside the scope. See [scope] for
     * details.
     *
     * The join handle provides a [join] method that can be used to join the spawned
     * thread. If the spawned thread panics, [join] will return an [Err] containing
     * the panic payload.
     *
     * If the join handle is dropped, the spawned thread will be implicitly joined at the
     * end of the scope. In that case, if the spawned thread panics, [scope] will
     * panic after all threads are joined.
     *
     * This function creates a thread with the default parameters of [Builder].
     * To specify the new thread's stack size or the name, use [Builder.spawnScoped].
     *
     * # Panics
     *
     * Panics if the OS fails to create a thread; use [Builder.spawnScoped]
     * to recover from such errors.
     *
     * [join]: ScopedJoinHandle.join
     */
    public open fun <T> spawn(f: () -> T): ScopedJoinHandle<T> = Builder.new().spawnScoped(this, f).expect("failed to spawn thread")

    public open fun fmt(f: StringBuilder): RustResult<Unit, Error> = run { DebugStruct(f, "Scope").field("num_running_threads", this.data.numRunningThreads.load(Ordering.Relaxed)).field("a_thread_panicked", this.data.aThreadPanicked.load(Ordering.Relaxed)).field("main_thread", this.data.mainThread).finishNonExhaustive(); RustResult.Ok<Unit, Error>(Unit) }
}

/**
 * An owned permission to join on a scoped thread (block on its termination).
 *
 * See [Scope.spawn] for details.
 */
public open class ScopedJoinHandle<T>(
    public var value: JoinInner<T>,
) {
    public var field0: JoinInner<T>
        get() = value
        set(v) {
            value = v
        }

    /**
     * Extracts a handle to the underlying thread.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * thread.scope({ s ->
     *     val t = s.spawn({
     *         println("hello")
     *     })
     *     println("thread id: ${t.thread().id()}")
     * })
     * ```
     */
    public open fun thread(): Thread = this.value.thread()

    /**
     * Waits for the associated thread to finish.
     *
     * This function will return immediately if the associated thread has already finished.
     * Otherwise, it fully waits for the thread to finish, including all destructors
     * for thread-local variables that might be running after the main function of the thread.
     *
     * In terms of [atomic memory orderings], the completion of the associated
     * thread synchronizes with this function returning.
     * In other words, all operations performed by that thread
     * [happen before](https://doc.rust-lang.org/nomicon/atomics.html#data-accesses)
     * all operations that happen after `join` returns.
     *
     * If the associated thread panics, [Err] is returned with the panic payload.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * thread.scope({ s ->
     *     val t = s.spawn({
     *         error("oh no")
     *     })
     *     check(t.join().isErr())
     * })
     * ```
     */
    public open fun join(): Result<T> = this.value.join()

    /**
     * Checks if the associated thread has finished running its main function.
     *
     * `isFinished` supports implementing a non-blocking join operation, by checking
     * `isFinished`, and calling `join` if it returns `true`. This function does not block. To
     * block while waiting on the thread to finish, use [`join`][Self.join].
     *
     * This might return `true` for a brief moment after the thread's main
     * function has returned, but before the thread itself has stopped running.
     * However, once this returns `true`, [`join`][Self.join] can be expected
     * to return quickly, without blocking for any significant amount of time.
     */
    public open fun isFinished(): Boolean = this.value.isFinished()

    public open fun fmt(f: StringBuilder): RustResult<Unit, Error> = run { DebugStruct(f, "ScopedJoinHandle").finishNonExhaustive(); RustResult.Ok<Unit, Error>(Unit) }
}

public open class ScopeData internal constructor(
    numRunningThreadsCtorArg: Atomic<Int, Align8<Int>>,
    aThreadPanickedCtorArg: Atomic<Boolean, Align1<UByte>>,
    public var mainThread: Thread,
) {
    public var numRunningThreads: Atomic<Int, Align8<Int>> = numRunningThreadsCtorArg
        internal set

    public var aThreadPanicked: Atomic<Boolean, Align1<UByte>> = aThreadPanickedCtorArg
        internal set

    public open fun incrementNumRunningThreads() {
        // We check for `overflow` with usize.Max / 2, to make sure there's no
        // chance it overflows to 0, which would result in unsoundness.
        if (this.numRunningThreads.fetchAdd(1, Ordering.Relaxed) > Int.MAX_VALUE / 2) {
            // This can only reasonably happen by mem.forget()'ing a lot of ScopedJoinHandles.
            this.overflow()
        }
    }

    public open fun overflow() {
        this.decrementNumRunningThreads(false)
        error("too many running threads in thread scope")
    }

    public open fun decrementNumRunningThreads(panic: Boolean) {
        if (panic) {
            this.aThreadPanicked.store(true, Ordering.Relaxed)
        }
        if (this.numRunningThreads.fetchSub(1, Ordering.Release) == 1) {
            this.mainThread.unpark()
        }
    }

    public companion object {
        public operator fun invoke(
            numRunningThreads: Atomic<Int, Align8<Int>>,
            aThreadPanicked: Atomic<Boolean, Align1<UByte>>,
            mainThread: Thread,
        ): ScopeData = ScopeData(
            numRunningThreadsCtorArg = numRunningThreads,
            aThreadPanickedCtorArg = aThreadPanicked,
            mainThread = mainThread,
        )
    }
}

/**
 * Creates a scope for spawning scoped threads.
 *
 * The function passed to `scope` will be provided a [Scope] object,
 * through which scoped threads can be [spawned][Scope.spawn].
 *
 * Unlike non-scoped threads, scoped threads can borrow non-`static` data,
 * as the scope guarantees all threads will be joined at the end of the scope.
 *
 * All threads spawned within the scope that haven't been manually joined
 * will be automatically joined before this function returns.
 * However, note that joining will only wait for the main function of these threads to finish; even
 * when this function returns, destructors of thread-local variables in these threads might still
 * be running.
 *
 * # Panics
 *
 * If any of the automatically joined threads panicked, this function will panic.
 *
 * If you want to handle panics from spawned threads,
 * [`join`][ScopedJoinHandle.join] them before the end of the scope.
 *
 * # Example
 *
 * ```kotlin
 * import std.thread
 *
 * var a = mutableListOf(1, 2, 3)
 * var x = 0
 * thread.scope({ s ->
 *     s.spawn({
 *         println("hello from the first scoped thread")
 *         // We can borrow `a` here.
 *         dbg(a)
 *     })
 *     s.spawn({
 *         println("hello from the second scoped thread")
 *         // We can even mutably borrow `x` here,
 *         // because no other threads are using it.
 *         x += a[0] + a[2]
 *     })
 *     println("hello from the main thread")
 * })
 * // After the scope, we can modify and access our variables again:
 * a.add(4)
 * check(x == a.size)
 * ```
 *
 * # Scopes
 *
 * Scoped threads involve two scopes: `scope` and `env`.
 *
 * The `scope` scope represents the scope of the scope itself.
 * That is: the time during which new scoped threads may be spawned,
 * and also the time during which they might still be running.
 * Once this scope ends, all scoped threads are joined.
 * This scope starts within the `scope` function, before `f` (the argument to `scope`) starts.
 * It ends after `f` returns and all scoped threads have been joined, but before `scope` returns.
 *
 * The `env` scope represents the scope of whatever is borrowed by the scoped threads.
 * This scope must outlast the call to `scope`, and thus cannot be smaller than `scope`.
 * It can be as small as the call to `scope`, meaning that anything that outlives this call,
 * such as local variables defined right before the scope, can be borrowed by the scoped threads.
 *
 * The `:` bound is part of the definition of the `Scope` type.
 */
public fun <T> scope(f: (Scope) -> T): T {
    // We put the `ScopeData` into an `Arc` so that other threads can finish their
    // `decrementNumRunningThreads` even after this function returns.
    val scope = Scope(data = ScopeData(numRunningThreads = AtomicUsize.new(0), mainThread = currentOrUnnamed(), aThreadPanicked = AtomicBool.new(false)), env = Unit, scope = Unit)
    // Run `f`, but catch panics so we can make sure to wait for all the threads to join.
    val result = catchUnwind(AssertUnwindSafe({ f(scope) }))
    // Wait until all the threads are finished.
    while (scope.data.numRunningThreads.load(Ordering.Acquire) != 0) {
        // SAFETY: this is the main thread, the handle belongs to us.
        scope.data.mainThread.park()
    }
    // Throw any panic from `f`, or the return value of `f` if no thread panicked.
    return when {
        result is RustResult.Err<*, *> -> {
            val e = result.error
            resumeUnwind(e)
        }
        result is RustResult.Ok<*, *> && scope.data.aThreadPanicked.load(Ordering.Relaxed) -> {
            error("a scoped thread panicked")
        }
        else -> {
            val result1 = (result as RustResult.Ok<*, *>).value
            result1
        }
    }
}

/**
 * Spawns a new scoped thread using the settings set through this `Builder`.
 *
 * Unlike [Scope.spawn], this method yields an [io.Result] to
 * capture any failure to create the thread at the OS level.
 *
 * # Panics
 *
 * Panics if a thread name was set and it contained null bytes.
 *
 * # Example
 *
 * ```kotlin
 * import std.thread
 *
 * var a = mutableListOf(1, 2, 3)
 * var x = 0
 * thread.scope({ s ->
 *     thread.Builder.new().name("first".toString()).spawnScoped(s, {
 *         println("hello from the ${thread.current().name()} scoped thread")
 *         // We can borrow `a` here.
 *         dbg(a)
 *     }).unwrap()
 *     thread.Builder.new().name("second".toString()).spawnScoped(s, {
 *         println("hello from the ${thread.current().name()} scoped thread")
 *         // We can even mutably borrow `x` here,
 *         // because no other threads are using it.
 *         x += a[0] + a[2]
 *     }).unwrap()
 *     println("hello from the main thread")
 * })
 * // After the scope, we can modify and access our variables again:
 * a.add(4)
 * check(x == a.size)
 * ```
 */
public fun <T> Builder.spawnScoped(scope: Scope, f: () -> T): RustResult<ScopedJoinHandle<T>, io.github.kotlinmania.rustlang.core.io.error.Error> {
    val name = this.name
    val stackSize = this.stackSize
    val noHooks = this.noHooks
    return RustResult.Ok<ScopedJoinHandle<T>, io.github.kotlinmania.rustlang.core.io.error.Error>(ScopedJoinHandle(when (val tryResult = spawnUnchecked(name, stackSize, noHooks, scope.data.clone(), f)) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<ScopedJoinHandle<T>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.error) }))
}
