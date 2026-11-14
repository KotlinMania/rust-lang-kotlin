@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.rustlang.core.intrinsics

import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

public actual inline fun kmLoadU8(addr: Long): UByte = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmLoadU8(addr)
public actual inline fun kmLoadU16(addr: Long): UShort = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmLoadU16(addr)
public actual inline fun kmLoadU32(addr: Long): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmLoadU32(addr)
public actual inline fun kmLoadU64(addr: Long): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmLoadU64(addr)
public actual inline fun kmLoadF32(addr: Long): Float = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmLoadF32(addr)
public actual inline fun kmLoadF64(addr: Long): Double = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmLoadF64(addr)

public actual inline fun kmStoreU8(addr: Long, value: UByte): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmStoreU8(addr, value)
public actual inline fun kmStoreU16(addr: Long, value: UShort): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmStoreU16(addr, value)
public actual inline fun kmStoreU32(addr: Long, value: UInt): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmStoreU32(addr, value)
public actual inline fun kmStoreU64(addr: Long, value: ULong): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmStoreU64(addr, value)
public actual inline fun kmStoreF32(addr: Long, value: Float): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmStoreF32(addr, value)
public actual inline fun kmStoreF64(addr: Long, value: Double): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmStoreF64(addr, value)

public actual inline fun kmVolatileLoadU8(addr: Long): UByte = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileLoadU8(addr)
public actual inline fun kmVolatileLoadU16(addr: Long): UShort = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileLoadU16(addr)
public actual inline fun kmVolatileLoadU32(addr: Long): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileLoadU32(addr)
public actual inline fun kmVolatileLoadU64(addr: Long): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileLoadU64(addr)

public actual inline fun kmVolatileStoreU8(addr: Long, value: UByte): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileStoreU8(addr, value)
public actual inline fun kmVolatileStoreU16(addr: Long, value: UShort): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileStoreU16(addr, value)
public actual inline fun kmVolatileStoreU32(addr: Long, value: UInt): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileStoreU32(addr, value)
public actual inline fun kmVolatileStoreU64(addr: Long, value: ULong): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmVolatileStoreU64(addr, value)

public actual inline fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmCopyNonoverlapping(dst, src, bytes)
public actual inline fun kmCopy(dst: Long, src: Long, bytes: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmCopy(dst, src, bytes)
public actual inline fun kmWriteBytes(dst: Long, value: UByte, bytes: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmWriteBytes(dst, value, bytes)
public actual inline fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmCompareBytes(left, right, bytes)

public actual inline fun kmAlloc(size: Long, align: Long): Long = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAlloc(size, align)
public actual inline fun kmAllocZeroed(size: Long, align: Long): Long = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAllocZeroed(size, align)
public actual inline fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmRealloc(ptr, oldSize, align, newSize)
public actual inline fun kmDealloc(ptr: Long, size: Long, align: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmDealloc(ptr, size, align)

public actual inline fun kmAtomicFence(ord: Int): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicFence(ord)
public actual inline fun kmAtomicSignalFence(ord: Int): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicSignalFence(ord)
public actual inline fun kmAtomicLoadU8(addr: Long, ord: Int): UByte = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicLoadU8(addr, ord)
public actual inline fun kmAtomicLoadU16(addr: Long, ord: Int): UShort = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicLoadU16(addr, ord)
public actual inline fun kmAtomicLoadU32(addr: Long, ord: Int): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicLoadU32(addr, ord)
public actual inline fun kmAtomicLoadU64(addr: Long, ord: Int): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicLoadU64(addr, ord)
public actual inline fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicStoreU8(addr, value, ord)
public actual inline fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicStoreU16(addr, value, ord)
public actual inline fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicStoreU32(addr, value, ord)
public actual inline fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicStoreU64(addr, value, ord)
public actual inline fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicXchgU8(addr, value, ord)
public actual inline fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicXchgU16(addr, value, ord)
public actual inline fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicXchgU32(addr, value, ord)
public actual inline fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicXchgU64(addr, value, ord)

public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicCxchgU8(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU8Outcome(prev, ok.value != 0u.toUByte())
    }

public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicCxchgU16(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU16Outcome(prev, ok.value != 0u.toUByte())
    }

public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicCxchgU32(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU32Outcome(prev, ok.value != 0u.toUByte())
    }

public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome =
    memScoped {
        val ok = alloc<UByteVar>()
        val prev = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicCxchgU64(addr, expected, desired, succ, fail, ok.ptr)
        AtomicCxchgU64Outcome(prev, ok.value != 0u.toUByte())
    }

public actual inline fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicRmwU8(addr, value, op, ord)
public actual inline fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicRmwU16(addr, value, op, ord)
public actual inline fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicRmwU32(addr, value, op, ord)
public actual inline fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicRmwU64(addr, value, op, ord)

public actual inline fun kmCtpopU64(value: ULong): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmCtpopU64(value)
public actual inline fun kmCtlzU64(value: ULong): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmCtlzU64(value)
public actual inline fun kmCttzU64(value: ULong): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmCttzU64(value)
public actual inline fun kmBswapU16(value: UShort): UShort = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmBswapU16(value)
public actual inline fun kmBswapU32(value: UInt): UInt = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmBswapU32(value)
public actual inline fun kmBswapU64(value: ULong): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmBswapU64(value)
public actual inline fun kmBitreverseU64(value: ULong): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmBitreverseU64(value)
public actual inline fun kmFmaf32(a: Float, b: Float, c: Float): Float = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmFmaf32(a, b, c)
public actual inline fun kmFmaf64(a: Double, b: Double, c: Double): Double = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmFmaf64(a, b, c)
public actual inline fun kmSqrtf32(x: Float): Float = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmSqrtf32(x)
public actual inline fun kmSqrtf64(x: Double): Double = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmSqrtf64(x)
public actual fun kmAbort(): Nothing {
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAbort()
    error("Unreachable after kmAbort")
}

public actual inline fun kmSpinLoopHint(): Unit = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmSpinLoopHint()
public actual inline fun kmBlackBoxU64(value: ULong): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmBlackBoxU64(value)
public actual inline fun kmUmulhU64(a: ULong, b: ULong): ULong = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmUmulhU64(a, b)
public actual inline fun kmSmulhI64(a: Long, b: Long): Long = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmSmulhI64(a, b)
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
        val ok = io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmAtomicCxchgU128(addr, expLo, expHi, desLo, desHi, outLo.ptr, outHi.ptr)
        AtomicCxchgU128Outcome(outLo.value, outHi.value, ok != 0u.toUByte())
    }

public actual inline fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long): Unit =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmSimdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
public actual inline fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    io.github.kotlinmania.rustlang.core.intrinsics.cinterop.kmSimdDotF32(aAddr, bAddr, len)
