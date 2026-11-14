@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SysPalSgxAbiPanicJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysPalSgxAbiPanicGetDEBUG(): Byte

    @JvmStatic
    external fun stdSysPalSgxAbiPanicTakeDebugPanicBufPtr(): Long
}

public actual val DEBUG: UByte
    get() = SysPalSgxAbiPanicJniBridge.stdSysPalSgxAbiPanicGetDEBUG().toUByte()

public actual fun takeDebugPanicBufPtr(): MutPtr =
    MutPtr(SysPalSgxAbiPanicJniBridge.stdSysPalSgxAbiPanicTakeDebugPanicBufPtr())
