// port-lint: source global.rs
package io.github.kotlinmania.rustlang.dlmalloc.global

import io.github.kotlinmania.rustlang.core.alloc.global.GlobalAlloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.dlmalloc.Dlmalloc
import io.github.kotlinmania.rustlang.unix.acquireGlobalLock
import io.github.kotlinmania.rustlang.unix.releaseGlobalLock

/**
 * An instance of a "global allocator" backed by `Dlmalloc`
 *
 * This API requires the `global` feature is activated, and this type
 * implements the `GlobalAlloc` trait in the standard library.
 */
public open class GlobalDlmalloc : GlobalAlloc {
    public open fun alloc(layout: Layout): MutPtr {
        val dlmalloc = addrOfMut(DLMALLOC)
        return dlmalloc.malloc(layout.size(), layout.align())
    }

    public open fun dealloc(ptr: MutPtr, layout: Layout) {
        val dlmalloc = addrOfMut(DLMALLOC)
        dlmalloc.free(ptr, layout.size(), layout.align())
    }

    public open fun allocZeroed(layout: Layout): MutPtr {
        val dlmalloc = addrOfMut(DLMALLOC)
        return dlmalloc.calloc(layout.size(), layout.align())
    }

    public open fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
        val dlmalloc = addrOfMut(DLMALLOC)
        return dlmalloc.realloc(ptr, layout.size(), layout.align(), newSize)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<GlobalDlmalloc> =
            object : TypeLayout<GlobalDlmalloc> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): GlobalDlmalloc =
                    GlobalDlmalloc()
                override fun writeNative(addr: Long, value: GlobalDlmalloc) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): GlobalDlmalloc =
                    GlobalDlmalloc()
                override fun writeBytes(buf: ByteArray, offset: Int, value: GlobalDlmalloc) {
                }
            }
    }
}

public var DLMALLOC: Dlmalloc = Dlmalloc.new()

public fun lock(): Any {
    open class Guard {
        public open fun drop() {
            releaseGlobalLock()
        }
    }
    acquireGlobalLock()
    return Guard()
}
