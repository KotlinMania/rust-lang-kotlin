@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X8664FxsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX8664FxsrFxsave64(p: Long)

    @JvmStatic
    external fun corearchX8664FxsrFxrstor64(p: Long)
}

public actual fun fxsave64(p: MutPtr) {
    X8664FxsrJniBridge.corearchX8664FxsrFxsave64(p.rawAddress)
}

public actual fun fxrstor64(p: ConstPtr) {
    X8664FxsrJniBridge.corearchX8664FxsrFxrstor64(p.rawAddress)
}
