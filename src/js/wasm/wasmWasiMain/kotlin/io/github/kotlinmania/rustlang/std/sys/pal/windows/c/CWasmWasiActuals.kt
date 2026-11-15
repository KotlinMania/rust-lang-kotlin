@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.os.raw.CInt
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.Ntstatus

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCProcessPrng")

public actual fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCNtCreateNamedPipeFile")

public actual fun atexit(cb: AtexitCbCallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCAtexit")
