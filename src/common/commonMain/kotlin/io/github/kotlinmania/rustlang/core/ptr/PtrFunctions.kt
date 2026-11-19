// rust-kotlin: manual
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ptr

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.cmp
import io.github.kotlinmania.rustlang.core.intrinsics.assume
import io.github.kotlinmania.rustlang.core.intrinsics.cttzNonzero
import io.github.kotlinmania.rustlang.core.intrinsics.exactDiv
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopy
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.intrinsics.mulWithOverflow
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedRem
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShl
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedShr
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedSub
import io.github.kotlinmania.rustlang.core.intrinsics.wrappingAdd
import io.github.kotlinmania.rustlang.core.intrinsics.wrappingMul
import io.github.kotlinmania.rustlang.core.intrinsics.wrappingSub
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteNeo
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.function.FnPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.addr
import io.github.kotlinmania.rustlang.core.ptr.metadata.fromRawParts
import io.github.kotlinmania.rustlang.core.ptr.metadata.fromRawPartsMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.addr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

// Some functions are defined here because they accidentally got made
// available in this module on stable. See <https://github.com/rust-lang/rust/issues/15702>.
// (`reinterpret` also falls into this category, but it cannot be wrapped due to the
// check that `T` and `U` have the same size.)
// even without panics, this helps for Miri backtraces

/**
 * Copies `count * sizeOf<T>()` bytes from `src` to `dst`. The source
 * and destination must *not* overlap.
 *
 * For regions of memory which might overlap, use [copy] instead.
 *
 * `copyNonoverlapping` is semantically equivalent to C's [memcpy], but
 * with the source and destination arguments swapped,
 * and `count` counting the number of `T`s instead of bytes.
 *
 * The copy is "untyped" in the sense that data may be uninitialized or otherwise violate the
 * requirements of `T`. The initialization state is preserved exactly.
 *
 * [memcpy]: https://en.cppreference.com/w/c/string/byte/memcpy
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `src` must be [valid] for reads of `count * sizeOf<T>()` bytes or that number must be 0.
 *
 * * `dst` must be [valid] for writes of `count * sizeOf<T>()` bytes or that number must be 0.
 *
 * * Both `src` and `dst` must be properly aligned.
 *
 * * The region of memory beginning at `src` with a size of `count *
 *   sizeOf::<T>()` bytes must *not* overlap with the region of memory
 *   beginning at `dst` with the same size.
 *
 * Like [read], `copyNonoverlapping` creates a bitwise copy of `T`, regardless of
 * whether `T` is [Copy]. If `T` is not [Copy], using *both* the values
 * in the region beginning at `* src` and the region beginning at `* dst` can
 * [violate memory safety][read-ownership].
 *
 * Note that even if the effectively copied size (`count * sizeOf<T>()`) is
 * `0`, the pointers must be properly aligned.
 *
 * # Examples
 *
 * Manually implement [Vec.append]:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * / **
 *  * Moves all the elements of `src` into `dst`, leaving `src` empty.
 *  * /
 * fun <T> append(dst: MutableList<T>, src: MutableList<T>) {
 *     val srcLen = src.size
 *     val dstLen = dst.size
 *     // Ensure that `dst` has enough capacity to hold all of `src`.
 *     dst.reserve(srcLen)
 *     run {
 *         // The call to add is always safe because `List` will never
 *         // allocate more than `Long.MAX` bytes.
 *         val dstPtr = MutPtr((dst.asMutPtr()).rawAddress + ((dstLen).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
 *         val srcPtr = src.asPtr()
 *         // Truncate `src` without dropping its contents. We do this first,
 *         // to avoid problems in case something further down panics.
 *         src.setLen(0)
 *         // The two regions cannot overlap because mutable references do
 *         // not alias, and two different vectors cannot own the same
 *         // memory.
 *         ptr.copyNonoverlapping(srcPtr, dstPtr, srcLen)
 *         // Notify `dst` that it now holds the contents of `src`.
 *         dst.setLen(dstLen + srcLen)
 *     }
 * }
 * var a = mutableListOf('r')
 * var b = mutableListOf('u', 's', 't')
 * append(a, b)
 * check(a == listOf('r', 'u', 's', 't'))
 * check(b.isEmpty())
 * ```
 *
 * [Vec.append]: ../../std/vec/struct.Vec.html#method.append
 */
public fun <T> copyNonoverlapping(src: ConstPtr, dst: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the safety contract for `copyNonoverlapping` must be
    // upheld by the caller.
    kmCopyNonoverlapping(dst.rawAddress, src.rawAddress, (count).toLong() * (typeLayoutT).sizeBytes)
}

// even without panics, this helps for Miri backtraces

/**
 * Copies `count * sizeOf<T>()` bytes from `src` to `dst`. The source
 * and destination may overlap.
 *
 * If the source and destination will *never* overlap,
 * [copyNonoverlapping] can be used instead.
 *
 * `copy` is semantically equivalent to C's [memmove], but
 * with the source and destination arguments swapped,
 * and `count` counting the number of `T`s instead of bytes.
 * Copying takes place as if the bytes were copied from `src`
 * to a temporary array and then copied from the array to `dst`.
 *
 * The copy is "untyped" in the sense that data may be uninitialized or otherwise violate the
 * requirements of `T`. The initialization state is preserved exactly.
 *
 * [memmove]: https://en.cppreference.com/w/c/string/byte/memmove
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `src` must be [valid] for reads of `count * sizeOf<T>()` bytes or that number must be 0.
 *
 * * `dst` must be [valid] for writes of `count * sizeOf<T>()` bytes or that number must be 0,
 *   and `dst` must remain valid even when `src` is read for `count * sizeOf<T>()` bytes. (This
 *   means if the memory ranges overlap, the `dst` pointer must not be invalidated by `src` reads.)
 *
 * * Both `src` and `dst` must be properly aligned.
 *
 * Like [read], `copy` creates a bitwise copy of `T`, regardless of
 * whether `T` is [Copy]. If `T` is not [Copy], using both the values
 * in the region beginning at `* src` and the region beginning at `* dst` can
 * [violate memory safety][read-ownership].
 *
 * Note that even if the effectively copied size (`count * sizeOf<T>()`) is
 * `0`, the pointers must be properly aligned.
 *
 * # Examples
 *
 * Efficiently create a Rust vector from an unchecked buffer:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * / **
 *  * # Safety
 *  *
 *  * * `ptr` must be correctly aligned for its type and non-zero.
 *  * * `ptr` must be valid for reads of `elts` contiguous elements of type `T`.
 *  * * Those elements must not be used after calling this function unless `T: Copy`.
 *  * /
 * fun <T> fromBufRaw(ptr: ConstPtr, elts: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<T> {
 *     var dst = ArrayList<T>(elts)
 *     // SAFETY: Our precondition ensures the source is aligned and valid,
 *     // and `List.withCapacity` ensures that we have usable space to write them.
 *     run {
 *         ptr.copy(ptr, dst.asMutPtr(), elts)
 *     }
 *     // SAFETY: We created it with this much capacity earlier,
 *     // and the previous `copy` has initialized these elements.
 *     run {
 *         dst.setLen(elts)
 *     }
 *     return dst
 * }
 * ```
 */
public fun <T> copy(src: ConstPtr, dst: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the safety contract for `copy` must be upheld by the caller.
    kmCopy(dst.rawAddress, src.rawAddress, (count).toLong() * (typeLayoutT).sizeBytes)
}

// even without panics, this helps for Miri backtraces

/**
 * Sets `count * sizeOf<T>()` bytes of memory starting at `dst` to
 * `val`.
 *
 * `writeBytes` is similar to C's [memset], but sets `count *
 * sizeOf::<T>()`bytes to`val`.
 *
 * [memset]: https://en.cppreference.com/w/c/string/byte/memset
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `dst` must be [valid] for writes of `count * sizeOf<T>()` bytes.
 *
 * * `dst` must be properly aligned.
 *
 * Note that even if the effectively copied size (`count * sizeOf<T>()`) is
 * `0`, the pointer must be properly aligned.
 *
 * Additionally, note that changing `* dst` in this way can easily lead to undefined behavior (UB)
 * later if the written bytes are not a valid representation of some `T`. For instance, the
 * following is an **incorrect** use of this function:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * run {
 *     val localStorageL4C4 = AddressableLocal(0u.toUByte(), PrimitiveLayouts.U8)
 *     val ptr: MutPtr = (MutPtr(localStorageL4C4.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     val bool = (PrimitiveLayouts.BOOL).readNative((ptr).rawAddress)
 *     // This is fine, `ptr` points to a valid `Boolean`.
 *     kmWriteBytes((ptr).rawAddress, (42u.toUByte()).toUByte(), (1).toLong() * (PrimitiveLayouts.BOOL).sizeBytes)
 *     // This function itself does not cause UB...
 *     val bool1 = (PrimitiveLayouts.BOOL).readNative((ptr).rawAddress)
 *     // ...but it makes this operation UB! ⚠️
 * }
 * ```
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.ptr
 *
 * var vec = mutableListOf(0u, 4)
 * run {
 *     val vecPtr = vec.asMutPtr()
 *     ptr.writeBytes(vecPtr, 0xfe, 2)
 * }
 * check(vec == listOf(0xfefefefe, 0xfefefefe, 0, 0))
 * ```
 */
public fun <T> writeBytes(dst: MutPtr, value: UByte, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the safety contract for `writeBytes` must be upheld by the caller.
    kmWriteBytes(dst.rawAddress, value, (count).toLong() * (typeLayoutT).sizeBytes)
}

// Note: say something more useful than "almost the same"?
// There are open questions here: `read` requires the value to be fully valid, e.g. if `T` is a
// `Boolean` it must be 0 or 1, if it is a reference then it must be dereferenceable. `dropInPlace`
// only requires that `* toDrop` be "valid for dropping" and we have not defined what that means. In
// Miri it currently (May 2024) requires nothing at all for types without drop glue.

/**
 * Executes the destructor (if any) of the pointed-to value.
 *
 * This is almost the same as calling [ptr.read] and discarding
 * the result, but has the following advantages:
 *
 * * It is *required* to use `dropInPlace` to drop unsized types like
 *   trait objects, because they can't be read out onto the stack and
 *   dropped normally.
 *
 * * It is friendlier to the optimizer to do this over [ptr.read] when
 *   dropping manually allocated memory (e.g., in the implementations of
 *   `Holder`/`Rc`/`List`), as the compiler doesn't need to prove that it's
 *   sound to elide the copy.
 *
 * * It can be used to drop [pinned] data when `T` is not `repr(packed)`
 *   (pinned data must not be moved before it is dropped).
 *
 * Unaligned values cannot be dropped in place, they must be copied to an aligned
 * location first using [ptr.readUnaligned]. For packed structs, this move is
 * done automatically by the compiler. This means the fields of packed structs
 * are not dropped in-place.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `toDrop` must be [valid] for both reads and writes.
 *
 * * `toDrop` must be properly aligned, even if `T` has size 0.
 *
 * * `toDrop` must be nonnull, even if `T` has size 0.
 *
 * * The value `toDrop` points to must be valid for dropping, which may mean
 *   it must uphold additional invariants. These invariants depend on the type
 *   of the value being dropped. For instance, when dropping a Holder, the box's
 *   pointer to the heap must be valid.
 *
 * * While `dropInPlace` is executing, the only way to access parts of
 *   `toDrop` is through the `self` references supplied to the
 *   `Drop.drop` methods that `dropInPlace` invokes.
 *
 * Additionally, if `T` is not [Copy], using the pointed-to value after
 * calling `dropInPlace` can cause undefined behavior. Note that `*toDrop =
 * foo` counts as a use because it will cause the value to be dropped
 * again. [write] can be used to overwrite data without causing it to be
 * dropped.
 *
 * [valid]: self#safety
 *
 * # Examples
 *
 * Manually remove the last item from a vector:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import std.ptr
 *
 * val last = 1
 * val weak = Rc.downgrade(last)
 * var v = mutableListOf(0, last)
 * run {
 *     // Get a raw pointer to the last element in `v`.
 *     val ptr = mutPtrFromRustCast(v[1])
 *     // Shorten `v` to prevent the last item from being dropped. We do that first,
 *     // to prevent issues if the `dropInPlace` below panics.
 *     v.setLen(1)
 *     // Without a call `dropInPlace`, the last item would never be dropped,
 *     // and the memory it manages would be leaked.
 *     ptr.dropInPlace(ptr)
 * }
 * check(v == listOf(0.into()))
 * // Ensure that the last item was dropped.
 * check(weak.upgrade() == null)
 * ```
 */
public fun <T> dropInPlace(toDrop: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // Due to historic reasons, `dropInPlace` takes a pointer rather than a reference,
    // which results in worse codegen since we don't apply noalias/dereferenceable llvm
    // attributes to pointer arguments. To workaround this without breaking public
    // interface, `dropInPlace` calls the lang item, rather than being one directly.
    // SAFETY:
    // - compiler glue has the same safety requirements as this function
    // - the pointer must be valid as per the safety requirement of this function
    dropGlue(RustMutableReference(MutPtr((toDrop).rawAddress), typeLayoutT))
}

/**
 * Helper function for `dropInPlace`. The compiler replaces this by the actual drop glue.
 */
public fun <T> dropGlue(arg0: T) {
    // Code here does not matter - this is replaced by the
    // real drop glue by the compiler.
}

/**
 * Creates a null raw pointer.
 *
 * This function is equivalent to zero-initializing the pointer:
 * `MaybeUninit< * const T>.zeroed().assumeInit()`.
 * The resulting pointer has the address 0.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * val p: ConstPtr = ptr.nullVal()
 * check(((p).rawAddress == 0L))
 * check(p.rawAddress.toInt() == 0)
 * // this pointer has the address 0
 * ```
 */
public fun nullVal(): ConstPtr = fromRawParts(withoutProvenance(0), 0)

/**
 * Creates a null mutable raw pointer.
 *
 * This function is equivalent to zero-initializing the pointer:
 * `MaybeUninit< * T>.zeroed().assumeInit()`.
 * The resulting pointer has the address 0.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * val p: MutPtr = ptr.nullMut()
 * check(((p).rawAddress == 0L))
 * check(p.rawAddress.toInt() == 0)
 * // this pointer has the address 0
 * ```
 */
public fun nullMut(): MutPtr = fromRawPartsMut(withoutProvenanceMut(0), 0)

/**
 * Creates a pointer with the given address and no [provenance][ptr#provenance].
 *
 * This is equivalent to `ptr.null().withAddr(addr)`.
 *
 * Without provenance, this pointer is not associated with any actual allocation. Such a
 * no-provenance pointer may be used for zero-sized memory accesses (if suitably aligned), but
 * non-zero-sized memory accesses with a no-provenance pointer are UB. No-provenance pointers are
 * little more than a `Int` address in disguise.
 *
 * This is different from `addr as * const T`, which creates a pointer that picks up a previously
 * exposed provenance. See [withExposedProvenance] for more details on that operation.
 *
 * This is a [Strict Provenance][ptr#strict-provenance] API.
 */
public fun withoutProvenance(addr: Int): ConstPtr = ConstPtr(withoutProvenanceMut(addr).rawAddress, 0L)

/**
 * Creates a new pointer that is dangling, but non-null and well-aligned.
 *
 * This is useful for initializing types which lazily allocate, like
 * `List.new` does.
 *
 * Note that the address of the returned pointer may potentially
 * be that of a valid pointer, which means this must not be used
 * as a "not yet initialized" sentinel value.
 * Types that lazily allocate must track initialization by some other means.
 */
public fun dangling(): ConstPtr = ConstPtr(danglingMut().rawAddress, danglingMut().rawMeta)

// Expected semantics here.

/**
 * Creates a pointer with the given address and no [provenance][ptr#provenance].
 *
 * This is equivalent to `ptr.nullMut().withAddr(addr)`.
 *
 * Without provenance, this pointer is not associated with any actual allocation. Such a
 * no-provenance pointer may be used for zero-sized memory accesses (if suitably aligned), but
 * non-zero-sized memory accesses with a no-provenance pointer are UB. No-provenance pointers are
 * little more than a `Int` address in disguise.
 *
 * This is different from `addr as * T`, which creates a pointer that picks up a previously
 * exposed provenance. See [withExposedProvenanceMut] for more details on that operation.
 *
 * This is a [Strict Provenance][ptr#strict-provenance] API.
 */
public fun withoutProvenanceMut(addr: Int): MutPtr {
    // An int-to-pointer reinterpret currently has exactly the intended semantics: it creates a
    // pointer without provenance. Note that this is *not* a stable guarantee about reinterpret
    // semantics, it relies on sysroot crates having special status.
    // SAFETY: every valid integer is also a valid pointer (as long as you don't dereference that
    // pointer).
    return transmuteWithLayouts<Int, MutPtr>(addr, PrimitiveLayouts.USIZE64, PrimitiveLayouts.mutPtrLayout(64L))
}

/**
 * Creates a new pointer that is dangling, but non-null and well-aligned.
 *
 * This is useful for initializing types which lazily allocate, like
 * `List.new` does.
 *
 * Note that the address of the returned pointer may potentially
 * be that of a valid pointer, which means this must not be used
 * as a "not yet initialized" sentinel value.
 * Types that lazily allocate must track initialization by some other means.
 */
public fun danglingMut(): MutPtr = NonNull.dangling().asPtr()

// even without panics, this helps for Miri backtraces
// this *is* the explicit provenance API one should use instead

/**
 * Converts an address back to a pointer, picking up some previously `exposed`
 * [provenance][ptr#provenance].
 *
 * This is fully equivalent to `addr as * const T`. The provenance of the returned pointer is that
 * of *some* pointer that was previously exposed by passing it to
 * [`exposeProvenance`][pointer.exposeProvenance], or a `ptr as Int` cast. In addition, memory
 * which is outside the control of the Rust abstract machine (MMIO registers, for example) is
 * always considered to be accessible with an exposed provenance, so long as this memory is disjoint
 * from memory that will be used by the abstract machine such as the stack, heap, and statics.
 *
 * The exact provenance that gets picked is not specified. The compiler will do its best to pick
 * the "right" provenance for you (whatever that may be), but currently we cannot provide any
 * guarantees about which provenance the resulting pointer will have -- and therefore there
 * is no definite specification for which memory the resulting pointer may access.
 *
 * If there is *no* previously `exposed` provenance that justifies the way the returned pointer
 * will be used, the program has undefined behavior. In particular, the aliasing rules still apply:
 * pointers and references that have been invalidated due to aliasing accesses cannot be used
 * anymore, even if they have been exposed!
 *
 * Due to its inherent ambiguity, this operation may not be supported by tools that help you to
 * stay conformant with the Rust memory model. It is recommended to use [Strict
 * Provenance][self#strict-provenance] APIs such as [`withAddr`][pointer.withAddr] wherever
 * possible.
 *
 * On most platforms this will produce a value with the same bytes as the address. Platforms
 * which need to store additional information in a pointer may not support this operation,
 * since it is generally not possible to actually *compute* which provenance the returned
 * pointer has to pick up.
 *
 * This is an [Exposed Provenance][ptr#exposed-provenance] API.
 */
public fun withExposedProvenance(addr: Int): ConstPtr = ConstPtr(addr.toLong())

// even without panics, this helps for Miri backtraces
// this *is* the explicit provenance API one should use instead

/**
 * Converts an address back to a mutable pointer, picking up some previously `exposed`
 * [provenance][ptr#provenance].
 *
 * This is fully equivalent to `addr as * T`. The provenance of the returned pointer is that
 * of *some* pointer that was previously exposed by passing it to
 * [`exposeProvenance`][pointer.exposeProvenance], or a `ptr as Int` cast. In addition, memory
 * which is outside the control of the Rust abstract machine (MMIO registers, for example) is
 * always considered to be accessible with an exposed provenance, so long as this memory is disjoint
 * from memory that will be used by the abstract machine such as the stack, heap, and statics.
 *
 * The exact provenance that gets picked is not specified. The compiler will do its best to pick
 * the "right" provenance for you (whatever that may be), but currently we cannot provide any
 * guarantees about which provenance the resulting pointer will have -- and therefore there
 * is no definite specification for which memory the resulting pointer may access.
 *
 * If there is *no* previously `exposed` provenance that justifies the way the returned pointer
 * will be used, the program has undefined behavior. In particular, the aliasing rules still apply:
 * pointers and references that have been invalidated due to aliasing accesses cannot be used
 * anymore, even if they have been exposed!
 *
 * Due to its inherent ambiguity, this operation may not be supported by tools that help you to
 * stay conformant with the Rust memory model. It is recommended to use [Strict
 * Provenance][self#strict-provenance] APIs such as [`withAddr`][pointer.withAddr] wherever
 * possible.
 *
 * On most platforms this will produce a value with the same bytes as the address. Platforms
 * which need to store additional information in a pointer may not support this operation,
 * since it is generally not possible to actually *compute* which provenance the returned
 * pointer has to pick up.
 *
 * This is an [Exposed Provenance][ptr#exposed-provenance] API.
 */
public fun withExposedProvenanceMut(addr: Int): MutPtr = MutPtr(addr.toLong())

/**
 * Converts a reference to a raw pointer.
 *
 * For `r: T`, `fromRef(r)` is equivalent to `r as * const T` (except for the caveat noted below),
 * but is a bit safer since it will never silently change type or mutability, in particular if the
 * code is refactored.
 *
 * The caller must ensure that the pointee outlives the pointer this function returns, or else it
 * will end up dangling.
 *
 * The caller must also ensure that the memory the pointer (non-transitively) points to is never
 * written to (except inside an `UnsafeCell`) using this pointer or any pointer derived from it. If
 * you need to mutate the pointee, use [fromMut]. Specifically, to turn a mutable reference `m:
 * T`into`*const T`, prefer`fromMut(m).castConst()` to obtain a pointer that can later be
 * used for mutation.
 *
 * ## Interaction with scope extension
 *
 * Note that this has subtle interactions with the rules for scope extension of temporaries in
 * tail expressions. This code is valid, albeit in a non-obvious way:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 *
 * // The temporary holding the return value of `foo` has its scope extended,
 * // because the surrounding expression involves no function call.
 * val p = constPtrFromRustCast(foo())
 * (UnresolvedTypeLayout<T>()).readNative((p).rawAddress)
 * ```
 * Naively replacing the cast with `fromRef` is not valid:
 * ```kotlin
 * // The temporary holding the return value of `foo` does *not* have its scope extended,
 * // because the surrounding expression involves a function call.
 * val p = foo()
 * p.read()
 * // UB! Reading from a dangling pointer ⚠️
 * ```
 * The recommended way to write this code is to avoid relying on scope extension
 * when raw pointers are involved:
 * ```kotlin
 * val x = foo()
 * val p = x
 * p.read()
 * ```
 */
public fun <T> fromRef(r: T): ConstPtr =
    when (r) {
        is ConstPtr -> r
        is MutPtr -> ConstPtr(r.rawAddress, r.rawMeta)
        is RustMutableReference<*> -> ConstPtr(r.pointer.rawAddress, r.pointer.rawMeta)
        else -> constPtrFromRustCast(r as Any)
    }

/**
 * Converts a mutable reference to a raw pointer.
 *
 * For `r: T`, `fromMut(r)` is equivalent to `r as * T` (except for the caveat noted
 * below), but is a bit safer since it will never silently change type or mutability, in particular
 * if the code is refactored.
 *
 * The caller must ensure that the pointee outlives the pointer this function returns, or else it
 * will end up dangling.
 *
 * ## Interaction with scope extension
 *
 * Note that this has subtle interactions with the rules for scope extension of temporaries in
 * tail expressions. This code is valid, albeit in a non-obvious way:
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 *
 * // The temporary holding the return value of `foo` has its scope extended,
 * // because the surrounding expression involves no function call.
 * val p = mutPtrFromRustCast(foo())
 * (UnresolvedTypeLayout<T>()).writeNative((p).rawAddress, T.default())
 * ```
 * Naively replacing the cast with `fromMut` is not valid:
 * ```kotlin
 * // The temporary holding the return value of `foo` does *not* have its scope extended,
 * // because the surrounding expression involves a function call.
 * val p = foo()
 * p.write(T.default())
 * // UB! Writing to a dangling pointer ⚠️
 * ```
 * The recommended way to write this code is to avoid relying on scope extension
 * when raw pointers are involved:
 * ```kotlin
 * var x = foo()
 * val p = x
 * p.write(T.default())
 * ```
 */
public fun <T> fromMut(r: T): MutPtr =
    when (r) {
        is MutPtr -> r
        is RustMutableReference<*> -> MutPtr(r.pointer.rawAddress, r.pointer.rawMeta)
        else -> mutPtrFromRustCast(r as Any)
    }

/**
 * Forms a raw slice from a pointer and a length.
 *
 * The `len` argument is the number of **elements**, not the number of bytes.
 *
 * This function is safe, but actually using the return value is unchecked.
 * See the documentation of [slice.fromRawParts] for slice safety requirements.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import std.ptr
 *
 * // create a slice pointer when starting out with a pointer to the first element
 * val localStorageL6C0 = AddressableArrayLocal.fromIntArray(intArrayOf(5, 6, 7))
 * val rawPointer = localStorageL6C0.elementConstPtr(0)
 * val slice = ptr.sliceFromRawParts(rawPointer, 3)
 * check(slice[2] == 7)
 * ```
 *
 * You must ensure that the pointer is valid and not null before dereferencing
 * the raw slice. A slice reference must never have a null pointer, even if it's empty.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.ptr
 *
 * val danger: ConstPtr = ptr.sliceFromRawParts(ptr.nullVal(), 0)
 * run {
 *     danger.asRef().expect("references must not be null")
 * }
 * ```
 */
public fun <T> sliceFromRawParts(data: ConstPtr, len: Int): ConstPtr = ConstPtr(data.rawAddress, len.toLong())

/**
 * Forms a raw mutable slice from a pointer and a length.
 *
 * The `len` argument is the number of **elements**, not the number of bytes.
 *
 * Performs the same functionality as [sliceFromRawParts], except that a
 * raw mutable slice is returned, as opposed to a raw immutable slice.
 *
 * This function is safe, but actually using the return value is unchecked.
 * See the documentation of [slice.fromRawPartsMut] for slice safety requirements.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr
 *
 * val x = listOf(5, 6, 7)
 * val rawPointer = x.asMutPtr()
 * val slice = ptr.sliceFromRawPartsMut(rawPointer, 3)
 * run {
 *     slice[2] = 99
 *     // assign a value at an index in the slice
 * }
 * check(slice[2] == 99)
 * ```
 *
 * You must ensure that the pointer is valid and not null before dereferencing
 * the raw slice. A slice reference must never have a null pointer, even if it's empty.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * val danger: MutPtr = ptr.sliceFromRawPartsMut(ptr.nullMut(), 0)
 * run {
 *     danger.asMut().expect("references must not be null")
 * }
 * ```
 */
public fun <T> sliceFromRawPartsMut(data: MutPtr, len: Int): MutPtr = MutPtr(data.rawAddress, len.toLong())

/**
 * Swaps the values at two mutable locations of the same type, without
 * deinitializing either.
 *
 * But for the following exceptions, this function is semantically
 * equivalent to [mem.swap]:
 *
 * * It operates on raw pointers instead of references. When references are
 *   available, [mem.swap] should be preferred.
 *
 * * The two pointed-to values may overlap. If the values do overlap, then the
 *   overlapping region of memory from `x` will be used. This is demonstrated
 *   in the second example below.
 *
 * * The operation is "untyped" in the sense that data may be uninitialized or otherwise violate
 *   the requirements of `T`. The initialization state is preserved exactly.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * Both `x` and `y` must be [valid] for both reads and writes. They must remain valid even when the
 *   other pointer is written. (This means if the memory ranges overlap, the two pointers must not
 *   be subject to aliasing restrictions relative to each other.)
 *
 * * Both `x` and `y` must be properly aligned.
 *
 * Note that even if `T` has size `0`, the pointers must be properly aligned.
 *
 * [valid]: self#safety
 *
 * # Examples
 *
 * Swapping two non-overlapping regions:
 *
 * ```kotlin
 * import std.ptr
 *
 * var array = mutableListOf(0, 1, 2, 3)
 * val (x, y) = array.splitAtMut(2)
 * val x1 = x.asMutPtr().cast()
 * // this is `array[0 .. 2]`
 * val y1 = y.asMutPtr().cast()
 * // this is `array[2 .. 4]`
 * run {
 *     ptr.swap(x1, y1)
 *     check(listOf(2, 3, 0, 1) == array)
 * }
 * ```
 *
 * Swapping two overlapping regions:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.ptr
 *
 * val localStorageL5C0 = AddressableArrayLocal.fromIntArray(intArrayOf(0, 1, 2, 3))
 * var array by localStorageL5C0
 * val arrayPtr: MutPtr = localStorageL5C0.elementMutPtr(0)
 * val x = MutPtr(arrayPtr.rawAddress, arrayPtr.rawMeta)
 * // this is `array[0 .. 3]`
 * val y = (MutPtr((arrayPtr).rawAddress + ((1).toLong() * (PrimitiveLayouts.I32).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 * // this is `array[1 .. 4]`
 * run {
 *     ptr.swap(x, y)
 *     // The indices `1 .. 3` of the slice overlap between `x` and `y`.
 *     // Reasonable results would be for to them be `[2, 3]`, so that indices `0 .. 3` are
 *     // `[1, 2, 3]` (matching `y` before the `swap`); or for them to be `[0, 1]`
 *     // so that indices `1 .. 4` are `[0, 1, 2]` (matching `x` before the `swap`).
 *     // This implementation is defined to make the latter choice.
 *     check(intArrayOf(1, 0, 1, 2) == array)
 * }
 * ```
 */
public fun <T> swap(x: MutPtr, y: MutPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // Give ourselves some scratch space to work with.
    // We do not have to worry about drops: `MaybeUninit` does nothing when dropped.
    val tmp = MaybeUninit.uninit(typeLayoutT)
    // Perform the swap
    // SAFETY: the caller must guarantee that `x` and `y` are
    // valid for writes and properly aligned. `tmp` cannot be
    // overlapping either `x` or `y` because `tmp` was just allocated
    // on the stack as a separate allocation.
    run {
        copyNonoverlapping((x).asConstPtr(), tmp.asMutPtr(), 1, typeLayoutT = typeLayoutT)
        copy((y).asConstPtr(), x, 1, typeLayoutT = typeLayoutT)
        // `x` and `y` may overlap
        copyNonoverlapping(tmp.asPtr(), y, 1, typeLayoutT = typeLayoutT)
    }
}

// both implementations behave the same

/**
 * Swaps `count * sizeOf<T>()` bytes between the two regions of memory
 * beginning at `x` and `y`. The two regions must *not* overlap.
 *
 * The operation is "untyped" in the sense that data may be uninitialized or otherwise violate the
 * requirements of `T`. The initialization state is preserved exactly.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * Both `x` and `y` must be [valid] for both reads and writes of `count *
 *   sizeOf::<T>()` bytes.
 *
 * * Both `x` and `y` must be properly aligned.
 *
 * * The region of memory beginning at `x` with a size of `count *
 *   sizeOf::<T>()` bytes must *not* overlap with the region of memory
 *   beginning at `y` with the same size.
 *
 * Note that even if the effectively copied size (`count * sizeOf<T>()`) is `0`,
 * the pointers must be properly aligned.
 *
 * [valid]: self#safety
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
 * import std.ptr
 *
 * val localStorageL5C0 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3, 4))
 * var x by localStorageL5C0
 * val localStorageL6C0 = AddressableArrayLocal.fromIntArray(intArrayOf(7, 8, 9))
 * var y by localStorageL6C0
 * run {
 *     ptr.swapNonoverlapping(localStorageL5C0.elementMutPtr(0), localStorageL6C0.elementMutPtr(0), 2)
 * }
 * check(x == intArrayOf(7, 8, 3, 4))
 * check(y == intArrayOf(1, 2, 9))
 * ```
 */
public fun <T> swapNonoverlapping(x: MutPtr, y: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    val bytes = if (typeLayoutT.sizeBytes >= 0L) (count * typeLayoutT.sizeBytes.toInt()) else count
    when (val matchVal = NonZero.new<NonZeroUsizeInner, Int>(bytes)) {
        null -> {}
        else -> swapNonoverlappingBytes(x, y, matchVal)
    }
}

/**
 * Same behavior and safety conditions as [swapNonoverlapping]
 */
public fun <T> swapNonoverlappingConst(x: MutPtr, y: MutPtr, count: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    var i = 0
    while (i < count) {
        val x1 = MutPtr((x).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes))
        val y1 = MutPtr((y).rawAddress + ((i).toLong() * (typeLayoutT).strideBytes))
        run {
            val temp = MaybeUninit.uninit(typeLayoutT)
            copyNonoverlapping(ConstPtr(x1.rawAddress, x1.rawMeta), temp.asMutPtr(), 1, typeLayoutT = typeLayoutT)
            copyNonoverlapping(ConstPtr(y1.rawAddress, y1.rawMeta), x1, 1, typeLayoutT = typeLayoutT)
            copyNonoverlapping(temp.asPtr(), y1, 1, typeLayoutT = typeLayoutT)
        }
        i += 1
    }
}

public fun swapChunk(x: MutPtr, y: MutPtr, byteCount: Int = 1) {
    if (byteCount <= 0 || x.rawAddress == y.rawAddress) return
    for (i in 0 until byteCount) {
        val px = MutPtr(x.rawAddress + i.toLong())
        val py = MutPtr(y.rawAddress + i.toLong())
        val a = PrimitiveLayouts.U8.readNative(px.rawAddress)
        val b = PrimitiveLayouts.U8.readNative(py.rawAddress)
        PrimitiveLayouts.U8.writeNative(px.rawAddress, b)
        PrimitiveLayouts.U8.writeNative(py.rawAddress, a)
    }
}

public fun swapNonoverlappingBytes(x: MutPtr, y: MutPtr, bytes: NonZero<Int, NonZeroUsizeInner>) {
    val total = bytes.get()
    val chunkSize = sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L)).coerceAtLeast(1)
    val chunks = total / chunkSize
    val tail = total % chunkSize
    if (chunks > 0) {
        swapChunk(x, y, chunks * chunkSize)
    }
    if (tail > 0) {
        val delta = (chunks * chunkSize).toLong()
        swapChunk(MutPtr(x.rawAddress + delta), MutPtr(y.rawAddress + delta), tail)
    }
}

/**
 * Moves `src` into the pointed `dst`, returning the previous `dst` value.
 *
 * Neither value is dropped.
 *
 * This function is semantically equivalent to [mem.replace] except that it
 * operates on raw pointers instead of references. When references are
 * available, [mem.replace] should be preferred.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `dst` must be [valid] for both reads and writes or `T` must be a ZST.
 *
 * * `dst` must be properly aligned.
 *
 * * `dst` must point to a properly initialized value of type `T`.
 *
 * Note that even if `T` has size `0`, the pointer must be properly aligned.
 *
 * [valid]: self#safety
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr
 *
 * var rust = mutableListOf('b', 'u', 's', 't')
 * // `mem.replace` would have the same effect without requiring the unchecked
 * // block.
 * val b = ptr.replace(rust[0], 'r')
 * check(b == 'b')
 * check(rust == listOf('r', 'u', 's', 't'))
 * ```
 */
public fun <T> replace(dst: MutPtr, src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T =
    if (typeLayoutT.sizeBytes == 0L) {
        src
    } else {
        val old = read(ConstPtr(dst.rawAddress, dst.rawMeta), typeLayoutT)
        write(dst, src, typeLayoutT)
        old
    }

/**
 * Reads the value from `src` without moving it. This leaves the
 * memory in `src` unchanged.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `src` must be [valid] for reads or `T` must be a ZST.
 *
 * * `src` must be properly aligned. Use [readUnaligned] if this is not the
 *   case.
 *
 * * `src` must point to a properly initialized value of type `T`.
 *
 * Note that even if `T` has size `0`, the pointer must be properly aligned.
 *
 * Care must be taken around [ownership of the returned value](#ownership-of-the-returned-value).
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val localStorageL3C0 = AddressableLocal(12, PrimitiveLayouts.I32)
 * val y = ConstPtr(localStorageL3C0.asMutPtr().rawAddress)
 * run {
 *     check(read(y) == 12)
 * }
 * ```
 *
 * Manually implement [mem.swap]:
 *
 * ```kotlin
 * import std.ptr
 *
 * fun <T> swap(a: T, b: T) {
 *     run {
 *         // Create a bitwise copy of the value at `a` in `tmp`.
 *         val tmp = ptr.read(a)
 *         // Exiting at this point (either by explicitly returning or by
 *         // calling a function which panics) would cause the value in `tmp` to
 *         // be dropped while the same value is still referenced by `a`. This
 *         // could trigger undefined behavior if `T` is not `Copy`.
 *         // Create a bitwise copy of the value at `b` in `a`.
 *         // This is safe because mutable references cannot alias.
 *         ptr.copyNonoverlapping(b, a, 1)
 *         // As above, exiting here could trigger undefined behavior because
 *         // the same value is referenced by `a` and `b`.
 *         // Move `tmp` into `b`.
 *         ptr.write(b, tmp)
 *         // `tmp` has been moved (`write` takes ownership of its second argument),
 *         // so nothing is dropped implicitly here.
 *     }
 * }
 * var foo = "foo".toOwned()
 * var bar = "bar".toOwned()
 * swap(foo, bar)
 * check(foo == "bar")
 * check(bar == "foo")
 * ```
 *
 * ## Ownership of the Returned Value
 *
 * `read` creates a bitwise copy of `T`, regardless of whether `T` is [Copy].
 * If `T` is not [Copy], using both the returned value and the value at
 * `* src` can violate memory safety. Note that assigning to `* src` counts as a
 * use because it will attempt to drop the value at `* src`.
 *
 * [write] can be used to overwrite data without causing it to be dropped.
 *
 * ```kotlin
 * import std.ptr
 *
 * var s = "foo"
 * run {
 *     // `s2` now points to the same underlying memory as `s`.
 *     var s2: String = ptr.read(s)
 *     check(s2 == "foo")
 *     // Assigning to `s2` causes its original value to be dropped. Beyond
 *     // this point, `s` must no longer be used, as the underlying memory has
 *     // been freed.
 *     s2 = String.default()
 *     check(s2 == "")
 *     // Assigning to `s` would cause the old value to be dropped again,
 *     // resulting in undefined behavior.
 *     // s = String.from("bar"); // ERROR
 *     // `ptr.write` can be used to overwrite a value without dropping it.
 *     ptr.write(s, "bar")
 * }
 * check(s == "bar")
 * ```
 *
 * [valid]: self#safety
 */
public fun <T> read(src: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // It would be semantically correct to implement this via `copyNonoverlapping`
    // and `MaybeUninit`, as was done before PR #109035. Calling `assumeInit`
    // provides enough information to know that this is a typed operation.
    // However, as of March 2023 the compiler was not capable of taking advantage
    // of that information. Thus, the implementation here switched to an intrinsic,
    // which lowers to `_0 = * src` in MIR, to address a few issues:
    //
    // - Using `MaybeUninit.assumeInit` after a `copyNonoverlapping` was not
    // turning the untyped copy into a typed load. As such, the generated
    // `load` in LLVM didn't get various metadata, such as `!range` (#73258),
    // `!nonnull`, and `!noundef`, resulting in poorer optimization.
    // - Going through the extra local resulted in multiple extra copies, even
    // in optimized MIR.  (Ignoring StorageLive/Dead, the intrinsic is one
    // MIR statement, while the previous implementation was eight.)  LLVM
    // could sometimes optimize them away, but because `read` is at the core
    // of so many things, not having them in the first place improves what we
    // hand off to the backend.  For example, `mem.replace<Big>` previously
    // emitted 4 `alloca` and 6 `memcpy`s, but is now 1 `alloc` and 3 `memcpy`s.
    // - In general, this approach keeps us from getting any more bugs (like
    // #106369) that boil down to "`read(p)` is worse than `* p`", as this
    // makes them look identical to the backend (or other MIR consumers).
    //
    // Future enhancements to MIR optimizations might well allow this to return
    // to the previous implementation, rather than using an intrinsic.
    // SAFETY: the caller must guarantee that `src` is valid for reads.
    return (typeLayoutT).readNative(src.rawAddress)
}

/**
 * Reads the value from `src` without moving it. This leaves the
 * memory in `src` unchanged.
 *
 * Unlike [read], `readUnaligned` works with unaligned pointers.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `src` must be [valid] for reads.
 *
 * * `src` must point to a properly initialized value of type `T`.
 *
 * Like [read], `readUnaligned` creates a bitwise copy of `T`, regardless of
 * whether `T` is [Copy]. If `T` is not [Copy], using both the returned
 * value and the value at `* src` can [violate memory safety][read-ownership].
 *
 * [read-ownership]: read#ownership-of-the-returned-value
 * [valid]: self#safety
 *
 * ## On `packed` structs
 *
 * Attempting to create a raw pointer to an `unaligned` struct field with
 * an expression such as `packed.unaligned as * const FieldType` creates an
 * intermediate unaligned reference before converting that to a raw pointer.
 * That this reference is temporary and immediately cast is inconsequential
 * as the compiler always expects references to be properly aligned.
 * As a result, using `packed.unaligned as * const FieldType` causes immediate
 * *undefined behavior* in your program.
 *
 * Instead you must use the `raw const` syntax to create the pointer.
 * You may use that constructed pointer together with this function.
 *
 * An example of what not to do and how this relates to `readUnaligned` is:
 *
 * ```kotlin
 * @repr(packed, C)
 * open class Packed(public var padding: UByte, public var unaligned: UInt)
 * val packed = Packed(padding = 0x00u.toUByte(), unaligned = 0x01020304u)
 * // Take the address of a 32-bit integer which is not aligned.
 * // In contrast to `packed.unaligned as * const _`, this has no undefined behavior.
 * val unaligned = addrOf(packed.unaligned)
 * val v = readUnaligned(unaligned)
 * check(v == 0x01020304)
 * ```
 *
 * Accessing unaligned fields directly with e.g. `packed.unaligned` is safe however.
 *
 * # Examples
 *
 * Read a `Int` value from a byte buffer:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun readUsize(x: List<UByte>): Int {
 *     check(x.size >= sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
 *     val ptr = (x.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     return (PrimitiveLayouts.USIZE64).readNative((ptr).rawAddress)
 * }
 * ```
 */
public fun <T> readUnaligned(src: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // Always true thanks to the repr, but to demonstrate
    run {
        check(0 == 0)
        check(sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Unaligned<T>>(typeLayoutT = Unaligned.typeLayout<T>(typeLayoutT = typeLayoutT)))
    }
    val src1 = ConstPtr((src).rawAddress)
    // SAFETY: the caller must guarantee that `src` is valid for reads.
    // Reading it as `Unaligned<T>` instead of `T` reads those same bytes because
    // it's the same size (thus zero offset), but with alignment 1 instead.
    //
    // Similarly, because it's the same bytes it's sound to reinterpret from the
    // `Unaligned<T>` to `T`.  Transmute is a value-based (not a place-based)
    // operation that doesn't care about alignment.
    return run {
        val unaligned = read(src1, typeLayoutT = Unaligned.typeLayout<T>(typeLayoutT = typeLayoutT))
        // Can't just destructure because that's not allowed in const fn
        transmuteNeo(unaligned)
    }
}

/**
 * Overwrites a memory location with the given value without reading or
 * dropping the old value.
 *
 * `write` does not drop the contents of `dst`. This is safe, but it could leak
 * allocations or resources, so care should be taken not to overwrite an object
 * that should be dropped.
 *
 * Additionally, it does not drop `src`. Semantically, `src` is moved into the
 * location pointed to by `dst`.
 *
 * This is appropriate for initializing uninitialized memory, or overwriting
 * memory that has previously been [read] from.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `dst` must be [valid] for writes or `T` must be a ZST.
 *
 * * `dst` must be properly aligned. Use [writeUnaligned] if this is not the
 *   case.
 *
 * Note that even if `T` has size `0`, the pointer must be properly aligned.
 *
 * [valid]: self#safety
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableLocal(0, PrimitiveLayouts.I32)
 * val y = MutPtr(localStorageL3C0.asMutPtr().rawAddress)
 * val z = 12
 * run {
 *     write(y, z)
 *     check(read((y).asConstPtr()) == 12)
 * }
 * ```
 *
 * Manually implement [mem.swap]:
 *
 * ```kotlin
 * import std.ptr
 *
 * fun <T> swap(a: T, b: T) {
 *     run {
 *         // Create a bitwise copy of the value at `a` in `tmp`.
 *         val tmp = ptr.read(a)
 *         // Exiting at this point (either by explicitly returning or by
 *         // calling a function which panics) would cause the value in `tmp` to
 *         // be dropped while the same value is still referenced by `a`. This
 *         // could trigger undefined behavior if `T` is not `Copy`.
 *         // Create a bitwise copy of the value at `b` in `a`.
 *         // This is safe because mutable references cannot alias.
 *         ptr.copyNonoverlapping(b, a, 1)
 *         // As above, exiting here could trigger undefined behavior because
 *         // the same value is referenced by `a` and `b`.
 *         // Move `tmp` into `b`.
 *         ptr.write(b, tmp)
 *         // `tmp` has been moved (`write` takes ownership of its second argument),
 *         // so nothing is dropped implicitly here.
 *     }
 * }
 * var foo = "foo".toOwned()
 * var bar = "bar".toOwned()
 * swap(foo, bar)
 * check(foo == "bar")
 * check(bar == "foo")
 * ```
 */
public fun <T> write(dst: MutPtr, src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // Semantically, it would be fine for this to be implemented as a
    // `copyNonoverlapping` and appropriate drop suppression of `src`.
    // However, implementing via that currently produces more MIR than is ideal.
    // Using an intrinsic keeps it down to just the simple `* dst = move src` in
    // MIR (11 statements shorter, at the time of writing), and also allows
    // `src` to stay an SSA value in codegenSsa, rather than a memory one.
    // SAFETY: the caller must guarantee that `dst` is valid for writes.
    // `dst` cannot overlap `src` because the caller has mutable access
    // to `dst` while `src` is owned by this function.
    (typeLayoutT).writeNative(dst.rawAddress, src)
}

/**
 * Overwrites a memory location with the given value without reading or
 * dropping the old value.
 *
 * Unlike [write], the pointer may be unaligned.
 *
 * `writeUnaligned` does not drop the contents of `dst`. This is safe, but it
 * could leak allocations or resources, so care should be taken not to overwrite
 * an object that should be dropped.
 *
 * Additionally, it does not drop `src`. Semantically, `src` is moved into the
 * location pointed to by `dst`.
 *
 * This is appropriate for initializing uninitialized memory, or overwriting
 * memory that has previously been read with [readUnaligned].
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `dst` must be [valid] for writes.
 *
 * [valid]: self#safety
 *
 * ## On `packed` structs
 *
 * Attempting to create a raw pointer to an `unaligned` struct field with
 * an expression such as `packed.unaligned as * const FieldType` creates an
 * intermediate unaligned reference before converting that to a raw pointer.
 * That this reference is temporary and immediately cast is inconsequential
 * as the compiler always expects references to be properly aligned.
 * As a result, using `packed.unaligned as * const FieldType` causes immediate
 * *undefined behavior* in your program.
 *
 * Instead, you must use the `raw` syntax to create the pointer.
 * You may use that constructed pointer together with this function.
 *
 * An example of how to do it and how this relates to `writeUnaligned` is:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
 * import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
 *
 * @repr(packed, C)
 * open class Packed(public var padding: UByte, public var unaligned: UInt)
 * var packed: Packed = run { val ptr = kmAllocZeroed(maxOf(1L, (Packed.typeLayout()).sizeBytes), maxOf(1L, (Packed.typeLayout()).alignBytes)); val res = (Packed.typeLayout()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (Packed.typeLayout()).sizeBytes), maxOf(1L, (Packed.typeLayout()).alignBytes)); res }
 * // Take the address of a 32-bit integer which is not aligned.
 * // In contrast to `packed.unaligned as * _`, this has no undefined behavior.
 * val unaligned = addrOfMut(packed.unaligned)
 * writeUnaligned(unaligned, 42)
 * check(packed.unaligned == 42u)
 * // `( ... )` forces copying the field instead of creating a reference.
 * ```
 *
 * Accessing unaligned fields directly with e.g. `packed.unaligned` is safe however
 * (as can be seen in the `assertEq` above).
 *
 * # Examples
 *
 * Write a `Int` value to a byte buffer:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * fun writeUsize(x: MutableList<UByte>, value: Int) {
 *     check(x.size >= sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
 *     val ptr = (x.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     (PrimitiveLayouts.USIZE64).writeNative((ptr).rawAddress, value)
 * }
 * ```
 */
public fun <T> writeUnaligned(dst: MutPtr, src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // Always true thanks to the repr, but to demonstrate
    run {
        check(0 == 0)
        check(sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Unaligned<T>>(typeLayoutT = Unaligned.typeLayout<T>(typeLayoutT = typeLayoutT)))
    }
    val dst1 = MutPtr((dst).rawAddress)
    val src1 = Unaligned(src)
    // SAFETY: the caller must guarantee that `dst` is valid for writes.
    // Writing it as `Unaligned<T>` instead of `T` writes those same bytes because
    // it's the same size (thus zero offset), but with alignment 1 instead.
    write(dst1, src1, typeLayoutT = Unaligned.typeLayout<T>(typeLayoutT = typeLayoutT))
}

/**
 * Performs a volatile read of the value from `src` without moving it.
 *
 * Volatile operations are intended to act on I/O memory. As such, they are considered externally
 * observable events (just like syscalls, but less opaque), and are guaranteed to not be elided or
 * reordered by the compiler across other externally observable events. With this in mind, there
 * are two cases of usage that need to be distinguished:
 *
 * - When a volatile operation is used for memory inside an [allocation], it behaves exactly like
 *   [read], except for the additional guarantee that it won't be elided or reordered across
 *   other externally observable events (see above). This implies that the operation will actually
 *   access memory and not e.g. be lowered to reusing data from a previous read. Other than that,
 *   all the usual rules for memory accesses apply (including provenance). In particular, just
 *   like in C, whether an operation is volatile has no bearing whatsoever on questions involving
 *   concurrent accesses from multiple threads. Volatile accesses behave exactly like non-atomic
 *   accesses in that regard.
 *
 * - Volatile operations, however, may also be used to access memory that is _outside_ of any Rust
 *   allocation. In this use-case, the pointer does *not* have to be [valid] for reads. This is
 *   typically used for CPU and peripheral registers that must be accessed via an I/O memory
 *   mapping, most commonly at fixed addresses reserved by the hardware. These often have special
 *   semantics associated to their manipulation, and cannot be used as general purpose memory.
 *   Here, any address value is possible, including 0 and [usize.Max], so long as the semantics
 *   of such a read are well-defined by the target hardware. The provenance of the pointer is
 *   irrelevant, and it can be created with [withoutProvenance]. The access is allowed to trap,
 *   which must immediately abort the process. It can also cause other side-effects, but those
 *   must not affect Rust-allocated memory in any way. This access is still not considered
 *   [atomic], and as such it cannot be used for inter-thread synchronization.
 *
 * Note that volatile memory operations where T is a zero-sized type are noops and may be ignored.
 *
 * When invoked during const evaluation, this behaves like a regular read. In particular, such
 * reads must always follow the first of the two cases above.
 *
 * # Load splitting
 *
 * Exactly which hardware loads are performed by this function is, in general, highly target-dependent.
 *
 * For a simple scalar, such as when `T` is a thin pointer, this will typically be one load assuming
 * your target supports a load of exactly that size and alignment.
 *
 * For anything else, it will be split into multiple loads in some unspecified way.
 * This can happen even for scalars: notably, on many targets loading a `ULong` will still need to be split,
 * despite being "one" scalar.  On many targets loading anything larger than a pointer will need to be split.
 * On all current targets a load larger than 64 bytes will need to be split.
 * Any load whose size is not a power of two will also almost certainly need to be split.
 *
 * There is no stability guarantee on how that splitting happens.  It may change at any point.
 *
 * # Safety
 *
 * Like [read], `readVolatile` creates a bitwise copy of `T`, regardless of whether `T` is
 * [Copy]. If `T` is not [Copy], using both the returned value and the value at `* src` can
 * [violate memory safety][read-ownership]. However, storing non-[Copy] types in volatile memory
 * is almost certainly incorrect.
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `src` must be either [valid] for reads, or `T` must be a ZST, or `src` must point to memory
 *   outside of all Rust allocations and reading from that memory must not cause any memory inside
 *   a Rust allocation to be modified.
 *
 * * `src` must be properly aligned.
 *
 * * Reading from `src` must produce a properly initialized value of type `T`.
 *
 * Note that even if `T` has size `0`, the pointer must be properly aligned.
 *
 * [valid]: self#safety
 * [read-ownership]: read#ownership-of-the-returned-value
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * val localStorageL3C0 = AddressableLocal(12, PrimitiveLayouts.I32)
 * val y = ConstPtr(localStorageL3C0.asMutPtr().rawAddress)
 * run {
 *     check(readVolatile(y) == 12)
 * }
 * ```
 */
public fun <T> readVolatile(src: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // SAFETY: the caller must uphold the safety contract for `volatileLoad`.
    return (typeLayoutT).readVolatileNative(src.rawAddress)
}

/**
 * Performs a volatile write of a memory location with the given value without reading or dropping
 * the old value.
 *
 * Volatile operations are intended to act on I/O memory. As such, they are considered externally
 * observable events (just like syscalls), and are guaranteed to not be elided or reordered by the
 * compiler across other externally observable events. With this in mind, there are two cases of
 * usage that need to be distinguished:
 *
 * - When a volatile operation is used for memory inside an [allocation], it behaves exactly like
 *   [`write`][write()], except for the additional guarantee that it won't be elided or reordered
 *   across other externally observable events (see above). This implies that the operation will
 *   actually access memory and not e.g. be lowered to a register access. Other than that, all the
 *   usual rules for memory accesses apply (including provenance). In particular, just like in C,
 *   whether an operation is volatile has no bearing whatsoever on questions involving concurrent
 *   access from multiple threads. Volatile accesses behave exactly like non-atomic accesses in
 *   that regard.
 *
 * - Volatile operations, however, may also be used to access memory that is _outside_ of any Rust
 *   allocation. In this use-case, the pointer does *not* have to be [valid] for writes. This is
 *   typically used for CPU and peripheral registers that must be accessed via an I/O memory
 *   mapping, most commonly at fixed addresses reserved by the hardware. These often have special
 *   semantics associated to their manipulation, and cannot be used as general purpose memory.
 *   Here, any address value is possible, including 0 and [usize.Max], so long as the semantics
 *   of such a write are well-defined by the target hardware. The provenance of the pointer is
 *   irrelevant, and it can be created with [withoutProvenanceMut]. The access is allowed to
 *   trap, which must immediately abort the process. It can also cause other side-effects, but
 *   those must not affect Rust-allocated memory in any way. This access is still not considered
 *   [atomic], and as such it cannot be used for inter-thread synchronization.
 *
 * Note that volatile memory operations on zero-sized types (e.g., if a zero-sized type is passed
 * to `writeVolatile`) are noops and may be ignored.
 *
 * `writeVolatile` does not drop the contents of `dst`. This is safe, but it could leak
 * allocations or resources, so care should be taken not to overwrite an object that should be
 * dropped when operating on Rust memory. Additionally, it does not drop `src`. Semantically, `src`
 * is moved into the location pointed to by `dst`.
 *
 * When invoked during const evaluation, this behaves like a regular write. In particular, such
 * reads must always follow the first of the two cases above.
 *
 * # Store splitting
 *
 * Exactly which hardware stores are performed by this function is, in general, highly target-dependent.
 *
 * For a simple scalar, such as when `T` is a thin pointer, this will typically be one store assuming
 * your target supports a store of exactly that size and alignment.
 *
 * For anything else, it will be split into multiple stores in some unspecified way.
 * This can happen even for scalars: notably, on many targets storing a `ULong` will still need to be split,
 * despite being "one" scalar.  On many targets storing anything larger than a pointer will need to be split.
 * On all current targets a store larger than 64 bytes will need to be split.
 * Any store whose size is not a power of two will also almost certainly need to be split.
 *
 * There is no stability guarantee on how that splitting happens.  It may change at any point.
 *
 * # Safety
 *
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * `dst` must be either [valid] for writes, or `T` must be a ZST, or `dst` must point to memory
 *   outside of all Rust allocations and writing to that memory must not cause any memory inside a
 *   Rust allocation to be modified.
 *
 * * `dst` must be properly aligned.
 *
 * Note that even if `T` has size `0`, the pointer must be properly aligned.
 *
 * [valid]: self#safety
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * val localStorageL3C0 = AddressableLocal(0, PrimitiveLayouts.I32)
 * val y = MutPtr(localStorageL3C0.asMutPtr().rawAddress)
 * val z = 12
 * run {
 *     writeVolatile(y, z)
 *     check(readVolatile((y).asConstPtr()) == 12)
 * }
 * ```
 */
public fun <T> writeVolatile(dst: MutPtr, src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY: the caller must uphold the safety contract for `volatileStore`.
    (typeLayoutT).writeVolatileNative(dst.rawAddress, src)
}

/**
 * Calculate an element-offset that increases a pointer's alignment.
 *
 * Calculate an element-offset (not byte-offset) that when added to a given pointer `p`, increases `p`s alignment to at least the given alignment `a`.
 *
 * # Safety
 * `a` must be a power of two.
 *
 * # Notes
 * This implementation has been carefully tailored to not panic. It is UB for this to panic.
 * The only real change that can be made here is change of `INV_TABLE_MOD_16` and associated
 * constants.
 *
 * If we ever decide to make it possible to call the intrinsic with `a` that is not a
 * power-of-two, it will probably be more prudent to just change to a naive implementation rather
 * than trying to adapt this to accommodate that change.
 *
 * Any questions go to @nagisa.
 */
public fun <T> alignOffset(p: ConstPtr, a: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    /**
     * Calculate multiplicative modular inverse of `x` modulo `m`.
     *
     * This implementation is tailored for `alignOffset` and has following preconditions:
     *
     * * `m` is a power-of-two;
     * * `x<m`; (if `x ≥ m`, pass in `x % m` instead)
     *
     * Implementation of this function shall not panic. Ever.
     */
    fun modInv(x: Int, m: Int): Int {
        /**
         * Multiplicative modular inverse table modulo 2⁴ = 16.
         *
         * Note, that this table does not contain values where inverse does not exist (i.e., for
         * `0⁻¹ mod 16`, `2⁻¹ mod 16`, etc.)
         */
        val INV_TABLE_MOD_16: UByteArray = ubyteArrayOf(1u.toUByte(), 11u.toUByte(), 13u.toUByte(), 7u.toUByte(), 9u.toUByte(), 3u.toUByte(), 5u.toUByte(), 15u.toUByte())

        /**
         * Modulo for which the `INV_TABLE_MOD_16` is intended.
         */
        val INV_TABLE_MOD: Int = 16
        // SAFETY: `m` is required to be a power-of-two, hence non-zero.
        val mMinusOne = uncheckedSub(m, 1)
        var inverse = INV_TABLE_MOD_16[(x and (INV_TABLE_MOD - 1)) shr 1].toInt()
        var modGate = INV_TABLE_MOD
        // We iterate "up" using the following formula:
        //
        // $$ xy ≡ 1 (mod 2ⁿ) → xy (2 - xy) ≡ 1 (mod 2²ⁿ) $$
        //
        // This application needs to be applied at least until `2²ⁿ ≥ m`, at which point we can
        // finally reduce the computation to our desired `m` by taking `inverse mod m`.
        //
        // This computation is `O(log log m)`, which is to say, that on 64-bit machines this loop
        // will always finish in at most 4 iterations.
        while (true) {
            // y = y * (2 - xy) mod n
            //
            // Note, that we use wrapping operations here intentionally – the original formula
            // uses e.g., subtraction `mod n`. It is entirely fine to do them `mod
            // usize.Max`instead, because we take the result`mod n` at the end
            // anyway.
            if (modGate >= m) {
                break
            }
            inverse = wrappingMul(inverse, wrappingSub(2, wrappingMul(x, inverse)))
            val (newGate, overflow) = mulWithOverflow(modGate, modGate)
            if (overflow) {
                break
            }
            modGate = newGate
        }
        return inverse and mMinusOne
    }
    val stride = sizeOf<T>(typeLayoutT = typeLayoutT)
    val addr: Int = (p).rawAddress.toInt()
    // SAFETY: `a` is a power-of-two, therefore non-zero.
    val aMinusOne = uncheckedSub(a, 1)
    if (stride == 0) {
        // SPECIAL_CASE: handle 0-sized types. No matter how many times we step, the address will
        // stay the same, so no offset will be able to align the pointer unless it is already
        // aligned. This branch _will_ be optimized out as `stride` is known at compile-time.
        val pModA = addr and aMinusOne
        return if (pModA == 0) {
            0
        } else {
            Int.MAX_VALUE
        }
    }
    // SAFETY: `stride == 0` case has been handled by the special case above.
    val aModStride = uncheckedRem(a, stride)
    if (aModStride == 0) {
        // SPECIAL_CASE: In cases where the `a` is divisible by `stride`, byte offset to align a
        // pointer can be computed more simply through `- p(mod a)`. In the off-chance the byte
        // offset is not a multiple of `stride`, the input pointer was misaligned and no pointer
        // offset will be able to produce a `p` aligned to the specified `a`.
        //
        // The naive `- p(mod a)` equation inhibits LLVM's ability to select instructions
        // like `lea`. We compute `(roundUpToNextAlignment(p, a) - p)` instead. This
        // redistributes operations around the load-bearing, but pessimizing `and` instruction
        // sufficiently for LLVM to be able to utilize the various optimizations it knows about.
        //
        // LLVM handles the branch here particularly nicely. If this branch needs to be evaluated
        // at runtime, it will produce a mask `if addrModStride == 0 ( 0 ) else ( Int.MAX )`
        // in a branch-free way and then bitwise-OR it with whatever result the `- p mod a`
        // computation produces.
        val alignedAddress = wrappingAdd(addr, aMinusOne) and wrappingSub(0, a)
        val byteOffset = wrappingSub(alignedAddress, addr)
        // Note: Remove the assume after <https://github.com/llvm/llvm-project/issues/62502>
        // SAFETY: Masking by `- a` can only affect the low bits, and thus cannot have reduced
        // the value by more than `a - 1`, so even though the intermediate values might have
        // wrapped, the byteOffset is always in `[0, a)`.
        assume(byteOffset < a)
        // SAFETY: `stride == 0` case has been handled by the special case above.
        val addrModStride = uncheckedRem(addr, stride)
        return if (addrModStride == 0) {
            // SAFETY: `stride` is non-zero. This is guaranteed to divide exactly as well, because
            // addr has been verified to be aligned to the original type’s alignment requirements.
            exactDiv(byteOffset, stride)
        } else {
            Int.MAX_VALUE
        }
    }
    // GENERAL_CASE: From here on we’re handling the very general case where `addr` may be
    // misaligned, there isn’t an obvious relationship between `stride` and `a` that we can take an
    // advantage of, etc. This case produces machine code that isn’t particularly high quality,
    // compared to the special cases above. The code produced here is still within the realm of
    // miracles, given the situations this case has to deal with.
    // SAFETY: a is power-of-two hence non-zero. stride == 0 case is handled above.
    // Note(const-Note) replace with min
    val gcdpow =
        run {
            val x = cttzNonzero(stride)
            val y = cttzNonzero(a)
            if (x < y) {
                x
            } else {
                y
            }
        }
    // SAFETY: gcdpow has an upper-bound that’s at most the number of bits in a `Int`.
    val gcd = uncheckedShl(1, gcdpow)
    // SAFETY: gcd is always greater or equal to 1.
    if (addr and uncheckedSub(gcd, 1) == 0) {
        // This branch solves for the following linear congruence equation:
        //
        // `p + so = 0 mod a`
        //
        // `p` here is the pointer value, `s` - stride of `T`, `o` offset in `T`s, and `a` - the
        // requested alignment.
        //
        // With `g = gcd(a, s)`, and the above condition asserting that `p` is also divisible by
        // `g`, we can denote `a' = a/g`, `s' = s/g`, `p' = p/g`, then this becomes equivalent to:
        //
        // `p' + s'o = 0 mod a'`
        // `o = (a' - (p' mod a')) * (s'^-1 mod a')`
        //
        // The first term is "the relative alignment of `p` to `a`" (divided by the `g`), the
        // second term is "how does incrementing `p` by `s` bytes change the relative alignment of
        // `p`" (again divided by `g`). Division by `g` is necessary to make the inverse well
        // formed if `a` and `s` are not co-prime.
        //
        // Furthermore, the result produced by this solution is not "minimal", so it is necessary
        // to take the result `o mod lcm(s, a)`. This `lcm(s, a)` is the same as `a'`.
        // SAFETY: `gcdpow` has an upper-bound not greater than the number of trailing 0-bits in
        // `a`.
        val a2 = uncheckedShr(a, gcdpow)
        // SAFETY: `a2` is non-zero. Shifting `a` by `gcdpow` cannot shift out any of the set bits
        // in `a` (of which it has exactly one).
        val a2minus1 = uncheckedSub(a2, 1)
        // SAFETY: `gcdpow` has an upper-bound not greater than the number of trailing 0-bits in
        // `a`.
        val s2 = uncheckedShr(stride and aMinusOne, gcdpow)
        // SAFETY: `gcdpow` has an upper-bound not greater than the number of trailing 0-bits in
        // `a`. Furthermore, the subtraction cannot overflow, because `a2 = a>> gcdpow` will
        // always be strictly greater than `(p % a)>> gcdpow`.
        val minusp2 = uncheckedSub(a2, uncheckedShr(addr and aMinusOne, gcdpow))
        // SAFETY: `a2` is a power-of-two, as proven above. `s2` is strictly less than `a2`
        // because `(s % a)>> gcdpow` is strictly less than `a>> gcdpow`.
        return wrappingMul(minusp2, modInv(s2, a2)) and a2minus1
    }
    // Cannot be aligned at all.
    return Int.MAX_VALUE
}

// it's actually clear here

/**
 * Compares raw pointers for equality.
 *
 * This is the same as using the `==` operator, but less generic:
 * the arguments have to be `* const T` raw pointers,
 * not anything that implements `PartialEq`.
 *
 * This can be used to compare `T` references (which coerce to `* const T` implicitly)
 * by their address rather than comparing the values they point to
 * (which is what the `PartialEq for & T` implementation does).
 *
 * When comparing wide pointers, both the address and the metadata are tested for equality.
 * However, note that comparing trait object pointers (`* const dynamic Trait`) is unreliable: pointers
 * to values of the same underlying type can compare inequal (because vtables are duplicated in
 * multiple codegen units), and pointers to values of *different* underlying type can compare equal
 * (since identical vtables can be deduplicated within a codegen unit).
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr
 *
 * val five = 5
 * val otherFive = 5
 * val fiveRef = five
 * val sameFiveRef = five
 * val otherFiveRef = otherFive
 * check(fiveRef == sameFiveRef)
 * check(ptr.eq(fiveRef, sameFiveRef))
 * check(fiveRef == otherFiveRef)
 * check(!ptr.eq(fiveRef, otherFiveRef))
 * ```
 *
 * Slices are also compared by their length (fat pointers):
 *
 * ```kotlin
 * val a = listOf(1, 2, 3)
 * check(eq(a[0 until 3], a[0 until 3]))
 * check(!eq(a[0 until 2], a[0 until 3]))
 * check(!eq(a[0 until 2], a[1 until 3]))
 * ```
 */
public fun eq(a: ConstPtr, b: ConstPtr): Boolean = ((a).rawAddress.toULong() == (b).rawAddress.toULong())

/**
 * Compares the *addresses* of the two pointers for equality,
 * ignoring any metadata in fat pointers.
 *
 * If the arguments are thin pointers of the same type,
 * then this is the same as [eq].
 *
 * # Examples
 *
 * ```kotlin
 * import std.ptr
 *
 * val whole: IntArray = intArrayOf(1, 2, 3)
 * val first: Int = whole[0]
 * check(ptr.addrEq(whole, first))
 * check(!ptr.eq<Any>(whole, first))
 * ```
 */
public fun addrEq(p: ConstPtr, q: ConstPtr): Boolean = (((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress.toULong() == ((ConstPtr(q.rawAddress, q.rawMeta))).rawAddress.toULong())

/**
 * Compares the *addresses* of the two function pointers for equality.
 *
 * This is the same as `f == g`, but using this function makes clear that the potentially
 * surprising semantics of function pointer comparison are involved.
 *
 * There are **basically no guarantees** about how functions are compiled as they have no intrinsic
 * “identity”; in particular, this comparison:
 *
 * * May return `true` unexpectedly, even in cases where functions are not logically equivalent,
 *   as long as they compile to the same machine code.
 *
 *   For example, the following program is likely (but not guaranteed) to print `(true, true)`
 *   when compiled with optimization:
 *
 *   ```kotlin
 *   import std.hint.assertUnchecked
 *
 *   public fun interface FnCallback {
 *       public operator fun invoke(arg0: Int): Int
 *   }
 *
 *   public fun interface FnCallback1 {
 *       public operator fun invoke(arg0: UInt): UInt
 *   }
 *
 *   public fun doctestEntry() {
 *       val f: FnCallback = { x -> x }
 *       val g: FnCallback1 = { x -> x + 0 }
 *       // different type, different body
 *       val h: FnCallback = { x -> run {
 *           assertUnchecked(x != 0)
 *           x
 *       } }
 *       // different unchecked preconditions
 *       // These three functions are allowed but not guaranteed to compare equal.
 *       dbg(fnAddrEq(f, g), fnAddrEq(f, h))
 *   }
 *   ```
 *
 *   In other words, functions that compare equal can have different behavior! Even if
 *   `fnAddrEq(f, h)` is true, it is still undefined behavior to call `h(0)`, while `f(0)` is
 *   safe to call.
 *
 *   The explanation for this is that function pointers have [provenance][ptr#provenance].
 *   If functions have the same assembly and get merged, their provenance decides which variant of
 *   the function will be called. `f` and `h` therefore end up with the same address but different
 *   provenance, and `fnAddrEq` only compares the address.
 *
 * * May return `false` in any case.
 *
 *   This is particularly likely with generic functions but may happen with any function.
 *   (From an implementation perspective, this is possible because functions may sometimes be
 *   processed more than once by the compiler, resulting in duplicate machine code.)
 *
 * These limitations imply that comparing function pointers (via this function or via `==`) is only
 * useful in extremely niche circumstances. For instance, if a library maintains a set of
 * callbacks, and doesn't guarantee which of them are actually called (i.e., callbacks can be
 * spuriously skipped), then it can be legitimate to deduplicate callbacks based on `fnAddrEq`.
 * This can never introduce undefined behavior because it only skips executing some of the
 * registered callbacks.
 */
public fun <T : FnPtr<*>, U : FnPtr<*>> fnAddrEq(f: T, g: U): Boolean = f.addr() == g.addr()

/**
 * Hash a raw pointer.
 *
 * This can be used to hash a `T` reference (which coerces to `* const T` implicitly)
 * by its address rather than the value it points to
 * (which is what the `Hash for & T` implementation does).
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.hash.DefaultHasher
 * import std.ptr
 *
 * val five = 5
 * val fiveRef = five
 * var hasher = DefaultHasher.new()
 * ptr.hash(fiveRef, hasher)
 * val actual = hasher.finish()
 * var hasher1 = DefaultHasher.new()
 * (ConstPtr(fiveRef.toLong())).hash(hasher1)
 * val expected = hasher1.finish()
 * check(actual == expected)
 * ```
 */
public fun <S> hash(hashee: ConstPtr, into: S): Int = hashee.rawAddress.hashCode()

public fun <F : FnPtr<*>> F.eq(other: F): Boolean = this.addr() == other.addr()

public fun <F : FnPtr<*>> F.partialCmp(other: F): Ordering? = this.addr().cmp(other.addr())

public fun <F : FnPtr<*>> F.cmp(other: F): Ordering = this.addr().cmp(other.addr())

public fun <F : FnPtr<*>> F.fmt(f: StringBuilder): Result<Unit, Unit> =
    run {
        f.append("0x")
        f.append(this.addr().toUInt().toString(16))
        Result.Ok<Unit, Unit>(Unit)
    }
