package io.github.kotlinmania.rustlang.alloc.vec

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustTrailingZeros
import io.github.kotlinmania.rustlang.core.result.Result

public fun <T> dedupSimdChunkSize(typeLayoutT: TypeLayout<T>): Int? {
    // The minimal SIMD width on supported platforms
    val SIMD_WIDTH: Int = 16
    val size = sizeOf<T>(typeLayoutT = typeLayoutT)
    // We only have SIMD implementations for these element sizes.
    if (!when (size) {
        1, 2, 4, 8 -> true
        else -> false
    }) {
        return null
    }
    val simdWidth: Int? = SIMD_WIDTH
    return if (simdWidth != null) {
        val simdWidth1 = simdWidth
        simdWidth1 * 2 / size
    } else {
        null
    }
}

public fun <T> findFirstDuplicate(v: List<T>, chunkSize: Int): Int? {
    /**
     * # Safety
     * - `start> 0` and `start + chunkSize <= v.len()`
     * - `0<chunkSize <= 32`
     */
    fun <T> findDuplicate(v1: List<T>, start: Int, chunkSize1: Int): Int? {
        var mask: UInt = 0u
        check(32u.toInt() >= chunkSize1) { "the bit width of mask must be greater than or equal to chunk_size" }
        for (j in 0 until chunkSize1) {
            // SAFETY: start + j always in range [1..v.len())
            val eq = v1.getUnchecked(start + j) == v1.getUnchecked(start + j - 1)
            mask = mask or ((eq.toUInt()) shl j.toInt())
        }
        if (mask != 0u) {
            return start + rustTrailingZeros(mask).toInt()
        }
        return null
    }
    val len = v.size
    if (len <= 1) {
        return null
    }
    var i = 1
    // The scalar fallback path if the length is not long enough
    if (len < chunkSize + 1) {
        while (i < len) {
            // It keeps the same comparison order as `dedupTail`
            //
            // SAFETY: i always in range [1..len)
            if (v.getUnchecked(i) == v.getUnchecked(i - 1)) {
                return i
            }
            i += 1
        }
        return null
    }
    // This loop performs chunkSize comparisons in parallel without early exit,
    // which is what allows for SIMD optimization.
    while (i + chunkSize <= len) {
        // SAFETY: `i >= 1` and `i + chunkSize <= len`
        when (val matchVal = findDuplicate(v, i, chunkSize)) {
            null -> {}
            else -> {
                val duplicate = matchVal
                return duplicate
            }
        }
        i += chunkSize
    }
    // This may check the same elements multiple times.
    // However we don't guarantee `PartialEq.eq` is only called once per pair.
    if (i < len) {
        val start = len - chunkSize
        // SAFETY: `len - chunkSize >= 1` and `start + chunkSize <= len`
        when (val matchVal1 = findDuplicate(v, start, chunkSize)) {
            null -> {}
            else -> {
                val duplicate1 = matchVal1
                return duplicate1
            }
        }
    }
    return null
}

// ///////////////////////////////////////////////////////////////////////////
// Internal methods and functions
// ///////////////////////////////////////////////////////////////////////////
// This function is no longer used internally (`fromElemGlobal` is used instead).
// It is only kept so as not to break a few crates who mistakenly used it at a
// time when we didn't have the ability to gate internal methods used by public macros.
// NOTE: the real deprecation version is the same as the stabilization version.
// I.e. it should never have been used even in version 1.0.
public fun <T> fromElem(elem: T, n: Int): Vec<T> = fromElemGlobal(elem, n)

public fun <T> fromElemGlobal(elem: T, n: Int, fromElemTFn: (T, Int, Any?) -> Vec<T>): Vec<T> = fromElemTFn(elem, n, Global)

public fun <T, A : Allocator> fromElemIn(elem: T, n: Int, alloc: A, fromElemTFn: (T, Int, A) -> Vec<T, A>): Vec<T, A> = fromElemTFn(elem, n, alloc)

// SAFETY: enforced by `TransmuteFrom`

// note: test pulls in std, which causes errors here

/**
 * Converts a vector into a boxed slice.
 *
 * This conversion is in-place if `vec.len() == vec.capacity()`. Otherwise, excess capacity
 * is discarded by reallocating before doing the conversion.
 *
 * # Examples
 *
 * ```kotlin
 * import boxed.Holder
 *
 * check(Holder.from(mutableListOf(1, 2, 3)) == mutableListOf(1, 2, 3).intoBoxedSlice())
 * ```
 *
 * Any excess capacity is removed:
 * ```kotlin
 * import boxed.Holder
 *
 * var vec = Vec.withCapacity(10)
 * vec.extend(listOf(1, 2, 3))
 * check(Holder.from(vec) == mutableListOf(1, 2, 3).intoBoxedSlice())
 * ```
 */
public fun <T, A : Allocator> fromBoxTSliceA(v: Vec<T, A>): List<T> = v.intoBoxedSlice()

/**
 * Gets the entire contents of the `List<T>` as an array,
 * if its size exactly matches that of the requested array.
 *
 * # Examples
 *
 * ```kotlin
 * check(mutableListOf(1, 2, 3).tryInto() == Result.success(listOf(1, 2, 3)))
 * check(Vec.new().tryInto() == Result.success(listOf()))
 * ```
 *
 * If the length doesn't match, the input comes back in `Err`:
 * ```kotlin
 * val r: Result<IntArray> = (0 until 10).collect<Vec>().tryInto()
 * check(r == Result.failure(mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)))
 * ```
 *
 * If you're fine with just getting a prefix of the `List<T>`,
 * you can call [`truncate`](Vec.truncate) first.
 * ```kotlin
 * var v = "hello world".intoBytes()
 * v.sort()
 * v.truncate(2)
 * val (a, b) = v.tryInto().unwrap()
 * check(a == 32u)
 * check(b == 100u)
 * ```
 */
public fun <T, A : Allocator> tryFromTArrayn(vec: Vec<T, A>, n: Int, typeLayoutT: TypeLayout<T>): Result<List<T>, Vec<T, A>> {
    if (vec.len() != n) {
        return Result.Err(vec)
    }
    // SAFETY: `.setLen(0)` is always sound.
    vec.setLen(0)
    // SAFETY: A `List`s pointer is always aligned properly, and
    // the alignment the array needs is the same as the items.
    // We checked earlier that we have sufficient items.
    // The items will not double-drop as the `setLen`
    // tells the `List` not to also drop them.
    val array = read(constPtrFromRustCast(vec.asPtr()), typeLayoutT = fixedListTypeLayout(typeLayoutT, (n).toInt()))
    return Result.Ok(array)
}
