package io.github.kotlinmania.rustlang.core.slice.sort.unstable

import io.github.kotlinmania.rustlang.core.cfgSelect
import io.github.kotlinmania.rustlang.core.ops.RangeBounds
import io.github.kotlinmania.rustlang.core.slice.sort.select.partitionAtIndex
import io.github.kotlinmania.rustlang.core.slice.sort.shared.findExistingRun
import io.github.kotlinmania.rustlang.core.slice.sort.shared.smallsort.insertionSortShiftLeft
import io.github.kotlinmania.rustlang.core.slice.sort.unstable.quicksort.quicksort

/**
 * Unstable sort called ipnsort by Lukas Bergdoll and Orson Peters.
 * Design document:
 * <https://github.com/Voultapher/sort-research-rs/blob/main/writeup/ipnsortIntroduction/text.md>
 *
 * Upholds all safety properties outlined here:
 * <https://github.com/Voultapher/sort-research-rs/blob/main/writeup/sortSafety/text.md>
 */
public fun <T> sort(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    // Arrays of zero-sized types are always all-equal, and thus sorted.
    if (T.IS_ZST) {
        return
    }
    // Instrumenting the standard library showed that 90+% of the calls to sort
    // by rustc are either of size 0 or 1.
    val len = v.size
    if (len < 2) {
        return
    }
    cfgSelect(any, feature, "optimize_for_size", targetPointerWidth, "16", heapsort, heapsort, v, isLess, MAX_LEN_ALWAYS_INSERTION_SORT, 20, intrinsics, likely, len, MAX_LEN_ALWAYS_INSERTION_SORT, insertionSortShiftLeft, v, 1, isLess, ipnsort, v, isLess)
}

/**
 * Unstable partial sort the range `start .. end`, after which it's guaranteed that:
 *
 * 1. Every element in `v[.. start]` is smaller than or equal to
 * 2. Every element in `v[start .. end]`, which is sorted, and smaller than or equal to
 * 3. Every element in `v[end ..]`.
 */
public fun <T, R> partialSort(v: MutableList<T>, range: R, isLess: (T, T) -> Boolean) where R : RangeBounds<Int<R>> {
    val v1 = v
    // A heuristic factor to decide whether to partition the slice or not.
    // If the range bound is close to the edges of the slice, it's not worth
    // partitioning first.
    val PARTITION_THRESHOLD: Int = 8
    // Arrays of zero-sized types are always all-equal, and thus sorted.
    if (T.IS_ZST) {
        return
    }
    val len = v1.size
    val structVal = range(range, 0 until len)
    val start = structVal.start
    val end = structVal.end
    if (end - start <= 1) {
        // Empty range or single element. This case can be resolved in at most
        // single partitionAtIndex call, without further sorting.
        if (end == 0 || start == len) {
            // Do nothing if it is an empty range at start or end: all guarantees
            // are already upheld.
            return
        }
        partitionAtIndex(v1, start, isLess)
        return
    }
    var v2 = v1
    if (end + PARTITION_THRESHOLD <= len) {
        v2 = partitionAtIndex(v2, end - 1, isLess).first
    }
    if (start >= PARTITION_THRESHOLD) {
        v2 = partitionAtIndex(v2, start, isLess).third
    }
    sort(v2, isLess)
}

/**
 * See [sort]
 *
 * Deliberately don't inline the main sorting routine entrypoint to ensure the
 * inlined insertion sort i-cache footprint remains minimal.
 */
public fun <T> ipnsort(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    val len = v.size
    val (runLen, wasReversed) = findExistingRun(v, isLess)
    // SAFETY: findExistingRun promises to return a valid runLen.
    if (runLen == len) {
        if (wasReversed) {
            v.reverse()
        }
        // It would be possible to a do in-place merging here for a long existing streak. But that
        // makes the implementation a lot bigger, users can use `slice.sort` for that use-case.
        return
    }
    // Limit the number of imbalanced partitions to `2 * floor(log2(len))`.
    // The binary OR by one is used to eliminate the zero-check in the logarithm.
    val limit = 2 * (len or 1).ilog2()
    quicksort(v, null, limit, isLess)
}
