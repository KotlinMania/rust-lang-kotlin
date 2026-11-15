@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CSizeT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.cinterop.rustlangStdSysAllocUnixFree
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun malloc(size: CSizeT): MutPtr =
    MutPtr(0L)

public actual fun realloc(ptr: MutPtr, size: CSizeT): MutPtr =
    MutPtr(0L)

public actual fun free(ptr: MutPtr) {
    rustlangStdSysAllocUnixFree(ptr.rawAddress.toCPointer<ByteVar>())
}
