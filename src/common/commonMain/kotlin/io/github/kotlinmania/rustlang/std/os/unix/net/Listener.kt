// port-lint: source os/unix/net/listener.rs
package io.github.kotlinmania.rustlang.std.os.unix.net

import io.github.kotlinmania.libc.bind
import io.github.kotlinmania.libc.fuchsia.AF_UNIX
import io.github.kotlinmania.libc.fuchsia.SOCK_STREAM
import io.github.kotlinmania.libc.getsockname
import io.github.kotlinmania.libc.listen
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.motor.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SockaddrUn

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
 * # Examples
 *
 * use std.thread;
 * use std.os.unix.net::{UnixStream, UnixListener};
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
) : AsRawFd, FromRawFd<UnixListener, Any?, Any>, IntoRawFd, AsFd {
    public var field0: Socket
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(fmt: StringBuilder) {
        val builder = DebugStruct(fmt, "UnixListener")
        builder.field("fd", this.value.asInner())
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
     * use std.os.unix.net.UnixListener;
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
    public open fun accept(): Result<Pair<UnixStream, SocketAddr>> {
        val localStorageL184C8 = AddressableLocal(null, ObjectSlotLayout<io.github.kotlinmania.libc.fuchsia.SockaddrUn>())
        val storage by localStorageL184C8
        val len = sizeOfVal(storage)
        val sock = when (val tryResult = this.value.accept((localStorageL184C8.asMutPtr()), len)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val addr = when (val tryResult = SocketAddr.fromParts(storage, len)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Pair(UnixStream(sock), addr))
    }

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned `UnixListener` is a reference to the same socket that this
     * object references. Both handles can be used to accept incoming
     * connections and options set on one listener will affect the other.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *     let listenerCopy = listener.tryClone().expect("`UnixListener.tryClone` should not fail");
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun tryClone(): Result<UnixListener> = this.value.duplicate().map({ UnixListener(it) })

    /**
     * Returns the local socket address of this listener.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *     let addr = listener.localAddr().expect("`UnixListener.localAddr` should not fail");
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun localAddr(): Result<SocketAddr> = SocketAddr.new({ addr, len -> getsockname(this.asRawFd(), addr, len) })

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
     * use std.os.unix.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> {
     *     let listener = UnixListener.bind("/path/to/the/socket")?;
     *     listener.setNonblocking(true).expect("`UnixListener.setNonblocking` should not fail");
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = this.value.setNonblocking(nonblocking)

    /**
     * Returns the value of the `SO_ERROR` option.
     *
     * # Examples
     *
     * use std.os.unix.net.UnixListener;
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
     *
     * On Redox this always returns `None`.
     * ```
     */
    public open fun takeError(): Result<Error?> = this.value.takeError()

    /**
     * Returns an iterator over incoming connections.
     *
     * The iterator will never return [None] and will also not yield the
     * peer's [SocketAddr] structure.
     *
     * # Examples
     *
     * use std.thread;
     * use std.os.unix.net::{UnixStream, UnixListener};
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

    public override fun asRawFd(): RawFd = this.value.asInner().asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoInner().intoInner().intoRawFd()

    public override fun asFd(): BorrowedFd = this.value.asInner().asFd()

    public open fun intoIter(): Incoming = this.incoming()

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<UnixListener> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<UnixListener> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): UnixListener =
                    UnixListener(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: UnixListener) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnixListener =
                    UnixListener(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnixListener) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Creates a new `UnixListener` bound to the specified socket.
         *
         * # Examples
         *
         * use std.os.unix.net.UnixListener;
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
        public fun bind(path: Path): Result<UnixListener> {
            return run {
                val inner = when (val tryResult = Socket.new(AF_UNIX, SOCK_STREAM)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                val (addr, len) = when (val tryResult = SockaddrUn(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = cvt(io.github.kotlinmania.libc.bind(inner.asInner().asRawFd(), constPtrFromRustCast((addrOf(addr))), ((len as Any?) as Result<UnixListener>)))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = cvt(listen(inner.asInner().asRawFd(), backlog))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                Result.Ok(UnixListener(inner))
            }
        }

        /**
         * Creates a new `UnixListener` bound to the specified [socket.address].
         *
         * # Examples
         *
         * use std.os.unix.net::{SocketAddr, UnixListener};
         *
         * fn main() -> std.io.Result<()> {
         *     let addr = SocketAddr.fromPathname("path/to/socket")?;
         *
         *     let listener = match UnixListener.bindAddr(&addr) {
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
        public fun bindAddr(socketAddr: SocketAddr): Result<UnixListener> {
            return run {
                val inner = when (val tryResult = Socket.new(AF_UNIX, SOCK_STREAM)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = cvt(bind(inner.asRawFd(), (addrOf(socketAddr.addr)), ((socketAddr.len as Any?) as Result<UnixListener>)))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                when (val tryResult = cvt(listen(inner.asRawFd(), backlog))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                Result.Ok(UnixListener(inner))
            }
        }

        public fun fromRawFd(fd: RawFd): UnixListener = UnixListener(Socket.fromInner(FromInner.fromInner(OwnedFd.fromRawFd(fd))))

        public fun from(fd: OwnedFd): UnixListener = UnixListener(Socket.fromInner(FromInner.fromInner(fd)))
    }
}

/**
 * Takes ownership of a [UnixListener]s socket file descriptor.
 */
public fun fromOwnedFd(listener: UnixListener): OwnedFd = listener.value.intoInner().intoInner()

/**
 * An iterator over incoming connections to a [UnixListener].
 *
 * It will never return [None].
 *
 * # Examples
 *
 * use std.thread;
 * use std.os.unix.net::{UnixStream, UnixListener};
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
) : Iterable<Result<UnixStream>> {
    override fun iterator(): kotlin.collections.Iterator<Result<UnixStream>> = object : AbstractIterator<Result<UnixStream>>() {
        override fun computeNext() {
            when (val v = this@Incoming.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Result<UnixStream>? = this.listener.accept().map({ s -> s.first })

    public open fun sizeHint(): Pair<Int, Int?> = Pair(Int.MAX_VALUE, null)
}
