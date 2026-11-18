@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.loongarch64

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.FsidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.KeyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.DevT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Ino64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.ModeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.BlkcntT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.FsblkcntT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.FsfilcntT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.FswordT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.InoT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.ShmattT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.SigsetT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.TimeT

public open class Stat(
    public var stDev: DevT,
    public var stIno: InoT,
    public var stMode: ModeT,
    public var stNlink: NlinkT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var stRdev: DevT,
    public var pad1: DevT,
    public var stSize: OffT,
    public var stBlksize: BlksizeT,
    public var pad2: CInt,
    public var stBlocks: BlkcntT,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var unused: IntArray,
) {
    public open fun clone(): Stat = Stat(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

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
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr + 120L),
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
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr + 120L, value.unused)
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
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset + 120),
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
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset + 120, value.unused)
                }
            }
    }
}

public open class Stat64(
    public var stDev: DevT,
    public var stIno: Ino64T,
    public var stMode: ModeT,
    public var stNlink: NlinkT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var stRdev: DevT,
    public var pad1: DevT,
    public var stSize: Off64T,
    public var stBlksize: BlksizeT,
    public var pad2: CInt,
    public var stBlocks: BlkcntT,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var unused: IntArray,
) {
    public open fun clone(): Stat64 = Stat64(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

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
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr + 120L),
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
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr + 120L, value.unused)
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
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset + 120),
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
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset + 120, value.unused)
                }
            }
    }
}

public open class Statfs(
    public var fType: FswordT,
    public var fBsize: FswordT,
    public var fBlocks: FsblkcntT,
    public var fBfree: FsblkcntT,
    public var fBavail: FsblkcntT,
    public var fFiles: FsfilcntT,
    public var fFfree: FsfilcntT,
    public var fFsid: FsidT,
    public var fNamelen: FswordT,
    public var fFrsize: FswordT,
    public var fFlags: FswordT,
    public var fSpare: LongArray,
) {
    public open fun clone(): Statfs = Statfs(fType, fBsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fNamelen, fFrsize, fFlags, fSpare)
}

public open class Statfs64(
    public var fType: FswordT,
    public var fBsize: FswordT,
    public var fBlocks: ULong,
    public var fBfree: ULong,
    public var fBavail: ULong,
    public var fFiles: ULong,
    public var fFfree: ULong,
    public var fFsid: FsidT,
    public var fNamelen: FswordT,
    public var fFrsize: FswordT,
    public var fFlags: FswordT,
    public var fSpare: LongArray,
) {
    public open fun clone(): Statfs64 = Statfs64(fType, fBsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fNamelen, fFrsize, fFlags, fSpare)
}

public open class Flock(
    public var lType: CShort,
    public var lWhence: CShort,
    public var lStart: OffT,
    public var lLen: OffT,
    public var lPid: PidT,
) {
    public open fun clone(): Flock = Flock(lType, lWhence, lStart, lLen, lPid)

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

public open class Flock64(
    public var lType: CShort,
    public var lWhence: CShort,
    public var lStart: Off64T,
    public var lLen: Off64T,
    public var lPid: PidT,
) {
    public open fun clone(): Flock64 = Flock64(lType, lWhence, lStart, lLen, lPid)

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

public open class PthreadAttrT(
    public var size: ULongArray,
) {
    public open fun clone(): PthreadAttrT = PthreadAttrT(size)

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

public open class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssFlags: CInt,
    public var ssSize: SizeT,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public open fun clone(): StackT = StackT(ssSp, ssFlags, ssSize)

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
            ssSp: MutPtr,
            ssFlags: CInt,
            ssSize: SizeT,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssFlags = ssFlags,
            ssSize = ssSize,
        )
    }
}

public open class IpcPerm(
    public var key: KeyT,
    public var uid: UidT,
    public var gid: GidT,
    public var cuid: UidT,
    public var cgid: GidT,
    public var mode: CUint,
    public var seq: CUshort,
    public var pad2: CUshort,
    public var unused1: CUlong,
    public var unused2: CUlong,
) {
    public open fun clone(): IpcPerm = IpcPerm(key, uid, gid, cuid, cgid, mode, seq, pad2, unused1, unused2)

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
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 26, value.pad2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.unused2)
                }
            }
    }
}

public open class ShmidDs(
    public var shmPerm: IpcPerm,
    public var shmSegsz: SizeT,
    public var shmAtime: TimeT,
    public var shmDtime: TimeT,
    public var shmCtime: TimeT,
    public var shmCpid: PidT,
    public var shmLpid: PidT,
    public var shmNattch: ShmattT,
    public var unused4: CUlong,
    public var unused5: CUlong,
) {
    public open fun clone(): ShmidDs = ShmidDs(shmPerm, shmSegsz, shmAtime, shmDtime, shmCtime, shmCpid, shmLpid, shmNattch, unused4, unused5)

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

public open class UserRegsStruct(
    public var regs: ULongArray,
    public var origA0: ULong,
    public var csrEra: ULong,
    public var csrBadv: ULong,
    public var reserved: ULongArray,
) {
    public open fun clone(): UserRegsStruct = UserRegsStruct(regs, origA0, csrEra, csrBadv, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<UserRegsStruct> =
            object : TypeLayout<UserRegsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 360L
                override fun readNative(addr: Long): UserRegsStruct =
                    UserRegsStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U64).readNative(addr + 264L),
                        (PrimitiveLayouts.U64).readNative(addr + 272L),
                        (PrimitiveArrayLayouts.uLongArray(10)).readNative(addr + 280L),
                    )
                override fun writeNative(addr: Long, value: UserRegsStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.regs)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.origA0)
                    (PrimitiveLayouts.U64).writeNative(addr + 264L, value.csrEra)
                    (PrimitiveLayouts.U64).writeNative(addr + 272L, value.csrBadv)
                    (PrimitiveArrayLayouts.uLongArray(10)).writeNative(addr + 280L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserRegsStruct =
                    UserRegsStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 272),
                        (PrimitiveArrayLayouts.uLongArray(10)).readBytes(buf, offset + 280),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserRegsStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.regs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.origA0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 264, value.csrEra)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 272, value.csrBadv)
                    (PrimitiveArrayLayouts.uLongArray(10)).writeBytes(buf, offset + 280, value.reserved)
                }
            }
    }
}

public open class UserFpStruct(
    public var fpr: ULongArray,
    public var fcc: ULong,
    public var fcsr: UInt,
) {
    public open fun clone(): UserFpStruct = UserFpStruct(fpr, fcc, fcsr)

    public companion object {
        public fun typeLayout(): TypeLayout<UserFpStruct> =
            object : TypeLayout<UserFpStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): UserFpStruct =
                    UserFpStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                    )
                override fun writeNative(addr: Long, value: UserFpStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.fpr)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.fcc)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.fcsr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UserFpStruct =
                    UserFpStruct(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserFpStruct) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.fpr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.fcc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.fcsr)
                }
            }
    }
}

public open class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: StackT,
    public var ucSigmask: SigsetT,
    ucMcontextCtorArg: McontextT,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public open fun clone(): UcontextT = UcontextT(ucFlags, ucLink, ucStack, ucSigmask, ucMcontext)

    public companion object {
        public operator fun invoke(
            ucFlags: CUlong,
            ucLink: MutPtr,
            ucStack: StackT,
            ucSigmask: SigsetT,
            ucMcontext: McontextT,
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucSigmask = ucSigmask,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public open class McontextT(
    public var pc: CUlonglong,
    public var gregs: ULongArray,
    public var flags: CUint,
    public var extcontext: ULongArray,
) {
    public open fun clone(): McontextT = McontextT(pc, gregs, flags, extcontext)

    public companion object {
        public fun typeLayout(): TypeLayout<McontextT> =
            object : TypeLayout<McontextT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): McontextT =
                    McontextT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                        (PrimitiveArrayLayouts.uLongArray(0)).readNative(addr + 272L),
                    )
                override fun writeNative(addr: Long, value: McontextT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.pc)
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr + 8L, value.gregs)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.flags)
                    (PrimitiveArrayLayouts.uLongArray(0)).writeNative(addr + 272L, value.extcontext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): McontextT =
                    McontextT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                        (PrimitiveArrayLayouts.uLongArray(0)).readBytes(buf, offset + 272),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: McontextT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.pc)
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset + 8, value.gregs)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.flags)
                    (PrimitiveArrayLayouts.uLongArray(0)).writeBytes(buf, offset + 272, value.extcontext)
                }
            }
    }
}

public open class CloneArgs(
    public var flags: CUlonglong,
    public var pidfd: CUlonglong,
    public var childTid: CUlonglong,
    public var parentTid: CUlonglong,
    public var exitSignal: CUlonglong,
    public var stack: CUlonglong,
    public var stackSize: CUlonglong,
    public var tls: CUlonglong,
    public var setTid: CUlonglong,
    public var setTidSize: CUlonglong,
    public var cgroup: CUlonglong,
) {
    public open fun clone(): CloneArgs = CloneArgs(flags, pidfd, childTid, parentTid, exitSignal, stack, stackSize, tls, setTid, setTidSize, cgroup)

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

public open class MaxAlignT internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
) {
    public var priv: DoubleArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(priv, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MaxAlignT> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MaxAlignT(ptr, layout) }, { it.priv })

    public open fun clone(): MaxAlignT = MaxAlignT(priv, fieldLayout)

    public companion object {
        public operator fun invoke(priv: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()): MaxAlignT = MaxAlignT(priv, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()): MaxAlignT = MaxAlignT(fieldStorage, fieldLayout)
    }
}
