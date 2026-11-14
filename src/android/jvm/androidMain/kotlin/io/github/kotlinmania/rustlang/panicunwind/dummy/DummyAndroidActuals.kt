@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

private object DummyJniBridge {
    init {
        System.loadLibrary("panicunwind_jni")
    }

    @JvmStatic
    external fun panicunwindDummyRustAbort()
}

public actual fun rustAbort(): Nothing {
    DummyJniBridge.panicunwindDummyRustAbort()
    error("Unreachable noreturn C function rustAbort")
}
