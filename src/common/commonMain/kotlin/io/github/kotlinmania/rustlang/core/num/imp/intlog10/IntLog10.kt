// port-lint: source num/imp/int_log10.rs
package io.github.kotlinmania.rustlang.core.num.imp.intlog10

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU128Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU16Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU64Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero

// These functions compute the integer logarithm of their type, assuming
// that someone has already checked that the value is strictly positive.

// 0 < val <= u8.Max
public fun u8Impl(value: UByte): UInt {
    // For better performance, avoid branches by assembling the solution
    // in the bits above the low 8 bits.
    // Adding c1 to val gives 10 in the top bits for val < 10, 11 for val >= 10
    val C1: UInt = 0x300u - 10u
    // 758
    // Adding c2 to val gives 01 in the top bits for val < 100, 10 for val >= 100
    val C2: UInt = 0x200u - 100u
    val value1 = value.toUInt()
    // 412
    // Value of top bits:
    // +c1  +c2  1&2
    // 0..=9   10   01   00 = 0
    // 10..=99   11   01   01 = 1
    // 100..=255   11   10   10 = 2
    return ((value1 + C1) and (value1 + C2)) shr 8
}

// 0 < val < 100_000
public fun lessThan5(value: UInt): UInt {
    // Similar to u8, when adding one of these constants to val,
    // we get two possible bit patterns above the low 17 bits,
    // depending on whether val is below or above the threshold.
    val C1: UInt = 0x60000u - 10u
    // 393206
    val C2: UInt = 0x80000u - 100u
    // 524188
    val C3: UInt = 0xE0000u - 1000u
    // 916504
    val C4: UInt = 0x80000u - 10000u
    // 514288
    // Value of top bits:
    // +c1  +c2  1&2  +c3  +c4  3&4   ^
    // 0..=9  010  011  010  110  011  010  000 = 0
    // 10..=99  011  011  011  110  011  010  001 = 1
    // 100..=999  011  100  000  110  011  010  010 = 2
    // 1000..=9999  011  100  000  111  011  011  011 = 3
    // 10000..=99999  011  100  000  111  100  100  100 = 4
    return (((value + C1) and (value + C2)) xor ((value + C3) and (value + C4))) shr 17
}

// 0 < val <= u16.Max
public fun u16Impl(value: UShort): UInt = lessThan5(value.toUInt())

// 0 < val <= u32.Max
public fun u32Impl(value: UInt): UInt {
    var value1 = value
    var log = 0
    if (value1 >= 100000u) {
        value1 /= 100000u
        log += 5
    }
    return log + lessThan5(value1)
}

// 0 < val <= u64.Max
public fun u64Impl(value: ULong): UInt {
    var value1 = value
    var log = 0
    if (value1 >= 10000000000uL) {
        value1 /= 10000000000uL
        log += 10
    }
    if (value1 >= 100000uL) {
        value1 /= 100000uL
        log += 5
    }
    return log + lessThan5(value1.toUInt())
}

// 0 < val <= u128.Max
public fun u128Impl(value: U128): UInt {
    var value1 = value
    var log = 0
    if (value1 >= U128(0x4ee2d6d415buL, 0x85acef8100000000uL)) {
        value1 /= U128(0x4ee2d6d415buL, 0x85acef8100000000uL)
        log += 32
        return log + u32Impl(value1.toUInt())
    }
    if (value1 >= U128(0x0uL, 0x2386f26fc10000uL)) {
        value1 /= U128(0x0uL, 0x2386f26fc10000uL)
        log += 16
    }
    return log + u64Impl(value1.toULong())
}

public fun u8(value: NonZero<UByte, NonZeroU8Inner>): UInt {
    val result = u8Impl(value.get())
    assertUnchecked(result <= u8Impl(UByte.MAX_VALUE))
    return result
}

public fun u16(value: NonZero<UShort, NonZeroU16Inner>): UInt {
    val result = u16Impl(value.get())
    assertUnchecked(result <= u16Impl(UShort.MAX_VALUE))
    return result
}

public fun u32(value: NonZero<UInt, NonZeroU32Inner>): UInt {
    val result = u32Impl(value.get())
    assertUnchecked(result <= u32Impl(UInt.MAX_VALUE))
    return result
}

public fun u64(value: NonZero<ULong, NonZeroU64Inner>): UInt {
    val result = u64Impl(value.get())
    assertUnchecked(result <= u64Impl(ULong.MAX_VALUE))
    return result
}

public fun u128(value: NonZero<U128, NonZeroU128Inner>): UInt {
    val result = u128Impl(value.get())
    assertUnchecked(result <= u128Impl(U128.MAX_VALUE))
    return result
}

public fun usize(value: NonZero<Int, NonZeroUsizeInner>): UInt {
    val implFn = ::u64
    // SAFETY: We have selected the correct `implFn`, so the converting `val` to the argument is
    // safe.
    return implFn(NonZero.newUnchecked(value.get().toUInt()))
}

public fun i8(value: Byte): UInt? =
    if (value > 0.toByte()) {
        val result = u8Impl(value.castUnsigned())
        run {
            assertUnchecked(result <= u8Impl(Byte.MAX_VALUE.castUnsigned()))
        }
        result
    } else {
        null
    }

public fun i16(value: Short): UInt? =
    if (value > 0.toShort()) {
        val result = u16Impl(value.castUnsigned())
        run {
            assertUnchecked(result <= u16Impl(Short.MAX_VALUE.castUnsigned()))
        }
        result
    } else {
        null
    }

public fun i32(value: Int): UInt? =
    if (value > 0) {
        val result = u32Impl(value.castUnsigned())
        run {
            assertUnchecked(result <= u32Impl(Int.MAX_VALUE.castUnsigned()))
        }
        result
    } else {
        null
    }

public fun i64(value: Long): UInt? =
    if (value > 0L) {
        val result = u64Impl(value.castUnsigned())
        run {
            assertUnchecked(result <= u64Impl(Long.MAX_VALUE.castUnsigned()))
        }
        result
    } else {
        null
    }

public fun i128(value: I128): UInt? =
    if (value > I128(0x0L, 0x0uL)) {
        val result = u128Impl(value.castUnsigned())
        run {
            assertUnchecked(result <= u128Impl(I128.MAX_VALUE.castUnsigned()))
        }
        result
    } else {
        null
    }

/**
 * Instantiate this panic logic once, rather than for all the ilog methods
 * on every single primitive type.
 */
public fun panicForNonpositiveArgument(): Nothing {
    error("argument of integer logarithm must be positive")
}
