// port-lint: source sys/alloc/zkvm.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.zkvm

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.reallocFallback
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.abi.sysAllocAligned

public fun alloc(layout: Layout): MutPtr = sysAllocAligned(layout.size(), layout.align())

public fun dealloc(ptr: MutPtr, layout: Layout) {
    // this allocator never deallocates memory
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: this is just a `public` wrapper.
    return reallocFallback(ptr, layout, newSize)
}
