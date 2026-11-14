@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.std.ffi.CInt

private object SysExitJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysExitExit(status: Int)
}

public actual fun exit(status: CInt): Nothing {
    SysExitJniBridge.stdSysExitExit(status)
    error("Unreachable noreturn C function exit")
}
