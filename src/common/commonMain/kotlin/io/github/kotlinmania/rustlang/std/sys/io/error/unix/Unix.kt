// port-lint: source sys/io/error/unix.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.unix

import io.github.kotlinmania.libc.fuchsia.E2BIG
import io.github.kotlinmania.libc.fuchsia.EACCES
import io.github.kotlinmania.libc.fuchsia.EADDRINUSE
import io.github.kotlinmania.libc.fuchsia.EADDRNOTAVAIL
import io.github.kotlinmania.libc.fuchsia.EAGAIN
import io.github.kotlinmania.libc.fuchsia.EBUSY
import io.github.kotlinmania.libc.fuchsia.ECONNABORTED
import io.github.kotlinmania.libc.fuchsia.ECONNREFUSED
import io.github.kotlinmania.libc.fuchsia.ECONNRESET
import io.github.kotlinmania.libc.fuchsia.EDEADLK
import io.github.kotlinmania.libc.fuchsia.EDQUOT
import io.github.kotlinmania.libc.fuchsia.EEXIST
import io.github.kotlinmania.libc.fuchsia.EFBIG
import io.github.kotlinmania.libc.fuchsia.EHOSTUNREACH
import io.github.kotlinmania.libc.fuchsia.EINPROGRESS
import io.github.kotlinmania.libc.fuchsia.EINTR
import io.github.kotlinmania.libc.fuchsia.EINVAL
import io.github.kotlinmania.libc.fuchsia.EIO
import io.github.kotlinmania.libc.fuchsia.EISDIR
import io.github.kotlinmania.libc.fuchsia.ELOOP
import io.github.kotlinmania.libc.fuchsia.EMFILE
import io.github.kotlinmania.libc.fuchsia.EMLINK
import io.github.kotlinmania.libc.fuchsia.ENAMETOOLONG
import io.github.kotlinmania.libc.fuchsia.ENETDOWN
import io.github.kotlinmania.libc.fuchsia.ENETUNREACH
import io.github.kotlinmania.libc.fuchsia.ENFILE
import io.github.kotlinmania.libc.fuchsia.ENOENT
import io.github.kotlinmania.libc.fuchsia.ENOMEM
import io.github.kotlinmania.libc.fuchsia.ENOSPC
import io.github.kotlinmania.libc.fuchsia.ENOSYS
import io.github.kotlinmania.libc.fuchsia.ENOTCONN
import io.github.kotlinmania.libc.fuchsia.ENOTDIR
import io.github.kotlinmania.libc.fuchsia.ENOTEMPTY
import io.github.kotlinmania.libc.fuchsia.ENOTSUP
import io.github.kotlinmania.libc.fuchsia.EOPNOTSUPP
import io.github.kotlinmania.libc.fuchsia.EPERM
import io.github.kotlinmania.libc.fuchsia.EPIPE
import io.github.kotlinmania.libc.fuchsia.EROFS
import io.github.kotlinmania.libc.fuchsia.ESPIPE
import io.github.kotlinmania.libc.fuchsia.ESTALE
import io.github.kotlinmania.libc.fuchsia.ETIMEDOUT
import io.github.kotlinmania.libc.fuchsia.ETXTBSY
import io.github.kotlinmania.libc.fuchsia.EWOULDBLOCK
import io.github.kotlinmania.libc.fuchsia.EXDEV
import io.github.kotlinmania.libc.fuchsia.SizeT
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.CInt
import io.github.kotlinmania.rustlang.std.sync.nonpoison.WouldBlock

// SAFETY: this will always return the same pointer on a given thread.
public expect fun errnoLocation(): MutPtr

/**
 * Returns the platform-specific value of errno
 */
public fun errno(): Int = (ObjectSlotLayout<CInt>()).readNative((errnoLocation()).rawAddress)

// needed for readdir and syscall!

/**
 * Sets the platform-specific value of errno
 */
public fun setErrno(e: Int) {
    (ObjectSlotLayout<CInt>()).writeNative((errnoLocation()).rawAddress, e)
}

public expect var LIBC_ERRNO: CInt

public fun isInterrupted(errno: Int): Boolean = (errno == EINTR).toUByte()

public fun decodeErrorKind(errno: Int): ErrorKind =
    when {
        errno == E2BIG -> ArgumentListTooLong
        errno == EADDRINUSE -> AddrInUse
        errno == EADDRNOTAVAIL -> AddrNotAvailable
        errno == EBUSY -> ResourceBusy
        errno == ECONNABORTED -> ConnectionAborted
        errno == ECONNREFUSED -> ConnectionRefused
        errno == ECONNRESET -> ConnectionReset
        errno == EDEADLK -> Deadlock
        errno == EDQUOT -> QuotaExceeded
        errno == EEXIST -> AlreadyExists
        errno == EFBIG -> FileTooLarge
        errno == EHOSTUNREACH -> HostUnreachable
        errno == EINTR -> Interrupted
        errno == EINVAL -> InvalidInput
        errno == EISDIR -> IsADirectory
        errno == ELOOP -> FilesystemLoop
        errno == ENOENT -> NotFound
        errno == ENOMEM -> OutOfMemory
        errno == ENOSPC -> StorageFull
        errno == EMLINK -> TooManyLinks
        errno == ENAMETOOLONG -> InvalidFilename
        errno == ENETDOWN -> NetworkDown
        errno == ENETUNREACH -> NetworkUnreachable
        errno == ENOTCONN -> NotConnected
        errno == ENOTDIR -> NotADirectory
        errno == ENOTEMPTY -> DirectoryNotEmpty
        errno == EPIPE -> BrokenPipe
        errno == EROFS -> ReadOnlyFilesystem
        errno == ESPIPE -> NotSeekable
        errno == ESTALE -> StaleNetworkFileHandle
        errno == ETIMEDOUT -> TimedOut
        errno == ETXTBSY -> ExecutableFileBusy
        errno == EXDEV -> CrossesDevices
        errno == EINPROGRESS -> InProgress
        errno == EMFILE || errno == ENFILE -> TooManyOpenFiles
        errno == EIO -> InputOutputError
        errno == EACCES || errno == EPERM -> PermissionDenied
        errno == ENOSYS -> Unsupported
        // EOPNOTSUPP and ENOTSUP can have the same value on some systems,
        // but different values on others (e.g. Apple), so we can't use a
        // match clause
        errno == EOPNOTSUPP || errno == ENOTSUP -> {
            val x = errno
            Unsupported
        }
        // These two constants can have the same value on some systems,
        // but different values on others, so we can't use a match
        // clause
        errno == EAGAIN || errno == EWOULDBLOCK -> {
            val x1 = errno
            WouldBlock
        }
        else -> Uncategorized
    }

/**
 * Gets a detailed string description for the given error number.
 */
public fun formatError(errno: Int, f: StringBuilder) {
    val TMPBUF_SZ: Int =
        if (false) {
            1024
        } else {
            128
        }
    fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt = 0
    val localStorageL218C4 = AddressableArrayLocal.fromUByteArray(UByteArray((TMPBUF_SZ).toInt()) { 0.toUByte() })
    val buf by localStorageL218C4
    val p = localStorageL218C4.elementMutPtr(0)
    run {
        if (strerrorR(errno, p, buf.size) < 0) {
            error("strerror_r failure")
        }
        val p1 = ConstPtr(p.rawAddress, p.rawMeta)
        // We can't always expect a UTF-8 environment. When we don't get that luxury,
        // it's better to give a low-quality error message than none at all.
        f.append("${CStr.fromPtr(p1).display()}")
    }
}
