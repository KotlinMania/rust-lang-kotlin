// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowI16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowI32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowI64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowI8
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmAddWithOverflowU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmBitreverseU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmBitreverseU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmBitreverseU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmBitreverseU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmBswapU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmBswapU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmBswapU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtlzU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtlzU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtlzU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtlzU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtpopU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtpopU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtpopU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtpopU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmCttzU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmCttzU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCttzU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCttzU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmDoubleToU64Unchecked
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloatToU64Unchecked
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowI16
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowI32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowI64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowI8
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMulWithOverflowU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowI16
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowI32
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowI64
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowI8
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowU16
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowU32
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmSubWithOverflowU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Add
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Mul
import io.github.kotlinmania.rustlang.core.intrinsics.kmU128Sub
import io.github.kotlinmania.rustlang.core.num.error.IntErrorKind
import io.github.kotlinmania.rustlang.core.num.error.ParseIntError
import io.github.kotlinmania.rustlang.core.result.Result

public fun repeatU8Int(x: UByte): Int {
    val b = x.toInt() and 0xFF
    return b or (b shl 8) or (b shl 16) or (b shl 24)
}

public fun Int.overflowingAddSigned(rhs: Int): Pair<Int, Boolean> {
    val sum = this + rhs
    val overflow = ((this xor sum) and (rhs xor sum)) < 0
    return Pair(sum, overflow)
}

public fun Int.uncheckedNeg(): Int = -this

public fun Long.overflowingAddSigned(rhs: Long): Pair<Long, Boolean> {
    val sum = this + rhs
    val overflow = ((this xor sum) and (rhs xor sum)) < 0L
    return Pair(sum, overflow)
}

public fun Long.uncheckedNeg(): Long = -this

public fun repeatU8UByte(x: UByte): UByte = x
public fun repeatU8Long(x: UByte): Long =
    (repeatU8Int(x).toLong() and 0xFFFF_FFFFL) or (repeatU8Int(x).toLong() shl 32)
public fun repeatU8ULong(x: UByte): ULong = repeatU8Long(x).toULong()

// ---- Byte ----
public fun Byte.wrappingAdd(rhs: Byte): Byte = (this + rhs).toByte()
public fun Byte.wrappingSub(rhs: Byte): Byte = (this - rhs).toByte()
public fun Byte.wrappingMul(rhs: Byte): Byte = (this * rhs).toByte()
public fun Byte.wrappingNeg(): Byte = (-this).toByte()
public fun Byte.wrappingShl(rhs: UInt): Byte = (this.toInt() shl (rhs.toInt() and 7)).toByte()
public fun Byte.wrappingShr(rhs: UInt): Byte = (this.toInt() shr (rhs.toInt() and 7)).toByte()
public fun Byte.uncheckedSub(rhs: Byte): Byte = (this - rhs).toByte()
public fun Byte.overflowingAdd(rhs: Byte): Pair<Byte, Boolean> = kmAddWithOverflowI8(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Byte.overflowingSub(rhs: Byte): Pair<Byte, Boolean> = kmSubWithOverflowI8(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Byte.overflowingMul(rhs: Byte): Pair<Byte, Boolean> = kmMulWithOverflowI8(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Byte.checkedAdd(rhs: Byte): Byte? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun Byte.checkedSub(rhs: Byte): Byte? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun Byte.checkedMul(rhs: Byte): Byte? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun Byte.checkedDiv(rhs: Byte): Byte? = if (rhs == 0.toByte() || (this == Byte.MIN_VALUE && rhs == (-1).toByte())) null else (this / rhs).toByte()
public fun Byte.checkedRem(rhs: Byte): Byte? = if (rhs == 0.toByte() || (this == Byte.MIN_VALUE && rhs == (-1).toByte())) null else (this % rhs).toByte()
public fun Byte.checkedNeg(): Byte? = if (this == Byte.MIN_VALUE) null else (-this).toByte()
public fun Byte.saturatingAdd(rhs: Byte): Byte = (this.toInt() + rhs.toInt()).coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()
public fun Byte.saturatingSub(rhs: Byte): Byte = (this.toInt() - rhs.toInt()).coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()
public fun Byte.saturatingMul(rhs: Byte): Byte = (this.toInt() * rhs.toInt()).coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()
public fun Byte.countOnes(): UInt = kmCtpopU8(this.toUByte())
public fun Byte.countZeros(): UInt = 8u - countOnes()
public fun Byte.leadingZeros(): UInt = kmCtlzU8(this.toUByte())
public fun Byte.trailingZeros(): UInt = kmCttzU8(this.toUByte())
public fun Byte.leadingOnes(): UInt = kmCtlzU8(this.toUByte().inv())
public fun Byte.trailingOnes(): UInt = kmCttzU8(this.toUByte().inv())
public fun Byte.reverseBits(): Byte = kmBitreverseU8(this.toUByte()).toByte()
public fun Byte.swapBytes(): Byte = this
public fun Byte.absDiff(other: Byte): UByte = (this.toInt() - other.toInt()).let { if (it < 0) (-it) else it }.toUByte()
public fun Byte.isMultipleOf(rhs: Byte): Boolean = if (rhs == 0.toByte()) this == 0.toByte() else !(this == Byte.MIN_VALUE && rhs == (-1).toByte()) && (this % rhs == 0)

// ---- UByte ----
public fun UByte.wrappingAdd(rhs: UByte): UByte = (this + rhs).toUByte()
public fun UByte.wrappingSub(rhs: UByte): UByte = (this - rhs).toUByte()
public fun UByte.wrappingMul(rhs: UByte): UByte = (this * rhs).toUByte()
public fun UByte.wrappingNeg(): UByte = (-(this.toInt())).toUByte()
public fun UByte.wrappingShl(rhs: UInt): UByte = (this.toInt() shl (rhs.toInt() and 7)).toUByte()
public fun UByte.wrappingShr(rhs: UInt): UByte = (this.toInt() ushr (rhs.toInt() and 7)).toUByte()
public fun UByte.uncheckedSub(rhs: UByte): UByte = (this - rhs).toUByte()
public fun UByte.overflowingAdd(rhs: UByte): Pair<UByte, Boolean> = kmAddWithOverflowU8(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UByte.overflowingSub(rhs: UByte): Pair<UByte, Boolean> = kmSubWithOverflowU8(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UByte.overflowingMul(rhs: UByte): Pair<UByte, Boolean> = kmMulWithOverflowU8(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UByte.checkedAdd(rhs: UByte): UByte? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun UByte.checkedSub(rhs: UByte): UByte? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun UByte.checkedMul(rhs: UByte): UByte? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun UByte.checkedDiv(rhs: UByte): UByte? = if (rhs == 0u.toUByte()) null else (this / rhs).toUByte()
public fun UByte.checkedRem(rhs: UByte): UByte? = if (rhs == 0u.toUByte()) null else (this % rhs).toUByte()
public fun UByte.saturatingAdd(rhs: UByte): UByte = (this.toInt() + rhs.toInt()).coerceAtMost(255).toUByte()
public fun UByte.saturatingSub(rhs: UByte): UByte = (this.toInt() - rhs.toInt()).coerceAtLeast(0).toUByte()
public fun UByte.saturatingMul(rhs: UByte): UByte = (this.toInt() * rhs.toInt()).coerceAtMost(255).toUByte()
public fun UByte.countOnes(): UInt = kmCtpopU8(this)
public fun UByte.countZeros(): UInt = 8u - countOnes()
public fun UByte.leadingZeros(): UInt = kmCtlzU8(this)
public fun UByte.trailingZeros(): UInt = kmCttzU8(this)
public fun UByte.leadingOnes(): UInt = kmCtlzU8(this.inv())
public fun UByte.trailingOnes(): UInt = kmCttzU8(this.inv())
public fun UByte.reverseBits(): UByte = kmBitreverseU8(this)
public fun UByte.swapBytes(): UByte = this
public fun UByte.isMultipleOf(rhs: UByte): Boolean = if (rhs == 0u.toUByte()) this == 0u.toUByte() else this % rhs == 0u
public fun UByte.isPowerOfTwo(): Boolean = this != 0u.toUByte() && (this.toInt() and (this.toInt() - 1)) == 0

// ---- Short ----
public fun Short.wrappingAdd(rhs: Short): Short = (this + rhs).toShort()
public fun Short.wrappingSub(rhs: Short): Short = (this - rhs).toShort()
public fun Short.wrappingMul(rhs: Short): Short = (this * rhs).toShort()
public fun Short.wrappingNeg(): Short = (-this).toShort()
public fun Short.wrappingShl(rhs: UInt): Short = (this.toInt() shl (rhs.toInt() and 15)).toShort()
public fun Short.wrappingShr(rhs: UInt): Short = (this.toInt() shr (rhs.toInt() and 15)).toShort()
public fun Short.uncheckedSub(rhs: Short): Short = (this - rhs).toShort()
public fun Short.overflowingAdd(rhs: Short): Pair<Short, Boolean> = kmAddWithOverflowI16(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Short.overflowingSub(rhs: Short): Pair<Short, Boolean> = kmSubWithOverflowI16(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Short.overflowingMul(rhs: Short): Pair<Short, Boolean> = kmMulWithOverflowI16(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Short.checkedAdd(rhs: Short): Short? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun Short.checkedSub(rhs: Short): Short? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun Short.checkedMul(rhs: Short): Short? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun Short.checkedDiv(rhs: Short): Short? = if (rhs == 0.toShort() || (this == Short.MIN_VALUE && rhs == (-1).toShort())) null else (this / rhs).toShort()
public fun Short.checkedRem(rhs: Short): Short? = if (rhs == 0.toShort() || (this == Short.MIN_VALUE && rhs == (-1).toShort())) null else (this % rhs).toShort()
public fun Short.checkedNeg(): Short? = if (this == Short.MIN_VALUE) null else (-this).toShort()
public fun Short.saturatingAdd(rhs: Short): Short = (this.toInt() + rhs.toInt()).coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
public fun Short.saturatingSub(rhs: Short): Short = (this.toInt() - rhs.toInt()).coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
public fun Short.saturatingMul(rhs: Short): Short = (this.toInt() * rhs.toInt()).coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
public fun Short.countOnes(): UInt = kmCtpopU16(this.toUShort())
public fun Short.countZeros(): UInt = 16u - countOnes()
public fun Short.leadingZeros(): UInt = kmCtlzU16(this.toUShort())
public fun Short.trailingZeros(): UInt = kmCttzU16(this.toUShort())
public fun Short.leadingOnes(): UInt = kmCtlzU16(this.toUShort().inv())
public fun Short.trailingOnes(): UInt = kmCttzU16(this.toUShort().inv())
public fun Short.reverseBits(): Short = kmBitreverseU16(this.toUShort()).toShort()
public fun Short.swapBytes(): Short = kmBswapU16(this.toUShort()).toShort()
public fun Short.toBe(): Short = if (kmIsBigEndian()) this else swapBytes()
public fun Short.toLe(): Short = if (kmIsBigEndian()) swapBytes() else this
public fun fromBeShort(x: Short): Short = x.toBe()
public fun fromLeShort(x: Short): Short = x.toLe()
public fun Short.absDiff(other: Short): UShort = (this.toInt() - other.toInt()).let { if (it < 0) -it else it }.toUShort()
public fun Short.isMultipleOf(rhs: Short): Boolean = if (rhs == 0.toShort()) this == 0.toShort() else !(this == Short.MIN_VALUE && rhs == (-1).toShort()) && (this % rhs == 0)

// ---- UShort ----
public fun UShort.wrappingAdd(rhs: UShort): UShort = (this + rhs).toUShort()
public fun UShort.wrappingSub(rhs: UShort): UShort = (this - rhs).toUShort()
public fun UShort.wrappingMul(rhs: UShort): UShort = (this * rhs).toUShort()
public fun UShort.wrappingNeg(): UShort = (-(this.toInt())).toUShort()
public fun UShort.wrappingShl(rhs: UInt): UShort = (this.toInt() shl (rhs.toInt() and 15)).toUShort()
public fun UShort.wrappingShr(rhs: UInt): UShort = (this.toInt() ushr (rhs.toInt() and 15)).toUShort()
public fun UShort.uncheckedSub(rhs: UShort): UShort = (this - rhs).toUShort()
public fun UShort.overflowingAdd(rhs: UShort): Pair<UShort, Boolean> = kmAddWithOverflowU16(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UShort.overflowingSub(rhs: UShort): Pair<UShort, Boolean> = kmSubWithOverflowU16(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UShort.overflowingMul(rhs: UShort): Pair<UShort, Boolean> = kmMulWithOverflowU16(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UShort.checkedAdd(rhs: UShort): UShort? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun UShort.checkedSub(rhs: UShort): UShort? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun UShort.checkedMul(rhs: UShort): UShort? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun UShort.checkedDiv(rhs: UShort): UShort? = if (rhs == 0u.toUShort()) null else (this / rhs).toUShort()
public fun UShort.checkedRem(rhs: UShort): UShort? = if (rhs == 0u.toUShort()) null else (this % rhs).toUShort()
public fun UShort.saturatingAdd(rhs: UShort): UShort = (this.toInt() + rhs.toInt()).coerceAtMost(65535).toUShort()
public fun UShort.saturatingSub(rhs: UShort): UShort = (this.toInt() - rhs.toInt()).coerceAtLeast(0).toUShort()
public fun UShort.saturatingMul(rhs: UShort): UShort = (this.toInt() * rhs.toInt()).coerceAtMost(65535).toUShort()
public fun UShort.countOnes(): UInt = kmCtpopU16(this)
public fun UShort.countZeros(): UInt = 16u - countOnes()
public fun UShort.leadingZeros(): UInt = kmCtlzU16(this)
public fun UShort.trailingZeros(): UInt = kmCttzU16(this)
public fun UShort.leadingOnes(): UInt = kmCtlzU16(this.inv())
public fun UShort.trailingOnes(): UInt = kmCttzU16(this.inv())
public fun UShort.reverseBits(): UShort = kmBitreverseU16(this)
public fun UShort.swapBytes(): UShort = kmBswapU16(this)
public fun UShort.toBe(): UShort = if (kmIsBigEndian()) this else swapBytes()
public fun UShort.toLe(): UShort = if (kmIsBigEndian()) swapBytes() else this
public fun fromBeUShort(x: UShort): UShort = x.toBe()
public fun fromLeUShort(x: UShort): UShort = x.toLe()
public fun UShort.isMultipleOf(rhs: UShort): Boolean = if (rhs == 0u.toUShort()) this == 0u.toUShort() else this % rhs == 0u
public fun UShort.isPowerOfTwo(): Boolean = this != 0u.toUShort() && (this.toInt() and (this.toInt() - 1)) == 0

// ---- Int (Rust i32 / isize / usize mapped in Stage 1) ----
public fun Int.wrappingAdd(rhs: Int): Int = this + rhs
public fun Int.wrappingSub(rhs: Int): Int = this - rhs
public fun Int.wrappingMul(rhs: Int): Int = this * rhs
public fun Int.wrappingNeg(): Int = -this
public fun Int.wrappingShl(rhs: UInt): Int = this shl (rhs.toInt() and 31)
public fun Int.wrappingShr(rhs: UInt): Int = this shr (rhs.toInt() and 31)
public fun Int.uncheckedAdd(rhs: Int): Int = this + rhs
public fun Int.uncheckedSub(rhs: Int): Int = this - rhs
public fun Int.uncheckedMul(rhs: Int): Int = this * rhs
public fun Int.overflowingAdd(rhs: Int): Pair<Int, Boolean> = kmAddWithOverflowI32(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Int.overflowingSub(rhs: Int): Pair<Int, Boolean> = kmSubWithOverflowI32(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Int.overflowingMul(rhs: Int): Pair<Int, Boolean> = kmMulWithOverflowI32(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Int.checkedAdd(rhs: Int): Int? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun Int.checkedSub(rhs: Int): Int? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun Int.checkedMul(rhs: Int): Int? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun Int.checkedDiv(rhs: Int): Int? = if (rhs == 0 || (this == Int.MIN_VALUE && rhs == -1)) null else this / rhs
public fun Int.checkedRem(rhs: Int): Int? = if (rhs == 0 || (this == Int.MIN_VALUE && rhs == -1)) null else this % rhs
public fun Int.checkedNeg(): Int? = if (this == Int.MIN_VALUE) null else -this
public fun Int.checkedNextPowerOfTwo(): Int? {
    if (this <= 0) return 1
    if (this > 0x4000_0000) return null
    val p = 32 - (this - 1).countLeadingZeroBits()
    return 1 shl p
}
public fun Int.nextPowerOfTwo(): Int = checkedNextPowerOfTwo() ?: error("nextPowerOfTwo overflow for $this")
public fun Int.isPowerOfTwo(): Boolean = this > 0 && (this and (this - 1)) == 0
public fun Int.saturatingAdd(rhs: Int): Int = (this.toLong() + rhs.toLong()).coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()
public fun Int.saturatingSub(rhs: Int): Int = (this.toLong() - rhs.toLong()).coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()
public fun Int.saturatingMul(rhs: Int): Int = (this.toLong() * rhs.toLong()).coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()
public fun Int.countOnes(): UInt = kmCtpopU32(this.toUInt())
public fun Int.countZeros(): UInt = 32u - countOnes()
public fun Int.leadingZeros(): UInt = kmCtlzU32(this.toUInt())
public fun Int.trailingZeros(): UInt = kmCttzU32(this.toUInt())
public fun Int.leadingOnes(): UInt = kmCtlzU32(this.toUInt().inv())
public fun Int.trailingOnes(): UInt = kmCttzU32(this.toUInt().inv())
public fun Int.reverseBits(): Int = kmBitreverseU32(this.toUInt()).toInt()
public fun Int.swapBytes(): Int = kmBswapU32(this.toUInt()).toInt()
public fun Int.toBe(): Int = if (kmIsBigEndian()) this else swapBytes()
public fun Int.toLe(): Int = if (kmIsBigEndian()) swapBytes() else this
public fun fromBeInt(x: Int): Int = x.toBe()
public fun fromLeInt(x: Int): Int = x.toLe()
public fun Int.rotateLeft(n: UInt): Int = this.rotateLeft(n.toInt())
public fun Int.rotateRight(n: UInt): Int = this.rotateRight(n.toInt())
public fun Int.midpoint(rhs: Int): Int = ((this and rhs) + ((this xor rhs) shr 1))
public fun Int.absDiff(other: Int): UInt {
    val diff = this.toLong() - other.toLong()
    return (if (diff < 0L) -diff else diff).toUInt()
}
public fun Int.isMultipleOf(rhs: Int): Boolean = if (rhs == 0) this == 0 else !(this == Int.MIN_VALUE && rhs == -1) && (this % rhs == 0)
public fun Int.ilog10(): UInt {
    check(this > 0) { "ilog10 requires positive argument" }
    var v = this
    var r = 0u
    while (v >= 10) {
        v /= 10
        r += 1u
    }
    return r
}

// ---- UInt ----
public fun UInt.wrappingAdd(rhs: UInt): UInt = this + rhs
public fun UInt.wrappingSub(rhs: UInt): UInt = this - rhs
public fun UInt.wrappingMul(rhs: UInt): UInt = this * rhs
public fun UInt.wrappingNeg(): UInt = 0u - this
public fun UInt.wrappingShl(rhs: UInt): UInt = this shl (rhs.toInt() and 31)
public fun UInt.wrappingShr(rhs: UInt): UInt = this shr (rhs.toInt() and 31)
public fun UInt.uncheckedAdd(rhs: UInt): UInt = this + rhs
public fun UInt.uncheckedSub(rhs: UInt): UInt = this - rhs
public fun UInt.uncheckedMul(rhs: UInt): UInt = this * rhs
public fun UInt.overflowingAdd(rhs: UInt): Pair<UInt, Boolean> = kmAddWithOverflowU32(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UInt.overflowingSub(rhs: UInt): Pair<UInt, Boolean> = kmSubWithOverflowU32(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UInt.overflowingMul(rhs: UInt): Pair<UInt, Boolean> = kmMulWithOverflowU32(this, rhs).let { Pair(it.value, it.overflowed) }
public fun UInt.checkedAdd(rhs: UInt): UInt? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun UInt.checkedSub(rhs: UInt): UInt? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun UInt.checkedMul(rhs: UInt): UInt? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun UInt.checkedDiv(rhs: UInt): UInt? = if (rhs == 0u) null else this / rhs
public fun UInt.checkedRem(rhs: UInt): UInt? = if (rhs == 0u) null else this % rhs
public fun UInt.checkedNextPowerOfTwo(): UInt? {
    if (this <= 1u) return 1u
    if (this > 0x8000_0000u) return null
    val p = 32 - (this - 1u).countLeadingZeroBits()
    return 1u shl p
}
public fun UInt.nextPowerOfTwo(): UInt = checkedNextPowerOfTwo() ?: error("nextPowerOfTwo overflow for $this")
public fun UInt.isPowerOfTwo(): Boolean = this != 0u && (this and (this - 1u)) == 0u
public fun UInt.saturatingAdd(rhs: UInt): UInt = (this.toULong() + rhs.toULong()).coerceAtMost(UInt.MAX_VALUE.toULong()).toUInt()
public fun UInt.saturatingSub(rhs: UInt): UInt = if (this > rhs) this - rhs else 0u
public fun UInt.saturatingMul(rhs: UInt): UInt = (this.toULong() * rhs.toULong()).coerceAtMost(UInt.MAX_VALUE.toULong()).toUInt()
public fun UInt.countOnes(): UInt = kmCtpopU32(this)
public fun UInt.countZeros(): UInt = 32u - countOnes()
public fun UInt.leadingZeros(): UInt = kmCtlzU32(this)
public fun UInt.trailingZeros(): UInt = kmCttzU32(this)
public fun UInt.leadingOnes(): UInt = kmCtlzU32(this.inv())
public fun UInt.trailingOnes(): UInt = kmCttzU32(this.inv())
public fun UInt.reverseBits(): UInt = kmBitreverseU32(this)
public fun UInt.swapBytes(): UInt = kmBswapU32(this)
public fun UInt.toBe(): UInt = if (kmIsBigEndian()) this else swapBytes()
public fun UInt.toLe(): UInt = if (kmIsBigEndian()) swapBytes() else this
public fun fromBeUInt(x: UInt): UInt = x.toBe()
public fun fromLeUInt(x: UInt): UInt = x.toLe()
public fun UInt.rotateLeft(n: UInt): UInt = this.rotateLeft(n.toInt())
public fun UInt.rotateRight(n: UInt): UInt = this.rotateRight(n.toInt())
public fun UInt.midpoint(rhs: UInt): UInt = ((this and rhs) + ((this xor rhs) shr 1))
public fun UInt.isMultipleOf(rhs: UInt): Boolean = if (rhs == 0u) this == 0u else this % rhs == 0u
public fun UInt.ilog10(): UInt {
    check(this > 0u) { "ilog10 requires positive argument" }
    var v = this
    var r = 0u
    while (v >= 10u) {
        v /= 10u
        r += 1u
    }
    return r
}

// ---- Long ----
public fun Long.wrappingAdd(rhs: Long): Long = this + rhs
public fun Long.wrappingSub(rhs: Long): Long = this - rhs
public fun Long.wrappingMul(rhs: Long): Long = this * rhs
public fun Long.wrappingNeg(): Long = -this
public fun Long.wrappingShl(rhs: UInt): Long = this shl (rhs.toInt() and 63)
public fun Long.wrappingShr(rhs: UInt): Long = this shr (rhs.toInt() and 63)
public fun Long.uncheckedAdd(rhs: Long): Long = this + rhs
public fun Long.uncheckedSub(rhs: Long): Long = this - rhs
public fun Long.uncheckedMul(rhs: Long): Long = this * rhs
public fun Long.overflowingAdd(rhs: Long): Pair<Long, Boolean> = kmAddWithOverflowI64(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Long.overflowingSub(rhs: Long): Pair<Long, Boolean> = kmSubWithOverflowI64(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Long.overflowingMul(rhs: Long): Pair<Long, Boolean> = kmMulWithOverflowI64(this, rhs).let { Pair(it.value, it.overflowed) }
public fun Long.checkedAdd(rhs: Long): Long? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun Long.checkedSub(rhs: Long): Long? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun Long.checkedMul(rhs: Long): Long? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun Long.checkedDiv(rhs: Long): Long? = if (rhs == 0L || (this == Long.MIN_VALUE && rhs == -1L)) null else this / rhs
public fun Long.checkedRem(rhs: Long): Long? = if (rhs == 0L || (this == Long.MIN_VALUE && rhs == -1L)) null else this % rhs
public fun Long.checkedNeg(): Long? = if (this == Long.MIN_VALUE) null else -this
public fun Long.saturatingAdd(rhs: Long): Long =
    overflowingAdd(rhs).let { (v, ovf) ->
        if (!ovf) {
            v
        } else if (this < 0L) {
            Long.MIN_VALUE
        } else {
            Long.MAX_VALUE
        }
    }
public fun Long.saturatingSub(rhs: Long): Long =
    overflowingSub(rhs).let { (v, ovf) ->
        if (!ovf) {
            v
        } else if (this < 0L) {
            Long.MIN_VALUE
        } else {
            Long.MAX_VALUE
        }
    }
public fun Long.saturatingMul(rhs: Long): Long =
    overflowingMul(rhs).let { (v, ovf) ->
        if (!ovf) {
            v
        } else if ((this < 0L) xor (rhs < 0L)) {
            Long.MIN_VALUE
        } else {
            Long.MAX_VALUE
        }
    }
public fun Long.countOnes(): UInt = kmCtpopU64(this.toULong())
public fun Long.countZeros(): UInt = 64u - countOnes()
public fun Long.leadingZeros(): UInt = kmCtlzU64(this.toULong())
public fun Long.trailingZeros(): UInt = kmCttzU64(this.toULong())
public fun Long.leadingOnes(): UInt = kmCtlzU64(this.toULong().inv())
public fun Long.trailingOnes(): UInt = kmCttzU64(this.toULong().inv())
public fun Long.reverseBits(): Long = kmBitreverseU64(this.toULong()).toLong()
public fun Long.swapBytes(): Long = kmBswapU64(this.toULong()).toLong()
public fun Long.toBe(): Long = if (kmIsBigEndian()) this else swapBytes()
public fun Long.toLe(): Long = if (kmIsBigEndian()) swapBytes() else this
public fun fromBeLong(x: Long): Long = x.toBe()
public fun fromLeLong(x: Long): Long = x.toLe()
public fun Long.rotateLeft(n: UInt): Long = this.rotateLeft(n.toInt())
public fun Long.rotateRight(n: UInt): Long = this.rotateRight(n.toInt())
public fun Long.midpoint(rhs: Long): Long = ((this and rhs) + ((this xor rhs) shr 1))
public fun Long.isMultipleOf(rhs: Long): Boolean = if (rhs == 0L) this == 0L else !(this == Long.MIN_VALUE && rhs == -1L) && (this % rhs == 0L)

// ---- ULong ----
public fun ULong.wrappingAdd(rhs: ULong): ULong = this + rhs
public fun ULong.wrappingSub(rhs: ULong): ULong = this - rhs
public fun ULong.wrappingMul(rhs: ULong): ULong = this * rhs
public fun ULong.wrappingNeg(): ULong = 0uL - this
public fun ULong.wrappingShl(rhs: UInt): ULong = this shl (rhs.toInt() and 63)
public fun ULong.wrappingShr(rhs: UInt): ULong = this shr (rhs.toInt() and 63)
public fun ULong.uncheckedAdd(rhs: ULong): ULong = this + rhs
public fun ULong.uncheckedSub(rhs: ULong): ULong = this - rhs
public fun ULong.uncheckedMul(rhs: ULong): ULong = this * rhs
public fun ULong.overflowingAdd(rhs: ULong): Pair<ULong, Boolean> = kmAddWithOverflowU64(this, rhs).let { Pair(it.value, it.overflowed) }
public fun ULong.overflowingSub(rhs: ULong): Pair<ULong, Boolean> = kmSubWithOverflowU64(this, rhs).let { Pair(it.value, it.overflowed) }
public fun ULong.overflowingMul(rhs: ULong): Pair<ULong, Boolean> = kmMulWithOverflowU64(this, rhs).let { Pair(it.value, it.overflowed) }
public fun ULong.checkedAdd(rhs: ULong): ULong? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun ULong.checkedSub(rhs: ULong): ULong? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun ULong.checkedMul(rhs: ULong): ULong? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun ULong.checkedDiv(rhs: ULong): ULong? = if (rhs == 0uL) null else this / rhs
public fun ULong.checkedRem(rhs: ULong): ULong? = if (rhs == 0uL) null else this % rhs
public fun ULong.saturatingAdd(rhs: ULong): ULong = overflowingAdd(rhs).let { if (it.second) ULong.MAX_VALUE else it.first }
public fun ULong.saturatingSub(rhs: ULong): ULong = if (this > rhs) this - rhs else 0uL
public fun ULong.saturatingMul(rhs: ULong): ULong = overflowingMul(rhs).let { if (it.second) ULong.MAX_VALUE else it.first }
public fun ULong.countOnes(): UInt = kmCtpopU64(this)
public fun ULong.countZeros(): UInt = 64u - countOnes()
public fun ULong.leadingZeros(): UInt = kmCtlzU64(this)
public fun ULong.trailingZeros(): UInt = kmCttzU64(this)
public fun ULong.leadingOnes(): UInt = kmCtlzU64(this.inv())
public fun ULong.trailingOnes(): UInt = kmCttzU64(this.inv())
public fun ULong.reverseBits(): ULong = kmBitreverseU64(this)
public fun ULong.swapBytes(): ULong = kmBswapU64(this)
public fun ULong.toBe(): ULong = if (kmIsBigEndian()) this else swapBytes()
public fun ULong.toLe(): ULong = if (kmIsBigEndian()) swapBytes() else this
public fun fromBeULong(x: ULong): ULong = x.toBe()
public fun fromLeULong(x: ULong): ULong = x.toLe()
public fun ULong.rotateLeft(n: UInt): ULong = this.rotateLeft(n.toInt())
public fun ULong.rotateRight(n: UInt): ULong = this.rotateRight(n.toInt())
public fun ULong.midpoint(rhs: ULong): ULong = ((this and rhs) + ((this xor rhs) shr 1))
public fun ULong.isMultipleOf(rhs: ULong): Boolean = if (rhs == 0uL) this == 0uL else this % rhs == 0uL
public fun ULong.checkedNextPowerOfTwo(): ULong? {
    if (this <= 1uL) return 1uL
    if (this > 0x8000_0000_0000_0000uL) return null
    val p = 64 - (this - 1uL).countLeadingZeroBits()
    return 1uL shl p
}
public fun ULong.nextPowerOfTwo(): ULong = checkedNextPowerOfTwo() ?: error("nextPowerOfTwo overflow for $this")
public fun ULong.isPowerOfTwo(): Boolean = this != 0uL && (this and (this - 1uL)) == 0uL

// ---- Endianness helper (all retained KMP targets are little-endian) ----
private fun kmIsBigEndian(): Boolean = false

// ---- 128-bit U128 / I128 checked/wrapping/overflowing helpers ----
public fun U128.wrappingAdd(rhs: U128): U128 = this + rhs
public fun U128.wrappingSub(rhs: U128): U128 = this - rhs
public fun U128.wrappingMul(rhs: U128): U128 = this * rhs
public fun U128.wrappingNeg(): U128 = U128.ZERO - this

public fun U128.overflowingAdd(rhs: U128): Pair<U128, Boolean> {
    val out = kmU128Add(this.low, this.high, rhs.low, rhs.high)
    val sum = U128(out.hi, out.lo)
    return Pair(sum, sum < this)
}

public fun U128.overflowingSub(rhs: U128): Pair<U128, Boolean> {
    val out = kmU128Sub(this.low, this.high, rhs.low, rhs.high)
    return Pair(U128(out.hi, out.lo), this < rhs)
}

public fun U128.overflowingMul(rhs: U128): Pair<U128, Boolean> {
    val out = kmU128Mul(this.low, this.high, rhs.low, rhs.high)
    val prod = U128(out.hi, out.lo)
    val overflow = this != U128.ZERO && rhs != U128.ZERO && (prod / this != rhs || (this.high != 0uL && rhs.high != 0uL))
    return Pair(prod, overflow)
}

public fun U128.checkedAdd(rhs: U128): U128? = overflowingAdd(rhs).let { if (it.second) null else it.first }
public fun U128.checkedSub(rhs: U128): U128? = overflowingSub(rhs).let { if (it.second) null else it.first }
public fun U128.checkedMul(rhs: U128): U128? = overflowingMul(rhs).let { if (it.second) null else it.first }
public fun U128.checkedDiv(rhs: U128): U128? = if (rhs == U128.ZERO) null else this / rhs

public fun I128.wrappingAdd(rhs: I128): I128 = this + rhs
public fun I128.wrappingSub(rhs: I128): I128 = this - rhs
public fun I128.wrappingMul(rhs: I128): I128 = this * rhs
public fun I128.wrappingNeg(): I128 = -this
public fun I128.checkedNeg(): I128? = if (this.high == Long.MIN_VALUE && this.low == 0uL) null else -this

public fun I128.checkedAdd(rhs: I128): I128? {
    val sum = this + rhs
    val ovf = ((this.high < 0L) == (rhs.high < 0L)) && ((sum.high < 0L) != (this.high < 0L))
    return if (ovf) null else sum
}

public fun I128.checkedSub(rhs: I128): I128? {
    val diff = this - rhs
    val ovf = ((this.high < 0L) != (rhs.high < 0L)) && ((diff.high < 0L) != (this.high < 0L))
    return if (ovf) null else diff
}

public fun I128.checkedMul(rhs: I128): I128? {
    if (this == I128.ZERO || rhs == I128.ZERO) return I128.ZERO
    if ((this == I128.MIN && rhs == I128.fromInt(-1)) || (rhs == I128.MIN && this == I128.fromInt(-1))) return null
    val neg = (this.high < 0L) xor (rhs.high < 0L)
    val absA = if (this.high < 0L) (-this) else this
    val absB = if (rhs.high < 0L) (-rhs) else rhs
    val (uProd, uOvf) = U128(absA.high.toULong(), absA.low).overflowingMul(U128(absB.high.toULong(), absB.low))
    if (uOvf) return null
    val maxMag = if (neg) U128(0x8000_0000_0000_0000uL, 0uL) else U128(0x7FFF_FFFF_FFFF_FFFFuL, ULong.MAX_VALUE)
    if (uProd > maxMag) return null
    val raw = I128(uProd.high.toLong(), uProd.low)
    return if (neg) -raw else raw
}

public fun I128.checkedDiv(rhs: I128): I128? {
    if (rhs == I128.ZERO) return null
    if (this == I128.MIN && rhs == I128.fromInt(-1)) return null
    return this / rhs
}

// ---- `rust*` downstream helpers for byteorder / memchr / num-traits / Float / Double ----
public fun rustWrappingAdd(a: Byte, b: Byte): Byte = a.wrappingAdd(b)
public fun rustWrappingAdd(a: UByte, b: UByte): UByte = a.wrappingAdd(b)
public fun rustWrappingAdd(a: Short, b: Short): Short = a.wrappingAdd(b)
public fun rustWrappingAdd(a: UShort, b: UShort): UShort = a.wrappingAdd(b)
public fun rustWrappingAdd(a: Int, b: Int): Int = a.wrappingAdd(b)
public fun rustWrappingAdd(a: UInt, b: UInt): UInt = a.wrappingAdd(b)
public fun rustWrappingAdd(a: Long, b: Long): Long = a.wrappingAdd(b)
public fun rustWrappingAdd(a: ULong, b: ULong): ULong = a.wrappingAdd(b)
public fun rustWrappingAdd(a: I128, b: I128): I128 = a.wrappingAdd(b)
public fun rustWrappingAdd(a: U128, b: U128): U128 = a.wrappingAdd(b)

public fun rustWrappingSub(a: Byte, b: Byte): Byte = a.wrappingSub(b)
public fun rustWrappingSub(a: UByte, b: UByte): UByte = a.wrappingSub(b)
public fun rustWrappingSub(a: Short, b: Short): Short = a.wrappingSub(b)
public fun rustWrappingSub(a: UShort, b: UShort): UShort = a.wrappingSub(b)
public fun rustWrappingSub(a: Int, b: Int): Int = a.wrappingSub(b)
public fun rustWrappingSub(a: UInt, b: UInt): UInt = a.wrappingSub(b)
public fun rustWrappingSub(a: Long, b: Long): Long = a.wrappingSub(b)
public fun rustWrappingSub(a: ULong, b: ULong): ULong = a.wrappingSub(b)
public fun rustWrappingSub(a: I128, b: I128): I128 = a.wrappingSub(b)
public fun rustWrappingSub(a: U128, b: U128): U128 = a.wrappingSub(b)

public fun rustWrappingMul(a: Byte, b: Byte): Byte = a.wrappingMul(b)
public fun rustWrappingMul(a: UByte, b: UByte): UByte = a.wrappingMul(b)
public fun rustWrappingMul(a: Short, b: Short): Short = a.wrappingMul(b)
public fun rustWrappingMul(a: UShort, b: UShort): UShort = a.wrappingMul(b)
public fun rustWrappingMul(a: Int, b: Int): Int = a.wrappingMul(b)
public fun rustWrappingMul(a: UInt, b: UInt): UInt = a.wrappingMul(b)
public fun rustWrappingMul(a: Long, b: Long): Long = a.wrappingMul(b)
public fun rustWrappingMul(a: ULong, b: ULong): ULong = a.wrappingMul(b)
public fun rustWrappingMul(a: I128, b: I128): I128 = a.wrappingMul(b)
public fun rustWrappingMul(a: U128, b: U128): U128 = a.wrappingMul(b)

public fun rustWrappingNeg(a: Byte): Byte = a.wrappingNeg()
public fun rustWrappingNeg(a: UByte): UByte = a.wrappingNeg()
public fun rustWrappingNeg(a: Short): Short = a.wrappingNeg()
public fun rustWrappingNeg(a: UShort): UShort = a.wrappingNeg()
public fun rustWrappingNeg(a: Int): Int = a.wrappingNeg()
public fun rustWrappingNeg(a: UInt): UInt = a.wrappingNeg()
public fun rustWrappingNeg(a: Long): Long = a.wrappingNeg()
public fun rustWrappingNeg(a: ULong): ULong = a.wrappingNeg()
public fun rustWrappingNeg(a: I128): I128 = a.wrappingNeg()
public fun rustWrappingNeg(a: U128): U128 = a.wrappingNeg()

public fun rustWrappingShl(a: Byte, b: UInt): Byte = a.wrappingShl(b)
public fun rustWrappingShl(a: UByte, b: UInt): UByte = a.wrappingShl(b)
public fun rustWrappingShl(a: Short, b: UInt): Short = a.wrappingShl(b)
public fun rustWrappingShl(a: UShort, b: UInt): UShort = a.wrappingShl(b)
public fun rustWrappingShl(a: Int, b: UInt): Int = a.wrappingShl(b)
public fun rustWrappingShl(a: UInt, b: UInt): UInt = a.wrappingShl(b)
public fun rustWrappingShl(a: Long, b: UInt): Long = a.wrappingShl(b)
public fun rustWrappingShl(a: ULong, b: UInt): ULong = a.wrappingShl(b)

public fun rustWrappingShr(a: Byte, b: UInt): Byte = a.wrappingShr(b)
public fun rustWrappingShr(a: UByte, b: UInt): UByte = a.wrappingShr(b)
public fun rustWrappingShr(a: Short, b: UInt): Short = a.wrappingShr(b)
public fun rustWrappingShr(a: UShort, b: UInt): UShort = a.wrappingShr(b)
public fun rustWrappingShr(a: Int, b: UInt): Int = a.wrappingShr(b)
public fun rustWrappingShr(a: UInt, b: UInt): UInt = a.wrappingShr(b)
public fun rustWrappingShr(a: Long, b: UInt): Long = a.wrappingShr(b)
public fun rustWrappingShr(a: ULong, b: UInt): ULong = a.wrappingShr(b)

public fun rustCheckedAdd(a: Byte, b: Byte): Byte? = a.checkedAdd(b)
public fun rustCheckedAdd(a: UByte, b: UByte): UByte? = a.checkedAdd(b)
public fun rustCheckedAdd(a: Short, b: Short): Short? = a.checkedAdd(b)
public fun rustCheckedAdd(a: UShort, b: UShort): UShort? = a.checkedAdd(b)
public fun rustCheckedAdd(a: Int, b: Int): Int? = a.checkedAdd(b)
public fun rustCheckedAdd(a: UInt, b: UInt): UInt? = a.checkedAdd(b)
public fun rustCheckedAdd(a: Long, b: Long): Long? = a.checkedAdd(b)
public fun rustCheckedAdd(a: ULong, b: ULong): ULong? = a.checkedAdd(b)
public fun rustCheckedAdd(a: I128, b: I128): I128? = a.checkedAdd(b)
public fun rustCheckedAdd(a: U128, b: U128): U128? = a.checkedAdd(b)

public fun rustCheckedSub(a: Byte, b: Byte): Byte? = a.checkedSub(b)
public fun rustCheckedSub(a: UByte, b: UByte): UByte? = a.checkedSub(b)
public fun rustCheckedSub(a: Short, b: Short): Short? = a.checkedSub(b)
public fun rustCheckedSub(a: UShort, b: UShort): UShort? = a.checkedSub(b)
public fun rustCheckedSub(a: Int, b: Int): Int? = a.checkedSub(b)
public fun rustCheckedSub(a: UInt, b: UInt): UInt? = a.checkedSub(b)
public fun rustCheckedSub(a: Long, b: Long): Long? = a.checkedSub(b)
public fun rustCheckedSub(a: ULong, b: ULong): ULong? = a.checkedSub(b)
public fun rustCheckedSub(a: I128, b: I128): I128? = a.checkedSub(b)
public fun rustCheckedSub(a: U128, b: U128): U128? = a.checkedSub(b)

public fun rustCheckedMul(a: Byte, b: Byte): Byte? = a.checkedMul(b)
public fun rustCheckedMul(a: UByte, b: UByte): UByte? = a.checkedMul(b)
public fun rustCheckedMul(a: Short, b: Short): Short? = a.checkedMul(b)
public fun rustCheckedMul(a: UShort, b: UShort): UShort? = a.checkedMul(b)
public fun rustCheckedMul(a: Int, b: Int): Int? = a.checkedMul(b)
public fun rustCheckedMul(a: UInt, b: UInt): UInt? = a.checkedMul(b)
public fun rustCheckedMul(a: Long, b: Long): Long? = a.checkedMul(b)
public fun rustCheckedMul(a: ULong, b: ULong): ULong? = a.checkedMul(b)
public fun rustCheckedMul(a: I128, b: I128): I128? = a.checkedMul(b)
public fun rustCheckedMul(a: U128, b: U128): U128? = a.checkedMul(b)

public fun rustCheckedDiv(a: Byte, b: Byte): Byte? = a.checkedDiv(b)
public fun rustCheckedDiv(a: UByte, b: UByte): UByte? = a.checkedDiv(b)
public fun rustCheckedDiv(a: Short, b: Short): Short? = a.checkedDiv(b)
public fun rustCheckedDiv(a: UShort, b: UShort): UShort? = a.checkedDiv(b)
public fun rustCheckedDiv(a: Int, b: Int): Int? = a.checkedDiv(b)
public fun rustCheckedDiv(a: UInt, b: UInt): UInt? = a.checkedDiv(b)
public fun rustCheckedDiv(a: Long, b: Long): Long? = a.checkedDiv(b)
public fun rustCheckedDiv(a: ULong, b: ULong): ULong? = a.checkedDiv(b)
public fun rustCheckedDiv(a: I128, b: I128): I128? = a.checkedDiv(b)
public fun rustCheckedDiv(a: U128, b: U128): U128? = a.checkedDiv(b)

public fun rustCheckedRem(a: Byte, b: Byte): Byte? = a.checkedRem(b)
public fun rustCheckedRem(a: UByte, b: UByte): UByte? = a.checkedRem(b)
public fun rustCheckedRem(a: Short, b: Short): Short? = a.checkedRem(b)
public fun rustCheckedRem(a: UShort, b: UShort): UShort? = a.checkedRem(b)
public fun rustCheckedRem(a: Int, b: Int): Int? = a.checkedRem(b)
public fun rustCheckedRem(a: UInt, b: UInt): UInt? = a.checkedRem(b)
public fun rustCheckedRem(a: Long, b: Long): Long? = a.checkedRem(b)
public fun rustCheckedRem(a: ULong, b: ULong): ULong? = a.checkedRem(b)

public fun rustCheckedNeg(a: Byte): Byte? = a.checkedNeg()
public fun rustCheckedNeg(a: Short): Short? = a.checkedNeg()
public fun rustCheckedNeg(a: Int): Int? = a.checkedNeg()
public fun rustCheckedNeg(a: Long): Long? = a.checkedNeg()
public fun rustCheckedNeg(a: UByte): UByte? = if (a == 0u.toUByte()) 0u.toUByte() else null
public fun rustCheckedNeg(a: UShort): UShort? = if (a == 0u.toUShort()) 0u.toUShort() else null
public fun rustCheckedNeg(a: UInt): UInt? = if (a == 0u) 0u else null
public fun rustCheckedNeg(a: ULong): ULong? = if (a == 0uL) 0uL else null
public fun rustCheckedNeg(a: I128): I128? = a.checkedNeg()

public fun rustCheckedShl(a: Int, b: UInt): Int? = if (b < 32u) a shl b.toInt() else null
public fun rustCheckedShl(a: UInt, b: UInt): UInt? = if (b < 32u) a shl b.toInt() else null
public fun rustCheckedShl(a: Long, b: UInt): Long? = if (b < 64u) a shl b.toInt() else null
public fun rustCheckedShl(a: ULong, b: UInt): ULong? = if (b < 64u) a shl b.toInt() else null
public fun rustCheckedShr(a: Int, b: UInt): Int? = if (b < 32u) a shr b.toInt() else null
public fun rustCheckedShr(a: UInt, b: UInt): UInt? = if (b < 32u) a shr b.toInt() else null
public fun rustCheckedShr(a: Long, b: UInt): Long? = if (b < 64u) a shr b.toInt() else null
public fun rustCheckedShr(a: ULong, b: UInt): ULong? = if (b < 64u) a shr b.toInt() else null

public fun rustSaturatingAdd(a: Byte, b: Byte): Byte = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: UByte, b: UByte): UByte = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: Short, b: Short): Short = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: UShort, b: UShort): UShort = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: Int, b: Int): Int = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: UInt, b: UInt): UInt = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: Long, b: Long): Long = a.saturatingAdd(b)
public fun rustSaturatingAdd(a: ULong, b: ULong): ULong = a.saturatingAdd(b)

public fun rustSaturatingSub(a: Byte, b: Byte): Byte = a.saturatingSub(b)
public fun rustSaturatingSub(a: UByte, b: UByte): UByte = a.saturatingSub(b)
public fun rustSaturatingSub(a: Short, b: Short): Short = a.saturatingSub(b)
public fun rustSaturatingSub(a: UShort, b: UShort): UShort = a.saturatingSub(b)
public fun rustSaturatingSub(a: Int, b: Int): Int = a.saturatingSub(b)
public fun rustSaturatingSub(a: UInt, b: UInt): UInt = a.saturatingSub(b)
public fun rustSaturatingSub(a: Long, b: Long): Long = a.saturatingSub(b)
public fun rustSaturatingSub(a: ULong, b: ULong): ULong = a.saturatingSub(b)

public fun rustSaturatingMul(a: Byte, b: Byte): Byte = a.saturatingMul(b)
public fun rustSaturatingMul(a: UByte, b: UByte): UByte = a.saturatingMul(b)
public fun rustSaturatingMul(a: Short, b: Short): Short = a.saturatingMul(b)
public fun rustSaturatingMul(a: UShort, b: UShort): UShort = a.saturatingMul(b)
public fun rustSaturatingMul(a: Int, b: Int): Int = a.saturatingMul(b)
public fun rustSaturatingMul(a: UInt, b: UInt): UInt = a.saturatingMul(b)
public fun rustSaturatingMul(a: Long, b: Long): Long = a.saturatingMul(b)
public fun rustSaturatingMul(a: ULong, b: ULong): ULong = a.saturatingMul(b)

public fun rustOverflowingAdd(a: Int, b: Int): Pair<Int, Boolean> = a.overflowingAdd(b)
public fun rustOverflowingAdd(a: UInt, b: UInt): Pair<UInt, Boolean> = a.overflowingAdd(b)
public fun rustOverflowingAdd(a: Long, b: Long): Pair<Long, Boolean> = a.overflowingAdd(b)
public fun rustOverflowingAdd(a: ULong, b: ULong): Pair<ULong, Boolean> = a.overflowingAdd(b)
public fun rustOverflowingSub(a: Int, b: Int): Pair<Int, Boolean> = a.overflowingSub(b)
public fun rustOverflowingSub(a: UInt, b: UInt): Pair<UInt, Boolean> = a.overflowingSub(b)
public fun rustOverflowingSub(a: Long, b: Long): Pair<Long, Boolean> = a.overflowingSub(b)
public fun rustOverflowingSub(a: ULong, b: ULong): Pair<ULong, Boolean> = a.overflowingSub(b)
public fun rustOverflowingMul(a: Int, b: Int): Pair<Int, Boolean> = a.overflowingMul(b)
public fun rustOverflowingMul(a: UInt, b: UInt): Pair<UInt, Boolean> = a.overflowingMul(b)
public fun rustOverflowingMul(a: Long, b: Long): Pair<Long, Boolean> = a.overflowingMul(b)
public fun rustOverflowingMul(a: ULong, b: ULong): Pair<ULong, Boolean> = a.overflowingMul(b)

public fun rustCountOnes(a: Byte): UInt = a.countOnes()
public fun rustCountOnes(a: UByte): UInt = a.countOnes()
public fun rustCountOnes(a: Short): UInt = a.countOnes()
public fun rustCountOnes(a: UShort): UInt = a.countOnes()
public fun rustCountOnes(a: Int): UInt = a.countOnes()
public fun rustCountOnes(a: UInt): UInt = a.countOnes()
public fun rustCountOnes(a: Long): UInt = a.countOnes()
public fun rustCountOnes(a: ULong): UInt = a.countOnes()

public fun rustCountZeros(a: Byte): UInt = a.countZeros()
public fun rustCountZeros(a: UByte): UInt = a.countZeros()
public fun rustCountZeros(a: Short): UInt = a.countZeros()
public fun rustCountZeros(a: UShort): UInt = a.countZeros()
public fun rustCountZeros(a: Int): UInt = a.countZeros()
public fun rustCountZeros(a: UInt): UInt = a.countZeros()
public fun rustCountZeros(a: Long): UInt = a.countZeros()
public fun rustCountZeros(a: ULong): UInt = a.countZeros()

public fun rustLeadingZeros(a: Byte): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: UByte): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: Short): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: UShort): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: Int): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: UInt): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: Long): UInt = a.leadingZeros()
public fun rustLeadingZeros(a: ULong): UInt = a.leadingZeros()

public fun rustTrailingZeros(a: Byte): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: UByte): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: Short): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: UShort): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: Int): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: UInt): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: Long): UInt = a.trailingZeros()
public fun rustTrailingZeros(a: ULong): UInt = a.trailingZeros()

public fun rustLeadingOnes(a: Byte): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: UByte): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: Short): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: UShort): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: Int): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: UInt): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: Long): UInt = a.leadingOnes()
public fun rustLeadingOnes(a: ULong): UInt = a.leadingOnes()

public fun rustTrailingOnes(a: Byte): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: UByte): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: Short): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: UShort): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: Int): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: UInt): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: Long): UInt = a.trailingOnes()
public fun rustTrailingOnes(a: ULong): UInt = a.trailingOnes()

public fun rustReverseBits(a: Byte): Byte = a.reverseBits()
public fun rustReverseBits(a: UByte): UByte = a.reverseBits()
public fun rustReverseBits(a: Short): Short = a.reverseBits()
public fun rustReverseBits(a: UShort): UShort = a.reverseBits()
public fun rustReverseBits(a: Int): Int = a.reverseBits()
public fun rustReverseBits(a: UInt): UInt = a.reverseBits()
public fun rustReverseBits(a: Long): Long = a.reverseBits()
public fun rustReverseBits(a: ULong): ULong = a.reverseBits()

public fun rustSwapBytes(a: Byte): Byte = a
public fun rustSwapBytes(a: UByte): UByte = a
public fun rustSwapBytes(a: Short): Short = a.swapBytes()
public fun rustSwapBytes(a: UShort): UShort = a.swapBytes()
public fun rustSwapBytes(a: Int): Int = a.swapBytes()
public fun rustSwapBytes(a: UInt): UInt = a.swapBytes()
public fun rustSwapBytes(a: Long): Long = a.swapBytes()
public fun rustSwapBytes(a: ULong): ULong = a.swapBytes()

public fun rustToBe(a: Short): Short = a.toBe()
public fun rustToBe(a: UShort): UShort = a.toBe()
public fun rustToBe(a: Int): Int = a.toBe()
public fun rustToBe(a: UInt): UInt = a.toBe()
public fun rustToBe(a: Long): Long = a.toBe()
public fun rustToBe(a: ULong): ULong = a.toBe()
public fun rustToLe(a: Short): Short = a.toLe()
public fun rustToLe(a: UShort): UShort = a.toLe()
public fun rustToLe(a: Int): Int = a.toLe()
public fun rustToLe(a: UInt): UInt = a.toLe()
public fun rustToLe(a: Long): Long = a.toLe()
public fun rustToLe(a: ULong): ULong = a.toLe()

public fun rustFromBe(a: Short): Short = a.toBe()
public fun rustFromBe(a: UShort): UShort = a.toBe()
public fun rustFromBe(a: Int): Int = a.toBe()
public fun rustFromBe(a: UInt): UInt = a.toBe()
public fun rustFromBe(a: Long): Long = a.toBe()
public fun rustFromBe(a: ULong): ULong = a.toBe()
public fun rustFromLe(a: Short): Short = a.toLe()
public fun rustFromLe(a: UShort): UShort = a.toLe()
public fun rustFromLe(a: Int): Int = a.toLe()
public fun rustFromLe(a: UInt): UInt = a.toLe()
public fun rustFromLe(a: Long): Long = a.toLe()
public fun rustFromLe(a: ULong): ULong = a.toLe()

public fun rustToNeBytes(a: Int): ByteArray =
    byteArrayOf(
        (a and 0xFF).toByte(),
        ((a ushr 8) and 0xFF).toByte(),
        ((a ushr 16) and 0xFF).toByte(),
        ((a ushr 24) and 0xFF).toByte(),
    )

public fun rustToNeBytes(a: ULong): ByteArray =
    ByteArray(8) { idx -> ((a shr (idx * 8)) and 0xFFuL).toByte() }

public fun rustRotateLeft(a: Int, n: UInt): Int = a.rotateLeft(n)
public fun rustRotateLeft(a: UInt, n: UInt): UInt = a.rotateLeft(n)
public fun rustRotateLeft(a: Long, n: UInt): Long = a.rotateLeft(n)
public fun rustRotateLeft(a: ULong, n: UInt): ULong = a.rotateLeft(n)
public fun rustRotateRight(a: Int, n: UInt): Int = a.rotateRight(n)
public fun rustRotateRight(a: UInt, n: UInt): UInt = a.rotateRight(n)
public fun rustRotateRight(a: Long, n: UInt): Long = a.rotateRight(n)
public fun rustRotateRight(a: ULong, n: UInt): ULong = a.rotateRight(n)

public fun rustDivEuclid(a: Int, b: Int): Int = a.floorDiv(b)
public fun rustDivEuclid(a: Long, b: Long): Long = a.floorDiv(b)
public fun rustRemEuclid(a: Int, b: Int): Int = a.mod(b)
public fun rustRemEuclid(a: Long, b: Long): Long = a.mod(b)

public fun rustCheckedDivEuclid(a: Int, b: Int): Int? = if (b == 0 || (a == Int.MIN_VALUE && b == -1)) null else a.floorDiv(b)
public fun rustCheckedDivEuclid(a: Long, b: Long): Long? = if (b == 0L || (a == Long.MIN_VALUE && b == -1L)) null else a.floorDiv(b)
public fun rustCheckedRemEuclid(a: Int, b: Int): Int? = if (b == 0 || (a == Int.MIN_VALUE && b == -1)) null else a.mod(b)
public fun rustCheckedRemEuclid(a: Long, b: Long): Long? = if (b == 0L || (a == Long.MIN_VALUE && b == -1L)) null else a.mod(b)

public fun rustPow(base: Int, exp: UInt): Int {
    var result = 1
    var b = base
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) result *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return result
}

public fun rustPow(base: UInt, exp: UInt): UInt {
    var result = 1u
    var b = base
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) result *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return result
}

public fun rustPow(base: Long, exp: UInt): Long {
    var result = 1L
    var b = base
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) result *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return result
}

public fun rustPow(base: ULong, exp: UInt): ULong {
    var result = 1uL
    var b = base
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) result *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return result
}

public fun rustDoubleToULongUnchecked(v: Double): ULong = kmDoubleToU64Unchecked(v)
public fun rustFloatToULongUnchecked(v: Float): ULong = kmFloatToU64Unchecked(v)
public fun rustIsSignNegative(v: Double): Boolean = (v.toRawBits().toULong() and 0x8000_0000_0000_0000uL) != 0uL
public fun rustIsSignNegative(v: Float): Boolean = (v.toRawBits().toUInt() and 0x8000_0000u) != 0u
public fun rustIsSignPositive(v: Double): Boolean = !rustIsSignNegative(v)
public fun rustIsSignPositive(v: Float): Boolean = !rustIsSignNegative(v)

private fun parseSignedRadix(s: String, radix: UInt, minVal: Long, maxVal: Long): Result<Long, ParseIntError> {
    if (s.isEmpty()) return Result.Err<Long, ParseIntError>(ParseIntError(IntErrorKind.Empty))
    val r = radix.toInt()
    if (r !in 2..36) return Result.Err<Long, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
    var start = 0
    var negative = false
    when (s[0]) {
        '-' -> {
            negative = true
            start = 1
        }
        '+' -> start = 1
    }
    if (start >= s.length) return Result.Err<Long, ParseIntError>(ParseIntError(IntErrorKind.Empty))
    var acc = 0L
    val limit = if (negative) minVal else -maxVal
    val multMin = limit / r.toLong()
    for (i in start until s.length) {
        val digit = kmDigit(s[i], r) ?: return Result.Err<Long, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
        if (acc < multMin) {
            return Result.Err<Long, ParseIntError>(
                ParseIntError(if (negative) IntErrorKind.NegOverflow else IntErrorKind.PosOverflow),
            )
        }
        acc *= r.toLong()
        if (acc < limit + digit) {
            return Result.Err<Long, ParseIntError>(
                ParseIntError(if (negative) IntErrorKind.NegOverflow else IntErrorKind.PosOverflow),
            )
        }
        acc -= digit
    }
    return Result.Ok<Long, ParseIntError>(if (negative) acc else -acc)
}

private fun parseUnsignedRadix(s: String, radix: UInt, maxVal: ULong): Result<ULong, ParseIntError> {
    if (s.isEmpty()) return Result.Err<ULong, ParseIntError>(ParseIntError(IntErrorKind.Empty))
    val r = radix.toInt()
    if (r !in 2..36) return Result.Err<ULong, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
    var start = 0
    if (s[0] == '+') start = 1
    if (start >= s.length) return Result.Err<ULong, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
    var acc = 0uL
    val ur = radix.toULong()
    for (i in start until s.length) {
        val digit = kmDigit(s[i], r) ?: return Result.Err<ULong, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
        val uDigit = digit.toULong()
        if (acc > (maxVal - uDigit) / ur) {
            return Result.Err<ULong, ParseIntError>(ParseIntError(IntErrorKind.PosOverflow))
        }
        acc = acc * ur + uDigit
    }
    return Result.Ok<ULong, ParseIntError>(acc)
}

private fun kmDigit(c: Char, radix: Int): Int? {
    val d =
        when (c) {
            in '0'..'9' -> c - '0'
            in 'a'..'z' -> 10 + (c - 'a')
            in 'A'..'Z' -> 10 + (c - 'A')
            else -> return null
        }
    return if (d < radix) d else null
}

public fun rustFromStrRadixByte(s: String, radix: UInt): Result<Byte, ParseIntError> =
    when (val r = parseSignedRadix(s, radix, Byte.MIN_VALUE.toLong(), Byte.MAX_VALUE.toLong())) {
        is Result.Ok<*, *> -> Result.Ok<Byte, ParseIntError>(r.okValue().toByte())
        is Result.Err<*, *> -> Result.Err<Byte, ParseIntError>(r.errValue())
    }

public fun rustFromStrRadixShort(s: String, radix: UInt): Result<Short, ParseIntError> =
    when (val r = parseSignedRadix(s, radix, Short.MIN_VALUE.toLong(), Short.MAX_VALUE.toLong())) {
        is Result.Ok<*, *> -> Result.Ok<Short, ParseIntError>(r.okValue().toShort())
        is Result.Err<*, *> -> Result.Err<Short, ParseIntError>(r.errValue())
    }

public fun rustFromStrRadixInt(s: String, radix: UInt): Result<Int, ParseIntError> =
    when (val r = parseSignedRadix(s, radix, Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong())) {
        is Result.Ok<*, *> -> Result.Ok<Int, ParseIntError>(r.okValue().toInt())
        is Result.Err<*, *> -> Result.Err<Int, ParseIntError>(r.errValue())
    }

public fun rustFromStrRadixLong(s: String, radix: UInt): Result<Long, ParseIntError> =
    parseSignedRadix(s, radix, Long.MIN_VALUE, Long.MAX_VALUE)

public fun rustFromStrRadixUByte(s: String, radix: UInt): Result<UByte, ParseIntError> =
    when (val r = parseUnsignedRadix(s, radix, UByte.MAX_VALUE.toULong())) {
        is Result.Ok<*, *> -> Result.Ok<UByte, ParseIntError>(r.okValue().toUByte())
        is Result.Err<*, *> -> Result.Err<UByte, ParseIntError>(r.errValue())
    }

public fun rustFromStrRadixUShort(s: String, radix: UInt): Result<UShort, ParseIntError> =
    when (val r = parseUnsignedRadix(s, radix, UShort.MAX_VALUE.toULong())) {
        is Result.Ok<*, *> -> Result.Ok<UShort, ParseIntError>(r.okValue().toUShort())
        is Result.Err<*, *> -> Result.Err<UShort, ParseIntError>(r.errValue())
    }

public fun rustFromStrRadixUInt(s: String, radix: UInt): Result<UInt, ParseIntError> =
    when (val r = parseUnsignedRadix(s, radix, UInt.MAX_VALUE.toULong())) {
        is Result.Ok<*, *> -> Result.Ok<UInt, ParseIntError>(r.okValue().toUInt())
        is Result.Err<*, *> -> Result.Err<UInt, ParseIntError>(r.errValue())
    }

public fun rustFromStrRadixULong(s: String, radix: UInt): Result<ULong, ParseIntError> =
    parseUnsignedRadix(s, radix, ULong.MAX_VALUE)

public fun rustFromStrRadixI128(s: String, radix: UInt): Result<I128, ParseIntError> {
    if (s.isEmpty()) return Result.Err<I128, ParseIntError>(ParseIntError(IntErrorKind.Empty))
    val r = radix.toInt()
    if (r !in 2..36) return Result.Err<I128, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
    var start = 0
    var neg = false
    if (s[0] == '-') {
        neg = true
        start = 1
    } else if (s[0] == '+') {
        start = 1
    }
    if (start >= s.length) return Result.Err<I128, ParseIntError>(ParseIntError(IntErrorKind.Empty))
    var acc = U128.ZERO
    val uRadix = U128.fromInt(r)
    val maxMag = if (neg) U128(0x8000_0000_0000_0000uL, 0uL) else U128(0x7FFF_FFFF_FFFF_FFFFuL, ULong.MAX_VALUE)
    for (i in start until s.length) {
        val d = kmDigit(s[i], r) ?: return Result.Err<I128, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
        val mul = acc.overflowingMul(uRadix)
        if (mul.second) return Result.Err<I128, ParseIntError>(ParseIntError(if (neg) IntErrorKind.NegOverflow else IntErrorKind.PosOverflow))
        val add = mul.first.overflowingAdd(U128.fromInt(d))
        if (add.second || add.first > maxMag) return Result.Err<I128, ParseIntError>(ParseIntError(if (neg) IntErrorKind.NegOverflow else IntErrorKind.PosOverflow))
        acc = add.first
    }
    val signed =
        if (neg) {
            val inv = U128(acc.high.inv(), acc.low.inv()).wrappingAdd(U128.ONE)
            I128(inv.high.toLong(), inv.low)
        } else {
            I128(acc.high.toLong(), acc.low)
        }
    return Result.Ok<I128, ParseIntError>(signed)
}

public fun rustFromStrRadixU128(s: String, radix: UInt): Result<U128, ParseIntError> {
    if (s.isEmpty()) return Result.Err<U128, ParseIntError>(ParseIntError(IntErrorKind.Empty))
    val r = radix.toInt()
    if (r !in 2..36) return Result.Err<U128, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
    var start = 0
    if (s[0] == '+') start = 1
    if (start >= s.length) return Result.Err<U128, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
    var acc = U128.ZERO
    val uRadix = U128.fromInt(r)
    for (i in start until s.length) {
        val d = kmDigit(s[i], r) ?: return Result.Err<U128, ParseIntError>(ParseIntError(IntErrorKind.InvalidDigit))
        val mul = acc.overflowingMul(uRadix)
        if (mul.second) return Result.Err<U128, ParseIntError>(ParseIntError(IntErrorKind.PosOverflow))
        val add = mul.first.overflowingAdd(U128.fromInt(d))
        if (add.second) return Result.Err<U128, ParseIntError>(ParseIntError(IntErrorKind.PosOverflow))
        acc = add.first
    }
    return Result.Ok<U128, ParseIntError>(acc)
}

private fun isqrtU64(n: ULong): ULong {
    if (n < 2uL) return n
    var x = n
    var result = 0uL
    var bit = 1uL shl 62
    while (bit > x) bit = bit shr 2
    while (bit != 0uL) {
        val sum = result + bit
        if (x >= sum) {
            x -= sum
            result = (result shr 1) + bit
        } else {
            result = result shr 1
        }
        bit = bit shr 2
    }
    return result
}

private fun isqrtU128(n: U128): U128 {
    if (n < U128.fromInt(2)) return n
    var x = n
    var result = U128.ZERO
    var bit = U128(1uL shl 62, 0uL)
    while (bit > x) bit = bit shr 2
    while (bit != U128.ZERO) {
        val sum = result + bit
        if (x >= sum) {
            x -= sum
            result = (result shr 1) + bit
        } else {
            result = result shr 1
        }
        bit = bit shr 2
    }
    return result
}

// ---- Byte additional helpers ----
public fun Byte.wrappingDiv(rhs: Byte): Byte =
    if (this == Byte.MIN_VALUE && rhs == (-1).toByte()) this else (this / rhs).toByte()

public fun Byte.wrappingRem(rhs: Byte): Byte =
    if (this == Byte.MIN_VALUE && rhs == (-1).toByte()) 0.toByte() else (this % rhs).toByte()

public fun Byte.wrappingAbs(): Byte = if (this < 0) wrappingNeg() else this

public fun Byte.wrappingPow(exp: UInt): Byte {
    var acc = 1
    var b = this.toInt()
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return acc.toByte()
}

public fun Byte.uncheckedAdd(rhs: Byte): Byte = (this + rhs).toByte()
public fun Byte.uncheckedMul(rhs: Byte): Byte = (this * rhs).toByte()
public fun Byte.uncheckedDiv(rhs: Byte): Byte = wrappingDiv(rhs)
public fun Byte.uncheckedRem(rhs: Byte): Byte = wrappingRem(rhs)
public fun Byte.uncheckedNeg(): Byte = (-this).toByte()
public fun Byte.uncheckedShl(rhs: UInt): Byte = (this.toInt() shl rhs.toInt()).toByte()
public fun Byte.uncheckedShr(rhs: UInt): Byte = (this.toInt() shr rhs.toInt()).toByte()

public fun Byte.checkedAbs(): Byte? =
    if (this == Byte.MIN_VALUE) {
        null
    } else if (this < 0) {
        (-this).toByte()
    } else {
        this
    }

public fun Byte.checkedPow(exp: UInt): Byte? {
    var acc: Byte = 1
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun Byte.checkedShl(rhs: UInt): Byte? = if (rhs < 8u) (this.toInt() shl rhs.toInt()).toByte() else null
public fun Byte.checkedShr(rhs: UInt): Byte? = if (rhs < 8u) (this.toInt() shr rhs.toInt()).toByte() else null

public fun Byte.strictAdd(rhs: Byte): Byte = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun Byte.strictSub(rhs: Byte): Byte = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun Byte.strictMul(rhs: Byte): Byte = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun Byte.strictDiv(rhs: Byte): Byte = checkedDiv(rhs) ?: error("attempt to divide by zero or with overflow")
public fun Byte.strictRem(rhs: Byte): Byte = checkedRem(rhs) ?: error("attempt to calculate the remainder with overflow")
public fun Byte.strictNeg(): Byte = checkedNeg() ?: error("attempt to negate with overflow")
public fun Byte.strictAbs(): Byte = checkedAbs() ?: error("attempt to compute abs with overflow")
public fun Byte.strictPow(exp: UInt): Byte = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun Byte.saturatingDiv(rhs: Byte): Byte =
    if (this == Byte.MIN_VALUE && rhs == (-1).toByte()) Byte.MAX_VALUE else (this / rhs).toByte()

public fun Byte.saturatingNeg(): Byte = if (this == Byte.MIN_VALUE) Byte.MAX_VALUE else (-this).toByte()
public fun Byte.saturatingAbs(): Byte =
    if (this == Byte.MIN_VALUE) {
        Byte.MAX_VALUE
    } else if (this < 0) {
        (-this).toByte()
    } else {
        this
    }

public fun Byte.saturatingPow(exp: UInt): Byte {
    if (exp == 0u) return 1
    if (this == 0.toByte()) return 0
    if (this == 1.toByte()) return 1
    if (this == (-1).toByte()) return if ((exp and 1u) == 0u) 1 else -1
    val ovfVal = if (this < 0 && (exp and 1u) != 0u) Byte.MIN_VALUE else Byte.MAX_VALUE
    var acc: Byte = 1
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return ovfVal
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return ovfVal
    }
    return acc
}

public fun Byte.overflowingAbs(): Pair<Byte, Boolean> = Pair(wrappingAbs(), this == Byte.MIN_VALUE)
public fun Byte.overflowingNeg(): Pair<Byte, Boolean> = Pair(wrappingNeg(), this == Byte.MIN_VALUE)
public fun Byte.overflowingDiv(rhs: Byte): Pair<Byte, Boolean> =
    if (this == Byte.MIN_VALUE && rhs == (-1).toByte()) Pair(Byte.MIN_VALUE, true) else Pair((this / rhs).toByte(), false)

public fun Byte.overflowingRem(rhs: Byte): Pair<Byte, Boolean> =
    if (this == Byte.MIN_VALUE && rhs == (-1).toByte()) Pair(0.toByte(), true) else Pair((this % rhs).toByte(), false)

public fun Byte.overflowingPow(exp: UInt): Pair<Byte, Boolean> {
    var acc: Byte = 1
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun Byte.overflowingShl(rhs: UInt): Pair<Byte, Boolean> = Pair(wrappingShl(rhs), rhs >= 8u)
public fun Byte.overflowingShr(rhs: UInt): Pair<Byte, Boolean> = Pair(wrappingShr(rhs), rhs >= 8u)

public fun Byte.divCeil(rhs: Byte): Byte {
    val d = this / rhs
    val r = this % rhs
    val corr = if ((this < 0) == (rhs < 0) && r != 0) 1 else 0
    return (d + corr).toByte()
}

public fun Byte.ilog2(): UInt {
    check(this > 0) { "ilog2 requires positive argument" }
    return 7u - leadingZeros()
}

public fun Byte.ilog10(): UInt {
    check(this > 0) { "ilog10 requires positive argument" }
    var v = this.toInt()
    var r = 0u
    while (v >= 10) {
        v /= 10
        r += 1u
    }
    return r
}

public fun Byte.isqrt(): Byte {
    check(this >= 0) { "argument of integer square root cannot be negative" }
    return isqrtU64(this.toULong()).toByte()
}

public fun Byte.castUnsigned(): UByte = this.toUByte()
public fun Byte.abs(): Byte = if (this < 0) (-this).toByte() else this
public fun Byte.unsignedAbs(): UByte = (if (this < 0) -this.toInt() else this.toInt()).toUByte()
public fun Byte.neg(): Byte = (-this).toByte()
public fun Byte.signum(): Byte =
    if (this > 0) {
        1
    } else if (this < 0) {
        -1
    } else {
        0
    }
public fun Byte.isPositive(): Boolean = this > 0
public fun Byte.isNegative(): Boolean = this < 0

public fun Byte.clampMagnitude(limit: UByte): Byte {
    val mag = unsignedAbs()
    if (mag <= limit) return this
    return if (this < 0) (-(limit.toInt())).toByte() else limit.toByte()
}

public fun Byte.rotateLeft(n: UInt): Byte = this.rotateLeft(n.toInt())
public fun Byte.rotateRight(n: UInt): Byte = this.rotateRight(n.toInt())
public fun Byte.midpoint(rhs: Byte): Byte = ((this.toInt() and rhs.toInt()) + ((this.toInt() xor rhs.toInt()) shr 1)).toByte()
public fun Byte.toBe(): Byte = this
public fun Byte.toLe(): Byte = this
public fun fromBeByte(x: Byte): Byte = x
public fun fromLeByte(x: Byte): Byte = x

// ---- UByte additional helpers ----
public fun UByte.wrappingDiv(rhs: UByte): UByte = (this / rhs).toUByte()
public fun UByte.wrappingRem(rhs: UByte): UByte = (this % rhs).toUByte()

public fun UByte.wrappingPow(exp: UInt): UByte {
    var acc = 1u
    var b = this.toUInt()
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return acc.toUByte()
}

public fun UByte.checkedNextPowerOfTwo(): UByte? {
    if (this <= 1u.toUByte()) return 1u.toUByte()
    if (this > 0x80u.toUByte()) return null
    val p = 8 - (this - 1u).toUByte().leadingZeros().toInt()
    return (1u shl p).toUByte()
}

public fun UByte.nextPowerOfTwo(): UByte = checkedNextPowerOfTwo() ?: error("nextPowerOfTwo overflow for $this")
public fun UByte.wrappingNextPowerOfTwo(): UByte = checkedNextPowerOfTwo() ?: 0u.toUByte()

public fun UByte.uncheckedAdd(rhs: UByte): UByte = (this + rhs).toUByte()
public fun UByte.uncheckedMul(rhs: UByte): UByte = (this * rhs).toUByte()
public fun UByte.uncheckedDiv(rhs: UByte): UByte = (this / rhs).toUByte()
public fun UByte.uncheckedRem(rhs: UByte): UByte = (this % rhs).toUByte()
public fun UByte.uncheckedShl(rhs: UInt): UByte = (this.toUInt() shl rhs.toInt()).toUByte()
public fun UByte.uncheckedShr(rhs: UInt): UByte = (this.toUInt() shr rhs.toInt()).toUByte()

public fun UByte.checkedNeg(): UByte? = if (this == 0u.toUByte()) 0u.toUByte() else null

public fun UByte.checkedPow(exp: UInt): UByte? {
    var acc: UByte = 1u
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun UByte.checkedShl(rhs: UInt): UByte? = if (rhs < 8u) (this.toUInt() shl rhs.toInt()).toUByte() else null
public fun UByte.checkedShr(rhs: UInt): UByte? = if (rhs < 8u) (this.toUInt() shr rhs.toInt()).toUByte() else null

public fun UByte.strictAdd(rhs: UByte): UByte = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun UByte.strictSub(rhs: UByte): UByte = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun UByte.strictMul(rhs: UByte): UByte = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun UByte.strictDiv(rhs: UByte): UByte = checkedDiv(rhs) ?: error("attempt to divide by zero")
public fun UByte.strictRem(rhs: UByte): UByte = checkedRem(rhs) ?: error("attempt to calculate remainder by zero")
public fun UByte.strictNeg(): UByte = checkedNeg() ?: error("attempt to negate with overflow")
public fun UByte.strictPow(exp: UInt): UByte = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun UByte.saturatingDiv(rhs: UByte): UByte = (this / rhs).toUByte()

public fun UByte.saturatingPow(exp: UInt): UByte {
    var acc: UByte = 1u
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return UByte.MAX_VALUE
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return UByte.MAX_VALUE
    }
    return acc
}

public fun UByte.overflowingNeg(): Pair<UByte, Boolean> = Pair(wrappingNeg(), this != 0u.toUByte())
public fun UByte.overflowingDiv(rhs: UByte): Pair<UByte, Boolean> = Pair((this / rhs).toUByte(), false)
public fun UByte.overflowingRem(rhs: UByte): Pair<UByte, Boolean> = Pair((this % rhs).toUByte(), false)

public fun UByte.overflowingPow(exp: UInt): Pair<UByte, Boolean> {
    var acc: UByte = 1u
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun UByte.overflowingShl(rhs: UInt): Pair<UByte, Boolean> = Pair(wrappingShl(rhs), rhs >= 8u)
public fun UByte.overflowingShr(rhs: UInt): Pair<UByte, Boolean> = Pair(wrappingShr(rhs), rhs >= 8u)

public fun UByte.divCeil(rhs: UByte): UByte {
    val d = this / rhs
    val r = this % rhs
    return (if (r > 0u) d + 1u else d).toUByte()
}

public fun UByte.ilog2(): UInt {
    check(this > 0u.toUByte()) { "ilog2 requires positive argument" }
    return 7u - leadingZeros()
}

public fun UByte.ilog10(): UInt {
    check(this > 0u.toUByte()) { "ilog10 requires positive argument" }
    var v = this.toUInt()
    var r = 0u
    while (v >= 10u) {
        v /= 10u
        r += 1u
    }
    return r
}

public fun UByte.isqrt(): UByte = isqrtU64(this.toULong()).toUByte()
public fun UByte.castSigned(): Byte = this.toByte()
public fun UByte.absDiff(other: UByte): UByte = (if (this >= other) this - other else other - this).toUByte()
public fun UByte.rotateLeft(n: UInt): UByte = this.rotateLeft(n.toInt())
public fun UByte.rotateRight(n: UInt): UByte = this.rotateRight(n.toInt())
public fun UByte.midpoint(rhs: UByte): UByte = ((this.toInt() and rhs.toInt()) + ((this.toInt() xor rhs.toInt()) ushr 1)).toUByte()
public fun UByte.toBe(): UByte = this
public fun UByte.toLe(): UByte = this
public fun fromBeUByte(x: UByte): UByte = x
public fun fromLeUByte(x: UByte): UByte = x

// ---- Short additional helpers ----
public fun Short.wrappingDiv(rhs: Short): Short =
    if (this == Short.MIN_VALUE && rhs == (-1).toShort()) this else (this / rhs).toShort()

public fun Short.wrappingRem(rhs: Short): Short =
    if (this == Short.MIN_VALUE && rhs == (-1).toShort()) 0.toShort() else (this % rhs).toShort()

public fun Short.wrappingAbs(): Short = if (this < 0) wrappingNeg() else this

public fun Short.wrappingPow(exp: UInt): Short {
    var acc = 1
    var b = this.toInt()
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return acc.toShort()
}

public fun Short.uncheckedAdd(rhs: Short): Short = (this + rhs).toShort()
public fun Short.uncheckedMul(rhs: Short): Short = (this * rhs).toShort()
public fun Short.uncheckedDiv(rhs: Short): Short = wrappingDiv(rhs)
public fun Short.uncheckedRem(rhs: Short): Short = wrappingRem(rhs)
public fun Short.uncheckedNeg(): Short = (-this).toShort()
public fun Short.uncheckedShl(rhs: UInt): Short = (this.toInt() shl rhs.toInt()).toShort()
public fun Short.uncheckedShr(rhs: UInt): Short = (this.toInt() shr rhs.toInt()).toShort()

public fun Short.checkedAbs(): Short? =
    if (this == Short.MIN_VALUE) {
        null
    } else if (this < 0) {
        (-this).toShort()
    } else {
        this
    }

public fun Short.checkedPow(exp: UInt): Short? {
    var acc: Short = 1
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun Short.checkedShl(rhs: UInt): Short? = if (rhs < 16u) (this.toInt() shl rhs.toInt()).toShort() else null
public fun Short.checkedShr(rhs: UInt): Short? = if (rhs < 16u) (this.toInt() shr rhs.toInt()).toShort() else null

public fun Short.strictAdd(rhs: Short): Short = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun Short.strictSub(rhs: Short): Short = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun Short.strictMul(rhs: Short): Short = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun Short.strictDiv(rhs: Short): Short = checkedDiv(rhs) ?: error("attempt to divide by zero or with overflow")
public fun Short.strictRem(rhs: Short): Short = checkedRem(rhs) ?: error("attempt to calculate the remainder with overflow")
public fun Short.strictNeg(): Short = checkedNeg() ?: error("attempt to negate with overflow")
public fun Short.strictAbs(): Short = checkedAbs() ?: error("attempt to compute abs with overflow")
public fun Short.strictPow(exp: UInt): Short = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun Short.saturatingDiv(rhs: Short): Short =
    if (this == Short.MIN_VALUE && rhs == (-1).toShort()) Short.MAX_VALUE else (this / rhs).toShort()

public fun Short.saturatingNeg(): Short = if (this == Short.MIN_VALUE) Short.MAX_VALUE else (-this).toShort()
public fun Short.saturatingAbs(): Short =
    if (this == Short.MIN_VALUE) {
        Short.MAX_VALUE
    } else if (this < 0) {
        (-this).toShort()
    } else {
        this
    }

public fun Short.saturatingPow(exp: UInt): Short {
    if (exp == 0u) return 1
    if (this == 0.toShort()) return 0
    if (this == 1.toShort()) return 1
    if (this == (-1).toShort()) return if ((exp and 1u) == 0u) 1 else -1
    val ovfVal = if (this < 0 && (exp and 1u) != 0u) Short.MIN_VALUE else Short.MAX_VALUE
    var acc: Short = 1
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return ovfVal
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return ovfVal
    }
    return acc
}

public fun Short.overflowingAbs(): Pair<Short, Boolean> = Pair(wrappingAbs(), this == Short.MIN_VALUE)
public fun Short.overflowingNeg(): Pair<Short, Boolean> = Pair(wrappingNeg(), this == Short.MIN_VALUE)
public fun Short.overflowingDiv(rhs: Short): Pair<Short, Boolean> =
    if (this == Short.MIN_VALUE && rhs == (-1).toShort()) Pair(Short.MIN_VALUE, true) else Pair((this / rhs).toShort(), false)

public fun Short.overflowingRem(rhs: Short): Pair<Short, Boolean> =
    if (this == Short.MIN_VALUE && rhs == (-1).toShort()) Pair(0.toShort(), true) else Pair((this % rhs).toShort(), false)

public fun Short.overflowingPow(exp: UInt): Pair<Short, Boolean> {
    var acc: Short = 1
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun Short.overflowingShl(rhs: UInt): Pair<Short, Boolean> = Pair(wrappingShl(rhs), rhs >= 16u)
public fun Short.overflowingShr(rhs: UInt): Pair<Short, Boolean> = Pair(wrappingShr(rhs), rhs >= 16u)

public fun Short.divCeil(rhs: Short): Short {
    val d = this / rhs
    val r = this % rhs
    val corr = if ((this < 0) == (rhs < 0) && r != 0) 1 else 0
    return (d + corr).toShort()
}

public fun Short.ilog2(): UInt {
    check(this > 0) { "ilog2 requires positive argument" }
    return 15u - leadingZeros()
}

public fun Short.ilog10(): UInt {
    check(this > 0) { "ilog10 requires positive argument" }
    var v = this.toInt()
    var r = 0u
    while (v >= 10) {
        v /= 10
        r += 1u
    }
    return r
}

public fun Short.isqrt(): Short {
    check(this >= 0) { "argument of integer square root cannot be negative" }
    return isqrtU64(this.toULong()).toShort()
}

public fun Short.castUnsigned(): UShort = this.toUShort()
public fun Short.abs(): Short = if (this < 0) (-this).toShort() else this
public fun Short.unsignedAbs(): UShort = (if (this < 0) -this.toInt() else this.toInt()).toUShort()
public fun Short.neg(): Short = (-this).toShort()
public fun Short.signum(): Short =
    if (this > 0) {
        1
    } else if (this < 0) {
        -1
    } else {
        0
    }
public fun Short.isPositive(): Boolean = this > 0
public fun Short.isNegative(): Boolean = this < 0

public fun Short.clampMagnitude(limit: UShort): Short {
    val mag = unsignedAbs()
    if (mag <= limit) return this
    return if (this < 0) (-(limit.toInt())).toShort() else limit.toShort()
}

public fun Short.rotateLeft(n: UInt): Short = this.rotateLeft(n.toInt())
public fun Short.rotateRight(n: UInt): Short = this.rotateRight(n.toInt())
public fun Short.midpoint(rhs: Short): Short = ((this.toInt() and rhs.toInt()) + ((this.toInt() xor rhs.toInt()) shr 1)).toShort()

// ---- UShort additional helpers ----
public fun UShort.wrappingDiv(rhs: UShort): UShort = (this / rhs).toUShort()
public fun UShort.wrappingRem(rhs: UShort): UShort = (this % rhs).toUShort()

public fun UShort.wrappingPow(exp: UInt): UShort {
    var acc = 1u
    var b = this.toUInt()
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return acc.toUShort()
}

public fun UShort.checkedNextPowerOfTwo(): UShort? {
    if (this <= 1u.toUShort()) return 1u.toUShort()
    if (this > 0x8000u.toUShort()) return null
    val p = 16 - (this - 1u).toUShort().leadingZeros().toInt()
    return (1u shl p).toUShort()
}

public fun UShort.nextPowerOfTwo(): UShort = checkedNextPowerOfTwo() ?: error("nextPowerOfTwo overflow for $this")
public fun UShort.wrappingNextPowerOfTwo(): UShort = checkedNextPowerOfTwo() ?: 0u.toUShort()

public fun UShort.uncheckedAdd(rhs: UShort): UShort = (this + rhs).toUShort()
public fun UShort.uncheckedMul(rhs: UShort): UShort = (this * rhs).toUShort()
public fun UShort.uncheckedDiv(rhs: UShort): UShort = (this / rhs).toUShort()
public fun UShort.uncheckedRem(rhs: UShort): UShort = (this % rhs).toUShort()
public fun UShort.uncheckedShl(rhs: UInt): UShort = (this.toUInt() shl rhs.toInt()).toUShort()
public fun UShort.uncheckedShr(rhs: UInt): UShort = (this.toUInt() shr rhs.toInt()).toUShort()

public fun UShort.checkedNeg(): UShort? = if (this == 0u.toUShort()) 0u.toUShort() else null

public fun UShort.checkedPow(exp: UInt): UShort? {
    var acc: UShort = 1u
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun UShort.checkedShl(rhs: UInt): UShort? = if (rhs < 16u) (this.toUInt() shl rhs.toInt()).toUShort() else null
public fun UShort.checkedShr(rhs: UInt): UShort? = if (rhs < 16u) (this.toUInt() shr rhs.toInt()).toUShort() else null

public fun UShort.strictAdd(rhs: UShort): UShort = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun UShort.strictSub(rhs: UShort): UShort = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun UShort.strictMul(rhs: UShort): UShort = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun UShort.strictDiv(rhs: UShort): UShort = checkedDiv(rhs) ?: error("attempt to divide by zero")
public fun UShort.strictRem(rhs: UShort): UShort = checkedRem(rhs) ?: error("attempt to calculate remainder by zero")
public fun UShort.strictNeg(): UShort = checkedNeg() ?: error("attempt to negate with overflow")
public fun UShort.strictPow(exp: UInt): UShort = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun UShort.saturatingDiv(rhs: UShort): UShort = (this / rhs).toUShort()

public fun UShort.saturatingPow(exp: UInt): UShort {
    var acc: UShort = 1u
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return UShort.MAX_VALUE
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return UShort.MAX_VALUE
    }
    return acc
}

public fun UShort.overflowingNeg(): Pair<UShort, Boolean> = Pair(wrappingNeg(), this != 0u.toUShort())
public fun UShort.overflowingDiv(rhs: UShort): Pair<UShort, Boolean> = Pair((this / rhs).toUShort(), false)
public fun UShort.overflowingRem(rhs: UShort): Pair<UShort, Boolean> = Pair((this % rhs).toUShort(), false)

public fun UShort.overflowingPow(exp: UInt): Pair<UShort, Boolean> {
    var acc: UShort = 1u
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun UShort.overflowingShl(rhs: UInt): Pair<UShort, Boolean> = Pair(wrappingShl(rhs), rhs >= 16u)
public fun UShort.overflowingShr(rhs: UInt): Pair<UShort, Boolean> = Pair(wrappingShr(rhs), rhs >= 16u)

public fun UShort.divCeil(rhs: UShort): UShort {
    val d = this / rhs
    val r = this % rhs
    return (if (r > 0u) d + 1u else d).toUShort()
}

public fun UShort.ilog2(): UInt {
    check(this > 0u.toUShort()) { "ilog2 requires positive argument" }
    return 15u - leadingZeros()
}

public fun UShort.ilog10(): UInt {
    check(this > 0u.toUShort()) { "ilog10 requires positive argument" }
    var v = this.toUInt()
    var r = 0u
    while (v >= 10u) {
        v /= 10u
        r += 1u
    }
    return r
}

public fun UShort.isqrt(): UShort = isqrtU64(this.toULong()).toUShort()
public fun UShort.castSigned(): Short = this.toShort()
public fun UShort.absDiff(other: UShort): UShort = (if (this >= other) this - other else other - this).toUShort()
public fun UShort.rotateLeft(n: UInt): UShort = this.rotateLeft(n.toInt())
public fun UShort.rotateRight(n: UInt): UShort = this.rotateRight(n.toInt())
public fun UShort.midpoint(rhs: UShort): UShort = ((this.toInt() and rhs.toInt()) + ((this.toInt() xor rhs.toInt()) ushr 1)).toUShort()

// ---- Int additional helpers ----
public fun Int.wrappingDiv(rhs: Int): Int = if (this == Int.MIN_VALUE && rhs == -1) this else this / rhs
public fun Int.wrappingRem(rhs: Int): Int = if (this == Int.MIN_VALUE && rhs == -1) 0 else this % rhs
public fun Int.wrappingAbs(): Int = if (this < 0) -this else this
public fun Int.wrappingPow(exp: UInt): Int = rustPow(this, exp)
public fun Int.wrappingNextPowerOfTwo(): Int = checkedNextPowerOfTwo() ?: 0

public fun Int.uncheckedDiv(rhs: Int): Int = wrappingDiv(rhs)
public fun Int.uncheckedRem(rhs: Int): Int = wrappingRem(rhs)
public fun Int.uncheckedShl(rhs: UInt): Int = this shl rhs.toInt()
public fun Int.uncheckedShr(rhs: UInt): Int = this shr rhs.toInt()

public fun Int.checkedAbs(): Int? =
    if (this == Int.MIN_VALUE) {
        null
    } else if (this < 0) {
        -this
    } else {
        this
    }

public fun Int.checkedPow(exp: UInt): Int? {
    var acc = 1
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun Int.checkedShl(rhs: UInt): Int? = if (rhs < 32u) this shl rhs.toInt() else null
public fun Int.checkedShr(rhs: UInt): Int? = if (rhs < 32u) this shr rhs.toInt() else null

public fun Int.strictAdd(rhs: Int): Int = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun Int.strictSub(rhs: Int): Int = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun Int.strictMul(rhs: Int): Int = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun Int.strictDiv(rhs: Int): Int = checkedDiv(rhs) ?: error("attempt to divide by zero or with overflow")
public fun Int.strictRem(rhs: Int): Int = checkedRem(rhs) ?: error("attempt to calculate the remainder with overflow")
public fun Int.strictNeg(): Int = checkedNeg() ?: error("attempt to negate with overflow")
public fun Int.strictAbs(): Int = checkedAbs() ?: error("attempt to compute abs with overflow")
public fun Int.strictPow(exp: UInt): Int = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun Int.saturatingDiv(rhs: Int): Int = if (this == Int.MIN_VALUE && rhs == -1) Int.MAX_VALUE else this / rhs
public fun Int.saturatingNeg(): Int = if (this == Int.MIN_VALUE) Int.MAX_VALUE else -this
public fun Int.saturatingAbs(): Int =
    if (this == Int.MIN_VALUE) {
        Int.MAX_VALUE
    } else if (this < 0) {
        -this
    } else {
        this
    }

public fun Int.saturatingPow(exp: UInt): Int {
    if (exp == 0u) return 1
    if (this == 0) return 0
    if (this == 1) return 1
    if (this == -1) return if ((exp and 1u) == 0u) 1 else -1
    val ovfVal = if (this < 0 && (exp and 1u) != 0u) Int.MIN_VALUE else Int.MAX_VALUE
    var acc = 1
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return ovfVal
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return ovfVal
    }
    return acc
}

public fun Int.overflowingAbs(): Pair<Int, Boolean> = Pair(wrappingAbs(), this == Int.MIN_VALUE)
public fun Int.overflowingNeg(): Pair<Int, Boolean> = Pair(wrappingNeg(), this == Int.MIN_VALUE)
public fun Int.overflowingDiv(rhs: Int): Pair<Int, Boolean> =
    if (this == Int.MIN_VALUE && rhs == -1) Pair(Int.MIN_VALUE, true) else Pair(this / rhs, false)

public fun Int.overflowingRem(rhs: Int): Pair<Int, Boolean> =
    if (this == Int.MIN_VALUE && rhs == -1) Pair(0, true) else Pair(this % rhs, false)

public fun Int.overflowingPow(exp: UInt): Pair<Int, Boolean> {
    var acc = 1
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun Int.overflowingShl(rhs: UInt): Pair<Int, Boolean> = Pair(wrappingShl(rhs), rhs >= 32u)
public fun Int.overflowingShr(rhs: UInt): Pair<Int, Boolean> = Pair(wrappingShr(rhs), rhs >= 32u)

public fun Int.divCeil(rhs: Int): Int {
    val d = this / rhs
    val r = this % rhs
    val corr = if ((this < 0) == (rhs < 0) && r != 0) 1 else 0
    return d + corr
}

public fun Int.ilog2(): UInt {
    check(this > 0) { "ilog2 requires positive argument" }
    return 31u - leadingZeros()
}

public fun Int.isqrt(): Int {
    check(this >= 0) { "argument of integer square root cannot be negative" }
    return isqrtU64(this.toULong()).toInt()
}

public fun Int.castUnsigned(): UInt = this.toUInt()
public fun Int.abs(): Int = if (this < 0) -this else this
public fun Int.unsignedAbs(): UInt = if (this < 0) (0u - this.toUInt()) else this.toUInt()
public fun Int.neg(): Int = -this
public fun Int.signum(): Int =
    if (this > 0) {
        1
    } else if (this < 0) {
        -1
    } else {
        0
    }
public fun Int.isPositive(): Boolean = this > 0
public fun Int.isNegative(): Boolean = this < 0

public fun Int.clampMagnitude(limit: UInt): Int {
    val mag = unsignedAbs()
    if (mag <= limit) return this
    return if (this < 0) (0u - limit).toInt() else limit.toInt()
}

// ---- UInt additional helpers ----
public fun UInt.wrappingDiv(rhs: UInt): UInt = this / rhs
public fun UInt.wrappingRem(rhs: UInt): UInt = this % rhs
public fun UInt.wrappingPow(exp: UInt): UInt = rustPow(this, exp)
public fun UInt.wrappingNextPowerOfTwo(): UInt = checkedNextPowerOfTwo() ?: 0u

public fun UInt.uncheckedDiv(rhs: UInt): UInt = this / rhs
public fun UInt.uncheckedRem(rhs: UInt): UInt = this % rhs
public fun UInt.uncheckedShl(rhs: UInt): UInt = this shl rhs.toInt()
public fun UInt.uncheckedShr(rhs: UInt): UInt = this shr rhs.toInt()

public fun UInt.checkedNeg(): UInt? = if (this == 0u) 0u else null

public fun UInt.checkedPow(exp: UInt): UInt? {
    var acc = 1u
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun UInt.checkedShl(rhs: UInt): UInt? = if (rhs < 32u) this shl rhs.toInt() else null
public fun UInt.checkedShr(rhs: UInt): UInt? = if (rhs < 32u) this shr rhs.toInt() else null

public fun UInt.strictAdd(rhs: UInt): UInt = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun UInt.strictSub(rhs: UInt): UInt = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun UInt.strictMul(rhs: UInt): UInt = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun UInt.strictDiv(rhs: UInt): UInt = checkedDiv(rhs) ?: error("attempt to divide by zero")
public fun UInt.strictRem(rhs: UInt): UInt = checkedRem(rhs) ?: error("attempt to calculate remainder by zero")
public fun UInt.strictNeg(): UInt = checkedNeg() ?: error("attempt to negate with overflow")
public fun UInt.strictPow(exp: UInt): UInt = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun UInt.saturatingDiv(rhs: UInt): UInt = this / rhs

public fun UInt.saturatingPow(exp: UInt): UInt {
    var acc = 1u
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return UInt.MAX_VALUE
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return UInt.MAX_VALUE
    }
    return acc
}

public fun UInt.overflowingNeg(): Pair<UInt, Boolean> = Pair(wrappingNeg(), this != 0u)
public fun UInt.overflowingDiv(rhs: UInt): Pair<UInt, Boolean> = Pair(this / rhs, false)
public fun UInt.overflowingRem(rhs: UInt): Pair<UInt, Boolean> = Pair(this % rhs, false)

public fun UInt.overflowingPow(exp: UInt): Pair<UInt, Boolean> {
    var acc = 1u
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun UInt.overflowingShl(rhs: UInt): Pair<UInt, Boolean> = Pair(wrappingShl(rhs), rhs >= 32u)
public fun UInt.overflowingShr(rhs: UInt): Pair<UInt, Boolean> = Pair(wrappingShr(rhs), rhs >= 32u)

public fun UInt.divCeil(rhs: UInt): UInt {
    val d = this / rhs
    val r = this % rhs
    return if (r > 0u) d + 1u else d
}

public fun UInt.ilog2(): UInt {
    check(this > 0u) { "ilog2 requires positive argument" }
    return 31u - leadingZeros()
}

public fun UInt.isqrt(): UInt = isqrtU64(this.toULong()).toUInt()
public fun UInt.castSigned(): Int = this.toInt()
public fun UInt.absDiff(other: UInt): UInt = if (this >= other) this - other else other - this

// ---- Long additional helpers ----
public fun Long.wrappingDiv(rhs: Long): Long = if (this == Long.MIN_VALUE && rhs == -1L) this else this / rhs
public fun Long.wrappingRem(rhs: Long): Long = if (this == Long.MIN_VALUE && rhs == -1L) 0L else this % rhs
public fun Long.wrappingAbs(): Long = if (this < 0L) -this else this
public fun Long.wrappingPow(exp: UInt): Long = rustPow(this, exp)

public fun Long.uncheckedDiv(rhs: Long): Long = wrappingDiv(rhs)
public fun Long.uncheckedRem(rhs: Long): Long = wrappingRem(rhs)
public fun Long.uncheckedShl(rhs: UInt): Long = this shl rhs.toInt()
public fun Long.uncheckedShr(rhs: UInt): Long = this shr rhs.toInt()

public fun Long.checkedAbs(): Long? =
    if (this == Long.MIN_VALUE) {
        null
    } else if (this < 0L) {
        -this
    } else {
        this
    }

public fun Long.checkedPow(exp: UInt): Long? {
    var acc = 1L
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun Long.checkedShl(rhs: UInt): Long? = if (rhs < 64u) this shl rhs.toInt() else null
public fun Long.checkedShr(rhs: UInt): Long? = if (rhs < 64u) this shr rhs.toInt() else null

public fun Long.strictAdd(rhs: Long): Long = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun Long.strictSub(rhs: Long): Long = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun Long.strictMul(rhs: Long): Long = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun Long.strictDiv(rhs: Long): Long = checkedDiv(rhs) ?: error("attempt to divide by zero or with overflow")
public fun Long.strictRem(rhs: Long): Long = checkedRem(rhs) ?: error("attempt to calculate the remainder with overflow")
public fun Long.strictNeg(): Long = checkedNeg() ?: error("attempt to negate with overflow")
public fun Long.strictAbs(): Long = checkedAbs() ?: error("attempt to compute abs with overflow")
public fun Long.strictPow(exp: UInt): Long = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun Long.saturatingDiv(rhs: Long): Long = if (this == Long.MIN_VALUE && rhs == -1L) Long.MAX_VALUE else this / rhs
public fun Long.saturatingNeg(): Long = if (this == Long.MIN_VALUE) Long.MAX_VALUE else -this
public fun Long.saturatingAbs(): Long =
    if (this == Long.MIN_VALUE) {
        Long.MAX_VALUE
    } else if (this < 0L) {
        -this
    } else {
        this
    }

public fun Long.saturatingPow(exp: UInt): Long {
    if (exp == 0u) return 1L
    if (this == 0L) return 0L
    if (this == 1L) return 1L
    if (this == -1L) return if ((exp and 1u) == 0u) 1L else -1L
    val ovfVal = if (this < 0L && (exp and 1u) != 0u) Long.MIN_VALUE else Long.MAX_VALUE
    var acc = 1L
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return ovfVal
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return ovfVal
    }
    return acc
}

public fun Long.overflowingAbs(): Pair<Long, Boolean> = Pair(wrappingAbs(), this == Long.MIN_VALUE)
public fun Long.overflowingNeg(): Pair<Long, Boolean> = Pair(wrappingNeg(), this == Long.MIN_VALUE)
public fun Long.overflowingDiv(rhs: Long): Pair<Long, Boolean> =
    if (this == Long.MIN_VALUE && rhs == -1L) Pair(Long.MIN_VALUE, true) else Pair(this / rhs, false)

public fun Long.overflowingRem(rhs: Long): Pair<Long, Boolean> =
    if (this == Long.MIN_VALUE && rhs == -1L) Pair(0L, true) else Pair(this % rhs, false)

public fun Long.overflowingPow(exp: UInt): Pair<Long, Boolean> {
    var acc = 1L
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun Long.overflowingShl(rhs: UInt): Pair<Long, Boolean> = Pair(wrappingShl(rhs), rhs >= 64u)
public fun Long.overflowingShr(rhs: UInt): Pair<Long, Boolean> = Pair(wrappingShr(rhs), rhs >= 64u)

public fun Long.divCeil(rhs: Long): Long {
    val d = this / rhs
    val r = this % rhs
    val corr = if ((this < 0L) == (rhs < 0L) && r != 0L) 1L else 0L
    return d + corr
}

public fun Long.ilog2(): UInt {
    check(this > 0L) { "ilog2 requires positive argument" }
    return 63u - leadingZeros()
}

public fun Long.ilog10(): UInt {
    check(this > 0L) { "ilog10 requires positive argument" }
    var v = this
    var r = 0u
    while (v >= 10L) {
        v /= 10L
        r += 1u
    }
    return r
}

public fun Long.isqrt(): Long {
    check(this >= 0L) { "argument of integer square root cannot be negative" }
    return isqrtU64(this.toULong()).toLong()
}

public fun Long.castUnsigned(): ULong = this.toULong()
public fun Long.abs(): Long = if (this < 0L) -this else this
public fun Long.unsignedAbs(): ULong = if (this < 0L) (0uL - this.toULong()) else this.toULong()
public fun Long.neg(): Long = -this
public fun Long.signum(): Long =
    if (this > 0L) {
        1L
    } else if (this < 0L) {
        -1L
    } else {
        0L
    }
public fun Long.isPositive(): Boolean = this > 0L
public fun Long.isNegative(): Boolean = this < 0L

public fun Long.clampMagnitude(limit: ULong): Long {
    val mag = unsignedAbs()
    if (mag <= limit) return this
    return if (this < 0L) (0uL - limit).toLong() else limit.toLong()
}

// ---- ULong additional helpers ----
public fun ULong.wrappingDiv(rhs: ULong): ULong = this / rhs
public fun ULong.wrappingRem(rhs: ULong): ULong = this % rhs
public fun ULong.wrappingPow(exp: UInt): ULong = rustPow(this, exp)
public fun ULong.wrappingNextPowerOfTwo(): ULong = checkedNextPowerOfTwo() ?: 0uL

public fun ULong.uncheckedDiv(rhs: ULong): ULong = this / rhs
public fun ULong.uncheckedRem(rhs: ULong): ULong = this % rhs
public fun ULong.uncheckedShl(rhs: UInt): ULong = this shl rhs.toInt()
public fun ULong.uncheckedShr(rhs: UInt): ULong = this shr rhs.toInt()

public fun ULong.checkedNeg(): ULong? = if (this == 0uL) 0uL else null

public fun ULong.checkedPow(exp: UInt): ULong? {
    var acc = 1uL
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun ULong.checkedShl(rhs: UInt): ULong? = if (rhs < 64u) this shl rhs.toInt() else null
public fun ULong.checkedShr(rhs: UInt): ULong? = if (rhs < 64u) this shr rhs.toInt() else null

public fun ULong.strictAdd(rhs: ULong): ULong = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun ULong.strictSub(rhs: ULong): ULong = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun ULong.strictMul(rhs: ULong): ULong = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun ULong.strictDiv(rhs: ULong): ULong = checkedDiv(rhs) ?: error("attempt to divide by zero")
public fun ULong.strictRem(rhs: ULong): ULong = checkedRem(rhs) ?: error("attempt to calculate remainder by zero")
public fun ULong.strictNeg(): ULong = checkedNeg() ?: error("attempt to negate with overflow")
public fun ULong.strictPow(exp: UInt): ULong = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun ULong.saturatingDiv(rhs: ULong): ULong = this / rhs

public fun ULong.saturatingPow(exp: UInt): ULong {
    var acc = 1uL
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return ULong.MAX_VALUE
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return ULong.MAX_VALUE
    }
    return acc
}

public fun ULong.overflowingNeg(): Pair<ULong, Boolean> = Pair(wrappingNeg(), this != 0uL)
public fun ULong.overflowingDiv(rhs: ULong): Pair<ULong, Boolean> = Pair(this / rhs, false)
public fun ULong.overflowingRem(rhs: ULong): Pair<ULong, Boolean> = Pair(this % rhs, false)

public fun ULong.overflowingPow(exp: UInt): Pair<ULong, Boolean> {
    var acc = 1uL
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun ULong.overflowingShl(rhs: UInt): Pair<ULong, Boolean> = Pair(wrappingShl(rhs), rhs >= 64u)
public fun ULong.overflowingShr(rhs: UInt): Pair<ULong, Boolean> = Pair(wrappingShr(rhs), rhs >= 64u)

public fun ULong.divCeil(rhs: ULong): ULong {
    val d = this / rhs
    val r = this % rhs
    return if (r > 0uL) d + 1uL else d
}

public fun ULong.ilog2(): UInt {
    check(this > 0uL) { "ilog2 requires positive argument" }
    return 63u - leadingZeros()
}

public fun ULong.ilog10(): UInt {
    check(this > 0uL) { "ilog10 requires positive argument" }
    var v = this
    var r = 0u
    while (v >= 10uL) {
        v /= 10uL
        r += 1u
    }
    return r
}

public fun ULong.isqrt(): ULong = isqrtU64(this)
public fun ULong.castSigned(): Long = this.toLong()
public fun ULong.absDiff(other: ULong): ULong = if (this >= other) this - other else other - this

// ---- U128 additional helpers ----
public fun U128.wrappingDiv(rhs: U128): U128 = this / rhs
public fun U128.wrappingRem(rhs: U128): U128 = this % rhs
public fun U128.wrappingShl(rhs: UInt): U128 = this shl (rhs.toInt() and 127)
public fun U128.wrappingShr(rhs: UInt): U128 = this shr (rhs.toInt() and 127)

public fun U128.wrappingPow(exp: UInt): U128 {
    var acc = U128.ONE
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return acc
}

public fun U128.uncheckedAdd(rhs: U128): U128 = this + rhs
public fun U128.uncheckedSub(rhs: U128): U128 = this - rhs
public fun U128.uncheckedMul(rhs: U128): U128 = this * rhs
public fun U128.uncheckedDiv(rhs: U128): U128 = this / rhs
public fun U128.uncheckedRem(rhs: U128): U128 = this % rhs
public fun U128.uncheckedShl(rhs: UInt): U128 = this shl rhs.toInt()
public fun U128.uncheckedShr(rhs: UInt): U128 = this shr rhs.toInt()

public fun U128.checkedRem(rhs: U128): U128? = if (rhs == U128.ZERO) null else this % rhs
public fun U128.checkedNeg(): U128? = if (this == U128.ZERO) U128.ZERO else null

public fun U128.checkedPow(exp: UInt): U128? {
    var acc = U128.ONE
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun U128.checkedShl(rhs: UInt): U128? = if (rhs < 128u) this shl rhs.toInt() else null
public fun U128.checkedShr(rhs: UInt): U128? = if (rhs < 128u) this shr rhs.toInt() else null

public fun U128.strictAdd(rhs: U128): U128 = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun U128.strictSub(rhs: U128): U128 = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun U128.strictMul(rhs: U128): U128 = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun U128.strictDiv(rhs: U128): U128 = checkedDiv(rhs) ?: error("attempt to divide by zero")
public fun U128.strictRem(rhs: U128): U128 = checkedRem(rhs) ?: error("attempt to calculate remainder by zero")
public fun U128.strictNeg(): U128 = checkedNeg() ?: error("attempt to negate with overflow")
public fun U128.strictPow(exp: UInt): U128 = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun U128.saturatingAdd(rhs: U128): U128 = overflowingAdd(rhs).let { if (it.second) U128.MAX else it.first }
public fun U128.saturatingSub(rhs: U128): U128 = if (this > rhs) this - rhs else U128.ZERO
public fun U128.saturatingMul(rhs: U128): U128 = overflowingMul(rhs).let { if (it.second) U128.MAX else it.first }
public fun U128.saturatingDiv(rhs: U128): U128 = this / rhs

public fun U128.saturatingPow(exp: UInt): U128 {
    var acc = U128.ONE
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return U128.MAX
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return U128.MAX
    }
    return acc
}

public fun U128.overflowingNeg(): Pair<U128, Boolean> = Pair(wrappingNeg(), this != U128.ZERO)
public fun U128.overflowingDiv(rhs: U128): Pair<U128, Boolean> = Pair(this / rhs, false)
public fun U128.overflowingRem(rhs: U128): Pair<U128, Boolean> = Pair(this % rhs, false)

public fun U128.overflowingPow(exp: UInt): Pair<U128, Boolean> {
    var acc = U128.ONE
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun U128.overflowingShl(rhs: UInt): Pair<U128, Boolean> = Pair(wrappingShl(rhs), rhs >= 128u)
public fun U128.overflowingShr(rhs: UInt): Pair<U128, Boolean> = Pair(wrappingShr(rhs), rhs >= 128u)

public fun U128.countZeros(): UInt = 128u - countOnes()
public fun U128.leadingZeros(): UInt = if (high == 0uL) 64u + low.leadingZeros() else high.leadingZeros()
public fun U128.trailingZeros(): UInt = if (low == 0uL) 64u + high.trailingZeros() else low.trailingZeros()
public fun U128.leadingOnes(): UInt = inv().leadingZeros()
public fun U128.trailingOnes(): UInt = inv().trailingZeros()
public fun U128.reverseBits(): U128 = U128(low.reverseBits(), high.reverseBits())
public fun U128.swapBytes(): U128 = U128(low.swapBytes(), high.swapBytes())
public fun U128.toBe(): U128 = if (kmIsBigEndian()) this else swapBytes()
public fun U128.toLe(): U128 = if (kmIsBigEndian()) swapBytes() else this
public fun U128.Companion.fromBe(x: U128): U128 = x.toBe()
public fun U128.Companion.fromLe(x: U128): U128 = x.toLe()

public fun U128.rotateLeft(n: UInt): U128 {
    val k = (n and 127u).toInt()
    return if (k == 0) this else (this shl k) or (this shr (128 - k))
}

public fun U128.rotateRight(n: UInt): U128 {
    val k = (n and 127u).toInt()
    return if (k == 0) this else (this shr k) or (this shl (128 - k))
}

public fun U128.midpoint(rhs: U128): U128 = ((this and rhs) + ((this xor rhs) shr 1))
public fun U128.absDiff(other: U128): U128 = if (this >= other) this - other else other - this
public fun U128.isMultipleOf(rhs: U128): Boolean = if (rhs == U128.ZERO) this == U128.ZERO else (this % rhs) == U128.ZERO
public fun U128.isPowerOfTwo(): Boolean = this != U128.ZERO && (this and (this - U128.ONE)) == U128.ZERO

public fun U128.checkedNextPowerOfTwo(): U128? {
    if (this <= U128.ONE) return U128.ONE
    val maxPow2 = U128(0x8000_0000_0000_0000uL, 0uL)
    if (this > maxPow2) return null
    val p = 128 - (this - U128.ONE).leadingZeros().toInt()
    return U128.ONE shl p
}

public fun U128.nextPowerOfTwo(): U128 = checkedNextPowerOfTwo() ?: error("nextPowerOfTwo overflow for $this")
public fun U128.wrappingNextPowerOfTwo(): U128 = checkedNextPowerOfTwo() ?: U128.ZERO

public fun U128.divCeil(rhs: U128): U128 {
    val d = this / rhs
    val r = this % rhs
    return if (r > U128.ZERO) d + U128.ONE else d
}

public fun U128.ilog2(): UInt {
    check(this > U128.ZERO) { "ilog2 requires positive argument" }
    return 127u - leadingZeros()
}

public fun U128.ilog10(): UInt {
    check(this > U128.ZERO) { "ilog10 requires positive argument" }
    val ten = U128.fromInt(10)
    var v = this
    var r = 0u
    while (v >= ten) {
        v /= ten
        r += 1u
    }
    return r
}

public fun U128.isqrt(): U128 = isqrtU128(this)
public fun U128.castSigned(): I128 = this.toI128()

// ---- I128 additional helpers ----
public fun I128.wrappingDiv(rhs: I128): I128 = if (this == I128.MIN && rhs == I128.fromInt(-1)) this else this / rhs
public fun I128.wrappingRem(rhs: I128): I128 = if (this == I128.MIN && rhs == I128.fromInt(-1)) I128.ZERO else this % rhs
public fun I128.wrappingAbs(): I128 = if (this.high < 0L) -this else this
public fun I128.wrappingShl(rhs: UInt): I128 = this shl (rhs.toInt() and 127)
public fun I128.wrappingShr(rhs: UInt): I128 = this shr (rhs.toInt() and 127)

public fun I128.wrappingPow(exp: UInt): I128 {
    var acc = I128.ONE
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc *= b
        e = e shr 1
        if (e > 0u) b *= b
    }
    return acc
}

public fun I128.uncheckedAdd(rhs: I128): I128 = this + rhs
public fun I128.uncheckedSub(rhs: I128): I128 = this - rhs
public fun I128.uncheckedMul(rhs: I128): I128 = this * rhs
public fun I128.uncheckedDiv(rhs: I128): I128 = wrappingDiv(rhs)
public fun I128.uncheckedRem(rhs: I128): I128 = wrappingRem(rhs)
public fun I128.uncheckedNeg(): I128 = -this
public fun I128.uncheckedShl(rhs: UInt): I128 = this shl rhs.toInt()
public fun I128.uncheckedShr(rhs: UInt): I128 = this shr rhs.toInt()

public fun I128.checkedRem(rhs: I128): I128? {
    if (rhs == I128.ZERO) return null
    if (this == I128.MIN && rhs == I128.fromInt(-1)) return null
    return this % rhs
}

public fun I128.checkedAbs(): I128? =
    if (this == I128.MIN) {
        null
    } else if (this.high < 0L) {
        -this
    } else {
        this
    }

public fun I128.checkedPow(exp: UInt): I128? {
    var acc = I128.ONE
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return null
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return null
    }
    return acc
}

public fun I128.checkedShl(rhs: UInt): I128? = if (rhs < 128u) this shl rhs.toInt() else null
public fun I128.checkedShr(rhs: UInt): I128? = if (rhs < 128u) this shr rhs.toInt() else null

public fun I128.overflowingAdd(rhs: I128): Pair<I128, Boolean> {
    val sum = this + rhs
    val ovf = ((this.high < 0L) == (rhs.high < 0L)) && ((sum.high < 0L) != (this.high < 0L))
    return Pair(sum, ovf)
}

public fun I128.overflowingSub(rhs: I128): Pair<I128, Boolean> {
    val diff = this - rhs
    val ovf = ((this.high < 0L) != (rhs.high < 0L)) && ((diff.high < 0L) != (this.high < 0L))
    return Pair(diff, ovf)
}

public fun I128.overflowingMul(rhs: I128): Pair<I128, Boolean> {
    val prod = this * rhs
    return Pair(prod, checkedMul(rhs) == null)
}

public fun I128.overflowingAbs(): Pair<I128, Boolean> = Pair(wrappingAbs(), this == I128.MIN)
public fun I128.overflowingNeg(): Pair<I128, Boolean> = Pair(wrappingNeg(), this == I128.MIN)
public fun I128.overflowingDiv(rhs: I128): Pair<I128, Boolean> =
    if (this == I128.MIN && rhs == I128.fromInt(-1)) Pair(I128.MIN, true) else Pair(this / rhs, false)

public fun I128.overflowingRem(rhs: I128): Pair<I128, Boolean> =
    if (this == I128.MIN && rhs == I128.fromInt(-1)) Pair(I128.ZERO, true) else Pair(this % rhs, false)

public fun I128.overflowingPow(exp: UInt): Pair<I128, Boolean> {
    var acc = I128.ONE
    var b = this
    var e = exp
    var overflow = false
    while (e > 0u) {
        if ((e and 1u) != 0u) {
            val m = acc.overflowingMul(b)
            acc = m.first
            overflow = overflow || m.second
        }
        e = e shr 1
        if (e > 0u) {
            val m = b.overflowingMul(b)
            b = m.first
            overflow = overflow || m.second
        }
    }
    return Pair(acc, overflow)
}

public fun I128.overflowingShl(rhs: UInt): Pair<I128, Boolean> = Pair(wrappingShl(rhs), rhs >= 128u)
public fun I128.overflowingShr(rhs: UInt): Pair<I128, Boolean> = Pair(wrappingShr(rhs), rhs >= 128u)

public fun I128.strictAdd(rhs: I128): I128 = checkedAdd(rhs) ?: error("attempt to add with overflow")
public fun I128.strictSub(rhs: I128): I128 = checkedSub(rhs) ?: error("attempt to subtract with overflow")
public fun I128.strictMul(rhs: I128): I128 = checkedMul(rhs) ?: error("attempt to multiply with overflow")
public fun I128.strictDiv(rhs: I128): I128 = checkedDiv(rhs) ?: error("attempt to divide by zero or with overflow")
public fun I128.strictRem(rhs: I128): I128 = checkedRem(rhs) ?: error("attempt to calculate the remainder with overflow")
public fun I128.strictNeg(): I128 = checkedNeg() ?: error("attempt to negate with overflow")
public fun I128.strictAbs(): I128 = checkedAbs() ?: error("attempt to compute abs with overflow")
public fun I128.strictPow(exp: UInt): I128 = checkedPow(exp) ?: error("attempt to pow with overflow")

public fun I128.saturatingAdd(rhs: I128): I128 =
    overflowingAdd(rhs).let { (v, ovf) ->
        if (!ovf) {
            v
        } else if (this.high < 0L) {
            I128.MIN
        } else {
            I128.MAX
        }
    }

public fun I128.saturatingSub(rhs: I128): I128 =
    overflowingSub(rhs).let { (v, ovf) ->
        if (!ovf) {
            v
        } else if (this.high < 0L) {
            I128.MIN
        } else {
            I128.MAX
        }
    }

public fun I128.saturatingMul(rhs: I128): I128 =
    checkedMul(rhs) ?: if ((this.high < 0L) xor (rhs.high < 0L)) I128.MIN else I128.MAX

public fun I128.saturatingDiv(rhs: I128): I128 =
    if (this == I128.MIN && rhs == I128.fromInt(-1)) I128.MAX else this / rhs

public fun I128.saturatingNeg(): I128 = if (this == I128.MIN) I128.MAX else -this
public fun I128.saturatingAbs(): I128 =
    if (this == I128.MIN) {
        I128.MAX
    } else if (this.high < 0L) {
        -this
    } else {
        this
    }

public fun I128.saturatingPow(exp: UInt): I128 {
    if (exp == 0u) return I128.ONE
    if (this == I128.ZERO) return I128.ZERO
    if (this == I128.ONE) return I128.ONE
    val negOne = I128.fromInt(-1)
    if (this == negOne) return if ((exp and 1u) == 0u) I128.ONE else negOne
    val ovfVal = if (this.high < 0L && (exp and 1u) != 0u) I128.MIN else I128.MAX
    var acc = I128.ONE
    var b = this
    var e = exp
    while (e > 0u) {
        if ((e and 1u) != 0u) acc = acc.checkedMul(b) ?: return ovfVal
        e = e shr 1
        if (e > 0u) b = b.checkedMul(b) ?: return ovfVal
    }
    return acc
}

public fun I128.countZeros(): UInt = this.toU128().countZeros()
public fun I128.leadingZeros(): UInt = this.toU128().leadingZeros()
public fun I128.trailingZeros(): UInt = this.toU128().trailingZeros()
public fun I128.leadingOnes(): UInt = this.toU128().leadingOnes()
public fun I128.trailingOnes(): UInt = this.toU128().trailingOnes()
public fun I128.reverseBits(): I128 = this.toU128().reverseBits().toI128()
public fun I128.swapBytes(): I128 = this.toU128().swapBytes().toI128()
public fun I128.toBe(): I128 = if (kmIsBigEndian()) this else swapBytes()
public fun I128.toLe(): I128 = if (kmIsBigEndian()) swapBytes() else this
public fun I128.Companion.fromBe(x: I128): I128 = x.toBe()
public fun I128.Companion.fromLe(x: I128): I128 = x.toLe()
public fun I128.rotateLeft(n: UInt): I128 = this.toU128().rotateLeft(n).toI128()
public fun I128.rotateRight(n: UInt): I128 = this.toU128().rotateRight(n).toI128()
public fun I128.midpoint(rhs: I128): I128 = ((this and rhs) + ((this xor rhs) shr 1))
public fun I128.absDiff(other: I128): U128 = if (this >= other) (this - other).toU128() else (other - this).toU128()
public fun I128.isMultipleOf(rhs: I128): Boolean =
    if (rhs == I128.ZERO) this == I128.ZERO else !(this == I128.MIN && rhs == I128.fromInt(-1)) && ((this % rhs) == I128.ZERO)

public fun I128.divCeil(rhs: I128): I128 {
    val d = this / rhs
    val r = this % rhs
    val corr = if ((this.high < 0L) == (rhs.high < 0L) && r != I128.ZERO) I128.ONE else I128.ZERO
    return d + corr
}

public fun I128.ilog2(): UInt {
    check(this > I128.ZERO) { "ilog2 requires positive argument" }
    return 127u - leadingZeros()
}

public fun I128.ilog10(): UInt {
    check(this > I128.ZERO) { "ilog10 requires positive argument" }
    return this.toU128().ilog10()
}

public fun I128.isqrt(): I128 {
    check(this >= I128.ZERO) { "argument of integer square root cannot be negative" }
    return isqrtU128(this.toU128()).toI128()
}

public fun I128.castUnsigned(): U128 = this.toU128()
public fun I128.abs(): I128 = if (this.high < 0L) -this else this
public fun I128.unsignedAbs(): U128 = if (this.high < 0L) (U128.ZERO - this.toU128()) else this.toU128()
public fun I128.neg(): I128 = -this
public fun I128.signum(): I128 =
    if (this > I128.ZERO) {
        I128.ONE
    } else if (this < I128.ZERO) {
        I128.fromInt(-1)
    } else {
        I128.ZERO
    }
public fun I128.isPositive(): Boolean = this > I128.ZERO
public fun I128.isNegative(): Boolean = this < I128.ZERO

public fun I128.clampMagnitude(limit: U128): I128 {
    val mag = unsignedAbs()
    if (mag <= limit) return this
    return if (this.high < 0L) (U128.ZERO - limit).toI128() else limit.toI128()
}
