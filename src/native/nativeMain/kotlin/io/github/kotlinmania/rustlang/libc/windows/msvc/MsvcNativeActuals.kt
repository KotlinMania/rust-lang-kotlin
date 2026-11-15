@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows.msvc

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.windows.SizeT
import io.github.kotlinmania.rustlang.libc.windows.msvc.cinterop.libcWindowsMsvcMemccpy
import io.github.kotlinmania.rustlang.libc.windows.msvc.cinterop.libcWindowsMsvcStricmp
import io.github.kotlinmania.rustlang.libc.windows.msvc.cinterop.libcWindowsMsvcStrnicmp
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun stricmp(s1: ConstPtr, s2: ConstPtr): CInt =
    libcWindowsMsvcStricmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>())

public actual fun strnicmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    libcWindowsMsvcStrnicmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, count: SizeT): MutPtr =
    MutPtr(libcWindowsMsvcMemccpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), c, count)?.toLong() ?: 0L)
