@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.CcT
import io.github.kotlinmania.rustlang.libc.FanotifyEventInfoHeader
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.Sigevent
import io.github.kotlinmania.rustlang.libc.SocklenT
import io.github.kotlinmania.rustlang.libc.SpeedT
import io.github.kotlinmania.rustlang.libc.TcflagT
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf32Word
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf64Word
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Elf64Xword
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.ETH_ALEN
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
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

public class Aiocb internal constructor(
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

    public constructor() : this(
        aioFildes = 0,
        aioLioOpcode = 0,
        aioReqprio = 0,
        aioBufCtorArg = MutPtr(0L),
        aioNbytes = 0uL,
        aioSigevent = Sigevent(),
        nextPrioCtorArg = MutPtr(0L),
        absPrio = 0,
        policy = 0,
        errorCode = 0,
        returnValue = 0L,
        aioOffset = 0L,
        glibcReserved = UByteArray(32),
    )

    public operator fun component1(): CInt = this.aioFildes

    public operator fun component2(): CInt = this.aioLioOpcode

    public operator fun component3(): CInt = this.aioReqprio

    public operator fun component4(): MutPtr = this.aioBuf

    public operator fun component5(): SizeT = this.aioNbytes

    public operator fun component6(): Sigevent = this.aioSigevent

    public operator fun component7(): MutPtr = this.nextPrio

    public operator fun component8(): CInt = this.absPrio

    public operator fun component9(): CInt = this.policy

    public operator fun component10(): CInt = this.errorCode

    public operator fun component11(): SsizeT = this.returnValue

    public operator fun component12(): OffT = this.aioOffset

    public operator fun component13(): UByteArray = this.glibcReserved

    public fun copy(aioFildes: CInt = this.aioFildes, aioLioOpcode: CInt = this.aioLioOpcode, aioReqprio: CInt = this.aioReqprio, aioBuf: MutPtr = this.aioBuf, aioNbytes: SizeT = this.aioNbytes, aioSigevent: Sigevent = this.aioSigevent, nextPrio: MutPtr = this.nextPrio, absPrio: CInt = this.absPrio, policy: CInt = this.policy, errorCode: CInt = this.errorCode, returnValue: SsizeT = this.returnValue, aioOffset: OffT = this.aioOffset, glibcReserved: UByteArray = this.glibcReserved): Aiocb = Aiocb(aioFildes = aioFildes, aioLioOpcode = aioLioOpcode, aioReqprio = aioReqprio, aioBufCtorArg = aioBuf, aioNbytes = aioNbytes, aioSigevent = aioSigevent, nextPrioCtorArg = nextPrio, absPrio = absPrio, policy = policy, errorCode = errorCode, returnValue = returnValue, aioOffset = aioOffset, glibcReserved = glibcReserved)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Aiocb) return false
        if (this.aioFildes != other.aioFildes) return false
        if (this.aioLioOpcode != other.aioLioOpcode) return false
        if (this.aioReqprio != other.aioReqprio) return false
        if (this.aioBuf != other.aioBuf) return false
        if (this.aioNbytes != other.aioNbytes) return false
        if (this.aioSigevent != other.aioSigevent) return false
        if (this.nextPrio != other.nextPrio) return false
        if (this.absPrio != other.absPrio) return false
        if (this.policy != other.policy) return false
        if (this.errorCode != other.errorCode) return false
        if (this.returnValue != other.returnValue) return false
        if (this.aioOffset != other.aioOffset) return false
        if (!this.glibcReserved.contentEquals(other.glibcReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.aioFildes.hashCode()
        result = 31 * result + this.aioLioOpcode.hashCode()
        result = 31 * result + this.aioReqprio.hashCode()
        result = 31 * result + this.aioBuf.hashCode()
        result = 31 * result + this.aioNbytes.hashCode()
        result = 31 * result + this.aioSigevent.hashCode()
        result = 31 * result + this.nextPrio.hashCode()
        result = 31 * result + this.absPrio.hashCode()
        result = 31 * result + this.policy.hashCode()
        result = 31 * result + this.errorCode.hashCode()
        result = 31 * result + this.returnValue.hashCode()
        result = 31 * result + this.aioOffset.hashCode()
        result = 31 * result + this.glibcReserved.contentHashCode()
        return result
    }

    public fun clone(): Aiocb = Aiocb(aioFildes, aioLioOpcode, aioReqprio, aioBuf, aioNbytes, aioSigevent, nextPrio, absPrio, policy, errorCode, returnValue, aioOffset, glibcReserved)

    public companion object {
        public operator fun invoke(
            aioFildes: CInt = 0,
            aioLioOpcode: CInt = 0,
            aioReqprio: CInt = 0,
            aioBuf: MutPtr = MutPtr(0L),
            aioNbytes: SizeT = 0uL,
            aioSigevent: Sigevent = Sigevent(),
            nextPrio: MutPtr = MutPtr(0L),
            absPrio: CInt = 0,
            policy: CInt = 0,
            errorCode: CInt = 0,
            returnValue: SsizeT = 0L,
            aioOffset: OffT = 0L,
            glibcReserved: UByteArray = UByteArray(32),
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

public data class Timeval(
    public var tvSec: Int = 0,
    public var tvUsec: Int = 0,
) {
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

public class Glob64T internal constructor(
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

    public constructor() : this(
        glPathc = 0uL,
        glPathvCtorArg = MutPtr(0L),
        glOffs = 0uL,
        glFlags = 0,
        unused1CtorArg = MutPtr(0L),
        unused2CtorArg = MutPtr(0L),
        unused3CtorArg = MutPtr(0L),
        unused4CtorArg = MutPtr(0L),
        unused5CtorArg = MutPtr(0L),
    )

    public operator fun component1(): SizeT = this.glPathc

    public operator fun component2(): MutPtr = this.glPathv

    public operator fun component3(): SizeT = this.glOffs

    public operator fun component4(): CInt = this.glFlags

    public operator fun component5(): MutPtr = this.unused1

    public operator fun component6(): MutPtr = this.unused2

    public operator fun component7(): MutPtr = this.unused3

    public operator fun component8(): MutPtr = this.unused4

    public operator fun component9(): MutPtr = this.unused5

    public fun copy(glPathc: SizeT = this.glPathc, glPathv: MutPtr = this.glPathv, glOffs: SizeT = this.glOffs, glFlags: CInt = this.glFlags, unused1: MutPtr = this.unused1, unused2: MutPtr = this.unused2, unused3: MutPtr = this.unused3, unused4: MutPtr = this.unused4, unused5: MutPtr = this.unused5): Glob64T = Glob64T(glPathc = glPathc, glPathvCtorArg = glPathv, glOffs = glOffs, glFlags = glFlags, unused1CtorArg = unused1, unused2CtorArg = unused2, unused3CtorArg = unused3, unused4CtorArg = unused4, unused5CtorArg = unused5)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Glob64T) return false
        if (this.glPathc != other.glPathc) return false
        if (this.glPathv != other.glPathv) return false
        if (this.glOffs != other.glOffs) return false
        if (this.glFlags != other.glFlags) return false
        if (this.unused1 != other.unused1) return false
        if (this.unused2 != other.unused2) return false
        if (this.unused3 != other.unused3) return false
        if (this.unused4 != other.unused4) return false
        if (this.unused5 != other.unused5) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.glPathc.hashCode()
        result = 31 * result + this.glPathv.hashCode()
        result = 31 * result + this.glOffs.hashCode()
        result = 31 * result + this.glFlags.hashCode()
        result = 31 * result + this.unused1.hashCode()
        result = 31 * result + this.unused2.hashCode()
        result = 31 * result + this.unused3.hashCode()
        result = 31 * result + this.unused4.hashCode()
        result = 31 * result + this.unused5.hashCode()
        return result
    }

    public fun clone(): Glob64T = Glob64T(glPathc, glPathv, glOffs, glFlags, unused1, unused2, unused3, unused4, unused5)

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
            glPathc: SizeT = 0uL,
            glPathv: MutPtr = MutPtr(0L),
            glOffs: SizeT = 0uL,
            glFlags: CInt = 0,
            unused1: MutPtr = MutPtr(0L),
            unused2: MutPtr = MutPtr(0L),
            unused3: MutPtr = MutPtr(0L),
            unused4: MutPtr = MutPtr(0L),
            unused5: MutPtr = MutPtr(0L),
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

public class Msghdr internal constructor(
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

    public constructor() : this(
        msgNameCtorArg = MutPtr(0L),
        msgNamelen = 0u,
        msgIovCtorArg = MutPtr(0L),
        msgIovlen = 0uL,
        msgControlCtorArg = MutPtr(0L),
        msgControllen = 0uL,
        msgFlags = 0,
    )

    public operator fun component1(): MutPtr = this.msgName

    public operator fun component2(): SocklenT = this.msgNamelen

    public operator fun component3(): MutPtr = this.msgIov

    public operator fun component4(): SizeT = this.msgIovlen

    public operator fun component5(): MutPtr = this.msgControl

    public operator fun component6(): SizeT = this.msgControllen

    public operator fun component7(): CInt = this.msgFlags

    public fun copy(msgName: MutPtr = this.msgName, msgNamelen: SocklenT = this.msgNamelen, msgIov: MutPtr = this.msgIov, msgIovlen: SizeT = this.msgIovlen, msgControl: MutPtr = this.msgControl, msgControllen: SizeT = this.msgControllen, msgFlags: CInt = this.msgFlags): Msghdr = Msghdr(msgNameCtorArg = msgName, msgNamelen = msgNamelen, msgIovCtorArg = msgIov, msgIovlen = msgIovlen, msgControlCtorArg = msgControl, msgControllen = msgControllen, msgFlags = msgFlags)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Msghdr) return false
        if (this.msgName != other.msgName) return false
        if (this.msgNamelen != other.msgNamelen) return false
        if (this.msgIov != other.msgIov) return false
        if (this.msgIovlen != other.msgIovlen) return false
        if (this.msgControl != other.msgControl) return false
        if (this.msgControllen != other.msgControllen) return false
        if (this.msgFlags != other.msgFlags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.msgName.hashCode()
        result = 31 * result + this.msgNamelen.hashCode()
        result = 31 * result + this.msgIov.hashCode()
        result = 31 * result + this.msgIovlen.hashCode()
        result = 31 * result + this.msgControl.hashCode()
        result = 31 * result + this.msgControllen.hashCode()
        result = 31 * result + this.msgFlags.hashCode()
        return result
    }

    public fun clone(): Msghdr = Msghdr(msgName, msgNamelen, msgIov, msgIovlen, msgControl, msgControllen, msgFlags)

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
            msgName: MutPtr = MutPtr(0L),
            msgNamelen: SocklenT = 0u,
            msgIov: MutPtr = MutPtr(0L),
            msgIovlen: SizeT = 0uL,
            msgControl: MutPtr = MutPtr(0L),
            msgControllen: SizeT = 0uL,
            msgFlags: CInt = 0,
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

public data class Cmsghdr(
    public var cmsgLen: SizeT = 0uL,
    public var cmsgLevel: CInt = 0,
    public var cmsgType: CInt = 0,
) {
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

public data class Mallinfo(
    public var arena: CInt = 0,
    public var ordblks: CInt = 0,
    public var smblks: CInt = 0,
    public var hblks: CInt = 0,
    public var hblkhd: CInt = 0,
    public var usmblks: CInt = 0,
    public var fsmblks: CInt = 0,
    public var uordblks: CInt = 0,
    public var fordblks: CInt = 0,
    public var keepcost: CInt = 0,
) {
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

public data class Mallinfo2(
    public var arena: SizeT = 0uL,
    public var ordblks: SizeT = 0uL,
    public var smblks: SizeT = 0uL,
    public var hblks: SizeT = 0uL,
    public var hblkhd: SizeT = 0uL,
    public var usmblks: SizeT = 0uL,
    public var fsmblks: SizeT = 0uL,
    public var uordblks: SizeT = 0uL,
    public var fordblks: SizeT = 0uL,
    public var keepcost: SizeT = 0uL,
) {
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

public data class Ntptimeval(
    public var time: io.github.kotlinmania.rustlang.libc.Timeval = io.github.kotlinmania.rustlang.libc.Timeval(),
    public var maxerror: CLong = 0L,
    public var esterror: CLong = 0L,
    public var tai: CLong = 0L,
    public var glibcReserved1: CLong = 0L,
    public var glibcReserved2: CLong = 0L,
    public var glibcReserved3: CLong = 0L,
    public var glibcReserved4: CLong = 0L,
) {
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

public class RegexT internal constructor(
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

    public constructor() : this(
        bufferCtorArg = MutPtr(0L),
        allocated = 0uL,
        used = 0uL,
        syntax = 0uL,
        fastmapCtorArg = MutPtr(0L),
        translateCtorArg = MutPtr(0L),
        reNsub = 0uL,
        bitfield = 0u,
    )

    public operator fun component1(): MutPtr = this.buffer

    public operator fun component2(): SizeT = this.allocated

    public operator fun component3(): SizeT = this.used

    public operator fun component4(): CUlong = this.syntax

    public operator fun component5(): MutPtr = this.fastmap

    public operator fun component6(): MutPtr = this.translate

    public operator fun component7(): SizeT = this.reNsub

    public operator fun component8(): UByte = this.bitfield

    public fun copy(buffer: MutPtr = this.buffer, allocated: SizeT = this.allocated, used: SizeT = this.used, syntax: CUlong = this.syntax, fastmap: MutPtr = this.fastmap, translate: MutPtr = this.translate, reNsub: SizeT = this.reNsub, bitfield: UByte = this.bitfield): RegexT = RegexT(bufferCtorArg = buffer, allocated = allocated, used = used, syntax = syntax, fastmapCtorArg = fastmap, translateCtorArg = translate, reNsub = reNsub, bitfield = bitfield)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegexT) return false
        if (this.buffer != other.buffer) return false
        if (this.allocated != other.allocated) return false
        if (this.used != other.used) return false
        if (this.syntax != other.syntax) return false
        if (this.fastmap != other.fastmap) return false
        if (this.translate != other.translate) return false
        if (this.reNsub != other.reNsub) return false
        if (this.bitfield != other.bitfield) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.buffer.hashCode()
        result = 31 * result + this.allocated.hashCode()
        result = 31 * result + this.used.hashCode()
        result = 31 * result + this.syntax.hashCode()
        result = 31 * result + this.fastmap.hashCode()
        result = 31 * result + this.translate.hashCode()
        result = 31 * result + this.reNsub.hashCode()
        result = 31 * result + this.bitfield.hashCode()
        return result
    }

    public fun clone(): RegexT = RegexT(buffer, allocated, used, syntax, fastmap, translate, reNsub, bitfield)

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
            buffer: MutPtr = MutPtr(0L),
            allocated: SizeT = 0uL,
            used: SizeT = 0uL,
            syntax: CUlong = 0uL,
            fastmap: MutPtr = MutPtr(0L),
            translate: MutPtr = MutPtr(0L),
            reNsub: SizeT = 0uL,
            bitfield: UByte = 0u,
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

public data class Seminfo(
    public var semmap: CInt = 0,
    public var semmni: CInt = 0,
    public var semmns: CInt = 0,
    public var semmnu: CInt = 0,
    public var semmsl: CInt = 0,
    public var semopm: CInt = 0,
    public var semume: CInt = 0,
    public var semusz: CInt = 0,
    public var semvmx: CInt = 0,
    public var semaem: CInt = 0,
) {
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

public data class PtracePeeksiginfoArgs(
    public var off: U64 = 0uL,
    public var flags: U32 = 0u,
    public var nr: S32 = 0,
) {
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

public data class CAnonymousPtraceSyscallInfoEntry(
    public var nr: U64 = 0uL,
    public var args: ULongArray = ULongArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousPtraceSyscallInfoEntry) return false
        if (this.nr != other.nr) return false
        if (!this.args.contentEquals(other.args)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nr.hashCode()
        result = 31 * result + this.args.contentHashCode()
        return result
    }

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

public data class CAnonymousPtraceSyscallInfoExit(
    public var sval: S64 = 0L,
    public var isError: U8 = 0u,
) {
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

public data class CAnonymousPtraceSyscallInfoSeccomp(
    public var nr: U64 = 0uL,
    public var args: ULongArray = ULongArray(6),
    public var retData: U32 = 0u,
    public var reserved2: U32 = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousPtraceSyscallInfoSeccomp) return false
        if (this.nr != other.nr) return false
        if (!this.args.contentEquals(other.args)) return false
        if (this.retData != other.retData) return false
        if (this.reserved2 != other.reserved2) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nr.hashCode()
        result = 31 * result + this.args.contentHashCode()
        result = 31 * result + this.retData.hashCode()
        result = 31 * result + this.reserved2.hashCode()
        return result
    }

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

public data class PtraceSyscallInfo(
    public var op: U8 = 0u,
    public var reserved: U8 = 0u,
    public var flags: U16 = 0u,
    public var arch: U32 = 0u,
    public var instructionPointer: U64 = 0uL,
    public var stackPointer: U64 = 0uL,
    public var u: CAnonymousPtraceSyscallInfoData = CAnonymousPtraceSyscallInfoData(),
)

public data class PtraceSudConfig(
    public var mode: U64 = 0uL,
    public var selector: U64 = 0uL,
    public var offset: U64 = 0uL,
    public var len: U64 = 0uL,
) {
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

public data class Iocb(
    public var aioData: U64 = 0uL,
    public var aioKey: U32 = 0u,
    public var aioRwFlags: KernelRwfT = 0,
    public var aioLioOpcode: U16 = 0u,
    public var aioReqprio: S16 = 0,
    public var aioFildes: U32 = 0u,
    public var aioBuf: U64 = 0uL,
    public var aioNbytes: U64 = 0uL,
    public var aioOffset: S64 = 0L,
    public var aioReserved2: U64 = 0uL,
    public var aioFlags: U32 = 0u,
    public var aioResfd: U32 = 0u,
) {
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
public class TcpInfo {
    public var tcpiState: UByte = 0u

    public var tcpiCaState: UByte = 0u

    public var tcpiRetransmits: UByte = 0u

    public var tcpiProbes: UByte = 0u

    public var tcpiBackoff: UByte = 0u

    public var tcpiOptions: UByte = 0u

    /**
     * This contains the bitfields `tcpiSndWscale` and `tcpiRcvWscale`.
     * Each is 4 bits.
     */
    public var tcpiSndRcvWscale: UByte = 0u

    public var tcpiRto: UInt = 0u

    public var tcpiAto: UInt = 0u

    public var tcpiSndMss: UInt = 0u

    public var tcpiRcvMss: UInt = 0u

    public var tcpiUnacked: UInt = 0u

    public var tcpiSacked: UInt = 0u

    public var tcpiLost: UInt = 0u

    public var tcpiRetrans: UInt = 0u

    public var tcpiFackets: UInt = 0u

    public var tcpiLastDataSent: UInt = 0u

    public var tcpiLastAckSent: UInt = 0u

    public var tcpiLastDataRecv: UInt = 0u

    public var tcpiLastAckRecv: UInt = 0u

    public var tcpiPmtu: UInt = 0u

    public var tcpiRcvSsthresh: UInt = 0u

    public var tcpiRtt: UInt = 0u

    public var tcpiRttvar: UInt = 0u

    public var tcpiSndSsthresh: UInt = 0u

    public var tcpiSndCwnd: UInt = 0u

    public var tcpiAdvmss: UInt = 0u

    public var tcpiReordering: UInt = 0u

    public var tcpiRcvRtt: UInt = 0u

    public var tcpiRcvSpace: UInt = 0u

    public var tcpiTotalRetrans: UInt = 0u

    public var tcpiPacingRate: ULong = 0uL

    public var tcpiMaxPacingRate: ULong = 0uL

    public var tcpiBytesAcked: ULong = 0uL

    public var tcpiBytesReceived: ULong = 0uL

    public var tcpiSegsOut: UInt = 0u

    public var tcpiSegsIn: UInt = 0u

    public var tcpiNotsentBytes: UInt = 0u

    public var tcpiMinRtt: UInt = 0u

    public var tcpiDataSegsIn: UInt = 0u

    public var tcpiDataSegsOut: UInt = 0u

    public var tcpiDeliveryRate: ULong = 0uL

    public var tcpiBusyTime: ULong = 0uL

    public var tcpiRwndLimited: ULong = 0uL

    public var tcpiSndbufLimited: ULong = 0uL

    public var tcpiDelivered: UInt = 0u

    public var tcpiDeliveredCe: UInt = 0u

    public var tcpiBytesSent: ULong = 0uL

    public var tcpiBytesRetrans: ULong = 0uL

    public var tcpiDsackDups: UInt = 0u

    public var tcpiReordSeen: UInt = 0u

    public var tcpiRcvOoopack: UInt = 0u

    public var tcpiSndWnd: UInt = 0u

    public var tcpiRcvWnd: UInt = 0u

    public var tcpiRehash: UInt = 0u

    public var tcpiTotalRto: UShort = 0u

    public var tcpiTotalRtoRecoveries: UShort = 0u

    public var tcpiTotalRtoTime: UInt = 0u

    public var tcpiReceivedCe: UInt = 0u

    public var tcpiDeliveredE1Bytes: UInt = 0u

    public var tcpiDeliveredE0Bytes: UInt = 0u

    public var tcpiDeliveredCeBytes: UInt = 0u

    public var tcpiReceivedE1Bytes: UInt = 0u

    public var tcpiReceivedE0Bytes: UInt = 0u

    public var tcpiReceivedCeBytes: UInt = 0u

    public var tcpiAccecnFailMode: UShort = 0u

    public var tcpiAccecnOptSeen: UShort = 0u

    public companion object {
        public fun typeLayout(): TypeLayout<TcpInfo> =
            object : TypeLayout<TcpInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 280L
                override fun readNative(addr: Long): TcpInfo =
                    TcpInfo().apply {
                        this.tcpiState = (PrimitiveLayouts.U8).readNative(addr)
                        this.tcpiCaState = (PrimitiveLayouts.U8).readNative(addr + 1L)
                        this.tcpiRetransmits = (PrimitiveLayouts.U8).readNative(addr + 2L)
                        this.tcpiProbes = (PrimitiveLayouts.U8).readNative(addr + 3L)
                        this.tcpiBackoff = (PrimitiveLayouts.U8).readNative(addr + 4L)
                        this.tcpiOptions = (PrimitiveLayouts.U8).readNative(addr + 5L)
                        this.tcpiSndRcvWscale = (PrimitiveLayouts.U8).readNative(addr + 6L)
                        this.tcpiRto = (PrimitiveLayouts.U32).readNative(addr + 8L)
                        this.tcpiAto = (PrimitiveLayouts.U32).readNative(addr + 12L)
                        this.tcpiSndMss = (PrimitiveLayouts.U32).readNative(addr + 16L)
                        this.tcpiRcvMss = (PrimitiveLayouts.U32).readNative(addr + 20L)
                        this.tcpiUnacked = (PrimitiveLayouts.U32).readNative(addr + 24L)
                        this.tcpiSacked = (PrimitiveLayouts.U32).readNative(addr + 28L)
                        this.tcpiLost = (PrimitiveLayouts.U32).readNative(addr + 32L)
                        this.tcpiRetrans = (PrimitiveLayouts.U32).readNative(addr + 36L)
                        this.tcpiFackets = (PrimitiveLayouts.U32).readNative(addr + 40L)
                        this.tcpiLastDataSent = (PrimitiveLayouts.U32).readNative(addr + 44L)
                        this.tcpiLastAckSent = (PrimitiveLayouts.U32).readNative(addr + 48L)
                        this.tcpiLastDataRecv = (PrimitiveLayouts.U32).readNative(addr + 52L)
                        this.tcpiLastAckRecv = (PrimitiveLayouts.U32).readNative(addr + 56L)
                        this.tcpiPmtu = (PrimitiveLayouts.U32).readNative(addr + 60L)
                        this.tcpiRcvSsthresh = (PrimitiveLayouts.U32).readNative(addr + 64L)
                        this.tcpiRtt = (PrimitiveLayouts.U32).readNative(addr + 68L)
                        this.tcpiRttvar = (PrimitiveLayouts.U32).readNative(addr + 72L)
                        this.tcpiSndSsthresh = (PrimitiveLayouts.U32).readNative(addr + 76L)
                        this.tcpiSndCwnd = (PrimitiveLayouts.U32).readNative(addr + 80L)
                        this.tcpiAdvmss = (PrimitiveLayouts.U32).readNative(addr + 84L)
                        this.tcpiReordering = (PrimitiveLayouts.U32).readNative(addr + 88L)
                        this.tcpiRcvRtt = (PrimitiveLayouts.U32).readNative(addr + 92L)
                        this.tcpiRcvSpace = (PrimitiveLayouts.U32).readNative(addr + 96L)
                        this.tcpiTotalRetrans = (PrimitiveLayouts.U32).readNative(addr + 100L)
                        this.tcpiPacingRate = (PrimitiveLayouts.U64).readNative(addr + 104L)
                        this.tcpiMaxPacingRate = (PrimitiveLayouts.U64).readNative(addr + 112L)
                        this.tcpiBytesAcked = (PrimitiveLayouts.U64).readNative(addr + 120L)
                        this.tcpiBytesReceived = (PrimitiveLayouts.U64).readNative(addr + 128L)
                        this.tcpiSegsOut = (PrimitiveLayouts.U32).readNative(addr + 136L)
                        this.tcpiSegsIn = (PrimitiveLayouts.U32).readNative(addr + 140L)
                        this.tcpiNotsentBytes = (PrimitiveLayouts.U32).readNative(addr + 144L)
                        this.tcpiMinRtt = (PrimitiveLayouts.U32).readNative(addr + 148L)
                        this.tcpiDataSegsIn = (PrimitiveLayouts.U32).readNative(addr + 152L)
                        this.tcpiDataSegsOut = (PrimitiveLayouts.U32).readNative(addr + 156L)
                        this.tcpiDeliveryRate = (PrimitiveLayouts.U64).readNative(addr + 160L)
                        this.tcpiBusyTime = (PrimitiveLayouts.U64).readNative(addr + 168L)
                        this.tcpiRwndLimited = (PrimitiveLayouts.U64).readNative(addr + 176L)
                        this.tcpiSndbufLimited = (PrimitiveLayouts.U64).readNative(addr + 184L)
                        this.tcpiDelivered = (PrimitiveLayouts.U32).readNative(addr + 192L)
                        this.tcpiDeliveredCe = (PrimitiveLayouts.U32).readNative(addr + 196L)
                        this.tcpiBytesSent = (PrimitiveLayouts.U64).readNative(addr + 200L)
                        this.tcpiBytesRetrans = (PrimitiveLayouts.U64).readNative(addr + 208L)
                        this.tcpiDsackDups = (PrimitiveLayouts.U32).readNative(addr + 216L)
                        this.tcpiReordSeen = (PrimitiveLayouts.U32).readNative(addr + 220L)
                        this.tcpiRcvOoopack = (PrimitiveLayouts.U32).readNative(addr + 224L)
                        this.tcpiSndWnd = (PrimitiveLayouts.U32).readNative(addr + 228L)
                        this.tcpiRcvWnd = (PrimitiveLayouts.U32).readNative(addr + 232L)
                        this.tcpiRehash = (PrimitiveLayouts.U32).readNative(addr + 236L)
                        this.tcpiTotalRto = (PrimitiveLayouts.U16).readNative(addr + 240L)
                        this.tcpiTotalRtoRecoveries = (PrimitiveLayouts.U16).readNative(addr + 242L)
                        this.tcpiTotalRtoTime = (PrimitiveLayouts.U32).readNative(addr + 244L)
                        this.tcpiReceivedCe = (PrimitiveLayouts.U32).readNative(addr + 248L)
                        this.tcpiDeliveredE1Bytes = (PrimitiveLayouts.U32).readNative(addr + 252L)
                        this.tcpiDeliveredE0Bytes = (PrimitiveLayouts.U32).readNative(addr + 256L)
                        this.tcpiDeliveredCeBytes = (PrimitiveLayouts.U32).readNative(addr + 260L)
                        this.tcpiReceivedE1Bytes = (PrimitiveLayouts.U32).readNative(addr + 264L)
                        this.tcpiReceivedE0Bytes = (PrimitiveLayouts.U32).readNative(addr + 268L)
                        this.tcpiReceivedCeBytes = (PrimitiveLayouts.U32).readNative(addr + 272L)
                        this.tcpiAccecnFailMode = (PrimitiveLayouts.U16).readNative(addr + 276L)
                        this.tcpiAccecnOptSeen = (PrimitiveLayouts.U16).readNative(addr + 278L)
                    }
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
                    TcpInfo().apply {
                        this.tcpiState = (PrimitiveLayouts.U8).readBytes(buf, offset)
                        this.tcpiCaState = (PrimitiveLayouts.U8).readBytes(buf, offset + 1)
                        this.tcpiRetransmits = (PrimitiveLayouts.U8).readBytes(buf, offset + 2)
                        this.tcpiProbes = (PrimitiveLayouts.U8).readBytes(buf, offset + 3)
                        this.tcpiBackoff = (PrimitiveLayouts.U8).readBytes(buf, offset + 4)
                        this.tcpiOptions = (PrimitiveLayouts.U8).readBytes(buf, offset + 5)
                        this.tcpiSndRcvWscale = (PrimitiveLayouts.U8).readBytes(buf, offset + 6)
                        this.tcpiRto = (PrimitiveLayouts.U32).readBytes(buf, offset + 8)
                        this.tcpiAto = (PrimitiveLayouts.U32).readBytes(buf, offset + 12)
                        this.tcpiSndMss = (PrimitiveLayouts.U32).readBytes(buf, offset + 16)
                        this.tcpiRcvMss = (PrimitiveLayouts.U32).readBytes(buf, offset + 20)
                        this.tcpiUnacked = (PrimitiveLayouts.U32).readBytes(buf, offset + 24)
                        this.tcpiSacked = (PrimitiveLayouts.U32).readBytes(buf, offset + 28)
                        this.tcpiLost = (PrimitiveLayouts.U32).readBytes(buf, offset + 32)
                        this.tcpiRetrans = (PrimitiveLayouts.U32).readBytes(buf, offset + 36)
                        this.tcpiFackets = (PrimitiveLayouts.U32).readBytes(buf, offset + 40)
                        this.tcpiLastDataSent = (PrimitiveLayouts.U32).readBytes(buf, offset + 44)
                        this.tcpiLastAckSent = (PrimitiveLayouts.U32).readBytes(buf, offset + 48)
                        this.tcpiLastDataRecv = (PrimitiveLayouts.U32).readBytes(buf, offset + 52)
                        this.tcpiLastAckRecv = (PrimitiveLayouts.U32).readBytes(buf, offset + 56)
                        this.tcpiPmtu = (PrimitiveLayouts.U32).readBytes(buf, offset + 60)
                        this.tcpiRcvSsthresh = (PrimitiveLayouts.U32).readBytes(buf, offset + 64)
                        this.tcpiRtt = (PrimitiveLayouts.U32).readBytes(buf, offset + 68)
                        this.tcpiRttvar = (PrimitiveLayouts.U32).readBytes(buf, offset + 72)
                        this.tcpiSndSsthresh = (PrimitiveLayouts.U32).readBytes(buf, offset + 76)
                        this.tcpiSndCwnd = (PrimitiveLayouts.U32).readBytes(buf, offset + 80)
                        this.tcpiAdvmss = (PrimitiveLayouts.U32).readBytes(buf, offset + 84)
                        this.tcpiReordering = (PrimitiveLayouts.U32).readBytes(buf, offset + 88)
                        this.tcpiRcvRtt = (PrimitiveLayouts.U32).readBytes(buf, offset + 92)
                        this.tcpiRcvSpace = (PrimitiveLayouts.U32).readBytes(buf, offset + 96)
                        this.tcpiTotalRetrans = (PrimitiveLayouts.U32).readBytes(buf, offset + 100)
                        this.tcpiPacingRate = (PrimitiveLayouts.U64).readBytes(buf, offset + 104)
                        this.tcpiMaxPacingRate = (PrimitiveLayouts.U64).readBytes(buf, offset + 112)
                        this.tcpiBytesAcked = (PrimitiveLayouts.U64).readBytes(buf, offset + 120)
                        this.tcpiBytesReceived = (PrimitiveLayouts.U64).readBytes(buf, offset + 128)
                        this.tcpiSegsOut = (PrimitiveLayouts.U32).readBytes(buf, offset + 136)
                        this.tcpiSegsIn = (PrimitiveLayouts.U32).readBytes(buf, offset + 140)
                        this.tcpiNotsentBytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 144)
                        this.tcpiMinRtt = (PrimitiveLayouts.U32).readBytes(buf, offset + 148)
                        this.tcpiDataSegsIn = (PrimitiveLayouts.U32).readBytes(buf, offset + 152)
                        this.tcpiDataSegsOut = (PrimitiveLayouts.U32).readBytes(buf, offset + 156)
                        this.tcpiDeliveryRate = (PrimitiveLayouts.U64).readBytes(buf, offset + 160)
                        this.tcpiBusyTime = (PrimitiveLayouts.U64).readBytes(buf, offset + 168)
                        this.tcpiRwndLimited = (PrimitiveLayouts.U64).readBytes(buf, offset + 176)
                        this.tcpiSndbufLimited = (PrimitiveLayouts.U64).readBytes(buf, offset + 184)
                        this.tcpiDelivered = (PrimitiveLayouts.U32).readBytes(buf, offset + 192)
                        this.tcpiDeliveredCe = (PrimitiveLayouts.U32).readBytes(buf, offset + 196)
                        this.tcpiBytesSent = (PrimitiveLayouts.U64).readBytes(buf, offset + 200)
                        this.tcpiBytesRetrans = (PrimitiveLayouts.U64).readBytes(buf, offset + 208)
                        this.tcpiDsackDups = (PrimitiveLayouts.U32).readBytes(buf, offset + 216)
                        this.tcpiReordSeen = (PrimitiveLayouts.U32).readBytes(buf, offset + 220)
                        this.tcpiRcvOoopack = (PrimitiveLayouts.U32).readBytes(buf, offset + 224)
                        this.tcpiSndWnd = (PrimitiveLayouts.U32).readBytes(buf, offset + 228)
                        this.tcpiRcvWnd = (PrimitiveLayouts.U32).readBytes(buf, offset + 232)
                        this.tcpiRehash = (PrimitiveLayouts.U32).readBytes(buf, offset + 236)
                        this.tcpiTotalRto = (PrimitiveLayouts.U16).readBytes(buf, offset + 240)
                        this.tcpiTotalRtoRecoveries = (PrimitiveLayouts.U16).readBytes(buf, offset + 242)
                        this.tcpiTotalRtoTime = (PrimitiveLayouts.U32).readBytes(buf, offset + 244)
                        this.tcpiReceivedCe = (PrimitiveLayouts.U32).readBytes(buf, offset + 248)
                        this.tcpiDeliveredE1Bytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 252)
                        this.tcpiDeliveredE0Bytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 256)
                        this.tcpiDeliveredCeBytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 260)
                        this.tcpiReceivedE1Bytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 264)
                        this.tcpiReceivedE0Bytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 268)
                        this.tcpiReceivedCeBytes = (PrimitiveLayouts.U32).readBytes(buf, offset + 272)
                        this.tcpiAccecnFailMode = (PrimitiveLayouts.U16).readBytes(buf, offset + 276)
                        this.tcpiAccecnOptSeen = (PrimitiveLayouts.U16).readBytes(buf, offset + 278)
                    }
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

public data class FanotifyEventInfoPidfd(
    public var hdr: FanotifyEventInfoHeader = FanotifyEventInfoHeader(),
    public var pidfd: S32 = 0,
)

public data class FanotifyEventInfoError(
    public var hdr: FanotifyEventInfoHeader = FanotifyEventInfoHeader(),
    public var error: S32 = 0,
    public var errorCount: U32 = 0u,
)

public data class SemT(
    public var size: UByteArray = UByteArray(32),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SemT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

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

public data class MbstateT(
    public var count: CInt = 0,
    public var wchb: UByteArray = UByteArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MbstateT) return false
        if (this.count != other.count) return false
        if (!this.wchb.contentEquals(other.wchb)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.count.hashCode()
        result = 31 * result + this.wchb.contentHashCode()
        return result
    }

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

public data class Fpos64T(
    public var pos: Off64T = 0L,
    public var state: io.github.kotlinmania.rustlang.libc.MbstateT = io.github.kotlinmania.rustlang.libc.MbstateT(),
) {
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

public data class FposT(
    public var pos: OffT = 0L,
    public var state: io.github.kotlinmania.rustlang.libc.MbstateT = io.github.kotlinmania.rustlang.libc.MbstateT(),
) {
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

public data class Timespec(
    public var tvSec: TimeT = 0L,
    public var tvNsec: CLong = 0L,
    public var pad: Int = 0,
) {
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

public data class Utmpx(
    public var utType: CShort = 0,
    public var utPid: PidT = 0,
    public var utLine: UByteArray = UByteArray(UT_LINESIZE),
    public var utId: UByteArray = UByteArray(4),
    public var utUser: UByteArray = UByteArray(UT_NAMESIZE),
    public var utHost: UByteArray = UByteArray(UT_HOSTSIZE),
    public var utExit: ExitStatus = ExitStatus(),
    public var utSession: CLong = 0L,
    public var utTv: io.github.kotlinmania.rustlang.libc.Timeval = io.github.kotlinmania.rustlang.libc.Timeval(),
    public var utAddrV6: IntArray = IntArray(4),
    public var glibcReserved: UByteArray = UByteArray(20),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utmpx) return false
        if (this.utType != other.utType) return false
        if (this.utPid != other.utPid) return false
        if (!this.utLine.contentEquals(other.utLine)) return false
        if (!this.utId.contentEquals(other.utId)) return false
        if (!this.utUser.contentEquals(other.utUser)) return false
        if (!this.utHost.contentEquals(other.utHost)) return false
        if (this.utExit != other.utExit) return false
        if (this.utSession != other.utSession) return false
        if (this.utTv != other.utTv) return false
        if (!this.utAddrV6.contentEquals(other.utAddrV6)) return false
        if (!this.glibcReserved.contentEquals(other.glibcReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.utType.hashCode()
        result = 31 * result + this.utPid.hashCode()
        result = 31 * result + this.utLine.contentHashCode()
        result = 31 * result + this.utId.contentHashCode()
        result = 31 * result + this.utUser.contentHashCode()
        result = 31 * result + this.utHost.contentHashCode()
        result = 31 * result + this.utExit.hashCode()
        result = 31 * result + this.utSession.hashCode()
        result = 31 * result + this.utTv.hashCode()
        result = 31 * result + this.utAddrV6.contentHashCode()
        result = 31 * result + this.glibcReserved.contentHashCode()
        return result
    }

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

public data class Ethhdr(
    public var hDest: UByteArray = UByteArray(ETH_ALEN),
    public var hSource: UByteArray = UByteArray(ETH_ALEN),
    public var hProto: Be16 = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ethhdr) return false
        if (!this.hDest.contentEquals(other.hDest)) return false
        if (!this.hSource.contentEquals(other.hSource)) return false
        if (this.hProto != other.hProto) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.hDest.contentHashCode()
        result = 31 * result + this.hSource.contentHashCode()
        result = 31 * result + this.hProto.hashCode()
        return result
    }

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

public data class CAnonymousPtraceSyscallInfoData(
    public var entry: CAnonymousPtraceSyscallInfoEntry = CAnonymousPtraceSyscallInfoEntry(),
    public var exit: CAnonymousPtraceSyscallInfoExit = CAnonymousPtraceSyscallInfoExit(),
    public var seccomp: CAnonymousPtraceSyscallInfoSeccomp = CAnonymousPtraceSyscallInfoSeccomp(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ptrace_syscall_info_data").finishNonExhaustive()
    }

    public fun eq(other: CAnonymousPtraceSyscallInfoData): Boolean = this.entry == other.entry || this.exit == other.exit || this.seccomp == other.seccomp

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
