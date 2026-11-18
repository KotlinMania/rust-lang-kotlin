// port-lint: source sys/net/connection/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.unsupported

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
import io.github.kotlinmania.rustlang.std.sys.pal.unsupported.unsupported

public open class TcpStream(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun setReadTimeout(arg0: Duration?): Result<Unit, Error> = this.value

    public open fun setWriteTimeout(arg0: Duration?): Result<Unit, Error> = this.value

    public open fun readTimeout(): Result<Duration?, Error> = this.value

    public open fun writeTimeout(): Result<Duration?, Error> = this.value

    public open fun peek(arg0: MutableList<UByte>): Result<Int, Error> = this.value

    public open fun read(arg0: MutableList<UByte>): Result<Int, Error> = this.value

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.value

    public open fun readVectored(arg0: MutableList<IoSliceMut>): Result<Int, Error> = this.value

    public open fun isReadVectored(): Boolean = this.value

    public open fun write(arg0: List<UByte>): Result<Int, Error> = this.value

    public open fun writeVectored(arg0: List<IoSlice>): Result<Int, Error> = this.value

    public open fun isWriteVectored(): Boolean = this.value

    public open fun peerAddr(): Result<SocketAddr, Error> = this.value

    public open fun socketAddr(): Result<SocketAddr, Error> = this.value

    public open fun shutdown(arg0: Shutdown): Result<Unit, Error> = this.value

    public open fun duplicate(): Result<TcpStream, Error> = this.value

    public open fun setLinger(arg0: Duration?): Result<Unit, Error> = this.value

    public open fun linger(): Result<Duration?, Error> = this.value

    public open fun setKeepalive(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun keepalive(): Result<Boolean, Error> = this.value

    public open fun setNodelay(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun nodelay(): Result<Boolean, Error> = this.value

    public open fun setTtl(arg0: UInt): Result<Unit, Error> = this.value

    public open fun ttl(): Result<UInt, Error> = this.value

    public open fun setHopLimitV6(arg0: UByte): Result<Unit, Error> = this.value

    public open fun hopLimitV6(): Result<UByte, Error> = this.value

    public open fun takeError(): Result<Error?, Error> = this.value

    public open fun setNonblocking(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> connect(arg0: A): Result<TcpStream, Error> = unsupported()

        public fun connectTimeout(arg0: SocketAddr, arg1: Duration): Result<TcpStream, Error> = unsupported()
    }
}

public open class TcpListener(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun socketAddr(): Result<SocketAddr, Error> = this.value

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>, Error> = this.value

    public open fun duplicate(): Result<TcpListener, Error> = this.value

    public open fun setTtl(arg0: UInt): Result<Unit, Error> = this.value

    public open fun ttl(): Result<UInt, Error> = this.value

    public open fun setHopLimitV6(arg0: UByte): Result<Unit, Error> = this.value

    public open fun hopLimitV6(): Result<UByte, Error> = this.value

    public open fun setOnlyV6(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun onlyV6(): Result<Boolean, Error> = this.value

    public open fun takeError(): Result<Error?, Error> = this.value

    public open fun setNonblocking(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(arg0: A): Result<TcpListener, Error> = unsupported()
    }
}

public open class UdpSocket(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun peerAddr(): Result<SocketAddr, Error> = this.value

    public open fun socketAddr(): Result<SocketAddr, Error> = this.value

    public open fun recvFrom(arg0: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.value

    public open fun peekFrom(arg0: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.value

    public open fun sendTo(arg0: List<UByte>, arg1: SocketAddr): Result<Int, Error> = this.value

    public open fun duplicate(): Result<UdpSocket, Error> = this.value

    public open fun setReadTimeout(arg0: Duration?): Result<Unit, Error> = this.value

    public open fun setWriteTimeout(arg0: Duration?): Result<Unit, Error> = this.value

    public open fun readTimeout(): Result<Duration?, Error> = this.value

    public open fun writeTimeout(): Result<Duration?, Error> = this.value

    public open fun setBroadcast(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun broadcast(): Result<Boolean, Error> = this.value

    public open fun setMulticastLoopV4(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun multicastLoopV4(): Result<Boolean, Error> = this.value

    public open fun setMulticastTtlV4(arg0: UInt): Result<Unit, Error> = this.value

    public open fun multicastTtlV4(): Result<UInt, Error> = this.value

    public open fun setMulticastLoopV6(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun multicastLoopV6(): Result<Boolean, Error> = this.value

    public open fun joinMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit, Error> = this.value

    public open fun joinMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit, Error> = this.value

    public open fun leaveMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit, Error> = this.value

    public open fun leaveMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit, Error> = this.value

    public open fun setTtl(arg0: UInt): Result<Unit, Error> = this.value

    public open fun ttl(): Result<UInt, Error> = this.value

    public open fun setHopLimitV6(arg0: UByte): Result<Unit, Error> = this.value

    public open fun hopLimitV6(): Result<UByte, Error> = this.value

    public open fun setMulticastHopLimitV6(arg0: UByte): Result<Unit, Error> = this.value

    public open fun multicastHopLimitV6(): Result<UByte, Error> = this.value

    public open fun takeError(): Result<Error?, Error> = this.value

    public open fun setNonblocking(arg0: Boolean): Result<Unit, Error> = this.value

    public open fun recv(arg0: MutableList<UByte>): Result<Int, Error> = this.value

    public open fun peek(arg0: MutableList<UByte>): Result<Int, Error> = this.value

    public open fun send(arg0: List<UByte>): Result<Int, Error> = this.value

    public open fun <Iter, A : ToSocketAddrs<Iter>> connect(arg0: A): Result<Unit, Error> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(arg0: A): Result<UdpSocket, Error> = unsupported()
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

    public companion object
}

public fun lookupHost(host: String, port: UShort): Result<LookupHost, Error> = unsupported()
