// port-lint: source symbolize/gimli/libs_libnx.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libslibnx

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.core.mem.addrOf

// DevkitA64 doesn't natively support debug info, but the build system will
// place debug info at the path `romfs: / debugInfo.elf`.
public fun nativeLibraries(): List<Library> {
    val START: UByte = 0u
    val bias = (addrOf(START)).toInt()
    val ret = mutableListOf<Library>()
    var segments = mutableListOf<Library>()
    segments.add(LibrarySegment(statedVirtualMemoryAddress = 0, len = Int.maxValue() - bias))
    val path = "romfs:/debug_info.elf"
    ret.add(Library(name = path.into(), segments = segments, bias = bias))
    return ret
}
