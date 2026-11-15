@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.rustlangCorearchWasm32MemoryLlvmMemoryGrow
import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.rustlangCorearchWasm32MemoryLlvmMemorySize
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong =
    rustlangCorearchWasm32MemoryLlvmMemoryGrow(mem, pages)

public actual fun llvmMemorySize(mem: UInt): ULong =
    rustlangCorearchWasm32MemoryLlvmMemorySize(mem)
