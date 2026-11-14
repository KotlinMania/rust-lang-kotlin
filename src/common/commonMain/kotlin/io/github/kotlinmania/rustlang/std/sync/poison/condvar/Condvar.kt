// port-lint: source sync/poison/condvar.rs
package io.github.kotlinmania.rustlang.std.sync.poison.condvar

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.sync.WaitTimeoutResult
import io.github.kotlinmania.rustlang.std.sync.poison.LockResult
import io.github.kotlinmania.rustlang.std.sync.poison.PoisonError
import io.github.kotlinmania.rustlang.std.sync.poison.mapResult
import io.github.kotlinmania.rustlang.std.sync.poison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.poison.mutex.MutexGuard
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
 * Condition variables represent the ability to block a thread such that it
 * consumes no CPU time while waiting for an event to occur. Condition
 * variables are typically associated with a boolean predicate (a condition)
 * and a mutex. The predicate is always verified inside of the mutex before
 * determining that a thread must block.
 *
 * Functions in this module will block the current **thread** of execution.
 * Note that any attempt to use multiple mutexes on the same condition
 * variable may result in a runtime panic.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Condvar
 * import std.sync.Mutex
 *
 * val pair = Pair(Mutex.new(false), Condvar.new())
 * val pair2 = pair
 * // Inside of our lock, spawn a new thread, and then wait for it to start.
 * thread.spawn({
 *     val (lock, cvar) = pair2
 *     var started = lock.lock().unwrap()
 *     started = true
 *     // We notify the condvar that the value has changed.
 *     cvar.notifyOne()
 * })
 * // Wait for the thread to start up.
 * val (lock, cvar) = pair
 * var started = lock.lock().unwrap()
 * while (!started) {
 *     started = cvar.wait(started).unwrap()
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
     * # Errors
     *
     * This function will return an error if the mutex being waited on is
     * poisoned when this thread re-acquires the lock. For more information,
     * see information about [poisoning] on the [Mutex] type.
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
     * import std.sync.Condvar
     * import std.sync.Mutex
     *
     * val pair = Pair(Mutex.new(false), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var started = lock.lock().unwrap()
     *     started = true
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyOne()
     * })
     * // Wait for the thread to start up.
     * val (lock, cvar) = pair
     * var started = lock.lock().unwrap()
     * // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     * while (!started) {
     *     started = cvar.wait(started).unwrap()
     * }
     * ```
     */
    public open fun <T> wait(guard: MutexGuard<T>): LockResult<MutexGuard<T>> {
        val poisoned =
            run {
                val lock = Mutex.guardLock(guard)
                this.inner.wait(lock)
                Mutex.guardPoison(guard).get()
            }
        return if (poisoned) {
            Result.Err(PoisonError.new(guard))
        } else {
            Result.Ok(guard)
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
     * # Errors
     *
     * This function will return an error if the mutex being waited on is
     * poisoned when this thread re-acquires the lock. For more information,
     * see information about [poisoning] on the [Mutex] type.
     *
     * [wait]: Self.wait
     * [notifyOne]: Self.notifyOne
     * [notifyAll]: Self.notifyAll
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Condvar
     * import std.sync.Mutex
     *
     * val pair = Pair(Mutex.new(true), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var pending = lock.lock().unwrap()
     *     pending = false
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyOne()
     * })
     * // Wait for the thread to start up.
     * val (lock, cvar) = pair
     * // As long as the value inside the `Mutex<Boolean>` is `true`, we wait.
     * val guard = cvar.waitWhile(lock.lock().unwrap(), { pending ->
     *     pending
     * }).unwrap()
     * ```
     */
    public open fun <T> waitWhile(guard: MutexGuard<T>, condition: (T) -> Boolean): LockResult<MutexGuard<T>> {
        var guard1 = guard
        while (condition(guard1)) {
            guard1 = when (val tryResult = this.wait(guard1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        return Result.Ok(guard1)
    }

    /**
     * Waits on this condition variable for a notification, timing out after a
     * specified duration.
     *
     * The semantics of this function are equivalent to [wait]
     * except that the thread will be blocked for roughly no longer
     * than `ms` milliseconds. This method should not be used for
     * precise timing due to anomalies such as preemption or platform
     * differences that might not cause the maximum amount of time
     * waited to be precisely `ms`.
     *
     * Note that the best effort is made to ensure that the time waited is
     * measured with a monotonic clock, and not affected by the changes made to
     * the system time.
     *
     * The returned boolean is `false` only if the timeout is known
     * to have elapsed.
     *
     * Like [wait], the lock specified will be re-acquired when this function
     * returns, regardless of whether the timeout elapsed or not.
     *
     * [wait]: Self.wait
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Condvar
     * import std.sync.Mutex
     *
     * val pair = Pair(Mutex.new(false), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var started = lock.lock().unwrap()
     *     started = true
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyOne()
     * })
     * // Wait for the thread to start up.
     * val (lock, cvar) = pair
     * var started = lock.lock().unwrap()
     * // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     * while (true) {
     *     val result = cvar.waitTimeoutMs(started, 10).unwrap()
     *     // 10 milliseconds have passed, or maybe the value changed!
     *     started = result.first
     *     if (started == true) {
     *         // We received the notification and the value has been updated, we can leave.
     *         break
     *     }
     * }
     * ```
     */
    public open fun <T> waitTimeoutMs(guard: MutexGuard<T>, ms: UInt): LockResult<Pair<MutexGuard<T>, Boolean>> {
        val res = this.waitTimeout(guard, Duration.fromMillis(ms.toULong()))
        return mapResult(res, { (a, b) -> Pair(a, !b.timedOut()) })
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
     * returns to protect against spurious wakeups. Furthermore, since the timeout
     * is given relative to the moment this function is called, it needs to be adjusted
     * when this function is called in a loop. The [waitTimeoutWhile] method
     * lets you wait with a timeout while a predicate is true, taking care of all these concerns.
     *
     * The returned [WaitTimeoutResult] value indicates if the timeout is
     * known to have elapsed.
     *
     * Like [wait], the lock specified will be re-acquired when this function
     * returns, regardless of whether the timeout elapsed or not.
     *
     * [wait]: Self.wait
     * [waitTimeoutWhile]: Self.waitTimeoutWhile
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Condvar
     * import std.sync.Mutex
     * import std.time.Duration
     *
     * val pair = Pair(Mutex.new(false), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var started = lock.lock().unwrap()
     *     started = true
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyOne()
     * })
     * // wait for the thread to start up
     * val (lock, cvar) = pair
     * var started = lock.lock().unwrap()
     * // as long as the value inside the `Mutex<Boolean>` is `false`, we wait
     * while (true) {
     *     val result = cvar.waitTimeout(started, Duration.fromMillis(10)).unwrap()
     *     // 10 milliseconds have passed, or maybe the value changed!
     *     started = result.first
     *     if (started == true) {
     *         // We received the notification and the value has been updated, we can leave.
     *         break
     *     }
     * }
     * ```
     */
    public open fun <T> waitTimeout(guard: MutexGuard<T>, dur: Duration): LockResult<Pair<MutexGuard<T>, WaitTimeoutResult>> {
        val (poisoned, result) =
            run {
                val lock = Mutex.guardLock(guard)
                val success = this.inner.waitTimeout(lock, dur)
                Pair(Mutex.guardPoison(guard).get(), WaitTimeoutResult(!success))
            }
        return if (poisoned) {
            Result.Err(PoisonError.new(Pair(guard, result)))
        } else {
            Result.Ok(Pair(guard, result))
        }
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
     * Like [waitWhile], the lock specified will be re-acquired when this
     * function returns, regardless of whether the timeout elapsed or not.
     *
     * [waitWhile]: Self.waitWhile
     * [waitTimeout]: Self.waitTimeout
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Condvar
     * import std.sync.Mutex
     * import std.time.Duration
     *
     * val pair = Pair(Mutex.new(true), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var pending = lock.lock().unwrap()
     *     pending = false
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyOne()
     * })
     * // wait for the thread to start up
     * val (lock, cvar) = pair
     * val result = cvar.waitTimeoutWhile(lock.lock().unwrap(), Duration.fromMillis(100), { pending -> pending }).unwrap()
     * if (result.second.timedOut()) {
     *     // timed-out without the condition ever evaluating to false.
     * }
     * // access the locked mutex via result.0
     * ```
     */
    public open fun <T> waitTimeoutWhile(guard: MutexGuard<T>, dur: Duration, condition: (T) -> Boolean): LockResult<Pair<MutexGuard<T>, WaitTimeoutResult>> {
        var guard1 = guard
        val start = Instant.now()
        while (true) {
            if (!condition(guard1)) {
                return Result.Ok(Pair(guard1, WaitTimeoutResult(false)))
            }
            val timeout1 =
                when (val matchVal = dur.checkedSub(start.elapsed())) {
                    null -> { return Result.Ok(Pair(guard1, WaitTimeoutResult(true))) }
                    else -> {
                        val timeout = matchVal
                        timeout
                    }
                }
            guard1 = when (val tryResult = this.waitTimeout(guard1, timeout1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.first
        }
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
     * import std.sync.Condvar
     * import std.sync.Mutex
     *
     * val pair = Pair(Mutex.new(false), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var started = lock.lock().unwrap()
     *     started = true
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyOne()
     * })
     * // Wait for the thread to start up.
     * val (lock, cvar) = pair
     * var started = lock.lock().unwrap()
     * // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     * while (!started) {
     *     started = cvar.wait(started).unwrap()
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
     * import std.sync.Condvar
     * import std.sync.Mutex
     *
     * val pair = Pair(Mutex.new(false), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     var started = lock.lock().unwrap()
     *     started = true
     *     // We notify the condvar that the value has changed.
     *     cvar.notifyAll()
     * })
     * // Wait for the thread to start up.
     * val (lock, cvar) = pair
     * var started = lock.lock().unwrap()
     * // As long as the value inside the `Mutex<Boolean>` is `false`, we wait.
     * while (!started) {
     *     started = cvar.wait(started).unwrap()
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
        public fun new(): Condvar = Condvar(inner = io.github.kotlinmania.rustlang.std.sys.sync.condvar.futex.Condvar.new())

        /**
         * Creates a `Condvar` which is ready to be waited on and notified.
         */
        public fun default(): Condvar = Condvar.new()
    }
}
