// port-lint: source new/glibc/sysdeps/unix/linux/bits/types/siginfo_t.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.bits.types.siginfot

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.Sigval
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.ClockT

// Source header: `sysdeps / unix / sysv / linux / bits / types / siginfoT.h`
//
// Note this module corresponds with multiple header files upstream. The above
// header file contains the generic definition, while other
// architecture-specific definitions live upstream at
// `sysdeps / unix / sysv / linux / <arch> / bits / siginfo - arch.h`. Currently, the
// following set of header files are contained in this one Rust module:
//
// - `sysdeps / unix / sysv / linux / bits / types / siginfoT.h`
// - `sysdeps / unix / sysv / linux / x86 / bits / siginfo - arch.h`
// - `sysdeps / unix / sysv / linux / mips / bits / siginfo - arch.h`
// - `sysdeps / unix / sysv / linux / sparc / bits / siginfo - arch.h`
//
// <https://github.com/sailfishos-mirror/glibc/blob/master/sysdeps/unix/sysv/linux/bits/types/siginfoT.h>

public val SI_MAX_SIZE: Int = 128

public val SI_PAD_SIZE: Int =
    if (true) {
        SI_MAX_SIZE / sizeOf<CInt>(typeLayoutT = UnresolvedTypeLayout<CInt>()) - 4
    } else {
        SI_MAX_SIZE / sizeOf<CInt>(typeLayoutT = UnresolvedTypeLayout<CInt>()) - 3
    }

public typealias SiBandType = CInt

public open class SiClockT(
    public var value: ClockT,
) {
    public var field0: ClockT
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): SiClockT = SiClockT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<SiClockT> =
            object : TypeLayout<SiClockT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SiClockT =
                    SiClockT(
                        (PrimitiveLayouts.I64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SiClockT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SiClockT =
                    SiClockT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SiClockT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class SiginfoT(
    public var siSigno: CInt,
    public var siCode: CInt,
    public var siErrno: CInt,
    public var pad0: CInt,
    public var sifields: CAnonymousSiginfoTSiFields,
) {
    public open fun clone(): SiginfoT = SiginfoT(siSigno, siCode, siErrno, pad0, sifields)

    public open fun siPid(): PidT = this.sifields.kill.siPid

    public open fun siUid(): UidT = this.sifields.kill.siUid

    public open fun siTimerid(): CInt = this.sifields.timer.siTid

    public open fun siOverrun(): CInt = this.sifields.timer.siOverrun

    public open fun siStatus(): CInt = this.sifields.sigchld.siStatus

    public open fun siUtime(): ClockT = this.sifields.sigchld.siUtime.value

    public open fun siStime(): ClockT = this.sifields.sigchld.siStime.value

    public open fun siValue(): Sigval = this.sifields.rt.siSigval

    public open fun siInt(): CInt {
        // DIFF(main): sigval is a union on `main`, struct on `libc - 0.2`. The cast means
        // this can't be const at the MSRV.
        return this.sifields.rt.siSigval.sivalPtr.rawAddress.toInt()
    }

    public open fun siPtr(): MutPtr = this.sifields.rt.siSigval.sivalPtr

    public open fun siAddr(): MutPtr = this.sifields.sigfault.siAddr

    public open fun siTrapno(): CInt = this.sifields.sigfault.siTrapno

    public open fun siAddrLsb(): CShort = this.sifields.sigfault.siAddrLsb

    public open fun siLower(): MutPtr = this.sifields.sigfault.bounds.addrBnd.lower

    public open fun siUpper(): MutPtr = this.sifields.sigfault.bounds.addrBnd.upper

    public open fun siPkey(): UInt = this.sifields.sigfault.bounds.pkey

    public open fun siBand(): SiBandType = this.sifields.sigpoll.siBand

    public open fun siFd(): CInt = this.sifields.sigpoll.siFd

    public open fun siCallAddr(): MutPtr = this.sifields.sigsys.callAddr

    public open fun siSyscall(): CInt = this.sifields.sigsys.syscall

    public open fun siArch(): CUint = this.sifields.sigsys.arch

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
    public var pad: IntArray? = null,
    public var kill: CAnonymousSiFieldsKill? = null,
    public var timer: CAnonymousSiFieldsTimer? = null,
    public var rt: CAnonymousSiFieldsRt? = null,
    public var sigchld: CAnonymousSiFieldsSigchld? = null,
    public var sigfault: CAnonymousSiFieldsSigfault? = null,
    public var sigpoll: CAnonymousSiFieldsSigpoll? = null,
    public var sigsys: CAnonymousSiFieldsSigsys? = null,
)

public open class CAnonymousSiFieldsKill(
    public var siPid: PidT,
    public var siUid: UidT,
) {
    public open fun clone(): CAnonymousSiFieldsKill = CAnonymousSiFieldsKill(siPid, siUid)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSiFieldsKill> =
            object : TypeLayout<CAnonymousSiFieldsKill> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousSiFieldsKill =
                    CAnonymousSiFieldsKill(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSiFieldsKill) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siPid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siUid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSiFieldsKill =
                    CAnonymousSiFieldsKill(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSiFieldsKill) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siPid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siUid)
                }
            }
    }
}

public open class CAnonymousSiFieldsTimer(
    public var siTid: CInt,
    public var siOverrun: CInt,
    public var siSigval: Sigval,
) {
    public open fun clone(): CAnonymousSiFieldsTimer = CAnonymousSiFieldsTimer(siTid, siOverrun, siSigval)
}

public open class CAnonymousSiFieldsRt(
    public var siPid: PidT,
    public var siUid: UidT,
    public var siSigval: Sigval,
) {
    public open fun clone(): CAnonymousSiFieldsRt = CAnonymousSiFieldsRt(siPid, siUid, siSigval)
}

public open class CAnonymousSiFieldsSigchld(
    public var siPid: PidT,
    public var siUid: UidT,
    public var siStatus: CInt,
    public var siUtime: SiClockT,
    public var siStime: SiClockT,
) {
    public open fun clone(): CAnonymousSiFieldsSigchld = CAnonymousSiFieldsSigchld(siPid, siUid, siStatus, siUtime, siStime)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSiFieldsSigchld> =
            object : TypeLayout<CAnonymousSiFieldsSigchld> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): CAnonymousSiFieldsSigchld =
                    CAnonymousSiFieldsSigchld(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (SiClockT.typeLayout()).readNative(addr + 16L),
                        (SiClockT.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSiFieldsSigchld) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siPid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.siStatus)
                    (SiClockT.typeLayout()).writeNative(addr + 16L, value.siUtime)
                    (SiClockT.typeLayout()).writeNative(addr + 24L, value.siStime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSiFieldsSigchld =
                    CAnonymousSiFieldsSigchld(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (SiClockT.typeLayout()).readBytes(buf, offset + 16),
                        (SiClockT.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSiFieldsSigchld) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siPid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.siStatus)
                    (SiClockT.typeLayout()).writeBytes(buf, offset + 16, value.siUtime)
                    (SiClockT.typeLayout()).writeBytes(buf, offset + 24, value.siStime)
                }
            }
    }
}

public open class CAnonymousSiFieldsSigfault internal constructor(
    siAddrCtorArg: MutPtr,
    public var siTrapno: CInt,
    public var siAddrLsb: CShort,
    public var bounds: CAnonymousSigfaultBounds,
) {
    public var siAddr: MutPtr = siAddrCtorArg
        internal set

    public open fun clone(): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(siAddr, siTrapno, siAddrLsb, bounds)

    public companion object {
        public operator fun invoke(
            siAddr: MutPtr,
            siTrapno: CInt,
            siAddrLsb: CShort,
            bounds: CAnonymousSigfaultBounds,
        ): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(
            siAddrCtorArg = siAddr,
            siTrapno = siTrapno,
            siAddrLsb = siAddrLsb,
            bounds = bounds,
        )
    }
}

public open class CAnonymousSiFieldsSigpoll(
    public var siBand: SiBandType,
    public var siFd: CInt,
) {
    public open fun clone(): CAnonymousSiFieldsSigpoll = CAnonymousSiFieldsSigpoll(siBand, siFd)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousSiFieldsSigpoll> =
            object : TypeLayout<CAnonymousSiFieldsSigpoll> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousSiFieldsSigpoll =
                    CAnonymousSiFieldsSigpoll(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousSiFieldsSigpoll) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siBand)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siFd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSiFieldsSigpoll =
                    CAnonymousSiFieldsSigpoll(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSiFieldsSigpoll) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siBand)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siFd)
                }
            }
    }
}

public open class CAnonymousSiFieldsSigsys internal constructor(
    callAddrCtorArg: MutPtr,
    public var syscall: CInt,
    public var arch: CUint,
) {
    public var callAddr: MutPtr = callAddrCtorArg
        internal set

    public open fun clone(): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(callAddr, syscall, arch)

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
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.callAddr)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.syscall)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.arch)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousSiFieldsSigsys =
                    CAnonymousSiFieldsSigsys(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousSiFieldsSigsys) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.callAddr)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.syscall)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.arch)
                }
            }

        public operator fun invoke(
            callAddr: MutPtr,
            syscall: CInt,
            arch: CUint,
        ): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(
            callAddrCtorArg = callAddr,
            syscall = syscall,
            arch = arch,
        )
    }
}

public open class CAnonymousSigfaultBounds(
    public var addrBnd: CAnonymousBoundsAddrBnd? = null,
    public var pkey: UInt? = null,
)

public open class CAnonymousBoundsAddrBnd internal constructor(
    lowerCtorArg: MutPtr,
    upperCtorArg: MutPtr,
) {
    public var lower: MutPtr = lowerCtorArg
        internal set

    public var upper: MutPtr = upperCtorArg
        internal set

    public open fun clone(): CAnonymousBoundsAddrBnd = CAnonymousBoundsAddrBnd(lower, upper)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousBoundsAddrBnd> =
            object : TypeLayout<CAnonymousBoundsAddrBnd> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousBoundsAddrBnd =
                    CAnonymousBoundsAddrBnd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousBoundsAddrBnd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.lower)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.upper)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousBoundsAddrBnd =
                    CAnonymousBoundsAddrBnd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousBoundsAddrBnd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.lower)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.upper)
                }
            }

        public operator fun invoke(
            lower: MutPtr,
            upper: MutPtr,
        ): CAnonymousBoundsAddrBnd = CAnonymousBoundsAddrBnd(
            lowerCtorArg = lower,
            upperCtorArg = upper,
        )
    }
}
