// port-lint: source net/parser.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.net.parser

import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.net.ipaddr.IpAddr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV6
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.splitFirst

// A private parser implementation of IPv4, IPv6, and socket addresses.
//
// This module is "publicly exported" through the `FromStr` implementations
// below.

public interface ReadNumberHelper<T> {
    public val ZERO: T

    public fun checkedMul(other: UInt): T?

    public fun checkedAdd(other: UInt): T?
}

public val ZERO: UByte = 0u

public fun UByte.checkedMul(other: UInt): UByte? {
    return UByte.checkedMul(this, (other.tryInto().ok() ?: return null))
}

public fun UByte.checkedAdd(other: UInt): UByte? {
    return UByte.checkedAdd(this, (other.tryInto().ok() ?: return null))
}

public val ZERO1: UShort = 0u

public fun UShort.checkedMul(other: UInt): UShort? {
    return UShort.checkedMul(this, (other.tryInto().ok() ?: return null))
}

public fun UShort.checkedAdd(other: UInt): UShort? {
    return UShort.checkedAdd(this, (other.tryInto().ok() ?: return null))
}

public val ZERO2: UInt = 0u

public fun UInt.checkedMul(other: UInt): UInt? {
    return UInt.checkedMul(this, (other.tryInto().ok() ?: return null))
}

public fun UInt.checkedAdd(other: UInt): UInt? {
    return UInt.checkedAdd(this, (other.tryInto().ok() ?: return null))
}

public open class Parser(
    // Parsing as ASCII, so can use byte array.
    public var state: List<UByte>,
) {
    /**
     * Run a parser, and restore the pre-parse state if it fails.
     */
    public open fun <T> readAtomically(inner: (Parser) -> T?): T? {
        val state = this.state
        val result = inner(this)
        if (result == null) {
            this.state = state
        }
        return result
    }

    /**
     * Run a parser, but fail if the entire input wasn't consumed.
     * Doesn't run atomically.
     */
    public open fun <T> parseWith(inner: (Parser) -> T?, kind: AddrKind): Result<T, AddrParseError> {
        val result = inner(this)
        return ((if (this.state.isEmpty()) {
            result
        } else {
            null
        })?.let { Result.Ok<T, AddrParseError>(it) } ?: Result.Err<T, AddrParseError>(AddrParseError(kind)))
    }

    /**
     * Reads the next character from the input
     */
    public open fun readChar(): Char? =
        this.state.splitFirst().map({ (b, tail) ->
            this.state = tail
            Char.from(b)
        })

    /**
     * Reads the next character from the input if it matches the target.
     */
    public open fun readGivenChar(target: Char): Unit? =
        this.readAtomically({ p ->
            p.readChar().andThen({ c -> if (c == target) {} else {
                null
            } })
        })

    /**
     * Helper for reading separators in an indexed loop. Reads the separator
     * character iff index > 0, then runs the parser. When used in a loop,
     * the separator character will only be read on index > 0 (see
     * readIpv4Addr for an example)
     */
    public open fun <T> readSeparator(sep: Char, index: Int, inner: (Parser) -> T?): T? {
        return this.readAtomically(closure@{ p ->
            if (index > 0) {
                when (val tryResult = p.readGivenChar(sep)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
            }
            inner(p)
        })
    }

    /**
     * Reads a number off the front of the input in the given radix, stopping at the first
     * non-digit character or eof. Fails if the number has more digits than `maxDigits`, if there
     * is no number, if the number overflows `T`, or if there are leading zeros but
     * `allowZeroPrefix` is false.
     *
     * `maxDigits` must be in 1..=6.
     */
    public open fun <T : ReadNumberHelper<T>> readRadixMaxDigits(radix: UInt, maxDigits: UInt, allowZeroPrefix: Boolean): T? {
        if (false) {
            check(1u <= maxDigits)
        }
        if (false) {
            check(maxDigits <= 6u)
        }
        // Works for any radix in u32
        return this.readAtomically(closure1@{ p ->
            val first = when (val tryResult = when (val tryResult = p.readChar()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }.toDigit(radix)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }
            var result = first
            var digitCount = 1
            while (true) {
                val matchVal = p.readAtomically(closure2@{ p1 -> when (val tryResult = p1.readChar()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure2 Result.Err(tryResult.errValue()) }.toDigit(radix) })
                if (matchVal == null) break
                val digit = matchVal
                if (digitCount >= maxDigits) {
                    return@closure1 null
                }
                result *= radix
                result += digit
                digitCount += 1
            }
            if (!allowZeroPrefix && first == 0 && digitCount > 1) {
                null
            } else {
                result.tryInto().ok()
            }
        })
    }

    /**
     * Reads a decimal number off the front of the input, stopping at the first non-digit character
     * or eof. Fails if there is no number, or if the number overflows `T`. Allows an arbitrary
     * amount of leading zeros.
     */
    public open fun <T : ReadNumberHelper<T>> readDecimal(): T? {
        return this.readAtomically(closure3@{ p ->
            val first = when (val tryResult = when (val tryResult = p.readChar()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }.toDigit(10u)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
            var result = when (val tryResult = T.ZERO.checkedAdd(first)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
            while (true) {
                val matchVal = p.readAtomically(closure4@{ p1 -> when (val tryResult = p1.readChar()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }.toDigit(10u) })
                if (matchVal == null) break
                val digit = matchVal
                result = when (val tryResult = result.checkedMul(10)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
                result = when (val tryResult = result.checkedAdd(digit)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.errValue()) }
            }
            result
        })
    }

    /**
     * Reads an IPv4 address.
     */
    public open fun readIpv4Addr(): Ipv4Addr? {
        return this.readAtomically(closure5@{ p ->
            var groups = MutableList(4) { 0 }
            for ((i, slot) in ArrayDeque(groups).mapIndexed { index, item -> Pair(index, item) }) {
                slot = when (val tryResult = p.readSeparator('.', i, { p1 ->
                    // Disallow octal number in IP string.
                    // https://tools.ietf.org/html/rfc6943#section-3.1.1
                    p1.readRadixMaxDigits(10, 3, false)
                })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure5 Result.Err(tryResult.errValue()) }
            }
            groups.into()
        })
    }

    /**
     * Reads an IPv6 address.
     */
    public open fun readIpv6Addr(): Ipv6Addr? {
        /**
         * Read a chunk of an IPv6 address into `groups`. Returns the number
         * of groups read, along with a bool indicating if an embedded
         * trailing IPv4 address was read. Specifically, read a series of
         * colon-separated IPv6 groups (0x0000 - 0xFFFF), with an optional
         * trailing embedded IPv4 address.
         */
        fun readGroups(p: Parser, groups: MutableList<UShort>): Pair<Int, Boolean> {
            val limit = groups.size
            for ((i, slot) in groups.mapIndexed { index, item -> Pair(index, item) }) {
                // Try to read a trailing embedded IPv4 address. There must be
                // at least two groups left.
                if (i < limit - 1) {
                    val ipv4 = p.readSeparator(':', i, { p1 -> p1.readIpv4Addr() })
                    if (ipv4 != null) {
                        val v4Addr = ipv4
                        val (one, two, three, four) = v4Addr.octets()
                        groups[i + 0] = run { val bytesTmp6 = ubyteArrayOf(one, two); ((bytesTmp6[0].toInt() shl 8) or bytesTmp6[1].toInt()).toUShort() }
                        groups[i + 1] = run { val bytesTmp7 = ubyteArrayOf(three, four); ((bytesTmp7[0].toInt() shl 8) or bytesTmp7[1].toInt()).toUShort() }
                        return Pair(i + 2, true)
                    }
                }
                val group = p.readSeparator(':', i, { p1 -> p1.readRadixMaxDigits(16, 4, true) })
                when (group) {
                    null -> { return Pair(i, false) }
                    else -> {
                        val g = group
                        slot = g
                    }
                }
            }
            return Pair(groups.size, false)
        }
        return this.readAtomically(closure8@{ p ->
            // Read the front part of the address; either the whole thing, or up
            // to the first ::
            var head = MutableList(8) { 0 }
            val (headSize, headIpv4) = readGroups(p, head)
            if (headSize == 8) {
                return@closure8 head.into()
            }
            // IPv4 part is not allowed before `.`
            if (headIpv4) {
                return@closure8 null
            }
            // Read `.` if previous code parsed less than 8 groups.
            // `.` indicates one or more groups of 16 bits of zeros.
            when (val tryResult = p.readGivenChar(':')) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
            when (val tryResult = p.readGivenChar(':')) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
            // Read the back part of the address. The :: must contain at least one
            // set of zeroes, so our max length is 7.
            var tail = MutableList(7) { 0 }
            val limit = 8 - (headSize + 1)
            val (tailSize, _) = readGroups(p, tail.subList(0, limit))
            // Concat the head and tail of the IP address
            run { val dstTmp9 = head; val srcTmp9 = tail[0 until tailSize]; for (idxTmp9 in srcTmp9.indices) dstTmp9[((8 - tailSize)) + idxTmp9] = srcTmp9[idxTmp9] }
            head.into()
        })
    }

    /**
     * Reads an IP address, either IPv4 or IPv6.
     */
    public open fun readIpAddr(): IpAddr? = (this.readIpv4Addr()?.let(IpAddr.V4) ?: this.readIpv6Addr()?.let(IpAddr.V6))

    /**
     * Reads a `:` followed by a port in base 10.
     */
    public open fun readPort(): UShort? {
        return this.readAtomically(closure10@{ p ->
            when (val tryResult = p.readGivenChar(':')) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure10 Result.Err(tryResult.errValue()) }
            p.readDecimal()
        })
    }

    /**
     * Reads a `%` followed by a scope ID in base 10.
     */
    public open fun readScopeId(): UInt? {
        return this.readAtomically(closure11@{ p ->
            when (val tryResult = p.readGivenChar('%')) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure11 Result.Err(tryResult.errValue()) }
            p.readDecimal()
        })
    }

    /**
     * Reads an IPv4 address with a port.
     */
    public open fun readSocketAddrV4(): SocketAddrV4? {
        return this.readAtomically(closure12@{ p ->
            val ip = when (val tryResult = p.readIpv4Addr()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure12 Result.Err(tryResult.errValue()) }
            val port = when (val tryResult = p.readPort()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure12 Result.Err(tryResult.errValue()) }
            SocketAddrV4.new(ip, port)
        })
    }

    /**
     * Reads an IPv6 address with a port.
     */
    public open fun readSocketAddrV6(): SocketAddrV6? {
        return this.readAtomically(closure13@{ p ->
            when (val tryResult = p.readGivenChar('[')) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure13 Result.Err(tryResult.errValue()) }
            val ip = when (val tryResult = p.readIpv6Addr()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure13 Result.Err(tryResult.errValue()) }
            val scopeId = p.readScopeId().unwrapOr(0)
            when (val tryResult = p.readGivenChar(']')) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure13 Result.Err(tryResult.errValue()) }
            val port = when (val tryResult = p.readPort()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure13 Result.Err(tryResult.errValue()) }
            SocketAddrV6.new(ip, port, 0u, scopeId)
        })
    }

    /**
     * Reads an IP address with a port.
     */
    public open fun readSocketAddr(): SocketAddr? = (this.readSocketAddrV4()?.let(SocketAddr.V4) ?: this.readSocketAddrV6()?.let(SocketAddr.V6))

    public companion object {
        public fun new(input: List<UByte>): Parser = Parser(state = input)
    }
}

/**
 * Parse an IP address from a slice of bytes.
 *
 * ```kotlin
 * import std.net.IpAddr
 * import std.net.Ipv4Addr
 * import std.net.Ipv6Addr
 *
 * @feature(addrParseAscii)
 * public fun doctestEntry() {
 *     val localhostV4 = IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1))
 *     val localhostV6 = IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1))
 *     check(IpAddr.parseAscii("127.0.0.1".encodeToByteArray()) == Result.success(localhostV4))
 *     check(IpAddr.parseAscii("::1".encodeToByteArray()) == Result.success(localhostV6))
 * }
 * ```
 */
public fun IpAddr.Companion.parseAscii(b: List<UByte>): Result<IpAddr, AddrParseError> = Parser.new(b).parseWith({ p -> p.readIpAddr() }, AddrKind.Ip)

public fun fromStrIpAddr(s: String): Result<IpAddr, AddrParseError> = IpAddr.parseAscii(s.encodeToByteArray().map { it.toUByte() })

/**
 * Parse an IPv4 address from a slice of bytes.
 *
 * ```kotlin
 * import std.net.Ipv4Addr
 *
 * @feature(addrParseAscii)
 * public fun doctestEntry() {
 *     val localhost = Ipv4Addr.new(127, 0, 0, 1)
 *     check(Ipv4Addr.parseAscii("127.0.0.1".encodeToByteArray()) == Result.success(localhost))
 * }
 * ```
 */
public fun Ipv4Addr.Companion.parseAscii(b: List<UByte>): Result<Ipv4Addr, AddrParseError> {
    // don't try to parse if too long
    return if (b.size > 15) {
        Result.Err<Ipv4Addr, AddrParseError>(AddrParseError(AddrKind.Ipv4))
    } else {
        Parser.new(b).parseWith({ p -> p.readIpv4Addr() }, AddrKind.Ipv4)
    }
}

public fun fromStrIpv4Addr(s: String): Result<Ipv4Addr, AddrParseError> = Ipv4Addr.parseAscii(s.encodeToByteArray().map { it.toUByte() })

/**
 * Parse an IPv6 address from a slice of bytes.
 *
 * ```kotlin
 * import std.net.Ipv6Addr
 *
 * @feature(addrParseAscii)
 * public fun doctestEntry() {
 *     val localhost = Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1)
 *     check(Ipv6Addr.parseAscii("::1".encodeToByteArray()) == Result.success(localhost))
 * }
 * ```
 */
public fun Ipv6Addr.Companion.parseAscii(b: List<UByte>): Result<Ipv6Addr, AddrParseError> = Parser.new(b).parseWith({ p -> p.readIpv6Addr() }, AddrKind.Ipv6)

public fun fromStrIpv6Addr(s: String): Result<Ipv6Addr, AddrParseError> = Ipv6Addr.parseAscii(s.encodeToByteArray().map { it.toUByte() })

/**
 * Parse an IPv4 socket address from a slice of bytes.
 *
 * ```kotlin
 * import std.net.Ipv4Addr
 * import std.net.SocketAddrV4
 *
 * @feature(addrParseAscii)
 * public fun doctestEntry() {
 *     val socket = SocketAddrV4.new(Ipv4Addr.new(127, 0, 0, 1), 8080)
 *     check(SocketAddrV4.parseAscii("127.0.0.1:8080".encodeToByteArray()) == Result.success(socket))
 * }
 * ```
 */
public fun SocketAddrV4.Companion.parseAscii(b: List<UByte>): Result<SocketAddrV4, AddrParseError> = Parser.new(b).parseWith({ p -> p.readSocketAddrV4() }, AddrKind.SocketV4)

public fun fromStrSocketAddrV4(s: String): Result<SocketAddrV4, AddrParseError> = SocketAddrV4.parseAscii(s.encodeToByteArray().map { it.toUByte() })

/**
 * Parse an IPv6 socket address from a slice of bytes.
 *
 * ```kotlin
 * import std.net.Ipv6Addr
 * import std.net.SocketAddrV6
 *
 * @feature(addrParseAscii)
 * public fun doctestEntry() {
 *     val socket = SocketAddrV6.new(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 1), 8080, 0, 0)
 *     check(SocketAddrV6.parseAscii("[2001:db8::1]:8080".encodeToByteArray()) == Result.success(socket))
 * }
 * ```
 */
public fun SocketAddrV6.Companion.parseAscii(b: List<UByte>): Result<SocketAddrV6, AddrParseError> = Parser.new(b).parseWith({ p -> p.readSocketAddrV6() }, AddrKind.SocketV6)

public fun fromStrSocketAddrV6(s: String): Result<SocketAddrV6, AddrParseError> = SocketAddrV6.parseAscii(s.encodeToByteArray().map { it.toUByte() })

/**
 * Parse a socket address from a slice of bytes.
 *
 * ```kotlin
 * import std.net.IpAddr
 * import std.net.Ipv4Addr
 * import std.net.Ipv6Addr
 * import std.net.SocketAddr
 *
 * @feature(addrParseAscii)
 * public fun doctestEntry() {
 *     val socketV4 = SocketAddr.new(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)), 8080)
 *     val socketV6 = SocketAddr.new(IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1)), 8080)
 *     check(SocketAddr.parseAscii("127.0.0.1:8080".encodeToByteArray()) == Result.success(socketV4))
 *     check(SocketAddr.parseAscii("[::1]:8080".encodeToByteArray()) == Result.success(socketV6))
 * }
 * ```
 */
public fun SocketAddr.Companion.parseAscii(b: List<UByte>): Result<SocketAddr, AddrParseError> = Parser.new(b).parseWith({ p -> p.readSocketAddr() }, AddrKind.Socket)

public fun fromStrSocketAddr(s: String): Result<SocketAddr, AddrParseError> = SocketAddr.parseAscii(s.encodeToByteArray().map { it.toUByte() })

public sealed class AddrKind {
    public data object Ip : AddrKind()
    public data object Ipv4 : AddrKind()
    public data object Ipv6 : AddrKind()
    public data object Socket : AddrKind()
    public data object SocketV4 : AddrKind()
    public data object SocketV6 : AddrKind()

    public fun rustEquals(other: AddrKind): Boolean = this == other
}

/**
 * An error which can be returned when parsing an IP address or a socket address.
 *
 * This error is used as the error type for the [FromStr] implementation for
 * [IpAddr], [Ipv4Addr], [Ipv6Addr], [SocketAddr], [SocketAddrV4], and
 * [SocketAddrV6].
 *
 * # Potential causes
 *
 * `AddrParseError` may be thrown because the provided string does not parse as the given type,
 * often because it includes information only handled by a different address type.
 *
 * ```kotlin
 * import std.net.IpAddr
 *
 * val foo: IpAddr = "127.0.0.1:8080".parse(IpAddr.parse).expect("Cannot handle the socket port")
 * ```
 *
 * [IpAddr] doesn't handle the port. Use [SocketAddr] instead.
 *
 * ```kotlin
 * import std.net.SocketAddr
 *
 * // No problem, the `panic` message has disappeared.
 * val foo: SocketAddr = "127.0.0.1:8080".parse(SocketAddr.parse).expect("`parse` should succeed")
 * ```
 */
public open class AddrParseError(
    public var value: AddrKind,
) {
    public var field0: AddrKind
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): AddrParseError = AddrParseError(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this.value) {
            AddrKind.Ip -> "invalid IP address syntax"
            AddrKind.Ipv4 -> "invalid IPv4 address syntax"
            AddrKind.Ipv6 -> "invalid IPv6 address syntax"
            AddrKind.Socket -> "invalid socket address syntax"
            AddrKind.SocketV4 -> "invalid IPv4 socket address syntax"
            AddrKind.SocketV6 -> "invalid IPv6 socket address syntax"
        }.fmt(f)
}
