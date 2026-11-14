// port-lint: source sys/io/error/xous.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.xous

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error as XousError

public fun errno(): Int = 0

public fun isInterrupted(code: Int): Boolean = false

public fun decodeErrorKind(code: Int): ErrorKind = ErrorKind.Uncategorized

public fun formatError(errno: Int, f: StringBuilder) {
    val error = XousError.from(errno)
    f.append("$error")
}
