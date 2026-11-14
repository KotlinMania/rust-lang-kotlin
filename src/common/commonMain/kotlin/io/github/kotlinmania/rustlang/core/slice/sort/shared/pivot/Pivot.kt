// port-lint: source slice/sort/shared/pivot.rs
package io.github.kotlinmania.rustlang.core.slice.sort.shared.pivot

import io.github.kotlinmania.rustlang.core.intrinsics.abortImmediate
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// This module contains the logic for pivot selection.

// Recursively select a pseudomedian if above this threshold.
public val PSEUDO_MEDIAN_REC_THRESHOLD: Int = 64

/**
 * Selects a pivot from `v`. Algorithm taken from glidesort by Orson Peters.
 *
 * This chooses a pivot by sampling an adaptive amount of points, approximating
 * the quality of a median of sqrt(n) elements.
 */
public fun <T> choosePivot(v: List<T>, isLess: (T, T) -> Boolean): Int {
    // We use unchecked code and raw pointers here because we're dealing with
    // heavy recursion. Passing safe slices around would involve a lot of
    // branches and function call overhead.
    val len = v.size
    if (len < 8) {
        abortImmediate()
    }
    // SAFETY: a, b, c point to initialized regions of lenDiv8 elements,
    // satisfying median3 and median3Rec's preconditions as vBase points
    // to an initialized region of n = len elements.
    val index =
        run {
            val vBase = v.asPtr()
            val lenDiv8 = len / 8
            val a = vBase
            // [0, floor(n/8))
            val b = ConstPtr((vBase).rawAddress + ((lenDiv8 * 4).toLong() * (ObjectSlotLayout<T>()).strideBytes))
            // [4*floor(n/8), 5*floor(n/8))
            val c = ConstPtr((vBase).rawAddress + ((lenDiv8 * 7).toLong() * (ObjectSlotLayout<T>()).strideBytes))
            // [7*floor(n/8), 8*floor(n/8))
            if (len < PSEUDO_MEDIAN_REC_THRESHOLD) {
                median3((ObjectSlotLayout<T>()).readNative((a).rawAddress), (ObjectSlotLayout<T>()).readNative((b).rawAddress), (ObjectSlotLayout<T>()).readNative((c).rawAddress), isLess).offsetFromUnsigned(vBase)
            } else {
                median3Rec(a, b, c, lenDiv8, isLess).offsetFromUnsigned(vBase)
            }
        }
    // SAFETY: preconditions must have been met for offsetFromUnsigned()
    return run {
        assertUnchecked(index < v.size)
        index
    }
}

/**
 * Calculates an approximate median of 3 elements from sections a, b, c, or
 * recursively from an approximation of each, if they're large enough. By
 * dividing the size of each section by 8 when recursing we have logarithmic
 * recursion depth and overall sample from f(n) = 3*f(n/8) -> f(n) =
 * O(n^(log(3)/log(8))) ~= O(n^0.528) elements.
 *
 * SAFETY: a, b, c must point to the start of initialized regions of memory of
 * at least n elements.
 */
public fun <T> median3Rec(a: ConstPtr, b: ConstPtr, c: ConstPtr, n: Int, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T>): ConstPtr {
    var a1 = a
    var b1 = b
    var c1 = c
    // SAFETY: a, b, c still point to initialized regions of n / 8 elements,
    // by the exact same logic as in choosePivot.
    return run {
        if (n * 8 >= PSEUDO_MEDIAN_REC_THRESHOLD) {
            val n8 = n / 8
            a1 = median3Rec(a1, ConstPtr((a1).rawAddress + ((n8 * 4).toLong() * (typeLayoutT).strideBytes)), ConstPtr((a1).rawAddress + ((n8 * 7).toLong() * (typeLayoutT).strideBytes)), n8, isLess)
            b1 = median3Rec(b1, ConstPtr((b1).rawAddress + ((n8 * 4).toLong() * (typeLayoutT).strideBytes)), ConstPtr((b1).rawAddress + ((n8 * 7).toLong() * (typeLayoutT).strideBytes)), n8, isLess)
            c1 = median3Rec(c1, ConstPtr((c1).rawAddress + ((n8 * 4).toLong() * (typeLayoutT).strideBytes)), ConstPtr((c1).rawAddress + ((n8 * 7).toLong() * (typeLayoutT).strideBytes)), n8, isLess)
        }
        median3((typeLayoutT).readNative((a1).rawAddress), (typeLayoutT).readNative((b1).rawAddress), (typeLayoutT).readNative((c1).rawAddress), isLess)
    }
}

/**
 * Calculates the median of 3 elements.
 *
 * SAFETY: a, b, c must be valid initialized elements.
 */
public fun <T> median3(a: T, b: T, c: T, isLess: (T, T) -> Boolean): ConstPtr {
    // Compiler tends to make this branchless when sensible, and avoids the
    // third comparison when not.
    val x = isLess(a, b)
    val y = isLess(a, c)
    return if (x == y) {
        // If x=y=0 then b, c <= a. In this case we want to return max(b, c).
        // If x=y=1 then a < b, c. In this case we want to return min(b, c).
        // By toggling the outcome of b < c using XOR x we get this behavior.
        val z = isLess(b, c)
        if (z xor x) {
            c
        } else {
            b
        }
    } else {
        // Either c <= a < b or b <= a < c, thus a is our median.
        a
    }
}
