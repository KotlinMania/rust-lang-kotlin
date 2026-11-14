// port-lint: source os/fortanix_sgx/io.rs
package io.github.kotlinmania.rustlang.std.os.fortanixsgx.io

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.net.connection.motor.Socket
import io.github.kotlinmania.rustlang.std.sys.abi.usercalls.raw.Fd as RawFd

// SGX-specific extensions to general I/O primitives
//
// SGX file descriptors behave differently from Unix file descriptors. See the
// description of [TryIntoRawFd] for more details.

/**
 * A trait to extract the raw SGX file descriptor from an underlying
 * object.
 */
public interface AsRawFd {
    /**
     * Extracts the raw file descriptor.
     *
     * This method does **not** pass ownership of the raw file descriptor
     * to the caller. The descriptor is only guaranteed to be valid while
     * the original object has not yet been destroyed.
     */
    public fun asRawFd(): RawFd
}

/**
 * A trait to express the ability to construct an object from a raw file
 * descriptor.
 */
public interface FromRawFd<T, Metadata> {
    /**
     * Constructs a new instance of `Self` from the given raw file
     * descriptor and metadata.
     *
     * This function is typically used to **consume ownership** of the
     * specified file descriptor. When used in this way, the returned object
     * will take responsibility for closing it when the object goes out of
     * scope.
     *
     * However, consuming ownership is not strictly required. Use a
     * [From.OwnedFd.from] implementation for an API which strictly
     * consumes ownership.
     *
     * # Safety
     *
     * The `fd` passed in must be an [owned file descriptor][io-safety];
     * in particular, it must be open.
     *
     * [io-safety]: io#io-safety
     */
    public fun fromRawFd(fd: RawFd, metadata: Metadata): T
}

/**
 * A trait to express the ability to consume an object and acquire ownership of
 * its raw file descriptor.
 */
public interface TryIntoRawFd<T> {
    /**
     * Consumes this object, returning the raw underlying file descriptor, if
     * this object is not cloned.
     *
     * This function **transfers ownership** of the underlying file descriptor
     * to the caller. Callers are then the unique owners of the file descriptor
     * and must close the descriptor once it's no longer needed.
     *
     * Unlike other platforms, on SGX, the file descriptor is shared between
     * all clones of an object. To avoid race conditions, this function will
     * only return `Ok` when called on the final clone.
     */
    public fun tryIntoRawFd(): Result<RawFd, T>
}

public fun TcpStream.asRawFd(): RawFd = this.asInner().asInner().asInner().asInner()

public fun TcpListener.asRawFd(): RawFd = this.asInner().asInner().asInner().asInner()

/**
 * Metadata for `TcpStream`.
 */
public open class TcpStreamMetadata(
    /**
     * Local address of the TCP stream
     */
    public var localAddr: String?,
    /**
     * Peer address of the TCP stream
     */
    public var peerAddr: String?,
)

public fun fromRawFdTcpStream(fd: RawFd, metadata: TcpStreamMetadata): TcpStream {
    val fd1 = FileDesc.fromInner(fd)
    val socket = Socket.fromInner(Pair(fd1, metadata.localAddr))
    return net.TcpStream.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpStream.fromInner(Pair(socket, metadata.peerAddr)))
}

/**
 * Metadata for `TcpListener`.
 */
public open class TcpListenerMetadata(
    /**
     * Local address of the TCP listener
     */
    public var localAddr: String?,
)

public fun fromRawFdTcpListener(fd: RawFd, metadata: TcpListenerMetadata): TcpListener {
    val fd1 = FileDesc.fromInner(fd)
    val socket = Socket.fromInner(Pair(fd1, metadata.localAddr))
    return net.TcpListener.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpListener.fromInner(socket))
}

public fun TcpStream.tryIntoRawFd(): Result<RawFd, TcpStream> {
    val (socket, peerAddr) = this.intoInner().intoInner()
    return run {
        val matchVal = socket.tryIntoInner()
        when (matchVal) { is Result.Ok<*> -> {
            val fd = matchVal.value
            Result.Ok(fd.intoInner())
        }; is Result.Err<*> -> {
            val socket1 = matchVal.error
            val sys = io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpStream.fromInner(Pair(socket1, peerAddr))
            Result.Err(net.TcpStream.fromInner(sys))
        } }
    }
}

public fun TcpListener.tryIntoRawFd(): Result<RawFd, TcpListener> =
    run {
        val matchVal = this.intoInner().intoInner().tryIntoInner()
        when (matchVal) { is Result.Ok<*> -> {
            val fd = matchVal.value
            Result.Ok(fd.intoInner())
        }; is Result.Err<*> -> {
            val socket = matchVal.error
            val sys = io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpListener.fromInner(socket)
            Result.Err(net.TcpListener.fromInner(sys))
        } }
    }
