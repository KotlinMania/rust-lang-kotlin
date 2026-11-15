@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.signal

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object LibcNewGlibcSignalJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewGlibcSignalSigaction(signum: Int, act: Long, oldact: Long): Int
}

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    LibcNewGlibcSignalJniBridge.rustlangLibcNewGlibcSignalSigaction(signum, act.rawAddress, oldact.rawAddress)
