// port-lint: source os/windows/net/listener.rs
package io.github.kotlinmania.rustlang.std.os.windows.net

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.windows.io.AsRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.FromRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.IntoRawSocket
import io.github.kotlinmania.rustlang.std.os.windows.io.RawSocket
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.net.connection.wasip1.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtNz
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.AF_UNIX
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SOCK_STREAM
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SockaddrUn
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.bind
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getsockname
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.listen
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
 * A structure representing a Unix domain socket server.
 *
 * Under Windows, it will only work starting from Windows 10 17063.
 *
 * # Examples
 *
 * @file:feature(windowsUnixDomainSockets)
 * use std.thread;
 * use std.os.windows.net::{UnixStream, UnixListener};
 *
 * fn handleClient(stream: UnixStream) {
 *     // ...
 * }
 *
 * fn main() -> std.io.Result<()> {
 *     let listener = UnixListener.bind("/path/to/the/socket")?;
 *
 *     // accept connections and process them, spawning a new thread for each one
 *     for stream in listener.incoming() {
 *         match stream {
 *             Ok(stream) => {
 *                 // connection succeeded
 *                 thread.spawn(|| handleClient(stream));
 *             }
 *             Err(err) => {
 *                 // connection failed
 *                 break;
 *             }
 *         }
 *     }
 *     Ok(())
 * }
 * ```kotlin
 */
public open class UnixListener(
    public var value: Socket,
) : AsRawSocket, FromRawSocket<UnixListener>, IntoRawSocket {
    public var field0: Socket
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(fmt: StringBuilder) {
        val builder = DebugStruct(fmt, "UnixListener")
        builder.field("sock", this.value.asInner())
        when (val matchVal = this.localAddr()) {
            is Ok -> {
                val addr = matchVal.value
                builder.field("local", addr)
            }
            else -> {}
        }
        builder.finish()
    }

    /**
     * Accepts a new incoming connection to this listener.
     *
     * This function will block the calling thread until a new Unix connection
     * is established. When established, the corresponding [UnixStream] and
     * the remote peer's address will be returned.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *
     *     match listener.accept() {
     *         Ok((socket, addr)) => println("Got a client: {addr:?}"),
     *         Err(e) => println("accept function failed: {e:?}"),
     *     }
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun accept(): Result<Pair<UnixStream, SocketAddr>, Error> {
        val localStorageL146C8 = AddressableLocal(SockaddrUn.default(), ObjectSlotLayout<Any?>())
        val storage by localStorageL146C8
        val localStorageL147C8 = AddressableLocal(null, ObjectSlotLayout<Any?>())
        val len by localStorageL147C8
        val inner = when (val tryResult = this.value.accept(localStorageL146C8.asMutPtr(), localStorageL147C8.asMutPtr())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val addr = when (val tryResult = SocketAddr.fromParts(storage, len)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(UnixStream(inner), addr))
    }

    /**
     * Returns the local socket address of this listener.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *     let addr = listener.localAddr().expect("Couldn't get local address");
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun localAddr(): Result<SocketAddr, Error> = SocketAddr.new({ addr, len -> getsockname(this.value.asRaw(), addr, len) })

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned `UnixListener` is a reference to the same socket that this
     * object references. Both handles can be used to accept incoming
     * connections and options set on one listener will affect the other.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *     let listenerCopy = listener.tryClone().expect("tryClone failed");
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun tryClone(): Result<UnixListener, Error> = this.value.duplicate().map({ UnixListener(it) })

    /**
     * Moves the socket into or out of nonblocking mode.
     *
     * This will result in the `accept` operation becoming nonblocking,
     * i.e., immediately returning from their calls. If the IO operation is
     * successful, `Ok` is returned and no further action is required. If the
     * IO operation could not be completed and needs to be retried, an error
     * with kind [io.ErrorKind.WouldBlock] is returned.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *     listener.setNonblocking(true).expect("Couldn't set non blocking");
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> = this.value.setNonblocking(nonblocking)

    /**
     * Returns the value of the `SO_ERROR` option.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/tmp/sock")?;
     *
     *     if let Ok(Some(err)) = listener.takeError() {
     *         println("Got error: {err:?}");
     *     }
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun takeError(): Result<io.github.kotlinmania.rustlang.std.io.Error?, Error> = this.value.takeError()

    /**
     * Returns an iterator over incoming connections.
     *
     * The iterator will never return [None] and will also not yield the
     * peer's [SocketAddr] structure.
     *
     * # Examples
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.thread;
     * use std.os.windows.net::{UnixStream, UnixListener};
     *
     * fn handleClient(stream: UnixStream) {
     *     // ...
     * }
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *
     *     for stream in listener.incoming() {
     *         match stream {
     *             Ok(stream) => {
     *                 thread.spawn(|| handleClient(stream));
     *             }
     *             Err(err) => {
     *                 break;
     *             }
     *         }
     *     }
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun incoming(): Incoming = Incoming(listener = this)

    public override fun asRawSocket(): RawSocket = this.value.asRawSocket()

    public override fun intoRawSocket(): RawSocket = this.value.intoRawSocket()

    public open fun intoIter(): Incoming = this.incoming()

    public companion object {
        public fun typeLayout(): TypeLayout<UnixListener> =
            object : TypeLayout<UnixListener> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): UnixListener =
                    UnixListener(
                        (PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: UnixListener) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnixListener =
                    UnixListener(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnixListener) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Creates a new `UnixListener` bound to the specified socket.
         *
         * # Examples
         *
         * @file:feature(windowsUnixDomainSockets)
         * use std.os.windows.net.UnixListener;
         *
         * let listener = match UnixListener.bind("/path/to/the/socket") {
         *     Ok(sock) => sock,
         *     Err(e) => {
         *         println("Couldn't connect: {e:?}");
         *         return
         *     }
         * };
         * ```kotlin
         */
        public fun bind(path: Path): Result<UnixListener, Error> {
            val socketAddr = when (val tryResult = SocketAddr.fromPathname(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return UnixListener.bindAddr(socketAddr)
        }

        /**
         * Creates a new `UnixListener` bound to the specified [socket.address].
         *
         * # Examples
         *
         * @file:feature(windowsUnixDomainSockets)
         * use std.os.windows.net::{UnixListener};
         *
         * fn main() -> std.io.Result<()> {
         *     let listener1 = UnixListener.bind("path/to/socket")?;
         *     let addr = listener1.localAddr()?;
         *
         *     let listener2 = match UnixListener.bindAddr(&addr) {
         *         Ok(sock) => sock,
         *         Err(err) => {
         *             println("Couldn't bind: {err:?}");
         *             return Err(err);
         *         }
         *     };
         *     Ok(())
         * }
         * ```kotlin
         */
        public fun bindAddr(socketAddr: SocketAddr): Result<UnixListener, Error> {
            startup()
            val inner = when (val tryResult = Socket.new(AF_UNIX.toInt(), SOCK_STREAM)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            run {
                when (val tryResult = cvtNz(bind(inner.asRaw(), ((addrOf(socketAddr.addr) as Any?) as Result<UnixListener, Error>), socketAddr.len.toInt()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = cvtNz(listen(inner.asRaw(), 128))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            return Result.Ok(UnixListener(inner))
        }

        public fun fromRawSocket(sock: RawSocket): UnixListener = UnixListener(Socket.fromRawSocket(sock))
    }
}

/**
 * An iterator over incoming connections to a [UnixListener].
 *
 * It will never return [None].
 *
 * # Examples
 *
 * @file:feature(windowsUnixDomainSockets)
 * use std.thread;
 * use std.os.windows.net::{UnixStream, UnixListener};
 *
 * fn handleClient(stream: UnixStream) {
 *     // ...
 * }
 *
 * fn main() -> std.io.Result<()> {
 *     let listener = UnixListener.bind("/path/to/the/socket")?;
 *
 *     for stream in listener.incoming() {
 *         match stream {
 *             Ok(stream) => {
 *                 thread.spawn(|| handleClient(stream));
 *             }
 *             Err(err) => {
 *                 break;
 *             }
 *         }
 *     }
 *     Ok(())
 * }
 * ```kotlin
 */
public open class Incoming(
    public var listener: UnixListener,
) : Iterable<Result<UnixStream, Error>> {
    override fun iterator(): Iterator<Result<UnixStream, Error>> = object : AbstractIterator<Result<UnixStream, Error>>() {
        override fun computeNext() {
            when (val v = this@Incoming.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Result<UnixStream, Error>? = this.listener.accept().map({ s -> s.first })

    public open fun sizeHint(): Pair<Int, Int?> = Pair(Int.MAX_VALUE, null)
}
