// port-lint: source sys/alloc/xous.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.xous

import io.github.kotlinmania.dlmalloc.Dlmalloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicI32
import io.github.kotlinmania.rustlang.std.os.xous.ffi.doYield

public var DLMALLOC: Dlmalloc = Dlmalloc.new()

public fun alloc(layout: Layout): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling malloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.malloc(layout.size(), layout.align())
}

public fun allocZeroed(layout: Layout): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling calloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.calloc(layout.size(), layout.align())
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling free() is safe because preconditions on this function match the trait method preconditions.
    DLMALLOC.free(ptr, layout.size(), layout.align())
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling realloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.realloc(ptr, layout.size(), layout.align(), newSize)
}

public var LOCKED: Atomic<Int> = AtomicI32.new(0)

public open class DropLock {
    public open fun drop() {
        val r = LOCKED.swap(0, Release)
        check(r == 1)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<DropLock> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<DropLock> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): DropLock =
                    DropLock()
                override fun writeNative(addr: Long, value: DropLock) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): DropLock =
                    DropLock()
                override fun writeBytes(buf: ByteArray, offset: Int, value: DropLock) {
                }
            }
    }
}

public fun lock(): DropLock {
    while (true) {
        if (LOCKED.swap(1, Acquire) == 0) {
            return DropLock()
        }
        doYield()
    }
}
