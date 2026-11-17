// port-lint: source new/linux_uapi/linux/can/netlink.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.netlink

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*

// Header: `linux / can / netlink.h`

public open class CanBittiming(
    public var bitrate: UInt,
    public var samplePoint: UInt,
    public var tq: UInt,
    public var propSeg: UInt,
    public var phaseSeg1: UInt,
    public var phaseSeg2: UInt,
    public var sjw: UInt,
    public var brp: UInt,
) {
    public open fun clone(): CanBittiming = CanBittiming(bitrate, samplePoint, tq, propSeg, phaseSeg1, phaseSeg2, sjw, brp)

    public companion object {
        public fun typeLayout(): TypeLayout<CanBittiming> =
            object : TypeLayout<CanBittiming> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): CanBittiming =
                    CanBittiming(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: CanBittiming) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.bitrate)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.samplePoint)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tq)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.propSeg)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.phaseSeg1)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.phaseSeg2)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.sjw)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.brp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanBittiming =
                    CanBittiming(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanBittiming) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.bitrate)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.samplePoint)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tq)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.propSeg)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.phaseSeg1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.phaseSeg2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.sjw)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.brp)
                }
            }
    }
}

public open class CanBittimingConst(
    public var name: UByteArray,
    public var tseg1Min: UInt,
    public var tseg1Max: UInt,
    public var tseg2Min: UInt,
    public var tseg2Max: UInt,
    public var sjwMax: UInt,
    public var brpMin: UInt,
    public var brpMax: UInt,
    public var brpInc: UInt,
) {
    public open fun clone(): CanBittimingConst = CanBittimingConst(name, tseg1Min, tseg1Max, tseg2Min, tseg2Max, sjwMax, brpMin, brpMax, brpInc)

    public companion object {
        public fun typeLayout(): TypeLayout<CanBittimingConst> =
            object : TypeLayout<CanBittimingConst> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): CanBittimingConst =
                    CanBittimingConst(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                    )
                override fun writeNative(addr: Long, value: CanBittimingConst) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.name)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.tseg1Min)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tseg1Max)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.tseg2Min)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.tseg2Max)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.sjwMax)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.brpMin)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.brpMax)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.brpInc)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanBittimingConst =
                    CanBittimingConst(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanBittimingConst) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.name)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.tseg1Min)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tseg1Max)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.tseg2Min)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.tseg2Max)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.sjwMax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.brpMin)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.brpMax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.brpInc)
                }
            }
    }
}

public open class CanClock(
    public var freq: UInt,
) {
    public open fun clone(): CanClock = CanClock(freq)

    public companion object {
        public fun typeLayout(): TypeLayout<CanClock> =
            object : TypeLayout<CanClock> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): CanClock =
                    CanClock(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: CanClock) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.freq)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanClock =
                    CanClock(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanClock) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.freq)
                }
            }
    }
}

public open class CanBerrCounter(
    public var txerr: UShort,
    public var rxerr: UShort,
) {
    public open fun clone(): CanBerrCounter = CanBerrCounter(txerr, rxerr)

    public companion object {
        public fun typeLayout(): TypeLayout<CanBerrCounter> =
            object : TypeLayout<CanBerrCounter> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): CanBerrCounter =
                    CanBerrCounter(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: CanBerrCounter) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.txerr)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.rxerr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanBerrCounter =
                    CanBerrCounter(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanBerrCounter) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.txerr)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.rxerr)
                }
            }
    }
}

public open class CanCtrlmode(
    public var mask: UInt,
    public var flags: UInt,
) {
    public open fun clone(): CanCtrlmode = CanCtrlmode(mask, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<CanCtrlmode> =
            object : TypeLayout<CanCtrlmode> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CanCtrlmode =
                    CanCtrlmode(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CanCtrlmode) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.mask)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanCtrlmode =
                    CanCtrlmode(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanCtrlmode) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.mask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.flags)
                }
            }
    }
}

public open class CanDeviceStats(
    public var busError: UInt,
    public var errorWarning: UInt,
    public var errorPassive: UInt,
    public var busOff: UInt,
    public var arbitrationLost: UInt,
    public var restarts: UInt,
) {
    public open fun clone(): CanDeviceStats = CanDeviceStats(busError, errorWarning, errorPassive, busOff, arbitrationLost, restarts)

    public companion object {
        public fun typeLayout(): TypeLayout<CanDeviceStats> =
            object : TypeLayout<CanDeviceStats> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): CanDeviceStats =
                    CanDeviceStats(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: CanDeviceStats) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.busError)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.errorWarning)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.errorPassive)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.busOff)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.arbitrationLost)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.restarts)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CanDeviceStats =
                    CanDeviceStats(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CanDeviceStats) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.busError)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.errorWarning)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.errorPassive)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.busOff)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.arbitrationLost)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.restarts)
                }
            }
    }
}

public typealias CanState = CUint

public val CAN_STATE_ERROR_ACTIVE: CanState = 0u

public val CAN_STATE_ERROR_WARNING: CanState = CAN_STATE_ERROR_ACTIVE + 1u

public val CAN_STATE_ERROR_PASSIVE: CanState = CAN_STATE_ERROR_WARNING + 1u

public val CAN_STATE_BUS_OFF: CanState = CAN_STATE_ERROR_PASSIVE + 1u

public val CAN_STATE_STOPPED: CanState = CAN_STATE_BUS_OFF + 1u

public val CAN_STATE_SLEEPING: CanState = CAN_STATE_STOPPED + 1u

public val CAN_CTRLMODE_LOOPBACK: UInt = 0x01u

public val CAN_CTRLMODE_LISTENONLY: UInt = 0x02u

public val CAN_CTRLMODE_3_SAMPLES: UInt = 0x04u

public val CAN_CTRLMODE_ONE_SHOT: UInt = 0x08u

public val CAN_CTRLMODE_BERR_REPORTING: UInt = 0x10u

public val CAN_CTRLMODE_FD: UInt = 0x20u

public val CAN_CTRLMODE_PRESUME_ACK: UInt = 0x40u

public val CAN_CTRLMODE_FD_NON_ISO: UInt = 0x80u

public val CAN_CTRLMODE_CC_LEN8_DLC: UInt = 0x100u

public val CAN_CTRLMODE_TDC_AUTO: UInt = 0x200u

public val CAN_CTRLMODE_TDC_MANUAL: UInt = 0x400u

public val IFLA_CAN_UNSPEC: CInt = 0

public val IFLA_CAN_BITTIMING: CInt = IFLA_CAN_UNSPEC + 1

public val IFLA_CAN_BITTIMING_CONST: CInt = IFLA_CAN_BITTIMING + 1

public val IFLA_CAN_CLOCK: CInt = IFLA_CAN_BITTIMING_CONST + 1

public val IFLA_CAN_STATE: CInt = IFLA_CAN_CLOCK + 1

public val IFLA_CAN_CTRLMODE: CInt = IFLA_CAN_STATE + 1

public val IFLA_CAN_RESTART_MS: CInt = IFLA_CAN_CTRLMODE + 1

public val IFLA_CAN_RESTART: CInt = IFLA_CAN_RESTART_MS + 1

public val IFLA_CAN_BERR_COUNTER: CInt = IFLA_CAN_RESTART + 1

public val IFLA_CAN_DATA_BITTIMING: CInt = IFLA_CAN_BERR_COUNTER + 1

public val IFLA_CAN_DATA_BITTIMING_CONST: CInt = IFLA_CAN_DATA_BITTIMING + 1

public val IFLA_CAN_TERMINATION: CInt = IFLA_CAN_DATA_BITTIMING_CONST + 1

public val IFLA_CAN_TERMINATION_CONST: CInt = IFLA_CAN_TERMINATION + 1

public val IFLA_CAN_BITRATE_CONST: CInt = IFLA_CAN_TERMINATION_CONST + 1

public val IFLA_CAN_DATA_BITRATE_CONST: CInt = IFLA_CAN_BITRATE_CONST + 1

public val IFLA_CAN_BITRATE_MAX: CInt = IFLA_CAN_DATA_BITRATE_CONST + 1

public val IFLA_CAN_TDC: CInt = IFLA_CAN_BITRATE_MAX + 1

public val IFLA_CAN_CTRLMODE_EXT: CInt = IFLA_CAN_TDC + 1

public val IFLA_CAN_TDC_UNSPEC: CInt = 0

public val IFLA_CAN_TDC_TDCV_MIN: CInt = IFLA_CAN_TDC_UNSPEC + 1

public val IFLA_CAN_TDC_TDCV_MAX: CInt = IFLA_CAN_TDC_TDCV_MIN + 1

public val IFLA_CAN_TDC_TDCO_MIN: CInt = IFLA_CAN_TDC_TDCV_MAX + 1

public val IFLA_CAN_TDC_TDCO_MAX: CInt = IFLA_CAN_TDC_TDCO_MIN + 1

public val IFLA_CAN_TDC_TDCF_MIN: CInt = IFLA_CAN_TDC_TDCO_MAX + 1

public val IFLA_CAN_TDC_TDCF_MAX: CInt = IFLA_CAN_TDC_TDCF_MIN + 1

public val IFLA_CAN_TDC_TDCV: CInt = IFLA_CAN_TDC_TDCF_MAX + 1

public val IFLA_CAN_TDC_TDCO: CInt = IFLA_CAN_TDC_TDCV + 1

public val IFLA_CAN_TDC_TDCF: CInt = IFLA_CAN_TDC_TDCO + 1

public val IFLA_CAN_CTRLMODE_UNSPEC: CInt = 0

public val IFLA_CAN_CTRLMODE_SUPPORTED: CInt = IFLA_CAN_CTRLMODE_UNSPEC + 1

public val CAN_TERMINATION_DISABLED: UShort = 0u
