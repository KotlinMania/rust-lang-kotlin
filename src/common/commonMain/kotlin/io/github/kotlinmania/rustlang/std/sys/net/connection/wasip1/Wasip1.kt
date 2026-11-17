// port-lint: source sys/net/connection/wasip1.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.wasip1

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.fd.asFd
import io.github.kotlinmania.rustlang.std.os.fd.asRawFd
import io.github.kotlinmania.rustlang.std.os.fd.intoRawFd
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.asInner
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.pal.unsupported.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.wasi.err2io
import io.github.kotlinmania.rustlang.wasip1.FDFLAGS_NONBLOCK
import io.github.kotlinmania.rustlang.wasip1.Fd
import io.github.kotlinmania.rustlang.wasip1.SDFLAGS_RD
import io.github.kotlinmania.rustlang.wasip1.SDFLAGS_WR
import io.github.kotlinmania.rustlang.wasip1.fdFdstatGet
import io.github.kotlinmania.rustlang.wasip1.fdFdstatSetFlags
import io.github.kotlinmania.rustlang.wasip1.sockAccept
import io.github.kotlinmania.rustlang.wasip1.sockShutdown

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

public open class Socket(
    public var value: FileDesc,
) : AsInner<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, Socket>, AsFd, AsRawFd, IntoRawFd, FromRawFd<Socket> {
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
        public fun fromInner(inner: FileDesc): Socket = Socket(inner)

        public fun fromRawFd(rawFd: RawFd): Socket = Socket(FromRawFd.FromRawFd(rawFd))
    }
}

public open class TcpStream(
    public var inner: Socket,
) : FromInner<Socket, TcpStream> {
    public open fun setReadTimeout(arg0: Duration?): Result<Unit, Error> = unsupported()

    public open fun setWriteTimeout(arg0: Duration?): Result<Unit, Error> = unsupported()

    public open fun readTimeout(): Result<Duration?, Error> = unsupported()

    public open fun writeTimeout(): Result<Duration?, Error> = unsupported()

    public open fun peek(arg0: MutableList<UByte>): Result<Int, Error> = unsupported()

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.readVectored(listOf(IoSliceMut.new(buf)))

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.socket().asInner().readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.socket().asInner().readVectored(bufs)

    public open fun isReadVectored(): Boolean = true

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.writeVectored(listOf(IoSlice.new(buf)))

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.socket().asInner().writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = true

    public open fun peerAddr(): Result<SocketAddr, Error> = unsupported()

    public open fun socketAddr(): Result<SocketAddr, Error> = unsupported()

    public open fun shutdown(how: Shutdown): Result<Unit, Error> {
        val wasiHow =
            when (how) {
                Shutdown.Read -> SDFLAGS_RD
                Shutdown.Write -> SDFLAGS_WR
                Shutdown.Both -> SDFLAGS_RD or SDFLAGS_WR
            }
        return sockShutdown(this.socket().asRawFd(), wasiHow).mapErr(err2io)
    }

    public open fun duplicate(): Result<TcpStream, Error> = unsupported()

    public open fun setLinger(arg0: Duration?): Result<Unit, Error> = unsupported()

    public open fun linger(): Result<Duration?, Error> = unsupported()

    public open fun setKeepalive(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun keepalive(): Result<Boolean, Error> = unsupported()

    public open fun setNodelay(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun nodelay(): Result<Boolean, Error> = unsupported()

    public open fun setTtl(arg0: UInt): Result<Unit, Error> = unsupported()

    public open fun ttl(): Result<UInt, Error> = unsupported()

    public open fun setHopLimitV6(arg0: UByte): Result<Unit, Error> = unsupported()

    public open fun hopLimitV6(): Result<UByte, Error> = unsupported()

    public open fun takeError(): Result<Error?, Error> = unsupported()

    public open fun setNonblocking(state: Boolean): Result<Unit, Error> {
        val fdstat = when (val tryResult = fdFdstatGet(((this.socket().asInner().asRawFd() as Any?) as Fd)).mapErr(err2io)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        var flags = fdstat.fsFlags
        if (state) {
            flags = flags or FDFLAGS_NONBLOCK
        } else {
            flags = flags and FDFLAGS_NONBLOCK.inv()
        }
        return fdFdstatSetFlags(((this.socket().asInner().asRawFd() as Any?) as Fd), flags).mapErr(err2io)
    }

    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "TcpStream").field("fd", this.inner.asRawFd()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> connect(arg0: A): Result<TcpStream, Error> = unsupported()

        public fun connectTimeout(arg0: SocketAddr, arg1: Duration): Result<TcpStream, Error> = unsupported()

        public fun fromInner(socket: Socket): TcpStream = TcpStream(inner = socket)
    }
}

public open class TcpListener(
    public var inner: Socket,
) : AsInner<Socket>, IntoInner<Socket>, FromInner<Socket, TcpListener> {
    public open fun socketAddr(): Result<SocketAddr, Error> = unsupported()

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>, Error> {
        val fd = when (val tryResult = sockAccept(this.asInner().asInner().asRawFd(), 0).mapErr(err2io)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<TcpStream, SocketAddr>, Error>(tryResult.errValue()) }
        return Result.Ok<Pair<TcpStream, SocketAddr>, Error>(Pair(TcpStream.FromInner(Socket.FromRawFd(fd.toInt())), SocketAddr.new(Ipv4Addr.UNSPECIFIED.into(), 0)))
    }

    public open fun duplicate(): Result<TcpListener, Error> = unsupported()

    public open fun setTtl(arg0: UInt): Result<Unit, Error> = unsupported()

    public open fun ttl(): Result<UInt, Error> = unsupported()

    public open fun setHopLimitV6(arg0: UByte): Result<Unit, Error> = unsupported()

    public open fun hopLimitV6(): Result<UByte, Error> = unsupported()

    public open fun setOnlyV6(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun onlyV6(): Result<Boolean, Error> = unsupported()

    public open fun takeError(): Result<Error?, Error> = unsupported()

    public open fun setNonblocking(state: Boolean): Result<Unit, Error> {
        val fdstat = when (val tryResult = fdFdstatGet(((this.socket().asInner().asRawFd() as Any?) as Fd)).mapErr(err2io)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        var flags = fdstat.fsFlags
        if (state) {
            flags = flags or FDFLAGS_NONBLOCK
        } else {
            flags = flags and FDFLAGS_NONBLOCK.inv()
        }
        return fdFdstatSetFlags(((this.socket().asInner().asRawFd() as Any?) as Fd), flags).mapErr(err2io)
    }

    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public override fun asInner(): Socket = this.inner

    public override fun intoInner(): Socket = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "TcpListener").field("fd", this.inner.asRawFd()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(arg0: A): Result<TcpListener, Error> = unsupported()

        public fun fromInner(inner: Socket): TcpListener = TcpListener(inner = inner)
    }
}

public open class UdpSocket(
    public var inner: Socket,
) : AsInner<Socket>, IntoInner<Socket>, FromInner<Socket, UdpSocket> {
    public open fun peerAddr(): Result<SocketAddr, Error> = unsupported()

    public open fun socketAddr(): Result<SocketAddr, Error> = unsupported()

    public open fun recvFrom(arg0: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = unsupported()

    public open fun peekFrom(arg0: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = unsupported()

    public open fun sendTo(arg0: List<UByte>, arg1: SocketAddr): Result<Int, Error> = unsupported()

    public open fun duplicate(): Result<UdpSocket, Error> = unsupported()

    public open fun setReadTimeout(arg0: Duration?): Result<Unit, Error> = unsupported()

    public open fun setWriteTimeout(arg0: Duration?): Result<Unit, Error> = unsupported()

    public open fun readTimeout(): Result<Duration?, Error> = unsupported()

    public open fun writeTimeout(): Result<Duration?, Error> = unsupported()

    public open fun setBroadcast(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun broadcast(): Result<Boolean, Error> = unsupported()

    public open fun setMulticastLoopV4(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun multicastLoopV4(): Result<Boolean, Error> = unsupported()

    public open fun setMulticastTtlV4(arg0: UInt): Result<Unit, Error> = unsupported()

    public open fun multicastTtlV4(): Result<UInt, Error> = unsupported()

    public open fun setMulticastLoopV6(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun multicastLoopV6(): Result<Boolean, Error> = unsupported()

    public open fun joinMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit, Error> = unsupported()

    public open fun joinMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit, Error> = unsupported()

    public open fun leaveMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit, Error> = unsupported()

    public open fun leaveMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit, Error> = unsupported()

    public open fun setTtl(arg0: UInt): Result<Unit, Error> = unsupported()

    public open fun ttl(): Result<UInt, Error> = unsupported()

    public open fun setHopLimitV6(arg0: UByte): Result<Unit, Error> = unsupported()

    public open fun hopLimitV6(): Result<UByte, Error> = unsupported()

    public open fun setMulticastHopLimitV6(arg0: UByte): Result<Unit, Error> = unsupported()

    public open fun multicastHopLimitV6(): Result<UByte, Error> = unsupported()

    public open fun takeError(): Result<Error?, Error> = unsupported()

    public open fun setNonblocking(arg0: Boolean): Result<Unit, Error> = unsupported()

    public open fun recv(arg0: MutableList<UByte>): Result<Int, Error> = unsupported()

    public open fun peek(arg0: MutableList<UByte>): Result<Int, Error> = unsupported()

    public open fun send(arg0: List<UByte>): Result<Int, Error> = unsupported()

    public open fun <Iter, A : ToSocketAddrs<Iter>> connect(arg0: A): Result<Unit, Error> = unsupported()

    public open fun socket(): Socket = this.inner

    public open fun intoSocket(): Socket = this.inner

    public override fun asInner(): Socket = this.inner

    public override fun intoInner(): Socket = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "UdpSocket").field("fd", this.inner.asRawFd()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(arg0: A): Result<UdpSocket, Error> = unsupported()

        public fun fromInner(inner: Socket): UdpSocket = UdpSocket(inner = inner)
    }
}

public open class LookupHost(
    public var value: Nothing,
) : Iterable<SocketAddr> {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<SocketAddr> = object : AbstractIterator<SocketAddr>() {
        override fun computeNext() {
            when (val v = this@LookupHost.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): SocketAddr? = this.value
}

public fun lookupHost(host: String, port: UShort): Result<LookupHost, Error> = unsupported()
