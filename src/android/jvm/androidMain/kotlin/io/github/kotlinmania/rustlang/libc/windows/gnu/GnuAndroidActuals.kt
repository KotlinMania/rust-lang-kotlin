@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows.gnu

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.windows.SizeT
import io.github.kotlinmania.rustlang.windows.WcharT

private object LibcWindowsGnuJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcWindowsGnuStrcasecmp(s1: Long, s2: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGnuStrncasecmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun rustlangLibcWindowsGnuWmemchr(cx: Long, c: Short, n: Long): Long
}

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    LibcWindowsGnuJniBridge.rustlangLibcWindowsGnuStrcasecmp(s1.rawAddress, s2.rawAddress)

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    LibcWindowsGnuJniBridge.rustlangLibcWindowsGnuStrncasecmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    MutPtr(LibcWindowsGnuJniBridge.rustlangLibcWindowsGnuWmemchr(cx.rawAddress, c.toShort(), n.toLong()))
