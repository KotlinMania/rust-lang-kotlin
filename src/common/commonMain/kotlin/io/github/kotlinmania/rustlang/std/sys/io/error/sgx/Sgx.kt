// port-lint: source sys/io/error/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.sgx

import io.github.kotlinmania.fortanixsgxabi.Error
import io.github.kotlinmania.fortanixsgxabi.RESULT_SUCCESS
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind

public fun errno(): Int = RESULT_SUCCESS

public fun isInterrupted(code: Int): Boolean = (code == Error.Interrupted.value.toInt()).toUByte()

public fun decodeErrorKind(code: Int): ErrorKind {
    // Note: not sure how to make sure all variants of Error are covered
    return if (code == Error.NotFound.value.toInt()) {
        io.ErrorKind.NotFound
    } else if (code == Error.PermissionDenied.value.toInt()) {
        io.ErrorKind.PermissionDenied
    } else if (code == Error.ConnectionRefused.value.toInt()) {
        io.ErrorKind.ConnectionRefused
    } else if (code == Error.ConnectionReset.value.toInt()) {
        io.ErrorKind.ConnectionReset
    } else if (code == Error.ConnectionAborted.value.toInt()) {
        io.ErrorKind.ConnectionAborted
    } else if (code == Error.NotConnected.value.toInt()) {
        io.ErrorKind.NotConnected
    } else if (code == Error.AddrInUse.value.toInt()) {
        io.ErrorKind.AddrInUse
    } else if (code == Error.AddrNotAvailable.value.toInt()) {
        io.ErrorKind.AddrNotAvailable
    } else if (code == Error.BrokenPipe.value.toInt()) {
        io.ErrorKind.BrokenPipe
    } else if (code == Error.AlreadyExists.value.toInt()) {
        io.ErrorKind.AlreadyExists
    } else if (code == Error.WouldBlock.value.toInt()) {
        io.ErrorKind.WouldBlock
    } else if (code == Error.InvalidInput.value.toInt()) {
        io.ErrorKind.InvalidInput
    } else if (code == Error.InvalidData.value.toInt()) {
        io.ErrorKind.InvalidData
    } else if (code == Error.TimedOut.value.toInt()) {
        io.ErrorKind.TimedOut
    } else if (code == Error.WriteZero.value.toInt()) {
        io.ErrorKind.WriteZero
    } else if (code == Error.Interrupted.value.toInt()) {
        io.ErrorKind.Interrupted
    } else if (code == Error.Other.value.toInt()) {
        io.ErrorKind.Uncategorized
    } else if (code == Error.UnexpectedEof.value.toInt()) {
        io.ErrorKind.UnexpectedEof
    } else {
        io.ErrorKind.Uncategorized
    }
}

public fun formatError(errno: Int, f: StringBuilder) {
    if (errno == RESULT_SUCCESS) {
        f.append("operation successful")
    } else if (((((Error.UserRangeStart as Any?) as Unit))..(((Error.UserRangeEnd as Any?) as Unit))).contains(errno)) {
        f.append("user-specified error $errno")
    } else {
        val kind = decodeErrorKind(errno)
        f.append("$kind")
    }
}
