// port-lint: source net/udp.rs
package io.github.kotlinmania.rustlang.std.net.udp

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.socketaddr.ToSocketAddrs
import io.github.kotlinmania.rustlang.std.net.socketaddr.toSocketAddrs
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner

/**
 * A UDP socket.
 *
 * After creating a `UdpSocket` by [bind]ing it to a socket address, data can be
 * [sent to] and [received from] any other socket address.
 *
 * Although UDP is a connectionless protocol, this implementation provides an interface
 * to set an address where data should be sent and received from. After setting a remote
 * address with [connect], data can be sent to and received from that address with
 * [send] and [recv].
 *
 * As stated in the User Datagram Protocol's specification in [IETF RFC 768], UDP is
 * an unordered, unreliable protocol; refer to [TcpListener] and [TcpStream] for TCP
 * primitives.
 *
 * [bind]: UdpSocket.bind
 * [connect]: UdpSocket.connect
 * [IETF RFC 768]: https://tools.ietf.org/html/rfc768
 * [recv]: UdpSocket.recv
 * [received from]: UdpSocket.recvFrom
 * [send]: UdpSocket.send
 * [sent to]: UdpSocket.sendTo
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.UdpSocket
 *
 * fun main(): Result<Unit> {
 *     run {
 *         val socket = UdpSocket.bind("127.0.0.1:34254").getOrElse { return Result.failure(it) }
 *         // Receives a single datagram message on the socket. If `buf` is too small to hold
 *         // the message, it will be cut off.
 *         var buf = MutableList(10) { 0 }
 *         val (amt, src) = socket.recvFrom(buf).getOrElse { return Result.failure(it) }
 *         // Redeclare `buf` as slice of the received data and send reverse data back to origin.
 *         val buf1 = buf.subList(0, amt)
 *         buf1.reverse()
 *         socket.sendTo(buf1, src).getOrElse { return Result.failure(it) }
 *     }
 *     // the socket is closed here
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class UdpSocket(
    public var value: io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket>, FromInner<io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket, UdpSocket>, IntoInner<io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket
        get() = value
        set(v) {
            value = v
        }

    /**
     * Receives a single datagram message on the socket. On success, returns the number
     * of bytes read and the origin.
     *
     * The function must be called with valid byte array `buf` of sufficient size to
     * hold the message bytes. If a message is too long to fit in the supplied buffer,
     * excess bytes may be discarded.
     *
     * Refer to the platform-specific documentation on this function; it is considered
     * correct for its behavior to differ from [UdpSocket.recv] if the underlying system
     * call does so.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * var buf = MutableList(10) { 0 }
     * val (numberOfBytes, srcAddr) = socket.recvFrom(buf).expect("recvFrom should succeed")
     * val filledBuf = buf.subList(0, numberOfBytes)
     * ```
     */
    public open fun recvFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.value.recvFrom(buf)

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
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * var buf = MutableList(10) { 0 }
     * val (numberOfBytes, srcAddr) = socket.peekFrom(buf).expect("recvFrom should succeed")
     * val filledBuf = buf.subList(0, numberOfBytes)
     * ```
     */
    public open fun peekFrom(buf: MutableList<UByte>): Result<Pair<Int, SocketAddr>, Error> = this.value.peekFrom(buf)

    /**
     * Sends data on the socket to the given address. On success, returns the
     * number of bytes written. Note that the operating system may refuse
     * buffers larger than 65507. However, partial writes are not possible
     * until buffer sizes above `Int.MAX`.
     *
     * Address type can be any implementor of [ToSocketAddrs] trait. See its
     * documentation for concrete examples.
     *
     * It is possible for `addr` to yield multiple addresses, but `sendTo`
     * will only send data to the first address yielded by `addr`.
     *
     * This will return an error when the IP version of the local socket
     * does not match that returned from [ToSocketAddrs].
     *
     * See [Issue #34202] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.sendTo(List(10) { 0 }, "127.0.0.1:4242").expect("sendTo should succeed")
     * ```
     *
     * [Issue #34202]: https://github.com/rust-lang/rust/issues/34202
     */
    public open fun <Iter, A : ToSocketAddrs<Iter>> sendTo(buf: List<UByte>, addr: A): Result<Int, Error> {
        return when (val matchVal = when (val tryResult = addr.toSocketAddrs()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }.next()) {
            null -> Result.Err<Int, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "no addresses to send data to")))
            else -> {
                val addr1 = matchVal
                this.value.sendTo(buf, addr1)
            }
        }
    }

    /**
     * Returns the socket address of the remote peer this socket was connected to.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     * import std.net.SocketAddrV4
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.connect("192.168.0.1:41203").expect("connect should succeed")
     * check(socket.peerAddr().unwrap() == SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(192, 168, 0, 1), 41203)))
     * ```
     *
     * If the socket isn't connected, it will return a [NotConnected] error.
     *
     * [NotConnected]: io.ErrorKind.NotConnected
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * check(socket.peerAddr().unwrapErr().kind() == ErrorKind.NotConnected)
     * ```
     */
    public open fun peerAddr(): Result<SocketAddr, Error> = this.value.peerAddr()

    /**
     * Returns the socket address that this socket was created from.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     * import std.net.SocketAddrV4
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * check(socket.localAddr().unwrap() == SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 34254)))
     * ```
     */
    public open fun localAddr(): Result<SocketAddr, Error> = this.value.socketAddr()

    /**
     * Creates a new independently owned handle to the underlying socket.
     *
     * The returned `UdpSocket` is a reference to the same socket that this
     * object references. Both handles will read and write the same port, and
     * options set on one socket will be propagated to the other.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * val socketClone = socket.tryClone().expect("tryClone should succeed")
     * ```
     */
    public open fun tryClone(): Result<UdpSocket, Error> = this.value.duplicate().map({ UdpSocket(it) })

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
     * [read]: io.Read.read
     * [WouldBlock]: io.ErrorKind.WouldBlock
     * [TimedOut]: io.ErrorKind.TimedOut
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setReadTimeout(null).expect("setReadTimeout should succeed")
     * ```
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * ```kotlin
     * import std.io
     * import std.net.UdpSocket
     * import std.time.Duration
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").unwrap()
     * val result = socket.setReadTimeout(Duration.new(0, 0))
     * val err = result.unwrapErr()
     * check(err.kind() == io.ErrorKind.InvalidInput)
     * ```
     */
    public open fun setReadTimeout(dur: Duration?): Result<Unit, Error> = this.value.setReadTimeout(dur)

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
     * [write]: io.Write.write
     * [WouldBlock]: io.ErrorKind.WouldBlock
     * [TimedOut]: io.ErrorKind.TimedOut
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setWriteTimeout(null).expect("setWriteTimeout should succeed")
     * ```
     *
     * An [Err] is returned if the zero [Duration] is passed to this
     * method:
     *
     * ```kotlin
     * import std.io
     * import std.net.UdpSocket
     * import std.time.Duration
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").unwrap()
     * val result = socket.setWriteTimeout(Duration.new(0, 0))
     * val err = result.unwrapErr()
     * check(err.kind() == io.ErrorKind.InvalidInput)
     * ```
     */
    public open fun setWriteTimeout(dur: Duration?): Result<Unit, Error> = this.value.setWriteTimeout(dur)

    /**
     * Returns the read timeout of this socket.
     *
     * If the timeout is [None], then [read] calls will block indefinitely.
     *
     * [read]: io.Read.read
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setReadTimeout(null).expect("setReadTimeout should succeed")
     * check(socket.readTimeout().unwrap() == null)
     * ```
     */
    public open fun readTimeout(): Result<Duration?, Error> = this.value.readTimeout()

    /**
     * Returns the write timeout of this socket.
     *
     * If the timeout is [None], then [write] calls will block indefinitely.
     *
     * [write]: io.Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setWriteTimeout(null).expect("setWriteTimeout should succeed")
     * check(socket.writeTimeout().unwrap() == null)
     * ```
     */
    public open fun writeTimeout(): Result<Duration?, Error> = this.value.writeTimeout()

    /**
     * Sets the value of the `SO_BROADCAST` option for this socket.
     *
     * When enabled, this socket is allowed to send packets to a broadcast
     * address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setBroadcast(false).expect("setBroadcast should succeed")
     * ```
     */
    public open fun setBroadcast(broadcast: Boolean): Result<Unit, Error> = this.value.setBroadcast(broadcast)

    /**
     * Gets the value of the `SO_BROADCAST` option for this socket.
     *
     * For more information about this option, see [UdpSocket.setBroadcast].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setBroadcast(false).expect("setBroadcast should succeed")
     * check(socket.broadcast().unwrap() == false)
     * ```
     */
    public open fun broadcast(): Result<Boolean, Error> = this.value.broadcast()

    /**
     * Sets the value of the `IP_MULTICAST_LOOP` option for this socket.
     *
     * If enabled, multicast packets will be looped back to the local socket.
     * Note that this might not have any effect on IPv6 sockets.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setMulticastLoopV4(false).expect("setMulticastLoopV4 should succeed")
     * ```
     */
    public open fun setMulticastLoopV4(multicastLoopV4: Boolean): Result<Unit, Error> = this.value.setMulticastLoopV4(multicastLoopV4)

    /**
     * Gets the value of the `IP_MULTICAST_LOOP` option for this socket.
     *
     * For more information about this option, see [UdpSocket.setMulticastLoopV4].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setMulticastLoopV4(false).expect("setMulticastLoopV4 should succeed")
     * check(socket.multicastLoopV4().unwrap() == false)
     * ```
     */
    public open fun multicastLoopV4(): Result<Boolean, Error> = this.value.multicastLoopV4()

    /**
     * Sets the value of the `IP_MULTICAST_TTL` option for this socket.
     *
     * Indicates the time-to-live value of outgoing multicast packets for
     * this socket. The default value is 1 which means that multicast packets
     * don't leave the local network unless explicitly requested.
     *
     * Note that this might not have any effect on IPv6 sockets.
     *
     * Since the underlying socket option value is byte-sized,
     * any value above 255 will result in an error.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setMulticastTtlV4(42).expect("setMulticastTtlV4 should succeed")
     * ```
     */
    public open fun setMulticastTtlV4(multicastTtlV4: UInt): Result<Unit, Error> = this.value.setMulticastTtlV4(multicastTtlV4)

    /**
     * Gets the value of the `IP_MULTICAST_TTL` option for this socket.
     *
     * For more information about this option, see [UdpSocket.setMulticastTtlV4].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setMulticastTtlV4(42).expect("setMulticastTtlV4 should succeed")
     * check(socket.multicastTtlV4().unwrap() == 42)
     * ```
     */
    public open fun multicastTtlV4(): Result<UInt, Error> = this.value.multicastTtlV4()

    /**
     * Sets the value of the `IPV6_MULTICAST_LOOP` option for this socket.
     *
     * Controls whether this socket sees the multicast packets it sends itself.
     * Note that this might not have any affect on IPv4 sockets.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("[::1]:34254").expect("bind should succeed")
     * socket.setMulticastLoopV6(false).expect("setMulticastLoopV6 should succeed")
     * ```
     */
    public open fun setMulticastLoopV6(multicastLoopV6: Boolean): Result<Unit, Error> = this.value.setMulticastLoopV6(multicastLoopV6)

    /**
     * Gets the value of the `IPV6_MULTICAST_LOOP` option for this socket.
     *
     * For more information about this option, see [UdpSocket.setMulticastLoopV6].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("[::1]:34254").expect("bind should succeed")
     * socket.setMulticastLoopV6(false).expect("setMulticastLoopV6 should succeed")
     * check(socket.multicastLoopV6().unwrap() == false)
     * ```
     */
    public open fun multicastLoopV6(): Result<Boolean, Error> = this.value.multicastLoopV6()

    /**
     * Sets the value for the `IP_TTL` option on this socket.
     *
     * This value sets the time-to-live field that is used in every packet sent
     * from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setTtl(42).expect("setTtl should succeed")
     * ```
     */
    public open fun setTtl(ttl: UInt): Result<Unit, Error> = this.value.setTtl(ttl)

    /**
     * Gets the value of the `IP_TTL` option for this socket.
     *
     * For more information about this option, see [UdpSocket.setTtl].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.setTtl(42).expect("setTtl should succeed")
     * check(socket.ttl().unwrap() == 42)
     * ```
     */
    public open fun ttl(): Result<UInt, Error> = this.value.ttl()

    /**
     * Sets the value for the `IPV6_UNICAST_HOPS` option on this socket.
     *
     * This value sets the unicast hop limit field that is used in every packet
     * sent from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val socket = UdpSocket.bind("[::1]:12345").expect("couldn't bind to address")
     *     socket.setHopLimitV6(88).expect("setHopLimitV6 call failed")
     * }
     * ```
     */
    public open fun setHopLimitV6(limit: UByte): Result<Unit, Error> = this.value.setHopLimitV6(limit)

    /**
     * Gets the value of the `IPV6_UNICAST_HOPS` option on this socket.
     *
     * For more information about this option, see [UdpSocket.setHopLimitV6].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val socket = UdpSocket.bind("[::1]:12345").expect("couldn't bind to address")
     *     socket.setHopLimitV6(88).expect("setHopLimitV6 call failed")
     *     check(socket.hopLimitV6().unwrap() == 88)
     * }
     * ```
     */
    public open fun hopLimitV6(): Result<UByte, Error> = this.value.hopLimitV6()

    /**
     * Sets the value for the `IPV6_MULTICAST_HOPS` option on this socket.
     *
     * This value sets the hop limit field for outgoing multicast packets
     * sent from this socket.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val socket = UdpSocket.bind("[::1]:12345").expect("couldn't bind to address")
     *     socket.setMulticastHopLimitV6(88).expect("setMulticastHopLimitV6 call failed")
     * }
     * ```
     */
    public open fun setMulticastHopLimitV6(limit: UByte): Result<Unit, Error> = this.value.setMulticastHopLimitV6(limit)

    /**
     * Gets the value of the `IPV6_MULTICAST_HOPS` option on this socket.
     *
     * For more information about this option, see [UdpSocket.setMulticastHopLimitV6].
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * @feature(ipv6HopLimit)
     * public fun doctestEntry() {
     *     val socket = UdpSocket.bind("[::1]:12345").expect("couldn't bind to address")
     *     socket.setMulticastHopLimitV6(88).expect("setMulticastHopLimitV6 call failed")
     *     check(socket.multicastHopLimitV6().unwrap() == 88)
     * }
     * ```
     */
    public open fun multicastHopLimitV6(): Result<UByte, Error> = this.value.multicastHopLimitV6()

    /**
     * Executes an operation of the `IP_ADD_MEMBERSHIP` type.
     *
     * This function specifies a new multicast group for this socket to join.
     * The address must be a valid multicast address, and `interface` is the
     * address of the local interface with which the system should join the
     * multicast group. If it's equal to [`Unspecified`](Ipv4Addr.Unspecified)
     * then an appropriate interface is chosen by the system.
     */
    public open fun joinMulticastV4(multiaddr: Ipv4Addr, iface: Ipv4Addr): Result<Unit, Error> = this.value.joinMulticastV4(multiaddr, iface)

    /**
     * Executes an operation of the `IPV6_ADD_MEMBERSHIP` type.
     *
     * This function specifies a new multicast group for this socket to join.
     * The address must be a valid multicast address, and `interface` is the
     * index of the interface to join/leave (or 0 to indicate any interface).
     */
    public open fun joinMulticastV6(multiaddr: Ipv6Addr, iface: UInt): Result<Unit, Error> = this.value.joinMulticastV6(multiaddr, iface)

    /**
     * Executes an operation of the `IP_DROP_MEMBERSHIP` type.
     *
     * For more information about this option, see [UdpSocket.joinMulticastV4].
     */
    public open fun leaveMulticastV4(multiaddr: Ipv4Addr, iface: Ipv4Addr): Result<Unit, Error> = this.value.leaveMulticastV4(multiaddr, iface)

    /**
     * Executes an operation of the `IPV6_DROP_MEMBERSHIP` type.
     *
     * For more information about this option, see [UdpSocket.joinMulticastV6].
     */
    public open fun leaveMulticastV6(multiaddr: Ipv6Addr, iface: UInt): Result<Unit, Error> = this.value.leaveMulticastV6(multiaddr, iface)

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
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * run {
     *     val matchVal = socket.takeError()
     *     when {
     *         matchVal is Ok && matchVal.value != null -> {
     *             val error = matchVal.value
     *             println("UdpSocket error: $error")
     *         }
     *         matchVal is Ok && matchVal.value == null -> {
     *             val (None) = matchVal
     *             println("No error")
     *         }
     *         else -> {
     *             val (error1) = matchVal as Err
     *             println("UdpSocket.takeError failed: $error")
     *         }
     *     }
     * }
     * ```
     */
    public open fun takeError(): Result<Error?, Error> = this.value.takeError()

    /**
     * Connects this UDP socket to a remote address, allowing the `send` and
     * `recv` syscalls to be used to send data and also applies filters to only
     * receive data from the specified address.
     *
     * If `addr` yields multiple addresses, `connect` will be attempted with
     * each of the addresses until the underlying OS function returns no
     * error. Note that usually, a successful `connect` call does not specify
     * that there is a remote server listening on the port, rather, such an
     * error would only be detected after the first send. If the OS returns an
     * error for each of the specified addresses, the error returned from the
     * last connection attempt (the last address) is returned.
     *
     * # Examples
     *
     * Creates a UDP socket bound to `127.0.0.1: 3400` and connect the socket to
     * `127.0.0.1: 8080`:
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:3400").expect("bind should succeed")
     * socket.connect("127.0.0.1:8080").expect("connect should succeed")
     * ```
     *
     * Unlike in the TCP case, passing an array of addresses to the `connect`
     * function of a UDP socket is not a useful thing to do: The OS will be
     * unable to determine whether something is listening on the remote
     * address without the application sending data.
     *
     * If your first `connect` is to a loopback address, subsequent
     * `connect`s to non-loopback addresses might fail, depending
     * on the platform.
     */
    public open fun <Iter, A : ToSocketAddrs<Iter>> connect(addr: A): Result<Unit, Error> = this.value.connect(addr)

    /**
     * Sends data on the socket to the remote address to which it is connected.
     * On success, returns the number of bytes written. Note that the operating
     * system may refuse buffers larger than 65507. However, partial writes are
     * not possible until buffer sizes above `Int.MAX`.
     *
     * [UdpSocket.connect] will connect this socket to a remote address. This
     * method will fail if the socket is not connected.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.connect("127.0.0.1:8080").expect("connect should succeed")
     * socket.send(listOf(0, 1, 2)).expect("send should succeed")
     * ```
     */
    public open fun send(buf: List<UByte>): Result<Int, Error> = this.value.send(buf)

    /**
     * Receives a single datagram message on the socket from the remote address to
     * which it is connected. On success, returns the number of bytes read.
     *
     * The function must be called with valid byte array `buf` of sufficient size to
     * hold the message bytes. If a message is too long to fit in the supplied buffer,
     * excess bytes may be discarded.
     *
     * [UdpSocket.connect] will connect this socket to a remote address. This
     * method will fail if the socket is not connected.
     *
     * Refer to the platform-specific documentation on this function; it is considered
     * correct for its behavior to differ from [UdpSocket.recvFrom] if the underlying
     * system call does so.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.connect("127.0.0.1:8080").expect("connect should succeed")
     * var buf = MutableList(10) { 0 }
     * run {
     *     val matchVal = socket.recv(buf)
     *     if (matchVal.isSuccess) {
     *         val received = matchVal.getOrThrow()
     *         println("received $received bytes ${buf[0 until received]}")
     *     } else {
     *         val e = matchVal.exceptionOrNull()!!
     *         println("recv function failed: $e")
     *     }
     * }
     * ```
     */
    public open fun recv(buf: MutableList<UByte>): Result<Int, Error> = this.value.recv(buf)

    /**
     * Receives single datagram on the socket from the remote address to which it is
     * connected, without removing the message from input queue. On success, returns
     * the number of bytes peeked.
     *
     * The function must be called with valid byte array `buf` of sufficient size to
     * hold the message bytes. If a message is too long to fit in the supplied buffer,
     * excess bytes may be discarded.
     *
     * Successive calls return the same data. This is accomplished by passing
     * `MSG_PEEK` as a flag to the underlying `recv` system call.
     *
     * Do not use this function to implement busy waiting, instead use `libc.poll` to
     * synchronize IO events on one or more sockets.
     *
     * [UdpSocket.connect] will connect this socket to a remote address. This
     * method will fail if the socket is not connected.
     *
     * # Errors
     *
     * This method will fail if the socket is not connected. The `connect` method
     * will connect this socket to a remote address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:34254").expect("bind should succeed")
     * socket.connect("127.0.0.1:8080").expect("connect should succeed")
     * var buf = MutableList(10) { 0 }
     * run {
     *     val matchVal = socket.peek(buf)
     *     if (matchVal.isSuccess) {
     *         val received = matchVal.getOrThrow()
     *         println("received $received bytes")
     *     } else {
     *         val e = matchVal.exceptionOrNull()!!
     *         println("peek function failed: $e")
     *     }
     * }
     * ```
     */
    public open fun peek(buf: MutableList<UByte>): Result<Int, Error> = this.value.peek(buf)

    /**
     * Moves this UDP socket into or out of nonblocking mode.
     *
     * This will result in `recv`, `recvFrom`, `send`, and `sendTo` system
     * operations becoming nonblocking, i.e., immediately returning from their
     * calls. If the IO operation is successful, `Ok` is returned and no
     * further action is required. If the IO operation could not be completed
     * and needs to be retried, an error with kind
     * [io.ErrorKind.WouldBlock] is returned.
     *
     * On most Unix platforms, calling this method corresponds to calling `ioctl`
     * `FIONBIO`. On Windows, calling this method corresponds to calling
     * `ioctlsocket` `FIONBIO`.
     *
     * # Examples
     *
     * Creates a UDP socket bound to `127.0.0.1: 7878` and read bytes in
     * nonblocking mode:
     *
     * ```kotlin
     * import std.io
     * import std.net.UdpSocket
     *
     * val socket = UdpSocket.bind("127.0.0.1:7878").unwrap()
     * socket.setNonblocking(true).unwrap()
     * var buf = MutableList(10) { 0 }
     * val (numBytesRead, _) = while (true) {
     *     run {
     *         val matchVal = socket.recvFrom(buf)
     *         when {
     *             matchVal is Ok -> {
     *                 val (n) = matchVal
     *                 break
     *             }
     *             matchVal is Err && matchVal.value.kind() == io.ErrorKind.WouldBlock -> {
     *                 val (e) = matchVal
     *                 // wait until network socket is ready, typically implemented
     *                 // via platform-specific APIs such as epoll or IOCP
     *                 waitForFd()
     *             }
     *             else -> {
     *                 val (e1) = matchVal as Err
     *                 error("encountered IO error: $e")
     *             }
     *         }
     *     }
     * }
     * println("bytes: ${buf[0 until numBytesRead]}")
     * ```
     */
    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> = this.value.setNonblocking(nonblocking)

    // In addition to the `implementation`s here, `UdpSocket` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsSocket`/`From<OwnedSocket>`/`Into<OwnedSocket>` and
    // `AsRawSocket`/`IntoRawSocket`/`FromRawSocket` on Windows.
    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket = this.value

    public override fun intoInner(): io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value.fmt(f)

    public companion object {
        /**
         * Creates a UDP socket from the given address.
         *
         * The address type can be any implementor of [ToSocketAddrs] trait. See
         * its documentation for concrete examples.
         *
         * If `addr` yields multiple addresses, `bind` will be attempted with
         * each of the addresses until one succeeds and returns the socket. If none
         * of the addresses succeed in creating a socket, the error returned from
         * the last attempt (the last address) is returned.
         *
         * # Examples
         *
         * Creates a UDP socket bound to `127.0.0.1: 3400`:
         *
         * ```kotlin
         * import std.net.UdpSocket
         *
         * val socket = UdpSocket.bind("127.0.0.1:3400").expect("bind should succeed")
         * ```
         *
         * Creates a UDP socket bound to `127.0.0.1: 3400`. If the socket cannot be
         * bound to that address, create a UDP socket bound to `127.0.0.1: 3401`:
         *
         * ```kotlin
         * import std.net.SocketAddr
         * import std.net.UdpSocket
         *
         * val addrs = listOf(SocketAddr.from(Pair(listOf(127, 0, 0, 1), 3400)), SocketAddr.from(Pair(listOf(127, 0, 0, 1), 3401)))
         * val socket = UdpSocket.bind(addrs).expect("bind should succeed")
         * ```
         *
         * Creates a UDP socket bound to a port assigned by the operating system
         * at `127.0.0.1`.
         *
         * ```kotlin
         * import std.net.UdpSocket
         *
         * val socket = UdpSocket.bind("127.0.0.1:0").unwrap()
         * ```
         *
         * Note that `bind` declares the scope of your network connection.
         * You can only receive datagrams from and send datagrams to
         * participants in that view of the network.
         * For instance, binding to a loopback address as in the example
         * above will prevent you from sending datagrams to another device
         * in your local network.
         *
         * In order to limit your view of the network the least, `bind` to
         * [Ipv4Addr.Unspecified] or [Ipv6Addr.Unspecified].
         */
        public fun <Iter, A : ToSocketAddrs<Iter>> bind(addr: A): Result<UdpSocket, Error> = io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket.bind(addr).map({ UdpSocket(it) })

        public fun fromInner(inner: io.github.kotlinmania.rustlang.std.sys.net.connection.socket.UdpSocket): UdpSocket = UdpSocket(inner)
    }
}
