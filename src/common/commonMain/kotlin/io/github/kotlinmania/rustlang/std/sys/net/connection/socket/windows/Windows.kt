// port-lint: source sys/net/connection/socket/windows.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.io.ErrorKind
import io.github.kotlinmania.rustlang.io.constError
import io.github.kotlinmania.rustlang.net.Shutdown
import io.github.kotlinmania.rustlang.os.raw.CInt
import io.github.kotlinmania.rustlang.os.raw.CUint
import io.github.kotlinmania.rustlang.os.raw.CUlong
import io.github.kotlinmania.rustlang.os.raw.CUshort
import io.github.kotlinmania.rustlang.os.windows.io.AsRawSocket
import io.github.kotlinmania.rustlang.os.windows.io.AsSocket
import io.github.kotlinmania.rustlang.os.windows.io.BorrowedSocket
import io.github.kotlinmania.rustlang.os.windows.io.FromRawSocket
import io.github.kotlinmania.rustlang.os.windows.io.IntoRawSocket
import io.github.kotlinmania.rustlang.os.windows.io.OwnedSocket
import io.github.kotlinmania.rustlang.os.windows.io.RawSocket
import io.github.kotlinmania.rustlang.os.windows.io.lastError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.sys.AsInner
import io.github.kotlinmania.rustlang.sys.FromInner
import io.github.kotlinmania.rustlang.sys.IntoInner
import io.github.kotlinmania.rustlang.sys.c
import io.github.kotlinmania.rustlang.sys.net.connection.socket.MAX_SEND_LEN
import io.github.kotlinmania.rustlang.sys.net.connection.socket.socketAddrFromC
import io.github.kotlinmania.rustlang.sys.net.connection.socket.socketAddrToC
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.AddressFamily
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.SOL_SOCKET
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.connect
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.getsockopt
import io.github.kotlinmania.rustlang.sys.pal.windows.c.windowssys.setsockopt
import io.github.kotlinmania.rustlang.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.sys.pal.windows.dur2timeout

public typealias WrlenT = Int

public typealias SocklenT = CInt

public val AF_INET: Int = AF_INET.toInt()

public val AF_INET6: Int = AF_INET6.toInt()

// The following two structs use a union in the generated bindings but
// our cross-platform code expects a normal field so it's redefined here.
// As a consequence, we also need to redefine other structs that use this struct.
public open class InAddr(
    public var sAddr: UInt,
) {
    public open fun clone(): InAddr = InAddr(sAddr)

    public companion object {
        public fun typeLayout(): TypeLayout<InAddr> =
            object : TypeLayout<InAddr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): InAddr =
                    InAddr(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: InAddr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.sAddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InAddr =
                    InAddr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InAddr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.sAddr)
                }
            }
    }
}

public open class In6Addr(
    public var s6Addr: UByteArray,
) {
    public open fun clone(): In6Addr = In6Addr(s6Addr)

    public companion object {
        public fun typeLayout(): TypeLayout<In6Addr> =
            object : TypeLayout<In6Addr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): In6Addr =
                    In6Addr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: In6Addr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.s6Addr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): In6Addr =
                    In6Addr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: In6Addr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.s6Addr)
                }
            }
    }
}

public open class IpMreq(
    public var imrMultiaddr: InAddr,
    public var imrInterface: InAddr,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IpMreq> =
            object : TypeLayout<IpMreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): IpMreq =
                    IpMreq(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: IpMreq) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrInterface)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreq =
                    IpMreq(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreq) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrInterface)
                }
            }
    }
}

public open class Ipv6Mreq(
    public var ipv6mrMultiaddr: In6Addr,
    public var ipv6mrInterface: CUint,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Ipv6Mreq> =
            object : TypeLayout<Ipv6Mreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): Ipv6Mreq =
                    Ipv6Mreq(
                        (In6Addr.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Ipv6Mreq) {
                    (In6Addr.typeLayout()).writeNative(addr, value.ipv6mrMultiaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ipv6mrInterface)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ipv6Mreq =
                    Ipv6Mreq(
                        (In6Addr.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ipv6Mreq) {
                    (In6Addr.typeLayout()).writeBytes(buf, offset, value.ipv6mrMultiaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ipv6mrInterface)
                }
            }
    }
}

public open class SockaddrIn(
    public var sinFamily: AddressFamily,
    public var sinPort: CUshort,
    public var sinAddr: InAddr,
    public var sinZero: UByteArray,
) {
    public open fun clone(): SockaddrIn = SockaddrIn(sinFamily, sinPort, sinAddr, sinZero)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrIn> =
            object : TypeLayout<SockaddrIn> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrIn =
                    SockaddrIn(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrIn) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sinFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sinPort)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.sinAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.sinZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrIn =
                    SockaddrIn(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrIn) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sinFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sinPort)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.sinAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.sinZero)
                }
            }
    }
}

public open class SockaddrIn6(
    public var sin6Family: AddressFamily,
    public var sin6Port: CUshort,
    public var sin6Flowinfo: CUlong,
    public var sin6Addr: In6Addr,
    public var sin6ScopeId: CUlong,
) {
    public open fun clone(): SockaddrIn6 = SockaddrIn6(sin6Family, sin6Port, sin6Flowinfo, sin6Addr, sin6ScopeId)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrIn6> =
            object : TypeLayout<SockaddrIn6> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): SockaddrIn6 =
                    SockaddrIn6(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (In6Addr.typeLayout()).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: SockaddrIn6) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sin6Family)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sin6Port)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.sin6Flowinfo)
                    (In6Addr.typeLayout()).writeNative(addr + 16L, value.sin6Addr)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.sin6ScopeId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrIn6 =
                    SockaddrIn6(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (In6Addr.typeLayout()).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrIn6) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sin6Family)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sin6Port)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.sin6Flowinfo)
                    (In6Addr.typeLayout()).writeBytes(buf, offset + 16, value.sin6Addr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.sin6ScopeId)
                }
            }
    }
}

public fun send(socket: Socket, buf: ConstPtr, len: CInt, flags: CInt): CInt = send(socket, ConstPtr((buf).rawAddress), len, flags)

public fun sendto(socket: Socket, buf: ConstPtr, len: CInt, flags: CInt, addr: ConstPtr, addrlen: CInt): CInt = sendto(socket, ConstPtr((buf).rawAddress), len, flags, addr, addrlen)

public fun getaddrinfo(node: ConstPtr, service: ConstPtr, hints: ConstPtr, res: MutPtr): CInt = getaddrinfo(ConstPtr((node).rawAddress), ConstPtr((service).rawAddress), hints, res)

public open class Socket(
    public var value: OwnedSocket,
) : Read, AsInner<OwnedSocket>, FromInner<OwnedSocket, Socket>, IntoInner<OwnedSocket>, AsSocket, AsRawSocket, IntoRawSocket, FromRawSocket<Socket> {
    public var field0: OwnedSocket
        get() = value
        set(v) {
            value = v
        }

    public open fun connect(addr: SocketAddr): Result<Unit, Error> {
        val (addr1, len) = socketAddrToC(addr)
        val result = connect(this.asRaw(), addr1.asPtr(), len)
        return cvt(result).map({ Unit })
    }

    public open fun pollConnected(timeout: Duration): Result<Unit, Error> {
        if (timeout.asSecs() == 0 && timeout.subsecNanos() == 0) {
            return Result.Err(io.github.kotlinmania.rustlang.io.Error.ZERO_TIMEOUT)
        }
        val timeout1 = c.Timeval(tvSec = (minOf(timeout.asSecs(), Long.MAX_VALUE.toULong()).toLong()).toInt(), tvUsec = (timeout.subsecMicros().toLong()).toInt())
        if (timeout1.tvSec == 0 && timeout1.tvUsec == 0) {
            timeout1.tvUsec = 1
        }
        val fds1 = run {
            val fds = MaybeUninit.zeroed(ObjectSlotLayout<Result<Unit, Error>>()).assumeInit()
            fds.fdCount = 1
            fds.fdArray[0] = this.asRaw()
            fds
        }
        val writefds = fds1
        val errorfds = fds1
        val count = run {
            val result = select(1, MutPtr(0L), writefds, errorfds, timeout1)
            when (val tryResult = cvt(result)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        }
        return when (count) {
            0 -> Result.Err(constError(ErrorKind.TimedOut, "connection timed out"))
            else -> {
                if (writefds.fdCount != 1) {
                    when (val matchVal = when (val tryResult = this.takeError()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
                        null -> {}
                        else -> {
                            val e = matchVal
                            return Result.Err(e)
                        }
                    }
                }
                Result.Ok(Unit)
            }
        }
    }

    public open fun accept(storage: MutPtr, len: MutPtr): Result<Socket, Error> {
        val socket = accept(this.asRaw(), storage, len)
        return when (socket) {
            invalidSocket -> Result.Err(lastError())
            else -> Result.Ok(Socket.fromRaw(socket))
        }
    }

    public open fun duplicate(): Result<Socket, Error> {
        return Result.Ok(Socket(when (val tryResult = this.value.tryClone()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }))
    }

    public open fun recvWithFlags(buf: BorrowedCursor<UByte>, flags: CInt): Result<Unit, Error> {
        // On unix when a socket is shut down all further reads return 0, so we
        // do the same on windows to map a shut down socket to returning EOF.
        val length = minOf(buf.capacity(), Int.MAX_VALUE).toInt()
        val result = recv(this.asRaw(), mutPtrFromRustCast(buf.asMut().asMutPtr()), length, flags)
        return when (result) {
            socketError -> {
                val error = WSAGetLastError()
                if (error == WSAESHUTDOWN) {
                    Result.Ok(Unit)
                } else {
                    Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(error))
                }
            }
            else -> {
                buf.advance(result)
                Result.Ok(Unit)
            }
        }
    }

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> {
        val buf1 = BorrowedBuf.from(buf)
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), 0)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(buf1.len())
    }

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.recvWithFlags(buf, 0)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> {
        // On unix when a socket is shut down all further reads return 0, so we
        // do the same on windows to map a shut down socket to returning EOF.
        val length = minOf(bufs.size, UInt.MAX_VALUE.toInt()).toUInt()
        val localStorageL257C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val nread by localStorageL257C8
        val localStorageL258C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val result = WSARecv(this.asRaw(), (bufs.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, length, RustMutableReference(localStorageL257C8.asMutPtr(), localStorageL257C8.layout), RustMutableReference(localStorageL258C8.asMutPtr(), localStorageL258C8.layout), MutPtr(0L), null)
        return when (result) {
            0 -> Result.Ok(nread)
            else -> {
                val error = WSAGetLastError()
                if (error == WSAESHUTDOWN) {
                    Result.Ok(0)
                } else {
                    Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(error))
                }
            }
        }
    }

    public open fun isReadVectored(): Boolean = true

    public open fun peek(buf: MutableList<UByte>): Result<Int, Error> {
        val buf1 = BorrowedBuf.from(buf)
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), MSG_PEEK)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(buf1.len())
    }

    public open fun recvFromWithFlags(buf: MutableList<UByte>, flags: CInt): Result<Pair<Int, SocketAddr>, Error> {
        val localStorageL301C8 = AddressableLocal(null, ObjectSlotLayout<Any?>())
        val storage by localStorageL301C8
        val addrlen = sizeOfVal(storage)
        val length = minOf(buf.size, MAX_SEND_LEN).toInt()
        // On unix when a socket is shut down all further reads return 0, so we
        // do the same on windows to map a shut down socket to returning EOF.
        val result = recvfrom(this.asRaw(), buf.asMutPtr(), length, flags, (localStorageL301C8.asMutPtr()), addrlen)
        return when (result) {
            socketError -> {
                val error = WSAGetLastError()
                if (error == WSAESHUTDOWN) {
                    Result.Ok(Pair(0, when (val tryResult = socketAddrFromC(storage, addrlen)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }))
                } else {
                    Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(error))
                }
            }
            else -> Result.Ok(Pair(result, when (val tryResult = socketAddrFromC(storage, addrlen)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }))
        }
    }

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.recvFromWithFlags(buf, 0)

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.recvFromWithFlags(buf, MSG_PEEK)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> {
        val length = minOf(bufs.size, UInt.MAX_VALUE.toInt()).toUInt()
        val localStorageL342C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val nwritten by localStorageL342C8
        val result = WSASend(this.asRaw(), ((bufs.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, length, RustMutableReference(localStorageL342C8.asMutPtr(), localStorageL342C8.layout), 0, MutPtr(0L), null)
        return cvt(result).map({ nwritten })
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun setTimeout(dur: Duration?, kind: CInt): Result<Unit, Error> {
        val timeout1 =
            when (dur) {
                null -> 0
                else -> {
                    val dur1 = dur
                    val timeout = dur2timeout(dur1)
                    if (timeout == 0u) {
                        return Result.Err(io.github.kotlinmania.rustlang.io.Error.ZERO_TIMEOUT)
                    }
                    timeout
                }
            }
        return setsockopt(this, SOL_SOCKET, kind, timeout1)
    }

    public open fun timeout(kind: CInt): Result<Duration?, Error> {
        val raw: UInt = when (val tryResult = getsockopt(this, SOL_SOCKET, kind)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return if (raw == 0u) {
            Result.Ok(null)
        } else {
            val secs = raw / 1000u
            val nsec = (raw % 1000u) * 1000000u
            Result.Ok(Duration.new(secs.toULong(), nsec))
        }
    }

    public open fun shutdown(how: Shutdown): Result<Unit, Error> {
        val how1 =
            when (how) {
                Shutdown.Write -> SD_SEND
                Shutdown.Read -> SD_RECEIVE
                Shutdown.Both -> SD_BOTH
            }
        val result = shutdown(this.asRaw(), how1)
        return cvt(result).map({ Unit })
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> {
        val nonblocking1 = nonblocking.toULong()
        val result = ioctlsocket(this.asRaw(), FIONBIO.toInt(), nonblocking1)
        return cvt(result).map({ Unit })
    }

    public open fun setLinger(linger: Duration?): Result<Unit, Error> {
        val linger1 = c.Linger(lOnoff = (if (linger != null) 1u else 0u).toUShort(), lLinger = minOf(linger.unwrapOrDefault().asSecs(), UShort.MAX_VALUE.toULong()).toUShort())
        return setsockopt(this, SOL_SOCKET, SO_LINGER, linger1)
    }

    public open fun linger(): Result<Duration?, Error> {
        val value: Linger = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_LINGER)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok((value.lOnoff != 0u.toUShort()).then({ Duration.fromSecs(value.lLinger.toULong()) }))
    }

    public open fun setKeepalive(keepalive: Boolean): Result<Unit, Error> = setsockopt(this, SOL_SOCKET, SO_KEEPALIVE, keepalive.toInt())

    public open fun keepalive(): Result<Boolean, Error> {
        val raw: Bool = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_KEEPALIVE)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok((raw != 0).toUByte())
    }

    public open fun setNodelay(nodelay: Boolean): Result<Unit, Error> = setsockopt(this, IPPROTO_TCP, TCP_NODELAY, nodelay.toInt())

    public open fun nodelay(): Result<Boolean, Error> {
        val raw: Bool = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_NODELAY)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok((raw != 0).toUByte())
    }

    public open fun takeError(): Result<io.github.kotlinmania.rustlang.io.Error?, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_ERROR)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return if (raw == 0) {
            Result.Ok(null)
        } else {
            Result.Ok(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(raw))
        }
    }

    public open fun asRaw(): Socket {
        check(sizeOf<Socket>(typeLayoutT = ObjectSlotLayout<Socket>()) == sizeOf<RawSocket>(typeLayoutT = ObjectSlotLayout<RawSocket>()))
        check(alignOf<Socket>(typeLayoutT = ObjectSlotLayout<Socket>()) == alignOf<RawSocket>(typeLayoutT = ObjectSlotLayout<RawSocket>()))
        return ((this.asInner().asRawSocket() as Any?) as Socket)
    }

    public override fun asInner(): OwnedSocket = this.value

    public override fun intoInner(): OwnedSocket = this.value

    public override fun asSocket(): BorrowedSocket = this.value.asSocket()

    public override fun asRawSocket(): RawSocket = this.value.asRawSocket()

    public override fun intoRawSocket(): RawSocket = this.value.intoRawSocket()

    public companion object {
        public fun new(family: CInt, ty: CInt): Result<Socket, Error> {
            val socket = WSASocketW(family, ty, 0, MutPtr(0L), 0, WSA_FLAG_OVERLAPPED or WSA_FLAG_NO_HANDLE_INHERIT)
            return if (socket != INVALID_SOCKET) {
                Result.Ok(Socket.fromRaw(socket))
            } else {
                val error = WSAGetLastError()
                if (error != WSAEPROTOTYPE && error != WSAEINVAL) {
                    return Result.Err(io.github.kotlinmania.rustlang.io.Error.fromRawOsError(error))
                }
                val socket1 = WSASocketW(family, ty, 0, MutPtr(0L), 0, WSA_FLAG_OVERLAPPED)
                if (socket1 == INVALID_SOCKET) {
                    return Result.Err(lastError())
                }
                run {
                    val socket2 = Socket.fromRaw(socket1)
                    when (val tryResult = socket2.value.setNoInherit()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                    Result.Ok(socket2)
                }
            }
        }

        public fun fromRaw(raw: Socket): Socket {
            check(sizeOf<Socket>(typeLayoutT = ObjectSlotLayout<Socket>()) == sizeOf<RawSocket>(typeLayoutT = ObjectSlotLayout<RawSocket>()))
            check(alignOf<Socket>(typeLayoutT = ObjectSlotLayout<Socket>()) == alignOf<RawSocket>(typeLayoutT = ObjectSlotLayout<RawSocket>()))
            return Socket.fromRawSocket(((raw as Any?) as RawSocket))
        }

        public fun fromInner(sock: OwnedSocket): Socket = Socket(sock)

        public fun fromRawSocket(rawSocket: RawSocket): Socket = Socket(FromRawSocket.fromRawSocket(rawSocket))
    }
}
