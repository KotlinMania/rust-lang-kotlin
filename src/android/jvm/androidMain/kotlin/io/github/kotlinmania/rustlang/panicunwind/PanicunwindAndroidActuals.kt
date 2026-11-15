@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind

private object PanicunwindJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangPanicunwindRustDropPanic()

    @JvmStatic
    external fun rustlangPanicunwindRustForeignException()
}

public actual fun rustDropPanic(): Nothing {
    PanicunwindJniBridge.rustlangPanicunwindRustDropPanic()
    error("Unreachable noreturn C function rustDropPanic")
}

public actual fun rustForeignException(): Nothing {
    PanicunwindJniBridge.rustlangPanicunwindRustForeignException()
    error("Unreachable noreturn C function rustForeignException")
}
