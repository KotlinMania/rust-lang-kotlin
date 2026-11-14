// port-lint: source new/linux_uapi/linux/netlink.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.netlink

import io.github.kotlinmania.rustlang.core.ffi.CChar
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.SaFamilyT
import io.github.kotlinmania.rustlang.libc.unix.SizeT

// Header: `uapi / linux / netlink.h`

public val NETLINK_ROUTE: CInt = 0

public val NETLINK_UNUSED: CInt = 1

public val NETLINK_USERSOCK: CInt = 2

public val NETLINK_FIREWALL: CInt = 3

public val NETLINK_SOCK_DIAG: CInt = 4

public val NETLINK_NFLOG: CInt = 5

public val NETLINK_XFRM: CInt = 6

public val NETLINK_SELINUX: CInt = 7

public val NETLINK_ISCSI: CInt = 8

public val NETLINK_AUDIT: CInt = 9

public val NETLINK_FIB_LOOKUP: CInt = 10

public val NETLINK_CONNECTOR: CInt = 11

public val NETLINK_NETFILTER: CInt = 12

public val NETLINK_IP6_FW: CInt = 13

public val NETLINK_DNRTMSG: CInt = 14

public val NETLINK_KOBJECT_UEVENT: CInt = 15

public val NETLINK_GENERIC: CInt = 16

public val NETLINK_SCSITRANSPORT: CInt = 18

public val NETLINK_ECRYPTFS: CInt = 19

public val NETLINK_RDMA: CInt = 20

public val NETLINK_CRYPTO: CInt = 21

public val NETLINK_INET_DIAG: CInt = NETLINK_SOCK_DIAG

public val MAX_LINKS: CInt = 32

public data class SockaddrNl(
    public var nlFamily: SaFamilyT = 0u,
    public var nlPad: CUshort = 0u,
    public var nlPid: UInt = 0u,
    public var nlGroups: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrNl> =
            object : TypeLayout<SockaddrNl> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): SockaddrNl =
                    SockaddrNl(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrNl) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.nlFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.nlPad)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nlPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nlGroups)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrNl =
                    SockaddrNl(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrNl) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.nlFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.nlPad)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nlPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nlGroups)
                }
            }
    }
}

public data class Nlmsghdr(
    public var nlmsgLen: UInt = 0u,
    public var nlmsgType: UShort = 0u,
    public var nlmsgFlags: UShort = 0u,
    public var nlmsgSeq: UInt = 0u,
    public var nlmsgPid: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Nlmsghdr> =
            object : TypeLayout<Nlmsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Nlmsghdr =
                    Nlmsghdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Nlmsghdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nlmsgLen)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.nlmsgType)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.nlmsgFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nlmsgSeq)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.nlmsgPid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nlmsghdr =
                    Nlmsghdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nlmsghdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nlmsgLen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.nlmsgType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.nlmsgFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nlmsgSeq)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.nlmsgPid)
                }
            }
    }
}

public val NLM_F_REQUEST: CInt = 1

public val NLM_F_MULTI: CInt = 2

public val NLM_F_ACK: CInt = 4

public val NLM_F_ECHO: CInt = 8

public val NLM_F_DUMP_INTR: CInt = 16

public val NLM_F_DUMP_FILTERED: CInt = 32

public val NLM_F_ROOT: CInt = 0x100

public val NLM_F_MATCH: CInt = 0x200

public val NLM_F_ATOMIC: CInt = 0x400

public val NLM_F_DUMP: CInt = NLM_F_ROOT or NLM_F_MATCH

public val NLM_F_REPLACE: CInt = 0x100

public val NLM_F_EXCL: CInt = 0x200

public val NLM_F_CREATE: CInt = 0x400

public val NLM_F_APPEND: CInt = 0x800

public val NLM_F_NONREC: CInt = 0x100

public val NLM_F_CAPPED: CInt = 0x100

public val NLM_F_ACK_TLVS: CInt = 0x200

public val NLMSG_ALIGNTO: CUint = 4u

public val NLMSG_NOOP: CInt = 0x1

public fun nlmsgAlign(size: SizeT): CInt = ((size + NLMSG_ALIGNTO.toULong() - 1uL) and (NLMSG_ALIGNTO.toULong() - 1uL).inv()).toInt()

public fun nlmsgLength(size: SizeT): CInt = size.toInt() + NLMSG_HDRLEN

public fun nlmsgSpace(size: SizeT): CInt = nlmsgAlign(nlmsgLength(size).toULong())

public fun nlmsgData(nlh: MutPtr): MutPtr = MutPtr((MutPtr((MutPtr((nlh).rawAddress)).rawAddress + ((NLMSG_HDRLEN).toLong() * (ObjectSlotLayout<CChar>()).strideBytes))).rawAddress)

public fun nlmsgNext(nlh: MutPtr, size: CInt): MutPtr {
    var size1 = size
    size1 -= nlmsgAlign(((Nlmsghdr.typeLayout()).readNative((nlh).rawAddress).nlmsgLen).toULong())
    return MutPtr((MutPtr((MutPtr((nlh).rawAddress)).rawAddress + ((nlmsgAlign(((Nlmsghdr.typeLayout()).readNative((nlh).rawAddress).nlmsgLen).toULong())).toLong() * (ObjectSlotLayout<CChar>()).strideBytes))).rawAddress)
}

public fun nlmsgOk(nlh: ConstPtr, size: CInt): Boolean = size >= sizeOf<Nlmsghdr>(typeLayoutT = Nlmsghdr.typeLayout()).toInt() && (Nlmsghdr.typeLayout()).readNative((nlh).rawAddress).nlmsgLen >= sizeOf<Nlmsghdr>(typeLayoutT = Nlmsghdr.typeLayout()).toUInt() && (Nlmsghdr.typeLayout()).readNative((nlh).rawAddress).nlmsgLen <= size.toUInt()

public fun nlmsgPayload(nlh: ConstPtr, size: CInt): CInt = ((Nlmsghdr.typeLayout()).readNative((nlh).rawAddress).nlmsgLen).toInt() - nlmsgSpace(size.toULong())

public val NLMSG_ERROR: CInt = 0x2

public val NLMSG_DONE: CInt = 0x3

public val NLMSG_OVERRUN: CInt = 0x4

public val NLMSG_MIN_TYPE: CInt = 0x10

public val NETLINK_ADD_MEMBERSHIP: CInt = 1

public data class Nlmsgerr(
    public var error: CInt = 0,
    public var msg: Nlmsghdr = Nlmsghdr(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Nlmsgerr> =
            object : TypeLayout<Nlmsgerr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): Nlmsgerr =
                    Nlmsgerr(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (Nlmsghdr.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Nlmsgerr) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.error)
                    (Nlmsghdr.typeLayout()).writeNative(addr + 4L, value.msg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nlmsgerr =
                    Nlmsgerr(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (Nlmsghdr.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nlmsgerr) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.error)
                    (Nlmsghdr.typeLayout()).writeBytes(buf, offset + 4, value.msg)
                }
            }
    }
}

public val NETLINK_DROP_MEMBERSHIP: CInt = 2

public val NETLINK_PKTINFO: CInt = 3

public val NETLINK_BROADCAST_ERROR: CInt = 4

public val NETLINK_NO_ENOBUFS: CInt = 5

public val NETLINK_RX_RING: CInt = 6

public val NETLINK_TX_RING: CInt = 7

public val NETLINK_LISTEN_ALL_NSID: CInt = 8

public val NETLINK_LIST_MEMBERSHIPS: CInt = 9

public val NETLINK_CAP_ACK: CInt = 10

public val NETLINK_EXT_ACK: CInt = 11

public val NETLINK_GET_STRICT_CHK: CInt = 12

public val NLA_F_NESTED: CInt = 1 shl 15

public data class NlPktinfo(
    public var group: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<NlPktinfo> =
            object : TypeLayout<NlPktinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): NlPktinfo =
                    NlPktinfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: NlPktinfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.group)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NlPktinfo =
                    NlPktinfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NlPktinfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.group)
                }
            }
    }
}

public data class NlMmapReq(
    public var nmBlockSize: CUint = 0u,
    public var nmBlockNr: CUint = 0u,
    public var nmFrameSize: CUint = 0u,
    public var nmFrameNr: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<NlMmapReq> =
            object : TypeLayout<NlMmapReq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): NlMmapReq =
                    NlMmapReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: NlMmapReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nmBlockSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nmBlockNr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nmFrameSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.nmFrameNr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NlMmapReq =
                    NlMmapReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NlMmapReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nmBlockSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nmBlockNr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nmFrameSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.nmFrameNr)
                }
            }
    }
}

public data class NlMmapHdr(
    public var nmStatus: CUint = 0u,
    public var nmLen: CUint = 0u,
    public var nmGroup: UInt = 0u,
    public var nmPid: UInt = 0u,
    public var nmUid: UInt = 0u,
    public var nmGid: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<NlMmapHdr> =
            object : TypeLayout<NlMmapHdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): NlMmapHdr =
                    NlMmapHdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: NlMmapHdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nmStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nmLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nmGroup)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.nmPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.nmUid)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.nmGid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NlMmapHdr =
                    NlMmapHdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NlMmapHdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nmStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nmLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nmGroup)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.nmPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.nmUid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.nmGid)
                }
            }
    }
}

public data class Nlattr(
    public var nlaLen: UShort = 0u,
    public var nlaType: UShort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Nlattr> =
            object : TypeLayout<Nlattr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Nlattr =
                    Nlattr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Nlattr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.nlaLen)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.nlaType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nlattr =
                    Nlattr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nlattr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.nlaLen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.nlaType)
                }
            }
    }
}

public val NLA_F_NET_BYTEORDER: CInt = 1 shl 14

public val NLA_TYPE_MASK: CInt = (NLA_F_NESTED or NLA_F_NET_BYTEORDER).inv()

public val NLA_ALIGNTO: CInt = 4

public val NLMSG_HDRLEN: CInt = nlmsgAlign(sizeOf<Nlmsghdr>(typeLayoutT = Nlmsghdr.typeLayout()).toULong())

public fun nlaAlign(len: CInt): CInt = ((len) + NLA_ALIGNTO - 1) and (NLA_ALIGNTO - 1).inv()
