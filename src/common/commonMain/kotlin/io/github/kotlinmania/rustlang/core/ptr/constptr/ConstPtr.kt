// port-lint: source ptr/const_ptr.rs
package io.github.kotlinmania.rustlang.core.ptr.constptr

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.intrinsics.alignOf
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.marker.Copy
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.wrappingNeg
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.alignOffset
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.metadata.fromRawParts
import io.github.kotlinmania.rustlang.core.ptr.metadata.metadata
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.addr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asUninitRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asUninitSlice
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteOffset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteOffsetFrom
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteOffsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteSub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castInit
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castSlice
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castUninit
import io.github.kotlinmania.rustlang.core.ptr.mutptr.copyTo
import io.github.kotlinmania.rustlang.core.ptr.mutptr.copyToNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.exposeProvenance
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isAligned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isAlignedTo
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.mapAddr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.mask
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offsetFrom
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offsetFromUnsigned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.toRawParts
import io.github.kotlinmania.rustlang.core.ptr.mutptr.withAddr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.withMetadataOf
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingByteAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingByteOffset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingByteSub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingOffset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingSub
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.readVolatile
import io.github.kotlinmania.rustlang.core.ptr.sliceFromRawParts
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex

/**
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val s: String = "Follow the rabbit"
 * val ptr: ConstPtr = s.asPtr()
 * check(!((ptr).rawAddress == 0L))
 * ```
 */
public fun ConstPtr.isNull(): Boolean {
    // Compare via a cast to a thin pointer, so fat pointers are only
    // considering their "data" part for null-ness.
    val ptr = ConstPtr(this.rawAddress, this.rawMeta)
    return (ptr).rawAddress.toInt() == 0
}

/**
 * Casts to a pointer of another type.
 */
public fun ConstPtr.cast(): ConstPtr = this

/**
 * Try to cast to a pointer of another type by checking alignment.
 *
 * If the pointer is properly aligned to the target type, it will be
 * cast to the target type. Otherwise, `None` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val x = 0uL
 * val aligned: ConstPtr = x
 * val unaligned = ConstPtr((aligned).rawAddress + ((1).toLong()))
 * check(aligned.tryCastAligned<UInt>() != null)
 * check(unaligned.tryCastAligned<UInt>() == null)
 * ```
 */
public fun <U> ConstPtr.tryCastAligned(typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): ConstPtr? =
    if (this.isAlignedTo(alignOf<U>(typeLayoutT = typeLayoutU))) {
        ConstPtr((this).rawAddress)
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(setPtrValue)
 * public fun doctestEntry() {
 *     val localStorageL4C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3))
 *     var ptr = (localStorageL4C0.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     val thin = ConstPtr(ptr.rawAddress, ptr.rawMeta)
 *     run {
 *         ptr = ConstPtr((thin).rawAddress + ((8).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(ptr)
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(setPtrValue)
 * public fun doctestEntry() {
 *     val localStorageL4C0 = AddressableLocal(0u, PrimitiveLayouts.U32)
 *     val x by localStorageL4C0
 *     val localStorageL5C0 = AddressableLocal(1u, PrimitiveLayouts.U32)
 *     val y by localStorageL5C0
 *     val x1 = ConstPtr(localStorageL4C0.asMutPtr().rawAddress)
 *     val y1 = ConstPtr(localStorageL5C0.asMutPtr().rawAddress)
 *     val offset = (x1.rawAddress.toInt() - y1.rawAddress.toInt()) / 4
 *     val bad = ConstPtr((x1).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U32).strideBytes)).withMetadataOf(y1)
 *     // This dereference is UB. The pointer only has provenance for `x` but points to `y`.
 *     println("$bad")
 * }
 * ```
 */
public fun ConstPtr.withMetadataOf(meta: ConstPtr): ConstPtr = linearSliceView(ConstPtr(this.rawAddress, this.rawMeta), metadata(meta).toInt(), PrimitiveLayouts.UNIT)

/**
 * Changes constness without changing the type.
 *
 * This is a bit safer than `as` because it wouldn't silently change the type if the code is
 * refactored.
 */
public fun ConstPtr.castMut(): MutPtr = this

public fun ConstPtr.addr(): Int {
    // A pointer-to-integer reinterpret currently has exactly the right semantics: it returns the
    // address without exposing the provenance. Note that this is *not* a stable guarantee about
    // reinterpret semantics, it relies on sysroot crates having special status.
    // SAFETY: Pointer-to-integer transmutes are valid (if you are okay with losing the
    // provenance).
    return transmuteWithLayouts<MutPtr, Int>(ConstPtr((this).rawAddress), PrimitiveLayouts.mutPtrLayout(64L), PrimitiveLayouts.USIZE64)
}

/**
 * Exposes the ["provenance"][ptr#provenance] part of the pointer for future use in
 * [withExposedProvenance] and returns the "address" portion.
 *
 * This is equivalent to `self as Int`, which semantically discards provenance information.
 * Furthermore, this (like the `as` cast) has the implicit side-effect of marking the
 * provenance as `exposed`, so on platforms that support it you can later call
 * [withExposedProvenance] to reconstitute the original pointer including its provenance.
 *
 * Due to its inherent ambiguity, [withExposedProvenance] may not be supported by tools
 * that help you to stay conformant with the Rust memory model. It is recommended to use
 * [Strict Provenance][ptr#strict-provenance] APIs such as [`withAddr`][pointer.withAddr]
 * wherever possible, in which case [`addr`][pointer.addr] should be used instead of `exposeProvenance`.
 *
 * On most platforms this will produce a value with the same bytes as the original pointer,
 * because all the bytes are dedicated to describing the address. Platforms which need to store
 * additional information in the pointer may not support this operation, since the `expose`
 * side-effect which is required for [withExposedProvenance] to work is typically not
 * available.
 *
 * This is an [Exposed Provenance][ptr#exposed-provenance] API.
 *
 * [withExposedProvenance]: withExposedProvenance
 */
public fun ConstPtr.exposeProvenance(): Int = ConstPtr((this).rawAddress).rawAddress.toInt()

/**
 * Creates a new pointer with the given address and the [provenance][ptr#provenance] of
 * `self`.
 *
 * This is similar to a `addr as * const T` cast, but copies
 * the *provenance* of `self` to the new pointer.
 * This avoids the inherent ambiguity of the unary cast.
 *
 * This is equivalent to using [`wrappingOffset`][pointer.wrappingOffset] to offset
 * `self` to the given address, and therefore has all the same capabilities and restrictions.
 *
 * This is a [Strict Provenance][ptr#strict-provenance] API.
 */
public fun ConstPtr.withAddr(addr: Int): ConstPtr {
    // This should probably be an intrinsic to avoid doing any sort of arithmetic, but
    // meanwhile, we can implement it with `wrappingOffset`, which preserves the pointer's
    // provenance.
    val selfAddr = (this).rawAddress.toInt()
    val destAddr = addr
    val offset = (destAddr - (selfAddr.toInt()))
    return ConstPtr((this).rawAddress + ((offset).toLong()))
}

/**
 * Creates a new pointer by mapping `self`s address to a new one, preserving the
 * [provenance][ptr#provenance] of `self`.
 *
 * This is a convenience for [`withAddr`][pointer.withAddr], see that method for details.
 *
 * This is a [Strict Provenance][ptr#strict-provenance] API.
 */
public fun ConstPtr.mapAddr(f: (Int) -> Int): ConstPtr = ConstPtr((f((this).rawAddress.toInt())).toLong())

/**
 * Decompose a (possibly wide) pointer into its data pointer and metadata components.
 *
 * The pointer can be later reconstructed with [fromRawParts].
 */
public fun ConstPtr.toRawParts(): Pair<ConstPtr, Metadata> = Pair(this, metadata(this))

/**
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val ptr: ConstPtr = constPtrFromRustCast(10u.toUByte())
 * run {
 *     val valBack = ptr.asRefUnchecked()
 *     check(valBack == 10)
 * }
 * ```
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val ptr: ConstPtr = constPtrFromRustCast(10u.toUByte())
 * when (val matchVal = ptr.asRef()) {
 *     null -> {}
 *     else -> {
 *         val valBack = matchVal
 *         check(valBack == 10)
 *     }
 * }
 * ```
 *
 * [isNull]: #method.isNull
 * [asUninitRef]: #method.asUninitRef
 * [asRefUnchecked]: #method.asRefUnchecked
 */
public fun <T> ConstPtr.asRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T? {
    // SAFETY: the caller must guarantee that `self` is valid
    // for a reference if it isn't null.
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
 * [asRef]: #method.asRef
 * [asUninitRef]: #method.asUninitRef
 *
 * # Safety
 *
 * When calling this method, you have to ensure that
 * the pointer is [convertible to a reference](ptr#pointer-to-reference-conversion).
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val ptr: ConstPtr = constPtrFromRustCast(10u.toUByte())
 * run {
 *     check(ptr.asRefUnchecked() == 10)
 * }
 * ```
 */
public fun <T> ConstPtr.asRefUnchecked(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must guarantee that `self` is valid for a reference
    return (typeLayoutT).readNative((this).rawAddress)
}

/**
 * [isNull]: #method.isNull
 * [asRef]: #method.asRef
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(ptrAsUninit)
 * public fun doctestEntry() {
 *     val ptr: ConstPtr = constPtrFromRustCast(10u.toUByte())
 *     when (val matchVal = ptr.asUninitRef()) {
 *         null -> {}
 *         else -> {
 *             val valBack = matchVal
 *             check(valBack.assumeInit() == 10)
 *         }
 *     }
 * }
 * ```
 */
public fun <T> ConstPtr.asUninitRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T>? {
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
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val s: String = "123"
 * val ptr: ConstPtr = s.asPtr()
 * run {
 *     check((PrimitiveLayouts.U8).readNative((ConstPtr((ptr).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).toInt().toChar() == '2')
 *     check((PrimitiveLayouts.U8).readNative((ConstPtr((ptr).rawAddress + ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress).toInt().toChar() == '3')
 * }
 * ```
 */
public fun <T> ConstPtr.offset(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ConstPtr {
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
    return ConstPtr(this.rawAddress + (count).toLong() * 1L)
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
public fun ConstPtr.byteOffset(count: Int): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `offset`.
    return ConstPtr((ConstPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(this)
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * public fun doctestEntry(): Result<Unit> {
 *     // Iterate using a raw pointer in increments of two elements
 *     val data = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u)
 *     var ptr: ConstPtr = data.asPtr()
 *     val step = 2
 *     val endRoundedUp = ConstPtr((ptr).rawAddress + ((6).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     var out = ""
 *     while (((ptr).rawAddress.toULong() != (endRoundedUp).rawAddress.toULong())) {
 *         run {
 *             out.append("${(PrimitiveLayouts.U8).readNative((ptr).rawAddress)}, ")
 *         }
 *         ptr = ConstPtr((ptr).rawAddress + ((step).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     }
 *     check(out == "1, 3, 5, ")
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> ConstPtr.wrappingOffset(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ConstPtr {
    // SAFETY: the `arithOffset` intrinsic has no prerequisites to be called.
    return ConstPtr(this.rawAddress + (count).toLong() * 1L)
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
public fun ConstPtr.wrappingByteOffset(count: Int): ConstPtr = ConstPtr((ConstPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(this)

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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(ptrMask)
 * public fun doctestEntry() {
 *     val v = 17u
 *     val ptr: ConstPtr = v
 *     // `UInt` is 4 bytes aligned,
 *     // which means that lower 2 bits are always 0.
 *     val tagMask = 0x3
 *     val ptrMask = !tagMask
 *     // We can store something in these lower bits
 *     val taggedPtr = ptr.mapAddr({ a -> a or 0x2 })
 *     // Get the "tag" back
 *     val tag = taggedPtr.addr() and tagMask
 *     check(tag == 0x2)
 *     // Note that `taggedPtr` is unaligned, it's UB to read from it.
 *     // To get original pointer `mask` can be used:
 *     val maskedPtr = taggedPtr.mask(ptrMask)
 *     check(maskedPtr == 17)
 * }
 * ```
 */
public fun ConstPtr.mask(mask: Int): ConstPtr = ConstPtr(ConstPtr((this).rawAddress).rawAddress and (mask).toLong()).withMetadataOf(this)

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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val a = List(5) { 0 }
 * val ptr1: ConstPtr = a[1]
 * val ptr2: ConstPtr = a[3]
 * run {
 *     check((((ptr2).rawAddress - (ptr1).rawAddress) / (PrimitiveLayouts.I32).strideBytes).toInt() == 2)
 *     check((((ptr1).rawAddress - (ptr2).rawAddress) / (PrimitiveLayouts.I32).strideBytes).toInt() == -2)
 *     check(ConstPtr((ptr1).rawAddress + ((2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr2)
 *     check(ConstPtr((ptr2).rawAddress + ((-2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr1)
 * }
 * ```
 *
 * *Incorrect* usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val ptr1 = constPtrFromRustCast(Holder.intoRaw(0u.toUByte()))
 * val ptr2 = constPtrFromRustCast(Holder.intoRaw(1u.toUByte()))
 * val diff = ((ptr2.rawAddress.toInt()) - (ptr1.rawAddress.toInt()))
 * // Make ptr2Other an "alias" of ptr2.add(1), but derived from ptr1.
 * val ptr2Other = ConstPtr((ConstPtr(((ptr1)).rawAddress + ((diff).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))
 * check(ptr2.rawAddress.toInt() == ptr2Other.rawAddress.toInt())
 * // Since ptr2Other and ptr2 are derived from pointers to different objects,
 * // computing their offset is undefined behavior, even though
 * // they point to addresses that are in-bounds of the same object!
 * run {
 *     val one = (((ptr2Other).rawAddress - (ptr2).rawAddress) / (PrimitiveLayouts.U8).strideBytes).toInt()
 *     // Undefined Behavior! ⚠️
 * }
 * ```
 */
public fun <T> ConstPtr.offsetFrom(origin: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    val pointeeSize = sizeOf<T>(typeLayoutT = typeLayoutT)
    check(0 < pointeeSize && pointeeSize <= Int.MAX_VALUE)
    // SAFETY: the caller must uphold the safety contract for `ptrOffsetFrom`.
    return ((this.rawAddress - origin.rawAddress) / maxOf(1L, (typeLayoutT).sizeBytes)).toInt()
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
public fun ConstPtr.byteOffsetFrom(origin: ConstPtr): Int {
    // SAFETY: the caller must uphold the safety contract for `offsetFrom`.
    return (((ConstPtr((this).rawAddress)).rawAddress - (ConstPtr((origin).rawAddress)).rawAddress) / (PrimitiveLayouts.U8).strideBytes).toInt()
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun blah(ptr: ConstPtr, origin: ConstPtr, count: Int): Boolean {
 *     return ptr.offsetFromUnsigned(origin) == count && ((ConstPtr((origin).rawAddress + ((count).toLong() * (PrimitiveLayouts.I32).strideBytes))).rawAddress.toULong() == (ptr).rawAddress.toULong()) && ((ConstPtr((ptr).rawAddress - ((count).toLong() * (PrimitiveLayouts.I32).strideBytes))).rawAddress.toULong() == (origin).rawAddress.toULong())
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
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val a = List(5) { 0 }
 * val ptr1: ConstPtr = a[1]
 * val ptr2: ConstPtr = a[3]
 * run {
 *     check(ptr2.offsetFromUnsigned(ptr1) == 2)
 *     check(ConstPtr((ptr1).rawAddress + ((2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr2)
 *     check(ConstPtr((ptr2).rawAddress - ((2).toLong() * (PrimitiveLayouts.I32).strideBytes)) == ptr1)
 *     check(ptr2.offsetFromUnsigned(ptr2) == 0)
 * }
 * // This would be incorrect, as the pointers are not correctly ordered:
 * // ptr1.offsetFromUnsigned(ptr2)
 * ```
 */
public fun <T> ConstPtr.offsetFromUnsigned(origin: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    fun runtimePtrGe(thisRef: ConstPtr, origin1: ConstPtr): Boolean = ((thisRef).rawAddress.toULong() >= (origin1).rawAddress.toULong())
    Unit
    val pointeeSize = sizeOf<T>(typeLayoutT = typeLayoutT)
    check(0 < pointeeSize && pointeeSize <= Int.MAX_VALUE)
    // SAFETY: the caller must uphold the safety contract for `ptrOffsetFromUnsigned`.
    return ((this.rawAddress - origin.rawAddress) / maxOf(1L, (typeLayoutT).sizeBytes)).toInt()
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
public fun ConstPtr.byteOffsetFromUnsigned(origin: ConstPtr): Int {
    // SAFETY: the caller must uphold the safety contract for `offsetFromUnsigned`.
    return ConstPtr((this).rawAddress).offsetFromUnsigned(ConstPtr((origin).rawAddress))
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
public fun ConstPtr.guaranteedEq(other: ConstPtr): Boolean? =
    when (val matchVal = ptrGuaranteedCmp(this, other)) {
        2u.toUByte() -> null
        else -> {
            val other1 = matchVal
            other1 == 1u.toUByte()
        }
    }

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
public fun ConstPtr.guaranteedNe(other: ConstPtr): Boolean? =
    when (val matchVal = this.guaranteedEq(other)) {
        null -> null
        else -> {
            val eq = matchVal
            !eq
        }
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val s: String = "123"
 * val ptr: ConstPtr = s.asPtr()
 * run {
 *     check((PrimitiveLayouts.U8).readNative((ConstPtr((ptr).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress) == 50u)
 *     check((PrimitiveLayouts.U8).readNative((ConstPtr((ptr).rawAddress + ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress) == 51u)
 * }
 * ```
 */
public fun <T> ConstPtr.add(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ConstPtr {
    fun runtimeAddNowrap(thisRef: ConstPtr, count1: Int, size: Int): Boolean {
        return run {
            val byteOffset = count1.checkedMul(size) ?: return false
            val (_, overflow) = (thisRef).rawAddress.toInt().overflowingAdd(byteOffset)
            byteOffset <= (Int.MAX_VALUE) && !overflow
        }
    }
    Unit
    // SAFETY: the caller must uphold the safety contract for `offset`.
    return ConstPtr(this.rawAddress + (count).toLong() * 1L)
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
 * See [add][pointer.add] for documentation and safety requirements.
 */
public fun ConstPtr.byteAdd(count: Int): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `add`.
    return ConstPtr((ConstPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(this)
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
 *     check((PrimitiveLayouts.U8).readNative((ConstPtr((end).rawAddress - ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress) == 51u)
 *     check((PrimitiveLayouts.U8).readNative((ConstPtr((end).rawAddress - ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress) == 50u)
 * }
 * ```
 */
public fun <T> ConstPtr.sub(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ConstPtr {
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
        ConstPtr(this.rawAddress + (((0) - (count))).toLong() * 1L)
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
 * See [sub][pointer.sub] for documentation and safety requirements.
 */
public fun ConstPtr.byteSub(count: Int): ConstPtr {
    // SAFETY: the caller must uphold the safety contract for `sub`.
    return ConstPtr((ConstPtr((this).rawAddress)).rawAddress - ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(this)
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
 * public fun doctestEntry(): Result<Unit> {
 *     // Iterate using a raw pointer in increments of two elements
 *     val data = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u)
 *     var ptr: ConstPtr = data.asPtr()
 *     val step = 2
 *     val endRoundedUp = ConstPtr((ptr).rawAddress + ((6).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     var out = ""
 *     while (((ptr).rawAddress.toULong() != (endRoundedUp).rawAddress.toULong())) {
 *         run {
 *             out.append("${(PrimitiveLayouts.U8).readNative((ptr).rawAddress)}, ")
 *         }
 *         ptr = ConstPtr((ptr).rawAddress + ((step).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     }
 *     check(out == "1, 3, 5, ")
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> ConstPtr.wrappingAdd(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ConstPtr = ConstPtr((this).rawAddress + ((count).toLong() * (typeLayoutT).strideBytes))

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
public fun ConstPtr.wrappingByteAdd(count: Int): ConstPtr = ConstPtr((ConstPtr((this).rawAddress)).rawAddress + ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(this)

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
 * public fun doctestEntry(): Result<Unit> {
 *     // Iterate using a raw pointer in increments of two elements (backwards)
 *     val data = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u)
 *     var ptr: ConstPtr = data.asPtr()
 *     val startRoundedDown = ConstPtr((ptr).rawAddress - ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     ptr = ConstPtr((ptr).rawAddress + ((4).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     val step = 2
 *     var out = ""
 *     while (((ptr).rawAddress.toULong() != (startRoundedDown).rawAddress.toULong())) {
 *         run {
 *             out.append("${(PrimitiveLayouts.U8).readNative((ptr).rawAddress)}, ")
 *         }
 *         ptr = ConstPtr((ptr).rawAddress - ((step).toLong() * (PrimitiveLayouts.U8).strideBytes))
 *     }
 *     check(out == "5, 3, 1, ")
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> ConstPtr.wrappingSub(count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ConstPtr = ConstPtr((this).rawAddress + (((count).wrappingNeg()).toLong() * (typeLayoutT).strideBytes))

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
public fun ConstPtr.wrappingByteSub(count: Int): ConstPtr = ConstPtr((ConstPtr((this).rawAddress)).rawAddress - ((count).toLong() * (PrimitiveLayouts.U8).strideBytes)).withMetadataOf(this)

/**
 * Reads the value from `self` without moving it. This leaves the
 * memory in `self` unchanged.
 *
 * # Safety
 *
 * See [ptr.read] for safety concerns and examples.
 */
public fun <T> ConstPtr.read(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `read`.
    return read(this)
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
public fun <T> ConstPtr.readVolatile(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `readVolatile`.
    return readVolatile(this)
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
public fun <T> ConstPtr.readUnaligned(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `readUnaligned`.
    return readUnaligned(this)
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
public fun <T> ConstPtr.copyTo(dest: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `copy`.
    Copy(this, dest, count)
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
public fun <T> ConstPtr.copyToNonoverlapping(dest: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `copyNonoverlapping`.
    copyNonoverlapping(this, dest, count)
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * public fun doctestEntry() {
 *     run {
 *         val x = ubyteArrayOf(5u.toUByte(), 6u, 7u, 8u, 9u)
 *         val ptr = x.asPtr()
 *         val offset = ptr.alignOffset(alignOf<UShort>(typeLayoutT = PrimitiveLayouts.U16))
 *         if (offset < x.size - 1) {
 *             val u16Ptr = ConstPtr((ConstPtr((ptr).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
 *             check((PrimitiveLayouts.U16).readNative((u16Ptr).rawAddress) == run { val bytesTmp0 = ubyteArrayOf(5u, 6u); ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() } || (PrimitiveLayouts.U16).readNative((u16Ptr).rawAddress) == run { val bytesTmp1 = ubyteArrayOf(6u, 7u); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
 *         } else {
 *             // while the pointer can be aligned via `offset`, it would point
 *             // outside the allocation
 *         }
 *     }
 * }
 * ```
 */
public fun <T> ConstPtr.alignOffset(align: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    if (!align.isPowerOfTwo()) {
        error("align_offset: align is not a power-of-two")
    }
    // SAFETY: `align` has been checked to be a power of 2 above
    val ret = alignOffset(this, align)
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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
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
 * val ptr = ConstPtr(localStorageL7C0.asMutPtr().rawAddress)
 * check(ptr.isAligned())
 * check(!ConstPtr((ptr).rawAddress + ((1).toLong())).isAligned())
 * ```
 */
public fun <T> ConstPtr.isAligned(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = this.isAlignedTo(alignOf<T>(typeLayoutT = typeLayoutT))

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
public fun ConstPtr.isAlignedTo(align: Int): Boolean {
    if (!align.isPowerOfTwo()) {
        error("is_aligned_to: align is not a power-of-two")
    }
    return (this).rawAddress.toInt() and (align - 1) == 0
}

/**
 * Casts from a type to its maybe-uninitialized version.
 */
public fun ConstPtr.castUninit(): ConstPtr = this

/**
 * Forms a raw slice from a pointer and a length.
 *
 * The `len` argument is the number of **elements**, not the number of bytes.
 *
 * See [ptr.sliceFromRawParts] for more information.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 *
 * // create a slice pointer when starting out with a pointer to the first element
 * val localStorageL4C0 = AddressableArrayLocal.fromIntArray(intArrayOf(5, 6, 7))
 * val rawSlice = localStorageL4C0.elementConstPtr(0).castSlice(3)
 * check(rawSlice[2] == 7)
 * ```
 *
 * You must ensure that the pointer is valid and not null before dereferencing
 * the raw slice. A slice reference must never have a null pointer, even if it's empty.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * val danger: ConstPtr = ConstPtr(0L).castSlice(0)
 * run {
 *     danger.asRef().expect("references must not be null")
 * }
 * ```
 */
public fun ConstPtr.castSlice(len: Int): ConstPtr = sliceFromRawParts(this, len)

/**
 * Casts from a maybe-uninitialized type to its initialized version.
 *
 * This is always safe, since UB can only occur if the pointer is read
 * before being initialized.
 */
public fun ConstPtr.castInit(): ConstPtr = this

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
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * val slice: ConstPtr = ptr.sliceFromRawParts(ConstPtr(0L), 3)
 * check(slice.len() == 3)
 * ```
 */
public fun ConstPtr.len(): Int = metadata(this)

/**
 * Returns `true` if the raw slice has a length of 0.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * val slice: ConstPtr = ptr.sliceFromRawParts(ConstPtr(0L), 3)
 * check(!slice.isEmpty())
 * ```
 */
public fun ConstPtr.isEmpty(): Boolean = this.len() == 0

/**
 * Returns a raw pointer to the slice's buffer.
 *
 * This is equivalent to casting `self` to `* const T`, but more type-safe.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val slice: ConstPtr = ptr.sliceFromRawParts(ConstPtr(0L), 3)
 *     check(slice.asPtr() == ConstPtr(0L))
 * }
 * ```
 */
public fun ConstPtr.asPtr(): ConstPtr = ConstPtr(this.rawAddress, this.rawMeta)

/**
 * Gets a raw pointer to the underlying array.
 *
 * If `N` is not exactly equal to the length of `self`, then this method returns `None`.
 */
public fun ConstPtr.asArray(n: Int): ConstPtr? =
    if (this.len() == n) {
        val me = constPtrFromRustCast(this.asPtr())
        me
    } else {
        null
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
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 *
 * @feature(slicePtrGet)
 * public fun doctestEntry() {
 *     val x = constPtrFromRustCast(listOf(1, 2, 4))
 *     run {
 *         check(x.getUnchecked(1) == x.asPtr().add(1))
 *     }
 * }
 * ```
 */
public fun <Output, T, I> ConstPtr.getUnchecked(index: I): ConstPtr where I : SliceIndex<List<T>, Output> {
    // SAFETY: the caller ensures that `self` is dereferenceable and `index` in-bounds.
    return index.getUnchecked(this)
}

public fun <T> ConstPtr.asUninitSlice(): List<MaybeUninit<T>>? =
    if (((this).rawAddress == 0L)) {
        null
    } else {
        // SAFETY: the caller must uphold the safety contract for `asUninitSlice`.
        io.github.kotlinmania.rustlang.core.slice.raw.fromRawParts(ConstPtr(this.rawAddress, this.rawMeta), this.len())
    }

/**
 * Casts from a pointer-to-`T` to a pointer-to-`[T; N]`.
 */
public fun ConstPtr.castArray(n: Int): ConstPtr = ConstPtr((this).rawAddress)

/**
 * Returns a raw pointer to the array's buffer.
 *
 * This is equivalent to casting `self` to `* const T`, but more type-safe.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * @feature(arrayPtrGet)
 * public fun doctestEntry() {
 *     val arr: ConstPtr = ConstPtr(0L)
 *     check(arr.asPtr() == ConstPtr(0L))
 * }
 * ```
 */
public fun ConstPtr.asPtr(n: Int): ConstPtr = ConstPtr(this.rawAddress, this.rawMeta)

/**
 * Returns a raw pointer to a slice containing the entire array.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(arrayPtrGet)
 * public fun doctestEntry() {
 *     val arr: ConstPtr = constPtrFromRustCast(listOf(1, 2, 4))
 *     val slice: ConstPtr = arr.asSlice()
 *     check(slice.len() == 3)
 * }
 * ```
 */
public fun ConstPtr.asSlice(n: Int): ConstPtr = this

/**
 * For thin pointers, equality is by address, as produced by the [`const.T.addr`](pointer.addr) method.
 */
public fun <T> ConstPtr.eq(other: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (typeLayoutT).readNative((this).rawAddress) == (typeLayoutT).readNative((other).rawAddress)

/**
 * Pointer equality is an equivalence relation.
 */

/**
 * For thin pointers, comparison is by address, as produced by the [`const.T.addr`](pointer.addr) method.
 */
public fun ConstPtr.cmp(other: ConstPtr): Ordering =
    if (((this).rawAddress.toULong() < (other).rawAddress.toULong())) {
        Less
    } else if (((this).rawAddress.toULong() == (other).rawAddress.toULong())) {
        Equal
    } else {
        Greater
    }

/**
 * For thin pointers, comparison is by address, as produced by the [`const.T.addr`](pointer.addr) method.
 */
public fun ConstPtr.partialCmp(other: ConstPtr): Ordering? = this.cmp(other)

public fun <T> ConstPtr.lt(other: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) < 0)

public fun <T> ConstPtr.le(other: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) <= 0)

public fun <T> ConstPtr.gt(other: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) > 0)

public fun <T> ConstPtr.ge(other: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean = (kmCompareValues((typeLayoutT).readNative((this).rawAddress), (typeLayoutT).readNative((other).rawAddress)) >= 0)

/**
 * Returns the default value of [`nullVal`][ptr.nullVal].
 */
public fun defaultConstPtrT(): ConstPtr = ConstPtr(0L)
