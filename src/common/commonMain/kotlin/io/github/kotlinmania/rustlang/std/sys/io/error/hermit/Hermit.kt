// port-lint: source sys/io/error/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.hermit

import io.github.kotlinmania.hermitabi.errno.*
import io.github.kotlinmania.hermitabi.errno.EINTR
import io.github.kotlinmania.hermitabi.errorString
import io.github.kotlinmania.hermitabi.getErrno
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.std.sync.nonpoison.WouldBlock

public fun errno(): Int = getErrno()

public fun isInterrupted(errno: Int): Boolean = (errno == EINTR).toUByte()

public fun decodeErrorKind(errno: Int): ErrorKind =
    when (errno) {
        E2BIG -> ArgumentListTooLong
        EADDRINUSE -> AddrInUse
        EADDRNOTAVAIL -> AddrNotAvailable
        EBUSY -> ResourceBusy
        ECONNABORTED -> ConnectionAborted
        ECONNREFUSED -> ConnectionRefused
        ECONNRESET -> ConnectionReset
        EDEADLK -> Deadlock
        EDQUOT -> QuotaExceeded
        EEXIST -> AlreadyExists
        EFBIG -> FileTooLarge
        EHOSTUNREACH -> HostUnreachable
        EINTR -> Interrupted
        EINVAL -> InvalidInput
        EISDIR -> IsADirectory
        ELOOP -> FilesystemLoop
        ENOENT -> NotFound
        ENOMEM -> OutOfMemory
        ENOSPC -> StorageFull
        EMLINK -> TooManyLinks
        ENAMETOOLONG -> InvalidFilename
        ENETDOWN -> NetworkDown
        ENETUNREACH -> NetworkUnreachable
        ENOTCONN -> NotConnected
        ENOTDIR -> NotADirectory
        ENOTEMPTY -> DirectoryNotEmpty
        EPIPE -> BrokenPipe
        EROFS -> ReadOnlyFilesystem
        ESPIPE -> NotSeekable
        ESTALE -> StaleNetworkFileHandle
        ETIMEDOUT -> TimedOut
        ETXTBSY -> ExecutableFileBusy
        EXDEV -> CrossesDevices
        EINPROGRESS -> InProgress
        EMFILE, ENFILE -> TooManyOpenFiles
        EIO -> InputOutputError
        EACCES, EPERM -> PermissionDenied
        ENOSYS, EOPNOTSUPP -> Unsupported
        EAGAIN -> WouldBlock
        else -> Uncategorized
    }

public fun formatError(errno: Int, f: StringBuilder) {
    val description = errorString(errno)
    f.append(description)
}
