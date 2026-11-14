@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.panicunwind

// The host must provide the C companion wrapper exports in import module panicunwind.
@kotlin.wasm.WasmImport("panicunwind", "panicunwindRustDropPanic")
private external fun wasiPanicunwindRustDropPanic(): Unit

public actual fun rustDropPanic(): Nothing {
    wasiPanicunwindRustDropPanic()
    error("C noreturn function panicunwindRustDropPanic returned")
}

@kotlin.wasm.WasmImport("panicunwind", "panicunwindRustForeignException")
private external fun wasiPanicunwindRustForeignException(): Unit

public actual fun rustForeignException(): Nothing {
    wasiPanicunwindRustForeignException()
    error("C noreturn function panicunwindRustForeignException returned")
}
