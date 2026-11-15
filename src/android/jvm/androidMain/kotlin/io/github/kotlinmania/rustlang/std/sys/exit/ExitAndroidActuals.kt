@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

private object StdSysExitJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangStdSysExitExit(status: Int)
}

public actual fun exit(status: CInt): Nothing {
    StdSysExitJniBridge.rustlangStdSysExitExit(status)
    error("Unreachable noreturn C function exit")
}
