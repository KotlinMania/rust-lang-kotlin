@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsaix

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SymbolizeGimliLibsaixLibsAixJniBridge {
    init {
        System.loadLibrary("backtrace_jni")
    }

    @JvmStatic
    external fun backtraceSymbolizeGimliLibsaixLibsAixErrnoLocation(): Long
}

public actual fun errnoLocation(): MutPtr =
    MutPtr(SymbolizeGimliLibsaixLibsAixJniBridge.backtraceSymbolizeGimliLibsaixLibsAixErrnoLocation())
