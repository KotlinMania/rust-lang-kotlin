// port-lint: source sync/nonpoison/condvar.rs
package io.github.kotlinmania.rustlang.std.sync.nonpoison.condvar

import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.WaitTimeoutResult
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.MutexGuard
import io.github.kotlinmania.rustlang.std.time.Instant

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
 * A Condition Variable
 *
 * For more information about condition variables, check out the documentation for the poisoning
 * variant of this type at [poison.Condvar].
 *
 * # Examples
 *
 * Note that this `Condvar` does **not** propagate information about threads that panic while
 * holding a lock. If you need this functionality, see [poison.Mutex] and [poison.Condvar].
 *
 * ```kotlin
 * import std.sync.nonpoison.Condvar
 * import std.sync.nonpoison.Mutex
 * import std.thread
 *
 * @feature(nonpoisonMutex)
 * @feature(nonpoisonCondvar)
 * public fun doctestEntry() {
 *     val pair = Pair(Mutex.new(false), Condvar.new())
 *     val pair2 = pair
 *     // Inside of our lock, spawn a new thread, and then wait for it to start.
 *     thread.spawn({
 *         val (lock, cvar) = pair2
 *         var started = lock.lock()
 *         started = true
 *         // We notify the condvar that the value has changed.
 *         cvar.notifyOne()
 *     })
 *     // Wait for the thread to start up.
 *     val (lock, cvar) = pair
 *     var started = lock.lock()
 *     while (!started) {
 *         cvar.wait(started)
 *     }
 * }
 * ```
 */
public open class Condvar(
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.condvar.futex.Condvar,
) {
    /**
     * Blocks the current thread until this condition variable receives a
     * notification.
     *
     * This function will atomically unlock the mutex specified (represented by
     * `guard`) and block the current thread. This means that any calls
     * to [notifyOne] or [notifyAll] which happen logically after the
     * mutex is unlocked are candidates to wake this thread up. When this
     * function call returns, the lock specified will have been re-acquired.
     *
     * Note that this function is susceptible to spurious wakeups. Condition
     * variables normally have a boolean predicate associated with them, and
     * the predicate must always be checked each time this function returns to
     * protect against spurious wakeups.
     *
     * # Panics
     *
     * This function may [panic] if it is used with more than one mutex
     * over time.
     *
     * [notifyOne]: Self.notifyOne
     * [notifyAll]: Self.notifyAll
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Condvar
     * import std.sync.nonpoison.Mutex
     * import std.thread
     *
     * @feature(nonpoisonMutex)
     * @feature(nonpoisonCondvar)
     * public fun doctestEntry() {
     *     val pair = Pair(Mutex.new(false), Condvar.new())
     *     val pair2 = pair
     *     thread.spawn({
     *         val (lock, cvar) = pair2
     *         var started = lock.lock()
     *         started = true
     *         // We notify the condvar that the value has changed.
     *         cvar.notifyOne()
     *     })
     *     // Wait for the thread to start up.
     *     val (lock, cvar) = pair
     *     var started = lock.lock()
     *     // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     *     while (!started) {
     *         cvar.wait(started)
     *     }
     * }
     * ```
     */
    public open fun <T> wait(guard: MutexGuard<T>) {
        run {
            val lock = Mutex.guardLock(guard)
            this.inner.wait(lock)
        }
    }

    /**
     * Blocks the current thread until the provided condition becomes false.
     *
     * `condition` is checked immediately; if not met (returns `true`), this
     * will [wait] for the next notification then check again. This repeats
     * until `condition` returns `false`, in which case this function returns.
     *
     * This function will atomically unlock the mutex specified (represented by
     * `guard`) and block the current thread. This means that any calls
     * to [notifyOne] or [notifyAll] which happen logically after the
     * mutex is unlocked are candidates to wake this thread up. When this
     * function call returns, the lock specified will have been re-acquired.
     *
     * [wait]: Self.wait
     * [notifyOne]: Self.notifyOne
     * [notifyAll]: Self.notifyAll
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Condvar
     * import std.sync.nonpoison.Mutex
     * import std.thread
     *
     * @feature(nonpoisonMutex)
     * @feature(nonpoisonCondvar)
     * public fun doctestEntry() {
     *     val pair = Pair(Mutex.new(true), Condvar.new())
     *     val pair2 = pair
     *     thread.spawn({
     *         val (lock, cvar) = pair2
     *         var pending = lock.lock()
     *         pending = false
     *         // We notify the condvar that the value has changed.
     *         cvar.notifyOne()
     *     })
     *     // Wait for the thread to start up.
     *     val (lock, cvar) = pair
     *     // As long as the value inside the `Mutex<Boolean>` is `true`, we wait.
     *     var guard = lock.lock()
     *     cvar.waitWhile(guard, { pending ->
     *         pending
     *     })
     * }
     * ```
     */
    public open fun <T> waitWhile(guard: MutexGuard<T>, condition: (T) -> Boolean) {
        while (condition(guard.derefMut())) {
            this.wait(guard)
        }
    }

    /**
     * Waits on this condition variable for a notification, timing out after a
     * specified duration.
     *
     * The semantics of this function are equivalent to [wait] except that
     * the thread will be blocked for roughly no longer than `dur`. This
     * method should not be used for precise timing due to anomalies such as
     * preemption or platform differences that might not cause the maximum
     * amount of time waited to be precisely `dur`.
     *
     * Note that the best effort is made to ensure that the time waited is
     * measured with a monotonic clock, and not affected by the changes made to
     * the system time. This function is susceptible to spurious wakeups.
     * Condition variables normally have a boolean predicate associated with
     * them, and the predicate must always be checked each time this function
     * returns to protect against spurious wakeups.  Furthermore, since the timeout
     * is given relative to the moment this function is called, it needs to be adjusted
     * when this function is called in a loop. The [waitTimeoutWhile] method
     * lets you wait with a timeout while a predicate is true, taking care of all these concerns.
     *
     * The returned [WaitTimeoutResult] value indicates if the timeout is
     * known to have elapsed.
     *
     * Like [wait], the lock specified will have been re-acquired when this function
     * returns, regardless of whether the timeout elapsed or not.
     *
     * [wait]: Self.wait
     * [waitTimeoutWhile]: Self.waitTimeoutWhile
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Condvar
     * import std.sync.nonpoison.Mutex
     * import std.thread
     * import std.time.Duration
     *
     * @feature(nonpoisonMutex)
     * @feature(nonpoisonCondvar)
     * public fun doctestEntry() {
     *     val pair = Pair(Mutex.new(false), Condvar.new())
     *     val pair2 = pair
     *     thread.spawn({
     *         val (lock, cvar) = pair2
     *         var started = lock.lock()
     *         started = true
     *         // We notify the condvar that the value has changed.
     *         cvar.notifyOne()
     *     })
     *     // wait for the thread to start up
     *     val (lock, cvar) = pair
     *     var started = lock.lock()
     *     // as long as the value inside the `Mutex<Boolean>` is `false`, we wait
     *     while (true) {
     *         val result = cvar.waitTimeout(started, Duration.fromMillis(10))
     *         // 10 milliseconds have passed, or maybe the value changed!
     *         if (started == true) {
     *             // We received the notification and the value has been updated, we can leave.
     *             break
     *         }
     *     }
     * }
     * ```
     */
    public open fun <T> waitTimeout(guard: MutexGuard<T>, dur: Duration): WaitTimeoutResult {
        val success =
            run {
                val lock = Mutex.guardLock(guard)
                this.inner.waitTimeout(lock, dur)
            }
        return WaitTimeoutResult(!success)
    }

    /**
     * Waits on this condition variable for a notification, timing out after a
     * specified duration.
     *
     * The semantics of this function are equivalent to [waitWhile] except
     * that the thread will be blocked for roughly no longer than `dur`. This
     * method should not be used for precise timing due to anomalies such as
     * preemption or platform differences that might not cause the maximum
     * amount of time waited to be precisely `dur`.
     *
     * Note that the best effort is made to ensure that the time waited is
     * measured with a monotonic clock, and not affected by the changes made to
     * the system time.
     *
     * The returned [WaitTimeoutResult] value indicates if the timeout is
     * known to have elapsed without the condition being met.
     *
     * Like [waitWhile], the lock specified will have been re-acquired when this
     * function returns, regardless of whether the timeout elapsed or not.
     *
     * [waitWhile]: Self.waitWhile
     * [waitTimeout]: Self.waitTimeout
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Condvar
     * import std.sync.nonpoison.Mutex
     * import std.thread
     * import std.time.Duration
     *
     * @feature(nonpoisonMutex)
     * @feature(nonpoisonCondvar)
     * public fun doctestEntry() {
     *     val pair = Pair(Mutex.new(true), Condvar.new())
     *     val pair2 = pair
     *     thread.spawn({
     *         val (lock, cvar) = pair2
     *         var pending = lock.lock()
     *         pending = false
     *         // We notify the condvar that the value has changed.
     *         cvar.notifyOne()
     *     })
     *     // wait for the thread to start up
     *     val (lock, cvar) = pair
     *     var guard = lock.lock()
     *     val result = cvar.waitTimeoutWhile(guard, Duration.fromMillis(100), { pending -> pending })
     *     if (result.timedOut()) {
     *         // timed-out without the condition ever evaluating to false.
     *     }
     *     // access the locked mutex via guard
     * }
     * ```
     */
    public open fun <T> waitTimeoutWhile(guard: MutexGuard<T>, dur: Duration, condition: (T) -> Boolean): WaitTimeoutResult {
        val start = Instant.now()
        while (condition(guard.derefMut())) {
            val timeout1 =
                when (val matchVal = dur.checkedSub(start.elapsed())) {
                    null -> { return WaitTimeoutResult(true) }
                    else -> {
                        val timeout = matchVal
                        timeout
                    }
                }
            this.waitTimeout(guard, timeout1)
        }
        return WaitTimeoutResult(false)
    }

    /**
     * Wakes up one blocked thread on this condvar.
     *
     * If there is a blocked thread on this condition variable, then it will
     * be woken up from its call to [wait] or [waitTimeout]. Calls to
     * `notifyOne` are not buffered in any way.
     *
     * To wake up all threads, see [notifyAll].
     *
     * [wait]: Self.wait
     * [waitTimeout]: Self.waitTimeout
     * [notifyAll]: Self.notifyAll
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Condvar
     * import std.sync.nonpoison.Mutex
     * import std.thread
     *
     * @feature(nonpoisonMutex)
     * @feature(nonpoisonCondvar)
     * public fun doctestEntry() {
     *     val pair = Pair(Mutex.new(false), Condvar.new())
     *     val pair2 = pair
     *     thread.spawn({
     *         val (lock, cvar) = pair2
     *         var started = lock.lock()
     *         started = true
     *         // We notify the condvar that the value has changed.
     *         cvar.notifyOne()
     *     })
     *     // Wait for the thread to start up.
     *     val (lock, cvar) = pair
     *     var started = lock.lock()
     *     // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     *     while (!started) {
     *         cvar.wait(started)
     *     }
     * }
     * ```
     */
    public open fun notifyOne() {
        this.inner.notifyOne()
    }

    /**
     * Wakes up all blocked threads on this condvar.
     *
     * This method will ensure that any current waiters on the condition
     * variable are awoken. Calls to `notifyAll()` are not buffered in any
     * way.
     *
     * To wake up only one thread, see [notifyOne].
     *
     * [notifyOne]: Self.notifyOne
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Condvar
     * import std.sync.nonpoison.Mutex
     * import std.thread
     *
     * @feature(nonpoisonMutex)
     * @feature(nonpoisonCondvar)
     * public fun doctestEntry() {
     *     val pair = Pair(Mutex.new(false), Condvar.new())
     *     val pair2 = pair
     *     thread.spawn({
     *         val (lock, cvar) = pair2
     *         var started = lock.lock()
     *         started = true
     *         // We notify the condvar that the value has changed.
     *         cvar.notifyAll()
     *     })
     *     // Wait for the thread to start up.
     *     val (lock, cvar) = pair
     *     var started = lock.lock()
     *     // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     *     while (!started) {
     *         cvar.wait(started)
     *     }
     * }
     * ```
     */
    public open fun notifyAll() {
        this.inner.notifyAll()
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "Condvar").finishNonExhaustive()
    }

    public companion object {
        /**
         * Creates a new condition variable which is ready to be waited on and
         * notified.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.Condvar
         *
         * val condvar = Condvar.new()
         * ```
         */
        public fun new(): Condvar = Condvar(inner = io.github.kotlinmania.rustlang.std.sys.sync.condvar.new())

        /**
         * Creates a `Condvar` which is ready to be waited on and notified.
         */
        public fun default(): Condvar = Condvar.new()
    }
}
