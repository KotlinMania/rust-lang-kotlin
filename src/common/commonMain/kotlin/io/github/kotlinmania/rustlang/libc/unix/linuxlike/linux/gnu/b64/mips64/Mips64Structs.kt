@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.mips64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.FsidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.KeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Blkcnt64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Ino64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.BlkcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.FsblkcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.InoT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ShmattT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT

public class Stat(
    public var stDev: CUlong = 0uL,
    public var stPad1: LongArray = LongArray(2),
    public var stIno: InoT = 0uL,
    public var stMode: ModeT = 0u,
    public var stNlink: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.mips64.NlinkT = 0uL,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: CUlong = 0uL,
    public var stPad2: ULongArray = ULongArray(1),
    public var stSize: OffT = 0L,
    public var stPad3: CLong = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.mips64.BlksizeT = 0L,
    public var stPad4: CLong = 0L,
    public var stBlocks: BlkcntT = 0L,
    public var stPad5: LongArray = LongArray(7),
) {
    public fun clone(): Stat = Stat(stDev, stPad1, stIno, stMode, stNlink, stUid, stGid, stRdev, stPad2, stSize, stPad3, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, stBlksize, stPad4, stBlocks, stPad5)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 216L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 52L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveArrayLayouts.uLongArray(1)).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveLayouts.I64).readNative(addr + 120L),
                        (PrimitiveLayouts.I64).readNative(addr + 128L),
                        (PrimitiveLayouts.I64).readNative(addr + 136L),
                        (PrimitiveLayouts.I64).readNative(addr + 144L),
                        (PrimitiveLayouts.I64).readNative(addr + 152L),
                        (PrimitiveArrayLayouts.longArray(7)).readNative(addr + 160L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr + 8L, value.stPad1)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.stIno)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.stMode)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stNlink)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 52L, value.stGid)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.stRdev)
                    (PrimitiveArrayLayouts.uLongArray(1)).writeNative(addr + 64L, value.stPad2)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stPad3)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 120L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 128L, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 136L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 144L, value.stPad4)
                    (PrimitiveLayouts.I64).writeNative(addr + 152L, value.stBlocks)
                    (PrimitiveArrayLayouts.longArray(7)).writeNative(addr + 160L, value.stPad5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveArrayLayouts.uLongArray(1)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 152),
                        (PrimitiveArrayLayouts.longArray(7)).readBytes(buf, offset + 160),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset + 8, value.stPad1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.stIno)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.stMode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stNlink)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 52, value.stGid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.stRdev)
                    (PrimitiveArrayLayouts.uLongArray(1)).writeBytes(buf, offset + 64, value.stPad2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stPad3)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 120, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 128, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 136, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 144, value.stPad4)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 152, value.stBlocks)
                    (PrimitiveArrayLayouts.longArray(7)).writeBytes(buf, offset + 160, value.stPad5)
                }
            }
    }
}

public data class Statfs(
    public var fType: CLong = 0L,
    public var fBsize: CLong = 0L,
    public var fFrsize: CLong = 0L,
    public var fBlocks: FsblkcntT = 0uL,
    public var fBfree: FsblkcntT = 0uL,
    public var fFiles: FsblkcntT = 0uL,
    public var fFfree: FsblkcntT = 0uL,
    public var fBavail: FsblkcntT = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: CLong = 0L,
    public var fSpare: LongArray = LongArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statfs) return false
        if (this.fType != other.fType) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fNamelen != other.fNamelen) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fType.hashCode()
        result = 31 * result + this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fNamelen.hashCode()
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

public class Stat64(
    public var stDev: CUlong = 0uL,
    public var stPad1: LongArray = LongArray(2),
    public var stIno: Ino64T = 0uL,
    public var stMode: ModeT = 0u,
    public var stNlink: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.mips64.NlinkT = 0uL,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: CUlong = 0uL,
    public var stPad2: LongArray = LongArray(2),
    public var stSize: Off64T = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.mips64.BlksizeT = 0L,
    public var stPad3: CLong = 0L,
    public var stBlocks: Blkcnt64T = 0L,
    public var stPad5: LongArray = LongArray(7),
) {
    public fun clone(): Stat64 = Stat64(stDev, stPad1, stIno, stMode, stNlink, stUid, stGid, stRdev, stPad2, stSize, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, stBlksize, stPad3, stBlocks, stPad5)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat64> =
            object : TypeLayout<Stat64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 216L
                override fun readNative(addr: Long): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                        (PrimitiveLayouts.I32).readNative(addr + 52L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I64).readNative(addr + 112L),
                        (PrimitiveLayouts.I64).readNative(addr + 120L),
                        (PrimitiveLayouts.I64).readNative(addr + 128L),
                        (PrimitiveLayouts.I64).readNative(addr + 136L),
                        (PrimitiveLayouts.I64).readNative(addr + 144L),
                        (PrimitiveLayouts.I64).readNative(addr + 152L),
                        (PrimitiveArrayLayouts.longArray(7)).readNative(addr + 160L),
                    )
                override fun writeNative(addr: Long, value: Stat64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr + 8L, value.stPad1)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.stIno)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.stMode)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stNlink)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.stUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 52L, value.stGid)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.stRdev)
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr + 64L, value.stPad2)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 112L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 120L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 128L, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 136L, value.stBlksize)
                    (PrimitiveLayouts.I64).writeNative(addr + 144L, value.stPad3)
                    (PrimitiveLayouts.I64).writeNative(addr + 152L, value.stBlocks)
                    (PrimitiveArrayLayouts.longArray(7)).writeNative(addr + 160L, value.stPad5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat64 =
                    Stat64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 152),
                        (PrimitiveArrayLayouts.longArray(7)).readBytes(buf, offset + 160),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset + 8, value.stPad1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.stIno)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.stMode)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stNlink)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.stUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 52, value.stGid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.stRdev)
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset + 64, value.stPad2)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 120, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 128, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 136, value.stBlksize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 144, value.stPad3)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 152, value.stBlocks)
                    (PrimitiveArrayLayouts.longArray(7)).writeBytes(buf, offset + 160, value.stPad5)
                }
            }
    }
}

public data class Statfs64(
    public var fType: CLong = 0L,
    public var fBsize: CLong = 0L,
    public var fFrsize: CLong = 0L,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: CLong = 0L,
    public var fFlags: CLong = 0L,
    public var fSpare: LongArray = LongArray(5),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statfs64) return false
        if (this.fType != other.fType) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fNamelen != other.fNamelen) return false
        if (this.fFlags != other.fFlags) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fType.hashCode()
        result = 31 * result + this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fNamelen.hashCode()
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

public class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssSize: SizeT,
    public var ssFlags: CInt,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public constructor() : this(
        ssSpCtorArg = MutPtr(0L),
        ssSize = 0uL,
        ssFlags = 0,
    )

    public operator fun component1(): MutPtr = this.ssSp

    public operator fun component2(): SizeT = this.ssSize

    public operator fun component3(): CInt = this.ssFlags

    public fun copy(ssSp: MutPtr = this.ssSp, ssSize: SizeT = this.ssSize, ssFlags: CInt = this.ssFlags): StackT = StackT(ssSpCtorArg = ssSp, ssSize = ssSize, ssFlags = ssFlags)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StackT) return false
        if (this.ssSp != other.ssSp) return false
        if (this.ssSize != other.ssSize) return false
        if (this.ssFlags != other.ssFlags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ssSp.hashCode()
        result = 31 * result + this.ssSize.hashCode()
        result = 31 * result + this.ssFlags.hashCode()
        return result
    }

    public fun clone(): StackT = StackT(ssSp, ssSize, ssFlags)

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
            ssSp: MutPtr = MutPtr(0L),
            ssSize: SizeT = 0uL,
            ssFlags: CInt = 0,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssSize = ssSize,
            ssFlags = ssFlags,
        )
    }
}

public data class IpcPerm(
    public var key: KeyT = 0,
    public var uid: UidT = 0,
    public var gid: GidT = 0,
    public var cuid: UidT = 0,
    public var cgid: GidT = 0,
    public var mode: CUint = 0u,
    public var seq: CUshort = 0u,
    public var pad1: CUshort = 0u,
    public var unused1: CUlong = 0uL,
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
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
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
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.mode)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.seq)
                    (PrimitiveLayouts.U16).writeNative(addr + 26L, value.pad1)
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
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
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
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.mode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.seq)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 26, value.pad1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.unused2)
                }
            }
    }
}

public data class ShmidDs(
    public var shmPerm: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.mips64.IpcPerm = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.mips64.IpcPerm(),
    public var shmSegsz: SizeT = 0uL,
    public var shmAtime: TimeT = 0L,
    public var shmDtime: TimeT = 0L,
    public var shmCtime: TimeT = 0L,
    public var shmCpid: PidT = 0,
    public var shmLpid: PidT = 0,
    public var shmNattch: ShmattT = 0uL,
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
