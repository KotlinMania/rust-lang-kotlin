// port-lint: source new/musl/signal.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.musl.signal

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.Sigval
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.ClockT

// Header source: `include / signal.h`
//
// * Headers: <https://git.musl-libc.org/cgit/musl/tree/include/signal.h> (official)
// * Headers: <https://github.com/kraj/musl/blob/master/include/signal.h> (mirror)

// `mips *` targets swap the `sErrno` and `sCode` fields otherwise this
// struct is target-agnostic (see
// <https://www.openwall.com/lists/musl/2016/01/27/1/2>)
public data class SiginfoT(
    public var siSigno: CInt = 0,
    public var siCode: CInt = 0,
    public var siErrno: CInt = 0,
    public var siFields: CAnonymousSiginfoTSiFields = CAnonymousSiginfoTSiFields(),
) {
    public fun siPid(): PidT = this.siFields.siCommon.first.piduid.siPid

    public fun siUid(): UidT = this.siFields.siCommon.first.piduid.siUid

    public fun siStatus(): CInt = this.siFields.siCommon.second.sigchld.siStatus

    public fun siUtime(): ClockT = this.siFields.siCommon.second.sigchld.siUtime

    public fun siStime(): ClockT = this.siFields.siCommon.second.sigchld.siStime

    public fun siValue(): Sigval = this.siFields.siCommon.second.siValue

    public fun siAddr(): MutPtr = this.siFields.sigfault.siAddr

    public fun siAddrLsb(): CShort = this.siFields.sigfault.siAddrLsb

    public fun siLower(): MutPtr = this.siFields.sigfault.first.addrBand.siLower

    public fun siUpper(): MutPtr = this.siFields.sigfault.first.addrBand.siUpper

    public fun siPkey(): CUint = this.siFields.sigfault.first.siPkey

    public fun siBand(): CLong = this.siFields.sigpoll.siBand

    public fun siFd(): CInt = this.siFields.sigpoll.siFd

    public fun siTimerid(): CInt = this.siFields.siCommon.first.timer.siTimerid

    public fun siOverrun(): CInt = this.siFields.siCommon.first.timer.siOverrun

    public fun siPtr(): MutPtr = this.siFields.siCommon.second.siValue.sivalPtr

    public fun siInt(): CInt {
        // DIFF(main): sigval is a union on `main`, struct on `libc - 0.2`. The cast means
        // this can't be const at the MSRV.
        return this.siFields.siCommon.second.siValue.sivalPtr.rawAddress.toInt()
    }

    public fun siCallAddr(): MutPtr = this.siFields.sigsys.siCallAddr

    public fun siSyscall(): CInt = this.siFields.sigsys.siSyscall

    public fun siArch(): CUint = this.siFields.sigsys.siArch

    public fun eq(other: SiginfoT): Boolean = Triple(this.siSigno, this.siErrno, this.siCode) == Triple(other.siSigno, other.siErrno, other.siCode)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.siSigno).hashCode()
        state = 31 * state + (this.siErrno).hashCode()
        state = 31 * state + (this.siCode).hashCode()
        return state
    }
}

public data class CAnonymousSiginfoTSiFields(
    public var pad: UByteArray = UByteArray((128 - 2 * sizeOf<CInt>(typeLayoutT = ObjectSlotLayout<CInt>()) - sizeOf<CLong>(typeLayoutT = ObjectSlotLayout<CLong>())).toInt()),
    public var siCommon: CAnonymousSiFieldsSiCommon = CAnonymousSiFieldsSiCommon(),
    public var sigfault: CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(),
    public var sigpoll: CAnonymousSiFieldsSigpoll = CAnonymousSiFieldsSigpoll(),
    public var sigsys: CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousSiginfoTSiFields) return false
        if (!this.pad.contentEquals(other.pad)) return false
        if (this.siCommon != other.siCommon) return false
        if (this.sigfault != other.sigfault) return false
        if (this.sigpoll != other.sigpoll) return false
        if (this.sigsys != other.sigsys) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pad.contentHashCode()
        result = 31 * result + this.siCommon.hashCode()
        result = 31 * result + this.sigfault.hashCode()
        result = 31 * result + this.sigpoll.hashCode()
        result = 31 * result + this.sigsys.hashCode()
        return result
    }
}

public data class CAnonymousSiFieldsSiCommon(
    public var first: CAnonymousSiCommonFirst = CAnonymousSiCommonFirst(),
    public var second: CAnonymousSiCommonSecond = CAnonymousSiCommonSecond(),
)

public class CAnonymousSiFieldsSigfault internal constructor(
    siAddrCtorArg: MutPtr,
    public var siAddrLsb: CShort,
    public var first: CAnonymousSigfaultFirst,
) {
    public var siAddr: MutPtr = siAddrCtorArg
        internal set

    public constructor() : this(
        siAddrCtorArg = MutPtr(0L),
        siAddrLsb = 0,
        first = CAnonymousSigfaultFirst(),
    )

    public operator fun component1(): MutPtr = this.siAddr

    public operator fun component2(): CShort = this.siAddrLsb

    public operator fun component3(): CAnonymousSigfaultFirst = this.first

    public fun copy(siAddr: MutPtr = this.siAddr, siAddrLsb: CShort = this.siAddrLsb, first: CAnonymousSigfaultFirst = this.first): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(siAddrCtorArg = siAddr, siAddrLsb = siAddrLsb, first = first)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousSiFieldsSigfault) return false
        if (this.siAddr != other.siAddr) return false
        if (this.siAddrLsb != other.siAddrLsb) return false
        if (this.first != other.first) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siAddr.hashCode()
        result = 31 * result + this.siAddrLsb.hashCode()
        result = 31 * result + this.first.hashCode()
        return result
    }

    public fun clone(): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(siAddr, siAddrLsb, first)

    public companion object {
        public operator fun invoke(
            siAddr: MutPtr = MutPtr(0L),
            siAddrLsb: CShort = 0,
            first: CAnonymousSigfaultFirst = CAnonymousSigfaultFirst(),
        ): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(
            siAddrCtorArg = siAddr,
            siAddrLsb = siAddrLsb,
            first = first,
        )
    }
}

public data class CAnonymousSiFieldsSigpoll(
    public var siBand: CLong = 0L,
    public var siFd: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSiFieldsSigpoll> =
            object : TypeLayout<CAnonymousSiFieldsSigpoll> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousSiFieldsSigpoll =
                    CAnonymousSiFieldsSigpoll(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSiFieldsSigpoll) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.siBand)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.siFd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSiFieldsSigpoll =
                    CAnonymousSiFieldsSigpoll(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSiFieldsSigpoll) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.siBand)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.siFd)
                }
            }
    }
}

public class CAnonymousSiFieldsSigsys internal constructor(
    siCallAddrCtorArg: MutPtr,
    public var siSyscall: CInt,
    public var siArch: CUint,
) {
    public var siCallAddr: MutPtr = siCallAddrCtorArg
        internal set

    public constructor() : this(
        siCallAddrCtorArg = MutPtr(0L),
        siSyscall = 0,
        siArch = 0u,
    )

    public operator fun component1(): MutPtr = this.siCallAddr

    public operator fun component2(): CInt = this.siSyscall

    public operator fun component3(): CUint = this.siArch

    public fun copy(siCallAddr: MutPtr = this.siCallAddr, siSyscall: CInt = this.siSyscall, siArch: CUint = this.siArch): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(siCallAddrCtorArg = siCallAddr, siSyscall = siSyscall, siArch = siArch)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousSiFieldsSigsys) return false
        if (this.siCallAddr != other.siCallAddr) return false
        if (this.siSyscall != other.siSyscall) return false
        if (this.siArch != other.siArch) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siCallAddr.hashCode()
        result = 31 * result + this.siSyscall.hashCode()
        result = 31 * result + this.siArch.hashCode()
        return result
    }

    public fun clone(): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(siCallAddr, siSyscall, siArch)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSiFieldsSigsys> =
            object : TypeLayout<CAnonymousSiFieldsSigsys> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousSiFieldsSigsys =
                    CAnonymousSiFieldsSigsys(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSiFieldsSigsys) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.siCallAddr)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.siSyscall)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.siArch)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSiFieldsSigsys =
                    CAnonymousSiFieldsSigsys(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSiFieldsSigsys) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.siCallAddr)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.siSyscall)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.siArch)
                }
            }

        public operator fun invoke(
            siCallAddr: MutPtr = MutPtr(0L),
            siSyscall: CInt = 0,
            siArch: CUint = 0u,
        ): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(
            siCallAddrCtorArg = siCallAddr,
            siSyscall = siSyscall,
            siArch = siArch,
        )
    }
}

public data class CAnonymousSiCommonFirst(
    public var piduid: CAnonymousFirstPiduid = CAnonymousFirstPiduid(),
    public var timer: CAnonymousFirstTimer = CAnonymousFirstTimer(),
)

public data class CAnonymousSiCommonSecond(
    public var siValue: Sigval = Sigval(),
    public var sigchld: CAnonymousSecondSigchld = CAnonymousSecondSigchld(),
)

public data class CAnonymousSigfaultFirst(
    public var addrBand: CAnonymousFirstAddrBand = CAnonymousFirstAddrBand(),
    public var siPkey: CUint = 0u,
)

public data class CAnonymousFirstPiduid(
    public var siPid: PidT = 0,
    public var siUid: UidT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousFirstPiduid> =
            object : TypeLayout<CAnonymousFirstPiduid> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousFirstPiduid =
                    CAnonymousFirstPiduid(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousFirstPiduid) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siPid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siUid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousFirstPiduid =
                    CAnonymousFirstPiduid(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousFirstPiduid) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siPid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siUid)
                }
            }
    }
}

public data class CAnonymousFirstTimer(
    public var siTimerid: CInt = 0,
    public var siOverrun: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousFirstTimer> =
            object : TypeLayout<CAnonymousFirstTimer> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousFirstTimer =
                    CAnonymousFirstTimer(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousFirstTimer) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siTimerid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siOverrun)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousFirstTimer =
                    CAnonymousFirstTimer(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousFirstTimer) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siTimerid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siOverrun)
                }
            }
    }
}

public data class CAnonymousSecondSigchld(
    public var siStatus: CInt = 0,
    public var siUtime: ClockT = 0L,
    public var siStime: ClockT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSecondSigchld> =
            object : TypeLayout<CAnonymousSecondSigchld> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): CAnonymousSecondSigchld =
                    CAnonymousSecondSigchld(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSecondSigchld) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siStatus)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.siUtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.siStime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSecondSigchld =
                    CAnonymousSecondSigchld(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSecondSigchld) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siStatus)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.siUtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.siStime)
                }
            }
    }
}

public class CAnonymousFirstAddrBand internal constructor(
    siLowerCtorArg: MutPtr,
    siUpperCtorArg: MutPtr,
) {
    public var siLower: MutPtr = siLowerCtorArg
        internal set

    public var siUpper: MutPtr = siUpperCtorArg
        internal set

    public constructor() : this(
        siLowerCtorArg = MutPtr(0L),
        siUpperCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.siLower

    public operator fun component2(): MutPtr = this.siUpper

    public fun copy(siLower: MutPtr = this.siLower, siUpper: MutPtr = this.siUpper): CAnonymousFirstAddrBand = CAnonymousFirstAddrBand(siLowerCtorArg = siLower, siUpperCtorArg = siUpper)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousFirstAddrBand) return false
        if (this.siLower != other.siLower) return false
        if (this.siUpper != other.siUpper) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siLower.hashCode()
        result = 31 * result + this.siUpper.hashCode()
        return result
    }

    public fun clone(): CAnonymousFirstAddrBand = CAnonymousFirstAddrBand(siLower, siUpper)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousFirstAddrBand> =
            object : TypeLayout<CAnonymousFirstAddrBand> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousFirstAddrBand =
                    CAnonymousFirstAddrBand(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousFirstAddrBand) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.siLower)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.siUpper)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousFirstAddrBand =
                    CAnonymousFirstAddrBand(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousFirstAddrBand) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.siLower)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.siUpper)
                }
            }

        public operator fun invoke(
            siLower: MutPtr = MutPtr(0L),
            siUpper: MutPtr = MutPtr(0L),
        ): CAnonymousFirstAddrBand = CAnonymousFirstAddrBand(
            siLowerCtorArg = siLower,
            siUpperCtorArg = siUpper,
        )
    }
}
