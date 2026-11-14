// port-lint: source net/ip_addr.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.net.ipaddr

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.intrinsics.transmute
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.net.displaybuffer.DisplayBuffer
import io.github.kotlinmania.rustlang.core.ops.bit.BitAnd
import io.github.kotlinmania.rustlang.core.ops.bit.BitAndAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.BitOrAssign
import io.github.kotlinmania.rustlang.core.ops.bit.Not
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An IP address, either IPv4 or IPv6.
 *
 * This enum can contain either an [Ipv4Addr] or an [Ipv6Addr], see their
 * respective documentation for more details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.IpAddr
 * import std.net.Ipv4Addr
 * import std.net.Ipv6Addr
 *
 * val localhostV4 = IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1))
 * val localhostV6 = IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1))
 * check("127.0.0.1".parse() == Result.success(localhostV4))
 * check("::1".parse() == Result.success(localhostV6))
 * check(localhostV4.isIpv6() == false)
 * check(localhostV4.isIpv4() == true)
 * ```
 */
public sealed class IpAddr {
    /**
     * An IPv4 address.
     */
    public data class V4(public var value: Ipv4Addr) : IpAddr()
    /**
     * An IPv6 address.
     */
    public data class V6(public var value: Ipv6Addr) : IpAddr()

    public fun rustEquals(other: IpAddr): Boolean = this == other

    /**
     * Returns [trueVal] for the special 'unspecified' address.
     *
     * See the documentation for [Ipv4Addr.isUnspecified] and
     * [Ipv6Addr.isUnspecified] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(IpAddr.v4(Ipv4Addr.new(0, 0, 0, 0)).isUnspecified() == true)
     * check(IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 0)).isUnspecified() == true)
     * ```
     */
    public open fun isUnspecified(): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.isUnspecified()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.isUnspecified()
            }
        }

    /**
     * Returns [trueVal] if this is a loopback address.
     *
     * See the documentation for [Ipv4Addr.isLoopback] and
     * [Ipv6Addr.isLoopback] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)).isLoopback() == true)
     * check(IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 0x1)).isLoopback() == true)
     * ```
     */
    public open fun isLoopback(): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.isLoopback()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.isLoopback()
            }
        }

    /**
     * Returns [trueVal] if the address appears to be globally routable.
     *
     * See the documentation for [Ipv4Addr.isGlobal] and
     * [Ipv6Addr.isGlobal] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(IpAddr.v4(Ipv4Addr.new(80, 9, 12, 3)).isGlobal() == true)
     *     check(IpAddr.v6(Ipv6Addr.new(0, 0, 0x1c9, 0, 0, 0xafc8, 0, 0x1)).isGlobal() == true)
     * }
     * ```
     */
    public open fun isGlobal(): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.isGlobal()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.isGlobal()
            }
        }

    /**
     * Returns [trueVal] if this is a multicast address.
     *
     * See the documentation for [Ipv4Addr.isMulticast] and
     * [Ipv6Addr.isMulticast] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(IpAddr.v4(Ipv4Addr.new(224, 254, 0, 0)).isMulticast() == true)
     * check(IpAddr.v6(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0)).isMulticast() == true)
     * ```
     */
    public open fun isMulticast(): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.isMulticast()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.isMulticast()
            }
        }

    /**
     * Returns [trueVal] if this address is in a range designated for documentation.
     *
     * See the documentation for [Ipv4Addr.isDocumentation] and
     * [Ipv6Addr.isDocumentation] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(IpAddr.v4(Ipv4Addr.new(203, 0, 113, 6)).isDocumentation() == true)
     *     check(IpAddr.v6(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0)).isDocumentation() == true)
     * }
     * ```
     */
    public open fun isDocumentation(): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.isDocumentation()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.isDocumentation()
            }
        }

    /**
     * Returns [trueVal] if this address is in a range designated for benchmarking.
     *
     * See the documentation for [Ipv4Addr.isBenchmarking] and
     * [Ipv6Addr.isBenchmarking] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(IpAddr.v4(Ipv4Addr.new(198, 19, 255, 255)).isBenchmarking() == true)
     *     check(IpAddr.v6(Ipv6Addr.new(0x2001, 0x2, 0, 0, 0, 0, 0, 0)).isBenchmarking() == true)
     * }
     * ```
     */
    public open fun isBenchmarking(): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.isBenchmarking()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.isBenchmarking()
            }
        }

    /**
     * Returns [trueVal] if this address is an [`IPv4` address], and [falseVal]
     * otherwise.
     *
     * [`IPv4` address]: IpAddr.V4
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(IpAddr.v4(Ipv4Addr.new(203, 0, 113, 6)).isIpv4() == true)
     * check(IpAddr.v6(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0)).isIpv4() == false)
     * ```
     */
    public open fun isIpv4(): Boolean =
        when (this) {
            is IpAddr.V4 -> true
            else -> false
        }

    /**
     * Returns [trueVal] if this address is an [`IPv6` address], and [falseVal]
     * otherwise.
     *
     * [`IPv6` address]: IpAddr.V6
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(IpAddr.v4(Ipv4Addr.new(203, 0, 113, 6)).isIpv6() == false)
     * check(IpAddr.v6(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0)).isIpv6() == true)
     * ```
     */
    public open fun isIpv6(): Boolean =
        when (this) {
            is IpAddr.V6 -> true
            else -> false
        }

    /**
     * Converts this address to an `IpAddr.V4` if it is an IPv4-mapped IPv6
     * address, otherwise returns `self` as-is.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * val localhostV4 = Ipv4Addr.new(127, 0, 0, 1)
     * check(IpAddr.v4(localhostV4).toCanonical() == localhostV4)
     * check(IpAddr.v6(localhostV4.toIpv6Mapped()).toCanonical() == localhostV4)
     * check(IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1)).toCanonical().isLoopback() == true)
     * check(IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0x7f00, 0x1)).isLoopback() == false)
     * check(IpAddr.v6(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0x7f00, 0x1)).toCanonical().isLoopback() == true)
     * ```
     */
    public open fun toCanonical(): IpAddr =
        when (this) {
            is IpAddr.V4 -> this
            is IpAddr.V6 -> {
                val (v6) = this
                v6.toCanonical()
            }
        }

    /**
     * Returns the eight-bit integers this address consists of as a slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.IpAddr
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * @feature(ipAsOctets)
     * public fun doctestEntry() {
     *     check(IpAddr.v4(Ipv4Addr.LOCALHOST).asOctets() == listOf(127, 0, 0, 1))
     *     check(IpAddr.v6(Ipv6Addr.LOCALHOST).asOctets() == listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1))
     * }
     * ```
     */
    public open fun asOctets(): List<UByte> =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                ip.asOctets().asSlice()
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                ip1.asOctets().asSlice()
            }
        }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> =
        when (this) {
            is IpAddr.V4 -> {
                val (ip) = this
                run { ip.fmt(fmt); Result.Ok(Unit) }
            }
            is IpAddr.V6 -> {
                val (ip1) = this
                run { ip1.fmt(fmt); Result.Ok(Unit) }
            }
        }

    public open fun fmtDebug(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append((this).toString()); Result.Ok(Unit) }

    public open fun eq(other: Ipv4Addr): Boolean =
        when (this) {
            is IpAddr.V4 -> {
                val (v4) = this
                v4 == other
            }
            is IpAddr.V6 -> false
        }

    public open fun partialCmp(other: Ipv4Addr): Ordering? =
        when (this) {
            is IpAddr.V4 -> {
                val (v4) = this
                v4.partialCmp(other)
            }
            is IpAddr.V6 -> Ordering.Greater
        }

    public open fun eq(other: Ipv6Addr): Boolean =
        when (this) {
            is IpAddr.V4 -> false
            is IpAddr.V6 -> {
                val (v6) = this
                v6 == other
            }
        }

    public open fun partialCmp(other: Ipv6Addr): Ordering? =
        when (this) {
            is IpAddr.V4 -> Ordering.Less
            is IpAddr.V6 -> {
                val (v6) = this
                v6.partialCmp(other)
            }
        }

    public companion object {
        /**
         * Returns the unspecified IP address for the same IP version.
         *
         * Returns `0.0.0.0` for IPv4 and `.` for IPv6.
         *
         * Use this method when you must bind a socket to an unspecified local
         * address that uses the same IP version as a remote address.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.IpAddr
         * import std.net.Ipv4Addr
         * import std.net.Ipv6Addr
         *
         * @feature(addrUnspecifiedFrom)
         * public fun doctestEntry() {
         *     val ipv4 = IpAddr.v4(Ipv4Addr.new(127, 0, 0, 1))
         *     check(IpAddr.unspecifiedFrom(ipv4) == IpAddr.v4(Ipv4Addr.UNSPECIFIED))
         *     val ipv6 = IpAddr.v6(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 1))
         *     check(IpAddr.unspecifiedFrom(ipv6) == IpAddr.v6(Ipv6Addr.UNSPECIFIED))
         * }
         * ```
         */
        public fun unspecifiedFrom(thisRef: IpAddr): IpAddr =
            when (thisRef) {
                is V4 -> IpAddr.v4(Ipv4Addr.UNSPECIFIED)
                is V6 -> IpAddr.v6(Ipv6Addr.UNSPECIFIED)
            }

        /**
         * Copies this address to a new `IpAddr.V4`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.IpAddr
         * import std.net.Ipv4Addr
         *
         * val addr = Ipv4Addr.new(127, 0, 0, 1)
         * check(IpAddr.v4(addr) == IpAddr.from(addr))
         * ```
         */
        public fun from(ipv4: Ipv4Addr): IpAddr = IpAddr.v4(ipv4)

        /**
         * Copies this address to a new `IpAddr.V6`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.IpAddr
         * import std.net.Ipv6Addr
         *
         * val addr = Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff)
         * check(IpAddr.v6(addr) == IpAddr.from(addr))
         * ```
         */
        public fun from(ipv6: Ipv6Addr): IpAddr = IpAddr.v6(ipv6)

        /**
         * Creates an `IpAddr.V4` from a four element byte array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.IpAddr
         * import std.net.Ipv4Addr
         *
         * public fun doctestEntry() {
         *     val addr = IpAddr.from(ubyteArrayOf(13u.toUByte(), 12u.toUByte(), 11u.toUByte(), 10u.toUByte()))
         *     check(IpAddr.v4(Ipv4Addr.new(13, 12, 11, 10)) == addr)
         * }
         * ```
         */
        public fun from(octets: UByteArray): IpAddr = IpAddr.v4(Ipv4Addr.from(octets))

        /**
         * Creates an `IpAddr.V6` from a sixteen element byte array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.IpAddr
         * import std.net.Ipv6Addr
         *
         * public fun doctestEntry() {
         *     val addr = IpAddr.from(ubyteArrayOf(0x19u.toUByte(), 0x18u.toUByte(), 0x17u.toUByte(), 0x16u.toUByte(), 0x15u.toUByte(), 0x14u.toUByte(), 0x13u.toUByte(), 0x12u.toUByte(), 0x11u.toUByte(), 0x10u.toUByte(), 0x0fu.toUByte(), 0x0eu.toUByte(), 0x0du.toUByte(), 0x0cu.toUByte(), 0x0bu.toUByte(), 0x0au.toUByte()))
         *     check(IpAddr.v6(Ipv6Addr.new(0x1918, 0x1716, 0x1514, 0x1312, 0x1110, 0x0f0e, 0x0d0c, 0x0b0a)) == addr)
         * }
         * ```
         */
        public fun fromUByteArray16(octets: UByteArray): IpAddr = IpAddr.v6(Ipv6Addr.from(octets))

        /**
         * Creates an `IpAddr.V6` from an eight element 16-bit array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.IpAddr
         * import std.net.Ipv6Addr
         *
         * public fun doctestEntry() {
         *     val addr = IpAddr.from(ushortArrayOf(0x20du.toUShort(), 0x20cu.toUShort(), 0x20bu.toUShort(), 0x20au.toUShort(), 0x209u.toUShort(), 0x208u.toUShort(), 0x207u.toUShort(), 0x206u.toUShort()))
         *     check(IpAddr.v6(Ipv6Addr.new(0x20d, 0x20c, 0x20b, 0x20a, 0x209, 0x208, 0x207, 0x206)) == addr)
         * }
         * ```
         */
        public fun from(segments: UShortArray): IpAddr = IpAddr.v6(Ipv6Addr.from(segments))
    }
}

/**
 * An IPv4 address.
 *
 * IPv4 addresses are defined as 32-bit integers in [IETF RFC 791].
 * They are usually represented as four octets.
 *
 * See [IpAddr] for a type encompassing both IPv4 and IPv6 addresses.
 *
 * [IETF RFC 791]: https://tools.ietf.org/html/rfc791
 *
 * # Textual representation
 *
 * `Ipv4Addr` provides a [FromStr] implementation. The four octets are in decimal
 * notation, divided by `.` (this is called "dot-decimal notation").
 * Notably, octal numbers (which are indicated with a leading `0`) and hexadecimal numbers (which
 * are indicated with a leading `0x`) are not allowed per [IETF RFC 6943].
 *
 * [IETF RFC 6943]: https://tools.ietf.org/html/rfc6943#section-3.1.1
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.Ipv4Addr
 *
 * val localhost = Ipv4Addr.new(127, 0, 0, 1)
 * check("127.0.0.1".parse() == Result.success(localhost))
 * check(localhost.isLoopback() == true)
 * check("012.004.002.000".parse<Ipv4Addr>().isErr())
 * // all octets are in octal
 * check("0000000.0.0.0".parse<Ipv4Addr>().isErr())
 * // first octet is a zero in octal
 * check("0xcb.0x0.0x71.0x00".parse<Ipv4Addr>().isErr())
 * // all octets are in hex
 * ```
 */
public open class Ipv4Addr(
    public var octets: UByteArray,
) : Not<Any?, Ipv4Addr>, BitAndAssign<Ipv4Addr>, BitAnd<Ipv4Addr, Any?, Ipv4Addr>, BitOrAssign<Ipv4Addr>, BitOr<Ipv4Addr, Any?, Ipv4Addr> {
    public open fun clone(): Ipv4Addr = Ipv4Addr(octets)

    public override fun hashCode(): Int {
        var state = 0
        // Hashers are often more efficient at hashing a fixed-width integer
        // than a bytestring, so convert before hashing. We don't use toBits()
        // here as that may involve a byteswap which is unnecessary.
        state = 31 * state + (run { val bytesTmp0 = this.octets; ((bytesTmp0[3].toUInt() shl 24) or (bytesTmp0[2].toUInt() shl 16) or (bytesTmp0[1].toUInt() shl 8) or bytesTmp0[0].toUInt()) }).hashCode()
        return state
    }

    /**
     * Converts an IPv4 address into a `UInt` representation using native byte order.
     *
     * Although IPv4 addresses are big-endian, the `UInt` value will use the target platform's
     * native byte order. That is, the `UInt` value is an integer representation of the IPv4
     * address and not an integer interpretation of the IPv4 address's big-endian bitstring. This
     * means that the `UInt` value masked with `0xffffff00` will set the last octet in the address
     * to 0, regardless of the target platform's endianness.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * val addr = Ipv4Addr.new(0x12, 0x34, 0x56, 0x78)
     * check(0x12345678uL == addr.toBits())
     * ```
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * val addr = Ipv4Addr.new(0x12, 0x34, 0x56, 0x78)
     * val addrBits = addr.toBits() and 0xffffff00uL
     * check(Ipv4Addr.new(0x12, 0x34, 0x56, 0x00) == Ipv4Addr.fromBits(addrBits))
     * ```
     */
    public open fun toBits(): UInt = run { val bytesTmp1 = this.octets; ((bytesTmp1[0].toUInt() shl 24) or (bytesTmp1[1].toUInt() shl 16) or (bytesTmp1[2].toUInt() shl 8) or bytesTmp1[3].toUInt()) }

    /**
     * Returns the four eight-bit integers that make up this address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * val addr = Ipv4Addr.new(127, 0, 0, 1)
     * check(addr.octets() == listOf(127, 0, 0, 1))
     * ```
     */
    public open fun octets(): UByteArray = this.octets

    /**
     * Returns the four eight-bit integers that make up this address
     * as a slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * @feature(ipAsOctets)
     * public fun doctestEntry() {
     *     val addr = Ipv4Addr.new(127, 0, 0, 1)
     *     check(addr.asOctets() == listOf(127, 0, 0, 1))
     * }
     * ```
     */
    public open fun asOctets(): UByteArray = this.octets

    /**
     * Returns [trueVal] for the special 'unspecified' address (`0.0.0.0`).
     *
     * This property is defined in _UNIX Network Programming, Second Edition_,
     * W. Richard Stevens, p. 891; see also [ip7].
     *
     * [ip7]: https://man7.org/linux/man-pages/man7/ip.7.html
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(0, 0, 0, 0).isUnspecified() == true)
     * check(Ipv4Addr.new(45, 22, 13, 197).isUnspecified() == false)
     * ```
     */
    public open fun isUnspecified(): Boolean = run { val bytesTmp2 = this.octets; ((bytesTmp2[0].toUInt() shl 24) or (bytesTmp2[1].toUInt() shl 16) or (bytesTmp2[2].toUInt() shl 8) or bytesTmp2[3].toUInt()) } == 0u

    /**
     * Returns [trueVal] if this is a loopback address (`127.0.0.0 / 8`).
     *
     * This property is defined by [IETF RFC 1122].
     *
     * [IETF RFC 1122]: https://tools.ietf.org/html/rfc1122
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(127, 0, 0, 1).isLoopback() == true)
     * check(Ipv4Addr.new(45, 22, 13, 197).isLoopback() == false)
     * ```
     */
    public open fun isLoopback(): Boolean = this.octets()[0] == 127u.toUByte()

    /**
     * Returns [trueVal] if this is a private address.
     *
     * The private address ranges are defined in [IETF RFC 1918] and include:
     *
     *  - `10.0.0.0 / 8`
     *  - `172.16.0.0 / 12`
     *  - `192.168.0.0 / 16`
     *
     * [IETF RFC 1918]: https://tools.ietf.org/html/rfc1918
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(10, 0, 0, 1).isPrivate() == true)
     * check(Ipv4Addr.new(10, 10, 10, 10).isPrivate() == true)
     * check(Ipv4Addr.new(172, 16, 10, 10).isPrivate() == true)
     * check(Ipv4Addr.new(172, 29, 45, 14).isPrivate() == true)
     * check(Ipv4Addr.new(172, 32, 0, 2).isPrivate() == false)
     * check(Ipv4Addr.new(192, 168, 0, 2).isPrivate() == true)
     * check(Ipv4Addr.new(192, 169, 0, 2).isPrivate() == false)
     * ```
     */
    public open fun isPrivate(): Boolean =
        run {
            val matchVal = this.octets()
            when {
                matchVal.size >= 1 && matchVal[0] == 10 -> true
                matchVal.size >= 2 && matchVal[0] == 172 && matchVal[1] >= 16 && matchVal[1] <= 31 -> {
                    val b = matchVal[1]
                    true
                }
                matchVal.size >= 2 && matchVal[0] == 192 && matchVal[1] == 168 -> true
                else -> false
            }
        }

    /**
     * Returns [trueVal] if the address is link-local (`169.254.0.0 / 16`).
     *
     * This property is defined by [IETF RFC 3927].
     *
     * [IETF RFC 3927]: https://tools.ietf.org/html/rfc3927
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(169, 254, 0, 0).isLinkLocal() == true)
     * check(Ipv4Addr.new(169, 254, 10, 65).isLinkLocal() == true)
     * check(Ipv4Addr.new(16, 89, 10, 65).isLinkLocal() == false)
     * ```
     */
    public open fun isLinkLocal(): Boolean =
        run {
            val matchVal = this.octets()
            when {
                matchVal.size >= 2 && matchVal[0] == 169 && matchVal[1] == 254 -> true
                else -> false
            }
        }

    /**
     * Returns [trueVal] if the address appears to be globally reachable
     * as specified by the [IANA IPv4 Special-Purpose Address Registry].
     *
     * Whether or not an address is practically reachable will depend on your
     * network configuration. Most IPv4 addresses are globally reachable, unless
     * they are specifically defined as *not* globally reachable.
     *
     * Non-exhaustive list of notable addresses that are not globally reachable:
     *
     * - The [unspecified address] ([`isUnspecified`](Ipv4Addr.isUnspecified))
     * - Addresses reserved for private use ([`isPrivate`](Ipv4Addr.isPrivate))
     * - Addresses in the shared address space ([`isShared`](Ipv4Addr.isShared))
     * - Loopback addresses ([`isLoopback`](Ipv4Addr.isLoopback))
     * - Link-local addresses ([`isLinkLocal`](Ipv4Addr.isLinkLocal))
     * - Addresses reserved for documentation ([`isDocumentation`](Ipv4Addr.isDocumentation))
     * - Addresses reserved for benchmarking ([`isBenchmarking`](Ipv4Addr.isBenchmarking))
     * - Reserved addresses ([`isReserved`](Ipv4Addr.isReserved))
     * - The [broadcast address] ([`isBroadcast`](Ipv4Addr.isBroadcast))
     *
     * For the complete overview of which addresses are globally reachable, see the table at the [IANA IPv4 Special-Purpose Address Registry].
     *
     * [IANA IPv4 Special-Purpose Address Registry]: https://www.iana.org/assignments/iana-ipv4-special-registry/iana-ipv4-special-registry.xhtml
     * [unspecified address]: Ipv4Addr.Unspecified
     * [broadcast address]: Ipv4Addr.Broadcast
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     // Most IPv4 addresses are globally reachable:
     *     check(Ipv4Addr.new(80, 9, 12, 3).isGlobal() == true)
     *     // However some addresses have been assigned a special meaning
     *     // that makes them not globally reachable. Some examples are:
     *     // The unspecified address (`0.0.0.0`)
     *     check(Ipv4Addr.UNSPECIFIED.isGlobal() == false)
     *     // Addresses reserved for private use (`10.0.0.0 / 8`, `172.16.0.0 / 12`, 192.168.0.0/16)
     *     check(Ipv4Addr.new(10, 254, 0, 0).isGlobal() == false)
     *     check(Ipv4Addr.new(192, 168, 10, 65).isGlobal() == false)
     *     check(Ipv4Addr.new(172, 16, 10, 65).isGlobal() == false)
     *     // Addresses in the shared address space (`100.64.0.0 / 10`)
     *     check(Ipv4Addr.new(100, 100, 0, 0).isGlobal() == false)
     *     // The loopback addresses (`127.0.0.0 / 8`)
     *     check(Ipv4Addr.LOCALHOST.isGlobal() == false)
     *     // Link-local addresses (`169.254.0.0 / 16`)
     *     check(Ipv4Addr.new(169, 254, 45, 1).isGlobal() == false)
     *     // Addresses reserved for documentation (`192.0.2.0 / 24`, `198.51.100.0 / 24`, `203.0.113.0 / 24`)
     *     check(Ipv4Addr.new(192, 0, 2, 255).isGlobal() == false)
     *     check(Ipv4Addr.new(198, 51, 100, 65).isGlobal() == false)
     *     check(Ipv4Addr.new(203, 0, 113, 6).isGlobal() == false)
     *     // Addresses reserved for benchmarking (`198.18.0.0 / 15`)
     *     check(Ipv4Addr.new(198, 18, 0, 0).isGlobal() == false)
     *     // Reserved addresses (`240.0.0.0 / 4`)
     *     check(Ipv4Addr.new(250, 10, 20, 30).isGlobal() == false)
     *     // The broadcast address (`255.255.255.255`)
     *     check(Ipv4Addr.BROADCAST.isGlobal() == false)
     *     // For a complete overview see the IANA IPv4 Special-Purpose Address Registry.
     * }
     * ```
     */
    public open fun isGlobal(): Boolean =
        !(this.octets()[0] == 0u.toUByte() || this.isPrivate() || this.isShared() || this.isLoopback() || this.isLinkLocal() || (this.octets()[0] == 192u.toUByte() && this.octets()[1] == 0u.toUByte() && this.octets()[2] == 0u.toUByte() && this.octets()[3] != 9u.toUByte() && this.octets()[3] != 10u.toUByte()) ||
            run {
                val matchVal = this.octets()
                when {
                    matchVal.size == 4 && matchVal[0] == 192 && matchVal[1] == 88 && matchVal[2] == 99 && matchVal[3] == 2 -> true
                    else -> false
                }
            } || this.isDocumentation() || this.isBenchmarking() || this.isReserved() || this.isBroadcast())

    /**
     * Returns [trueVal] if this address is part of the Shared Address Space defined in
     * [IETF RFC 6598] (`100.64.0.0 / 10`).
     *
     * [IETF RFC 6598]: https://tools.ietf.org/html/rfc6598
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv4Addr.new(100, 64, 0, 0).isShared() == true)
     *     check(Ipv4Addr.new(100, 127, 255, 255).isShared() == true)
     *     check(Ipv4Addr.new(100, 128, 0, 0).isShared() == false)
     * }
     * ```
     */
    public open fun isShared(): Boolean = this.octets()[0] == 100u.toUByte() && (this.octets()[1] and 0xC0u.toUByte() == 0x40u.toUByte())

    /**
     * Returns [trueVal] if this address part of the `198.18.0.0 / 15` range, which is reserved for
     * network devices benchmarking.
     *
     * This range is defined in [IETF RFC 2544] as `192.18.0.0` through
     * `198.19.255.255` but [errata 423] corrects it to `198.18.0.0 / 15`.
     *
     * [IETF RFC 2544]: https://tools.ietf.org/html/rfc2544
     * [errata 423]: https://www.rfc-editor.org/errata/eid423
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv4Addr.new(198, 17, 255, 255).isBenchmarking() == false)
     *     check(Ipv4Addr.new(198, 18, 0, 0).isBenchmarking() == true)
     *     check(Ipv4Addr.new(198, 19, 255, 255).isBenchmarking() == true)
     *     check(Ipv4Addr.new(198, 20, 0, 0).isBenchmarking() == false)
     * }
     * ```
     */
    public open fun isBenchmarking(): Boolean = this.octets()[0] == 198u.toUByte() && (this.octets()[1] and 0xfeu.toUByte()) == 18u.toUByte()

    /**
     * Returns [trueVal] if this address is reserved by IANA for future use.
     *
     * [IETF RFC 1112] defines the block of reserved addresses as `240.0.0.0 / 4`.
     * This range normally includes the broadcast address `255.255.255.255`, but
     * this implementation explicitly excludes it, since it is obviously not
     * reserved for future use.
     *
     * [IETF RFC 1112]: https://tools.ietf.org/html/rfc1112
     *
     * # Warning
     *
     * As IANA assigns new addresses, this method will be
     * updated. This may result in non-reserved addresses being
     * treated as reserved in code that relies on an outdated version
     * of this method.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv4Addr.new(240, 0, 0, 0).isReserved() == true)
     *     check(Ipv4Addr.new(255, 255, 255, 254).isReserved() == true)
     *     check(Ipv4Addr.new(239, 255, 255, 255).isReserved() == false)
     *     // The broadcast address is not considered as reserved for future use by this implementation
     *     check(Ipv4Addr.new(255, 255, 255, 255).isReserved() == false)
     * }
     * ```
     */
    public open fun isReserved(): Boolean = this.octets()[0] and 240u.toUByte() == 240u.toUByte() && !this.isBroadcast()

    /**
     * Returns [trueVal] if this is a multicast address (`224.0.0.0 / 4`).
     *
     * Multicast addresses have a most significant octet between `224` and `239`,
     * and is defined by [IETF RFC 5771].
     *
     * [IETF RFC 5771]: https://tools.ietf.org/html/rfc5771
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(224, 254, 0, 0).isMulticast() == true)
     * check(Ipv4Addr.new(236, 168, 10, 65).isMulticast() == true)
     * check(Ipv4Addr.new(172, 16, 10, 65).isMulticast() == false)
     * ```
     */
    public open fun isMulticast(): Boolean = this.octets()[0] >= 224u.toUByte() && this.octets()[0] <= 239u.toUByte()

    /**
     * Returns [trueVal] if this is a broadcast address (`255.255.255.255`).
     *
     * A broadcast address has all octets set to `255` as defined in [IETF RFC 919].
     *
     * [IETF RFC 919]: https://tools.ietf.org/html/rfc919
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(255, 255, 255, 255).isBroadcast() == true)
     * check(Ipv4Addr.new(236, 168, 10, 65).isBroadcast() == false)
     * ```
     */
    public open fun isBroadcast(): Boolean = run { val bytesTmp3 = this.octets(); ((bytesTmp3[0].toUInt() shl 24) or (bytesTmp3[1].toUInt() shl 16) or (bytesTmp3[2].toUInt() shl 8) or bytesTmp3[3].toUInt()) } == run { val bytesTmp4 = Ipv4Addr.BROADCAST.octets(); ((bytesTmp4[0].toUInt() shl 24) or (bytesTmp4[1].toUInt() shl 16) or (bytesTmp4[2].toUInt() shl 8) or bytesTmp4[3].toUInt()) }

    /**
     * Returns [trueVal] if this address is in a range designated for documentation.
     *
     * This is defined in [IETF RFC 5737]:
     *
     * - `192.0.2.0 / 24` (TEST-NET-1)
     * - `198.51.100.0 / 24` (TEST-NET-2)
     * - `203.0.113.0 / 24` (TEST-NET-3)
     *
     * [IETF RFC 5737]: https://tools.ietf.org/html/rfc5737
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     *
     * check(Ipv4Addr.new(192, 0, 2, 255).isDocumentation() == true)
     * check(Ipv4Addr.new(198, 51, 100, 65).isDocumentation() == true)
     * check(Ipv4Addr.new(203, 0, 113, 6).isDocumentation() == true)
     * check(Ipv4Addr.new(193, 34, 17, 19).isDocumentation() == false)
     * ```
     */
    public open fun isDocumentation(): Boolean =
        run {
            val matchVal = this.octets()
            when {
                matchVal.size == 4 && matchVal[0] == 192 && matchVal[1] == 0 && matchVal[2] == 2 -> true
                matchVal.size == 4 && matchVal[0] == 198 && matchVal[1] == 51 && matchVal[2] == 100 -> true
                matchVal.size == 4 && matchVal[0] == 203 && matchVal[1] == 0 && matchVal[2] == 113 -> true
                else -> false
            }
        }

    /**
     * Converts this address to an [IPv4-compatible] [`IPv6` address].
     *
     * `a.b.c.d` becomes `.a.b.c.d`
     *
     * Note that IPv4-compatible addresses have been officially deprecated.
     * If you don't explicitly need an IPv4-compatible address for legacy reasons, consider using `toIpv6Mapped` instead.
     *
     * [IPv4-compatible]: Ipv6Addr#ipv4-compatible-ipv6-addresses
     * [`IPv6` address]: Ipv6Addr
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(Ipv4Addr.new(192, 0, 2, 255).toIpv6Compatible() == Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0xc000, 0x2ff))
     * ```
     */
    public open fun toIpv6Compatible(): Ipv6Addr {
        val (a, b, c, d) = this.octets()
        return Ipv6Addr(octets = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, a, b, c, d))
    }

    /**
     * Converts this address to an [IPv4-mapped] [`IPv6` address].
     *
     * `a.b.c.d` becomes `.ffff: a.b.c.d`
     *
     * [IPv4-mapped]: Ipv6Addr#ipv4-mapped-ipv6-addresses
     * [`IPv6` address]: Ipv6Addr
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(Ipv4Addr.new(192, 0, 2, 255).toIpv6Mapped() == Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc000, 0x2ff))
     * ```
     */
    public open fun toIpv6Mapped(): Ipv6Addr {
        val (a, b, c, d) = this.octets()
        return Ipv6Addr(octets = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xFFu, 0xFFu, a, b, c, d))
    }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        val octets = this.octets()
        // If there are no alignment requirements, write the IP address directly to `f`.
        // Otherwise, write it to a local buffer and then use `f.pad`.
        return if (fmt.precision() == null && fmt.width() == null) {
            fmt.writeFmt("${octets[0]}.${octets[1]}.${octets[2]}.${octets[3]}")
        } else {
            val LONGEST_IPV4_ADDR: String = "255.255.255.255"
            val buf = DisplayBuffer.buffer(LONGEST_IPV4_ADDR.len())
            val buf1 = DisplayBuffer.new(buf)
            buf1.writeFmt("${octets[0]}.${octets[1]}.${octets[2]}.${octets[3]}").unwrap()
            run { fmt.append(buf1.asStr()); Result.Ok(Unit) }
        }
    }

    public open fun fmtDebug(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append((this).toString()); Result.Ok(Unit) }

    public open fun eq(other: IpAddr): Boolean =
        when (other) {
            is IpAddr.V4 -> {
                val (v4) = other
                this == v4
            }
            is IpAddr.V6 -> false
        }

    public open fun partialCmp(other: Ipv4Addr): Ordering? = this.cmp(other)

    public open fun partialCmp(other: IpAddr): Ordering? =
        when (other) {
            is IpAddr.V4 -> {
                val (v4) = other
                this.partialCmp(v4)
            }
            is IpAddr.V6 -> Ordering.Less
        }

    public open fun cmp(other: Ipv4Addr): Ordering = this.octets.cmp(other.octets)

    public override fun not(): Ipv4Addr {
        var idx = 0
        while (idx < 4) {
            this.octets[idx] = (!this.octets[idx]).toUByte()
            idx += 1
        }
        return this
    }

    public override fun bitandAssign(rhs: Ipv4Addr) {
        var idx = 0
        while (idx < this.octets.size) {
            this.octets[idx].bitandAssign(rhs.octets[idx])
            idx += 1
        }
    }

    public open fun bitandAssignIpv4Addr(rhs: Ipv4Addr) {
        this.bitandAssign(rhs)
    }

    public override fun bitand(rhs: Ipv4Addr): Ipv4Addr {
        this.bitandAssign(rhs)
        return this
    }

    public open fun bitandIpv4Addr(rhs: Ipv4Addr): Ipv4Addr {
        this.bitandAssign(rhs)
        return this
    }

    public override fun bitorAssign(rhs: Ipv4Addr) {
        var idx = 0
        while (idx < this.octets.size) {
            this.octets[idx].bitorAssign(rhs.octets[idx])
            idx += 1
        }
    }

    public open fun bitorAssignIpv4Addr(rhs: Ipv4Addr) {
        this.bitorAssign(rhs)
    }

    public override fun bitor(rhs: Ipv4Addr): Ipv4Addr {
        this.bitorAssign(rhs)
        return this
    }

    public open fun bitorIpv4Addr(rhs: Ipv4Addr): Ipv4Addr {
        this.bitorAssign(rhs)
        return this
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Ipv4Addr> =
            object : TypeLayout<Ipv4Addr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Ipv4Addr =
                    Ipv4Addr(
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Ipv4Addr) {
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr, value.octets)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ipv4Addr =
                    Ipv4Addr(
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ipv4Addr) {
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset, value.octets)
                }
            }

        /**
         * Creates a new IPv4 address from four eight-bit octets.
         *
         * The result will represent the IP address `a`.`b`.`c`.`d`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         *
         * val addr = Ipv4Addr.new(127, 0, 0, 1)
         * ```
         */
        public fun new(a: UByte, b: UByte, c: UByte, d: UByte): Ipv4Addr = Ipv4Addr(octets = ubyteArrayOf(a, b, c, d))

        /**
         * The size of an IPv4 address in bits.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         *
         * check(Ipv4Addr.BITS == 32)
         * ```
         */
        public val BITS: UInt = 32u

        /**
         * Converts a native byte order `UInt` into an IPv4 address.
         *
         * See [Ipv4Addr.toBits] for an explanation on endianness.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         *
         * val addr = Ipv4Addr.fromBits(0x12345678)
         * check(Ipv4Addr.new(0x12, 0x34, 0x56, 0x78) == addr)
         * ```
         */
        public fun fromBits(bits: UInt): Ipv4Addr = Ipv4Addr(octets = run { val rawBits = bits; ubyteArrayOf((rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) })

        /**
         * An IPv4 address with the address pointing to localhost: `127.0.0.1`
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         *
         * val addr = Ipv4Addr.LOCALHOST
         * check(addr == Ipv4Addr.new(127, 0, 0, 1))
         * ```
         */
        public val LOCALHOST: Ipv4Addr = Ipv4Addr.new(127u, 0u, 0u, 1u)

        /**
         * An IPv4 address representing an unspecified address: `0.0.0.0`
         *
         * This corresponds to the constant `INADDR_ANY` in other languages.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         *
         * val addr = Ipv4Addr.UNSPECIFIED
         * check(addr == Ipv4Addr.new(0, 0, 0, 0))
         * ```
         */
        public val UNSPECIFIED: Ipv4Addr = Ipv4Addr.new(0u, 0u, 0u, 0u)

        /**
         * An IPv4 address representing the broadcast address: `255.255.255.255`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv4Addr
         *
         * val addr = Ipv4Addr.BROADCAST
         * check(addr == Ipv4Addr.new(255, 255, 255, 255))
         * ```
         */
        public val BROADCAST: Ipv4Addr = Ipv4Addr.new(255u, 255u, 255u, 255u)

        /**
         * Creates an `Ipv4Addr` from a four element byte array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.Ipv4Addr
         *
         * public fun doctestEntry() {
         *     val addr = Ipv4Addr.fromOctets(ubyteArrayOf(13u.toUByte(), 12u.toUByte(), 11u.toUByte(), 10u.toUByte()))
         *     check(Ipv4Addr.new(13, 12, 11, 10) == addr)
         * }
         * ```
         */
        public fun fromOctets(octets: UByteArray): Ipv4Addr = Ipv4Addr(octets = octets)

        /**
         * Uses [Ipv4Addr.fromBits] to convert a host byte order `UInt` into an IPv4 address.
         */
        public fun from(ip: UInt): Ipv4Addr = Ipv4Addr.fromBits(ip)

        /**
         * Creates an `Ipv4Addr` from a four element byte array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.Ipv4Addr
         *
         * public fun doctestEntry() {
         *     val addr = Ipv4Addr.from(ubyteArrayOf(13u.toUByte(), 12u.toUByte(), 11u.toUByte(), 10u.toUByte()))
         *     check(Ipv4Addr.new(13, 12, 11, 10) == addr)
         * }
         * ```
         */
        public fun from(octets: UByteArray): Ipv4Addr = Ipv4Addr(octets = octets)
    }
}

/**
 * An IPv6 address.
 *
 * IPv6 addresses are defined as 128-bit integers in [IETF RFC 4291].
 * They are usually represented as eight 16-bit segments.
 *
 * [IETF RFC 4291]: https://tools.ietf.org/html/rfc4291
 *
 * # Embedding IPv4 Addresses
 *
 * See [IpAddr] for a type encompassing both IPv4 and IPv6 addresses.
 *
 * To assist in the transition from IPv4 to IPv6 two types of IPv6 addresses that embed an IPv4 address were defined:
 * IPv4-compatible and IPv4-mapped addresses. Of these IPv4-compatible addresses have been officially deprecated.
 *
 * Both types of addresses are not assigned any special meaning by this implementation,
 * other than what the relevant standards prescribe. This means that an address like `.ffff: 127.0.0.1`,
 * while representing an IPv4 loopback address, is not itself an IPv6 loopback address; only `.1` is.
 * To handle these so called "IPv4-in-IPv6" addresses, they have to first be converted to their canonical IPv4 address.
 *
 * ### IPv4-Compatible IPv6 Addresses
 *
 * IPv4-compatible IPv6 addresses are defined in [IETF RFC 4291 Section 2.5.5.1], and have been officially deprecated.
 * The RFC describes the format of an "IPv4-Compatible IPv6 address" as follows:
 *
 * ```text
 * |                80 bits               | 16 |      32 bits        |
 * +--------------------------------------+--------------------------+
 * |0000..............................0000|0000|    IPv4 address     |
 * +--------------------------------------+----+---------------------+
 * ```
 * So `.a.b.c.d` would be an IPv4-compatible IPv6 address representing the IPv4 address `a.b.c.d`.
 *
 * To convert from an IPv4 address to an IPv4-compatible IPv6 address, use [Ipv4Addr.toIpv6Compatible].
 * Use [Ipv6Addr.toIpv4] to convert an IPv4-compatible IPv6 address to the canonical IPv4 address.
 *
 * [IETF RFC 4291 Section 2.5.5.1]: https://datatracker.ietf.org/doc/html/rfc4291#section-2.5.5.1
 *
 * ### IPv4-Mapped IPv6 Addresses
 *
 * IPv4-mapped IPv6 addresses are defined in [IETF RFC 4291 Section 2.5.5.2].
 * The RFC describes the format of an "IPv4-Mapped IPv6 address" as follows:
 *
 * ```text
 * |                80 bits               | 16 |      32 bits        |
 * +--------------------------------------+--------------------------+
 * |0000..............................0000|FFFF|    IPv4 address     |
 * +--------------------------------------+----+---------------------+
 * ```
 * So `.ffff: a.b.c.d` would be an IPv4-mapped IPv6 address representing the IPv4 address `a.b.c.d`.
 *
 * To convert from an IPv4 address to an IPv4-mapped IPv6 address, use [Ipv4Addr.toIpv6Mapped].
 * Use [Ipv6Addr.toIpv4] to convert an IPv4-mapped IPv6 address to the canonical IPv4 address.
 * Note that this will also convert the IPv6 loopback address `.1` to `0.0.0.1`. Use
 * [Ipv6Addr.toIpv4Mapped] to avoid this.
 *
 * [IETF RFC 4291 Section 2.5.5.2]: https://datatracker.ietf.org/doc/html/rfc4291#section-2.5.5.2
 *
 * # Textual representation
 *
 * `Ipv6Addr` provides a [FromStr] implementation. There are many ways to represent
 * an IPv6 address in text, but in general, each segments is written in hexadecimal
 * notation, and segments are separated by `:`. For more information, see
 * [IETF RFC 5952].
 *
 * [IETF RFC 5952]: https://tools.ietf.org/html/rfc5952
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.Ipv6Addr
 *
 * val localhost = Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1)
 * check("::1".parse() == Result.success(localhost))
 * check(localhost.isLoopback() == true)
 * ```
 */
public open class Ipv6Addr(
    public var octets: UByteArray,
) : Not<Any?, Ipv6Addr>, BitAndAssign<Ipv6Addr>, BitAnd<Ipv6Addr, Any?, Ipv6Addr>, BitOrAssign<Ipv6Addr>, BitOr<Ipv6Addr, Any?, Ipv6Addr> {
    public open fun clone(): Ipv6Addr = Ipv6Addr(octets)

    public override fun hashCode(): Int {
        var state = 0
        // Hashers are often more efficient at hashing a fixed-width integer
        // than a bytestring, so convert before hashing. We don't use toBits()
        // here as that may involve unnecessary byteswaps.
        state = 31 * state + (U128.fromLeBytes(this.octets)).hashCode()
        return state
    }

    /**
     * Converts an IPv6 address into a `ULong` representation using native byte order.
     *
     * Although IPv6 addresses are big-endian, the `ULong` value will use the target platform's
     * native byte order. That is, the `ULong` value is an integer representation of the IPv6
     * address and not an integer interpretation of the IPv6 address's big-endian bitstring. This
     * means that the `ULong` value masked with `0xffffffffffffffffffffffffffff0000U128` will set
     * the last segment in the address to 0, regardless of the target platform's endianness.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * val addr = Ipv6Addr.new(0x1020, 0x3040, 0x5060, 0x7080, 0x90A0, 0xB0C0, 0xD0E0, 0xF00D)
     * check(U128(0x1020304050607080uL, 0x90a0b0c0d0e0f00duL) == addr.toBits())
     * ```
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * val addr = Ipv6Addr.new(0x1020, 0x3040, 0x5060, 0x7080, 0x90A0, 0xB0C0, 0xD0E0, 0xF00D)
     * val addrBits = addr.toBits() and (U128(0xffffffffffffffffuL, 0xffffffffffff0000uL)).toULong()
     * check(Ipv6Addr.new(0x1020, 0x3040, 0x5060, 0x7080, 0x90A0, 0xB0C0, 0xD0E0, 0x0000) == Ipv6Addr.fromBits(addrBits))
     * ```
     */
    public open fun toBits(): U128 = U128.fromBeBytes(this.octets)

    /**
     * Returns the eight 16-bit segments that make up this address.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).segments() == listOf(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff))
     * ```
     */
    public open fun segments(): UShortArray {
        // All elements in `self.octets` must be big endian.
        // SAFETY: `[UByte; 16]` is always safe to reinterpret to `[UShort; 8]`.
        val (a, b, c, d, e, f, g, h) = transmute<UShortArray>(this.octets)
        // We want native endian u16
        return listOf((((((a).toUInt() and 0xFFu) shl 8) or (((a).toUInt() shr 8) and 0xFFu)).toUShort()), (((((b).toUInt() and 0xFFu) shl 8) or (((b).toUInt() shr 8) and 0xFFu)).toUShort()), (((((c).toUInt() and 0xFFu) shl 8) or (((c).toUInt() shr 8) and 0xFFu)).toUShort()), (((((d).toUInt() and 0xFFu) shl 8) or (((d).toUInt() shr 8) and 0xFFu)).toUShort()), (((((e).toUInt() and 0xFFu) shl 8) or (((e).toUInt() shr 8) and 0xFFu)).toUShort()), (((((f).toUInt() and 0xFFu) shl 8) or (((f).toUInt() shr 8) and 0xFFu)).toUShort()), (((((g).toUInt() and 0xFFu) shl 8) or (((g).toUInt() shr 8) and 0xFFu)).toUShort()), (((((h).toUInt() and 0xFFu) shl 8) or (((h).toUInt() shr 8) and 0xFFu)).toUShort()))
    }

    /**
     * Returns [trueVal] for the special 'unspecified' address (`.`).
     *
     * This property is defined in [IETF RFC 4291].
     *
     * [IETF RFC 4291]: https://tools.ietf.org/html/rfc4291
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isUnspecified() == false)
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 0).isUnspecified() == true)
     * ```
     */
    public open fun isUnspecified(): Boolean = U128.fromBeBytes(this.octets()) == U128.fromBeBytes(Ipv6Addr.UNSPECIFIED.octets())

    /**
     * Returns [trueVal] if this is the [loopback address] (`.1`),
     * as defined in [IETF RFC 4291 section 2.5.3].
     *
     * Contrary to IPv4, in IPv6 there is only one loopback address.
     *
     * [loopback address]: Ipv6Addr.Localhost
     * [IETF RFC 4291 section 2.5.3]: https://tools.ietf.org/html/rfc4291#section-2.5.3
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isLoopback() == false)
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 0x1).isLoopback() == true)
     * ```
     */
    public open fun isLoopback(): Boolean = U128.fromBeBytes(this.octets()) == U128.fromBeBytes(Ipv6Addr.LOCALHOST.octets())

    /**
     * Returns [trueVal] if the address appears to be globally reachable
     * as specified by the [IANA IPv6 Special-Purpose Address Registry].
     *
     * Whether or not an address is practically reachable will depend on your
     * network configuration. Most IPv6 addresses are globally reachable, unless
     * they are specifically defined as *not* globally reachable.
     *
     * Non-exhaustive list of notable addresses that are not globally reachable:
     * - The [unspecified address] ([`isUnspecified`](Ipv6Addr.isUnspecified))
     * - The [loopback address] ([`isLoopback`](Ipv6Addr.isLoopback))
     * - IPv4-mapped addresses
     * - Addresses reserved for benchmarking ([`isBenchmarking`](Ipv6Addr.isBenchmarking))
     * - Addresses reserved for documentation ([`isDocumentation`](Ipv6Addr.isDocumentation))
     * - Unique local addresses ([`isUniqueLocal`](Ipv6Addr.isUniqueLocal))
     * - Unicast addresses with link-local scope ([`isUnicastLinkLocal`](Ipv6Addr.isUnicastLinkLocal))
     *
     * For the complete overview of which addresses are globally reachable, see the table at the [IANA IPv6 Special-Purpose Address Registry].
     *
     * Note that an address having global scope is not the same as being globally reachable,
     * and there is no direct relation between the two concepts: There exist addresses with global scope
     * that are not globally reachable (for example unique local addresses),
     * and addresses that are globally reachable without having global scope
     * (multicast addresses with non-global scope).
     *
     * [IANA IPv6 Special-Purpose Address Registry]: https://www.iana.org/assignments/iana-ipv6-special-registry/iana-ipv6-special-registry.xhtml
     * [unspecified address]: Ipv6Addr.Unspecified
     * [loopback address]: Ipv6Addr.Localhost
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     // Most IPv6 addresses are globally reachable:
     *     check(Ipv6Addr.new(0x26, 0, 0x1c9, 0, 0, 0xafc8, 0x10, 0x1).isGlobal() == true)
     *     // However some addresses have been assigned a special meaning
     *     // that makes them not globally reachable. Some examples are:
     *     // The unspecified address (`.`)
     *     check(Ipv6Addr.UNSPECIFIED.isGlobal() == false)
     *     // The loopback address (`.1`)
     *     check(Ipv6Addr.LOCALHOST.isGlobal() == false)
     *     // IPv4-mapped addresses (`.ffff: 0: 0 / 96`)
     *     check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isGlobal() == false)
     *     // Addresses reserved for benchmarking (`2001: 2./ 48`)
     *     check(Ipv6Addr.new(0x2001, 2, 0, 0, 0, 0, 0, 1).isGlobal() == false)
     *     // Addresses reserved for documentation (`2001: db8./ 32` and `3fff./ 20`)
     *     check(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 1).isGlobal() == false)
     *     check(Ipv6Addr.new(0x3fff, 0, 0, 0, 0, 0, 0, 0).isGlobal() == false)
     *     // Unique local addresses (`fc00./ 7`)
     *     check(Ipv6Addr.new(0xfc02, 0, 0, 0, 0, 0, 0, 1).isGlobal() == false)
     *     // Unicast addresses with link-local scope (`fe80./ 10`)
     *     check(Ipv6Addr.new(0xfe81, 0, 0, 0, 0, 0, 0, 1).isGlobal() == false)
     *     // For a complete overview see the IANA IPv6 Special-Purpose Address Registry.
     * }
     * ```
     */
    public open fun isGlobal(): Boolean =
        !(this.isUnspecified() || this.isLoopback() ||
            run {
                val matchVal = this.segments()
                when {
                    matchVal.size == 8 && matchVal[0] == 0 && matchVal[1] == 0 && matchVal[2] == 0 && matchVal[3] == 0 && matchVal[4] == 0 && matchVal[5] == 0xffff -> true
                    else -> false
                }
            } ||
            run {
                val matchVal1 = this.segments()
                when {
                    matchVal1.size == 8 && matchVal1[0] == 0x64 && matchVal1[1] == 0xff9b && matchVal1[2] == 1 -> true
                    else -> false
                }
            } ||
            run {
                val matchVal2 = this.segments()
                when {
                    matchVal2.size == 8 && matchVal2[0] == 0x100 && matchVal2[1] == 0 && matchVal2[2] == 0 && matchVal2[3] == 0 -> true
                    else -> false
                }
            } ||
            run {
                val matchVal3 = this.segments()
                when {
                    matchVal3.size == 8 && matchVal3[0] == 0x100 && matchVal3[1] == 0 && matchVal3[2] == 0 && matchVal3[3] == 1 -> true
                    else -> false
                }
            } ||
            (run {
                val matchVal4 = this.segments()
                when {
                    matchVal4.size == 8 && matchVal4[0] == 0x2001 && matchVal4[1] < 0x200 -> {
                        val b = matchVal4[1]
                        true
                    }
                    else -> false
                }
            } &&
                !(U128.fromBeBytes(this.octets()) == U128(0x2001000100000000uL, 0x1uL) || U128.fromBeBytes(this.octets()) == U128(0x2001000100000000uL, 0x2uL) || U128.fromBeBytes(this.octets()) == U128(0x2001000100000000uL, 0x3uL) ||
                    run {
                        val matchVal5 = this.segments()
                        when {
                            matchVal5.size == 8 && matchVal5[0] == 0x2001 && matchVal5[1] == 3 -> true
                            else -> false
                        }
                    } ||
                    run {
                        val matchVal6 = this.segments()
                        when {
                            matchVal6.size == 8 && matchVal6[0] == 0x2001 && matchVal6[1] == 4 && matchVal6[2] == 0x112 -> true
                            else -> false
                        }
                    } ||
                    run {
                        val matchVal7 = this.segments()
                        when {
                            matchVal7.size == 8 && matchVal7[0] == 0x2001 && matchVal7[1] >= 0x20 && matchVal7[1] <= 0x3F -> {
                                val b1 = matchVal7[1]
                                true
                            }
                            else -> false
                        }
                    })) ||
            run {
                val matchVal8 = this.segments()
                when {
                    matchVal8.size == 8 && matchVal8[0] == 0x2002 -> true
                    else -> false
                }
            } || this.isDocumentation() ||
            run {
                val matchVal9 = this.segments()
                when {
                    matchVal9.size >= 1 && matchVal9[0] == 0x5f00 -> true
                    else -> false
                }
            } || this.isUniqueLocal() || this.isUnicastLinkLocal())

    /**
     * Returns [trueVal] if this is a unique local address (`fc00./ 7`).
     *
     * This property is defined in [IETF RFC 4193].
     *
     * [IETF RFC 4193]: https://tools.ietf.org/html/rfc4193
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isUniqueLocal() == false)
     * check(Ipv6Addr.new(0xfc02, 0, 0, 0, 0, 0, 0, 0).isUniqueLocal() == true)
     * ```
     */
    public open fun isUniqueLocal(): Boolean = (this.segments()[0] and 0xfe00u.toUShort()) == 0xfc00u.toUShort()

    /**
     * Returns [trueVal] if this is a unicast address, as defined by [IETF RFC 4291].
     * Any address that is not a [multicast address] (`ff00./ 8`) is unicast.
     *
     * [IETF RFC 4291]: https://tools.ietf.org/html/rfc4291
     * [multicast address]: Ipv6Addr.isMulticast
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     // The unspecified and loopback addresses are unicast.
     *     check(Ipv6Addr.UNSPECIFIED.isUnicast() == true)
     *     check(Ipv6Addr.LOCALHOST.isUnicast() == true)
     *     // Any address that is not a multicast address (`ff00./ 8`) is unicast.
     *     check(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0).isUnicast() == true)
     *     check(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0).isUnicast() == false)
     * }
     * ```
     */
    public open fun isUnicast(): Boolean = !this.isMulticast()

    /**
     * Returns `true` if the address is a unicast address with link-local scope,
     * as defined in [RFC 4291].
     *
     * A unicast address has link-local scope if it has the prefix `fe80./ 10`, as per [RFC 4291 section 2.4].
     * Note that this encompasses more addresses than those defined in [RFC 4291 section 2.5.6],
     * which describes "Link-Local IPv6 Unicast Addresses" as having the following stricter format:
     *
     * ```text
     * | 10 bits  |         54 bits         |          64 bits           |
     * +----------+-------------------------+----------------------------+
     * |1111111010|           0             |       interface ID         |
     * +----------+-------------------------+----------------------------+
     * ```
     * So while currently the only addresses with link-local scope an application will encounter are all in `fe80./ 64`,
     * this might change in the future with the publication of new standards. More addresses in `fe80./ 10` could be allocated,
     * and those addresses will have link-local scope.
     *
     * Also note that while [RFC 4291 section 2.5.3] mentions about the [loopback address] (`.1`) that "it is treated as having Link-Local scope",
     * this does not mean that the loopback address actually has link-local scope and this method will return `false` on it.
     *
     * [RFC 4291]: https://tools.ietf.org/html/rfc4291
     * [RFC 4291 section 2.4]: https://tools.ietf.org/html/rfc4291#section-2.4
     * [RFC 4291 section 2.5.3]: https://tools.ietf.org/html/rfc4291#section-2.5.3
     * [RFC 4291 section 2.5.6]: https://tools.ietf.org/html/rfc4291#section-2.5.6
     * [loopback address]: Ipv6Addr.Localhost
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * // The loopback address (`.1`) does not actually have link-local scope.
     * check(Ipv6Addr.LOCALHOST.isUnicastLinkLocal() == false)
     * // Only addresses in `fe80./ 10` have link-local scope.
     * check(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0).isUnicastLinkLocal() == false)
     * check(Ipv6Addr.new(0xfe80, 0, 0, 0, 0, 0, 0, 0).isUnicastLinkLocal() == true)
     * // Addresses outside the stricter `fe80./ 64` also have link-local scope.
     * check(Ipv6Addr.new(0xfe80, 0, 0, 1, 0, 0, 0, 0).isUnicastLinkLocal() == true)
     * check(Ipv6Addr.new(0xfe81, 0, 0, 0, 0, 0, 0, 0).isUnicastLinkLocal() == true)
     * ```
     */
    public open fun isUnicastLinkLocal(): Boolean = (this.segments()[0] and 0xffc0u.toUShort()) == 0xfe80u.toUShort()

    /**
     * Returns [trueVal] if this is an address reserved for documentation
     * (`2001: db8./ 32` and `3fff./ 20`).
     *
     * This property is defined by [IETF RFC 3849] and [IETF RFC 9637].
     *
     * [IETF RFC 3849]: https://tools.ietf.org/html/rfc3849
     * [IETF RFC 9637]: https://tools.ietf.org/html/rfc9637
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isDocumentation() == false)
     *     check(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0).isDocumentation() == true)
     *     check(Ipv6Addr.new(0x3fff, 0, 0, 0, 0, 0, 0, 0).isDocumentation() == true)
     * }
     * ```
     */
    public open fun isDocumentation(): Boolean =
        run {
            val matchVal = this.segments()
            when {
                matchVal.size >= 2 && matchVal[0] == 0x2001 && matchVal[1] == 0xdb8 -> true
                matchVal.size >= 2 && matchVal[0] == 0x3fff && matchVal[1] in 0..0x0fff -> true
                else -> false
            }
        }

    /**
     * Returns [trueVal] if this is an address reserved for benchmarking (`2001: 2./ 48`).
     *
     * This property is defined in [IETF RFC 5180], where it is mistakenly specified as covering the range `2001: 0200./ 48`.
     * This is corrected in [IETF RFC Errata 1752] to `2001: 0002./ 48`.
     *
     * [IETF RFC 5180]: https://tools.ietf.org/html/rfc5180
     * [IETF RFC Errata 1752]: https://www.rfc-editor.org/errataSearch.php?eid=1752
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc613, 0x0).isBenchmarking() == false)
     *     check(Ipv6Addr.new(0x2001, 0x2, 0, 0, 0, 0, 0, 0).isBenchmarking() == true)
     * }
     * ```
     */
    public open fun isBenchmarking(): Boolean = (this.segments()[0] == 0x2001u.toUShort()) && (this.segments()[1] == 0x2u.toUShort()) && (this.segments()[2] == 0u.toUShort())

    /**
     * Returns [trueVal] if the address is a globally routable unicast address.
     *
     * The following return false:
     *
     * - the loopback address
     * - the link-local addresses
     * - unique local addresses
     * - the unspecified address
     * - the address range reserved for documentation
     *
     * This method returns [trueVal] for site-local addresses as per [RFC 4291 section 2.5.7]
     *
     * ```kotlin
     * The special behavior of[the site - local unicast] prefix defined in [RFC3513] must no longer be supported in new implementations(i.e., new implementations must treat this prefix as Global Unicast).
     * ```
     *
     * [RFC 4291 section 2.5.7]: https://tools.ietf.org/html/rfc4291#section-2.5.7
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0).isUnicastGlobal() == false)
     *     check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isUnicastGlobal() == true)
     * }
     * ```
     */
    public open fun isUnicastGlobal(): Boolean = this.isUnicast() && !this.isLoopback() && !this.isUnicastLinkLocal() && !this.isUniqueLocal() && !this.isUnspecified() && !this.isDocumentation() && !this.isBenchmarking()

    /**
     * Returns the address's multicast scope if the address is multicast.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     * import std.net.Ipv6MulticastScope
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     check(Ipv6Addr.new(0xff0e, 0, 0, 0, 0, 0, 0, 0).multicastScope() == Ipv6MulticastScope.Global)
     *     check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).multicastScope() == null)
     * }
     * ```
     */
    public open fun multicastScope(): Ipv6MulticastScope? =
        if (this.isMulticast()) {
            when (this.segments()[0] and 0x000fu.toUShort()) {
                0x0 -> Ipv6MulticastScope.Reserved0
                0x1 -> Ipv6MulticastScope.InterfaceLocal
                0x2 -> Ipv6MulticastScope.LinkLocal
                0x3 -> Ipv6MulticastScope.RealmLocal
                0x4 -> Ipv6MulticastScope.AdminLocal
                0x5 -> Ipv6MulticastScope.SiteLocal
                0x6 -> Ipv6MulticastScope.Unassigned6
                0x7 -> Ipv6MulticastScope.Unassigned7
                0x8 -> Ipv6MulticastScope.OrganizationLocal
                0x9 -> Ipv6MulticastScope.Unassigned9
                0xA -> Ipv6MulticastScope.UnassignedA
                0xB -> Ipv6MulticastScope.UnassignedB
                0xC -> Ipv6MulticastScope.UnassignedC
                0xD -> Ipv6MulticastScope.UnassignedD
                0xE -> Ipv6MulticastScope.Global
                0xF -> Ipv6MulticastScope.ReservedF
                else -> error("unreachable")
            }
        } else {
            null
        }

    /**
     * Returns [trueVal] if this is a multicast address (`ff00./ 8`).
     *
     * This property is defined by [IETF RFC 4291].
     *
     * [IETF RFC 4291]: https://tools.ietf.org/html/rfc4291
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0).isMulticast() == true)
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).isMulticast() == false)
     * ```
     */
    public open fun isMulticast(): Boolean = (this.segments()[0] and 0xff00u.toUShort()) == 0xff00u.toUShort()

    /**
     * Returns [trueVal] if the address is an IPv4-mapped address (`.ffff: 0: 0 / 96`).
     *
     * IPv4-mapped addresses can be converted to their canonical IPv4 address with
     * [`toIpv4Mapped`](Ipv6Addr.toIpv4Mapped).
     *
     * # Examples
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * @feature(ip)
     * public fun doctestEntry() {
     *     val ipv4Mapped = Ipv4Addr.new(192, 0, 2, 255).toIpv6Mapped()
     *     check(ipv4Mapped.isIpv4Mapped() == true)
     *     check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc000, 0x2ff).isIpv4Mapped() == true)
     *     check(Ipv6Addr.new(0x2001, 0xdb8, 0, 0, 0, 0, 0, 0).isIpv4Mapped() == false)
     * }
     * ```
     */
    public open fun isIpv4Mapped(): Boolean =
        run {
            val matchVal = this.segments()
            when {
                matchVal.size == 8 && matchVal[0] == 0 && matchVal[1] == 0 && matchVal[2] == 0 && matchVal[3] == 0 && matchVal[4] == 0 && matchVal[5] == 0xffff -> true
                else -> false
            }
        }

    /**
     * Converts this address to an [`IPv4` address] if it's an [IPv4-mapped] address,
     * as defined in [IETF RFC 4291 section 2.5.5.2], otherwise returns [None].
     *
     * `.ffff: a.b.c.d` becomes `a.b.c.d`.
     * All addresses *not* starting with `.ffff` will return `None`.
     *
     * [`IPv4` address]: Ipv4Addr
     * [IPv4-mapped]: Ipv6Addr
     * [IETF RFC 4291 section 2.5.5.2]: https://tools.ietf.org/html/rfc4291#section-2.5.5.2
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0).toIpv4Mapped() == null)
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).toIpv4Mapped() == Ipv4Addr.new(192, 10, 2, 255))
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1).toIpv4Mapped() == null)
     * ```
     */
    public open fun toIpv4Mapped(): Ipv4Addr? =
        run {
            val matchVal = this.octets()
            when {
                matchVal.size == 16 && matchVal[0] == 0 && matchVal[1] == 0 && matchVal[2] == 0 && matchVal[3] == 0 && matchVal[4] == 0 && matchVal[5] == 0 && matchVal[6] == 0 && matchVal[7] == 0 && matchVal[8] == 0 && matchVal[9] == 0 && matchVal[10] == 0xff && matchVal[11] == 0xff -> {
                    val a = matchVal[12]
                    val b = matchVal[13]
                    val c = matchVal[14]
                    val d = matchVal[15]
                    Ipv4Addr.new(a, b, c, d)
                }
                else -> null
            }
        }

    /**
     * Converts this address to an [`IPv4` address] if it is either
     * an [IPv4-compatible] address as defined in [IETF RFC 4291 section 2.5.5.1],
     * or an [IPv4-mapped] address as defined in [IETF RFC 4291 section 2.5.5.2],
     * otherwise returns [None].
     *
     * Note that this will return an [`IPv4` address] for the IPv6 loopback address `.1`. Use
     * [Ipv6Addr.toIpv4Mapped] to avoid this.
     *
     * `.a.b.c.d` and `.ffff: a.b.c.d` become `a.b.c.d`. `.1` becomes `0.0.0.1`.
     * All addresses *not* starting with either all zeroes or `.ffff` will return `None`.
     *
     * [`IPv4` address]: Ipv4Addr
     * [IPv4-compatible]: Ipv6Addr#ipv4-compatible-ipv6-addresses
     * [IPv4-mapped]: Ipv6Addr#ipv4-mapped-ipv6-addresses
     * [IETF RFC 4291 section 2.5.5.1]: https://tools.ietf.org/html/rfc4291#section-2.5.5.1
     * [IETF RFC 4291 section 2.5.5.2]: https://tools.ietf.org/html/rfc4291#section-2.5.5.2
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv4Addr
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0).toIpv4() == null)
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff).toIpv4() == Ipv4Addr.new(192, 10, 2, 255))
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1).toIpv4() == Ipv4Addr.new(0, 0, 0, 1))
     * ```
     */
    public open fun toIpv4(): Ipv4Addr? =
        if (this.segments() != null) {
            val ab = this.segments()[6]
            val cd = this.segments()[7]
            val (a, b) = ab.toBeBytes()
            val (c, d) = cd.toBeBytes()
            Ipv4Addr.new(a, b, c, d)
        } else {
            null
        }

    /**
     * Converts this address to an `IpAddr.V4` if it is an IPv4-mapped address,
     * otherwise returns self wrapped in an `IpAddr.V6`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0x7f00, 0x1).isLoopback() == false)
     * check(Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0x7f00, 0x1).toCanonical().isLoopback() == true)
     * ```
     */
    public open fun toCanonical(): IpAddr {
        when (val matchVal = this.toIpv4Mapped()) {
            null -> {}
            else -> {
                val mapped = matchVal
                return IpAddr.v4(mapped)
            }
        }
        return IpAddr.v6(this)
    }

    /**
     * Returns the sixteen eight-bit integers the IPv6 address consists of.
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * check(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0).octets() == listOf(0xff, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
     * ```
     */
    public open fun octets(): UByteArray = this.octets

    /**
     * Returns the sixteen eight-bit integers the IPv6 address consists of
     * as a slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.Ipv6Addr
     *
     * @feature(ipAsOctets)
     * public fun doctestEntry() {
     *     check(Ipv6Addr.new(0xff00, 0, 0, 0, 0, 0, 0, 0).asOctets() == listOf(255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
     * }
     * ```
     */
    public open fun asOctets(): UByteArray = this.octets

    /**
     * Writes an Ipv6Addr, conforming to the canonical style described by
     * [RFC 5952](https://tools.ietf.org/html/rfc5952).
     */
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        // If there are no alignment requirements, write the IP address directly to `f`.
        // Otherwise, write it to a local buffer and then use `f.pad`.
        return if (f.precision() == null && f.width() == null) {
            val segments = this.segments()
            when (val matchVal = this.toIpv4Mapped()) {
                null -> {
                    open class Span(
                        public var start: Int,
                        public var len: Int,
                    ) {
                        public open fun clone(): Span = Span(start, len)
                    }
                    /**
                     * Writes a colon-separated part of the address.
                     */
                    fun fmtSubslice(f1: StringBuilder, chunk: List<UShort>) {
                        when (val matchVal = chunk.splitFirst()) {
                            null -> {}
                            else -> {
                                val first = matchVal.first
                                val tail = matchVal.second
                                f1.writeFmt("$first")
                                for (segment in tail) {
                                    f1.append(':')
                                    f1.writeFmt("$segment")
                                }
                            }
                        }
                    }
                    // Find the inner 0 span
                    val zeroes = run {
                        var longest = Span.default()
                        var current = Span.default()
                        for ((i, segment) in segments.mapIndexed { index, item -> Pair(index, item) }) {
                            if (segment == 0) {
                                if (current.len == 0) {
                                    current.start = i
                                }
                                current.len += 1
                                if (current.len > longest.len) {
                                    longest = current
                                }
                            } else {
                                current = Span.default()
                            }
                        }
                        longest
                    }
                    if (zeroes.len > 1) {
                        fmtSubslice(f, ((segments).sliceArray(0 until zeroes.start)))
                        f.append("::")
                        run { fmtSubslice(f, ((segments).sliceArray(zeroes.start + zeroes.len until segments.size))); Result.Ok(Unit) }
                    } else {
                        run { fmtSubslice(f, segments); Result.Ok(Unit) }
                    }
                }
                else -> {
                    val ipv4 = matchVal
                    f.writeFmt("::ffff:$ipv4")
                }
            }
        } else {
            val LONGEST_IPV6_ADDR: String = "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff"
            val buf = DisplayBuffer.buffer(LONGEST_IPV6_ADDR.len())
            val buf1 = DisplayBuffer.new(buf)
            buf1.writeFmt("$this").unwrap()
            run { f.append(buf1.asStr()); Result.Ok(Unit) }
        }
    }

    public open fun fmtDebug(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append((this).toString()); Result.Ok(Unit) }

    public open fun eq(other: IpAddr): Boolean =
        when (other) {
            is IpAddr.V4 -> false
            is IpAddr.V6 -> {
                val (v6) = other
                this == v6
            }
        }

    public open fun partialCmp(other: Ipv6Addr): Ordering? = this.cmp(other)

    public open fun partialCmp(other: IpAddr): Ordering? =
        when (other) {
            is IpAddr.V4 -> Ordering.Greater
            is IpAddr.V6 -> {
                val (v6) = other
                this.partialCmp(v6)
            }
        }

    public open fun cmp(other: Ipv6Addr): Ordering = this.segments().cmp(other.segments())

    public override fun not(): Ipv6Addr {
        var idx = 0
        while (idx < 16) {
            this.octets[idx] = (!this.octets[idx]).toUByte()
            idx += 1
        }
        return this
    }

    public override fun bitandAssign(rhs: Ipv6Addr) {
        var idx = 0
        while (idx < this.octets.size) {
            this.octets[idx].bitandAssign(rhs.octets[idx])
            idx += 1
        }
    }

    public open fun bitandAssignIpv6Addr(rhs: Ipv6Addr) {
        this.bitandAssign(rhs)
    }

    public override fun bitand(rhs: Ipv6Addr): Ipv6Addr {
        this.bitandAssign(rhs)
        return this
    }

    public open fun bitandIpv6Addr(rhs: Ipv6Addr): Ipv6Addr {
        this.bitandAssign(rhs)
        return this
    }

    public override fun bitorAssign(rhs: Ipv6Addr) {
        var idx = 0
        while (idx < this.octets.size) {
            this.octets[idx].bitorAssign(rhs.octets[idx])
            idx += 1
        }
    }

    public open fun bitorAssignIpv6Addr(rhs: Ipv6Addr) {
        this.bitorAssign(rhs)
    }

    public override fun bitor(rhs: Ipv6Addr): Ipv6Addr {
        this.bitorAssign(rhs)
        return this
    }

    public open fun bitorIpv6Addr(rhs: Ipv6Addr): Ipv6Addr {
        this.bitorAssign(rhs)
        return this
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Ipv6Addr> =
            object : TypeLayout<Ipv6Addr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Ipv6Addr =
                    Ipv6Addr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Ipv6Addr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.octets)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ipv6Addr =
                    Ipv6Addr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ipv6Addr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.octets)
                }
            }

        /**
         * Creates a new IPv6 address from eight 16-bit segments.
         *
         * The result will represent the IP address `a: b: c: d: e: f: g: h`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv6Addr
         *
         * val addr = Ipv6Addr.new(0, 0, 0, 0, 0, 0xffff, 0xc00a, 0x2ff)
         * ```
         */
        public fun new(a: UShort, b: UShort, c: UShort, d: UShort, e: UShort, f: UShort, g: UShort, h: UShort): Ipv6Addr {
            val addr16 = ushortArrayOf((((((a).toUInt() and 0xFFu) shl 8) or (((a).toUInt() shr 8) and 0xFFu)).toUShort()), (((((b).toUInt() and 0xFFu) shl 8) or (((b).toUInt() shr 8) and 0xFFu)).toUShort()), (((((c).toUInt() and 0xFFu) shl 8) or (((c).toUInt() shr 8) and 0xFFu)).toUShort()), (((((d).toUInt() and 0xFFu) shl 8) or (((d).toUInt() shr 8) and 0xFFu)).toUShort()), (((((e).toUInt() and 0xFFu) shl 8) or (((e).toUInt() shr 8) and 0xFFu)).toUShort()), (((((f).toUInt() and 0xFFu) shl 8) or (((f).toUInt() shr 8) and 0xFFu)).toUShort()), (((((g).toUInt() and 0xFFu) shl 8) or (((g).toUInt() shr 8) and 0xFFu)).toUShort()), (((((h).toUInt() and 0xFFu) shl 8) or (((h).toUInt() shr 8) and 0xFFu)).toUShort()))
            return Ipv6Addr(octets = transmute<UByteArray>(addr16))
        }

        /**
         * The size of an IPv6 address in bits.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv6Addr
         *
         * check(Ipv6Addr.BITS == 128)
         * ```
         */
        public val BITS: UInt = 128u

        /**
         * Converts a native byte order `ULong` into an IPv6 address.
         *
         * See [Ipv6Addr.toBits] for an explanation on endianness.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv6Addr
         *
         * val addr = Ipv6Addr.fromBits(U128(0x1020304050607080uL, 0x90a0b0c0d0e0f00duL))
         * check(Ipv6Addr.new(0x1020, 0x3040, 0x5060, 0x7080, 0x90A0, 0xB0C0, 0xD0E0, 0xF00D) == addr)
         * ```
         */
        public fun fromBits(bits: U128): Ipv6Addr = Ipv6Addr(octets = bits.toBeBytes())

        /**
         * An IPv6 address representing localhost: `.1`.
         *
         * This corresponds to constant `IN6ADDR_LOOPBACK_INIT` or `in6addrLoopback` in other
         * languages.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv6Addr
         *
         * val addr = Ipv6Addr.LOCALHOST
         * check(addr == Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 1))
         * ```
         */
        public val LOCALHOST: Ipv6Addr = Ipv6Addr.new(0u, 0u, 0u, 0u, 0u, 0u, 0u, 1u)

        /**
         * An IPv6 address representing the unspecified address: `.`.
         *
         * This corresponds to constant `IN6ADDR_ANY_INIT` or `in6addrAny` in other languages.
         *
         * # Examples
         *
         * ```kotlin
         * import std.net.Ipv6Addr
         *
         * val addr = Ipv6Addr.UNSPECIFIED
         * check(addr == Ipv6Addr.new(0, 0, 0, 0, 0, 0, 0, 0))
         * ```
         */
        public val UNSPECIFIED: Ipv6Addr = Ipv6Addr.new(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)

        /**
         * Creates an `Ipv6Addr` from an eight element 16-bit array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.Ipv6Addr
         *
         * public fun doctestEntry() {
         *     val addr = Ipv6Addr.fromSegments(ushortArrayOf(0x20du.toUShort(), 0x20cu.toUShort(), 0x20bu.toUShort(), 0x20au.toUShort(), 0x209u.toUShort(), 0x208u.toUShort(), 0x207u.toUShort(), 0x206u.toUShort()))
         *     check(Ipv6Addr.new(0x20d, 0x20c, 0x20b, 0x20a, 0x209, 0x208, 0x207, 0x206) == addr)
         * }
         * ```
         */
        public fun fromSegments(segments: UShortArray): Ipv6Addr {
            val (a, b, c, d, e, f, g, h) = segments
            return Ipv6Addr.new(a, b, c, d, e.toUShort(), f, g, h)
        }

        /**
         * Creates an `Ipv6Addr` from a sixteen element byte array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.Ipv6Addr
         *
         * public fun doctestEntry() {
         *     val addr = Ipv6Addr.fromOctets(ubyteArrayOf(0x19u.toUByte(), 0x18u.toUByte(), 0x17u.toUByte(), 0x16u.toUByte(), 0x15u.toUByte(), 0x14u.toUByte(), 0x13u.toUByte(), 0x12u.toUByte(), 0x11u.toUByte(), 0x10u.toUByte(), 0x0fu.toUByte(), 0x0eu.toUByte(), 0x0du.toUByte(), 0x0cu.toUByte(), 0x0bu.toUByte(), 0x0au.toUByte()))
         *     check(Ipv6Addr.new(0x1918, 0x1716, 0x1514, 0x1312, 0x1110, 0x0f0e, 0x0d0c, 0x0b0a) == addr)
         * }
         * ```
         */
        public fun fromOctets(octets: UByteArray): Ipv6Addr = Ipv6Addr(octets = octets)

        /**
         * Uses [Ipv6Addr.fromBits] to convert a host byte order `ULong` to an IPv6 address.
         */
        public fun from(ip: U128): Ipv6Addr = Ipv6Addr.fromBits(ip)

        /**
         * Creates an `Ipv6Addr` from a sixteen element byte array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.Ipv6Addr
         *
         * public fun doctestEntry() {
         *     val addr = Ipv6Addr.from(ubyteArrayOf(0x19u.toUByte(), 0x18u.toUByte(), 0x17u.toUByte(), 0x16u.toUByte(), 0x15u.toUByte(), 0x14u.toUByte(), 0x13u.toUByte(), 0x12u.toUByte(), 0x11u.toUByte(), 0x10u.toUByte(), 0x0fu.toUByte(), 0x0eu.toUByte(), 0x0du.toUByte(), 0x0cu.toUByte(), 0x0bu.toUByte(), 0x0au.toUByte()))
         *     check(Ipv6Addr.new(0x1918, 0x1716, 0x1514, 0x1312, 0x1110, 0x0f0e, 0x0d0c, 0x0b0a) == addr)
         * }
         * ```
         */
        public fun from(octets: UByteArray): Ipv6Addr = Ipv6Addr(octets = octets)

        /**
         * Creates an `Ipv6Addr` from an eight element 16-bit array.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.net.Ipv6Addr
         *
         * public fun doctestEntry() {
         *     val addr = Ipv6Addr.from(ushortArrayOf(0x20du.toUShort(), 0x20cu.toUShort(), 0x20bu.toUShort(), 0x20au.toUShort(), 0x209u.toUShort(), 0x208u.toUShort(), 0x207u.toUShort(), 0x206u.toUShort()))
         *     check(Ipv6Addr.new(0x20d, 0x20c, 0x20b, 0x20a, 0x209, 0x208, 0x207, 0x206) == addr)
         * }
         * ```
         */
        public fun from(segments: UShortArray): Ipv6Addr {
            val (a, b, c, d, e, f, g, h) = segments
            return Ipv6Addr.new(a, b, c, d, e.toUShort(), f, g, h)
        }
    }
}

/**
 * Scope of an [IPv6 multicast address] as defined in [IETF RFC 7346 section 2],
 * which updates [IETF RFC 4291 section 2.7].
 *
 * # Stability Guarantees
 *
 * Scopes 0 and F are currently reserved by IETF, and may be assigned in the future.
 * For this reason, the enum variants for those two scopes are not currently nameable.
 * You can still check for them in your code using `as` casts.
 *
 * # Examples
 *
 * ```kotlin
 * import std.net.Ipv6Addr
 * import std.net.Ipv6MulticastScope.*
 *
 * @feature(ip)
 * public fun doctestEntry() {
 *     // An IPv6 multicast address with global scope (`ff0e.`).
 *     val address = Ipv6Addr.new(0xff0e, 0, 0, 0, 0, 0, 0, 0)
 *     // Will print "Global scope".
 *     run {
 *         val matchVal = address.multicastScope()
 *         when {
 *             matchVal != null && matchVal == InterfaceLocal -> println("Interface-Local scope")
 *             matchVal != null && matchVal == LinkLocal -> println("Link-Local scope")
 *             matchVal != null && matchVal == RealmLocal -> println("Realm-Local scope")
 *             matchVal != null && matchVal == AdminLocal -> println("Admin-Local scope")
 *             matchVal != null && matchVal == SiteLocal -> println("Site-Local scope")
 *             matchVal != null && matchVal == OrganizationLocal -> println("Organization-Local scope")
 *             matchVal != null && matchVal == Global -> println("Global scope")
 *             matchVal != null -> {
 *                 val s = matchVal
 *                 val snum = s.toUByte()
 *                 if (when (val matchVal1 = 0x0 or 0xF) {
 *                     else -> {
 *                         val snum1 = matchVal1
 *                         true
 *                     }
 *                     else -> false
 *                 }) {
 *                     println("Reserved scope $snum")
 *                 } else {
 *                     println("Unassigned scope $snum")
 *                 }
 *             }
 *             else -> println("Not a multicast address!")
 *         }
 *     }
 * }
 * ```
 *
 * [IPv6 multicast address]: Ipv6Addr
 * [IETF RFC 7346 section 2]: https://tools.ietf.org/html/rfc7346#section-2
 * [IETF RFC 4291 section 2.7]: https://datatracker.ietf.org/doc/html/rfc4291#section-2.7
 */
public sealed class Ipv6MulticastScope(
    public val value: Int,
) {
    /**
     * Reserved by IETF.
     */
    public data object Reserved0 : Ipv6MulticastScope(0x0)
    /**
     * Interface-Local scope.
     */
    public data object InterfaceLocal : Ipv6MulticastScope(0x1)
    /**
     * Link-Local scope.
     */
    public data object LinkLocal : Ipv6MulticastScope(0x2)
    /**
     * Realm-Local scope.
     */
    public data object RealmLocal : Ipv6MulticastScope(0x3)
    /**
     * Admin-Local scope.
     */
    public data object AdminLocal : Ipv6MulticastScope(0x4)
    /**
     * Site-Local scope.
     */
    public data object SiteLocal : Ipv6MulticastScope(0x5)
    /**
     * Scope 6. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object Unassigned6 : Ipv6MulticastScope(0x6)
    /**
     * Scope 7. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object Unassigned7 : Ipv6MulticastScope(0x7)
    /**
     * Organization-Local scope.
     */
    public data object OrganizationLocal : Ipv6MulticastScope(0x8)
    /**
     * Scope 9. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object Unassigned9 : Ipv6MulticastScope(0x9)
    /**
     * Scope A. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object UnassignedA : Ipv6MulticastScope(0xA)
    /**
     * Scope B. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object UnassignedB : Ipv6MulticastScope(0xB)
    /**
     * Scope C. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object UnassignedC : Ipv6MulticastScope(0xC)
    /**
     * Scope D. Unassigned, available for administrators
     * to define additional multicast regions.
     */
    public data object UnassignedD : Ipv6MulticastScope(0xD)
    /**
     * Global scope.
     */
    public data object Global : Ipv6MulticastScope(0xE)
    /**
     * Reserved by IETF.
     */
    public data object ReservedF : Ipv6MulticastScope(0xF)

    public fun rustEquals(other: Ipv6MulticastScope): Boolean = this == other
}

/**
 * Uses [Ipv4Addr.toBits] to convert an IPv4 address to a host byte order `UInt`.
 */
public fun fromUInt(ip: Ipv4Addr): UInt = ip.toBits().toUInt()

/**
 * Uses [Ipv6Addr.toBits] to convert an IPv6 address to a host byte order `ULong`.
 */
public fun fromU128(ip: Ipv6Addr): U128 = ip.toBits()
