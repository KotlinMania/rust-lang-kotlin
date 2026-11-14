@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object X86MovrsJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX86MovrsPrefetchrs(p: Long)
}

public actual fun prefetchrs(p: ConstPtr) {
    X86MovrsJniBridge.corearchX86MovrsPrefetchrs(p.rawAddress)
}
