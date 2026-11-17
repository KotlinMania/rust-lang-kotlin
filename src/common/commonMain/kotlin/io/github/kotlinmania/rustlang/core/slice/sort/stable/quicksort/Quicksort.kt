// port-lint: source slice/sort/stable/quicksort.rs
package io.github.kotlinmania.rustlang.core.slice.sort.stable.quicksort

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castInit
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.sort.shared.pivot.choosePivot
import io.github.kotlinmania.rustlang.core.slice.sort.stable.drift.sort
import io.github.kotlinmania.rustlang.core.slice.splitAtMut

// This module contains a stable quicksort and partition implementation.

/**
 * Sorts `v` recursively using quicksort.
 * `scratch.len()` must be at least `max(v.len() - v.len() / 2, SMALL_SORT_GENERAL_SCRATCH_LEN)`
 * otherwise the implementation may abort.
 *
 * `limit` when initialized with `c * log(v.len())` for some c ensures we do not
 * overflow the stack or go quadratic.
 */
public fun <T> quicksort(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, limit: UInt, leftAncestorPivot: T?, isLess: (T, T) -> Boolean, smallSortThresholdTFn: () -> T, smallSortTFn: (MutableList<T>, MutableList<MaybeUninit<T>>, (T, T) -> Boolean) -> Unit) {
    var v1 = v
    var limit1 = limit
    var leftAncestorPivot1 = leftAncestorPivot
    while (true) {
        val len = v1.size
        if (len <= smallSortThresholdTFn()) {
            smallSortTFn(v1, scratch, isLess)
            return
        }
        if (limit1 == 0u) {
            // We have had too many bad pivots, switch to O(n log n) fallback
            // algorithm. In our case that is driftsort in eager mode.
            sort(v1, scratch, true, isLess)
            return
        }
        limit1 -= 1u
        val pivotPos = choosePivot(v1, isLess)
        // SAFETY: We only access the temporary copy for Freeze types, otherwise
        // self-modifications via `isLess` would not be observed and this would
        // be unsound. Our temporary copy does not escape this scope.
        // We use `MaybeUninit` to avoid re-tag issues. Note: use `MaybeDangling`.
        val pivotCopy = read(ConstPtr(((addrOf(v1[pivotPos]))).rawAddress), typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
        val pivotRef = (!hasDirectInteriorMutability<T>()).thenSome(pivotCopy.asPtr())
        // We choose a pivot, and check if this pivot is equal to our left
        // ancestor. If true, we do a partition putting equal elements on the
        // left and do not recurse on it. This gives O(n log k) sorting for k
        // distinct values, a strategy borrowed from pdqsort. For types with
        // interior mutability we can't soundly create a temporary copy of the
        // ancestor pivot, and use leftPartitionLen == 0 as our method for
        // detecting when we re-use a pivot, which means we do at most three
        // partition operations with pivot p instead of the optimal two.
        var performEqualPartition = false
        if (leftAncestorPivot1 != null) {
            val laPivot = leftAncestorPivot1
            performEqualPartition = !isLess(laPivot, v1[pivotPos])
        }
        var leftPartitionLen = 0
        if (!performEqualPartition) {
            leftPartitionLen = stablePartition(v1, scratch, pivotPos, false, isLess)
            performEqualPartition = leftPartitionLen == 0
        }
        if (performEqualPartition) {
            val midEq = stablePartition(v1, scratch, pivotPos, true, { a, b -> !isLess(b, a) })
            v1 = v1.subList(midEq, v1.size)
            leftAncestorPivot1 = null
            continue
        }
        // Process left side with the next loop iter, right side with recursion.
        val (left, right) = v1.splitAtMut(leftPartitionLen)
        quicksort(right, scratch, limit1, pivotRef, isLess)
        v1 = left
    }
}

/**
 * Partitions `v` using pivot `p = v[pivotPos]` and returns the number of
 * elements less than `p`. The relative order of elements that compare < p and
 * those that compare >= p is preserved - it is a stable partition.
 *
 * If `isLess` is not a strict total order or panics, `scratch.len()<v.len()`,
 * or `pivotPos >= v.len()`, the result and `v`s state is sound but unspecified.
 */
public fun <T> stablePartition(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, pivotPos: Int, pivotGoesLeft: Boolean, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    val len = v.size
    if (scratch.size < len || pivotPos >= len) {
        kmAbort()
    }
    val vBase = v.asPtr()
    val scratchBase = scratch.asMutPtr().castInit()
    // The core idea is to write the values that compare as less-than to the left
    // side of `scratch`, while the values that compared as greater or equal than
    // `v[pivotPos]` go to the right side of `scratch` in reverse. See
    // PartitionState for details.
    // SAFETY: see individual comments.
    return run {
        // SAFETY: we made sure the scratch has length >= len and that pivotPos
        // is in-bounds. v and scratch are disjoint slices.
        val pivot = ConstPtr((vBase).rawAddress + ((pivotPos).toLong() * (typeLayoutT).strideBytes))
        val state = PartitionState.new(vBase, scratchBase, len)
        var pivotInScratch = MutPtr(0L)
        var loopEndPos = pivotPos
        // SAFETY: this loop is equivalent to calling state.partitionOne
        // exactly len times.
        while (true) {
            // Ideally the outer loop won't be unrolled, to save binary size,
            // but we do want the inner loop to be unrolled for small types, as
            // this gave significant performance boosts in benchmarks. Unrolling
            // through for _ in 0..UNROLL_LEN ( .. ) instead of manually improves
            // compile times but has a ~10-20% performance penalty on opt-level=s.
            if (sizeOf<T>(typeLayoutT = typeLayoutT) <= 16) {
                val UNROLL_LEN: Int = 4
                val unrollEnd = ConstPtr((vBase).rawAddress + ((loopEndPos.saturatingSub((UNROLL_LEN - 1).toInt())).toLong() * (typeLayoutT).strideBytes))
                while (((state.scan).rawAddress.toULong() < (unrollEnd).rawAddress.toULong())) {
                    state.partitionOne(isLess((typeLayoutT).readNative((state.scan).rawAddress), (typeLayoutT).readNative((pivot).rawAddress)))
                    state.partitionOne(isLess((typeLayoutT).readNative((state.scan).rawAddress), (typeLayoutT).readNative((pivot).rawAddress)))
                    state.partitionOne(isLess((typeLayoutT).readNative((state.scan).rawAddress), (typeLayoutT).readNative((pivot).rawAddress)))
                    state.partitionOne(isLess((typeLayoutT).readNative((state.scan).rawAddress), (typeLayoutT).readNative((pivot).rawAddress)))
                }
            }
            val loopEnd = ConstPtr((vBase).rawAddress + ((loopEndPos).toLong() * (typeLayoutT).strideBytes))
            while (((state.scan).rawAddress.toULong() < (loopEnd).rawAddress.toULong())) {
                state.partitionOne(isLess((typeLayoutT).readNative((state.scan).rawAddress), (typeLayoutT).readNative((pivot).rawAddress)))
            }
            if (loopEndPos == len) {
                break
            }
            // We avoid comparing pivot with itself, as this could create deadlocks for
            // certain comparison operators. We also store its location later for later.
            pivotInScratch = state.partitionOne(pivotGoesLeft)
            loopEndPos = len
        }
        // `pivot` must be copied into its correct position again, because a
        // comparison operator might have modified it.
        if (hasDirectInteriorMutability<T>()) {
            copyNonoverlapping(pivot, pivotInScratch, 1, typeLayoutT = typeLayoutT)
        }
        // SAFETY: partitionOne being called exactly len times guarantees that scratch
        // is initialized with a permuted copy of `v`, and that numLeft <= v.len().
        // Copying scratch[0..numLeft] and scratch[numLeft..v.len()] back is thus
        // sound, as the values in scratch will never be read again, meaning our copies
        // semantically act as moves, permuting `v`.
        // Copy all the elements < p directly from swap to v.
        val vBase1 = v.asMutPtr()
        copyNonoverlapping(scratchBase, vBase1, state.numLeft)
        // Copy the elements >= p in reverse order.
        for (i in 0 until len - state.numLeft) {
            copyNonoverlapping(scratchBase.add(len - 1 - i), MutPtr((vBase1).rawAddress + ((state.numLeft + i).toLong() * (typeLayoutT).strideBytes)), 1)
        }
        state.numLeft
    }
}

public open class PartitionState<T>(
    // The start of the scratch auxiliary memory.
    public var scratchBase: MutPtr,
    // The current element that is being looked at, scans left to right through slice.
    public var scan: ConstPtr,
    // Counts the number of elements that went to the left side, also works around:
    // https://github.com/rust-lang/rust/issues/117128
    public var numLeft: Int,
    // Reverse scratch output pointer.
    public var scratchRev: MutPtr,
) {
    /**
     * Depending on the value of `towardsLeft` this function will write a value
     * to the growing left or right side of the scratch memory. This forms the
     * branchless core of the partition.
     *
     * # Safety
     *
     * This function may be called at most `len` times. If it is called exactly
     * `len` times the scratch buffer then contains a copy of each element from
     * the scan buffer exactly once - a permutation, and numLeft <= len.
     */
    public open fun partitionOne(towardsLeft: Boolean): MutPtr {
        // SAFETY: see individual comments.
        return run {
            // SAFETY: in-bounds because this function is called at most len times, and thus
            // right now is incremented at most len - 1 times. Similarly, numLeft < len and
            // numRight < len, where numRight == i - numLeft at the start of the ith
            // iteration (zero-indexed).
            this.scratchRev = MutPtr((this.scratchRev).rawAddress - ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            // SAFETY: now we have scratchRev == base + len - (i + 1). This means
            // scratchRev + numLeft == base + len - 1 - numRight < base + len.
            val dstBase =
                if (towardsLeft) {
                    this.scratchBase
                } else {
                    this.scratchRev
                }
            val dst = MutPtr((dstBase).rawAddress + ((this.numLeft).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            copyNonoverlapping(this.scan, dst, 1)
            this.numLeft += towardsLeft.toInt()
            this.scan = ConstPtr((this.scan).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            dst
        }
    }

    public companion object {
        public fun <T> typeLayout(): TypeLayout<PartitionState<T>> =
            object : TypeLayout<PartitionState<T>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): PartitionState<T> =
                    PartitionState(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: PartitionState<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.scratchBase)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 8L, value.scan)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.numLeft)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.scratchRev)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PartitionState<T> =
                    PartitionState(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PartitionState<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.scratchBase)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 8, value.scan)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.numLeft)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.scratchRev)
                }
            }

        /**
         * # Safety
         *
         * `scan` and `scratch` must point to valid disjoint buffers of length `len`. The
         * scan buffer must be initialized.
         */
        public fun <T> new(scan: ConstPtr, scratch: MutPtr, len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): PartitionState<T> {
            // SAFETY: See function safety comment.
            return PartitionState(scratchBase = scratch, scan = scan, numLeft = 0, scratchRev = MutPtr((scratch).rawAddress + ((len).toLong() * (typeLayoutT).strideBytes)))
        }
    }
}

public interface IsFreeze {
    public fun isFreeze(): Boolean
}

public fun <T> isFreezeT(): Boolean = false

public fun <T> hasDirectInteriorMutability(isFreezeTFn: () -> T): Boolean {
    // If a type has interior mutability it may alter itself during comparison
    // in a way that must be preserved after the sort operation concludes.
    // Otherwise a type like Mutex<Option<Holder<str>>> could lead to double free.
    return !isFreezeTFn()
}
