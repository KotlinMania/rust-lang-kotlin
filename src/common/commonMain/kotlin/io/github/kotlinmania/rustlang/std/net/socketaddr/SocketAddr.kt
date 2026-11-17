// port-lint: source net/socket_addr.rs
package io.github.kotlinmania.rustlang.std.net.socketaddr

import io.github.kotlinmania.rustlang.core.net.ipaddr.IpAddr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV6
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Iter
import io.github.kotlinmania.rustlang.std.sys.net.connection.lookupHostString
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.lookupHost

/**
 * A trait for objects which can be converted or resolved to one or more
 * [SocketAddr] values.
 *
 * This trait is used for generic address resolution when constructing network
 * objects. By default it is implemented for the following types:
 *
 *  * [SocketAddr]: [toSocketAddrs] is the identity function.
 *
 *  * [SocketAddrV4], [SocketAddrV6], <code>([IpAddr], [u16])</code>,
 *    <code>([Ipv4Addr], [u16])</code>, <code>([Ipv6Addr], [u16])</code>:
 *    [toSocketAddrs] constructs a [SocketAddr] trivially.
 *
 *  * <code>(&[str], [u16])</code>: <code>&[str]</code> should be either a string representation
 *    of an [IpAddr] address as expected by [FromStr] implementation or a host
 *    name. [u16] is the port number.
 *
 *  * <code>&[str]</code>: the string should be either a string representation of a
 *    [SocketAddr] as expected by its [FromStr] implementation or a string like
 *    `<hostName>: <port>` pair where `<port>` is a [u16] value.
 *
 *  * <code>&[[SocketAddr]]</code>: all [SocketAddr] values in the slice will be used.
 *
 * This trait allows constructing network objects like [TcpStream] or
 * [UdpSocket] easily with values of various types for the bind/connection
 * address. It is needed because sometimes one type is more appropriate than
 * the other: for simple uses a string like `"localhost:12345"` is much nicer
 * than manual construction of the corresponding [SocketAddr], but sometimes
 * [SocketAddr] value is *the* main source of the address, and converting it to
 * some other type (e.g., a string) just for it to be converted back to
 * [SocketAddr] in constructor methods is pointless.
 *
 * Addresses returned by the operating system that are not IP addresses are
 * silently ignored.
 *
 * [toSocketAddrs]: ToSocketAddrs.toSocketAddrs
 *
 * # Examples
 *
 * Creating a [SocketAddr] iterator that yields one item:
 *
 * ```kotlin
 * import std.net.SocketAddr
 * import std.net.ToSocketAddrs
 *
 * val addr = SocketAddr.from(Pair(listOf(127, 0, 0, 1), 443))
 * var addrsIter = addr.toSocketAddrs().unwrap()
 * check(addr == addrsIter.next())
 * check(addrsIter.next() == null)
 * ```
 *
 * Creating a [SocketAddr] iterator from a hostname:
 *
 * ```kotlin
 * import std.net.SocketAddr
 * import std.net.ToSocketAddrs
 *
 * // assuming `localhost` resolves to 127.0.0.1
 * var addrsIter = "localhost:443".toSocketAddrs().unwrap()
 * check(addrsIter.next() == SocketAddr.from(Pair(listOf(127, 0, 0, 1), 443)))
 * check(addrsIter.next() == null)
 * // assuming `foo` does not resolve
 * check("foo:443".toSocketAddrs().isErr())
 * ```
 *
 * Creating a [SocketAddr] iterator that yields multiple items:
 *
 * ```kotlin
 * import std.net.SocketAddr
 * import std.net.ToSocketAddrs
 *
 * val addr1 = SocketAddr.from(Pair(listOf(0, 0, 0, 0), 80))
 * val addr2 = SocketAddr.from(Pair(listOf(127, 0, 0, 1), 443))
 * val addrs = mutableListOf(addr1, addr2)
 * var addrsIter = (addrs).toSocketAddrs().unwrap()
 * check(addr1 == addrsIter.next())
 * check(addr2 == addrsIter.next())
 * check(addrsIter.next() == null)
 * ```
 *
 * Attempting to create a [SocketAddr] iterator from an improperly formatted
 * socket address `String` (missing the port):
 *
 * ```kotlin
 * import std.io
 * import std.net.ToSocketAddrs
 *
 * val err = "127.0.0.1".toSocketAddrs().unwrapErr()
 * check(err.kind() == io.ErrorKind.InvalidInput)
 * ```
 *
 * [TcpStream.connect] is an example of a function that utilizes
 * `ToSocketAddrs` as a trait bound on its parameter in order to accept
 * different types:
 *
 * ```kotlin
 * import std.net.Ipv4Addr
 * import std.net.TcpStream
 *
 * val stream = TcpStream.connect(Pair("127.0.0.1", 443))
 * // or
 * val stream1 = TcpStream.connect("127.0.0.1:443")
 * // or
 * val stream2 = TcpStream.connect(Pair(Ipv4Addr.new(127, 0, 0, 1), 443))
 * ```
 */
public interface ToSocketAddrs<out Iter> {
    /**
     * Converts this object to an iterator of resolved [SocketAddr]s.
     *
     * The returned iterator might not actually yield any values depending on the
     * outcome of any resolution performed.
     *
     * Note that this function may block the current thread while resolution is
     * performed.
     */
    public fun toSocketAddrs(): Result<Iter, Error>
}

public fun SocketAddr.toSocketAddrs(): Result<ArrayDeque<SocketAddr>, Error> = Result.Ok<ArrayDeque<SocketAddr>, Error>(this.intoIter())

public fun SocketAddrV4.toSocketAddrs(): Result<ArrayDeque<SocketAddr>, Error> = SocketAddr.v4(this).toSocketAddrs()

public fun SocketAddrV6.toSocketAddrs(): Result<ArrayDeque<SocketAddr>, Error> = SocketAddr.v6(this).toSocketAddrs()

public fun Pair<IpAddr, UShort>.toSocketAddrs(): Result<ArrayDeque<SocketAddr>, Error> {
    val (ip, port) = this
    return when (ip) {
        is IpAddr.V4 -> {
            val (a) = ip
            Pair(a, port).toSocketAddrs()
        }
        is IpAddr.V6 -> {
            val (a1) = ip
            Pair(a1, port).toSocketAddrs()
        }
    }
}

public fun Pair<Ipv4Addr, UShort>.toSocketAddrsFromIpv4AddrUShort(): Result<ArrayDeque<SocketAddr>, Error> {
    val (ip, port) = this
    return SocketAddrV4.new(ip, port).toSocketAddrs()
}

public fun Pair<Ipv6Addr, UShort>.toSocketAddrsFromIpv6AddrUShort(): Result<ArrayDeque<SocketAddr>, Error> {
    val (ip, port) = this
    return SocketAddrV6.new(ip, port, 0, 0).toSocketAddrs()
}

public fun Pair<String, UShort>.toSocketAddrsFromStringUShort(): Result<ArrayDeque<SocketAddr>, Error> {
    val (host, port) = this
    // Try to parse the host as a regular IP address first
    when (val matchVal = host.parse<IpAddr>()) {
        is Result.Ok -> {
            val addr = matchVal.value
            val addr1 = SocketAddr.new(addr, port)
            return Result.Ok<ArrayDeque<SocketAddr>, Error>(mutableListOf(addr1).intoIter())
        }
        else -> {}
    }
    // Otherwise, make the system look it up.
    return lookupHost(host, port).map({ addrs -> Vec.fromIter(addrs).intoIter() })
}

// accepts strings like localhost:12345'
public fun String.toSocketAddrs(): Result<ArrayDeque<SocketAddr>, Error> {
    // Try to parse as a regular SocketAddr first
    when (val matchVal = this.parse()) {
        is Result.Ok -> {
            val addr = matchVal.value
            return Result.Ok<ArrayDeque<SocketAddr>, Error>(mutableListOf(addr).intoIter())
        }
        else -> {}
    }
    // Otherwise, make the system look it up.
    return lookupHostString(this).map({ addrs -> Vec.fromIter(addrs).intoIter() })
}

public fun List<SocketAddr>.toSocketAddrs(): Result<Cloned<ArrayDeque<SocketAddr>>, Error> = Result.Ok<Cloned<ArrayDeque<SocketAddr>>, Error>(this.iter().cloned())
