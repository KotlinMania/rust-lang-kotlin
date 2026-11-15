// port-lint: source str/traits.rs
package io.github.kotlinmania.rustlang.core.str.traits

import io.github.kotlinmania.rustlang.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.intrinsics.uncheckedSub
import io.github.kotlinmania.rustlang.ops.range.Bound
import io.github.kotlinmania.rustlang.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.ops.range.RangeFull
import io.github.kotlinmania.rustlang.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.ops.range.RangeTo
import io.github.kotlinmania.rustlang.ops.range.RangeToInclusive
import io.github.kotlinmania.rustlang.range.Range
import io.github.kotlinmania.rustlang.slice.index.SliceIndex
import io.github.kotlinmania.rustlang.slice.index.intoRangeUnchecked
import io.github.kotlinmania.rustlang.slice.index.intoSliceRange
import io.github.kotlinmania.rustlang.slice.index.tryIntoSliceRange
import io.github.kotlinmania.rustlang.str.error.ParseBoolError
import io.github.kotlinmania.rustlang.str.sliceErrorFail

// Trait implementations for `String`.

/**
 * Implements ordering of strings.
 *
 * Strings are ordered  [lexicographically](Ord#lexicographical-comparison) by their byte values. This orders Unicode code
 * points based on their positions in the code charts. This is not necessarily the same as
 * "alphabetical" order, which varies by language and locale. Sorting strings according to
 * culturally-accepted standards requires locale-specific data that is outside the scope of
 * the `String` type.
 */
public fun String.cmp(other: String): Ordering = this.encodeToByteArray().map { it.toUByte() }.cmp(other.encodeToByteArray().map { it.toUByte() })

public fun String.eq(other: String): Boolean = this.encodeToByteArray().map { it.toUByte() } == other.encodeToByteArray().map { it.toUByte() }

/**
 * Implements comparison operations on strings.
 *
 * Strings are compared [lexicographically](Ord#lexicographical-comparison) by their byte values. This compares Unicode code
 * points based on their positions in the code charts. This is not necessarily the same as
 * "alphabetical" order, which varies by language and locale. Comparing strings according to
 * culturally-accepted standards requires locale-specific data that is outside the scope of
 * the `String` type.
 */
public fun String.partialCmp(other: String): Ordering? = this.cmp(other)

public fun <I> String.index(index: I): Output where I : SliceIndex<String, Any?> = index.index(this)

public fun <I> String.indexMut(index: I): Output where I : SliceIndex<String, Any?> = index.indexMut(this)

/**
 * Implements substring slicing with syntax `self[..]` or `self[..]`.
 *
 * Returns a slice of the whole string, i.e., returns `self` or `&mut
 * self`.Equivalent to`&self[0 .. len]`or`self[0 .. len]`. Unlike
 * other indexing operations, this can never panic.
 *
 * This operation is *O*(1).
 *
 * Prior to 1.20.0, these indexing operations were still supported by
 * direct implementation of `Index` and `IndexMut`.
 *
 * Equivalent to `self[0 .. len]` or `self[0 .. len]`.
 */
public fun RangeFull.get(slice: String): String? = slice

public fun RangeFull.getMut(slice: String): String? = slice

public fun RangeFull.getUnchecked(slice: ConstPtr): ConstPtr = slice

public fun RangeFull.getUncheckedMut(slice: MutPtr): MutPtr = slice

public fun RangeFull.index(slice: String): String = slice

public fun RangeFull.indexMut(slice: String): String = slice

/**
 * Check that a range is in bounds for slicing a string.
 * If this returns true, it is safe to call `slice.getUnchecked(range)` or
 * `slice.getUncheckedMut(range)`.
 */
public fun checkRange(slice: String, range: Range<Int>): Boolean {
    val start = range.start
    val end = range.end
    val bytes = slice.encodeToByteArray().map { it.toUByte() }
    if (start > end || end > slice.length) {
        return false
    }
    if (start == slice.length) {
        // If `start == slice.len()`, then `end == slice.len()` must also be true.
        return true
    }
    // SAFETY:
    // `start> end || end> slice.len()` is false, so `start <= end <= slice.len()` is true.
    // `start == slice.len()` is false, so `start<slice.len()` is also true.
    //
    // No need to check for `end == 0`, because if `end == 0` is true then `start == slice.len()`
    // would also be true, which is already handled above.
    return (start == 0 || (PrimitiveLayouts.U8).readNative((ConstPtr((bytes.asPtr()).rawAddress + ((start).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).isUtf8CharBoundary()) && (end == slice.length || (PrimitiveLayouts.U8).readNative((ConstPtr((bytes.asPtr()).rawAddress + ((end).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).isUtf8CharBoundary())
}

/**
 * Implements substring slicing with syntax `self[begin .. end]` or `&mut
 * self[begin .. end]`.
 *
 * Returns a slice of the given string from the byte range
 * [`begin`, `end`).
 *
 * This operation is *O*(1).
 *
 * Prior to 1.20.0, these indexing operations were still supported by
 * direct implementation of `Index` and `IndexMut`.
 *
 * # Panics
 *
 * Panics if `begin` or `end` does not point to the starting byte offset of
 * a character (as defined by `isCharBoundary`), if `begin> end`, or if
 * `end> len`.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * check(s[0 until 1] == "L")
 * check(s[1 until 9] == "öwe 老")
 * // these will panic:
 * // byte 2 lies within `ö`:
 * // &s[2 ..3];
 * // byte 8 lies within `老`
 * // &s[1 .. 8];
 * // byte 100 is outside the string
 * // &s[3 .. 100];
 * ```
 */
public fun io.github.kotlinmania.rustlang.ops.range.Range<Int>.get(slice: String): String? = range.Range.from(this).get(slice)

public fun io.github.kotlinmania.rustlang.ops.range.Range<Int>.getMut(slice: String): String? = range.Range.from(this).getMut(slice)

public fun io.github.kotlinmania.rustlang.ops.range.Range<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    Unit
    // SAFETY: the caller guarantees that `self` is in bounds of `slice`
    // which satisfies all the conditions for `add`.
    return run {
        val newLen = uncheckedSub(this.end, this.start)
        constPtrFromRustCast(slice1.asPtr().add(this.start).castSlice(newLen))
    }
}

public fun io.github.kotlinmania.rustlang.ops.range.Range<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    val slice1 = MutPtr(slice.rawAddress, slice.rawMeta)
    Unit
    // SAFETY: see comments for `getUnchecked`.
    return run {
        val newLen = uncheckedSub(this.end, this.start)
        mutPtrFromRustCast(slice1.asMutPtr().add(this.start).castSlice(newLen))
    }
}

public fun io.github.kotlinmania.rustlang.ops.range.Range<Int>.index(slice: String): String = range.Range.from(this).index(slice)

public fun io.github.kotlinmania.rustlang.ops.range.Range<Int>.indexMut(slice: String): String = range.Range.from(this).indexMut(slice)

public fun range.Range<Int>.get(slice: String): String? {
    return if (checkRange(slice, this)) {
        // SAFETY: just checked that `self` is in bounds,
        // and we are passing in a safe reference, so the return value will also be one.
        // We also checked char boundaries, so this is valid UTF-8.
        (ObjectSlotLayout<String>()).readNative((this.getUnchecked(slice)).rawAddress)
    } else {
        null
    }
}

public fun range.Range<Int>.getMut(slice: String): String? =
    if (checkRange(slice, this)) {
        // SAFETY: just checked that `self` is in bounds.
        // We know the pointer is unique because we got it from `slice`.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        null
    }

public fun range.Range<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    Unit
    // SAFETY: the caller guarantees that `self` is in bounds of `slice`
    // which satisfies all the conditions for `add`.
    return run {
        val newLen = uncheckedSub(this.end, this.start)
        constPtrFromRustCast(slice1.asPtr().add(this.start).castSlice(newLen))
    }
}

public fun range.Range<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    val slice1 = MutPtr(slice.rawAddress, slice.rawMeta)
    Unit
    // SAFETY: see comments for `getUnchecked`.
    return run {
        val newLen = uncheckedSub(this.end, this.start)
        mutPtrFromRustCast(slice1.asMutPtr().add(this.start).castSlice(newLen))
    }
}

public fun range.Range<Int>.index(slice: String): String {
    val (start, end) = Pair(this.start, this.end)
    return when (val matchVal = this.get(slice)) {
        null -> sliceErrorFail(slice, start, end)
        else -> {
            val s = matchVal
            s
        }
    }
}

public fun range.Range<Int>.indexMut(slice: String): String {
    // cannot reuse `get` as above, because of NLL trouble
    return if (checkRange(slice, this)) {
        // SAFETY: just checked that `self` is in bounds,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        sliceErrorFail(slice, this.start, this.end)
    }
}

/**
 * Implements substring slicing for arbitrary bounds.
 *
 * Returns a slice of the given string bounded by the byte indices
 * provided by each bound.
 *
 * This operation is *O*(1).
 *
 * # Panics
 *
 * Panics if `begin` or `end` (if it exists and once adjusted for
 * inclusion/exclusion) does not point to the starting byte offset of
 * a character (as defined by `isCharBoundary`), if `begin> end`, or if
 * `end> len`.
 */
public fun Pair<Bound<Int>, Bound<Int>>.get(slice: String): String? {
    return (tryIntoSliceRange(slice.length, this).ok() ?: return null).get(slice)
}

public fun Pair<Bound<Int>, Bound<Int>>.getMut(slice: String): String? {
    return (tryIntoSliceRange(slice.length, this).ok() ?: return null).getMut(slice)
}

public fun Pair<Bound<Int>, Bound<Int>>.getUnchecked(slice: ConstPtr): ConstPtr {
    val len = (ConstPtr(slice.rawAddress, slice.rawMeta)).len()
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return intoRangeUnchecked(len, this).getUnchecked(slice)
}

public fun Pair<Bound<Int>, Bound<Int>>.getUncheckedMut(slice: MutPtr): MutPtr {
    val len = (MutPtr(slice.rawAddress, slice.rawMeta)).len()
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return intoRangeUnchecked(len, this).getUncheckedMut(slice)
}

public fun Pair<Bound<Int>, Bound<Int>>.index(slice: String): String = intoSliceRange(slice.length, this).index(slice)

public fun Pair<Bound<Int>, Bound<Int>>.indexMut(slice: String): String = intoSliceRange(slice.length, this).indexMut(slice)

/**
 * Implements substring slicing with syntax `self[.. end]` or `&mut
 * self[.. end]`.
 *
 * Returns a slice of the given string from the byte range \[0, `end`).
 * Equivalent to `self[0 .. end]` or `self[0 .. end]`.
 *
 * This operation is *O*(1).
 *
 * Prior to 1.20.0, these indexing operations were still supported by
 * direct implementation of `Index` and `IndexMut`.
 *
 * # Panics
 *
 * Panics if `end` does not point to the starting byte offset of a
 * character (as defined by `isCharBoundary`), or if `end> len`.
 */
public fun RangeTo<Int>.get(slice: String): String? {
    return if (slice.isCharBoundary(this.end)) {
        // SAFETY: just checked that `end` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        (ObjectSlotLayout<String>()).readNative((this.getUnchecked(slice)).rawAddress)
    } else {
        null
    }
}

public fun RangeTo<Int>.getMut(slice: String): String? {
    return if (slice.isCharBoundary(this.end)) {
        // SAFETY: just checked that `end` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        null
    }
}

public fun RangeTo<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (0 until this.end).getUnchecked(slice)
}

public fun RangeTo<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (0 until this.end).getUncheckedMut(slice)
}

public fun RangeTo<Int>.index(slice: String): String {
    val end = this.end
    return when (val matchVal = this.get(slice)) {
        null -> sliceErrorFail(slice, 0, end)
        else -> {
            val s = matchVal
            s
        }
    }
}

public fun RangeTo<Int>.indexMut(slice: String): String {
    return if (slice.isCharBoundary(this.end)) {
        // SAFETY: just checked that `end` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        sliceErrorFail(slice, 0, this.end)
    }
}

/**
 * Implements substring slicing with syntax `self[begin ..]` or `&mut
 * self[begin ..]`.
 *
 * Returns a slice of the given string from the byte range \[`begin`, `len`).
 * Equivalent to `self[begin .. len]` or `self[begin .. len]`.
 *
 * This operation is *O*(1).
 *
 * Prior to 1.20.0, these indexing operations were still supported by
 * direct implementation of `Index` and `IndexMut`.
 *
 * # Panics
 *
 * Panics if `begin` does not point to the starting byte offset of
 * a character (as defined by `isCharBoundary`), or if `begin> len`.
 */
public fun RangeFrom<Int>.get(slice: String): String? {
    return if (slice.isCharBoundary(this.start)) {
        // SAFETY: just checked that `start` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        (ObjectSlotLayout<String>()).readNative((this.getUnchecked(slice)).rawAddress)
    } else {
        null
    }
}

public fun RangeFrom<Int>.getMut(slice: String): String? {
    return if (slice.isCharBoundary(this.start)) {
        // SAFETY: just checked that `start` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        null
    }
}

public fun RangeFrom<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    val len = (ConstPtr(slice.rawAddress, slice.rawMeta)).len()
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (this.start until len).getUnchecked(slice)
}

public fun RangeFrom<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    val len = (MutPtr(slice.rawAddress, slice.rawMeta)).len()
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (this.start until len).getUncheckedMut(slice)
}

public fun RangeFrom<Int>.index(slice: String): String {
    val (start, end) = Pair(this.start, slice.length)
    return when (val matchVal = this.get(slice)) {
        null -> sliceErrorFail(slice, start, end)
        else -> {
            val s = matchVal
            s
        }
    }
}

public fun RangeFrom<Int>.indexMut(slice: String): String {
    return if (slice.isCharBoundary(this.start)) {
        // SAFETY: just checked that `start` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        sliceErrorFail(slice, this.start, slice.length)
    }
}

public fun range.RangeFrom<Int>.get(slice: String): String? {
    return if (slice.isCharBoundary(this.start)) {
        // SAFETY: just checked that `start` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        (ObjectSlotLayout<String>()).readNative((this.getUnchecked(slice)).rawAddress)
    } else {
        null
    }
}

public fun range.RangeFrom<Int>.getMut(slice: String): String? {
    return if (slice.isCharBoundary(this.start)) {
        // SAFETY: just checked that `start` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        null
    }
}

public fun range.RangeFrom<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    val len = (ConstPtr(slice.rawAddress, slice.rawMeta)).len()
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return (this.start until len).getUnchecked(slice)
}

public fun range.RangeFrom<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    val len = (MutPtr(slice.rawAddress, slice.rawMeta)).len()
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return (this.start until len).getUncheckedMut(slice)
}

public fun range.RangeFrom<Int>.index(slice: String): String {
    val (start, end) = Pair(this.start, slice.length)
    return when (val matchVal = this.get(slice)) {
        null -> sliceErrorFail(slice, start, end)
        else -> {
            val s = matchVal
            s
        }
    }
}

public fun range.RangeFrom<Int>.indexMut(slice: String): String {
    return if (slice.isCharBoundary(this.start)) {
        // SAFETY: just checked that `start` is on a char boundary,
        // and we are passing in a safe reference, so the return value will also be one.
        RustMutableReference(MutPtr((this.getUncheckedMut(slice)).rawAddress), ObjectSlotLayout<String>())
    } else {
        sliceErrorFail(slice, this.start, slice.length)
    }
}

/**
 * Implements substring slicing with syntax `self[begin ..= end]` or `&mut
 * self[begin ..= end]`.
 *
 * Returns a slice of the given string from the byte range
 * [`begin`, `end`]. Equivalent to `self[begin .. end + 1]` or `&mut
 * self[begin .. end + 1]`, except if`end` has the maximum value for
 * `Int`.
 *
 * This operation is *O*(1).
 *
 * # Panics
 *
 * Panics if `begin` does not point to the starting byte offset of
 * a character (as defined by `isCharBoundary`), if `end` does not point
 * to the ending byte offset of a character (`end + 1` is either a starting
 * byte offset or equal to `len`), if `begin> end`, or if `end >= len`.
 */
public fun RangeInclusive<Int>.get(slice: String): String? =
    if (this.end() >= slice.length) {
        null
    } else {
        this.intoSliceRange().get(slice)
    }

public fun RangeInclusive<Int>.getMut(slice: String): String? =
    if (this.end() >= slice.length) {
        null
    } else {
        this.intoSliceRange().getMut(slice)
    }

public fun RangeInclusive<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `getUnchecked`.
    return this.intoSliceRange().getUnchecked(slice)
}

public fun RangeInclusive<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `getUncheckedMut`.
    return this.intoSliceRange().getUncheckedMut(slice)
}

public fun RangeInclusive<Int>.index(slice: String): String {
    var start = this.start
    var end = this.end
    val exhausted = this.exhausted
    val len = slice.length
    if (end < len) {
        end = end + 1
        start = if (exhausted) {
            end
        } else {
            start
        }
        if (start <= end && slice.isCharBoundary(start) && slice.isCharBoundary(end)) {
            // SAFETY: just checked that `start` and `end` are on a char boundary,
            // and we are passing in a safe reference, so the return value will also be one.
            // We also checked char boundaries, so this is valid UTF-8.
            return (start until end).getUnchecked(slice)
        }
    }
    return sliceErrorFail(slice, start, end)
}

public fun RangeInclusive<Int>.indexMut(slice: String): String {
    var start = this.start
    var end = this.end
    val exhausted = this.exhausted
    val len = slice.length
    if (end < len) {
        end = end + 1
        start = if (exhausted) {
            end
        } else {
            start
        }
        if (start <= end && slice.isCharBoundary(start) && slice.isCharBoundary(end)) {
            // SAFETY: just checked that `start` and `end` are on a char boundary,
            // and we are passing in a safe reference, so the return value will also be one.
            // We also checked char boundaries, so this is valid UTF-8.
            return (start until end).getUncheckedMut(slice)
        }
    }
    return sliceErrorFail(slice, start, end)
}

public fun range.RangeInclusive<Int>.get(slice: String): String? = RangeInclusive.from(this).get(slice)

public fun range.RangeInclusive<Int>.getMut(slice: String): String? = RangeInclusive.from(this).getMut(slice)

public fun range.RangeInclusive<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `getUnchecked`.
    return RangeInclusive.from(this).getUnchecked(slice)
}

public fun range.RangeInclusive<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `getUncheckedMut`.
    return RangeInclusive.from(this).getUncheckedMut(slice)
}

public fun range.RangeInclusive<Int>.index(slice: String): String = RangeInclusive.from(this).index(slice)

public fun range.RangeInclusive<Int>.indexMut(slice: String): String = RangeInclusive.from(this).indexMut(slice)

/**
 * Implements substring slicing with syntax `self[..= end]` or `&mut
 * self[..= end]`.
 *
 * Returns a slice of the given string from the byte range \[0, `end`\].
 * Equivalent to `self[0 .. end + 1]`, except if `end` has the maximum
 * value for `Int`.
 *
 * This operation is *O*(1).
 *
 * # Panics
 *
 * Panics if `end` does not point to the ending byte offset of a character
 * (`end + 1` is either a starting byte offset as defined by
 * `isCharBoundary`, or equal to `len`), or if `end >= len`.
 */
public fun RangeToInclusive<Int>.get(slice: String): String? = (0..this.end).get(slice)

public fun RangeToInclusive<Int>.getMut(slice: String): String? = (0..this.end).getMut(slice)

public fun RangeToInclusive<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `getUnchecked`.
    return (0..this.end).getUnchecked(slice)
}

public fun RangeToInclusive<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `getUncheckedMut`.
    return (0..this.end).getUncheckedMut(slice)
}

public fun RangeToInclusive<Int>.index(slice: String): String = (0..this.end).index(slice)

public fun RangeToInclusive<Int>.indexMut(slice: String): String = (0..this.end).indexMut(slice)

/**
 * Implements substring slicing with syntax `self[..= last]` or `&mut
 * self[..= last]`.
 *
 * Returns a slice of the given string from the byte range \[0, `last`\].
 * Equivalent to `self[0 .. last + 1]`, except if `last` has the maximum
 * value for `Int`.
 *
 * This operation is *O*(1).
 *
 * # Panics
 *
 * Panics if `last` does not point to the ending byte offset of a character
 * (`last + 1` is either a starting byte offset as defined by
 * `isCharBoundary`, or equal to `len`), or if `last >= len`.
 */
public fun range.RangeToInclusive<Int>.get(slice: String): String? = (0..this.last).get(slice)

public fun range.RangeToInclusive<Int>.getMut(slice: String): String? = (0..this.last).getMut(slice)

public fun range.RangeToInclusive<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `getUnchecked`.
    return (0..this.last).getUnchecked(slice)
}

public fun range.RangeToInclusive<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `getUncheckedMut`.
    return (0..this.last).getUncheckedMut(slice)
}

public fun range.RangeToInclusive<Int>.index(slice: String): String = (0..this.last).index(slice)

public fun range.RangeToInclusive<Int>.indexMut(slice: String): String = (0..this.last).indexMut(slice)

/**
 * Parse a value from a string
 *
 * `FromStr`s [fromStr] method is often used implicitly, through
 * [str]s [parse] method. See [parse]s documentation for examples.
 *
 * [fromStr]: FromStr.fromStr
 * [parse]: str.parse
 *
 * `FromStr` does not have a scope parameter, and so you can only parse types
 * that do not contain a scope parameter themselves. In other words, you can
 * parse an `Int` with `FromStr`, but not a `Int`. You can parse a struct that
 * contains an `Int`, but not one that contains an `Int`.
 *
 * # Input format and round-tripping
 *
 * The input format expected by a type's `FromStr` implementation depends on the type. Check the
 * type's documentation for the input formats it knows how to parse. Note that the input format of
 * a type's `FromStr` implementation might not necessarily accept the output format of its
 * `Display` implementation, and even if it does, the `Display` implementation may not be lossless
 * so the round-trip may lose information.
 *
 * However, if a type has a lossless `Display` implementation whose output is meant to be
 * conveniently machine-parseable and not just meant for human consumption, then the type may wish
 * to accept the same format in `FromStr`, and document that usage. Having both `Display` and
 * `FromStr` implementations where the result of `Display` cannot be parsed with `FromStr` may
 * surprise users.
 *
 * # Examples
 *
 * Basic implementation of `FromStr` on an example `Point` type:
 *
 * ```kotlin
 * import std.str.FromStr
 *
 * open class ParsePointError
 * open class Point(public var x: Int, public var y: Int) : FromStr<ParsePointError> {
 *     public open fun fromStr(s: String): Result<Point> {
 *         val (x, y) = ((s.takeIf { it.startsWith('(') }?.removePrefix('('))?.let({ s1 -> s1.takeIf { it.endsWith(')') }?.removeSuffix(')') })?.let({ s1 -> s1.let { s -> val idx = s.indexOf(','); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } })?.let { Result.success(it) } ?: Result.failure(ParsePointError())).getOrElse { return Result.failure(it) }
 *         val xFromstr = (x.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).mapErr({ ParsePointError() }).getOrElse { return Result.failure(it) }
 *         val yFromstr = (y.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).mapErr({ ParsePointError() }).getOrElse { return Result.failure(it) }
 *         return Result.success(Point(x = xFromstr, y = yFromstr))
 *     }
 * }
 * val expected = Result.success(Point(x = 1, y = 2))
 * // Explicit call
 * check(Point.fromStr("(1,2)") == expected)
 * // Implicit calls, through parse
 * check("(1,2)".parse() == expected)
 * check("(1,2)".parse<Point>() == expected)
 * // Invalid input string
 * check(Point.fromStr("(1 2)").isErr())
 * ```
 */
public interface FromStr<T, out Err> {
    /**
     * Parses a string `s` to return a value of this type.
     *
     * If parsing succeeds, return the value inside [Ok], otherwise
     * when the string is ill-formatted return an error specific to the
     * inside [Err]. The error type is specific to the implementation of the trait.
     *
     * # Examples
     *
     * Basic usage with [i32], a type that implements `FromStr`:
     *
     * ```kotlin
     * import std.str.FromStr
     *
     * val s = "5"
     * val x = Int.fromStr(s).unwrap()
     * check(5 == x)
     * ```
     */
    public fun fromStr(s: String): Result<T, Err>
}

/**
 * Parse a `Boolean` from a string.
 *
 * The only accepted values are `"true"` and `"false"`. Any other input
 * will return an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.str.FromStr
 *
 * check(FromStr.fromStr("true") == Result.success(true))
 * check(FromStr.fromStr("false") == Result.success(false))
 * check(Boolean.fromStr("not even a boolean").isErr())
 * ```
 *
 * Note, in many cases, the `.parse()` method on `String` is more proper.
 *
 * ```kotlin
 * check("true".parse() == Result.success(true))
 * check("false".parse() == Result.success(false))
 * check((("not even a boolean").toBooleanStrictOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Boolean"))).isErr())
 * ```
 */
public fun fromStrBoolean(s: String): Result<Boolean, ParseBoolError> =
    when (s) {
        "true" -> Result.Ok(true)
        "false" -> Result.Ok(false)
        else -> Result.Err(ParseBoolError)
    }
