// port-lint: source os/haiku/raw.rs
package io.github.kotlinmania.rustlang.std.os.haiku.raw

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.std.os.raw.CLong
import io.github.kotlinmania.rustlang.std.os.unix.raw.GidT
import io.github.kotlinmania.rustlang.std.os.unix.raw.UidT

// Haiku-specific raw type definitions

// Use the direct definition of Int, instead of uintptrT like in libc
public typealias PthreadT = Int

public typealias BlkcntT = Long

public typealias BlksizeT = Int

public typealias DevT = Int

public typealias InoT = Long

public typealias ModeT = UInt

public typealias NlinkT = Int

public typealias OffT = Long

public typealias TimeT = Int

public open class Stat(
    public var stDev: DevT,
    public var stIno: InoT,
    public var stMode: ModeT,
    public var stNlink: NlinkT,
    public var stUid: UidT,
    public var stGid: GidT,
    public var stSize: OffT,
    public var stRdev: DevT,
    public var stBlksize: BlksizeT,
    public var stAtime: TimeT,
    public var stAtimeNsec: CLong,
    public var stMtime: TimeT,
    public var stMtimeNsec: CLong,
    public var stCtime: TimeT,
    public var stCtimeNsec: CLong,
    public var stCrtime: TimeT,
    public var stCrtimeNsec: CLong,
    public var stType: UInt,
    public var stBlocks: BlkcntT,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Stat> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 20L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 28L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 44L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 56L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 64L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 72L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 80L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 88L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 96L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 104L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 112L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 120L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 8L, value.stIno)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 16L, value.stMode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 20L, value.stNlink)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 24L, value.stUid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 28L, value.stGid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 32L, value.stSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 40L, value.stRdev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 44L, value.stBlksize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 48L, value.stAtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 56L, value.stAtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 64L, value.stMtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 72L, value.stMtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 80L, value.stCtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 88L, value.stCtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 96L, value.stCrtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 104L, value.stCrtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 112L, value.stType)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 120L, value.stBlocks)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 64),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 80),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 96),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 112),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 120),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.stIno)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stMode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.stNlink)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stUid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.stGid)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.stSize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.stRdev)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.stBlksize)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.stAtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stAtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 64, value.stMtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stMtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 80, value.stCtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stCtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 96, value.stCrtime)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stCrtimeNsec)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 112, value.stType)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 120, value.stBlocks)
                }
            }
    }
}
