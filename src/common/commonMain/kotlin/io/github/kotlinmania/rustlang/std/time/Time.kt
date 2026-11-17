// port-lint: source time.rs
package io.github.kotlinmania.rustlang.std.time

import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.AddAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Sub
import io.github.kotlinmania.rustlang.core.ops.arith.SubAssign
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.time.unix.UNIX_EPOCH

// Temporal quantification.
//
// # Examples
//
// There are multiple ways to create a new [Duration]:
//
// ```kotlin
// val fiveSeconds = Duration.fromSecs(5)
// check(fiveSeconds == Duration.fromMillis(5000))
// check(fiveSeconds == Duration.fromMicros(5000000))
// check(fiveSeconds == Duration.fromNanos(5000000000))
// val tenSeconds = Duration.fromSecs(10)
// val sevenNanos = Duration.fromNanos(7)
// val total = tenSeconds + sevenNanos
// check(total == Duration.new(10, 7))
// ```
//
// Using [Instant] to calculate how long a function took to run:
//
// ```ignore (incomplete)
// let now = Instant.now();
//
// // Calling a slow function, it may take a while
// slowFunction();
//
// let elapsedTime = now.elapsed();
// println("Running slowFunction() took {} seconds.", elapsedTime.asSecs());
// ```

/**
 * A measurement of a monotonically nondecreasing clock.
 * Opaque and useful only with [Duration].
 *
 * Instants are always guaranteed, barring [platform bugs], to be no less than any previously
 * measured instant when created, and are often useful for tasks such as measuring
 * benchmarks or timing how long an operation takes.
 *
 * Note, however, that instants are **not** guaranteed to be **steady**. In other
 * words, each tick of the underlying clock might not be the same length (e.g.
 * some seconds may be longer than others). An instant may jump forwards or
 * experience time dilation (slow down or speed up), but it will never go
 * backwards.
 * As part of this non-guarantee it is also not specified whether system suspends count as
 * elapsed time or not. The behavior varies across platforms and Rust versions.
 *
 * Instants are opaque types that can only be compared to one another. There is
 * no method to get "the number of seconds" from an instant. Instead, it only
 * allows measuring the duration between two instants (or comparing two
 * instants).
 *
 * The size of an `Instant` struct may vary depending on the target operating
 * system.
 *
 * Example:
 *
 * ```kotlin
 * import std.thread.sleep
 * import std.time.Duration
 * import std.time.Instant
 *
 * fun main() {
 *     val now = Instant.now()
 *     // we sleep for 2 seconds
 *     sleep(Duration.new(2, 0))
 *     // it prints '2'
 *     println("${now.elapsed().asSecs()}")
 * }
 * ```
 *
 * [platform bugs]: Instant#monotonicity
 *
 * # OS-specific behaviors
 *
 * An `Instant` is a wrapper around system-specific types and it may behave
 * differently depending on the underlying operating system. For example,
 * the following snippet is fine on Linux but panics on macOS:
 *
 * ```kotlin
 * import std.time.Duration
 * import std.time.Instant
 *
 * val now = Instant.now()
 * val daysPer10Millennia = 3652425
 * val solarSecondsPerDay = 60 * 60 * 24
 * val millenniumInSolarSeconds = 31556952000
 * check(millenniumInSolarSeconds == daysPer10Millennia * solarSecondsPerDay / 10)
 * val duration = Duration.new(millenniumInSolarSeconds, 0)
 * println("${now + duration}")
 * ```
 *
 * For cross-platform code, you can comfortably use durations of up to around one hundred years.
 *
 * # Underlying System calls
 *
 * The following system calls are [currently] being used by `now()` to find out
 * the current time:
 *
 * |  Platform |               System call                                            |
 * |-----------|----------------------------------------------------------------------|
 * | SGX       | [`insecureTime` usercall]. More information on [timekeeping in SGX] |
 * | UNIX      | [clockGettime] with `CLOCK_MONOTONIC`                               |
 * | WASI      | [clockGettime] with `CLOCK_MONOTONIC`                               |
 * | Darwin    | [clockGettime] with `CLOCK_UPTIME_RAW`                              |
 * | VXWorks   | [clockGettime] with `CLOCK_MONOTONIC`                               |
 * | SOLID     | `getTim`                                                            |
 * | Windows   | [QueryPerformanceCounter]                                            |
 *
 * [QueryPerformanceCounter]: https://docs.microsoft.com/en-us/windows/win32/api/profileapi/nf-profileapi-queryperformancecounter
 * [`insecureTime` usercall]: https://edp.fortanix.com/docs/api/fortanixSgxAbi/struct.Usercalls.html#method.insecureTime
 * [timekeeping in SGX]: https://edp.fortanix.com/docs/concepts/rust-std/#codestdtimecode
 * [clockGettime]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/clockGetres.html
 *
 * **Disclaimer:** These system calls might change over time.
 *
 * > Note: mathematical operations like [add] may panic if the underlying
 * > structure cannot represent the new point in time.
 *
 * [add]: Instant.add
 *
 * ## Monotonicity
 *
 * On all platforms `Instant` will try to use an OS API that guarantees monotonic behavior
 * if available, which is the case for all [tier 1] platforms.
 * In practice such guarantees are – under rare circumstances – broken by hardware, virtualization
 * or operating system bugs. To work around these bugs and platforms not offering monotonic clocks
 * [durationSince], [elapsed] and [`sub`](#impl-Sub-for-Instant) saturate to zero. In older
 * Rust versions this lead to a panic instead. [checkedDurationSince] can be used to detect and
 * handle situations where monotonicity is violated, or `Instant`s are subtracted in the wrong order.
 *
 * This workaround obscures programming errors where earlier and later instants are accidentally
 * swapped. For this reason future Rust versions may reintroduce panics.
 *
 * [tier 1]: https://doc.rust-lang.org/rustc/platform-support.html
 * [durationSince]: Instant.durationSince
 * [elapsed]: Instant.elapsed
 * [sub]: Instant.sub
 * [checkedDurationSince]: Instant.checkedDurationSince
 */
public open class Instant(
    public var value: io.github.kotlinmania.rustlang.std.sys.time.unix.Instant,
) : Add<Duration, Instant>, AddAssign<Duration>, Sub<Duration, Instant>, SubAssign<Duration> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.time.unix.Instant
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Instant = Instant(value)

    /**
     * Returns the amount of time elapsed from another instant to this one,
     * or zero duration if that instant is later than this one.
     *
     * # Panics
     *
     * Previous Rust versions panicked when `earlier` was later than `self`. Currently this
     * method saturates. Future versions may reintroduce the panic in some circumstances.
     * See [Monotonicity].
     *
     * [Monotonicity]: Instant#monotonicity
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.sleep
     * import std.time.Duration
     * import std.time.Instant
     *
     * val now = Instant.now()
     * sleep(Duration.new(1, 0))
     * val newNow = Instant.now()
     * println("${newNow.durationSince(now)}")
     * println("${now.durationSince(newNow)}")
     * // 0ns
     * ```
     */
    public open fun durationSince(earlier: Instant): Duration = this.checkedDurationSince(earlier).unwrapOrDefault()

    /**
     * Returns the amount of time elapsed from another instant to this one,
     * or None if that instant is later than this one.
     *
     * Due to [monotonicity bugs], even under correct logical ordering of the passed `Instant`s,
     * this method can return `None`.
     *
     * [monotonicity bugs]: Instant#monotonicity
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.sleep
     * import std.time.Duration
     * import std.time.Instant
     *
     * val now = Instant.now()
     * sleep(Duration.new(1, 0))
     * val newNow = Instant.now()
     * println("${newNow.checkedDurationSince(now)}")
     * println("${now.checkedDurationSince(newNow)}")
     * // None
     * ```
     */
    public open fun checkedDurationSince(earlier: Instant): Duration? = this.value.checkedSubInstant(earlier.value)

    /**
     * Returns the amount of time elapsed from another instant to this one,
     * or zero duration if that instant is later than this one.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.sleep
     * import std.time.Duration
     * import std.time.Instant
     *
     * val now = Instant.now()
     * sleep(Duration.new(1, 0))
     * val newNow = Instant.now()
     * println("${newNow.saturatingDurationSince(now)}")
     * println("${now.saturatingDurationSince(newNow)}")
     * // 0ns
     * ```
     */
    public open fun saturatingDurationSince(earlier: Instant): Duration = this.checkedDurationSince(earlier).unwrapOrDefault()

    /**
     * Returns the amount of time elapsed since this instant.
     *
     * # Panics
     *
     * Previous Rust versions panicked when the current time was earlier than self. Currently this
     * method returns a Duration of zero in that case. Future versions may reintroduce the panic.
     * See [Monotonicity].
     *
     * [Monotonicity]: Instant#monotonicity
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.sleep
     * import std.time.Duration
     * import std.time.Instant
     *
     * val instant = Instant.now()
     * val threeSecs = Duration.fromSecs(3)
     * sleep(threeSecs)
     * check(instant.elapsed() >= threeSecs)
     * ```
     */
    public open fun elapsed(): Duration = Instant.now() - this

    /**
     * Returns `Some(t)` where `t` is the time `self + duration` if `t` can be represented as
     * `Instant` (which means it's inside the bounds of the underlying data structure), `None`
     * otherwise.
     */
    public open fun checkedAdd(duration: Duration): Instant? = this.value.checkedAddDuration(duration).map({ Instant(it) })

    /**
     * Returns `Some(t)` where `t` is the time `self - duration` if `t` can be represented as
     * `Instant` (which means it's inside the bounds of the underlying data structure), `None`
     * otherwise.
     */
    public open fun checkedSub(duration: Duration): Instant? = this.value.checkedSubDuration(duration).map({ Instant(it) })

    // Used by platform specific `sleepUntil` implementations such as the one used on Linux.
    public open fun intoInner(): io.github.kotlinmania.rustlang.std.sys.time.unix.Instant = this.value

    /**
     * # Panics
     *
     * This function may panic if the resulting point in time cannot be represented by the
     * underlying data structure. See [Instant.checkedAdd] for a version without panic.
     */
    public open fun add(other: Duration): Instant = (this.checkedAdd(other) ?: error("overflow when adding duration to instant"))

    public open fun addAssign(other: Duration) {
        var selfMut = this
        selfMut = selfMut + other
    }

    public open fun sub(other: Duration): Instant = (this.checkedSub(other) ?: error("overflow when subtracting duration from instant"))

    public open fun subAssign(other: Duration) {
        var selfMut = this
        selfMut = selfMut - other
    }

    /**
     * Returns the amount of time elapsed from another instant to this one,
     * or zero duration if that instant is later than this one.
     *
     * # Panics
     *
     * Previous Rust versions panicked when `other` was later than `self`. Currently this
     * method saturates. Future versions may reintroduce the panic in some circumstances.
     * See [Monotonicity].
     *
     * [Monotonicity]: Instant#monotonicity
     */
    public open fun sub(other: Instant): Duration = this.durationSince(other)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.value.fmt(f)

    public companion object {
        /**
         * Returns an instant corresponding to "now".
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Instant
         *
         * val now = Instant.now()
         * ```
         */
        public fun now(): Instant = Instant(io.github.kotlinmania.rustlang.std.sys.time.unix.Instant.now())
    }
}

/**
 * A measurement of the system clock, useful for talking to
 * external entities like the file system or other processes.
 *
 * Distinct from the [Instant] type, this time measurement **is not
 * monotonic**. This means that you can save a file to the file system, then
 * save another file to the file system, **and the second file has a
 * `SystemTime` measurement earlier than the first**. In other words, an
 * operation that happens after another operation in real time may have an
 * earlier `SystemTime`!
 *
 * Consequently, comparing two `SystemTime` instances to learn about the
 * duration between them returns a [Result] instead of an infallible [Duration]
 * to indicate that this sort of time drift may happen and needs to be handled.
 *
 * Although a `SystemTime` cannot be directly inspected, the [UnixEpoch]
 * constant is provided in this module as an anchor in time to learn
 * information about a `SystemTime`. By calculating the duration from this
 * fixed point in time, a `SystemTime` can be converted to a human-readable time,
 * or perhaps some other string representation.
 *
 * The size of a `SystemTime` struct may vary depending on the target operating
 * system.
 *
 * A `SystemTime` does not count leap seconds.
 * `SystemTime.now()`s behavior around a leap second
 * is the same as the operating system's wall clock.
 * The precise behavior near a leap second
 * (e.g. whether the clock appears to run slow or fast, or stop, or jump)
 * depends on platform and configuration,
 * so should not be relied on.
 *
 * Example:
 *
 * ```kotlin
 * import std.thread.sleep
 * import std.time.Duration
 * import std.time.SystemTime
 *
 * fun main() {
 *     val now = SystemTime.now()
 *     // we sleep for 2 seconds
 *     sleep(Duration.new(2, 0))
 *     run {
 *         val matchVal = now.elapsed()
 *         if (matchVal.isSuccess) {
 *             val elapsed = matchVal.getOrThrow()
 *             // it prints '2'
 *             println("${elapsed.asSecs()}")
 *         } else {
 *             val e = matchVal.exceptionOrNull()!!
 *             // the system clock went backwards!
 *             println("Great Scott! $e")
 *         }
 *     }
 * }
 * ```
 *
 * # Platform-specific behavior
 *
 * The precision of `SystemTime` can depend on the underlying OS-specific time format.
 * For example, on Windows the time is represented in 100 nanosecond intervals whereas Linux
 * can represent nanosecond intervals.
 *
 * The following system calls are [currently] being used by `now()` to find out
 * the current time:
 *
 * |  Platform |               System call                                            |
 * |-----------|----------------------------------------------------------------------|
 * | SGX       | [`insecureTime` usercall]. More information on [timekeeping in SGX] |
 * | UNIX      | [clockGettime (Realtime Clock)]                                     |
 * | WASI      | [clockGettime (Realtime Clock)]                                     |
 * | Darwin    | [clockGettime (Realtime Clock)]                                     |
 * | VXWorks   | [clockGettime (Realtime Clock)]                                     |
 * | SOLID     | `SolidRtcReadTime`                                                 |
 * | Windows   | [GetSystemTimePreciseAsFileTime] / [GetSystemTimeAsFileTime]         |
 *
 * [`insecureTime` usercall]: https://edp.fortanix.com/docs/api/fortanixSgxAbi/struct.Usercalls.html#method.insecureTime
 * [timekeeping in SGX]: https://edp.fortanix.com/docs/concepts/rust-std/#codestdtimecode
 * [clockGettime (Realtime Clock)]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/clockGetres.html
 * [GetSystemTimePreciseAsFileTime]: https://docs.microsoft.com/en-us/windows/win32/api/sysinfoapi/nf-sysinfoapi-getsystemtimepreciseasfiletime
 * [GetSystemTimeAsFileTime]: https://docs.microsoft.com/en-us/windows/win32/api/sysinfoapi/nf-sysinfoapi-getsystemtimeasfiletime
 *
 * **Disclaimer:** These system calls might change over time.
 *
 * > Note: mathematical operations like [add] may panic if the underlying
 * > structure cannot represent the new point in time.
 *
 * [add]: SystemTime.add
 * [UnixEpoch]: SystemTime.UnixEpoch
 */
public open class SystemTime(
    public var value: io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime,
) : Add<Duration, SystemTime>, AddAssign<Duration>, Sub<Duration, SystemTime>, SubAssign<Duration>, FromInner<io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime, SystemTime>, IntoInner<io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): SystemTime = SystemTime(value)

    /**
     * Returns the amount of time elapsed from an earlier point in time.
     *
     * This function may fail because measurements taken earlier are not
     * guaranteed to always be before later measurements (due to anomalies such
     * as the system clock being adjusted either forwards or backwards).
     * [Instant] can be used to measure elapsed time without this risk of failure.
     *
     * If successful, <code>[Ok]\([Duration])</code> is returned where the duration represents
     * the amount of time elapsed from the specified measurement to this one.
     *
     * Returns an [Err] if `earlier` is later than `self`, and the error
     * contains how far from `self` the time is.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.SystemTime
     *
     * val sysTime = SystemTime.now()
     * val newSysTime = SystemTime.now()
     * val difference = newSysTime.durationSince(sysTime).expect("Clock may have gone backwards")
     * println("$difference")
     * ```
     */
    public open fun durationSince(earlier: SystemTime): Result<Duration, SystemTimeError> = this.value.subTime(earlier.value).mapErr({ SystemTimeError(it) })

    /**
     * Returns the difference from this system time to the
     * current clock time.
     *
     * This function may fail as the underlying system clock is susceptible to
     * drift and updates (e.g., the system clock could go backwards), so this
     * function might not always succeed. If successful, <code>[Ok]\([Duration])</code> is
     * returned where the duration represents the amount of time elapsed from
     * this time measurement to the current time.
     *
     * To measure elapsed time reliably, use [Instant] instead.
     *
     * Returns an [Err] if `self` is later than the current system time, and
     * the error contains how far from the current system time `self` is.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.sleep
     * import std.time.Duration
     * import std.time.SystemTime
     *
     * val sysTime = SystemTime.now()
     * val oneSec = Duration.fromSecs(1)
     * sleep(oneSec)
     * check(sysTime.elapsed().unwrap() >= oneSec)
     * ```
     */
    public open fun elapsed(): Result<Duration, SystemTimeError> = SystemTime.now().durationSince(this)

    /**
     * Returns `Some(t)` where `t` is the time `self + duration` if `t` can be represented as
     * `SystemTime` (which means it's inside the bounds of the underlying data structure), `None`
     * otherwise.
     *
     * In the case that the `duration` is smaller than the time precision of the operating
     * system, `Some(self)` will be returned.
     */
    public open fun checkedAdd(duration: Duration): SystemTime? = this.value.checkedAddDuration(duration).map({ SystemTime(it) })

    /**
     * Returns `Some(t)` where `t` is the time `self - duration` if `t` can be represented as
     * `SystemTime` (which means it's inside the bounds of the underlying data structure), `None`
     * otherwise.
     *
     * In the case that the `duration` is smaller than the time precision of the operating
     * system, `Some(self)` will be returned.
     */
    public open fun checkedSub(duration: Duration): SystemTime? = this.value.checkedSubDuration(duration).map({ SystemTime(it) })

    /**
     * Saturating [SystemTime] addition, computing `self + duration`,
     * returning [SystemTime.Max] if overflow occurred.
     *
     * In the case that the `duration` is smaller than the time precision of
     * the operating system, `self` will be returned.
     */
    public open fun saturatingAdd(duration: Duration): SystemTime = (this.checkedAdd(duration) ?: SystemTime.MAX)

    /**
     * Saturating [SystemTime] subtraction, computing `self - duration`,
     * returning [SystemTime.Min] if overflow occurred.
     *
     * In the case that the `duration` is smaller than the time precision of
     * the operating system, `self` will be returned.
     */
    public open fun saturatingSub(duration: Duration): SystemTime = (this.checkedSub(duration) ?: SystemTime.MIN)

    /**
     * Saturating computation of time elapsed from an earlier point in time,
     * returning [Duration.Zero] in the case that `earlier` is later or
     * equal to `self`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     * import std.time.SystemTime
     *
     * @feature(timeSaturatingSystemtime)
     * public fun doctestEntry() {
     *     val now = SystemTime.now()
     *     val prev = now.saturatingSub(Duration.new(1, 0))
     *     // now - prev should return non-zero.
     *     check(now.saturatingDurationSince(prev) == Duration.new(1, 0))
     *     check(now.durationSince(prev).isOk())
     *     // prev - now should return zero (and fail with the non-saturating).
     *     check(prev.saturatingDurationSince(now) == Duration.ZERO)
     *     check(prev.durationSince(now).isErr())
     *     // now - now should return zero (and work with the non-saturating).
     *     check(now.saturatingDurationSince(now) == Duration.ZERO)
     *     check(now.durationSince(now).isOk())
     * }
     * ```
     */
    public open fun saturatingDurationSince(earlier: SystemTime): Duration = this.durationSince(earlier).unwrapOr(Duration.ZERO)

    /**
     * # Panics
     *
     * This function may panic if the resulting point in time cannot be represented by the
     * underlying data structure. See [SystemTime.checkedAdd] for a version without panic.
     */
    public open fun add(dur: Duration): SystemTime = (this.checkedAdd(dur) ?: error("overflow when adding duration to `SystemTime`"))

    public open fun addAssign(other: Duration) {
        var selfMut = this
        selfMut = selfMut + other
    }

    public open fun sub(dur: Duration): SystemTime = (this.checkedSub(dur) ?: error("overflow when subtracting duration from `SystemTime`"))

    public open fun subAssign(other: Duration) {
        var selfMut = this
        selfMut = selfMut - other
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.value.fmt(f)

    public override fun intoInner(): io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime = this.value

    public companion object {
        //
        // NOTE! this documentation is duplicated, here and in std.time.UnixEpoch.
        // The two copies are not quite identical, because of the difference in naming.

        /**
         * An anchor in time which can be used to create new `SystemTime` instances or
         * learn about where in time a `SystemTime` lies.
         *
         * This constant is defined to be "1970-01-01 00:00:00 UTC" on all systems with
         * respect to the system clock. Using `durationSince` on an existing
         * `SystemTime` instance can tell how far away from this point in time a
         * measurement lies, and using `UNIX_EPOCH + duration` can be used to create a
         * `SystemTime` instance to represent another fixed point in time.
         *
         * `durationSince(UNIX_EPOCH).unwrap().asSecs()` returns
         * the number of non-leap seconds since the start of 1970 UTC.
         * This is a POSIX `timeT` (as a `ULong`),
         * and is the same time representation as used in many Internet protocols.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.SystemTime
         *
         * run {
         *     val matchVal = SystemTime.now().durationSince(SystemTime.UNIX_EPOCH)
         *     if (matchVal.isSuccess) {
         *         val n = matchVal.getOrThrow()
         *         println("1970-01-01 00:00:00 UTC was ${n.asSecs()} seconds ago!")
         *     } else {
         *         error("SystemTime before UNIX EPOCH!")
         *     }
         * }
         * ```
         */
        public val UNIX_EPOCH: SystemTime = UNIX_EPOCH

        /**
         * Represents the maximum value representable by [SystemTime] on this platform.
         *
         * This value differs a lot between platforms, but it is always the case
         * that any positive addition of a [Duration], whose value is greater
         * than or equal to the time precision of the operating system, to
         * [SystemTime.Max] will fail.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         * import std.time.SystemTime
         *
         * @feature(timeSystemtimeLimits)
         * public fun doctestEntry() {
         *     // Adding zero will change nothing.
         *     check(SystemTime.MAX.checkedAdd(Duration.ZERO) == SystemTime.MAX)
         *     // But adding just one second will already fail ...
         *     //
         *     // Keep in mind that this in fact may succeed, if the Duration is
         *     // smaller than the time precision of the operating system, which
         *     // happens to be 1ns on most operating systems, with Windows being the
         *     // notable exception by using 100ns, hence why this example uses 1s.
         *     check(SystemTime.MAX.checkedAdd(Duration.new(1, 0)) == null)
         *     // Utilize this for saturating arithmetic to improve error handling.
         *     // In this case, we will use a certificate with a timestamp in the
         *     // future as a practical example.
         *     val configuredOffset = Duration.fromSecs(60 * 60 * 24)
         *     val validAfter = (SystemTime.now().checkedAdd(configuredOffset) ?: SystemTime.MAX)
         * }
         * ```
         */
        public val MAX: SystemTime = SystemTime(io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime.MAX)

        /**
         * Represents the minimum value representable by [SystemTime] on this platform.
         *
         * This value differs a lot between platforms, but it is always the case
         * that any positive subtraction of a [Duration] from, whose value is
         * greater than or equal to the time precision of the operating system, to
         * [SystemTime.Min] will fail.
         *
         * Depending on the platform, this may be either less than or equal to
         * [SystemTime.UnixEpoch], depending on whether the operating system
         * supports the representation of timestamps before the Unix epoch or not.
         * However, it is always guaranteed that a [SystemTime.UnixEpoch] fits
         * between a [SystemTime.Min] and [SystemTime.Max].
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         * import std.time.SystemTime
         *
         * @feature(timeSystemtimeLimits)
         * public fun doctestEntry() {
         *     // Subtracting zero will change nothing.
         *     check(SystemTime.MIN.checkedSub(Duration.ZERO) == SystemTime.MIN)
         *     // But subtracting just one second will already fail.
         *     //
         *     // Keep in mind that this in fact may succeed, if the Duration is
         *     // smaller than the time precision of the operating system, which
         *     // happens to be 1ns on most operating systems, with Windows being the
         *     // notable exception by using 100ns, hence why this example uses 1s.
         *     check(SystemTime.MIN.checkedSub(Duration.new(1, 0)) == null)
         *     // Utilize this for saturating arithmetic to improve error handling.
         *     // In this case, we will use a cache expiry as a practical example.
         *     val configuredExpiry = Duration.fromSecs(60 * 3)
         *     val expiryThreshold = (SystemTime.now().checkedSub(configuredExpiry) ?: SystemTime.MIN)
         * }
         * ```
         */
        public val MIN: SystemTime = SystemTime(io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime.MIN)

        /**
         * Returns the system time corresponding to "now".
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.SystemTime
         *
         * val sysTime = SystemTime.now()
         * ```
         */
        public fun now(): SystemTime = SystemTime(io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime.now())

        public fun fromInner(time: io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime): SystemTime = SystemTime(time)
    }
}

/**
 * An error returned from the `durationSince` and `elapsed` methods on
 * `SystemTime`, used to learn how far in the opposite direction a system time
 * lies.
 *
 * # Examples
 *
 * ```kotlin
 * import std.thread.sleep
 * import std.time.Duration
 * import std.time.SystemTime
 *
 * val sysTime = SystemTime.now()
 * sleep(Duration.fromSecs(1))
 * val newSysTime = SystemTime.now()
 * run {
 *     val matchVal = sysTime.durationSince(newSysTime)
 *     if (matchVal.isSuccess) {} else {
 *         val e = matchVal.exceptionOrNull()!!
 *         println("SystemTimeError difference: ${e.duration()}")
 *     }
 * }
 * ```
 */
public open class SystemTimeError(
    public var value: Duration,
) {
    public var field0: Duration
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): SystemTimeError = SystemTimeError(value)

    /**
     * Returns the positive duration which represents how far forward the
     * second system time was from the first.
     *
     * A `SystemTimeError` is returned from the [SystemTime.durationSince]
     * and [SystemTime.elapsed] methods whenever the second system time
     * represents a point later in time than the `self` of the method call.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.sleep
     * import std.time.Duration
     * import std.time.SystemTime
     *
     * val sysTime = SystemTime.now()
     * sleep(Duration.fromSecs(1))
     * val newSysTime = SystemTime.now()
     * run {
     *     val matchVal = sysTime.durationSince(newSysTime)
     *     if (matchVal.isSuccess) {} else {
     *         val e = matchVal.exceptionOrNull()!!
     *         println("SystemTimeError difference: ${e.duration()}")
     *     }
     * }
     * ```
     */
    public open fun duration(): Duration = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("second time provided was later than self"); Result.Ok(Unit) }
}

//
// NOTE! this documentation is duplicated, here and in SystemTime.UnixEpoch.
// The two copies are not quite identical, because of the difference in naming.

/**
 * An anchor in time which can be used to create new `SystemTime` instances or
 * learn about where in time a `SystemTime` lies.
 *
 * This constant is defined to be "1970-01-01 00:00:00 UTC" on all systems with
 * respect to the system clock. Using `durationSince` on an existing
 * [SystemTime] instance can tell how far away from this point in time a
 * measurement lies, and using `UNIX_EPOCH + duration` can be used to create a
 * [SystemTime] instance to represent another fixed point in time.
 *
 * `durationSince(UNIX_EPOCH).unwrap().asSecs()` returns
 * the number of non-leap seconds since the start of 1970 UTC.
 * This is a POSIX `timeT` (as a `ULong`),
 * and is the same time representation as used in many Internet protocols.
 *
 * # Examples
 *
 * ```kotlin
 * import std.time.SystemTime
 * import std.time.UNIX_EPOCH
 *
 * run {
 *     val matchVal = SystemTime.now().durationSince(UNIX_EPOCH)
 *     if (matchVal.isSuccess) {
 *         val n = matchVal.getOrThrow()
 *         println("1970-01-01 00:00:00 UTC was ${n.asSecs()} seconds ago!")
 *     } else {
 *         error("SystemTime before UNIX EPOCH!")
 *     }
 * }
 * ```
 */
public val UNIX_EPOCH: SystemTime = SystemTime(UNIX_EPOCH)
