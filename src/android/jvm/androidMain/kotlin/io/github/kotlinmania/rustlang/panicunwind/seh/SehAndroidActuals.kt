@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.seh

private object SehJniBridge {
    init {
        System.loadLibrary("panicunwind_jni")
    }

    @JvmStatic
    external fun panicunwindSehGetTYPEINFOVTABLE(): Byte
}

public actual val TYPE_INFO_VTABLE: UByte
    get() = SehJniBridge.panicunwindSehGetTYPEINFOVTABLE().toUByte()
