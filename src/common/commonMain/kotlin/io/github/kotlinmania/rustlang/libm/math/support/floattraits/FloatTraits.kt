// port-lint: source math/support/float_traits.rs
package io.github.kotlinmania.rustlang.libm.math.support.floattraits

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFmaf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFmaf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundTiesEvenf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundTiesEvenf64
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.floatFmaWithLayout
import io.github.kotlinmania.rustlang.core.mem.floatUnaryWithLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.f16.isSignNegative
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.AddAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Div
import io.github.kotlinmania.rustlang.core.ops.arith.Mul
import io.github.kotlinmania.rustlang.core.ops.arith.MulAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Neg
import io.github.kotlinmania.rustlang.core.ops.arith.Rem
import io.github.kotlinmania.rustlang.core.ops.arith.Sub
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.fma.fmaf
import io.github.kotlinmania.rustlang.libm.math.floor.floorf
import io.github.kotlinmania.rustlang.libm.math.floor.floorf128
import io.github.kotlinmania.rustlang.libm.math.floor.floorf16
import io.github.kotlinmania.rustlang.libm.math.fma.fmaf128
import io.github.kotlinmania.rustlang.libm.math.fmaf16.fmaf16
import io.github.kotlinmania.rustlang.libm.math.generic.fabs
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundevenf
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundevenf128
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundevenf16
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.DisplayHex
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

// only used in c-b

/**
 * Wrapper to extract the integer type half of the float's size
 */
public typealias HalfRep<F> = H

/**
 * Whether MIPS sNaN/qNaNs should be used.
 *
 * Note that MIPS is doing some general migration here, though this is only available on (rare)
 * modern MIPS hardware per discussion at <https://github.com/WebAssembly/design/issues/976>.
 */
public val MIPS_NAN: Boolean = false || false || false || false

// Some constants are only used with tests

/**
 * Trait for some basic operations on floats
 */
public interface Float<T, Int, SignedInt> : DisplayHex, AddAssign, MulAssign, Add<T>, Sub<T>, Mul<T>, Div<T>, Rem<T>, Neg<T> {
    public val ZERO: T

    public val NEG_ZERO: T

    public val ONE: T

    public val NEG_ONE: T

    public val INFINITY: T

    public val NEG_INFINITY: T

    public val MAX: T

    public val MIN: T

    /**
     * Quiet NaN.
     */
    public val NAN: T

    /**
     * Signaling NaN.
     */
    public val SNAN: T

    public val NEG_NAN: T

    public val NEG_SNAN: T

    /**
     * The result of quieting the default sNaN.
     */
    public val QSNAN: T

    public val NEG_QSNAN: T

    public val EPSILON: T

    public val PI: T

    public val NEG_PI: T

    public val FRAC_PI_2: T

    public val MIN_POSITIVE_NORMAL: T

    public val MIN_POSITIVE_SUBNORMAL: T

    /**
     * The bitwidth of the float type.
     */
    public val BITS: UInt

    /**
     * The bitwidth of the significand (does not include the implicit bit).
     */
    public val SIG_BITS: UInt

    /**
     * The bitwidth of the exponent.
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
     * The exponent bias value.
     */
    public val EXP_BIAS: UInt
        get() = T.EXP_SAT shr 1

    /**
     * Maximum unbiased exponent value.
     */
    public val EXP_MAX: Int
        get() = T.EXP_BIAS.toInt()

    /**
     * Minimum *NORMAL* unbiased exponent value.
     */
    public val EXP_MIN: Int
        get() = -(T.EXP_MAX - 1)

    /**
     * Minimum subnormal exponent value.
     */
    public val EXP_MIN_SUBNORM: Int
        get() = T.EXP_MIN - T.SIG_BITS.toInt()

    /**
     * A mask for the sign bit
     */
    public val SIGN_MASK: Int

    /**
     * A mask for the significand
     */
    public val SIG_MASK: Int

    /**
     * A mask for the exponent
     */
    public val EXP_MASK: Int

    /**
     * The implicit bit of the float format
     */
    public val IMPLICIT_BIT: Int

    /**
     * A mask for the top bit of the significand, useful for NaN ops.
     */
    public val SIG_TOP_BIT: Int

    /**
     * The minimum subnormal number.
     */
    public val TINY_BITS: Int
        get() = T.Int.ONE

    /**
     * Returns `self` transmuted to `Self.Int`
     */
    public fun toBits(): Int

    /**
     * Returns `self` transmuted to `Self.SignedInt`
     */
    public fun toBitsSigned(): SignedInt = this.toBits().signed()

    /**
     * Check bitwise equality.
     */
    public fun biteq(rhs: T): Boolean = this.toBits() == rhs.toBits()

    /**
     * Checks if two floats have the same bit representation. *Except* for NaNs! NaN can be
     * represented in multiple different ways.
     *
     * This method returns `true` if two NaNs are compared. Use [`biteq`](Self.biteq) instead
     * if `NaN` should not be treated separately.
     */
    public fun eqRepr(rhs: T): Boolean

    /**
     * Returns true if the value is NaN.
     */
    public fun isNan(): Boolean

    public fun isQnan(): Boolean

    public fun isSnan(): Boolean

    /**
     * Returns true if the value is +inf or -inf.
     */
    public fun isInfinite(): Boolean

    /**
     * Returns true if this number is neither infinite nor NaN.
     */
    public fun isFinite(): Boolean = this.abs() < T.INFINITY

    /**
     * Returns true if the sign is negative. Extracts the sign bit regardless of zero or NaN.
     */
    public fun isSignNegative(): Boolean

    /**
     * Returns true if the sign is positive. Extracts the sign bit regardless of zero or NaN.
     */
    public fun isSignPositive(): Boolean

    /**
     * Returns if `self` is subnormal.
     */
    public fun isSubnormal(): Boolean = (this.toBits() and T.EXP_MASK) == T.Int.ZERO

    /**
     * Returns the exponent, not adjusting for bias, not accounting for subnormals or zero.
     */
    public fun ex(): UInt = UInt.castFrom(this.toBits() shr T.SIG_BITS.toInt()) and T.EXP_SAT

    /**
     * Extract the exponent and adjust it for bias, not accounting for subnormals or zero.
     */
    public fun expUnbiased(): Int = this.ex().signed() - (T.EXP_BIAS.toInt())

    /**
     * Returns the significand with no implicit bit (or the "fractional" part)
     */
    public fun frac(): Int = this.toBits() and T.SIG_MASK

    /**
     * Returns a `Self.Int` transmuted back to `Self`
     */
    public fun fromBits(a: Int): T

    /**
     * Constructs a `Self` from its parts. Inputs are treated as bits and shifted into position.
     */
    public fun fromParts(negative: Boolean, exponent: UInt, significand: Int): T {
        val sign =
            if (negative) {
                T.Int.ONE
            } else {
                T.Int.ZERO
            }
        return this.fromBits((sign shl (T.BITS - 1).toInt()) or (T.Int.castFrom(exponent and T.EXP_SAT) shl T.SIG_BITS.toInt()) or (significand and T.SIG_MASK))
    }

    public fun abs(): T

    /**
     * Returns a number composed of the magnitude of self and the sign of sign.
     */
    public fun copysign(other: T): T

    /**
     * Fused multiply add, rounding once.
     */
    public fun fma(y: T, z: T): T

    /**
     * Round down to the nearest integer.
     */
    public fun floor(): T

    /**
     * Round to the nearest integer, breaking ties toward even.
     */
    public fun roundeven(): T

    /**
     * Returns (normalized exponent, normalized significand)
     */
    public fun normalize(significand: Int): Pair<Int, Int>

    /**
     * Returns a number that represents the sign of self.
     */
    public fun signum(): T

    /**
     * Make a best-effort attempt to canonicalize the number. Note that this is allowed
     * to be a nop and does not always quiet sNaNs.
     */
    public fun canonicalize(): T {
        // Note: LLVM often removes this. We should determine whether we can remove the operation,
        // or switch to something based on `llvm.canonicalize` (which has crashes,
        // <https://github.com/llvm/llvm-project/issues/32650>).
        return this * T.ONE
    }

    /**
     * Increment by one ULP, saturating at infinity.
     */
    public fun nextUp(): T

    /**
     * Decrement by one ULP, saturating at negative infinity.
     */
    public fun nextDown(): T
}

/**
 * Access the associated `Int` type from a float (helper to avoid ambiguous associated types).
 */
public typealias IntTy<F> = Int

public val ZERO: F16 = F16.fromFloat(0.0f)

public val NEG_ZERO: F16 = -F16.fromFloat(0.0f)

public val ONE: F16 = F16.fromFloat(1.0f)

public val NEG_ONE: F16 = -F16.fromFloat(1.0f)

public val INFINITY: F16 = F16.INFINITY

public val NEG_INFINITY: F16 = F16.NEG_INFINITY

public val MAX: F16 = -F16.MIN

public val MIN: F16 = F16.fromBits((F16.Int.MAX and ((1u.toUInt() shl (F16.SIG_BITS.toInt())).toUShort()).inv()).toUShort())

public val NAN: F16 = F16.fromBits((if (MIPS_NAN) {
    F16.EXP_MASK or (F16.SIG_TOP_BIT - 1u)
} else {
    F16.EXP_MASK or F16.SIG_TOP_BIT
}).toUShort())

public val SNAN: F16 = F16.fromBits((if (MIPS_NAN) {
    F16.EXP_MASK or F16.SIG_MASK
} else {
    F16.EXP_MASK or ((F16.SIG_TOP_BIT.toUInt() shr 1).toUShort())
}).toUShort())

public val QSNAN: F16 = if (MIPS_NAN) {
    F16.NAN
} else {
    F16.fromBits((F16.SNAN.toBits() or F16.SIG_TOP_BIT).toUShort())
}

public val NEG_NAN: F16 = F16.fromBits((F16.NAN.toBits() or F16.SIGN_MASK).toUShort())

public val NEG_SNAN: F16 = F16.fromBits((F16.SNAN.toBits() or F16.SIGN_MASK).toUShort())

public val NEG_QSNAN: F16 = F16.fromBits((F16.QSNAN.toBits() or F16.SIGN_MASK).toUShort())

public val EPSILON: F16 = F16.EPSILON

public val MIN_POSITIVE_NORMAL: F16 = F16.fromBits((1u.toUInt() shl (F16.SIG_BITS.toInt())).toUShort())

public val MIN_POSITIVE_SUBNORMAL: F16 = F16.fromBits(1u.toUShort())

public val PI: F16 = F16.fromFloat(3.1415927f)

public val NEG_PI: F16 = -F16.PI

public val FRAC_PI_2: F16 = F16.fromFloat(1.5707964f)

public val BITS: UInt = 16u

public val SIG_BITS: UInt = 10u

public val SIGN_MASK: UShort = (1u.toUInt() shl ((F16.BITS - 1).toInt())).toUShort()

public val SIG_MASK: UShort = (((1u.toUInt() shl (F16.SIG_BITS.toInt())).toUShort()) - 1u).toUShort()

public val EXP_MASK: UShort = ((F16.SIGN_MASK or F16.SIG_MASK).inv()).toUShort()

public val IMPLICIT_BIT: UShort = (1u.toUInt() shl (F16.SIG_BITS.toInt())).toUShort()

public val SIG_TOP_BIT: UShort = (F16.IMPLICIT_BIT.toUInt() shr 1).toUShort()

public fun F16.toBitsFloat(): UShort = this.toBits()

public fun F16.eqRepr(rhs: F16): Boolean {
    fun isNan(x: F16): Boolean = x.isNaN()
    return if (isNan(this) && isNan(rhs)) {
        true
    } else {
        this.toBits() == rhs.toBits()
    }
}

public fun F16.isNanFloat(): Boolean = this.isNaN()

public fun F16.isInfiniteFloat(): Boolean = this.isInfinite()

public fun F16.isSignNegativeFloat(): Boolean = this.isSignNegative()

public fun floatFromBitsF16(a: UShort): F16 = F16.fromBits(a)

public fun F16.absFloat(): F16 {
    F16.fromFloat(kmFabsf32(this.toFloat()))
    return fabs(this)
}

public fun F16.copysignFloat(other: F16): F16 {
    F16.fromFloat(kmCopysignf32(this.toFloat(), other.toFloat()))
    return io.github.kotlinmania.rustlang.libm.math.generic.copysign(this, other)
}

public fun F16.fma(y: F16, z: F16): F16 {
    floatFmaWithLayout(this, y, z, PrimitiveLayouts.F16_LAYOUT)
    return fmaf16(this, y, z)
}

public fun F16.floor(): F16 {
    floatUnaryWithLayout("floor", this, PrimitiveLayouts.F16_LAYOUT)
    return floorf16(this)
}

public fun F16.roundeven(): F16 {
    floatUnaryWithLayout("round_ties_even", this, PrimitiveLayouts.F16_LAYOUT)
    return roundevenf16(this)
}

public fun floatNormalizeF16(significand: UShort): Pair<Int, UShort> {
    val shift = (significand.leadingZeros() - (F16.EXP_BITS.toUInt()))
    return Pair((1 - (shift.toInt())), (significand.toUInt() shl (shift.toInt())).toUShort())
}

public val ZERO1: kotlin.Float = 0.0f

public val NEG_ZERO1: kotlin.Float = -0.0f

public val ONE1: kotlin.Float = 1.0f

public val NEG_ONE1: kotlin.Float = -1.0f

public val INFINITY1: kotlin.Float = kotlin.Float.POSITIVE_INFINITY

public val NEG_INFINITY1: kotlin.Float = kotlin.Float.NEGATIVE_INFINITY

public val MAX1: kotlin.Float = -(-kotlin.Float.MAX_VALUE)

public val MIN1: kotlin.Float = f32FromBits(kotlin.Float.Int.MAX and (1u shl kotlin.Float.SIG_BITS.toInt()).inv())

public val NAN1: kotlin.Float = f32FromBits(if (MIPS_NAN) {
    kotlin.Float.EXP_MASK or (kotlin.Float.SIG_TOP_BIT - 1u)
} else {
    kotlin.Float.EXP_MASK or kotlin.Float.SIG_TOP_BIT
})

public val SNAN1: kotlin.Float = f32FromBits(if (MIPS_NAN) {
    kotlin.Float.EXP_MASK or kotlin.Float.SIG_MASK
} else {
    kotlin.Float.EXP_MASK or (kotlin.Float.SIG_TOP_BIT shr 1)
})

public val QSNAN1: kotlin.Float = if (MIPS_NAN) {
    kotlin.Float.NAN
} else {
    f32FromBits(f32ToBits(kotlin.Float.SNAN) or kotlin.Float.SIG_TOP_BIT)
}

public val NEG_NAN1: kotlin.Float = f32FromBits(f32ToBits(kotlin.Float.NaN) or kotlin.Float.SIGN_MASK)

public val NEG_SNAN1: kotlin.Float = f32FromBits(f32ToBits(kotlin.Float.SNAN) or kotlin.Float.SIGN_MASK)

public val NEG_QSNAN1: kotlin.Float = f32FromBits(f32ToBits(kotlin.Float.QSNAN) or kotlin.Float.SIGN_MASK)

public val EPSILON1: kotlin.Float = 1.1920929E-7f

public val MIN_POSITIVE_NORMAL1: kotlin.Float = f32FromBits(1u shl kotlin.Float.SIG_BITS.toInt())

public val MIN_POSITIVE_SUBNORMAL1: kotlin.Float = f32FromBits(1u)

public val PI1: kotlin.Float = 3.1415927f

public val NEG_PI1: kotlin.Float = -kotlin.Float.PI

public val FRAC_PI_21: kotlin.Float = 1.5707964f

public val BITS1: UInt = 32u

public val SIG_BITS1: UInt = 23u

public val SIGN_MASK1: UInt = 1u shl (kotlin.Float.BITS - 1).toInt()

public val SIG_MASK1: UInt = (1u shl kotlin.Float.SIG_BITS.toInt()) - 1u

public val EXP_MASK1: UInt = (kotlin.Float.SIGN_MASK or kotlin.Float.SIG_MASK).inv()

public val IMPLICIT_BIT1: UInt = 1u shl kotlin.Float.SIG_BITS.toInt()

public val SIG_TOP_BIT1: UInt = kotlin.Float.IMPLICIT_BIT shr 1

public fun kotlin.Float.toBitsFloat(): UInt = this.toRawBits().toUInt()

public fun kotlin.Float.eqRepr(rhs: kotlin.Float): Boolean {
    fun isNan(x: kotlin.Float): Boolean = x.isNaN()
    return if (isNan(this) && isNan(rhs)) {
        true
    } else {
        this.toRawBits().toUInt() == rhs.toRawBits().toUInt()
    }
}

public fun kotlin.Float.isNanFloat(): Boolean = this.isNaN()

public fun kotlin.Float.isInfiniteFloat(): Boolean = this.isInfinite()

public fun kotlin.Float.isSignNegativeFloat(): Boolean = this.isSignNegative()

public fun floatFromBitsFloat(a: UInt): kotlin.Float = kotlin.Float.fromBits(a)

public fun kotlin.Float.absFloat(): kotlin.Float {
    kmFabsf32(this)
    return fabs(this)
}

public fun kotlin.Float.copysignFloat(other: kotlin.Float): kotlin.Float {
    kmCopysignf32(this, other)
    return io.github.kotlinmania.rustlang.libm.math.generic.copysign(this, other)
}

public fun kotlin.Float.fma(y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    kmFmaf32(this, y, z)
    return fmaf(this, y, z)
}

public fun kotlin.Float.floor(): kotlin.Float {
    kmFloorf32(this)
    return floorf(this)
}

public fun kotlin.Float.roundeven(): kotlin.Float {
    kmRoundTiesEvenf32(this)
    return roundevenf(this)
}

public fun floatNormalizeFloat(significand: UInt): Pair<Int, UInt> {
    val shift = (significand.leadingZeros() - (kotlin.Float.EXP_BITS.toUInt()))
    return Pair((1 - (shift.toInt())), significand shl shift.toInt())
}

public val ZERO2: Double = 0.0

public val NEG_ZERO2: Double = -0.0

public val ONE2: Double = 1.0

public val NEG_ONE2: Double = -1.0

public val INFINITY2: Double = Double.POSITIVE_INFINITY

public val NEG_INFINITY2: Double = Double.NEGATIVE_INFINITY

public val MAX2: Double = -(-Double.MAX_VALUE)

public val MIN2: Double = f64FromBits(Double.Int.MAX and (1uL shl Double.SIG_BITS.toInt()).inv())

public val NAN2: Double = f64FromBits(if (MIPS_NAN) {
    Double.EXP_MASK or (Double.SIG_TOP_BIT - 1uL)
} else {
    Double.EXP_MASK or Double.SIG_TOP_BIT
})

public val SNAN2: Double = f64FromBits(if (MIPS_NAN) {
    Double.EXP_MASK or Double.SIG_MASK
} else {
    Double.EXP_MASK or (Double.SIG_TOP_BIT shr 1)
})

public val QSNAN2: Double = if (MIPS_NAN) {
    Double.NAN
} else {
    f64FromBits(f64ToBits(Double.SNAN) or Double.SIG_TOP_BIT)
}

public val NEG_NAN2: Double = f64FromBits(f64ToBits(Double.NaN) or Double.SIGN_MASK)

public val NEG_SNAN2: Double = f64FromBits(f64ToBits(Double.SNAN) or Double.SIGN_MASK)

public val NEG_QSNAN2: Double = f64FromBits(f64ToBits(Double.QSNAN) or Double.SIGN_MASK)

public val EPSILON2: Double = 2.220446049250313E-16

public val MIN_POSITIVE_NORMAL2: Double = f64FromBits(1uL shl Double.SIG_BITS.toInt())

public val MIN_POSITIVE_SUBNORMAL2: Double = f64FromBits(1uL)

public val PI2: Double = kotlin.math.PI

public val NEG_PI2: Double = -Double.PI

public val FRAC_PI_22: Double = 1.5707963267948966

public val BITS2: UInt = 64u

public val SIG_BITS2: UInt = 52u

public val SIGN_MASK2: ULong = 1uL shl (Double.BITS - 1).toInt()

public val SIG_MASK2: ULong = (1uL shl Double.SIG_BITS.toInt()) - 1uL

public val EXP_MASK2: ULong = (Double.SIGN_MASK or Double.SIG_MASK).inv()

public val IMPLICIT_BIT2: ULong = 1uL shl Double.SIG_BITS.toInt()

public val SIG_TOP_BIT2: ULong = Double.IMPLICIT_BIT shr 1

public fun Double.toBitsFloat(): ULong = this.toRawBits().toULong()

public fun Double.eqRepr(rhs: Double): Boolean {
    fun isNan(x: Double): Boolean = x.isNaN()
    return if (isNan(this) && isNan(rhs)) {
        true
    } else {
        this.toRawBits().toULong() == rhs.toRawBits().toULong()
    }
}

public fun Double.isNanFloat(): Boolean = this.isNaN()

public fun Double.isInfiniteFloat(): Boolean = this.isInfinite()

public fun Double.isSignNegativeFloat(): Boolean = this.isSignNegative()

public fun floatFromBitsDouble(a: ULong): Double = Double.fromBits(a)

public fun Double.absFloat(): Double {
    kmFabsf64(this)
    return fabs(this)
}

public fun Double.copysignFloat(other: Double): Double {
    kmCopysignf64(this, other)
    return io.github.kotlinmania.rustlang.libm.math.generic.copysign(this, other)
}

public fun Double.fma(y: Double, z: Double): Double {
    kmFmaf64(this, y, z)
    return fma(this, y, z)
}

public fun Double.floor(): Double {
    kmFloorf64(this)
    return floor(this)
}

public fun Double.roundeven(): Double {
    kmRoundTiesEvenf64(this)
    return roundeven(this)
}

public fun floatNormalizeDouble(significand: ULong): Pair<Int, ULong> {
    val shift = (significand.leadingZeros() - (Double.EXP_BITS.toUInt()))
    return Pair((1 - (shift.toInt())), significand shl shift.toInt())
}

public val ZERO3: F128 = F128.fromDouble(0.0)

public val NEG_ZERO3: F128 = -F128.fromDouble(0.0)

public val ONE3: F128 = F128.fromDouble(1.0)

public val NEG_ONE3: F128 = -F128.fromDouble(1.0)

public val INFINITY3: F128 = F128.INFINITY

public val NEG_INFINITY3: F128 = F128.NEG_INFINITY

public val MAX3: F128 = -F128.MIN

public val MIN3: F128 = F128.fromBits(F128.Int.MAX and (U128(0x0uL, 0x1uL) shl F128.SIG_BITS.toInt()).inv())

public val NAN3: F128 = F128.fromBits(if (MIPS_NAN) {
    F128.EXP_MASK or (F128.SIG_TOP_BIT - U128(0x0uL, 0x1uL))
} else {
    F128.EXP_MASK or F128.SIG_TOP_BIT
})

public val SNAN3: F128 = F128.fromBits(if (MIPS_NAN) {
    F128.EXP_MASK or F128.SIG_MASK
} else {
    F128.EXP_MASK or (F128.SIG_TOP_BIT shr 1)
})

public val QSNAN3: F128 = if (MIPS_NAN) {
    F128.NAN
} else {
    F128.fromBits(F128.SNAN.toBits() or F128.SIG_TOP_BIT)
}

public val NEG_NAN3: F128 = F128.fromBits(F128.NAN.toBits() or F128.SIGN_MASK)

public val NEG_SNAN3: F128 = F128.fromBits(F128.SNAN.toBits() or F128.SIGN_MASK)

public val NEG_QSNAN3: F128 = F128.fromBits(F128.QSNAN.toBits() or F128.SIGN_MASK)

public val EPSILON3: F128 = F128.EPSILON

public val MIN_POSITIVE_NORMAL3: F128 = F128.fromBits(U128(0x0uL, 0x1uL) shl F128.SIG_BITS.toInt())

public val MIN_POSITIVE_SUBNORMAL3: F128 = F128.fromBits(U128(0x0uL, 0x1uL))

public val PI3: F128 = F128.fromDouble(kotlin.math.PI)

public val NEG_PI3: F128 = -F128.PI

public val FRAC_PI_23: F128 = F128.fromDouble(1.5707963267948966)

public val BITS3: UInt = 128u

public val SIG_BITS3: UInt = 112u

public val SIGN_MASK3: U128 = U128(0x0uL, 0x1uL) shl (F128.BITS - 1).toInt()

public val SIG_MASK3: U128 = (U128(0x0uL, 0x1uL) shl F128.SIG_BITS.toInt()) - U128(0x0uL, 0x1uL)

public val EXP_MASK3: U128 = (F128.SIGN_MASK or F128.SIG_MASK).inv()

public val IMPLICIT_BIT3: U128 = U128(0x0uL, 0x1uL) shl F128.SIG_BITS.toInt()

public val SIG_TOP_BIT3: U128 = F128.IMPLICIT_BIT shr 1

public fun F128.toBitsFloat(): U128 = this.toBits()

public fun F128.eqRepr(rhs: F128): Boolean {
    fun isNan(x: F128): Boolean = x.isNaN()
    return if (isNan(this) && isNan(rhs)) {
        true
    } else {
        this.toBits() == rhs.toBits()
    }
}

public fun F128.isNanFloat(): Boolean = this.isNaN()

public fun F128.isInfiniteFloat(): Boolean = this.isInfinite()

public fun F128.isSignNegativeFloat(): Boolean = this.isSignNegative()

public fun floatFromBitsF128(a: U128): F128 = F128.fromBits(a)

public fun F128.absFloat(): F128 {
    F128.fromDouble(kmFabsf64(this.toDouble()))
    return fabs(this)
}

public fun F128.copysignFloat(other: F128): F128 {
    F128.fromDouble(kmCopysignf64(this.toDouble(), other.toDouble()))
    return io.github.kotlinmania.rustlang.libm.math.generic.copysign(this, other)
}

public fun F128.fma(y: F128, z: F128): F128 {
    floatFmaWithLayout(this, y, z, PrimitiveLayouts.F128_LAYOUT)
    return fmaf128(this, y, z)
}

public fun F128.floor(): F128 {
    floatUnaryWithLayout("floor", this, PrimitiveLayouts.F128_LAYOUT)
    return floorf128(this)
}

public fun F128.roundeven(): F128 {
    floatUnaryWithLayout("round_ties_even", this, PrimitiveLayouts.F128_LAYOUT)
    return roundevenf128(this)
}

public fun floatNormalizeF128(significand: U128): Pair<Int, U128> {
    val shift = (significand.leadingZeros() - (F128.EXP_BITS.toUInt()))
    return Pair((1 - (shift.toInt())), significand shl shift.toInt())
}

// Note(msrv): vendor some things that are not const stable at our MSRV
// lint appears in newer versions of Rust

/**
 * `Float.fromBits`
 */
public fun f32FromBits(bits: UInt): kotlin.Float {
    // SAFETY: POD cast with no preconditions
    return transmuteWithLayouts(bits, PrimitiveLayouts.U32, PrimitiveLayouts.F32)
}

// workaround for false positive RUST-144060
// lint appears in newer versions of Rust

/**
 * `Float.toBits`
 */
public fun f32ToBits(x: kotlin.Float): UInt {
    // SAFETY: POD cast with no preconditions
    return transmuteWithLayouts(x, PrimitiveLayouts.F32, PrimitiveLayouts.U32)
}

// lint appears in newer versions of Rust

/**
 * `Double.fromBits`
 */
public fun f64FromBits(bits: ULong): Double {
    // SAFETY: POD cast with no preconditions
    return transmuteWithLayouts(bits, PrimitiveLayouts.U64, PrimitiveLayouts.F64)
}

// workaround for false positive RUST-144060
// lint appears in newer versions of Rust

/**
 * `Double.toBits`
 */
public fun f64ToBits(x: Double): ULong {
    // SAFETY: POD cast with no preconditions
    return transmuteWithLayouts(x, PrimitiveLayouts.F64, PrimitiveLayouts.U64)
}

/**
 * Trait for floats that are wider than a `NFloat` type.
 */
public interface WideFloat<T, out H> : Float<T, Any?, Any?> {
    /**
     * Narrow the float type.
     */
    public fun narrow(): H
}

/**
 * Trait for floats narrower than a `WFloat` type.
 */
public interface NarrowFloat<T, out D> : Float<T, Any?, Any?> {
    /**
     * Widen the float type.
     */
    public fun widen(): D
}

public fun Double.narrow(): kotlin.Float = this.toFloat()

public fun kotlin.Float.narrow(): F16 = F16.fromFloat(this)

public fun F128.narrow(): Double = this.toDouble()

public fun kotlin.Float.widen(): Double = this.toDouble()

public fun F16.widen(): kotlin.Float = this.toFloat()

public fun Double.widen(): F128 = F128.fromDouble(this)
