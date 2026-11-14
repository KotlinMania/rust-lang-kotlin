@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows.gnu

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.WcharT
import io.github.kotlinmania.rustlang.libc.windows.SizeT

private object WindowsGnuJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcWindowsGnuStrcasecmp(s1: Long, s2: Long): Int

    @JvmStatic
    external fun libcWindowsGnuStrncasecmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun libcWindowsGnuWmemchr(cx: Long, c: Int, n: Long): Long
}

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    WindowsGnuJniBridge.libcWindowsGnuStrcasecmp(s1.rawAddress, s2.rawAddress)

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    WindowsGnuJniBridge.libcWindowsGnuStrncasecmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    MutPtr(WindowsGnuJniBridge.libcWindowsGnuWmemchr(cx.rawAddress, c, n.toLong()))
