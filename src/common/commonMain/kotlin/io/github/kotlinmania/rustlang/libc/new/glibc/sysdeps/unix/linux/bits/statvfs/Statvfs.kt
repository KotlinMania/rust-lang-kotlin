// port-lint: source new/glibc/sysdeps/unix/linux/bits/statvfs.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.bits.statvfs

import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.FsblkcntT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.FsfilcntT

// Header: `sysdeps / unix / sysv / linux / bits / statvfs.h`

public open class Statvfs(
    public var fBsize: CUlong,
    public var fFrsize: CUlong,
    public var fBlocks: FsblkcntT,
    public var fBfree: FsblkcntT,
    public var fBavail: FsblkcntT,
    public var fFiles: FsfilcntT,
    public var fFfree: FsfilcntT,
    public var fFavail: FsfilcntT,
    public var fFsid: CUlong,
    public var fFlag: CUlong,
    public var fNamemax: CUlong,
    public var fType: CUint,
    public var fSpare: IntArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): Statvfs = Statvfs(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax, fType, fSpare, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs> =
            object : TypeLayout<Statvfs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Statvfs =
                    Statvfs(
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
                override fun writeNative(addr: Long, value: Statvfs) {
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
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs =
                    Statvfs(
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
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs) {
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

        public fun default(): Statvfs = Statvfs(fBsize = 0uL, fFrsize = 0uL, fBlocks = 0u, fBfree = 0u, fBavail = 0u, fFiles = 0u, fFfree = 0u, fFavail = 0u, fFsid = 0uL, fUnused = Statvfs.default(), fFlag = 0uL, fNamemax = 0uL, fType = 0u, fSpare = Default.default(), nonExhaustive = Unit)
    }
}

public open class Statvfs64(
    public var fBsize: CUlong,
    public var fFrsize: CUlong,
    public var fBlocks: ULong,
    public var fBfree: ULong,
    public var fBavail: ULong,
    public var fFiles: ULong,
    public var fFfree: ULong,
    public var fFavail: ULong,
    public var fFsid: CUlong,
    public var fFlag: CUlong,
    public var fNamemax: CUlong,
    public var fType: CUint,
    public var fSpare: IntArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): Statvfs64 = Statvfs64(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax, fType, fSpare, nonExhaustive)

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

        public fun default(): Statvfs64 = Statvfs64(fBsize = 0uL, fFrsize = 0uL, fBlocks = 0uL, fBfree = 0uL, fBavail = 0uL, fFiles = 0uL, fFfree = 0uL, fFavail = 0uL, fFsid = 0uL, fUnused = Statvfs64.default(), fFlag = 0uL, fNamemax = 0uL, fType = 0u, fSpare = Default.default(), nonExhaustive = Unit)
    }
}
