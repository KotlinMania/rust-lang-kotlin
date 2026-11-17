@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.cinterop.stdSysIoErrorUnixErrnoLocation
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.cinterop.stdSysIoErrorUnixGetLIBCERRNO
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.cinterop.stdSysIoErrorUnixSetLIBCERRNO
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toLong

public actual var LIBC_ERRNO: io.github.kotlinmania.libc.CInt
    get() = stdSysIoErrorUnixGetLIBCERRNO()
    set(value) {
        stdSysIoErrorUnixSetLIBCERRNO(value)
    }

public actual fun errnoLocation(): MutPtr =
    MutPtr(stdSysIoErrorUnixErrnoLocation()?.toLong() ?: 0L)
