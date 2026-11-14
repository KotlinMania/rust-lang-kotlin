// port-lint: source vector.rs
package io.github.kotlinmania.rustlang.coresimd

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.intrinsics.simd.SimdAlign
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.coresimd.simd.MaskElement

//
// NOTE: Accessing the inner array directly in any way (e.g. by using the `.0` field syntax) or
// directly constructing an instance of the type (i.e. `val vector = Simd(array)`) should be
// avoided, as it will likely become illegal on `@repr(simd)` structs in the future. It also
// causes rustc to emit illegal LLVM IR in some cases.

/**
 * A SIMD vector with the shape of `[T; N]` but the operations of `T`.
 *
 * `Simd<T, N>` supports the operators (+, *, etc.) that `T` does in "elementwise" fashion.
 * These take the element at each index from the left-hand side and right-hand side,
 * perform the operation, then return the result in the same index in a vector of equal size.
 * However, `Simd` differs from normal iteration and normal arrays:
 * - `Simd<T, N>` executes `N` operations in a single step with no `break`s
 * - `Simd<T, N>` can have an alignment greater than `T`, for better mechanical sympathy
 *
 * By always imposing these constraints on `Simd`, it is easier to compile elementwise operations
 * into machine instructions that can themselves be executed in parallel.
 *
 * ```kotlin
 * val a: IntArray = intArrayOf(-2, 0, 2, 4)
 * val b = listOf(10, 9, 8, 7)
 * val sum = fromFn({ i -> a[i] + b[i] })
 * val prod = fromFn({ i -> a[i] * b[i] })
 * // `Simd<T, N>` implements `From<[T; N]>`
 * val (v, w) = Pair(Simd.from(a), Simd.from(b))
 * // Which means arrays implement `Into<Simd<T, N>>`.
 * check(v + w == sum.into())
 * check(v * w == prod.into())
 * ```
 *
 * `Simd` with integer elements treats operators as wrapping, as if `T` was [Wrapping.T].
 * Thus, `Simd` does not implement `wrappingAdd`, because that is the default behavior.
 * This means there is no warning on overflows, even in "debug" builds.
 * For most applications where `Simd` is appropriate, it is "not a bug" to wrap,
 * and even "debug builds" are unlikely to tolerate the loss of performance.
 * You may want to consider using explicitly checked arithmetic if such is required.
 * Division by zero on integers still causes a panic, so
 * you may want to consider using `Float` or `Double` if that is unacceptable.
 *
 * # Layout
 * `Simd<T, N>` has a layout similar to `[T; N]` (identical "shapes"), with a greater alignment.
 * `[T; N]` is aligned to `T`, but `Simd<T, N>` will have an alignment based on both `T` and `N`.
 * Thus it is sound to [transmute] `Simd<T, N>` to `[T; N]` and should optimize to "zero cost",
 * but the reverse transmutation may require a copy the compiler cannot simply elide.
 *
 * `N` cannot be 0 and may be at most 64. This limit may be increased in the future.
 *
 * # ABI "Features"
 * Due to Rust's safety guarantees, `Simd<T, N>` is currently passed and returned via memory,
 * not SIMD registers, except as an optimization. Using `@inline` on functions that accept
 * `Simd<T, N>` or return it is recommended, at the cost of code generation time, as
 * inlining SIMD-using functions can omit a large function prolog or epilog and thus
 * improve both speed and code size. The need for this may be corrected in the future.
 *
 * Using `@inline(always)` still requires additional care.
 *
 * # Safe SIMD with Unsafe Rust
 *
 * Operations with `Simd` are typically safe, but there are many reasons to want to combine SIMD with `unchecked` code.
 * Care must be taken to respect differences between `Simd` and other types it may be transformed into or derived from.
 * In particular, the layout of `Simd<T, N>` may be similar to `[T; N]`, and may allow some transmutations,
 * but references to `[T; N]` are not interchangeable with those to `Simd<T, N>`.
 * Thus, when using `unchecked` Rust to read and write `Simd<T, N>` through [raw pointers], it is a good idea to first try with
 * [readUnaligned] and [writeUnaligned]. This is because:
 * - [read] and [write] require full alignment (in this case, `Simd<T, N>`s alignment)
 * - `Simd<T, N>` is often read from or written to [`[T]`](slice) and other types aligned to `T`
 * - combining these actions violates the `unchecked` contract and explodes the program into
 *   a puff of **undefined behavior**
 * - the compiler can implicitly adjust layouts to make unaligned reads or writes fully aligned
 *   if it sees the optimization
 * - most contemporary processors with "aligned" and "unaligned" read and write instructions
 *   exhibit no performance difference if the "unaligned" variant is aligned at runtime
 *
 * Less obligations mean unaligned reads and writes are less likely to make the program unsound,
 * and may be just as fast as stricter alternatives.
 * When trying to guarantee alignment, [`asSimd`][asSimd] is an option for
 * converting `[T]` to `[Simd<T, N>]`, and allows soundly operating on an aligned SIMD body,
 * but it may cost more time when handling the scalar head and tail.
 * If these are not enough, it is most ideal to design data structures to be already aligned
 * to `alignOf<Simd<T, N>>()` before using `unchecked` Rust to read or write.
 * Other ways to compensate for these facts, like materializing `Simd` to or from an array first,
 * are handled by safe methods like [Simd.fromArray] and [Simd.fromSlice].
 *
 * [raw pointers]: pointer
 * [readUnaligned]: pointer.readUnaligned
 * [writeUnaligned]: pointer.writeUnaligned
 * [read]: pointer.read
 * [write]: pointer.write
 * [asSimd]: slice.asSimd
 */
public open class Simd<T>(
    public var value: List<T>,
) {
    public var field0: List<T>
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Simd<T>> =
            object : TypeLayout<Simd<T>> {
                override val alignBytes: Long = listOf(1L, minOf(typeLayoutT.alignBytes, 1L)).max()
                override val sizeBytes: Long = alignUpBytes(((N).toLong() * (typeLayoutT).strideBytes), alignBytes)
                override fun readNative(addr: Long): Simd<T> =
                    Simd(
                        (fixedListTypeLayout(typeLayoutT, (N).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Simd<T>) {
                    (fixedListTypeLayout(typeLayoutT, (N).toInt())).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Simd<T> =
                    Simd(
                        (fixedListTypeLayout(typeLayoutT, (N).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Simd<T>) {
                    (fixedListTypeLayout(typeLayoutT, (N).toInt())).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Number of elements in this vector.
 */
public val LEN: Int = N

/**
 * Returns the number of elements in this SIMD vector.
 *
 * # Examples
 *
 * ```kotlin
 * val v = splat(0)
 * check(v.len() == 4)
 * ```
 */
public fun <Mask, T> Simd<T, N>.len(n: Int): Int = Simd.LEN

/**
 * Constructs a new SIMD vector with all elements set to the given value.
 *
 * # Examples
 *
 * ```kotlin
 * val v = splat(8)
 * check(v.asArray() == listOf(8, 8, 8, 8))
 * ```
 */
public fun <Mask, T> Simd.Companion.splat(value: T, n: Int): Simd<T, N> {
    // SAFETY: T is a SimdElement, and the item type of Self.
    return Simd.simdSplat(value)
}

/**
 * Returns an array reference containing the entire SIMD vector.
 *
 * # Examples
 *
 * ```kotlin
 * val v: U64x4 = Simd.fromArray(listOf(0, 1, 2, 3))
 * check(v.asArray() == listOf(0, 1, 2, 3))
 * ```
 */
public fun <Mask, T> Simd<T, N>.asArray(n: Int, typeLayoutT: TypeLayout<T>): List<T> {
    // SAFETY: `Simd<T, N>` is just an overaligned `[T; N]` with
    // potential padding at the end, so pointer casting to a
    // `[T; N]` is safe.
    //
    // NOTE: This deliberately doesn't just use `self.0`, see the comment
    // on the struct definition for details.
    return (fixedListTypeLayout(typeLayoutT, (n).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
}

/**
 * Returns a mutable array reference containing the entire SIMD vector.
 */
public fun <Mask, T> Simd<T, N>.asMutArray(n: Int, typeLayoutT: TypeLayout<T>): List<T> {
    // SAFETY: `Simd<T, N>` is just an overaligned `[T; N]` with
    // potential padding at the end, so pointer casting to a
    // `[T; N]` is safe.
    //
    // NOTE: This deliberately doesn't just use `self.0`, see the comment
    // on the struct definition for details.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), fixedListTypeLayout(typeLayoutT, (n).toInt()))
}

/**
 * Loads a vector from an array of `T`.
 *
 * This function is necessary since `repr(simd)` has padding for non-power-of-2 vectors (at the time of writing).
 * With padding, `readUnaligned` will read past the end of an array of N elements.
 *
 * # Safety
 * Reading `ptr` must be safe, as if by `< * const[T; N]>.read`.
 */
public fun <Mask, T> Simd.Companion.load(ptr: ConstPtr, n: Int, typeLayoutT: TypeLayout<T>): Simd<T, N> {
    // There are potentially simpler ways to write this function, but this should result in
    // LLVM `load<N x T>`
    val tmp = MaybeUninit.uninit(ObjectSlotLayout<Simd<T, N>>())
    // SAFETY: `Simd<T, N>` always contains `N` elements of type `T`.  It may have padding
    // which does not need to be initialized.  The safety of reading `ptr` is ensured by the
    // caller.
    return run {
        copyNonoverlapping(ptr, tmp.asMutPtr().cast(), 1, typeLayoutT = fixedListTypeLayout(typeLayoutT, (n).toInt()))
        tmp.assumeInit()
    }
}

/**
 * Store a vector to an array of `T`.
 *
 * See `load` as to why this function is necessary.
 *
 * # Safety
 * Writing to `ptr` must be safe, as if by `< * [T; N]>.write`.
 */
public fun <Mask, T> Simd<T, N>.store(ptr: MutPtr, n: Int) {
    // There are potentially simpler ways to write this function, but this should result in
    // LLVM `store<N x T>`
    // Creating a temporary helps LLVM turn the memcpy into a store.
    val tmp = this
    // SAFETY: `Simd<T, N>` always contains `N` elements of type `T`.  The safety of writing
    // `ptr` is ensured by the caller.
    copyNonoverlapping(tmp.asArray(), ptr, 1)
}

/**
 * Converts an array to a SIMD vector.
 */
public fun <Mask, T> Simd.Companion.fromArray(array: List<T>, n: Int): Simd<T, N> {
    // SAFETY: `array` is safe to read.
    //
    // Note: We currently use a pointer load instead of `transmuteCopy` because `repr(simd)`
    // results in padding for non-power-of-2 vectors (so vectors are larger than arrays).
    //
    // NOTE: This deliberately doesn't just use `Self(array)`, see the comment
    // on the struct definition for details.
    return Simd.load(array)
}

/**
 * Converts a SIMD vector to an array.
 */
public fun <Mask, T> Simd<T, N>.toArray(n: Int): List<T> {
    val tmp = MaybeUninit.uninit(ObjectSlotLayout<List<T>>())
    // SAFETY: writing to `tmp` is safe and initializes it.
    //
    // Note: We currently use a pointer store instead of `transmuteCopy` because `repr(simd)`
    // results in padding for non-power-of-2 vectors (so vectors are larger than arrays).
    //
    // NOTE: This deliberately doesn't just use `self.0`, see the comment
    // on the struct definition for details.
    return run {
        this.store(tmp.asMutPtr())
        tmp.assumeInit()
    }
}

/**
 * Converts a slice to a SIMD vector containing `slice[.. N]`.
 *
 * # Panics
 *
 * Panics if the slice's length is less than the vector's `Simd.N`.
 * Use `loadOrDefault` for an alternative that does not panic.
 *
 * # Example
 *
 * ```kotlin
 * val source = mutableListOf(1, 2, 3, 4, 5, 6)
 * val v = fromSlice(source)
 * check(v.asArray() == listOf(1, 2, 3, 4))
 * ```
 */
public fun <Mask, T> Simd.Companion.fromSlice(slice: List<T>, n: Int): Simd<T, N> {
    check(slice.size >= Simd.LEN) { "slice length must be at least the number of elements" }
    // SAFETY: We just checked that the slice contains
    // at least `N` elements.
    return Simd.load(slice.asPtr())
}

/**
 * Writes a SIMD vector to the first `N` elements of a slice.
 *
 * # Panics
 *
 * Panics if the slice's length is less than the vector's `Simd.N`.
 *
 * # Example
 *
 * ```kotlin
 * var dest = mutableListOf(0, 6)
 * val v = fromArray(listOf(1, 2, 3, 4))
 * v.copyToSlice(dest)
 * check(dest == listOf(1, 2, 3, 4, 0, 0))
 * ```
 */
public fun <Mask, T> Simd<T, N>.copyToSlice(slice: MutableList<T>, n: Int) {
    check(slice.size >= Simd.LEN) { "slice length must be at least the number of elements" }
    // SAFETY: We just checked that the slice contains
    // at least `N` elements.
    this.store(slice.asMutPtr())
}

/**
 * Reads contiguous elements from `slice`. Elements are read so long as they're in-bounds for
 * the `slice`. Otherwise, the default value for the element type is returned.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11)
 * val result = Simd.loadOrDefault(vec)
 * check(result == Simd.fromArray(listOf(10, 11, 0, 0)))
 * ```
 */
public fun <Mask, T> Simd.Companion.loadOrDefault(slice: List<T>, n: Int, defaultTFn: () -> T): Simd<T, N> = Simd.loadOr(slice, defaultTFn())

/**
 * Reads contiguous elements from `slice`. Elements are read so long as they're in-bounds for
 * the `slice`. Otherwise, the corresponding value from `or` is passed through.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11)
 * val or = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val result = Simd.loadOr(vec, or)
 * check(result == Simd.fromArray(listOf(10, 11, -3, -2)))
 * ```
 */
public fun <Mask, T> Simd.Companion.loadOr(slice: List<T>, or: Simd<T, N>, n: Int): Simd<T, N> = Simd.loadSelect(slice, Mask.splat(true), or)

/**
 * Reads contiguous elements from `slice`. Each element is read from memory if its
 * corresponding element in `enable` is `true`.
 *
 * When the element is disabled or out of bounds for the slice, that memory location
 * is not accessed and the default value for the element type is returned.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11, 12)
 * val enable = Mask.fromArray(booleanArrayOf(false, true, true, true))
 * val result = Simd.loadSelectOrDefault(vec, enable)
 * check(result == Simd.fromArray(listOf(0, 11, 12, 0)))
 * ```
 */
public fun <Mask, T> Simd.Companion.loadSelectOrDefault(slice: List<T>, enable: Mask<Mask, N>, n: Int, defaultTFn: () -> T): Simd<T, N> = Simd.loadSelect(slice, enable, defaultTFn())

/**
 * Reads contiguous elements from `slice`. Each element is read from memory if its
 * corresponding element in `enable` is `true`.
 *
 * When the element is disabled or out of bounds for the slice, that memory location
 * is not accessed and the corresponding value from `or` is passed through.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val enable = Mask.fromArray(booleanArrayOf(true, true, false, true))
 * val or = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val result = Simd.loadSelect(vec, enable, or)
 * check(result == Simd.fromArray(listOf(10, 11, -3, 13)))
 * ```
 */
public fun <Mask, T> Simd.Companion.loadSelect(slice: List<T>, enable: Mask<Mask, N>, or: Simd<T, N>, n: Int): Simd<T, N> {
    var enable1 = enable
    enable1 = enable1 and maskUpTo(slice.size)
    // SAFETY: We performed the bounds check by updating the mask. &[T] is properly aligned to
    // the element.
    return Simd.loadSelectPtr(slice.asPtr(), enable1, or)
}

/**
 * Reads contiguous elements from `slice`. Each element is read from memory if its
 * corresponding element in `enable` is `true`.
 *
 * When the element is disabled, that memory location is not accessed and the corresponding
 * value from `or` is passed through.
 *
 * # Safety
 * Enabled loads must not exceed the length of `slice`.
 */
public fun <Mask, T> Simd.Companion.loadSelectUnchecked(slice: List<T>, enable: Mask<Mask, N>, or: Simd<T, N>, n: Int): Simd<T, N> {
    val ptr = slice.asPtr()
    // SAFETY: The safety of reading elements from `slice` is ensured by the caller.
    return Simd.loadSelectPtr(ptr, enable, or)
}

/**
 * Reads contiguous elements starting at `ptr`. Each element is read from memory if its
 * corresponding element in `enable` is `true`.
 *
 * When the element is disabled, that memory location is not accessed and the corresponding
 * value from `or` is passed through.
 *
 * # Safety
 * Enabled `ptr` elements must be safe to read as if by `core.ptr.read`.
 */
public fun <Mask, T> Simd.Companion.loadSelectPtr(ptr: ConstPtr, enable: Mask<Mask, N>, or: Simd<T, N>, n: Int): Simd<T, N> {
    // SAFETY: The safety of reading elements through `ptr` is ensured by the caller.
    return Simd.simdMaskedLoad(enable.toSimd(), ptr, or, SimdAlign.Element)
}

/**
 * Reads from potentially discontiguous indices in `slice` to construct a SIMD vector.
 * If an index is out-of-bounds, the element is instead selected from the `or` vector.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 5))
 * // Note the index that is out-of-bounds
 * val alt = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val result = Simd.gatherOr(vec, idxs, alt)
 * check(result == Simd.fromArray(listOf(-5, 13, 10, 15)))
 * ```
 */
public fun <Mask, T> Simd.Companion.gatherOr(slice: List<T>, idxs: Simd<Int, N>, or: Simd<T, N>, n: Int): Simd<T, N> = Simd.gatherSelect(slice, Mask.splat(true), idxs, or)

/**
 * Reads from indices in `slice` to construct a SIMD vector.
 * If an index is out-of-bounds, the element is set to the default given by `T: Default`.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 5))
 * // Note the index that is out-of-bounds
 * val result = Simd.gatherOrDefault(vec, idxs)
 * check(result == Simd.fromArray(listOf(0, 13, 10, 15)))
 * ```
 */
public fun <Mask, T> Simd.Companion.gatherOrDefault(slice: List<T>, idxs: Simd<Int, N>, n: Int, defaultTFn: () -> T): Simd<T, N> = Simd.gatherOr(slice, idxs, Simd.splat(defaultTFn()))

/**
 * Reads from indices in `slice` to construct a SIMD vector.
 * The mask `enable`s all `true` indices and disables all `false` indices.
 * If an index is disabled or is out-of-bounds, the element is selected from the `or` vector.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 5))
 * // Includes an out-of-bounds index
 * val alt = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val enable = Mask.fromArray(booleanArrayOf(true, true, true, false))
 * // Includes a masked element
 * val result = Simd.gatherSelect(vec, enable, idxs, alt)
 * check(result == Simd.fromArray(listOf(-5, 13, 10, -2)))
 * ```
 */
public fun <Mask, T> Simd.Companion.gatherSelect(slice: List<T>, enable: Mask<Int, N>, idxs: Simd<Int, N>, or: Simd<T, N>, n: Int): Simd<T, N> {
    val enable1: Mask<Int> = enable and idxs.simdLt(Simd.splat(slice.size))
    // Safety: We have masked-off out-of-bounds indices.
    return Simd.gatherSelectUnchecked(slice, enable1, idxs, or)
}

// even without panics, this helps for Miri backtraces

/**
 * Reads from indices in `slice` to construct a SIMD vector.
 * The mask `enable`s all `true` indices and disables all `false` indices.
 * If an index is disabled, the element is selected from the `or` vector.
 *
 * # Safety
 *
 * Calling this function with an `enable`d out-of-bounds index is *[undefined behavior]*
 * even if the resulting value is not used.
 *
 * # Examples
 * ```kotlin
 * val vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 5))
 * // Includes an out-of-bounds index
 * val alt = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val enable = Mask.fromArray(booleanArrayOf(true, true, true, false))
 * // Includes a masked element
 * // If this mask was used to gather, it would be unsound. Let's fix that.
 * val enable1 = enable and idxs.simdLt(Simd.splat(vec.size))
 * // The out-of-bounds index has been masked, so it's safe to gather now.
 * val result = Simd.gatherSelectUnchecked(vec, enable1, idxs, alt)
 * check(result == Simd.fromArray(listOf(-5, 13, 10, -2)))
 * ```
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 */
public fun <Mask, T> Simd.Companion.gatherSelectUnchecked(slice: List<T>, enable: Mask<Int, N>, idxs: Simd<Int, N>, or: Simd<T, N>, n: Int): Simd<T, N> {
    val basePtr = Simd.splat(slice.asPtr())
    // Ferris forgive me, I have done pointer arithmetic here.
    val ptrs = basePtr.wrappingAdd(idxs)
    // Safety: The caller is responsible for determining the indices are okay to read
    return Simd.gatherSelectPtr(ptrs, enable, or)
}

// even without panics, this helps for Miri backtraces

/**
 * Reads elementwise from pointers into a SIMD vector.
 *
 * # Safety
 *
 * Each read must satisfy the same conditions as [core.ptr.read].
 *
 * # Example
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(6, 2, 4, 9))
 * val offsets = Simd.fromArray(listOf(1, 0, 0, 3))
 * val source = Simd.splat(localStorageL3C0.elementConstPtr(0)).wrappingAdd(offsets)
 * val gathered = Simd.gatherPtr(source)
 * check(gathered == Simd.fromArray(listOf(2, 6, 6, 9)))
 * ```
 */
public fun <Mask, T> Simd.Companion.gatherPtr(source: Simd<ConstPtr, N>, n: Int): Simd<T, N> {
    // Note: add an intrinsic that doesn't use a passthru vector, and remove the T: Default bound
    // Safety: The caller is responsible for upholding all invariants
    return Simd.gatherSelectPtr(source, Mask.splat(true), Simd.default())
}

// even without panics, this helps for Miri backtraces

/**
 * Conditionally read elementwise from pointers into a SIMD vector.
 * The mask `enable`s all `true` pointers and disables all `false` pointers.
 * If a pointer is disabled, the element is selected from the `or` vector,
 * and no read is performed.
 *
 * # Safety
 *
 * Enabled elements must satisfy the same conditions as [core.ptr.read].
 *
 * # Example
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(6, 2, 4, 9))
 * val enable = Mask.fromArray(booleanArrayOf(true, true, false, true))
 * val offsets = Simd.fromArray(listOf(1, 0, 0, 3))
 * val source = Simd.splat(localStorageL3C0.elementConstPtr(0)).wrappingAdd(offsets)
 * val gathered = Simd.gatherSelectPtr(source, enable, Simd.splat(0))
 * check(gathered == Simd.fromArray(listOf(2, 6, 0, 9)))
 * ```
 */
public fun <Mask, T> Simd.Companion.gatherSelectPtr(source: Simd<ConstPtr, N>, enable: Mask<Int, N>, or: Simd<T, N>, n: Int): Simd<T, N> {
    // Safety: The caller is responsible for upholding all invariants
    return Simd.simdGather(or, source, enable.toSimd())
}

/**
 * Conditionally write contiguous elements to `slice`. The `enable` mask controls
 * which elements are written, as long as they're in-bounds of the `slice`.
 * If the element is disabled or out of bounds, no memory access to that location
 * is made.
 *
 * # Examples
 * ```kotlin
 * var arr = IntArray(4) { 0 }
 * val write = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val enable = Mask.fromArray(booleanArrayOf(false, true, true, true))
 * run { val subSlice0 = arr.sliceArray(0 until 3); val subCall0 = write.storeSelect(subSlice0, enable); subSlice0.copyInto(arr, destinationOffset = 0); subCall0 }
 * check(arr == intArrayOf(0, -4, -3, 0))
 * ```
 */
public fun <Mask, T> Simd<T, N>.storeSelect(slice: MutableList<T>, enable: Mask<Mask, N>, n: Int) {
    var enable1 = enable
    enable1 = enable1 and maskUpTo(slice.size)
    // SAFETY: We performed the bounds check by updating the mask. &[T] is properly aligned to
    // the element.
    this.storeSelectPtr(slice.asMutPtr(), enable1)
}

/**
 * Conditionally write contiguous elements to `slice`. The `enable` mask controls
 * which elements are written.
 *
 * # Safety
 *
 * Every enabled element must be in bounds for the `slice`.
 *
 * # Examples
 * ```kotlin
 * var arr = IntArray(4) { 0 }
 * val write = Simd.fromArray(listOf(-5, -4, -3, -2))
 * val enable = Mask.fromArray(booleanArrayOf(false, true, true, true))
 * write.storeSelectUnchecked(arr, enable)
 * check(arr == intArrayOf(0, -4, -3, -2))
 * ```
 */
public fun <Mask, T> Simd<T, N>.storeSelectUnchecked(slice: MutableList<T>, enable: Mask<Mask, N>, n: Int) {
    val ptr = slice.asMutPtr()
    // SAFETY: The safety of writing elements in `slice` is ensured by the caller.
    this.storeSelectPtr(ptr, enable)
}

/**
 * Conditionally write contiguous elements starting from `ptr`.
 * The `enable` mask controls which elements are written.
 * When disabled, the memory location corresponding to that element is not accessed.
 *
 * # Safety
 *
 * Memory addresses for element are calculated [pointer.wrappingOffset] and
 * each enabled element must satisfy the same conditions as [core.ptr.write].
 */
public fun <Mask, T> Simd<T, N>.storeSelectPtr(ptr: MutPtr, enable: Mask<Mask, N>, n: Int) {
    // SAFETY: The safety of writing elements through `ptr` is ensured by the caller.
    Simd.simdMaskedStore(enable.toSimd(), ptr, this, SimdAlign.Element)
}

/**
 * Writes the values in a SIMD vector to potentially discontiguous indices in `slice`.
 * If an index is out-of-bounds, the write is suppressed without panicking.
 * If two elements in the scattered vector would write to the same index
 * only the last element is guaranteed to actually be written.
 *
 * # Examples
 * ```kotlin
 * var vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 0))
 * // Note the duplicate index.
 * val vals = Simd.fromArray(listOf(-27, 82, -41, 124))
 * vals.scatter(vec, idxs)
 * // two logical writes means the last wins.
 * check(vec == mutableListOf(124, 11, 12, 82, 14, 15, 16, 17, 18))
 * ```
 */
public fun <Mask, T> Simd<T, N>.scatter(slice: MutableList<T>, idxs: Simd<Int, N>, n: Int) {
    this.scatterSelect(slice, Mask.splat(true), idxs)
}

/**
 * Writes values from a SIMD vector to multiple potentially discontiguous indices in `slice`.
 * The mask `enable`s all `true` indices and disables all `false` indices.
 * If an enabled index is out-of-bounds, the write is suppressed without panicking.
 * If two enabled elements in the scattered vector would write to the same index,
 * only the last element is guaranteed to actually be written.
 *
 * # Examples
 * ```kotlin
 * var vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 0))
 * // Includes an out-of-bounds index
 * val vals = Simd.fromArray(listOf(-27, 82, -41, 124))
 * val enable = Mask.fromArray(booleanArrayOf(true, true, true, false))
 * // Includes a masked element
 * vals.scatterSelect(vec, enable, idxs)
 * // The last write is masked, thus omitted.
 * check(vec == mutableListOf(-41, 11, 12, 82, 14, 15, 16, 17, 18))
 * ```
 */
public fun <Mask, T> Simd<T, N>.scatterSelect(slice: MutableList<T>, enable: Mask<Int, N>, idxs: Simd<Int, N>, n: Int) {
    val enable1: Mask<Int> = enable and idxs.simdLt(Simd.splat(slice.size))
    // Safety: We have masked-off out-of-bounds indices.
    this.scatterSelectUnchecked(slice, enable1, idxs)
}

// even without panics, this helps for Miri backtraces

/**
 * Writes values from a SIMD vector to multiple potentially discontiguous indices in `slice`.
 * The mask `enable`s all `true` indices and disables all `false` indices.
 * If two enabled elements in the scattered vector would write to the same index,
 * only the last element is guaranteed to actually be written.
 *
 * # Safety
 *
 * Calling this function with an enabled out-of-bounds index is *[undefined behavior]*,
 * and may lead to memory corruption.
 *
 * # Examples
 * ```kotlin
 * var vec: List<Int> = mutableListOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
 * val idxs = Simd.fromArray(listOf(9, 3, 0, 0))
 * val vals = Simd.fromArray(listOf(-27, 82, -41, 124))
 * val enable = Mask.fromArray(booleanArrayOf(true, true, true, false))
 * // Masks the final index
 * // If this mask was used to scatter, it would be unsound. Let's fix that.
 * val enable1 = enable and idxs.simdLt(Simd.splat(vec.size))
 * // We have masked the OOB index, so it's safe to scatter now.
 * run {
 *     vals.scatterSelectUnchecked(vec, enable1, idxs)
 * }
 * // The second write to index 0 was masked, thus omitted.
 * check(vec == mutableListOf(-41, 11, 12, 82, 14, 15, 16, 17, 18))
 * ```
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 */
public fun <Mask, T> Simd<T, N>.scatterSelectUnchecked(slice: MutableList<T>, enable: Mask<Int, N>, idxs: Simd<Int, N>, n: Int) {
    // Safety: This block works with *mut T derived from a [T],
    // which means it is delicate in Rust's borrowing model, circa 2021:
    // a [T] asserts uniqueness, so deriving &a [T] invalidates live *mut Ts!
    // Even though this block is largely safe methods, it must be exactly this way
    // to prevent invalidating the raw ptrs while they're live.
    // Thus, entering this block requires all values to use being already ready:
    // 0. idxs we want to write to, which are used to construct the mask.
    // 1. enable, which depends on an initial &a [T] and the idxs.
    // 2. actual values to scatter (self).
    // 3. [T] which will become our base ptr.
    run {
        // Now Entering ☢️ *mut T Zone
        val basePtr = Simd.splat(slice.asMutPtr())
        // Ferris forgive me, I have done pointer arithmetic here.
        val ptrs = basePtr.wrappingAdd(idxs)
        // The ptrs have been bounds-masked to prevent memory-unchecked writes insha'allah
        this.scatterSelectPtr(ptrs, enable)
        // Cleared ☢️ *mut T Zone
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Writes pointers elementwise into a SIMD vector.
 *
 * # Safety
 *
 * Each write must satisfy the same conditions as [core.ptr.write].
 *
 * # Example
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
 * var values by localStorageL3C0
 * val offset = Simd.fromArray(listOf(3, 2, 1, 0))
 * val ptrs = Simd.splat(localStorageL3C0.elementMutPtr(0)).wrappingAdd(offset)
 * run {
 *     Simd.fromArray(listOf(6, 3, 5, 7)).scatterPtr(ptrs)
 * }
 * check(values == intArrayOf(7, 5, 3, 6))
 * ```
 */
public fun <Mask, T> Simd<T, N>.scatterPtr(dest: Simd<MutPtr, N>, n: Int) {
    // Safety: The caller is responsible for upholding all invariants
    this.scatterSelectPtr(dest, Mask.splat(true))
}

// even without panics, this helps for Miri backtraces

/**
 * Conditionally write pointers elementwise into a SIMD vector.
 * The mask `enable`s all `true` pointers and disables all `false` pointers.
 * If a pointer is disabled, the write to its pointee is skipped.
 *
 * # Safety
 *
 * Enabled pointers must satisfy the same conditions as [core.ptr.write].
 *
 * # Example
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
 * var values by localStorageL3C0
 * val offset = Simd.fromArray(listOf(3, 2, 1, 0))
 * val ptrs = Simd.splat(localStorageL3C0.elementMutPtr(0)).wrappingAdd(offset)
 * val enable = Mask.fromArray(booleanArrayOf(true, true, false, false))
 * run {
 *     Simd.fromArray(listOf(6, 3, 5, 7)).scatterSelectPtr(ptrs, enable)
 * }
 * check(values == intArrayOf(0, 0, 3, 6))
 * ```
 */
public fun <Mask, T> Simd<T, N>.scatterSelectPtr(dest: Simd<MutPtr, N>, enable: Mask<Int, N>, n: Int) {
    // Safety: The caller is responsible for upholding all invariants
    Simd.simdScatter(this, dest, enable.toSimd())
}

public fun <Mask, T> Simd<T, N>.clone(n: Int): Simd<T, N> = Simd(this.value)

public fun <Mask, T> defaultSimdTN(n: Int, defaultTFn: () -> T): Simd<T, N> = Simd.splat(defaultTFn())

public fun <Mask, T> Simd<T, N>.eq(other: Simd<T, N>, n: Int): Boolean {
    // Safety: All SIMD vectors are SimdPartialEq, and the comparison produces a valid mask.
    val mask =
        run {
            val tfvec: Simd<Mask> = Simd.simdEq(this, other)
            Mask.fromSimdUnchecked(tfvec)
        }
    // Two vectors are equal if all elements are equal when compared elementwise
    return mask.all()
}

public fun <Mask, T> Simd<T, N>.ne(other: Simd<T, N>, n: Int): Boolean {
    // Safety: All SIMD vectors are SimdPartialEq, and the comparison produces a valid mask.
    val mask =
        run {
            val tfvec: Simd<Mask> = Simd.simdNe(this, other)
            Mask.fromSimdUnchecked(tfvec)
        }
    // Two vectors are non-equal if any elements are non-equal when compared elementwise
    return mask.any()
}

/**
 * Lexicographic order. For the SIMD elementwise minimum and maximum, use simdMin and simdMax instead.
 */
public fun <Mask, T> Simd<T, N>.partialCmp(other: Simd<T, N>, n: Int): Ordering? {
    // Note use SIMD equality
    return this.toArray().partialCmp(other.asRef())
}

/**
 * Lexicographic order. For the SIMD elementwise minimum and maximum, use simdMin and simdMax instead.
 */
public fun <Mask, T> Simd<T, N>.cmp(other: Simd<T, N>, n: Int): Ordering {
    // Note use SIMD equality
    return this.toArray().cmp(other.asRef())
}

public fun <Mask, T, H> Simd<T, N>.hash(state: H, n: Int) {
    this.asArray().hash(state)
}

// array references
public fun <Mask, T> Simd<T, N>.asRef(n: Int): List<T> = this.asArray()

public fun <Mask, T> Simd<T, N>.asMut(n: Int): List<T> = this.asMutArray()

// slice references
public fun <Mask, T> Simd<T, N>.asRefTSlice(n: Int): List<T> = this.asArray()

public fun <Mask, T> Simd<T, N>.asMutTSlice(n: Int): MutableList<T> = this.asMutArray()

// vector/array conversion
public fun <Mask, T> fromSimdTN(array: List<T>, n: Int): Simd<T, N> = Simd.fromArray(array)

public fun <Mask, T> fromTArrayn(vector: Simd<T, N>, n: Int): List<T> = vector.toArray()

public fun <Mask, T> tryFromSimdTN(slice: List<T>, n: Int): Result<Simd<T, N>, TryFromSliceError> {
    return Result.Ok(Simd.fromArray(when (val tryResult = slice.tryInto()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
}

public fun <Mask, T> tryFromSimdTN(slice: MutableList<T>, n: Int): Result<Simd<T, N>, TryFromSliceError> {
    return Result.Ok(Simd.fromArray(when (val tryResult = slice.tryInto()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
}

/**
 * Marker trait for types that may be used as SIMD vector elements.
 *
 * # Safety
 * This trait, when implemented, asserts the compiler can monomorphize
 * `@repr(simd)` structs with the marked type as an element.
 * Strictly, it is valid to impl if the vector will not be miscompiled.
 * Practically, it is user-unfriendly to impl it if the vector won't compile,
 * even when no soundness guarantees are broken by allowing the user to try.
 */
public interface SimdElement<out Mask>

// Safety: u8 is a valid SIMD element type, and is supported by this API

// Safety: u16 is a valid SIMD element type, and is supported by this API

// Safety: u32 is a valid SIMD element type, and is supported by this API

// Safety: u64 is a valid SIMD element type, and is supported by this API

// Safety: Int is a valid SIMD element type, and is supported by this API

// Safety: i8 is a valid SIMD element type, and is supported by this API

// Safety: i16 is a valid SIMD element type, and is supported by this API

// Safety: i32 is a valid SIMD element type, and is supported by this API

// Safety: i64 is a valid SIMD element type, and is supported by this API

// Safety: isize is a valid SIMD element type, and is supported by this API

// Safety: f16 is a valid SIMD element type, and is supported by this API

// Safety: f32 is a valid SIMD element type, and is supported by this API

// Safety: f64 is a valid SIMD element type, and is supported by this API

// Safety: (thin) const pointers are valid SIMD element types, and are supported by this API
//
// Fat pointers may be supported in the future.

// Safety: (thin) mut pointers are valid SIMD element types, and are supported by this API
//
// Fat pointers may be supported in the future.

public fun laneIndices(n: Int): Simd<Int> {
    val index = MutableList(n) { 0 }
    for (i in 0 until n) {
        index[i] = i
    }
    return Simd.fromArray(index)
}

public fun <M> maskUpTo(len: Int, n: Int, maxUnsignedMFn: () -> ULong): Mask<M> where M : MaskElement<M> {
    val index = laneIndices(n)
    val maxValue: ULong = maxUnsignedMFn()
    if (n < UByte.MAX_VALUE.toInt() && (maxValue.toUByte()).toULong() == maxValue) {
        return index.cast().simdLt(Simd.splat(len.min(n).toUByte())).cast()
    }
    if (n < UShort.MAX_VALUE.toInt() && (maxValue.toUShort()).toULong() == maxValue) {
        return index.cast().simdLt(Simd.splat(len.min(n).toUShort())).cast()
    }
    if (n < UInt.MAX_VALUE.toInt() && (maxValue.toUInt()).toULong() == maxValue) {
        return index.cast().simdLt(Simd.splat(len.min(n).toUInt())).cast()
    }
    if (n < ULong.MAX_VALUE.toInt() && maxValue == maxValue) {
        return index.cast().simdLt(Simd.splat(len.min(n).toULong())).cast()
    }
    return index.simdLt(Simd.splat(len)).cast()
}
