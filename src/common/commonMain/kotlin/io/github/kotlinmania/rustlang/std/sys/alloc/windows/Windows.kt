// port-lint: source sys/alloc/windows.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.windows

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.MIN_ALIGN
import io.github.kotlinmania.rustlang.std.sys.alloc.reallocFallback
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle

// Implements `System` on Windows.
//
// All pointers returned by this allocator have, in addition to the guarantees of `GlobalAlloc`, the
// following properties:
//
// If the pointer was allocated or reallocated with a `layout` specifying an alignment <= `MIN_ALIGN`
// the pointer will be aligned to at least `MIN_ALIGN` and point to the start of the allocated block.
//
// If the pointer was allocated or reallocated with a `layout` specifying an alignment > `MIN_ALIGN`
// the pointer will be aligned to the specified alignment and not point to the start of the allocated block.
// Instead there will be a header readable directly before the returned pointer, containing the actual
// location of the start of the block.

// Heap memory management on Windows is done by using the system Heap API (heapapi.h)
// See https://docs.microsoft.com/windows/win32/api/heapapi/
// Flag to indicate that the memory returned by `HeapAlloc` should be zeroed.
public val HEAP_ZERO_MEMORY: UInt = 0x00000008u

// Get a handle to the default heap of the current process, or null if the operation fails.
//
// SAFETY: Successful calls to this function within the same process are assumed to
// always return the same handle, which remains valid for the entire scope of the process.
//
// See https://docs.microsoft.com/windows/win32/api/heapapi/nf-heapapi-getprocessheap
public expect fun getProcessHeap(): Handle

// Allocate a block of `dwBytes` bytes of memory from a given heap `hHeap`.
// The allocated memory may be uninitialized, or zeroed if `dwFlags` is
// set to `HEAP_ZERO_MEMORY`.
//
// Returns a pointer to the newly-allocated memory or null if the operation fails.
// The returned pointer will be aligned to at least `MIN_ALIGN`.
//
// SAFETY:
// - `hHeap` must be a non-null handle returned by `GetProcessHeap`.
// - `dwFlags` must be set to either zero or `HEAP_ZERO_MEMORY`.
//
// Note that `dwBytes` is allowed to be zero, contrary to some other allocators.
//
// See https://docs.microsoft.com/windows/win32/api/heapapi/nf-heapapi-heapalloc
public expect fun heapAlloc(hheap: Handle, dwflags: UInt, dwbytes: ULong): MutPtr

// Reallocate a block of memory behind a given pointer `lpMem` from a given heap `hHeap`,
// to a block of at least `dwBytes` bytes, either shrinking the block in place,
// or allocating at a new location, copying memory, and freeing the original location.
//
// Returns a pointer to the reallocated memory or null if the operation fails.
// The returned pointer will be aligned to at least `MIN_ALIGN`.
// If the operation fails the given block will never have been freed.
//
// SAFETY:
// - `hHeap` must be a non-null handle returned by `GetProcessHeap`.
// - `dwFlags` must be set to zero.
// - `lpMem` must be a non-null pointer to an allocated block returned by `HeapAlloc` or
// `HeapReAlloc`, that has not already been freed.
// If the block was successfully reallocated at a new location, pointers pointing to
// the freed memory, such as `lpMem`, must not be dereferenced ever again.
//
// Note that `dwBytes` is allowed to be zero, contrary to some other allocators.
//
// See https://docs.microsoft.com/windows/win32/api/heapapi/nf-heapapi-heaprealloc
public expect fun heapReAlloc(hheap: Handle, dwflags: UInt, lpmem: ConstPtr, dwbytes: ULong): MutPtr

// Free a block of memory behind a given pointer `lpMem` from a given heap `hHeap`.
// Returns a nonzero value if the operation is successful, and zero if the operation fails.
//
// SAFETY:
// - `hHeap` must be a non-null handle returned by `GetProcessHeap`.
// - `dwFlags` must be set to zero.
// - `lpMem` must be a pointer to an allocated block returned by `HeapAlloc` or `HeapReAlloc`,
// that has not already been freed.
// If the block was successfully freed, pointers pointing to the freed memory, such as `lpMem`,
// must not be dereferenced ever again.
//
// Note that `lpMem` is allowed to be null, which will not cause the operation to fail.
//
// See https://docs.microsoft.com/windows/win32/api/heapapi/nf-heapapi-heapfree
public expect fun heapFree(hheap: Handle, dwflags: UInt, lpmem: ConstPtr): Bool

public fun getProcessHeap(): MutPtr {
    // SAFETY: GetProcessHeap simply returns a valid handle or NULL so is always safe to call.
    return GetProcessHeap()
}

public fun processHeapAlloc(heap: MaybeUninit<Handle>, flags: UInt, bytes: Int): MutPtr {
    val heap = getProcessHeap()
    if (((heap).rawAddress == 0L)) {
        return MutPtr(0L)
    }
    // SAFETY: `heap` is a non-null handle returned by `GetProcessHeap`.
    return HeapAlloc(heap, flags, bytes)
}

// Header containing a pointer to the start of an allocated block.
// SAFETY: Size and alignment must be <= `MIN_ALIGN`.
public open class Header internal constructor(
    valueCtorArg: MutPtr,
) {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<Header> =
            object : TypeLayout<Header> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Header =
                    Header(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Header) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Header =
                    Header(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Header) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): Header = Header(
            valueCtorArg = value,
        )
    }
}

// Allocate a block of optionally zeroed memory for a given `layout`.
// SAFETY: Returns a pointer satisfying the guarantees of `System` about allocated pointers,
// or null if the operation fails. If this returns non-null `HEAP` will have been successfully
// initialized.
public fun allocate(layout: Layout, zeroed: Boolean): MutPtr {
    // Allocated memory will be either zeroed or uninitialized.
    val flags =
        if (zeroed) {
            HEAP_ZERO_MEMORY
        } else {
            0
        }
    return if (layout.align() <= MIN_ALIGN) {
        // The returned pointer points to the start of an allocated block.
        (processHeapAlloc(MaybeUninit.uninit(PrimitiveLayouts.mutPtrLayout(64L)), flags, layout.size())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    } else {
        // Allocate extra padding in order to be able to satisfy the alignment.
        // This addition does not overflow due to `Layout` type invariants,
        // `size()` is at most `Long.MAX` while
        // `align()` is at most `1<<(bits in Int - 2)` if `size()` is non-zero.
        val total = layout.align() + layout.size()
        val ptr = (processHeapAlloc(MaybeUninit.uninit(PrimitiveLayouts.mutPtrLayout(64L)), flags, total)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        if (((ptr).rawAddress == 0L)) {
            // Allocation has failed.
            return MutPtr(0L)
        }
        // Create a correctly aligned pointer offset from the start of the allocated block,
        // and write a header before it.
        // SAFETY: Guaranteed by Layout.align.
        // This hint is technically redundant, but currently LLVM needs this explicit hint to
        // optimize out a panic path inside alignOffset.
        run {
            assertUnchecked(layout.align().isPowerOfTwo())
        }
        val offset = MutPtr((ptr).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes)).alignOffset(layout.align()) + 1
        // SAFETY: `MIN_ALIGN` <= `offset` <= `layout.align()` and the size of the allocated
        // block is `layout.align() + layout.size()`. `aligned` will thus be a correctly aligned
        // pointer inside the allocated block with at least `layout.size()` bytes after it and at
        // least `MIN_ALIGN` bytes of padding before it.
        val aligned = MutPtr((ptr).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))
        // SAFETY: Because the size and alignment of a header is <= `MIN_ALIGN` and `aligned`
        // is aligned to at least `MIN_ALIGN` and has at least `MIN_ALIGN` bytes of padding before
        // it, it is safe to write a header directly before it.
        write(MutPtr(((MutPtr(aligned.rawAddress, aligned.rawMeta))).rawAddress - ((1).toLong() * (Header.typeLayout()).strideBytes)), Header(ptr), typeLayoutT = Header.typeLayout())
        // SAFETY: The returned pointer does not point to the start of an allocated block,
        // but there is a header readable directly before it containing the location of the start
        // of the block.
        aligned
    }
}

public fun alloc(layout: Layout): MutPtr {
    // SAFETY: Pointers returned by `allocate` satisfy the guarantees of `System`
    val zeroed = false
    return allocate(layout, zeroed)
}

public fun allocZeroed(layout: Layout): MutPtr {
    // SAFETY: Pointers returned by `allocate` satisfy the guarantees of `System`
    val zeroed = true
    return allocate(layout, zeroed)
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    val block = if (layout.align() <= MIN_ALIGN) {
        ptr
    } else {
        // The location of the start of the block is stored in the padding before `ptr`.
        // SAFETY: Because of the contract of `System`, `ptr` is guaranteed to be non-null
        // and have a header readable directly before it.
        read(MutPtr(((MutPtr(ptr.rawAddress, ptr.rawMeta))).rawAddress - ((1).toLong() * (Header.typeLayout()).strideBytes)), typeLayoutT = Header.typeLayout()).first
    }
    // because `ptr` has been successfully allocated with this allocator,
    // there must be a valid process heap.
    val heap = getProcessHeap()
    // SAFETY: `heap` is a non-null handle returned by `GetProcessHeap`,
    // `block` is a pointer to the start of an allocated block.
    HeapFree(heap, 0, MutPtr((block).rawAddress))
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    return if (layout.align() <= MIN_ALIGN) {
        // because `ptr` has been successfully allocated with this allocator,
        // there must be a valid process heap.
        val heap = getProcessHeap()
        // SAFETY: `heap` is a non-null handle returned by `GetProcessHeap`,
        // `ptr` is a pointer to the start of an allocated block.
        // The returned pointer points to the start of an allocated block.
        MutPtr((HeapReAlloc(heap, 0, MutPtr((ptr).rawAddress), newSize)).rawAddress)
    } else {
        // SAFETY: `reallocFallback` is implemented using `dealloc` and `alloc`, which will
        // correctly handle `ptr` and return a pointer satisfying the guarantees of `System`
        reallocFallback(ptr, layout, newSize)
    }
}
