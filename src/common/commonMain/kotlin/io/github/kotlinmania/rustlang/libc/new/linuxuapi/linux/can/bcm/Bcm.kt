// port-lint: source new/linux_uapi/linux/can/bcm.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.bcm

import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.CanFrame
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.CanidT

// Header: `linux / can / bcm.h`

public open class BcmTimeval(
    public var tvSec: CLong,
    public var tvUsec: CLong,
) {
    public open fun clone(): BcmTimeval = BcmTimeval(tvSec, tvUsec)

    public companion object {
        public fun typeLayout(): TypeLayout<BcmTimeval> =
            object : TypeLayout<BcmTimeval> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): BcmTimeval =
                    BcmTimeval(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: BcmTimeval) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tvUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BcmTimeval =
                    BcmTimeval(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BcmTimeval) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tvUsec)
                }
            }
    }
}

public open class BcmMsgHead(
    public var opcode: UInt,
    public var flags: UInt,
    public var count: UInt,
    public var ival1: BcmTimeval,
    public var ival2: BcmTimeval,
    public var canId: CanidT,
    public var nframes: UInt,
    public var frames: List<CanFrame>,
) {
    public open fun clone(): BcmMsgHead = BcmMsgHead(opcode, flags, count, ival1, ival2, canId, nframes, frames)

    public companion object
}

public val TX_SETUP: UInt = 1u

public val TX_DELETE: UInt = TX_SETUP + 1u

public val TX_READ: UInt = TX_DELETE + 1u

public val TX_SEND: UInt = TX_READ + 1u

public val RX_SETUP: UInt = TX_SEND + 1u

public val RX_DELETE: UInt = RX_SETUP + 1u

public val RX_READ: UInt = RX_DELETE + 1u

public val TX_STATUS: UInt = RX_READ + 1u

public val TX_EXPIRED: UInt = TX_STATUS + 1u

public val RX_STATUS: UInt = TX_EXPIRED + 1u

public val RX_TIMEOUT: UInt = RX_STATUS + 1u

public val RX_CHANGED: UInt = RX_TIMEOUT + 1u

public val SETTIMER: UInt = 0x0001u

public val STARTTIMER: UInt = 0x0002u

public val TX_COUNTEVT: UInt = 0x0004u

public val TX_ANNOUNCE: UInt = 0x0008u

public val TX_CP_CAN_ID: UInt = 0x0010u

public val RX_FILTER_ID: UInt = 0x0020u

public val RX_CHECK_DLC: UInt = 0x0040u

public val RX_NO_AUTOTIMER: UInt = 0x0080u

public val RX_ANNOUNCE_RESUME: UInt = 0x0100u

public val TX_RESET_MULTI_IDX: UInt = 0x0200u

public val RX_RTR_FRAME: UInt = 0x0400u

public val CAN_FD_FRAME: UInt = 0x0800u
