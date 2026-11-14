// port-lint: source symbolize/gimli/libs_aix.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsaix

import io.github.kotlinmania.libc.CChar
import io.github.kotlinmania.libc.fuchsia.ENOMEM
import io.github.kotlinmania.libc.loadquery
import io.github.kotlinmania.libc.unix.aix.L_GETINFO
import io.github.kotlinmania.libc.unix.aix.powerpc64.LdInfo
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Library
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.LibrarySegment
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.env
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.ffi.OsStr
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.xcoff.Xcoff
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CStr
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

public val EXE_IMAGE_BASE: ULong = 0x100000000uL

public expect fun errnoLocation(): MutPtr

public fun errno(): Int = (ObjectSlotLayout<CInt>()).readNative((errnoLocation()).rawAddress)

/**
 * On AIX, we use `loadquery` with `L_GETINFO` flag to query libraries mmapped.
 * See https://www.ibm.com/docs/en/aix/7.2?topic=l-loadquery-subroutine for
 * detailed information of `loadquery`.
 */
internal fun nativeLibraries(): List<Library> {
    val ret = mutableListOf<Library>()
    run {
        val buffer = mutableListOf(null, 64)
        while (true) {
            if (loadquery(L_GETINFO, buffer.asMutPtr().cast(), (mem.sizeOf<LdInfo>(typeLayoutT = ObjectSlotLayout<LdInfo>()) * buffer.size).toUInt()) != -1) {
                break
            } else {
                when (errno()) {
                    ENOMEM -> {
                        buffer.resize(buffer.size * 2, null)
                    }
                    else -> {
                        // If other error occurs, return empty libraries.
                        return mutableListOf()
                    }
                }
            }
        }
        var current = buffer.asMutPtr()
        while (true) {
            val textBase = current.ldinfoTextorg.toInt()
            val filenamePtr: ConstPtr = current.ldinfoFilename[0]
            val bytes = CStr.fromPtr(filenamePtr).toBytes()
            val memberNamePtr = ConstPtr((filenamePtr).rawAddress + (((bytes.len() + 1)).toLong() * (ObjectSlotLayout<CChar>()).strideBytes))
            var filename = OsStr.fromBytes(bytes).toOwned()
            if (textBase == EXE_IMAGE_BASE.toInt()) {
                when (val matchVal = env.currentExe()) {
                    is Ok -> {
                        val exe = matchVal.value
                        filename = exe.intoOsString()
                    }
                    else -> {}
                }
            }
            val bytes1 = CStr.fromPtr(memberNamePtr).toBytes()
            val memberName = OsStr.fromBytes(bytes1).toOwned()
            when (val matchVal1 = Xcoff.parseImage(filename.asRef(), memberName)) {
                null -> {}
                else -> {
                    val image = matchVal1
                    ret.add(Library(name = filename, memberName = memberName, segments = mutableListOf(LibrarySegment(statedVirtualMemoryAddress = image.base.toInt(), len = image.size)), bias = ((textBase + image.offset) - (image.base.toInt()))))
                }
            }
            if (current.ldinfoNext == 0) {
                break
            }
            current = current.cast().offset(current.ldinfoNext.toInt()).cast()
        }
    }
    return ret
}
