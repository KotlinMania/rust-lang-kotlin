// port-lint: source slice/raw.rs
package io.github.kotlinmania.rustlang.core.slice.raw

import io.github.kotlinmania.rustlang.core.intrinsics.ubChecks
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.core.ubchecks.assertUnsafePrecondition
import io.github.kotlinmania.rustlang.core.ubchecks.checkLanguageUb

// Free functions to create `[T]` and `[T]`.

/**
 * Forms a slice from a pointer and a length.
 *
 * The `len` argument is the number of **elements**, not the number of bytes.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `data` must be non-null, [valid] for reads for `len * sizeOf<T>()` many bytes,
 *   and it must be properly aligned. This means in particular:
 *
 *     * The entire memory range of this slice must be contained within a single allocation!
 *       Slices can never span across multiple allocations. See [below](#incorrect-usage)
 *       for an example incorrectly not taking this into account.
 *     * `data` must be non-null and aligned even for zero-length slices or slices of ZSTs. One
 *       reason for this is that enum layout optimizations may rely on references
 *       (including slices of any length) being aligned and non-null to distinguish
 *       them from other data. You can obtain a pointer that is usable as `data`
 *       for zero-length slices using [NonNull.dangling].
 *
 * * `data` must point to `len` consecutive properly initialized values of type `T`.
 *
 * * The memory referenced by the returned slice must not be mutated for the duration
 *   of scope `a`, except inside an `UnsafeCell`.
 *
 * * The total size `len * sizeOf<T>()` of the slice must be no larger than `Long.MAX`,
 *   and adding that size to `data` must not "wrap around" the address space.
 *   See the safety documentation of [pointer.offset].
 *
 * # Caveat
 *
 * The scope for the returned slice is inferred from its usage. To
 * prevent accidental misuse, it's suggested to tie the scope to whichever
 * source scope is safe in the context, such as by providing a helper
 * function taking the scope of a host value for the slice, or by explicit
 * annotation.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.slice
 *
 * // manifest a slice for a single element
 * val localStorageL6C0 = AddressableLocal(42, PrimitiveLayouts.I32)
 * val ptr = ConstPtr(localStorageL6C0.asMutPtr().rawAddress)
 * val slice = slice.fromRawParts(ptr, 1)
 * check(slice[0] == 42)
 * ```
 *
 * ### Incorrect usage
 *
 * The following `joinSlices` function is **unsound** ⚠️
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.slice
 *
 * fun <T> joinSlices(fst: List<T>, snd: List<T>): List<T> {
 *     val fstEnd = ConstPtr((fst.asPtr()).rawAddress + ((fst.size).toLong() * (ObjectSlotLayout<T>()).strideBytes))
 *     val sndStart = snd.asPtr()
 *     check(fstEnd == sndStart)
 *     return slice.fromRawParts(fst.asPtr(), fst.size + snd.size)
 * }
 * fun main() {
 *     // `a` and `b` are different allocations...
 *     val a = 42
 *     val b = 27
 *     // ... which may nevertheless be laid out contiguously in memory: | a | b |
 *     joinSlices(slice.fromRef(a), slice.fromRef(b))
 *     // UB
 * }
 * ```
 *
 * ### FFI: Handling null pointers
 *
 * In languages such as C++, pointers to empty collections are not guaranteed to be non-null.
 * When accepting such pointers, they have to be checked for null-ness to avoid undefined
 * behavior.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.slice
 *
 * / **
 *  * Sum the elements of an FFI slice.
 *  *
 *  * # Safety
 *  *
 *  * If ptr is not NULL, it must be correctly aligned and
 *  * point to `len` initialized items of type `Float`.
 *  * /
 * fun sumSlice(ptr: ConstPtr, len: Int): Float {
 *     val data = if (((ptr).rawAddress == 0L)) {
 *         // `len` is assumed to be 0.
 *         listOf()
 *     } else {
 *         // SAFETY: see function docstring.
 *         slice.fromRawParts(ptr, len)
 *     }
 *     return data.intoIter().sum()
 * }
 * // This could be the result of C++'s std.vector.data():
 * val ptr = ConstPtr(0L)
 * // And this could be std.vector.size():
 * val len = 0
 * check(sumSlice(ptr, len) == 0.0f.toFloat())
 * ```
 *
 * [valid]: ptr#safety
 * [NonNull.dangling]: ptr.NonNull.dangling
 */
public fun <T> fromRawParts(data: ConstPtr, len: Int): List<T> {
    // SAFETY: the caller must uphold the safety contract for `fromRawParts`.
    return run {
        assertUnsafePrecondition(checkLanguageUb, "slice::from_raw_parts requires the pointer to be aligned and non-null, and the total size of the slice not to exceed `isize::MAX`", data, data, size, sizeOf, T, align, alignOf, T, len, len, ubChecks, maybeIsAlignedAndNotNull, data, align, false, ubChecks, isValidAllocationSize, size, len)
        (PrimitiveLayouts.U8).readNative((sliceFromRawParts(data, len)).rawAddress)
    }
}

/**
 * Performs the same functionality as [fromRawParts], except that a
 * mutable slice is returned.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `data` must be non-null, [valid] for both reads and writes for `len * sizeOf<T>()` many bytes,
 *   and it must be properly aligned. This means in particular:
 *
 *     * The entire memory range of this slice must be contained within a single allocation!
 *       Slices can never span across multiple allocations.
 *     * `data` must be non-null and aligned even for zero-length slices or slices of ZSTs. One
 *       reason for this is that enum layout optimizations may rely on references
 *       (including slices of any length) being aligned and non-null to distinguish
 *       them from other data. You can obtain a pointer that is usable as `data`
 *       for zero-length slices using [NonNull.dangling].
 *
 * * `data` must point to `len` consecutive properly initialized values of type `T`.
 *
 * * The memory referenced by the returned slice must not be accessed through any other pointer
 *   (not derived from the return value) for the duration of scope `a`.
 *   Both read and write accesses are forbidden.
 *
 * * The total size `len * sizeOf<T>()` of the slice must be no larger than `Long.MAX`,
 *   and adding that size to `data` must not "wrap around" the address space.
 *   See the safety documentation of [pointer.offset].
 *
 * [valid]: ptr#safety
 * [NonNull.dangling]: ptr.NonNull.dangling
 */
public fun <T> fromRawPartsMut(data: MutPtr, len: Int): MutableList<T> {
    // SAFETY: the caller must uphold the safety contract for `fromRawPartsMut`.
    return run {
        assertUnsafePrecondition(checkLanguageUb, "slice::from_raw_parts_mut requires the pointer to be aligned and non-null, and the total size of the slice not to exceed `isize::MAX`", data, data, size, sizeOf, T, align, alignOf, T, len, len, ubChecks, maybeIsAlignedAndNotNull, data, align, false, ubChecks, isValidAllocationSize, size, len)
        RustMutableReference(MutPtr((sliceFromRawPartsMut(data, len)).rawAddress), PrimitiveLayouts.U8)
    }
}

/**
 * Converts a reference to T into a slice of length 1 (without copying).
 */
public fun <T> fromRef(s: T): List<T> = io.github.kotlinmania.rustlang.core.array.fromRef(s)

/**
 * Converts a reference to T into a slice of length 1 (without copying).
 */
public fun <T> fromMut(s: T): MutableList<T> = io.github.kotlinmania.rustlang.core.array.fromMut(s)

/**
 * Forms a slice from a pointer range.
 *
 * This function is useful for interacting with foreign interfaces which
 * use two pointers to refer to a range of elements in memory, as is
 * common in C++.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * The `start` pointer of the range must be a non-null, [valid] and properly aligned pointer
 *   to the first element of a slice.
 *
 * * The `end` pointer must be a [valid] and properly aligned pointer to *one past*
 *   the last element, such that the offset from the end to the start pointer is
 *   the length of the slice.
 *
 * * The entire memory range of this slice must be contained within a single allocation!
 *   Slices can never span across multiple allocations.
 *
 * * The range must contain `N` consecutive properly initialized values of type `T`.
 *
 * * The memory referenced by the returned slice must not be mutated for the duration
 *   of scope `a`, except inside an `UnsafeCell`.
 *
 * * The total length of the range must be no larger than `Long.MAX`,
 *   and adding that size to `start` must not "wrap around" the address space.
 *   See the safety documentation of [pointer.offset].
 *
 * Note that a range created from [slice.asPtrRange] fulfills these requirements.
 *
 * # Panics
 *
 * This function panics if `T` is a Zero-Sized Type (“ZST”).
 *
 * # Caveat
 *
 * The scope for the returned slice is inferred from its usage. To
 * prevent accidental misuse, it's suggested to tie the scope to whichever
 * source scope is safe in the context, such as by providing a helper
 * function taking the scope of a host value for the slice, or by explicit
 * annotation.
 *
 * # Examples
 *
 * ```kotlin
 * import core.slice
 *
 * @feature(sliceFromPtrRange)
 * public fun doctestEntry() {
 *     val x = listOf(1, 2, 3)
 *     val range = x.asPtrRange()
 *     run {
 *         check(slice.fromPtrRange(range) == x)
 *     }
 * }
 * ```
 *
 * [valid]: ptr#safety
 */
public fun <T> fromPtrRange(range: Range<ConstPtr>): List<T> {
    // SAFETY: the caller must uphold the safety contract for `fromPtrRange`.
    return rawSliceList(range.start, range.end.offsetFromUnsigned(range.start).toInt(), PrimitiveLayouts.U8)
}

/**
 * Forms a mutable slice from a pointer range.
 *
 * This is the same functionality as [fromPtrRange], except that a
 * mutable slice is returned.
 *
 * This function is useful for interacting with foreign interfaces which
 * use two pointers to refer to a range of elements in memory, as is
 * common in C++.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * The `start` pointer of the range must be a non-null, [valid] and properly aligned pointer
 *   to the first element of a slice.
 *
 * * The `end` pointer must be a [valid] and properly aligned pointer to *one past*
 *   the last element, such that the offset from the end to the start pointer is
 *   the length of the slice.
 *
 * * The entire memory range of this slice must be contained within a single allocation!
 *   Slices can never span across multiple allocations.
 *
 * * The range must contain `N` consecutive properly initialized values of type `T`.
 *
 * * The memory referenced by the returned slice must not be accessed through any other pointer
 *   (not derived from the return value) for the duration of scope `a`.
 *   Both read and write accesses are forbidden.
 *
 * * The total length of the range must be no larger than `Long.MAX`,
 *   and adding that size to `start` must not "wrap around" the address space.
 *   See the safety documentation of [pointer.offset].
 *
 * Note that a range created from [slice.asMutPtrRange] fulfills these requirements.
 *
 * # Panics
 *
 * This function panics if `T` is a Zero-Sized Type (“ZST”).
 *
 * # Caveat
 *
 * The scope for the returned slice is inferred from its usage. To
 * prevent accidental misuse, it's suggested to tie the scope to whichever
 * source scope is safe in the context, such as by providing a helper
 * function taking the scope of a host value for the slice, or by explicit
 * annotation.
 *
 * # Examples
 *
 * ```kotlin
 * import core.slice
 *
 * @feature(sliceFromPtrRange)
 * public fun doctestEntry() {
 *     var x = mutableListOf(1, 2, 3)
 *     val range = x.asMutPtrRange()
 *     run {
 *         check(slice.fromMutPtrRange(range) == listOf(1, 2, 3))
 *     }
 * }
 * ```
 *
 * [valid]: ptr#safety
 */
public fun <T> fromMutPtrRange(range: Range<MutPtr>): MutableList<T> {
    // SAFETY: the caller must uphold the safety contract for `fromMutPtrRange`.
    return rawMutSliceList(range.start, range.end.offsetFromUnsigned(range.start).toInt(), PrimitiveLayouts.U8)
}
