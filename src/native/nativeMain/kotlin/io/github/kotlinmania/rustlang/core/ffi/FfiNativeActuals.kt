@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.cinterop.coreFfiBcmp
import io.github.kotlinmania.rustlang.core.ffi.cinterop.coreFfiMemcmp
import io.github.kotlinmania.rustlang.core.ffi.cinterop.coreFfiMemcpy
import io.github.kotlinmania.rustlang.core.ffi.cinterop.coreFfiMemmove
import io.github.kotlinmania.rustlang.core.ffi.cinterop.coreFfiMemset
import io.github.kotlinmania.rustlang.core.ffi.cinterop.coreFfiStrlen
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(coreFfiMemcpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(coreFfiMemmove(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    MutPtr(coreFfiMemset(s.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    coreFfiMemcmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    coreFfiBcmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strlen(s: ConstPtr): ULong =
    coreFfiStrlen(s.rawAddress.toCPointer<ByteVar>())
