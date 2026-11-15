@file:OptIn(ExperimentalForeignApi::class)

package io.github.kotlinmania.rustlang.core.intrinsics

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

public actual inline fun kmLoadU8(addr: Long): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLoadU8(addr)
public actual inline fun kmLoadU16(addr: Long): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLoadU16(addr)
public actual inline fun kmLoadU32(addr: Long): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLoadU32(addr)
public actual inline fun kmLoadU64(addr: Long): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLoadU64(addr)
public actual inline fun kmLoadF32(addr: Long): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLoadF32(addr)
public actual inline fun kmLoadF64(addr: Long): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLoadF64(addr)

public actual inline fun kmStoreU8(addr: Long, value: UByte): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmStoreU8(addr, value)
public actual inline fun kmStoreU16(addr: Long, value: UShort): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmStoreU16(addr, value)
public actual inline fun kmStoreU32(addr: Long, value: UInt): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmStoreU32(addr, value)
public actual inline fun kmStoreU64(addr: Long, value: ULong): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmStoreU64(addr, value)
public actual inline fun kmStoreF32(addr: Long, value: Float): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmStoreF32(addr, value)
public actual inline fun kmStoreF64(addr: Long, value: Double): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmStoreF64(addr, value)

public actual inline fun kmVolatileLoadU8(addr: Long): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileLoadU8(addr)
public actual inline fun kmVolatileLoadU16(addr: Long): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileLoadU16(addr)
public actual inline fun kmVolatileLoadU32(addr: Long): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileLoadU32(addr)
public actual inline fun kmVolatileLoadU64(addr: Long): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileLoadU64(addr)

public actual inline fun kmVolatileStoreU8(addr: Long, value: UByte): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileStoreU8(addr, value)
public actual inline fun kmVolatileStoreU16(addr: Long, value: UShort): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileStoreU16(addr, value)
public actual inline fun kmVolatileStoreU32(addr: Long, value: UInt): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileStoreU32(addr, value)
public actual inline fun kmVolatileStoreU64(addr: Long, value: ULong): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmVolatileStoreU64(addr, value)

public actual inline fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCopyNonoverlapping(dst, src, bytes)
public actual inline fun kmCopy(dst: Long, src: Long, bytes: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCopy(dst, src, bytes)
public actual inline fun kmWriteBytes(dst: Long, value: UByte, bytes: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmWriteBytes(dst, value, bytes)
public actual inline fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCompareBytes(left, right, bytes)

public actual inline fun kmAlloc(size: Long, align: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAlloc(size, align)
public actual inline fun kmAllocZeroed(size: Long, align: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAllocZeroed(size, align)
public actual inline fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRealloc(ptr, oldSize, align, newSize)
public actual inline fun kmDealloc(ptr: Long, size: Long, align: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmDealloc(ptr, size, align)

public actual inline fun kmAtomicFence(ord: Int): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicFence(ord)
public actual inline fun kmAtomicSignalFence(ord: Int): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicSignalFence(ord)
public actual inline fun kmAtomicLoadU8(addr: Long, ord: Int): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicLoadU8(addr, ord)
public actual inline fun kmAtomicLoadU16(addr: Long, ord: Int): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicLoadU16(addr, ord)
public actual inline fun kmAtomicLoadU32(addr: Long, ord: Int): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicLoadU32(addr, ord)
public actual inline fun kmAtomicLoadU64(addr: Long, ord: Int): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicLoadU64(addr, ord)
public actual inline fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicStoreU8(addr, value, ord)
public actual inline fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicStoreU16(addr, value, ord)
public actual inline fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicStoreU32(addr, value, ord)
public actual inline fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicStoreU64(addr, value, ord)
public actual inline fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicXchgU8(addr, value, ord)
public actual inline fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicXchgU16(addr, value, ord)
public actual inline fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicXchgU32(addr, value, ord)
public actual inline fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicXchgU64(addr, value, ord)

public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAtomicCxchgU8(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU8Outcome(prev, ok.value != 0u.toUByte())
    }

public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAtomicCxchgU16(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU16Outcome(prev, ok.value != 0u.toUByte())
    }

public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAtomicCxchgU32(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU32Outcome(prev, ok.value != 0u.toUByte())
    }

public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAtomicCxchgU64(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU64Outcome(prev, ok.value != 0u.toUByte())
    }

public actual inline fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicRmwU8(addr, value, op, ord)
public actual inline fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicRmwU16(addr, value, op, ord)
public actual inline fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicRmwU32(addr, value, op, ord)
public actual inline fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAtomicRmwU64(addr, value, op, ord)

public actual inline fun kmCtpopU64(value: ULong): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtpopU64(value)
public actual inline fun kmCtlzU64(value: ULong): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtlzU64(value)
public actual inline fun kmCttzU64(value: ULong): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCttzU64(value)
public actual inline fun kmBswapU16(value: UShort): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBswapU16(value)
public actual inline fun kmBswapU32(value: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBswapU32(value)
public actual inline fun kmBswapU64(value: ULong): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBswapU64(value)
public actual inline fun kmBitreverseU64(value: ULong): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBitreverseU64(value)
public actual inline fun kmFmaf32(a: Float, b: Float, c: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFmaf32(a, b, c)
public actual inline fun kmFmaf64(a: Double, b: Double, c: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFmaf64(a, b, c)
public actual inline fun kmSqrtf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSqrtf32(x)
public actual inline fun kmSqrtf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSqrtf64(x)
public actual fun kmAbort(): Nothing {
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmAbort()
    error("Unreachable after kmAbort")
}

public actual inline fun kmSpinLoopHint(): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSpinLoopHint()
public actual inline fun kmBlackBoxU64(value: ULong): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBlackBoxU64(value)
public actual inline fun kmUmulhU64(a: ULong, b: ULong): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmUmulhU64(a, b)
public actual inline fun kmSmulhI64(a: Long, b: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSmulhI64(a, b)
public actual fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ok =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAtomicCxchgU128(addr, expLo, expHi, desLo, desHi, outLo.ptr, outHi.ptr)
        AtomicCxchgU128Outcome(outLo.value, outHi.value, ok != 0u.toUByte())
    }

public actual inline fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSimdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
public actual inline fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSimdDotF32(aAddr, bAddr, len)

public actual inline fun kmCtpopU8(value: UByte): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtpopU8(value)
public actual inline fun kmCtpopU16(value: UShort): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtpopU16(value)
public actual inline fun kmCtpopU32(value: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtpopU32(value)
public actual inline fun kmCtpopU128(lo: ULong, hi: ULong): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtpopU128(lo, hi)
public actual inline fun kmCtlzU8(value: UByte): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtlzU8(value)
public actual inline fun kmCtlzU16(value: UShort): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtlzU16(value)
public actual inline fun kmCtlzU32(value: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtlzU32(value)
public actual inline fun kmCtlzU128(lo: ULong, hi: ULong): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCtlzU128(lo, hi)
public actual inline fun kmCttzU8(value: UByte): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCttzU8(value)
public actual inline fun kmCttzU16(value: UShort): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCttzU16(value)
public actual inline fun kmCttzU32(value: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCttzU32(value)
public actual inline fun kmCttzU128(lo: ULong, hi: ULong): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCttzU128(lo, hi)
public actual fun kmBswapU128(lo: ULong, hi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmBswapU128Out(lo, hi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmBitreverseU8(value: UByte): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBitreverseU8(value)
public actual inline fun kmBitreverseU16(value: UShort): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBitreverseU16(value)
public actual inline fun kmBitreverseU32(value: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmBitreverseU32(value)
public actual fun kmBitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmBitreverseU128Out(lo, hi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmRotateLeftU8(value: UByte, n: UInt): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateLeftU8(value, n)
public actual inline fun kmRotateLeftU16(value: UShort, n: UInt): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateLeftU16(value, n)
public actual inline fun kmRotateLeftU32(value: UInt, n: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateLeftU32(value, n)
public actual inline fun kmRotateLeftU64(value: ULong, n: UInt): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateLeftU64(value, n)
public actual fun kmRotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmRotateLeftU128Out(lo, hi, n, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmRotateRightU8(value: UByte, n: UInt): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateRightU8(value, n)
public actual inline fun kmRotateRightU16(value: UShort, n: UInt): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateRightU16(value, n)
public actual inline fun kmRotateRightU32(value: UInt, n: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateRightU32(value, n)
public actual inline fun kmRotateRightU64(value: ULong, n: UInt): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRotateRightU64(value, n)
public actual fun kmRotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmRotateRightU128Out(lo, hi, n, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmAddWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmAddWithOverflowU8Packed(a, b)
    return OverflowU8Outcome(packed.toUByte(), (packed shr 32) != 0uL)
}
public actual fun kmAddWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmAddWithOverflowU16Packed(a, b)
    return OverflowU16Outcome(packed.toUShort(), (packed shr 32) != 0uL)
}
public actual fun kmAddWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmAddWithOverflowU32Packed(a, b)
    return OverflowU32Outcome(packed.toUInt(), (packed shr 32) != 0uL)
}
public actual fun kmAddWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    memScoped {
        val outVal = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAddWithOverflowU64Out(a, b, outVal.ptr)
        OverflowU64Outcome(outVal.value, ovf != 0u.toUByte())
    }
public actual fun kmAddWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAddWithOverflowU128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        OverflowU128Outcome(outLo.value, outHi.value, ovf != 0u.toUByte())
    }
public actual fun kmAddWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmAddWithOverflowI8Packed(a, b)
    return OverflowI8Outcome(packed.toByte(), (packed shr 32) != 0uL)
}
public actual fun kmAddWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmAddWithOverflowI16Packed(a, b)
    return OverflowI16Outcome(packed.toShort(), (packed shr 32) != 0uL)
}
public actual fun kmAddWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmAddWithOverflowI32Packed(a, b)
    return OverflowI32Outcome(packed.toInt(), (packed shr 32) != 0uL)
}
public actual fun kmAddWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    memScoped {
        val outVal = alloc<LongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAddWithOverflowI64Out(a, b, outVal.ptr)
        OverflowI64Outcome(outVal.value, ovf != 0u.toUByte())
    }
public actual fun kmAddWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmAddWithOverflowI128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        OverflowI128Outcome(outLo.value, outHi.value, ovf != 0u.toUByte())
    }
public actual fun kmSubWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSubWithOverflowU8Packed(a, b)
    return OverflowU8Outcome(packed.toUByte(), (packed shr 32) != 0uL)
}
public actual fun kmSubWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSubWithOverflowU16Packed(a, b)
    return OverflowU16Outcome(packed.toUShort(), (packed shr 32) != 0uL)
}
public actual fun kmSubWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSubWithOverflowU32Packed(a, b)
    return OverflowU32Outcome(packed.toUInt(), (packed shr 32) != 0uL)
}
public actual fun kmSubWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    memScoped {
        val outVal = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmSubWithOverflowU64Out(a, b, outVal.ptr)
        OverflowU64Outcome(outVal.value, ovf != 0u.toUByte())
    }
public actual fun kmSubWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmSubWithOverflowU128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        OverflowU128Outcome(outLo.value, outHi.value, ovf != 0u.toUByte())
    }
public actual fun kmSubWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSubWithOverflowI8Packed(a, b)
    return OverflowI8Outcome(packed.toByte(), (packed shr 32) != 0uL)
}
public actual fun kmSubWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSubWithOverflowI16Packed(a, b)
    return OverflowI16Outcome(packed.toShort(), (packed shr 32) != 0uL)
}
public actual fun kmSubWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSubWithOverflowI32Packed(a, b)
    return OverflowI32Outcome(packed.toInt(), (packed shr 32) != 0uL)
}
public actual fun kmSubWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    memScoped {
        val outVal = alloc<LongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmSubWithOverflowI64Out(a, b, outVal.ptr)
        OverflowI64Outcome(outVal.value, ovf != 0u.toUByte())
    }
public actual fun kmSubWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmSubWithOverflowI128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        OverflowI128Outcome(outLo.value, outHi.value, ovf != 0u.toUByte())
    }
public actual fun kmMulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmMulWithOverflowU8Packed(a, b)
    return OverflowU8Outcome(packed.toUByte(), (packed shr 32) != 0uL)
}
public actual fun kmMulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmMulWithOverflowU16Packed(a, b)
    return OverflowU16Outcome(packed.toUShort(), (packed shr 32) != 0uL)
}
public actual fun kmMulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmMulWithOverflowU32Packed(a, b)
    return OverflowU32Outcome(packed.toUInt(), (packed shr 32) != 0uL)
}
public actual fun kmMulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    memScoped {
        val outVal = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmMulWithOverflowU64Out(a, b, outVal.ptr)
        OverflowU64Outcome(outVal.value, ovf != 0u.toUByte())
    }
public actual fun kmMulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmMulWithOverflowU128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        OverflowU128Outcome(outLo.value, outHi.value, ovf != 0u.toUByte())
    }
public actual fun kmMulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmMulWithOverflowI8Packed(a, b)
    return OverflowI8Outcome(packed.toByte(), (packed shr 32) != 0uL)
}
public actual fun kmMulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmMulWithOverflowI16Packed(a, b)
    return OverflowI16Outcome(packed.toShort(), (packed shr 32) != 0uL)
}
public actual fun kmMulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome {
    val packed =
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmMulWithOverflowI32Packed(a, b)
    return OverflowI32Outcome(packed.toInt(), (packed shr 32) != 0uL)
}
public actual fun kmMulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    memScoped {
        val outVal = alloc<LongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmMulWithOverflowI64Out(a, b, outVal.ptr)
        OverflowI64Outcome(outVal.value, ovf != 0u.toUByte())
    }
public actual fun kmMulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        val ovf =
            io.github.kotlinmania.rustlang.core.intrinsics.cinterop
                .kmMulWithOverflowI128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        OverflowI128Outcome(outLo.value, outHi.value, ovf != 0u.toUByte())
    }
public actual inline fun kmSaturatingAddU8(a: UByte, b: UByte): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddU8(a, b)
public actual inline fun kmSaturatingAddU16(a: UShort, b: UShort): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddU16(a, b)
public actual inline fun kmSaturatingAddU32(a: UInt, b: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddU32(a, b)
public actual inline fun kmSaturatingAddU64(a: ULong, b: ULong): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddU64(a, b)
public actual fun kmSaturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSaturatingAddU128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmSaturatingAddI8(a: Byte, b: Byte): Byte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddI8(a, b)
public actual inline fun kmSaturatingAddI16(a: Short, b: Short): Short =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddI16(a, b)
public actual inline fun kmSaturatingAddI32(a: Int, b: Int): Int =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddI32(a, b)
public actual inline fun kmSaturatingAddI64(a: Long, b: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingAddI64(a, b)
public actual fun kmSaturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSaturatingAddI128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        I128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmSaturatingSubU8(a: UByte, b: UByte): UByte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubU8(a, b)
public actual inline fun kmSaturatingSubU16(a: UShort, b: UShort): UShort =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubU16(a, b)
public actual inline fun kmSaturatingSubU32(a: UInt, b: UInt): UInt =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubU32(a, b)
public actual inline fun kmSaturatingSubU64(a: ULong, b: ULong): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubU64(a, b)
public actual fun kmSaturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSaturatingSubU128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmSaturatingSubI8(a: Byte, b: Byte): Byte =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubI8(a, b)
public actual inline fun kmSaturatingSubI16(a: Short, b: Short): Short =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubI16(a, b)
public actual inline fun kmSaturatingSubI32(a: Int, b: Int): Int =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubI32(a, b)
public actual inline fun kmSaturatingSubI64(a: Long, b: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSaturatingSubI64(a, b)
public actual fun kmSaturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmSaturatingSubI128Out(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        I128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmU128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmU128AddOut(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmU128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmU128SubOut(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmU128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmU128MulOut(aLo, aHi, bLo, bHi, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmU128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome =
    memScoped {
        val qLo = alloc<ULongVar>()
        val qHi = alloc<ULongVar>()
        val rLo = alloc<ULongVar>()
        val rHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmU128DivRemOut(aLo, aHi, bLo, bHi, qLo.ptr, qHi.ptr, rLo.ptr, rHi.ptr)
        U128DivRemOutcome(qLo.value, qHi.value, rLo.value, rHi.value)
    }
public actual fun kmI128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome =
    memScoped {
        val qLo = alloc<ULongVar>()
        val qHi = alloc<ULongVar>()
        val rLo = alloc<ULongVar>()
        val rHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmI128DivRemOut(aLo, aHi, bLo, bHi, qLo.ptr, qHi.ptr, rLo.ptr, rHi.ptr)
        I128DivRemOutcome(qLo.value, qHi.value, rLo.value, rHi.value)
    }
public actual fun kmU128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmU128ShlOut(aLo, aHi, shift, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmU128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmU128ShrOut(aLo, aHi, shift, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmI128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmI128ShrOut(aLo, aHi, shift, outLo.ptr, outHi.ptr)
        I128ValueOutcome(outLo.value, outHi.value)
    }
public actual inline fun kmSinf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSinf32(x)
public actual inline fun kmSinf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmSinf64(x)
public actual inline fun kmCosf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCosf32(x)
public actual inline fun kmCosf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCosf64(x)
public actual inline fun kmExpf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmExpf32(x)
public actual inline fun kmExpf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmExpf64(x)
public actual inline fun kmExp2f32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmExp2f32(x)
public actual inline fun kmExp2f64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmExp2f64(x)
public actual inline fun kmLogf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLogf32(x)
public actual inline fun kmLogf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLogf64(x)
public actual inline fun kmLog2f32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLog2f32(x)
public actual inline fun kmLog2f64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLog2f64(x)
public actual inline fun kmLog10f32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLog10f32(x)
public actual inline fun kmLog10f64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmLog10f64(x)
public actual inline fun kmTruncf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmTruncf32(x)
public actual inline fun kmTruncf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmTruncf64(x)
public actual inline fun kmRoundf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRoundf32(x)
public actual inline fun kmRoundf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRoundf64(x)
public actual inline fun kmRoundTiesEvenf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRoundTiesEvenf32(x)
public actual inline fun kmRoundTiesEvenf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmRoundTiesEvenf64(x)
public actual inline fun kmCeilf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCeilf32(x)
public actual inline fun kmCeilf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCeilf64(x)
public actual inline fun kmFloorf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFloorf32(x)
public actual inline fun kmFloorf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFloorf64(x)
public actual inline fun kmFabsf32(x: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFabsf32(x)
public actual inline fun kmFabsf64(x: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFabsf64(x)
public actual inline fun kmPowf32(a: Float, b: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmPowf32(a, b)
public actual inline fun kmPowf64(a: Double, b: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmPowf64(a, b)
public actual inline fun kmPowif32(x: Float, exp: Int): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmPowif32(x, exp)
public actual inline fun kmPowif64(x: Double, exp: Int): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmPowif64(x, exp)
public actual inline fun kmCopysignf32(a: Float, b: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCopysignf32(a, b)
public actual inline fun kmCopysignf64(a: Double, b: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmCopysignf64(a, b)
public actual inline fun kmMinnumf32(a: Float, b: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmMinnumf32(a, b)
public actual inline fun kmMinnumf64(a: Double, b: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmMinnumf64(a, b)
public actual inline fun kmMaxnumf32(a: Float, b: Float): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmMaxnumf32(a, b)
public actual inline fun kmMaxnumf64(a: Double, b: Double): Double =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmMaxnumf64(a, b)
public actual inline fun kmFloatToU64Unchecked(v: Float): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmFloatToU64Unchecked(v)
public actual inline fun kmDoubleToU64Unchecked(v: Double): ULong =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop
        .kmDoubleToU64Unchecked(v)
public actual fun kmFloatToU128Unchecked(v: Float): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmFloatToU128UncheckedOut(v, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
public actual fun kmDoubleToU128Unchecked(v: Double): U128ValueOutcome =
    memScoped {
        val outLo = alloc<ULongVar>()
        val outHi = alloc<ULongVar>()
        io.github.kotlinmania.rustlang.core.intrinsics.cinterop
            .kmDoubleToU128UncheckedOut(v, outLo.ptr, outHi.ptr)
        U128ValueOutcome(outLo.value, outHi.value)
    }
