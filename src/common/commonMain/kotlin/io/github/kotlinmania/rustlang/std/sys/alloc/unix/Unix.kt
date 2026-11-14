// port-lint: source sys/alloc/unix.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.unix

import io.github.kotlinmania.libc.unix.bsd.apple.b64.x8664.calloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ptr
import io.github.kotlinmania.rustlang.std.sys.alloc.MIN_ALIGN
import io.github.kotlinmania.rustlang.std.sys.alloc.reallocFallback

public expect fun malloc(size: CSizeT): MutPtr

public expect fun realloc(ptr: MutPtr, size: CSizeT): MutPtr

public expect fun free(ptr: MutPtr)

public fun alloc(layout: Layout): MutPtr {
    // jemalloc provides alignment less than MIN_ALIGN for small allocations.
    // So only rely on MIN_ALIGN if size >= align.
    // Also see <https://github.com/rust-lang/rust/issues/45955> and
    // <https://github.com/rust-lang/rust/issues/62251#issuecomment-507580914>.
    return if (layout.align() <= MIN_ALIGN && layout.align() <= layout.size()) {
        (io.github.kotlinmania.libc.unix.bsd.apple.b64.x8664.malloc(layout.size())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    } else {
        // `posixMemalign` returns a non-aligned value if supplied a very
        // large alignment on older versions of Apple's platforms (unknown
        // exactly which version range, but the issue is definitely
        // present in macOS 10.14 and iOS 13.3).
        //
        // <https://github.com/rust-lang/rust/issues/30170>
        if (layout.align() > (1 shl 31)) {
            return MutPtr(0L)
        }
        alignedMalloc(layout)
    }
}

public fun allocZeroed(layout: Layout): MutPtr {
    // See the comment above in `alloc` for why this check looks the way it does.
    return if (layout.align() <= MIN_ALIGN && layout.align() <= layout.size()) {
        mutPtrFromRustCast(calloc(layout.size(), 1))
    } else {
        val ptr = alloc(layout)
        if (!ptr.isNull()) {
            ptr.writeBytes(ptr, 0, layout.size())
        }
        ptr
    }
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    io.github.kotlinmania.libc.psp.free(MutPtr(ptr.rawAddress, ptr.rawMeta))
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr =
    if (layout.align() <= MIN_ALIGN && layout.align() <= newSize) {
        mutPtrFromRustCast(io.github.kotlinmania.libc.unix.bsd.apple.b64.x8664.realloc(MutPtr(ptr.rawAddress, ptr.rawMeta), newSize))
    } else {
        reallocFallback(ptr, layout, newSize)
    }
