package io.github.kotlinmania.rustlang.dlmalloc

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.dlmalloc.sys.System

/**
 * An allocator instance
 *
 * Instances of this type are used to allocate blocks of memory. For best
 * results only use one of these. Currently doesn't implement `Drop` to release
 * lingering memory back to the OS. That may happen eventually though!
 */
public open class Dlmalloc<A>(
    public var value: io.github.kotlinmania.rustlang.dlmalloc.dlmalloc.Dlmalloc<A>,
) {
    public var field0: io.github.kotlinmania.rustlang.dlmalloc.dlmalloc.Dlmalloc<A>
        get() = value
        set(v) {
            value = v
        }

    /**
     * Allocates `size` bytes with `align` align.
     *
     * Returns a null pointer if allocation fails. Returns a valid pointer
     * otherwise.
     *
     * Safety and contracts are largely governed by the `GlobalAlloc.alloc`
     * method contracts.
     */
    public open fun malloc(size: Int, align: Int): MutPtr =
        if (align <= this.value.mallocAlignment()) {
            this.value.malloc(size)
        } else {
            this.value.memalign(align, size)
        }

    /**
     * Same as `malloc`, except if the allocation succeeds it's guaranteed to
     * point to `size` bytes of zeros.
     */
    public open fun calloc(size: Int, align: Int): MutPtr {
        val ptr = this.malloc(size, align)
        if (!ptr.isNull() && this.value.callocMustClear(ptr)) {
            writeBytes(ptr, 0, size)
        }
        return ptr
    }

    /**
     * Deallocates a `ptr` with `size` and `align` as the previous request used
     * to allocate it.
     *
     * Safety and contracts are largely governed by the `GlobalAlloc.dealloc`
     * method contracts.
     */
    public open fun free(ptr: MutPtr, size: Int, align: Int) {
        this.value.validateSize(ptr, size)
        this.value.free(ptr)
    }

    /**
     * Reallocates `ptr`, a previous allocation with `oldSize` and
     * `oldAlign`, to have `newSize` and the same alignment as before.
     *
     * Returns a null pointer if the memory couldn't be reallocated, but `ptr`
     * is still valid. Returns a valid pointer and frees `ptr` if the request
     * is satisfied.
     *
     * Safety and contracts are largely governed by the `GlobalAlloc.realloc`
     * method contracts.
     */
    public open fun realloc(ptr: MutPtr, oldSize: Int, oldAlign: Int, newSize: Int): MutPtr {
        this.value.validateSize(ptr, oldSize)
        return if (oldAlign <= this.value.mallocAlignment()) {
            this.value.realloc(ptr, newSize)
        } else {
            val res = this.malloc(newSize, oldAlign)
            if (!res.isNull()) {
                val size = minOf(oldSize, newSize)
                copyNonoverlapping(ptr, res, size, typeLayoutT = PrimitiveLayouts.U8)
                this.free(ptr, oldSize, oldAlign)
            }
            res
        }
    }

    /**
     * If possible, gives memory back to the system if there is unused memory
     * at the high end of the malloc pool or in unused segments.
     *
     * You can call this after freeing large blocks of memory to potentially
     * reduce the system-level memory requirements of a program. However, it
     * cannot guarantee to reduce memory. Under some allocation patterns, some
     * large free blocks of memory will be locked between two used chunks, so
     * they cannot be given back to the system.
     *
     * The `pad` argument represents the amount of free trailing space to
     * leave untrimmed. If this argument is zero, only the minimum amount of
     * memory to maintain internal data structures will be left. Non-zero
     * arguments can be supplied to maintain enough trailing space to service
     * future expected allocations without having to re-obtain memory from the
     * system.
     *
     * Returns `true` if it actually released any memory, else `false`.
     */
    public open fun trim(pad: Int): Boolean = this.value.trim(pad)

    /**
     * Releases all allocations in this allocator back to the system,
     * consuming self and preventing further use.
     *
     * Returns the number of bytes released to the system.
     */
    public open fun destroy(): Int = this.value.destroy()

    /**
     * Get a reference to the underlying [Allocator] that this `Dlmalloc` was
     * constructed with.
     */
    public open fun allocator(): A = this.value.allocator()

    /**
     * Get a mutable reference to the underlying [Allocator] that this
     * `Dlmalloc` was constructed with.
     */
    public open fun allocatorMut(): A = this.value.allocatorMut()

    public companion object {
        /**
         * Creates a new instance of an allocator
         */
        public fun <A> newWithAllocator(sysAllocator: A): Dlmalloc<A> = Dlmalloc(io.github.kotlinmania.rustlang.dlmalloc.dlmalloc.Dlmalloc.new(sysAllocator))
    }
}

/**
 * Creates a new instance of an allocator
 */
public fun Dlmalloc.Companion.new(): Dlmalloc<System> = Dlmalloc(io.github.kotlinmania.rustlang.dlmalloc.dlmalloc.Dlmalloc.new(System.new()))
