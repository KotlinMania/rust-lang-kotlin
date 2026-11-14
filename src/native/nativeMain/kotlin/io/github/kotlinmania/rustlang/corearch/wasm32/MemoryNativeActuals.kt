@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.corearchWasm32MemoryLlvmMemoryGrow
import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.corearchWasm32MemoryLlvmMemorySize

public actual fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong =
    corearchWasm32MemoryLlvmMemoryGrow(mem, pages)

public actual fun llvmMemorySize(mem: UInt): ULong =
    corearchWasm32MemoryLlvmMemorySize(mem)
