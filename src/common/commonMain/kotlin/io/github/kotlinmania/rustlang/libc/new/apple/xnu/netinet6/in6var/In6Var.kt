// port-lint: source new/apple/xnu/netinet6/in6_var.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.xnu.netinet6.in6var

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.apple.xnu.sys.ioccom.iow
import io.github.kotlinmania.rustlang.libc.new.apple.xnu.sys.ioccom.iowr
import io.github.kotlinmania.rustlang.libc.unix.bsd.SockaddrIn6
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.In6Addrlifetime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.In6Ifreq

// Header: `netinet6 / in6Var.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/bsd/netinet6/in6Var.h>

public open class In6Aliasreq(
    public var ifraName: UByteArray,
    public var ifraAddr: SockaddrIn6,
    public var ifraDstaddr: SockaddrIn6,
    public var ifraPrefixmask: SockaddrIn6,
    public var ifraFlags: CInt,
    public var ifraLifetime: In6Addrlifetime,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): In6Aliasreq = In6Aliasreq(ifraName, ifraAddr, ifraDstaddr, ifraPrefixmask, ifraFlags, ifraLifetime, nonExhaustive)

    public companion object {
        public fun default(): In6Aliasreq = In6Aliasreq(ifraName = Default.default(), ifraAddr = Default.default(), ifraDstaddr = Default.default(), ifraPrefixmask = Default.default(), ifraFlags = Default.default(), ifraLifetime = Default.default(), nonExhaustive = Unit)
    }
}

public val IN6_IFF_ANYCAST: CInt = 0x0001

public val IN6_IFF_TENTATIVE: CInt = 0x0002

public val IN6_IFF_DUPLICATED: CInt = 0x0004

public val IN6_IFF_DETACHED: CInt = 0x0008

public val IN6_IFF_DEPRECATED: CInt = 0x0010

public val IN6_IFF_NODAD: CInt = 0x0020

public val IN6_IFF_AUTOCONF: CInt = 0x0040

public val IN6_IFF_TEMPORARY: CInt = 0x0080

public val IN6_IFF_DYNAMIC: CInt = 0x0100

public val IN6_IFF_OPTIMISTIC: CInt = 0x0200

public val IN6_IFF_SECURED: CInt = 0x0400

public val IN6_IFF_CLAT46: CInt = 0x1000

public val IN6_IFF_NOPFX: CInt = 0x8000

public val SIOCDIFADDR_IN6: CUlong = iow<In6Ifreq>('i'.code.toULong(), 25uL)

public val SIOCAIFADDR_IN6: CUlong = iow<In6Aliasreq>('i'.code.toULong(), 26uL)

public val SIOCSIFPHYADDR_IN6: CUlong = iow<In6Aliasreq>('i'.code.toULong(), 62uL)

public val SIOCGIFAFLAG_IN6: CUlong = iowr<In6Ifreq>('i'.code.toULong(), 73uL)
