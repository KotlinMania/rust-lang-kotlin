@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind

import io.github.kotlinmania.rustlang.panicunwind.cinterop.rustlangPanicunwindRustDropPanic
import io.github.kotlinmania.rustlang.panicunwind.cinterop.rustlangPanicunwindRustForeignException
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rustDropPanic(): Nothing {
    rustlangPanicunwindRustDropPanic()
    error("Unreachable noreturn C function rustDropPanic")
}

public actual fun rustForeignException(): Nothing {
    rustlangPanicunwindRustForeignException()
    error("Unreachable noreturn C function rustForeignException")
}
