@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "panicunwindDummyRustAbort")
private external fun wasiPanicunwindDummyRustAbort(): Unit

public actual fun rustAbort(): Nothing {
    wasiPanicunwindDummyRustAbort()
    error("C noreturn function panicunwindDummyRustAbort returned")
}
