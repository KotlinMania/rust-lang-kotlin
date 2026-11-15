@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.cinterop.rustlangCoreFfiBcmp
import io.github.kotlinmania.rustlang.core.ffi.cinterop.rustlangCoreFfiMemcmp
import io.github.kotlinmania.rustlang.core.ffi.cinterop.rustlangCoreFfiMemcpy
import io.github.kotlinmania.rustlang.core.ffi.cinterop.rustlangCoreFfiMemmove
import io.github.kotlinmania.rustlang.core.ffi.cinterop.rustlangCoreFfiMemset
import io.github.kotlinmania.rustlang.core.ffi.cinterop.rustlangCoreFfiStrlen
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(rustlangCoreFfiMemcpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(rustlangCoreFfiMemmove(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    MutPtr(rustlangCoreFfiMemset(s.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    rustlangCoreFfiMemcmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    rustlangCoreFfiBcmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strlen(s: ConstPtr): ULong =
    rustlangCoreFfiStrlen(s.rawAddress.toCPointer<ByteVar>())
