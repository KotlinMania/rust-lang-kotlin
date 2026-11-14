// port-lint: source sys/io/error/motor.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.motor

import io.github.kotlinmania.motort.Error
import io.github.kotlinmania.motort.ErrorCode
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.RawOsError

public fun errno(): RawOsError {
    // Not used in Motor OS because it is ambiguous: Motor OS
    // is micro-kernel-based, and I/O happens via a shared-memory
    // ring buffer, so an I/O operation that on a unix is a syscall
    // may involve no sycalls on Motor OS at all, or a syscall
    // that e.g. waits for a notification from the I/O driver
    // (sys-io); and the wait syscall may succeed, but the
    // driver may report an I/O error; or a bunch of results
    // for several I/O operations, some successful and some
    // not.
    //
    // Also I/O operations in a Motor OS process are handled by a
    // separate runtime background/I/O thread, so it is really hard
    // to define what "last system error in the current thread"
    // actually means.
    val errorCode: ErrorCode = Error.Unknown.into()
    return errorCode.into()
}

public fun isInterrupted(code: RawOsError): Boolean {
    return false
    // Motor OS doesn't have signals.
}

public fun decodeErrorKind(code: RawOsError): ErrorKind {
    if (code < 0 || code > UShort.MAX_VALUE.into()) {
        return io.ErrorKind.Uncategorized
    }
    val error = Error.from(((code as Any?) as ErrorCode))
    return when (error) {
        Error.Unspecified -> io.ErrorKind.Uncategorized
        Error.Unknown -> io.ErrorKind.Uncategorized
        Error.NotReady -> io.ErrorKind.WouldBlock
        Error.unsupportedCase -> io.ErrorKind.Unsupported
        Error.VersionTooHigh -> io.ErrorKind.Unsupported
        Error.VersionTooLow -> io.ErrorKind.Unsupported
        Error.InvalidArgument -> io.ErrorKind.InvalidInput
        Error.OutOfMemory -> io.ErrorKind.OutOfMemory
        Error.NotAllowed -> io.ErrorKind.PermissionDenied
        Error.NotFound -> io.ErrorKind.NotFound
        Error.InternalError -> io.ErrorKind.Other
        Error.TimedOut -> io.ErrorKind.TimedOut
        Error.AlreadyInUse -> io.ErrorKind.AlreadyExists
        Error.UnexpectedEof -> io.ErrorKind.UnexpectedEof
        Error.InvalidFilename -> io.ErrorKind.InvalidFilename
        Error.NotADirectory -> io.ErrorKind.NotADirectory
        Error.BadHandle -> io.ErrorKind.InvalidInput
        Error.FileTooLarge -> io.ErrorKind.FileTooLarge
        Error.NotConnected -> io.ErrorKind.NotConnected
        Error.ConnectionReset -> io.ErrorKind.ConnectionReset
        Error.StorageFull -> io.ErrorKind.StorageFull
        Error.InvalidData -> io.ErrorKind.InvalidData
        else -> io.ErrorKind.Uncategorized
    }
}

public fun formatError(errno: RawOsError, f: StringBuilder) {
    val error =
        when {
            errno < 0 -> {
                val x = errno
                Error.Unknown
            }
            errno > UShort.MAX_VALUE.into() -> {
                val x1 = errno
                Error.Unknown
            }
            else -> {
                val x2 = errno
                Error.from(((x2 as Any?) as ErrorCode))
            }
        }
    f.append("$error")
}
