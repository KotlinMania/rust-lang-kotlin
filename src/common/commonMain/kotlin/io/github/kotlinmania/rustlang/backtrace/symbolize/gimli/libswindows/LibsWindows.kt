// port-lint: source symbolize/gimli/libs_windows.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libswindows

import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.SizeT
import io.github.kotlinmania.libc.fuchsia.SsizeT
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.coff.getImageBase
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsString
import io.github.kotlinmania.rustlang.backtrace.windowssys.INVALID_HANDLE_VALUE
import io.github.kotlinmania.rustlang.backtrace.windowssys.Moduleentry32w
import io.github.kotlinmania.rustlang.backtrace.windowssys.TH32CS_SNAPMODULE
import io.github.kotlinmania.rustlang.backtrace.windowssys.TRUE
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// For loading native libraries on Windows, see some discussion on
// rust-lang/rust#71060 for the various strategies here.
internal fun nativeLibraries(): List<Library> {
    val ret = mutableListOf<Library>()
    run {
        addLoadedImages(ret)
    }
    return ret
}

public fun addLoadedImages(ret: List<Library>) {
    run {
        val snap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, 0)
        if (snap == INVALID_HANDLE_VALUE) {
            return
        }
        // huge struct, probably should avoid manually initializing it even if we can
        val me = MaybeUninit.zeroed(ObjectSlotLayout<Moduleentry32w>()).assumeInit()
        me.dwSize = mem.sizeOfVal(me).toUInt()
        if (Module32FirstW(snap, me) == TRUE) {
            while (true) {
                when (val matchVal = loadLibrary(me)) {
                    null -> {}
                    else -> {
                        val lib = matchVal
                        ret.push(lib)
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

// Safety: longPath should be null-terminated
public fun getPosixPath(longPath: List<UShort>): OsString? {
    val CCP_WIN_W_TO_POSIX: CUint = 3u
    // Doc: https://cygwin.com/cygwin-api/func-cygwin-conv-path.html
    // Src: https://github.com/cygwin/cygwin/blob/718a15ba50e0d01c79800bd658c2477f9a603540/winsup/cygwin/path.cc#L3902
    // Safety:
    // * `what` should be `CCP_WIN_W_TO_POSIX` here
    // * `from` is null-terminated UTF-16 path
    // * `to` is buffer, the buffer size is `size`.
    fun cygwinConvPath(what: CUint, from: ConstPtr, to: MutPtr, size: SizeT): SsizeT = 0L
    // If `size` is 0, returns needed buffer size, including null terminator;
    // or -1 if error.
    // Safety: if `size` is 0, `to` is not used.
    val nameLen = cygwinConvPath(CCP_WIN_W_TO_POSIX, longPath.asPtr(), MutPtr(0L), 0)
    // Expect at least 1 for null terminator.
    // It's not likely to return error here.
    if (nameLen < 1) {
        return null
    }
    val nameLen1 = nameLen.toInt()
    val nameBuffer = ArrayList(nameLen1)
    // Safety: `nameBuffer` is large enough.
    val res = cygwinConvPath(CCP_WIN_W_TO_POSIX, longPath.asPtr(), nameBuffer.asMutPtr(), nameLen1)
    // It's not likely to return error here.
    if (res != 0) {
        return null
    }
    // Remove the null terminator.
    nameBuffer.setLen(nameLen1 - 1)
    val name = OsString.fromVec(nameBuffer)
    return name
}

public fun loadLibrary(me: Moduleentry32w): Library? {
    val name = run {
        val pos = (me.szExePath.indexOfFirst({ i -> i == 0 }).takeIf { it >= 0 } ?: me.szExePath.size)
        OsString.fromWide(((me.szExePath).sliceArray(0 until pos)))
    }
    val name1 = (getPosixPath(me.szExePath) ?: return null)
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
    val mmap = (mmap(name1.asRef()) ?: return null)
    val imageBase = (getImageBase(mmap) ?: return null)
    val baseAddr = me.modBaseAddr.rawAddress.toInt()
    return Library(name = name1, bias = (baseAddr - (imageBase.toInt())), segments = mutableListOf(LibrarySegment(statedVirtualMemoryAddress = imageBase, len = me.modBaseSize.toInt())))
}
