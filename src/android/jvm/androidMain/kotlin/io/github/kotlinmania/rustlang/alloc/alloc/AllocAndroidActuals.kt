@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.alloc

import io.github.kotlinmania.rustlang.core.alloc.*

private object AllocJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun allocRustAllocErrorHandler(size: Long, align: Long)
}

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    AllocJniBridge.allocRustAllocErrorHandler(size.toLong(), align.toLong())
    error("Unreachable noreturn C function rustAllocErrorHandler")
}
