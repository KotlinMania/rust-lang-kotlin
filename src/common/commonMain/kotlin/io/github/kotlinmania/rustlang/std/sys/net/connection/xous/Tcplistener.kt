// port-lint: source sys/net/connection/xous/tcplistener.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.xous

import io.github.kotlinmania.rustlang.core.net.ipaddr.IpAddr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.or
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU16
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.blockingScalar
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.os.xous.ffi.lendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.net.NetBlockingScalar
import io.github.kotlinmania.rustlang.std.os.xous.services.net.NetLendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.net.netServer
import io.github.kotlinmania.rustlang.std.sys.net.connection.eachAddr

public open class TcpListener internal constructor(
    fdCtorArg: Atomic<UShort>,
    public var local: SocketAddr,
    handleCountCtorArg: Atomic<Int>,
    nonblockingCtorArg: Atomic<Boolean>,
) {
    public var fd: Atomic<UShort> = fdCtorArg
        internal set

    public var handleCount: Atomic<Int> = handleCountCtorArg
        internal set

    public var nonblocking: Atomic<Boolean> = nonblockingCtorArg
        internal set

    public open fun socketAddr(): Result<SocketAddr> = Result.Ok(this.local)

    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> {
        val receiveRequest = ReceiveData(raw = UByteArray(4096) { 0u.toUByte() })
        if (this.nonblocking.load(Ordering.Relaxed)) {
            // nonblocking
            receiveRequest.raw[0] = 0u
        } else {
            // blocking
            receiveRequest.raw[0] = 1u
        }
        return when (val matchVal = lendMut(netServer(), NetLendMut.StdTcpAccept(this.fd.load(Ordering.Relaxed)).into(), receiveRequest.raw, 0, 0)) {
            is Ok -> {
                if (receiveRequest.raw[0] != 0u.toUByte()) {
                    // Error case — code lives at byte 4 (where the send path
                    // also reads it). Byte 1 is part of the marker header.
                    if (receiveRequest.raw[4] == NetError.TimedOut.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.TimedOut, "accept timed out"))
                    } else if (receiveRequest.raw[4] == NetError.WouldBlock.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.WouldBlock, "accept would block"))
                    } else if (receiveRequest.raw[4] == NetError.LibraryError.value.toUByte()) {
                        return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                    } else {
                        return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                    }
                } else {
                    // accept successful
                    val rr = receiveRequest.raw
                    val streamFd = run { val bytesTmp1 = ((rr).sliceArray(1 until 3)); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() }
                    val port = run { val bytesTmp2 = ((rr).sliceArray(20 until 22)); ((bytesTmp2[1].toInt() shl 8) or bytesTmp2[0].toInt()).toUShort() }
                    val addr =
                        if (rr[3] == 4u.toUByte()) {
                            SocketAddr.new(IpAddr.v4(Ipv4Addr.new(rr[4], rr[5], rr[6], rr[7])), port)
                        } else if (rr[3] == 6u.toUByte()) {
                            SocketAddr.new(IpAddr.v6(Ipv6Addr.new(run { val bytesTmp3 = ((rr).sliceArray(4 until 6)); ((bytesTmp3[0].toInt() shl 8) or bytesTmp3[1].toInt()).toUShort() }, run { val bytesTmp4 = ((rr).sliceArray(6 until 8)); ((bytesTmp4[0].toInt() shl 8) or bytesTmp4[1].toInt()).toUShort() }, run { val bytesTmp5 = ((rr).sliceArray(8 until 10)); ((bytesTmp5[0].toInt() shl 8) or bytesTmp5[1].toInt()).toUShort() }, run { val bytesTmp6 = ((rr).sliceArray(10 until 12)); ((bytesTmp6[0].toInt() shl 8) or bytesTmp6[1].toInt()).toUShort() }, run { val bytesTmp7 = ((rr).sliceArray(12 until 14)); ((bytesTmp7[0].toInt() shl 8) or bytesTmp7[1].toInt()).toUShort() }, run { val bytesTmp8 = ((rr).sliceArray(14 until 16)); ((bytesTmp8[0].toInt() shl 8) or bytesTmp8[1].toInt()).toUShort() }, run { val bytesTmp9 = ((rr).sliceArray(16 until 18)); ((bytesTmp9[0].toInt() shl 8) or bytesTmp9[1].toInt()).toUShort() }, run { val bytesTmp10 = ((rr).sliceArray(18 until 20)); ((bytesTmp10[0].toInt() shl 8) or bytesTmp10[1].toInt()).toUShort() })), port)
                        } else {
                            return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                        }
                    // replenish the listener
                    val localCopy = this.local.clone()
                    // port is non-0 by this time, but the method signature needs a mut
                    val newFd = when (val tryResult = TcpListener.bindInner(localCopy)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    this.fd.store(newFd, Ordering.Relaxed)
                    // now return a stream converted from the old stream's fd
                    Result.Ok(Pair(TcpStream.fromListener(streamFd, this.local.port(), port, addr), addr))
                }
            }
            else -> {
                Result.Err(io.constError(io.ErrorKind.InvalidInput, "unable to accept"))
            }
        }
    }

    public open fun duplicate(): Result<TcpListener> {
        this.handleCount.fetchAdd(1, Ordering.Relaxed)
        return Result.Ok(this.clone())
    }

    public open fun setTtl(ttl: UInt): Result<Unit> {
        if (ttl > 255u) {
            return Result.Err(io.constError(io.ErrorKind.InvalidInput, "TTL must be less than 256"))
        }
        return blockingScalar(netServer(), NetBlockingScalar.StdSetTtlTcp(this.fd.load(Ordering.Relaxed), ttl).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ Unit })
    }

    public open fun ttl(): Result<UInt> {
        return Result.Ok(when (val tryResult = blockingScalar(netServer(), NetBlockingScalar.StdGetTtlTcp(this.fd.load(Ordering.Relaxed)).into()).or(Result.Err(io.constError(io.ErrorKind.InvalidInput, "unexpected return value"))).map({ res -> ((res[0] as Any?) as Any?) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun setHopLimitV6(hlim: UByte): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun hopLimitV6(): Result<UByte> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun setOnlyV6(arg0: Boolean): Result<Unit> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun onlyV6(): Result<Boolean> = Result.Err(io.constError(io.ErrorKind.Unsupported, "this function is not yet implemented"))

    public open fun takeError(): Result<Error?> {
        // this call doesn't have a meaning on our platform, but we can at least not panic if it's used.
        return Result.Ok(null)
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> {
        this.nonblocking.store(nonblocking, Ordering.Relaxed)
        return Result.Ok(Unit)
    }

    public open fun fmt(f: StringBuilder) {
        f.append("TCP listening on ${this.local}")
    }

    public open fun drop() {
        if (this.handleCount.fetchSub(1, Ordering.Relaxed) == 1) {
            // only drop if we're the last clone
            blockingScalar(netServer(), NetBlockingScalar.StdTcpClose(this.fd.load(Ordering.Relaxed)).into()).unwrap()
        }
    }

    public companion object {
        public operator fun invoke(
            fd: Atomic<UShort>,
            local: SocketAddr,
            handleCount: Atomic<Int>,
            nonblocking: Atomic<Boolean>,
        ): TcpListener = TcpListener(
            fdCtorArg = fd,
            local = local,
            handleCountCtorArg = handleCount,
            nonblockingCtorArg = nonblocking,
        )

        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<TcpListener> {
            fun inner(addr1: SocketAddr): Result<TcpListener> {
                val addr2 = addr1
                val fd = when (val tryResult = TcpListener.bindInner(addr2)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                return Result.Ok(TcpListener(fd = AtomicU16.new(fd), local = addr2, handleCount = AtomicUsize.new(1), nonblocking = AtomicBool.new(false)))
            }
            return eachAddr(addr, inner)
        }

        /**
         * This returns the raw fd of a Listener, so that it can also be used by the
         * accept routine to replenish the Listener object after its handle has been converted into
         * a TcpStream object.
         */
        public fun bindInner(addr: SocketAddr): Result<UShort> {
            // Construct the request
            val connectRequest = ConnectRequest(raw = UByteArray(4096) { 0u.toUByte() })
            // Serialize the StdUdpBind structure. This is done "manually" because we don't want to
            // make an auto-serdes (like bincode or rkyv) crate a dependency of Xous.
            val portBytes = addr.port().toLeBytes()
            connectRequest.raw[0] = portBytes[0]
            connectRequest.raw[1] = portBytes[1]
            when (val matchVal = addr.ip()) {
                is IpAddr.V4 -> {
                    val (addr1) = matchVal
                    connectRequest.raw[2] = 4
                    for ((dest, src) in (connectRequest.raw[3 until connectRequest.raw.size].zip(addr1.octets()))) {
                        dest = src
                    }
                }
                is IpAddr.V6 -> {
                    val (addr2) = matchVal
                    connectRequest.raw[2] = 6
                    for ((dest1, src1) in (connectRequest.raw[3 until connectRequest.raw.size].zip(addr2.octets()))) {
                        dest1 = src1
                    }
                }
            }
            val (_, valid) = lendMut(netServer(), NetLendMut.StdTcpListen.into(), connectRequest.raw, 0, 4096) ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "invalid response"))
            // The first four bytes should be zero upon success, and will be nonzero
            // for an error.
            val response = connectRequest.raw
            if (response[0] != 0 || valid == 0) {
                val errcode = response[1]
                if (errcode == NetError.SocketInUse.value.toUByte()) {
                    return Result.Err(io.constError(io.ErrorKind.ResourceBusy, "socket in use"))
                } else if (errcode == NetError.Invalid.value.toUByte()) {
                    return Result.Err(io.constError(io.ErrorKind.AddrNotAvailable, "invalid address"))
                } else if (errcode == NetError.LibraryError.value.toUByte()) {
                    return Result.Err(io.constError(io.ErrorKind.Other, "library error"))
                } else {
                    return Result.Err(io.constError(io.ErrorKind.Other, "unable to connect or internal error"))
                }
            }
            val fd = response[1].toInt()
            if (addr.port() == 0) {
                // oddly enough, this is a valid port and it means "give me something valid, up to you what that is"
                val assignedPort = run { val bytesTmp0 = response[2 until 4]; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() }
                addr.setPort(assignedPort)
            }
            // println("TcpListening with file handle of {}\r\n", fd);
            return Result.Ok(fd.tryInto().unwrap())
        }
    }
}
