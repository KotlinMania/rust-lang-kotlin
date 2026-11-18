package io.github.kotlinmania.rustlang.std.sys.alloc

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.alloc
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.dealloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.setthreaddescription.copyNonoverlapping

public fun reallocFallback(ptr: MutPtr, oldLayout: Layout, newSize: Int): MutPtr {
    // SAFETY: Docs for GlobalAlloc.realloc require this to be valid
    return run {
        val newLayout = Layout.fromSizeAlignUnchecked(newSize, oldLayout.align())
        val newPtr = alloc(newLayout)
        if (!newPtr.isNull()) {
            val size = minOf(oldLayout.size(), newSize)
            copyNonoverlapping((ptr).asConstPtr(), newPtr, size, typeLayoutT = PrimitiveLayouts.U8)
            dealloc(ptr, oldLayout)
        }
        newPtr
    }
}
