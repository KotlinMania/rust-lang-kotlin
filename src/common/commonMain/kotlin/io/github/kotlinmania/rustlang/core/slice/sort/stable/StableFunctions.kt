package io.github.kotlinmania.rustlang.core.slice.sort.stable

import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.slice.sort.shared.smallsort.SMALL_SORT_GENERAL_SCRATCH_LEN
import io.github.kotlinmania.rustlang.core.slice.sort.stable.tiny.mergesort

/**
 * Stable sort called driftsort by Orson Peters and Lukas Bergdoll.
 * Design document:
 * <https://github.com/Voultapher/sort-research-rs/blob/main/writeup/driftsortIntroduction/text.md>
 *
 * Upholds all safety properties outlined here:
 * <https://github.com/Voultapher/sort-research-rs/blob/main/writeup/sortSafety/text.md>
 */
public fun <T, BufT : BufGuard<T, BufT>> sort(v: MutableList<T>, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), withCapacityBufTFn: (Int) -> BufT) {
    // Arrays of zero-sized types are always all-equal, and thus sorted.
    if (((typeLayoutT).sizeBytes == 0L)) {
        return
    }
    // Instrumenting the standard library showed that 90+% of the calls to sort
    // by rustc are either of size 0 or 1.
    val len = v.size
    if (len < 2) {
        return
    }
    // Unlike driftsort, mergesort only requires len / 2,
    // not len - len / 2.
    val allocLen = len / 2
    // For small inputs 4KiB of stack storage suffices, which allows us to avoid
    // calling the (de-)allocator. Benchmarks showed this was quite beneficial.
    val stackBuf = AlignedStorage.new()
    val stackScratch = stackBuf.asUninitSliceMut()
    var heapBuf: Any?
    val scratch =
        if (stackScratch.len() >= allocLen) {
            stackScratch
        } else {
            heapBuf = withCapacityBufTFn(allocLen)
            heapBuf.asUninitSliceMut()
        }
    mergesort(v, scratch, isLess)
}

/**
 * See [sort]
 *
 * Deliberately don't inline the main sorting routine entrypoint to ensure the
 * inlined insertion sort i-cache footprint remains minimal.
 */
public fun <T, BufT : BufGuard<T, BufT>> driftsortMain(v: MutableList<T>, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), smallSortThresholdTFn: () -> T, withCapacityBufTFn: (Int) -> BufT) {
    // By allocating n elements of memory we can ensure the entire input can
    // be sorted using stable quicksort, which allows better performance on
    // random and low-cardinality distributions. However, we still want to
    // reduce our memory usage to n - n / 2 for large inputs. We do this by scaling
    // our allocation as max(n - n / 2, min(n, 8MB)), ensuring we scale like n for
    // small inputs and n - n / 2 for large inputs, without a sudden drop off. We
    // also need to ensure our alloc >= SMALL_SORT_GENERAL_SCRATCH_LEN, as the
    // small-sort always needs this much memory.
    //
    // driftsort will produce unsorted runs of up to minGoodRunLen, which
    // is at most len - len / 2.
    // Unsorted runs need to be processed by quicksort, which requires as much
    // scratch space as the run length, therefore the scratch space must be at
    // least len - len / 2.
    // If minGoodRunLen is ever modified, this code must be updated to allocate
    // the correct scratch size for it.
    val MAX_FULL_ALLOC_BYTES: Int = 8000000
    // 8MB
    val maxFullAlloc = MAX_FULL_ALLOC_BYTES / sizeOf<T>(typeLayoutT = typeLayoutT)
    val len = v.size
    val allocLen = cmp.max(cmp.max(len - len / 2, minOf(len, maxFullAlloc)), SMALL_SORT_GENERAL_SCRATCH_LEN)
    // For small inputs 4KiB of stack storage suffices, which allows us to avoid
    // calling the (de-)allocator. Benchmarks showed this was quite beneficial.
    val stackBuf = AlignedStorage.new()
    val stackScratch = stackBuf.asUninitSliceMut()
    var heapBuf: Any?
    val scratch =
        if (stackScratch.len() >= allocLen) {
            stackScratch
        } else {
            heapBuf = withCapacityBufTFn(allocLen)
            heapBuf.asUninitSliceMut()
        }
    // For small inputs using quicksort is not yet beneficial, and a single
    // small-sort or two small-sorts plus a single merge outperforms it, so use
    // eager mode.
    val eagerSort = len <= smallSortThresholdTFn() * 2
    io.github.kotlinmania.rustlang.core.slice.sort.stable.drift.sort(v, scratch, eagerSort, isLess)
}
