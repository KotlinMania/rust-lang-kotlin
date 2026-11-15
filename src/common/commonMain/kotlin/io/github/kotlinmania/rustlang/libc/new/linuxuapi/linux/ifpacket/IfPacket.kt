// port-lint: source new/linux_uapi/linux/if_packet.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.ifpacket

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.unix.linuxlike.SockaddrLl

// Header: `uapi / linux / ifPacket.h`

public data class SockaddrPkt(
    public var spktFamily: CUshort = 0u,
    public var spktDevice: UByteArray = UByteArray(14),
    public var spktProtocol: CUshort = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrPkt) return false
        if (this.spktFamily != other.spktFamily) return false
        if (!this.spktDevice.contentEquals(other.spktDevice)) return false
        if (this.spktProtocol != other.spktProtocol) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.spktFamily.hashCode()
        result = 31 * result + this.spktDevice.contentHashCode()
        result = 31 * result + this.spktProtocol.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrPkt> =
            object : TypeLayout<SockaddrPkt> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 18L
                override fun readNative(addr: Long): SockaddrPkt =
                    SockaddrPkt(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SockaddrPkt) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.spktFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.spktDevice)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.spktProtocol)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrPkt =
                    SockaddrPkt(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrPkt) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.spktFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.spktDevice)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.spktProtocol)
                }
            }
    }
}

public val PACKET_HOST: CUchar = 0u

public val PACKET_BROADCAST: CUchar = 1u

public val PACKET_MULTICAST: CUchar = 2u

public val PACKET_OTHERHOST: CUchar = 3u

public val PACKET_OUTGOING: CUchar = 4u

public val PACKET_LOOPBACK: CUchar = 5u

public val PACKET_USER: CUchar = 6u

public val PACKET_KERNEL: CUchar = 7u

public val PACKET_ADD_MEMBERSHIP: CInt = 1

public val PACKET_DROP_MEMBERSHIP: CInt = 2

public val PACKET_RECV_OUTPUT: CInt = 3

public val PACKET_RX_RING: CInt = 5

public val PACKET_STATISTICS: CInt = 6

public val PACKET_COPY_THRESH: CInt = 7

public val PACKET_AUXDATA: CInt = 8

public val PACKET_ORIGDEV: CInt = 9

public val PACKET_VERSION: CInt = 10

public val PACKET_HDRLEN: CInt = 11

public val PACKET_RESERVE: CInt = 12

public val PACKET_TX_RING: CInt = 13

public val PACKET_LOSS: CInt = 14

public val PACKET_VNET_HDR: CInt = 15

public val PACKET_TX_TIMESTAMP: CInt = 16

public val PACKET_TIMESTAMP: CInt = 17

public val PACKET_FANOUT: CInt = 18

public val PACKET_TX_HAS_OFF: CInt = 19

public val PACKET_QDISC_BYPASS: CInt = 20

public val PACKET_ROLLOVER_STATS: CInt = 21

public val PACKET_FANOUT_DATA: CInt = 22

public val PACKET_IGNORE_OUTGOING: CInt = 23

public val PACKET_VNET_HDR_SZ: CInt = 24

public val PACKET_FANOUT_HASH: CUint = 0u

public val PACKET_FANOUT_LB: CUint = 1u

public val PACKET_FANOUT_CPU: CUint = 2u

public val PACKET_FANOUT_ROLLOVER: CUint = 3u

public val PACKET_FANOUT_RND: CUint = 4u

public val PACKET_FANOUT_QM: CUint = 5u

public val PACKET_FANOUT_CBPF: CUint = 6u

public val PACKET_FANOUT_EBPF: CUint = 7u

public val PACKET_FANOUT_FLAG_ROLLOVER: CUint = 0x1000u

public val PACKET_FANOUT_FLAG_UNIQUEID: CUint = 0x2000u

public val PACKET_FANOUT_FLAG_IGNORE_OUTGOING: CUint = 0x4000u

public val PACKET_FANOUT_FLAG_DEFRAG: CUint = 0x8000u

public data class TpacketStats(
    public var tpPackets: CUint = 0u,
    public var tpDrops: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketStats> =
            object : TypeLayout<TpacketStats> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TpacketStats =
                    TpacketStats(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: TpacketStats) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpPackets)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpDrops)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketStats =
                    TpacketStats(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketStats) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpPackets)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpDrops)
                }
            }
    }
}

public data class TpacketStatsV3(
    public var tpPackets: CUint = 0u,
    public var tpDrops: CUint = 0u,
    public var tpFreezeQCnt: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketStatsV3> =
            object : TypeLayout<TpacketStatsV3> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): TpacketStatsV3 =
                    TpacketStatsV3(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: TpacketStatsV3) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpPackets)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpDrops)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpFreezeQCnt)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketStatsV3 =
                    TpacketStatsV3(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketStatsV3) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpPackets)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpDrops)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpFreezeQCnt)
                }
            }
    }
}

public data class TpacketRolloverStats(
    public var tpAll: U64 = 0uL,
    public var tpHuge: U64 = 0uL,
    public var tpFailed: U64 = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketRolloverStats> =
            object : TypeLayout<TpacketRolloverStats> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): TpacketRolloverStats =
                    TpacketRolloverStats(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: TpacketRolloverStats) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.tpAll)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.tpHuge)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.tpFailed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketRolloverStats =
                    TpacketRolloverStats(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketRolloverStats) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.tpAll)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.tpHuge)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.tpFailed)
                }
            }
    }
}

public data class TpacketAuxdata(
    public var tpStatus: UInt = 0u,
    public var tpLen: UInt = 0u,
    public var tpSnaplen: UInt = 0u,
    public var tpMac: UShort = 0u,
    public var tpNet: UShort = 0u,
    public var tpVlanTci: UShort = 0u,
    public var tpVlanTpid: UShort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketAuxdata> =
            object : TypeLayout<TpacketAuxdata> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): TpacketAuxdata =
                    TpacketAuxdata(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 18L),
                    )
                override fun writeNative(addr: Long, value: TpacketAuxdata) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpSnaplen)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.tpMac)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.tpNet)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.tpVlanTci)
                    (PrimitiveLayouts.U16).writeNative(addr + 18L, value.tpVlanTpid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketAuxdata =
                    TpacketAuxdata(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 18),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketAuxdata) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpSnaplen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.tpMac)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.tpNet)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.tpVlanTci)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 18, value.tpVlanTpid)
                }
            }
    }
}

public val TP_STATUS_KERNEL: UInt = 0u

public val TP_STATUS_USER: UInt = 1u shl 0

public val TP_STATUS_COPY: UInt = 1u shl 1

public val TP_STATUS_LOSING: UInt = 1u shl 2

public val TP_STATUS_CSUMNOTREADY: UInt = 1u shl 3

public val TP_STATUS_VLAN_VALID: UInt = 1u shl 4

public val TP_STATUS_BLK_TMO: UInt = 1u shl 5

public val TP_STATUS_VLAN_TPID_VALID: UInt = 1u shl 6

public val TP_STATUS_CSUM_VALID: UInt = 1u shl 7

public val TP_STATUS_AVAILABLE: UInt = 0u

public val TP_STATUS_SEND_REQUEST: UInt = 1u shl 0

public val TP_STATUS_SENDING: UInt = 1u shl 1

public val TP_STATUS_WRONG_FORMAT: UInt = 1u shl 2

public val TP_STATUS_TS_SOFTWARE: UInt = 1u shl 29

public val TP_STATUS_TS_SYS_HARDWARE: UInt = 1u shl 30

public val TP_STATUS_TS_RAW_HARDWARE: UInt = 1u shl 31

public val TP_FT_REQ_FILL_RXHASH: UInt = 1u

public data class TpacketHdr(
    public var tpStatus: CUlong = 0uL,
    public var tpLen: CUint = 0u,
    public var tpSnaplen: CUint = 0u,
    public var tpMac: CUshort = 0u,
    public var tpNet: CUshort = 0u,
    public var tpSec: CUint = 0u,
    public var tpUsec: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketHdr> =
            object : TypeLayout<TpacketHdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): TpacketHdr =
                    TpacketHdr(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 18L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: TpacketHdr) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.tpStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.tpSnaplen)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.tpMac)
                    (PrimitiveLayouts.U16).writeNative(addr + 18L, value.tpNet)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tpSec)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.tpUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketHdr =
                    TpacketHdr(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketHdr) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.tpStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.tpSnaplen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.tpMac)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 18, value.tpNet)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tpSec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.tpUsec)
                }
            }
    }
}

public val TPACKET_ALIGNMENT: Int = 16

public fun tpacketAlign(x: Int): Int = (x + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()

public val PACKET_MR_MULTICAST: CInt = 0

public data class Tpacket2Hdr(
    public var tpStatus: UInt = 0u,
    public var tpLen: UInt = 0u,
    public var tpSnaplen: UInt = 0u,
    public var tpMac: UShort = 0u,
    public var tpNet: UShort = 0u,
    public var tpSec: UInt = 0u,
    public var tpNsec: UInt = 0u,
    public var tpVlanTci: UShort = 0u,
    public var tpVlanTpid: UShort = 0u,
    public var tpPadding: UByteArray = UByteArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tpacket2Hdr) return false
        if (this.tpStatus != other.tpStatus) return false
        if (this.tpLen != other.tpLen) return false
        if (this.tpSnaplen != other.tpSnaplen) return false
        if (this.tpMac != other.tpMac) return false
        if (this.tpNet != other.tpNet) return false
        if (this.tpSec != other.tpSec) return false
        if (this.tpNsec != other.tpNsec) return false
        if (this.tpVlanTci != other.tpVlanTci) return false
        if (this.tpVlanTpid != other.tpVlanTpid) return false
        if (!this.tpPadding.contentEquals(other.tpPadding)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.tpStatus.hashCode()
        result = 31 * result + this.tpLen.hashCode()
        result = 31 * result + this.tpSnaplen.hashCode()
        result = 31 * result + this.tpMac.hashCode()
        result = 31 * result + this.tpNet.hashCode()
        result = 31 * result + this.tpSec.hashCode()
        result = 31 * result + this.tpNsec.hashCode()
        result = 31 * result + this.tpVlanTci.hashCode()
        result = 31 * result + this.tpVlanTpid.hashCode()
        result = 31 * result + this.tpPadding.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Tpacket2Hdr> =
            object : TypeLayout<Tpacket2Hdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Tpacket2Hdr =
                    Tpacket2Hdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U16).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 26L),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: Tpacket2Hdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpSnaplen)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.tpMac)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.tpNet)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.tpSec)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tpNsec)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.tpVlanTci)
                    (PrimitiveLayouts.U16).writeNative(addr + 26L, value.tpVlanTpid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 28L, value.tpPadding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tpacket2Hdr =
                    Tpacket2Hdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 26),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tpacket2Hdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpSnaplen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.tpMac)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.tpNet)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.tpSec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tpNsec)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.tpVlanTci)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 26, value.tpVlanTpid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 28, value.tpPadding)
                }
            }
    }
}

public data class TpacketHdrVariant1(
    public var tpRxhash: UInt = 0u,
    public var tpVlanTci: UInt = 0u,
    public var tpVlanTpid: UShort = 0u,
    public var tpPadding: UShort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketHdrVariant1> =
            object : TypeLayout<TpacketHdrVariant1> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): TpacketHdrVariant1 =
                    TpacketHdrVariant1(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: TpacketHdrVariant1) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpRxhash)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpVlanTci)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.tpVlanTpid)
                    (PrimitiveLayouts.U16).writeNative(addr + 10L, value.tpPadding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketHdrVariant1 =
                    TpacketHdrVariant1(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketHdrVariant1) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpRxhash)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpVlanTci)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.tpVlanTpid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 10, value.tpPadding)
                }
            }
    }
}

public data class Tpacket3Hdr(
    public var tpNextOffset: UInt = 0u,
    public var tpSec: UInt = 0u,
    public var tpNsec: UInt = 0u,
    public var tpSnaplen: UInt = 0u,
    public var tpLen: UInt = 0u,
    public var tpStatus: UInt = 0u,
    public var tpMac: UShort = 0u,
    public var tpNet: UShort = 0u,
    public var hv1: TpacketHdrVariant1 = TpacketHdrVariant1(),
    public var tpPadding: UByteArray = UByteArray(8),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tpacket3Hdr) return false
        if (this.tpNextOffset != other.tpNextOffset) return false
        if (this.tpSec != other.tpSec) return false
        if (this.tpNsec != other.tpNsec) return false
        if (this.tpSnaplen != other.tpSnaplen) return false
        if (this.tpLen != other.tpLen) return false
        if (this.tpStatus != other.tpStatus) return false
        if (this.tpMac != other.tpMac) return false
        if (this.tpNet != other.tpNet) return false
        if (this.hv1 != other.hv1) return false
        if (!this.tpPadding.contentEquals(other.tpPadding)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.tpNextOffset.hashCode()
        result = 31 * result + this.tpSec.hashCode()
        result = 31 * result + this.tpNsec.hashCode()
        result = 31 * result + this.tpSnaplen.hashCode()
        result = 31 * result + this.tpLen.hashCode()
        result = 31 * result + this.tpStatus.hashCode()
        result = 31 * result + this.tpMac.hashCode()
        result = 31 * result + this.tpNet.hashCode()
        result = 31 * result + this.hv1.hashCode()
        result = 31 * result + this.tpPadding.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Tpacket3Hdr> =
            object : TypeLayout<Tpacket3Hdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Tpacket3Hdr =
                    Tpacket3Hdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U16).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 26L),
                        (TpacketHdrVariant1.typeLayout()).readNative(addr + 28L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Tpacket3Hdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpNextOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpSec)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpNsec)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.tpSnaplen)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.tpLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tpStatus)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.tpMac)
                    (PrimitiveLayouts.U16).writeNative(addr + 26L, value.tpNet)
                    (TpacketHdrVariant1.typeLayout()).writeNative(addr + 28L, value.hv1)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 40L, value.tpPadding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tpacket3Hdr =
                    Tpacket3Hdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 26),
                        (TpacketHdrVariant1.typeLayout()).readBytes(buf, offset + 28),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tpacket3Hdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpNextOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpSec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpNsec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.tpSnaplen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.tpLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tpStatus)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.tpMac)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 26, value.tpNet)
                    (TpacketHdrVariant1.typeLayout()).writeBytes(buf, offset + 28, value.hv1)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 40, value.tpPadding)
                }
            }
    }
}

public data class TpacketBdTs(
    public var tsSec: CUint = 0u,
    public var tsUsec: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketBdTs> =
            object : TypeLayout<TpacketBdTs> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TpacketBdTs =
                    TpacketBdTs(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: TpacketBdTs) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tsSec)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tsUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketBdTs =
                    TpacketBdTs(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketBdTs) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tsSec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tsUsec)
                }
            }
    }
}

public data class TpacketHdrV1(
    public var blockStatus: UInt = 0u,
    public var numPkts: UInt = 0u,
    public var offsetToFirstPkt: UInt = 0u,
    public var blkLen: UInt = 0u,
    public var seqNum: U64 = 0uL,
    public var tsFirstPkt: TpacketBdTs = TpacketBdTs(),
    public var tsLastPkt: TpacketBdTs = TpacketBdTs(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketHdrV1> =
            object : TypeLayout<TpacketHdrV1> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): TpacketHdrV1 =
                    TpacketHdrV1(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (TpacketBdTs.typeLayout()).readNative(addr + 24L),
                        (TpacketBdTs.typeLayout()).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: TpacketHdrV1) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.blockStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.numPkts)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.offsetToFirstPkt)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.blkLen)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.seqNum)
                    (TpacketBdTs.typeLayout()).writeNative(addr + 24L, value.tsFirstPkt)
                    (TpacketBdTs.typeLayout()).writeNative(addr + 32L, value.tsLastPkt)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketHdrV1 =
                    TpacketHdrV1(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (TpacketBdTs.typeLayout()).readBytes(buf, offset + 24),
                        (TpacketBdTs.typeLayout()).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketHdrV1) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.blockStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.numPkts)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.offsetToFirstPkt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.blkLen)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.seqNum)
                    (TpacketBdTs.typeLayout()).writeBytes(buf, offset + 24, value.tsFirstPkt)
                    (TpacketBdTs.typeLayout()).writeBytes(buf, offset + 32, value.tsLastPkt)
                }
            }
    }
}

public data class TpacketBdHeaderU(
    public var bh1: TpacketHdrV1 = TpacketHdrV1(),
)

public data class TpacketBlockDesc(
    public var version: UInt = 0u,
    public var offsetToPriv: UInt = 0u,
    public var hdr: TpacketBdHeaderU = TpacketBdHeaderU(),
)

public val PACKET_MR_PROMISC: CInt = 1

public val PACKET_MR_ALLMULTI: CInt = 2

public enum class TpacketVersions(
    public val value: UInt,
) {
    TPACKET_V1(0u),
    TPACKET_V2(1u),
    TPACKET_V3(2u),
}

public data class TpacketReq(
    public var tpBlockSize: CUint = 0u,
    public var tpBlockNr: CUint = 0u,
    public var tpFrameSize: CUint = 0u,
    public var tpFrameNr: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketReq> =
            object : TypeLayout<TpacketReq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): TpacketReq =
                    TpacketReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: TpacketReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpBlockSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpBlockNr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpFrameSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.tpFrameNr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketReq =
                    TpacketReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpBlockSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpBlockNr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpFrameSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.tpFrameNr)
                }
            }
    }
}

public data class TpacketReq3(
    public var tpBlockSize: CUint = 0u,
    public var tpBlockNr: CUint = 0u,
    public var tpFrameSize: CUint = 0u,
    public var tpFrameNr: CUint = 0u,
    public var tpRetireBlkTov: CUint = 0u,
    public var tpSizeofPriv: CUint = 0u,
    public var tpFeatureReqWord: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TpacketReq3> =
            object : TypeLayout<TpacketReq3> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 28L
                override fun readNative(addr: Long): TpacketReq3 =
                    TpacketReq3(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: TpacketReq3) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.tpBlockSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tpBlockNr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tpFrameSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.tpFrameNr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.tpRetireBlkTov)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tpSizeofPriv)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.tpFeatureReqWord)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TpacketReq3 =
                    TpacketReq3(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TpacketReq3) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.tpBlockSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tpBlockNr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tpFrameSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.tpFrameNr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.tpRetireBlkTov)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tpSizeofPriv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.tpFeatureReqWord)
                }
            }
    }
}

public data class TpacketReqU(
    public var req: TpacketReq = TpacketReq(),
    public var req3: TpacketReq3 = TpacketReq3(),
)

public data class PacketMreq(
    public var mrIfindex: CInt = 0,
    public var mrType: CUshort = 0u,
    public var mrAlen: CUshort = 0u,
    public var mrAddress: UByteArray = UByteArray(8),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PacketMreq) return false
        if (this.mrIfindex != other.mrIfindex) return false
        if (this.mrType != other.mrType) return false
        if (this.mrAlen != other.mrAlen) return false
        if (!this.mrAddress.contentEquals(other.mrAddress)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.mrIfindex.hashCode()
        result = 31 * result + this.mrType.hashCode()
        result = 31 * result + this.mrAlen.hashCode()
        result = 31 * result + this.mrAddress.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PacketMreq> =
            object : TypeLayout<PacketMreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): PacketMreq =
                    PacketMreq(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PacketMreq) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.mrIfindex)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.mrType)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.mrAlen)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.mrAddress)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PacketMreq =
                    PacketMreq(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PacketMreq) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.mrIfindex)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.mrType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.mrAlen)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.mrAddress)
                }
            }
    }
}

public data class FanoutArgs(
    public var id: UShort = 0u,
    public var typeFlags: UShort = 0u,
    public var maxNumMembers: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FanoutArgs> =
            object : TypeLayout<FanoutArgs> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FanoutArgs =
                    FanoutArgs(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: FanoutArgs) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.id)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.typeFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.maxNumMembers)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FanoutArgs =
                    FanoutArgs(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FanoutArgs) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.typeFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.maxNumMembers)
                }
            }
    }
}

public val TPACKET_HDRLEN: Int = ((sizeOf<TpacketHdr>(typeLayoutT = TpacketHdr.typeLayout()) + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()) + sizeOf<SockaddrLl>(typeLayoutT = ObjectSlotLayout<SockaddrLl>())

public val TPACKET2_HDRLEN: Int = ((sizeOf<Tpacket2Hdr>(typeLayoutT = Tpacket2Hdr.typeLayout()) + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()) + sizeOf<SockaddrLl>(typeLayoutT = ObjectSlotLayout<SockaddrLl>())

public val TPACKET3_HDRLEN: Int = ((sizeOf<Tpacket3Hdr>(typeLayoutT = Tpacket3Hdr.typeLayout()) + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()) + sizeOf<SockaddrLl>(typeLayoutT = ObjectSlotLayout<SockaddrLl>())
