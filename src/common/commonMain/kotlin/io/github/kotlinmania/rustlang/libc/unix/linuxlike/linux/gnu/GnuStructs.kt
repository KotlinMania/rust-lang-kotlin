@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.libc.unix.CcT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf32Word
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf64Word
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf64Xword
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Sigevent
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SpeedT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.TcflagT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.ETH_ALEN
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.FanotifyEventInfoHeader
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.NCCS

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

public open class Aiocb internal constructor(
    public var aioFildes: CInt,
    public var aioLioOpcode: CInt,
    public var aioReqprio: CInt,
    aioBufCtorArg: MutPtr,
    public var aioNbytes: SizeT,
    public var aioSigevent: Sigevent,
    nextPrioCtorArg: MutPtr,
    public var absPrio: CInt,
    public var policy: CInt,
    public var errorCode: CInt,
    public var returnValue: SsizeT,
    public var aioOffset: OffT,
    public var glibcReserved: UByteArray,
) {
    public var aioBuf: MutPtr = aioBufCtorArg
        internal set

    public var nextPrio: MutPtr = nextPrioCtorArg
        internal set

    public open fun clone(): Aiocb = Aiocb(aioFildes, aioLioOpcode, aioReqprio, aioBuf, aioNbytes, aioSigevent, nextPrio, absPrio, policy, errorCode, returnValue, aioOffset, glibcReserved)

    public companion object {
        public operator fun invoke(
            aioFildes: CInt,
            aioLioOpcode: CInt,
            aioReqprio: CInt,
            aioBuf: MutPtr,
            aioNbytes: SizeT,
            aioSigevent: Sigevent,
            nextPrio: MutPtr,
            absPrio: CInt,
            policy: CInt,
            errorCode: CInt,
            returnValue: SsizeT,
            aioOffset: OffT,
            glibcReserved: UByteArray,
        ): Aiocb = Aiocb(
            aioFildes = aioFildes,
            aioLioOpcode = aioLioOpcode,
            aioReqprio = aioReqprio,
            aioBufCtorArg = aioBuf,
            aioNbytes = aioNbytes,
            aioSigevent = aioSigevent,
            nextPrioCtorArg = nextPrio,
            absPrio = absPrio,
            policy = policy,
            errorCode = errorCode,
            returnValue = returnValue,
            aioOffset = aioOffset,
            glibcReserved = glibcReserved,
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

public open class Timeval(
    public var tvSec: Int,
    public var tvUsec: Int,
) {
    public open fun clone(): Timeval = Timeval(tvSec, tvUsec)

    public companion object {
        public fun typeLayout(): TypeLayout<Timeval> =
            object : TypeLayout<Timeval> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Timeval) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.tvUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timeval) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.tvUsec)
                }
            }
    }
}

public open class Glob64T internal constructor(
    public var glPathc: SizeT,
    glPathvCtorArg: MutPtr,
    public var glOffs: SizeT,
    public var glFlags: CInt,
    unused1CtorArg: MutPtr,
    unused2CtorArg: MutPtr,
    unused3CtorArg: MutPtr,
    unused4CtorArg: MutPtr,
    unused5CtorArg: MutPtr,
) {
    public var glPathv: MutPtr = glPathvCtorArg
        internal set

    public var unused1: MutPtr = unused1CtorArg
        internal set

    public var unused2: MutPtr = unused2CtorArg
        internal set

    public var unused3: MutPtr = unused3CtorArg
        internal set

    public var unused4: MutPtr = unused4CtorArg
        internal set

    public var unused5: MutPtr = unused5CtorArg
        internal set

    public open fun clone(): Glob64T = Glob64T(glPathc, glPathv, glOffs, glFlags, unused1, unused2, unused3, unused4, unused5)

    public companion object {
        public fun typeLayout(): TypeLayout<Glob64T> =
            object : TypeLayout<Glob64T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): Glob64T =
                    Glob64T(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                    )
                override fun writeNative(addr: Long, value: Glob64T) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.glPathc)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.glPathv)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.glOffs)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.glFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.unused1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.unused2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.unused3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.unused4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.unused5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Glob64T =
                    Glob64T(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Glob64T) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.glPathc)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.glPathv)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.glOffs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.glFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.unused2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.unused3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.unused4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.unused5)
                }
            }

        public operator fun invoke(
            glPathc: SizeT,
            glPathv: MutPtr,
            glOffs: SizeT,
            glFlags: CInt,
            unused1: MutPtr,
            unused2: MutPtr,
            unused3: MutPtr,
            unused4: MutPtr,
            unused5: MutPtr,
        ): Glob64T = Glob64T(
            glPathc = glPathc,
            glPathvCtorArg = glPathv,
            glOffs = glOffs,
            glFlags = glFlags,
            unused1CtorArg = unused1,
            unused2CtorArg = unused2,
            unused3CtorArg = unused3,
            unused4CtorArg = unused4,
            unused5CtorArg = unused5,
        )
    }
}

public open class Msghdr internal constructor(
    msgNameCtorArg: MutPtr,
    public var msgNamelen: SocklenT,
    msgIovCtorArg: MutPtr,
    public var msgIovlen: SizeT,
    msgControlCtorArg: MutPtr,
    public var msgControllen: SizeT,
    public var msgFlags: CInt,
) {
    public var msgName: MutPtr = msgNameCtorArg
        internal set

    public var msgIov: MutPtr = msgIovCtorArg
        internal set

    public var msgControl: MutPtr = msgControlCtorArg
        internal set

    public open fun clone(): Msghdr = Msghdr(msgName, msgNamelen, msgIov, msgIovlen, msgControl, msgControllen, msgFlags)

    public companion object {
        public fun typeLayout(): TypeLayout<Msghdr> =
            object : TypeLayout<Msghdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.msgName)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.msgIov)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.msgIovlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.msgControl)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.msgControllen)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.msgFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.msgName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.msgIov)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.msgIovlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.msgControl)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.msgControllen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.msgFlags)
                }
            }

        public operator fun invoke(
            msgName: MutPtr,
            msgNamelen: SocklenT,
            msgIov: MutPtr,
            msgIovlen: SizeT,
            msgControl: MutPtr,
            msgControllen: SizeT,
            msgFlags: CInt,
        ): Msghdr = Msghdr(
            msgNameCtorArg = msgName,
            msgNamelen = msgNamelen,
            msgIovCtorArg = msgIov,
            msgIovlen = msgIovlen,
            msgControlCtorArg = msgControl,
            msgControllen = msgControllen,
            msgFlags = msgFlags,
        )
    }
}

public open class Cmsghdr(
    public var cmsgLen: SizeT,
    public var cmsgLevel: CInt,
    public var cmsgType: CInt,
) {
    public open fun clone(): Cmsghdr = Cmsghdr(cmsgLen, cmsgLevel, cmsgType)

    public companion object {
        public fun typeLayout(): TypeLayout<Cmsghdr> =
            object : TypeLayout<Cmsghdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Cmsghdr) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cmsgType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cmsghdr) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cmsgType)
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

public open class Mallinfo(
    public var arena: CInt,
    public var ordblks: CInt,
    public var smblks: CInt,
    public var hblks: CInt,
    public var hblkhd: CInt,
    public var usmblks: CInt,
    public var fsmblks: CInt,
    public var uordblks: CInt,
    public var fordblks: CInt,
    public var keepcost: CInt,
) {
    public open fun clone(): Mallinfo = Mallinfo(arena, ordblks, smblks, hblks, hblkhd, usmblks, fsmblks, uordblks, fordblks, keepcost)

    public companion object {
        public fun typeLayout(): TypeLayout<Mallinfo> =
            object : TypeLayout<Mallinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): Mallinfo =
                    Mallinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: Mallinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.arena)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ordblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.smblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.hblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.hblkhd)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.usmblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.fsmblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.uordblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.fordblks)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.keepcost)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mallinfo =
                    Mallinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mallinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.arena)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ordblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.smblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.hblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.hblkhd)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.usmblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.fsmblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.uordblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.fordblks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.keepcost)
                }
            }
    }
}

public open class Mallinfo2(
    public var arena: SizeT,
    public var ordblks: SizeT,
    public var smblks: SizeT,
    public var hblks: SizeT,
    public var hblkhd: SizeT,
    public var usmblks: SizeT,
    public var fsmblks: SizeT,
    public var uordblks: SizeT,
    public var fordblks: SizeT,
    public var keepcost: SizeT,
) {
    public open fun clone(): Mallinfo2 = Mallinfo2(arena, ordblks, smblks, hblks, hblkhd, usmblks, fsmblks, uordblks, fordblks, keepcost)

    public companion object {
        public fun typeLayout(): TypeLayout<Mallinfo2> =
            object : TypeLayout<Mallinfo2> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): Mallinfo2 =
                    Mallinfo2(
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
                    )
                override fun writeNative(addr: Long, value: Mallinfo2) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.arena)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ordblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.smblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.hblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.hblkhd)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.usmblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fsmblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.uordblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.fordblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.keepcost)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mallinfo2 =
                    Mallinfo2(
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
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mallinfo2) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.arena)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ordblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.smblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.hblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.hblkhd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.usmblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fsmblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.uordblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.fordblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.keepcost)
                }
            }
    }
}

public open class Ntptimeval(
    public var time: Timeval,
    public var maxerror: CLong,
    public var esterror: CLong,
    public var tai: CLong,
    public var glibcReserved1: CLong,
    public var glibcReserved2: CLong,
    public var glibcReserved3: CLong,
    public var glibcReserved4: CLong,
) {
    public open fun clone(): Ntptimeval = Ntptimeval(time, maxerror, esterror, tai, glibcReserved1, glibcReserved2, glibcReserved3, glibcReserved4)

    public companion object {
        public fun typeLayout(): TypeLayout<Ntptimeval> =
            object : TypeLayout<Ntptimeval> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Ntptimeval =
                    Ntptimeval(
                        (Timeval.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: Ntptimeval) {
                    (Timeval.typeLayout()).writeNative(addr, value.time)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.maxerror)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.esterror)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.tai)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.glibcReserved1)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.glibcReserved2)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.glibcReserved3)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.glibcReserved4)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ntptimeval =
                    Ntptimeval(
                        (Timeval.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ntptimeval) {
                    (Timeval.typeLayout()).writeBytes(buf, offset, value.time)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.maxerror)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.esterror)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.tai)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.glibcReserved1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.glibcReserved2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.glibcReserved3)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.glibcReserved4)
                }
            }
    }
}

public open class RegexT internal constructor(
    bufferCtorArg: MutPtr,
    public var allocated: SizeT,
    public var used: SizeT,
    public var syntax: CUlong,
    fastmapCtorArg: MutPtr,
    translateCtorArg: MutPtr,
    public var reNsub: SizeT,
    public var bitfield: UByte,
) {
    public var buffer: MutPtr = bufferCtorArg
        internal set

    public var fastmap: MutPtr = fastmapCtorArg
        internal set

    public var translate: MutPtr = translateCtorArg
        internal set

    public open fun clone(): RegexT = RegexT(buffer, allocated, used, syntax, fastmap, translate, reNsub, bitfield)

    public companion object {
        public fun typeLayout(): TypeLayout<RegexT> =
            object : TypeLayout<RegexT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): RegexT =
                    RegexT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U8).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: RegexT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.buffer)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.allocated)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.used)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.syntax)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.fastmap)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.translate)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.reNsub)
                    (PrimitiveLayouts.U8).writeNative(addr + 56L, value.bitfield)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RegexT =
                    RegexT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RegexT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.buffer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.allocated)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.used)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.syntax)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.fastmap)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.translate)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.reNsub)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 56, value.bitfield)
                }
            }

        public operator fun invoke(
            buffer: MutPtr,
            allocated: SizeT,
            used: SizeT,
            syntax: CUlong,
            fastmap: MutPtr,
            translate: MutPtr,
            reNsub: SizeT,
            bitfield: UByte,
        ): RegexT = RegexT(
            bufferCtorArg = buffer,
            allocated = allocated,
            used = used,
            syntax = syntax,
            fastmapCtorArg = fastmap,
            translateCtorArg = translate,
            reNsub = reNsub,
            bitfield = bitfield,
        )
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

public open class Seminfo(
    public var semmap: CInt,
    public var semmni: CInt,
    public var semmns: CInt,
    public var semmnu: CInt,
    public var semmsl: CInt,
    public var semopm: CInt,
    public var semume: CInt,
    public var semusz: CInt,
    public var semvmx: CInt,
    public var semaem: CInt,
) {
    public open fun clone(): Seminfo = Seminfo(semmap, semmni, semmns, semmnu, semmsl, semopm, semume, semusz, semvmx, semaem)

    public companion object {
        public fun typeLayout(): TypeLayout<Seminfo> =
            object : TypeLayout<Seminfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): Seminfo =
                    Seminfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: Seminfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.semmap)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.semmni)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.semmns)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.semmnu)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.semmsl)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.semopm)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.semume)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.semusz)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.semvmx)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.semaem)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Seminfo =
                    Seminfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Seminfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.semmap)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.semmni)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.semmns)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.semmnu)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.semmsl)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.semopm)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.semume)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.semusz)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.semvmx)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.semaem)
                }
            }
    }
}

public open class PtracePeeksiginfoArgs(
    public var off: U64,
    public var flags: U32,
    public var nr: S32,
) {
    public open fun clone(): PtracePeeksiginfoArgs = PtracePeeksiginfoArgs(off, flags, nr)

    public companion object {
        public fun typeLayout(): TypeLayout<PtracePeeksiginfoArgs> =
            object : TypeLayout<PtracePeeksiginfoArgs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): PtracePeeksiginfoArgs =
                    PtracePeeksiginfoArgs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: PtracePeeksiginfoArgs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.off)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.flags)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.nr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtracePeeksiginfoArgs =
                    PtracePeeksiginfoArgs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtracePeeksiginfoArgs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.off)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.flags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.nr)
                }
            }
    }
}

public open class CAnonymousPtraceSyscallInfoEntry(
    public var nr: U64,
    public var args: ULongArray,
) {
    public open fun clone(): CAnonymousPtraceSyscallInfoEntry = CAnonymousPtraceSyscallInfoEntry(nr, args)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousPtraceSyscallInfoEntry> =
            object : TypeLayout<CAnonymousPtraceSyscallInfoEntry> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): CAnonymousPtraceSyscallInfoEntry =
                    CAnonymousPtraceSyscallInfoEntry(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(6)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousPtraceSyscallInfoEntry) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.nr)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeNative(addr + 8L, value.args)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousPtraceSyscallInfoEntry =
                    CAnonymousPtraceSyscallInfoEntry(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(6)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousPtraceSyscallInfoEntry) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.nr)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeBytes(buf, offset + 8, value.args)
                }
            }
    }
}

public open class CAnonymousPtraceSyscallInfoExit(
    public var sval: S64,
    public var isError: U8,
) {
    public open fun clone(): CAnonymousPtraceSyscallInfoExit = CAnonymousPtraceSyscallInfoExit(sval, isError)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousPtraceSyscallInfoExit> =
            object : TypeLayout<CAnonymousPtraceSyscallInfoExit> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousPtraceSyscallInfoExit =
                    CAnonymousPtraceSyscallInfoExit(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousPtraceSyscallInfoExit) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sval)
                    (PrimitiveLayouts.U8).writeNative(addr + 8L, value.isError)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousPtraceSyscallInfoExit =
                    CAnonymousPtraceSyscallInfoExit(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousPtraceSyscallInfoExit) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sval)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 8, value.isError)
                }
            }
    }
}

public open class CAnonymousPtraceSyscallInfoSeccomp(
    public var nr: U64,
    public var args: ULongArray,
    public var retData: U32,
    public var reserved2: U32,
) {
    public open fun clone(): CAnonymousPtraceSyscallInfoSeccomp = CAnonymousPtraceSyscallInfoSeccomp(nr, args, retData, reserved2)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousPtraceSyscallInfoSeccomp> =
            object : TypeLayout<CAnonymousPtraceSyscallInfoSeccomp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): CAnonymousPtraceSyscallInfoSeccomp =
                    CAnonymousPtraceSyscallInfoSeccomp(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(6)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 60L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousPtraceSyscallInfoSeccomp) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.nr)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeNative(addr + 8L, value.args)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.retData)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.reserved2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousPtraceSyscallInfoSeccomp =
                    CAnonymousPtraceSyscallInfoSeccomp(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(6)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 60),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousPtraceSyscallInfoSeccomp) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.nr)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeBytes(buf, offset + 8, value.args)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.retData)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.reserved2)
                }
            }
    }
}

public open class PtraceSyscallInfo(
    public var op: U8,
    public var reserved: U8,
    public var flags: U16,
    public var arch: U32,
    public var instructionPointer: U64,
    public var stackPointer: U64,
    public var u: CAnonymousPtraceSyscallInfoData,
) {
    public open fun clone(): PtraceSyscallInfo = PtraceSyscallInfo(op, reserved, flags, arch, instructionPointer, stackPointer, u)
}

public open class PtraceSudConfig(
    public var mode: U64,
    public var selector: U64,
    public var offset: U64,
    public var len: U64,
) {
    public open fun clone(): PtraceSudConfig = PtraceSudConfig(mode, selector, offset, len)

    public companion object {
        public fun typeLayout(): TypeLayout<PtraceSudConfig> =
            object : TypeLayout<PtraceSudConfig> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): PtraceSudConfig =
                    PtraceSudConfig(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: PtraceSudConfig) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.mode)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.selector)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.offset)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtraceSudConfig =
                    PtraceSudConfig(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtraceSudConfig) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.mode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.selector)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.offset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.len)
                }
            }
    }
}

public open class Iocb(
    public var aioData: U64,
    public var aioKey: U32,
    public var aioRwFlags: KernelRwfT,
    public var aioLioOpcode: U16,
    public var aioReqprio: S16,
    public var aioFildes: U32,
    public var aioBuf: U64,
    public var aioNbytes: U64,
    public var aioOffset: S64,
    public var aioReserved2: U64,
    public var aioFlags: U32,
    public var aioResfd: U32,
) {
    public open fun clone(): Iocb = Iocb(aioData, aioKey, aioRwFlags, aioLioOpcode, aioReqprio, aioFildes, aioBuf, aioNbytes, aioOffset, aioReserved2, aioFlags, aioResfd)

    public companion object {
        public fun typeLayout(): TypeLayout<Iocb> =
            object : TypeLayout<Iocb> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Iocb =
                    Iocb(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.I16).readNative(addr + 18L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 60L),
                    )
                override fun writeNative(addr: Long, value: Iocb) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.aioData)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.aioKey)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.aioRwFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.aioLioOpcode)
                    (PrimitiveLayouts.I16).writeNative(addr + 18L, value.aioReqprio)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.aioFildes)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.aioBuf)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.aioNbytes)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.aioOffset)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.aioReserved2)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.aioFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.aioResfd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Iocb =
                    Iocb(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 60),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Iocb) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.aioData)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.aioKey)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.aioRwFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.aioLioOpcode)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 18, value.aioReqprio)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.aioFildes)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.aioBuf)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.aioNbytes)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.aioOffset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.aioReserved2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.aioFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.aioResfd)
                }
            }
    }
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
    public var tcpiRcvWnd: UInt,
    public var tcpiRehash: UInt,
    public var tcpiTotalRto: UShort,
    public var tcpiTotalRtoRecoveries: UShort,
    public var tcpiTotalRtoTime: UInt,
    public var tcpiReceivedCe: UInt,
    public var tcpiDeliveredE1Bytes: UInt,
    public var tcpiDeliveredE0Bytes: UInt,
    public var tcpiDeliveredCeBytes: UInt,
    public var tcpiReceivedE1Bytes: UInt,
    public var tcpiReceivedE0Bytes: UInt,
    public var tcpiReceivedCeBytes: UInt,
    public var tcpiAccecnFailMode: UShort,
    public var tcpiAccecnOptSeen: UShort,
) {
    public open fun clone(): TcpInfo = TcpInfo(tcpiState, tcpiCaState, tcpiRetransmits, tcpiProbes, tcpiBackoff, tcpiOptions, tcpiSndRcvWscale, tcpiRto, tcpiAto, tcpiSndMss, tcpiRcvMss, tcpiUnacked, tcpiSacked, tcpiLost, tcpiRetrans, tcpiFackets, tcpiLastDataSent, tcpiLastAckSent, tcpiLastDataRecv, tcpiLastAckRecv, tcpiPmtu, tcpiRcvSsthresh, tcpiRtt, tcpiRttvar, tcpiSndSsthresh, tcpiSndCwnd, tcpiAdvmss, tcpiReordering, tcpiRcvRtt, tcpiRcvSpace, tcpiTotalRetrans, tcpiPacingRate, tcpiMaxPacingRate, tcpiBytesAcked, tcpiBytesReceived, tcpiSegsOut, tcpiSegsIn, tcpiNotsentBytes, tcpiMinRtt, tcpiDataSegsIn, tcpiDataSegsOut, tcpiDeliveryRate, tcpiBusyTime, tcpiRwndLimited, tcpiSndbufLimited, tcpiDelivered, tcpiDeliveredCe, tcpiBytesSent, tcpiBytesRetrans, tcpiDsackDups, tcpiReordSeen, tcpiRcvOoopack, tcpiSndWnd, tcpiRcvWnd, tcpiRehash, tcpiTotalRto, tcpiTotalRtoRecoveries, tcpiTotalRtoTime, tcpiReceivedCe, tcpiDeliveredE1Bytes, tcpiDeliveredE0Bytes, tcpiDeliveredCeBytes, tcpiReceivedE1Bytes, tcpiReceivedE0Bytes, tcpiReceivedCeBytes, tcpiAccecnFailMode, tcpiAccecnOptSeen)

    public companion object {
        public fun typeLayout(): TypeLayout<TcpInfo> =
            object : TypeLayout<TcpInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 280L
                override fun readNative(addr: Long): TcpInfo =
                    TcpInfo(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
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
                        (PrimitiveLayouts.U32).readNative(addr + 232L),
                        (PrimitiveLayouts.U32).readNative(addr + 236L),
                        (PrimitiveLayouts.U16).readNative(addr + 240L),
                        (PrimitiveLayouts.U16).readNative(addr + 242L),
                        (PrimitiveLayouts.U32).readNative(addr + 244L),
                        (PrimitiveLayouts.U32).readNative(addr + 248L),
                        (PrimitiveLayouts.U32).readNative(addr + 252L),
                        (PrimitiveLayouts.U32).readNative(addr + 256L),
                        (PrimitiveLayouts.U32).readNative(addr + 260L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                        (PrimitiveLayouts.U32).readNative(addr + 268L),
                        (PrimitiveLayouts.U32).readNative(addr + 272L),
                        (PrimitiveLayouts.U16).readNative(addr + 276L),
                        (PrimitiveLayouts.U16).readNative(addr + 278L),
                    )
                override fun writeNative(addr: Long, value: TcpInfo) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.tcpiState)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.tcpiCaState)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.tcpiRetransmits)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.tcpiProbes)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.tcpiBackoff)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.tcpiOptions)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.tcpiSndRcvWscale)
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
                    (PrimitiveLayouts.U32).writeNative(addr + 232L, value.tcpiRcvWnd)
                    (PrimitiveLayouts.U32).writeNative(addr + 236L, value.tcpiRehash)
                    (PrimitiveLayouts.U16).writeNative(addr + 240L, value.tcpiTotalRto)
                    (PrimitiveLayouts.U16).writeNative(addr + 242L, value.tcpiTotalRtoRecoveries)
                    (PrimitiveLayouts.U32).writeNative(addr + 244L, value.tcpiTotalRtoTime)
                    (PrimitiveLayouts.U32).writeNative(addr + 248L, value.tcpiReceivedCe)
                    (PrimitiveLayouts.U32).writeNative(addr + 252L, value.tcpiDeliveredE1Bytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 256L, value.tcpiDeliveredE0Bytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 260L, value.tcpiDeliveredCeBytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.tcpiReceivedE1Bytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 268L, value.tcpiReceivedE0Bytes)
                    (PrimitiveLayouts.U32).writeNative(addr + 272L, value.tcpiReceivedCeBytes)
                    (PrimitiveLayouts.U16).writeNative(addr + 276L, value.tcpiAccecnFailMode)
                    (PrimitiveLayouts.U16).writeNative(addr + 278L, value.tcpiAccecnOptSeen)
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
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 236),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 240),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 242),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 244),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 248),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 252),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 260),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 268),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 272),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 276),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 278),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TcpInfo) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.tcpiState)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.tcpiCaState)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.tcpiRetransmits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.tcpiProbes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.tcpiBackoff)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.tcpiOptions)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.tcpiSndRcvWscale)
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
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 232, value.tcpiRcvWnd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 236, value.tcpiRehash)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 240, value.tcpiTotalRto)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 242, value.tcpiTotalRtoRecoveries)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 244, value.tcpiTotalRtoTime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 248, value.tcpiReceivedCe)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 252, value.tcpiDeliveredE1Bytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 256, value.tcpiDeliveredE0Bytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 260, value.tcpiDeliveredCeBytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.tcpiReceivedE1Bytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 268, value.tcpiReceivedE0Bytes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 272, value.tcpiReceivedCeBytes)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 276, value.tcpiAccecnFailMode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 278, value.tcpiAccecnOptSeen)
                }
            }
    }
}

public open class FanotifyEventInfoPidfd(
    public var hdr: FanotifyEventInfoHeader,
    public var pidfd: S32,
) {
    public open fun clone(): FanotifyEventInfoPidfd = FanotifyEventInfoPidfd(hdr, pidfd)
}

public open class FanotifyEventInfoError(
    public var hdr: FanotifyEventInfoHeader,
    public var error: S32,
    public var errorCount: U32,
) {
    public open fun clone(): FanotifyEventInfoError = FanotifyEventInfoError(hdr, error, errorCount)
}

public open class SemT(
    public var size: UByteArray,
) {
    public open fun clone(): SemT = SemT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<SemT> =
            object : TypeLayout<SemT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SemT =
                    SemT(
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SemT) {
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SemT =
                    SemT(
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SemT) {
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class MbstateT(
    public var count: CInt,
    public var wchb: UByteArray,
) {
    public open fun clone(): MbstateT = MbstateT(count, wchb)

    public companion object {
        public fun typeLayout(): TypeLayout<MbstateT> =
            object : TypeLayout<MbstateT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MbstateT =
                    MbstateT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: MbstateT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.count)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 4L, value.wchb)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MbstateT =
                    MbstateT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MbstateT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.count)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 4, value.wchb)
                }
            }
    }
}

public open class Fpos64T(
    public var pos: Off64T,
    public var state: MbstateT,
) {
    public open fun clone(): Fpos64T = Fpos64T(pos, state)

    public companion object {
        public fun typeLayout(): TypeLayout<Fpos64T> =
            object : TypeLayout<Fpos64T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Fpos64T =
                    Fpos64T(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (MbstateT.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Fpos64T) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.pos)
                    (MbstateT.typeLayout()).writeNative(addr + 8L, value.state)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Fpos64T =
                    Fpos64T(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (MbstateT.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Fpos64T) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.pos)
                    (MbstateT.typeLayout()).writeBytes(buf, offset + 8, value.state)
                }
            }
    }
}

public open class FposT(
    public var pos: OffT,
    public var state: MbstateT,
) {
    public open fun clone(): FposT = FposT(pos, state)

    public companion object {
        public fun typeLayout(): TypeLayout<FposT> =
            object : TypeLayout<FposT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): FposT =
                    FposT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (MbstateT.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: FposT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.pos)
                    (MbstateT.typeLayout()).writeNative(addr + 8L, value.state)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FposT =
                    FposT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (MbstateT.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FposT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.pos)
                    (MbstateT.typeLayout()).writeBytes(buf, offset + 8, value.state)
                }
            }
    }
}

public open class Timespec(
    public var tvSec: TimeT,
    public var tvNsec: CLong,
    public var pad: Int,
) {
    public open fun clone(): Timespec = Timespec(tvSec, tvNsec, pad)

    public companion object {
        public fun typeLayout(): TypeLayout<Timespec> =
            object : TypeLayout<Timespec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Timespec) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.tvNsec)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timespec =
                    Timespec(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timespec) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.tvNsec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pad)
                }
            }
    }
}

public open class Utmpx(
    public var utType: CShort,
    public var utPid: PidT,
    public var utLine: UByteArray,
    public var utId: UByteArray,
    public var utUser: UByteArray,
    public var utHost: UByteArray,
    public var utExit: ExitStatus,
    public var utSession: CLong,
    public var utTv: Timeval,
    public var utAddrV6: IntArray,
    public var glibcReserved: UByteArray,
) {
    public open fun clone(): Utmpx = Utmpx(utType, utPid, utLine, utId, utUser, utHost, utExit, utSession, utTv, utAddrV6, glibcReserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Utmpx> =
            object : TypeLayout<Utmpx> {
                override val alignBytes: Long = 8L
                private val off3: Long = alignUpBytes((8L + ((UT_LINESIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off4: Long = alignUpBytes((off3 + 4L), 1L)
                private val off5: Long = alignUpBytes((off4 + ((UT_NAMESIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                private val off6: Long = alignUpBytes((off5 + ((UT_HOSTSIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 2L)
                private val off7: Long = alignUpBytes((off6 + 4L), 8L)
                private val off8: Long = alignUpBytes((off7 + 8L), 4L)
                private val off9: Long = alignUpBytes((off8 + 8L), 4L)
                private val off10: Long = alignUpBytes((off9 + 16L), 1L)
                override val sizeBytes: Long = alignUpBytes((off10 + 20L), 8L)
                override fun readNative(addr: Long): Utmpx =
                    Utmpx(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + off3),
                        (PrimitiveArrayLayouts.uByteArray((UT_NAMESIZE).toInt())).readNative(addr + off4),
                        (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).readNative(addr + off5),
                        (ExitStatus.typeLayout()).readNative(addr + off6),
                        (PrimitiveLayouts.I64).readNative(addr + off7),
                        (Timeval.typeLayout()).readNative(addr + off8),
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr + off9),
                        (PrimitiveArrayLayouts.uByteArray(20)).readNative(addr + off10),
                    )
                override fun writeNative(addr: Long, value: Utmpx) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.utType)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.utPid)
                    (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).writeNative(addr + 8L, value.utLine)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + off3, value.utId)
                    (PrimitiveArrayLayouts.uByteArray((UT_NAMESIZE).toInt())).writeNative(addr + off4, value.utUser)
                    (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).writeNative(addr + off5, value.utHost)
                    (ExitStatus.typeLayout()).writeNative(addr + off6, value.utExit)
                    (PrimitiveLayouts.I64).writeNative(addr + off7, value.utSession)
                    (Timeval.typeLayout()).writeNative(addr + off8, value.utTv)
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr + off9, value.utAddrV6)
                    (PrimitiveArrayLayouts.uByteArray(20)).writeNative(addr + off10, value.glibcReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Utmpx =
                    Utmpx(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((UT_NAMESIZE).toInt())).readBytes(buf, offset + off4.toInt()),
                        (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).readBytes(buf, offset + off5.toInt()),
                        (ExitStatus.typeLayout()).readBytes(buf, offset + off6.toInt()),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + off7.toInt()),
                        (Timeval.typeLayout()).readBytes(buf, offset + off8.toInt()),
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset + off9.toInt()),
                        (PrimitiveArrayLayouts.uByteArray(20)).readBytes(buf, offset + off10.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Utmpx) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.utType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.utPid)
                    (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).writeBytes(buf, offset + 8, value.utLine)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + off3.toInt(), value.utId)
                    (PrimitiveArrayLayouts.uByteArray((UT_NAMESIZE).toInt())).writeBytes(buf, offset + off4.toInt(), value.utUser)
                    (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).writeBytes(buf, offset + off5.toInt(), value.utHost)
                    (ExitStatus.typeLayout()).writeBytes(buf, offset + off6.toInt(), value.utExit)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + off7.toInt(), value.utSession)
                    (Timeval.typeLayout()).writeBytes(buf, offset + off8.toInt(), value.utTv)
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset + off9.toInt(), value.utAddrV6)
                    (PrimitiveArrayLayouts.uByteArray(20)).writeBytes(buf, offset + off10.toInt(), value.glibcReserved)
                }
            }
    }
}

public open class Ethhdr(
    public var hDest: UByteArray,
    public var hSource: UByteArray,
    public var hProto: Be16,
) {
    public open fun clone(): Ethhdr = Ethhdr(hDest, hSource, hProto)

    public companion object {
        public fun typeLayout(): TypeLayout<Ethhdr> =
            object : TypeLayout<Ethhdr> {
                override val alignBytes: Long = 1L
                private val off1: Long = alignUpBytes(((ETH_ALEN).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                private val off2: Long = alignUpBytes((off1 + ((ETH_ALEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + 2L), 1L)
                override fun readNative(addr: Long): Ethhdr =
                    Ethhdr(
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readNative(addr + off1),
                        (PrimitiveLayouts.U16).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Ethhdr) {
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeNative(addr, value.hDest)
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeNative(addr + off1, value.hSource)
                    (PrimitiveLayouts.U16).writeNative(addr + off2, value.hProto)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ethhdr =
                    Ethhdr(
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ethhdr) {
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeBytes(buf, offset, value.hDest)
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeBytes(buf, offset + off1.toInt(), value.hSource)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off2.toInt(), value.hProto)
                }
            }
    }
}

public open class CAnonymousPtraceSyscallInfoData(
    public var entry: CAnonymousPtraceSyscallInfoEntry? = null,
    public var exit: CAnonymousPtraceSyscallInfoExit? = null,
    public var seccomp: CAnonymousPtraceSyscallInfoSeccomp? = null,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ptrace_syscall_info_data").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun eq(other: CAnonymousPtraceSyscallInfoData): Boolean = this.entry == other.entry || this.exit == other.exit || this.seccomp == other.seccomp

    public override fun hashCode(): Int {
        var state = 0
        run {
            state = 31 * state + (this.entry).hashCode()
            state = 31 * state + (this.exit).hashCode()
            state = 31 * state + (this.seccomp).hashCode()
        }
        return state
    }
}
