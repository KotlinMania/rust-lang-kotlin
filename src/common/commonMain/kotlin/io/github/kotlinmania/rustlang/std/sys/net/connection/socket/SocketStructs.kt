package io.github.kotlinmania.rustlang.std.sys.net.connection.socket

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.c.EMSGSIZE
import io.github.kotlinmania.rustlang.std.c.Send
import io.github.kotlinmania.rustlang.std.ffi.CInt
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.MSG_NOSIGNAL
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.eachAddr
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.solid.cvt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.solid.cvtR
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.Socket
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.init
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows.netc.IpMreq
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows.netc.Ipv6Mreq
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows.netc.SockaddrIn
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows.netc.SockaddrIn6
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows.netc.sendto
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.EINPROGRESS
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IPPROTO_IP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IPPROTO_IPV6
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IPV6_ADD_MEMBERSHIP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IPV6_DROP_MEMBERSHIP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IPV6_MULTICAST_LOOP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IPV6_V6ONLY
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IP_ADD_MEMBERSHIP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IP_DROP_MEMBERSHIP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IP_MULTICAST_LOOP
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IP_MULTICAST_TTL
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.IP_TTL
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SOCK_DGRAM
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SOCK_STREAM
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SOL_SOCKET
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SO_BROADCAST
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SO_RCVTIMEO
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SO_REUSEADDR
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SO_SNDTIMEO
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SockaddrStorage
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.bind
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.connect
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.freeaddrinfo
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.getpeername
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.getsockname
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.listen
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEWOULDBLOCK

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

/**
 * A type with the same memory layout as `c.sockaddr`. Used in converting Rust level
 * SocketAddr* types into their system representation. The benefit of this specific
 * type over using `c.sockaddrStorage` is that this type is exactly as large as it
 * needs to be and not a lot larger. And it can be initialized more cleanly from Rust.
 */
public open class SocketAddrCRepr(
    public var v4: SockaddrIn? = null,
    public var v6: SockaddrIn6? = null,
) {
    public open fun asPtr(): ConstPtr = (constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
}

// ///////////////////////////////////////////////////////////////////////////
// lookupHost
// ///////////////////////////////////////////////////////////////////////////
public open class LookupHost internal constructor(
    originalCtorArg: MutPtr,
    curCtorArg: MutPtr,
    public var port: UShort,
) : Iterable<SocketAddr> {
    public var original: MutPtr = originalCtorArg
        internal set

    public var cur: MutPtr = curCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<SocketAddr> = object : AbstractIterator<SocketAddr>() {
        override fun computeNext() {
            when (val v = this@LookupHost.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): SocketAddr? {
        while (true) {
            run {
                val cur = (this.cur.asRef() ?: return null)
                this.cur = cur.aiNext
                run {
                    val matchVal = socketAddrFromC(cur.aiAddr.cast(), cur.aiAddrlen.toInt())
                    when (matchVal) { is Result.Ok<*> -> {
                        val addr = matchVal.value
                        addr.setPort(this.port)
                        return addr
                    }; is Result.Err<*> -> {
                        continue
                    } }
                }
            }
        }
    }

    public open fun drop() {
        freeaddrinfo(this.original)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<LookupHost> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<LookupHost> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): LookupHost =
                    LookupHost(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: LookupHost) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.original)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.cur)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr + 16L, value.port)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LookupHost =
                    LookupHost(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LookupHost) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.original)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.cur)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.port)
                }
            }

        public operator fun invoke(
            original: MutPtr,
            cur: MutPtr,
            port: UShort,
        ): LookupHost = LookupHost(
            originalCtorArg = original,
            curCtorArg = cur,
            port = port,
        )
    }
}

// ///////////////////////////////////////////////////////////////////////////
// TCP streams
// ///////////////////////////////////////////////////////////////////////////
public open class TcpStream(
    public var inner: Socket,
) : AsInner<Socket>, FromInner<Socket, TcpStream> {
    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun setReadTimeout(dur: Duration?): Result<Unit> = this.inner.setTimeout(dur, SO_RCVTIMEO)

    public open fun setWriteTimeout(dur: Duration?): Result<Unit> = this.inner.setTimeout(dur, SO_SNDTIMEO)

    public open fun readTimeout(): Result<Duration?> = this.inner.timeout(SO_RCVTIMEO)

    public open fun writeTimeout(): Result<Duration?> = this.inner.timeout(SO_SNDTIMEO)

    public open fun peek(buf: MutableList<UByte>): Result<Int> = this.inner.peek(buf)

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.inner.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.inner.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    public open fun write(buf: List<UByte>): Result<Int> {
        val len = minOf(buf.size, MAX_SEND_LEN).toInt()
        val ret = when (val tryResult = cvt(Send(this.inner.asRaw(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, len, MSG_NOSIGNAL))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.inner.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.inner.isWriteVectored()

    public open fun peerAddr(): Result<SocketAddr> = sockname({ buf, len -> getpeername(this.inner.asRaw(), buf, len) })

    public open fun socketAddr(): Result<SocketAddr> = sockname({ buf, len -> getsockname(this.inner.asRaw(), buf, len) })

    public open fun shutdown(how: Shutdown): Result<Unit> = this.inner.shutdown(how)

    public open fun duplicate(): Result<TcpStream> = this.inner.duplicate().map({ s -> TcpStream(inner = s) })

    public open fun setLinger(linger: Duration?): Result<Unit> = this.inner.setLinger(linger)

    public open fun linger(): Result<Duration?> = this.inner.linger()

    public open fun setKeepalive(keepalive: Boolean): Result<Unit> = this.inner.setKeepalive(keepalive)

    public open fun keepalive(): Result<Boolean> = this.inner.keepalive()

    public open fun setNodelay(nodelay: Boolean): Result<Unit> = this.inner.setNodelay(nodelay)

    public open fun nodelay(): Result<Boolean> = this.inner.nodelay()

    public open fun setTtl(ttl: UInt): Result<Unit> = setsockopt(this.inner, IPPROTO_IP, IP_TTL, ttl.toInt(), typeLayoutT = ObjectSlotLayout<CInt>())

    public open fun ttl(): Result<UInt> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_TTL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw.toUInt())
    }

    public open fun setHopLimitV6(limit: UByte): Result<Unit> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", setsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_UNICAST_HOPS, limit, cInt, limit, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun hopLimitV6(): Result<UByte> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", raw, cInt, getsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_UNICAST_HOPS, Ok, raw, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun takeError(): Result<Error?> = this.inner.takeError()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = this.inner.setNonblocking(nonblocking)

    public override fun asInner(): Socket = this.inner

    public open fun fmt(f: StringBuilder) {
        val res = DebugStruct(f, "TcpStream")
        when (val matchVal = this.socketAddr()) {
            is Ok -> {
                val addr = matchVal.value
                res.field("addr", addr)
            }
            else -> {}
        }
        when (val matchVal1 = this.peerAddr()) {
            is Ok -> {
                val peer = matchVal1.value
                res.field("peer", peer)
            }
            else -> {}
        }
        val name =
            if (false) {
                "socket"
            } else {
                "fd"
            }
        res.field(name, this.inner.asRaw()).finish()
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<TcpStream> {
            fun inner(addr1: SocketAddr): Result<TcpStream> {
                val sock = when (val tryResult = Socket.new(addrFamily(addr1), SOCK_STREAM)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = sock.connect(addr1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                return Result.Ok(TcpStream(inner = sock))
            }
            init()
            return eachAddr(addr, inner)
        }

        public fun connectTimeout(addr: SocketAddr, timeout: Duration): Result<TcpStream> {
            init()
            val sock = when (val tryResult = Socket.new(addrFamily(addr), SOCK_STREAM or SOCK_NONBLOCK)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            if (SOCK_NONBLOCK == 0) {
                // The `SOCK_NONBLOCK` option is unsupported...
                when (val tryResult = sock.setNonblocking(true)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            run {
                val matchVal = sock.connect(addr)
                when {
                    matchVal is Result.Ok<*> -> {
                        when (val tryResult = sock.setNonblocking(false)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                        return Result.Ok(TcpStream(inner = sock))
                    }
                    matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.rawOsError() == EINPROGRESS -> {
                        val e = (matchVal as Result.Err<*>).error
                    }
                    matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.rawOsError() == WSAEWOULDBLOCK -> {
                        val e1 = (matchVal as Result.Err<*>).error
                    }
                    else -> {
                        val e2 = (matchVal as Result.Err<*>).error
                        return Result.Err(e2)
                    }
                }
            }
            when (val tryResult = sock.setNonblocking(false)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            when (val tryResult = sock.pollConnected(timeout)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(TcpStream(inner = sock))
        }

        public fun fromInner(socket: Socket): TcpStream = TcpStream(inner = socket)
    }
}

// ///////////////////////////////////////////////////////////////////////////
// TCP listeners
// ///////////////////////////////////////////////////////////////////////////
public open class TcpListener(
    public var inner: Socket,
) : FromInner<Socket, TcpListener> {
    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun socketAddr(): Result<SocketAddr> = sockname({ buf, len -> getsockname(this.inner.asRaw(), buf, len) })

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> {
        // The `accept` function will fill in the storage with the address,
        // so we don't need to zero it here.
        // reference: https://linux.die.net/man/2/accept4
        val storage = MaybeUninit.uninit(ObjectSlotLayout<SockaddrStorage>())
        val len = sizeOf<SockaddrStorage>(typeLayoutT = ObjectSlotLayout<SockaddrStorage>())
        val sock = when (val tryResult = this.inner.accept(mutPtrFromRustCast(storage.asMutPtr()), len)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val addr = when (val tryResult = socketAddrFromC(storage.asPtr(), len)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(TcpStream(inner = sock), addr))
    }

    public open fun duplicate(): Result<TcpListener> = this.inner.duplicate().map({ s -> TcpListener(inner = s) })

    public open fun setTtl(ttl: UInt): Result<Unit> = setsockopt(this.inner, IPPROTO_IP, IP_TTL, ttl.toInt(), typeLayoutT = ObjectSlotLayout<CInt>())

    public open fun ttl(): Result<UInt> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_TTL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw.toUInt())
    }

    public open fun setHopLimitV6(limit: UByte): Result<Unit> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", setsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_UNICAST_HOPS, limit, cInt, limit, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun hopLimitV6(): Result<UByte> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", raw, cInt, getsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_UNICAST_HOPS, Ok, raw, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun setOnlyV6(onlyV6: Boolean): Result<Unit> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_V6ONLY, onlyV6.toInt(), typeLayoutT = ObjectSlotLayout<CInt>())

    public open fun onlyV6(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_V6ONLY)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun takeError(): Result<Error?> = this.inner.takeError()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = this.inner.setNonblocking(nonblocking)

    public open fun fmt(f: StringBuilder) {
        val res = DebugStruct(f, "TcpListener")
        when (val matchVal = this.socketAddr()) {
            is Ok -> {
                val addr = matchVal.value
                res.field("addr", addr)
            }
            else -> {}
        }
        val name =
            if (false) {
                "socket"
            } else {
                "fd"
            }
        res.field(name, this.inner.asRaw()).finish()
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<TcpListener> {
            fun inner(addr1: SocketAddr): Result<TcpListener> {
                val sock = when (val tryResult = Socket.new(addrFamily(addr1), SOCK_STREAM)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                // On platforms with Berkeley-derived sockets, this allows to quickly
                // rebind a socket, without needing to wait for the OS to clean up the
                // previous one.
                //
                // On Windows, this allows rebinding sockets which are actively in use,
                // which allows “socket hijacking”, so we explicitly don't set it here.
                // https://docs.microsoft.com/en-us/windows/win32/winsock/using-so-reuseaddr-and-so-exclusiveaddruse
                when (val tryResult = setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, 1, typeLayoutT = ObjectSlotLayout<CInt>())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                // Bind our new socket
                val (addr2, len) = socketAddrToC(addr1)
                when (val tryResult = cvt(bind(sock.asRaw(), addr2.asPtr(), len.toUInt()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                val backlog =
                    if (false) {
                        // The 3DS doesn't support a big connection backlog. Sometimes
                        // it allows up to about 37, but other times it doesn't even
                        // accept 32. There may be a global limitation causing this.
                        20
                    } else if (false) {
                        // Haiku does not support a queue length > 32
                        // https://github.com/haiku/haiku/blob/979a0bc487864675517fb2fab28f87dc8bf43041/headers/posix/sys/socket.h#L81
                        32
                    } else {
                        // The default for all other platforms
                        128
                    }
                // Start listening
                when (val tryResult = cvt(listen(sock.asRaw(), backlog))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                return Result.Ok(TcpListener(inner = sock))
            }
            init()
            return eachAddr(addr, inner)
        }

        public fun fromInner(socket: Socket): TcpListener = TcpListener(inner = socket)
    }
}

// ///////////////////////////////////////////////////////////////////////////
// UDP
// ///////////////////////////////////////////////////////////////////////////
public open class UdpSocket(
    public var inner: Socket,
) : FromInner<Socket, UdpSocket> {
    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun peerAddr(): Result<SocketAddr> = sockname({ buf, len -> getpeername(this.inner.asRaw(), buf, len) })

    public open fun socketAddr(): Result<SocketAddr> = sockname({ buf, len -> getsockname(this.inner.asRaw(), buf, len) })

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.inner.recvFrom(buf)

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.inner.peekFrom(buf)

    public open fun sendTo(buf: List<UByte>, dst: SocketAddr): Result<Int> {
        if (buf.size > MAX_SEND_LEN) {
            return Result.Err(Error.fromRawOsError(EMSGSIZE))
        }
        val (dst1, dstlen) = socketAddrToC(dst)
        val ret = when (val tryResult = cvt(sendto(this.inner.asRaw().toULong(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, MSG_NOSIGNAL, dst1.asPtr(), dstlen))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun duplicate(): Result<UdpSocket> = this.inner.duplicate().map({ s -> UdpSocket(inner = s) })

    public open fun setReadTimeout(dur: Duration?): Result<Unit> = this.inner.setTimeout(dur, SO_RCVTIMEO)

    public open fun setWriteTimeout(dur: Duration?): Result<Unit> = this.inner.setTimeout(dur, SO_SNDTIMEO)

    public open fun readTimeout(): Result<Duration?> = this.inner.timeout(SO_RCVTIMEO)

    public open fun writeTimeout(): Result<Duration?> = this.inner.timeout(SO_SNDTIMEO)

    public open fun setBroadcast(broadcast: Boolean): Result<Unit> = setsockopt(this.inner, SOL_SOCKET, SO_BROADCAST, broadcast.toInt(), typeLayoutT = ObjectSlotLayout<CInt>())

    public open fun broadcast(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, SOL_SOCKET, SO_BROADCAST)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setMulticastLoopV4(multicastLoopV4: Boolean): Result<Unit> = setsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_LOOP, ((multicastLoopV4 as Any?) as IpV4MultiCastType), typeLayoutT = ObjectSlotLayout<IpV4MultiCastType>())

    public open fun multicastLoopV4(): Result<Boolean> {
        val raw: IpV4MultiCastType = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_LOOP)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setMulticastTtlV4(multicastTtlV4: UInt): Result<Unit> {
        val ttl: UByte = when (val tryResult = multicastTtlV4.tryInto().mapErr({ Error.from(ErrorKind.InvalidInput) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return setsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_TTL, ((ttl as Any?) as IpV4MultiCastType), typeLayoutT = ObjectSlotLayout<IpV4MultiCastType>())
    }

    public open fun multicastTtlV4(): Result<UInt> {
        val raw: IpV4MultiCastType = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_TTL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw.toUInt())
    }

    public open fun setMulticastLoopV6(multicastLoopV6: Boolean): Result<Unit> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_MULTICAST_LOOP, multicastLoopV6.toInt(), typeLayoutT = ObjectSlotLayout<CInt>())

    public open fun multicastLoopV6(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_MULTICAST_LOOP)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun joinMulticastV4(multiaddr: Ipv4Addr, iface: Ipv4Addr): Result<Unit> {
        val mreq = IpMreq(imrMultiaddr = ipV4AddrToC(multiaddr), imrInterface = ipV4AddrToC(iface))
        return setsockopt(this.inner, IPPROTO_IP, IP_ADD_MEMBERSHIP, mreq)
    }

    public open fun joinMulticastV6(multiaddr: Ipv6Addr, iface: UInt): Result<Unit> {
        val mreq = Ipv6Mreq(ipv6mrMultiaddr = ipV6AddrToC(multiaddr), ipv6mrInterface = toIpv6mrInterface(iface).toUInt())
        return setsockopt(this.inner, IPPROTO_IPV6, IPV6_ADD_MEMBERSHIP, mreq)
    }

    public open fun leaveMulticastV4(multiaddr: Ipv4Addr, iface: Ipv4Addr): Result<Unit> {
        val mreq = IpMreq(imrMultiaddr = ipV4AddrToC(multiaddr), imrInterface = ipV4AddrToC(iface))
        return setsockopt(this.inner, IPPROTO_IP, IP_DROP_MEMBERSHIP, mreq)
    }

    public open fun leaveMulticastV6(multiaddr: Ipv6Addr, iface: UInt): Result<Unit> {
        val mreq = Ipv6Mreq(ipv6mrMultiaddr = ipV6AddrToC(multiaddr), ipv6mrInterface = toIpv6mrInterface(iface).toUInt())
        return setsockopt(this.inner, IPPROTO_IPV6, IPV6_DROP_MEMBERSHIP, mreq)
    }

    public open fun setTtl(ttl: UInt): Result<Unit> = setsockopt(this.inner, IPPROTO_IP, IP_TTL, ttl.toInt(), typeLayoutT = ObjectSlotLayout<CInt>())

    public open fun ttl(): Result<UInt> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_TTL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw.toUInt())
    }

    public open fun setHopLimitV6(limit: UByte): Result<Unit> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", setsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_UNICAST_HOPS, limit, cInt, limit, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun hopLimitV6(): Result<UByte> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", raw, cInt, getsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_UNICAST_HOPS, Ok, raw, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun setMulticastHopLimitV6(limit: UByte): Result<Unit> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", setsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_MULTICAST_HOPS, limit, cInt, limit, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun multicastHopLimitV6(): Result<UByte> = cfgSelect(any, targetFamily, "unix", targetOs, "windows", raw, cInt, getsockopt, this, inner, c, IPPROTO_IPV6, c, IPV6_MULTICAST_HOPS, Ok, raw, Err, io, Error, new, ErrorKind, Unsupported, "not supported on this platform")

    public open fun takeError(): Result<Error?> = this.inner.takeError()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = this.inner.setNonblocking(nonblocking)

    public open fun recv(buf: MutableList<UByte>): Result<Int> = this.inner.read(buf)

    public open fun peek(buf: MutableList<UByte>): Result<Int> = this.inner.peek(buf)

    public open fun send(buf: List<UByte>): Result<Int> {
        if (buf.size > MAX_SEND_LEN) {
            return Result.Err(Error.fromRawOsError(EMSGSIZE))
        }
        val ret = when (val tryResult = cvt(Send(this.inner.asRaw(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, MSG_NOSIGNAL))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<Unit> {
        fun inner(thisRef: UdpSocket, addr1: SocketAddr): Result<Unit> {
            val (addr2, len) = socketAddrToC(addr1)
            return cvtR({ connect(thisRef.inner.asRaw(), addr2.asPtr(), len) }).map(drop)
        }
        return eachAddr(addr, { addr1 -> inner(this, addr1) })
    }

    public open fun fmt(f: StringBuilder) {
        val res = DebugStruct(f, "UdpSocket")
        when (val matchVal = this.socketAddr()) {
            is Ok -> {
                val addr = matchVal.value
                res.field("addr", addr)
            }
            else -> {}
        }
        val name =
            if (false) {
                "socket"
            } else {
                "fd"
            }
        res.field(name, this.inner.asRaw()).finish()
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<UdpSocket> {
            fun inner(addr1: SocketAddr): Result<UdpSocket> {
                val sock = when (val tryResult = Socket.new(addrFamily(addr1), SOCK_DGRAM)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                val (addr2, len) = socketAddrToC(addr1)
                when (val tryResult = cvt(bind(sock.asRaw(), addr2.asPtr(), len.toUInt()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                return Result.Ok(UdpSocket(inner = sock))
            }
            init()
            return eachAddr(addr, inner)
        }

        public fun fromInner(socket: Socket): UdpSocket = UdpSocket(inner = socket)
    }
}
