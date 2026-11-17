// port-lint: source sys/io/error/generic.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.generic

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.result.Result

public fun errno(): Int = 0

public fun isInterrupted(code: Int): Boolean = false

public fun decodeErrorKind(code: Int): ErrorKind = ErrorKind.Uncategorized

public fun formatError(errno: Int, f: StringBuilder): Result<Unit, Error> = run { f.append("operation successful"); Result.Ok<Unit, Error>(Unit) }
