@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.alloc.alloc

import io.github.kotlinmania.rustlang.core.alloc.*

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "allocRustAllocErrorHandler")
private external fun wasiAllocRustAllocErrorHandler(size: Long, align: Long): Unit

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    wasiAllocRustAllocErrorHandler(size.toLong(), align.toLong())
    error("C noreturn function allocRustAllocErrorHandler returned")
}
