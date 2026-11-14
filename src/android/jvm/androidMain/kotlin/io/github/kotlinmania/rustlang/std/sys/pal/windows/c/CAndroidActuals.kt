@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Ntstatus

private object SysPalWindowsCJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysPalWindowsCProcessPrng(pbdata: Long, cbdata: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCNtCreateNamedPipeFile(filehandle: Long, desiredaccess: Int, objectattributes: Long, iostatusblock: Long, shareaccess: Int, createdisposition: Int, createoptions: Int, namedpipetype: Int, readmode: Int, completionmode: Int, maximuminstances: Int, inboundquota: Int, outboundquota: Int, defaulttimeout: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCAtexit(cb: Long): Int
}

public actual fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool =
    SysPalWindowsCJniBridge.stdSysPalWindowsCProcessPrng(pbdata.rawAddress, cbdata.toLong())

public actual fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus =
    SysPalWindowsCJniBridge.stdSysPalWindowsCNtCreateNamedPipeFile(filehandle.rawAddress, desiredaccess.toInt(), objectattributes.rawAddress, iostatusblock.rawAddress, shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), namedpipetype.toInt(), readmode.toInt(), completionmode.toInt(), maximuminstances.toInt(), inboundquota.toInt(), outboundquota.toInt(), defaulttimeout.rawAddress)

public actual fun atexit(cb: AtexitCbCallback): CInt =
    SysPalWindowsCJniBridge.stdSysPalWindowsCAtexit(0L)
