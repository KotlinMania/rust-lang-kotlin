// port-lint: source sys/io/error/generic.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.generic

import io.github.kotlinmania.rustlang.Io

public fun errno(): Int = 0

public fun isInterrupted(code: Int): Boolean = false

public fun decodeErrorKind(code: Int): Io.ErrorKind = ErrorKind.Uncategorized

public fun formatError(errno: Int, f: StringBuilder) {
    f.append("operation successful")
}
