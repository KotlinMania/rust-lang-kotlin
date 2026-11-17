package io.github.kotlinmania.rustlang.std.os.unix.io

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.wasilibcFdRenumber
import io.github.kotlinmania.rustlang.std.fs.OpenOptions
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

public fun nullFd(): Result<OwnedFd, Error> {
    val nullDev = when (val tryResult = OpenOptions.new().read(true).write(true).open("/dev/null")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<OwnedFd, Error>(tryResult.error) }
    return Result.Ok<OwnedFd, Error>(nullDev.into())
}

/**
 * Replaces the underlying file descriptor with the one from `other`.
 * Does not set CLOEXEC.
 */
public fun replaceStdioFd(thisRef: BorrowedFd, other: OwnedFd): Result<Unit, Error> = cvt(wasilibcFdRenumber(other.asRawFd(), thisRef.asRawFd())).map({ Unit })
