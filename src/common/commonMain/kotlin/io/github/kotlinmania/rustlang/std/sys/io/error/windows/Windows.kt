// port-lint: source sys/io/error/windows.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.windows

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.sync.nonpoison.WouldBlock
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.DNS_ERROR_RECORD_TIMED_OUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_ACCESS_DENIED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_ALREADY_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_BAD_NETPATH
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_BAD_NET_NAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_BAD_PATHNAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_BROKEN_PIPE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_BUSY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CALL_NOT_IMPLEMENTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CANT_RESOLVE_FILENAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_COUNTER_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CTX_CLIENT_QUERY_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CTX_MODEM_RESPONSE_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DIRECTORY_NOT_SUPPORTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DIR_NOT_EMPTY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DISK_FULL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DISK_QUOTA_EXCEEDED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DRIVER_CANCEL_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DS_TIMELIMIT_EXCEEDED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_FILENAME_EXCED_RANGE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_FILE_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_FILE_NOT_FOUND
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_FILE_TOO_LARGE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_HANDLE_DISK_FULL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_HOST_UNREACHABLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INVALID_DRIVE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INVALID_NAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INVALID_PARAMETER
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_IO_DEVICE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_IPSEC_IKE_TIMED_OUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_NEGATIVE_SEEK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_NETWORK_UNREACHABLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_NOT_ENOUGH_MEMORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_NOT_SAME_DEVICE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_NO_DATA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_OPERATION_ABORTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_OUTOFMEMORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_PATH_NOT_FOUND
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_POSSIBLE_DEADLOCK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_RESOURCE_CALL_TIMED_OUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_RUNLEVEL_SWITCH_AGENT_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_RUNLEVEL_SWITCH_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_SEEK_ON_DEVICE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_SEM_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_SERVICE_REQUEST_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_TOO_MANY_LINKS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_TOO_MANY_OPEN_FILES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_WRITE_PROTECT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FORMAT_MESSAGE_FROM_HMODULE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FORMAT_MESSAGE_FROM_SYSTEM
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FORMAT_MESSAGE_IGNORE_INSERTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FRS_ERR_SYSVOL_POPULATE_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.WAIT_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FACILITY_NT_BIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEACCES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEADDRINUSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEADDRNOTAVAIL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAECONNABORTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAECONNREFUSED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAECONNRESET
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEDQUOT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEHOSTUNREACH
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEINVAL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEMFILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAENETDOWN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAENETUNREACH
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAENOTCONN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAESHUTDOWN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAETIMEDOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEWOULDBLOCK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.formatMessageW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getModuleHandleW

public fun errno(): Int = getLastError().code

public fun isInterrupted(errno: Int): Boolean = false

public fun decodeErrorKind(errno: Int): ErrorKind {
    when (errno.toUInt()) {
        ERROR_ACCESS_DENIED -> { return PermissionDenied }
        ERROR_ALREADY_EXISTS -> { return AlreadyExists }
        ERROR_FILE_EXISTS -> { return AlreadyExists }
        ERROR_BROKEN_PIPE -> { return BrokenPipe }
        ERROR_FILE_NOT_FOUND, ERROR_PATH_NOT_FOUND, ERROR_INVALID_DRIVE, ERROR_BAD_NETPATH, ERROR_BAD_NET_NAME -> { return NotFound }
        ERROR_NO_DATA -> { return BrokenPipe }
        ERROR_INVALID_NAME, ERROR_BAD_PATHNAME -> { return InvalidFilename }
        ERROR_INVALID_PARAMETER -> { return InvalidInput }
        ERROR_NOT_ENOUGH_MEMORY, ERROR_OUTOFMEMORY -> { return OutOfMemory }
        ERROR_SEM_TIMEOUT, WAIT_TIMEOUT, ERROR_DRIVER_CANCEL_TIMEOUT, ERROR_OPERATION_ABORTED, ERROR_SERVICE_REQUEST_TIMEOUT, ERROR_COUNTER_TIMEOUT, ERROR_TIMEOUT, ERROR_RESOURCE_CALL_TIMED_OUT, ERROR_CTX_MODEM_RESPONSE_TIMEOUT, ERROR_CTX_CLIENT_QUERY_TIMEOUT, FRS_ERR_SYSVOL_POPULATE_TIMEOUT, ERROR_DS_TIMELIMIT_EXCEEDED, DNS_ERROR_RECORD_TIMED_OUT, ERROR_IPSEC_IKE_TIMED_OUT, ERROR_RUNLEVEL_SWITCH_TIMEOUT, ERROR_RUNLEVEL_SWITCH_AGENT_TIMEOUT -> { return TimedOut }
        ERROR_CALL_NOT_IMPLEMENTED -> { return Unsupported }
        ERROR_HOST_UNREACHABLE -> { return HostUnreachable }
        ERROR_NETWORK_UNREACHABLE -> { return NetworkUnreachable }
        ERROR_DIRECTORY -> { return NotADirectory }
        ERROR_DIRECTORY_NOT_SUPPORTED -> { return IsADirectory }
        ERROR_DIR_NOT_EMPTY -> { return DirectoryNotEmpty }
        ERROR_WRITE_PROTECT -> { return ReadOnlyFilesystem }
        ERROR_DISK_FULL, ERROR_HANDLE_DISK_FULL -> { return StorageFull }
        ERROR_SEEK_ON_DEVICE -> { return NotSeekable }
        ERROR_DISK_QUOTA_EXCEEDED -> { return QuotaExceeded }
        ERROR_FILE_TOO_LARGE -> { return FileTooLarge }
        ERROR_BUSY -> { return ResourceBusy }
        ERROR_POSSIBLE_DEADLOCK -> { return Deadlock }
        ERROR_NOT_SAME_DEVICE -> { return CrossesDevices }
        ERROR_TOO_MANY_LINKS -> { return TooManyLinks }
        ERROR_TOO_MANY_OPEN_FILES -> { return TooManyOpenFiles }
        ERROR_FILENAME_EXCED_RANGE -> { return InvalidFilename }
        ERROR_CANT_RESOLVE_FILENAME -> { return FilesystemLoop }
        ERROR_IO_DEVICE -> { return InputOutputError }
        ERROR_NEGATIVE_SEEK -> { return InvalidInput }
        else -> {}
    }
    return when (errno) {
        WSAEACCES -> PermissionDenied
        WSAEADDRINUSE -> AddrInUse
        WSAEADDRNOTAVAIL -> AddrNotAvailable
        WSAECONNABORTED -> ConnectionAborted
        WSAECONNREFUSED -> ConnectionRefused
        WSAECONNRESET -> ConnectionReset
        WSAEINVAL -> InvalidInput
        WSAENOTCONN -> NotConnected
        WSAEWOULDBLOCK -> WouldBlock
        WSAETIMEDOUT -> TimedOut
        WSAEHOSTUNREACH -> HostUnreachable
        WSAENETDOWN -> NetworkDown
        WSAENETUNREACH -> NetworkUnreachable
        WSAEDQUOT -> QuotaExceeded
        WSAEMFILE -> TooManyOpenFiles
        // Not a perfect mapping but this error is only returned when writing to
        // a socket after shutting down the write-end. On Unix targets, EPIPE is
        // returned in those cases.
        WSAESHUTDOWN -> BrokenPipe
        else -> Uncategorized
    }
}

/**
 * Gets a detailed string description for the given error number.
 */
public fun formatError(errnum: Int, f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
    var errnum1 = errnum
    val localStorageL99C4 = AddressableArrayLocal.fromUShortArray(UShortArray(2048) { 0.toUShort() })
    val buf by localStorageL99C4
    return run {
        var module = MutPtr(0L)
        var flags = 0
        // NTSTATUS errors may be encoded as HRESULT, which may returned from
        // GetLastError. For more information about Windows error codes, see
        // `[MS - ERREF]`: https://docs.microsoft.com/en-us/openspecs/windowsProtocols/ms-erref/0642cb2f-2075-4469-918c-4441e69c548a
        if ((errnum1 and FACILITY_NT_BIT.toInt()) != 0) {
            // format according to https://support.microsoft.com/en-us/help/259693
            val NTDLL_DLL: List<UShort> = listOf('N'.code.toUShort(), 'T'.code.toUShort(), 'D'.code.toUShort(), 'L'.code.toUShort(), 'L'.code.toUShort(), '.'.code.toUShort(), 'D'.code.toUShort(), 'L'.code.toUShort(), 'L'.code.toUShort(), 0u.toUShort())
            module = getModuleHandleW(NTDLL_DLL.asPtr())
            if (!module.isNull()) {
                errnum1 = errnum1 xor (FACILITY_NT_BIT.toInt())
                flags = FORMAT_MESSAGE_FROM_HMODULE
            }
        }
        val res = formatMessageW(flags or FORMAT_MESSAGE_FROM_SYSTEM or FORMAT_MESSAGE_IGNORE_INSERTS, module, errnum1.toUInt(), 0u, localStorageL99C4.elementMutPtr(0), buf.size.toUInt(), ConstPtr(0L)).toInt()
        if (res == 0) {
            // Sometimes FormatMessageW can fail e.g., system doesn't like 0 as langId,
            val fmErr = errno()
            return run { f.append("OS Error $errnum (FormatMessageW() returned error $fmErr)"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
        }
        run {
            val matchVal = String.fromUtf16(((buf).sliceArray(0 until res)))
            when (matchVal) { is Result.Ok<*, *> -> {
                val msg = matchVal.okValue()
                // Trim trailing CRLF inserted by FormatMessageW
                f.append(msg.trimAsciiEnd())
            }; is Result.Err<*, *> -> {
                f.append("OS Error $errnum1 (FormatMessageW() returned invalid UTF-16)")
            } }
        }
    }
}
