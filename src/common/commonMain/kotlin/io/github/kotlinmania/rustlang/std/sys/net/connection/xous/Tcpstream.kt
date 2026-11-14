// port-lint: source sys/net/connection/xous/tcpstream.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.xous

import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadVectored
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.defaultWriteVectored
import io.github.kotlinmania.rustlang.core.net.ipaddr.IpAddr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV6
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.or
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.blockingScalar
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.os.xous.ffi.lendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.net.NetBlockingScalar
import io.github.kotlinmania.rustlang.std.os.xous.services.net.NetLendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.net.netServer
import io.github.kotlinmania.rustlang.std.sys.net.connection.eachAddr

public sealed class ReadOrPeek {
    public data object Read : ReadOrPeek()
    public data object Peek : ReadOrPeek()
}

public open class TcpStream internal constructor(
    public var fd: UShort,
    public var localPort: UShort,
    public var remotePort: UShort,
    public var peerAddr: SocketAddr,
    readTimeoutCtorArg: Atomic<UInt>,
    writeTimeoutCtorArg: Atomic<UInt>,
    handleCountCtorArg: Atomic<Int>,
    nonblockingCtorArg: Atomic<Boolean>,
) {
    // milliseconds
    public var readTimeout: Atomic<UInt> = readTimeoutCtorArg
        internal set

    // milliseconds
    public var writeTimeout: Atomic<UInt> = writeTimeoutCtorArg
        internal set

    public var handleCount: Atomic<Int> = handleCountCtorArg
        internal set

    public var nonblocking: Atomic<Boolean> = nonblockingCtorArg
        internal set

    public open fun setReadTimeout(timeout: Duration?): Result<Unit> {
        if (timeout != null) {
            val to = timeout
            if (to.isZero()) {
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
        }
        this.readTimeout.store((timeout?.let({ t -> UInt.tryFrom(t.asMillis()).unwrapOr(UInt.MAX_VALUE).max(1) }) ?: 0), Ordering.Relaxed)
        return Result.Ok(Unit)
    }

    public open fun setWriteTimeout(timeout: Duration?): Result<Unit> {
        if (timeout != null) {
            val to = timeout
            if (to.isZero()) {
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
        }
        this.writeTimeout.store((timeout?.let({ t -> UInt.tryFrom(t.asMillis()).unwrapOr(UInt.MAX_VALUE).max(1) }) ?: 0), Ordering.Relaxed)
        return Result.Ok(Unit)
    }

    public open fun readTimeout(): Result<Duration?> =
        when (val matchVal = this.readTimeout.load(Ordering.Relaxed)) {
            0 -> Result.Ok(null)
            else -> {
                val t = matchVal
                Result.Ok(Duration.fromMillis(t.toULong()))
            }
        }

    public open fun writeTimeout(): Result<Duration?> =
        when (val matchVal = this.writeTimeout.load(Ordering.Relaxed)) {
            0 -> Result.Ok(null)
            else -> {
                val t = matchVal
                Result.Ok(Duration.fromMillis(t.toULong()))
            }
        }

    public open fun readOrPeek(buf: MutableList<UByte>, op: ReadOrPeek): Result<Int> {
        val receiveRequest = ReceiveData(raw = UByteArray(4096) { 0u.toUByte() })
        val dataToRead = buf.size.min(receiveRequest.raw.size)
        val opcode =
            when (op) {
                ReadOrPeek.Read -> {
                    NetLendMut.StdTcpRx(this.fd, this.nonblocking.load(Ordering.Relaxed))
                }
                ReadOrPeek.Peek -> {
                    NetLendMut.StdTcpPeek(this.fd, this.nonblocking.load(Ordering.Relaxed))
                }
            }
        val (offset, length) = lendMut(netServer(), opcode.into(), receiveRequest.raw, this.readTimeout.load(Ordering.Relaxed).toInt(), dataToRead) ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "library failure: wrong message type or messaging error"))
        return if (offset != 0) {
            for ((dest, src) in (buf.zip(((receiveRequest.raw).sliceArray(0 until length))))) {
                dest = src
            }
            Result.Ok(length)
        } else {
            val result = receiveRequest.raw
            if (result[0] != 0u.toUByte()) {
                // The error code lives at byte 4 of the kernel's response buffer
                // (matches the byte the send path reads in `write` below). Byte 1
                // is part of the marker header, not the code.
                if (result[4] == 8u.toUByte()) {
                    // timed out
                    return Result.Err(io.constError(io.ErrorKind.TimedOut, "timeout"))
                }
                if (result[4] == 9u.toUByte()) {
                    // would block
                    return Result.Err(io.constError(io.ErrorKind.WouldBlock, "would block"))
                }
            }
            Result.Err(io.constError(io.ErrorKind.Other, "recv_slice failure"))
        }
    }

    public open fun peek(buf: MutableList<UByte>): Result<Int> = this.readOrPeek(buf, ReadOrPeek.Peek)

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.readOrPeek(buf, ReadOrPeek.Read)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = defaultReadVectored({ b -> this.read(b) }, bufs)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ buf -> this.read(buf) }, cursor)

    public open fun isReadVectored(): Boolean = false

    public open fun write(buf: List<UByte>): Result<Int> {
        val sendRequest = SendData(raw = UByteArray(4096) { 0u.toUByte() })
        for ((dest, src) in (sendRequest.raw.iterMut().zip(buf))) {
            dest = src
        }
        val bufLen = sendRequest.raw.len().min(buf.size)
        val (offset, valid) = when (val tryResult = lendMut(netServer(), NetLendMut.StdTcpTx(this.fd).into(), sendRequest.raw, this.writeTimeout.load(Ordering.Relaxed).toInt(), bufLen).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "internal error")))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        if (sendRequest.raw[0] != 0) {
            if (sendRequest.raw[4] == 8) {
                // timed out
                return Result.Err(io.constError(io.ErrorKind.BrokenPipe, "timeout or connection closed"))
            } else if (sendRequest.raw[4] == 9) {
                // would block
                return Result.Err(io.constError(io.ErrorKind.WouldBlock, "would block"))
            } else {
                return Result.Err(io.constError(io.ErrorKind.InvalidInput, "error when sending"))
            }
        }
        return Result.Ok((run { val bytesTmp3 = ubyteArrayOf(sendRequest.raw[4], sendRequest.raw[5], sendRequest.raw[6], sendRequest.raw[7]); ((bytesTmp3[3].toUInt() shl 24) or (bytesTmp3[2].toUInt() shl 16) or (bytesTmp3[1].toUInt() shl 8) or bytesTmp3[0].toUInt()) }).toInt())
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = defaultWriteVectored({ b -> this.write(b) }, bufs)

    public open fun isWriteVectored(): Boolean = false

    public open fun peerAddr(): Result<SocketAddr> = Result.Ok(this.peerAddr)

    public open fun socketAddr(): Result<SocketAddr> {
        val getAddr = GetAddress(raw = UByteArray(4096) { 0u.toUByte() })
        val (offset, valid) = lendMut(netServer(), NetLendMut.StdGetAddress(this.fd).into(), getAddr.raw, 0, 0) ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "internal error"))
        val i = getAddr.raw.iter()
        return when (i.next()!!) {
            4 -> Result.Ok(SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(i.next()!!, i.next()!!, i.next()!!, i.next()!!), this.localPort)))
            6 -> {
                val newAddr = UByteArray(16) { 0u.toUByte() }
                for ((src, octet) in (i.zip(newAddr))) {
                    octet = src
                }
                Result.Ok(SocketAddr.v6(SocketAddrV6.new(newAddr.into(), this.localPort, 0, 0)))
            }
            else -> Result.Err(io.constError(io.ErrorKind.InvalidInput, "internal error"))
        }
    }

    public open fun shutdown(how: Shutdown): Result<Unit> {
        return blockingScalar(netServer(), NetBlockingScalar.StdTcpStreamShutdown(this.fd, how).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ Unit })
    }

    public open fun duplicate(): Result<TcpStream> {
        this.handleCount.fetchAdd(1, Ordering.Relaxed)
        return Result.Ok(this.clone())
    }

    public open fun setLinger(arg0: Duration?): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun linger(): Result<Duration?> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setKeepalive(arg0: Boolean): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun keepalive(): Result<Boolean> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setNodelay(enabled: Boolean): Result<Unit> {
        return blockingScalar(netServer(), NetBlockingScalar.StdSetNodelay(this.fd, enabled).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ Unit })
    }

    public open fun nodelay(): Result<Boolean> {
        return Result.Ok(when (val tryResult = blockingScalar(netServer(), NetBlockingScalar.StdGetNodelay(this.fd).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ res -> res[0] != 0 })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun setTtl(ttl: UInt): Result<Unit> {
        if (ttl > 255u) {
            return Result.Err(io.constError(io.ErrorKind.InvalidInput, "TTL must be less than 256"))
        }
        return blockingScalar(netServer(), NetBlockingScalar.StdSetTtlTcp(this.fd, ttl).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ Unit })
    }

    public open fun ttl(): Result<UInt> {
        return Result.Ok(when (val tryResult = blockingScalar(netServer(), NetBlockingScalar.StdGetTtlTcp(this.fd).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ res -> ((res[0] as Any?) as Any?) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun hopLimitV6(): Result<UByte> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun takeError(): Result<Error?> {
        // this call doesn't have a meaning on our platform, but we can at least not panic if it's used.
        return Result.Ok(null)
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> {
        this.nonblocking.store(nonblocking, Ordering.Relaxed)
        return Result.Ok(Unit)
    }

    public open fun fmt(f: StringBuilder) {
        f.append("TCP connection to ${this.peerAddr} port ${this.remotePort} to local port ${this.localPort}")
    }

    public open fun drop() {
        if (this.handleCount.fetchSub(1, Ordering.Relaxed) == 1) {
            // only drop if we're the last clone
            blockingScalar(netServer(), NetBlockingScalar.StdTcpClose(this.fd).into()).unwrap()
        }
    }

    public companion object {
        public operator fun invoke(
            fd: UShort,
            localPort: UShort,
            remotePort: UShort,
            peerAddr: SocketAddr,
            readTimeout: Atomic<UInt>,
            writeTimeout: Atomic<UInt>,
            handleCount: Atomic<Int>,
            nonblocking: Atomic<Boolean>,
        ): TcpStream = TcpStream(
            fd = fd,
            localPort = localPort,
            remotePort = remotePort,
            peerAddr = peerAddr,
            readTimeoutCtorArg = readTimeout,
            writeTimeoutCtorArg = writeTimeout,
            handleCountCtorArg = handleCount,
            nonblockingCtorArg = nonblocking,
        )

        internal fun fromListener(fd: UShort, localPort: UShort, remotePort: UShort, peerAddr: SocketAddr): TcpStream = TcpStream(fd = fd, localPort = localPort, remotePort = remotePort, peerAddr = peerAddr, readTimeout = AtomicU32.new(0), writeTimeout = AtomicU32.new(0), handleCount = AtomicUsize.new(1), nonblocking = AtomicBool.new(false))

        public fun connectInner(addr: SocketAddr, timeout: Duration?): Result<TcpStream> {
            val connectRequest = ConnectRequest(raw = UByteArray(4096) { 0u.toUByte() })
            // Construct the request.
            sockaddrToBuf(timeout, addr, connectRequest.raw)
            val (_, valid) = lendMut(netServer(), NetLendMut.StdTcpConnect.into(), connectRequest.raw, 0, 4096) ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "invalid response"))
            // The first four bytes should be zero upon success, and will be nonzero
            // for an error.
            val response = connectRequest.raw
            if (response[0] != 0 || valid == 0) {
                // errcode is a u8 but stuck in a u16 where the upper byte is invalid. Mask & decode accordingly.
                val errcode = response[0]
                if (errcode == NetError.SocketInUse.value.toUByte()) {
                    return Result.Err(io.constError(io.ErrorKind.ResourceBusy, "socket in use"))
                } else if (errcode == NetError.Unaddressable.value.toUByte()) {
                    return Result.Err(io.constError(io.ErrorKind.AddrNotAvailable, "invalid address"))
                } else {
                    return Result.Err(io.constError(io.ErrorKind.InvalidInput, "unable to connect or internal error"))
                }
            }
            val fd = run { val bytesTmp0 = ubyteArrayOf(response[2], response[3]); ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() }
            val localPort = run { val bytesTmp1 = ubyteArrayOf(response[4], response[5]); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() }
            val remotePort = run { val bytesTmp2 = ubyteArrayOf(response[6], response[7]); ((bytesTmp2[1].toInt() shl 8) or bytesTmp2[0].toInt()).toUShort() }
            // println(
            // "Connected with local port of {}, remote port of {}, file handle of {}",
            // localPort, remotePort, fd
            // );
            return Result.Ok(TcpStream(fd = fd, localPort = localPort, remotePort = remotePort, peerAddr = addr, readTimeout = AtomicU32.new(0), writeTimeout = AtomicU32.new(0), handleCount = AtomicUsize.new(1), nonblocking = AtomicBool.new(false)))
        }

        public fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<TcpStream> = eachAddr(addr, { addr1 -> TcpStream.connectInner(addr1, null) })

        public fun connectTimeout(addr: SocketAddr, duration: Duration): Result<TcpStream> {
            if (duration.isZero()) {
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
            return TcpStream.connectInner(addr, duration)
        }
    }
}

public fun sockaddrToBuf(duration: Duration?, addr: SocketAddr, buf: MutableList<UByte>) {
    // Construct the request.
    val portBytes = addr.port().toLeBytes()
    buf[0] = portBytes[0]
    buf[1] = portBytes[1]
    val millis = (duration?.let({ d -> ULong.tryFrom(d.asMillis()).unwrapOr(ULong.MAX_VALUE).max(1) }) ?: 0)
    for ((dest, src) in (((buf).slice(2 until buf.size)).zip(millis.toLeBytes()))) {
        dest = src
    }
    when (val matchVal = addr.ip()) {
        is IpAddr.V4 -> {
            val (addr1) = matchVal
            buf[10] = 4u
            for ((dest1, src1) in (((buf).slice(11 until buf.size)).zip(addr1.octets()))) {
                dest1 = src1
            }
        }
        is IpAddr.V6 -> {
            val (addr2) = matchVal
            buf[10] = 6u
            for ((dest2, src2) in (((buf).slice(11 until buf.size)).zip(addr2.octets()))) {
                dest2 = src2
            }
        }
    }
}
