// port-lint: source os/windows/io/raw.rs
package io.github.kotlinmania.rustlang.std.os.windows.io

import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.fs.File
import io.github.kotlinmania.rustlang.io.pipe.PipeReader
import io.github.kotlinmania.rustlang.io.pipe.PipeWriter
import io.github.kotlinmania.rustlang.io.stdio.Stderr
import io.github.kotlinmania.rustlang.io.stdio.StderrLock
import io.github.kotlinmania.rustlang.io.stdio.Stdin
import io.github.kotlinmania.rustlang.io.stdio.StdinLock
import io.github.kotlinmania.rustlang.io.stdio.Stdout
import io.github.kotlinmania.rustlang.io.stdio.StdoutLock
import io.github.kotlinmania.rustlang.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.net.udp.UdpSocket
import io.github.kotlinmania.rustlang.os.windows.io.OwnedHandle
import io.github.kotlinmania.rustlang.os.windows.io.OwnedSocket
import io.github.kotlinmania.rustlang.os.windows.raw.Handle
import io.github.kotlinmania.rustlang.os.windows.raw.Socket
import io.github.kotlinmania.rustlang.sys.FromInner

// Windows-specific extensions to general I/O primitives.

/**
 * Raw HANDLEs.
 */
public typealias RawHandle = Handle

/**
 * Raw SOCKETs.
 */
public typealias RawSocket = Socket

/**
 * Extracts raw handles.
 */
public interface AsRawHandle {
    /**
     * Extracts the raw handle.
     *
     * This function is typically used to **borrow** an owned handle.
     * When used in this way, this method does **not** pass ownership of the
     * raw handle to the caller, and the handle is only guaranteed
     * to be valid while the original object has not yet been destroyed.
     *
     * This function may return null, such as when called on [Stdin],
     * [Stdout], or [Stderr] when the console is detached.
     *
     * However, borrowing is not strictly required. See [AsHandle.asHandle]
     * for an API which strictly borrows a handle.
     *
     * [Stdin]: io.Stdin
     * [Stdout]: io.Stdout
     * [Stderr]: io.Stderr
     */
    public fun asRawHandle(): RawHandle
}

/**
 * Constructs I/O objects from raw handles.
 */
public interface FromRawHandle<T> {
    /**
     * Constructs a new I/O object from the specified raw handle.
     *
     * This function is typically used to **consume ownership** of the handle
     * given, passing responsibility for closing the handle to the returned
     * object. When used in this way, the returned object
     * will take responsibility for closing it when the object goes out of
     * scope.
     *
     * However, consuming ownership is not strictly required. Use a
     * `From<OwnedHandle>.from` implementation for an API which strictly
     * consumes ownership.
     *
     * # Safety
     *
     * The `handle` passed in must:
     *   - be an [owned handle][io-safety]; in particular, it must be open.
     *   - be a handle for a resource that may be freed via [CloseHandle]
     *     (as opposed to `RegCloseKey` or other close functions).
     *
     * Note that the handle *may* have the value `INVALID_HANDLE_VALUE` (-1),
     * which is sometimes a valid handle value. See [here] for the full story.
     *
     * [CloseHandle]: https://docs.microsoft.com/en-us/windows/win32/api/handleapi/nf-handleapi-closehandle
     * [here]: https://devblogs.microsoft.com/oldnewthing/20040302-00/?p=40443
     * [io-safety]: io#io-safety
     */
    public fun fromRawHandle(handle: RawHandle): T
}

/**
 * A trait to express the ability to consume an object and acquire ownership of
 * its raw `HANDLE`.
 */
public interface IntoRawHandle {
    /**
     * Consumes this object, returning the raw underlying handle.
     *
     * This function is typically used to **transfer ownership** of the underlying
     * handle to the caller. When used in this way, callers are then the unique
     * owners of the handle and must close it once it's no longer needed.
     *
     * However, transferring ownership is not strictly required. Use a
     * `Into<OwnedHandle>.into` implementation for an API which strictly
     * transfers ownership.
     */
    public fun intoRawHandle(): RawHandle
}

public fun File.asRawHandle(): RawHandle = ((this.asInner().asRawHandle() as Any?) as RawHandle)

public fun Stdin.asRawHandle(): RawHandle = stdioHandle(MutPtr(C.GetStdHandle(C.STD_INPUT_HANDLE).rawAddress))

public fun Stdout.asRawHandle(): RawHandle = stdioHandle(MutPtr(C.GetStdHandle(C.STD_OUTPUT_HANDLE).rawAddress))

public fun Stderr.asRawHandle(): RawHandle = stdioHandle(MutPtr(C.GetStdHandle(C.STD_ERROR_HANDLE).rawAddress))

public fun StdinLock.asRawHandle(): RawHandle = stdioHandle(MutPtr(C.GetStdHandle(C.STD_INPUT_HANDLE).rawAddress))

public fun StdoutLock.asRawHandle(): RawHandle = stdioHandle(MutPtr(C.GetStdHandle(C.STD_OUTPUT_HANDLE).rawAddress))

public fun StderrLock.asRawHandle(): RawHandle = stdioHandle(MutPtr(C.GetStdHandle(C.STD_ERROR_HANDLE).rawAddress))

// Translate a handle returned from `GetStdHandle` into a handle to return to
// the user.
public fun stdioHandle(raw: RawHandle): RawHandle {
    // `GetStdHandle` isn't expected to actually fail, so when it returns
    // `INVALID_HANDLE_VALUE`, it means we were launched from a parent which
    // didn't provide us with stdio handles, such as a parent with a detached
    // console. In that case, return null to the user, which is consistent
    // with what they'd get in the parent, and which avoids the problem that
    // `INVALID_HANDLE_VALUE` aliases the current process handle.
    return if (raw == C.INVALID_HANDLE_VALUE) {
        MutPtr(0L)
    } else {
        raw
    }
}

public fun fromRawHandleFile(handle: RawHandle): File =
    run {
        val handle1 = MutPtr(handle.rawAddress)
        File.fromInner(io.github.kotlinmania.rustlang.sys.fs.unix.File.fromInner(FromInner.fromInner(OwnedHandle.FromRawHandle(handle1))))
    }

public fun File.intoRawHandle(): RawHandle = mutPtrFromRustCast(this.intoInner().intoRawHandle())

/**
 * Extracts raw sockets.
 */
public interface AsRawSocket {
    /**
     * Extracts the raw socket.
     *
     * This function is typically used to **borrow** an owned socket.
     * When used in this way, this method does **not** pass ownership of the
     * raw socket to the caller, and the socket is only guaranteed
     * to be valid while the original object has not yet been destroyed.
     *
     * However, borrowing is not strictly required. See [AsSocket.asSocket]
     * for an API which strictly borrows a socket.
     */
    public fun asRawSocket(): RawSocket
}

/**
 * Creates I/O objects from raw sockets.
 */
public interface FromRawSocket<T> {
    /**
     * Constructs a new I/O object from the specified raw socket.
     *
     * This function is typically used to **consume ownership** of the socket
     * given, passing responsibility for closing the socket to the returned
     * object. When used in this way, the returned object
     * will take responsibility for closing it when the object goes out of
     * scope.
     *
     * However, consuming ownership is not strictly required. Use a
     * `From<OwnedSocket>.from` implementation for an API which strictly
     * consumes ownership.
     *
     * # Safety
     *
     * The `socket` passed in must:
     *   - be an [owned socket][io-safety]; in particular, it must be open.
     *   - be a socket that may be freed via [closesocket].
     *
     * [closesocket]: https://docs.microsoft.com/en-us/windows/win32/api/winsock2/nf-winsock2-closesocket
     * [io-safety]: io#io-safety
     */
    public fun fromRawSocket(sock: RawSocket): T
}

/**
 * A trait to express the ability to consume an object and acquire ownership of
 * its raw `SOCKET`.
 */
public interface IntoRawSocket {
    /**
     * Consumes this object, returning the raw underlying socket.
     *
     * This function is typically used to **transfer ownership** of the underlying
     * socket to the caller. When used in this way, callers are then the unique
     * owners of the socket and must close it once it's no longer needed.
     *
     * However, transferring ownership is not strictly required. Use a
     * `Into<OwnedSocket>.into` implementation for an API which strictly
     * transfers ownership.
     */
    public fun intoRawSocket(): RawSocket
}

public fun TcpStream.asRawSocket(): RawSocket = this.asInner().socket().asRawSocket()

public fun TcpListener.asRawSocket(): RawSocket = this.asInner().socket().asRawSocket()

public fun UdpSocket.asRawSocket(): RawSocket = this.asInner().socket().asRawSocket()

public fun fromRawSocketTcpStream(sock: RawSocket): TcpStream =
    run {
        val sock1 = io.github.kotlinmania.rustlang.sys.net.connection.wasip1.Socket.fromInner(OwnedSocket.FromRawSocket(sock))
        TcpStream.fromInner(io.github.kotlinmania.rustlang.sys.net.connection.socket.TcpStream.fromInner(sock1))
    }

public fun fromRawSocketTcpListener(sock: RawSocket): TcpListener =
    run {
        val sock1 = io.github.kotlinmania.rustlang.sys.net.connection.wasip1.Socket.fromInner(OwnedSocket.FromRawSocket(sock))
        TcpListener.fromInner(io.github.kotlinmania.rustlang.sys.net.connection.socket.TcpListener.fromInner(sock1))
    }

public fun fromRawSocketUdpSocket(sock: RawSocket): UdpSocket =
    run {
        val sock1 = io.github.kotlinmania.rustlang.sys.net.connection.wasip1.Socket.fromInner(OwnedSocket.FromRawSocket(sock))
        UdpSocket.fromInner(io.github.kotlinmania.rustlang.sys.net.connection.socket.UdpSocket.fromInner(sock1))
    }

public fun TcpStream.intoRawSocket(): RawSocket = this.intoInner().intoSocket().intoInner().intoRawSocket()

public fun TcpListener.intoRawSocket(): RawSocket = this.intoInner().intoSocket().intoInner().intoRawSocket()

public fun UdpSocket.intoRawSocket(): RawSocket = this.intoInner().intoSocket().intoInner().intoRawSocket()

public fun PipeReader.asRawHandle(): RawHandle = this.value.asRawHandle()

public fun fromRawHandlePipeReader(rawHandle: RawHandle): PipeReader = PipeReader.fromInner(FromRawHandle.FromRawHandle(rawHandle))

public fun PipeReader.intoRawHandle(): RawHandle = this.value.intoRawHandle()

public fun PipeWriter.asRawHandle(): RawHandle = this.value.asRawHandle()

public fun fromRawHandlePipeWriter(rawHandle: RawHandle): PipeWriter = PipeWriter.fromInner(FromRawHandle.FromRawHandle(rawHandle))

public fun PipeWriter.intoRawHandle(): RawHandle = this.value.intoRawHandle()
