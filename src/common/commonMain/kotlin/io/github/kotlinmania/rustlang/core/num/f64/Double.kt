// port-lint: source num/f64.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.f64

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.clamp
import io.github.kotlinmania.rustlang.core.cmp.clamp.ClampBounds
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.kmCeilf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFmaf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMaxnumf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMinnumf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmPowif64
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundTiesEvenf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf64
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.floatBinaryWithLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.FpCategory
import io.github.kotlinmania.rustlang.core.num.imp.libm.fdim
import io.github.kotlinmania.rustlang.core.panic.constAssert
import io.github.kotlinmania.rustlang.core.result.Result

// Constants for the `Double` double-precision floating point type.
//
// *[See also the `Double` primitive type][f64].*
//
// Mathematically significant numbers are provided in the `consts` sub-module.
//
// For the constants defined directly in this module
// (as distinct from those defined in the `consts` sub-module),
// new code should instead use the associated constants
// defined directly on the `Double` type.

/**
 * The radix or base of the internal representation of `Double`.
 * Use [f64.Radix] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val r = 2u
 * // intended way
 * val r1 = 2u
 * ```
 */
public val RADIX: UInt = 2u

/**
 * Number of significant digits in base 2.
 * Use [f64.MantissaDigits] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val d = 53u
 * // intended way
 * val d1 = 53u
 * ```
 */
public val MANTISSA_DIGITS: UInt = 53u

/**
 * Approximate number of significant digits in base 10.
 * Use [f64.Digits] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val d = 15u
 * // intended way
 * val d1 = 15u
 * ```
 */
public val DIGITS: UInt = 15u

/**
 * [Machine epsilon] value for `Double`.
 * Use [f64.Epsilon] instead.
 *
 * This is the difference between `1.0` and the next larger representable number.
 *
 * [Machine epsilon]: https://en.wikipedia.org/wiki/MachineEpsilon
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val e = 2.220446049250313E-16
 * // intended way
 * val e1 = 2.220446049250313E-16
 * ```
 */
public val EPSILON: Double = 2.220446049250313E-16

/**
 * Smallest finite `Double` value.
 * Use [f64.Min] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = (-Double.MAX_VALUE)
 * // intended way
 * val min1 = (-Double.MAX_VALUE)
 * ```
 */
public val MIN: Double = (-Double.MAX_VALUE)

/**
 * Smallest positive normal `Double` value.
 * Use [f64.MinPositive] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = 2.2250738585072014E-308
 * // intended way
 * val min1 = 2.2250738585072014E-308
 * ```
 */
public val MIN_POSITIVE: Double = 2.2250738585072014E-308

/**
 * Largest finite `Double` value.
 * Use [f64.Max] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val max = Double.MAX_VALUE
 * // intended way
 * val max1 = Double.MAX_VALUE
 * ```
 */
public val MAX: Double = Double.MAX_VALUE

/**
 * One greater than the minimum possible normal power of 2 exponent.
 * Use [f64.MinExp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = (-1021)
 * // intended way
 * val min1 = (-1021)
 * ```
 */
public val MIN_EXP: Int = (-1021)

/**
 * Maximum possible power of 2 exponent.
 * Use [f64.MaxExp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val max = 1024
 * // intended way
 * val max1 = 1024
 * ```
 */
public val MAX_EXP: Int = 1024

/**
 * Minimum possible normal power of 10 exponent.
 * Use [f64.Min10Exp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = (-307)
 * // intended way
 * val min1 = (-307)
 * ```
 */
public val MIN_10_EXP: Int = (-307)

/**
 * Maximum possible power of 10 exponent.
 * Use [f64.Max10Exp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val max = 308
 * // intended way
 * val max1 = 308
 * ```
 */
public val MAX_10_EXP: Int = 308

/**
 * Not a Number (NaN).
 * Use [f64.Nan] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val nan = Double.NaN
 * // intended way
 * val nan1 = Double.NaN
 * ```
 */
public val NAN: Double = Double.NaN

/**
 * Infinity (∞).
 * Use [f64.Infinity] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val inf = Double.POSITIVE_INFINITY
 * // intended way
 * val inf1 = Double.POSITIVE_INFINITY
 * ```
 */
public val INFINITY: Double = Double.POSITIVE_INFINITY

/**
 * Negative infinity (−∞).
 * Use [f64.NegInfinity] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val ninf = Double.NEGATIVE_INFINITY
 * // intended way
 * val ninf1 = Double.NEGATIVE_INFINITY
 * ```
 */
public val NEG_INFINITY: Double = Double.NEGATIVE_INFINITY

// Note: replace with mathematical constants from cmath.

/**
 * Archimedes' constant (π)
 */
public val PI: Double = 3.14159265358979323846264338327950288

/**
 * The full circle constant (τ)
 *
 * Equal to 2π.
 */
public val TAU: Double = 6.28318530717958647692528676655900577

/**
 * The golden ratio (φ)
 */
public val GOLDEN_RATIO: Double = 1.618033988749894848204586834365638118

/**
 * The Euler-Mascheroni constant (γ)
 */
public val EULER_GAMMA: Double = 0.577215664901532860606512090082402431

/**
 * π/2
 */
public val FRAC_PI_2: Double = 1.57079632679489661923132169163975144

/**
 * π/3
 */
public val FRAC_PI_3: Double = 1.04719755119659774615421446109316763

/**
 * π/4
 */
public val FRAC_PI_4: Double = 0.785398163397448309615660845819875721

/**
 * π/6
 */
public val FRAC_PI_6: Double = 0.52359877559829887307710723054658381

/**
 * π/8
 */
public val FRAC_PI_8: Double = 0.39269908169872415480783042290993786

/**
 * 1/π
 */
public val FRAC_1_PI: Double = 0.318309886183790671537767526745028724

/**
 * 1/sqrt(π)
 */
public val FRAC_1_SQRT_PI: Double = 0.564189583547756286948079451560772586

/**
 * 1/sqrt(2π)
 */
public val FRAC_1_SQRT_2PI: Double = 0.398942280401432677939946059934381868

/**
 * 2/π
 */
public val FRAC_2_PI: Double = 0.636619772367581343075535053490057448

/**
 * 2/sqrt(π)
 */
public val FRAC_2_SQRT_PI: Double = 1.12837916709551257389615890312154517

/**
 * sqrt(2)
 */
public val SQRT_2: Double = 1.41421356237309504880168872420969808

/**
 * 1/sqrt(2)
 */
public val FRAC_1_SQRT_2: Double = 0.707106781186547524400844362104849039

/**
 * sqrt(3)
 */
public val SQRT_3: Double = 1.732050807568877293527446341505872367

/**
 * 1/sqrt(3)
 */
public val FRAC_1_SQRT_3: Double = 0.577350269189625764509148780501957456

/**
 * sqrt(5)
 */
public val SQRT_5: Double = 2.23606797749978969640917366873127623

/**
 * 1/sqrt(5)
 */
public val FRAC_1_SQRT_5: Double = 0.44721359549995793928183473374625524

/**
 * Euler's number (e)
 */
public val E: Double = 2.71828182845904523536028747135266250

/**
 * log<sub>2</sub>(10)
 */
public val LOG2_10: Double = 3.32192809488736234787031942948939018

/**
 * log<sub>2</sub>(e)
 */
public val LOG2_E: Double = 1.44269504088896340735992468100189214

/**
 * log<sub>10</sub>(2)
 */
public val LOG10_2: Double = 0.301029995663981195213738894724493027

/**
 * log<sub>10</sub>(e)
 */
public val LOG10_E: Double = 0.434294481903251827651128918916605082

/**
 * ln(2)
 */
public val LN_2: Double = 0.693147180559945309417232121458176568

/**
 * ln(10)
 */
public val LN_10: Double = 2.30258509299404568401799145468436421

// > if you intended to check if the operand is NaN, use `.isNan()` instead :)

/**
 * Returns `true` if this value is NaN.
 *
 * ```kotlin
 * val nan = Double.NaN
 * val f = 7.0
 * check(nan.isNan())
 * check(!f.isNan())
 * ```
 */
public fun Double.isNan(): Boolean = this != this

/**
 * Returns `true` if this value is positive infinity or negative infinity, and
 * `false` otherwise.
 *
 * ```kotlin
 * val f = 7.0
 * val inf = Double.POSITIVE_INFINITY
 * val negInf = Double.NEGATIVE_INFINITY
 * val nan = Double.NaN
 * check(!f.isInfinite())
 * check(!nan.isInfinite())
 * check(inf.isInfinite())
 * check(negInf.isInfinite())
 * ```
 */
public fun Double.isInfinite(): Boolean {
    // Getting clever with transmutation can result in incorrect answers on some FPUs
    // Note: alter the Rust <-> Rust calling convention to prevent this problem.
    // See https://github.com/rust-lang/rust/issues/72327
    return (this == Double.POSITIVE_INFINITY) or (this == Double.NEGATIVE_INFINITY)
}

/**
 * Returns `true` if this number is neither infinite nor NaN.
 *
 * ```kotlin
 * val f = 7.0
 * val inf: Double = Double.POSITIVE_INFINITY
 * val negInf: Double = Double.NEGATIVE_INFINITY
 * val nan: Double = Double.NaN
 * check(f.isFinite())
 * check(!nan.isFinite())
 * check(!inf.isFinite())
 * check(!negInf.isFinite())
 * ```
 */
public fun Double.isFinite(): Boolean {
    // There's no need to handle NaN separately: if self is NaN,
    // the comparison is not true, exactly as desired.
    return kmFabsf64(this) < Double.POSITIVE_INFINITY
}

/**
 * Returns `true` if the number is [subnormal].
 *
 * ```kotlin
 * val min = 2.2250738585072014E-308
 * // 2.2250738585072014e-308_f64
 * val max = Double.MAX_VALUE
 * val lowerThanMin = 1.0e-308
 * val zero = 0.0
 * check(!min.isSubnormal())
 * check(!max.isSubnormal())
 * check(!zero.isSubnormal())
 * check(!Double.NaN.isSubnormal())
 * check(!Double.POSITIVE_INFINITY.isSubnormal())
 * // Values between `0` and `min` are Subnormal.
 * check(lowerThanMin.isSubnormal())
 * ```
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun Double.isSubnormal(): Boolean =
    when (this.classify()) {
        FpCategory.Subnormal -> true
        else -> false
    }

/**
 * Returns `true` if the number is neither zero, infinite,
 * [subnormal], or NaN.
 *
 * ```kotlin
 * val min = 2.2250738585072014E-308
 * // 2.2250738585072014e-308f64
 * val max = Double.MAX_VALUE
 * val lowerThanMin = 1.0e-308
 * val zero = 0.0
 * check(min.isNormal())
 * check(max.isNormal())
 * check(!zero.isNormal())
 * check(!Double.NaN.isNormal())
 * check(!Double.POSITIVE_INFINITY.isNormal())
 * // Values between `0` and `min` are Subnormal.
 * check(!lowerThanMin.isNormal())
 * ```
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun Double.isNormal(): Boolean =
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
 * import std.num.FpCategory
 *
 * val num = 12.4
 * val inf = Double.POSITIVE_INFINITY
 * check(num.classify() == FpCategory.Normal)
 * check(inf.classify() == FpCategory.Infinite)
 * ```
 */
public fun Double.classify(): FpCategory {
    // We used to have complicated logic here that avoids the simple bit-based tests to work
    // around buggy codegen for x87 targets (see
    // https://github.com/rust-lang/rust/issues/114479). However, some LLVM versions later, none
    // of our tests is able to find any difference between the complicated and the naive
    // version, so now we are back to the naive version.
    val b = this.toRawBits().toULong()
    return run {
        val matchVal = Pair(b and Double.MANTISSA_MASK, b and Double.EXPONENT_MASK)
        when {
            matchVal.first == 0 && matchVal.second == Double.EXPONENT_MASK -> FpCategory.Infinite
            matchVal.second == Double.EXPONENT_MASK -> FpCategory.Nan
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
 * val f = 7.0
 * val g = -7.0
 * check(f.isSignPositive())
 * check(!g.isSignPositive())
 * ```
 */
public fun Double.isSignPositive(): Boolean = !this.isSignNegative()

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
 * val f = 7.0
 * val g = -7.0
 * check(!f.isSignNegative())
 * check(g.isSignNegative())
 * ```
 */
public fun Double.isSignNegative(): Boolean {
    // IEEE754 says: isSignMinus(x) is true if and only if x has negative sign. isSignMinus
    // applies to zeros and NaNs as well.
    return this.toRawBits().toULong() and Double.SIGN_MASK != 0uL
}

/**
 * Returns the least number greater than `self`.
 *
 * Let `TINY` be the smallest representable positive `Double`. Then,
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
 * // f64.Epsilon is the difference between 1.0 and the next number up.
 * check(1.0.nextUp() == 1.0 + 2.220446049250313E-16)
 * // But not for most numbers.
 * check(0.1.nextUp() < 0.1 + 2.220446049250313E-16)
 * check(9007199254740992.0.nextUp() == 9007199254740994.0)
 * ```
 *
 * This operation corresponds to IEEE-754 `nextUp`.
 *
 * [NegInfinity]: Self.NegInfinity
 * [Infinity]: Self.Infinity
 * [Min]: Self.Min
 * [Max]: Self.Max
 */
public fun Double.nextUp(): Double {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toRawBits().toULong()
    if (this.isNaN() || bits == Double.POSITIVE_INFINITY.toBits()) {
        return this
    }
    val abs = bits and Double.SIGN_MASK.inv()
    val nextBits =
        if (abs == 0uL) {
            Double.TINY_BITS
        } else if (bits == abs) {
            bits + 1uL
        } else {
            bits - 1uL
        }
    return Double.fromBits(nextBits)
}

/**
 * Returns the greatest number less than `self`.
 *
 * Let `TINY` be the smallest representable positive `Double`. Then,
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
 * val x = 1.0
 * // Clamp value into range [0, 1).
 * val clamped = x.clamp(0.0, 1.0.nextDown())
 * check(clamped < 1.0)
 * check(clamped.nextUp() == 1.0)
 * ```
 *
 * This operation corresponds to IEEE-754 `nextDown`.
 *
 * [NegInfinity]: Self.NegInfinity
 * [Infinity]: Self.Infinity
 * [Min]: Self.Min
 * [Max]: Self.Max
 */
public fun Double.nextDown(): Double {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toRawBits().toULong()
    if (this.isNaN() || bits == Double.NEGATIVE_INFINITY.toBits()) {
        return this
    }
    val abs = bits and Double.SIGN_MASK.inv()
    val nextBits =
        if (abs == 0uL) {
            Double.NEG_TINY_BITS
        } else if (bits == abs) {
            bits - 1uL
        } else {
            bits + 1uL
        }
    return Double.fromBits(nextBits)
}

/**
 * Takes the reciprocal (inverse) of a number, `1 / x`.
 *
 * ```kotlin
 * val x = 2.0
 * val absDifference = (x.recip() - (1.0 / x)).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.recip(): Double = 1.0 / this

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
 * val angle = kotlin.math.PI
 * val absDifference = (angle.toDegrees() - 180.0).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.toDegrees(): Double {
    // The division here is correctly rounded with respect to the true value of 180/π.
    // Although π is irrational and already rounded, the double rounding happens
    // to produce correct result for f64.
    val PIS_IN_180: Double = 180.0 / PI
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
 * val angle = 180.0
 * val absDifference = (angle.toRadians() - kotlin.math.PI).abs()
 * check(absDifference < 1e-10)
 * ```
 */
public fun Double.toRadians(): Double {
    // The division here is correctly rounded with respect to the true value of π/180.
    // Although π is irrational and already rounded, the double rounding happens
    // to produce correct result for f64.
    val RADS_PER_DEG: Double = PI / 180.0
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
 * val x = 1.0
 * val y = 2.0
 * check(x.max(y) == y)
 * check(x.max(Double.NaN) == x)
 * ```
 */
public fun Double.max(other: Double): Double = kmMaxnumf64(this, other)

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
 * val x = 1.0
 * val y = 2.0
 * check(x.min(y) == x)
 * check(x.min(Double.NaN) == x)
 * ```
 */
public fun Double.min(other: Double): Double = kmMinnumf64(this, other)

/**
 * Returns the maximum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f64.max] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `maximum`.
 *
 * ```kotlin
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val y = 2.0
 *     check(x.maximum(y) == y)
 *     check(x.maximum(Double.NaN).isNan())
 * }
 * ```
 */
public fun Double.maximum(other: Double): Double = floatBinaryWithLayout("maximum", this, other, PrimitiveLayouts.F64)

/**
 * Returns the minimum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f64.min] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `minimum`.
 *
 * ```kotlin
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val y = 2.0
 *     check(x.minimum(y) == x)
 *     check(x.minimum(Double.NaN).isNan())
 * }
 * ```
 */
public fun Double.minimum(other: Double): Double = floatBinaryWithLayout("minimum", this, other, PrimitiveLayouts.F64)

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * This returns NaN when *either* argument is NaN or if a combination of
 * +inf and -inf is provided as arguments.
 *
 * # Examples
 *
 * ```kotlin
 * check(1.0.midpoint(4.0) == 2.5)
 * check((-5.5).midpoint(8.0) == 1.25)
 * ```
 */
public fun Double.midpoint(other: Double): Double {
    val HI: Double = Double.MAX_VALUE * 0.5
    val (a, b) = Pair(this, other)
    val absA = kmFabsf64(a)
    val absB = kmFabsf64(b)
    return if (absA <= HI && absB <= HI) {
        // Overflow is impossible
        (a + b) * 0.5
    } else {
        (a * 0.5) + (b * 0.5)
    }
}

/**
 * Rounds toward zero and converts to any primitive integer type,
 * assuming that the value is finite and fits in that type.
 *
 * ```kotlin
 * val value = 4.6
 * val rounded = value.toIntUnchecked<UShort>()
 * check(rounded == 4)
 * val value1 = -128.9
 * val rounded1 = value1.toIntUnchecked<Byte>()
 * check(rounded1 == Byte.MIN_VALUE)
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
public inline fun <reified Int> Double.toIntUnchecked(): Int {
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
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     val x = 1.5
 *     check(x.cast() == 1.5f)
 * }
 * ```
 */
public inline fun <reified Flt> Double.cast(): Flt = this.cast()

/**
 * Rounds toward zero and converts to any primitive integer type, saturating
 * at the type's boundaries and mapping `NaN` to zero.
 *
 * This is equivalent to `self as Int`.
 *
 * ```kotlin
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     check(255.5.toIntSaturating<UByte>() == 255)
 *     check(300.0.toIntSaturating<UByte>() == 255)
 *     check((-1.0).toIntSaturating<UByte>() == 0)
 *     check(Double.NaN.toIntSaturating<UByte>() == 0)
 * }
 * ```
 */
public inline fun <reified Int> Double.toIntSaturating(): Int = this.toIntSaturating()

/**
 * Rounds toward zero and converts to any primitive integer type, returning
 * `None` if the value is `NaN`, infinite, or does not fit in the target type.
 *
 * ```kotlin
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     check(255.5.toIntChecked<UByte>() == 255)
 *     check(256.0.toIntChecked<UByte>() == null)
 *     check(Double.NaN.toIntChecked<UByte>() == null)
 * }
 * ```
 */
public inline fun <reified Int> Double.toIntChecked(): Int? = this.toIntChecked()

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
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     check(255.5.toIntStrict<UByte>() == 255)
 * }
 * ```
 */
public inline fun <reified Int> Double.toIntStrict(): Int = (this.toIntChecked<Int>() ?: error("the value cannot be represented in the target integer type"))

/**
 * Raw transmutation to `ULong`.
 *
 * This is currently identical to `reinterpret<Double, ULong>(self)` on all platforms.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * Note that this function is distinct from `as` casting, which attempts to
 * preserve the *numeric* value, and not the bitwise value.
 *
 * # Examples
 *
 * ```kotlin
 * check((1.0).toBits() != 1.0.toULong())
 * // toBits() is not casting!
 * check((12.5).toRawBits().toULong() == 0x4029000000000000uL)
 * ```
 */
public fun Double.toBits(): ULong {
    // SAFETY: `ULong` is a plain old datatype so we can always reinterpret to it.
    return transmuteWithLayouts<Double, ULong>(this, UnresolvedTypeLayout<Double>(), PrimitiveLayouts.U64)
}

/**
 * Raw transmutation from `ULong`.
 *
 * This is currently identical to `reinterpret<ULong, Double>(v)` on all platforms.
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
 * If you don't care about signaling-ness (very likely), then there is no
 * portability concern.
 *
 * Note that this function is distinct from `as` casting, which attempts to
 * preserve the *numeric* value, and not the bitwise value.
 *
 * # Examples
 *
 * ```kotlin
 * val v = Double.fromBits(0x4029000000000000uL.toLong())
 * check(v == 12.5.toDouble())
 * ```
 */
public fun Double.Companion.fromBits(v: ULong): Double {
    // It turns out the safety issues with sNaN were overblown! Hooray!
    // SAFETY: `ULong` is a plain old datatype so we can always reinterpret from it.
    return transmuteWithLayouts<ULong, Double>(v, PrimitiveLayouts.U64, PrimitiveLayouts.F64)
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
 * public fun doctestEntry() {
 *     val bytes = run { val rawBits = (12.5).toRawBits().toULong(); ubyteArrayOf((rawBits shr 56).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) }
 *     check(bytes == ubyteArrayOf(0x40u, 0x29u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u))
 * }
 * ```
 */
public fun Double.toBeBytes(): UByteArray = run { val rawBits = this.toRawBits().toULong(); ubyteArrayOf((rawBits shr 56).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) }

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
 * public fun doctestEntry() {
 *     val bytes = run { val rawBits = (12.5).toRawBits().toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }
 *     check(bytes == ubyteArrayOf(0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x29u, 0x40u))
 * }
 * ```
 */
public fun Double.toLeBytes(): UByteArray = run { val rawBits = this.toRawBits().toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }

/**
 * Returns the memory representation of this floating point number as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate, instead.
 *
 * [toBeBytes]: f64.toBeBytes
 * [toLeBytes]: f64.toLeBytes
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     val bytes = run { val rawBits = (12.5).toRawBits().toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }
 *     check(bytes == if (false) {
 *         listOf(0x40, 0x29, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
 *     } else {
 *         listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x29, 0x40)
 *     })
 * }
 * ```
 */
public fun Double.toNeBytes(): UByteArray = run { val rawBits = this.toRawBits().toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }

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
 * public fun doctestEntry() {
 *     val value = run { val bytesTmp0 = ubyteArrayOf(0x40u, 0x29u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u); Double.fromBits((bytesTmp0[0].toLong() shl 56) or (bytesTmp0[1].toLong() shl 48) or (bytesTmp0[2].toLong() shl 40) or (bytesTmp0[3].toLong() shl 32) or (bytesTmp0[4].toLong() shl 24) or (bytesTmp0[5].toLong() shl 16) or (bytesTmp0[6].toLong() shl 8) or bytesTmp0[7].toLong()) }
 *     check(value == 12.5.toDouble())
 * }
 * ```
 */
public fun Double.Companion.fromBeBytes(bytes: UByteArray): Double = Double.fromBits(run { val bytesTmp0 = bytes; ((bytesTmp0[0].toULong() shl 56) or (bytesTmp0[1].toULong() shl 48) or (bytesTmp0[2].toULong() shl 40) or (bytesTmp0[3].toULong() shl 32) or (bytesTmp0[4].toULong() shl 24) or (bytesTmp0[5].toULong() shl 16) or (bytesTmp0[6].toULong() shl 8) or bytesTmp0[7].toULong()) })

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
 * public fun doctestEntry() {
 *     val value = run { val bytesTmp0 = ubyteArrayOf(0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x29u, 0x40u); Double.fromBits((bytesTmp0[7].toLong() shl 56) or (bytesTmp0[6].toLong() shl 48) or (bytesTmp0[5].toLong() shl 40) or (bytesTmp0[4].toLong() shl 32) or (bytesTmp0[3].toLong() shl 24) or (bytesTmp0[2].toLong() shl 16) or (bytesTmp0[1].toLong() shl 8) or bytesTmp0[0].toLong()) }
 *     check(value == 12.5.toDouble())
 * }
 * ```
 */
public fun Double.Companion.fromLeBytes(bytes: UByteArray): Double = Double.fromBits(run { val bytesTmp1 = bytes; ((bytesTmp1[7].toULong() shl 56) or (bytesTmp1[6].toULong() shl 48) or (bytesTmp1[5].toULong() shl 40) or (bytesTmp1[4].toULong() shl 32) or (bytesTmp1[3].toULong() shl 24) or (bytesTmp1[2].toULong() shl 16) or (bytesTmp1[1].toULong() shl 8) or bytesTmp1[0].toULong()) })

/**
 * Creates a floating point value from its representation as a byte array in native endian.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: f64.fromBeBytes
 * [fromLeBytes]: f64.fromLeBytes
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     val value = run { val bytesTmp0 = if (false) {
 *         ubyteArrayOf(0x40u, 0x29u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u)
 *     } else {
 *         ubyteArrayOf(0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x29u, 0x40u)
 *     }; Double.fromBits((bytesTmp0[7].toLong() shl 56) or (bytesTmp0[6].toLong() shl 48) or (bytesTmp0[5].toLong() shl 40) or (bytesTmp0[4].toLong() shl 32) or (bytesTmp0[3].toLong() shl 24) or (bytesTmp0[2].toLong() shl 16) or (bytesTmp0[1].toLong() shl 8) or bytesTmp0[0].toLong()) }
 *     check(value == 12.5.toDouble())
 * }
 * ```
 */
public fun Double.Companion.fromNeBytes(bytes: UByteArray): Double = Double.fromBits(run { val bytesTmp2 = bytes; ((bytesTmp2[7].toULong() shl 56) or (bytesTmp2[6].toULong() shl 48) or (bytesTmp2[5].toULong() shl 40) or (bytesTmp2[4].toULong() shl 32) or (bytesTmp2[3].toULong() shl 24) or (bytesTmp2[2].toULong() shl 16) or (bytesTmp2[1].toULong() shl 8) or bytesTmp2[0].toULong()) })

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
 * [PartialOrd] and [PartialEq] implementations of `Double`. For example,
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
 * open class GoodBoy(public var name: String, public var weight: Double)
 * var bois = mutableListOf(GoodBoy(name = "Pucci".toOwned(), weight = 0.1), GoodBoy(name = "Woofer".toOwned(), weight = 99.0), GoodBoy(name = "Yapper".toOwned(), weight = 10.0), GoodBoy(name = "Chonk".toOwned(), weight = Double.POSITIVE_INFINITY), GoodBoy(name = "Abs. Unit".toOwned(), weight = Double.NaN), GoodBoy(name = "Floaty".toOwned(), weight = -5.0))
 * bois.sortBy({ a, b -> a.weight.totalCmp(b.weight) })
 * // `Double.NAN` could be positive or negative, which will affect the sort order.
 * if (Double.NaN.isSignNegative()) {
 *     check((ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(doubleArrayOf(Double.NaN, -5.0, 0.1, 10.0, 99.0, Double.POSITIVE_INFINITY))).all({ (a, b) -> a.toBits() == b.toBits() }))
 * } else {
 *     check((ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(doubleArrayOf(-5.0, 0.1, 10.0, 99.0, Double.POSITIVE_INFINITY, Double.NaN))).all({ (a, b) -> a.toBits() == b.toBits() }))
 * }
 * ```
 */
public fun Double.totalCmp(other: Double): Ordering {
    var left = this.toRawBits().toULong().toLong()
    var right = other.toRawBits().toULong().toLong()
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
    left = left xor ((((left shr 63).toULong()) shr 1).toLong())
    right = right xor ((((right shr 63).toULong()) shr 1).toLong())
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
 * check((-3.0).clamp(-2.0, 1.0) == -2.0)
 * check((0.0).clamp(-2.0, 1.0) == 0.0)
 * check((2.0).clamp(-2.0, 1.0) == 1.0)
 * check((Double.NaN).clamp(-2.0, 1.0).isNan())
 * // These always returns zero, but the sign (which is ignored by `==`) is non-deterministic.
 * check((0.0).clamp(-0.0, -0.0) == 0.0)
 * check((1.0).clamp(-0.0, 0.0) == 0.0)
 * // This is definitely a negative zero.
 * check((-1.0).clamp(-0.0, 1.0).isSignNegative())
 * ```
 */
public fun Double.clamp(min: Double, max: Double): Double {
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
 * @feature(clampMagnitude)
 * public fun doctestEntry() {
 *     check(5.0.clampMagnitude(3.0) == 3.0)
 *     check((-5.0).clampMagnitude(3.0) == -3.0)
 *     check(2.0.clampMagnitude(3.0) == 2.0)
 *     check((-2.0).clampMagnitude(3.0) == -2.0)
 * }
 * ```
 */
public fun Double.clampMagnitude(limit: Double): Double {
    check(limit >= 0.0) { "limit must be non-negative and not NaN" }
    val limit1 = kmFabsf64(limit)
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
 * @feature(clampTo)
 * public fun doctestEntry() {
 *     check((-3.0).clampTo(-2.0..1.0) == -2.0)
 *     check(0.0.clampTo(-2.0..1.0) == 0.0)
 *     check(2.0.clampTo(0..1.0) == 1.0)
 *     check(5.0.clampTo(7.0 until Int.MAX_VALUE) == 7.0)
 *     check(Double.NaN.clampTo(1.0..2.0).isNan())
 * }
 * ```
 */
public fun <R> Double.clampTo(range: R): Double where R : ClampBounds<Double> = range.clamp(this)

/**
 * Computes the absolute value of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 3.5
 * val y = -3.5
 * check(x.abs() == x)
 * check(y.abs() == -y)
 * check(Double.NaN.abs().isNan())
 * ```
 */
public fun Double.abs(): Double = kmFabsf64(this)

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
 * val f = 3.5
 * check(f.signum() == 1.0)
 * check(Double.NEGATIVE_INFINITY.signum() == -1.0)
 * check(Double.NaN.signum().isNan())
 * ```
 */
public fun Double.signum(): Double =
    if (this.isNaN()) {
        Double.NaN
    } else {
        kmCopysignf64(1.0, this)
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
 * val f = 3.5
 * check(f.copysign(0.42) == 3.5)
 * check(f.copysign(-0.42) == -3.5)
 * check((-f).copysign(0.42) == 3.5)
 * check((-f).copysign(-0.42) == -3.5)
 * check(Double.NaN.copysign(1.0).isNan())
 * ```
 */
public fun Double.copysign(sign: Double): Double = kmCopysignf64(this, sign)

/**
 * Float addition that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun Double.algebraicAdd(rhs: Double): Double = ((this) + (rhs))

/**
 * Float subtraction that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun Double.algebraicSub(rhs: Double): Double = ((this) - (rhs))

/**
 * Float multiplication that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun Double.algebraicMul(rhs: Double): Double = ((this) * (rhs))

/**
 * Float division that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun Double.algebraicDiv(rhs: Double): Double = ((this) / (rhs))

/**
 * Float remainder that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun Double.algebraicRem(rhs: Double): Double = ((this) % (rhs))

/**
 * Computes `(self * a) + b` with nondeterministic rounding.
 *
 * This is similar to [mulAdd], but the intermediate result may be
 * rounded differently depending on the implementation. The operation is
 * either executed as a single fused multiply-add instruction, or as
 * separate multiply and add instructions.
 *
 * The choice of which one is used is unspecified and non-deterministic:
 * it may vary by target, optimization level, and surrounding code, and
 * even two invocations of this operation with the same inputs may
 * produce different results.
 *
 * # Precision
 *
 * The result of this operation is not guaranteed: it is either the result
 * of [mulAdd] (one rounding of the infinite-precision result) or of
 * `self * a + b` (two roundings, with an intermediate rounding of the
 * product).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatMulAddRelaxed)
 * public fun doctestEntry() {
 *     // When the fused and unfused operations round differently, either
 *     // result may be returned:
 *     // - 9.020562075079397e-19 is the fused result (one rounding)
 *     // - 1.734723475976807e-18 is the unfused result (two roundings)
 *     val r = 0.1.mulAddRelaxed(0.1, -0.01)
 *     check(r == 9.020562075079397e-19 || r == 1.734723475976807e-18)
 * }
 * ```
 *
 * [mulAdd]: ../std/primitive.f64.html#method.mulAdd
 */
public fun Double.mulAddRelaxed(a: Double, b: Double): Double = kmFmaf64(this, a, b)

/**
 * Returns `self` if the value is not NaN, otherwise returns `replacement`
 * if `self` is NaN.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatNanTo)
 * public fun doctestEntry() {
 *     val n = Double.NaN
 *     val x = 2.0
 *     val y = Double.POSITIVE_INFINITY
 *     check(n.nanTo(0.0) == 0.0)
 *     check(x.nanTo(0.0) == 2.0)
 *     check(y.nanTo(0.0) == Double.POSITIVE_INFINITY)
 * }
 * ```
 */
public fun Double.nanTo(replacement: Double): Double =
    if (this.isNaN()) {
        replacement
    } else {
        this
    }

/**
 * Experimental version of `floor` in `core`. See [f64.floor] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.7
 *     val g = 3.0
 *     val h = -3.7
 *     check(Double.floor(f) == 3.0)
 *     check(Double.floor(g) == 3.0)
 *     check(Double.floor(h) == -4.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.floor]: ../../../std/primitive.f64.html#method.floor
 */
public fun floor(x: Double): Double = kmFloorf64(x)

/**
 * Experimental version of `ceil` in `core`. See [f64.ceil] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.01
 *     val g = 4.0
 *     check(Double.ceil(f) == 4.0)
 *     check(Double.ceil(g) == 4.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.ceil]: ../../../std/primitive.f64.html#method.ceil
 */
public fun ceil(x: Double): Double = kmCeilf64(x)

/**
 * Experimental version of `round` in `core`. See [f64.round] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.3
 *     val g = -3.3
 *     val h = -3.7
 *     val i = 3.5
 *     val j = 4.5
 *     check(Double.round(f) == 3.0)
 *     check(Double.round(g) == -3.0)
 *     check(Double.round(h) == -4.0)
 *     check(Double.round(i) == 4.0)
 *     check(Double.round(j) == 5.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.round]: ../../../std/primitive.f64.html#method.round
 */
public fun round(x: Double): Double = kmRoundf64(x)

/**
 * Experimental version of `roundTiesEven` in `core`. See [f64.roundTiesEven] for
 * details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.3
 *     val g = -3.3
 *     val h = 3.5
 *     val i = 4.5
 *     check(Double.roundTiesEven(f) == 3.0)
 *     check(Double.roundTiesEven(g) == -3.0)
 *     check(Double.roundTiesEven(h) == 4.0)
 *     check(Double.roundTiesEven(i) == 4.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.roundTiesEven]: ../../../std/primitive.f64.html#method.roundTiesEven
 */
public fun roundTiesEven(x: Double): Double = kmRoundTiesEvenf64(x)

/**
 * Experimental version of `trunc` in `core`. See [f64.trunc] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.7
 *     val g = 3.0
 *     val h = -3.7
 *     check(Double.trunc(f) == 3.0)
 *     check(Double.trunc(g) == 3.0)
 *     check(Double.trunc(h) == -3.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.trunc]: ../../../std/primitive.f64.html#method.trunc
 */
public fun trunc(x: Double): Double = kmTruncf64(x)

/**
 * Experimental version of `fract` in `core`. See [f64.fract] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 3.6
 *     val y = -3.6
 *     val absDifferenceX = (Double.fract(x) - 0.6).abs()
 *     val absDifferenceY = (Double.fract(y) - (-0.6)).abs()
 *     check(absDifferenceX < 1e-10)
 *     check(absDifferenceY < 1e-10)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.fract]: ../../../std/primitive.f64.html#method.fract
 */
public fun fract(x: Double): Double = x - trunc(x)

/**
 * Experimental version of `mulAdd` in `core`. See [f64.mulAdd] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val m = 10.0
 *     val x = 4.0
 *     val b = 60.0
 *     check(Double.mulAdd(m, x, b) == 100.0)
 *     check(m * x + b == 100.0.toDouble())
 *     val onePlusEps = 1.0 + 2.220446049250313E-16
 *     val oneMinusEps = 1.0 - 2.220446049250313E-16
 *     val minusOne = -1.0
 *     // The exact result (1 + eps) * (1 - eps) = 1 - eps * eps.
 *     check(Double.mulAdd(onePlusEps, oneMinusEps, minusOne) == -2.220446049250313E-16 * 2.220446049250313E-16)
 *     // Different rounding with the non-fused multiply and add.
 *     check(onePlusEps * oneMinusEps + minusOne == 0.0.toDouble())
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.mulAdd]: ../../../std/primitive.f64.html#method.mulAdd
 */
public fun mulAdd(x: Double, a: Double, b: Double): Double = kmFmaf64(x, a, b)

/**
 * Experimental version of `divEuclid` in `core`. See [f64.divEuclid] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val a: Double = 7.0
 *     val b = 4.0
 *     check(Double.divEuclid(a, b) == 1.0)
 *     // 7.0 > 4.0 * 1.0
 *     check(Double.divEuclid(-a, b) == -2.0)
 *     // -7.0 >= 4.0 * -2.0
 *     check(Double.divEuclid(a, -b) == -1.0)
 *     // 7.0 >= -4.0 * -1.0
 *     check(Double.divEuclid(-a, -b) == 2.0)
 *     // -7.0 >= -4.0 * 2.0
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.divEuclid]: ../../../std/primitive.f64.html#method.divEuclid
 */
public fun divEuclid(x: Double, rhs: Double): Double {
    val q = trunc(x / rhs)
    if (x % rhs < 0.0) {
        return if (rhs > 0.0) {
            q - 1.0
        } else {
            q + 1.0
        }
    }
    return q
}

/**
 * Experimental version of `remEuclid` in `core`. See [f64.remEuclid] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val a: Double = 7.0
 *     val b = 4.0
 *     check(Double.remEuclid(a, b) == 3.0)
 *     check(Double.remEuclid(-a, b) == 1.0)
 *     check(Double.remEuclid(a, -b) == 3.0)
 *     check(Double.remEuclid(-a, -b) == 1.0)
 *     // limitation due to round-off error
 *     check(Double.remEuclid(-Double.EPSILON, 3.0) != 0.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.remEuclid]: ../../../std/primitive.f64.html#method.remEuclid
 */
public fun remEuclid(x: Double, rhs: Double): Double {
    val r = x % rhs
    return if (r < 0.0) {
        r + kmFabsf64(rhs)
    } else {
        r
    }
}

/**
 * Experimental version of `powi` in `core`. See [f64.powi] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val absDifference = (Double.powi(x, 2) - (x * x)).abs()
 *     check(absDifference <= 1e-6)
 *     check(Double.powi(Double.NaN, 0) == 1.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.powi]: ../../../std/primitive.f64.html#method.powi
 */
public fun powi(x: Double, n: Int): Double = kmPowif64(x, n)

/**
 * Experimental version of `sqrt` in `core`. See [f64.sqrt] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val positive = 4.0
 *     val negative = -4.0
 *     val negativeZero = -0.0
 *     check(Double.sqrt(positive) == 2.0)
 *     check(Double.sqrt(negative).isNan())
 *     check(Double.sqrt(negativeZero) == negativeZero)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.sqrt]: ../../../std/primitive.f64.html#method.sqrt
 */
public fun sqrt(x: Double): Double = kmSqrtf64(x)

/**
 * Experimental version of `absSub` in `core`. See [f64.absSub] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 3.0
 *     val y = -3.0
 *     val absDifferenceX = (Double.absSub(x, 1.0) - 2.0).abs()
 *     val absDifferenceY = (Double.absSub(y, 1.0) - 0.0).abs()
 *     check(absDifferenceX < 1e-10)
 *     check(absDifferenceY < 1e-10)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.absSub]: ../../../std/primitive.f64.html#method.absSub
 */
public fun absSub(x: Double, other: Double): Double = fdim(x, other)

/**
 * Experimental version of `cbrt` in `core`. See [f64.cbrt] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 8.0
 *     // x^(1/3) - 2 == 0
 *     val absDifference = (Double.cbrt(x) - 2.0).abs()
 *     check(absDifference < 1e-10)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f64.cbrt]: ../../../std/primitive.f64.html#method.cbrt
 */
public fun cbrt(x: Double): Double = io.github.kotlinmania.rustlang.core.num.imp.libm.cbrt(x)
