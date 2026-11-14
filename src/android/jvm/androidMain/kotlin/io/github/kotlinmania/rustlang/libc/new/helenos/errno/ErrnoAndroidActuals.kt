@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.errno

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object NewHelenosErrnoJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosErrnoErrno(): Long
}

public actual fun errno(): MutPtr =
    MutPtr(NewHelenosErrnoJniBridge.libcNewHelenosErrnoErrno())
