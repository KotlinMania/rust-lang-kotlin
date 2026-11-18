// port-lint: source slice/sort/shared/smallsort.rs
package io.github.kotlinmania.rustlang.core.slice.sort.shared.smallsort

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.hint.selectUnpredictable
import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.isMultipleOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.sort.shared.FreezeMarker

// This module contains a variety of sort implementations that are optimized for small lengths.

// It's important to differentiate between SMALL_SORT_THRESHOLD performance for
// small slices and small-sort performance sorting small sub-slices as part of
// the main quicksort loop. For the former, testing showed that the
// representative benchmarks for real-world performance are cold CPU state and
// not single-size hot benchmarks. For the latter the CPU will call them many
// times, so hot benchmarks are fine and more realistic. And it's worth it to
// optimize sorting small sub-slices with more sophisticated solutions than
// insertion sort.

/**
 * Using a trait allows us to specialize on `Freeze` which in turn allows us to make safe
 * abstractions.
 */
public interface StableSmallSortTypeImpl<T> {
    /**
     * For which input length <= return value of this function, is it valid to call `smallSort`.
     */
    public fun smallSortThreshold(): Int

    /**
     * Sorts `v` using strategies optimized for small sizes.
     */
    public fun smallSort(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, isLess: (T, T) -> Boolean)
}

public fun <T> stableSmallSortTypeImplSmallSortThresholdT(): Int {
    // Optimal number of comparisons, and good perf.
    return SMALL_SORT_FALLBACK_THRESHOLD
}

public fun <T> stableSmallSortTypeImplSmallSortT(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, isLess: (T, T) -> Boolean) {
    if (v.size >= 2) {
        insertionSortShiftLeft(v, 1, isLess)
    }
}

/**
 * Using a trait allows us to specialize on `Freeze` which in turn allows us to make safe
 * abstractions.
 */
public interface UnstableSmallSortTypeImpl<T> {
    /**
     * For which input length <= return value of this function, is it valid to call `smallSort`.
     */
    public fun smallSortThreshold(): Int

    /**
     * Sorts `v` using strategies optimized for small sizes.
     */
    public fun smallSort(v: MutableList<T>, isLess: (T, T) -> Boolean)
}

public fun <T> unstableSmallSortTypeImplSmallSortThresholdT(): Int = SMALL_SORT_FALLBACK_THRESHOLD

public fun <T> unstableSmallSortTypeImplSmallSortT(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    smallSortFallback(v, isLess)
}

public fun <T : FreezeMarker> unstableSmallSortTypeImplSmallSortThresholdT(smallSortThresholdTFn: () -> Int): Int = smallSortThresholdTFn()

public fun <T : FreezeMarker> unstableSmallSortTypeImplSmallSortT(v: MutableList<T>, isLess: (T, T) -> Boolean, smallSortTFn: (MutableList<T>, (T, T) -> Boolean) -> Unit) {
    smallSortTFn(v, isLess)
}

/**
 * Note(constTraitImpl) use original ipnsort approach with chooseUnstableSmallSort,
 * as found here <https://github.com/Voultapher/sort-research-rs/blob/438fad5d0495f65d4b72aa87f0b62fc96611dff3/ipnsort/src/smallsort.rs#L83C10-L83C36>.
 */
public interface UnstableSmallSortFreezeTypeImpl<T> : FreezeMarker {
    public fun smallSortThreshold(): Int

    public fun smallSort(v: MutableList<T>, isLess: (T, T) -> Boolean)
}

public fun <T : FreezeMarker> unstableSmallSortFreezeTypeImplSmallSortThresholdT(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int =
    if ((sizeOf<T>(typeLayoutT = typeLayoutT) * SMALL_SORT_GENERAL_SCRATCH_LEN) <= MAX_STACK_ARRAY_SIZE) {
        SMALL_SORT_GENERAL_THRESHOLD
    } else {
        SMALL_SORT_FALLBACK_THRESHOLD
    }

public fun <T : FreezeMarker> unstableSmallSortFreezeTypeImplSmallSortT(v: MutableList<T>, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    if ((sizeOf<T>(typeLayoutT = typeLayoutT) * SMALL_SORT_GENERAL_SCRATCH_LEN) <= MAX_STACK_ARRAY_SIZE) {
        smallSortGeneral(v, isLess)
    } else {
        smallSortFallback(v, isLess)
    }
}

/**
 * SAFETY: Only used for run-time optimization heuristic.
 */
public interface CopyMarker

/**
 * Optimal number of comparisons, and good perf.
 */
public val SMALL_SORT_FALLBACK_THRESHOLD: Int = 16

/**
 * From a comparison perspective 20 was ~2% more efficient for fully random input, but for
 * wall-clock performance choosing 32 yielded better performance overall.
 *
 * SAFETY: If you change this value, you have to adjust [smallSortGeneral] !
 */
public val SMALL_SORT_GENERAL_THRESHOLD: Int = 32

/**
 * [smallSortGeneral] uses [sort8Stable] as primitive and does a kind of ping-pong merge,
 * where the output of the first two [sort8Stable] calls is stored at the end of the scratch
 * buffer. This simplifies panic handling and avoids additional copies. This affects the required
 * scratch buffer size.
 *
 * SAFETY: If you change this value, you have to adjust [smallSortGeneral] !
 */
public val SMALL_SORT_GENERAL_SCRATCH_LEN: Int = SMALL_SORT_GENERAL_THRESHOLD + 16

/**
 * SAFETY: If you change this value, you have to adjust [smallSortNetwork] !
 */
public val SMALL_SORT_NETWORK_THRESHOLD: Int = 32

public val SMALL_SORT_NETWORK_SCRATCH_LEN: Int = SMALL_SORT_NETWORK_THRESHOLD

/**
 * Using a stack array, could cause a stack overflow if the type `T` is very large. To be
 * conservative we limit the usage of small-sorts that require a stack array to types that fit
 * within this limit.
 */
public val MAX_STACK_ARRAY_SIZE: Int = 4096

public fun <T> smallSortFallback(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    if (v.size >= 2) {
        insertionSortShiftLeft(v, 1, isLess)
    }
}

public fun <T : FreezeMarker> smallSortGeneral(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    val stackArray = MaybeUninit.uninit(UnresolvedTypeLayout<List<T>>())
    // SAFETY: The memory is backed by `stackArray`, and the operation is safe as long as the len
    // is the same.
    val scratch = linearMutSliceView(mutPtrFromRustCast(stackArray.asMutPtr()), SMALL_SORT_GENERAL_SCRATCH_LEN.toInt(), UnresolvedTypeLayout<MaybeUninit<T>>())
    smallSortGeneralWithScratch(v, scratch, isLess)
}

public fun <T : FreezeMarker> smallSortGeneralWithScratch(v: MutableList<T>, scratch: MutableList<MaybeUninit<T>>, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    val len = v.size
    if (len < 2) {
        return
    }
    if (scratch.size < len + 16) {
        kmAbort()
    }
    val vBase = v.asMutPtr()
    val lenDiv2 = len / 2
    // SAFETY: See individual comments.
    run {
        val scratchBase = (scratch.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        val presortedLen =
            if (sizeOf<T>(typeLayoutT = typeLayoutT) <= 16 && len >= 16) {
                // SAFETY: scratchBase is valid and has enough space.
                sort8Stable(vBase, scratchBase, MutPtr((scratchBase).rawAddress + ((len).toLong() * (typeLayoutT).strideBytes)), isLess, typeLayoutT = typeLayoutT)
                sort8Stable(MutPtr((vBase).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes)), MutPtr((scratchBase).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes)), MutPtr((scratchBase).rawAddress + ((len + 8).toLong() * (typeLayoutT).strideBytes)), isLess, typeLayoutT = typeLayoutT)
                8
            } else if (len >= 8) {
                // SAFETY: scratchBase is valid and has enough space.
                sort4Stable((vBase).asConstPtr(), scratchBase, isLess, typeLayoutT = typeLayoutT)
                sort4Stable((MutPtr((vBase).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes))).asConstPtr(), MutPtr((scratchBase).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes)), isLess, typeLayoutT = typeLayoutT)
                4
            } else {
                copyNonoverlapping((vBase).asConstPtr(), scratchBase, 1, typeLayoutT = typeLayoutT)
                copyNonoverlapping((MutPtr((vBase).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes))).asConstPtr(), MutPtr((scratchBase).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes)), 1, typeLayoutT = typeLayoutT)
                1
            }
        for (offset in intArrayOf(0, lenDiv2)) {
            // SAFETY: at this point dst is initialized with presortedLen elements.
            // We extend this to desiredLen, src is valid for desiredLen elements.
            val src = MutPtr((vBase).rawAddress + ((offset).toLong() * (typeLayoutT).strideBytes))
            val dst = MutPtr((scratchBase).rawAddress + ((offset).toLong() * (typeLayoutT).strideBytes))
            val desiredLen =
                if (offset == 0) {
                    lenDiv2
                } else {
                    len - lenDiv2
                }
            for (i in presortedLen until desiredLen) {
                copyNonoverlapping((MutPtr((src).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes))).asConstPtr(), MutPtr((dst).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes)), 1, typeLayoutT = typeLayoutT)
                insertTail(dst, MutPtr((dst).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes)), isLess, typeLayoutT = typeLayoutT)
            }
        }
        // SAFETY: see comment in `CopyOnDrop.drop`.
        val dropGuard = CopyOnDrop(src = scratchBase, dst = vBase, len = len)
        // SAFETY: at this point scratchBase is fully initialized, allowing us
        // to use it as the source of our merge back into the original array.
        // If a panic occurs we ensure the original array is restored to a valid
        // permutation of the input through dropGuard. This technique is similar
        // to ping-pong merging.
        bidirectionalMerge(linearSliceView(dropGuard.src, dropGuard.len.toInt(), typeLayoutT), dropGuard.dst, isLess)
        forget(dropGuard)
    }
}

public open class CopyOnDrop<T>(
    public var src: ConstPtr,
    public var dst: MutPtr,
    public var len: Int,
) {
    public open fun drop() {
        // SAFETY: `src` must contain `len` initialized elements, and dst must
        // be valid to write `len` elements.
        run {
            copyNonoverlapping(this.src, this.dst, this.len)
        }
    }

    public companion object {
        public fun <T> typeLayout(): TypeLayout<CopyOnDrop<T>> =
            object : TypeLayout<CopyOnDrop<T>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): CopyOnDrop<T> =
                    CopyOnDrop(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: CopyOnDrop<T>) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.src)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.dst)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CopyOnDrop<T> =
                    CopyOnDrop(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CopyOnDrop<T>) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.src)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.dst)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.len)
                }
            }
    }
}

public fun <T> smallSortNetwork(v: MutableList<T>, isLess: (T, T) -> Boolean) where T : FreezeMarker {
    // This implementation is tuned to be efficient for integer types.
    val len = v.size
    if (len < 2) {
        return
    }
    if (len > SMALL_SORT_NETWORK_SCRATCH_LEN) {
        kmAbort()
    }
    val stackArray = MaybeUninit.uninit(UnresolvedTypeLayout<List<T>>())
    val lenDiv2 = len / 2
    val noMerge = len < 18
    val vBase = v.asMutPtr()
    val initialRegionLen =
        if (noMerge) {
            len
        } else {
            lenDiv2
        }
    // SAFETY: Both possible values of `initialRegionLen` are in-bounds.
    var region = linearMutSliceView(vBase, initialRegionLen.toInt(), UnresolvedTypeLayout<T>())
    // Avoid compiler unrolling, we *really* don't want that to happen here for binary-size reasons.
    while (true) {
        val presortedLen =
            if (region.size >= 13) {
                sort13Optimal(region, isLess)
                13
            } else if (region.size >= 9) {
                sort9Optimal(region, isLess)
                9
            } else {
                1
            }
        insertionSortShiftLeft(region, presortedLen, isLess)
        if (noMerge) {
            return
        }
        if (((region.asPtr()).rawAddress.toULong() != (vBase).rawAddress.toULong())) {
            break
        }
        // SAFETY: The right side of `v` based on `lenDiv2` is guaranteed in-bounds.
        region = linearMutSliceView(MutPtr((vBase).rawAddress + ((lenDiv2).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), (len - lenDiv2).toInt(), UnresolvedTypeLayout<T>())
    }
    // SAFETY: We checked that T is Freeze and thus observation safe.
    // Should isLess panic v was not modified in parityMerge and retains it's original input.
    // scratch and v must not alias and scratch has v.len() space.
    run {
        val scratchBase = mutPtrFromRustCast(stackArray.asMutPtr())
        bidirectionalMerge(linearMutSliceView(vBase, len.toInt(), UnresolvedTypeLayout<T>()), scratchBase, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        copyNonoverlapping((scratchBase).asConstPtr(), vBase, len, typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

/**
 * Swap two values in the slice pointed to by `vBase` at the position `aPos` and `bPos` if the
 * value at position `bPos` is less than the one at position `aPos`.
 *
 * Purposefully not marked `@inline`, despite us wanting it to be inlined for integers like
 * types. `isLess` could be a huge function and we want to give the compiler an option to
 * not inline this function. For the same reasons that this function is very perf critical
 * it should be in the same module as the functions that use it.
 */
public fun <T> swapIfLess(vBase: MutPtr, aPos: Int, bPos: Int, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must guarantee that `aPos` and `bPos` each added to `vBase` yield valid
    // pointers into `vBase`, and are properly aligned, and part of the same allocation.
    run {
        val vA = MutPtr((vBase).rawAddress + ((aPos).toLong() * (typeLayoutT).strideBytes))
        val vB = MutPtr((vBase).rawAddress + ((bPos).toLong() * (typeLayoutT).strideBytes))
        // PANIC SAFETY: if isLess panics, no scratch memory was created and the slice should still be
        // in a well defined state, without duplicates.
        // Important to only swap if it is more and not if it is equal. isLess should return false for
        // equal, so we don't swap.
        val shouldSwap = isLess((typeLayoutT).readNative((vB).rawAddress), (typeLayoutT).readNative((vA).rawAddress))
        // This is a branchless version of swap if.
        // The equivalent code with a branch would be:
        //
        // if shouldSwap (
        // ptr.swap(vA, vB, 1);
        // )
        // The goal is to generate cmov instructions here.
        val vASwap = selectUnpredictable(shouldSwap, vB, vA)
        val vBSwap = selectUnpredictable(shouldSwap, vA, vB)
        val vBSwapTmp = read(vBSwap)
        copy(vASwap, vA, 1)
        copyNonoverlapping(vBSwapTmp, vB, 1)
    }
}

/**
 * Sorts the first 9 elements of `v` with a fast fixed function.
 *
 * Should `isLess` generate substantial amounts of code the compiler can choose to not inline
 * `swapIfLess`. If the code of a sort impl changes so as to call this function in multiple
 * places, `@inline(never)` is recommended to keep binary-size in check. The current design of
 * `smallSortNetwork` makes sure to only call this once.
 */
public fun <T> sort9Optimal(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    if (v.size < 9) {
        kmAbort()
    }
    val vBase = v.asMutPtr()
    // Optimal sorting network see:
    // https://bertdobbelaere.github.io/sortingNetworks.html.
    // SAFETY: We checked the len.
    run {
        swapIfLess(vBase, 0, 3, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 0, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 0, 2, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 3, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 7, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 0, 1, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 3, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 2, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

/**
 * Sorts the first 13 elements of `v` with a fast fixed function.
 *
 * Should `isLess` generate substantial amounts of code the compiler can choose to not inline
 * `swapIfLess`. If the code of a sort impl changes so as to call this function in multiple
 * places, `@inline(never)` is recommended to keep binary-size in check. The current design of
 * `smallSortNetwork` makes sure to only call this once.
 */
public fun <T> sort13Optimal(v: MutableList<T>, isLess: (T, T) -> Boolean) {
    if (v.size < 13) {
        kmAbort()
    }
    val vBase = v.asMutPtr()
    // Optimal sorting network see:
    // https://bertdobbelaere.github.io/sortingNetworks.html.
    // SAFETY: We checked the len.
    run {
        swapIfLess(vBase, 0, 12, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 10, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 9, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 11, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 3, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 11, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 7, 9, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 8, 10, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 0, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 2, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 7, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 9, 10, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 11, 12, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 9, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 8, 11, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 10, 12, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 0, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 11, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 9, 10, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 0, 1, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 9, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 7, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 10, 11, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 3, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 9, 10, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 1, 2, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 8, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 2, 3, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 4, 5, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 6, 7, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 8, 9, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 3, 4, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
        swapIfLess(vBase, 5, 6, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

/**
 * Sorts range [begin, tail] assuming [begin, tail) is already sorted.
 *
 * # Safety
 * begin < tail and p must be valid and initialized for all begin <= p <= tail.
 */
public fun <T> insertTail(begin: MutPtr, tail: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: see individual comments.
    run {
        // SAFETY: in-bounds as tail > begin.
        var sift = MutPtr((tail).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
        if (!isLess((typeLayoutT).readNative((tail).rawAddress), (typeLayoutT).readNative((sift).rawAddress))) {
            return
        }
        // SAFETY: after this read tail is never read from again, as we only ever
        // read from sift, sift < tail and we only ever decrease sift. Thus this is
        // effectively a move, not a copy. Should a panic occur, or we have found
        // the correct insertion position, gapGuard ensures the element is moved
        // back into the array.
        val tmp = (typeLayoutT).readNative((tail).rawAddress)
        val gapGuard = CopyOnDrop(src = tmp, dst = tail, len = 1)
        while (true) {
            // SAFETY: we move sift into the gap (which is valid), and point the
            // gap guard destination at sift, ensuring that if a panic occurs the
            // gap is once again filled.
            copyNonoverlapping((sift).asConstPtr(), gapGuard.dst, 1, typeLayoutT = typeLayoutT)
            gapGuard.dst = sift
            if (((sift).rawAddress.toULong() == (begin).rawAddress.toULong())) {
                break
            }
            // SAFETY: we checked that sift != begin, thus this is in-bounds.
            sift = MutPtr((sift).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
            if (!isLess(tmp, (typeLayoutT).readNative((sift).rawAddress))) {
                break
            }
        }
    }
}

/**
 * Sort `v` assuming `v[.. offset]` is already sorted.
 */
public fun <T> insertionSortShiftLeft(v: MutableList<T>, offset: Int, isLess: (T, T) -> Boolean) {
    val len = v.size
    if (offset == 0 || offset > len) {
        kmAbort()
    }
    // SAFETY: see individual comments.
    run {
        // We write this basic loop directly using pointers, as when we use a
        // for loop LLVM likes to unroll this loop which we do not want.
        // SAFETY: vEnd is the one-past-end pointer, and we checked that
        // offset <= len, thus tail is also in-bounds.
        val vBase = v.asMutPtr()
        val vEnd = MutPtr((vBase).rawAddress + ((len).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        var tail = MutPtr((vBase).rawAddress + ((offset).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        while (((tail).rawAddress.toULong() != (vEnd).rawAddress.toULong())) {
            // SAFETY: vBase and tail are both valid pointers to elements, and
            // vBase < tail since we checked offset != 0.
            insertTail(vBase, tail, isLess, typeLayoutT = UnresolvedTypeLayout<T>())
            // SAFETY: we checked that tail is not yet the one-past-end pointer.
            tail = MutPtr((tail).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        }
    }
}

/**
 * SAFETY: The caller MUST guarantee that `vBase` is valid for 4 reads and
 * `dst` is valid for 4 writes. The result will be stored in `dst[0 .. 4]`.
 */
public fun <T> sort4Stable(vBase: ConstPtr, dst: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // By limiting select to picking pointers, we are guaranteed good cmov code-gen
    // regardless of type T's size. Further this only does 5 instead of 6
    // comparisons compared to a stable transposition 4 element sorting-network,
    // and always copies each element exactly once.
    // SAFETY: all pointers have offset at most 3 from vBase and dst, and are
    // thus in-bounds by the precondition.
    run {
        // Stably create two pairs a <= b and c <= d.
        val c1 = isLess((typeLayoutT).readNative((ConstPtr((vBase).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))).rawAddress), (typeLayoutT).readNative((vBase).rawAddress))
        val c2 = isLess((typeLayoutT).readNative((ConstPtr((vBase).rawAddress + ((3).toLong() * (typeLayoutT).strideBytes))).rawAddress), (typeLayoutT).readNative((ConstPtr((vBase).rawAddress + ((2).toLong() * (typeLayoutT).strideBytes))).rawAddress))
        val a = ConstPtr((vBase).rawAddress + ((c1.toInt()).toLong() * (typeLayoutT).strideBytes))
        val b = ConstPtr((vBase).rawAddress + (((if (!c1) 1 else 0)).toLong() * (typeLayoutT).strideBytes))
        val c = ConstPtr((vBase).rawAddress + ((2 + c2.toInt()).toLong() * (typeLayoutT).strideBytes))
        val d = ConstPtr((vBase).rawAddress + ((2 + ((if (!c2) 1 else 0))).toLong() * (typeLayoutT).strideBytes))
        // Compare (a, c) and (b, d) to identify max/min. We're left with two
        // unknown elements, but because we are a stable sort we must know which
        // one is leftmost and which one is rightmost.
        // c3, c4 | min max unknownLeft unknownRight
        // 0,  0 |  a   d    b         c
        // 0,  1 |  a   b    c         d
        // 1,  0 |  c   d    a         b
        // 1,  1 |  c   b    a         d
        val c3 = isLess((typeLayoutT).readNative((c).rawAddress), (typeLayoutT).readNative((a).rawAddress))
        val c4 = isLess((typeLayoutT).readNative((d).rawAddress), (typeLayoutT).readNative((b).rawAddress))
        val min = selectUnpredictable(c3, c, a)
        val max = selectUnpredictable(c4, b, d)
        val unknownLeft = selectUnpredictable(c3, a, selectUnpredictable(c4, c, b))
        val unknownRight = selectUnpredictable(c4, d, selectUnpredictable(c3, b, c))
        // Sort the last two unknown elements.
        val c5 = isLess(unknownRight, unknownLeft)
        val lo = selectUnpredictable(c5, unknownRight, unknownLeft)
        val hi = selectUnpredictable(c5, unknownLeft, unknownRight)
        copyNonoverlapping(min, dst, 1)
        copyNonoverlapping(lo, MutPtr((dst).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes)), 1)
        copyNonoverlapping(hi, MutPtr((dst).rawAddress + ((2).toLong() * (typeLayoutT).strideBytes)), 1)
        copyNonoverlapping(max, MutPtr((dst).rawAddress + ((3).toLong() * (typeLayoutT).strideBytes)), 1)
    }
}

/**
 * SAFETY: The caller MUST guarantee that `vBase` is valid for 8 reads and
 * writes, `scratchBase` and `dst` MUST be valid for 8 writes. The result will
 * be stored in `dst[0 .. 8]`.
 */
public fun <T : FreezeMarker> sort8Stable(vBase: MutPtr, dst: MutPtr, scratchBase: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: these pointers are all in-bounds by the precondition of our function.
    run {
        sort4Stable((vBase).asConstPtr(), scratchBase, isLess, typeLayoutT = typeLayoutT)
        sort4Stable((MutPtr((vBase).rawAddress + ((4).toLong() * (typeLayoutT).strideBytes))).asConstPtr(), MutPtr((scratchBase).rawAddress + ((4).toLong() * (typeLayoutT).strideBytes)), isLess, typeLayoutT = typeLayoutT)
    }
    // SAFETY: scratchBase[0..8] is now initialized, allowing us to merge back
    // into dst.
    run {
        bidirectionalMerge(linearSliceView(scratchBase, 8, typeLayoutT), dst, isLess, typeLayoutT = typeLayoutT)
    }
}

public fun <T> mergeUp(leftSrc: ConstPtr, rightSrc: ConstPtr, dst: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Triple<ConstPtr, ConstPtr, MutPtr> {
    var leftSrc1 = leftSrc
    var rightSrc1 = rightSrc
    var dst1 = dst
    // This is a branchless merge utility function.
    // The equivalent code with a branch would be:
    //
    // if !isLess(&*rightSrc, &*leftSrc) (
    // ptr.copyNonoverlapping(leftSrc, dst, 1);
    // leftSrc = leftSrc.add(1);
    // ) else (
    // ptr.copyNonoverlapping(rightSrc, dst, 1);
    // rightSrc = rightSrc.add(1);
    // )
    // dst = dst.add(1);
    // SAFETY: The caller must guarantee that `leftSrc`, `rightSrc` are valid
    // to read and `dst` is valid to write, while not aliasing.
    run {
        val isL = !isLess((typeLayoutT).readNative((rightSrc1).rawAddress), (typeLayoutT).readNative((leftSrc1).rawAddress))
        val src =
            if (isL) {
                leftSrc1
            } else {
                rightSrc1
            }
        copyNonoverlapping(src, dst1, 1)
        rightSrc1 = ConstPtr((rightSrc1).rawAddress + (((if (!isL) 1 else 0)).toLong() * (typeLayoutT).strideBytes))
        leftSrc1 = ConstPtr((leftSrc1).rawAddress + ((isL.toInt()).toLong() * (typeLayoutT).strideBytes))
        dst1 = MutPtr((dst1).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
    }
    return Triple(leftSrc1, rightSrc1, dst1)
}

public fun <T> mergeDown(leftSrc: ConstPtr, rightSrc: ConstPtr, dst: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Triple<ConstPtr, ConstPtr, MutPtr> {
    var leftSrc1 = leftSrc
    var rightSrc1 = rightSrc
    var dst1 = dst
    // This is a branchless merge utility function.
    // The equivalent code with a branch would be:
    //
    // if !isLess(&*rightSrc, &*leftSrc) (
    // ptr.copyNonoverlapping(rightSrc, dst, 1);
    // rightSrc = rightSrc.wrappingSub(1);
    // ) else (
    // ptr.copyNonoverlapping(leftSrc, dst, 1);
    // leftSrc = leftSrc.wrappingSub(1);
    // )
    // dst = dst.sub(1);
    // SAFETY: The caller must guarantee that `leftSrc`, `rightSrc` are valid
    // to read and `dst` is valid to write, while not aliasing.
    run {
        val isL = !isLess((typeLayoutT).readNative((rightSrc1).rawAddress), (typeLayoutT).readNative((leftSrc1).rawAddress))
        val src =
            if (isL) {
                rightSrc1
            } else {
                leftSrc1
            }
        copyNonoverlapping(src, dst1, 1)
        rightSrc1 = ConstPtr((rightSrc1).rawAddress - ((isL.toInt()).toLong() * (typeLayoutT).strideBytes))
        leftSrc1 = ConstPtr((leftSrc1).rawAddress - (((if (!isL) 1 else 0)).toLong() * (typeLayoutT).strideBytes))
        dst1 = MutPtr((dst1).rawAddress - ((1).toLong() * (typeLayoutT).strideBytes))
    }
    return Triple(leftSrc1, rightSrc1, dst1)
}

/**
 * Merge v assuming v[..len / 2] and v[len / 2..] are sorted.
 *
 * Original idea for bi-directional merging by Igor van den Hoven (quadsort),
 * adapted to only use merge up and down. In contrast to the original
 * parityMerge function, it performs 2 writes instead of 4 per iteration.
 *
 * # Safety
 * The caller must guarantee that `dst` is valid for v.len() writes.
 * Also `v.asPtr()` and `dst` must not alias and v.len() must be >= 2.
 *
 * Note that T must be Freeze, the comparison function is evaluated on outdated
 * temporary `copies` that may not end up in the final array.
 */
public fun <T : FreezeMarker> bidirectionalMerge(v: List<T>, dst: MutPtr, isLess: (T, T) -> Boolean, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // It helps to visualize the merge:
    //
    // Initial:
    //
    // |dst (in dst)
    // |left               |right
    // v                   v
    // [xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx]
    // ^                   ^
    // |leftRev           |rightRev
    // |dstRev (in dst)
    //
    // After:
    //
    // |dst (in dst)
    // |left         |           |right
    // v             v           v
    // [xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx]
    // ^             ^           ^
    // |leftRev     |           |rightRev
    // |dstRev (in dst)
    //
    // In each iteration one of left or right moves up one position, and one of
    // leftRev or rightRev moves down one position, whereas dst always moves
    // up one position and dstRev always moves down one position. Assuming
    // the input was sorted and the comparison function is correctly implemented
    // at the end we will have left == leftRev + 1, and right == rightRev + 1,
    // fully consuming the input having written it to dst.
    val len = v.size
    val src = v.asPtr()
    val lenDiv2 = len / 2
    // SAFETY: The caller has to ensure that len >= 2.
    run {
        // This can avoid useless code-gen.
    }
    // SAFETY: no matter what the result of the user-provided comparison function
    // is, all 4 read pointers will always be in-bounds. Writing `dst` and `dstRev`
    // will always be in bounds if the caller guarantees that `dst` is valid for
    // `v.len()` writes.
    run {
        var left = src
        var right = ConstPtr((src).rawAddress + ((lenDiv2).toLong() * (typeLayoutT).strideBytes))
        var dst1 = dst
        var leftRev = ConstPtr((src).rawAddress + ((lenDiv2 - 1).toLong() * (typeLayoutT).strideBytes))
        var rightRev = ConstPtr((src).rawAddress + ((len - 1).toLong() * (typeLayoutT).strideBytes))
        var dstRev = dst1.add(len - 1)
        for (unused in 0 until lenDiv2) {
            run { val (destructVal0, destructVal1, destructVal2) = mergeUp(left, right, dst1, isLess, typeLayoutT = typeLayoutT); left = destructVal0; right = destructVal1; dst1 = destructVal2 }
            run { val (destructVal0, destructVal1, destructVal2) = mergeDown(leftRev, rightRev, dstRev, isLess, typeLayoutT = typeLayoutT); leftRev = destructVal0; rightRev = destructVal1; dstRev = destructVal2 }
        }
        val leftEnd = ConstPtr((leftRev).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
        val rightEnd = ConstPtr((rightRev).rawAddress + ((1).toLong() * (typeLayoutT).strideBytes))
        // Odd length, so one element is left unconsumed in the input.
        if (!len.isMultipleOf(2)) {
            val leftNonempty = ((left).rawAddress.toULong() < (leftEnd).rawAddress.toULong())
            val lastSrc =
                if (leftNonempty) {
                    left
                } else {
                    right
                }
            copyNonoverlapping(lastSrc, dst1, 1)
            left = ConstPtr((left).rawAddress + ((leftNonempty.toInt()).toLong() * (typeLayoutT).strideBytes))
            right = ConstPtr((right).rawAddress + (((if (!leftNonempty) 1 else 0)).toLong() * (typeLayoutT).strideBytes))
        }
        // We now should have consumed the full input exactly once. This can only fail if the
        // user-provided comparison function fails to implement a strict weak ordering. In that case
        // we panic and never access the inconsistent state in dst.
        if (((left).rawAddress.toULong() != (leftEnd).rawAddress.toULong()) || ((right).rawAddress.toULong() != (rightEnd).rawAddress.toULong())) {
            panicOnOrdViolation()
        }
    }
}

public fun panicOnOrdViolation(): Nothing {
    // This is indicative of a logic bug in the user-provided comparison function or Ord
    // implementation. They are expected to implement a total order as explained in the Ord
    // documentation.
    //
    // By panicking we inform the user, that they have a logic bug in their program. If a strict
    // weak ordering is not given, the concept of comparison based sorting cannot yield a sorted
    // result. E.g.: a < b < c < a
    //
    // The Ord documentation requires users to implement a total order. Arguably that's
    // unnecessarily strict in the context of sorting. Issues only arise if the weaker requirement
    // of a strict weak ordering is violated.
    //
    // The panic message talks about a total order because that's what the Ord documentation talks
    // about and requires, so as to not confuse users.
    error("user-provided comparison function does not correctly implement a total order")
}

public fun <T> hasEfficientInPlaceSwap(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean {
    // Heuristic that holds true on all tested 64-bit capable architectures.
    return sizeOf<T>(typeLayoutT = typeLayoutT) <= 8
    // sizeOf::<u64>()
}
