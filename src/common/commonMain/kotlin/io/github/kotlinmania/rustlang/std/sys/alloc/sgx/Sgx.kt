// port-lint: source sys/alloc/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.sgx

import io.github.kotlinmania.dlmalloc.Allocator
import io.github.kotlinmania.dlmalloc.Dlmalloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.ptr
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.waitqueue.spinmutex.SpinMutex
import io.github.kotlinmania.sgxmem.heapBase
import io.github.kotlinmania.sgxmem.heapSize

// Using a SpinMutex because we never want to exit the enclave waiting for the
// allocator.
//
// The current allocator here is the `dlmalloc` crate which we've got included
// in the rust-lang/rust repository as a submodule. The crate is a port of
// dlmalloc.c from C to Rust.
//
// Specifying linkage/symbol name is solely to ensure a single instance between this crate and its unit tests
public var DLMALLOC: SpinMutex<Dlmalloc<Sgx>> = SpinMutex.new(Dlmalloc.newWithAllocator(Sgx()))

public open class Sgx : Allocator {
    /**
     * Allocs system resources
     */
    public open fun alloc(size: Int): Triple<MutPtr, Int, UInt> {
        val INIT: Atomic<Boolean>  = AtomicBool.new(false)
        // No ordering requirement since this function is protected by the global lock.
        return if (!INIT.swap(true, Ordering.Relaxed)) {
            Triple(((heapBase() as Any?) as Triple<MutPtr, Int, UInt>), heapSize(), 0)
        } else {
            Triple(MutPtr(0L), 0, 0)
        }
    }

    public open fun remap(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr = MutPtr(0L)

    public open fun freePart(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean = false

    public open fun free(ptr: MutPtr, size: Int): Boolean = false

    public open fun canReleasePart(flags: UInt): Boolean = false

    public open fun allocatesZeros(): Boolean = false

    public open fun pageSize(): Int = 0x1000

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sgx> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Sgx> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Sgx =
                    Sgx()
                override fun writeNative(addr: Long, value: Sgx) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sgx =
                    Sgx()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sgx) {
                }
            }
    }
}

public fun alloc(layout: Layout): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `malloc`
    return DLMALLOC.lock().malloc(layout.size(), layout.align())
}

public fun allocZeroed(layout: Layout): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `malloc`
    return DLMALLOC.lock().calloc(layout.size(), layout.align())
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    // SAFETY: the caller must uphold the safety contract for `malloc`
    DLMALLOC.lock().free(ptr, layout.size(), layout.align())
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: the caller must uphold the safety contract for `malloc`
    return DLMALLOC.lock().realloc(ptr, layout.size(), layout.align(), newSize)
}

// The following functions are needed by libunwind. These symbols are named
// in pre-link args for the target specification, so keep that in sync.
public fun rustCAlloc(size: Int, align: Int): MutPtr = alloc(Layout.fromSizeAlignUnchecked(size, align))

public fun rustCDealloc(ptr: MutPtr, size: Int, align: Int) {
    dealloc(ptr, Layout.fromSizeAlignUnchecked(size, align))
}
