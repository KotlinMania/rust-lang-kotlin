@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.cinterop.stdSysPalWindowsCAtexit
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.cinterop.stdSysPalWindowsCNtCreateNamedPipeFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.cinterop.stdSysPalWindowsCProcessPrng
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Ntstatus
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool =
    stdSysPalWindowsCProcessPrng(pbdata.rawAddress.toCPointer<ByteVar>(), cbdata)

public actual fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus =
    stdSysPalWindowsCNtCreateNamedPipeFile(filehandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, objectattributes.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), shareaccess, createdisposition, createoptions, namedpipetype, readmode, completionmode, maximuminstances, inboundquota, outboundquota, defaulttimeout.rawAddress.toCPointer<ByteVar>())

public actual fun atexit(cb: AtexitCbCallback): CInt =
    stdSysPalWindowsCAtexit(0L.toCPointer<ByteVar>())
