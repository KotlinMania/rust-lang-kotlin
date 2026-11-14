@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchWasm32MemoryLlvmMemoryGrow")
private external fun wasiCorearchWasm32MemoryLlvmMemoryGrow(mem: Int, pages: Long): Long

public actual fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong =
    wasiCorearchWasm32MemoryLlvmMemoryGrow(mem.toInt(), pages.toLong()).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchWasm32MemoryLlvmMemorySize")
private external fun wasiCorearchWasm32MemoryLlvmMemorySize(mem: Int): Long

public actual fun llvmMemorySize(mem: UInt): ULong =
    wasiCorearchWasm32MemoryLlvmMemorySize(mem.toInt()).toULong()
