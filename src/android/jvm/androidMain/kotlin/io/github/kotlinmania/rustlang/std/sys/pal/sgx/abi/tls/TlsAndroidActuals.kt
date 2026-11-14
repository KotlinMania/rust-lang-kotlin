@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object SysPalSgxAbiTlsJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysPalSgxAbiTlsGetTlsPtr(): Long

    @JvmStatic
    external fun stdSysPalSgxAbiTlsSetTlsPtr(tls: Long)
}

public actual fun getTlsPtr(): ConstPtr =
    ConstPtr(SysPalSgxAbiTlsJniBridge.stdSysPalSgxAbiTlsGetTlsPtr())

public actual fun setTlsPtr(tls: ConstPtr) {
    SysPalSgxAbiTlsJniBridge.stdSysPalSgxAbiTlsSetTlsPtr(tls.rawAddress)
}
