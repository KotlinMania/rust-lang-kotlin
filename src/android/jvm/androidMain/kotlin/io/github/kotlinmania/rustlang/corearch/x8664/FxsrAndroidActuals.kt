@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X8664FxsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX8664FxsrExternFxsave64(p: Long)

    @JvmStatic
    external fun corearchX8664FxsrExternFxrstor64(p: Long)
}

public actual fun externFxsave64(p: MutPtr) {
    X8664FxsrJniBridge.corearchX8664FxsrExternFxsave64(p.rawAddress)
}

public actual fun externFxrstor64(p: ConstPtr) {
    X8664FxsrJniBridge.corearchX8664FxsrExternFxrstor64(p.rawAddress)
}
