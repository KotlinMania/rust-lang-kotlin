@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.cinterop.rustlangStdSysIoErrorUnixErrnoLocation
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.cinterop.rustlangStdSysIoErrorUnixGetLIBCERRNO
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.cinterop.rustlangStdSysIoErrorUnixSetLIBCERRNO
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toLong

public actual var LIBC_ERRNO: io.github.kotlinmania.rustlang.libc.CInt
    get() = rustlangStdSysIoErrorUnixGetLIBCERRNO()
    set(value) {
        rustlangStdSysIoErrorUnixSetLIBCERRNO(value)
    }

public actual fun errnoLocation(): MutPtr =
    MutPtr(rustlangStdSysIoErrorUnixErrnoLocation()?.toLong() ?: 0L)
