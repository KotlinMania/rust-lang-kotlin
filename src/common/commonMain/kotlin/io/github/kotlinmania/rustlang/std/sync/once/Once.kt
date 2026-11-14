// port-lint: source sync/once.rs
package io.github.kotlinmania.rustlang.std.sync.once

import io.github.kotlinmania.rustlang.std.fmt

// A "once initialization" primitive
//
// This primitive is meant to be used to run one-time initialization. An
// example use case would be for initializing an FFI library.

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
 * A low-level synchronization primitive for one-time global execution.
 *
 * Previously this was the only "execute once" synchronization in `std`.
 * Other libraries implemented novel synchronizing types with `Once`, like
 * [OnceLock.T] or [LazyLock.T.F], before those were added to `std`.
 * `OnceLock<T>` in particular supersedes `Once` in functionality and should
 * be preferred for the common case where the `Once` is associated with data.
 *
 * This type can only be constructed with [Once.new].
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Once
 *
 * var START: Once = Once.new()
 * START.callOnce({})
 * ```
 */
public open class Once(
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.once.futex.Once,
) {
    /**
     * Performs an initialization routine once and only once. The given closure
     * will be executed if this is the first time `callOnce` has been called,
     * and otherwise the routine will *not* be invoked.
     *
     * This method will block the calling thread if another initialization
     * routine is currently running.
     *
     * When this function returns, it is guaranteed that some initialization
     * has run and completed (it might not be the closure specified). It is also
     * guaranteed that any memory writes performed by the executed closure can
     * be reliably observed by other threads at this point (there is a
     * happens-before relation between the closure and code executing after the
     * return).
     *
     * If the given closure recursively invokes `callOnce` on the same [Once]
     * instance, the exact behavior is not specified: allowed outcomes are
     * a panic or a deadlock.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var VAL: Int = 0
     * var INIT: Once = Once.new()
     * fun expensiveComputation(): Int {
     *     // ...
     * }
     * // Accessing a `static` is unchecked much of the time, but if we do so
     * // in a synchronized fashion (e.g., write once or read all) then we're
     * // good to go!
     * //
     * // This function will only call `expensiveComputation` once, and will
     * // otherwise always return the value returned from the first invocation.
     * fun getCachedVal(): Int {
     *     return run {
     *         INIT.callOnce({
     *             VAL = expensiveComputation()
     *         })
     *         VAL
     *     }
     * }
     * ```
     *
     * # Panics
     *
     * The closure `f` will only be executed once even if this is called
     * concurrently amongst many threads. If that closure panics, however, then
     * it will *poison* this [Once] instance, causing all future invocations of
     * `callOnce` to also panic.
     *
     * This is similar to [poisoning with mutexes][poison], but this mechanism
     * is guaranteed to never skip panics within `f`.
     *
     * [poison]: struct.Mutex.html#poisoning
     */
    public open fun callOnce(f: () -> Unit) {
        // Fast path check
        if (this.inner.isCompleted()) {
            return
        }
        var f1 = f
        this.inner.call(false, { (f1.also { f1 = null })!!() })
    }

    /**
     * Performs the same function as [callOnce] except ignores poisoning.
     *
     * Unlike [callOnce], if this [Once] has been poisoned (i.e., a previous
     * call to [callOnce] or [callOnceForce] caused a panic), calling
     * [callOnceForce] will still invoke the closure `f` and will _not_
     * result in an immediate panic. If `f` panics, the [Once] will remain
     * in a poison state. If `f` does _not_ panic, the [Once] will no
     * longer be in a poison state and all future calls to [callOnce] or
     * [callOnceForce] will be no-ops.
     *
     * The closure `f` is yielded a [OnceState] structure which can be used
     * to query the poison status of the [Once].
     *
     * [callOnce]: Once.callOnce
     * [callOnceForce]: Once.callOnceForce
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var INIT: Once = Once.new()
     * // poison the once
     * val handle = thread.spawn({
     *     INIT.callOnce({ error("panic") })
     * })
     * check(handle.join().isErr())
     * // poisoning propagates
     * val handle1 = thread.spawn({
     *     INIT.callOnce({})
     * })
     * check(handle1.join().isErr())
     * // callOnceForce will still run and reset the poisoned state
     * INIT.callOnceForce({ state ->
     *     check(state.isPoisoned())
     * })
     * // once any success happens, we stop propagating the poison
     * INIT.callOnce({})
     * ```
     */
    public open fun callOnceForce(f: (OnceState) -> Unit) {
        // Fast path check
        if (this.inner.isCompleted()) {
            return
        }
        var f1 = f
        this.inner.call(true, { p -> (f1.also { f1 = null })!!(p) })
    }

    /**
     * Returns `true` if some [callOnce] call has completed
     * successfully. Specifically, `isCompleted` will return false in
     * the following situations:
     *   * [callOnce] was not called at all,
     *   * [callOnce] was called, but has not yet completed,
     *   * the [Once] instance is poisoned
     *
     * This function returning `false` does not mean that [Once] has not been
     * executed. For example, it may have been executed in the time between
     * when `isCompleted` starts executing and when it returns, in which case
     * the `false` return value would be stale (but still permissible).
     *
     * [callOnce]: Once.callOnce
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var INIT: Once = Once.new()
     * check(INIT.isCompleted() == false)
     * INIT.callOnce({
     *     check(INIT.isCompleted() == false)
     * })
     * check(INIT.isCompleted() == true)
     * ```
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var INIT: Once = Once.new()
     * check(INIT.isCompleted() == false)
     * val handle = thread.spawn({
     *     INIT.callOnce({ error("panic") })
     * })
     * check(handle.join().isErr())
     * check(INIT.isCompleted() == false)
     * ```
     */
    public open fun isCompleted(): Boolean = this.inner.isCompleted()

    /**
     * Blocks the current thread until initialization has completed.
     *
     * # Example
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var READY: Once = Once.new()
     * val thread = thread.spawn({
     *     READY.wait()
     *     println("everything is ready")
     * })
     * READY.callOnce({ println("performing setup") })
     * ```
     *
     * # Panics
     *
     * If this [Once] has been poisoned because an initialization closure has
     * panicked, this method will also panic. Use [`waitForce`](Self.waitForce)
     * if this behavior is not desired.
     */
    public open fun wait() {
        if (!this.inner.isCompleted()) {
            this.inner.wait(false)
        }
    }

    /**
     * Blocks the current thread until initialization has completed, ignoring
     * poisoning.
     *
     * If this [Once] has been poisoned, this function blocks until it
     * becomes completed, unlike [Once.wait], which panics in this case.
     */
    public open fun waitForce() {
        if (!this.inner.isCompleted()) {
            this.inner.wait(true)
        }
    }

    /**
     * Returns the current state of the `Once` instance.
     *
     * Since this takes a mutable reference, no initialization can currently
     * be running, so the state must be either "incomplete", "poisoned" or
     * "complete".
     */
    internal open fun state(): OnceExclusiveState = this.inner.state()

    /**
     * Sets current state of the `Once` instance.
     *
     * Since this takes a mutable reference, no initialization can currently
     * be running, so the state must be either "incomplete", "poisoned" or
     * "complete".
     */
    internal open fun setState(newState: OnceExclusiveState) {
        this.inner.setState(newState)
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "Once").finishNonExhaustive()
    }

    public companion object {
        /**
         * Creates a new `Once` value.
         */
        public fun new(): Once = Once(inner = io.github.kotlinmania.rustlang.std.sys.sync.once.futex.Once.new())

        /**
         * Creates a new `Once` value that starts already completed.
         */
        internal fun newComplete(): Once = Once(inner = io.github.kotlinmania.rustlang.std.sys.sync.once.futex.Once.newComplete())

        /**
         * Creates a new `Once` value, same as [Once.new].
         */
        public fun default(): Once = Once.new()
    }
}

/**
 * State yielded to [Once.callOnceForce]’s closure parameter. The state
 * can be used to query the poison status of the [Once].
 */
public open class OnceState(
    internal var inner: io.github.kotlinmania.rustlang.std.sys.sync.once.futex.OnceState,
) {
    /**
     * Returns `true` if the associated [Once] was poisoned prior to the
     * invocation of the closure passed to [Once.callOnceForce].
     *
     * # Examples
     *
     * A poisoned [Once]:
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var INIT: Once = Once.new()
     * // poison the once
     * val handle = thread.spawn({
     *     INIT.callOnce({ error("panic") })
     * })
     * check(handle.join().isErr())
     * INIT.callOnceForce({ state ->
     *     check(state.isPoisoned())
     * })
     * ```
     *
     * An unpoisoned [Once]:
     *
     * ```kotlin
     * import std.sync.Once
     *
     * var INIT: Once = Once.new()
     * INIT.callOnceForce({ state ->
     *     check(!state.isPoisoned())
     * })
     * ```
     */
    public open fun isPoisoned(): Boolean = this.inner.isPoisoned()

    // NOTE: This is currently only exposed for `OnceLock`.

    /**
     * Poison the associated [Once] without explicitly panicking.
     */
    internal open fun poison() {
        this.inner.poison()
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "OnceState").field("poisoned", this.isPoisoned()).finish()
    }
}

/**
 * Used for the internal implementation of `sys.sync.once` on different platforms and the
 * [`LazyLock`](sync.LazyLock) implementation.
 */
internal sealed class OnceExclusiveState {
    public data object Incomplete : OnceExclusiveState()
    public data object Poisoned : OnceExclusiveState()
    public data object Complete : OnceExclusiveState()
}

/**
 * Initialization value for static [Once] values.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.ONCE_INIT
 * import std.sync.Once
 *
 * var START: Once = ONCE_INIT
 * ```
 */
public val ONCE_INIT: Once = Once.new()
