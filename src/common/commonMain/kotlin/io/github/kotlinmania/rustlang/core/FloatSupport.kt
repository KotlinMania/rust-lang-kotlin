@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core

/** IEEE-754 binary16 half-precision floating-point value. */
public class F16(
    public val bits: UShort = 0u.toUShort(),
) : Comparable<F16> {
    public fun toBits(): UShort = bits

    public fun toFloat(): Float {
        val b = bits.toInt()
        val sign = (b and 0x8000) shl 16
        val exp = (b ushr 10) and 0x1F
        val mant = b and 0x03FF
        return when (exp) {
            0 -> {
                if (mant == 0) {
                    Float.fromBits(sign)
                } else {
                    var m = mant
                    var e = 127 - 14
                    while ((m and 0x0400) == 0) {
                        m = m shl 1
                        e--
                    }
                    val normMant = (m and 0x03FF) shl 13
                    Float.fromBits(sign or (e shl 23) or normMant)
                }
            }
            31 -> {
                val nanPayload = if (mant != 0) (mant shl 13) or 0x0040_0000 else 0
                Float.fromBits(sign or 0x7F80_0000 or nanPayload)
            }
            else -> Float.fromBits(sign or ((exp + 112) shl 23) or (mant shl 13))
        }
    }

    public fun toDouble(): Double = toFloat().toDouble()
    public fun toByte(): Byte = toFloat().toInt().toByte()
    public fun toShort(): Short = toFloat().toInt().toShort()
    public fun toInt(): Int = toFloat().toInt()
    public fun toLong(): Long = toFloat().toLong()
    public fun toUByte(): UByte = toFloat().toInt().toUByte()
    public fun toUShort(): UShort = toFloat().toInt().toUShort()
    public fun toUInt(): UInt = toFloat().toUInt()
    public fun toULong(): ULong = toFloat().toULong()

    public fun toBeBytes(): UByteArray =
        ubyteArrayOf((bits.toInt() ushr 8).toUByte(), bits.toUByte())

    public fun toLeBytes(): UByteArray =
        ubyteArrayOf(bits.toUByte(), (bits.toInt() ushr 8).toUByte())

    public fun toNeBytes(): UByteArray = toLeBytes()

    public fun isNaN(): Boolean = (bits.toInt() and 0x7FFF) > 0x7C00
    public fun isInfinite(): Boolean = (bits.toInt() and 0x7FFF) == 0x7C00
    public fun isFinite(): Boolean = (bits.toInt() and 0x7C00) != 0x7C00
    public fun isSignPositive(): Boolean = (bits.toInt() and 0x8000) == 0
    public fun isSignNegative(): Boolean = (bits.toInt() and 0x8000) != 0

    public operator fun unaryPlus(): F16 = this
    public operator fun unaryMinus(): F16 = F16((bits.toInt() xor 0x8000).toUShort())
    public operator fun plus(other: F16): F16 = fromFloat(toFloat() + other.toFloat())
    public operator fun minus(other: F16): F16 = fromFloat(toFloat() - other.toFloat())
    public operator fun times(other: F16): F16 = fromFloat(toFloat() * other.toFloat())
    public operator fun div(other: F16): F16 = fromFloat(toFloat() / other.toFloat())
    public operator fun rem(other: F16): F16 = fromFloat(toFloat() % other.toFloat())

    public override operator fun compareTo(other: F16): Int = toFloat().compareTo(other.toFloat())

    override fun equals(other: Any?): Boolean = other is F16 && bits == other.bits
    override fun hashCode(): Int = bits.hashCode()
    override fun toString(): String = toFloat().toString()

    public companion object {
        public val ZERO: F16 = F16(0x0000u.toUShort())
        public val NEG_ZERO: F16 = F16(0x8000u.toUShort())
        public val ONE: F16 = F16(0x3C00u.toUShort())
        public val NAN: F16 = F16(0x7E00u.toUShort())
        public val INFINITY: F16 = F16(0x7C00u.toUShort())
        public val NEG_INFINITY: F16 = F16(0xFC00u.toUShort())
        public val MIN: F16 = F16(0xFBFFu.toUShort())
        public val MAX: F16 = F16(0x7BFFu.toUShort())
        public val MIN_VALUE: F16 = MIN
        public val MAX_VALUE: F16 = MAX
        public val MIN_POSITIVE: F16 = F16(0x0400u.toUShort())
        public val EPSILON: F16 = F16(0x1400u.toUShort())
        public val RADIX: UInt = 2u
        public val MANTISSA_DIGITS: UInt = 11u
        public val DIGITS: UInt = 3u
        public val MIN_EXP: Int = -13
        public val MAX_EXP: Int = 16
        public val MIN_10_EXP: Int = -4
        public val MAX_10_EXP: Int = 4
        public val BITS: UInt = 16u
        public val SIGN_MASK: UShort = 0x8000u.toUShort()
        public val EXP_MASK: UShort = 0x7C00u.toUShort()
        public val SIG_MASK: UShort = 0x03FFu.toUShort()
        public val IMPLICIT_BIT: UShort = 0x0400u.toUShort()
        public val SIG_TOP_BIT: UShort = 0x0200u.toUShort()
        public val SIG_BITS: UInt = 10u
        public val EXP_BITS: UInt = 5u
        public val EXP_SAT: UInt = 31u
        public val EXP_BIAS: UInt = 15u
        public val EXP_MAX: Int = 15
        public val EXP_MIN: Int = -14
        public val EXP_MIN_SUBNORM: Int = -24
        public val TINY_BITS: UShort = 1u.toUShort()
        public val MIN_POSITIVE_SUBNORMAL: F16 = F16(1u.toUShort())

        public fun fromBits(bits: UShort): F16 = F16(bits)
        public fun toBits(value: F16): UShort = value.toBits()

        public fun fromFloat(v: Float): F16 {
            val fb = v.toRawBits()
            val sign = (fb ushr 16) and 0x8000
            val rawExp = (fb ushr 23) and 0xFF
            val mant = fb and 0x007F_FFFF
            if (rawExp == 0xFF) {
                val payload = if (mant != 0) ((mant ushr 13) or 0x0200) else 0
                return F16((sign or 0x7C00 or payload).toUShort())
            }
            val exp = rawExp - 112
            if (exp >= 31) {
                return F16((sign or 0x7C00).toUShort())
            }
            if (exp <= 0) {
                if (exp < -10) return F16(sign.toUShort())
                val m = mant or 0x0080_0000
                val shift = 14 - exp
                val halfBit = 1 shl (shift - 1)
                val tailMask = (1 shl shift) - 1
                val low = m and tailMask
                var rounded = m ushr shift
                if (low > halfBit || (low == halfBit && (rounded and 1) != 0)) {
                    rounded++
                }
                return F16((sign or rounded).toUShort())
            }
            val roundBits = mant and 0x1FFF
            var halfMant = mant ushr 13
            var halfExp = exp
            if (roundBits > 0x1000 || (roundBits == 0x1000 && (halfMant and 1) != 0)) {
                halfMant++
                if ((halfMant and 0x0400) != 0) {
                    halfMant = 0
                    halfExp++
                }
            }
            if (halfExp >= 31) {
                return F16((sign or 0x7C00).toUShort())
            }
            return F16((sign or (halfExp shl 10) or halfMant).toUShort())
        }

        public fun fromDouble(v: Double): F16 = fromFloat(v.toFloat())
        public fun fromByte(v: Byte): F16 = fromFloat(v.toFloat())
        public fun fromShort(v: Short): F16 = fromFloat(v.toFloat())
        public fun fromInt(v: Int): F16 = fromFloat(v.toFloat())
        public fun fromLong(v: Long): F16 = fromFloat(v.toFloat())
        public fun fromUByte(v: UByte): F16 = fromFloat(v.toFloat())
        public fun fromUShort(v: UShort): F16 = fromFloat(v.toFloat())
        public fun fromUInt(v: UInt): F16 = fromFloat(v.toFloat())
        public fun fromULong(v: ULong): F16 = fromFloat(v.toFloat())
        public fun fromF128(v: F128): F16 = fromDouble(v.toDouble())

        public fun fromBeBytes(bytes: UByteArray): F16 =
            F16((((bytes[0].toInt() and 0xFF) shl 8) or (bytes[1].toInt() and 0xFF)).toUShort())

        public fun fromBeBytes(bytes: List<UByte>): F16 =
            F16((((bytes[0].toInt() and 0xFF) shl 8) or (bytes[1].toInt() and 0xFF)).toUShort())

        public fun fromLeBytes(bytes: UByteArray): F16 =
            F16((((bytes[1].toInt() and 0xFF) shl 8) or (bytes[0].toInt() and 0xFF)).toUShort())

        public fun fromLeBytes(bytes: List<UByte>): F16 =
            F16((((bytes[1].toInt() and 0xFF) shl 8) or (bytes[0].toInt() and 0xFF)).toUShort())
    }
}

/** IEEE-754 binary128 quadruple-precision floating-point value. */
public class F128(
    public val high: ULong = 0uL,
    public val low: ULong = 0uL,
) : Comparable<F128> {
    public fun toBits(): U128 = U128(high, low)

    public fun toDouble(): Double {
        val signBit = high and 0x8000_0000_0000_0000uL
        val rawExp = ((high shr 48) and 0x7FFFuL).toInt()
        val highMant = high and 0x0000_FFFF_FFFF_FFFFuL
        if (rawExp == 0 && highMant == 0uL && low == 0uL) {
            return Double.fromBits(signBit.toLong())
        }
        if (rawExp == 0x7FFF) {
            val isNan = highMant != 0uL || low != 0uL
            val dBits =
                if (isNan) {
                    signBit or 0x7FF8_0000_0000_0000uL or (highMant and 0x0007_FFFF_FFFF_FFFFuL)
                } else {
                    signBit or 0x7FF0_0000_0000_0000uL
                }
            return Double.fromBits(dBits.toLong())
        }
        val dExp = rawExp - 16383 + 1023
        if (dExp >= 0x7FF) {
            return Double.fromBits((signBit or 0x7FF0_0000_0000_0000uL).toLong())
        }
        val dMant52 = (highMant shl 4) or (low shr 60)
        if (dExp <= 0) {
            if (dExp < -52) {
                return Double.fromBits(signBit.toLong())
            }
            val fullMant = 0x0010_0000_0000_0000uL or dMant52
            val subMant = fullMant shr (1 - dExp)
            return Double.fromBits((signBit or subMant).toLong())
        }
        return Double.fromBits((signBit or (dExp.toULong() shl 52) or (dMant52 and 0x000F_FFFF_FFFF_FFFFuL)).toLong())
    }

    public fun toFloat(): Float = toDouble().toFloat()
    public fun toByte(): Byte = toDouble().toInt().toByte()
    public fun toShort(): Short = toDouble().toInt().toShort()
    public fun toInt(): Int = toDouble().toInt()
    public fun toLong(): Long = toDouble().toLong()
    public fun toUByte(): UByte = toDouble().toInt().toUByte()
    public fun toUShort(): UShort = toDouble().toInt().toUShort()
    public fun toUInt(): UInt = toDouble().toUInt()
    public fun toULong(): ULong = toDouble().toULong()
    public fun toF16(): F16 = F16.fromDouble(toDouble())

    public fun toBeBytes(): UByteArray = U128(high, low).toBeBytes()
    public fun toLeBytes(): UByteArray = U128(high, low).toLeBytes()
    public fun toNeBytes(): UByteArray = U128(high, low).toNeBytes()

    public fun isNaN(): Boolean =
        ((high shr 48) and 0x7FFFuL) == 0x7FFFuL &&
            ((high and 0x0000_FFFF_FFFF_FFFFuL) != 0uL || low != 0uL)

    public fun isInfinite(): Boolean =
        ((high shr 48) and 0x7FFFuL) == 0x7FFFuL &&
            (high and 0x0000_FFFF_FFFF_FFFFuL) == 0uL &&
            low == 0uL

    public fun isFinite(): Boolean = ((high shr 48) and 0x7FFFuL) != 0x7FFFuL
    public fun isSignPositive(): Boolean = (high and 0x8000_0000_0000_0000uL) == 0uL
    public fun isSignNegative(): Boolean = (high and 0x8000_0000_0000_0000uL) != 0uL

    public operator fun unaryPlus(): F128 = this
    public operator fun unaryMinus(): F128 = F128(high xor 0x8000_0000_0000_0000uL, low)
    public operator fun plus(other: F128): F128 = fromDouble(toDouble() + other.toDouble())
    public operator fun minus(other: F128): F128 = fromDouble(toDouble() - other.toDouble())
    public operator fun times(other: F128): F128 = fromDouble(toDouble() * other.toDouble())
    public operator fun div(other: F128): F128 = fromDouble(toDouble() / other.toDouble())
    public operator fun rem(other: F128): F128 = fromDouble(toDouble() % other.toDouble())

    public override operator fun compareTo(other: F128): Int = toDouble().compareTo(other.toDouble())

    override fun equals(other: Any?): Boolean = other is F128 && high == other.high && low == other.low
    override fun hashCode(): Int = 31 * high.hashCode() + low.hashCode()
    override fun toString(): String = toDouble().toString()

    public companion object {
        public val ZERO: F128 = F128(0uL, 0uL)
        public val NEG_ZERO: F128 = F128(0x8000_0000_0000_0000uL, 0uL)
        public val ONE: F128 = F128(0x3FFF_0000_0000_0000uL, 0uL)
        public val NAN: F128 = F128(0x7FFF_8000_0000_0000uL, 0uL)
        public val INFINITY: F128 = F128(0x7FFF_0000_0000_0000uL, 0uL)
        public val NEG_INFINITY: F128 = F128(0xFFFF_0000_0000_0000uL, 0uL)
        public val MIN: F128 = F128(0xFFFE_FFFF_FFFF_FFFFuL, ULong.MAX_VALUE)
        public val MAX: F128 = F128(0x7FFE_FFFF_FFFF_FFFFuL, ULong.MAX_VALUE)
        public val MIN_VALUE: F128 = MIN
        public val MAX_VALUE: F128 = MAX
        public val MIN_POSITIVE: F128 = F128(0x0001_0000_0000_0000uL, 0uL)
        public val EPSILON: F128 = F128(0x3F8F_0000_0000_0000uL, 0uL)
        public val RADIX: UInt = 2u
        public val MANTISSA_DIGITS: UInt = 113u
        public val DIGITS: UInt = 33u
        public val MIN_EXP: Int = -16381
        public val MAX_EXP: Int = 16384
        public val MIN_10_EXP: Int = -4931
        public val MAX_10_EXP: Int = 4932
        public val BITS: UInt = 128u
        public val SIGN_MASK: U128 = U128(0x8000_0000_0000_0000uL, 0uL)
        public val EXP_MASK: U128 = U128(0x7FFF_0000_0000_0000uL, 0uL)
        public val SIG_MASK: U128 = U128(0x0000_FFFF_FFFF_FFFFuL, 0xFFFF_FFFF_FFFF_FFFFuL)
        public val IMPLICIT_BIT: U128 = U128(0x0001_0000_0000_0000uL, 0uL)
        public val SIG_TOP_BIT: U128 = U128(0x0000_8000_0000_0000uL, 0uL)
        public val SIG_BITS: UInt = 112u
        public val EXP_BITS: UInt = 15u
        public val EXP_SAT: UInt = 32767u
        public val EXP_BIAS: UInt = 16383u
        public val EXP_MAX: Int = 16383
        public val EXP_MIN: Int = -16382
        public val EXP_MIN_SUBNORM: Int = -16494
        public val TINY_BITS: U128 = U128.ONE
        public val MIN_POSITIVE_SUBNORMAL: F128 = F128(0uL, 1uL)

        public fun fromBits(bits: U128): F128 = F128(bits.high, bits.low)
        public fun fromBits(high: ULong, low: ULong): F128 = F128(high, low)
        public fun toBits(value: F128): U128 = value.toBits()

        public fun fromDouble(v: Double): F128 {
            val db = v.toRawBits().toULong()
            val signBit = db and 0x8000_0000_0000_0000uL
            val rawExp = ((db shr 52) and 0x7FFuL).toInt()
            val dMant = db and 0x000F_FFFF_FFFF_FFFFuL
            if (rawExp == 0 && dMant == 0uL) {
                return F128(signBit, 0uL)
            }
            if (rawExp == 0x7FF) {
                return if (dMant != 0uL) {
                    F128(signBit or 0x7FFF_8000_0000_0000uL or (dMant shr 4), (dMant and 0xFuL) shl 60)
                } else {
                    F128(signBit or 0x7FFF_0000_0000_0000uL, 0uL)
                }
            }
            var normExp = rawExp - 1023 + 16383
            var normMant = dMant
            if (rawExp == 0) {
                normExp = 16383 - 1022
                while ((normMant and 0x0010_0000_0000_0000uL) == 0uL) {
                    normMant = normMant shl 1
                    normExp--
                }
                normMant = normMant and 0x000F_FFFF_FFFF_FFFFuL
            }
            return F128(
                signBit or (normExp.toULong() shl 48) or (normMant shr 4),
                (normMant and 0xFuL) shl 60,
            )
        }

        public fun fromFloat(v: Float): F128 = fromDouble(v.toDouble())
        public fun fromByte(v: Byte): F128 = fromDouble(v.toDouble())
        public fun fromShort(v: Short): F128 = fromDouble(v.toDouble())
        public fun fromInt(v: Int): F128 = fromDouble(v.toDouble())
        public fun fromLong(v: Long): F128 = fromDouble(v.toDouble())
        public fun fromUByte(v: UByte): F128 = fromDouble(v.toDouble())
        public fun fromUShort(v: UShort): F128 = fromDouble(v.toDouble())
        public fun fromUInt(v: UInt): F128 = fromDouble(v.toDouble())
        public fun fromULong(v: ULong): F128 = fromDouble(v.toDouble())
        public fun fromF16(v: F16): F128 = fromDouble(v.toDouble())

        public fun fromBeBytes(bytes: UByteArray): F128 = fromBits(U128.fromBeBytes(bytes))
        public fun fromBeBytes(bytes: List<UByte>): F128 = fromBits(U128.fromBeBytes(bytes))
        public fun fromLeBytes(bytes: UByteArray): F128 = fromBits(U128.fromLeBytes(bytes))
        public fun fromLeBytes(bytes: List<UByte>): F128 = fromBits(U128.fromLeBytes(bytes))
    }
}
