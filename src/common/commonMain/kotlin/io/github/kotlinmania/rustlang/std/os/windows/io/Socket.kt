// port-lint: source os/windows/io/socket.rs
package io.github.kotlinmania.rustlang.std.os.windows.io

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.Rc
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.net.udp.UdpSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.AsRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.FromRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.IntoRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.raw.RawSocket
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt

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

    public open fun clone(): BorrowedSocket = BorrowedSocket(socket, phantom)

    /**
     * Creates a new `OwnedSocket` instance that shares the same underlying
     * object as the existing `BorrowedSocket` instance.
     */
    public open fun tryCloneToOwned(): Result<OwnedSocket, Error> {
        val info = MaybeUninit.zeroed(UnresolvedTypeLayout<Result<OwnedSocket, Error>>()).assumeInit()
        val result = C.WSADuplicateSocketW(((this.asRawSocket() as Any?) as C.Socket), C.GetCurrentProcessId(), info)
        when (val tryResult = cvt(result)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<OwnedSocket, Error>(tryResult.error) }
        val socket = C.WSASocketW(info.iAddressFamily, info.iSocketType, info.iProtocol, info, 0, C.WSA_FLAG_OVERLAPPED or C.WSA_FLAG_NO_HANDLE_INHERIT)
        return if (socket != C.INVALID_SOCKET) {
            Result.Ok<OwnedSocket, Error>(OwnedSocket.fromRawSocket(((socket as Any?) as RawSocket), fieldLayout = UnresolvedTypeLayout<NotAllOnes<RawSocket>>()))
        } else {
            val error = C.WSAGetLastError()
            if (error != C.WSAEPROTOTYPE && error != C.WSAEINVAL) {
                return Result.Err<OwnedSocket, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.fromRawOsError(error))
            }
            val socket1 = C.WSASocketW(info.iAddressFamily, info.iSocketType, info.iProtocol, info, 0, C.WSA_FLAG_OVERLAPPED)
            if (socket1 == C.INVALID_SOCKET) {
                return Result.Err<OwnedSocket, Error>(lastError())
            }
            run {
                val socket2 = OwnedSocket.fromRawSocket(((socket1 as Any?) as RawSocket), fieldLayout = UnresolvedTypeLayout<NotAllOnes<RawSocket>>())
                when (val tryResult = socket2.setNoInherit()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<OwnedSocket, Error>(tryResult.error) }
                Result.Ok<OwnedSocket, Error>(socket2)
            }
        }
    }

    public override fun asRawSocket(): RawSocket = this.socket.asInner()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "BorrowedSocket").field("socket", this.socket).finish()

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
    public val fieldLayout: TypeLayout<ValidRawSocket> = UnresolvedTypeLayout(),
) : AsRawSocket, IntoRawSocket, FromRawSocket<OwnedSocket>, AsSocket {
    public var socket: ValidRawSocket
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(socket: ValidRawSocket, fieldLayout: TypeLayout<ValidRawSocket> = UnresolvedTypeLayout()) : this(allocateNativeCell(socket, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<OwnedSocket> = TransparentTypeLayout(fieldLayout, { ptr, layout -> OwnedSocket(ptr, layout) }, { it.socket })

    /**
     * Creates a new `OwnedSocket` instance that shares the same underlying
     * object as the existing `OwnedSocket` instance.
     */
    public open fun tryClone(): Result<OwnedSocket, Error> = this.asSocket().tryCloneToOwned()

    // Note(strictProvenanceMagic): we defined RawSocket to be a u64 ;-;
    public open fun setNoInherit(): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt(C.SetHandleInformation(((this.asRawSocket() as Any?) as C.Handle), C.HANDLE_FLAG_INHERIT, 0)).map({ drop(it) })

    public override fun asRawSocket(): RawSocket = this.socket.asInner()

    public override fun intoRawSocket(): RawSocket = this.socket.asInner()

    public open fun drop() {
        run {
            C.closesocket(((this.socket.asInner() as Any?) as C.Socket))
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "OwnedSocket").field("socket", this.socket).finish()

    public override fun asSocket(): BorrowedSocket {
        // Safety: `OwnedSocket` and `BorrowedSocket` have the same validity
        // invariants, and the `BorrowedSocket` is bounded by the scope
        // of `self`.
        return BorrowedSocket.borrowRaw(this.asRawSocket())
    }

    public companion object {
        public operator fun invoke(socket: ValidRawSocket, fieldLayout: TypeLayout<ValidRawSocket> = UnresolvedTypeLayout()): OwnedSocket = OwnedSocket(socket, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ValidRawSocket> = UnresolvedTypeLayout()): OwnedSocket = OwnedSocket(fieldStorage, fieldLayout)

        public fun fromRawSocket(socket: RawSocket, fieldLayout: TypeLayout<NotAllOnes<RawSocket>> = UnresolvedTypeLayout()): OwnedSocket = OwnedSocket(socket = ValidRawSocket.new(socket).expect("socket != -1"), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`TcpStream`](net.TcpStream)'s socket.
         */
        public fun from(tcpStream: TcpStream, fieldLayout: TypeLayout<NotAllOnes<RawSocket>> = UnresolvedTypeLayout()): OwnedSocket = OwnedSocket.fromRawSocket(tcpStream.intoRawSocket(), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`TcpListener`](net.TcpListener)'s socket.
         */
        public fun from(tcpListener: TcpListener, fieldLayout: TypeLayout<NotAllOnes<RawSocket>> = UnresolvedTypeLayout()): OwnedSocket = OwnedSocket.fromRawSocket(tcpListener.intoRawSocket(), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`UdpSocket`](net.UdpSocket)'s underlying socket.
         */
        public fun from(udpSocket: UdpSocket, fieldLayout: TypeLayout<NotAllOnes<RawSocket>> = UnresolvedTypeLayout()): OwnedSocket = OwnedSocket.fromRawSocket(udpSocket.intoRawSocket(), fieldLayout = fieldLayout)
    }
}

/**
 * Returns the last error from the Windows socket interface.
 */
public fun lastError(): io.github.kotlinmania.rustlang.core.io.error.Error = io.github.kotlinmania.rustlang.core.io.error.Error.fromRawOsError(C.WSAGetLastError())

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
public fun <T : AsSocket> Arc<T, Global>.asSocket(): BorrowedSocket = this.asSocket()

public fun <T : AsSocket> Rc.Rc<T, Global>.asSocket(): BorrowedSocket = this.asSocket()

public fun <T : AsSocket> Rc.UniqueRc<T>.asSocket(): BorrowedSocket = this.asSocket()

public fun TcpStream.asSocket(): BorrowedSocket = BorrowedSocket.borrowRaw(this.asRawSocket())

public fun fromTcpStream(owned: OwnedSocket): TcpStream = TcpStream.fromRawSocket(owned.intoRawSocket())

public fun TcpListener.asSocket(): BorrowedSocket = BorrowedSocket.borrowRaw(this.asRawSocket())

public fun fromTcpListener(owned: OwnedSocket): TcpListener = TcpListener.fromRawSocket(owned.intoRawSocket())

public fun UdpSocket.asSocket(): BorrowedSocket = BorrowedSocket.borrowRaw(this.asRawSocket())

public fun fromUdpSocket(owned: OwnedSocket): UdpSocket = UdpSocket.fromRawSocket(owned.intoRawSocket())
