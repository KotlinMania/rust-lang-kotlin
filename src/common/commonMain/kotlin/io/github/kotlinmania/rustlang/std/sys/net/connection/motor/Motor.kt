// port-lint: source sys/net/connection/motor.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.motor

import io.github.kotlinmania.motort.fs.read
import io.github.kotlinmania.motort.fs.write
import io.github.kotlinmania.motort.net.Linger
import io.github.kotlinmania.motort.net.PROTO_TCP
import io.github.kotlinmania.motort.net.PROTO_UDP
import io.github.kotlinmania.motort.net.SHUTDOWN_READ
import io.github.kotlinmania.motort.net.SHUTDOWN_WRITE
import io.github.kotlinmania.motort.net.joinUdpMulticastV4
import io.github.kotlinmania.motort.net.joinUdpMulticastV6
import io.github.kotlinmania.motort.net.leaveUdpMulticastV4
import io.github.kotlinmania.motort.net.leaveUdpMulticastV6
import io.github.kotlinmania.motort.net.setUdpBroadcast
import io.github.kotlinmania.motort.net.setUdpMulticastLoopV4
import io.github.kotlinmania.motort.net.setUdpMulticastLoopV6
import io.github.kotlinmania.motort.net.setUdpMulticastTtlV4
import io.github.kotlinmania.motort.net.tcpConnect
import io.github.kotlinmania.motort.net.udpBroadcast
import io.github.kotlinmania.motort.net.udpConnect
import io.github.kotlinmania.motort.net.udpMulticastLoopV4
import io.github.kotlinmania.motort.net.udpMulticastLoopV6
import io.github.kotlinmania.motort.net.udpMulticastTtlV4
import io.github.kotlinmania.motort.net.udpPeekFrom
import io.github.kotlinmania.motort.net.udpRecvFrom
import io.github.kotlinmania.motort.net.udpSendTo
import io.github.kotlinmania.motort.netc
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV6
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.SocketAddr.V4
import io.github.kotlinmania.rustlang.std.net.SocketAddr.v6
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.motor.mapMotorError

// We want to re-use as much of Rust's stdlib code as possible,
// and most of it is unixy, but with a lot of nesting.
public open class Socket(
    public var value: FileDesc,
) : AsInner<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, Socket>, AsFd, AsRawFd, IntoRawFd, FromRawFd<Socket, Any?, Any> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public override fun asInner(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        public fun fromInner(fileDesc: FileDesc): Socket = Socket(fileDesc)

        public fun fromRawFd(rawFd: RawFd): Socket = Socket(FromRawFd.fromRawFd(rawFd))
    }
}

public open class TcpStream(
    public var inner: Socket,
) : AsInner<Socket>, FromInner<Socket, TcpStream> {
    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun setReadTimeout(timeout: Duration?): Result<Unit> = io.github.kotlinmania.motort.net.setReadTimeout(this.inner.asRawFd(), timeout).mapErr(mapMotorError)

    public open fun setWriteTimeout(timeout: Duration?): Result<Unit> = io.github.kotlinmania.motort.net.setWriteTimeout(this.inner.asRawFd(), timeout).mapErr(mapMotorError)

    public open fun readTimeout(): Result<Duration?> = io.github.kotlinmania.motort.net.readTimeout(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun writeTimeout(): Result<Duration?> = io.github.kotlinmania.motort.net.writeTimeout(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun peek(buf: MutableList<UByte>): Result<Int> = io.github.kotlinmania.motort.net.peek(this.inner.asRawFd(), buf).mapErr(mapMotorError)

    public open fun read(buf: MutableList<UByte>): Result<Int> = io.github.kotlinmania.motort.fs.read(this.inner.asRawFd(), buf).mapErr(mapMotorError)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ buf -> this.read(buf) }, cursor)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
        val bufs1: MutableList<MutableList<UByte>> = transmute(bufs)
        return io.github.kotlinmania.motort.fs.readVectored(this.inner.asRawFd(), bufs1).mapErr(mapMotorError)
    }

    public open fun isReadVectored(): Boolean = true

    public open fun write(buf: List<UByte>): Result<Int> = io.github.kotlinmania.motort.fs.write(this.inner.asRawFd(), buf).mapErr(mapMotorError)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        val bufs1: List<List<UByte>> = transmute(bufs)
        return io.github.kotlinmania.motort.fs.writeVectored(this.inner.asRawFd(), bufs1).mapErr(mapMotorError)
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun peerAddr(): Result<SocketAddr> = io.github.kotlinmania.motort.net.peerAddr(this.inner.asRawFd()).map({ addr -> fromNetc(addr) }).mapErr(mapMotorError)

    public open fun socketAddr(): Result<SocketAddr> = io.github.kotlinmania.motort.net.SocketAddr(this.inner.asRawFd()).map({ addr -> fromNetc(addr) }).mapErr(mapMotorError)

    public open fun shutdown(shutdown: Shutdown): Result<Unit> {
        val shutdown1 =
            when (shutdown) {
                Shutdown.Read -> SHUTDOWN_READ
                Shutdown.Write -> SHUTDOWN_WRITE
                Shutdown.Both -> SHUTDOWN_READ or SHUTDOWN_WRITE
            }
        return io.github.kotlinmania.motort.net.Shutdown(this.inner.asRawFd(), shutdown1).mapErr(mapMotorError)
    }

    public open fun duplicate(): Result<TcpStream> = io.github.kotlinmania.motort.fs.duplicate(this.inner.asRawFd()).map({ fd -> TcpStream(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)

    public open fun setLinger(timeout: Duration?): Result<Unit> = io.github.kotlinmania.motort.net.setLinger(this.inner.asRawFd(), timeout).mapErr(mapMotorError)

    public open fun linger(): Result<Duration?> = Linger(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setKeepalive(arg0: Boolean): Result<Unit> = unsupported()

    public open fun keepalive(): Result<Boolean> = unsupported()

    public open fun setNodelay(nodelay: Boolean): Result<Unit> = io.github.kotlinmania.motort.net.setNodelay(this.inner.asRawFd(), nodelay).mapErr(mapMotorError)

    public open fun nodelay(): Result<Boolean> = io.github.kotlinmania.motort.net.nodelay(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setTtl(ttl: UInt): Result<Unit> = io.github.kotlinmania.motort.net.setTtl(this.inner.asRawFd(), ttl).mapErr(mapMotorError)

    public open fun ttl(): Result<UInt> = io.github.kotlinmania.motort.net.ttl(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = unsupported()

    public open fun hopLimitV6(): Result<UByte> = unsupported()

    public open fun takeError(): Result<Error?> = io.github.kotlinmania.motort.net.takeError(this.inner.asRawFd()).map({ e -> e.map(mapMotorError) }).mapErr(mapMotorError)

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = io.github.kotlinmania.motort.net.setNonblocking(this.inner.asRawFd(), nonblocking).mapErr(mapMotorError)

    public override fun asInner(): Socket = this.inner

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<TcpStream> {
            val addr1 = intoNetc((when (val tryResult = addr.toSocketAddrs()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.next())!!)
            return tcpConnect(addr1, Duration.MAX, false).map({ fd -> TcpStream(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)
        }

        public fun connectTimeout(addr: SocketAddr, timeout: Duration): Result<TcpStream> {
            val addr1 = intoNetc(addr)
            return tcpConnect(addr1, timeout, false).map({ fd -> TcpStream(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)
        }

        public fun fromInner(socket: Socket): TcpStream = TcpStream(inner = socket)
    }
}

public open class TcpListener(
    public var inner: Socket,
) : FromInner<Socket, TcpListener> {
    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun socketAddr(): Result<SocketAddr> = io.github.kotlinmania.motort.net.SocketAddr(this.inner.asRawFd()).map({ addr -> fromNetc(addr) }).mapErr(mapMotorError)

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> =
        io.github.kotlinmania.motort.net.accept(this.inner.asRawFd()).map({ (fd, addr) ->
            Pair(TcpStream(inner = Socket.fromRawFd(fd)), fromNetc(addr))
        }).mapErr(mapMotorError)

    public open fun duplicate(): Result<TcpListener> = io.github.kotlinmania.motort.fs.duplicate(this.inner.asRawFd()).map({ fd -> TcpListener(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)

    public open fun setTtl(ttl: UInt): Result<Unit> = io.github.kotlinmania.motort.net.setTtl(this.inner.asRawFd(), ttl).mapErr(mapMotorError)

    public open fun ttl(): Result<UInt> = io.github.kotlinmania.motort.net.ttl(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = unsupported()

    public open fun hopLimitV6(): Result<UByte> = unsupported()

    public open fun setOnlyV6(onlyV6: Boolean): Result<Unit> = io.github.kotlinmania.motort.net.setOnlyV6(this.inner.asRawFd(), onlyV6).mapErr(mapMotorError)

    public open fun onlyV6(): Result<Boolean> = io.github.kotlinmania.motort.net.onlyV6(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun takeError(): Result<Error?> = io.github.kotlinmania.motort.net.takeError(this.inner.asRawFd()).map({ e -> e.map(mapMotorError) }).mapErr(mapMotorError)

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = io.github.kotlinmania.motort.net.setNonblocking(this.inner.asRawFd(), nonblocking).mapErr(mapMotorError)

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<TcpListener> {
            val addr1 = intoNetc((when (val tryResult = addr.toSocketAddrs()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.next())!!)
            return io.github.kotlinmania.motort.net.bind(PROTO_TCP, addr1).map({ fd -> TcpListener(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)
        }

        public fun fromInner(socket: Socket): TcpListener = TcpListener(inner = socket)
    }
}

public open class UdpSocket(
    public var inner: Socket,
) : FromInner<Socket, UdpSocket> {
    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun peerAddr(): Result<SocketAddr> = io.github.kotlinmania.motort.net.peerAddr(this.inner.asRawFd()).map({ addr -> fromNetc(addr) }).mapErr(mapMotorError)

    public open fun socketAddr(): Result<SocketAddr> = io.github.kotlinmania.motort.net.SocketAddr(this.inner.asRawFd()).map({ addr -> fromNetc(addr) }).mapErr(mapMotorError)

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = udpRecvFrom(this.inner.asRawFd(), buf).map({ (sz, addr) -> Pair(sz, fromNetc(addr)) }).mapErr(mapMotorError)

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = udpPeekFrom(this.inner.asRawFd(), buf).map({ (sz, addr) -> Pair(sz, fromNetc(addr)) }).mapErr(mapMotorError)

    public open fun sendTo(buf: List<UByte>, addr: SocketAddr): Result<Int> {
        val addr1 = intoNetc(addr)
        return udpSendTo(this.inner.asRawFd(), buf, addr1).mapErr(mapMotorError)
    }

    public open fun duplicate(): Result<UdpSocket> = io.github.kotlinmania.motort.fs.duplicate(this.inner.asRawFd()).map({ fd -> UdpSocket(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)

    public open fun setReadTimeout(timeout: Duration?): Result<Unit> = io.github.kotlinmania.motort.net.setReadTimeout(this.inner.asRawFd(), timeout).mapErr(mapMotorError)

    public open fun setWriteTimeout(timeout: Duration?): Result<Unit> = io.github.kotlinmania.motort.net.setWriteTimeout(this.inner.asRawFd(), timeout).mapErr(mapMotorError)

    public open fun readTimeout(): Result<Duration?> = io.github.kotlinmania.motort.net.readTimeout(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun writeTimeout(): Result<Duration?> = io.github.kotlinmania.motort.net.writeTimeout(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setBroadcast(broadcast: Boolean): Result<Unit> = setUdpBroadcast(this.inner.asRawFd(), broadcast).mapErr(mapMotorError)

    public open fun broadcast(): Result<Boolean> = udpBroadcast(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setMulticastLoopV4(value: Boolean): Result<Unit> = setUdpMulticastLoopV4(this.inner.asRawFd(), value).mapErr(mapMotorError)

    public open fun multicastLoopV4(): Result<Boolean> = udpMulticastLoopV4(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setMulticastTtlV4(value: UInt): Result<Unit> = setUdpMulticastTtlV4(this.inner.asRawFd(), value).mapErr(mapMotorError)

    public open fun multicastTtlV4(): Result<UInt> = udpMulticastTtlV4(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setMulticastLoopV6(value: Boolean): Result<Unit> = setUdpMulticastLoopV6(this.inner.asRawFd(), value).mapErr(mapMotorError)

    public open fun multicastLoopV6(): Result<Boolean> = udpMulticastLoopV6(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun joinMulticastV4(addr: Ipv4Addr, iface: Ipv4Addr): Result<Unit> {
        val addr1 = addr.into()
        val iface1 = iface.into()
        return joinUdpMulticastV4(this.inner.asRawFd(), addr1, iface1).mapErr(mapMotorError)
    }

    public open fun joinMulticastV6(addr: Ipv6Addr, iface: UInt): Result<Unit> {
        val addr1 = addr.into()
        return joinUdpMulticastV6(this.inner.asRawFd(), addr1, iface).mapErr(mapMotorError)
    }

    public open fun leaveMulticastV4(addr: Ipv4Addr, iface: Ipv4Addr): Result<Unit> {
        val addr1 = addr.into()
        val iface1 = iface.into()
        return leaveUdpMulticastV4(this.inner.asRawFd(), addr1, iface1).mapErr(mapMotorError)
    }

    public open fun leaveMulticastV6(addr: Ipv6Addr, iface: UInt): Result<Unit> {
        val addr1 = addr.into()
        return leaveUdpMulticastV6(this.inner.asRawFd(), addr1, iface).mapErr(mapMotorError)
    }

    public open fun setTtl(ttl: UInt): Result<Unit> = io.github.kotlinmania.motort.net.setTtl(this.inner.asRawFd(), ttl).mapErr(mapMotorError)

    public open fun ttl(): Result<UInt> = io.github.kotlinmania.motort.net.ttl(this.inner.asRawFd()).mapErr(mapMotorError)

    public open fun setMulticastHopLimitV6(arg0: UByte): Result<Unit> = unsupported()

    public open fun multicastHopLimitV6(): Result<UByte> = unsupported()

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = unsupported()

    public open fun hopLimitV6(): Result<UByte> = unsupported()

    public open fun takeError(): Result<Error?> = io.github.kotlinmania.motort.net.takeError(this.inner.asRawFd()).map({ e -> e.map(mapMotorError) }).mapErr(mapMotorError)

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = io.github.kotlinmania.motort.net.setNonblocking(this.inner.asRawFd(), nonblocking).mapErr(mapMotorError)

    public open fun recv(buf: MutableList<UByte>): Result<Int> = read(this.inner.asRawFd(), buf).mapErr(mapMotorError)

    public open fun peek(buf: MutableList<UByte>): Result<Int> = io.github.kotlinmania.motort.net.peek(this.inner.asRawFd(), buf).mapErr(mapMotorError)

    public open fun send(buf: List<UByte>): Result<Int> = write(this.inner.asRawFd(), buf).mapErr(mapMotorError)

    public open fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<Unit> {
        val addr1 = intoNetc((when (val tryResult = addr.toSocketAddrs()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.next())!!)
        return udpConnect(this.inner.asRawFd(), addr1).mapErr(mapMotorError)
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<UdpSocket> {
            val addr1 = intoNetc((when (val tryResult = addr.toSocketAddrs()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.next())!!)
            return io.github.kotlinmania.motort.net.bind(PROTO_UDP, addr1).map({ fd -> UdpSocket(inner = Socket.fromRawFd(fd)) }).mapErr(mapMotorError)
        }

        public fun fromInner(socket: Socket): UdpSocket = UdpSocket(inner = socket)
    }
}

public open class LookupHost internal constructor(
    addressesCtorArg: VecDeque<netc.Sockaddr>,
) : Iterable<SocketAddr> {
    public var addresses: VecDeque<netc.Sockaddr> = addressesCtorArg
        internal set

    override fun iterator(): kotlin.collections.Iterator<SocketAddr> = object : AbstractIterator<SocketAddr>() {
        override fun computeNext() {
            when (val v = this@LookupHost.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): SocketAddr? = this.addresses.removeFirstOrNull()?.let({ addr -> fromNetc(addr) })

    public companion object {
        public operator fun invoke(
            addresses: VecDeque<netc.Sockaddr>,
        ): LookupHost = LookupHost(
            addressesCtorArg = addresses,
        )

        public fun tryFrom(hostPort: String): Result<LookupHost> {
            val (host, portStr) = when (val tryResult = ((hostPort.let { s -> val idx = s.lastIndexOf(':'); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) })?.let { Result.Ok(it) } ?: Result.Err(io.github.kotlinmania.motort.Error.InvalidArgument)).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val port: UShort = when (val tryResult = (portStr.toUShortOrNull()?.let { Result.Ok(it) } ?: Result.Err("failed to parse UShort")).mapErr({ io.github.kotlinmania.motort.Error.InvalidArgument }).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Pair(host, port).tryInto()
        }

        public fun tryFrom(hostPort: Pair<String, UShort>): Result<LookupHost> {
            val (host, port) = hostPort
            val (port, addresses) = when (val tryResult = io.github.kotlinmania.motort.net.lookupHost(host, port).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(LookupHost(addresses = addresses))
        }
    }
}

public fun lookupHost(host: String, port: UShort): Result<LookupHost> {
    val (port, addresses) = when (val tryResult = io.github.kotlinmania.motort.net.lookupHost(host, port).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(LookupHost(addresses = addresses))
}

public fun intoNetc(addr: SocketAddr): netc.Sockaddr =
    when (addr) {
        is V4 -> {
            val (addr4) = addr
            netc.Sockaddr(v4 = addr4.into())
        }
        is V6 -> {
            val (addr6) = addr
            netc.Sockaddr(v6 = addr6.into())
        }
    }

public fun fromNetc(addr: netc.Sockaddr): SocketAddr {
    // SAFETY: all variants of union netc.sockaddr have `sinFamily` at the same offset.
    val family = addr.v4.sinFamily
    return when (family) {
        netc.afInet -> SocketAddr.v4(SocketAddrV4.from(addr.v4))
        netc.afInet6 -> SocketAddr.v6(SocketAddrV6.from(addr.v6))
        else -> error("bad sin_family $family")
    }
}
