package io.github.kotlinmania.rustlang.std.sys.net.connection.uefi

import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sys.net.connection.eachAddr
import io.github.kotlinmania.rustlang.std.sys.net.connection.uefi.tcp.Tcp
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported

public open class TcpStream internal constructor(
    public var inner: Tcp,
    readTimeoutCtorArg: Mutex<Duration?>,
    writeTimeoutCtorArg: Mutex<Duration?>,
) {
    public var readTimeout: Mutex<Duration?> = readTimeoutCtorArg
        internal set

    public var writeTimeout: Mutex<Duration?> = writeTimeoutCtorArg
        internal set

    public open fun setReadTimeout(t: Duration?): Result<Unit> {
        if (t == Duration.ZERO) {
            return Result.Err(Error.ZERO_TIMEOUT)
        }
        this.readTimeout.set(t).unwrap()
        return Result.Ok(Unit)
    }

    public open fun setWriteTimeout(t: Duration?): Result<Unit> {
        if (t == Duration.ZERO) {
            return Result.Err(Error.ZERO_TIMEOUT)
        }
        this.writeTimeout.set(t).unwrap()
        return Result.Ok(Unit)
    }

    public open fun readTimeout(): Result<Duration?> = Result.Ok(this.readTimeout.getCloned().unwrap())

    public open fun writeTimeout(): Result<Duration?> = Result.Ok(this.writeTimeout.getCloned().unwrap())

    public open fun peek(arg0: MutableList<UByte>): Result<Int> = unsupported()

    public open fun read(buf: MutableList<UByte>): Result<Int> {
        return this.inner.read(buf, when (val tryResult = this.readTimeout()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ buf -> this.read(buf) }, cursor)

    public open fun readVectored(buf: MutableList<IoSliceMut>): Result<Int> {
        return this.inner.readVectored(buf, when (val tryResult = this.readTimeout()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun isReadVectored(): Boolean = true

    public open fun write(buf: List<UByte>): Result<Int> {
        return this.inner.write(buf, when (val tryResult = this.writeTimeout()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun writeVectored(buf: List<IoSlice>): Result<Int> {
        return this.inner.writeVectored(buf, when (val tryResult = this.writeTimeout()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun peerAddr(): Result<SocketAddr> = this.inner.peerAddr()

    public open fun socketAddr(): Result<SocketAddr> = this.inner.socketAddr()

    public open fun shutdown(arg0: Shutdown): Result<Unit> = unsupported()

    public open fun duplicate(): Result<TcpStream> = unsupported()

    public open fun setLinger(arg0: Duration?): Result<Unit> = unsupported()

    public open fun linger(): Result<Duration?> = unsupported()

    public open fun setKeepalive(arg0: Boolean): Result<Unit> = unsupported()

    public open fun keepalive(): Result<Boolean> = unsupported()

    public open fun setNodelay(arg0: Boolean): Result<Unit> = unsupported()

    public open fun nodelay(): Result<Boolean> = this.inner.nodelay()

    public open fun setTtl(arg0: UInt): Result<Unit> = unsupported()

    public open fun ttl(): Result<UInt> = this.inner.ttl()

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = unsupported()

    public open fun hopLimitV6(): Result<UByte> = unsupported()

    public open fun takeError(): Result<Error?> = unsupported()

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = unsupported()

    public open fun fmt(f: StringBuilder) {
        // Note(implement this)
        error("unsupported")
    }

    public companion object {
        public operator fun invoke(
            inner: Tcp,
            readTimeout: Mutex<Duration?>,
            writeTimeout: Mutex<Duration?>,
        ): TcpStream = TcpStream(
            inner = inner,
            readTimeoutCtorArg = readTimeout,
            writeTimeoutCtorArg = writeTimeout,
        )

        public fun new(inner: Tcp): TcpStream = TcpStream(inner = inner, readTimeout = Mutex.new(null), writeTimeout = Mutex.new(null))

        public fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<TcpStream> {
            fun inner(addr1: SocketAddr): Result<TcpStream> {
                val inner = when (val tryResult = Tcp.connect(addr1, null)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                return Result.Ok(TcpStream.new(inner))
            }
            return eachAddr(addr, inner)
        }

        public fun connectTimeout(addr: SocketAddr, timeout: Duration): Result<TcpStream> {
            if (timeout == Duration.ZERO) {
                return Result.Err(Error.ZERO_TIMEOUT)
            }
            val inner = when (val tryResult = Tcp.connect(addr, timeout)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(TcpStream.new(inner))
        }
    }
}

public open class TcpListener(
    public var inner: Tcp,
) {
    public open fun socketAddr(): Result<SocketAddr> = this.inner.socketAddr()

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> {
        val tcp = when (val tryResult = this.inner.accept()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val addr = when (val tryResult = tcp.peerAddr()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(TcpStream.new(tcp), addr))
    }

    public open fun duplicate(): Result<TcpListener> = unsupported()

    public open fun setTtl(arg0: UInt): Result<Unit> = unsupported()

    public open fun ttl(): Result<UInt> = this.inner.ttl()

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = unsupported()

    public open fun hopLimitV6(): Result<UByte> = unsupported()

    public open fun setOnlyV6(arg0: Boolean): Result<Unit> = unsupported()

    public open fun onlyV6(): Result<Boolean> = unsupported()

    public open fun takeError(): Result<Error?> = unsupported()

    public open fun setNonblocking(arg0: Boolean): Result<Unit> = unsupported()

    public open fun fmt(f: StringBuilder) {
        // Note(implement this)
        error("unsupported")
    }

    public companion object {
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<TcpListener> {
            fun inner(addr1: SocketAddr): Result<TcpListener> {
                val inner = when (val tryResult = Tcp.bind(addr1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                return Result.Ok(TcpListener(inner = inner))
            }
            return eachAddr(addr, inner)
        }
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
