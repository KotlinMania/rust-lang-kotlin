// port-lint: source new/helenos/inet/addr.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr

// HelenOS internet address types
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/inet/include/inet/addr.h>

public typealias Addr32T = UInt

public typealias Addr128T = UByteArray

public typealias IpVerT = UInt

public val IP_ANY: IpVerT = 0u

public val IP_V4: IpVerT = IP_ANY + 1u

public val IP_V6: IpVerT = IP_V4 + 1u

public data class InetAddrTAddrUnion(
    public var addr: Addr32T = 0u,
    public var addr6: Addr128T = UByteArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InetAddrTAddrUnion) return false
        if (this.addr != other.addr) return false
        if (!this.addr6.contentEquals(other.addr6)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.addr.hashCode()
        result = 31 * result + this.addr6.contentHashCode()
        return result
    }
}

public data class InetAddrT(
    public var version: IpVerT = 0u,
    public var addr: InetAddrTAddrUnion = InetAddrTAddrUnion(),
)
