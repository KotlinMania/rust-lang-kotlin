// port-lint: source sys/net/connection/socket/solid.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.socket.solid

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.fuchsia.EACCES
import io.github.kotlinmania.libc.fuchsia.EADDRINUSE
import io.github.kotlinmania.libc.fuchsia.EADDRNOTAVAIL
import io.github.kotlinmania.libc.fuchsia.EAGAIN
import io.github.kotlinmania.libc.fuchsia.ECONNABORTED
import io.github.kotlinmania.libc.fuchsia.ECONNREFUSED
import io.github.kotlinmania.libc.fuchsia.ECONNRESET
import io.github.kotlinmania.libc.fuchsia.EEXIST
import io.github.kotlinmania.libc.fuchsia.EINTR
import io.github.kotlinmania.libc.fuchsia.EINVAL
import io.github.kotlinmania.libc.fuchsia.ENOENT
import io.github.kotlinmania.libc.fuchsia.ENOMEM
import io.github.kotlinmania.libc.fuchsia.ENOSYS
import io.github.kotlinmania.libc.fuchsia.ENOTCONN
import io.github.kotlinmania.libc.fuchsia.EPERM
import io.github.kotlinmania.libc.fuchsia.EPIPE
import io.github.kotlinmania.libc.fuchsia.ETIMEDOUT
import io.github.kotlinmania.libc.fuchsia.SizeT
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.String
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.solid.io.AsFd
import io.github.kotlinmania.rustlang.std.os.solid.io.AsRawFd
import io.github.kotlinmania.rustlang.std.os.solid.io.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.solid.io.FromRawFd
import io.github.kotlinmania.rustlang.std.os.solid.io.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.solid.io.OwnedFd
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.getsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.setsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrFromC
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrToC
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.solid.netc.MSG_PEEK
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.ER
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.FdSet
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.Linger
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Timeval

public typealias WrlenT = SizeT

public fun maxIov(): Int {
    // Judging by the source code, it's unlimited, but specify a lower
    // value just in case.
    return 1024
}

public interface IsMinusOne {
    public fun isMinusOne(): Boolean
}

public fun Byte.isMinusOne(): Boolean = (this == (-1).toByte()).toUByte()

public fun Short.isMinusOne(): Boolean = (this == (-1).toShort()).toUByte()

public fun Int.isMinusOne(): Boolean = (this == -1).toUByte()

public fun Long.isMinusOne(): Boolean = (this == -1L).toUByte()

public fun <T : IsMinusOne> cvt(t: T): Result<T> =
    if (t.isMinusOne()) {
        Result.Err(lastError())
    } else {
        Result.Ok(t)
    }

/**
 * A variant of `cvt` for `getaddrinfo` which return 0 for a success.
 */
public fun cvtGai(err: CInt): Result<Unit> =
    if (err == 0) {
        Result.Ok(Unit)
    } else {
        val msg: Any =
            when (err) {
                eaiNoname -> "name or service not known"
                eaiService -> "service not supported"
                eaiFail -> "non-recoverable failure in name resolution"
                eaiMemory -> "memory allocation failure"
                eaiFamily -> "family not supported"
                else -> err
            }
        Result.Err(io.Error.new(io.ErrorKind.Uncategorized, "failed to lookup address information: $msg"))
    }

/**
 * Just to provide the same interface as sys/pal/unix/net.rs
 */
public fun <T> cvtR(f: () -> T): Result<T> where T : IsMinusOne = cvt(f())

/**
 * Returns the last error from the network subsystem.
 */
public fun lastError(): Error = io.Error.fromRawOsError(SolidNetGetLastError())

public fun errorName(er: ER): String? = CStr.fromPtr(strerror(er)).toStr().ok()

public fun isInterrupted(er: ER): Boolean = (er == SOLID_NET_ERR_BASE - EINTR).toUByte()

public fun decodeErrorKind(er: ER): ErrorKind {
    val errno = SOLID_NET_ERR_BASE - er
    return when (errno) {
        ECONNREFUSED -> ErrorKind.ConnectionRefused
        ECONNRESET -> ErrorKind.ConnectionReset
        EPERM, EACCES -> ErrorKind.PermissionDenied
        EPIPE -> ErrorKind.BrokenPipe
        ENOTCONN -> ErrorKind.NotConnected
        ECONNABORTED -> ErrorKind.ConnectionAborted
        EADDRNOTAVAIL -> ErrorKind.AddrNotAvailable
        EADDRINUSE -> ErrorKind.AddrInUse
        ENOENT -> ErrorKind.NotFound
        EINTR -> ErrorKind.Interrupted
        EINVAL -> ErrorKind.InvalidInput
        ETIMEDOUT -> ErrorKind.TimedOut
        EEXIST -> ErrorKind.AlreadyExists
        ENOSYS -> ErrorKind.Unsupported
        ENOMEM -> ErrorKind.OutOfMemory
        EAGAIN -> ErrorKind.WouldBlock
        else -> ErrorKind.Uncategorized
    }
}

public fun init() {}

public open class Socket(
    public var value: OwnedFd,
) : FromInner<OwnedFd, Socket>, IntoInner<OwnedFd>, AsFd, AsRawFd, FromRawFd<Socket, Any?, Any>, IntoRawFd {
    public var field0: OwnedFd
        get() = value
        set(v) {
            value = v
        }

    public open fun connect(addr: SocketAddr): Result<Unit> {
        val (addr1, len) = socketAddrToC(addr)
        when (val tryResult = cvt(connect(this.asRawFd(), addr1.asPtr(), len))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun pollConnected(timeout: Duration): Result<Unit> {
        if (timeout.asSecs() == 0 && timeout.subsecNanos() == 0) {
            return Result.Err(io.Error.ZERO_TIMEOUT)
        }
        val secs =
            if (timeout.asSecs() > MAX.toULong()) {
                MAX
            } else {
                timeout.asSecs().toLong()
            }
        val timeout1 = Timeval(tvSec = secs, tvUsec = timeout.subsecMicros().toInt())
        if (timeout1.tvSec == 0 && timeout1.tvUsec == 0) {
            timeout1.tvUsec = 1
        }
        val fds = FdSet(numFds = 1, fds = intArrayOf(this.asRawFd()))
        val writefds = fds
        val errorfds = fds
        val n = when (val tryResult = cvt(select(this.asRawFd() + 1, MutPtr(0L), writefds, errorfds, timeout1))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return when (n) {
            0 -> Result.Err(io.constError(io.ErrorKind.TimedOut, "connection timed out"))
            else -> {
                val canWrite = writefds.numFds != 0
                if (!canWrite) {
                    when (val matchVal = when (val tryResult = this.takeError()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }) {
                        null -> {}
                        else -> {
                            val e = matchVal
                            return Result.Err(e)
                        }
                    }
                }
                Result.Ok(Unit)
            }
        }
    }

    public open fun accept(storage: MutPtr, len: MutPtr): Result<Socket> {
        val fd = when (val tryResult = cvtR({ accept(this.asRawFd(), storage, len) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Socket.fromRawFd(fd))
    }

    public open fun duplicate(): Result<Socket> {
        return Result.Ok(Socket(when (val tryResult = this.value.tryClone()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
    }

    public open fun recvWithFlags(buf: BorrowedCursor<UByte>, flags: CInt): Result<Unit> {
        val ret = when (val tryResult = cvt(recv(this.asRawFd(), buf.asMut().asMutPtr().cast(), buf.capacity(), flags))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
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
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), MSG_PEEK)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(buf1.len())
    }

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.recvWithFlags(buf, 0)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
        val ret = when (val tryResult = cvt(readv(this.asRawFd(), (bufs.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov()).toInt()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun isReadVectored(): Boolean = true

    public open fun recvFromWithFlags(buf: MutableList<UByte>, flags: CInt): Result<Pair<Int, SocketAddr>> {
        val localStorageL230C8 = AddressableLocal(null, ObjectSlotLayout<SockaddrStorage>())
        val storage by localStorageL230C8
        val addrlen = sizeOfVal(storage)
        val n = when (val tryResult = cvt(recvfrom(this.asRawFd(), (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, flags, MutPtr(localStorageL230C8.asMutPtr().rawAddress), addrlen))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(n.toInt(), when (val tryResult = socketAddrFromC(storage, addrlen)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
    }

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvFromWithFlags(buf, 0)

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvFromWithFlags(buf, MSG_PEEK)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        val ret = when (val tryResult = cvt(writev(this.asRawFd(), (bufs.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov()).toInt()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun setTimeout(dur: Duration?, kind: CInt): Result<Unit> {
        val timeout1 =
            when (dur) {
                null -> Timeval(tvSec = 0, tvUsec = 0)
                else -> {
                    val dur1 = dur
                    if (dur1.asSecs() == 0 && dur1.subsecNanos() == 0) {
                        return Result.Err(io.Error.ZERO_TIMEOUT)
                    }
                    val secs =
                        if (dur1.asSecs() > MAX.toULong()) {
                            MAX
                        } else {
                            dur1.asSecs().toLong()
                        }
                    var timeout = Timeval(tvSec = secs, tvUsec = dur1.subsecMicros().toInt())
                    if (timeout.tvSec == 0 && timeout.tvUsec == 0) {
                        timeout.tvUsec = 1
                    }
                    timeout
                }
            }
        return setsockopt(this, SOL_SOCKET, kind, timeout1)
    }

    public open fun timeout(kind: CInt): Result<Duration?> {
        val raw: Timeval = when (val tryResult = getsockopt(this, SOL_SOCKET, kind)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
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
                Shutdown.Write -> SHUT_WR
                Shutdown.Read -> SHUT_RD
                Shutdown.Both -> SHUT_RDWR
            }
        when (val tryResult = cvt(shutdown(this.asRawFd(), how1))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun setLinger(linger: Duration?): Result<Unit> {
        val linger1 = Linger(lOnoff = (if (linger != null) 1 else 0), lLinger = minOf(linger.unwrapOrDefault().asSecs(), CInt.MAX_VALUE.toULong()).toInt())
        return setsockopt(this, SOL_SOCKET, SO_LINGER, linger1)
    }

    public open fun linger(): Result<Duration?> {
        val value: Linger = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_LINGER)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok((value.lOnoff != 0).then({ Duration.fromSecs(value.lLinger.toULong()) }))
    }

    public open fun setKeepalive(arg0: Boolean): Result<Unit> = unsupported()

    public open fun keepalive(): Result<Boolean> = unsupported()

    public open fun setNodelay(nodelay: Boolean): Result<Unit> = setsockopt(this, IPPROTO_TCP, TCP_NODELAY, nodelay.toInt())

    public open fun nodelay(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_NODELAY)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> {
        val localStorageL348C8 = AddressableLocal(nonblocking.toInt(), ObjectSlotLayout<CInt>())
        return cvt(ioctl(this.asRawFd(), FIONBIO, (((MutPtr(localStorageL348C8.asMutPtr().rawAddress) as Any?) as Result<Unit>)).toLong())).map(drop)
    }

    public open fun takeError(): Result<Error?> {
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_ERROR)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (raw == 0) {
            Result.Ok(null)
        } else {
            Result.Ok(io.Error.fromRawOsError(raw))
        }
    }

    public open fun asRaw(): CInt = this.asRawFd()

    public override fun intoInner(): OwnedFd = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): CInt = this.value.asRawFd()

    public override fun intoRawFd(): CInt = this.value.intoRawFd()

    public companion object {
        public fun new(fam: CInt, ty: CInt): Result<Socket> {
            val fd = when (val tryResult = cvt(Socket(fam, ty, 0))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(Socket.fromRawFd(fd))
        }

        public fun fromInner(sock: OwnedFd): Socket = Socket(sock)

        public fun fromRawFd(fd: CInt): Socket = Socket(FromRawFd.fromRawFd(fd))
    }
}
