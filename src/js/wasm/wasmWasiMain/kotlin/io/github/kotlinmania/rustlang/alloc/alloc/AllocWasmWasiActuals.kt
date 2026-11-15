@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.alloc.alloc

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangAllocRustAllocErrorHandler")
private external fun wasiRustlangAllocRustAllocErrorHandler(size: Long, align: Long): Unit

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    wasiRustlangAllocRustAllocErrorHandler(size.toLong(), align.toLong())
    error("C noreturn function rustlangAllocRustAllocErrorHandler returned")
}
