@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.panicunwind.seh

// The host must provide the C companion wrapper exports in import module panicunwind.
@kotlin.wasm.WasmImport("panicunwind", "panicunwindSehGetTYPEINFOVTABLE")
private external fun wasiPanicunwindSehGetTYPEINFOVTABLE(): Int

public actual val TYPE_INFO_VTABLE: UByte
    get() = wasiPanicunwindSehGetTYPEINFOVTABLE().toUByte()
