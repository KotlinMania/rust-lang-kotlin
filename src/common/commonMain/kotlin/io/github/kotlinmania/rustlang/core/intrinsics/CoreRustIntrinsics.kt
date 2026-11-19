// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.intrinsics

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.panic.location.Location
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull

public fun assume(b: Boolean) {
    check(b) { "core.intrinsics.assume invariant violated" }
}

public fun coldPath() {}

public fun likely(b: Boolean): Boolean = b

public fun unlikely(b: Boolean): Boolean = b

public fun <T> selectUnpredictable(b: Boolean, trueVal: T, falseVal: T): T =
    if (b) trueVal else falseVal

public fun <T> isValStaticallyKnown(arg: T): Boolean = false

public fun ubChecks(): Boolean = false

public fun overflowChecks(): Boolean = false

public fun ptrGuaranteedCmp(ptr: ConstPtr, other: ConstPtr): UByte =
    if (ptr.rawAddress == other.rawAddress) 1u else 0u

public fun callerLocation(): Location =
    Location(
        filename = NonNull.newUnchecked(kmInternCStr("<kotlin>").castMut()),
        line = 0u,
        col = 0u,
    )

public fun discriminantValue(v: Any?): Any =
    when (v) {
        null -> Unit
        is Enum<*> -> v.ordinal
        else -> v::class
    }

public fun threeWayCompare(a: Boolean, b: Boolean): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: Byte, b: Byte): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: UByte, b: UByte): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: Short, b: Short): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: UShort, b: UShort): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: Int, b: Int): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: UInt, b: UInt): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: Long, b: Long): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: ULong, b: ULong): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompareChar(a: Char, b: Char): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: String, b: String): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: U128, b: U128): Ordering = kmOrderingFromInt(kmCompareValues(a, b))
public fun threeWayCompare(a: I128, b: I128): Ordering = kmOrderingFromInt(kmCompareValues(a, b))

private fun kmOrderingFromInt(cmpSign: Int): Ordering =
    when {
        cmpSign < 0 -> Ordering.Less
        cmpSign > 0 -> Ordering.Greater
        else -> Ordering.Equal
    }

public fun integerMin(a: Byte, b: Byte): Byte = if (a <= b) a else b
public fun integerMax(a: Byte, b: Byte): Byte = if (a >= b) a else b
public fun integerMin(a: UByte, b: UByte): UByte = if (a <= b) a else b
public fun integerMax(a: UByte, b: UByte): UByte = if (a >= b) a else b
public fun integerMin(a: Short, b: Short): Short = if (a <= b) a else b
public fun integerMax(a: Short, b: Short): Short = if (a >= b) a else b
public fun integerMin(a: UShort, b: UShort): UShort = if (a <= b) a else b
public fun integerMax(a: UShort, b: UShort): UShort = if (a >= b) a else b
public fun integerMin(a: Int, b: Int): Int = if (a <= b) a else b
public fun integerMax(a: Int, b: Int): Int = if (a >= b) a else b
public fun integerMin(a: UInt, b: UInt): UInt = if (a <= b) a else b
public fun integerMax(a: UInt, b: UInt): UInt = if (a >= b) a else b
public fun integerMin(a: Long, b: Long): Long = if (a <= b) a else b
public fun integerMax(a: Long, b: Long): Long = if (a >= b) a else b
public fun integerMin(a: ULong, b: ULong): ULong = if (a <= b) a else b
public fun integerMax(a: ULong, b: ULong): ULong = if (a >= b) a else b
public fun integerMin(a: U128, b: U128): U128 = if (a <= b) a else b
public fun integerMax(a: U128, b: U128): U128 = if (a >= b) a else b
public fun integerMin(a: I128, b: I128): I128 = if (a <= b) a else b
public fun integerMax(a: I128, b: I128): I128 = if (a >= b) a else b

public fun disjointBitor(a: Byte, b: Byte): Byte = (a.toInt() or b.toInt()).toByte()
public fun disjointBitor(a: UByte, b: UByte): UByte = a or b
public fun disjointBitor(a: Short, b: Short): Short = (a.toInt() or b.toInt()).toShort()
public fun disjointBitor(a: UShort, b: UShort): UShort = a or b
public fun disjointBitor(a: Int, b: Int): Int = a or b
public fun disjointBitor(a: UInt, b: UInt): UInt = a or b
public fun disjointBitor(a: Long, b: Long): Long = a or b
public fun disjointBitor(a: ULong, b: ULong): ULong = a or b
public fun disjointBitor(a: Boolean, b: Boolean): Boolean = a || b

public fun uncheckedAdd(a: Int, b: Int): Int = a + b
public fun uncheckedAdd(a: UInt, b: UInt): UInt = a + b
public fun uncheckedAdd(a: Long, b: Long): Long = a + b
public fun uncheckedAdd(a: ULong, b: ULong): ULong = a + b

public fun uncheckedSub(a: Int, b: Int): Int = a - b
public fun uncheckedSub(a: UInt, b: UInt): UInt = a - b
public fun uncheckedSub(a: Long, b: Long): Long = a - b
public fun uncheckedSub(a: ULong, b: ULong): ULong = a - b

public fun uncheckedMul(a: Int, b: Int): Int = a * b
public fun uncheckedMul(a: UInt, b: UInt): UInt = a * b
public fun uncheckedMul(a: Long, b: Long): Long = a * b
public fun uncheckedMul(a: ULong, b: ULong): ULong = a * b

public fun uncheckedRem(a: Int, b: Int): Int = a % b
public fun uncheckedRem(a: UInt, b: UInt): UInt = a % b
public fun uncheckedRem(a: Long, b: Long): Long = a % b
public fun uncheckedRem(a: ULong, b: ULong): ULong = a % b

public fun uncheckedShl(a: Int, b: Int): Int = a shl b
public fun uncheckedShl(a: Int, b: UInt): Int = a shl b.toInt()
public fun uncheckedShl(a: UInt, b: UInt): UInt = a shl b.toInt()
public fun uncheckedShl(a: Long, b: UInt): Long = a shl b.toInt()
public fun uncheckedShl(a: ULong, b: UInt): ULong = a shl b.toInt()

public fun uncheckedShr(a: Int, b: Int): Int = a shr b
public fun uncheckedShr(a: Int, b: UInt): Int = a shr b.toInt()
public fun uncheckedShr(a: UInt, b: UInt): UInt = a shr b.toInt()
public fun uncheckedShr(a: Long, b: UInt): Long = a shr b.toInt()
public fun uncheckedShr(a: ULong, b: UInt): ULong = a shr b.toInt()

public fun wrappingAdd(a: Byte, b: Byte): Byte = (a + b).toByte()
public fun wrappingAdd(a: UByte, b: UByte): UByte = (a + b).toUByte()
public fun wrappingAdd(a: Short, b: Short): Short = (a + b).toShort()
public fun wrappingAdd(a: UShort, b: UShort): UShort = (a + b).toUShort()
public fun wrappingAdd(a: Int, b: Int): Int = a + b
public fun wrappingAdd(a: UInt, b: UInt): UInt = a + b
public fun wrappingAdd(a: Long, b: Long): Long = a + b
public fun wrappingAdd(a: ULong, b: ULong): ULong = a + b

public fun wrappingSub(a: Byte, b: Byte): Byte = (a - b).toByte()
public fun wrappingSub(a: UByte, b: UByte): UByte = (a - b).toUByte()
public fun wrappingSub(a: Short, b: Short): Short = (a - b).toShort()
public fun wrappingSub(a: UShort, b: UShort): UShort = (a - b).toUShort()
public fun wrappingSub(a: Int, b: Int): Int = a - b
public fun wrappingSub(a: UInt, b: UInt): UInt = a - b
public fun wrappingSub(a: Long, b: Long): Long = a - b
public fun wrappingSub(a: ULong, b: ULong): ULong = a - b

public fun wrappingMul(a: Byte, b: Byte): Byte = (a * b).toByte()
public fun wrappingMul(a: UByte, b: UByte): UByte = (a * b).toUByte()
public fun wrappingMul(a: Short, b: Short): Short = (a * b).toShort()
public fun wrappingMul(a: UShort, b: UShort): UShort = (a * b).toUShort()
public fun wrappingMul(a: Int, b: Int): Int = a * b
public fun wrappingMul(a: UInt, b: UInt): UInt = a * b
public fun wrappingMul(a: Long, b: Long): Long = a * b
public fun wrappingMul(a: ULong, b: ULong): ULong = a * b

public fun exactDiv(a: Int, b: Int): Int {
    check(b != 0 && a % b == 0) { "exactDiv precondition violated" }
    return a / b
}

public fun exactDiv(a: UInt, b: UInt): UInt {
    check(b != 0u && a % b == 0u) { "exactDiv precondition violated" }
    return a / b
}

public fun exactDiv(a: Long, b: Long): Long {
    check(b != 0L && a % b == 0L) { "exactDiv precondition violated" }
    return a / b
}

public fun exactDiv(a: ULong, b: ULong): ULong {
    check(b != 0uL && a % b == 0uL) { "exactDiv precondition violated" }
    return a / b
}

public fun cttzNonzero(x: UByte): Int {
    check(x != 0u.toUByte()) { "cttzNonzero(0)" }
    return x.countTrailingZeroBits()
}

public fun cttzNonzero(x: UShort): Int {
    check(x != 0u.toUShort()) { "cttzNonzero(0)" }
    return x.countTrailingZeroBits()
}

public fun cttzNonzero(x: Int): Int {
    check(x != 0) { "cttzNonzero(0)" }
    return x.countTrailingZeroBits()
}

public fun cttzNonzero(x: UInt): Int {
    check(x != 0u) { "cttzNonzero(0)" }
    return x.countTrailingZeroBits()
}

public fun cttzNonzero(x: Long): Int {
    check(x != 0L) { "cttzNonzero(0)" }
    return x.countTrailingZeroBits()
}

public fun cttzNonzero(x: ULong): Int {
    check(x != 0uL) { "cttzNonzero(0)" }
    return x.countTrailingZeroBits()
}

public fun mulWithOverflow(a: Int, b: Int): Pair<Int, Boolean> {
    val wide = a.toLong() * b.toLong()
    val res = wide.toInt()
    return Pair(res, wide != res.toLong())
}

public fun mulWithOverflow(a: UInt, b: UInt): Pair<UInt, Boolean> {
    val wide = a.toULong() * b.toULong()
    val res = wide.toUInt()
    return Pair(res, wide > UInt.MAX_VALUE.toULong())
}

public fun mulWithOverflow(a: Long, b: Long): Pair<Long, Boolean> {
    val out = kmMulWithOverflowI64(a, b)
    return Pair(out.value, out.overflowed)
}

public fun mulWithOverflow(a: ULong, b: ULong): Pair<ULong, Boolean> {
    val out = kmMulWithOverflowU64(a, b)
    return Pair(out.value, out.overflowed)
}

public fun ptrMetadata(ptr: ConstPtr): Long = ptr.rawMeta

public fun ptrMetadata(ptr: MutPtr): Long = ptr.rawMeta

public fun aggregateRawPtr(dataPointer: ConstPtr, metadata: Long): ConstPtr =
    ConstPtr(dataPointer.rawAddress, metadata)

public fun aggregateRawPtr(dataPointer: ConstPtr, metadata: Int): ConstPtr =
    ConstPtr(dataPointer.rawAddress, metadata.toLong())

public fun aggregateRawPtr(dataPointer: MutPtr, metadata: Long): MutPtr =
    MutPtr(dataPointer.rawAddress, metadata)

public fun aggregateRawPtr(dataPointer: MutPtr, metadata: Int): MutPtr =
    MutPtr(dataPointer.rawAddress, metadata.toLong())

private fun kmSwapNativeRanges(addrX: Long, addrY: Long, bytes: Long, align: Long) {
    if (bytes <= 0L || addrX == addrY) return
    val scratch = kmAlloc(bytes, maxOf(1L, align))
    kmCopyNonoverlapping(scratch, addrX, bytes)
    kmCopyNonoverlapping(addrX, addrY, bytes)
    kmCopyNonoverlapping(addrY, scratch, bytes)
    kmDealloc(scratch, bytes, maxOf(1L, align))
}

public fun <T> typedSwapNonoverlapping(
    x: T,
    y: T,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    when {
        x is MutPtr && y is MutPtr -> {
            kmSwapNativeRanges(x.rawAddress, y.rawAddress, typeLayoutT.sizeBytes, typeLayoutT.alignBytes)
        }
        x is RustMutableReference<*> && y is RustMutableReference<*> -> {
            kmSwapNativeRanges(
                x.pointer.rawAddress,
                y.pointer.rawAddress,
                x.layout.sizeBytes,
                x.layout.alignBytes,
            )
        }
        else -> {
            error("typedSwapNonoverlapping requires MutPtr or RustMutableReference arguments")
        }
    }
}

public fun <T> readViaCopy(
    src: T,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): T =
    when (src) {
        is ConstPtr -> typeLayoutT.readNative(src.rawAddress)
        is MutPtr -> typeLayoutT.readNative(src.rawAddress)
        is RustMutableReference<*> -> typeLayoutT.readNative(src.pointer.rawAddress)
        else -> src
    }

public fun <T> writeViaMove(
    dst: Any?,
    value: T,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    when (dst) {
        is MutPtr -> typeLayoutT.writeNative(dst.rawAddress, value)
        is RustMutableReference<*> -> typeLayoutT.writeNative(dst.pointer.rawAddress, value)
        else -> error("writeViaMove requires MutPtr or RustMutableReference destination")
    }
}
