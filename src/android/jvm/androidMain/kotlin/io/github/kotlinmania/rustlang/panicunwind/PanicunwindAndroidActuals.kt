@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind

private object PanicunwindJniBridge {
    init {
        System.loadLibrary("panicunwind_jni")
    }

    @JvmStatic
    external fun panicunwindRustDropPanic()

    @JvmStatic
    external fun panicunwindRustForeignException()
}

public actual fun rustDropPanic(): Nothing {
    PanicunwindJniBridge.panicunwindRustDropPanic()
    error("Unreachable noreturn C function rustDropPanic")
}

public actual fun rustForeignException(): Nothing {
    PanicunwindJniBridge.panicunwindRustForeignException()
    error("Unreachable noreturn C function rustForeignException")
}
