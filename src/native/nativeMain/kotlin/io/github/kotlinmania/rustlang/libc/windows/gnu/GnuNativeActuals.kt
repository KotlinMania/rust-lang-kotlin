@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows.gnu

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.windows.gnu.cinterop.rustlangLibcWindowsGnuStrcasecmp
import io.github.kotlinmania.rustlang.libc.windows.gnu.cinterop.rustlangLibcWindowsGnuStrncasecmp
import io.github.kotlinmania.rustlang.libc.windows.gnu.cinterop.rustlangLibcWindowsGnuWmemchr
import io.github.kotlinmania.rustlang.windows.SizeT
import io.github.kotlinmania.rustlang.windows.WcharT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    rustlangLibcWindowsGnuStrcasecmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>())

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    rustlangLibcWindowsGnuStrncasecmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsGnuWmemchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)
