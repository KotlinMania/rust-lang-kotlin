package io.github.kotlinmania.rustlang.core.intrinsics

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public data class AtomicCxchgU8Outcome(
    public val previous: UByte,
    public val succeeded: Boolean,
)

public data class AtomicCxchgU16Outcome(
    public val previous: UShort,
    public val succeeded: Boolean,
)

public data class AtomicCxchgU32Outcome(
    public val previous: UInt,
    public val succeeded: Boolean,
)

public data class AtomicCxchgU64Outcome(
    public val previous: ULong,
    public val succeeded: Boolean,
)

public data class AtomicCxchgU128Outcome(
    public val prevLo: ULong,
    public val prevHi: ULong,
    public val succeeded: Boolean,
)

public data class OverflowU8Outcome(
    public val value: UByte,
    public val overflowed: Boolean,
)

public data class OverflowU16Outcome(
    public val value: UShort,
    public val overflowed: Boolean,
)

public data class OverflowU32Outcome(
    public val value: UInt,
    public val overflowed: Boolean,
)

public data class OverflowU64Outcome(
    public val value: ULong,
    public val overflowed: Boolean,
)

public data class OverflowU128Outcome(
    public val lo: ULong,
    public val hi: ULong,
    public val overflowed: Boolean,
)

public data class OverflowI8Outcome(
    public val value: Byte,
    public val overflowed: Boolean,
)

public data class OverflowI16Outcome(
    public val value: Short,
    public val overflowed: Boolean,
)

public data class OverflowI32Outcome(
    public val value: Int,
    public val overflowed: Boolean,
)

public data class OverflowI64Outcome(
    public val value: Long,
    public val overflowed: Boolean,
)

public data class OverflowI128Outcome(
    public val lo: ULong,
    public val hi: ULong,
    public val overflowed: Boolean,
)

public data class U128ValueOutcome(
    public val lo: ULong,
    public val hi: ULong,
)

public data class I128ValueOutcome(
    public val lo: ULong,
    public val hi: ULong,
)

public data class U128DivRemOutcome(
    public val qLo: ULong,
    public val qHi: ULong,
    public val rLo: ULong,
    public val rHi: ULong,
)

public data class I128DivRemOutcome(
    public val qLo: ULong,
    public val qHi: ULong,
    public val rLo: ULong,
    public val rHi: ULong,
)

public expect fun kmLoadU8(addr: Long): UByte
public expect fun kmLoadU16(addr: Long): UShort
public expect fun kmLoadU32(addr: Long): UInt
public expect fun kmLoadU64(addr: Long): ULong
public expect fun kmLoadF32(addr: Long): Float
public expect fun kmLoadF64(addr: Long): Double

public expect fun kmStoreU8(addr: Long, value: UByte)
public expect fun kmStoreU16(addr: Long, value: UShort)
public expect fun kmStoreU32(addr: Long, value: UInt)
public expect fun kmStoreU64(addr: Long, value: ULong)
public expect fun kmStoreF32(addr: Long, value: Float)
public expect fun kmStoreF64(addr: Long, value: Double)

public expect fun kmVolatileLoadU8(addr: Long): UByte
public expect fun kmVolatileLoadU16(addr: Long): UShort
public expect fun kmVolatileLoadU32(addr: Long): UInt
public expect fun kmVolatileLoadU64(addr: Long): ULong

public expect fun kmVolatileStoreU8(addr: Long, value: UByte)
public expect fun kmVolatileStoreU16(addr: Long, value: UShort)
public expect fun kmVolatileStoreU32(addr: Long, value: UInt)
public expect fun kmVolatileStoreU64(addr: Long, value: ULong)

public expect fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long)
public expect fun kmCopy(dst: Long, src: Long, bytes: Long)
public expect fun kmWriteBytes(dst: Long, value: UByte, bytes: Long)
public expect fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int

public expect fun kmAlloc(size: Long, align: Long): Long
public expect fun kmAllocZeroed(size: Long, align: Long): Long
public expect fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long
public expect fun kmDealloc(ptr: Long, size: Long, align: Long)

public expect fun kmAtomicFence(ord: Int)
public expect fun kmAtomicSignalFence(ord: Int)
public expect fun kmAtomicLoadU8(addr: Long, ord: Int): UByte
public expect fun kmAtomicLoadU16(addr: Long, ord: Int): UShort
public expect fun kmAtomicLoadU32(addr: Long, ord: Int): UInt
public expect fun kmAtomicLoadU64(addr: Long, ord: Int): ULong
public expect fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int)
public expect fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int)
public expect fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int)
public expect fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int)
public expect fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte
public expect fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort
public expect fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt
public expect fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong
public expect fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome
public expect fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome
public expect fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome
public expect fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome
public expect fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte
public expect fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort
public expect fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt
public expect fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong

public expect fun kmCtpopU64(value: ULong): UInt
public expect fun kmCtlzU64(value: ULong): UInt
public expect fun kmCttzU64(value: ULong): UInt
public expect fun kmBswapU16(value: UShort): UShort
public expect fun kmBswapU32(value: UInt): UInt
public expect fun kmBswapU64(value: ULong): ULong
public expect fun kmBitreverseU64(value: ULong): ULong
public expect fun kmFmaf32(a: Float, b: Float, c: Float): Float
public expect fun kmFmaf64(a: Double, b: Double, c: Double): Double
public expect fun kmSqrtf32(x: Float): Float
public expect fun kmSqrtf64(x: Double): Double
public expect fun kmAbort(): Nothing

public expect fun kmSpinLoopHint()
public expect fun kmBlackBoxU64(value: ULong): ULong
public expect fun kmUmulhU64(a: ULong, b: ULong): ULong
public expect fun kmSmulhI64(a: Long, b: Long): Long
public expect fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome
public expect fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long)
public expect fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float

public expect fun kmCtpopU8(value: UByte): UInt
public expect fun kmCtpopU16(value: UShort): UInt
public expect fun kmCtpopU32(value: UInt): UInt
public expect fun kmCtpopU128(lo: ULong, hi: ULong): UInt
public expect fun kmCtlzU8(value: UByte): UInt
public expect fun kmCtlzU16(value: UShort): UInt
public expect fun kmCtlzU32(value: UInt): UInt
public expect fun kmCtlzU128(lo: ULong, hi: ULong): UInt
public expect fun kmCttzU8(value: UByte): UInt
public expect fun kmCttzU16(value: UShort): UInt
public expect fun kmCttzU32(value: UInt): UInt
public expect fun kmCttzU128(lo: ULong, hi: ULong): UInt
public expect fun kmBswapU128(lo: ULong, hi: ULong): U128ValueOutcome
public expect fun kmBitreverseU8(value: UByte): UByte
public expect fun kmBitreverseU16(value: UShort): UShort
public expect fun kmBitreverseU32(value: UInt): UInt
public expect fun kmBitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome
public expect fun kmRotateLeftU8(value: UByte, n: UInt): UByte
public expect fun kmRotateLeftU16(value: UShort, n: UInt): UShort
public expect fun kmRotateLeftU32(value: UInt, n: UInt): UInt
public expect fun kmRotateLeftU64(value: ULong, n: UInt): ULong
public expect fun kmRotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome
public expect fun kmRotateRightU8(value: UByte, n: UInt): UByte
public expect fun kmRotateRightU16(value: UShort, n: UInt): UShort
public expect fun kmRotateRightU32(value: UInt, n: UInt): UInt
public expect fun kmRotateRightU64(value: ULong, n: UInt): ULong
public expect fun kmRotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome
public expect fun kmAddWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome
public expect fun kmAddWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome
public expect fun kmAddWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome
public expect fun kmAddWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome
public expect fun kmAddWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome
public expect fun kmAddWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome
public expect fun kmAddWithOverflowI16(a: Short, b: Short): OverflowI16Outcome
public expect fun kmAddWithOverflowI32(a: Int, b: Int): OverflowI32Outcome
public expect fun kmAddWithOverflowI64(a: Long, b: Long): OverflowI64Outcome
public expect fun kmAddWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome
public expect fun kmSubWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome
public expect fun kmSubWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome
public expect fun kmSubWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome
public expect fun kmSubWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome
public expect fun kmSubWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome
public expect fun kmSubWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome
public expect fun kmSubWithOverflowI16(a: Short, b: Short): OverflowI16Outcome
public expect fun kmSubWithOverflowI32(a: Int, b: Int): OverflowI32Outcome
public expect fun kmSubWithOverflowI64(a: Long, b: Long): OverflowI64Outcome
public expect fun kmSubWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome
public expect fun kmMulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome
public expect fun kmMulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome
public expect fun kmMulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome
public expect fun kmMulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome
public expect fun kmMulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome
public expect fun kmMulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome
public expect fun kmMulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome
public expect fun kmMulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome
public expect fun kmMulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome
public expect fun kmMulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome
public expect fun kmSaturatingAddU8(a: UByte, b: UByte): UByte
public expect fun kmSaturatingAddU16(a: UShort, b: UShort): UShort
public expect fun kmSaturatingAddU32(a: UInt, b: UInt): UInt
public expect fun kmSaturatingAddU64(a: ULong, b: ULong): ULong
public expect fun kmSaturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome
public expect fun kmSaturatingAddI8(a: Byte, b: Byte): Byte
public expect fun kmSaturatingAddI16(a: Short, b: Short): Short
public expect fun kmSaturatingAddI32(a: Int, b: Int): Int
public expect fun kmSaturatingAddI64(a: Long, b: Long): Long
public expect fun kmSaturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome
public expect fun kmSaturatingSubU8(a: UByte, b: UByte): UByte
public expect fun kmSaturatingSubU16(a: UShort, b: UShort): UShort
public expect fun kmSaturatingSubU32(a: UInt, b: UInt): UInt
public expect fun kmSaturatingSubU64(a: ULong, b: ULong): ULong
public expect fun kmSaturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome
public expect fun kmSaturatingSubI8(a: Byte, b: Byte): Byte
public expect fun kmSaturatingSubI16(a: Short, b: Short): Short
public expect fun kmSaturatingSubI32(a: Int, b: Int): Int
public expect fun kmSaturatingSubI64(a: Long, b: Long): Long
public expect fun kmSaturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome
public expect fun kmU128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome
public expect fun kmU128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome
public expect fun kmU128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome
public expect fun kmU128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome
public expect fun kmI128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome
public expect fun kmU128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome
public expect fun kmU128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome
public expect fun kmI128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome
public expect fun kmSinf32(x: Float): Float
public expect fun kmSinf64(x: Double): Double
public expect fun kmCosf32(x: Float): Float
public expect fun kmCosf64(x: Double): Double
public expect fun kmExpf32(x: Float): Float
public expect fun kmExpf64(x: Double): Double
public expect fun kmExp2f32(x: Float): Float
public expect fun kmExp2f64(x: Double): Double
public expect fun kmLogf32(x: Float): Float
public expect fun kmLogf64(x: Double): Double
public expect fun kmLog2f32(x: Float): Float
public expect fun kmLog2f64(x: Double): Double
public expect fun kmLog10f32(x: Float): Float
public expect fun kmLog10f64(x: Double): Double
public expect fun kmTruncf32(x: Float): Float
public expect fun kmTruncf64(x: Double): Double
public expect fun kmRoundf32(x: Float): Float
public expect fun kmRoundf64(x: Double): Double
public expect fun kmRoundTiesEvenf32(x: Float): Float
public expect fun kmRoundTiesEvenf64(x: Double): Double
public expect fun kmCeilf32(x: Float): Float
public expect fun kmCeilf64(x: Double): Double
public expect fun kmFloorf32(x: Float): Float
public expect fun kmFloorf64(x: Double): Double
public expect fun kmFabsf32(x: Float): Float
public expect fun kmFabsf64(x: Double): Double
public expect fun kmPowf32(a: Float, b: Float): Float
public expect fun kmPowf64(a: Double, b: Double): Double
public expect fun kmPowif32(x: Float, exp: Int): Float
public expect fun kmPowif64(x: Double, exp: Int): Double
public expect fun kmCopysignf32(a: Float, b: Float): Float
public expect fun kmCopysignf64(a: Double, b: Double): Double
public expect fun kmMinnumf32(a: Float, b: Float): Float
public expect fun kmMinnumf64(a: Double, b: Double): Double
public expect fun kmMaxnumf32(a: Float, b: Float): Float
public expect fun kmMaxnumf64(a: Double, b: Double): Double
public expect fun kmFloatToU64Unchecked(v: Float): ULong
public expect fun kmDoubleToU64Unchecked(v: Double): ULong
public expect fun kmFloatToU128Unchecked(v: Float): U128ValueOutcome
public expect fun kmDoubleToU128Unchecked(v: Double): U128ValueOutcome

private val internedCStrPool: MutableMap<String, Long> = linkedMapOf()
private val internedWideStrPool: MutableMap<String, Long> = linkedMapOf()

public fun kmInternCStr(literal: String): ConstPtr {
    val existing = internedCStrPool[literal]
    if (existing != null) return ConstPtr(existing)
    val bytes = literal.encodeToByteArray()
    val addr = kmAlloc((bytes.size + 1).toLong(), 1L)
    for (index in bytes.indices) {
        kmStoreU8(addr + index.toLong(), bytes[index].toUByte())
    }
    kmStoreU8(addr + bytes.size.toLong(), 0u)
    internedCStrPool[literal] = addr
    return ConstPtr(addr)
}

public fun kmInternWideStr(literal: String): ConstPtr {
    val existing = internedWideStrPool[literal]
    if (existing != null) return ConstPtr(existing)
    val addr = kmAlloc(((literal.length + 1) * 2).toLong(), 2L)
    for (index in literal.indices) {
        kmStoreU16(addr + (index * 2).toLong(), literal[index].code.toUShort())
    }
    kmStoreU16(addr + (literal.length * 2).toLong(), 0u)
    internedWideStrPool[literal] = addr
    return ConstPtr(addr)
}
