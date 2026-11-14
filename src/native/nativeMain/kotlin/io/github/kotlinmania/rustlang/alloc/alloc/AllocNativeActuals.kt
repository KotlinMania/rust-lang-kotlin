@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.alloc

import io.github.kotlinmania.rustlang.alloc.alloc.cinterop.allocRustAllocErrorHandler

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    allocRustAllocErrorHandler(size, align)
    error("Unreachable noreturn C function rustAllocErrorHandler")
}
