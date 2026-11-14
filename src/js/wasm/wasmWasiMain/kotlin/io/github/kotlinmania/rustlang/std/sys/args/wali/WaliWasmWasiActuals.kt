@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.args.wali.imp

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.raw.CUint as WaliArgIdx

// The host must provide the C companion wrapper exports in import module std.
public actual fun clGetArgc(): WaliArgIdx =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysArgsWaliImpWaliClGetArgc")

public actual fun clGetArgvLen(offset: WaliArgIdx): CUint =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysArgsWaliImpWaliClGetArgvLen")

public actual fun clCopyArgv(buf: MutPtr, offset: WaliArgIdx): CUint =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysArgsWaliImpWaliClCopyArgv")
