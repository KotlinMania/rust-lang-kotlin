// port-lint: source sys/net/connection/socket/unix.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.Poll
import io.github.kotlinmania.libc.Send
import io.github.kotlinmania.libc.fuchsia.AF_LOCAL
import io.github.kotlinmania.libc.fuchsia.EAI_SYSTEM
import io.github.kotlinmania.libc.fuchsia.EINTR
import io.github.kotlinmania.libc.fuchsia.EISCONN
import io.github.kotlinmania.libc.fuchsia.FIONBIO
import io.github.kotlinmania.libc.fuchsia.IPPROTO_TCP
import io.github.kotlinmania.libc.fuchsia.Linger
import io.github.kotlinmania.libc.fuchsia.MSG_CMSG_CLOEXEC
import io.github.kotlinmania.libc.fuchsia.MSG_PEEK
import io.github.kotlinmania.libc.fuchsia.Msghdr
import io.github.kotlinmania.libc.fuchsia.POLLERR
import io.github.kotlinmania.libc.fuchsia.POLLHUP
import io.github.kotlinmania.libc.fuchsia.POLLOUT
import io.github.kotlinmania.libc.fuchsia.Pollfd
import io.github.kotlinmania.libc.fuchsia.SHUT_RD
import io.github.kotlinmania.libc.fuchsia.SHUT_RDWR
import io.github.kotlinmania.libc.fuchsia.SHUT_WR
import io.github.kotlinmania.libc.fuchsia.SOL_SOCKET
import io.github.kotlinmania.libc.fuchsia.SO_ERROR
import io.github.kotlinmania.libc.fuchsia.SO_KEEPALIVE
import io.github.kotlinmania.libc.fuchsia.SO_PASSCRED
import io.github.kotlinmania.libc.fuchsia.SizeT
import io.github.kotlinmania.libc.fuchsia.SockaddrStorage
import io.github.kotlinmania.libc.fuchsia.TCP_DEFER_ACCEPT
import io.github.kotlinmania.libc.fuchsia.TCP_NODELAY
import io.github.kotlinmania.libc.fuchsia.TCP_QUICKACK
import io.github.kotlinmania.libc.fuchsia.TimeT
import io.github.kotlinmania.libc.fuchsia.Timeval
import io.github.kotlinmania.libc.recv
import io.github.kotlinmania.libc.recvfrom
import io.github.kotlinmania.libc.recvmsg
import io.github.kotlinmania.libc.resInit
import io.github.kotlinmania.libc.sendmsg
import io.github.kotlinmania.libc.unix.bsd.freebsdlike.AcceptFilterArg
import io.github.kotlinmania.libc.unix.bsd.freebsdlike.freebsd.LOCAL_CREDS_PERSISTENT
import io.github.kotlinmania.libc.unix.bsd.netbsdlike.openbsd.SO_RTABLE
import io.github.kotlinmania.libc.unix.linuxlike.android.Ioctl
import io.github.kotlinmania.libc.unix.newlib.SO_ACCEPTFILTER
import io.github.kotlinmania.libc.unix.nto.LOCAL_CREDS
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.io.error.generic.errno
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.MAX_SEND_LEN
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.getsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.setsockopt
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrFromC
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.socketAddrToC
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvtR
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.SO_LINGER
import io.github.kotlinmania.rustlang.std.sys.pal.unix.conf.glibcVersion
import io.github.kotlinmania.rustlang.std.time.Instant

public typealias WrlenT = SizeT

public open class Socket(
    public var value: FileDesc,
) : AsInner<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, Socket>, AsFd, AsRawFd, IntoRawFd, FromRawFd<Socket, Any?, Any> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun connect(addr: SocketAddr): Result<Unit> {
        val (addr1, len) = socketAddrToC(addr)
        while (true) {
            val result = io.github.kotlinmania.libc.connect(this.asRawFd(), addr1.asPtr(), len)
            if (result.isMinusOne()) {
                val err = errno()
                when (err) {
                    EINTR -> { continue }
                    EISCONN -> { return Result.Ok(Unit) }
                    else -> { return Result.Err(io.Error.fromRawOsError(err)) }
                }
            }
            return Result.Ok(Unit)
        }
    }

    public open fun pollConnected(timeout: Duration): Result<Unit> {
        val timeout1 = timeout
        val pollfd: Pollfd?  = null
        pollfd!!.fd = this.asRawFd()
        pollfd.events = POLLOUT
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
            when (Poll(pollfd, 1, timeout4)) {
                -1 -> {
                    val err = io.Error.lastOsError()
                    if (!err.isInterrupted()) {
                        return Result.Err(err)
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
                        return Result.Ok(Unit)
                    }
                    when (val matchVal = when (val tryResult = this.takeError()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }) {
                        null -> {}
                        else -> {
                            val e = matchVal
                            return Result.Err(e)
                        }
                    }
                    if (hupOrErr) {
                        return Result.Err(io.constError(io.ErrorKind.Uncategorized, "no error set after POLLHUP"))
                    }
                    return Result.Ok(Unit)
                }
            }
        }
    }

    public open fun accept(storage: MutPtr, len: MutPtr): Result<Socket> {
        // Unfortunately the only known way right now to accept a socket and
        // atomically set the CLOEXEC flag is to use the `accept4` syscall on
        // platforms that support it. On Linux, this was added in 2.6.28,
        // glibc 2.10 and musl 0.9.5.
        return cfgSelect(any, targetOs, "android", targetOs, "dragonfly", targetOs, "freebsd", targetOs, "illumos", targetOs, "linux", targetOs, "hurd", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", fd, cvtR, libc, accept4, this, asRawFd, storage, len, libc, SOCK_CLOEXEC, Ok, Socket, FileDesc, fromRawFd, fd, fd, cvtR, libc, accept, this, asRawFd, storage, len, fd, FileDesc, fromRawFd, fd, fd, setCloexec, Ok, Socket, fd)
    }

    public open fun duplicate(): Result<Socket> = this.value.duplicate().map({ Socket(it) })

    public open fun sendWithFlags(buf: List<UByte>, flags: CInt): Result<Int> {
        val len = minOf(buf.size, MAX_SEND_LEN).toULong()
        val ret = when (val tryResult = cvt(Send(this.asRawFd(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, len, flags))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun recvWithFlags(buf: BorrowedCursor<UByte>, flags: CInt): Result<Unit> {
        val ret = when (val tryResult = cvt(recv(this.asRawFd(), mutPtrFromRustCast(buf.asMut().asMutPtr()), buf.capacity(), flags))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
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

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun recvFromWithFlags(buf: MutableList<UByte>, flags: CInt): Result<Pair<Int, SocketAddr>> {
        // The `recvfrom` function will fill in the storage with the address,
        // so we don't need to zero it here.
        // reference: https://linux.die.net/man/2/recvfrom
        val localStorageL330C8 = AddressableLocal(MaybeUninit.uninit(ObjectSlotLayout<SockaddrStorage>()), ObjectSlotLayout<MaybeUninit<SockaddrStorage>>())
        val storage by localStorageL330C8
        val addrlen = sizeOfVal(storage)
        val n = when (val tryResult = cvt(recvfrom(this.asRawFd(), (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, buf.size, flags, (localStorageL330C8.asMutPtr()), addrlen))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(n.toInt(), when (val tryResult = socketAddrFromC(storage.asPtr(), addrlen)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
    }

    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvFromWithFlags(buf, 0)

    public open fun recvMsg(msg: Msghdr): Result<Int> {
        val n = when (val tryResult = cvt(recvmsg(this.asRawFd(), msg, MSG_CMSG_CLOEXEC))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(n.toInt())
    }

    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>> = this.recvFromWithFlags(buf, MSG_PEEK)

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun sendMsg(msg: Msghdr): Result<Int> {
        val n = when (val tryResult = cvt(sendmsg(this.asRawFd(), msg, 0))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(n.toInt())
    }

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
        when (val tryResult = cvt(io.github.kotlinmania.libc.Shutdown(this.asRawFd(), how1))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
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

    public open fun setKeepalive(keepalive: Boolean): Result<Unit> = setsockopt(this, SOL_SOCKET, SO_KEEPALIVE, keepalive.toInt())

    public open fun keepalive(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_KEEPALIVE)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setNodelay(nodelay: Boolean): Result<Unit> = setsockopt(this, IPPROTO_TCP, TCP_NODELAY, nodelay.toInt())

    public open fun nodelay(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_NODELAY)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setQuickack(quickack: Boolean): Result<Unit> = setsockopt(this, IPPROTO_TCP, TCP_QUICKACK, quickack.toInt())

    public open fun quickack(): Result<Boolean> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_QUICKACK)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    // bionic libc makes no use of this flag
    public open fun setDeferaccept(accept: Duration): Result<Unit> {
        val value = minOf(accept.asSecs(), CInt.MAX_VALUE.toULong()).toInt()
        return setsockopt(this, IPPROTO_TCP, TCP_DEFER_ACCEPT, value)
    }

    public open fun deferaccept(): Result<Duration> {
        val raw: CInt = when (val tryResult = getsockopt(this, IPPROTO_TCP, TCP_DEFER_ACCEPT)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Duration.fromSecs(((raw as Any?) as Result<Duration>)))
    }

    public open fun setAcceptfilter(name: CStr): Result<Unit> =
        if (!name.toBytes().isEmpty()) {
            val AF_NAME_MAX: Int = 16
            val buf = MutableList((AF_NAME_MAX).toInt()) { 0 }
            for ((src, dst) in (name.toBytes().iter().zip(buf.subList(0, AF_NAME_MAX - 1)))) {
                dst = src.toUByte()
            }
            val arg: AcceptFilterArg?  = null
            arg!!.afName = buf
            setsockopt(this, SOL_SOCKET, SO_ACCEPTFILTER, arg)
        } else {
            setsockopt(this, SOL_SOCKET, SO_ACCEPTFILTER, mutPtrFromRustCast(MutPtr(0L)))
        }

    public open fun acceptfilter(): Result<CStr> {
        val arg: AcceptFilterArg = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_ACCEPTFILTER)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val s: List<UByte> = rawSliceList(constPtrFromRustCast(arg.afName.asPtr()), 16, PrimitiveLayouts.U8)
        val name = CStr.fromBytesWithNul(s).unwrap()
        return Result.Ok(name)
    }

    public open fun setExclbind(excl: Boolean): Result<Unit> {
        // not yet on libc crate
        val SO_EXCLBIND: Int = 0x1015
        return setsockopt(this, SOL_SOCKET, SO_EXCLBIND, excl)
    }

    public open fun exclbind(): Result<Boolean> {
        // not yet on libc crate
        val SO_EXCLBIND: Int = 0x1015
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_EXCLBIND)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(raw != 0)
    }

    public open fun setPasscred(passcred: Boolean): Result<Unit> = setsockopt(this, SOL_SOCKET, SO_PASSCRED, passcred.toInt())

    public open fun passcred(): Result<Boolean> {
        val passcred: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_PASSCRED)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(passcred != 0)
    }

    public open fun setLocalCreds(localCreds: Boolean): Result<Unit> = setsockopt(this, 0, LOCAL_CREDS, localCreds.toInt())

    public open fun localCreds(): Result<Boolean> {
        val localCreds: CInt = when (val tryResult = getsockopt(this, 0, LOCAL_CREDS)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(localCreds != 0)
    }

    public open fun setLocalCredsPersistent(localCredsPersistent: Boolean): Result<Unit> = setsockopt(this, AF_LOCAL, LOCAL_CREDS_PERSISTENT, localCredsPersistent.toInt())

    public open fun localCredsPersistent(): Result<Boolean> {
        val localCredsPersistent: CInt = when (val tryResult = getsockopt(this, AF_LOCAL, LOCAL_CREDS_PERSISTENT)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(localCredsPersistent != 0)
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> {
        val nonblocking1 = nonblocking.toInt()
        return cvt(Ioctl(this.asRawFd(), FIONBIO, nonblocking1)).map(drop)
    }

    public open fun setMark(mark: UInt): Result<Unit> {
        val option2 = SO_RTABLE
        return setsockopt(this, SOL_SOCKET, option2, mark.toInt())
    }

    public open fun takeError(): Result<Error?> {
        val raw: CInt = when (val tryResult = getsockopt(this, SOL_SOCKET, SO_ERROR)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (raw == 0) {
            Result.Ok(null)
        } else {
            Result.Ok(io.Error.fromRawOsError(raw))
        }
    }

    public open fun asRaw(): RawFd = this.asRawFd()

    public override fun asInner(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        public fun new(family: CInt, ty: CInt): Result<Socket> = cfgSelect(any, targetOs, "android", targetOs, "dragonfly", targetOs, "freebsd", targetOs, "illumos", targetOs, "hurd", targetOs, "linux", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "nto", targetOs, "qnx", targetOs, "solaris", fd, cvt, libc, socket, family, ty, libc, SOCK_CLOEXEC, 0, socket, Socket, FileDesc, fromRawFd, fd, cfg, any, targetOs, "freebsd", targetOs, "netbsd", targetOs, "dragonfly", setsockopt, socket, libc, SOL_SOCKET, libc, SO_NOSIGPIPE, 1, Ok, socket, fd, cvt, libc, socket, family, ty, 0, fd, FileDesc, fromRawFd, fd, fd, setCloexec, socket, Socket, fd, cfg, targetVendor, "apple", setsockopt, socket, libc, SOL_SOCKET, libc, SO_NOSIGPIPE, 1, Ok, socket)

        public fun newPair(fam: CInt, ty: CInt): Result<Pair<Socket, Socket>> =
            run {
                val fds = mutableListOf(0, 0)
                cfgSelect(any, targetOs, "android", targetOs, "dragonfly", targetOs, "freebsd", targetOs, "illumos", targetOs, "linux", targetOs, "hurd", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "nto", targetOs, "qnx", cvt, libc, socketpair, fam, ty, libc, SOCK_CLOEXEC, 0, fds, asMutPtr, Ok, Socket, FileDesc, fromRawFd, fds, 0, Socket, FileDesc, fromRawFd, fds, 1, cvt, libc, socketpair, fam, ty, 0, fds, asMutPtr, a, FileDesc, fromRawFd, fds, 0, b, FileDesc, fromRawFd, fds, 1, a, setCloexec, b, setCloexec, Ok, Socket, a, Socket, b)
            }

        public fun fromInner(fileDesc: FileDesc): Socket = Socket(fileDesc)

        public fun fromRawFd(rawFd: RawFd): Socket = Socket(FromRawFd.fromRawFd(rawFd))
    }
}

public fun init() {}

public fun cvtGai(err: CInt): Result<Unit> {
    if (err == 0) {
        return Result.Ok(Unit)
    }
    // We may need to trigger a glibc workaround. See onResolverFailure() for details.
    onResolverFailure()
    if (err == EAI_SYSTEM) {
        return Result.Err(io.Error.lastOsError())
    }
    val detail = ""
    return Result.Err(io.Error.new(io.ErrorKind.Uncategorized, "failed to lookup address information: $detail"))
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
    when (val matchVal = glibcVersion()) {
        null -> {}
        else -> {
            val version = matchVal
            if (version < Pair(2, 26)) {
                resInit()
            }
        }
    }
}
