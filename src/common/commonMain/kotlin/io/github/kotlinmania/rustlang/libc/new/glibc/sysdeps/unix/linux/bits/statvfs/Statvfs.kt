// port-lint: source new/glibc/sysdeps/unix/linux/bits/statvfs.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.bits.statvfs

import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.FsblkcntT
import io.github.kotlinmania.rustlang.libc.FsfilcntT

// Header: `sysdeps / unix / sysv / linux / bits / statvfs.h`

public data class Statvfs(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: FsblkcntT = 0u,
    public var fBfree: FsblkcntT = 0u,
    public var fBavail: FsblkcntT = 0u,
    public var fFiles: FsfilcntT = 0u,
    public var fFfree: FsfilcntT = 0u,
    public var fFavail: FsfilcntT = 0u,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
    public var fType: CUint = 0u,
    public var fSpare: IntArray = IntArray(5),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statvfs) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFavail != other.fFavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fFlag != other.fFlag) return false
        if (this.fNamemax != other.fNamemax) return false
        if (this.fType != other.fType) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fFlag.hashCode()
        result = 31 * result + this.fNamemax.hashCode()
        result = 31 * result + this.fType.hashCode()
        result = 31 * result + this.fSpare.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs> =
            object : TypeLayout<Statvfs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                        (PrimitiveArrayLayouts.intArray(5)).readNative(addr + 68L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fBsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.fFrsize)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.fBlocks)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.fBfree)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.fBavail)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.fFiles)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.fFfree)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.fFavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fFsid)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fFlag)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.fNamemax)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.fType)
                    (PrimitiveArrayLayouts.intArray(5)).writeNative(addr + 68L, value.fSpare)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 88L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (PrimitiveArrayLayouts.intArray(5)).readBytes(buf, offset + 68),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fBsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.fFrsize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.fBlocks)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.fBfree)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.fBavail)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.fFiles)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.fFfree)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.fFavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fFsid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fFlag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.fNamemax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.fType)
                    (PrimitiveArrayLayouts.intArray(5)).writeBytes(buf, offset + 68, value.fSpare)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 88, value.nonExhaustive)
                }
            }
    }
}

public data class Statvfs64(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fFavail: ULong = 0uL,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
    public var fType: CUint = 0u,
    public var fSpare: IntArray = IntArray(5),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statvfs64) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFavail != other.fFavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fFlag != other.fFlag) return false
        if (this.fNamemax != other.fNamemax) return false
        if (this.fType != other.fType) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fFlag.hashCode()
        result = 31 * result + this.fNamemax.hashCode()
        result = 31 * result + this.fType.hashCode()
        result = 31 * result + this.fSpare.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs64> =
            object : TypeLayout<Statvfs64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Statvfs64 =
                    Statvfs64(
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
                        (PrimitiveLayouts.U32).readNative(addr + 88L),
                        (PrimitiveArrayLayouts.intArray(5)).readNative(addr + 92L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 112L),
                    )
                override fun writeNative(addr: Long, value: Statvfs64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fBsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.fFrsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.fBlocks)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.fBfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.fBavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fFiles)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fFfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.fFavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.fFsid)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.fFlag)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.fNamemax)
                    (PrimitiveLayouts.U32).writeNative(addr + 88L, value.fType)
                    (PrimitiveArrayLayouts.intArray(5)).writeNative(addr + 92L, value.fSpare)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 112L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs64 =
                    Statvfs64(
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
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 88),
                        (PrimitiveArrayLayouts.intArray(5)).readBytes(buf, offset + 92),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 112),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fBsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.fFrsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.fBlocks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.fBfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.fBavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fFiles)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fFfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.fFavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.fFsid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.fFlag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.fNamemax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 88, value.fType)
                    (PrimitiveArrayLayouts.intArray(5)).writeBytes(buf, offset + 92, value.fSpare)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 112, value.nonExhaustive)
                }
            }
    }
}
