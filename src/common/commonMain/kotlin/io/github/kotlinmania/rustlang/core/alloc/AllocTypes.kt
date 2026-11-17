package io.github.kotlinmania.rustlang.core.alloc

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// NOTE: the above bound on allocating methods not unwinding, alongside the similar
// bound on `AllocatorClone`, are currently load-bearing in std! see the below issues
// and make sure they cannot be triggered before relaxing this:
// https://rust.tf/156490
// https://rust.tf/159982

/**
 * An implementation of `Allocator` can allocate, grow, shrink, and deallocate arbitrary blocks of
 * data described via [`Layout`][].
 *
 * `Allocator` is mostly designed to be implemented on ZSTs, references, or smart pointers,
 * but can also be implemented directly on the underlying memory-owning type so long as it
 * upholds the necessary guarantees. In general, an allocator of the type `MyAlloc([UByte; N])`
 * cannot be soundly created without being pinned or otherwise immovable in order to be
 * correct.
 *
 * In contrast to [`GlobalAlloc`][], `Allocator` allows zero-sized allocations. If an underlying
 * allocator does not support this (like jemalloc) or responds by returning a null pointer
 * (such as `libc.malloc`), this must be caught by the implementation.
 *
 * # Examples
 *
 * Sample that keeps track of the number of existing allocations:
 *
 * ```kotlin
 * import ptr
 * import ptr.NonNull
 * import std.alloc.Layout
 * import std.alloc.System
 * import std.sync.atomic.AtomicUsize
 * import std.sync.atomic.Ordering
 *
 * var LIVE_ALLOCATIONS: AtomicUsize = AtomicUsize.new(0)
 * open class CountingAllocator : Allocator {
 *     public override fun allocate(layout: Layout): Result<NonNull> {
 *         val ptr = System.allocate(layout).getOrElse { return Result.failure(it) }
 *         LIVE_ALLOCATIONS.fetchAdd(1, Ordering.Relaxed)
 *         return Result.success(ptr)
 *     }
 *
 *     public override fun deallocate(ptr: NonNull, layout: Layout) {
 *         // SAFETY: the caller guarantees `ptr` was allocated by this allocator with `layout`,
 *         // and every block from this allocator was allocated by `System` with the same layout.
 *         System.deallocate(ptr, layout)
 *         LIVE_ALLOCATIONS.fetchSub(1, Ordering.Relaxed)
 *     }
 * }
 * // The Holder allocates through our allocator.
 * val newBox = Holder.newIn(10, CountingAllocator())
 * check(LIVE_ALLOCATIONS.load(Ordering.Relaxed) == 1)
 * (newBox).let { }
 * check(LIVE_ALLOCATIONS.load(Ordering.Relaxed) == 0)
 * ```
 *
 * ## Implementation Requirements
 *
 * In order to be usable in a flexible manner while still being sound, implementors of the trait
 * must uphold very detailed semantics as explained below; the following terms are thus provided
 * as vocabulary for allocator safety and implementation requirements:
 *
 * ### Equivalent allocators
 *
 * Multiple allocator values can sometimes be interchangeable with each other.
 * When this is the case, we refer to those allocators as being *equivalent* to
 * each other.
 *
 * Users of allocators may assume the following are true of equivalent allocators,
 * and implementors must ensure these rules are upheld:
 * * An allocator is equivalent to itself. (Equivalence is reflexive.)
 * * If an allocator is equivalent to a second allocator, then
 *   the second allocator is also equivalent to the first. (Equivalence is symmetric.)
 * * If an allocator is equivalent to a second allocator, and
 *   the second allocator is equivalent to a third allocator, then
 *   the first allocator is also equivalent to the third allocator.
 *   (Equivalence is transitive.)
 * * Moving, subtyping, unsize-coercing, or trait-upcasting an allocator does not change
 *   what the allocator is equivalent to.
 * * Copying or cloning an allocator creates an equivalent one, should the
 *   [AllocatorClone] trait be implemented.
 *
 * Additionally, implementors of `Allocator` may specify additional equivalences
 * between allocators. It is the responsibility of such implementors to make sure
 * that equivalent allocators have "compatible" `Allocator` implementations.
 * In particular, the standard library specifies the following equivalences:
 * * A reference to an allocator (either  or ) is equivalent to
 *   the allocator being referenced.
 * * A `Holder`, `Rc`, or `Arc` containing an allocator is equivalent to
 *   the allocator inside.
 * * All `Global` allocator instances are equivalent with each other.
 * * All `System` allocator instances are equivalent with each other.
 *
 * ### Currently allocated memory
 *
 * Some of the methods require that a memory block is *currently allocated* by some specific allocator.
 * This means that:
 * * the starting address for that memory block was previously returned by
 *   the [allocate], [allocateZeroed], [grow], [growZeroed], or [shrink] methods,
 *   called on an allocator that's equivalent to this specific allocator; and
 * * the memory block has not subsequently been [*invalidated*].
 *
 * ### Invalidating memory blocks
 *
 * A memory block that is currently allocated becomes *invalidated* when one
 * of the following happens:
 * * The memory block is deallocated. This occurs when the memory block
 *   is passed as an argument to a [deallocate] call, or when it is passed
 *   as an argument to a [grow], [growZeroed] or [shrink] call that returns `Ok`.
 * * For all (equivalent) allocators that this memory block is currently allocated by, at
 *   least one of the following has occurred:
 *   * The allocator's destructor runs.
 *   * The allocator is mutated through a public or otherwise untrusted API taking  access.
 *   * One of the borrow-checker scopes in the allocator's type expires.
 *
 * Note that these conditions imply that a collection may ensure that
 * any specific currently allocated memory block won't be invalidated by:
 * * not deallocating that memory block,
 * * owning an allocator that memory block is allocated with, and
 * * not publicly exposing  access to that allocator.
 *
 * Also note that safe public API of an allocator with  access is not
 * allowed to invalidate its memory blocks. Furthermore, unchecked public API
 * of an allocator with  access must document that they invalidate
 * memory blocks (e.g., by calling `deallocate`) if they do. Therefore,
 * a collection may safely expose  access to its allocator.
 *
 * Also note that, even in cases where there are other "alive" allocators known
 * to be equivalent to a given collection's allocator, most collections still should
 * not publicly expose  access to their allocators. The fact that there are
 * other "alive" allocators would prevent this  access from invalidating
 * the collection's memory block, but public  access is still likely to
 * be unsound, since a user could replace the collection's allocator with
 * a non-equivalent allocator, causing the collection to deallocate its memory
 * with the wrong allocator.
 *
 * [allocate]: Allocator.allocate
 * [allocateZeroed]: Allocator.allocateZeroed
 * [grow]: Allocator.grow
 * [growZeroed]: Allocator.growZeroed
 * [shrink]: Allocator.shrink
 * [deallocate]: Allocator.deallocate
 *
 * ### Memory fitting
 *
 * Some of the methods require that a `layout` *fits* a memory block or vice versa. This means
 * that the following conditions must hold:
 *  * the memory block must be *currently allocated* by the allocator,
 *  * [layout.align] must be the same as the alignment of the layout used to allocate the block, and
 *  * [layout.size] must fall in the range `min ..= max`, where:
 *    - `min` is the size of the layout used to allocate the block, and
 *    - `max` is the actual size returned from [allocate], [allocateZeroed],
 *      [grow], [growZeroed], or [shrink].
 *
 * [layout.align]: Layout.align
 * [layout.size]: Layout.size
 *
 * # Safety
 *
 * Implementors of `Allocator` must ensure that a memory block that
 * is [*currently allocated*] by the allocator points to valid memory
 * until that memory block is [*invalidated*]. The implementor must also
 * not violate this invariant of `Allocator` via allocator equivalences
 * that are in the implementor's control.
 *
 * Additionally, any memory block returned by the allocator must
 * satisfy the allocation invariants described in `core.ptr`.
 * In particular, if a block has base address `p` and size `n`,
 * then `p as Int + n <= Int.MAX` must hold. These blocks must also
 * be wholly disjoint.
 *
 * This ensures that pointer arithmetic within the allocation
 * (for example, `ptr.add(len)`) cannot overflow the address space, and
 * that it is possible to perform nonoverlapping copies between allocations.
 *
 * None of the allocating or deallocating methods may unwind. This restriction
 * may be lifted in the future by ensuring unwinding out of an allocating function always
 * aborts. If an implementor of `Allocator` also has drop glue or directly implements `Drop`,
 * dropping the allocator must not result in an unwind.
 *
 * It is undefined behavior for the allocator to read, write, or deallocate any memory that
 * is currently allocated. This memory is owned by the user; the allocator must not touch it.
 *
 * Lastly, the methods on this trait must be *correct*; in particular, the layout requested
 * must be respected, calls must zero out memory if the documentation so requires,
 * returning an `AllocError` from a reallocating method must indeed ensure that
 * the old pointer was not invalidated, and de/reallocating calls must accept layouts
 * in the ranges defined by their documentation.
 *
 * [*currently allocated*]: #currently-allocated-memory
 * [*invalidated*]: #invalidating-memory-blocks
 */
public interface Allocator {
    /**
     * Attempts to allocate a block of memory.
     *
     * On success, returns a [`NonNull`][NonNull] meeting the size and alignment
     * guarantees of `layout`. The returned block may have a larger size than specified
     * by `layout.size()`, and may or may not have its contents initialized.
     *
     * It is recommended that overallocating as per the above is only performed if doing so
     * is cheap; there is no guarantee that the caller is able to take advantage of the
     * returned excess. Implementors are free to e.g. provide an alternate method to query
     * available excess if doing so is expensive and should be left to the caller.
     *
     * Note that the returned block of memory is considered [*currently allocated*]
     * with this allocator (and equivalent allocators).
     * Therefore, it is the responsibility of implementors of `Allocator` to make sure that
     * this block of memory remains valid until it is [*invalidated*].
     *
     * [*currently allocated*]: #currently-allocated-memory
     * [*invalidated*]: #invalidating-memory-blocks
     *
     * # Errors
     *
     * Returning `Err` indicates that either memory is exhausted or `layout` does not meet
     * allocator's size or alignment constraints.
     *
     * Implementations are encouraged to return `Err` on memory exhaustion rather than
     * aborting, but this is not a strict requirement. (Specifically: it is *legal* to implement
     * this trait atop an underlying native allocation library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to an allocation error are encouraged to
     * call the [handleAllocError] function, rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun allocate(layout: Layout): Result<NonNull, AllocError>

    /**
     * Behaves like `allocate`, but also ensures that the returned memory is zero-initialized.
     *
     * # Errors
     *
     * Returning `Err` indicates that either memory is exhausted or `layout` does not meet
     * allocator's size or alignment constraints.
     *
     * Implementations are encouraged to return `Err` on memory exhaustion rather than
     * aborting, but this is not a strict requirement. (Specifically: it is *legal* to implement
     * this trait atop an underlying native allocation library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to an allocation error are encouraged to
     * call the [handleAllocError] function, rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun allocateZeroed(layout: Layout): Result<NonNull, AllocError> {
        val ptr = when (val tryResult = this.allocate(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.errValue()) }
        // SAFETY: `alloc` returns a valid memory block
        ptr.asNonNullPtr().asPtr().writeBytes(0, ptr.len())
        return Result.Ok<NonNull, AllocError>(ptr)
    }

    /**
     * Deallocates the memory referenced by `ptr`.
     *
     * # Safety
     *
     * * `ptr` must denote a block of memory [*currently allocated*] via this allocator, and
     * * `layout` must [*fit*] that block of memory.
     *
     * Note that it is *immediate* language UB for a deallocation or reallocation to
     * invalidate any outstanding references, smart pointers, etc.; thus, notably, an
     * allocator that has been moved into its own [*currently allocated*] memory may
     * not have its backing memory be freed, even if the allocator is never used again
     * afterwards. This is due to the fact that such a deallocation would invalidate the
     * `self` reference passed to this method.
     *
     * [*currently allocated*]: #currently-allocated-memory
     * [*fit*]: #memory-fitting
     */
    public fun deallocate(ptr: NonNull, layout: Layout)

    /**
     * Attempts to extend the memory block.
     *
     * Returns a new [`NonNull`][NonNull] containing a pointer and the actual size of the allocated
     * memory. The pointer is suitable for holding data described by `newLayout`. To accomplish
     * this, the allocator may extend the allocation referenced by `ptr` to fit the new layout.
     *
     * If this returns `Ok`, then the memory block referenced by `ptr` has been [*invalidated*].
     * The old `ptr` must not be used to access the memory, even if the allocation was grown in-place.
     * The newly returned pointer is the only valid pointer for accessing this memory now.
     * All bytes past `oldLayout.size()` should be assumed to be uninitialised.
     *
     * If this method returns `Err`, then the memory block has not been *invalidated*,
     * and the contents of the memory block are unaltered.
     *
     * # Safety
     *
     * * `ptr` must denote a block of memory [*currently allocated*] via this allocator.
     * * `oldLayout` must [*fit*] that block of memory (The `newLayout` argument need not fit it.).
     * * `newLayout.size()` must be greater than or equal to `oldLayout.size()`.
     *
     * Note that `newLayout.align()` need not be the same as `oldLayout.align()`.
     *
     * [*currently allocated*]: #currently-allocated-memory
     * [*fit*]: #memory-fitting
     * [*invalidated*]: #invalidating-memory-blocks
     *
     * # Errors
     *
     * Returns `Err` if the new layout does not meet the allocator's size and alignment
     * constraints of the allocator, or if growing otherwise fails.
     *
     * Implementations are encouraged to return `Err` on memory exhaustion rather than
     * aborting, but this is not a strict requirement. (Specifically: it is *legal* to implement
     * this trait atop an underlying native allocation library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to an allocation error are encouraged to
     * call the [handleAllocError] function, rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        if (false) {
            check(newLayout.size() >= oldLayout.size()) { "`new_layout.size()` must be greater than or equal to `old_layout.size()`" }
        }
        val newPtr = when (val tryResult = this.allocate(newLayout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.errValue()) }
        // SAFETY: because `newLayout.size()` must be greater than or equal to
        // `oldLayout.size()`, both the old and new memory allocation are valid for reads and
        // writes for `oldLayout.size()` bytes. Also, because the old allocation wasn't yet
        // deallocated, it cannot overlap `newPtr`. Thus, the call to `copyNonoverlapping` is
        // safe. The safety contract for `dealloc` must be upheld by the caller.
        run {
            copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asMutPtr(), oldLayout.size(), typeLayoutT = PrimitiveLayouts.U8)
            this.deallocate(ptr, oldLayout)
        }
        return Result.Ok<NonNull, AllocError>(newPtr)
    }

    /**
     * Behaves like `grow`, but also ensures that the new contents are set to zero before being
     * returned.
     *
     * The memory block will contain the following contents after a successful call to
     * `growZeroed`:
     *   * Bytes `0 .. oldLayout.size()` are preserved from the original allocation.
     *   * Bytes `oldLayout.size() .. newSize` are zeroed. `newSize` refers to the size
     *     of the memory block returned by the `growZeroed` call, which may be larger than
     *     `newLayout.size()`.
     *
     * # Safety
     *
     * * `ptr` must denote a block of memory [*currently allocated*] via this allocator.
     * * `oldLayout` must [*fit*] that block of memory (The `newLayout` argument need not fit it.).
     * * `newLayout.size()` must be greater than or equal to `oldLayout.size()`.
     *
     * Note that `newLayout.align()` need not be the same as `oldLayout.align()`.
     *
     * [*currently allocated*]: #currently-allocated-memory
     * [*fit*]: #memory-fitting
     *
     * # Errors
     *
     * Returns `Err` if the new layout does not meet the allocator's size and alignment
     * constraints of the allocator, or if growing otherwise fails.
     *
     * Implementations are encouraged to return `Err` on memory exhaustion rather than
     * aborting, but this is not a strict requirement. (Specifically: it is *legal* to implement
     * this trait atop an underlying native allocation library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to an allocation error are encouraged to
     * call the [handleAllocError] function, rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        if (false) {
            check(newLayout.size() >= oldLayout.size()) { "`new_layout.size()` must be greater than or equal to `old_layout.size()`" }
        }
        val newPtr = when (val tryResult = this.allocateZeroed(newLayout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.errValue()) }
        // SAFETY: because `newLayout.size()` must be greater than or equal to
        // `oldLayout.size()`, both the old and new memory allocation are valid for reads and
        // writes for `oldLayout.size()` bytes. Also, because the old allocation wasn't yet
        // deallocated, it cannot overlap `newPtr`. Thus, the call to `copyNonoverlapping` is
        // safe. The safety contract for `dealloc` must be upheld by the caller.
        run {
            copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asMutPtr(), oldLayout.size(), typeLayoutT = PrimitiveLayouts.U8)
            this.deallocate(ptr, oldLayout)
        }
        return Result.Ok<NonNull, AllocError>(newPtr)
    }

    /**
     * Attempts to shrink the memory block.
     *
     * Returns a new [`NonNull`][NonNull] containing a pointer and the actual size of the allocated
     * memory. The pointer is suitable for holding data described by `newLayout`. To accomplish
     * this, the allocator may shrink the allocation referenced by `ptr` to fit the new layout.
     *
     * If this returns `Ok`, then the memory block referenced by `ptr` has been [*invalidated*].
     * The old `ptr` must not be used to access the memory, even if the allocation was shrunk in-place.
     * The newly returned pointer is the only valid pointer for accessing this memory now.
     * All bytes past `newLayout.size()` should be assumed to be uninitialised.
     *
     * If this method returns `Err`, then the memory block has not been *invalidated*,
     * and the contents of the memory block are unaltered.
     *
     * # Safety
     *
     * * `ptr` must denote a block of memory [*currently allocated*] via this allocator.
     * * `oldLayout` must [*fit*] that block of memory (The `newLayout` argument need not fit it.).
     * * `newLayout.size()` must be smaller than or equal to `oldLayout.size()`.
     *
     * Note that `newLayout.align()` need not be the same as `oldLayout.align()`.
     *
     * [*currently allocated*]: #currently-allocated-memory
     * [*fit*]: #memory-fitting
     * [*invalidated*]: #invalidating-memory-blocks
     *
     * # Errors
     *
     * Returns `Err` if the new layout does not meet the allocator's size and alignment
     * constraints of the allocator, or if shrinking otherwise fails.
     *
     * Implementations are encouraged to return `Err` on memory exhaustion rather than
     * aborting, but this is not a strict requirement. (Specifically: it is *legal* to implement
     * this trait atop an underlying native allocation library that aborts on memory exhaustion.)
     *
     * Clients wishing to abort computation in response to an allocation error are encouraged to
     * call the [handleAllocError] function, rather than directly invoking `panic` or similar.
     *
     * [handleAllocError]: ../../alloc/alloc/fn.handleAllocError.html
     */
    public fun shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        if (false) {
            check(newLayout.size() <= oldLayout.size()) { "`new_layout.size()` must be smaller than or equal to `old_layout.size()`" }
        }
        val newPtr = when (val tryResult = this.allocate(newLayout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.errValue()) }
        // SAFETY: because `newLayout.size()` must be lower than or equal to
        // `oldLayout.size()`, both the old and new memory allocation are valid for reads and
        // writes for `newLayout.size()` bytes. Also, because the old allocation wasn't yet
        // deallocated, it cannot overlap `newPtr`. Thus, the call to `copyNonoverlapping` is
        // safe. The safety contract for `dealloc` must be upheld by the caller.
        run {
            copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asMutPtr(), newLayout.size(), typeLayoutT = PrimitiveLayouts.U8)
            this.deallocate(ptr, oldLayout)
        }
        return Result.Ok<NonNull, AllocError>(newPtr)
    }
}

/**
 * An [Allocator] that can be registered as the standard library’s default
 * through the `@globalAllocator` attribute.
 *
 * Types implementing this trait can be used as the default allocator for
 * memory allocations through `Holder`, `List` and the collection types. For
 * instance, the `System` allocator implements this trait, and thus can be
 * explicitly set as the default like so:
 * ```kotlin
 * import std.alloc.System
 *
 * @globalAllocator
 * var ALLOCATOR: System = System
 * ```
 *
 * The `Global` allocator forwards all memory allocation requests to the
 * `static` annotated with `@globalAllocator`. Hence, `Global` does not
 * implement `GlobalAllocator` itself, as that would lead to infinite recursion.
 *
 * # Note to implementors
 *
 * This trait is used to prevent the infinite recursion that would occur if the
 * default allocator were to attempt to allocate memory through `Global` (and
 * thus from itself).
 *
 * When to implement this trait:
 * * for custom global allocators that only use system memory allocation
 *   services.
 * * for allocators that wrap another allocator that implements `GlobalAllocator`.
 *
 * When **not** to implement this trait:
 * * for wrappers of arbitrary allocators (which might end up being `Global`,
 *   leading to infinite recursion).
 *
 * # Safety
 *
 * When implementing a global allocator, one has to be careful not to create an infinitely
 * recursive implementation by accident, as many constructs in the Rust standard library may
 * allocate in their implementation. For example, on some platforms, [std.sync.Mutex] may
 * allocate, so using it is highly problematic in a global allocator.
 *
 * For this reason, one should generally stick to library features available through
 * [core], and avoid using [std] in a global allocator. A few features from [std] are
 * guaranteed to not use `@globalAllocator` to allocate:
 *
 *  - [std.threadLocal],
 *  - [std.thread.current],
 *  - [std.thread.park] and [std.thread.Thread]s [unpark] method and
 * [Clone] implementation.
 *
 * [std]: ../../std/index.html
 * [std.sync.Mutex]: ../../std/sync/struct.Mutex.html
 * [std.threadLocal]: ../../std/macro.threadLocal.html
 * [std.thread.current]: ../../std/thread/fn.current.html
 * [std.thread.park]: ../../std/thread/fn.park.html
 * [std.thread.Thread]: ../../std/thread/struct.Thread.html
 * [unpark]: ../../std/thread/struct.Thread.html#method.unpark
 */
public interface GlobalAllocator : StaticAllocator

/**
 * Marks a type's [Clone] implementation as sound with regard to [Allocator] equivalence.
 * Implementors must ensure that, upon cloning, the two allocators are equivalent
 * (i.e. it is possible to free memory with one that was allocated with the other).
 * Further, mutable accesses such as moving or dropping the allocator must not invalidate
 * its currently allocated blocks at least so long as clones exist.
 *
 * Additionally, the bound that allocators do not unwind when (de)allocating also applies
 * to guaranteeing allocators will not unwind when cloned.
 *
 * It must also be the case that types which are `AllocatorClone` are either explicitly not
 * copyable (such as by containing a `!Copy` field) or that copying them also respects allocator
 * equivalence as if it had been a clone.
 */
public interface AllocatorClone : Allocator

/**
 * Marks that an allocator and its supertypes will never invalidate currently allocated
 * memory unless explicitly deallocated via a call to a deallocating method, even if
 * dropped or if the allocator's scope expires.
 *
 * This is a necessity in conjunction with [Pin], as only allocators that promise
 * memory is never reused without a destructor running may be used to back a pinned pointer.
 *
 * # Safety
 *
 * Implementors must ensure that memory blocks are *only, ever* invalidated by a
 * call to a de/reallocating method on `Allocator`, and that this holds true for all
 * possible instances of all subtypes of the implementor as well.
 *
 * These requirements trivially apply to allocators that always maintain global state, such as
 * `System` or `Global`. However, due to subtype coercion, it is *not* sound to implement
 * for an arbitrary `Allocator +` due to [edge-case interactions][unsound] with e.g.
 * `Pin.clone`. Namely, an impl of `StaticAllocator for MyAllocator +` guarantees that any
 * value of `MyAllocator +` also fulfills the requirements of `StaticAllocator`.
 *
 * The following must thus be guaranteed:
 * - the `Drop` impl of the allocator does not invalidate any allocations;
 * - the allocator does not expose a safe API surface that allows invalidating
 *   its allocations;
 * - the allocator's scope expiring does not invalidate any allocations;
 * - the above also hold for all equivalent allocators (see [Allocator] docs).
 *
 * [Pin]: ../../core/pin/struct.Pin.html
 * [unsound]: https://github.com/rust-lang/rust/issues/157089
 */
public interface StaticAllocator : Allocator

/**
 * Allocator marker trait that is only implemented stably for `Global`.
 *
 * Enabling `feature(allocatorExt)` causes this trait to be implemented for all
 * allocators in std, and for any downstream allocator that explicitly implements it.
 *
 * This trait is used as a bound whenever a function constructing
 * a type with an `@unstable A: Allocator = Global` parameter
 * may be callable for `A != Global`.
 *
 * This is to prevent stable code from e.g. constructing `Arc<T, NotGlobal>`
 * using the `From<Holder<T, A>> for Arc<T, A>` impl.
 *
 * Note that this trait *will* be removed in the future, but downstream
 * allocators should still feel free to implement it on nightly, to
 * allow their allocator to be used with all unstable allocator APIs.
 */
public interface AllocatorNightly : Allocator
