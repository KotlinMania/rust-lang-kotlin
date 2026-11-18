package io.github.kotlinmania.rustlang.std.sys.net.connection.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.constptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.Send
import io.github.kotlinmania.rustlang.libc.bind
import io.github.kotlinmania.rustlang.libc.connect
import io.github.kotlinmania.rustlang.libc.freeaddrinfo
import io.github.kotlinmania.rustlang.libc.getpeername
import io.github.kotlinmania.rustlang.libc.getsockname
import io.github.kotlinmania.rustlang.libc.listen
import io.github.kotlinmania.rustlang.libc.sendto
import io.github.kotlinmania.rustlang.libc.unix.IPPROTO_IP
import io.github.kotlinmania.rustlang.libc.unix.IPPROTO_IPV6
import io.github.kotlinmania.rustlang.libc.unix.Ipv6Mreq
import io.github.kotlinmania.rustlang.libc.unix.bsd.IPV6_MULTICAST_HOPS
import io.github.kotlinmania.rustlang.libc.unix.bsd.IPV6_MULTICAST_LOOP
import io.github.kotlinmania.rustlang.libc.unix.bsd.IPV6_UNICAST_HOPS
import io.github.kotlinmania.rustlang.libc.unix.bsd.IPV6_V6ONLY
import io.github.kotlinmania.rustlang.libc.unix.bsd.IP_MULTICAST_LOOP
import io.github.kotlinmania.rustlang.libc.unix.bsd.IP_MULTICAST_TTL
import io.github.kotlinmania.rustlang.libc.unix.bsd.SockaddrIn6
import io.github.kotlinmania.rustlang.libc.unix.bsd.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SOCK_DGRAM
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SOCK_STREAM
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_BROADCAST
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_RCVTIMEO
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_REUSEADDR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_SNDTIMEO
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.IP_ADD_MEMBERSHIP
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.IP_DROP_MEMBERSHIP
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.IP_TTL
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.IpMreq
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SockaddrIn
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SockaddrStorage
import io.github.kotlinmania.rustlang.libc.wasi.EINPROGRESS
import io.github.kotlinmania.rustlang.libc.wasi.EMSGSIZE
import io.github.kotlinmania.rustlang.libc.wasi.SOL_SOCKET
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.eachAddr
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.Socket
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.init
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtR
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSAEWOULDBLOCK
import io.github.kotlinmania.rustlang.std.c.IPV6_JOIN_GROUP as IPV6_ADD_MEMBERSHIP
import io.github.kotlinmania.rustlang.std.c.IPV6_LEAVE_GROUP as IPV6_DROP_MEMBERSHIP

private class DebugStruct(
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

    override fun iterator(): Iterator<SocketAddr> = object : AbstractIterator<SocketAddr>() {
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
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val addr = matchVal.okValue()
                        addr.setPort(this.port)
                        return addr
                    }; is Result.Err<*, *> -> {
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
        public fun typeLayout(): TypeLayout<LookupHost> =
            object : TypeLayout<LookupHost> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): LookupHost =
                    LookupHost(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: LookupHost) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.original)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.cur)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.port)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LookupHost =
                    LookupHost(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LookupHost) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.original)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.cur)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.port)
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

    public open fun setReadTimeout(dur: Duration?): Result<Unit, Error> = this.inner.setTimeout(dur, SO_RCVTIMEO)

    public open fun setWriteTimeout(dur: Duration?): Result<Unit, Error> = this.inner.setTimeout(dur, SO_SNDTIMEO)

    public open fun readTimeout(): Result<Duration?, Error> = this.inner.timeout(SO_RCVTIMEO)

    public open fun writeTimeout(): Result<Duration?, Error> = this.inner.timeout(SO_SNDTIMEO)

    public open fun peek(buf: MutableList<UByte>): Result<Int, Error> = this.inner.peek(buf)

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.inner.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.inner.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    public open fun write(buf: List<UByte>): Result<Int, Error> {
        val len = minOf(buf.size, MAX_SEND_LEN).toInt()
        val ret = when (val tryResult = cvt(Send(this.inner.asRaw(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, len, MSG_NOSIGNAL))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.inner.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.inner.isWriteVectored()

    public open fun peerAddr(): Result<SocketAddr, Error> = sockname({ buf, len -> getpeername(this.inner.asRaw(), buf, len) })

    public open fun socketAddr(): Result<SocketAddr, Error> = sockname({ buf, len -> getsockname(this.inner.asRaw(), buf, len) })

    public open fun shutdown(how: Shutdown): Result<Unit, Error> = this.inner.shutdown(how)

    public open fun duplicate(): Result<TcpStream, Error> = this.inner.duplicate().map({ s -> TcpStream(inner = s) })

    public open fun setLinger(linger: Duration?): Result<Unit, Error> = this.inner.setLinger(linger)

    public open fun linger(): Result<Duration?, Error> = this.inner.linger()

    public open fun setKeepalive(keepalive: Boolean): Result<Unit, Error> = this.inner.setKeepalive(keepalive)

    public open fun keepalive(): Result<Boolean, Error> = this.inner.keepalive()

    public open fun setNodelay(nodelay: Boolean): Result<Unit, Error> = this.inner.setNodelay(nodelay)

    public open fun nodelay(): Result<Boolean, Error> = this.inner.nodelay()

    public open fun setTtl(ttl: UInt): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IP, IP_TTL, ttl.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun ttl(): Result<UInt, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_TTL)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UInt, Error>(tryResult.errValue()) }
        return Result.Ok<UInt, Error>(raw.toUInt())
    }

    public open fun setHopLimitV6(limit: UByte): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_UNICAST_HOPS, limit.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun hopLimitV6(): Result<UByte, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_UNICAST_HOPS)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UByte, Error>(tryResult.errValue()) }
        return Result.Ok<UByte, Error>(raw.toUByte())
    }

    public open fun takeError(): Result<Error?, Error> = this.inner.takeError()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> = this.inner.setNonblocking(nonblocking)

    public override fun asInner(): Socket = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val res = DebugStruct(f, "TcpStream")
        when (val matchVal = this.socketAddr()) {
            is Result.Ok<*, *> -> {
                val addr = matchVal.okValue()
                res.field("addr", addr)
            }
            else -> {}
        }
        when (val matchVal1 = this.peerAddr()) {
            is Result.Ok<*, *> -> {
                val peer = matchVal1.okValue()
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
        return res.field(name, this.inner.asRaw()).finish()
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> connect(addr: A): Result<TcpStream, Error> {
            fun inner(addr1: SocketAddr): Result<TcpStream, Error> {
                val sock = when (val tryResult = Socket.new(addrFamily(addr1), SOCK_STREAM)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
                when (val tryResult = sock.connect(addr1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
                return Result.Ok<TcpStream, Error>(TcpStream(inner = sock))
            }
            init()
            return eachAddr(addr, ::inner)
        }

        public fun connectTimeout(addr: SocketAddr, timeout: Duration): Result<TcpStream, Error> {
            init()
            val sock = when (val tryResult = Socket.new(addrFamily(addr), SOCK_STREAM or SOCK_NONBLOCK)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
            if (SOCK_NONBLOCK == 0) {
                // The `SOCK_NONBLOCK` option is unsupported...
                when (val tryResult = sock.setNonblocking(true)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
            }
            run {
                val matchVal = sock.connect(addr)
                when {
                    matchVal is Result.Ok<*, *> -> {
                        val okValueVal = matchVal.okValue()
                        when (val tryResult = sock.setNonblocking(false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
                        return Result.Ok<TcpStream, Error>(TcpStream(inner = sock))
                    }
                    matchVal is Result.Err<*, *> && matchVal.errValue().rawOsError() == EINPROGRESS -> {
                        val e = matchVal.errValue()
                    }
                    matchVal is Result.Err<*, *> && matchVal.errValue().rawOsError() == WSAEWOULDBLOCK -> {
                        val e1 = matchVal.errValue()
                    }
                    else -> {
                        val e2 = matchVal.errValue()
                        return Result.Err<TcpStream, Error>(e2)
                    }
                }
            }
            when (val tryResult = sock.setNonblocking(false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
            when (val tryResult = sock.pollConnected(timeout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpStream, Error>(tryResult.errValue()) }
            return Result.Ok<TcpStream, Error>(TcpStream(inner = sock))
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

    public open fun socketAddr(): Result<SocketAddr, Error> = sockname({ buf, len -> getsockname(this.inner.asRaw(), buf, len) })

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>, Error> {
        // The `accept` function will fill in the storage with the address,
        // so we don't need to zero it here.
        // reference: https://linux.die.net/man/2/accept4
        val storage = MaybeUninit.uninit(UnresolvedTypeLayout<SockaddrStorage>())
        val localStorageL691C8 = AddressableLocal(sizeOf<SockaddrStorage>(typeLayoutT = UnresolvedTypeLayout<SockaddrStorage>()).toInt(), UnresolvedTypeLayout<SocklenT>())
        val len by localStorageL691C8
        val sock = when (val tryResult = this.inner.accept(mutPtrFromRustCast(storage.asMutPtr()), RustMutableReference(localStorageL691C8.asMutPtr(), localStorageL691C8.layout))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<TcpStream, SocketAddr>, Error>(tryResult.errValue()) }
        val addr = when (val tryResult = socketAddrFromC(storage.asPtr(), len)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<TcpStream, SocketAddr>, Error>(tryResult.errValue()) }
        return Result.Ok<Pair<TcpStream, SocketAddr>, Error>(Pair(TcpStream(inner = sock), addr))
    }

    public open fun duplicate(): Result<TcpListener, Error> = this.inner.duplicate().map({ s -> TcpListener(inner = s) })

    public open fun setTtl(ttl: UInt): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IP, IP_TTL, ttl.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun ttl(): Result<UInt, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_TTL)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UInt, Error>(tryResult.errValue()) }
        return Result.Ok<UInt, Error>(raw.toUInt())
    }

    public open fun setHopLimitV6(limit: UByte): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_UNICAST_HOPS, limit.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun hopLimitV6(): Result<UByte, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_UNICAST_HOPS)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UByte, Error>(tryResult.errValue()) }
        return Result.Ok<UByte, Error>(raw.toUByte())
    }

    public open fun setOnlyV6(onlyV6: Boolean): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_V6ONLY, (if (onlyV6) 1 else 0), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun onlyV6(): Result<Boolean, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_V6ONLY)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((raw != 0).toUByte())
    }

    public open fun takeError(): Result<Error?, Error> = this.inner.takeError()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> = this.inner.setNonblocking(nonblocking)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val res = DebugStruct(f, "TcpListener")
        when (val matchVal = this.socketAddr()) {
            is Result.Ok<*, *> -> {
                val addr = matchVal.okValue()
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
        return res.field(name, this.inner.asRaw()).finish()
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(addr: A): Result<TcpListener, Error> {
            fun inner(addr1: SocketAddr): Result<TcpListener, Error> {
                val sock = when (val tryResult = Socket.new(addrFamily(addr1), SOCK_STREAM)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpListener, Error>(tryResult.errValue()) }
                // On platforms with Berkeley-derived sockets, this allows to quickly
                // rebind a socket, without needing to wait for the OS to clean up the
                // previous one.
                //
                // On Windows, this allows rebinding sockets which are actively in use,
                // which allows “socket hijacking”, so we explicitly don't set it here.
                // https://docs.microsoft.com/en-us/windows/win32/winsock/using-so-reuseaddr-and-so-exclusiveaddruse
                when (val tryResult = setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, 1, typeLayoutT = UnresolvedTypeLayout<CInt>())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpListener, Error>(tryResult.errValue()) }
                // Bind our new socket
                val (addr2, len) = socketAddrToC(addr1)
                when (val tryResult = cvt(bind(sock.asRaw(), addr2.asPtr(), len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpListener, Error>(tryResult.errValue()) }
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
                when (val tryResult = cvt(listen(sock.asRaw(), backlog))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<TcpListener, Error>(tryResult.errValue()) }
                return Result.Ok<TcpListener, Error>(TcpListener(inner = sock))
            }
            init()
            return eachAddr(addr, ::inner)
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

    public open fun peerAddr(): Result<SocketAddr, Error> = sockname({ buf, len -> getpeername(this.inner.asRaw(), buf, len) })

    public open fun socketAddr(): Result<SocketAddr, Error> = sockname({ buf, len -> getsockname(this.inner.asRaw(), buf, len) })

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.inner.recvFrom(buf)

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.inner.peekFrom(buf)

    public open fun sendTo(buf: List<UByte>, dst: SocketAddr): Result<Int, Error> {
        if (buf.size > MAX_SEND_LEN) {
            return Result.Err<Int, Error>(Error.fromRawOsError(EMSGSIZE))
        }
        val (dst1, dstlen) = socketAddrToC(dst)
        val ret = when (val tryResult = cvt(sendto(this.inner.asRaw(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, MSG_NOSIGNAL, dst1.asPtr(), dstlen))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun duplicate(): Result<UdpSocket, Error> = this.inner.duplicate().map({ s -> UdpSocket(inner = s) })

    public open fun setReadTimeout(dur: Duration?): Result<Unit, Error> = this.inner.setTimeout(dur, SO_RCVTIMEO)

    public open fun setWriteTimeout(dur: Duration?): Result<Unit, Error> = this.inner.setTimeout(dur, SO_SNDTIMEO)

    public open fun readTimeout(): Result<Duration?, Error> = this.inner.timeout(SO_RCVTIMEO)

    public open fun writeTimeout(): Result<Duration?, Error> = this.inner.timeout(SO_SNDTIMEO)

    public open fun setBroadcast(broadcast: Boolean): Result<Unit, Error> = setsockopt(this.inner, SOL_SOCKET, SO_BROADCAST, (if (broadcast) 1 else 0), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun broadcast(): Result<Boolean, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, SOL_SOCKET, SO_BROADCAST)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((raw != 0).toUByte())
    }

    public open fun setMulticastLoopV4(multicastLoopV4: Boolean): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_LOOP, (if (multicastLoopV4) 1u else 0u).toUByte(), typeLayoutT = UnresolvedTypeLayout<CUchar>())

    public open fun multicastLoopV4(): Result<Boolean, Error> {
        val raw: IpV4MultiCastType = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_LOOP)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>(raw != 0u.toUByte())
    }

    public open fun setMulticastTtlV4(multicastTtlV4: UInt): Result<Unit, Error> {
        val ttl: UByte = when (val tryResult = multicastTtlV4.tryInto().mapErr({ Error.from(ErrorKind.InvalidInput) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return setsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_TTL, ttl, typeLayoutT = UnresolvedTypeLayout<CUchar>())
    }

    public open fun multicastTtlV4(): Result<UInt, Error> {
        val raw: IpV4MultiCastType = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_MULTICAST_TTL)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UInt, Error>(tryResult.errValue()) }
        return Result.Ok<UInt, Error>(raw.toUInt())
    }

    public open fun setMulticastLoopV6(multicastLoopV6: Boolean): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_MULTICAST_LOOP, (if (multicastLoopV6) 1 else 0), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun multicastLoopV6(): Result<Boolean, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_MULTICAST_LOOP)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((raw != 0).toUByte())
    }

    public open fun joinMulticastV4(multiaddr: Ipv4Addr, iface: Ipv4Addr): Result<Unit, Error> {
        val mreq = IpMreq(imrMultiaddr = ipV4AddrToC(multiaddr), imrInterface = ipV4AddrToC(iface))
        return setsockopt(this.inner, IPPROTO_IP, IP_ADD_MEMBERSHIP, mreq)
    }

    public open fun joinMulticastV6(multiaddr: Ipv6Addr, iface: UInt): Result<Unit, Error> {
        val mreq = Ipv6Mreq(ipv6mrMultiaddr = ipV6AddrToC(multiaddr), ipv6mrInterface = toIpv6mrInterface(iface).toUInt())
        return setsockopt(this.inner, IPPROTO_IPV6, IPV6_ADD_MEMBERSHIP, mreq)
    }

    public open fun leaveMulticastV4(multiaddr: Ipv4Addr, iface: Ipv4Addr): Result<Unit, Error> {
        val mreq = IpMreq(imrMultiaddr = ipV4AddrToC(multiaddr), imrInterface = ipV4AddrToC(iface))
        return setsockopt(this.inner, IPPROTO_IP, IP_DROP_MEMBERSHIP, mreq)
    }

    public open fun leaveMulticastV6(multiaddr: Ipv6Addr, iface: UInt): Result<Unit, Error> {
        val mreq = Ipv6Mreq(ipv6mrMultiaddr = ipV6AddrToC(multiaddr), ipv6mrInterface = toIpv6mrInterface(iface).toUInt())
        return setsockopt(this.inner, IPPROTO_IPV6, IPV6_DROP_MEMBERSHIP, mreq)
    }

    public open fun setTtl(ttl: UInt): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IP, IP_TTL, ttl.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun ttl(): Result<UInt, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IP, IP_TTL)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UInt, Error>(tryResult.errValue()) }
        return Result.Ok<UInt, Error>(raw.toUInt())
    }

    public open fun setHopLimitV6(limit: UByte): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_UNICAST_HOPS, limit.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun hopLimitV6(): Result<UByte, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_UNICAST_HOPS)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UByte, Error>(tryResult.errValue()) }
        return Result.Ok<UByte, Error>(raw.toUByte())
    }

    public open fun setMulticastHopLimitV6(limit: UByte): Result<Unit, Error> = setsockopt(this.inner, IPPROTO_IPV6, IPV6_MULTICAST_HOPS, limit.toInt(), typeLayoutT = UnresolvedTypeLayout<CInt>())

    public open fun multicastHopLimitV6(): Result<UByte, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this.inner, IPPROTO_IPV6, IPV6_MULTICAST_HOPS)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UByte, Error>(tryResult.errValue()) }
        return Result.Ok<UByte, Error>(raw.toUByte())
    }

    public open fun takeError(): Result<Error?, Error> = this.inner.takeError()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> = this.inner.setNonblocking(nonblocking)

    public open fun recv(buf: MutableList<UByte>): Result<Int, Error> = this.inner.read(buf)

    public open fun peek(buf: MutableList<UByte>): Result<Int, Error> = this.inner.peek(buf)

    public open fun send(buf: List<UByte>): Result<Int, Error> {
        if (buf.size > MAX_SEND_LEN) {
            return Result.Err<Int, Error>(Error.fromRawOsError(EMSGSIZE))
        }
        val ret = when (val tryResult = cvt(Send(this.inner.asRaw(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, MSG_NOSIGNAL))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun <Iter, A : ToSocketAddrs<Iter>> connect(addr: A): Result<Unit, Error> {
        fun inner(thisRef: UdpSocket, addr1: SocketAddr): Result<Unit, Error> {
            val (addr2, len) = socketAddrToC(addr1)
            return cvtR({ connect(thisRef.inner.asRaw(), addr2.asPtr(), len) }).map({ drop(it) })
        }
        return eachAddr(addr, { addr1 -> inner(this, addr1) })
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val res = DebugStruct(f, "UdpSocket")
        when (val matchVal = this.socketAddr()) {
            is Result.Ok<*, *> -> {
                val addr = matchVal.okValue()
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
        return res.field(name, this.inner.asRaw()).finish()
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(addr: A): Result<UdpSocket, Error> {
            fun inner(addr1: SocketAddr): Result<UdpSocket, Error> {
                val sock = when (val tryResult = Socket.new(addrFamily(addr1), SOCK_DGRAM)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UdpSocket, Error>(tryResult.errValue()) }
                val (addr2, len) = socketAddrToC(addr1)
                when (val tryResult = cvt(bind(sock.asRaw(), addr2.asPtr(), len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UdpSocket, Error>(tryResult.errValue()) }
                return Result.Ok<UdpSocket, Error>(UdpSocket(inner = sock))
            }
            init()
            return eachAddr(addr, ::inner)
        }

        public fun fromInner(socket: Socket): UdpSocket = UdpSocket(inner = socket)
    }
}
