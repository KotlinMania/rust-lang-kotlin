@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.core.ffi.CChar
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUchar
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.BlkcntT
import io.github.kotlinmania.rustlang.libc.BooleanT
import io.github.kotlinmania.rustlang.libc.FsidT
import io.github.kotlinmania.rustlang.libc.GidT
import io.github.kotlinmania.rustlang.libc.In6Addr
import io.github.kotlinmania.rustlang.libc.InAddrT
import io.github.kotlinmania.rustlang.libc.InPortT
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.SaFamilyT
import io.github.kotlinmania.rustlang.libc.SighandlerT
import io.github.kotlinmania.rustlang.libc.Sigval
import io.github.kotlinmania.rustlang.libc.Sockaddr
import io.github.kotlinmania.rustlang.libc.SockaddrIn6
import io.github.kotlinmania.rustlang.libc.SocklenT
import io.github.kotlinmania.rustlang.libc.Timespec
import io.github.kotlinmania.rustlang.libc.Timeval
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.UintptrT
import io.github.kotlinmania.rustlang.libc.unix.IFNAMSIZ
import io.github.kotlinmania.rustlang.libc.unix.IntptrT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.bsd.NCCS
import io.github.kotlinmania.rustlang.libc.unix.bsd.OffT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.IfData
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.Timeval32

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class Timezone(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

// Note(exhaustive): review for 1.0
public data class IpMreq(
    public var imrMultiaddr: InAddr = InAddr(),
    public var imrInterface: InAddr = InAddr(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IpMreq> =
            object : TypeLayout<IpMreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): IpMreq =
                    IpMreq(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: IpMreq) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrInterface)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreq =
                    IpMreq(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreq) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrInterface)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class IpMreqn(
    public var imrMultiaddr: InAddr = InAddr(),
    public var imrAddress: InAddr = InAddr(),
    public var imrIfindex: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IpMreqn> =
            object : TypeLayout<IpMreqn> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): IpMreqn =
                    IpMreqn(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IpMreqn) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrAddress)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.imrIfindex)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreqn =
                    IpMreqn(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreqn) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrAddress)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.imrIfindex)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class IpMreqSource(
    public var imrMultiaddr: InAddr = InAddr(),
    public var imrSourceaddr: InAddr = InAddr(),
    public var imrInterface: InAddr = InAddr(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IpMreqSource> =
            object : TypeLayout<IpMreqSource> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): IpMreqSource =
                    IpMreqSource(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (InAddr.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IpMreqSource) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrSourceaddr)
                    (InAddr.typeLayout()).writeNative(addr + 8L, value.imrInterface)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreqSource =
                    IpMreqSource(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (InAddr.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreqSource) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrSourceaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 8, value.imrInterface)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Aiocb internal constructor(
    public var aioFildes: CInt,
    public var aioOffset: OffT,
    aioBufCtorArg: MutPtr,
    public var aioNbytes: SizeT,
    public var aioReqprio: CInt,
    public var aioSigevent: Sigevent,
    public var aioLioOpcode: CInt,
) {
    public var aioBuf: MutPtr = aioBufCtorArg
        internal set

    public constructor() : this(
        aioFildes = 0,
        aioOffset = 0L,
        aioBufCtorArg = MutPtr(0L),
        aioNbytes = 0uL,
        aioReqprio = 0,
        aioSigevent = Sigevent(),
        aioLioOpcode = 0,
    )

    public operator fun component1(): CInt = this.aioFildes

    public operator fun component2(): OffT = this.aioOffset

    public operator fun component3(): MutPtr = this.aioBuf

    public operator fun component4(): SizeT = this.aioNbytes

    public operator fun component5(): CInt = this.aioReqprio

    public operator fun component6(): Sigevent = this.aioSigevent

    public operator fun component7(): CInt = this.aioLioOpcode

    public fun copy(aioFildes: CInt = this.aioFildes, aioOffset: OffT = this.aioOffset, aioBuf: MutPtr = this.aioBuf, aioNbytes: SizeT = this.aioNbytes, aioReqprio: CInt = this.aioReqprio, aioSigevent: Sigevent = this.aioSigevent, aioLioOpcode: CInt = this.aioLioOpcode): Aiocb = Aiocb(aioFildes = aioFildes, aioOffset = aioOffset, aioBufCtorArg = aioBuf, aioNbytes = aioNbytes, aioReqprio = aioReqprio, aioSigevent = aioSigevent, aioLioOpcode = aioLioOpcode)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Aiocb) return false
        if (this.aioFildes != other.aioFildes) return false
        if (this.aioOffset != other.aioOffset) return false
        if (this.aioBuf != other.aioBuf) return false
        if (this.aioNbytes != other.aioNbytes) return false
        if (this.aioReqprio != other.aioReqprio) return false
        if (this.aioSigevent != other.aioSigevent) return false
        if (this.aioLioOpcode != other.aioLioOpcode) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.aioFildes.hashCode()
        result = 31 * result + this.aioOffset.hashCode()
        result = 31 * result + this.aioBuf.hashCode()
        result = 31 * result + this.aioNbytes.hashCode()
        result = 31 * result + this.aioReqprio.hashCode()
        result = 31 * result + this.aioSigevent.hashCode()
        result = 31 * result + this.aioLioOpcode.hashCode()
        return result
    }

    public fun clone(): Aiocb = Aiocb(aioFildes, aioOffset, aioBuf, aioNbytes, aioReqprio, aioSigevent, aioLioOpcode)

    public companion object {
        public operator fun invoke(
            aioFildes: CInt = 0,
            aioOffset: OffT = 0L,
            aioBuf: MutPtr = MutPtr(0L),
            aioNbytes: SizeT = 0uL,
            aioReqprio: CInt = 0,
            aioSigevent: Sigevent = Sigevent(),
            aioLioOpcode: CInt = 0,
        ): Aiocb = Aiocb(
            aioFildes = aioFildes,
            aioOffset = aioOffset,
            aioBufCtorArg = aioBuf,
            aioNbytes = aioNbytes,
            aioReqprio = aioReqprio,
            aioSigevent = aioSigevent,
            aioLioOpcode = aioLioOpcode,
        )
    }
}

public class GlobT internal constructor(
    public var glPathc: SizeT,
    public var unused1: CInt,
    public var glOffs: SizeT,
    public var unused2: CInt,
    glPathvCtorArg: MutPtr,
    unused3CtorArg: MutPtr,
    unused4CtorArg: MutPtr,
    unused5CtorArg: MutPtr,
    unused6CtorArg: MutPtr,
    unused7CtorArg: MutPtr,
    unused8CtorArg: MutPtr,
    public var nonExhaustive: Unit,
) {
    public var glPathv: MutPtr = glPathvCtorArg
        internal set

    public var unused3: MutPtr = unused3CtorArg
        internal set

    public var unused4: MutPtr = unused4CtorArg
        internal set

    public var unused5: MutPtr = unused5CtorArg
        internal set

    public var unused6: MutPtr = unused6CtorArg
        internal set

    public var unused7: MutPtr = unused7CtorArg
        internal set

    public var unused8: MutPtr = unused8CtorArg
        internal set

    public constructor() : this(
        glPathc = 0uL,
        unused1 = 0,
        glOffs = 0uL,
        unused2 = 0,
        glPathvCtorArg = MutPtr(0L),
        unused3CtorArg = MutPtr(0L),
        unused4CtorArg = MutPtr(0L),
        unused5CtorArg = MutPtr(0L),
        unused6CtorArg = MutPtr(0L),
        unused7CtorArg = MutPtr(0L),
        unused8CtorArg = MutPtr(0L),
        nonExhaustive = Unit,
    )

    public operator fun component1(): SizeT = this.glPathc

    public operator fun component2(): CInt = this.unused1

    public operator fun component3(): SizeT = this.glOffs

    public operator fun component4(): CInt = this.unused2

    public operator fun component5(): MutPtr = this.glPathv

    public operator fun component6(): MutPtr = this.unused3

    public operator fun component7(): MutPtr = this.unused4

    public operator fun component8(): MutPtr = this.unused5

    public operator fun component9(): MutPtr = this.unused6

    public operator fun component10(): MutPtr = this.unused7

    public operator fun component11(): MutPtr = this.unused8

    public operator fun component12(): Unit = this.nonExhaustive

    public fun copy(glPathc: SizeT = this.glPathc, unused1: CInt = this.unused1, glOffs: SizeT = this.glOffs, unused2: CInt = this.unused2, glPathv: MutPtr = this.glPathv, unused3: MutPtr = this.unused3, unused4: MutPtr = this.unused4, unused5: MutPtr = this.unused5, unused6: MutPtr = this.unused6, unused7: MutPtr = this.unused7, unused8: MutPtr = this.unused8, nonExhaustive: Unit = this.nonExhaustive): GlobT = GlobT(glPathc = glPathc, unused1 = unused1, glOffs = glOffs, unused2 = unused2, glPathvCtorArg = glPathv, unused3CtorArg = unused3, unused4CtorArg = unused4, unused5CtorArg = unused5, unused6CtorArg = unused6, unused7CtorArg = unused7, unused8CtorArg = unused8, nonExhaustive = nonExhaustive)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GlobT) return false
        if (this.glPathc != other.glPathc) return false
        if (this.unused1 != other.unused1) return false
        if (this.glOffs != other.glOffs) return false
        if (this.unused2 != other.unused2) return false
        if (this.glPathv != other.glPathv) return false
        if (this.unused3 != other.unused3) return false
        if (this.unused4 != other.unused4) return false
        if (this.unused5 != other.unused5) return false
        if (this.unused6 != other.unused6) return false
        if (this.unused7 != other.unused7) return false
        if (this.unused8 != other.unused8) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.glPathc.hashCode()
        result = 31 * result + this.unused1.hashCode()
        result = 31 * result + this.glOffs.hashCode()
        result = 31 * result + this.unused2.hashCode()
        result = 31 * result + this.glPathv.hashCode()
        result = 31 * result + this.unused3.hashCode()
        result = 31 * result + this.unused4.hashCode()
        result = 31 * result + this.unused5.hashCode()
        result = 31 * result + this.unused6.hashCode()
        result = 31 * result + this.unused7.hashCode()
        result = 31 * result + this.unused8.hashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public fun clone(): GlobT = GlobT(glPathc, unused1, glOffs, unused2, glPathv, unused3, unused4, unused5, unused6, unused7, unused8, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<GlobT> =
            object : TypeLayout<GlobT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): GlobT =
                    GlobT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: GlobT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.glPathc)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.unused1)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.glOffs)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.unused2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.glPathv)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.unused3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.unused4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.unused5)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.unused6)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.unused7)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.unused8)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 88L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GlobT =
                    GlobT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GlobT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.glPathc)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.unused1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.glOffs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.unused2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.glPathv)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.unused3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.unused4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.unused5)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.unused6)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.unused7)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.unused8)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 88, value.nonExhaustive)
                }
            }

        public operator fun invoke(
            glPathc: SizeT = 0uL,
            unused1: CInt = 0,
            glOffs: SizeT = 0uL,
            unused2: CInt = 0,
            glPathv: MutPtr = MutPtr(0L),
            unused3: MutPtr = MutPtr(0L),
            unused4: MutPtr = MutPtr(0L),
            unused5: MutPtr = MutPtr(0L),
            unused6: MutPtr = MutPtr(0L),
            unused7: MutPtr = MutPtr(0L),
            unused8: MutPtr = MutPtr(0L),
            nonExhaustive: Unit = Unit,
        ): GlobT = GlobT(
            glPathc = glPathc,
            unused1 = unused1,
            glOffs = glOffs,
            unused2 = unused2,
            glPathvCtorArg = glPathv,
            unused3CtorArg = unused3,
            unused4CtorArg = unused4,
            unused5CtorArg = unused5,
            unused6CtorArg = unused6,
            unused7CtorArg = unused7,
            unused8CtorArg = unused8,
            nonExhaustive = nonExhaustive,
        )
    }
}

// Note(exhaustive): review for 1.0
public class Tm internal constructor(
    public var tmSec: CInt,
    public var tmMin: CInt,
    public var tmHour: CInt,
    public var tmMday: CInt,
    public var tmMon: CInt,
    public var tmYear: CInt,
    public var tmWday: CInt,
    public var tmYday: CInt,
    public var tmIsdst: CInt,
    public var tmGmtoff: CLong,
    tmZoneCtorArg: MutPtr,
) {
    public var tmZone: MutPtr = tmZoneCtorArg
        internal set

    public constructor() : this(
        tmSec = 0,
        tmMin = 0,
        tmHour = 0,
        tmMday = 0,
        tmMon = 0,
        tmYear = 0,
        tmWday = 0,
        tmYday = 0,
        tmIsdst = 0,
        tmGmtoff = 0L,
        tmZoneCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CInt = this.tmSec

    public operator fun component2(): CInt = this.tmMin

    public operator fun component3(): CInt = this.tmHour

    public operator fun component4(): CInt = this.tmMday

    public operator fun component5(): CInt = this.tmMon

    public operator fun component6(): CInt = this.tmYear

    public operator fun component7(): CInt = this.tmWday

    public operator fun component8(): CInt = this.tmYday

    public operator fun component9(): CInt = this.tmIsdst

    public operator fun component10(): CLong = this.tmGmtoff

    public operator fun component11(): MutPtr = this.tmZone

    public fun copy(tmSec: CInt = this.tmSec, tmMin: CInt = this.tmMin, tmHour: CInt = this.tmHour, tmMday: CInt = this.tmMday, tmMon: CInt = this.tmMon, tmYear: CInt = this.tmYear, tmWday: CInt = this.tmWday, tmYday: CInt = this.tmYday, tmIsdst: CInt = this.tmIsdst, tmGmtoff: CLong = this.tmGmtoff, tmZone: MutPtr = this.tmZone): Tm = Tm(tmSec = tmSec, tmMin = tmMin, tmHour = tmHour, tmMday = tmMday, tmMon = tmMon, tmYear = tmYear, tmWday = tmWday, tmYday = tmYday, tmIsdst = tmIsdst, tmGmtoff = tmGmtoff, tmZoneCtorArg = tmZone)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tm) return false
        if (this.tmSec != other.tmSec) return false
        if (this.tmMin != other.tmMin) return false
        if (this.tmHour != other.tmHour) return false
        if (this.tmMday != other.tmMday) return false
        if (this.tmMon != other.tmMon) return false
        if (this.tmYear != other.tmYear) return false
        if (this.tmWday != other.tmWday) return false
        if (this.tmYday != other.tmYday) return false
        if (this.tmIsdst != other.tmIsdst) return false
        if (this.tmGmtoff != other.tmGmtoff) return false
        if (this.tmZone != other.tmZone) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.tmSec.hashCode()
        result = 31 * result + this.tmMin.hashCode()
        result = 31 * result + this.tmHour.hashCode()
        result = 31 * result + this.tmMday.hashCode()
        result = 31 * result + this.tmMon.hashCode()
        result = 31 * result + this.tmYear.hashCode()
        result = 31 * result + this.tmWday.hashCode()
        result = 31 * result + this.tmYday.hashCode()
        result = 31 * result + this.tmIsdst.hashCode()
        result = 31 * result + this.tmGmtoff.hashCode()
        result = 31 * result + this.tmZone.hashCode()
        return result
    }

    public fun clone(): Tm = Tm(tmSec, tmMin, tmHour, tmMday, tmMon, tmYear, tmWday, tmYday, tmIsdst, tmGmtoff, tmZone)

    public companion object {
        public fun typeLayout(): TypeLayout<Tm> =
            object : TypeLayout<Tm> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Tm =
                    Tm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Tm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.tmSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.tmMin)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.tmHour)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.tmMday)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.tmMon)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.tmYear)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.tmWday)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.tmYday)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.tmIsdst)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.tmGmtoff)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.tmZone)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tm =
                    Tm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.tmSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.tmMin)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.tmHour)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.tmMday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.tmMon)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.tmYear)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.tmWday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.tmYday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.tmIsdst)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.tmGmtoff)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.tmZone)
                }
            }

        public operator fun invoke(
            tmSec: CInt = 0,
            tmMin: CInt = 0,
            tmHour: CInt = 0,
            tmMday: CInt = 0,
            tmMon: CInt = 0,
            tmYear: CInt = 0,
            tmWday: CInt = 0,
            tmYday: CInt = 0,
            tmIsdst: CInt = 0,
            tmGmtoff: CLong = 0L,
            tmZone: MutPtr = MutPtr(0L),
        ): Tm = Tm(
            tmSec = tmSec,
            tmMin = tmMin,
            tmHour = tmHour,
            tmMday = tmMday,
            tmMon = tmMon,
            tmYear = tmYear,
            tmWday = tmWday,
            tmYday = tmYday,
            tmIsdst = tmIsdst,
            tmGmtoff = tmGmtoff,
            tmZoneCtorArg = tmZone,
        )
    }
}

// Note(exhaustive): review for 1.0
public class Addrinfo internal constructor(
    public var aiFlags: CInt,
    public var aiFamily: CInt,
    public var aiSocktype: CInt,
    public var aiProtocol: CInt,
    public var aiAddrlen: SocklenT,
    aiCanonnameCtorArg: MutPtr,
    aiAddrCtorArg: MutPtr,
    aiNextCtorArg: MutPtr,
) {
    public var aiCanonname: MutPtr = aiCanonnameCtorArg
        internal set

    public var aiAddr: MutPtr = aiAddrCtorArg
        internal set

    public var aiNext: MutPtr = aiNextCtorArg
        internal set

    public constructor() : this(
        aiFlags = 0,
        aiFamily = 0,
        aiSocktype = 0,
        aiProtocol = 0,
        aiAddrlen = 0u,
        aiCanonnameCtorArg = MutPtr(0L),
        aiAddrCtorArg = MutPtr(0L),
        aiNextCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CInt = this.aiFlags

    public operator fun component2(): CInt = this.aiFamily

    public operator fun component3(): CInt = this.aiSocktype

    public operator fun component4(): CInt = this.aiProtocol

    public operator fun component5(): SocklenT = this.aiAddrlen

    public operator fun component6(): MutPtr = this.aiCanonname

    public operator fun component7(): MutPtr = this.aiAddr

    public operator fun component8(): MutPtr = this.aiNext

    public fun copy(aiFlags: CInt = this.aiFlags, aiFamily: CInt = this.aiFamily, aiSocktype: CInt = this.aiSocktype, aiProtocol: CInt = this.aiProtocol, aiAddrlen: SocklenT = this.aiAddrlen, aiCanonname: MutPtr = this.aiCanonname, aiAddr: MutPtr = this.aiAddr, aiNext: MutPtr = this.aiNext): Addrinfo = Addrinfo(aiFlags = aiFlags, aiFamily = aiFamily, aiSocktype = aiSocktype, aiProtocol = aiProtocol, aiAddrlen = aiAddrlen, aiCanonnameCtorArg = aiCanonname, aiAddrCtorArg = aiAddr, aiNextCtorArg = aiNext)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Addrinfo) return false
        if (this.aiFlags != other.aiFlags) return false
        if (this.aiFamily != other.aiFamily) return false
        if (this.aiSocktype != other.aiSocktype) return false
        if (this.aiProtocol != other.aiProtocol) return false
        if (this.aiAddrlen != other.aiAddrlen) return false
        if (this.aiCanonname != other.aiCanonname) return false
        if (this.aiAddr != other.aiAddr) return false
        if (this.aiNext != other.aiNext) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.aiFlags.hashCode()
        result = 31 * result + this.aiFamily.hashCode()
        result = 31 * result + this.aiSocktype.hashCode()
        result = 31 * result + this.aiProtocol.hashCode()
        result = 31 * result + this.aiAddrlen.hashCode()
        result = 31 * result + this.aiCanonname.hashCode()
        result = 31 * result + this.aiAddr.hashCode()
        result = 31 * result + this.aiNext.hashCode()
        return result
    }

    public fun clone(): Addrinfo = Addrinfo(aiFlags, aiFamily, aiSocktype, aiProtocol, aiAddrlen, aiCanonname, aiAddr, aiNext)

    public companion object {
        public fun typeLayout(): TypeLayout<Addrinfo> =
            object : TypeLayout<Addrinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Addrinfo =
                    Addrinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Addrinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.aiFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.aiFamily)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.aiSocktype)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.aiProtocol)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.aiAddrlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.aiCanonname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.aiAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.aiNext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Addrinfo =
                    Addrinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Addrinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.aiFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.aiFamily)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.aiSocktype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.aiProtocol)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.aiAddrlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.aiCanonname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.aiAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.aiNext)
                }
            }

        public operator fun invoke(
            aiFlags: CInt = 0,
            aiFamily: CInt = 0,
            aiSocktype: CInt = 0,
            aiProtocol: CInt = 0,
            aiAddrlen: SocklenT = 0u,
            aiCanonname: MutPtr = MutPtr(0L),
            aiAddr: MutPtr = MutPtr(0L),
            aiNext: MutPtr = MutPtr(0L),
        ): Addrinfo = Addrinfo(
            aiFlags = aiFlags,
            aiFamily = aiFamily,
            aiSocktype = aiSocktype,
            aiProtocol = aiProtocol,
            aiAddrlen = aiAddrlen,
            aiCanonnameCtorArg = aiCanonname,
            aiAddrCtorArg = aiAddr,
            aiNextCtorArg = aiNext,
        )
    }
}

public data class MachTimebaseInfo(
    public var numer: UInt = 0u,
    public var denom: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MachTimebaseInfo> =
            object : TypeLayout<MachTimebaseInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MachTimebaseInfo =
                    MachTimebaseInfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: MachTimebaseInfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.numer)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.denom)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MachTimebaseInfo =
                    MachTimebaseInfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MachTimebaseInfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.numer)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.denom)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Stat(
    public var stDev: DevT = 0,
    public var stMode: ModeT = 0u,
    public var stNlink: NlinkT = 0u,
    public var stIno: InoT = 0uL,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var stBirthtime: TimeT = 0L,
    public var stBirthtimeNsec: CLong = 0L,
    public var stSize: OffT = 0L,
    public var stBlocks: BlkcntT = 0L,
    public var stBlksize: BlksizeT = 0,
    public var stFlags: UInt = 0u,
    public var stGen: UInt = 0u,
    public var stLspare: Int = 0,
    public var stQspare: LongArray = LongArray(2),
) {
    public fun clone(): Stat = Stat(stDev, stMode, stNlink, stIno, stUid, stGid, stRdev, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, stBirthtime, stBirthtimeNsec, stSize, stBlocks, stBlksize, stFlags, stGen, stLspare, stQspare)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I32).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 116L),
                        (PrimitiveLayouts.U32).readNative(addr + 120L),
                        (PrimitiveLayouts.I32).readNative(addr + 124L),
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.stMode)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.stNlink)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stBirthtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stBirthtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stBlocks)
                    (PrimitiveLayouts.I32).writeNative(addr + 112L, value.stBlksize)
                    (PrimitiveLayouts.U32).writeNative(addr + 116L, value.stFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 120L, value.stGen)
                    (PrimitiveLayouts.I32).writeNative(addr + 124L, value.stLspare)
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr + 128L, value.stQspare)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 116),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 124),
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.stMode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.stNlink)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stBirthtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stBirthtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stBlocks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 112, value.stBlksize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 116, value.stFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 120, value.stGen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 124, value.stLspare)
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset + 128, value.stQspare)
                }
            }
    }
}

public class SiginfoT internal constructor(
    public var siSigno: CInt,
    public var siErrno: CInt,
    public var siCode: CInt,
    public var siPid: PidT,
    public var siUid: UidT,
    public var siStatus: CInt,
    siAddrCtorArg: MutPtr,
    // Requires it to be union for tests
    // pub siValue: sigval,
    public var pad: IntArray,
    public var nonExhaustive: Unit,
) {
    public var siAddr: MutPtr = siAddrCtorArg
        internal set

    public constructor() : this(
        siSigno = 0,
        siErrno = 0,
        siCode = 0,
        siPid = 0,
        siUid = 0,
        siStatus = 0,
        siAddrCtorArg = MutPtr(0L),
        pad = IntArray(9),
        nonExhaustive = Unit,
    )

    public operator fun component1(): CInt = this.siSigno

    public operator fun component2(): CInt = this.siErrno

    public operator fun component3(): CInt = this.siCode

    public operator fun component4(): PidT = this.siPid

    public operator fun component5(): UidT = this.siUid

    public operator fun component6(): CInt = this.siStatus

    public operator fun component7(): MutPtr = this.siAddr

    public operator fun component8(): IntArray = this.pad

    public operator fun component9(): Unit = this.nonExhaustive

    public fun copy(siSigno: CInt = this.siSigno, siErrno: CInt = this.siErrno, siCode: CInt = this.siCode, siPid: PidT = this.siPid, siUid: UidT = this.siUid, siStatus: CInt = this.siStatus, siAddr: MutPtr = this.siAddr, pad: IntArray = this.pad, nonExhaustive: Unit = this.nonExhaustive): SiginfoT = SiginfoT(siSigno = siSigno, siErrno = siErrno, siCode = siCode, siPid = siPid, siUid = siUid, siStatus = siStatus, siAddrCtorArg = siAddr, pad = pad, nonExhaustive = nonExhaustive)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SiginfoT) return false
        if (this.siSigno != other.siSigno) return false
        if (this.siErrno != other.siErrno) return false
        if (this.siCode != other.siCode) return false
        if (this.siPid != other.siPid) return false
        if (this.siUid != other.siUid) return false
        if (this.siStatus != other.siStatus) return false
        if (this.siAddr != other.siAddr) return false
        if (!this.pad.contentEquals(other.pad)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siSigno.hashCode()
        result = 31 * result + this.siErrno.hashCode()
        result = 31 * result + this.siCode.hashCode()
        result = 31 * result + this.siPid.hashCode()
        result = 31 * result + this.siUid.hashCode()
        result = 31 * result + this.siStatus.hashCode()
        result = 31 * result + this.siAddr.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public fun clone(): SiginfoT = SiginfoT(siSigno, siErrno, siCode, siPid, siUid, siStatus, siAddr, pad, nonExhaustive)

    public fun siValue(): Sigval {
        data class SiginfoTimer(
            public var siSigno: CInt = 0,
            public var siErrno: CInt = 0,
            public var siCode: CInt = 0,
            public var siPid: PidT = 0,
            public var siUid: UidT = 0,
            public var siStatus: CInt = 0,
            public var siAddr: MutPtr = MutPtr(0L),
            public var siValue: Sigval = Sigval(),
        )
        return (ObjectSlotLayout<SiginfoTimer>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).siValue
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SiginfoT> =
            object : TypeLayout<SiginfoT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 104L
                override fun readNative(addr: Long): SiginfoT =
                    SiginfoT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.usize64Array(9)).readNative(addr + 32L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 104L),
                    )
                override fun writeNative(addr: Long, value: SiginfoT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siSigno)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siErrno)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.siCode)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.siPid)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.siUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.siStatus)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.siAddr)
                    (PrimitiveArrayLayouts.usize64Array(9)).writeNative(addr + 32L, value.pad)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 104L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SiginfoT =
                    SiginfoT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.usize64Array(9)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 104),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SiginfoT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siSigno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siErrno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.siCode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.siPid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.siUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.siStatus)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.siAddr)
                    (PrimitiveArrayLayouts.usize64Array(9)).writeBytes(buf, offset + 32, value.pad)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 104, value.nonExhaustive)
                }
            }

        public operator fun invoke(
            siSigno: CInt = 0,
            siErrno: CInt = 0,
            siCode: CInt = 0,
            siPid: PidT = 0,
            siUid: UidT = 0,
            siStatus: CInt = 0,
            siAddr: MutPtr = MutPtr(0L),
            pad: IntArray = IntArray(9),
            nonExhaustive: Unit = Unit,
        ): SiginfoT = SiginfoT(
            siSigno = siSigno,
            siErrno = siErrno,
            siCode = siCode,
            siPid = siPid,
            siUid = siUid,
            siStatus = siStatus,
            siAddrCtorArg = siAddr,
            pad = pad,
            nonExhaustive = nonExhaustive,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class Sigaction(
    // Note(union): this field is actually a union
    public var saSigaction: SighandlerT = 0uL,
    public var saMask: SigsetT = 0u,
    public var saFlags: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Sigaction> =
            object : TypeLayout<Sigaction> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Sigaction =
                    Sigaction(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Sigaction) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.saSigaction)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.saMask)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.saFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sigaction =
                    Sigaction(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sigaction) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.saSigaction)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.saMask)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.saFlags)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssSize: SizeT,
    public var ssFlags: CInt,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public constructor() : this(
        ssSpCtorArg = MutPtr(0L),
        ssSize = 0uL,
        ssFlags = 0,
    )

    public operator fun component1(): MutPtr = this.ssSp

    public operator fun component2(): SizeT = this.ssSize

    public operator fun component3(): CInt = this.ssFlags

    public fun copy(ssSp: MutPtr = this.ssSp, ssSize: SizeT = this.ssSize, ssFlags: CInt = this.ssFlags): StackT = StackT(ssSpCtorArg = ssSp, ssSize = ssSize, ssFlags = ssFlags)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StackT) return false
        if (this.ssSp != other.ssSp) return false
        if (this.ssSize != other.ssSize) return false
        if (this.ssFlags != other.ssFlags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ssSp.hashCode()
        result = 31 * result + this.ssSize.hashCode()
        result = 31 * result + this.ssFlags.hashCode()
        return result
    }

    public fun clone(): StackT = StackT(ssSp, ssSize, ssFlags)

    public companion object {
        public fun typeLayout(): TypeLayout<StackT> =
            object : TypeLayout<StackT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): StackT =
                    StackT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: StackT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ssSp)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ssSize)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.ssFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StackT =
                    StackT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StackT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ssSp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ssSize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.ssFlags)
                }
            }

        public operator fun invoke(
            ssSp: MutPtr = MutPtr(0L),
            ssSize: SizeT = 0uL,
            ssFlags: CInt = 0,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssSize = ssSize,
            ssFlags = ssFlags,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class FstoreT(
    public var fstFlags: CUint = 0u,
    public var fstPosmode: CInt = 0,
    public var fstOffset: OffT = 0L,
    public var fstLength: OffT = 0L,
    public var fstBytesalloc: OffT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FstoreT> =
            object : TypeLayout<FstoreT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): FstoreT =
                    FstoreT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: FstoreT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.fstFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.fstPosmode)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.fstOffset)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.fstLength)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.fstBytesalloc)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FstoreT =
                    FstoreT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FstoreT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.fstFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.fstPosmode)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.fstOffset)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.fstLength)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.fstBytesalloc)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class FpunchholeT(
    public var fpFlags: CUint = 0u,
    // unused
    public var reserved: CUint = 0u,
    // (to maintain 8-byte alignment)
    public var fpOffset: OffT = 0L,
    // IN: start of the region
    public var fpLength: OffT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FpunchholeT> =
            object : TypeLayout<FpunchholeT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FpunchholeT =
                    FpunchholeT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: FpunchholeT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.fpFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.reserved)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.fpOffset)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.fpLength)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FpunchholeT =
                    FpunchholeT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FpunchholeT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.fpFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.reserved)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.fpOffset)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.fpLength)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class FtrimactivefileT(
    public var ftaOffset: OffT = 0L,
    public var ftaLength: OffT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FtrimactivefileT> =
            object : TypeLayout<FtrimactivefileT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): FtrimactivefileT =
                    FtrimactivefileT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: FtrimactivefileT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.ftaOffset)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.ftaLength)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FtrimactivefileT =
                    FtrimactivefileT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FtrimactivefileT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.ftaOffset)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.ftaLength)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class FspecreadT(
    public var fsrFlags: CUint = 0u,
    public var reserved: CUint = 0u,
    public var fsrOffset: OffT = 0L,
    public var fsrLength: OffT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FspecreadT> =
            object : TypeLayout<FspecreadT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FspecreadT =
                    FspecreadT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: FspecreadT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.fsrFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.reserved)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.fsrOffset)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.fsrLength)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FspecreadT =
                    FspecreadT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FspecreadT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.fsrFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.reserved)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.fsrOffset)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.fsrLength)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Radvisory(
    public var raOffset: OffT = 0L,
    public var raCount: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Radvisory> =
            object : TypeLayout<Radvisory> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Radvisory =
                    Radvisory(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Radvisory) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.raOffset)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.raCount)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Radvisory =
                    Radvisory(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Radvisory) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.raOffset)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.raCount)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Statvfs(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: io.github.kotlinmania.rustlang.libc.FsblkcntT = 0u,
    public var fBfree: io.github.kotlinmania.rustlang.libc.FsblkcntT = 0u,
    public var fBavail: io.github.kotlinmania.rustlang.libc.FsblkcntT = 0u,
    public var fFiles: io.github.kotlinmania.rustlang.libc.FsfilcntT = 0u,
    public var fFfree: io.github.kotlinmania.rustlang.libc.FsfilcntT = 0u,
    public var fFavail: io.github.kotlinmania.rustlang.libc.FsfilcntT = 0u,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs> =
            object : TypeLayout<Statvfs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fBsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.fFrsize)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.fBlocks)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.fBfree)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.fBavail)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.fFiles)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.fFfree)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.fFavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fFsid)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fFlag)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.fNamemax)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fBsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.fFrsize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.fBlocks)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.fBfree)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.fBavail)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.fFiles)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.fFfree)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.fFavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fFsid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fFlag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.fNamemax)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class DlInfo internal constructor(
    dliFnameCtorArg: ConstPtr,
    dliFbaseCtorArg: MutPtr,
    dliSnameCtorArg: ConstPtr,
    dliSaddrCtorArg: MutPtr,
) {
    public var dliFname: ConstPtr = dliFnameCtorArg
        internal set

    public var dliFbase: MutPtr = dliFbaseCtorArg
        internal set

    public var dliSname: ConstPtr = dliSnameCtorArg
        internal set

    public var dliSaddr: MutPtr = dliSaddrCtorArg
        internal set

    public constructor() : this(
        dliFnameCtorArg = ConstPtr(0L),
        dliFbaseCtorArg = MutPtr(0L),
        dliSnameCtorArg = ConstPtr(0L),
        dliSaddrCtorArg = MutPtr(0L),
    )

    public operator fun component1(): ConstPtr = this.dliFname

    public operator fun component2(): MutPtr = this.dliFbase

    public operator fun component3(): ConstPtr = this.dliSname

    public operator fun component4(): MutPtr = this.dliSaddr

    public fun copy(dliFname: ConstPtr = this.dliFname, dliFbase: MutPtr = this.dliFbase, dliSname: ConstPtr = this.dliSname, dliSaddr: MutPtr = this.dliSaddr): DlInfo = DlInfo(dliFnameCtorArg = dliFname, dliFbaseCtorArg = dliFbase, dliSnameCtorArg = dliSname, dliSaddrCtorArg = dliSaddr)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DlInfo) return false
        if (this.dliFname != other.dliFname) return false
        if (this.dliFbase != other.dliFbase) return false
        if (this.dliSname != other.dliSname) return false
        if (this.dliSaddr != other.dliSaddr) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dliFname.hashCode()
        result = 31 * result + this.dliFbase.hashCode()
        result = 31 * result + this.dliSname.hashCode()
        result = 31 * result + this.dliSaddr.hashCode()
        return result
    }

    public fun clone(): DlInfo = DlInfo(dliFname, dliFbase, dliSname, dliSaddr)

    public companion object {
        public fun typeLayout(): TypeLayout<DlInfo> =
            object : TypeLayout<DlInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): DlInfo =
                    DlInfo(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: DlInfo) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.dliFname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.dliFbase)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.dliSname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.dliSaddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DlInfo =
                    DlInfo(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DlInfo) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.dliFname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.dliFbase)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.dliSname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.dliSaddr)
                }
            }

        public operator fun invoke(
            dliFname: ConstPtr = ConstPtr(0L),
            dliFbase: MutPtr = MutPtr(0L),
            dliSname: ConstPtr = ConstPtr(0L),
            dliSaddr: MutPtr = MutPtr(0L),
        ): DlInfo = DlInfo(
            dliFnameCtorArg = dliFname,
            dliFbaseCtorArg = dliFbase,
            dliSnameCtorArg = dliSname,
            dliSaddrCtorArg = dliSaddr,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class SockaddrIn(
    public var sinLen: UByte = 0u,
    public var sinFamily: SaFamilyT = 0u,
    public var sinPort: InPortT = 0u,
    public var sinAddr: io.github.kotlinmania.rustlang.libc.InAddr = io.github.kotlinmania.rustlang.libc.InAddr(),
    public var sinZero: UByteArray = UByteArray(8),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrIn) return false
        if (this.sinLen != other.sinLen) return false
        if (this.sinFamily != other.sinFamily) return false
        if (this.sinPort != other.sinPort) return false
        if (this.sinAddr != other.sinAddr) return false
        if (!this.sinZero.contentEquals(other.sinZero)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sinLen.hashCode()
        result = 31 * result + this.sinFamily.hashCode()
        result = 31 * result + this.sinPort.hashCode()
        result = 31 * result + this.sinAddr.hashCode()
        result = 31 * result + this.sinZero.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrIn> =
            object : TypeLayout<SockaddrIn> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrIn =
                    SockaddrIn(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrIn) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.sinLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.sinFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sinPort)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.sinAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.sinZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrIn =
                    SockaddrIn(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrIn) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.sinLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.sinFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sinPort)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.sinAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.sinZero)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Kevent64S(
    public var ident: ULong = 0uL,
    public var filter: Short = 0,
    public var flags: UShort = 0u,
    public var fflags: UInt = 0u,
    public var data: Long = 0L,
    public var udata: ULong = 0uL,
    public var ext: ULongArray = ULongArray(2),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Kevent64S) return false
        if (this.ident != other.ident) return false
        if (this.filter != other.filter) return false
        if (this.flags != other.flags) return false
        if (this.fflags != other.fflags) return false
        if (this.data != other.data) return false
        if (this.udata != other.udata) return false
        if (!this.ext.contentEquals(other.ext)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ident.hashCode()
        result = 31 * result + this.filter.hashCode()
        result = 31 * result + this.flags.hashCode()
        result = 31 * result + this.fflags.hashCode()
        result = 31 * result + this.data.hashCode()
        result = 31 * result + this.udata.hashCode()
        result = 31 * result + this.ext.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Kevent64S> =
            object : TypeLayout<Kevent64S> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Kevent64S =
                    Kevent64S(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 10L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Kevent64S) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.ident)
                    (PrimitiveLayouts.I16).writeNative(addr + 8L, value.filter)
                    (PrimitiveLayouts.U16).writeNative(addr + 10L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.fflags)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.data)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.udata)
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr + 32L, value.ext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Kevent64S =
                    Kevent64S(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Kevent64S) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.ident)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 8, value.filter)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 10, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.fflags)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.data)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.udata)
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset + 32, value.ext)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Dqblk(
    public var dqbBhardlimit: ULong = 0uL,
    public var dqbBsoftlimit: ULong = 0uL,
    public var dqbCurbytes: ULong = 0uL,
    public var dqbIhardlimit: UInt = 0u,
    public var dqbIsoftlimit: UInt = 0u,
    public var dqbCurinodes: UInt = 0u,
    public var dqbBtime: UInt = 0u,
    public var dqbItime: UInt = 0u,
    public var dqbId: UInt = 0u,
    public var dqbSpare: UIntArray = UIntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dqblk) return false
        if (this.dqbBhardlimit != other.dqbBhardlimit) return false
        if (this.dqbBsoftlimit != other.dqbBsoftlimit) return false
        if (this.dqbCurbytes != other.dqbCurbytes) return false
        if (this.dqbIhardlimit != other.dqbIhardlimit) return false
        if (this.dqbIsoftlimit != other.dqbIsoftlimit) return false
        if (this.dqbCurinodes != other.dqbCurinodes) return false
        if (this.dqbBtime != other.dqbBtime) return false
        if (this.dqbItime != other.dqbItime) return false
        if (this.dqbId != other.dqbId) return false
        if (!this.dqbSpare.contentEquals(other.dqbSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dqbBhardlimit.hashCode()
        result = 31 * result + this.dqbBsoftlimit.hashCode()
        result = 31 * result + this.dqbCurbytes.hashCode()
        result = 31 * result + this.dqbIhardlimit.hashCode()
        result = 31 * result + this.dqbIsoftlimit.hashCode()
        result = 31 * result + this.dqbCurinodes.hashCode()
        result = 31 * result + this.dqbBtime.hashCode()
        result = 31 * result + this.dqbItime.hashCode()
        result = 31 * result + this.dqbId.hashCode()
        result = 31 * result + this.dqbSpare.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Dqblk> =
            object : TypeLayout<Dqblk> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Dqblk =
                    Dqblk(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Dqblk) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dqbBhardlimit)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.dqbBsoftlimit)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.dqbCurbytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.dqbIhardlimit)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.dqbIsoftlimit)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.dqbCurinodes)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.dqbBtime)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.dqbItime)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.dqbId)
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr + 48L, value.dqbSpare)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dqblk =
                    Dqblk(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dqblk) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dqbBhardlimit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.dqbBsoftlimit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.dqbCurbytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.dqbIhardlimit)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.dqbIsoftlimit)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.dqbCurinodes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.dqbBtime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.dqbItime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.dqbId)
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset + 48, value.dqbSpare)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class IfMsghdr(
    public var ifmMsglen: CUshort = 0u,
    public var ifmVersion: CUchar = 0u,
    public var ifmType: CUchar = 0u,
    public var ifmAddrs: CInt = 0,
    public var ifmFlags: CInt = 0,
    public var ifmIndex: CUshort = 0u,
    public var ifmData: IfData = IfData(),
)

// Note(exhaustive): review for 1.0
public data class IfaMsghdr(
    public var ifamMsglen: CUshort = 0u,
    public var ifamVersion: CUchar = 0u,
    public var ifamType: CUchar = 0u,
    public var ifamAddrs: CInt = 0,
    public var ifamFlags: CInt = 0,
    public var ifamIndex: CUshort = 0u,
    public var ifamMetric: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IfaMsghdr> =
            object : TypeLayout<IfaMsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): IfaMsghdr =
                    IfaMsghdr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: IfaMsghdr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.ifamMsglen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.ifamVersion)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.ifamType)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ifamAddrs)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ifamFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.ifamIndex)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.ifamMetric)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IfaMsghdr =
                    IfaMsghdr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IfaMsghdr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.ifamMsglen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.ifamVersion)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.ifamType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ifamAddrs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ifamFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.ifamIndex)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.ifamMetric)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class IfmaMsghdr(
    public var ifmamMsglen: CUshort = 0u,
    public var ifmamVersion: CUchar = 0u,
    public var ifmamType: CUchar = 0u,
    public var ifmamAddrs: CInt = 0,
    public var ifmamFlags: CInt = 0,
    public var ifmamIndex: CUshort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IfmaMsghdr> =
            object : TypeLayout<IfmaMsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): IfmaMsghdr =
                    IfmaMsghdr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: IfmaMsghdr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.ifmamMsglen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.ifmamVersion)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.ifmamType)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ifmamAddrs)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ifmamFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.ifmamIndex)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IfmaMsghdr =
                    IfmaMsghdr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IfmaMsghdr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.ifmamMsglen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.ifmamVersion)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.ifmamType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ifmamAddrs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ifmamFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.ifmamIndex)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class IfmaMsghdr2(
    public var ifmamMsglen: CUshort = 0u,
    public var ifmamVersion: CUchar = 0u,
    public var ifmamType: CUchar = 0u,
    public var ifmamAddrs: CInt = 0,
    public var ifmamFlags: CInt = 0,
    public var ifmamIndex: CUshort = 0u,
    public var ifmamRefcount: Int = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IfmaMsghdr2> =
            object : TypeLayout<IfmaMsghdr2> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): IfmaMsghdr2 =
                    IfmaMsghdr2(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: IfmaMsghdr2) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.ifmamMsglen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.ifmamVersion)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.ifmamType)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ifmamAddrs)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ifmamFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.ifmamIndex)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.ifmamRefcount)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IfmaMsghdr2 =
                    IfmaMsghdr2(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IfmaMsghdr2) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.ifmamMsglen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.ifmamVersion)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.ifmamType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ifmamAddrs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ifmamFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.ifmamIndex)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.ifmamRefcount)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class RtMetrics(
    public var rmxLocks: UInt = 0u,
    public var rmxMtu: UInt = 0u,
    public var rmxHopcount: UInt = 0u,
    public var rmxExpire: Int = 0,
    public var rmxRecvpipe: UInt = 0u,
    public var rmxSendpipe: UInt = 0u,
    public var rmxSsthresh: UInt = 0u,
    public var rmxRtt: UInt = 0u,
    public var rmxRttvar: UInt = 0u,
    public var rmxPksent: UInt = 0u,
    /**
     * This field does not exist anymore, the u32 is now part of a resized
     * `rmxFiller` array.
     */
    public var rmxState: UInt = 0u,
    public var rmxFiller: UIntArray = UIntArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RtMetrics) return false
        if (this.rmxLocks != other.rmxLocks) return false
        if (this.rmxMtu != other.rmxMtu) return false
        if (this.rmxHopcount != other.rmxHopcount) return false
        if (this.rmxExpire != other.rmxExpire) return false
        if (this.rmxRecvpipe != other.rmxRecvpipe) return false
        if (this.rmxSendpipe != other.rmxSendpipe) return false
        if (this.rmxSsthresh != other.rmxSsthresh) return false
        if (this.rmxRtt != other.rmxRtt) return false
        if (this.rmxRttvar != other.rmxRttvar) return false
        if (this.rmxPksent != other.rmxPksent) return false
        if (this.rmxState != other.rmxState) return false
        if (!this.rmxFiller.contentEquals(other.rmxFiller)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.rmxLocks.hashCode()
        result = 31 * result + this.rmxMtu.hashCode()
        result = 31 * result + this.rmxHopcount.hashCode()
        result = 31 * result + this.rmxExpire.hashCode()
        result = 31 * result + this.rmxRecvpipe.hashCode()
        result = 31 * result + this.rmxSendpipe.hashCode()
        result = 31 * result + this.rmxSsthresh.hashCode()
        result = 31 * result + this.rmxRtt.hashCode()
        result = 31 * result + this.rmxRttvar.hashCode()
        result = 31 * result + this.rmxPksent.hashCode()
        result = 31 * result + this.rmxState.hashCode()
        result = 31 * result + this.rmxFiller.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RtMetrics> =
            object : TypeLayout<RtMetrics> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): RtMetrics =
                    RtMetrics(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveArrayLayouts.uIntArray(3)).readNative(addr + 44L),
                    )
                override fun writeNative(addr: Long, value: RtMetrics) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.rmxLocks)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.rmxMtu)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.rmxHopcount)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.rmxExpire)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.rmxRecvpipe)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.rmxSendpipe)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.rmxSsthresh)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.rmxRtt)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.rmxRttvar)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.rmxPksent)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.rmxState)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeNative(addr + 44L, value.rmxFiller)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RtMetrics =
                    RtMetrics(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveArrayLayouts.uIntArray(3)).readBytes(buf, offset + 44),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RtMetrics) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.rmxLocks)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.rmxMtu)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.rmxHopcount)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.rmxExpire)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.rmxRecvpipe)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.rmxSendpipe)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.rmxSsthresh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.rmxRtt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.rmxRttvar)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.rmxPksent)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.rmxState)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeBytes(buf, offset + 44, value.rmxFiller)
                }
            }
    }
}

public data class RtMsghdr(
    public var rtmMsglen: CUshort = 0u,
    public var rtmVersion: CUchar = 0u,
    public var rtmType: CUchar = 0u,
    public var rtmIndex: CUshort = 0u,
    public var rtmFlags: CInt = 0,
    public var rtmAddrs: CInt = 0,
    public var rtmPid: PidT = 0,
    public var rtmSeq: CInt = 0,
    public var rtmErrno: CInt = 0,
    public var rtmUse: CInt = 0,
    public var rtmInits: UInt = 0u,
    public var rtmRmx: RtMetrics = RtMetrics(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<RtMsghdr> =
            object : TypeLayout<RtMsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 92L
                override fun readNative(addr: Long): RtMsghdr =
                    RtMsghdr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (RtMetrics.typeLayout()).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: RtMsghdr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.rtmMsglen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.rtmVersion)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.rtmType)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.rtmIndex)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.rtmFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.rtmAddrs)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.rtmPid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.rtmSeq)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.rtmErrno)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.rtmUse)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.rtmInits)
                    (RtMetrics.typeLayout()).writeNative(addr + 36L, value.rtmRmx)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RtMsghdr =
                    RtMsghdr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (RtMetrics.typeLayout()).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RtMsghdr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.rtmMsglen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.rtmVersion)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.rtmType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.rtmIndex)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.rtmFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.rtmAddrs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.rtmPid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.rtmSeq)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.rtmErrno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.rtmUse)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.rtmInits)
                    (RtMetrics.typeLayout()).writeBytes(buf, offset + 36, value.rtmRmx)
                }
            }
    }
}

public data class RtMsghdr2(
    public var rtmMsglen: CUshort = 0u,
    public var rtmVersion: CUchar = 0u,
    public var rtmType: CUchar = 0u,
    public var rtmIndex: CUshort = 0u,
    public var rtmFlags: CInt = 0,
    public var rtmAddrs: CInt = 0,
    public var rtmRefcnt: Int = 0,
    public var rtmParentflags: CInt = 0,
    public var rtmReserved: CInt = 0,
    public var rtmUse: CInt = 0,
    public var rtmInits: UInt = 0u,
    public var rtmRmx: RtMetrics = RtMetrics(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<RtMsghdr2> =
            object : TypeLayout<RtMsghdr2> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 92L
                override fun readNative(addr: Long): RtMsghdr2 =
                    RtMsghdr2(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (RtMetrics.typeLayout()).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: RtMsghdr2) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.rtmMsglen)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.rtmVersion)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.rtmType)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.rtmIndex)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.rtmFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.rtmAddrs)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.rtmRefcnt)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.rtmParentflags)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.rtmReserved)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.rtmUse)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.rtmInits)
                    (RtMetrics.typeLayout()).writeNative(addr + 36L, value.rtmRmx)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RtMsghdr2 =
                    RtMsghdr2(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (RtMetrics.typeLayout()).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RtMsghdr2) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.rtmMsglen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.rtmVersion)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.rtmType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.rtmIndex)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.rtmFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.rtmAddrs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.rtmRefcnt)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.rtmParentflags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.rtmReserved)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.rtmUse)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.rtmInits)
                    (RtMetrics.typeLayout()).writeBytes(buf, offset + 36, value.rtmRmx)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Termios(
    public var cIflag: io.github.kotlinmania.rustlang.libc.TcflagT = 0uL,
    public var cOflag: io.github.kotlinmania.rustlang.libc.TcflagT = 0uL,
    public var cCflag: io.github.kotlinmania.rustlang.libc.TcflagT = 0uL,
    public var cLflag: io.github.kotlinmania.rustlang.libc.TcflagT = 0uL,
    public var cCc: UByteArray = UByteArray(NCCS),
    public var cIspeed: io.github.kotlinmania.rustlang.libc.SpeedT = 0uL,
    public var cOspeed: io.github.kotlinmania.rustlang.libc.SpeedT = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Termios) return false
        if (this.cIflag != other.cIflag) return false
        if (this.cOflag != other.cOflag) return false
        if (this.cCflag != other.cCflag) return false
        if (this.cLflag != other.cLflag) return false
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
        result = 31 * result + this.cCc.contentHashCode()
        result = 31 * result + this.cIspeed.hashCode()
        result = 31 * result + this.cOspeed.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Termios> =
            object : TypeLayout<Termios> {
                override val alignBytes: Long = 8L
                private val off5: Long = alignUpBytes((32L + ((NCCS).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                private val off6: Long = alignUpBytes((off5 + 8L), 8L)
                override val sizeBytes: Long = alignUpBytes((off6 + 8L), 8L)
                override fun readNative(addr: Long): Termios =
                    Termios(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + off5),
                        (PrimitiveLayouts.U64).readNative(addr + off6),
                    )
                override fun writeNative(addr: Long, value: Termios) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.cOflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.cCflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.cLflag)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeNative(addr + 32L, value.cCc)
                    (PrimitiveLayouts.U64).writeNative(addr + off5, value.cIspeed)
                    (PrimitiveLayouts.U64).writeNative(addr + off6, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios =
                    Termios(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off5.toInt()),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off6.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.cOflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.cCflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.cLflag)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeBytes(buf, offset + 32, value.cCc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off5.toInt(), value.cIspeed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off6.toInt(), value.cOspeed)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Flock(
    public var lStart: OffT = 0L,
    public var lLen: OffT = 0L,
    public var lPid: PidT = 0,
    public var lType: CShort = 0,
    public var lWhence: CShort = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Flock> =
            object : TypeLayout<Flock> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Flock =
                    Flock(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I16).readNative(addr + 20L),
                        (PrimitiveLayouts.I16).readNative(addr + 22L),
                    )
                override fun writeNative(addr: Long, value: Flock) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.lStart)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.lLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.lPid)
                    (PrimitiveLayouts.I16).writeNative(addr + 20L, value.lType)
                    (PrimitiveLayouts.I16).writeNative(addr + 22L, value.lWhence)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Flock =
                    Flock(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 22),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Flock) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.lStart)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.lLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.lPid)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 20, value.lType)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 22, value.lWhence)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class SfHdtr internal constructor(
    headersCtorArg: MutPtr,
    public var hdrCnt: CInt,
    trailersCtorArg: MutPtr,
    public var trlCnt: CInt,
) {
    public var headers: MutPtr = headersCtorArg
        internal set

    public var trailers: MutPtr = trailersCtorArg
        internal set

    public constructor() : this(
        headersCtorArg = MutPtr(0L),
        hdrCnt = 0,
        trailersCtorArg = MutPtr(0L),
        trlCnt = 0,
    )

    public operator fun component1(): MutPtr = this.headers

    public operator fun component2(): CInt = this.hdrCnt

    public operator fun component3(): MutPtr = this.trailers

    public operator fun component4(): CInt = this.trlCnt

    public fun copy(headers: MutPtr = this.headers, hdrCnt: CInt = this.hdrCnt, trailers: MutPtr = this.trailers, trlCnt: CInt = this.trlCnt): SfHdtr = SfHdtr(headersCtorArg = headers, hdrCnt = hdrCnt, trailersCtorArg = trailers, trlCnt = trlCnt)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SfHdtr) return false
        if (this.headers != other.headers) return false
        if (this.hdrCnt != other.hdrCnt) return false
        if (this.trailers != other.trailers) return false
        if (this.trlCnt != other.trlCnt) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.headers.hashCode()
        result = 31 * result + this.hdrCnt.hashCode()
        result = 31 * result + this.trailers.hashCode()
        result = 31 * result + this.trlCnt.hashCode()
        return result
    }

    public fun clone(): SfHdtr = SfHdtr(headers, hdrCnt, trailers, trlCnt)

    public companion object {
        public fun typeLayout(): TypeLayout<SfHdtr> =
            object : TypeLayout<SfHdtr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SfHdtr =
                    SfHdtr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SfHdtr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.headers)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.hdrCnt)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.trailers)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.trlCnt)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SfHdtr =
                    SfHdtr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SfHdtr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.headers)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.hdrCnt)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.trailers)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.trlCnt)
                }
            }

        public operator fun invoke(
            headers: MutPtr = MutPtr(0L),
            hdrCnt: CInt = 0,
            trailers: MutPtr = MutPtr(0L),
            trlCnt: CInt = 0,
        ): SfHdtr = SfHdtr(
            headersCtorArg = headers,
            hdrCnt = hdrCnt,
            trailersCtorArg = trailers,
            trlCnt = trlCnt,
        )
    }
}

// Note(exhaustive): review for 1.0
public class Lconv internal constructor(
    decimalPointCtorArg: MutPtr,
    thousandsSepCtorArg: MutPtr,
    groupingCtorArg: MutPtr,
    intCurrSymbolCtorArg: MutPtr,
    currencySymbolCtorArg: MutPtr,
    monDecimalPointCtorArg: MutPtr,
    monThousandsSepCtorArg: MutPtr,
    monGroupingCtorArg: MutPtr,
    positiveSignCtorArg: MutPtr,
    negativeSignCtorArg: MutPtr,
    public var intFracDigits: CChar,
    public var fracDigits: CChar,
    public var pCsPrecedes: CChar,
    public var pSepBySpace: CChar,
    public var nCsPrecedes: CChar,
    public var nSepBySpace: CChar,
    public var pSignPosn: CChar,
    public var nSignPosn: CChar,
    public var intPCsPrecedes: CChar,
    public var intNCsPrecedes: CChar,
    public var intPSepBySpace: CChar,
    public var intNSepBySpace: CChar,
    public var intPSignPosn: CChar,
    public var intNSignPosn: CChar,
) {
    public var decimalPoint: MutPtr = decimalPointCtorArg
        internal set

    public var thousandsSep: MutPtr = thousandsSepCtorArg
        internal set

    public var grouping: MutPtr = groupingCtorArg
        internal set

    public var intCurrSymbol: MutPtr = intCurrSymbolCtorArg
        internal set

    public var currencySymbol: MutPtr = currencySymbolCtorArg
        internal set

    public var monDecimalPoint: MutPtr = monDecimalPointCtorArg
        internal set

    public var monThousandsSep: MutPtr = monThousandsSepCtorArg
        internal set

    public var monGrouping: MutPtr = monGroupingCtorArg
        internal set

    public var positiveSign: MutPtr = positiveSignCtorArg
        internal set

    public var negativeSign: MutPtr = negativeSignCtorArg
        internal set

    public constructor() : this(
        decimalPointCtorArg = MutPtr(0L),
        thousandsSepCtorArg = MutPtr(0L),
        groupingCtorArg = MutPtr(0L),
        intCurrSymbolCtorArg = MutPtr(0L),
        currencySymbolCtorArg = MutPtr(0L),
        monDecimalPointCtorArg = MutPtr(0L),
        monThousandsSepCtorArg = MutPtr(0L),
        monGroupingCtorArg = MutPtr(0L),
        positiveSignCtorArg = MutPtr(0L),
        negativeSignCtorArg = MutPtr(0L),
        intFracDigits = 0u,
        fracDigits = 0u,
        pCsPrecedes = 0u,
        pSepBySpace = 0u,
        nCsPrecedes = 0u,
        nSepBySpace = 0u,
        pSignPosn = 0u,
        nSignPosn = 0u,
        intPCsPrecedes = 0u,
        intNCsPrecedes = 0u,
        intPSepBySpace = 0u,
        intNSepBySpace = 0u,
        intPSignPosn = 0u,
        intNSignPosn = 0u,
    )

    public fun clone(): Lconv = Lconv(decimalPoint, thousandsSep, grouping, intCurrSymbol, currencySymbol, monDecimalPoint, monThousandsSep, monGrouping, positiveSign, negativeSign, intFracDigits, fracDigits, pCsPrecedes, pSepBySpace, nCsPrecedes, nSepBySpace, pSignPosn, nSignPosn, intPCsPrecedes, intNCsPrecedes, intPSepBySpace, intNSepBySpace, intPSignPosn, intNSignPosn)

    public companion object {
        public fun typeLayout(): TypeLayout<Lconv> =
            object : TypeLayout<Lconv> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): Lconv =
                    Lconv(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.U8).readNative(addr + 80L),
                        (PrimitiveLayouts.U8).readNative(addr + 81L),
                        (PrimitiveLayouts.U8).readNative(addr + 82L),
                        (PrimitiveLayouts.U8).readNative(addr + 83L),
                        (PrimitiveLayouts.U8).readNative(addr + 84L),
                        (PrimitiveLayouts.U8).readNative(addr + 85L),
                        (PrimitiveLayouts.U8).readNative(addr + 86L),
                        (PrimitiveLayouts.U8).readNative(addr + 87L),
                        (PrimitiveLayouts.U8).readNative(addr + 88L),
                        (PrimitiveLayouts.U8).readNative(addr + 89L),
                        (PrimitiveLayouts.U8).readNative(addr + 90L),
                        (PrimitiveLayouts.U8).readNative(addr + 91L),
                        (PrimitiveLayouts.U8).readNative(addr + 92L),
                        (PrimitiveLayouts.U8).readNative(addr + 93L),
                    )
                override fun writeNative(addr: Long, value: Lconv) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.decimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.thousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.grouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.intCurrSymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.currencySymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.monDecimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.monThousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.monGrouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.positiveSign)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.negativeSign)
                    (PrimitiveLayouts.U8).writeNative(addr + 80L, value.intFracDigits)
                    (PrimitiveLayouts.U8).writeNative(addr + 81L, value.fracDigits)
                    (PrimitiveLayouts.U8).writeNative(addr + 82L, value.pCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 83L, value.pSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 84L, value.nCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 85L, value.nSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 86L, value.pSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 87L, value.nSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 88L, value.intPCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 89L, value.intNCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 90L, value.intPSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 91L, value.intNSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 92L, value.intPSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 93L, value.intNSignPosn)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Lconv =
                    Lconv(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 81),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 83),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 85),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 86),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 87),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 89),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 90),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 91),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 92),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 93),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Lconv) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.decimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.thousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.grouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.intCurrSymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.currencySymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.monDecimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.monThousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.monGrouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.positiveSign)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.negativeSign)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 80, value.intFracDigits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 81, value.fracDigits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 82, value.pCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 83, value.pSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 84, value.nCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 85, value.nSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 86, value.pSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 87, value.nSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 88, value.intPCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 89, value.intNCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 90, value.intPSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 91, value.intNSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 92, value.intPSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 93, value.intNSignPosn)
                }
            }

        public operator fun invoke(
            decimalPoint: MutPtr = MutPtr(0L),
            thousandsSep: MutPtr = MutPtr(0L),
            grouping: MutPtr = MutPtr(0L),
            intCurrSymbol: MutPtr = MutPtr(0L),
            currencySymbol: MutPtr = MutPtr(0L),
            monDecimalPoint: MutPtr = MutPtr(0L),
            monThousandsSep: MutPtr = MutPtr(0L),
            monGrouping: MutPtr = MutPtr(0L),
            positiveSign: MutPtr = MutPtr(0L),
            negativeSign: MutPtr = MutPtr(0L),
            intFracDigits: CChar = 0u,
            fracDigits: CChar = 0u,
            pCsPrecedes: CChar = 0u,
            pSepBySpace: CChar = 0u,
            nCsPrecedes: CChar = 0u,
            nSepBySpace: CChar = 0u,
            pSignPosn: CChar = 0u,
            nSignPosn: CChar = 0u,
            intPCsPrecedes: CChar = 0u,
            intNCsPrecedes: CChar = 0u,
            intPSepBySpace: CChar = 0u,
            intNSepBySpace: CChar = 0u,
            intPSignPosn: CChar = 0u,
            intNSignPosn: CChar = 0u,
        ): Lconv = Lconv(
            decimalPointCtorArg = decimalPoint,
            thousandsSepCtorArg = thousandsSep,
            groupingCtorArg = grouping,
            intCurrSymbolCtorArg = intCurrSymbol,
            currencySymbolCtorArg = currencySymbol,
            monDecimalPointCtorArg = monDecimalPoint,
            monThousandsSepCtorArg = monThousandsSep,
            monGroupingCtorArg = monGrouping,
            positiveSignCtorArg = positiveSign,
            negativeSignCtorArg = negativeSign,
            intFracDigits = intFracDigits,
            fracDigits = fracDigits,
            pCsPrecedes = pCsPrecedes,
            pSepBySpace = pSepBySpace,
            nCsPrecedes = nCsPrecedes,
            nSepBySpace = nSepBySpace,
            pSignPosn = pSignPosn,
            nSignPosn = nSignPosn,
            intPCsPrecedes = intPCsPrecedes,
            intNCsPrecedes = intNCsPrecedes,
            intPSepBySpace = intPSepBySpace,
            intNSepBySpace = intNSepBySpace,
            intPSignPosn = intPSignPosn,
            intNSignPosn = intNSignPosn,
        )
    }
}

public class ProcTaskinfo(
    public var ptiVirtualSize: ULong = 0uL,
    public var ptiResidentSize: ULong = 0uL,
    public var ptiTotalUser: ULong = 0uL,
    public var ptiTotalSystem: ULong = 0uL,
    public var ptiThreadsUser: ULong = 0uL,
    public var ptiThreadsSystem: ULong = 0uL,
    public var ptiPolicy: Int = 0,
    public var ptiFaults: Int = 0,
    public var ptiPageins: Int = 0,
    public var ptiCowFaults: Int = 0,
    public var ptiMessagesSent: Int = 0,
    public var ptiMessagesReceived: Int = 0,
    public var ptiSyscallsMach: Int = 0,
    public var ptiSyscallsUnix: Int = 0,
    public var ptiCsw: Int = 0,
    public var ptiThreadnum: Int = 0,
    public var ptiNumrunning: Int = 0,
    public var ptiPriority: Int = 0,
) {
    public fun clone(): ProcTaskinfo = ProcTaskinfo(ptiVirtualSize, ptiResidentSize, ptiTotalUser, ptiTotalSystem, ptiThreadsUser, ptiThreadsSystem, ptiPolicy, ptiFaults, ptiPageins, ptiCowFaults, ptiMessagesSent, ptiMessagesReceived, ptiSyscallsMach, ptiSyscallsUnix, ptiCsw, ptiThreadnum, ptiNumrunning, ptiPriority)

    public companion object {
        public fun typeLayout(): TypeLayout<ProcTaskinfo> =
            object : TypeLayout<ProcTaskinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): ProcTaskinfo =
                    ProcTaskinfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 52L),
                        (PrimitiveLayouts.I32).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + 60L),
                        (PrimitiveLayouts.I32).readNative(addr + 64L),
                        (PrimitiveLayouts.I32).readNative(addr + 68L),
                        (PrimitiveLayouts.I32).readNative(addr + 72L),
                        (PrimitiveLayouts.I32).readNative(addr + 76L),
                        (PrimitiveLayouts.I32).readNative(addr + 80L),
                        (PrimitiveLayouts.I32).readNative(addr + 84L),
                        (PrimitiveLayouts.I32).readNative(addr + 88L),
                        (PrimitiveLayouts.I32).readNative(addr + 92L),
                    )
                override fun writeNative(addr: Long, value: ProcTaskinfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.ptiVirtualSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ptiResidentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.ptiTotalUser)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.ptiTotalSystem)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.ptiThreadsUser)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.ptiThreadsSystem)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.ptiPolicy)
                    (PrimitiveLayouts.I32).writeNative(addr + 52L, value.ptiFaults)
                    (PrimitiveLayouts.I32).writeNative(addr + 56L, value.ptiPageins)
                    (PrimitiveLayouts.I32).writeNative(addr + 60L, value.ptiCowFaults)
                    (PrimitiveLayouts.I32).writeNative(addr + 64L, value.ptiMessagesSent)
                    (PrimitiveLayouts.I32).writeNative(addr + 68L, value.ptiMessagesReceived)
                    (PrimitiveLayouts.I32).writeNative(addr + 72L, value.ptiSyscallsMach)
                    (PrimitiveLayouts.I32).writeNative(addr + 76L, value.ptiSyscallsUnix)
                    (PrimitiveLayouts.I32).writeNative(addr + 80L, value.ptiCsw)
                    (PrimitiveLayouts.I32).writeNative(addr + 84L, value.ptiThreadnum)
                    (PrimitiveLayouts.I32).writeNative(addr + 88L, value.ptiNumrunning)
                    (PrimitiveLayouts.I32).writeNative(addr + 92L, value.ptiPriority)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcTaskinfo =
                    ProcTaskinfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 68),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 76),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 92),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcTaskinfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.ptiVirtualSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ptiResidentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.ptiTotalUser)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.ptiTotalSystem)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.ptiThreadsUser)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.ptiThreadsSystem)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.ptiPolicy)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 52, value.ptiFaults)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 56, value.ptiPageins)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 60, value.ptiCowFaults)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 64, value.ptiMessagesSent)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 68, value.ptiMessagesReceived)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 72, value.ptiSyscallsMach)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 76, value.ptiSyscallsUnix)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 80, value.ptiCsw)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 84, value.ptiThreadnum)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 88, value.ptiNumrunning)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 92, value.ptiPriority)
                }
            }
    }
}

public class ProcBsdinfo(
    public var pbiFlags: UInt = 0u,
    public var pbiStatus: UInt = 0u,
    public var pbiXstatus: UInt = 0u,
    public var pbiPid: UInt = 0u,
    public var pbiPpid: UInt = 0u,
    public var pbiUid: UidT = 0,
    public var pbiGid: GidT = 0,
    public var pbiRuid: UidT = 0,
    public var pbiRgid: GidT = 0,
    public var pbiSvuid: UidT = 0,
    public var pbiSvgid: GidT = 0,
    public var rfu1: UInt = 0u,
    public var pbiComm: UByteArray = UByteArray(MAXCOMLEN),
    public var pbiName: UByteArray = UByteArray(32),
    // MAXCOMLEN * 2, but macro isn't happy...
    public var pbiNfiles: UInt = 0u,
    public var pbiPgid: UInt = 0u,
    public var pbiPjobc: UInt = 0u,
    public var eTdev: UInt = 0u,
    public var eTpgid: UInt = 0u,
    public var pbiNice: Int = 0,
    public var pbiStartTvsec: ULong = 0uL,
    public var pbiStartTvusec: ULong = 0uL,
) {
    public fun clone(): ProcBsdinfo = ProcBsdinfo(pbiFlags, pbiStatus, pbiXstatus, pbiPid, pbiPpid, pbiUid, pbiGid, pbiRuid, pbiRgid, pbiSvuid, pbiSvgid, rfu1, pbiComm, pbiName, pbiNfiles, pbiPgid, pbiPjobc, eTdev, eTpgid, pbiNice, pbiStartTvsec, pbiStartTvusec)

    public companion object {
        public fun typeLayout(): TypeLayout<ProcBsdinfo> =
            object : TypeLayout<ProcBsdinfo> {
                override val alignBytes: Long = 8L
                private val off13: Long = alignUpBytes((48L + ((MAXCOMLEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off14: Long = alignUpBytes((off13 + 32L), 4L)
                private val off15: Long = alignUpBytes((off14 + 4L), 4L)
                private val off16: Long = alignUpBytes((off15 + 4L), 4L)
                private val off17: Long = alignUpBytes((off16 + 4L), 4L)
                private val off18: Long = alignUpBytes((off17 + 4L), 4L)
                private val off19: Long = alignUpBytes((off18 + 4L), 4L)
                private val off20: Long = alignUpBytes((off19 + 4L), 8L)
                private val off21: Long = alignUpBytes((off20 + 8L), 8L)
                override val sizeBytes: Long = alignUpBytes((off21 + 8L), 8L)
                override fun readNative(addr: Long): ProcBsdinfo =
                    ProcBsdinfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).readNative(addr + 48L),
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr + off13),
                        (PrimitiveLayouts.U32).readNative(addr + off14),
                        (PrimitiveLayouts.U32).readNative(addr + off15),
                        (PrimitiveLayouts.U32).readNative(addr + off16),
                        (PrimitiveLayouts.U32).readNative(addr + off17),
                        (PrimitiveLayouts.U32).readNative(addr + off18),
                        (PrimitiveLayouts.I32).readNative(addr + off19),
                        (PrimitiveLayouts.U64).readNative(addr + off20),
                        (PrimitiveLayouts.U64).readNative(addr + off21),
                    )
                override fun writeNative(addr: Long, value: ProcBsdinfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.pbiFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.pbiStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.pbiXstatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.pbiPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.pbiPpid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pbiUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.pbiGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.pbiRuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.pbiRgid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pbiSvuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.pbiSvgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.rfu1)
                    (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).writeNative(addr + 48L, value.pbiComm)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr + off13, value.pbiName)
                    (PrimitiveLayouts.U32).writeNative(addr + off14, value.pbiNfiles)
                    (PrimitiveLayouts.U32).writeNative(addr + off15, value.pbiPgid)
                    (PrimitiveLayouts.U32).writeNative(addr + off16, value.pbiPjobc)
                    (PrimitiveLayouts.U32).writeNative(addr + off17, value.eTdev)
                    (PrimitiveLayouts.U32).writeNative(addr + off18, value.eTpgid)
                    (PrimitiveLayouts.I32).writeNative(addr + off19, value.pbiNice)
                    (PrimitiveLayouts.U64).writeNative(addr + off20, value.pbiStartTvsec)
                    (PrimitiveLayouts.U64).writeNative(addr + off21, value.pbiStartTvusec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcBsdinfo =
                    ProcBsdinfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).readBytes(buf, offset + 48),
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset + off13.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off14.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off15.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off16.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off17.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off18.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off19.toInt()),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off20.toInt()),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off21.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcBsdinfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.pbiFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.pbiStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.pbiXstatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.pbiPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.pbiPpid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pbiUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.pbiGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.pbiRuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.pbiRgid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pbiSvuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.pbiSvgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.rfu1)
                    (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).writeBytes(buf, offset + 48, value.pbiComm)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset + off13.toInt(), value.pbiName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off14.toInt(), value.pbiNfiles)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off15.toInt(), value.pbiPgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off16.toInt(), value.pbiPjobc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off17.toInt(), value.eTdev)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off18.toInt(), value.eTpgid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off19.toInt(), value.pbiNice)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off20.toInt(), value.pbiStartTvsec)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off21.toInt(), value.pbiStartTvusec)
                }
            }
    }
}

public data class ProcBsdshortinfo(
    /**
     * Process ID.
     */
    public var pbsiPid: UInt = 0u,
    /**
     * Process parent ID.
     */
    public var pbsiPpid: UInt = 0u,
    /**
     * Process perp ID.
     */
    public var pbsiPgid: UInt = 0u,
    /**
     * `pStat` value: `SZOMB`, `SRUN`, etc.
     */
    public var pbsiStatus: UInt = 0u,
    /**
     * Up to 16 characters of process name.
     */
    public var pbsiComm: UByteArray = UByteArray(MAXCOMLEN),
    /**
     * 64bit, emulated, etc.
     */
    public var pbsiFlags: UInt = 0u,
    /**
     * Current UID on process.
     */
    public var pbsiUid: UidT = 0,
    /**
     * Current GID on process.
     */
    public var pbsiGid: GidT = 0,
    /**
     * Current RUID on process.
     */
    public var pbsiRuid: UidT = 0,
    /**
     * Current RGID on process.
     */
    public var pbsiRgid: GidT = 0,
    /**
     * Current SVUID on process.
     */
    public var pbsiSvuid: UidT = 0,
    /**
     * Current SVGID on process.
     */
    public var pbsiSvgid: GidT = 0,
    /**
     * Reserved for future use.
     */
    public var pbsiRfu: UInt = 0u,
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProcBsdshortinfo) return false
        if (this.pbsiPid != other.pbsiPid) return false
        if (this.pbsiPpid != other.pbsiPpid) return false
        if (this.pbsiPgid != other.pbsiPgid) return false
        if (this.pbsiStatus != other.pbsiStatus) return false
        if (!this.pbsiComm.contentEquals(other.pbsiComm)) return false
        if (this.pbsiFlags != other.pbsiFlags) return false
        if (this.pbsiUid != other.pbsiUid) return false
        if (this.pbsiGid != other.pbsiGid) return false
        if (this.pbsiRuid != other.pbsiRuid) return false
        if (this.pbsiRgid != other.pbsiRgid) return false
        if (this.pbsiSvuid != other.pbsiSvuid) return false
        if (this.pbsiSvgid != other.pbsiSvgid) return false
        if (this.pbsiRfu != other.pbsiRfu) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pbsiPid.hashCode()
        result = 31 * result + this.pbsiPpid.hashCode()
        result = 31 * result + this.pbsiPgid.hashCode()
        result = 31 * result + this.pbsiStatus.hashCode()
        result = 31 * result + this.pbsiComm.contentHashCode()
        result = 31 * result + this.pbsiFlags.hashCode()
        result = 31 * result + this.pbsiUid.hashCode()
        result = 31 * result + this.pbsiGid.hashCode()
        result = 31 * result + this.pbsiRuid.hashCode()
        result = 31 * result + this.pbsiRgid.hashCode()
        result = 31 * result + this.pbsiSvuid.hashCode()
        result = 31 * result + this.pbsiSvgid.hashCode()
        result = 31 * result + this.pbsiRfu.hashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ProcBsdshortinfo> =
            object : TypeLayout<ProcBsdshortinfo> {
                override val alignBytes: Long = 4L
                private val off5: Long = alignUpBytes((16L + ((MAXCOMLEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                private val off6: Long = alignUpBytes((off5 + 4L), 4L)
                private val off7: Long = alignUpBytes((off6 + 4L), 4L)
                private val off8: Long = alignUpBytes((off7 + 4L), 4L)
                private val off9: Long = alignUpBytes((off8 + 4L), 4L)
                private val off10: Long = alignUpBytes((off9 + 4L), 4L)
                private val off11: Long = alignUpBytes((off10 + 4L), 4L)
                private val off12: Long = alignUpBytes((off11 + 4L), 4L)
                private val off13: Long = alignUpBytes((off12 + 4L), 1L)
                override val sizeBytes: Long = alignUpBytes((off13 + 0L), 4L)
                override fun readNative(addr: Long): ProcBsdshortinfo =
                    ProcBsdshortinfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + off5),
                        (PrimitiveLayouts.I32).readNative(addr + off6),
                        (PrimitiveLayouts.I32).readNative(addr + off7),
                        (PrimitiveLayouts.I32).readNative(addr + off8),
                        (PrimitiveLayouts.I32).readNative(addr + off9),
                        (PrimitiveLayouts.I32).readNative(addr + off10),
                        (PrimitiveLayouts.I32).readNative(addr + off11),
                        (PrimitiveLayouts.U32).readNative(addr + off12),
                        (PrimitiveLayouts.UNIT).readNative(addr + off13),
                    )
                override fun writeNative(addr: Long, value: ProcBsdshortinfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.pbsiPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.pbsiPpid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.pbsiPgid)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.pbsiStatus)
                    (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).writeNative(addr + 16L, value.pbsiComm)
                    (PrimitiveLayouts.U32).writeNative(addr + off5, value.pbsiFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + off6, value.pbsiUid)
                    (PrimitiveLayouts.I32).writeNative(addr + off7, value.pbsiGid)
                    (PrimitiveLayouts.I32).writeNative(addr + off8, value.pbsiRuid)
                    (PrimitiveLayouts.I32).writeNative(addr + off9, value.pbsiRgid)
                    (PrimitiveLayouts.I32).writeNative(addr + off10, value.pbsiSvuid)
                    (PrimitiveLayouts.I32).writeNative(addr + off11, value.pbsiSvgid)
                    (PrimitiveLayouts.U32).writeNative(addr + off12, value.pbsiRfu)
                    (PrimitiveLayouts.UNIT).writeNative(addr + off13, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcBsdshortinfo =
                    ProcBsdshortinfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off5.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off6.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off7.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off8.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off9.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off10.toInt()),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + off11.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off12.toInt()),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + off13.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcBsdshortinfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.pbsiPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.pbsiPpid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.pbsiPgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.pbsiStatus)
                    (PrimitiveArrayLayouts.uByteArray((MAXCOMLEN).toInt())).writeBytes(buf, offset + 16, value.pbsiComm)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off5.toInt(), value.pbsiFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off6.toInt(), value.pbsiUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off7.toInt(), value.pbsiGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off8.toInt(), value.pbsiRuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off9.toInt(), value.pbsiRgid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off10.toInt(), value.pbsiSvuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + off11.toInt(), value.pbsiSvgid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off12.toInt(), value.pbsiRfu)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + off13.toInt(), value.nonExhaustive)
                }
            }
    }
}

public data class ProcTaskallinfo(
    public var pbsd: ProcBsdinfo = ProcBsdinfo(),
    public var ptinfo: ProcTaskinfo = ProcTaskinfo(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ProcTaskallinfo> =
            object : TypeLayout<ProcTaskallinfo> {
                override val alignBytes: Long = 8L
                private val off1: Long = alignUpBytes(ProcBsdinfo.typeLayout().sizeBytes, 8L)
                override val sizeBytes: Long = alignUpBytes((off1 + 96L), 8L)
                override fun readNative(addr: Long): ProcTaskallinfo =
                    ProcTaskallinfo(
                        (ProcBsdinfo.typeLayout()).readNative(addr),
                        (ProcTaskinfo.typeLayout()).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: ProcTaskallinfo) {
                    (ProcBsdinfo.typeLayout()).writeNative(addr, value.pbsd)
                    (ProcTaskinfo.typeLayout()).writeNative(addr + off1, value.ptinfo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcTaskallinfo =
                    ProcTaskallinfo(
                        (ProcBsdinfo.typeLayout()).readBytes(buf, offset),
                        (ProcTaskinfo.typeLayout()).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcTaskallinfo) {
                    (ProcBsdinfo.typeLayout()).writeBytes(buf, offset, value.pbsd)
                    (ProcTaskinfo.typeLayout()).writeBytes(buf, offset + off1.toInt(), value.ptinfo)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class XswUsage(
    public var xsuTotal: ULong = 0uL,
    public var xsuAvail: ULong = 0uL,
    public var xsuUsed: ULong = 0uL,
    public var xsuPagesize: UInt = 0u,
    public var xsuEncrypted: BooleanT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<XswUsage> =
            object : TypeLayout<XswUsage> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): XswUsage =
                    XswUsage(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: XswUsage) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.xsuTotal)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.xsuAvail)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.xsuUsed)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.xsuPagesize)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.xsuEncrypted)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XswUsage =
                    XswUsage(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XswUsage) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.xsuTotal)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.xsuAvail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.xsuUsed)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.xsuPagesize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.xsuEncrypted)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Xucred(
    public var crVersion: CUint = 0u,
    public var crUid: UidT = 0,
    public var crNgroups: CShort = 0,
    public var crGroups: IntArray = IntArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Xucred) return false
        if (this.crVersion != other.crVersion) return false
        if (this.crUid != other.crUid) return false
        if (this.crNgroups != other.crNgroups) return false
        if (!this.crGroups.contentEquals(other.crGroups)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.crVersion.hashCode()
        result = 31 * result + this.crUid.hashCode()
        result = 31 * result + this.crNgroups.hashCode()
        result = 31 * result + this.crGroups.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Xucred> =
            object : TypeLayout<Xucred> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 76L
                override fun readNative(addr: Long): Xucred =
                    Xucred(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.intArray(16)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Xucred) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.crVersion)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.crUid)
                    (PrimitiveLayouts.I16).writeNative(addr + 8L, value.crNgroups)
                    (PrimitiveArrayLayouts.intArray(16)).writeNative(addr + 12L, value.crGroups)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Xucred =
                    Xucred(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.intArray(16)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Xucred) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.crVersion)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.crUid)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 8, value.crNgroups)
                    (PrimitiveArrayLayouts.intArray(16)).writeBytes(buf, offset + 12, value.crGroups)
                }
            }
    }
}

public data class MachHeader(
    public var magic: UInt = 0u,
    public var cputype: CpuTypeT = 0,
    public var cpusubtype: CpuSubtypeT = 0,
    public var filetype: UInt = 0u,
    public var ncmds: UInt = 0u,
    public var sizeofcmds: UInt = 0u,
    public var flags: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MachHeader> =
            object : TypeLayout<MachHeader> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 28L
                override fun readNative(addr: Long): MachHeader =
                    MachHeader(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: MachHeader) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.magic)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.cputype)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cpusubtype)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.filetype)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ncmds)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.sizeofcmds)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MachHeader =
                    MachHeader(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MachHeader) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.magic)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.cputype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cpusubtype)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.filetype)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ncmds)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.sizeofcmds)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.flags)
                }
            }
    }
}

public data class MachHeader64(
    public var magic: UInt = 0u,
    public var cputype: CpuTypeT = 0,
    public var cpusubtype: CpuSubtypeT = 0,
    public var filetype: UInt = 0u,
    public var ncmds: UInt = 0u,
    public var sizeofcmds: UInt = 0u,
    public var flags: UInt = 0u,
    public var reserved: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MachHeader64> =
            object : TypeLayout<MachHeader64> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): MachHeader64 =
                    MachHeader64(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: MachHeader64) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.magic)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.cputype)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cpusubtype)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.filetype)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ncmds)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.sizeofcmds)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MachHeader64 =
                    MachHeader64(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MachHeader64) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.magic)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.cputype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cpusubtype)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.filetype)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ncmds)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.sizeofcmds)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.reserved)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class SegmentCommand(
    public var cmd: UInt = 0u,
    public var cmdsize: UInt = 0u,
    public var segname: UByteArray = UByteArray(16),
    public var vmaddr: UInt = 0u,
    public var vmsize: UInt = 0u,
    public var fileoff: UInt = 0u,
    public var filesize: UInt = 0u,
    public var maxprot: VmProtT = 0,
    public var initprot: VmProtT = 0,
    public var nsects: UInt = 0u,
    public var flags: UInt = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SegmentCommand) return false
        if (this.cmd != other.cmd) return false
        if (this.cmdsize != other.cmdsize) return false
        if (!this.segname.contentEquals(other.segname)) return false
        if (this.vmaddr != other.vmaddr) return false
        if (this.vmsize != other.vmsize) return false
        if (this.fileoff != other.fileoff) return false
        if (this.filesize != other.filesize) return false
        if (this.maxprot != other.maxprot) return false
        if (this.initprot != other.initprot) return false
        if (this.nsects != other.nsects) return false
        if (this.flags != other.flags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cmd.hashCode()
        result = 31 * result + this.cmdsize.hashCode()
        result = 31 * result + this.segname.contentHashCode()
        result = 31 * result + this.vmaddr.hashCode()
        result = 31 * result + this.vmsize.hashCode()
        result = 31 * result + this.fileoff.hashCode()
        result = 31 * result + this.filesize.hashCode()
        result = 31 * result + this.maxprot.hashCode()
        result = 31 * result + this.initprot.hashCode()
        result = 31 * result + this.nsects.hashCode()
        result = 31 * result + this.flags.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SegmentCommand> =
            object : TypeLayout<SegmentCommand> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): SegmentCommand =
                    SegmentCommand(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                        (PrimitiveLayouts.U32).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 52L),
                    )
                override fun writeNative(addr: Long, value: SegmentCommand) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cmd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cmdsize)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr + 8L, value.segname)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.vmaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.vmsize)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.fileoff)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.filesize)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.maxprot)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.initprot)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.nsects)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SegmentCommand =
                    SegmentCommand(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 52),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SegmentCommand) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cmd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cmdsize)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset + 8, value.segname)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.vmaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.vmsize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.fileoff)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.filesize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.maxprot)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.initprot)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.nsects)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.flags)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class SegmentCommand64(
    public var cmd: UInt = 0u,
    public var cmdsize: UInt = 0u,
    public var segname: UByteArray = UByteArray(16),
    public var vmaddr: ULong = 0uL,
    public var vmsize: ULong = 0uL,
    public var fileoff: ULong = 0uL,
    public var filesize: ULong = 0uL,
    public var maxprot: VmProtT = 0,
    public var initprot: VmProtT = 0,
    public var nsects: UInt = 0u,
    public var flags: UInt = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SegmentCommand64) return false
        if (this.cmd != other.cmd) return false
        if (this.cmdsize != other.cmdsize) return false
        if (!this.segname.contentEquals(other.segname)) return false
        if (this.vmaddr != other.vmaddr) return false
        if (this.vmsize != other.vmsize) return false
        if (this.fileoff != other.fileoff) return false
        if (this.filesize != other.filesize) return false
        if (this.maxprot != other.maxprot) return false
        if (this.initprot != other.initprot) return false
        if (this.nsects != other.nsects) return false
        if (this.flags != other.flags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cmd.hashCode()
        result = 31 * result + this.cmdsize.hashCode()
        result = 31 * result + this.segname.contentHashCode()
        result = 31 * result + this.vmaddr.hashCode()
        result = 31 * result + this.vmsize.hashCode()
        result = 31 * result + this.fileoff.hashCode()
        result = 31 * result + this.filesize.hashCode()
        result = 31 * result + this.maxprot.hashCode()
        result = 31 * result + this.initprot.hashCode()
        result = 31 * result + this.nsects.hashCode()
        result = 31 * result + this.flags.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SegmentCommand64> =
            object : TypeLayout<SegmentCommand64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): SegmentCommand64 =
                    SegmentCommand64(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + 60L),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                        (PrimitiveLayouts.U32).readNative(addr + 68L),
                    )
                override fun writeNative(addr: Long, value: SegmentCommand64) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cmd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cmdsize)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr + 8L, value.segname)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.vmaddr)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.vmsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fileoff)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.filesize)
                    (PrimitiveLayouts.I32).writeNative(addr + 56L, value.maxprot)
                    (PrimitiveLayouts.I32).writeNative(addr + 60L, value.initprot)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.nsects)
                    (PrimitiveLayouts.U32).writeNative(addr + 68L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SegmentCommand64 =
                    SegmentCommand64(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 68),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SegmentCommand64) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cmd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cmdsize)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset + 8, value.segname)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.vmaddr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.vmsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fileoff)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.filesize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 56, value.maxprot)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 60, value.initprot)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.nsects)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.flags)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class LoadCommand(
    public var cmd: UInt = 0u,
    public var cmdsize: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<LoadCommand> =
            object : TypeLayout<LoadCommand> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): LoadCommand =
                    LoadCommand(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: LoadCommand) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cmd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cmdsize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LoadCommand =
                    LoadCommand(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LoadCommand) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cmd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cmdsize)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class SockaddrDl(
    public var sdlLen: CUchar = 0u,
    public var sdlFamily: CUchar = 0u,
    public var sdlIndex: CUshort = 0u,
    public var sdlType: CUchar = 0u,
    public var sdlNlen: CUchar = 0u,
    public var sdlAlen: CUchar = 0u,
    public var sdlSlen: CUchar = 0u,
    public var sdlData: UByteArray = UByteArray(12),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrDl) return false
        if (this.sdlLen != other.sdlLen) return false
        if (this.sdlFamily != other.sdlFamily) return false
        if (this.sdlIndex != other.sdlIndex) return false
        if (this.sdlType != other.sdlType) return false
        if (this.sdlNlen != other.sdlNlen) return false
        if (this.sdlAlen != other.sdlAlen) return false
        if (this.sdlSlen != other.sdlSlen) return false
        if (!this.sdlData.contentEquals(other.sdlData)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sdlLen.hashCode()
        result = 31 * result + this.sdlFamily.hashCode()
        result = 31 * result + this.sdlIndex.hashCode()
        result = 31 * result + this.sdlType.hashCode()
        result = 31 * result + this.sdlNlen.hashCode()
        result = 31 * result + this.sdlAlen.hashCode()
        result = 31 * result + this.sdlSlen.hashCode()
        result = 31 * result + this.sdlData.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrDl> =
            object : TypeLayout<SockaddrDl> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): SockaddrDl =
                    SockaddrDl(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                        (PrimitiveArrayLayouts.uByteArray(12)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrDl) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.sdlLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.sdlFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sdlIndex)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.sdlType)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.sdlNlen)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.sdlAlen)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.sdlSlen)
                    (PrimitiveArrayLayouts.uByteArray(12)).writeNative(addr + 8L, value.sdlData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrDl =
                    SockaddrDl(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                        (PrimitiveArrayLayouts.uByteArray(12)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrDl) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.sdlLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.sdlFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sdlIndex)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.sdlType)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.sdlNlen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.sdlAlen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.sdlSlen)
                    (PrimitiveArrayLayouts.uByteArray(12)).writeBytes(buf, offset + 8, value.sdlData)
                }
            }
    }
}

public data class SockaddrInarp(
    public var sinLen: CUchar = 0u,
    public var sinFamily: CUchar = 0u,
    public var sinPort: CUshort = 0u,
    public var sinAddr: io.github.kotlinmania.rustlang.libc.InAddr = io.github.kotlinmania.rustlang.libc.InAddr(),
    public var sinSrcaddr: io.github.kotlinmania.rustlang.libc.InAddr = io.github.kotlinmania.rustlang.libc.InAddr(),
    public var sinTos: CUshort = 0u,
    public var sinOther: CUshort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrInarp> =
            object : TypeLayout<SockaddrInarp> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrInarp =
                    SockaddrInarp(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (InAddr.typeLayout()).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                    )
                override fun writeNative(addr: Long, value: SockaddrInarp) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.sinLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.sinFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sinPort)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.sinAddr)
                    (InAddr.typeLayout()).writeNative(addr + 8L, value.sinSrcaddr)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.sinTos)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.sinOther)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrInarp =
                    SockaddrInarp(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (InAddr.typeLayout()).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrInarp) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.sinLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.sinFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sinPort)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.sinAddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 8, value.sinSrcaddr)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.sinTos)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.sinOther)
                }
            }
    }
}

public data class SockaddrCtl(
    public var scLen: CUchar = 0u,
    public var scFamily: CUchar = 0u,
    public var ssSysaddr: UShort = 0u,
    public var scId: UInt = 0u,
    public var scUnit: UInt = 0u,
    public var scReserved: UIntArray = UIntArray(5),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrCtl) return false
        if (this.scLen != other.scLen) return false
        if (this.scFamily != other.scFamily) return false
        if (this.ssSysaddr != other.ssSysaddr) return false
        if (this.scId != other.scId) return false
        if (this.scUnit != other.scUnit) return false
        if (!this.scReserved.contentEquals(other.scReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.scLen.hashCode()
        result = 31 * result + this.scFamily.hashCode()
        result = 31 * result + this.ssSysaddr.hashCode()
        result = 31 * result + this.scId.hashCode()
        result = 31 * result + this.scUnit.hashCode()
        result = 31 * result + this.scReserved.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrCtl> =
            object : TypeLayout<SockaddrCtl> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SockaddrCtl =
                    SockaddrCtl(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uIntArray(5)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrCtl) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.scLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.scFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.ssSysaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.scId)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.scUnit)
                    (PrimitiveArrayLayouts.uIntArray(5)).writeNative(addr + 12L, value.scReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrCtl =
                    SockaddrCtl(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uIntArray(5)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrCtl) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.scLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.scFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.ssSysaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.scId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.scUnit)
                    (PrimitiveArrayLayouts.uIntArray(5)).writeBytes(buf, offset + 12, value.scReserved)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class InPktinfo(
    public var ipiIfindex: CUint = 0u,
    public var ipiSpecDst: io.github.kotlinmania.rustlang.libc.InAddr = io.github.kotlinmania.rustlang.libc.InAddr(),
    public var ipiAddr: io.github.kotlinmania.rustlang.libc.InAddr = io.github.kotlinmania.rustlang.libc.InAddr(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<InPktinfo> =
            object : TypeLayout<InPktinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): InPktinfo =
                    InPktinfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (InAddr.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InPktinfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ipiIfindex)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.ipiSpecDst)
                    (InAddr.typeLayout()).writeNative(addr + 8L, value.ipiAddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InPktinfo =
                    InPktinfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (InAddr.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InPktinfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ipiIfindex)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.ipiSpecDst)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 8, value.ipiAddr)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class In6Pktinfo(
    public var ipi6Addr: In6Addr = In6Addr(),
    public var ipi6Ifindex: CUint = 0u,
)

// Note(exhaustive): review for 1.0
public data class IpcPerm(
    public var uid: UidT = 0,
    public var gid: GidT = 0,
    public var cuid: UidT = 0,
    public var cgid: GidT = 0,
    public var mode: ModeT = 0u,
    public var seq: CUshort = 0u,
    public var key: io.github.kotlinmania.rustlang.libc.KeyT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IpcPerm> =
            object : TypeLayout<IpcPerm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 18L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.uid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.gid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cgid)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.mode)
                    (PrimitiveLayouts.U16).writeNative(addr + 18L, value.seq)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.key)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.uid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.gid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cgid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.mode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 18, value.seq)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.key)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Sembuf(
    public var semNum: CUshort = 0u,
    public var semOp: CShort = 0,
    public var semFlg: CShort = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Sembuf> =
            object : TypeLayout<Sembuf> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): Sembuf =
                    Sembuf(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Sembuf) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.semNum)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.semOp)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.semFlg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sembuf =
                    Sembuf(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sembuf) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.semNum)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.semOp)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.semFlg)
                }
            }
    }
}

public data class Arphdr(
    public var arHrd: UShort = 0u,
    public var arPro: UShort = 0u,
    public var arHln: UByte = 0u,
    public var arPln: UByte = 0u,
    public var arOp: UShort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Arphdr> =
            object : TypeLayout<Arphdr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Arphdr =
                    Arphdr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: Arphdr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.arHrd)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.arPro)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.arHln)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.arPln)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.arOp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arphdr =
                    Arphdr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arphdr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.arHrd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.arPro)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.arHln)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.arPln)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.arOp)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class InAddr(
    public var sAddr: InAddrT = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<InAddr> =
            object : TypeLayout<InAddr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): InAddr =
                    InAddr(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: InAddr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.sAddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InAddr =
                    InAddr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InAddr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.sAddr)
                }
            }
    }
}

public data class SockaddrNdrv(
    public var sndLen: CUchar = 0u,
    public var sndFamily: CUchar = 0u,
    public var sndName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrNdrv) return false
        if (this.sndLen != other.sndLen) return false
        if (this.sndFamily != other.sndFamily) return false
        if (!this.sndName.contentEquals(other.sndName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sndLen.hashCode()
        result = 31 * result + this.sndFamily.hashCode()
        result = 31 * result + this.sndName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrNdrv> =
            object : TypeLayout<SockaddrNdrv> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = alignUpBytes((2L + ((IFNAMSIZ).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override fun readNative(addr: Long): SockaddrNdrv =
                    SockaddrNdrv(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: SockaddrNdrv) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.sndLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.sndFamily)
                    (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).writeNative(addr + 2L, value.sndName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrNdrv =
                    SockaddrNdrv(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrNdrv) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.sndLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.sndFamily)
                    (PrimitiveArrayLayouts.uByteArray((IFNAMSIZ).toInt())).writeBytes(buf, offset + 2, value.sndName)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class SaEndpointsT internal constructor(
    public var saeSrcif: CUint,
    saeSrcaddrCtorArg: ConstPtr,
    // optional source address
    public var saeSrcaddrlen: SocklenT,
    saeDstaddrCtorArg: ConstPtr,
    // destination address
    public var saeDstaddrlen: SocklenT,
) {
    public var saeSrcaddr: ConstPtr = saeSrcaddrCtorArg
        internal set

    public var saeDstaddr: ConstPtr = saeDstaddrCtorArg
        internal set

    public constructor() : this(
        saeSrcif = 0u,
        saeSrcaddrCtorArg = ConstPtr(0L),
        saeSrcaddrlen = 0u,
        saeDstaddrCtorArg = ConstPtr(0L),
        saeDstaddrlen = 0u,
    )

    public operator fun component1(): CUint = this.saeSrcif

    public operator fun component2(): ConstPtr = this.saeSrcaddr

    public operator fun component3(): SocklenT = this.saeSrcaddrlen

    public operator fun component4(): ConstPtr = this.saeDstaddr

    public operator fun component5(): SocklenT = this.saeDstaddrlen

    public fun copy(saeSrcif: CUint = this.saeSrcif, saeSrcaddr: ConstPtr = this.saeSrcaddr, saeSrcaddrlen: SocklenT = this.saeSrcaddrlen, saeDstaddr: ConstPtr = this.saeDstaddr, saeDstaddrlen: SocklenT = this.saeDstaddrlen): SaEndpointsT = SaEndpointsT(saeSrcif = saeSrcif, saeSrcaddrCtorArg = saeSrcaddr, saeSrcaddrlen = saeSrcaddrlen, saeDstaddrCtorArg = saeDstaddr, saeDstaddrlen = saeDstaddrlen)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SaEndpointsT) return false
        if (this.saeSrcif != other.saeSrcif) return false
        if (this.saeSrcaddr != other.saeSrcaddr) return false
        if (this.saeSrcaddrlen != other.saeSrcaddrlen) return false
        if (this.saeDstaddr != other.saeDstaddr) return false
        if (this.saeDstaddrlen != other.saeDstaddrlen) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.saeSrcif.hashCode()
        result = 31 * result + this.saeSrcaddr.hashCode()
        result = 31 * result + this.saeSrcaddrlen.hashCode()
        result = 31 * result + this.saeDstaddr.hashCode()
        result = 31 * result + this.saeDstaddrlen.hashCode()
        return result
    }

    public fun clone(): SaEndpointsT = SaEndpointsT(saeSrcif, saeSrcaddr, saeSrcaddrlen, saeDstaddr, saeDstaddrlen)

    public companion object {
        public fun typeLayout(): TypeLayout<SaEndpointsT> =
            object : TypeLayout<SaEndpointsT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): SaEndpointsT =
                    SaEndpointsT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: SaEndpointsT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.saeSrcif)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 8L, value.saeSrcaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.saeSrcaddrlen)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 24L, value.saeDstaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.saeDstaddrlen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SaEndpointsT =
                    SaEndpointsT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SaEndpointsT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.saeSrcif)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 8, value.saeSrcaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.saeSrcaddrlen)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 24, value.saeDstaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.saeDstaddrlen)
                }
            }

        public operator fun invoke(
            saeSrcif: CUint = 0u,
            saeSrcaddr: ConstPtr = ConstPtr(0L),
            saeSrcaddrlen: SocklenT = 0u,
            saeDstaddr: ConstPtr = ConstPtr(0L),
            saeDstaddrlen: SocklenT = 0u,
        ): SaEndpointsT = SaEndpointsT(
            saeSrcif = saeSrcif,
            saeSrcaddrCtorArg = saeSrcaddr,
            saeSrcaddrlen = saeSrcaddrlen,
            saeDstaddrCtorArg = saeDstaddr,
            saeDstaddrlen = saeDstaddrlen,
        )
    }
}

// Note(exhaustive): review for 1.0
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
    public var ppsfreq: CLong = 0L,
    public var jitter: CLong = 0L,
    public var shift: CInt = 0,
    public var stabil: CLong = 0L,
    public var jitcnt: CLong = 0L,
    public var calcnt: CLong = 0L,
    public var errcnt: CLong = 0L,
    public var stbcnt: CLong = 0L,
) {
    public fun clone(): Timex = Timex(modes, offset, freq, maxerror, esterror, status, constant, precision, tolerance, ppsfreq, jitter, shift, stabil, jitcnt, calcnt, errcnt, stbcnt)

    public companion object {
        public fun typeLayout(): TypeLayout<Timex> =
            object : TypeLayout<Timex> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 136L
                override fun readNative(addr: Long): Timex =
                    Timex(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I32).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveLayouts.I64).readNative(addr + 120L),
                        (PrimitiveLayouts.I64).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: Timex) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.modes)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.offset)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.freq)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.maxerror)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.esterror)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.status)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.constant)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.precision)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.tolerance)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.ppsfreq)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.jitter)
                    (PrimitiveLayouts.I32).writeNative(addr + 88L, value.shift)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stabil)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.jitcnt)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.calcnt)
                    (PrimitiveLayouts.I64).writeNative(addr + 120L, value.errcnt)
                    (PrimitiveLayouts.I64).writeNative(addr + 128L, value.stbcnt)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timex =
                    Timex(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timex) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.modes)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.offset)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.freq)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.maxerror)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.esterror)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.status)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.constant)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.precision)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.tolerance)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.ppsfreq)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.jitter)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 88, value.shift)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stabil)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.jitcnt)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.calcnt)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 120, value.errcnt)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 128, value.stbcnt)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Ntptimeval(
    public var time: Timespec = Timespec(),
    public var maxerror: CLong = 0L,
    public var esterror: CLong = 0L,
    public var tai: CLong = 0L,
    public var timeState: CInt = 0,
)

// Note(exhaustive): review for 1.0
public data class ThreadStandardPolicy(
    public var noData: NaturalT = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadStandardPolicy> =
            object : TypeLayout<ThreadStandardPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadStandardPolicy =
                    ThreadStandardPolicy(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadStandardPolicy) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.noData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadStandardPolicy =
                    ThreadStandardPolicy(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadStandardPolicy) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.noData)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadExtendedPolicy(
    public var timeshare: io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.aarch64.BooleanT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadExtendedPolicy> =
            object : TypeLayout<ThreadExtendedPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadExtendedPolicy =
                    ThreadExtendedPolicy(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadExtendedPolicy) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.timeshare)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadExtendedPolicy =
                    ThreadExtendedPolicy(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadExtendedPolicy) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.timeshare)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadTimeConstraintPolicy(
    public var period: UInt = 0u,
    public var computation: UInt = 0u,
    public var constraint: UInt = 0u,
    public var preemptible: io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.aarch64.BooleanT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadTimeConstraintPolicy> =
            object : TypeLayout<ThreadTimeConstraintPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ThreadTimeConstraintPolicy =
                    ThreadTimeConstraintPolicy(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: ThreadTimeConstraintPolicy) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.period)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.computation)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.constraint)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.preemptible)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadTimeConstraintPolicy =
                    ThreadTimeConstraintPolicy(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadTimeConstraintPolicy) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.period)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.computation)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.constraint)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.preemptible)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadPrecedencePolicy(
    public var importance: IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadPrecedencePolicy> =
            object : TypeLayout<ThreadPrecedencePolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadPrecedencePolicy =
                    ThreadPrecedencePolicy(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadPrecedencePolicy) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.importance)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadPrecedencePolicy =
                    ThreadPrecedencePolicy(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadPrecedencePolicy) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.importance)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadAffinityPolicy(
    public var affinityTag: IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadAffinityPolicy> =
            object : TypeLayout<ThreadAffinityPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadAffinityPolicy =
                    ThreadAffinityPolicy(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadAffinityPolicy) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.affinityTag)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadAffinityPolicy =
                    ThreadAffinityPolicy(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadAffinityPolicy) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.affinityTag)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadBackgroundPolicy(
    public var priority: IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadBackgroundPolicy> =
            object : TypeLayout<ThreadBackgroundPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadBackgroundPolicy =
                    ThreadBackgroundPolicy(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadBackgroundPolicy) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.priority)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadBackgroundPolicy =
                    ThreadBackgroundPolicy(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadBackgroundPolicy) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.priority)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadLatencyQosPolicy(
    public var threadLatencyQosTier: ThreadLatencyQosT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadLatencyQosPolicy> =
            object : TypeLayout<ThreadLatencyQosPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadLatencyQosPolicy =
                    ThreadLatencyQosPolicy(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadLatencyQosPolicy) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.threadLatencyQosTier)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadLatencyQosPolicy =
                    ThreadLatencyQosPolicy(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadLatencyQosPolicy) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.threadLatencyQosTier)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadThroughputQosPolicy(
    public var threadThroughputQosTier: ThreadThroughputQosT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadThroughputQosPolicy> =
            object : TypeLayout<ThreadThroughputQosPolicy> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ThreadThroughputQosPolicy =
                    ThreadThroughputQosPolicy(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ThreadThroughputQosPolicy) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.threadThroughputQosTier)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadThroughputQosPolicy =
                    ThreadThroughputQosPolicy(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadThroughputQosPolicy) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.threadThroughputQosTier)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class MallocStatisticsT(
    public var blocksInUse: CUint = 0u,
    public var sizeInUse: SizeT = 0uL,
    public var maxSizeInUse: SizeT = 0uL,
    public var sizeAllocated: SizeT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MallocStatisticsT> =
            object : TypeLayout<MallocStatisticsT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): MallocStatisticsT =
                    MallocStatisticsT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: MallocStatisticsT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.blocksInUse)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.sizeInUse)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.maxSizeInUse)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.sizeAllocated)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MallocStatisticsT =
                    MallocStatisticsT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MallocStatisticsT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.blocksInUse)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.sizeInUse)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.maxSizeInUse)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.sizeAllocated)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Mstats(
    public var bytesTotal: SizeT = 0uL,
    public var chunksUsed: SizeT = 0uL,
    public var bytesUsed: SizeT = 0uL,
    public var chunksFree: SizeT = 0uL,
    public var bytesFree: SizeT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Mstats> =
            object : TypeLayout<Mstats> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): Mstats =
                    Mstats(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: Mstats) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.bytesTotal)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.chunksUsed)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.bytesUsed)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.chunksFree)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.bytesFree)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mstats =
                    Mstats(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mstats) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.bytesTotal)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.chunksUsed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.bytesUsed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.chunksFree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.bytesFree)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class VmRangeT(
    public var address: io.github.kotlinmania.rustlang.libc.VmAddressT = 0uL,
    public var size: io.github.kotlinmania.rustlang.libc.VmSizeT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<VmRangeT> =
            object : TypeLayout<VmRangeT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VmRangeT =
                    VmRangeT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: VmRangeT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.address)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VmRangeT =
                    VmRangeT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VmRangeT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.address)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.size)
                }
            }
    }
}

public class VinfoStat(
    public var vstDev: UInt = 0u,
    public var vstMode: UShort = 0u,
    public var vstNlink: UShort = 0u,
    public var vstIno: ULong = 0uL,
    public var vstUid: UidT = 0,
    public var vstGid: GidT = 0,
    public var vstAtime: Long = 0L,
    public var vstAtimensec: Long = 0L,
    public var vstMtime: Long = 0L,
    public var vstMtimensec: Long = 0L,
    public var vstCtime: Long = 0L,
    public var vstCtimensec: Long = 0L,
    public var vstBirthtime: Long = 0L,
    public var vstBirthtimensec: Long = 0L,
    public var vstSize: OffT = 0L,
    public var vstBlocks: Long = 0L,
    public var vstBlksize: Int = 0,
    public var vstFlags: UInt = 0u,
    public var vstGen: UInt = 0u,
    public var vstRdev: UInt = 0u,
    public var vstQspare: LongArray = LongArray(2),
) {
    public fun clone(): VinfoStat = VinfoStat(vstDev, vstMode, vstNlink, vstIno, vstUid, vstGid, vstAtime, vstAtimensec, vstMtime, vstMtimensec, vstCtime, vstCtimensec, vstBirthtime, vstBirthtimensec, vstSize, vstBlocks, vstBlksize, vstFlags, vstGen, vstRdev, vstQspare)

    public companion object {
        public fun typeLayout(): TypeLayout<VinfoStat> =
            object : TypeLayout<VinfoStat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 136L
                override fun readNative(addr: Long): VinfoStat =
                    VinfoStat(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I32).readNative(addr + 104L),
                        (PrimitiveLayouts.U32).readNative(addr + 108L),
                        (PrimitiveLayouts.U32).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 116L),
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: VinfoStat) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.vstDev)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.vstMode)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.vstNlink)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.vstIno)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.vstUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.vstGid)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.vstAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.vstAtimensec)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.vstMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.vstMtimensec)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.vstCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.vstCtimensec)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.vstBirthtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.vstBirthtimensec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.vstSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.vstBlocks)
                    (PrimitiveLayouts.I32).writeNative(addr + 104L, value.vstBlksize)
                    (PrimitiveLayouts.U32).writeNative(addr + 108L, value.vstFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 112L, value.vstGen)
                    (PrimitiveLayouts.U32).writeNative(addr + 116L, value.vstRdev)
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr + 120L, value.vstQspare)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VinfoStat =
                    VinfoStat(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 108),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 116),
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VinfoStat) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.vstDev)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.vstMode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.vstNlink)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.vstIno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.vstUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.vstGid)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.vstAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.vstAtimensec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.vstMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.vstMtimensec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.vstCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.vstCtimensec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.vstBirthtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.vstBirthtimensec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.vstSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.vstBlocks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 104, value.vstBlksize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 108, value.vstFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 112, value.vstGen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 116, value.vstRdev)
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset + 120, value.vstQspare)
                }
            }
    }
}

public data class VnodeInfo(
    public var viStat: VinfoStat = VinfoStat(),
    public var viType: CInt = 0,
    public var viPad: CInt = 0,
    public var viFsid: FsidT = FsidT(),
)

public data class VnodeInfoPath(
    public var vipVi: VnodeInfo = VnodeInfo(),
    // Normally it's `vipPath: [cChar; MAXPATHLEN]` but because libc supports an old rustc
    // version, we go around this limitation like this.
    public var vipPath: List<UByteArray> = List(32) { UByteArray(0) },
)

public data class ProcVnodepathinfo(
    public var pviCdir: VnodeInfoPath = VnodeInfoPath(),
    public var pviRdir: VnodeInfoPath = VnodeInfoPath(),
)

// Note(exhaustive): review for 1.0
public data class VmStatistics(
    public var freeCount: NaturalT = 0u,
    public var activeCount: NaturalT = 0u,
    public var inactiveCount: NaturalT = 0u,
    public var wireCount: NaturalT = 0u,
    public var zeroFillCount: NaturalT = 0u,
    public var reactivations: NaturalT = 0u,
    public var pageins: NaturalT = 0u,
    public var pageouts: NaturalT = 0u,
    public var faults: NaturalT = 0u,
    public var cowFaults: NaturalT = 0u,
    public var lookups: NaturalT = 0u,
    public var hits: NaturalT = 0u,
    public var purgeableCount: NaturalT = 0u,
    public var purges: NaturalT = 0u,
    public var speculativeCount: NaturalT = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<VmStatistics> =
            object : TypeLayout<VmStatistics> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 60L
                override fun readNative(addr: Long): VmStatistics =
                    VmStatistics(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
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
                    )
                override fun writeNative(addr: Long, value: VmStatistics) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.freeCount)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.activeCount)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.inactiveCount)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.wireCount)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.zeroFillCount)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.reactivations)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.pageins)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.pageouts)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.faults)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.cowFaults)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.lookups)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.hits)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.purgeableCount)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.purges)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.speculativeCount)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VmStatistics =
                    VmStatistics(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
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
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VmStatistics) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.freeCount)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.activeCount)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.inactiveCount)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.wireCount)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.zeroFillCount)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.reactivations)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.pageins)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.pageouts)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.faults)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.cowFaults)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.lookups)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.hits)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.purgeableCount)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.purges)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.speculativeCount)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class TaskThreadTimesInfo(
    public var userTime: TimeValueT = TimeValueT(),
    public var systemTime: TimeValueT = TimeValueT(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TaskThreadTimesInfo> =
            object : TypeLayout<TaskThreadTimesInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): TaskThreadTimesInfo =
                    TaskThreadTimesInfo(
                        (TimeValueT.typeLayout()).readNative(addr),
                        (TimeValueT.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: TaskThreadTimesInfo) {
                    (TimeValueT.typeLayout()).writeNative(addr, value.userTime)
                    (TimeValueT.typeLayout()).writeNative(addr + 8L, value.systemTime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TaskThreadTimesInfo =
                    TaskThreadTimesInfo(
                        (TimeValueT.typeLayout()).readBytes(buf, offset),
                        (TimeValueT.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TaskThreadTimesInfo) {
                    (TimeValueT.typeLayout()).writeBytes(buf, offset, value.userTime)
                    (TimeValueT.typeLayout()).writeBytes(buf, offset + 8, value.systemTime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class RusageInfoV0(
    public var riUuid: UByteArray = UByteArray(16),
    public var riUserTime: ULong = 0uL,
    public var riSystemTime: ULong = 0uL,
    public var riPkgIdleWkups: ULong = 0uL,
    public var riInterruptWkups: ULong = 0uL,
    public var riPageins: ULong = 0uL,
    public var riWiredSize: ULong = 0uL,
    public var riResidentSize: ULong = 0uL,
    public var riPhysFootprint: ULong = 0uL,
    public var riProcStartAbstime: ULong = 0uL,
    public var riProcExitAbstime: ULong = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RusageInfoV0) return false
        if (!this.riUuid.contentEquals(other.riUuid)) return false
        if (this.riUserTime != other.riUserTime) return false
        if (this.riSystemTime != other.riSystemTime) return false
        if (this.riPkgIdleWkups != other.riPkgIdleWkups) return false
        if (this.riInterruptWkups != other.riInterruptWkups) return false
        if (this.riPageins != other.riPageins) return false
        if (this.riWiredSize != other.riWiredSize) return false
        if (this.riResidentSize != other.riResidentSize) return false
        if (this.riPhysFootprint != other.riPhysFootprint) return false
        if (this.riProcStartAbstime != other.riProcStartAbstime) return false
        if (this.riProcExitAbstime != other.riProcExitAbstime) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.riUuid.contentHashCode()
        result = 31 * result + this.riUserTime.hashCode()
        result = 31 * result + this.riSystemTime.hashCode()
        result = 31 * result + this.riPkgIdleWkups.hashCode()
        result = 31 * result + this.riInterruptWkups.hashCode()
        result = 31 * result + this.riPageins.hashCode()
        result = 31 * result + this.riWiredSize.hashCode()
        result = 31 * result + this.riResidentSize.hashCode()
        result = 31 * result + this.riPhysFootprint.hashCode()
        result = 31 * result + this.riProcStartAbstime.hashCode()
        result = 31 * result + this.riProcExitAbstime.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RusageInfoV0> =
            object : TypeLayout<RusageInfoV0> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): RusageInfoV0 =
                    RusageInfoV0(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: RusageInfoV0) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.riUuid)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.riUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.riPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.riProcExitAbstime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RusageInfoV0 =
                    RusageInfoV0(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RusageInfoV0) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.riUuid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.riUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.riPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.riProcExitAbstime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class RusageInfoV1(
    public var riUuid: UByteArray = UByteArray(16),
    public var riUserTime: ULong = 0uL,
    public var riSystemTime: ULong = 0uL,
    public var riPkgIdleWkups: ULong = 0uL,
    public var riInterruptWkups: ULong = 0uL,
    public var riPageins: ULong = 0uL,
    public var riWiredSize: ULong = 0uL,
    public var riResidentSize: ULong = 0uL,
    public var riPhysFootprint: ULong = 0uL,
    public var riProcStartAbstime: ULong = 0uL,
    public var riProcExitAbstime: ULong = 0uL,
    public var riChildUserTime: ULong = 0uL,
    public var riChildSystemTime: ULong = 0uL,
    public var riChildPkgIdleWkups: ULong = 0uL,
    public var riChildInterruptWkups: ULong = 0uL,
    public var riChildPageins: ULong = 0uL,
    public var riChildElapsedAbstime: ULong = 0uL,
) {
    public fun clone(): RusageInfoV1 = RusageInfoV1(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime)

    public companion object {
        public fun typeLayout(): TypeLayout<RusageInfoV1> =
            object : TypeLayout<RusageInfoV1> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): RusageInfoV1 =
                    RusageInfoV1(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                    )
                override fun writeNative(addr: Long, value: RusageInfoV1) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.riUuid)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.riUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.riPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.riChildElapsedAbstime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RusageInfoV1 =
                    RusageInfoV1(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RusageInfoV1) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.riUuid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.riUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.riPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.riChildElapsedAbstime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class RusageInfoV2(
    public var riUuid: UByteArray = UByteArray(16),
    public var riUserTime: ULong = 0uL,
    public var riSystemTime: ULong = 0uL,
    public var riPkgIdleWkups: ULong = 0uL,
    public var riInterruptWkups: ULong = 0uL,
    public var riPageins: ULong = 0uL,
    public var riWiredSize: ULong = 0uL,
    public var riResidentSize: ULong = 0uL,
    public var riPhysFootprint: ULong = 0uL,
    public var riProcStartAbstime: ULong = 0uL,
    public var riProcExitAbstime: ULong = 0uL,
    public var riChildUserTime: ULong = 0uL,
    public var riChildSystemTime: ULong = 0uL,
    public var riChildPkgIdleWkups: ULong = 0uL,
    public var riChildInterruptWkups: ULong = 0uL,
    public var riChildPageins: ULong = 0uL,
    public var riChildElapsedAbstime: ULong = 0uL,
    public var riDiskioBytesread: ULong = 0uL,
    public var riDiskioByteswritten: ULong = 0uL,
) {
    public fun clone(): RusageInfoV2 = RusageInfoV2(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime, riDiskioBytesread, riDiskioByteswritten)

    public companion object {
        public fun typeLayout(): TypeLayout<RusageInfoV2> =
            object : TypeLayout<RusageInfoV2> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 160L
                override fun readNative(addr: Long): RusageInfoV2 =
                    RusageInfoV2(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                    )
                override fun writeNative(addr: Long, value: RusageInfoV2) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.riUuid)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.riUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.riPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.riChildElapsedAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.riDiskioBytesread)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.riDiskioByteswritten)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RusageInfoV2 =
                    RusageInfoV2(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RusageInfoV2) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.riUuid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.riUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.riPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.riChildElapsedAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.riDiskioBytesread)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.riDiskioByteswritten)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class RusageInfoV3(
    public var riUuid: UByteArray = UByteArray(16),
    public var riUserTime: ULong = 0uL,
    public var riSystemTime: ULong = 0uL,
    public var riPkgIdleWkups: ULong = 0uL,
    public var riInterruptWkups: ULong = 0uL,
    public var riPageins: ULong = 0uL,
    public var riWiredSize: ULong = 0uL,
    public var riResidentSize: ULong = 0uL,
    public var riPhysFootprint: ULong = 0uL,
    public var riProcStartAbstime: ULong = 0uL,
    public var riProcExitAbstime: ULong = 0uL,
    public var riChildUserTime: ULong = 0uL,
    public var riChildSystemTime: ULong = 0uL,
    public var riChildPkgIdleWkups: ULong = 0uL,
    public var riChildInterruptWkups: ULong = 0uL,
    public var riChildPageins: ULong = 0uL,
    public var riChildElapsedAbstime: ULong = 0uL,
    public var riDiskioBytesread: ULong = 0uL,
    public var riDiskioByteswritten: ULong = 0uL,
    public var riCpuTimeQosDefault: ULong = 0uL,
    public var riCpuTimeQosMaintenance: ULong = 0uL,
    public var riCpuTimeQosBackground: ULong = 0uL,
    public var riCpuTimeQosUtility: ULong = 0uL,
    public var riCpuTimeQosLegacy: ULong = 0uL,
    public var riCpuTimeQosUserInitiated: ULong = 0uL,
    public var riCpuTimeQosUserInteractive: ULong = 0uL,
    public var riBilledSystemTime: ULong = 0uL,
    public var riServicedSystemTime: ULong = 0uL,
) {
    public fun clone(): RusageInfoV3 = RusageInfoV3(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime, riDiskioBytesread, riDiskioByteswritten, riCpuTimeQosDefault, riCpuTimeQosMaintenance, riCpuTimeQosBackground, riCpuTimeQosUtility, riCpuTimeQosLegacy, riCpuTimeQosUserInitiated, riCpuTimeQosUserInteractive, riBilledSystemTime, riServicedSystemTime)

    public companion object {
        public fun typeLayout(): TypeLayout<RusageInfoV3> =
            object : TypeLayout<RusageInfoV3> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 232L
                override fun readNative(addr: Long): RusageInfoV3 =
                    RusageInfoV3(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                        (PrimitiveLayouts.U64).readNative(addr + 216L),
                        (PrimitiveLayouts.U64).readNative(addr + 224L),
                    )
                override fun writeNative(addr: Long, value: RusageInfoV3) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.riUuid)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.riUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.riPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.riChildElapsedAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.riDiskioBytesread)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.riDiskioByteswritten)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.riCpuTimeQosDefault)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.riCpuTimeQosMaintenance)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.riCpuTimeQosBackground)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.riCpuTimeQosUtility)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.riCpuTimeQosLegacy)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.riCpuTimeQosUserInitiated)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.riCpuTimeQosUserInteractive)
                    (PrimitiveLayouts.U64).writeNative(addr + 216L, value.riBilledSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 224L, value.riServicedSystemTime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RusageInfoV3 =
                    RusageInfoV3(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RusageInfoV3) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.riUuid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.riUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.riPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.riChildElapsedAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.riDiskioBytesread)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.riDiskioByteswritten)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.riCpuTimeQosDefault)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.riCpuTimeQosMaintenance)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.riCpuTimeQosBackground)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.riCpuTimeQosUtility)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.riCpuTimeQosLegacy)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.riCpuTimeQosUserInitiated)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.riCpuTimeQosUserInteractive)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.riBilledSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.riServicedSystemTime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class RusageInfoV4(
    public var riUuid: UByteArray = UByteArray(16),
    public var riUserTime: ULong = 0uL,
    public var riSystemTime: ULong = 0uL,
    public var riPkgIdleWkups: ULong = 0uL,
    public var riInterruptWkups: ULong = 0uL,
    public var riPageins: ULong = 0uL,
    public var riWiredSize: ULong = 0uL,
    public var riResidentSize: ULong = 0uL,
    public var riPhysFootprint: ULong = 0uL,
    public var riProcStartAbstime: ULong = 0uL,
    public var riProcExitAbstime: ULong = 0uL,
    public var riChildUserTime: ULong = 0uL,
    public var riChildSystemTime: ULong = 0uL,
    public var riChildPkgIdleWkups: ULong = 0uL,
    public var riChildInterruptWkups: ULong = 0uL,
    public var riChildPageins: ULong = 0uL,
    public var riChildElapsedAbstime: ULong = 0uL,
    public var riDiskioBytesread: ULong = 0uL,
    public var riDiskioByteswritten: ULong = 0uL,
    public var riCpuTimeQosDefault: ULong = 0uL,
    public var riCpuTimeQosMaintenance: ULong = 0uL,
    public var riCpuTimeQosBackground: ULong = 0uL,
    public var riCpuTimeQosUtility: ULong = 0uL,
    public var riCpuTimeQosLegacy: ULong = 0uL,
    public var riCpuTimeQosUserInitiated: ULong = 0uL,
    public var riCpuTimeQosUserInteractive: ULong = 0uL,
    public var riBilledSystemTime: ULong = 0uL,
    public var riServicedSystemTime: ULong = 0uL,
    public var riLogicalWrites: ULong = 0uL,
    public var riLifetimeMaxPhysFootprint: ULong = 0uL,
    public var riInstructions: ULong = 0uL,
    public var riCycles: ULong = 0uL,
    public var riBilledEnergy: ULong = 0uL,
    public var riServicedEnergy: ULong = 0uL,
    public var riIntervalMaxPhysFootprint: ULong = 0uL,
    public var riRunnableTime: ULong = 0uL,
) {
    public fun clone(): RusageInfoV4 = RusageInfoV4(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime, riDiskioBytesread, riDiskioByteswritten, riCpuTimeQosDefault, riCpuTimeQosMaintenance, riCpuTimeQosBackground, riCpuTimeQosUtility, riCpuTimeQosLegacy, riCpuTimeQosUserInitiated, riCpuTimeQosUserInteractive, riBilledSystemTime, riServicedSystemTime, riLogicalWrites, riLifetimeMaxPhysFootprint, riInstructions, riCycles, riBilledEnergy, riServicedEnergy, riIntervalMaxPhysFootprint, riRunnableTime)

    public companion object {
        public fun typeLayout(): TypeLayout<RusageInfoV4> =
            object : TypeLayout<RusageInfoV4> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 296L
                override fun readNative(addr: Long): RusageInfoV4 =
                    RusageInfoV4(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                        (PrimitiveLayouts.U64).readNative(addr + 216L),
                        (PrimitiveLayouts.U64).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 232L),
                        (PrimitiveLayouts.U64).readNative(addr + 240L),
                        (PrimitiveLayouts.U64).readNative(addr + 248L),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                        (PrimitiveLayouts.U64).readNative(addr + 272L),
                        (PrimitiveLayouts.U64).readNative(addr + 280L),
                        (PrimitiveLayouts.U64).readNative(addr + 288L),
                    )
                override fun writeNative(addr: Long, value: RusageInfoV4) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.riUuid)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.riUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.riPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.riChildElapsedAbstime)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.riDiskioBytesread)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.riDiskioByteswritten)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.riCpuTimeQosDefault)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.riCpuTimeQosMaintenance)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.riCpuTimeQosBackground)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.riCpuTimeQosUtility)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.riCpuTimeQosLegacy)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.riCpuTimeQosUserInitiated)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.riCpuTimeQosUserInteractive)
                    (PrimitiveLayouts.U64).writeNative(addr + 216L, value.riBilledSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 224L, value.riServicedSystemTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 232L, value.riLogicalWrites)
                    (PrimitiveLayouts.U64).writeNative(addr + 240L, value.riLifetimeMaxPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 248L, value.riInstructions)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.riCycles)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.riBilledEnergy)
                    (PrimitiveLayouts.U64).writeNative(addr + 272L, value.riServicedEnergy)
                    (PrimitiveLayouts.U64).writeNative(addr + 280L, value.riIntervalMaxPhysFootprint)
                    (PrimitiveLayouts.U64).writeNative(addr + 288L, value.riRunnableTime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RusageInfoV4 =
                    RusageInfoV4(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 248),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 272),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 280),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 288),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RusageInfoV4) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.riUuid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.riUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.riSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.riPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.riInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.riPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.riWiredSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.riResidentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.riPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.riProcStartAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.riProcExitAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.riChildUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.riChildSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.riChildPkgIdleWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.riChildInterruptWkups)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.riChildPageins)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.riChildElapsedAbstime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.riDiskioBytesread)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.riDiskioByteswritten)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.riCpuTimeQosDefault)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.riCpuTimeQosMaintenance)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.riCpuTimeQosBackground)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.riCpuTimeQosUtility)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.riCpuTimeQosLegacy)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.riCpuTimeQosUserInitiated)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.riCpuTimeQosUserInteractive)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.riBilledSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.riServicedSystemTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.riLogicalWrites)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.riLifetimeMaxPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 248, value.riInstructions)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.riCycles)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.riBilledEnergy)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 272, value.riServicedEnergy)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 280, value.riIntervalMaxPhysFootprint)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 288, value.riRunnableTime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ImageOffset(
    public var uuid: io.github.kotlinmania.rustlang.libc.UuidT = UByteArray(16),
    public var offset: UInt = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImageOffset) return false
        if (!this.uuid.contentEquals(other.uuid)) return false
        if (this.offset != other.offset) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.uuid.contentHashCode()
        result = 31 * result + this.offset.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ImageOffset> =
            object : TypeLayout<ImageOffset> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): ImageOffset =
                    ImageOffset(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: ImageOffset) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.uuid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.offset)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ImageOffset =
                    ImageOffset(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ImageOffset) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.uuid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.offset)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Attrlist(
    public var bitmapcount: CUshort = 0u,
    public var reserved: UShort = 0u,
    public var commonattr: AttrgroupT = 0u,
    public var volattr: AttrgroupT = 0u,
    public var dirattr: AttrgroupT = 0u,
    public var fileattr: AttrgroupT = 0u,
    public var forkattr: AttrgroupT = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Attrlist> =
            object : TypeLayout<Attrlist> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Attrlist =
                    Attrlist(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: Attrlist) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.bitmapcount)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.reserved)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.commonattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.volattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.dirattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.fileattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.forkattr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Attrlist =
                    Attrlist(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Attrlist) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.bitmapcount)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.reserved)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.commonattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.volattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.dirattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.fileattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.forkattr)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class AttrreferenceT(
    public var attrDataoffset: Int = 0,
    public var attrLength: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<AttrreferenceT> =
            object : TypeLayout<AttrreferenceT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): AttrreferenceT =
                    AttrreferenceT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: AttrreferenceT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.attrDataoffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.attrLength)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AttrreferenceT =
                    AttrreferenceT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AttrreferenceT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.attrDataoffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.attrLength)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class VolCapabilitiesAttrT(
    public var capabilities: VolCapabilitiesSetT = UIntArray(4),
    public var valid: VolCapabilitiesSetT = UIntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VolCapabilitiesAttrT) return false
        if (!this.capabilities.contentEquals(other.capabilities)) return false
        if (!this.valid.contentEquals(other.valid)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.capabilities.contentHashCode()
        result = 31 * result + this.valid.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<VolCapabilitiesAttrT> =
            object : TypeLayout<VolCapabilitiesAttrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): VolCapabilitiesAttrT =
                    VolCapabilitiesAttrT(
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: VolCapabilitiesAttrT) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.capabilities)
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr + 16L, value.valid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VolCapabilitiesAttrT =
                    VolCapabilitiesAttrT(
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VolCapabilitiesAttrT) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.capabilities)
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset + 16, value.valid)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class AttributeSetT(
    public var commonattr: AttrgroupT = 0u,
    public var volattr: AttrgroupT = 0u,
    public var dirattr: AttrgroupT = 0u,
    public var fileattr: AttrgroupT = 0u,
    public var forkattr: AttrgroupT = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<AttributeSetT> =
            object : TypeLayout<AttributeSetT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): AttributeSetT =
                    AttributeSetT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: AttributeSetT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.commonattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.volattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.dirattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.fileattr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.forkattr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AttributeSetT =
                    AttributeSetT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AttributeSetT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.commonattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.volattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.dirattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.fileattr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.forkattr)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class VolAttributesAttrT(
    public var validattr: AttributeSetT = AttributeSetT(),
    public var nativeattr: AttributeSetT = AttributeSetT(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<VolAttributesAttrT> =
            object : TypeLayout<VolAttributesAttrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): VolAttributesAttrT =
                    VolAttributesAttrT(
                        (AttributeSetT.typeLayout()).readNative(addr),
                        (AttributeSetT.typeLayout()).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: VolAttributesAttrT) {
                    (AttributeSetT.typeLayout()).writeNative(addr, value.validattr)
                    (AttributeSetT.typeLayout()).writeNative(addr + 20L, value.nativeattr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VolAttributesAttrT =
                    VolAttributesAttrT(
                        (AttributeSetT.typeLayout()).readBytes(buf, offset),
                        (AttributeSetT.typeLayout()).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VolAttributesAttrT) {
                    (AttributeSetT.typeLayout()).writeBytes(buf, offset, value.validattr)
                    (AttributeSetT.typeLayout()).writeBytes(buf, offset + 20, value.nativeattr)
                }
            }
    }
}

public class TcpConnectionInfo(
    public var tcpiState: UByte = 0u,
    public var tcpiSndWscale: UByte = 0u,
    public var tcpiRcvWscale: UByte = 0u,
    public var pad1: UByte = 0u,
    public var tcpiOptions: UInt = 0u,
    public var tcpiFlags: UInt = 0u,
    public var tcpiRto: UInt = 0u,
    public var tcpiMaxseg: UInt = 0u,
    public var tcpiSndSsthresh: UInt = 0u,
    public var tcpiSndCwnd: UInt = 0u,
    public var tcpiSndWnd: UInt = 0u,
    public var tcpiSndSbbytes: UInt = 0u,
    public var tcpiRcvWnd: UInt = 0u,
    public var tcpiRttcur: UInt = 0u,
    public var tcpiSrtt: UInt = 0u,
    public var tcpiRttvar: UInt = 0u,
    public var tcpiTfoCookieReq: UInt = 0u,
    public var tcpiTfoCookieRcv: UInt = 0u,
    public var tcpiTfoSynLoss: UInt = 0u,
    public var tcpiTfoSynDataSent: UInt = 0u,
    public var tcpiTfoSynDataAcked: UInt = 0u,
    public var tcpiTfoSynDataRcv: UInt = 0u,
    public var tcpiTfoCookieReqRcv: UInt = 0u,
    public var tcpiTfoCookieSent: UInt = 0u,
    public var tcpiTfoCookieInvalid: UInt = 0u,
    public var tcpiTfoCookieWrong: UInt = 0u,
    public var tcpiTfoNoCookieRcv: UInt = 0u,
    public var tcpiTfoHeuristicsDisable: UInt = 0u,
    public var tcpiTfoSendBlackhole: UInt = 0u,
    public var tcpiTfoRecvBlackhole: UInt = 0u,
    public var tcpiTfoOnebyteProxy: UInt = 0u,
    public var pad2: UInt = 0u,
    public var tcpiTxpackets: ULong = 0uL,
    public var tcpiTxbytes: ULong = 0uL,
    public var tcpiTxretransmitbytes: ULong = 0uL,
    public var tcpiRxpackets: ULong = 0uL,
    public var tcpiRxbytes: ULong = 0uL,
    public var tcpiRxoutoforderbytes: ULong = 0uL,
    public var tcpiRxretransmitpackets: ULong = 0uL,
    public var nonExhaustive: Unit = Unit,
) {
    public fun clone(): TcpConnectionInfo = TcpConnectionInfo(tcpiState, tcpiSndWscale, tcpiRcvWscale, pad1, tcpiOptions, tcpiFlags, tcpiRto, tcpiMaxseg, tcpiSndSsthresh, tcpiSndCwnd, tcpiSndWnd, tcpiSndSbbytes, tcpiRcvWnd, tcpiRttcur, tcpiSrtt, tcpiRttvar, tcpiTfoCookieReq, tcpiTfoCookieRcv, tcpiTfoSynLoss, tcpiTfoSynDataSent, tcpiTfoSynDataAcked, tcpiTfoSynDataRcv, tcpiTfoCookieReqRcv, tcpiTfoCookieSent, tcpiTfoCookieInvalid, tcpiTfoCookieWrong, tcpiTfoNoCookieRcv, tcpiTfoHeuristicsDisable, tcpiTfoSendBlackhole, tcpiTfoRecvBlackhole, tcpiTfoOnebyteProxy, pad2, tcpiTxpackets, tcpiTxbytes, tcpiTxretransmitbytes, tcpiRxpackets, tcpiRxbytes, tcpiRxoutoforderbytes, tcpiRxretransmitpackets, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<TcpConnectionInfo> =
            object : TypeLayout<TcpConnectionInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 176L
                override fun readNative(addr: Long): TcpConnectionInfo =
                    TcpConnectionInfo(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
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
                        (PrimitiveLayouts.U32).readNative(addr + 104L),
                        (PrimitiveLayouts.U32).readNative(addr + 108L),
                        (PrimitiveLayouts.U32).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 176L),
                    )
                override fun writeNative(addr: Long, value: TcpConnectionInfo) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.tcpiState)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.tcpiSndWscale)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.tcpiRcvWscale)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.pad1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.tcpiOptions)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tcpiFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.tcpiRto)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.tcpiMaxseg)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.tcpiSndSsthresh)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.tcpiSndCwnd)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.tcpiSndWnd)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.tcpiSndSbbytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.tcpiRcvWnd)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.tcpiRttcur)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.tcpiSrtt)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.tcpiRttvar)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.tcpiTfoCookieReq)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.tcpiTfoCookieRcv)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.tcpiTfoSynLoss)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.tcpiTfoSynDataSent)
                    (PrimitiveLayouts.U32).writeNative(addr + 68L, value.tcpiTfoSynDataAcked)
                    (PrimitiveLayouts.U32).writeNative(addr + 72L, value.tcpiTfoSynDataRcv)
                    (PrimitiveLayouts.U32).writeNative(addr + 76L, value.tcpiTfoCookieReqRcv)
                    (PrimitiveLayouts.U32).writeNative(addr + 80L, value.tcpiTfoCookieSent)
                    (PrimitiveLayouts.U32).writeNative(addr + 84L, value.tcpiTfoCookieInvalid)
                    (PrimitiveLayouts.U32).writeNative(addr + 88L, value.tcpiTfoCookieWrong)
                    (PrimitiveLayouts.U32).writeNative(addr + 92L, value.tcpiTfoNoCookieRcv)
                    (PrimitiveLayouts.U32).writeNative(addr + 96L, value.tcpiTfoHeuristicsDisable)
                    (PrimitiveLayouts.U32).writeNative(addr + 100L, value.tcpiTfoSendBlackhole)
                    (PrimitiveLayouts.U32).writeNative(addr + 104L, value.tcpiTfoRecvBlackhole)
                    (PrimitiveLayouts.U32).writeNative(addr + 108L, value.tcpiTfoOnebyteProxy)
                    (PrimitiveLayouts.U32).writeNative(addr + 112L, value.pad2)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.tcpiTxpackets)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.tcpiTxbytes)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.tcpiTxretransmitbytes)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.tcpiRxpackets)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.tcpiRxbytes)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.tcpiRxoutoforderbytes)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.tcpiRxretransmitpackets)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 176L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TcpConnectionInfo =
                    TcpConnectionInfo(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
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
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 108),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 176),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TcpConnectionInfo) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.tcpiState)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.tcpiSndWscale)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.tcpiRcvWscale)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.pad1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.tcpiOptions)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tcpiFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.tcpiRto)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.tcpiMaxseg)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.tcpiSndSsthresh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.tcpiSndCwnd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.tcpiSndWnd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.tcpiSndSbbytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.tcpiRcvWnd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.tcpiRttcur)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.tcpiSrtt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.tcpiRttvar)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.tcpiTfoCookieReq)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.tcpiTfoCookieRcv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.tcpiTfoSynLoss)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.tcpiTfoSynDataSent)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 68, value.tcpiTfoSynDataAcked)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 72, value.tcpiTfoSynDataRcv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 76, value.tcpiTfoCookieReqRcv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 80, value.tcpiTfoCookieSent)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 84, value.tcpiTfoCookieInvalid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 88, value.tcpiTfoCookieWrong)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 92, value.tcpiTfoNoCookieRcv)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 96, value.tcpiTfoHeuristicsDisable)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 100, value.tcpiTfoSendBlackhole)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 104, value.tcpiTfoRecvBlackhole)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 108, value.tcpiTfoOnebyteProxy)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 112, value.pad2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.tcpiTxpackets)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.tcpiTxbytes)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.tcpiTxretransmitbytes)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.tcpiRxpackets)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.tcpiRxbytes)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.tcpiRxoutoforderbytes)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.tcpiRxretransmitpackets)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 176, value.nonExhaustive)
                }
            }
    }
}

public data class In6Addrlifetime(
    public var ia6tExpire: TimeT = 0L,
    public var ia6tPreferred: TimeT = 0L,
    public var ia6tVltime: UInt = 0u,
    public var ia6tPltime: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<In6Addrlifetime> =
            object : TypeLayout<In6Addrlifetime> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): In6Addrlifetime =
                    In6Addrlifetime(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: In6Addrlifetime) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.ia6tExpire)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.ia6tPreferred)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ia6tVltime)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.ia6tPltime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): In6Addrlifetime =
                    In6Addrlifetime(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: In6Addrlifetime) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.ia6tExpire)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.ia6tPreferred)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ia6tVltime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.ia6tPltime)
                }
            }
    }
}

public class In6Ifstat(
    public var ifs6InReceive: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InHdrerr: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InToobig: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InNoroute: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InAddrerr: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InProtounknown: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InTruncated: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InDiscard: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InDeliver: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutForward: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutRequest: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutDiscard: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutFragok: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutFragfail: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutFragcreat: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6ReassReqd: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6ReassOk: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6AtmfragRcvd: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6ReassFail: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InMcast: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutMcast: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6CantfowardIcmp6: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6AddrExpiryCnt: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6PfxExpiryCnt: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6DefrtrExpiryCnt: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
) {
    public fun clone(): In6Ifstat = In6Ifstat(ifs6InReceive, ifs6InHdrerr, ifs6InToobig, ifs6InNoroute, ifs6InAddrerr, ifs6InProtounknown, ifs6InTruncated, ifs6InDiscard, ifs6InDeliver, ifs6OutForward, ifs6OutRequest, ifs6OutDiscard, ifs6OutFragok, ifs6OutFragfail, ifs6OutFragcreat, ifs6ReassReqd, ifs6ReassOk, ifs6AtmfragRcvd, ifs6ReassFail, ifs6InMcast, ifs6OutMcast, ifs6CantfowardIcmp6, ifs6AddrExpiryCnt, ifs6PfxExpiryCnt, ifs6DefrtrExpiryCnt)

    public companion object {
        public fun typeLayout(): TypeLayout<In6Ifstat> =
            object : TypeLayout<In6Ifstat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 200L
                override fun readNative(addr: Long): In6Ifstat =
                    In6Ifstat(
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
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                    )
                override fun writeNative(addr: Long, value: In6Ifstat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.ifs6InReceive)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ifs6InHdrerr)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.ifs6InToobig)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.ifs6InNoroute)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.ifs6InAddrerr)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.ifs6InProtounknown)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.ifs6InTruncated)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.ifs6InDiscard)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.ifs6InDeliver)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.ifs6OutForward)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.ifs6OutRequest)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.ifs6OutDiscard)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.ifs6OutFragok)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.ifs6OutFragfail)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.ifs6OutFragcreat)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.ifs6ReassReqd)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.ifs6ReassOk)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.ifs6AtmfragRcvd)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.ifs6ReassFail)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.ifs6InMcast)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.ifs6OutMcast)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.ifs6CantfowardIcmp6)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.ifs6AddrExpiryCnt)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.ifs6PfxExpiryCnt)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.ifs6DefrtrExpiryCnt)
                }
                override fun readBytes(buf: ByteArray, offset: Int): In6Ifstat =
                    In6Ifstat(
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
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: In6Ifstat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.ifs6InReceive)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ifs6InHdrerr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.ifs6InToobig)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.ifs6InNoroute)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.ifs6InAddrerr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.ifs6InProtounknown)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.ifs6InTruncated)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.ifs6InDiscard)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.ifs6InDeliver)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.ifs6OutForward)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.ifs6OutRequest)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.ifs6OutDiscard)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.ifs6OutFragok)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.ifs6OutFragfail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.ifs6OutFragcreat)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.ifs6ReassReqd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.ifs6ReassOk)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.ifs6AtmfragRcvd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.ifs6ReassFail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.ifs6InMcast)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.ifs6OutMcast)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.ifs6CantfowardIcmp6)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.ifs6AddrExpiryCnt)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.ifs6PfxExpiryCnt)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.ifs6DefrtrExpiryCnt)
                }
            }
    }
}

public class Icmp6Ifstat(
    public var ifs6InMsg: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InError: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InDstunreach: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InAdminprohib: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InTimeexceed: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InParamprob: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InPkttoobig: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InEcho: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InEchoreply: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InRoutersolicit: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InRouteradvert: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InNeighborsolicit: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InNeighboradvert: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InRedirect: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InMldquery: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InMldreport: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6InMlddone: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutMsg: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutError: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutDstunreach: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutAdminprohib: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutTimeexceed: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutParamprob: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutPkttoobig: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutEcho: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutEchoreply: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutRoutersolicit: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutRouteradvert: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutNeighborsolicit: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutNeighboradvert: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutRedirect: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutMldquery: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutMldreport: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
    public var ifs6OutMlddone: io.github.kotlinmania.rustlang.libc.UQuadT = 0uL,
) {
    public fun clone(): Icmp6Ifstat = Icmp6Ifstat(ifs6InMsg, ifs6InError, ifs6InDstunreach, ifs6InAdminprohib, ifs6InTimeexceed, ifs6InParamprob, ifs6InPkttoobig, ifs6InEcho, ifs6InEchoreply, ifs6InRoutersolicit, ifs6InRouteradvert, ifs6InNeighborsolicit, ifs6InNeighboradvert, ifs6InRedirect, ifs6InMldquery, ifs6InMldreport, ifs6InMlddone, ifs6OutMsg, ifs6OutError, ifs6OutDstunreach, ifs6OutAdminprohib, ifs6OutTimeexceed, ifs6OutParamprob, ifs6OutPkttoobig, ifs6OutEcho, ifs6OutEchoreply, ifs6OutRoutersolicit, ifs6OutRouteradvert, ifs6OutNeighborsolicit, ifs6OutNeighboradvert, ifs6OutRedirect, ifs6OutMldquery, ifs6OutMldreport, ifs6OutMlddone)

    public companion object {
        public fun typeLayout(): TypeLayout<Icmp6Ifstat> =
            object : TypeLayout<Icmp6Ifstat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): Icmp6Ifstat =
                    Icmp6Ifstat(
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
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                        (PrimitiveLayouts.U64).readNative(addr + 216L),
                        (PrimitiveLayouts.U64).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 232L),
                        (PrimitiveLayouts.U64).readNative(addr + 240L),
                        (PrimitiveLayouts.U64).readNative(addr + 248L),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                    )
                override fun writeNative(addr: Long, value: Icmp6Ifstat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.ifs6InMsg)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ifs6InError)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.ifs6InDstunreach)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.ifs6InAdminprohib)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.ifs6InTimeexceed)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.ifs6InParamprob)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.ifs6InPkttoobig)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.ifs6InEcho)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.ifs6InEchoreply)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.ifs6InRoutersolicit)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.ifs6InRouteradvert)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.ifs6InNeighborsolicit)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.ifs6InNeighboradvert)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.ifs6InRedirect)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.ifs6InMldquery)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.ifs6InMldreport)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.ifs6InMlddone)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.ifs6OutMsg)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.ifs6OutError)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.ifs6OutDstunreach)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.ifs6OutAdminprohib)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.ifs6OutTimeexceed)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.ifs6OutParamprob)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.ifs6OutPkttoobig)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.ifs6OutEcho)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.ifs6OutEchoreply)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.ifs6OutRoutersolicit)
                    (PrimitiveLayouts.U64).writeNative(addr + 216L, value.ifs6OutRouteradvert)
                    (PrimitiveLayouts.U64).writeNative(addr + 224L, value.ifs6OutNeighborsolicit)
                    (PrimitiveLayouts.U64).writeNative(addr + 232L, value.ifs6OutNeighboradvert)
                    (PrimitiveLayouts.U64).writeNative(addr + 240L, value.ifs6OutRedirect)
                    (PrimitiveLayouts.U64).writeNative(addr + 248L, value.ifs6OutMldquery)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.ifs6OutMldreport)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.ifs6OutMlddone)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Icmp6Ifstat =
                    Icmp6Ifstat(
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
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 248),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Icmp6Ifstat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.ifs6InMsg)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ifs6InError)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.ifs6InDstunreach)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.ifs6InAdminprohib)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.ifs6InTimeexceed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.ifs6InParamprob)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.ifs6InPkttoobig)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.ifs6InEcho)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.ifs6InEchoreply)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.ifs6InRoutersolicit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.ifs6InRouteradvert)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.ifs6InNeighborsolicit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.ifs6InNeighboradvert)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.ifs6InRedirect)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.ifs6InMldquery)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.ifs6InMldreport)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.ifs6InMlddone)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.ifs6OutMsg)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.ifs6OutError)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.ifs6OutDstunreach)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.ifs6OutAdminprohib)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.ifs6OutTimeexceed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.ifs6OutParamprob)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.ifs6OutPkttoobig)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.ifs6OutEcho)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.ifs6OutEchoreply)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.ifs6OutRoutersolicit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.ifs6OutRouteradvert)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.ifs6OutNeighborsolicit)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.ifs6OutNeighboradvert)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.ifs6OutRedirect)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 248, value.ifs6OutMldquery)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.ifs6OutMldreport)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.ifs6OutMlddone)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class HostCpuLoadInfo(
    public var cpuTicks: UIntArray = UIntArray(CPU_STATE_MAX),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HostCpuLoadInfo) return false
        if (!this.cpuTicks.contentEquals(other.cpuTicks)) return false
        return true
    }

    override fun hashCode(): Int = this.cpuTicks.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<HostCpuLoadInfo> =
            object : TypeLayout<HostCpuLoadInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((CPU_STATE_MAX).toLong() * (PrimitiveLayouts.U32).strideBytes), 4L)
                override fun readNative(addr: Long): HostCpuLoadInfo =
                    HostCpuLoadInfo(
                        (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: HostCpuLoadInfo) {
                    (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).writeNative(addr, value.cpuTicks)
                }
                override fun readBytes(buf: ByteArray, offset: Int): HostCpuLoadInfo =
                    HostCpuLoadInfo(
                        (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: HostCpuLoadInfo) {
                    (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).writeBytes(buf, offset, value.cpuTicks)
                }
            }
    }
}

public data class Ifmibdata(
    /**
     * Name of interface
     */
    public var ifmdName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
    /**
     * Number of promiscuous listeners
     */
    public var ifmdPcount: CUint = 0u,
    /**
     * Interface flags
     */
    public var ifmdFlags: CUint = 0u,
    /**
     * Instantaneous length of send queue
     */
    public var ifmdSndLen: CUint = 0u,
    /**
     * Maximum length of send queue
     */
    public var ifmdSndMaxlen: CUint = 0u,
    /**
     * Number of drops in send queue
     */
    public var ifmdSndDrops: CUint = 0u,
    /**
     * For future expansion
     */
    public var ifmdFiller: UIntArray = UIntArray(4),
    /**
     * Generic information and statistics
     */
    public var ifmdData: IfData64 = IfData64(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ifmibdata) return false
        if (!this.ifmdName.contentEquals(other.ifmdName)) return false
        if (this.ifmdPcount != other.ifmdPcount) return false
        if (this.ifmdFlags != other.ifmdFlags) return false
        if (this.ifmdSndLen != other.ifmdSndLen) return false
        if (this.ifmdSndMaxlen != other.ifmdSndMaxlen) return false
        if (this.ifmdSndDrops != other.ifmdSndDrops) return false
        if (!this.ifmdFiller.contentEquals(other.ifmdFiller)) return false
        if (this.ifmdData != other.ifmdData) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifmdName.contentHashCode()
        result = 31 * result + this.ifmdPcount.hashCode()
        result = 31 * result + this.ifmdFlags.hashCode()
        result = 31 * result + this.ifmdSndLen.hashCode()
        result = 31 * result + this.ifmdSndMaxlen.hashCode()
        result = 31 * result + this.ifmdSndDrops.hashCode()
        result = 31 * result + this.ifmdFiller.contentHashCode()
        result = 31 * result + this.ifmdData.hashCode()
        return result
    }
}

public data class IfsIso88023(
    public var dot3StatsAlignmentErrors: UInt = 0u,
    public var dot3StatsFCSErrors: UInt = 0u,
    public var dot3StatsSingleCollisionFrames: UInt = 0u,
    public var dot3StatsMultipleCollisionFrames: UInt = 0u,
    public var dot3StatsSQETestErrors: UInt = 0u,
    public var dot3StatsDeferredTransmissions: UInt = 0u,
    public var dot3StatsLateCollisions: UInt = 0u,
    public var dot3StatsExcessiveCollisions: UInt = 0u,
    public var dot3StatsInternalMacTransmitErrors: UInt = 0u,
    public var dot3StatsCarrierSenseErrors: UInt = 0u,
    public var dot3StatsFrameTooLongs: UInt = 0u,
    public var dot3StatsInternalMacReceiveErrors: UInt = 0u,
    public var dot3StatsEtherChipSet: UInt = 0u,
    public var dot3StatsMissedFrames: UInt = 0u,
    public var dot3StatsCollFrequencies: UIntArray = UIntArray(16),
    public var dot3Compliance: UInt = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IfsIso88023) return false
        if (this.dot3StatsAlignmentErrors != other.dot3StatsAlignmentErrors) return false
        if (this.dot3StatsFCSErrors != other.dot3StatsFCSErrors) return false
        if (this.dot3StatsSingleCollisionFrames != other.dot3StatsSingleCollisionFrames) return false
        if (this.dot3StatsMultipleCollisionFrames != other.dot3StatsMultipleCollisionFrames) return false
        if (this.dot3StatsSQETestErrors != other.dot3StatsSQETestErrors) return false
        if (this.dot3StatsDeferredTransmissions != other.dot3StatsDeferredTransmissions) return false
        if (this.dot3StatsLateCollisions != other.dot3StatsLateCollisions) return false
        if (this.dot3StatsExcessiveCollisions != other.dot3StatsExcessiveCollisions) return false
        if (this.dot3StatsInternalMacTransmitErrors != other.dot3StatsInternalMacTransmitErrors) return false
        if (this.dot3StatsCarrierSenseErrors != other.dot3StatsCarrierSenseErrors) return false
        if (this.dot3StatsFrameTooLongs != other.dot3StatsFrameTooLongs) return false
        if (this.dot3StatsInternalMacReceiveErrors != other.dot3StatsInternalMacReceiveErrors) return false
        if (this.dot3StatsEtherChipSet != other.dot3StatsEtherChipSet) return false
        if (this.dot3StatsMissedFrames != other.dot3StatsMissedFrames) return false
        if (!this.dot3StatsCollFrequencies.contentEquals(other.dot3StatsCollFrequencies)) return false
        if (this.dot3Compliance != other.dot3Compliance) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dot3StatsAlignmentErrors.hashCode()
        result = 31 * result + this.dot3StatsFCSErrors.hashCode()
        result = 31 * result + this.dot3StatsSingleCollisionFrames.hashCode()
        result = 31 * result + this.dot3StatsMultipleCollisionFrames.hashCode()
        result = 31 * result + this.dot3StatsSQETestErrors.hashCode()
        result = 31 * result + this.dot3StatsDeferredTransmissions.hashCode()
        result = 31 * result + this.dot3StatsLateCollisions.hashCode()
        result = 31 * result + this.dot3StatsExcessiveCollisions.hashCode()
        result = 31 * result + this.dot3StatsInternalMacTransmitErrors.hashCode()
        result = 31 * result + this.dot3StatsCarrierSenseErrors.hashCode()
        result = 31 * result + this.dot3StatsFrameTooLongs.hashCode()
        result = 31 * result + this.dot3StatsInternalMacReceiveErrors.hashCode()
        result = 31 * result + this.dot3StatsEtherChipSet.hashCode()
        result = 31 * result + this.dot3StatsMissedFrames.hashCode()
        result = 31 * result + this.dot3StatsCollFrequencies.contentHashCode()
        result = 31 * result + this.dot3Compliance.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<IfsIso88023> =
            object : TypeLayout<IfsIso88023> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 124L
                override fun readNative(addr: Long): IfsIso88023 =
                    IfsIso88023(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
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
                        (PrimitiveArrayLayouts.uIntArray(16)).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: IfsIso88023) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.dot3StatsAlignmentErrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.dot3StatsFCSErrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.dot3StatsSingleCollisionFrames)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.dot3StatsMultipleCollisionFrames)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.dot3StatsSQETestErrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.dot3StatsDeferredTransmissions)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.dot3StatsLateCollisions)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.dot3StatsExcessiveCollisions)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.dot3StatsInternalMacTransmitErrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.dot3StatsCarrierSenseErrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.dot3StatsFrameTooLongs)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.dot3StatsInternalMacReceiveErrors)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.dot3StatsEtherChipSet)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.dot3StatsMissedFrames)
                    (PrimitiveArrayLayouts.uIntArray(16)).writeNative(addr + 56L, value.dot3StatsCollFrequencies)
                    (PrimitiveLayouts.U32).writeNative(addr + 120L, value.dot3Compliance)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IfsIso88023 =
                    IfsIso88023(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
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
                        (PrimitiveArrayLayouts.uIntArray(16)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IfsIso88023) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.dot3StatsAlignmentErrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.dot3StatsFCSErrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.dot3StatsSingleCollisionFrames)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.dot3StatsMultipleCollisionFrames)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dot3StatsSQETestErrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.dot3StatsDeferredTransmissions)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.dot3StatsLateCollisions)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.dot3StatsExcessiveCollisions)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.dot3StatsInternalMacTransmitErrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.dot3StatsCarrierSenseErrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.dot3StatsFrameTooLongs)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.dot3StatsInternalMacReceiveErrors)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.dot3StatsEtherChipSet)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.dot3StatsMissedFrames)
                    (PrimitiveArrayLayouts.uIntArray(16)).writeBytes(buf, offset + 56, value.dot3StatsCollFrequencies)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 120, value.dot3Compliance)
                }
            }
    }
}

public data class CtlInfo(
    public var ctlId: UInt = 0u,
    public var ctlName: UByteArray = UByteArray(MAX_KCTL_NAME),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CtlInfo) return false
        if (this.ctlId != other.ctlId) return false
        if (!this.ctlName.contentEquals(other.ctlName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ctlId.hashCode()
        result = 31 * result + this.ctlName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<CtlInfo> =
            object : TypeLayout<CtlInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes((4L + ((MAX_KCTL_NAME).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                override fun readNative(addr: Long): CtlInfo =
                    CtlInfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((MAX_KCTL_NAME).toInt())).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CtlInfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ctlId)
                    (PrimitiveArrayLayouts.uByteArray((MAX_KCTL_NAME).toInt())).writeNative(addr + 4L, value.ctlName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CtlInfo =
                    CtlInfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((MAX_KCTL_NAME).toInt())).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CtlInfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ctlId)
                    (PrimitiveArrayLayouts.uByteArray((MAX_KCTL_NAME).toInt())).writeBytes(buf, offset + 4, value.ctlName)
                }
            }
    }
}

public data class ProcFdinfo(
    public var procFd: Int = 0,
    public var procFdtype: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ProcFdinfo> =
            object : TypeLayout<ProcFdinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): ProcFdinfo =
                    ProcFdinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: ProcFdinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.procFd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.procFdtype)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcFdinfo =
                    ProcFdinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcFdinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.procFd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.procFdtype)
                }
            }
    }
}

public class Kevent internal constructor(
    public var ident: UintptrT,
    public var filter: Short,
    public var flags: UShort,
    public var fflags: UInt,
    public var data: IntptrT,
    udataCtorArg: MutPtr,
) {
    public var udata: MutPtr = udataCtorArg
        internal set

    public constructor() : this(
        ident = 0uL,
        filter = 0,
        flags = 0u,
        fflags = 0u,
        data = 0L,
        udataCtorArg = MutPtr(0L),
    )

    public operator fun component1(): UintptrT = this.ident

    public operator fun component2(): Short = this.filter

    public operator fun component3(): UShort = this.flags

    public operator fun component4(): UInt = this.fflags

    public operator fun component5(): IntptrT = this.data

    public operator fun component6(): MutPtr = this.udata

    public fun copy(ident: UintptrT = this.ident, filter: Short = this.filter, flags: UShort = this.flags, fflags: UInt = this.fflags, data: IntptrT = this.data, udata: MutPtr = this.udata): Kevent = Kevent(ident = ident, filter = filter, flags = flags, fflags = fflags, data = data, udataCtorArg = udata)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Kevent) return false
        if (this.ident != other.ident) return false
        if (this.filter != other.filter) return false
        if (this.flags != other.flags) return false
        if (this.fflags != other.fflags) return false
        if (this.data != other.data) return false
        if (this.udata != other.udata) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ident.hashCode()
        result = 31 * result + this.filter.hashCode()
        result = 31 * result + this.flags.hashCode()
        result = 31 * result + this.fflags.hashCode()
        result = 31 * result + this.data.hashCode()
        result = 31 * result + this.udata.hashCode()
        return result
    }

    public fun clone(): Kevent = Kevent(ident, filter, flags, fflags, data, udata)

    public companion object {
        public fun typeLayout(): TypeLayout<Kevent> =
            object : TypeLayout<Kevent> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Kevent =
                    Kevent(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 10L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Kevent) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.ident)
                    (PrimitiveLayouts.I16).writeNative(addr + 8L, value.filter)
                    (PrimitiveLayouts.U16).writeNative(addr + 10L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.fflags)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.data)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.udata)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Kevent =
                    Kevent(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Kevent) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.ident)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 8, value.filter)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 10, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.fflags)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.data)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.udata)
                }
            }

        public operator fun invoke(
            ident: UintptrT = 0uL,
            filter: Short = 0,
            flags: UShort = 0u,
            fflags: UInt = 0u,
            data: IntptrT = 0L,
            udata: MutPtr = MutPtr(0L),
        ): Kevent = Kevent(
            ident = ident,
            filter = filter,
            flags = flags,
            fflags = fflags,
            data = data,
            udataCtorArg = udata,
        )
    }
}

public data class SemidDs(
    // Note the manpage shows different types than the system header.
    public var semPerm: IpcPerm = IpcPerm(),
    public var semBase: Int = 0,
    public var semNsems: CUshort = 0u,
    public var semOtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var semPad1: Int = 0,
    public var semCtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var semPad2: Int = 0,
    public var semPad3: IntArray = IntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SemidDs) return false
        if (this.semPerm != other.semPerm) return false
        if (this.semBase != other.semBase) return false
        if (this.semNsems != other.semNsems) return false
        if (this.semOtime != other.semOtime) return false
        if (this.semPad1 != other.semPad1) return false
        if (this.semCtime != other.semCtime) return false
        if (this.semPad2 != other.semPad2) return false
        if (!this.semPad3.contentEquals(other.semPad3)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.semPerm.hashCode()
        result = 31 * result + this.semBase.hashCode()
        result = 31 * result + this.semNsems.hashCode()
        result = 31 * result + this.semOtime.hashCode()
        result = 31 * result + this.semPad1.hashCode()
        result = 31 * result + this.semCtime.hashCode()
        result = 31 * result + this.semPad2.hashCode()
        result = 31 * result + this.semPad3.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SemidDs> =
            object : TypeLayout<SemidDs> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): SemidDs =
                    SemidDs(
                        (IpcPerm.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 28L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 44L),
                        (PrimitiveLayouts.I32).readNative(addr + 52L),
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: SemidDs) {
                    (IpcPerm.typeLayout()).writeNative(addr, value.semPerm)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.semBase)
                    (PrimitiveLayouts.U16).writeNative(addr + 28L, value.semNsems)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.semOtime)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.semPad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 44L, value.semCtime)
                    (PrimitiveLayouts.I32).writeNative(addr + 52L, value.semPad2)
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr + 56L, value.semPad3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SemidDs =
                    SemidDs(
                        (IpcPerm.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 52),
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SemidDs) {
                    (IpcPerm.typeLayout()).writeBytes(buf, offset, value.semPerm)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.semBase)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 28, value.semNsems)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.semOtime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.semPad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 44, value.semCtime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 52, value.semPad2)
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset + 56, value.semPad3)
                }
            }
    }
}

public class ShmidDs internal constructor(
    public var shmPerm: IpcPerm,
    public var shmSegsz: SizeT,
    public var shmLpid: PidT,
    public var shmCpid: PidT,
    public var shmNattch: io.github.kotlinmania.rustlang.libc.ShmattT,
    public var shmAtime: io.github.kotlinmania.rustlang.libc.TimeT,
    // Note(macos): 64-bit wrong align => wrong offset
    public var shmDtime: io.github.kotlinmania.rustlang.libc.TimeT,
    // Note(macos): 64-bit wrong align => wrong offset
    public var shmCtime: io.github.kotlinmania.rustlang.libc.TimeT,
    shmInternalCtorArg: MutPtr,
) {
    public var shmInternal: MutPtr = shmInternalCtorArg
        internal set

    public constructor() : this(
        shmPerm = IpcPerm(),
        shmSegsz = 0uL,
        shmLpid = 0,
        shmCpid = 0,
        shmNattch = 0u,
        shmAtime = 0L,
        shmDtime = 0L,
        shmCtime = 0L,
        shmInternalCtorArg = MutPtr(0L),
    )

    public operator fun component1(): IpcPerm = this.shmPerm

    public operator fun component2(): SizeT = this.shmSegsz

    public operator fun component3(): PidT = this.shmLpid

    public operator fun component4(): PidT = this.shmCpid

    public operator fun component5(): io.github.kotlinmania.rustlang.libc.ShmattT = this.shmNattch

    public operator fun component6(): io.github.kotlinmania.rustlang.libc.TimeT = this.shmAtime

    public operator fun component7(): io.github.kotlinmania.rustlang.libc.TimeT = this.shmDtime

    public operator fun component8(): io.github.kotlinmania.rustlang.libc.TimeT = this.shmCtime

    public operator fun component9(): MutPtr = this.shmInternal

    public fun copy(shmPerm: IpcPerm = this.shmPerm, shmSegsz: SizeT = this.shmSegsz, shmLpid: PidT = this.shmLpid, shmCpid: PidT = this.shmCpid, shmNattch: io.github.kotlinmania.rustlang.libc.ShmattT = this.shmNattch, shmAtime: io.github.kotlinmania.rustlang.libc.TimeT = this.shmAtime, shmDtime: io.github.kotlinmania.rustlang.libc.TimeT = this.shmDtime, shmCtime: io.github.kotlinmania.rustlang.libc.TimeT = this.shmCtime, shmInternal: MutPtr = this.shmInternal): ShmidDs = ShmidDs(shmPerm = shmPerm, shmSegsz = shmSegsz, shmLpid = shmLpid, shmCpid = shmCpid, shmNattch = shmNattch, shmAtime = shmAtime, shmDtime = shmDtime, shmCtime = shmCtime, shmInternalCtorArg = shmInternal)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShmidDs) return false
        if (this.shmPerm != other.shmPerm) return false
        if (this.shmSegsz != other.shmSegsz) return false
        if (this.shmLpid != other.shmLpid) return false
        if (this.shmCpid != other.shmCpid) return false
        if (this.shmNattch != other.shmNattch) return false
        if (this.shmAtime != other.shmAtime) return false
        if (this.shmDtime != other.shmDtime) return false
        if (this.shmCtime != other.shmCtime) return false
        if (this.shmInternal != other.shmInternal) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.shmPerm.hashCode()
        result = 31 * result + this.shmSegsz.hashCode()
        result = 31 * result + this.shmLpid.hashCode()
        result = 31 * result + this.shmCpid.hashCode()
        result = 31 * result + this.shmNattch.hashCode()
        result = 31 * result + this.shmAtime.hashCode()
        result = 31 * result + this.shmDtime.hashCode()
        result = 31 * result + this.shmCtime.hashCode()
        result = 31 * result + this.shmInternal.hashCode()
        return result
    }

    public fun clone(): ShmidDs = ShmidDs(shmPerm, shmSegsz, shmLpid, shmCpid, shmNattch, shmAtime, shmDtime, shmCtime, shmInternal)

    public companion object {
        public fun typeLayout(): TypeLayout<ShmidDs> =
            object : TypeLayout<ShmidDs> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 76L
                override fun readNative(addr: Long): ShmidDs =
                    ShmidDs(
                        (IpcPerm.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.U16).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 44L),
                        (PrimitiveLayouts.I64).readNative(addr + 52L),
                        (PrimitiveLayouts.I64).readNative(addr + 60L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 68L),
                    )
                override fun writeNative(addr: Long, value: ShmidDs) {
                    (IpcPerm.typeLayout()).writeNative(addr, value.shmPerm)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.shmSegsz)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.shmLpid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.shmCpid)
                    (PrimitiveLayouts.U16).writeNative(addr + 40L, value.shmNattch)
                    (PrimitiveLayouts.I64).writeNative(addr + 44L, value.shmAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 52L, value.shmDtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 60L, value.shmCtime)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 68L, value.shmInternal)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ShmidDs =
                    ShmidDs(
                        (IpcPerm.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 68),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ShmidDs) {
                    (IpcPerm.typeLayout()).writeBytes(buf, offset, value.shmPerm)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.shmSegsz)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.shmLpid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.shmCpid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 40, value.shmNattch)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 44, value.shmAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 52, value.shmDtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 60, value.shmCtime)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 68, value.shmInternal)
                }
            }

        public operator fun invoke(
            shmPerm: IpcPerm = IpcPerm(),
            shmSegsz: SizeT = 0uL,
            shmLpid: PidT = 0,
            shmCpid: PidT = 0,
            shmNattch: io.github.kotlinmania.rustlang.libc.ShmattT = 0u,
            shmAtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
            shmDtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
            shmCtime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
            shmInternal: MutPtr = MutPtr(0L),
        ): ShmidDs = ShmidDs(
            shmPerm = shmPerm,
            shmSegsz = shmSegsz,
            shmLpid = shmLpid,
            shmCpid = shmCpid,
            shmNattch = shmNattch,
            shmAtime = shmAtime,
            shmDtime = shmDtime,
            shmCtime = shmCtime,
            shmInternalCtorArg = shmInternal,
        )
    }
}

public data class ProcThreadinfo(
    public var pthUserTime: ULong = 0uL,
    public var pthSystemTime: ULong = 0uL,
    public var pthCpuUsage: Int = 0,
    public var pthPolicy: Int = 0,
    public var pthRunState: Int = 0,
    public var pthFlags: Int = 0,
    public var pthSleepTime: Int = 0,
    public var pthCurpri: Int = 0,
    public var pthPriority: Int = 0,
    public var pthMaxpriority: Int = 0,
    public var pthName: UByteArray = UByteArray(MAXTHREADNAMESIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProcThreadinfo) return false
        if (this.pthUserTime != other.pthUserTime) return false
        if (this.pthSystemTime != other.pthSystemTime) return false
        if (this.pthCpuUsage != other.pthCpuUsage) return false
        if (this.pthPolicy != other.pthPolicy) return false
        if (this.pthRunState != other.pthRunState) return false
        if (this.pthFlags != other.pthFlags) return false
        if (this.pthSleepTime != other.pthSleepTime) return false
        if (this.pthCurpri != other.pthCurpri) return false
        if (this.pthPriority != other.pthPriority) return false
        if (this.pthMaxpriority != other.pthMaxpriority) return false
        if (!this.pthName.contentEquals(other.pthName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pthUserTime.hashCode()
        result = 31 * result + this.pthSystemTime.hashCode()
        result = 31 * result + this.pthCpuUsage.hashCode()
        result = 31 * result + this.pthPolicy.hashCode()
        result = 31 * result + this.pthRunState.hashCode()
        result = 31 * result + this.pthFlags.hashCode()
        result = 31 * result + this.pthSleepTime.hashCode()
        result = 31 * result + this.pthCurpri.hashCode()
        result = 31 * result + this.pthPriority.hashCode()
        result = 31 * result + this.pthMaxpriority.hashCode()
        result = 31 * result + this.pthName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ProcThreadinfo> =
            object : TypeLayout<ProcThreadinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((48L + ((MAXTHREADNAMESIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): ProcThreadinfo =
                    ProcThreadinfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                        (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: ProcThreadinfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.pthUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.pthSystemTime)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pthCpuUsage)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pthPolicy)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.pthRunState)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.pthFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.pthSleepTime)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pthCurpri)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.pthPriority)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.pthMaxpriority)
                    (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).writeNative(addr + 48L, value.pthName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcThreadinfo =
                    ProcThreadinfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcThreadinfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.pthUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.pthSystemTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pthCpuUsage)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pthPolicy)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.pthRunState)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.pthFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.pthSleepTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pthCurpri)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.pthPriority)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.pthMaxpriority)
                    (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).writeBytes(buf, offset + 48, value.pthName)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Statfs(
    public var fBsize: UInt = 0u,
    public var fIosize: Int = 0,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fOwner: UidT = 0,
    public var fType: UInt = 0u,
    public var fFlags: UInt = 0u,
    public var fFssubtype: UInt = 0u,
    public var fFstypename: UByteArray = UByteArray(16),
    public var fMntonname: UByteArray = UByteArray(1024),
    public var fMntfromname: UByteArray = UByteArray(1024),
    public var fFlagsExt: UInt = 0u,
    public var fReserved: UIntArray = UIntArray(7),
) {
    public fun clone(): Statfs = Statfs(fBsize, fIosize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fOwner, fType, fFlags, fFssubtype, fFstypename, fMntonname, fMntfromname, fFlagsExt, fReserved)
}

// Note(exhaustive): review for 1.0
public data class Dirent(
    public var dIno: ULong = 0uL,
    public var dSeekoff: ULong = 0uL,
    public var dReclen: UShort = 0u,
    public var dNamlen: UShort = 0u,
    public var dType: UByte = 0u,
    public var dName: UByteArray = UByteArray(1024),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dirent) return false
        if (this.dIno != other.dIno) return false
        if (this.dSeekoff != other.dSeekoff) return false
        if (this.dReclen != other.dReclen) return false
        if (this.dNamlen != other.dNamlen) return false
        if (this.dType != other.dType) return false
        if (!this.dName.contentEquals(other.dName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dIno.hashCode()
        result = 31 * result + this.dSeekoff.hashCode()
        result = 31 * result + this.dReclen.hashCode()
        result = 31 * result + this.dNamlen.hashCode()
        result = 31 * result + this.dType.hashCode()
        result = 31 * result + this.dName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Dirent> =
            object : TypeLayout<Dirent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 1048L
                override fun readNative(addr: Long): Dirent =
                    Dirent(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 18L),
                        (PrimitiveLayouts.U8).readNative(addr + 20L),
                        (PrimitiveArrayLayouts.uByteArray(1024)).readNative(addr + 21L),
                    )
                override fun writeNative(addr: Long, value: Dirent) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.dSeekoff)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.dReclen)
                    (PrimitiveLayouts.U16).writeNative(addr + 18L, value.dNamlen)
                    (PrimitiveLayouts.U8).writeNative(addr + 20L, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(1024)).writeNative(addr + 21L, value.dName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent =
                    Dirent(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 20),
                        (PrimitiveArrayLayouts.uByteArray(1024)).readBytes(buf, offset + 21),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.dSeekoff)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.dReclen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 18, value.dNamlen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 20, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(1024)).writeBytes(buf, offset + 21, value.dName)
                }
            }
    }
}

public data class SockaddrStorage(
    public var ssLen: UByte = 0u,
    public var ssFamily: SaFamilyT = 0u,
    public var ssPad1: UByteArray = UByteArray(6),
    public var ssAlign: Long = 0L,
    public var ssPad2: UByteArray = UByteArray(112),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrStorage) return false
        if (this.ssLen != other.ssLen) return false
        if (this.ssFamily != other.ssFamily) return false
        if (!this.ssPad1.contentEquals(other.ssPad1)) return false
        if (this.ssAlign != other.ssAlign) return false
        if (!this.ssPad2.contentEquals(other.ssPad2)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ssLen.hashCode()
        result = 31 * result + this.ssFamily.hashCode()
        result = 31 * result + this.ssPad1.contentHashCode()
        result = 31 * result + this.ssAlign.hashCode()
        result = 31 * result + this.ssPad2.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrStorage> =
            object : TypeLayout<SockaddrStorage> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SockaddrStorage =
                    SockaddrStorage(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveArrayLayouts.uByteArray(6)).readNative(addr + 2L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(112)).readNative(addr + 16L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: SockaddrStorage) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.ssLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.ssFamily)
                    (PrimitiveArrayLayouts.uByteArray(6)).writeNative(addr + 2L, value.ssPad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.ssAlign)
                    (PrimitiveArrayLayouts.uByteArray(112)).writeNative(addr + 16L, value.ssPad2)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 128L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrStorage =
                    SockaddrStorage(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveArrayLayouts.uByteArray(6)).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(112)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrStorage) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.ssLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.ssFamily)
                    (PrimitiveArrayLayouts.uByteArray(6)).writeBytes(buf, offset + 2, value.ssPad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.ssAlign)
                    (PrimitiveArrayLayouts.uByteArray(112)).writeBytes(buf, offset + 16, value.ssPad2)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 128, value.nonExhaustive)
                }
            }
    }
}

public data class GroupReq(
    public var grInterface: UInt = 0u,
    public var grGroup: io.github.kotlinmania.rustlang.libc.SockaddrStorage = io.github.kotlinmania.rustlang.libc.SockaddrStorage(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<GroupReq> =
            object : TypeLayout<GroupReq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 132L
                override fun readNative(addr: Long): GroupReq =
                    GroupReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (SockaddrStorage.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: GroupReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.grInterface)
                    (SockaddrStorage.typeLayout()).writeNative(addr + 4L, value.grGroup)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GroupReq =
                    GroupReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (SockaddrStorage.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GroupReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.grInterface)
                    (SockaddrStorage.typeLayout()).writeBytes(buf, offset + 4, value.grGroup)
                }
            }
    }
}

public data class GroupSourceReq(
    public var gsrInterface: UInt = 0u,
    public var gsrGroup: io.github.kotlinmania.rustlang.libc.SockaddrStorage = io.github.kotlinmania.rustlang.libc.SockaddrStorage(),
    public var gsrSource: io.github.kotlinmania.rustlang.libc.SockaddrStorage = io.github.kotlinmania.rustlang.libc.SockaddrStorage(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<GroupSourceReq> =
            object : TypeLayout<GroupSourceReq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 260L
                override fun readNative(addr: Long): GroupSourceReq =
                    GroupSourceReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (SockaddrStorage.typeLayout()).readNative(addr + 4L),
                        (SockaddrStorage.typeLayout()).readNative(addr + 132L),
                    )
                override fun writeNative(addr: Long, value: GroupSourceReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.gsrInterface)
                    (SockaddrStorage.typeLayout()).writeNative(addr + 4L, value.gsrGroup)
                    (SockaddrStorage.typeLayout()).writeNative(addr + 132L, value.gsrSource)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GroupSourceReq =
                    GroupSourceReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (SockaddrStorage.typeLayout()).readBytes(buf, offset + 4),
                        (SockaddrStorage.typeLayout()).readBytes(buf, offset + 132),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GroupSourceReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.gsrInterface)
                    (SockaddrStorage.typeLayout()).writeBytes(buf, offset + 4, value.gsrGroup)
                    (SockaddrStorage.typeLayout()).writeBytes(buf, offset + 132, value.gsrSource)
                }
            }
    }
}

public data class Utmpx(
    public var utUser: UByteArray = UByteArray(UTX_USERSIZE),
    public var utId: UByteArray = UByteArray(UTX_IDSIZE),
    public var utLine: UByteArray = UByteArray(UTX_LINESIZE),
    public var utPid: PidT = 0,
    public var utType: CShort = 0,
    public var utTv: Timeval = Timeval(),
    public var utHost: UByteArray = UByteArray(UTX_HOSTSIZE),
    public var utPad: UIntArray = UIntArray(16),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utmpx) return false
        if (!this.utUser.contentEquals(other.utUser)) return false
        if (!this.utId.contentEquals(other.utId)) return false
        if (!this.utLine.contentEquals(other.utLine)) return false
        if (this.utPid != other.utPid) return false
        if (this.utType != other.utType) return false
        if (this.utTv != other.utTv) return false
        if (!this.utHost.contentEquals(other.utHost)) return false
        if (!this.utPad.contentEquals(other.utPad)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.utUser.contentHashCode()
        result = 31 * result + this.utId.contentHashCode()
        result = 31 * result + this.utLine.contentHashCode()
        result = 31 * result + this.utPid.hashCode()
        result = 31 * result + this.utType.hashCode()
        result = 31 * result + this.utTv.hashCode()
        result = 31 * result + this.utHost.contentHashCode()
        result = 31 * result + this.utPad.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }
}

public class Sigevent internal constructor(
    public var sigevNotify: CInt,
    public var sigevSigno: CInt,
    public var sigevValue: Sigval,
    unused1CtorArg: MutPtr,
    sigevNotifyAttributesCtorArg: MutPtr,
    public var nonExhaustive: Unit,
) {
    public var unused1: MutPtr = unused1CtorArg
        internal set

    public var sigevNotifyAttributes: MutPtr = sigevNotifyAttributesCtorArg
        internal set

    public constructor() : this(
        sigevNotify = 0,
        sigevSigno = 0,
        sigevValue = Sigval(),
        unused1CtorArg = MutPtr(0L),
        sigevNotifyAttributesCtorArg = MutPtr(0L),
        nonExhaustive = Unit,
    )

    public operator fun component1(): CInt = this.sigevNotify

    public operator fun component2(): CInt = this.sigevSigno

    public operator fun component3(): Sigval = this.sigevValue

    public operator fun component4(): MutPtr = this.unused1

    public operator fun component5(): MutPtr = this.sigevNotifyAttributes

    public operator fun component6(): Unit = this.nonExhaustive

    public fun copy(sigevNotify: CInt = this.sigevNotify, sigevSigno: CInt = this.sigevSigno, sigevValue: Sigval = this.sigevValue, unused1: MutPtr = this.unused1, sigevNotifyAttributes: MutPtr = this.sigevNotifyAttributes, nonExhaustive: Unit = this.nonExhaustive): Sigevent = Sigevent(sigevNotify = sigevNotify, sigevSigno = sigevSigno, sigevValue = sigevValue, unused1CtorArg = unused1, sigevNotifyAttributesCtorArg = sigevNotifyAttributes, nonExhaustive = nonExhaustive)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sigevent) return false
        if (this.sigevNotify != other.sigevNotify) return false
        if (this.sigevSigno != other.sigevSigno) return false
        if (this.sigevValue != other.sigevValue) return false
        if (this.unused1 != other.unused1) return false
        if (this.sigevNotifyAttributes != other.sigevNotifyAttributes) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sigevNotify.hashCode()
        result = 31 * result + this.sigevSigno.hashCode()
        result = 31 * result + this.sigevValue.hashCode()
        result = 31 * result + this.unused1.hashCode()
        result = 31 * result + this.sigevNotifyAttributes.hashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public fun clone(): Sigevent = Sigevent(sigevNotify, sigevSigno, sigevValue, unused1, sigevNotifyAttributes, nonExhaustive)

    public companion object {
        public operator fun invoke(
            sigevNotify: CInt = 0,
            sigevSigno: CInt = 0,
            sigevValue: Sigval = Sigval(),
            unused1: MutPtr = MutPtr(0L),
            sigevNotifyAttributes: MutPtr = MutPtr(0L),
            nonExhaustive: Unit = Unit,
        ): Sigevent = Sigevent(
            sigevNotify = sigevNotify,
            sigevSigno = sigevSigno,
            sigevValue = sigevValue,
            unused1CtorArg = unused1,
            sigevNotifyAttributesCtorArg = sigevNotifyAttributes,
            nonExhaustive = nonExhaustive,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class ProcessorCpuLoadInfo(
    public var cpuTicks: UIntArray = UIntArray(CPU_STATE_MAX),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProcessorCpuLoadInfo) return false
        if (!this.cpuTicks.contentEquals(other.cpuTicks)) return false
        return true
    }

    override fun hashCode(): Int = this.cpuTicks.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<ProcessorCpuLoadInfo> =
            object : TypeLayout<ProcessorCpuLoadInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((CPU_STATE_MAX).toLong() * (PrimitiveLayouts.U32).strideBytes), 4L)
                override fun readNative(addr: Long): ProcessorCpuLoadInfo =
                    ProcessorCpuLoadInfo(
                        (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ProcessorCpuLoadInfo) {
                    (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).writeNative(addr, value.cpuTicks)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcessorCpuLoadInfo =
                    ProcessorCpuLoadInfo(
                        (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcessorCpuLoadInfo) {
                    (PrimitiveArrayLayouts.uIntArray((CPU_STATE_MAX).toInt())).writeBytes(buf, offset, value.cpuTicks)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ProcessorBasicInfo(
    public var cpuType: CpuTypeT = 0,
    public var cpuSubtype: CpuSubtypeT = 0,
    public var running: BooleanT = 0,
    public var slotNum: CInt = 0,
    public var isMaster: BooleanT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ProcessorBasicInfo> =
            object : TypeLayout<ProcessorBasicInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): ProcessorBasicInfo =
                    ProcessorBasicInfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: ProcessorBasicInfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.cpuType)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.cpuSubtype)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.running)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.slotNum)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.isMaster)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcessorBasicInfo =
                    ProcessorBasicInfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcessorBasicInfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.cpuType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.cpuSubtype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.running)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.slotNum)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.isMaster)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ProcessorSetBasicInfo(
    public var processorCount: CInt = 0,
    public var defaultPolicy: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ProcessorSetBasicInfo> =
            object : TypeLayout<ProcessorSetBasicInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): ProcessorSetBasicInfo =
                    ProcessorSetBasicInfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: ProcessorSetBasicInfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.processorCount)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.defaultPolicy)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcessorSetBasicInfo =
                    ProcessorSetBasicInfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcessorSetBasicInfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.processorCount)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.defaultPolicy)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ProcessorSetLoadInfo(
    public var taskCount: CInt = 0,
    public var threadCount: CInt = 0,
    public var loadAverage: IntegerT = 0,
    public var machFactor: IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ProcessorSetLoadInfo> =
            object : TypeLayout<ProcessorSetLoadInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ProcessorSetLoadInfo =
                    ProcessorSetLoadInfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: ProcessorSetLoadInfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.taskCount)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.threadCount)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.loadAverage)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.machFactor)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcessorSetLoadInfo =
                    ProcessorSetLoadInfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcessorSetLoadInfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.taskCount)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.threadCount)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.loadAverage)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.machFactor)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class TimeValueT(
    public var seconds: IntegerT = 0,
    public var microseconds: IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<TimeValueT> =
            object : TypeLayout<TimeValueT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TimeValueT =
                    TimeValueT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: TimeValueT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.seconds)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.microseconds)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TimeValueT =
                    TimeValueT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TimeValueT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.seconds)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.microseconds)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadBasicInfo(
    public var userTime: TimeValueT = TimeValueT(),
    public var systemTime: TimeValueT = TimeValueT(),
    public var cpuUsage: io.github.kotlinmania.rustlang.libc.IntegerT = 0,
    public var policy: io.github.kotlinmania.rustlang.libc.PolicyT = 0,
    public var runState: io.github.kotlinmania.rustlang.libc.IntegerT = 0,
    public var flags: io.github.kotlinmania.rustlang.libc.IntegerT = 0,
    public var suspendCount: io.github.kotlinmania.rustlang.libc.IntegerT = 0,
    public var sleepTime: io.github.kotlinmania.rustlang.libc.IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadBasicInfo> =
            object : TypeLayout<ThreadBasicInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): ThreadBasicInfo =
                    ThreadBasicInfo(
                        (TimeValueT.typeLayout()).readNative(addr),
                        (TimeValueT.typeLayout()).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: ThreadBasicInfo) {
                    (TimeValueT.typeLayout()).writeNative(addr, value.userTime)
                    (TimeValueT.typeLayout()).writeNative(addr + 8L, value.systemTime)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.cpuUsage)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.policy)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.runState)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.flags)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.suspendCount)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.sleepTime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadBasicInfo =
                    ThreadBasicInfo(
                        (TimeValueT.typeLayout()).readBytes(buf, offset),
                        (TimeValueT.typeLayout()).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadBasicInfo) {
                    (TimeValueT.typeLayout()).writeBytes(buf, offset, value.userTime)
                    (TimeValueT.typeLayout()).writeBytes(buf, offset + 8, value.systemTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.cpuUsage)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.policy)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.runState)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.flags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.suspendCount)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.sleepTime)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadIdentifierInfo(
    public var threadId: ULong = 0uL,
    public var threadHandle: ULong = 0uL,
    public var dispatchQaddr: ULong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ThreadIdentifierInfo> =
            object : TypeLayout<ThreadIdentifierInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): ThreadIdentifierInfo =
                    ThreadIdentifierInfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: ThreadIdentifierInfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.threadId)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.threadHandle)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.dispatchQaddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadIdentifierInfo =
                    ThreadIdentifierInfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadIdentifierInfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.threadId)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.threadHandle)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.dispatchQaddr)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class ThreadExtendedInfo(
    public var pthUserTime: ULong = 0uL,
    public var pthSystemTime: ULong = 0uL,
    public var pthCpuUsage: Int = 0,
    public var pthPolicy: Int = 0,
    public var pthRunState: Int = 0,
    public var pthFlags: Int = 0,
    public var pthSleepTime: Int = 0,
    public var pthCurpri: Int = 0,
    public var pthPriority: Int = 0,
    public var pthMaxpriority: Int = 0,
    public var pthName: UByteArray = UByteArray(MAXTHREADNAMESIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ThreadExtendedInfo) return false
        if (this.pthUserTime != other.pthUserTime) return false
        if (this.pthSystemTime != other.pthSystemTime) return false
        if (this.pthCpuUsage != other.pthCpuUsage) return false
        if (this.pthPolicy != other.pthPolicy) return false
        if (this.pthRunState != other.pthRunState) return false
        if (this.pthFlags != other.pthFlags) return false
        if (this.pthSleepTime != other.pthSleepTime) return false
        if (this.pthCurpri != other.pthCurpri) return false
        if (this.pthPriority != other.pthPriority) return false
        if (this.pthMaxpriority != other.pthMaxpriority) return false
        if (!this.pthName.contentEquals(other.pthName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pthUserTime.hashCode()
        result = 31 * result + this.pthSystemTime.hashCode()
        result = 31 * result + this.pthCpuUsage.hashCode()
        result = 31 * result + this.pthPolicy.hashCode()
        result = 31 * result + this.pthRunState.hashCode()
        result = 31 * result + this.pthFlags.hashCode()
        result = 31 * result + this.pthSleepTime.hashCode()
        result = 31 * result + this.pthCurpri.hashCode()
        result = 31 * result + this.pthPriority.hashCode()
        result = 31 * result + this.pthMaxpriority.hashCode()
        result = 31 * result + this.pthName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ThreadExtendedInfo> =
            object : TypeLayout<ThreadExtendedInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((48L + ((MAXTHREADNAMESIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): ThreadExtendedInfo =
                    ThreadExtendedInfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                        (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: ThreadExtendedInfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.pthUserTime)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.pthSystemTime)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pthCpuUsage)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pthPolicy)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.pthRunState)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.pthFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.pthSleepTime)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pthCurpri)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.pthPriority)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.pthMaxpriority)
                    (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).writeNative(addr + 48L, value.pthName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ThreadExtendedInfo =
                    ThreadExtendedInfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ThreadExtendedInfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.pthUserTime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.pthSystemTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pthCpuUsage)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pthPolicy)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.pthRunState)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.pthFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.pthSleepTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pthCurpri)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.pthPriority)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.pthMaxpriority)
                    (PrimitiveArrayLayouts.uByteArray((MAXTHREADNAMESIZE).toInt())).writeBytes(buf, offset + 48, value.pthName)
                }
            }
    }
}

public class IfData64(
    public var ifiType: CUchar = 0u,
    public var ifiTypelen: CUchar = 0u,
    public var ifiPhysical: CUchar = 0u,
    public var ifiAddrlen: CUchar = 0u,
    public var ifiHdrlen: CUchar = 0u,
    public var ifiRecvquota: CUchar = 0u,
    public var ifiXmitquota: CUchar = 0u,
    public var ifiUnused1: CUchar = 0u,
    public var ifiMtu: UInt = 0u,
    public var ifiMetric: UInt = 0u,
    public var ifiBaudrate: ULong = 0uL,
    public var ifiIpackets: ULong = 0uL,
    public var ifiIerrors: ULong = 0uL,
    public var ifiOpackets: ULong = 0uL,
    public var ifiOerrors: ULong = 0uL,
    public var ifiCollisions: ULong = 0uL,
    public var ifiIbytes: ULong = 0uL,
    public var ifiObytes: ULong = 0uL,
    public var ifiImcasts: ULong = 0uL,
    public var ifiOmcasts: ULong = 0uL,
    public var ifiIqdrops: ULong = 0uL,
    public var ifiNoproto: ULong = 0uL,
    public var ifiRecvtiming: UInt = 0u,
    public var ifiXmittiming: UInt = 0u,
    public var ifiLastchange: Timeval32 = Timeval32(),
) {
    public fun clone(): IfData64 = IfData64(ifiType, ifiTypelen, ifiPhysical, ifiAddrlen, ifiHdrlen, ifiRecvquota, ifiXmitquota, ifiUnused1, ifiMtu, ifiMetric, ifiBaudrate, ifiIpackets, ifiIerrors, ifiOpackets, ifiOerrors, ifiCollisions, ifiIbytes, ifiObytes, ifiImcasts, ifiOmcasts, ifiIqdrops, ifiNoproto, ifiRecvtiming, ifiXmittiming, ifiLastchange)
}

public data class IfMsghdr2(
    public var ifmMsglen: CUshort = 0u,
    public var ifmVersion: CUchar = 0u,
    public var ifmType: CUchar = 0u,
    public var ifmAddrs: CInt = 0,
    public var ifmFlags: CInt = 0,
    public var ifmIndex: CUshort = 0u,
    public var ifmSndLen: CInt = 0,
    public var ifmSndMaxlen: CInt = 0,
    public var ifmSndDrops: CInt = 0,
    public var ifmTimer: CInt = 0,
    public var ifmData: IfData64 = IfData64(),
)

public data class MachTaskBasicInfo(
    public var virtualSize: MachVmSizeT = 0uL,
    public var residentSize: MachVmSizeT = 0uL,
    public var residentSizeMax: MachVmSizeT = 0uL,
    public var userTime: TimeValueT = TimeValueT(),
    public var systemTime: TimeValueT = TimeValueT(),
    public var policy: io.github.kotlinmania.rustlang.libc.PolicyT = 0,
    public var suspendCount: IntegerT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MachTaskBasicInfo> =
            object : TypeLayout<MachTaskBasicInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): MachTaskBasicInfo =
                    MachTaskBasicInfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (TimeValueT.typeLayout()).readNative(addr + 24L),
                        (TimeValueT.typeLayout()).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                    )
                override fun writeNative(addr: Long, value: MachTaskBasicInfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.virtualSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.residentSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.residentSizeMax)
                    (TimeValueT.typeLayout()).writeNative(addr + 24L, value.userTime)
                    (TimeValueT.typeLayout()).writeNative(addr + 32L, value.systemTime)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.policy)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.suspendCount)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MachTaskBasicInfo =
                    MachTaskBasicInfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (TimeValueT.typeLayout()).readBytes(buf, offset + 24),
                        (TimeValueT.typeLayout()).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MachTaskBasicInfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.virtualSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.residentSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.residentSizeMax)
                    (TimeValueT.typeLayout()).writeBytes(buf, offset + 24, value.userTime)
                    (TimeValueT.typeLayout()).writeBytes(buf, offset + 32, value.systemTime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.policy)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.suspendCount)
                }
            }
    }
}

public data class Log2phys(
    public var l2pFlags: CUint = 0u,
    public var l2pContigbytes: OffT = 0L,
    public var l2pDevoffset: OffT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Log2phys> =
            object : TypeLayout<Log2phys> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): Log2phys =
                    Log2phys(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 4L),
                        (PrimitiveLayouts.I64).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Log2phys) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.l2pFlags)
                    (PrimitiveLayouts.I64).writeNative(addr + 4L, value.l2pContigbytes)
                    (PrimitiveLayouts.I64).writeNative(addr + 12L, value.l2pDevoffset)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Log2phys =
                    Log2phys(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Log2phys) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.l2pFlags)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 4, value.l2pContigbytes)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 12, value.l2pDevoffset)
                }
            }
    }
}

public data class OsUnfairLockS(
    public var osUnfairLockOpaque: UInt = 0u,
    public var nonExhaustive: Unit = Unit,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<OsUnfairLockS> =
            object : TypeLayout<OsUnfairLockS> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): OsUnfairLockS =
                    OsUnfairLockS(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: OsUnfairLockS) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.osUnfairLockOpaque)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 4L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OsUnfairLockS =
                    OsUnfairLockS(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OsUnfairLockS) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.osUnfairLockOpaque)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 4, value.nonExhaustive)
                }
            }
    }
}

public data class SockaddrVm(
    public var svmLen: CUchar = 0u,
    public var svmFamily: SaFamilyT = 0u,
    public var svmReserved1: CUshort = 0u,
    public var svmPort: CUint = 0u,
    public var svmCid: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrVm> =
            object : TypeLayout<SockaddrVm> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrVm) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.svmLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.svmFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.svmPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.svmCid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrVm) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.svmLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.svmFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.svmPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.svmCid)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Ifdevmtu(
    public var ifdmCurrent: CInt = 0,
    public var ifdmMin: CInt = 0,
    public var ifdmMax: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Ifdevmtu> =
            object : TypeLayout<Ifdevmtu> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Ifdevmtu =
                    Ifdevmtu(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Ifdevmtu) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.ifdmCurrent)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ifdmMin)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ifdmMax)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ifdevmtu =
                    Ifdevmtu(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ifdevmtu) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.ifdmCurrent)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ifdmMin)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ifdmMax)
                }
            }
    }
}

public data class Ifkpi(
    public var ifkModuleId: CUint = 0u,
    public var ifkType: CUint = 0u,
    public var ifkData: CAnonymousIfkData = CAnonymousIfkData(),
)

// Note(exhaustive): review for 1.0
public data class Ifreq(
    public var ifrName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
    public var ifrIfru: CAnonymousIfrIfru = CAnonymousIfrIfru(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ifreq) return false
        if (!this.ifrName.contentEquals(other.ifrName)) return false
        if (this.ifrIfru != other.ifrIfru) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifrName.contentHashCode()
        result = 31 * result + this.ifrIfru.hashCode()
        return result
    }
}

public data class Ifaliasreq(
    public var ifraName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
    public var ifraAddr: Sockaddr = Sockaddr(),
    public var ifraBroadaddr: Sockaddr = Sockaddr(),
    public var ifraMask: Sockaddr = Sockaddr(),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ifaliasreq) return false
        if (!this.ifraName.contentEquals(other.ifraName)) return false
        if (this.ifraAddr != other.ifraAddr) return false
        if (this.ifraBroadaddr != other.ifraBroadaddr) return false
        if (this.ifraMask != other.ifraMask) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifraName.contentHashCode()
        result = 31 * result + this.ifraAddr.hashCode()
        result = 31 * result + this.ifraBroadaddr.hashCode()
        result = 31 * result + this.ifraMask.hashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }
}

public data class In6Ifreq(
    public var ifrName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
    public var ifrIfru: CAnonymousIfrIfru6 = CAnonymousIfrIfru6(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is In6Ifreq) return false
        if (!this.ifrName.contentEquals(other.ifrName)) return false
        if (this.ifrIfru != other.ifrIfru) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifrName.contentHashCode()
        result = 31 * result + this.ifrIfru.hashCode()
        return result
    }
}

public data class Ifconf(
    public var ifcLen: CInt = 0,
    public var ifcIfcu: CAnonymousIfcIfcu = CAnonymousIfcIfcu(),
) {
    public fun eq(other: Ifconf): Boolean {
        val lenPtr1 = addrOf(this.ifcLen)
        val lenPtr2 = addrOf(other.ifcLen)
        val ifcuPtr1 = addrOf(this.ifcIfcu)
        val ifcuPtr2 = addrOf(other.ifcIfcu)
        // SAFETY: `ifconf` implements `Copy` so the reads are valid
        val len1 = lenPtr1.readUnaligned()
        val len2 = lenPtr2.readUnaligned()
        val ifcu1 = ifcuPtr1.readUnaligned()
        val ifcu2 = ifcuPtr2.readUnaligned()
        return len1 == len2 && ifcu1 == ifcu2
    }
}

public class CAnonymousIfkData internal constructor(
    ifkPtrCtorArg: MutPtr,
    public var ifkValue: CInt,
) {
    public var ifkPtr: MutPtr = ifkPtrCtorArg
        internal set

    public constructor() : this(
        ifkPtrCtorArg = MutPtr(0L),
        ifkValue = 0,
    )

    public operator fun component1(): MutPtr = this.ifkPtr

    public operator fun component2(): CInt = this.ifkValue

    public fun copy(ifkPtr: MutPtr = this.ifkPtr, ifkValue: CInt = this.ifkValue): CAnonymousIfkData = CAnonymousIfkData(ifkPtrCtorArg = ifkPtr, ifkValue = ifkValue)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIfkData) return false
        if (this.ifkPtr != other.ifkPtr) return false
        if (this.ifkValue != other.ifkValue) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifkPtr.hashCode()
        result = 31 * result + this.ifkValue.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ifk_data").finishNonExhaustive()
    }

    public fun eq(other: CAnonymousIfkData): Boolean = ((this.ifkPtr).rawAddress.toULong() == (other.ifkPtr).rawAddress.toULong()) && this.ifkValue == other.ifkValue

    public companion object {
        public operator fun invoke(
            ifkPtr: MutPtr = MutPtr(0L),
            ifkValue: CInt = 0,
        ): CAnonymousIfkData = CAnonymousIfkData(
            ifkPtrCtorArg = ifkPtr,
            ifkValue = ifkValue,
        )
    }
}

public class CAnonymousIfrIfru internal constructor(
    public var ifruAddr: Sockaddr,
    public var ifruDstaddr: Sockaddr,
    public var ifruBroadaddr: Sockaddr,
    public var ifruFlags: CShort,
    public var ifruMetrics: CInt,
    public var ifruMtu: CInt,
    public var ifruPhys: CInt,
    public var ifruMedia: CInt,
    public var ifruIntval: CInt,
    ifruDataCtorArg: MutPtr,
    public var ifruDevmtu: Ifdevmtu,
    public var ifruKpi: Ifkpi,
    public var ifruWakeFlags: UInt,
    public var ifruRouteRefcnt: UInt,
    public var ifruCap: IntArray,
    public var ifruFunctionalType: UInt,
) {
    public var ifruData: MutPtr = ifruDataCtorArg
        internal set

    public constructor() : this(
        ifruAddr = Sockaddr(),
        ifruDstaddr = Sockaddr(),
        ifruBroadaddr = Sockaddr(),
        ifruFlags = 0,
        ifruMetrics = 0,
        ifruMtu = 0,
        ifruPhys = 0,
        ifruMedia = 0,
        ifruIntval = 0,
        ifruDataCtorArg = MutPtr(0L),
        ifruDevmtu = Ifdevmtu(),
        ifruKpi = Ifkpi(),
        ifruWakeFlags = 0u,
        ifruRouteRefcnt = 0u,
        ifruCap = IntArray(2),
        ifruFunctionalType = 0u,
    )

    public operator fun component1(): Sockaddr = this.ifruAddr

    public operator fun component2(): Sockaddr = this.ifruDstaddr

    public operator fun component3(): Sockaddr = this.ifruBroadaddr

    public operator fun component4(): CShort = this.ifruFlags

    public operator fun component5(): CInt = this.ifruMetrics

    public operator fun component6(): CInt = this.ifruMtu

    public operator fun component7(): CInt = this.ifruPhys

    public operator fun component8(): CInt = this.ifruMedia

    public operator fun component9(): CInt = this.ifruIntval

    public operator fun component10(): MutPtr = this.ifruData

    public operator fun component11(): Ifdevmtu = this.ifruDevmtu

    public operator fun component12(): Ifkpi = this.ifruKpi

    public operator fun component13(): UInt = this.ifruWakeFlags

    public operator fun component14(): UInt = this.ifruRouteRefcnt

    public operator fun component15(): IntArray = this.ifruCap

    public operator fun component16(): UInt = this.ifruFunctionalType

    public fun copy(ifruAddr: Sockaddr = this.ifruAddr, ifruDstaddr: Sockaddr = this.ifruDstaddr, ifruBroadaddr: Sockaddr = this.ifruBroadaddr, ifruFlags: CShort = this.ifruFlags, ifruMetrics: CInt = this.ifruMetrics, ifruMtu: CInt = this.ifruMtu, ifruPhys: CInt = this.ifruPhys, ifruMedia: CInt = this.ifruMedia, ifruIntval: CInt = this.ifruIntval, ifruData: MutPtr = this.ifruData, ifruDevmtu: Ifdevmtu = this.ifruDevmtu, ifruKpi: Ifkpi = this.ifruKpi, ifruWakeFlags: UInt = this.ifruWakeFlags, ifruRouteRefcnt: UInt = this.ifruRouteRefcnt, ifruCap: IntArray = this.ifruCap, ifruFunctionalType: UInt = this.ifruFunctionalType): CAnonymousIfrIfru = CAnonymousIfrIfru(ifruAddr = ifruAddr, ifruDstaddr = ifruDstaddr, ifruBroadaddr = ifruBroadaddr, ifruFlags = ifruFlags, ifruMetrics = ifruMetrics, ifruMtu = ifruMtu, ifruPhys = ifruPhys, ifruMedia = ifruMedia, ifruIntval = ifruIntval, ifruDataCtorArg = ifruData, ifruDevmtu = ifruDevmtu, ifruKpi = ifruKpi, ifruWakeFlags = ifruWakeFlags, ifruRouteRefcnt = ifruRouteRefcnt, ifruCap = ifruCap, ifruFunctionalType = ifruFunctionalType)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIfrIfru) return false
        if (this.ifruAddr != other.ifruAddr) return false
        if (this.ifruDstaddr != other.ifruDstaddr) return false
        if (this.ifruBroadaddr != other.ifruBroadaddr) return false
        if (this.ifruFlags != other.ifruFlags) return false
        if (this.ifruMetrics != other.ifruMetrics) return false
        if (this.ifruMtu != other.ifruMtu) return false
        if (this.ifruPhys != other.ifruPhys) return false
        if (this.ifruMedia != other.ifruMedia) return false
        if (this.ifruIntval != other.ifruIntval) return false
        if (this.ifruData != other.ifruData) return false
        if (this.ifruDevmtu != other.ifruDevmtu) return false
        if (this.ifruKpi != other.ifruKpi) return false
        if (this.ifruWakeFlags != other.ifruWakeFlags) return false
        if (this.ifruRouteRefcnt != other.ifruRouteRefcnt) return false
        if (!this.ifruCap.contentEquals(other.ifruCap)) return false
        if (this.ifruFunctionalType != other.ifruFunctionalType) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifruAddr.hashCode()
        result = 31 * result + this.ifruDstaddr.hashCode()
        result = 31 * result + this.ifruBroadaddr.hashCode()
        result = 31 * result + this.ifruFlags.hashCode()
        result = 31 * result + this.ifruMetrics.hashCode()
        result = 31 * result + this.ifruMtu.hashCode()
        result = 31 * result + this.ifruPhys.hashCode()
        result = 31 * result + this.ifruMedia.hashCode()
        result = 31 * result + this.ifruIntval.hashCode()
        result = 31 * result + this.ifruData.hashCode()
        result = 31 * result + this.ifruDevmtu.hashCode()
        result = 31 * result + this.ifruKpi.hashCode()
        result = 31 * result + this.ifruWakeFlags.hashCode()
        result = 31 * result + this.ifruRouteRefcnt.hashCode()
        result = 31 * result + this.ifruCap.contentHashCode()
        result = 31 * result + this.ifruFunctionalType.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ifr_ifru").finishNonExhaustive()
    }

    public fun eq(other: CAnonymousIfrIfru): Boolean = this.ifruAddr == other.ifruAddr && this.ifruDstaddr == other.ifruDstaddr && this.ifruBroadaddr == other.ifruBroadaddr && this.ifruFlags == other.ifruFlags && this.ifruMetrics == other.ifruMetrics && this.ifruMtu == other.ifruMtu && this.ifruPhys == other.ifruPhys && this.ifruMedia == other.ifruMedia && this.ifruIntval == other.ifruIntval && ((this.ifruData).rawAddress.toULong() == (other.ifruData).rawAddress.toULong()) && this.ifruDevmtu == other.ifruDevmtu && this.ifruKpi == other.ifruKpi && this.ifruWakeFlags == other.ifruWakeFlags && this.ifruRouteRefcnt == other.ifruRouteRefcnt && (this.ifruCap.zip(other.ifruCap)).all({ (a, b) -> a == b }) && this.ifruFunctionalType == other.ifruFunctionalType

    public companion object {
        public operator fun invoke(
            ifruAddr: Sockaddr = Sockaddr(),
            ifruDstaddr: Sockaddr = Sockaddr(),
            ifruBroadaddr: Sockaddr = Sockaddr(),
            ifruFlags: CShort = 0,
            ifruMetrics: CInt = 0,
            ifruMtu: CInt = 0,
            ifruPhys: CInt = 0,
            ifruMedia: CInt = 0,
            ifruIntval: CInt = 0,
            ifruData: MutPtr = MutPtr(0L),
            ifruDevmtu: Ifdevmtu = Ifdevmtu(),
            ifruKpi: Ifkpi = Ifkpi(),
            ifruWakeFlags: UInt = 0u,
            ifruRouteRefcnt: UInt = 0u,
            ifruCap: IntArray = IntArray(2),
            ifruFunctionalType: UInt = 0u,
        ): CAnonymousIfrIfru = CAnonymousIfrIfru(
            ifruAddr = ifruAddr,
            ifruDstaddr = ifruDstaddr,
            ifruBroadaddr = ifruBroadaddr,
            ifruFlags = ifruFlags,
            ifruMetrics = ifruMetrics,
            ifruMtu = ifruMtu,
            ifruPhys = ifruPhys,
            ifruMedia = ifruMedia,
            ifruIntval = ifruIntval,
            ifruDataCtorArg = ifruData,
            ifruDevmtu = ifruDevmtu,
            ifruKpi = ifruKpi,
            ifruWakeFlags = ifruWakeFlags,
            ifruRouteRefcnt = ifruRouteRefcnt,
            ifruCap = ifruCap,
            ifruFunctionalType = ifruFunctionalType,
        )
    }
}

public class CAnonymousIfcIfcu internal constructor(
    ifcuBufCtorArg: MutPtr,
    ifcuReqCtorArg: MutPtr,
) {
    public var ifcuBuf: MutPtr = ifcuBufCtorArg
        internal set

    public var ifcuReq: MutPtr = ifcuReqCtorArg
        internal set

    public constructor() : this(
        ifcuBufCtorArg = MutPtr(0L),
        ifcuReqCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.ifcuBuf

    public operator fun component2(): MutPtr = this.ifcuReq

    public fun copy(ifcuBuf: MutPtr = this.ifcuBuf, ifcuReq: MutPtr = this.ifcuReq): CAnonymousIfcIfcu = CAnonymousIfcIfcu(ifcuBufCtorArg = ifcuBuf, ifcuReqCtorArg = ifcuReq)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIfcIfcu) return false
        if (this.ifcuBuf != other.ifcuBuf) return false
        if (this.ifcuReq != other.ifcuReq) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifcuBuf.hashCode()
        result = 31 * result + this.ifcuReq.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ifc_ifcu").finishNonExhaustive()
    }

    public fun eq(other: CAnonymousIfcIfcu): Boolean = ((this.ifcuBuf).rawAddress.toULong() == (other.ifcuBuf).rawAddress.toULong()) && ((this.ifcuReq).rawAddress.toULong() == (other.ifcuReq).rawAddress.toULong())

    public companion object {
        public operator fun invoke(
            ifcuBuf: MutPtr = MutPtr(0L),
            ifcuReq: MutPtr = MutPtr(0L),
        ): CAnonymousIfcIfcu = CAnonymousIfcIfcu(
            ifcuBufCtorArg = ifcuBuf,
            ifcuReqCtorArg = ifcuReq,
        )
    }
}

public class CAnonymousIfrIfru6 internal constructor(
    public var ifruAddr: SockaddrIn6,
    public var ifruDstaddr: SockaddrIn6,
    public var ifruFlags: CInt,
    public var ifruFlags6: CInt,
    public var ifruMetrics: CInt,
    public var ifruIntval: CInt,
    ifruDataCtorArg: MutPtr,
    public var ifruLifetime: In6Addrlifetime,
    public var ifruStat: In6Ifstat,
    public var ifruIcmp6stat: Icmp6Ifstat,
    public var ifruScopeId: UIntArray,
) {
    public var ifruData: MutPtr = ifruDataCtorArg
        internal set

    public constructor() : this(
        ifruAddr = SockaddrIn6(),
        ifruDstaddr = SockaddrIn6(),
        ifruFlags = 0,
        ifruFlags6 = 0,
        ifruMetrics = 0,
        ifruIntval = 0,
        ifruDataCtorArg = MutPtr(0L),
        ifruLifetime = In6Addrlifetime(),
        ifruStat = In6Ifstat(),
        ifruIcmp6stat = Icmp6Ifstat(),
        ifruScopeId = UIntArray(SCOPE6_ID_MAX.toInt()),
    )

    public operator fun component1(): SockaddrIn6 = this.ifruAddr

    public operator fun component2(): SockaddrIn6 = this.ifruDstaddr

    public operator fun component3(): CInt = this.ifruFlags

    public operator fun component4(): CInt = this.ifruFlags6

    public operator fun component5(): CInt = this.ifruMetrics

    public operator fun component6(): CInt = this.ifruIntval

    public operator fun component7(): MutPtr = this.ifruData

    public operator fun component8(): In6Addrlifetime = this.ifruLifetime

    public operator fun component9(): In6Ifstat = this.ifruStat

    public operator fun component10(): Icmp6Ifstat = this.ifruIcmp6stat

    public operator fun component11(): UIntArray = this.ifruScopeId

    public fun copy(ifruAddr: SockaddrIn6 = this.ifruAddr, ifruDstaddr: SockaddrIn6 = this.ifruDstaddr, ifruFlags: CInt = this.ifruFlags, ifruFlags6: CInt = this.ifruFlags6, ifruMetrics: CInt = this.ifruMetrics, ifruIntval: CInt = this.ifruIntval, ifruData: MutPtr = this.ifruData, ifruLifetime: In6Addrlifetime = this.ifruLifetime, ifruStat: In6Ifstat = this.ifruStat, ifruIcmp6stat: Icmp6Ifstat = this.ifruIcmp6stat, ifruScopeId: UIntArray = this.ifruScopeId): CAnonymousIfrIfru6 = CAnonymousIfrIfru6(ifruAddr = ifruAddr, ifruDstaddr = ifruDstaddr, ifruFlags = ifruFlags, ifruFlags6 = ifruFlags6, ifruMetrics = ifruMetrics, ifruIntval = ifruIntval, ifruDataCtorArg = ifruData, ifruLifetime = ifruLifetime, ifruStat = ifruStat, ifruIcmp6stat = ifruIcmp6stat, ifruScopeId = ifruScopeId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIfrIfru6) return false
        if (this.ifruAddr != other.ifruAddr) return false
        if (this.ifruDstaddr != other.ifruDstaddr) return false
        if (this.ifruFlags != other.ifruFlags) return false
        if (this.ifruFlags6 != other.ifruFlags6) return false
        if (this.ifruMetrics != other.ifruMetrics) return false
        if (this.ifruIntval != other.ifruIntval) return false
        if (this.ifruData != other.ifruData) return false
        if (this.ifruLifetime != other.ifruLifetime) return false
        if (this.ifruStat != other.ifruStat) return false
        if (this.ifruIcmp6stat != other.ifruIcmp6stat) return false
        if (!this.ifruScopeId.contentEquals(other.ifruScopeId)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifruAddr.hashCode()
        result = 31 * result + this.ifruDstaddr.hashCode()
        result = 31 * result + this.ifruFlags.hashCode()
        result = 31 * result + this.ifruFlags6.hashCode()
        result = 31 * result + this.ifruMetrics.hashCode()
        result = 31 * result + this.ifruIntval.hashCode()
        result = 31 * result + this.ifruData.hashCode()
        result = 31 * result + this.ifruLifetime.hashCode()
        result = 31 * result + this.ifruStat.hashCode()
        result = 31 * result + this.ifruIcmp6stat.hashCode()
        result = 31 * result + this.ifruScopeId.contentHashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ifr_ifru6").finishNonExhaustive()
    }

    public fun eq(other: CAnonymousIfrIfru6): Boolean = this.ifruAddr == other.ifruAddr && this.ifruDstaddr == other.ifruDstaddr && this.ifruFlags == other.ifruFlags && this.ifruFlags6 == other.ifruFlags6 && this.ifruMetrics == other.ifruMetrics && this.ifruIntval == other.ifruIntval && ((this.ifruData).rawAddress.toULong() == (other.ifruData).rawAddress.toULong()) && (this.ifruScopeId.zip(other.ifruScopeId)).all({ (a, b) -> a == b })

    public companion object {
        public operator fun invoke(
            ifruAddr: SockaddrIn6 = SockaddrIn6(),
            ifruDstaddr: SockaddrIn6 = SockaddrIn6(),
            ifruFlags: CInt = 0,
            ifruFlags6: CInt = 0,
            ifruMetrics: CInt = 0,
            ifruIntval: CInt = 0,
            ifruData: MutPtr = MutPtr(0L),
            ifruLifetime: In6Addrlifetime = In6Addrlifetime(),
            ifruStat: In6Ifstat = In6Ifstat(),
            ifruIcmp6stat: Icmp6Ifstat = Icmp6Ifstat(),
            ifruScopeId: UIntArray = UIntArray(SCOPE6_ID_MAX.toInt()),
        ): CAnonymousIfrIfru6 = CAnonymousIfrIfru6(
            ifruAddr = ifruAddr,
            ifruDstaddr = ifruDstaddr,
            ifruFlags = ifruFlags,
            ifruFlags6 = ifruFlags6,
            ifruMetrics = ifruMetrics,
            ifruIntval = ifruIntval,
            ifruDataCtorArg = ifruData,
            ifruLifetime = ifruLifetime,
            ifruStat = ifruStat,
            ifruIcmp6stat = ifruIcmp6stat,
            ifruScopeId = ifruScopeId,
        )
    }
}

public class Semun internal constructor(
    public var value: CInt,
    bufCtorArg: MutPtr,
    arrayCtorArg: MutPtr,
) {
    public var buf: MutPtr = bufCtorArg
        internal set

    public var array: MutPtr = arrayCtorArg
        internal set

    public constructor() : this(
        value = 0,
        bufCtorArg = MutPtr(0L),
        arrayCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CInt = this.value

    public operator fun component2(): MutPtr = this.buf

    public operator fun component3(): MutPtr = this.array

    public fun copy(value: CInt = this.value, buf: MutPtr = this.buf, array: MutPtr = this.array): Semun = Semun(value = value, bufCtorArg = buf, arrayCtorArg = array)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Semun) return false
        if (this.value != other.value) return false
        if (this.buf != other.buf) return false
        if (this.array != other.array) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.value.hashCode()
        result = 31 * result + this.buf.hashCode()
        result = 31 * result + this.array.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "semun").finishNonExhaustive()
    }

    public fun eq(other: Semun): Boolean = this.value == other.value

    public companion object {
        public operator fun invoke(
            value: CInt = 0,
            buf: MutPtr = MutPtr(0L),
            array: MutPtr = MutPtr(0L),
        ): Semun = Semun(
            value = value,
            bufCtorArg = buf,
            arrayCtorArg = array,
        )
    }
}
