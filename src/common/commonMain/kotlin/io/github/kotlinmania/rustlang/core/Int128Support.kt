// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core

import io.github.kotlinmania.rustlang.core.intrinsics.kmI128DivRem
import io.github.kotlinmania.rustlang.core.intrinsics.kmI128Shr
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Add
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128DivRem
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Mul
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Shl
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Shr
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Sub

public class U128(
    public val high: ULong = 0uL,
    public val low: ULong = 0uL,
) {
    public infix fun shr(bitCount: Int): U128 {
        val out = kmU128Shr(low, high, (bitCount and 127).toUInt())
        return U128(out.hi, out.lo)
    }

    public infix fun shl(bitCount: Int): U128 {
        val out = kmU128Shl(low, high, (bitCount and 127).toUInt())
        return U128(out.hi, out.lo)
    }

    public infix fun and(other: U128): U128 = U128(high and other.high, low and other.low)
    public infix fun and(mask: ULong): U128 = U128(0uL, low and mask)
    public infix fun and(mask: UInt): U128 = U128(0uL, low and mask.toULong())
    public infix fun and(mask: Long): U128 = and(fromLong(mask))
    public infix fun and(mask: Int): U128 = and(fromLong(mask.toLong()))

    public infix fun or(other: U128): U128 = U128(high or other.high, low or other.low)
    public infix fun or(mask: ULong): U128 = U128(high, low or mask)
    public infix fun or(mask: UInt): U128 = U128(high, low or mask.toULong())
    public infix fun or(mask: Long): U128 = or(fromLong(mask))
    public infix fun or(mask: Int): U128 = or(fromLong(mask.toLong()))

    public infix fun xor(other: U128): U128 = U128(high xor other.high, low xor other.low)
    public infix fun xor(mask: ULong): U128 = U128(high, low xor mask)
    public infix fun xor(mask: UInt): U128 = U128(high, low xor mask.toULong())
    public infix fun xor(mask: Long): U128 = xor(fromLong(mask))
    public infix fun xor(mask: Int): U128 = xor(fromLong(mask.toLong()))

    public fun inv(): U128 = U128(high.inv(), low.inv())

    public operator fun plus(other: U128): U128 {
        val out = kmU128Add(low, high, other.low, other.high)
        return U128(out.hi, out.lo)
    }

    public operator fun minus(other: U128): U128 {
        val out = kmU128Sub(low, high, other.low, other.high)
        return U128(out.hi, out.lo)
    }

    public operator fun times(other: U128): U128 {
        val out = kmU128Mul(low, high, other.low, other.high)
        return U128(out.hi, out.lo)
    }

    public operator fun div(other: U128): U128 = divRem(other).first
    public operator fun rem(other: U128): U128 = divRem(other).second

    public fun countOnes(): UInt = (high.countOneBits() + low.countOneBits()).toUInt()

    public fun pow(exp: UInt): U128 {
        var acc = ONE
        var b = this
        var e = exp
        while (e > 0u) {
            if ((e and 1u) != 0u) acc *= b
            e = e shr 1
            if (e > 0u) b *= b
        }
        return acc
    }

    private fun divRem(divisor: U128): Pair<U128, U128> {
        if (divisor.high == 0uL && divisor.low == 0uL) throw ArithmeticException("/ by zero")
        val out = kmU128DivRem(low, high, divisor.low, divisor.high)
        return Pair(U128(out.qHi, out.qLo), U128(out.rHi, out.rLo))
    }

    public fun toByte(): Byte = low.toByte()
    public fun toUByte(): UByte = low.toUByte()
    public fun toShort(): Short = low.toShort()
    public fun toUShort(): UShort = low.toUShort()
    public fun toInt(): Int = low.toInt()
    public fun toUInt(): UInt = low.toUInt()
    public fun toLong(): Long = low.toLong()
    public fun toULong(): ULong = low
    public fun toFloat(): Float = high.toDouble().toFloat() * 18446744073709551616.0f + low.toDouble().toFloat()
    public fun toDouble(): Double = high.toDouble() * 18446744073709551616.0 + low.toDouble()
    public fun toU128(): U128 = this
    public fun toI128(): I128 = I128(high.toLong(), low)

    public fun toBeBytes(): UByteArray =
        ubyteArrayOf(
            (high shr 56).toUByte(),
            (high shr 48).toUByte(),
            (high shr 40).toUByte(),
            (high shr 32).toUByte(),
            (high shr 24).toUByte(),
            (high shr 16).toUByte(),
            (high shr 8).toUByte(),
            high.toUByte(),
            (low shr 56).toUByte(),
            (low shr 48).toUByte(),
            (low shr 40).toUByte(),
            (low shr 32).toUByte(),
            (low shr 24).toUByte(),
            (low shr 16).toUByte(),
            (low shr 8).toUByte(),
            low.toUByte(),
        )

    public fun toLeBytes(): UByteArray =
        ubyteArrayOf(
            low.toUByte(),
            (low shr 8).toUByte(),
            (low shr 16).toUByte(),
            (low shr 24).toUByte(),
            (low shr 32).toUByte(),
            (low shr 40).toUByte(),
            (low shr 48).toUByte(),
            (low shr 56).toUByte(),
            high.toUByte(),
            (high shr 8).toUByte(),
            (high shr 16).toUByte(),
            (high shr 24).toUByte(),
            (high shr 32).toUByte(),
            (high shr 40).toUByte(),
            (high shr 48).toUByte(),
            (high shr 56).toUByte(),
        )

    public fun toNeBytes(): UByteArray = toLeBytes()

    public operator fun compareTo(other: U128): Int {
        val hc = high.compareTo(other.high)
        return if (hc != 0) hc else low.compareTo(other.low)
    }

    override fun equals(other: Any?): Boolean =
        other is U128 && high == other.high && low == other.low

    override fun hashCode(): Int = 31 * high.hashCode() + low.hashCode()

    override fun toString(): String {
        if (high == 0uL) return low.toString()
        val ten = U128(0uL, 10uL)
        val sb = StringBuilder()
        var cur = this
        while (cur.high != 0uL || cur.low != 0uL) {
            val (q, r) = cur.divRem(ten)
            sb.append(('0'.code + r.low.toInt()).toChar())
            cur = q
        }
        return sb.reverse().toString()
    }

    public companion object {
        public val ZERO: U128 = U128(0uL, 0uL)
        public val ONE: U128 = U128(0uL, 1uL)
        public val MIN: U128 = ZERO
        public val MAX: U128 = U128(ULong.MAX_VALUE, ULong.MAX_VALUE)
        public val MIN_VALUE: U128 = MIN
        public val MAX_VALUE: U128 = MAX

        public fun fromULong(v: ULong): U128 = U128(0uL, v)
        public fun fromUInt(v: UInt): U128 = U128(0uL, v.toULong())
        public fun fromUShort(v: UShort): U128 = U128(0uL, v.toULong())
        public fun fromUByte(v: UByte): U128 = U128(0uL, v.toULong())
        public fun fromLong(v: Long): U128 = U128(if (v < 0L) ULong.MAX_VALUE else 0uL, v.toULong())
        public fun fromInt(v: Int): U128 = fromLong(v.toLong())
        public fun fromShort(v: Short): U128 = fromLong(v.toLong())
        public fun fromByte(v: Byte): U128 = fromLong(v.toLong())

        public fun fromBeBytes(bytes: UByteArray): U128 {
            var hi = 0uL
            var lo = 0uL
            for (i in 0 until 8) hi = (hi shl 8) or bytes[i].toULong()
            for (i in 8 until 16) lo = (lo shl 8) or bytes[i].toULong()
            return U128(hi, lo)
        }

        public fun fromBeBytes(bytes: List<UByte>): U128 {
            var hi = 0uL
            var lo = 0uL
            for (i in 0 until 8) hi = (hi shl 8) or bytes[i].toULong()
            for (i in 8 until 16) lo = (lo shl 8) or bytes[i].toULong()
            return U128(hi, lo)
        }

        public fun fromLeBytes(bytes: UByteArray): U128 {
            var hi = 0uL
            var lo = 0uL
            for (i in 7 downTo 0) lo = (lo shl 8) or bytes[i].toULong()
            for (i in 15 downTo 8) hi = (hi shl 8) or bytes[i].toULong()
            return U128(hi, lo)
        }

        public fun fromLeBytes(bytes: List<UByte>): U128 {
            var hi = 0uL
            var lo = 0uL
            for (i in 7 downTo 0) lo = (lo shl 8) or bytes[i].toULong()
            for (i in 15 downTo 8) hi = (hi shl 8) or bytes[i].toULong()
            return U128(hi, lo)
        }
    }
}

public class I128(
    public val high: Long = 0L,
    public val low: ULong = 0uL,
) {
    public infix fun shr(bitCount: Int): I128 {
        val out = kmI128Shr(low, high.toULong(), (bitCount and 127).toUInt())
        return I128(out.hi.toLong(), out.lo)
    }

    public infix fun shl(bitCount: Int): I128 {
        val out = kmU128Shl(low, high.toULong(), (bitCount and 127).toUInt())
        return I128(out.hi.toLong(), out.lo)
    }

    public infix fun and(other: I128): I128 = I128(high and other.high, low and other.low)
    public infix fun and(mask: Long): I128 = and(fromLong(mask))
    public infix fun and(mask: Int): I128 = and(fromLong(mask.toLong()))
    public infix fun and(mask: ULong): I128 = I128(0L, low and mask)
    public infix fun and(mask: UInt): I128 = I128(0L, low and mask.toULong())

    public infix fun or(other: I128): I128 = I128(high or other.high, low or other.low)
    public infix fun or(mask: Long): I128 = or(fromLong(mask))
    public infix fun or(mask: Int): I128 = or(fromLong(mask.toLong()))
    public infix fun or(mask: ULong): I128 = I128(high, low or mask)
    public infix fun or(mask: UInt): I128 = I128(high, low or mask.toULong())

    public infix fun xor(other: I128): I128 = I128(high xor other.high, low xor other.low)
    public infix fun xor(mask: Long): I128 = xor(fromLong(mask))
    public infix fun xor(mask: Int): I128 = xor(fromLong(mask.toLong()))
    public infix fun xor(mask: ULong): I128 = I128(high, low xor mask)
    public infix fun xor(mask: UInt): I128 = I128(high, low xor mask.toULong())

    public fun inv(): I128 = I128(high.inv(), low.inv())

    public operator fun unaryMinus(): I128 {
        val invLow = low.inv() + 1uL
        val carry = if (invLow == 0uL) 1L else 0L
        return I128(high.inv() + carry, invLow)
    }

    public operator fun plus(other: I128): I128 {
        val out = kmU128Add(low, high.toULong(), other.low, other.high.toULong())
        return I128(out.hi.toLong(), out.lo)
    }

    public operator fun minus(other: I128): I128 {
        val out = kmU128Sub(low, high.toULong(), other.low, other.high.toULong())
        return I128(out.hi.toLong(), out.lo)
    }

    public operator fun times(other: I128): I128 {
        val out = kmU128Mul(low, high.toULong(), other.low, other.high.toULong())
        return I128(out.hi.toLong(), out.lo)
    }

    public operator fun div(other: I128): I128 {
        if (other.high == 0L && other.low == 0uL) throw ArithmeticException("/ by zero")
        val out = kmI128DivRem(low, high.toULong(), other.low, other.high.toULong())
        return I128(out.qHi.toLong(), out.qLo)
    }

    public operator fun rem(other: I128): I128 {
        if (other.high == 0L && other.low == 0uL) throw ArithmeticException("/ by zero")
        val out = kmI128DivRem(low, high.toULong(), other.low, other.high.toULong())
        return I128(out.rHi.toLong(), out.rLo)
    }

    public fun toByte(): Byte = low.toByte()
    public fun toUByte(): UByte = low.toUByte()
    public fun toShort(): Short = low.toShort()
    public fun toUShort(): UShort = low.toUShort()
    public fun toInt(): Int = low.toInt()
    public fun toUInt(): UInt = low.toUInt()
    public fun toLong(): Long = low.toLong()
    public fun toULong(): ULong = low
    public fun toFloat(): Float = toDouble().toFloat()
    public fun toDouble(): Double =
        if (high < 0L) {
            val mag = -this
            -(U128(mag.high.toULong(), mag.low).toDouble())
        } else {
            U128(high.toULong(), low).toDouble()
        }
    public fun toU128(): U128 = U128(high.toULong(), low)
    public fun toI128(): I128 = this

    public fun toBeBytes(): UByteArray = U128(high.toULong(), low).toBeBytes()
    public fun toLeBytes(): UByteArray = U128(high.toULong(), low).toLeBytes()
    public fun toNeBytes(): UByteArray = U128(high.toULong(), low).toNeBytes()

    public fun countOnes(): UInt = toU128().countOnes()

    public fun pow(exp: UInt): I128 {
        var acc = ONE
        var b = this
        var e = exp
        while (e > 0u) {
            if ((e and 1u) != 0u) acc *= b
            e = e shr 1
            if (e > 0u) b *= b
        }
        return acc
    }

    public operator fun compareTo(other: I128): Int {
        val hc = high.compareTo(other.high)
        return if (hc != 0) hc else low.compareTo(other.low)
    }

    override fun equals(other: Any?): Boolean =
        other is I128 && high == other.high && low == other.low

    override fun hashCode(): Int = 31 * high.hashCode() + low.hashCode()

    override fun toString(): String {
        if (high >= 0L) return U128(high.toULong(), low).toString()
        val mag = -this
        return "-${U128(mag.high.toULong(), mag.low)}"
    }

    public companion object {
        public val ZERO: I128 = I128(0L, 0uL)
        public val ONE: I128 = I128(0L, 1uL)
        public val MIN: I128 = I128(Long.MIN_VALUE, 0uL)
        public val MAX: I128 = I128(Long.MAX_VALUE, ULong.MAX_VALUE)
        public val MIN_VALUE: I128 = MIN
        public val MAX_VALUE: I128 = MAX

        public fun fromLong(v: Long): I128 = I128(if (v < 0L) -1L else 0L, v.toULong())
        public fun fromInt(v: Int): I128 = fromLong(v.toLong())
        public fun fromShort(v: Short): I128 = fromLong(v.toLong())
        public fun fromByte(v: Byte): I128 = fromLong(v.toLong())
        public fun fromULong(v: ULong): I128 = I128(0L, v)
        public fun fromUInt(v: UInt): I128 = I128(0L, v.toULong())
        public fun fromUShort(v: UShort): I128 = I128(0L, v.toULong())
        public fun fromUByte(v: UByte): I128 = I128(0L, v.toULong())

        public fun fromBeBytes(bytes: UByteArray): I128 = U128.fromBeBytes(bytes).toI128()
        public fun fromBeBytes(bytes: List<UByte>): I128 = U128.fromBeBytes(bytes).toI128()
        public fun fromLeBytes(bytes: UByteArray): I128 = U128.fromLeBytes(bytes).toI128()
        public fun fromLeBytes(bytes: List<UByte>): I128 = U128.fromLeBytes(bytes).toI128()
    }
}
