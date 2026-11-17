@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X86FxsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86FxsrExternFxsave(p: Long)

    @JvmStatic
    external fun corearchX86FxsrExternFxrstor(p: Long)
}

public actual fun externFxsave(p: MutPtr) {
    X86FxsrJniBridge.corearchX86FxsrExternFxsave(p.rawAddress)
}

public actual fun externFxrstor(p: ConstPtr) {
    X86FxsrJniBridge.corearchX86FxsrExternFxrstor(p.rawAddress)
}
