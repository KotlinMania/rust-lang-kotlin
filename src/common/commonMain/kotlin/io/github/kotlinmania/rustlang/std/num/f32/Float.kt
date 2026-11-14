// port-lint: source num/f32.rs
package io.github.kotlinmania.rustlang.std.num.f32

import io.github.kotlinmania.intrinsics.log
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.std.cmath.acosf
import io.github.kotlinmania.rustlang.std.cmath.acoshf
import io.github.kotlinmania.rustlang.std.cmath.asinf
import io.github.kotlinmania.rustlang.std.cmath.asinhf
import io.github.kotlinmania.rustlang.std.cmath.atan2f
import io.github.kotlinmania.rustlang.std.cmath.atanf
import io.github.kotlinmania.rustlang.std.cmath.coshf
import io.github.kotlinmania.rustlang.std.cmath.sinhf
import io.github.kotlinmania.rustlang.std.cmath.tanf
import io.github.kotlinmania.rustlang.std.cmath.tanhf
import io.github.kotlinmania.rustlang.std.intrinsics
import io.github.kotlinmania.rustlang.std.sys.cmath.erfcf
import io.github.kotlinmania.rustlang.std.sys.cmath.erff
import io.github.kotlinmania.rustlang.std.sys.cmath.expm1f
import io.github.kotlinmania.rustlang.std.sys.cmath.hypotf
import io.github.kotlinmania.rustlang.std.sys.cmath.lgammafR
import io.github.kotlinmania.rustlang.std.sys.cmath.log1pf
import io.github.kotlinmania.rustlang.std.sys.cmath.tgammaf
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.sin

// Constants for the `Float` single-precision floating point type.
//
// *[See also the `Float` primitive type](primitive@f32).*
//
// Mathematically significant numbers are provided in the `consts` sub-module.
//
// For the constants defined directly in this module
// (as distinct from those defined in the `consts` sub-module),
// new code should instead use the associated constants
// defined directly on the `Float` type.

/**
 * Returns the largest integer that is less than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.7f
 * val g = 3.0f
 * val h = -3.7f
 * check(f.floor() == 3.0)
 * check(g.floor() == 3.0)
 * check(h.floor() == -4.0)
 * ```
 */
public fun Float.floor(): Float = Float.floor(this)

/**
 * Returns the smallest integer that is greater than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.01f
 * val g = 4.0f
 * val h = -3.01f
 * check(f.ceil() == 4.0)
 * check(g.ceil() == 4.0)
 * check(h.ceil() == -3.0)
 * ```
 */
public fun Float.ceil(): Float = Float.ceil(this)

/**
 * Returns the nearest integer to `self`. If a value is half-way between two
 * integers, round away from `0.0`.
 *
 * This function always returns the precise result.
 *
 * On most hardware platforms, [`roundTiesEven`](Self.roundTiesEven) may execute faster
 * than `round`. If both rounding methods fit the use case, consider using `roundTiesEven`.
 * Note that the two methods apply different rounding rules to values exactly halfway between
 * two integers.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.3f
 * val g = -3.3f
 * val h = -3.7f
 * val i = 3.5f
 * val j = 4.5f
 * check(f.round() == 3.0)
 * check(g.round() == -3.0)
 * check(h.round() == -4.0)
 * check(i.round() == 4.0)
 * check(j.round() == 5.0)
 * ```
 */
public fun Float.round(): Float = Float.round(this)

/**
 * Returns the nearest integer to a number. Rounds half-way cases to the number
 * with an even least significant digit.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.3f
 * val g = -3.3f
 * val h = 3.5f
 * val i = 4.5f
 * check(f.roundTiesEven() == 3.0)
 * check(g.roundTiesEven() == -3.0)
 * check(h.roundTiesEven() == 4.0)
 * check(i.roundTiesEven() == 4.0)
 * ```
 */
public fun Float.roundTiesEven(): Float = Float.roundTiesEven(this)

/**
 * Returns the integer part of `self`.
 * This means that non-integer numbers are always truncated towards zero.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.7f
 * val g = 3.0f
 * val h = -3.7f
 * check(f.trunc() == 3.0)
 * check(g.trunc() == 3.0)
 * check(h.trunc() == -3.0)
 * ```
 */
public fun Float.trunc(): Float = Float.trunc(this)

/**
 * Returns the fractional part of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 3.6f
 * val y = -3.6f
 * val absDifferenceX = (x.fract() - 0.6).abs()
 * val absDifferenceY = (y.fract() - (-0.6)).abs()
 * check(absDifferenceX <= 1.1920929E-7f)
 * check(absDifferenceY <= 1.1920929E-7f)
 * ```
 */
public fun Float.fract(): Float = Float.fract(this)

/**
 * Fused multiply-add. Computes `(self * a) + b` with only one rounding
 * error, yielding a more accurate result than an unfused multiply-add.
 *
 * Using `mulAdd` *may* be more performant than an unfused multiply-add if
 * the target architecture has a dedicated `fma` CPU instruction. However,
 * this is not always true, and will be heavily dependant on designing
 * algorithms with specific target hardware in mind.
 *
 * # Precision
 *
 * The result of this operation is guaranteed to be the rounded
 * infinite-precision result. It is specified by IEEE 754 as
 * `fusedMultiplyAdd` and guaranteed not to change.
 *
 * # Examples
 *
 * ```kotlin
 * val m = 10.0f
 * val x = 4.0f
 * val b = 60.0f
 * check(m.mulAdd(x, b) == 100.0)
 * check(m * x + b == 100.0f.toFloat())
 * val onePlusEps = 1.0f + 1.1920929E-7f
 * val oneMinusEps = 1.0f - 1.1920929E-7f
 * val minusOne = -1.0f
 * // The exact result (1 + eps) * (1 - eps) = 1 - eps * eps.
 * check(onePlusEps.mulAdd(oneMinusEps, minusOne) == -1.1920929E-7f * 1.1920929E-7f)
 * // Different rounding with the non-fused multiply and add.
 * check(onePlusEps * oneMinusEps + minusOne == 0.0f.toFloat())
 * ```
 */
public fun Float.mulAdd(a: Float, b: Float): Float = Float.mulAdd(this, a, b)

/**
 * Calculates Euclidean division, the matching method for `remEuclid`.
 *
 * This computes the integer `n` such that
 * `self = n * rhs + self.remEuclid(rhs)`.
 * In other words, the result is `self / rhs` rounded to the integer `n`
 * such that `self >= n * rhs`.
 *
 * # Precision
 *
 * The result of this operation is guaranteed to be the rounded
 * infinite-precision result.
 *
 * # Examples
 *
 * ```kotlin
 * val a: Float = 7.0f
 * val b = 4.0
 * check(a.divEuclid(b) == 1.0)
 * // 7.0 > 4.0 * 1.0
 * check((-a).divEuclid(b) == -2.0)
 * // -7.0 >= 4.0 * -2.0
 * check(a.divEuclid(-b) == -1.0)
 * // 7.0 >= -4.0 * -1.0
 * check((-a).divEuclid(-b) == 2.0)
 * // -7.0 >= -4.0 * 2.0
 * ```
 */
public fun Float.divEuclid(rhs: Float): Float = Float.divEuclid(this, rhs)

/**
 * Calculates the least nonnegative remainder of `self` when divided by
 * `rhs`.
 *
 * In particular, the return value `r` satisfies `0.0 <= r<rhs.abs()` in
 * most cases. However, due to a floating point round-off error it can
 * result in `r == rhs.abs()`, violating the mathematical definition, if
 * `self` is much smaller than `rhs.abs()` in magnitude and `self<0.0`.
 * This result is not an element of the function's codomain, but it is the
 * closest floating point number in the real numbers and thus fulfills the
 * property `self == self.divEuclid(rhs) * rhs + self.remEuclid(rhs)`
 * approximately.
 *
 * # Precision
 *
 * The result of this operation is guaranteed to be the rounded
 * infinite-precision result.
 *
 * # Examples
 *
 * ```kotlin
 * val a: Float = 7.0f
 * val b = 4.0
 * check(a.remEuclid(b) == 3.0)
 * check((-a).remEuclid(b) == 1.0)
 * check(a.remEuclid(-b) == 3.0)
 * check((-a).remEuclid(-b) == 1.0)
 * // limitation due to round-off error
 * check((-1.1920929E-7f).remEuclid(3.0f) != 0.0)
 * ```
 */
public fun Float.remEuclid(rhs: Float): Float = Float.remEuclid(this, rhs)

/**
 * Raises a number to an integer power.
 *
 * Using this function is generally faster than using `powf`.
 * It might have a different sequence of rounding operations than `powf`,
 * so the results are not guaranteed to agree.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `Float.powi(Float.NAN, 0)` returns `1.0`. However, if an input is a *signaling*
 * NaN, then the result is non-deterministically either a NaN or the result that the
 * corresponding quiet NaN would produce.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 2.0f
 * val absDifference = (x.powi(2) - (x * x)).abs()
 * check(absDifference <= 1e-5)
 * check(Float.powi(Float.NaN, 0) == 1.0)
 * check(Float.powi(0.0, 0) == 1.0)
 * ```
 */
public fun Float.powi(n: Int): Float = Float.powi(this, n)

/**
 * Raises a number to a floating point power.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `Float.powf(Float.NAN, 0.0)` returns `1.0`. However, if an input is a *signaling*
 * NaN, then the result is non-deterministically either a NaN or the result that the
 * corresponding quiet NaN would produce.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 2.0f
 * val absDifference = (x.powf(2.0f) - (x * x)).abs()
 * check(absDifference <= 1e-5)
 * check(Float.powf(1.0, Float.NaN) == 1.0)
 * check(Float.powf(Float.NaN, 0.0) == 1.0)
 * check(Float.powf(0.0, 0.0) == 1.0)
 * ```
 */
public fun Float.powf(n: Float): Float = io.github.kotlinmania.intrinsics.powf(this, n)

/**
 * Returns the square root of a number.
 *
 * Returns NaN if `self` is a negative number other than `- 0.0`.
 *
 * # Precision
 *
 * The result of this operation is guaranteed to be the rounded
 * infinite-precision result. It is specified by IEEE 754 as `squareRoot`
 * and guaranteed not to change.
 *
 * # Examples
 *
 * ```kotlin
 * val positive = 4.0f
 * val negative = -4.0f
 * val negativeZero = -0.0f
 * check(positive.sqrt() == 2.0)
 * check(negative.sqrt().isNan())
 * check(negativeZero.sqrt() == negativeZero)
 * ```
 */
public fun Float.sqrt(): Float = Float.sqrt(this)

/**
 * Returns `e ^(self)`, (the exponential function).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val one = 1.0f
 * // e^1
 * val e = one.exp()
 * // ln(e) - 1 == 0
 * val absDifference = (e.ln() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.exp(): Float = io.github.kotlinmania.intrinsics.exp(this)

/**
 * Returns `2 ^(self)`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 2.0f
 * // 2^2 - 4 == 0
 * val absDifference = (f.exp2() - 4.0).abs()
 * check(absDifference <= 1e-5)
 * ```
 */
public fun Float.exp2(): Float = io.github.kotlinmania.intrinsics.exp2(this)

/**
 * Returns the natural logarithm of the number.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val one = 1.0f
 * // e^1
 * val e = one.exp()
 * // ln(e) - 1 == 0
 * val absDifference = (e.ln() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0f.ln() == Float.NEGATIVE_INFINITY)
 * check((-42.0f).ln().isNan())
 * ```
 */
public fun Float.ln(): Float = log(this)

/**
 * Returns the logarithm of the number with respect to an arbitrary base.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * The result might not be correctly rounded owing to implementation details;
 * `self.log2()` can produce more accurate results for base 2, and
 * `self.log10()` can produce more accurate results for base 10.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5.0f
 * // log5(5) - 1 == 0
 * val absDifference = (five.log(5.0f) - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0f.log(10.0) == Float.NEGATIVE_INFINITY)
 * check((-42.0f).log(10.0).isNan())
 * ```
 */
public fun Float.log(base: Float): Float = ln(this) / ln(base)

/**
 * Returns the base 2 logarithm of the number.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val two = 2.0f
 * // log2(2) - 1 == 0
 * val absDifference = (two.log2() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0f.log2() == Float.NEGATIVE_INFINITY)
 * check((-42.0f).log2().isNan())
 * ```
 */
public fun Float.log2(): Float = io.github.kotlinmania.intrinsics.log2(this)

/**
 * Returns the base 10 logarithm of the number.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val ten = 10.0f
 * // log10(10) - 1 == 0
 * val absDifference = (ten.log10() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0f.log10() == Float.NEGATIVE_INFINITY)
 * check((-42.0f).log10().isNan())
 * ```
 */
public fun Float.log10(): Float = io.github.kotlinmania.intrinsics.log10(this)

/**
 * The positive difference of two numbers.
 *
 * * If `self <= other`: `0.0`
 * * Else: `self - other`
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `fdimf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 3.0f
 * val y = -3.0f
 * val absDifferenceX = (x.absSub(1.0) - 2.0).abs()
 * val absDifferenceY = (y.absSub(1.0) - 0.0).abs()
 * check(absDifferenceX <= 1e-6)
 * check(absDifferenceY <= 1e-6)
 * ```
 */
public fun Float.absSub(other: Float): Float = Float.absSub(this, other)

/**
 * Returns the cube root of a number.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `cbrtf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 8.0f
 * // x^(1/3) - 2 == 0
 * val absDifference = (x.cbrt() - 2.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.cbrt(): Float = Float.cbrt(this)

/**
 * Compute the distance between the origin and a point (`x`, `y`) on the
 * Euclidean plane. Equivalently, compute the length of the hypotenuse of a
 * right-angle triangle with other sides having length `x.abs()` and
 * `y.abs()`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `hypotf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 2.0f
 * val y = 3.0f
 * // sqrt(x^2 + y^2)
 * val absDifference = (x.hypot(y) - (x.powi(2) + y.powi(2)).sqrt()).abs()
 * check(absDifference <= 1e-5)
 * ```
 */
public fun Float.hypot(other: Float): Float = hypotf(this, other)

/**
 * Computes the sine of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1.5707964f
 * val absDifference = (x.sin() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.sin(): Float = io.github.kotlinmania.intrinsics.sin(this)

/**
 * Computes the cosine of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 2.0f * 3.1415927f
 * val absDifference = (x.cos() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.cos(): Float = io.github.kotlinmania.intrinsics.cos(this)

/**
 * Computes the tangent of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `tanf` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 0.7853982f
 * val absDifference = (x.tan() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.tan(): Float = tanf(this)

/**
 * Computes the arcsine of a number. Return value is in radians in
 * the range [-pi/2, pi/2] or NaN if the number is outside the range
 * [-1, 1].
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `asinf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 0.7853982f
 * // asin(sin(pi/2))
 * val absDifference = (f.sin().asin() - f).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.asin(): Float = asinf(this)

/**
 * Computes the arccosine of a number. Return value is in radians in
 * the range [0, pi] or NaN if the number is outside the range
 * [-1, 1].
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `acosf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 0.7853982f
 * // acos(cos(pi/4))
 * val absDifference = (f.cos().acos() - 0.7853982f).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.acos(): Float = acosf(this)

/**
 * Computes the arctangent of a number. Return value is in radians in the
 * range [-pi/2, pi/2];
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `atanf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 1.0f
 * // atan(tan(1))
 * val absDifference = (f.tan().atan() - 1.0).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.atan(): Float = atanf(this)

/**
 * Computes the four quadrant arctangent of `self` (`y`) and `other` (`x`) in radians.
 *
 *  | `x`     | `y`     | Piecewise Definition | Range         |
 *  |---------|---------|----------------------|---------------|
 *  | `>= + 0` | `>= + 0` | `arctan(y / x)`        | `[+ 0, + pi / 2]` |
 *  | `>= + 0` | `<= - 0` | `arctan(y / x)`        | `[- pi / 2, - 0]` |
 *  | `<= - 0` | `>= + 0` | `arctan(y / x) + pi`   | `[+ pi / 2, + pi]`|
 *  | `<= - 0` | `<= - 0` | `arctan(y / x) - pi`   | `[- pi, - pi / 2]`|
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `atan2f` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * // Positive angles measured counter-clockwise
 * // from positive x axis
 * // -pi/4 radians (45 deg clockwise)
 * val x1 = 3.0f
 * val y1 = -3.0f
 * // 3pi/4 radians (135 deg counter-clockwise)
 * val x2 = -3.0f
 * val y2 = 3.0f
 * val absDifference1 = (y1.atan2(x1) - (-0.7853982f)).abs()
 * val absDifference2 = (y2.atan2(x2) - (3.0f * 0.7853982f)).abs()
 * check(absDifference1 <= 1e-5)
 * check(absDifference2 <= 1e-5)
 * ```
 */
public fun Float.atan2(other: Float): Float = atan2f(this, other)

/**
 * Simultaneously computes the sine and cosine of the number, `x`. Returns
 * `(sin(x), cos(x))`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `(f32.sin(x),
 * f32.cos(x))`. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 0.7853982f
 * val f = x.sinCos()
 * val absDifference0 = (f.first - x.sin()).abs()
 * val absDifference1 = (f.second - x.cos()).abs()
 * check(absDifference0 <= 1e-4)
 * check(absDifference1 <= 1e-4)
 * ```
 */
public fun Float.sinCos(): Pair<Float, Float> = Pair(sin(this), cos(this))

/**
 * Returns `e ^(self) - 1` in a way that is accurate even if the
 * number is close to zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `expm1f` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1e-8f
 * // for very small x, e^x is approximately 1 + x + x^2 / 2
 * val approx = x + x * x / 2.0f
 * val absDifference = (x.expM1() - approx).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Float.expM1(): Float = expm1f(this)

/**
 * Returns `ln(1 + n)` (natural logarithm) more accurately than if
 * the operations were performed separately.
 *
 * This returns NaN when `n< - 1.0`, and negative infinity when `n == - 1.0`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `log1pf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1e-8f
 * // for very small x, ln(1 + x) is approximately x - x^2 / 2
 * val approx = x - x * x / 2.0f
 * val absDifference = (x.ln1p() - approx).abs()
 * check(absDifference < 1e-10)
 * ```
 *
 * Out-of-range values:
 * ```kotlin
 * check((-1.0f).ln1p() == Float.NEGATIVE_INFINITY)
 * check((-2.0f).ln1p().isNan())
 * ```
 */
public fun Float.ln1p(): Float = log1pf(this)

/**
 * Hyperbolic sine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `sinhf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val e = 2.7182817f
 * val x = 1.0f
 * val f = x.sinh()
 * // Solving sinh() at 1 gives `(e ^ 2 - 1) / (2e)`
 * val g = ((e * e) - 1.0f) / (2.0f * e)
 * val absDifference = (f - g).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.sinh(): Float = sinhf(this)

/**
 * Hyperbolic cosine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `coshf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val e = 2.7182817f
 * val x = 1.0f
 * val f = x.cosh()
 * // Solving cosh() at 1 gives this result
 * val g = ((e * e) + 1.0f) / (2.0f * e)
 * val absDifference = (f - g).abs()
 * // Same result
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.cosh(): Float = coshf(this)

/**
 * Hyperbolic tangent function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `tanhf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val e = 2.7182817f
 * val x = 1.0f
 * val f = x.tanh()
 * // Solving tanh() at 1 gives `(1 - e ^(- 2)) / (1 + e ^(- 2))`
 * val g = (1.0 - e.powi(-2)) / (1.0 + e.powi(-2))
 * val absDifference = (f - g).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.tanh(): Float = tanhf(this)

/**
 * Inverse hyperbolic sine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1.0f
 * val f = x.sinh().asinh()
 * val absDifference = (f - x).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.asinh(): Float = asinhf(this)

/**
 * Inverse hyperbolic cosine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1.0f
 * val f = x.cosh().acosh()
 * val absDifference = (f - x).abs()
 * check(absDifference <= 1e-6)
 * ```
 */
public fun Float.acosh(): Float = acoshf(this)

/**
 * Inverse hyperbolic tangent function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 0.5235988f
 * val f = x.tanh().atanh()
 * val absDifference = (f - x).abs()
 * check(absDifference <= 1e-5)
 * ```
 */
public fun Float.atanh(): Float = 0.5f * ((2.0f * this) / (1.0f - this)).ln1p()

/**
 * Gamma function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `tgammaf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatGamma)
 * public fun doctestEntry() {
 *     val x = 5.0f
 *     val absDifference = (x.gamma() - 24.0).abs()
 *     check(absDifference <= 1e-5)
 * }
 * ```
 */
public fun Float.gamma(): Float = tgammaf(this)

/**
 * Natural logarithm of the absolute value of the gamma function
 *
 * The integer part of the tuple indicates the sign of the gamma function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `lgammaR` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatGamma)
 * public fun doctestEntry() {
 *     val x = 2.0f
 *     val absDifference = (x.lnGamma().first - 0.0).abs()
 *     check(absDifference <= 1.1920929E-7f)
 * }
 * ```
 */
public fun Float.lnGamma(): Pair<Float, Int> {
    val localStorageL1210C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    val signgamp by localStorageL1210C8
    val x = lgammafR(this, RustMutableReference(localStorageL1210C8.asMutPtr(), localStorageL1210C8.layout))
    return Pair(x, signgamp)
}

/**
 * Error function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `erff` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatErf)
 * public fun doctestEntry() {
 *     / **
 *      * The error function relates what percent of a normal distribution lies
 *      * within `x` standard deviations (scaled by `1 / sqrt(2)`).
 *      * /
 *     fun withinStandardDeviations(x: Float): Float {
 *         return (x * 0.70710677f).erf() * 100.0f
 *     }
 *     // 68% of a normal distribution is within one standard deviation
 *     check((withinStandardDeviations(1.0f) - 68.269).abs() < 0.01)
 *     // 95% of a normal distribution is within two standard deviations
 *     check((withinStandardDeviations(2.0f) - 95.450).abs() < 0.01)
 *     // 99.7% of a normal distribution is within three standard deviations
 *     check((withinStandardDeviations(3.0f) - 99.730).abs() < 0.01)
 * }
 * ```
 */
public fun Float.erf(): Float = erff(this)

/**
 * Complementary error function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `erfcf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatErf)
 * public fun doctestEntry() {
 *     val x: Float = 0.123f
 *     val one = x.erf() + x.erfc()
 *     val absDifference = (one - 1.0).abs()
 *     check(absDifference <= 1e-6)
 * }
 * ```
 */
public fun Float.erfc(): Float = erfcf(this)
