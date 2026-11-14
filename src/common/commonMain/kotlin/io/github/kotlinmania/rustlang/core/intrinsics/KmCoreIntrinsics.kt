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
