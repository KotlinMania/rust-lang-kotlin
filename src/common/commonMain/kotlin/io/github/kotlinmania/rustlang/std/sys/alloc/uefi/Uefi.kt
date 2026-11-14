// port-lint: source sys/alloc/uefi.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.uefi

import io.github.kotlinmania.refi.protocols.LoadedImage
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.os.uefi.env.bootServices
import io.github.kotlinmania.rustlang.std.os.uefi.env.systemTable
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.alloc.reallocFallback
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.imageHandleProtocol

// Global Allocator for UEFI.
// Uses [r-efi-alloc](https://crates.io/crates/r-efi-alloc)

public fun alloc(layout: Layout): MutPtr {
    val EFI_MEMORY_TYPE: OnceLock<UInt>  = OnceLock.new()
    // Return null pointer if boot services are not available
    if (bootServices() == null) {
        return MutPtr(0L)
    }
    // If boot services is valid then SystemTable is not null.
    val systemTable = systemTable().asPtr().cast()
    // Each loaded image has an image handle that supports `EFI_LOADED_IMAGE_PROTOCOL`. Thus, this
    // will never fail.
    val memType =
        EFI_MEMORY_TYPE.getOrInit({
            val protocol = imageHandleProtocol<Protocol>(LoadedImage.PROTOCOL_GUID).unwrap()
            // Gives allocations the memory type that the data sections were loaded as.
            protocol.asPtr().imageDataType
        })
    // The caller must ensure non-0 layout
    return io.github.kotlinmania.refialloc.raw.alloc(systemTable, layout, memType)
}

public fun dealloc(ptr: MutPtr, layout: Layout) {
    // Do nothing if boot services are not available
    if (bootServices() == null) {
        return
    }
    // If boot services is valid then SystemTable is not null.
    val systemTable = systemTable().asPtr().cast()
    // The caller must ensure non-0 layout
    io.github.kotlinmania.refialloc.raw.dealloc(systemTable, ptr, layout)
}

public fun realloc(ptr: MutPtr, layout: Layout, newSize: Int): MutPtr {
    // SAFETY: this is just a `public` wrapper.
    return reallocFallback(ptr, layout, newSize)
}
