@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.intrinsics

internal object KmCoreJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic external fun kmLoadU8(addr: Long): Byte

    @JvmStatic external fun kmLoadU16(addr: Long): Short

    @JvmStatic external fun kmLoadU32(addr: Long): Int

    @JvmStatic external fun kmLoadU64(addr: Long): Long

    @JvmStatic external fun kmLoadF32(addr: Long): Float

    @JvmStatic external fun kmLoadF64(addr: Long): Double

    @JvmStatic external fun kmStoreU8(addr: Long, value: Byte)

    @JvmStatic external fun kmStoreU16(addr: Long, value: Short)

    @JvmStatic external fun kmStoreU32(addr: Long, value: Int)

    @JvmStatic external fun kmStoreU64(addr: Long, value: Long)

    @JvmStatic external fun kmStoreF32(addr: Long, value: Float)

    @JvmStatic external fun kmStoreF64(addr: Long, value: Double)

    @JvmStatic external fun kmVolatileLoadU8(addr: Long): Byte

    @JvmStatic external fun kmVolatileLoadU16(addr: Long): Short

    @JvmStatic external fun kmVolatileLoadU32(addr: Long): Int

    @JvmStatic external fun kmVolatileLoadU64(addr: Long): Long

    @JvmStatic external fun kmVolatileStoreU8(addr: Long, value: Byte)

    @JvmStatic external fun kmVolatileStoreU16(addr: Long, value: Short)

    @JvmStatic external fun kmVolatileStoreU32(addr: Long, value: Int)

    @JvmStatic external fun kmVolatileStoreU64(addr: Long, value: Long)

    @JvmStatic external fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long)

    @JvmStatic external fun kmCopy(dst: Long, src: Long, bytes: Long)

    @JvmStatic external fun kmWriteBytes(dst: Long, value: Byte, bytes: Long)

    @JvmStatic external fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int

    @JvmStatic external fun kmAlloc(size: Long, align: Long): Long

    @JvmStatic external fun kmAllocZeroed(size: Long, align: Long): Long

    @JvmStatic external fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long

    @JvmStatic external fun kmDealloc(ptr: Long, size: Long, align: Long)

    @JvmStatic external fun kmAtomicFence(ord: Int)

    @JvmStatic external fun kmAtomicSignalFence(ord: Int)

    @JvmStatic external fun kmAtomicLoadU8(addr: Long, ord: Int): Byte

    @JvmStatic external fun kmAtomicLoadU16(addr: Long, ord: Int): Short

    @JvmStatic external fun kmAtomicLoadU32(addr: Long, ord: Int): Int

    @JvmStatic external fun kmAtomicLoadU64(addr: Long, ord: Int): Long

    @JvmStatic external fun kmAtomicStoreU8(addr: Long, value: Byte, ord: Int)

    @JvmStatic external fun kmAtomicStoreU16(addr: Long, value: Short, ord: Int)

    @JvmStatic external fun kmAtomicStoreU32(addr: Long, value: Int, ord: Int)

    @JvmStatic external fun kmAtomicStoreU64(addr: Long, value: Long, ord: Int)

    @JvmStatic external fun kmAtomicXchgU8(addr: Long, value: Byte, ord: Int): Byte

    @JvmStatic external fun kmAtomicXchgU16(addr: Long, value: Short, ord: Int): Short

    @JvmStatic external fun kmAtomicXchgU32(addr: Long, value: Int, ord: Int): Int

    @JvmStatic external fun kmAtomicXchgU64(addr: Long, value: Long, ord: Int): Long

    @JvmStatic external fun kmAtomicCxchgU8Packed(addr: Long, expected: Byte, desired: Byte, succ: Int, fail: Int): Long

    @JvmStatic external fun kmAtomicCxchgU16Packed(addr: Long, expected: Short, desired: Short, succ: Int, fail: Int): Long

    @JvmStatic external fun kmAtomicCxchgU32Packed(addr: Long, expected: Int, desired: Int, succ: Int, fail: Int): Long

    @JvmStatic external fun kmAtomicCxchgU64Out(addr: Long, expected: Long, desired: Long, succ: Int, fail: Int, outBuf: LongArray)

    @JvmStatic external fun kmAtomicRmwU8(addr: Long, value: Byte, op: Int, ord: Int): Byte

    @JvmStatic external fun kmAtomicRmwU16(addr: Long, value: Short, op: Int, ord: Int): Short

    @JvmStatic external fun kmAtomicRmwU32(addr: Long, value: Int, op: Int, ord: Int): Int

    @JvmStatic external fun kmAtomicRmwU64(addr: Long, value: Long, op: Int, ord: Int): Long

    @JvmStatic external fun kmCtpopU64(value: Long): Int

    @JvmStatic external fun kmCtlzU64(value: Long): Int

    @JvmStatic external fun kmCttzU64(value: Long): Int

    @JvmStatic external fun kmBswapU16(value: Short): Short

    @JvmStatic external fun kmBswapU32(value: Int): Int

    @JvmStatic external fun kmBswapU64(value: Long): Long

    @JvmStatic external fun kmBitreverseU64(value: Long): Long

    @JvmStatic external fun kmFmaf32(a: Float, b: Float, c: Float): Float

    @JvmStatic external fun kmFmaf64(a: Double, b: Double, c: Double): Double

    @JvmStatic external fun kmSqrtf32(x: Float): Float

    @JvmStatic external fun kmSqrtf64(x: Double): Double

    @JvmStatic external fun kmAbort()

    @JvmStatic external fun kmSpinLoopHint()

    @JvmStatic external fun kmBlackBoxU64(value: Long): Long

    @JvmStatic external fun kmUmulhU64(a: Long, b: Long): Long

    @JvmStatic external fun kmSmulhI64(a: Long, b: Long): Long

    @JvmStatic external fun kmAtomicCxchgU128Out(addr: Long, expLo: Long, expHi: Long, desLo: Long, desHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long)

    @JvmStatic external fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float

    @JvmStatic external fun kmCtpopU8(value: Byte): Int

    @JvmStatic external fun kmCtpopU16(value: Short): Int

    @JvmStatic external fun kmCtpopU32(value: Int): Int

    @JvmStatic external fun kmCtpopU128(lo: Long, hi: Long): Int

    @JvmStatic external fun kmCtlzU8(value: Byte): Int

    @JvmStatic external fun kmCtlzU16(value: Short): Int

    @JvmStatic external fun kmCtlzU32(value: Int): Int

    @JvmStatic external fun kmCtlzU128(lo: Long, hi: Long): Int

    @JvmStatic external fun kmCttzU8(value: Byte): Int

    @JvmStatic external fun kmCttzU16(value: Short): Int

    @JvmStatic external fun kmCttzU32(value: Int): Int

    @JvmStatic external fun kmCttzU128(lo: Long, hi: Long): Int

    @JvmStatic external fun kmBswapU128Out(lo: Long, hi: Long, outBuf: LongArray)

    @JvmStatic external fun kmBitreverseU8(value: Byte): Byte

    @JvmStatic external fun kmBitreverseU16(value: Short): Short

    @JvmStatic external fun kmBitreverseU32(value: Int): Int

    @JvmStatic external fun kmBitreverseU128Out(lo: Long, hi: Long, outBuf: LongArray)

    @JvmStatic external fun kmRotateLeftU8(value: Byte, n: Int): Byte

    @JvmStatic external fun kmRotateLeftU16(value: Short, n: Int): Short

    @JvmStatic external fun kmRotateLeftU32(value: Int, n: Int): Int

    @JvmStatic external fun kmRotateLeftU64(value: Long, n: Int): Long

    @JvmStatic external fun kmRotateLeftU128Out(lo: Long, hi: Long, n: Int, outBuf: LongArray)

    @JvmStatic external fun kmRotateRightU8(value: Byte, n: Int): Byte

    @JvmStatic external fun kmRotateRightU16(value: Short, n: Int): Short

    @JvmStatic external fun kmRotateRightU32(value: Int, n: Int): Int

    @JvmStatic external fun kmRotateRightU64(value: Long, n: Int): Long

    @JvmStatic external fun kmRotateRightU128Out(lo: Long, hi: Long, n: Int, outBuf: LongArray)

    @JvmStatic external fun kmAddWithOverflowU8Packed(a: Byte, b: Byte): Long

    @JvmStatic external fun kmAddWithOverflowU16Packed(a: Short, b: Short): Long

    @JvmStatic external fun kmAddWithOverflowU32Packed(a: Int, b: Int): Long

    @JvmStatic external fun kmAddWithOverflowU64Out(a: Long, b: Long, outBuf: LongArray)

    @JvmStatic external fun kmAddWithOverflowU128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmAddWithOverflowI8Packed(a: Byte, b: Byte): Long

    @JvmStatic external fun kmAddWithOverflowI16Packed(a: Short, b: Short): Long

    @JvmStatic external fun kmAddWithOverflowI32Packed(a: Int, b: Int): Long

    @JvmStatic external fun kmAddWithOverflowI64Out(a: Long, b: Long, outBuf: LongArray)

    @JvmStatic external fun kmAddWithOverflowI128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSubWithOverflowU8Packed(a: Byte, b: Byte): Long

    @JvmStatic external fun kmSubWithOverflowU16Packed(a: Short, b: Short): Long

    @JvmStatic external fun kmSubWithOverflowU32Packed(a: Int, b: Int): Long

    @JvmStatic external fun kmSubWithOverflowU64Out(a: Long, b: Long, outBuf: LongArray)

    @JvmStatic external fun kmSubWithOverflowU128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSubWithOverflowI8Packed(a: Byte, b: Byte): Long

    @JvmStatic external fun kmSubWithOverflowI16Packed(a: Short, b: Short): Long

    @JvmStatic external fun kmSubWithOverflowI32Packed(a: Int, b: Int): Long

    @JvmStatic external fun kmSubWithOverflowI64Out(a: Long, b: Long, outBuf: LongArray)

    @JvmStatic external fun kmSubWithOverflowI128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmMulWithOverflowU8Packed(a: Byte, b: Byte): Long

    @JvmStatic external fun kmMulWithOverflowU16Packed(a: Short, b: Short): Long

    @JvmStatic external fun kmMulWithOverflowU32Packed(a: Int, b: Int): Long

    @JvmStatic external fun kmMulWithOverflowU64Out(a: Long, b: Long, outBuf: LongArray)

    @JvmStatic external fun kmMulWithOverflowU128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmMulWithOverflowI8Packed(a: Byte, b: Byte): Long

    @JvmStatic external fun kmMulWithOverflowI16Packed(a: Short, b: Short): Long

    @JvmStatic external fun kmMulWithOverflowI32Packed(a: Int, b: Int): Long

    @JvmStatic external fun kmMulWithOverflowI64Out(a: Long, b: Long, outBuf: LongArray)

    @JvmStatic external fun kmMulWithOverflowI128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSaturatingAddU8(a: Byte, b: Byte): Byte

    @JvmStatic external fun kmSaturatingAddU16(a: Short, b: Short): Short

    @JvmStatic external fun kmSaturatingAddU32(a: Int, b: Int): Int

    @JvmStatic external fun kmSaturatingAddU64(a: Long, b: Long): Long

    @JvmStatic external fun kmSaturatingAddU128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSaturatingAddI8(a: Byte, b: Byte): Byte

    @JvmStatic external fun kmSaturatingAddI16(a: Short, b: Short): Short

    @JvmStatic external fun kmSaturatingAddI32(a: Int, b: Int): Int

    @JvmStatic external fun kmSaturatingAddI64(a: Long, b: Long): Long

    @JvmStatic external fun kmSaturatingAddI128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSaturatingSubU8(a: Byte, b: Byte): Byte

    @JvmStatic external fun kmSaturatingSubU16(a: Short, b: Short): Short

    @JvmStatic external fun kmSaturatingSubU32(a: Int, b: Int): Int

    @JvmStatic external fun kmSaturatingSubU64(a: Long, b: Long): Long

    @JvmStatic external fun kmSaturatingSubU128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmSaturatingSubI8(a: Byte, b: Byte): Byte

    @JvmStatic external fun kmSaturatingSubI16(a: Short, b: Short): Short

    @JvmStatic external fun kmSaturatingSubI32(a: Int, b: Int): Int

    @JvmStatic external fun kmSaturatingSubI64(a: Long, b: Long): Long

    @JvmStatic external fun kmSaturatingSubI128Out(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmU128AddOut(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmU128SubOut(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmU128MulOut(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmU128DivRemOut(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmI128DivRemOut(aLo: Long, aHi: Long, bLo: Long, bHi: Long, outBuf: LongArray)

    @JvmStatic external fun kmU128ShlOut(aLo: Long, aHi: Long, shift: Int, outBuf: LongArray)

    @JvmStatic external fun kmU128ShrOut(aLo: Long, aHi: Long, shift: Int, outBuf: LongArray)

    @JvmStatic external fun kmI128ShrOut(aLo: Long, aHi: Long, shift: Int, outBuf: LongArray)

    @JvmStatic external fun kmSinf32(x: Float): Float

    @JvmStatic external fun kmSinf64(x: Double): Double

    @JvmStatic external fun kmCosf32(x: Float): Float

    @JvmStatic external fun kmCosf64(x: Double): Double

    @JvmStatic external fun kmExpf32(x: Float): Float

    @JvmStatic external fun kmExpf64(x: Double): Double

    @JvmStatic external fun kmExp2f32(x: Float): Float

    @JvmStatic external fun kmExp2f64(x: Double): Double

    @JvmStatic external fun kmLogf32(x: Float): Float

    @JvmStatic external fun kmLogf64(x: Double): Double

    @JvmStatic external fun kmLog2f32(x: Float): Float

    @JvmStatic external fun kmLog2f64(x: Double): Double

    @JvmStatic external fun kmLog10f32(x: Float): Float

    @JvmStatic external fun kmLog10f64(x: Double): Double

    @JvmStatic external fun kmTruncf32(x: Float): Float

    @JvmStatic external fun kmTruncf64(x: Double): Double

    @JvmStatic external fun kmRoundf32(x: Float): Float

    @JvmStatic external fun kmRoundf64(x: Double): Double

    @JvmStatic external fun kmRoundTiesEvenf32(x: Float): Float

    @JvmStatic external fun kmRoundTiesEvenf64(x: Double): Double

    @JvmStatic external fun kmCeilf32(x: Float): Float

    @JvmStatic external fun kmCeilf64(x: Double): Double

    @JvmStatic external fun kmFloorf32(x: Float): Float

    @JvmStatic external fun kmFloorf64(x: Double): Double

    @JvmStatic external fun kmFabsf32(x: Float): Float

    @JvmStatic external fun kmFabsf64(x: Double): Double

    @JvmStatic external fun kmPowf32(a: Float, b: Float): Float

    @JvmStatic external fun kmPowf64(a: Double, b: Double): Double

    @JvmStatic external fun kmPowif32(x: Float, exp: Int): Float

    @JvmStatic external fun kmPowif64(x: Double, exp: Int): Double

    @JvmStatic external fun kmCopysignf32(a: Float, b: Float): Float

    @JvmStatic external fun kmCopysignf64(a: Double, b: Double): Double

    @JvmStatic external fun kmMinnumf32(a: Float, b: Float): Float

    @JvmStatic external fun kmMinnumf64(a: Double, b: Double): Double

    @JvmStatic external fun kmMaxnumf32(a: Float, b: Float): Float

    @JvmStatic external fun kmMaxnumf64(a: Double, b: Double): Double

    @JvmStatic external fun kmFloatToU64Unchecked(v: Float): Long

    @JvmStatic external fun kmDoubleToU64Unchecked(v: Double): Long

    @JvmStatic external fun kmFloatToU128UncheckedOut(v: Float, outBuf: LongArray)

    @JvmStatic external fun kmDoubleToU128UncheckedOut(v: Double, outBuf: LongArray)
}

public actual fun kmLoadU8(addr: Long): UByte = KmCoreJniBridge.kmLoadU8(addr).toUByte()
public actual fun kmLoadU16(addr: Long): UShort = KmCoreJniBridge.kmLoadU16(addr).toUShort()
public actual fun kmLoadU32(addr: Long): UInt = KmCoreJniBridge.kmLoadU32(addr).toUInt()
public actual fun kmLoadU64(addr: Long): ULong = KmCoreJniBridge.kmLoadU64(addr).toULong()
public actual fun kmLoadF32(addr: Long): Float = KmCoreJniBridge.kmLoadF32(addr)
public actual fun kmLoadF64(addr: Long): Double = KmCoreJniBridge.kmLoadF64(addr)

public actual fun kmStoreU8(addr: Long, value: UByte): Unit = KmCoreJniBridge.kmStoreU8(addr, value.toByte())
public actual fun kmStoreU16(addr: Long, value: UShort): Unit = KmCoreJniBridge.kmStoreU16(addr, value.toShort())
public actual fun kmStoreU32(addr: Long, value: UInt): Unit = KmCoreJniBridge.kmStoreU32(addr, value.toInt())
public actual fun kmStoreU64(addr: Long, value: ULong): Unit = KmCoreJniBridge.kmStoreU64(addr, value.toLong())
public actual fun kmStoreF32(addr: Long, value: Float): Unit = KmCoreJniBridge.kmStoreF32(addr, value)
public actual fun kmStoreF64(addr: Long, value: Double): Unit = KmCoreJniBridge.kmStoreF64(addr, value)

public actual fun kmVolatileLoadU8(addr: Long): UByte = KmCoreJniBridge.kmVolatileLoadU8(addr).toUByte()
public actual fun kmVolatileLoadU16(addr: Long): UShort = KmCoreJniBridge.kmVolatileLoadU16(addr).toUShort()
public actual fun kmVolatileLoadU32(addr: Long): UInt = KmCoreJniBridge.kmVolatileLoadU32(addr).toUInt()
public actual fun kmVolatileLoadU64(addr: Long): ULong = KmCoreJniBridge.kmVolatileLoadU64(addr).toULong()

public actual fun kmVolatileStoreU8(addr: Long, value: UByte): Unit = KmCoreJniBridge.kmVolatileStoreU8(addr, value.toByte())
public actual fun kmVolatileStoreU16(addr: Long, value: UShort): Unit = KmCoreJniBridge.kmVolatileStoreU16(addr, value.toShort())
public actual fun kmVolatileStoreU32(addr: Long, value: UInt): Unit = KmCoreJniBridge.kmVolatileStoreU32(addr, value.toInt())
public actual fun kmVolatileStoreU64(addr: Long, value: ULong): Unit = KmCoreJniBridge.kmVolatileStoreU64(addr, value.toLong())

public actual fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long): Unit =
    KmCoreJniBridge.kmCopyNonoverlapping(dst, src, bytes)
public actual fun kmCopy(dst: Long, src: Long, bytes: Long): Unit =
    KmCoreJniBridge.kmCopy(dst, src, bytes)
public actual fun kmWriteBytes(dst: Long, value: UByte, bytes: Long): Unit =
    KmCoreJniBridge.kmWriteBytes(dst, value.toByte(), bytes)
public actual fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    KmCoreJniBridge.kmCompareBytes(left, right, bytes)

public actual fun kmAlloc(size: Long, align: Long): Long = KmCoreJniBridge.kmAlloc(size, align)
public actual fun kmAllocZeroed(size: Long, align: Long): Long = KmCoreJniBridge.kmAllocZeroed(size, align)
public actual fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    KmCoreJniBridge.kmRealloc(ptr, oldSize, align, newSize)
public actual fun kmDealloc(ptr: Long, size: Long, align: Long): Unit =
    KmCoreJniBridge.kmDealloc(ptr, size, align)

public actual fun kmAtomicFence(ord: Int): Unit = KmCoreJniBridge.kmAtomicFence(ord)
public actual fun kmAtomicSignalFence(ord: Int): Unit = KmCoreJniBridge.kmAtomicSignalFence(ord)
public actual fun kmAtomicLoadU8(addr: Long, ord: Int): UByte = KmCoreJniBridge.kmAtomicLoadU8(addr, ord).toUByte()
public actual fun kmAtomicLoadU16(addr: Long, ord: Int): UShort = KmCoreJniBridge.kmAtomicLoadU16(addr, ord).toUShort()
public actual fun kmAtomicLoadU32(addr: Long, ord: Int): UInt = KmCoreJniBridge.kmAtomicLoadU32(addr, ord).toUInt()
public actual fun kmAtomicLoadU64(addr: Long, ord: Int): ULong = KmCoreJniBridge.kmAtomicLoadU64(addr, ord).toULong()
public actual fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU8(addr, value.toByte(), ord)
public actual fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU16(addr, value.toShort(), ord)
public actual fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU32(addr, value.toInt(), ord)
public actual fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int): Unit = KmCoreJniBridge.kmAtomicStoreU64(addr, value.toLong(), ord)
public actual fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte = KmCoreJniBridge.kmAtomicXchgU8(addr, value.toByte(), ord).toUByte()
public actual fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort = KmCoreJniBridge.kmAtomicXchgU16(addr, value.toShort(), ord).toUShort()
public actual fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt = KmCoreJniBridge.kmAtomicXchgU32(addr, value.toInt(), ord).toUInt()
public actual fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong = KmCoreJniBridge.kmAtomicXchgU64(addr, value.toLong(), ord).toULong()

public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome {
    val packed = KmCoreJniBridge.kmAtomicCxchgU8Packed(addr, expected.toByte(), desired.toByte(), succ, fail)
    return AtomicCxchgU8Outcome((packed and 0xFFL).toUByte(), (packed ushr 32) != 0L)
}
public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome {
    val packed = KmCoreJniBridge.kmAtomicCxchgU16Packed(addr, expected.toShort(), desired.toShort(), succ, fail)
    return AtomicCxchgU16Outcome((packed and 0xFFFFL).toUShort(), (packed ushr 32) != 0L)
}
public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome {
    val packed = KmCoreJniBridge.kmAtomicCxchgU32Packed(addr, expected.toInt(), desired.toInt(), succ, fail)
    return AtomicCxchgU32Outcome((packed and 0xFFFFFFFFL).toUInt(), (packed ushr 32) != 0L)
}
public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmAtomicCxchgU64Out(addr, expected.toLong(), desired.toLong(), succ, fail, outBuf)
    return AtomicCxchgU64Outcome(outBuf[0].toULong(), outBuf[1] != 0L)
}

public actual fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte =
    KmCoreJniBridge.kmAtomicRmwU8(addr, value.toByte(), op, ord).toUByte()
public actual fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort =
    KmCoreJniBridge.kmAtomicRmwU16(addr, value.toShort(), op, ord).toUShort()
public actual fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt =
    KmCoreJniBridge.kmAtomicRmwU32(addr, value.toInt(), op, ord).toUInt()
public actual fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong =
    KmCoreJniBridge.kmAtomicRmwU64(addr, value.toLong(), op, ord).toULong()

public actual fun kmCtpopU64(value: ULong): UInt = KmCoreJniBridge.kmCtpopU64(value.toLong()).toUInt()
public actual fun kmCtlzU64(value: ULong): UInt = KmCoreJniBridge.kmCtlzU64(value.toLong()).toUInt()
public actual fun kmCttzU64(value: ULong): UInt = KmCoreJniBridge.kmCttzU64(value.toLong()).toUInt()
public actual fun kmBswapU16(value: UShort): UShort = KmCoreJniBridge.kmBswapU16(value.toShort()).toUShort()
public actual fun kmBswapU32(value: UInt): UInt = KmCoreJniBridge.kmBswapU32(value.toInt()).toUInt()
public actual fun kmBswapU64(value: ULong): ULong = KmCoreJniBridge.kmBswapU64(value.toLong()).toULong()
public actual fun kmBitreverseU64(value: ULong): ULong = KmCoreJniBridge.kmBitreverseU64(value.toLong()).toULong()
public actual fun kmFmaf32(a: Float, b: Float, c: Float): Float = KmCoreJniBridge.kmFmaf32(a, b, c)
public actual fun kmFmaf64(a: Double, b: Double, c: Double): Double = KmCoreJniBridge.kmFmaf64(a, b, c)
public actual fun kmSqrtf32(x: Float): Float = KmCoreJniBridge.kmSqrtf32(x)
public actual fun kmSqrtf64(x: Double): Double = KmCoreJniBridge.kmSqrtf64(x)
public actual fun kmAbort(): Nothing {
    KmCoreJniBridge.kmAbort()
    error("Unreachable after kmAbort")
}

public actual fun kmSpinLoopHint(): Unit = KmCoreJniBridge.kmSpinLoopHint()
public actual fun kmBlackBoxU64(value: ULong): ULong = KmCoreJniBridge.kmBlackBoxU64(value.toLong()).toULong()
public actual fun kmUmulhU64(a: ULong, b: ULong): ULong = KmCoreJniBridge.kmUmulhU64(a.toLong(), b.toLong()).toULong()
public actual fun kmSmulhI64(a: Long, b: Long): Long = KmCoreJniBridge.kmSmulhI64(a, b)
public actual fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmAtomicCxchgU128Out(addr, expLo.toLong(), expHi.toLong(), desLo.toLong(), desHi.toLong(), outBuf)
    return AtomicCxchgU128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long): Unit =
    KmCoreJniBridge.kmSimdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
public actual fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    KmCoreJniBridge.kmSimdDotF32(aAddr, bAddr, len)

public actual fun kmCtpopU8(value: UByte): UInt = KmCoreJniBridge.kmCtpopU8(value.toByte()).toUInt()
public actual fun kmCtpopU16(value: UShort): UInt = KmCoreJniBridge.kmCtpopU16(value.toShort()).toUInt()
public actual fun kmCtpopU32(value: UInt): UInt = KmCoreJniBridge.kmCtpopU32(value.toInt()).toUInt()
public actual fun kmCtpopU128(lo: ULong, hi: ULong): UInt = KmCoreJniBridge.kmCtpopU128(lo.toLong(), hi.toLong()).toUInt()
public actual fun kmCtlzU8(value: UByte): UInt = KmCoreJniBridge.kmCtlzU8(value.toByte()).toUInt()
public actual fun kmCtlzU16(value: UShort): UInt = KmCoreJniBridge.kmCtlzU16(value.toShort()).toUInt()
public actual fun kmCtlzU32(value: UInt): UInt = KmCoreJniBridge.kmCtlzU32(value.toInt()).toUInt()
public actual fun kmCtlzU128(lo: ULong, hi: ULong): UInt = KmCoreJniBridge.kmCtlzU128(lo.toLong(), hi.toLong()).toUInt()
public actual fun kmCttzU8(value: UByte): UInt = KmCoreJniBridge.kmCttzU8(value.toByte()).toUInt()
public actual fun kmCttzU16(value: UShort): UInt = KmCoreJniBridge.kmCttzU16(value.toShort()).toUInt()
public actual fun kmCttzU32(value: UInt): UInt = KmCoreJniBridge.kmCttzU32(value.toInt()).toUInt()
public actual fun kmCttzU128(lo: ULong, hi: ULong): UInt = KmCoreJniBridge.kmCttzU128(lo.toLong(), hi.toLong()).toUInt()
public actual fun kmBswapU128(lo: ULong, hi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmBswapU128Out(lo.toLong(), hi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmBitreverseU8(value: UByte): UByte = KmCoreJniBridge.kmBitreverseU8(value.toByte()).toUByte()
public actual fun kmBitreverseU16(value: UShort): UShort = KmCoreJniBridge.kmBitreverseU16(value.toShort()).toUShort()
public actual fun kmBitreverseU32(value: UInt): UInt = KmCoreJniBridge.kmBitreverseU32(value.toInt()).toUInt()
public actual fun kmBitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmBitreverseU128Out(lo.toLong(), hi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmRotateLeftU8(value: UByte, n: UInt): UByte = KmCoreJniBridge.kmRotateLeftU8(value.toByte(), n.toInt()).toUByte()
public actual fun kmRotateLeftU16(value: UShort, n: UInt): UShort = KmCoreJniBridge.kmRotateLeftU16(value.toShort(), n.toInt()).toUShort()
public actual fun kmRotateLeftU32(value: UInt, n: UInt): UInt = KmCoreJniBridge.kmRotateLeftU32(value.toInt(), n.toInt()).toUInt()
public actual fun kmRotateLeftU64(value: ULong, n: UInt): ULong = KmCoreJniBridge.kmRotateLeftU64(value.toLong(), n.toInt()).toULong()
public actual fun kmRotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmRotateLeftU128Out(lo.toLong(), hi.toLong(), n.toInt(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmRotateRightU8(value: UByte, n: UInt): UByte = KmCoreJniBridge.kmRotateRightU8(value.toByte(), n.toInt()).toUByte()
public actual fun kmRotateRightU16(value: UShort, n: UInt): UShort = KmCoreJniBridge.kmRotateRightU16(value.toShort(), n.toInt()).toUShort()
public actual fun kmRotateRightU32(value: UInt, n: UInt): UInt = KmCoreJniBridge.kmRotateRightU32(value.toInt(), n.toInt()).toUInt()
public actual fun kmRotateRightU64(value: ULong, n: UInt): ULong = KmCoreJniBridge.kmRotateRightU64(value.toLong(), n.toInt()).toULong()
public actual fun kmRotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmRotateRightU128Out(lo.toLong(), hi.toLong(), n.toInt(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmAddWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
    val packed = KmCoreJniBridge.kmAddWithOverflowU8Packed(a.toByte(), b.toByte())
    return OverflowU8Outcome(packed.toUByte(), (packed ushr 32) != 0L)
}
public actual fun kmAddWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
    val packed = KmCoreJniBridge.kmAddWithOverflowU16Packed(a.toShort(), b.toShort())
    return OverflowU16Outcome(packed.toUShort(), (packed ushr 32) != 0L)
}
public actual fun kmAddWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
    val packed = KmCoreJniBridge.kmAddWithOverflowU32Packed(a.toInt(), b.toInt())
    return OverflowU32Outcome(packed.toUInt(), (packed ushr 32) != 0L)
}
public actual fun kmAddWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmAddWithOverflowU64Out(a.toLong(), b.toLong(), outBuf)
    return OverflowU64Outcome(outBuf[0].toULong(), outBuf[1] != 0L)
}
public actual fun kmAddWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmAddWithOverflowU128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return OverflowU128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmAddWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
    val packed = KmCoreJniBridge.kmAddWithOverflowI8Packed(a, b)
    return OverflowI8Outcome(packed.toByte(), (packed ushr 32) != 0L)
}
public actual fun kmAddWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
    val packed = KmCoreJniBridge.kmAddWithOverflowI16Packed(a, b)
    return OverflowI16Outcome(packed.toShort(), (packed ushr 32) != 0L)
}
public actual fun kmAddWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
    val packed = KmCoreJniBridge.kmAddWithOverflowI32Packed(a, b)
    return OverflowI32Outcome(packed.toInt(), (packed ushr 32) != 0L)
}
public actual fun kmAddWithOverflowI64(a: Long, b: Long): OverflowI64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmAddWithOverflowI64Out(a, b, outBuf)
    return OverflowI64Outcome(outBuf[0], outBuf[1] != 0L)
}
public actual fun kmAddWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmAddWithOverflowI128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return OverflowI128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmSubWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
    val packed = KmCoreJniBridge.kmSubWithOverflowU8Packed(a.toByte(), b.toByte())
    return OverflowU8Outcome(packed.toUByte(), (packed ushr 32) != 0L)
}
public actual fun kmSubWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
    val packed = KmCoreJniBridge.kmSubWithOverflowU16Packed(a.toShort(), b.toShort())
    return OverflowU16Outcome(packed.toUShort(), (packed ushr 32) != 0L)
}
public actual fun kmSubWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
    val packed = KmCoreJniBridge.kmSubWithOverflowU32Packed(a.toInt(), b.toInt())
    return OverflowU32Outcome(packed.toUInt(), (packed ushr 32) != 0L)
}
public actual fun kmSubWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmSubWithOverflowU64Out(a.toLong(), b.toLong(), outBuf)
    return OverflowU64Outcome(outBuf[0].toULong(), outBuf[1] != 0L)
}
public actual fun kmSubWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmSubWithOverflowU128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return OverflowU128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmSubWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
    val packed = KmCoreJniBridge.kmSubWithOverflowI8Packed(a, b)
    return OverflowI8Outcome(packed.toByte(), (packed ushr 32) != 0L)
}
public actual fun kmSubWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
    val packed = KmCoreJniBridge.kmSubWithOverflowI16Packed(a, b)
    return OverflowI16Outcome(packed.toShort(), (packed ushr 32) != 0L)
}
public actual fun kmSubWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
    val packed = KmCoreJniBridge.kmSubWithOverflowI32Packed(a, b)
    return OverflowI32Outcome(packed.toInt(), (packed ushr 32) != 0L)
}
public actual fun kmSubWithOverflowI64(a: Long, b: Long): OverflowI64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmSubWithOverflowI64Out(a, b, outBuf)
    return OverflowI64Outcome(outBuf[0], outBuf[1] != 0L)
}
public actual fun kmSubWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmSubWithOverflowI128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return OverflowI128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmMulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
    val packed = KmCoreJniBridge.kmMulWithOverflowU8Packed(a.toByte(), b.toByte())
    return OverflowU8Outcome(packed.toUByte(), (packed ushr 32) != 0L)
}
public actual fun kmMulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
    val packed = KmCoreJniBridge.kmMulWithOverflowU16Packed(a.toShort(), b.toShort())
    return OverflowU16Outcome(packed.toUShort(), (packed ushr 32) != 0L)
}
public actual fun kmMulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
    val packed = KmCoreJniBridge.kmMulWithOverflowU32Packed(a.toInt(), b.toInt())
    return OverflowU32Outcome(packed.toUInt(), (packed ushr 32) != 0L)
}
public actual fun kmMulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmMulWithOverflowU64Out(a.toLong(), b.toLong(), outBuf)
    return OverflowU64Outcome(outBuf[0].toULong(), outBuf[1] != 0L)
}
public actual fun kmMulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmMulWithOverflowU128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return OverflowU128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmMulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
    val packed = KmCoreJniBridge.kmMulWithOverflowI8Packed(a, b)
    return OverflowI8Outcome(packed.toByte(), (packed ushr 32) != 0L)
}
public actual fun kmMulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
    val packed = KmCoreJniBridge.kmMulWithOverflowI16Packed(a, b)
    return OverflowI16Outcome(packed.toShort(), (packed ushr 32) != 0L)
}
public actual fun kmMulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
    val packed = KmCoreJniBridge.kmMulWithOverflowI32Packed(a, b)
    return OverflowI32Outcome(packed.toInt(), (packed ushr 32) != 0L)
}
public actual fun kmMulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmMulWithOverflowI64Out(a, b, outBuf)
    return OverflowI64Outcome(outBuf[0], outBuf[1] != 0L)
}
public actual fun kmMulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome {
    val outBuf = LongArray(3)
    KmCoreJniBridge.kmMulWithOverflowI128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return OverflowI128Outcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2] != 0L)
}
public actual fun kmSaturatingAddU8(a: UByte, b: UByte): UByte = KmCoreJniBridge.kmSaturatingAddU8(a.toByte(), b.toByte()).toUByte()
public actual fun kmSaturatingAddU16(a: UShort, b: UShort): UShort = KmCoreJniBridge.kmSaturatingAddU16(a.toShort(), b.toShort()).toUShort()
public actual fun kmSaturatingAddU32(a: UInt, b: UInt): UInt = KmCoreJniBridge.kmSaturatingAddU32(a.toInt(), b.toInt()).toUInt()
public actual fun kmSaturatingAddU64(a: ULong, b: ULong): ULong = KmCoreJniBridge.kmSaturatingAddU64(a.toLong(), b.toLong()).toULong()
public actual fun kmSaturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmSaturatingAddU128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmSaturatingAddI8(a: Byte, b: Byte): Byte = KmCoreJniBridge.kmSaturatingAddI8(a, b)
public actual fun kmSaturatingAddI16(a: Short, b: Short): Short = KmCoreJniBridge.kmSaturatingAddI16(a, b)
public actual fun kmSaturatingAddI32(a: Int, b: Int): Int = KmCoreJniBridge.kmSaturatingAddI32(a, b)
public actual fun kmSaturatingAddI64(a: Long, b: Long): Long = KmCoreJniBridge.kmSaturatingAddI64(a, b)
public actual fun kmSaturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmSaturatingAddI128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return I128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmSaturatingSubU8(a: UByte, b: UByte): UByte = KmCoreJniBridge.kmSaturatingSubU8(a.toByte(), b.toByte()).toUByte()
public actual fun kmSaturatingSubU16(a: UShort, b: UShort): UShort = KmCoreJniBridge.kmSaturatingSubU16(a.toShort(), b.toShort()).toUShort()
public actual fun kmSaturatingSubU32(a: UInt, b: UInt): UInt = KmCoreJniBridge.kmSaturatingSubU32(a.toInt(), b.toInt()).toUInt()
public actual fun kmSaturatingSubU64(a: ULong, b: ULong): ULong = KmCoreJniBridge.kmSaturatingSubU64(a.toLong(), b.toLong()).toULong()
public actual fun kmSaturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmSaturatingSubU128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmSaturatingSubI8(a: Byte, b: Byte): Byte = KmCoreJniBridge.kmSaturatingSubI8(a, b)
public actual fun kmSaturatingSubI16(a: Short, b: Short): Short = KmCoreJniBridge.kmSaturatingSubI16(a, b)
public actual fun kmSaturatingSubI32(a: Int, b: Int): Int = KmCoreJniBridge.kmSaturatingSubI32(a, b)
public actual fun kmSaturatingSubI64(a: Long, b: Long): Long = KmCoreJniBridge.kmSaturatingSubI64(a, b)
public actual fun kmSaturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmSaturatingSubI128Out(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return I128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmU128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmU128AddOut(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmU128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmU128SubOut(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmU128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmU128MulOut(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmU128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome {
    val outBuf = LongArray(4)
    KmCoreJniBridge.kmU128DivRemOut(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return U128DivRemOutcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2].toULong(), outBuf[3].toULong())
}
public actual fun kmI128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome {
    val outBuf = LongArray(4)
    KmCoreJniBridge.kmI128DivRemOut(aLo.toLong(), aHi.toLong(), bLo.toLong(), bHi.toLong(), outBuf)
    return I128DivRemOutcome(outBuf[0].toULong(), outBuf[1].toULong(), outBuf[2].toULong(), outBuf[3].toULong())
}
public actual fun kmU128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmU128ShlOut(aLo.toLong(), aHi.toLong(), shift.toInt(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmU128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmU128ShrOut(aLo.toLong(), aHi.toLong(), shift.toInt(), outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmI128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmI128ShrOut(aLo.toLong(), aHi.toLong(), shift.toInt(), outBuf)
    return I128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmSinf32(x: Float): Float = KmCoreJniBridge.kmSinf32(x)
public actual fun kmSinf64(x: Double): Double = KmCoreJniBridge.kmSinf64(x)
public actual fun kmCosf32(x: Float): Float = KmCoreJniBridge.kmCosf32(x)
public actual fun kmCosf64(x: Double): Double = KmCoreJniBridge.kmCosf64(x)
public actual fun kmExpf32(x: Float): Float = KmCoreJniBridge.kmExpf32(x)
public actual fun kmExpf64(x: Double): Double = KmCoreJniBridge.kmExpf64(x)
public actual fun kmExp2f32(x: Float): Float = KmCoreJniBridge.kmExp2f32(x)
public actual fun kmExp2f64(x: Double): Double = KmCoreJniBridge.kmExp2f64(x)
public actual fun kmLogf32(x: Float): Float = KmCoreJniBridge.kmLogf32(x)
public actual fun kmLogf64(x: Double): Double = KmCoreJniBridge.kmLogf64(x)
public actual fun kmLog2f32(x: Float): Float = KmCoreJniBridge.kmLog2f32(x)
public actual fun kmLog2f64(x: Double): Double = KmCoreJniBridge.kmLog2f64(x)
public actual fun kmLog10f32(x: Float): Float = KmCoreJniBridge.kmLog10f32(x)
public actual fun kmLog10f64(x: Double): Double = KmCoreJniBridge.kmLog10f64(x)
public actual fun kmTruncf32(x: Float): Float = KmCoreJniBridge.kmTruncf32(x)
public actual fun kmTruncf64(x: Double): Double = KmCoreJniBridge.kmTruncf64(x)
public actual fun kmRoundf32(x: Float): Float = KmCoreJniBridge.kmRoundf32(x)
public actual fun kmRoundf64(x: Double): Double = KmCoreJniBridge.kmRoundf64(x)
public actual fun kmRoundTiesEvenf32(x: Float): Float = KmCoreJniBridge.kmRoundTiesEvenf32(x)
public actual fun kmRoundTiesEvenf64(x: Double): Double = KmCoreJniBridge.kmRoundTiesEvenf64(x)
public actual fun kmCeilf32(x: Float): Float = KmCoreJniBridge.kmCeilf32(x)
public actual fun kmCeilf64(x: Double): Double = KmCoreJniBridge.kmCeilf64(x)
public actual fun kmFloorf32(x: Float): Float = KmCoreJniBridge.kmFloorf32(x)
public actual fun kmFloorf64(x: Double): Double = KmCoreJniBridge.kmFloorf64(x)
public actual fun kmFabsf32(x: Float): Float = KmCoreJniBridge.kmFabsf32(x)
public actual fun kmFabsf64(x: Double): Double = KmCoreJniBridge.kmFabsf64(x)
public actual fun kmPowf32(a: Float, b: Float): Float = KmCoreJniBridge.kmPowf32(a, b)
public actual fun kmPowf64(a: Double, b: Double): Double = KmCoreJniBridge.kmPowf64(a, b)
public actual fun kmPowif32(x: Float, exp: Int): Float = KmCoreJniBridge.kmPowif32(x, exp)
public actual fun kmPowif64(x: Double, exp: Int): Double = KmCoreJniBridge.kmPowif64(x, exp)
public actual fun kmCopysignf32(a: Float, b: Float): Float = KmCoreJniBridge.kmCopysignf32(a, b)
public actual fun kmCopysignf64(a: Double, b: Double): Double = KmCoreJniBridge.kmCopysignf64(a, b)
public actual fun kmMinnumf32(a: Float, b: Float): Float = KmCoreJniBridge.kmMinnumf32(a, b)
public actual fun kmMinnumf64(a: Double, b: Double): Double = KmCoreJniBridge.kmMinnumf64(a, b)
public actual fun kmMaxnumf32(a: Float, b: Float): Float = KmCoreJniBridge.kmMaxnumf32(a, b)
public actual fun kmMaxnumf64(a: Double, b: Double): Double = KmCoreJniBridge.kmMaxnumf64(a, b)
public actual fun kmFloatToU64Unchecked(v: Float): ULong = KmCoreJniBridge.kmFloatToU64Unchecked(v).toULong()
public actual fun kmDoubleToU64Unchecked(v: Double): ULong = KmCoreJniBridge.kmDoubleToU64Unchecked(v).toULong()
public actual fun kmFloatToU128Unchecked(v: Float): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmFloatToU128UncheckedOut(v, outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
public actual fun kmDoubleToU128Unchecked(v: Double): U128ValueOutcome {
    val outBuf = LongArray(2)
    KmCoreJniBridge.kmDoubleToU128UncheckedOut(v, outBuf)
    return U128ValueOutcome(outBuf[0].toULong(), outBuf[1].toULong())
}
