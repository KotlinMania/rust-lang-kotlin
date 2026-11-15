@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysIoErrorUnixGetLIBCERRNO")
private external fun wasiRustlangStdSysIoErrorUnixGetLIBCERRNO(): Int

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysIoErrorUnixSetLIBCERRNO")
private external fun wasiRustlangStdSysIoErrorUnixSetLIBCERRNO(value: Int): Unit

public actual var LIBC_ERRNO: io.github.kotlinmania.rustlang.libc.CInt
    get() = wasiRustlangStdSysIoErrorUnixGetLIBCERRNO().toInt()
    set(value) {
        wasiRustlangStdSysIoErrorUnixSetLIBCERRNO(value)
    }

public actual fun errnoLocation(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysIoErrorUnixErrnoLocation")
