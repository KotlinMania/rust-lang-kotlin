// port-lint: source new/glibc/sysdeps/unix/linux/bits/types/siginfo_t.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.bits.types.siginfot

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
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
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ClockT

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
        SI_MAX_SIZE / sizeOf<CInt>(typeLayoutT = ObjectSlotLayout<CInt>()) - 4
    } else {
        SI_MAX_SIZE / sizeOf<CInt>(typeLayoutT = ObjectSlotLayout<CInt>()) - 3
    }

public typealias SiBandType = CInt

public data class SiClockT(
    public var value: ClockT = 0L,
) {
    public var field0: ClockT
        get() = value
        set(v) {
            value = v
        }

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

public data class SiginfoT(
    public var siSigno: CInt = 0,
    public var siCode: CInt = 0,
    public var siErrno: CInt = 0,
    public var pad0: CInt = 0,
    public var sifields: CAnonymousSiginfoTSiFields = CAnonymousSiginfoTSiFields(),
) {
    public fun siPid(): PidT = this.sifields.kill.siPid

    public fun siUid(): UidT = this.sifields.kill.siUid

    public fun siTimerid(): CInt = this.sifields.timer.siTid

    public fun siOverrun(): CInt = this.sifields.timer.siOverrun

    public fun siStatus(): CInt = this.sifields.sigchld.siStatus

    public fun siUtime(): ClockT = this.sifields.sigchld.siUtime.value

    public fun siStime(): ClockT = this.sifields.sigchld.siStime.value

    public fun siValue(): Sigval = this.sifields.rt.siSigval

    public fun siInt(): CInt {
        // DIFF(main): sigval is a union on `main`, struct on `libc - 0.2`. The cast means
        // this can't be const at the MSRV.
        return this.sifields.rt.siSigval.sivalPtr.rawAddress.toInt()
    }

    public fun siPtr(): MutPtr = this.sifields.rt.siSigval.sivalPtr

    public fun siAddr(): MutPtr = this.sifields.sigfault.siAddr

    public fun siTrapno(): CInt = this.sifields.sigfault.siTrapno

    public fun siAddrLsb(): CShort = this.sifields.sigfault.siAddrLsb

    public fun siLower(): MutPtr = this.sifields.sigfault.bounds.addrBnd.lower

    public fun siUpper(): MutPtr = this.sifields.sigfault.bounds.addrBnd.upper

    public fun siPkey(): UInt = this.sifields.sigfault.bounds.pkey

    public fun siBand(): SiBandType = this.sifields.sigpoll.siBand

    public fun siFd(): CInt = this.sifields.sigpoll.siFd

    public fun siCallAddr(): MutPtr = this.sifields.sigsys.callAddr

    public fun siSyscall(): CInt = this.sifields.sigsys.syscall

    public fun siArch(): CUint = this.sifields.sigsys.arch

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
    public var pad: IntArray = IntArray(SI_PAD_SIZE),
    public var kill: CAnonymousSiFieldsKill = CAnonymousSiFieldsKill(),
    public var timer: CAnonymousSiFieldsTimer = CAnonymousSiFieldsTimer(),
    public var rt: CAnonymousSiFieldsRt = CAnonymousSiFieldsRt(),
    public var sigchld: CAnonymousSiFieldsSigchld = CAnonymousSiFieldsSigchld(),
    public var sigfault: CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(),
    public var sigpoll: CAnonymousSiFieldsSigpoll = CAnonymousSiFieldsSigpoll(),
    public var sigsys: CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousSiginfoTSiFields) return false
        if (!this.pad.contentEquals(other.pad)) return false
        if (this.kill != other.kill) return false
        if (this.timer != other.timer) return false
        if (this.rt != other.rt) return false
        if (this.sigchld != other.sigchld) return false
        if (this.sigfault != other.sigfault) return false
        if (this.sigpoll != other.sigpoll) return false
        if (this.sigsys != other.sigsys) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pad.contentHashCode()
        result = 31 * result + this.kill.hashCode()
        result = 31 * result + this.timer.hashCode()
        result = 31 * result + this.rt.hashCode()
        result = 31 * result + this.sigchld.hashCode()
        result = 31 * result + this.sigfault.hashCode()
        result = 31 * result + this.sigpoll.hashCode()
        result = 31 * result + this.sigsys.hashCode()
        return result
    }
}

public data class CAnonymousSiFieldsKill(
    public var siPid: PidT = 0,
    public var siUid: UidT = 0,
) {
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

public data class CAnonymousSiFieldsTimer(
    public var siTid: CInt = 0,
    public var siOverrun: CInt = 0,
    public var siSigval: Sigval = Sigval(),
)

public data class CAnonymousSiFieldsRt(
    public var siPid: PidT = 0,
    public var siUid: UidT = 0,
    public var siSigval: Sigval = Sigval(),
)

public data class CAnonymousSiFieldsSigchld(
    public var siPid: PidT = 0,
    public var siUid: UidT = 0,
    public var siStatus: CInt = 0,
    public var siUtime: SiClockT = SiClockT(),
    public var siStime: SiClockT = SiClockT(),
) {
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

public class CAnonymousSiFieldsSigfault internal constructor(
    siAddrCtorArg: MutPtr,
    public var siTrapno: CInt,
    public var siAddrLsb: CShort,
    public var bounds: CAnonymousSigfaultBounds,
) {
    public var siAddr: MutPtr = siAddrCtorArg
        internal set

    public constructor() : this(
        siAddrCtorArg = MutPtr(0L),
        siTrapno = 0,
        siAddrLsb = 0,
        bounds = CAnonymousSigfaultBounds(),
    )

    public operator fun component1(): MutPtr = this.siAddr

    public operator fun component2(): CInt = this.siTrapno

    public operator fun component3(): CShort = this.siAddrLsb

    public operator fun component4(): CAnonymousSigfaultBounds = this.bounds

    public fun copy(siAddr: MutPtr = this.siAddr, siTrapno: CInt = this.siTrapno, siAddrLsb: CShort = this.siAddrLsb, bounds: CAnonymousSigfaultBounds = this.bounds): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(siAddrCtorArg = siAddr, siTrapno = siTrapno, siAddrLsb = siAddrLsb, bounds = bounds)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousSiFieldsSigfault) return false
        if (this.siAddr != other.siAddr) return false
        if (this.siTrapno != other.siTrapno) return false
        if (this.siAddrLsb != other.siAddrLsb) return false
        if (this.bounds != other.bounds) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siAddr.hashCode()
        result = 31 * result + this.siTrapno.hashCode()
        result = 31 * result + this.siAddrLsb.hashCode()
        result = 31 * result + this.bounds.hashCode()
        return result
    }

    public fun clone(): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(siAddr, siTrapno, siAddrLsb, bounds)

    public companion object {
        public operator fun invoke(
            siAddr: MutPtr = MutPtr(0L),
            siTrapno: CInt = 0,
            siAddrLsb: CShort = 0,
            bounds: CAnonymousSigfaultBounds = CAnonymousSigfaultBounds(),
        ): CAnonymousSiFieldsSigfault = CAnonymousSiFieldsSigfault(
            siAddrCtorArg = siAddr,
            siTrapno = siTrapno,
            siAddrLsb = siAddrLsb,
            bounds = bounds,
        )
    }
}

public data class CAnonymousSiFieldsSigpoll(
    public var siBand: SiBandType = 0,
    public var siFd: CInt = 0,
) {
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

public class CAnonymousSiFieldsSigsys internal constructor(
    callAddrCtorArg: MutPtr,
    public var syscall: CInt,
    public var arch: CUint,
) {
    public var callAddr: MutPtr = callAddrCtorArg
        internal set

    public constructor() : this(
        callAddrCtorArg = MutPtr(0L),
        syscall = 0,
        arch = 0u,
    )

    public operator fun component1(): MutPtr = this.callAddr

    public operator fun component2(): CInt = this.syscall

    public operator fun component3(): CUint = this.arch

    public fun copy(callAddr: MutPtr = this.callAddr, syscall: CInt = this.syscall, arch: CUint = this.arch): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(callAddrCtorArg = callAddr, syscall = syscall, arch = arch)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousSiFieldsSigsys) return false
        if (this.callAddr != other.callAddr) return false
        if (this.syscall != other.syscall) return false
        if (this.arch != other.arch) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.callAddr.hashCode()
        result = 31 * result + this.syscall.hashCode()
        result = 31 * result + this.arch.hashCode()
        return result
    }

    public fun clone(): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(callAddr, syscall, arch)

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
            callAddr: MutPtr = MutPtr(0L),
            syscall: CInt = 0,
            arch: CUint = 0u,
        ): CAnonymousSiFieldsSigsys = CAnonymousSiFieldsSigsys(
            callAddrCtorArg = callAddr,
            syscall = syscall,
            arch = arch,
        )
    }
}

public data class CAnonymousSigfaultBounds(
    public var addrBnd: CAnonymousBoundsAddrBnd = CAnonymousBoundsAddrBnd(),
    public var pkey: UInt = 0u,
)

public class CAnonymousBoundsAddrBnd internal constructor(
    lowerCtorArg: MutPtr,
    upperCtorArg: MutPtr,
) {
    public var lower: MutPtr = lowerCtorArg
        internal set

    public var upper: MutPtr = upperCtorArg
        internal set

    public constructor() : this(
        lowerCtorArg = MutPtr(0L),
        upperCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.lower

    public operator fun component2(): MutPtr = this.upper

    public fun copy(lower: MutPtr = this.lower, upper: MutPtr = this.upper): CAnonymousBoundsAddrBnd = CAnonymousBoundsAddrBnd(lowerCtorArg = lower, upperCtorArg = upper)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousBoundsAddrBnd) return false
        if (this.lower != other.lower) return false
        if (this.upper != other.upper) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.lower.hashCode()
        result = 31 * result + this.upper.hashCode()
        return result
    }

    public fun clone(): CAnonymousBoundsAddrBnd = CAnonymousBoundsAddrBnd(lower, upper)

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
            lower: MutPtr = MutPtr(0L),
            upper: MutPtr = MutPtr(0L),
        ): CAnonymousBoundsAddrBnd = CAnonymousBoundsAddrBnd(
            lowerCtorArg = lower,
            upperCtorArg = upper,
        )
    }
}
