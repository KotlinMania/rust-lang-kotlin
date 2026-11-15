@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangDummyRustAbort")
private external fun wasiRustlangDummyRustAbort(): Unit

public actual fun rustAbort(): Nothing {
    wasiRustlangDummyRustAbort()
    error("C noreturn function rustlangDummyRustAbort returned")
}
