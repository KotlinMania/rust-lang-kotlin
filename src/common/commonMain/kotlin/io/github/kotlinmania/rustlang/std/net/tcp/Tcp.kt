// port-lint: source net/tcp.rs
package io.github.kotlinmania.rustlang.std.net.tcp

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner

/**
 * A TCP stream between a local and a remote socket.
 *
 * After creating a `TcpStream` by either [connect]ing to a remote host or
 * [accept]ing a connection on a [TcpListener], data can be transmitted
 * by [reading] and [writing] to it.
 *
 * The connection will be closed when the value is dropped. The reading and writing
 * portions of the connection can also be shut down individually with the [shutdown]
 * method.
 *
 * The Transmission Control Protocol is specified in [IETF RFC 793].
 *
 * [accept]: TcpListener.accept
 * [connect]: TcpStream.connect
 * [IETF RFC 793]: https://tools.ietf.org/html/rfc793
 * [reading]: Read
 * [shutdown]: TcpStream.shutdown
 * [writing]: Write
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.prelude.*
 * import std.net.TcpStream
 *
 * fun main(): Result<Unit> {
 *     var stream = TcpStream.connect("127.0.0.1:34254").getOrElse { return Result.failure(it) }
 *     stream.write(listOf(1)).getOrElse { return Result.failure(it) }
 *     stream.read(List(128) { 0 }).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * // the stream is closed here
 * ```
 *
 * # Platform-specific Behavior
 *
 * On Unix, writes to the underlying socket in `SOCK_STREAM` mode are made with
 * `MSG_NOSIGNAL` flag. This suppresses the emission of the  `SIGPIPE` signal when writing
 * to disconnected socket. In some cases, getting a `SIGPIPE` would trigger process termination.
 */
public open class TcpStream(
    public var value: io.github.kotlinmania.netimp.TcpStream,
) : Read, Write, AsInner<io.github.kotlinmania.netimp.TcpStream>, FromInner<io.github.kotlinmania.netimp.TcpStream, TcpStream>, IntoInner<io.github.kotlinmania.netimp.TcpStream> {
    public var field0: io.github.kotlinmania.netimp.TcpStream
        get() = value
        set(v) {
            value = v
        }

    /**
     * Returns the socket address of the remote peer of this TCP connection.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     * import std.net.SocketAddrV4
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * check(stream.peerAddr().unwrap() == SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)))
     * ```
     */
    public open fun peerAddr(): Result<SocketAddr> = this.value.peerAddr()

    /**
     * Returns the socket address of the local half of this TCP connection.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * check(stream.localAddr().unwrap().ip() == IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)))
     * ```
     */
    public open fun localAddr(): Result<SocketAddr> = this.value.socketAddr()

    /**
     * Shuts down the read, write, or both halves of this connection.
     *
     * This function will cause all pending and future I/O on the specified
     * portions to return immediately with an appropriate value (see the
     * documentation of [Shutdown]).
     *
     * # Platform-specific behavior
     *
     * Calling this function multiple times may result in different behavior,
     * depending on the operating system. On Linux, the second call will
     * return `Ok(())`, but on macOS, it will return `ErrorKind.NotConnected`.
     * This may change in the future.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Shutdown
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.shutdown(Shutdown.Both).expect("shutdown should succeed")
     * ```
     */
    public open fun shutdown(how: Shutdown): Result<Unit> = this.value.shutdown(how)

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned `TcpStream` is a reference to the same stream that this
     * object references. Both handles will read and write the same stream of
     * data, and options set on one stream will be propagated to the other
     * stream.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * val streamClone = stream.tryClone().expect("clone should succeed")
     * ```
     */
    public open fun tryClone(): Result<TcpStream> = this.value.duplicate().map({ TcpStream(it) })

    /**
     * Sets the read timeout to the timeout specified.
     *
     * If the value specified is [None], then [read] calls will block
     * indefinitely. An [Err] is returned if the zero [Duration] is
     * passed to this method.
     *
     * # Platform-specific behavior
     *
     * Platforms may return a different error code whenever a read times out as
     * a result of setting this option. For example Unix typically returns an
     * error of the kind [WouldBlock], but Windows may return [TimedOut].
     *
     * [read]: Read.read
     * [WouldBlock]: io.ErrorKind.WouldBlock
     * [TimedOut]: io.ErrorKind.TimedOut
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setReadTimeout(null).expect("setReadTimeout should succeed")
     * ```
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * ```kotlin
     * import std.net.TcpStream
     * import std.time.Duration
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").unwrap()
     * val result = stream.setReadTimeout(Duration.new(0, 0))
     * val err = result.unwrapErr()
     * check(err.kind() == io.ErrorKind.InvalidInput)
     * ```
     */
    public open fun setReadTimeout(dur: Duration?): Result<Unit> = this.value.setReadTimeout(dur)

    /**
     * Sets the write timeout to the timeout specified.
     *
     * If the value specified is [None], then [write] calls will block
     * indefinitely. An [Err] is returned if the zero [Duration] is
     * passed to this method.
     *
     * # Platform-specific behavior
     *
     * Platforms may return a different error code whenever a write times out
     * as a result of setting this option. For example Unix typically returns
     * an error of the kind [WouldBlock], but Windows may return [TimedOut].
     *
     * [write]: Write.write
     * [WouldBlock]: io.ErrorKind.WouldBlock
     * [TimedOut]: io.ErrorKind.TimedOut
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setWriteTimeout(null).expect("setWriteTimeout should succeed")
     * ```
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * ```kotlin
     * import std.net.TcpStream
     * import std.time.Duration
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").unwrap()
     * val result = stream.setWriteTimeout(Duration.new(0, 0))
     * val err = result.unwrapErr()
     * check(err.kind() == io.ErrorKind.InvalidInput)
     * ```
     */
    public open fun setWriteTimeout(dur: Duration?): Result<Unit> = this.value.setWriteTimeout(dur)

    /**
     * Returns the read timeout of this socket.
     *
     * If the timeout is [None], then [read] calls will block indefinitely.
     *
     * # Platform-specific behavior
     *
     * Some platforms do not provide access to the current timeout.
     *
     * [read]: Read.read
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setReadTimeout(null).expect("setReadTimeout should succeed")
     * check(stream.readTimeout().unwrap() == null)
     * ```
     */
    public open fun readTimeout(): Result<Duration?> = this.value.readTimeout()

    /**
     * Returns the write timeout of this socket.
     *
     * If the timeout is [None], then [write] calls will block indefinitely.
     *
     * # Platform-specific behavior
     *
     * Some platforms do not provide access to the current timeout.
     *
     * [write]: Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setWriteTimeout(null).expect("setWriteTimeout should succeed")
     * check(stream.writeTimeout().unwrap() == null)
     * ```
     */
    public open fun writeTimeout(): Result<Duration?> = this.value.writeTimeout()

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
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8000").expect("Couldn't connect to the server...")
     * var buf = MutableList(10) { 0 }
     * val len = stream.peek(buf).expect("peek should succeed")
     * ```
     */
    public open fun peek(buf: MutableList<UByte>): Result<Int> = this.value.peek(buf)

    /**
     * Sets the value of the `SO_LINGER` option on this socket.
     *
     * This value controls how the socket is closed when data remains
     * to be sent. If `SO_LINGER` is set, the socket will remain open
     * for the specified duration as the system attempts to send pending data.
     * Otherwise, the system may close the socket immediately, or wait for a
     * default timeout.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     * import std.time.Duration
     *
     * @feature(tcpLinger)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     *     stream.setLinger(Duration.fromSecs(0)).expect("setLinger should succeed")
     * }
     * ```
     */
    public open fun setLinger(linger: Duration?): Result<Unit> = this.value.setLinger(linger)

    /**
     * Gets the value of the `SO_LINGER` option on this socket.
     *
     * For more information about this option, see [TcpStream.setLinger].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     * import std.time.Duration
     *
     * @feature(tcpLinger)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     *     stream.setLinger(Duration.fromSecs(0)).expect("setLinger should succeed")
     *     check(stream.linger().unwrap() == Duration.fromSecs(0))
     * }
     * ```
     */
    public open fun linger(): Result<Duration?> = this.value.linger()

    /**
     * Sets the value of the `SO_KEEPALIVE` option on this socket.
     *
     * If set to `true`, the operating system will periodically send keepalive
     * probes on an idle connection to verify that the remote peer is still
     * reachable. If the peer fails to respond after a system-determined number
     * of probes, the connection is considered broken and subsequent I/O calls
     * will return an error.
     *
     * This is useful for detecting dead peers on long-lived connections where
     * no application-level traffic is exchanged, such as database or SSH
     * connections.
     *
     * The timing and frequency of keepalive probes are controlled by
     * system-level settings and are not configured by this method alone.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * @feature(tcpKeepalive)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     *     stream.setKeepalive(true).expect("setKeepalive should succeed")
     * }
     * ```
     */
    public open fun setKeepalive(keepalive: Boolean): Result<Unit> = this.value.setKeepalive(keepalive)

    /**
     * Gets the value of the `SO_KEEPALIVE` option on this socket.
     *
     * For more information about this option, see [TcpStream.setKeepalive].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * @feature(tcpKeepalive)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     *     stream.setKeepalive(true).expect("setKeepalive should succeed")
     *     check(stream.keepalive().unwrapOr(false) == true)
     * }
     * ```
     */
    public open fun keepalive(): Result<Boolean> = this.value.keepalive()

    /**
     * Sets the value of the `TCP_NODELAY` option on this socket.
     *
     * If set, this option disables the Nagle algorithm. This means that
     * segments are always sent as soon as possible, even if there is only a
     * small amount of data. When not set, data is buffered until there is a
     * sufficient amount to send out, thereby avoiding the frequent sending of
     * small packets.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setNodelay(true).expect("setNodelay should succeed")
     * ```
     */
    public open fun setNodelay(nodelay: Boolean): Result<Unit> = this.value.setNodelay(nodelay)

    /**
     * Gets the value of the `TCP_NODELAY` option on this socket.
     *
     * For more information about this option, see [TcpStream.setNodelay].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setNodelay(true).expect("setNodelay should succeed")
     * check(stream.nodelay().unwrapOr(false) == true)
     * ```
     */
    public open fun nodelay(): Result<Boolean> = this.value.nodelay()

    /**
     * Sets the value for the `IP_TTL` option on this socket.
     *
     * This value sets the time-to-live field that is used in every packet sent
     * from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setTtl(100).expect("setTtl should succeed")
     * ```
     */
    public open fun setTtl(ttl: UInt): Result<Unit> = this.value.setTtl(ttl)

    /**
     * Gets the value of the `IP_TTL` option for this socket.
     *
     * For more information about this option, see [TcpStream.setTtl].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.setTtl(100).expect("setTtl should succeed")
     * check(stream.ttl().unwrapOr(0) == 100)
     * ```
     */
    public open fun ttl(): Result<UInt> = this.value.ttl()

    /**
     * Sets the value for the `IPV6_UNICAST_HOPS` option on this socket.
     *
     * This value sets the unicast hop limit field that is used in every packet
     * sent from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("[::1]:12345").expect("Couldn't connect to the server...")
     *     stream.setHopLimitV6(88).expect("setHopLimitV6 call failed")
     * }
     * ```
     */
    public open fun setHopLimitV6(limit: UByte): Result<Unit> = this.value.setHopLimitV6(limit)

    /**
     * Gets the value of the `IPV6_UNICAST_HOPS` option on this socket.
     *
     * For more information about this option, see [TcpStream.setHopLimitV6].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("[::1]:12345").expect("Couldn't connect to the server...")
     *     stream.setHopLimitV6(88).expect("setHopLimitV6 call failed")
     *     check(stream.hopLimitV6().unwrap() == 88)
     * }
     * ```
     */
    public open fun hopLimitV6(): Result<UByte> = this.value.hopLimitV6()

    /**
     * Gets the value of the `SO_ERROR` option on this socket.
     *
     * This will retrieve the stored error in the underlying socket, clearing
     * the field in the process. This can be useful for checking errors between
     * calls.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     *
     * val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     * stream.takeError().expect("No error was expected...")
     * ```
     */
    public open fun takeError(): Result<Error?> = this.value.takeError()

    /**
     * Moves this TCP stream into or out of nonblocking mode.
     *
     * This will result in `read`, `write`, `recv` and `send` system operations
     * becoming nonblocking, i.e., immediately returning from their calls.
     * If the IO operation is successful, `Ok` is returned and no further
     * action is required. If the IO operation could not be completed and needs
     * to be retried, an error with kind [io.ErrorKind.WouldBlock] is
     * returned.
     *
     * On most Unix platforms, calling this method corresponds to calling `ioctl`
     * `FIONBIO`. On Windows, calling this method corresponds to calling
     * `ioctlsocket` `FIONBIO`.
     *
     * # Examples
     *
     * Reading bytes from a TCP stream in non-blocking mode:
     *
     * ```kotlin
     * import std.io.Read
     * import std.net.TcpStream
     *
     * var stream = TcpStream.connect("127.0.0.1:7878").expect("Couldn't connect to the server...")
     * stream.setNonblocking(true).expect("setNonblocking should succeed")
     * var buf = mutableListOf()
     * while (true) {
     *     run {
     *         val matchVal = stream.readToEnd(buf)
     *         when {
     *             matchVal is Ok -> { break }
     *             matchVal is Err && (matchVal as Err).value.kind() == io.ErrorKind.WouldBlock -> {
     *                 val (e) = matchVal
     *                 // wait until network socket is ready, typically implemented
     *                 // via platform-specific APIs such as epoll or IOCP
     *                 waitForFd()
     *             }
     *             else -> {
     *                 val (e1) = matchVal
     *                 error("encountered IO error: $e")
     *             }
     *         }
     *     }
     * }
     * println("bytes: $buf")
     * ```
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = this.value.setNonblocking(nonblocking)

    // In addition to the `implementation`s here, `TcpStream` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsSocket`/`From<OwnedSocket>`/`Into<OwnedSocket>` and
    // `AsRawSocket`/`IntoRawSocket`/`FromRawSocket` on Windows.
    public open fun read(buf: MutableList<UByte>): Result<Int> = this.value.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.value.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun flush(): Result<Unit> = Result.Ok(Unit)

    public override fun asInner(): io.github.kotlinmania.netimp.TcpStream = this.value

    public override fun intoInner(): io.github.kotlinmania.netimp.TcpStream = this.value

    public open fun fmt(f: StringBuilder) {
        this.value.fmt(f)
    }

    public companion object {
        /**
         * Opens a TCP connection to a remote host.
         *
         * `addr` is an address of the remote host. Anything which implements
         * [ToSocketAddrs] trait can be supplied for the address; see this trait
         * documentation for concrete examples.
         *
         * If `addr` yields multiple addresses, `connect` will be attempted with
         * each of the addresses until a connection is successful. If none of
         * the addresses result in a successful connection, the error returned from
         * the last connection attempt (the last address) is returned.
         *
         * # Examples
         *
         * Open a TCP connection to `127.0.0.1: 8080`:
         *
         * ```kotlin
         * import std.net.TcpStream
         *
         * when (val matchVal = TcpStream.connect("127.0.0.1:8080")) {
         *     is Ok -> {
         *         val (stream) = matchVal
         *         println("Connected to the server!")
         *     }
         *     else -> {
         *         println("Couldn't connect to server...")
         *     }
         * }
         * ```
         *
         * Open a TCP connection to `127.0.0.1: 8080`. If the connection fails, open
         * a TCP connection to `127.0.0.1: 8081`:
         *
         * ```kotlin
         * import std.net.SocketAddr
         * import std.net.TcpStream
         *
         * val addrs = listOf(SocketAddr.from(Pair(listOf(127, 0, 0, 1), 8080)), SocketAddr.from(Pair(listOf(127, 0, 0, 1), 8081)))
         * when (val matchVal = TcpStream.connect(addrs)) {
         *     is Ok -> {
         *         val (stream) = matchVal
         *         println("Connected to the server!")
         *     }
         *     else -> {
         *         println("Couldn't connect to server...")
         *     }
         * }
         * ```
         */
        public fun <Iter, A : ToSocketAddrs<Any?>> connect(addr: A): Result<TcpStream> = io.github.kotlinmania.netimp.TcpStream.connect(addr).map({ TcpStream(it) })

        /**
         * Opens a TCP connection to a remote host with a timeout.
         *
         * Unlike `connect`, `connectTimeout` takes a single [SocketAddr] since
         * timeout must be applied to individual addresses.
         *
         * It is an error to pass a zero `Duration` to this function.
         *
         * Unlike other methods on `TcpStream`, this does not correspond to a
         * single system call. It instead calls `connect` in nonblocking mode and
         * then uses an OS-specific mechanism to await the completion of the
         * connection request.
         */
        public fun connectTimeout(addr: SocketAddr, timeout: Duration): Result<TcpStream> = io.github.kotlinmania.netimp.TcpStream.connectTimeout(addr, timeout).map({ TcpStream(it) })

        public fun fromInner(inner: io.github.kotlinmania.netimp.TcpStream): TcpStream = TcpStream(inner)
    }
}

/**
 * A TCP socket server, listening for connections.
 *
 * After creating a `TcpListener` by [bind]ing it to a socket address, it listens
 * for incoming TCP connections. These can be accepted by calling [accept] or by
 * iterating over the [Incoming] iterator returned by [`incoming`][TcpListener.incoming].
 *
 * The socket will be closed when the value is dropped.
 *
 * The Transmission Control Protocol is specified in [IETF RFC 793].
 *
 * [accept]: TcpListener.accept
 * [bind]: TcpListener.bind
 * [IETF RFC 793]: https://tools.ietf.org/html/rfc793
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.TcpListener
 * import std.net.TcpStream
 *
 * fun handleClient(stream: TcpStream) {
 *     // ...
 * }
 * fun main(): Result<Unit> {
 *     val listener = TcpListener.bind("127.0.0.1:80").getOrElse { return Result.failure(it) }
 *     // accept connections and process them serially
 *     for (stream in listener.incoming()) {
 *         handleClient(stream.getOrElse { return Result.failure(it) })
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class TcpListener(
    public var value: io.github.kotlinmania.netimp.TcpListener,
) : AsInner<io.github.kotlinmania.netimp.TcpListener>, FromInner<io.github.kotlinmania.netimp.TcpListener, TcpListener>, IntoInner<io.github.kotlinmania.netimp.TcpListener> {
    public var field0: io.github.kotlinmania.netimp.TcpListener
        get() = value
        set(v) {
            value = v
        }

    /**
     * Returns the local socket address of this listener.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     * import std.net.SocketAddrV4
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:8080").unwrap()
     * check(listener.localAddr().unwrap() == SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)))
     * ```
     */
    public open fun localAddr(): Result<SocketAddr> = this.value.socketAddr()

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned [TcpListener] is a reference to the same socket that this
     * object references. Both handles can be used to accept incoming
     * connections and options set on one listener will affect the other.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:8080").unwrap()
     * val listenerClone = listener.tryClone().unwrap()
     * ```
     */
    public open fun tryClone(): Result<TcpListener> = this.value.duplicate().map({ TcpListener(it) })

    /**
     * Accept a new incoming connection from this listener.
     *
     * This function will block the calling thread until a new TCP connection
     * is established. When established, the corresponding [TcpStream] and the
     * remote peer's address will be returned.
     *
     * # Errors
     *
     * Some errors this function returns do not indicate a problem with the
     * listener itself, and a program serving a long-lived listener will
     * usually want to handle them and keep accepting connections rather than
     * treat them as fatal. These include, but are not limited to:
     *
     * - An error specific to a single incoming connection that failed before
     *   it could be accepted, such as one aborted by the peer
     *   ([ConnectionAborted]). A later call may succeed immediately.
     * - An error from reaching the per-process or system-wide open file
     *   descriptor limit. The call can be retried once other file descriptors
     *   have been closed, typically after a short delay.
     * - An error from failing to allocate memory while accepting a connection
     *   ([OutOfMemory]).
     *
     * Which errors can occur is platform-specific. On Unix, [Interrupted]
     * errors are retried internally rather than being returned.
     *
     * [ConnectionAborted]: io.ErrorKind.ConnectionAborted
     * [OutOfMemory]: io.ErrorKind.OutOfMemory
     * [Interrupted]: io.ErrorKind.Interrupted
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:8080").unwrap()
     * run {
     *     val matchVal = listener.accept()
     *     if (matchVal.isSuccess) {
     *         val okVal = matchVal.getOrThrow()
     *         val socket = okVal.first
     *         val addr = okVal.second
     *         println("new client: $addr")
     *     } else {
     *         val e = matchVal.exceptionOrNull()!!
     *         println("couldn't get client: $e")
     *     }
     * }
     * ```
     */
    public open fun accept(): Result<Pair<TcpStream, SocketAddr>> {
        // On wasm*-unknown-unknown, `TcpStream` is uninhabited (as it's
        // unsupported) and so the `a` variable here is technically unused.
        return this.value.accept().map({ (a, b) -> Pair(TcpStream(a), b) })
    }

    /**
     * Returns an iterator over the connections being received on this
     * listener.
     *
     * The returned iterator will never return [None] and will also not yield
     * the peer's [SocketAddr] structure. Iterating over it is equivalent to
     * calling [TcpListener.accept] in a loop.
     *
     * # Errors
     *
     * Each connection yielded by the iterator can fail for the same reasons as
     * [TcpListener.accept]; see its documentation for details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     * import std.net.TcpStream
     *
     * fun handleConnection(stream: TcpStream) {
     *     // ...
     * }
     * fun main(): Result<Unit> {
     *     val listener = TcpListener.bind("127.0.0.1:80").getOrElse { return Result.failure(it) }
     *     for (stream in listener.incoming()) {
     *         if (stream.isSuccess) {
     *             val stream1 = stream.getOrThrow()
     *             handleConnection(stream1)
     *         } else {
     *             val e = stream.exceptionOrNull()!!
     *         }
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun incoming(): Incoming = Incoming(listener = this)

    /**
     * Turn this into an iterator over the connections being received on this
     * listener.
     *
     * The returned iterator will never return [None] and will also not yield
     * the peer's [SocketAddr] structure. Iterating over it is equivalent to
     * calling [TcpListener.accept] in a loop.
     *
     * # Errors
     *
     * Each connection yielded by the iterator can fail for the same reasons as
     * [TcpListener.accept]; see its documentation for details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     * import std.net.TcpStream
     *
     * @feature(tcplistenerIntoIncoming)
     * public fun doctestEntry() {
     *     fun listenOn(port: UShort): Any {
     *         val listener = TcpListener.bind(Pair("127.0.0.1", port)).unwrap()
     *         return listener.intoIncoming().mapNotNull(Result.ok)
     *         // Ignore failed connections
     *     }
     *     fun main(): Result<Unit> {
     *         for (stream in listenOn(80u)) {
     *             // handle the connection here
     *         }
     *         return Result.success(Unit)
     *     }
     * }
     * ```
     */
    public open fun intoIncoming(): IntoIncoming = IntoIncoming(listener = this)

    /**
     * Sets the value for the `IP_TTL` option on this socket.
     *
     * This value sets the time-to-live field that is used in every packet sent
     * from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:80").unwrap()
     * listener.setTtl(100).expect("setTtl should succeed")
     * ```
     */
    public open fun setTtl(ttl: UInt): Result<Unit> = this.value.setTtl(ttl)

    /**
     * Gets the value of the `IP_TTL` option for this socket.
     *
     * For more information about this option, see [TcpListener.setTtl].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:80").unwrap()
     * listener.setTtl(100).expect("setTtl should succeed")
     * check(listener.ttl().unwrapOr(0) == 100)
     * ```
     */
    public open fun ttl(): Result<UInt> = this.value.ttl()

    /**
     * Sets the value for the `IPV6_UNICAST_HOPS` option on this socket.
     *
     * This value sets the unicast hop limit field that is used in every packet
     * sent from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val listener = TcpListener.bind("[::1]:12345").unwrap()
     *     listener.setHopLimitV6(88).expect("setHopLimitV6 call failed")
     * }
     * ```
     */
    public open fun setHopLimitV6(limit: UByte): Result<Unit> = this.value.setHopLimitV6(limit)

    /**
     * Gets the value of the `IPV6_UNICAST_HOPS` option on this socket.
     *
     * For more information about this option, see [TcpListener.setHopLimitV6].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val listener = TcpListener.bind("[::1]:12345").unwrap()
     *     listener.setHopLimitV6(88).expect("setHopLimitV6 call failed")
     *     check(listener.hopLimitV6().unwrap() == 88)
     * }
     * ```
     */
    public open fun hopLimitV6(): Result<UByte> = this.value.hopLimitV6()

    public open fun setOnlyV6(onlyV6: Boolean): Result<Unit> = this.value.setOnlyV6(onlyV6)

    public open fun onlyV6(): Result<Boolean> = this.value.onlyV6()

    /**
     * Gets the value of the `SO_ERROR` option on this socket.
     *
     * This will retrieve the stored error in the underlying socket, clearing
     * the field in the process. This can be useful for checking errors between
     * calls.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:80").unwrap()
     * listener.takeError().expect("`takeError` should succeed")
     * ```
     */
    public open fun takeError(): Result<Error?> = this.value.takeError()

    /**
     * Moves this TCP stream into or out of nonblocking mode.
     *
     * This will result in the `accept` operation becoming nonblocking,
     * i.e., immediately returning from their calls. If the IO operation is
     * successful, `Ok` is returned and no further action is required. If the
     * IO operation could not be completed and needs to be retried, an error
     * with kind [io.ErrorKind.WouldBlock] is returned.
     *
     * On most Unix platforms, calling this method corresponds to calling `ioctl`
     * `FIONBIO`. On Windows, calling this method corresponds to calling
     * `ioctlsocket` `FIONBIO`.
     *
     * # Examples
     *
     * Bind a TCP listener to an address, listen for connections, and read
     * bytes in nonblocking mode:
     *
     * ```kotlin
     * import std.net.TcpListener
     *
     * val listener = TcpListener.bind("127.0.0.1:7878").unwrap()
     * listener.setNonblocking(true).expect("setNonblocking should succeed")
     * for (stream in listener.incoming()) {
     *     when {
     *         stream is Ok -> {
     *             val (s) = stream
     *             // do something with the TcpStream
     *             handleConnection(s)
     *         }
     *         stream is Err && (stream as Err).value.kind() == io.ErrorKind.WouldBlock -> {
     *             val (e) = stream
     *             // wait until network socket is ready, typically implemented
     *             // via platform-specific APIs such as epoll or IOCP
     *             waitForFd()
     *             continue
     *         }
     *         else -> {
     *             val (e1) = stream
     *             error("encountered IO error: $e")
     *         }
     *     }
     * }
     * ```
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = this.value.setNonblocking(nonblocking)

    public override fun asInner(): io.github.kotlinmania.netimp.TcpListener = this.value

    public override fun intoInner(): io.github.kotlinmania.netimp.TcpListener = this.value

    public open fun fmt(f: StringBuilder) {
        this.value.fmt(f)
    }

    public companion object {
        /**
         * Creates a new `TcpListener` which will be bound to the specified
         * address.
         *
         * The returned listener is ready for accepting connections.
         *
         * Binding with a port number of 0 will request that the OS assigns a port
         * to this listener. The port allocated can be queried via the
         * [TcpListener.localAddr] method.
         *
         * The address type can be any implementor of [ToSocketAddrs] trait. See
         * its documentation for concrete examples.
         *
         * If `addr` yields multiple addresses, `bind` will be attempted with
         * each of the addresses until one succeeds and returns the listener. If
         * none of the addresses succeed in creating a listener, the error returned
         * from the last attempt (the last address) is returned.
         *
         * # Examples
         *
         * Creates a TCP listener bound to `127.0.0.1: 80`:
         *
         * ```kotlin
         * import std.net.TcpListener
         *
         * val listener = TcpListener.bind("127.0.0.1:80").unwrap()
         * ```
         *
         * Creates a TCP listener bound to `127.0.0.1: 80`. If that fails, create a
         * TCP listener bound to `127.0.0.1: 443`:
         *
         * ```kotlin
         * import std.net.SocketAddr
         * import std.net.TcpListener
         *
         * val addrs = listOf(SocketAddr.from(Pair(listOf(127, 0, 0, 1), 80)), SocketAddr.from(Pair(listOf(127, 0, 0, 1), 443)))
         * val listener = TcpListener.bind(addrs).unwrap()
         * ```
         *
         * Creates a TCP listener bound to a port assigned by the operating system
         * at `127.0.0.1`.
         *
         * ```kotlin
         * import std.net.TcpListener
         *
         * val socket = TcpListener.bind("127.0.0.1:0").unwrap()
         * ```
         */
        public fun <Iter, A : ToSocketAddrs<Any?>> bind(addr: A): Result<TcpListener> = io.github.kotlinmania.netimp.TcpListener.bind(addr).map({ TcpListener(it) })

        public fun fromInner(inner: io.github.kotlinmania.netimp.TcpListener): TcpListener = TcpListener(inner)
    }
}

/**
 * An iterator that infinitely [accept]s connections on a [TcpListener].
 *
 * This `class` is created by the [TcpListener.incoming] method.
 * See its documentation for more.
 *
 * [accept]: TcpListener.accept
 */
public open class Incoming(
    public var listener: TcpListener,
) : Iterable<Result<TcpStream>> {
    override fun iterator(): kotlin.collections.Iterator<Result<TcpStream>> = object : AbstractIterator<Result<TcpStream>>() {
        override fun computeNext() {
            when (val v = this@Incoming.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // In addition to the `implementation`s here, `TcpListener` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsSocket`/`From<OwnedSocket>`/`Into<OwnedSocket>` and
    // `AsRawSocket`/`IntoRawSocket`/`FromRawSocket` on Windows.
    public open fun next(): Result<TcpStream>? = this.listener.accept().map({ p -> p.first })
}

/**
 * An iterator that infinitely [accept]s connections on a [TcpListener].
 *
 * This `class` is created by the [TcpListener.intoIncoming] method.
 * See its documentation for more.
 *
 * [accept]: TcpListener.accept
 */
public open class IntoIncoming(
    public var listener: TcpListener,
) : Iterable<Result<TcpStream>> {
    override fun iterator(): kotlin.collections.Iterator<Result<TcpStream>> = object : AbstractIterator<Result<TcpStream>>() {
        override fun computeNext() {
            when (val v = this@IntoIncoming.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Result<TcpStream>? = this.listener.accept().map({ p -> p.first })
}
