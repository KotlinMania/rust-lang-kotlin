@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "stdSysExitExit")
private external fun wasiStdSysExitExit(status: Int): Unit

public actual fun exit(status: CInt): Nothing {
    wasiStdSysExitExit(status)
    error("C noreturn function stdSysExitExit returned")
}
