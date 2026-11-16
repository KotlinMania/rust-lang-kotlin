@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.aarch64

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*

public open class MallocZoneT(
    public var private: ULongArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): MallocZoneT = MallocZoneT(private, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<MallocZoneT> =
            object : TypeLayout<MallocZoneT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 144L
                override fun readNative(addr: Long): MallocZoneT =
                    MallocZoneT(
                        (PrimitiveArrayLayouts.uLongArray(18)).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 144L),
                    )
                override fun writeNative(addr: Long, value: MallocZoneT) {
                    (PrimitiveArrayLayouts.uLongArray(18)).writeNative(addr, value.private)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 144L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MallocZoneT =
                    MallocZoneT(
                        (PrimitiveArrayLayouts.uLongArray(18)).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 144),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MallocZoneT) {
                    (PrimitiveArrayLayouts.uLongArray(18)).writeBytes(buf, offset, value.private)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 144, value.nonExhaustive)
                }
            }

        public fun default(): MallocZoneT = MallocZoneT(private = Default.default(), nonExhaustive = Unit)
    }
}

public open class MaxAlignT(
    public var priv: Double,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): MaxAlignT = MaxAlignT(priv, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<MaxAlignT> =
            object : TypeLayout<MaxAlignT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveLayouts.F64).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveLayouts.F64).writeNative(addr, value.priv)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 8L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveLayouts.F64).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveLayouts.F64).writeBytes(buf, offset, value.priv)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 8, value.nonExhaustive)
                }
            }

        public fun default(): MaxAlignT = MaxAlignT(priv = Default.default(), nonExhaustive = Unit)
    }
}
