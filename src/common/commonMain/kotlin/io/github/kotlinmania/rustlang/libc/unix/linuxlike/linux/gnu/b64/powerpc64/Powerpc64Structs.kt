@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.powerpc64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUlonglong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.Blkcnt64T
import io.github.kotlinmania.rustlang.libc.BlkcntT
import io.github.kotlinmania.rustlang.libc.DevT
import io.github.kotlinmania.rustlang.libc.FsblkcntT
import io.github.kotlinmania.rustlang.libc.FsfilcntT
import io.github.kotlinmania.rustlang.libc.FsidT
import io.github.kotlinmania.rustlang.libc.GidT
import io.github.kotlinmania.rustlang.libc.Ino64T
import io.github.kotlinmania.rustlang.libc.InoT
import io.github.kotlinmania.rustlang.libc.KeyT
import io.github.kotlinmania.rustlang.libc.ModeT
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.ShmattT
import io.github.kotlinmania.rustlang.libc.SigsetT
import io.github.kotlinmania.rustlang.libc.TimeT
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.FswordT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT

public data class Statfs(
    public var fType: FswordT = 0L,
    public var fBsize: FswordT = 0L,
    public var fBlocks: FsblkcntT = 0u,
    public var fBfree: FsblkcntT = 0u,
    public var fBavail: FsblkcntT = 0u,
    public var fFiles: FsfilcntT = 0u,
    public var fFfree: FsfilcntT = 0u,
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

public class Stat(
    public var stDev: DevT = 0,
    public var stIno: InoT = 0uL,
    public var stNlink: io.github.kotlinmania.rustlang.libc.NlinkT = 0u,
    public var stMode: ModeT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var pad0: CInt = 0,
    public var stRdev: DevT = 0,
    public var stSize: OffT = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.libc.BlksizeT = 0,
    public var stBlocks: BlkcntT = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
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
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
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
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.stRdev)
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
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
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
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.stRdev)
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
    public var stDev: DevT = 0,
    public var stIno: Ino64T = 0uL,
    public var stNlink: io.github.kotlinmania.rustlang.libc.NlinkT = 0u,
    public var stMode: ModeT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var pad0: CInt = 0,
    public var stRdev: DevT = 0,
    public var stSize: Off64T = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.libc.BlksizeT = 0,
    public var stBlocks: Blkcnt64T = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
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
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
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
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.stMode)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.stRdev)
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
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
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
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.stMode)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.stRdev)
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

public data class IpcPerm(
    public var key: KeyT = 0,
    public var uid: UidT = 0,
    public var gid: GidT = 0,
    public var cuid: UidT = 0,
    public var cgid: GidT = 0,
    public var mode: ModeT = 0u,
    public var seq: UInt = 0u,
    public var pad1: UInt = 0u,
    public var unused1: ULong = 0uL,
    public var unused2: CUlong = 0uL,
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
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
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
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.seq)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.pad1)
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
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
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
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.seq)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.pad1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.unused2)
                }
            }
    }
}

public data class ShmidDs(
    public var shmPerm: io.github.kotlinmania.rustlang.libc.IpcPerm = io.github.kotlinmania.rustlang.libc.IpcPerm(),
    public var shmAtime: TimeT = 0L,
    public var shmDtime: TimeT = 0L,
    public var shmCtime: TimeT = 0L,
    public var shmSegsz: SizeT = 0uL,
    public var shmCpid: PidT = 0,
    public var shmLpid: PidT = 0,
    public var shmNattch: ShmattT = 0u,
    public var unused4: CUlong = 0uL,
    public var unused5: CUlong = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ShmidDs> =
            object : TypeLayout<ShmidDs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): ShmidDs =
                    ShmidDs(
                        (IpcPerm.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.I32).readNative(addr + 80L),
                        (PrimitiveLayouts.I32).readNative(addr + 84L),
                        (PrimitiveLayouts.U16).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                    )
                override fun writeNative(addr: Long, value: ShmidDs) {
                    (IpcPerm.typeLayout()).writeNative(addr, value.shmPerm)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.shmAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.shmDtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.shmCtime)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.shmSegsz)
                    (PrimitiveLayouts.I32).writeNative(addr + 80L, value.shmCpid)
                    (PrimitiveLayouts.I32).writeNative(addr + 84L, value.shmLpid)
                    (PrimitiveLayouts.U16).writeNative(addr + 88L, value.shmNattch)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.unused4)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.unused5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ShmidDs =
                    ShmidDs(
                        (IpcPerm.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ShmidDs) {
                    (IpcPerm.typeLayout()).writeBytes(buf, offset, value.shmPerm)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.shmAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.shmDtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.shmCtime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.shmSegsz)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 80, value.shmCpid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 84, value.shmLpid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 88, value.shmNattch)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.unused4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.unused5)
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

public data class MaxAlignT(
    public var priv: LongArray = LongArray(4),
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
                        (PrimitiveArrayLayouts.longArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.longArray(4)).writeNative(addr, value.priv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.longArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.longArray(4)).writeBytes(buf, offset, value.priv)
                }
            }
    }
}

public class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: io.github.kotlinmania.rustlang.libc.StackT,
    public var ucSigmask: SigsetT,
    ucMcontextCtorArg: McontextT,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public constructor() : this(
        ucFlags = 0uL,
        ucLinkCtorArg = MutPtr(0L),
        ucStack = io.github.kotlinmania.rustlang.libc.StackT(),
        ucSigmask = SigsetT(),
        ucMcontextCtorArg = McontextT(),
    )

    public operator fun component1(): CUlong = this.ucFlags

    public operator fun component2(): MutPtr = this.ucLink

    public operator fun component3(): io.github.kotlinmania.rustlang.libc.StackT = this.ucStack

    public operator fun component4(): SigsetT = this.ucSigmask

    public operator fun component5(): McontextT = this.ucMcontext

    public fun copy(ucFlags: CUlong = this.ucFlags, ucLink: MutPtr = this.ucLink, ucStack: io.github.kotlinmania.rustlang.libc.StackT = this.ucStack, ucSigmask: SigsetT = this.ucSigmask, ucMcontext: McontextT = this.ucMcontext): UcontextT = UcontextT(ucFlags = ucFlags, ucLinkCtorArg = ucLink, ucStack = ucStack, ucSigmask = ucSigmask, ucMcontextCtorArg = ucMcontext)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UcontextT) return false
        if (this.ucFlags != other.ucFlags) return false
        if (this.ucLink != other.ucLink) return false
        if (this.ucStack != other.ucStack) return false
        if (this.ucSigmask != other.ucSigmask) return false
        if (this.ucMcontext != other.ucMcontext) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ucFlags.hashCode()
        result = 31 * result + this.ucLink.hashCode()
        result = 31 * result + this.ucStack.hashCode()
        result = 31 * result + this.ucSigmask.hashCode()
        result = 31 * result + this.ucMcontext.hashCode()
        return result
    }

    public fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucSigmask, ucMcontext)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong = 0uL,
            ucLink: MutPtr = MutPtr(0L),
            ucStack: io.github.kotlinmania.rustlang.libc.StackT = io.github.kotlinmania.rustlang.libc.StackT(),
            ucSigmask: SigsetT = SigsetT(),
            ucMcontext: McontextT = McontextT(),
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucSigmask = ucSigmask,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public data class PtRegs(
    public var gpr: ULongArray = ULongArray(32),
    public var nip: CUlong = 0uL,
    public var msr: CUlong = 0uL,
    public var origGpr3: CUlong = 0uL,
    public var ctr: CUlong = 0uL,
    public var link: CUlong = 0uL,
    public var xer: CUlong = 0uL,
    public var ccr: CUlong = 0uL,
    public var softe: CUlong = 0uL,
    public var trap: CUlong = 0uL,
    public var dar: CUlong = 0uL,
    public var dsisr: CUlong = 0uL,
    public var result: CUlong = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PtRegs) return false
        if (!this.gpr.contentEquals(other.gpr)) return false
        if (this.nip != other.nip) return false
        if (this.msr != other.msr) return false
        if (this.origGpr3 != other.origGpr3) return false
        if (this.ctr != other.ctr) return false
        if (this.link != other.link) return false
        if (this.xer != other.xer) return false
        if (this.ccr != other.ccr) return false
        if (this.softe != other.softe) return false
        if (this.trap != other.trap) return false
        if (this.dar != other.dar) return false
        if (this.dsisr != other.dsisr) return false
        if (this.result != other.result) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.gpr.contentHashCode()
        result = 31 * result + this.nip.hashCode()
        result = 31 * result + this.msr.hashCode()
        result = 31 * result + this.origGpr3.hashCode()
        result = 31 * result + this.ctr.hashCode()
        result = 31 * result + this.link.hashCode()
        result = 31 * result + this.xer.hashCode()
        result = 31 * result + this.ccr.hashCode()
        result = 31 * result + this.softe.hashCode()
        result = 31 * result + this.trap.hashCode()
        result = 31 * result + this.dar.hashCode()
        result = 31 * result + this.dsisr.hashCode()
        result = 31 * result + this.result.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PtRegs> =
            object : TypeLayout<PtRegs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 352L
                override fun readNative(addr: Long): PtRegs =
                    PtRegs(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                        (PrimitiveLayouts.U64).readNative(addr + 272L),
                        (PrimitiveLayouts.U64).readNative(addr + 280L),
                        (PrimitiveLayouts.U64).readNative(addr + 288L),
                        (PrimitiveLayouts.U64).readNative(addr + 296L),
                        (PrimitiveLayouts.U64).readNative(addr + 304L),
                        (PrimitiveLayouts.U64).readNative(addr + 312L),
                        (PrimitiveLayouts.U64).readNative(addr + 320L),
                        (PrimitiveLayouts.U64).readNative(addr + 328L),
                        (PrimitiveLayouts.U64).readNative(addr + 336L),
                        (PrimitiveLayouts.U64).readNative(addr + 344L),
                    )
                override fun writeNative(addr: Long, value: PtRegs) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.gpr)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.nip)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.msr)
                    (PrimitiveLayouts.U64).writeNative(addr + 272L, value.origGpr3)
                    (PrimitiveLayouts.U64).writeNative(addr + 280L, value.ctr)
                    (PrimitiveLayouts.U64).writeNative(addr + 288L, value.link)
                    (PrimitiveLayouts.U64).writeNative(addr + 296L, value.xer)
                    (PrimitiveLayouts.U64).writeNative(addr + 304L, value.ccr)
                    (PrimitiveLayouts.U64).writeNative(addr + 312L, value.softe)
                    (PrimitiveLayouts.U64).writeNative(addr + 320L, value.trap)
                    (PrimitiveLayouts.U64).writeNative(addr + 328L, value.dar)
                    (PrimitiveLayouts.U64).writeNative(addr + 336L, value.dsisr)
                    (PrimitiveLayouts.U64).writeNative(addr + 344L, value.result)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtRegs =
                    PtRegs(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 272),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 280),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 288),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 296),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 304),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 312),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 320),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 328),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 336),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 344),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtRegs) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.gpr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.nip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.msr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 272, value.origGpr3)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 280, value.ctr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 288, value.link)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 296, value.xer)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 304, value.ccr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 312, value.softe)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 320, value.trap)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 328, value.dar)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 336, value.dsisr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 344, value.result)
                }
            }
    }
}

public class McontextT internal constructor(
    public var glibcReserved: ULongArray,
    public var signal: CInt,
    public var pad0: CInt,
    public var handler: CUlong,
    public var oldmask: CUlong,
    regsCtorArg: MutPtr,
    public var gpRegs: io.github.kotlinmania.rustlang.libc.GregsetT,
    public var fpRegs: io.github.kotlinmania.rustlang.libc.FpregsetT,
    vRegsCtorArg: MutPtr,
    public var vmxReserve: LongArray,
) {
    public var regs: MutPtr = regsCtorArg
        internal set

    public var vRegs: MutPtr = vRegsCtorArg
        internal set

    public constructor() : this(
        glibcReserved = ULongArray(4),
        signal = 0,
        pad0 = 0,
        handler = 0uL,
        oldmask = 0uL,
        regsCtorArg = MutPtr(0L),
        gpRegs = ULongArray(NGREG),
        fpRegs = ULongArray(NFPREG),
        vRegsCtorArg = MutPtr(0L),
        vmxReserve = LongArray(NVRREG + NVRREG + 1),
    )

    public operator fun component1(): ULongArray = this.glibcReserved

    public operator fun component2(): CInt = this.signal

    public operator fun component3(): CInt = this.pad0

    public operator fun component4(): CUlong = this.handler

    public operator fun component5(): CUlong = this.oldmask

    public operator fun component6(): MutPtr = this.regs

    public operator fun component7(): io.github.kotlinmania.rustlang.libc.GregsetT = this.gpRegs

    public operator fun component8(): io.github.kotlinmania.rustlang.libc.FpregsetT = this.fpRegs

    public operator fun component9(): MutPtr = this.vRegs

    public operator fun component10(): LongArray = this.vmxReserve

    public fun copy(glibcReserved: ULongArray = this.glibcReserved, signal: CInt = this.signal, pad0: CInt = this.pad0, handler: CUlong = this.handler, oldmask: CUlong = this.oldmask, regs: MutPtr = this.regs, gpRegs: io.github.kotlinmania.rustlang.libc.GregsetT = this.gpRegs, fpRegs: io.github.kotlinmania.rustlang.libc.FpregsetT = this.fpRegs, vRegs: MutPtr = this.vRegs, vmxReserve: LongArray = this.vmxReserve): McontextT = McontextT(glibcReserved = glibcReserved, signal = signal, pad0 = pad0, handler = handler, oldmask = oldmask, regsCtorArg = regs, gpRegs = gpRegs, fpRegs = fpRegs, vRegsCtorArg = vRegs, vmxReserve = vmxReserve)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is McontextT) return false
        if (!this.glibcReserved.contentEquals(other.glibcReserved)) return false
        if (this.signal != other.signal) return false
        if (this.pad0 != other.pad0) return false
        if (this.handler != other.handler) return false
        if (this.oldmask != other.oldmask) return false
        if (this.regs != other.regs) return false
        if (!this.gpRegs.contentEquals(other.gpRegs)) return false
        if (!this.fpRegs.contentEquals(other.fpRegs)) return false
        if (this.vRegs != other.vRegs) return false
        if (!this.vmxReserve.contentEquals(other.vmxReserve)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.glibcReserved.contentHashCode()
        result = 31 * result + this.signal.hashCode()
        result = 31 * result + this.pad0.hashCode()
        result = 31 * result + this.handler.hashCode()
        result = 31 * result + this.oldmask.hashCode()
        result = 31 * result + this.regs.hashCode()
        result = 31 * result + this.gpRegs.contentHashCode()
        result = 31 * result + this.fpRegs.contentHashCode()
        result = 31 * result + this.vRegs.hashCode()
        result = 31 * result + this.vmxReserve.contentHashCode()
        return result
    }

    public fun clone(): McontextT = McontextT(glibcReserved, signal, pad0, handler, oldmask, regs, gpRegs, fpRegs, vRegs, vmxReserve)

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 8L
                private val off7: Long = alignUpBytes((64L + ((NGREG).toLong() * (PrimitiveLayouts.U64).strideBytes)), 8L)
                private val off8: Long = alignUpBytes((off7 + ((NFPREG).toLong() * (PrimitiveLayouts.U64).strideBytes)), 8L)
                private val off9: Long = alignUpBytes((off8 + 8L), 8L)
                override val sizeBytes: Long = alignUpBytes((off9 + ((NVRREG + NVRREG + 1).toLong() * (PrimitiveLayouts.I64).strideBytes)), 8L)
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveArrayLayouts.uLongArray((NGREG).toInt())).readNative(addr + 64L),
                        (PrimitiveArrayLayouts.uLongArray((NFPREG).toInt())).readNative(addr + off7),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + off8),
                        (PrimitiveArrayLayouts.longArray((NVRREG + NVRREG + 1).toInt())).readNative(addr + off9),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr, value.glibcReserved)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.signal)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.pad0)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.handler)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.oldmask)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.regs)
                    (PrimitiveArrayLayouts.uLongArray((NGREG).toInt())).writeNative(addr + 64L, value.gpRegs)
                    (PrimitiveArrayLayouts.uLongArray((NFPREG).toInt())).writeNative(addr + off7, value.fpRegs)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + off8, value.vRegs)
                    (PrimitiveArrayLayouts.longArray((NVRREG + NVRREG + 1).toInt())).writeNative(addr + off9, value.vmxReserve)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveArrayLayouts.uLongArray((NGREG).toInt())).readBytes(buf, offset + 64),
                        (PrimitiveArrayLayouts.uLongArray((NFPREG).toInt())).readBytes(buf, offset + off7.toInt()),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + off8.toInt()),
                        (PrimitiveArrayLayouts.longArray((NVRREG + NVRREG + 1).toInt())).readBytes(buf, offset + off9.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset, value.glibcReserved)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.signal)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.pad0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.handler)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.oldmask)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.regs)
                    (PrimitiveArrayLayouts.uLongArray((NGREG).toInt())).writeBytes(buf, offset + 64, value.gpRegs)
                    (PrimitiveArrayLayouts.uLongArray((NFPREG).toInt())).writeBytes(buf, offset + off7.toInt(), value.fpRegs)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + off8.toInt(), value.vRegs)
                    (PrimitiveArrayLayouts.longArray((NVRREG + NVRREG + 1).toInt())).writeBytes(buf, offset + off9.toInt(), value.vmxReserve)
                }
            }

        public operator fun invoke(
            glibcReserved: ULongArray = ULongArray(4),
            signal: CInt = 0,
            pad0: CInt = 0,
            handler: CUlong = 0uL,
            oldmask: CUlong = 0uL,
            regs: MutPtr = MutPtr(0L),
            gpRegs: io.github.kotlinmania.rustlang.libc.GregsetT = ULongArray(NGREG),
            fpRegs: io.github.kotlinmania.rustlang.libc.FpregsetT = ULongArray(NFPREG),
            vRegs: MutPtr = MutPtr(0L),
            vmxReserve: LongArray = LongArray(NVRREG + NVRREG + 1),
        ): McontextT = McontextT(
            glibcReserved = glibcReserved,
            signal = signal,
            pad0 = pad0,
            handler = handler,
            oldmask = oldmask,
            regsCtorArg = regs,
            gpRegs = gpRegs,
            fpRegs = fpRegs,
            vRegsCtorArg = vRegs,
            vmxReserve = vmxReserve,
        )
    }
}

public data class VrregsetT(
    public var vrregs: List<UIntArray> = List(32) { UIntArray(0) },
    public var vscr: VscrT = VscrT(),
    public var vrsave: CUint = 0u,
    public var pad: UIntArray = UIntArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VrregsetT) return false
        if (this.vrregs != other.vrregs) return false
        if (this.vscr != other.vscr) return false
        if (this.vrsave != other.vrsave) return false
        if (!this.pad.contentEquals(other.pad)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.vrregs.hashCode()
        result = 31 * result + this.vscr.hashCode()
        result = 31 * result + this.vrsave.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<VrregsetT> =
            object : TypeLayout<VrregsetT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 544L
                override fun readNative(addr: Long): VrregsetT =
                    VrregsetT(
                        (fixedListTypeLayout(PrimitiveArrayLayouts.uIntArray(4), 32)).readNative(addr),
                        (VscrT.typeLayout()).readNative(addr + 512L),
                        (PrimitiveLayouts.U32).readNative(addr + 528L),
                        (PrimitiveArrayLayouts.uIntArray(3)).readNative(addr + 532L),
                    )
                override fun writeNative(addr: Long, value: VrregsetT) {
                    (fixedListTypeLayout(PrimitiveArrayLayouts.uIntArray(4), 32)).writeNative(addr, value.vrregs)
                    (VscrT.typeLayout()).writeNative(addr + 512L, value.vscr)
                    (PrimitiveLayouts.U32).writeNative(addr + 528L, value.vrsave)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeNative(addr + 532L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VrregsetT =
                    VrregsetT(
                        (fixedListTypeLayout(PrimitiveArrayLayouts.uIntArray(4), 32)).readBytes(buf, offset),
                        (VscrT.typeLayout()).readBytes(buf, offset + 512),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 528),
                        (PrimitiveArrayLayouts.uIntArray(3)).readBytes(buf, offset + 532),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VrregsetT) {
                    (fixedListTypeLayout(PrimitiveArrayLayouts.uIntArray(4), 32)).writeBytes(buf, offset, value.vrregs)
                    (VscrT.typeLayout()).writeBytes(buf, offset + 512, value.vscr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 528, value.vrsave)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeBytes(buf, offset + 532, value.pad)
                }
            }
    }
}

public data class VscrT(
    public var vscrWord: CUint = 0u,
    public var pad: UIntArray = UIntArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VscrT) return false
        if (this.vscrWord != other.vscrWord) return false
        if (!this.pad.contentEquals(other.pad)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.vscrWord.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<VscrT> =
            object : TypeLayout<VscrT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VscrT =
                    VscrT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.uIntArray(3)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: VscrT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.vscrWord)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeNative(addr + 4L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VscrT =
                    VscrT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uIntArray(3)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VscrT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.vscrWord)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeBytes(buf, offset + 4, value.pad)
                }
            }
    }
}
