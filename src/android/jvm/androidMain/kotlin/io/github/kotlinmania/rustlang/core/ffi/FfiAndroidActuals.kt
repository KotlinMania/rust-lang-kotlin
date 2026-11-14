@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object FfiJniBridge {
    init {
        System.loadLibrary("core_jni")
    }

    @JvmStatic
    external fun coreFfiMemcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun coreFfiMemmove(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun coreFfiMemset(s: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun coreFfiMemcmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun coreFfiBcmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun coreFfiStrlen(s: Long): Long
}

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(FfiJniBridge.coreFfiMemcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(FfiJniBridge.coreFfiMemmove(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    MutPtr(FfiJniBridge.coreFfiMemset(s.rawAddress, c, n.toLong()))

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    FfiJniBridge.coreFfiMemcmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    FfiJniBridge.coreFfiBcmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun strlen(s: ConstPtr): ULong =
    FfiJniBridge.coreFfiStrlen(s.rawAddress).toULong()
