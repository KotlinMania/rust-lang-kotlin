// port-lint: source new/musl/signal.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.musl.signal

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.Sigval
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.ClockT

// Header source: `include / signal.h`
//
// * Headers: <https://git.musl-libc.org/cgit/musl/tree/include/signal.h> (official)
// * Headers: <https://github.com/kraj/musl/blob/master/include/signal.h> (mirror)

// `mips *` targets swap the `sErrno` and `sCode` fields otherwise this
// struct is target-agnostic (see
// <https://www.openwall.com/lists/musl/2016/01/27/1/2>)
public open class SiginfoT(
    public var siSigno: CInt,
    public var siCode: CInt,
    public var siErrno: CInt,
    public var siFields: CAnonymousSiginfoTSiFields,
) {
    public open fun clone(): SiginfoT = SiginfoT(siSigno, siCode, siErrno, siFields)

    public open fun siPid(): PidT = this.siFields.siCommon.first.piduid.siPid

    public open fun siUid(): UidT = this.siFields.siCommon.first.piduid.siUid

    public open fun siStatus(): CInt = this.siFields.siCommon.second.sigchld.siStatus

    public open fun siUtime(): ClockT = this.siFields.siCommon.second.sigchld.siUtime

    public open fun siStime(): ClockT = this.siFields.siCommon.second.sigchld.siStime

    public open fun siValue(): Sigval = this.siFields.siCommon.second.siValue

    public open fun siAddr(): MutPtr = this.siFields.sigfault.siAddr

    public open fun siAddrLsb(): CShort = this.siFields.sigfault.siAddrLsb

    public open fun siLower(): MutPtr = this.siFields.sigfault.first.addrBand.siLower

    public open fun siUpper(): MutPtr = this.siFields.sigfault.first.addrBand.siUpper

    public open fun siPkey(): CUint = this.siFields.sigfault.first.siPkey

    public open fun siBand(): CLong = this.siFields.sigpoll.siBand

    public open fun siFd(): CInt = this.siFields.sigpoll.siFd

    public open fun siTimerid(): CInt = this.siFields.siCommon.first.timer.siTimerid

    public open fun siOverrun(): CInt = this.siFields.siCommon.first.timer.siOverrun

    public open fun siPtr(): MutPtr = this.siFields.siCommon.second.siValue.sivalPtr

    public open fun siInt(): CInt {
        // DIFF(main): sigval is a union on `main`, struct on `libc - 0.2`. The cast means
        // this can't be const at the MSRV.
        return this.siFields.siCommon.second.siValue.sivalPtr.rawAddress.toInt()
    }

    public open fun siCallAddr(): MutPtr = this.siFields.sigsys.siCallAddr

    public open fun siSyscall(): CInt = this.siFields.sigsys.siSyscall

    public open fun siArch(): CUint = this.siFields.sigsys.siArch

    public open fun eq(other: SiginfoT): Boolean = Triple(this.siSigno, this.siErrno, this.siCode) == Triple(other.siSigno, other.siErrno, other.siCode)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.siSigno).hashCode()
        state = 31 * state + (this.siErrno).hashCode()
        state = 31 * state + (this.siCode).hashCode()
        return state
    }
}

public open class CAnonymousSiginfoTSiFields(
    public var pad: UByteArray? = null,
    public var siCommon: CAnonymousSiFieldsSiCommon? = null,
    public var sigfault: CAnonymousSiFieldsSigfault? = null,
    public var sigpoll: CAnonymousSiFieldsSigpoll? = null,
    public var sigsys: CAnonymousSiFieldsSigsys? = null,
)

public open class CAnonymousSiFieldsSiCommon(
    public var first: CAnonymousSiCommonFirst,
    public var second: CAnonymousSiCommonSecond,
) {
    public open fun clone(): CAnonymousSiFieldsSiCommon = CAnonymousSiFieldsSiCommon(first, second)
}

public open class CAnonymousSiFieldsSigfault internal constructor(
    siAddrCtorArg: MutPtr,
    public var siAddrLsb: CShort,
    public var first: CAnonymousSigfaultFirst,
) {
    public var siAddr: MutPtr = siAddrCtorArg
        internal set

    public open fun clone(): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(siAddr, siAddrLsb, first)

    public companion object {
        public operator fun invoke(
            siAddr: MutPtr,
            siAddrLsb: CShort,
            first: CAnonymousSigfaultFirst,
        ): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(
            siAddrCtorArg = siAddr,
            siAddrLsb = siAddrLsb,
            first = first,
        )
    }
}

public open class CAnonymousSiFieldsSigpoll(
    public var siBand: CLong,
    public var siFd: CInt,
) {
    public open fun clone(): CAnonymousSiFieldsSigpoll = CAnonymousSiFieldsSigpoll(siBand, siFd)

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

public open class CAnonymousSiFieldsSigsys internal constructor(
    siCallAddrCtorArg: MutPtr,
    public var siSyscall: CInt,
    public var siArch: CUint,
) {
    public var siCallAddr: MutPtr = siCallAddrCtorArg
        internal set

    public open fun clone(): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(siCallAddr, siSyscall, siArch)

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
            siCallAddr: MutPtr,
            siSyscall: CInt,
            siArch: CUint,
        ): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(
            siCallAddrCtorArg = siCallAddr,
            siSyscall = siSyscall,
            siArch = siArch,
        )
    }
}

public open class CAnonymousSiCommonFirst(
    public var piduid: CAnonymousFirstPiduid? = null,
    public var timer: CAnonymousFirstTimer? = null,
)

public open class CAnonymousSiCommonSecond(
    public var siValue: Sigval? = null,
    public var sigchld: CAnonymousSecondSigchld? = null,
)

public open class CAnonymousSigfaultFirst(
    public var addrBand: CAnonymousFirstAddrBand? = null,
    public var siPkey: CUint? = null,
)

public open class CAnonymousFirstPiduid(
    public var siPid: PidT,
    public var siUid: UidT,
) {
    public open fun clone(): CAnonymousFirstPiduid = CAnonymousFirstPiduid(siPid, siUid)

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

public open class CAnonymousFirstTimer(
    public var siTimerid: CInt,
    public var siOverrun: CInt,
) {
    public open fun clone(): CAnonymousFirstTimer = CAnonymousFirstTimer(siTimerid, siOverrun)

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

public open class CAnonymousSecondSigchld(
    public var siStatus: CInt,
    public var siUtime: ClockT,
    public var siStime: ClockT,
) {
    public open fun clone(): CAnonymousSecondSigchld = CAnonymousSecondSigchld(siStatus, siUtime, siStime)

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

public open class CAnonymousFirstAddrBand internal constructor(
    siLowerCtorArg: MutPtr,
    siUpperCtorArg: MutPtr,
) {
    public var siLower: MutPtr = siLowerCtorArg
        internal set

    public var siUpper: MutPtr = siUpperCtorArg
        internal set

    public open fun clone(): CAnonymousFirstAddrBand = CAnonymousFirstAddrBand(siLower, siUpper)

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
            siLower: MutPtr,
            siUpper: MutPtr,
        ): CAnonymousFirstAddrBand = CAnonymousFirstAddrBand(
            siLowerCtorArg = siLower,
            siUpperCtorArg = siUpper,
        )
    }
}
