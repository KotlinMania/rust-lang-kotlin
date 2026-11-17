// port-lint: source new/linux_uapi/linux/can.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.bsd.SaFamilyT

// Header: `uapi / linux / can.h`

public val CAN_EFF_FLAG: CanidT = 0x80000000u

public val CAN_RTR_FLAG: CanidT = 0x40000000u

public val CAN_ERR_FLAG: CanidT = 0x20000000u

public val CAN_SFF_MASK: CanidT = 0x000007FFu

public val CAN_EFF_MASK: CanidT = 0x1FFFFFFFu

public val CAN_ERR_MASK: CanidT = 0x1FFFFFFFu

public val CANXL_PRIO_MASK: CanidT = CAN_SFF_MASK

public typealias CanidT = UInt

public val CAN_SFF_ID_BITS: CInt = 11

public val CAN_EFF_ID_BITS: CInt = 29

public val CANXL_PRIO_BITS: CInt = CAN_SFF_ID_BITS

public typealias CanErrMaskT = UInt

public val CAN_MAX_DLC: CInt = 8

public val CAN_MAX_DLEN: Int = 8

public val CANFD_MAX_DLC: CInt = 15

public val CANFD_MAX_DLEN: Int = 64

public val CANXL_MIN_DLC: CInt = 0

public val CANXL_MAX_DLC: CInt = 2047

public val CANXL_MAX_DLC_MASK: CInt = 0x07FF

public val CANXL_MIN_DLEN: Int = 1

public val CANXL_MAX_DLEN: Int = 2048

public open class CanFrame(
    public var canId: CanidT,
    // Note(1.0): this field was renamed to `len` in Linux 5.11
    public var canDlc: UByte,
    public var pad: UByte,
    public var res0: UByte,
    public var len8Dlc: UByte,
    public var data: UByteArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): CanFrame = CanFrame(canId, canDlc, pad, res0, len8Dlc, data, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<CanFrame> =
            object : TypeLayout<CanFrame> {
                override val alignBytes: Long = 8L
                private val off6: Long = alignUpBytes((8L + ((CAN_MAX_DLEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off6 + 0L), 8L)
                override fun readNative(addr: Long): CanFrame =
                    CanFrame(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                        (PrimitiveArrayLayouts.uByteArray((CAN_MAX_DLEN).toInt())).readNative(addr + 8L),
                        (PrimitiveLayouts.UNIT).readNative(addr + off6),
                    )
                override fun writeNative(addr: Long, value: CanFrame) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.canId)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.canDlc)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.pad)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.res0)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.len8Dlc)
                    (PrimitiveArrayLayouts.uByteArray((CAN_MAX_DLEN).toInt())).writeNative(addr + 8L, value.data)
                    (PrimitiveLayouts.UNIT).writeNative(addr + off6, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanFrame =
                    CanFrame(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                        (PrimitiveArrayLayouts.uByteArray((CAN_MAX_DLEN).toInt())).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + off6.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanFrame) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.canId)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.canDlc)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.pad)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.res0)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.len8Dlc)
                    (PrimitiveArrayLayouts.uByteArray((CAN_MAX_DLEN).toInt())).writeBytes(buf, offset + 8, value.data)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + off6.toInt(), value.nonExhaustive)
                }
            }

        public fun default(): CanFrame = CanFrame(canId = 0u, canDlc = 0u, pad = 0u, res0 = 0u, len8Dlc = 0u, data = Default.default(), nonExhaustive = Unit)
    }
}

public val CANFD_BRS: CInt = 0x01

public val CANFD_ESI: CInt = 0x02

public val CANFD_FDF: CInt = 0x04

public open class CanfdFrame(
    public var canId: CanidT,
    public var len: UByte,
    public var flags: UByte,
    public var res0: UByte,
    public var res1: UByte,
    public var data: UByteArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): CanfdFrame = CanfdFrame(canId, len, flags, res0, res1, data, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<CanfdFrame> =
            object : TypeLayout<CanfdFrame> {
                override val alignBytes: Long = 8L
                private val off6: Long = alignUpBytes((8L + ((CANFD_MAX_DLEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off6 + 0L), 8L)
                override fun readNative(addr: Long): CanfdFrame =
                    CanfdFrame(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                        (PrimitiveArrayLayouts.uByteArray((CANFD_MAX_DLEN).toInt())).readNative(addr + 8L),
                        (PrimitiveLayouts.UNIT).readNative(addr + off6),
                    )
                override fun writeNative(addr: Long, value: CanfdFrame) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.canId)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.len)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.flags)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.res0)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.res1)
                    (PrimitiveArrayLayouts.uByteArray((CANFD_MAX_DLEN).toInt())).writeNative(addr + 8L, value.data)
                    (PrimitiveLayouts.UNIT).writeNative(addr + off6, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanfdFrame =
                    CanfdFrame(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                        (PrimitiveArrayLayouts.uByteArray((CANFD_MAX_DLEN).toInt())).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + off6.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanfdFrame) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.canId)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.len)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.flags)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.res0)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.res1)
                    (PrimitiveArrayLayouts.uByteArray((CANFD_MAX_DLEN).toInt())).writeBytes(buf, offset + 8, value.data)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + off6.toInt(), value.nonExhaustive)
                }
            }

        public fun default(): CanfdFrame = CanfdFrame(canId = 0u, len = 0u, flags = 0u, res0 = 0u, res1 = 0u, data = UByteArray(CANFD_MAX_DLEN) { 0u.toUByte() }, nonExhaustive = Unit)
    }
}

public val CANXL_XLF: CInt = 0x80

public val CANXL_SEC: CInt = 0x01

public open class CanxlFrame(
    public var prio: CanidT,
    public var flags: UByte,
    public var sdt: UByte,
    public var len: UShort,
    public var af: UInt,
    public var data: UByteArray,
) {
    public open fun clone(): CanxlFrame = CanxlFrame(prio, flags, sdt, len, af, data)

    public companion object {
        public fun typeLayout(): TypeLayout<CanxlFrame> =
            object : TypeLayout<CanxlFrame> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes((12L + ((CANXL_MAX_DLEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                override fun readNative(addr: Long): CanxlFrame =
                    CanxlFrame(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray((CANXL_MAX_DLEN).toInt())).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: CanxlFrame) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.prio)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.flags)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.sdt)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.len)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.af)
                    (PrimitiveArrayLayouts.uByteArray((CANXL_MAX_DLEN).toInt())).writeNative(addr + 12L, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanxlFrame =
                    CanxlFrame(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray((CANXL_MAX_DLEN).toInt())).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanxlFrame) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.prio)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.flags)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.sdt)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.len)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.af)
                    (PrimitiveArrayLayouts.uByteArray((CANXL_MAX_DLEN).toInt())).writeBytes(buf, offset + 12, value.data)
                }
            }

        public fun default(): CanxlFrame = CanxlFrame(prio = 0u, flags = 0u, sdt = 0u, len = 0u, af = 0u, data = UByteArray(CANXL_MAX_DLEN) { 0u.toUByte() })
    }
}

// Note(offsetOf): use `core.mem.offsetOf` once that is available
// https://github.com/rust-lang/rfcs/pull/3308
// pub const CANXL_HDR_SIZE: Int = core.mem.offsetOf(canxlFrame, data);
public val CANXL_HDR_SIZE: Int = 12

public val CANXL_MIN_MTU: Int = CANXL_HDR_SIZE + 64

public val CANXL_MTU: Int = sizeOf<CanxlFrame>(typeLayoutT = CanxlFrame.typeLayout())

public val CANXL_MAX_MTU: Int = CANXL_MTU

public val CAN_RAW: CInt = 1

public val CAN_BCM: CInt = 2

public val CAN_TP16: CInt = 3

public val CAN_TP20: CInt = 4

public val CAN_MCNET: CInt = 5

public val CAN_ISOTP: CInt = 6

public val CAN_J1939: CInt = 7

public val CAN_NPROTO: CInt = 8

public val SOL_CAN_BASE: CInt = 100

public val CAN_INV_FILTER: CanidT = 0x20000000u

public val CAN_MTU: Int = sizeOf<CanFrame>(typeLayoutT = CanFrame.typeLayout())

public open class SockaddrCan(
    public var canFamily: SaFamilyT,
    public var canIfindex: CInt,
    public var canAddr: CAnonymousSockaddrCanCanAddr,
) {
    public open fun clone(): SockaddrCan = SockaddrCan(canFamily, canIfindex, canAddr)

    public companion object {
        public fun default(): SockaddrCan = SockaddrCan(canFamily = 0u, canIfindex = 0, canAddr = CAnonymousSockaddrCanCanAddr())
    }
}

public open class CAnonymousSockaddrCanCanAddr(
    public var tp: CAnonymousSockaddrCanTp? = null,
    public var j1939: CAnonymousSockaddrCanJ1939? = null,
)

public open class CAnonymousSockaddrCanTp(
    public var rxId: CanidT,
    public var txId: CanidT,
) {
    public open fun clone(): CAnonymousSockaddrCanTp = CAnonymousSockaddrCanTp(rxId, txId)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSockaddrCanTp> =
            object : TypeLayout<CAnonymousSockaddrCanTp> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousSockaddrCanTp =
                    CAnonymousSockaddrCanTp(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSockaddrCanTp) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.rxId)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.txId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSockaddrCanTp =
                    CAnonymousSockaddrCanTp(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSockaddrCanTp) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.rxId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.txId)
                }
            }

        public fun default(): CAnonymousSockaddrCanTp = CAnonymousSockaddrCanTp(rxId = 0u, txId = 0u)
    }
}

public open class CAnonymousSockaddrCanJ1939(
    public var name: ULong,
    public var pgn: UInt,
    public var addr: UByte,
) {
    public open fun clone(): CAnonymousSockaddrCanJ1939 = CAnonymousSockaddrCanJ1939(name, pgn, addr)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSockaddrCanJ1939> =
            object : TypeLayout<CAnonymousSockaddrCanJ1939> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousSockaddrCanJ1939 =
                    CAnonymousSockaddrCanJ1939(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U8).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSockaddrCanJ1939) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.name)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.pgn)
                    (PrimitiveLayouts.U8).writeNative(addr + 12L, value.addr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSockaddrCanJ1939 =
                    CAnonymousSockaddrCanJ1939(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSockaddrCanJ1939) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.name)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.pgn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 12, value.addr)
                }
            }

        public fun default(): CAnonymousSockaddrCanJ1939 = CAnonymousSockaddrCanJ1939(name = 0uL, pgn = 0u, addr = 0u)
    }
}

public open class CanFilter(
    public var canId: CanidT,
    public var canMask: CanidT,
) {
    public open fun clone(): CanFilter = CanFilter(canId, canMask)

    public companion object {
        public fun typeLayout(): TypeLayout<CanFilter> =
            object : TypeLayout<CanFilter> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CanFilter =
                    CanFilter(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CanFilter) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.canId)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.canMask)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanFilter =
                    CanFilter(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanFilter) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.canId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.canMask)
                }
            }

        public fun default(): CanFilter = CanFilter(canId = 0u, canMask = 0u)
    }
}

public val CANFD_MTU: Int = sizeOf<CanfdFrame>(typeLayoutT = CanfdFrame.typeLayout())
