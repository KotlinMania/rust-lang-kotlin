package io.github.kotlinmania.rustlang.dlmalloc

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * In order for this crate to efficiently manage memory, it needs a way to communicate with the
 * underlying platform. This `Allocator` trait provides an interface for this communication.
 */
public interface Allocator {
    /**
     * Allocates system memory region of at least `size` bytes
     * Returns a triple of `(base, size, flags)` where `base` is a pointer to the beginning of the
     * allocated memory region. `size` is the actual size of the region while `flags` specifies
     * properties of the allocated region. If `EXTERN_BIT` (bit 0) set in flags, then we did not
     * allocate this segment and so should not try to deallocate or merge with others.
     * This function can return a `std.ptr.nullMut()` when allocation fails (other values of
     * the triple will be ignored).
     */
    public fun alloc(size: Int): Triple<MutPtr, Int, UInt>

    /**
     * Remaps system memory region at `ptr` with size `oldsize` to a potential new location with
     * size `newsize`. `canMove` indicates if the location is allowed to move to a completely new
     * location, or that it is only allowed to change in size. Returns a pointer to the new
     * location in memory.
     * This function can return a `std.ptr.nullMut()` to signal an error.
     */
    public fun remap(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr

    /**
     * Frees a part of a memory chunk. The original memory chunk starts at `ptr` with size `oldsize`
     * and is turned into a memory region starting at the same address but with `newsize` bytes.
     * Returns `true` iff the access memory region could be freed.
     */
    public fun freePart(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean

    /**
     * Frees an entire memory region. Returns `true` iff the operation succeeded. When `false` is
     * returned, the `dlmalloc` may re-use the location on future allocation requests
     */
    public fun free(ptr: MutPtr, size: Int): Boolean

    /**
     * Indicates if the system can release a part of memory. For the `flags` argument, see
     * `Allocator.alloc`
     */
    public fun canReleasePart(flags: UInt): Boolean

    /**
     * Indicates whether newly allocated regions contain zeros.
     */
    public fun allocatesZeros(): Boolean

    /**
     * Returns the page size. Must be a power of two
     */
    public fun pageSize(): Int
}
