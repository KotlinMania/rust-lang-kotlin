@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.CInt

private object SysIoErrorUnixJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysIoErrorUnixGetLIBCERRNO(): Int

    @JvmStatic
    external fun stdSysIoErrorUnixSetLIBCERRNO(value: Int)

    @JvmStatic
    external fun stdSysIoErrorUnixErrnoLocation(): Long
}

public actual var LIBC_ERRNO: CInt
    get() = SysIoErrorUnixJniBridge.stdSysIoErrorUnixGetLIBCERRNO()
    set(value) {
        SysIoErrorUnixJniBridge.stdSysIoErrorUnixSetLIBCERRNO(value)
    }

public actual fun errnoLocation(): MutPtr =
    MutPtr(SysIoErrorUnixJniBridge.stdSysIoErrorUnixErrnoLocation())
