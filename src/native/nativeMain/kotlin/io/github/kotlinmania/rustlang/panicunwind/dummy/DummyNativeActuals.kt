@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

import io.github.kotlinmania.rustlang.panicunwind.dummy.cinterop.rustlangDummyRustAbort
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rustAbort(): Nothing {
    rustlangDummyRustAbort()
    error("Unreachable noreturn C function rustAbort")
}
