// port-lint: source select.rs
package io.github.kotlinmania.rustlang.coresimd

import io.github.kotlinmania.rustlang.coresimd.simd.MaskElement
import io.github.kotlinmania.rustlang.coresimd.simd.SimdElement

/**
 * Choose elements from two vectors using a mask.
 *
 * For each element in the mask, choose the corresponding element from `trueValues` if
 * that element mask is true, and `falseValues` if that element mask is false.
 *
 * If the mask is `ULong`, it's treated as a bitmask with the least significant bit
 * corresponding to the first element.
 *
 * # Examples
 *
 * ## Selecting values from `Simd`
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 1, 2, 3))
 * val b = Simd.fromArray(listOf(4, 5, 6, 7))
 * val mask = Mask.fromArray(booleanArrayOf(true, false, false, true))
 * val c = mask.select(a, b)
 * check(c.toArray() == listOf(0, 5, 6, 3))
 * ```
 *
 * ## Selecting values from `Mask`
 * ```kotlin
 * val a = Mask.fromArray(booleanArrayOf(true, true, false, false))
 * val b = Mask.fromArray(booleanArrayOf(false, false, true, true))
 * val mask = Mask.fromArray(booleanArrayOf(true, false, false, true))
 * val c = mask.select(a, b)
 * check(c.toArray() == booleanArrayOf(true, false, true, false))
 * ```
 *
 * ## Selecting with a bitmask
 * ```kotlin
 * val a = Mask.fromArray(booleanArrayOf(true, true, false, false))
 * val b = Mask.fromArray(booleanArrayOf(false, false, true, true))
 * val mask = 0x9
 * val c = mask.select(a, b)
 * check(c.toArray() == booleanArrayOf(true, false, true, false))
 * ```
 */
public interface Select<T> {
    /**
     * Choose elements
     */
    public fun select(trueValues: T, falseValues: T): T
}

public fun <Mask, T, U> Mask<U, N>.select(trueValues: Simd<T, N>, falseValues: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?>, U : MaskElement<U> {
    // Safety:
    // simdAs between masks is always safe (they're vectors of ints).
    // simdSelect uses a mask that matches the width and number of elements
    return run {
        val mask: Simd<Mask> = simd.simdAs(this.toSimd())
        simd.simdSelect(mask, trueValues, falseValues)
    }
}

public fun <Mask, T> ULong.select(trueValues: Simd<T, N>, falseValues: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> {
    fun <Mask, T, U : FixEndianness<U>> selectImpl(bitmask: U, trueValues1: Simd<T>, falseValues1: Simd<T>, m: Int, n: Int): Simd<T> where T : SimdElement<Any?> {
        val default = trueValues1[0]
        val trueValues2 = trueValues1.resize(default)
        val falseValues2 = falseValues1.resize(default)
        // LLVM assumes bit order should match endianness
        val bitmask1 = bitmask.fixEndianness()
        // Safety: the caller guarantees that the size of U matches M
        val selected = simd.simdSelectBitmask(bitmask1, trueValues2, falseValues2)
        return selected.resize(default)
    }
    run {
        check(n <= 64) { "number of elements can't be greater than 64" }
    }
    // Note modify simdBitmaskSelect to truncate input, making this unnecessary
    return if (n <= 8) {
        val bitmask = this.toUByte()
        // Safety: bitmask matches length
        selectImpl<T, UByte>(bitmask, trueValues, falseValues, 8, n)
    } else if (n <= 16) {
        val bitmask1 = this.toUShort()
        // Safety: bitmask matches length
        selectImpl<T, UShort>(bitmask1, trueValues, falseValues, 16, n)
    } else if (n <= 32) {
        val bitmask2 = this.toUInt()
        // Safety: bitmask matches length
        selectImpl<T, UInt>(bitmask2, trueValues, falseValues, 32, n)
    } else {
        val bitmask3 = this
        // Safety: bitmask matches length
        selectImpl<T, ULong>(bitmask3, trueValues, falseValues, 64, n)
    }
}

public fun <T, U> Mask<U, N>.select(trueValues: Mask<T, N>, falseValues: Mask<T, N>, n: Int): Mask<T> where T : MaskElement<T>, U : MaskElement<U> {
    val selected: Simd<T> = Select.Select(this, trueValues.toSimd(), falseValues.toSimd())
    // Safety: all values come from masks
    return Mask.fromSimdUnchecked(selected)
}

public fun <T> ULong.select(trueValues: Mask<T, N>, falseValues: Mask<T, N>, n: Int): Mask<T> where T : MaskElement<T> {
    val selected: Simd<T> = Select.Select(this, trueValues.toSimd(), falseValues.toSimd())
    // Safety: all values come from masks
    return Mask.fromSimdUnchecked(selected)
}
