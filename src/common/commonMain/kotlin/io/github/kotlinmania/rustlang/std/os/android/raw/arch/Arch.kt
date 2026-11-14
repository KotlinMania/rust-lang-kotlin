// port-lint: source os/android/raw.rs
package io.github.kotlinmania.rustlang.std.os.android.raw.arch

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.std.os.raw.CLong
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.raw.CUlong
import io.github.kotlinmania.rustlang.std.os.unix.raw.GidT
import io.github.kotlinmania.rustlang.std.os.unix.raw.UidT

public typealias DevT = ULong

public typealias ModeT = CUint

public typealias BlkcntT = CUlong

public typealias BlksizeT = CUlong

public typealias InoT = CUlong

public typealias NlinkT = UInt

public typealias OffT = Long

public typealias TimeT = CLong

public open class Stat(
    public var stDev: DevT,
    public var stIno: InoT,
    public var stNlink: CUlong,
    public var stMode: CUint,
    public var stUid: UidT,
    public var stGid: GidT,
    public var pad0: CUint,
    public var stRdev: DevT,
    public var stSize: OffT,
    public var stBlksize: CLong,
    public var stBlocks: CLong,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var pad3: LongArray,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Stat> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 28L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 36L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 96L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 104L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 112L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.longArray(3)).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.stDev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 16L, value.stNlink)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 24L, value.stMode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 28L, value.stUid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 32L, value.stGid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 36L, value.pad0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 40L, value.stRdev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 48L, value.stSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 56L, value.stBlksize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 64L, value.stBlocks)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 72L, value.stAtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 80L, value.stAtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 88L, value.stMtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 96L, value.stMtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 112L, value.stCtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.longArray(3)).writeNative(addr + 120L, value.pad3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 112),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.longArray(3)).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.stDev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stNlink)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stMode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.stUid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.stGid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.pad0)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stRdev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stBlksize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stBlocks)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stAtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stAtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stMtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stMtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 112, value.stCtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts.longArray(3)).writeBytes(buf, offset + 120, value.pad3)
                }
            }
    }
}
