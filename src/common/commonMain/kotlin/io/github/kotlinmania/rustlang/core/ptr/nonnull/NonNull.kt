// port-lint: source ptr/non_null.rs
package io.github.kotlinmania.rustlang.core.ptr.nonnull

import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.intrinsics.alignOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dangling
import io.github.kotlinmania.rustlang.core.ptr.dropGlue
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.fromMut
import io.github.kotlinmania.rustlang.core.ptr.fromRef
import io.github.kotlinmania.rustlang.core.ptr.metadata.Thin
import io.github.kotlinmania.rustlang.core.ptr.metadata.fromRawPartsMut
import io.github.kotlinmania.rustlang.core.ptr.metadata.metadata
import io.github.kotlinmania.rustlang.core.ptr.mutptr.addr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.alignOffset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asUninitSlice
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asUninitSliceMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteOffset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteOffsetFrom
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteOffsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteSub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castConst
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castInit
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castSlice
import io.github.kotlinmania.rustlang.core.ptr.mutptr.exposeProvenance
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isAligned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isAlignedTo
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offsetFrom
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.readVolatile
import io.github.kotlinmania.rustlang.core.ptr.mutptr.replace
import io.github.kotlinmania.rustlang.core.ptr.mutptr.swap
import io.github.kotlinmania.rustlang.core.ptr.mutptr.withAddr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeVolatile
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.sliceFromRawParts
import io.github.kotlinmania.rustlang.core.ptr.withExposedProvenanceMut
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenance
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeUnaligned
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex
import io.github.kotlinmania.rustlang.core.slice.raw.fromRawParts

/**
 * `* T` but non-zero and [covariant].
 *
 * This is often the correct thing to use when building data structures using
 * raw pointers, but is ultimately more dangerous to use because of its additional
 * properties. If you're not sure if you should use `NonNull<T>`, just use `* T`!
 *
 * Unlike `* T`, the pointer must always be non-null, even if the pointer
 * is never dereferenced. This is so that enums may use this forbidden value
 * as a discriminant -- `Option<NonNull<T>>` has the same size as `* T`.
 * However the pointer may still dangle if it isn't dereferenced.
 *
 * Unlike `* T`, `NonNull<T>` is covariant over `T`. This is usually the correct
 * choice for most data structures and safe abstractions, such as `Holder`, `Rc`, `Arc`, `List`,
 * and `LinkedList`.
 *
 * In rare cases, if your type exposes a way to mutate the value of `T` through a `NonNull<T>`,
 * and you need to prevent unsoundness from variance (for example, if `T` could be a reference
 * with a shorter scope), you should add a field to make your type invariant, such as
 * `PhantomData<Cell<T>>` or `PhantomData<T>`.
 *
 * Example of a type that must be invariant:
 * ```kotlin
 * import std.cell.Cell
 *
 * open class Invariant<T>(public var ptr: NonNull, public var invariant: Unit)
 * ```
 *
 * Notice that `NonNull<T>` has a `From` instance for `T`. However, this does
 * not change the fact that mutating through a (pointer derived from a) shared
 * reference is undefined behavior unless the mutation happens inside an
 * [UnsafeCell.T]. The same goes for creating a mutable reference from a shared
 * reference. When using this `From` instance without an `UnsafeCell<T>`,
 * it is your responsibility to ensure that `asMut` is never called, and `asPtr`
 * is never used for mutation.
 *
 * # Layout
 *
 * `NonNull<T>` is guaranteed to have the same layout and bit validity as `* T`
 * with the exception that a null pointer is invalid.
 * `Option<NonNull<T>>` is guaranteed to be ABI-compatible with `* T`, including in
 * FFI.
 *
 * Thanks to the [null pointer optimization],
 * `NonNull<T>` and `Option<NonNull<T>>`
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.ptr.NonNull
 *
 * check(sizeOf<NonNull>(typeLayoutT = UnresolvedTypeLayout<NonNull>()) == sizeOf<NonNull?>(typeLayoutT = UnresolvedTypeLayout<NonNull?>()))
 * check(alignOf<NonNull>(typeLayoutT = UnresolvedTypeLayout<NonNull>()) == alignOf<NonNull?>(typeLayoutT = UnresolvedTypeLayout<NonNull?>()))
 * check(sizeOf<NonNull>(typeLayoutT = UnresolvedTypeLayout<NonNull>()) == sizeOf<NonNull?>(typeLayoutT = UnresolvedTypeLayout<NonNull?>()))
 * check(alignOf<NonNull>(typeLayoutT = UnresolvedTypeLayout<NonNull>()) == alignOf<NonNull?>(typeLayoutT = UnresolvedTypeLayout<NonNull?>()))
 * ```
 *
 * [covariant]: https://doc.rust-lang.org/reference/subtyping.html
 */
public open class NonNull(
    public var pointer: ConstPtr,
) : TrivialClone<NonNull> {
    public val rawAddress: Long
        get() = pointer.rawAddress

    public val rawMeta: Long
        get() = pointer.rawMeta

    // N.B., this impl is unnecessary, but should provide better error messages.

    /**
     * `NonNull` pointers are not `Send` because the data they reference may be aliased.
     */

    // N.B., this impl is unnecessary, but should provide better error messages.

    /**
     * `NonNull` pointers are not `Sync` because the data they reference may be aliased.
     */

    /**
     * Returns a shared references to the value. In contrast to [asRef], this does not require
     * that the value has to be initialized.
     *
     * For the mutable counterpart see [asUninitMut].
     *
     * [asRef]: NonNull.asRef
     * [asUninitMut]: NonNull.asUninitMut
     *
     * # Safety
     *
     * When calling this method, you have to ensure that
     * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
     * Note that because the created reference is to `MaybeUninit<T>`, the
     * source pointer can point to uninitialized memory.
     */
    public open fun asUninitRef(): MaybeUninit<T> {
        // SAFETY: the caller must guarantee that `self` meets all the
        // requirements for a reference.
        return this.cast().asPtr()
    }

    /**
     * Returns a unique references to the value. In contrast to [asMut], this does not require
     * that the value has to be initialized.
     *
     * For the shared counterpart see [asUninitRef].
     *
     * [asMut]: NonNull.asMut
     * [asUninitRef]: NonNull.asUninitRef
     *
     * # Safety
     *
     * When calling this method, you have to ensure that
     * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
     * Note that because the created reference is to `MaybeUninit<T>`, the
     * source pointer can point to uninitialized memory.
     */
    public open fun asUninitMut(): MaybeUninit<T> {
        // SAFETY: the caller must guarantee that `self` meets all the
        // requirements for a reference.
        return this.cast().asPtr()
    }

    /**
     * Casts from a pointer-to-`T` to a pointer-to-`[T; N]`.
     */
    public open fun castArray(n: Int): NonNull = this.cast()

    /**
     * Decompose a (possibly wide) pointer into its data pointer and metadata components.
     *
     * The pointer can be later reconstructed with [NonNull.fromRawParts].
     */
    public open fun toRawParts(): Pair<NonNull, Metadata> = Pair(this.cast(), metadata(this.asPtr()))

    /**
     * Gets the "address" portion of the pointer.
     *
     * For more details, see the equivalent method on a raw pointer, [pointer.addr].
     *
     * This is a [Strict Provenance][ptr#strict-provenance] API.
     */
    public open fun addr(): NonZero<Int, NonZeroUsizeInner> {
        // SAFETY: The pointer is guaranteed by the type to be non-null,
        // meaning that the address will be non-zero.
        return NonZero.newUnchecked<NonZeroUsizeInner, Int>(this.asPtr().addr())
    }

    /**
     * Exposes the ["provenance"][ptr#provenance] part of the pointer for future use in
     * [`withExposedProvenance`][NonNull.withExposedProvenance] and returns the "address" portion.
     *
     * For more details, see the equivalent method on a raw pointer, [pointer.exposeProvenance].
     *
     * This is an [Exposed Provenance][ptr#exposed-provenance] API.
     */
    public open fun exposeProvenance(): NonZero<Int, NonZeroUsizeInner> {
        // SAFETY: The pointer is guaranteed by the type to be non-null,
        // meaning that the address will be non-zero.
        return NonZero.newUnchecked<NonZeroUsizeInner, Int>(this.asPtr().exposeProvenance())
    }

    /**
     * Creates a new pointer with the given address and the [provenance][ptr#provenance] of
     * `self`.
     *
     * For more details, see the equivalent method on a raw pointer, [pointer.withAddr].
     *
     * This is a [Strict Provenance][ptr#strict-provenance] API.
     */
    public open fun withAddr(addr: NonZero<Int, NonZeroUsizeInner>): NonNull {
        // SAFETY: The result of `ptr.from.withAddr` is non-null because `addr` is guaranteed to be non-zero.
        return NonNull.newUnchecked(mutPtrFromRustCast(this.asPtr().withAddr(addr.get())))
    }

    /**
     * Creates a new pointer by mapping `self`s address to a new one, preserving the
     * [provenance][ptr#provenance] of `self`.
     *
     * For more details, see the equivalent method on a raw pointer, [pointer.mapAddr].
     *
     * This is a [Strict Provenance][ptr#strict-provenance] API.
     */
    public open fun mapAddr(f: (NonZero<Int, NonZeroUsizeInner>) -> NonZero<Int, NonZeroUsizeInner>): NonNull = this.withAddr(f(this.addr()))

    /**
     * Acquires the underlying `*` pointer.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import std.ptr.NonNull
     *
     * val localStorageL5C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
     * val ptr = NonNull.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
     * val xValue = ptr.asPtr()
     * check(xValue == 0)
     * run {
     *     ptr.asPtr() += 2
     * }
     * val xValue1 = ptr.asPtr()
     * check(xValue1 == 2)
     * ```
     */
    public open fun asPtr(): MutPtr {
        // This is a reinterpret for the same reasons as `NonZero.get`.
        // SAFETY: `NonNull` is `transparent` over a `* const T`, and `* const T`
        // and `* T` have the same layout, so transitively we can reinterpret
        // our `NonNull` to a `* T` directly.
        return transmuteWithLayouts<NonNull, MutPtr>(this, NonNull.typeLayout(), PrimitiveLayouts.mutPtrLayout(64L))
    }

    /**
     * Returns a shared reference to the value. If the value may be uninitialized, [asUninitRef]
     * must be used instead.
     *
     * For the mutable counterpart see [asMut].
     *
     * [asUninitRef]: NonNull.asUninitRef
     * [asMut]: NonNull.asMut
     *
     * # Safety
     *
     * When calling this method, you have to ensure that
     * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import std.ptr.NonNull
     *
     * val localStorageL5C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
     * val ptr = NonNull.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
     * val refX = ptr.asRef()
     * println("$refX")
     * ```
     */
    public open fun asRef(): T {
        // SAFETY: the caller must guarantee that `self` meets all the
        // requirements for a reference.
        // `castConst` avoids a mutable raw pointer deref.
        return this.asPtr().castConst()
    }

    /**
     * Returns a unique reference to the value. If the value may be uninitialized, [asUninitMut]
     * must be used instead.
     *
     * For the shared counterpart see [asRef].
     *
     * [asUninitMut]: NonNull.asUninitMut
     * [asRef]: NonNull.asRef
     *
     * # Safety
     *
     * When calling this method, you have to ensure that
     * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import std.ptr.NonNull
     *
     * val localStorageL5C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
     * var ptr = NonNull.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
     * val xRef = ptr.asMut()
     * check(xRef == 0)
     * xRef += 2
     * check(xRef == 2)
     * ```
     */
    public open fun asMut(): T {
        // SAFETY: the caller must guarantee that `self` meets all the
        // requirements for a mutable reference.
        return this.asPtr()
    }

    /**
     * Casts to a pointer of another type.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     * import std.ptr.NonNull
     *
     * val localStorageL5C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
     * val ptr = NonNull.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
     * val castedPtr = ptr.cast()
     * val rawPtr: MutPtr = castedPtr.asPtr()
     * ```
     */
    public open fun <U> cast(): NonNull {
        // SAFETY: `self` is a `NonNull` pointer which is necessarily non-null
        return transmuteWithLayouts<MutPtr, NonNull>(mutPtrFromRustCast(this.asPtr()), PrimitiveLayouts.mutPtrLayout(64L), NonNull.typeLayout<U>())
    }

    /**
     * Try to cast to a pointer of another type by checking alignment.
     *
     * If the pointer is properly aligned to the target type, it will be
     * cast to the target type. Otherwise, `None` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import std.ptr.NonNull
     *
     * val localStorageL5C0 = AddressableLocal(0uL, PrimitiveLayouts.U64)
     * val aligned = NonNull.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
     * val unaligned = aligned.byteAdd(1)
     * check(aligned.tryCastAligned<UInt>() != null)
     * check(unaligned.tryCastAligned<UInt>() == null)
     * ```
     */
    public open fun <U> tryCastAligned(typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): NonNull? =
        if (this.isAlignedTo(alignOf<U>(typeLayoutT = typeLayoutU))) {
            this.cast()
        } else {
            null
        }

    // even without panics, this helps for Miri backtraces

    /**
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
     * import std.ptr.NonNull
     *
     * val localStorageL5C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
     * val ptr: NonNull = NonNull.new(localStorageL5C0.elementMutPtr(0)).unwrap()
     * run {
     *     println("${ptr.offset(1).read()}")
     *     println("${ptr.offset(2).read()}")
     * }
     * ```
     */
    public open fun offset(count: Int): NonNull {
        // SAFETY: the caller must uphold the safety contract for `offset`.
        // Additionally safety contract of `offset` guarantees that the resulting pointer is
        // pointing to an allocation, there can't be an allocation at null, thus it's safe to
        // construct `NonNull`.
        return transmuteWithLayouts<Ptr, NonNull>(ConstPtr(this.asPtr().rawAddress + (count).toLong() * 1L), UnresolvedTypeLayout<Ptr>(), NonNull.typeLayout())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the offset from a pointer in bytes.
     *
     * `count` is in units of **bytes**.
     *
     * This is purely a convenience for casting to a `UByte` pointer and
     * using [offset][pointer.offset] on it.
     *
     * For non-`Sized` pointees this operation changes only the data pointer,
     * leaving the metadata untouched.
     *
     * # Safety
     *
     * See [offset][pointer.offset] for documentation and safety requirements.
     */
    public open fun byteOffset(count: Int): NonNull {
        // SAFETY: the caller must uphold the safety contract for `offset` and `byteOffset` has
        // the same safety contract.
        // Additionally safety contract of `offset` guarantees that the resulting pointer is
        // pointing to an allocation, there can't be an allocation at null, thus it's safe to
        // construct `NonNull`.
        return transmuteWithLayouts<MutPtr, NonNull>(this.asPtr().byteOffset(count), UnresolvedTypeLayout<MutPtr>(), NonNull.typeLayout())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * # Examples
     *
     * ```kotlin
     * import std.ptr.NonNull
     *
     * val s: String = "123"
     * val ptr: NonNull = NonNull.new(s.asPtr().castMut()).unwrap()
     * run {
     *     println("${ptr.add(1).read().toInt().toChar()}")
     *     println("${ptr.add(2).read().toInt().toChar()}")
     * }
     * ```
     */
    public open fun add(count: Int): NonNull {
        // SAFETY: the caller must uphold the safety contract for `offset`.
        // Additionally safety contract of `offset` guarantees that the resulting pointer is
        // pointing to an allocation, there can't be an allocation at null, thus it's safe to
        // construct `NonNull`.
        return transmuteWithLayouts<Ptr, NonNull>(ConstPtr(this.asPtr().rawAddress + (count).toLong() * 1L), UnresolvedTypeLayout<Ptr>(), NonNull.typeLayout())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the offset from a pointer in bytes (convenience for `.byteOffset(count as Long)`).
     *
     * `count` is in units of bytes.
     *
     * This is purely a convenience for casting to a `UByte` pointer and
     * using [`add`][NonNull.add] on it.
     *
     * For non-`Sized` pointees this operation changes only the data pointer,
     * leaving the metadata untouched.
     *
     * # Safety
     *
     * See [`add`][NonNull.add] for documentation and safety requirements.
     */
    public open fun byteAdd(count: Int): NonNull {
        // SAFETY: the caller must uphold the safety contract for `add` and `byteAdd` has the same
        // safety contract.
        // Additionally safety contract of `add` guarantees that the resulting pointer is pointing
        // to an allocation, there can't be an allocation at null, thus it's safe to construct
        // `NonNull`.
        return transmuteWithLayouts<MutPtr, NonNull>(this.asPtr().byteAdd(count), UnresolvedTypeLayout<MutPtr>(), NonNull.typeLayout())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * # Examples
     *
     * ```kotlin
     * import std.ptr.NonNull
     *
     * val s: String = "123"
     * run {
     *     val end: NonNull = NonNull.new(s.asPtr().castMut()).unwrap().add(3)
     *     println("${end.sub(1).read().toInt().toChar()}")
     *     println("${end.sub(2).read().toInt().toChar()}")
     * }
     * ```
     */
    public open fun sub(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): NonNull =
        if (((typeLayoutT).sizeBytes == 0L)) {
            // Pointer arithmetic does nothing when the pointee is a ZST.
            this
        } else {
            // SAFETY: the caller must uphold the safety contract for `offset`.
            // Because the pointee is *not* a ZST, that means that `count` is
            // at most `Long.MAX`, and thus the negation cannot overflow.
            this.offset((count).uncheckedNeg())
        }

    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the offset from a pointer in bytes (convenience for
     * `.byteOffset((count as Long).wrappingNeg())`).
     *
     * `count` is in units of bytes.
     *
     * This is purely a convenience for casting to a `UByte` pointer and
     * using [`sub`][NonNull.sub] on it.
     *
     * For non-`Sized` pointees this operation changes only the data pointer,
     * leaving the metadata untouched.
     *
     * # Safety
     *
     * See [`sub`][NonNull.sub] for documentation and safety requirements.
     */
    public open fun byteSub(count: Int): NonNull {
        // SAFETY: the caller must uphold the safety contract for `sub` and `byteSub` has the same
        // safety contract.
        // Additionally safety contract of `sub` guarantees that the resulting pointer is pointing
        // to an allocation, there can't be an allocation at null, thus it's safe to construct
        // `NonNull`.
        return transmuteWithLayouts<MutPtr, NonNull>(this.asPtr().byteSub(count), UnresolvedTypeLayout<MutPtr>(), NonNull.typeLayout())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the distance between two pointers within the same allocation. The returned value is in
     * units of T: the distance in bytes divided by `sizeOf<T>()`.
     *
     * This is equivalent to `(self as Long - origin as Long) / (sizeOf<T>() as Long)`,
     * except that it has a lot more opportunities for UB, in exchange for the compiler
     * better understanding what you are doing.
     *
     * The primary motivation of this method is for computing the `len` of an array/slice
     * of `T` that you are currently representing as a "start" and "end" pointer
     * (and "end" is "one past the end" of the array).
     * In that case, `end.offsetFrom(start)` gets you the length of the array.
     *
     * All of the following safety requirements are trivially satisfied for this usecase.
     *
     * [offset]: #method.offset
     *
     * # Safety
     *
     * If any of the following conditions are violated, the result is Undefined Behavior:
     *
     * * `self` and `origin` must either
     *
     *   * point to the same address, or
     *   * both be *derived from* a pointer to the same [allocation], and the memory range between
     *     the two pointers must be in bounds of that object. (See below for an example.)
     *
     * * The distance between the pointers, in bytes, must be an exact multiple
     *   of the size of `T`.
     *
     * As a consequence, the absolute distance between the pointers, in bytes, computed on
     * mathematical integers (without "wrapping around"), cannot overflow an `Long`. This is
     * implied by the in-bounds requirement, and the fact that no allocation can be larger
     * than `Long.MAX` bytes.
     *
     * The requirement for pointers to be derived from the same allocation is primarily
     * needed for `const`-compatibility: the distance between pointers into *different* allocated
     * objects is not known at compile-time. However, the requirement also exists at
     * runtime and may be exploited by optimizations. If you wish to compute the difference between
     * pointers that are not guaranteed to be from the same allocation, use
     * `(self.addr() as Long - origin.addr() as Long) / sizeOf<T>()`.
     *
     * [add]: #method.add
     *
     * # Panics
     *
     * This function panics if `T` is a Zero-Sized Type ("ZST").
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import std.ptr.NonNull
     *
     * val a = List(5) { 0 }
     * val ptr1: NonNull = NonNull.from(a[1])
     * val ptr2: NonNull = NonNull.from(a[3])
     * run {
     *     check(ptr2.offsetFrom(ptr1) == 2)
     *     check(ptr1.offsetFrom(ptr2) == -2)
     *     check(ptr1.offset(2) == ptr2)
     *     check(ptr2.offset(-2) == ptr1)
     * }
     * ```
     *
     * *Incorrect* usage:
     *
     * ```kotlin
     * import std.ptr.NonNull
     *
     * val ptr1 = NonNull.new(Holder.intoRaw(0u.toUByte())).unwrap()
     * val ptr2 = NonNull.new(Holder.intoRaw(1u.toUByte())).unwrap()
     * val diff = ((ptr2.addr().get().toInt()) - (ptr1.addr().get().toInt()))
     * // Make ptr2Other an "alias" of ptr2.add(1), but derived from ptr1.
     * val diffPlus1 = (diff + 1)
     * val ptr2Other = NonNull.new(ptr1.asPtr().wrappingByteOffset(diffPlus1)).unwrap()
     * check(ptr2.addr() == ptr2Other.addr())
     * // Since ptr2Other and ptr2 are derived from pointers to different objects,
     * // computing their offset is undefined behavior, even though
     * // they point to addresses that are in-bounds of the same object!
     * val one = ptr2Other.offsetFrom(ptr2)
     * // Undefined Behavior! ⚠️
     * ```
     */
    public open fun offsetFrom(origin: NonNull): Int {
        // SAFETY: the caller must uphold the safety contract for `offsetFrom`.
        return this.asPtr().offsetFrom((origin.asPtr()).asConstPtr())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the distance between two pointers within the same allocation. The returned value is in
     * units of **bytes**.
     *
     * This is purely a convenience for casting to a `UByte` pointer and
     * using [`offsetFrom`][NonNull.offsetFrom] on it.
     *
     * For non-`Sized` pointees this operation considers only the data pointers,
     * ignoring the metadata.
     *
     * # Safety
     *
     * See [`offsetFrom`][NonNull.offsetFrom] for documentation and safety requirements.
     */
    public open fun byteOffsetFrom(origin: NonNull): Int {
        // SAFETY: the caller must uphold the safety contract for `byteOffsetFrom`.
        return this.asPtr().byteOffsetFrom((origin.asPtr()).asConstPtr())
    }

    // N.B. `wrappingOffset``,`wrappingAdd`, etc are not implemented because they can wrap to null
    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the distance between two pointers within the same allocation, *where it's known that
     * `self` is equal to or greater than `origin`*. The returned value is in
     * units of T: the distance in bytes is divided by `sizeOf<T>()`.
     *
     * This computes the same value that [`offsetFrom`](#method.offsetFrom)
     * would compute, but with the added precondition that the offset is
     * guaranteed to be non-negative.  This method is equivalent to
     * `Int.tryFrom(self.offsetFrom(origin)).unwrapUnchecked()`,
     * but it provides slightly more information to the optimizer, which can
     * sometimes allow it to optimize slightly better with some backends.
     *
     * This method can be though of as recovering the `count` that was passed
     * to [`add`](#method.add) (or, with the parameters in the other order,
     * to [`sub`](#method.sub)).  The following are all equivalent, assuming
     * that their safety preconditions are met:
     * ```kotlin
     * fun blah(ptr: NonNull, origin: NonNull, count: Int): Boolean {
     *     return ptr.offsetFromUnsigned(origin) == count && origin.add(count) == ptr && ptr.sub(count) == origin
     * }
     * ```
     *
     * # Safety
     *
     * - The distance between the pointers must be non-negative (`self >= origin`)
     *
     * - *All* the safety conditions of [`offsetFrom`](#method.offsetFrom)
     *   apply to this method as well; see it for the full details.
     *
     * Importantly, despite the return type of this method being able to represent
     * a larger offset, it's still *not permitted* to pass pointers which differ
     * by more than `Long.MAX` *bytes*.  As such, the result of this method will
     * always be less than or equal to `Long.MAX as Int`.
     *
     * # Panics
     *
     * This function panics if `T` is a Zero-Sized Type ("ZST").
     *
     * # Examples
     *
     * ```kotlin
     * import std.ptr.NonNull
     *
     * val a = List(5) { 0 }
     * val ptr1: NonNull = NonNull.from(a[1])
     * val ptr2: NonNull = NonNull.from(a[3])
     * run {
     *     check(ptr2.offsetFromUnsigned(ptr1) == 2)
     *     check(ptr1.add(2) == ptr2)
     *     check(ptr2.sub(2) == ptr1)
     *     check(ptr2.offsetFromUnsigned(ptr2) == 0)
     * }
     * // This would be incorrect, as the pointers are not correctly ordered:
     * // ptr1.offsetFromUnsigned(ptr2)
     * ```
     */
    public open fun offsetFromUnsigned(subtracted: NonNull): Int {
        // SAFETY: the caller must uphold the safety contract for `offsetFromUnsigned`.
        return this.asPtr().offsetFromUnsigned((subtracted.asPtr()).asConstPtr())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Calculates the distance between two pointers within the same allocation, *where it's known that
     * `self` is equal to or greater than `origin`*. The returned value is in
     * units of **bytes**.
     *
     * This is purely a convenience for casting to a `UByte` pointer and
     * using [`offsetFromUnsigned`][NonNull.offsetFromUnsigned] on it.
     *
     * For non-`Sized` pointees this operation considers only the data pointers,
     * ignoring the metadata.
     *
     * # Safety
     *
     * See [`offsetFromUnsigned`][NonNull.offsetFromUnsigned] for documentation and safety requirements.
     */
    public open fun byteOffsetFromUnsigned(origin: NonNull): Int {
        // SAFETY: the caller must uphold the safety contract for `byteOffsetFromUnsigned`.
        return this.asPtr().byteOffsetFromUnsigned((origin.asPtr()).asConstPtr())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Reads the value from `self` without moving it. This leaves the
     * memory in `self` unchanged.
     *
     * # Safety
     *
     * See [ptr.read] for safety concerns and examples.
     */
    public open fun read(): T {
        // SAFETY: the caller must uphold the safety contract for `read`.
        return io.github.kotlinmania.rustlang.core.ptr.read((this.asPtr()).asConstPtr())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Performs a volatile read of the value from `self` without moving it. This
     * leaves the memory in `self` unchanged.
     *
     * Volatile operations are intended to act on I/O memory, and are guaranteed
     * to not be elided or reordered by the compiler across other volatile
     * operations.
     *
     * # Safety
     *
     * See [ptr.readVolatile] for safety concerns and examples.
     */
    public open fun readVolatile(): T {
        // SAFETY: the caller must uphold the safety contract for `readVolatile`.
        return io.github.kotlinmania.rustlang.core.ptr.readVolatile((this.asPtr()).asConstPtr())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Reads the value from `self` without moving it. This leaves the
     * memory in `self` unchanged.
     *
     * Unlike `read`, the pointer may be unaligned.
     *
     * # Safety
     *
     * See [ptr.readUnaligned] for safety concerns and examples.
     */
    public open fun readUnaligned(): T {
        // SAFETY: the caller must uphold the safety contract for `readUnaligned`.
        return io.github.kotlinmania.rustlang.core.ptr.readUnaligned((this.asPtr()).asConstPtr())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Copies `count * sizeOf<T>()` bytes from `self` to `dest`. The source
     * and destination may overlap.
     *
     * NOTE: this has the *same* argument order as [ptr.copy].
     *
     * # Safety
     *
     * See [ptr.copy] for safety concerns and examples.
     */
    public open fun copyTo(dest: NonNull, count: Int) {
        // SAFETY: the caller must uphold the safety contract for `copy`.
        copy((this.asPtr()).asConstPtr(), dest.asPtr(), count)
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Copies `count * sizeOf<T>()` bytes from `self` to `dest`. The source
     * and destination may *not* overlap.
     *
     * NOTE: this has the *same* argument order as [ptr.copyNonoverlapping].
     *
     * # Safety
     *
     * See [ptr.copyNonoverlapping] for safety concerns and examples.
     */
    public open fun copyToNonoverlapping(dest: NonNull, count: Int) {
        // SAFETY: the caller must uphold the safety contract for `copyNonoverlapping`.
        copyNonoverlapping((this.asPtr()).asConstPtr(), dest.asPtr(), count)
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Copies `count * sizeOf<T>()` bytes from `src` to `self`. The source
     * and destination may overlap.
     *
     * NOTE: this has the *opposite* argument order of [ptr.copy].
     *
     * # Safety
     *
     * See [ptr.copy] for safety concerns and examples.
     */
    public open fun copyFrom(src: NonNull, count: Int) {
        // SAFETY: the caller must uphold the safety contract for `copy`.
        copy((src.asPtr()).asConstPtr(), this.asPtr(), count, typeLayoutT = UnresolvedTypeLayout<T>())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Copies `count * sizeOf<T>()` bytes from `src` to `self`. The source
     * and destination may *not* overlap.
     *
     * NOTE: this has the *opposite* argument order of [ptr.copyNonoverlapping].
     *
     * # Safety
     *
     * See [ptr.copyNonoverlapping] for safety concerns and examples.
     */
    public open fun copyFromNonoverlapping(src: NonNull, count: Int) {
        // SAFETY: the caller must uphold the safety contract for `copyNonoverlapping`.
        copyNonoverlapping((src.asPtr()).asConstPtr(), this.asPtr(), count, typeLayoutT = UnresolvedTypeLayout<T>())
    }

    /**
     * Executes the destructor (if any) of the pointed-to value.
     *
     * # Safety
     *
     * See [ptr.dropInPlace] for safety concerns and examples.
     */
    public open fun dropInPlace() {
        // SAFETY: the caller must uphold the safety contract for `dropInPlace`.
        dropGlue(this.asMut())
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Overwrites a memory location with the given value without reading or
     * dropping the old value.
     *
     * # Safety
     *
     * See [ptr.write] for safety concerns and examples.
     */
    public open fun write(value: T) {
        // SAFETY: the caller must uphold the safety contract for `write`.
        io.github.kotlinmania.rustlang.core.ptr.write(this.asPtr(), value)
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Invokes memset on the specified pointer, setting `count * sizeOf<T>()`
     * bytes of memory starting at `self` to `val`.
     *
     * # Safety
     *
     * See [ptr.writeBytes] for safety concerns and examples.
     */
    public open fun writeBytes(value: UByte, count: Int) {
        // SAFETY: the caller must uphold the safety contract for `writeBytes`.
        io.github.kotlinmania.rustlang.core.ptr.writeBytes(this.asPtr(), value, count)
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Performs a volatile write of a memory location with the given value without
     * reading or dropping the old value.
     *
     * Volatile operations are intended to act on I/O memory, and are guaranteed
     * to not be elided or reordered by the compiler across other volatile
     * operations.
     *
     * # Safety
     *
     * See [ptr.writeVolatile] for safety concerns and examples.
     */
    public open fun writeVolatile(value: T) {
        // SAFETY: the caller must uphold the safety contract for `writeVolatile`.
        io.github.kotlinmania.rustlang.core.ptr.writeVolatile(this.asPtr(), value)
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Overwrites a memory location with the given value without reading or
     * dropping the old value.
     *
     * Unlike `write`, the pointer may be unaligned.
     *
     * # Safety
     *
     * See [ptr.writeUnaligned] for safety concerns and examples.
     */
    public open fun writeUnaligned(value: T) {
        // SAFETY: the caller must uphold the safety contract for `writeUnaligned`.
        io.github.kotlinmania.rustlang.core.ptr.writeUnaligned(this.asPtr(), value)
    }

    /**
     * Replaces the value at `self` with `src`, returning the old
     * value, without dropping either.
     *
     * # Safety
     *
     * See [ptr.replace] for safety concerns and examples.
     */
    public open fun replace(src: T): T {
        // SAFETY: the caller must uphold the safety contract for `replace`.
        return io.github.kotlinmania.rustlang.core.ptr.replace(this.asPtr(), src)
    }

    /**
     * Swaps the values at two mutable locations of the same type, without
     * deinitializing either. They may overlap, unlike `mem.swap` which is
     * otherwise equivalent.
     *
     * # Safety
     *
     * See [ptr.swap] for safety concerns and examples.
     */
    public open fun swap(with: NonNull) {
        // SAFETY: the caller must uphold the safety contract for `swap`.
        io.github.kotlinmania.rustlang.core.ptr.swap(this.asPtr(), with.asPtr())
    }

    /**
     * Computes the offset that needs to be applied to the pointer in order to make it aligned to
     * `align`.
     *
     * If it is not possible to align the pointer, the implementation returns
     * `Int.MAX`.
     *
     * The offset is expressed in number of `T` elements, and not bytes.
     *
     * There are no guarantees whatsoever that offsetting the pointer will not overflow or go
     * beyond the allocation that the pointer points into. It is up to the caller to ensure that
     * the returned offset is correct in all terms other than alignment.
     *
     * When this is called during compile-time evaluation (which is unstable), the implementation
     * may return `Int.MAX` in cases where that can never happen at runtime. This is because the
     * actual alignment of pointers is not known yet during compile-time, so an offset with
     * guaranteed alignment can sometimes not be computed. For example, a buffer declared as `[u8;
     * N]` might be allocated at an odd or an even address, but at compile-time this is not yet
     * known, so the execution has to be correct for either choice. It is therefore impossible to
     * find an offset that is guaranteed to be 2-aligned. (This behavior is subject to change, as usual
     * for unstable APIs.)
     *
     * # Panics
     *
     * The function panics if `align` is not a power-of-two.
     *
     * # Examples
     *
     * Accessing adjacent `UByte` as `UShort`
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     * import std.ptr.NonNull
     *
     * public fun doctestEntry() {
     *     run {
     *         val x = ubyteArrayOf(5u.toUByte(), 6u, 7u, 8u, 9u)
     *         val ptr = NonNull.new((x.asPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).unwrap()
     *         val offset = ptr.alignOffset(alignOf<UShort>(typeLayoutT = PrimitiveLayouts.U16))
     *         if (offset < x.size - 1) {
     *             val u16Ptr = ptr.add(offset).cast()
     *             check(u16Ptr.read() == run { val bytesTmp0 = ubyteArrayOf(5u, 6u); ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() } || u16Ptr.read() == run { val bytesTmp1 = ubyteArrayOf(6u, 7u); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
     *         } else {
     *             // while the pointer can be aligned via `offset`, it would point
     *             // outside the allocation
     *         }
     *     }
     * }
     * ```
     */
    public open fun alignOffset(align: Int): Int {
        if (!align.isPowerOfTwo()) {
            error("align_offset: align is not a power-of-two")
        }
        return io.github.kotlinmania.rustlang.core.ptr.alignOffset((this.asPtr()).asConstPtr(), align)
    }

    /**
     * Returns whether the pointer is properly aligned for `T`.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     * import std.ptr.NonNull
     *
     * // On some platforms, the alignment of i32 is less than 4.
     * @repr(align(4))
     * open class AlignedI32(public val fieldStorage: MutPtr, public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) {
     *     public var field0: Int
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public var value: Int
     *         get() = fieldLayout.readNative(fieldStorage.rawAddress)
     *         set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }
     *
     *     public constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))
     *
     *     public fun pointerLayout(): TypeLayout<AlignedI32> = TransparentTypeLayout(fieldLayout, { ptr, layout -> AlignedI32(ptr, layout) }, { it.value })
     * }
     * val data = AlignedI32(42, fieldLayout = PrimitiveLayouts.I32)
     * val ptr = NonNull.from(data)
     * check(ptr.isAligned())
     * check(!NonNull.new(ptr.asPtr().wrappingByteAdd(1)).unwrap().isAligned())
     * ```
     */
    public open fun isAligned(): Boolean = this.asPtr().isAligned()

    /**
     * Returns whether the pointer is aligned to `align`.
     *
     * For non-`Sized` pointees this operation considers only the data pointer,
     * ignoring the metadata.
     *
     * # Panics
     *
     * The function panics if `align` is not a power-of-two (this includes 0).
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     *
     * @feature(pointerIsAlignedTo)
     * public fun doctestEntry() {
     *     // On some platforms, the alignment of i32 is less than 4.
     *     @repr(align(4))
     *     open class AlignedI32(public val fieldStorage: MutPtr, public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) {
     *         public var field0: Int
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public var value: Int
     *             get() = fieldLayout.readNative(fieldStorage.rawAddress)
     *             set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }
     *
     *         public constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 4L)), AlignedTypeLayout(fieldLayout, 4L))
     *
     *         public fun pointerLayout(): TypeLayout<AlignedI32> = TransparentTypeLayout(fieldLayout, { ptr, layout -> AlignedI32(ptr, layout) }, { it.value })
     *     }
     *     val localStorageL9C0 = AddressableLocal(AlignedI32(42, fieldLayout = PrimitiveLayouts.I32), AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> AlignedI32(ptr, layout) }, { it.value }), 4L))
     *     val ptr = ConstPtr(localStorageL9C0.asMutPtr().rawAddress)
     *     check(ptr.isAlignedTo(1))
     *     check(ptr.isAlignedTo(2))
     *     check(ptr.isAlignedTo(4))
     *     check(ConstPtr((ptr).rawAddress + ((2).toLong())).isAlignedTo(2))
     *     check(!ConstPtr((ptr).rawAddress + ((2).toLong())).isAlignedTo(4))
     *     check(ptr.isAlignedTo(8) != ConstPtr((ptr).rawAddress + ((1).toLong() * (AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> AlignedI32(ptr, layout) }, { it.value }), 4L)).strideBytes)).isAlignedTo(8))
     * }
     * ```
     */
    public open fun isAlignedTo(align: Int): Boolean = this.asPtr().isAlignedTo(align)

    /**
     * Casts from a type to its maybe-uninitialized version.
     */
    public open fun castUninit(): NonNull = this.cast()

    /**
     * Creates a non-null raw slice from a thin pointer and a length.
     *
     * The `len` argument is the number of **elements**, not the number of bytes.
     *
     * See [ptr.sliceFromRawParts] for more information.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
     * import std.ptr.NonNull
     *
     * // create a slice pointer when starting out with a pointer to the first element
     * val localStorageL6C0 = AddressableArrayLocal.fromIntArray(intArrayOf(5, 6, 7))
     * val nonnullPointer = NonNull.new(localStorageL6C0.elementMutPtr(0)).unwrap()
     * val slice = nonnullPointer.castSlice(3)
     * check(slice.asRef()[2] == 7)
     * ```
     *
     * (Note that this example artificially demonstrates a use of this method,
     * but `val slice = NonNull.from(x[..]);` would be a better way to write code like this.)
     */
    public open fun castSlice(len: Int): NonNull = NonNull.sliceFromRawParts(this, len)

    public open fun clone(): NonNull = NonNull(this.pointer)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.asPtr()).toString()); Result.Ok<Unit, Unit>(Unit) }

    public open fun fmtPointer(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.asPtr()).toString()); Result.Ok<Unit, Unit>(Unit) }

    public open fun eq(other: NonNull): Boolean = this.asPtr() == other.asPtr()

    public open fun cmp(other: NonNull): Ordering = this.asPtr().cmp(other.asPtr())

    public open fun partialCmp(other: NonNull): Ordering? = this.asPtr().partialCmp(other.asPtr())

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.asPtr()).hashCode()
        return state
    }

    public companion object {
        public fun typeLayout(): TypeLayout<NonNull> =
            object : TypeLayout<NonNull> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): NonNull =
                    NonNull(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: NonNull) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.pointer)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NonNull =
                    NonNull(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NonNull) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.pointer)
                }
            }

        /**
         * Creates a pointer with the given address and no [provenance][ptr#provenance].
         *
         * For more details, see the equivalent method on a raw pointer, [ptr.withoutProvenanceMut].
         *
         * This is a [Strict Provenance][ptr#strict-provenance] API.
         */
        public fun withoutProvenance(addr: NonZero<Int, NonZeroUsizeInner>): NonNull {
            // SAFETY: we know `addr` is non-zero and all nonzero integers are valid raw pointers.
            return transmuteWithLayouts<NonZero<Int, NonZeroUsizeInner>, NonNull>(addr, UnresolvedTypeLayout<NonZero<Int, NonZeroUsizeInner>>(), NonNull.typeLayout())
        }

        /**
         * Creates a new `NonNull` that is dangling, but well-aligned.
         *
         * This is useful for initializing types which lazily allocate, like
         * `List.new` does.
         *
         * Note that the address of the returned pointer may potentially
         * be that of a valid pointer, which means this must not be used
         * as a "not yet initialized" sentinel value.
         * Types that lazily allocate must track initialization by some other means.
         *
         * # Examples
         *
         * ```kotlin
         * import std.ptr.NonNull
         *
         * val ptr = NonNull.dangling()
         * // Important: don't try to access the value of `ptr` without
         * // initializing it first! The pointer is not null but isn't valid either!
         * ```
         */
        public fun <T> dangling(): NonNull {
            val align = Alignment.of<T>()
            return NonNull.withoutProvenance(align.asNonzeroUsize())
        }

        /**
         * Converts an address back to a mutable pointer, picking up some previously `exposed`
         * [provenance][ptr#provenance].
         *
         * For more details, see the equivalent method on a raw pointer, [ptr.withExposedProvenanceMut].
         *
         * This is an [Exposed Provenance][ptr#exposed-provenance] API.
         */
        public fun withExposedProvenance(addr: NonZero<Int, NonZeroUsizeInner>): NonNull {
            // SAFETY: we know `addr` is non-zero.
            return run {
                val ptr = withExposedProvenanceMut(addr.get())
                NonNull.newUnchecked(ptr)
            }
        }

        /**
         * Creates a new `NonNull`.
         *
         * Note that if you have an , you can use the safe [fromMut] instead.
         *
         * [fromMut]: NonNull.fromMut
         *
         * # Safety
         *
         * `ptr` must be non-null.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
         * import std.ptr.NonNull
         *
         * val localStorageL5C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
         * val ptr = NonNull.newUnchecked(MutPtr(localStorageL5C0.asMutPtr().rawAddress))
         * ```
         *
         * *Incorrect* usage of this function:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
         * import std.ptr.NonNull
         *
         * // NEVER DO THAT!!! This is undefined behavior. ⚠️
         * val ptr = NonNull.newUnchecked(MutPtr(0L))
         * ```
         */
        public fun newUnchecked(ptr: MutPtr): NonNull {
            // SAFETY: the caller must guarantee that `ptr` is non-null.
            return run {
                Unit
                transmuteWithLayouts<MutPtr, NonNull>(ptr, PrimitiveLayouts.mutPtrLayout(64L), NonNull.typeLayout())
            }
        }

        /**
         * Creates a new `NonNull` if `ptr` is non-null.
         *
         * Note that if you have an , you can use [fromMut] instead to avoid the `Option`.
         *
         * [fromMut]: NonNull.fromMut
         *
         * # Panics during const evaluation
         *
         * This method will panic during const evaluation if the pointer cannot be
         * determined to be null or not. See [isNull] for more information.
         *
         * [isNull]: ../primitive.pointer.html#method.isNull-1
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
         * import std.ptr.NonNull
         *
         * val localStorageL5C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
         * val ptr = NonNull.new(MutPtr(localStorageL5C0.asMutPtr().rawAddress)).expect("pointer should not be null")
         * when (val matchVal = NonNull.new(MutPtr(0L))) {
         *     null -> {}
         *     else -> {
         *         val ptr1 = matchVal
         *         error("unreachable")
         *     }
         * }
         * ```
         */
        public fun new(ptr: MutPtr): NonNull? =
            if (!((ptr).rawAddress == 0L)) {
                // SAFETY: The pointer is already checked and is not null
                NonNull.newUnchecked(ptr)
            } else {
                null
            }

        /**
         * Converts a reference to a `NonNull` pointer.
         */
        public fun <T> fromRef(r: T): NonNull {
            // SAFETY: A reference cannot be null.
            return NonNull(constPtrFromRustCast(r))
        }

        /**
         * Converts a mutable reference to a `NonNull` pointer.
         */
        public fun <T> fromMut(r: T): NonNull {
            // SAFETY: A mutable reference cannot be null.
            return transmuteWithLayouts<MutPtr, NonNull>(mutPtrFromRustCast(r), PrimitiveLayouts.mutPtrLayout(64L), NonNull.typeLayout())
        }

        /**
         * Performs the same functionality as [std.ptr.fromRawParts], except that a
         * `NonNull` pointer is returned, as opposed to a raw `* const` pointer.
         *
         * See the documentation of [std.ptr.fromRawParts] for more details.
         */
        public fun fromRawParts(dataPointer: NonNull, metadata: Metadata): NonNull {
            // SAFETY: The result of `ptr.from.rawPartsMut` is non-null because `dataPointer` is.
            return NonNull.newUnchecked(linearMutSliceView(dataPointer.asPtr(), metadata.toInt(), UnresolvedTypeLayout<Thin>()))
        }

        /**
         * Converts a `T` to a `NonNull<T>`.
         *
         * This conversion is safe and infallible since references cannot be null.
         */
        public fun <T> from(r: T): NonNull = NonNull.fromMut(r)

        /**
         * Converts a `T` to a `NonNull<T>`.
         *
         * This conversion is safe and infallible since references cannot be null.
         */
        public fun <T> fromT(r: T): NonNull = NonNull.fromRef(r)
    }
}

/**
 * Casts from a maybe-uninitialized type to its initialized version.
 *
 * This is always safe, since UB can only occur if the pointer is read
 * before being initialized.
 */
public fun NonNull.castInit(): NonNull = this.cast()

/**
 * Creates a non-null raw slice from a thin pointer and a length.
 *
 * The `len` argument is the number of **elements**, not the number of bytes.
 *
 * This function is safe, but dereferencing the return value is unchecked.
 * See the documentation of [slice.fromRawParts] for slice safety requirements.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import std.ptr.NonNull
 *
 * // create a slice pointer when starting out with a pointer to the first element
 * val localStorageL6C0 = AddressableArrayLocal.fromIntArray(intArrayOf(5, 6, 7))
 * val nonnullPointer = NonNull.new(localStorageL6C0.elementMutPtr(0)).unwrap()
 * val slice = NonNull.sliceFromRawParts(nonnullPointer, 3)
 * check(slice.asRef()[2] == 7)
 * ```
 *
 * (Note that this example artificially demonstrates a use of this method,
 * but `val slice = NonNull.from(x[..]);` would be a better way to write code like this.)
 */
public fun NonNull.Companion.sliceFromRawParts(data: NonNull, len: Int): NonNull {
    // SAFETY: `data` is a `NonNull` pointer which is necessarily non-null
    return NonNull.newUnchecked(data.asPtr().castSlice(len))
}

/**
 * Returns the length of a non-null raw slice.
 *
 * The returned value is the number of **elements**, not the number of bytes.
 *
 * This function is safe, even when the non-null raw slice cannot be dereferenced to a slice
 * because the pointer does not have a valid address.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr.NonNull
 *
 * val slice: NonNull = NonNull.sliceFromRawParts(NonNull.dangling(), 3)
 * check(slice.len() == 3)
 * ```
 */
public fun NonNull.len(): Int = this.asPtr().len()

/**
 * Returns `true` if the non-null raw slice has a length of 0.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr.NonNull
 *
 * val slice: NonNull = NonNull.sliceFromRawParts(NonNull.dangling(), 3)
 * check(!slice.isEmpty())
 * ```
 */
public fun NonNull.isEmpty(): Boolean = this.len() == 0

/**
 * Returns a non-null pointer to the slice's buffer.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr.NonNull
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val slice: NonNull = NonNull.sliceFromRawParts(NonNull.dangling(), 3)
 *     check(slice.asNonNullPtr() == NonNull.dangling())
 * }
 * ```
 */
public fun NonNull.asNonNullPtr(): NonNull = this.cast()

/**
 * Returns a raw pointer to the slice's buffer.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr.NonNull
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val slice: NonNull = NonNull.sliceFromRawParts(NonNull.dangling(), 3)
 *     check(slice.asPtr() == NonNull.dangling().asPtr())
 * }
 * ```
 */
public fun NonNull.asMutPtr(): MutPtr = this.asNonNullPtr().asPtr()

/**
 * Returns a shared reference to a slice of possibly uninitialized values. In contrast to
 * [asRef], this does not require that the value has to be initialized.
 *
 * For the mutable counterpart see [asUninitSliceMut].
 *
 * [asRef]: NonNull.asRef
 * [asUninitSliceMut]: NonNull.asUninitSliceMut
 *
 * # Safety
 *
 * When calling this method, you have to ensure that all of the following is true:
 *
 * * The pointer must be [valid] for reads for `ptr.len() * sizeOf<T>()` many bytes,
 *   and it must be properly aligned. This means in particular:
 *
 *     * The entire memory range of this slice must be contained within a single allocation!
 *       Slices can never span across multiple allocations.
 *
 *     * The pointer must be aligned even for zero-length slices. One
 *       reason for this is that enum layout optimizations may rely on references
 *       (including slices of any length) being aligned and non-null to distinguish
 *       them from other data. You can obtain a pointer that is usable as `data`
 *       for zero-length slices using [NonNull.dangling].
 *
 * * The total size `ptr.len() * sizeOf<T>()` of the slice must be no larger than `Long.MAX`.
 *   See the safety documentation of [pointer.offset].
 *
 * * You must enforce Rust's aliasing rules, since the returned scope `a` is
 *   arbitrarily chosen and does not necessarily reflect the actual scope of the data.
 *   In particular, while this reference exists, the memory the pointer points to must
 *   not get mutated (except inside `UnsafeCell`).
 *
 * This applies even if the result of this method is unused!
 *
 * See also [slice.fromRawParts].
 */
public fun <T> NonNull.asUninitSlice(): List<MaybeUninit<T>> {
    // SAFETY: the caller must uphold the safety contract for `asUninitSlice`.
    return linearSliceView(this.cast().asPtr(), this.len().toInt(), UnresolvedTypeLayout<U>())
}

/**
 * Returns a unique reference to a slice of possibly uninitialized values. In contrast to
 * [asMut], this does not require that the value has to be initialized.
 *
 * For the shared counterpart see [asUninitSlice].
 *
 * [asMut]: NonNull.asMut
 * [asUninitSlice]: NonNull.asUninitSlice
 *
 * # Safety
 *
 * When calling this method, you have to ensure that all of the following is true:
 *
 * * The pointer must be [valid] for reads and writes for `ptr.len() * sizeOf<T>()`
 *   many bytes, and it must be properly aligned. This means in particular:
 *
 *     * The entire memory range of this slice must be contained within a single allocation!
 *       Slices can never span across multiple allocations.
 *
 *     * The pointer must be aligned even for zero-length slices. One
 *       reason for this is that enum layout optimizations may rely on references
 *       (including slices of any length) being aligned and non-null to distinguish
 *       them from other data. You can obtain a pointer that is usable as `data`
 *       for zero-length slices using [NonNull.dangling].
 *
 * * The total size `ptr.len() * sizeOf<T>()` of the slice must be no larger than `Long.MAX`.
 *   See the safety documentation of [pointer.offset].
 *
 * * You must enforce Rust's aliasing rules, since the returned scope `a` is
 *   arbitrarily chosen and does not necessarily reflect the actual scope of the data.
 *   In particular, while this reference exists, the memory the pointer points to must
 *   not get accessed (read or written) through any other pointer.
 *
 * This applies even if the result of this method is unused!
 *
 * See also [slice.fromRawPartsMut].
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
 * import std.alloc.Allocator
 * import std.alloc.Global
 * import std.alloc.Layout
 * import std.mem.MaybeUninit
 * import std.ptr.NonNull
 *
 * @feature(ptrAsUninit)
 * public fun doctestEntry(): Result<Unit> {
 *     val memory: NonNull = Global.allocate(Layout.new<UByteArray>(typeLayoutT = PrimitiveArrayLayouts.uByteArray((32).toInt()))).getOrElse { return Result.failure(it) }
 *     // This is safe as `memory` is valid for reads and writes for `memory.len()` many bytes.
 *     // Note that calling `memory.asMut()` is not allowed here as the content may be uninitialized.
 *     val slice: MutableList<MaybeUninit<UByte>> = memory.asUninitSliceMut()
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> NonNull.asUninitSliceMut(): MutableList<MaybeUninit<T>> {
    // SAFETY: the caller must uphold the safety contract for `asUninitSliceMut`.
    return io.github.kotlinmania.rustlang.core.slice.raw.fromRawPartsMut(this.cast().asPtr(), this.len())
}

/**
 * Returns a raw pointer to an element or subslice, without doing bounds
 * checking.
 *
 * # Safety
 *
 * Calling this method with an [out-of-bounds index] or when `self` is not dereferenceable
 * is *[undefined behavior]* even if the resulting pointer is not used.
 *
 * [out-of-bounds index]: #method.add
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr.NonNull
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val x = listOf(1, 2, 4)
 *     val x1 = NonNull.sliceFromRawParts(NonNull.new(x.asMutPtr()).unwrap(), x.size)
 *     run {
 *         check(x1.getUncheckedMut(1).asPtr() == x1.asNonNullPtr().asPtr().add(1))
 *     }
 * }
 * ```
 */
public fun <Output, T, I> NonNull.getUncheckedMut(index: I): NonNull where I : SliceIndex<List<T>, Output> {
    // SAFETY: the caller ensures that `self` is dereferenceable and `index` in-bounds.
    // As a consequence, the resulting pointer cannot be null.
    return NonNull.newUnchecked(this.asPtr().getUncheckedMut(index))
}
