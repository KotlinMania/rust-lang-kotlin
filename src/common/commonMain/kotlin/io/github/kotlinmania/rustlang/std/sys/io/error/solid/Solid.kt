// port-lint: source sys/io/error/solid.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.solid

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error

public fun errno(): Int = 0

public fun isInterrupted(code: Int): Boolean = io.github.kotlinmania.rustlang.std.sys.net.connection.socket.solid.isInterrupted(code)

public fun decodeErrorKind(code: Int): ErrorKind = Error.decodeErrorKind(code)

public fun formatError(errno: Int, f: StringBuilder) {
    when (val matchVal = Error.errorName(errno)) {
        null -> {
            f.append("$errno")
        }
        else -> {
            val name = matchVal
            f.append(name)
        }
    }
}
