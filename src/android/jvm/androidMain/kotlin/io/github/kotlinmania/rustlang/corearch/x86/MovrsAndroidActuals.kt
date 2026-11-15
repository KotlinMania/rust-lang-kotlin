@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object CorearchX86MovrsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86MovrsPrefetchrs(p: Long)
}

public actual fun prefetchrs(p: ConstPtr) {
    CorearchX86MovrsJniBridge.rustlangCorearchX86MovrsPrefetchrs(p.rawAddress)
}
