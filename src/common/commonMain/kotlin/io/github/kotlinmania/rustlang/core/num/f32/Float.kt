// rust-kotlin: manual
// port-lint: source num/f32.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.f32

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.clamp.FloatClampBounds
import io.github.kotlinmania.rustlang.core.cmp.cmp
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.intrinsics.kmCeilf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFmaf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMaxnumf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMinnumf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmPowif32
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundTiesEvenf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf32
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.floatBinaryWithLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.FpCategory
import kotlin.reflect.KClass
import kotlin.math.cbrt as kotlinCbrt

internal const val FLOAT_SIGN_MASK: UInt = 0x8000_0000u
internal const val FLOAT_EXPONENT_MASK: UInt = 0x7F80_0000u
internal const val FLOAT_MANTISSA_MASK: UInt = 0x007F_FFFFu
internal const val FLOAT_TINY_BITS: UInt = 0x1u
internal const val FLOAT_NEG_TINY_BITS: UInt = 0x8000_0001u

// Constants for the `Float` single-precision floating point type.
//
// *[See also the `Float` primitive type][f32].*
//
// Mathematically significant numbers are provided in the `consts` sub-module.
//
// For the constants defined directly in this module
// (as distinct from those defined in the `consts` sub-module),
// new code should instead use the associated constants
// defined directly on the `Float` type.

/**
 * The radix or base of the internal representation of `Float`.
 * Use [f32.Radix] instead.
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
 * Use [f32.MantissaDigits] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val d = 24u
 * // intended way
 * val d1 = 24u
 * ```
 */
public val MANTISSA_DIGITS: UInt = 24u

/**
 * Approximate number of significant digits in base 10.
 * Use [f32.Digits] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val d = 6u
 * // intended way
 * val d1 = 6u
 * ```
 */
public val DIGITS: UInt = 6u

/**
 * [Machine epsilon] value for `Float`.
 * Use [f32.Epsilon] instead.
 *
 * This is the difference between `1.0` and the next larger representable number.
 *
 * [Machine epsilon]: https://en.wikipedia.org/wiki/MachineEpsilon
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val e = 1.1920929E-7f
 * // intended way
 * val e1 = 1.1920929E-7f
 * ```
 */
public val EPSILON: kotlin.Float = 1.1920929E-7f

/**
 * Smallest finite `Float` value.
 * Use [f32.Min] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = (-Float.MAX_VALUE)
 * // intended way
 * val min1 = (-Float.MAX_VALUE)
 * ```
 */
public val MIN: kotlin.Float = (-kotlin.Float.MAX_VALUE)

/**
 * Smallest positive normal `Float` value.
 * Use [f32.MinPositive] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = 1.17549435E-38f
 * // intended way
 * val min1 = 1.17549435E-38f
 * ```
 */
public val MIN_POSITIVE: kotlin.Float = 1.17549435E-38f

/**
 * Largest finite `Float` value.
 * Use [f32.Max] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val max = Float.MAX_VALUE
 * // intended way
 * val max1 = Float.MAX_VALUE
 * ```
 */
public val MAX: kotlin.Float = kotlin.Float.MAX_VALUE

/**
 * One greater than the minimum possible normal power of 2 exponent.
 * Use [f32.MinExp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = (-125)
 * // intended way
 * val min1 = (-125)
 * ```
 */
public val MIN_EXP: Int = (-125)

/**
 * Maximum possible power of 2 exponent.
 * Use [f32.MaxExp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val max = 128
 * // intended way
 * val max1 = 128
 * ```
 */
public val MAX_EXP: Int = 128

/**
 * Minimum possible normal power of 10 exponent.
 * Use [f32.Min10Exp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val min = (-37)
 * // intended way
 * val min1 = (-37)
 * ```
 */
public val MIN_10_EXP: Int = (-37)

/**
 * Maximum possible power of 10 exponent.
 * Use [f32.Max10Exp] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val max = 38
 * // intended way
 * val max1 = 38
 * ```
 */
public val MAX_10_EXP: Int = 38

/**
 * Not a Number (NaN).
 * Use [f32.Nan] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val nan = Float.NaN
 * // intended way
 * val nan1 = Float.NaN
 * ```
 */
public val NAN: kotlin.Float = kotlin.Float.NaN

/**
 * Infinity (∞).
 * Use [f32.Infinity] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val inf = Float.POSITIVE_INFINITY
 * // intended way
 * val inf1 = Float.POSITIVE_INFINITY
 * ```
 */
public val INFINITY: kotlin.Float = kotlin.Float.POSITIVE_INFINITY

/**
 * Negative infinity (−∞).
 * Use [f32.NegInfinity] instead.
 *
 * # Examples
 *
 * ```kotlin
 * // deprecated way
 * val ninf = Float.NEGATIVE_INFINITY
 * // intended way
 * val ninf1 = Float.NEGATIVE_INFINITY
 * ```
 */
public val NEG_INFINITY: kotlin.Float = kotlin.Float.NEGATIVE_INFINITY

// Note: replace with mathematical constants from cmath.

/**
 * Archimedes' constant (π)
 */
public val PI: kotlin.Float = 3.14159265358979323846264338327950288f

/**
 * The full circle constant (τ)
 *
 * Equal to 2π.
 */
public val TAU: kotlin.Float = 6.28318530717958647692528676655900577f

/**
 * The golden ratio (φ)
 */
public val GOLDEN_RATIO: kotlin.Float = 1.618033988749894848204586834365638118f

/**
 * The Euler-Mascheroni constant (γ)
 */
public val EULER_GAMMA: kotlin.Float = 0.577215664901532860606512090082402431f

/**
 * π/2
 */
public val FRAC_PI_2: kotlin.Float = 1.57079632679489661923132169163975144f

/**
 * π/3
 */
public val FRAC_PI_3: kotlin.Float = 1.04719755119659774615421446109316763f

/**
 * π/4
 */
public val FRAC_PI_4: kotlin.Float = 0.785398163397448309615660845819875721f

/**
 * π/6
 */
public val FRAC_PI_6: kotlin.Float = 0.52359877559829887307710723054658381f

/**
 * π/8
 */
public val FRAC_PI_8: kotlin.Float = 0.39269908169872415480783042290993786f

/**
 * 1/π
 */
public val FRAC_1_PI: kotlin.Float = 0.318309886183790671537767526745028724f

/**
 * 1/sqrt(π)
 */
public val FRAC_1_SQRT_PI: kotlin.Float = 0.564189583547756286948079451560772586f

/**
 * 1/sqrt(2π)
 */
public val FRAC_1_SQRT_2PI: kotlin.Float = 0.398942280401432677939946059934381868f

/**
 * 2/π
 */
public val FRAC_2_PI: kotlin.Float = 0.636619772367581343075535053490057448f

/**
 * 2/sqrt(π)
 */
public val FRAC_2_SQRT_PI: kotlin.Float = 1.12837916709551257389615890312154517f

/**
 * sqrt(2)
 */
public val SQRT_2: kotlin.Float = 1.41421356237309504880168872420969808f

/**
 * 1/sqrt(2)
 */
public val FRAC_1_SQRT_2: kotlin.Float = 0.707106781186547524400844362104849039f

/**
 * sqrt(3)
 */
public val SQRT_3: kotlin.Float = 1.732050807568877293527446341505872367f

/**
 * 1/sqrt(3)
 */
public val FRAC_1_SQRT_3: kotlin.Float = 0.577350269189625764509148780501957456f

/**
 * sqrt(5)
 */
public val SQRT_5: kotlin.Float = 2.23606797749978969640917366873127623f

/**
 * 1/sqrt(5)
 */
public val FRAC_1_SQRT_5: kotlin.Float = 0.44721359549995793928183473374625524f

/**
 * Euler's number (e)
 */
public val E: kotlin.Float = 2.71828182845904523536028747135266250f

/**
 * log<sub>2</sub>(e)
 */
public val LOG2_E: kotlin.Float = 1.44269504088896340735992468100189214f

/**
 * log<sub>2</sub>(10)
 */
public val LOG2_10: kotlin.Float = 3.32192809488736234787031942948939018f

/**
 * log<sub>10</sub>(e)
 */
public val LOG10_E: kotlin.Float = 0.434294481903251827651128918916605082f

/**
 * log<sub>10</sub>(2)
 */
public val LOG10_2: kotlin.Float = 0.301029995663981195213738894724493027f

/**
 * ln(2)
 */
public val LN_2: kotlin.Float = 0.693147180559945309417232121458176568f

/**
 * ln(10)
 */
public val LN_10: kotlin.Float = 2.30258509299404568401799145468436421f

// > if you intended to check if the operand is NaN, use `.isNan()` instead :)

/**
 * Returns `true` if this value is NaN.
 *
 * ```kotlin
 * val nan = Float.NaN
 * val f = 7.0f
 * check(nan.isNan())
 * check(!f.isNan())
 * ```
 */
public fun kotlin.Float.isNan(): Boolean = this != this

/**
 * Returns `true` if this value is positive infinity or negative infinity, and
 * `false` otherwise.
 *
 * ```kotlin
 * val f = 7.0f
 * val inf = Float.POSITIVE_INFINITY
 * val negInf = Float.NEGATIVE_INFINITY
 * val nan = Float.NaN
 * check(!f.isInfinite())
 * check(!nan.isInfinite())
 * check(inf.isInfinite())
 * check(negInf.isInfinite())
 * ```
 */
public fun kotlin.Float.isInfinite(): Boolean {
    // Getting clever with transmutation can result in incorrect answers on some FPUs
    // Note: alter the Rust <-> Rust calling convention to prevent this problem.
    // See https://github.com/rust-lang/rust/issues/72327
    return (this == kotlin.Float.POSITIVE_INFINITY) or (this == kotlin.Float.NEGATIVE_INFINITY)
}

/**
 * Returns `true` if this number is neither infinite nor NaN.
 *
 * ```kotlin
 * val f = 7.0f
 * val inf = Float.POSITIVE_INFINITY
 * val negInf = Float.NEGATIVE_INFINITY
 * val nan = Float.NaN
 * check(f.isFinite())
 * check(!nan.isFinite())
 * check(!inf.isFinite())
 * check(!negInf.isFinite())
 * ```
 */
public fun kotlin.Float.isFinite(): Boolean {
    // There's no need to handle NaN separately: if self is NaN,
    // the comparison is not true, exactly as desired.
    return kmFabsf32(this) < kotlin.Float.POSITIVE_INFINITY
}

/**
 * Returns `true` if the number is [subnormal].
 *
 * ```kotlin
 * val min = 1.17549435E-38f
 * // 1.17549435e-38f32
 * val max = Float.MAX_VALUE
 * val lowerThanMin = 1.0e-40f
 * val zero = 0.0f
 * check(!min.isSubnormal())
 * check(!max.isSubnormal())
 * check(!zero.isSubnormal())
 * check(!Float.NaN.isSubnormal())
 * check(!Float.POSITIVE_INFINITY.isSubnormal())
 * // Values between `0` and `min` are Subnormal.
 * check(lowerThanMin.isSubnormal())
 * ```
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun kotlin.Float.isSubnormal(): Boolean =
    when (this.classify()) {
        FpCategory.Subnormal -> true
        else -> false
    }

/**
 * Returns `true` if the number is neither zero, infinite,
 * [subnormal], or NaN.
 *
 * ```kotlin
 * val min = 1.17549435E-38f
 * // 1.17549435e-38f32
 * val max = Float.MAX_VALUE
 * val lowerThanMin = 1.0e-40f
 * val zero = 0.0f
 * check(min.isNormal())
 * check(max.isNormal())
 * check(!zero.isNormal())
 * check(!Float.NaN.isNormal())
 * check(!Float.POSITIVE_INFINITY.isNormal())
 * // Values between `0` and `min` are Subnormal.
 * check(!lowerThanMin.isNormal())
 * ```
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun kotlin.Float.isNormal(): Boolean =
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
 * val num = 12.4f
 * val inf = Float.POSITIVE_INFINITY
 * check(num.classify() == FpCategory.Normal)
 * check(inf.classify() == FpCategory.Infinite)
 * ```
 */
public fun kotlin.Float.classify(): FpCategory {
    // We used to have complicated logic here that avoids the simple bit-based tests to work
    // around buggy codegen for x87 targets (see
    // https://github.com/rust-lang/rust/issues/114479). However, some LLVM versions later, none
    // of our tests is able to find any difference between the complicated and the naive
    // version, so now we are back to the naive version.
    val b = this.toRawBits().toUInt()
    return run {
        val matchVal = Pair(b and FLOAT_MANTISSA_MASK, b and FLOAT_EXPONENT_MASK)
        when {
            matchVal.first == 0u && matchVal.second == FLOAT_EXPONENT_MASK -> FpCategory.Infinite
            matchVal.second == FLOAT_EXPONENT_MASK -> FpCategory.Nan
            matchVal.first == 0u && matchVal.second == 0u -> FpCategory.Zero
            matchVal.second == 0u -> FpCategory.Subnormal
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
 * val f = 7.0f
 * val g = -7.0f
 * check(f.isSignPositive())
 * check(!g.isSignPositive())
 * ```
 */
public fun kotlin.Float.isSignPositive(): Boolean = !this.isSignNegative()

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
 * val f = 7.0f
 * val g = -7.0f
 * check(!f.isSignNegative())
 * check(g.isSignNegative())
 * ```
 */
public fun kotlin.Float.isSignNegative(): Boolean {
    // IEEE754 says: isSignMinus(x) is true if and only if x has negative sign. isSignMinus
    // applies to zeros and NaNs as well.
    return this.toRawBits().toUInt() and 0x80000000u != 0u
}

/**
 * Returns the least number greater than `self`.
 *
 * Let `TINY` be the smallest representable positive `Float`. Then,
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
 * // f32.Epsilon is the difference between 1.0 and the next number up.
 * check(1.0f.nextUp() == 1.0f + 1.1920929E-7f)
 * // But not for most numbers.
 * check(0.1f.nextUp() < 0.1f + 1.1920929E-7f)
 * check(16777216.0f.nextUp() == 16777218.0)
 * ```
 *
 * This operation corresponds to IEEE-754 `nextUp`.
 *
 * [NegInfinity]: Self.NegInfinity
 * [Infinity]: Self.Infinity
 * [Min]: Self.Min
 * [Max]: Self.Max
 */
public fun kotlin.Float.nextUp(): kotlin.Float {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toRawBits().toUInt()
    if (this.isNaN() || bits == kotlin.Float.POSITIVE_INFINITY.toBits()) {
        return this
    }
    val abs = bits and FLOAT_SIGN_MASK.inv()
    val nextBits =
        if (abs == 0u) {
            FLOAT_TINY_BITS
        } else if (bits == abs) {
            bits + 1u
        } else {
            bits - 1u
        }
    return kotlin.Float.fromBits(nextBits.toInt())
}

/**
 * Returns the greatest number less than `self`.
 *
 * Let `TINY` be the smallest representable positive `Float`. Then,
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
 * val x = 1.0f
 * // Clamp value into range [0, 1).
 * val clamped = x.clamp(0.0, 1.0f.nextDown())
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
public fun kotlin.Float.nextDown(): kotlin.Float {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toRawBits().toUInt()
    if (this.isNaN() || bits == kotlin.Float.NEGATIVE_INFINITY.toBits()) {
        return this
    }
    val abs = bits and FLOAT_SIGN_MASK.inv()
    val nextBits =
        if (abs == 0u) {
            FLOAT_NEG_TINY_BITS
        } else if (bits == abs) {
            bits - 1u
        } else {
            bits + 1u
        }
    return kotlin.Float.fromBits(nextBits.toInt())
}

/**
 * Takes the reciprocal (inverse) of a number, `1 / x`.
 *
 * ```kotlin
 * val x = 2.0f
 * val absDifference = (x.recip() - (1.0f / x)).abs()
 * check(absDifference <= 1.1920929E-7f)
 * ```
 */
public fun kotlin.Float.recip(): kotlin.Float = 1.0f / this

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
 * val angle = 3.1415927f
 * val absDifference = (angle.toDegrees() - 180.0).abs()
 * check(absDifference <= 1.1920929E-7f)
 * ```
 */
public fun kotlin.Float.toDegrees(): kotlin.Float {
    // Use a literal to avoid double rounding, consts.PI is already rounded,
    // and dividing would round again.
    val PIS_IN_180: kotlin.Float = 57.2957795130823208767981548141051703f
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
 * val angle = 180.0f
 * val absDifference = (angle.toRadians() - 3.1415927f).abs()
 * check(absDifference <= 1.1920929E-7f)
 * ```
 */
public fun kotlin.Float.toRadians(): kotlin.Float {
    // The division here is correctly rounded with respect to the true value of π/180.
    // Although π is irrational and already rounded, the double rounding happens
    // to produce correct result for f32.
    val RADS_PER_DEG: kotlin.Float = PI / 180.0f
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
 * val x = 1.0f
 * val y = 2.0f
 * check(x.max(y) == y)
 * check(x.max(Float.NaN) == x)
 * ```
 */
public fun kotlin.Float.max(other: kotlin.Float): kotlin.Float = kmMaxnumf32(this, other)

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
 * val x = 1.0f
 * val y = 2.0f
 * check(x.min(y) == x)
 * check(x.min(Float.NaN) == x)
 * ```
 */
public fun kotlin.Float.min(other: kotlin.Float): kotlin.Float = kmMinnumf32(this, other)

/**
 * Returns the maximum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f32.max] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `maximum`.
 *
 * ```kotlin
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = 1.0f
 *     val y = 2.0f
 *     check(x.maximum(y) == y)
 *     check(x.maximum(Float.NaN).isNan())
 * }
 * ```
 */
public fun kotlin.Float.maximum(other: kotlin.Float): kotlin.Float = floatBinaryWithLayout("maximum", this, other, PrimitiveLayouts.F32)

/**
 * Returns the minimum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f32.min] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `minimum`.
 *
 * ```kotlin
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = 1.0f
 *     val y = 2.0f
 *     check(x.minimum(y) == x)
 *     check(x.minimum(Float.NaN).isNan())
 * }
 * ```
 */
public fun kotlin.Float.minimum(other: kotlin.Float): kotlin.Float = floatBinaryWithLayout("minimum", this, other, PrimitiveLayouts.F32)

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * This returns NaN when *either* argument is NaN or if a combination of
 * +inf and -inf is provided as arguments.
 *
 * # Examples
 *
 * ```kotlin
 * check(1.0f.midpoint(4.0) == 2.5)
 * check((-5.5f).midpoint(8.0) == 1.25)
 * ```
 */
public fun kotlin.Float.midpoint(other: kotlin.Float): kotlin.Float = ((this.toDouble() + other.toDouble()) * 0.5).toFloat()

/**
 * Rounds toward zero and converts to any primitive integer type,
 * assuming that the value is finite and fits in that type.
 *
 * ```kotlin
 * val value = 4.6f
 * val rounded = value.toIntUnchecked<UShort>()
 * check(rounded == 4)
 * val value1 = -128.9f
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
public fun dispatchFloatToIntUnchecked(v: kotlin.Float, cls: KClass<*>): Any =
    when (cls) {
        Byte::class -> v.toInt().toByte()
        UByte::class -> v.toInt().toUByte()
        Short::class -> v.toInt().toShort()
        UShort::class -> v.toInt().toUShort()
        Int::class -> v.toInt()
        UInt::class -> v.toUInt()
        Long::class -> v.toLong()
        ULong::class -> v.toULong()
        else -> error("unsupported float target $cls")
    }

public fun dispatchFloatToIntChecked(v: kotlin.Float, cls: KClass<*>): Any? {
    if (v.isNaN() || v.isInfinite()) return null
    val d = v.toDouble()
    return when (cls) {
        Byte::class -> if (d > -129.0 && d < 128.0) v.toInt().toByte() else null
        UByte::class -> if (d > -1.0 && d < 256.0) v.toInt().toUByte() else null
        Short::class -> if (d > -32769.0 && d < 32768.0) v.toInt().toShort() else null
        UShort::class -> if (d > -1.0 && d < 65536.0) v.toInt().toUShort() else null
        Int::class -> if (d >= -2147483648.0 && d < 2147483648.0) v.toInt() else null
        UInt::class -> if (d > -1.0 && d < 4294967296.0) v.toUInt() else null
        Long::class -> if (d >= -9223372036854775808.0 && d < 9223372036854775808.0) v.toLong() else null
        ULong::class -> if (d > -1.0 && d < 18446744073709551616.0) v.toULong() else null
        else -> null
    }
}

public fun dispatchFloatToIntSaturating(v: kotlin.Float, cls: KClass<*>): Any {
    if (v.isNaN()) {
        return when (cls) {
            Byte::class -> 0.toByte()
            UByte::class -> 0u.toUByte()
            Short::class -> 0.toShort()
            UShort::class -> 0u.toUShort()
            Int::class -> 0
            UInt::class -> 0u
            Long::class -> 0L
            ULong::class -> 0uL
            else -> error("unsupported float target $cls")
        }
    }
    return when (cls) {
        Byte::class -> v.toInt().coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()
        UByte::class -> v.toInt().coerceIn(0, UByte.MAX_VALUE.toInt()).toUByte()
        Short::class -> v.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        UShort::class -> v.toInt().coerceIn(0, UShort.MAX_VALUE.toInt()).toUShort()
        Int::class -> v.toInt()
        UInt::class -> v.toUInt()
        Long::class -> v.toLong()
        ULong::class -> v.toULong()
        else -> error("unsupported float target $cls")
    }
}

public inline fun <reified Target> kotlin.Float.toIntUnchecked(): Target =
    dispatchFloatToIntUnchecked(this, Target::class) as Target

/**
 * Converts to the target float type, rounding as defined in IEEE 754.
 *
 * This is equivalent to `self as Flt`. Narrowing to a smaller type can
 * produce an infinity.
 *
 * ```kotlin
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     val x = 1.5f
 *     check(x.cast() == 1.5)
 * }
 * ```
 */
public inline fun <reified Target> kotlin.Float.cast(): Target =
    when (Target::class) {
        kotlin.Float::class -> this as Target
        Double::class -> this.toDouble() as Target
        else -> dispatchFloatToIntSaturating(this, Target::class) as Target
    }

/**
 * Rounds toward zero and converts to any primitive integer type, saturating
 * at the type's boundaries and mapping `NaN` to zero.
 *
 * This is equivalent to `self as Int`.
 *
 * ```kotlin
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     check(255.5f.toIntSaturating<UByte>() == 255)
 *     check(300.0f.toIntSaturating<UByte>() == 255)
 *     check((-1.0f).toIntSaturating<UByte>() == 0)
 *     check(Float.NaN.toIntSaturating<UByte>() == 0)
 * }
 * ```
 */
public inline fun <reified Target> kotlin.Float.toIntSaturating(): Target =
    dispatchFloatToIntSaturating(this, Target::class) as Target

/**
 * Rounds toward zero and converts to any primitive integer type, returning
 * `None` if the value is `NaN`, infinite, or does not fit in the target type.
 *
 * ```kotlin
 * @feature(floatConversions)
 * public fun doctestEntry() {
 *     check(255.5f.toIntChecked<UByte>() == 255)
 *     check(256.0f.toIntChecked<UByte>() == null)
 *     check(Float.NaN.toIntChecked<UByte>() == null)
 * }
 * ```
 */
public inline fun <reified Target> kotlin.Float.toIntChecked(): Target? =
    dispatchFloatToIntChecked(this, Target::class) as Target?

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
 *     check(255.5f.toIntStrict<UByte>() == 255)
 * }
 * ```
 */
public inline fun <reified Int> kotlin.Float.toIntStrict(): Int = (this.toIntChecked<Int>() ?: error("the value cannot be represented in the target integer type"))

/**
 * Raw transmutation to `UInt`.
 *
 * This is currently identical to `reinterpret<Float, UInt>(self)` on all platforms.
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
 * check((1.0f).toBits() != 1.0f.toUInt())
 * // toBits() is not casting!
 * check((12.5f).toRawBits().toUInt() == 0x41480000u)
 * ```
 */
public fun kotlin.Float.toBits(): UInt {
    // SAFETY: `UInt` is a plain old datatype so we can always reinterpret to it.
    return this.toRawBits().toUInt()
}

/**
 * Raw transmutation from `UInt`.
 *
 * This is currently identical to `reinterpret<UInt, Float>(v)` on all platforms.
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
 * # Examples
 *
 * ```kotlin
 * val v = Float.fromBits(0x41480000u.toInt())
 * check(v == 12.5f.toFloat())
 * ```
 */
public fun f32FromBits(v: UInt): kotlin.Float {
    // It turns out the safety issues with sNaN were overblown! Hooray!
    // SAFETY: `UInt` is a plain old datatype so we can always reinterpret from it.
    return transmuteWithLayouts<UInt, kotlin.Float>(v, PrimitiveLayouts.U32, PrimitiveLayouts.F32)
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
 *     val bytes = run { val rawBits = (12.5f).toRawBits().toUInt(); ubyteArrayOf((rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) }
 *     check(bytes == ubyteArrayOf(0x41u.toUByte(), 0x48u.toUByte(), 0x00u.toUByte(), 0x00u.toUByte()))
 * }
 * ```
 */
public fun kotlin.Float.toBeBytes(): UByteArray =
    run {
        val rawBits = this.toRawBits().toUInt()
        ubyteArrayOf((rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte())
    }

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
 *     val bytes = run { val rawBits = (12.5f).toRawBits().toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
 *     check(bytes == ubyteArrayOf(0x00u.toUByte(), 0x00u.toUByte(), 0x48u.toUByte(), 0x41u.toUByte()))
 * }
 * ```
 */
public fun kotlin.Float.toLeBytes(): UByteArray =
    run {
        val rawBits = this.toRawBits().toUInt()
        ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte())
    }

/**
 * Returns the memory representation of this floating point number as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate, instead.
 *
 * [toBeBytes]: f32.toBeBytes
 * [toLeBytes]: f32.toLeBytes
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
 *     val bytes = run { val rawBits = (12.5f).toRawBits().toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
 *     check(bytes == if (false) {
 *         listOf(0x41, 0x48, 0x00, 0x00)
 *     } else {
 *         listOf(0x00, 0x00, 0x48, 0x41)
 *     })
 * }
 * ```
 */
public fun kotlin.Float.toNeBytes(): UByteArray =
    run {
        val rawBits = this.toRawBits().toUInt()
        ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte())
    }

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
 *     val value = run { val bytesTmp0 = ubyteArrayOf(0x41u.toUByte(), 0x48u.toUByte(), 0x00u.toUByte(), 0x00u.toUByte()); Float.fromBits(((bytesTmp0[0].toInt() shl 24) or (bytesTmp0[1].toInt() shl 16) or (bytesTmp0[2].toInt() shl 8) or bytesTmp0[3].toInt())) }
 *     check(value == 12.5f.toFloat())
 * }
 * ```
 */
public fun f32FromBeBytes(bytes: UByteArray): kotlin.Float =
    f32FromBits(
        run {
            val bytesTmp0 = bytes
            ((bytesTmp0[0].toUInt() shl 24) or (bytesTmp0[1].toUInt() shl 16) or (bytesTmp0[2].toUInt() shl 8) or bytesTmp0[3].toUInt())
        },
    )

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
 *     val value = run { val bytesTmp0 = ubyteArrayOf(0x00u.toUByte(), 0x00u.toUByte(), 0x48u.toUByte(), 0x41u.toUByte()); Float.fromBits(((bytesTmp0[3].toInt() shl 24) or (bytesTmp0[2].toInt() shl 16) or (bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt())) }
 *     check(value == 12.5f.toFloat())
 * }
 * ```
 */
public fun f32FromLeBytes(bytes: UByteArray): kotlin.Float =
    f32FromBits(
        run {
            val bytesTmp1 = bytes
            ((bytesTmp1[3].toUInt() shl 24) or (bytesTmp1[2].toUInt() shl 16) or (bytesTmp1[1].toUInt() shl 8) or bytesTmp1[0].toUInt())
        },
    )

/**
 * Creates a floating point value from its representation as a byte array in native endian.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: f32.fromBeBytes
 * [fromLeBytes]: f32.fromLeBytes
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
 *         ubyteArrayOf(0x41u.toUByte(), 0x48u.toUByte(), 0x00u.toUByte(), 0x00u.toUByte())
 *     } else {
 *         ubyteArrayOf(0x00u.toUByte(), 0x00u.toUByte(), 0x48u.toUByte(), 0x41u.toUByte())
 *     }; Float.fromBits(((bytesTmp0[3].toInt() shl 24) or (bytesTmp0[2].toInt() shl 16) or (bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt())) }
 *     check(value == 12.5f.toFloat())
 * }
 * ```
 */
public fun f32FromNeBytes(bytes: UByteArray): kotlin.Float =
    f32FromBits(
        run {
            val bytesTmp2 = bytes
            ((bytesTmp2[3].toUInt() shl 24) or (bytesTmp2[2].toUInt() shl 16) or (bytesTmp2[1].toUInt() shl 8) or bytesTmp2[0].toUInt())
        },
    )

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
 * [PartialOrd] and [PartialEq] implementations of `Float`. For example,
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
 * open class GoodBoy(public var name: String, public var weight: Float)
 * var bois = mutableListOf(GoodBoy(name = "Pucci".toOwned(), weight = 0.1f), GoodBoy(name = "Woofer".toOwned(), weight = 99.0f), GoodBoy(name = "Yapper".toOwned(), weight = 10.0f), GoodBoy(name = "Chonk".toOwned(), weight = Float.POSITIVE_INFINITY), GoodBoy(name = "Abs. Unit".toOwned(), weight = Float.NaN), GoodBoy(name = "Floaty".toOwned(), weight = -5.0f))
 * bois.sortBy({ a, b -> a.weight.totalCmp(b.weight) })
 * // `Float.NAN` could be positive or negative, which will affect the sort order.
 * if (Float.NaN.isSignNegative()) {
 *     check((ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(floatArrayOf(Float.NaN, -5.0f, 0.1f, 10.0f, 99.0f, Float.POSITIVE_INFINITY))).all({ (a, b) -> a.toBits() == b.toBits() }))
 * } else {
 *     check((ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(floatArrayOf(-5.0f, 0.1f, 10.0f, 99.0f, Float.POSITIVE_INFINITY, Float.NaN))).all({ (a, b) -> a.toBits() == b.toBits() }))
 * }
 * ```
 */
public fun kotlin.Float.totalCmp(other: kotlin.Float): Ordering {
    var left = this.toRawBits().toUInt().toInt()
    var right = other.toRawBits().toUInt().toInt()
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
    left = left xor ((((left shr 31).toUInt()) shr 1).toInt())
    right = right xor ((((right shr 31).toUInt()) shr 1).toInt())
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
 * check((-3.0f).clamp(-2.0, 1.0) == -2.0)
 * check((0.0f).clamp(-2.0, 1.0) == 0.0)
 * check((2.0f).clamp(-2.0, 1.0) == 1.0)
 * check((Float.NaN).clamp(-2.0, 1.0).isNan())
 * // These always returns zero, but the sign (which is ignored by `==`) is non-deterministic.
 * check((0.0f).clamp(-0.0, -0.0) == 0.0)
 * check((1.0f).clamp(-0.0, 0.0) == 0.0)
 * // This is definitely a negative zero.
 * check((-1.0f).clamp(-0.0, 1.0).isSignNegative())
 * ```
 */
public fun kotlin.Float.clamp(min: kotlin.Float, max: kotlin.Float): kotlin.Float {
    var selfMut = this
    check(min <= max) { "min > max, or either was NaN" }
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
 *     check(5.0f.clampMagnitude(3.0) == 3.0)
 *     check((-5.0f).clampMagnitude(3.0) == -3.0)
 *     check(2.0f.clampMagnitude(3.0) == 2.0)
 *     check((-2.0f).clampMagnitude(3.0) == -2.0)
 * }
 * ```
 */
public fun kotlin.Float.clampMagnitude(limit: kotlin.Float): kotlin.Float {
    check(limit >= 0.0f) { "limit must be non-negative and not NaN" }
    val limit1 = kmFabsf32(limit)
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
 *     check((-3.0f).clampTo(-2.0..1.0) == -2.0)
 *     check(0.0f.clampTo(-2.0..1.0) == 0.0)
 *     check(2.0f.clampTo(0..1.0) == 1.0)
 *     check(5.0f.clampTo(7.0 until Int.MAX_VALUE) == 7.0)
 *     check(Float.NaN.clampTo(1.0..2.0).isNan())
 * }
 * ```
 */
public fun kotlin.Float.clampTo(range: FloatClampBounds): kotlin.Float = range.clamp(this)

/**
 * Computes the absolute value of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 3.5f
 * val y = -3.5f
 * check(x.abs() == x)
 * check(y.abs() == -y)
 * check(Float.NaN.abs().isNan())
 * ```
 */
public fun kotlin.Float.abs(): kotlin.Float = kmFabsf32(this)

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
 * val f = 3.5f
 * check(f.signum() == 1.0)
 * check(Float.NEGATIVE_INFINITY.signum() == -1.0)
 * check(Float.NaN.signum().isNan())
 * ```
 */
public fun kotlin.Float.signum(): kotlin.Float =
    if (this.isNaN()) {
        kotlin.Float.NaN
    } else {
        kmCopysignf32(1.0f, this)
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
 * val f = 3.5f
 * check(f.copysign(0.42) == 3.5f)
 * check(f.copysign(-0.42) == -3.5f)
 * check((-f).copysign(0.42) == 3.5f)
 * check((-f).copysign(-0.42) == -3.5f)
 * check(Float.NaN.copysign(1.0).isNan())
 * ```
 */
public fun kotlin.Float.copysign(sign: kotlin.Float): kotlin.Float = kmCopysignf32(this, sign)

/**
 * Float addition that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun kotlin.Float.algebraicAdd(rhs: kotlin.Float): kotlin.Float = ((this) + (rhs))

/**
 * Float subtraction that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun kotlin.Float.algebraicSub(rhs: kotlin.Float): kotlin.Float = ((this) - (rhs))

/**
 * Float multiplication that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun kotlin.Float.algebraicMul(rhs: kotlin.Float): kotlin.Float = ((this) * (rhs))

/**
 * Float division that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun kotlin.Float.algebraicDiv(rhs: kotlin.Float): kotlin.Float = ((this) / (rhs))

/**
 * Float remainder that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun kotlin.Float.algebraicRem(rhs: kotlin.Float): kotlin.Float = ((this) % (rhs))

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
 *     // - 5.2154064e-10 is the fused result (one rounding)
 *     // - 9.313226e-10 is the unfused result (two roundings)
 *     val r = 0.1f.mulAddRelaxed(0.1f, -0.01f)
 *     check(r == 5.2154064e-10 || r == 9.313226e-10)
 * }
 * ```
 *
 * [mulAdd]: ../std/primitive.f32.html#method.mulAdd
 */
public fun kotlin.Float.mulAddRelaxed(a: kotlin.Float, b: kotlin.Float): kotlin.Float = kmFmaf32(this, a, b)

/**
 * Returns `self` if the value is not NaN, otherwise returns `replacement`
 * if `self` is NaN.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(floatNanTo)
 * public fun doctestEntry() {
 *     val n = Float.NaN
 *     val x = 2.0f
 *     val y = Float.POSITIVE_INFINITY
 *     check(n.nanTo(0.0f) == 0.0f)
 *     check(x.nanTo(0.0f) == 2.0f)
 *     check(y.nanTo(0.0f) == Float.POSITIVE_INFINITY)
 * }
 * ```
 */
public fun kotlin.Float.nanTo(replacement: kotlin.Float): kotlin.Float =
    if (this.isNaN()) {
        replacement
    } else {
        this
    }

/**
 * Experimental version of `floor` in `core`. See [f32.floor] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.7f
 *     val g = 3.0f
 *     val h = -3.7f
 *     check(Float.floor(f) == 3.0)
 *     check(Float.floor(g) == 3.0)
 *     check(Float.floor(h) == -4.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.floor]: ../../../std/primitive.f32.html#method.floor
 */
public fun floor(x: kotlin.Float): kotlin.Float = kmFloorf32(x)

/**
 * Experimental version of `ceil` in `core`. See [f32.ceil] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.01f
 *     val g = 4.0f
 *     check(Float.ceil(f) == 4.0)
 *     check(Float.ceil(g) == 4.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.ceil]: ../../../std/primitive.f32.html#method.ceil
 */
public fun ceil(x: kotlin.Float): kotlin.Float = kmCeilf32(x)

/**
 * Experimental version of `round` in `core`. See [f32.round] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.3f
 *     val g = -3.3f
 *     val h = -3.7f
 *     val i = 3.5f
 *     val j = 4.5f
 *     check(Float.round(f) == 3.0)
 *     check(Float.round(g) == -3.0)
 *     check(Float.round(h) == -4.0)
 *     check(Float.round(i) == 4.0)
 *     check(Float.round(j) == 5.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.round]: ../../../std/primitive.f32.html#method.round
 */
public fun round(x: kotlin.Float): kotlin.Float = kmRoundf32(x)

/**
 * Experimental version of `roundTiesEven` in `core`. See [f32.roundTiesEven] for
 * details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.3f
 *     val g = -3.3f
 *     val h = 3.5f
 *     val i = 4.5f
 *     check(Float.roundTiesEven(f) == 3.0)
 *     check(Float.roundTiesEven(g) == -3.0)
 *     check(Float.roundTiesEven(h) == 4.0)
 *     check(Float.roundTiesEven(i) == 4.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.roundTiesEven]: ../../../std/primitive.f32.html#method.roundTiesEven
 */
public fun roundTiesEven(x: kotlin.Float): kotlin.Float = kmRoundTiesEvenf32(x)

/**
 * Experimental version of `trunc` in `core`. See [f32.trunc] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val f = 3.7f
 *     val g = 3.0f
 *     val h = -3.7f
 *     check(Float.trunc(f) == 3.0)
 *     check(Float.trunc(g) == 3.0)
 *     check(Float.trunc(h) == -3.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.trunc]: ../../../std/primitive.f32.html#method.trunc
 */
public fun trunc(x: kotlin.Float): kotlin.Float = kmTruncf32(x)

/**
 * Experimental version of `fract` in `core`. See [f32.fract] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 3.6f
 *     val y = -3.6f
 *     val absDifferenceX = (Float.fract(x) - 0.6).abs()
 *     val absDifferenceY = (Float.fract(y) - (-0.6)).abs()
 *     check(absDifferenceX <= 1.1920929E-7f)
 *     check(absDifferenceY <= 1.1920929E-7f)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.fract]: ../../../std/primitive.f32.html#method.fract
 */
public fun fract(x: kotlin.Float): kotlin.Float = x - trunc(x)

/**
 * Experimental version of `mulAdd` in `core`. See [f32.mulAdd] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val m = 10.0f
 *     val x = 4.0f
 *     val b = 60.0f
 *     check(Float.mulAdd(m, x, b) == 100.0)
 *     check(m * x + b == 100.0f.toFloat())
 *     val onePlusEps = 1.0f + 1.1920929E-7f
 *     val oneMinusEps = 1.0f - 1.1920929E-7f
 *     val minusOne = -1.0f
 *     // The exact result (1 + eps) * (1 - eps) = 1 - eps * eps.
 *     check(Float.mulAdd(onePlusEps, oneMinusEps, minusOne) == -1.1920929E-7f * 1.1920929E-7f)
 *     // Different rounding with the non-fused multiply and add.
 *     check(onePlusEps * oneMinusEps + minusOne == 0.0f.toFloat())
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.mulAdd]: ../../../std/primitive.f32.html#method.mulAdd
 */
public fun mulAdd(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float = kmFmaf32(x, y, z)

/**
 * Experimental version of `divEuclid` in `core`. See [f32.divEuclid] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val a: Float = 7.0f
 *     val b = 4.0
 *     check(Float.divEuclid(a, b) == 1.0)
 *     // 7.0 > 4.0 * 1.0
 *     check(Float.divEuclid(-a, b) == -2.0)
 *     // -7.0 >= 4.0 * -2.0
 *     check(Float.divEuclid(a, -b) == -1.0)
 *     // 7.0 >= -4.0 * -1.0
 *     check(Float.divEuclid(-a, -b) == 2.0)
 *     // -7.0 >= -4.0 * 2.0
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.divEuclid]: ../../../std/primitive.f32.html#method.divEuclid
 */
public fun divEuclid(x: kotlin.Float, rhs: kotlin.Float): kotlin.Float {
    val q = trunc(x / rhs)
    if (x % rhs < 0.0f) {
        return if (rhs > 0.0f) {
            q - 1.0f
        } else {
            q + 1.0f
        }
    }
    return q
}

/**
 * Experimental version of `remEuclid` in `core`. See [f32.remEuclid] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val a: Float = 7.0f
 *     val b = 4.0
 *     check(Float.remEuclid(a, b) == 3.0)
 *     check(Float.remEuclid(-a, b) == 1.0)
 *     check(Float.remEuclid(a, -b) == 3.0)
 *     check(Float.remEuclid(-a, -b) == 1.0)
 *     // limitation due to round-off error
 *     check(Float.remEuclid(-Float.EPSILON, 3.0) != 0.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.remEuclid]: ../../../std/primitive.f32.html#method.remEuclid
 */
public fun remEuclid(x: kotlin.Float, rhs: kotlin.Float): kotlin.Float {
    val r = x % rhs
    return if (r < 0.0f) {
        r + kmFabsf32(rhs)
    } else {
        r
    }
}

/**
 * Experimental version of `powi` in `core`. See [f32.powi] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 2.0f
 *     val absDifference = (Float.powi(x, 2) - (x * x)).abs()
 *     check(absDifference <= 1e-5)
 *     check(Float.powi(Float.NaN, 0) == 1.0)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.powi]: ../../../std/primitive.f32.html#method.powi
 */
public fun powi(x: kotlin.Float, n: Int): kotlin.Float = kmPowif32(x, n)

/**
 * Experimental version of `sqrt` in `core`. See [f32.sqrt] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val positive = 4.0f
 *     val negative = -4.0f
 *     val negativeZero = -0.0f
 *     check(Float.sqrt(positive) == 2.0)
 *     check(Float.sqrt(negative).isNan())
 *     check(Float.sqrt(negativeZero) == negativeZero)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.sqrt]: ../../../std/primitive.f32.html#method.sqrt
 */
public fun sqrt(x: kotlin.Float): kotlin.Float = kmSqrtf32(x)

/**
 * Experimental version of `absSub` in `core`. See [f32.absSub] for details.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 3.0f
 *     val y = -3.0f
 *     val absDifferenceX = (Float.absSub(x, 1.0) - 2.0).abs()
 *     val absDifferenceY = (Float.absSub(y, 1.0) - 0.0).abs()
 *     check(absDifferenceX <= 1e-6)
 *     check(absDifferenceY <= 1e-6)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.absSub]: ../../../std/primitive.f32.html#method.absSub
 */
public fun absSub(x: kotlin.Float, other: kotlin.Float): kotlin.Float =
    if (x.isNaN() || other.isNaN()) {
        kotlin.Float.NaN
    } else if (x > other) {
        x - other
    } else {
        0.0f
    }

/**
 * Experimental version of `cbrt` in `core`. See [f32.cbrt] for details.
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
 * @feature(coreFloatMath)
 * public fun doctestEntry() {
 *     val x = 8.0f
 *     // x^(1/3) - 2 == 0
 *     val absDifference = (Float.cbrt(x) - 2.0).abs()
 *     check(absDifference <= 1e-6)
 * }
 * ```
 *
 * _This standalone function is for testing only.
 * It will be stabilized as an inherent method._
 *
 * [f32.cbrt]: ../../../std/primitive.f32.html#method.cbrt
 */
public fun cbrt(x: kotlin.Float): kotlin.Float = kotlinCbrt(x)
