@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.CcT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SighandlerT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.Timeval
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf32Word
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf64Word
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf64Xword
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.FsidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Sigevent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Sockaddr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SpeedT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.TcflagT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.SigsetT

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public open class Aiocb internal constructor(
    public var aioFildes: CInt,
    public var aioLioOpcode: CInt,
    public var aioReqprio: CInt,
    aioBufCtorArg: MutPtr,
    public var aioNbytes: SizeT,
    public var aioSigevent: Sigevent,
    tdCtorArg: MutPtr,
    public var lock: IntArray,
    public var err: CInt,
    public var ret: SsizeT,
    public var aioOffset: OffT,
    nextCtorArg: MutPtr,
    prevCtorArg: MutPtr,
    public var dummy4: UByteArray,
) {
    public var aioBuf: MutPtr = aioBufCtorArg
        internal set

    public var td: MutPtr = tdCtorArg
        internal set

    public var next: MutPtr = nextCtorArg
        internal set

    public var prev: MutPtr = prevCtorArg
        internal set

    public open fun clone(): Aiocb = Aiocb(aioFildes, aioLioOpcode, aioReqprio, aioBuf, aioNbytes, aioSigevent, td, lock, err, ret, aioOffset, next, prev, dummy4)

    public companion object {
        public operator fun invoke(
            aioFildes: CInt,
            aioLioOpcode: CInt,
            aioReqprio: CInt,
            aioBuf: MutPtr,
            aioNbytes: SizeT,
            aioSigevent: Sigevent,
            td: MutPtr,
            lock: IntArray,
            err: CInt,
            ret: SsizeT,
            aioOffset: OffT,
            next: MutPtr,
            prev: MutPtr,
            dummy4: UByteArray,
        ): Aiocb = Aiocb(
            aioFildes = aioFildes,
            aioLioOpcode = aioLioOpcode,
            aioReqprio = aioReqprio,
            aioBufCtorArg = aioBuf,
            aioNbytes = aioNbytes,
            aioSigevent = aioSigevent,
            tdCtorArg = td,
            lock = lock,
            err = err,
            ret = ret,
            aioOffset = aioOffset,
            nextCtorArg = next,
            prevCtorArg = prev,
            dummy4 = dummy4,
        )
    }
}

public open class FanotifyEventMetadata(
    public var eventLen: CUint,
    public var vers: CUchar,
    public var reserved: CUchar,
    public var metadataLen: CUshort,
    public var mask: CUlonglong,
    public var fd: CInt,
    public var pid: CInt,
) {
    public open fun clone(): FanotifyEventMetadata = FanotifyEventMetadata(eventLen, vers, reserved, metadataLen, mask, fd, pid)

    public companion object {
        public fun typeLayout(): TypeLayout<FanotifyEventMetadata> =
            object : TypeLayout<FanotifyEventMetadata> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FanotifyEventMetadata =
                    FanotifyEventMetadata(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: FanotifyEventMetadata) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.eventLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.vers)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.reserved)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.metadataLen)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.mask)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.fd)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FanotifyEventMetadata =
                    FanotifyEventMetadata(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FanotifyEventMetadata) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.eventLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.vers)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.reserved)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.metadataLen)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.mask)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.fd)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pid)
                }
            }
    }
}

public open class Sigaction(
    public var saSigaction: SighandlerT,
    public var saMask: SigsetT,
    public var saFlags: CInt,
    public var saRestorer: SigactionSaRestorerCallback?,
) {
    public open fun clone(): Sigaction = Sigaction(saSigaction, saMask, saFlags, saRestorer)

    public companion object
}

// musl's uint64T is an unsigned long instead of an unsigned long long on
// 64-bit targets, so it's not a __u64 (which is cUlonglong), but rather a
// real u64. This is also how we handle musl's uint64T elsewhere.
public open class Statx(
    public var stxMask: UInt,
    public var stxBlksize: UInt,
    public var stxAttributes: ULong,
    public var stxNlink: UInt,
    public var stxUid: UInt,
    public var stxGid: UInt,
    public var stxMode: UShort,
    public var statxPad1: UShortArray,
    public var stxIno: ULong,
    public var stxSize: ULong,
    public var stxBlocks: ULong,
    public var stxAttributesMask: ULong,
    public var stxAtime: StatxTimestamp,
    public var stxBtime: StatxTimestamp,
    public var stxCtime: StatxTimestamp,
    public var stxMtime: StatxTimestamp,
    public var stxRdevMajor: UInt,
    public var stxRdevMinor: UInt,
    public var stxDevMajor: UInt,
    public var stxDevMinor: UInt,
    public var stxMntId: ULong,
    public var stxDioMemAlign: UInt,
    public var stxDioOffsetAlign: UInt,
    public var stxSubvol: ULong,
    public var stxAtomicWriteUnitMin: UInt,
    public var stxAtomicWriteUnitMax: UInt,
    public var stxAtomicWriteSegmentsMax: UInt,
    public var statxPad2: UIntArray,
    public var statxPad3: ULongArray,
) {
    public open fun clone(): Statx = Statx(stxMask, stxBlksize, stxAttributes, stxNlink, stxUid, stxGid, stxMode, statxPad1, stxIno, stxSize, stxBlocks, stxAttributesMask, stxAtime, stxBtime, stxCtime, stxMtime, stxRdevMajor, stxRdevMinor, stxDevMajor, stxDevMinor, stxMntId, stxDioMemAlign, stxDioOffsetAlign, stxSubvol, stxAtomicWriteUnitMin, stxAtomicWriteUnitMax, stxAtomicWriteSegmentsMax, statxPad2, statxPad3)

    public companion object {
        public fun typeLayout(): TypeLayout<Statx> =
            object : TypeLayout<Statx> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 256L
                override fun readNative(addr: Long): Statx =
                    Statx(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 28L),
                        (PrimitiveArrayLayouts.uShortArray(1)).readNative(addr + 30L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 64L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 80L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 96L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 128L),
                        (PrimitiveLayouts.U32).readNative(addr + 132L),
                        (PrimitiveLayouts.U32).readNative(addr + 136L),
                        (PrimitiveLayouts.U32).readNative(addr + 140L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U32).readNative(addr + 152L),
                        (PrimitiveLayouts.U32).readNative(addr + 156L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U32).readNative(addr + 168L),
                        (PrimitiveLayouts.U32).readNative(addr + 172L),
                        (PrimitiveLayouts.U32).readNative(addr + 176L),
                        (PrimitiveArrayLayouts.uIntArray(1)).readNative(addr + 180L),
                        (PrimitiveArrayLayouts.uLongArray(9)).readNative(addr + 184L),
                    )
                override fun writeNative(addr: Long, value: Statx) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.stxMask)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.stxBlksize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stxAttributes)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stxNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stxUid)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stxGid)
                    (PrimitiveLayouts.U16).writeNative(addr + 28L, value.stxMode)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeNative(addr + 30L, value.statxPad1)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.stxIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stxSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.stxBlocks)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.stxAttributesMask)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 64L, value.stxAtime)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 80L, value.stxBtime)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 96L, value.stxCtime)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 112L, value.stxMtime)
                    (PrimitiveLayouts.U32).writeNative(addr + 128L, value.stxRdevMajor)
                    (PrimitiveLayouts.U32).writeNative(addr + 132L, value.stxRdevMinor)
                    (PrimitiveLayouts.U32).writeNative(addr + 136L, value.stxDevMajor)
                    (PrimitiveLayouts.U32).writeNative(addr + 140L, value.stxDevMinor)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.stxMntId)
                    (PrimitiveLayouts.U32).writeNative(addr + 152L, value.stxDioMemAlign)
                    (PrimitiveLayouts.U32).writeNative(addr + 156L, value.stxDioOffsetAlign)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.stxSubvol)
                    (PrimitiveLayouts.U32).writeNative(addr + 168L, value.stxAtomicWriteUnitMin)
                    (PrimitiveLayouts.U32).writeNative(addr + 172L, value.stxAtomicWriteUnitMax)
                    (PrimitiveLayouts.U32).writeNative(addr + 176L, value.stxAtomicWriteSegmentsMax)
                    (PrimitiveArrayLayouts.uIntArray(1)).writeNative(addr + 180L, value.statxPad2)
                    (PrimitiveArrayLayouts.uLongArray(9)).writeNative(addr + 184L, value.statxPad3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statx =
                    Statx(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 28),
                        (PrimitiveArrayLayouts.uShortArray(1)).readBytes(buf, offset + 30),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 64),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 80),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 96),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 132),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 140),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 156),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 172),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 176),
                        (PrimitiveArrayLayouts.uIntArray(1)).readBytes(buf, offset + 180),
                        (PrimitiveArrayLayouts.uLongArray(9)).readBytes(buf, offset + 184),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statx) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.stxMask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.stxBlksize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stxAttributes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stxNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stxUid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stxGid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 28, value.stxMode)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeBytes(buf, offset + 30, value.statxPad1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.stxIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stxSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.stxBlocks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.stxAttributesMask)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 64, value.stxAtime)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 80, value.stxBtime)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 96, value.stxCtime)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 112, value.stxMtime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 128, value.stxRdevMajor)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 132, value.stxRdevMinor)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 136, value.stxDevMajor)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 140, value.stxDevMinor)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.stxMntId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 152, value.stxDioMemAlign)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 156, value.stxDioOffsetAlign)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.stxSubvol)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 168, value.stxAtomicWriteUnitMin)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 172, value.stxAtomicWriteUnitMax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 176, value.stxAtomicWriteSegmentsMax)
                    (PrimitiveArrayLayouts.uIntArray(1)).writeBytes(buf, offset + 180, value.statxPad2)
                    (PrimitiveArrayLayouts.uLongArray(9)).writeBytes(buf, offset + 184, value.statxPad3)
                }
            }
    }
}

public open class StatxTimestamp(
    public var tvSec: Long,
    public var tvNsec: UInt,
    public var statxTimestampPad1: IntArray,
) {
    public open fun clone(): StatxTimestamp = StatxTimestamp(tvSec, tvNsec, statxTimestampPad1)

    public companion object {
        public fun typeLayout(): TypeLayout<StatxTimestamp> =
            object : TypeLayout<StatxTimestamp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): StatxTimestamp =
                    StatxTimestamp(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.intArray(1)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: StatxTimestamp) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tvNsec)
                    (PrimitiveArrayLayouts.intArray(1)).writeNative(addr + 12L, value.statxTimestampPad1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StatxTimestamp =
                    StatxTimestamp(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.intArray(1)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StatxTimestamp) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tvNsec)
                    (PrimitiveArrayLayouts.intArray(1)).writeBytes(buf, offset + 12, value.statxTimestampPad1)
                }
            }
    }
}

public open class Statvfs(
    public var fBsize: CUlong,
    public var fFrsize: CUlong,
    public var fBlocks: FsblkcntT,
    public var fBfree: FsblkcntT,
    public var fBavail: FsblkcntT,
    public var fFiles: FsfilcntT,
    public var fFfree: FsfilcntT,
    public var fFavail: FsfilcntT,
    public var fFsid: CUlong,
    public var fFlag: CUlong,
    public var fNamemax: CUlong,
    public var fReserved: IntArray,
) {
    public open fun clone(): Statvfs = Statvfs(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax, fReserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs> =
            object : TypeLayout<Statvfs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveArrayLayouts.intArray(6)).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fBsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.fFrsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.fBlocks)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.fBfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.fBavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fFiles)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fFfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.fFavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.fFsid)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.fFlag)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.fNamemax)
                    (PrimitiveArrayLayouts.intArray(6)).writeNative(addr + 88L, value.fReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveArrayLayouts.intArray(6)).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fBsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.fFrsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.fBlocks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.fBfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.fBavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fFiles)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fFfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.fFavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.fFsid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.fFlag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.fNamemax)
                    (PrimitiveArrayLayouts.intArray(6)).writeBytes(buf, offset + 88, value.fReserved)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public open class Statvfs64(
    public var fBsize: CUlong,
    public var fFrsize: CUlong,
    public var fBlocks: Fsblkcnt64T,
    public var fBfree: Fsblkcnt64T,
    public var fBavail: Fsblkcnt64T,
    public var fFiles: Fsfilcnt64T,
    public var fFfree: Fsfilcnt64T,
    public var fFavail: Fsfilcnt64T,
    public var fFsid: CUlong,
    public var fFlag: CUlong,
    public var fNamemax: CUlong,
    public var fReserved: IntArray,
) {
    public open fun clone(): Statvfs64 = Statvfs64(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax, fReserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs64> =
            object : TypeLayout<Statvfs64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Statvfs64 =
                    Statvfs64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveArrayLayouts.intArray(6)).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: Statvfs64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fBsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.fFrsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.fBlocks)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.fBfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.fBavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fFiles)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fFfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.fFavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.fFsid)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.fFlag)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.fNamemax)
                    (PrimitiveArrayLayouts.intArray(6)).writeNative(addr + 88L, value.fReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs64 =
                    Statvfs64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveArrayLayouts.intArray(6)).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fBsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.fFrsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.fBlocks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.fBfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.fBavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fFiles)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fFfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.fFavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.fFsid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.fFlag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.fNamemax)
                    (PrimitiveArrayLayouts.intArray(6)).writeBytes(buf, offset + 88, value.fReserved)
                }
            }
    }
}

public open class Termios(
    public var cIflag: TcflagT,
    public var cOflag: TcflagT,
    public var cCflag: TcflagT,
    public var cLflag: TcflagT,
    public var cLine: CcT,
    public var cCc: UByteArray,
    public var cIspeed: SpeedT,
    public var cOspeed: SpeedT,
) {
    public open fun clone(): Termios = Termios(cIflag, cOflag, cCflag, cLflag, cLine, cCc, cIspeed, cOspeed)

    public companion object {
        public fun typeLayout(): TypeLayout<Termios> =
            object : TypeLayout<Termios> {
                override val alignBytes: Long = 4L
                private val off6: Long = alignUpBytes((17L + ((NCCS).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                private val off7: Long = alignUpBytes((off6 + 4L), 4L)
                override val sizeBytes: Long = alignUpBytes((off7 + 4L), 4L)
                override fun readNative(addr: Long): Termios =
                    Termios(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readNative(addr + 17L),
                        (PrimitiveLayouts.U32).readNative(addr + off6),
                        (PrimitiveLayouts.U32).readNative(addr + off7),
                    )
                override fun writeNative(addr: Long, value: Termios) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cOflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.cCflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 16L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeNative(addr + 17L, value.cCc)
                    (PrimitiveLayouts.U32).writeNative(addr + off6, value.cIspeed)
                    (PrimitiveLayouts.U32).writeNative(addr + off7, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios =
                    Termios(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readBytes(buf, offset + 17),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off6.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off7.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cOflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.cCflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 16, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeBytes(buf, offset + 17, value.cCc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off6.toInt(), value.cIspeed)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off7.toInt(), value.cOspeed)
                }
            }
    }
}

public open class Flock(
    public var lType: CShort,
    public var lWhence: CShort,
    public var lStart: OffT,
    public var lLen: OffT,
    public var lPid: PidT,
) {
    public open fun clone(): Flock = Flock(lType, lWhence, lStart, lLen, lPid)

    public companion object {
        public fun typeLayout(): TypeLayout<Flock> =
            object : TypeLayout<Flock> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Flock =
                    Flock(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Flock) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.lType)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.lWhence)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.lStart)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.lLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.lPid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Flock =
                    Flock(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Flock) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.lType)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.lWhence)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.lStart)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.lLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.lPid)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public open class Flock64(
    public var lType: CShort,
    public var lWhence: CShort,
    public var lStart: Off64T,
    public var lLen: Off64T,
    public var lPid: PidT,
) {
    public open fun clone(): Flock64 = Flock64(lType, lWhence, lStart, lLen, lPid)

    public companion object {
        public fun typeLayout(): TypeLayout<Flock64> =
            object : TypeLayout<Flock64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Flock64 =
                    Flock64(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Flock64) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.lType)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.lWhence)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.lStart)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.lLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.lPid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Flock64 =
                    Flock64(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Flock64) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.lType)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.lWhence)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.lStart)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.lLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.lPid)
                }
            }
    }
}

public open class RegexT internal constructor(
    public var reNsub: SizeT,
    opaqueCtorArg: MutPtr,
    paddingCtorArg: List<MutPtr>,
    public var nsub2: SizeT,
    public var padding2: CChar,
) {
    public var opaque: MutPtr = opaqueCtorArg
        internal set

    public var padding: List<MutPtr> = paddingCtorArg
        internal set

    public open fun clone(): RegexT = RegexT(reNsub, opaque, padding, nsub2, padding2)

    public companion object {
        public fun typeLayout(): TypeLayout<RegexT> =
            object : TypeLayout<RegexT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): RegexT =
                    RegexT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 4)).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U8).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: RegexT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.reNsub)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.opaque)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 4)).writeNative(addr + 16L, value.padding)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.nsub2)
                    (PrimitiveLayouts.U8).writeNative(addr + 56L, value.padding2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RegexT =
                    RegexT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 4)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RegexT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.reNsub)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.opaque)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 4)).writeBytes(buf, offset + 16, value.padding)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.nsub2)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 56, value.padding2)
                }
            }

        public operator fun invoke(
            reNsub: SizeT,
            opaque: MutPtr,
            padding: List<MutPtr>,
            nsub2: SizeT,
            padding2: CChar,
        ): RegexT = RegexT(
            reNsub = reNsub,
            opaqueCtorArg = opaque,
            paddingCtorArg = padding,
            nsub2 = nsub2,
            padding2 = padding2,
        )
    }
}

public open class Rtentry internal constructor(
    public var rtPad1: CUlong,
    public var rtDst: Sockaddr,
    public var rtGateway: Sockaddr,
    public var rtGenmask: Sockaddr,
    public var rtFlags: CUshort,
    public var rtPad2: CShort,
    public var rtPad3: CUlong,
    public var rtTos: CUchar,
    public var rtClass: CUchar,
    public var rtPad4: ShortArray,
    public var rtMetric: CShort,
    rtDevCtorArg: MutPtr,
    public var rtMtu: CUlong,
    public var rtWindow: CUlong,
    public var rtIrtt: CUshort,
) {
    public var rtDev: MutPtr = rtDevCtorArg
        internal set

    public open fun clone(): Rtentry = Rtentry(rtPad1, rtDst, rtGateway, rtGenmask, rtFlags, rtPad2, rtPad3, rtTos, rtClass, rtPad4, rtMetric, rtDev, rtMtu, rtWindow, rtIrtt)

    public companion object {
        public operator fun invoke(
            rtPad1: CUlong,
            rtDst: Sockaddr,
            rtGateway: Sockaddr,
            rtGenmask: Sockaddr,
            rtFlags: CUshort,
            rtPad2: CShort,
            rtPad3: CUlong,
            rtTos: CUchar,
            rtClass: CUchar,
            rtPad4: ShortArray,
            rtMetric: CShort,
            rtDev: MutPtr,
            rtMtu: CUlong,
            rtWindow: CUlong,
            rtIrtt: CUshort,
        ): Rtentry = Rtentry(
            rtPad1 = rtPad1,
            rtDst = rtDst,
            rtGateway = rtGateway,
            rtGenmask = rtGenmask,
            rtFlags = rtFlags,
            rtPad2 = rtPad2,
            rtPad3 = rtPad3,
            rtTos = rtTos,
            rtClass = rtClass,
            rtPad4 = rtPad4,
            rtMetric = rtMetric,
            rtDevCtorArg = rtDev,
            rtMtu = rtMtu,
            rtWindow = rtWindow,
            rtIrtt = rtIrtt,
        )
    }
}

public open class ExitStatus(
    public var eTermination: CShort,
    public var eExit: CShort,
) {
    public open fun clone(): ExitStatus = ExitStatus(eTermination, eExit)

    public companion object {
        public fun typeLayout(): TypeLayout<ExitStatus> =
            object : TypeLayout<ExitStatus> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: ExitStatus) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.eTermination)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.eExit)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitStatus) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.eTermination)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.eExit)
                }
            }
    }
}

public open class Elf64Chdr(
    public var chType: Elf64Word,
    public var chReserved: Elf64Word,
    public var chSize: Elf64Xword,
    public var chAddralign: Elf64Xword,
) {
    public open fun clone(): Elf64Chdr = Elf64Chdr(chType, chReserved, chSize, chAddralign)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf64Chdr> =
            object : TypeLayout<Elf64Chdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Elf64Chdr =
                    Elf64Chdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Elf64Chdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.chType)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.chReserved)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.chSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.chAddralign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf64Chdr =
                    Elf64Chdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf64Chdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.chType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.chReserved)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.chSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.chAddralign)
                }
            }
    }
}

public open class Elf32Chdr(
    public var chType: Elf32Word,
    public var chSize: Elf32Word,
    public var chAddralign: Elf32Word,
) {
    public open fun clone(): Elf32Chdr = Elf32Chdr(chType, chSize, chAddralign)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf32Chdr> =
            object : TypeLayout<Elf32Chdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Elf32Chdr =
                    Elf32Chdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Elf32Chdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.chType)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.chSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.chAddralign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf32Chdr =
                    Elf32Chdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf32Chdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.chType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.chSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.chAddralign)
                }
            }
    }
}

public open class Timex(
    public var modes: CUint,
    public var offset: CLong,
    public var freq: CLong,
    public var maxerror: CLong,
    public var esterror: CLong,
    public var status: CInt,
    public var constant: CLong,
    public var precision: CLong,
    public var tolerance: CLong,
    public var time: Timeval,
    public var tick: CLong,
    public var ppsfreq: CLong,
    public var jitter: CLong,
    public var shift: CInt,
    public var stabil: CLong,
    public var jitcnt: CLong,
    public var calcnt: CLong,
    public var errcnt: CLong,
    public var stbcnt: CLong,
    public var tai: CInt,
    public var padding: IntArray,
) {
    public open fun clone(): Timex = Timex(modes, offset, freq, maxerror, esterror, status, constant, precision, tolerance, time, tick, ppsfreq, jitter, shift, stabil, jitcnt, calcnt, errcnt, stbcnt, tai, padding)

    public companion object
}

public open class Ntptimeval(
    public var time: Timeval,
    public var maxerror: CLong,
    public var esterror: CLong,
) {
    public open fun clone(): Ntptimeval = Ntptimeval(time, maxerror, esterror)

    public companion object
}

// netinet/tcp.h
public open class TcpInfo(
    public var tcpiState: UByte,
    public var tcpiCaState: UByte,
    public var tcpiRetransmits: UByte,
    public var tcpiProbes: UByte,
    public var tcpiBackoff: UByte,
    public var tcpiOptions: UByte,
    /**
     * This contains the bitfields `tcpiSndWscale` and `tcpiRcvWscale`.
     * Each is 4 bits.
     */
    public var tcpiSndRcvWscale: UByte,
    /**
     * This contains the bitfields `tcpiDeliveryRateAppLimited` (1 bit) and
     * `tcpiFastopenClientFail` (2 bits).
     */
    public var tcpiDeliveryFastopenBitfields: UByte,
    public var tcpiRto: UInt,
    public var tcpiAto: UInt,
    public var tcpiSndMss: UInt,
    public var tcpiRcvMss: UInt,
    public var tcpiUnacked: UInt,
    public var tcpiSacked: UInt,
    public var tcpiLost: UInt,
    public var tcpiRetrans: UInt,
    public var tcpiFackets: UInt,
    public var tcpiLastDataSent: UInt,
    public var tcpiLastAckSent: UInt,
    public var tcpiLastDataRecv: UInt,
    public var tcpiLastAckRecv: UInt,
    public var tcpiPmtu: UInt,
    public var tcpiRcvSsthresh: UInt,
    public var tcpiRtt: UInt,
    public var tcpiRttvar: UInt,
    public var tcpiSndSsthresh: UInt,
    public var tcpiSndCwnd: UInt,
    public var tcpiAdvmss: UInt,
    public var tcpiReordering: UInt,
    public var tcpiRcvRtt: UInt,
    public var tcpiRcvSpace: UInt,
    public var tcpiTotalRetrans: UInt,
    public var tcpiPacingRate: ULong,
    public var tcpiMaxPacingRate: ULong,
    public var tcpiBytesAcked: ULong,
    public var tcpiBytesReceived: ULong,
    public var tcpiSegsOut: UInt,
    public var tcpiSegsIn: UInt,
    public var tcpiNotsentBytes: UInt,
    public var tcpiMinRtt: UInt,
    public var tcpiDataSegsIn: UInt,
    public var tcpiDataSegsOut: UInt,
    public var tcpiDeliveryRate: ULong,
    public var tcpiBusyTime: ULong,
    public var tcpiRwndLimited: ULong,
    public var tcpiSndbufLimited: ULong,
    public var tcpiDelivered: UInt,
    public var tcpiDeliveredCe: UInt,
    public var tcpiBytesSent: ULong,
    public var tcpiBytesRetrans: ULong,
    public var tcpiDsackDups: UInt,
    public var tcpiReordSeen: UInt,
    public var tcpiRcvOoopack: UInt,
    public var tcpiSndWnd: UInt,
) {
    public open fun clone(): TcpInfo = TcpInfo(tcpiState, tcpiCaState, tcpiRetransmits, tcpiProbes, tcpiBackoff, tcpiOptions, tcpiSndRcvWscale, tcpiDeliveryFastopenBitfields, tcpiRto, tcpiAto, tcpiSndMss, tcpiRcvMss, tcpiUnacked, tcpiSacked, tcpiLost, tcpiRetrans, tcpiFackets, tcpiLastDataSent, tcpiLastAckSent, tcpiLastDataRecv, tcpiLastAckRecv, tcpiPmtu, tcpiRcvSsthresh, tcpiRtt, tcpiRttvar, tcpiSndSsthresh, tcpiSndCwnd, tcpiAdvmss, tcpiReordering, tcpiRcvRtt, tcpiRcvSpace, tcpiTotalRetrans, tcpiPacingRate, tcpiMaxPacingRate, tcpiBytesAcked, tcpiBytesReceived, tcpiSegsOut, tcpiSegsIn, tcpiNotsentBytes, tcpiMinRtt, tcpiDataSegsIn, tcpiDataSegsOut, tcpiDeliveryRate, tcpiBusyTime, tcpiRwndLimited, tcpiSndbufLimited, tcpiDelivered, tcpiDeliveredCe, tcpiBytesSent, tcpiBytesRetrans, tcpiDsackDups, tcpiReordSeen, tcpiRcvOoopack, tcpiSndWnd)

    public companion object {
        public fun typeLayout(): TypeLayout<TcpInfo> =
            object : TypeLayout<TcpInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 232L
                override fun readNative(addr: Long): TcpInfo =
                    TcpInfo(
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
                        (PrimitiveLayouts.U32).readNative(addr + 72L),
                        (PrimitiveLayouts.U32).readNative(addr + 76L),
                        (PrimitiveLayouts.U32).readNative(addr + 80L),
                        (PrimitiveLayouts.U32).readNative(addr + 84L),
                        (PrimitiveLayouts.U32).readNative(addr + 88L),
                        (PrimitiveLayouts.U32).readNative(addr + 92L),
                        (PrimitiveLayouts.U32).readNative(addr + 96L),
                        (PrimitiveLayouts.U32).readNative(addr + 100L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U32).readNative(addr + 136L),
                        (PrimitiveLayouts.U32).readNative(addr + 140L),
                        (PrimitiveLayouts.U32).readNative(addr + 144L),
                        (PrimitiveLayouts.U32).readNative(addr + 148L),
                        (PrimitiveLayouts.U32).readNative(addr + 152L),
                        (PrimitiveLayouts.U32).readNative(addr + 156L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U32).readNative(addr + 192L),
                        (PrimitiveLayouts.U32).readNative(addr + 196L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                        (PrimitiveLayouts.U32).readNative(addr + 216L),
                        (PrimitiveLayouts.U32).readNative(addr + 220L),
                        (PrimitiveLayouts.U32).readNative(addr + 224L),
                        (PrimitiveLayouts.U32).readNative(addr + 228L),
                    )
                override fun writeNative(addr: Long, value: TcpInfo) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.tcpiState)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.tcpiCaState)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.tcpiRetransmits)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.tcpiProbes)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.tcpiBackoff)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.tcpiOptions)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.tcpiSndRcvWscale)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.tcpiDeliveryFastopenBitfields)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tcpiRto)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.tcpiAto)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.tcpiSndMss)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tcpiRcvMss)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.tcpiUnacked)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.tcpiSacked)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.tcpiLost)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.tcpiRetrans)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.tcpiFackets)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.tcpiLastDataSent)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.tcpiLastAckSent)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.tcpiLastDataRecv)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.tcpiLastAckRecv)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.tcpiPmtu)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.tcpiRcvSsthresh)
                    (PrimitiveLayouts.U32).writeNative(addr + 68L, value.tcpiRtt)
                    (PrimitiveLayouts.U32).writeNative(addr + 72L, value.tcpiRttvar)
                    (PrimitiveLayouts.U32).writeNative(addr + 76L, value.tcpiSndSsthresh)
                    (PrimitiveLayouts.U32).writeNative(addr + 80L, value.tcpiSndCwnd)
                    (PrimitiveLayouts.U32).writeNative(addr + 84L, value.tcpiAdvmss)
                    (PrimitiveLayouts.U32).writeNative(addr + 88L, value.tcpiReordering)
                    (PrimitiveLayouts.U32).writeNative(addr + 92L, value.tcpiRcvRtt)
                    (PrimitiveLayouts.U32).writeNative(addr + 96L, value.tcpiRcvSpace)
                    (PrimitiveLayouts.U32).writeNative(addr + 100L, value.tcpiTotalRetrans)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.tcpiPacingRate)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.tcpiMaxPacingRate)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.tcpiBytesAcked)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.tcpiBytesReceived)
                    (PrimitiveLayouts.U32).writeNative(addr + 136L, value.tcpiSegsOut)
                    (PrimitiveLayouts.U32).writeNative(addr + 140L, value.tcpiSegsIn)
                    (PrimitiveLayouts.U32).writeNative(addr + 144L, value.tcpiNotsentBytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 148L, value.tcpiMinRtt)
                    (PrimitiveLayouts.U32).writeNative(addr + 152L, value.tcpiDataSegsIn)
                    (PrimitiveLayouts.U32).writeNative(addr + 156L, value.tcpiDataSegsOut)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.tcpiDeliveryRate)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.tcpiBusyTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.tcpiRwndLimited)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.tcpiSndbufLimited)
                    (PrimitiveLayouts.U32).writeNative(addr + 192L, value.tcpiDelivered)
                    (PrimitiveLayouts.U32).writeNative(addr + 196L, value.tcpiDeliveredCe)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.tcpiBytesSent)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.tcpiBytesRetrans)
                    (PrimitiveLayouts.U32).writeNative(addr + 216L, value.tcpiDsackDups)
                    (PrimitiveLayouts.U32).writeNative(addr + 220L, value.tcpiReordSeen)
                    (PrimitiveLayouts.U32).writeNative(addr + 224L, value.tcpiRcvOoopack)
                    (PrimitiveLayouts.U32).writeNative(addr + 228L, value.tcpiSndWnd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TcpInfo =
                    TcpInfo(
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
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 76),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 92),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 100),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 140),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 148),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 156),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 196),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 220),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 228),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TcpInfo) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.tcpiState)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.tcpiCaState)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.tcpiRetransmits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.tcpiProbes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.tcpiBackoff)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.tcpiOptions)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.tcpiSndRcvWscale)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.tcpiDeliveryFastopenBitfields)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tcpiRto)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.tcpiAto)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.tcpiSndMss)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tcpiRcvMss)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.tcpiUnacked)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.tcpiSacked)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.tcpiLost)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.tcpiRetrans)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.tcpiFackets)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.tcpiLastDataSent)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.tcpiLastAckSent)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.tcpiLastDataRecv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.tcpiLastAckRecv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.tcpiPmtu)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.tcpiRcvSsthresh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.tcpiRtt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.tcpiRttvar)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 76, value.tcpiSndSsthresh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 80, value.tcpiSndCwnd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 84, value.tcpiAdvmss)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 88, value.tcpiReordering)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 92, value.tcpiRcvRtt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 96, value.tcpiRcvSpace)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 100, value.tcpiTotalRetrans)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.tcpiPacingRate)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.tcpiMaxPacingRate)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.tcpiBytesAcked)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.tcpiBytesReceived)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 136, value.tcpiSegsOut)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 140, value.tcpiSegsIn)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 144, value.tcpiNotsentBytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 148, value.tcpiMinRtt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 152, value.tcpiDataSegsIn)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 156, value.tcpiDataSegsOut)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.tcpiDeliveryRate)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.tcpiBusyTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.tcpiRwndLimited)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.tcpiSndbufLimited)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 192, value.tcpiDelivered)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 196, value.tcpiDeliveredCe)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.tcpiBytesSent)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.tcpiBytesRetrans)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 216, value.tcpiDsackDups)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 220, value.tcpiReordSeen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 224, value.tcpiRcvOoopack)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 228, value.tcpiSndWnd)
                }
            }
    }
}

public open class Statfs(
    public var fType: CUlong,
    public var fBsize: CUlong,
    public var fBlocks: FsblkcntT,
    public var fBfree: FsblkcntT,
    public var fBavail: FsblkcntT,
    public var fFiles: FsfilcntT,
    public var fFfree: FsfilcntT,
    public var fFsid: FsidT,
    public var fNamelen: CUlong,
    public var fFrsize: CUlong,
    public var fFlags: CUlong,
    public var fSpare: ULongArray,
) {
    public open fun clone(): Statfs = Statfs(fType, fBsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fNamelen, fFrsize, fFlags, fSpare)

    public companion object
}

public open class Statfs64(
    public var fType: CUlong,
    public var fBsize: CUlong,
    public var fBlocks: Fsblkcnt64T,
    public var fBfree: Fsblkcnt64T,
    public var fBavail: Fsblkcnt64T,
    public var fFiles: Fsfilcnt64T,
    public var fFfree: Fsfilcnt64T,
    public var fFsid: FsidT,
    public var fNamelen: CUlong,
    public var fFrsize: CUlong,
    public var fFlags: CUlong,
    public var fSpare: ULongArray,
) {
    public open fun clone(): Statfs64 = Statfs64(fType, fBsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fNamelen, fFrsize, fFlags, fSpare)

    public companion object
}

public open class Sysinfo(
    public var uptime: CUlong,
    public var loads: ULongArray,
    public var totalram: CUlong,
    public var freeram: CUlong,
    public var sharedram: CUlong,
    public var bufferram: CUlong,
    public var totalswap: CUlong,
    public var freeswap: CUlong,
    public var procs: CUshort,
    public var pad: CUshort,
    public var totalhigh: CUlong,
    public var freehigh: CUlong,
    public var memUnit: CUint,
    public var reserved: UByteArray,
) {
    public open fun clone(): Sysinfo = Sysinfo(uptime, loads, totalram, freeram, sharedram, bufferram, totalswap, freeswap, procs, pad, totalhigh, freehigh, memUnit, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Sysinfo> =
            object : TypeLayout<Sysinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 368L
                override fun readNative(addr: Long): Sysinfo =
                    Sysinfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U16).readNative(addr + 80L),
                        (PrimitiveLayouts.U16).readNative(addr + 82L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U32).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 108L),
                    )
                override fun writeNative(addr: Long, value: Sysinfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.uptime)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 8L, value.loads)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.totalram)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.freeram)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.sharedram)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.bufferram)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.totalswap)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.freeswap)
                    (PrimitiveLayouts.U16).writeNative(addr + 80L, value.procs)
                    (PrimitiveLayouts.U16).writeNative(addr + 82L, value.pad)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.totalhigh)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.freehigh)
                    (PrimitiveLayouts.U32).writeNative(addr + 104L, value.memUnit)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 108L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sysinfo =
                    Sysinfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 108),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sysinfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.uptime)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 8, value.loads)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.totalram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.freeram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.sharedram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.bufferram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.totalswap)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.freeswap)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 80, value.procs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 82, value.pad)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.totalhigh)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.freehigh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 104, value.memUnit)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 108, value.reserved)
                }
            }
    }
}

public open class Utmpx(
    public var utType: CShort,
    public var utPad1: CShort,
    public var utPid: PidT,
    public var utLine: UByteArray,
    public var utId: UByteArray,
    public var utUser: UByteArray,
    public var utHost: UByteArray,
    public var utExit: ExitStatus,
    public var utSession: CLong,
    public var utPad2: CInt,
    public var utTv: Timeval,
    public var utAddrV6: UIntArray,
    public var unused: UByteArray,
) {
    public open fun clone(): Utmpx = Utmpx(utType, utPad1, utPid, utLine, utId, utUser, utHost, utExit, utSession, utPad2, utTv, utAddrV6, unused)

    public companion object
}
