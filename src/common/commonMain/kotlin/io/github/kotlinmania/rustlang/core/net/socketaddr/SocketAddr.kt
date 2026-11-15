// port-lint: source net/socket_addr.rs
package io.github.kotlinmania.rustlang.core.net.socketaddr

import io.github.kotlinmania.rustlang.core.net.displaybuffer.DisplayBuffer
import io.github.kotlinmania.rustlang.core.net.ipaddr.IpAddr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An internet socket address, either IPv4 or IPv6.
 *
 * Internet socket addresses consist of an [IP address], a 16-bit port number, as well
 * as possibly some version-dependent additional information. See [SocketAddrV4]s and
 * [SocketAddrV6]s respective documentation for more details.
 *
 * [IP address]: IpAddr
 *
 * # Portability
 *
 * `SocketAddr` is intended to be a portable representation of socket addresses and is likely not
 * the same as the internal socket address type used by the target operating system's API. Like all
 * `repr(Rust)` structs, however, its exact layout remains undefined and should not be relied upon
 * between builds.
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.IpAddr
 * import std.net.Ipv4Addr
 * import std.net.SocketAddr
 *
 * val socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
 * check("127.0.0.1:8080".parse() == Result.success(socket))
 * check(socket.port() == 8080)
 * check(socket.isIpv4() == true)
 * ```
 */
public sealed class SocketAddr {
    /**
     * An IPv4 socket address.
     */
    public data class V4(
        public var value: SocketAddrV4,
    ) : SocketAddr()

    /**
     * An IPv6 socket address.
     */
    public data class V6(
        public var value: SocketAddrV6,
    ) : SocketAddr()

    public fun rustEquals(other: SocketAddr): Boolean = this == other

    /**
     * Returns the IP address associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     *
     * val socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
     * check(socket.ip() == IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)))
     * ```
     */
    public open fun ip(): IpAddr =
        when (this) {
            is SocketAddr.V4 -> {
                val (a) = this
                IpAddr.v4(a.ip())
            }
            is SocketAddr.V6 -> {
                val (a1) = this
                IpAddr.v6(a1.ip())
            }
        }

    /**
     * Changes the IP address associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     *
     * var socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
     * socket.setIp(IpAddr.v4(Ipv4Addr.new(10, 10, 0, 1)))
     * check(socket.ip() == IpAddr.v4(Ipv4Addr.new(10, 10, 0, 1)))
     * ```
     */
    public open fun setIp(newIp: IpAddr) {
        // `when (* self, newIp)` would have us mutate a copy of self only to throw it away.
        run {
            val matchVal = Pair(this, newIp)
            when {
                matchVal.first is SocketAddr.V4 && matchVal.second is IpAddr.V4 -> {
                    val (a) = matchVal.first as SocketAddr.V4
                    val (newIp1) = matchVal.second as IpAddr.V4
                    a.setIp(newIp1)
                }
                matchVal.first is SocketAddr.V6 && matchVal.second is IpAddr.V6 -> {
                    val (a1) = matchVal.first as SocketAddr.V6
                    val (newIp2) = matchVal.second as IpAddr.V6
                    a1.setIp(newIp2)
                }
                else -> {
                    val selfRef = matchVal.first
                    val newIp3 = matchVal.second
                    selfRef = SocketAddr.new(newIp3, selfRef.port())
                }
            }
        }
    }

    /**
     * Returns the port number associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     *
     * val socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
     * check(socket.port() == 8080)
     * ```
     */
    public open fun port(): UShort =
        when (this) {
            is SocketAddr.V4 -> {
                val (a) = this
                a.port()
            }
            is SocketAddr.V6 -> {
                val (a1) = this
                a1.port()
            }
        }

    /**
     * Changes the port number associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     *
     * var socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
     * socket.setPort(1025)
     * check(socket.port() == 1025)
     * ```
     */
    public open fun setPort(newPort: UShort) {
        when (this) {
            is SocketAddr.V4 -> {
                val (a) = this
                a.setPort(newPort)
            }
            is SocketAddr.V6 -> {
                val (a1) = this
                a1.setPort(newPort)
            }
        }
    }

    /**
     * Returns [trueVal] if the [IP address] in this `SocketAddr` is an
     * [`IPv4` address], and [falseVal] otherwise.
     *
     * [IP address]: IpAddr
     * [`IPv4` address]: IpAddr.V4
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.SocketAddr
     *
     * val socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
     * check(socket.isIpv4() == true)
     * check(socket.isIpv6() == false)
     * ```
     */
    public open fun isIpv4(): Boolean =
        when (this) {
            is SocketAddr.V4 -> true
            else -> false
        }

    /**
     * Returns [trueVal] if the [IP address] in this `SocketAddr` is an
     * [`IPv6` address], and [falseVal] otherwise.
     *
     * [IP address]: IpAddr
     * [`IPv6` address]: IpAddr.V6
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv6Addr
     * import std.net.SocketAddr
     *
     * val socket = SocketAddr.new(IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 65535, 0, 1)), 8080)
     * check(socket.isIpv4() == false)
     * check(socket.isIpv6() == true)
     * ```
     */
    public open fun isIpv6(): Boolean =
        when (this) {
            is SocketAddr.V6 -> true
            else -> false
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this) {
            is SocketAddr.V4 -> {
                val (a) = this
                run {
                    a.fmt(f)
                    Result.Ok(Unit)
                }
            }
            is SocketAddr.V6 -> {
                val (a1) = this
                run {
                    a1.fmt(f)
                    Result.Ok(Unit)
                }
            }
        }

    public open fun fmtDebug(fmt: StringBuilder): Result<Unit, Unit> =
        run {
            fmt.append((this).toString())
            Result.Ok(Unit)
        }

    public companion object {
        /**
         * Creates a new socket address from an [IP address] and a port number.
         *
         * [IP address]: IpAddr
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.IpAddr
         * import std.net.Ipv4Addr
         * import std.net.SocketAddr
         *
         * val socket = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
         * check(socket.ip() == IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)))
         * check(socket.port() == 8080)
         * ```
         */
        public fun new(ip: IpAddr, port: UShort): SocketAddr =
            when (ip) {
                is IpAddr.V4 -> {
                    val (a) = ip
                    SocketAddr.v4(SocketAddrV4.new(a, port))
                }
                is IpAddr.V6 -> {
                    val (a1) = ip
                    SocketAddr.v6(SocketAddrV6.new(a1, port, 0u, 0u))
                }
            }

        /**
         * Returns the unspecified socket address for the same IP version.
         *
         * Returns `0.0.0.0: 0` for IPv4 and `[.]: 0` for IPv6. For IPv6, this
         * method preserves the flow information and scope ID.
         *
         * Use this method when you must bind a socket to an unspecified local
         * address that uses the same IP version as a remote address.
         *
         * # Examples
         * ```kotlin
         * import std.net.Ipv4Addr
         * import std.net.Ipv6Addr
         * import std.net.SocketAddr
         * import std.net.SocketAddrV4
         * import std.net.SocketAddrV6
         *
         * @feature(addrUnspecifiedFrom)
         * public fun doctestEntry() {
         *     val addr = SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 3000))
         *     check(SocketAddr.unspecifiedFrom(addr) == SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.UNSPECIFIED, 0)))
         *     val addr1 = SocketAddr.v6(SocketAddrV6.new(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 1), 443, 0x1234, 5))
         *     val unspecified = SocketAddr.unspecifiedFrom(addr1)
         *     check(unspecified.ip() == Ipv6Addr.UNSPECIFIED)
         *     if (unspecified is SocketAddr.V6) {
         *         val (v6) = unspecified
         *         check(v6.flowinfo() == 0x1234)
         *         check(v6.scopeId() == 5)
         *     }
         * }
         * ```
         */
        public fun unspecifiedFrom(thisRef: SocketAddr): SocketAddr =
            when (thisRef) {
                is V4 -> SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.UNSPECIFIED, 0u))
                is V6 -> {
                    val (addr) = thisRef
                    SocketAddr.v6(SocketAddrV6.new(Ipv6Addr.UNSPECIFIED, 0u, addr.flowinfo(), addr.scopeId()))
                }
            }

        /**
         * Converts a [SocketAddrV4] into a [SocketAddr.V4].
         */
        public fun from(sock4: SocketAddrV4): SocketAddr = SocketAddr.v4(sock4)

        /**
         * Converts a [SocketAddrV6] into a [SocketAddr.V6].
         */
        public fun from(sock6: SocketAddrV6): SocketAddr = SocketAddr.v6(sock6)

        /**
         * Converts a tuple struct (Into<[IpAddr]>, `UShort`) into a [SocketAddr].
         *
         * This conversion creates a [SocketAddr.V4] for an [IpAddr.V4]
         * and creates a [SocketAddr.V6] for an [IpAddr.V6].
         *
         * `UShort` is treated as port of the newly created [SocketAddr].
         */
        public fun from(pieces: Pair<IpAddr, UShort>): SocketAddr = SocketAddr.new(pieces.first.into(), pieces.second)
    }
}

/**
 * An IPv4 socket address.
 *
 * IPv4 socket addresses consist of an [`IPv4` address] and a 16-bit port number, as
 * stated in [IETF RFC 793].
 *
 * See [SocketAddr] for a type encompassing both IPv4 and IPv6 socket addresses.
 *
 * [IETF RFC 793]: https://tools.ietf.org/html/rfc793
 * [`IPv4` address]: Ipv4Addr
 *
 * # Portability
 *
 * `SocketAddrV4` is intended to be a portable representation of socket addresses and is likely not
 * the same as the internal socket address type used by the target operating system's API. Like all
 * `repr(Rust)` structs, however, its exact layout remains undefined and should not be relied upon
 * between builds.
 *
 * # Textual representation
 *
 * `SocketAddrV4` provides a [`FromStr`](str.FromStr) implementation.
 * It accepts an IPv4 address in its [textual representation], followed by a
 * single `:`, followed by the port encoded as a decimal integer.  Other
 * formats are not accepted.
 *
 * [textual representation]: Ipv4Addr#textual-representation
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.Ipv4Addr
 * import std.net.SocketAddrV4
 *
 * val socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
 * check("127.0.0.1:8080".parse() == Result.success(socket))
 * check(socket.ip() == Ipv4Addr.new(127, 0, 0, 1))
 * check(socket.port() == 8080)
 * ```
 */
public open class SocketAddrV4(
    public var ip: Ipv4Addr,
    public var port: UShort,
) {
    public open fun clone(): SocketAddrV4 = SocketAddrV4(ip, port)

    /**
     * Returns the IP address associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddrV4
     *
     * val socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
     * check(socket.ip() == Ipv4Addr.new(127, 0, 0, 1))
     * ```
     */
    public open fun ip(): Ipv4Addr = this.ip

    /**
     * Changes the IP address associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddrV4
     *
     * var socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
     * socket.setIp(Ipv4Addr.new(192, 168, 0, 1))
     * check(socket.ip() == Ipv4Addr.new(192, 168, 0, 1))
     * ```
     */
    public open fun setIp(newIp: Ipv4Addr) {
        this.ip = newIp
    }

    /**
     * Returns the port number associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddrV4
     *
     * val socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
     * check(socket.port() == 8080)
     * ```
     */
    public open fun port(): UShort = this.port

    /**
     * Changes the port number associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.SocketAddrV4
     *
     * var socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
     * socket.setPort(4242)
     * check(socket.port() == 4242)
     * ```
     */
    public open fun setPort(newPort: UShort) {
        this.port = newPort
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        // If there are no alignment requirements, write the socket address directly to `f`.
        // Otherwise, write it to a local buffer and then use `f.pad`.
        return if (f.precision() == null && f.width() == null) {
            f.writeFmt("${this.ip()}:${this.port()}")
        } else {
            val LONGEST_IPV4_SOCKET_ADDR: String = "255.255.255.255:65535"
            val buf = DisplayBuffer.buffer(LONGEST_IPV4_SOCKET_ADDR.len())
            val buf1 = DisplayBuffer.new(buf)
            buf1.writeFmt("${this.ip()}:${this.port()}").unwrap()
            run {
                f.append(buf1.asStr())
                Result.Ok(Unit)
            }
        }
    }

    public open fun fmtDebug(fmt: StringBuilder): Result<Unit, Unit> =
        run {
            fmt.append((this).toString())
            Result.Ok(Unit)
        }

    public companion object {
        /**
         * Creates a new socket address from an [`IPv4` address] and a port number.
         *
         * [`IPv4` address]: Ipv4Addr
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         * import std.net.SocketAddrV4
         *
         * val socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
         * ```
         */
        public fun new(ip: Ipv4Addr, port: UShort): SocketAddrV4 = SocketAddrV4(ip = ip, port = port)
    }
}

/**
 * An IPv6 socket address.
 *
 * IPv6 socket addresses consist of an [`IPv6` address], a 16-bit port number, as well
 * as fields containing the traffic class, the flow label, and a scope identifier
 * (see [IETF RFC 2553, Section 3.3] for more details).
 *
 * See [SocketAddr] for a type encompassing both IPv4 and IPv6 socket addresses.
 *
 * [IETF RFC 2553, Section 3.3]: https://tools.ietf.org/html/rfc2553#section-3.3
 * [`IPv6` address]: Ipv6Addr
 *
 * # Portability
 *
 * `SocketAddrV6` is intended to be a portable representation of socket addresses and is likely not
 * the same as the internal socket address type used by the target operating system's API. Like all
 * `repr(Rust)` structs, however, its exact layout remains undefined and should not be relied upon
 * between builds.
 *
 * # Textual representation
 *
 * `SocketAddrV6` provides a [`FromStr`](str.FromStr) implementation,
 * based on the bracketed format recommended by [IETF RFC 5952],
 * with scope identifiers based on those specified in [IETF RFC 4007].
 *
 * It accepts addresses consisting of the following elements, in order:
 *   - A left square bracket (`[`)
 *   - The [textual representation] of an IPv6 address
 *   - _Optionally_, a percent sign (`%`) followed by the scope identifier
 *     encoded as a decimal integer
 *   - A right square bracket (`]`)
 *   - A colon (`:`)
 *   - The port, encoded as a decimal integer.
 *
 * For example, the string `[2001: db8.413]: 443` represents a `SocketAddrV6`
 * with the address `2001: db8.413` and port `443`.  The string
 * `[2001: db8.413 % 612]: 443` represents the same address and port, with a
 * scope identifier of `612`.
 *
 * Other formats are not accepted.
 *
 * [IETF RFC 5952]: https://tools.ietf.org/html/rfc5952#section-6
 * [IETF RFC 4007]: https://tools.ietf.org/html/rfc4007#section-11
 * [textual representation]: Ipv6Addr#textual-representation
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.Ipv6Addr
 * import std.net.SocketAddrV6
 *
 * val socket = SocketAddrV6.new(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
 * check("[2001:db8::1]:8080".parse() == Result.success(socket))
 * check(socket.ip() == Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 1))
 * check(socket.port() == 8080)
 * var withScope = socket.clone()
 * withScope.setScopeId(3)
 * check("[2001:db8::1%3]:8080".parse() == Result.success(withScope))
 * ```
 */
public open class SocketAddrV6(
    public var ip: Ipv6Addr,
    public var port: UShort,
    public var flowinfo: UInt,
    public var scopeId: UInt,
) {
    public open fun clone(): SocketAddrV6 = SocketAddrV6(ip, port, flowinfo, scopeId)

    /**
     * Returns the IP address associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * val socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
     * check(socket.ip() == Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1))
     * ```
     */
    public open fun ip(): Ipv6Addr = this.ip

    /**
     * Changes the IP address associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * var socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
     * socket.setIp(Ipv6Addr.new(76, 45, 0, 0, 0, 0, 0, 0))
     * check(socket.ip() == Ipv6Addr.new(76, 45, 0, 0, 0, 0, 0, 0))
     * ```
     */
    public open fun setIp(newIp: Ipv6Addr) {
        this.ip = newIp
    }

    /**
     * Returns the port number associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * val socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
     * check(socket.port() == 8080)
     * ```
     */
    public open fun port(): UShort = this.port

    /**
     * Changes the port number associated with this socket address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * var socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
     * socket.setPort(4242)
     * check(socket.port() == 4242)
     * ```
     */
    public open fun setPort(newPort: UShort) {
        this.port = newPort
    }

    /**
     * Returns the flow information associated with this address.
     *
     * This information corresponds to the `sin6Flowinfo` field in C's `netinet / in.h`,
     * as specified in [IETF RFC 2553, Section 3.3].
     * It combines information about the flow label and the traffic class as specified
     * in [IETF RFC 2460], respectively [Section 6] and [Section 7].
     *
     * [IETF RFC 2553, Section 3.3]: https://tools.ietf.org/html/rfc2553#section-3.3
     * [IETF RFC 2460]: https://tools.ietf.org/html/rfc2460
     * [Section 6]: https://tools.ietf.org/html/rfc2460#section-6
     * [Section 7]: https://tools.ietf.org/html/rfc2460#section-7
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * val socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 10, 0)
     * check(socket.flowinfo() == 10)
     * ```
     */
    public open fun flowinfo(): UInt = this.flowinfo

    /**
     * Changes the flow information associated with this socket address.
     *
     * See [SocketAddrV6.flowinfo]s documentation for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * var socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 10, 0)
     * socket.setFlowinfo(56)
     * check(socket.flowinfo() == 56)
     * ```
     */
    public open fun setFlowinfo(newFlowinfo: UInt) {
        this.flowinfo = newFlowinfo
    }

    /**
     * Returns the scope ID associated with this address.
     *
     * This information corresponds to the `sin6ScopeId` field in C's `netinet / in.h`,
     * as specified in [IETF RFC 2553, Section 3.3].
     *
     * [IETF RFC 2553, Section 3.3]: https://tools.ietf.org/html/rfc2553#section-3.3
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * val socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 78)
     * check(socket.scopeId() == 78)
     * ```
     */
    public open fun scopeId(): UInt = this.scopeId

    /**
     * Changes the scope ID associated with this socket address.
     *
     * See [SocketAddrV6.scopeId]s documentation for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.SocketAddrV6
     *
     * var socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 78)
     * socket.setScopeId(42)
     * check(socket.scopeId() == 42)
     * ```
     */
    public open fun setScopeId(newScopeId: UInt) {
        this.scopeId = newScopeId
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        // If there are no alignment requirements, write the socket address directly to `f`.
        // Otherwise, write it to a local buffer and then use `f.pad`.
        return if (f.precision() == null && f.width() == null) {
            when (val matchVal = this.scopeId()) {
                0 -> f.writeFmt("[${this.ip()}]:${this.port()}")
                else -> {
                    val scopeId = matchVal
                    f.writeFmt("[${this.ip()}%$scopeId]:${this.port()}")
                }
            }
        } else {
            val LONGEST_IPV6_SOCKET_ADDR: String = "[ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff%4294967295]:65535"
            val buf = DisplayBuffer.buffer(LONGEST_IPV6_SOCKET_ADDR.len())
            val buf1 = DisplayBuffer.new(buf)
            when (val matchVal1 = this.scopeId()) {
                0 -> buf1.writeFmt("[${this.ip()}]:${this.port()}")
                else -> {
                    val scopeId1 = matchVal1
                    buf1.writeFmt("[${this.ip()}%$scopeId1]:${this.port()}")
                }
            }.unwrap()
            run {
                f.append(buf1.asStr())
                Result.Ok(Unit)
            }
        }
    }

    public open fun fmtDebug(fmt: StringBuilder): Result<Unit, Unit> =
        run {
            fmt.append((this).toString())
            Result.Ok(Unit)
        }

    public companion object {
        /**
         * Creates a new socket address from an [`IPv6` address], a 16-bit port number,
         * and the `flowinfo` and `scopeId` fields.
         *
         * For more information on the meaning and layout of the `flowinfo` and `scopeId`
         * parameters, see [IETF RFC 2553, Section 3.3].
         *
         * [IETF RFC 2553, Section 3.3]: https://tools.ietf.org/html/rfc2553#section-3.3
         * [`IPv6` address]: Ipv6Addr
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv6Addr
         * import std.net.SocketAddrV6
         *
         * val socket = SocketAddrV6.new(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
         * ```
         */
        public fun new(ip: Ipv6Addr, port: UShort, flowinfo: UInt, scopeId: UInt): SocketAddrV6 = SocketAddrV6(ip = ip, port = port, flowinfo = flowinfo, scopeId = scopeId)
    }
}
