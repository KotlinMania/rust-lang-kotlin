package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64

import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*

// unlikely to change
public open class Timeval32(
    public var tvSec: Int,
    public var tvUsec: Int,
) {
    public open fun clone(): Timeval32 = Timeval32(tvSec, tvUsec)

    public companion object {
        public fun typeLayout(): TypeLayout<Timeval32> =
            object : TypeLayout<Timeval32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Timeval32 =
                    Timeval32(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Timeval32) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.tvUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timeval32 =
                    Timeval32(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timeval32) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.tvUsec)
                }
            }

        public fun default(): Timeval32 = Timeval32(tvSec = Default.default(), tvUsec = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class IfData(
    public var ifiType: CUchar,
    public var ifiTypelen: CUchar,
    public var ifiPhysical: CUchar,
    public var ifiAddrlen: CUchar,
    public var ifiHdrlen: CUchar,
    public var ifiRecvquota: CUchar,
    public var ifiXmitquota: CUchar,
    public var ifiUnused1: CUchar,
    public var ifiMtu: UInt,
    public var ifiMetric: UInt,
    public var ifiBaudrate: UInt,
    public var ifiIpackets: UInt,
    public var ifiIerrors: UInt,
    public var ifiOpackets: UInt,
    public var ifiOerrors: UInt,
    public var ifiCollisions: UInt,
    public var ifiIbytes: UInt,
    public var ifiObytes: UInt,
    public var ifiImcasts: UInt,
    public var ifiOmcasts: UInt,
    public var ifiIqdrops: UInt,
    public var ifiNoproto: UInt,
    public var ifiRecvtiming: UInt,
    public var ifiXmittiming: UInt,
    public var ifiLastchange: Timeval32,
    public var ifiUnused2: UInt,
    public var ifiHwassist: UInt,
    public var ifiReserved1: UInt,
    public var ifiReserved2: UInt,
) {
    public open fun clone(): IfData = IfData(ifiType, ifiTypelen, ifiPhysical, ifiAddrlen, ifiHdrlen, ifiRecvquota, ifiXmitquota, ifiUnused1, ifiMtu, ifiMetric, ifiBaudrate, ifiIpackets, ifiIerrors, ifiOpackets, ifiOerrors, ifiCollisions, ifiIbytes, ifiObytes, ifiImcasts, ifiOmcasts, ifiIqdrops, ifiNoproto, ifiRecvtiming, ifiXmittiming, ifiLastchange, ifiUnused2, ifiHwassist, ifiReserved1, ifiReserved2)

    public companion object {
        public fun typeLayout(): TypeLayout<IfData> =
            object : TypeLayout<IfData> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): IfData =
                    IfData(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveLayouts.U32).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 52L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 60L),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                        (PrimitiveLayouts.U32).readNative(addr + 68L),
                        (Timeval32.typeLayout()).readNative(addr + 72L),
                        (PrimitiveLayouts.U32).readNative(addr + 80L),
                        (PrimitiveLayouts.U32).readNative(addr + 84L),
                        (PrimitiveLayouts.U32).readNative(addr + 88L),
                        (PrimitiveLayouts.U32).readNative(addr + 92L),
                    )
                override fun writeNative(addr: Long, value: IfData) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.ifiType)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.ifiTypelen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.ifiPhysical)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.ifiAddrlen)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.ifiHdrlen)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.ifiRecvquota)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.ifiXmitquota)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.ifiUnused1)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.ifiMtu)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.ifiMetric)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ifiBaudrate)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.ifiIpackets)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.ifiIerrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.ifiOpackets)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.ifiOerrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.ifiCollisions)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.ifiIbytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.ifiObytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.ifiImcasts)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.ifiOmcasts)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.ifiIqdrops)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.ifiNoproto)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.ifiRecvtiming)
                    (PrimitiveLayouts.U32).writeNative(addr + 68L, value.ifiXmittiming)
                    (Timeval32.typeLayout()).writeNative(addr + 72L, value.ifiLastchange)
                    (PrimitiveLayouts.U32).writeNative(addr + 80L, value.ifiUnused2)
                    (PrimitiveLayouts.U32).writeNative(addr + 84L, value.ifiHwassist)
                    (PrimitiveLayouts.U32).writeNative(addr + 88L, value.ifiReserved1)
                    (PrimitiveLayouts.U32).writeNative(addr + 92L, value.ifiReserved2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IfData =
                    IfData(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 68),
                        (Timeval32.typeLayout()).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 92),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IfData) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.ifiType)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.ifiTypelen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.ifiPhysical)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.ifiAddrlen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.ifiHdrlen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.ifiRecvquota)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.ifiXmitquota)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.ifiUnused1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.ifiMtu)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.ifiMetric)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ifiBaudrate)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.ifiIpackets)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.ifiIerrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.ifiOpackets)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.ifiOerrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.ifiCollisions)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.ifiIbytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.ifiObytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.ifiImcasts)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.ifiOmcasts)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.ifiIqdrops)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.ifiNoproto)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.ifiRecvtiming)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.ifiXmittiming)
                    (Timeval32.typeLayout()).writeBytes(buf, offset + 72, value.ifiLastchange)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 80, value.ifiUnused2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 84, value.ifiHwassist)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 88, value.ifiReserved1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 92, value.ifiReserved2)
                }
            }

        public fun default(): IfData = IfData(ifiType = Default.default(), ifiTypelen = Default.default(), ifiPhysical = Default.default(), ifiAddrlen = Default.default(), ifiHdrlen = Default.default(), ifiRecvquota = Default.default(), ifiXmitquota = Default.default(), ifiUnused1 = Default.default(), ifiMtu = Default.default(), ifiMetric = Default.default(), ifiBaudrate = Default.default(), ifiIpackets = Default.default(), ifiIerrors = Default.default(), ifiOpackets = Default.default(), ifiOerrors = Default.default(), ifiCollisions = Default.default(), ifiIbytes = Default.default(), ifiObytes = Default.default(), ifiImcasts = Default.default(), ifiOmcasts = Default.default(), ifiIqdrops = Default.default(), ifiNoproto = Default.default(), ifiRecvtiming = Default.default(), ifiXmittiming = Default.default(), ifiLastchange = Default.default(), ifiUnused2 = Default.default(), ifiHwassist = Default.default(), ifiReserved1 = Default.default(), ifiReserved2 = Default.default())
    }
}

public open class BpfHdr(
    public var bhTstamp: Timeval32,
    public var bhCaplen: UInt,
    public var bhDatalen: UInt,
    public var bhHdrlen: CUshort,
) {
    public open fun clone(): BpfHdr = BpfHdr(bhTstamp, bhCaplen, bhDatalen, bhHdrlen)

    public companion object {
        public fun typeLayout(): TypeLayout<BpfHdr> =
            object : TypeLayout<BpfHdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): BpfHdr =
                    BpfHdr(
                        (Timeval32.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: BpfHdr) {
                    (Timeval32.typeLayout()).writeNative(addr, value.bhTstamp)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.bhCaplen)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.bhDatalen)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.bhHdrlen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BpfHdr =
                    BpfHdr(
                        (Timeval32.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BpfHdr) {
                    (Timeval32.typeLayout()).writeBytes(buf, offset, value.bhTstamp)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.bhCaplen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.bhDatalen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.bhHdrlen)
                }
            }

        public fun default(): BpfHdr = BpfHdr(bhTstamp = Default.default(), bhCaplen = Default.default(), bhDatalen = Default.default(), bhHdrlen = Default.default())
    }
}
