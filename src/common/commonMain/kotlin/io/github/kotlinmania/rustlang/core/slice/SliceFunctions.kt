package io.github.kotlinmania.rustlang.core.slice

import io.github.kotlinmania.rustlang.core.Simd
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.hint.selectUnpredictable
import io.github.kotlinmania.rustlang.core.intrinsics.exactDiv
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopy
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.isMultipleOf
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.uncheckedMul
import io.github.kotlinmania.rustlang.core.ops.range.OneSidedRange
import io.github.kotlinmania.rustlang.core.ops.range.OneSidedRangeBound.End
import io.github.kotlinmania.rustlang.core.ops.range.OneSidedRangeBound.StartInclusive
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.alignOffset
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.metadata.metadata
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.copyFromNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.splitAtMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingSub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.swap
import io.github.kotlinmania.rustlang.core.ptr.swapNonoverlapping
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.cmp.sliceContains
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex
import io.github.kotlinmania.rustlang.core.slice.index.range
import io.github.kotlinmania.rustlang.core.slice.iter.ArrayWindows
import io.github.kotlinmania.rustlang.core.slice.iter.ChunkBy
import io.github.kotlinmania.rustlang.core.slice.iter.ChunkByMut
import io.github.kotlinmania.rustlang.core.slice.iter.Chunks
import io.github.kotlinmania.rustlang.core.slice.iter.ChunksExact
import io.github.kotlinmania.rustlang.core.slice.iter.ChunksExactMut
import io.github.kotlinmania.rustlang.core.slice.iter.ChunksMut
import io.github.kotlinmania.rustlang.core.slice.iter.Iter
import io.github.kotlinmania.rustlang.core.slice.iter.IterMut
import io.github.kotlinmania.rustlang.core.slice.iter.RChunks
import io.github.kotlinmania.rustlang.core.slice.iter.RChunksExact
import io.github.kotlinmania.rustlang.core.slice.iter.RChunksExactMut
import io.github.kotlinmania.rustlang.core.slice.iter.RChunksMut
import io.github.kotlinmania.rustlang.core.slice.iter.RSplit
import io.github.kotlinmania.rustlang.core.slice.iter.RSplitMut
import io.github.kotlinmania.rustlang.core.slice.iter.RSplitN
import io.github.kotlinmania.rustlang.core.slice.iter.RSplitNMut
import io.github.kotlinmania.rustlang.core.slice.iter.Split
import io.github.kotlinmania.rustlang.core.slice.iter.SplitInclusive
import io.github.kotlinmania.rustlang.core.slice.iter.SplitInclusiveMut
import io.github.kotlinmania.rustlang.core.slice.iter.SplitMut
import io.github.kotlinmania.rustlang.core.slice.iter.SplitN
import io.github.kotlinmania.rustlang.core.slice.iter.SplitNMut
import io.github.kotlinmania.rustlang.core.slice.iter.Windows
import io.github.kotlinmania.rustlang.core.slice.rotate.ptrRotate
import io.github.kotlinmania.rustlang.core.slice.sort.select.partitionAtIndex
import io.github.kotlinmania.rustlang.core.slice.sort.unstable.partialSort
import io.github.kotlinmania.rustlang.core.slice.sort.unstable.sort
import io.github.kotlinmania.rustlang.core.slice.specialize.specFill

/**
 * Calculates the direction and split point of a one-sided range.
 *
 * This is a helper function for `splitOff` and `splitOffMut` that returns
 * the direction of the split (front or back) as well as the index at
 * which to split. Returns `None` if the split index would overflow.
 */
public fun splitPointOf(range: OneSidedRange<Int>): Pair<Direction, Int>? {
    return run {
        val matchVal = range.bound()
        when {
            matchVal.first == StartInclusive -> {
                val i = matchVal.second
                Pair(Direction.Back, i)
            }
            matchVal.first == End -> {
                val i1 = matchVal.second
                Pair(Direction.Front, i1)
            }
            else -> {
                val i2 = matchVal.second
                Pair(Direction.Front, (i2.checkedAdd(1) ?: return null))
            }
        }
    }
}

/**
 * Copies `src` to `dest`.
 *
 * # Safety
 * `T` must implement one of `Copy` or `TrivialClone`.
 */
public fun <T> copyFromSliceImpl(dest: MutableList<T>, src: List<T>) {
    // The panic code path was put into a cold function to not bloat the
    // call site.
    fun lenMismatchFail(dstLen: Int, srcLen: Int): Nothing {
        error("copy_from_slice: source slice length does not match destination slice length")
    }
    if (dest.size != src.size) {
        lenMismatchFail(dest.size, src.size)
    }
    // SAFETY: `self` is valid for `self.len()` elements by definition, and `src` was
    // checked to have the same length. The slices cannot overlap because
    // mutable references are exclusive.
    run {
        copyNonoverlapping(src.asPtr(), dest.asMutPtr(), dest.size, typeLayoutT = UnresolvedTypeLayout<T>())
    }
}

/**
 * This checks every index against each other, and against `len`.
 *
 * This will do `binomial(N + 1, 2) = N * (N + 1) / 2 = 0, 1, 3, 6, 10, ..`
 * comparison operations.
 */
public fun <I : GetDisjointMutIndex<I>> getDisjointCheckValid(indices: List<I>, len: Int, n: Int = 0): Result<Unit, GetDisjointMutError> {
    // NB: The optimizer should inline the loops into a sequence
    // of instructions without additional branching.
    for ((i, idx) in indices.mapIndexed { index, item -> Pair(index, item) }) {
        if (!idx.isInBounds(len)) {
            return Result.Err<Unit, GetDisjointMutError>(GetDisjointMutError.IndexOutOfBounds)
        }
        for (idx2 in ((indices).subList(0, i))) {
            if (idx.isOverlapping(idx2)) {
                return Result.Err<Unit, GetDisjointMutError>(GetDisjointMutError.OverlappingIndices)
            }
        }
    }
    return Result.Ok<Unit, GetDisjointMutError>(Unit)
}

/**
 * Returns the number of elements in the slice.
 *
 * # Examples
 *
 * ```kotlin
 * val a = listOf(1, 2, 3)
 * check(a.len() == 3)
 * ```
 */
public fun <T> List<T>.len(): Int = metadata(this)

/**
 * Returns `true` if the slice has a length of 0.
 *
 * # Examples
 *
 * ```kotlin
 * val a = listOf(1, 2, 3)
 * check(!a.isEmpty())
 * val b: List<Int> = listOf()
 * check(b.isEmpty())
 * ```
 */
public fun <T> List<T>.isEmpty(): Boolean = this.size == 0

/**
 * Returns the first element of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(10 == v.first())
 * val w: List<Int> = listOf()
 * check(null == w.first())
 * ```
 */
public fun <T> List<T>.first(): T? =
    if (this != null) {
        val first = this[0]
        first
    } else {
        null
    }

/**
 * Returns a mutable reference to the first element of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.firstMut()) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal
 *         first = 5
 *     }
 * }
 * check((x).contentEquals(listOf(5, 1, 2)))
 * val y: MutableList<Int> = mutableListOf()
 * check(null == y.firstOrNull())
 * ```
 */
public fun <T> List<T>.firstMut(): T? =
    if (this != null) {
        val first = this[0]
        first
    } else {
        null
    }

/**
 * Returns the first and all the rest of the elements of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitFirst()) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal.first
 *         val elements = matchVal.second
 *         check(first == 0)
 *         check(elements == listOf(1, 2))
 *     }
 * }
 * ```
 */
public fun <T> List<T>.splitFirst(): Pair<T, List<T>>? =
    if (this != null) {
        val first = this[0]
        val tail = this.subList(1, this.size)
        Pair(first, tail)
    } else {
        null
    }

/**
 * Returns the first and all the rest of the elements of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitFirstMut()) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal.first
 *         val elements = matchVal.second
 *         first = 3
 *         elements[0] = 4
 *         elements[1] = 5
 *     }
 * }
 * check((x).contentEquals(listOf(3, 4, 5)))
 * ```
 */
public fun <T> List<T>.splitFirstMut(): Pair<T, MutableList<T>>? =
    if (this != null) {
        val first = this[0]
        val tail = this.subList(1, this.size)
        Pair(first, tail)
    } else {
        null
    }

/**
 * Returns the last and all the rest of the elements of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitLast()) {
 *     null -> {}
 *     else -> {
 *         val last = matchVal.first
 *         val elements = matchVal.second
 *         check(last == 2)
 *         check(elements == listOf(0, 1))
 *     }
 * }
 * ```
 */
public fun <T> List<T>.splitLast(): Pair<T, List<T>>? =
    if (this != null) {
        val init = this.subList(0, this.size - 1)
        val last = this[this.size - 1]
        Pair(last, init)
    } else {
        null
    }

/**
 * Returns the last and all the rest of the elements of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitLastMut()) {
 *     null -> {}
 *     else -> {
 *         val last = matchVal.first
 *         val elements = matchVal.second
 *         last = 3
 *         elements[0] = 4
 *         elements[1] = 5
 *     }
 * }
 * check((x).contentEquals(listOf(4, 5, 3)))
 * ```
 */
public fun <T> List<T>.splitLastMut(): Pair<T, MutableList<T>>? =
    if (this != null) {
        val init = this.subList(0, this.size - 1)
        val last = this[this.size - 1]
        Pair(last, init)
    } else {
        null
    }

/**
 * Returns the last element of the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(30 == v.last())
 * val w: List<Int> = listOf()
 * check(null == w.last())
 * ```
 */
public fun <T> List<T>.last(): T? =
    if (this != null) {
        val last = this[this.size - 1]
        last
    } else {
        null
    }

/**
 * Returns a mutable reference to the last item in the slice, or `None` if it is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.lastMut()) {
 *     null -> {}
 *     else -> {
 *         val last = matchVal
 *         last = 10
 *     }
 * }
 * check((x).contentEquals(listOf(0, 1, 10)))
 * val y: MutableList<Int> = mutableListOf()
 * check(null == y.lastOrNull())
 * ```
 */
public fun <T> List<T>.lastMut(): T? =
    if (this != null) {
        val last = this[this.size - 1]
        last
    } else {
        null
    }

/**
 * Returns an array reference to the first `N` items in the slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val u = listOf(10, 40, 30)
 * check(listOf(10, 40) == u.firstChunk(2))
 * val v: List<Int> = listOf(10)
 * check(null == v.firstChunk(2))
 * val w: List<Int> = listOf()
 * check(listOf() == w.firstChunk(0))
 * ```
 */
public fun <T> List<T>.firstChunk(n: Int = 0): List<T>? =
    if (this.size < n) {
        null
    } else {
        // SAFETY: We explicitly check for the correct number of elements,
        // and do not let the reference outlive the slice.
        (this.asPtr().castArray())
    }

/**
 * Returns a mutable array reference to the first `N` items in the slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.firstChunkMut(2)) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal
 *         first[0] = 5
 *         first[1] = 4
 *     }
 * }
 * check((x).contentEquals(listOf(5, 4, 2)))
 * check(null == x.firstChunkMut(4))
 * ```
 */
public fun <T> List<T>.firstChunkMut(n: Int = 0): List<T>? =
    if (this.size < n) {
        null
    } else {
        // SAFETY: We explicitly check for the correct number of elements,
        // do not let the reference outlive the slice,
        // and require exclusive access to the entire slice to mutate the chunk.
        (this.asMutPtr().castArray())
    }

/**
 * Returns an array reference to the first `N` items in the slice and the remaining slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitFirstChunk(2)) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal.first
 *         val elements = matchVal.second
 *         check(first == listOf(0, 1))
 *         check(elements == listOf(2))
 *     }
 * }
 * check(null == x.splitFirstChunk(4))
 * ```
 */
public fun <T> List<T>.splitFirstChunk(n: Int = 0): Pair<List<T>, List<T>>? {
    val (first, tail) = this.splitAtChecked(n) ?: return null
    // SAFETY: We explicitly check for the correct number of elements,
    // and do not let the references outlive the slice.
    return Pair((first.asPtr().castArray()), tail)
}

/**
 * Returns a mutable array reference to the first `N` items in the slice and the remaining
 * slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitFirstChunkMut(2)) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal.first
 *         val elements = matchVal.second
 *         first[0] = 3
 *         first[1] = 4
 *         elements[0] = 5
 *     }
 * }
 * check((x).contentEquals(listOf(3, 4, 5)))
 * check(null == x.splitFirstChunkMut(4))
 * ```
 */
public fun <T> List<T>.splitFirstChunkMut(n: Int = 0): Pair<List<T>, MutableList<T>>? {
    val (first, tail) = this.splitAtMutChecked(n) ?: return null
    // SAFETY: We explicitly check for the correct number of elements,
    // do not let the reference outlive the slice,
    // and enforce exclusive mutability of the chunk by the split.
    return Pair((first.asMutPtr().castArray()), tail)
}

/**
 * Returns an array reference to the last `N` items in the slice and the remaining slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitLastChunk(2)) {
 *     null -> {}
 *     else -> {
 *         val elements = matchVal.first
 *         val last = matchVal.second
 *         check(elements == listOf(0))
 *         check(last == listOf(1, 2))
 *     }
 * }
 * check(null == x.splitLastChunk(4))
 * ```
 */
public fun <T> List<T>.splitLastChunk(n: Int = 0): Pair<List<T>, List<T>>? {
    val index = this.size.checkedSub(n) ?: return null
    val (init, last) = this.splitAt(index)
    // SAFETY: We explicitly check for the correct number of elements,
    // and do not let the references outlive the slice.
    return Pair(init, (last.asPtr().castArray()))
}

/**
 * Returns a mutable array reference to the last `N` items in the slice and the remaining
 * slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.splitLastChunkMut(2)) {
 *     null -> {}
 *     else -> {
 *         val elements = matchVal.first
 *         val last = matchVal.second
 *         last[0] = 3
 *         last[1] = 4
 *         elements[0] = 5
 *     }
 * }
 * check((x).contentEquals(listOf(5, 3, 4)))
 * check(null == x.splitLastChunkMut(4))
 * ```
 */
public fun <T> List<T>.splitLastChunkMut(n: Int = 0): Pair<MutableList<T>, List<T>>? {
    val index = this.size.checkedSub(n) ?: return null
    val (init, last) = this.splitAtMut(index)
    // SAFETY: We explicitly check for the correct number of elements,
    // do not let the reference outlive the slice,
    // and enforce exclusive mutability of the chunk by the split.
    return Pair(init, (last.asMutPtr().castArray()))
}

/**
 * Returns an array reference to the last `N` items in the slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val u = listOf(10, 40, 30)
 * check(listOf(40, 30) == u.lastChunk(2))
 * val v: List<Int> = listOf(10)
 * check(null == v.lastChunk(2))
 * val w: List<Int> = listOf()
 * check(listOf() == w.lastChunk(0))
 * ```
 */
public fun <T> List<T>.lastChunk(n: Int = 0): List<T>? {
    // Note(const-Note): Without const traits, we need this instead of `get`.
    val index = this.size.checkedSub(n) ?: return null
    val (_, last) = this.splitAt(index)
    // SAFETY: We explicitly check for the correct number of elements,
    // and do not let the references outlive the slice.
    return (last.asPtr().castArray())
}

/**
 * Returns a mutable array reference to the last `N` items in the slice.
 *
 * If the slice is not at least `N` in length, this will return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.lastChunkMut(2)) {
 *     null -> {}
 *     else -> {
 *         val last = matchVal
 *         last[0] = 10
 *         last[1] = 20
 *     }
 * }
 * check((x).contentEquals(listOf(0, 10, 20)))
 * check(null == x.lastChunkMut(4))
 * ```
 */
public fun <T> List<T>.lastChunkMut(n: Int = 0): List<T>? {
    // Note(const-Note): Without const traits, we need this instead of `get`.
    val index = this.size.checkedSub(n) ?: return null
    val (_, last) = this.splitAtMut(index)
    // SAFETY: We explicitly check for the correct number of elements,
    // do not let the reference outlive the slice,
    // and require exclusive access to the entire slice to mutate the chunk.
    return (last.asMutPtr().castArray())
}

/**
 * Returns a reference to an element or subslice depending on the type of
 * index.
 *
 * - If given a position, returns a reference to the element at that
 *   position or `None` if out of bounds.
 * - If given a range, returns the subslice corresponding to that range,
 *   or `None` if out of bounds.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(40 == v.get(1))
 * check(listOf(10, 40) == v.get(0 until 2))
 * check(null == v.get(3))
 * check(null == v.get(0 until 4))
 * ```
 */
public fun <Output, T, I> List<T>.get(index: I): Output? where I : SliceIndex<List<T>, Output> = index.get(this)

/**
 * Returns a mutable reference to an element or subslice depending on the
 * type of index (see [get]) or `None` if the index is out of bounds.
 *
 * [get]: slice.get
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(0, 1, 2)
 * when (val matchVal = x.getMut(1)) {
 *     null -> {}
 *     else -> {
 *         val elem = matchVal
 *         elem = 42
 *     }
 * }
 * check((x).contentEquals(listOf(0, 42, 2)))
 * ```
 */
public fun <Output, T, I> List<T>.getMut(index: I): Output? where I : SliceIndex<List<T>, Output> = index.getMut(this)

/**
 * Returns a reference to an element or subslice, without doing bounds
 * checking.
 *
 * For a safe alternative see [get].
 *
 * # Safety
 *
 * Calling this method with an out-of-bounds index is *[undefined behavior]*
 * even if the resulting reference is not used.
 *
 * You can think of this like `.get(index).unwrapUnchecked()`.  It's UB
 * to call `.getUnchecked(len)`, even if you immediately convert to a
 * pointer.  And it's UB to call `.getUnchecked(.. len + 1)`,
 * `.getUnchecked(..= len)`, or similar.
 *
 * [get]: slice.get
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(1, 2, 4)
 * run {
 *     check(x.getUnchecked(1) == 2)
 * }
 * ```
 */
public fun <Output, T, I> List<T>.getUnchecked(index: I): Output where I : SliceIndex<List<T>, Output> {
    // SAFETY: the caller must uphold most of the safety requirements for `getUnchecked`;
    // the slice is dereferenceable because `self` is a safe reference.
    // The returned pointer is safe because impls of `SliceIndex` have to guarantee that it is.
    return index.getUnchecked(this)
}

/**
 * Returns a mutable reference to an element or subslice, without doing
 * bounds checking.
 *
 * For a safe alternative see [getMut].
 *
 * # Safety
 *
 * Calling this method with an out-of-bounds index is *[undefined behavior]*
 * even if the resulting reference is not used.
 *
 * You can think of this like `.getMut(index).unwrapUnchecked()`.  It's
 * UB to call `.getUncheckedMut(len)`, even if you immediately convert
 * to a pointer.  And it's UB to call `.getUncheckedMut(.. len + 1)`,
 * `.getUncheckedMut(..= len)`, or similar.
 *
 * [getMut]: slice.getMut
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(1, 2, 4)
 * run {
 *     val elem = x.getUncheckedMut(1)
 *     elem = 13
 * }
 * check((x).contentEquals(listOf(1, 13, 4)))
 * ```
 */
public fun <Output, T, I> List<T>.getUncheckedMut(index: I): Output where I : SliceIndex<List<T>, Output> {
    // SAFETY: the caller must uphold the safety requirements for `getUncheckedMut`;
    // the slice is dereferenceable because `self` is a safe reference.
    // The returned pointer is safe because impls of `SliceIndex` have to guarantee that it is.
    return index.getUncheckedMut(this)
}

/**
 * Returns a raw pointer to the slice's buffer.
 *
 * The caller must ensure that the slice outlives the pointer this
 * function returns, or else it will end up dangling.
 *
 * The caller must also ensure that the memory the pointer (non-transitively) points to
 * is never written to (except inside an `UnsafeCell`) using this pointer or any pointer
 * derived from it. If you need to mutate the contents of the slice, use [asMutPtr].
 *
 * Modifying the container referenced by this slice may cause its buffer
 * to be reallocated, which would also make any pointers to it invalid.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val x = listOf(1, 2, 4)
 * val xPtr = x.asPtr()
 * for (i in 0 until x.size) {
 *     check(x.getUnchecked(i) == (PrimitiveLayouts.I32).readNative((ConstPtr((xPtr).rawAddress + ((i).toLong() * (PrimitiveLayouts.I32).strideBytes))).rawAddress))
 * }
 * ```
 *
 * [asMutPtr]: slice.asMutPtr
 */
public fun <T> List<T>.asPtr(): ConstPtr = (constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Returns an unchecked mutable pointer to the slice's buffer.
 *
 * The caller must ensure that the slice outlives the pointer this
 * function returns, or else it will end up dangling.
 *
 * Modifying the container referenced by this slice may cause its buffer
 * to be reallocated, which would also make any pointers to it invalid.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val x = listOf(1, 2, 4)
 * val xPtr = x.asMutPtr()
 * for (i in 0 until x.size) {
 *     (MutPtr((xPtr).rawAddress + ((i).toLong() * (PrimitiveLayouts.I32).strideBytes))).let { targetPtr -> val targetLayout = PrimitiveLayouts.I32; var currentValue = targetLayout.readNative(targetPtr.rawAddress); currentValue += (2); targetLayout.writeNative(targetPtr.rawAddress, currentValue) }
 * }
 * check((x).contentEquals(listOf(3, 4, 6)))
 * ```
 */
public fun <T> List<T>.asMutPtr(): MutPtr = (mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Returns the two raw pointers spanning the slice.
 *
 * The returned range is half-open, which means that the end pointer
 * points *one past* the last element of the slice. This way, an empty
 * slice is represented by two equal pointers, and the difference between
 * the two pointers represents the size of the slice.
 *
 * See [asPtr] for warnings on using these pointers. The end pointer
 * requires extra caution, as it does not point to a valid element in the
 * slice.
 *
 * This function is useful for interacting with foreign interfaces which
 * use two pointers to refer to a range of elements in memory, as is
 * common in C++.
 *
 * It can also be useful to check if a pointer to an element refers to an
 * element of this slice:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 *
 * val a = listOf(1, 2, 3)
 * val x = constPtrFromRustCast(a[1])
 * val y = constPtrFromRustCast(5)
 * check(a.asPtrRange().contains(x))
 * check(!a.asPtrRange().contains(y))
 * ```
 *
 * [asPtr]: slice.asPtr
 */
public fun <T> List<T>.asPtrRange(): io.github.kotlinmania.rustlang.core.range.Range<ConstPtr> {
    val start = this.asPtr()
    // SAFETY: The `add` here is safe, because:
    //
    // - Both pointers are part of the same object, as pointing directly
    // past the object also counts.
    //
    // - The size of the slice is never larger than `Long.MAX` bytes, as
    // noted here:
    // - https://github.com/rust-lang/unchecked-code-guidelines/issues/102#issuecomment-473340447
    // - https://doc.rust-lang.org/reference/behavior-considered-undefined.html
    // - https://doc.rust-lang.org/core/slice/fn.fromRawParts.html#safety
    // (This doesn't seem normative yet, but the very same assumption is
    // made in many places, including the Index implementation of slices.)
    //
    // - There is no wrapping around involved, as slices do not wrap past
    // the end of the address space.
    //
    // See the documentation of [pointer.add].
    val end = ConstPtr((start).rawAddress + ((this.size).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
    return start until end
}

/**
 * Returns the two unchecked mutable pointers spanning the slice.
 *
 * The returned range is half-open, which means that the end pointer
 * points *one past* the last element of the slice. This way, an empty
 * slice is represented by two equal pointers, and the difference between
 * the two pointers represents the size of the slice.
 *
 * See [asMutPtr] for warnings on using these pointers. The end
 * pointer requires extra caution, as it does not point to a valid element
 * in the slice.
 *
 * This function is useful for interacting with foreign interfaces which
 * use two pointers to refer to a range of elements in memory, as is
 * common in C++.
 *
 * [asMutPtr]: slice.asMutPtr
 */
public fun <T> List<T>.asMutPtrRange(): io.github.kotlinmania.rustlang.core.range.Range<MutPtr> {
    val start = this.asMutPtr()
    // SAFETY: See asPtrRange() above for why `add` here is safe.
    val end = MutPtr((start).rawAddress + ((this.size).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
    return start until end
}

/**
 * Gets a reference to the underlying array.
 *
 * If `N` is not exactly equal to the length of `self`, then this method returns `None`.
 */
public fun <T> List<T>.asArray(n: Int = 0): List<T>? =
    if (this.size == n) {
        val ptr = this.asPtr().castArray()
        // SAFETY: The underlying array of a slice can be reinterpreted as an actual array `[T; N]` if `N` is not greater than the slice's length.
        val me = ptr
        me
    } else {
        null
    }

/**
 * Gets a mutable reference to the slice's underlying array.
 *
 * If `N` is not exactly equal to the length of `self`, then this method returns `None`.
 */
public fun <T> List<T>.asMutArray(n: Int = 0): List<T>? =
    if (this.size == n) {
        val ptr = this.asMutPtr().castArray()
        // SAFETY: The underlying array of a slice can be reinterpreted as an actual array `[T; N]` if `N` is not greater than the slice's length.
        val me = ptr
        me
    } else {
        null
    }

/**
 * Swaps two elements in the slice.
 *
 * If `a` equals `b`, it's guaranteed that elements won't change value.
 *
 * # Arguments
 *
 * * a - The index of the first element
 * * b - The index of the second element
 *
 * # Panics
 *
 * Panics if `a` or `b` are out of bounds.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf("a", "b", "c", "d", "e")
 * v.swap(2, 4)
 * check(v == listOf("a", "b", "e", "d", "c"))
 * ```
 */
public fun <T> List<T>.swap(a: Int, b: Int) {
    // Bounds checks that panic exactly like indexing would.
    this[a]
    this[b]
    // SAFETY: `a` and `b` were checked to be in bounds above.
    run {
        this.swapUnchecked(a, b)
    }
}

/**
 * Swaps two elements in the slice, without doing bounds checking.
 *
 * For a safe alternative see [swap].
 *
 * # Arguments
 *
 * * a - The index of the first element
 * * b - The index of the second element
 *
 * # Safety
 *
 * Calling this method with an out-of-bounds index is *[undefined behavior]*.
 * The caller has to ensure that `a<self.len()` and `b<self.len()`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(sliceSwapUnchecked)
 * public fun doctestEntry() {
 *     var v = mutableListOf("a", "b", "c", "d")
 *     // SAFETY: we know that 1 and 3 are both indices of the slice
 *     v.swapUnchecked(1, 3)
 *     check(v == listOf("a", "d", "c", "b"))
 * }
 * ```
 *
 * [swap]: slice.swap
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 */
public fun <T> List<T>.swapUnchecked(a: Int, b: Int) {
    Unit
    val ptr = this.asMutPtr()
    // SAFETY: caller has to guarantee that `a<self.len()` and `b<self.len()`
    run {
        swap(MutPtr((ptr).rawAddress + ((a).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), MutPtr((ptr).rawAddress + ((b).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)))
    }
}

/**
 * Reverses the order of elements in the slice, in place.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(1, 2, 3)
 * v.reverse()
 * check(v == listOf(3, 2, 1))
 * ```
 */
public fun <T> List<T>.reverse() {
    fun <T> revswap(a: MutableList<T>, b: MutableList<T>, n: Int) {
        if (false) {
            check(a.size == n)
        }
        if (false) {
            check(b.size == n)
        }
        // Because this function is first compiled in isolation,
        // this check tells LLVM that the indexing below is
        // in-bounds. Then after inlining -- once the actual
        // lengths of the slices are known -- it's removed.
        // Note(constTraitImpl) replace with let (a, b) = (a[..n], b[..n]);
        val (a1, _) = a.splitAtMut(n)
        val (b1, _) = b.splitAtMut(n)
        var i = 0
        while (i < n) {
            io.github.kotlinmania.rustlang.core.mem.swap(a1[i], b1[n - 1 - i])
            i += 1
        }
    }
    val halfLen = this.size / 2
    val structVal = this.asMutPtrRange()
    val start = structVal.start
    val end = structVal.end
    // These slices will skip the middle item for an odd length,
    // since that one doesn't need to move.
    val (frontHalf, backHalf) = Pair(linearMutSliceView(start, halfLen.toInt(), PrimitiveLayouts.U8), linearMutSliceView(end.sub(halfLen), halfLen.toInt(), PrimitiveLayouts.U8))
    // Introducing a function boundary here means that the two halves
    // get `noalias` markers, allowing better optimization as LLVM
    // knows that they're disjoint, unlike in the original slice.
    revswap(frontHalf, backHalf, halfLen)
}

/**
 * Returns an iterator over the slice.
 *
 * The iterator yields all items from start to end.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(1, 2, 4)
 * var iterator = x.iter()
 * check(iterator.next() == 1)
 * check(iterator.next() == 2)
 * check(iterator.next() == 4)
 * check(iterator.next() == null)
 * ```
 */
public fun <T> List<T>.iter(): Iter<T> = Iter.new(this)

/**
 * Returns an iterator that allows modifying each value.
 *
 * The iterator yields all items from start to end.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(1, 2, 4)
 * for (idx in x.indices) {
 *     x[idx] += 2
 * }
 * check((x).contentEquals(listOf(3, 4, 6)))
 * ```
 */
public fun <T> List<T>.iterMut(): IterMut<T> = IterMut.new(this)

/**
 * Returns an iterator over all contiguous windows of length
 * `size`. The windows overlap. If the slice is shorter than
 * `size`, the iterator returns no values.
 *
 * # Panics
 *
 * Panics if `size` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.windows(3)
 * check(iter.next()!! == listOf('l', 'o', 'r'))
 * check(iter.next()!! == listOf('o', 'r', 'e'))
 * check(iter.next()!! == listOf('r', 'e', 'm'))
 * check(iter.next() == null)
 * ```
 *
 * If the slice is shorter than `size`:
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('f', 'o', 'o')
 * var iter = slice.windows(4)
 * check(iter.next() == null)
 * ```
 *
 * Because the [Iterator] trait cannot represent the required scopes,
 * there is no `windowsMut` analog to `windows`;
 * `[0, 1, 2].windowsMut(2).collect()` would violate [the rules of references]
 * (though a [LendingIterator] analog is possible). You can sometimes use
 * [`Cell.asSliceOfCells`](cell.Cell.asSliceOfCells) in
 * conjunction with `windows` instead:
 *
 * [the rules of references]: https://doc.rust-lang.org/book/ch04-02-references-and-borrowing.html#the-rules-of-references
 * [LendingIterator]: https://blog.rust-lang.org/2022/10/28/gats-stabilization.html
 * ```kotlin
 * import slice
 * import std.cell.Cell
 *
 * var array = mutableListOf('R', 'u', 's', 't', ' ', '2', '0', '1', '5')
 * val slice = array
 * val sliceOfCells: List<Cell<Char>> = Cell.fromMut(slice).asSliceOfCells()
 * for (w in sliceOfCells.windows(3)) {
 *     Cell.swap(w[0], w[2])
 * }
 * check(array == listOf('s', 't', ' ', '2', '0', '1', '5', 'u', 'R'))
 * ```
 */
public fun <T> List<T>.windows(size: Int): Windows<T> {
    val size1 = (NonZero.new<NonZeroI32Inner, Int>(size) ?: error("window size must be non-zero"))
    return Windows.new(this, size1)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the
 * beginning of the slice.
 *
 * The chunks are slices and do not overlap. If `chunkSize` does not divide the length of the
 * slice, then the last chunk will not have length `chunkSize`.
 *
 * See [chunksExact] for a variant of this iterator that returns chunks of always exactly
 * `chunkSize` elements, and [rchunks] for the same iterator but starting at the end of the
 * slice.
 *
 * If your `chunkSize` is a constant, consider using [asChunks] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.chunks(2)
 * check(iter.next()!! == listOf('l', 'o'))
 * check(iter.next()!! == listOf('r', 'e'))
 * check(iter.next()!! == listOf('m'))
 * check(iter.next() == null)
 * ```
 *
 * [chunksExact]: slice.chunksExact
 * [rchunks]: slice.rchunks
 * [asChunks]: slice.asChunks
 */
public fun <T> List<T>.chunks(chunkSize: Int): Chunks<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return Chunks.new(this, chunkSize)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the
 * beginning of the slice.
 *
 * The chunks are mutable slices, and do not overlap. If `chunkSize` does not divide the
 * length of the slice, then the last chunk will not have length `chunkSize`.
 *
 * See [chunksExactMut] for a variant of this iterator that returns chunks of always
 * exactly `chunkSize` elements, and [rchunksMut] for the same iterator but starting at
 * the end of the slice.
 *
 * If your `chunkSize` is a constant, consider using [asChunksMut] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(0, 0, 0, 0, 0)
 * var count = 1
 * for (chunk in v.chunksMut(2)) {
 *     for (idx in chunk.indices) {
 *         chunk[idx] += count
 *     }
 *     count += 1
 * }
 * check((v).contentEquals(listOf(1, 1, 2, 2, 3)))
 * ```
 *
 * [chunksExactMut]: slice.chunksExactMut
 * [rchunksMut]: slice.rchunksMut
 * [asChunksMut]: slice.asChunksMut
 */
public fun <T> List<T>.chunksMut(chunkSize: Int): ChunksMut<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return ChunksMut.new(this, chunkSize)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the
 * beginning of the slice.
 *
 * The chunks are slices and do not overlap. If `chunkSize` does not divide the length of the
 * slice, then the last up to `chunkSize - 1` elements will be omitted and can be retrieved
 * from the `remainder` function of the iterator.
 *
 * Due to each chunk having exactly `chunkSize` elements, the compiler can often optimize the
 * resulting code better than in the case of [chunks].
 *
 * See [chunks] for a variant of this iterator that also returns the remainder as a smaller
 * chunk, and [rchunksExact] for the same iterator but starting at the end of the slice.
 *
 * If your `chunkSize` is a constant, consider using [asChunks] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.chunksExact(2)
 * check(iter.next()!! == listOf('l', 'o'))
 * check(iter.next()!! == listOf('r', 'e'))
 * check(iter.next() == null)
 * check(iter.remainder() == listOf('m'))
 * ```
 *
 * [chunks]: slice.chunks
 * [rchunksExact]: slice.rchunksExact
 * [asChunks]: slice.asChunks
 */
public fun <T> List<T>.chunksExact(chunkSize: Int): ChunksExact<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return ChunksExact.new(this, chunkSize)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the
 * beginning of the slice.
 *
 * The chunks are mutable slices, and do not overlap. If `chunkSize` does not divide the
 * length of the slice, then the last up to `chunkSize - 1` elements will be omitted and can be
 * retrieved from the `intoRemainder` function of the iterator.
 *
 * Due to each chunk having exactly `chunkSize` elements, the compiler can often optimize the
 * resulting code better than in the case of [chunksMut].
 *
 * See [chunksMut] for a variant of this iterator that also returns the remainder as a
 * smaller chunk, and [rchunksExactMut] for the same iterator but starting at the end of
 * the slice.
 *
 * If your `chunkSize` is a constant, consider using [asChunksMut] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(0, 0, 0, 0, 0)
 * var count = 1
 * for (chunk in v.chunksExactMut(2)) {
 *     for (idx in chunk.indices) {
 *         chunk[idx] += count
 *     }
 *     count += 1
 * }
 * check((v).contentEquals(listOf(1, 1, 2, 2, 0)))
 * ```
 *
 * [chunksMut]: slice.chunksMut
 * [rchunksExactMut]: slice.rchunksExactMut
 * [asChunksMut]: slice.asChunksMut
 */
public fun <T> List<T>.chunksExactMut(chunkSize: Int): ChunksExactMut<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return ChunksExactMut.new(this, chunkSize)
}

/**
 * Splits the slice into a slice of `N`-element arrays,
 * assuming that there's no remainder.
 *
 * This is the inverse operation to [asFlattened].
 *
 * [asFlattened]: slice.asFlattened
 *
 * As this is `unchecked`, consider whether you could use [asChunks] or
 * [asRchunks] instead, perhaps via something like
 * `if val (chunks, []) = slice.asChunks()` or
 * `val (chunks, []) = slice.asChunks() else ( unreachable() );`.
 *
 * [asChunks]: slice.asChunks
 * [asRchunks]: slice.asRchunks
 *
 * # Safety
 *
 * This may only be called when
 * - The slice splits exactly into `N`-element chunks (aka `self.len() % N == 0`).
 * - `N != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice: List<Char> = listOf('l', 'o', 'r', 'e', 'm', '!')
 * val chunks: List<List<Char>> = slice.asChunksUnchecked()
 * check(chunks == listOf(listOf('l'), listOf('o'), listOf('r'), listOf('e'), listOf('m'), listOf('!')))
 * val chunks1: List<List<Char>> = slice.asChunksUnchecked()
 * check(chunks1 == listOf(listOf('l', 'o', 'r'), listOf('e', 'm', '!')))
 * // These would be unsound:
 * // let chunks: &[[_; 5]] = slice.asChunksUnchecked() // The slice length is not a multiple of 5
 * // let chunks: &[[_; 0]] = slice.asChunksUnchecked() // Zero-length chunks are never allowed
 * ```
 */
public fun <T> List<T>.asChunksUnchecked(n: Int = 0): List<List<T>> {
    Unit
    // SAFETY: Caller must guarantee that `N` is nonzero and exactly divides the slice length
    val newLen = exactDiv(this.size, n)
    // SAFETY: We cast a slice of `newLen * N` elements into
    // a slice of `newLen` many `N` elements chunks.
    return linearSliceView(ConstPtr((this.asPtr()).rawAddress), newLen.toInt(), PrimitiveLayouts.U8)
}

/**
 * Splits the slice into a slice of `N`-element arrays,
 * starting at the beginning of the slice,
 * and a remainder slice with length strictly less than `N`.
 *
 * The remainder is meaningful in the division sense.  Given
 * `val (chunks, remainder) = slice.asChunks()`, then:
 * - `chunks.len()` equals `slice.len() / N`,
 * - `remainder.len()` equals `slice.len() % N`, and
 * - `slice.len()` equals `chunks.len() * N + remainder.len()`.
 *
 * You can flatten the chunks back into a slice-of-`T` with [asFlattened].
 *
 * [asFlattened]: slice.asFlattened
 *
 * # Panics
 *
 * Panics if `N` is zero.
 *
 * Note that this check is against a const generic parameter, not a runtime
 * value, and thus a particular monomorphization will either always panic
 * or it will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * val (chunks, remainder) = slice.asChunks()
 * check(chunks == listOf(listOf('l', 'o'), listOf('r', 'e')))
 * check(remainder == listOf('m'))
 * ```
 *
 * If you expect the slice to be an exact multiple, you can combine
 * `val`-`else` with an empty slice pattern:
 * ```kotlin
 * import slice
 *
 * val slice = listOf('R', 'u', 's', 't')
 * val (chunks, _) = slice.asChunks(2) ?: error("slice didn't have even length")
 * check(chunks == listOf(listOf('R', 'u'), listOf('s', 't')))
 * ```
 */
public fun <T> List<T>.asChunks(n: Int = 0): Pair<List<List<T>>, List<T>> {
    check(n != 0) { "chunk size must be non-zero" }
    val lenRoundedDown = this.size / n * n
    // SAFETY: The rounded-down value is always the same or smaller than the
    // original length, and thus must be in-bounds of the slice.
    val (multipleOfN, remainder) = this.splitAtUnchecked(lenRoundedDown)
    // SAFETY: We already panicked for zero, and ensured by construction
    // that the length of the subslice is a multiple of N.
    val arraySlice = multipleOfN.asChunksUnchecked()
    return Pair(arraySlice, remainder)
}

/**
 * Splits the slice into a slice of `N`-element arrays,
 * starting at the end of the slice,
 * and a remainder slice with length strictly less than `N`.
 *
 * The remainder is meaningful in the division sense.  Given
 * `val (remainder, chunks) = slice.asRchunks()`, then:
 * - `remainder.len()` equals `slice.len() % N`,
 * - `chunks.len()` equals `slice.len() / N`, and
 * - `slice.len()` equals `chunks.len() * N + remainder.len()`.
 *
 * You can flatten the chunks back into a slice-of-`T` with [asFlattened].
 *
 * [asFlattened]: slice.asFlattened
 *
 * # Panics
 *
 * Panics if `N` is zero.
 *
 * Note that this check is against a const generic parameter, not a runtime
 * value, and thus a particular monomorphization will either always panic
 * or it will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * val (remainder, chunks) = slice.asRchunks()
 * check(remainder == listOf('l'))
 * check(chunks == listOf(listOf('o', 'r'), listOf('e', 'm')))
 * ```
 */
public fun <T> List<T>.asRchunks(n: Int = 0): Pair<List<T>, List<List<T>>> {
    check(n != 0) { "chunk size must be non-zero" }
    val len = this.size / n
    val (remainder, multipleOfN) = this.splitAt(this.size - len * n)
    // SAFETY: We already panicked for zero, and ensured by construction
    // that the length of the subslice is a multiple of N.
    val arraySlice = multipleOfN.asChunksUnchecked()
    return Pair(remainder, arraySlice)
}

/**
 * Splits the slice into a slice of `N`-element arrays,
 * assuming that there's no remainder.
 *
 * This is the inverse operation to [asFlattenedMut].
 *
 * [asFlattenedMut]: slice.asFlattenedMut
 *
 * As this is `unchecked`, consider whether you could use [asChunksMut] or
 * [asRchunksMut] instead, perhaps via something like
 * `if val (chunks, []) = slice.asChunksMut()` or
 * `val (chunks, []) = slice.asChunksMut() else ( unreachable() );`.
 *
 * [asChunksMut]: slice.asChunksMut
 * [asRchunksMut]: slice.asRchunksMut
 *
 * # Safety
 *
 * This may only be called when
 * - The slice splits exactly into `N`-element chunks (aka `self.len() % N == 0`).
 * - `N != 0`.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice: MutableList<Char> = mutableListOf('l', 'o', 'r', 'e', 'm', '!')
 * val chunks: MutableList<List<Char>> = slice.asChunksUncheckedMut()
 * chunks[0] = listOf('L')
 * check(chunks == listOf(listOf('L'), listOf('o'), listOf('r'), listOf('e'), listOf('m'), listOf('!')))
 * val chunks1: MutableList<List<Char>> = slice.asChunksUncheckedMut()
 * chunks1[1] = listOf('a', 'x', '?')
 * check(slice == listOf('L', 'o', 'r', 'a', 'x', '?'))
 * // These would be unsound:
 * // let chunks: &[[_; 5]] = slice.asChunksUncheckedMut() // The slice length is not a multiple of 5
 * // let chunks: &[[_; 0]] = slice.asChunksUncheckedMut() // Zero-length chunks are never allowed
 * ```
 */
public fun <T> List<T>.asChunksUncheckedMut(n: Int = 0): MutableList<List<T>> {
    Unit
    // SAFETY: Caller must guarantee that `N` is nonzero and exactly divides the slice length
    val newLen = exactDiv(this.size, n)
    // SAFETY: We cast a slice of `newLen * N` elements into
    // a slice of `newLen` many `N` elements chunks.
    return linearMutSliceView(MutPtr((this.asMutPtr()).rawAddress), newLen.toInt(), PrimitiveLayouts.U8)
}

/**
 * Splits the slice into a slice of `N`-element arrays,
 * starting at the beginning of the slice,
 * and a remainder slice with length strictly less than `N`.
 *
 * The remainder is meaningful in the division sense.  Given
 * `val (chunks, remainder) = slice.asChunksMut()`, then:
 * - `chunks.len()` equals `slice.len() / N`,
 * - `remainder.len()` equals `slice.len() % N`, and
 * - `slice.len()` equals `chunks.len() * N + remainder.len()`.
 *
 * You can flatten the chunks back into a slice-of-`T` with [asFlattenedMut].
 *
 * [asFlattenedMut]: slice.asFlattenedMut
 *
 * # Panics
 *
 * Panics if `N` is zero.
 *
 * Note that this check is against a const generic parameter, not a runtime
 * value, and thus a particular monomorphization will either always panic
 * or it will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(0, 0, 0, 0, 0)
 * var count = 1
 * val (chunks, remainder) = v.asChunksMut()
 * remainder[0] = 9
 * for (chunk in chunks) {
 *     chunk = List(2) { count }
 *     count += 1
 * }
 * check((v).contentEquals(listOf(1, 1, 2, 2, 9)))
 * ```
 */
public fun <T> List<T>.asChunksMut(n: Int = 0): Pair<MutableList<List<T>>, MutableList<T>> {
    check(n != 0) { "chunk size must be non-zero" }
    val lenRoundedDown = this.size / n * n
    // SAFETY: The rounded-down value is always the same or smaller than the
    // original length, and thus must be in-bounds of the slice.
    val (multipleOfN, remainder) = this.splitAtMutUnchecked(lenRoundedDown)
    // SAFETY: We already panicked for zero, and ensured by construction
    // that the length of the subslice is a multiple of N.
    val arraySlice = multipleOfN.asChunksUncheckedMut()
    return Pair(arraySlice, remainder)
}

/**
 * Splits the slice into a slice of `N`-element arrays,
 * starting at the end of the slice,
 * and a remainder slice with length strictly less than `N`.
 *
 * The remainder is meaningful in the division sense.  Given
 * `val (remainder, chunks) = slice.asRchunksMut()`, then:
 * - `remainder.len()` equals `slice.len() % N`,
 * - `chunks.len()` equals `slice.len() / N`, and
 * - `slice.len()` equals `chunks.len() * N + remainder.len()`.
 *
 * You can flatten the chunks back into a slice-of-`T` with [asFlattenedMut].
 *
 * [asFlattenedMut]: slice.asFlattenedMut
 *
 * # Panics
 *
 * Panics if `N` is zero.
 *
 * Note that this check is against a const generic parameter, not a runtime
 * value, and thus a particular monomorphization will either always panic
 * or it will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(0, 0, 0, 0, 0)
 * var count = 1
 * val (remainder, chunks) = v.asRchunksMut()
 * remainder[0] = 9
 * for (chunk in chunks) {
 *     chunk = List(2) { count }
 *     count += 1
 * }
 * check((v).contentEquals(listOf(9, 1, 1, 2, 2)))
 * ```
 */
public fun <T> List<T>.asRchunksMut(n: Int = 0): Pair<MutableList<T>, MutableList<List<T>>> {
    check(n != 0) { "chunk size must be non-zero" }
    val len = this.size / n
    val (remainder, multipleOfN) = this.splitAtMut(this.size - len * n)
    // SAFETY: We already panicked for zero, and ensured by construction
    // that the length of the subslice is a multiple of N.
    val arraySlice = multipleOfN.asChunksUncheckedMut()
    return Pair(remainder, arraySlice)
}

/**
 * Returns an iterator over overlapping windows of `N` elements of a slice,
 * starting at the beginning of the slice.
 *
 * This is the const generic equivalent of [windows].
 *
 * If `N` is greater than the size of the slice, it will return no windows.
 *
 * # Panics
 *
 * Panics if `N` is zero.
 *
 * Note that this check is against a const generic parameter, not a runtime
 * value, and thus a particular monomorphization will either always panic
 * or it will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(0, 1, 2, 3)
 * var iter = slice.arrayWindows()
 * check(iter.next()!! == listOf(0, 1))
 * check(iter.next()!! == listOf(1, 2))
 * check(iter.next()!! == listOf(2, 3))
 * check(iter.next() == null)
 * ```
 *
 * [windows]: slice.windows
 */
public fun <T> List<T>.arrayWindows(n: Int = 0): ArrayWindows<T> {
    check(n != 0) { "window size must be non-zero" }
    return ArrayWindows.new(this, n)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the end
 * of the slice.
 *
 * The chunks are slices and do not overlap. If `chunkSize` does not divide the length of the
 * slice, then the last chunk will not have length `chunkSize`.
 *
 * See [rchunksExact] for a variant of this iterator that returns chunks of always exactly
 * `chunkSize` elements, and [chunks] for the same iterator but starting at the beginning
 * of the slice.
 *
 * If your `chunkSize` is a constant, consider using [asRchunks] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.rchunks(2)
 * check(iter.next()!! == listOf('e', 'm'))
 * check(iter.next()!! == listOf('o', 'r'))
 * check(iter.next()!! == listOf('l'))
 * check(iter.next() == null)
 * ```
 *
 * [rchunksExact]: slice.rchunksExact
 * [chunks]: slice.chunks
 * [asRchunks]: slice.asRchunks
 */
public fun <T> List<T>.rchunks(chunkSize: Int): RChunks<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return RChunks.new(this, chunkSize)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the end
 * of the slice.
 *
 * The chunks are mutable slices, and do not overlap. If `chunkSize` does not divide the
 * length of the slice, then the last chunk will not have length `chunkSize`.
 *
 * See [rchunksExactMut] for a variant of this iterator that returns chunks of always
 * exactly `chunkSize` elements, and [chunksMut] for the same iterator but starting at the
 * beginning of the slice.
 *
 * If your `chunkSize` is a constant, consider using [asRchunksMut] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(0, 0, 0, 0, 0)
 * var count = 1
 * for (chunk in v.rchunksMut(2)) {
 *     for (idx in chunk.indices) {
 *         chunk[idx] += count
 *     }
 *     count += 1
 * }
 * check((v).contentEquals(listOf(3, 2, 2, 1, 1)))
 * ```
 *
 * [rchunksExactMut]: slice.rchunksExactMut
 * [chunksMut]: slice.chunksMut
 * [asRchunksMut]: slice.asRchunksMut
 */
public fun <T> List<T>.rchunksMut(chunkSize: Int): RChunksMut<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return RChunksMut.new(this, chunkSize)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the
 * end of the slice.
 *
 * The chunks are slices and do not overlap. If `chunkSize` does not divide the length of the
 * slice, then the last up to `chunkSize - 1` elements will be omitted and can be retrieved
 * from the `remainder` function of the iterator.
 *
 * Due to each chunk having exactly `chunkSize` elements, the compiler can often optimize the
 * resulting code better than in the case of [rchunks].
 *
 * See [rchunks] for a variant of this iterator that also returns the remainder as a smaller
 * chunk, and [chunksExact] for the same iterator but starting at the beginning of the
 * slice.
 *
 * If your `chunkSize` is a constant, consider using [asRchunks] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf('l', 'o', 'r', 'e', 'm')
 * var iter = slice.rchunksExact(2)
 * check(iter.next()!! == listOf('e', 'm'))
 * check(iter.next()!! == listOf('o', 'r'))
 * check(iter.next() == null)
 * check(iter.remainder() == listOf('l'))
 * ```
 *
 * [chunks]: slice.chunks
 * [rchunks]: slice.rchunks
 * [chunksExact]: slice.chunksExact
 * [asRchunks]: slice.asRchunks
 */
public fun <T> List<T>.rchunksExact(chunkSize: Int): RChunksExact<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return RChunksExact.new(this, chunkSize)
}

/**
 * Returns an iterator over `chunkSize` elements of the slice at a time, starting at the end
 * of the slice.
 *
 * The chunks are mutable slices, and do not overlap. If `chunkSize` does not divide the
 * length of the slice, then the last up to `chunkSize - 1` elements will be omitted and can be
 * retrieved from the `intoRemainder` function of the iterator.
 *
 * Due to each chunk having exactly `chunkSize` elements, the compiler can often optimize the
 * resulting code better than in the case of [chunksMut].
 *
 * See [rchunksMut] for a variant of this iterator that also returns the remainder as a
 * smaller chunk, and [chunksExactMut] for the same iterator but starting at the beginning
 * of the slice.
 *
 * If your `chunkSize` is a constant, consider using [asRchunksMut] instead, which will
 * give references to arrays of exactly that length, rather than slices.
 *
 * # Panics
 *
 * Panics if `chunkSize` is zero.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(0, 0, 0, 0, 0)
 * var count = 1
 * for (chunk in v.rchunksExactMut(2)) {
 *     for (idx in chunk.indices) {
 *         chunk[idx] += count
 *     }
 *     count += 1
 * }
 * check((v).contentEquals(listOf(0, 2, 2, 1, 1)))
 * ```
 *
 * [chunksMut]: slice.chunksMut
 * [rchunksMut]: slice.rchunksMut
 * [chunksExactMut]: slice.chunksExactMut
 * [asRchunksMut]: slice.asRchunksMut
 */
public fun <T> List<T>.rchunksExactMut(chunkSize: Int): RChunksExactMut<T> {
    check(chunkSize != 0) { "chunk size must be non-zero" }
    return RChunksExactMut.new(this, chunkSize)
}

/**
 * Returns an iterator over the slice producing non-overlapping runs
 * of elements using the predicate to separate them.
 *
 * The predicate is called for every pair of consecutive elements,
 * meaning that it is called on `slice[0]` and `slice[1]`,
 * followed by `slice[1]` and `slice[2]`, and so on.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(1, 1, 1, 3, 3, 2, 2, 2)
 * var iter = slice.chunkBy({ a, b -> a == b })
 * check(iter.next() == listOf(1, 1, 1))
 * check(iter.next() == listOf(3, 3))
 * check(iter.next() == listOf(2, 2, 2))
 * check(iter.next() == null)
 * ```
 *
 * This method can be used to extract the sorted subslices:
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(1, 1, 2, 3, 2, 3, 2, 3, 4)
 * var iter = slice.chunkBy({ a, b -> a <= b })
 * check(iter.next() == listOf(1, 1, 2, 3))
 * check(iter.next() == listOf(2, 3))
 * check(iter.next() == listOf(2, 3, 4))
 * check(iter.next() == null)
 * ```
 */
public fun <T> List<T>.chunkBy(pred: (T, T) -> Boolean): ChunkBy<T, (T, T) -> Boolean> = ChunkBy.new(this, pred)

/**
 * Returns an iterator over the slice producing non-overlapping mutable
 * runs of elements using the predicate to separate them.
 *
 * The predicate is called for every pair of consecutive elements,
 * meaning that it is called on `slice[0]` and `slice[1]`,
 * followed by `slice[1]` and `slice[2]`, and so on.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(1, 1, 1, 3, 3, 2, 2, 2)
 * var iter = slice.chunkByMut({ a, b -> a == b })
 * check(iter.next() == listOf(1, 1, 1))
 * check(iter.next() == listOf(3, 3))
 * check(iter.next() == listOf(2, 2, 2))
 * check(iter.next() == null)
 * ```
 *
 * This method can be used to extract the sorted subslices:
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(1, 1, 2, 3, 2, 3, 2, 3, 4)
 * var iter = slice.chunkByMut({ a, b -> a <= b })
 * check(iter.next() == listOf(1, 1, 2, 3))
 * check(iter.next() == listOf(2, 3))
 * check(iter.next() == listOf(2, 3, 4))
 * check(iter.next() == null)
 * ```
 */
public fun <T> List<T>.chunkByMut(pred: (T, T) -> Boolean): ChunkByMut<T, (T, T) -> Boolean> = ChunkByMut.new(this, pred)

/**
 * Divides one slice into two at an index.
 *
 * The first will contain all indices from `[0, mid)` (excluding
 * the index `mid` itself) and the second will contain all
 * indices from `[mid, len)` (excluding the index `len` itself).
 *
 * # Panics
 *
 * Panics if `mid> len`.  For a non-panicking alternative see
 * [`splitAtChecked`](slice.splitAtChecked).
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf('a', 'b', 'c')
 * run {
 *     val (left, right) = v.splitAt(0)
 *     check(left == listOf())
 *     check(right == listOf('a', 'b', 'c'))
 * }
 * run {
 *     val (left1, right1) = v.splitAt(2)
 *     check(left1 == listOf('a', 'b'))
 *     check(right1 == listOf('c'))
 * }
 * run {
 *     val (left2, right2) = v.splitAt(3)
 *     check(left2 == listOf('a', 'b', 'c'))
 *     check(right2 == listOf())
 * }
 * ```
 */
public fun <T> List<T>.splitAt(mid: Int): Pair<List<T>, List<T>> =
    when (val matchVal = this.splitAtChecked(mid)) {
        null -> error("mid > len")
        else -> {
            val pair = matchVal
            pair
        }
    }

/**
 * Divides one mutable slice into two at an index.
 *
 * The first will contain all indices from `[0, mid)` (excluding
 * the index `mid` itself) and the second will contain all
 * indices from `[mid, len)` (excluding the index `len` itself).
 *
 * # Panics
 *
 * Panics if `mid> len`.  For a non-panicking alternative see
 * [`splitAtMutChecked`](slice.splitAtMutChecked).
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(1, 0, 3, 0, 5, 6)
 * val (left, right) = v.splitAtMut(2)
 * check(left == listOf(1, 0))
 * check(right == listOf(3, 0, 5, 6))
 * left[1] = 2
 * right[1] = 4
 * check(v == listOf(1, 2, 3, 4, 5, 6))
 * ```
 */
public fun <T> List<T>.splitAtMut(mid: Int): Pair<MutableList<T>, MutableList<T>> =
    when (val matchVal = this.splitAtMutChecked(mid)) {
        null -> error("mid > len")
        else -> {
            val pair = matchVal
            pair
        }
    }

/**
 * Divides one slice into two at an index, without doing bounds checking.
 *
 * The first will contain all indices from `[0, mid)` (excluding
 * the index `mid` itself) and the second will contain all
 * indices from `[mid, len)` (excluding the index `len` itself).
 *
 * For a safe alternative see [splitAt].
 *
 * # Safety
 *
 * Calling this method with an out-of-bounds index is *[undefined behavior]*
 * even if the resulting reference is not used. The caller has to ensure that
 * `0 <= mid <= self.len()`.
 *
 * [splitAt]: slice.splitAt
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf('a', 'b', 'c')
 * run {
 *     val (left, right) = v.splitAtUnchecked(0)
 *     check(left == listOf())
 *     check(right == listOf('a', 'b', 'c'))
 * }
 * run {
 *     val (left1, right1) = v.splitAtUnchecked(2)
 *     check(left1 == listOf('a', 'b'))
 *     check(right1 == listOf('c'))
 * }
 * run {
 *     val (left2, right2) = v.splitAtUnchecked(3)
 *     check(left2 == listOf('a', 'b', 'c'))
 *     check(right2 == listOf())
 * }
 * ```
 */
public fun <T> List<T>.splitAtUnchecked(mid: Int): Pair<List<T>, List<T>> {
    // Note(const-Note): the const function `fromRawParts` is used to make this
    // function const; previously the implementation used
    // `(self.getUnchecked(.. mid), self.getUnchecked(mid ..))`
    val len = this.size
    val ptr = this.asPtr()
    Unit
    // SAFETY: Caller has to check that `0 <= mid <= self.len()`
    return Pair(linearSliceView(ptr, mid.toInt(), UnresolvedTypeLayout<T>()), linearSliceView(ConstPtr((ptr).rawAddress + ((mid).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), uncheckedSub(len, mid).toInt(), UnresolvedTypeLayout<T>()))
}

/**
 * Divides one mutable slice into two at an index, without doing bounds checking.
 *
 * The first will contain all indices from `[0, mid)` (excluding
 * the index `mid` itself) and the second will contain all
 * indices from `[mid, len)` (excluding the index `len` itself).
 *
 * For a safe alternative see [splitAtMut].
 *
 * # Safety
 *
 * Calling this method with an out-of-bounds index is *[undefined behavior]*
 * even if the resulting reference is not used. The caller has to ensure that
 * `0 <= mid <= self.len()`.
 *
 * [splitAtMut]: slice.splitAtMut
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(1, 0, 3, 0, 5, 6)
 * // scoped to restrict the scope of the borrows
 * run {
 *     val (left, right) = v.splitAtMutUnchecked(2)
 *     check(left == listOf(1, 0))
 *     check(right == listOf(3, 0, 5, 6))
 *     left[1] = 2
 *     right[1] = 4
 * }
 * check(v == listOf(1, 2, 3, 4, 5, 6))
 * ```
 */
public fun <T> List<T>.splitAtMutUnchecked(mid: Int): Pair<MutableList<T>, MutableList<T>> {
    val len = this.size
    val ptr = this.asMutPtr()
    Unit
    // SAFETY: Caller has to check that `0 <= mid <= self.len()`.
    //
    // `[ptr; mid]` and `[mid; len]` are not overlapping, so returning a mutable reference
    // is fine.
    return Pair(linearMutSliceView(ptr, mid.toInt(), UnresolvedTypeLayout<T>()), linearMutSliceView(MutPtr((ptr).rawAddress + ((mid).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), uncheckedSub(len, mid).toInt(), UnresolvedTypeLayout<T>()))
}

/**
 * Divides one slice into two at an index, returning `None` if the slice is
 * too short.
 *
 * If `mid ≤ len`, returns a pair of slices where the first will contain all
 * indices from `[0, mid)` (excluding the index `mid` itself) and the
 * second will contain all indices from `[mid, len)` (excluding the index
 * `len` itself).
 *
 * Otherwise, if `mid> len`, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(1, -2, 3, -4, 5, -6)
 * run {
 *     val (left, right) = v.splitAtChecked(0).unwrap()
 *     check(left == listOf())
 *     check(right == listOf(1, -2, 3, -4, 5, -6))
 * }
 * run {
 *     val (left1, right1) = v.splitAtChecked(2).unwrap()
 *     check(left1 == listOf(1, -2))
 *     check(right1 == listOf(3, -4, 5, -6))
 * }
 * run {
 *     val (left2, right2) = v.splitAtChecked(6).unwrap()
 *     check(left2 == listOf(1, -2, 3, -4, 5, -6))
 *     check(right2 == listOf())
 * }
 * check(null == v.splitAtChecked(7))
 * ```
 */
public fun <T> List<T>.splitAtChecked(mid: Int): Pair<List<T>, List<T>>? =
    if (mid <= this.size) {
        // SAFETY: `[ptr; mid]` and `[mid; len]` are inside `self`, which
        // fulfills the requirements of `splitAtUnchecked`.
        this.splitAtUnchecked(mid)
    } else {
        null
    }

/**
 * Divides one mutable slice into two at an index, returning `None` if the
 * slice is too short.
 *
 * If `mid ≤ len`, returns a pair of slices where the first will contain all
 * indices from `[0, mid)` (excluding the index `mid` itself) and the
 * second will contain all indices from `[mid, len)` (excluding the index
 * `len` itself).
 *
 * Otherwise, if `mid> len`, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(1, 0, 3, 0, 5, 6)
 * when (val matchVal = v.splitAtMutChecked(2)) {
 *     null -> {}
 *     else -> {
 *         val left = matchVal.first
 *         val right = matchVal.second
 *         check(left == listOf(1, 0))
 *         check(right == listOf(3, 0, 5, 6))
 *         left[1] = 2
 *         right[1] = 4
 *     }
 * }
 * check(v == listOf(1, 2, 3, 4, 5, 6))
 * check(null == v.splitAtMutChecked(7))
 * ```
 */
public fun <T> List<T>.splitAtMutChecked(mid: Int): Pair<MutableList<T>, MutableList<T>>? =
    if (mid <= this.size) {
        // SAFETY: `[ptr; mid]` and `[mid; len]` are inside `self`, which
        // fulfills the requirements of `splitAtUnchecked`.
        this.splitAtMutUnchecked(mid)
    } else {
        null
    }

/**
 * Returns an iterator over subslices separated by elements that match
 * `pred`. The matched element is not contained in the subslices.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(10, 40, 33, 20)
 * var iter = slice.split({ num -> num % 3 == 0 })
 * check(iter.next()!! == listOf(10, 40))
 * check(iter.next()!! == listOf(20))
 * check(iter.next() == null)
 * ```
 *
 * If the first element is matched, an empty slice will be the first item
 * returned by the iterator. Similarly, if the last element in the slice
 * is matched, an empty slice will be the last item returned by the
 * iterator:
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(10, 40, 33)
 * var iter = slice.split({ num -> num % 3 == 0 })
 * check(iter.next()!! == listOf(10, 40))
 * check(iter.next()!! == listOf())
 * check(iter.next() == null)
 * ```
 *
 * If two matched elements are directly adjacent, an empty slice will be
 * present between them:
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(10, 6, 33, 20)
 * var iter = slice.split({ num -> num % 3 == 0 })
 * check(iter.next()!! == listOf(10))
 * check(iter.next()!! == listOf())
 * check(iter.next()!! == listOf(20))
 * check(iter.next() == null)
 * ```
 */
public fun <T> List<T>.split(pred: (T) -> Boolean): Split<T, (T) -> Boolean> = Split.new(this, pred)

/**
 * Returns an iterator over mutable subslices separated by elements that
 * match `pred`. The matched element is not contained in the subslices.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(10, 40, 30, 20, 60, 50)
 * for (group in v.splitMut({ num -> num % 3 == 0 })) {
 *     group[0] = 1
 * }
 * check(v == listOf(1, 40, 30, 1, 60, 1))
 * ```
 */
public fun <T> List<T>.splitMut(pred: (T) -> Boolean): SplitMut<T, (T) -> Boolean> = SplitMut.new(this, pred)

/**
 * Returns an iterator over subslices separated by elements that match
 * `pred`. The matched element is contained in the end of the previous
 * subslice as a terminator.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(10, 40, 33, 20)
 * var iter = slice.splitInclusive({ num -> num % 3 == 0 })
 * check(iter.next()!! == listOf(10, 40, 33))
 * check(iter.next()!! == listOf(20))
 * check(iter.next() == null)
 * ```
 *
 * If the last element of the slice is matched,
 * that element will be considered the terminator of the preceding slice.
 * That slice will be the last item returned by the iterator.
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(3, 10, 40, 33)
 * var iter = slice.splitInclusive({ num -> num % 3 == 0 })
 * check(iter.next()!! == listOf(3))
 * check(iter.next()!! == listOf(10, 40, 33))
 * check(iter.next() == null)
 * ```
 */
public fun <T> List<T>.splitInclusive(pred: (T) -> Boolean): SplitInclusive<T, (T) -> Boolean> = SplitInclusive.new(this, pred)

/**
 * Returns an iterator over mutable subslices separated by elements that
 * match `pred`. The matched element is contained in the previous
 * subslice as a terminator.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(10, 40, 30, 20, 60, 50)
 * for (group in v.splitInclusiveMut({ num -> num % 3 == 0 })) {
 *     val terminatorIdx = group.len() - 1
 *     group[terminatorIdx] = 1
 * }
 * check(v == listOf(10, 40, 1, 20, 1, 1))
 * ```
 */
public fun <T> List<T>.splitInclusiveMut(pred: (T) -> Boolean): SplitInclusiveMut<T, (T) -> Boolean> = SplitInclusiveMut.new(this, pred)

/**
 * Returns an iterator over subslices separated by elements that match
 * `pred`, starting at the end of the slice and working backwards.
 * The matched element is not contained in the subslices.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * val slice = listOf(11, 22, 33, 0, 44, 55)
 * var iter = slice.rsplit({ num -> num == 0 })
 * check(iter.next()!! == listOf(44, 55))
 * check(iter.next()!! == listOf(11, 22, 33))
 * check(iter.next() == null)
 * ```
 *
 * As with `split()`, if the first or last element is matched, an empty
 * slice will be the first (or last) item returned by the iterator.
 *
 * ```kotlin
 * val v = listOf(0, 1, 1, 2, 3, 5, 8)
 * var it = v.rsplit({ n -> n % 2 == 0 })
 * check(it.next()!! == listOf())
 * check(it.next()!! == listOf(3, 5))
 * check(it.next()!! == listOf(1, 1))
 * check(it.next()!! == listOf())
 * check(it.next() == null)
 * ```
 */
public fun <T> List<T>.rsplit(pred: (T) -> Boolean): RSplit<T, (T) -> Boolean> = RSplit.new(this, pred)

/**
 * Returns an iterator over mutable subslices separated by elements that
 * match `pred`, starting at the end of the slice and working
 * backwards. The matched element is not contained in the subslices.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(100, 400, 300, 200, 600, 500)
 * var count = 0
 * for (group in v.rsplitMut({ num -> num % 3 == 0 })) {
 *     count += 1
 *     group[0] = count
 * }
 * check(v == listOf(3, 400, 300, 2, 600, 1))
 * ```
 */
public fun <T> List<T>.rsplitMut(pred: (T) -> Boolean): RSplitMut<T, (T) -> Boolean> = RSplitMut.new(this, pred)

/**
 * Returns an iterator over subslices separated by elements that match
 * `pred`, limited to returning at most `n` items. The matched element is
 * not contained in the subslices.
 *
 * The last element returned, if any, will contain the remainder of the
 * slice.
 *
 * # Examples
 *
 * Print the slice split once by numbers divisible by 3 (i.e., `[10, 40]`,
 * `[20, 60, 50]`):
 *
 * ```kotlin
 * val v = listOf(10, 40, 30, 20, 60, 50)
 * for (group in v.splitn(2, { num -> num % 3 == 0 })) {
 *     println("$group")
 * }
 * ```
 */
public fun <T> List<T>.splitn(n: Int, pred: (T) -> Boolean): SplitN<T, (T) -> Boolean> = SplitN.new(this.split(pred), n)

/**
 * Returns an iterator over mutable subslices separated by elements that match
 * `pred`, limited to returning at most `n` items. The matched element is
 * not contained in the subslices.
 *
 * The last element returned, if any, will contain the remainder of the
 * slice.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(10, 40, 30, 20, 60, 50)
 * for (group in v.splitnMut(2, { num -> num % 3 == 0 })) {
 *     group[0] = 1
 * }
 * check(v == listOf(1, 40, 30, 1, 60, 50))
 * ```
 */
public fun <T> List<T>.splitnMut(n: Int, pred: (T) -> Boolean): SplitNMut<T, (T) -> Boolean> = SplitNMut.new(this.splitMut(pred), n)

/**
 * Returns an iterator over subslices separated by elements that match
 * `pred` limited to returning at most `n` items. This starts at the end of
 * the slice and works backwards. The matched element is not contained in
 * the subslices.
 *
 * The last element returned, if any, will contain the remainder of the
 * slice.
 *
 * # Examples
 *
 * Print the slice split once, starting from the end, by numbers divisible
 * by 3 (i.e., `[50]`, `[10, 40, 30, 20]`):
 *
 * ```kotlin
 * val v = listOf(10, 40, 30, 20, 60, 50)
 * for (group in v.rsplitn(2, { num -> num % 3 == 0 })) {
 *     println("$group")
 * }
 * ```
 */
public fun <T> List<T>.rsplitn(n: Int, pred: (T) -> Boolean): RSplitN<T, (T) -> Boolean> = RSplitN.new(this.rsplit(pred), n)

/**
 * Returns an iterator over subslices separated by elements that match
 * `pred` limited to returning at most `n` items. This starts at the end of
 * the slice and works backwards. The matched element is not contained in
 * the subslices.
 *
 * The last element returned, if any, will contain the remainder of the
 * slice.
 *
 * # Examples
 *
 * ```kotlin
 * var s = mutableListOf(10, 40, 30, 20, 60, 50)
 * for (group in s.rsplitnMut(2, { num -> num % 3 == 0 })) {
 *     group[0] = 1
 * }
 * check(s == listOf(1, 40, 30, 20, 60, 1))
 * ```
 */
public fun <T> List<T>.rsplitnMut(n: Int, pred: (T) -> Boolean): RSplitNMut<T, (T) -> Boolean> = RSplitNMut.new(this.rsplitMut(pred), n)

/**
 * Splits the slice on the first element that matches the specified
 * predicate.
 *
 * If any matching elements are present in the slice, returns the prefix
 * before the match and suffix after. The matching element itself is not
 * included. If no elements match, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val s = listOf(1, 2, 3, 2, 4)
 * check(s.let { s -> val d = { x -> x == 2 }; val idx = s.indexOf(d); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + d.length)) } == Pair(listOf(1), listOf(3, 2, 4)))
 * check(s.let { s -> val d = { x -> x == 0 }; val idx = s.indexOf(d); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + d.length)) } == null)
 * ```
 */
public fun <T> List<T>.splitOnce(pred: (T) -> Boolean): Pair<List<T>, List<T>>? {
    val index = (this.indexOfFirst(pred).takeIf { it >= 0 } ?: return null)
    // Slice bounds checks optimized are away (as of June 2026)
    return Pair(((this).subList(0, index)), ((this).subList(index + 1, (this).size)))
}

/**
 * Splits the slice on the last element that matches the specified
 * predicate.
 *
 * If any matching elements are present in the slice, returns the prefix
 * before the match and suffix after. The matching element itself is not
 * included. If no elements match, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val s = listOf(1, 2, 3, 2, 4)
 * check(s.let { s -> val d = { x -> x == 2 }; val idx = s.lastIndexOf(d); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + d.length)) } == Pair(listOf(1, 2, 3), listOf(4)))
 * check(s.let { s -> val d = { x -> x == 0 }; val idx = s.lastIndexOf(d); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + d.length)) } == null)
 * ```
 */
public fun <T> List<T>.rsplitOnce(pred: (T) -> Boolean): Pair<List<T>, List<T>>? {
    val index = (this.iter().indexOfLast(pred).takeIf { it >= 0 } ?: return null)
    // Slice bounds checks optimized are away (as of June 2026)
    return Pair(((this).subList(0, index)), ((this).subList(index + 1, (this).size)))
}

/**
 * Returns `true` if the slice contains an element with the given value.
 *
 * This operation is *O*(*n*).
 *
 * Note that if you have a sorted slice, [binarySearch] may be faster.
 *
 * [binarySearch]: slice.binarySearch
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.contains(30))
 * check(!v.contains(50))
 * ```
 *
 * If you do not have a `T`, but some other value that you can compare
 * with one (for example, `String` implements `PartialEq<String>`), you can
 * use `iter().any`:
 *
 * ```kotlin
 * val v = listOf("hello", "world")
 * // slice of `String`
 * check(v.any({ e -> e == "hello" }))
 * // search with `String`
 * check(!v.any({ e -> e == "hi" }))
 * ```
 */
public fun <T> List<T>.contains(x: T): Boolean = x.sliceContains(this)

/**
 * Returns `true` if `needle` is a prefix of the slice or equal to the slice.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.startsWith(listOf(10)))
 * check(v.startsWith(listOf(10, 40)))
 * check(v.startsWith(v))
 * check(!v.startsWith(listOf(50)))
 * check(!v.startsWith(listOf(10, 50)))
 * ```
 *
 * Always returns `true` if `needle` is an empty slice:
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.startsWith(listOf()))
 * val v1: List<UByte> = listOf()
 * check(v1.startsWith(listOf()))
 * ```
 */
public fun <T> List<T>.startsWith(needle: List<T>): Boolean {
    val n = needle.size
    return this.size >= n && needle == ((this).subList(0, n))
}

/**
 * Returns `true` if `needle` is a suffix of the slice or equal to the slice.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.endsWith(listOf(30)))
 * check(v.endsWith(listOf(40, 30)))
 * check(v.endsWith(v))
 * check(!v.endsWith(listOf(50)))
 * check(!v.endsWith(listOf(50, 30)))
 * ```
 *
 * Always returns `true` if `needle` is an empty slice:
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.endsWith(listOf()))
 * val v1: List<UByte> = listOf()
 * check(v1.endsWith(listOf()))
 * ```
 */
public fun <T> List<T>.endsWith(needle: List<T>): Boolean {
    val (m, n) = Pair(this.size, needle.size)
    return m >= n && needle == ((this).subList(m - n, (this).size))
}

/**
 * Returns a subslice with the prefix removed.
 *
 * If the slice starts with `prefix`, returns the subslice after the prefix, wrapped in `Some`.
 * If `prefix` is empty, simply returns the original slice. If `prefix` is equal to the
 * original slice, returns an empty slice.
 *
 * If the slice does not start with `prefix`, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.stripPrefix(listOf(10)) == listOf(40, 30))
 * check(v.stripPrefix(listOf(10, 40)) == listOf(30))
 * check(v.stripPrefix(listOf(10, 40, 30)) == listOf())
 * check(v.stripPrefix(listOf(50)) == null)
 * check(v.stripPrefix(listOf(10, 50)) == null)
 * val prefix: String = "he"
 * check(("hello".encodeToByteArray()).takeIf { it.startsWith(prefix.encodeToByteArray().map { it.toUByte() }) }?.removePrefix(prefix.encodeToByteArray().map { it.toUByte() }) == "llo".encodeToByteArray().asRef())
 * ```
 */
public fun <T, P : SlicePattern<T>> List<T>.stripPrefix(prefix: P): List<T>? {
    // This function will need rewriting if and when SlicePattern becomes more sophisticated.
    val prefix1 = prefix.asSlice()
    val n = prefix1.len()
    if (n <= this.size) {
        val (head, tail) = this.splitAt(n)
        if (head == prefix1) {
            return tail
        }
    }
    return null
}

/**
 * Returns a subslice with the suffix removed.
 *
 * If the slice ends with `suffix`, returns the subslice before the suffix, wrapped in `Some`.
 * If `suffix` is empty, simply returns the original slice. If `suffix` is equal to the
 * original slice, returns an empty slice.
 *
 * If the slice does not end with `suffix`, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * check(v.stripSuffix(listOf(30)) == listOf(10, 40))
 * check(v.stripSuffix(listOf(40, 30)) == listOf(10))
 * check(v.stripSuffix(listOf(10, 40, 30)) == listOf())
 * check(v.stripSuffix(listOf(50)) == null)
 * check(v.stripSuffix(listOf(50, 30)) == null)
 * ```
 */
public fun <T, P : SlicePattern<T>> List<T>.stripSuffix(suffix: P): List<T>? {
    // This function will need rewriting if and when SlicePattern becomes more sophisticated.
    val suffix1 = suffix.asSlice()
    val (len, n) = Pair(this.size, suffix1.len())
    if (n <= len) {
        val (head, tail) = this.splitAt(len - n)
        if (tail == suffix1) {
            return head
        }
    }
    return null
}

/**
 * Returns a subslice with the prefix and suffix removed.
 *
 * If the slice starts with `prefix`, ends with `suffix`, and
 * the prefix and suffix don't overlap, returns the subslice after
 * the prefix and before the suffix, wrapped in `Some`.
 *
 * If the slice does not start with `prefix`, does not end with `suffix`,
 * or the prefix and suffix overlap in the slice, returns `None`.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 50, 40, 30)
 * check(v.stripCircumfix(listOf(10), listOf(30)) == listOf(50, 40))
 * check(v.stripCircumfix(listOf(10), listOf(40, 30)) == listOf(50))
 * check(v.stripCircumfix(listOf(10, 50), listOf(40, 30)) == listOf())
 * check(v.stripCircumfix(listOf(50), listOf(30)) == null)
 * check(v.stripCircumfix(listOf(10), listOf(40)) == null)
 * check(v.stripCircumfix(listOf(), listOf(40, 30)) == listOf(10, 50))
 * check(v.stripCircumfix(listOf(10, 50), listOf()) == listOf(40, 30))
 * check(v.stripCircumfix(listOf(10, 50, 40), listOf(50, 40, 30)) == null)
 * ```
 */
public fun <T, S, P> List<T>.stripCircumfix(prefix: P, suffix: S): List<T>? where S : SlicePattern<T>, P : SlicePattern<T> {
    return ((this.stripPrefix(prefix) ?: return null)).takeIf { it.endsWith(suffix) }?.removeSuffix(suffix)
}

/**
 * Returns a subslice with the optional prefix removed.
 *
 * If the slice starts with `prefix`, returns the subslice after the prefix.  If `prefix`
 * is empty or the slice does not start with `prefix`, simply returns the original slice.
 * If `prefix` is equal to the original slice, returns an empty slice.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * // Prefix present - removes it
 * check(v.trimPrefix(listOf(10)) == listOf(40, 30))
 * check(v.trimPrefix(listOf(10, 40)) == listOf(30))
 * check(v.trimPrefix(listOf(10, 40, 30)) == listOf())
 * // Prefix absent - returns original slice
 * check(v.trimPrefix(listOf(50)) == listOf(10, 40, 30))
 * check(v.trimPrefix(listOf(10, 50)) == listOf(10, 40, 30))
 * val prefix: String = "he"
 * check("hello".encodeToByteArray().trimPrefix(prefix.encodeToByteArray().map { it.toUByte() }) == "llo".encodeToByteArray().asRef())
 * ```
 */
public fun <T, P : SlicePattern<T>> List<T>.trimPrefix(prefix: P): List<T> {
    // This function will need rewriting if and when SlicePattern becomes more sophisticated.
    val prefix1 = prefix.asSlice()
    val n = prefix1.len()
    if (n <= this.size) {
        val (head, tail) = this.splitAt(n)
        if (head == prefix1) {
            return tail
        }
    }
    return this
}

/**
 * Returns a subslice with the optional suffix removed.
 *
 * If the slice ends with `suffix`, returns the subslice before the suffix.  If `suffix`
 * is empty or the slice does not end with `suffix`, simply returns the original slice.
 * If `suffix` is equal to the original slice, returns an empty slice.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(10, 40, 30)
 * // Suffix present - removes it
 * check(v.trimSuffix(listOf(30)) == listOf(10, 40))
 * check(v.trimSuffix(listOf(40, 30)) == listOf(10))
 * check(v.trimSuffix(listOf(10, 40, 30)) == listOf())
 * // Suffix absent - returns original slice
 * check(v.trimSuffix(listOf(50)) == listOf(10, 40, 30))
 * check(v.trimSuffix(listOf(50, 30)) == listOf(10, 40, 30))
 * ```
 */
public fun <T, P : SlicePattern<T>> List<T>.trimSuffix(suffix: P): List<T> {
    // This function will need rewriting if and when SlicePattern becomes more sophisticated.
    val suffix1 = suffix.asSlice()
    val (len, n) = Pair(this.size, suffix1.len())
    if (n <= len) {
        val (head, tail) = this.splitAt(len - n)
        if (tail == suffix1) {
            return head
        }
    }
    return this
}

/**
 * Binary searches this slice for a given element.
 * If the slice is not sorted, the returned result is unspecified and
 * meaningless.
 *
 * If the value is found then [Result.Ok] is returned, containing the
 * index of the matching element. If there are multiple matches, then any
 * one of the matches could be returned. The index is chosen
 * deterministically, but is subject to change in future versions of Rust.
 * If the value is not found then [Result.Err] is returned, containing
 * the index where a matching element could be inserted while maintaining
 * sorted order.
 *
 * See also [binarySearchBy], [binarySearchByKey], and [partitionPoint].
 *
 * [binarySearchBy]: slice.binarySearchBy
 * [binarySearchByKey]: slice.binarySearchByKey
 * [partitionPoint]: slice.partitionPoint
 *
 * # Examples
 *
 * Looks up a series of four elements. The first is found, with a
 * uniquely determined position; the second and third are not
 * found; the fourth could match any position in `[1, 4]`.
 *
 * ```kotlin
 * val s = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
 * check(s.binarySearch(13) == Result.success(9))
 * check(s.binarySearch(4) == Result.failure(7))
 * check(s.binarySearch(100) == Result.failure(13))
 * val r = s.binarySearch(1)
 * check(when {
 *     r is Ok && r.value in 1..4 -> true
 *     else -> false
 * })
 * ```
 *
 * If you want to find that whole *range* of matching items, rather than
 * an arbitrary matching one, that can be done using [partitionPoint]:
 * ```kotlin
 * val s = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
 * val low = s.partitionPoint({ x -> x < 1 })
 * check(low == 1)
 * val high = s.partitionPoint({ x -> x <= 1 })
 * check(high == 5)
 * val r = s.binarySearch(1)
 * check((low until high).contains(r.unwrap()))
 * check(s[0 until low].all({ x -> x < 1 }))
 * check(s[low until high].all({ x -> x == 1 }))
 * check(s[high until s.size].all({ x -> x > 1 }))
 * // For something not found, the "range" of equal items is empty
 * check(s.partitionPoint({ x -> x < 11 }) == 9)
 * check(s.partitionPoint({ x -> x <= 11 }) == 9)
 * check(s.binarySearch(11) == Result.failure(9))
 * ```
 *
 * If you want to insert an item to a sorted vector, while maintaining
 * sort order, consider using [partitionPoint]:
 *
 * ```kotlin
 * var s = mutableListOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
 * val num = 42
 * val idx = s.partitionPoint({ x -> x <= num })
 * // If `num` is unique, `s.partitionPoint(| & x | x<num)` (with `<`) is equivalent to
 * // `s.binarySearch(num).unwrapOrElse(| x | x)`, but using `<=` will allow `insert`
 * // to shift less elements.
 * s.add(idx, num)
 * check(s == listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 42, 55))
 * ```
 */
public fun <T> List<T>.binarySearch(x: T): Result<Int, Int> = this.binarySearchBy({ p -> p.cmp(x) })

/**
 * Binary searches this slice with a comparator function.
 *
 * The comparator function should return an order code that indicates
 * whether its argument is `Less`, `Equal` or `Greater` the desired
 * target.
 * If the slice is not sorted or if the comparator function does not
 * implement an order consistent with the sort order of the underlying
 * slice, the returned result is unspecified and meaningless.
 *
 * If the value is found then [Result.Ok] is returned, containing the
 * index of the matching element. If there are multiple matches, then any
 * one of the matches could be returned. The index is chosen
 * deterministically, but is subject to change in future versions of Rust.
 * If the value is not found then [Result.Err] is returned, containing
 * the index where a matching element could be inserted while maintaining
 * sorted order.
 *
 * See also [binarySearch], [binarySearchByKey], and [partitionPoint].
 *
 * [binarySearch]: slice.binarySearch
 * [binarySearchByKey]: slice.binarySearchByKey
 * [partitionPoint]: slice.partitionPoint
 *
 * # Examples
 *
 * Looks up a series of four elements. The first is found, with a
 * uniquely determined position; the second and third are not
 * found; the fourth could match any position in `[1, 4]`.
 *
 * ```kotlin
 * val s = listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
 * val seek = 13
 * check(s.binarySearchBy({ probe -> probe.cmp(seek) }) == Result.success(9))
 * val seek1 = 4
 * check(s.binarySearchBy({ probe -> probe.cmp(seek1) }) == Result.failure(7))
 * val seek2 = 100
 * check(s.binarySearchBy({ probe -> probe.cmp(seek2) }) == Result.failure(13))
 * val seek3 = 1
 * val r = s.binarySearchBy({ probe -> probe.cmp(seek3) })
 * check(when {
 *     r is Ok && r.value in 1..4 -> true
 *     else -> false
 * })
 * ```
 */
public fun <T> List<T>.binarySearchBy(f: (T) -> Ordering): Result<Int, Int> {
    var size = this.size
    if (size == 0) {
        return Result.Err<Int, Int>(0)
    }
    var base = 0
    // This loop intentionally doesn't have an early exit if the comparison
    // returns Equal. We want the number of loop iterations to depend *only*
    // on the size of the input slice so that the CPU can reliably predict
    // the loop count.
    while (size > 1) {
        val half = size / 2
        val mid = base + half
        // SAFETY: the call is made safe by the following invariants:
        // - `mid >= 0`: by definition
        // - `mid<size`: `mid = size / 2 + size / 4 + size / 8 ...`
        val cmp = f(this.getUnchecked(mid))
        // Binary search interacts poorly with branch prediction, so force
        // the compiler to use conditional moves if supported by the target
        // architecture.
        base = selectUnpredictable(cmp == Greater, base, mid)
        // This is imprecise in the case where `size` is odd and the
        // comparison returns Greater: the mid element still gets included
        // by `size` even though it's known to be larger than the element
        // being searched for.
        //
        // This is fine though: we gain more performance by keeping the
        // loop iteration count invariant (and thus predictable) than we
        // lose from considering one additional element.
        size -= half
    }
    // SAFETY: base is always in [0, size) because base <= mid.
    val cmp1 = f(this.getUnchecked(base))
    return if (cmp1 == Equal) {
        // SAFETY: same as the `getUnchecked` above.
        assertUnchecked(base < this.size)
        Result.Ok<Int, Int>(base)
    } else {
        val result = base + (if (cmp1 == Less) 1 else 0)
        // SAFETY: same as the `getUnchecked` above.
        // Note that this is `<=`, unlike the assume in the `Ok` path.
        assertUnchecked(result <= this.size)
        Result.Err<Int, Int>(result)
    }
}

// Lint rustdoc.brokenIntraDocLinks is allowed as `slice.sortByKey` is
// in crate `alloc`, and as such doesn't exists yet when building `core`: #74481.
// This breaks links when slice is displayed in core, but changing it to use relative links
// would break when the item is re-exported. So allow the core links to be broken for now.

/**
 * Binary searches this slice with a key extraction function.
 *
 * Assumes that the slice is sorted by the key, for instance with
 * [sortByKey] using the same key extraction function.
 * If the slice is not sorted by the key, the returned result is
 * unspecified and meaningless.
 *
 * If the value is found then [Result.Ok] is returned, containing the
 * index of the matching element. If there are multiple matches, then any
 * one of the matches could be returned. The index is chosen
 * deterministically, but is subject to change in future versions of Rust.
 * If the value is not found then [Result.Err] is returned, containing
 * the index where a matching element could be inserted while maintaining
 * sorted order.
 *
 * See also [binarySearch], [binarySearchBy], and [partitionPoint].
 *
 * [sortByKey]: slice.sortByKey
 * [binarySearch]: slice.binarySearch
 * [binarySearchBy]: slice.binarySearchBy
 * [partitionPoint]: slice.partitionPoint
 *
 * # Examples
 *
 * Looks up a series of four elements in a slice of pairs sorted by
 * their second elements. The first is found, with a uniquely
 * determined position; the second and third are not found; the
 * fourth could match any position in `[1, 4]`.
 *
 * ```kotlin
 * val s = listOf(Pair(0, 0), Pair(2, 1), Pair(4, 1), Pair(5, 1), Pair(3, 1), Pair(1, 2), Pair(2, 3), Pair(4, 5), Pair(5, 8), Pair(3, 13), Pair(1, 21), Pair(2, 34), Pair(4, 55))
 * check(s.binarySearchByKey(13, { (a, b) -> b }) == Result.success(9))
 * check(s.binarySearchByKey(4, { (a, b) -> b }) == Result.failure(7))
 * check(s.binarySearchByKey(100, { (a, b) -> b }) == Result.failure(13))
 * val r = s.binarySearchByKey(1, { (a, b) -> b })
 * check(when {
 *     r is Ok && r.value in 1..4 -> true
 *     else -> false
 * })
 * ```
 */
public fun <T, B> List<T>.binarySearchByKey(b: B, f: (T) -> B): Result<Int, Int> = this.binarySearchBy({ k -> kmCompareValues(f(k), b).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } } })

/**
 * Sorts the slice in ascending order **without** preserving the initial order of equal elements.
 *
 * This sort is unstable (i.e., may reorder equal elements), in-place (i.e., does not
 * allocate), and *O*(*n* \* log(*n*)) worst-case.
 *
 * If the implementation of [Ord] for `T` does not implement a [total order], the function
 * may panic; even if the function exits normally, the resulting order of elements in the slice
 * is unspecified. See also the note on panicking below.
 *
 * For example `| a, b |(a - b).cmp(a)` is a comparison function that is neither transitive nor
 * reflexive nor total, `a<b<c<a` with `a = 1, b = 2, c = 3`. For more information and
 * examples see the [Ord] documentation.
 *
 * All original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. Same is true if the implementation of [Ord] for `T` panics.
 *
 * Sorting types that only implement [PartialOrd] such as [f32] and [f64] require
 * additional precautions. For example, `Float.NAN != Float.NAN`, which doesn't fulfill the
 * reflexivity requirement of [Ord]. By using an alternative comparison function with
 * `slice.sortUnstableBy` such as [f32.totalCmp] or [f64.totalCmp] that defines a
 * [total order] users can sort slices containing floating-point values. Alternatively, if all
 * values in the slice are guaranteed to be in a subset for which [PartialOrd.partialCmp]
 * forms a [total order], it's possible to sort the slice with `sortUnstableBy(|a, b|
 * a.partialCmp(b).unwrap())`.
 *
 * # Current implementation
 *
 * The current implementation is based on [ipnsort] by Lukas Bergdoll and Orson Peters, which
 * combines the fast average case of quicksort with the fast worst case of heapsort, achieving
 * linear time on fully sorted and reversed inputs. On inputs with k distinct elements, the
 * expected time to sort the data is *O*(*n* \* log(*k*)).
 *
 * It is typically faster than stable sorting, except in a few special cases, e.g., when the
 * slice is partially sorted.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `T` does not implement a [total order], or if
 * the [Ord] implementation panics.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(4, -5, 1, -3, 2)
 * v.sortUnstable()
 * check(v == listOf(-5, -3, 1, 2, 4))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T> List<T>.sortUnstable(ltTFn: (T, Rhs) -> Boolean) {
    sort(this, ltTFn)
}

/**
 * Sorts the slice in ascending order with a comparison function, **without** preserving the
 * initial order of equal elements.
 *
 * This sort is unstable (i.e., may reorder equal elements), in-place (i.e., does not
 * allocate), and *O*(*n* \* log(*n*)) worst-case.
 *
 * If the comparison function `compare` does not implement a [total order], the function
 * may panic; even if the function exits normally, the resulting order of elements in the slice
 * is unspecified. See also the note on panicking below.
 *
 * For example `| a, b |(a - b).cmp(a)` is a comparison function that is neither transitive nor
 * reflexive nor total, `a<b<c<a` with `a = 1, b = 2, c = 3`. For more information and
 * examples see the [Ord] documentation.
 *
 * All original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. Same is true if `compare` panics.
 *
 * # Current implementation
 *
 * The current implementation is based on [ipnsort] by Lukas Bergdoll and Orson Peters, which
 * combines the fast average case of quicksort with the fast worst case of heapsort, achieving
 * linear time on fully sorted and reversed inputs. On inputs with k distinct elements, the
 * expected time to sort the data is *O*(*n* \* log(*k*)).
 *
 * It is typically faster than stable sorting, except in a few special cases, e.g., when the
 * slice is partially sorted.
 *
 * # Panics
 *
 * May panic if the `compare` does not implement a [total order], or if
 * the `compare` itself panics.
 *
 * # Examples
 *
 * ```kotlin
 * var v = mutableListOf(4, -5, 1, -3, 2)
 * v.sortUnstableBy({ a, b -> a.cmp(b) })
 * check(v == listOf(-5, -3, 1, 2, 4))
 * // reverse sorting
 * v.sortUnstableBy({ a, b -> b.cmp(a) })
 * check(v == listOf(4, 2, 1, -3, -5))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T> List<T>.sortUnstableBy(compare: (T, T) -> Ordering) {
    sort(this, { a, b -> compare(a, b) == io.github.kotlinmania.rustlang.core.cmp.Less })
}

/**
 * Sorts the slice in ascending order with a key extraction function, **without** preserving
 * the initial order of equal elements.
 *
 * This sort is unstable (i.e., may reorder equal elements), in-place (i.e., does not
 * allocate), and *O*(*n* \* log(*n*)) worst-case.
 *
 * If the implementation of [Ord] for `K` does not implement a [total order], the function
 * may panic; even if the function exits normally, the resulting order of elements in the slice
 * is unspecified. See also the note on panicking below.
 *
 * For example `| a, b |(a - b).cmp(a)` is a comparison function that is neither transitive nor
 * reflexive nor total, `a<b<c<a` with `a = 1, b = 2, c = 3`. For more information and
 * examples see the [Ord] documentation.
 *
 * All original elements will remain in the slice and any possible modifications via interior
 * mutability are observed in the input. Same is true if the implementation of [Ord] for `K` panics.
 *
 * # Current implementation
 *
 * The current implementation is based on [ipnsort] by Lukas Bergdoll and Orson Peters, which
 * combines the fast average case of quicksort with the fast worst case of heapsort, achieving
 * linear time on fully sorted and reversed inputs. On inputs with k distinct elements, the
 * expected time to sort the data is *O*(*n* \* log(*k*)).
 *
 * It is typically faster than stable sorting, except in a few special cases, e.g., when the
 * slice is partially sorted.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `K` does not implement a [total order], or if
 * the [Ord] implementation panics.
 *
 * # Examples
 *
 * ```kotlin
 * var v = intArrayOf(4, -5, 1, -3, 2)
 * v.sortUnstableByKey({ k -> k.abs() })
 * check(v == intArrayOf(1, 2, -3, 4, -5))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T, K> List<T>.sortUnstableByKey(f: (T) -> K) {
    sort(this, { a, b -> (kmPartialCompareValues(f(a), f(b))?.let { cmpSign -> cmpSign < 0 } == true) })
}

/**
 * Partially sorts the slice in ascending order **without** preserving the initial order of equal elements.
 *
 * Upon completion, for the specified range `start .. end`, it's guaranteed that:
 *
 * 1. Every element in `self[.. start]` is smaller than or equal to
 * 2. Every element in `self[start .. end]`, which is sorted, and smaller than or equal to
 * 3. Every element in `self[end ..]`.
 *
 * This partial sort is unstable, meaning it may reorder equal elements in the specified range.
 * It may reorder elements outside the specified range as well, but the guarantees above still hold.
 *
 * This partial sort is in-place (i.e., does not allocate), and *O*(*n* + *k* \* log(*k*)) worst-case,
 * where *n* is the length of the slice and *k* is the length of the specified range.
 *
 * See the documentation of [sortUnstable] for implementation notes.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `T` does not implement a total order, or if
 * the [Ord] implementation panics, or if the specified range is out of bounds.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(slicePartialSortUnstable)
 * public fun doctestEntry() {
 *     var v = mutableListOf(4, -5, 1, -3, 2)
 *     // empty range at the beginning, nothing changed
 *     v.partialSortUnstable(0 until 0)
 *     check(v == listOf(4, -5, 1, -3, 2))
 *     // empty range in the middle, partitioning the slice
 *     v.partialSortUnstable(2 until 2)
 *     for (i in 0 until 2) {
 *         check(v[i] <= v[2])
 *     }
 *     for (i1 in 3 until v.size) {
 *         check(v[2] <= v[i1])
 *     }
 *     // single element range, same as selectNthUnstable
 *     v.partialSortUnstable(2 until 3)
 *     for (i2 in 0 until 2) {
 *         check(v[i2] <= v[2])
 *     }
 *     for (i3 in 3 until v.size) {
 *         check(v[2] <= v[i3])
 *     }
 *     // partial sort a subrange
 *     v.partialSortUnstable(1 until 4)
 *     check(v[1 until 4] == listOf(-3, 1, 2))
 *     // partial sort the whole range, same as sortUnstable
 *     v.partialSortUnstable(0 until 0)
 *     check(v == listOf(-5, -3, 1, 2, 4))
 * }
 * ```
 *
 * [sortUnstable]: slice.sortUnstable
 */
public fun <T, R> List<T>.partialSortUnstable(range: R, ltTFn: (T, Rhs) -> Boolean) where R : RangeBounds<Int> {
    partialSort(this, range, ltTFn)
}

/**
 * Partially sorts the slice in ascending order with a comparison function, **without**
 * preserving the initial order of equal elements.
 *
 * Upon completion, for the specified range `start .. end`, it's guaranteed that:
 *
 * 1. Every element in `self[.. start]` is smaller than or equal to
 * 2. Every element in `self[start .. end]`, which is sorted, and smaller than or equal to
 * 3. Every element in `self[end ..]`.
 *
 * This partial sort is unstable, meaning it may reorder equal elements in the specified range.
 * It may reorder elements outside the specified range as well, but the guarantees above still hold.
 *
 * This partial sort is in-place (i.e., does not allocate), and *O*(*n* + *k* \* log(*k*)) worst-case,
 * where *n* is the length of the slice and *k* is the length of the specified range.
 *
 * See the documentation of [sortUnstableBy] for implementation notes.
 *
 * # Panics
 *
 * May panic if the `compare` does not implement a total order, or if
 * the `compare` itself panics, or if the specified range is out of bounds.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(slicePartialSortUnstable)
 * public fun doctestEntry() {
 *     var v = mutableListOf(4, -5, 1, -3, 2)
 *     // empty range at the beginning, nothing changed
 *     v.partialSortUnstableBy(0 until 0, { a, b -> b.cmp(a) })
 *     check(v == listOf(4, -5, 1, -3, 2))
 *     // empty range in the middle, partitioning the slice
 *     v.partialSortUnstableBy(2 until 2, { a, b -> b.cmp(a) })
 *     for (i in 0 until 2) {
 *         check(v[i] >= v[2])
 *     }
 *     for (i1 in 3 until v.size) {
 *         check(v[2] >= v[i1])
 *     }
 *     // single element range, same as selectNthUnstable
 *     v.partialSortUnstableBy(2 until 3, { a, b -> b.cmp(a) })
 *     for (i2 in 0 until 2) {
 *         check(v[i2] >= v[2])
 *     }
 *     for (i3 in 3 until v.size) {
 *         check(v[2] >= v[i3])
 *     }
 *     // partial sort a subrange
 *     v.partialSortUnstableBy(1 until 4, { a, b -> b.cmp(a) })
 *     check(v[1 until 4] == listOf(2, 1, -3))
 *     // partial sort the whole range, same as sortUnstable
 *     v.partialSortUnstableBy(0 until 0, { a, b -> b.cmp(a) })
 *     check(v == listOf(4, 2, 1, -3, -5))
 * }
 * ```
 *
 * [sortUnstableBy]: slice.sortUnstableBy
 */
public fun <T, R> List<T>.partialSortUnstableBy(range: R, compare: (T, T) -> Ordering) where R : RangeBounds<Int> {
    partialSort(this, range, { a, b -> compare(a, b) == Less })
}

/**
 * Partially sorts the slice in ascending order with a key extraction function, **without**
 * preserving the initial order of equal elements.
 *
 * Upon completion, for the specified range `start .. end`, it's guaranteed that:
 *
 * 1. Every element in `self[.. start]` is smaller than or equal to
 * 2. Every element in `self[start .. end]`, which is sorted, and smaller than or equal to
 * 3. Every element in `self[end ..]`.
 *
 * This partial sort is unstable, meaning it may reorder equal elements in the specified range.
 * It may reorder elements outside the specified range as well, but the guarantees above still hold.
 *
 * This partial sort is in-place (i.e., does not allocate), and *O*(*n* + *k* \* log(*k*)) worst-case,
 * where *n* is the length of the slice and *k* is the length of the specified range.
 *
 * See the documentation of [sortUnstableByKey] for implementation notes.
 *
 * # Panics
 *
 * May panic if the implementation of [Ord] for `K` does not implement a total order, or if
 * the [Ord] implementation panics, or if the specified range is out of bounds.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(slicePartialSortUnstable)
 * public fun doctestEntry() {
 *     var v = intArrayOf(4, -5, 1, -3, 2)
 *     // empty range at the beginning, nothing changed
 *     v.partialSortUnstableByKey(0 until 0, { k -> k.abs() })
 *     check(v == intArrayOf(4, -5, 1, -3, 2))
 *     // empty range in the middle, partitioning the slice
 *     v.partialSortUnstableByKey(2 until 2, { k -> k.abs() })
 *     for (i in 0 until 2) {
 *         check(v[i].abs() <= v[2].abs())
 *     }
 *     for (i1 in 3 until v.size) {
 *         check(v[2].abs() <= v[i1].abs())
 *     }
 *     // single element range, same as selectNthUnstable
 *     v.partialSortUnstableByKey(2 until 3, { k -> k.abs() })
 *     for (i2 in 0 until 2) {
 *         check(v[i2].abs() <= v[2].abs())
 *     }
 *     for (i3 in 3 until v.size) {
 *         check(v[2].abs() <= v[i3].abs())
 *     }
 *     // partial sort a subrange
 *     v.partialSortUnstableByKey(1 until 4, { k -> k.abs() })
 *     check(((v).sliceArray(1 until 4)) == intArrayOf(2, -3, 4))
 *     // partial sort the whole range, same as sortUnstable
 *     v.partialSortUnstableByKey(0 until 0, { k -> k.abs() })
 *     check(v == intArrayOf(1, 2, -3, 4, -5))
 * }
 * ```
 *
 * [sortUnstableByKey]: slice.sortUnstableByKey
 */
public fun <T, K, R> List<T>.partialSortUnstableByKey(range: R, f: (T) -> K) where R : RangeBounds<Int> {
    partialSort(this, range, { a, b -> (kmPartialCompareValues(f(a), f(b))?.let { cmpSign -> cmpSign < 0 } == true) })
}

/**
 * Reorders the slice such that the element at `index` is at a sort-order position. All
 * elements before `index` will be `<=` to this value, and all elements after will be `>=` to
 * it.
 *
 * This reordering is unstable (i.e. any element that compares equal to the nth element may end
 * up at that position), in-place (i.e.  does not allocate), and runs in *O*(*n*) time. This
 * function is also known as "kth element" in other libraries.
 *
 * Returns a triple that partitions the reordered slice:
 *
 * * The unsorted subslice before `index`, whose elements all satisfy `x <= self[index]`.
 *
 * * The element at `index`.
 *
 * * The unsorted subslice after `index`, whose elements all satisfy `x >= self[index]`.
 *
 * # Current implementation
 *
 * The current algorithm is an introselect implementation based on [ipnsort] by Lukas Bergdoll
 * and Orson Peters, which is also the basis for [sortUnstable]. The fallback algorithm is
 * Median of Medians using Tukey's Ninther for pivot selection, which guarantees linear runtime
 * for all inputs.
 *
 * [sortUnstable]: slice.sortUnstable
 *
 * # Panics
 *
 * Panics when `index >= len()`, and so always panics on empty slices.
 *
 * May panic if the implementation of [Ord] for `T` does not implement a [total order].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 *
 * var v = intArrayOf(-5, 4, 2, -3, 1)
 * // Find the items `<=` to the median, the median itself, and the items `>=` to it.
 * val (lesser, median, greater) = v.selectNthUnstable(2)
 * check(lesser == listOf(-3, -5) || lesser == listOf(-5, -3))
 * check(median == RustMutableReference(allocateNativeCell(1, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
 * check(greater == listOf(4, 2) || greater == listOf(2, 4))
 * // We are only guaranteed the slice will be one of the following, based on the way we sort
 * // about the specified index.
 * check(v.contentEquals(listOf(-3, -5, 1, 2, 4)) || v.contentEquals(listOf(-5, -3, 1, 2, 4)) || v.contentEquals(listOf(-3, -5, 1, 4, 2)) || v.contentEquals(listOf(-5, -3, 1, 4, 2)))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T> List<T>.selectNthUnstable(index: Int, ltTFn: (T, Rhs) -> Boolean): Triple<MutableList<T>, T, MutableList<T>> = partitionAtIndex(this, index, ltTFn)

/**
 * Reorders the slice with a comparator function such that the element at `index` is at a
 * sort-order position. All elements before `index` will be `<=` to this value, and all
 * elements after will be `>=` to it, according to the comparator function.
 *
 * This reordering is unstable (i.e. any element that compares equal to the nth element may end
 * up at that position), in-place (i.e.  does not allocate), and runs in *O*(*n*) time. This
 * function is also known as "kth element" in other libraries.
 *
 * Returns a triple partitioning the reordered slice:
 *
 * * The unsorted subslice before `index`, whose elements all satisfy
 *   `compare(x, self[index]).isLe()`.
 *
 * * The element at `index`.
 *
 * * The unsorted subslice after `index`, whose elements all satisfy
 *   `compare(x, self[index]).isGe()`.
 *
 * # Current implementation
 *
 * The current algorithm is an introselect implementation based on [ipnsort] by Lukas Bergdoll
 * and Orson Peters, which is also the basis for [sortUnstable]. The fallback algorithm is
 * Median of Medians using Tukey's Ninther for pivot selection, which guarantees linear runtime
 * for all inputs.
 *
 * [sortUnstable]: slice.sortUnstable
 *
 * # Panics
 *
 * Panics when `index >= len()`, and so always panics on empty slices.
 *
 * May panic if `compare` does not implement a [total order].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 *
 * var v = intArrayOf(-5, 4, 2, -3, 1)
 * // Find the items `>=` to the median, the median itself, and the items `<=` to it, by using
 * // a reversed comparator.
 * val (before, median, after) = v.selectNthUnstableBy(2, { a, b -> b.cmp(a) })
 * check(before == listOf(4, 2) || before == listOf(2, 4))
 * check(median == RustMutableReference(allocateNativeCell(1, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
 * check(after == listOf(-3, -5) || after == listOf(-5, -3))
 * // We are only guaranteed the slice will be one of the following, based on the way we sort
 * // about the specified index.
 * check(v.contentEquals(listOf(2, 4, 1, -5, -3)) || v.contentEquals(listOf(2, 4, 1, -3, -5)) || v.contentEquals(listOf(4, 2, 1, -5, -3)) || v.contentEquals(listOf(4, 2, 1, -3, -5)))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T> List<T>.selectNthUnstableBy(index: Int, compare: (T, T) -> Ordering): Triple<MutableList<T>, T, MutableList<T>> = partitionAtIndex(this, index, { a: T, b: T -> compare(a, b) == Less })

/**
 * Reorders the slice with a key extraction function such that the element at `index` is at a
 * sort-order position. All elements before `index` will have keys `<=` to the key at `index`,
 * and all elements after will have keys `>=` to it.
 *
 * This reordering is unstable (i.e. any element that compares equal to the nth element may end
 * up at that position), in-place (i.e.  does not allocate), and runs in *O*(*n*) time. This
 * function is also known as "kth element" in other libraries.
 *
 * Returns a triple partitioning the reordered slice:
 *
 * * The unsorted subslice before `index`, whose elements all satisfy `f(x) <= f(self[index])`.
 *
 * * The element at `index`.
 *
 * * The unsorted subslice after `index`, whose elements all satisfy `f(x) >= f(self[index])`.
 *
 * # Current implementation
 *
 * The current algorithm is an introselect implementation based on [ipnsort] by Lukas Bergdoll
 * and Orson Peters, which is also the basis for [sortUnstable]. The fallback algorithm is
 * Median of Medians using Tukey's Ninther for pivot selection, which guarantees linear runtime
 * for all inputs.
 *
 * [sortUnstable]: slice.sortUnstable
 *
 * # Panics
 *
 * Panics when `index >= len()`, meaning it always panics on empty slices.
 *
 * May panic if `K: Ord` does not implement a total order.
 *
 * # Examples
 *
 * ```kotlin
 * var v = intArrayOf(-5, 4, 1, -3, 2)
 * // Find the items `<=` to the absolute median, the absolute median itself, and the items
 * // `>=` to it.
 * val (lesser, median, greater) = v.selectNthUnstableByKey(2, { a -> a.abs() })
 * check(lesser == listOf(1, 2) || lesser == listOf(2, 1))
 * check(median == -3)
 * check(greater == listOf(4, -5) || greater == listOf(-5, 4))
 * // We are only guaranteed the slice will be one of the following, based on the way we sort
 * // about the specified index.
 * check(v.contentEquals(listOf(1, 2, -3, 4, -5)) || v.contentEquals(listOf(1, 2, -3, -5, 4)) || v.contentEquals(listOf(2, 1, -3, 4, -5)) || v.contentEquals(listOf(2, 1, -3, -5, 4)))
 * ```
 *
 * [ipnsort]: https://github.com/Voultapher/sort-research-rs/tree/main/ipnsort
 * [total order]: https://en.wikipedia.org/wiki/TotalOrder
 */
public fun <T, K> List<T>.selectNthUnstableByKey(index: Int, f: (T) -> K): Triple<MutableList<T>, T, MutableList<T>> = partitionAtIndex(this, index, { a: T, b: T -> (kmPartialCompareValues(f(a), f(b))?.let { cmpSign -> cmpSign < 0 } == true) })

/**
 * Moves all consecutive repeated elements to the end of the slice according to the
 * [PartialEq] trait implementation.
 *
 * Returns two slices. The first contains no consecutive repeated elements.
 * The second contains all the duplicates in no specified order.
 *
 * If the slice is sorted, the first returned slice contains no duplicates.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * @feature(slicePartitionDedup)
 * public fun doctestEntry() {
 *     var slice = mutableListOf(1, 2, 2, 3, 3, 2, 1, 1)
 *     val (dedup, duplicates) = slice.partitionDedup()
 *     check(dedup == listOf(1, 2, 3, 2, 1))
 *     check(duplicates == listOf(2, 3, 1))
 * }
 * ```
 */
public fun <T> List<T>.partitionDedup(): Pair<MutableList<T>, MutableList<T>> = this.partitionDedupBy({ a, b -> a == b })

/**
 * Moves all but the first of consecutive elements to the end of the slice that are
 * "equal" according to the given predicate function.
 *
 * Returns two slices. The first contains no consecutive repeated elements.
 * The second contains all the duplicates in no specified order.
 *
 * The predicate `sameBucket(x, p)` is passed references to two elements from
 * the slice and must determine if the elements compare equal. The element `p` occurs
 * *before* `x` in the slice (`[.., p, .., x, ..]`), so `sameBucket(x, p)`
 * is receiving them in reversed order.
 *
 * If the slice is sorted, the first returned slice contains no duplicates. For more
 * complicated predicates however, the order (ascending vs. descending) can matter.
 *
 * Both references passed to `sameBucket` are mutable.
 * This allows merged elements in the first slice by mutating `p` and returning `true`.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * @feature(slicePartitionDedup)
 * public fun doctestEntry() {
 *     var slice = mutableListOf("foo", "Foo", "BAZ", "Bar", "bar", "baz", "BAZ")
 *     val (dedup, duplicates) = slice.partitionDedupBy({ x, p -> x.eqIgnoreAsciiCase(p) })
 *     check(dedup == listOf("foo", "BAZ", "Bar", "baz"))
 *     check(duplicates == listOf("bar", "Foo", "BAZ"))
 * }
 * ```
 */
public fun <T> List<T>.partitionDedupBy(sameBucket: (T, T) -> Boolean): Pair<MutableList<T>, MutableList<T>> {
    // Although we have a mutable reference to `self`, we cannot make
    // *arbitrary* changes. The `sameBucket` calls could panic, so we
    // must ensure that the slice is in a valid state at all times.
    //
    // The way that we handle this is by using swaps; we iterate
    // over all the elements, swapping as we go so that at the end
    // the elements we wish to keep are in the front, and those we
    // wish to reject are at the back. We can then split the slice.
    // This operation is still `O(n)`.
    //
    // Example: We start in this state, where `r` represents "next
    // read" and `w` represents "nextWrite".
    //
    // r
    // +---+---+---+---+---+---+
    // | 0 | 1 | 1 | 2 | 3 | 3 |
    // +---+---+---+---+---+---+
    // w
    //
    // Comparing self[r] against self[w-1], this is not a duplicate, so
    // we swap self[r] and self[w] (no effect as r==w) and then increment both
    // r and w, leaving us with:
    //
    // r
    // +---+---+---+---+---+---+
    // | 0 | 1 | 1 | 2 | 3 | 3 |
    // +---+---+---+---+---+---+
    // w
    //
    // Comparing self[r] against self[w-1], this value is a duplicate,
    // so we increment `r` but leave everything else unchanged:
    //
    // r
    // +---+---+---+---+---+---+
    // | 0 | 1 | 1 | 2 | 3 | 3 |
    // +---+---+---+---+---+---+
    // w
    //
    // Comparing self[r] against self[w-1], this is not a duplicate,
    // so swap self[r] and self[w] and advance r and w:
    //
    // r
    // +---+---+---+---+---+---+
    // | 0 | 1 | 2 | 1 | 3 | 3 |
    // +---+---+---+---+---+---+
    // w
    //
    // Not a duplicate, repeat:
    //
    // r
    // +---+---+---+---+---+---+
    // | 0 | 1 | 2 | 3 | 1 | 3 |
    // +---+---+---+---+---+---+
    // w
    //
    // Duplicate, advance r. End of slice. Split at w.
    val len = this.size
    if (len <= 1) {
        return Pair(this, listOf())
    }
    val ptr = this.asMutPtr()
    var nextRead: Int = 1
    var nextWrite: Int = 1
    // SAFETY: the `while` condition guarantees `nextRead` and `nextWrite`
    // are less than `len`, thus are inside `self`. `prevPtrWrite` points to
    // one element before `ptrWrite`, but `nextWrite` starts at 1, so
    // `prevPtrWrite` is never less than 0 and is inside the slice.
    // This fulfills the requirements for dereferencing `ptrRead`, `prevPtrWrite`
    // and `ptrWrite`, and for using `ptr.add(nextRead)`, `ptr.add(nextWrite - 1)`
    // and `prevPtrWrite.offset(1)`.
    //
    // `nextWrite` is also incremented at most once per loop at most meaning
    // no element is skipped when it may need to be swapped.
    //
    // `ptrRead` and `prevPtrWrite` never point to the same element. This
    // is required for `* ptrRead`, `* prevPtrWrite` to be safe.
    // The explanation is simply that `nextRead >= nextWrite` is always true,
    // thus `nextRead> nextWrite - 1` is too.
    while (nextRead < len) {
        val ptrRead = MutPtr((ptr).rawAddress + ((nextRead).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        val prevPtrWrite = MutPtr((ptr).rawAddress + ((nextWrite - 1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        if (!sameBucket(RustMutableReference(MutPtr((ptrRead).rawAddress), UnresolvedTypeLayout<T>()), RustMutableReference(MutPtr((prevPtrWrite).rawAddress), UnresolvedTypeLayout<T>()))) {
            if (nextRead != nextWrite) {
                val ptrWrite = MutPtr((prevPtrWrite).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
                io.github.kotlinmania.rustlang.core.mem.swap(RustMutableReference(MutPtr((ptrRead).rawAddress), UnresolvedTypeLayout<T>()), RustMutableReference(MutPtr((ptrWrite).rawAddress), UnresolvedTypeLayout<T>()))
            }
            nextWrite += 1
        }
        nextRead += 1
    }
    return this.splitAtMut(nextWrite)
}

/**
 * Moves all but the first of consecutive elements to the end of the slice that resolve
 * to the same key.
 *
 * Returns two slices. The first contains no consecutive repeated elements.
 * The second contains all the duplicates in no specified order.
 *
 * If the slice is sorted, the first returned slice contains no duplicates.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * @feature(slicePartitionDedup)
 * public fun doctestEntry() {
 *     var slice = mutableListOf(10, 20, 21, 30, 30, 20, 11, 13)
 *     val (dedup, duplicates) = slice.partitionDedupByKey({ i -> i / 10 })
 *     check(dedup == listOf(10, 20, 30, 20, 11))
 *     check(duplicates == listOf(21, 30, 13))
 * }
 * ```
 */
public fun <T, K> List<T>.partitionDedupByKey(key: (T) -> K): Pair<MutableList<T>, MutableList<T>> = this.partitionDedupBy({ a, b -> key(a) == key(b) })

/**
 * Rotates the slice in-place such that the first `mid` elements of the
 * slice move to the end while the last `self.len() - mid` elements move to
 * the front.
 *
 * After calling `rotateLeft`, the element previously at index `mid` will
 * become the first element in the slice.
 *
 * # Panics
 *
 * This function will panic if `mid` is greater than the length of the
 * slice. Note that `mid == self.len()` does _not_ panic and is a no-op
 * rotation.
 *
 * # Complexity
 *
 * Takes linear (in `self.len()`) time.
 *
 * # Examples
 *
 * ```kotlin
 * var a = mutableListOf('a', 'b', 'c', 'd', 'e', 'f')
 * a.rotateLeft(2)
 * check(a == listOf('c', 'd', 'e', 'f', 'a', 'b'))
 * ```
 *
 * Rotating a subslice:
 *
 * ```kotlin
 * var a = mutableListOf('a', 'b', 'c', 'd', 'e', 'f')
 * a[1 until 5].rotateLeft(1)
 * check(a == listOf('a', 'c', 'd', 'e', 'b', 'f'))
 * ```
 */
public fun <T> List<T>.rotateLeft(mid: Int) {
    check(mid <= this.size)
    val k = this.size - mid
    val p = this.asMutPtr()
    // SAFETY: The range `[p.add(mid) - mid, p.add(mid) + k)` is trivially
    // valid for reading and writing, as required by `ptrRotate`.
    run {
        ptrRotate(mid, MutPtr((p).rawAddress + ((mid).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), k)
    }
}

/**
 * Rotates the slice in-place such that the first `self.len() - k`
 * elements of the slice move to the end while the last `k` elements move
 * to the front.
 *
 * After calling `rotateRight`, the element previously at index
 * `self.len() - k` will become the first element in the slice.
 *
 * # Panics
 *
 * This function will panic if `k` is greater than the length of the
 * slice. Note that `k == self.len()` does _not_ panic and is a no-op
 * rotation.
 *
 * # Complexity
 *
 * Takes linear (in `self.len()`) time.
 *
 * # Examples
 *
 * ```kotlin
 * var a = mutableListOf('a', 'b', 'c', 'd', 'e', 'f')
 * a.rotateRight(2)
 * check(a == listOf('e', 'f', 'a', 'b', 'c', 'd'))
 * ```
 *
 * Rotating a subslice:
 *
 * ```kotlin
 * var a = mutableListOf('a', 'b', 'c', 'd', 'e', 'f')
 * a[1 until 5].rotateRight(1)
 * check(a == listOf('a', 'e', 'b', 'c', 'd', 'f'))
 * ```
 */
public fun <T> List<T>.rotateRight(k: Int) {
    check(k <= this.size)
    val mid = this.size - k
    val p = this.asMutPtr()
    // SAFETY: The range `[p.add(mid) - mid, p.add(mid) + k)` is trivially
    // valid for reading and writing, as required by `ptrRotate`.
    run {
        ptrRotate(mid, MutPtr((p).rawAddress + ((mid).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), k)
    }
}

/**
 * Moves the elements of this slice `N` places to the left, returning the ones
 * that "fall off" the front, and putting `inserted` at the end.
 *
 * Equivalently, you can think of concatenating `self` and `inserted` into one
 * long sequence, then returning the left-most `N` items and the rest into `self`:
 *
 * ```text
 *           self (before)    inserted
 *           vvvvvvvvvvvvvvv  vvv
 *           [1, 2, 3, 4, 5]  [9]
 *        ↙   ↙  ↙  ↙  ↙   ↙
 *      [1]  [2, 3, 4, 5, 9]
 *      ^^^  ^^^^^^^^^^^^^^^
 * returned  self (after)
 * ```
 *
 * See also [Self.shiftRight] and compare [Self.rotateLeft].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(sliceShift)
 * public fun doctestEntry() {
 *     // Same as the diagram above
 *     var a = mutableListOf(1, 2, 3, 4, 5)
 *     val inserted = listOf(9)
 *     val returned = a.shiftLeft(inserted)
 *     check(returned == listOf(1))
 *     check(a == listOf(2, 3, 4, 5, 9))
 *     // You can shift multiple items at a time
 *     var a1 = "Hello world".encodeToByteArray()
 *     check(a1.shiftLeft(" peace".encodeToByteArray()) == "Hello ".encodeToByteArray())
 *     check(a1 == "world peace".encodeToByteArray())
 *     // The name comes from this operation's similarity to bitshifts
 *     var a2: UByte = 0x96u
 *     a2 = ((a2).toUInt() shl 3).toUByte()
 *     check(a2 == 0xB0u.toUByte())
 *     var a3: List<Any> = listOf(1, 0, 0, 1, 0, 1, 1, 0)
 *     a3.shiftLeft(List(3) { 0 })
 *     check(a3 == listOf(1, 0, 1, 1, 0, 0, 0, 0))
 *     // Remember you can sub-slice to affect less that the whole slice.
 *     // For example, this is similar to `.remove(1)` + `.insert(4, `Z`)`
 *     var a4 = mutableListOf('a', 'b', 'c', 'd', 'e', 'f')
 *     check(a4[1..4].shiftLeft(listOf('Z')) == listOf('b'))
 *     check(a4 == listOf('a', 'c', 'd', 'e', 'Z', 'f'))
 *     // If the size matches it's equivalent to `mem.replace`
 *     var a5 = mutableListOf(1, 2, 3)
 *     check(a5.shiftLeft(listOf(7, 8, 9)) == listOf(1, 2, 3))
 *     check(a5 == listOf(7, 8, 9))
 *     // Some of the "inserted" elements end up returned if the slice is too short
 *     var a6 = mutableListOf()
 *     check(a6.shiftLeft(listOf(1, 2, 3)) == listOf(1, 2, 3))
 *     var a7 = mutableListOf(9)
 *     check(a7.shiftLeft(listOf(1, 2, 3)) == listOf(9, 1, 2))
 *     check(a7 == listOf(3))
 * }
 * ```
 */
public fun <T> List<T>.shiftLeft(inserted: List<T>, n: Int = 0): List<T> {
    return when (val matchVal = this.size.checkedSub(n)) {
        null -> {
            // SAFETY: Having checked that the slice is strictly shorter than the
            // inserted/returned arrays, it means we'll be copying the whole slice
            // into the returned array, but that's not enough on its own.  We also
            // need to copy some of the inserted array into the returned array,
            // with the rest going into the slice.  Because  is exclusive
            // and we own both `inserted` and `returned`, they're all disjoint
            // allocations from each other as we can use `nonoverlapping` copies.
            //
            // We avoid double-frees by `ManuallyDrop`ing the inserted items,
            // since we always copy them to other locations that will drop them
            // instead.  Plus nothing in here can panic -- it's just memcpy three
            // times -- so there's no intermediate unwinds to worry about.
            run {
                val len = this.size
                val slice = this.asMutPtr()
                val localStorageL4053C16 = AddressableLocal(inserted, UnresolvedTypeLayout<Any?>())
                val inserted1 by localStorageL4053C16
                val inserted2 = ConstPtr(((localStorageL4053C16.asConstPtr())).rawAddress)
                val returned = MaybeUninit.uninit(UnresolvedTypeLayout<List<T>>())
                val ptr = returned.asMutPtr().cast()
                ptr.copyFromNonoverlapping(slice, len)
                ptr.add(len).copyFromNonoverlapping(inserted2, n - len)
                kmCopyNonoverlapping((inserted2.add(n - len)).rawAddress, (slice).rawAddress, (len).toLong() * (UnresolvedTypeLayout<T>()).sizeBytes)
                returned.assumeInit()
            }
        }
        else -> {
            val shift = matchVal
            // SAFETY: Having just checked that the inserted/returned arrays are
            // shorter than (or the same length as) the slice:
            // 1. The read for the items to return is in-bounds
            // 2. We can `memmove` the slice over to cover the items we're returning
            // to ensure those aren't double-dropped
            // 3. Then we write (in-bounds for the same reason as the read) the
            // inserted items atop the items of the slice that we just duplicated
            //
            // And none of this can panic, so there's no risk of intermediate unwinds.
            run {
                val ptr1 = this.asMutPtr()
                val returned1 = ptr1.castArray(n).read()
                kmCopy((MutPtr((ptr1).rawAddress + ((n).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))).rawAddress, (ptr1).rawAddress, (shift).toLong() * (UnresolvedTypeLayout<T>()).sizeBytes)
                MutPtr((ptr1).rawAddress + ((shift).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)).castArray(n).write(inserted)
                returned1
            }
        }
    }
}

/**
 * Moves the elements of this slice `N` places to the right, returning the ones
 * that "fall off" the back, and putting `inserted` at the beginning.
 *
 * Equivalently, you can think of concatenating `inserted` and `self` into one
 * long sequence, then returning the right-most `N` items and the rest into `self`:
 *
 * ```text
 * inserted  self (before)
 *      vvv  vvvvvvvvvvvvvvv
 *      [0]  [5, 6, 7, 8, 9]
 *        ↘   ↘  ↘  ↘  ↘   ↘
 *           [0, 5, 6, 7, 8]  [9]
 *           ^^^^^^^^^^^^^^^  ^^^
 *           self (after)     returned
 * ```
 *
 * See also [Self.shiftLeft] and compare [Self.rotateRight].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(sliceShift)
 * public fun doctestEntry() {
 *     // Same as the diagram above
 *     var a = mutableListOf(5, 6, 7, 8, 9)
 *     val inserted = listOf(0)
 *     val returned = a.shiftRight(inserted)
 *     check(returned == listOf(9))
 *     check(a == listOf(0, 5, 6, 7, 8))
 *     // The name comes from this operation's similarity to bitshifts
 *     var a1: UByte = 0x96u
 *     a1 = ((a1).toUInt() shr 3).toUByte()
 *     check(a1 == 0x12u.toUByte())
 *     var a2: List<Any> = listOf(1, 0, 0, 1, 0, 1, 1, 0)
 *     a2.shiftRight(List(3) { 0 })
 *     check(a2 == listOf(0, 0, 0, 1, 0, 0, 1, 0))
 *     // Remember you can sub-slice to affect less that the whole slice.
 *     // For example, this is similar to `.remove(4)` + `.insert(1, `Z`)`
 *     var a3 = mutableListOf('a', 'b', 'c', 'd', 'e', 'f')
 *     check(a3[1..4].shiftRight(listOf('Z')) == listOf('e'))
 *     check(a3 == listOf('a', 'Z', 'b', 'c', 'd', 'f'))
 *     // If the size matches it's equivalent to `mem.replace`
 *     var a4 = mutableListOf(1, 2, 3)
 *     check(a4.shiftRight(listOf(7, 8, 9)) == listOf(1, 2, 3))
 *     check(a4 == listOf(7, 8, 9))
 *     // Some of the "inserted" elements end up returned if the slice is too short
 *     var a5 = mutableListOf()
 *     check(a5.shiftRight(listOf(1, 2, 3)) == listOf(1, 2, 3))
 *     var a6 = mutableListOf(9)
 *     check(a6.shiftRight(listOf(1, 2, 3)) == listOf(2, 3, 9))
 *     check(a6 == listOf(1))
 * }
 * ```
 */
public fun <T> List<T>.shiftRight(inserted: List<T>, n: Int = 0): List<T> {
    return when (val matchVal = this.size.checkedSub(n)) {
        null -> {
            // SAFETY: Having checked that the slice is strictly shorter than the
            // inserted/returned arrays, it means we'll be copying the whole slice
            // into the returned array, but that's not enough on its own.  We also
            // need to copy some of the inserted array into the returned array,
            // with the rest going into the slice.  Because  is exclusive
            // and we own both `inserted` and `returned`, they're all disjoint
            // allocations from each other as we can use `nonoverlapping` copies.
            //
            // We avoid double-frees by `ManuallyDrop`ing the inserted items,
            // since we always copy them to other locations that will drop them
            // instead.  Plus nothing in here can panic -- it's just memcpy three
            // times -- so there's no intermediate unwinds to worry about.
            run {
                val len = this.size
                val slice = this.asMutPtr()
                val localStorageL4157C16 = AddressableLocal(inserted, UnresolvedTypeLayout<Any?>())
                val inserted1 by localStorageL4157C16
                val inserted2 = ConstPtr(((localStorageL4157C16.asConstPtr())).rawAddress)
                val returned = MaybeUninit.uninit(UnresolvedTypeLayout<List<T>>())
                val ptr = returned.asMutPtr().cast()
                ptr.add(n - len).copyFromNonoverlapping(slice, len)
                ptr.copyFromNonoverlapping(inserted2.add(len), n - len)
                kmCopyNonoverlapping((inserted2).rawAddress, (slice).rawAddress, (len).toLong() * (UnresolvedTypeLayout<T>()).sizeBytes)
                returned.assumeInit()
            }
        }
        else -> {
            val shift = matchVal
            // SAFETY: Having just checked that the inserted/returned arrays are
            // shorter than (or the same length as) the slice:
            // 1. The read for the items to return is in-bounds
            // 2. We can `memmove` the slice over to cover the items we're returning
            // to ensure those aren't double-dropped
            // 3. Then we write (in-bounds for the same reason as the read) the
            // inserted items atop the items of the slice that we just duplicated
            //
            // And none of this can panic, so there's no risk of intermediate unwinds.
            run {
                val ptr1 = this.asMutPtr()
                val returned1 = MutPtr((ptr1).rawAddress + ((shift).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)).castArray(n).read()
                kmCopy((ptr1).rawAddress, (MutPtr((ptr1).rawAddress + ((n).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))).rawAddress, (shift).toLong() * (UnresolvedTypeLayout<T>()).sizeBytes)
                ptr1.castArray(n).write(inserted)
                returned1
            }
        }
    }
}

/**
 * Fills `self` with elements by cloning `value`.
 *
 * # Examples
 *
 * ```kotlin
 * var buf = mutableListOf(0, 10)
 * buf.fill(1)
 * check(buf == mutableListOf(1, 10))
 * ```
 */
public fun <T> List<T>.fill(value: T) {
    this.specFill(value)
}

/**
 * Fills `self` with elements returned by calling a closure repeatedly.
 *
 * This method uses a closure to create new values. If you'd rather
 * [Clone] a given value, use [fill]. If you want to use the [Default]
 * trait to generate values, you can pass [Default.default] as the
 * argument.
 *
 * [fill]: slice.fill
 *
 * # Examples
 *
 * ```kotlin
 * var buf = mutableListOf(1, 10)
 * buf.fillWith(Default.default)
 * check(buf == mutableListOf(0, 10))
 * ```
 */
public fun <T> List<T>.fillWith(f: () -> T) {
    for (el in this) {
        el = f()
    }
}

/**
 * Copies the elements from `src` into `self`.
 *
 * The length of `src` must be the same as `self`.
 *
 * # Panics
 *
 * This function will panic if the two slices have different lengths.
 *
 * # Examples
 *
 * Cloning two elements from a slice into another:
 *
 * ```kotlin
 * val src = listOf(1, 2, 3, 4)
 * var dst = mutableListOf(0, 0)
 * // Because the slices have to be the same length,
 * // we slice the source slice from four elements
 * // to two. It will panic if we don't do this.
 * dst.cloneFromSlice(src[2 until src.size])
 * check(src == listOf(1, 2, 3, 4))
 * check(dst == listOf(3, 4))
 * ```
 *
 * Rust enforces that there can only be one mutable reference with no
 * immutable references to a particular piece of data in a particular
 * scope. Because of this, attempting to use `cloneFromSlice` on a
 * single slice will result in a compile failure:
 *
 * ```compile_fail
 * let mut slice = [1, 2, 3, 4, 5];
 *
 * slice[..2].cloneFromSlice(&slice[3..]); // compile fail!
 * ```
 *
 * To work around this, we can use [splitAtMut] to create two distinct
 * sub-slices from a slice:
 *
 * ```kotlin
 * import slice
 *
 * var slice = mutableListOf(1, 2, 3, 4, 5)
 * run {
 *     val (left, right) = slice.splitAtMut(2)
 *     left.cloneFromSlice(right[1 until right.size])
 * }
 * check(slice == listOf(4, 5, 3, 4, 5))
 * ```
 *
 * [copyFromSlice]: slice.copyFromSlice
 * [splitAtMut]: slice.splitAtMut
 */
public fun <T> List<T>.cloneFromSlice(src: List<T>) {
    this.specCloneFrom(src)
}

/**
 * Copies all elements from `src` into `self`, using a memcpy.
 *
 * The length of `src` must be the same as `self`.
 *
 * If `T` does not implement `Copy`, use [cloneFromSlice].
 *
 * # Panics
 *
 * This function will panic if the two slices have different lengths.
 *
 * # Examples
 *
 * Copying two elements from a slice into another:
 *
 * ```kotlin
 * val src = listOf(1, 2, 3, 4)
 * var dst = mutableListOf(0, 0)
 * // Because the slices have to be the same length,
 * // we slice the source slice from four elements
 * // to two. It will panic if we don't do this.
 * dst.copyFromSlice(src[2 until src.size])
 * check(src == listOf(1, 2, 3, 4))
 * check(dst == listOf(3, 4))
 * ```
 *
 * Rust enforces that there can only be one mutable reference with no
 * immutable references to a particular piece of data in a particular
 * scope. Because of this, attempting to use `copyFromSlice` on a
 * single slice will result in a compile failure:
 *
 * ```compile_fail
 * let mut slice = [1, 2, 3, 4, 5];
 *
 * slice[..2].copyFromSlice(&slice[3..]); // compile fail!
 * ```
 *
 * To work around this, we can use [splitAtMut] to create two distinct
 * sub-slices from a slice:
 *
 * ```kotlin
 * import slice
 *
 * var slice = mutableListOf(1, 2, 3, 4, 5)
 * run {
 *     val (left, right) = slice.splitAtMut(2)
 *     left.copyFromSlice(right[1 until right.size])
 * }
 * check(slice == listOf(4, 5, 3, 4, 5))
 * ```
 *
 * [cloneFromSlice]: slice.cloneFromSlice
 * [splitAtMut]: slice.splitAtMut
 */
public fun <T> List<T>.copyFromSlice(src: List<T>) {
    // SAFETY: `T` implements `Copy`.
    copyFromSliceImpl(this, src)
}

/**
 * Copies elements from one part of the slice to another part of itself,
 * using a memmove.
 *
 * `src` is the range within `self` to copy from. `dest` is the starting
 * index of the range within `self` to copy to, which will have the same
 * length as `src`. The two ranges may overlap. The ends of the two ranges
 * must be less than or equal to `self.len()`.
 *
 * # Panics
 *
 * This function will panic if either range exceeds the end of the slice,
 * or if the end of `src` is before the start.
 *
 * # Examples
 *
 * Copying four bytes within a slice:
 *
 * ```kotlin
 * var bytes = "Hello, World!".encodeToByteArray()
 * bytes.copyWithin(1 until 5, 8)
 * check(bytes == "Hello, Wello!".encodeToByteArray())
 * ```
 */
public fun <T, R : RangeBounds<Int>> List<T>.copyWithin(src: R, dest: Int) {
    val structVal = range(src, 0 until this.size)
    val srcStart = structVal.start
    val srcEnd = structVal.end
    val count = srcEnd - srcStart
    check(dest <= this.size - count.toInt()) { "dest is out of bounds" }
    // SAFETY: the conditions for `ptr.copy` have all been checked above,
    // as have those for `ptr.add`.
    run {
        // Derive both `srcPtr` and `destPtr` from the same loan
        val ptr = this.asMutPtr()
        val srcPtr = MutPtr((ptr).rawAddress + ((srcStart).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        val destPtr = MutPtr((ptr).rawAddress + ((dest).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
        copy((srcPtr).asConstPtr(), destPtr, count.toInt())
    }
}

/**
 * Swaps all elements in `self` with those in `other`.
 *
 * The length of `other` must be the same as `self`.
 *
 * # Panics
 *
 * This function will panic if the two slices have different lengths.
 *
 * # Example
 *
 * Swapping two elements across slices:
 *
 * ```kotlin
 * var slice1 = mutableListOf(0, 0)
 * var slice2 = mutableListOf(1, 2, 3, 4)
 * slice1.swapWithSlice(slice2.subList(2, slice2.size))
 * check(slice1 == listOf(3, 4))
 * check(slice2 == listOf(1, 2, 0, 0))
 * ```
 *
 * Rust enforces that there can only be one mutable reference to a
 * particular piece of data in a particular scope. Because of this,
 * attempting to use `swapWithSlice` on a single slice will result in
 * a compile failure:
 *
 * ```compile_fail
 * let mut slice = [1, 2, 3, 4, 5];
 * slice[..2].swapWithSlice(slice[3..]); // compile fail!
 * ```
 *
 * To work around this, we can use [splitAtMut] to create two distinct
 * mutable sub-slices from a slice:
 *
 * ```kotlin
 * import slice
 *
 * var slice = mutableListOf(1, 2, 3, 4, 5)
 * run {
 *     val (left, right) = slice.splitAtMut(2)
 *     left.swapWithSlice(right[1 until right.size])
 * }
 * check(slice == listOf(4, 5, 3, 1, 2))
 * ```
 *
 * [splitAtMut]: slice.splitAtMut
 */
public fun <T> List<T>.swapWithSlice(other: MutableList<T>) {
    check(this.size == other.size) { "destination and source slices have different lengths" }
    // SAFETY: `self` is valid for `self.len()` elements by definition, and `src` was
    // checked to have the same length. The slices cannot overlap because
    // mutable references are exclusive.
    run {
        swapNonoverlapping(this.asMutPtr(), other.asMutPtr(), this.size)
    }
}

/**
 * Function to calculate lengths of the middle and trailing slice for `alignTo ( , _mut )`.
 */
public fun <T, U> List<T>.alignToOffsets(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): Pair<Int, Int> {
    // What we gonna do about `rest` is figure out what multiple of `U`s we can put in a
    // lowest number of `T`s. And how many `T`s we need for each such "multiple".
    //
    // Consider for example T=u8 U=u16. Then we can put 1 U in 2 Ts. Simple. Now, consider
    // for example a case where sizeOf::<T> = 16, sizeOf::<U> = 24. We can put 2 Us in
    // place of every 3 Ts in the `rest` slice. A bit more complicated.
    //
    // Formula to calculate this is:
    //
    // Us = lcm(sizeOf::<T>, sizeOf::<U>) / sizeOf::<U>
    // Ts = lcm(sizeOf::<T>, sizeOf::<U>) / sizeOf::<T>
    //
    // Expanded and simplified:
    //
    // Us = sizeOf::<T> / gcd(sizeOf::<T>, sizeOf::<U>)
    // Ts = sizeOf::<U> / gcd(sizeOf::<T>, sizeOf::<U>)
    //
    // Luckily since all this is constant-evaluated... performance here matters not!
    fun gcd(a: Int, b: Int): Int =
        if (b == 0) {
            a
        } else {
            gcd(b, a % b)
        }
    // Explicitly wrap the function call in a const block so it gets
    // constant-evaluated even in debug mode.
    val gcd: Int = gcd(sizeOf<T>(typeLayoutT = typeLayoutT), sizeOf<U>(typeLayoutT = typeLayoutU))
    val ts: Int = sizeOf<U>(typeLayoutT = typeLayoutU) / gcd
    val us: Int = sizeOf<T>(typeLayoutT = typeLayoutT) / gcd
    // Armed with this knowledge, we can find how many `U`s we can fit!
    val usLen = this.size / ts * us
    // And how many `T`s will be in the trailing slice!
    val tsLen = this.size % ts
    return Pair(usLen, tsLen)
}

/**
 * Transmutes the slice to a slice of another type, ensuring alignment of the types is
 * maintained.
 *
 * This method splits the slice into three distinct slices: prefix, correctly aligned middle
 * slice of a new type, and the suffix slice. The middle part will be as big as possible under
 * the given alignment constraint and element size.
 *
 * This method has no purpose when either input element `T` or output element `U` are
 * zero-sized and will return the original slice without splitting anything.
 *
 * # Safety
 *
 * This method is essentially a `reinterpret` with respect to the elements in the returned
 * middle slice, so all the usual caveats pertaining to `reinterpret<T, U>` also apply here.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     run {
 *         val bytes: UByteArray = ubyteArrayOf(1u, 2u, 3u, 4u, 5u, 6u, 7u)
 *         val (prefix, shorts, suffix) = bytes.alignTo<UShort>()
 *         // lessEfficientAlgorithmForBytes(prefix);
 *         // moreEfficientAlgorithmForAlignedShorts(shorts);
 *         // lessEfficientAlgorithmForBytes(suffix);
 *     }
 * }
 * ```
 */
public fun <T, U> List<T>.alignTo(typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout(), typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Triple<List<T>, List<U>, List<T>> {
    // Note that most of this function will be constant-evaluated,
    if (((typeLayoutU).sizeBytes == 0L) || ((typeLayoutT).sizeBytes == 0L)) {
        // handle ZSTs specially, which is – don't handle them at all.
        return Triple(this, listOf(), listOf())
    }
    // First, find at what point do we split between the first and 2nd slice. Easy with
    // ptr.alignOffset.
    val ptr = this.asPtr()
    // SAFETY: See the `alignToMut` method for the detailed safety comment.
    val offset = alignOffset(ptr, alignOf<U>(typeLayoutT = typeLayoutU))
    return if (offset > this.size) {
        Triple(this, listOf(), listOf())
    } else {
        val (left, rest) = this.splitAt(offset)
        val (usLen, tsLen) = rest.alignToOffsets<U>()
        // SAFETY: now `rest` is definitely aligned, so `fromRawParts` below is okay,
        // since the caller guarantees that we can reinterpret `T` to `U` safely.
        Triple(left, linearSliceView(constPtrFromRustCast(rest.asPtr()), usLen.toInt(), typeLayoutU), linearSliceView(rest.asPtr().add(rest.len() - tsLen), tsLen.toInt(), PrimitiveLayouts.U8))
    }
}

/**
 * Transmutes the mutable slice to a mutable slice of another type, ensuring alignment of the
 * types is maintained.
 *
 * This method splits the slice into three distinct slices: prefix, correctly aligned middle
 * slice of a new type, and the suffix slice. The middle part will be as big as possible under
 * the given alignment constraint and element size.
 *
 * This method has no purpose when either input element `T` or output element `U` are
 * zero-sized and will return the original slice without splitting anything.
 *
 * # Safety
 *
 * This method is essentially a `reinterpret` with respect to the elements in the returned
 * middle slice, so all the usual caveats pertaining to `reinterpret<T, U>` also apply here.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     run {
 *         var bytes: UByteArray = ubyteArrayOf(1u, 2u, 3u, 4u, 5u, 6u, 7u)
 *         val (prefix, shorts, suffix) = bytes.alignToMut<UShort>()
 *         // lessEfficientAlgorithmForBytes(prefix);
 *         // moreEfficientAlgorithmForAlignedShorts(shorts);
 *         // lessEfficientAlgorithmForBytes(suffix);
 *     }
 * }
 * ```
 */
public fun <T, U> List<T>.alignToMut(typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout(), typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Triple<MutableList<T>, MutableList<U>, MutableList<T>> {
    // Note that most of this function will be constant-evaluated,
    if (((typeLayoutU).sizeBytes == 0L) || ((typeLayoutT).sizeBytes == 0L)) {
        // handle ZSTs specially, which is – don't handle them at all.
        return Triple(this, listOf(), listOf())
    }
    // First, find at what point do we split between the first and 2nd slice. Easy with
    // ptr.alignOffset.
    val ptr = this.asPtr()
    // SAFETY: Here we are ensuring we will use aligned pointers for U for the
    // rest of the method. This is done by passing a pointer to &[T] with an
    // alignment targeted for U.
    // `ptr.alignOffset` is called with a correctly aligned and
    // valid pointer `ptr` (it comes from a reference to `self`) and with
    // a size that is a power of two (since it comes from the alignment for U),
    // satisfying its safety constraints.
    val offset = alignOffset(ptr, alignOf<U>(typeLayoutT = typeLayoutU))
    return if (offset > this.size) {
        Triple(this, listOf(), listOf())
    } else {
        val (left, rest) = this.splitAtMut(offset)
        val (usLen, tsLen) = rest.alignToOffsets<U>()
        val restLen = rest.len()
        val mutPtr = rest.asMutPtr()
        // We can't use `rest` again after this, that would invalidate its alias `mutPtr`!
        // SAFETY: see comments for `alignTo`.
        Triple(left, linearMutSliceView(mutPtrFromRustCast(mutPtr), usLen.toInt(), typeLayoutU), linearMutSliceView(mutPtr.add(restLen - tsLen), tsLen.toInt(), PrimitiveLayouts.U8))
    }
}

/**
 * Splits a slice into a prefix, a middle of aligned SIMD types, and a suffix.
 *
 * This is a safe wrapper around [slice.alignTo], so inherits the same
 * guarantees as that method.
 *
 * # Panics
 *
 * This will panic if the size of the SIMD type is different from
 * `LANES` times that of the scalar.
 *
 * At the time of writing, the trait restrictions on `Simd<T, LANES>` keeps
 * that from ever happening, as only power-of-two numbers of lanes are
 * supported.  It's possible that, in the future, those restrictions might
 * be lifted in a way that would make it possible to see panics from this
 * method for something like `LANES == 3`.
 *
 * # Examples
 *
 * ```kotlin
 * import core.simd.prelude.*
 * import simd.Simd
 *
 * @feature(portableSimd)
 * public fun doctestEntry() {
 *     fun basicSimdSum(x: List<Float>): Float {
 *         val (prefix, middle, suffix) = x.asSimd()
 *         val sums = Simd<Float>((listOf(prefix.iter().copied().sum(), 0.0, 0.0, suffix.iter().copied().sum())).toList())
 *         val sums1 = middle.iter().copied().fold(sums, add)
 *         return sums1.reduceSum()
 *     }
 *     val short = listOf(1, 2, 3)
 *     val (prefix, middle, suffix) = short.asSimd(4)
 *     check(middle == listOf())
 *     // Not enough elements for anything in the middle
 *     // They might be split in any possible way between prefix and suffix
 *     val it = prefix.iter().chain(suffix).copied()
 *     check(it.collect<Vec>() == mutableListOf(1, 2, 3))
 *     val numbers: List<Float> = (1 until 101).map({ x -> x }).toMutableList()
 *     check(basicSimdSum(((numbers).subList(1, 99))) == 4949.0f.toFloat())
 * }
 * ```
 */
public fun <T> List<T>.asSimd(lanes: Int = 0, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Triple<List<T>, List<Simd<T>>, List<T>> {
    when (val matchVal = Pair(sizeOf<Simd<T>>(typeLayoutT = UnresolvedTypeLayout<Simd<T>>()), sizeOf<List<T>>(typeLayoutT = fixedListTypeLayout(typeLayoutT, (lanes).toInt())))) {
        else -> {
            val leftVal = matchVal.first
            val rightVal = matchVal.second
            if (!(leftVal == rightVal)) {
                val kind = AssertKind.Eq
                assertFailed(kind, leftVal, rightVal, Option.None())
            }
        }
    }
    // SAFETY: The simd types have the same layout as arrays, just with
    // potentially-higher alignment, so the de-facto transmutes are sound.
    return this.alignTo()
}

/**
 * Splits a mutable slice into a mutable prefix, a middle of aligned SIMD types,
 * and a mutable suffix.
 *
 * This is a safe wrapper around [slice.alignToMut], so inherits the same
 * guarantees as that method.
 *
 * This is the mutable version of [slice.asSimd]; see that for examples.
 *
 * # Panics
 *
 * This will panic if the size of the SIMD type is different from
 * `LANES` times that of the scalar.
 *
 * At the time of writing, the trait restrictions on `Simd<T, LANES>` keeps
 * that from ever happening, as only power-of-two numbers of lanes are
 * supported.  It's possible that, in the future, those restrictions might
 * be lifted in a way that would make it possible to see panics from this
 * method for something like `LANES == 3`.
 */
public fun <T> List<T>.asSimdMut(lanes: Int = 0, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Triple<MutableList<T>, MutableList<Simd<T>>, MutableList<T>> {
    when (val matchVal = Pair(sizeOf<Simd<T>>(typeLayoutT = UnresolvedTypeLayout<Simd<T>>()), sizeOf<List<T>>(typeLayoutT = fixedListTypeLayout(typeLayoutT, (lanes).toInt())))) {
        else -> {
            val leftVal = matchVal.first
            val rightVal = matchVal.second
            if (!(leftVal == rightVal)) {
                val kind = AssertKind.Eq
                assertFailed(kind, leftVal, rightVal, Option.None())
            }
        }
    }
    // SAFETY: The simd types have the same layout as arrays, just with
    // potentially-higher alignment, so the de-facto transmutes are sound.
    return this.alignToMut()
}

/**
 * Checks if the elements of this slice are sorted.
 *
 * That is, for each element `a` and its following element `b`, `a <= b` must hold. If the
 * slice yields exactly zero or one element, `true` is returned.
 *
 * Note that if `Self.Item` is only `PartialOrd`, but not `Ord`, the above definition
 * implies that this function returns `false` if any two consecutive items are not
 * comparable.
 *
 * # Examples
 *
 * ```kotlin
 * val empty: IntArray = intArrayOf()
 * check(listOf(1, 2, 2, 9).isSorted())
 * check(!listOf(1, 3, 2, 4).isSorted())
 * check(listOf(0).isSorted())
 * check(empty.isSorted())
 * check(!floatArrayOf(0.0f, 1.0f, Float.NaN).isSorted())
 * ```
 */
public fun <T> List<T>.isSorted(): Boolean {
    // This odd number works the best. 32 + 1 extra due to overlapping chunk boundaries.
    val CHUNK_SIZE: Int = 33
    if (this.size < CHUNK_SIZE) {
        return this.windows(2).all({ w -> w[0] <= w[1] })
    }
    var i = 0
    // Check in chunks for autovectorization.
    while (i < this.size - CHUNK_SIZE) {
        val chunk = ((this).subList(i, i + CHUNK_SIZE))
        if (!chunk.windows(2).fold(true, { acc, w -> acc and (w[0] <= w[1]) })) {
            return false
        }
        // We need to ensure that chunk boundaries are also sorted.
        // Overlap the next chunk with the last element of our last chunk.
        i += CHUNK_SIZE - 1
    }
    return ((this).subList(i, (this).size)).windows(2).all({ w -> w[0] <= w[1] })
}

/**
 * Checks if the elements of this slice are sorted using the given comparator function.
 *
 * Instead of using `PartialOrd.partialCmp`, this function uses the given `compare`
 * function to determine whether two elements are to be considered in sorted order.
 *
 * # Examples
 *
 * ```kotlin
 * check(listOf(1, 2, 2, 9).isSortedBy({ a, b -> a <= b }))
 * check(!listOf(1, 2, 2, 9).isSortedBy({ a, b -> a < b }))
 * check(listOf(0).isSortedBy({ a, b -> true }))
 * check(listOf(0).isSortedBy({ a, b -> false }))
 * val empty: IntArray = intArrayOf()
 * check(empty.isSortedBy({ a, b -> false }))
 * check(empty.isSortedBy({ a, b -> true }))
 * ```
 */
public fun <T> List<T>.isSortedBy(compare: (T, T) -> Boolean): Boolean = this.arrayWindows().all({ param0 -> compare(a, b) })

/**
 * Checks if the elements of this slice are sorted using the given key extraction function.
 *
 * Instead of comparing the slice's elements directly, this function compares the keys of the
 * elements, as determined by `f`. Apart from that, it's equivalent to [isSorted]; see its
 * documentation for more information.
 *
 * [isSorted]: slice.isSorted
 *
 * # Examples
 *
 * ```kotlin
 * check(listOf("c", "bb", "aaa").isSortedByKey({ s -> s.len() }))
 * check(!intArrayOf(-2, -1, 0, 3).isSortedByKey({ n -> n.abs() }))
 * ```
 */
public fun <T, K> List<T>.isSortedByKey(f: (T) -> K): Boolean = this.iter().isSortedByKey(f)

/**
 * Returns the index of the partition point according to the given predicate
 * (the index of the first element of the second partition).
 *
 * The slice is assumed to be partitioned according to the given predicate.
 * This means that all elements for which the predicate returns true are at the start of the slice
 * and all elements for which the predicate returns false are at the end.
 * For example, `[7, 15, 3, 5, 4, 12, 6]` is partitioned under the predicate `x % 2 != 0`
 * (all odd numbers are at the start, all even at the end).
 *
 * If this slice is not partitioned, the returned result is unspecified and meaningless,
 * as this method performs a kind of binary search.
 *
 * See also [binarySearch], [binarySearchBy], and [binarySearchByKey].
 *
 * [binarySearch]: slice.binarySearch
 * [binarySearchBy]: slice.binarySearchBy
 * [binarySearchByKey]: slice.binarySearchByKey
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(1, 2, 3, 3, 5, 6, 7)
 * val i = v.partitionPoint({ x -> x < 5 })
 * check(i == 4)
 * check(v[0 until i].all({ x -> x < 5 }))
 * check(v[i until v.size].all({ x -> !(x < 5) }))
 * ```
 *
 * If all elements of the slice match the predicate, including if the slice
 * is empty, then the length of the slice will be returned:
 *
 * ```kotlin
 * val a = listOf(2, 4, 8)
 * check(a.partitionPoint({ x -> x < 100 }) == a.len())
 * val a1: IntArray = intArrayOf()
 * check(a1.partitionPoint({ x -> x < 100 }) == 0)
 * ```
 *
 * If you want to insert an item to a sorted vector, while maintaining
 * sort order:
 *
 * ```kotlin
 * var s = mutableListOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
 * val num = 42
 * val idx = s.partitionPoint({ x -> x <= num })
 * s.add(idx, num)
 * check(s == listOf(0, 1, 1, 1, 1, 2, 3, 5, 8, 13, 21, 34, 42, 55))
 * ```
 */
public fun <T> List<T>.partitionPoint(pred: (T) -> Boolean): Int =
    this.binarySearchBy({ x -> if (pred(x)) {
        Less
    } else {
        Greater
    } }).unwrapOrElse({ i -> i })

/**
 * Removes the subslice corresponding to the given range
 * and returns a reference to it.
 *
 * Returns `None` and does not modify the slice if the given
 * range is out of bounds.
 *
 * Note that this method only accepts one-sided ranges such as
 * `2 ..` or `.. 6`, but not `2 .. 6`.
 *
 * # Examples
 *
 * Splitting off the first three elements of a slice:
 *
 * ```kotlin
 * import slice
 *
 * var slice: List<Any?> = listOf('a', 'b', 'c', 'd')
 * var firstThree = slice.splitOff(0 until 3).unwrap()
 * check(slice == listOf('d'))
 * check(firstThree == listOf('a', 'b', 'c'))
 * ```
 *
 * Splitting off a slice starting with the third element:
 *
 * ```kotlin
 * import slice
 *
 * var slice: List<Any?> = listOf('a', 'b', 'c', 'd')
 * var tail = slice.splitOff(2 until Int.MAX_VALUE).unwrap()
 * check(slice == listOf('a', 'b'))
 * check(tail == listOf('c', 'd'))
 * ```
 *
 * Getting `None` when `range` is out of bounds:
 *
 * ```kotlin
 * import slice
 *
 * var slice: List<Any?> = listOf('a', 'b', 'c', 'd')
 * check(null == slice.splitOff(5 until Int.MAX_VALUE))
 * check(null == slice.splitOff(0 until 5))
 * check(null == slice.splitOff(0..4))
 * val expected: List<Char> = listOf('a', 'b', 'c', 'd')
 * check(expected == slice.splitOff(0 until 4))
 * ```
 */
public fun <T, R : OneSidedRange<Int>> List<T>.splitOff(range: R): List<T>? {
    var selfMut = this
    val (direction, splitIndex) = (splitPointOf(range) ?: return null)
    if (splitIndex > selfMut.size) {
        return null
    }
    val (front, back) = selfMut.splitAt(splitIndex)
    return when (direction) {
        Direction.Front -> {
            selfMut = back
            front
        }
        Direction.Back -> {
            selfMut = front
            back
        }
    }
}

/**
 * Removes the subslice corresponding to the given range
 * and returns a mutable reference to it.
 *
 * Returns `None` and does not modify the slice if the given
 * range is out of bounds.
 *
 * Note that this method only accepts one-sided ranges such as
 * `2 ..` or `.. 6`, but not `2 .. 6`.
 *
 * # Examples
 *
 * Splitting off the first three elements of a slice:
 *
 * ```kotlin
 * import slice
 *
 * var slice: MutableList<Any?> = mutableListOf('a', 'b', 'c', 'd')
 * var firstThree = slice.splitOffMut(0 until 3).unwrap()
 * check(slice == listOf('d'))
 * check(firstThree == listOf('a', 'b', 'c'))
 * ```
 *
 * Splitting off a slice starting with the third element:
 *
 * ```kotlin
 * import slice
 *
 * var slice: MutableList<Any?> = mutableListOf('a', 'b', 'c', 'd')
 * var tail = slice.splitOffMut(2 until Int.MAX_VALUE).unwrap()
 * check(slice == listOf('a', 'b'))
 * check(tail == listOf('c', 'd'))
 * ```
 *
 * Getting `None` when `range` is out of bounds:
 *
 * ```kotlin
 * import slice
 *
 * var slice: MutableList<Any?> = mutableListOf('a', 'b', 'c', 'd')
 * check(null == slice.splitOffMut(5 until Int.MAX_VALUE))
 * check(null == slice.splitOffMut(0 until 5))
 * check(null == slice.splitOffMut(0..4))
 * val expected: MutableList<Any?> = mutableListOf('a', 'b', 'c', 'd')
 * check(expected == slice.splitOffMut(0 until 4))
 * ```
 */
public fun <T, R : OneSidedRange<Int>> List<T>.splitOffMut(range: R): List<T>? {
    var selfMut = this
    val (direction, splitIndex) = (splitPointOf(range) ?: return null)
    if (splitIndex > selfMut.size) {
        return null
    }
    val (front, back) = take(selfMut).splitAtMut(splitIndex)
    return when (direction) {
        Direction.Front -> {
            selfMut = back
            front
        }
        Direction.Back -> {
            selfMut = front
            back
        }
    }
}

/**
 * Removes the first element of the slice and returns a reference
 * to it.
 *
 * Returns `None` if the slice is empty.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * var slice: List<Any?> = listOf('a', 'b', 'c')
 * val first = slice.splitOffFirst().unwrap()
 * check(slice == listOf('b', 'c'))
 * check(first == 'a')
 * ```
 */
public fun <T> List<T>.splitOffFirst(): T? {
    var selfMut = this
    // Note(const-Note): Use `?` when available in const instead of `val - else`.
    val (first, rem) = selfMut.splitFirst() ?: return null
    selfMut = rem
    return first
}

/**
 * Removes the first element of the slice and returns a mutable
 * reference to it.
 *
 * Returns `None` if the slice is empty.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * var slice: MutableList<Any?> = mutableListOf('a', 'b', 'c')
 * val first = slice.splitOffFirstMut().unwrap()
 * first = 'd'
 * check(slice == listOf('b', 'c'))
 * check(first == 'd')
 * ```
 */
public fun <T> List<T>.splitOffFirstMut(): T? {
    var selfMut = this
    // Note(const-Note): Use `mem.take` and `?` when available in const.
    // Original: `mem.take(self).splitFirstMut()`
    val (first, rem) = replace(selfMut, listOf()).splitFirstMut() ?: return null
    selfMut = rem
    return first
}

/**
 * Removes the last element of the slice and returns a reference
 * to it.
 *
 * Returns `None` if the slice is empty.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * var slice: List<Any?> = listOf('a', 'b', 'c')
 * val last = slice.splitOffLast().unwrap()
 * check(slice == listOf('a', 'b'))
 * check(last == 'c')
 * ```
 */
public fun <T> List<T>.splitOffLast(): T? {
    var selfMut = this
    // Note(const-Note): Use `?` when available in const instead of `val - else`.
    val (last, rem) = selfMut.splitLast() ?: return null
    selfMut = rem
    return last
}

/**
 * Removes the last element of the slice and returns a mutable
 * reference to it.
 *
 * Returns `None` if the slice is empty.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * var slice: MutableList<Any?> = mutableListOf('a', 'b', 'c')
 * val last = slice.splitOffLastMut().unwrap()
 * last = 'd'
 * check(slice == listOf('a', 'b'))
 * check(last == 'd')
 * ```
 */
public fun <T> List<T>.splitOffLastMut(): T? {
    var selfMut = this
    // Note(const-Note): Use `mem.take` and `?` when available in const.
    // Original: `mem.take(self).splitLastMut()`
    val (last, rem) = replace(selfMut, listOf()).splitLastMut() ?: return null
    selfMut = rem
    return last
}

/**
 * Returns mutable references to many indices at once, without doing any checks.
 *
 * An index can be either a `Int`, a [Range] or a [RangeInclusive]. Note
 * that this method takes an array, so all indices must be of the same type.
 * If passed an array of `Int`s this method gives back an array of mutable references
 * to single elements, while if passed an array of ranges it gives back an array of
 * mutable references to slices.
 *
 * For a safe alternative see [getDisjointMut].
 *
 * # Safety
 *
 * Calling this method with overlapping or out-of-bounds indices is *[undefined behavior]*
 * even if the resulting references are not used.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(1, 2, 4)
 * run {
 *     val (a, b) = x.getDisjointUncheckedMut(listOf(0, 2))
 *     a *= 10
 *     b *= 100
 * }
 * check((x).contentEquals(listOf(10, 2, 400)))
 * run {
 *     val (a1, b1) = x.getDisjointUncheckedMut(listOf(0 until 1, 1 until 3))
 *     a1[0] = 8
 *     b1[0] = 88
 *     b1[1] = 888
 * }
 * check((x).contentEquals(listOf(8, 88, 888)))
 * run {
 *     val (a2, b2) = x.getDisjointUncheckedMut(listOf(1..2, 0..0))
 *     a2[0] = 11
 *     a2[1] = 111
 *     b2[0] = 1
 * }
 * check((x).contentEquals(listOf(1, 11, 111)))
 * ```
 *
 * [getDisjointMut]: slice.getDisjointMut
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 */
public fun <Output, T, I> List<T>.getDisjointUncheckedMut(indices: List<I>, n: Int = 0): List<Output> where I : GetDisjointMutIndex<I>, I : SliceIndex<List<T>, Output> {
    // NB: This implementation is written as it is because any variation of
    // `indices.map(| i | self.getUncheckedMut(i))` would make miri unhappy,
    // or generate worse code otherwise. This is also why we need to go
    // through a raw pointer here.
    val slice: MutPtr = this
    val arr: MaybeUninit<List<Output>>  = MaybeUninit.uninit(UnresolvedTypeLayout<List<Output>>())
    val arrPtr = arr.asMutPtr()
    // SAFETY: We expect `indices` to contain disjunct values that are
    // in bounds of `self`.
    return run {
        for (i in 0 until n) {
            val idx = indices.getUnchecked(i).clone()
            arrPtr.cast().add(i).write(slice.getUncheckedMut(idx))
        }
        arr.assumeInit()
    }
}

/**
 * Returns mutable references to many indices at once.
 *
 * An index can be either a `Int`, a [Range] or a [RangeInclusive]. Note
 * that this method takes an array, so all indices must be of the same type.
 * If passed an array of `Int`s this method gives back an array of mutable references
 * to single elements, while if passed an array of ranges it gives back an array of
 * mutable references to slices.
 *
 * Returns an error if any index is out-of-bounds, or if there are overlapping indices.
 * An empty range is not considered to overlap if it is located at the beginning or at
 * the end of another range, but is considered to overlap if it is located in the middle.
 *
 * This method does a O(n^2) check to check that there are no overlapping indices, so be careful
 * when passing many indices.
 *
 * # Examples
 *
 * ```kotlin
 * val v = listOf(1, 2, 3)
 * run {
 *     val matchVal = v.getDisjointMut(listOf(0, 2))
 *     if (matchVal is Ok && matchVal.value.size == 2) {
 *         val a = matchVal.value[0]
 *         val b = matchVal.value[1]
 *         a = 413
 *         b = 612
 *     }
 * }
 * check((v).contentEquals(listOf(413, 2, 612)))
 * run {
 *     val matchVal1 = v.getDisjointMut(listOf(0 until 1, 1 until 3))
 *     if (matchVal1 is Ok && matchVal1.value.size == 2) {
 *         val a1 = matchVal1.value[0]
 *         val b1 = matchVal1.value[1]
 *         a1[0] = 8
 *         b1[0] = 88
 *         b1[1] = 888
 *     }
 * }
 * check((v).contentEquals(listOf(8, 88, 888)))
 * run {
 *     val matchVal2 = v.getDisjointMut(listOf(1..2, 0..0))
 *     if (matchVal2 is Ok && matchVal2.value.size == 2) {
 *         val a2 = matchVal2.value[0]
 *         val b2 = matchVal2.value[1]
 *         a2[0] = 11
 *         a2[1] = 111
 *         b2[0] = 1
 *     }
 * }
 * check((v).contentEquals(listOf(1, 11, 111)))
 * ```
 */
public fun <Output, T, I> List<T>.getDisjointMut(indices: List<I>, n: Int = 0): Result<List<Output>, GetDisjointMutError> where I : GetDisjointMutIndex<I>, I : SliceIndex<List<T>, Output> {
    when (val tryResult = getDisjointCheckValid(indices, this.size, n)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<Output>, GetDisjointMutError>(tryResult.errValue()) }
    // SAFETY: The `getDisjointCheckValid()` call checked that all indices
    // are disjunct and in bounds.
    return Result.Ok<List<Output>, GetDisjointMutError>(this.getDisjointUncheckedMut(indices))
}

/**
 * Returns the index that an element reference points to.
 *
 * Returns `None` if `element` does not point to the start of an element within the slice.
 *
 * This method is useful for extending slice iterators like [slice.split].
 *
 * Note that this uses pointer arithmetic and **does not compare elements**.
 * To find the index of an element via comparison, use
 * [`iter.position`](iter.Iterator.position) instead.
 *
 * # Panics
 * Panics if `T` is zero-sized.
 *
 * # Examples
 * Basic usage:
 * ```kotlin
 * val nums: List<UInt> = listOf(1u, 7u, 1u, 1u)
 * val num = nums[2]
 * check(num == 1)
 * check(nums.elementOffset(num) == 2)
 * ```
 * Returning `None` with an unaligned element:
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     val arr: List<UIntArray> = listOf(uintArrayOf(0u, 1u), uintArrayOf(2u, 3u))
 *     val flatArr: List<UInt> = arr.asFlattened()
 *     val okElm: UIntArray = ((flatArr).subList(0, 2)).toUIntArray()
 *     val weirdElm: UIntArray = ((flatArr).subList(1, 3)).toUIntArray()
 *     check(okElm == listOf(0, 1))
 *     check(weirdElm == listOf(1, 2))
 *     check(arr.elementOffset(okElm) == 0)
 *     // Points to element 0
 *     check(arr.elementOffset(weirdElm) == null)
 *     // Points between element 0 and 1
 * }
 * ```
 */
public fun <T> List<T>.elementOffset(element: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int? {
    if (((typeLayoutT).sizeBytes == 0L)) {
        error("elements are zero-sized")
    }
    val selfStart = (this.asPtr()).rawAddress.toInt()
    val elemStart = (element).rawAddress.toInt()
    val byteOffset = elemStart.wrappingSub(selfStart)
    if (!byteOffset.isMultipleOf(sizeOf<T>(typeLayoutT = typeLayoutT))) {
        return null
    }
    val offset = byteOffset / sizeOf<T>(typeLayoutT = typeLayoutT)
    return if (offset < this.size) {
        offset
    } else {
        null
    }
}

/**
 * Returns the range of indices that a subslice points to.
 *
 * Returns `None` if `subslice` does not point within the slice or if it is not aligned with the
 * elements in the slice.
 *
 * This method **does not compare elements**. Instead, this method finds the location in the slice that
 * `subslice` was obtained from. To find the index of a subslice via comparison, instead use
 * [`windows`](slice.windows)[`position`](iter.Iterator.position).
 *
 * This method is useful for extending slice iterators like [slice.split].
 *
 * Note that this may return a false positive (either `Some(0 .. 0)` or `Some(self.len() .. self.len())`)
 * if `subslice` has a length of zero and points to the beginning or end of another, separate, slice.
 *
 * # Panics
 * Panics if `T` is zero-sized.
 *
 * # Examples
 * Basic usage:
 * ```kotlin
 * import ops.Range
 *
 * val nums = listOf(0, 5, 10, 0, 0, 5)
 * var iter = nums.split({ t -> t == 0 }).map({ n -> nums.subsliceRange(n).unwrap() })
 * check(iter.next() == Range(start = 0, end = 0))
 * check(iter.next() == Range(start = 1, end = 3))
 * check(iter.next() == Range(start = 4, end = 4))
 * check(iter.next() == Range(start = 5, end = 6))
 * ```
 * Returning `None` with a slice not aligned to the elements of the base array:
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import ops.Range
 *
 * public fun doctestEntry() {
 *     val arr: List<UByteArray> = listOf(ubyteArrayOf(0u, 1u, 2u, 3u), ubyteArrayOf(4u, 5u, 6u, 7u))
 *     val flattened: List<UByte> = arr.asFlattened()
 *     // This sub-slice is aligned with the first element of `arr`:
 *     // &[[0, 1, 2, 3], [4, 5, 6, 7]]
 *     // &[[0, 1, 2, 3]]
 *     val alignedElem: UByteArray = ((flattened).subList(0, 4)).toUByteArray()
 *     val alignedSlice: List<UByteArray> = alignedElem
 *     // `alignedSlice` is `arr[0 .. 1]`
 *     check(arr.subsliceRange(alignedSlice) == Range(start = 0, end = 1))
 *     // This sub-slice is **not** aligned to the elements of `arr`, despite pointing inside it:
 *     // &[[0, 1, 2, 3], [4, 5, 6, 7]]
 *     // &[[1, 2, 3,   4]]
 *     val misalignedElem: UByteArray = ((flattened).subList(1, 5)).toUByteArray()
 *     val misalignedSlice: List<UByteArray> = misalignedElem
 *     // `misalignedSlice` has no corresponding slice of `arr`
 *     check(arr.subsliceRange(misalignedSlice) == null)
 * }
 * ```
 */
public fun <T> List<T>.subsliceRange(subslice: List<T>, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): range.Range<Int>? {
    if (((typeLayoutT).sizeBytes == 0L)) {
        error("elements are zero-sized")
    }
    val selfStart = (this.asPtr()).rawAddress.toInt()
    val subsliceStart = (subslice.asPtr()).rawAddress.toInt()
    val byteStart = subsliceStart.wrappingSub(selfStart)
    if (!byteStart.isMultipleOf(sizeOf<T>(typeLayoutT = typeLayoutT))) {
        return null
    }
    val start = byteStart / sizeOf<T>(typeLayoutT = typeLayoutT)
    val end = (start + subslice.size)
    return if (start <= this.size && end <= this.size) {
        range.Range(start = start.toUShort(), end = end)
    } else {
        null
    }
}

/**
 * Returns the same slice `[T]`.
 *
 * This method is redundant when used directly on `[T]`, but
 * it helps dereferencing other "container" types to slices,
 * for example `Holder<[T]>` or `Arc<[T]>`.
 */
public fun <T> List<T>.asSlice(): List<T> = this

/**
 * Returns the same slice `[T]`.
 *
 * This method is redundant when used directly on `[T]`, but
 * it helps dereferencing other "container" types to slices,
 * for example `Holder<[T]>` or `MutexGuard<[T]>`.
 */
public fun <T> List<T>.asMutSlice(): MutableList<T> = this

/**
 * Transmutes the mutable uninitialized slice to a mutable uninitialized slice of
 * another type, ensuring alignment of the types is maintained.
 *
 * This is a safe wrapper around [slice.alignToMut], so inherits the same
 * guarantees as that method.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import mem.MaybeUninit
 *
 * @feature(alignToUninitMut)
 * public fun doctestEntry(): Result<Unit> {
 *     open class BumpAllocator(public var memory: MutableList<MaybeUninit<UByte>>) {
 *         public open fun new(memory: MutableList<MaybeUninit<UByte>>): BumpAllocator {
 *             return BumpAllocator(memory = memory)
 *         }
 *
 *         public open fun <T> tryAllocUninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T>? {
 *             val firstEnd = this.memory.asPtr().alignOffset(alignOf<T>(typeLayoutT = typeLayoutT)) + sizeOf<T>(typeLayoutT = typeLayoutT)
 *             val prefix = (this.memory.splitOffMut(0 until firstEnd) ?: return null)
 *             return prefix.alignToUninitMut<T>().second[0]
 *         }
 *
 *         public open fun tryAllocU32(value: UInt): RustMutableReference<UInt>? {
 *             val uninit = (this.tryAllocUninit() ?: return null)
 *             return uninit.write(value)
 *         }
 *     }
 *     var memory = MutableList(10) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 *     var allocator = BumpAllocator.new(memory)
 *     val v = allocator.tryAllocU32(42)
 *     check(v == RustMutableReference(allocateNativeCell(42, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T, U> List<MaybeUninit<T>>.alignToUninitMut(): Triple<List<MaybeUninit<T>>, MutableList<MaybeUninit<U>>, List<MaybeUninit<T>>> {
    // SAFETY: `MaybeUninit` is transparent. Correct size and alignment are guaranteed by
    // `alignToMut` itself. Therefore the only thing that we have to ensure for a safe
    // `reinterpret` is that the values are valid for the types involved. But for `MaybeUninit`
    // any values are valid, so this operation is safe.
    return this.alignToMut()
}

/**
 * Takes a `[[T; N]]`, and flattens it to a `[T]`.
 *
 * For the opposite operation, see [asChunks] and [asRchunks].
 *
 * [asChunks]: slice.asChunks
 * [asRchunks]: slice.asRchunks
 *
 * # Panics
 *
 * This panics if the length of the resulting slice would overflow a `Int`.
 *
 * This is only possible when flattening a slice of arrays of zero-sized
 * types, and thus tends to be irrelevant in practice. If
 * `sizeOf<T>()> 0`, this will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     check(listOf(listOf(1, 2, 3), listOf(4, 5, 6)).asFlattened() == listOf(1, 2, 3, 4, 5, 6))
 *     check(listOf(listOf(1, 2, 3), listOf(4, 5, 6)).asFlattened() == listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6)).asFlattened())
 *     val sliceOfEmptyArrays: List<IntArray> = listOf(intArrayOf(), intArrayOf(), intArrayOf(), intArrayOf(), intArrayOf())
 *     check(sliceOfEmptyArrays.asFlattened().isEmpty())
 *     val emptySliceOfArrays: List<UIntArray> = listOf()
 *     check(emptySliceOfArrays.asFlattened().isEmpty())
 * }
 * ```
 */
public fun <T> List<List<T>>.asFlattened(n: Int = 0, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<T> {
    val len =
        if (((typeLayoutT).sizeBytes == 0L)) {
            (this.size.checkedMul(n) ?: error("slice len overflow"))
        } else {
            // SAFETY: `self.len() * N` cannot overflow because `self` is
            // already in the address space.
            this.size.uncheckedMul(n)
        }
    // SAFETY: `[T]` is layout-identical to `[T; N]`
    return linearSliceView(ConstPtr((this.asPtr()).rawAddress), len.toInt(), PrimitiveLayouts.U8)
}

/**
 * Takes a `[[T; N]]`, and flattens it to a `[T]`.
 *
 * For the opposite operation, see [asChunksMut] and [asRchunksMut].
 *
 * [asChunksMut]: slice.asChunksMut
 * [asRchunksMut]: slice.asRchunksMut
 *
 * # Panics
 *
 * This panics if the length of the resulting slice would overflow a `Int`.
 *
 * This is only possible when flattening a slice of arrays of zero-sized
 * types, and thus tends to be irrelevant in practice. If
 * `sizeOf<T>()> 0`, this will never panic.
 *
 * # Examples
 *
 * ```kotlin
 * import slice
 *
 * fun add5ToAll(slice: MutableList<Int>) {
 *     for (idx in slice.indices) {
 *         slice[idx] += 5
 *     }
 * }
 * var array = mutableListOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9))
 * add5ToAll(array.asFlattenedMut())
 * check(array == listOf(listOf(6, 7, 8), listOf(9, 10, 11), listOf(12, 13, 14)))
 * ```
 */
public fun <T> List<List<T>>.asFlattenedMut(n: Int = 0, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutableList<T> {
    val len =
        if (((typeLayoutT).sizeBytes == 0L)) {
            (this.size.checkedMul(n) ?: error("slice len overflow"))
        } else {
            // SAFETY: `self.len() * N` cannot overflow because `self` is
            // already in the address space.
            this.size.uncheckedMul(n)
        }
    // SAFETY: `[T]` is layout-identical to `[T; N]`
    return linearMutSliceView(MutPtr((this.asMutPtr()).rawAddress), len.toInt(), PrimitiveLayouts.U8)
}

/**
 * Sorts the slice of floats.
 *
 * This sort is in-place (i.e. does not allocate), *O*(*n* \* log(*n*)) worst-case, and uses
 * the ordering defined by [f32.totalCmp].
 *
 * # Current implementation
 *
 * This uses the same sorting algorithm as [`sortUnstableBy`](slice.sortUnstableBy).
 *
 * # Examples
 *
 * ```kotlin
 * @feature(sortFloats)
 * public fun doctestEntry() {
 *     var v = floatArrayOf(2.6f, -5e-8f, Float.NaN, 8.29f, Float.POSITIVE_INFINITY, -1.0f, 0.0f, -Float.POSITIVE_INFINITY, -0.0f)
 *     v.sortFloats()
 *     val sorted = floatArrayOf(-Float.POSITIVE_INFINITY, -1.0f, -5e-8f, -0.0f, 0.0f, 2.6f, 8.29f, Float.POSITIVE_INFINITY, Float.NaN)
 *     check((((v).sliceArray(0 until 8))).contentEquals(((sorted).sliceArray(0 until 8))))
 *     check(v[8].isNan())
 * }
 * ```
 */
public fun List<kotlin.Float>.sortFloats() {
    this.sortUnstableBy({ kotlin.Float.totalCmp(it) })
}

public fun <T> List<T>.specCloneFrom(src: List<T>) {
    check(this.size == src.size) { "destination and source slices have different lengths" }
    // NOTE: We need to explicitly slice them to the same length
    // to make it easier for the optimizer to elide bounds checking.
    // But since it can't be relied on we also have an explicit specialization for T: Copy.
    val len = this.size
    val src1 = ((src).subList(0, len))
    for (i in 0 until len) {
        this[i].cloneFrom(src1[i])
    }
}

public fun <T> List<T>.specCloneFromT(src: List<T>) where T : TrivialClone<T> {
    // SAFETY: `T` implements `TrivialClone`.
    run {
        copyFromSliceImpl(this, src)
    }
}

/**
 * Creates an empty slice.
 */
public fun <T> defaultTSlice(): List<T> = listOf()

public fun <T> List<T>.asSliceFromTSlice(): List<T> = this

public fun <T> List<T>.asSliceFromTArrayn(n: Int = 0): List<T> = this

// SAFETY: We implement `isInBounds()` and `isOverlapping()` correctly.
public fun Int.isInBounds(len: Int): Boolean = this < len

public fun Int.isOverlapping(other: Int): Boolean = this == other

// SAFETY: We implement `isInBounds()` and `isOverlapping()` correctly.
public fun IntRange.isInBounds(len: Int): Boolean = (this.start <= this.end) and (this.end <= len)

public fun IntRange.isOverlapping(other: IntRange): Boolean = (this.start < other.end) and (other.start < this.end)

// SAFETY: We implement `isInBounds()` and `isOverlapping()` correctly.

// SAFETY: We implement `isInBounds()` and `isOverlapping()` correctly.
public fun Range<Int>.isInBounds(len: Int): Boolean = Range.from(this).isInBounds(len)

public fun Range<Int>.isOverlapping(other: Range<Int>): Boolean = Range.from(this).isOverlapping(Range.from(other))

// SAFETY: We implement `isInBounds()` and `isOverlapping()` correctly.
public fun RangeInclusive<Int>.isInBounds(len: Int): Boolean = RangeInclusive.from(this).isInBounds(len)

public fun RangeInclusive<Int>.isOverlapping(other: RangeInclusive<Int>): Boolean = RangeInclusive.from(this).isOverlapping(RangeInclusive.from(other))
