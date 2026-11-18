@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

private object SysExitJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun stdSysExitExit(status: Int)
}

public actual fun exit(status: io.github.kotlinmania.rustlang.core.ffi.primitives.CInt): Nothing {
    SysExitJniBridge.stdSysExitExit(status)
    error("Unreachable noreturn C function exit")
}
