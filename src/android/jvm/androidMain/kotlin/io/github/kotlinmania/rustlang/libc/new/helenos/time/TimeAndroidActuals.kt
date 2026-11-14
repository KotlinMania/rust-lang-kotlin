@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.time

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object NewHelenosTimeJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosTimeGetuptime(tp: Long)
}

public actual fun getuptime(tp: MutPtr) {
    NewHelenosTimeJniBridge.libcNewHelenosTimeGetuptime(tp.rawAddress)
}
