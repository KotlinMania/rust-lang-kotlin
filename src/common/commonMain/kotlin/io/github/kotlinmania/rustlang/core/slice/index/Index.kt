// port-lint: source slice/index.rs
package io.github.kotlinmania.rustlang.core.slice.index

import io.github.kotlinmania.rustlang.core.intrinsics.sliceGetUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.core.ops.indexrange.IndexRange
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.ops.range.RangeTo
import io.github.kotlinmania.rustlang.core.ops.range.RangeToInclusive
import io.github.kotlinmania.rustlang.core.panic.constPanic
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.range.Range
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.ubchecks.assertUnsafePrecondition

// Indexing implementations for `[T]`.

public fun <T, I> List<T>.index(index: I): Output where I : SliceIndex<List<T>, Any?> = index.index(this)

public fun <T, I> List<T>.indexMut(index: I): Output where I : SliceIndex<List<T>, Any?> = index.indexMut(this)

public fun sliceIndexFail(start: Int, end: Int, len: Int): Nothing {
    if (start > len) {
        constPanic("slice start index is out of range for slice", "range start index {start} out of range for slice of length {len}", start, len)
    }
    if (end > len) {
        constPanic("slice end index is out of range for slice", "range end index {end} out of range for slice of length {len}", end, len)
    }
    if (start > end) {
        constPanic("slice index start is larger than end", "slice index starts at {start} but ends at {end}", start, end)
    }
    // Only reachable if the range was a `RangeInclusive` or a
    // `RangeToInclusive`, with `end == len`.
    return constPanic("slice end index is out of range for slice", "range end index {end} out of range for slice of length {len}", end, len)
}

// The UbChecks are great for catching bugs in the unchecked methods, but including
// them in safe indexing is unnecessary and hurts inlining and debug runtime perf.
// Both the safe and unchecked public methods share these helpers,
// which use intrinsics directly to get *no* extra checks.
public fun <T> getOffsetLenNoubcheck(ptr: ConstPtr, offset: Int, len: Int, typeLayoutT: TypeLayout<T>): ConstPtr {
    val ptr1 = ConstPtr(ptr.rawAddress, ptr.rawMeta)
    // SAFETY: The caller already checked these preconditions
    val ptr2 = ConstPtr(ptr1.rawAddress + (offset).toLong() * (typeLayoutT).strideBytes)
    return ConstPtr(ptr2.rawAddress, (len).toLong())
}

public fun <T> getOffsetLenMutNoubcheck(ptr: MutPtr, offset: Int, len: Int, typeLayoutT: TypeLayout<T>): MutPtr {
    val ptr1 = MutPtr(ptr.rawAddress, ptr.rawMeta)
    // SAFETY: The caller already checked these preconditions
    val ptr2 = MutPtr(ptr1.rawAddress + (offset).toLong() * (typeLayoutT).strideBytes)
    return MutPtr(ptr2.rawAddress, (len).toLong())
}

/**
 * A helper trait used for indexing operations.
 *
 * Implementations of this trait have to promise that if the argument
 * to `getUnchecked(_mut)` is a safe reference, then so is the result.
 */
public interface SliceIndex<T, Output> {
    /**
     * Returns a shared reference to the output at this location, if in
     * bounds.
     */
    public fun get(slice: T): Output?

    /**
     * Returns a mutable reference to the output at this location, if in
     * bounds.
     */
    public fun getMut(slice: T): Output?

    /**
     * Returns a pointer to the output at this location, without
     * performing any bounds checking.
     *
     * # Safety
     *
     * Calling this method with an out-of-bounds index or a dangling `slice` pointer
     * is *[undefined behavior]* even if the resulting pointer is not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public fun getUnchecked(slice: ConstPtr): ConstPtr

    /**
     * Returns a mutable pointer to the output at this location, without
     * performing any bounds checking.
     *
     * # Safety
     *
     * Calling this method with an out-of-bounds index or a dangling `slice` pointer
     * is *[undefined behavior]* even if the resulting pointer is not used.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     */
    public fun getUncheckedMut(slice: MutPtr): MutPtr

    /**
     * Returns a shared reference to the output at this location, panicking
     * if out of bounds.
     */
    public fun index(slice: T): Output

    /**
     * Returns a mutable reference to the output at this location, panicking
     * if out of bounds.
     */
    public fun indexMut(slice: T): Output
}

/**
 * The methods `index` and `indexMut` panic if the index is out of bounds.
 */
public fun <T> Int.get(slice: List<T>): T? =
    if (this < slice.size) {
        // SAFETY: `self` is checked to be in bounds.
        sliceGetUnchecked(slice, this)
    } else {
        null
    }

public fun <T> Int.getMut(slice: MutableList<T>): T? =
    if (this < slice.size) {
        // SAFETY: `self` is checked to be in bounds.
        sliceGetUnchecked(slice, this)
    } else {
        null
    }

public fun Int.getUnchecked(slice: ConstPtr): ConstPtr {
    assertUnsafePrecondition(checkLibraryUb, "slice::get_unchecked requires that the index is within the slice", thisRef, this, len, slice, len, thisRef, len)
    // SAFETY: the caller guarantees that `slice` is not dangling, so it
    // cannot be longer than `Long.MAX`. They also guarantee that
    // `self` is in bounds of `slice` so `self` cannot overflow an `Long`,
    // so the call to `add` is safe.
    return run {
        // Use intrinsics.assume instead of hint.assertUnchecked so that we don't check the
        // precondition of this function twice.
        sliceGetUnchecked(slice, this)
    }
}

public fun Int.getUncheckedMut(slice: MutPtr): MutPtr {
    assertUnsafePrecondition(checkLibraryUb, "slice::get_unchecked_mut requires that the index is within the slice", thisRef, this, len, slice, len, thisRef, len)
    // SAFETY: see comments for `getUnchecked` above.
    return sliceGetUnchecked(slice, this)
}

public fun <T> Int.index(slice: List<T>): T {
    // N.B., use intrinsic indexing
    return slice[this]
}

public fun <T> Int.indexMut(slice: MutableList<T>): T {
    // N.B., use intrinsic indexing
    return slice[this]
}

/**
 * Because `IndexRange` guarantees `start <= end`, fewer checks are needed here
 * than there are for a general `Range<Int>` (which might be `100 .. 3`).
 */
public fun <T> IndexRange.get(slice: List<T>): List<T>? =
    if (this.end() <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        (PrimitiveLayouts.U8).readNative((getOffsetLenNoubcheck(slice, this.start(), this.len())).rawAddress)
    } else {
        null
    }

public fun <T> IndexRange.getMut(slice: MutableList<T>): MutableList<T>? =
    if (this.end() <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        RustMutableReference(MutPtr((getOffsetLenMutNoubcheck(slice, this.start(), this.len())).rawAddress), PrimitiveLayouts.U8)
    } else {
        null
    }

public fun IndexRange.getUnchecked(slice: ConstPtr): ConstPtr {
    assertUnsafePrecondition(checkLibraryUb, "slice::get_unchecked requires that the index is within the slice", end, this, end, len, slice, len, end, len)
    // SAFETY: the caller guarantees that `slice` is not dangling, so it
    // cannot be longer than `Long.MAX`. They also guarantee that
    // `self` is in bounds of `slice` so `self` cannot overflow an `Long`,
    // so the call to `add` is safe.
    return getOffsetLenNoubcheck(slice, this.start(), this.len())
}

public fun IndexRange.getUncheckedMut(slice: MutPtr): MutPtr {
    assertUnsafePrecondition(checkLibraryUb, "slice::get_unchecked_mut requires that the index is within the slice", end, this, end, len, slice, len, end, len)
    // SAFETY: see comments for `getUnchecked` above.
    return getOffsetLenMutNoubcheck(slice, this.start(), this.len())
}

public fun <T> IndexRange.index(slice: List<T>): List<T> =
    if (this.end() <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        (PrimitiveLayouts.U8).readNative((getOffsetLenNoubcheck(slice, this.start(), this.len())).rawAddress)
    } else {
        sliceIndexFail(this.start(), this.end(), slice.size)
    }

public fun <T> IndexRange.indexMut(slice: MutableList<T>): MutableList<T> =
    if (this.end() <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        RustMutableReference(MutPtr((getOffsetLenMutNoubcheck(slice, this.start(), this.len())).rawAddress), PrimitiveLayouts.U8)
    } else {
        sliceIndexFail(this.start(), this.end(), slice.size)
    }

/**
 * The methods `index` and `indexMut` panic if:
 * - the start of the range is greater than the end of the range or
 * - the end of the range is out of bounds.
 */
public fun <T> Range<Int>.get(slice: List<T>): List<T>? {
    // Using checkedSub is a safe way to get `SubUnchecked` in MIR
    return if (rustCheckedSub(this.end, this.start.toInt()) != null && this.end <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        (PrimitiveLayouts.U8).readNative((getOffsetLenNoubcheck(slice, this.start, newLen)).rawAddress)
    } else {
        null
    }
}

public fun <T> Range<Int>.getMut(slice: MutableList<T>): MutableList<T>? =
    if (rustCheckedSub(this.end, this.start.toInt()) != null && this.end <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        RustMutableReference(MutPtr((getOffsetLenMutNoubcheck(slice, this.start, newLen)).rawAddress), PrimitiveLayouts.U8)
    } else {
        null
    }

public fun Range<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    assertUnsafePrecondition(checkLibraryUb, "slice::get_unchecked requires that the range is within the slice", start, this, start, end, this, end, len, slice, len, end, start, end, len)
    // SAFETY: the caller guarantees that `slice` is not dangling, so it
    // cannot be longer than `Long.MAX`. They also guarantee that
    // `self` is in bounds of `slice` so `self` cannot overflow an `Long`,
    // so the call to `add` is safe and the length calculation cannot overflow.
    return run {
        // Using the intrinsic avoids a superfluous UB check,
        // since the one on this method already checked `end >= start`.
        val newLen = uncheckedSub(this.end, this.start)
        getOffsetLenNoubcheck(slice, this.start, newLen)
    }
}

public fun Range<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    assertUnsafePrecondition(checkLibraryUb, "slice::get_unchecked_mut requires that the range is within the slice", start, this, start, end, this, end, len, slice, len, end, start, end, len)
    // SAFETY: see comments for `getUnchecked` above.
    return run {
        val newLen = uncheckedSub(this.end, this.start)
        getOffsetLenMutNoubcheck(slice, this.start, newLen)
    }
}

public fun <T> Range<Int>.index(slice: List<T>): List<T> {
    // Using checkedSub is a safe way to get `SubUnchecked` in MIR
    return if (rustCheckedSub(this.end, this.start.toInt()) != null && this.end <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        (PrimitiveLayouts.U8).readNative((getOffsetLenNoubcheck(slice, this.start, newLen)).rawAddress)
    } else {
        sliceIndexFail(this.start, this.end, slice.size)
    }
}

public fun <T> Range<Int>.indexMut(slice: MutableList<T>): MutableList<T> {
    // Using checkedSub is a safe way to get `SubUnchecked` in MIR
    return if (rustCheckedSub(this.end, this.start.toInt()) != null && this.end <= slice.size) {
        // SAFETY: `self` is checked to be valid and in bounds above.
        RustMutableReference(MutPtr((getOffsetLenMutNoubcheck(slice, this.start, newLen)).rawAddress), PrimitiveLayouts.U8)
    } else {
        sliceIndexFail(this.start, this.end, slice.size)
    }
}

public fun <T> IntRange.get(slice: List<T>): List<T>? = Range.from(this).get(slice)

public fun <T> IntRange.getMut(slice: MutableList<T>): MutableList<T>? = Range.from(this).getMut(slice)

public fun IntRange.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return Range.from(this).getUnchecked(slice)
}

public fun IntRange.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return Range.from(this).getUncheckedMut(slice)
}

public fun <T> IntRange.index(slice: List<T>): List<T> = Range.from(this).index(slice)

public fun <T> IntRange.indexMut(slice: MutableList<T>): MutableList<T> = Range.from(this).indexMut(slice)

/**
 * The methods `index` and `indexMut` panic if the end of the range is out of bounds.
 */
public fun <T> RangeTo<Int>.get(slice: List<T>): List<T>? = (0 until this.end).get(slice)

public fun <T> RangeTo<Int>.getMut(slice: MutableList<T>): MutableList<T>? = (0 until this.end).getMut(slice)

public fun RangeTo<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (0 until this.end).getUnchecked(slice)
}

public fun RangeTo<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (0 until this.end).getUncheckedMut(slice)
}

public fun <T> RangeTo<Int>.index(slice: List<T>): List<T> = (0 until this.end).index(slice)

public fun <T> RangeTo<Int>.indexMut(slice: MutableList<T>): MutableList<T> = (0 until this.end).indexMut(slice)

/**
 * The methods `index` and `indexMut` panic if the start of the range is out of bounds.
 */
public fun <T> RangeFrom<Int>.get(slice: List<T>): List<T>? = (this.start until slice.size).get(slice)

public fun <T> RangeFrom<Int>.getMut(slice: MutableList<T>): MutableList<T>? = (this.start until slice.size).getMut(slice)

public fun RangeFrom<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (this.start until slice.len()).getUnchecked(slice)
}

public fun RangeFrom<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (this.start until slice.len()).getUncheckedMut(slice)
}

public fun <T> RangeFrom<Int>.index(slice: List<T>): List<T> {
    if (this.start > slice.size) {
        sliceIndexFail(this.start, slice.size, slice.size)
    }
    // SAFETY: `self` is checked to be valid and in bounds above.
    return run {
        val newLen = uncheckedSub(slice.size, this.start)
        (PrimitiveLayouts.U8).readNative((getOffsetLenNoubcheck(slice, this.start, newLen)).rawAddress)
    }
}

public fun <T> RangeFrom<Int>.indexMut(slice: MutableList<T>): MutableList<T> {
    if (this.start > slice.size) {
        sliceIndexFail(this.start, slice.size, slice.size)
    }
    // SAFETY: `self` is checked to be valid and in bounds above.
    return run {
        val newLen = uncheckedSub(slice.size, this.start)
        RustMutableReference(MutPtr((getOffsetLenMutNoubcheck(slice, this.start, newLen)).rawAddress), PrimitiveLayouts.U8)
    }
}

public fun <T> RangeFrom<Int>.getTSlice(slice: List<T>): List<T>? = RangeFrom.from(this).get(slice)

public fun <T> RangeFrom<Int>.getMutTSlice(slice: MutableList<T>): MutableList<T>? = RangeFrom.from(this).getMut(slice)

public fun RangeFrom<Int>.getUncheckedTSlice(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return RangeFrom.from(this).getUnchecked(slice)
}

public fun RangeFrom<Int>.getUncheckedMutTSlice(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return RangeFrom.from(this).getUncheckedMut(slice)
}

public fun <T> RangeFrom<Int>.indexTSlice(slice: List<T>): List<T> = RangeFrom.from(this).index(slice)

public fun <T> RangeFrom<Int>.indexMutTSlice(slice: MutableList<T>): MutableList<T> = RangeFrom.from(this).indexMut(slice)

public fun <T> RangeFull.get(slice: List<T>): List<T>? = slice

public fun <T> RangeFull.getMut(slice: MutableList<T>): MutableList<T>? = slice

public fun RangeFull.getUnchecked(slice: ConstPtr): ConstPtr = slice

public fun RangeFull.getUncheckedMut(slice: MutPtr): MutPtr = slice

public fun <T> RangeFull.index(slice: List<T>): List<T> = slice

public fun <T> RangeFull.indexMut(slice: MutableList<T>): MutableList<T> = slice

/**
 * The methods `index` and `indexMut` panic if:
 * - the start of the range is greater than the end of the range or
 * - the end of the range is out of bounds.
 */
public fun <T> RangeInclusive<Int>.get(slice: List<T>): List<T>? =
    if (this.end() >= slice.size) {
        null
    } else {
        this.intoSliceRange().get(slice)
    }

public fun <T> RangeInclusive<Int>.getMut(slice: MutableList<T>): MutableList<T>? =
    if (this.end() >= slice.size) {
        null
    } else {
        this.intoSliceRange().getMut(slice)
    }

public fun RangeInclusive<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return this.intoSliceRange().getUnchecked(slice)
}

public fun RangeInclusive<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return this.intoSliceRange().getUncheckedMut(slice)
}

public fun <T> RangeInclusive<Int>.index(slice: List<T>): List<T> {
    var start = this.start
    var end = this.end
    val exhausted = this.exhausted
    val len = slice.size
    if (end < len) {
        end = end + 1
        start = if (exhausted) {
            end
        } else {
            start
        }
        when (val matchVal = rustCheckedSub(end, start.toInt())) {
            null -> {}
            else -> {
                val newLen = matchVal
                // SAFETY: `self` is checked to be valid and in bounds above.
                return (PrimitiveLayouts.U8).readNative((getOffsetLenNoubcheck(slice, start, newLen)).rawAddress)
            }
        }
    }
    return sliceIndexFail(start, end, slice.size)
}

public fun <T> RangeInclusive<Int>.indexMut(slice: MutableList<T>): MutableList<T> {
    var start = this.start
    var end = this.end
    val exhausted = this.exhausted
    val len = slice.size
    if (end < len) {
        end = end + 1
        start = if (exhausted) {
            end
        } else {
            start
        }
        when (val matchVal = rustCheckedSub(end, start.toInt())) {
            null -> {}
            else -> {
                val newLen = matchVal
                // SAFETY: `self` is checked to be valid and in bounds above.
                return RustMutableReference(MutPtr((getOffsetLenMutNoubcheck(slice, start, newLen)).rawAddress), PrimitiveLayouts.U8)
            }
        }
    }
    return sliceIndexFail(start, end, slice.size)
}

public fun <T> IntRange.getTSlice(slice: List<T>): List<T>? = RangeInclusive.from(this).get(slice)

public fun <T> IntRange.getMutTSlice(slice: MutableList<T>): MutableList<T>? = RangeInclusive.from(this).getMut(slice)

public fun IntRange.getUncheckedTSlice(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return RangeInclusive.from(this).getUnchecked(slice)
}

public fun IntRange.getUncheckedMutTSlice(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return RangeInclusive.from(this).getUncheckedMut(slice)
}

public fun <T> IntRange.indexTSlice(slice: List<T>): List<T> = RangeInclusive.from(this).index(slice)

public fun <T> IntRange.indexMutTSlice(slice: MutableList<T>): MutableList<T> = RangeInclusive.from(this).indexMut(slice)

/**
 * The methods `index` and `indexMut` panic if the end of the range is out of bounds.
 */
public fun <T> RangeToInclusive<Int>.get(slice: List<T>): List<T>? = (0..this.end).get(slice)

public fun <T> RangeToInclusive<Int>.getMut(slice: MutableList<T>): MutableList<T>? = (0..this.end).getMut(slice)

public fun RangeToInclusive<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (0..this.end).getUnchecked(slice)
}

public fun RangeToInclusive<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (0..this.end).getUncheckedMut(slice)
}

public fun <T> RangeToInclusive<Int>.index(slice: List<T>): List<T> = (0..this.end).index(slice)

public fun <T> RangeToInclusive<Int>.indexMut(slice: MutableList<T>): MutableList<T> = (0..this.end).indexMut(slice)

/**
 * The methods `index` and `indexMut` panic if the end of the range is out of bounds.
 */
public fun <T> RangeToInclusive<Int>.getTSlice(slice: List<T>): List<T>? = (0..this.last).get(slice)

public fun <T> RangeToInclusive<Int>.getMutTSlice(slice: MutableList<T>): MutableList<T>? = (0..this.last).getMut(slice)

public fun RangeToInclusive<Int>.getUncheckedTSlice(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (0..this.last).getUnchecked(slice)
}

public fun RangeToInclusive<Int>.getUncheckedMutTSlice(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (0..this.last).getUncheckedMut(slice)
}

public fun <T> RangeToInclusive<Int>.indexTSlice(slice: List<T>): List<T> = (0..this.last).index(slice)

public fun <T> RangeToInclusive<Int>.indexMutTSlice(slice: MutableList<T>): MutableList<T> = (0..this.last).indexMut(slice)

/**
 * Performs bounds checking of a range.
 *
 * This method is similar to [Index.index] for slices, but it returns a
 * [Range] equivalent to `range`. You can use this method to turn any range
 * into `start` and `end` values.
 *
 * `bounds` is the range of the slice to use for bounds checking. It should
 * be a [RangeTo] range that ends at the length of the slice.
 *
 * The returned [Range] is safe to pass to [slice.getUnchecked] and
 * [slice.getUncheckedMut] for slices with the given range.
 *
 * [Range]: ops.Range
 * [RangeTo]: ops.RangeTo
 * [slice.getUnchecked]: slice.getUnchecked
 * [slice.getUncheckedMut]: slice.getUncheckedMut
 *
 * # Panics
 *
 * Panics if `range` would be out of bounds.
 *
 * # Examples
 *
 * ```kotlin
 * import std.slice
 *
 * @feature(sliceRange)
 * public fun doctestEntry() {
 *     val v = listOf(10, 40, 30)
 *     check(1 until 2 == slice.range(1 until 2, 0 until v.len()))
 *     check(0 until 2 == slice.range(0 until 2, 0 until v.len()))
 *     check(1 until 3 == slice.range(1 until Int.MAX_VALUE, 0 until v.len()))
 * }
 * ```
 *
 * Panics when [Index.index] would panic:
 *
 * ```kotlin
 * import std.slice
 *
 * @feature(sliceRange)
 * public fun doctestEntry() {
 *     slice.range(2 until 1, 0 until 3)
 * }
 * ```
 *
 * ```kotlin
 * import std.slice
 *
 * @feature(sliceRange)
 * public fun doctestEntry() {
 *     slice.range(1 until 4, 0 until 3)
 * }
 * ```
 *
 * ```kotlin
 * import std.slice
 *
 * @feature(sliceRange)
 * public fun doctestEntry() {
 *     slice.range(1..Int.MAX_VALUE, 0 until 3)
 * }
 * ```
 *
 * [Index.index]: ops.Index.index
 */
public fun <R> range(range: R, bounds: RangeTo<Int>): Range<Int> where R : RangeBounds<Int<R>> {
    val len = bounds.end
    return intoSliceRange(len, Pair(range.startBound().copied(), range.endBound().copied()))
}

/**
 * Performs bounds checking of a range without panicking.
 *
 * This is a version of [range] that returns [None] instead of panicking.
 *
 * # Examples
 *
 * ```kotlin
 * import std.slice
 *
 * @feature(sliceRange)
 * public fun doctestEntry() {
 *     val v = listOf(10, 40, 30)
 *     check(1 until 2 == slice.tryRange(1 until 2, 0 until v.len()))
 *     check(0 until 2 == slice.tryRange(0 until 2, 0 until v.len()))
 *     check(1 until 3 == slice.tryRange(1 until Int.MAX_VALUE, 0 until v.len()))
 * }
 * ```
 *
 * Returns [None] when [Index.index] would panic:
 *
 * ```kotlin
 * import std.slice
 *
 * @feature(sliceRange)
 * public fun doctestEntry() {
 *     check(null == slice.tryRange(2 until 1, 0 until 3))
 *     check(null == slice.tryRange(1 until 4, 0 until 3))
 *     check(null == slice.tryRange(1..Int.MAX_VALUE, 0 until 3))
 * }
 * ```
 *
 * [Index.index]: ops.Index.index
 */
public fun <R> tryRange(range: R, bounds: RangeTo<Int>): Range<Int>? where R : RangeBounds<Int<R>> {
    val len = bounds.end
    return tryIntoSliceRange(len, Pair(range.startBound().copied(), range.endBound().copied())).ok()
}

/**
 * Converts a pair of `ops.Bound`s into `ops.Range` without performing any
 * bounds checking or (in debug) overflow checking.
 */
public fun intoRangeUnchecked(len: Int, tupleArg1: Pair<Bound<Int>, Bound<Int>>): Range<Int> {
    val start =
        when (start) {
            is Bound.Included -> {
                val (i) = start
                i
            }
            is Bound.Excluded -> {
                val (i1) = start
                i1 + 1
            }
            Bound.Unbounded -> 0
        }
    val end =
        when (end) {
            is Bound.Included -> {
                val (i2) = end
                i2 + 1
            }
            is Bound.Excluded -> {
                val (i3) = end
                i3
            }
            Bound.Unbounded -> len
        }
    return start until end
}

public sealed class RangeError {
    public data class EndPastLen(public var end: Int) : RangeError()
    public data class StartPastEnd(public var start: Int, public var end: Int) : RangeError()

    public open fun report(len: Int): Nothing =
        when (this) {
            is EndPastLen -> {
                val end = this.end
                sliceIndexFail(0, end, len)
            }
            is StartPastEnd -> {
                val start = this.start
                val end1 = this.end
                sliceIndexFail(start, end1, len)
            }
        }
}

/**
 * Converts pair of `ops.Bound`s into `ops.Range`.
 * Returns `None` on overflowing indices.
 */
public fun tryIntoSliceRange(len: Int, tupleArg1: Pair<Bound<Int>, Bound<Int>>): Result<Range<Int>, RangeError> {
    val end4 =
        when {
            end is Bound.Included && (end as Bound.Included).value >= len -> {
                val (end) = end as Bound.Included
                return Result.Err(RangeError.EndPastLen(end = end))
            }
            // Cannot overflow because `end<len` implies `end<Int.MAX`.
            end is Bound.Included -> {
                val (end1) = end
                end1 + 1
            }
            end is Bound.Excluded && (end as Bound.Excluded).value > len -> {
                val (end2) = end as Bound.Excluded
                return Result.Err(RangeError.EndPastLen(end = end2))
            }
            end is Bound.Excluded -> {
                val (end3) = end
                end3
            }
            else -> len
        }
    val start4 =
        when {
            start is Bound.Excluded && (start as Bound.Excluded).value >= end4 -> {
                val (start) = start as Bound.Excluded
                return Result.Err(RangeError.StartPastEnd(start = start, end = end4))
            }
            // Cannot overflow because `start<end` implies `start<Int.MAX`.
            start is Bound.Excluded -> {
                val (start1) = start
                start1 + 1
            }
            start is Bound.Included && (start as Bound.Included).value > end4 -> {
                val (start2) = start as Bound.Included
                return Result.Err(RangeError.StartPastEnd(start = start2, end = end4))
            }
            start is Bound.Included -> {
                val (start3) = start
                start3
            }
            else -> 0
        }
    return Result.Ok(start4 until end4)
}

/**
 * Converts pair of `ops.Bound`s into `ops.Range`.
 * Panics on overflowing indices.
 */
public fun intoSliceRange(len: Int, bounds: Pair<Bound<Int>, Bound<Int>>): Range<Int> =
    run {
        val matchVal = tryIntoSliceRange(len, bounds)
        when (matchVal) { is Result.Ok<*> -> {
            val range = matchVal.value
            range
        }; is Result.Err<*> -> {
            val e = matchVal.error
            e.report(len)
        } }
    }

public fun <T> Pair<Bound<Int>, Bound<Int>>.get(slice: List<T>): List<T>? {
    return (tryIntoSliceRange(slice.size, this).ok() ?: return null).get(slice)
}

public fun <T> Pair<Bound<Int>, Bound<Int>>.getMut(slice: MutableList<T>): List<T>? {
    return (tryIntoSliceRange(slice.size, this).ok() ?: return null).getMut(slice)
}

public fun Pair<Bound<Int>, Bound<Int>>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return intoRangeUnchecked(slice.len(), this).getUnchecked(slice)
}

public fun Pair<Bound<Int>, Bound<Int>>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return intoRangeUnchecked(slice.len(), this).getUncheckedMut(slice)
}

public fun <T> Pair<Bound<Int>, Bound<Int>>.index(slice: List<T>): List<T> = intoSliceRange(slice.size, this).index(slice)

public fun <T> Pair<Bound<Int>, Bound<Int>>.indexMut(slice: MutableList<T>): List<T> = intoSliceRange(slice.size, this).indexMut(slice)
