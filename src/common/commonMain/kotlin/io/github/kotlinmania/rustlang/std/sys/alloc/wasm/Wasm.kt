// port-lint: source sys/alloc/wasm.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.wasm

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.cell.SyncUnsafeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// This is an implementation of a global allocator on wasm targets when
// emscripten or wasi is not in use. In that situation there's no actual runtime
// for us to lean on for allocation, so instead we provide our own!
//
// The wasm instruction set has two instructions for getting the current
// amount of memory and growing the amount of memory. These instructions are the
// foundation on which we're able to build an allocator, so we do so! Note that
// the instructions are also pretty "global" and this is the "global" allocator
// after all!
//
// The current allocator here is the `dlmalloc` crate which we've got included
// in the rust-lang/rust repository as a submodule. The crate is a port of
// dlmalloc.c from C to Rust and is basically just so we can have "pure Rust"
// for now which is currently technically required (can't link with C yet).
//
// The crate itself provides a global allocator which on wasm has no
// synchronization as there are no threads!

public open class SyncDlmalloc(
    public var value: Dlmalloc,
) {
    public var field0: Dlmalloc
        get() = value
        set(v) {
            value = v
        }
}

public var DLMALLOC: SyncUnsafeCell<SyncDlmalloc> = SyncUnsafeCell.new(SyncDlmalloc(Dlmalloc.new()))

public fun alloc(layout: Layout): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling malloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.get().first.malloc(layout.size(), layout.align())
}

public fun allocZeroed(layout: Layout): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling calloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.get().first.calloc(layout.size(), layout.align())
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling free() is safe because preconditions on this function match the trait method preconditions.
    DLMALLOC.get().first.free(ptr, layout.size(), layout.align())
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: DLMALLOC access is guaranteed to be safe because the lock gives us unique and non-reentrant access.
    // Calling realloc() is safe because preconditions on this function match the trait method preconditions.
    return DLMALLOC.get().first.realloc(ptr, layout.size(), layout.align(), newSize)
}
