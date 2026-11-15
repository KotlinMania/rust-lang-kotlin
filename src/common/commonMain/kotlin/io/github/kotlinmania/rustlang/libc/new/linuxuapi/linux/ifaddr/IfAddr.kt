// port-lint: source new/linux_uapi/linux/if_addr.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.ifaddr

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

// Header: `uapi / linux / ifAddr.h`

public data class Ifaddrmsg(
    public var ifaFamily: UByte = 0u,
    public var ifaPrefixlen: UByte = 0u,
    public var ifaFlags: UByte = 0u,
    public var ifaScope: UByte = 0u,
    public var ifaIndex: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Ifaddrmsg> =
            object : TypeLayout<Ifaddrmsg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Ifaddrmsg =
                    Ifaddrmsg(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Ifaddrmsg) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.ifaFamily)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.ifaPrefixlen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.ifaFlags)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.ifaScope)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.ifaIndex)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ifaddrmsg =
                    Ifaddrmsg(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ifaddrmsg) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.ifaFamily)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.ifaPrefixlen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.ifaFlags)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.ifaScope)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.ifaIndex)
                }
            }
    }
}

public val IFA_UNSPEC: CUshort = 0u

public val IFA_ADDRESS: CUshort = 1u

public val IFA_LOCAL: CUshort = 2u

public val IFA_LABEL: CUshort = 3u

public val IFA_BROADCAST: CUshort = 4u

public val IFA_ANYCAST: CUshort = 5u

public val IFA_CACHEINFO: CUshort = 6u

public val IFA_MULTICAST: CUshort = 7u

public val IFA_FLAGS: CUshort = 8u

public val IFA_F_SECONDARY: UInt = 0x01u

public val IFA_F_TEMPORARY: UInt = 0x01u

public val IFA_F_NODAD: UInt = 0x02u

public val IFA_F_OPTIMISTIC: UInt = 0x04u

public val IFA_F_DADFAILED: UInt = 0x08u

public val IFA_F_HOMEADDRESS: UInt = 0x10u

public val IFA_F_DEPRECATED: UInt = 0x20u

public val IFA_F_TENTATIVE: UInt = 0x40u

public val IFA_F_PERMANENT: UInt = 0x80u

public val IFA_F_MANAGETEMPADDR: UInt = 0x100u

public val IFA_F_NOPREFIXROUTE: UInt = 0x200u

public val IFA_F_MCAUTOJOIN: UInt = 0x400u

public val IFA_F_STABLE_PRIVACY: UInt = 0x800u
