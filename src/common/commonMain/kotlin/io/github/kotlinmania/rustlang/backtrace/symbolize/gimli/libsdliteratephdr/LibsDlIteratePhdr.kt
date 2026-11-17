// port-lint: source symbolize/gimli/libs_dl_iterate_phdr.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsdliteratephdr

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.extractZipPathAndroid
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.Env
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsString
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.dlIteratePhdr
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.DlPhdrInfo

public open class CallbackData(
    public var libs: List<Library>,
    public var maps: List<parserunningmmaps.MapsEntry>?,
)

public fun nativeLibraries(): List<Library> {
    val cbData = CallbackData(libs = mutableListOf(), maps = parserunningmmaps.parseMaps().ok())
    run {
        dlIteratePhdr(::callback, addrOfMut(cbData).cast())
    }
    return cbData.libs
}

public fun inferCurrentExe(maps: List<parserunningmmaps.MapsEntry>?, baseAddr: Int): OsString {
    if (maps != null) {
        val entries = maps
        val optPath = (entries.indexOf({ e -> e.ipMatches(baseAddr) && e.pathname().len() > 0 }).takeIf { it >= 0 })?.let({ e -> e.pathname() })
        if (optPath != null) {
            val path = optPath
            return path
        }
    }
    return Env.currentExe().map({ e -> e.into() }).unwrapOrDefault()
}

/**
 * # Safety
 * `info` must be a valid pointer.
 * `data` must be a valid pointer to `CallbackData`.
 */
public fun callback(info: MutPtr, size: SizeT, data: MutPtr): CInt {
    // SAFETY: We are guaranteed these fields:
    val dlpiAddr = (UnresolvedTypeLayout<DlPhdrInfo>()).readNative((info).rawAddress).dlpiAddr
    val dlpiName = (UnresolvedTypeLayout<DlPhdrInfo>()).readNative((info).rawAddress).dlpiName
    val dlpiPhdr = (UnresolvedTypeLayout<DlPhdrInfo>()).readNative((info).rawAddress).dlpiPhdr
    val dlpiPhnum = (UnresolvedTypeLayout<DlPhdrInfo>()).readNative((info).rawAddress).dlpiPhnum
    // SAFETY: We assured this.
    val structVal = RustMutableReference(MutPtr((MutPtr((data).rawAddress)).rawAddress), UnresolvedTypeLayout<CallbackData>())
    val libs = structVal.libs
    val maps = structVal.maps
    // most implementations give us the main program first
    val isMain = libs.isEmpty()
    // we may be statically linked, which means we are main and mostly one big blob of code
    val isStatic = dlpiAddr == 0
    // sometimes we get a null or 0-len CStr, based on libc's whims, but these mean the same thing
    val noGivenName = dlpiName.isNull() || dlpiName == 0
    val name =
        if (isStatic) {
            // don't try to look up our name from /proc/self/maps, it'll get silly
            Env.currentExe().unwrapOrDefault().intoOsString()
        } else if (isMain && noGivenName) {
            inferCurrentExe(maps, dlpiAddr.toInt())
        } else {
            // this fallback works even if we are main, because some platforms give the name anyways
            if (dlpiName.isNull()) {
                OsString.new()
            } else {
                // SAFETY: we just checked for nullness
                OsStr.fromBytes(CStr.fromPtr(dlpiName).toBytes()).toOwned()
            }
        }
    val zipOffset: ULong? = maps?.let({ maps1 ->
        // check if file is embedded within a ZIP archive by searching for `! /`
        extractZipPathAndroid(name)?.let({
            // find MapsEntry matching library's base address and get its file offset
            (maps1.iter().indexOf({ m -> m.ipMatches(dlpiAddr.toInt()) }).takeIf { it >= 0 })?.let({ m -> m.offset() })
        })
    })
    val headers =
        if (dlpiPhdr.isNull() || dlpiPhnum == 0) {
            listOf()
        } else {
            // SAFETY: We just checked for nullness or 0-len slices
            Slice.fromRawParts(dlpiPhdr, dlpiPhnum.toInt())
        }
    libs.add(Library(name = name, zipOffset = zipOffset, segments = headers.iter().map({ header -> LibrarySegment(len = header.pMemsz.toInt(), statedVirtualMemoryAddress = header.pVaddr.toInt()) }).collect(), bias = dlpiAddr.toInt()))
    return 0
}
