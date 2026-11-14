@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.std.ffi.CInt

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysExitExit")
private external fun wasiStdSysExitExit(status: Int): Unit

public actual fun exit(status: CInt): Nothing {
    wasiStdSysExitExit(status)
    error("C noreturn function stdSysExitExit returned")
}
