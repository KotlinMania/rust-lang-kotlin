@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.FsidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Blkcnt64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.DevT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Ino64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.BlkcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.FsblkcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.FsfilcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.FswordT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.InoT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ShmattT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT

public data class Statfs(
    public var fType: FswordT = 0L,
    public var fBsize: FswordT = 0L,
    public var fBlocks: FsblkcntT = 0uL,
    public var fBfree: FsblkcntT = 0uL,
    public var fBavail: FsblkcntT = 0uL,
    public var fFiles: FsfilcntT = 0uL,
    public var fFfree: FsfilcntT = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: FswordT = 0L,
    public var fFrsize: FswordT = 0L,
    public var fSpare: LongArray = LongArray(5),
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
        result = 31 * result + this.fSpare.contentHashCode()
        return result
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

public class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssFlags: CInt,
    public var ssSize: SizeT,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public constructor() : this(
        ssSpCtorArg = MutPtr(0L),
        ssFlags = 0,
        ssSize = 0uL,
    )

    public operator fun component1(): MutPtr = this.ssSp

    public operator fun component2(): CInt = this.ssFlags

    public operator fun component3(): SizeT = this.ssSize

    public fun copy(ssSp: MutPtr = this.ssSp, ssFlags: CInt = this.ssFlags, ssSize: SizeT = this.ssSize): StackT = StackT(ssSpCtorArg = ssSp, ssFlags = ssFlags, ssSize = ssSize)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StackT) return false
        if (this.ssSp != other.ssSp) return false
        if (this.ssFlags != other.ssFlags) return false
        if (this.ssSize != other.ssSize) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ssSp.hashCode()
        result = 31 * result + this.ssFlags.hashCode()
        result = 31 * result + this.ssSize.hashCode()
        return result
    }

    public fun clone(): StackT = StackT(ssSp, ssFlags, ssSize)

    public companion object {
        public fun typeLayout(): TypeLayout<StackT> =
            object : TypeLayout<StackT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): StackT =
                    StackT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: StackT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ssSp)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ssFlags)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.ssSize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StackT =
                    StackT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StackT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ssSp)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ssFlags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.ssSize)
                }
            }

        public operator fun invoke(
            ssSp: MutPtr = MutPtr(0L),
            ssFlags: CInt = 0,
            ssSize: SizeT = 0uL,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssFlags = ssFlags,
            ssSize = ssSize,
        )
    }
}

public class Stat(
    public var stDev: DevT = 0uL,
    public var stIno: InoT = 0uL,
    public var stNlink: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.NlinkT = 0uL,
    public var stMode: ModeT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var pad0: CInt = 0,
    public var stRdev: DevT = 0uL,
    public var stSize: OffT = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.BlksizeT = 0L,
    public var stBlocks: BlkcntT = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: Long = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: Long = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: Long = 0L,
    public var unused: LongArray = LongArray(3),
) {
    public fun clone(): Stat = Stat(stDev, stIno, stNlink, stMode, stUid, stGid, pad0, stRdev, stSize, stBlksize, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveArrayLayouts.longArray(3)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 120L, value.unused)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 120, value.unused)
                }
            }
    }
}

public class Stat64(
    public var stDev: DevT = 0uL,
    public var stIno: Ino64T = 0uL,
    public var stNlink: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.NlinkT = 0uL,
    public var stMode: ModeT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var pad0: CInt = 0,
    public var stRdev: DevT = 0uL,
    public var stSize: OffT = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.BlksizeT = 0L,
    public var stBlocks: Blkcnt64T = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: Long = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: Long = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: Long = 0L,
    public var reserved: LongArray = LongArray(3),
) {
    public fun clone(): Stat64 = Stat64(stDev, stIno, stNlink, stMode, stUid, stGid, pad0, stRdev, stSize, stBlksize, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat64> =
            object : TypeLayout<Stat64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveArrayLayouts.longArray(3)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 120L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 120, value.reserved)
                }
            }
    }
}

public data class Statfs64(
    public var fType: FswordT = 0L,
    public var fBsize: FswordT = 0L,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: FswordT = 0L,
    public var fFrsize: FswordT = 0L,
    public var fFlags: FswordT = 0L,
    public var fSpare: LongArray = LongArray(4),
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

public data class PthreadAttrT(
    public var size: ULongArray = ULongArray(7),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadAttrT) return false
        if (!this.size.contentEquals(other.size)) return false
        return true
    }

    override fun hashCode(): Int = this.size.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadAttrT> =
            object : TypeLayout<PthreadAttrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveArrayLayouts.uLongArray(7)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadAttrT) {
                    (PrimitiveArrayLayouts.uLongArray(7)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveArrayLayouts.uLongArray(7)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadAttrT) {
                    (PrimitiveArrayLayouts.uLongArray(7)).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public data class LibcFpxreg(
    public var significand: UShortArray = UShortArray(4),
    public var exponent: UShort = 0u,
    public var private: UShortArray = UShortArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibcFpxreg) return false
        if (!this.significand.contentEquals(other.significand)) return false
        if (this.exponent != other.exponent) return false
        if (!this.private.contentEquals(other.private)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.significand.contentHashCode()
        result = 31 * result + this.exponent.hashCode()
        result = 31 * result + this.private.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<LibcFpxreg> =
            object : TypeLayout<LibcFpxreg> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): LibcFpxreg =
                    LibcFpxreg(
                        (PrimitiveArrayLayouts.uShortArray(4)).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uShortArray(3)).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: LibcFpxreg) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeNative(addr, value.significand)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.exponent)
                    (PrimitiveArrayLayouts.uShortArray(3)).writeNative(addr + 10L, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibcFpxreg =
                    LibcFpxreg(
                        (PrimitiveArrayLayouts.uShortArray(4)).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uShortArray(3)).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibcFpxreg) {
                    (PrimitiveArrayLayouts.uShortArray(4)).writeBytes(buf, offset, value.significand)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.exponent)
                    (PrimitiveArrayLayouts.uShortArray(3)).writeBytes(buf, offset + 10, value.private)
                }
            }
    }
}

public data class LibcXmmreg(
    public var element: UIntArray = UIntArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibcXmmreg) return false
        if (!this.element.contentEquals(other.element)) return false
        return true
    }

    override fun hashCode(): Int = this.element.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<LibcXmmreg> =
            object : TypeLayout<LibcXmmreg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): LibcXmmreg =
                    LibcXmmreg(
                        (PrimitiveArrayLayouts.uIntArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: LibcXmmreg) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeNative(addr, value.element)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibcXmmreg =
                    LibcXmmreg(
                        (PrimitiveArrayLayouts.uIntArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibcXmmreg) {
                    (PrimitiveArrayLayouts.uIntArray(4)).writeBytes(buf, offset, value.element)
                }
            }
    }
}

public data class LibcFpstate(
    public var cwd: UShort = 0u,
    public var swd: UShort = 0u,
    public var ftw: UShort = 0u,
    public var fop: UShort = 0u,
    public var rip: ULong = 0uL,
    public var rdp: ULong = 0uL,
    public var mxcsr: UInt = 0u,
    public var mxcrMask: UInt = 0u,
    public var st: List<LibcFpxreg> = List(8) { LibcFpxreg() },
    public var xmm: List<LibcXmmreg> = List(16) { LibcXmmreg() },
    public var private: ULongArray = ULongArray(12),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LibcFpstate) return false
        if (this.cwd != other.cwd) return false
        if (this.swd != other.swd) return false
        if (this.ftw != other.ftw) return false
        if (this.fop != other.fop) return false
        if (this.rip != other.rip) return false
        if (this.rdp != other.rdp) return false
        if (this.mxcsr != other.mxcsr) return false
        if (this.mxcrMask != other.mxcrMask) return false
        if (this.st != other.st) return false
        if (this.xmm != other.xmm) return false
        if (!this.private.contentEquals(other.private)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cwd.hashCode()
        result = 31 * result + this.swd.hashCode()
        result = 31 * result + this.ftw.hashCode()
        result = 31 * result + this.fop.hashCode()
        result = 31 * result + this.rip.hashCode()
        result = 31 * result + this.rdp.hashCode()
        result = 31 * result + this.mxcsr.hashCode()
        result = 31 * result + this.mxcrMask.hashCode()
        result = 31 * result + this.st.hashCode()
        result = 31 * result + this.xmm.hashCode()
        result = 31 * result + this.private.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<LibcFpstate> =
            object : TypeLayout<LibcFpstate> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): LibcFpstate =
                    LibcFpstate(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).readNative(addr + 32L),
                        (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).readNative(addr + 160L),
                        (PrimitiveArrayLayouts.uLongArray(12)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: LibcFpstate) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.cwd)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.swd)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.ftw)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.fop)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.rdp)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxcsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxcrMask)
                    (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).writeNative(addr + 32L, value.st)
                    (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).writeNative(addr + 160L, value.xmm)
                    (PrimitiveArrayLayouts.uLongArray(12)).writeNative(addr + 416L, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LibcFpstate =
                    LibcFpstate(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).readBytes(buf, offset + 32),
                        (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).readBytes(buf, offset + 160),
                        (PrimitiveArrayLayouts.uLongArray(12)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LibcFpstate) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.cwd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.swd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.ftw)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.fop)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.rdp)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxcsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxcrMask)
                    (fixedListTypeLayout(LibcFpxreg.typeLayout(), 8)).writeBytes(buf, offset + 32, value.st)
                    (fixedListTypeLayout(LibcXmmreg.typeLayout(), 16)).writeBytes(buf, offset + 160, value.xmm)
                    (PrimitiveArrayLayouts.uLongArray(12)).writeBytes(buf, offset + 416, value.private)
                }
            }
    }
}

public class UserRegsStruct(
    public var r15: CUlonglong = 0uL,
    public var r14: CUlonglong = 0uL,
    public var r13: CUlonglong = 0uL,
    public var r12: CUlonglong = 0uL,
    public var rbp: CUlonglong = 0uL,
    public var rbx: CUlonglong = 0uL,
    public var r11: CUlonglong = 0uL,
    public var r10: CUlonglong = 0uL,
    public var r9: CUlonglong = 0uL,
    public var r8: CUlonglong = 0uL,
    public var rax: CUlonglong = 0uL,
    public var rcx: CUlonglong = 0uL,
    public var rdx: CUlonglong = 0uL,
    public var rsi: CUlonglong = 0uL,
    public var rdi: CUlonglong = 0uL,
    public var origRax: CUlonglong = 0uL,
    public var rip: CUlonglong = 0uL,
    public var cs: CUlonglong = 0uL,
    public var eflags: CUlonglong = 0uL,
    public var rsp: CUlonglong = 0uL,
    public var ss: CUlonglong = 0uL,
    public var fsBase: CUlonglong = 0uL,
    public var gsBase: CUlonglong = 0uL,
    public var ds: CUlonglong = 0uL,
    public var es: CUlonglong = 0uL,
    public var fs: CUlonglong = 0uL,
    public var gs: CUlonglong = 0uL,
) {
    public fun clone(): UserRegsStruct = UserRegsStruct(r15, r14, r13, r12, rbp, rbx, r11, r10, r9, r8, rax, rcx, rdx, rsi, rdi, origRax, rip, cs, eflags, rsp, ss, fsBase, gsBase, ds, es, fs, gs)

    public companion object {
        public fun typeLayout(): TypeLayout<UserRegsStruct> =
            object : TypeLayout<UserRegsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 216L
                override fun readNative(addr: Long): UserRegsStruct =
                    UserRegsStruct(
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
                    )
                override fun writeNative(addr: Long, value: UserRegsStruct) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.r15)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.r14)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.r13)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.r12)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.rbp)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.rbx)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.r11)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.r10)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.r9)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.r8)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.rax)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.rcx)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.rdx)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.rsi)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.rdi)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.origRax)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.cs)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.eflags)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.rsp)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.ss)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.fsBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.gsBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.ds)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.es)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.fs)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.gs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserRegsStruct =
                    UserRegsStruct(
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
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserRegsStruct) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.r15)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.r14)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.r13)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.r12)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.rbp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.rbx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.r11)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.r10)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.r9)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.r8)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.rax)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.rcx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.rdx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.rsi)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.rdi)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.origRax)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.cs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.eflags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.rsp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.ss)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.fsBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.gsBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.ds)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.es)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.fs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.gs)
                }
            }
    }
}

public class User internal constructor(
    public var regs: UserRegsStruct,
    public var uFpvalid: CInt,
    public var i387: UserFpregsStruct,
    public var uTsize: CUlonglong,
    public var uDsize: CUlonglong,
    public var uSsize: CUlonglong,
    public var startCode: CUlonglong,
    public var startStack: CUlonglong,
    public var signal: CLonglong,
    public var reserved: CInt,
    uAr0CtorArg: MutPtr,
    uFpstateCtorArg: MutPtr,
    public var magic: CUlonglong,
    public var uComm: UByteArray,
    public var uDebugreg: ULongArray,
) {
    public var uAr0: MutPtr = uAr0CtorArg
        internal set

    public var uFpstate: MutPtr = uFpstateCtorArg
        internal set

    public constructor() : this(
        regs = UserRegsStruct(),
        uFpvalid = 0,
        i387 = UserFpregsStruct(),
        uTsize = 0uL,
        uDsize = 0uL,
        uSsize = 0uL,
        startCode = 0uL,
        startStack = 0uL,
        signal = 0L,
        reserved = 0,
        uAr0CtorArg = MutPtr(0L),
        uFpstateCtorArg = MutPtr(0L),
        magic = 0uL,
        uComm = UByteArray(32),
        uDebugreg = ULongArray(8),
    )

    public operator fun component1(): UserRegsStruct = this.regs

    public operator fun component2(): CInt = this.uFpvalid

    public operator fun component3(): UserFpregsStruct = this.i387

    public operator fun component4(): CUlonglong = this.uTsize

    public operator fun component5(): CUlonglong = this.uDsize

    public operator fun component6(): CUlonglong = this.uSsize

    public operator fun component7(): CUlonglong = this.startCode

    public operator fun component8(): CUlonglong = this.startStack

    public operator fun component9(): CLonglong = this.signal

    public operator fun component10(): CInt = this.reserved

    public operator fun component11(): MutPtr = this.uAr0

    public operator fun component12(): MutPtr = this.uFpstate

    public operator fun component13(): CUlonglong = this.magic

    public operator fun component14(): UByteArray = this.uComm

    public operator fun component15(): ULongArray = this.uDebugreg

    public fun copy(regs: UserRegsStruct = this.regs, uFpvalid: CInt = this.uFpvalid, i387: UserFpregsStruct = this.i387, uTsize: CUlonglong = this.uTsize, uDsize: CUlonglong = this.uDsize, uSsize: CUlonglong = this.uSsize, startCode: CUlonglong = this.startCode, startStack: CUlonglong = this.startStack, signal: CLonglong = this.signal, reserved: CInt = this.reserved, uAr0: MutPtr = this.uAr0, uFpstate: MutPtr = this.uFpstate, magic: CUlonglong = this.magic, uComm: UByteArray = this.uComm, uDebugreg: ULongArray = this.uDebugreg): User = User(regs = regs, uFpvalid = uFpvalid, i387 = i387, uTsize = uTsize, uDsize = uDsize, uSsize = uSsize, startCode = startCode, startStack = startStack, signal = signal, reserved = reserved, uAr0CtorArg = uAr0, uFpstateCtorArg = uFpstate, magic = magic, uComm = uComm, uDebugreg = uDebugreg)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (this.regs != other.regs) return false
        if (this.uFpvalid != other.uFpvalid) return false
        if (this.i387 != other.i387) return false
        if (this.uTsize != other.uTsize) return false
        if (this.uDsize != other.uDsize) return false
        if (this.uSsize != other.uSsize) return false
        if (this.startCode != other.startCode) return false
        if (this.startStack != other.startStack) return false
        if (this.signal != other.signal) return false
        if (this.reserved != other.reserved) return false
        if (this.uAr0 != other.uAr0) return false
        if (this.uFpstate != other.uFpstate) return false
        if (this.magic != other.magic) return false
        if (!this.uComm.contentEquals(other.uComm)) return false
        if (!this.uDebugreg.contentEquals(other.uDebugreg)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.regs.hashCode()
        result = 31 * result + this.uFpvalid.hashCode()
        result = 31 * result + this.i387.hashCode()
        result = 31 * result + this.uTsize.hashCode()
        result = 31 * result + this.uDsize.hashCode()
        result = 31 * result + this.uSsize.hashCode()
        result = 31 * result + this.startCode.hashCode()
        result = 31 * result + this.startStack.hashCode()
        result = 31 * result + this.signal.hashCode()
        result = 31 * result + this.reserved.hashCode()
        result = 31 * result + this.uAr0.hashCode()
        result = 31 * result + this.uFpstate.hashCode()
        result = 31 * result + this.magic.hashCode()
        result = 31 * result + this.uComm.contentHashCode()
        result = 31 * result + this.uDebugreg.contentHashCode()
        return result
    }

    public fun clone(): User = User(regs, uFpvalid, i387, uTsize, uDsize, uSsize, startCode, startStack, signal, reserved, uAr0, uFpstate, magic, uComm, uDebugreg)

    public companion object {
        public fun typeLayout(): TypeLayout<User> =
            object : TypeLayout<User> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 912L
                override fun readNative(addr: Long): User =
                    User(
                        (UserRegsStruct.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 216L),
                        (UserFpregsStruct.typeLayout()).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 736L),
                        (PrimitiveLayouts.U64).readNative(addr + 744L),
                        (PrimitiveLayouts.U64).readNative(addr + 752L),
                        (PrimitiveLayouts.U64).readNative(addr + 760L),
                        (PrimitiveLayouts.U64).readNative(addr + 768L),
                        (PrimitiveLayouts.I64).readNative(addr + 776L),
                        (PrimitiveLayouts.I32).readNative(addr + 784L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 792L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 800L),
                        (PrimitiveLayouts.U64).readNative(addr + 808L),
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr + 816L),
                        (PrimitiveArrayLayouts.uLongArray(8)).readNative(addr + 848L),
                    )
                override fun writeNative(addr: Long, value: User) {
                    (UserRegsStruct.typeLayout()).writeNative(addr, value.regs)
                    (PrimitiveLayouts.I32).writeNative(addr + 216L, value.uFpvalid)
                    (UserFpregsStruct.typeLayout()).writeNative(addr + 224L, value.i387)
                    (PrimitiveLayouts.U64).writeNative(addr + 736L, value.uTsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 744L, value.uDsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 752L, value.uSsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 760L, value.startCode)
                    (PrimitiveLayouts.U64).writeNative(addr + 768L, value.startStack)
                    (PrimitiveLayouts.I64).writeNative(addr + 776L, value.signal)
                    (PrimitiveLayouts.I32).writeNative(addr + 784L, value.reserved)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 792L, value.uAr0)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 800L, value.uFpstate)
                    (PrimitiveLayouts.U64).writeNative(addr + 808L, value.magic)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr + 816L, value.uComm)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeNative(addr + 848L, value.uDebugreg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): User =
                    User(
                        (UserRegsStruct.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 216),
                        (UserFpregsStruct.typeLayout()).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 736),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 744),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 752),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 760),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 768),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 776),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 784),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 792),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 800),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 808),
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset + 816),
                        (PrimitiveArrayLayouts.uLongArray(8)).readBytes(buf, offset + 848),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: User) {
                    (UserRegsStruct.typeLayout()).writeBytes(buf, offset, value.regs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 216, value.uFpvalid)
                    (UserFpregsStruct.typeLayout()).writeBytes(buf, offset + 224, value.i387)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 736, value.uTsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 744, value.uDsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 752, value.uSsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 760, value.startCode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 768, value.startStack)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 776, value.signal)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 784, value.reserved)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 792, value.uAr0)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 800, value.uFpstate)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 808, value.magic)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset + 816, value.uComm)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeBytes(buf, offset + 848, value.uDebugreg)
                }
            }

        public operator fun invoke(
            regs: UserRegsStruct = UserRegsStruct(),
            uFpvalid: CInt = 0,
            i387: UserFpregsStruct = UserFpregsStruct(),
            uTsize: CUlonglong = 0uL,
            uDsize: CUlonglong = 0uL,
            uSsize: CUlonglong = 0uL,
            startCode: CUlonglong = 0uL,
            startStack: CUlonglong = 0uL,
            signal: CLonglong = 0L,
            reserved: CInt = 0,
            uAr0: MutPtr = MutPtr(0L),
            uFpstate: MutPtr = MutPtr(0L),
            magic: CUlonglong = 0uL,
            uComm: UByteArray = UByteArray(32),
            uDebugreg: ULongArray = ULongArray(8),
        ): User = User(
            regs = regs,
            uFpvalid = uFpvalid,
            i387 = i387,
            uTsize = uTsize,
            uDsize = uDsize,
            uSsize = uSsize,
            startCode = startCode,
            startStack = startStack,
            signal = signal,
            reserved = reserved,
            uAr0CtorArg = uAr0,
            uFpstateCtorArg = uFpstate,
            magic = magic,
            uComm = uComm,
            uDebugreg = uDebugreg,
        )
    }
}

public class McontextT internal constructor(
    public var gregs: LongArray,
    fpregsCtorArg: MutPtr,
    public var private: ULongArray,
) {
    public var fpregs: MutPtr = fpregsCtorArg
        internal set

    public constructor() : this(
        gregs = LongArray(23),
        fpregsCtorArg = MutPtr(0L),
        private = ULongArray(8),
    )

    public operator fun component1(): LongArray = this.gregs

    public operator fun component2(): MutPtr = this.fpregs

    public operator fun component3(): ULongArray = this.private

    public fun copy(gregs: LongArray = this.gregs, fpregs: MutPtr = this.fpregs, private: ULongArray = this.private): McontextT = McontextT(gregs = gregs, fpregsCtorArg = fpregs, private = private)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is McontextT) return false
        if (!this.gregs.contentEquals(other.gregs)) return false
        if (this.fpregs != other.fpregs) return false
        if (!this.private.contentEquals(other.private)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.gregs.contentHashCode()
        result = 31 * result + this.fpregs.hashCode()
        result = 31 * result + this.private.contentHashCode()
        return result
    }

    public fun clone(): McontextT = McontextT(gregs, fpregs, private)

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 256L
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.longArray(23)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 184L),
                        (PrimitiveArrayLayouts.uLongArray(8)).readNative(addr + 192L),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveArrayLayouts.longArray(23)).writeNative(addr, value.gregs)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 184L, value.fpregs)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeNative(addr + 192L, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.longArray(23)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 184),
                        (PrimitiveArrayLayouts.uLongArray(8)).readBytes(buf, offset + 192),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveArrayLayouts.longArray(23)).writeBytes(buf, offset, value.gregs)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 184, value.fpregs)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeBytes(buf, offset + 192, value.private)
                }
            }

        public operator fun invoke(
            gregs: LongArray = LongArray(23),
            fpregs: MutPtr = MutPtr(0L),
            private: ULongArray = ULongArray(8),
        ): McontextT = McontextT(
            gregs = gregs,
            fpregsCtorArg = fpregs,
            private = private,
        )
    }
}

public data class IpcPerm(
    public var key: KeyT = 0,
    public var uid: UidT = 0,
    public var gid: GidT = 0,
    public var cuid: UidT = 0,
    public var cgid: GidT = 0,
    public var mode: CUshort = 0u,
    public var pad1: CUshort = 0u,
    public var seq: CUshort = 0u,
    public var pad2: CUshort = 0u,
    public var unused1: ULong = 0uL,
    public var unused2: ULong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<IpcPerm> =
            object : TypeLayout<IpcPerm> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 20L),
                        (PrimitiveLayouts.U16).readNative(addr + 22L),
                        (PrimitiveLayouts.U16).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 26L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.key)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.uid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.gid)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cuid)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.cgid)
                    (PrimitiveLayouts.U16).writeNative(addr + 20L, value.mode)
                    (PrimitiveLayouts.U16).writeNative(addr + 22L, value.pad1)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.seq)
                    (PrimitiveLayouts.U16).writeNative(addr + 26L, value.pad2)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.unused1)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.unused2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpcPerm =
                    IpcPerm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 22),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 26),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpcPerm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.key)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.uid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.gid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cuid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.cgid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 20, value.mode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 22, value.pad1)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.seq)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 26, value.pad2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.unused2)
                }
            }
    }
}

public data class ShmidDs(
    public var shmPerm: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.IpcPerm = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.IpcPerm(),
    public var shmSegsz: SizeT = 0uL,
    public var shmAtime: TimeT = 0L,
    public var shmDtime: TimeT = 0L,
    public var shmCtime: TimeT = 0L,
    public var shmCpid: PidT = 0,
    public var shmLpid: PidT = 0,
    public var shmNattch: ShmattT = 0uL,
    public var unused4: ULong = 0uL,
    public var unused5: ULong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ShmidDs> =
            object : TypeLayout<ShmidDs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): ShmidDs =
                    ShmidDs(
                        (IpcPerm.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I32).readNative(addr + 80L),
                        (PrimitiveLayouts.I32).readNative(addr + 84L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                    )
                override fun writeNative(addr: Long, value: ShmidDs) {
                    (IpcPerm.typeLayout()).writeNative(addr, value.shmPerm)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.shmSegsz)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.shmAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.shmDtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.shmCtime)
                    (PrimitiveLayouts.I32).writeNative(addr + 80L, value.shmCpid)
                    (PrimitiveLayouts.I32).writeNative(addr + 84L, value.shmLpid)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.shmNattch)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.unused4)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.unused5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ShmidDs =
                    ShmidDs(
                        (IpcPerm.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ShmidDs) {
                    (IpcPerm.typeLayout()).writeBytes(buf, offset, value.shmPerm)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.shmSegsz)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.shmAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.shmDtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.shmCtime)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 80, value.shmCpid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 84, value.shmLpid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.shmNattch)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.unused4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.unused5)
                }
            }
    }
}

public data class PtraceRseqConfiguration(
    public var rseqAbiPointer: U64 = 0uL,
    public var rseqAbiSize: U32 = 0u,
    public var signature: U32 = 0u,
    public var flags: U32 = 0u,
    public var pad: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PtraceRseqConfiguration> =
            object : TypeLayout<PtraceRseqConfiguration> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): PtraceRseqConfiguration =
                    PtraceRseqConfiguration(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: PtraceRseqConfiguration) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rseqAbiPointer)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.rseqAbiSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.signature)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtraceRseqConfiguration =
                    PtraceRseqConfiguration(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtraceRseqConfiguration) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rseqAbiPointer)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.rseqAbiSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.signature)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.pad)
                }
            }
    }
}

public data class CloneArgs(
    public var flags: CUlonglong = 0uL,
    public var pidfd: CUlonglong = 0uL,
    public var childTid: CUlonglong = 0uL,
    public var parentTid: CUlonglong = 0uL,
    public var exitSignal: CUlonglong = 0uL,
    public var stack: CUlonglong = 0uL,
    public var stackSize: CUlonglong = 0uL,
    public var tls: CUlonglong = 0uL,
    public var setTid: CUlonglong = 0uL,
    public var setTidSize: CUlonglong = 0uL,
    public var cgroup: CUlonglong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CloneArgs> =
            object : TypeLayout<CloneArgs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): CloneArgs =
                    CloneArgs(
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
                    )
                override fun writeNative(addr: Long, value: CloneArgs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.flags)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.pidfd)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.childTid)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.parentTid)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.exitSignal)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stack)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.stackSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.tls)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.setTid)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.setTidSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.cgroup)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CloneArgs =
                    CloneArgs(
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
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CloneArgs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.pidfd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.childTid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.parentTid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.exitSignal)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stack)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.stackSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.tls)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.setTid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.setTidSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.cgroup)
                }
            }
    }
}

public data class UserFpregsStruct(
    public var cwd: CUshort = 0u,
    public var swd: CUshort = 0u,
    public var ftw: CUshort = 0u,
    public var fop: CUshort = 0u,
    public var rip: CUlonglong = 0uL,
    public var rdp: CUlonglong = 0uL,
    public var mxcsr: CUint = 0u,
    public var mxcrMask: CUint = 0u,
    public var stSpace: UIntArray = UIntArray(32),
    public var xmmSpace: UIntArray = UIntArray(64),
    public var padding: UIntArray = UIntArray(24),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserFpregsStruct) return false
        if (this.cwd != other.cwd) return false
        if (this.swd != other.swd) return false
        if (this.ftw != other.ftw) return false
        if (this.fop != other.fop) return false
        if (this.rip != other.rip) return false
        if (this.rdp != other.rdp) return false
        if (this.mxcsr != other.mxcsr) return false
        if (this.mxcrMask != other.mxcrMask) return false
        if (!this.stSpace.contentEquals(other.stSpace)) return false
        if (!this.xmmSpace.contentEquals(other.xmmSpace)) return false
        if (!this.padding.contentEquals(other.padding)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cwd.hashCode()
        result = 31 * result + this.swd.hashCode()
        result = 31 * result + this.ftw.hashCode()
        result = 31 * result + this.fop.hashCode()
        result = 31 * result + this.rip.hashCode()
        result = 31 * result + this.rdp.hashCode()
        result = 31 * result + this.mxcsr.hashCode()
        result = 31 * result + this.mxcrMask.hashCode()
        result = 31 * result + this.stSpace.contentHashCode()
        result = 31 * result + this.xmmSpace.contentHashCode()
        result = 31 * result + this.padding.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UserFpregsStruct> =
            object : TypeLayout<UserFpregsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): UserFpregsStruct =
                    UserFpregsStruct(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveArrayLayouts.uIntArray(32)).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uIntArray(64)).readNative(addr + 160L),
                        (PrimitiveArrayLayouts.uIntArray(24)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: UserFpregsStruct) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.cwd)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.swd)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.ftw)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.fop)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rip)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.rdp)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxcsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxcrMask)
                    (PrimitiveArrayLayouts.uIntArray(32)).writeNative(addr + 32L, value.stSpace)
                    (PrimitiveArrayLayouts.uIntArray(64)).writeNative(addr + 160L, value.xmmSpace)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeNative(addr + 416L, value.padding)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserFpregsStruct =
                    UserFpregsStruct(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveArrayLayouts.uIntArray(32)).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uIntArray(64)).readBytes(buf, offset + 160),
                        (PrimitiveArrayLayouts.uIntArray(24)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserFpregsStruct) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.cwd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.swd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.ftw)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.fop)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.rdp)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxcsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxcrMask)
                    (PrimitiveArrayLayouts.uIntArray(32)).writeBytes(buf, offset + 32, value.stSpace)
                    (PrimitiveArrayLayouts.uIntArray(64)).writeBytes(buf, offset + 160, value.xmmSpace)
                    (PrimitiveArrayLayouts.uIntArray(24)).writeBytes(buf, offset + 416, value.padding)
                }
            }
    }
}

public class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.StackT,
    ucMcontextCtorArg: McontextT,
    public var ucSigmask: SigsetT,
    public var private: UByteArray,
    public var ssp: ULongArray,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public constructor() : this(
        ucFlags = 0uL,
        ucLinkCtorArg = MutPtr(0L),
        ucStack = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.StackT(),
        ucMcontextCtorArg = MutPtr(0L),
        ucSigmask = SigsetT(),
        private = UByteArray(512),
        ssp = ULongArray(4),
    )

    public operator fun component1(): CUlong = this.ucFlags

    public operator fun component2(): MutPtr = this.ucLink

    public operator fun component3(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.StackT = this.ucStack

    public operator fun component4(): McontextT = this.ucMcontext

    public operator fun component5(): SigsetT = this.ucSigmask

    public operator fun component6(): UByteArray = this.private

    public operator fun component7(): ULongArray = this.ssp

    public fun copy(ucFlags: CUlong = this.ucFlags, ucLink: MutPtr = this.ucLink, ucStack: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.StackT = this.ucStack, ucMcontext: McontextT = this.ucMcontext, ucSigmask: SigsetT = this.ucSigmask, private: UByteArray = this.private, ssp: ULongArray = this.ssp): UcontextT = UcontextT(ucFlags = ucFlags, ucLinkCtorArg = ucLink, ucStack = ucStack, ucMcontextCtorArg = ucMcontext, ucSigmask = ucSigmask, private = private, ssp = ssp)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UcontextT) return false
        if (this.ucFlags != other.ucFlags) return false
        if (this.ucLink != other.ucLink) return false
        if (this.ucStack != other.ucStack) return false
        if (this.ucMcontext != other.ucMcontext) return false
        if (this.ucSigmask != other.ucSigmask) return false
        if (!this.private.contentEquals(other.private)) return false
        if (!this.ssp.contentEquals(other.ssp)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ucFlags.hashCode()
        result = 31 * result + this.ucLink.hashCode()
        result = 31 * result + this.ucStack.hashCode()
        result = 31 * result + this.ucMcontext.hashCode()
        result = 31 * result + this.ucSigmask.hashCode()
        result = 31 * result + this.private.contentHashCode()
        result = 31 * result + this.ssp.contentHashCode()
        return result
    }

    public fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucMcontext, ucSigmask, private, ssp)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong = 0uL,
            ucLink: MutPtr = MutPtr(0L),
            ucStack: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.StackT = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.StackT(),
            ucMcontext: McontextT = MutPtr(0L),
            ucSigmask: SigsetT = SigsetT(),
            private: UByteArray = UByteArray(512),
            ssp: ULongArray = ULongArray(4),
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucMcontextCtorArg = ucMcontext,
            ucSigmask = ucSigmask,
            private = private,
            ssp = ssp,
        )
    }
}

public data class MaxAlignT(
    public var priv: DoubleArray = DoubleArray(4),
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
                        (PrimitiveArrayLayouts.doubleArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeNative(addr, value.priv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(4)).writeBytes(buf, offset, value.priv)
                }
            }
    }
}
