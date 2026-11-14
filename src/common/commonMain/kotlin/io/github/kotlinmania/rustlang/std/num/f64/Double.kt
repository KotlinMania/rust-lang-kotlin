// port-lint: source num/f64.rs
package io.github.kotlinmania.rustlang.std.num.f64

import io.github.kotlinmania.intrinsics.log
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.std.cmath.Erf
import io.github.kotlinmania.rustlang.std.sys.cmath.expm1
import io.github.kotlinmania.rustlang.std.sys.cmath.lgammaR
import io.github.kotlinmania.rustlang.std.sys.cmath.log1p
import io.github.kotlinmania.rustlang.std.sys.cmath.tgamma
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.sin

// Constants for the `Double` double-precision floating point type.
//
// *[See also the `Double` primitive type](primitive@f64).*
//
// Mathematically significant numbers are provided in the `consts` sub-module.
//
// For the constants defined directly in this module
// (as distinct from those defined in the `consts` sub-module),
// new code should instead use the associated constants
// defined directly on the `Double` type.

/**
 * Returns the largest integer that is less than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.7
 * val g = 3.0
 * val h = -3.7
 * check(f.floor() == 3.0)
 * check(g.floor() == 3.0)
 * check(h.floor() == -4.0)
 * ```
 */
public fun Double.floor(): Double = Double.floor(this)

/**
 * Returns the smallest integer that is greater than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.01
 * val g = 4.0
 * val h = -3.01
 * check(f.ceil() == 4.0)
 * check(g.ceil() == 4.0)
 * check(h.ceil() == -3.0)
 * ```
 */
public fun Double.ceil(): Double = Double.ceil(this)

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
 * val f = 3.3
 * val g = -3.3
 * val h = -3.7
 * val i = 3.5
 * val j = 4.5
 * check(f.round() == 3.0)
 * check(g.round() == -3.0)
 * check(h.round() == -4.0)
 * check(i.round() == 4.0)
 * check(j.round() == 5.0)
 * ```
 */
public fun Double.round(): Double = Double.round(this)

/**
 * Returns the nearest integer to a number. Rounds half-way cases to the number
 * with an even least significant digit.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.3
 * val g = -3.3
 * val h = 3.5
 * val i = 4.5
 * check(f.roundTiesEven() == 3.0)
 * check(g.roundTiesEven() == -3.0)
 * check(h.roundTiesEven() == 4.0)
 * check(i.roundTiesEven() == 4.0)
 * ```
 */
public fun Double.roundTiesEven(): Double = Double.roundTiesEven(this)

/**
 * Returns the integer part of `self`.
 * This means that non-integer numbers are always truncated towards zero.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 3.7
 * val g = 3.0
 * val h = -3.7
 * check(f.trunc() == 3.0)
 * check(g.trunc() == 3.0)
 * check(h.trunc() == -3.0)
 * ```
 */
public fun Double.trunc(): Double = Double.trunc(this)

/**
 * Returns the fractional part of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 3.6
 * val y = -3.6
 * val absDifferenceX = (x.fract() - 0.6).abs()
 * val absDifferenceY = (y.fract() - (-0.6)).abs()
 * check(absDifferenceX < 1e-10)
 * check(absDifferenceY < 1e-10)
 * ```
 */
public fun Double.fract(): Double = Double.fract(this)

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
 * val m = 10.0
 * val x = 4.0
 * val b = 60.0
 * check(m.mulAdd(x, b) == 100.0)
 * check(m * x + b == 100.0.toDouble())
 * val onePlusEps = 1.0 + 2.220446049250313E-16
 * val oneMinusEps = 1.0 - 2.220446049250313E-16
 * val minusOne = -1.0
 * // The exact result (1 + eps) * (1 - eps) = 1 - eps * eps.
 * check(onePlusEps.mulAdd(oneMinusEps, minusOne) == -2.220446049250313E-16 * 2.220446049250313E-16)
 * // Different rounding with the non-fused multiply and add.
 * check(onePlusEps * oneMinusEps + minusOne == 0.0.toDouble())
 * ```
 */
public fun Double.mulAdd(a: Double, b: Double): Double = Double.mulAdd(this, a, b)

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
 * val a: Double = 7.0
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
public fun Double.divEuclid(rhs: Double): Double = Double.divEuclid(this, rhs)

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
 * val a: Double = 7.0
 * val b = 4.0
 * check(a.remEuclid(b) == 3.0)
 * check((-a).remEuclid(b) == 1.0)
 * check(a.remEuclid(-b) == 3.0)
 * check((-a).remEuclid(-b) == 1.0)
 * // limitation due to round-off error
 * check((-2.220446049250313E-16).remEuclid(3.0) != 0.0)
 * ```
 */
public fun Double.remEuclid(rhs: Double): Double = Double.remEuclid(this, rhs)

/**
 * Raises a number to an integer power.
 *
 * Using this function is generally faster than using `powf`.
 * It might have a different sequence of rounding operations than `powf`,
 * so the results are not guaranteed to agree.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `Double.powi(Double.NAN, 0)` returns `1.0`. However, if an input is a *signaling*
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
 * val x = 2.0
 * val absDifference = (x.powi(2) - (x * x)).abs()
 * check(absDifference <= 1e-14)
 * check(Double.powi(Double.NaN, 0) == 1.0)
 * check(Double.powi(0.0, 0) == 1.0)
 * ```
 */
public fun Double.powi(n: Int): Double = Double.powi(this, n)

/**
 * Raises a number to a floating point power.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `Double.powf(Double.NAN, 0.0)` returns `1.0`. However, if an input is a *signaling*
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
 * val x = 2.0
 * val absDifference = (x.powf(2.0) - (x * x)).abs()
 * check(absDifference <= 1e-14)
 * check(Double.powf(1.0, Double.NaN) == 1.0)
 * check(Double.powf(Double.NaN, 0.0) == 1.0)
 * check(Double.powf(0.0, 0.0) == 1.0)
 * ```
 */
public fun Double.powf(n: Double): Double = io.github.kotlinmania.intrinsics.powf(this, n)

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
 * val positive = 4.0
 * val negative = -4.0
 * val negativeZero = -0.0
 * check(positive.sqrt() == 2.0)
 * check(negative.sqrt().isNan())
 * check(negativeZero.sqrt() == negativeZero)
 * ```
 */
public fun Double.sqrt(): Double = Double.sqrt(this)

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
 * val one = 1.0
 * // e^1
 * val e = one.exp()
 * // ln(e) - 1 == 0
 * val absDifference = (e.ln() - 1.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.exp(): Double = io.github.kotlinmania.intrinsics.exp(this)

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
 * val f = 2.0
 * // 2^2 - 4 == 0
 * val absDifference = (f.exp2() - 4.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.exp2(): Double = io.github.kotlinmania.intrinsics.exp2(this)

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
 * val one = 1.0
 * // e^1
 * val e = one.exp()
 * // ln(e) - 1 == 0
 * val absDifference = (e.ln() - 1.0).abs()
 * check(absDifference < 1e-10)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0.ln() == Double.NEGATIVE_INFINITY)
 * check((-42.0).ln().isNan())
 * ```
 */
public fun Double.ln(): Double = log(this)

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
 * val twentyFive = 25.0
 * // log5(25) - 2 == 0
 * val absDifference = (twentyFive.log(5.0) - 2.0).abs()
 * check(absDifference < 1e-10)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0.log(10.0) == Double.NEGATIVE_INFINITY)
 * check((-42.0).log(10.0).isNan())
 * ```
 */
public fun Double.log(base: Double): Double = ln(this) / ln(base)

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
 * val four = 4.0
 * // log2(4) - 2 == 0
 * val absDifference = (four.log2() - 2.0).abs()
 * check(absDifference < 1e-10)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0.log2() == Double.NEGATIVE_INFINITY)
 * check((-42.0).log2().isNan())
 * ```
 */
public fun Double.log2(): Double = io.github.kotlinmania.intrinsics.log2(this)

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
 * val hundred = 100.0
 * // log10(100) - 2 == 0
 * val absDifference = (hundred.log10() - 2.0).abs()
 * check(absDifference < 1e-10)
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * check(0.0.log10() == Double.NEGATIVE_INFINITY)
 * check((-42.0).log10().isNan())
 * ```
 */
public fun Double.log10(): Double = io.github.kotlinmania.intrinsics.log10(this)

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
 * This function currently corresponds to the `fdim` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 3.0
 * val y = -3.0
 * val absDifferenceX = (x.absSub(1.0) - 2.0).abs()
 * val absDifferenceY = (y.absSub(1.0) - 0.0).abs()
 * check(absDifferenceX < 1e-10)
 * check(absDifferenceY < 1e-10)
 * ```
 */
public fun Double.absSub(other: Double): Double = Double.absSub(this, other)

/**
 * Returns the cube root of a number.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `cbrt` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 8.0
 * // x^(1/3) - 2 == 0
 * val absDifference = (x.cbrt() - 2.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.cbrt(): Double = Double.cbrt(this)

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
 * This function currently corresponds to the `hypot` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 2.0
 * val y = 3.0
 * // sqrt(x^2 + y^2)
 * val absDifference = (x.hypot(y) - (x.powi(2) + y.powi(2)).sqrt()).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.hypot(other: Double): Double = io.github.kotlinmania.rustlang.std.sys.cmath.hypot(this, other)

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
 * val x = 1.5707963267948966
 * val absDifference = (x.sin() - 1.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.sin(): Double = io.github.kotlinmania.intrinsics.sin(this)

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
 * val x = 2.0 * kotlin.math.PI
 * val absDifference = (x.cos() - 1.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.cos(): Double = io.github.kotlinmania.intrinsics.cos(this)

/**
 * Computes the tangent of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `tan` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 0.7853981633974483
 * val absDifference = (x.tan() - 1.0).abs()
 * check(absDifference < 1e-14)
 * ```
 */
public fun Double.tan(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.tan(this)

/**
 * Computes the arcsine of a number. Return value is in radians in
 * the range [-pi/2, pi/2] or NaN if the number is outside the range
 * [-1, 1].
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `asin` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 0.7853981633974483
 * // asin(sin(pi/2))
 * val absDifference = (f.sin().asin() - f).abs()
 * check(absDifference < 1e-14)
 * ```
 */
public fun Double.asin(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.asin(this)

/**
 * Computes the arccosine of a number. Return value is in radians in
 * the range [0, pi] or NaN if the number is outside the range
 * [-1, 1].
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `acos` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 0.7853981633974483
 * // acos(cos(pi/4))
 * val absDifference = (f.cos().acos() - 0.7853981633974483).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.acos(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.acos(this)

/**
 * Computes the arctangent of a number. Return value is in radians in the
 * range [-pi/2, pi/2];
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `atan` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val f = 1.0
 * // atan(tan(1))
 * val absDifference = (f.tan().atan() - 1.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.atan(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.atan(this)

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
 * This function currently corresponds to the `atan2` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * // Positive angles measured counter-clockwise
 * // from positive x axis
 * // -pi/4 radians (45 deg clockwise)
 * val x1 = 3.0
 * val y1 = -3.0
 * // 3pi/4 radians (135 deg counter-clockwise)
 * val x2 = -3.0
 * val y2 = 3.0
 * val absDifference1 = (y1.atan2(x1) - (-0.7853981633974483)).abs()
 * val absDifference2 = (y2.atan2(x2) - (3.0 * 0.7853981633974483)).abs()
 * check(absDifference1 < 1e-10)
 * check(absDifference2 < 1e-10)
 * ```
 */
public fun Double.atan2(other: Double): Double = io.github.kotlinmania.rustlang.std.sys.cmath.atan2(this, other)

/**
 * Simultaneously computes the sine and cosine of the number, `x`. Returns
 * `(sin(x), cos(x))`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `(f64.sin(x),
 * f64.cos(x))`. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 0.7853981633974483
 * val f = x.sinCos()
 * val absDifference0 = (f.first - x.sin()).abs()
 * val absDifference1 = (f.second - x.cos()).abs()
 * check(absDifference0 < 1e-10)
 * check(absDifference1 < 1e-10)
 * ```
 */
public fun Double.sinCos(): Pair<Double, Double> = Pair(sin(this), cos(this))

/**
 * Returns `e ^(self) - 1` in a way that is accurate even if the
 * number is close to zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `expm1` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1e-16
 * // for very small x, e^x is approximately 1 + x + x^2 / 2
 * val approx = x + x * x / 2.0
 * val absDifference = (x.expM1() - approx).abs()
 * check(absDifference < 1e-20)
 * ```
 */
public fun Double.expM1(): Double = expm1(this)

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
 * This function currently corresponds to the `log1p` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 1e-16
 * // for very small x, ln(1 + x) is approximately x - x^2 / 2
 * val approx = x - x * x / 2.0
 * val absDifference = (x.ln1p() - approx).abs()
 * check(absDifference < 1e-20)
 * ```
 *
 * Out-of-range values:
 * ```kotlin
 * check((-1.0).ln1p() == Double.NEGATIVE_INFINITY)
 * check((-2.0).ln1p().isNan())
 * ```
 */
public fun Double.ln1p(): Double = log1p(this)

/**
 * Hyperbolic sine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `sinh` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val e = kotlin.math.E
 * val x = 1.0
 * val f = x.sinh()
 * // Solving sinh() at 1 gives `(e ^ 2 - 1) / (2e)`
 * val g = ((e * e) - 1.0) / (2.0 * e)
 * val absDifference = (f - g).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.sinh(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.sinh(this)

/**
 * Hyperbolic cosine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `cosh` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val e = kotlin.math.E
 * val x = 1.0
 * val f = x.cosh()
 * // Solving cosh() at 1 gives this result
 * val g = ((e * e) + 1.0) / (2.0 * e)
 * val absDifference = (f - g).abs()
 * // Same result
 * check(absDifference < 1.0e-10)
 * ```
 */
public fun Double.cosh(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.cosh(this)

/**
 * Hyperbolic tangent function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `tanh` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * val e = kotlin.math.E
 * val x = 1.0
 * val f = x.tanh()
 * // Solving tanh() at 1 gives `(1 - e ^(- 2)) / (1 + e ^(- 2))`
 * val g = (1.0 - e.powi(-2)) / (1.0 + e.powi(-2))
 * val absDifference = (f - g).abs()
 * check(absDifference < 1.0e-10)
 * ```
 */
public fun Double.tanh(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.tanh(this)

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
 * val x = 1.0
 * val f = x.sinh().asinh()
 * val absDifference = (f - x).abs()
 * check(absDifference < 1.0e-10)
 * ```
 */
public fun Double.asinh(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.asinh(this)

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
 * val x = 1.0
 * val f = x.cosh().acosh()
 * val absDifference = (f - x).abs()
 * check(absDifference < 1.0e-10)
 * ```
 */
public fun Double.acosh(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.acosh(this)

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
 * val x = 0.5235987755982989
 * val f = x.tanh().atanh()
 * val absDifference = (f - x).abs()
 * check(absDifference < 1.0e-10)
 * ```
 */
public fun Double.atanh(): Double = 0.5 * ((2.0 * this) / (1.0 - this)).ln1p()

/**
 * Gamma function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform, Rust version, and
 * can even differ within the same execution from one invocation to the next.
 * This function currently corresponds to the `tgamma` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatGamma)
 * public fun doctestEntry() {
 *     val x = 5.0
 *     val absDifference = (x.gamma() - 24.0).abs()
 *     check(absDifference <= 1e-10)
 * }
 * ```
 */
public fun Double.gamma(): Double = tgamma(this)

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
 *     val x = 2.0
 *     val absDifference = (x.lnGamma().first - 0.0).abs()
 *     check(absDifference <= 2.220446049250313E-16)
 * }
 * ```
 */
public fun Double.lnGamma(): Pair<Double, Int> {
    val localStorageL1210C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    val signgamp by localStorageL1210C8
    val x = lgammaR(this, RustMutableReference(localStorageL1210C8.asMutPtr(), localStorageL1210C8.layout))
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
 * This function currently corresponds to the `erf` from libc on Unix
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
 *     fun withinStandardDeviations(x: Double): Double {
 *         return (x * 0.7071067811865476).erf() * 100.0
 *     }
 *     // 68% of a normal distribution is within one standard deviation
 *     check((withinStandardDeviations(1.0) - 68.269).abs() < 0.01)
 *     // 95% of a normal distribution is within two standard deviations
 *     check((withinStandardDeviations(2.0) - 95.450).abs() < 0.01)
 *     // 99.7% of a normal distribution is within three standard deviations
 *     check((withinStandardDeviations(3.0) - 99.730).abs() < 0.01)
 * }
 * ```
 */
public fun Double.erf(): Double = Erf(this)

/**
 * Complementary error function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `erfc` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatErf)
 * public fun doctestEntry() {
 *     val x: Double = 0.123
 *     val one = x.erf() + x.erfc()
 *     val absDifference = (one - 1.0).abs()
 *     check(absDifference <= 1e-10)
 * }
 * ```
 */
public fun Double.erfc(): Double = io.github.kotlinmania.rustlang.std.sys.cmath.erfc(this)
