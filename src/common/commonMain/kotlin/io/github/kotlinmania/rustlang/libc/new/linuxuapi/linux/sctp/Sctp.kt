// port-lint: source new/linux_uapi/linux/sctp.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.sctp

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MSG_FIN

public typealias SctpAssocT = S32

public val SCTP_FUTURE_ASSOC: CInt = 0

public val SCTP_CURRENT_ASSOC: CInt = 1

public val SCTP_ALL_ASSOC: CInt = 2

public val SCTP_RTOINFO: CInt = 0

public val SCTP_ASSOCINFO: CInt = 1

public val SCTP_INITMSG: CInt = 2

public val SCTP_NODELAY: CInt = 3

public val SCTP_AUTOCLOSE: CInt = 4

public val SCTP_SET_PEER_PRIMARY_ADDR: CInt = 5

public val SCTP_PRIMARY_ADDR: CInt = 6

public val SCTP_ADAPTATION_LAYER: CInt = 7

public val SCTP_DISABLE_FRAGMENTS: CInt = 8

public val SCTP_PEER_ADDR_PARAMS: CInt = 9

public val SCTP_DEFAULT_SEND_PARAM: CInt = 10

public val SCTP_EVENTS: CInt = 11

public val SCTP_I_WANT_MAPPED_V4_ADDR: CInt = 12

public val SCTP_MAXSEG: CInt = 13

public val SCTP_STATUS: CInt = 14

public val SCTP_GET_PEER_ADDR_INFO: CInt = 15

public val SCTP_DELAYED_ACK_TIME: CInt = 16

public val SCTP_DELAYED_ACK: CInt = SCTP_DELAYED_ACK_TIME

public val SCTP_DELAYED_SACK: CInt = SCTP_DELAYED_ACK_TIME

public val SCTP_CONTEXT: CInt = 17

public val SCTP_FRAGMENT_INTERLEAVE: CInt = 18

public val SCTP_PARTIAL_DELIVERY_POINT: CInt = 19

public val SCTP_MAX_BURST: CInt = 20

public val SCTP_AUTH_CHUNK: CInt = 21

public val SCTP_HMAC_IDENT: CInt = 22

public val SCTP_AUTH_KEY: CInt = 23

public val SCTP_AUTH_ACTIVE_KEY: CInt = 24

public val SCTP_AUTH_DELETE_KEY: CInt = 25

public val SCTP_PEER_AUTH_CHUNKS: CInt = 26

public val SCTP_LOCAL_AUTH_CHUNKS: CInt = 27

public val SCTP_GET_ASSOC_NUMBER: CInt = 28

public val SCTP_GET_ASSOC_ID_LIST: CInt = 29

public val SCTP_AUTO_ASCONF: CInt = 30

public val SCTP_PEER_ADDR_THLDS: CInt = 31

public val SCTP_RECVRCVINFO: CInt = 32

public val SCTP_RECVNXTINFO: CInt = 33

public val SCTP_DEFAULT_SNDINFO: CInt = 34

public val SCTP_AUTH_DEACTIVATE_KEY: CInt = 35

public val SCTP_REUSE_PORT: CInt = 36

public val SCTP_PEER_ADDR_THLDS_V2: CInt = 37

public val SCTP_PR_SCTP_NONE: CInt = 0x0000

public val SCTP_PR_SCTP_TTL: CInt = 0x0010

public val SCTP_PR_SCTP_RTX: CInt = 0x0020

public val SCTP_PR_SCTP_PRIO: CInt = 0x0030

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SCTP_PR_SCTP_MAX: CInt = SCTP_PR_SCTP_PRIO

public val SCTP_PR_SCTP_MASK: CInt = 0x0030

public fun sctpPrIndex(policy: CInt): CInt = policy shr (4 - 1).toInt()

public fun sctpPrPolicy(policy: CInt): CInt = policy and SCTP_PR_SCTP_MASK

public fun sctpPrSetPolicy(flags: CInt, policy: CInt) {
    var flags1 = flags
    flags1 = flags1 and SCTP_PR_SCTP_MASK.inv()
    flags1 = flags1 or policy
}

public fun sctpPrTtlEnabled(policy: CInt): Boolean = policy == SCTP_PR_SCTP_TTL

public fun sctpPrRtxEnabled(policy: CInt): Boolean = policy == SCTP_PR_SCTP_RTX

public fun sctpPrPrioEnabled(policy: CInt): Boolean = policy == SCTP_PR_SCTP_PRIO

public val SCTP_ENABLE_RESET_STREAM_REQ: CInt = 0x01

public val SCTP_ENABLE_RESET_ASSOC_REQ: CInt = 0x02

public val SCTP_ENABLE_CHANGE_ASSOC_REQ: CInt = 0x04

public val SCTP_ENABLE_STRRESET_MASK: CInt = 0x07

public val SCTP_STREAM_RESET_INCOMING: CInt = 0x01

public val SCTP_STREAM_RESET_OUTGOING: CInt = 0x02

public val MSG_NOTIFICATION: CInt = 0x8000

public data class SctpInitmsg(
    public var sinitNumOstreams: U16 = 0u,
    public var sinitMaxInstreams: U16 = 0u,
    public var sinitMaxAttempts: U16 = 0u,
    public var sinitMaxInitTimeo: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpInitmsg> =
            object : TypeLayout<SctpInitmsg> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SctpInitmsg =
                    SctpInitmsg(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: SctpInitmsg) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sinitNumOstreams)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sinitMaxInstreams)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.sinitMaxAttempts)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.sinitMaxInitTimeo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpInitmsg =
                    SctpInitmsg(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpInitmsg) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sinitNumOstreams)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sinitMaxInstreams)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.sinitMaxAttempts)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.sinitMaxInitTimeo)
                }
            }
    }
}

public data class SctpSndrcvinfo(
    public var sinfoStream: U16 = 0u,
    public var sinfoSsn: U16 = 0u,
    public var sinfoFlags: U16 = 0u,
    public var sinfoPpid: U32 = 0u,
    public var sinfoContext: U32 = 0u,
    public var sinfoTimetolive: U32 = 0u,
    public var sinfoTsn: U32 = 0u,
    public var sinfoCumtsn: U32 = 0u,
    public var sinfoAssocId: io.github.kotlinmania.rustlang.libc.SctpAssocT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpSndrcvinfo> =
            object : TypeLayout<SctpSndrcvinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SctpSndrcvinfo =
                    SctpSndrcvinfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: SctpSndrcvinfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sinfoStream)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sinfoSsn)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.sinfoFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.sinfoPpid)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.sinfoContext)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.sinfoTimetolive)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.sinfoTsn)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.sinfoCumtsn)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.sinfoAssocId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpSndrcvinfo =
                    SctpSndrcvinfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpSndrcvinfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sinfoStream)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sinfoSsn)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.sinfoFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.sinfoPpid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.sinfoContext)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.sinfoTimetolive)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.sinfoTsn)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.sinfoCumtsn)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.sinfoAssocId)
                }
            }
    }
}

public data class SctpSndinfo(
    public var sndSid: U16 = 0u,
    public var sndFlags: U16 = 0u,
    public var sndPpid: U32 = 0u,
    public var sndContext: U32 = 0u,
    public var sndAssocId: io.github.kotlinmania.rustlang.libc.SctpAssocT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpSndinfo> =
            object : TypeLayout<SctpSndinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SctpSndinfo =
                    SctpSndinfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SctpSndinfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sndSid)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sndFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.sndPpid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.sndContext)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.sndAssocId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpSndinfo =
                    SctpSndinfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpSndinfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sndSid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sndFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.sndPpid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.sndContext)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.sndAssocId)
                }
            }
    }
}

public data class SctpRcvinfo(
    public var rcvSid: U16 = 0u,
    public var rcvSsn: U16 = 0u,
    public var rcvFlags: U16 = 0u,
    public var rcvPpid: U32 = 0u,
    public var rcvTsn: U32 = 0u,
    public var rcvCumtsn: U32 = 0u,
    public var rcvContext: U32 = 0u,
    public var rcvAssocId: io.github.kotlinmania.rustlang.libc.SctpAssocT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpRcvinfo> =
            object : TypeLayout<SctpRcvinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 28L
                override fun readNative(addr: Long): SctpRcvinfo =
                    SctpRcvinfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SctpRcvinfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.rcvSid)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.rcvSsn)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.rcvFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.rcvPpid)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.rcvTsn)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.rcvCumtsn)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.rcvContext)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.rcvAssocId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpRcvinfo =
                    SctpRcvinfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpRcvinfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.rcvSid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.rcvSsn)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.rcvFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.rcvPpid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.rcvTsn)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.rcvCumtsn)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.rcvContext)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.rcvAssocId)
                }
            }
    }
}

public data class SctpNxtinfo(
    public var nxtSid: U16 = 0u,
    public var nxtFlags: U16 = 0u,
    public var nxtPpid: U32 = 0u,
    public var nxtLength: U32 = 0u,
    public var nxtAssocId: io.github.kotlinmania.rustlang.libc.SctpAssocT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpNxtinfo> =
            object : TypeLayout<SctpNxtinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SctpNxtinfo =
                    SctpNxtinfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SctpNxtinfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.nxtSid)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.nxtFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nxtPpid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nxtLength)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.nxtAssocId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpNxtinfo =
                    SctpNxtinfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpNxtinfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.nxtSid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.nxtFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nxtPpid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nxtLength)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.nxtAssocId)
                }
            }
    }
}

public data class SctpPrinfo(
    public var prPolicy: U16 = 0u,
    public var prValue: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpPrinfo> =
            object : TypeLayout<SctpPrinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SctpPrinfo =
                    SctpPrinfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SctpPrinfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.prPolicy)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.prValue)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpPrinfo =
                    SctpPrinfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpPrinfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.prPolicy)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.prValue)
                }
            }
    }
}

public data class SctpAuthinfo(
    public var authKeynumber: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SctpAuthinfo> =
            object : TypeLayout<SctpAuthinfo> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): SctpAuthinfo =
                    SctpAuthinfo(
                        (PrimitiveLayouts.U16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SctpAuthinfo) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.authKeynumber)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SctpAuthinfo =
                    SctpAuthinfo(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SctpAuthinfo) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.authKeynumber)
                }
            }
    }
}

public val SCTP_UNORDERED: CInt = 1 shl 0

public val SCTP_ADDR_OVER: CInt = 1 shl 1

public val SCTP_ABORT: CInt = 1 shl 2

public val SCTP_SACK_IMMEDIATELY: CInt = 1 shl 3

public val SCTP_SENDALL: CInt = 1 shl 6

public val SCTP_PR_SCTP_ALL: CInt = 1 shl 7

public val SCTP_NOTIFICATION: CInt = MSG_NOTIFICATION

public val SCTP_EOF: CInt = MSG_FIN

public val SCTP_INIT: CInt = 0

public val SCTP_SNDRCV: CInt = 1

public val SCTP_SNDINFO: CInt = 2

public val SCTP_RCVINFO: CInt = 3

public val SCTP_NXTINFO: CInt = 4

public val SCTP_PRINFO: CInt = 5

public val SCTP_AUTHINFO: CInt = 6

public val SCTP_DSTADDRV4: CInt = 7

public val SCTP_DSTADDRV6: CInt = 8
