// port-lint: source sys/alloc/vexos.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.vexos

import io.github.kotlinmania.dlmalloc.Allocator
import io.github.kotlinmania.dlmalloc.Dlmalloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.ptr

// Symbols for heap section boundaries defined in the target's linkerscript
public expect var HEAP_START: UByte

public expect var HEAP_END: UByte

public var DLMALLOC: Dlmalloc<Vexos> = Dlmalloc.newWithAllocator(Vexos())

public open class Vexos : Allocator {
    /**
     * Allocs system resources
     */
    public open fun alloc(size: Int): Triple<MutPtr, Int, UInt> {
        val INIT: AtomicBool  = AtomicBool.new(false)
        return if (!INIT.swap(true, Ordering.Relaxed)) {
            // This target has no growable heap, as user memory has a fixed
            // size/location and VEXos does not manage allocation for us.
            Triple((addrOfMut(HEAP_START)), (addrOf(HEAP_END)).offsetFromUnsigned(addrOf(HEAP_START)), 0)
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
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Vexos> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Vexos> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Vexos =
                    Vexos()
                override fun writeNative(addr: Long, value: Vexos) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Vexos =
                    Vexos()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Vexos) {
                }
            }
    }
}

public fun alloc(layout: Layout): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because we are a single-threaded target, which
    // guarantees unique and non-reentrant access to the allocator. As such, no allocator lock is used.
    // Calling malloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.malloc(layout.size(), layout.align())
}

public fun allocZeroed(layout: Layout): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because we are a single-threaded target, which
    // guarantees unique and non-reentrant access to the allocator. As such, no allocator lock is used.
    // Calling calloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.calloc(layout.size(), layout.align())
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    // SAFETY: DLMALLOC access is guaranteed to be safe because we are a single-threaded target, which
    // guarantees unique and non-reentrant access to the allocator. As such, no allocator lock is used.
    // Calling free() is safe because preconditions on this function match the trait method preconditions.
    DLMALLOC.free(ptr, layout.size(), layout.align())
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because we are a single-threaded target, which
    // guarantees unique and non-reentrant access to the allocator. As such, no allocator lock is used.
    // Calling realloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.realloc(ptr, layout.size(), layout.align(), newSize)
}
