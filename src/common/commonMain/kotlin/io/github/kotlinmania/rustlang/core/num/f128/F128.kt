// port-lint: source num/f128.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.f128

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.ClampBounds
import io.github.kotlinmania.rustlang.core.f128.max
import io.github.kotlinmania.rustlang.core.intrinsics.copysignf128
import io.github.kotlinmania.rustlang.core.intrinsics.fabs
import io.github.kotlinmania.rustlang.core.intrinsics.faddAlgebraic
import io.github.kotlinmania.rustlang.core.intrinsics.fdivAlgebraic
import io.github.kotlinmania.rustlang.core.intrinsics.fma
import io.github.kotlinmania.rustlang.core.intrinsics.fmulAlgebraic
import io.github.kotlinmania.rustlang.core.intrinsics.fmuladd
import io.github.kotlinmania.rustlang.core.intrinsics.fremAlgebraic
import io.github.kotlinmania.rustlang.core.intrinsics.fsubAlgebraic
import io.github.kotlinmania.rustlang.core.intrinsics.maximumNumberNszF128
import io.github.kotlinmania.rustlang.core.intrinsics.maximumf128
import io.github.kotlinmania.rustlang.core.intrinsics.minimumNumberNszF128
import io.github.kotlinmania.rustlang.core.intrinsics.minimumf128
import io.github.kotlinmania.rustlang.core.num.FpCategory
import io.github.kotlinmania.rustlang.core.num.f32.INFINITY
import io.github.kotlinmania.rustlang.core.num.f32.NEG_INFINITY
import io.github.kotlinmania.rustlang.core.panic.constAssert
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

// Constants for the `f128` quadruple-precision floating point type.
//
// *[See also the `f128` primitive type][f128].*
//
// Mathematically significant numbers are provided in the `consts` sub-module.
//
// For the constants defined directly in this module
// (as distinct from those defined in the `consts` sub-module),
// new code should instead use the associated constants
// defined directly on the `f128` type.

// Note: replace with mathematical constants from cmath.

/**
 * Archimedes' constant (π)
 */
public val PI: F128 = 3.14159265358979323846264338327950288419716939937510582097494

/**
 * The full circle constant (τ)
 *
 * Equal to 2π.
 */
public val TAU: F128 = 6.28318530717958647692528676655900576839433879875021164194989

/**
 * The golden ratio (φ)
 */
public val GOLDEN_RATIO: F128 = 1.61803398874989484820458683436563811772030917980576286213545

/**
 * The Euler-Mascheroni constant (γ)
 */
public val EULER_GAMMA: F128 = 0.577215664901532860606512090082402431042159335939923598805767

/**
 * π/2
 */
public val FRAC_PI_2: F128 = 1.57079632679489661923132169163975144209858469968755291048747

/**
 * π/3
 */
public val FRAC_PI_3: F128 = 1.04719755119659774615421446109316762806572313312503527365831

/**
 * π/4
 */
public val FRAC_PI_4: F128 = 0.785398163397448309615660845819875721049292349843776455243736

/**
 * π/6
 */
public val FRAC_PI_6: F128 = 0.523598775598298873077107230546583814032861566562517636829157

/**
 * π/8
 */
public val FRAC_PI_8: F128 = 0.392699081698724154807830422909937860524646174921888227621868

/**
 * 1/π
 */
public val FRAC_1_PI: F128 = 0.318309886183790671537767526745028724068919291480912897495335

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * 1/sqrt(π)
 */
public val FRAC_1_SQRT_PI: F128 = 0.564189583547756286948079451560772585844050629328998856844086

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * 1/sqrt(2π)
 */
public val FRAC_1_SQRT_2PI: F128 = 0.398942280401432677939946059934381868475858631164934657665926

/**
 * 2/π
 */
public val FRAC_2_PI: F128 = 0.636619772367581343075535053490057448137838582961825794990669

/**
 * 2/sqrt(π)
 */
public val FRAC_2_SQRT_PI: F128 = 1.12837916709551257389615890312154517168810125865799771368817

/**
 * sqrt(2)
 */
public val SQRT_2: F128 = 1.41421356237309504880168872420969807856967187537694807317668

/**
 * 1/sqrt(2)
 */
public val FRAC_1_SQRT_2: F128 = 0.707106781186547524400844362104849039284835937688474036588340

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * sqrt(3)
 */
public val SQRT_3: F128 = 1.73205080756887729352744634150587236694280525381038062805581

// Also, @unstable(feature = "moreFloatConstants", issue = "146939")

/**
 * 1/sqrt(3)
 */
public val FRAC_1_SQRT_3: F128 = 0.577350269189625764509148780501957455647601751270126876018602

// Also, @unstable(feature = "f128", issue = "116909")

/**
 * sqrt(5)
 */
public val SQRT_5: F128 = 2.23606797749978969640917366873127623544061835961152572427089

// Also, @unstable(feature = "f128", issue = "116909")

/**
 * 1/sqrt(5)
 */
public val FRAC_1_SQRT_5: F128 = 0.447213595499957939281834733746255247088123671922305144854179

/**
 * Euler's number (e)
 */
public val E: F128 = 2.71828182845904523536028747135266249775724709369995957496697

/**
 * log<sub>2</sub>(10)
 */
public val LOG2_10: F128 = 3.32192809488736234787031942948939017586483139302458061205476

/**
 * log<sub>2</sub>(e)
 */
public val LOG2_E: F128 = 1.44269504088896340735992468100189213742664595415298593413545

/**
 * log<sub>10</sub>(2)
 */
public val LOG10_2: F128 = 0.301029995663981195213738894724493026768189881462108541310427

/**
 * log<sub>10</sub>(e)
 */
public val LOG10_E: F128 = 0.434294481903251827651128918916605082294397005803666566114454

/**
 * ln(2)
 */
public val LN_2: F128 = 0.693147180559945309417232121458176568075500134360255254120680

/**
 * ln(10)
 */
public val LN_10: F128 = 2.30258509299404568401799145468436420760110148862877297603333

// > if you intended to check if the operand is NaN, use `.isNan()` instead :)

/**
 * Returns `true` if this value is NaN.
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val nan = NAN
 *     val f = 7.0
 *     check(nan.isNan())
 *     check(!f.isNan())
 * }
 * ```
 */
public fun F128.isNan(): Boolean = this != this

/**
 * Returns `true` if this value is positive infinity or negative infinity, and
 * `false` otherwise.
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 7.0
 *     val inf = INFINITY
 *     val negInf = NEG_INFINITY
 *     val nan = NAN
 *     check(!f.isInfinite())
 *     check(!nan.isInfinite())
 *     check(inf.isInfinite())
 *     check(negInf.isInfinite())
 * }
 * ```
 */
public fun F128.isInfinite(): Boolean = (this == INFINITY) or (this == NEG_INFINITY)

/**
 * Returns `true` if this number is neither infinite nor NaN.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.F128
 *
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 7.0
 *     val inf: F128 = INFINITY
 *     val negInf: F128 = NEG_INFINITY
 *     val nan: F128 = NAN
 *     check(f.isFinite())
 *     check(!nan.isFinite())
 *     check(!inf.isFinite())
 *     check(!negInf.isFinite())
 * }
 * ```
 */
public fun F128.isFinite(): Boolean {
    // There's no need to handle NaN separately: if self is NaN,
    // the comparison is not true, exactly as desired.
    return this.abs() < F128.INFINITY
}

/**
 * Returns `true` if the number is [subnormal].
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val min = MIN_POSITIVE
 *     // 3.362103143e-4932f128
 *     val max = MAX
 *     val lowerThanMin = 1.0e-4960
 *     val zero = 0.0
 *     check(!min.isSubnormal())
 *     check(!max.isSubnormal())
 *     check(!zero.isSubnormal())
 *     check(!NAN.isSubnormal())
 *     check(!INFINITY.isSubnormal())
 *     // Values between `0` and `min` are Subnormal.
 *     check(lowerThanMin.isSubnormal())
 * }
 * ```
 *
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun F128.isSubnormal(): Boolean =
    when (this.classify()) {
        FpCategory.Subnormal -> true
        else -> false
    }

/**
 * Returns `true` if the number is neither zero, infinite, [subnormal], or NaN.
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val min = MIN_POSITIVE
 *     // 3.362103143e-4932f128
 *     val max = MAX
 *     val lowerThanMin = 1.0e-4960
 *     val zero = 0.0
 *     check(min.isNormal())
 *     check(max.isNormal())
 *     check(!zero.isNormal())
 *     check(!NAN.isNormal())
 *     check(!INFINITY.isNormal())
 *     // Values between `0` and `min` are Subnormal.
 *     check(!lowerThanMin.isNormal())
 * }
 * ```
 *
 * [subnormal]: https://en.wikipedia.org/wiki/DenormalNumber
 */
public fun F128.isNormal(): Boolean =
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val num = 12.4
 *     val inf = INFINITY
 *     check(num.classify() == FpCategory.Normal)
 *     check(inf.classify() == FpCategory.Infinite)
 * }
 * ```
 */
public fun F128.classify(): FpCategory {
    val bits = this.toBits()
    return run {
        val matchVal = Pair(bits and F128.MANTISSA_MASK, bits and F128.EXPONENT_MASK)
        when {
            matchVal.first == 0 && matchVal.second == F128.EXPONENT_MASK -> FpCategory.Infinite
            matchVal.second == F128.EXPONENT_MASK -> FpCategory.Nan
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 7.0
 *     val g = -7.0
 *     check(f.isSignPositive())
 *     check(!g.isSignPositive())
 * }
 * ```
 */
public fun F128.isSignPositive(): Boolean = !this.isSignNegative()

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 7.0
 *     val g = -7.0
 *     check(!f.isSignNegative())
 *     check(g.isSignNegative())
 * }
 * ```
 */
public fun F128.isSignNegative(): Boolean {
    // IEEE754 says: isSignMinus(x) is true if and only if x has negative sign. isSignMinus
    // applies to zeros and NaNs as well.
    // SAFETY: This is just transmuting to get the sign bit, it's fine.
    return (this.toBits() and (U128(0x0uL, 0x1uL) shl 127)) != U128(0x0uL, 0x0uL)
}

/**
 * Returns the least number greater than `self`.
 *
 * Let `TINY` be the smallest representable positive `f128`. Then,
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     // f128.Epsilon is the difference between 1.0 and the next number up.
 *     check(1.0.nextUp() == 1.0 + EPSILON)
 *     // But not for most numbers.
 *     check(0.1.nextUp() < 0.1 + EPSILON)
 *     check(4611686018427387904.nextUp() == 4611686018427387904.000000000000001)
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
public fun F128.nextUp(): F128 {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toBits()
    if (this.isNan() || bits == F128.INFINITY.toBits()) {
        return this
    }
    val abs = bits and F128.SIGN_MASK.inv()
    val nextBits =
        if (abs == U128(0x0uL, 0x0uL)) {
            F128.TINY_BITS
        } else if (bits == abs) {
            bits + U128(0x0uL, 0x1uL)
        } else {
            bits - U128(0x0uL, 0x1uL)
        }
    return F128.fromBits(nextBits)
}

/**
 * Returns the greatest number less than `self`.
 *
 * Let `TINY` be the smallest representable positive `f128`. Then,
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     // Clamp value into range [0, 1).
 *     val clamped = x.clamp(0.0, 1.0.nextDown())
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
public fun F128.nextDown(): F128 {
    // Some targets violate Rust's assumption of IEEE semantics, e.g. by flushing
    // denormals to zero. This is in general unsound and unsupported, but here
    // we do our best to still produce the correct result on such targets.
    val bits = this.toBits()
    if (this.isNan() || bits == F128.NEG_INFINITY.toBits()) {
        return this
    }
    val abs = bits and F128.SIGN_MASK.inv()
    val nextBits =
        if (abs == U128(0x0uL, 0x0uL)) {
            F128.NEG_TINY_BITS
        } else if (bits == abs) {
            bits - U128(0x0uL, 0x1uL)
        } else {
            bits + U128(0x0uL, 0x1uL)
        }
    return F128.fromBits(nextBits)
}

/**
 * Takes the reciprocal (inverse) of a number, `1 / x`.
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 2.0
 *     val absDifference = (x.recip() - (1.0 / x)).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.recip(): F128 = 1.0 / this

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val angle = PI
 *     val absDifference = (angle.toDegrees() - 180.0).abs()
 *     check(absDifference <= EPSILON)
 * }
 * ```
 */
public fun F128.toDegrees(): F128 {
    // The division here is correctly rounded with respect to the true value of 180/π.
    // Although π is irrational and already rounded, the double rounding happens
    // to produce correct result for f128.
    val PIS_IN_180: F128 = 180.0 / PI
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val angle = 180.0
 *     val absDifference = (angle.toRadians() - PI).abs()
 *     check(absDifference <= 1e-30)
 * }
 * ```
 */
public fun F128.toRadians(): F128 {
    // Use a literal to avoid double rounding, consts.PI is already rounded,
    // and dividing would round again.
    val RADS_PER_DEG: F128 = 0.0174532925199432957692369076848861271344287188854172545609719
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val y = 2.0
 *     check(x.max(y) == y)
 *     check(x.max(NAN) == x)
 * }
 * ```
 */
public fun F128.max(other: F128): F128 = maximumNumberNszF128(this, other)

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val y = 2.0
 *     check(x.min(y) == x)
 *     check(x.min(NAN) == x)
 * }
 * ```
 */
public fun F128.min(other: F128): F128 = minimumNumberNszF128(this, other)

// @unstable(feature = "floatMinimumMaximum", issue = "91079")

/**
 * Returns the maximum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f128.max] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `maximum`.
 *
 * ```kotlin
 * @feature(f128)
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val y = 2.0
 *     check(x.maximum(y) == y)
 *     check(x.maximum(NAN).isNan())
 * }
 * ```
 */
public fun F128.maximum(other: F128): F128 = maximumf128(this, other)

// @unstable(feature = "floatMinimumMaximum", issue = "91079")

/**
 * Returns the minimum of the two numbers, propagating NaN.
 *
 * If at least one of the arguments is NaN, the return value is NaN, with the bit pattern
 * picked using the usual [rules for arithmetic operations](f32#nan-bit-patterns). Furthermore,
 * `- 0.0` is considered to be less than `+ 0.0`, making this function fully deterministic for
 * non-NaN inputs.
 *
 * This is in contrast to [f128.min] which only returns NaN when *both* arguments are NaN,
 * and which does not reliably order `- 0.0` and `+ 0.0`.
 *
 * This follows the IEEE 754-2019 semantics for `minimum`.
 *
 * ```kotlin
 * @feature(f128)
 * @feature(floatMinimumMaximum)
 * public fun doctestEntry() {
 *     val x = 1.0
 *     val y = 2.0
 *     check(x.minimum(y) == x)
 *     check(x.minimum(NAN).isNan())
 * }
 * ```
 */
public fun F128.minimum(other: F128): F128 = minimumf128(this, other)

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * This returns NaN when *either* argument is NaN or if a combination of
 * +inf and -inf is provided as arguments.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check(1.midpoint(4.0) == 2.5)
 *     check((-5.5).midpoint(8.0) == 1.25)
 * }
 * ```
 */
public fun F128.midpoint(other: F128): F128 {
    val HI: F128 = max * 0.5
    val (a, b) = Pair(this, other)
    val absA = a.abs()
    val absB = b.abs()
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val value = 4.6
 *     val rounded = value.toIntUnchecked<UShort>()
 *     check(rounded == 4)
 *     val value1 = -128.9
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
public inline fun <reified Int> F128.toIntUnchecked(): Int {
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
 * @feature(floatConversions, f128)
 * public fun doctestEntry() {
 *     val x = 1.5
 *     check(x.cast() == 1.5)
 * }
 * ```
 */
public inline fun <reified Flt> F128.cast(): Flt = this.cast()

/**
 * Rounds toward zero and converts to any primitive integer type, saturating
 * at the type's boundaries and mapping `NaN` to zero.
 *
 * This is equivalent to `self as Int`.
 *
 * ```kotlin
 * @feature(floatConversions, f128)
 * public fun doctestEntry() {
 *     check(4.6.toIntSaturating<UByte>() == 4)
 *     check(NAN.toIntSaturating<UByte>() == 0)
 * }
 * ```
 */
public inline fun <reified Int> F128.toIntSaturating(): Int = this.toIntSaturating()

/**
 * Rounds toward zero and converts to any primitive integer type, returning
 * `None` if the value is `NaN`, infinite, or does not fit in the target type.
 *
 * ```kotlin
 * @feature(floatConversions, f128)
 * public fun doctestEntry() {
 *     check(4.6.toIntChecked<UByte>() == 4)
 *     check(NAN.toIntChecked<UByte>() == null)
 * }
 * ```
 */
public inline fun <reified Int> F128.toIntChecked(): Int? = this.toIntChecked()

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
 * @feature(floatConversions, f128)
 * public fun doctestEntry() {
 *     check(4.6.toIntStrict<UByte>() == 4)
 * }
 * ```
 */
public inline fun <reified Int> F128.toIntStrict(): Int = (this.toIntChecked<Int>() ?: error("the value cannot be represented in the target integer type"))

/**
 * Raw transmutation to `ULong`.
 *
 * This is currently identical to `reinterpret<f128, ULong>(self)` on all platforms.
 *
 * See [`fromBits`](#method.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * Note that this function is distinct from `as` casting, which attempts to
 * preserve the *numeric* value, and not the bitwise value.
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check((1).toBits() != U128.fromLong(1.toLong()))
 *     // toBits() is not casting!
 *     check((12.5).toBits() == U128(0x4002900000000000uL, 0x0uL))
 * }
 * ```
 */
public fun F128.toBits(): U128 {
    // SAFETY: `ULong` is a plain old datatype so we can always reinterpret to it.
    return mem.transmute(this)
}

/**
 * Raw transmutation from `ULong`.
 *
 * This is currently identical to `reinterpret<ULong, f128>(v)` on all platforms.
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val v = fromBits(U128(0x4002900000000000uL, 0x0uL))
 *     check(v == 12.5)
 * }
 * ```
 */
public fun F128.Companion.fromBits(v: U128): F128 {
    // It turns out the safety issues with sNaN were overblown! Hooray!
    // SAFETY: `ULong` is a plain old datatype so we can always reinterpret from it.
    return mem.transmute(v)
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val bytes = 12.5.toBeBytes()
 *     check(bytes == ubyteArrayOf(0x40u, 0x02u, 0x90u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u))
 * }
 * ```
 */
public fun F128.toBeBytes(): UByteArray = this.toBits().toBeBytes()

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val bytes = 12.5.toLeBytes()
 *     check(bytes == ubyteArrayOf(0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x90u, 0x02u, 0x40u))
 * }
 * ```
 */
public fun F128.toLeBytes(): UByteArray = this.toBits().toLeBytes()

/**
 * Returns the memory representation of this floating point number as a byte array in
 * native byte order.
 *
 * As the target platform's native endianness is used, portable code
 * should use [toBeBytes] or [toLeBytes], as appropriate, instead.
 *
 * [toBeBytes]: f128.toBeBytes
 * [toLeBytes]: f128.toLeBytes
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val bytes = 12.5.toNeBytes()
 *     check(bytes == if (false) {
 *         listOf(0x40, 0x02, 0x90, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
 *     } else {
 *         listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x90, 0x02, 0x40)
 *     })
 * }
 * ```
 */
public fun F128.toNeBytes(): UByteArray = this.toBits().toLeBytes()

/**
 * Creates a floating point value from its representation as a byte array in big endian.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val value = fromBeBytes(listOf(0x40, 0x02, 0x90, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
 *     check(value == 12.5)
 * }
 * ```
 */
public fun F128.Companion.fromBeBytes(bytes: UByteArray): F128 = F128.fromBits(U128.fromBeBytes(bytes))

/**
 * Creates a floating point value from its representation as a byte array in little endian.
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val value = fromLeBytes(listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x90, 0x02, 0x40))
 *     check(value == 12.5)
 * }
 * ```
 */
public fun F128.Companion.fromLeBytes(bytes: UByteArray): F128 = F128.fromBits(U128.fromLeBytes(bytes))

/**
 * Creates a floating point value from its representation as a byte array in native endian.
 *
 * As the target platform's native endianness is used, portable code
 * likely wants to use [fromBeBytes] or [fromLeBytes], as
 * appropriate instead.
 *
 * [fromBeBytes]: f128.fromBeBytes
 * [fromLeBytes]: f128.fromLeBytes
 *
 * See [`fromBits`](Self.fromBits) for some discussion of the
 * portability of this operation (there are almost no issues).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val value = fromNeBytes(if (false) {
 *         listOf(0x40, 0x02, 0x90, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
 *     } else {
 *         listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x90, 0x02, 0x40)
 *     })
 *     check(value == 12.5)
 * }
 * ```
 */
public fun F128.Companion.fromNeBytes(bytes: UByteArray): F128 = F128.fromBits(U128.fromLeBytes(bytes))

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
 * [PartialOrd] and [PartialEq] implementations of `f128`. For example,
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
 * import io.github.kotlinmania.rustlang.core.F128
 *
 * @feature(f128)
 * public fun doctestEntry() {
 *     open class GoodBoy(public var name: String, public var weight: F128)
 *     var bois = mutableListOf(GoodBoy(name = "Pucci", weight = 0.1), GoodBoy(name = "Woofer", weight = 99.0), GoodBoy(name = "Yapper", weight = 10.0), GoodBoy(name = "Chonk", weight = INFINITY), GoodBoy(name = "Abs. Unit", weight = NAN), GoodBoy(name = "Floaty", weight = -5.0))
 *     bois.sortBy({ a, b -> a.weight.totalCmp(b.weight) })
 *     // `f128.NAN` could be positive or negative, which will affect the sort order.
 *     if (NAN.isSignNegative()) {
 *         (ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(listOf(NAN, -5.0, 0.1, 10.0, 99.0, INFINITY).iter())).forEach({ (a, b) -> check(a.toBits() == b.toBits()) })
 *     } else {
 *         (ArrayDeque((ArrayDeque(bois)).map({ b -> b.weight })).zip(listOf(-5.0, 0.1, 10.0, 99.0, INFINITY, NAN).iter())).forEach({ (a, b) -> check(a.toBits() == b.toBits()) })
 *     }
 * }
 * ```
 */
public fun F128.totalCmp(other: F128): Ordering {
    var left = this.toBits().toI128()
    var right = I128.fromULong(other.toBits())
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
    left = left xor ((((left shr 127).toU128()) shr 1).toI128())
    right = right xor ((((right shr 127).toU128()) shr 1).toI128())
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     check((-3.0).clamp(-2.0, 1.0) == -2.0)
 *     check((0.0).clamp(-2.0, 1.0) == 0.0)
 *     check((2.0).clamp(-2.0, 1.0) == 1.0)
 *     check((NAN).clamp(-2.0, 1.0).isNan())
 *     // These always returns zero, but the sign (which is ignored by `==`) is non-deterministic.
 *     check((0.0).clamp(-0.0, -0.0) == 0.0)
 *     check((1.0).clamp(-0.0, 0.0) == 0.0)
 *     // This is definitely a negative zero.
 *     check((-1.0).clamp(-0.0, 1.0).isSignNegative())
 * }
 * ```
 */
public fun F128.clamp(min: F128, max: F128): F128 {
    constAssert(min <= max, "min > max, or either was NaN", "min > max, or either was NaN. min = {min:?}, max = {max:?}", min, f128, max, f128)
    if (this < min) {
        this = min
    }
    if (this > max) {
        this = max
    }
    return this
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
 * @feature(f128)
 * @feature(clampMagnitude)
 * public fun doctestEntry() {
 *     check(5.0.clampMagnitude(3.0) == 3.0)
 *     check((-5.0).clampMagnitude(3.0) == -3.0)
 *     check(2.0.clampMagnitude(3.0) == 2.0)
 *     check((-2.0).clampMagnitude(3.0) == -2.0)
 * }
 * ```
 */
public fun F128.clampMagnitude(limit: F128): F128 {
    check(limit >= 0.0) { "limit must be non-negative and not NaN" }
    val limit1 = limit.abs()
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
 * @feature(f128, clampTo)
 * public fun doctestEntry() {
 *     check((-3.0).clampTo(-2.0..1.0) == -2.0)
 *     check(0.0.clampTo(-2.0..1.0) == 0.0)
 *     check(2.0.clampTo(0..1.0) == 1.0)
 *     check(5.0.clampTo(7.0 until Int.MAX_VALUE) == 7.0)
 *     check(NAN.clampTo(1.0..2.0).isNan())
 * }
 * ```
 */
public fun <R> F128.clampTo(range: R): F128 where R : ClampBounds<F128> = range.clamp(this)

/**
 * Computes the absolute value of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 3.5
 *     val y = -3.5
 *     check(x.abs() == x)
 *     check(y.abs() == -y)
 *     check(NAN.abs().isNan())
 * }
 * ```
 */
public fun F128.abs(): F128 = fabs(this)

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.5
 *     check(f.signum() == 1.0)
 *     check(NEG_INFINITY.signum() == -1.0)
 *     check(NAN.signum().isNan())
 * }
 * ```
 */
public fun F128.signum(): F128 =
    if (this.isNan()) {
        F128.NAN
    } else {
        1.0.copysign(this)
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.5
 *     check(f.copysign(0.42) == 3.5)
 *     check(f.copysign(-0.42) == -3.5)
 *     check((-f).copysign(0.42) == 3.5)
 *     check((-f).copysign(-0.42) == -3.5)
 *     check(NAN.copysign(1.0).isNan())
 * }
 * ```
 */
public fun F128.copysign(sign: F128): F128 = copysignf128(this, sign)

/**
 * Float addition that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F128.algebraicAdd(rhs: F128): F128 = faddAlgebraic(this, rhs)

/**
 * Float subtraction that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F128.algebraicSub(rhs: F128): F128 = fsubAlgebraic(this, rhs)

/**
 * Float multiplication that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F128.algebraicMul(rhs: F128): F128 = fmulAlgebraic(this, rhs)

/**
 * Float division that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F128.algebraicDiv(rhs: F128): F128 = fdivAlgebraic(this, rhs)

/**
 * Float remainder that allows optimizations based on algebraic rules.
 *
 * See [algebraic operators](primitive@f32#algebraic-operators) for more info.
 */
public fun F128.algebraicRem(rhs: F128): F128 = fremAlgebraic(this, rhs)

/**
 * Returns `self` if the value is not NaN, otherwise returns `replacement`
 * if `self` is NaN.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * @feature(floatNanTo)
 * public fun doctestEntry() {
 *     val n = NAN
 *     val x = 2.0
 *     val y = INFINITY
 *     check(n.nanTo(0.0) == 0.0)
 *     check(x.nanTo(0.0) == 2.0)
 *     check(y.nanTo(0.0) == INFINITY)
 * }
 * ```
 */
public fun F128.nanTo(replacement: F128): F128 =
    if (this.isNan()) {
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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.7
 *     val g = 3.0
 *     val h = -3.7
 *     check(f.floor() == 3.0)
 *     check(g.floor() == 3.0)
 *     check(h.floor() == -4.0)
 * }
 * ```
 */
public fun F128.floor(): F128 = io.github.kotlinmania.rustlang.core.intrinsics.floor(this)

/**
 * Returns the smallest integer greater than or equal to `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.01
 *     val g = 4.0
 *     check(f.ceil() == 4.0)
 *     check(g.ceil() == 4.0)
 * }
 * ```
 */
public fun F128.ceil(): F128 = io.github.kotlinmania.rustlang.core.intrinsics.ceil(this)

/**
 * Returns the nearest integer to `self`. If a value is half-way between two
 * integers, round away from `0.0`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.3
 *     val g = -3.3
 *     val h = -3.7
 *     val i = 3.5
 *     val j = 4.5
 *     check(f.round() == 3.0)
 *     check(g.round() == -3.0)
 *     check(h.round() == -4.0)
 *     check(i.round() == 4.0)
 *     check(j.round() == 5.0)
 * }
 * ```
 */
public fun F128.round(): F128 = io.github.kotlinmania.rustlang.core.intrinsics.round(this)

/**
 * Returns the nearest integer to a number. Rounds half-way cases to the number
 * with an even least significant digit.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.3
 *     val g = -3.3
 *     val h = 3.5
 *     val i = 4.5
 *     check(f.roundTiesEven() == 3.0)
 *     check(g.roundTiesEven() == -3.0)
 *     check(h.roundTiesEven() == 4.0)
 *     check(i.roundTiesEven() == 4.0)
 * }
 * ```
 */
public fun F128.roundTiesEven(): F128 = io.github.kotlinmania.rustlang.core.intrinsics.roundTiesEven(this)

/**
 * Returns the integer part of `self`.
 * This means that non-integer numbers are always truncated towards zero.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val f = 3.7
 *     val g = 3.0
 *     val h = -3.7
 *     check(f.trunc() == 3.0)
 *     check(g.trunc() == 3.0)
 *     check(h.trunc() == -3.0)
 * }
 * ```
 */
public fun F128.trunc(): F128 = io.github.kotlinmania.rustlang.core.intrinsics.trunc(this)

/**
 * Returns the fractional part of `self`.
 *
 * This function always returns the precise result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     val x = 3.6
 *     val y = -3.6
 *     val absDifferenceX = (x.fract() - 0.6).abs()
 *     val absDifferenceY = (y.fract() - (-0.6)).abs()
 *     check(absDifferenceX <= EPSILON)
 *     check(absDifferenceY <= EPSILON)
 * }
 * ```
 */
public fun F128.fract(): F128 = this - this.trunc()

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val m = 10.0
 *     val x = 4.0
 *     val b = 60.0
 *     check(m.mulAdd(x, b) == 100.0)
 *     check(m * x + b == 100.0)
 *     val onePlusEps = 1.0 + EPSILON
 *     val oneMinusEps = 1.0 - EPSILON
 *     val minusOne = -1.0
 *     // The exact result (1 + eps) * (1 - eps) = 1 - eps * eps.
 *     check(onePlusEps.mulAdd(oneMinusEps, minusOne) == -EPSILON * EPSILON)
 *     // Different rounding with the non-fused multiply and add.
 *     check(onePlusEps * oneMinusEps + minusOne == 0.0)
 * }
 * ```
 */
public fun F128.mulAdd(a: F128, b: F128): F128 = fma(this, a, b)

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
 * @feature(f128)
 * @feature(floatMulAddRelaxed)
 * public fun doctestEntry() {
 *     // When the fused and unfused operations round differently, either
 *     // result may be returned:
 *     // - 7.824090399073145653039910391751267e-37 is the fused result (one rounding)
 *     // - 1.5046327690525280101999827676444745e-36 is the unfused result (two roundings)
 *     val r = 0.1.mulAddRelaxed(0.1, -0.01)
 *     check(r == 7.824090399073145653039910391751267e-37 || r == 1.5046327690525280101999827676444745e-36)
 * }
 * ```
 */
public fun F128.mulAddRelaxed(a: F128, b: F128): F128 = fmuladd(this, a, b)

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
 * import io.github.kotlinmania.rustlang.core.F128
 *
 * @feature(f128)
 * public fun doctestEntry() {
 *     val a: F128 = 7.0
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
public fun F128.divEuclid(rhs: F128): F128 {
    val q = (this / rhs).trunc()
    if (this % rhs < 0.0) {
        return if (rhs > 0.0) {
            q - 1.0
        } else {
            q + 1.0
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
 * import io.github.kotlinmania.rustlang.core.F128
 *
 * @feature(f128)
 * public fun doctestEntry() {
 *     val a: F128 = 7.0
 *     val b = 4.0
 *     check(a.remEuclid(b) == 3.0)
 *     check((-a).remEuclid(b) == 1.0)
 *     check(a.remEuclid(-b) == 3.0)
 *     check((-a).remEuclid(-b) == 1.0)
 *     // limitation due to round-off error
 *     check((-EPSILON).remEuclid(3.0) != 0.0)
 * }
 * ```
 */
public fun F128.remEuclid(rhs: F128): F128 {
    val r = this % rhs
    return if (r < 0.0) {
        r + rhs.abs()
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
 * example, `f128.powi(f128.NAN, 0)` returns `1.0`. However, if an input is a *signaling*
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
 *     val absDifference = (x.powi(2) - (x * x)).abs()
 *     check(absDifference <= 1e-9)
 *     check(powi(NAN, 0) == 1.0)
 *     check(powi(0.0, 0) == 1.0)
 * }
 * ```
 */
public fun F128.powi(n: Int): F128 = io.github.kotlinmania.rustlang.core.intrinsics.powi(this, n)

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
 * @feature(f128)
 * public fun doctestEntry() {
 *     val positive = 4.0
 *     val negative = -4.0
 *     val negativeZero = -0.0
 *     check(positive.sqrt() == 2.0)
 *     check(negative.sqrt().isNan())
 *     check(negativeZero.sqrt() == negativeZero)
 * }
 * ```
 */
public fun F128.sqrt(): F128 = io.github.kotlinmania.rustlang.core.intrinsics.sqrt(this)
