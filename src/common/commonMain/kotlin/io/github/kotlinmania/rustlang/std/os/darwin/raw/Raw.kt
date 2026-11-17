// port-lint: source os/darwin/raw.rs
package io.github.kotlinmania.rustlang.std.os.darwin.raw

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.std.os.raw.CLong

// Apple-specific raw type definitions

public typealias BlkcntT = ULong

public typealias BlksizeT = ULong

public typealias DevT = ULong

public typealias InoT = ULong

public typealias ModeT = UInt

public typealias NlinkT = ULong

public typealias OffT = ULong

public typealias TimeT = Long

public typealias PthreadT = Int

public open class Stat(
    public var stDev: Int,
    public var stMode: UShort,
    public var stNlink: UShort,
    public var stIno: ULong,
    public var stUid: UInt,
    public var stGid: UInt,
    public var stRdev: Int,
    public var stAtime: CLong,
    public var stAtimeNsec: CLong,
    public var stMtime: CLong,
    public var stMtimeNsec: CLong,
    public var stCtime: CLong,
    public var stCtimeNsec: CLong,
    public var stBirthtime: CLong,
    public var stBirthtimeNsec: CLong,
    public var stSize: Long,
    public var stBlocks: Long,
    public var stBlksize: Int,
    public var stFlags: UInt,
    public var stGen: UInt,
    public var stLspare: Int,
    public var stQspare: LongArray,
) {
    public open fun clone(): Stat = Stat(stDev, stMode, stNlink, stIno, stUid, stGid, stRdev, stAtime, stAtimeNsec, stMtime, stMtimeNsec, stCtime, stCtimeNsec, stBirthtime, stBirthtimeNsec, stSize, stBlocks, stBlksize, stFlags, stGen, stLspare, stQspare)

    public companion object {
        public fun typeLayout(): TypeLayout<Stat> =
            object : TypeLayout<Stat> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I64).readNative(addr + 72L),
                        (PrimitiveLayouts.I64).readNative(addr + 80L),
                        (PrimitiveLayouts.I64).readNative(addr + 88L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveLayouts.I64).readNative(addr + 104L),
                        (PrimitiveLayouts.I32).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 116L),
                        (PrimitiveLayouts.U32).readNative(addr + 120L),
                        (PrimitiveLayouts.I32).readNative(addr + 124L),
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: Stat) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.stDev)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.stMode)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.stNlink)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stIno)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stUid)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stGid)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.stRdev)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.stAtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.stMtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.stCtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 72L, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 80L, value.stBirthtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 88L, value.stBirthtimeNsec)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.stSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 104L, value.stBlocks)
                    (PrimitiveLayouts.I32).writeNative(addr + 112L, value.stBlksize)
                    (PrimitiveLayouts.U32).writeNative(addr + 116L, value.stFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 120L, value.stGen)
                    (PrimitiveLayouts.I32).writeNative(addr + 124L, value.stLspare)
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr + 128L, value.stQspare)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stat =
                    Stat(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 116),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 124),
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stat) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.stDev)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.stMode)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.stNlink)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stIno)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stUid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stGid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.stRdev)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.stAtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.stAtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.stMtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.stMtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.stCtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 72, value.stCtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 80, value.stBirthtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 88, value.stBirthtimeNsec)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.stSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 104, value.stBlocks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 112, value.stBlksize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 116, value.stFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 120, value.stGen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 124, value.stLspare)
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset + 128, value.stQspare)
                }
            }
    }
}
