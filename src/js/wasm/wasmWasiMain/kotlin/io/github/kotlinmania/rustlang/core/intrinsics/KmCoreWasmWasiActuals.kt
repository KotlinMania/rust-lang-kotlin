// rust-kotlin: manual
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

public actual fun kmCtpopU8(value: UByte): UInt = KmLinearMemoryArena.ctpopU8(value)
public actual fun kmCtpopU16(value: UShort): UInt = KmLinearMemoryArena.ctpopU16(value)
public actual fun kmCtpopU32(value: UInt): UInt = KmLinearMemoryArena.ctpopU32(value)
public actual fun kmCtpopU128(lo: ULong, hi: ULong): UInt = KmLinearMemoryArena.ctpopU128(lo, hi)
public actual fun kmCtlzU8(value: UByte): UInt = KmLinearMemoryArena.ctlzU8(value)
public actual fun kmCtlzU16(value: UShort): UInt = KmLinearMemoryArena.ctlzU16(value)
public actual fun kmCtlzU32(value: UInt): UInt = KmLinearMemoryArena.ctlzU32(value)
public actual fun kmCtlzU128(lo: ULong, hi: ULong): UInt = KmLinearMemoryArena.ctlzU128(lo, hi)
public actual fun kmCttzU8(value: UByte): UInt = KmLinearMemoryArena.cttzU8(value)
public actual fun kmCttzU16(value: UShort): UInt = KmLinearMemoryArena.cttzU16(value)
public actual fun kmCttzU32(value: UInt): UInt = KmLinearMemoryArena.cttzU32(value)
public actual fun kmCttzU128(lo: ULong, hi: ULong): UInt = KmLinearMemoryArena.cttzU128(lo, hi)
public actual fun kmBswapU128(lo: ULong, hi: ULong): U128ValueOutcome = KmLinearMemoryArena.bswapU128(lo, hi)
public actual fun kmBitreverseU8(value: UByte): UByte = KmLinearMemoryArena.bitreverseU8(value)
public actual fun kmBitreverseU16(value: UShort): UShort = KmLinearMemoryArena.bitreverseU16(value)
public actual fun kmBitreverseU32(value: UInt): UInt = KmLinearMemoryArena.bitreverseU32(value)
public actual fun kmBitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome = KmLinearMemoryArena.bitreverseU128(lo, hi)
public actual fun kmRotateLeftU8(value: UByte, n: UInt): UByte = KmLinearMemoryArena.rotateLeftU8(value, n)
public actual fun kmRotateLeftU16(value: UShort, n: UInt): UShort = KmLinearMemoryArena.rotateLeftU16(value, n)
public actual fun kmRotateLeftU32(value: UInt, n: UInt): UInt = KmLinearMemoryArena.rotateLeftU32(value, n)
public actual fun kmRotateLeftU64(value: ULong, n: UInt): ULong = KmLinearMemoryArena.rotateLeftU64(value, n)
public actual fun kmRotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome = KmLinearMemoryArena.rotateLeftU128(lo, hi, n)
public actual fun kmRotateRightU8(value: UByte, n: UInt): UByte = KmLinearMemoryArena.rotateRightU8(value, n)
public actual fun kmRotateRightU16(value: UShort, n: UInt): UShort = KmLinearMemoryArena.rotateRightU16(value, n)
public actual fun kmRotateRightU32(value: UInt, n: UInt): UInt = KmLinearMemoryArena.rotateRightU32(value, n)
public actual fun kmRotateRightU64(value: ULong, n: UInt): ULong = KmLinearMemoryArena.rotateRightU64(value, n)
public actual fun kmRotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome = KmLinearMemoryArena.rotateRightU128(lo, hi, n)
public actual fun kmAddWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome = KmLinearMemoryArena.addWithOverflowU8(a, b)
public actual fun kmAddWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome = KmLinearMemoryArena.addWithOverflowU16(a, b)
public actual fun kmAddWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome = KmLinearMemoryArena.addWithOverflowU32(a, b)
public actual fun kmAddWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome = KmLinearMemoryArena.addWithOverflowU64(a, b)
public actual fun kmAddWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome = KmLinearMemoryArena.addWithOverflowU128(aLo, aHi, bLo, bHi)
public actual fun kmAddWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome = KmLinearMemoryArena.addWithOverflowI8(a, b)
public actual fun kmAddWithOverflowI16(a: Short, b: Short): OverflowI16Outcome = KmLinearMemoryArena.addWithOverflowI16(a, b)
public actual fun kmAddWithOverflowI32(a: Int, b: Int): OverflowI32Outcome = KmLinearMemoryArena.addWithOverflowI32(a, b)
public actual fun kmAddWithOverflowI64(a: Long, b: Long): OverflowI64Outcome = KmLinearMemoryArena.addWithOverflowI64(a, b)
public actual fun kmAddWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome = KmLinearMemoryArena.addWithOverflowI128(aLo, aHi, bLo, bHi)
public actual fun kmSubWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome = KmLinearMemoryArena.subWithOverflowU8(a, b)
public actual fun kmSubWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome = KmLinearMemoryArena.subWithOverflowU16(a, b)
public actual fun kmSubWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome = KmLinearMemoryArena.subWithOverflowU32(a, b)
public actual fun kmSubWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome = KmLinearMemoryArena.subWithOverflowU64(a, b)
public actual fun kmSubWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome = KmLinearMemoryArena.subWithOverflowU128(aLo, aHi, bLo, bHi)
public actual fun kmSubWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome = KmLinearMemoryArena.subWithOverflowI8(a, b)
public actual fun kmSubWithOverflowI16(a: Short, b: Short): OverflowI16Outcome = KmLinearMemoryArena.subWithOverflowI16(a, b)
public actual fun kmSubWithOverflowI32(a: Int, b: Int): OverflowI32Outcome = KmLinearMemoryArena.subWithOverflowI32(a, b)
public actual fun kmSubWithOverflowI64(a: Long, b: Long): OverflowI64Outcome = KmLinearMemoryArena.subWithOverflowI64(a, b)
public actual fun kmSubWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome = KmLinearMemoryArena.subWithOverflowI128(aLo, aHi, bLo, bHi)
public actual fun kmMulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome = KmLinearMemoryArena.mulWithOverflowU8(a, b)
public actual fun kmMulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome = KmLinearMemoryArena.mulWithOverflowU16(a, b)
public actual fun kmMulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome = KmLinearMemoryArena.mulWithOverflowU32(a, b)
public actual fun kmMulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome = KmLinearMemoryArena.mulWithOverflowU64(a, b)
public actual fun kmMulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome = KmLinearMemoryArena.mulWithOverflowU128(aLo, aHi, bLo, bHi)
public actual fun kmMulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome = KmLinearMemoryArena.mulWithOverflowI8(a, b)
public actual fun kmMulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome = KmLinearMemoryArena.mulWithOverflowI16(a, b)
public actual fun kmMulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome = KmLinearMemoryArena.mulWithOverflowI32(a, b)
public actual fun kmMulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome = KmLinearMemoryArena.mulWithOverflowI64(a, b)
public actual fun kmMulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome = KmLinearMemoryArena.mulWithOverflowI128(aLo, aHi, bLo, bHi)
public actual fun kmSaturatingAddU8(a: UByte, b: UByte): UByte = KmLinearMemoryArena.saturatingAddU8(a, b)
public actual fun kmSaturatingAddU16(a: UShort, b: UShort): UShort = KmLinearMemoryArena.saturatingAddU16(a, b)
public actual fun kmSaturatingAddU32(a: UInt, b: UInt): UInt = KmLinearMemoryArena.saturatingAddU32(a, b)
public actual fun kmSaturatingAddU64(a: ULong, b: ULong): ULong = KmLinearMemoryArena.saturatingAddU64(a, b)
public actual fun kmSaturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome = KmLinearMemoryArena.saturatingAddU128(aLo, aHi, bLo, bHi)
public actual fun kmSaturatingAddI8(a: Byte, b: Byte): Byte = KmLinearMemoryArena.saturatingAddI8(a, b)
public actual fun kmSaturatingAddI16(a: Short, b: Short): Short = KmLinearMemoryArena.saturatingAddI16(a, b)
public actual fun kmSaturatingAddI32(a: Int, b: Int): Int = KmLinearMemoryArena.saturatingAddI32(a, b)
public actual fun kmSaturatingAddI64(a: Long, b: Long): Long = KmLinearMemoryArena.saturatingAddI64(a, b)
public actual fun kmSaturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome = KmLinearMemoryArena.saturatingAddI128(aLo, aHi, bLo, bHi)
public actual fun kmSaturatingSubU8(a: UByte, b: UByte): UByte = KmLinearMemoryArena.saturatingSubU8(a, b)
public actual fun kmSaturatingSubU16(a: UShort, b: UShort): UShort = KmLinearMemoryArena.saturatingSubU16(a, b)
public actual fun kmSaturatingSubU32(a: UInt, b: UInt): UInt = KmLinearMemoryArena.saturatingSubU32(a, b)
public actual fun kmSaturatingSubU64(a: ULong, b: ULong): ULong = KmLinearMemoryArena.saturatingSubU64(a, b)
public actual fun kmSaturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome = KmLinearMemoryArena.saturatingSubU128(aLo, aHi, bLo, bHi)
public actual fun kmSaturatingSubI8(a: Byte, b: Byte): Byte = KmLinearMemoryArena.saturatingSubI8(a, b)
public actual fun kmSaturatingSubI16(a: Short, b: Short): Short = KmLinearMemoryArena.saturatingSubI16(a, b)
public actual fun kmSaturatingSubI32(a: Int, b: Int): Int = KmLinearMemoryArena.saturatingSubI32(a, b)
public actual fun kmSaturatingSubI64(a: Long, b: Long): Long = KmLinearMemoryArena.saturatingSubI64(a, b)
public actual fun kmSaturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome = KmLinearMemoryArena.saturatingSubI128(aLo, aHi, bLo, bHi)
public actual fun kmU128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome = KmLinearMemoryArena.u128Add(aLo, aHi, bLo, bHi)
public actual fun kmU128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome = KmLinearMemoryArena.u128Sub(aLo, aHi, bLo, bHi)
public actual fun kmU128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome = KmLinearMemoryArena.u128Mul(aLo, aHi, bLo, bHi)
public actual fun kmU128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome = KmLinearMemoryArena.u128DivRem(aLo, aHi, bLo, bHi)
public actual fun kmI128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome = KmLinearMemoryArena.i128DivRem(aLo, aHi, bLo, bHi)
public actual fun kmU128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome = KmLinearMemoryArena.u128Shl(aLo, aHi, shift)
public actual fun kmU128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome = KmLinearMemoryArena.u128Shr(aLo, aHi, shift)
public actual fun kmI128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome = KmLinearMemoryArena.i128Shr(aLo, aHi, shift)
public actual fun kmSinf32(x: Float): Float = KmLinearMemoryArena.sinf32(x)
public actual fun kmSinf64(x: Double): Double = KmLinearMemoryArena.sinf64(x)
public actual fun kmCosf32(x: Float): Float = KmLinearMemoryArena.cosf32(x)
public actual fun kmCosf64(x: Double): Double = KmLinearMemoryArena.cosf64(x)
public actual fun kmExpf32(x: Float): Float = KmLinearMemoryArena.expf32(x)
public actual fun kmExpf64(x: Double): Double = KmLinearMemoryArena.expf64(x)
public actual fun kmExp2f32(x: Float): Float = KmLinearMemoryArena.exp2f32(x)
public actual fun kmExp2f64(x: Double): Double = KmLinearMemoryArena.exp2f64(x)
public actual fun kmLogf32(x: Float): Float = KmLinearMemoryArena.logf32(x)
public actual fun kmLogf64(x: Double): Double = KmLinearMemoryArena.logf64(x)
public actual fun kmLog2f32(x: Float): Float = KmLinearMemoryArena.log2f32(x)
public actual fun kmLog2f64(x: Double): Double = KmLinearMemoryArena.log2f64(x)
public actual fun kmLog10f32(x: Float): Float = KmLinearMemoryArena.log10f32(x)
public actual fun kmLog10f64(x: Double): Double = KmLinearMemoryArena.log10f64(x)
public actual fun kmTruncf32(x: Float): Float = KmLinearMemoryArena.truncf32(x)
public actual fun kmTruncf64(x: Double): Double = KmLinearMemoryArena.truncf64(x)
public actual fun kmRoundf32(x: Float): Float = KmLinearMemoryArena.roundf32(x)
public actual fun kmRoundf64(x: Double): Double = KmLinearMemoryArena.roundf64(x)
public actual fun kmRoundTiesEvenf32(x: Float): Float = KmLinearMemoryArena.roundTiesEvenf32(x)
public actual fun kmRoundTiesEvenf64(x: Double): Double = KmLinearMemoryArena.roundTiesEvenf64(x)
public actual fun kmCeilf32(x: Float): Float = KmLinearMemoryArena.ceilf32(x)
public actual fun kmCeilf64(x: Double): Double = KmLinearMemoryArena.ceilf64(x)
public actual fun kmFloorf32(x: Float): Float = KmLinearMemoryArena.floorf32(x)
public actual fun kmFloorf64(x: Double): Double = KmLinearMemoryArena.floorf64(x)
public actual fun kmFabsf32(x: Float): Float = KmLinearMemoryArena.fabsf32(x)
public actual fun kmFabsf64(x: Double): Double = KmLinearMemoryArena.fabsf64(x)
public actual fun kmPowf32(a: Float, b: Float): Float = KmLinearMemoryArena.powf32(a, b)
public actual fun kmPowf64(a: Double, b: Double): Double = KmLinearMemoryArena.powf64(a, b)
public actual fun kmPowif32(x: Float, exp: Int): Float = KmLinearMemoryArena.powif32(x, exp)
public actual fun kmPowif64(x: Double, exp: Int): Double = KmLinearMemoryArena.powif64(x, exp)
public actual fun kmCopysignf32(a: Float, b: Float): Float = KmLinearMemoryArena.copysignf32(a, b)
public actual fun kmCopysignf64(a: Double, b: Double): Double = KmLinearMemoryArena.copysignf64(a, b)
public actual fun kmMinnumf32(a: Float, b: Float): Float = KmLinearMemoryArena.minnumf32(a, b)
public actual fun kmMinnumf64(a: Double, b: Double): Double = KmLinearMemoryArena.minnumf64(a, b)
public actual fun kmMaxnumf32(a: Float, b: Float): Float = KmLinearMemoryArena.maxnumf32(a, b)
public actual fun kmMaxnumf64(a: Double, b: Double): Double = KmLinearMemoryArena.maxnumf64(a, b)
public actual fun kmFloatToU64Unchecked(v: Float): ULong = KmLinearMemoryArena.floatToU64Unchecked(v)
public actual fun kmDoubleToU64Unchecked(v: Double): ULong = KmLinearMemoryArena.doubleToU64Unchecked(v)
public actual fun kmFloatToU128Unchecked(v: Float): U128ValueOutcome = KmLinearMemoryArena.floatToU128Unchecked(v)
public actual fun kmDoubleToU128Unchecked(v: Double): U128ValueOutcome = KmLinearMemoryArena.doubleToU128Unchecked(v)

internal class KmWasmWasiCleanState(
    private var cleanup: (() -> Unit)?,
) {
    private var state: Int = 0

    fun disarm() {
        if (state == 0) {
            state = 1
            cleanup = null
        }
    }

    fun clean() {
        if (state == 0) {
            state = 2
            val action = cleanup
            cleanup = null
            action?.invoke()
        }
    }
}

public actual class KmResourceCleaner internal actual constructor() : KmForgettableResource {
    private var cleanState: KmWasmWasiCleanState? = null

    internal fun attach(state: KmWasmWasiCleanState) {
        cleanState = state
    }

    public actual fun disarm() {
        val target = cleanState
        cleanState = null
        target?.disarm()
    }

    public actual fun cleanNow() {
        val target = cleanState
        cleanState = null
        target?.clean()
    }

    public actual override fun disarmCleaner(): Unit = disarm()

    public actual override fun close(): Unit = cleanNow()
}

public actual fun kmRegisterResourceCleaner(owner: Any, cleanup: () -> Unit): KmResourceCleaner {
    val state = KmWasmWasiCleanState(cleanup)
    return KmResourceCleaner().apply { attach(state) }
}
