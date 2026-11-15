@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.*
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
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.CcT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SighandlerT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.FsidT
import io.github.kotlinmania.rustlang.unix.bsd.Sockaddr
import io.github.kotlinmania.rustlang.unix.bsd.apple.Sigevent
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.bsd.apple.TcflagT
import io.github.kotlinmania.rustlang.unix.linuxlike.Elf32Word
import io.github.kotlinmania.rustlang.unix.linuxlike.Elf64Word
import io.github.kotlinmania.rustlang.unix.linuxlike.Elf64Xword
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Timeval
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.NCCS
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.b64.SigsetT

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public class Aiocb internal constructor(
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

    public constructor() : this(
        aioFildes = 0,
        aioLioOpcode = 0,
        aioReqprio = 0,
        aioBufCtorArg = MutPtr(0L),
        aioNbytes = 0uL,
        aioSigevent = Sigevent(),
        tdCtorArg = MutPtr(0L),
        lock = IntArray(2),
        err = 0,
        ret = 0L,
        aioOffset = 0L,
        nextCtorArg = MutPtr(0L),
        prevCtorArg = MutPtr(0L),
        dummy4 = UByteArray((32 - 2 * sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toInt()),
    )

    public operator fun component1(): CInt = this.aioFildes

    public operator fun component2(): CInt = this.aioLioOpcode

    public operator fun component3(): CInt = this.aioReqprio

    public operator fun component4(): MutPtr = this.aioBuf

    public operator fun component5(): SizeT = this.aioNbytes

    public operator fun component6(): Sigevent = this.aioSigevent

    public operator fun component7(): MutPtr = this.td

    public operator fun component8(): IntArray = this.lock

    public operator fun component9(): CInt = this.err

    public operator fun component10(): SsizeT = this.ret

    public operator fun component11(): OffT = this.aioOffset

    public operator fun component12(): MutPtr = this.next

    public operator fun component13(): MutPtr = this.prev

    public operator fun component14(): UByteArray = this.dummy4

    public fun copy(aioFildes: CInt = this.aioFildes, aioLioOpcode: CInt = this.aioLioOpcode, aioReqprio: CInt = this.aioReqprio, aioBuf: MutPtr = this.aioBuf, aioNbytes: SizeT = this.aioNbytes, aioSigevent: Sigevent = this.aioSigevent, td: MutPtr = this.td, lock: IntArray = this.lock, err: CInt = this.err, ret: SsizeT = this.ret, aioOffset: OffT = this.aioOffset, next: MutPtr = this.next, prev: MutPtr = this.prev, dummy4: UByteArray = this.dummy4): Aiocb = Aiocb(aioFildes = aioFildes, aioLioOpcode = aioLioOpcode, aioReqprio = aioReqprio, aioBufCtorArg = aioBuf, aioNbytes = aioNbytes, aioSigevent = aioSigevent, tdCtorArg = td, lock = lock, err = err, ret = ret, aioOffset = aioOffset, nextCtorArg = next, prevCtorArg = prev, dummy4 = dummy4)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Aiocb) return false
        if (this.aioFildes != other.aioFildes) return false
        if (this.aioLioOpcode != other.aioLioOpcode) return false
        if (this.aioReqprio != other.aioReqprio) return false
        if (this.aioBuf != other.aioBuf) return false
        if (this.aioNbytes != other.aioNbytes) return false
        if (this.aioSigevent != other.aioSigevent) return false
        if (this.td != other.td) return false
        if (!this.lock.contentEquals(other.lock)) return false
        if (this.err != other.err) return false
        if (this.ret != other.ret) return false
        if (this.aioOffset != other.aioOffset) return false
        if (this.next != other.next) return false
        if (this.prev != other.prev) return false
        if (!this.dummy4.contentEquals(other.dummy4)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.aioFildes.hashCode()
        result = 31 * result + this.aioLioOpcode.hashCode()
        result = 31 * result + this.aioReqprio.hashCode()
        result = 31 * result + this.aioBuf.hashCode()
        result = 31 * result + this.aioNbytes.hashCode()
        result = 31 * result + this.aioSigevent.hashCode()
        result = 31 * result + this.td.hashCode()
        result = 31 * result + this.lock.contentHashCode()
        result = 31 * result + this.err.hashCode()
        result = 31 * result + this.ret.hashCode()
        result = 31 * result + this.aioOffset.hashCode()
        result = 31 * result + this.next.hashCode()
        result = 31 * result + this.prev.hashCode()
        result = 31 * result + this.dummy4.contentHashCode()
        return result
    }

    public fun clone(): Aiocb = Aiocb(aioFildes, aioLioOpcode, aioReqprio, aioBuf, aioNbytes, aioSigevent, td, lock, err, ret, aioOffset, next, prev, dummy4)

    public companion object {
        public operator fun invoke(
            aioFildes: CInt = 0,
            aioLioOpcode: CInt = 0,
            aioReqprio: CInt = 0,
            aioBuf: MutPtr = MutPtr(0L),
            aioNbytes: SizeT = 0uL,
            aioSigevent: Sigevent = Sigevent(),
            td: MutPtr = MutPtr(0L),
            lock: IntArray = IntArray(2),
            err: CInt = 0,
            ret: SsizeT = 0L,
            aioOffset: OffT = 0L,
            next: MutPtr = MutPtr(0L),
            prev: MutPtr = MutPtr(0L),
            dummy4: UByteArray = UByteArray((32 - 2 * sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toInt()),
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

public data class FanotifyEventMetadata(
    public var eventLen: CUint = 0u,
    public var vers: CUchar = 0u,
    public var reserved: CUchar = 0u,
    public var metadataLen: CUshort = 0u,
    public var mask: CUlonglong = 0uL,
    public var fd: CInt = 0,
    public var pid: CInt = 0,
) {
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

public data class Sigaction(
    public var saSigaction: SighandlerT = 0uL,
    public var saMask: SigsetT = SigsetT(),
    public var saFlags: CInt = 0,
    public var saRestorer: SigactionSaRestorerCallback? = null,
)

// musl's uint64T is an unsigned long instead of an unsigned long long on
// 64-bit targets, so it's not a __u64 (which is cUlonglong), but rather a
// real u64. This is also how we handle musl's uint64T elsewhere.
public class Statx(
    public var stxMask: UInt = 0u,
    public var stxBlksize: UInt = 0u,
    public var stxAttributes: ULong = 0uL,
    public var stxNlink: UInt = 0u,
    public var stxUid: UInt = 0u,
    public var stxGid: UInt = 0u,
    public var stxMode: UShort = 0u,
    public var statxPad1: UShortArray = UShortArray(1),
    public var stxIno: ULong = 0uL,
    public var stxSize: ULong = 0uL,
    public var stxBlocks: ULong = 0uL,
    public var stxAttributesMask: ULong = 0uL,
    public var stxAtime: StatxTimestamp = StatxTimestamp(),
    public var stxBtime: StatxTimestamp = StatxTimestamp(),
    public var stxCtime: StatxTimestamp = StatxTimestamp(),
    public var stxMtime: StatxTimestamp = StatxTimestamp(),
    public var stxRdevMajor: UInt = 0u,
    public var stxRdevMinor: UInt = 0u,
    public var stxDevMajor: UInt = 0u,
    public var stxDevMinor: UInt = 0u,
    public var stxMntId: ULong = 0uL,
    public var stxDioMemAlign: UInt = 0u,
    public var stxDioOffsetAlign: UInt = 0u,
    public var stxSubvol: ULong = 0uL,
    public var stxAtomicWriteUnitMin: UInt = 0u,
    public var stxAtomicWriteUnitMax: UInt = 0u,
    public var stxAtomicWriteSegmentsMax: UInt = 0u,
    public var statxPad2: UIntArray = UIntArray(1),
    public var statxPad3: ULongArray = ULongArray(9),
) {
    public fun clone(): Statx = Statx(stxMask, stxBlksize, stxAttributes, stxNlink, stxUid, stxGid, stxMode, statxPad1, stxIno, stxSize, stxBlocks, stxAttributesMask, stxAtime, stxBtime, stxCtime, stxMtime, stxRdevMajor, stxRdevMinor, stxDevMajor, stxDevMinor, stxMntId, stxDioMemAlign, stxDioOffsetAlign, stxSubvol, stxAtomicWriteUnitMin, stxAtomicWriteUnitMax, stxAtomicWriteSegmentsMax, statxPad2, statxPad3)

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

public data class StatxTimestamp(
    public var tvSec: Long = 0L,
    public var tvNsec: UInt = 0u,
    public var statxTimestampPad1: IntArray = IntArray(1),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StatxTimestamp) return false
        if (this.tvSec != other.tvSec) return false
        if (this.tvNsec != other.tvNsec) return false
        if (!this.statxTimestampPad1.contentEquals(other.statxTimestampPad1)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.tvSec.hashCode()
        result = 31 * result + this.tvNsec.hashCode()
        result = 31 * result + this.statxTimestampPad1.contentHashCode()
        return result
    }

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

public data class Statvfs(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsblkcntT = 0uL,
    public var fBfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsblkcntT = 0uL,
    public var fBavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsblkcntT = 0uL,
    public var fFiles: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsfilcntT = 0uL,
    public var fFfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsfilcntT = 0uL,
    public var fFavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsfilcntT = 0uL,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
    public var fReserved: IntArray = IntArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statvfs) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFavail != other.fFavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fFlag != other.fFlag) return false
        if (this.fNamemax != other.fNamemax) return false
        if (!this.fReserved.contentEquals(other.fReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fFlag.hashCode()
        result = 31 * result + this.fNamemax.hashCode()
        result = 31 * result + this.fReserved.contentHashCode()
        return result
    }

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
public data class Statvfs64(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsblkcnt64T = 0uL,
    public var fBfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsblkcnt64T = 0uL,
    public var fBavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsblkcnt64T = 0uL,
    public var fFiles: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsfilcnt64T = 0uL,
    public var fFfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsfilcnt64T = 0uL,
    public var fFavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsfilcnt64T = 0uL,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
    public var fReserved: IntArray = IntArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statvfs64) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFavail != other.fFavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fFlag != other.fFlag) return false
        if (this.fNamemax != other.fNamemax) return false
        if (!this.fReserved.contentEquals(other.fReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fFlag.hashCode()
        result = 31 * result + this.fNamemax.hashCode()
        result = 31 * result + this.fReserved.contentHashCode()
        return result
    }

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

public data class Termios(
    public var cIflag: TcflagT = 0uL,
    public var cOflag: TcflagT = 0uL,
    public var cCflag: TcflagT = 0uL,
    public var cLflag: TcflagT = 0uL,
    public var cLine: CcT = 0u,
    public var cCc: UByteArray = UByteArray(NCCS),
    public var cIspeed: SpeedT = 0uL,
    public var cOspeed: SpeedT = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Termios) return false
        if (this.cIflag != other.cIflag) return false
        if (this.cOflag != other.cOflag) return false
        if (this.cCflag != other.cCflag) return false
        if (this.cLflag != other.cLflag) return false
        if (this.cLine != other.cLine) return false
        if (!this.cCc.contentEquals(other.cCc)) return false
        if (this.cIspeed != other.cIspeed) return false
        if (this.cOspeed != other.cOspeed) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cIflag.hashCode()
        result = 31 * result + this.cOflag.hashCode()
        result = 31 * result + this.cCflag.hashCode()
        result = 31 * result + this.cLflag.hashCode()
        result = 31 * result + this.cLine.hashCode()
        result = 31 * result + this.cCc.contentHashCode()
        result = 31 * result + this.cIspeed.hashCode()
        result = 31 * result + this.cOspeed.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Termios> =
            object : TypeLayout<Termios> {
                override val alignBytes: Long = 8L
                private val off6: Long = alignUpBytes((33L + ((NCCS).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                private val off7: Long = alignUpBytes((off6 + 8L), 8L)
                override val sizeBytes: Long = alignUpBytes((off7 + 8L), 8L)
                override fun readNative(addr: Long): Termios =
                    Termios(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U8).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readNative(addr + 33L),
                        (PrimitiveLayouts.U64).readNative(addr + off6),
                        (PrimitiveLayouts.U64).readNative(addr + off7),
                    )
                override fun writeNative(addr: Long, value: Termios) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.cOflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.cCflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 32L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeNative(addr + 33L, value.cCc)
                    (PrimitiveLayouts.U64).writeNative(addr + off6, value.cIspeed)
                    (PrimitiveLayouts.U64).writeNative(addr + off7, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios =
                    Termios(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readBytes(buf, offset + 33),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off6.toInt()),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off7.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.cOflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.cCflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 32, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeBytes(buf, offset + 33, value.cCc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off6.toInt(), value.cIspeed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off7.toInt(), value.cOspeed)
                }
            }
    }
}

public data class Flock(
    public var lType: CShort = 0,
    public var lWhence: CShort = 0,
    public var lStart: OffT = 0L,
    public var lLen: OffT = 0L,
    public var lPid: PidT = 0,
) {
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
public data class Flock64(
    public var lType: CShort = 0,
    public var lWhence: CShort = 0,
    public var lStart: Off64T = 0L,
    public var lLen: Off64T = 0L,
    public var lPid: PidT = 0,
) {
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

public class RegexT internal constructor(
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

    public constructor() : this(
        reNsub = 0uL,
        opaqueCtorArg = MutPtr(0L),
        paddingCtorArg = List(4) { MutPtr(0L) },
        nsub2 = 0uL,
        padding2 = 0u,
    )

    public operator fun component1(): SizeT = this.reNsub

    public operator fun component2(): MutPtr = this.opaque

    public operator fun component3(): List<MutPtr> = this.padding

    public operator fun component4(): SizeT = this.nsub2

    public operator fun component5(): CChar = this.padding2

    public fun copy(reNsub: SizeT = this.reNsub, opaque: MutPtr = this.opaque, padding: List<MutPtr> = this.padding, nsub2: SizeT = this.nsub2, padding2: CChar = this.padding2): RegexT = RegexT(reNsub = reNsub, opaqueCtorArg = opaque, paddingCtorArg = padding, nsub2 = nsub2, padding2 = padding2)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegexT) return false
        if (this.reNsub != other.reNsub) return false
        if (this.opaque != other.opaque) return false
        if (this.padding != other.padding) return false
        if (this.nsub2 != other.nsub2) return false
        if (this.padding2 != other.padding2) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.reNsub.hashCode()
        result = 31 * result + this.opaque.hashCode()
        result = 31 * result + this.padding.hashCode()
        result = 31 * result + this.nsub2.hashCode()
        result = 31 * result + this.padding2.hashCode()
        return result
    }

    public fun clone(): RegexT = RegexT(reNsub, opaque, padding, nsub2, padding2)

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
            reNsub: SizeT = 0uL,
            opaque: MutPtr = MutPtr(0L),
            padding: List<MutPtr> = List(4) { MutPtr(0L) },
            nsub2: SizeT = 0uL,
            padding2: CChar = 0u,
        ): RegexT = RegexT(
            reNsub = reNsub,
            opaqueCtorArg = opaque,
            paddingCtorArg = padding,
            nsub2 = nsub2,
            padding2 = padding2,
        )
    }
}

public class Rtentry internal constructor(
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

    public constructor() : this(
        rtPad1 = 0uL,
        rtDst = Sockaddr(),
        rtGateway = Sockaddr(),
        rtGenmask = Sockaddr(),
        rtFlags = 0u,
        rtPad2 = 0,
        rtPad3 = 0uL,
        rtTos = 0u,
        rtClass = 0u,
        rtPad4 = ShortArray(3),
        rtMetric = 0,
        rtDevCtorArg = MutPtr(0L),
        rtMtu = 0uL,
        rtWindow = 0uL,
        rtIrtt = 0u,
    )

    public operator fun component1(): CUlong = this.rtPad1

    public operator fun component2(): Sockaddr = this.rtDst

    public operator fun component3(): Sockaddr = this.rtGateway

    public operator fun component4(): Sockaddr = this.rtGenmask

    public operator fun component5(): CUshort = this.rtFlags

    public operator fun component6(): CShort = this.rtPad2

    public operator fun component7(): CUlong = this.rtPad3

    public operator fun component8(): CUchar = this.rtTos

    public operator fun component9(): CUchar = this.rtClass

    public operator fun component10(): ShortArray = this.rtPad4

    public operator fun component11(): CShort = this.rtMetric

    public operator fun component12(): MutPtr = this.rtDev

    public operator fun component13(): CUlong = this.rtMtu

    public operator fun component14(): CUlong = this.rtWindow

    public operator fun component15(): CUshort = this.rtIrtt

    public fun copy(rtPad1: CUlong = this.rtPad1, rtDst: Sockaddr = this.rtDst, rtGateway: Sockaddr = this.rtGateway, rtGenmask: Sockaddr = this.rtGenmask, rtFlags: CUshort = this.rtFlags, rtPad2: CShort = this.rtPad2, rtPad3: CUlong = this.rtPad3, rtTos: CUchar = this.rtTos, rtClass: CUchar = this.rtClass, rtPad4: ShortArray = this.rtPad4, rtMetric: CShort = this.rtMetric, rtDev: MutPtr = this.rtDev, rtMtu: CUlong = this.rtMtu, rtWindow: CUlong = this.rtWindow, rtIrtt: CUshort = this.rtIrtt): Rtentry = Rtentry(rtPad1 = rtPad1, rtDst = rtDst, rtGateway = rtGateway, rtGenmask = rtGenmask, rtFlags = rtFlags, rtPad2 = rtPad2, rtPad3 = rtPad3, rtTos = rtTos, rtClass = rtClass, rtPad4 = rtPad4, rtMetric = rtMetric, rtDevCtorArg = rtDev, rtMtu = rtMtu, rtWindow = rtWindow, rtIrtt = rtIrtt)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rtentry) return false
        if (this.rtPad1 != other.rtPad1) return false
        if (this.rtDst != other.rtDst) return false
        if (this.rtGateway != other.rtGateway) return false
        if (this.rtGenmask != other.rtGenmask) return false
        if (this.rtFlags != other.rtFlags) return false
        if (this.rtPad2 != other.rtPad2) return false
        if (this.rtPad3 != other.rtPad3) return false
        if (this.rtTos != other.rtTos) return false
        if (this.rtClass != other.rtClass) return false
        if (!this.rtPad4.contentEquals(other.rtPad4)) return false
        if (this.rtMetric != other.rtMetric) return false
        if (this.rtDev != other.rtDev) return false
        if (this.rtMtu != other.rtMtu) return false
        if (this.rtWindow != other.rtWindow) return false
        if (this.rtIrtt != other.rtIrtt) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.rtPad1.hashCode()
        result = 31 * result + this.rtDst.hashCode()
        result = 31 * result + this.rtGateway.hashCode()
        result = 31 * result + this.rtGenmask.hashCode()
        result = 31 * result + this.rtFlags.hashCode()
        result = 31 * result + this.rtPad2.hashCode()
        result = 31 * result + this.rtPad3.hashCode()
        result = 31 * result + this.rtTos.hashCode()
        result = 31 * result + this.rtClass.hashCode()
        result = 31 * result + this.rtPad4.contentHashCode()
        result = 31 * result + this.rtMetric.hashCode()
        result = 31 * result + this.rtDev.hashCode()
        result = 31 * result + this.rtMtu.hashCode()
        result = 31 * result + this.rtWindow.hashCode()
        result = 31 * result + this.rtIrtt.hashCode()
        return result
    }

    public fun clone(): Rtentry = Rtentry(rtPad1, rtDst, rtGateway, rtGenmask, rtFlags, rtPad2, rtPad3, rtTos, rtClass, rtPad4, rtMetric, rtDev, rtMtu, rtWindow, rtIrtt)

    public companion object {
        public operator fun invoke(
            rtPad1: CUlong = 0uL,
            rtDst: Sockaddr = Sockaddr(),
            rtGateway: Sockaddr = Sockaddr(),
            rtGenmask: Sockaddr = Sockaddr(),
            rtFlags: CUshort = 0u,
            rtPad2: CShort = 0,
            rtPad3: CUlong = 0uL,
            rtTos: CUchar = 0u,
            rtClass: CUchar = 0u,
            rtPad4: ShortArray = ShortArray(3),
            rtMetric: CShort = 0,
            rtDev: MutPtr = MutPtr(0L),
            rtMtu: CUlong = 0uL,
            rtWindow: CUlong = 0uL,
            rtIrtt: CUshort = 0u,
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

public data class ExitStatus(
    public var eTermination: CShort = 0,
    public var eExit: CShort = 0,
) {
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

public data class Elf64Chdr(
    public var chType: Elf64Word = 0u,
    public var chReserved: Elf64Word = 0u,
    public var chSize: Elf64Xword = 0uL,
    public var chAddralign: Elf64Xword = 0uL,
) {
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

public data class Elf32Chdr(
    public var chType: Elf32Word = 0u,
    public var chSize: Elf32Word = 0u,
    public var chAddralign: Elf32Word = 0u,
) {
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

public class Timex(
    public var modes: CUint = 0u,
    public var offset: CLong = 0L,
    public var freq: CLong = 0L,
    public var maxerror: CLong = 0L,
    public var esterror: CLong = 0L,
    public var status: CInt = 0,
    public var constant: CLong = 0L,
    public var precision: CLong = 0L,
    public var tolerance: CLong = 0L,
    public var time: Timeval = Timeval(),
    public var tick: CLong = 0L,
    public var ppsfreq: CLong = 0L,
    public var jitter: CLong = 0L,
    public var shift: CInt = 0,
    public var stabil: CLong = 0L,
    public var jitcnt: CLong = 0L,
    public var calcnt: CLong = 0L,
    public var errcnt: CLong = 0L,
    public var stbcnt: CLong = 0L,
    public var tai: CInt = 0,
    public var padding: IntArray = IntArray(11),
) {
    public fun clone(): Timex = Timex(modes, offset, freq, maxerror, esterror, status, constant, precision, tolerance, time, tick, ppsfreq, jitter, shift, stabil, jitcnt, calcnt, errcnt, stbcnt, tai, padding)
}

public data class Ntptimeval(
    public var time: Timeval = Timeval(),
    public var maxerror: CLong = 0L,
    public var esterror: CLong = 0L,
)

// netinet/tcp.h
public class TcpInfo(
    public var tcpiState: UByte = 0u,
    public var tcpiCaState: UByte = 0u,
    public var tcpiRetransmits: UByte = 0u,
    public var tcpiProbes: UByte = 0u,
    public var tcpiBackoff: UByte = 0u,
    public var tcpiOptions: UByte = 0u,
    /**
     * This contains the bitfields `tcpiSndWscale` and `tcpiRcvWscale`.
     * Each is 4 bits.
     */
    public var tcpiSndRcvWscale: UByte = 0u,
    /**
     * This contains the bitfields `tcpiDeliveryRateAppLimited` (1 bit) and
     * `tcpiFastopenClientFail` (2 bits).
     */
    public var tcpiDeliveryFastopenBitfields: UByte = 0u,
    public var tcpiRto: UInt = 0u,
    public var tcpiAto: UInt = 0u,
    public var tcpiSndMss: UInt = 0u,
    public var tcpiRcvMss: UInt = 0u,
    public var tcpiUnacked: UInt = 0u,
    public var tcpiSacked: UInt = 0u,
    public var tcpiLost: UInt = 0u,
    public var tcpiRetrans: UInt = 0u,
    public var tcpiFackets: UInt = 0u,
    public var tcpiLastDataSent: UInt = 0u,
    public var tcpiLastAckSent: UInt = 0u,
    public var tcpiLastDataRecv: UInt = 0u,
    public var tcpiLastAckRecv: UInt = 0u,
    public var tcpiPmtu: UInt = 0u,
    public var tcpiRcvSsthresh: UInt = 0u,
    public var tcpiRtt: UInt = 0u,
    public var tcpiRttvar: UInt = 0u,
    public var tcpiSndSsthresh: UInt = 0u,
    public var tcpiSndCwnd: UInt = 0u,
    public var tcpiAdvmss: UInt = 0u,
    public var tcpiReordering: UInt = 0u,
    public var tcpiRcvRtt: UInt = 0u,
    public var tcpiRcvSpace: UInt = 0u,
    public var tcpiTotalRetrans: UInt = 0u,
    public var tcpiPacingRate: ULong = 0uL,
    public var tcpiMaxPacingRate: ULong = 0uL,
    public var tcpiBytesAcked: ULong = 0uL,
    public var tcpiBytesReceived: ULong = 0uL,
    public var tcpiSegsOut: UInt = 0u,
    public var tcpiSegsIn: UInt = 0u,
    public var tcpiNotsentBytes: UInt = 0u,
    public var tcpiMinRtt: UInt = 0u,
    public var tcpiDataSegsIn: UInt = 0u,
    public var tcpiDataSegsOut: UInt = 0u,
    public var tcpiDeliveryRate: ULong = 0uL,
    public var tcpiBusyTime: ULong = 0uL,
    public var tcpiRwndLimited: ULong = 0uL,
    public var tcpiSndbufLimited: ULong = 0uL,
    public var tcpiDelivered: UInt = 0u,
    public var tcpiDeliveredCe: UInt = 0u,
    public var tcpiBytesSent: ULong = 0uL,
    public var tcpiBytesRetrans: ULong = 0uL,
    public var tcpiDsackDups: UInt = 0u,
    public var tcpiReordSeen: UInt = 0u,
    public var tcpiRcvOoopack: UInt = 0u,
    public var tcpiSndWnd: UInt = 0u,
) {
    public fun clone(): TcpInfo = TcpInfo(tcpiState, tcpiCaState, tcpiRetransmits, tcpiProbes, tcpiBackoff, tcpiOptions, tcpiSndRcvWscale, tcpiDeliveryFastopenBitfields, tcpiRto, tcpiAto, tcpiSndMss, tcpiRcvMss, tcpiUnacked, tcpiSacked, tcpiLost, tcpiRetrans, tcpiFackets, tcpiLastDataSent, tcpiLastAckSent, tcpiLastDataRecv, tcpiLastAckRecv, tcpiPmtu, tcpiRcvSsthresh, tcpiRtt, tcpiRttvar, tcpiSndSsthresh, tcpiSndCwnd, tcpiAdvmss, tcpiReordering, tcpiRcvRtt, tcpiRcvSpace, tcpiTotalRetrans, tcpiPacingRate, tcpiMaxPacingRate, tcpiBytesAcked, tcpiBytesReceived, tcpiSegsOut, tcpiSegsIn, tcpiNotsentBytes, tcpiMinRtt, tcpiDataSegsIn, tcpiDataSegsOut, tcpiDeliveryRate, tcpiBusyTime, tcpiRwndLimited, tcpiSndbufLimited, tcpiDelivered, tcpiDeliveredCe, tcpiBytesSent, tcpiBytesRetrans, tcpiDsackDups, tcpiReordSeen, tcpiRcvOoopack, tcpiSndWnd)

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

public data class Statfs(
    public var fType: CUlong = 0uL,
    public var fBsize: CUlong = 0uL,
    public var fBlocks: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsblkcntT = 0uL,
    public var fBfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsblkcntT = 0uL,
    public var fBavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsblkcntT = 0uL,
    public var fFiles: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsfilcntT = 0uL,
    public var fFfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.FsfilcntT = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fFlags: CUlong = 0uL,
    public var fSpare: ULongArray = ULongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statfs) return false
        if (this.fType != other.fType) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fNamelen != other.fNamelen) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fFlags != other.fFlags) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fType.hashCode()
        result = 31 * result + this.fBsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fNamelen.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fFlags.hashCode()
        result = 31 * result + this.fSpare.contentHashCode()
        return result
    }
}

public data class Statfs64(
    public var fType: CUlong = 0uL,
    public var fBsize: CUlong = 0uL,
    public var fBlocks: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsblkcnt64T = 0uL,
    public var fBfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsblkcnt64T = 0uL,
    public var fBavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsblkcnt64T = 0uL,
    public var fFiles: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsfilcnt64T = 0uL,
    public var fFfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.Fsfilcnt64T = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fFlags: CUlong = 0uL,
    public var fSpare: ULongArray = ULongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statfs64) return false
        if (this.fType != other.fType) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fNamelen != other.fNamelen) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fFlags != other.fFlags) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fType.hashCode()
        result = 31 * result + this.fBsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fNamelen.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fFlags.hashCode()
        result = 31 * result + this.fSpare.contentHashCode()
        return result
    }
}

public data class Sysinfo(
    public var uptime: CUlong = 0uL,
    public var loads: ULongArray = ULongArray(3),
    public var totalram: CUlong = 0uL,
    public var freeram: CUlong = 0uL,
    public var sharedram: CUlong = 0uL,
    public var bufferram: CUlong = 0uL,
    public var totalswap: CUlong = 0uL,
    public var freeswap: CUlong = 0uL,
    public var procs: CUshort = 0u,
    public var pad: CUshort = 0u,
    public var totalhigh: CUlong = 0uL,
    public var freehigh: CUlong = 0uL,
    public var memUnit: CUint = 0u,
    public var reserved: UByteArray = UByteArray(256),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sysinfo) return false
        if (this.uptime != other.uptime) return false
        if (!this.loads.contentEquals(other.loads)) return false
        if (this.totalram != other.totalram) return false
        if (this.freeram != other.freeram) return false
        if (this.sharedram != other.sharedram) return false
        if (this.bufferram != other.bufferram) return false
        if (this.totalswap != other.totalswap) return false
        if (this.freeswap != other.freeswap) return false
        if (this.procs != other.procs) return false
        if (this.pad != other.pad) return false
        if (this.totalhigh != other.totalhigh) return false
        if (this.freehigh != other.freehigh) return false
        if (this.memUnit != other.memUnit) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.uptime.hashCode()
        result = 31 * result + this.loads.contentHashCode()
        result = 31 * result + this.totalram.hashCode()
        result = 31 * result + this.freeram.hashCode()
        result = 31 * result + this.sharedram.hashCode()
        result = 31 * result + this.bufferram.hashCode()
        result = 31 * result + this.totalswap.hashCode()
        result = 31 * result + this.freeswap.hashCode()
        result = 31 * result + this.procs.hashCode()
        result = 31 * result + this.pad.hashCode()
        result = 31 * result + this.totalhigh.hashCode()
        result = 31 * result + this.freehigh.hashCode()
        result = 31 * result + this.memUnit.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

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

public data class Utmpx(
    public var utType: CShort = 0,
    public var utPad1: CShort = 0,
    public var utPid: PidT = 0,
    public var utLine: UByteArray = UByteArray(32),
    public var utId: UByteArray = UByteArray(4),
    public var utUser: UByteArray = UByteArray(32),
    public var utHost: UByteArray = UByteArray(256),
    public var utExit: ExitStatus = ExitStatus(),
    public var utSession: CLong = 0L,
    public var utPad2: CInt = 0,
    public var utTv: Timeval = Timeval(),
    public var utAddrV6: UIntArray = UIntArray(4),
    public var unused: UByteArray = UByteArray(20),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utmpx) return false
        if (this.utType != other.utType) return false
        if (this.utPad1 != other.utPad1) return false
        if (this.utPid != other.utPid) return false
        if (!this.utLine.contentEquals(other.utLine)) return false
        if (!this.utId.contentEquals(other.utId)) return false
        if (!this.utUser.contentEquals(other.utUser)) return false
        if (!this.utHost.contentEquals(other.utHost)) return false
        if (this.utExit != other.utExit) return false
        if (this.utSession != other.utSession) return false
        if (this.utPad2 != other.utPad2) return false
        if (this.utTv != other.utTv) return false
        if (!this.utAddrV6.contentEquals(other.utAddrV6)) return false
        if (!this.unused.contentEquals(other.unused)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.utType.hashCode()
        result = 31 * result + this.utPad1.hashCode()
        result = 31 * result + this.utPid.hashCode()
        result = 31 * result + this.utLine.contentHashCode()
        result = 31 * result + this.utId.contentHashCode()
        result = 31 * result + this.utUser.contentHashCode()
        result = 31 * result + this.utHost.contentHashCode()
        result = 31 * result + this.utExit.hashCode()
        result = 31 * result + this.utSession.hashCode()
        result = 31 * result + this.utPad2.hashCode()
        result = 31 * result + this.utTv.hashCode()
        result = 31 * result + this.utAddrV6.contentHashCode()
        result = 31 * result + this.unused.contentHashCode()
        return result
    }
}
