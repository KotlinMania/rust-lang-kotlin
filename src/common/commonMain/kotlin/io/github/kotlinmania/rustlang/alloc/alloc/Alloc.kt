// port-lint: source alloc.rs
package io.github.kotlinmania.rustlang.alloc.alloc

import io.github.kotlinmania.rustlang.core.alloc.*
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmRealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

// Memory allocation APIs

// These are the magic symbols to call the global allocator. rustc generates
// them to call the global allocator if there is a `@globalAllocator` attribute
// (the code expanding that attribute macro generates those functions), or to call
// the default implementations in std (`__rdl_alloc` etc. in `library / std / src / alloc.rs`)
// otherwise.
public fun rustAlloc(size: ULong, align: Alignment): MutPtr = MutPtr(kmAlloc(size.toLong(), align.asUsize().toLong()))

public fun rustDealloc(ptr: NonNull, size: ULong, align: Alignment) = kmDealloc(ptr.rawAddress, size.toLong(), align.asUsize().toLong())

public fun rustRealloc(ptr: NonNull, oldSize: ULong, align: Alignment, newSize: ULong): MutPtr = MutPtr(kmRealloc(ptr.rawAddress, oldSize.toLong(), align.asUsize().toLong(), newSize.toLong()))

public fun rustAllocZeroed(size: ULong, align: Alignment): MutPtr = MutPtr(kmAllocZeroed(size.toLong(), align.asUsize().toLong()))

public fun rustNoAllocShimIsUnstableV2() = Unit

// the compiler needs to know when a Holder uses the global allocator vs a custom one

/**
 * A wrapper for the global allocator.
 *
 * This type implements the [Allocator] trait by forwarding calls
 * to the allocator registered with the `@globalAllocator` attribute
 * if there is one, or the `std` crate’s default.
 *
 * `Global` is not "the global allocator", but a wrapper for it.
 * If the global allocator used `Global`, it would call itself recursively.
 * To avoid this, `Global` does not implement [GlobalAlloc].
 *
 * Similar to [alloc], [dealloc], and the other global allocation functions,
 * when and how calls are forwarded to the allocator registered with
 * `@globalAllocator` is unspecified. See their safety docs for more information.
 *
 * In particular, even if you know which allocator was registered
 * as the global allocator, calling it directly is not the same as calling
 * `Global`. You cannot deallocate memory from one using the other.
 *
 * `Global` must be treated like an opaque allocator that only guarantees the contract
 * described in the docs of [Allocator], as well as the following:
 * * All instances of `Global` are [*equivalent*].
 * * Allocations from `Global` are only invalidated by calls to de-/reallocating functions.
 *   If no such call is made, then the allocation will live for the rest of the program.
 * * The global allocation functions in the [`alloc`](self) module are equivalent to the
 *   methods on `Global`, except that they disallow zero-sized allocations, and implicitly
 *   ignore any returned excess size.
 *   In particular, you may deallocate memory from [`alloc.alloc`](alloc) using
 *   [`Global.deallocate`](Global.deallocate) and vice-versa.
 *
 * Note that the current implementation of `Global` does not take advantage of
 * some features of [Allocator], such as zero-sized allocations (which currently
 * return a dangling pointer) and overallocating.
 * This may change in the future. You must not rely on it for correctness!
 *
 * [*equivalent*]: Allocator#equivalent-allocators
 */
public open class Global : AllocatorClone, StaticAllocator, Allocator, AllocatorNightly {
    public open fun clone(): Global = Global()

    // SAFETY: Same as `Allocator.grow`
    // even without panics, this helps for Miri backtraces
    public open fun growImplRuntime(ptr: NonNull, oldLayout: Layout, newLayout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
        check(newLayout.size() >= oldLayout.size()) { "`new_layout.size()` must be greater than or equal to `old_layout.size()`" }
        return run {
            val matchVal = oldLayout.size()
            when {
                matchVal == 0 -> this.allocImpl(newLayout, zeroed)
                // SAFETY: `newSize` is non-zero as `oldSize` is greater than or equal to `newSize`
                // as required by safety conditions. Other conditions must be upheld by the caller
                oldLayout.align() == newLayout.align() -> {
                    val oldSize = matchVal
                    run {
                        val newSize = newLayout.size()
                        // `realloc` probably checks for `newSize >= oldLayout.size()` or something similar.
                        assertUnchecked(newSize >= oldLayout.size())
                        val rawPtr = reallocNonnull(ptr, oldLayout, newSize)
                        val ptr1 = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        if (zeroed) {
                            kmWriteBytes((MutPtr((rawPtr).rawAddress + ((oldSize).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, (0).toUByte(), (newSize - oldSize).toLong() * (PrimitiveLayouts.U8).sizeBytes)
                        }
                        Result.Ok<NonNull, AllocError>(ptr1.castSlice(newSize))
                    }
                }
                // SAFETY: because `newLayout.size()` must be greater than or equal to `oldSize`,
                // both the old and new memory allocation are valid for reads and writes for `oldSize`
                // bytes. Also, because the old allocation wasn't yet deallocated, it cannot overlap
                // `newPtr`. Thus, the call to `copyNonoverlapping` is safe. The safety contract
                // for `dealloc` must be upheld by the caller.
                else -> {
                    val oldSize1 = matchVal
                    run {
                        val newPtr = when (val tryResult = this.allocImpl(newLayout, zeroed)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asPtr(), oldSize1, typeLayoutT = PrimitiveLayouts.U8)
                        this.deallocate(ptr, oldLayout)
                        Result.Ok<NonNull, AllocError>(newPtr)
                    }
                }
            }
        }
    }

    // SAFETY: Same as `Allocator.grow`
    // even without panics, this helps for Miri backtraces
    public open fun shrinkImplRuntime(ptr: NonNull, oldLayout: Layout, newLayout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
        check(newLayout.size() <= oldLayout.size()) { "`new_layout.size()` must be smaller than or equal to `old_layout.size()`" }
        return run {
            val matchVal = newLayout.size()
            when {
                // SAFETY: conditions must be upheld by the caller
                matchVal == 0 -> run {
                    this.deallocate(ptr, oldLayout)
                    Result.Ok<NonNull, AllocError>(newLayout.danglingPtr().castSlice(0))
                }
                // SAFETY: `newSize` is non-zero. Other conditions must be upheld by the caller
                oldLayout.align() == newLayout.align() -> {
                    val newSize = matchVal
                    run {
                        // `realloc` probably checks for `newSize <= oldLayout.size()` or something similar.
                        assertUnchecked(newSize <= oldLayout.size())
                        val rawPtr = reallocNonnull(ptr, oldLayout, newSize)
                        val ptr1 = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        Result.Ok<NonNull, AllocError>(ptr1.castSlice(newSize))
                    }
                }
                // SAFETY: because `newSize` must be smaller than or equal to `oldLayout.size()`,
                // both the old and new memory allocation are valid for reads and writes for `newSize`
                // bytes. Also, because the old allocation wasn't yet deallocated, it cannot overlap
                // `newPtr`. Thus, the call to `copyNonoverlapping` is safe. The safety contract
                // for `dealloc` must be upheld by the caller.
                else -> {
                    val newSize1 = matchVal
                    run {
                        val newPtr = when (val tryResult = this.allocate(newLayout)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asPtr(), newSize1, typeLayoutT = PrimitiveLayouts.U8)
                        this.deallocate(ptr, oldLayout)
                        Result.Ok<NonNull, AllocError>(newPtr)
                    }
                }
            }
        }
    }

    // SAFETY: Same as `Allocator.allocate`
    // even without panics, this helps for Miri backtraces
    public open fun allocImpl(layout: Layout, zeroed: Boolean): Result<NonNull, AllocError> = constEvalSelect(Pair(layout, zeroed), Global::allocImplConst, Global::allocImplRuntime)

    // SAFETY: Same as `Allocator.deallocate`
    // even without panics, this helps for Miri backtraces
    public open fun deallocateImpl(ptr: NonNull, layout: Layout) {
        constEvalSelect(Pair(ptr, layout), Global::deallocateImplConst, Global::deallocateImplRuntime)
    }

    // SAFETY: Same as `Allocator.grow`
    // even without panics, this helps for Miri backtraces
    public open fun growImpl(ptr: NonNull, oldLayout: Layout, newLayout: Layout, zeroed: Boolean): Result<NonNull, AllocError> = constEvalSelect(listOf(this, ptr, oldLayout, newLayout, zeroed), { it.growShrinkImplConst() }, { it.growImplRuntime() })

    // SAFETY: Same as `Allocator.shrink`
    // even without panics, this helps for Miri backtraces
    public open fun shrinkImpl(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> = constEvalSelect(listOf(this, ptr, oldLayout, newLayout, false), { it.growShrinkImplConst() }, { it.shrinkImplRuntime() })

    // even without panics, this helps for Miri backtraces
    public open fun growShrinkImplConst(ptr: NonNull, oldLayout: Layout, newLayout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
        val newPtr = when (val tryResult = this.allocImpl(newLayout, zeroed)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
        // SAFETY: both pointers are valid and this operations is in bounds.
        run {
            copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asPtr(), minOf(oldLayout.size(), newLayout.size()), typeLayoutT = PrimitiveLayouts.U8)
        }
        // SAFETY: Caller ensures the ptr & layout are correct.
        run {
            this.deallocateImpl(ptr, oldLayout)
        }
        return Result.Ok<NonNull, AllocError>(newPtr)
    }

    // even without panics, this helps for Miri backtraces
    public open fun allocate(layout: Layout): Result<NonNull, AllocError> = this.allocImpl(layout, false)

    // even without panics, this helps for Miri backtraces
    public open fun allocateZeroed(layout: Layout): Result<NonNull, AllocError> = this.allocImpl(layout, true)

    // even without panics, this helps for Miri backtraces
    public open fun deallocate(ptr: NonNull, layout: Layout) {
        // SAFETY: all conditions must be upheld by the caller
        this.deallocateImpl(ptr, layout)
    }

    // even without panics, this helps for Miri backtraces
    public open fun grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: all conditions must be upheld by the caller
        return this.growImpl(ptr, oldLayout, newLayout, false)
    }

    // even without panics, this helps for Miri backtraces
    public open fun growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: all conditions must be upheld by the caller
        return this.growImpl(ptr, oldLayout, newLayout, true)
    }

    // even without panics, this helps for Miri backtraces
    public open fun shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: all conditions must be upheld by the caller
        return this.shrinkImpl(ptr, oldLayout, newLayout)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Global> =
            object : TypeLayout<Global> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Global =
                    Global()
                override fun writeNative(addr: Long, value: Global) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Global =
                    Global()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Global) {
                }
            }

        // even without panics, this helps for Miri backtraces
        public fun allocImplRuntime(layout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
            return when (val matchVal = layout.size()) {
                0 -> Result.Ok<NonNull, AllocError>(layout.danglingPtr().castSlice(0))
                // SAFETY: `layout` is non-zero in size,
                else -> {
                    val size = matchVal
                    run {
                        val rawPtr =
                            if (zeroed) {
                                allocZeroed(layout)
                            } else {
                                alloc(layout)
                            }
                        val ptr = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        Result.Ok<NonNull, AllocError>(ptr.castSlice(size))
                    }
                }
            }
        }

        // even without panics, this helps for Miri backtraces
        public fun deallocateImplRuntime(ptr: NonNull, layout: Layout) {
            if (layout.size() != 0) {
                // SAFETY:
                // * We have checked that `layout` is non-zero in size.
                // * The caller is obligated to provide a layout that "fits", and in this case,
                // "fit" always means a layout that is equal to the original, because our
                // `allocate()`, `grow()`, and `shrink()` implementations never returns a larger
                // allocation than requested.
                // * Other conditions must be upheld by the caller, as per `Allocator.deallocate()`s
                // safety documentation.
                deallocNonnull(ptr, layout)
            }
        }

        // even without panics, this helps for Miri backtraces
        public fun allocImplConst(layout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
            return when (val matchVal = layout.size()) {
                0 -> Result.Ok<NonNull, AllocError>(layout.danglingPtr().castSlice(0))
                // SAFETY: `layout` is non-zero in size,
                else -> {
                    val size = matchVal
                    run {
                        val rawPtr = constAllocate(layout.size(), layout.align())
                        val ptr = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        if (zeroed) {
                            // SAFETY: the pointer returned by `constAllocate` is valid to write to.
                            ptr.writeBytes(0, size)
                        }
                        Result.Ok<NonNull, AllocError>(ptr.castSlice(size))
                    }
                }
            }
        }

        // even without panics, this helps for Miri backtraces
        public fun deallocateImplConst(ptr: NonNull, layout: Layout) {
            if (layout.size() != 0) {
                // SAFETY: We checked for nonzero size; other preconditions must be upheld by caller.
                run {
                    constDeallocate(ptr.asPtr(), layout.size(), layout.align())
                }
            }
        }
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Allocates memory with the global allocator.
 *
 * This function forwards calls to the [GlobalAlloc.alloc] method
 * of the allocator registered with the `@globalAllocator` attribute
 * if there is one, or the `std` crate’s default.
 *
 * Note, however, that invoking this function is *not* equivalent to invoking the underlying
 * [GlobalAlloc.alloc] method of the registered allocator directly. Users of this function
 * cannot assume anything about what the allocator does, other than the documented requirements.
 * This means:
 *
 * - This function may non-deterministically entirely skip the underlying allocator, e.g. if the
 *   compiler can show that this allocation can be replaced by a stack variable. The compiler may
 *   also merge multiple allocation operations into one, as long as it can also adjust all
 *   corresponding deallocation operations accordingly.
 * - An allocation created by invoking this function has exactly the size and minimum alignment
 *   defined by `layout`, even if the underlying allocator makes stronger promises.
 * - The allocation can only be freed by invoking [dealloc] or [realloc]. In particular,
 *   passing a pointer to such an allocation directly to the underlying method on [GlobalAlloc] is
 *   not permitted. Until one of those functions is called, it is undefined behavior to access the
 *   memory that backs this allocation with any pointer not derived from the return value of this
 *   function (e.g., with internal pointers the allocator might keep around).
 * - This function de-initializes the contents of the allocation before handing it to the user. So even
 *   if you control the underlying allocator and know that it explicitly initialized this memory,
 *   you cannot rely on it being initialized.
 *
 * Users of this function have to consider that in the future, allocators may be allowed to unwind.
 *
 * This function is expected to be deprecated in favor of the `allocate` method
 * of the [Global] type when it and the [Allocator] trait become stable.
 *
 * # Safety
 *
 * See [GlobalAlloc.alloc].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import std.alloc.Layout
 * import std.alloc.alloc
 * import std.alloc.dealloc
 * import std.alloc.handleAllocError
 *
 * run {
 *     val layout = Layout.new<UShort>(typeLayoutT = PrimitiveLayouts.U16)
 *     val ptr = alloc(layout)
 *     if (ptr.isNull()) {
 *         handleAllocError(layout)
 *     }
 *     (PrimitiveLayouts.U16).writeNative(((mutPtrFromRustCast(ptr))).rawAddress, 42)
 *     check((PrimitiveLayouts.U16).readNative(((mutPtrFromRustCast(ptr))).rawAddress) == 42u.toUShort())
 *     dealloc(ptr, layout)
 * }
 * ```
 */
public fun alloc(layout: Layout): MutPtr {
    // SAFETY: Upheld by caller.
    return run {
        // Make sure we don't accidentally allow omitting the allocator shim in
        // stable code until it is actually stabilized.
        rustNoAllocShimIsUnstableV2()
        MutPtr(kmAlloc((layout.size()).toLong(), (layout.alignment()).asUsize().toLong()))
    }
}

// even without panics, this helps for Miri backtraces

/**
 * Deallocates memory with the global allocator.
 *
 * This function forwards calls to the [GlobalAlloc.dealloc] method
 * of the allocator registered with the `@globalAllocator` attribute
 * if there is one, or the `std` crate’s default.
 *
 * Note, however, that invoking this function is *not* equivalent to invoking the underlying
 * [GlobalAlloc.dealloc] method of the registered allocator directly. Users of this function
 * cannot assume anything about what the allocator does, other than the documented requirements.
 * This means:
 *
 * - This function may non-deterministically entirely skip the underlying allocator, e.g. if the
 *   compiler can show that this allocation can be replaced by a stack variable. The compiler may
 *   also merge multiple allocation operations into one, as long as it can also adjust all
 *   corresponding deallocation operations accordingly.
 * - The pointer passed to this function must have been obtained by invoking [alloc],
 *   [allocZeroed], or [realloc]. In particular, passing a pointer returned by the underlying
 *   methods on [GlobalAlloc] is not permitted.
 * - This function de-initializes the contents of the allocation before handing it to the allocator.
 *   So even if you know that the program previously initialized that memory, the allocator cannot
 *   rely on it being initialized.
 *
 * Users of this function have to consider that in the future, allocators may be allowed to unwind.
 *
 * This function is expected to be deprecated in favor of the `deallocate` method
 * of the [Global] type when it and the [Allocator] trait become stable.
 *
 * # Safety
 *
 * See [GlobalAlloc.dealloc].
 */
public fun dealloc(ptr: MutPtr, layout: Layout) {
    // SAFETY: Upheld by caller.
    deallocNonnull(NonNull.newUnchecked(ptr), layout)
}

// even without panics, this helps for Miri backtraces

/**
 * Same as [dealloc] but when you already have a non-null pointer
 */
public fun deallocNonnull(ptr: NonNull, layout: Layout) {
    // SAFETY: Upheld by caller.
    kmDealloc((ptr).rawAddress, (layout.size()).toLong(), (layout.alignment()).asUsize().toLong())
}

// even without panics, this helps for Miri backtraces

/**
 * Reallocates memory with the global allocator.
 *
 * This function forwards calls to the [GlobalAlloc.realloc] method
 * of the allocator registered with the `@globalAllocator` attribute
 * if there is one, or the `std` crate’s default.
 *
 * Note, however, that invoking this function is *not* equivalent to invoking the underlying
 * [GlobalAlloc.realloc] method of the registered allocator directly. Users of this function
 * cannot assume anything about what the allocator does, other than the documented requirements.
 * This means:
 *
 * - This function may non-deterministically entirely skip the underlying allocator, e.g. if the
 *   compiler can show that this allocation can be replaced by a stack variable. The compiler may
 *   also merge multiple allocation operations into one, as long as it can also adjust all
 *   corresponding deallocation operations accordingly.
 * - The pointer passed to this function must have been obtained by invoking [alloc],
 *   [allocZeroed], or [realloc]. In particular, passing a pointer returned by the underlying
 *   methods on [GlobalAlloc] is not permitted.
 * - An allocation created by invoking this function has exactly the size and minimum alignment
 *   defined by `layout`, even if the underlying allocator makes stronger promises.
 * - The allocation can only be freed by invoking [dealloc] or [realloc]. In particular,
 *   passing a pointer to such an allocation directly to the underlying method on [GlobalAlloc] is
 *   not permitted. Until one of those functions is called, it is undefined behavior to access the
 *   memory that backs this allocation with any pointer not derived from the return value of this
 *   function (e.g., with internal pointers the allocator might keep around).
 * - If this grows the allocation, the contents of the grown part of the new allocation allocation
 *   are de-initialized by this function before returning.
 * - If this shrinks the allocation, the contents of the removed part of the old allocation are
 *   de-initialized by this function before invoking the underlying allocator.
 *
 * Users of this function have to consider that in the future, allocators may be allowed to unwind.
 *
 * This function is expected to be deprecated in favor of the `grow` and `shrink` methods
 * of the [Global] type when it and the [Allocator] trait become stable.
 *
 * # Safety
 *
 * See [GlobalAlloc.realloc].
 */
public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: Upheld by caller.
    return reallocNonnull(NonNull.newUnchecked(ptr), layout, newSize)
}

// even without panics, this helps for Miri backtraces

/**
 * Same as [realloc] but when you already have a non-null pointer
 */
public fun reallocNonnull(ptr: NonNull, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: Upheld by caller.
    return MutPtr(kmRealloc((ptr).rawAddress, (layout.size()).toLong(), (layout.alignment()).asUsize().toLong(), (newSize).toLong()))
}

// even without panics, this helps for Miri backtraces

/**
 * Allocates zero-initialized memory with the global allocator.
 *
 * This function forwards calls to the [GlobalAlloc.allocZeroed] method
 * of the allocator registered with the `@globalAllocator` attribute
 * if there is one, or the `std` crate’s default.
 *
 * Note, however, that invoking this function is *not* equivalent to invoking the underlying
 * [GlobalAlloc.allocZeroed] method of the registered allocator directly. Users of this
 * function cannot assume anything about what the allocator does, other than the documented
 * requirements. This means:
 *
 * - This function may non-deterministically entirely skip the underlying allocator, e.g. if the
 *   compiler can show that this allocation can be replaced by a stack variable. The compiler may
 *   also merge multiple allocation operations into one, as long as it can also adjust all
 *   corresponding deallocation operations accordingly.
 * - The allocation can only be freed by invoking [dealloc] or [realloc]. In particular,
 *   passing a pointer to such an allocation directly to the underlying method on [GlobalAlloc] is
 *   not permitted. Until one of those functions is called, it is undefined behavior to access the
 *   memory that backs this allocation with any pointer not derived from the return value of this
 *   function (e.g., with internal pointers the allocator might keep around).
 * - An allocation created by invoking this function has exactly the size and minimum alignment
 *   defined by `layout`, even if the underlying allocator makes stronger promises.
 *
 * Users of this function have to consider that in the future, allocators may be allowed to unwind.
 *
 * This function is expected to be deprecated in favor of the `allocateZeroed` method
 * of the [Global] type when it and the [Allocator] trait become stable.
 *
 * # Safety
 *
 * See [GlobalAlloc.allocZeroed].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import std.alloc.Layout
 * import std.alloc.allocZeroed
 * import std.alloc.dealloc
 * import std.alloc.handleAllocError
 *
 * run {
 *     val layout = Layout.new<UShort>(typeLayoutT = PrimitiveLayouts.U16)
 *     val ptr = allocZeroed(layout)
 *     if (ptr.isNull()) {
 *         handleAllocError(layout)
 *     }
 *     check((PrimitiveLayouts.U16).readNative(((mutPtrFromRustCast(ptr))).rawAddress) == 0u.toUShort())
 *     dealloc(ptr, layout)
 * }
 * ```
 */
public fun allocZeroed(layout: Layout): MutPtr {
    // SAFETY: Upheld by caller.
    return run {
        // Make sure we don't accidentally allow omitting the allocator shim in
        // stable code until it is actually stabilized.
        rustNoAllocShimIsUnstableV2()
        MutPtr(kmAllocZeroed((layout.size()).toLong(), (layout.alignment()).asUsize().toLong()))
    }
}

// # Allocation error handler
// This is the magic symbol to call the global alloc error handler. rustc generates
// it to call `__rg_oom` if there is a `@allocErrorHandler`, or to call the
// default implementations below (`__rdl_alloc_error_handler`) otherwise.
public expect fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing

/**
 * Signals a memory allocation error.
 *
 * Callers of memory allocation APIs wishing to cease execution
 * in response to an allocation error are encouraged to call this function,
 * rather than directly invoking [panic] or similar.
 *
 * This function is guaranteed to diverge (not return normally with a value), but depending on
 * global configuration, it may either panic (resulting in unwinding or aborting as per
 * configuration for all panics), or abort the process (with no unwinding).
 *
 * The default behavior is:
 *
 *  * If the binary links against `std` (typically the case), then
 *   print a message to standard error and abort the process.
 *   This behavior can be replaced with [setAllocErrorHook] and [takeAllocErrorHook].
 *   Future versions of Rust may panic by default instead.
 *
 * * If the binary does not link against `std` (all of its crates are marked
 *   [`#![noStd]`][noStd]), then call [panic] with a message.
 *   [The panic handler] applies as to any panic.
 *
 * [setAllocErrorHook]: ../../std/alloc/fn.setAllocErrorHook.html
 * [takeAllocErrorHook]: ../../std/alloc/fn.takeAllocErrorHook.html
 * [The panic handler]: https://doc.rust-lang.org/reference/runtime.html#the-panicHandler-attribute
 * [noStd]: https://doc.rust-lang.org/reference/names/preludes.html#the-noStd-attribute
 */
public fun handleAllocError(layout: Layout): Nothing {
    fun ctError(arg0: Layout): Nothing {
        error("allocation failed")
    }
    fun rtError(layout1: Layout): Nothing {
        // SAFETY: Safe to call; we control this function.
        return run {
            rustAllocErrorHandler(layout1.size(), layout1.align())
        }
    }
    return ctError(layout)
}

// called via generated `__rust_alloc_error_handler` if there is no
// `@allocErrorHandler`.
public fun rdlAllocErrorHandler(size: Int, align: Int): Nothing = panicNounwindFmt("memory allocation of $size bytes failed", false)
