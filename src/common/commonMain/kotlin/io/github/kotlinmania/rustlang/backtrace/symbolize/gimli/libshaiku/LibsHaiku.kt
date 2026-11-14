// port-lint: source symbolize/gimli/libs_haiku.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libshaiku

import io.github.kotlinmania.libc.unix.haiku.B_CURRENT_TEAM
import io.github.kotlinmania.libc.unix.haiku.B_OK
import io.github.kotlinmania.libc.unix.haiku.ImageInfo
import io.github.kotlinmania.libc.unix.haiku.getNextImageInfo
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.core.ffi.CStr
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

internal fun nativeLibraries(): List<Library> {
    val libraries: List<Library>  = mutableListOf()
    run {
        val info = MaybeUninit.zeroed(ObjectSlotLayout<ImageInfo>())
        val localStorageL21C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        // Load the first image to get a valid info struct
        var status = getNextImageInfo(B_CURRENT_TEAM, RustMutableReference(localStorageL21C8.asMutPtr(), localStorageL21C8.layout), info.asMutPtr())
        if (status != B_OK) {
            return libraries
        }
        val info1 = info.assumeInit()
        while (status == B_OK) {
            var segments = mutableListOf<Library>()
            segments.add(LibrarySegment(statedVirtualMemoryAddress = 0, len = info1.textSize.toInt()))
            val bytes = CStr.fromPtr(info1.name.asPtr()).toBytes()
            val name = OsStr.fromBytes(bytes).toOwned()
            libraries.add(Library(name = name, segments = segments, bias = info1.text.toInt()))
            status = getNextImageInfo(B_CURRENT_TEAM, RustMutableReference(localStorageL21C8.asMutPtr(), localStorageL21C8.layout), info1)
        }
    }
    return libraries
}
