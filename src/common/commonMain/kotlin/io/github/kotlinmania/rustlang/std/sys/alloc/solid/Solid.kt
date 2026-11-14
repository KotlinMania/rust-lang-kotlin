// port-lint: source sys/alloc/solid.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.solid

import io.github.kotlinmania.libc.psp.free
import io.github.kotlinmania.libc.unix.bsd.apple.b64.x8664.malloc
import io.github.kotlinmania.libc.unix.bsd.apple.b64.x8664.memalign
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.MIN_ALIGN
import io.github.kotlinmania.rustlang.std.sys.alloc.reallocFallback

public fun alloc(layout: Layout): MutPtr =
    if (layout.align() <= MIN_ALIGN && layout.align() <= layout.size()) {
        (malloc(layout.size())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    } else {
        mutPtrFromRustCast(memalign(layout.align(), layout.size()))
    }

public fun dealloc(ptr: MutPtr, layout: Layout) {
    free(MutPtr(ptr.rawAddress, ptr.rawMeta))
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr =
    if (layout.align() <= MIN_ALIGN && layout.align() <= newSize) {
        mutPtrFromRustCast(io.github.kotlinmania.libc.unix.bsd.apple.b64.x8664.realloc(MutPtr(ptr.rawAddress, ptr.rawMeta), newSize))
    } else {
        reallocFallback(ptr, layout, newSize)
    }
