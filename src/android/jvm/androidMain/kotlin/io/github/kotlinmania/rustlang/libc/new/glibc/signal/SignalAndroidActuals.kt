@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.signal

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object NewGlibcSignalJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewGlibcSignalSigaction(signum: Int, act: Long, oldact: Long): Int
}

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    NewGlibcSignalJniBridge.libcNewGlibcSignalSigaction(signum, act.rawAddress, oldact.rawAddress)
