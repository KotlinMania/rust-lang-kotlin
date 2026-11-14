// port-lint: source os/windows/io/socket.rs
package io.github.kotlinmania.rustlang.std.os.windows.io

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.Rc
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.net.udp.UdpSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.AsRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.FromRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.Handle
import io.github.kotlinmania.rustlang.std.os.windows.raw.IntoRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.RawSocket
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.HANDLE_FLAG_INHERIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.WSA_FLAG_NO_HANDLE_INHERIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.WSA_FLAG_OVERLAPPED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.INVALID_SOCKET
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEINVAL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEPROTOTYPE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.closesocket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getCurrentProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setHandleInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.wSADuplicateSocketW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.wSAGetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.wSASocketW

// Owned and borrowed OS sockets.

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

// The max here is -2, in two's complement. -1 is `INVALID_SOCKET`.
public typealias ValidRawSocket = NotAllOnes<RawSocket>

/**
 * A borrowed socket.
 *
 * This has a scope parameter to tie it to the scope of something that
 * owns the socket.
 *
 * This uses `repr(transparent)` and has the representation of a host socket,
 * so it can be used in FFI in places where a socket is passed as an argument,
 * it is not captured or consumed, and it never has the value
 * `INVALID_SOCKET`.
 *
 * This type's `.toOwned()` implementation returns another `BorrowedSocket`
 * rather than an `OwnedSocket`. It just makes a trivial copy of the raw
 * socket, which is then borrowed under the same scope.
 */
public open class BorrowedSocket internal constructor(
    socketCtorArg: ValidRawSocket,
    public var phantom: Unit,
) : AsRawSocket, AsSocket {
    public var socket: ValidRawSocket = socketCtorArg
        internal set

    /**
     * Creates a new `OwnedSocket` instance that shares the same underlying
     * object as the existing `BorrowedSocket` instance.
     */
    public open fun tryCloneToOwned(): Result<OwnedSocket> {
        val info = null
        val result = wSADuplicateSocketW(this.asRawSocket().toULong(), getCurrentProcessId(), info)
        when (val tryResult = cvt(result)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val socket = wSASocketW(info.iAddressFamily, info.iSocketType, info.iProtocol, info, 0u, WSA_FLAG_OVERLAPPED or WSA_FLAG_NO_HANDLE_INHERIT)
        return if (socket != INVALID_SOCKET) {
            Result.Ok(OwnedSocket.fromRawSocket(((socket as Any?) as RawSocket), fieldLayout = ObjectSlotLayout<NotAllOnes<RawSocket>>()))
        } else {
            val error = wSAGetLastError()
            if (error != WSAEPROTOTYPE && error != WSAEINVAL) {
                return Result.Err(io.Error.fromRawOsError(error))
            }
            val socket1 = wSASocketW(info.iAddressFamily, info.iSocketType, info.iProtocol, info, 0u, WSA_FLAG_OVERLAPPED)
            if (socket1 == INVALID_SOCKET) {
                return Result.Err(lastError())
            }
            run {
                val socket2 = OwnedSocket.fromRawSocket(((socket1 as Any?) as RawSocket), fieldLayout = ObjectSlotLayout<NotAllOnes<RawSocket>>())
                when (val tryResult = socket2.setNoInherit()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                Result.Ok(socket2)
            }
        }
    }

    public override fun asRawSocket(): RawSocket = this.socket.asInner()

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "BorrowedSocket").field("socket", this.socket).finish()
    }

    public override fun asSocket(): BorrowedSocket = this

    public companion object {
        public operator fun invoke(
            socket: ValidRawSocket,
            phantom: Unit,
        ): BorrowedSocket = BorrowedSocket(
            socketCtorArg = socket,
            phantom = phantom,
        )

        /**
         * Returns a `BorrowedSocket` holding the given raw socket.
         *
         * # Safety
         *
         * The resource pointed to by `socket` must remain open for the duration of
         * the returned `BorrowedSocket`, and it must not have the value
         * `INVALID_SOCKET`.
         */
        public fun borrowRaw(socket: RawSocket): BorrowedSocket = BorrowedSocket(socket = ValidRawSocket.new(socket).expect("socket != -1"), phantom = Unit)
    }
}

/**
 * An owned socket.
 *
 * This closes the socket on drop.
 *
 * This uses `repr(transparent)` and has the representation of a host socket,
 * so it can be used in FFI in places where a socket is passed as a consumed
 * argument or returned as an owned value, and it never has the value
 * `INVALID_SOCKET`.
 */
public open class OwnedSocket internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ValidRawSocket>,
) : AsRawSocket, IntoRawSocket, FromRawSocket<OwnedSocket>, AsSocket {
    public var socket: ValidRawSocket
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(socket: ValidRawSocket, fieldLayout: TypeLayout<ValidRawSocket>) : this(allocateNativeCell(socket, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<OwnedSocket> = TransparentTypeLayout(fieldLayout, { ptr, layout -> OwnedSocket(ptr, layout) }, { it.socket })

    /**
     * Creates a new `OwnedSocket` instance that shares the same underlying
     * object as the existing `OwnedSocket` instance.
     */
    public open fun tryClone(): Result<OwnedSocket> = this.asSocket().tryCloneToOwned()

    // Note(strictProvenanceMagic): we defined RawSocket to be a u64 ;-;
    internal open fun setNoInherit(): Result<Unit> = io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt(setHandleInformation(((this.asRawSocket() as Any?) as Handle), HANDLE_FLAG_INHERIT, 0u)).map(drop)

    public override fun asRawSocket(): RawSocket = this.socket.asInner()

    public override fun intoRawSocket(): RawSocket = this.socket.asInner()

    public open fun drop() {
        run {
            closesocket(this.socket.asInner().toULong())
        }
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "OwnedSocket").field("socket", this.socket).finish()
    }

    public override fun asSocket(): BorrowedSocket {
        // Safety: `OwnedSocket` and `BorrowedSocket` have the same validity
        // invariants, and the `BorrowedSocket` is bounded by the scope
        // of `self`.
        return BorrowedSocket.borrowRaw(this.asRawSocket())
    }

    public companion object {
        public operator fun invoke(socket: ValidRawSocket, fieldLayout: TypeLayout<ValidRawSocket>): OwnedSocket = OwnedSocket(socket, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ValidRawSocket>): OwnedSocket = OwnedSocket(fieldStorage, fieldLayout)

        public fun fromRawSocket(socket: RawSocket, fieldLayout: TypeLayout<NotAllOnes<RawSocket>>): OwnedSocket = OwnedSocket(socket = ValidRawSocket.new(socket).expect("socket != -1"), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`TcpStream`](net.TcpStream)'s socket.
         */
        public fun from(tcpStream: TcpStream, fieldLayout: TypeLayout<NotAllOnes<RawSocket>>): OwnedSocket = OwnedSocket.fromRawSocket(tcpStream.intoRawSocket(), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`TcpListener`](net.TcpListener)'s socket.
         */
        public fun from(tcpListener: TcpListener, fieldLayout: TypeLayout<NotAllOnes<RawSocket>>): OwnedSocket = OwnedSocket.fromRawSocket(tcpListener.intoRawSocket(), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`UdpSocket`](net.UdpSocket)'s underlying socket.
         */
        public fun from(udpSocket: UdpSocket, fieldLayout: TypeLayout<NotAllOnes<RawSocket>>): OwnedSocket = OwnedSocket.fromRawSocket(udpSocket.intoRawSocket(), fieldLayout = fieldLayout)
    }
}

/**
 * Returns the last error from the Windows socket interface.
 */
public fun lastError(): Error = io.Error.fromRawOsError(wSAGetLastError())

/**
 * A trait to borrow the socket from an underlying object.
 */
public interface AsSocket {
    /**
     * Borrows the socket.
     */
    public fun asSocket(): BorrowedSocket
}

/**
 * This impl allows implementing traits that require `AsSocket` on Arc.
 * ```kotlin
 * import std.net.UdpSocket
 *
 * interface MyTrait : AsSocket
 * ```
 */
public fun <T : AsSocket> Arc<T>.asSocket(): BorrowedSocket = this.asSocket()

public fun <T : AsSocket> Rc.Rc<T>.asSocket(): BorrowedSocket = this.asSocket()

public fun <T : AsSocket> Rc.UniqueRc<T>.asSocket(): BorrowedSocket = this.asSocket()

public fun TcpStream.asSocket(): BorrowedSocket = BorrowedSocket.borrowRaw(this.asRawSocket())

public fun fromTcpStream(owned: OwnedSocket): TcpStream = TcpStream.fromRawSocket(owned.intoRawSocket())

public fun TcpListener.asSocket(): BorrowedSocket = BorrowedSocket.borrowRaw(this.asRawSocket())

public fun fromTcpListener(owned: OwnedSocket): TcpListener = TcpListener.fromRawSocket(owned.intoRawSocket())

public fun UdpSocket.asSocket(): BorrowedSocket = BorrowedSocket.borrowRaw(this.asRawSocket())

public fun fromUdpSocket(owned: OwnedSocket): UdpSocket = UdpSocket.fromRawSocket(owned.intoRawSocket())
