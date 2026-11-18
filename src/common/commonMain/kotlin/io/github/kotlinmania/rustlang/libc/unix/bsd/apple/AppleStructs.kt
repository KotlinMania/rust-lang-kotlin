@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.IFNAMSIZ
import io.github.kotlinmania.rustlang.libc.unix.In6Addr
import io.github.kotlinmania.rustlang.libc.unix.InAddrT
import io.github.kotlinmania.rustlang.libc.unix.InPortT
import io.github.kotlinmania.rustlang.libc.unix.IntptrT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SighandlerT
import io.github.kotlinmania.rustlang.libc.unix.Sigval
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.Timespec
import io.github.kotlinmania.rustlang.libc.unix.Timeval
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.UintptrT
import io.github.kotlinmania.rustlang.libc.unix.bsd.BlkcntT
import io.github.kotlinmania.rustlang.libc.unix.bsd.FsidT
import io.github.kotlinmania.rustlang.libc.unix.bsd.NCCS
import io.github.kotlinmania.rustlang.libc.unix.bsd.OffT
import io.github.kotlinmania.rustlang.libc.unix.bsd.SaFamilyT
import io.github.kotlinmania.rustlang.libc.unix.bsd.Sockaddr
import io.github.kotlinmania.rustlang.libc.unix.bsd.SockaddrIn6
import io.github.kotlinmania.rustlang.libc.unix.bsd.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.IfData
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.Timeval32
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.aarch64.BooleanT

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
public open class Timezone(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): Timezone = Timezone(data, marker)
}

// Note(exhaustive): review for 1.0
public open class IpMreq(
    public var imrMultiaddr: InAddr,
    public var imrInterface: InAddr,
) {
    public open fun clone(): IpMreq = IpMreq(imrMultiaddr, imrInterface)

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

        public fun default(): IpMreq = IpMreq(imrMultiaddr = InAddr.default(), imrInterface = InAddr.default())
    }
}

// Note(exhaustive): review for 1.0
public open class IpMreqn(
    public var imrMultiaddr: InAddr,
    public var imrAddress: InAddr,
    public var imrIfindex: CInt,
) {
    public open fun clone(): IpMreqn = IpMreqn(imrMultiaddr, imrAddress, imrIfindex)

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

        public fun default(): IpMreqn = IpMreqn(imrMultiaddr = InAddr.default(), imrAddress = InAddr.default(), imrIfindex = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class IpMreqSource(
    public var imrMultiaddr: InAddr,
    public var imrSourceaddr: InAddr,
    public var imrInterface: InAddr,
) {
    public open fun clone(): IpMreqSource = IpMreqSource(imrMultiaddr, imrSourceaddr, imrInterface)

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

        public fun default(): IpMreqSource = IpMreqSource(imrMultiaddr = InAddr.default(), imrSourceaddr = InAddr.default(), imrInterface = InAddr.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Aiocb internal constructor(
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

    public open fun clone(): Aiocb = Aiocb(aioFildes, aioOffset, aioBuf, aioNbytes, aioReqprio, aioSigevent, aioLioOpcode)

    public companion object {
        public operator fun invoke(
            aioFildes: CInt,
            aioOffset: OffT,
            aioBuf: MutPtr,
            aioNbytes: SizeT,
            aioReqprio: CInt,
            aioSigevent: Sigevent,
            aioLioOpcode: CInt,
        ): Aiocb = Aiocb(
            aioFildes = aioFildes,
            aioOffset = aioOffset,
            aioBufCtorArg = aioBuf,
            aioNbytes = aioNbytes,
            aioReqprio = aioReqprio,
            aioSigevent = aioSigevent,
            aioLioOpcode = aioLioOpcode,
        )

        public fun default(): Aiocb = Aiocb(aioFildes = 0, aioOffset = 0L, aioBuf = MutPtr(0L), aioNbytes = 0uL, aioReqprio = 0, aioSigevent = Sigevent.default(), aioLioOpcode = 0)
    }
}

public open class GlobT internal constructor(
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

    public open fun clone(): GlobT = GlobT(glPathc, unused1, glOffs, unused2, glPathv, unused3, unused4, unused5, unused6, unused7, unused8, nonExhaustive)

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
            glPathc: SizeT,
            unused1: CInt,
            glOffs: SizeT,
            unused2: CInt,
            glPathv: MutPtr,
            unused3: MutPtr,
            unused4: MutPtr,
            unused5: MutPtr,
            unused6: MutPtr,
            unused7: MutPtr,
            unused8: MutPtr,
            nonExhaustive: Unit,
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

        public fun default(): GlobT = GlobT(glPathc = 0uL, unused1 = 0, glOffs = 0uL, unused2 = 0, glPathv = MutPtr(0L), unused3 = MutPtr(0L), unused4 = MutPtr(0L), unused5 = MutPtr(0L), unused6 = MutPtr(0L), unused7 = MutPtr(0L), unused8 = MutPtr(0L), nonExhaustive = Unit)
    }
}

// Note(exhaustive): review for 1.0
public open class Tm internal constructor(
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

    public open fun clone(): Tm = Tm(tmSec, tmMin, tmHour, tmMday, tmMon, tmYear, tmWday, tmYday, tmIsdst, tmGmtoff, tmZone)

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
            tmSec: CInt,
            tmMin: CInt,
            tmHour: CInt,
            tmMday: CInt,
            tmMon: CInt,
            tmYear: CInt,
            tmWday: CInt,
            tmYday: CInt,
            tmIsdst: CInt,
            tmGmtoff: CLong,
            tmZone: MutPtr,
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

        public fun default(): Tm = Tm(tmSec = 0, tmMin = 0, tmHour = 0, tmMday = 0, tmMon = 0, tmYear = 0, tmWday = 0, tmYday = 0, tmIsdst = 0, tmGmtoff = 0L, tmZone = MutPtr(0L))
    }
}

// Note(exhaustive): review for 1.0
public open class Addrinfo internal constructor(
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

    public open fun clone(): Addrinfo = Addrinfo(aiFlags, aiFamily, aiSocktype, aiProtocol, aiAddrlen, aiCanonname, aiAddr, aiNext)

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
            aiFlags: CInt,
            aiFamily: CInt,
            aiSocktype: CInt,
            aiProtocol: CInt,
            aiAddrlen: SocklenT,
            aiCanonname: MutPtr,
            aiAddr: MutPtr,
            aiNext: MutPtr,
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

        public fun default(): Addrinfo = Addrinfo(aiFlags = 0, aiFamily = 0, aiSocktype = 0, aiProtocol = 0, aiAddrlen = 0u, aiCanonname = MutPtr(0L), aiAddr = MutPtr(0L), aiNext = MutPtr(0L))
    }
}

public open class MachTimebaseInfo(
    public var numer: UInt,
    public var denom: UInt,
) {
    public open fun clone(): MachTimebaseInfo = MachTimebaseInfo(numer, denom)

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

        public fun default(): MachTimebaseInfo = MachTimebaseInfo(numer = 0u, denom = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class Stat(
    public var stDev: DevT,
    public var stMode: ModeT,
    public var stNlink: NlinkT,
    public var stIno: InoT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var stRdev: DevT,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var stBirthtime: TimeT,
    public var stBirthtimeNsec: CLong,
    public var stSize: OffT,
    public var stBlocks: BlkcntT,
    public var stBlksize: BlksizeT,
    public var stFlags: UInt,
    public var stGen: UInt,
    public var stLspare: Int,
    public var stQspare: LongArray,
) {
    public open fun clone(): Stat = Stat(stDev, stMode, stNlink, stIno, stUid, stGid, stRdev, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, stBirthtime, stBirthtimeNsec, stSize, stBlocks, stBlksize, stFlags, stGen, stLspare, stQspare)

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

        public fun default(): Stat = Stat(stDev = 0, stMode = 0u, stNlink = 0u, stIno = 0uL, stUid = 0u, stGid = 0u, stRdev = 0, stAtime = 0L, stAtimeNsec = 0L, stMtime = 0L, stMtimeNsec = 0L, stCtime = 0L, stCtimeNsec = 0L, stBirthtime = 0L, stBirthtimeNsec = 0L, stSize = 0L, stBlocks = 0L, stBlksize = 0, stFlags = 0u, stGen = 0u, stLspare = 0, stQspare = Default.default())
    }
}

public open class SiginfoT internal constructor(
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

    public open fun clone(): SiginfoT = SiginfoT(siSigno, siErrno, siCode, siPid, siUid, siStatus, siAddr, pad, nonExhaustive)

    public open fun siAddr(): MutPtr = this.siAddr

    public open fun siValue(): Sigval {
        open class SiginfoTimer(
            public var siSigno: CInt,
            public var siErrno: CInt,
            public var siCode: CInt,
            public var siPid: PidT,
            public var siUid: UidT,
            public var siStatus: CInt,
            public var siAddr: MutPtr,
            public var siValue: Sigval,
        )
        return (UnresolvedTypeLayout<SiginfoTimer>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).siValue
    }

    public open fun siPid(): PidT = this.siPid

    public open fun siUid(): UidT = this.siUid

    public open fun siStatus(): CInt = this.siStatus

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
            siSigno: CInt,
            siErrno: CInt,
            siCode: CInt,
            siPid: PidT,
            siUid: UidT,
            siStatus: CInt,
            siAddr: MutPtr,
            pad: IntArray,
            nonExhaustive: Unit,
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

        public fun default(): SiginfoT = SiginfoT(siSigno = 0, siErrno = 0, siCode = 0, siPid = 0, siUid = 0u, siStatus = 0, siAddr = MutPtr(0L), pad = Default.default(), nonExhaustive = Unit)
    }
}

// Note(exhaustive): review for 1.0
public open class Sigaction(
    // Note(union): this field is actually a union
    public var saSigaction: SighandlerT,
    public var saMask: SigsetT,
    public var saFlags: CInt,
) {
    public open fun clone(): Sigaction = Sigaction(saSigaction, saMask, saFlags)

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

        public fun default(): Sigaction = Sigaction(saSigaction = 0uL, saMask = SigsetT.default(), saFlags = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssSize: SizeT,
    public var ssFlags: CInt,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public open fun clone(): StackT = StackT(ssSp, ssSize, ssFlags)

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
            ssSp: MutPtr,
            ssSize: SizeT,
            ssFlags: CInt,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssSize = ssSize,
            ssFlags = ssFlags,
        )

        public fun default(): StackT = StackT(ssSp = MutPtr(0L), ssSize = 0uL, ssFlags = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class FstoreT(
    public var fstFlags: CUint,
    public var fstPosmode: CInt,
    public var fstOffset: OffT,
    public var fstLength: OffT,
    public var fstBytesalloc: OffT,
) {
    public open fun clone(): FstoreT = FstoreT(fstFlags, fstPosmode, fstOffset, fstLength, fstBytesalloc)

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

        public fun default(): FstoreT = FstoreT(fstFlags = 0u, fstPosmode = 0, fstOffset = 0L, fstLength = 0L, fstBytesalloc = 0L)
    }
}

// Note(exhaustive): review for 1.0
public open class FpunchholeT(
    public var fpFlags: CUint,
    // unused
    public var reserved: CUint,
    // (to maintain 8-byte alignment)
    public var fpOffset: OffT,
    // IN: start of the region
    public var fpLength: OffT,
) {
    public open fun clone(): FpunchholeT = FpunchholeT(fpFlags, reserved, fpOffset, fpLength)

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

        public fun default(): FpunchholeT = FpunchholeT(fpFlags = 0u, reserved = 0u, fpOffset = 0L, fpLength = 0L)
    }
}

// Note(exhaustive): review for 1.0
public open class FtrimactivefileT(
    public var ftaOffset: OffT,
    public var ftaLength: OffT,
) {
    public open fun clone(): FtrimactivefileT = FtrimactivefileT(ftaOffset, ftaLength)

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

        public fun default(): FtrimactivefileT = FtrimactivefileT(ftaOffset = 0L, ftaLength = 0L)
    }
}

// Note(exhaustive): review for 1.0
public open class FspecreadT(
    public var fsrFlags: CUint,
    public var reserved: CUint,
    public var fsrOffset: OffT,
    public var fsrLength: OffT,
) {
    public open fun clone(): FspecreadT = FspecreadT(fsrFlags, reserved, fsrOffset, fsrLength)

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

        public fun default(): FspecreadT = FspecreadT(fsrFlags = 0u, reserved = 0u, fsrOffset = 0L, fsrLength = 0L)
    }
}

// Note(exhaustive): review for 1.0
public open class Radvisory(
    public var raOffset: OffT,
    public var raCount: CInt,
) {
    public open fun clone(): Radvisory = Radvisory(raOffset, raCount)

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

        public fun default(): Radvisory = Radvisory(raOffset = 0L, raCount = 0)
    }
}

// Note(exhaustive): review for 1.0
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
) {
    public open fun clone(): Statvfs = Statvfs(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax)

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

        public fun default(): Statvfs = Statvfs(fBsize = 0uL, fFrsize = 0uL, fBlocks = 0u, fBfree = 0u, fBavail = 0u, fFiles = 0u, fFfree = 0u, fFavail = 0u, fFsid = 0uL, fFlag = 0uL, fNamemax = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class DlInfo internal constructor(
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

    public open fun clone(): DlInfo = DlInfo(dliFname, dliFbase, dliSname, dliSaddr)

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
            dliFname: ConstPtr,
            dliFbase: MutPtr,
            dliSname: ConstPtr,
            dliSaddr: MutPtr,
        ): DlInfo = DlInfo(
            dliFnameCtorArg = dliFname,
            dliFbaseCtorArg = dliFbase,
            dliSnameCtorArg = dliSname,
            dliSaddrCtorArg = dliSaddr,
        )

        public fun default(): DlInfo = DlInfo(dliFname = MutPtr(0L), dliFbase = MutPtr(0L), dliSname = MutPtr(0L), dliSaddr = MutPtr(0L))
    }
}

// Note(exhaustive): review for 1.0
public open class SockaddrIn(
    public var sinLen: UByte,
    public var sinFamily: SaFamilyT,
    public var sinPort: InPortT,
    public var sinAddr: InAddr,
    public var sinZero: UByteArray,
) {
    public open fun clone(): SockaddrIn = SockaddrIn(sinLen, sinFamily, sinPort, sinAddr, sinZero)

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

        public fun default(): SockaddrIn = SockaddrIn(sinLen = 0u, sinFamily = 0u, sinPort = 0u, sinAddr = InAddr.default(), sinZero = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Kevent64S(
    public var ident: ULong,
    public var filter: Short,
    public var flags: UShort,
    public var fflags: UInt,
    public var data: Long,
    public var udata: ULong,
    public var ext: ULongArray,
) {
    public open fun clone(): Kevent64S = Kevent64S(ident, filter, flags, fflags, data, udata, ext)

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

        public fun default(): Kevent64S = Kevent64S(ident = 0uL, filter = 0, flags = 0u, fflags = 0u, data = 0L, udata = 0uL, ext = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Dqblk(
    public var dqbBhardlimit: ULong,
    public var dqbBsoftlimit: ULong,
    public var dqbCurbytes: ULong,
    public var dqbIhardlimit: UInt,
    public var dqbIsoftlimit: UInt,
    public var dqbCurinodes: UInt,
    public var dqbBtime: UInt,
    public var dqbItime: UInt,
    public var dqbId: UInt,
    public var dqbSpare: UIntArray,
) {
    public open fun clone(): Dqblk = Dqblk(dqbBhardlimit, dqbBsoftlimit, dqbCurbytes, dqbIhardlimit, dqbIsoftlimit, dqbCurinodes, dqbBtime, dqbItime, dqbId, dqbSpare)

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

        public fun default(): Dqblk = Dqblk(dqbBhardlimit = 0uL, dqbBsoftlimit = 0uL, dqbCurbytes = 0uL, dqbIhardlimit = 0u, dqbIsoftlimit = 0u, dqbCurinodes = 0u, dqbBtime = 0u, dqbItime = 0u, dqbId = 0u, dqbSpare = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class IfMsghdr(
    public var ifmMsglen: CUshort,
    public var ifmVersion: CUchar,
    public var ifmType: CUchar,
    public var ifmAddrs: CInt,
    public var ifmFlags: CInt,
    public var ifmIndex: CUshort,
    public var ifmData: IfData,
) {
    public open fun clone(): IfMsghdr = IfMsghdr(ifmMsglen, ifmVersion, ifmType, ifmAddrs, ifmFlags, ifmIndex, ifmData)

    public companion object {
        public fun default(): IfMsghdr = IfMsghdr(ifmMsglen = 0u, ifmVersion = 0u, ifmType = 0u, ifmAddrs = 0, ifmFlags = 0, ifmIndex = 0u, ifmData = IfData.default())
    }
}

// Note(exhaustive): review for 1.0
public open class IfaMsghdr(
    public var ifamMsglen: CUshort,
    public var ifamVersion: CUchar,
    public var ifamType: CUchar,
    public var ifamAddrs: CInt,
    public var ifamFlags: CInt,
    public var ifamIndex: CUshort,
    public var ifamMetric: CInt,
) {
    public open fun clone(): IfaMsghdr = IfaMsghdr(ifamMsglen, ifamVersion, ifamType, ifamAddrs, ifamFlags, ifamIndex, ifamMetric)

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

        public fun default(): IfaMsghdr = IfaMsghdr(ifamMsglen = 0u, ifamVersion = 0u, ifamType = 0u, ifamAddrs = 0, ifamFlags = 0, ifamIndex = 0u, ifamMetric = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class IfmaMsghdr(
    public var ifmamMsglen: CUshort,
    public var ifmamVersion: CUchar,
    public var ifmamType: CUchar,
    public var ifmamAddrs: CInt,
    public var ifmamFlags: CInt,
    public var ifmamIndex: CUshort,
) {
    public open fun clone(): IfmaMsghdr = IfmaMsghdr(ifmamMsglen, ifmamVersion, ifmamType, ifmamAddrs, ifmamFlags, ifmamIndex)

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

        public fun default(): IfmaMsghdr = IfmaMsghdr(ifmamMsglen = 0u, ifmamVersion = 0u, ifmamType = 0u, ifmamAddrs = 0, ifmamFlags = 0, ifmamIndex = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class IfmaMsghdr2(
    public var ifmamMsglen: CUshort,
    public var ifmamVersion: CUchar,
    public var ifmamType: CUchar,
    public var ifmamAddrs: CInt,
    public var ifmamFlags: CInt,
    public var ifmamIndex: CUshort,
    public var ifmamRefcount: Int,
) {
    public open fun clone(): IfmaMsghdr2 = IfmaMsghdr2(ifmamMsglen, ifmamVersion, ifmamType, ifmamAddrs, ifmamFlags, ifmamIndex, ifmamRefcount)

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

        public fun default(): IfmaMsghdr2 = IfmaMsghdr2(ifmamMsglen = 0u, ifmamVersion = 0u, ifmamType = 0u, ifmamAddrs = 0, ifmamFlags = 0, ifmamIndex = 0u, ifmamRefcount = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class RtMetrics(
    public var rmxLocks: UInt,
    public var rmxMtu: UInt,
    public var rmxHopcount: UInt,
    public var rmxExpire: Int,
    public var rmxRecvpipe: UInt,
    public var rmxSendpipe: UInt,
    public var rmxSsthresh: UInt,
    public var rmxRtt: UInt,
    public var rmxRttvar: UInt,
    public var rmxPksent: UInt,
    /**
     * This field does not exist anymore, the u32 is now part of a resized
     * `rmxFiller` array.
     */
    public var rmxState: UInt,
    public var rmxFiller: UIntArray,
) {
    public open fun clone(): RtMetrics = RtMetrics(rmxLocks, rmxMtu, rmxHopcount, rmxExpire, rmxRecvpipe, rmxSendpipe, rmxSsthresh, rmxRtt, rmxRttvar, rmxPksent, rmxState, rmxFiller)

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

        public fun default(): RtMetrics = RtMetrics(rmxLocks = 0u, rmxMtu = 0u, rmxHopcount = 0u, rmxExpire = 0, rmxRecvpipe = 0u, rmxSendpipe = 0u, rmxSsthresh = 0u, rmxRtt = 0u, rmxRttvar = 0u, rmxPksent = 0u, rmxState = 0u, rmxFiller = Default.default())
    }
}

public open class RtMsghdr(
    public var rtmMsglen: CUshort,
    public var rtmVersion: CUchar,
    public var rtmType: CUchar,
    public var rtmIndex: CUshort,
    public var rtmFlags: CInt,
    public var rtmAddrs: CInt,
    public var rtmPid: PidT,
    public var rtmSeq: CInt,
    public var rtmErrno: CInt,
    public var rtmUse: CInt,
    public var rtmInits: UInt,
    public var rtmRmx: RtMetrics,
) {
    public open fun clone(): RtMsghdr = RtMsghdr(rtmMsglen, rtmVersion, rtmType, rtmIndex, rtmFlags, rtmAddrs, rtmPid, rtmSeq, rtmErrno, rtmUse, rtmInits, rtmRmx)

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

        public fun default(): RtMsghdr = RtMsghdr(rtmMsglen = 0u, rtmVersion = 0u, rtmType = 0u, rtmIndex = 0u, rtmFlags = 0, rtmAddrs = 0, rtmPid = 0, rtmSeq = 0, rtmErrno = 0, rtmUse = 0, rtmInits = 0u, rtmRmx = RtMetrics.default())
    }
}

public open class RtMsghdr2(
    public var rtmMsglen: CUshort,
    public var rtmVersion: CUchar,
    public var rtmType: CUchar,
    public var rtmIndex: CUshort,
    public var rtmFlags: CInt,
    public var rtmAddrs: CInt,
    public var rtmRefcnt: Int,
    public var rtmParentflags: CInt,
    public var rtmReserved: CInt,
    public var rtmUse: CInt,
    public var rtmInits: UInt,
    public var rtmRmx: RtMetrics,
) {
    public open fun clone(): RtMsghdr2 = RtMsghdr2(rtmMsglen, rtmVersion, rtmType, rtmIndex, rtmFlags, rtmAddrs, rtmRefcnt, rtmParentflags, rtmReserved, rtmUse, rtmInits, rtmRmx)

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

        public fun default(): RtMsghdr2 = RtMsghdr2(rtmMsglen = 0u, rtmVersion = 0u, rtmType = 0u, rtmIndex = 0u, rtmFlags = 0, rtmAddrs = 0, rtmRefcnt = 0, rtmParentflags = 0, rtmReserved = 0, rtmUse = 0, rtmInits = 0u, rtmRmx = RtMetrics.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Termios(
    public var cIflag: TcflagT,
    public var cOflag: TcflagT,
    public var cCflag: TcflagT,
    public var cLflag: TcflagT,
    public var cCc: UByteArray,
    public var cIspeed: SpeedT,
    public var cOspeed: SpeedT,
) {
    public open fun clone(): Termios = Termios(cIflag, cOflag, cCflag, cLflag, cCc, cIspeed, cOspeed)

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

        public fun default(): Termios = Termios(cIflag = 0uL, cOflag = 0uL, cCflag = 0uL, cLflag = 0uL, cCc = Default.default(), cIspeed = 0uL, cOspeed = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class Flock(
    public var lStart: OffT,
    public var lLen: OffT,
    public var lPid: PidT,
    public var lType: CShort,
    public var lWhence: CShort,
) {
    public open fun clone(): Flock = Flock(lStart, lLen, lPid, lType, lWhence)

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

        public fun default(): Flock = Flock(lStart = 0L, lLen = 0L, lPid = 0, lType = 0, lWhence = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class SfHdtr internal constructor(
    headersCtorArg: MutPtr,
    public var hdrCnt: CInt,
    trailersCtorArg: MutPtr,
    public var trlCnt: CInt,
) {
    public var headers: MutPtr = headersCtorArg
        internal set

    public var trailers: MutPtr = trailersCtorArg
        internal set

    public open fun clone(): SfHdtr = SfHdtr(headers, hdrCnt, trailers, trlCnt)

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
            headers: MutPtr,
            hdrCnt: CInt,
            trailers: MutPtr,
            trlCnt: CInt,
        ): SfHdtr = SfHdtr(
            headersCtorArg = headers,
            hdrCnt = hdrCnt,
            trailersCtorArg = trailers,
            trlCnt = trlCnt,
        )

        public fun default(): SfHdtr = SfHdtr(headers = MutPtr(0L), hdrCnt = 0, trailers = MutPtr(0L), trlCnt = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class Lconv internal constructor(
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

    public open fun clone(): Lconv = Lconv(decimalPoint, thousandsSep, grouping, intCurrSymbol, currencySymbol, monDecimalPoint, monThousandsSep, monGrouping, positiveSign, negativeSign, intFracDigits, fracDigits, pCsPrecedes, pSepBySpace, nCsPrecedes, nSepBySpace, pSignPosn, nSignPosn, intPCsPrecedes, intNCsPrecedes, intPSepBySpace, intNSepBySpace, intPSignPosn, intNSignPosn)

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
            decimalPoint: MutPtr,
            thousandsSep: MutPtr,
            grouping: MutPtr,
            intCurrSymbol: MutPtr,
            currencySymbol: MutPtr,
            monDecimalPoint: MutPtr,
            monThousandsSep: MutPtr,
            monGrouping: MutPtr,
            positiveSign: MutPtr,
            negativeSign: MutPtr,
            intFracDigits: CChar,
            fracDigits: CChar,
            pCsPrecedes: CChar,
            pSepBySpace: CChar,
            nCsPrecedes: CChar,
            nSepBySpace: CChar,
            pSignPosn: CChar,
            nSignPosn: CChar,
            intPCsPrecedes: CChar,
            intNCsPrecedes: CChar,
            intPSepBySpace: CChar,
            intNSepBySpace: CChar,
            intPSignPosn: CChar,
            intNSignPosn: CChar,
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

        public fun default(): Lconv = Lconv(decimalPoint = MutPtr(0L), thousandsSep = MutPtr(0L), grouping = MutPtr(0L), intCurrSymbol = MutPtr(0L), currencySymbol = MutPtr(0L), monDecimalPoint = MutPtr(0L), monThousandsSep = MutPtr(0L), monGrouping = MutPtr(0L), positiveSign = MutPtr(0L), negativeSign = MutPtr(0L), intFracDigits = 0u, fracDigits = 0u, pCsPrecedes = 0u, pSepBySpace = 0u, nCsPrecedes = 0u, nSepBySpace = 0u, pSignPosn = 0u, nSignPosn = 0u, intPCsPrecedes = 0u, intNCsPrecedes = 0u, intPSepBySpace = 0u, intNSepBySpace = 0u, intPSignPosn = 0u, intNSignPosn = 0u)
    }
}

public open class ProcTaskinfo(
    public var ptiVirtualSize: ULong,
    public var ptiResidentSize: ULong,
    public var ptiTotalUser: ULong,
    public var ptiTotalSystem: ULong,
    public var ptiThreadsUser: ULong,
    public var ptiThreadsSystem: ULong,
    public var ptiPolicy: Int,
    public var ptiFaults: Int,
    public var ptiPageins: Int,
    public var ptiCowFaults: Int,
    public var ptiMessagesSent: Int,
    public var ptiMessagesReceived: Int,
    public var ptiSyscallsMach: Int,
    public var ptiSyscallsUnix: Int,
    public var ptiCsw: Int,
    public var ptiThreadnum: Int,
    public var ptiNumrunning: Int,
    public var ptiPriority: Int,
) {
    public open fun clone(): ProcTaskinfo = ProcTaskinfo(ptiVirtualSize, ptiResidentSize, ptiTotalUser, ptiTotalSystem, ptiThreadsUser, ptiThreadsSystem, ptiPolicy, ptiFaults, ptiPageins, ptiCowFaults, ptiMessagesSent, ptiMessagesReceived, ptiSyscallsMach, ptiSyscallsUnix, ptiCsw, ptiThreadnum, ptiNumrunning, ptiPriority)

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

        public fun default(): ProcTaskinfo = ProcTaskinfo(ptiVirtualSize = 0uL, ptiResidentSize = 0uL, ptiTotalUser = 0uL, ptiTotalSystem = 0uL, ptiThreadsUser = 0uL, ptiThreadsSystem = 0uL, ptiPolicy = 0, ptiFaults = 0, ptiPageins = 0, ptiCowFaults = 0, ptiMessagesSent = 0, ptiMessagesReceived = 0, ptiSyscallsMach = 0, ptiSyscallsUnix = 0, ptiCsw = 0, ptiThreadnum = 0, ptiNumrunning = 0, ptiPriority = 0)
    }
}

public open class ProcBsdinfo(
    public var pbiFlags: UInt,
    public var pbiStatus: UInt,
    public var pbiXstatus: UInt,
    public var pbiPid: UInt,
    public var pbiPpid: UInt,
    public var pbiUid: UidT,
    public var pbiGid: GidT,
    public var pbiRuid: UidT,
    public var pbiRgid: GidT,
    public var pbiSvuid: UidT,
    public var pbiSvgid: GidT,
    public var rfu1: UInt,
    public var pbiComm: UByteArray,
    public var pbiName: UByteArray,
    // MAXCOMLEN * 2, but macro isn't happy...
    public var pbiNfiles: UInt,
    public var pbiPgid: UInt,
    public var pbiPjobc: UInt,
    public var eTdev: UInt,
    public var eTpgid: UInt,
    public var pbiNice: Int,
    public var pbiStartTvsec: ULong,
    public var pbiStartTvusec: ULong,
) {
    public open fun clone(): ProcBsdinfo = ProcBsdinfo(pbiFlags, pbiStatus, pbiXstatus, pbiPid, pbiPpid, pbiUid, pbiGid, pbiRuid, pbiRgid, pbiSvuid, pbiSvgid, rfu1, pbiComm, pbiName, pbiNfiles, pbiPgid, pbiPjobc, eTdev, eTpgid, pbiNice, pbiStartTvsec, pbiStartTvusec)

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

        public fun default(): ProcBsdinfo = ProcBsdinfo(pbiFlags = 0u, pbiStatus = 0u, pbiXstatus = 0u, pbiPid = 0u, pbiPpid = 0u, pbiUid = 0u, pbiGid = 0u, pbiRuid = 0u, pbiRgid = 0u, pbiSvuid = 0u, pbiSvgid = 0u, rfu1 = 0u, pbiComm = Default.default(), pbiName = Default.default(), pbiNfiles = 0u, pbiPgid = 0u, pbiPjobc = 0u, eTdev = 0u, eTpgid = 0u, pbiNice = 0, pbiStartTvsec = 0uL, pbiStartTvusec = 0uL)
    }
}

public open class ProcBsdshortinfo(
    /**
     * Process ID.
     */
    public var pbsiPid: UInt,
    /**
     * Process parent ID.
     */
    public var pbsiPpid: UInt,
    /**
     * Process perp ID.
     */
    public var pbsiPgid: UInt,
    /**
     * `pStat` value: `SZOMB`, `SRUN`, etc.
     */
    public var pbsiStatus: UInt,
    /**
     * Up to 16 characters of process name.
     */
    public var pbsiComm: UByteArray,
    /**
     * 64bit, emulated, etc.
     */
    public var pbsiFlags: UInt,
    /**
     * Current UID on process.
     */
    public var pbsiUid: UidT,
    /**
     * Current GID on process.
     */
    public var pbsiGid: GidT,
    /**
     * Current RUID on process.
     */
    public var pbsiRuid: UidT,
    /**
     * Current RGID on process.
     */
    public var pbsiRgid: GidT,
    /**
     * Current SVUID on process.
     */
    public var pbsiSvuid: UidT,
    /**
     * Current SVGID on process.
     */
    public var pbsiSvgid: GidT,
    /**
     * Reserved for future use.
     */
    public var pbsiRfu: UInt,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): ProcBsdshortinfo = ProcBsdshortinfo(pbsiPid, pbsiPpid, pbsiPgid, pbsiStatus, pbsiComm, pbsiFlags, pbsiUid, pbsiGid, pbsiRuid, pbsiRgid, pbsiSvuid, pbsiSvgid, pbsiRfu, nonExhaustive)

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

        public fun default(): ProcBsdshortinfo = ProcBsdshortinfo(pbsiPid = 0u, pbsiPpid = 0u, pbsiPgid = 0u, pbsiStatus = 0u, pbsiComm = Default.default(), pbsiFlags = 0u, pbsiUid = 0u, pbsiGid = 0u, pbsiRuid = 0u, pbsiRgid = 0u, pbsiSvuid = 0u, pbsiSvgid = 0u, pbsiRfu = 0u, nonExhaustive = Unit)
    }
}

public open class ProcTaskallinfo(
    public var pbsd: ProcBsdinfo,
    public var ptinfo: ProcTaskinfo,
) {
    public open fun clone(): ProcTaskallinfo = ProcTaskallinfo(pbsd, ptinfo)

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

        public fun default(): ProcTaskallinfo = ProcTaskallinfo(pbsd = ProcBsdinfo.default(), ptinfo = ProcTaskinfo.default())
    }
}

// Note(exhaustive): review for 1.0
public open class XswUsage(
    public var xsuTotal: ULong,
    public var xsuAvail: ULong,
    public var xsuUsed: ULong,
    public var xsuPagesize: UInt,
    public var xsuEncrypted: BooleanT,
) {
    public open fun clone(): XswUsage = XswUsage(xsuTotal, xsuAvail, xsuUsed, xsuPagesize, xsuEncrypted)

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

        public fun default(): XswUsage = XswUsage(xsuTotal = 0uL, xsuAvail = 0uL, xsuUsed = 0uL, xsuPagesize = 0u, xsuEncrypted = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class Xucred(
    public var crVersion: CUint,
    public var crUid: UidT,
    public var crNgroups: CShort,
    public var crGroups: IntArray,
) {
    public open fun clone(): Xucred = Xucred(crVersion, crUid, crNgroups, crGroups)

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

        public fun default(): Xucred = Xucred(crVersion = 0u, crUid = 0u, crNgroups = 0, crGroups = Default.default())
    }
}

public open class MachHeader(
    public var magic: UInt,
    public var cputype: CpuTypeT,
    public var cpusubtype: CpuSubtypeT,
    public var filetype: UInt,
    public var ncmds: UInt,
    public var sizeofcmds: UInt,
    public var flags: UInt,
) {
    public open fun clone(): MachHeader = MachHeader(magic, cputype, cpusubtype, filetype, ncmds, sizeofcmds, flags)

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

        public fun default(): MachHeader = MachHeader(magic = 0u, cputype = 0, cpusubtype = 0, filetype = 0u, ncmds = 0u, sizeofcmds = 0u, flags = 0u)
    }
}

public open class MachHeader64(
    public var magic: UInt,
    public var cputype: CpuTypeT,
    public var cpusubtype: CpuSubtypeT,
    public var filetype: UInt,
    public var ncmds: UInt,
    public var sizeofcmds: UInt,
    public var flags: UInt,
    public var reserved: UInt,
) {
    public open fun clone(): MachHeader64 = MachHeader64(magic, cputype, cpusubtype, filetype, ncmds, sizeofcmds, flags, reserved)

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

        public fun default(): MachHeader64 = MachHeader64(magic = 0u, cputype = 0, cpusubtype = 0, filetype = 0u, ncmds = 0u, sizeofcmds = 0u, flags = 0u, reserved = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class SegmentCommand(
    public var cmd: UInt,
    public var cmdsize: UInt,
    public var segname: UByteArray,
    public var vmaddr: UInt,
    public var vmsize: UInt,
    public var fileoff: UInt,
    public var filesize: UInt,
    public var maxprot: VmProtT,
    public var initprot: VmProtT,
    public var nsects: UInt,
    public var flags: UInt,
) {
    public open fun clone(): SegmentCommand = SegmentCommand(cmd, cmdsize, segname, vmaddr, vmsize, fileoff, filesize, maxprot, initprot, nsects, flags)

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

        public fun default(): SegmentCommand = SegmentCommand(cmd = 0u, cmdsize = 0u, segname = Default.default(), vmaddr = 0u, vmsize = 0u, fileoff = 0u, filesize = 0u, maxprot = 0, initprot = 0, nsects = 0u, flags = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class SegmentCommand64(
    public var cmd: UInt,
    public var cmdsize: UInt,
    public var segname: UByteArray,
    public var vmaddr: ULong,
    public var vmsize: ULong,
    public var fileoff: ULong,
    public var filesize: ULong,
    public var maxprot: VmProtT,
    public var initprot: VmProtT,
    public var nsects: UInt,
    public var flags: UInt,
) {
    public open fun clone(): SegmentCommand64 = SegmentCommand64(cmd, cmdsize, segname, vmaddr, vmsize, fileoff, filesize, maxprot, initprot, nsects, flags)

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

        public fun default(): SegmentCommand64 = SegmentCommand64(cmd = 0u, cmdsize = 0u, segname = Default.default(), vmaddr = 0uL, vmsize = 0uL, fileoff = 0uL, filesize = 0uL, maxprot = 0, initprot = 0, nsects = 0u, flags = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class LoadCommand(
    public var cmd: UInt,
    public var cmdsize: UInt,
) {
    public open fun clone(): LoadCommand = LoadCommand(cmd, cmdsize)

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

        public fun default(): LoadCommand = LoadCommand(cmd = 0u, cmdsize = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class SockaddrDl(
    public var sdlLen: CUchar,
    public var sdlFamily: CUchar,
    public var sdlIndex: CUshort,
    public var sdlType: CUchar,
    public var sdlNlen: CUchar,
    public var sdlAlen: CUchar,
    public var sdlSlen: CUchar,
    public var sdlData: UByteArray,
) {
    public open fun clone(): SockaddrDl = SockaddrDl(sdlLen, sdlFamily, sdlIndex, sdlType, sdlNlen, sdlAlen, sdlSlen, sdlData)

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

        public fun default(): SockaddrDl = SockaddrDl(sdlLen = 0u, sdlFamily = 0u, sdlIndex = 0u, sdlType = 0u, sdlNlen = 0u, sdlAlen = 0u, sdlSlen = 0u, sdlData = Default.default())
    }
}

public open class SockaddrInarp(
    public var sinLen: CUchar,
    public var sinFamily: CUchar,
    public var sinPort: CUshort,
    public var sinAddr: InAddr,
    public var sinSrcaddr: InAddr,
    public var sinTos: CUshort,
    public var sinOther: CUshort,
) {
    public open fun clone(): SockaddrInarp = SockaddrInarp(sinLen, sinFamily, sinPort, sinAddr, sinSrcaddr, sinTos, sinOther)

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

        public fun default(): SockaddrInarp = SockaddrInarp(sinLen = 0u, sinFamily = 0u, sinPort = 0u, sinAddr = InAddr.default(), sinSrcaddr = InAddr.default(), sinTos = 0u, sinOther = 0u)
    }
}

public open class SockaddrCtl(
    public var scLen: CUchar,
    public var scFamily: CUchar,
    public var ssSysaddr: UShort,
    public var scId: UInt,
    public var scUnit: UInt,
    public var scReserved: UIntArray,
) {
    public open fun clone(): SockaddrCtl = SockaddrCtl(scLen, scFamily, ssSysaddr, scId, scUnit, scReserved)

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

        public fun default(): SockaddrCtl = SockaddrCtl(scLen = 0u, scFamily = 0u, ssSysaddr = 0u, scId = 0u, scUnit = 0u, scReserved = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class InPktinfo(
    public var ipiIfindex: CUint,
    public var ipiSpecDst: InAddr,
    public var ipiAddr: InAddr,
) {
    public open fun clone(): InPktinfo = InPktinfo(ipiIfindex, ipiSpecDst, ipiAddr)

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

        public fun default(): InPktinfo = InPktinfo(ipiIfindex = 0u, ipiSpecDst = InAddr.default(), ipiAddr = InAddr.default())
    }
}

// Note(exhaustive): review for 1.0
public open class In6Pktinfo(
    public var ipi6Addr: In6Addr,
    public var ipi6Ifindex: CUint,
) {
    public open fun clone(): In6Pktinfo = In6Pktinfo(ipi6Addr, ipi6Ifindex)

    public companion object {
        public fun default(): In6Pktinfo = In6Pktinfo(ipi6Addr = In6Addr.default(), ipi6Ifindex = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class IpcPerm(
    public var uid: UidT,
    public var gid: GidT,
    public var cuid: UidT,
    public var cgid: GidT,
    public var mode: ModeT,
    public var seq: CUshort,
    public var key: KeyT,
) {
    public open fun clone(): IpcPerm = IpcPerm(uid, gid, cuid, cgid, mode, seq, key)

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

        public fun default(): IpcPerm = IpcPerm(uid = 0u, gid = 0u, cuid = 0u, cgid = 0u, mode = 0u, seq = 0u, key = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class Sembuf(
    public var semNum: CUshort,
    public var semOp: CShort,
    public var semFlg: CShort,
) {
    public open fun clone(): Sembuf = Sembuf(semNum, semOp, semFlg)

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

        public fun default(): Sembuf = Sembuf(semNum = 0u, semOp = 0, semFlg = 0)
    }
}

public open class Arphdr(
    public var arHrd: UShort,
    public var arPro: UShort,
    public var arHln: UByte,
    public var arPln: UByte,
    public var arOp: UShort,
) {
    public open fun clone(): Arphdr = Arphdr(arHrd, arPro, arHln, arPln, arOp)

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

        public fun default(): Arphdr = Arphdr(arHrd = 0u, arPro = 0u, arHln = 0u, arPln = 0u, arOp = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class InAddr(
    public var sAddr: InAddrT,
) {
    public open fun clone(): InAddr = InAddr(sAddr)

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

        public fun default(): InAddr = InAddr(sAddr = 0u)
    }
}

public open class SockaddrNdrv(
    public var sndLen: CUchar,
    public var sndFamily: CUchar,
    public var sndName: UByteArray,
) {
    public open fun clone(): SockaddrNdrv = SockaddrNdrv(sndLen, sndFamily, sndName)

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

        public fun default(): SockaddrNdrv = SockaddrNdrv(sndLen = 0u, sndFamily = 0u, sndName = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class SaEndpointsT internal constructor(
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

    public open fun clone(): SaEndpointsT = SaEndpointsT(saeSrcif, saeSrcaddr, saeSrcaddrlen, saeDstaddr, saeDstaddrlen)

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
            saeSrcif: CUint,
            saeSrcaddr: ConstPtr,
            saeSrcaddrlen: SocklenT,
            saeDstaddr: ConstPtr,
            saeDstaddrlen: SocklenT,
        ): SaEndpointsT = SaEndpointsT(
            saeSrcif = saeSrcif,
            saeSrcaddrCtorArg = saeSrcaddr,
            saeSrcaddrlen = saeSrcaddrlen,
            saeDstaddrCtorArg = saeDstaddr,
            saeDstaddrlen = saeDstaddrlen,
        )

        public fun default(): SaEndpointsT = SaEndpointsT(saeSrcif = 0u, saeSrcaddr = MutPtr(0L), saeSrcaddrlen = 0u, saeDstaddr = MutPtr(0L), saeDstaddrlen = 0u)
    }
}

// Note(exhaustive): review for 1.0
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
    public var ppsfreq: CLong,
    public var jitter: CLong,
    public var shift: CInt,
    public var stabil: CLong,
    public var jitcnt: CLong,
    public var calcnt: CLong,
    public var errcnt: CLong,
    public var stbcnt: CLong,
) {
    public open fun clone(): Timex = Timex(modes, offset, freq, maxerror, esterror, status, constant, precision, tolerance, ppsfreq, jitter, shift, stabil, jitcnt, calcnt, errcnt, stbcnt)

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

        public fun default(): Timex = Timex(modes = 0u, offset = 0L, freq = 0L, maxerror = 0L, esterror = 0L, status = 0, constant = 0L, precision = 0L, tolerance = 0L, ppsfreq = 0L, jitter = 0L, shift = 0, stabil = 0L, jitcnt = 0L, calcnt = 0L, errcnt = 0L, stbcnt = 0L)
    }
}

// Note(exhaustive): review for 1.0
public open class Ntptimeval(
    public var time: Timespec,
    public var maxerror: CLong,
    public var esterror: CLong,
    public var tai: CLong,
    public var timeState: CInt,
) {
    public open fun clone(): Ntptimeval = Ntptimeval(time, maxerror, esterror, tai, timeState)

    public companion object {
        public fun default(): Ntptimeval = Ntptimeval(time = Timespec.default(), maxerror = 0L, esterror = 0L, tai = 0L, timeState = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadStandardPolicy(
    public var noData: NaturalT,
) {
    public open fun clone(): ThreadStandardPolicy = ThreadStandardPolicy(noData)

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

        public fun default(): ThreadStandardPolicy = ThreadStandardPolicy(noData = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadExtendedPolicy(
    public var timeshare: BooleanT,
) {
    public open fun clone(): ThreadExtendedPolicy = ThreadExtendedPolicy(timeshare)

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

        public fun default(): ThreadExtendedPolicy = ThreadExtendedPolicy(timeshare = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadTimeConstraintPolicy(
    public var period: UInt,
    public var computation: UInt,
    public var constraint: UInt,
    public var preemptible: BooleanT,
) {
    public open fun clone(): ThreadTimeConstraintPolicy = ThreadTimeConstraintPolicy(period, computation, constraint, preemptible)

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

        public fun default(): ThreadTimeConstraintPolicy = ThreadTimeConstraintPolicy(period = 0u, computation = 0u, constraint = 0u, preemptible = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadPrecedencePolicy(
    public var importance: IntegerT,
) {
    public open fun clone(): ThreadPrecedencePolicy = ThreadPrecedencePolicy(importance)

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

        public fun default(): ThreadPrecedencePolicy = ThreadPrecedencePolicy(importance = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadAffinityPolicy(
    public var affinityTag: IntegerT,
) {
    public open fun clone(): ThreadAffinityPolicy = ThreadAffinityPolicy(affinityTag)

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

        public fun default(): ThreadAffinityPolicy = ThreadAffinityPolicy(affinityTag = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadBackgroundPolicy(
    public var priority: IntegerT,
) {
    public open fun clone(): ThreadBackgroundPolicy = ThreadBackgroundPolicy(priority)

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

        public fun default(): ThreadBackgroundPolicy = ThreadBackgroundPolicy(priority = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadLatencyQosPolicy(
    public var threadLatencyQosTier: ThreadLatencyQosT,
) {
    public open fun clone(): ThreadLatencyQosPolicy = ThreadLatencyQosPolicy(threadLatencyQosTier)

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

        public fun default(): ThreadLatencyQosPolicy = ThreadLatencyQosPolicy(threadLatencyQosTier = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadThroughputQosPolicy(
    public var threadThroughputQosTier: ThreadThroughputQosT,
) {
    public open fun clone(): ThreadThroughputQosPolicy = ThreadThroughputQosPolicy(threadThroughputQosTier)

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

        public fun default(): ThreadThroughputQosPolicy = ThreadThroughputQosPolicy(threadThroughputQosTier = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class MallocStatisticsT(
    public var blocksInUse: CUint,
    public var sizeInUse: SizeT,
    public var maxSizeInUse: SizeT,
    public var sizeAllocated: SizeT,
) {
    public open fun clone(): MallocStatisticsT = MallocStatisticsT(blocksInUse, sizeInUse, maxSizeInUse, sizeAllocated)

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

        public fun default(): MallocStatisticsT = MallocStatisticsT(blocksInUse = 0u, sizeInUse = 0uL, maxSizeInUse = 0uL, sizeAllocated = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class Mstats(
    public var bytesTotal: SizeT,
    public var chunksUsed: SizeT,
    public var bytesUsed: SizeT,
    public var chunksFree: SizeT,
    public var bytesFree: SizeT,
) {
    public open fun clone(): Mstats = Mstats(bytesTotal, chunksUsed, bytesUsed, chunksFree, bytesFree)

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

        public fun default(): Mstats = Mstats(bytesTotal = 0uL, chunksUsed = 0uL, bytesUsed = 0uL, chunksFree = 0uL, bytesFree = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class VmRangeT(
    public var address: VmAddressT,
    public var size: VmSizeT,
) {
    public open fun clone(): VmRangeT = VmRangeT(address, size)

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

        public fun default(): VmRangeT = VmRangeT(address = 0uL, size = 0uL)
    }
}

public open class VinfoStat(
    public var vstDev: UInt,
    public var vstMode: UShort,
    public var vstNlink: UShort,
    public var vstIno: ULong,
    public var vstUid: UidT,
    public var vstGid: GidT,
    public var vstAtime: Long,
    public var vstAtimensec: Long,
    public var vstMtime: Long,
    public var vstMtimensec: Long,
    public var vstCtime: Long,
    public var vstCtimensec: Long,
    public var vstBirthtime: Long,
    public var vstBirthtimensec: Long,
    public var vstSize: OffT,
    public var vstBlocks: Long,
    public var vstBlksize: Int,
    public var vstFlags: UInt,
    public var vstGen: UInt,
    public var vstRdev: UInt,
    public var vstQspare: LongArray,
) {
    public open fun clone(): VinfoStat = VinfoStat(vstDev, vstMode, vstNlink, vstIno, vstUid, vstGid, vstAtime, vstAtimensec, vstMtime, vstMtimensec, vstCtime, vstCtimensec, vstBirthtime, vstBirthtimensec, vstSize, vstBlocks, vstBlksize, vstFlags, vstGen, vstRdev, vstQspare)

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

        public fun default(): VinfoStat = VinfoStat(vstDev = 0u, vstMode = 0u, vstNlink = 0u, vstIno = 0uL, vstUid = 0u, vstGid = 0u, vstAtime = 0L, vstAtimensec = 0L, vstMtime = 0L, vstMtimensec = 0L, vstCtime = 0L, vstCtimensec = 0L, vstBirthtime = 0L, vstBirthtimensec = 0L, vstSize = 0L, vstBlocks = 0L, vstBlksize = 0, vstFlags = 0u, vstGen = 0u, vstRdev = 0u, vstQspare = Default.default())
    }
}

public open class VnodeInfo(
    public var viStat: VinfoStat,
    public var viType: CInt,
    public var viPad: CInt,
    public var viFsid: FsidT,
) {
    public open fun clone(): VnodeInfo = VnodeInfo(viStat, viType, viPad, viFsid)

    public companion object {
        public fun default(): VnodeInfo = VnodeInfo(viStat = VinfoStat.default(), viType = 0, viPad = 0, viFsid = FsidT.default())
    }
}

public open class VnodeInfoPath(
    public var vipVi: VnodeInfo,
    // Normally it's `vipPath: [cChar; MAXPATHLEN]` but because libc supports an old rustc
    // version, we go around this limitation like this.
    public var vipPath: List<UByteArray>,
) {
    public open fun clone(): VnodeInfoPath = VnodeInfoPath(vipVi, vipPath)

    public companion object {
        public fun default(): VnodeInfoPath = VnodeInfoPath(vipVi = VnodeInfo.default(), vipPath = emptyList())
    }
}

public open class ProcVnodepathinfo(
    public var pviCdir: VnodeInfoPath,
    public var pviRdir: VnodeInfoPath,
) {
    public open fun clone(): ProcVnodepathinfo = ProcVnodepathinfo(pviCdir, pviRdir)

    public companion object {
        public fun default(): ProcVnodepathinfo = ProcVnodepathinfo(pviCdir = VnodeInfoPath.default(), pviRdir = VnodeInfoPath.default())
    }
}

// Note(exhaustive): review for 1.0
public open class VmStatistics(
    public var freeCount: NaturalT,
    public var activeCount: NaturalT,
    public var inactiveCount: NaturalT,
    public var wireCount: NaturalT,
    public var zeroFillCount: NaturalT,
    public var reactivations: NaturalT,
    public var pageins: NaturalT,
    public var pageouts: NaturalT,
    public var faults: NaturalT,
    public var cowFaults: NaturalT,
    public var lookups: NaturalT,
    public var hits: NaturalT,
    public var purgeableCount: NaturalT,
    public var purges: NaturalT,
    public var speculativeCount: NaturalT,
) {
    public open fun clone(): VmStatistics = VmStatistics(freeCount, activeCount, inactiveCount, wireCount, zeroFillCount, reactivations, pageins, pageouts, faults, cowFaults, lookups, hits, purgeableCount, purges, speculativeCount)

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

        public fun default(): VmStatistics = VmStatistics(freeCount = 0u, activeCount = 0u, inactiveCount = 0u, wireCount = 0u, zeroFillCount = 0u, reactivations = 0u, pageins = 0u, pageouts = 0u, faults = 0u, cowFaults = 0u, lookups = 0u, hits = 0u, purgeableCount = 0u, purges = 0u, speculativeCount = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class TaskThreadTimesInfo(
    public var userTime: TimeValueT,
    public var systemTime: TimeValueT,
) {
    public open fun clone(): TaskThreadTimesInfo = TaskThreadTimesInfo(userTime, systemTime)

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

        public fun default(): TaskThreadTimesInfo = TaskThreadTimesInfo(userTime = TimeValueT.default(), systemTime = TimeValueT.default())
    }
}

// Note(exhaustive): review for 1.0
public open class RusageInfoV0(
    public var riUuid: UByteArray,
    public var riUserTime: ULong,
    public var riSystemTime: ULong,
    public var riPkgIdleWkups: ULong,
    public var riInterruptWkups: ULong,
    public var riPageins: ULong,
    public var riWiredSize: ULong,
    public var riResidentSize: ULong,
    public var riPhysFootprint: ULong,
    public var riProcStartAbstime: ULong,
    public var riProcExitAbstime: ULong,
) {
    public open fun clone(): RusageInfoV0 = RusageInfoV0(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime)

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

        public fun default(): RusageInfoV0 = RusageInfoV0(riUuid = Default.default(), riUserTime = 0uL, riSystemTime = 0uL, riPkgIdleWkups = 0uL, riInterruptWkups = 0uL, riPageins = 0uL, riWiredSize = 0uL, riResidentSize = 0uL, riPhysFootprint = 0uL, riProcStartAbstime = 0uL, riProcExitAbstime = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class RusageInfoV1(
    public var riUuid: UByteArray,
    public var riUserTime: ULong,
    public var riSystemTime: ULong,
    public var riPkgIdleWkups: ULong,
    public var riInterruptWkups: ULong,
    public var riPageins: ULong,
    public var riWiredSize: ULong,
    public var riResidentSize: ULong,
    public var riPhysFootprint: ULong,
    public var riProcStartAbstime: ULong,
    public var riProcExitAbstime: ULong,
    public var riChildUserTime: ULong,
    public var riChildSystemTime: ULong,
    public var riChildPkgIdleWkups: ULong,
    public var riChildInterruptWkups: ULong,
    public var riChildPageins: ULong,
    public var riChildElapsedAbstime: ULong,
) {
    public open fun clone(): RusageInfoV1 = RusageInfoV1(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime)

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

        public fun default(): RusageInfoV1 = RusageInfoV1(riUuid = Default.default(), riUserTime = 0uL, riSystemTime = 0uL, riPkgIdleWkups = 0uL, riInterruptWkups = 0uL, riPageins = 0uL, riWiredSize = 0uL, riResidentSize = 0uL, riPhysFootprint = 0uL, riProcStartAbstime = 0uL, riProcExitAbstime = 0uL, riChildUserTime = 0uL, riChildSystemTime = 0uL, riChildPkgIdleWkups = 0uL, riChildInterruptWkups = 0uL, riChildPageins = 0uL, riChildElapsedAbstime = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class RusageInfoV2(
    public var riUuid: UByteArray,
    public var riUserTime: ULong,
    public var riSystemTime: ULong,
    public var riPkgIdleWkups: ULong,
    public var riInterruptWkups: ULong,
    public var riPageins: ULong,
    public var riWiredSize: ULong,
    public var riResidentSize: ULong,
    public var riPhysFootprint: ULong,
    public var riProcStartAbstime: ULong,
    public var riProcExitAbstime: ULong,
    public var riChildUserTime: ULong,
    public var riChildSystemTime: ULong,
    public var riChildPkgIdleWkups: ULong,
    public var riChildInterruptWkups: ULong,
    public var riChildPageins: ULong,
    public var riChildElapsedAbstime: ULong,
    public var riDiskioBytesread: ULong,
    public var riDiskioByteswritten: ULong,
) {
    public open fun clone(): RusageInfoV2 = RusageInfoV2(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime, riDiskioBytesread, riDiskioByteswritten)

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

        public fun default(): RusageInfoV2 = RusageInfoV2(riUuid = Default.default(), riUserTime = 0uL, riSystemTime = 0uL, riPkgIdleWkups = 0uL, riInterruptWkups = 0uL, riPageins = 0uL, riWiredSize = 0uL, riResidentSize = 0uL, riPhysFootprint = 0uL, riProcStartAbstime = 0uL, riProcExitAbstime = 0uL, riChildUserTime = 0uL, riChildSystemTime = 0uL, riChildPkgIdleWkups = 0uL, riChildInterruptWkups = 0uL, riChildPageins = 0uL, riChildElapsedAbstime = 0uL, riDiskioBytesread = 0uL, riDiskioByteswritten = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class RusageInfoV3(
    public var riUuid: UByteArray,
    public var riUserTime: ULong,
    public var riSystemTime: ULong,
    public var riPkgIdleWkups: ULong,
    public var riInterruptWkups: ULong,
    public var riPageins: ULong,
    public var riWiredSize: ULong,
    public var riResidentSize: ULong,
    public var riPhysFootprint: ULong,
    public var riProcStartAbstime: ULong,
    public var riProcExitAbstime: ULong,
    public var riChildUserTime: ULong,
    public var riChildSystemTime: ULong,
    public var riChildPkgIdleWkups: ULong,
    public var riChildInterruptWkups: ULong,
    public var riChildPageins: ULong,
    public var riChildElapsedAbstime: ULong,
    public var riDiskioBytesread: ULong,
    public var riDiskioByteswritten: ULong,
    public var riCpuTimeQosDefault: ULong,
    public var riCpuTimeQosMaintenance: ULong,
    public var riCpuTimeQosBackground: ULong,
    public var riCpuTimeQosUtility: ULong,
    public var riCpuTimeQosLegacy: ULong,
    public var riCpuTimeQosUserInitiated: ULong,
    public var riCpuTimeQosUserInteractive: ULong,
    public var riBilledSystemTime: ULong,
    public var riServicedSystemTime: ULong,
) {
    public open fun clone(): RusageInfoV3 = RusageInfoV3(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime, riDiskioBytesread, riDiskioByteswritten, riCpuTimeQosDefault, riCpuTimeQosMaintenance, riCpuTimeQosBackground, riCpuTimeQosUtility, riCpuTimeQosLegacy, riCpuTimeQosUserInitiated, riCpuTimeQosUserInteractive, riBilledSystemTime, riServicedSystemTime)

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

        public fun default(): RusageInfoV3 = RusageInfoV3(riUuid = Default.default(), riUserTime = 0uL, riSystemTime = 0uL, riPkgIdleWkups = 0uL, riInterruptWkups = 0uL, riPageins = 0uL, riWiredSize = 0uL, riResidentSize = 0uL, riPhysFootprint = 0uL, riProcStartAbstime = 0uL, riProcExitAbstime = 0uL, riChildUserTime = 0uL, riChildSystemTime = 0uL, riChildPkgIdleWkups = 0uL, riChildInterruptWkups = 0uL, riChildPageins = 0uL, riChildElapsedAbstime = 0uL, riDiskioBytesread = 0uL, riDiskioByteswritten = 0uL, riCpuTimeQosDefault = 0uL, riCpuTimeQosMaintenance = 0uL, riCpuTimeQosBackground = 0uL, riCpuTimeQosUtility = 0uL, riCpuTimeQosLegacy = 0uL, riCpuTimeQosUserInitiated = 0uL, riCpuTimeQosUserInteractive = 0uL, riBilledSystemTime = 0uL, riServicedSystemTime = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class RusageInfoV4(
    public var riUuid: UByteArray,
    public var riUserTime: ULong,
    public var riSystemTime: ULong,
    public var riPkgIdleWkups: ULong,
    public var riInterruptWkups: ULong,
    public var riPageins: ULong,
    public var riWiredSize: ULong,
    public var riResidentSize: ULong,
    public var riPhysFootprint: ULong,
    public var riProcStartAbstime: ULong,
    public var riProcExitAbstime: ULong,
    public var riChildUserTime: ULong,
    public var riChildSystemTime: ULong,
    public var riChildPkgIdleWkups: ULong,
    public var riChildInterruptWkups: ULong,
    public var riChildPageins: ULong,
    public var riChildElapsedAbstime: ULong,
    public var riDiskioBytesread: ULong,
    public var riDiskioByteswritten: ULong,
    public var riCpuTimeQosDefault: ULong,
    public var riCpuTimeQosMaintenance: ULong,
    public var riCpuTimeQosBackground: ULong,
    public var riCpuTimeQosUtility: ULong,
    public var riCpuTimeQosLegacy: ULong,
    public var riCpuTimeQosUserInitiated: ULong,
    public var riCpuTimeQosUserInteractive: ULong,
    public var riBilledSystemTime: ULong,
    public var riServicedSystemTime: ULong,
    public var riLogicalWrites: ULong,
    public var riLifetimeMaxPhysFootprint: ULong,
    public var riInstructions: ULong,
    public var riCycles: ULong,
    public var riBilledEnergy: ULong,
    public var riServicedEnergy: ULong,
    public var riIntervalMaxPhysFootprint: ULong,
    public var riRunnableTime: ULong,
) {
    public open fun clone(): RusageInfoV4 = RusageInfoV4(riUuid, riUserTime, riSystemTime, riPkgIdleWkups, riInterruptWkups, riPageins, riWiredSize, riResidentSize, riPhysFootprint, riProcStartAbstime, riProcExitAbstime, riChildUserTime, riChildSystemTime, riChildPkgIdleWkups, riChildInterruptWkups, riChildPageins, riChildElapsedAbstime, riDiskioBytesread, riDiskioByteswritten, riCpuTimeQosDefault, riCpuTimeQosMaintenance, riCpuTimeQosBackground, riCpuTimeQosUtility, riCpuTimeQosLegacy, riCpuTimeQosUserInitiated, riCpuTimeQosUserInteractive, riBilledSystemTime, riServicedSystemTime, riLogicalWrites, riLifetimeMaxPhysFootprint, riInstructions, riCycles, riBilledEnergy, riServicedEnergy, riIntervalMaxPhysFootprint, riRunnableTime)

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

        public fun default(): RusageInfoV4 = RusageInfoV4(riUuid = Default.default(), riUserTime = 0uL, riSystemTime = 0uL, riPkgIdleWkups = 0uL, riInterruptWkups = 0uL, riPageins = 0uL, riWiredSize = 0uL, riResidentSize = 0uL, riPhysFootprint = 0uL, riProcStartAbstime = 0uL, riProcExitAbstime = 0uL, riChildUserTime = 0uL, riChildSystemTime = 0uL, riChildPkgIdleWkups = 0uL, riChildInterruptWkups = 0uL, riChildPageins = 0uL, riChildElapsedAbstime = 0uL, riDiskioBytesread = 0uL, riDiskioByteswritten = 0uL, riCpuTimeQosDefault = 0uL, riCpuTimeQosMaintenance = 0uL, riCpuTimeQosBackground = 0uL, riCpuTimeQosUtility = 0uL, riCpuTimeQosLegacy = 0uL, riCpuTimeQosUserInitiated = 0uL, riCpuTimeQosUserInteractive = 0uL, riBilledSystemTime = 0uL, riServicedSystemTime = 0uL, riLogicalWrites = 0uL, riLifetimeMaxPhysFootprint = 0uL, riInstructions = 0uL, riCycles = 0uL, riBilledEnergy = 0uL, riServicedEnergy = 0uL, riIntervalMaxPhysFootprint = 0uL, riRunnableTime = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class ImageOffset(
    public var uuid: UuidT,
    public var offset: UInt,
) {
    public open fun clone(): ImageOffset = ImageOffset(uuid, offset)

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

        public fun default(): ImageOffset = ImageOffset(uuid = UByteArray(0), offset = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class Attrlist(
    public var bitmapcount: CUshort,
    public var reserved: UShort,
    public var commonattr: AttrgroupT,
    public var volattr: AttrgroupT,
    public var dirattr: AttrgroupT,
    public var fileattr: AttrgroupT,
    public var forkattr: AttrgroupT,
) {
    public open fun clone(): Attrlist = Attrlist(bitmapcount, reserved, commonattr, volattr, dirattr, fileattr, forkattr)

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

        public fun default(): Attrlist = Attrlist(bitmapcount = 0u, reserved = 0u, commonattr = 0u, volattr = 0u, dirattr = 0u, fileattr = 0u, forkattr = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class AttrreferenceT(
    public var attrDataoffset: Int,
    public var attrLength: UInt,
) {
    public open fun clone(): AttrreferenceT = AttrreferenceT(attrDataoffset, attrLength)

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

        public fun default(): AttrreferenceT = AttrreferenceT(attrDataoffset = 0, attrLength = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class VolCapabilitiesAttrT(
    public var capabilities: VolCapabilitiesSetT,
    public var valid: VolCapabilitiesSetT,
) {
    public open fun clone(): VolCapabilitiesAttrT = VolCapabilitiesAttrT(capabilities, valid)

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

        public fun default(): VolCapabilitiesAttrT = VolCapabilitiesAttrT(capabilities = UIntArray(0), valid = UIntArray(0))
    }
}

// Note(exhaustive): review for 1.0
public open class AttributeSetT(
    public var commonattr: AttrgroupT,
    public var volattr: AttrgroupT,
    public var dirattr: AttrgroupT,
    public var fileattr: AttrgroupT,
    public var forkattr: AttrgroupT,
) {
    public open fun clone(): AttributeSetT = AttributeSetT(commonattr, volattr, dirattr, fileattr, forkattr)

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

        public fun default(): AttributeSetT = AttributeSetT(commonattr = 0u, volattr = 0u, dirattr = 0u, fileattr = 0u, forkattr = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class VolAttributesAttrT(
    public var validattr: AttributeSetT,
    public var nativeattr: AttributeSetT,
) {
    public open fun clone(): VolAttributesAttrT = VolAttributesAttrT(validattr, nativeattr)

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

        public fun default(): VolAttributesAttrT = VolAttributesAttrT(validattr = AttributeSetT.default(), nativeattr = AttributeSetT.default())
    }
}

public open class TcpConnectionInfo(
    public var tcpiState: UByte,
    public var tcpiSndWscale: UByte,
    public var tcpiRcvWscale: UByte,
    public var pad1: UByte,
    public var tcpiOptions: UInt,
    public var tcpiFlags: UInt,
    public var tcpiRto: UInt,
    public var tcpiMaxseg: UInt,
    public var tcpiSndSsthresh: UInt,
    public var tcpiSndCwnd: UInt,
    public var tcpiSndWnd: UInt,
    public var tcpiSndSbbytes: UInt,
    public var tcpiRcvWnd: UInt,
    public var tcpiRttcur: UInt,
    public var tcpiSrtt: UInt,
    public var tcpiRttvar: UInt,
    public var tcpiTfoCookieReq: UInt,
    public var tcpiTfoCookieRcv: UInt,
    public var tcpiTfoSynLoss: UInt,
    public var tcpiTfoSynDataSent: UInt,
    public var tcpiTfoSynDataAcked: UInt,
    public var tcpiTfoSynDataRcv: UInt,
    public var tcpiTfoCookieReqRcv: UInt,
    public var tcpiTfoCookieSent: UInt,
    public var tcpiTfoCookieInvalid: UInt,
    public var tcpiTfoCookieWrong: UInt,
    public var tcpiTfoNoCookieRcv: UInt,
    public var tcpiTfoHeuristicsDisable: UInt,
    public var tcpiTfoSendBlackhole: UInt,
    public var tcpiTfoRecvBlackhole: UInt,
    public var tcpiTfoOnebyteProxy: UInt,
    public var pad2: UInt,
    public var tcpiTxpackets: ULong,
    public var tcpiTxbytes: ULong,
    public var tcpiTxretransmitbytes: ULong,
    public var tcpiRxpackets: ULong,
    public var tcpiRxbytes: ULong,
    public var tcpiRxoutoforderbytes: ULong,
    public var tcpiRxretransmitpackets: ULong,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): TcpConnectionInfo = TcpConnectionInfo(tcpiState, tcpiSndWscale, tcpiRcvWscale, pad1, tcpiOptions, tcpiFlags, tcpiRto, tcpiMaxseg, tcpiSndSsthresh, tcpiSndCwnd, tcpiSndWnd, tcpiSndSbbytes, tcpiRcvWnd, tcpiRttcur, tcpiSrtt, tcpiRttvar, tcpiTfoCookieReq, tcpiTfoCookieRcv, tcpiTfoSynLoss, tcpiTfoSynDataSent, tcpiTfoSynDataAcked, tcpiTfoSynDataRcv, tcpiTfoCookieReqRcv, tcpiTfoCookieSent, tcpiTfoCookieInvalid, tcpiTfoCookieWrong, tcpiTfoNoCookieRcv, tcpiTfoHeuristicsDisable, tcpiTfoSendBlackhole, tcpiTfoRecvBlackhole, tcpiTfoOnebyteProxy, pad2, tcpiTxpackets, tcpiTxbytes, tcpiTxretransmitbytes, tcpiRxpackets, tcpiRxbytes, tcpiRxoutoforderbytes, tcpiRxretransmitpackets, nonExhaustive)

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

        public fun default(): TcpConnectionInfo = TcpConnectionInfo(tcpiState = 0u, tcpiSndWscale = 0u, tcpiRcvWscale = 0u, pad1 = 0u, tcpiOptions = 0u, tcpiFlags = 0u, tcpiRto = 0u, tcpiMaxseg = 0u, tcpiSndSsthresh = 0u, tcpiSndCwnd = 0u, tcpiSndWnd = 0u, tcpiSndSbbytes = 0u, tcpiRcvWnd = 0u, tcpiRttcur = 0u, tcpiSrtt = 0u, tcpiRttvar = 0u, tcpiTfoCookieReq = 0u, tcpiTfoCookieRcv = 0u, tcpiTfoSynLoss = 0u, tcpiTfoSynDataSent = 0u, tcpiTfoSynDataAcked = 0u, tcpiTfoSynDataRcv = 0u, tcpiTfoCookieReqRcv = 0u, tcpiTfoCookieSent = 0u, tcpiTfoCookieInvalid = 0u, tcpiTfoCookieWrong = 0u, tcpiTfoNoCookieRcv = 0u, tcpiTfoHeuristicsDisable = 0u, tcpiTfoSendBlackhole = 0u, tcpiTfoRecvBlackhole = 0u, tcpiTfoOnebyteProxy = 0u, pad2 = 0u, tcpiTxpackets = 0uL, tcpiTxbytes = 0uL, tcpiTxretransmitbytes = 0uL, tcpiRxpackets = 0uL, tcpiRxbytes = 0uL, tcpiRxoutoforderbytes = 0uL, tcpiRxretransmitpackets = 0uL, nonExhaustive = Unit)
    }
}

public open class In6Addrlifetime(
    public var ia6tExpire: TimeT,
    public var ia6tPreferred: TimeT,
    public var ia6tVltime: UInt,
    public var ia6tPltime: UInt,
) {
    public open fun clone(): In6Addrlifetime = In6Addrlifetime(ia6tExpire, ia6tPreferred, ia6tVltime, ia6tPltime)

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

        public fun default(): In6Addrlifetime = In6Addrlifetime(ia6tExpire = 0L, ia6tPreferred = 0L, ia6tVltime = 0u, ia6tPltime = 0u)
    }
}

public open class In6Ifstat(
    public var ifs6InReceive: UQuadT,
    public var ifs6InHdrerr: UQuadT,
    public var ifs6InToobig: UQuadT,
    public var ifs6InNoroute: UQuadT,
    public var ifs6InAddrerr: UQuadT,
    public var ifs6InProtounknown: UQuadT,
    public var ifs6InTruncated: UQuadT,
    public var ifs6InDiscard: UQuadT,
    public var ifs6InDeliver: UQuadT,
    public var ifs6OutForward: UQuadT,
    public var ifs6OutRequest: UQuadT,
    public var ifs6OutDiscard: UQuadT,
    public var ifs6OutFragok: UQuadT,
    public var ifs6OutFragfail: UQuadT,
    public var ifs6OutFragcreat: UQuadT,
    public var ifs6ReassReqd: UQuadT,
    public var ifs6ReassOk: UQuadT,
    public var ifs6AtmfragRcvd: UQuadT,
    public var ifs6ReassFail: UQuadT,
    public var ifs6InMcast: UQuadT,
    public var ifs6OutMcast: UQuadT,
    public var ifs6CantfowardIcmp6: UQuadT,
    public var ifs6AddrExpiryCnt: UQuadT,
    public var ifs6PfxExpiryCnt: UQuadT,
    public var ifs6DefrtrExpiryCnt: UQuadT,
) {
    public open fun clone(): In6Ifstat = In6Ifstat(ifs6InReceive, ifs6InHdrerr, ifs6InToobig, ifs6InNoroute, ifs6InAddrerr, ifs6InProtounknown, ifs6InTruncated, ifs6InDiscard, ifs6InDeliver, ifs6OutForward, ifs6OutRequest, ifs6OutDiscard, ifs6OutFragok, ifs6OutFragfail, ifs6OutFragcreat, ifs6ReassReqd, ifs6ReassOk, ifs6AtmfragRcvd, ifs6ReassFail, ifs6InMcast, ifs6OutMcast, ifs6CantfowardIcmp6, ifs6AddrExpiryCnt, ifs6PfxExpiryCnt, ifs6DefrtrExpiryCnt)

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

        public fun default(): In6Ifstat = In6Ifstat(ifs6InReceive = 0uL, ifs6InHdrerr = 0uL, ifs6InToobig = 0uL, ifs6InNoroute = 0uL, ifs6InAddrerr = 0uL, ifs6InProtounknown = 0uL, ifs6InTruncated = 0uL, ifs6InDiscard = 0uL, ifs6InDeliver = 0uL, ifs6OutForward = 0uL, ifs6OutRequest = 0uL, ifs6OutDiscard = 0uL, ifs6OutFragok = 0uL, ifs6OutFragfail = 0uL, ifs6OutFragcreat = 0uL, ifs6ReassReqd = 0uL, ifs6ReassOk = 0uL, ifs6AtmfragRcvd = 0uL, ifs6ReassFail = 0uL, ifs6InMcast = 0uL, ifs6OutMcast = 0uL, ifs6CantfowardIcmp6 = 0uL, ifs6AddrExpiryCnt = 0uL, ifs6PfxExpiryCnt = 0uL, ifs6DefrtrExpiryCnt = 0uL)
    }
}

public open class Icmp6Ifstat(
    public var ifs6InMsg: UQuadT,
    public var ifs6InError: UQuadT,
    public var ifs6InDstunreach: UQuadT,
    public var ifs6InAdminprohib: UQuadT,
    public var ifs6InTimeexceed: UQuadT,
    public var ifs6InParamprob: UQuadT,
    public var ifs6InPkttoobig: UQuadT,
    public var ifs6InEcho: UQuadT,
    public var ifs6InEchoreply: UQuadT,
    public var ifs6InRoutersolicit: UQuadT,
    public var ifs6InRouteradvert: UQuadT,
    public var ifs6InNeighborsolicit: UQuadT,
    public var ifs6InNeighboradvert: UQuadT,
    public var ifs6InRedirect: UQuadT,
    public var ifs6InMldquery: UQuadT,
    public var ifs6InMldreport: UQuadT,
    public var ifs6InMlddone: UQuadT,
    public var ifs6OutMsg: UQuadT,
    public var ifs6OutError: UQuadT,
    public var ifs6OutDstunreach: UQuadT,
    public var ifs6OutAdminprohib: UQuadT,
    public var ifs6OutTimeexceed: UQuadT,
    public var ifs6OutParamprob: UQuadT,
    public var ifs6OutPkttoobig: UQuadT,
    public var ifs6OutEcho: UQuadT,
    public var ifs6OutEchoreply: UQuadT,
    public var ifs6OutRoutersolicit: UQuadT,
    public var ifs6OutRouteradvert: UQuadT,
    public var ifs6OutNeighborsolicit: UQuadT,
    public var ifs6OutNeighboradvert: UQuadT,
    public var ifs6OutRedirect: UQuadT,
    public var ifs6OutMldquery: UQuadT,
    public var ifs6OutMldreport: UQuadT,
    public var ifs6OutMlddone: UQuadT,
) {
    public open fun clone(): Icmp6Ifstat = Icmp6Ifstat(ifs6InMsg, ifs6InError, ifs6InDstunreach, ifs6InAdminprohib, ifs6InTimeexceed, ifs6InParamprob, ifs6InPkttoobig, ifs6InEcho, ifs6InEchoreply, ifs6InRoutersolicit, ifs6InRouteradvert, ifs6InNeighborsolicit, ifs6InNeighboradvert, ifs6InRedirect, ifs6InMldquery, ifs6InMldreport, ifs6InMlddone, ifs6OutMsg, ifs6OutError, ifs6OutDstunreach, ifs6OutAdminprohib, ifs6OutTimeexceed, ifs6OutParamprob, ifs6OutPkttoobig, ifs6OutEcho, ifs6OutEchoreply, ifs6OutRoutersolicit, ifs6OutRouteradvert, ifs6OutNeighborsolicit, ifs6OutNeighboradvert, ifs6OutRedirect, ifs6OutMldquery, ifs6OutMldreport, ifs6OutMlddone)

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

        public fun default(): Icmp6Ifstat = Icmp6Ifstat(ifs6InMsg = 0uL, ifs6InError = 0uL, ifs6InDstunreach = 0uL, ifs6InAdminprohib = 0uL, ifs6InTimeexceed = 0uL, ifs6InParamprob = 0uL, ifs6InPkttoobig = 0uL, ifs6InEcho = 0uL, ifs6InEchoreply = 0uL, ifs6InRoutersolicit = 0uL, ifs6InRouteradvert = 0uL, ifs6InNeighborsolicit = 0uL, ifs6InNeighboradvert = 0uL, ifs6InRedirect = 0uL, ifs6InMldquery = 0uL, ifs6InMldreport = 0uL, ifs6InMlddone = 0uL, ifs6OutMsg = 0uL, ifs6OutError = 0uL, ifs6OutDstunreach = 0uL, ifs6OutAdminprohib = 0uL, ifs6OutTimeexceed = 0uL, ifs6OutParamprob = 0uL, ifs6OutPkttoobig = 0uL, ifs6OutEcho = 0uL, ifs6OutEchoreply = 0uL, ifs6OutRoutersolicit = 0uL, ifs6OutRouteradvert = 0uL, ifs6OutNeighborsolicit = 0uL, ifs6OutNeighboradvert = 0uL, ifs6OutRedirect = 0uL, ifs6OutMldquery = 0uL, ifs6OutMldreport = 0uL, ifs6OutMlddone = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class HostCpuLoadInfo(
    public var cpuTicks: UIntArray,
) {
    public open fun clone(): HostCpuLoadInfo = HostCpuLoadInfo(cpuTicks)

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

        public fun default(): HostCpuLoadInfo = HostCpuLoadInfo(cpuTicks = Default.default())
    }
}

public open class Ifmibdata(
    /**
     * Name of interface
     */
    public var ifmdName: UByteArray,
    /**
     * Number of promiscuous listeners
     */
    public var ifmdPcount: CUint,
    /**
     * Interface flags
     */
    public var ifmdFlags: CUint,
    /**
     * Instantaneous length of send queue
     */
    public var ifmdSndLen: CUint,
    /**
     * Maximum length of send queue
     */
    public var ifmdSndMaxlen: CUint,
    /**
     * Number of drops in send queue
     */
    public var ifmdSndDrops: CUint,
    /**
     * For future expansion
     */
    public var ifmdFiller: UIntArray,
    /**
     * Generic information and statistics
     */
    public var ifmdData: IfData64,
) {
    public open fun clone(): Ifmibdata = Ifmibdata(ifmdName, ifmdPcount, ifmdFlags, ifmdSndLen, ifmdSndMaxlen, ifmdSndDrops, ifmdFiller, ifmdData)

    public companion object {
        public fun default(): Ifmibdata = Ifmibdata(ifmdName = Default.default(), ifmdPcount = 0u, ifmdFlags = 0u, ifmdSndLen = 0u, ifmdSndMaxlen = 0u, ifmdSndDrops = 0u, ifmdFiller = Default.default(), ifmdData = IfData64.default())
    }
}

public open class IfsIso88023(
    public var dot3StatsAlignmentErrors: UInt,
    public var dot3StatsFCSErrors: UInt,
    public var dot3StatsSingleCollisionFrames: UInt,
    public var dot3StatsMultipleCollisionFrames: UInt,
    public var dot3StatsSQETestErrors: UInt,
    public var dot3StatsDeferredTransmissions: UInt,
    public var dot3StatsLateCollisions: UInt,
    public var dot3StatsExcessiveCollisions: UInt,
    public var dot3StatsInternalMacTransmitErrors: UInt,
    public var dot3StatsCarrierSenseErrors: UInt,
    public var dot3StatsFrameTooLongs: UInt,
    public var dot3StatsInternalMacReceiveErrors: UInt,
    public var dot3StatsEtherChipSet: UInt,
    public var dot3StatsMissedFrames: UInt,
    public var dot3StatsCollFrequencies: UIntArray,
    public var dot3Compliance: UInt,
) {
    public open fun clone(): IfsIso88023 = IfsIso88023(dot3StatsAlignmentErrors, dot3StatsFCSErrors, dot3StatsSingleCollisionFrames, dot3StatsMultipleCollisionFrames, dot3StatsSQETestErrors, dot3StatsDeferredTransmissions, dot3StatsLateCollisions, dot3StatsExcessiveCollisions, dot3StatsInternalMacTransmitErrors, dot3StatsCarrierSenseErrors, dot3StatsFrameTooLongs, dot3StatsInternalMacReceiveErrors, dot3StatsEtherChipSet, dot3StatsMissedFrames, dot3StatsCollFrequencies, dot3Compliance)

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

        public fun default(): IfsIso88023 = IfsIso88023(dot3StatsAlignmentErrors = 0u, dot3StatsFCSErrors = 0u, dot3StatsSingleCollisionFrames = 0u, dot3StatsMultipleCollisionFrames = 0u, dot3StatsSQETestErrors = 0u, dot3StatsDeferredTransmissions = 0u, dot3StatsLateCollisions = 0u, dot3StatsExcessiveCollisions = 0u, dot3StatsInternalMacTransmitErrors = 0u, dot3StatsCarrierSenseErrors = 0u, dot3StatsFrameTooLongs = 0u, dot3StatsInternalMacReceiveErrors = 0u, dot3StatsEtherChipSet = 0u, dot3StatsMissedFrames = 0u, dot3StatsCollFrequencies = Default.default(), dot3Compliance = 0u)
    }
}

public open class CtlInfo(
    public var ctlId: UInt,
    public var ctlName: UByteArray,
) {
    public open fun clone(): CtlInfo = CtlInfo(ctlId, ctlName)

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

        public fun default(): CtlInfo = CtlInfo(ctlId = 0u, ctlName = UByteArray(MAX_KCTL_NAME) { 0u.toUByte() })
    }
}

public open class ProcFdinfo(
    public var procFd: Int,
    public var procFdtype: UInt,
) {
    public open fun clone(): ProcFdinfo = ProcFdinfo(procFd, procFdtype)

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

        public fun default(): ProcFdinfo = ProcFdinfo(procFd = 0, procFdtype = 0u)
    }
}

public open class Kevent internal constructor(
    public var ident: UintptrT,
    public var filter: Short,
    public var flags: UShort,
    public var fflags: UInt,
    public var data: IntptrT,
    udataCtorArg: MutPtr,
) {
    public var udata: MutPtr = udataCtorArg
        internal set

    public open fun clone(): Kevent = Kevent(ident, filter, flags, fflags, data, udata)

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
            ident: UintptrT,
            filter: Short,
            flags: UShort,
            fflags: UInt,
            data: IntptrT,
            udata: MutPtr,
        ): Kevent = Kevent(
            ident = ident,
            filter = filter,
            flags = flags,
            fflags = fflags,
            data = data,
            udataCtorArg = udata,
        )

        public fun default(): Kevent = Kevent(ident = 0uL, filter = 0, flags = 0u, fflags = 0u, data = 0L, udata = MutPtr(0L))
    }
}

public open class SemidDs(
    // Note the manpage shows different types than the system header.
    public var semPerm: IpcPerm,
    public var semBase: Int,
    public var semNsems: CUshort,
    public var semOtime: TimeT,
    public var semPad1: Int,
    public var semCtime: TimeT,
    public var semPad2: Int,
    public var semPad3: IntArray,
) {
    public open fun clone(): SemidDs = SemidDs(semPerm, semBase, semNsems, semOtime, semPad1, semCtime, semPad2, semPad3)

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

        public fun default(): SemidDs = SemidDs(semPerm = IpcPerm.default(), semBase = 0, semNsems = 0u, semOtime = 0L, semPad1 = 0, semCtime = 0L, semPad2 = 0, semPad3 = Default.default())
    }
}

public open class ShmidDs internal constructor(
    public var shmPerm: IpcPerm,
    public var shmSegsz: SizeT,
    public var shmLpid: PidT,
    public var shmCpid: PidT,
    public var shmNattch: ShmattT,
    public var shmAtime: TimeT,
    // Note(macos): 64-bit wrong align => wrong offset
    public var shmDtime: TimeT,
    // Note(macos): 64-bit wrong align => wrong offset
    public var shmCtime: TimeT,
    shmInternalCtorArg: MutPtr,
) {
    public var shmInternal: MutPtr = shmInternalCtorArg
        internal set

    public open fun clone(): ShmidDs = ShmidDs(shmPerm, shmSegsz, shmLpid, shmCpid, shmNattch, shmAtime, shmDtime, shmCtime, shmInternal)

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
            shmPerm: IpcPerm,
            shmSegsz: SizeT,
            shmLpid: PidT,
            shmCpid: PidT,
            shmNattch: ShmattT,
            shmAtime: TimeT,
            shmDtime: TimeT,
            shmCtime: TimeT,
            shmInternal: MutPtr,
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

        public fun default(): ShmidDs = ShmidDs(shmPerm = IpcPerm.default(), shmSegsz = 0uL, shmLpid = 0, shmCpid = 0, shmNattch = 0u, shmAtime = 0L, shmDtime = 0L, shmCtime = 0L, shmInternal = MutPtr(0L))
    }
}

public open class ProcThreadinfo(
    public var pthUserTime: ULong,
    public var pthSystemTime: ULong,
    public var pthCpuUsage: Int,
    public var pthPolicy: Int,
    public var pthRunState: Int,
    public var pthFlags: Int,
    public var pthSleepTime: Int,
    public var pthCurpri: Int,
    public var pthPriority: Int,
    public var pthMaxpriority: Int,
    public var pthName: UByteArray,
) {
    public open fun clone(): ProcThreadinfo = ProcThreadinfo(pthUserTime, pthSystemTime, pthCpuUsage, pthPolicy, pthRunState, pthFlags, pthSleepTime, pthCurpri, pthPriority, pthMaxpriority, pthName)

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

        public fun default(): ProcThreadinfo = ProcThreadinfo(pthUserTime = 0uL, pthSystemTime = 0uL, pthCpuUsage = 0, pthPolicy = 0, pthRunState = 0, pthFlags = 0, pthSleepTime = 0, pthCurpri = 0, pthPriority = 0, pthMaxpriority = 0, pthName = UByteArray(MAXTHREADNAMESIZE) { 0u.toUByte() })
    }
}

// Note(exhaustive): review for 1.0
public open class Statfs(
    public var fBsize: UInt,
    public var fIosize: Int,
    public var fBlocks: ULong,
    public var fBfree: ULong,
    public var fBavail: ULong,
    public var fFiles: ULong,
    public var fFfree: ULong,
    public var fFsid: FsidT,
    public var fOwner: UidT,
    public var fType: UInt,
    public var fFlags: UInt,
    public var fFssubtype: UInt,
    public var fFstypename: UByteArray,
    public var fMntonname: UByteArray,
    public var fMntfromname: UByteArray,
    public var fFlagsExt: UInt,
    public var fReserved: UIntArray,
) {
    public open fun clone(): Statfs = Statfs(fBsize, fIosize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fOwner, fType, fFlags, fFssubtype, fFstypename, fMntonname, fMntfromname, fFlagsExt, fReserved)

    public companion object {
        public fun default(): Statfs = Statfs(fBsize = 0u, fIosize = 0, fBlocks = 0uL, fBfree = 0uL, fBavail = 0uL, fFiles = 0uL, fFfree = 0uL, fFsid = FsidT.default(), fOwner = 0u, fType = 0u, fFlags = 0u, fFssubtype = 0u, fFstypename = UByteArray(16) { 0u.toUByte() }, fMntonname = UByteArray(1024) { 0u.toUByte() }, fMntfromname = UByteArray(1024) { 0u.toUByte() }, fFlagsExt = 0u, fReserved = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Dirent(
    public var dIno: ULong,
    public var dSeekoff: ULong,
    public var dReclen: UShort,
    public var dNamlen: UShort,
    public var dType: UByte,
    public var dName: UByteArray,
) {
    public open fun clone(): Dirent = Dirent(dIno, dSeekoff, dReclen, dNamlen, dType, dName)

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

        public fun default(): Dirent = Dirent(dIno = 0uL, dSeekoff = 0uL, dReclen = 0u, dNamlen = 0u, dType = 0u, dName = UByteArray(1024) { 0u.toUByte() })
    }
}

public open class SockaddrStorage(
    public var ssLen: UByte,
    public var ssFamily: SaFamilyT,
    public var ssPad1: UByteArray,
    public var ssAlign: Long,
    public var ssPad2: UByteArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): SockaddrStorage = SockaddrStorage(ssLen, ssFamily, ssPad1, ssAlign, ssPad2, nonExhaustive)

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

        public fun default(): SockaddrStorage = SockaddrStorage(ssLen = 0u, ssFamily = 0u, ssPad1 = Default.default(), ssAlign = 0L, ssPad2 = UByteArray(112) { 0u.toUByte() }, nonExhaustive = Unit)
    }
}

public open class GroupReq(
    public var grInterface: UInt,
    public var grGroup: SockaddrStorage,
) {
    public open fun clone(): GroupReq = GroupReq(grInterface, grGroup)

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

        public fun default(): GroupReq = GroupReq(grInterface = 0u, grGroup = SockaddrStorage.default())
    }
}

public open class GroupSourceReq(
    public var gsrInterface: UInt,
    public var gsrGroup: SockaddrStorage,
    public var gsrSource: SockaddrStorage,
) {
    public open fun clone(): GroupSourceReq = GroupSourceReq(gsrInterface, gsrGroup, gsrSource)

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

        public fun default(): GroupSourceReq = GroupSourceReq(gsrInterface = 0u, gsrGroup = SockaddrStorage.default(), gsrSource = SockaddrStorage.default())
    }
}

public open class Utmpx(
    public var utUser: UByteArray,
    public var utId: UByteArray,
    public var utLine: UByteArray,
    public var utPid: PidT,
    public var utType: CShort,
    public var utTv: Timeval,
    public var utHost: UByteArray,
    public var utPad: UIntArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): Utmpx = Utmpx(utUser, utId, utLine, utPid, utType, utTv, utHost, utPad, nonExhaustive)

    public companion object {
        public fun default(): Utmpx = Utmpx(utUser = UByteArray(UTX_USERSIZE) { 0u.toUByte() }, utId = Default.default(), utLine = Default.default(), utPid = 0, utType = 0, utTv = Timeval.default(), utHost = UByteArray(UTX_HOSTSIZE) { 0u.toUByte() }, utPad = Default.default(), nonExhaustive = Unit)
    }
}

public open class Sigevent internal constructor(
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

    public open fun clone(): Sigevent = Sigevent(sigevNotify, sigevSigno, sigevValue, unused1, sigevNotifyAttributes, nonExhaustive)

    public companion object {
        public operator fun invoke(
            sigevNotify: CInt,
            sigevSigno: CInt,
            sigevValue: Sigval,
            unused1: MutPtr,
            sigevNotifyAttributes: MutPtr,
            nonExhaustive: Unit,
        ): Sigevent = Sigevent(
            sigevNotify = sigevNotify,
            sigevSigno = sigevSigno,
            sigevValue = sigevValue,
            unused1CtorArg = unused1,
            sigevNotifyAttributesCtorArg = sigevNotifyAttributes,
            nonExhaustive = nonExhaustive,
        )

        public fun default(): Sigevent = Sigevent(sigevNotify = 0, sigevSigno = 0, sigevValue = Sigval.default(), unused1 = MutPtr(0L), sigevNotifyAttributes = MutPtr(0L), nonExhaustive = Unit)
    }
}

// Note(exhaustive): review for 1.0
public open class ProcessorCpuLoadInfo(
    public var cpuTicks: UIntArray,
) {
    public open fun clone(): ProcessorCpuLoadInfo = ProcessorCpuLoadInfo(cpuTicks)

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

        public fun default(): ProcessorCpuLoadInfo = ProcessorCpuLoadInfo(cpuTicks = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class ProcessorBasicInfo(
    public var cpuType: CpuTypeT,
    public var cpuSubtype: CpuSubtypeT,
    public var running: BooleanT,
    public var slotNum: CInt,
    public var isMaster: BooleanT,
) {
    public open fun clone(): ProcessorBasicInfo = ProcessorBasicInfo(cpuType, cpuSubtype, running, slotNum, isMaster)

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

        public fun default(): ProcessorBasicInfo = ProcessorBasicInfo(cpuType = 0, cpuSubtype = 0, running = 0, slotNum = 0, isMaster = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ProcessorSetBasicInfo(
    public var processorCount: CInt,
    public var defaultPolicy: CInt,
) {
    public open fun clone(): ProcessorSetBasicInfo = ProcessorSetBasicInfo(processorCount, defaultPolicy)

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

        public fun default(): ProcessorSetBasicInfo = ProcessorSetBasicInfo(processorCount = 0, defaultPolicy = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ProcessorSetLoadInfo(
    public var taskCount: CInt,
    public var threadCount: CInt,
    public var loadAverage: IntegerT,
    public var machFactor: IntegerT,
) {
    public open fun clone(): ProcessorSetLoadInfo = ProcessorSetLoadInfo(taskCount, threadCount, loadAverage, machFactor)

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

        public fun default(): ProcessorSetLoadInfo = ProcessorSetLoadInfo(taskCount = 0, threadCount = 0, loadAverage = 0, machFactor = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class TimeValueT(
    public var seconds: IntegerT,
    public var microseconds: IntegerT,
) {
    public open fun clone(): TimeValueT = TimeValueT(seconds, microseconds)

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

        public fun default(): TimeValueT = TimeValueT(seconds = 0, microseconds = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadBasicInfo(
    public var userTime: TimeValueT,
    public var systemTime: TimeValueT,
    public var cpuUsage: IntegerT,
    public var policy: PolicyT,
    public var runState: IntegerT,
    public var flags: IntegerT,
    public var suspendCount: IntegerT,
    public var sleepTime: IntegerT,
) {
    public open fun clone(): ThreadBasicInfo = ThreadBasicInfo(userTime, systemTime, cpuUsage, policy, runState, flags, suspendCount, sleepTime)

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

        public fun default(): ThreadBasicInfo = ThreadBasicInfo(userTime = TimeValueT.default(), systemTime = TimeValueT.default(), cpuUsage = 0, policy = 0, runState = 0, flags = 0, suspendCount = 0, sleepTime = 0)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadIdentifierInfo(
    public var threadId: ULong,
    public var threadHandle: ULong,
    public var dispatchQaddr: ULong,
) {
    public open fun clone(): ThreadIdentifierInfo = ThreadIdentifierInfo(threadId, threadHandle, dispatchQaddr)

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

        public fun default(): ThreadIdentifierInfo = ThreadIdentifierInfo(threadId = 0uL, threadHandle = 0uL, dispatchQaddr = 0uL)
    }
}

// Note(exhaustive): review for 1.0
public open class ThreadExtendedInfo(
    public var pthUserTime: ULong,
    public var pthSystemTime: ULong,
    public var pthCpuUsage: Int,
    public var pthPolicy: Int,
    public var pthRunState: Int,
    public var pthFlags: Int,
    public var pthSleepTime: Int,
    public var pthCurpri: Int,
    public var pthPriority: Int,
    public var pthMaxpriority: Int,
    public var pthName: UByteArray,
) {
    public open fun clone(): ThreadExtendedInfo = ThreadExtendedInfo(pthUserTime, pthSystemTime, pthCpuUsage, pthPolicy, pthRunState, pthFlags, pthSleepTime, pthCurpri, pthPriority, pthMaxpriority, pthName)

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

        public fun default(): ThreadExtendedInfo = ThreadExtendedInfo(pthUserTime = 0uL, pthSystemTime = 0uL, pthCpuUsage = 0, pthPolicy = 0, pthRunState = 0, pthFlags = 0, pthSleepTime = 0, pthCurpri = 0, pthPriority = 0, pthMaxpriority = 0, pthName = UByteArray(MAXTHREADNAMESIZE) { 0u.toUByte() })
    }
}

public open class IfData64(
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
    public var ifiBaudrate: ULong,
    public var ifiIpackets: ULong,
    public var ifiIerrors: ULong,
    public var ifiOpackets: ULong,
    public var ifiOerrors: ULong,
    public var ifiCollisions: ULong,
    public var ifiIbytes: ULong,
    public var ifiObytes: ULong,
    public var ifiImcasts: ULong,
    public var ifiOmcasts: ULong,
    public var ifiIqdrops: ULong,
    public var ifiNoproto: ULong,
    public var ifiRecvtiming: UInt,
    public var ifiXmittiming: UInt,
    public var ifiLastchange: Timeval32,
) {
    public open fun clone(): IfData64 = IfData64(ifiType, ifiTypelen, ifiPhysical, ifiAddrlen, ifiHdrlen, ifiRecvquota, ifiXmitquota, ifiUnused1, ifiMtu, ifiMetric, ifiBaudrate, ifiIpackets, ifiIerrors, ifiOpackets, ifiOerrors, ifiCollisions, ifiIbytes, ifiObytes, ifiImcasts, ifiOmcasts, ifiIqdrops, ifiNoproto, ifiRecvtiming, ifiXmittiming, ifiLastchange)

    public companion object {
        public fun default(): IfData64 = IfData64(ifiType = 0u, ifiTypelen = 0u, ifiPhysical = 0u, ifiAddrlen = 0u, ifiHdrlen = 0u, ifiRecvquota = 0u, ifiXmitquota = 0u, ifiUnused1 = 0u, ifiMtu = 0u, ifiMetric = 0u, ifiBaudrate = 0uL, ifiIpackets = 0uL, ifiIerrors = 0uL, ifiOpackets = 0uL, ifiOerrors = 0uL, ifiCollisions = 0uL, ifiIbytes = 0uL, ifiObytes = 0uL, ifiImcasts = 0uL, ifiOmcasts = 0uL, ifiIqdrops = 0uL, ifiNoproto = 0uL, ifiRecvtiming = 0u, ifiXmittiming = 0u, ifiLastchange = Timeval32.default())
    }
}

public open class IfMsghdr2(
    public var ifmMsglen: CUshort,
    public var ifmVersion: CUchar,
    public var ifmType: CUchar,
    public var ifmAddrs: CInt,
    public var ifmFlags: CInt,
    public var ifmIndex: CUshort,
    public var ifmSndLen: CInt,
    public var ifmSndMaxlen: CInt,
    public var ifmSndDrops: CInt,
    public var ifmTimer: CInt,
    public var ifmData: IfData64,
) {
    public open fun clone(): IfMsghdr2 = IfMsghdr2(ifmMsglen, ifmVersion, ifmType, ifmAddrs, ifmFlags, ifmIndex, ifmSndLen, ifmSndMaxlen, ifmSndDrops, ifmTimer, ifmData)

    public companion object {
        public fun default(): IfMsghdr2 = IfMsghdr2(ifmMsglen = 0u, ifmVersion = 0u, ifmType = 0u, ifmAddrs = 0, ifmFlags = 0, ifmIndex = 0u, ifmSndLen = 0, ifmSndMaxlen = 0, ifmSndDrops = 0, ifmTimer = 0, ifmData = IfData64.default())
    }
}

public open class MachTaskBasicInfo(
    public var virtualSize: MachVmSizeT,
    public var residentSize: MachVmSizeT,
    public var residentSizeMax: MachVmSizeT,
    public var userTime: TimeValueT,
    public var systemTime: TimeValueT,
    public var policy: PolicyT,
    public var suspendCount: IntegerT,
) {
    public open fun clone(): MachTaskBasicInfo = MachTaskBasicInfo(virtualSize, residentSize, residentSizeMax, userTime, systemTime, policy, suspendCount)

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

        public fun default(): MachTaskBasicInfo = MachTaskBasicInfo(virtualSize = 0uL, residentSize = 0uL, residentSizeMax = 0uL, userTime = TimeValueT.default(), systemTime = TimeValueT.default(), policy = 0, suspendCount = 0)
    }
}

public open class Log2phys(
    public var l2pFlags: CUint,
    public var l2pContigbytes: OffT,
    public var l2pDevoffset: OffT,
) {
    public open fun clone(): Log2phys = Log2phys(l2pFlags, l2pContigbytes, l2pDevoffset)

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

        public fun default(): Log2phys = Log2phys(l2pFlags = 0u, l2pContigbytes = 0L, l2pDevoffset = 0L)
    }
}

public open class OsUnfairLockS(
    public var osUnfairLockOpaque: UInt,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): OsUnfairLockS = OsUnfairLockS(osUnfairLockOpaque, nonExhaustive)

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

        public fun default(): OsUnfairLockS = OsUnfairLockS(osUnfairLockOpaque = 0u, nonExhaustive = Unit)
    }
}

public open class SockaddrVm(
    public var svmLen: CUchar,
    public var svmFamily: SaFamilyT,
    public var svmReserved1: CUshort,
    public var svmPort: CUint,
    public var svmCid: CUint,
) {
    public open fun clone(): SockaddrVm = SockaddrVm(svmLen, svmFamily, svmReserved1, svmPort, svmCid)

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

        public fun default(): SockaddrVm = SockaddrVm(svmLen = 0u, svmFamily = 0u, svmReserved1 = 0u, svmPort = 0u, svmCid = 0u)
    }
}

// Note(exhaustive): review for 1.0
public open class Ifdevmtu(
    public var ifdmCurrent: CInt,
    public var ifdmMin: CInt,
    public var ifdmMax: CInt,
) {
    public open fun clone(): Ifdevmtu = Ifdevmtu(ifdmCurrent, ifdmMin, ifdmMax)

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

        public fun default(): Ifdevmtu = Ifdevmtu(ifdmCurrent = 0, ifdmMin = 0, ifdmMax = 0)
    }
}

public open class Ifkpi(
    public var ifkModuleId: CUint,
    public var ifkType: CUint,
    public var ifkData: CAnonymousIfkData,
) {
    public open fun clone(): Ifkpi = Ifkpi(ifkModuleId, ifkType, ifkData)

    public companion object {
        public fun default(): Ifkpi = Ifkpi(ifkModuleId = 0u, ifkType = 0u, ifkData = CAnonymousIfkData.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Ifreq(
    public var ifrName: UByteArray,
    public var ifrIfru: CAnonymousIfrIfru,
) {
    public open fun clone(): Ifreq = Ifreq(ifrName, ifrIfru)

    public companion object {
        public fun default(): Ifreq = Ifreq(ifrName = Default.default(), ifrIfru = CAnonymousIfrIfru.default())
    }
}

public open class Ifaliasreq(
    public var ifraName: UByteArray,
    public var ifraAddr: Sockaddr,
    public var ifraBroadaddr: Sockaddr,
    public var ifraMask: Sockaddr,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): Ifaliasreq = Ifaliasreq(ifraName, ifraAddr, ifraBroadaddr, ifraMask, nonExhaustive)

    public companion object {
        public fun default(): Ifaliasreq = Ifaliasreq(ifraName = Default.default(), ifraAddr = Sockaddr.default(), ifraBroadaddr = Sockaddr.default(), ifraMask = Sockaddr.default(), nonExhaustive = Unit)
    }
}

public open class In6Ifreq(
    public var ifrName: UByteArray,
    public var ifrIfru: CAnonymousIfrIfru6,
) {
    public open fun clone(): In6Ifreq = In6Ifreq(ifrName, ifrIfru)

    public companion object {
        public fun default(): In6Ifreq = In6Ifreq(ifrName = Default.default(), ifrIfru = CAnonymousIfrIfru6.default())
    }
}

public open class Ifconf(
    public var ifcLen: CInt,
    public var ifcIfcu: CAnonymousIfcIfcu,
) {
    public open fun clone(): Ifconf = Ifconf(ifcLen, ifcIfcu)

    public open fun eq(other: Ifconf): Boolean {
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

    public companion object {
        public fun default(): Ifconf = Ifconf(ifcLen = 0, ifcIfcu = CAnonymousIfcIfcu.default())
    }
}

public class CAnonymousIfkData internal constructor(
    ifkPtrCtorArg: MutPtr?,
    public var ifkValue: CInt?,
) {
    public var ifkPtr: MutPtr? = ifkPtrCtorArg
        internal set

    public constructor() : this(
        ifkPtrCtorArg = null,
        ifkValue = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ifk_data").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun eq(other: CAnonymousIfkData): Boolean = ((this.ifkPtr).rawAddress.toULong() == (other.ifkPtr).rawAddress.toULong()) && this.ifkValue == other.ifkValue

    public override fun hashCode(): Int {
        var state = 0
        run {
            state = 31 * state + (this.ifkPtr).hashCode()
            state = 31 * state + (this.ifkValue).hashCode()
        }
        return state
    }

    public companion object {
        public operator fun invoke(
            ifkPtr: MutPtr? = null,
            ifkValue: CInt? = null,
        ): CAnonymousIfkData = CAnonymousIfkData(
            ifkPtrCtorArg = ifkPtr,
            ifkValue = ifkValue,
        )

        public fun default(): CAnonymousIfkData = CAnonymousIfkData()
    }
}

public class CAnonymousIfrIfru internal constructor(
    public var ifruAddr: Sockaddr?,
    public var ifruDstaddr: Sockaddr?,
    public var ifruBroadaddr: Sockaddr?,
    public var ifruFlags: CShort?,
    public var ifruMetrics: CInt?,
    public var ifruMtu: CInt?,
    public var ifruPhys: CInt?,
    public var ifruMedia: CInt?,
    public var ifruIntval: CInt?,
    ifruDataCtorArg: MutPtr?,
    public var ifruDevmtu: Ifdevmtu?,
    public var ifruKpi: Ifkpi?,
    public var ifruWakeFlags: UInt?,
    public var ifruRouteRefcnt: UInt?,
    public var ifruCap: IntArray?,
    public var ifruFunctionalType: UInt?,
) {
    public var ifruData: MutPtr? = ifruDataCtorArg
        internal set

    public constructor() : this(
        ifruAddr = null,
        ifruDstaddr = null,
        ifruBroadaddr = null,
        ifruFlags = null,
        ifruMetrics = null,
        ifruMtu = null,
        ifruPhys = null,
        ifruMedia = null,
        ifruIntval = null,
        ifruDataCtorArg = null,
        ifruDevmtu = null,
        ifruKpi = null,
        ifruWakeFlags = null,
        ifruRouteRefcnt = null,
        ifruCap = null,
        ifruFunctionalType = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ifr_ifru").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun eq(other: CAnonymousIfrIfru): Boolean = this.ifruAddr == other.ifruAddr && this.ifruDstaddr == other.ifruDstaddr && this.ifruBroadaddr == other.ifruBroadaddr && this.ifruFlags == other.ifruFlags && this.ifruMetrics == other.ifruMetrics && this.ifruMtu == other.ifruMtu && this.ifruPhys == other.ifruPhys && this.ifruMedia == other.ifruMedia && this.ifruIntval == other.ifruIntval && ((this.ifruData).rawAddress.toULong() == (other.ifruData).rawAddress.toULong()) && this.ifruDevmtu == other.ifruDevmtu && this.ifruKpi == other.ifruKpi && this.ifruWakeFlags == other.ifruWakeFlags && this.ifruRouteRefcnt == other.ifruRouteRefcnt && (this.ifruCap.zip(other.ifruCap)).all({ (a, b) -> a == b }) && this.ifruFunctionalType == other.ifruFunctionalType

    public override fun hashCode(): Int {
        var state = 0
        run {
            state = 31 * state + (this.ifruAddr).hashCode()
            state = 31 * state + (this.ifruDstaddr).hashCode()
            state = 31 * state + (this.ifruBroadaddr).hashCode()
            state = 31 * state + (this.ifruFlags).hashCode()
            state = 31 * state + (this.ifruMetrics).hashCode()
            state = 31 * state + (this.ifruMtu).hashCode()
            state = 31 * state + (this.ifruPhys).hashCode()
            state = 31 * state + (this.ifruMedia).hashCode()
            state = 31 * state + (this.ifruIntval).hashCode()
            state = 31 * state + (this.ifruData).hashCode()
            state = 31 * state + (this.ifruDevmtu).hashCode()
            state = 31 * state + (this.ifruKpi).hashCode()
            state = 31 * state + (this.ifruWakeFlags).hashCode()
            state = 31 * state + (this.ifruRouteRefcnt).hashCode()
            state = 31 * state + (this.ifruCap).hashCode()
            state = 31 * state + (this.ifruFunctionalType).hashCode()
        }
        return state
    }

    public companion object {
        public operator fun invoke(
            ifruAddr: Sockaddr? = null,
            ifruDstaddr: Sockaddr? = null,
            ifruBroadaddr: Sockaddr? = null,
            ifruFlags: CShort? = null,
            ifruMetrics: CInt? = null,
            ifruMtu: CInt? = null,
            ifruPhys: CInt? = null,
            ifruMedia: CInt? = null,
            ifruIntval: CInt? = null,
            ifruData: MutPtr? = null,
            ifruDevmtu: Ifdevmtu? = null,
            ifruKpi: Ifkpi? = null,
            ifruWakeFlags: UInt? = null,
            ifruRouteRefcnt: UInt? = null,
            ifruCap: IntArray? = null,
            ifruFunctionalType: UInt? = null,
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

        public fun default(): CAnonymousIfrIfru = CAnonymousIfrIfru()
    }
}

public class CAnonymousIfcIfcu internal constructor(
    ifcuBufCtorArg: MutPtr?,
    ifcuReqCtorArg: MutPtr?,
) {
    public var ifcuBuf: MutPtr? = ifcuBufCtorArg
        internal set

    public var ifcuReq: MutPtr? = ifcuReqCtorArg
        internal set

    public constructor() : this(
        ifcuBufCtorArg = null,
        ifcuReqCtorArg = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ifc_ifcu").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun eq(other: CAnonymousIfcIfcu): Boolean = ((this.ifcuBuf).rawAddress.toULong() == (other.ifcuBuf).rawAddress.toULong()) && ((this.ifcuReq).rawAddress.toULong() == (other.ifcuReq).rawAddress.toULong())

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.ifcuBuf).hashCode()
        state = 31 * state + (this.ifcuReq).hashCode()
        return state
    }

    public companion object {
        public operator fun invoke(
            ifcuBuf: MutPtr? = null,
            ifcuReq: MutPtr? = null,
        ): CAnonymousIfcIfcu = CAnonymousIfcIfcu(
            ifcuBufCtorArg = ifcuBuf,
            ifcuReqCtorArg = ifcuReq,
        )

        public fun default(): CAnonymousIfcIfcu = CAnonymousIfcIfcu()
    }
}

public class CAnonymousIfrIfru6 internal constructor(
    public var ifruAddr: SockaddrIn6?,
    public var ifruDstaddr: SockaddrIn6?,
    public var ifruFlags: CInt?,
    public var ifruFlags6: CInt?,
    public var ifruMetrics: CInt?,
    public var ifruIntval: CInt?,
    ifruDataCtorArg: MutPtr?,
    public var ifruLifetime: In6Addrlifetime?,
    public var ifruStat: In6Ifstat?,
    public var ifruIcmp6stat: Icmp6Ifstat?,
    public var ifruScopeId: UIntArray?,
) {
    public var ifruData: MutPtr? = ifruDataCtorArg
        internal set

    public constructor() : this(
        ifruAddr = null,
        ifruDstaddr = null,
        ifruFlags = null,
        ifruFlags6 = null,
        ifruMetrics = null,
        ifruIntval = null,
        ifruDataCtorArg = null,
        ifruLifetime = null,
        ifruStat = null,
        ifruIcmp6stat = null,
        ifruScopeId = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ifr_ifru6").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun eq(other: CAnonymousIfrIfru6): Boolean = this.ifruAddr == other.ifruAddr && this.ifruDstaddr == other.ifruDstaddr && this.ifruFlags == other.ifruFlags && this.ifruFlags6 == other.ifruFlags6 && this.ifruMetrics == other.ifruMetrics && this.ifruIntval == other.ifruIntval && ((this.ifruData).rawAddress.toULong() == (other.ifruData).rawAddress.toULong()) && (this.ifruScopeId.zip(other.ifruScopeId)).all({ (a, b) -> a == b })

    public override fun hashCode(): Int {
        var state = 0
        run {
            state = 31 * state + (this.ifruAddr).hashCode()
            state = 31 * state + (this.ifruDstaddr).hashCode()
            state = 31 * state + (this.ifruFlags).hashCode()
            state = 31 * state + (this.ifruFlags6).hashCode()
            state = 31 * state + (this.ifruMetrics).hashCode()
            state = 31 * state + (this.ifruIntval).hashCode()
            state = 31 * state + (this.ifruData).hashCode()
            state = 31 * state + (this.ifruScopeId).hashCode()
        }
        return state
    }

    public companion object {
        public operator fun invoke(
            ifruAddr: SockaddrIn6? = null,
            ifruDstaddr: SockaddrIn6? = null,
            ifruFlags: CInt? = null,
            ifruFlags6: CInt? = null,
            ifruMetrics: CInt? = null,
            ifruIntval: CInt? = null,
            ifruData: MutPtr? = null,
            ifruLifetime: In6Addrlifetime? = null,
            ifruStat: In6Ifstat? = null,
            ifruIcmp6stat: Icmp6Ifstat? = null,
            ifruScopeId: UIntArray? = null,
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

        public fun default(): CAnonymousIfrIfru6 = CAnonymousIfrIfru6()
    }
}

public class Semun internal constructor(
    public var value: CInt?,
    bufCtorArg: MutPtr?,
    arrayCtorArg: MutPtr?,
) {
    public var buf: MutPtr? = bufCtorArg
        internal set

    public var array: MutPtr? = arrayCtorArg
        internal set

    public constructor() : this(
        value = null,
        bufCtorArg = null,
        arrayCtorArg = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "semun").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun eq(other: Semun): Boolean = this.value == other.value

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.value).hashCode()
        return state
    }

    public companion object {
        public operator fun invoke(
            value: CInt? = null,
            buf: MutPtr? = null,
            array: MutPtr? = null,
        ): Semun = Semun(
            value = value,
            bufCtorArg = buf,
            arrayCtorArg = array,
        )
    }
}
