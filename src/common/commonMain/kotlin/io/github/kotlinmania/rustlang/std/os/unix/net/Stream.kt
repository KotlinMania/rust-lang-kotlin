// port-lint: source os/unix/net/stream.rs
package io.github.kotlinmania.rustlang.std.os.unix.net

import io.github.kotlinmania.libc.fuchsia.AF_UNIX
import io.github.kotlinmania.libc.fuchsia.SOCK_STREAM
import io.github.kotlinmania.libc.fuchsia.SO_RCVTIMEO
import io.github.kotlinmania.libc.fuchsia.SO_SNDTIMEO
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.connect
import io.github.kotlinmania.rustlang.libc.getpeername
import io.github.kotlinmania.rustlang.libc.getsockname
import io.github.kotlinmania.rustlang.std.io.BorrowedCursor
import io.github.kotlinmania.rustlang.std.io.Read
import io.github.kotlinmania.rustlang.std.io.Write
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.os.unix.net.ancillary.SocketAncillary
import io.github.kotlinmania.rustlang.std.os.unix.net.ancillary.recvVectoredWithAncillaryFrom
import io.github.kotlinmania.rustlang.std.os.unix.net.ancillary.sendVectoredWithAncillaryTo
import io.github.kotlinmania.rustlang.std.os.unix.net.ucred.UCred
import io.github.kotlinmania.rustlang.std.os.unix.net.ucred.peerCred
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.wasip1.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

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

public val MSG_NOSIGNAL: CInt = 0x0

/**
 * A Unix stream socket.
 *
 * # Examples
 *
 * use std.os.unix.net.UnixStream;
 * use std.io.prelude::*;
 *
 * fn main() -> std.io.Result<()> (
 *     let mut stream = UnixStream.connect("/path/to/my/socket")?;
 *     stream.writeAll(b"hello world")?;
 *     let mut response = String.new();
 *     stream.readToString(response)?;
 *     println("(response)");
 *     Ok(())
 * )
 * ```kotlin
 *
 * On platforms that support it, we pass the close - on - exec flag to atomically create the socket and
 * set it as CLOEXEC. On Linux, this was added in 2.6.27. See [socket] for more information.
 *
 * [socket]: https://www.man7.org/linux/man-pages/man2/socket.2.html#:~:text=SOCK_CLOEXEC
 *
 * Writes to the underlying socket in `SOCK_STREAM` mode are made with `MSG_NOSIGNAL` flag.
 * This suppresses the emission of the  `SIGPIPE` signal when writing to disconnected socket.
 * In some cases getting a `SIGPIPE` would trigger process termination.
 * ```
 */
public open class UnixStream(
    public var value: Socket,
) : Read, Write, AsRawFd, FromRawFd<UnixStream>, IntoRawFd, AsFd, AsInner<Socket> {
    public var field0: Socket
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Error> {
        val builder = DebugStruct(fmt, "UnixStream")
        builder.field("fd", this.value.asInner())
        when (val matchVal = this.localAddr()) {
            is Ok -> {
                val addr = matchVal.value
                builder.field("local", addr)
            }
            else -> {}
        }
        when (val matchVal1 = this.peerAddr()) {
            is Ok -> {
                val addr1 = matchVal1.value
                builder.field("peer", addr1)
            }
            else -> {}
        }
        return builder.finish()
    }

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned `UnixStream` is a reference to the same stream that this
     * object references. Both handles will read and write the same stream of
     * data, and options set on one stream will be propagated to the other
     * stream.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let sockCopy = socket.tryClone().expect("`UnixStream.tryClone` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun tryClone(): Result<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.duplicate().map({ UnixStream(it) })

    /**
     * Returns the socket address of the local half of this connection.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let addr = socket.localAddr().expect("`UnixStream.localAddr` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun localAddr(): Result<SocketAddr, io.github.kotlinmania.rustlang.std.io.error.Error> = SocketAddr.new({ addr, len -> getsockname(this.asRawFd(), addr, len) })

    /**
     * Returns the socket address of the remote half of this connection.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let addr = socket.peerAddr().expect("`UnixStream.peerAddr` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peerAddr(): Result<SocketAddr, io.github.kotlinmania.rustlang.std.io.error.Error> = SocketAddr.new({ addr, len -> getpeername(this.asRawFd(), addr, len) })

    /**
     * Gets the peer credentials for this Unix domain socket.
     *
     * # Examples
     *
     * @file:feature(peerCredentialsUnixSocket)
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let peerCred = socket.peerCred().expect("`UnixStream.peerCred` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peerCred(): Result<UCred, io.github.kotlinmania.rustlang.std.io.error.Error> = peerCred(this)

    /**
     * Sets the read timeout for the socket.
     *
     * If the provided value is [None], then [read] calls will block
     * indefinitely. An [Err] is returned if the zero [Duration] is passed to this
     * method.
     *
     * [read]: io.Read.read
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setReadTimeout(Some(Duration.new(1, 0))).expect("`UnixStream.setReadTimeout` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * import std.io;
     * import std.os.unix.net.UnixStream;
     * import std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     * val socket = UnixStream.connect("/tmp/sock");
     * val result = socket.setReadTimeout(Some(Duration.new(0, 0)));
     * val err = result.unwrapErr();
     * assertEq(err.kind(), io.ErrorKind.InvalidInput);
     * Ok(())
     * )
     * ```
     */
    public open fun setReadTimeout(timeout: Duration?): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.setTimeout(timeout, SO_RCVTIMEO)

    /**
     * Sets the write timeout for the socket.
     *
     * If the provided value is [None], then [write] calls will block
     * indefinitely. An [Err] is returned if the zero [Duration] is
     * passed to this method.
     *
     * [read]: io.Read.read
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setWriteTimeout(Some(Duration.new(1, 0)))
     *         .expect("`UnixStream.connect` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * import std.io;
     * import std.os.unix.net.UnixStream;
     * import std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     * val socket = UnixStream.connect("/tmp/sock");
     * val result = socket.setWriteTimeout(Some(Duration.new(0, 0)));
     * val err = result.unwrapErr();
     * assertEq(err.kind(), io.ErrorKind.InvalidInput);
     * Ok(())
     * )
     * ```
     */
    public open fun setWriteTimeout(timeout: Duration?): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.setTimeout(timeout, SO_SNDTIMEO)

    /**
     * Returns the read timeout of this socket.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setReadTimeout(Some(Duration.new(1, 0))).expect("`UnixStream.setReadTimeout` should not fail");
     *     assertEq(socket.readTimeout()?, Some(Duration.new(1, 0)));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun readTimeout(): Result<Duration?, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.timeout(SO_RCVTIMEO)

    /**
     * Returns the write timeout of this socket.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setWriteTimeout(Some(Duration.new(1, 0)))
     *         .expect("`UnixStream.setWriteTimeout` should not fail");
     *     assertEq(socket.writeTimeout()?, Some(Duration.new(1, 0)));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun writeTimeout(): Result<Duration?, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.timeout(SO_SNDTIMEO)

    /**
     * Moves the socket into or out of nonblocking mode.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setNonblocking(true).expect("`UnixStream.setNonblocking` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.setNonblocking(nonblocking)

    /**
     * Set the id of the socket for network filtering purpose
     *
     * @file:feature(unixSetMark)
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let sock = UnixStream.connect("/tmp/sock")?;
     *     sock.setMark(32)?;
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun setMark(mark: UInt): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.setMark(mark)

    /**
     * Returns the value of the `SO_ERROR` option.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     if let Ok(Some(err)) = socket.takeError() (
     *         println("Got error: (err:?)");
     *     )
     *     Ok(())
     * )
     * ```kotlin
     *
     * On Redox this always returns `None`.
     * ```
     */
    public open fun takeError(): Result<io.github.kotlinmania.rustlang.std.io.Error?, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.takeError()

    /**
     * Shuts down the read, write, or both halves of this connection.
     *
     * This function will cause all pending and future I/O calls on the
     * specified portions to immediately return with an appropriate value
     * (see the documentation of [Shutdown]).
     *
     * # Examples
     *
     * use std.os.unix.net.UnixStream;
     * use std.net.Shutdown;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.shutdown(Shutdown.Both).expect("`UnixStream.shutdown` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun shutdown(how: Shutdown): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.shutdown(how)

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
     * use std.os.unix.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let mut buf = [0; 10];
     *     let len = socket.peek(buf).expect("`UnixStream.peek` should not fail");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peek(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.peek(buf)

    /**
     * Receives data and ancillary data from socket.
     *
     * On success, returns the number of bytes read.
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::(UnixStream, SocketAncillary, AncillaryData);
     * use std.io.IoSliceMut;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
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
     *     let size = socket.recvVectoredWithAncillary(bufs, ancillary)?;
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
    public open fun recvVectoredWithAncillary(bufs: MutableList<IoSliceMut>, ancillary: SocketAncillary): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> {
        val (count, _, _) = when (val tryResult = recvVectoredWithAncillaryFrom(this.value, bufs, ancillary)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        return Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(count)
    }

    /**
     * Sends data and ancillary data on the socket.
     *
     * On success, returns the number of bytes written.
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::(UnixStream, SocketAncillary);
     * use std.io.IoSlice;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
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
     *     socket.sendVectoredWithAncillary(bufs, ancillary)
     *         .expect("sendVectoredWithAncillary function failed");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun sendVectoredWithAncillary(bufs: List<IoSlice>, ancillary: SocketAncillary): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = sendVectoredWithAncillaryTo(this.value, null, bufs, ancillary)

    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Read.read(this, buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Read.readBuf(this, buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Read.readVectored(this, bufs)

    public open fun isReadVectored(): Boolean = Read.isReadVectored(this)

    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Write.write(this, buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Write.writeVectored(this, bufs)

    public open fun isWriteVectored(): Boolean = Write.isWriteVectored(this)

    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Write.flush(this)

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asInner(): Socket = this.value

    public companion object {
        /**
         * Connects to the socket named by `path`.
         *
         * # Examples
         *
         * use std.os.unix.net.UnixStream;
         *
         * let socket = match UnixStream.connect("/tmp/sock") (
         *     Ok(sock) => sock,
         *     Err(e) => (
         *         println("Couldn't connect: (e:?)");
         *         return
         *     )
         * );
         * ```kotlin
         */
        public fun connect(path: Path): Result<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error> {
            return run {
                val inner = when (val tryResult = Socket.new(AF_UNIX, SOCK_STREAM)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
                val (addr, len) = when (val tryResult = SockaddrUn(path)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
                when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.connect(inner.asRawFd(), constPtrFromRustCast((addrOf(addr))), len))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
                Result.Ok<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(UnixStream(inner))
            }
        }

        /**
         * Connects to the socket specified by [address].
         *
         * # Examples
         *
         * use std.os.unix.net::(UnixListener, UnixStream);
         *
         * fn main() -> std.io.Result<()> (
         *     let listener = UnixListener.bind("/path/to/the/socket")?;
         *     let addr = listener.localAddr()?;
         *
         *     let sock = match UnixStream.connectAddr(&addr) (
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
        public fun connectAddr(socketAddr: SocketAddr): Result<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error> {
            return run {
                val inner = when (val tryResult = Socket.new(AF_UNIX, SOCK_STREAM)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
                when (val tryResult = cvt(connect(inner.asRawFd(), (addrOf(socketAddr.addr)), socketAddr.len))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
                Result.Ok<UnixStream, io.github.kotlinmania.rustlang.std.io.error.Error>(UnixStream(inner))
            }
        }

        /**
         * Creates an unnamed pair of connected sockets.
         *
         * Returns two `UnixStream`s which are connected to each other.
         *
         * # Examples
         *
         * use std.os.unix.net.UnixStream;
         *
         * let (sock1, sock2) = match UnixStream.pair() (
         *     Ok((sock1, sock2)) => (sock1, sock2),
         *     Err(e) => (
         *         println("Couldn't create a pair of sockets: (e:?)");
         *         return
         *     )
         * );
         * ```kotlin
         */
        public fun pair(): Result<Pair<UnixStream, UnixStream>, io.github.kotlinmania.rustlang.std.io.error.Error> {
            val (i1, i2) = when (val tryResult = Socket.newPair(AF_UNIX, SOCK_STREAM)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Pair<UnixStream, UnixStream>, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
            return Result.Ok<Pair<UnixStream, UnixStream>, io.github.kotlinmania.rustlang.std.io.error.Error>(Pair(UnixStream(i1), UnixStream(i2)))
        }

        public fun fromRawFd(fd: RawFd): UnixStream = UnixStream(Socket.fromInner(FromInner.fromInner(OwnedFd.fromRawFd(fd))))

        public fun from(owned: OwnedFd): UnixStream = UnixStream.fromRawFd(owned.intoRawFd())
    }
}

/**
 * Takes ownership of a [UnixStream]s socket file descriptor.
 */
public fun fromOwnedFd(unixStream: UnixStream): OwnedFd = OwnedFd.fromRawFd(unixStream.intoRawFd())
