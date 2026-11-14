// port-lint: source sys/alloc/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.hermit

import io.github.kotlinmania.hermitabi.free
import io.github.kotlinmania.hermitabi.malloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun alloc(layout: Layout): MutPtr {
    val size = layout.size()
    val align = layout.align()
    return malloc(size, align)
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    val size = layout.size()
    val align = layout.align()
    run {
        free(ptr, size, align)
    }
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    val size = layout.size()
    val align = layout.align()
    return io.github.kotlinmania.hermitabi.realloc(ptr, size, align, newSize)
}
