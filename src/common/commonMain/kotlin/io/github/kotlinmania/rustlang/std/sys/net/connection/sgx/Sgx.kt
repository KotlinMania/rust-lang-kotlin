// port-lint: source sys/net/connection/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.sgx

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.TryIntoInner
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.acceptStream
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.bindStream
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.connectStream
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.write
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.sgxIneffective
import io.github.kotlinmania.rustlang.std.usercalls.raw.Fd

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

public val DEFAULT_FAKE_TTL: UInt = 64u

public val DEFAULT_FAKE_HLIM: UByte = 64u

public open class Socket(
    public var inner: FileDesc,
    public var localAddr: String?,
) : AsInner<FileDesc>, TryIntoInner<FileDesc, Socket>, FromInner<Pair<FileDesc, String?>, Socket> {
    public override fun asInner(): FileDesc = this.inner

    public override fun tryIntoInner(): Result<FileDesc, Socket> {
        val inner = this.inner
        val localAddr = this.localAddr
        return Arc.tryUnwrap(inner).mapErr({ inner1 -> Socket(inner = inner1, localAddr = localAddr) })
    }

    public companion object {
        public fun new(fd: Fd, localAddr: String): Socket = Socket(inner = FileDesc.new(fd), localAddr = localAddr)

        public fun fromInner(tupleArg0: Pair<FileDesc, String?>): Socket = Socket(inner = inner, localAddr = localAddr)
    }
}

public open class TcpStream(
    public var inner: Socket,
    public var peerAddr: String?,
) : AsInner<Socket>, IntoInner<Pair<Socket, String?>>, FromInner<Pair<Socket, String?>, TcpStream> {
    public open fun fmt(f: StringBuilder) {
        val res = DebugStruct(f, "TcpStream")
        when (val matchVal = this.inner.localAddr) {
            null -> {}
            else -> {
                val addr = matchVal
                res.field("addr", addr)
            }
        }
        when (val matchVal1 = this.peerAddr) {
            null -> {}
            else -> {
                val peer = matchVal1
                res.field("peer", peer)
            }
        }
        res.field("fd", this.inner.inner.asInner()).finish()
    }

    public open fun setReadTimeout(dur: Duration?): Result<Unit> {
        return when {
            dur != null && dur == Duration.default() -> {
                val dur1 = dur
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
            else -> sgxIneffective()
        }
    }

    public open fun setWriteTimeout(dur: Duration?): Result<Unit> {
        return when {
            dur != null && dur == Duration.default() -> {
                val dur1 = dur
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
            else -> sgxIneffective()
        }
    }

    public open fun readTimeout(): Result<Duration?> = sgxIneffective(null)

    public open fun writeTimeout(): Result<Duration?> = sgxIneffective(null)

    public open fun peek(arg0: MutableList<UByte>): Result<Int> = Result.Ok(0)

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.inner.inner.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.inner.inner.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.inner.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.inner.isReadVectored()

    public open fun write(buf: List<UByte>): Result<Int> = this.inner.inner.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.inner.inner.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.inner.inner.isWriteVectored()

    public open fun peerAddr(): Result<SocketAddr> = addrToSockaddr(this.peerAddr)

    public open fun socketAddr(): Result<SocketAddr> = addrToSockaddr(this.inner.localAddr)

    public open fun shutdown(arg0: Shutdown): Result<Unit> = sgxIneffective()

    public open fun duplicate(): Result<TcpStream> = Result.Ok(this.clone())

    public open fun setLinger(arg0: Duration?): Result<Unit> = sgxIneffective()

    public open fun linger(): Result<Duration?> = sgxIneffective(null)

    public open fun setKeepalive(arg0: Boolean): Result<Unit> = sgxIneffective()

    public open fun keepalive(): Result<Boolean> = sgxIneffective(false)

    public open fun setNodelay(arg0: Boolean): Result<Unit> = sgxIneffective()

    public open fun nodelay(): Result<Boolean> = sgxIneffective(false)

    public open fun setTtl(arg0: UInt): Result<Unit> = sgxIneffective()

    public open fun ttl(): Result<UInt> = sgxIneffective(DEFAULT_FAKE_TTL)

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = sgxIneffective()

    public open fun hopLimitV6(): Result<UByte> = sgxIneffective(DEFAULT_FAKE_HLIM)

    public open fun takeError(): Result<Error?> = Result.Ok(null)

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = sgxIneffective()

    public override fun asInner(): Socket = this.inner

    // `Inner` includes `peerAddr` so that a `TcpStream` maybe correctly
    // reconstructed if `Socket.tryIntoInner` fails.
    public override fun intoInner(): Pair<Socket, String?> = Pair(this.inner, this.peerAddr)

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<TcpStream> {
            return eachAddr(addr, closure@{ addr1 ->
                val (fd, localAddr, peerAddr) = when (val tryResult = connectStream(addr1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
                Result.Ok(TcpStream(inner = Socket.new(fd, localAddr), peerAddr = peerAddr))
            })
        }

        public fun connectTimeout(addr: SocketAddr, dur: Duration): Result<TcpStream> {
            if (dur == Duration.default()) {
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
            return TcpStream.connect(addr)
            // Note: ignoring timeout
        }

        public fun fromInner(tupleArg0: Pair<Socket, String?>): TcpStream = TcpStream(inner = inner, peerAddr = peerAddr)
    }
}

// Note: This is a terrible, terrible Note. Fixing this requires Fortanix to
// add a method for resolving addresses.

/**
 * Converts each address in `addr` into a hostname.
 *
 * SGX doesn't support DNS resolution but rather accepts hostnames in
 * the same place as socket addresses. So, to make e.g.
 * ```kotlin
 * TcpStream.connect("example.com:80")
 * ```
 * work, the DNS lookup returns a special error (`NonIpSockAddr`) instead,
 * which contains the hostname being looked up. When `.toSocketAddrs()`
 * fails, we inspect the error and try recover the hostname from it. If that
 * succeeds, we thus continue with the hostname.
 *
 * This is a terrible Note and leads to buggy code. For instance, when users
 * use the result of `.toSocketAddrs()` in their own `ToSocketAddrs`
 * implementation to select from a list of possible URLs, the only URL used
 * will be that of the last item tried.
 */
public fun <Iter, A : ToSocketAddrs<Any?>, T> eachAddr(addr: A, f: (String) -> Result<T>): Result<T> {
    return run {
        val matchVal = addr.toSocketAddrs()
        when (matchVal) { is Result.Ok<*> -> {
            val addrs = matchVal.value
            var lastErr = null
            var encoded = ""
            for (addr1 in addrs) {
                // Format the IP address as a string, reusing the buffer.
                encoded = ""
                encoded.append("$addr1").unwrap()
                run {
                    val matchVal1 = f(encoded)
                    when (matchVal1) { is Result.Ok<*> -> {
                        val value = matchVal1.value
                        return Result.Ok(value)
                    }; is Result.Err<*> -> {
                        val err = matchVal1.error
                        lastErr = err
                    } }
                }
            }
            when (lastErr) {
                null -> Result.Err(io.Error.NO_ADDRESSES)
                else -> {
                    val err1 = lastErr
                    Result.Err(err1)
                }
            }
        }; is Result.Err<*> -> {
            val err2 = matchVal.error
            when (val matchVal2 = err2.getRef().andThen({ e -> e.downcastRef<NonIpSockAddr>() })) {
                else -> {
                    val host = matchVal2.host
                    f(host)
                }
                null -> Result.Err(err2)
            }
        } }
    }
}

public fun addrToSockaddr(addr: String?): Result<SocketAddr> {
    return when (val tryResult = (addr?.let { Result.Ok(it) } ?: Result.Err(io.ErrorKind.AddrNotAvailable))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.toSocketAddrs().map({ it -> it.next()!! })
}

public open class TcpListener(
    public var inner: Socket,
) : AsInner<Socket>, IntoInner<Socket>, FromInner<Socket, TcpListener> {
    public open fun fmt(f: StringBuilder) {
        val res = DebugStruct(f, "TcpListener")
        when (val matchVal = this.inner.localAddr) {
            null -> {}
            else -> {
                val addr = matchVal
                res.field("addr", addr)
            }
        }
        res.field("fd", this.inner.inner.asInner()).finish()
    }

    public open fun socketAddr(): Result<SocketAddr> = addrToSockaddr(this.inner.localAddr)

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> {
        val (fd, localAddr, peerAddr) = when (val tryResult = acceptStream(this.inner.inner.raw())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val peerAddr1 = peerAddr
        val retPeer = addrToSockaddr(peerAddr1.asDeref()).unwrapOrElse({ Pair(List(4) { 0 }, 0).into() })
        return Result.Ok(Pair(TcpStream(inner = Socket.new(fd, localAddr), peerAddr = peerAddr1), retPeer))
    }

    public open fun duplicate(): Result<TcpListener> = Result.Ok(this.clone())

    public open fun setTtl(arg0: UInt): Result<Unit> = sgxIneffective()

    public open fun ttl(): Result<UInt> = sgxIneffective(DEFAULT_FAKE_TTL)

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = sgxIneffective()

    public open fun hopLimitV6(): Result<UByte> = sgxIneffective(DEFAULT_FAKE_HLIM)

    public open fun setOnlyV6(arg0: Boolean): Result<Unit> = sgxIneffective()

    public open fun onlyV6(): Result<Boolean> = sgxIneffective(false)

    public open fun takeError(): Result<Error?> = Result.Ok(null)

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = sgxIneffective()

    public override fun asInner(): Socket = this.inner

    public override fun intoInner(): Socket = this.inner

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<TcpListener> {
            return eachAddr(addr, closure1@{ addr1 ->
                val (fd, localAddr) = when (val tryResult = bindStream(addr1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure1 Result.Err(tryResult.error) }
                Result.Ok(TcpListener(inner = Socket.new(fd, localAddr)))
            })
        }

        public fun fromInner(inner: Socket): TcpListener = TcpListener(inner = inner)
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

public open class NonIpSockAddr(
    public var host: String,
) {
    public open fun fmt(f: StringBuilder) {
        f.append("Failed to convert address to SocketAddr: ${this.host}")
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

internal fun lookupHostString(addr: Any): Result<LookupHost> = Result.Err(io.Error.new(io.ErrorKind.Uncategorized, NonIpSockAddr(host = addr.into())))

public fun lookupHost(host: String, port: UShort): Result<LookupHost> = lookupHostString("$host:$port")
