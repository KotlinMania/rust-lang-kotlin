@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

import io.github.kotlinmania.rustlang.panicunwind.dummy.cinterop.panicunwindDummyRustAbort

public actual fun rustAbort(): Nothing {
    panicunwindDummyRustAbort()
    error("Unreachable noreturn C function rustAbort")
}
