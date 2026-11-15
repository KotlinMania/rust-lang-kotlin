// port-lint: source io/error.rs
package io.github.kotlinmania.rustlang.std.io.error

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.OsFunctions
import io.github.kotlinmania.rustlang.core.io.error.RawOsError
import io.github.kotlinmania.rustlang.sys.io.Errno
import io.github.kotlinmania.rustlang.sys.io.error.generic.decodeErrorKind
import io.github.kotlinmania.rustlang.sys.io.error.generic.formatError
import io.github.kotlinmania.rustlang.sys.io.error.generic.isInterrupted

// Because std is linked in during testing, these incoherent implementations would
// be duplicated if this was unconditionally included.
// See #2912 for details.

/**
 * Returns an error representing the last OS error which occurred.
 *
 * This function reads the value of `errno` for the target platform (e.g.
 * `GetLastError` on Windows) and will return a corresponding instance of
 * [Error] for the error code.
 *
 * This should be called immediately after a call to a platform function,
 * otherwise the state of the error value is indeterminate. In particular,
 * other standard library functions may call platform functions that may
 * (or may not) reset the error value even if they succeed.
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Error
 *
 * val osError = Error.lastOsError()
 * println("last OS error: $osError")
 * ```
 */
public fun Error.Companion.lastOsError(): Error = Error.fromRawOsError(Errno())

/**
 * Creates a new instance of an [Error] from a particular OS error code.
 *
 * # Examples
 *
 * On Linux:
 *
 * ```kotlin
 * import std.io
 *
 * val error = io.Error.fromRawOsError(22)
 * check(error.kind() == io.ErrorKind.InvalidInput)
 * ```
 *
 * On Windows:
 *
 * ```kotlin
 * import std.io
 *
 * val error = io.Error.fromRawOsError(10022)
 * check(error.kind() == io.ErrorKind.InvalidInput)
 * ```
 */
public fun Error.Companion.fromRawOsError(code: RawOsError): Error {
    val FUNCTIONS: OsFunctions = OsFunctions(formatOsError = formatError, decodeErrorKind = decodeErrorKind, isInterrupted = isInterrupted)
    // SAFETY: `FUNCTIONS` is a constant and not created at runtime.
    return Error.fromRawOsErrorWithFunctions(code, FUNCTIONS)
}
