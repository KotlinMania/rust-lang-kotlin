@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

private object Wasm32MemoryJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchWasm32MemoryLlvmMemoryGrow(mem: Int, pages: Long): Long

    @JvmStatic
    external fun corearchWasm32MemoryLlvmMemorySize(mem: Int): Long
}

public actual fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong =
    Wasm32MemoryJniBridge.corearchWasm32MemoryLlvmMemoryGrow(mem.toInt(), pages.toLong()).toULong()

public actual fun llvmMemorySize(mem: UInt): ULong =
    Wasm32MemoryJniBridge.corearchWasm32MemoryLlvmMemorySize(mem.toInt()).toULong()
