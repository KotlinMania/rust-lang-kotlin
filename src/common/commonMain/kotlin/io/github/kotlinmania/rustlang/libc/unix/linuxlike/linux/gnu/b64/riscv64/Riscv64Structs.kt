@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.riscv64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
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
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.DevT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.Ino64T
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.BlkcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.FsblkcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.FsfilcntT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.InoT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ShmattT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT

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

public class Stat(
    public var stDev: DevT = 0uL,
    public var stIno: InoT = 0uL,
    public var stMode: ModeT = 0u,
    public var stNlink: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.NlinkT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0uL,
    public var pad1: DevT = 0uL,
    public var stSize: OffT = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.BlksizeT = 0,
    public var pad2: CInt = 0,
    public var stBlocks: BlkcntT = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused: IntArray = IntArray(2),
) {
    public fun clone(): Stat = Stat(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

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

public class Stat64(
    public var stDev: DevT = 0uL,
    public var stIno: Ino64T = 0uL,
    public var stMode: ModeT = 0u,
    public var stNlink: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.NlinkT = 0u,
    public var stUid: UidT = 0,
    public var stGid: GidT = 0,
    public var stRdev: DevT = 0uL,
    public var pad1: DevT = 0uL,
    public var stSize: Off64T = 0L,
    public var stBlksize: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.BlksizeT = 0,
    public var pad2: CInt = 0,
    public var stBlocks: BlkcntT = 0L,
    public var stAtime: TimeT = 0L,
    public var stAtimeNsec: CLong = 0L,
    public var stMtime: TimeT = 0L,
    public var stMtimeNsec: CLong = 0L,
    public var stCtime: TimeT = 0L,
    public var stCtimeNsec: CLong = 0L,
    public var unused: IntArray = IntArray(2),
) {
    public fun clone(): Stat64 = Stat64(stDev, stIno, stMode, stNlink, stUid, stGid, stRdev, pad1, stSize, stBlksize, pad2, stBlocks, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, unused)

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

public data class Statfs(
    public var fType: CLong = 0L,
    public var fBsize: CLong = 0L,
    public var fBlocks: FsblkcntT = 0uL,
    public var fBfree: FsblkcntT = 0uL,
    public var fBavail: FsblkcntT = 0uL,
    public var fFiles: FsfilcntT = 0uL,
    public var fFfree: FsfilcntT = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: CLong = 0L,
    public var fFrsize: CLong = 0L,
    public var fFlags: CLong = 0L,
    public var fSpare: LongArray = LongArray(4),
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

public data class Statfs64(
    public var fType: CLong = 0L,
    public var fBsize: CLong = 0L,
    public var fBlocks: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.Fsblkcnt64T = 0uL,
    public var fBfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.Fsblkcnt64T = 0uL,
    public var fBavail: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.Fsblkcnt64T = 0uL,
    public var fFiles: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.Fsfilcnt64T = 0uL,
    public var fFfree: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.Fsfilcnt64T = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: CLong = 0L,
    public var fFrsize: CLong = 0L,
    public var fFlags: CLong = 0L,
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
    public var shmPerm: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.IpcPerm = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.IpcPerm(),
    public var shmSegsz: SizeT = 0uL,
    public var shmAtime: TimeT = 0L,
    public var shmDtime: TimeT = 0L,
    public var shmCtime: TimeT = 0L,
    public var shmCpid: PidT = 0,
    public var shmLpid: PidT = 0,
    public var shmNattch: ShmattT = 0uL,
    public var unused5: CUlong = 0uL,
    public var unused6: CUlong = 0uL,
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
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.unused5)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.unused6)
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
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.unused5)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.unused6)
                }
            }
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

public class UserRegsStruct(
    public var pc: CUlong = 0uL,
    public var ra: CUlong = 0uL,
    public var sp: CUlong = 0uL,
    public var gp: CUlong = 0uL,
    public var tp: CUlong = 0uL,
    public var t0: CUlong = 0uL,
    public var t1: CUlong = 0uL,
    public var t2: CUlong = 0uL,
    public var s0: CUlong = 0uL,
    public var s1: CUlong = 0uL,
    public var a0: CUlong = 0uL,
    public var a1: CUlong = 0uL,
    public var a2: CUlong = 0uL,
    public var a3: CUlong = 0uL,
    public var a4: CUlong = 0uL,
    public var a5: CUlong = 0uL,
    public var a6: CUlong = 0uL,
    public var a7: CUlong = 0uL,
    public var s2: CUlong = 0uL,
    public var s3: CUlong = 0uL,
    public var s4: CUlong = 0uL,
    public var s5: CUlong = 0uL,
    public var s6: CUlong = 0uL,
    public var s7: CUlong = 0uL,
    public var s8: CUlong = 0uL,
    public var s9: CUlong = 0uL,
    public var s10: CUlong = 0uL,
    public var s11: CUlong = 0uL,
    public var t3: CUlong = 0uL,
    public var t4: CUlong = 0uL,
    public var t5: CUlong = 0uL,
    public var t6: CUlong = 0uL,
) {
    public fun clone(): UserRegsStruct = UserRegsStruct(pc, ra, sp, gp, tp, t0, t1, t2, s0, s1, a0, a1, a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, t3, t4, t5, t6)

    public companion object {
        public fun typeLayout(): TypeLayout<UserRegsStruct> =
            object : TypeLayout<UserRegsStruct> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 256L
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
                        (PrimitiveLayouts.U64).readNative(addr + 216L),
                        (PrimitiveLayouts.U64).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 232L),
                        (PrimitiveLayouts.U64).readNative(addr + 240L),
                        (PrimitiveLayouts.U64).readNative(addr + 248L),
                    )
                override fun writeNative(addr: Long, value: UserRegsStruct) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.pc)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ra)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.sp)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.gp)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.tp)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.t0)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.t1)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.t2)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.s0)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.s1)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.a0)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.a1)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.a2)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.a3)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.a4)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.a5)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.a6)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.a7)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.s2)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.s3)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.s4)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.s5)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.s6)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.s7)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.s8)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.s9)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.s10)
                    (PrimitiveLayouts.U64).writeNative(addr + 216L, value.s11)
                    (PrimitiveLayouts.U64).writeNative(addr + 224L, value.t3)
                    (PrimitiveLayouts.U64).writeNative(addr + 232L, value.t4)
                    (PrimitiveLayouts.U64).writeNative(addr + 240L, value.t5)
                    (PrimitiveLayouts.U64).writeNative(addr + 248L, value.t6)
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
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 248),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UserRegsStruct) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.pc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ra)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.sp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.gp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.tp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.t0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.t1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.t2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.s0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.s1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.a0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.a1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.a2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.a3)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.a4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.a5)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.a6)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.a7)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.s2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.s3)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.s4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.s5)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.s6)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.s7)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.s8)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.s9)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.s10)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.s11)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.t3)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.t4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.t5)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 248, value.t6)
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

public class UcontextT internal constructor(
    public var ucFlags: CUlong,
    ucLinkCtorArg: MutPtr,
    public var ucStack: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.StackT,
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
        ucStack = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.StackT(),
        ucSigmask = SigsetT(),
        ucMcontextCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CUlong = this.ucFlags

    public operator fun component2(): MutPtr = this.ucLink

    public operator fun component3(): io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.StackT = this.ucStack

    public operator fun component4(): SigsetT = this.ucSigmask

    public operator fun component5(): McontextT = this.ucMcontext

    public fun copy(ucFlags: CUlong = this.ucFlags, ucLink: MutPtr = this.ucLink, ucStack: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.StackT = this.ucStack, ucSigmask: SigsetT = this.ucSigmask, ucMcontext: McontextT = this.ucMcontext): UcontextT = UcontextT(ucFlags = ucFlags, ucLinkCtorArg = ucLink, ucStack = ucStack, ucSigmask = ucSigmask, ucMcontextCtorArg = ucMcontext)

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
            ucStack: io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.StackT = io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.riscv64.StackT(),
            ucSigmask: SigsetT = SigsetT(),
            ucMcontext: McontextT = MutPtr(0L),
        ): UcontextT = UcontextT(
            ucFlags = ucFlags,
            ucLinkCtorArg = ucLink,
            ucStack = ucStack,
            ucSigmask = ucSigmask,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public data class McontextT(
    public var gregs: ULongArray = ULongArray(32),
    public var fpregs: RiscvMcFpState = RiscvMcFpState(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is McontextT) return false
        if (!this.gregs.contentEquals(other.gregs)) return false
        if (this.fpregs != other.fpregs) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.gregs.contentHashCode()
        result = 31 * result + this.fpregs.hashCode()
        return result
    }
}

public data class RiscvMcFpState(
    public var f: RiscvMcFExtState = RiscvMcFExtState(),
    public var d: RiscvMcDExtState = RiscvMcDExtState(),
    public var q: RiscvMcQExtState = RiscvMcQExtState(),
) {
    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__riscv_mc_fp_state").finishNonExhaustive()
    }
}

public data class RiscvMcFExtState(
    public var f: UIntArray = UIntArray(32),
    public var fcsr: CUint = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RiscvMcFExtState) return false
        if (!this.f.contentEquals(other.f)) return false
        if (this.fcsr != other.fcsr) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.f.contentHashCode()
        result = 31 * result + this.fcsr.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvMcFExtState> =
            object : TypeLayout<RiscvMcFExtState> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 132L
                override fun readNative(addr: Long): RiscvMcFExtState =
                    RiscvMcFExtState(
                        (PrimitiveArrayLayouts.uIntArray(32)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: RiscvMcFExtState) {
                    (PrimitiveArrayLayouts.uIntArray(32)).writeNative(addr, value.f)
                    (PrimitiveLayouts.U32).writeNative(addr + 128L, value.fcsr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvMcFExtState =
                    RiscvMcFExtState(
                        (PrimitiveArrayLayouts.uIntArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvMcFExtState) {
                    (PrimitiveArrayLayouts.uIntArray(32)).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 128, value.fcsr)
                }
            }
    }
}

public data class RiscvMcDExtState(
    public var f: ULongArray = ULongArray(32),
    public var fcsr: CUint = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RiscvMcDExtState) return false
        if (!this.f.contentEquals(other.f)) return false
        if (this.fcsr != other.fcsr) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.f.contentHashCode()
        result = 31 * result + this.fcsr.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvMcDExtState> =
            object : TypeLayout<RiscvMcDExtState> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 264L
                override fun readNative(addr: Long): RiscvMcDExtState =
                    RiscvMcDExtState(
                        (PrimitiveArrayLayouts.uLongArray(32)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 256L),
                    )
                override fun writeNative(addr: Long, value: RiscvMcDExtState) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeNative(addr, value.f)
                    (PrimitiveLayouts.U32).writeNative(addr + 256L, value.fcsr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvMcDExtState =
                    RiscvMcDExtState(
                        (PrimitiveArrayLayouts.uLongArray(32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 256),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvMcDExtState) {
                    (PrimitiveArrayLayouts.uLongArray(32)).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 256, value.fcsr)
                }
            }
    }
}

public data class RiscvMcQExtState(
    public var f: ULongArray = ULongArray(64),
    public var fcsr: CUint = 0u,
    public var glibcReserved: UIntArray = UIntArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RiscvMcQExtState) return false
        if (!this.f.contentEquals(other.f)) return false
        if (this.fcsr != other.fcsr) return false
        if (!this.glibcReserved.contentEquals(other.glibcReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.f.contentHashCode()
        result = 31 * result + this.fcsr.hashCode()
        result = 31 * result + this.glibcReserved.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<RiscvMcQExtState> =
            object : TypeLayout<RiscvMcQExtState> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 528L
                override fun readNative(addr: Long): RiscvMcQExtState =
                    RiscvMcQExtState(
                        (PrimitiveArrayLayouts.uLongArray(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 512L),
                        (PrimitiveArrayLayouts.uIntArray(3)).readNative(addr + 516L),
                    )
                override fun writeNative(addr: Long, value: RiscvMcQExtState) {
                    (PrimitiveArrayLayouts.uLongArray(64)).writeNative(addr, value.f)
                    (PrimitiveLayouts.U32).writeNative(addr + 512L, value.fcsr)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeNative(addr + 516L, value.glibcReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RiscvMcQExtState =
                    RiscvMcQExtState(
                        (PrimitiveArrayLayouts.uLongArray(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 512),
                        (PrimitiveArrayLayouts.uIntArray(3)).readBytes(buf, offset + 516),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RiscvMcQExtState) {
                    (PrimitiveArrayLayouts.uLongArray(64)).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 512, value.fcsr)
                    (PrimitiveArrayLayouts.uIntArray(3)).writeBytes(buf, offset + 516, value.glibcReserved)
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
