// port-lint: source num/f16.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.f16

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.clamp.ClampBounds
import io.github.kotlinmania.rustlang.core.intrinsics.copysignf16
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf32
import io.github.kotlinmania.rustlang.core.intrinsics.maximumNumberNszF16
import io.github.kotlinmania.rustlang.core.intrinsics.maximumf16
import io.github.kotlinmania.rustlang.core.intrinsics.minimumNumberNszF16
import io.github.kotlinmania.rustlang.core.intrinsics.minimumf16
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.floatBinaryWithLayout
import io.github.kotlinmania.rustlang.core.mem.floatFmaWithLayout
import io.github.kotlinmania.rustlang.core.mem.floatPowiWithLayout
import io.github.kotlinmania.rustlang.core.mem.floatUnaryWithLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.FpCategory
import io.github.kotlinmania.rustlang.core.num.imp.libm.cbrtf
import io.github.kotlinmania.rustlang.core.panic.constAssert

// Constants for the `f16` half-precision floating point type.
//
// *[See also the `f16` primitive type][f16].*
//
// Mathematically significant numbers are provided in the `consts` sub-module.
//
// For the constants defined directly in this module
// (as distinct from those defined in the `consts` sub-module),
// new code should instead use the associated constants
// defined directly on the `f16` type.

// Note: replace with mathematical constants from cmath.

/**
 * Archimedes' constant (π)
 */
public val PI: F16 = F16.fromFloat(3.14159265358979323846264338327950288f)

/**
 * The full circle constant (τ)
 *
 * Equal to 2π.
 */
public val TAU: F16 = F16.fromFloat(6.28318530717958647692528676655900577f)

/**
 * The golden ratio (φ)
 */
public val GOLDEN_RATIO: F16 = F16.fromFloat(1.618033988749894848204586834365638118f)

/**
 * The Euler-Mascheroni constant (γ)
 */
public val EULER_GAMMA: F16 = F16.fromFloat(0.577215664901532860606512090082402431f)

/**
 * π/2
 */
public val FRAC_PI_2: F16 = F16.fromFloat(1.57079632679489661923132169163975144f)

/**
 * π/3
 */
public val FRAC_PI_3: F16 = F16.fromFloat(1.04719755119659774615421446109316763f)

/**
 * π/4
 */
public val FRAC_PI_4: F16 = F16.fromFloat(0.785398163397448309615660845819875721f)

/**
 * π/6
 */
public val FRAC_PI_6: F16 = F16.fromFloat(0.52359877559829887307710723054658381f)

/**
 * π/8
 */
public val FRAC_PI_8: F16 = F16.fromFloat(0.39269908169872415480783042290993786f)

/**
 * 1/π
 */
public val FRAC_1_PI: F16 = F16.fromFloat(0.318309886183790671537767526745028724f)

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * 1/sqrt(π)
 */
public val FRAC_1_SQRT_PI: F16 = F16.fromFloat(0.564189583547756286948079451560772586f)

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * 1/sqrt(2π)
 */
public val FRAC_1_SQRT_2PI: F16 = F16.fromFloat(0.398942280401432677939946059934381868f)

/**
 * 2/π
 */
public val FRAC_2_PI: F16 = F16.fromFloat(0.636619772367581343075535053490057448f)

/**
 * 2/sqrt(π)
 */
public val FRAC_2_SQRT_PI: F16 = F16.fromFloat(1.12837916709551257389615890312154517f)

/**
 * sqrt(2)
 */
public val SQRT_2: F16 = F16.fromFloat(1.41421356237309504880168872420969808f)

/**
 * 1/sqrt(2)
 */
public val FRAC_1_SQRT_2: F16 = F16.fromFloat(0.707106781186547524400844362104849039f)

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * sqrt(3)
 */
public val SQRT_3: F16 = F16.fromFloat(1.732050807568877293527446341505872367f)

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * 1/sqrt(3)
 */
public val FRAC_1_SQRT_3: F16 = F16.fromFloat(0.577350269189625764509148780501957456f)

// Also, @unstable(feature = "f16", issue = "116909")

/**
 * sqrt(5)
 */
public val SQRT_5: F16 = F16.fromFloat(2.23606797749978969640917366873127623f)

// Also, @unstable(feature = "f16", issue = "116909")

/**
 * 1/sqrt(5)
 */
public val FRAC_1_SQRT_5: F16 = F16.fromFloat(0.44721359549995793928183473374625524f)

/**
 * Euler's number (e)
 */
public val E: F16 = F16.fromFloat(2.71828182845904523536028747135266250f)

/**
 * log<sub>2</sub>(10)
 */
public val LOG2_10: F16 = F16.fromFloat(3.32192809488736234787031942948939018f)

/**
 * log<sub>2</sub>(e)
 */
public val LOG2_E: F16 = F16.fromFloat(1.44269504088896340735992468100189214f)

/**
 * log<sub>10</sub>(2)
 */
public val LOG10_2: F16 = F16.fromFloat(0.301029995663981195213738894724493027f)

/**
 * log<sub>10</sub>(e)
 */
public val LOG10_E: F16 = F16.fromFloat(0.434294481903251827651128918916605082f)

/**
 * ln(2)
 */
public val LN_2: F16 = F16.fromFloat(0.693147180559945309417232121458176568f)

/**
 * ln(10)
 */
public val LN_10: F16 = F16.fromFloat(2.30258509299404568401799145468436421f)

// > if you intended to check if the operand is NaN, use `.isNan()` instead :)

/**
 * Returns `true` if this value is NaN.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val nan = F16.NAN
 *     val f = F16.fromFloat(7.0f)
 *     check(nan.isNan())
 *     check(!f.isNan())
 * }
 * ```
 */
public fun F16.isNan(): Boolean = this != this

/**
 * Returns `true` if this value is positive infinity or negative infinity, and
 * `false` otherwise.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(7.0f)
 *     val inf = F16.INFINITY
 *     val negInf = F16.NEG_INFINITY
 *     val nan = F16.NAN
 *     check(!f.isInfinite())
 *     check(!nan.isInfinite())
 *     check(inf.isInfinite())
 *     check(negInf.isInfinite())
 * }
 * ```
 */
public fun F16.isInfinite(): Boolean = (this == F16.INFINITY) or (this == F16.NEG_INFINITY)

/**
 * Returns `true` if this number is neither infinite nor NaN.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(7.0f)
 *     val inf: F16 = F16.INFINITY
 *     val negInf: F16 = F16.NEG_INFINITY
 *     val nan: F16 = F16.NAN
 *     check(f.isFinite())
 *     check(!nan.isFinite())
 *     check(!inf.isFinite())
 *     check(!negInf.isFinite())
 * }
 * ```
 */
public fun F16.isFinite(): Boolean {
    // There's no need to handle NaN separately: if self is NaN,
    // the comparison is not true, exactly as desired.
    return F16.fromFloat(kmFabsf32(this.toFloat())) < F16.INFINITY
}

/**
 * Returns `true` if the number is [subnormal].
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val min = F16.MIN_POSITIVE
 *     // 6.1035e-5
 *     val max = F16.MAX
 *     val lowerThanMin = F16.fromFloat(1.0e-7f)
 *     val zero = F16.fromFloat(0.0f)
 *     check(!min.isSubnormal())
 *     check(!max.isSubnormal())
 *     check(!zero.isSubnormal())
 *     check(!F16.NAN.isSubnormal())
 *     check(!F16.INFINITY.isSubnormal())
 *     // Values between `0` and `min` are Subnormal.
 *     check(lowerThanMin.isSubnormal())
 * }
 * ```
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun F16.isSubnormal(): Boolean =
    when (this.classify()) {
        FpCategory.Subnormal -> true
        else -> false
    }

/**
 * Returns `true` if the number is neither zero, infinite, [subnormal], or NaN.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val min = F16.MIN_POSITIVE
 *     // 6.1035e-5
 *     val max = F16.MAX
 *     val lowerThanMin = F16.fromFloat(1.0e-7f)
 *     val zero = F16.fromFloat(0.0f)
 *     check(min.isNormal())
 *     check(max.isNormal())
 *     check(!zero.isNormal())
 *     check(!F16.NAN.isNormal())
 *     check(!F16.INFINITY.isNormal())
 *     // Values between `0` and `min` are Subnormal.
 *     check(!lowerThanMin.isNormal())
 * }
 * ```
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun F16.isNormal(): Boolean =
    when (this.classify()) {
        FpCategory.Normal -> true
        else -> false
    }

/**
 * Returns the floating point category of the number. If only one property
 * is going to be tested, it is generally faster to use the specific
 * predicate instead.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 * import std.num.FpCategory
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val num = F16.fromFloat(12.4f)
 *     val inf = F16.INFINITY
 *     check(num.classify() == FpCategory.Normal)
 *     check(inf.classify() == FpCategory.Infinite)
 * }
 * ```
 */
public fun F16.classify(): FpCategory {
    val b = this.toBits()
    return run {
        val matchVal = Pair(b and F16.MANTISSA_MASK, b and F16.EXPONENT_MASK)
        when {
            matchVal.first == 0 && matchVal.second == F16.EXPONENT_MASK -> FpCategory.Infinite
            matchVal.second == F16.EXPONENT_MASK -> FpCategory.Nan
            matchVal.first == 0 && matchVal.second == 0 -> FpCategory.Zero
            matchVal.second == 0 -> FpCategory.Subnormal
            else -> FpCategory.Normal
        }
    }
}

/**
 * Returns `true` if `self` has a positive sign, including `+ 0.0`, NaNs with
 * positive sign bit and positive infinity.
 *
 * Note that IEEE 754 doesn't assign any meaning to the sign bit in case of
 * a NaN, and as Rust doesn't guarantee that the bit pattern of NaNs are
 * conserved over arithmetic operations, the result of `isSignPositive` on
 * a NaN might produce an unexpected or non-portable result. See the [specification
 * of NaN bit patterns](f32#nan-bit-patterns) for more info. Use `self.signum() == 1.0`
 * if you need fully portable behavior (evaluates to `false` for all NaNs).
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(7.0f)
 *     val g = -F16.fromFloat(7.0f)
 *     check(f.isSignPositive())
 *     check(!g.isSignPositive())
 * }
 * ```
 */
public fun F16.isSignPositive(): Boolean = !this.isSignNegative()

/**
 * Returns `true` if `self` has a negative sign, including `- 0.0`, NaNs with
 * negative sign bit and negative infinity.
 *
 * Note that IEEE 754 doesn't assign any meaning to the sign bit in case of
 * a NaN, and as Rust doesn't guarantee that the bit pattern of NaNs are
 * conserved over arithmetic operations, the result of `isSignNegative` on
 * a NaN might produce an unexpected or non-portable result. See the [specification
 * of NaN bit patterns](f32#nan-bit-patterns) for more info. Use `self.signum() == - 1.0`
 * if you need fully portable behavior (evaluates to `false` for all NaNs).
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(7.0f)
 *     val g = -F16.fromFloat(7.0f)
 *     check(!f.isSignNegative())
 *     check(g.isSignNegative())
 * }
 * ```
 */
public fun F16.isSignNegative(): Boolean {
    // IEEE754 says: isSignMinus(x) is true if and only if x has negative sign. isSignMinus
    // applies to zeros and NaNs as well.
    // SAFETY: This is just transmuting to get the sign bit, it's fine.
    return (this.toBits() and ((1u.toUInt() shl 15).toUShort())) != 0u.toUShort()
}

/**
 * Returns the least number greater than `self`.
 *
 * Let `TINY` be the smallest representable positive `f16`. Then,
 *  - if `self.isNan()`, this returns `self`;
 *  - if `self` is [NegInfinity], this returns [Min];
 *  - if `self` is `- TINY`, this returns -0.0;
 *  - if `self` is -0.0 or +0.0, this returns `TINY`;
 *  - if `self` is [Max] or [Infinity], this returns [Infinity];
 *  - otherwise the unique least value greater than `self` is returned.
 *
 * The identity `x.nextUp() == - (- x).nextDown()` holds for all non-NaN `x`. When `x`
 * is finite `x == x.nextUp().nextDown()` also holds.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     // f16.Epsilon is the difference between 1.0 and the next number up.
 *     check(F16.fromFloat(1.0f).nextUp() == F16.fromFloat(1.0f) + F16.EPSILON)
 *     // But not for most numbers.
 *     check(F16.fromFloat(0.1f).nextUp() < F16.fromFloat(0.1f) + F16.EPSILON)
 *     check(F16.fromFloat(4356.0f).nextUp() == 4360.0)
 * }
 * ```
 *
 * This operation corresponds to IEEE-754 `nextUp`.
 *
 * [NegInfinity]: Self.NegInfinity
 * [Infinity]: Self.Infinity
 * [Min]: Self.Min
 * [Max]: Self.Max
 */
public fun F16.nextUp(): F16 {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toBits()
    if (this.isNaN() || bits == F16.INFINITY.toBits().toUShort()) {
        return this
    }
    val abs = bits and F16.SIGN_MASK.inv()
    val nextBits =
        if (abs == 0u.toUShort()) {
            F16.TINY_BITS
        } else if (bits == abs) {
            bits + 1u
        } else {
            bits - 1u
        }
    return F16.fromBits(nextBits)
}

/**
 * Returns the greatest number less than `self`.
 *
 * Let `TINY` be the smallest representable positive `f16`. Then,
 *  - if `self.isNan()`, this returns `self`;
 *  - if `self` is [Infinity], this returns [Max];
 *  - if `self` is `TINY`, this returns 0.0;
 *  - if `self` is -0.0 or +0.0, this returns `- TINY`;
 *  - if `self` is [Min] or [NegInfinity], this returns [NegInfinity];
 *  - otherwise the unique greatest value less than `self` is returned.
 *
 * The identity `x.nextDown() == - (- x).nextUp()` holds for all non-NaN `x`. When `x`
 * is finite `x == x.nextDown().nextUp()` also holds.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     // Clamp value into range [0, 1).
 *     val clamped = x.clamp(0.0, F16.fromFloat(1.0f).nextDown())
 *     check(clamped < 1.0)
 *     check(clamped.nextUp() == 1.0)
 * }
 * ```
 *
 * This operation corresponds to IEEE-754 `nextDown`.
 *
 * [NegInfinity]: Self.NegInfinity
 * [Infinity]: Self.Infinity
 * [Min]: Self.Min
 * [Max]: Self.Max
 */
public fun F16.nextDown(): F16 {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toBits()
    if (this.isNaN() || bits == F16.NEG_INFINITY.toBits().toUShort()) {
        return this
    }
    val abs = bits and F16.SIGN_MASK.inv()
    val nextBits =
        if (abs == 0u.toUShort()) {
            F16.NEG_TINY_BITS
        } else if (bits == abs) {
            bits - 1u
        } else {
            bits + 1u
        }
    return F16.fromBits(nextBits)
}

/**
 * Takes the reciprocal (inverse) of a number, `1 / x`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(2.0f)
 *     val absDifference = (x.recip() - (F16.fromFloat(1.0f) / x)).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.recip(): F16 = F16.fromFloat(1.0f) / this

/**
 * Converts radians to degrees.
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
 *     val angle = F16.fromFloat(3.1415927f)
 *     val absDifference = (angle.toDegrees() - 180.0).abs()
 *     check(absDifference <= 0.5)
 * }
 * ```
 */
public fun F16.toDegrees(): F16 {
    // Use a literal to avoid double rounding, consts.PI is already rounded,
    // and dividing would round again.
    val PIS_IN_180: F16 = F16.fromFloat(57.2957795130823208767981548141051703f)
    return this * PIS_IN_180
}

/**
 * Converts degrees to radians.
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
 *     val angle = F16.fromFloat(180.0f)
 *     val absDifference = (angle.toRadians() - F16.fromFloat(3.1415927f)).abs()
 *     check(absDifference <= 0.01)
 * }
 * ```
 */
public fun F16.toRadians(): F16 {
    // Use a literal to avoid double rounding, consts.PI is already rounded,
    // and dividing would round again.
    val RADS_PER_DEG: F16 = F16.fromFloat(0.017453292519943295769236907684886f)
    return this * RADS_PER_DEG
}

/**
 * Returns the maximum of the two numbers, ignoring NaN.
 *
 * If exactly one of the arguments is NaN (quiet or signaling), then the other argument is
 * returned. If both arguments are NaN, the return value is NaN, with the bit pattern picked
 * using the usual [rules for arithmetic operations](f32#nan-bit-patterns). If the inputs
 * compare equal (such as for the case of `+ 0.0` and `- 0.0`), either input may be returned
 * non-deterministically.
 *
 * The handling of NaNs follows the IEEE 754-2019 semantics for `maximumNumber`, treating all
 * NaNs the same way to ensure the operation is associative. The handling of signed zeros
 * follows the IEEE 754-2008 semantics for `maxNum`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     val y = F16.fromFloat(2.0f)
 *     check(x.max(y) == y)
 *     check(x.max(F16.NAN) == x)
 * }
 * ```
 */
public fun F16.max(other: F16): F16 = maximumNumberNszF16(this, other)

/**
 * Returns the minimum of the two numbers, ignoring NaN.
 *
 * If exactly one of the arguments is NaN (quiet or signaling), then the other argument is
 * returned. If both arguments are NaN, the return value is NaN, with the bit pattern picked
 * using the usual [rules for arithmetic operations](f32#nan-bit-patterns). If the inputs
 * compare equal (such as for the case of `+ 0.0` and `- 0.0`), either input may be returned
 * non-deterministically.
 *
 * The handling of NaNs follows the IEEE 754-2019 semantics for `minimumNumber`, treating all
 * NaNs the same way to ensure the operation is associative. The handling of signed zeros
 * follows the IEEE 754-2008 semantics for `minNum`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     val y = F16.fromFloat(2.0f)
 *     check(x.min(y) == x)
 *     check(x.min(F16.NAN) == x)
 * }
 * ```
 */
public fun F16.min(other: F16): F16 = minimumNumberNszF16(this, other)

// @unstable(feature = "floatMinimumMaximum", issue = "91079")

/**
 * Returns the maximum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f16.max] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `maximum`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     val y = F16.fromFloat(2.0f)
 *     check(x.maximum(y) == y)
 *     check(x.maximum(F16.NAN).isNan())
 * }
 * ```
 */
public fun F16.maximum(other: F16): F16 = maximumf16(this, other)

// @unstable(feature = "floatMinimumMaximum", issue = "91079")

/**
 * Returns the minimum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f16.min] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `minimum`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.0f)
 *     val y = F16.fromFloat(2.0f)
 *     check(x.minimum(y) == x)
 *     check(x.minimum(F16.NAN).isNan())
 * }
 * ```
 */
public fun F16.minimum(other: F16): F16 = minimumf16(this, other)

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * This returns NaN when *either* argument is NaN or if a combination of
 * +inf and -inf is provided as arguments.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(1.0f).midpoint(4.0) == 2.5)
 *     check((-F16.fromFloat(5.5f)).midpoint(8.0) == 1.25)
 * }
 * ```
 */
public fun F16.midpoint(other: F16): F16 {
    val HI: F16 = F16.MAX * F16.fromFloat(0.5f)
    val (a, b) = Pair(this, other)
    val absA = F16.fromFloat(kmFabsf32(a.toFloat()))
    val absB = F16.fromFloat(kmFabsf32(b.toFloat()))
    return if (absA <= HI && absB <= HI) {
        // Overflow is impossible
        (a + b) * F16.fromFloat(0.5f)
    } else {
        (a * F16.fromFloat(0.5f)) + (b * F16.fromFloat(0.5f))
    }
}

/**
 * Rounds toward zero and converts to any primitive integer type,
 * assuming that the value is finite and fits in that type.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val value = F16.fromFloat(4.6f)
 *     val rounded = value.toIntUnchecked<UShort>()
 *     check(rounded == 4)
 *     val value1 = -F16.fromFloat(128.9f)
 *     val rounded1 = value1.toIntUnchecked<Byte>()
 *     check(rounded1 == Byte.MIN_VALUE)
 * }
 * ```
 *
 * # Safety
 *
 * The value must:
 *
 * * Not be `NaN`
 * * Not be infinite
 * * Be representable in the return type `Int`, after truncating off its fractional part
 */
public inline fun <reified Int> F16.toIntUnchecked(): Int {
    // SAFETY: the caller must uphold the safety contract for
    // `FloatToInt.toIntUnchecked`.
    return this.toIntUnchecked()
}

/**
 * Converts to the target float type, rounding as defined in IEEE 754.
 *
 * This is equivalent to `self as Flt`. Narrowing to a smaller type can
 * produce an infinity.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(floatConversions, f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(1.5f)
 *     check(x.cast() == 1.5f)
 * }
 * ```
 */
public inline fun <reified Flt> F16.cast(): Flt = this.cast()

/**
 * Rounds toward zero and converts to any primitive integer type, saturating
 * at the type's boundaries and mapping `NaN` to zero.
 *
 * This is equivalent to `self as Int`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(floatConversions, f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(4.6f).toIntSaturating<UByte>() == 4)
 *     check(F16.NAN.toIntSaturating<UByte>() == 0)
 * }
 * ```
 */
public inline fun <reified Int> F16.toIntSaturating(): Int = this.toIntSaturating()

/**
 * Rounds toward zero and converts to any primitive integer type, returning
 * `None` if the value is `NaN`, infinite, or does not fit in the target type.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(floatConversions, f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(4.6f).toIntChecked<UByte>() == 4)
 *     check(F16.NAN.toIntChecked<UByte>() == null)
 * }
 * ```
 */
public inline fun <reified Int> F16.toIntChecked(): Int? = this.toIntChecked()

/**
 * Rounds toward zero and converts to any primitive integer type.
 *
 * This is equivalent to `self.toIntChecked().unwrap()`.
 *
 * # Panics
 *
 * Panics if the value is `NaN`, infinite, or does not fit in the target type.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(floatConversions, f16)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(4.6f).toIntStrict<UByte>() == 4)
 * }
 * ```
 */
public inline fun <reified Int> F16.toIntStrict(): Int = (this.toIntChecked<Int>() ?: error("the value cannot be represented in the target integer type"))

/**
 * Raw transmutation to `UShort`.
 *
 * This is currently identical to `reinterpret<f16, UShort>(self)` on all platforms.
 *
 * See [`fromBits`](#method.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * Note that this function is distinct from `as` casting, which attempts to
 * preserve the *numeric* value, and not the bitwise value.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check((F16.fromFloat(1.0f)).toBits() != (F16.fromFloat(1.0f)).toUShort())
 *     // toBits() is not casting!
 *     check((F16.fromFloat(12.5f)).toBits() == 0x4a40u.toUShort())
 * }
 * ```
 */
public fun F16.toBits(): UShort {
    // SAFETY: `UShort` is a plain old datatype so we can always reinterpret to it.
    return transmuteWithLayouts<F16, UShort>(this, UnresolvedTypeLayout<F16>(), PrimitiveLayouts.U16)
}

/**
 * Raw transmutation from `UShort`.
 *
 * This is currently identical to `reinterpret<UShort, f16>(v)` on all platforms.
 * It turns out this is incredibly portable, for two reasons:
 *
 * * Floats and Ints have the same endianness on all supported platforms.
 * * IEEE 754 very precisely specifies the bit layout of floats.
 *
 * However there is one caveat: prior to the 2008 version of IEEE 754, how
 * to interpret the NaN signaling bit wasn't actually specified. Most platforms
 * (notably x86 and ARM) picked the interpretation that was ultimately
 * standardized in 2008, but some didn't (notably MIPS). As a result, all
 * signaling NaNs on MIPS are quiet NaNs on x86, and vice-versa.
 *
 * Rather than trying to preserve signaling-ness cross-platform, this
 * implementation favors preserving the exact bits. This means that
 * any payloads encoded in NaNs will be preserved even if the result of
 * this method is sent over the network from an x86 machine to a MIPS one.
 *
 * If the results of this method are only manipulated by the same
 * architecture that produced them, then there is no portability concern.
 *
 * If the input isn't NaN, then there is no portability concern.
 *
 * If you don't care about signalingness (very likely), then there is no
 * portability concern.
 *
 * Note that this function is distinct from `as` casting, which attempts to
 * preserve the *numeric* value, and not the bitwise value.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val v = F16.fromBits(0x4a40u.toUShort())
 *     check(v == F16.fromFloat(12.5f))
 * }
 * ```
 */
public fun F16.Companion.fromBits(v: UShort): F16 {
    // It turns out the safety issues with sNaN were overblown! Hooray!
    // SAFETY: `UShort` is a plain old datatype so we can always reinterpret from it.
    return transmuteWithLayouts<UShort, F16>(v, PrimitiveLayouts.U16, PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Returns the memory representation of this floating point number as a byte array in
 * big-endian (network) byte order.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val bytes = (F16.fromFloat(12.5f)).toBeBytes()
 *     check(bytes == ubyteArrayOf(0x4au, 0x40u))
 * }
 * ```
 */
public fun F16.toBeBytes(): UByteArray = run { val rawBits = this.toBits().toUInt(); ubyteArrayOf((rawBits shr 8).toUByte(), rawBits.toUByte()) }

/**
 * Returns the memory representation of this floating point number as a byte array in
 * little-endian byte order.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val bytes = (F16.fromFloat(12.5f)).toLeBytes()
 *     check(bytes == ubyteArrayOf(0x40u, 0x4au))
 * }
 * ```
 */
public fun F16.toLeBytes(): UByteArray = run { val rawBits = this.toBits().toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }

/**
 * Returns the memory representation of this floating point number as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate, instead.
 *
 * [toBeBytes]: f16.toBeBytes
 * [toLeBytes]: f16.toLeBytes
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val bytes = (F16.fromFloat(12.5f)).toLeBytes()
 *     check(bytes == if (false) {
 *         listOf(0x4a, 0x40)
 *     } else {
 *         listOf(0x40, 0x4a)
 *     })
 * }
 * ```
 */
public fun F16.toNeBytes(): UByteArray = run { val rawBits = this.toBits().toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }

/**
 * Creates a floating point value from its representation as a byte array in big endian.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val value = F16.fromBeBytes(ubyteArrayOf(0x4au, 0x40u))
 *     check(value == F16.fromFloat(12.5f))
 * }
 * ```
 */
public fun F16.Companion.fromBeBytes(bytes: UByteArray): F16 = F16.fromBits(run { val bytesTmp0 = bytes; ((bytesTmp0[0].toInt() shl 8) or bytesTmp0[1].toInt()).toUShort() })

/**
 * Creates a floating point value from its representation as a byte array in little endian.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val value = F16.fromLeBytes(ubyteArrayOf(0x40u, 0x4au))
 *     check(value == F16.fromFloat(12.5f))
 * }
 * ```
 */
public fun F16.Companion.fromLeBytes(bytes: UByteArray): F16 = F16.fromBits(run { val bytesTmp1 = bytes; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })

/**
 * Creates a floating point value from its representation as a byte array in native endian.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: f16.fromBeBytes
 * [fromLeBytes]: f16.fromLeBytes
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val value = F16.fromLeBytes(if (false) {
 *         ubyteArrayOf(0x4au, 0x40u)
 *     } else {
 *         ubyteArrayOf(0x40u, 0x4au)
 *     })
 *     check(value == F16.fromFloat(12.5f))
 * }
 * ```
 */
public fun F16.Companion.fromNeBytes(bytes: UByteArray): F16 = F16.fromBits(run { val bytesTmp2 = bytes; ((bytesTmp2[1].toInt() shl 8) or bytesTmp2[0].toInt()).toUShort() })

/**
 * Returns the ordering between `self` and `other`.
 *
 * Unlike the standard partial comparison between floating point numbers,
 * this comparison always produces an ordering in accordance to
 * the `totalOrder` predicate as defined in the IEEE 754 (2008 revision)
 * floating point standard. The values are ordered in the following sequence:
 *
 * - negative quiet NaN
 * - negative signaling NaN
 * - negative infinity
 * - negative numbers
 * - negative subnormal numbers
 * - negative zero
 * - positive zero
 * - positive subnormal numbers
 * - positive numbers
 * - positive infinity
 * - positive signaling NaN
 * - positive quiet NaN.
 *
 * The ordering established by this function does not always agree with the
 * [PartialOrd] and [PartialEq] implementations of `f16`. For example,
 * they consider negative and positive zero equal, while `totalCmp`
 * doesn't.
 *
 * The interpretation of the signaling NaN bit follows the definition in
 * the IEEE 754 standard, which may not match the interpretation by some of
 * the older, non-conformant (e.g. MIPS) hardware implementations.
 *
 * # Example
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     open class GoodBoy(public var name: String, public var weight: F16)
 *     var bois = mutableListOf(GoodBoy(name = "Pucci", weight = F16.fromFloat(0.1f)), GoodBoy(name = "Woofer", weight = F16.fromFloat(99.0f)), GoodBoy(name = "Yapper", weight = F16.fromFloat(10.0f)), GoodBoy(name = "Chonk", weight = F16.INFINITY), GoodBoy(name = "Abs. Unit", weight = F16.NAN), GoodBoy(name = "Floaty", weight = -F16.fromFloat(5.0f)))
 *     bois.sortBy({ a, b -> a.weight.totalCmp(b.weight) })
 *     // `f16.NAN` could be positive or negative, which will affect the sort order.
 *     if (F16.NAN.isSignNegative()) {
 *         (ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(listOf(F16.NAN, -5.0, 0.1, 10.0, 99.0, F16.INFINITY))).forEach({ (a, b) -> check(a.toBits() == b.toBits()) })
 *     } else {
 *         (ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(listOf(-5.0, 0.1, 10.0, 99.0, F16.INFINITY, F16.NAN))).forEach({ (a, b) -> check(a.toBits() == b.toBits()) })
 *     }
 * }
 * ```
 */
public fun F16.totalCmp(other: F16): Ordering {
    var left = this.toBits().toShort()
    var right = other.toBits().toShort()
    // In case of negatives, flip all the bits except the sign
    // to achieve a similar layout as two's complement integers
    //
    // Why does this work? IEEE 754 floats consist of three fields:
    // Sign bit, exponent and mantissa. The set of exponent and mantissa
    // fields as a whole have the property that their bitwise order is
    // equal to the numeric magnitude where the magnitude is defined.
    // The magnitude is not normally defined on NaN values, but
    // IEEE 754 totalOrder defines the NaN values also to follow the
    // bitwise order. This leads to order explained in the doc comment.
    // However, the representation of magnitude is the same for negative
    // and positive numbers – only the sign bit is different.
    // To easily compare the floats as signed integers, we need to
    // flip the exponent and mantissa bits in case of negative numbers.
    // We effectively convert the numbers to "two's complement" form.
    //
    // To do the flipping, we construct a mask and XOR against it.
    // We branchlessly calculate an "all-ones except for the sign bit"
    // mask from negative-signed values: right shifting sign-extends
    // the integer, so we "fill" the mask with sign bits, and then
    // convert to unsigned to push one more zero bit.
    // On positive values, the mask is all zeros, so it's a no-op.
    left = left xor (((((left shr 15).toUShort()).toUInt() shr 1).toUShort()).toShort())
    right = right xor (((((right shr 15).toUShort()).toUInt() shr 1).toUShort()).toShort())
    return left.cmp(right)
}

/**
 * Restrict a value to a certain interval unless it is NaN.
 *
 * Returns `max` if `self` is greater than `max`, and `min` if `self` is
 * less than `min`. Otherwise this returns `self`.
 *
 * Note that this function returns NaN if the initial value was NaN as
 * well. If the result is zero and among the three inputs `self`, `min`, and `max` there are
 * zeros with different sign, either `0.0` or `- 0.0` is returned non-deterministically.
 *
 * # Panics
 *
 * Panics if `min> max`, `min` is NaN, or `max` is NaN.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     check((-F16.fromFloat(3.0f)).clamp(-2.0, 1.0) == -2.0)
 *     check((F16.fromFloat(0.0f)).clamp(-2.0, 1.0) == 0.0)
 *     check((F16.fromFloat(2.0f)).clamp(-2.0, 1.0) == 1.0)
 *     check((F16.NAN).clamp(-2.0, 1.0).isNan())
 *     // These always returns zero, but the sign (which is ignored by `==`) is non-deterministic.
 *     check((F16.fromFloat(0.0f)).clamp(-0.0, -0.0) == 0.0)
 *     check((F16.fromFloat(1.0f)).clamp(-0.0, 0.0) == 0.0)
 *     // This is definitely a negative zero.
 *     check((-F16.fromFloat(1.0f)).clamp(-0.0, 1.0).isSignNegative())
 * }
 * ```
 */
public fun F16.clamp(min: F16, max: F16): F16 {
    var selfMut = this
    constAssert(min <= max, "min > max, or either was NaN", "min > max, or either was NaN. min = {min:?}, max = {max:?}", min, max)
    if (selfMut < min) {
        selfMut = min
    }
    if (selfMut > max) {
        selfMut = max
    }
    return selfMut
}

/**
 * Clamps this number to a symmetric range centered around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Panics
 *
 * Panics if `limit` is negative or NaN, as this indicates a logic error.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * @feature(clampMagnitude)
 * public fun doctestEntry() {
 *     check(F16.fromFloat(5.0f).clampMagnitude(3.0) == 3.0)
 *     check((-F16.fromFloat(5.0f)).clampMagnitude(3.0) == -3.0)
 *     check(F16.fromFloat(2.0f).clampMagnitude(3.0) == 2.0)
 *     check((-F16.fromFloat(2.0f)).clampMagnitude(3.0) == -2.0)
 * }
 * ```
 */
public fun F16.clampMagnitude(limit: F16): F16 {
    check(limit >= F16.fromFloat(0.0f)) { "limit must be non-negative and not NaN" }
    val limit1 = F16.fromFloat(kmFabsf32(limit.toFloat()))
    // Canonicalises -0.0 to 0.0
    return this.clamp(-limit1, limit1)
}

/**
 * Restrict a value to a certain range, unless it is NaN.
 *
 * This is largely equal to `max`, `min`, or `clamp`, depending on whether the range is
 * `min ..`, `..= max`, or `min ..= max`, respectively. However, unlike `max` and `min`, it will
 * panic if any bound is NaN.
 *
 * Note that this function returns NaN if the initial value was NaN as
 * well.
 *
 * Exclusive ranges are not permitted.
 *
 * # Panics
 *
 * Panics on `min ..= max` if `min> max`, or if any bound is NaN.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16, clampTo)
 * public fun doctestEntry() {
 *     check((-F16.fromFloat(3.0f)).clampTo(-2.0..1.0) == -2.0)
 *     check(F16.fromFloat(0.0f).clampTo(-2.0..1.0) == 0.0)
 *     check(F16.fromFloat(2.0f).clampTo(0..1.0) == 1.0)
 *     check(F16.fromFloat(5.0f).clampTo(7.0 until Int.MAX_VALUE) == 7.0)
 *     check(F16.NAN.clampTo(1.0..2.0).isNan())
 * }
 * ```
 */
public fun <R> F16.clampTo(range: R): F16 where R : ClampBounds<F16> = range.clamp(this)

/**
 * Computes the absolute value of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(3.5f)
 *     val y = -F16.fromFloat(3.5f)
 *     check(x.abs() == x)
 *     check(y.abs() == -y)
 *     check(F16.NAN.abs().isNan())
 * }
 * ```
 */
public fun F16.abs(): F16 = floatUnaryWithLayout("fabs", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns a number that represents the sign of `self`.
 *
 * - `1.0` if the number is positive, `+ 0.0` or `INFINITY`
 * - `- 1.0` if the number is negative, `- 0.0` or `NEG_INFINITY`
 * - NaN if the number is NaN
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.5f)
 *     check(f.signum() == 1.0)
 *     check(F16.NEG_INFINITY.signum() == -1.0)
 *     check(F16.NAN.signum().isNan())
 * }
 * ```
 */
public fun F16.signum(): F16 =
    if (this.isNaN()) {
        F16.NAN
    } else {
        F16.fromFloat(kmCopysignf32((F16.fromFloat(1.0f)).toFloat(), this.toFloat()))
    }

/**
 * Returns a number composed of the magnitude of `self` and the sign of
 * `sign`.
 *
 * Equal to `self` if the sign of `self` and `sign` are the same, otherwise equal to `- self`.
 * If `self` is a NaN, then a NaN with the same payload as `self` and the sign bit of `sign` is
 * returned.
 *
 * If `sign` is a NaN, then this operation will still carry over its sign into the result. Note
 * that IEEE 754 doesn't assign any meaning to the sign bit in case of a NaN, and as Rust
 * doesn't guarantee that the bit pattern of NaNs are conserved over arithmetic operations, the
 * result of `copysign` with `sign` being a NaN might produce an unexpected or non-portable
 * result. See the [specification of NaN bit patterns](primitive@f32#nan-bit-patterns) for more
 * info.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.5f)
 *     check(f.copysign(0.42) == F16.fromFloat(3.5f))
 *     check(f.copysign(-0.42) == -F16.fromFloat(3.5f))
 *     check((-f).copysign(0.42) == F16.fromFloat(3.5f))
 *     check((-f).copysign(-0.42) == -F16.fromFloat(3.5f))
 *     check(F16.NAN.copysign(1.0).isNan())
 * }
 * ```
 */
public fun F16.copysign(sign: F16): F16 = copysignf16(this, sign)

/**
 * Float addition that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F16.algebraicAdd(rhs: F16): F16 = floatBinaryWithLayout("fadd", this, rhs, PrimitiveLayouts.F16_LAYOUT)

/**
 * Float subtraction that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F16.algebraicSub(rhs: F16): F16 = floatBinaryWithLayout("fsub", this, rhs, PrimitiveLayouts.F16_LAYOUT)

/**
 * Float multiplication that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F16.algebraicMul(rhs: F16): F16 = floatBinaryWithLayout("fmul", this, rhs, PrimitiveLayouts.F16_LAYOUT)

/**
 * Float division that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F16.algebraicDiv(rhs: F16): F16 = floatBinaryWithLayout("fdiv", this, rhs, PrimitiveLayouts.F16_LAYOUT)

/**
 * Float remainder that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F16.algebraicRem(rhs: F16): F16 = floatBinaryWithLayout("frem", this, rhs, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns `self` if the value is not NaN, otherwise returns `replacement`
 * if `self` is NaN.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * @feature(floatNanTo)
 * public fun doctestEntry() {
 *     val n = F16.NAN
 *     val x = F16.fromFloat(2.0f)
 *     val y = F16.INFINITY
 *     check(n.nanTo(F16.fromFloat(0.0f)) == F16.fromFloat(0.0f))
 *     check(x.nanTo(F16.fromFloat(0.0f)) == F16.fromFloat(2.0f))
 *     check(y.nanTo(F16.fromFloat(0.0f)) == F16.INFINITY)
 * }
 * ```
 */
public fun F16.nanTo(replacement: F16): F16 =
    if (this.isNaN()) {
        replacement
    } else {
        this
    }

// Functions in this module fall into `coreFloatMath`
// @unstable(feature = "coreFloatMath", issue = "137578")

/**
 * Returns the largest integer less than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.7f)
 *     val g = F16.fromFloat(3.0f)
 *     val h = -F16.fromFloat(3.7f)
 *     check(f.floor() == 3.0)
 *     check(g.floor() == 3.0)
 *     check(h.floor() == -4.0)
 * }
 * ```
 */
public fun F16.floor(): F16 = floatUnaryWithLayout("floor", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns the smallest integer greater than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.01f)
 *     val g = F16.fromFloat(4.0f)
 *     check(f.ceil() == 4.0)
 *     check(g.ceil() == 4.0)
 * }
 * ```
 */
public fun F16.ceil(): F16 = floatUnaryWithLayout("ceil", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns the nearest integer to `self`. If a value is half-way between two
 * integers, round away from `0.0`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.3f)
 *     val g = -F16.fromFloat(3.3f)
 *     val h = -F16.fromFloat(3.7f)
 *     val i = F16.fromFloat(3.5f)
 *     val j = F16.fromFloat(4.5f)
 *     check(f.round() == 3.0)
 *     check(g.round() == -3.0)
 *     check(h.round() == -4.0)
 *     check(i.round() == 4.0)
 *     check(j.round() == 5.0)
 * }
 * ```
 */
public fun F16.round(): F16 = floatUnaryWithLayout("round", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns the nearest integer to a number. Rounds half-way cases to the number
 * with an even least significant digit.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.3f)
 *     val g = -F16.fromFloat(3.3f)
 *     val h = F16.fromFloat(3.5f)
 *     val i = F16.fromFloat(4.5f)
 *     check(f.roundTiesEven() == 3.0)
 *     check(g.roundTiesEven() == -3.0)
 *     check(h.roundTiesEven() == 4.0)
 *     check(i.roundTiesEven() == 4.0)
 * }
 * ```
 */
public fun F16.roundTiesEven(): F16 = floatUnaryWithLayout("round_ties_even", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns the integer part of `self`.
 * This means that non-integer numbers are always truncated towards zero.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val f = F16.fromFloat(3.7f)
 *     val g = F16.fromFloat(3.0f)
 *     val h = -F16.fromFloat(3.7f)
 *     check(f.trunc() == 3.0)
 *     check(g.trunc() == 3.0)
 *     check(h.trunc() == -3.0)
 * }
 * ```
 */
public fun F16.trunc(): F16 = floatUnaryWithLayout("trunc", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns the fractional part of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(3.6f)
 *     val y = -F16.fromFloat(3.6f)
 *     val absDifferenceX = (x.fract() - 0.6).abs()
 *     val absDifferenceY = (y.fract() - (-0.6)).abs()
 *     check(absDifferenceX <= F16.EPSILON)
 *     check(absDifferenceY <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.fract(): F16 = this - F16.fromFloat(kmTruncf32(this.toFloat()))

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val m = F16.fromFloat(10.0f)
 *     val x = F16.fromFloat(4.0f)
 *     val b = F16.fromFloat(60.0f)
 *     check(m.mulAdd(x, b) == 100.0)
 *     check(m * x + b == F16.fromFloat(100.0f))
 *     val onePlusEps = F16.fromFloat(1.0f) + F16.EPSILON
 *     val oneMinusEps = F16.fromFloat(1.0f) - F16.EPSILON
 *     val minusOne = -F16.fromFloat(1.0f)
 *     // The exact result (1 + eps) * (1 - eps) = 1 - eps * eps.
 *     check(onePlusEps.mulAdd(oneMinusEps, minusOne) == -F16.EPSILON * F16.EPSILON)
 *     // Different rounding with the non-fused multiply and add.
 *     check(onePlusEps * oneMinusEps + minusOne == F16.fromFloat(0.0f))
 * }
 * ```
 */
public fun F16.mulAdd(a: F16, b: F16): F16 = floatFmaWithLayout(this, a, b, PrimitiveLayouts.F16_LAYOUT)

/**
 * Computes `(self * a) + b` with nondeterministic rounding.
 *
 * This is similar to [`mulAdd`](Self.mulAdd), but the intermediate
 * result may be rounded differently depending on the implementation.
 * The operation is either executed as a single fused multiply-add
 * instruction, or as separate multiply and add instructions.
 *
 * The choice of which one is used is unspecified and non-deterministic:
 * it may vary by target, optimization level, and surrounding code, and
 * even two invocations of this operation with the same inputs may
 * produce different results.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * @feature(floatMulAddRelaxed)
 * public fun doctestEntry() {
 *     // When the fused and unfused operations round differently, either
 *     // result may be returned:
 *     // - -7.03e-6 is the fused result (one rounding)
 *     // - -7.6e-6 is the unfused result (two roundings)
 *     val r = F16.fromFloat(0.1f).mulAddRelaxed(F16.fromFloat(0.1f), -F16.fromFloat(0.01f))
 *     check(r == -7.03e-6 || r == -7.6e-6)
 * }
 * ```
 */
public fun F16.mulAddRelaxed(a: F16, b: F16): F16 = floatFmaWithLayout(this, a, b, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val a: F16 = F16.fromFloat(7.0f)
 *     val b = 4.0
 *     check(a.divEuclid(b) == 1.0)
 *     // 7.0 > 4.0 * 1.0
 *     check((-a).divEuclid(b) == -2.0)
 *     // -7.0 >= 4.0 * -2.0
 *     check(a.divEuclid(-b) == -1.0)
 *     // 7.0 >= -4.0 * -1.0
 *     check((-a).divEuclid(-b) == 2.0)
 *     // -7.0 >= -4.0 * 2.0
 * }
 * ```
 */
public fun F16.divEuclid(rhs: F16): F16 {
    val q = F16.fromFloat(kmTruncf32((this / rhs).toFloat()))
    if (this % rhs < F16.fromFloat(0.0f)) {
        return if (rhs > F16.fromFloat(0.0f)) {
            q - F16.fromFloat(1.0f)
        } else {
            q + F16.fromFloat(1.0f)
        }
    }
    return q
}

/**
 * Calculates the least nonnegative remainder of `self` when
 * divided by `rhs`.
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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val a: F16 = F16.fromFloat(7.0f)
 *     val b = 4.0
 *     check(a.remEuclid(b) == 3.0)
 *     check((-a).remEuclid(b) == 1.0)
 *     check(a.remEuclid(-b) == 3.0)
 *     check((-a).remEuclid(-b) == 1.0)
 *     // limitation due to round-off error
 *     check((-F16.EPSILON).remEuclid(F16.fromFloat(3.0f)) != 0.0)
 * }
 * ```
 */
public fun F16.remEuclid(rhs: F16): F16 {
    val r = this % rhs
    return if (r < F16.fromFloat(0.0f)) {
        r + F16.fromFloat(kmFabsf32(rhs.toFloat()))
    } else {
        r
    }
}

/**
 * Raises a number to an integer power.
 *
 * Using this function is generally faster than using `powf`.
 * It might have a different sequence of rounding operations than `powf`,
 * so the results are not guaranteed to agree.
 *
 * Note that this function is special in that it can return non-NaN results for NaN inputs. For
 * example, `f16.powi(f16.NAN, 0)` returns `1.0`. However, if an input is a *signaling*
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
 *     val absDifference = (x.powi(2) - (x * x)).abs()
 *     check(absDifference <= 0.1)
 *     check(F16.powi(F16.NAN, 0) == 1.0)
 *     check(F16.powi(0.0, 0) == 1.0)
 * }
 * ```
 */
public fun F16.powi(n: Int): F16 = floatPowiWithLayout(this, n, PrimitiveLayouts.F16_LAYOUT)

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
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val positive = F16.fromFloat(4.0f)
 *     val negative = -F16.fromFloat(4.0f)
 *     val negativeZero = -F16.fromFloat(0.0f)
 *     check(positive.sqrt() == 2.0)
 *     check(negative.sqrt().isNan())
 *     check(negativeZero.sqrt() == negativeZero)
 * }
 * ```
 */
public fun F16.sqrt(): F16 = floatUnaryWithLayout("sqrt", this, PrimitiveLayouts.F16_LAYOUT)

/**
 * Returns the cube root of a number.
 *
 * # Unspecified precision
 *
 * The precision of this function is non-deterministic. This means it varies by platform,
 * Rust version, and can even differ within the same execution from one invocation to the next.
 *
 * This function currently corresponds to the `cbrtf` from libc on Unix
 * and Windows. Note that this might change in the future.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F16
 *
 * @feature(f16)
 * public fun doctestEntry() {
 *     val x = F16.fromFloat(8.0f)
 *     // x^(1/3) - 2 == 0
 *     val absDifference = (x.cbrt() - 2.0).abs()
 *     check(absDifference <= F16.EPSILON)
 * }
 * ```
 */
public fun F16.cbrt(): F16 = F16.fromDouble(cbrtf(this.toFloat()).toDouble())
