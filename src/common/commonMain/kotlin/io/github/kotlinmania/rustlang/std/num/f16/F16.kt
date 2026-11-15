// port-lint: source num/f16.rs
package io.github.kotlinmania.rustlang.std.num.f16

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.kmCosf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmLogf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmSinf32
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.floatBinaryWithLayout
import io.github.kotlinmania.rustlang.core.mem.floatUnaryWithLayout
import io.github.kotlinmania.rustlang.std.sys.cmath.acosf
import io.github.kotlinmania.rustlang.std.sys.cmath.acoshf
import io.github.kotlinmania.rustlang.std.sys.cmath.asinf
import io.github.kotlinmania.rustlang.std.sys.cmath.asinhf
import io.github.kotlinmania.rustlang.std.sys.cmath.atan2f
import io.github.kotlinmania.rustlang.std.sys.cmath.atanf
import io.github.kotlinmania.rustlang.std.sys.cmath.coshf
import io.github.kotlinmania.rustlang.std.sys.cmath.erfcf
import io.github.kotlinmania.rustlang.std.sys.cmath.erff
import io.github.kotlinmania.rustlang.std.sys.cmath.expm1f
import io.github.kotlinmania.rustlang.std.sys.cmath.hypotf
import io.github.kotlinmania.rustlang.std.sys.cmath.lgammafR
import io.github.kotlinmania.rustlang.std.sys.cmath.log1pf
import io.github.kotlinmania.rustlang.std.sys.cmath.sinhf
import io.github.kotlinmania.rustlang.std.sys.cmath.tanf
import io.github.kotlinmania.rustlang.std.sys.cmath.tanhf
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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(2.0f)
 *     val absDifference = (x.powf(F16.fromFloat(2.0f)) - (x * x)).abs()
 *     check(absDifference <= 1e-1)
 *     check(F16.powf(1.0, F16.NAN) == 1.0)
 *     check(F16.powf(F16.NAN, 0.0) == 1.0)
 *     check(F16.powf(0.0, 0.0) == 1.0)
 * }
 * ```
 */
public fun F16.powf(n: F16): F16 = floatBinaryWithLayout("powf", this, n, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val one = F16.fromFloat(1.0f)
 *     // e^1
 *     val e = one.exp()
 *     // ln(e) - 1 == 0
 *     val absDifference = (e.ln() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.exp(): F16 = floatUnaryWithLayout("exp", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(2.0f)
 *     // 2^2 - 4 == 0
 *     val absDifference = (f.exp2() - 4.0).abs()
 *     check(absDifference <= 1e-1)
 * }
 * ```
 */
public fun F16.exp2(): F16 = floatUnaryWithLayout("exp2", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val one = F16.fromFloat(1.0f)
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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(0.0f).ln() == F16.NEG_INFINITY)
 *     check((-F16.fromFloat(42.0f)).ln().isNan())
 * }
 * ```
 */
public fun F16.ln(): F16 = floatUnaryWithLayout("log", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val five = F16.fromFloat(5.0f)
 *     // log5(5) - 1 == 0
 *     val absDifference = (five.log(F16.fromFloat(5.0f)) - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(0.0f).log(10.0) == F16.NEG_INFINITY)
 *     check((-F16.fromFloat(42.0f)).log(10.0).isNan())
 * }
 * ```
 */
public fun F16.log(base: F16): F16 = F16.fromFloat(kmLogf32(this.toFloat())) / F16.fromFloat(kmLogf32(base.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val two = F16.fromFloat(2.0f)
 *     // log2(2) - 1 == 0
 *     val absDifference = (two.log2() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(0.0f).log2() == F16.NEG_INFINITY)
 *     check((-F16.fromFloat(42.0f)).log2().isNan())
 * }
 * ```
 */
public fun F16.log2(): F16 = floatUnaryWithLayout("log2", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val ten = F16.fromFloat(10.0f)
 *     // log10(10) - 1 == 0
 *     val absDifference = (ten.log10() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 *
 * Non-positive values:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(0.0f).log10() == F16.NEG_INFINITY)
 *     check((-F16.fromFloat(42.0f)).log10().isNan())
 * }
 * ```
 */
public fun F16.log10(): F16 = floatUnaryWithLayout("log10", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(2.0f)
 *     val y = F16.fromFloat(3.0f)
 *     // sqrt(x^2 + y^2)
 *     val absDifference = (x.hypot(y) - (x.powi(2) + y.powi(2)).sqrt()).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.hypot(other: F16): F16 = F16.fromFloat(hypotf(this.toFloat(), other.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.5707964f)
 *     val absDifference = (x.sin() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.sin(): F16 = floatUnaryWithLayout("sin", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(2.0f) * F16.fromFloat(3.1415927f)
 *     val absDifference = (x.cos() - 1.0).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.cos(): F16 = floatUnaryWithLayout("cos", this, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(0.7853982f)
 *     val absDifference = (x.tan() - 1.0).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.tan(): F16 = F16.fromFloat(tanf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(0.7853982f)
 *     // asin(sin(pi/2))
 *     val absDifference = (f.sin().asin() - f).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.asin(): F16 = F16.fromFloat(asinf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(0.7853982f)
 *     // acos(cos(pi/4))
 *     val absDifference = (f.cos().acos() - F16.fromFloat(0.7853982f)).abs()
 *     check(absDifference <= 1e-2)
 * }
 * ```
 */
public fun F16.acos(): F16 = F16.fromFloat(acosf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(1.0f)
 *     // atan(tan(1))
 *     val absDifference = (f.tan().atan() - 1.0).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.atan(): F16 = F16.fromFloat(atanf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     // Positive angles measured counter-clockwise
 *     // from positive x axis
 *     // -pi/4 radians (45 deg clockwise)
 *     val x1 = F16.fromFloat(3.0f)
 *     val y1 = -F16.fromFloat(3.0f)
 *     // 3pi/4 radians (135 deg counter-clockwise)
 *     val x2 = -F16.fromFloat(3.0f)
 *     val y2 = F16.fromFloat(3.0f)
 *     val absDifference1 = (y1.atan2(x1) - (-F16.fromFloat(0.7853982f))).abs()
 *     val absDifference2 = (y2.atan2(x2) - (F16.fromFloat(3.0f) * F16.fromFloat(0.7853982f))).abs()
 *     check(absDifference1 <= F16.EPSILON)
 *     check(absDifference2 <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.atan2(other: F16): F16 = F16.fromFloat(atan2f(this.toFloat(), other.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(0.7853982f)
 *     val f = x.sinCos()
 *     val absDifference0 = (f.first - x.sin()).abs()
 *     val absDifference1 = (f.second - x.cos()).abs()
 *     check(absDifference0 <= 1e-2)
 *     check(absDifference1 <= 1e-2)
 * }
 * ```
 */
public fun F16.sinCos(): Pair<F16, F16> = Pair(F16.fromFloat(kmSinf32(this.toFloat())), F16.fromFloat(kmCosf32(this.toFloat())))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1e-4f)
 *     // for very small x, e^x is approximately 1 + x + x^2 / 2
 *     val approx = x + x * x / F16.fromFloat(2.0f)
 *     val absDifference = (x.expM1() - approx).abs()
 *     check(absDifference < 1e-4)
 * }
 * ```
 */
public fun F16.expM1(): F16 = F16.fromFloat(expm1f(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1e-4f)
 *     // for very small x, ln(1 + x) is approximately x - x^2 / 2
 *     val approx = x - x * x / F16.fromFloat(2.0f)
 *     val absDifference = (x.ln1p() - approx).abs()
 *     check(absDifference < 1e-4)
 * }
 * ```
 *
 * Out-of-range values:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check((-F16.fromFloat(1.0f)).ln1p() == F16.NEG_INFINITY)
 *     check((-F16.fromFloat(2.0f)).ln1p().isNan())
 * }
 * ```
 */
public fun F16.ln1p(): F16 = F16.fromFloat(log1pf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val e = F16.fromFloat(2.7182817f)
 *     val x = F16.fromFloat(1.0f)
 *     val f = x.sinh()
 *     // Solving sinh() at 1 gives `(e ^ 2 - 1) / (2e)`
 *     val g = ((e * e) - F16.fromFloat(1.0f)) / (F16.fromFloat(2.0f) * e)
 *     val absDifference = (f - g).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.sinh(): F16 = F16.fromFloat(sinhf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val e = F16.fromFloat(2.7182817f)
 *     val x = F16.fromFloat(1.0f)
 *     val f = x.cosh()
 *     // Solving cosh() at 1 gives this result
 *     val g = ((e * e) + F16.fromFloat(1.0f)) / (F16.fromFloat(2.0f) * e)
 *     val absDifference = (f - g).abs()
 *     // Same result
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.cosh(): F16 = F16.fromFloat(coshf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val e = F16.fromFloat(2.7182817f)
 *     val x = F16.fromFloat(1.0f)
 *     val f = x.tanh()
 *     // Solving tanh() at 1 gives `(1 - e ^(- 2)) / (1 + e ^(- 2))`
 *     val g = (1.0 - e.powi(-2)) / (1.0 + e.powi(-2))
 *     val absDifference = (f - g).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.tanh(): F16 = F16.fromFloat(tanhf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     val f = x.sinh().asinh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.asinh(): F16 = F16.fromFloat(asinhf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     val f = x.cosh().acosh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.acosh(): F16 = F16.fromFloat(acoshf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(0.5235988f)
 *     val f = x.tanh().atanh()
 *     val absDifference = (f - x).abs()
 *     check(absDifference <= 0.01)
 * }
 * ```
 */
public fun F16.atanh(): F16 = F16.fromFloat(0.5f) * ((F16.fromFloat(2.0f) * this) / (F16.fromFloat(1.0f) - this)).ln1p()

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(5.0f)
 *     val absDifference = (x.gamma() - 24.0).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.gamma(): F16 = F16.fromFloat(tgammaf(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(2.0f)
 *     val absDifference = (x.lnGamma().first - 0.0).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.lnGamma(): Pair<F16, Int> {
    val localStorageL935C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    val signgamp by localStorageL935C8
    val x = F16.fromFloat(lgammafR(this.toFloat(), RustMutableReference(localStorageL935C8.asMutPtr(), localStorageL935C8.layout)))
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
 *         return (x * F16.fromFloat(0.70710677f)).erf() * F16.fromFloat(100.0f)
 *     }
 *     // 68% of a normal distribution is within one standard deviation
 *     check((withinStandardDeviations(F16.fromFloat(1.0f)) - F16.fromFloat(68.269f)).abs() < 0.1)
 *     // 95% of a normal distribution is within two standard deviations
 *     check((withinStandardDeviations(F16.fromFloat(2.0f)) - F16.fromFloat(95.450f)).abs() < 0.1)
 *     // 99.7% of a normal distribution is within three standard deviations
 *     check((withinStandardDeviations(F16.fromFloat(3.0f)) - F16.fromFloat(99.730f)).abs() < 0.1)
 * }
 * ```
 */
public fun F16.erf(): F16 = F16.fromFloat(erff(this.toFloat()))

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
 *     val x: F16 = F16.fromFloat(0.123f)
 *     val one = x.erf() + x.erfc()
 *     val absDifference = (one - 1.0).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.erfc(): F16 = F16.fromFloat(erfcf(this.toFloat()))
