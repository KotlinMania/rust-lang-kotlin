@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind

import io.github.kotlinmania.rustlang.panicunwind.cinterop.panicunwindRustDropPanic
import io.github.kotlinmania.rustlang.panicunwind.cinterop.panicunwindRustForeignException

public actual fun rustDropPanic(): Nothing {
    panicunwindRustDropPanic()
    error("Unreachable noreturn C function rustDropPanic")
}

public actual fun rustForeignException(): Nothing {
    panicunwindRustForeignException()
    error("Unreachable noreturn C function rustForeignException")
}
