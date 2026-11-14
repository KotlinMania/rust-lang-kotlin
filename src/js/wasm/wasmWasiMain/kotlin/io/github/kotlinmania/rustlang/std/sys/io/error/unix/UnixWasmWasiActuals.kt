@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.CInt

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysIoErrorUnixGetLIBCERRNO")
private external fun wasiStdSysIoErrorUnixGetLIBCERRNO(): Int

@kotlin.wasm.WasmImport("std", "stdSysIoErrorUnixSetLIBCERRNO")
private external fun wasiStdSysIoErrorUnixSetLIBCERRNO(value: Int): Unit

public actual var LIBC_ERRNO: CInt
    get() = wasiStdSysIoErrorUnixGetLIBCERRNO().toInt()
    set(value) {
        wasiStdSysIoErrorUnixSetLIBCERRNO(value)
    }

public actual fun errnoLocation(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysIoErrorUnixErrnoLocation")
