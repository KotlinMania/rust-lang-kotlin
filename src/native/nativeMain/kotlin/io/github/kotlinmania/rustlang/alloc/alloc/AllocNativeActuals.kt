@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.alloc

import io.github.kotlinmania.rustlang.alloc.alloc.cinterop.allocRustAllocErrorHandler
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rustAllocErrorHandler(size: ULong, align: ULong): Nothing {
    allocRustAllocErrorHandler(size, align)
    error("Unreachable noreturn C function rustAllocErrorHandler")
}
