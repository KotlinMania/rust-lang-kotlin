@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X86FxsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86FxsrFxsave(p: Long)

    @JvmStatic
    external fun corearchX86FxsrFxrstor(p: Long)
}

public actual fun fxsave(p: MutPtr) {
    X86FxsrJniBridge.corearchX86FxsrFxsave(p.rawAddress)
}

public actual fun fxrstor(p: ConstPtr) {
    X86FxsrJniBridge.corearchX86FxsrFxrstor(p.rawAddress)
}
