@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

import io.github.kotlinmania.rustlang.panicunwind.dummy.cinterop.panicunwindDummyRustAbort
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rustAbort(): Nothing {
    panicunwindDummyRustAbort()
    error("Unreachable noreturn C function rustAbort")
}
