@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object X86ClflushoptJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX86ClflushoptClflushopt(p: Long)
}

public actual fun clflushopt(p: ConstPtr) {
    X86ClflushoptJniBridge.corearchX86ClflushoptClflushopt(p.rawAddress)
}
