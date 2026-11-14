@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.unix

import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ptr
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.cinterop.stdSysAllocUnixFree
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun malloc(size: CSizeT): MutPtr =
    MutPtr(0L)

public actual fun realloc(ptr: MutPtr, size: CSizeT): MutPtr =
    MutPtr(0L)

public actual fun free(ptr: MutPtr) {
    stdSysAllocUnixFree(ptr.rawAddress.toCPointer<ByteVar>())
}
