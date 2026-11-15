@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.panicunwind.seh

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangSehGetTYPEINFOVTABLE")
private external fun wasiRustlangSehGetTYPEINFOVTABLE(): Int

public actual val TYPE_INFO_VTABLE: UByte
    get() = wasiRustlangSehGetTYPEINFOVTABLE().toUByte()
