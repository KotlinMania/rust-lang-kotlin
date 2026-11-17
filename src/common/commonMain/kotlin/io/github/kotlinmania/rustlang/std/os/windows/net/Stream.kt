// port-lint: source os/windows/net/stream.rs
package io.github.kotlinmania.rustlang.std.os.windows.net

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.windows.io.AsRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.AsSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.FromRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.IntoRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.RawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.asRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.asSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.fromRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.intoRawSocket
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.windows.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.AF_UNIX
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SOCK_STREAM
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SO_RCVTIMEO
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SO_SNDTIMEO
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.connect
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getpeername
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getsockname
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvtNz
import io.github.kotlinmania.rustlang.std.sys.pal.windows.winsock.startup

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

/**
 * A Unix stream socket.
 *
 * Under Windows, it will only work starting from Windows 10 17063.
 *
 * # Examples
 *
 * @file:feature(windowsUnixDomainSockets)
 * use std.os.windows.net.UnixStream;
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
 */
public open class UnixStream(
    public var value: Socket,
) : Read, Write, AsSocket, AsRawSocket, FromRawSocket<UnixStream>, IntoRawSocket {
    public var field0: Socket
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Error> {
        val builder = DebugStruct(fmt, "UnixStream")
        builder.field("sock", this.value.asInner())
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
        return builder.finish()
    }

    /**
     * Returns the socket address of the local half of this connection.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let addr = socket.localAddr().expect("Couldn't get local address");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun localAddr(): Result<SocketAddr, io.github.kotlinmania.rustlang.core.io.error.Error> = SocketAddr.new({ addr, len -> getsockname(this.value.asRaw(), addr, len) })

    /**
     * Returns the socket address of the remote half of this connection.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let addr = socket.peerAddr().expect("Couldn't get peer address");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun peerAddr(): Result<SocketAddr, io.github.kotlinmania.rustlang.core.io.error.Error> = SocketAddr.new({ addr, len -> getpeername(this.value.asRaw(), addr, len) })

    /**
     * Returns the read timeout of this socket.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setReadTimeout(Some(Duration.new(1, 0))).expect("Couldn't set read timeout");
     *     assertEq(socket.readTimeout()?, Some(Duration.new(1, 0)));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun readTimeout(): Result<Duration?, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.timeout(SO_RCVTIMEO)

    /**
     * Moves the socket into or out of nonblocking mode.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setNonblocking(true).expect("Couldn't set nonblocking");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setNonblocking(nonblocking)

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
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setReadTimeout(Some(Duration.new(1, 0))).expect("Couldn't set read timeout");
     *     Ok(())
     * )
     * ```kotlin
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * @file:feature(windowsUnixDomainSockets)
     * import std.io;
     * import std.os.windows.net.UnixStream;
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
    public open fun setReadTimeout(dur: Duration?): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setTimeout(dur, SO_RCVTIMEO)

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
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setWriteTimeout(Some(Duration.new(1, 0)))
     *         .expect("Couldn't set write timeout");
     *     Ok(())
     * )
     * ```kotlin
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * @file:feature(windowsUnixDomainSockets)
     * import std.io;
     * import std.os.windows.net.UnixStream;
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
    public open fun setWriteTimeout(dur: Duration?): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.setTimeout(dur, SO_SNDTIMEO)

    /**
     * Shuts down the read, write, or both halves of this connection.
     *
     * This function will cause all pending and future I/O calls on the
     * specified portions to immediately return with an appropriate value
     * (see the documentation of [Shutdown]).
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     * use std.net.Shutdown;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.shutdown(Shutdown.Both).expect("shutdown function failed");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun shutdown(how: Shutdown): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.shutdown(how)

    /**
     * Returns the value of the `SO_ERROR` option.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     if let Ok(Some(err)) = socket.takeError() (
     *         println("Got error: (err:?)");
     *     )
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun takeError(): Result<io.github.kotlinmania.rustlang.core.io.error.Error?, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.takeError()

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
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     let sockCopy = socket.tryClone().expect("Couldn't clone socket");
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun tryClone(): Result<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.duplicate().map({ UnixStream(it) })

    /**
     * Returns the write timeout of this socket.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixStream;
     * use std.time.Duration;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixStream.connect("/tmp/sock")?;
     *     socket.setWriteTimeout(Some(Duration.new(1, 0)))
     *         .expect("Couldn't set write timeout");
     *     assertEq(socket.writeTimeout()?, Some(Duration.new(1, 0)));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun writeTimeout(): Result<Duration?, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.timeout(SO_SNDTIMEO)

    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = Read.read(this, buf)

    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = Write.write(this, buf)

    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = Write.flush(this)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public override fun asSocket(): BorrowedSocket = this.value.asSocket()

    public override fun asRawSocket(): RawSocket = this.value.asRawSocket()

    public override fun intoRawSocket(): RawSocket = this.value.intoRawSocket()

    public companion object {
        public fun typeLayout(): TypeLayout<UnixStream> =
            object : TypeLayout<UnixStream> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): UnixStream =
                    UnixStream(
                        (PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: UnixStream) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnixStream =
                    UnixStream(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnixStream) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Connects to the socket named by `path`.
         *
         * # Examples
         *
         * @file:feature(windowsUnixDomainSockets)
         * use std.os.windows.net.UnixStream;
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
        public fun connect(path: Path): Result<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error> {
            val socketAddr = when (val tryResult = SocketAddr.fromPathname(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            return UnixStream.connectAddr(socketAddr)
        }

        /**
         * Connects to the socket specified by [address].
         *
         * # Examples
         *
         * @file:feature(windowsUnixDomainSockets)
         * use std.os.windows.net::(UnixListener, UnixStream);
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
        public fun connectAddr(socketAddr: SocketAddr): Result<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error> {
            startup()
            val inner = when (val tryResult = Socket.new(AF_UNIX.toInt(), SOCK_STREAM)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            run {
                when (val tryResult = cvtNz(connect(inner.asRaw(), addrOf(socketAddr.addr), socketAddr.len.toInt()))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            }
            return Result.Ok<UnixStream, io.github.kotlinmania.rustlang.core.io.error.Error>(UnixStream(inner))
        }

        public fun fromRawSocket(sock: RawSocket): UnixStream = UnixStream(Socket.fromRawSocket(sock))
    }
}
