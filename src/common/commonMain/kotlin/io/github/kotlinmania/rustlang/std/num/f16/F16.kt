// port-lint: source num/f16.rs
package io.github.kotlinmania.rustlang.std.num.f16

import io.github.kotlinmania.intrinsics.log
import io.github.kotlinmania.rustlang.core.F16
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

// Constants for the `f16` half-precision floating point type.
//
// *[See also the `f16` primitive type](primitive@f16).*
//
// Mathematically significant numbers are provided in the `consts` sub-module.

/**
 * Raises a number to a floating point power.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `f16.powf(f16.NAN, 0.0)` returns `1.0`. However, if an input is a *signaling*
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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val absDifference = (x.powf(2.0) - (x * x)).abs()
 *     check(absDifference <= 1e-1)
 *     check(powf(1.0, NAN) == 1.0)
 *     check(powf(NAN, 0.0) == 1.0)
 *     check(powf(0.0, 0.0) == 1.0)
 * }
 * ```
 */
public fun F16.powf(n: F16): F16 = io.github.kotlinmania.intrinsics.powf(this, n)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val one = 1.0
 *     // e^1
 *     val e = one.exp()
 *     // ln(e) - 1 == 0
 *     val absDifference = (e.ln() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.exp(): F16 = io.github.kotlinmania.intrinsics.exp(this)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = 2.0
 *     // 2^2 - 4 == 0
 *     val absDifference = (f.exp2() - 4.0).abs()
 *     check(absDifference <= 1e-1)
 * }
 * ```
 */
public fun F16.exp2(): F16 = io.github.kotlinmania.intrinsics.exp2(this)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val one = 1.0
 *     // e^1
 *     val e = one.exp()
 *     // ln(e) - 1 == 0
 *     val absDifference = (e.ln() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(0.ln() == NEG_INFINITY)
 *     check((-42).ln().isNan())
 * }
 * ```
 */
public fun F16.ln(): F16 = log(this)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val five = 5.0
 *     // log5(5) - 1 == 0
 *     val absDifference = (five.log(5.0) - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(0.log(10.0) == NEG_INFINITY)
 *     check((-42).log(10.0).isNan())
 * }
 * ```
 */
public fun F16.log(base: F16): F16 = this.ln() / base.ln()

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val two = 2.0
 *     // log2(2) - 1 == 0
 *     val absDifference = (two.log2() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(0.log2() == NEG_INFINITY)
 *     check((-42).log2().isNan())
 * }
 * ```
 */
public fun F16.log2(): F16 = io.github.kotlinmania.intrinsics.log2(this)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val ten = 10.0
 *     // log10(10) - 1 == 0
 *     val absDifference = (ten.log10() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(0.log10() == NEG_INFINITY)
 *     check((-42).log10().isNan())
 * }
 * ```
 */
public fun F16.log10(): F16 = io.github.kotlinmania.intrinsics.log10(this)

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
 * This function currently corresponds to the `hypotf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val y = 3.0
 *     // sqrt(x^2 + y^2)
 *     val absDifference = (x.hypot(y) - (x.powi(2) + y.powi(2)).sqrt()).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.hypot(other: F16): F16 = ((hypotf(this.toFloat(), other.toFloat()) as Any?) as F16)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_2
 *     val absDifference = (x.sin() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.sin(): F16 = io.github.kotlinmania.intrinsics.sin(this)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 2.0 * PI
 *     val absDifference = (x.cos() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.cos(): F16 = io.github.kotlinmania.intrinsics.cos(this)

/**
 * Computes the tangent of a number (in radians).
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `tanf` from libc on Unix and
 * Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_4
 *     val absDifference = (x.tan() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.tan(): F16 = ((tanf(this.toFloat()) as Any?) as F16)

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
 * This function currently corresponds to the `asinf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = FRAC_PI_4
 *     // asin(sin(pi/2))
 *     val absDifference = (f.sin().asin() - f).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.asin(): F16 = ((asinf(this.toFloat()) as Any?) as F16)

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
 * This function currently corresponds to the `acosf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = FRAC_PI_4
 *     // acos(cos(pi/4))
 *     val absDifference = (f.cos().acos() - FRAC_PI_4).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.acos(): F16 = ((acosf(this.toFloat()) as Any?) as F16)

/**
 * Computes the arctangent of a number. Return value is in radians in the
 * range [-pi/2, pi/2];
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `atanf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = 1.0
 *     // atan(tan(1))
 *     val absDifference = (f.tan().atan() - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.atan(): F16 = ((atanf(this.toFloat()) as Any?) as F16)

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
 * This function currently corresponds to the `atan2f` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
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
public fun F16.atan2(other: F16): F16 = ((atan2f(this.toFloat(), other.toFloat()) as Any?) as F16)

/**
 * Simultaneously computes the sine and cosine of the number, `x`. Returns
 * `(sin(x), cos(x))`.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `(f16.sin(x),
 * f16.cos(x))`. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_4
 *     val f = x.sinCos()
 *     val absDifference0 = (f.first - x.sin()).abs()
 *     val absDifference1 = (f.second - x.cos()).abs()
 *     check(absDifference0 <= 1e-2)
 *     check(absDifference1 <= 1e-2)
 * }
 * ```
 */
public fun F16.sinCos(): Pair<F16, F16> = Pair(this.sin(), this.cos())

/**
 * Returns `e ^(self) - 1` in a way that is accurate even if the
 * number is close to zero.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `expm1f` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 1e-4
 *     // for very small x, e^x is approximately 1 + x + x^2 / 2
 *     val approx = x + x * x / 2.0
 *     val absDifference = (x.expM1() - approx).abs()
 *     check(absDifference < 1e-4)
 * }
 * ```
 */
public fun F16.expM1(): F16 = ((expm1f(this.toFloat()) as Any?) as F16)

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
 * This function currently corresponds to the `log1pf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 1e-4
 *     // for very small x, ln(1 + x) is approximately x - x^2 / 2
 *     val approx = x - x * x / 2.0
 *     val absDifference = (x.ln1p() - approx).abs()
 *     check(absDifference < 1e-4)
 * }
 * ```
 *
 * Out-of-range values:
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     check((-1.0).ln1p() == NEG_INFINITY)
 *     check((-2.0).ln1p().isNan())
 * }
 * ```
 */
public fun F16.ln1p(): F16 = ((log1pf(this.toFloat()) as Any?) as F16)

/**
 * Hyperbolic sine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `sinhf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
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
public fun F16.sinh(): F16 = ((sinhf(this.toFloat()) as Any?) as F16)

/**
 * Hyperbolic cosine function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `coshf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
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
public fun F16.cosh(): F16 = ((coshf(this.toFloat()) as Any?) as F16)

/**
 * Hyperbolic tangent function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `tanhf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
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
public fun F16.tanh(): F16 = ((tanhf(this.toFloat()) as Any?) as F16)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val f = x.sinh().asinh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.asinh(): F16 = ((asinhf(this.toFloat()) as Any?) as F16)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val f = x.cosh().acosh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.acosh(): F16 = ((acoshf(this.toFloat()) as Any?) as F16)

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
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = FRAC_PI_6
 *     val f = x.tanh().atanh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= 0.01)
 * }
 * ```
 */
public fun F16.atanh(): F16 = 0.5 * ((2.0 * this) / (1.0 - this)).ln1p()

// @unstable(feature = "floatGamma", issue = "99842")

/**
 * Gamma function.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `tgammaf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 5.0
 *     val absDifference = (x.gamma() - 24.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.gamma(): F16 = ((tgammaf(this.toFloat()) as Any?) as F16)

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
 * This function currently corresponds to the `lgammaR` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val absDifference = (x.lnGamma().first - 0.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.lnGamma(): Pair<F16, Int> {
    val localStorageL935C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    val signgamp by localStorageL935C8
    val x = ((lgammafR(this.toFloat(), RustMutableReference(localStorageL935C8.asMutPtr(), localStorageL935C8.layout)) as Any?) as F16)
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
 * This function currently corresponds to the `erff` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     / **
 *      * The error function relates what percent of a normal distribution lies
 *      * within `x` standard deviations (scaled by `1 / sqrt(2)`).
 *      * /
 *     fun withinStandardDeviations(x: F16): F16 {
 *         return (x * FRAC_1_SQRT_2).erf() * 100.0
 *     }
 *     // 68% of a normal distribution is within one standard deviation
 *     check((withinStandardDeviations(1.0) - 68.269).abs() < 0.1)
 *     // 95% of a normal distribution is within two standard deviations
 *     check((withinStandardDeviations(2.0) - 95.450).abs() < 0.1)
 *     // 99.7% of a normal distribution is within three standard deviations
 *     check((withinStandardDeviations(3.0) - 99.730).abs() < 0.1)
 * }
 * ```
 */
public fun F16.erf(): F16 = ((erff(this.toFloat()) as Any?) as F16)

// @unstable(feature = "floatErf", issue = "136321")

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x: F16 = 0.123
 *     val one = x.erf() + x.erfc()
 *     val absDifference = (one - 1.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F16.erfc(): F16 = ((erfcf(this.toFloat()) as Any?) as F16)
