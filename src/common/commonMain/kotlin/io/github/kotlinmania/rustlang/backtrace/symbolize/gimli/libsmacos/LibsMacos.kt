// port-lint: source symbolize/gimli/libs_macos.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsmacos

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.libc.dyldGetImageHeader
import io.github.kotlinmania.rustlang.libc.dyldGetImageName
import io.github.kotlinmania.rustlang.libc.dyldGetImageVmaddrSlide
import io.github.kotlinmania.rustlang.libc.dyldImageCount

// Note: replace with ptr.fromRef once MSRV is high enough
public fun <T> ptrFromRef(r: T): ConstPtr = r

public fun nativeLibraries(): List<Library> {
    val ret = mutableListOf<Library>()
    val images = dyldImageCount()
    for (i in 0 until images) {
        ret.extend(nativeLibrary(i))
    }
    return ret
}

public fun nativeLibrary(i: UInt): Library? {
    // Fetch the name of this library which corresponds to the path of
    // where to load it as well.
    val name1 =
        run {
            val name = dyldGetImageName(i)
            if (name.isNull()) {
                return null
            }
            CStr.fromPtr(name)
        }
    // Load the image header of this library and delegate to `object` to
    // parse all the load commands so we can figure out all the segments
    // involved here.
    var (loadCommands, endian2) =
        run {
            val header = dyldGetImageHeader(i)
            if (header.isNull()) {
                return null
            }
            when (header.magic) {
                macho.mhMagic -> {
                    val endian = NativeEndian
                    val header1 = header.cast()
                    val data = linearSliceView(ConstPtr((ptrFromRef(header1)).rawAddress), sizeOfVal(header1) + header1.sizeofcmds.get(endian).toInt(), PrimitiveLayouts.U8)
                    Pair((header1.loadCommands(endian, data, 0).ok() ?: return null), endian)
                }
                macho.mhMagic64 -> {
                    val endian1 = NativeEndian
                    val header2 = header.cast()
                    val data1 = linearSliceView(ConstPtr((ptrFromRef(header2)).rawAddress), sizeOfVal(header2) + header2.sizeofcmds.get(endian1).toInt(), PrimitiveLayouts.U8)
                    Pair((header2.loadCommands(endian1, data1, 0).ok() ?: return null), endian1)
                }
                else -> {
                    return null
                }
            }
        }
    // Iterate over the segments and register known regions for segments
    // that we find. Additionally record information bout text segments
    // for processing later, see comments below.
    var segments = mutableListOf<LibrarySegment>()
    var firstText = 0
    var textFileoffZero = false
    while (true) {
        val matchVal = (loadCommands.next().ok() ?: return null)
        if (matchVal == null) break
        val cmd = matchVal
        when (val matchVal1 = (cmd.segment32().ok() ?: return null)) {
            null -> {}
            else -> {
                val seg = matchVal1.first
                if (seg.name() == "__TEXT".encodeToByteArray()) {
                    firstText = segments.size
                    if (seg.fileoff(endian2) == 0 && seg.filesize(endian2) > 0) {
                        textFileoffZero = true
                    }
                }
                segments.add(LibrarySegment(len = (seg.vmsize(endian2).tryInto().ok() ?: return null), statedVirtualMemoryAddress = (seg.vmaddr(endian2).tryInto().ok() ?: return null)))
            }
        }
        when (val matchVal2 = (cmd.segment64().ok() ?: return null)) {
            null -> {}
            else -> {
                val seg1 = matchVal2.first
                if (seg1.name() == "__TEXT".encodeToByteArray()) {
                    firstText = segments.size
                    if (seg1.fileoff(endian2) == 0 && seg1.filesize(endian2) > 0) {
                        textFileoffZero = true
                    }
                }
                segments.add(LibrarySegment(len = (seg1.vmsize(endian2).tryInto().ok() ?: return null), statedVirtualMemoryAddress = (seg1.vmaddr(endian2).tryInto().ok() ?: return null)))
            }
        }
    }
    // Determine the "slide" for this library which ends up being the
    // bias we use to figure out where in memory objects are loaded.
    // This is a bit of a weird computation though and is the result of
    // trying a few things in the wild and seeing what sticks.
    //
    // The general idea is that the `bias` plus a segment's
    // `statedVirtualMemoryAddress` is going to be where in the
    // actual address space the segment resides. The other thing we rely
    // on though is that a real address minus the `bias` is the index to
    // look up in the symbol table and debuginfo.
    //
    // It turns out, though, that for system loaded libraries these
    // calculations are incorrect. For native executables, however, it
    // appears correct. Lifting some logic from LLDB's source it has
    // some special-casing for the first `__TEXT` section loaded from
    // file offset 0 with a nonzero size. For whatever reason when this
    // is present it appears to mean that the symbol table is relative
    // to just the vmaddr slide for the library. If it's *not* present
    // then the symbol table is relative to the vmaddr slide plus the
    // segment's stated address.
    //
    // To handle this situation if we *don't* find a text section at
    // file offset zero then we increase the bias by the first text
    // sections's stated address and decrease all stated addresses by
    // that amount as well. That way the symbol table is always appears
    // relative to the library's bias amount. This appears to have the
    // right results for symbolizing via the symbol table.
    //
    // Honestly I'm not entirely sure whether this is right or if
    // there's something else that should indicate how to do this. For
    // now though this seems to work well enough (?) and we should
    // always be able to tweak this over time if necessary.
    //
    // For some more information see #318
    var slide = dyldGetImageVmaddrSlide(i).toInt()
    if (!textFileoffZero) {
        val adjust = segments[firstText].statedVirtualMemoryAddress
        for (segment in ArrayDeque(segments)) {
            segment.statedVirtualMemoryAddress -= adjust
        }
        slide += adjust
    }
    return Library(name = OsStr.fromBytes(name1.toBytes()).toOwned(), segments = segments, bias = slide)
}
