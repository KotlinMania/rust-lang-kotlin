@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object CorearchX8664FxsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664FxsrFxsave64(p: Long)

    @JvmStatic
    external fun rustlangCorearchX8664FxsrFxrstor64(p: Long)
}

public actual fun fxsave64(p: MutPtr) {
    CorearchX8664FxsrJniBridge.rustlangCorearchX8664FxsrFxsave64(p.rawAddress)
}

public actual fun fxrstor64(p: ConstPtr) {
    CorearchX8664FxsrJniBridge.rustlangCorearchX8664FxsrFxrstor64(p.rawAddress)
}
