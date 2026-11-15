@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object StdSysIoErrorUnixJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangStdSysIoErrorUnixGetLIBCERRNO(): Int

    @JvmStatic
    external fun rustlangStdSysIoErrorUnixSetLIBCERRNO(value: Int)

    @JvmStatic
    external fun rustlangStdSysIoErrorUnixErrnoLocation(): Long
}

public actual var LIBC_ERRNO: io.github.kotlinmania.rustlang.libc.CInt
    get() = StdSysIoErrorUnixJniBridge.rustlangStdSysIoErrorUnixGetLIBCERRNO()
    set(value) {
        StdSysIoErrorUnixJniBridge.rustlangStdSysIoErrorUnixSetLIBCERRNO(value)
    }

public actual fun errnoLocation(): MutPtr =
    MutPtr(StdSysIoErrorUnixJniBridge.rustlangStdSysIoErrorUnixErrnoLocation())
