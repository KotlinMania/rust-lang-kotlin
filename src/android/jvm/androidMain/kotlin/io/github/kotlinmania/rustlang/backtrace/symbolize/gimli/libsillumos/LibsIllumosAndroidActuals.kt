@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsillumos

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SymbolizeGimliLibsillumosLibsIllumosJniBridge {
    init {
        System.loadLibrary("backtrace_jni")
    }

    @JvmStatic
    external fun backtraceSymbolizeGimliLibsillumosLibsIllumosDlinfo(handle: Long, request: Int, p: Long): Int
}

public actual fun dlinfo(handle: ConstPtr, request: CInt, p: MutPtr): CInt =
    SymbolizeGimliLibsillumosLibsIllumosJniBridge.backtraceSymbolizeGimliLibsillumosLibsIllumosDlinfo(handle.rawAddress, request, p.rawAddress)
