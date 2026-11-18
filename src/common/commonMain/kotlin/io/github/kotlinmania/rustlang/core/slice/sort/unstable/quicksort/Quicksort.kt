// port-lint: source slice/sort/unstable/quicksort.rs
package io.github.kotlinmania.rustlang.core.slice.sort.unstable.quicksort

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.sort.shared.pivot.choosePivot
import io.github.kotlinmania.rustlang.core.slice.sort.unstable.heapsort.heapsort
import io.github.kotlinmania.rustlang.core.slice.splitAtMut

// This module contains an unstable quicksort and two partition implementations.

/**
 * Sorts `v` recursively.
 *
 * If the slice had a predecessor in the original array, it is specified as `ancestorPivot`.
 *
 * `limit` is the number of allowed imbalanced partitions before switching to `heapsort`. If zero,
 * this function will immediately switch to heapsort.
 */
public fun <T> quicksort(v: MutableList<T>, ancestorPivot: T?, limit: UInt, isLess: (T, T) -> Boolean, smallSortThresholdTFn: () -> T, smallSortTFn: (MutableList<T>, (T, T) -> Boolean) -> Unit) {
    var v1 = v
    var ancestorPivot1 = ancestorPivot
    var limit1 = limit
    while (true) {
        if (v1.size <= smallSortThresholdTFn()) {
            smallSortTFn(v1, isLess)
            return
        }
        // If too many bad pivot choices were made, simply fall back to heapsort in order to
        // guarantee `O(N x log(N))` worst-case.
        if (limit1 == 0u) {
            heapsort(v1, isLess)
            return
        }
        limit1 -= 1u
        // Choose a pivot and try guessing whether the slice is already sorted.
        val pivotPos = choosePivot(v1, isLess)
        // If the chosen pivot is equal to the predecessor, then it's the smallest element in the
        // slice. Partition the slice into elements equal to and elements greater than the pivot.
        // This case is usually hit when the slice contains many duplicate elements.
        if (ancestorPivot1 != null) {
            val p = ancestorPivot1
            if (!isLess(p, v1[pivotPos])) {
                val numLt = partition(v1, pivotPos, { a, b -> !isLess(b, a) })
                // Continue sorting elements greater than the pivot. We know that `numLt` contains
                // the pivot. So we can continue after `numLt`.
                v1 = v1.subList((numLt + 1), v1.size)
                ancestorPivot1 = null
                continue
            }
        }
        // Partition the slice.
        val numLt1 = partition(v1, pivotPos, isLess)
        // SAFETY: partition ensures that `numLt` will be in-bounds.
        // Split the slice into `left`, `pivot`, and `right`.
        val (left, right) = v1.splitAtMut(numLt1)
        val (pivot, right1) = right.splitAtMut(1)
        val pivot1 = pivot[0]
        // Recurse into the left side. We have a fixed recursion limit, testing shows no real
        // benefit for recursing into the shorter side.
        quicksort(left, ancestorPivot1, limit1, isLess)
        // Continue with the right side.
        v1 = right1
        ancestorPivot1 = pivot1
    }
}

/**
 * Takes the input slice `v` and re-arranges elements such that when the call returns normally
 * all elements that compare true for `isLess(elem, pivot)` where `pivot == v[pivotPos]` are
 * on the left side of `v` followed by the other elements, notionally considered greater or
 * equal to `pivot`.
 *
 * Returns the number of elements that are compared true for `isLess(elem, pivot)`.
 *
 * If `isLess` does not implement a total order the resulting order and return value are
 * unspecified. All original elements will remain in `v` and any possible modifications via
 * interior mutability will be observable. Same is true if `isLess` panics or `v.len()`
 * exceeds `scratch.len()`.
 */
public fun <T> partition(v: MutableList<T>, pivot: Int, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    val len = v.size
    // Allows for panic-free code-gen by proving this property to the compiler.
    if (len == 0) {
        return 0
    }
    if (pivot >= len) {
        kmAbort()
    }
    // SAFETY: We checked that `pivot` is in-bounds.
    run {
        // Place the pivot at the beginning of slice.
        v.swapUnchecked(0, pivot)
    }
    val (pivot1, vWithoutPivot) = v.splitAtMut(1)
    // Assuming that Rust generates noalias LLVM IR we can be sure that a partition function
    // signature of the form `(v: [T], pivot: T)` guarantees that pivot and v can't alias.
    // Having this guarantee is crucial for optimizations. It's possible to copy the pivot value
    // into a stack value, but this creates issues for types with interior mutability mandating
    // a drop guard.
    val pivot2 = pivot1[0]
    // This construct is used to limit the LLVM IR generated, which saves large amounts of
    // compile-time by only instantiating the code that is needed. Idea by Frank Steffahn.
    val numLt = (instPartition<T, (T, T) -> Boolean>(typeLayoutT = typeLayoutT))(vWithoutPivot, pivot2, isLess)
    if (numLt >= len) {
        kmAbort()
    }
    // SAFETY: We checked that `numLt` is in-bounds.
    run {
        // Place the pivot between the two partitions.
        v.swapUnchecked(0, numLt)
    }
    return numLt
}

public fun <T> instPartition(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): (MutableList<T>, T, (T, T) -> Boolean) -> Int {
    val MAX_BRANCHLESS_PARTITION_SIZE: Int = 96
    return if (sizeOf<T>(typeLayoutT = typeLayoutT) <= MAX_BRANCHLESS_PARTITION_SIZE) {
        // Specialize for types that are relatively cheap to copy, where branchless optimizations
        // have large leverage e.g. `ULong` and `String`.
        cfgSelect(feature, "optimize_for_size", partitionLomutoBranchlessSimple, T, F, partitionLomutoBranchlessCyclic, T, F)
    } else {
        partitionHoareBranchyCyclic<T, (T, T) -> Boolean>
    }
}

/**
 * See [partition].
 */
public fun <T> partitionHoareBranchyCyclic(v: MutableList<T>, pivot: T, isLess: (T, T) -> Boolean): Int {
    val len = v.size
    if (len == 0) {
        return 0
    }
    // Optimized for large types that are expensive to move. Not optimized for integers. Optimized
    // for small code-gen, assuming that isLess is an expensive operation that generates
    // substantial amounts of code or a call. And that copying elements will likely be a call to
    // memcpy. Using 2 `ptr.copyNonoverlapping` has the chance to be faster than
    // `ptr.swapNonoverlapping` because `memcpy` can use wide SIMD based on runtime feature
    // detection. Benchmarks support this analysis.
    var gapOpt: GapGuard<T>? = null
    // SAFETY: The left-to-right scanning loop performs a bounds check, where we know that `left >=
    // vBase && left < right && right <= vBase.add(len)`. The right-to-left scanning loop performs
    // a bounds check ensuring that `right` is in-bounds. We checked that `len` is more than zero,
    // which means that unconditional `right = right.sub(1)` is safe to do. The exit check makes
    // sure that `left` and `right` never alias, making `ptr.copyNonoverlapping` safe. The
    // drop-guard `gap` ensures that should `isLess` panic we always overwrite the duplicate in the
    // input. `gap.pos` stores the previous value of `right` and starts at `right` and so it too is
    // in-bounds. We never pass the saved `gap.value` to `isLess` while it is inside the `GapGuard`
    // thus any changes via interior mutability will be observed.
    return run {
        val vBase = v.asMutPtr()
        var left = vBase
        var right = MutPtr((vBase).rawAddress + ((len).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        while (true) {
            // Find the first element greater than the pivot.
            while (((left).rawAddress.toULong() < (right).rawAddress.toULong()) && isLess((UnresolvedTypeLayout<T>()).readNative((left).rawAddress), pivot)) {
                left = MutPtr((left).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            }
            // Find the last element equal to the pivot.
            while (true) {
                right = MutPtr((right).rawAddress - ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
                if (((left).rawAddress.toULong() >= (right).rawAddress.toULong()) || isLess((UnresolvedTypeLayout<T>()).readNative((right).rawAddress), pivot)) {
                    break
                }
            }
            if (((left).rawAddress.toULong() >= (right).rawAddress.toULong())) {
                break
            }
            // Swap the found pair of out-of-order elements via cyclic permutation.
            val isFirstSwapPair = gapOpt == null
            if (isFirstSwapPair) {
                gapOpt = GapGuard(pos = right, value = read((left).asConstPtr(), typeLayoutT = UnresolvedTypeLayout<T>()))
            }
            val gap = gapOpt.unwrapUnchecked()
            // Single place where we instantiate ptr.copyNonoverlapping in the partition.
            if (!isFirstSwapPair) {
                copyNonoverlapping((left).asConstPtr(), gap.pos, 1, typeLayoutT = UnresolvedTypeLayout<T>())
            }
            gap.pos = right
            copyNonoverlapping((right).asConstPtr(), left, 1, typeLayoutT = UnresolvedTypeLayout<T>())
            left = MutPtr((left).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        }
        left.offsetFromUnsigned((vBase).asConstPtr())
        // `gapOpt` goes out of scope and overwrites the last wrong-side element on the right side
        // with the first wrong-side element of the left side that was initially overwritten by the
        // first wrong-side element on the right side element.
    }
}

public open class PartitionState<T>(
    // The current element that is being looked at, scans left to right through slice.
    public var right: MutPtr,
    // Counts the number of elements that compared less-than, also works around:
    // https://github.com/rust-lang/rust/issues/117128
    public var numLt: Int,
    // Gap guard that tracks the temporary duplicate in the input.
    public var gap: GapGuardRaw<T>,
)

public fun <T> partitionLomutoBranchlessCyclic(v: MutableList<T>, pivot: T, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // Novel partition implementation by Lukas Bergdoll and Orson Peters. Branchless Lomuto
    // partition paired with a cyclic permutation.
    // https://github.com/Voultapher/sort-research-rs/blob/main/writeup/lomcycPartition/text.md
    val len = v.size
    val vBase = v.asMutPtr()
    if (len == 0) {
        return 0
    }
    // SAFETY: We checked that `len` is more than zero, which means that reading `vBase` is safe to
    // do. From there we have a bounded loop where `vBase.add(i)` is guaranteed in-bounds. `v` and
    // `pivot` can't alias because of type system rules. The drop-guard `gap` ensures that should
    // `isLess` panic we always overwrite the duplicate in the input. `gap.pos` stores the previous
    // value of `right` and starts at `vBase` and so it too is in-bounds. Given `UNROLL_LEN == 2`
    // after the main loop we either have A) the last element in `v` that has not yet been processed
    // because `len % 2 != 0`, or B) all elements have been processed except the gap value that was
    // saved at the beginning with `ptr.read(vBase)`. In the case A) the loop will iterate twice,
    // first performing loopBody to take care of the last element that didn't fit into the unroll.
    // After that the behavior is the same as for B) where we use the saved value as `right` to
    // overwrite the duplicate. If this very last call to `isLess` panics the saved value will be
    // copied back including all possible changes via interior mutability. If `isLess` does not
    // panic and the code continues we overwrite the duplicate and do `right = right.add(1)`, this
    // is safe to do with `* gap.value` because `T` is the same as `[T; 1]` and generating a
    // pointer one past the allocation is safe.
    return run {
        var loopBody =
            { state: PartitionState<T> ->
                val rightIsLt = isLess(state.right, pivot)
                val left = MutPtr((vBase).rawAddress + ((state.numLt).toLong() * (typeLayoutT).strideBytes))
                copy((left).asConstPtr(), state.gap.pos, 1, typeLayoutT = typeLayoutT)
                copyNonoverlapping(state.right, left, 1)
                state.gap.pos = state.right
                state.numLt += rightIsLt.toInt()
                state.right = state.right.add(1)
            }
        // Ideally we could just use GapGuard in PartitionState, but the reference that is
        // materialized with `state` when calling `loopBody` would create a mutable reference
        // to the parent struct that contains the gap value, invalidating the reference pointer
        // created from a reference to the gap value in the cleanup loop. This is only an issue
        // under Stacked Borrows, Tree Borrows accepts the intuitive code using GapGuard as valid.
        val gapValue = read((vBase).asConstPtr(), typeLayoutT = typeLayoutT)
        val state = PartitionState(numLt = 0, right = MutPtr((vBase).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes)), gap = GapGuardRaw(pos = vBase, value = gapValue))
        // Manual unrolling that works well on x86, Arm and with opt-level=s without murdering
        // compile-times. Leaving this to the compiler yields ok to bad results.
        val unrollLen =
            if (sizeOf<T>(typeLayoutT = typeLayoutT) <= 16) {
                2
            } else {
                1
            }
        val unrollEnd = MutPtr((vBase).rawAddress + ((len - (unrollLen - 1)).toLong() * (typeLayoutT).strideBytes))
        while (((state.right).rawAddress.toULong() < (unrollEnd).rawAddress.toULong())) {
            if (unrollLen == 2) {
                loopBody(state)
                loopBody(state)
            } else {
                loopBody(state)
            }
        }
        // Single instantiate `loopBody` for both the unroll cleanup and cyclic permutation
        // cleanup. Optimizes binary-size and compile-time.
        val end = MutPtr((vBase).rawAddress + ((len).toLong() * (typeLayoutT).strideBytes))
        while (true) {
            val isDone = ((state.right).rawAddress.toULong() == (end).rawAddress.toULong())
            state.right = if (isDone) {
                state.gap.value
            } else {
                state.right
            }
            loopBody(state)
            if (isDone) {
                forget(state.gap)
                break
            }
        }
        state.numLt
    }
}

public fun <T> partitionLomutoBranchlessSimple(v: MutableList<T>, pivot: T, isLess: (T, T) -> Boolean): Int {
    var left = 0
    for (right in 0 until v.size) {
        // SAFETY: `left` can at max be incremented by 1 each loop iteration, which implies that
        // left <= right and that both are in-bounds.
        run {
            val rightIsLt = isLess(v.getUnchecked(right), pivot)
            v.swapUnchecked(left, right)
            left += rightIsLt.toInt()
        }
    }
    return left
}

public open class GapGuard<T>(
    public var pos: MutPtr,
    public var value: T,
) {
    public open fun drop() {
        // SAFETY: `self` MUST be constructed in a way that makes copying the gap value into
        // `self.pos` sound.
        run {
            copyNonoverlapping(this.value, this.pos, 1)
        }
    }
}

/**
 * Ideally this wouldn't be needed and we could just use the regular GapGuard.
 * See comment in [partitionLomutoBranchlessCyclic].
 */
public open class GapGuardRaw<T>(
    public var pos: MutPtr,
    public var value: MutPtr,
) {
    public open fun drop() {
        // SAFETY: `self` MUST be constructed in a way that makes copying the gap value into
        // `self.pos` sound.
        run {
            copyNonoverlapping((this.value).asConstPtr(), this.pos, 1)
        }
    }

    public companion object {
        public fun <T> typeLayout(): TypeLayout<GapGuardRaw<T>> =
            object : TypeLayout<GapGuardRaw<T>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): GapGuardRaw<T> =
                    GapGuardRaw(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: GapGuardRaw<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.pos)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GapGuardRaw<T> =
                    GapGuardRaw(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GapGuardRaw<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.pos)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.value)
                }
            }
    }
}
