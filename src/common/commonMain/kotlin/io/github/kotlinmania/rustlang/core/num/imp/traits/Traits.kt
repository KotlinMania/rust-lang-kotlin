// port-lint: source num/imp/traits.rs
package io.github.kotlinmania.rustlang.core.num.imp.traits

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.FpCategory
import io.github.kotlinmania.rustlang.core.num.f16.classify
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.Div
import io.github.kotlinmania.rustlang.core.ops.arith.Mul
import io.github.kotlinmania.rustlang.core.ops.arith.Neg
import io.github.kotlinmania.rustlang.core.ops.bit.BitAnd
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.Shl
import io.github.kotlinmania.rustlang.core.ops.bit.Shr

// Numeric traits used for internal implementations.

/**
 * Lossy `as` casting between two types.
 */
public interface CastInto<T, SelfType> {
    public fun cast(): T
}

/**
 * Collection of traits that allow us to be generic over integer size.
 */
public interface Int<T> :
    Shr<UInt, T>,
    Shl<UInt, T>,
    BitAnd<T, T>,
    BitOr<T, T>,
    CastInto<Short, T> {
    public val ZERO: T

    public val ONE: T
}

public fun UShort.cast(): Short = this.toShort()

public val ZERO: UShort = 0u

public val ONE: UShort = 1u

public fun UInt.cast(): Short = this.toShort()

public val ZERO1: UInt = 0u

public val ONE1: UInt = 1u

public fun ULong.cast(): Short = this.toShort()

public val ZERO2: ULong = 0uL

public val ONE2: ULong = 1uL

/**
 * A helper trait to avoid duplicating basically all the conversion code for IEEE floats.
 */
public interface Float<T, out Int> :
    Div<T, T>,
    Neg<T>,
    Mul<T, T>,
    Add<T, T> {
    public val INFINITY: T

    public val NEG_INFINITY: T

    public val NAN: T

    public val NEG_NAN: T

    /**
     * Bit width of the float
     */
    public val BITS: UInt

    /**
     * The number of bits in the significand, *including* the hidden bit.
     */
    public val SIG_TOTAL_BITS: UInt

    public val EXP_MASK: Int

    public val SIG_MASK: Int

    /**
     * The number of bits in the significand, *excluding* the hidden bit.
     */
    public val SIG_BITS: UInt
        get() = T.SIG_TOTAL_BITS - 1u

    /**
     * Number of bits in the exponent.
     */
    public val EXP_BITS: UInt
        get() = T.BITS - T.SIG_BITS - 1u

    /**
     * The saturated (maximum bitpattern) value of the exponent, i.e. the infinite
     * representation.
     *
     * This shifted fully right, use `EXP_MASK` for the shifted value.
     */
    public val EXP_SAT: UInt
        get() = (1u shl T.EXP_BITS.toInt()) - 1u

    /**
     * Signed version of `EXP_SAT` since we convert a lot.
     */
    public val INFINITE_POWER: Int
        get() = T.EXP_SAT.toInt()

    /**
     * The exponent bias value. This is also the maximum value of the exponent.
     */
    public val EXP_BIAS: UInt
        get() = T.EXP_SAT shr 1

    /**
     * Minimum exponent value of normal values.
     */
    public val EXP_MIN: Int
        get() = -(T.EXP_BIAS.toInt() - 1)

    /**
     * Round-to-even only happens for negative values of q
     * when q ≥ −4 in the 64-bit case and when q ≥ −17 in
     * the 32-bit case.
     *
     * When q ≥ 0,we have that 5^q ≤ 2m+1. In the 64-bit case,we
     * have 5^q ≤ 2m+1 ≤ 2^54 or q ≤ 23. In the 32-bit case,we have
     * 5^q ≤ 2m+1 ≤ 2^25 or q ≤ 10.
     *
     * When q < 0, we have w ≥ (2m+1)×5^−q. We must have that w < 2^64
     * so (2m+1)×5^−q < 2^64. We have that 2m+1 > 2^53 (64-bit case)
     * or 2m+1 > 2^24 (32-bit case). Hence,we must have 2^53×5^−q < 2^64
     * (64-bit) and 2^24×5^−q < 2^64 (32-bit). Hence we have 5^−q < 2^11
     * or q ≥ −4 (64-bit case) and 5^−q < 2^40 or q ≥ −17 (32-bit case).
     *
     * Thus we have that we only need to round ties to even when
     * we have that q ∈ [−4,23](in the 64-bit case) or q∈[−17,10]
     * (in the 32-bit case). In both cases,the power of five(5^|q|)
     * fits in a 64-bit word.
     */
    public val MIN_EXPONENT_ROUND_TO_EVEN: Int

    public val MAX_EXPONENT_ROUND_TO_EVEN: Int

    /**
     * Largest decimal exponent for a non-infinite value.
     *
     * This is the max exponent in binary converted to the max exponent in decimal. Allows fast
     * pathing anything larger than `10 ^ LARGEST_POWER_OF_TEN`, which will round to infinity.
     */
    public val LARGEST_POWER_OF_TEN: Int
        get() =
            run {
                val largestPow2 = T.EXP_BIAS + 1
                pow2ToPow10(largestPow2.toLong()).toInt()
            }

    /**
     * Smallest decimal exponent for a non-zero value. This allows for fast pathing anything
     * smaller than `10 ^ SMALLEST_POWER_OF_TEN`, which will round to zero.
     *
     * The smallest power of ten is represented by `⌊log10(2^-n / (2^64 - 1))⌋`, where `n` is
     * the smallest power of two. The `2^64 - 1)` denominator comes from the number of values
     * that are representable by the intermediate storage format. I don't actually know _why_
     * the storage format is relevant here.
     *
     * The values may be calculated using the formula. Unfortunately we cannot calculate them at
     * compile time since intermediates exceed the range of an `Double`.
     */
    public val SMALLEST_POWER_OF_TEN: Int

    /**
     * Returns the category that this number falls into.
     */
    public fun classify(): FpCategory

    /**
     * Transmute to the integer representation
     */
    public fun toBits(): Int
}

/**
 * Items that ideally would be on `Float`, but don't apply to all float types because they
 * rely on the mantissa fitting into a `ULong` (which isn't true for `f128`).
 */
public interface FloatExt<T> : Float<T, Any?> {
    /**
     * Performs a raw transmutation from an integer.
     */
    public fun fromU64Bits(v: ULong): T

    /**
     * Returns the mantissa, exponent and sign as integers.
     *
     * This returns `(m, p, s)` such that `s * m * 2 ^ p` represents the original float. For 0, the
     * exponent will be `- (EXP_BIAS + SIG_BITS)`, which is the minimum subnormal power. For
     * infinity or NaN, the exponent will be `EXP_SAT - EXP_BIAS - SIG_BITS`.
     *
     * If subnormal, the mantissa will be shifted one bit to the left. Otherwise, it is returned
     * with the explicit bit set but otherwise unshifted
     *
     * `s` is only ever +/-1.
     */
    public fun integerDecode(): Triple<ULong, Short, Byte> {
        val bits = this.toBits()
        val sign: Byte =
            if (bits shr (T.BITS - 1).toInt() == T.Int.ZERO) {
                1
            } else {
                -1
            }
        var exponent: Short = ((bits and T.EXP_MASK) shr T.SIG_BITS.toInt()).cast()
        val mantissa =
            if (exponent == 0.toShort()) {
                (bits and T.SIG_MASK) shl 1
            } else {
                (bits and T.SIG_MASK) or (T.Int.ONE shl T.SIG_BITS.toInt())
            }
        // Exponent bias + mantissa shift
        exponent -= (T.EXP_BIAS + T.SIG_BITS).toShort()
        return Triple(mantissa.into(), exponent, sign)
    }
}

/**
 * Solve for `b` in `10 ^ b = 2 ^ a`
 */
public fun pow2ToPow10(a: Long): Long {
    val res = (a.toDouble()) / 3.321928094887362
    return res.toLong()
}

public val INFINITY: F16 = F16.INFINITY

public val NEG_INFINITY: F16 = F16.NEG_INFINITY

public val NAN: F16 = F16.NAN

public val NEG_NAN: F16 = -F16.NAN

public val BITS: UInt = 16u

public val SIG_TOTAL_BITS: UInt = F16.MANTISSA_DIGITS

public val EXP_MASK: UShort = F16.EXPONENT_MASK

public val SIG_MASK: UShort = F16.MANTISSA_MASK

public val MIN_EXPONENT_ROUND_TO_EVEN: Int = -22

public val MAX_EXPONENT_ROUND_TO_EVEN: Int = 5

public val SMALLEST_POWER_OF_TEN: Int = -27

public fun F16.toBitsFloat(): UShort = this.toBits()

public fun F16.classifyFloat(): FpCategory = this.classify()

public fun floatExtFromU64BitsF16(v: ULong): F16 = F16.fromBits((v and 0xFFFFuL).toUShort())

public val INFINITY1: kotlin.Float = kotlin.Float.POSITIVE_INFINITY

public val NEG_INFINITY1: kotlin.Float = kotlin.Float.NEGATIVE_INFINITY

public val NAN1: kotlin.Float = kotlin.Float.NaN

public val NEG_NAN1: kotlin.Float = -kotlin.Float.NaN

public val BITS1: UInt = 32u

public val SIG_TOTAL_BITS1: UInt = 24u

public val EXP_MASK1: UInt = kotlin.Float.EXPONENT_MASK

public val SIG_MASK1: UInt = kotlin.Float.MANTISSA_MASK

public val MIN_EXPONENT_ROUND_TO_EVEN1: Int = -17

public val MAX_EXPONENT_ROUND_TO_EVEN1: Int = 10

public val SMALLEST_POWER_OF_TEN1: Int = -65

public fun kotlin.Float.toBitsFloat(): UInt = this.toRawBits().toUInt()

public fun kotlin.Float.classifyFloat(): FpCategory = this.classify()

public fun floatExtFromU64BitsFloat(v: ULong): kotlin.Float = kotlin.Float.fromBits(((v and 0xFFFFFFFFuL).toUInt()).toInt())

public val INFINITY2: Double = Double.POSITIVE_INFINITY

public val NEG_INFINITY2: Double = Double.NEGATIVE_INFINITY

public val NAN2: Double = Double.NaN

public val NEG_NAN2: Double = -Double.NaN

public val BITS2: UInt = 64u

public val SIG_TOTAL_BITS2: UInt = 53u

public val EXP_MASK2: ULong = Double.EXPONENT_MASK

public val SIG_MASK2: ULong = Double.MANTISSA_MASK

public val MIN_EXPONENT_ROUND_TO_EVEN2: Int = -4

public val MAX_EXPONENT_ROUND_TO_EVEN2: Int = 23

public val SMALLEST_POWER_OF_TEN2: Int = -342

public fun Double.toBitsFloat(): ULong = this.toRawBits().toULong()

public fun Double.classifyFloat(): FpCategory = this.classify()

public fun floatExtFromU64BitsDouble(v: ULong): Double = Double.fromBits(v.toLong())
