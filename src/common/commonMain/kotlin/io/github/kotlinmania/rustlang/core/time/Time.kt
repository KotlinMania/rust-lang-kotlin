// port-lint: source time.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.time

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.iter.Sum
import io.github.kotlinmania.rustlang.core.num.f32.MIN_EXP
import io.github.kotlinmania.rustlang.core.num.nichetypes.Nanoseconds
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.num.rustCheckedMul
import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.core.ops.Add
import io.github.kotlinmania.rustlang.core.ops.AddAssign
import io.github.kotlinmania.rustlang.core.ops.Div
import io.github.kotlinmania.rustlang.core.ops.DivAssign
import io.github.kotlinmania.rustlang.core.ops.Mul
import io.github.kotlinmania.rustlang.core.ops.MulAssign
import io.github.kotlinmania.rustlang.core.ops.Sub
import io.github.kotlinmania.rustlang.core.ops.SubAssign
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.rustStrChars

// Temporal quantification.
//
// # Examples:
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

public val NANOS_PER_SEC: UInt = 1000000000u

public val NANOS_PER_MILLI: UInt = 1000000u

public val NANOS_PER_MICRO: UInt = 1000u

public val MILLIS_PER_SEC: ULong = 1000uL

public val MICROS_PER_SEC: ULong = 1000000uL

public val SECS_PER_MINUTE: ULong = 60uL

public val MINS_PER_HOUR: ULong = 60uL

public val HOURS_PER_DAY: ULong = 24uL

public val DAYS_PER_WEEK: ULong = 7uL

/**
 * A `Duration` type to represent a span of time, typically used for system
 * timeouts.
 *
 * Each `Duration` is composed of a whole number of seconds and a fractional part
 * represented in nanoseconds. If the underlying system does not support
 * nanosecond-level precision, APIs binding a system timeout will typically round up
 * the number of nanoseconds.
 *
 * [Duration]s implement many common traits, including [Add], [Sub], and other
 * [ops] traits. It implements [Default] by returning a zero-length `Duration`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.time.Duration
 *
 * val fiveSeconds = Duration.new(5, 0)
 * val fiveSecondsAndFiveNanos = fiveSeconds + Duration.new(0, 5)
 * check(fiveSecondsAndFiveNanos.asSecs() == 5)
 * check(fiveSecondsAndFiveNanos.subsecNanos() == 5)
 * val tenMillis = Duration.fromMillis(10)
 * ```
 *
 * # Formatting `Duration` values
 *
 * `Duration` intentionally does not have a `Display` impl, as there are a
 * variety of ways to format spans of time for human readability. `Duration`
 * provides a `Debug` impl that shows the full precision of the value.
 *
 * The `Debug` output uses the non-ASCII "µs" suffix for microseconds. If your
 * program output may appear in contexts that cannot rely on full Unicode
 * compatibility, you may wish to format `Duration` objects yourself or use a
 * crate to do so.
 *
 * Alternatively, you can display the value as seconds via [Duration.displaySeconds].
 */
public open class Duration(
    public var secs: ULong,
    public var nanos: Nanoseconds,
) : Add<Duration, Any?, Duration>, AddAssign<Duration>, Sub<Duration, Any?, Duration>, SubAssign<Duration>, Mul<UInt, Any?, Duration>, MulAssign<UInt>, Div<UInt, Any?, Duration>, DivAssign<UInt>, Sum<Duration, Duration> {
    public open fun clone(): Duration = Duration(secs, nanos)

    /**
     * Returns true if this `Duration` spans no time.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.ZERO.isZero())
     * check(Duration.new(0, 0).isZero())
     * check(Duration.fromNanos(0).isZero())
     * check(Duration.fromSecs(0).isZero())
     * check(!Duration.new(1, 1).isZero())
     * check(!Duration.fromNanos(1).isZero())
     * check(!Duration.fromSecs(1).isZero())
     * ```
     */
    public open fun isZero(): Boolean = this.secs == 0uL && this.nanos.asInner() == 0

    /**
     * Returns the number of _whole_ seconds contained by this `Duration`.
     *
     * The returned value does not include the fractional (nanosecond) part of the
     * duration, which can be obtained using [subsecNanos].
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.new(5, 730023852)
     * check(duration.asSecs() == 5)
     * ```
     *
     * To determine the total number of seconds represented by the `Duration`
     * including the fractional part, use [asSecsF64] or [asSecsF32]
     *
     * [asSecsF64]: Duration.asSecsF64
     * [asSecsF32]: Duration.asSecsF32
     * [subsecNanos]: Duration.subsecNanos
     */
    public open fun asSecs(): ULong = this.secs

    /**
     * Returns the fractional part of this `Duration`, in whole milliseconds.
     *
     * This method does **not** return the length of the duration when
     * represented by milliseconds. The returned number always represents a
     * fractional portion of a second (i.e., it is less than one thousand).
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.fromMillis(5432)
     * check(duration.asSecs() == 5)
     * check(duration.subsecMillis() == 432)
     * ```
     */
    public open fun subsecMillis(): UInt = this.nanos.asInner() / NANOS_PER_MILLI

    /**
     * Returns the fractional part of this `Duration`, in whole microseconds.
     *
     * This method does **not** return the length of the duration when
     * represented by microseconds. The returned number always represents a
     * fractional portion of a second (i.e., it is less than one million).
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.fromMicros(1234567)
     * check(duration.asSecs() == 1)
     * check(duration.subsecMicros() == 234567)
     * ```
     */
    public open fun subsecMicros(): UInt = this.nanos.asInner() / NANOS_PER_MICRO

    /**
     * Returns the fractional part of this `Duration`, in nanoseconds.
     *
     * This method does **not** return the length of the duration when
     * represented by nanoseconds. The returned number always represents a
     * fractional portion of a second (i.e., it is less than one billion).
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.fromMillis(5010)
     * check(duration.asSecs() == 5)
     * check(duration.subsecNanos() == 10000000)
     * ```
     */
    public open fun subsecNanos(): UInt = this.nanos.asInner()

    /**
     * Returns the total number of whole milliseconds contained by this `Duration`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.new(5, 730023852)
     * check(duration.asMillis() == 5730)
     * ```
     */
    public open fun asMillis(): U128 = U128.fromULong(this.secs) * U128.fromULong(MILLIS_PER_SEC) + U128.fromUInt((this.nanos.asInner() / NANOS_PER_MILLI))

    /**
     * Returns the total number of whole microseconds contained by this `Duration`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.new(5, 730023852)
     * check(duration.asMicros() == 5730023)
     * ```
     */
    public open fun asMicros(): U128 = U128.fromULong(this.secs) * U128.fromULong(MICROS_PER_SEC) + U128.fromUInt((this.nanos.asInner() / NANOS_PER_MICRO))

    /**
     * Returns the total number of nanoseconds contained by this `Duration`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val duration = Duration.new(5, 730023852)
     * check(duration.asNanos() == 5730023852)
     * ```
     */
    public open fun asNanos(): U128 = U128.fromULong(this.secs) * U128.fromUInt(NANOS_PER_SEC) + U128.fromLong(this.nanos.asInner().toLong())

    /**
     * Computes the absolute difference between `self` and `other`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(100, 0).absDiff(Duration.new(80, 0)) == Duration.new(20, 0))
     * check(Duration.new(100, 400000000).absDiff(Duration.new(110, 0)) == Duration.new(9, 600000000))
     * ```
     */
    public open fun absDiff(other: Duration): Duration =
        when (val matchVal = this.checkedSub(other)) {
            null -> {
                other.checkedSub(this)!!
            }
            else -> {
                val res = matchVal
                res
            }
        }

    /**
     * Checked `Duration` addition. Computes `self + other`, returning [None]
     * if overflow occurred.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(0, 0).checkedAdd(Duration.new(0, 1)) == Duration.new(0, 1))
     * check(Duration.new(1, 0).checkedAdd(Duration.new(ULong.MAX_VALUE, 0)) == null)
     * ```
     */
    public open fun checkedAdd(rhs: Duration): Duration? {
        return when (val matchVal = rustCheckedAdd(this.secs, rhs.secs)) {
            null -> {
                null
            }
            else -> {
                val secs = matchVal
                var nanos = this.nanos.asInner() + rhs.nanos.asInner()
                if (nanos >= NANOS_PER_SEC) {
                    nanos -= NANOS_PER_SEC
                    val newSecs = secs.checkedAdd(1) ?: return null
                    secs = newSecs
                }
                if (false) {
                    check(nanos < NANOS_PER_SEC)
                }
                Duration.new(secs, nanos)
            }
        }
    }

    /**
     * Saturating `Duration` addition. Computes `self + other`, returning [Duration.Max]
     * if overflow occurred.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(0, 0).saturatingAdd(Duration.new(0, 1)) == Duration.new(0, 1))
     * check(Duration.new(1, 0).saturatingAdd(Duration.new(ULong.MAX_VALUE, 0)) == Duration.MAX)
     * ```
     */
    public open fun saturatingAdd(rhs: Duration): Duration =
        when (val matchVal = this.checkedAdd(rhs)) {
            null -> Duration.MAX
            else -> {
                val res = matchVal
                res
            }
        }

    /**
     * Checked `Duration` subtraction. Computes `self - other`, returning [None]
     * if the result would be negative or if overflow occurred.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(0, 1).checkedSub(Duration.new(0, 0)) == Duration.new(0, 1))
     * check(Duration.new(0, 0).checkedSub(Duration.new(0, 1)) == null)
     * ```
     */
    public open fun checkedSub(rhs: Duration): Duration? {
        return when (val matchVal = rustCheckedSub(this.secs, rhs.secs)) {
            null -> {
                null
            }
            else -> {
                val secs = matchVal
                val nanos =
                    if (this.nanos.asInner() >= rhs.nanos.asInner()) {
                        this.nanos.asInner() - rhs.nanos.asInner()
                    } else when (val matchVal1 = secs.checkedSub(1)) {
                        null -> {
                            return null
                        }
                        else -> {
                            val subSecs = matchVal1
                            secs = subSecs
                            this.nanos.asInner() + NANOS_PER_SEC - rhs.nanos.asInner()
                        }
                    }
                if (false) {
                    check(nanos < NANOS_PER_SEC)
                }
                Duration.new(secs, nanos)
            }
        }
    }

    /**
     * Saturating `Duration` subtraction. Computes `self - other`, returning [Duration.Zero]
     * if the result would be negative or if overflow occurred.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(0, 1).saturatingSub(Duration.new(0, 0)) == Duration.new(0, 1))
     * check(Duration.new(0, 0).saturatingSub(Duration.new(0, 1)) == Duration.ZERO)
     * ```
     */
    public open fun saturatingSub(rhs: Duration): Duration =
        when (val matchVal = this.checkedSub(rhs)) {
            null -> Duration.ZERO
            else -> {
                val res = matchVal
                res
            }
        }

    /**
     * Checked `Duration` multiplication. Computes `self * other`, returning
     * [None] if overflow occurred.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(0, 500000001).checkedMul(2) == Duration.new(1, 2))
     * check(Duration.new(ULong.MAX_VALUE - 1uL, 0).checkedMul(2) == null)
     * ```
     */
    public open fun checkedMul(rhs: UInt): Duration? {
        // Multiply nanoseconds as u64, because it cannot overflow that way.
        val totalNanos = this.nanos.asInner().toULong() * rhs.toULong()
        val extraSecs = totalNanos / (NANOS_PER_SEC.toULong())
        val nanos = (totalNanos % (NANOS_PER_SEC.toULong())).toUInt()
        // Note(const-Note): use `andThen` once that is possible.
        when (val matchVal = rustCheckedMul(this.secs, rhs.toULong())) {
            null -> {}
            else -> {
                val s = matchVal
                when (val matchVal1 = s.checkedAdd(extraSecs)) {
                    null -> {}
                    else -> {
                        val secs = matchVal1
                        if (false) {
                            check(nanos < NANOS_PER_SEC)
                        }
                        return Duration.new(secs, nanos)
                    }
                }
            }
        }
        return null
    }

    /**
     * Saturating `Duration` multiplication. Computes `self * other`, returning
     * [Duration.Max] if overflow occurred.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(0, 500000001).saturatingMul(2) == Duration.new(1, 2))
     * check(Duration.new(ULong.MAX_VALUE - 1uL, 0).saturatingMul(2) == Duration.MAX)
     * ```
     */
    public open fun saturatingMul(rhs: UInt): Duration =
        when (val matchVal = this.checkedMul(rhs)) {
            null -> Duration.MAX
            else -> {
                val res = matchVal
                res
            }
        }

    /**
     * Checked `Duration` division. Computes `self / other`, returning [None]
     * if `other == 0`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * check(Duration.new(2, 0).checkedDiv(2) == Duration.new(1, 0))
     * check(Duration.new(1, 0).checkedDiv(2) == Duration.new(0, 500000000))
     * check(Duration.new(2, 0).checkedDiv(0) == null)
     * ```
     */
    public open fun checkedDiv(rhs: UInt): Duration? =
        if (rhs != 0u) {
            val (secs, extraSecs) = Pair(this.secs / (rhs.toULong()), this.secs % (rhs.toULong()))
            var (nanos, extraNanos) = Pair(this.nanos.asInner() / rhs, this.nanos.asInner() % rhs)
            nanos += ((extraSecs * (NANOS_PER_SEC.toULong()) + extraNanos.toULong()) / (rhs.toULong())).toUInt()
            if (false) {
                check(nanos < NANOS_PER_SEC)
            }
            Duration.new(secs, nanos)
        } else {
            null
        }

    /**
     * Returns the number of seconds contained by this `Duration` as `Double`.
     *
     * The returned value includes the fractional (nanosecond) part of the duration.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * val dur = Duration.new(2, 700000000)
     * check(dur.asSecsF64() == 2.7)
     * ```
     */
    public open fun asSecsF64(): Double = (this.secs.toDouble()) + (this.nanos.asInner().toDouble()) / (NANOS_PER_SEC.toDouble())

    /**
     * Returns the number of seconds contained by this `Duration` as `Float`.
     *
     * The returned value includes the fractional (nanosecond) part of the duration.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * val dur = Duration.new(2, 700000000)
     * check(dur.asSecsF32() == 2.7)
     * ```
     */
    public open fun asSecsF32(): kotlin.Float = (this.secs.toFloat()) + (this.nanos.asInner().toFloat()) / (NANOS_PER_SEC.toFloat())

    /**
     * Returns the number of milliseconds contained by this `Duration` as `Double`.
     *
     * The returned value includes the fractional (nanosecond) part of the duration.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * @feature(durationMillisFloat)
     * public fun doctestEntry() {
     *     val dur = Duration.new(2, 345678000)
     *     check(dur.asMillisF64() == 2345.678)
     * }
     * ```
     */
    public open fun asMillisF64(): Double = (this.secs.toDouble()) * (MILLIS_PER_SEC.toDouble()) + (this.nanos.asInner().toDouble()) / (NANOS_PER_MILLI.toDouble())

    /**
     * Returns the number of milliseconds contained by this `Duration` as `Float`.
     *
     * The returned value includes the fractional (nanosecond) part of the duration.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * @feature(durationMillisFloat)
     * public fun doctestEntry() {
     *     val dur = Duration.new(2, 345678000)
     *     check(dur.asMillisF32() == 2345.678)
     * }
     * ```
     */
    public open fun asMillisF32(): kotlin.Float = (this.secs.toFloat()) * (MILLIS_PER_SEC.toFloat()) + (this.nanos.asInner().toFloat()) / (NANOS_PER_MILLI.toFloat())

    /**
     * Multiplies `Duration` by `Double`.
     *
     * # Panics
     * This method will panic if result is negative, overflows `Duration` or not finite.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val dur = Duration.new(2, 700000000)
     * check(dur.mulF64(3.14) == Duration.new(8, 478000000))
     * check(dur.mulF64(3.14e5) == Duration.new(847800, 0))
     * ```
     *
     * Note that `Double` does not have enough bits ([f64.MantissaDigits]) to represent the full
     * range of possible `Duration` with nanosecond precision, so rounding may occur even for
     * trivial operations like multiplying by 1.
     *
     * ```kotlin
     * import std.time.Duration
     *
     * // This is about 14.9 weeks, remaining precise to the nanosecond:
     * val weeks = Duration.fromNanos(Double.MAX_EXACT_INTEGER.toULong())
     * check(weeks == weeks.mulF64(1.0))
     * // A larger value incurs rounding in the floating-point operation:
     * val weeks1 = Duration.fromNanos(ULong.MAX_VALUE)
     * check(weeks1 != weeks1.mulF64(1.0))
     * // This is over 285 million years, remaining precise to the second:
     * val years = Duration.fromSecs(Double.MAX_EXACT_INTEGER.toULong())
     * check(years == years.mulF64(1.0))
     * // And again larger values incur rounding:
     * val years1 = Duration.fromSecs(ULong.MAX_VALUE / 2uL)
     * check(years1 != years1.mulF64(1.0))
     * ```
     *
     * ```kotlin
     * // In the extreme, rounding can even overflow `Duration`, which panics.
     * Duration.fromSecs(ULong.MAX_VALUE).mulF64(1.0)
     * ```
     */
    public open fun mulF64(rhs: Double): Duration = Duration.fromSecsF64(rhs * this.asSecsF64())

    /**
     * Multiplies `Duration` by `Float`.
     *
     * Since the significand of `Float` is quite limited compared to the range of `Duration`
     * -- only about 16.8ms of exact nanosecond precision -- this method currently forwards
     * to [`mulF64`][Self.mulF64] for greater accuracy.
     *
     * # Panics
     * This method will panic if result is negative, overflows `Duration` or not finite.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * val dur = Duration.new(2, 700000000)
     * // Note that this `3.14_f32` argument already has more floating-point
     * // representation error than a direct `3.14_f64` would, so the result
     * // is slightly different from the ideal 8.478s.
     * check(dur.mulF32(3.14) == Duration.new(8, 478000283))
     * check(dur.mulF32(3.14e5) == Duration.new(847800, 0))
     * ```
     */
    public open fun mulF32(rhs: kotlin.Float): Duration = this.mulF64(rhs.into())

    /**
     * Divides `Duration` by `Double`.
     *
     * # Panics
     * This method will panic if result is negative, overflows `Duration` or not finite.
     *
     * # Examples
     *
     * ```kotlin
     * import std.time.Duration
     *
     * val dur = Duration.new(2, 700000000)
     * check(dur.divF64(3.14) == Duration.new(0, 859872611))
     * check(dur.divF64(3.14e5) == Duration.new(0, 8599))
     * ```
     *
     * Note that `Double` does not have enough bits ([f64.MantissaDigits]) to represent the full
     * range of possible `Duration` with nanosecond precision, so rounding may occur even for
     * trivial operations like dividing by 1.
     *
     * ```kotlin
     * import std.time.Duration
     *
     * // This is about 14.9 weeks, remaining precise to the nanosecond:
     * val weeks = Duration.fromNanos(Double.MAX_EXACT_INTEGER.toULong())
     * check(weeks == weeks.divF64(1.0))
     * // A larger value incurs rounding in the floating-point operation:
     * val weeks1 = Duration.fromNanos(ULong.MAX_VALUE)
     * check(weeks1 != weeks1.divF64(1.0))
     * // This is over 285 million years, remaining precise to the second:
     * val years = Duration.fromSecs(Double.MAX_EXACT_INTEGER.toULong())
     * check(years == years.divF64(1.0))
     * // And again larger values incur rounding:
     * val years1 = Duration.fromSecs(ULong.MAX_VALUE / 2uL)
     * check(years1 != years1.divF64(1.0))
     * ```
     *
     * ```kotlin
     * // In the extreme, rounding can even overflow `Duration`, which panics.
     * Duration.fromSecs(ULong.MAX_VALUE).divF64(1.0)
     * ```
     */
    public open fun divF64(rhs: Double): Duration = Duration.fromSecsF64(this.asSecsF64() / rhs)

    /**
     * Divides `Duration` by `Float`.
     *
     * Since the significand of `Float` is quite limited compared to the range of `Duration`
     * -- only about 16.8ms of exact nanosecond precision -- this method currently forwards
     * to [`divF64`][Self.divF64] for greater accuracy.
     *
     * # Panics
     * This method will panic if result is negative, overflows `Duration` or not finite.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * val dur = Duration.new(2, 700000000)
     * // Note that this `3.14_f32` argument already has more floating-point
     * // representation error than a direct `3.14_f64` would, so the result
     * // is slightly different from the ideally rounded 0.859_872_611.
     * check(dur.divF32(3.14) == Duration.new(0, 859872583))
     * check(dur.divF32(3.14e5) == Duration.new(0, 8599))
     * ```
     */
    public open fun divF32(rhs: kotlin.Float): Duration = this.divF64(rhs.into())

    /**
     * Divides `Duration` by `Duration` and returns `Double`.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * val dur1 = Duration.new(2, 700000000)
     * val dur2 = Duration.new(5, 400000000)
     * check(dur1.divDurationF64(dur2) == 0.5)
     * ```
     */
    public open fun divDurationF64(rhs: Duration): Double {
        val selfNanos = (this.secs.toDouble()) * (NANOS_PER_SEC.toDouble()) + (this.nanos.asInner().toDouble())
        val rhsNanos = (rhs.secs.toDouble()) * (NANOS_PER_SEC.toDouble()) + (rhs.nanos.asInner().toDouble())
        return selfNanos / rhsNanos
    }

    /**
     * Divides `Duration` by `Duration` and returns `Float`.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * val dur1 = Duration.new(2, 700000000)
     * val dur2 = Duration.new(5, 400000000)
     * check(dur1.divDurationF32(dur2) == 0.5)
     * ```
     */
    public open fun divDurationF32(rhs: Duration): kotlin.Float {
        val selfNanos = (this.secs.toFloat()) * (NANOS_PER_SEC.toFloat()) + (this.nanos.asInner().toFloat())
        val rhsNanos = (rhs.secs.toFloat()) * (NANOS_PER_SEC.toFloat()) + (rhs.nanos.asInner().toFloat())
        return selfNanos / rhsNanos
    }

    /**
     * Divides `Duration` by `Duration` and returns `ULong`, rounding the result towards zero.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * @feature(durationIntegerDivision)
     * public fun doctestEntry() {
     *     val dur = Duration.new(2, 0)
     *     check(dur.divDurationFloor(Duration.new(1, 1)) == 1)
     *     check(dur.divDurationFloor(Duration.new(1, 0)) == 2)
     *     check(dur.divDurationFloor(Duration.new(0, 999999999)) == 2)
     * }
     * ```
     */
    public open fun divDurationFloor(rhs: Duration): U128 = this.asNanos().divFloor(rhs.asNanos())

    /**
     * Divides `Duration` by `Duration` and returns `ULong`, rounding the result towards positive infinity.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * @feature(durationIntegerDivision)
     * public fun doctestEntry() {
     *     val dur = Duration.new(2, 0)
     *     check(dur.divDurationCeil(Duration.new(1, 1)) == 2)
     *     check(dur.divDurationCeil(Duration.new(1, 0)) == 2)
     *     check(dur.divDurationCeil(Duration.new(0, 999999999)) == 3)
     * }
     * ```
     */
    public open fun divDurationCeil(rhs: Duration): U128 = this.asNanos().divCeil(rhs.asNanos())

    /**
     * Displays `Duration` in seconds, even if it only contains the nanosecond part.
     *
     * Unlike [Duration.asSecsF32] or [Duration.asSecsF64], which convert
     * to floating-point numbers and may lose precision, this method displays a precise
     * value. If the formatting precision `{ :.N }` is explicitly specified:
     *
     * - `N` < 9: it rounds the subsecond part with tie resolution to even.
     * - `N` = 9: it displays the precise 9-digit subsecond part.
     * - `N` > 9: it appends trailing zeros to the subsecond part.
     *
     * # Examples
     * ```kotlin
     * import std.time.Duration
     *
     * @feature(durationDisplaySeconds)
     * public fun doctestEntry() {
     *     // Display a duration longer than a second as seconds.
     *     check(Duration.new(2, 0).displaySeconds().toString() == "2s")
     *     // Display a duration shorter than a second as seconds.
     *     check(Duration.new(0, 2).displaySeconds().toString() == "0.000000002s")
     *     // `displaySeconds` displays a precise value.
     *     check("${Duration.new(1000000000000, 1).displaySeconds()}" == "1000000000000.000000001s")
     *     // If formatting precision (< 9) is explicitly specified, it rounds
     *     // the subsecond part with tie resolution to even.
     *     check("${Duration.new(1000000000000, 1).displaySeconds()}" == "1000000000000s")
     *     // If formatting precision (> 9) is explicitly specified, it appends
     *     // trailing zeros to the subsecond part.
     *     check("${Duration.new(1000000000000, 1).displaySeconds()}" == "1000000000000.0000000010s")
     *     // In contrast, `asSecsF64` converts it to a floating-point number
     *     // and may introduce floating-point computation errors.
     *     check("${Duration.new(1000000000000, 1).asSecsF64()}" == "1000000000000")
     * }
     * ```
     */
    public open fun displaySeconds(): DisplaySeconds = DisplaySeconds(this)

    public override fun add(rhs: Duration): Duration = (this.checkedAdd(rhs) ?: error("adding durations should not exceed u64::MAX"))

    public override fun addAssign(rhs: Duration) {
        this = this + rhs
    }

    public override fun sub(rhs: Duration): Duration = (this.checkedSub(rhs) ?: error("subtracting durations should not be negative"))

    public override fun subAssign(rhs: Duration) {
        this = this - rhs
    }

    public override fun mul(rhs: UInt): Duration = (this.checkedMul(rhs) ?: error("multiplying duration by scalar should not exceed u64::MAX"))

    public override fun mulAssign(rhs: UInt) {
        this = this * rhs
    }

    public override fun div(rhs: UInt): Duration = (this.checkedDiv(rhs) ?: error("divisor should not be zero"))

    public override fun divAssign(rhs: UInt) {
        this = this / rhs
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        // Print leading '+' sign if requested
        val prefix =
            if (f.signPlus()) {
                "+"
            } else {
                ""
            }
        return if (this.secs > 0uL) {
            run { fmtDecimal(f, this.secs, this.nanos.asInner(), NANOS_PER_SEC / 10u, prefix, "s"); Result.Ok(Unit) }
        } else if (this.nanos.asInner() >= NANOS_PER_MILLI) {
            run { fmtDecimal(f, (this.nanos.asInner() / NANOS_PER_MILLI).toULong(), this.nanos.asInner() % NANOS_PER_MILLI, NANOS_PER_MILLI / 10u, prefix, "ms"); Result.Ok(Unit) }
        } else if (this.nanos.asInner() >= NANOS_PER_MICRO) {
            run { fmtDecimal(f, (this.nanos.asInner() / NANOS_PER_MICRO).toULong(), this.nanos.asInner() % NANOS_PER_MICRO, NANOS_PER_MICRO / 10u, prefix, "µs"); Result.Ok(Unit) }
        } else {
            run { fmtDecimal(f, this.nanos.asInner().toULong(), 0u, 1u, prefix, "ns"); Result.Ok(Unit) }
        }
    }

    public companion object {
        /**
         * The duration of one second.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * public fun doctestEntry() {
         *     check(Duration.SECOND == Duration.fromSecs(1))
         * }
         * ```
         */
        public val SECOND: Duration = Duration.fromSecs(1uL)

        /**
         * The duration of one millisecond.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * public fun doctestEntry() {
         *     check(Duration.MILLISECOND == Duration.fromMillis(1))
         * }
         * ```
         */
        public val MILLISECOND: Duration = Duration.fromMillis(1uL)

        /**
         * The duration of one microsecond.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * public fun doctestEntry() {
         *     check(Duration.MICROSECOND == Duration.fromMicros(1))
         * }
         * ```
         */
        public val MICROSECOND: Duration = Duration.fromMicros(1uL)

        /**
         * The duration of one nanosecond.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * public fun doctestEntry() {
         *     check(Duration.NANOSECOND == Duration.fromNanos(1))
         * }
         * ```
         */
        public val NANOSECOND: Duration = Duration.fromNanos(1uL)

        // Also, @unstable(feature = "durationConstructors", issue = "120301")

        /**
         * The duration of one week.
         *
         * For this constant, one week is defined as 7 days, or 604,800 seconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * @feature(durationConstructors)
         * public fun doctestEntry() {
         *     val week = Duration.WEEK
         *     check(week == Duration.fromWeeks(1))
         *     check(week.asSecs() == 604800)
         *     check(week.subsecNanos() == 0)
         * }
         * ```
         */
        public val WEEK: Duration = Duration.fromWeeks(1uL)

        // Also, @unstable(feature = "durationConstructors", issue = "120301")

        /**
         * The duration of one day.
         *
         * For this constant, one day is defined as 24 hours, or 86,400 seconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * @feature(durationConstructors)
         * public fun doctestEntry() {
         *     val day = Duration.DAY
         *     check(day == Duration.fromDays(1))
         *     check(day.asSecs() == 86400)
         *     check(day.subsecNanos() == 0)
         * }
         * ```
         */
        public val DAY: Duration = Duration.fromDays(1uL)

        /**
         * The duration of one hour.
         *
         * For this constant, one hour is defined as 60 minutes, or 3,600 seconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * public fun doctestEntry() {
         *     val hour = Duration.HOUR
         *     check(hour == Duration.fromHours(1))
         *     check(hour.asSecs() == 3600)
         *     check(hour.subsecNanos() == 0)
         * }
         * ```
         */
        public val HOUR: Duration = Duration.fromHours(1uL)

        /**
         * The duration of one minute.
         *
         * For this constant, one minute is defined as 60 seconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstants)
         * public fun doctestEntry() {
         *     val minute = Duration.MINUTE
         *     check(minute == Duration.fromMins(1))
         *     check(minute.asSecs() == 60)
         *     check(minute.subsecNanos() == 0)
         * }
         * ```
         */
        public val MINUTE: Duration = Duration.fromMins(1uL)

        /**
         * A duration of zero time.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.ZERO
         * check(duration.isZero())
         * check(duration.asNanos() == 0)
         * ```
         */
        public val ZERO: Duration = Duration.fromNanos(0uL)

        /**
         * The maximum duration.
         *
         * May vary by platform as necessary. Must be able to contain the difference between
         * two instances of [Instant] or two instances of [SystemTime].
         * This constraint gives it a value of about 584,942,417,355 years in practice,
         * which is currently used on all platforms.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * check(Duration.MAX == Duration.new(ULong.MAX_VALUE, 1000000000 - 1))
         * ```
         * [Instant]: ../../std/time/struct.Instant.html
         * [SystemTime]: ../../std/time/struct.SystemTime.html
         */
        public val MAX: Duration = Duration.new(ULong.MAX_VALUE, NANOS_PER_SEC - 1u)

        /**
         * Creates a new `Duration` from the specified number of whole seconds and
         * additional nanoseconds.
         *
         * If the number of nanoseconds is greater than 1 billion (the number of
         * nanoseconds in a second), then it will carry over into the seconds provided.
         *
         * # Panics
         *
         * This constructor will panic if the carry from the nanoseconds overflows
         * the seconds counter.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val fiveSeconds = Duration.new(5, 0)
         * ```
         */
        public fun new(secs: ULong, nanos: UInt): Duration =
            if (nanos < NANOS_PER_SEC) {
                // SAFETY: nanos < NANOS_PER_SEC, therefore nanos is within the valid range
                Duration(secs = secs, nanos = Nanoseconds.newUnchecked(nanos))
            } else {
                val secs1 = (rustCheckedAdd(secs, (nanos / NANOS_PER_SEC).toULong()) ?: error("adding carry-over seconds from nanoseconds should not exceed u64::MAX"))
                val nanos1 = nanos % NANOS_PER_SEC
                // SAFETY: nanos % NANOS_PER_SEC < NANOS_PER_SEC, therefore nanos is within the valid range
                Duration(secs = secs1, nanos = Nanoseconds.newUnchecked(nanos1))
            }

        /**
         * Creates a new `Duration` from the specified number of whole seconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.fromSecs(5)
         * check(5 == duration.asSecs())
         * check(0 == duration.subsecNanos())
         * ```
         */
        public fun fromSecs(secs: ULong): Duration = Duration(secs = secs, nanos = Nanoseconds.ZERO)

        /**
         * Creates a new `Duration` from the specified number of milliseconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.fromMillis(2569)
         * check(2 == duration.asSecs())
         * check(569000000 == duration.subsecNanos())
         * ```
         */
        public fun fromMillis(millis: ULong): Duration {
            val secs = millis / MILLIS_PER_SEC
            val subsecMillis = (millis % MILLIS_PER_SEC).toUInt()
            // SAFETY: (x % 1_000) * 1_000_000 < 1_000_000_000
            // => x % 1_000 < 1_000
            val subsecNanos = Nanoseconds.newUnchecked(subsecMillis * NANOS_PER_MILLI)
            return Duration(secs = secs, nanos = subsecNanos)
        }

        /**
         * Creates a new `Duration` from the specified number of microseconds.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.fromMicros(1000002)
         * check(1 == duration.asSecs())
         * check(2000 == duration.subsecNanos())
         * ```
         */
        public fun fromMicros(micros: ULong): Duration {
            val secs = micros / MICROS_PER_SEC
            val subsecMicros = (micros % MICROS_PER_SEC).toUInt()
            // SAFETY: (x % 1_000_000) * 1_000 < 1_000_000_000
            // => x % 1_000_000 < 1_000_000
            val subsecNanos = Nanoseconds.newUnchecked(subsecMicros * NANOS_PER_MICRO)
            return Duration(secs = secs, nanos = subsecNanos)
        }

        /**
         * Creates a new `Duration` from the specified number of nanoseconds.
         *
         * Note: Using this on the return value of `asNanos()` might cause unexpected behavior:
         * `asNanos()` returns a u128, and can return values that do not fit in u64, e.g. 585 years.
         * Instead, consider using the pattern `Duration.new(d.asSecs(), d.subsecNanos())`
         * if you cannot copy/clone the Duration directly.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.fromNanos(1000000123)
         * check(1 == duration.asSecs())
         * check(123 == duration.subsecNanos())
         * ```
         */
        public fun fromNanos(nanos: ULong): Duration {
            val NANOS_PER_SEC: ULong = NANOS_PER_SEC.toULong()
            val secs = nanos / NANOS_PER_SEC.toULong()
            val subsecNanos = (nanos % NANOS_PER_SEC.toULong()).toUInt()
            // SAFETY: x % 1_000_000_000 < 1_000_000_000
            val subsecNanos1 = Nanoseconds.newUnchecked(subsecNanos)
            return Duration(secs = secs, nanos = subsecNanos1)
        }

        // for `ULong.tryFrom`

        /**
         * Creates a new `Duration` from the specified number of nanoseconds.
         *
         * # Panics
         *
         * Panics if the given number of nanoseconds is greater than [Duration.Max].
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val nanos = U128(0x0uL, 0xauL).pow(24u) + 321
         * val duration = Duration.fromNanosU128(nanos)
         * check(10uL.pow(15u) == duration.asSecs())
         * check(321 == duration.subsecNanos())
         * ```
         */
        public fun fromNanosU128(nanos: U128): Duration {
            val NANOS_PER_SEC: U128 = U128.fromUInt(NANOS_PER_SEC)
            val secs = ULong.tryFrom(nanos / NANOS_PER_SEC) ?: error("overflow in `Duration::from_nanos_u128`")
            val subsecNanos = (nanos % NANOS_PER_SEC).toUInt()
            // SAFETY: x % 1_000_000_000 < 1_000_000_000 also, subsecNanos >= 0 since u128 >=0 and u32 >=0
            val subsecNanos1 = Nanoseconds.newUnchecked(subsecNanos)
            return Duration(secs = secs.toULong(), nanos = subsecNanos1)
        }

        /**
         * Creates a new `Duration` from the specified number of weeks.
         *
         * For this function, one week is defined as 7 days, or 604,800 seconds.
         *
         * # Panics
         *
         * Panics if the given number of weeks overflows the `Duration` size.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstructors)
         * public fun doctestEntry() {
         *     val duration = Duration.fromWeeks(4)
         *     check(4 * 7 * 24 * 60 * 60 == duration.asSecs())
         *     check(0 == duration.subsecNanos())
         * }
         * ```
         */
        public fun fromWeeks(weeks: ULong): Duration {
            if (weeks > ULong.MAX_VALUE / (SECS_PER_MINUTE * MINS_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK)) {
                error("overflow in Duration::from_weeks")
            }
            return Duration.fromSecs(weeks * MINS_PER_HOUR * SECS_PER_MINUTE * HOURS_PER_DAY * DAYS_PER_WEEK)
        }

        /**
         * Creates a new `Duration` from the specified number of days.
         *
         * For this function, one day is defined as 24 hours, or 86,400 seconds.
         *
         * # Panics
         *
         * Panics if the given number of days overflows the `Duration` size.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * @feature(durationConstructors)
         * public fun doctestEntry() {
         *     val duration = Duration.fromDays(7)
         *     check(7 * 24 * 60 * 60 == duration.asSecs())
         *     check(0 == duration.subsecNanos())
         * }
         * ```
         */
        public fun fromDays(days: ULong): Duration {
            if (days > ULong.MAX_VALUE / (SECS_PER_MINUTE * MINS_PER_HOUR * HOURS_PER_DAY)) {
                error("overflow in Duration::from_days")
            }
            return Duration.fromSecs(days * MINS_PER_HOUR * SECS_PER_MINUTE * HOURS_PER_DAY)
        }

        /**
         * Creates a new `Duration` from the specified number of hours.
         *
         * For this function, one hour is defined as 60 minutes, or 3,600 seconds.
         *
         * # Panics
         *
         * Panics if the given number of hours overflows the `Duration` size.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.fromHours(6)
         * check(6 * 60 * 60 == duration.asSecs())
         * check(0 == duration.subsecNanos())
         * ```
         */
        public fun fromHours(hours: ULong): Duration {
            if (hours > ULong.MAX_VALUE / (SECS_PER_MINUTE * MINS_PER_HOUR)) {
                error("overflow in Duration::from_hours")
            }
            return Duration.fromSecs(hours * MINS_PER_HOUR * SECS_PER_MINUTE)
        }

        /**
         * Creates a new `Duration` from the specified number of minutes.
         *
         * For this function, one minute is defined as 60 seconds.
         *
         * # Panics
         *
         * Panics if the given number of minutes overflows the `Duration` size.
         *
         * # Examples
         *
         * ```kotlin
         * import std.time.Duration
         *
         * val duration = Duration.fromMins(10)
         * check(10 * 60 == duration.asSecs())
         * check(0 == duration.subsecNanos())
         * ```
         */
        public fun fromMins(mins: ULong): Duration {
            if (mins > ULong.MAX_VALUE / SECS_PER_MINUTE) {
                error("overflow in Duration::from_mins")
            }
            return Duration.fromSecs(mins * SECS_PER_MINUTE)
        }

        /**
         * Creates a new `Duration` from the specified number of seconds represented
         * as `Double`.
         *
         * # Panics
         * This constructor will panic if `secs` is negative, overflows `Duration` or not finite.
         *
         * # Examples
         * ```kotlin
         * import std.time.Duration
         *
         * val res = Duration.fromSecsF64(0.0)
         * check(res == Duration.new(0, 0))
         * val res1 = Duration.fromSecsF64(1e-20)
         * check(res1 == Duration.new(0, 0))
         * val res2 = Duration.fromSecsF64(4.2e-7)
         * check(res2 == Duration.new(0, 420))
         * val res3 = Duration.fromSecsF64(2.7)
         * check(res3 == Duration.new(2, 700000000))
         * val res4 = Duration.fromSecsF64(3e10)
         * check(res4 == Duration.new(30000000000, 0))
         * // subnormal float
         * val res5 = Duration.fromSecsF64(Double.fromBits(1uL.toLong()))
         * check(res5 == Duration.new(0, 0))
         * // conversion uses rounding
         * val res6 = Duration.fromSecsF64(0.999e-9)
         * check(res6 == Duration.new(0, 1))
         * ```
         */
        public fun fromSecsF64(secs: Double): Duration =
            run {
                val matchVal = Duration.tryFromSecsF64(secs)
                when (matchVal) { is Result.Ok<*> -> {
                    val v = matchVal.value
                    v
                }; is Result.Err<*> -> {
                    val e = matchVal.error
                    error("$e")
                } }
            }

        /**
         * Creates a new `Duration` from the specified number of seconds represented
         * as `Float`.
         *
         * # Panics
         * This constructor will panic if `secs` is negative, overflows `Duration` or not finite.
         *
         * # Examples
         * ```kotlin
         * import std.time.Duration
         *
         * val res = Duration.fromSecsF32(0.0)
         * check(res == Duration.new(0, 0))
         * val res1 = Duration.fromSecsF32(1e-20)
         * check(res1 == Duration.new(0, 0))
         * val res2 = Duration.fromSecsF32(4.2e-7)
         * check(res2 == Duration.new(0, 420))
         * val res3 = Duration.fromSecsF32(2.7)
         * check(res3 == Duration.new(2, 700000048))
         * val res4 = Duration.fromSecsF32(3e10)
         * check(res4 == Duration.new(30000001024, 0))
         * // subnormal float
         * val res5 = Duration.fromSecsF32(Float.fromBits(1u.toInt()))
         * check(res5 == Duration.new(0, 0))
         * // conversion uses rounding
         * val res6 = Duration.fromSecsF32(0.999e-9)
         * check(res6 == Duration.new(0, 1))
         * ```
         */
        public fun fromSecsF32(secs: kotlin.Float): Duration =
            run {
                val matchVal = Duration.tryFromSecsF32(secs)
                when (matchVal) { is Result.Ok<*> -> {
                    val v = matchVal.value
                    v
                }; is Result.Err<*> -> {
                    val e = matchVal.error
                    error("$e")
                } }
            }

        public fun <I : Iterator<Duration>> sum(iter: I): Duration =
            run {
                var totalSecs: ULong = 0uL
                var totalNanos: ULong = 0uL
                for (entry in iter) {
                    totalSecs = (rustCheckedAdd(totalSecs, entry.secs.toULong()) ?: error("sum over duration iterator should not overflow u64::MAX"))
                    totalNanos = when (val matchVal = rustCheckedAdd(totalNanos, entry.nanos.asInner().toULong())) {
                        null -> {
                            totalSecs = (rustCheckedAdd(totalSecs, totalNanos / NANOS_PER_SEC.toULong()) ?: error("sum over duration iterator should not overflow u64::MAX"))
                            (totalNanos % NANOS_PER_SEC.toULong()) + entry.nanos.asInner().toULong()
                        }
                        else -> {
                            val n = matchVal
                            n
                        }
                    }
                }
                totalSecs = (rustCheckedAdd(totalSecs, totalNanos / NANOS_PER_SEC.toULong()) ?: error("sum over duration iterator should not overflow u64::MAX"))
                totalNanos %= NANOS_PER_SEC.toULong()
                Duration.new(totalSecs, totalNanos.toUInt())
            }

        public fun <I : Iterator<Duration>> sumDuration(iter: I): Duration =
            run {
                var totalSecs: ULong = 0uL
                var totalNanos: ULong = 0uL
                for (entry in iter) {
                    totalSecs = (rustCheckedAdd(totalSecs, entry.secs.toULong()) ?: error("sum over duration iterator should not overflow u64::MAX"))
                    totalNanos = when (val matchVal = rustCheckedAdd(totalNanos, entry.nanos.asInner().toULong())) {
                        null -> {
                            totalSecs = (rustCheckedAdd(totalSecs, totalNanos / NANOS_PER_SEC.toULong()) ?: error("sum over duration iterator should not overflow u64::MAX"))
                            (totalNanos % NANOS_PER_SEC.toULong()) + entry.nanos.asInner().toULong()
                        }
                        else -> {
                            val n = matchVal
                            n
                        }
                    }
                }
                totalSecs = (rustCheckedAdd(totalSecs, totalNanos / NANOS_PER_SEC.toULong()) ?: error("sum over duration iterator should not overflow u64::MAX"))
                totalNanos %= NANOS_PER_SEC.toULong()
                Duration.new(totalSecs, totalNanos.toUInt())
            }

        /**
         * The checked version of [fromSecsF32].
         *
         * [fromSecsF32]: Duration.fromSecsF32
         *
         * This constructor will return an `Err` if `secs` is negative, overflows `Duration` or not finite.
         *
         * # Examples
         * ```kotlin
         * import std.time.Duration
         *
         * val res = Duration.tryFromSecsF32(0.0)
         * check(res == Result.success(Duration.new(0, 0)))
         * val res1 = Duration.tryFromSecsF32(1e-20)
         * check(res1 == Result.success(Duration.new(0, 0)))
         * val res2 = Duration.tryFromSecsF32(4.2e-7)
         * check(res2 == Result.success(Duration.new(0, 420)))
         * val res3 = Duration.tryFromSecsF32(2.7)
         * check(res3 == Result.success(Duration.new(2, 700000048)))
         * val res4 = Duration.tryFromSecsF32(3e10)
         * check(res4 == Result.success(Duration.new(30000001024, 0)))
         * // subnormal float:
         * val res5 = Duration.tryFromSecsF32(Float.fromBits(1u.toInt()))
         * check(res5 == Result.success(Duration.new(0, 0)))
         * val res6 = Duration.tryFromSecsF32(-5.0)
         * check(res6.isErr())
         * val res7 = Duration.tryFromSecsF32(Float.NaN)
         * check(res7.isErr())
         * val res8 = Duration.tryFromSecsF32(2e19)
         * check(res8.isErr())
         * // the conversion uses rounding with tie resolution to even
         * val res9 = Duration.tryFromSecsF32(0.999e-9)
         * check(res9 == Result.success(Duration.new(0, 1)))
         * // this float represents exactly 976562.5e-9
         * val value = Float.fromBits(0x3A800000u.toInt())
         * val res10 = Duration.tryFromSecsF32(value)
         * check(res10 == Result.success(Duration.new(0, 976562)))
         * // this float represents exactly 2929687.5e-9
         * val value1 = Float.fromBits(0x3B400000u.toInt())
         * val res11 = Duration.tryFromSecsF32(value1)
         * check(res11 == Result.success(Duration.new(0, 2929688)))
         * // this float represents exactly 1.000_976_562_5
         * val value2 = Float.fromBits(0x3F802000u.toInt())
         * val res12 = Duration.tryFromSecsF32(value2)
         * check(res12 == Result.success(Duration.new(1, 976562)))
         * // this float represents exactly 1.002_929_687_5
         * val value3 = Float.fromBits(0x3F806000u.toInt())
         * val res13 = Duration.tryFromSecsF32(value3)
         * check(res13 == Result.success(Duration.new(1, 2929688)))
         * ```
         */
        public fun tryFromSecsF32(secs: kotlin.Float): Result<Duration, TryFromFloatSecsError> {
            return run {
                val MIN_EXP: Short = (1 - (1 shl 8) / 2).toShort()
                val MANT_MASK: UInt = (1u shl 23) - 1u
                val EXP_MASK: UInt = (1u shl 8) - 1u
                if (secs < 0.0f) {
                    return Result.Err(TryFromFloatSecsError(kind = TryFromFloatSecsErrorKind.Negative))
                }
                val bits = secs.toRawBits().toUInt()
                val mant = (bits and MANT_MASK) or (MANT_MASK + 1u)
                val exp = ((bits shr 23) and EXP_MASK).toShort() + MIN_EXP
                val (secs3, nanos4) =
                    if (exp < (-31).toShort()) {
                        Pair(0uL, 0u)
                    } else if (exp < 0.toShort()) {
                        val t = ULong.from(mant) shl (41 + exp).toInt()
                        val nanosOffset = 23 + 41
                        val nanosTmp = U128.from(NANOS_PER_SEC) * U128.from(t)
                        val nanos = (nanosTmp shr nanosOffset.toInt()).toUInt()
                        val remMask = (1 shl nanosOffset.toInt()) - 1
                        val remMsbMask = 1 shl (nanosOffset - 1).toInt()
                        val rem = nanosTmp and remMask
                        val isTie = rem == remMsbMask
                        val isEven = (nanos and 1u) == 0u
                        val remMsb = nanosTmp and remMsbMask == 0
                        val addNs = !(remMsb || (isEven && isTie))
                        val nanos1 = nanos + addNs.toUInt()
                        if ((23 == 23) || (nanos1 != NANOS_PER_SEC)) {
                            Pair(0, nanos1)
                        } else {
                            Pair(1, 0)
                        }
                    } else if (exp < 23.toShort()) {
                        val secs1 = ULong.from(mant shr (23 - exp).toInt())
                        val t1 = ULong.from((mant shl exp.toInt()) and MANT_MASK)
                        val nanosOffset1 = 23
                        val nanosTmp1 = ULong.from(NANOS_PER_SEC) * t1
                        val nanos2 = (nanosTmp1 shr nanosOffset1.toInt()).toUInt()
                        val remMask1 = (1 shl nanosOffset1.toInt()) - 1
                        val remMsbMask1 = 1 shl (nanosOffset1 - 1).toInt()
                        val rem1 = nanosTmp1 and remMask1
                        val isTie1 = rem1 == remMsbMask1
                        val isEven1 = (nanos2 and 1u) == 0u
                        val remMsb1 = nanosTmp1 and remMsbMask1 == 0
                        val addNs1 = !(remMsb1 || (isEven1 && isTie1))
                        val nanos3 = nanos2 + addNs1.toUInt()
                        if ((23 == 23) || (nanos3 != NANOS_PER_SEC)) {
                            Pair(secs1, nanos3)
                        } else {
                            Pair(secs1 + 1, 0)
                        }
                    } else if (exp < 64.toShort()) {
                        val secs2 = ULong.from(mant) shl (exp - 23).toInt()
                        Pair(secs2, 0)
                    } else {
                        return Result.Err(TryFromFloatSecsError(kind = TryFromFloatSecsErrorKind.OverflowOrNan))
                    }
                Result.Ok(Duration.new(secs3, nanos4))
            }
        }

        /**
         * The checked version of [fromSecsF64].
         *
         * [fromSecsF64]: Duration.fromSecsF64
         *
         * This constructor will return an `Err` if `secs` is negative, overflows `Duration` or not finite.
         *
         * # Examples
         * ```kotlin
         * import std.time.Duration
         *
         * val res = Duration.tryFromSecsF64(0.0)
         * check(res == Result.success(Duration.new(0, 0)))
         * val res1 = Duration.tryFromSecsF64(1e-20)
         * check(res1 == Result.success(Duration.new(0, 0)))
         * val res2 = Duration.tryFromSecsF64(4.2e-7)
         * check(res2 == Result.success(Duration.new(0, 420)))
         * val res3 = Duration.tryFromSecsF64(2.7)
         * check(res3 == Result.success(Duration.new(2, 700000000)))
         * val res4 = Duration.tryFromSecsF64(3e10)
         * check(res4 == Result.success(Duration.new(30000000000, 0)))
         * // subnormal float
         * val res5 = Duration.tryFromSecsF64(Double.fromBits(1uL.toLong()))
         * check(res5 == Result.success(Duration.new(0, 0)))
         * val res6 = Duration.tryFromSecsF64(-5.0)
         * check(res6.isErr())
         * val res7 = Duration.tryFromSecsF64(Double.NaN)
         * check(res7.isErr())
         * val res8 = Duration.tryFromSecsF64(2e19)
         * check(res8.isErr())
         * // the conversion uses rounding with tie resolution to even
         * val res9 = Duration.tryFromSecsF64(0.999e-9)
         * check(res9 == Result.success(Duration.new(0, 1)))
         * val res10 = Duration.tryFromSecsF64(0.999999999499)
         * check(res10 == Result.success(Duration.new(0, 999999999)))
         * val res11 = Duration.tryFromSecsF64(0.999999999501)
         * check(res11 == Result.success(Duration.new(1, 0)))
         * val res12 = Duration.tryFromSecsF64(42.999999999499)
         * check(res12 == Result.success(Duration.new(42, 999999999)))
         * val res13 = Duration.tryFromSecsF64(42.999999999501)
         * check(res13 == Result.success(Duration.new(43, 0)))
         * // this float represents exactly 976562.5e-9
         * val value = Double.fromBits(0x3F50000000000000uL.toLong())
         * val res14 = Duration.tryFromSecsF64(value)
         * check(res14 == Result.success(Duration.new(0, 976562)))
         * // this float represents exactly 2929687.5e-9
         * val value1 = Double.fromBits(0x3F68000000000000uL.toLong())
         * val res15 = Duration.tryFromSecsF64(value1)
         * check(res15 == Result.success(Duration.new(0, 2929688)))
         * // this float represents exactly 1.000_976_562_5
         * val value2 = Double.fromBits(0x3FF0040000000000uL.toLong())
         * val res16 = Duration.tryFromSecsF64(value2)
         * check(res16 == Result.success(Duration.new(1, 976562)))
         * // this float represents exactly 1.002_929_687_5
         * val value3 = Double.fromBits(0x3FF00C0000000000uL.toLong())
         * val res17 = Duration.tryFromSecsF64(value3)
         * check(res17 == Result.success(Duration.new(1, 2929688)))
         * ```
         */
        public fun tryFromSecsF64(secs: Double): Result<Duration, TryFromFloatSecsError> {
            return run {
                val MIN_EXP: Short = (1 - (1 shl 11) / 2).toShort()
                val MANT_MASK: ULong = (1uL shl 52) - 1uL
                val EXP_MASK: ULong = (1uL shl 11) - 1uL
                if (secs < 0.0) {
                    return Result.Err(TryFromFloatSecsError(kind = TryFromFloatSecsErrorKind.Negative))
                }
                val bits = secs.toRawBits().toULong()
                val mant = (bits and MANT_MASK) or (MANT_MASK + 1uL)
                val exp = ((bits shr 52) and EXP_MASK).toShort() + MIN_EXP
                val (secs3, nanos4) =
                    if (exp < (-31).toShort()) {
                        Pair(0uL, 0u)
                    } else if (exp < 0.toShort()) {
                        val t = U128.from(mant) shl (44 + exp).toInt()
                        val nanosOffset = 52 + 44
                        val nanosTmp = U128.from(NANOS_PER_SEC) * U128.from(t)
                        val nanos = (nanosTmp shr nanosOffset.toInt()).toUInt()
                        val remMask = (1 shl nanosOffset.toInt()) - 1
                        val remMsbMask = 1 shl (nanosOffset - 1).toInt()
                        val rem = nanosTmp and remMask
                        val isTie = rem == remMsbMask
                        val isEven = (nanos and 1u) == 0u
                        val remMsb = nanosTmp and remMsbMask == 0
                        val addNs = !(remMsb || (isEven && isTie))
                        val nanos1 = nanos + addNs.toUInt()
                        if ((52 == 23) || (nanos1 != NANOS_PER_SEC)) {
                            Pair(0, nanos1)
                        } else {
                            Pair(1, 0)
                        }
                    } else if (exp < 52.toShort()) {
                        val secs1 = ULong.from(mant shr (52 - exp).toInt())
                        val t1 = U128.from((mant shl exp.toInt()) and MANT_MASK)
                        val nanosOffset1 = 52
                        val nanosTmp1 = U128.from(NANOS_PER_SEC) * t1
                        val nanos2 = (nanosTmp1 shr nanosOffset1.toInt()).toUInt()
                        val remMask1 = (1 shl nanosOffset1.toInt()) - 1
                        val remMsbMask1 = 1 shl (nanosOffset1 - 1).toInt()
                        val rem1 = nanosTmp1 and remMask1
                        val isTie1 = rem1 == remMsbMask1
                        val isEven1 = (nanos2 and 1u) == 0u
                        val remMsb1 = nanosTmp1 and remMsbMask1 == 0
                        val addNs1 = !(remMsb1 || (isEven1 && isTie1))
                        val nanos3 = nanos2 + addNs1.toUInt()
                        if ((52 == 23) || (nanos3 != NANOS_PER_SEC)) {
                            Pair(secs1, nanos3)
                        } else {
                            Pair(secs1 + 1, 0)
                        }
                    } else if (exp < 64.toShort()) {
                        val secs2 = ULong.from(mant) shl (exp - 52).toInt()
                        Pair(secs2, 0)
                    } else {
                        return Result.Err(TryFromFloatSecsError(kind = TryFromFloatSecsErrorKind.OverflowOrNan))
                    }
                Result.Ok(Duration.new(secs3, nanos4))
            }
        }
    }
}

public fun UInt.mul(rhs: Duration): Duration = rhs * this

/**
 * Formats a floating point number in decimal notation.
 *
 * The number is given as the `integerPart` and a fractional part.
 * The value of the fractional part is `fractionalPart / divisor`. So
 * `integerPart` = 3, `fractionalPart` = 12 and `divisor` = 100
 * represents the number `3.012`. Trailing zeros are omitted.
 *
 * `divisor` must not be above 100_000_000. It also should be a power
 * of 10, everything else doesn't make sense. `fractionalPart` has
 * to be less than `10 * divisor`!
 *
 * A prefix and postfix may be added. The whole thing is padded
 * to the formatter's `width`, if specified.
 */
public fun fmtDecimal(f: StringBuilder, integerPart: ULong, fractionalPart: UInt, divisor: UInt, prefix: String, postfix: String) {
    var fractionalPart1 = fractionalPart
    var divisor1 = divisor
    // Encode the fractional part into a temporary buffer. The buffer
    // only need to hold 9 elements, because `fractionalPart` has to
    // be smaller than 10^9. The buffer is prefilled with '0' digits
    // to simplify the code below.
    val buf = UByteArray(9) { 48u }
    // The next digit is written at this position
    var pos = 0
    // We keep writing digits into the buffer while there are non-zero
    // digits left and we haven't written enough digits yet.
    while (fractionalPart1 > 0u && pos < f.precision().unwrapOr(9)) {
        // Write new digit into the buffer
        buf[pos] = 48u + (fractionalPart1 / divisor1).toUByte()
        fractionalPart1 %= divisor1
        divisor1 /= 10u
        pos += 1
    }
    // If a precision < 9 was specified, there may be some non-zero
    // digits left that weren't written into the buffer. In that case we
    // need to perform rounding to match the semantics of printing
    // normal floating point numbers. However, we only need to do work
    // when rounding up. This happens if the first digit of the
    // remaining ones is >= 5. When the first digit is exactly 5, rounding
    // follows IEEE-754 round-ties-to-even semantics: we only round up
    // if the last written digit is odd.
    val integerPart1 =
        if (fractionalPart1 > 0u && fractionalPart1 >= divisor1 * 5u) {
            // For ties (fractionalPart == divisor * 5), only round up if last digit is odd
            val isTie = fractionalPart1 == divisor1 * 5u
            val lastDigitIsOdd =
                if (pos > 0) {
                    (buf[pos - 1] - 48u) % 2u == 1u.toUByte()
                } else {
                    // No fractional digits - check the integer part
                    (integerPart % 2uL) == 1uL
                }
            if (isTie && !lastDigitIsOdd) {
                integerPart
            } else {
                // Round up the number contained in the buffer. We go through
                // the buffer backwards and keep track of the carry.
                var revPos = pos
                var carry = true
                while (carry && revPos > 0) {
                    revPos -= 1
                    // If the digit in the buffer is not '9', we just need to
                    // increment it and can stop then (since we don't have a
                    // carry anymore). Otherwise, we set it to '0' (overflow)
                    // and continue.
                    if (buf[revPos] < 57u) {
                        buf[revPos] += 1u
                        carry = false
                    } else {
                        buf[revPos] = 48u
                    }
                }
                // If we still have the carry bit set, that means that we set
                // the whole buffer to '0's and need to increment the integer
                // part.
                if (carry) {
                    // If `integerPart == ULong.MAX` and precision < 9, any
                    // carry of the overflow during rounding of the
                    // `fractionalPart` into the `integerPart` will cause the
                    // `integerPart` itself to overflow. Avoid this by using an
                    // `Option<ULong>`, with `None` representing `ULong.MAX + 1`.
                    rustCheckedAdd(integerPart, 1uL)
                } else {
                    integerPart
                }
            }
        } else {
            integerPart
        }
    // Determine the end of the buffer: if precision is set, we just
    // use as many digits from the buffer (capped to 9). If it isn't
    // set, we only use all digits up to the last non-zero one.
    val end = f.precision().map({ p -> minOf(p, 9) }).unwrapOr(pos)
    // This closure emits the formatted duration without emitting any
    // padding (padding is calculated below).
    val emitWithoutPadding =
        closure@{ f1: StringBuilder ->
            if (integerPart1 != null) {
                val integerPart2 = integerPart1
                when (val tryResult = f1.writeFmt("$prefix$integerPart2")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
            } else {
                when (val tryResult = f1.writeFmt("${prefix}18446744073709551616")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
            }
            // Write the decimal point and the fractional part (if any).
            if (end > 0) {
                // SAFETY: We are only writing ASCII digits into the buffer and
                // it was initialized with '0's, so it contains valid UTF8.
                val s = fromUtf8Unchecked(((buf).sliceArray(0 until end)))
                // If the user request a precision > 9, we pad '0's at the end.
                when (val tryResult = f1.writeFmt(".$s")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
            }
            f1.writeFmt("$postfix")
        }
    when (val matchVal = f.width()) {
        null -> {
            // No `width` specified. There's no need to calculate the
            // length of the output in this case, just emit it.
            emitWithoutPadding(f)
        }
        else -> {
            val requestedW = matchVal
            // A `width` was specified. Calculate the actual width of
            // the output in order to calculate the required padding.
            // It consists of 4 parts:
            // 1. The prefix: is either "+" or "", so we can just use len().
            // 2. The postfix: can be "µs" so we have to count UTF8 characters.
            var actualW = prefix.length + postfix.rustStrChars().count()
            // 3. The integer part:
            if (integerPart1 != null) {
                val integerPart2 = integerPart1
                when (val matchVal1 = integerPart2.checkedIlog10()) {
                    null -> {
                        // integerPart is 0, so has length 1.
                        actualW += 1
                    }
                    else -> {
                        val log = matchVal1
                        // integerPart is > 0, so has length log10(x)+1
                        actualW += 1 + log.toInt()
                    }
                }
            } else {
                // integerPart is u64.Max + 1, so has length 20
                actualW += 20
            }
            // 4. The fractional part (if any):
            if (end > 0) {
                val fracPartW = f.precision().unwrapOr(pos)
                actualW += 1 + fracPartW
            }
            if (requestedW <= actualW) {
                // Output is already longer than `width`, so don't pad.
                emitWithoutPadding(f)
            } else {
                // We need to add padding. Use the `Formatter.padding` helper function.
                val defaultAlign = Alignment.Left
                val postPadding = f.padding((requestedW - actualW).toUShort(), defaultAlign)
                emitWithoutPadding(f)
                postPadding.write(f)
            }
        }
    }
}

/**
 * Displays `Duration` in seconds, even if it only contains the nanosecond part.
 *
 * This struct is created by [Duration.displaySeconds].
 *
 * # Examples
 * ```kotlin
 * import std.time.Duration
 *
 * @feature(durationDisplaySeconds)
 * public fun doctestEntry() {
 *     check(Duration.new(2, 0).displaySeconds().toString() == "2s")
 *     check(Duration.new(0, 2).displaySeconds().toString() == "0.000000002s")
 * }
 * ```
 */
public open class DisplaySeconds(
    public var value: Duration,
) {
    public var field0: Duration
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this).toString()); Result.Ok(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> {
        // Print leading '+' sign if requested
        val prefix =
            if (f.signPlus()) {
                "+"
            } else {
                ""
            }
        return run { fmtDecimal(f, this.value.secs, this.value.nanos.asInner(), NANOS_PER_SEC / 10u, prefix, "s"); Result.Ok(Unit) }
    }
}

/**
 * An error which can be returned when converting a floating-point value of seconds
 * into a [Duration].
 *
 * This error is used as the error type for [Duration.tryFromSecsF32] and
 * [Duration.tryFromSecsF64].
 *
 * # Example
 *
 * ```kotlin
 * import std.time.Duration
 *
 * when (val matchVal = Duration.tryFromSecsF32(-1.0)) {
 *     is Err -> {
 *         val (e) = matchVal
 *         println("Failed conversion to Duration: $e")
 *     }
 *     else -> {}
 * }
 * ```
 */
public open class TryFromFloatSecsError(
    public var kind: TryFromFloatSecsErrorKind,
) {
    public open fun clone(): TryFromFloatSecsError = TryFromFloatSecsError(kind)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this.kind) {
            TryFromFloatSecsErrorKind.Negative -> {
                "cannot convert float seconds to Duration: value is negative"
            }
            TryFromFloatSecsErrorKind.OverflowOrNan -> {
                "cannot convert float seconds to Duration: value is either too big or NaN"
            }
        }.fmt(f)
}

public sealed class TryFromFloatSecsErrorKind {
    // Value is negative.
    public data object Negative : TryFromFloatSecsErrorKind()
    // Value is either too big to be represented as `Duration` or `NaN`.
    public data object OverflowOrNan : TryFromFloatSecsErrorKind()

    public fun rustEquals(other: TryFromFloatSecsErrorKind): Boolean = this == other
}
