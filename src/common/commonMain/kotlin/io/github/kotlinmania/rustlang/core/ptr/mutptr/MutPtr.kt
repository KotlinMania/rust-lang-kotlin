// port-lint: source ptr/mut_ptr.rs
package io.github.kotlinmania.rustlang.core.ptr.mutptr

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.fmt.write
import io.github.kotlinmania.rustlang.core.marker.Copy
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.overflowingAdd
import io.github.kotlinmania.rustlang.core.num.overflowingAddSigned
import io.github.kotlinmania.rustlang.core.num.wrappingNeg
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.alignOffset
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.addr
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.asUninitRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.asUninitSlice
import io.github.kotlinmania.rustlang.core.ptr.constptr.byteAdd
import io.github.kotlinmania.rustlang.core.ptr.constptr.byteOffset
import io.github.kotlinmania.rustlang.core.ptr.constptr.byteOffsetFrom
import io.github.kotlinmania.rustlang.core.ptr.constptr.byteOffsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.constptr.byteSub
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.constptr.castInit
import io.github.kotlinmania.rustlang.core.ptr.constptr.castSlice
import io.github.kotlinmania.rustlang.core.ptr.constptr.castUninit
import io.github.kotlinmania.rustlang.core.ptr.constptr.copyTo
import io.github.kotlinmania.rustlang.core.ptr.constptr.copyToNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.constptr.exposeProvenance
import io.github.kotlinmania.rustlang.core.ptr.constptr.isAligned
import io.github.kotlinmania.rustlang.core.ptr.constptr.isAlignedTo
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.constptr.mapAddr
import io.github.kotlinmania.rustlang.core.ptr.constptr.mask
import io.github.kotlinmania.rustlang.core.ptr.constptr.offset
import io.github.kotlinmania.rustlang.core.ptr.constptr.offsetFrom
import io.github.kotlinmania.rustlang.core.ptr.constptr.offsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.constptr.sub
import io.github.kotlinmania.rustlang.core.ptr.constptr.toRawParts
import io.github.kotlinmania.rustlang.core.ptr.constptr.withAddr
import io.github.kotlinmania.rustlang.core.ptr.constptr.withMetadataOf
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingAdd
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingByteAdd
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingByteOffset
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingByteSub
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingOffset
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingSub
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.metadata.fromRawPartsMut
import io.github.kotlinmania.rustlang.core.ptr.metadata.metadata
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.readVolatile
import io.github.kotlinmania.rustlang.core.ptr.replace
import io.github.kotlinmania.rustlang.core.ptr.sliceFromRawPartsMut
import io.github.kotlinmania.rustlang.core.ptr.swap
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeUnaligned
import io.github.kotlinmania.rustlang.core.ptr.writeVolatile
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex
import io.github.kotlinmania.rustlang.core.slice.splitAtMut

/**
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 * val ptr: MutPtr = localStorageL3C0.elementMutPtr(0)
 * check(!((ptr).rawAddress == 0L))
 * ```
 */
public fun MutPtr.isNull(): Boolean = ((ConstPtr((this).rawAddress)).rawAddress == 0L)

/**
 * Casts to a pointer of another type.
 */
public fun MutPtr.cast(): MutPtr = this

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
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableLocal(0uL, PrimitiveLayouts.U64)
 * val aligned: MutPtr = RustMutableReference(localStorageL3C0.asMutPtr(), localStorageL3C0.layout)
 * val unaligned = MutPtr((aligned).rawAddress + ((1).toLong()))
 * check(aligned.tryCastAligned<UInt>() != null)
 * check(unaligned.tryCastAligned<UInt>() == null)
 * ```
 */
public fun <U> MutPtr.tryCastAligned(typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): MutPtr? =
    if (this.isAlignedTo(alignOf<U>(typeLayoutT = typeLayoutU))) {
        MutPtr((this).rawAddress)
    } else {
        null
    }

/**
 * Uses the address value in a new pointer of another type.
 *
 * This operation will ignore the address part of its `meta` operand and discard existing
 * metadata of `self`. For pointers to a sized types (thin pointers), this has the same effect
 * as a simple cast. For pointers to an unsized type (fat pointers) this recombines the address
 * with new metadata such as slice lengths or `dynamic`-vtable.
 *
 * The resulting pointer will have provenance of `self`. This operation is semantically the
 * same as creating a new pointer with the data pointer value of `self` but the metadata of
 * `meta`, being fat or thin depending on the `meta` operand.
 *
 * # Examples
 *
 * This function is primarily useful for enabling pointer arithmetic on potentially fat
 * pointers. The pointer is cast to a sized pointee to utilize offset operations and then
 * recombined with its own original metadata.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(setPtrValue)
 * public fun doctestEntry() {
 *     val localStorageL4C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 *     var ptr = (localStorageL4C0.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     val thin = MutPtr(ptr.rawAddress, ptr.rawMeta)
 *     run {
 *         ptr = MutPtr((thin).rawAddress + ((8).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((ptr).asConstPtr())
 *         println("${(UnresolvedTypeLayout<Any>()).readNative((ptr).rawAddress)}")
 *         // will print "3"
 *     }
 * }
 * ```
 *
 * # *Incorrect* usage
 *
 * The provenance from pointers is *not* combined. The result must only be used to refer to the
 * address allowed by `self`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(setPtrValue)
 * public fun doctestEntry() {
 *     val localStorageL4C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
 *     val x by localStorageL4C0
 *     val localStorageL5C0 = AddressableLocal(1u, PrimitiveLayouts.U32)
 *     val y by localStorageL5C0
 *     val x1 = MutPtr(localStorageL4C0.asMutPtr().rawAddress)
 *     val y1 = MutPtr(localStorageL5C0.asMutPtr().rawAddress)
 *     val offset = (x1.rawAddress.toInt() - y1.rawAddress.toInt()) / 4
 *     val bad = MutPtr((x1).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U32).strideBytes)).withMetadataOf((y1).asConstPtr())
 *     // This dereference is UB. The pointer only has provenance for `x` but points to `y`.
 *     println("$bad")
 * }
 * ```
 */
public fun MutPtr.withMetadataOf(meta: ConstPtr): MutPtr = linearMutSliceView(MutPtr(this.rawAddress, this.rawMeta), metadata(meta).toInt(), PrimitiveLayouts.UNIT)

/**
 * Changes constness without changing the type.
 *
 * This is a bit safer than `as` because it wouldn't silently change the type if the code is
 * refactored.
 *
 * While not strictly required (`* T` coerces to `* const T`), this is provided for symmetry
 * with [castMut] on `* const T` and may have documentation value if used instead of implicit
 * coercion.
 *
 * [castMut]: pointer.castMut
 */
public fun MutPtr.castConst(): ConstPtr = this

/**
 * [withoutProvenance]: withoutProvenanceMut
 */
public fun MutPtr.addr(): Int {
    // A pointer-to-integer reinterpret currently has exactly the right semantics: it returns the
    // address without exposing the provenance. Note that this is *not* a stable guarantee about
    // reinterpret semantics, it relies on sysroot crates having special status.
    // SAFETY: Pointer-to-integer transmutes are valid (if you are okay with losing the
    // provenance).
    return transmuteWithLayouts<MutPtr, Int>(MutPtr((this).rawAddress), PrimitiveLayouts.mutPtrLayout(64L), PrimitiveLayouts.USIZE64)
}

/**
 * Exposes the ["provenance"][ptr#provenance] part of the pointer for future use in
 * [withExposedProvenanceMut] and returns the "address" portion.
 *
 * This is equivalent to `self as Int`, which semantically discards provenance information.
 * Furthermore, this (like the `as` cast) has the implicit side-effect of marking the
 * provenance as `exposed`, so on platforms that support it you can later call
 * [withExposedProvenanceMut] to reconstitute the original pointer including its provenance.
 *
 * Due to its inherent ambiguity, [withExposedProvenanceMut] may not be supported by tools
 * that help you to stay conformant with the Rust memory model. It is recommended to use
 * [Strict Provenance][ptr#strict-provenance] APIs such as [`withAddr`][pointer.withAddr]
 * wherever possible, in which case [`addr`][pointer.addr] should be used instead of `exposeProvenance`.
 *
 * On most platforms this will produce a value with the same bytes as the original pointer,
 * because all the bytes are dedicated to describing the address. Platforms which need to store
 * additional information in the pointer may not support this operation, since the `expose`
 * side-effect which is required for [withExposedProvenanceMut] to work is typically not
 * available.
 *
 * This is an [Exposed Provenance][ptr#exposed-provenance] API.
 *
 * [withExposedProvenanceMut]: withExposedProvenanceMut
 */
public fun MutPtr.exposeProvenance(): Int = MutPtr((this).rawAddress).rawAddress.toInt()

/**
 * Creates a new pointer with the given address and the [provenance][ptr#provenance] of
 * `self`.
 *
 * This is similar to a `addr as * T` cast, but copies
 * the *provenance* of `self` to the new pointer.
 * This avoids the inherent ambiguity of the unary cast.
 *
 * This is equivalent to using [`wrappingOffset`][pointer.wrappingOffset] to offset
 * `self` to the given address, and therefore has all the same capabilities and restrictions.
 *
 * This is a [Strict Provenance][ptr#strict-provenance] API.
 */
public fun MutPtr.withAddr(addr: Int): MutPtr {
    // This should probably be an intrinsic to avoid doing any sort of arithmetic, but
    // meanwhile, we can implement it with `wrappingOffset`, which preserves the pointer's
    // provenance.
    val selfAddr = (this).rawAddress.toInt()
    val destAddr = addr
    val offset = (destAddr - (selfAddr.toInt()))
    return MutPtr((this).rawAddress + ((offset).toLong()))
}

/**
 * Creates a new pointer by mapping `self`s address to a new one, preserving the original
 * pointer's [provenance][ptr#provenance].
 *
 * This is a convenience for [`withAddr`][pointer.withAddr], see that method for details.
 *
 * This is a [Strict Provenance][ptr#strict-provenance] API.
 */
public fun MutPtr.mapAddr(f: (Int) -> Int): MutPtr = MutPtr((f((this).rawAddress.toInt())).toLong())

/**
 * Decompose a (possibly wide) pointer into its data pointer and metadata components.
 *
 * The pointer can be later reconstructed with [fromRawPartsMut].
 */
public fun MutPtr.toRawParts(): Pair<MutPtr, Metadata> = Pair(this, metadata(this))

/**
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val ptr: MutPtr = MutPtr((RustMutableReference(allocateNativeCell(10u.toUByte(), PrimitiveLayouts.U8), PrimitiveLayouts.U8)).asMutPtr().rawAddress)
 * run {
 *     val valBack = ptr.asRefUnchecked()
 *     println("We got back the value: $valBack!")
 * }
 * ```
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val ptr: MutPtr = MutPtr((RustMutableReference(allocateNativeCell(10u.toUByte(), PrimitiveLayouts.U8), PrimitiveLayouts.U8)).asMutPtr().rawAddress)
 * when (val matchVal = ptr.asRef()) {
 *     null -> {}
 *     else -> {
 *         val valBack = matchVal
 *         println("We got back the value: $valBack!")
 *     }
 * }
 * ```
 *
 * # See Also
 *
 * For the mutable counterpart see [asMut].
 *
 * [isNull]: #method.isNull-1
 * [asUninitRef]: #method.asUninitRef-1
 * [asRefUnchecked]: #method.asRefUnchecked-1
 * [asMut]: #method.asMut
 */
public fun <T> MutPtr.asRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T? {
    // SAFETY: the caller must guarantee that `self` is valid for a
    // reference if it isn't null.
    return if (((this).rawAddress == 0L)) {
        null
    } else {
        (typeLayoutT).readNative((this).rawAddress)
    }
}

/**
 * Returns a shared reference to the value behind the pointer.
 * If the pointer may be null or the value may be uninitialized, [asUninitRef] must be used instead.
 * If the pointer may be null, but the value is known to have been initialized, [asRef] must be used instead.
 *
 * For the mutable counterpart see [asMutUnchecked].
 *
 * [asRef]: #method.asRef
 * [asUninitRef]: #method.asUninitRef
 * [asMutUnchecked]: #method.asMutUnchecked
 *
 * # Safety
 *
 * When calling this method, you have to ensure that the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val ptr: MutPtr = MutPtr((RustMutableReference(allocateNativeCell(10u.toUByte(), PrimitiveLayouts.U8), PrimitiveLayouts.U8)).asMutPtr().rawAddress)
 * run {
 *     println("We got back the value: ${ptr.asRefUnchecked()}!")
 * }
 * ```
 */
public fun <T> MutPtr.asRefUnchecked(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must guarantee that `self` is valid for a reference
    return (typeLayoutT).readNative((this).rawAddress)
}

/**
 * [isNull]: #method.isNull-1
 * [asRef]: pointer#method.asRef-1
 *
 * # See Also
 * For the mutable counterpart see [asUninitMut].
 *
 * [asUninitMut]: #method.asUninitMut
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(ptrAsUninit)
 * public fun doctestEntry() {
 *     val ptr: MutPtr = MutPtr((RustMutableReference(allocateNativeCell(10u.toUByte(), PrimitiveLayouts.U8), PrimitiveLayouts.U8)).asMutPtr().rawAddress)
 *     when (val matchVal = ptr.asUninitRef()) {
 *         null -> {}
 *         else -> {
 *             val valBack = matchVal
 *             println("We got back the value: ${valBack.assumeInit()}!")
 *         }
 *     }
 * }
 * ```
 */
public fun <T> MutPtr.asUninitRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T>? {
    // SAFETY: the caller must guarantee that `self` meets all the
    // requirements for a reference.
    return if (((this).rawAddress == 0L)) {
        null
    } else {
        (UnresolvedTypeLayout<MaybeUninit<T>>()).readNative(((ConstPtr(this.rawAddress, this.rawMeta))).rawAddress)
    }
}

/**
 * Consider using [`wrappingOffset`](#method.wrappingOffset) instead if these constraints are
 * difficult to satisfy. The only advantage of this method is that it
 * enables more aggressive compiler optimizations.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 * val ptr: MutPtr = localStorageL3C0.elementMutPtr(0)
 * run {
 *     check(2u == (PrimitiveLayouts.U32).readNative((MutPtr((ptr).rawAddress + ((1).toLong() * (PrimitiveLayouts.U32).strideBytes))).rawAddress))
 *     check(3u == (PrimitiveLayouts.U32).readNative((MutPtr((ptr).rawAddress + ((2).toLong() * (PrimitiveLayouts.U32).strideBytes))).rawAddress))
 * }
 * ```
 */
public fun <T> MutPtr.offset(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
    fun runtimeOffsetNowrap(thisRef: ConstPtr, count1: Int, size: Int): Boolean {
        // We can use constEvalSelect here because this is only for UB checks.
        return run {
            val byteOffset = count1.checkedMul(size) ?: return false
            val (_, overflow) = (thisRef).rawAddress.toInt().overflowingAddSigned(byteOffset)
            !overflow
        }
    }
    Unit
    // SAFETY: the caller must uphold the safety contract for `offset`.
    // The obtained pointer is valid for writes since the caller must
    // guarantee that it points to the same allocation as `self`.
    return MutPtr(this.rawAddress + (count).toLong() * 1L)
}

/**
 * Adds a signed offset in bytes to a pointer.
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
public fun MutPtr.byteOffset(count: Int): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `offset`.
    return MutPtr((MutPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((this).asConstPtr())
}

/**
 * Adds a signed offset to a pointer using wrapping arithmetic.
 *
 * `count` is in units of T; e.g., a `count` of 3 represents a pointer
 * offset of `3 * sizeOf<T>()` bytes.
 *
 * # Safety
 *
 * This operation itself is always safe, but using the resulting pointer is not.
 *
 * The resulting pointer "remembers" the [allocation] that `self` points to
 * (this is called "[Provenance](ptr/index.html#provenance)").
 * The pointer must not be used to read or write other allocations.
 *
 * In other words, `val z = x.wrappingOffset((y as Long) - (x as Long))` does *not* make `z`
 * the same as `y` even if we assume `T` has size `1` and there is no overflow: `z` is still
 * attached to the object `x` is attached to, and dereferencing it is Undefined Behavior unless
 * `x` and `y` point into the same allocation.
 *
 * Compared to [offset], this method basically delays the requirement of staying within the
 * same allocation: [offset] is immediate Undefined Behavior when crossing object
 * boundaries; `wrappingOffset` produces a pointer but still leads to Undefined Behavior if a
 * pointer is dereferenced when it is out-of-bounds of the object it is attached to. [offset]
 * can be optimized better and is thus preferable in performance-sensitive code.
 *
 * The delayed check only considers the value of the pointer that was dereferenced, not the
 * intermediate values used during the computation of the final result. For example,
 * `x.wrappingOffset(o).wrappingOffset(o.wrappingNeg())` is always the same as `x`. In other
 * words, leaving the allocation and then re-entering it later is permitted.
 *
 * [offset]: #method.offset
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * public fun doctestEntry() {
 *     // Iterate using a raw pointer in increments of two elements
 *     var data = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u)
 *     var ptr: MutPtr = data.asMutPtr()
 *     val step = 2
 *     val endRoundedUp = MutPtr((ptr).rawAddress + ((6).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     while (((ptr).rawAddress.toULong() != (endRoundedUp).rawAddress.toULong())) {
 *         run {
 *             (PrimitiveLayouts.U8).writeNative((ptr).rawAddress, 0)
 *         }
 *         ptr = MutPtr((ptr).rawAddress + ((step).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     }
 *     check(data == listOf(0, 2, 0, 4, 0))
 * }
 * ```
 */
public fun <T> MutPtr.wrappingOffset(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
    // SAFETY: the `arithOffset` intrinsic has no prerequisites to be called.
    return (MutPtr(this.rawAddress + (count).toLong() * 1L)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
}

/**
 * Adds a signed offset in bytes to a pointer using wrapping arithmetic.
 *
 * `count` is in units of **bytes**.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [wrappingOffset][pointer.wrappingOffset] on it. See that method
 * for documentation.
 *
 * For non-`Sized` pointees this operation changes only the data pointer,
 * leaving the metadata untouched.
 */
public fun MutPtr.wrappingByteOffset(count: Int): MutPtr = MutPtr((MutPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((this).asConstPtr())

/**
 * Masks out bits of the pointer according to a mask.
 *
 * This is convenience for `ptr.mapAddr(| a | a & mask)`.
 *
 * For non-`Sized` pointees this operation changes only the data pointer,
 * leaving the metadata untouched.
 *
 * ## Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(ptrMask)
 * public fun doctestEntry() {
 *     val localStorageL4C0 = AddressableLocal(17u, PrimitiveLayouts.U32)
 *     var v by localStorageL4C0
 *     val ptr: MutPtr = RustMutableReference(localStorageL4C0.asMutPtr(), localStorageL4C0.layout)
 *     // `UInt` is 4 bytes aligned,
 *     // which means that lower 2 bits are always 0.
 *     val tagMask = 0x3
 *     val ptrMask = !tagMask
 *     // We can store something in these lower bits
 *     val taggedPtr = ptr.mapAddr({ a -> a or 0x2 })
 *     // Get the "tag" back
 *     val tag = taggedPtr.addr() and tagMask
 *     check(tag == 0x2)
 *     // Note that `taggedPtr` is unaligned, it's UB to read from/write to it.
 *     // To get original pointer `mask` can be used:
 *     val maskedPtr = taggedPtr.mask(ptrMask)
 *     check(maskedPtr == 17)
 *     maskedPtr = 0
 *     check(v == 0u)
 * }
 * ```
 */
public fun MutPtr.mask(mask: Int): MutPtr = MutPtr((ConstPtr(MutPtr((this).rawAddress).rawAddress and (mask).toLong())).rawAddress).withMetadataOf((this).asConstPtr())

/**
 * Returns `None` if the pointer is null, or else returns a unique reference to
 * the value wrapped in `Some`. If the value may be uninitialized, [asUninitMut]
 * must be used instead. If the value is known to be non-null, [asMutUnchecked]
 * can be used instead.
 *
 * For the shared counterpart see [asRef].
 *
 * [asUninitMut]: #method.asUninitMut
 * [asMutUnchecked]: #method.asMutUnchecked
 * [asRef]: pointer#method.asRef-1
 *
 * # Safety
 *
 * When calling this method, you have to ensure that *either*
 * the pointer is null *or*
 * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
 *
 * # Panics during const evaluation
 *
 * This method will panic during const evaluation if the pointer cannot be
 * determined to be null or not. See [isNull] for more information.
 *
 * [isNull]: #method.isNull-1
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 * val ptr: MutPtr = localStorageL3C0.elementMutPtr(0)
 * val firstValue = ptr.asMut().unwrap()
 * firstValue = 4
 * println("$s")
 * // It'll print: "[4, 2, 3]".
 * ```
 *
 * # Null-unchecked version
 *
 * If you are sure the pointer can never be null, you can use `asMutUnchecked` which returns
 * `T` instead of `Option<T>`.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 * val ptr: MutPtr = localStorageL3C0.elementMutPtr(0)
 * val firstValue = ptr.asMutUnchecked()
 * firstValue = 4
 * println("$s")
 * // It'll print: "[4, 2, 3]".
 * ```
 */
public fun <T> MutPtr.asMut(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T? {
    // SAFETY: the caller must guarantee that `self` is be valid for
    // a mutable reference if it isn't null.
    return if (((this).rawAddress == 0L)) {
        null
    } else {
        RustMutableReference(MutPtr((this).rawAddress), typeLayoutT)
    }
}

/**
 * Returns a unique reference to the value behind the pointer.
 * If the pointer may be null or the value may be uninitialized, [asUninitMut] must be used instead.
 * If the pointer may be null, but the value is known to have been initialized, [asMut] must be used instead.
 *
 * For the shared counterpart see [asRefUnchecked].
 *
 * [asMut]: #method.asMut
 * [asUninitMut]: #method.asUninitMut
 * [asRefUnchecked]: #method.asRefUnchecked
 *
 * # Safety
 *
 * When calling this method, you have to ensure that
 * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 * val ptr: MutPtr = localStorageL3C0.elementMutPtr(0)
 * val firstValue = ptr.asMutUnchecked()
 * firstValue = 4
 * println("$s")
 * // It'll print: "[4, 2, 3]".
 * ```
 */
public fun <T> MutPtr.asMutUnchecked(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must guarantee that `self` is valid for a reference
    return RustMutableReference(MutPtr((this).rawAddress), typeLayoutT)
}

/**
 * Returns `None` if the pointer is null, or else returns a unique reference to
 * the value wrapped in `Some`. In contrast to [asMut], this does not require
 * that the value has to be initialized.
 *
 * For the shared counterpart see [asUninitRef].
 *
 * [asMut]: #method.asMut
 * [asUninitRef]: pointer#method.asUninitRef-1
 *
 * # Safety
 *
 * When calling this method, you have to ensure that *either* the pointer is null *or*
 * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
 * Note that because the created reference is to `MaybeUninit<T>`, the
 * source pointer can point to uninitialized memory.
 *
 * # Panics during const evaluation
 *
 * This method will panic during const evaluation if the pointer cannot be
 * determined to be null or not. See [isNull] for more information.
 *
 * [isNull]: #method.isNull-1
 */
public fun <T> MutPtr.asUninitMut(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T>? {
    // SAFETY: the caller must guarantee that `self` meets all the
    // requirements for a reference.
    return if (((this).rawAddress == 0L)) {
        null
    } else {
        RustMutableReference(MutPtr(((MutPtr(this.rawAddress, this.rawMeta))).rawAddress), UnresolvedTypeLayout<MaybeUninit<T>>())
    }
}

/**
 * Returns whether two pointers are guaranteed to be equal.
 *
 * At runtime this function behaves like `Some(self == other)`.
 * However, in some contexts (e.g., compile-time evaluation),
 * it is not always possible to determine equality of two pointers, so this function may
 * spuriously return `None` for pointers that later actually turn out to have its equality known.
 * But when it returns `Some`, the pointers' equality is guaranteed to be known.
 *
 * The return value may change from `Some` to `None` and vice versa depending on the compiler
 * version and unchecked code must not
 * rely on the result of this function for soundness. It is suggested to only use this function
 * for performance optimizations where spurious `None` return values by this function do not
 * affect the outcome, but just the performance.
 * The consequences of using this method to make runtime and compile-time code behave
 * differently have not been explored. This method should not be used to introduce such
 * differences, and it should also not be stabilized before we have a better understanding
 * of this issue.
 */
public fun MutPtr.guaranteedEq(other: MutPtr): Boolean? = (ConstPtr(this.rawAddress, this.rawMeta)).guaranteedEq(other)

/**
 * Returns whether two pointers are guaranteed to be inequal.
 *
 * At runtime this function behaves like `Some(self != other)`.
 * However, in some contexts (e.g., compile-time evaluation),
 * it is not always possible to determine inequality of two pointers, so this function may
 * spuriously return `None` for pointers that later actually turn out to have its inequality known.
 * But when it returns `Some`, the pointers' inequality is guaranteed to be known.
 *
 * The return value may change from `Some` to `None` and vice versa depending on the compiler
 * version and unchecked code must not
 * rely on the result of this function for soundness. It is suggested to only use this function
 * for performance optimizations where spurious `None` return values by this function do not
 * affect the outcome, but just the performance.
 * The consequences of using this method to make runtime and compile-time code behave
 * differently have not been explored. This method should not be used to introduce such
 * differences, and it should also not be stabilized before we have a better understanding
 * of this issue.
 */
public fun MutPtr.guaranteedNe(other: MutPtr): Boolean? = (ConstPtr(this.rawAddress, this.rawMeta)).guaranteedNe(other)

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
 * [offset]: pointer#method.offset-1
 *
 * # Safety
 *
 * If any of the following conditions are violated, the result is Undefined Behavior:
 *
 * * `self` and `origin` must either
 *
 *   * point to the same address, or
 *   * both be [derived from][ptr#provenance] a pointer to the same [allocation], and the memory range between
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
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * var a = MutableList(5) { 0 }
 * val ptr1: MutPtr = a[1]
 * val ptr2: MutPtr = a[3]
 * run {
 *     check((((ptr2).rawAddress - (ptr1).rawAddress) / (PrimitiveLayouts.I32).strideBytes).toInt() == 2)
 *     check((((ptr1).rawAddress - (ptr2).rawAddress) / (PrimitiveLayouts.I32).strideBytes).toInt() == -2)
 *     check(MutPtr((ptr1).rawAddress + ((2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr2)
 *     check(MutPtr((ptr2).rawAddress + ((-2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr1)
 * }
 * ```
 *
 * *Incorrect* usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val ptr1 = Holder.intoRaw(0u.toUByte())
 * val ptr2 = Holder.intoRaw(1u.toUByte())
 * val diff = ((ptr2.toInt()) - (ptr1.toInt()))
 * // Make ptr2Other an "alias" of ptr2.add(1), but derived from ptr1.
 * val ptr2Other = MutPtr((MutPtr(((mutPtrFromRustCast(ptr1))).rawAddress + ((diff).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
 * check(ptr2.toInt() == ptr2Other.rawAddress.toInt())
 * // Since ptr2Other and ptr2 are derived from pointers to different objects,
 * // computing their offset is undefined behavior, even though
 * // they point to addresses that are in-bounds of the same object!
 * run {
 *     val one = (((ptr2Other).rawAddress - (ptr2).rawAddress) / (PrimitiveLayouts.U8).strideBytes).toInt()
 *     // Undefined Behavior! ⚠️
 * }
 * ```
 */
public fun <T> MutPtr.offsetFrom(origin: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: the caller must uphold the safety contract for `offsetFrom`.
    return ((((ConstPtr(this.rawAddress, this.rawMeta))).rawAddress - (origin).rawAddress) / (typeLayoutT).strideBytes).toInt()
}

// even without panics, this helps for Miri backtraces

/**
 * Calculates the distance between two pointers within the same allocation. The returned value is in
 * units of **bytes**.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [`offsetFrom`][pointer.offsetFrom] on it.
 *
 * For non-`Sized` pointees this operation considers only the data pointers,
 * ignoring the metadata.
 *
 * # Safety
 *
 * See [`offsetFrom`][pointer.offsetFrom] for documentation and safety requirements.
 */
public fun MutPtr.byteOffsetFrom(origin: ConstPtr): Int {
    // SAFETY: the caller must uphold the safety contract for `offsetFrom`.
    return (((MutPtr((this).rawAddress)).rawAddress - (ConstPtr((origin).rawAddress)).rawAddress) / (PrimitiveLayouts.U8).strideBytes).toInt()
}

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
 * This method can be thought of as recovering the `count` that was passed
 * to [`add`](#method.add) (or, with the parameters in the other order,
 * to [`sub`](#method.sub)).  The following are all equivalent, assuming
 * that their safety preconditions are met:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * fun blah(ptr: MutPtr, origin: MutPtr, count: Int): Boolean {
 *     return ptr.offsetFromUnsigned((origin).asConstPtr()) == count && ((MutPtr((origin).rawAddress + ((count).toLong() * (PrimitiveLayouts.I32).strideBytes))).rawAddress.toULong() == (ptr).rawAddress.toULong()) && ((MutPtr((ptr).rawAddress - ((count).toLong() * (PrimitiveLayouts.I32).strideBytes))).rawAddress.toULong() == (origin).rawAddress.toULong())
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
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableArrayLocal.fromIntArray(IntArray(5) { 0 })
 * val p: MutPtr = localStorageL3C0.elementMutPtr(0)
 * run {
 *     val ptr1: MutPtr = MutPtr((p).rawAddress + ((1).toLong() * (PrimitiveLayouts.I32).strideBytes))
 *     val ptr2: MutPtr = MutPtr((p).rawAddress + ((3).toLong() * (PrimitiveLayouts.I32).strideBytes))
 *     check(ptr2.offsetFromUnsigned((ptr1).asConstPtr()) == 2)
 *     check(MutPtr((ptr1).rawAddress + ((2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr2)
 *     check(MutPtr((ptr2).rawAddress - ((2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr1)
 *     check(ptr2.offsetFromUnsigned((ptr2).asConstPtr()) == 0)
 * }
 * // This would be incorrect, as the pointers are not correctly ordered:
 * // ptr1.offsetFrom(ptr2)
 * ```
 */
public fun <T> MutPtr.offsetFromUnsigned(origin: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: the caller must uphold the safety contract for `offsetFromUnsigned`.
    return (ConstPtr(this.rawAddress, this.rawMeta)).offsetFromUnsigned(origin)
}

/**
 * Calculates the distance between two pointers within the same allocation, *where it's known that
 * `self` is equal to or greater than `origin`*. The returned value is in
 * units of **bytes**.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [`offsetFromUnsigned`][pointer.offsetFromUnsigned] on it.
 *
 * For non-`Sized` pointees this operation considers only the data pointers,
 * ignoring the metadata.
 *
 * # Safety
 *
 * See [`offsetFromUnsigned`][pointer.offsetFromUnsigned] for documentation and safety requirements.
 */
public fun MutPtr.byteOffsetFromUnsigned(origin: MutPtr): Int {
    // SAFETY: the caller must uphold the safety contract for `byteOffsetFromUnsigned`.
    return (ConstPtr(this.rawAddress, this.rawMeta)).byteOffsetFromUnsigned((origin).asConstPtr())
}

/**
 * Consider using [`wrappingAdd`](#method.wrappingAdd) instead if these constraints are
 * difficult to satisfy. The only advantage of this method is that it
 * enables more aggressive compiler optimizations.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * var s: String = "123".toString()
 * val ptr: MutPtr = s.asMutPtr()
 * run {
 *     check('2' == (PrimitiveLayouts.U8).readNative((MutPtr((ptr).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).toInt().toChar())
 *     check('3' == (PrimitiveLayouts.U8).readNative((MutPtr((ptr).rawAddress + ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).toInt().toChar())
 * }
 * ```
 */
public fun <T> MutPtr.add(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
    fun runtimeAddNowrap(thisRef: ConstPtr, count1: Int, size: Int): Boolean {
        return run {
            val byteOffset = count1.checkedMul(size) ?: return false
            val (_, overflow) = (thisRef).rawAddress.toInt().overflowingAdd(byteOffset)
            byteOffset <= (Int.MAX_VALUE) && !overflow
        }
    }
    Unit
    // SAFETY: the caller must uphold the safety contract for `offset`.
    return MutPtr(this.rawAddress + (count).toLong() * 1L)
}

/**
 * Adds an unsigned offset in bytes to a pointer.
 *
 * `count` is in units of bytes.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [add][pointer.add] on it.
 *
 * For non-`Sized` pointees this operation changes only the data pointer,
 * leaving the metadata untouched.
 *
 * # Safety
 *
 * See [`add`][pointer.add] for documentation and safety requirements.
 */
public fun MutPtr.byteAdd(count: Int): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `add`.
    return MutPtr((MutPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((this).asConstPtr())
}

/**
 * Consider using [`wrappingSub`](#method.wrappingSub) instead if these constraints are
 * difficult to satisfy. The only advantage of this method is that it
 * enables more aggressive compiler optimizations.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val s: String = "123"
 * run {
 *     val end: ConstPtr = s.asPtr().add(3)
 *     check('3' == (PrimitiveLayouts.U8).readNative((ConstPtr((end).rawAddress - ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).toInt().toChar())
 *     check('2' == (PrimitiveLayouts.U8).readNative((ConstPtr((end).rawAddress - ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).toInt().toChar())
 * }
 * ```
 */
public fun <T> MutPtr.sub(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
    fun runtimeSubNowrap(thisRef: ConstPtr, count1: Int, size: Int): Boolean {
        return run {
            val byteOffset = count1.checkedMul(size) ?: return false
            byteOffset <= (Int.MAX_VALUE) && (thisRef).rawAddress.toInt() >= byteOffset
        }
    }
    Unit
    return if (((typeLayoutT).sizeBytes == 0L)) {
        // Pointer arithmetic does nothing when the pointee is a ZST.
        this
    } else {
        // SAFETY: the caller must uphold the safety contract for `offset`.
        // Because the pointee is *not* a ZST, that means that `count` is
        // at most `Long.MAX`, and thus the negation cannot overflow.
        MutPtr(this.rawAddress + (((0) - (count))).toLong() * 1L)
    }
}

/**
 * Subtracts an unsigned offset in bytes from a pointer.
 *
 * `count` is in units of bytes.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [sub][pointer.sub] on it.
 *
 * For non-`Sized` pointees this operation changes only the data pointer,
 * leaving the metadata untouched.
 *
 * # Safety
 *
 * See [`sub`][pointer.sub] for documentation and safety requirements.
 */
public fun MutPtr.byteSub(count: Int): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `sub`.
    return MutPtr((MutPtr((this).rawAddress)).rawAddress - ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((this).asConstPtr())
}

/**
 * Adds an unsigned offset to a pointer using wrapping arithmetic.
 *
 * `count` is in units of T; e.g., a `count` of 3 represents a pointer
 * offset of `3 * sizeOf<T>()` bytes.
 *
 * # Safety
 *
 * This operation itself is always safe, but using the resulting pointer is not.
 *
 * The resulting pointer "remembers" the [allocation] that `self` points to; it must not
 * be used to read or write other allocations.
 *
 * In other words, `val z = x.wrappingAdd((y as Int) - (x as Int))` does *not* make `z`
 * the same as `y` even if we assume `T` has size `1` and there is no overflow: `z` is still
 * attached to the object `x` is attached to, and dereferencing it is Undefined Behavior unless
 * `x` and `y` point into the same allocation.
 *
 * Compared to [add], this method basically delays the requirement of staying within the
 * same allocation: [add] is immediate Undefined Behavior when crossing object
 * boundaries; `wrappingAdd` produces a pointer but still leads to Undefined Behavior if a
 * pointer is dereferenced when it is out-of-bounds of the object it is attached to. [add]
 * can be optimized better and is thus preferable in performance-sensitive code.
 *
 * The delayed check only considers the value of the pointer that was dereferenced, not the
 * intermediate values used during the computation of the final result. For example,
 * `x.wrappingAdd(o).wrappingSub(o)` is always the same as `x`. In other words, leaving the
 * allocation and then re-entering it later is permitted.
 *
 * [add]: #method.add
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * public fun doctestEntry() {
 *     // Iterate using a raw pointer in increments of two elements
 *     val data = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u)
 *     var ptr: ConstPtr = data.asPtr()
 *     val step = 2
 *     val endRoundedUp = ConstPtr((ptr).rawAddress + ((6).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     // This loop prints "1, 3, 5, "
 *     while (((ptr).rawAddress.toULong() != (endRoundedUp).rawAddress.toULong())) {
 *         run {
 *             print("${(PrimitiveLayouts.U8).readNative((ptr).rawAddress)}, ")
 *         }
 *         ptr = ConstPtr((ptr).rawAddress + ((step).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     }
 * }
 * ```
 */
public fun <T> MutPtr.wrappingAdd(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr = MutPtr((this).rawAddress + ((count).toLong() * (typeLayoutT).strideBytes))

/**
 * Adds an unsigned offset in bytes to a pointer using wrapping arithmetic.
 *
 * `count` is in units of bytes.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [wrappingAdd][pointer.wrappingAdd] on it. See that method for documentation.
 *
 * For non-`Sized` pointees this operation changes only the data pointer,
 * leaving the metadata untouched.
 */
public fun MutPtr.wrappingByteAdd(count: Int): MutPtr = MutPtr((MutPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((this).asConstPtr())

/**
 * Subtracts an unsigned offset from a pointer using wrapping arithmetic.
 *
 * `count` is in units of T; e.g., a `count` of 3 represents a pointer
 * offset of `3 * sizeOf<T>()` bytes.
 *
 * # Safety
 *
 * This operation itself is always safe, but using the resulting pointer is not.
 *
 * The resulting pointer "remembers" the [allocation] that `self` points to; it must not
 * be used to read or write other allocations.
 *
 * In other words, `val z = x.wrappingSub((x as Int) - (y as Int))` does *not* make `z`
 * the same as `y` even if we assume `T` has size `1` and there is no overflow: `z` is still
 * attached to the object `x` is attached to, and dereferencing it is Undefined Behavior unless
 * `x` and `y` point into the same allocation.
 *
 * Compared to [sub], this method basically delays the requirement of staying within the
 * same allocation: [sub] is immediate Undefined Behavior when crossing object
 * boundaries; `wrappingSub` produces a pointer but still leads to Undefined Behavior if a
 * pointer is dereferenced when it is out-of-bounds of the object it is attached to. [sub]
 * can be optimized better and is thus preferable in performance-sensitive code.
 *
 * The delayed check only considers the value of the pointer that was dereferenced, not the
 * intermediate values used during the computation of the final result. For example,
 * `x.wrappingAdd(o).wrappingSub(o)` is always the same as `x`. In other words, leaving the
 * allocation and then re-entering it later is permitted.
 *
 * [sub]: #method.sub
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * public fun doctestEntry() {
 *     // Iterate using a raw pointer in increments of two elements (backwards)
 *     val data = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u)
 *     var ptr: ConstPtr = data.asPtr()
 *     val startRoundedDown = ConstPtr((ptr).rawAddress - ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     ptr = ConstPtr((ptr).rawAddress + ((4).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     val step = 2
 *     // This loop prints "5, 3, 1, "
 *     while (((ptr).rawAddress.toULong() != (startRoundedDown).rawAddress.toULong())) {
 *         run {
 *             print("${(PrimitiveLayouts.U8).readNative((ptr).rawAddress)}, ")
 *         }
 *         ptr = ConstPtr((ptr).rawAddress - ((step).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     }
 * }
 * ```
 */
public fun <T> MutPtr.wrappingSub(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr = MutPtr((this).rawAddress + (((count).wrappingNeg()).toLong() * (typeLayoutT).strideBytes))

/**
 * Subtracts an unsigned offset in bytes from a pointer using wrapping arithmetic.
 *
 * `count` is in units of bytes.
 *
 * This is purely a convenience for casting to a `UByte` pointer and
 * using [wrappingSub][pointer.wrappingSub] on it. See that method for documentation.
 *
 * For non-`Sized` pointees this operation changes only the data pointer,
 * leaving the metadata untouched.
 */
public fun MutPtr.wrappingByteSub(count: Int): MutPtr = MutPtr((MutPtr((this).rawAddress)).rawAddress - ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf((this).asConstPtr())

/**
 * Reads the value from `self` without moving it. This leaves the
 * memory in `self` unchanged.
 *
 * # Safety
 *
 * See [ptr.read] for safety concerns and examples.
 */
public fun <T> MutPtr.read(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for .
    return read((this).asConstPtr())
}

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
public fun <T> MutPtr.readVolatile(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `readVolatile`.
    return readVolatile((this).asConstPtr())
}

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
public fun <T> MutPtr.readUnaligned(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `readUnaligned`.
    return readUnaligned((this).asConstPtr())
}

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
public fun <T> MutPtr.copyTo(dest: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `copy`.
    Copy((this).asConstPtr(), dest, count)
}

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
public fun <T> MutPtr.copyToNonoverlapping(dest: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `copyNonoverlapping`.
    copyNonoverlapping((this).asConstPtr(), dest, count)
}

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
public fun <T> MutPtr.copyFrom(src: ConstPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `copy`.
    Copy(src, this, count, typeLayoutT = typeLayoutT)
}

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
public fun <T> MutPtr.copyFromNonoverlapping(src: ConstPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `copyNonoverlapping`.
    copyNonoverlapping(src, this, count, typeLayoutT = typeLayoutT)
}

/**
 * Executes the destructor (if any) of the pointed-to value.
 *
 * # Safety
 *
 * See [ptr.dropInPlace] for safety concerns and examples.
 */
public fun MutPtr.dropInPlace() {
    // SAFETY: the caller must uphold the safety contract for `dropInPlace`.
    dropInPlace(this)
}

/**
 * Overwrites a memory location with the given value without reading or
 * dropping the old value.
 *
 * # Safety
 *
 * See [ptr.write] for safety concerns and examples.
 */
public fun <T> MutPtr.write(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `write`.
    Write(this, value)
}

/**
 * Invokes memset on the specified pointer, setting `count * sizeOf<T>()`
 * bytes of memory starting at `self` to `val`.
 *
 * # Safety
 *
 * See [ptr.writeBytes] for safety concerns and examples.
 */
public fun <T> MutPtr.writeBytes(value: UByte, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `writeBytes`.
    writeBytes(this, value, count)
}

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
public fun <T> MutPtr.writeVolatile(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `writeVolatile`.
    writeVolatile(this, value)
}

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
public fun <T> MutPtr.writeUnaligned(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `writeUnaligned`.
    writeUnaligned(this, value)
}

/**
 * Replaces the value at `self` with `src`, returning the old
 * value, without dropping either.
 *
 * # Safety
 *
 * See [ptr.replace] for safety concerns and examples.
 */
public fun <T> MutPtr.replace(src: T): T {
    // SAFETY: the caller must uphold the safety contract for `replace`.
    return replace(this, src)
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
public fun MutPtr.swap(with: MutPtr) {
    // SAFETY: the caller must uphold the safety contract for `swap`.
    swap(this, with)
}

/**
 * Computes the offset that needs to be applied to the pointer in order to make it aligned to
 * `align`.
 *
 * If it is not possible to align the pointer, the implementation returns
 * `Int.MAX`.
 *
 * The offset is expressed in number of `T` elements, and not bytes. The value returned can be
 * used with the `wrappingAdd` method.
 *
 * There are no guarantees whatsoever that offsetting the pointer will not overflow or go
 * beyond the allocation that the pointer points into. It is up to the caller to ensure that
 * the returned offset is correct in all terms other than alignment.
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
 *
 * public fun doctestEntry() {
 *     run {
 *         var x = ubyteArrayOf(5u.toUByte(), 6u, 7u, 8u, 9u)
 *         val ptr = x.asMutPtr()
 *         val offset = ptr.alignOffset(alignOf<UShort>(typeLayoutT = PrimitiveLayouts.U16))
 *         if (offset < x.size - 1) {
 *             val u16Ptr = MutPtr((MutPtr((ptr).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
 *             (PrimitiveLayouts.U16).writeNative((u16Ptr).rawAddress, 0)
 *             check(x.contentEquals(listOf(0, 0, 7, 8, 9)) || x.contentEquals(listOf(5, 0, 0, 8, 9)))
 *         } else {
 *             // while the pointer can be aligned via `offset`, it would point
 *             // outside the allocation
 *         }
 *     }
 * }
 * ```
 */
public fun <T> MutPtr.alignOffset(align: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    if (!align.isPowerOfTwo()) {
        error("align_offset: align is not a power-of-two")
    }
    // SAFETY: `align` has been checked to be a power of 2 above
    val ret = alignOffset((this).asConstPtr(), align)
    return ret
}

/**
 * Returns whether the pointer is properly aligned for `T`.
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
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
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
 * val localStorageL7C0 = AddressableLocal(AlignedI32(42, fieldLayout = PrimitiveLayouts.I32), AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> AlignedI32(ptr, layout) }, { it.value }), 4L))
 * val ptr = MutPtr(localStorageL7C0.asMutPtr().rawAddress)
 * check(ptr.isAligned())
 * check(!MutPtr((ptr).rawAddress + ((1).toLong())).isAligned())
 * ```
 */
public fun <T> MutPtr.isAligned(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = this.isAlignedTo(alignOf<T>(typeLayoutT = typeLayoutT))

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
 *     val ptr = MutPtr(localStorageL9C0.asMutPtr().rawAddress)
 *     check(ptr.isAlignedTo(1))
 *     check(ptr.isAlignedTo(2))
 *     check(ptr.isAlignedTo(4))
 *     check(MutPtr((ptr).rawAddress + ((2).toLong())).isAlignedTo(2))
 *     check(!MutPtr((ptr).rawAddress + ((2).toLong())).isAlignedTo(4))
 *     check(ptr.isAlignedTo(8) != MutPtr((ptr).rawAddress + ((1).toLong() * (AlignedTypeLayout(TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> AlignedI32(ptr, layout) }, { it.value }), 4L)).strideBytes)).isAlignedTo(8))
 * }
 * ```
 */
public fun MutPtr.isAlignedTo(align: Int): Boolean {
    if (!align.isPowerOfTwo()) {
        error("is_aligned_to: align is not a power-of-two")
    }
    return (this).rawAddress.toInt() and (align - 1) == 0
}

/**
 * Casts from a type to its maybe-uninitialized version.
 *
 * This is always safe, since UB can only occur if the pointer is read
 * before being initialized.
 */
public fun MutPtr.castUninit(): MutPtr = this

/**
 * Forms a raw mutable slice from a pointer and a length.
 *
 * The `len` argument is the number of **elements**, not the number of bytes.
 *
 * Performs the same functionality as [castSlice] on a `* const T`, except that a
 * raw mutable slice is returned, as opposed to a raw immutable slice.
 *
 * See [ptr.sliceFromRawParts] for more information.
 *
 * [castSlice]: pointer.castSlice
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(5, 6, 7)
 * val rawMutSlice = x.asMutPtr().castSlice(3)
 * run {
 *     rawMutSlice[2] = 99
 *     // assign a value at an index in the slice
 * }
 * check(rawMutSlice[2] == 99)
 * ```
 *
 * You must ensure that the pointer is valid and not null before dereferencing
 * the raw slice. A slice reference must never have a null pointer, even if it's empty.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * val danger: MutPtr = MutPtr(0L).castSlice(0)
 * run {
 *     danger.asMut().expect("references must not be null")
 * }
 * ```
 */
public fun MutPtr.castSlice(len: Int): MutPtr = sliceFromRawPartsMut(this, len)

/**
 * Casts from a maybe-uninitialized type to its initialized version.
 *
 * This is always safe, since UB can only occur if the pointer is read
 * before being initialized.
 */
public fun MutPtr.castInit(): MutPtr = this

/**
 * Returns the length of a raw slice.
 *
 * The returned value is the number of **elements**, not the number of bytes.
 *
 * This function is safe, even when the raw slice cannot be cast to a slice
 * reference because the pointer is null or unaligned.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * val slice: MutPtr = ptr.sliceFromRawPartsMut(MutPtr(0L), 3)
 * check(slice.len() == 3)
 * ```
 */
public fun MutPtr.len(): Int = metadata(this)

/**
 * Returns `true` if the raw slice has a length of 0.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * val slice: MutPtr = ptr.sliceFromRawPartsMut(MutPtr(0L), 3)
 * check(!slice.isEmpty())
 * ```
 */
public fun MutPtr.isEmpty(): Boolean = this.len() == 0

/**
 * Gets a raw, mutable pointer to the underlying array.
 *
 * If `N` is not exactly equal to the length of `self`, then this method returns `None`.
 */
public fun MutPtr.asMutArray(n: Int = 0): MutPtr? =
    if (this.len() == n) {
        val me = mutPtrFromRustCast(this.asMutPtr())
        me
    } else {
        null
    }

/**
 * Divides one mutable raw slice into two at an index.
 *
 * The first will contain all indices from `[0, mid)` (excluding
 * the index `mid` itself) and the second will contain all
 * indices from `[mid, len)` (excluding the index `len` itself).
 *
 * # Panics
 *
 * Panics if `mid> len`.
 *
 * # Safety
 *
 * `mid` must be [in-bounds] of the underlying [allocation].
 * Which means `self` must be dereferenceable and span a single allocation
 * that is at least `mid * sizeOf<T>()` bytes long. Not upholding these
 * requirements is *[undefined behavior]* even if the resulting pointers are not used.
 *
 * Since `len` being in-bounds is not a safety invariant of `* [T]` the
 * safety requirements of this method are the same as for [splitAtMutUnchecked].
 * The explicit bounds check is only as useful as `len` is correct.
 *
 * [splitAtMutUnchecked]: #method.splitAtMutUnchecked
 * [in-bounds]: #method.add
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(rawSliceSplit)
 * public fun doctestEntry() {
 *     val localStorageL5C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 0, 3, 0, 5, 6))
 *     val ptr = MutPtr(localStorageL5C0.elementMutPtr(0).rawAddress)
 *     run {
 *         val (left, right) = ptr.splitAtMut(2)
 *         check(left == listOf(1, 0))
 *         check(right == listOf(3, 0, 5, 6))
 *     }
 * }
 * ```
 */
public fun MutPtr.splitAtMut(mid: Int): Pair<MutPtr, MutPtr> {
    check(mid <= this.len())
    // SAFETY: The assert above is only a safety-net as long as `self.len()` is correct
    // The actual safety requirements of this function are the same as for `splitAtMutUnchecked`
    return this.splitAtMutUnchecked(mid)
}

/**
 * Divides one mutable raw slice into two at an index, without doing bounds checking.
 *
 * The first will contain all indices from `[0, mid)` (excluding
 * the index `mid` itself) and the second will contain all
 * indices from `[mid, len)` (excluding the index `len` itself).
 *
 * # Safety
 *
 * `mid` must be [in-bounds] of the underlying [allocation].
 * Which means `self` must be dereferenceable and span a single allocation
 * that is at least `mid * sizeOf<T>()` bytes long. Not upholding these
 * requirements is *[undefined behavior]* even if the resulting pointers are not used.
 *
 * [in-bounds]: #method.add
 * [out-of-bounds index]: #method.add
 * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(rawSliceSplit)
 * public fun doctestEntry() {
 *     val localStorageL5C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 0, 3, 0, 5, 6))
 *     var v by localStorageL5C0
 *     // scoped to restrict the scope of the borrows
 *     run {
 *         val ptr = MutPtr(localStorageL5C0.elementMutPtr(0).rawAddress)
 *         val (left, right) = ptr.splitAtMutUnchecked(2)
 *         check(left == listOf(1, 0))
 *         check(right == listOf(3, 0, 5, 6))
 *         (left)[1] = 2
 *         (right)[1] = 4
 *     }
 *     check(v == intArrayOf(1, 2, 3, 4, 5, 6))
 * }
 * ```
 */
public fun MutPtr.splitAtMutUnchecked(mid: Int): Pair<MutPtr, MutPtr> {
    val len = this.len()
    val ptr = this.asMutPtr()
    // SAFETY: Caller must pass a valid pointer and an index that is in-bounds.
    val tail = ptr.add(mid)
    return Pair(sliceFromRawPartsMut(ptr, mid), sliceFromRawPartsMut(tail, len - mid))
}

/**
 * Returns a raw pointer to the slice's buffer.
 *
 * This is equivalent to casting `self` to `* T`, but more type-safe.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val slice: MutPtr = ptr.sliceFromRawPartsMut(MutPtr(0L), 3)
 *     check(slice.asMutPtr() == MutPtr(0L))
 * }
 * ```
 */
public fun MutPtr.asMutPtr(): MutPtr = MutPtr(this.rawAddress, this.rawMeta)

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
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val x = mutPtrFromRustCast(listOf(1, 2, 4))
 *     run {
 *         check(x.getUncheckedMut(1) == x.asMutPtr().add(1))
 *     }
 * }
 * ```
 */
public fun <Output, T, I> MutPtr.getUncheckedMut(index: I): MutPtr where I : SliceIndex<List<T>, Output> {
    // SAFETY: the caller ensures that `self` is dereferenceable and `index` in-bounds.
    return index.getUncheckedMut(this)
}

/**
 * # See Also
 * For the mutable counterpart see [`asUninitSliceMut`](pointer.asUninitSliceMut).
 */
public fun <T> MutPtr.asUninitSlice(): List<MaybeUninit<T>>? =
    if (((this).rawAddress == 0L)) {
        null
    } else {
        // SAFETY: the caller must uphold the safety contract for `asUninitSlice`.
        linearSliceView(ConstPtr(this.rawAddress, this.rawMeta), this.len().toInt(), UnresolvedTypeLayout<MaybeUninit<T>>())
    }

/**
 * Returns `None` if the pointer is null, or else returns a unique slice to
 * the value wrapped in `Some`. In contrast to [asMut], this does not require
 * that the value has to be initialized.
 *
 * For the shared counterpart see [asUninitSlice].
 *
 * [asMut]: #method.asMut
 * [asUninitSlice]: #method.asUninitSlice-1
 *
 * # Safety
 *
 * When calling this method, you have to ensure that *either* the pointer is null *or*
 * all of the following is true:
 *
 * * The pointer must be [valid] for reads and writes for `ptr.len() * sizeOf<T>()`
 *   many bytes, and it must be properly aligned. This means in particular:
 *
 *     * The entire memory range of this slice must be contained within a single [allocation]!
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
 * See also [`slice.fromRawPartsMut`][].
 *
 * # Panics during const evaluation
 *
 * This method will panic during const evaluation if the pointer cannot be
 * determined to be null or not. See [isNull] for more information.
 *
 * [isNull]: #method.isNull-1
 */
public fun <T> MutPtr.asUninitSliceMut(): MutableList<MaybeUninit<T>>? =
    if (((this).rawAddress == 0L)) {
        null
    } else {
        // SAFETY: the caller must uphold the safety contract for `asUninitSliceMut`.
        io.github.kotlinmania.rustlang.core.slice.raw.fromRawPartsMut(MutPtr(this.rawAddress, this.rawMeta), this.len())
    }

/**
 * Casts from a pointer-to-`T` to a pointer-to-`[T; N]`.
 */
public fun MutPtr.castArray(n: Int = 0): MutPtr = MutPtr((this).rawAddress)

/**
 * Returns a raw pointer to the array's buffer.
 *
 * This is equivalent to casting `self` to `* T`, but more type-safe.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * @feature(arrayPtrGet)
 * public fun doctestEntry() {
 *     val arr: MutPtr = MutPtr(0L)
 *     check(arr.asMutPtr() == MutPtr(0L))
 * }
 * ```
 */
public fun MutPtr.asMutPtr(n: Int = 0): MutPtr = MutPtr(this.rawAddress, this.rawMeta)

/**
 * Returns a raw pointer to a mutable slice containing the entire array.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * @feature(arrayPtrGet)
 * public fun doctestEntry() {
 *     var arr = mutableListOf(1, 2, 5)
 *     val ptr: MutPtr = arr
 *     run {
 *         run { val dstTmp0 = (ptr.asMutSlice()); val srcTmp0 = listOf(3, 4); for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
 *     }
 *     check(arr == listOf(3, 4, 5))
 * }
 * ```
 */
public fun MutPtr.asMutSlice(n: Int = 0): MutPtr = this

/**
 * For thin pointers, equality is by address, as produced by the [`mut.T.addr`](pointer.addr) method.
 */
public fun <T> MutPtr.eq(other: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (typeLayoutT).readNative((this).rawAddress) == (typeLayoutT).readNative((other).rawAddress)

/**
 * Pointer equality is an equivalence relation.
 */

/**
 * For thin pointers, comparison is by address, as produced by the [`mut.T.addr`](pointer.addr) method.
 */
public fun MutPtr.cmp(other: MutPtr): Ordering =
    if (((this).rawAddress.toULong() < (other).rawAddress.toULong())) {
        Less
    } else if (((this).rawAddress.toULong() == (other).rawAddress.toULong())) {
        Equal
    } else {
        Greater
    }

/**
 * For thin pointers, comparison is by address, as produced by the [`mut.T.addr`](pointer.addr) method.
 */
public fun MutPtr.partialCmp(other: MutPtr): Ordering? = this.cmp(other)

public fun <T> MutPtr.lt(other: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) < 0)

public fun <T> MutPtr.le(other: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) <= 0)

public fun <T> MutPtr.gt(other: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) > 0)

public fun <T> MutPtr.ge(other: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) >= 0)

/**
 * Returns the default value of [`nullMut`][ptr.nullMut].
 */
public fun defaultMutPtrT(): MutPtr = MutPtr(0L)
