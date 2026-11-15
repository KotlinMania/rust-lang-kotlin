@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object CoreFfiJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCoreFfiMemcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangCoreFfiMemmove(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun rustlangCoreFfiMemset(s: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun rustlangCoreFfiMemcmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun rustlangCoreFfiBcmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun rustlangCoreFfiStrlen(s: Long): Long
}

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(CoreFfiJniBridge.rustlangCoreFfiMemcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(CoreFfiJniBridge.rustlangCoreFfiMemmove(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    MutPtr(CoreFfiJniBridge.rustlangCoreFfiMemset(s.rawAddress, c, n.toLong()))

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    CoreFfiJniBridge.rustlangCoreFfiMemcmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    CoreFfiJniBridge.rustlangCoreFfiBcmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun strlen(s: ConstPtr): ULong =
    CoreFfiJniBridge.rustlangCoreFfiStrlen(s.rawAddress).toULong()
