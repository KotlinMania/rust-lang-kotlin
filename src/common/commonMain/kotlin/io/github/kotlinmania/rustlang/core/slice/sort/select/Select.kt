// port-lint: source slice/sort/select.rs
package io.github.kotlinmania.rustlang.core.slice.sort.select

import io.github.kotlinmania.rustlang.core.num.rustSaturatingMul
import io.github.kotlinmania.rustlang.mem.swap
import io.github.kotlinmania.rustlang.slice.sort.shared.pivot.choosePivot
import io.github.kotlinmania.rustlang.slice.sort.shared.smallsort.insertionSortShiftLeft
import io.github.kotlinmania.rustlang.slice.sort.unstable.quicksort.partition

// This module contains the implementation for `slice.selectNthUnstable`.
// It uses an introselect algorithm based on ipnsort by Lukas Bergdoll and Orson Peters,
// published at: <https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort>
//
// The fallback algorithm used for introselect is Median of Medians using Tukey's Ninther
// for pivot selection. Using this as a fallback ensures O(n) worst case running time with
// better performance than one would get using heapsort as fallback.

/**
 * Reorders the slice such that the element at `index` is at its final sorted position.
 */
public fun <T> partitionAtIndex(v: MutableList<T>, index: Int, isLess: (T, T) -> Boolean): Triple<MutableList<T>, T, MutableList<T>> {
    val len = v.size
    // Puts a lower limit of 1 on `len`.
    if (index >= len) {
        error("partition_at_index index $index greater than length of slice $len")
    }
    if (T.IS_ZST) {
        // Sorting has no meaningful behavior on zero-sized types. Do nothing.
    } else if (index == len - 1) {
        // Find max element and place it in the last position of the array. We're free to use
        // `unwrap()` here because we checked that `v` is not empty.
        val maxIdx = maxIndex(v, isLess).unwrap()
        v.swap(maxIdx, index)
    } else if (index == 0) {
        // Find min element and place it in the first position of the array. We're free to use
        // `unwrap()` here because we checked that `v` is not empty.
        val minIdx = minIndex(v, isLess).unwrap()
        v.swap(minIdx, index)
    } else {
        medianOfMedians(v, isLess, index)
    }
    val (left, right) = v.splitAtMut(index)
    val (pivot, right1) = right.splitAtMut(1)
    val pivot1 = pivot[0]
    return Triple(left, pivot1, right1)
}

// For small sub-slices it's faster to use a dedicated small-sort, but because it is only called at
// most once, it doesn't make sense to use something more sophisticated than insertion-sort.
public val INSERTION_SORT_THRESHOLD: Int = 16

public fun <T> partitionAtIndexLoop(v: MutableList<T>, index: Int, ancestorPivot: T?, isLess: (T, T) -> Boolean) {
    var v1 = v
    var index1 = index
    var ancestorPivot1 = ancestorPivot
    // Limit the amount of iterations and fall back to fast deterministic selection to ensure O(n)
    // worst case running time. This limit needs to be constant, because using `ilog2(len)` like in
    // `sort` would result in O(n log n) time complexity. The exact value of the limit is chosen
    // somewhat arbitrarily, but for most inputs bad pivot selections should be relatively rare, so
    // the limit is reached for sub-slices len / (2^limit or less). Which makes the remaining work
    // with the fallback minimal in relative terms.
    var limit = 16
    while (true) {
        if (v1.size <= INSERTION_SORT_THRESHOLD) {
            if (v1.size >= 2) {
                insertionSortShiftLeft(v1, 1, isLess)
            }
            return
        }
        if (limit == 0) {
            medianOfMedians(v1, isLess, index1)
            return
        }
        limit -= 1
        // Choose a pivot
        val pivotPos = choosePivot(v1, isLess)
        // If the chosen pivot is equal to the predecessor, then it's the smallest element in the
        // slice. Partition the slice into elements equal to and elements greater than the pivot.
        // This case is usually hit when the slice contains many duplicate elements.
        if (ancestorPivot1 != null) {
            val p = ancestorPivot1
            val pivot = v1[pivotPos]
            if (!isLess(p, pivot)) {
                val numLt = partition(v1, pivotPos, { a, b -> !isLess(b, a) })
                // Continue sorting elements greater than the pivot. We know that `mid` contains
                // the pivot. So we can continue after `mid`.
                val mid = numLt + 1
                // If we've passed our index, then we're good.
                if (mid > index1) {
                    return
                }
                v1 = v1.subList(mid, v1.size)
                index1 -= mid
                ancestorPivot1 = null
                continue
            }
        }
        val mid1 = partition(v1, pivotPos, isLess)
        // Split the slice into `left`, `pivot`, and `right`.
        val (left, right) = v1.splitAtMut(mid1)
        val (pivot1, right1) = right.splitAtMut(1)
        val pivot2 = pivot1[0]
        if (mid1 < index1) {
            v1 = right1
            index1 = index1 - mid1 - 1
            ancestorPivot1 = pivot2
        } else if (mid1 > index1) {
            v1 = left
        } else {
            // If mid == index, then we're done, since partition() guaranteed that all elements
            // after mid are greater than or equal to mid.
            return
        }
    }
}

/**
 * Helper function that returns the index of the minimum element in the slice using the given
 * comparator function
 */
public fun <T> minIndex(slice: List<T>, isLess: (T, T) -> Boolean): Int? =
    slice.mapIndexed { index, item -> Pair(index, item) }.reduce({ acc, t -> if (isLess(t.second, acc.second)) {
        t
    } else {
        acc
    } }).map({ (i, _) -> i })

/**
 * Helper function that returns the index of the maximum element in the slice using the given
 * comparator function
 */
public fun <T> maxIndex(slice: List<T>, isLess: (T, T) -> Boolean): Int? =
    slice.mapIndexed { index, item -> Pair(index, item) }.reduce({ acc, t -> if (isLess(acc.second, t.second)) {
        t
    } else {
        acc
    } }).map({ (i, _) -> i })

/**
 * Selection algorithm to select the k-th element from the slice in guaranteed O(n) time.
 * This is essentially a quickselect that uses Tukey's Ninther for pivot selection
 */
public fun <T> medianOfMedians(v: MutableList<T>, isLess: (T, T) -> Boolean, k: Int) {
    var v1 = v
    var k1 = k
    if (false) {
        check(k1 < v1.size)
    }
    if (false) {
        check(T.IS_ZST.inv())
    }
    // We now know that `k<v.len() <= Long.MAX`
    while (true) {
        if (v1.size <= INSERTION_SORT_THRESHOLD) {
            if (v1.size >= 2) {
                insertionSortShiftLeft(v1, 1, isLess)
            }
            return
        }
        // `median_of_ { minima, maxima }` can't handle the extreme cases of the first/last element,
        // so we catch them here and just do a linear search.
        if (k1 == v1.size - 1) {
            // Find max element and place it in the last position of the array. We're free to use
            // `unwrap()` here because we know v must not be empty.
            val maxIdx = maxIndex(v1, isLess).unwrap()
            v1.swap(maxIdx, k1)
            return
        } else if (k1 == 0) {
            // Find min element and place it in the first position of the array. We're free to use
            // `unwrap()` here because we know v must not be empty.
            val minIdx = minIndex(v1, isLess).unwrap()
            v1.swap(minIdx, k1)
            return
        }
        val p = medianOfNinthers(v1, isLess)
        if (p == k1) {
            return
        } else if (p > k1) {
            v1 = v1.subList(0, p)
        } else {
            // Since `p<k<v.len()`, `p + 1` doesn't overflow and is
            // a valid index into the slice.
            v1 = v1.subList(p + 1, v1.size)
            k1 -= p + 1
        }
    }
}

// Optimized for when `k` lies somewhere in the middle of the slice. Selects a pivot
// as close as possible to the median of the slice. For more details on how the algorithm
// operates, refer to the paper <https://drops.dagstuhl.de/opus/volltexte/2017/7612/pdf/LIPIcs-SEA-2017-24.pdf>.
public fun <T> medianOfNinthers(v: MutableList<T>, isLess: (T, T) -> Boolean): Int {
    // use `saturatingMul` so the multiplication doesn't overflow on 16-bit platforms.
    val frac =
        if (v.size <= 1024) {
            v.size / 12
        } else if (v.size <= rustSaturatingMul(128, 1024)) {
            v.size / 64
        } else {
            v.size / 1024
        }
    val pivot = frac / 2
    val lo = v.size / 2 - pivot
    val hi = frac + lo
    val gap = (v.size - 9 * frac) / 4
    var a = lo - 4 * frac - gap
    var b = hi + gap
    for (i in lo until hi) {
        ninther(v, isLess, a, i - frac, b, a + 1, i, b + 1, a + 2, i + frac, b + 2)
        a += 3
        b += 3
    }
    medianOfMedians(v.subList(lo, lo + frac), isLess, pivot)
    return partition(v, lo + pivot, isLess)
}

/**
 * Moves around the 9 elements at the indices a..i, such that
 * `v[d]` contains the median of the 9 elements and the other
 * elements are partitioned around it.
 */
public fun <T> ninther(v: MutableList<T>, isLess: (T, T) -> Boolean, a: Int, b: Int, c: Int, d: Int, e: Int, f: Int, g: Int, h: Int, i: Int) {
    var b1 = b
    var d1 = d
    var h1 = h
    b1 = medianIdx(v, isLess, a, b1, c)
    h1 = medianIdx(v, isLess, g, h1, i)
    if (isLess(v[h1], v[b1])) {
        swap(b1, h1)
    }
    if (isLess(v[f], v[d1])) {
        swap(d1, f)
    }
    if (isLess(v[e], v[d1])) {
        // do nothing
    } else if (isLess(v[f], v[e])) {
        d1 = f
    } else {
        if (isLess(v[e], v[b1])) {
            v.swap(e, b1)
        } else if (isLess(v[h1], v[e])) {
            v.swap(e, h1)
        }
        return
    }
    if (isLess(v[d1], v[b1])) {
        d1 = b1
    } else if (isLess(v[h1], v[d1])) {
        d1 = h1
    }
    v.swap(d1, e)
}

/**
 * returns the index pointing to the median of the 3
 * elements `v[a]`, `v[b]` and `v[c]`
 */
public fun <T> medianIdx(v: List<T>, isLess: (T, T) -> Boolean, a: Int, b: Int, c: Int): Int {
    if (isLess(v[c], v[a])) {
        swap(a, c)
    }
    if (isLess(v[c], v[b])) {
        return c
    }
    if (isLess(v[b], v[a])) {
        return a
    }
    return b
}
