// port-lint: source os/unix/net/datagram.rs
package io.github.kotlinmania.rustlang.std.os.unix.net

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.bind
import io.github.kotlinmania.rustlang.libc.connect
import io.github.kotlinmania.rustlang.libc.getpeername
import io.github.kotlinmania.rustlang.libc.getsockname
import io.github.kotlinmania.rustlang.libc.recvfrom
import io.github.kotlinmania.rustlang.libc.sendto
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SOCK_DGRAM
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_RCVTIMEO
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SO_SNDTIMEO
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.AF_UNIX
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MSG_PEEK
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.fd.asFd
import io.github.kotlinmania.rustlang.std.os.fd.asRawFd
import io.github.kotlinmania.rustlang.std.os.fd.fromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.intoRawFd
import io.github.kotlinmania.rustlang.std.os.unix.net.ancillary.SocketAncillary
import io.github.kotlinmania.rustlang.std.os.unix.net.ancillary.recvVectoredWithAncillaryFrom
import io.github.kotlinmania.rustlang.std.os.unix.net.ancillary.sendVectoredWithAncillaryTo
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.asInner
import io.github.kotlinmania.rustlang.std.sys.fromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

private class DebugStruct(
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

public val MSG_NOSIGNAL: CInt = 0x0

/**
 * A Unix datagram socket.
 *
 * # Examples
 *
 * use std.os.unix.net.UnixDatagram;
 *
 * fn main() -> std.io.Result<()> (
 *     let socket = UnixDatagram.bind("/path/to/my/socket")?;
 *     socket.sendTo(b"hello world", "/path/to/other/socket")?;
 *     let mut buf = [0; 100];
 *     let (count, address) = socket.recvFrom(buf)?;
 *     println("socket (:?) sent (:?)", address, &buf[..count]);
 *     Ok(())
 * )
 * ```kotlin
 */
public open class UnixDatagram(
    public var value: Socket,
) : AsRawFd, FromRawFd<UnixDatagram>, IntoRawFd, AsFd, AsInner<Socket> {
    public var field0: Socket
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Error> {
        val builder = DebugStruct(fmt, "UnixDatagram")
        builder.field("fd", this.value.asInner())
        when (val matchVal = this.localAddr()) {
            is Result.Ok<*, *> -> {
                val addr = matchVal.okValue()
                builder.field("local", addr)
            }
            else -> {}
        }
        when (val matchVal1 = this.peerAddr()) {
            is Result.Ok<*, *> -> {
                val addr1 = matchVal1.okValue()
                builder.field("peer", addr1)
            }
            else -> {}
        }
        return run { builder.finish(); Result.Ok<Unit, Error>(Unit) }
    }

    /**
     * Connects the socket to the specified path address.
     *
     * The [send] method may be used to send data to the specified address.
     * [recv] and [recvFrom] will only receive data from that address.
     *
     * [send]: UnixDatagram.send
     * [recv]: UnixDatagram.recv
     * [recvFrom]: UnixDatagram.recvFrom
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     match sock.connect("/path/to/the/socket") (
     *         Ok(sock) => sock,
     *         Err(e) => (
     *             println("Couldn't connect: (e:?)");
     *             return Err(e)
     *         )
     *     );
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun connect(path: Path): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> {
        run {
            val (addr, len) = when (val tryResult = SockaddrUn(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.connect(this.asRawFd(), constPtrFromRustCast((addrOf(addr))), len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        return Result.Ok<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(Unit)
    }

    /**
     * Connects the socket to an address.
     *
     * # Examples
     *
     * use std.os.unix.net::(UnixDatagram);
     *
     * fn main() -> std.io.Result<()> (
     *     let bound = UnixDatagram.bind("/path/to/socket")?;
     *     let addr = bound.localAddr()?;
     *
     *     let sock = UnixDatagram.unbound()?;
     *     match sock.connectAddr(&addr) (
     *         Ok(sock) => sock,
     *         Err(e) => (
     *             println("Couldn't connect: (e:?)");
     *             return Err(e)
     *         )
     *     );
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun connectAddr(socketAddr: SocketAddr): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> {
        run {
            when (val tryResult = cvt(connect(this.asRawFd(), (addrOf(socketAddr.addr)), socketAddr.len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
        return Result.Ok<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(Unit)
    }

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned `UnixDatagram` is a reference to the same socket that this
     * object references. Both handles can be used to accept incoming
     * connections and options set on one side will affect the other.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.bind("/path/to/the/socket")?;
     *     let sockCopy = sock.tryClone().expect("`UnixDatagram.tryClone` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun tryClone(): Result<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.duplicate().map({ UnixDatagram(it) })

    /**
     * Returns the address of this socket.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.bind("/path/to/the/socket")?;
     *     let addr = sock.localAddr().expect("`UnixDatagram.localAddr` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun localAddr(): Result<SocketAddr, io.github.kotlinmania.rustlang.core.io.error.Error> = SocketAddr.new({ addr, len -> getsockname(this.asRawFd(), addr, len) })

    /**
     * Returns the address of this socket's peer.
     *
     * The [connect] method will connect the socket to a peer.
     *
     * [connect]: UnixDatagram.connect
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.connect("/path/to/the/socket")?;
     *
     *     let addr = sock.peerAddr().expect("`UnixDatagram.peerAddr` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peerAddr(): Result<SocketAddr, io.github.kotlinmania.rustlang.core.io.error.Error> = SocketAddr.new({ addr, len -> getpeername(this.asRawFd(), addr, len) })

    public open fun recvFromFlags(buf: MutableList<UByte>, flags: CInt): Result<Pair<Int, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error> {
        var count = 0
        val addr =
            when (val tryResult = SocketAddr.new({ addr, len -> run {
                count = recvfrom(this.asRawFd(), buf.asMutPtr(), buf.size, flags, addr, len)
                if (count > 0) {
                    1
                } else if (count == 0) {
                    0
                } else {
                    -1
                }
            } })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Int, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        return Result.Ok<Pair<Int, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error>(Pair(count.toInt(), addr))
    }

    /**
     * Receives data from the socket.
     *
     * On success, returns the number of bytes read and the address from
     * whence the data came.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     let mut buf = vec[0; 10];
     *     let (size, sender) = sock.recvFrom(buf.asMutSlice())?;
     *     println("received (size) bytes from (sender:?)");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error> = this.recvFromFlags(buf, 0)

    /**
     * Receives data from the socket.
     *
     * On success, returns the number of bytes read.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.bind("/path/to/the/socket")?;
     *     let mut buf = vec[0; 10];
     *     sock.recv(buf.asMutSlice()).expect("`UnixDatagram.recv` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun recv(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.read(buf)

    /**
     * Receives data and ancillary data from socket.
     *
     * On success, returns the number of bytes read, if the data was truncated and the address from whence the msg came.
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::(UnixDatagram, SocketAncillary, AncillaryData);
     * use std.io.IoSliceMut;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     let mut buf1 = [1; 8];
     *     let mut buf2 = [2; 16];
     *     let mut buf3 = [3; 8];
     *     let mut bufs = [
     *         IoSliceMut.new(buf1),
     *         IoSliceMut.new(buf2),
     *         IoSliceMut.new(buf3),
     *     ][..];
     *     let mut fds = [0; 8];
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *     let (size, _truncated, sender) = sock.recvVectoredWithAncillaryFrom(bufs, ancillary)?;
     *     println("received (size)");
     *     for ancillaryResult in ancillary.messages() (
     *         if let AncillaryData.ScmRights(scmRights) = ancillaryResult.unwrap() (
     *             for fd in scmRights (
     *                 println("receive file descriptor: (fd)");
     *             )
     *         )
     *     )
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun recvVectoredWithAncillaryFrom(bufs: MutableList<IoSliceMut>, ancillary: SocketAncillary): Result<Triple<Int, Boolean, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error> {
        val (count, truncated, addr) = when (val tryResult = recvVectoredWithAncillaryFrom(this.value, bufs, ancillary)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Triple<Int, Boolean, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        val addr1 = when (val tryResult = addr) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Triple<Int, Boolean, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        return Result.Ok<Triple<Int, Boolean, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error>(Triple(count, truncated, addr1))
    }

    /**
     * Receives data and ancillary data from socket.
     *
     * On success, returns the number of bytes read and if the data was truncated.
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::(UnixDatagram, SocketAncillary, AncillaryData);
     * use std.io.IoSliceMut;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     let mut buf1 = [1; 8];
     *     let mut buf2 = [2; 16];
     *     let mut buf3 = [3; 8];
     *     let mut bufs = [
     *         IoSliceMut.new(buf1),
     *         IoSliceMut.new(buf2),
     *         IoSliceMut.new(buf3),
     *     ][..];
     *     let mut fds = [0; 8];
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *     let (size, _truncated) = sock.recvVectoredWithAncillary(bufs, ancillary)?;
     *     println("received (size)");
     *     for ancillaryResult in ancillary.messages() (
     *         if let AncillaryData.ScmRights(scmRights) = ancillaryResult.unwrap() (
     *             for fd in scmRights (
     *                 println("receive file descriptor: (fd)");
     *             )
     *         )
     *     )
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun recvVectoredWithAncillary(bufs: MutableList<IoSliceMut>, ancillary: SocketAncillary): Result<Pair<Int, Boolean>, io.github.kotlinmania.rustlang.core.io.error.Error> {
        val (count, truncated, addr) = when (val tryResult = recvVectoredWithAncillaryFrom(this.value, bufs, ancillary)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Int, Boolean>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        when (val tryResult = addr) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Int, Boolean>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        return Result.Ok<Pair<Int, Boolean>, io.github.kotlinmania.rustlang.core.io.error.Error>(Pair(count, truncated))
    }

    /**
     * Sends data on the socket to the specified address.
     *
     * On success, returns the number of bytes written.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.sendTo(b"omelette au fromage", "/some/sock").expect("`UnixDatagram.sendTo` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun sendTo(buf: List<UByte>, path: Path): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> {
        return run {
            val (addr, len) = when (val tryResult = SockaddrUn(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            val count = when (val tryResult = cvt(sendto(this.asRawFd(), buf.asPtr(), buf.size, MSG_NOSIGNAL, constPtrFromRustCast((addrOf(addr))), len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            Result.Ok<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(count.toInt())
        }
    }

    /**
     * Sends data on the socket to the specified [SocketAddr].
     *
     * On success, returns the number of bytes written.
     *
     * # Examples
     *
     * use std.os.unix.net::(UnixDatagram);
     *
     * fn main() -> std.io.Result<()> (
     *     let bound = UnixDatagram.bind("/path/to/socket")?;
     *     let addr = bound.localAddr()?;
     *
     *     let sock = UnixDatagram.unbound()?;
     *     sock.sendToAddr(b"bacon egg and cheese", &addr).expect("`UnixDatagram.sendToAddr` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun sendToAddr(buf: List<UByte>, socketAddr: SocketAddr): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> {
        return run {
            val count = when (val tryResult = cvt(sendto(this.asRawFd(), buf.asPtr(), buf.size, MSG_NOSIGNAL, (addrOf(socketAddr.addr)), socketAddr.len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            Result.Ok<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(count.toInt())
        }
    }

    /**
     * Sends data on the socket to the socket's peer.
     *
     * The peer address may be set by the `connect` method, and this method
     * will return an error if the socket has not already been connected.
     *
     * On success, returns the number of bytes written.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.connect("/some/sock").expect("`UnixDatagram.connect` should not fail");
     *     sock.send(b"omelette au fromage").expect("`UnixDatagram` is not connected");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun send(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.write(buf)

    /**
     * Sends data and ancillary data on the socket to the specified address.
     *
     * On success, returns the number of bytes written.
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::(UnixDatagram, SocketAncillary);
     * use std.io.IoSlice;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     let buf1 = [1; 8];
     *     let buf2 = [2; 16];
     *     let buf3 = [3; 8];
     *     let bufs = &[
     *         IoSlice.new(&buf1),
     *         IoSlice.new(&buf2),
     *         IoSlice.new(&buf3),
     *     ][..];
     *     let fds = [0, 1, 2];
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *     ancillary.addFds(&fds[..]);
     *     sock.sendVectoredWithAncillaryTo(bufs, ancillary, "/some/sock")
     *         .expect("`UnixDatagram.sendVectoredWithAncillaryTo` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun sendVectoredWithAncillaryTo(bufs: List<IoSlice>, ancillary: SocketAncillary, path: Path): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = sendVectoredWithAncillaryTo(this.value, path, bufs, ancillary)

    /**
     * Sends data and ancillary data on the socket.
     *
     * On success, returns the number of bytes written.
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::(UnixDatagram, SocketAncillary);
     * use std.io.IoSlice;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     let buf1 = [1; 8];
     *     let buf2 = [2; 16];
     *     let buf3 = [3; 8];
     *     let bufs = &[
     *         IoSlice.new(&buf1),
     *         IoSlice.new(&buf2),
     *         IoSlice.new(&buf3),
     *     ][..];
     *     let fds = [0, 1, 2];
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *     ancillary.addFds(&fds[..]);
     *     sock.sendVectoredWithAncillary(bufs, ancillary)
     *         .expect("`UnixDatagram.sendVectoredWithAncillary` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun sendVectoredWithAncillary(bufs: List<IoSlice>, ancillary: SocketAncillary): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = sendVectoredWithAncillaryTo(this.value, null, bufs, ancillary)

    /**
     * Sets the read timeout for the socket.
     *
     * If the provided value is [None], then [recv] and [recvFrom] calls will
     * block indefinitely. An [Err] is returned if the zero [Duration]
     * is passed to this method.
     *
     * [recv]: UnixDatagram.recv
     * [recvFrom]: UnixDatagram.recvFrom
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setReadTimeout(Some(Duration.new(1, 0)))
     *         .expect("`UnixDatagram.setReadTimeout` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * import std.io;
     * import std.os.unix.net.UnixDatagram;
     * import std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     * val socket = UnixDatagram.unbound();
     * val result = socket.setReadTimeout(Some(Duration.new(0, 0)));
     * val err = result.unwrapErr();
     * assertEq(err.kind(), io.ErrorKind.InvalidInput);
     * Ok(())
     * )
     * ```
     */
    public open fun setReadTimeout(timeout: Duration?): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setTimeout(timeout, SO_RCVTIMEO)

    /**
     * Sets the write timeout for the socket.
     *
     * If the provided value is [None], then [send] and [sendTo] calls will
     * block indefinitely. An [Err] is returned if the zero [Duration] is passed to this
     * method.
     *
     * [send]: UnixDatagram.send
     * [sendTo]: UnixDatagram.sendTo
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setWriteTimeout(Some(Duration.new(1, 0)))
     *         .expect("`UnixDatagram.setWriteTimeout` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * import std.io;
     * import std.os.unix.net.UnixDatagram;
     * import std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     * val socket = UnixDatagram.unbound();
     * val result = socket.setWriteTimeout(Some(Duration.new(0, 0)));
     * val err = result.unwrapErr();
     * assertEq(err.kind(), io.ErrorKind.InvalidInput);
     * Ok(())
     * )
     * ```
     */
    public open fun setWriteTimeout(timeout: Duration?): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setTimeout(timeout, SO_SNDTIMEO)

    /**
     * Returns the read timeout of this socket.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setReadTimeout(Some(Duration.new(1, 0)))
     *         .expect("`UnixDatagram.setReadTimeout` should not fail");
     *     assertEq(sock.readTimeout()?, Some(Duration.new(1, 0)));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun readTimeout(): Result<Duration?, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.timeout(SO_RCVTIMEO)

    /**
     * Returns the write timeout of this socket.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setWriteTimeout(Some(Duration.new(1, 0)))
     *         .expect("`UnixDatagram.setWriteTimeout` should not fail");
     *     assertEq(sock.writeTimeout()?, Some(Duration.new(1, 0)));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun writeTimeout(): Result<Duration?, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.timeout(SO_SNDTIMEO)

    /**
     * Moves the socket into or out of nonblocking mode.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setNonblocking(true).expect("`UnixDatagram.setNonblocking` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setNonblocking(nonblocking)

    /**
     * Set the id of the socket for network filtering purpose
     *
     * @file:feature(unixSetMark)
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setMark(32)?;
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun setMark(mark: UInt): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setMark(mark)

    /**
     * Returns the value of the `SO_ERROR` option.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     if let Ok(Some(err)) = sock.takeError() (
     *         println("Got error: (err:?)");
     *     )
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun takeError(): Result<io.github.kotlinmania.rustlang.core.io.error.Error?, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.takeError()

    /**
     * Shut down the read, write, or both halves of this connection.
     *
     * This function will cause all pending and future I/O calls on the
     * specified portions to immediately return with an appropriate value
     * (see the documentation of [Shutdown]).
     *
     * use std.os.unix.net.UnixDatagram;
     * use std.net.Shutdown;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixDatagram.unbound()?;
     *     sock.shutdown(Shutdown.Both).expect("`UnixDatagram.shutdown` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun shutdown(how: Shutdown): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.shutdown(how)

    /**
     * Receives data on the socket from the remote address to which it is
     * connected, without removing that data from the queue. On success,
     * returns the number of bytes peeked.
     *
     * Successive calls return the same data. This is accomplished by passing
     * `MSG_PEEK` as a flag to the underlying `recv` system call.
     *
     * # Examples
     *
     * @file:feature(unixSocketPeek)
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixDatagram.bind("/tmp/sock")?;
     *     let mut buf = [0; 10];
     *     let len = socket.peek(buf).expect("`UnixDatagram.peek` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peek(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.peek(buf)

    /**
     * Receives a single datagram message on the socket, without removing it from the
     * queue. On success, returns the number of bytes read and the origin.
     *
     * The function must be called with valid byte array `buf` of sufficient size to
     * hold the message bytes. If a message is too long to fit in the supplied buffer,
     * excess bytes may be discarded.
     *
     * Successive calls return the same data. This is accomplished by passing
     * `MSG_PEEK` as a flag to the underlying `recvfrom` system call.
     *
     * Do not use this function to implement busy waiting, instead use `libc.poll` to
     * synchronize IO events on one or more sockets.
     *
     * # Examples
     *
     * @file:feature(unixSocketPeek)
     *
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixDatagram.bind("/tmp/sock")?;
     *     let mut buf = [0; 10];
     *     let (len, addr) = socket.peekFrom(buf).expect("peek failed");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, io.github.kotlinmania.rustlang.core.io.error.Error> = this.recvFromFlags(buf, MSG_PEEK)

    public override fun asRawFd(): RawFd = this.value.asInner().asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoInner().intoInner().intoRawFd()

    public override fun asFd(): BorrowedFd = this.value.asInner().asFd()

    public override fun asInner(): Socket = this.value

    public companion object {
        /**
         * Creates a Unix datagram socket bound to the given path.
         *
         * # Examples
         *
         * use std.os.unix.net.UnixDatagram;
         *
         * let sock = match UnixDatagram.bind("/path/to/the/socket") (
         *     Ok(sock) => sock,
         *     Err(e) => (
         *         println("Couldn't bind: (e:?)");
         *         return
         *     )
         * );
         * ```kotlin
         */
        public fun bind(path: Path): Result<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error> {
            return run {
                val socket = when (val tryResult = UnixDatagram.unbound()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                val (addr, len) = when (val tryResult = SockaddrUn(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.bind(socket.asRawFd(), constPtrFromRustCast((addrOf(addr))), len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                Result.Ok<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(socket)
            }
        }

        /**
         * Creates a Unix datagram socket bound to an address.
         *
         * # Examples
         *
         * use std.os.unix.net::(SocketAddr, UnixDatagram);
         *
         * fn main() -> std.io.Result<()> (
         *     let addr = SocketAddr.fromPathname("path/to/socket")?;
         *
         *     let sock = match UnixDatagram.bindAddr(&addr) (
         *         Ok(sock) => sock,
         *         Err(err) => (
         *             println("Couldn't bind: (err:?)");
         *             return Err(err);
         *         )
         *     );
         *     Ok(())
         * )
         * ```kotlin
         */
        public fun bindAddr(socketAddr: SocketAddr): Result<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error> {
            return run {
                val socket = when (val tryResult = UnixDatagram.unbound()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                when (val tryResult = cvt(bind(socket.asRawFd(), (addrOf(socketAddr.addr)), socketAddr.len))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
                Result.Ok<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(socket)
            }
        }

        /**
         * Creates a Unix Datagram socket which is not bound to any address.
         *
         * # Examples
         *
         * use std.os.unix.net.UnixDatagram;
         *
         * let sock = match UnixDatagram.unbound() (
         *     Ok(sock) => sock,
         *     Err(e) => (
         *         println("Couldn't unbound: (e:?)");
         *         return
         *     )
         * );
         * ```kotlin
         */
        public fun unbound(): Result<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error> {
            val inner = when (val tryResult = Socket.new(AF_UNIX, SOCK_DGRAM)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            return Result.Ok<UnixDatagram, io.github.kotlinmania.rustlang.core.io.error.Error>(UnixDatagram(inner))
        }

        /**
         * Creates an unnamed pair of connected sockets.
         *
         * Returns two `UnixDatagrams`s which are connected to each other.
         *
         * # Examples
         *
         * use std.os.unix.net.UnixDatagram;
         *
         * let (sock1, sock2) = match UnixDatagram.pair() (
         *     Ok((sock1, sock2)) => (sock1, sock2),
         *     Err(e) => (
         *         println("Couldn't unbound: (e:?)");
         *         return
         *     )
         * );
         * ```kotlin
         */
        public fun pair(): Result<Pair<UnixDatagram, UnixDatagram>, io.github.kotlinmania.rustlang.core.io.error.Error> {
            val (i1, i2) = when (val tryResult = Socket.newPair(AF_UNIX, SOCK_DGRAM)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<UnixDatagram, UnixDatagram>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            return Result.Ok<Pair<UnixDatagram, UnixDatagram>, io.github.kotlinmania.rustlang.core.io.error.Error>(Pair(UnixDatagram(i1), UnixDatagram(i2)))
        }

        public fun fromRawFd(fd: RawFd): UnixDatagram = UnixDatagram(Socket.fromInner(FromInner.fromInner(OwnedFd.fromRawFd(fd))))

        public fun from(owned: OwnedFd): UnixDatagram = UnixDatagram.fromRawFd(owned.intoRawFd())
    }
}

/**
 * Takes ownership of a [UnixDatagram]s socket file descriptor.
 */
public fun fromOwnedFd(unixDatagram: UnixDatagram): OwnedFd = OwnedFd.fromRawFd(unixDatagram.intoRawFd())
