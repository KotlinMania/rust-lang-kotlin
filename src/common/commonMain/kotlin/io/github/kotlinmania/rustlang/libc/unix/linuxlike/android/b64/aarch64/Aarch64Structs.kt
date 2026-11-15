@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.aarch64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.DevT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SigsetT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.InoT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.NlinkT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.StackT

public class Stat(
    public var stDev: DevT = 0uL,
    public var stIno: InoT = 0uL,
    public var stMode: CUint = 0u,
    public var stNlink: NlinkT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0uL,
    public var pad1: CUlong = 0uL,
    public var stSize: Off64T = 0L,
    public var stBlksize: CInt = 0,
    public var pad2: CInt = 0,
    public var stBlocks: CLong = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused4: CUint = 0u,
    public var unused5: CUint = 0u,
) {
    public fun clone(): Stat = Stat(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused4, unused5)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + 60L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 120L),
                        (PrimitiveLayouts.U32).readNative(addr + 124L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stMode)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stNlink)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stGid)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.stRdev)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.pad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I32).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I32).writeNative(addr + 60L, value.pad2)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (PrimitiveLayouts.U32).writeNative(addr + 120L, value.unused4)
                    (PrimitiveLayouts.U32).writeNative(addr + 124L, value.unused5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 124),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stMode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stNlink)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stGid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.stRdev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.pad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 60, value.pad2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 120, value.unused4)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 124, value.unused5)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public class Stat64(
    public var stDev: DevT = 0uL,
    public var stIno: InoT = 0uL,
    public var stMode: CUint = 0u,
    public var stNlink: NlinkT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0uL,
    public var pad1: CUlong = 0uL,
    public var stSize: Off64T = 0L,
    public var stBlksize: CInt = 0,
    public var pad2: CInt = 0,
    public var stBlocks: CLong = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused4: CUint = 0u,
    public var unused5: CUint = 0u,
) {
    public fun clone(): Stat64 = Stat64(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused4, unused5)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat64> =
            object : TypeLayout<Stat64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 56L),
                        (PrimitiveLayouts.I32).readNative(addr + 60L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 120L),
                        (PrimitiveLayouts.U32).readNative(addr + 124L),
                    )
                override fun writeNative(addr: Long, value: Stat64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stMode)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stNlink)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stGid)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.stRdev)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.pad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I32).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I32).writeNative(addr + 60L, value.pad2)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (PrimitiveLayouts.U32).writeNative(addr + 120L, value.unused4)
                    (PrimitiveLayouts.U32).writeNative(addr + 124L, value.unused5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 124),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stMode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stNlink)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stGid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.stRdev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.pad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 60, value.pad2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 120, value.unused4)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 124, value.unused5)
                }
            }
    }
}

public data class UserRegsStruct(
    public var regs: ULongArray = ULongArray(31),
    public var sp: ULong = 0uL,
    public var pc: ULong = 0uL,
    public var pstate: ULong = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserRegsStruct) return false
        if (!this.regs.contentEquals(other.regs)) return false
        if (this.sp != other.sp) return false
        if (this.pc != other.pc) return false
        if (this.pstate != other.pstate) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.regs.contentHashCode()
        result = 31 * result + this.sp.hashCode()
        result = 31 * result + this.pc.hashCode()
        result = 31 * result + this.pstate.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UserRegsStruct> =
            object : TypeLayout<UserRegsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): UserRegsStruct =
                    UserRegsStruct(
                        (PrimitiveArrayLayouts.uLongArray(31)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 248L),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                    )
                override fun writeNative(addr: Long, value: UserRegsStruct) {
                    (PrimitiveArrayLayouts.uLongArray(31)).writeNative(addr, value.regs)
                    (PrimitiveLayouts.U64).writeNative(addr + 248L, value.sp)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.pc)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.pstate)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserRegsStruct =
                    UserRegsStruct(
                        (PrimitiveArrayLayouts.uLongArray(31)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 248),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserRegsStruct) {
                    (PrimitiveArrayLayouts.uLongArray(31)).writeBytes(buf, offset, value.regs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 248, value.sp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.pc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.pstate)
                }
            }
    }
}

public class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: StackT,
    public var ucSigmask: SigsetT,
    // The kernel adds extra padding after ucSigmask to match
    // glibc sigsetT on ARM64.
    public var padding: UByteArray,
    ucMcontextCtorArg: McontextT,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public constructor() : this(
        ucFlags = 0uL,
        ucLinkCtorArg = MutPtr(0L),
        ucStack = StackT(),
        ucSigmask = SigsetT(),
        padding = UByteArray((128 - sizeOf<SigsetT>(typeLayoutT = ObjectSlotLayout<SigsetT>())).toInt()),
        ucMcontextCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CUlong = this.ucFlags

    public operator fun component2(): MutPtr = this.ucLink

    public operator fun component3(): StackT = this.ucStack

    public operator fun component4(): SigsetT = this.ucSigmask

    public operator fun component5(): UByteArray = this.padding

    public operator fun component6(): McontextT = this.ucMcontext

    public fun copy(ucFlags: CUlong = this.ucFlags, ucLink: MutPtr = this.ucLink, ucStack: StackT = this.ucStack, ucSigmask: SigsetT = this.ucSigmask, padding: UByteArray = this.padding, ucMcontext: McontextT = this.ucMcontext): UcontextT = UcontextT(ucFlags = ucFlags, ucLinkCtorArg = ucLink, ucStack = ucStack, ucSigmask = ucSigmask, padding = padding, ucMcontextCtorArg = ucMcontext)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UcontextT) return false
        if (this.ucFlags != other.ucFlags) return false
        if (this.ucLink != other.ucLink) return false
        if (this.ucStack != other.ucStack) return false
        if (this.ucSigmask != other.ucSigmask) return false
        if (!this.padding.contentEquals(other.padding)) return false
        if (this.ucMcontext != other.ucMcontext) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ucFlags.hashCode()
        result = 31 * result + this.ucLink.hashCode()
        result = 31 * result + this.ucStack.hashCode()
        result = 31 * result + this.ucSigmask.hashCode()
        result = 31 * result + this.padding.contentHashCode()
        result = 31 * result + this.ucMcontext.hashCode()
        return result
    }

    public fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucSigmask, padding, ucMcontext)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong = 0uL,
            ucLink: MutPtr = MutPtr(0L),
            ucStack: StackT = StackT(),
            ucSigmask: SigsetT = SigsetT(),
            padding: UByteArray = UByteArray((128 - sizeOf<SigsetT>(typeLayoutT = ObjectSlotLayout<SigsetT>())).toInt()),
            ucMcontext: McontextT = MutPtr(0L),
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucSigmask = ucSigmask,
            padding = padding,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public data class McontextT(
    public var faultAddress: CUlonglong = 0uL,
    public var regs: ULongArray = ULongArray(31),
    public var sp: CUlonglong = 0uL,
    public var pc: CUlonglong = 0uL,
    public var pstate: CUlonglong = 0uL,
    public var reserved: ULongArray = ULongArray(512),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is McontextT) return false
        if (this.faultAddress != other.faultAddress) return false
        if (!this.regs.contentEquals(other.regs)) return false
        if (this.sp != other.sp) return false
        if (this.pc != other.pc) return false
        if (this.pstate != other.pstate) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.faultAddress.hashCode()
        result = 31 * result + this.regs.contentHashCode()
        result = 31 * result + this.sp.hashCode()
        result = 31 * result + this.pc.hashCode()
        result = 31 * result + this.pstate.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 4384L
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(31)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                        (PrimitiveLayouts.U64).readNative(addr + 272L),
                        (PrimitiveArrayLayouts.uLongArray(512)).readNative(addr + 280L),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.faultAddress)
                    (PrimitiveArrayLayouts.uLongArray(31)).writeNative(addr + 8L, value.regs)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.sp)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.pc)
                    (PrimitiveLayouts.U64).writeNative(addr + 272L, value.pstate)
                    (PrimitiveArrayLayouts.uLongArray(512)).writeNative(addr + 280L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(31)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 272),
                        (PrimitiveArrayLayouts.uLongArray(512)).readBytes(buf, offset + 280),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.faultAddress)
                    (PrimitiveArrayLayouts.uLongArray(31)).writeBytes(buf, offset + 8, value.regs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.sp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.pc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 272, value.pstate)
                    (PrimitiveArrayLayouts.uLongArray(512)).writeBytes(buf, offset + 280, value.reserved)
                }
            }
    }
}

public data class UserFpsimdStruct(
    public var vregs: List<U128> = List(32) { U128() },
    public var fpsr: UInt = 0u,
    public var fpcr: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<UserFpsimdStruct> =
            object : TypeLayout<UserFpsimdStruct> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 528L
                override fun readNative(addr: Long): UserFpsimdStruct =
                    UserFpsimdStruct(
                        (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 512L),
                        (PrimitiveLayouts.U32).readNative(addr + 516L),
                    )
                override fun writeNative(addr: Long, value: UserFpsimdStruct) {
                    (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).writeNative(addr, value.vregs)
                    (PrimitiveLayouts.U32).writeNative(addr + 512L, value.fpsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 516L, value.fpcr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserFpsimdStruct =
                    UserFpsimdStruct(
                        (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 512),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 516),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserFpsimdStruct) {
                    (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).writeBytes(buf, offset, value.vregs)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 512, value.fpsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 516, value.fpcr)
                }
            }
    }
}

public data class MaxAlignT(
    public var priv: FloatArray = FloatArray(8),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaxAlignT) return false
        if (!this.priv.contentEquals(other.priv)) return false
        return true
    }

    override fun hashCode(): Int = this.priv.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<MaxAlignT> =
            object : TypeLayout<MaxAlignT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.floatArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.floatArray(8)).writeNative(addr, value.priv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.floatArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.floatArray(8)).writeBytes(buf, offset, value.priv)
                }
            }
    }
}
