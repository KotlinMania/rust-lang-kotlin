@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.dummy

private object DummyJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangDummyRustAbort()
}

public actual fun rustAbort(): Nothing {
    DummyJniBridge.rustlangDummyRustAbort()
    error("Unreachable noreturn C function rustAbort")
}
