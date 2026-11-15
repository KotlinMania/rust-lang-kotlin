@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmSmulhI64
import io.github.kotlinmania.rustlang.core.intrinsics.kmUmulhU64
import io.github.kotlinmania.rustlang.core.num.error.IntErrorKind
import io.github.kotlinmania.rustlang.core.num.error.ParseIntError
import io.github.kotlinmania.rustlang.core.result.Result

public data class OverflowingResult<T>(
    public val value: T,
    public val overflowed: Boolean,
)

private val I128_NEG_ONE: I128 = I128(-1L, ULong.MAX_VALUE)

// --- Checked arithmetic ---

public fun rustCheckedAdd(a: Byte, b: Byte): Byte? {
    val s = a + b
    return if (s < Byte.MIN_VALUE || s > Byte.MAX_VALUE) null else s.toByte()
}

public fun rustCheckedAdd(a: Short, b: Short): Short? {
    val s = a + b
    return if (s < Short.MIN_VALUE || s > Short.MAX_VALUE) null else s.toShort()
}

public fun rustCheckedAdd(a: Int, b: Int): Int? {
    val ovf = rustOverflowingAdd(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedAdd(a: Long, b: Long): Long? {
    val ovf = rustOverflowingAdd(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedAdd(a: UByte, b: UByte): UByte? {
    val s = a.toInt() + b.toInt()
    return if (s > 0xFF) null else s.toUByte()
}

public fun rustCheckedAdd(a: UShort, b: UShort): UShort? {
    val s = a.toInt() + b.toInt()
    return if (s > 0xFFFF) null else s.toUShort()
}

public fun rustCheckedAdd(a: UInt, b: UInt): UInt? {
    val s = a + b
    return if (s < a) null else s
}

public fun rustCheckedAdd(a: ULong, b: ULong): ULong? {
    val s = a + b
    return if (s < a) null else s
}

public fun rustCheckedAdd(a: U128, b: U128): U128? {
    val sum = a + b
    return if (sum < a) null else sum
}

public fun rustCheckedAdd(a: I128, b: I128): I128? {
    val ovf = rustOverflowingAdd(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedSub(a: Byte, b: Byte): Byte? {
    val d = a - b
    return if (d < Byte.MIN_VALUE || d > Byte.MAX_VALUE) null else d.toByte()
}

public fun rustCheckedSub(a: Short, b: Short): Short? {
    val d = a - b
    return if (d < Short.MIN_VALUE || d > Short.MAX_VALUE) null else d.toShort()
}

public fun rustCheckedSub(a: Int, b: Int): Int? {
    val ovf = rustOverflowingSub(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedSub(a: Long, b: Long): Long? {
    val ovf = rustOverflowingSub(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedSub(a: UByte, b: UByte): UByte? =
    if (a < b) null else (a - b).toUByte()

public fun rustCheckedSub(a: UShort, b: UShort): UShort? =
    if (a < b) null else (a - b).toUShort()

public fun rustCheckedSub(a: UInt, b: UInt): UInt? =
    if (a < b) null else a - b

public fun rustCheckedSub(a: ULong, b: ULong): ULong? =
    if (a < b) null else a - b

public fun rustCheckedSub(a: U128, b: U128): U128? = if (a < b) null else a - b

public fun rustCheckedSub(a: I128, b: I128): I128? {
    val ovf = rustOverflowingSub(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedMul(a: Byte, b: Byte): Byte? {
    val p = a * b
    return if (p < Byte.MIN_VALUE || p > Byte.MAX_VALUE) null else p.toByte()
}

public fun rustCheckedMul(a: Short, b: Short): Short? {
    val p = a * b
    return if (p < Short.MIN_VALUE || p > Short.MAX_VALUE) null else p.toShort()
}

public fun rustCheckedMul(a: Int, b: Int): Int? {
    val ovf = rustOverflowingMul(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedMul(a: Long, b: Long): Long? {
    val ovf = rustOverflowingMul(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedMul(a: UByte, b: UByte): UByte? {
    val p = a.toInt() * b.toInt()
    return if (p > 0xFF) null else p.toUByte()
}

public fun rustCheckedMul(a: UShort, b: UShort): UShort? {
    val p = a.toInt() * b.toInt()
    return if (p > 0xFFFF) null else p.toUShort()
}

public fun rustCheckedMul(a: UInt, b: UInt): UInt? {
    val ovf = rustOverflowingMul(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedMul(a: ULong, b: ULong): ULong? =
    if (kmUmulhU64(a, b) != 0uL) null else a * b

public fun rustCheckedMul(a: U128, b: U128): U128? {
    val ovf = rustOverflowingMul(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedMul(a: I128, b: I128): I128? {
    val ovf = rustOverflowingMul(a, b)
    return if (ovf.overflowed) null else ovf.value
}

public fun rustCheckedDiv(a: Byte, b: Byte): Byte? =
    if (b == 0.toByte() || (a == Byte.MIN_VALUE && b == (-1).toByte())) null else (a / b).toByte()

public fun rustCheckedDiv(a: Short, b: Short): Short? =
    if (b == 0.toShort() || (a == Short.MIN_VALUE && b == (-1).toShort())) null else (a / b).toShort()

public fun rustCheckedDiv(a: Int, b: Int): Int? =
    if (b == 0 || (a == Int.MIN_VALUE && b == -1)) null else a / b

public fun rustCheckedDiv(a: Long, b: Long): Long? =
    if (b == 0L || (a == Long.MIN_VALUE && b == -1L)) null else a / b

public fun rustCheckedDiv(a: UByte, b: UByte): UByte? =
    if (b == 0u.toUByte()) null else (a / b).toUByte()

public fun rustCheckedDiv(a: UShort, b: UShort): UShort? =
    if (b == 0u.toUShort()) null else (a / b).toUShort()

public fun rustCheckedDiv(a: UInt, b: UInt): UInt? =
    if (b == 0u) null else a / b

public fun rustCheckedDiv(a: ULong, b: ULong): ULong? =
    if (b == 0uL) null else a / b

public fun rustCheckedDiv(a: U128, b: U128): U128? = if (b == U128.ZERO) null else a / b

public fun rustCheckedDiv(a: I128, b: I128): I128? = if (b == I128.ZERO || (a == I128.MIN && b == I128_NEG_ONE)) null else a / b

public fun rustCheckedRem(a: Byte, b: Byte): Byte? =
    if (b == 0.toByte() || (a == Byte.MIN_VALUE && b == (-1).toByte())) null else (a % b).toByte()

public fun rustCheckedRem(a: Short, b: Short): Short? =
    if (b == 0.toShort() || (a == Short.MIN_VALUE && b == (-1).toShort())) null else (a % b).toShort()

public fun rustCheckedRem(a: Int, b: Int): Int? =
    if (b == 0 || (a == Int.MIN_VALUE && b == -1)) null else a % b

public fun rustCheckedRem(a: Long, b: Long): Long? =
    if (b == 0L || (a == Long.MIN_VALUE && b == -1L)) null else a % b

public fun rustCheckedRem(a: UByte, b: UByte): UByte? =
    if (b == 0u.toUByte()) null else (a % b).toUByte()

public fun rustCheckedRem(a: UShort, b: UShort): UShort? =
    if (b == 0u.toUShort()) null else (a % b).toUShort()

public fun rustCheckedRem(a: UInt, b: UInt): UInt? =
    if (b == 0u) null else a % b

public fun rustCheckedRem(a: ULong, b: ULong): ULong? =
    if (b == 0uL) null else a % b

public fun rustCheckedRem(a: U128, b: U128): U128? = if (b == U128.ZERO) null else a % b

public fun rustCheckedRem(a: I128, b: I128): I128? = if (b == I128.ZERO || (a == I128.MIN && b == I128_NEG_ONE)) null else a % b

public fun rustCheckedNeg(a: Byte): Byte? =
    if (a == Byte.MIN_VALUE) null else (-a).toByte()

public fun rustCheckedNeg(a: Short): Short? =
    if (a == Short.MIN_VALUE) null else (-a).toShort()

public fun rustCheckedNeg(a: Int): Int? =
    if (a == Int.MIN_VALUE) null else -a

public fun rustCheckedNeg(a: Long): Long? =
    if (a == Long.MIN_VALUE) null else -a

public fun rustCheckedNeg(a: UByte): UByte? =
    if (a == 0u.toUByte()) a else null

public fun rustCheckedNeg(a: UShort): UShort? =
    if (a == 0u.toUShort()) a else null

public fun rustCheckedNeg(a: UInt): UInt? =
    if (a == 0u) a else null

public fun rustCheckedNeg(a: ULong): ULong? =
    if (a == 0uL) a else null

public fun rustCheckedNeg(a: U128): U128? = if (a == U128.ZERO) U128.ZERO else null

public fun rustCheckedNeg(a: I128): I128? = if (a == I128.MIN) null else -a

public fun rustCheckedShl(a: Byte, rhs: UInt): Byte? =
    if (rhs >= 8u) null else (a.toInt() shl rhs.toInt()).toByte()

public fun rustCheckedShl(a: Short, rhs: UInt): Short? =
    if (rhs >= 16u) null else (a.toInt() shl rhs.toInt()).toShort()

public fun rustCheckedShl(a: Int, rhs: UInt): Int? =
    if (rhs >= 32u) null else a shl rhs.toInt()

public fun rustCheckedShl(a: Long, rhs: UInt): Long? =
    if (rhs >= 64u) null else a shl rhs.toInt()

public fun rustCheckedShl(a: UByte, rhs: UInt): UByte? =
    if (rhs >= 8u) null else (a.toUInt() shl rhs.toInt()).toUByte()

public fun rustCheckedShl(a: UShort, rhs: UInt): UShort? =
    if (rhs >= 16u) null else (a.toUInt() shl rhs.toInt()).toUShort()

public fun rustCheckedShl(a: UInt, rhs: UInt): UInt? =
    if (rhs >= 32u) null else a shl rhs.toInt()

public fun rustCheckedShl(a: ULong, rhs: UInt): ULong? =
    if (rhs >= 64u) null else a shl rhs.toInt()

public fun rustCheckedShl(a: U128, rhs: UInt): U128? = if (rhs >= 128u) null else a shl rhs.toInt()

public fun rustCheckedShl(a: I128, rhs: UInt): I128? = if (rhs >= 128u) null else a shl rhs.toInt()

public fun rustCheckedShr(a: Byte, rhs: UInt): Byte? =
    if (rhs >= 8u) null else (a.toInt() shr rhs.toInt()).toByte()

public fun rustCheckedShr(a: Short, rhs: UInt): Short? =
    if (rhs >= 16u) null else (a.toInt() shr rhs.toInt()).toShort()

public fun rustCheckedShr(a: Int, rhs: UInt): Int? =
    if (rhs >= 32u) null else a shr rhs.toInt()

public fun rustCheckedShr(a: Long, rhs: UInt): Long? =
    if (rhs >= 64u) null else a shr rhs.toInt()

public fun rustCheckedShr(a: UByte, rhs: UInt): UByte? =
    if (rhs >= 8u) null else (a.toUInt() shr rhs.toInt()).toUByte()

public fun rustCheckedShr(a: UShort, rhs: UInt): UShort? =
    if (rhs >= 16u) null else (a.toUInt() shr rhs.toInt()).toUShort()

public fun rustCheckedShr(a: UInt, rhs: UInt): UInt? =
    if (rhs >= 32u) null else a shr rhs.toInt()

public fun rustCheckedShr(a: ULong, rhs: UInt): ULong? =
    if (rhs >= 64u) null else a shr rhs.toInt()

public fun rustCheckedShr(a: U128, rhs: UInt): U128? = if (rhs >= 128u) null else a shr rhs.toInt()

public fun rustCheckedShr(a: I128, rhs: UInt): I128? = if (rhs >= 128u) null else a shr rhs.toInt()

// --- Wrapping arithmetic ---

public fun rustWrappingAdd(a: Byte, b: Byte): Byte = (a + b).toByte()

public fun rustWrappingAdd(a: Short, b: Short): Short = (a + b).toShort()

public fun rustWrappingAdd(a: Int, b: Int): Int = a + b

public fun rustWrappingAdd(a: Long, b: Long): Long = a + b

public fun rustWrappingAdd(a: UByte, b: UByte): UByte = (a + b).toUByte()

public fun rustWrappingAdd(a: UShort, b: UShort): UShort = (a + b).toUShort()

public fun rustWrappingAdd(a: UInt, b: UInt): UInt = a + b

public fun rustWrappingAdd(a: ULong, b: ULong): ULong = a + b

public fun rustWrappingAdd(a: U128, b: U128): U128 = a + b

public fun rustWrappingAdd(a: I128, b: I128): I128 = a + b

public fun rustWrappingSub(a: Byte, b: Byte): Byte = (a - b).toByte()

public fun rustWrappingSub(a: Short, b: Short): Short = (a - b).toShort()

public fun rustWrappingSub(a: Int, b: Int): Int = a - b

public fun rustWrappingSub(a: Long, b: Long): Long = a - b

public fun rustWrappingSub(a: UByte, b: UByte): UByte = (a - b).toUByte()

public fun rustWrappingSub(a: UShort, b: UShort): UShort = (a - b).toUShort()

public fun rustWrappingSub(a: UInt, b: UInt): UInt = a - b

public fun rustWrappingSub(a: ULong, b: ULong): ULong = a - b

public fun rustWrappingSub(a: U128, b: U128): U128 = a - b

public fun rustWrappingSub(a: I128, b: I128): I128 = a - b

public fun rustWrappingMul(a: Byte, b: Byte): Byte = (a * b).toByte()

public fun rustWrappingMul(a: Short, b: Short): Short = (a * b).toShort()

public fun rustWrappingMul(a: Int, b: Int): Int = a * b

public fun rustWrappingMul(a: Long, b: Long): Long = a * b

public fun rustWrappingMul(a: UByte, b: UByte): UByte = (a * b).toUByte()

public fun rustWrappingMul(a: UShort, b: UShort): UShort = (a * b).toUShort()

public fun rustWrappingMul(a: UInt, b: UInt): UInt = a * b

public fun rustWrappingMul(a: ULong, b: ULong): ULong = a * b

public fun rustWrappingMul(a: U128, b: U128): U128 = a * b

public fun rustWrappingMul(a: I128, b: I128): I128 = a * b

public fun rustWrappingDiv(a: Byte, b: Byte): Byte =
    if (a == Byte.MIN_VALUE && b == (-1).toByte()) Byte.MIN_VALUE else (a / b).toByte()

public fun rustWrappingDiv(a: Short, b: Short): Short =
    if (a == Short.MIN_VALUE && b == (-1).toShort()) Short.MIN_VALUE else (a / b).toShort()

public fun rustWrappingDiv(a: Int, b: Int): Int =
    if (a == Int.MIN_VALUE && b == -1) Int.MIN_VALUE else a / b

public fun rustWrappingDiv(a: Long, b: Long): Long =
    if (a == Long.MIN_VALUE && b == -1L) Long.MIN_VALUE else a / b

public fun rustWrappingDiv(a: UByte, b: UByte): UByte = (a / b).toUByte()

public fun rustWrappingDiv(a: UShort, b: UShort): UShort = (a / b).toUShort()

public fun rustWrappingDiv(a: UInt, b: UInt): UInt = a / b

public fun rustWrappingDiv(a: ULong, b: ULong): ULong = a / b

public fun rustWrappingDiv(a: U128, b: U128): U128 = a / b

public fun rustWrappingDiv(a: I128, b: I128): I128 = if (a == I128.MIN && b == I128_NEG_ONE) I128.MIN else a / b

public fun rustWrappingRem(a: Byte, b: Byte): Byte =
    if (a == Byte.MIN_VALUE && b == (-1).toByte()) 0 else (a % b).toByte()

public fun rustWrappingRem(a: Short, b: Short): Short =
    if (a == Short.MIN_VALUE && b == (-1).toShort()) 0 else (a % b).toShort()

public fun rustWrappingRem(a: Int, b: Int): Int =
    if (a == Int.MIN_VALUE && b == -1) 0 else a % b

public fun rustWrappingRem(a: Long, b: Long): Long =
    if (a == Long.MIN_VALUE && b == -1L) 0L else a % b

public fun rustWrappingRem(a: UByte, b: UByte): UByte = (a % b).toUByte()

public fun rustWrappingRem(a: UShort, b: UShort): UShort = (a % b).toUShort()

public fun rustWrappingRem(a: UInt, b: UInt): UInt = a % b

public fun rustWrappingRem(a: ULong, b: ULong): ULong = a % b

public fun rustWrappingRem(a: U128, b: U128): U128 = a % b

public fun rustWrappingRem(a: I128, b: I128): I128 = if (a == I128.MIN && b == I128_NEG_ONE) I128.ZERO else a % b

public fun rustWrappingNeg(a: Byte): Byte = (-a).toByte()

public fun rustWrappingNeg(a: Short): Short = (-a).toShort()

public fun rustWrappingNeg(a: Int): Int = -a

public fun rustWrappingNeg(a: Long): Long = -a

public fun rustWrappingNeg(a: UByte): UByte = (0 - a.toInt()).toUByte()

public fun rustWrappingNeg(a: UShort): UShort = (0 - a.toInt()).toUShort()

public fun rustWrappingNeg(a: UInt): UInt = 0u - a

public fun rustWrappingNeg(a: ULong): ULong = 0uL - a

public fun rustWrappingNeg(a: U128): U128 = if (a == U128.ZERO) U128.ZERO else a.inv() + U128.ONE

public fun rustWrappingNeg(a: I128): I128 = -a

public fun rustWrappingShl(a: Byte, rhs: UInt): Byte =
    (a.toInt() shl (rhs.toInt() and 7)).toByte()

public fun rustWrappingShl(a: Short, rhs: UInt): Short =
    (a.toInt() shl (rhs.toInt() and 15)).toShort()

public fun rustWrappingShl(a: Int, rhs: UInt): Int = a shl (rhs.toInt() and 31)

public fun rustWrappingShl(a: Long, rhs: UInt): Long = a shl (rhs.toInt() and 63)

public fun rustWrappingShl(a: UByte, rhs: UInt): UByte =
    (a.toUInt() shl (rhs.toInt() and 7)).toUByte()

public fun rustWrappingShl(a: UShort, rhs: UInt): UShort =
    (a.toUInt() shl (rhs.toInt() and 15)).toUShort()

public fun rustWrappingShl(a: UInt, rhs: UInt): UInt = a shl (rhs.toInt() and 31)

public fun rustWrappingShl(a: ULong, rhs: UInt): ULong = a shl (rhs.toInt() and 63)

public fun rustWrappingShl(a: U128, rhs: UInt): U128 = a shl (rhs.toInt() and 127)

public fun rustWrappingShl(a: I128, rhs: UInt): I128 = a shl (rhs.toInt() and 127)

public fun rustWrappingShr(a: Byte, rhs: UInt): Byte =
    (a.toInt() shr (rhs.toInt() and 7)).toByte()

public fun rustWrappingShr(a: Short, rhs: UInt): Short =
    (a.toInt() shr (rhs.toInt() and 15)).toShort()

public fun rustWrappingShr(a: Int, rhs: UInt): Int = a shr (rhs.toInt() and 31)

public fun rustWrappingShr(a: Long, rhs: UInt): Long = a shr (rhs.toInt() and 63)

public fun rustWrappingShr(a: UByte, rhs: UInt): UByte =
    (a.toUInt() shr (rhs.toInt() and 7)).toUByte()

public fun rustWrappingShr(a: UShort, rhs: UInt): UShort =
    (a.toUInt() shr (rhs.toInt() and 15)).toUShort()

public fun rustWrappingShr(a: UInt, rhs: UInt): UInt = a shr (rhs.toInt() and 31)

public fun rustWrappingShr(a: ULong, rhs: UInt): ULong = a shr (rhs.toInt() and 63)

public fun rustWrappingShr(a: U128, rhs: UInt): U128 = a shr (rhs.toInt() and 127)

public fun rustWrappingShr(a: I128, rhs: UInt): I128 = a shr (rhs.toInt() and 127)

// --- Saturating arithmetic ---

public fun rustSaturatingAdd(a: Byte, b: Byte): Byte =
    (a + b).coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()

public fun rustSaturatingAdd(a: Short, b: Short): Short =
    (a + b).coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()

public fun rustSaturatingAdd(a: Int, b: Int): Int =
    rustCheckedAdd(a, b) ?: if (a < 0) Int.MIN_VALUE else Int.MAX_VALUE

public fun rustSaturatingAdd(a: Long, b: Long): Long =
    rustCheckedAdd(a, b) ?: if (a < 0L) Long.MIN_VALUE else Long.MAX_VALUE

public fun rustSaturatingAdd(a: UByte, b: UByte): UByte =
    (a.toInt() + b.toInt()).coerceAtMost(0xFF).toUByte()

public fun rustSaturatingAdd(a: UShort, b: UShort): UShort =
    (a.toInt() + b.toInt()).coerceAtMost(0xFFFF).toUShort()

public fun rustSaturatingAdd(a: UInt, b: UInt): UInt =
    rustCheckedAdd(a, b) ?: UInt.MAX_VALUE

public fun rustSaturatingAdd(a: ULong, b: ULong): ULong =
    rustCheckedAdd(a, b) ?: ULong.MAX_VALUE

public fun rustSaturatingAdd(a: U128, b: U128): U128 = rustCheckedAdd(a, b) ?: U128.MAX

public fun rustSaturatingAdd(a: I128, b: I128): I128 = rustCheckedAdd(a, b) ?: if (a.high < 0L) I128.MIN else I128.MAX

public fun rustSaturatingSub(a: Byte, b: Byte): Byte =
    (a - b).coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()

public fun rustSaturatingSub(a: Short, b: Short): Short =
    (a - b).coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()

public fun rustSaturatingSub(a: Int, b: Int): Int =
    rustCheckedSub(a, b) ?: if (a < 0) Int.MIN_VALUE else Int.MAX_VALUE

public fun rustSaturatingSub(a: Long, b: Long): Long =
    rustCheckedSub(a, b) ?: if (a < 0L) Long.MIN_VALUE else Long.MAX_VALUE

public fun rustSaturatingSub(a: UByte, b: UByte): UByte =
    if (a < b) 0u.toUByte() else (a - b).toUByte()

public fun rustSaturatingSub(a: UShort, b: UShort): UShort =
    if (a < b) 0u.toUShort() else (a - b).toUShort()

public fun rustSaturatingSub(a: UInt, b: UInt): UInt =
    if (a < b) 0u else a - b

public fun rustSaturatingSub(a: ULong, b: ULong): ULong =
    if (a < b) 0uL else a - b

public fun rustSaturatingSub(a: U128, b: U128): U128 = rustCheckedSub(a, b) ?: U128.ZERO

public fun rustSaturatingSub(a: I128, b: I128): I128 = rustCheckedSub(a, b) ?: if (a.high < 0L) I128.MIN else I128.MAX

public fun rustSaturatingMul(a: Byte, b: Byte): Byte =
    (a * b).coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()

public fun rustSaturatingMul(a: Short, b: Short): Short =
    (a * b).coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()

public fun rustSaturatingMul(a: Int, b: Int): Int =
    rustCheckedMul(a, b) ?: if ((a < 0) != (b < 0)) Int.MIN_VALUE else Int.MAX_VALUE

public fun rustSaturatingMul(a: Long, b: Long): Long =
    rustCheckedMul(a, b) ?: if ((a < 0L) != (b < 0L)) Long.MIN_VALUE else Long.MAX_VALUE

public fun rustSaturatingMul(a: UByte, b: UByte): UByte =
    (a.toInt() * b.toInt()).coerceAtMost(0xFF).toUByte()

public fun rustSaturatingMul(a: UShort, b: UShort): UShort =
    (a.toInt() * b.toInt()).coerceAtMost(0xFFFF).toUShort()

public fun rustSaturatingMul(a: UInt, b: UInt): UInt =
    rustCheckedMul(a, b) ?: UInt.MAX_VALUE

public fun rustSaturatingMul(a: ULong, b: ULong): ULong =
    rustCheckedMul(a, b) ?: ULong.MAX_VALUE

public fun rustSaturatingMul(a: U128, b: U128): U128 = rustCheckedMul(a, b) ?: U128.MAX

public fun rustSaturatingMul(a: I128, b: I128): I128 {
    val ovf = rustOverflowingMul(a, b)
    if (!ovf.overflowed) return ovf.value
    return if ((a.high < 0L) != (b.high < 0L)) I128.MIN else I128.MAX
}

public fun rustSaturatingAbs(a: Byte): Byte =
    if (a == Byte.MIN_VALUE) {
        Byte.MAX_VALUE
    } else if (a < 0) {
        (-a).toByte()
    } else {
        a
    }

public fun rustSaturatingAbs(a: Short): Short =
    if (a == Short.MIN_VALUE) {
        Short.MAX_VALUE
    } else if (a < 0) {
        (-a).toShort()
    } else {
        a
    }

public fun rustSaturatingAbs(a: Int): Int =
    if (a == Int.MIN_VALUE) {
        Int.MAX_VALUE
    } else if (a < 0) {
        -a
    } else {
        a
    }

public fun rustSaturatingAbs(a: Long): Long =
    if (a == Long.MIN_VALUE) {
        Long.MAX_VALUE
    } else if (a < 0L) {
        -a
    } else {
        a
    }

public fun rustSaturatingAbs(a: I128): I128 = if (a == I128.MIN) I128.MAX else if (a.high < 0L) -a else a

public fun rustSaturatingNeg(a: Byte): Byte =
    if (a == Byte.MIN_VALUE) Byte.MAX_VALUE else (-a).toByte()

public fun rustSaturatingNeg(a: Short): Short =
    if (a == Short.MIN_VALUE) Short.MAX_VALUE else (-a).toShort()

public fun rustSaturatingNeg(a: Int): Int =
    if (a == Int.MIN_VALUE) Int.MAX_VALUE else -a

public fun rustSaturatingNeg(a: Long): Long =
    if (a == Long.MIN_VALUE) Long.MAX_VALUE else -a

public fun rustSaturatingNeg(a: I128): I128 =
    if (a == I128.MIN) I128.MAX else -a

public fun rustCheckedAbs(a: Byte): Byte? =
    if (a == Byte.MIN_VALUE) {
        null
    } else if (a < 0) {
        (-a).toByte()
    } else {
        a
    }

public fun rustCheckedAbs(a: Short): Short? =
    if (a == Short.MIN_VALUE) {
        null
    } else if (a < 0) {
        (-a).toShort()
    } else {
        a
    }

public fun rustCheckedAbs(a: Int): Int? =
    if (a == Int.MIN_VALUE) {
        null
    } else if (a < 0) {
        -a
    } else {
        a
    }

public fun rustCheckedAbs(a: Long): Long? =
    if (a == Long.MIN_VALUE) {
        null
    } else if (a < 0L) {
        -a
    } else {
        a
    }

public fun rustCheckedAbs(a: I128): I128? = if (a == I128.MIN) null else if (a.high < 0L) -a else a

public fun rustWrappingAbs(a: Byte): Byte = if (a < 0) (-a).toByte() else a

public fun rustWrappingAbs(a: Short): Short = if (a < 0) (-a).toShort() else a

public fun rustWrappingAbs(a: Int): Int = if (a < 0) -a else a

public fun rustWrappingAbs(a: Long): Long = if (a < 0L) -a else a

public fun rustWrappingAbs(a: I128): I128 = if (a.high < 0L) -a else a

public fun rustOverflowingAbs(a: Byte): OverflowingResult<Byte> =
    OverflowingResult(rustWrappingAbs(a), a == Byte.MIN_VALUE)

public fun rustOverflowingAbs(a: Short): OverflowingResult<Short> =
    OverflowingResult(rustWrappingAbs(a), a == Short.MIN_VALUE)

public fun rustOverflowingAbs(a: Int): OverflowingResult<Int> =
    OverflowingResult(rustWrappingAbs(a), a == Int.MIN_VALUE)

public fun rustOverflowingAbs(a: Long): OverflowingResult<Long> =
    OverflowingResult(rustWrappingAbs(a), a == Long.MIN_VALUE)

public fun rustOverflowingAbs(a: I128): OverflowingResult<I128> = OverflowingResult(rustWrappingAbs(a), a == I128.MIN)

// --- Overflowing arithmetic ---

public fun rustOverflowingAdd(a: Byte, b: Byte): OverflowingResult<Byte> {
    val s = a + b
    return OverflowingResult(s.toByte(), s < Byte.MIN_VALUE || s > Byte.MAX_VALUE)
}

public fun rustOverflowingAdd(a: Short, b: Short): OverflowingResult<Short> {
    val s = a + b
    return OverflowingResult(s.toShort(), s < Short.MIN_VALUE || s > Short.MAX_VALUE)
}

public fun rustOverflowingAdd(a: Int, b: Int): OverflowingResult<Int> {
    val sum = a + b
    return OverflowingResult(sum, ((a xor sum) and (b xor sum)) < 0)
}

public fun rustOverflowingAdd(a: Long, b: Long): OverflowingResult<Long> {
    val sum = a + b
    return OverflowingResult(sum, ((a xor sum) and (b xor sum)) < 0L)
}

public fun rustOverflowingAdd(a: UByte, b: UByte): OverflowingResult<UByte> {
    val s = a.toInt() + b.toInt()
    return OverflowingResult(s.toUByte(), s > 0xFF)
}

public fun rustOverflowingAdd(a: UShort, b: UShort): OverflowingResult<UShort> {
    val s = a.toInt() + b.toInt()
    return OverflowingResult(s.toUShort(), s > 0xFFFF)
}

public fun rustOverflowingAdd(a: UInt, b: UInt): OverflowingResult<UInt> {
    val s = a + b
    return OverflowingResult(s, s < a)
}

public fun rustOverflowingAdd(a: ULong, b: ULong): OverflowingResult<ULong> {
    val s = a + b
    return OverflowingResult(s, s < a)
}

public fun rustOverflowingAdd(a: U128, b: U128): OverflowingResult<U128> {
    val sum = a + b
    return OverflowingResult(sum, sum < a)
}

public fun rustOverflowingAdd(a: I128, b: I128): OverflowingResult<I128> {
    val sum = a + b
    val overflowed = (b >= I128.ZERO && sum < a) || (b < I128.ZERO && sum > a)
    return OverflowingResult(sum, overflowed)
}

public fun rustOverflowingSub(a: Byte, b: Byte): OverflowingResult<Byte> {
    val d = a - b
    return OverflowingResult(d.toByte(), d < Byte.MIN_VALUE || d > Byte.MAX_VALUE)
}

public fun rustOverflowingSub(a: Short, b: Short): OverflowingResult<Short> {
    val d = a - b
    return OverflowingResult(d.toShort(), d < Short.MIN_VALUE || d > Short.MAX_VALUE)
}

public fun rustOverflowingSub(a: Int, b: Int): OverflowingResult<Int> {
    val diff = a - b
    return OverflowingResult(diff, ((a xor b) and (a xor diff)) < 0)
}

public fun rustOverflowingSub(a: Long, b: Long): OverflowingResult<Long> {
    val diff = a - b
    return OverflowingResult(diff, ((a xor b) and (a xor diff)) < 0L)
}

public fun rustOverflowingSub(a: UByte, b: UByte): OverflowingResult<UByte> =
    OverflowingResult((a - b).toUByte(), a < b)

public fun rustOverflowingSub(a: UShort, b: UShort): OverflowingResult<UShort> =
    OverflowingResult((a - b).toUShort(), a < b)

public fun rustOverflowingSub(a: UInt, b: UInt): OverflowingResult<UInt> =
    OverflowingResult(a - b, a < b)

public fun rustOverflowingSub(a: ULong, b: ULong): OverflowingResult<ULong> =
    OverflowingResult(a - b, a < b)

public fun rustOverflowingSub(a: U128, b: U128): OverflowingResult<U128> = OverflowingResult(a - b, a < b)

public fun rustOverflowingSub(a: I128, b: I128): OverflowingResult<I128> {
    val diff = a - b
    val overflowed = (b >= I128.ZERO && diff > a) || (b < I128.ZERO && diff < a)
    return OverflowingResult(diff, overflowed)
}

public fun rustOverflowingMul(a: Byte, b: Byte): OverflowingResult<Byte> {
    val p = a * b
    return OverflowingResult(p.toByte(), p < Byte.MIN_VALUE || p > Byte.MAX_VALUE)
}

public fun rustOverflowingMul(a: Short, b: Short): OverflowingResult<Short> {
    val p = a * b
    return OverflowingResult(p.toShort(), p < Short.MIN_VALUE || p > Short.MAX_VALUE)
}

public fun rustOverflowingMul(a: Int, b: Int): OverflowingResult<Int> {
    val p = a.toLong() * b.toLong()
    return OverflowingResult(p.toInt(), p < Int.MIN_VALUE || p > Int.MAX_VALUE)
}

public fun rustOverflowingMul(a: Long, b: Long): OverflowingResult<Long> {
    val lo = a * b
    val hi = kmSmulhI64(a, b)
    return OverflowingResult(lo, hi != (lo shr 63))
}

public fun rustOverflowingMul(a: UByte, b: UByte): OverflowingResult<UByte> {
    val p = a.toInt() * b.toInt()
    return OverflowingResult(p.toUByte(), p > 0xFF)
}

public fun rustOverflowingMul(a: UShort, b: UShort): OverflowingResult<UShort> {
    val p = a.toInt() * b.toInt()
    return OverflowingResult(p.toUShort(), p > 0xFFFF)
}

public fun rustOverflowingMul(a: UInt, b: UInt): OverflowingResult<UInt> {
    val p = a.toULong() * b.toULong()
    return OverflowingResult(p.toUInt(), p > UInt.MAX_VALUE.toULong())
}

public fun rustOverflowingMul(a: ULong, b: ULong): OverflowingResult<ULong> =
    OverflowingResult(a * b, kmUmulhU64(a, b) != 0uL)

public fun rustOverflowingMul(a: U128, b: U128): OverflowingResult<U128> {
    val lo = a.low * b.low
    val c = kmUmulhU64(a.low, b.low)
    val p01 = a.low * b.high
    val p10 = a.high * b.low
    val s1 = c + p01
    val hi = s1 + p10
    val overflowed =
        (a.high != 0uL && b.high != 0uL) ||
            kmUmulhU64(a.low, b.high) != 0uL ||
            kmUmulhU64(a.high, b.low) != 0uL ||
            s1 < c ||
            hi < s1
    return OverflowingResult(U128(hi, lo), overflowed)
}

public fun rustOverflowingMul(a: I128, b: I128): OverflowingResult<I128> {
    val negA = a.high < 0L
    val negB = b.high < 0L
    val magA = if (negA) (a.toU128().inv() + U128.ONE) else a.toU128()
    val magB = if (negB) (b.toU128().inv() + U128.ONE) else b.toU128()
    val magProd = rustOverflowingMul(magA, magB)
    val raw = magProd.value
    val signedVal = if (negA != negB) (raw.inv() + U128.ONE).toI128() else raw.toI128()
    val signBit = 1uL shl 63
    val minMag = U128(signBit, 0uL)
    val overflowed =
        if (magProd.overflowed) {
            true
        } else if (negA != negB) {
            raw > minMag
        } else {
            raw.high >= signBit
        }
    return OverflowingResult(signedVal, overflowed)
}

public fun rustOverflowingNeg(a: Byte): OverflowingResult<Byte> =
    OverflowingResult((-a).toByte(), a == Byte.MIN_VALUE)

public fun rustOverflowingNeg(a: Short): OverflowingResult<Short> =
    OverflowingResult((-a).toShort(), a == Short.MIN_VALUE)

public fun rustOverflowingNeg(a: Int): OverflowingResult<Int> =
    OverflowingResult(-a, a == Int.MIN_VALUE)

public fun rustOverflowingNeg(a: Long): OverflowingResult<Long> =
    OverflowingResult(-a, a == Long.MIN_VALUE)

public fun rustOverflowingNeg(a: UByte): OverflowingResult<UByte> =
    OverflowingResult(rustWrappingNeg(a), a != 0u.toUByte())

public fun rustOverflowingNeg(a: UShort): OverflowingResult<UShort> =
    OverflowingResult(rustWrappingNeg(a), a != 0u.toUShort())

public fun rustOverflowingNeg(a: UInt): OverflowingResult<UInt> =
    OverflowingResult(rustWrappingNeg(a), a != 0u)

public fun rustOverflowingNeg(a: ULong): OverflowingResult<ULong> =
    OverflowingResult(rustWrappingNeg(a), a != 0uL)

public fun rustOverflowingNeg(a: U128): OverflowingResult<U128> = OverflowingResult(rustWrappingNeg(a), a != U128.ZERO)

public fun rustOverflowingNeg(a: I128): OverflowingResult<I128> = OverflowingResult(-a, a == I128.MIN)

public fun rustOverflowingShl(a: Byte, rhs: UInt): OverflowingResult<Byte> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 8u)

public fun rustOverflowingShl(a: Short, rhs: UInt): OverflowingResult<Short> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 16u)

public fun rustOverflowingShl(a: Int, rhs: UInt): OverflowingResult<Int> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 32u)

public fun rustOverflowingShl(a: Long, rhs: UInt): OverflowingResult<Long> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 64u)

public fun rustOverflowingShl(a: UByte, rhs: UInt): OverflowingResult<UByte> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 8u)

public fun rustOverflowingShl(a: UShort, rhs: UInt): OverflowingResult<UShort> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 16u)

public fun rustOverflowingShl(a: UInt, rhs: UInt): OverflowingResult<UInt> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 32u)

public fun rustOverflowingShl(a: ULong, rhs: UInt): OverflowingResult<ULong> =
    OverflowingResult(rustWrappingShl(a, rhs), rhs >= 64u)

public fun rustOverflowingShl(a: U128, rhs: UInt): OverflowingResult<U128> = OverflowingResult(rustWrappingShl(a, rhs), rhs >= 128u)

public fun rustOverflowingShl(a: I128, rhs: UInt): OverflowingResult<I128> = OverflowingResult(rustWrappingShl(a, rhs), rhs >= 128u)

public fun rustOverflowingShr(a: Byte, rhs: UInt): OverflowingResult<Byte> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 8u)

public fun rustOverflowingShr(a: Short, rhs: UInt): OverflowingResult<Short> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 16u)

public fun rustOverflowingShr(a: Int, rhs: UInt): OverflowingResult<Int> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 32u)

public fun rustOverflowingShr(a: Long, rhs: UInt): OverflowingResult<Long> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 64u)

public fun rustOverflowingShr(a: UByte, rhs: UInt): OverflowingResult<UByte> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 8u)

public fun rustOverflowingShr(a: UShort, rhs: UInt): OverflowingResult<UShort> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 16u)

public fun rustOverflowingShr(a: UInt, rhs: UInt): OverflowingResult<UInt> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 32u)

public fun rustOverflowingShr(a: ULong, rhs: UInt): OverflowingResult<ULong> =
    OverflowingResult(rustWrappingShr(a, rhs), rhs >= 64u)

public fun rustOverflowingShr(a: U128, rhs: UInt): OverflowingResult<U128> = OverflowingResult(rustWrappingShr(a, rhs), rhs >= 128u)

public fun rustOverflowingShr(a: I128, rhs: UInt): OverflowingResult<I128> = OverflowingResult(rustWrappingShr(a, rhs), rhs >= 128u)

// --- Euclidean division & remainder ---

public fun rustDivEuclid(a: Byte, b: Byte): Byte =
    rustCheckedDivEuclid(a, b) ?: error("attempt to divide with overflow")

public fun rustDivEuclid(a: Short, b: Short): Short =
    rustCheckedDivEuclid(a, b) ?: error("attempt to divide with overflow")

public fun rustDivEuclid(a: Int, b: Int): Int =
    rustCheckedDivEuclid(a, b) ?: error("attempt to divide with overflow")

public fun rustDivEuclid(a: Long, b: Long): Long =
    rustCheckedDivEuclid(a, b) ?: error("attempt to divide with overflow")

public fun rustDivEuclid(a: UByte, b: UByte): UByte = (a / b).toUByte()

public fun rustDivEuclid(a: UShort, b: UShort): UShort = (a / b).toUShort()

public fun rustDivEuclid(a: UInt, b: UInt): UInt = a / b

public fun rustDivEuclid(a: ULong, b: ULong): ULong = a / b

public fun rustDivEuclid(a: U128, b: U128): U128 = a / b

public fun rustDivEuclid(a: I128, b: I128): I128 {
    val q = rustWrappingDiv(a, b)
    val r = rustWrappingRem(a, b)
    return if (r < I128.ZERO) {
        if (b > I128.ZERO) q - I128.ONE else q + I128.ONE
    } else {
        q
    }
}

public fun rustRemEuclid(a: Byte, b: Byte): Byte =
    rustCheckedRemEuclid(a, b) ?: error("attempt to calculate the remainder with overflow")

public fun rustRemEuclid(a: Short, b: Short): Short =
    rustCheckedRemEuclid(a, b) ?: error("attempt to calculate the remainder with overflow")

public fun rustRemEuclid(a: Int, b: Int): Int =
    rustCheckedRemEuclid(a, b) ?: error("attempt to calculate the remainder with overflow")

public fun rustRemEuclid(a: Long, b: Long): Long =
    rustCheckedRemEuclid(a, b) ?: error("attempt to calculate the remainder with overflow")

public fun rustRemEuclid(a: UByte, b: UByte): UByte = (a % b).toUByte()

public fun rustRemEuclid(a: UShort, b: UShort): UShort = (a % b).toUShort()

public fun rustRemEuclid(a: UInt, b: UInt): UInt = a % b

public fun rustRemEuclid(a: ULong, b: ULong): ULong = a % b

public fun rustRemEuclid(a: U128, b: U128): U128 = a % b

public fun rustRemEuclid(a: I128, b: I128): I128 {
    val r = rustWrappingRem(a, b)
    if (r < I128.ZERO) {
        return if (b < I128.ZERO) r - b else r + b
    }
    return r
}

public fun rustCheckedDivEuclid(a: Byte, b: Byte): Byte? {
    val q = rustCheckedDiv(a, b) ?: return null
    val r = a % b
    return if (r < 0) (if (b > 0) q - 1 else q + 1).toByte() else q
}

public fun rustCheckedDivEuclid(a: Short, b: Short): Short? {
    val q = rustCheckedDiv(a, b) ?: return null
    val r = a % b
    return if (r < 0) (if (b > 0) q - 1 else q + 1).toShort() else q
}

public fun rustCheckedDivEuclid(a: Int, b: Int): Int? {
    val q = rustCheckedDiv(a, b) ?: return null
    val r = a % b
    return if (r < 0) (if (b > 0) q - 1 else q + 1) else q
}

public fun rustCheckedDivEuclid(a: Long, b: Long): Long? {
    val q = rustCheckedDiv(a, b) ?: return null
    val r = a % b
    return if (r < 0L) (if (b > 0L) q - 1L else q + 1L) else q
}

public fun rustCheckedDivEuclid(a: UByte, b: UByte): UByte? = rustCheckedDiv(a, b)

public fun rustCheckedDivEuclid(a: UShort, b: UShort): UShort? = rustCheckedDiv(a, b)

public fun rustCheckedDivEuclid(a: UInt, b: UInt): UInt? = rustCheckedDiv(a, b)

public fun rustCheckedDivEuclid(a: ULong, b: ULong): ULong? = rustCheckedDiv(a, b)

public fun rustCheckedDivEuclid(a: U128, b: U128): U128? = rustCheckedDiv(a, b)

public fun rustCheckedDivEuclid(a: I128, b: I128): I128? {
    val q = rustCheckedDiv(a, b) ?: return null
    val r = a % b
    return if (r < I128.ZERO) {
        if (b > I128.ZERO) q - I128.ONE else q + I128.ONE
    } else {
        q
    }
}

public fun rustCheckedRemEuclid(a: Byte, b: Byte): Byte? {
    if (b == 0.toByte() || (a == Byte.MIN_VALUE && b == (-1).toByte())) return null
    val r = a % b
    return if (r < 0) (if (b < 0) r - b else r + b).toByte() else r.toByte()
}

public fun rustCheckedRemEuclid(a: Short, b: Short): Short? {
    if (b == 0.toShort() || (a == Short.MIN_VALUE && b == (-1).toShort())) return null
    val r = a % b
    return if (r < 0) (if (b < 0) r - b else r + b).toShort() else r.toShort()
}

public fun rustCheckedRemEuclid(a: Int, b: Int): Int? {
    if (b == 0 || (a == Int.MIN_VALUE && b == -1)) return null
    val r = a % b
    return if (r < 0) (if (b < 0) r - b else r + b) else r
}

public fun rustCheckedRemEuclid(a: Long, b: Long): Long? {
    if (b == 0L || (a == Long.MIN_VALUE && b == -1L)) return null
    val r = a % b
    return if (r < 0L) (if (b < 0L) r - b else r + b) else r
}

public fun rustCheckedRemEuclid(a: UByte, b: UByte): UByte? = rustCheckedRem(a, b)

public fun rustCheckedRemEuclid(a: UShort, b: UShort): UShort? = rustCheckedRem(a, b)

public fun rustCheckedRemEuclid(a: UInt, b: UInt): UInt? = rustCheckedRem(a, b)

public fun rustCheckedRemEuclid(a: ULong, b: ULong): ULong? = rustCheckedRem(a, b)

public fun rustCheckedRemEuclid(a: U128, b: U128): U128? = rustCheckedRem(a, b)

public fun rustCheckedRemEuclid(a: I128, b: I128): I128? {
    if (b == I128.ZERO || (a == I128.MIN && b == I128_NEG_ONE)) return null
    val r = a % b
    if (r < I128.ZERO) {
        return if (b < I128.ZERO) r - b else r + b
    }
    return r
}

// --- Endianness, bit counts, bit reversal, rotations ---

public fun rustSwapBytes(v: Byte): Byte = v

public fun rustSwapBytes(v: UByte): UByte = v

public fun rustSwapBytes(v: Short): Short = rustSwapBytes(v.toUShort()).toShort()

public fun rustSwapBytes(v: UShort): UShort {
    val x = v.toInt() and 0xFFFF
    return (((x and 0xFF) shl 8) or (x ushr 8)).toUShort()
}

public fun rustSwapBytes(v: Int): Int = rustSwapBytes(v.toUInt()).toInt()

public fun rustSwapBytes(v: UInt): UInt =
    ((v and 0x000000FFu) shl 24) or
        ((v and 0x0000FF00u) shl 8) or
        ((v and 0x00FF0000u) shr 8) or
        ((v and 0xFF000000u) shr 24)

public fun rustSwapBytes(v: Long): Long = rustSwapBytes(v.toULong()).toLong()

public fun rustSwapBytes(v: ULong): ULong {
    val x0 = (v and 0x00000000000000FFuL) shl 56
    val x1 = (v and 0x000000000000FF00uL) shl 40
    val x2 = (v and 0x0000000000FF0000uL) shl 24
    val x3 = (v and 0x00000000FF000000uL) shl 8
    val x4 = (v and 0x000000FF00000000uL) shr 8
    val x5 = (v and 0x0000FF0000000000uL) shr 24
    val x6 = (v and 0x00FF000000000000uL) shr 40
    val x7 = (v and 0xFF00000000000000uL) shr 56
    return x0 or x1 or x2 or x3 or x4 or x5 or x6 or x7
}

public fun rustSwapBytes(v: U128): U128 = U128(rustSwapBytes(v.low), rustSwapBytes(v.high))

public fun rustSwapBytes(v: I128): I128 = rustSwapBytes(v.toU128()).toI128()

public fun rustToBe(v: Byte): Byte = v

public fun rustToBe(v: UByte): UByte = v

public fun rustToBe(v: Short): Short = rustSwapBytes(v)

public fun rustToBe(v: UShort): UShort = rustSwapBytes(v)

public fun rustToBe(v: Int): Int = rustSwapBytes(v)

public fun rustToBe(v: UInt): UInt = rustSwapBytes(v)

public fun rustToBe(v: Long): Long = rustSwapBytes(v)

public fun rustToBe(v: ULong): ULong = rustSwapBytes(v)

public fun rustToBe(v: U128): U128 = rustSwapBytes(v)

public fun rustToBe(v: I128): I128 = rustSwapBytes(v)

public fun rustToLe(v: Byte): Byte = v

public fun rustToLe(v: UByte): UByte = v

public fun rustToLe(v: Short): Short = v

public fun rustToLe(v: UShort): UShort = v

public fun rustToLe(v: Int): Int = v

public fun rustToLe(v: UInt): UInt = v

public fun rustToLe(v: Long): Long = v

public fun rustToLe(v: ULong): ULong = v

public fun rustToLe(v: U128): U128 = v

public fun rustToLe(v: I128): I128 = v

public fun rustFromBe(v: Byte): Byte = v

public fun rustFromBe(v: UByte): UByte = v

public fun rustFromBe(v: Short): Short = rustSwapBytes(v)

public fun rustFromBe(v: UShort): UShort = rustSwapBytes(v)

public fun rustFromBe(v: Int): Int = rustSwapBytes(v)

public fun rustFromBe(v: UInt): UInt = rustSwapBytes(v)

public fun rustFromBe(v: Long): Long = rustSwapBytes(v)

public fun rustFromBe(v: ULong): ULong = rustSwapBytes(v)

public fun rustFromBe(v: U128): U128 = rustSwapBytes(v)

public fun rustFromBe(v: I128): I128 = rustSwapBytes(v)

public fun rustFromLe(v: Byte): Byte = v

public fun rustFromLe(v: UByte): UByte = v

public fun rustFromLe(v: Short): Short = v

public fun rustFromLe(v: UShort): UShort = v

public fun rustFromLe(v: Int): Int = v

public fun rustFromLe(v: UInt): UInt = v

public fun rustFromLe(v: Long): Long = v

public fun rustFromLe(v: ULong): ULong = v

public fun rustFromLe(v: U128): U128 = v

public fun rustFromLe(v: I128): I128 = v

public fun rustReverseBits(v: UByte): UByte {
    var x = v.toInt() and 0xFF
    x = ((x and 0x55) shl 1) or ((x ushr 1) and 0x55)
    x = ((x and 0x33) shl 2) or ((x ushr 2) and 0x33)
    x = ((x and 0x0F) shl 4) or ((x ushr 4) and 0x0F)
    return x.toUByte()
}

public fun rustReverseBits(v: Byte): Byte = rustReverseBits(v.toUByte()).toByte()

public fun rustReverseBits(v: UShort): UShort {
    var x = v.toInt() and 0xFFFF
    x = ((x and 0x5555) shl 1) or ((x ushr 1) and 0x5555)
    x = ((x and 0x3333) shl 2) or ((x ushr 2) and 0x3333)
    x = ((x and 0x0F0F) shl 4) or ((x ushr 4) and 0x0F0F)
    return rustSwapBytes(x.toUShort())
}

public fun rustReverseBits(v: Short): Short = rustReverseBits(v.toUShort()).toShort()

public fun rustReverseBits(v: UInt): UInt {
    var x = v
    x = ((x and 0x55555555u) shl 1) or ((x shr 1) and 0x55555555u)
    x = ((x and 0x33333333u) shl 2) or ((x shr 2) and 0x33333333u)
    x = ((x and 0x0F0F0F0Fu) shl 4) or ((x shr 4) and 0x0F0F0F0Fu)
    return rustSwapBytes(x)
}

public fun rustReverseBits(v: Int): Int = rustReverseBits(v.toUInt()).toInt()

public fun rustReverseBits(v: ULong): ULong {
    var x = v
    x = ((x and 0x5555555555555555uL) shl 1) or ((x shr 1) and 0x5555555555555555uL)
    x = ((x and 0x3333333333333333uL) shl 2) or ((x shr 2) and 0x3333333333333333uL)
    x = ((x and 0x0F0F0F0F0F0F0F0FuL) shl 4) or ((x shr 4) and 0x0F0F0F0F0F0F0F0FuL)
    return rustSwapBytes(x)
}

public fun rustReverseBits(v: Long): Long = rustReverseBits(v.toULong()).toLong()

public fun rustReverseBits(v: U128): U128 = U128(rustReverseBits(v.low), rustReverseBits(v.high))

public fun rustReverseBits(v: I128): I128 = rustReverseBits(v.toU128()).toI128()

public fun rustCountOnes(v: UByte): UInt = (v.toUInt() and 0xFFu).countOneBits().toUInt()

public fun rustCountOnes(v: Byte): UInt = rustCountOnes(v.toUByte())

public fun rustCountOnes(v: UShort): UInt = (v.toUInt() and 0xFFFFu).countOneBits().toUInt()

public fun rustCountOnes(v: Short): UInt = rustCountOnes(v.toUShort())

public fun rustCountOnes(v: UInt): UInt = v.countOneBits().toUInt()

public fun rustCountOnes(v: Int): UInt = v.countOneBits().toUInt()

public fun rustCountOnes(v: ULong): UInt = v.countOneBits().toUInt()

public fun rustCountOnes(v: Long): UInt = v.countOneBits().toUInt()

public fun rustCountOnes(v: U128): UInt = (v.high.countOneBits() + v.low.countOneBits()).toUInt()

public fun rustCountOnes(v: I128): UInt = rustCountOnes(v.toU128())

public fun rustCountZeros(v: UByte): UInt = 8u - rustCountOnes(v)

public fun rustCountZeros(v: Byte): UInt = 8u - rustCountOnes(v)

public fun rustCountZeros(v: UShort): UInt = 16u - rustCountOnes(v)

public fun rustCountZeros(v: Short): UInt = 16u - rustCountOnes(v)

public fun rustCountZeros(v: UInt): UInt = 32u - rustCountOnes(v)

public fun rustCountZeros(v: Int): UInt = 32u - rustCountOnes(v)

public fun rustCountZeros(v: ULong): UInt = 64u - rustCountOnes(v)

public fun rustCountZeros(v: Long): UInt = 64u - rustCountOnes(v)

public fun rustCountZeros(v: U128): UInt = 128u - rustCountOnes(v)

public fun rustCountZeros(v: I128): UInt = 128u - rustCountOnes(v)

public fun rustLeadingZeros(v: UByte): UInt =
    if (v == 0u.toUByte()) 8u else ((v.toUInt() and 0xFFu).countLeadingZeroBits() - 24).toUInt()

public fun rustLeadingZeros(v: Byte): UInt = rustLeadingZeros(v.toUByte())

public fun rustLeadingZeros(v: UShort): UInt =
    if (v == 0u.toUShort()) 16u else ((v.toUInt() and 0xFFFFu).countLeadingZeroBits() - 16).toUInt()

public fun rustLeadingZeros(v: Short): UInt = rustLeadingZeros(v.toUShort())

public fun rustLeadingZeros(v: UInt): UInt = v.countLeadingZeroBits().toUInt()

public fun rustLeadingZeros(v: Int): UInt = v.countLeadingZeroBits().toUInt()

public fun rustLeadingZeros(v: ULong): UInt = v.countLeadingZeroBits().toUInt()

public fun rustLeadingZeros(v: Long): UInt = v.countLeadingZeroBits().toUInt()

public fun rustLeadingZeros(v: U128): UInt =
    if (v.high != 0uL) {
        v.high.countLeadingZeroBits().toUInt()
    } else {
        64u + v.low.countLeadingZeroBits().toUInt()
    }

public fun rustLeadingZeros(v: I128): UInt = rustLeadingZeros(v.toU128())

public fun rustTrailingZeros(v: UByte): UInt =
    if (v == 0u.toUByte()) 8u else (v.toUInt() and 0xFFu).countTrailingZeroBits().toUInt()

public fun rustTrailingZeros(v: Byte): UInt = rustTrailingZeros(v.toUByte())

public fun rustTrailingZeros(v: UShort): UInt =
    if (v == 0u.toUShort()) 16u else (v.toUInt() and 0xFFFFu).countTrailingZeroBits().toUInt()

public fun rustTrailingZeros(v: Short): UInt = rustTrailingZeros(v.toUShort())

public fun rustTrailingZeros(v: UInt): UInt = v.countTrailingZeroBits().toUInt()

public fun rustTrailingZeros(v: Int): UInt = v.countTrailingZeroBits().toUInt()

public fun rustTrailingZeros(v: ULong): UInt = v.countTrailingZeroBits().toUInt()

public fun rustTrailingZeros(v: Long): UInt = v.countTrailingZeroBits().toUInt()

public fun rustTrailingZeros(v: U128): UInt =
    if (v.low != 0uL) {
        v.low.countTrailingZeroBits().toUInt()
    } else {
        64u + v.high.countTrailingZeroBits().toUInt()
    }

public fun rustTrailingZeros(v: I128): UInt = rustTrailingZeros(v.toU128())

public fun rustLeadingOnes(v: UByte): UInt = rustLeadingZeros(v.inv())

public fun rustLeadingOnes(v: Byte): UInt = rustLeadingZeros(v.toUByte().inv())

public fun rustLeadingOnes(v: UShort): UInt = rustLeadingZeros(v.inv())

public fun rustLeadingOnes(v: Short): UInt = rustLeadingZeros(v.toUShort().inv())

public fun rustLeadingOnes(v: UInt): UInt = v.inv().countLeadingZeroBits().toUInt()

public fun rustLeadingOnes(v: Int): UInt = v.inv().countLeadingZeroBits().toUInt()

public fun rustLeadingOnes(v: ULong): UInt = v.inv().countLeadingZeroBits().toUInt()

public fun rustLeadingOnes(v: Long): UInt = v.inv().countLeadingZeroBits().toUInt()

public fun rustLeadingOnes(v: U128): UInt = rustLeadingZeros(v.inv())

public fun rustLeadingOnes(v: I128): UInt = rustLeadingZeros(v.inv())

public fun rustTrailingOnes(v: UByte): UInt = rustTrailingZeros(v.inv())

public fun rustTrailingOnes(v: Byte): UInt = rustTrailingZeros(v.toUByte().inv())

public fun rustTrailingOnes(v: UShort): UInt = rustTrailingZeros(v.inv())

public fun rustTrailingOnes(v: Short): UInt = rustTrailingZeros(v.toUShort().inv())

public fun rustTrailingOnes(v: UInt): UInt = v.inv().countTrailingZeroBits().toUInt()

public fun rustTrailingOnes(v: Int): UInt = v.inv().countTrailingZeroBits().toUInt()

public fun rustTrailingOnes(v: ULong): UInt = v.inv().countTrailingZeroBits().toUInt()

public fun rustTrailingOnes(v: Long): UInt = v.inv().countTrailingZeroBits().toUInt()

public fun rustTrailingOnes(v: U128): UInt = rustTrailingZeros(v.inv())

public fun rustTrailingOnes(v: I128): UInt = rustTrailingZeros(v.inv())

public fun rustRotateLeft(v: UByte, n: UInt): UByte {
    val s = (n and 7u).toInt()
    val x = v.toInt() and 0xFF
    return if (s == 0) v else (((x shl s) or (x ushr (8 - s))) and 0xFF).toUByte()
}

public fun rustRotateLeft(v: Byte, n: UInt): Byte = rustRotateLeft(v.toUByte(), n).toByte()

public fun rustRotateLeft(v: UShort, n: UInt): UShort {
    val s = (n and 15u).toInt()
    val x = v.toInt() and 0xFFFF
    return if (s == 0) v else (((x shl s) or (x ushr (16 - s))) and 0xFFFF).toUShort()
}

public fun rustRotateLeft(v: Short, n: UInt): Short = rustRotateLeft(v.toUShort(), n).toShort()

public fun rustRotateLeft(v: UInt, n: UInt): UInt = v.rotateLeft((n and 31u).toInt())

public fun rustRotateLeft(v: Int, n: UInt): Int = v.rotateLeft((n and 31u).toInt())

public fun rustRotateLeft(v: ULong, n: UInt): ULong = v.rotateLeft((n and 63u).toInt())

public fun rustRotateLeft(v: Long, n: UInt): Long = v.rotateLeft((n and 63u).toInt())

public fun rustRotateLeft(v: U128, n: UInt): U128 {
    val s = (n and 127u).toInt()
    return if (s == 0) v else (v shl s) or (v shr (128 - s))
}

public fun rustRotateLeft(v: I128, n: UInt): I128 = rustRotateLeft(v.toU128(), n).toI128()

public fun rustRotateRight(v: UByte, n: UInt): UByte {
    val s = (n and 7u).toInt()
    val x = v.toInt() and 0xFF
    return if (s == 0) v else (((x ushr s) or (x shl (8 - s))) and 0xFF).toUByte()
}

public fun rustRotateRight(v: Byte, n: UInt): Byte = rustRotateRight(v.toUByte(), n).toByte()

public fun rustRotateRight(v: UShort, n: UInt): UShort {
    val s = (n and 15u).toInt()
    val x = v.toInt() and 0xFFFF
    return if (s == 0) v else (((x ushr s) or (x shl (16 - s))) and 0xFFFF).toUShort()
}

public fun rustRotateRight(v: Short, n: UInt): Short = rustRotateRight(v.toUShort(), n).toShort()

public fun rustRotateRight(v: UInt, n: UInt): UInt = v.rotateRight((n and 31u).toInt())

public fun rustRotateRight(v: Int, n: UInt): Int = v.rotateRight((n and 31u).toInt())

public fun rustRotateRight(v: ULong, n: UInt): ULong = v.rotateRight((n and 63u).toInt())

public fun rustRotateRight(v: Long, n: UInt): Long = v.rotateRight((n and 63u).toInt())

public fun rustRotateRight(v: U128, n: UInt): U128 {
    val s = (n and 127u).toInt()
    return if (s == 0) v else (v shr s) or (v shl (128 - s))
}

public fun rustRotateRight(v: I128, n: UInt): I128 = rustRotateRight(v.toU128(), n).toI128()

// --- Integer pow / checkedPow ---

public fun rustPow(v: Byte, exp: UInt): Byte = rustPow(v.toInt(), exp).toByte()

public fun rustPow(v: UByte, exp: UInt): UByte = rustPow(v.toUInt(), exp).toUByte()

public fun rustPow(v: Short, exp: UInt): Short = rustPow(v.toInt(), exp).toShort()

public fun rustPow(v: UShort, exp: UInt): UShort = rustPow(v.toUInt(), exp).toUShort()

public fun rustPow(v: Int, exp: UInt): Int {
    var acc = 1
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= base
        e = e shr 1
        if (e > 0u) base *= base
    }
    return acc
}

public fun rustPow(v: UInt, exp: UInt): UInt {
    var acc = 1u
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= base
        e = e shr 1
        if (e > 0u) base *= base
    }
    return acc
}

public fun rustPow(v: Long, exp: UInt): Long {
    var acc = 1L
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= base
        e = e shr 1
        if (e > 0u) base *= base
    }
    return acc
}

public fun rustPow(v: ULong, exp: UInt): ULong {
    var acc = 1uL
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= base
        e = e shr 1
        if (e > 0u) base *= base
    }
    return acc
}

public fun rustPow(v: U128, exp: UInt): U128 {
    var acc = U128.ONE
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= base
        e = e shr 1
        if (e > 0u) base *= base
    }
    return acc
}

public fun rustPow(v: I128, exp: UInt): I128 {
    var acc = I128.ONE
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= base
        e = e shr 1
        if (e > 0u) base *= base
    }
    return acc
}

public fun rustCheckedPow(v: Byte, exp: UInt): Byte? {
    var acc: Byte = 1
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: UByte, exp: UInt): UByte? {
    var acc: UByte = 1u
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: Short, exp: UInt): Short? {
    var acc: Short = 1
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: UShort, exp: UInt): UShort? {
    var acc: UShort = 1u
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: Int, exp: UInt): Int? {
    var acc = 1
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: UInt, exp: UInt): UInt? {
    var acc = 1u
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: Long, exp: UInt): Long? {
    var acc = 1L
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: ULong, exp: UInt): ULong? {
    var acc = 1uL
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: U128, exp: UInt): U128? {
    var acc = U128.ONE
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

public fun rustCheckedPow(v: I128, exp: UInt): I128? {
    var acc = I128.ONE
    var base = v
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = rustCheckedMul(acc, base) ?: return null
        e = e shr 1
        if (e > 0u) base = rustCheckedMul(base, base) ?: return null
    }
    return acc
}

// --- Radix parsing (`core::num::from_str_radix`) ---

private fun parseUnsignedRadixULong(
    src: String,
    radix: UInt,
    maxVal: ULong,
): Result<ULong, ParseIntError> {
    require(radix in 2u..36u) { "fromStrRadix: radix must lie in the range 2..36, found $radix" }
    if (src.isEmpty()) return Result.Err(ParseIntError(IntErrorKind.Empty))
    val digits =
        when (src[0]) {
            '+' -> {
                if (src.length == 1) return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
                src.substring(1)
            }
            else -> src
        }
    val radixInt = radix.toInt()
    val radixULong = radix.toULong()
    var acc = 0uL
    var overflowed = false
    for (i in 0 until digits.length) {
        val d =
            digits[i].digitToIntOrNull(radixInt)
                ?: return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
        if (!overflowed) {
            val dU = d.toULong()
            if (acc > (maxVal - dU) / radixULong) {
                overflowed = true
            } else {
                acc = acc * radixULong + dU
            }
        }
    }
    return if (overflowed) {
        Result.Err(ParseIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok(acc)
    }
}

private fun parseSignedRadixLong(
    src: String,
    radix: UInt,
    minVal: Long,
    maxVal: Long,
): Result<Long, ParseIntError> {
    require(radix in 2u..36u) { "fromStrRadix: radix must lie in the range 2..36, found $radix" }
    if (src.isEmpty()) return Result.Err(ParseIntError(IntErrorKind.Empty))
    var isNegative = false
    val digits =
        when (src[0]) {
            '-' -> {
                isNegative = true
                if (src.length == 1) return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
                src.substring(1)
            }
            '+' -> {
                if (src.length == 1) return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
                src.substring(1)
            }
            else -> src
        }
    val radixInt = radix.toInt()
    val radixULong = radix.toULong()
    val maxMag = if (isNegative) (0uL - minVal.toULong()) else maxVal.toULong()
    var acc = 0uL
    var overflowed = false
    for (i in 0 until digits.length) {
        val d =
            digits[i].digitToIntOrNull(radixInt)
                ?: return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
        if (!overflowed) {
            val dU = d.toULong()
            if (acc > (maxMag - dU) / radixULong) {
                overflowed = true
            } else {
                acc = acc * radixULong + dU
            }
        }
    }
    if (overflowed) {
        val kind = if (isNegative) IntErrorKind.NegOverflow else IntErrorKind.PosOverflow
        return Result.Err(ParseIntError(kind))
    }
    return Result.Ok(if (isNegative) (0uL - acc).toLong() else acc.toLong())
}

public fun rustFromStrRadixByte(src: String, radix: UInt): Result<Byte, ParseIntError> =
    parseSignedRadixLong(src, radix, Byte.MIN_VALUE.toLong(), Byte.MAX_VALUE.toLong()).map { it.toByte() }

public fun rustFromStrRadixShort(src: String, radix: UInt): Result<Short, ParseIntError> =
    parseSignedRadixLong(src, radix, Short.MIN_VALUE.toLong(), Short.MAX_VALUE.toLong()).map { it.toShort() }

public fun rustFromStrRadixInt(src: String, radix: UInt): Result<Int, ParseIntError> =
    parseSignedRadixLong(src, radix, Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).map { it.toInt() }

public fun rustFromStrRadixLong(src: String, radix: UInt): Result<Long, ParseIntError> =
    parseSignedRadixLong(src, radix, Long.MIN_VALUE, Long.MAX_VALUE)

public fun rustFromStrRadixUByte(src: String, radix: UInt): Result<UByte, ParseIntError> =
    parseUnsignedRadixULong(src, radix, UByte.MAX_VALUE.toULong()).map { it.toUByte() }

public fun rustFromStrRadixUShort(src: String, radix: UInt): Result<UShort, ParseIntError> =
    parseUnsignedRadixULong(src, radix, UShort.MAX_VALUE.toULong()).map { it.toUShort() }

public fun rustFromStrRadixUInt(src: String, radix: UInt): Result<UInt, ParseIntError> =
    parseUnsignedRadixULong(src, radix, UInt.MAX_VALUE.toULong()).map { it.toUInt() }

public fun rustFromStrRadixULong(src: String, radix: UInt): Result<ULong, ParseIntError> =
    parseUnsignedRadixULong(src, radix, ULong.MAX_VALUE)

public fun rustFromStrRadixU128(src: String, radix: UInt): Result<U128, ParseIntError> {
    require(radix in 2u..36u) { "fromStrRadix: radix must lie in the range 2..36, found $radix" }
    if (src.isEmpty()) return Result.Err(ParseIntError(IntErrorKind.Empty))
    val digits =
        when (src[0]) {
            '+' -> {
                if (src.length == 1) return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
                src.substring(1)
            }
            else -> src
        }
    val radixInt = radix.toInt()
    val radixU128 = U128.fromInt(radixInt)
    var acc = U128.ZERO
    var overflowed = false
    for (i in 0 until digits.length) {
        val d =
            digits[i].digitToIntOrNull(radixInt)
                ?: return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
        if (!overflowed) {
            val mulRes = rustCheckedMul(acc, radixU128)
            if (mulRes == null) {
                overflowed = true
            } else {
                val addRes = rustCheckedAdd(mulRes, U128.fromInt(d))
                if (addRes == null) {
                    overflowed = true
                } else {
                    acc = addRes
                }
            }
        }
    }
    return if (overflowed) {
        Result.Err(ParseIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok(acc)
    }
}

public fun rustFromStrRadixI128(src: String, radix: UInt): Result<I128, ParseIntError> {
    require(radix in 2u..36u) { "fromStrRadix: radix must lie in the range 2..36, found $radix" }
    if (src.isEmpty()) return Result.Err(ParseIntError(IntErrorKind.Empty))
    var isNegative = false
    val digits =
        when (src[0]) {
            '-' -> {
                isNegative = true
                if (src.length == 1) return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
                src.substring(1)
            }
            '+' -> {
                if (src.length == 1) return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
                src.substring(1)
            }
            else -> src
        }
    val radixInt = radix.toInt()
    val radixU128 = U128.fromInt(radixInt)
    val maxMag = if (isNegative) U128(1uL shl 63, 0uL) else I128.MAX.toU128()
    var mag = U128.ZERO
    var overflowed = false
    for (i in 0 until digits.length) {
        val d =
            digits[i].digitToIntOrNull(radixInt)
                ?: return Result.Err(ParseIntError(IntErrorKind.InvalidDigit))
        if (!overflowed) {
            val mulRes = rustCheckedMul(mag, radixU128)
            if (mulRes == null) {
                overflowed = true
            } else {
                val addRes = rustCheckedAdd(mulRes, U128.fromInt(d))
                if (addRes == null || addRes > maxMag) {
                    overflowed = true
                } else {
                    mag = addRes
                }
            }
        }
    }
    if (overflowed) {
        val kind = if (isNegative) IntErrorKind.NegOverflow else IntErrorKind.PosOverflow
        return Result.Err(ParseIntError(kind))
    }
    return Result.Ok(if (isNegative) -mag.toI128() else mag.toI128())
}

// --- Native-endian byte conversion (`to_ne_bytes`) ---

public fun rustToNeBytes(v: Byte): UByteArray = ubyteArrayOf(v.toUByte())

public fun rustToNeBytes(v: UByte): UByteArray = ubyteArrayOf(v)

public fun rustToNeBytes(v: Short): UByteArray = rustToNeBytes(v.toUShort())

public fun rustToNeBytes(v: UShort): UByteArray =
    ubyteArrayOf(
        (v.toUInt() and 0xFFu).toUByte(),
        ((v.toUInt() shr 8) and 0xFFu).toUByte(),
    )

public fun rustToNeBytes(v: Int): UByteArray = rustToNeBytes(v.toUInt())

public fun rustToNeBytes(v: UInt): UByteArray =
    ubyteArrayOf(
        (v and 0xFFu).toUByte(),
        ((v shr 8) and 0xFFu).toUByte(),
        ((v shr 16) and 0xFFu).toUByte(),
        ((v shr 24) and 0xFFu).toUByte(),
    )

public fun rustToNeBytes(v: Long): UByteArray = rustToNeBytes(v.toULong())

public fun rustToNeBytes(v: ULong): UByteArray =
    ubyteArrayOf(
        (v and 0xFFuL).toUByte(),
        ((v shr 8) and 0xFFuL).toUByte(),
        ((v shr 16) and 0xFFuL).toUByte(),
        ((v shr 24) and 0xFFuL).toUByte(),
        ((v shr 32) and 0xFFuL).toUByte(),
        ((v shr 40) and 0xFFuL).toUByte(),
        ((v shr 48) and 0xFFuL).toUByte(),
        ((v shr 56) and 0xFFuL).toUByte(),
    )

public fun rustToNeBytes(v: Float): UByteArray = rustToNeBytes(v.toRawBits())

public fun rustToNeBytes(v: Double): UByteArray = rustToNeBytes(v.toRawBits())

public fun rustToNeBytes(v: F16): UByteArray = rustToNeBytes(v.toBits())

public fun rustToNeBytes(v: U128): UByteArray = v.toNeBytes()

public fun rustToNeBytes(v: I128): UByteArray = v.toNeBytes()

// --- Float classification, sign, and unchecked unsigned conversion ---

public fun rustFloatToULongUnchecked(v: Float): ULong = rustDoubleToULongUnchecked(v.toDouble())

public fun rustDoubleToULongUnchecked(v: Double): ULong =
    if (v >= 9223372036854775808.0) {
        (v - 9223372036854775808.0).toLong().toULong() or 0x8000000000000000uL
    } else {
        v.toLong().toULong()
    }

public fun rustClassifyFloat(v: Float): FpCategory {
    val bits = v.toRawBits() and 0x7FFFFFFF
    val exp = bits ushr 23
    val mant = bits and 0x007FFFFF
    return when {
        exp == 0xFF -> if (mant == 0) FpCategory.Infinite else FpCategory.Nan
        exp == 0 -> if (mant == 0) FpCategory.Zero else FpCategory.Subnormal
        else -> FpCategory.Normal
    }
}

public fun rustClassifyDouble(v: Double): FpCategory {
    val bits = v.toRawBits() and 0x7FFFFFFFFFFFFFFFL
    val exp = (bits ushr 52).toInt()
    val mant = bits and 0x000FFFFFFFFFFFFFL
    return when {
        exp == 0x7FF -> if (mant == 0L) FpCategory.Infinite else FpCategory.Nan
        exp == 0 -> if (mant == 0L) FpCategory.Zero else FpCategory.Subnormal
        else -> FpCategory.Normal
    }
}

public fun rustIsSignPositive(v: Float): Boolean = (v.toRawBits() and Int.MIN_VALUE) == 0

public fun rustIsSignNegative(v: Float): Boolean = (v.toRawBits() and Int.MIN_VALUE) != 0

public fun rustIsSignPositive(v: Double): Boolean = (v.toRawBits() and Long.MIN_VALUE) == 0L

public fun rustIsSignNegative(v: Double): Boolean = (v.toRawBits() and Long.MIN_VALUE) != 0L
