@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.miri

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object MiriJniBridge {
    init {
        System.loadLibrary("panicunwind_jni")
    }

    @JvmStatic
    external fun panicunwindMiriMiriStartUnwind(payload: Long)
}

public actual fun miriStartUnwind(payload: MutPtr): Nothing {
    MiriJniBridge.panicunwindMiriMiriStartUnwind(payload.rawAddress)
    error("Unreachable noreturn C function miriStartUnwind")
}
