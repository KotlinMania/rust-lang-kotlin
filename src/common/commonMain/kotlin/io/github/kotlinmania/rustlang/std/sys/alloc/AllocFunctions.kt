package io.github.kotlinmania.rustlang.std.sys.alloc

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.alloc
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.dealloc

public fun reallocFallback(ptr: MutPtr, oldLayout: Layout, newSize: Int): MutPtr {
    // SAFETY: Docs for GlobalAlloc.realloc require this to be valid
    return run {
        val newLayout = Layout.fromSizeAlignUnchecked(newSize, oldLayout.align())
        val newPtr = alloc(newLayout)
        if (!newPtr.isNull()) {
            val size = minOf(oldLayout.size(), newSize)
            copyNonoverlapping(ptr, newPtr, size, typeLayoutT = PrimitiveLayouts.U8)
            dealloc(ptr, oldLayout)
        }
        newPtr
    }
}
