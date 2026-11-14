// port-lint: source sys/net/connection/socket/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.socket.hermit

import io.github.kotlinmania.libc.fuchsia.SOL_SOCKET
import io.github.kotlinmania.libc.fuchsia.SO_ERROR
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.getsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.setsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrFromC
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrToC
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvtR
import io.github.kotlinmania.rustlang.std.time.Instant

public typealias WrlenT = Int

public fun cvtGai(err: Int): Result<Unit> {
    if (err == 0) {
        return Result.Ok(Unit)
    }
    val detail = ""
    return Result.Err(io.Error.new(io.ErrorKind.Uncategorized, "failed to lookup address information: $detail"))
}

public fun init() {}

public open class Socket(
    public var value: FileDesc,
) : AsInner<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, Socket>, AsFd, AsRawFd {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun connect(addr: SocketAddr): Result<Unit> {
        val (addr1, len) = socketAddrToC(addr)
        when (val tryResult = cvtR({ netc.connect(this.asRawFd(), addr1.asPtr(), len) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun pollConnected(timeout: Duration): Result<Unit> {
        val timeout1 = timeout
        val pollfd = netc.Pollfd(fd = this.asRawFd(), events = netc.POLLOUT, revents = 0)
        if (timeout1.asSecs() == 0 && timeout1.subsecNanos() == 0) {
            return Result.Err(io.Error.ZERO_TIMEOUT)
        }
        val start = Instant.now()
        while (true) {
            val elapsed = start.elapsed()
            if (elapsed >= timeout1) {
                return Result.Err(io.constError(io.ErrorKind.TimedOut, "connection timed out"))
            }
            val timeout2 = timeout1 - elapsed
            var timeout3 = timeout2.asSecs().saturatingMul(1000).saturatingAdd(timeout2.subsecNanos().toULong() / 1000000uL)
            if (timeout3 == 0) {
                timeout3 = 1
            }
            val timeout4 = minOf(timeout3, CInt.MAX_VALUE.toULong()).toInt()
            when (netc.poll(pollfd, 1, timeout4)) {
                -1 -> {
                    val err = io.Error.lastOsError()
                    if (!err.isInterrupted()) {
                        return Result.Err(err)
                    }
                }
                0 -> {}
                else -> {
                    // linux returns POLLOUT|POLLERR|POLLHUP for refused connections (!), so look
                    // for POLLHUP rather than read readiness
                    if (pollfd.revents and netc.POLLHUP != 0) {
                        val e = (when (val tryResult = this.takeError()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } ?: io.constError(io.ErrorKind.Uncategorized, "no error set after POLLHUP"))
                        return Result.Err(e)
                    }
                    return Result.Ok(Unit)
                }
            }
        }
    }

    public open fun accept(storage: MutPtr, len: MutPtr): Result<Socket> {
        val fd = when (val tryResult = cvt(netc.accept(this.value.asRawFd(), storage, len))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Socket(FileDesc.fromRawFd(fd)))
    }

    public open fun duplicate(): Result<Socket> {
        val fd = when (val tryResult = cvt(netc.dup(this.value.asRawFd()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Socket(FileDesc.fromRawFd(fd)))
    }

    public open fun recvWithFlags(buf: BorrowedCursor<UByte>, flags: Int): Result<Unit> {
        val ret = when (val tryResult = cvt(netc.recv(this.value.asRawFd(), mutPtrFromRustCast(buf.asMut().asMutPtr()), buf.capacity(), flags))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        run {
            buf.advance(ret.toInt())
        }
        return Result.Ok(Unit)
    }

    public open fun read(buf: MutableList<UByte>): Result<Int> {
        val buf1 = BorrowedBuf.from(buf)
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), 0)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(buf1.len())
    }

    public open fun peek(buf: MutableList<UByte>): Result<Int> {
        val buf1 = BorrowedBuf.from(buf)
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), netc.MSG_PEEK)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(buf1.len())
    }

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.recvWithFlags(buf, 0)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun recvFromWithFlags(buf: MutableList<UByte>, flags: Int): Result<Pair<Int, SocketAddr>> {
        val localStorageL162C8 = AddressableLocal(null, ObjectSlotLayout<netc.SockaddrStorage>())
        val storage by localStorageL162C8
        val addrlen = sizeOfVal(storage)
        val n = when (val tryResult = cvt(netc.recvfrom(this.asRawFd(), buf.asMutPtr(), buf.size, flags, (localStorageL162C8.asMutPtr()), addrlen))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(n.toInt(), when (val tryResult = socketAddrFromC(storage, addrlen)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
    }

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvFromWithFlags(buf, 0)

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvFromWithFlags(buf, netc.MSG_PEEK)

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun setTimeout(dur: Duration?, kind: Int): Result<Unit> {
        val timeout1 =
            when (dur) {
                null -> netc.Timeval(tvSec = 0, tvUsec = 0)
                else -> {
                    val dur1 = dur
                    if (dur1.asSecs() == 0 && dur1.subsecNanos() == 0) {
                        return Result.Err(io.Error.ZERO_TIMEOUT)
                    }
                    val secs =
                        if (dur1.asSecs() > netc.timeT.MAX.toULong()) {
                            netc.timeT.MAX
                        } else {
                            dur1.asSecs().toLong()
                        }
                    var timeout = netc.Timeval(tvSec = secs, tvUsec = dur1.subsecMicros().toInt())
                    if (timeout.tvSec == 0 && timeout.tvUsec == 0) {
                        timeout.tvUsec = 1
                    }
                    timeout
                }
            }
        return setsockopt(this, netc.SOL_SOCKET, kind, timeout1)
    }

    public open fun timeout(kind: Int): Result<Duration?> {
        val raw: netc.Timeval = when (val tryResult = getsockopt(this, netc.SOL_SOCKET, kind)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (raw.tvSec == 0 && raw.tvUsec == 0) {
            Result.Ok(null)
        } else {
            val sec = raw.tvSec.toULong()
            val nsec = (raw.tvUsec.toUInt()) * 1000u
            Result.Ok(Duration.new(sec, nsec))
        }
    }

    public open fun shutdown(how: Shutdown): Result<Unit> {
        val how1 =
            when (how) {
                Shutdown.Write -> netc.SHUT_WR
                Shutdown.Read -> netc.SHUT_RD
                Shutdown.Both -> netc.SHUT_RDWR
            }
        when (val tryResult = cvt(netc.shutdown(this.asRawFd(), how1))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun setLinger(linger: Duration?): Result<Unit> {
        val linger1 = netc.Linger(lOnoff = (if (linger != null) 1 else 0), lLinger = minOf(linger.unwrapOrDefault().asSecs(), CInt.MAX_VALUE.toULong()).toInt())
        return setsockopt(this, netc.SOL_SOCKET, netc.SO_LINGER, linger1)
    }

    public open fun linger(): Result<Duration?> {
        val value: netc.Linger = when (val tryResult = getsockopt(this, netc.SOL_SOCKET, netc.SO_LINGER)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok((value.lOnoff != 0).then({ Duration.fromSecs(value.lLinger.toULong()) }))
    }

    public open fun setKeepalive(keepalive: Boolean): Result<Unit> = setsockopt(this, netc.SOL_SOCKET, netc.SO_KEEPALIVE, keepalive.toInt())

    public open fun keepalive(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this, netc.SOL_SOCKET, netc.SO_KEEPALIVE)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setNodelay(nodelay: Boolean): Result<Unit> {
        val value: Int =
            if (nodelay) {
                1
            } else {
                0
            }
        return setsockopt(this, netc.IPPROTO_TCP, netc.TCP_NODELAY, value)
    }

    public open fun nodelay(): Result<Boolean> {
        val raw: Int = when (val tryResult = getsockopt(this, netc.IPPROTO_TCP, netc.TCP_NODELAY)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> {
        val localStorageL281C8 = AddressableLocal(if (nonblocking) {
            1
        } else {
            0
        }, PrimitiveLayouts.I32)
        return cvt(netc.ioctl(this.asRawFd(), netc.FIONBIO, (((localStorageL281C8.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress)).map(drop)
    }

    public open fun takeError(): Result<Error?> {
        val raw = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_ERROR)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (raw == 0) {
            Result.Ok(null)
        } else {
            Result.Ok(io.Error.fromRawOsError(raw))
        }
    }

    public open fun asRaw(): RawFd = this.value.asRawFd()

    public override fun asInner(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public companion object {
        public fun new(fam: Int, ty: Int): Result<Socket> {
            val fd = when (val tryResult = cvt(netc.Socket(fam, ty, 0))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(Socket(FileDesc.fromRawFd(fd)))
        }

        public fun newPair(fam: Int, ty: Int): Result<Pair<Socket, Socket>> {
            error("unsupported")
        }

        public fun fromInner(fileDesc: FileDesc): Socket = Socket(fileDesc)
    }
}
