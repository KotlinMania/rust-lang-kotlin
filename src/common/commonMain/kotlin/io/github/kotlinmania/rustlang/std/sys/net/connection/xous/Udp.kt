// port-lint: source sys/net/connection/xous/udp.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.xous

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.net.ipaddr.IpAddr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.or
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.blockingScalar
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.os.xous.ffi.lendMut
import io.github.kotlinmania.rustlang.std.os.xous.ffi.tryLendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.net.NetBlockingScalar
import io.github.kotlinmania.rustlang.std.os.xous.services.net.NetLendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.net.netServer
import io.github.kotlinmania.rustlang.std.sys.net.connection.eachAddr
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.systemtime.now
import io.github.kotlinmania.rustlang.std.thread.functions.yieldNow

public open class UdpSocket internal constructor(
    public var fd: UShort,
    public var local: SocketAddr,
    remoteCtorArg: Cell<SocketAddr?>,
    readTimeoutCtorArg: Cell<ULong>,
    writeTimeoutCtorArg: Cell<ULong>,
    handleCountCtorArg: Atomic<Int>,
    nonblockingCtorArg: Cell<Boolean>,
) {
    public var remote: Cell<SocketAddr?> = remoteCtorArg
        internal set

    // in milliseconds. The setting applies only to `recv` calls after the timeout is set.
    public var readTimeout: Cell<ULong> = readTimeoutCtorArg
        internal set

    // in milliseconds. The setting applies only to `send` calls after the timeout is set.
    public var writeTimeout: Cell<ULong> = writeTimeoutCtorArg
        internal set

    public var handleCount: Atomic<Int> = handleCountCtorArg
        internal set

    public var nonblocking: Cell<Boolean> = nonblockingCtorArg
        internal set

    public open fun peerAddr(): Result<SocketAddr> =
        when (val matchVal = this.remote.get()) {
            null -> Result.Err(io.constError(io.ErrorKind.NotConnected, "no peer specified"))
            else -> {
                val dest = matchVal
                Result.Ok(dest)
            }
        }

    public open fun socketAddr(): Result<SocketAddr> = Result.Ok(this.local)

    public open fun recvInner(buf: MutableList<UByte>, doPeek: Boolean): Result<Pair<Int, SocketAddr>> {
        val receiveRequest = ReceiveData(raw = UByteArray(4096) { 0u.toUByte() })
        if (this.nonblocking.get()) {
            // nonblocking
            receiveRequest.raw[0] = 0u
        } else {
            // blocking
            receiveRequest.raw[0] = 1u
            for ((s, d) in (this.readTimeout.get().toLeBytes().zip(((receiveRequest.raw).sliceArray(1 until 9))))) {
                d = s
            }
        }
        return when (val matchVal = lendMut(netServer(), NetLendMut.StdUdpRx(this.fd).into(), receiveRequest.raw, if (doPeek) {
            1
        } else {
            0
        }, 0)) {
            is Ok -> {
                if (receiveRequest.raw[0] != 0u.toUByte()) {
                    // Error case — code lives at byte 4 (where `sendMessage`
                    // also reads it). Byte 1 is part of the marker header.
                    if (receiveRequest.raw[4] == NetError.TimedOut.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.TimedOut, "recv timed out"))
                    } else if (receiveRequest.raw[4] == NetError.WouldBlock.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.WouldBlock, "recv would block"))
                    } else if (receiveRequest.raw[4] == NetError.LibraryError.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                    } else {
                        return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                    }
                } else {
                    val rr = receiveRequest.raw
                    val rxlen = run { val bytesTmp0 = ((rr).sliceArray(1 until 3)); ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() }
                    val port = run { val bytesTmp1 = ((rr).sliceArray(20 until 22)); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() }
                    val addr =
                        if (rr[3] == 4u.toUByte()) {
                            SocketAddr.new(IpAddr.v4(Ipv4Addr.new(rr[4], rr[5], rr[6], rr[7])), port)
                        } else if (rr[3] == 6u.toUByte()) {
                            SocketAddr.new(IpAddr.v6(Ipv6Addr.new(run { val bytesTmp2 = ((rr).sliceArray(4 until 6)); ((bytesTmp2[0].toInt() shl 8) or bytesTmp2[1].toInt()).toUShort() }, run { val bytesTmp3 = ((rr).sliceArray(6 until 8)); ((bytesTmp3[0].toInt() shl 8) or bytesTmp3[1].toInt()).toUShort() }, run { val bytesTmp4 = ((rr).sliceArray(8 until 10)); ((bytesTmp4[0].toInt() shl 8) or bytesTmp4[1].toInt()).toUShort() }, run { val bytesTmp5 = ((rr).sliceArray(10 until 12)); ((bytesTmp5[0].toInt() shl 8) or bytesTmp5[1].toInt()).toUShort() }, run { val bytesTmp6 = ((rr).sliceArray(12 until 14)); ((bytesTmp6[0].toInt() shl 8) or bytesTmp6[1].toInt()).toUShort() }, run { val bytesTmp7 = ((rr).sliceArray(14 until 16)); ((bytesTmp7[0].toInt() shl 8) or bytesTmp7[1].toInt()).toUShort() }, run { val bytesTmp8 = ((rr).sliceArray(16 until 18)); ((bytesTmp8[0].toInt() shl 8) or bytesTmp8[1].toInt()).toUShort() }, run { val bytesTmp9 = ((rr).sliceArray(18 until 20)); ((bytesTmp9[0].toInt() shl 8) or bytesTmp9[1].toInt()).toUShort() })), port)
                        } else {
                            return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                        }
                    val max = (rxlen.toInt()).min(buf.size).min(rr.size - 22)
                    // Both sides must be sliced: `max` is shorter than `buf` whenever
                    // the datagram doesn't fill it, and `copyFromSlice` panics on a
                    // length mismatch.
                    run { val dstTmp10 = buf; val srcTmp10 = ((((rr).sliceArray(22 until rr.size))).sliceArray(0 until max)); for (idxTmp10 in srcTmp10.indices) dstTmp10[idxTmp10] = srcTmp10[idxTmp10] }
                    Result.Ok(Pair(max, addr))
                }
            }
            else -> {
                Result.Err(io.constError(io.ErrorKind.InvalidInput, "unable to recv"))
            }
        }
    }

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvInner(buf, false)

    public open fun recv(buf: MutableList<UByte>): Result<Int> = this.recvFrom(buf).map({ (len, addr) -> len })

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvInner(buf, true)

    public open fun peek(buf: MutableList<UByte>): Result<Int> = this.peekFrom(buf).map({ (len, addr) -> len })

    public open fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<Unit> =
        eachAddr(addr, { addr1 ->
            this.remote.set(addr1)
            Result.Ok(Unit)
        })

    public open fun send(buf: List<UByte>): Result<Int> =
        when (val matchVal = this.remote.get()) {
            null -> {
                Result.Err(io.constError(io.ErrorKind.NotConnected, "No remote specified"))
            }
            else -> {
                val addr = matchVal
                this.sendTo(buf, addr)
            }
        }

    public open fun sendTo(buf: List<UByte>, addr: SocketAddr): Result<Int> {
        val txReq = SendData(raw = UByteArray(4096) { 0u.toUByte() })
        // Construct the request.
        val portBytes = addr.port().toLeBytes()
        txReq.raw[0] = portBytes[0]
        txReq.raw[1] = portBytes[1]
        when (val matchVal = addr.ip()) {
            is IpAddr.V4 -> {
                val (addr1) = matchVal
                txReq.raw[2] = 4
                for ((dest, src) in (txReq.raw[3 until txReq.raw.size].zip(addr1.octets()))) {
                    dest = src
                }
            }
            is IpAddr.V6 -> {
                val (addr2) = matchVal
                txReq.raw[2] = 6
                for ((dest1, src1) in (txReq.raw[3 until txReq.raw.size].zip(addr2.octets()))) {
                    dest1 = src1
                }
            }
        }
        val headerLen = 21
        val len = buf.size.min(txReq.raw.len() - headerLen)
        val lenBytes = run { val rawBits = (len.toUShort()).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }
        txReq.raw[19] = lenBytes[0]
        txReq.raw[20] = lenBytes[1]
        run { val dstTmp11 = txReq.raw; val srcTmp11 = ((buf).slice(0 until len)); for (idxTmp11 in srcTmp11.indices) dstTmp11[(headerLen) + idxTmp11] = srcTmp11[idxTmp11] }
        // let buf = unchecked {
        // xous.MemoryRange.new(
        // txReq as *mut SendData as Int,
        // sizeOf::<SendData>(),
        // )
        // .unwrap()
        // };
        // write time-outs are implemented on the caller side. Basically, if the Net crate server
        // is too busy to take the call immediately: retry, until the timeout is reached.
        val now = now()
        val writeTimeout =
            if (this.nonblocking.get()) {
                // nonblocking
                Duration.ZERO
            } else {
                // blocking
                if (this.writeTimeout.get() == 0) {
                    // forever
                    Duration.fromMillis(ULong.MAX_VALUE)
                } else {
                    // or this amount of time
                    Duration.fromMillis(this.writeTimeout.get())
                }
            }
        while (true) {
            val response = tryLendMut(netServer(), NetLendMut.StdUdpTx(this.fd).into(), txReq.raw, 0, 4096)
            when {
                response is Result.Ok<*> -> {
                    val response1 = txReq.raw
                    if (response1[0] != 0 || valid == 0) {
                        val errcode = response1[1]
                        if (errcode == NetError.SocketInUse.value.toUByte()) {
                            return Result.Err(io.constError(io.ErrorKind.ResourceBusy, "socket in use"))
                        } else if (errcode == NetError.Invalid.value.toUByte()) {
                            return Result.Err(io.constError(io.ErrorKind.InvalidInput, "socket not valid"))
                        } else if (errcode == NetError.LibraryError.value.toUByte()) {
                            return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                        } else {
                            return Result.Err(io.constError(io.ErrorKind.Other, "unable to connect"))
                        }
                    } else {
                        // no error
                        return Result.Ok(len)
                    }
                }
                response is Result.Err<*> && response.error == Error.ServerQueueFull -> {
                    if (now.elapsed() >= writeTimeout) {
                        return Result.Err(io.constError(io.ErrorKind.WouldBlock, "write timed out"))
                    } else {
                        // question: do we want to do something a bit more gentle than immediately retrying?
                        yieldNow()
                    }
                }
                else -> { return Result.Err(io.constError(io.ErrorKind.Other, "library error")) }
            }
        }
    }

    public open fun duplicate(): Result<UdpSocket> {
        this.handleCount.fetchAdd(1, Ordering.Relaxed)
        return Result.Ok(this.clone())
    }

    public open fun setReadTimeout(timeout: Duration?): Result<Unit> {
        if (timeout != null) {
            val d = timeout
            if (d.isZero()) {
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
        }
        this.readTimeout.set(timeout?.let({ t -> t.asMillis().min(U128.fromULong(ULong.MAX_VALUE)).toULong() }).unwrapOrDefault())
        return Result.Ok(Unit)
    }

    public open fun setWriteTimeout(timeout: Duration?): Result<Unit> {
        if (timeout != null) {
            val d = timeout
            if (d.isZero()) {
                return Result.Err(io.Error.ZERO_TIMEOUT)
            }
        }
        this.writeTimeout.set(timeout?.let({ t -> t.asMillis().min(U128.fromULong(ULong.MAX_VALUE)).toULong() }).unwrapOrDefault())
        return Result.Ok(Unit)
    }

    public open fun readTimeout(): Result<Duration?> =
        when (val matchVal = this.readTimeout.get()) {
            0 -> Result.Ok(null)
            else -> {
                val t = matchVal
                Result.Ok(Duration.fromMillis(t.toULong()))
            }
        }

    public open fun writeTimeout(): Result<Duration?> =
        when (val matchVal = this.writeTimeout.get()) {
            0 -> Result.Ok(null)
            else -> {
                val t = matchVal
                Result.Ok(Duration.fromMillis(t.toULong()))
            }
        }

    public open fun setTtl(ttl: UInt): Result<Unit> {
        if (ttl > 255u) {
            return Result.Err(io.constError(io.ErrorKind.InvalidInput, "TTL must be less than 256"))
        }
        return blockingScalar(netServer(), NetBlockingScalar.StdSetTtlUdp(this.fd, ttl).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ Unit })
    }

    public open fun ttl(): Result<UInt> {
        return Result.Ok(when (val tryResult = blockingScalar(netServer(), NetBlockingScalar.StdGetTtlUdp(this.fd).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ res -> ((res[0] as Any?) as Any?) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun setHopLimitV6(arg0: UByte): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun hopLimitV6(): Result<UByte> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun takeError(): Result<Error?> {
        // this call doesn't have a meaning on our platform, but we can at least not panic if it's used.
        return Result.Ok(null)
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> {
        this.nonblocking.set(nonblocking)
        return Result.Ok(Unit)
    }

    // ------------- smoltcp base stack does not have multicast or broadcast support ---------------
    public open fun setBroadcast(arg0: Boolean): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun broadcast(): Result<Boolean> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setMulticastLoopV4(arg0: Boolean): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun multicastLoopV4(): Result<Boolean> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setMulticastTtlV4(arg0: UInt): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun multicastTtlV4(): Result<UInt> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setMulticastHopLimitV6(arg0: UByte): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun multicastHopLimitV6(): Result<UByte> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setMulticastLoopV6(arg0: Boolean): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun multicastLoopV6(): Result<Boolean> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun joinMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun joinMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun leaveMulticastV4(arg0: Ipv4Addr, arg1: Ipv4Addr): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun leaveMulticastV6(arg0: Ipv6Addr, arg1: UInt): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun fmt(f: StringBuilder) {
        f.append("UDP listening on ${this.local} to ${this.remote.get()}")
    }

    public open fun drop() {
        if (this.handleCount.fetchSub(1, Ordering.Relaxed) == 1) {
            // only drop if we're the last clone
            blockingScalar(netServer(), NetBlockingScalar.StdUdpClose(this.fd).into()).unwrap()
        }
    }

    public companion object {
        public operator fun invoke(
            fd: UShort,
            local: SocketAddr,
            remote: Cell<SocketAddr?>,
            readTimeout: Cell<ULong>,
            writeTimeout: Cell<ULong>,
            handleCount: Atomic<Int>,
            nonblocking: Cell<Boolean>,
        ): UdpSocket = UdpSocket(
            fd = fd,
            local = local,
            remoteCtorArg = remote,
            readTimeoutCtorArg = readTimeout,
            writeTimeoutCtorArg = writeTimeout,
            handleCountCtorArg = handleCount,
            nonblockingCtorArg = nonblocking,
        )

        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<UdpSocket> {
            fun inner(addr1: SocketAddr): Result<UdpSocket> {
                // Construct the request
                val connectRequest = ConnectRequest(raw = UByteArray(4096) { 0u.toUByte() })
                // Serialize the StdUdpBind structure. This is done "manually" because we don't want to
                // make an auto-serdes (like bincode or rkyv) crate a dependency of Xous.
                val portBytes = addr1.port().toLeBytes()
                connectRequest.raw[0] = portBytes[0]
                connectRequest.raw[1] = portBytes[1]
                when (val matchVal = addr1.ip()) {
                    is IpAddr.V4 -> {
                        val (addr2) = matchVal
                        connectRequest.raw[2] = 4
                        for ((dest, src) in (connectRequest.raw[3 until connectRequest.raw.size].zip(addr2.octets()))) {
                            dest = src
                        }
                    }
                    is IpAddr.V6 -> {
                        val (addr3) = matchVal
                        connectRequest.raw[2] = 6
                        for ((dest1, src1) in (connectRequest.raw[3 until connectRequest.raw.size].zip(addr3.octets()))) {
                            dest1 = src1
                        }
                    }
                }
                val response = lendMut(netServer(), NetLendMut.StdUdpBind.into(), connectRequest.raw, 0, 4096)
                val (_, valid) = response ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "invalid response"))
                // The first four bytes should be zero upon success, and will be nonzero
                // for an error.
                val response1 = connectRequest.raw
                if (response1[0] != 0 || valid == 0) {
                    val errcode = response1[1]
                    if (errcode == NetError.SocketInUse.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.ResourceBusy, "socket in use"))
                    } else if (errcode == NetError.Invalid.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.InvalidInput, "port can't be 0 or invalid address"))
                    } else if (errcode == NetError.LibraryError.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                    } else {
                        return Result.Err(io.constError(io.ErrorKind.Other, "unable to connect or internal error"))
                    }
                }
                val fd = response1[1].toUShort()
                return Result.Ok(UdpSocket(fd = fd, local = addr1, remote = Cell.new(null), readTimeout = Cell.new(0), writeTimeout = Cell.new(0), handleCount = AtomicUsize.new(1), nonblocking = Cell.new(false)))
            }
            return eachAddr(addr, inner)
        }
    }
}
