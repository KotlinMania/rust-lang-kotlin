// port-lint: source num/f128.rs
package io.github.kotlinmania.rustlang.std.num.f128

import io.github.kotlinmania.intrinsics.log
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.std.sys.cmath.acosf128
import io.github.kotlinmania.rustlang.std.sys.cmath.acoshf128
import io.github.kotlinmania.rustlang.std.sys.cmath.asinf128
import io.github.kotlinmania.rustlang.std.sys.cmath.asinhf128
import io.github.kotlinmania.rustlang.std.sys.cmath.atan2f128
import io.github.kotlinmania.rustlang.std.sys.cmath.atanf128
import io.github.kotlinmania.rustlang.std.sys.cmath.cbrtf128
import io.github.kotlinmania.rustlang.std.sys.cmath.coshf128
import io.github.kotlinmania.rustlang.std.sys.cmath.erfcf128
import io.github.kotlinmania.rustlang.std.sys.cmath.erff128
import io.github.kotlinmania.rustlang.std.sys.cmath.expm1f128
import io.github.kotlinmania.rustlang.std.sys.cmath.hypotf128
import io.github.kotlinmania.rustlang.std.sys.cmath.lgammaf128R
import io.github.kotlinmania.rustlang.std.sys.cmath.log1pf128
import io.github.kotlinmania.rustlang.std.sys.cmath.sinhf128
import io.github.kotlinmania.rustlang.std.sys.cmath.tanf128
import io.github.kotlinmania.rustlang.std.sys.cmath.tanhf128
import io.github.kotlinmania.rustlang.std.sys.cmath.tgammaf128

// Constants for the `f128` quadruple-precision floating point type.
//
// *[See also the `f128` primitive type](primitive@f128).*
//
// Mathematically significant numbers are provided in the `consts` sub-module.

/**
 * Raises a number to a floating point power.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `f128.powf(f128.NAN, 0.0)` returns `1.0`. However, if an input is a *signaling*
 * NaN, then the result is non-deterministically either a NaN or the result that the
 * corresponding quiet NaN would produce.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val absDifference = (x.powf(2.0) - (x * x)).abs()
 *     check(absDifference <= EPSILON)
 *     check(powf(1.0, NAN) == 1.0)
 *     check(powf(NAN, 0.0) == 1.0)
 *     check(powf(0.0, 0.0) == 1.0)
 * }
 * ```
 */
public fun F128.powf(n: F128): F128 = io.github.kotlinmania.intrinsics.powf(this, n)

/**
 * Returns `e ^(self)`, (the exponential function).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val one = 1.0
 *     // e^1
 *     val e = one.exp()
 *     // ln(e) - 1 == 0
 *     val absDifference = (e.ln() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.exp(): F128 = io.github.kotlinmania.intrinsics.exp(this)

/**
 * Returns `2 ^(self)`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 2.0
 *     // 2^2 - 4 == 0
 *     val absDifference = (f.exp2() - 4.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.exp2(): F128 = io.github.kotlinmania.intrinsics.exp2(this)

/**
 * Returns the natural logarithm of the number.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val one = 1.0
 *     // e^1
 *     val e = one.exp()
 *     // ln(e) - 1 == 0
 *     val absDifference = (e.ln() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check(0.ln() == NEG_INFINITY)
 *     check((-42).ln().isNan())
 * }
 * ```
 */
public fun F128.ln(): F128 = log(this)

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
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val five = 5.0
 *     // log5(5) - 1 == 0
 *     val absDifference = (five.log(5.0) - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check(0.log(10.0) == NEG_INFINITY)
 *     check((-42).log(10.0).isNan())
 * }
 * ```
 */
public fun F128.log(base: F128): F128 = this.ln() / base.ln()

/**
 * Returns the base 2 logarithm of the number.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val two = 2.0
 *     // log2(2) - 1 == 0
 *     val absDifference = (two.log2() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check(0.log2() == NEG_INFINITY)
 *     check((-42).log2().isNan())
 * }
 * ```
 */
public fun F128.log2(): F128 = io.github.kotlinmania.intrinsics.log2(this)

/**
 * Returns the base 10 logarithm of the number.
 *
 * This returns NaN when the number is negative, and negative infinity when number is zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val ten = 10.0
 *     // log10(10) - 1 == 0
 *     val absDifference = (ten.log10() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check(0.log10() == NEG_INFINITY)
 *     check((-42).log10().isNan())
 * }
 * ```
 */
public fun F128.log10(): F128 = io.github.kotlinmania.intrinsics.log10(this)

/**
 * Returns the cube root of a number.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `cbrtf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 8.0
 *     // x^(1/3) - 2 == 0
 *     val absDifference = (x.cbrt() - 2.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.cbrt(): F128 = cbrtf128(this)

/**
 * Compute the distance between the origin and a point (`x`, `y`) on the
 * Euclidean plane. Equivalently, compute the length of the hypotenuse of a
 * right-angle triangle with other sides having length `x.abs()` and
 * `y.abs()`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `hypotf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val y = 3.0
 *     // sqrt(x^2 + y^2)
 *     val absDifference = (x.hypot(y) - (x.powi(2) + y.powi(2)).sqrt()).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.hypot(other: F128): F128 = hypotf128(this, other)

/**
 * Computes the sine of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_2
 *     val absDifference = (x.sin() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.sin(): F128 = io.github.kotlinmania.intrinsics.sin(this)

/**
 * Computes the cosine of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 2.0 * PI
 *     val absDifference = (x.cos() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.cos(): F128 = io.github.kotlinmania.intrinsics.cos(this)

/**
 * Computes the tangent of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `tanf128` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_4
 *     val absDifference = (x.tan() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.tan(): F128 = tanf128(this)

/**
 * Computes the arcsine of a number. Return value is in radians in
 * the range [-pi/2, pi/2] or NaN if the number is outside the range
 * [-1, 1].
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `asinf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = FRAC_PI_4
 *     // asin(sin(pi/2))
 *     val absDifference = (f.sin().asin() - f).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.asin(): F128 = asinf128(this)

/**
 * Computes the arccosine of a number. Return value is in radians in
 * the range [0, pi] or NaN if the number is outside the range
 * [-1, 1].
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `acosf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = FRAC_PI_4
 *     // acos(cos(pi/4))
 *     val absDifference = (f.cos().acos() - FRAC_PI_4).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.acos(): F128 = acosf128(this)

/**
 * Computes the arctangent of a number. Return value is in radians in the
 * range [-pi/2, pi/2];
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `atanf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 1.0
 *     // atan(tan(1))
 *     val absDifference = (f.tan().atan() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.atan(): F128 = atanf128(this)

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
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `atan2f128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     // Positive angles measured counter-clockwise
 *     // from positive x axis
 *     // -pi/4 radians (45 deg clockwise)
 *     val x1 = 3.0
 *     val y1 = -3.0
 *     // 3pi/4 radians (135 deg counter-clockwise)
 *     val x2 = -3.0
 *     val y2 = 3.0
 *     val absDifference1 = (y1.atan2(x1) - (-FRAC_PI_4)).abs()
 *     val absDifference2 = (y2.atan2(x2) - (3.0 * FRAC_PI_4)).abs()
 *     check(absDifference1 <= EPSILON)
 *     check(absDifference2 <= EPSILON)
 * }
 * ```
 */
public fun F128.atan2(other: F128): F128 = atan2f128(this, other)

/**
 * Simultaneously computes the sine and cosine of the number, `x`. Returns
 * `(sin(x), cos(x))`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `(f128.sin(x),
 * f128.cos(x))`. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_4
 *     val f = x.sinCos()
 *     val absDifference0 = (f.first - x.sin()).abs()
 *     val absDifference1 = (f.second - x.cos()).abs()
 *     check(absDifference0 <= EPSILON)
 *     check(absDifference1 <= EPSILON)
 * }
 * ```
 */
public fun F128.sinCos(): Pair<F128, F128> = Pair(this.sin(), this.cos())

/**
 * Returns `e ^(self) - 1` in a way that is accurate even if the
 * number is close to zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `expm1f128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1e-8
 *     // for very small x, e^x is approximately 1 + x + x^2 / 2
 *     val approx = x + x * x / 2.0
 *     val absDifference = (x.expM1() - approx).abs()
 *     check(absDifference < 1e-10)
 * }
 * ```
 */
public fun F128.expM1(): F128 = expm1f128(this)

/**
 * Returns `ln(1 + n)` (natural logarithm) more accurately than if
 * the operations were performed separately.
 *
 * This returns NaN when `n< - 1.0`, and negative infinity when `n == - 1.0`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `log1pf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1e-8
 *     // for very small x, ln(1 + x) is approximately x - x^2 / 2
 *     val approx = x - x * x / 2.0
 *     val absDifference = (x.ln1p() - approx).abs()
 *     check(absDifference < 1e-10)
 * }
 * ```
 *
 * Out-of-range values:
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check((-1.0).ln1p() == NEG_INFINITY)
 *     check((-2.0).ln1p().isNan())
 * }
 * ```
 */
public fun F128.ln1p(): F128 = log1pf128(this)

/**
 * Hyperbolic sine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `sinhf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val e = E
 *     val x = 1.0
 *     val f = x.sinh()
 *     // Solving sinh() at 1 gives `(e ^ 2 - 1) / (2e)`
 *     val g = ((e * e) - 1.0) / (2.0 * e)
 *     val absDifference = (f - g).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.sinh(): F128 = sinhf128(this)

/**
 * Hyperbolic cosine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `coshf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val e = E
 *     val x = 1.0
 *     val f = x.cosh()
 *     // Solving cosh() at 1 gives this result
 *     val g = ((e * e) + 1.0) / (2.0 * e)
 *     val absDifference = (f - g).abs()
 *     // Same result
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.cosh(): F128 = coshf128(this)

/**
 * Hyperbolic tangent function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `tanhf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val e = E
 *     val x = 1.0
 *     val f = x.tanh()
 *     // Solving tanh() at 1 gives `(1 - e ^(- 2)) / (1 + e ^(- 2))`
 *     val g = (1.0 - e.powi(-2)) / (1.0 + e.powi(-2))
 *     val absDifference = (f - g).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.tanh(): F128 = tanhf128(this)

/**
 * Inverse hyperbolic sine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val f = x.sinh().asinh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.asinh(): F128 = asinhf128(this)

/**
 * Inverse hyperbolic cosine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val f = x.cosh().acosh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.acosh(): F128 = acoshf128(this)

/**
 * Inverse hyperbolic tangent function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_6
 *     val f = x.tanh().atanh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= 1e-5)
 * }
 * ```
 */
public fun F128.atanh(): F128 = 0.5 * ((2.0 * this) / (1.0 - this)).ln1p()

// @unstable(feature = "floatGamma", issue = "99842")

/**
 * Gamma function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `tgammaf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * @feature(floatGamma)
 * public fun doctestEntry() {
 *     val x = 5.0
 *     val absDifference = (x.gamma() - 24.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.gamma(): F128 = tgammaf128(this)

// @unstable(feature = "floatGamma", issue = "99842")

/**
 * Natural logarithm of the absolute value of the gamma function
 *
 * The integer part of the tuple indicates the sign of the gamma function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `lgammaf128R` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * @feature(floatGamma)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val absDifference = (x.lnGamma().first - 0.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.lnGamma(): Pair<F128, Int> {
    val localStorageL971C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    val signgamp by localStorageL971C8
    val x = lgammaf128R(this, RustMutableReference(localStorageL971C8.asMutPtr(), localStorageL971C8.layout))
    return Pair(x, signgamp)
}

// @unstable(feature = "floatErf", issue = "136321")

/**
 * Error function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `erff128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F128
 *
 * @feature(f128)
 * @feature(floatErf)
 * public fun doctestEntry() {
 *     / **
 *      * The error function relates what percent of a normal distribution lies
 *      * within `x` standard deviations (scaled by `1 / sqrt(2)`).
 *      * /
 *     fun withinStandardDeviations(x: F128): F128 {
 *         return (x * FRAC_1_SQRT_2).erf() * 100.0
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
public fun F128.erf(): F128 = erff128(this)

// @unstable(feature = "floatErf", issue = "136321")

/**
 * Complementary error function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `erfcf128` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F128
 *
 * @feature(f128)
 * @feature(floatErf)
 * public fun doctestEntry() {
 *     val x: F128 = 0.123
 *     val one = x.erf() + x.erfc()
 *     val absDifference = (one - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.erfc(): F128 = erfcf128(this)
