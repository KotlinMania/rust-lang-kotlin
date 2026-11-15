@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object CorearchX86ClflushoptJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86ClflushoptClflushopt(p: Long)
}

public actual fun clflushopt(p: ConstPtr) {
    CorearchX86ClflushoptJniBridge.rustlangCorearchX86ClflushoptClflushopt(p.rawAddress)
}
