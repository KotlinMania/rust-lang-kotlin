@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.os.raw.CInt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.cinterop.rustlangStdSysPalWindowsCAtexit
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.cinterop.rustlangStdSysPalWindowsCNtCreateNamedPipeFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.cinterop.rustlangStdSysPalWindowsCProcessPrng
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Ntstatus
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool =
    rustlangStdSysPalWindowsCProcessPrng(pbdata.rawAddress.toCPointer<ByteVar>(), cbdata)

public actual fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus =
    rustlangStdSysPalWindowsCNtCreateNamedPipeFile(filehandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, objectattributes.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), shareaccess, createdisposition, createoptions, namedpipetype, readmode, completionmode, maximuminstances, inboundquota, outboundquota, defaulttimeout.rawAddress.toCPointer<ByteVar>())

public actual fun atexit(cb: AtexitCbCallback): CInt =
    rustlangStdSysPalWindowsCAtexit(0L.toCPointer<ByteVar>())
