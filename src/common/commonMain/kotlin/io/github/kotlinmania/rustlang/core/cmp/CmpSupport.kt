package io.github.kotlinmania.rustlang.core.cmp

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.num.wrapping.Wrapping
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull

public fun interface OrderingSupplier {
    public fun get(): Ordering
}

public fun interface ValueComparator<T> {
    public fun compare(left: T, right: T): Ordering
}

private fun orderingToInt(ordering: Ordering): Int =
    when (ordering) {
        Ordering.Less -> -1
        Ordering.Equal -> 0
        Ordering.Greater -> 1
    }

/**
 * Compares two Rust/Kotlin scalar, pointer, or ordered wrapper values without requiring an F-bounded
 * `T : Comparable<T>` type parameter.
 */
public fun compareNaturalOrder(left: Any?, right: Any?): Int {
    if (left === right) return 0
    if (left == null) return -1
    if (right == null) return 1
    return when (left) {
        is Byte -> left.compareTo(right as Byte)
        is UByte -> left.compareTo(right as UByte)
        is Short -> left.compareTo(right as Short)
        is UShort -> left.compareTo(right as UShort)
        is Int -> left.compareTo(right as Int)
        is UInt -> left.compareTo(right as UInt)
        is Long -> left.compareTo(right as Long)
        is ULong -> left.compareTo(right as ULong)
        is Float -> left.compareTo(right as Float)
        is Double -> left.compareTo(right as Double)
        is Char -> left.compareTo(right as Char)
        is Boolean -> left.compareTo(right as Boolean)
        is String -> left.compareTo(right as String)
        is F16 -> left.compareTo(right as F16)
        is F128 -> left.compareTo(right as F128)
        is U128 -> left.compareTo(right as U128)
        is I128 -> left.compareTo(right as I128)
        is Alignment -> orderingToInt(left.cmp(right as Alignment))
        is CStr -> orderingToInt(left.cmp(right as CStr))
        is ConstPtr -> left.rawAddress.toULong().compareTo((right as ConstPtr).rawAddress.toULong())
        is MutPtr -> left.rawAddress.toULong().compareTo((right as MutPtr).rawAddress.toULong())
        is NonNull -> left.rawAddress.toULong().compareTo((right as NonNull).rawAddress.toULong())
        is Reverse<*> -> compareNaturalOrder((right as Reverse<*>).value, left.value)
        is Wrapping<*> -> compareNaturalOrder(left.value, (right as Wrapping<*>).value)
        else -> error("Unsupported natural ordering receiver ${left::class}; supply an explicit ValueComparator")
    }
}

public fun min(v1: Byte, v2: Byte): Byte = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Byte, v2: Byte): Byte = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: UByte, v2: UByte): UByte = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: UByte, v2: UByte): UByte = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Short, v2: Short): Short = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Short, v2: Short): Short = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: UShort, v2: UShort): UShort = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: UShort, v2: UShort): UShort = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Int, v2: Int): Int = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Int, v2: Int): Int = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: UInt, v2: UInt): UInt = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: UInt, v2: UInt): UInt = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Long, v2: Long): Long = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Long, v2: Long): Long = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: ULong, v2: ULong): ULong = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: ULong, v2: ULong): ULong = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Float, v2: Float): Float = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Float, v2: Float): Float = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Double, v2: Double): Double = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Double, v2: Double): Double = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Char, v2: Char): Char = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Char, v2: Char): Char = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: Boolean, v2: Boolean): Boolean = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: Boolean, v2: Boolean): Boolean = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: String, v2: String): String = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: String, v2: String): String = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: U128, v2: U128): U128 = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: U128, v2: U128): U128 = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: I128, v2: I128): I128 = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: I128, v2: I128): I128 = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: F16, v2: F16): F16 = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: F16, v2: F16): F16 = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: F128, v2: F128): F128 = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: F128, v2: F128): F128 = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: ConstPtr, v2: ConstPtr): ConstPtr = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: ConstPtr, v2: ConstPtr): ConstPtr = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: MutPtr, v2: MutPtr): MutPtr = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: MutPtr, v2: MutPtr): MutPtr = if (compareNaturalOrder(v2, v1) > 0) v2 else v1

public fun min(v1: NonNull, v2: NonNull): NonNull = if (compareNaturalOrder(v2, v1) < 0) v2 else v1

public fun max(v1: NonNull, v2: NonNull): NonNull = if (compareNaturalOrder(v2, v1) > 0) v2 else v1
