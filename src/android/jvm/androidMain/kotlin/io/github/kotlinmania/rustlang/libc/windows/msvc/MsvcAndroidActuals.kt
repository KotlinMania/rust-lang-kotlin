@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows.msvc

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.windows.SizeT

private object LibcWindowsMsvcJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcWindowsMsvcStricmp(s1: Long, s2: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsMsvcStrnicmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsMsvcMemccpy(dest: Long, src: Long, c: Int, count: Long): Long
}

public actual fun stricmp(s1: ConstPtr, s2: ConstPtr): CInt =
    LibcWindowsMsvcJniBridge.rustlangLibcWindowsMsvcStricmp(s1.rawAddress, s2.rawAddress)

public actual fun strnicmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    LibcWindowsMsvcJniBridge.rustlangLibcWindowsMsvcStrnicmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, count: SizeT): MutPtr =
    MutPtr(LibcWindowsMsvcJniBridge.rustlangLibcWindowsMsvcMemccpy(dest.rawAddress, src.rawAddress, c, count.toLong()))
