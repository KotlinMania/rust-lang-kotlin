package io.github.kotlinmania.rustlang.std.sys.pal.wasi

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.abort
import io.github.kotlinmania.rustlang.wasip1.Errno

public fun abortInternal(): Nothing = abort()

public fun err2io(err: Errno): Error = Error.fromRawOsError(err.raw().into())

public fun <T : IsMinusOne> cvt(t: T): Result<T, Error> =
    if (t.isMinusOne()) {
        Result.Err<T, Error>(Error.lastOsError())
    } else {
        Result.Ok<T, Error>(t)
    }

public fun <T> cvtR(f: () -> T): Result<T, Error> where T : IsMinusOne {
    while (true) {
        run {
            val matchVal = cvt(f())
            when {
                matchVal is Result.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                    val e = matchVal.errValue()
                }
                else -> {
                    val other = matchVal
                    return other
                }
            }
        }
    }
}

public fun Byte.isMinusOne(): Boolean = (this == (-1).toByte()).toUByte()

public fun Short.isMinusOne(): Boolean = (this == (-1).toShort()).toUByte()

public fun Int.isMinusOne(): Boolean = (this == -1).toUByte()

public fun Long.isMinusOne(): Boolean = (this == -1L).toUByte()
