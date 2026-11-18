// port-lint: source sys/net/connection/socket/unix.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.num.saturatingMul
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.Poll
import io.github.kotlinmania.rustlang.libc.Send
import io.github.kotlinmania.rustlang.libc.gaiStrerror
import io.github.kotlinmania.rustlang.libc.recv
import io.github.kotlinmania.rustlang.libc.recvfrom
import io.github.kotlinmania.rustlang.libc.recvmsg
import io.github.kotlinmania.rustlang.libc.resInit
import io.github.kotlinmania.rustlang.libc.sendmsg
import io.github.kotlinmania.rustlang.libc.socket
import io.github.kotlinmania.rustlang.libc.socketpair
import io.github.kotlinmania.rustlang.libc.unix.IPPROTO_TCP
import io.github.kotlinmania.rustlang.libc.unix.Linger
import io.github.kotlinmania.rustlang.libc.unix.Pollfd
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.Timeval
import io.github.kotlinmania.rustlang.libc.unix.bsd.Msghdr
import io.github.kotlinmania.rustlang.libc.unix.bsd.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.bsd.TCP_NODELAY
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SHUT_RD
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SHUT_RDWR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SHUT_WR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_ERROR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_KEEPALIVE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.EAI_SYSTEM
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MSG_CMSG_CLOEXEC
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MSG_PEEK
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SockaddrStorage
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.TCP_DEFER_ACCEPT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.TCP_QUICKACK
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.Ioctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.SO_MARK
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.SO_PASSCRED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.accept4
import io.github.kotlinmania.rustlang.libc.wasi.EINTR
import io.github.kotlinmania.rustlang.libc.wasi.EISCONN
import io.github.kotlinmania.rustlang.libc.wasi.FIONBIO
import io.github.kotlinmania.rustlang.libc.wasi.POLLERR
import io.github.kotlinmania.rustlang.libc.wasi.POLLHUP
import io.github.kotlinmania.rustlang.libc.wasi.POLLOUT
import io.github.kotlinmania.rustlang.libc.wasi.SOCK_CLOEXEC
import io.github.kotlinmania.rustlang.libc.wasi.SOL_SOCKET
import io.github.kotlinmania.rustlang.libc.wasi.TimeT
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.fd.asFd
import io.github.kotlinmania.rustlang.std.os.fd.asRawFd
import io.github.kotlinmania.rustlang.std.os.fd.fromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.intoRawFd
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.io.Errno
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.MAX_SEND_LEN
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.getsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.setsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrFromC
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrToC
import io.github.kotlinmania.rustlang.std.sys.pal.Conf
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtR
import io.github.kotlinmania.rustlang.std.time.Instant
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_LINGER_SEC as SO_LINGER

public typealias WrlenT = SizeT

public open class Socket(
    public var value: FileDesc,
) : AsInner<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, Socket>, AsFd, AsRawFd, IntoRawFd, FromRawFd<Socket> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun connect(addr: SocketAddr): Result<Unit, Error> {
        val (addr1, len) = socketAddrToC(addr)
        while (true) {
            val result = io.github.kotlinmania.rustlang.libc.connect(this.asRawFd(), addr1.asPtr(), len)
            if (result.isMinusOne()) {
                val err = Errno()
                when (err) {
                    EINTR -> { continue }
                    EISCONN -> { return Result.Ok<Unit, Error>(Unit) }
                    else -> { return Result.Err<Unit, Error>(Error.fromRawOsError(err)) }
                }
            }
            return Result.Ok<Unit, Error>(Unit)
        }
    }

    public open fun pollConnected(timeout: Duration): Result<Unit, Error> {
        val timeout1 = timeout
        val localStorageL175C8 = AddressableLocal(Pollfd(), UnresolvedTypeLayout<Pollfd>())
        val pollfd by localStorageL175C8
        pollfd.fd = this.asRawFd()
        pollfd.events = POLLOUT
        if (timeout1.asSecs() == 0 && timeout1.subsecNanos() == 0) {
            return Result.Err<Unit, Error>(Error.ZERO_TIMEOUT)
        }
        val start = Instant.now()
        while (true) {
            val elapsed = start.elapsed()
            if (elapsed >= timeout1) {
                return Result.Err<Unit, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.TimedOut, message = "connection timed out")))
            }
            val timeout2 = timeout1 - elapsed
            var timeout3 = timeout2.asSecs().saturatingMul(1000).saturatingAdd(timeout2.subsecNanos().toULong() / 1000000uL)
            if (timeout3 == 0) {
                timeout3 = 1
            }
            val timeout4 = minOf(timeout3, Int.MAX_VALUE.toULong()).toInt()
            when (Poll(RustMutableReference(localStorageL175C8.asMutPtr(), localStorageL175C8.layout), 1, timeout4)) {
                -1 -> {
                    val err = Error.lastOsError()
                    if (!err.isInterrupted()) {
                        return Result.Err<Unit, Error>(err)
                    }
                }
                0 -> {}
                else -> {
                    // A completed connect makes the socket writable, but that doesn't guarantee it
                    // succeeded: whether a failed connect also sets `POLLERR`/`POLLHUP` is
                    // platform-specific. On Linux a clean `revents` means success. Elsewhere
                    // (`illumos`, `VxWorks`) only `SO_ERROR` holds the actual status.
                    val failureShowsInRevents = false
                    val hupOrErr = pollfd.revents and (POLLHUP or POLLERR) != 0
                    if (failureShowsInRevents && !hupOrErr) {
                        return Result.Ok<Unit, Error>(Unit)
                    }
                    when (val matchVal = when (val tryResult = this.takeError()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }) {
                        null -> {}
                        else -> {
                            val e = matchVal
                            return Result.Err<Unit, Error>(e)
                        }
                    }
                    if (hupOrErr) {
                        return Result.Err<Unit, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.Uncategorized, message = "no error set after POLLHUP")))
                    }
                    return Result.Ok<Unit, Error>(Unit)
                }
            }
        }
    }

    public open fun accept(storage: MutPtr, len: MutPtr): Result<Socket, Error> {
        return run {
            val fd = when (val tryResult = cvtR({ accept4(this.asRawFd(), storage, len, SOCK_CLOEXEC) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Socket, Error>(tryResult.errValue()) }
            Result.Ok<Socket, Error>(Socket(FileDesc.fromRawFd(fd)))
        }
    }

    public open fun duplicate(): Result<Socket, Error> = this.value.duplicate().map({ Socket(it) })

    public open fun sendWithFlags(buf: List<UByte>, flags: CInt): Result<Int, Error> {
        val len = minOf(buf.size, MAX_SEND_LEN).toULong()
        val ret = when (val tryResult = cvt(Send(this.asRawFd(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, len, flags))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun recvWithFlags(buf: BorrowedCursor<UByte>, flags: CInt): Result<Unit, Error> {
        val ret = when (val tryResult = cvt(recv(this.asRawFd(), mutPtrFromRustCast(buf.asMut().asMutPtr()), buf.capacity(), flags))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        run {
            buf.advance(ret.toInt())
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> {
        val buf1 = BorrowedBuf.from(buf)
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), 0)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(buf1.len())
    }

    public open fun peek(buf: MutableList<UByte>): Result<Int, Error> {
        val buf1 = BorrowedBuf.from(buf)
        when (val tryResult = this.recvWithFlags(buf1.unfilled(), MSG_PEEK)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(buf1.len())
    }

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.recvWithFlags(buf, 0)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun recvFromWithFlags(buf: MutableList<UByte>, flags: CInt): Result<Pair<Int, SocketAddr>, Error> {
        // The `recvfrom` function will fill in the storage with the address,
        // so we don't need to zero it here.
        // reference: https://linux.die.net/man/2/recvfrom
        val localStorageL330C8 = AddressableLocal(MaybeUninit.uninit(UnresolvedTypeLayout<SockaddrStorage>()), UnresolvedTypeLayout<MaybeUninit<SockaddrStorage>>())
        val storage by localStorageL330C8
        val localStorageL331C8 = AddressableLocal(sizeOfVal(storage, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<SockaddrStorage>>()).toInt(), UnresolvedTypeLayout<io.github.kotlinmania.rustlang.libc.unix.bsd.SocklenT>())
        val addrlen by localStorageL331C8
        val n = when (val tryResult = cvt(recvfrom(this.asRawFd(), (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, flags, (localStorageL330C8.asMutPtr()), RustMutableReference(localStorageL331C8.asMutPtr(), localStorageL331C8.layout)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Int, SocketAddr>, Error>(tryResult.errValue()) }
        return Result.Ok<Pair<Int, SocketAddr>, Error>(Pair(n.toInt(), when (val tryResult = socketAddrFromC(storage.asPtr(), addrlen)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()) }))
    }

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.recvFromWithFlags(buf, 0)

    public open fun recvMsg(msg: RustMutableReference<Msghdr>): Result<Int, Error> {
        val n = when (val tryResult = cvt(recvmsg(this.asRawFd(), msg, MSG_CMSG_CLOEXEC))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(n.toInt())
    }

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.recvFromWithFlags(buf, MSG_PEEK)

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun sendMsg(msg: RustMutableReference<Msghdr>): Result<Int, Error> {
        val n = when (val tryResult = cvt(sendmsg(this.asRawFd(), msg, 0))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
        return Result.Ok<Int, Error>(n.toInt())
    }

    public open fun setTimeout(dur: Duration?, kind: CInt): Result<Unit, Error> {
        val timeout1 =
            when (dur) {
                null -> Timeval(tvSec = 0, tvUsec = 0)
                else -> {
                    val dur1 = dur
                    if (dur1.asSecs() == 0 && dur1.subsecNanos() == 0) {
                        return Result.Err<Unit, Error>(Error.ZERO_TIMEOUT)
                    }
                    val secs =
                        if (dur1.asSecs() > TimeT.MAX_VALUE.toULong()) {
                            TimeT.MAX_VALUE
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

    public open fun timeout(kind: CInt): Result<Duration?, Error> {
        val raw: Timeval = when (val tryResult = getsockopt(this, SOL_SOCKET, kind)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Duration?, Error>(tryResult.errValue()) }
        return if (raw.tvSec == 0 && raw.tvUsec == 0) {
            Result.Ok<Duration?, Error>(null)
        } else {
            val sec = raw.tvSec.toULong()
            val nsec = (raw.tvUsec.toUInt()) * 1000u
            Result.Ok<Duration?, Error>(Duration.new(sec, nsec))
        }
    }

    public open fun shutdown(how: Shutdown): Result<Unit, Error> {
        val how1 =
            when (how) {
                Shutdown.Write -> SHUT_WR
                Shutdown.Read -> SHUT_RD
                Shutdown.Both -> SHUT_RDWR
            }
        when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.Shutdown(this.asRawFd(), how1))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun setLinger(linger: Duration?): Result<Unit, Error> {
        val linger1 = Linger(lOnoff = ((if (linger != null) 1 else 0)).toUShort(), lLinger = (minOf(linger.unwrapOrDefault().asSecs(), Int.MAX_VALUE.toULong()).toInt()).toUShort())
        return setsockopt(this, SOL_SOCKET, SO_LINGER, linger1)
    }

    public open fun linger(): Result<Duration?, Error> {
        val value: Linger = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_LINGER)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Duration?, Error>(tryResult.errValue()) }
        return Result.Ok<Duration?, Error>((value.lOnoff != 0u.toUShort()).then({ Duration.fromSecs(value.lLinger.toULong()) }))
    }

    public open fun setKeepalive(keepalive: Boolean): Result<Unit, Error> = setsockopt(this, SOL_SOCKET, SO_KEEPALIVE, keepalive.toInt())

    public open fun keepalive(): Result<Boolean, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_KEEPALIVE)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((raw != 0).toUByte())
    }

    public open fun setNodelay(nodelay: Boolean): Result<Unit, Error> = setsockopt(this, IPPROTO_TCP, TCP_NODELAY, nodelay.toInt())

    public open fun nodelay(): Result<Boolean, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_NODELAY)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((raw != 0).toUByte())
    }

    public open fun setQuickack(quickack: Boolean): Result<Unit, Error> = setsockopt(this, IPPROTO_TCP, TCP_QUICKACK, quickack.toInt())

    public open fun quickack(): Result<Boolean, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_QUICKACK)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((raw != 0).toUByte())
    }

    // bionic libc makes no use of this flag
    public open fun setDeferaccept(accept: Duration): Result<Unit, Error> {
        val value = minOf(accept.asSecs(), Int.MAX_VALUE.toULong()).toInt()
        return setsockopt(this, IPPROTO_TCP, TCP_DEFER_ACCEPT, value)
    }

    public open fun deferaccept(): Result<Duration, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_DEFER_ACCEPT)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Duration, Error>(tryResult.errValue()) }
        return Result.Ok<Duration, Error>(Duration.fromSecs(raw))
    }

    public open fun setPasscred(passcred: Boolean): Result<Unit, Error> = setsockopt(this, SOL_SOCKET, SO_PASSCRED, passcred.toInt())

    public open fun passcred(): Result<Boolean, Error> {
        val passcred: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_PASSCRED)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
        return Result.Ok<Boolean, Error>((passcred != 0).toUByte())
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> {
        val localStorageL586C8 = AddressableLocal(nonblocking.toInt(), UnresolvedTypeLayout<CInt>())
        return cvt(Ioctl(this.asRawFd(), FIONBIO, RustMutableReference(localStorageL586C8.asMutPtr(), localStorageL586C8.layout))).map({ drop(it) })
    }

    public open fun setMark(mark: UInt): Result<Unit, Error> {
        val option = SO_MARK
        return setsockopt(this, SOL_SOCKET, option, mark.toInt())
    }

    public open fun takeError(): Result<Error?, Error> {
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_ERROR)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Error?, Error>(tryResult.errValue()) }
        return if (raw == 0) {
            Result.Ok<Error?, Error>(null)
        } else {
            Result.Ok<Error?, Error>(Error.fromRawOsError(raw))
        }
    }

    public open fun asRaw(): RawFd = this.asRawFd()

    public override fun asInner(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        public fun new(family: CInt, ty: CInt): Result<Socket, Error> {
            // On platforms that support it we pass the SOCK_CLOEXEC
            // flag to atomically create the socket and set it as
            // CLOEXEC. On Linux this was added in 2.6.27.
            val fd = when (val tryResult = cvt(socket(family, ty or SOCK_CLOEXEC, 0))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Socket, Error>(tryResult.errValue()) }
            val socket = Socket(FileDesc.fromRawFd(fd))
            return Result.Ok<Socket, Error>(socket)
        }

        public fun newPair(fam: CInt, ty: CInt): Result<Pair<Socket, Socket>, Error> {
            return run {
                val localStorageL118C12 = AddressableArrayLocal.fromIntArray(intArrayOf(0, 0))
                val fds by localStorageL118C12
                // Like above, set cloexec atomically
                when (val tryResult = cvt(socketpair(fam, ty or SOCK_CLOEXEC, 0, localStorageL118C12.elementMutPtr(0)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Socket, Socket>, Error>(tryResult.errValue()) }
                Result.Ok<Pair<Socket, Socket>, Error>(Pair(Socket(FileDesc.fromRawFd(fds[0])), Socket(FileDesc.fromRawFd(fds[1]))))
            }
        }

        public fun fromInner(fileDesc: FileDesc): Socket = Socket(fileDesc)

        public fun fromRawFd(rawFd: RawFd): Socket = Socket(FromRawFd.fromRawFd(rawFd))
    }
}

public fun init() {}

public fun cvtGai(err: CInt): Result<Unit, Error> {
    if (err == 0) {
        return Result.Ok<Unit, Error>(Unit)
    }
    // We may need to trigger a glibc workaround. See onResolverFailure() for details.
    onResolverFailure()
    if (err == EAI_SYSTEM) {
        return Result.Err<Unit, Error>(Error.lastOsError())
    }
    val detail = CStr.fromPtr(gaiStrerror(err)).toStringLossy()
    return Result.Err<Unit, Error>(Error.new(ErrorKind.Uncategorized, "failed to lookup address information: $detail"))
}

// In versions of glibc prior to 2.26, there's a bug where the DNS resolver
// will cache the contents of /etc/resolv.conf, so changes to that file on disk
// can be ignored by a long-running program. That can break DNS lookups on e.g.
// laptops where the network comes and goes. See
// https://sourceware.org/bugzilla/showBug.cgi?id=984. Note however that some
// distros including Debian have patched glibc to fix this for a long time.
//
// A workaround for this bug is to call the resInit libc function, to clear
// the cached configs. Unfortunately, while we believe glibc's implementation
// of resInit is thread-safe, we know that other implementations are not
// (https://github.com/rust-lang/rust/issues/43592). Code here in std could
// try to synchronize its resInit calls with a Mutex, but that wouldn't
// protect programs that call into libc in other ways. So instead of calling
// resInit unconditionally, we call it only when we detect we're linking
// against glibc version < 2.26. (That is, when we both know its needed and
// believe it's thread-safe).
public fun onResolverFailure() {
    // If the version fails to parse, we treat it the same as "not glibc".
    when (val matchVal = Conf.glibcVersion()) {
        null -> {}
        else -> {
            val version = matchVal
            if (version < Pair(2, 26)) {
                resInit()
            }
        }
    }
}
