// port-lint: source alloc/global.rs
package io.github.kotlinmania.rustlang.core.alloc.global

import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.GlobalAllocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * A memory allocator that can be registered as the standard library’s default
 * through the `@globalAllocator` attribute.
 *
 * Some of the methods require that a memory block be *currently
 * allocated* via an allocator. This means that:
 *
 * * the starting address for that memory block was previously
 *   returned by a previous call to an allocation method
 *   such as `alloc`, and
 *
 * * the memory block has not been subsequently deallocated, where
 *   blocks are deallocated either by being passed to a deallocation
 *   method such as `dealloc` or by being
 *   passed to a reallocation method that returns a non-null pointer.
 *
 * # Example
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.alloc.GlobalAlloc
 * import std.alloc.Layout
 * import std.cell.UnsafeCell
 * import std.ptr.nullMut
 * import std.sync.atomic.AtomicUsize
 * import std.sync.atomic.ordering.Relaxed
 *
 * public fun doctestEntry() {
 *     val ARENA_SIZE: Int = 128 * 1024
 *     val MAX_SUPPORTED_ALIGN: Int = 4096
 *     // 4096 == MAX_SUPPORTED_ALIGN
 *     @repr(C, align(4096))
 *     open class SimpleAllocator(public var arena: UnsafeCell<UByteArray>, public var remaining: AtomicUsize) : GlobalAlloc {
 *         public open fun alloc(layout: Layout): MutPtr {
 *             val size = layout.size()
 *             val align = layout.align()
 *             // `Layout` contract forbids making a `Layout` with align=0, or align not power of 2.
 *             // So we can safely use a mask to ensure alignment without worrying about UB.
 *             val alignMaskToRoundDown = (align - 1).inv()
 *             if (align > MAX_SUPPORTED_ALIGN) {
 *                 return MutPtr(0L)
 *             }
 *             var allocated = 0
 *             if (this.remaining.tryUpdate(Relaxed, Relaxed, closure@{ remaining ->
 *                 if (size > remaining) {
 *                     return@closure null
 *                 }
 *                 remaining -= size
 *                 remaining = remaining and alignMaskToRoundDown
 *                 allocated = remaining
 *                 remaining
 *             }).isErr()) {
 *                 return MutPtr(0L)
 *             }
 *             return this.arena.get().cast().add(allocated)
 *         }
 *
 *         public open fun dealloc(ptr: MutPtr, layout: Layout) {}
 *     }
 *     @globalAllocator
 *     var ALLOCATOR: SimpleAllocator = SimpleAllocator(arena = UnsafeCell.new(List((ARENA_SIZE).toInt()) { 0x55 }), remaining = AtomicUsize.new(ARENA_SIZE))
 *     fun main() {
 *         val s = "allocating a string!"
 *         val currently = ALLOCATOR.remaining.load(Relaxed)
 *         println("allocated so far: ${ARENA_SIZE - currently}")
 *     }
 * }
 * ```
 *
 * # The `@globalAllocator` attribute
 *
 * As the example above demonstrates, the `@globalAllocator` attribute can be used to register a
 * concrete `static` of a type that implements this trait to become *the* global allocator
 * for the current program. That global allocator can be invoked via the functions [alloc],
 * [allocZeroed], [dealloc], [realloc]). Note, however, that invoking those functions is
 * *not* equivalent to directly invoking the underlying methods on the declared global allocator!
 * Users of the global allocator cannot assume anything about what the allocator does (even if they know which allocator is being used),
 * and implementors of the allocator cannot assume anything about what the program does (even if they know how the allocator is being used).
 * Both can only assume the documented requirements for the respective other party of this contract.
 * This means:
 *
 * - Allocation functions may non-deterministically entirely skip the underlying allocator, e.g. if the
 *   compiler can show that this allocation can be replaced by a stack variable. The compiler may
 *   also merge multiple allocation operations into one, as long as it can also adjust all
 *   corresponding deallocation operations accordingly.
 * - An allocation created by invoking [alloc], [allocZeroed], or [realloc] has exactly the
 *   size and minimum alignment defined by `layout`, even if the underlying allocator makes
 *   stronger promises.
 * - An allocation created by invoking [alloc], [allocZeroed], or [realloc] can only be
 *   freed by invoking [dealloc] or [realloc]. In particular, passing a pointer to such an
 *   allocation directly to the underlying method on [GlobalAlloc] is not permitted. Until one of
 *   those functions is called, it is undefined behavior to access the memory that backs this
 *   allocation with any pointer not derived from the return value of this function (e.g., with
 *   internal pointers the allocator might keep around).
 * - The pointer passed to [dealloc] or [realloc] must have been obtained by invoking [alloc],
 *   [allocZeroed], or [realloc]. In particular, passing a pointer returned by the underlying
 *   methods on [GlobalAlloc] is not permitted.
 * - [alloc] de-initializes the contents of the allocation before handing it to the user. So even
 *   if you control the underlying allocator and know that it explicitly initialized this memory,
 *   you cannot rely on it being initialized. For a [realloc] that grows an allocation, this
 *   applies to the newly allocated part.
 * - [dealloc] de-initializes the contents of the allocation before handing it to the allocator.
 *   So even if you know that the program previously initialized that memory, the allocator cannot
 *   rely on it being initialized. For a [realloc] that shrinks an allocation, this applies to
 *   the part being removed.
 *
 * [alloc]: ../../std/alloc/fn.alloc.html
 * [allocZeroed]: ../../std/alloc/fn.allocZeroed.html
 * [dealloc]: ../../std/alloc/fn.dealloc.html
 * [realloc]: ../../std/alloc/fn.realloc.html
 *
 * The first point means that you cannot rely on global allocations actually happening, even if
 * there are explicit global allocations in the source. The optimizer may detect unused global
 * allocations that it can either eliminate entirely or move to the stack and thus never invoke the
 * global allocator. The optimizer may further assume that allocation is infallible, so code that
 * used to fail due to allocator failures may now suddenly work because the optimizer worked around
 * the need for an allocation. More concretely, the following code example is unsound, irrespective
 * of whether your custom allocator allows counting how many allocations have happened.
 *
 * ```rust,ignore (unsound and has placeholders)
 * drop(Box.new(42));
 * let numberOfHeapAllocs = / * call private allocator API * /;
 * unchecked { std.hint.assertUnchecked(numberOfHeapAllocs > 0); }
 * ```
 *
 * Note that the optimizations mentioned above are not the only
 * optimization that can be applied. You may generally not rely on global allocations
 * happening if they can be removed without changing program behavior.
 * Whether allocations happen or not is not part of the program behavior, even if it
 * could be detected via an allocator that tracks allocations by printing or otherwise
 * having side effects.
 *
 * # Safety
 *
 * The `GlobalAlloc` trait is an `unchecked` trait for a number of reasons, and
 * implementors must ensure that they adhere to these contracts:
 *
 * * It is undefined behavior for the allocator to read, write, or deallocate any memory that
 *   is *currently allocated*. This memory is owned by the user, the allocator must not touch it.
 *
 * * It's undefined behavior if global allocators unwind. This restriction may
 *   be lifted in the future, but currently a panic from any of these
 *   functions may lead to memory unsafety.
 *
 * * Callers of this trait are allowed to rely on the contracts defined on each method, and
 *   implementors must ensure such contracts remain true.
 *
 * # Re-entrance
 *
 * When implementing a global allocator, one has to be careful not to create an infinitely recursive
 * implementation by accident, as many constructs in the Rust standard library may allocate in
 * their implementation. For example, on some platforms, [std.sync.Mutex] may allocate, so using
 * it is highly problematic in a global allocator.
 *
 * For this reason, one should generally stick to library features available through
 * [core], and avoid using [std] in a global allocator. A few features from [std] are
 * guaranteed to not use `@globalAllocator` to allocate:
 *
 *  - [std.threadLocal],
 *  - [std.thread.current],
 *  - [std.thread.park] and [std.thread.Thread]s [unpark] method and
 *    [Clone] implementation.
 *
 * [std]: ../../std/index.html
 * [std.sync.Mutex]: ../../std/sync/struct.Mutex.html
 * [std.threadLocal]: ../../std/macro.threadLocal.html
 * [std.thread.current]: ../../std/thread/fn.current.html
 * [std.thread.park]: ../../std/thread/fn.park.html
 * [std.thread.Thread]: ../../std/thread/struct.Thread.html
 * [unpark]: ../../std/thread/struct.Thread.html#method.unpark
 */
public interface GlobalAlloc {
    /**
     * Allocates memory as described by the given `layout`.
     *
     * Returns a pointer to newly-allocated memory,
     * or null to indicate allocation failure.
     *
     * # Safety
     *
     * `layout` must have non-zero size. Attempting to allocate for a zero-sized `layout` will
     * result in undefined behavior.
     *
     * (Extension subtraits might provide more specific bounds on
     * behavior, e.g., guarantee a sentinel address or a null pointer
     * in response to a zero-size allocation request.)
     *
     * The allocated block of memory may or may not be initialized.
     *
     * # Errors
     *
     * Returning a null pointer indicates that either memory is exhausted
     * or `layout` does not meet this allocator's size or alignment constraints.
     *
     * Implementations are encouraged to return null on memory
     * exhaustion rather than aborting, but this is not
     * a strict requirement. (Specifically: it is *legal* to
     * implement this trait atop an underlying native allocation
     * library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to an
     * allocation error are encouraged to call the [handleAllocError] function,
     * rather than directly invoking `panic` or similar (but note that both may unwind).
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun alloc(layout: Layout): MutPtr

    /**
     * Deallocates the block of memory at the given `ptr` pointer with the given `layout`.
     *
     * # Safety
     *
     * The caller must ensure:
     *
     * * `ptr` is a block of memory currently allocated via this allocator and,
     *
     * * `layout` is the same layout that was used to allocate that block of
     *   memory.
     *
     * Otherwise the behavior is undefined.
     */
    public fun dealloc(ptr: MutPtr, layout: Layout)

    /**
     * Behaves like `alloc`, but also ensures that the contents
     * are set to zero before being returned.
     *
     * # Safety
     *
     * The caller has to ensure that `layout` has non-zero size. Like `alloc`
     * zero sized `layout` will result in undefined behavior.
     * However the allocated block of memory is guaranteed to be initialized.
     *
     * # Errors
     *
     * Returning a null pointer indicates that either memory is exhausted
     * or `layout` does not meet allocator's size or alignment constraints,
     * just as in `alloc`.
     *
     * Clients wishing to abort computation in response to an
     * allocation error are encouraged to call the [handleAllocError] function,
     * rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun allocZeroed(layout: Layout): MutPtr {
        val size = layout.size()
        // SAFETY: the safety contract for `alloc` must be upheld by the caller.
        val ptr = this.alloc(layout)
        if (!ptr.isNull()) {
            // SAFETY: as allocation succeeded, the region from `ptr`
            // of size `size` is guaranteed to be valid for writes.
            writeBytes(ptr, 0u, size)
        }
        return ptr
    }

    /**
     * Shrinks or grows a block of memory to the given `newSize` in bytes.
     * The block is described by the given `ptr` pointer and `layout`.
     *
     * If this returns a non-null pointer, then ownership of the memory block
     * referenced by `ptr` has been transferred to this allocator.
     * Any access to the old `ptr` is Undefined Behavior, even if the
     * allocation remained in-place. The newly returned pointer is the only valid pointer
     * for accessing this memory now.
     *
     * The new memory block is allocated with `layout`,
     * but with the `size` updated to `newSize` in bytes.
     * This new layout must be used when deallocating the new memory block with `dealloc`.
     * The range `0 .. min(layout.size(), newSize)` of the new memory block is
     * guaranteed to have the same values as the original block.
     *
     * If this method returns null, then ownership of the memory
     * block has not been transferred to this allocator, and the
     * contents of the memory block are unaltered.
     *
     * # Safety
     *
     * The caller must ensure that:
     *
     * * `ptr` is allocated via this allocator,
     *
     * * `layout` is the same layout that was used
     *   to allocate that block of memory,
     *
     * * `newSize` is greater than zero.
     *
     * * `newSize`, when rounded up to the nearest multiple of `layout.align()`,
     *   does not overflow `Long` (i.e., the rounded value must be less than or
     *   equal to `Long.MAX`).
     *
     * If these are not followed, the behavior is undefined.
     *
     * (Extension subtraits might provide more specific bounds on
     * behavior, e.g., guarantee a sentinel address or a null pointer
     * in response to a zero-size allocation request.)
     *
     * # Errors
     *
     * Returns null if the new layout does not meet the size
     * and alignment constraints of the allocator, or if reallocation
     * otherwise fails.
     *
     * Implementations are encouraged to return null on memory
     * exhaustion rather than panicking or aborting, but this is not
     * a strict requirement. (Specifically: it is *legal* to
     * implement this trait atop an underlying native allocation
     * library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to a
     * reallocation error are encouraged to call the [handleAllocError] function,
     * rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
        val alignment = layout.alignment()
        // SAFETY: the caller must ensure that the `newSize` does not overflow
        // when rounded up to the next multiple of `alignment`.
        val newLayout = Layout.fromSizeAlignmentUnchecked(newSize, alignment)
        // SAFETY: the caller must ensure that `newLayout` is greater than zero.
        val newPtr = this.alloc(newLayout)
        if (!newPtr.isNull()) {
            // SAFETY: the previously allocated block cannot overlap the newly allocated block.
            // The safety contract for `dealloc` must be upheld by the caller.
            run {
                copyNonoverlapping(ptr, newPtr, minOf(layout.size(), newSize), typeLayoutT = PrimitiveLayouts.U8)
                this.dealloc(ptr, layout)
            }
        }
        return newPtr
    }
}

/**
 * Allows all [GlobalAllocator]s to be used with the legacy [GlobalAlloc] interface.
 */
public fun <A> A.alloc(layout: Layout): MutPtr where A : GlobalAllocator {
    // SAFETY: guaranteed by the caller.
    // This might lead to the removal of zero-size checks inside the
    // `Allocator` implementation.
    assertUnchecked(layout.size() != 0)
    return run {
        val matchVal = this.allocate(layout)
        when {
            matchVal is Result.Ok<*> -> {
                val ptr = (matchVal as Result.Ok<*>).value
                ptr.cast().asPtr()
            }
            else -> {
                val AllocError = (matchVal as Result.Err<*>).error
                MutPtr(0L)
            }
        }
    }
}

public fun <A> A.dealloc(ptr: MutPtr, layout: Layout) where A : GlobalAllocator {
    // SAFETY: guaranteed by the caller.
    assertUnchecked(layout.size() != 0)
    // SAFETY: only non-null pointers can be currently allocated.
    val ptr1 = NonNull.newUnchecked(ptr)
    // SAFETY: guaranteed by caller.
    this.deallocate(ptr1, layout)
}

public fun <A> A.allocZeroed(layout: Layout): MutPtr where A : GlobalAllocator {
    // SAFETY: guaranteed by the caller.
    assertUnchecked(layout.size() != 0)
    return run {
        val matchVal = this.allocateZeroed(layout)
        when {
            matchVal is Result.Ok<*> -> {
                val ptr = (matchVal as Result.Ok<*>).value
                ptr.cast().asPtr()
            }
            else -> {
                val AllocError = (matchVal as Result.Err<*>).error
                MutPtr(0L)
            }
        }
    }
}

public fun <A> A.realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr where A : GlobalAllocator {
    // SAFETY: guaranteed by the caller.
    assertUnchecked(layout.size() != 0)
    // SAFETY: guaranteed by the caller.
    assertUnchecked(newSize != 0)
    // SAFETY: only non-null pointers can be currently allocated.
    val ptr1 = NonNull.newUnchecked(ptr)
    val alignment = layout.alignment()
    // SAFETY: the caller must ensure that the `newSize` does not overflow
    // when rounded up to the next multiple of `alignment`.
    val newLayout = Layout.fromSizeAlignmentUnchecked(newSize, alignment)
    // SAFETY:
    // Two preconditions are guaranteed by the caller:
    // * `ptr` is currently allocated with this allocator.
    // * `layout` fits the block of memory.
    // The size precondition is upheld by selecting between `grow` and `shrink`
    // based on the size.
    val ptr2 =
        if (newSize >= layout.size()) {
            this.grow(ptr1, layout, newLayout)
        } else {
            this.shrink(ptr1, layout, newLayout)
        }
    return when {
        ptr2 is Result.Ok<*> -> {
            val ptr3 = (ptr2 as Result.Ok<*>).value
            ptr3.cast().asPtr()
        }
        else -> {
            val AllocError = (ptr2 as Result.Err<*>).error
            MutPtr(0L)
        }
    }
}
