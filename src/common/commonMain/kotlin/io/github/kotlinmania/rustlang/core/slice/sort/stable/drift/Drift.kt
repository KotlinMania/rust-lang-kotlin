// port-lint: source slice/sort/stable/drift.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.slice.sort.stable.drift

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.rustLeadingZeros
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.slice.sort.shared.findExistingRun
import io.github.kotlinmania.rustlang.slice.sort.stable.merge.merge
import io.github.kotlinmania.rustlang.slice.sort.stable.quicksort.quicksort

// This module contains the hybrid top-level loop combining bottom-up Mergesort with top-down
// Quicksort.

/**
 * Sorts `v` based on comparison function `isLess`. If `eagerSort` is true,
 * it will only do small-sorts and physical merges, ensuring O(N * log(N))
 * worst-case complexity. `scratch.len()` must be at least
 * `max(v.len() - v.len() / 2, SMALL_SORT_GENERAL_SCRATCH_LEN)` otherwise the implementation may abort.
 * Fully ascending and descending inputs will be sorted with exactly N - 1
 * comparisons.
 *
 * This is the main loop for driftsort, which uses powersort's heuristic to
 * determine in which order to merge runs, see below for details.
 */
public fun <T> sort(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, eagerSort: Boolean, isLess: (T, T) -> Boolean) {
    // It's important to have a relatively high entry barrier for pre-sorted
    // runs, as the presence of a single such run will force on average several
    // merge operations and shrink the maximum quicksort size a lot. For that
    // reason we use sqrt(len) as our pre-sorted run threshold.
    val MIN_SQRT_RUN_LEN: Int = 64
    val len = v.size
    if (len < 2) {
        return
        // Removing this length check *increases* code size.
    }
    val scaleFactor = mergeTreeScaleFactor(len)
    val minGoodRunLen =
        if (len <= (MIN_SQRT_RUN_LEN * MIN_SQRT_RUN_LEN)) {
            // For small input length `MIN_SQRT_RUN_LEN` would break pattern
            // detection of full or nearly sorted inputs.
            minOf(len - len / 2, MIN_SQRT_RUN_LEN)
        } else {
            sqrtApprox(len)
        }
    // (stackLen, runs, desiredDepths) together form a stack maintaining run
    // information for the powersort heuristic. desiredDepths[i] is the desired
    // depth of the merge node that merges runs[i] with the run that comes after
    // it.
    var stackLen = 0
    val runStorage = MaybeUninit.uninit(ObjectSlotLayout<List<DriftsortRun>>())
    val runs: MutPtr = runStorage.asMutPtr().cast()
    val desiredDepthStorage = MaybeUninit.uninit(ObjectSlotLayout<UByteArray>())
    val desiredDepths: MutPtr = desiredDepthStorage.asMutPtr().cast()
    var scanIdx = 0
    var prevRun = DriftsortRun.newSorted(0)
    // Initial dummy run.
    while (true) {
        // Compute the next run and the desired depth of the merge node between
        // prevRun and nextRun. On the last iteration we create a dummy run
        // with root-level desired depth to fully collapse the merge tree.
        val (nextRun, desiredDepth)
        if (scanIdx < len) {
            nextRun = createRun(v.subList(scanIdx, v.size), scratch, minGoodRunLen, eagerSort, isLess)
            desiredDepth = mergeTreeDepth(scanIdx - prevRun.len(), scanIdx, scanIdx + nextRun.len(), scaleFactor)
        } else {
            nextRun = DriftsortRun.newSorted(0)
            desiredDepth = 0
        }
        // Process the merge nodes between earlier runs[i] that have a desire to
        // be deeper in the merge tree than the merge node for the splitpoint
        // between prevRun and nextRun.
        //
        // SAFETY: first note that this is the only place we modify stackLen,
        // runs or desired depths. We maintain the following invariants:
        // 1. The first stackLen elements of runs/desiredDepths are initialized.
        // 2. For all valid i > 0, desiredDepths[i] < desiredDepths[i+1].
        // 3. The sum of all valid runs[i].len() plus prevRun.len() equals
        // scanIdx.
        run {
            while (stackLen > 1 && (PrimitiveLayouts.U8).readNative((MutPtr((desiredDepths).rawAddress + ((stackLen - 1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress) >= desiredDepth) {
                // Desired depth greater than the upcoming desired depth, pop
                // left neighbor run from stack and merge into prevRun.
                val left = (DriftsortRun.typeLayout()).readNative((MutPtr((runs).rawAddress + ((stackLen - 1).toLong() * (DriftsortRun.typeLayout()).strideBytes))).rawAddress)
                val mergedLen = left.len() + prevRun.len()
                val mergeStartIdx = scanIdx - mergedLen
                val mergeSlice = v.getUncheckedMut(mergeStartIdx until scanIdx)
                prevRun = logicalMerge(mergeSlice, scratch, left, prevRun, isLess)
                stackLen -= 1
            }
            // We now know that desiredDepths[stackLen - 1] < desiredDepth,
            // maintaining our invariant. This also guarantees we don't overflow
            // the stack as mergeTreeDepth(..) <= 64 and thus we can only have
            // 64 distinct values on the stack before pushing, plus our initial
            // dummy run, while our capacity is 66.
            (DriftsortRun.typeLayout()).writeNative((MutPtr((runs).rawAddress + ((stackLen).toLong() * (DriftsortRun.typeLayout()).strideBytes))).rawAddress, prevRun)
            (PrimitiveLayouts.U8).writeNative((MutPtr((desiredDepths).rawAddress + ((stackLen).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, desiredDepth)
            stackLen += 1
        }
        // Break before overriding the last run with our dummy run.
        if (scanIdx >= len) {
            break
        }
        scanIdx += nextRun.len()
        prevRun = nextRun
    }
    if (!prevRun.sorted()) {
        stableQuicksort(v, scratch, isLess)
    }
}

// Nearly-Optimal Mergesorts: Fast, Practical Sorting Methods That Optimally
// Adapt to Existing Runs by J. Ian Munro and Sebastian Wild.
//
// This method forms a binary merge tree, where each internal node corresponds
// to a splitting point between the adjacent runs that have to be merged. If we
// visualize our array as the number line from 0 to 1, we want to find the
// dyadic fraction with smallest denominator that lies between the midpoints of
// our to-be-merged slices. The exponent in the dyadic fraction indicates the
// desired depth in the binary merge tree this internal node wishes to have.
// This does not always correspond to the actual depth due to the inherent
// imbalance in runs, but we follow it as closely as possible.
//
// As an optimization we rescale the number line from [0, 1) to [0, 2^62). Then
// finding the simplest dyadic fraction between midpoints corresponds to finding
// the most significant bit difference of the midpoints. We save scaleFactor =
// ceil(2^62 / n) to perform this rescaling using a multiplication, avoiding
// having to repeatedly do integer divides. This rescaling isn't exact when n is
// not a power of two since we use integers and not reals, but the result is
// very close, and in fact when n < 2^30 the resulting tree is equivalent as the
// approximation errors stay entirely in the lower order bits.
//
// Thus for the splitting point between two adjacent slices [a, b) and [b, c)
// the desired depth of the corresponding merge node is CLZ((a+b)*f ^ (b+c)*f),
// where CLZ counts the number of leading zeros in an integer and f is our scale
// factor. Note that we omitted the division by two in the midpoint
// calculations, as this simply shifts the bits by one position (and thus always
// adds one to the result), and we only care about the relative depths.
//
// Finally, if we try to upper bound x = (a+b)*f giving x = (n-1 + n) * ceil(2^62 / n) then
// x < (2^62 / n + 1) * 2n
// x < 2^63 + 2n
// So as long as n < 2^62 we find that x < 2^64, meaning our operations do not
// overflow.
public fun mergeTreeScaleFactor(n: Int): ULong {
    if (32u > 64u) {
        error("Platform not supported")
    }
    return (1uL shl 62).divCeil(n.toULong())
}

// Note: mergeTreeDepth output is < 64 when left < right as f*x and f*y must
// differ in some bit, and is <= 64 always.
public fun mergeTreeDepth(left: Int, mid: Int, right: Int, scaleFactor: ULong): UByte {
    val x = left.toULong() + mid.toULong()
    val y = mid.toULong() + right.toULong()
    return rustLeadingZeros(((scaleFactor * x) xor (scaleFactor * y))).toUByte()
}

public fun sqrtApprox(n: Int): Int {
    // Note that sqrt(n) = n^(1/2), and that 2^log2(n) = n. We combine these
    // two facts to approximate sqrt(n) as 2^(log2(n) / 2). Because our integer
    // log floors we want to add 0.5 to compensate for this on average, so our
    // initial approximation is 2^((1 + floor(log2(n))) / 2).
    //
    // We then apply an iteration of Newton's method to improve our
    // approximation, which for sqrt(n) is a1 = (a0 + n / a0) / 2.
    //
    // Finally we note that the exponentiation / division can be done directly
    // with shifts. We OR with 1 to avoid zero-checks in the integer log.
    val ilog = (n or 1).ilog2()
    val shift = ilog.divCeil(2)
    return ((1 shl shift.toInt()) + (n shr shift.toInt())) / 2
}

// Lazy logical runs as in Glidesort.
public fun <T> logicalMerge(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, left: DriftsortRun, right: DriftsortRun, isLess: (T, T) -> Boolean): DriftsortRun {
    // If one or both of the runs are sorted do a physical merge, using
    // quicksort to sort the unsorted run if present. We also *need* to
    // physically merge if the combined runs would not fit in the scratch space
    // anymore (as this would mean we are no longer able to quicksort them).
    val len = v.size
    val canFitInScratch = len <= scratch.size
    return if (!canFitInScratch || left.sorted() || right.sorted()) {
        if (!left.sorted()) {
            stableQuicksort(v.subList(0, left.len()), scratch, isLess)
        }
        if (!right.sorted()) {
            stableQuicksort(v.subList(left.len(), v.size), scratch, isLess)
        }
        merge(v, scratch, left.len(), isLess)
        DriftsortRun.newSorted(len)
    } else {
        DriftsortRun.newUnsorted(len)
    }
}

/**
 * Creates a new logical run.
 *
 * A logical run can either be sorted or unsorted. If there is a pre-existing
 * run that clears the `minGoodRunLen` threshold it is returned as a sorted
 * run. If not, the result depends on the value of `eagerSort`. If it is true,
 * then a sorted run of length `T.SMALL_SORT_THRESHOLD` is returned, and if it
 * is false an unsorted run of length `minGoodRunLen` is returned.
 */
public fun <T> createRun(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, minGoodRunLen: Int, eagerSort: Boolean, isLess: (T, T) -> Boolean, smallSortThresholdTFn: () -> T): DriftsortRun {
    val len = v.size
    if (len >= minGoodRunLen) {
        val (runLen, wasReversed) = findExistingRun(v, isLess)
        // SAFETY: findExistingRun promises to return a valid runLen.
        if (runLen >= minGoodRunLen) {
            if (wasReversed) {
                ((v).subList(0, runLen)).reverse()
            }
            return DriftsortRun.newSorted(runLen)
        }
    }
    return if (eagerSort) {
        // We call quicksort with a len that will immediately call small-sort.
        // By not calling the small-sort directly here it can always be inlined into
        // the quicksort itself, making the recursive base case faster and is generally
        // more binary-size efficient.
        val eagerRunLen = minOf(smallSortThresholdTFn(), len)
        quicksort(v.subList(0, eagerRunLen), scratch, 0u, null, isLess)
        DriftsortRun.newSorted(eagerRunLen)
    } else {
        DriftsortRun.newUnsorted(minOf(minGoodRunLen, len))
    }
}

public fun <T> stableQuicksort(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, isLess: (T, T) -> Boolean) {
    // Limit the number of imbalanced partitions to `2 * floor(log2(len))`.
    // The binary OR by one is used to eliminate the zero-check in the logarithm.
    val limit = 2 * (v.size or 1).ilog2()
    quicksort(v, scratch, limit, null, isLess)
}

/**
 * Compactly stores the length of a run, and whether or not it is sorted. This
 * can always fit in a `Int` because the maximum slice length is [isize.Max].
 */
public open class DriftsortRun(
    public var value: Int,
) {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): DriftsortRun = DriftsortRun(value)

    public open fun sorted(): Boolean = this.value and 1 == 1

    public open fun len(): Int = this.value shr 1

    public companion object {
        public fun typeLayout(): TypeLayout<DriftsortRun> =
            object : TypeLayout<DriftsortRun> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): DriftsortRun =
                    DriftsortRun(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DriftsortRun) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DriftsortRun =
                    DriftsortRun(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DriftsortRun) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.value)
                }
            }

        public fun newSorted(length: Int): DriftsortRun = DriftsortRun((length shl 1) or 1)

        public fun newUnsorted(length: Int): DriftsortRun = DriftsortRun(length shl 1)
    }
}
