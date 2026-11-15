// port-lint: source new/linux_uapi/linux/if_packet.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.ifpacket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SockaddrLl

// Header: `uapi / linux / ifPacket.h`

public open class SockaddrPkt(
    public var spktFamily: CUshort,
    public var spktDevice: UByteArray,
    public var spktProtocol: CUshort,
) {
    public open fun clone(): SockaddrPkt = SockaddrPkt(spktFamily, spktDevice, spktProtocol)

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

public open class TpacketStats(
    public var tpPackets: CUint,
    public var tpDrops: CUint,
) {
    public open fun clone(): TpacketStats = TpacketStats(tpPackets, tpDrops)

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

public open class TpacketStatsV3(
    public var tpPackets: CUint,
    public var tpDrops: CUint,
    public var tpFreezeQCnt: CUint,
) {
    public open fun clone(): TpacketStatsV3 = TpacketStatsV3(tpPackets, tpDrops, tpFreezeQCnt)

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

public open class TpacketRolloverStats(
    public var tpAll: U64,
    public var tpHuge: U64,
    public var tpFailed: U64,
) {
    public open fun clone(): TpacketRolloverStats = TpacketRolloverStats(tpAll, tpHuge, tpFailed)

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

public open class TpacketAuxdata(
    public var tpStatus: UInt,
    public var tpLen: UInt,
    public var tpSnaplen: UInt,
    public var tpMac: UShort,
    public var tpNet: UShort,
    public var tpVlanTci: UShort,
    public var tpVlanTpid: UShort,
) {
    public open fun clone(): TpacketAuxdata = TpacketAuxdata(tpStatus, tpLen, tpSnaplen, tpMac, tpNet, tpVlanTci, tpVlanTpid)

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

public open class TpacketHdr(
    public var tpStatus: CUlong,
    public var tpLen: CUint,
    public var tpSnaplen: CUint,
    public var tpMac: CUshort,
    public var tpNet: CUshort,
    public var tpSec: CUint,
    public var tpUsec: CUint,
) {
    public open fun clone(): TpacketHdr = TpacketHdr(tpStatus, tpLen, tpSnaplen, tpMac, tpNet, tpSec, tpUsec)

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

public open class Tpacket2Hdr(
    public var tpStatus: UInt,
    public var tpLen: UInt,
    public var tpSnaplen: UInt,
    public var tpMac: UShort,
    public var tpNet: UShort,
    public var tpSec: UInt,
    public var tpNsec: UInt,
    public var tpVlanTci: UShort,
    public var tpVlanTpid: UShort,
    public var tpPadding: UByteArray,
) {
    public open fun clone(): Tpacket2Hdr = Tpacket2Hdr(tpStatus, tpLen, tpSnaplen, tpMac, tpNet, tpSec, tpNsec, tpVlanTci, tpVlanTpid, tpPadding)

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

public open class TpacketHdrVariant1(
    public var tpRxhash: UInt,
    public var tpVlanTci: UInt,
    public var tpVlanTpid: UShort,
    public var tpPadding: UShort,
) {
    public open fun clone(): TpacketHdrVariant1 = TpacketHdrVariant1(tpRxhash, tpVlanTci, tpVlanTpid, tpPadding)

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

public open class Tpacket3Hdr(
    public var tpNextOffset: UInt,
    public var tpSec: UInt,
    public var tpNsec: UInt,
    public var tpSnaplen: UInt,
    public var tpLen: UInt,
    public var tpStatus: UInt,
    public var tpMac: UShort,
    public var tpNet: UShort,
    public var hv1: TpacketHdrVariant1,
    public var tpPadding: UByteArray,
) {
    public open fun clone(): Tpacket3Hdr = Tpacket3Hdr(tpNextOffset, tpSec, tpNsec, tpSnaplen, tpLen, tpStatus, tpMac, tpNet, hv1, tpPadding)

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

public open class TpacketBdTs(
    public var tsSec: CUint,
    public var tsUsec: CUint,
) {
    public open fun clone(): TpacketBdTs = TpacketBdTs(tsSec, tsUsec)

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

public open class TpacketHdrV1(
    public var blockStatus: UInt,
    public var numPkts: UInt,
    public var offsetToFirstPkt: UInt,
    public var blkLen: UInt,
    public var seqNum: U64,
    public var tsFirstPkt: TpacketBdTs,
    public var tsLastPkt: TpacketBdTs,
) {
    public open fun clone(): TpacketHdrV1 = TpacketHdrV1(blockStatus, numPkts, offsetToFirstPkt, blkLen, seqNum, tsFirstPkt, tsLastPkt)

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

public open class TpacketBdHeaderU(
    public var bh1: TpacketHdrV1? = null,
)

public open class TpacketBlockDesc(
    public var version: UInt,
    public var offsetToPriv: UInt,
    public var hdr: TpacketBdHeaderU,
) {
    public open fun clone(): TpacketBlockDesc = TpacketBlockDesc(version, offsetToPriv, hdr)
}

public val PACKET_MR_PROMISC: CInt = 1

public val PACKET_MR_ALLMULTI: CInt = 2

public sealed class TpacketVersions {
    public data object TpacketV1 : TpacketVersions()
    public data object TpacketV2 : TpacketVersions()
    public data object TpacketV3 : TpacketVersions()
}

public open class TpacketReq(
    public var tpBlockSize: CUint,
    public var tpBlockNr: CUint,
    public var tpFrameSize: CUint,
    public var tpFrameNr: CUint,
) {
    public open fun clone(): TpacketReq = TpacketReq(tpBlockSize, tpBlockNr, tpFrameSize, tpFrameNr)

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

public open class TpacketReq3(
    public var tpBlockSize: CUint,
    public var tpBlockNr: CUint,
    public var tpFrameSize: CUint,
    public var tpFrameNr: CUint,
    public var tpRetireBlkTov: CUint,
    public var tpSizeofPriv: CUint,
    public var tpFeatureReqWord: CUint,
) {
    public open fun clone(): TpacketReq3 = TpacketReq3(tpBlockSize, tpBlockNr, tpFrameSize, tpFrameNr, tpRetireBlkTov, tpSizeofPriv, tpFeatureReqWord)

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

public open class TpacketReqU(
    public var req: TpacketReq? = null,
    public var req3: TpacketReq3? = null,
)

public open class PacketMreq(
    public var mrIfindex: CInt,
    public var mrType: CUshort,
    public var mrAlen: CUshort,
    public var mrAddress: UByteArray,
) {
    public open fun clone(): PacketMreq = PacketMreq(mrIfindex, mrType, mrAlen, mrAddress)

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

public open class FanoutArgs(
    public var id: UShort,
    public var typeFlags: UShort,
    public var maxNumMembers: UInt,
) {
    public open fun clone(): FanoutArgs = FanoutArgs(id, typeFlags, maxNumMembers)

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

public val TPACKET_HDRLEN: Int = ((sizeOf<TpacketHdr>(typeLayoutT = TpacketHdr.typeLayout()) + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()) + sizeOf<SockaddrLl>(typeLayoutT = UnresolvedTypeLayout<SockaddrLl>())

public val TPACKET2_HDRLEN: Int = ((sizeOf<Tpacket2Hdr>(typeLayoutT = Tpacket2Hdr.typeLayout()) + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()) + sizeOf<SockaddrLl>(typeLayoutT = UnresolvedTypeLayout<SockaddrLl>())

public val TPACKET3_HDRLEN: Int = ((sizeOf<Tpacket3Hdr>(typeLayoutT = Tpacket3Hdr.typeLayout()) + TPACKET_ALIGNMENT - 1) and (TPACKET_ALIGNMENT - 1).inv()) + sizeOf<SockaddrLl>(typeLayoutT = UnresolvedTypeLayout<SockaddrLl>())
