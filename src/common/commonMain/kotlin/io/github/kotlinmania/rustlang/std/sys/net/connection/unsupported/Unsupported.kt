// port-lint: source sys/net/connection/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.unsupported

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.thread.Result

public open class TcpStream(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun setReadTimeout(arg0: Duration?): Result<Unit> = this.value

    public open fun setWriteTimeout(arg0: Duration?): Result<Unit> = this.value

    public open fun readTimeout(): Result<Duration?> = this.value

    public open fun writeTimeout(): Result<Duration?> = this.value

    public open fun peek(arg0: MutableList<UByte>): Result<Int> = this.value

    public open fun read(arg0: MutableList<UByte>): Result<Int> = this.value

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.value

    public open fun readVectored(arg0: MutableList<IoSliceMut>): Result<Int> = this.value

    public open fun isReadVectored(): Boolean = this.value

    public open fun write(arg0: List<UByte>): Result<Int> = this.value

    public open fun writeVectored(arg0: List<IoSlice>): Result<Int> = this.value

    public open fun isWriteVectored(): Boolean = this.value

    public open fun peerAddr(): Result<SocketAddr> = this.value

    public open fun socketAddr(): Result<SocketAddr> = this.value

    public open fun shutdown(arg0: Shutdown): Result<Unit> = this.value

    public open fun duplicate(): Result<TcpStream> = this.value

    public open fun setLinger(arg0: Duration?): Result<Unit> = this.value

    public open fun linger(): Result<Duration?> = this.value

    public open fun setKeepalive(arg0: Boolean): Result<Unit> = this.value

    public open fun keepalive(): Result<Boolean> = this.value

    public open fun setNodelay(arg0: Boolean): Result<Unit> = this.value

    public open fun nodelay(): Result<Boolean> = this.value

    public open fun setTtl(arg0: UInt): Result<Unit> = this.value

    public open fun ttl(): Result<UInt> = this.value

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = this.value

    public open fun hopLimitV6(): Result<UByte> = this.value

    public open fun takeError(): Result<Error?> = this.value

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = this.value

    public open fun fmt(f: StringBuilder) {
        this.value
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> connect(arg0: A): Result<TcpStream> = unsupported()

        public fun connectTimeout(arg0: SocketAddr, arg1: Duration): Result<TcpStream> = unsupported()
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

    public open fun socketAddr(): Result<SocketAddr> = this.value

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> = this.value

    public open fun duplicate(): Result<TcpListener> = this.value

    public open fun setTtl(arg0: UInt): Result<Unit> = this.value

    public open fun ttl(): Result<UInt> = this.value

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = this.value

    public open fun hopLimitV6(): Result<UByte> = this.value

    public open fun setOnlyV6(arg0: Boolean): Result<Unit> = this.value

    public open fun onlyV6(): Result<Boolean> = this.value

    public open fun takeError(): Result<Error?> = this.value

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = this.value

    public open fun fmt(f: StringBuilder) {
        this.value
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(arg0: A): Result<TcpListener> = unsupported()
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

    public open fun peerAddr(): Result<SocketAddr> = this.value

    public open fun socketAddr(): Result<SocketAddr> = this.value

    public open fun recvFrom(arg0: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.value

    public open fun peekFrom(arg0: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.value

    public open fun sendTo(arg0: List<UByte>, arg1: SocketAddr): Result<Int> = this.value

    public open fun duplicate(): Result<UdpSocket> = this.value

    public open fun setReadTimeout(arg0: Duration?): Result<Unit> = this.value

    public open fun setWriteTimeout(arg0: Duration?): Result<Unit> = this.value

    public open fun readTimeout(): Result<Duration?> = this.value

    public open fun writeTimeout(): Result<Duration?> = this.value

    public open fun setBroadcast(arg0: Boolean): Result<Unit> = this.value

    public open fun broadcast(): Result<Boolean> = this.value

    public open fun setMulticastLoopV4(arg0: Boolean): Result<Unit> = this.value

    public open fun multicastLoopV4(): Result<Boolean> = this.value

    public open fun setMulticastTtlV4(arg0: UInt): Result<Unit> = this.value

    public open fun multicastTtlV4(): Result<UInt> = this.value

    public open fun setMulticastLoopV6(arg0: Boolean): Result<Unit> = this.value

    public open fun multicastLoopV6(): Result<Boolean> = this.value

    public open fun joinMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit> = this.value

    public open fun joinMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit> = this.value

    public open fun leaveMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit> = this.value

    public open fun leaveMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit> = this.value

    public open fun setTtl(arg0: UInt): Result<Unit> = this.value

    public open fun ttl(): Result<UInt> = this.value

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = this.value

    public open fun hopLimitV6(): Result<UByte> = this.value

    public open fun setMulticastHopLimitV6(arg0: UByte): Result<Unit> = this.value

    public open fun multicastHopLimitV6(): Result<UByte> = this.value

    public open fun takeError(): Result<Error?> = this.value

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = this.value

    public open fun recv(arg0: MutableList<UByte>): Result<Int> = this.value

    public open fun peek(arg0: MutableList<UByte>): Result<Int> = this.value

    public open fun send(arg0: List<UByte>): Result<Int> = this.value

    public open fun <Iter, A : ToSocketAddrs<Any?>> connect(arg0: A): Result<Unit> = this.value

    public open fun fmt(f: StringBuilder) {
        this.value
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(arg0: A): Result<UdpSocket> = unsupported()
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

    override fun iterator(): kotlin.collections.Iterator<SocketAddr> = object : AbstractIterator<SocketAddr>() {
        override fun computeNext() {
            when (val v = this@LookupHost.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): SocketAddr? = this.value
}

public fun lookupHost(host: String, port: UShort): Result<LookupHost> = unsupported()
