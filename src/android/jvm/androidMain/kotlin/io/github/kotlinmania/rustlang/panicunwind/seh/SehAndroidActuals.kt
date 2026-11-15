@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.seh

private object SehJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangSehGetTYPEINFOVTABLE(): Byte
}

public actual val TYPE_INFO_VTABLE: UByte
    get() = SehJniBridge.rustlangSehGetTYPEINFOVTABLE().toUByte()
