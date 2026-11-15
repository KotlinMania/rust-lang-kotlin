@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchWasm32MemoryLlvmMemoryGrow")
private external fun wasiRustlangCorearchWasm32MemoryLlvmMemoryGrow(mem: Int, pages: Long): Long

public actual fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong =
    wasiRustlangCorearchWasm32MemoryLlvmMemoryGrow(mem.toInt(), pages.toLong()).toULong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchWasm32MemoryLlvmMemorySize")
private external fun wasiRustlangCorearchWasm32MemoryLlvmMemorySize(mem: Int): Long

public actual fun llvmMemorySize(mem: UInt): ULong =
    wasiRustlangCorearchWasm32MemoryLlvmMemorySize(mem.toInt()).toULong()
