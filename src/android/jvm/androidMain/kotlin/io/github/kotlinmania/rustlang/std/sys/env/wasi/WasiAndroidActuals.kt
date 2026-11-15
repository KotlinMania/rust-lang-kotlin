@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.env.wasi

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object StdSysEnvWasiJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangStdSysEnvWasiWasilibcGetEnviron(): Long
}

public actual fun wasilibcGetEnviron(): MutPtr =
    MutPtr(StdSysEnvWasiJniBridge.rustlangStdSysEnvWasiWasilibcGetEnviron())
