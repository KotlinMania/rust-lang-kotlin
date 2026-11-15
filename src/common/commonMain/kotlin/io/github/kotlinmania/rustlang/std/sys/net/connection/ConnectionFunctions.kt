package io.github.kotlinmania.rustlang.std.sys.net.connection

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.io.ErrorKind
import io.github.kotlinmania.rustlang.io.constError
import io.github.kotlinmania.rustlang.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.sys.net.connection.socket.lookupHost

public fun <Iter, A : ToSocketAddrs<Any?>, T> eachAddr(addr: A, f: (SocketAddr) -> Result<T, Error>): Result<T, Error> {
    var lastErr = null
    for (addr1 in when (val tryResult = addr.toSocketAddrs()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
        run {
            val matchVal = f(addr1)
            when (matchVal) { is Result.Ok<*, *> -> {
                val l = matchVal.value
                return Result.Ok(l)
            }; is Result.Err<*, *> -> {
                val e = matchVal.error
                lastErr = e
            } }
        }
    }
    return when (lastErr) {
        null -> Result.Err(Error.NO_ADDRESSES)
        else -> {
            val err = lastErr
            Result.Err(err)
        }
    }
}

// Default implementation, may be overridden by platform-specific implementations.
public fun lookupHostString(addr: String): Result<Any, Error> {
    // Split the string by ':' and convert the second part to u16...
    val (host, portStr) = addr.let { s -> val idx = s.lastIndexOf(':'); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } ?: return Result.Err(constError(ErrorKind.InvalidInput, "invalid socket address"))
    val port = (portStr.toUShortOrNull()?.let { Result.Ok(it) } ?: Result.Err("failed to parse UShort")) ?: return Result.Err(constError(ErrorKind.InvalidInput, "invalid port value"))
    // ... and make the system look up the host.
    return lookupHost(host, port)
}
