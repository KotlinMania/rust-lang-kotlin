// port-lint: source sys/pal/windows/winsock.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.winsock

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.io.Error
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c
import io.github.kotlinmania.rustlang.sync.once.Once

public var WSA_INIT: Once = Once.new()

/**
 * Checks whether the Windows socket interface has been started already, and
 * if not, starts it.
 */
public fun startup() {
    // Make sure to only call `WSAStartup` once, because it's not thread-safe
    // on Wine: https://bugs.winehq.org/showBug.cgi?id=60084.
    WSA_INIT.callOnceForce({ wsaStartup() })
}

public fun wsaStartup() {
    run {
        val data: c.Wsadata  = c.Wsadata()
        val ret = c.WSAStartup(0x202, data)
        check(ret == 0)
    }
}

public fun cleanup() {
    // We don't need to call WSACleanup here because exiting the process will cause
    // the OS to clean everything for us, which is faster than doing it manually.
    // See #141799.
}

/**
 * Returns the last error from the Windows socket interface.
 */
public fun lastError(): Error = Error.fromRawOsError(c.WSAGetLastError())

public interface IsMinusOne {
    public fun isMinusOne(): Boolean
}

public fun Byte.isMinusOne(): Boolean = (this == (-1).toByte()).toUByte()

public fun Short.isMinusOne(): Boolean = (this == (-1).toShort()).toUByte()

public fun Int.isMinusOne(): Boolean = (this == -1).toUByte()

public fun Long.isMinusOne(): Boolean = (this == -1L).toUByte()

/**
 * Checks if the signed integer is the Windows constant `SOCKET_ERROR` (-1)
 * and if so, returns the last error from the Windows socket interface. This
 * function must be called before another call to the socket API is made.
 */
public fun <T : IsMinusOne> cvt(t: T): Result<T, io.github.kotlinmania.rustlang.std.io.error.Error> =
    if (t.isMinusOne()) {
        Result.Err(lastError())
    } else {
        Result.Ok(t)
    }

/**
 * A variant of `cvt` for `getaddrinfo` which return 0 for a success.
 */
public fun cvtGai(err: CInt): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> =
    if (err == 0) {
        Result.Ok(Unit)
    } else {
        Result.Err(lastError())
    }

/**
 * Just to provide the same interface as sys/pal/unix/net.rs
 */
public fun <T> cvtR(f: () -> T): Result<T, io.github.kotlinmania.rustlang.std.io.error.Error> where T : IsMinusOne = cvt(f())
