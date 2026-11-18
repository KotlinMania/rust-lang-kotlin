// port-lint: source symbolize/gimli/libs_windows.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libswindows

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.coff.getImageBase
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsString
import io.github.kotlinmania.rustlang.backtrace.windowssys.INVALID_HANDLE_VALUE
import io.github.kotlinmania.rustlang.backtrace.windowssys.Moduleentry32w
import io.github.kotlinmania.rustlang.backtrace.windowssys.TH32CS_SNAPMODULE
import io.github.kotlinmania.rustlang.backtrace.windowssys.TRUE
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal

// For loading native libraries on Windows, see some discussion on
// rust-lang/rust#71060 for the various strategies here.
public fun nativeLibraries(): List<Library> {
    val ret = mutableListOf<Library>()
    run {
        addLoadedImages(ret)
    }
    return ret
}

public fun addLoadedImages(ret: MutableList<Library>) {
    run {
        val snap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, 0)
        if (snap == INVALID_HANDLE_VALUE) {
            return
        }
        // huge struct, probably should avoid manually initializing it even if we can
        val me = MaybeUninit.zeroed(PrimitiveLayouts.U8).assumeInit()
        me.dwSize = sizeOfVal(me).toUInt()
        if (Module32FirstW(snap, me) == TRUE) {
            while (true) {
                when (val matchVal = loadLibrary(me)) {
                    null -> {}
                    else -> {
                        val lib = matchVal
                        ret.add(lib)
                    }
                }
                if (Module32NextW(snap, me) != TRUE) {
                    break
                }
            }
        }
        CloseHandle(snap)
    }
}

public fun loadLibrary(me: Moduleentry32w): Library? {
    val name = run {
        val pos = (me.szExePath.indexOfFirst({ i -> i == 0 }).takeIf { it >= 0 } ?: me.szExePath.size)
        OsString.fromWide(((me.szExePath).sliceArray(0 until pos)))
    }
    // MinGW libraries currently don't support ASLR
    // (rust-lang/rust#16514), but DLLs can still be relocated around in
    // the address space. It appears that addresses in debug info are
    // all as-if this library was loaded at its "image base", which is a
    // field in its COFF file headers. Since this is what debuginfo
    // seems to list we parse the symbol table and store addresses as if
    // the library was loaded at "image base" as well.
    //
    // The library may not be loaded at "image base", however.
    // (presumably something else may be loaded there?) This is where
    // the `bias` field comes into play, and we need to figure out the
    // value of `bias` here. Unfortunately though it's not clear how to
    // acquire this from a loaded module. What we do have, however, is
    // the actual load address (`modBaseAddr`).
    //
    // As a bit of a cop-out for now we mmap the file, read the file
    // header information, then drop the mmap. This is wasteful because
    // we'll probably reopen the mmap later, but this should work well
    // enough for now.
    //
    // Once we have the `imageBase` (desired load location) and the
    // `baseAddr` (actual load location) we can fill in the `bias`
    // (difference between the actual and desired) and then the stated
    // address of each segment is the `imageBase` since that's what the
    // file says.
    //
    // For now it appears that unlike ELF/MachO we can make do with one
    // segment per library, using `modBaseSize` as the whole size.
    val mmap = (mmap(name.asRef()) ?: return null)
    val imageBase = (getImageBase(mmap) ?: return null)
    val baseAddr = me.modBaseAddr.rawAddress.toInt()
    return Library(name = name, bias = (baseAddr - (imageBase.toInt())), segments = mutableListOf(LibrarySegment(statedVirtualMemoryAddress = imageBase, len = me.modBaseSize.toInt())))
}
