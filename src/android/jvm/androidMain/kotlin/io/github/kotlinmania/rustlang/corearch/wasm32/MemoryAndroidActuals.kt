@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

private object CorearchWasm32MemoryJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchWasm32MemoryLlvmMemoryGrow(mem: Int, pages: Long): Long

    @JvmStatic
    external fun rustlangCorearchWasm32MemoryLlvmMemorySize(mem: Int): Long
}

public actual fun llvmMemoryGrow(mem: UInt, pages: ULong): ULong =
    CorearchWasm32MemoryJniBridge.rustlangCorearchWasm32MemoryLlvmMemoryGrow(mem.toInt(), pages.toLong()).toULong()

public actual fun llvmMemorySize(mem: UInt): ULong =
    CorearchWasm32MemoryJniBridge.rustlangCorearchWasm32MemoryLlvmMemorySize(mem.toInt()).toULong()
