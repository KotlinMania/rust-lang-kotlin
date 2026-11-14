// port-lint: source sys/io/error/uefi.rs
package io.github.kotlinmania.rustlang.std.sys.io.error.uefi

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.RawOsError

public fun errno(): RawOsError = 0

public fun isInterrupted(code: RawOsError): Boolean = false

public fun decodeErrorKind(code: RawOsError): ErrorKind =
    when (Status.fromUsize(code)) {
        Status.alreadyStarted, Status.compromisedData, Status.connectionFin, Status.crcError, Status.deviceError, Status.endOfMedia, Status.httpError, Status.icmpError, Status.incompatibleVersion, Status.loadError, Status.mediaChanged, Status.noMapping, Status.noMedia, Status.notStarted, Status.protocolError, Status.protocolUnreachable, Status.tftpError, Status.volumeCorrupted -> io.ErrorKind.Other
        Status.badBufferSize, Status.invalidLanguage -> io.ErrorKind.InvalidData
        Status.aborted -> io.ErrorKind.ConnectionAborted
        Status.accessDenied -> io.ErrorKind.PermissionDenied
        Status.bufferTooSmall -> io.ErrorKind.FileTooLarge
        Status.connectionRefused -> io.ErrorKind.ConnectionRefused
        Status.connectionReset -> io.ErrorKind.ConnectionReset
        Status.endOfFile -> io.ErrorKind.UnexpectedEof
        Status.hostUnreachable -> io.ErrorKind.HostUnreachable
        Status.invalidParameter -> io.ErrorKind.InvalidInput
        Status.ipAddressConflict -> io.ErrorKind.AddrInUse
        Status.networkUnreachable -> io.ErrorKind.NetworkUnreachable
        Status.noResponse -> io.ErrorKind.HostUnreachable
        Status.notFound -> io.ErrorKind.NotFound
        Status.notReady -> io.ErrorKind.ResourceBusy
        Status.outOfResources -> io.ErrorKind.OutOfMemory
        Status.securityViolation -> io.ErrorKind.PermissionDenied
        Status.timeout -> io.ErrorKind.TimedOut
        Status.unsupported -> io.ErrorKind.Unsupported
        Status.volumeFull -> io.ErrorKind.StorageFull
        Status.writeProtected -> io.ErrorKind.ReadOnlyFilesystem
        else -> io.ErrorKind.Uncategorized
    }

public fun formatError(errno: RawOsError, f: StringBuilder) {
    // Keep the List in Alphabetical Order
    // The Messages are taken from UEFI Specification Appendix D - Status Codes
    val msg =
        when (Status.fromUsize(errno)) {
            Status.aborted -> "The operation was aborted."
            Status.accessDenied -> "Access was denied."
            Status.alreadyStarted -> "The protocol has already been started."
            Status.badBufferSize -> "The buffer was not the proper size for the request."
            Status.bufferTooSmall -> "The buffer is not large enough to hold the requested data. The required buffer size is returned in the appropriate parameter when this error occurs."
            Status.compromisedData -> "The security status of the data is unknown or compromised and the data must be updated or replaced to restore a valid security status."
            Status.connectionFin -> "The receiving operation fails because the communication peer has closed the connection and there is no more data in the receive buffer of the instance."
            Status.connectionRefused -> "The receiving or transmission operation fails because this connection is refused."
            Status.connectionReset -> "The connect fails because the connection is reset either by instance itself or the communication peer."
            Status.crcError -> "A CRC error was detected."
            Status.deviceError -> "The physical device reported an error while attempting the operation."
            Status.endOfFile -> "The end of the file was reached."
            Status.endOfMedia -> "Beginning or end of media was reached"
            Status.hostUnreachable -> "The remote host is not reachable."
            Status.httpError -> "A HTTP error occurred during the network operation."
            Status.icmpError -> "An ICMP error occurred during the network operation."
            Status.incompatibleVersion -> "The function encountered an internal version that was incompatible with a version requested by the caller."
            Status.invalidLanguage -> "The language specified was invalid."
            Status.invalidParameter -> "A parameter was incorrect."
            Status.ipAddressConflict -> "There is an address conflict address allocation"
            Status.loadError -> "The image failed to load."
            Status.mediaChanged -> "The medium in the device has changed since the last access."
            Status.networkUnreachable -> "The network containing the remote host is not reachable."
            Status.noMapping -> "A mapping to a device does not exist."
            Status.noMedia -> "The device does not contain any medium to perform the operation."
            Status.noResponse -> "The server was not found or did not respond to the request."
            Status.notFound -> "The item was not found."
            Status.notReady -> "There is no data pending upon return."
            Status.notStarted -> "The protocol has not been started."
            Status.outOfResources -> "A resource has run out."
            Status.protocolError -> "A protocol error occurred during the network operation."
            Status.protocolUnreachable -> "An ICMP protocol unreachable error is received."
            Status.securityViolation -> "The function was not performed due to a security violation."
            Status.tftpError -> "A TFTP error occurred during the network operation."
            Status.timeout -> "The timeout time expired."
            Status.unsupported -> "The operation is not supported."
            Status.volumeFull -> "There is no more space on the file system."
            Status.volumeCorrupted -> "An inconstancy was detected on the file system causing the operating to fail."
            Status.writeProtected -> "The device cannot be written to."
            else -> { return f.append("Status: $errno") }
        }
    f.append(msg)
}
