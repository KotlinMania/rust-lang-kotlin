package io.github.kotlinmania.rustlang.core.intrinsics

public actual fun kmLoadU8(addr: Long): UByte = KmLinearMemoryArena.loadU8(addr)
public actual fun kmLoadU16(addr: Long): UShort = KmLinearMemoryArena.loadU16(addr)
public actual fun kmLoadU32(addr: Long): UInt = KmLinearMemoryArena.loadU32(addr)
public actual fun kmLoadU64(addr: Long): ULong = KmLinearMemoryArena.loadU64(addr)
public actual fun kmLoadF32(addr: Long): Float = KmLinearMemoryArena.loadF32(addr)
public actual fun kmLoadF64(addr: Long): Double = KmLinearMemoryArena.loadF64(addr)

public actual fun kmStoreU8(addr: Long, value: UByte): Unit = KmLinearMemoryArena.storeU8(addr, value)
public actual fun kmStoreU16(addr: Long, value: UShort): Unit = KmLinearMemoryArena.storeU16(addr, value)
public actual fun kmStoreU32(addr: Long, value: UInt): Unit = KmLinearMemoryArena.storeU32(addr, value)
public actual fun kmStoreU64(addr: Long, value: ULong): Unit = KmLinearMemoryArena.storeU64(addr, value)
public actual fun kmStoreF32(addr: Long, value: Float): Unit = KmLinearMemoryArena.storeF32(addr, value)
public actual fun kmStoreF64(addr: Long, value: Double): Unit = KmLinearMemoryArena.storeF64(addr, value)

public actual fun kmVolatileLoadU8(addr: Long): UByte = KmLinearMemoryArena.loadU8(addr)
public actual fun kmVolatileLoadU16(addr: Long): UShort = KmLinearMemoryArena.loadU16(addr)
public actual fun kmVolatileLoadU32(addr: Long): UInt = KmLinearMemoryArena.loadU32(addr)
public actual fun kmVolatileLoadU64(addr: Long): ULong = KmLinearMemoryArena.loadU64(addr)

public actual fun kmVolatileStoreU8(addr: Long, value: UByte): Unit = KmLinearMemoryArena.storeU8(addr, value)
public actual fun kmVolatileStoreU16(addr: Long, value: UShort): Unit = KmLinearMemoryArena.storeU16(addr, value)
public actual fun kmVolatileStoreU32(addr: Long, value: UInt): Unit = KmLinearMemoryArena.storeU32(addr, value)
public actual fun kmVolatileStoreU64(addr: Long, value: ULong): Unit = KmLinearMemoryArena.storeU64(addr, value)

public actual fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long): Unit =
    KmLinearMemoryArena.copyNonoverlapping(dst, src, bytes)
public actual fun kmCopy(dst: Long, src: Long, bytes: Long): Unit =
    KmLinearMemoryArena.copy(dst, src, bytes)
public actual fun kmWriteBytes(dst: Long, value: UByte, bytes: Long): Unit =
    KmLinearMemoryArena.writeBytes(dst, value, bytes)
public actual fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    KmLinearMemoryArena.compareBytes(left, right, bytes)

public actual fun kmAlloc(size: Long, align: Long): Long = KmLinearMemoryArena.alloc(size, align)
public actual fun kmAllocZeroed(size: Long, align: Long): Long = KmLinearMemoryArena.allocZeroed(size, align)
public actual fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    KmLinearMemoryArena.realloc(ptr, oldSize, align, newSize)
public actual fun kmDealloc(ptr: Long, size: Long, align: Long): Unit = KmLinearMemoryArena.dealloc(ptr, size, align)

public actual fun kmAtomicFence(ord: Int) {}
public actual fun kmAtomicSignalFence(ord: Int) {}
public actual fun kmAtomicLoadU8(addr: Long, ord: Int): UByte = KmLinearMemoryArena.loadU8(addr)
public actual fun kmAtomicLoadU16(addr: Long, ord: Int): UShort = KmLinearMemoryArena.loadU16(addr)
public actual fun kmAtomicLoadU32(addr: Long, ord: Int): UInt = KmLinearMemoryArena.loadU32(addr)
public actual fun kmAtomicLoadU64(addr: Long, ord: Int): ULong = KmLinearMemoryArena.loadU64(addr)
public actual fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int): Unit = KmLinearMemoryArena.storeU8(addr, value)
public actual fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int): Unit = KmLinearMemoryArena.storeU16(addr, value)
public actual fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int): Unit = KmLinearMemoryArena.storeU32(addr, value)
public actual fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int): Unit = KmLinearMemoryArena.storeU64(addr, value)
public actual fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte = KmLinearMemoryArena.atomicXchgU8(addr, value)
public actual fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort = KmLinearMemoryArena.atomicXchgU16(addr, value)
public actual fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt = KmLinearMemoryArena.atomicXchgU32(addr, value)
public actual fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong = KmLinearMemoryArena.atomicXchgU64(addr, value)
public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome =
    KmLinearMemoryArena.atomicCxchgU8(addr, expected, desired)
public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome =
    KmLinearMemoryArena.atomicCxchgU16(addr, expected, desired)
public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome =
    KmLinearMemoryArena.atomicCxchgU32(addr, expected, desired)
public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome =
    KmLinearMemoryArena.atomicCxchgU64(addr, expected, desired)
public actual fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte =
    KmLinearMemoryArena.atomicRmwU8(addr, value, op)
public actual fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort =
    KmLinearMemoryArena.atomicRmwU16(addr, value, op)
public actual fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt =
    KmLinearMemoryArena.atomicRmwU32(addr, value, op)
public actual fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong =
    KmLinearMemoryArena.atomicRmwU64(addr, value, op)

public actual fun kmCtpopU64(value: ULong): UInt = KmLinearMemoryArena.ctpopU64(value)
public actual fun kmCtlzU64(value: ULong): UInt = KmLinearMemoryArena.ctlzU64(value)
public actual fun kmCttzU64(value: ULong): UInt = KmLinearMemoryArena.cttzU64(value)
public actual fun kmBswapU16(value: UShort): UShort = KmLinearMemoryArena.bswapU16(value)
public actual fun kmBswapU32(value: UInt): UInt = KmLinearMemoryArena.bswapU32(value)
public actual fun kmBswapU64(value: ULong): ULong = KmLinearMemoryArena.bswapU64(value)
public actual fun kmBitreverseU64(value: ULong): ULong = KmLinearMemoryArena.bitreverseU64(value)
public actual fun kmFmaf32(a: Float, b: Float, c: Float): Float = KmLinearMemoryArena.fmaf32(a, b, c)
public actual fun kmFmaf64(a: Double, b: Double, c: Double): Double = KmLinearMemoryArena.fmaf64(a, b, c)
public actual fun kmSqrtf32(x: Float): Float = KmLinearMemoryArena.sqrtf32(x)
public actual fun kmSqrtf64(x: Double): Double = KmLinearMemoryArena.sqrtf64(x)
public actual fun kmAbort(): Nothing = error("Rust intrinsic abort triggered")

public actual fun kmSpinLoopHint() {}
public actual fun kmBlackBoxU64(value: ULong): ULong = value
public actual fun kmUmulhU64(a: ULong, b: ULong): ULong = KmLinearMemoryArena.umulhU64(a, b)
public actual fun kmSmulhI64(a: Long, b: Long): Long = KmLinearMemoryArena.smulhI64(a, b)
public actual fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome = KmLinearMemoryArena.atomicCxchgU128Pair(addr, expLo, expHi, desLo, desHi)
public actual fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long): Unit =
    KmLinearMemoryArena.simdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
public actual fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    KmLinearMemoryArena.simdDotF32(aAddr, bAddr, len)
