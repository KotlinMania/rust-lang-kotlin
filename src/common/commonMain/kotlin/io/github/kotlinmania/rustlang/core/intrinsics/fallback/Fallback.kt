// port-lint: source intrinsics/fallback.rs
package io.github.kotlinmania.rustlang.core.intrinsics.fallback

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.assume
import io.github.kotlinmania.rustlang.core.intrinsics.disjointBitor
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr
import io.github.kotlinmania.rustlang.core.num.overflowingAdd
import io.github.kotlinmania.rustlang.core.num.wrappingAdd
import io.github.kotlinmania.rustlang.core.num.wrappingMul

public interface CarryingMulAdd<T, out Unsigned> {
    public fun carryingMulAdd(multiplicand: T, addend: T, carry: T): Pair<Unsigned, T>
}

public fun UByte.carryingMulAdd(a: UByte, b: UByte, c: UByte): Pair<UByte, UByte> {
    val wide = (this.toUShort()) * (a.toUShort()) + (b.toUShort()) + (c.toUShort())
    return Pair(((wide as Any?) as Pair<UByte, UByte>), ((((wide.toUInt() shr (8u.toInt())).toUShort()) as Any?) as Pair<UByte, UByte>))
}

public fun UShort.carryingMulAdd(a: UShort, b: UShort, c: UShort): Pair<UShort, UShort> {
    val wide = (this.toUInt()) * (a.toUInt()) + (b.toUInt()) + (c.toUInt())
    return Pair(((wide as Any?) as Pair<UShort, UShort>), (((wide shr 16u.toInt()) as Any?) as Pair<UShort, UShort>))
}

public fun UInt.carryingMulAdd(a: UInt, b: UInt, c: UInt): Pair<UInt, UInt> {
    val wide = (this.toULong()) * (a.toULong()) + (b.toULong()) + (c.toULong())
    return Pair(((wide as Any?) as Pair<UInt, UInt>), (((wide shr 32u.toInt()) as Any?) as Pair<UInt, UInt>))
}

public fun ULong.carryingMulAdd(a: ULong, b: ULong, c: ULong): Pair<ULong, ULong> {
    val wide = (U128.fromULong(this)) * (U128.fromULong(a)) + (U128.fromULong(b)) + (U128.fromULong(c))
    return Pair(((wide as Any?) as Pair<ULong, ULong>), (((wide shr 64u.toInt()) as Any?) as Pair<ULong, ULong>))
}

public fun Int.carryingMulAdd(a: Int, b: Int, c: Int): Pair<Int, Int> {
    val wide = (U128.fromInt(this)) * (U128.fromInt(a)) + (U128.fromInt(b)) + (U128.fromInt(c))
    return Pair(((wide as Any?) as Pair<Int, Int>), (((wide shr 32u.toInt()) as Any?) as Pair<Int, Int>))
}

public fun Byte.carryingMulAdd(a: Byte, b: Byte, c: Byte): Pair<UByte, Byte> {
    val wide = (this.toShort()) * (a.toShort()) + (b.toShort()) + (c.toShort())
    return Pair(((wide as Any?) as Pair<UByte, Byte>), (((wide shr 8u.toInt()) as Any?) as Pair<UByte, Byte>))
}

public fun Short.carryingMulAdd(a: Short, b: Short, c: Short): Pair<UShort, Short> {
    val wide = (this.toInt()) * (a.toInt()) + (b.toInt()) + (c.toInt())
    return Pair(((wide as Any?) as Pair<UShort, Short>), (((wide shr 16u.toInt()) as Any?) as Pair<UShort, Short>))
}

public fun Long.carryingMulAdd(a: Long, b: Long, c: Long): Pair<ULong, Long> {
    val wide = (I128.fromLong(this)) * (I128.fromLong(a)) + (I128.fromLong(b)) + (I128.fromLong(c))
    return Pair(((wide as Any?) as Pair<ULong, Long>), (((wide shr 64u.toInt()) as Any?) as Pair<ULong, Long>))
}

public typealias UDoubleSize = U128

public fun wideMulU128(a: U128, b: U128): Pair<U128, U128> {
    fun toLowHigh(x: U128): List<U128> {
        val MASK: U128 = U128.fromULong(ULong.MAX_VALUE)
        return listOf(x and MASK, x shr 64)
    }
    fun fromLowHigh(x: List<U128>): U128 = x[0] or (x[1] shl 64)
    fun scalarMul(lowHigh: List<U128>, k: U128): List<U128> {
        val (x, c) = toLowHigh(k * lowHigh[0])
        val (y, z) = toLowHigh(k * lowHigh[1] + c)
        return listOf(x, y, z)
    }
    val a1 = toLowHigh(a)
    val b1 = toLowHigh(b)
    val low = scalarMul(a1, b1[0])
    val high = scalarMul(a1, b1[1])
    val r0 = low[0]
    val (r1, c) = toLowHigh(low[1] + high[0])
    val (r2, c1) = toLowHigh(low[2] + high[1] + c)
    val r3 = high[2] + c1
    return Pair(fromLowHigh(listOf(r0, r1)), fromLowHigh(listOf(r2, r3)))
}

public fun U128.carryingMulAdd(b: U128, c: U128, d: U128): Pair<U128, U128> {
    var (low, high) = wideMulU128(this, b)
    val (low1, carry) = low.overflowingAdd(c)
    high += U128.fromLong(carry.toLong())
    val (low2, carry1) = low1.overflowingAdd(d)
    high += U128.fromLong(carry1.toLong())
    return Pair(low2, high)
}

public fun I128.carryingMulAdd(b: I128, c: I128, d: I128): Pair<U128, I128> {
    val (low, high) = wideMulU128(this.toU128(), b.toU128())
    var high1 = I128.fromLong(high.toLong())
    high1 = high1.wrappingAdd((this shr 127).wrappingMul(b))
    high1 = high1.wrappingAdd(this.wrappingMul(b shr 127))
    val (low1, carry) = low.overflowingAdd(c.toU128())
    high1 = high1.wrappingAdd((I128.fromLong(carry.toLong())) + (c shr 127))
    val (low2, carry1) = low1.overflowingAdd(d.toU128())
    high1 = high1.wrappingAdd((I128.fromLong(carry1.toLong())) + (d shr 127))
    return Pair(low2, high1)
}

public interface DisjointBitOr<T> {
    /**
     * We just need the trait indirection to handle different types
     * since calling intrinsics with generics doesn't work.
     *
     * # Safety
     *
     * See [disjointBitor], with `self` corresponding to `a` and
     * `other` corresponding to `b`.
     */
    public fun disjointBitor(other: T): T
}

public fun Boolean.disjointBitor(other: Boolean): Boolean {
    assume((this and other) == false)
    return this or other
}

public fun UByte.disjointBitor(other: UByte): UByte {
    assume((this and other) == 0u.toUByte())
    return this or other
}

public fun UShort.disjointBitor(other: UShort): UShort {
    assume((this and other) == 0u.toUShort())
    return this or other
}

public fun UInt.disjointBitor(other: UInt): UInt {
    assume((this and other) == 0u)
    return this or other
}

public fun ULong.disjointBitor(other: ULong): ULong {
    assume((this and other) == 0uL)
    return this or other
}

public fun U128.disjointBitor(other: U128): U128 {
    assume((this and other) == U128(0x0uL, 0x0uL))
    return this or other
}

public fun Int.disjointBitor(other: Int): Int {
    assume((this and other) == 0)
    return this or other
}

public fun Byte.disjointBitor(other: Byte): Byte {
    assume(((this.toInt() and other.toInt()).toByte()) == 0.toByte())
    return (this.toInt() or other.toInt()).toByte()
}

public fun Short.disjointBitor(other: Short): Short {
    assume(((this.toInt() and other.toInt()).toShort()) == 0.toShort())
    return (this.toInt() or other.toInt()).toShort()
}

public fun Long.disjointBitor(other: Long): Long {
    assume((this and other) == 0L)
    return this or other
}

public fun I128.disjointBitor(other: I128): I128 {
    assume((this and other) == I128(0x0L, 0x0uL))
    return this or other
}

public interface FunnelShift<T> {
    /**
     * We just need the trait indirection to handle different types
     * since calling intrinsics with generics doesn't work.
     *
     * # Safety
     *
     * See [uncheckedFunnelShl], with `self` corresponding to `a` and
     * `right` corresponding to `b`.
     */
    public fun uncheckedFunnelShl(right: T, shift: UInt): T

    /**
     * We just need the trait indirection to handle different types
     * since calling intrinsics with generics doesn't work.
     *
     * # Safety
     *
     * See [uncheckedFunnelShr], with `self` corresponding to `a` and
     * `right` corresponding to `b`.
     */
    public fun uncheckedFunnelShr(right: T, shift: UInt): T
}

public fun UByte.uncheckedFunnelShl(right: UByte, shift: UInt): UByte =
    if (shift == 0u) {
        this
    } else {
        disjointBitor(uncheckedShl(this, shift), uncheckedShr(right, 8u - shift))
    }

public fun UByte.uncheckedFunnelShr(right: UByte, shift: UInt): UByte =
    if (shift == 0u) {
        right
    } else {
        disjointBitor(uncheckedShl(this, 8u - shift), uncheckedShr(right, shift))
    }

public fun UShort.uncheckedFunnelShl(right: UShort, shift: UInt): UShort =
    if (shift == 0u) {
        this
    } else {
        disjointBitor(uncheckedShl(this, shift), uncheckedShr(right, 16u - shift))
    }

public fun UShort.uncheckedFunnelShr(right: UShort, shift: UInt): UShort =
    if (shift == 0u) {
        right
    } else {
        disjointBitor(uncheckedShl(this, 16u - shift), uncheckedShr(right, shift))
    }

public fun UInt.uncheckedFunnelShl(right: UInt, shift: UInt): UInt =
    if (shift == 0u) {
        this
    } else {
        disjointBitor(uncheckedShl(this, shift), uncheckedShr(right, 32u - shift))
    }

public fun UInt.uncheckedFunnelShr(right: UInt, shift: UInt): UInt =
    if (shift == 0u) {
        right
    } else {
        disjointBitor(uncheckedShl(this, 32u - shift), uncheckedShr(right, shift))
    }

public fun ULong.uncheckedFunnelShl(right: ULong, shift: UInt): ULong =
    if (shift == 0u) {
        this
    } else {
        disjointBitor(uncheckedShl(this, shift), uncheckedShr(right, 64u - shift))
    }

public fun ULong.uncheckedFunnelShr(right: ULong, shift: UInt): ULong =
    if (shift == 0u) {
        right
    } else {
        disjointBitor(uncheckedShl(this, 64u - shift), uncheckedShr(right, shift))
    }

public fun U128.uncheckedFunnelShl(right: U128, shift: UInt): U128 =
    if (shift == 0u) {
        this
    } else {
        disjointBitor(uncheckedShl(this, shift), uncheckedShr(right, 128u - shift))
    }

public fun U128.uncheckedFunnelShr(right: U128, shift: UInt): U128 =
    if (shift == 0u) {
        right
    } else {
        disjointBitor(uncheckedShl(this, 128u - shift), uncheckedShr(right, shift))
    }

public fun Int.uncheckedFunnelShl(right: Int, shift: UInt): Int =
    if (shift == 0u) {
        this
    } else {
        disjointBitor(uncheckedShl(this, shift), uncheckedShr(right, 32u - shift))
    }

public fun Int.uncheckedFunnelShr(right: Int, shift: UInt): Int =
    if (shift == 0u) {
        right
    } else {
        disjointBitor(uncheckedShl(this, 32u - shift), uncheckedShr(right, shift))
    }

public interface CarrylessMul<T> {
    /**
     * See [carrylessMul]; we just need the trait indirection to handle
     * different types since calling intrinsics with generics doesn't work.
     */
    public fun carrylessMul(rhs: T): T
}

public fun UByte.carrylessMul(rhs: UByte): UByte {
    var result = 0u
    var i = 0
    while (i < 8u) {
        if (((rhs.toUInt() shr (i.toInt())).toUByte()) and 1u.toUByte() != 0u.toUByte()) {
            result = result xor ((this.toUInt() shl (i.toInt())).toUByte())
        }
        i += 1
    }
    return result
}

public fun UShort.carrylessMul(rhs: UShort): UShort {
    var result = 0u
    var i = 0
    while (i < 16u) {
        if (((rhs.toUInt() shr (i.toInt())).toUShort()) and 1u.toUShort() != 0u.toUShort()) {
            result = result xor ((this.toUInt() shl (i.toInt())).toUShort())
        }
        i += 1
    }
    return result
}

public fun UInt.carrylessMul(rhs: UInt): UInt {
    var result = 0u
    var i = 0
    while (i < 32u) {
        if ((rhs shr i.toInt()) and 1u != 0u) {
            result = result xor (this shl i.toInt())
        }
        i += 1
    }
    return result
}

public fun ULong.carrylessMul(rhs: ULong): ULong {
    var result = 0uL
    var i = 0
    while (i < 64u) {
        if ((rhs shr i.toInt()) and 1uL != 0uL) {
            result = result xor (this shl i.toInt())
        }
        i += 1
    }
    return result
}

public fun U128.carrylessMul(rhs: U128): U128 {
    var result = U128(0x0uL, 0x0uL)
    var i = 0
    while (i < 128u) {
        if ((rhs shr i.toInt()) and U128(0x0uL, 0x1uL) != U128(0x0uL, 0x0uL)) {
            result = result xor (this shl i.toInt())
        }
        i += 1
    }
    return result
}

public fun Int.carrylessMul(rhs: Int): Int {
    var result = 0
    var i = 0
    while (i < 32u) {
        if ((rhs shr i.toInt()) and 1 != 0) {
            result = result xor (this shl i.toInt())
        }
        i += 1
    }
    return result
}
