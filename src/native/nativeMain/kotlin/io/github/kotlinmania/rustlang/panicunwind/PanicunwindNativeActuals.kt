@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind

import io.github.kotlinmania.rustlang.panicunwind.cinterop.panicunwindRustDropPanic
import io.github.kotlinmania.rustlang.panicunwind.cinterop.panicunwindRustForeignException
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rustDropPanic(): Nothing {
    panicunwindRustDropPanic()
    error("Unreachable noreturn C function rustDropPanic")
}

public actual fun rustForeignException(): Nothing {
    panicunwindRustForeignException()
    error("Unreachable noreturn C function rustForeignException")
}
