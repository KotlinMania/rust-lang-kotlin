// port-lint: source sys/io/error/windows.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.io.error.windows

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.sync.nonpoison.WouldBlock
import io.github.kotlinmania.rustlang.std.sys.pal.api
import io.github.kotlinmania.rustlang.std.sys.pal.c

public fun errno(): Int = api.getLastError().code

public fun isInterrupted(errno: Int): Boolean = false

public fun decodeErrorKind(errno: Int): ErrorKind {
    when (errno.toUInt()) {
        c.errorAccessDenied -> { return PermissionDenied }
        c.errorAlreadyExists -> { return AlreadyExists }
        c.errorFileExists -> { return AlreadyExists }
        c.errorBrokenPipe -> { return BrokenPipe }
        c.errorFileNotFound, c.errorPathNotFound, c.errorInvalidDrive, c.errorBadNetpath, c.errorBadNetName -> { return NotFound }
        c.errorNoData -> { return BrokenPipe }
        c.errorInvalidName, c.errorBadPathname -> { return InvalidFilename }
        c.errorInvalidParameter -> { return InvalidInput }
        c.errorNotEnoughMemory, c.errorOutofmemory -> { return OutOfMemory }
        c.errorSemTimeout, c.waitTimeout, c.errorDriverCancelTimeout, c.errorOperationAborted, c.errorServiceRequestTimeout, c.errorCounterTimeout, c.errorTimeout, c.errorResourceCallTimedOut, c.errorCtxModemResponseTimeout, c.errorCtxClientQueryTimeout, c.frsErrSysvolPopulateTimeout, c.errorDsTimelimitExceeded, c.dnsErrorRecordTimedOut, c.errorIpsecIkeTimedOut, c.errorRunlevelSwitchTimeout, c.errorRunlevelSwitchAgentTimeout -> { return TimedOut }
        c.errorCallNotImplemented -> { return Unsupported }
        c.errorHostUnreachable -> { return HostUnreachable }
        c.errorNetworkUnreachable -> { return NetworkUnreachable }
        c.errorDirectory -> { return NotADirectory }
        c.errorDirectoryNotSupported -> { return IsADirectory }
        c.errorDirNotEmpty -> { return DirectoryNotEmpty }
        c.errorWriteProtect -> { return ReadOnlyFilesystem }
        c.errorDiskFull, c.errorHandleDiskFull -> { return StorageFull }
        c.errorSeekOnDevice -> { return NotSeekable }
        c.errorDiskQuotaExceeded -> { return QuotaExceeded }
        c.errorFileTooLarge -> { return FileTooLarge }
        c.errorBusy -> { return ResourceBusy }
        c.errorPossibleDeadlock -> { return Deadlock }
        c.errorNotSameDevice -> { return CrossesDevices }
        c.errorTooManyLinks -> { return TooManyLinks }
        c.errorTooManyOpenFiles -> { return TooManyOpenFiles }
        c.errorFilenameExcedRange -> { return InvalidFilename }
        c.errorCantResolveFilename -> { return FilesystemLoop }
        c.errorIoDevice -> { return InputOutputError }
        c.errorNegativeSeek -> { return InvalidInput }
        else -> {}
    }
    return when (errno) {
        c.wsaeacces -> PermissionDenied
        c.wsaeaddrinuse -> AddrInUse
        c.wsaeaddrnotavail -> AddrNotAvailable
        c.wsaeconnaborted -> ConnectionAborted
        c.wsaeconnrefused -> ConnectionRefused
        c.wsaeconnreset -> ConnectionReset
        c.wsaeinval -> InvalidInput
        c.wsaenotconn -> NotConnected
        c.wsaewouldblock -> WouldBlock
        c.wsaetimedout -> TimedOut
        c.wsaehostunreach -> HostUnreachable
        c.wsaenetdown -> NetworkDown
        c.wsaenetunreach -> NetworkUnreachable
        c.wsaedquot -> QuotaExceeded
        c.wsaemfile -> TooManyOpenFiles
        // Not a perfect mapping but this error is only returned when writing to
        // a socket after shutting down the write-end. On Unix targets, EPIPE is
        // returned in those cases.
        c.wsaeshutdown -> BrokenPipe
        else -> Uncategorized
    }
}

/**
 * Gets a detailed string description for the given error number.
 */
public fun formatError(errnum: Int, f: StringBuilder): Result<Unit, Error> {
    var errnum1 = errnum
    val localStorageL99C4 = AddressableArrayLocal.fromUShortArray(UShortArray(2048) { 0.toUShort() })
    val buf by localStorageL99C4
    return run {
        var module = MutPtr(0L)
        var flags = 0
        // NTSTATUS errors may be encoded as HRESULT, which may returned from
        // GetLastError. For more information about Windows error codes, see
        // `[MS - ERREF]`: https://docs.microsoft.com/en-us/openspecs/windowsProtocols/ms-erref/0642cb2f-2075-4469-918c-4441e69c548a
        if ((errnum1 and c.FACILITY_NT_BIT.toInt()) != 0) {
            // format according to https://support.microsoft.com/en-us/help/259693
            val NTDLL_DLL: List<UShort> = listOf('N'.code.toUShort(), 'T'.code.toUShort(), 'D'.code.toUShort(), 'L'.code.toUShort(), 'L'.code.toUShort(), '.'.code.toUShort(), 'D'.code.toUShort(), 'L'.code.toUShort(), 'L'.code.toUShort(), 0u.toUShort())
            module = c.GetModuleHandleW(NTDLL_DLL.asPtr())
            if (!module.isNull()) {
                errnum1 = errnum1 xor (c.FACILITY_NT_BIT.toInt())
                flags = c.FORMAT_MESSAGE_FROM_HMODULE
            }
        }
        val res = c.FormatMessageW(flags or c.FORMAT_MESSAGE_FROM_SYSTEM or c.FORMAT_MESSAGE_IGNORE_INSERTS, module, errnum1.toUInt(), 0, localStorageL99C4.elementMutPtr(0), buf.size.toUInt(), ConstPtr(0L)).toInt()
        if (res == 0) {
            // Sometimes FormatMessageW can fail e.g., system doesn't like 0 as langId,
            val fmErr = errno()
            return run { f.append("OS Error $errnum (FormatMessageW() returned error $fmErr)"); Result.Ok(Unit) }
        }
        run {
            val matchVal = String.fromUtf16(((buf).sliceArray(0 until res)))
            when (matchVal) { is Result.Ok -> {
                val msg = matchVal.value
                // Trim trailing CRLF inserted by FormatMessageW
                f.append(msg.trimAsciiEnd())
            }; is Result.Err -> {
                f.append("OS Error $errnum1 (FormatMessageW() returned invalid UTF-16)")
            } }
        }
    }
}
