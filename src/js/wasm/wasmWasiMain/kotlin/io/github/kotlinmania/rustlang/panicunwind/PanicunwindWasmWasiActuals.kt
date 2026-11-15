@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.panicunwind

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangPanicunwindRustDropPanic")
private external fun wasiRustlangPanicunwindRustDropPanic(): Unit

public actual fun rustDropPanic(): Nothing {
    wasiRustlangPanicunwindRustDropPanic()
    error("C noreturn function rustlangPanicunwindRustDropPanic returned")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangPanicunwindRustForeignException")
private external fun wasiRustlangPanicunwindRustForeignException(): Unit

public actual fun rustForeignException(): Nothing {
    wasiRustlangPanicunwindRustForeignException()
    error("C noreturn function rustlangPanicunwindRustForeignException returned")
}
