@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object CorearchX86FxsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86FxsrFxsave(p: Long)

    @JvmStatic
    external fun rustlangCorearchX86FxsrFxrstor(p: Long)
}

public actual fun fxsave(p: MutPtr) {
    CorearchX86FxsrJniBridge.rustlangCorearchX86FxsrFxsave(p.rawAddress)
}

public actual fun fxrstor(p: ConstPtr) {
    CorearchX86FxsrJniBridge.rustlangCorearchX86FxsrFxrstor(p.rawAddress)
}
