@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.unix

import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ptr

private object SysAllocUnixJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysAllocUnixFree(ptr: Long)
}

public actual fun malloc(size: CSizeT): MutPtr =
    MutPtr(0L)

public actual fun realloc(ptr: MutPtr, size: CSizeT): MutPtr =
    MutPtr(0L)

public actual fun free(ptr: MutPtr) {
    SysAllocUnixJniBridge.stdSysAllocUnixFree(ptr.rawAddress)
}
