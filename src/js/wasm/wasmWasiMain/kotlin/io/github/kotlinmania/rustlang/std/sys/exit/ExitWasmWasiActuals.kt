@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysExitExit")
private external fun wasiRustlangStdSysExitExit(status: Int): Unit

public actual fun exit(status: CInt): Nothing {
    wasiRustlangStdSysExitExit(status)
    error("C noreturn function rustlangStdSysExitExit returned")
}
