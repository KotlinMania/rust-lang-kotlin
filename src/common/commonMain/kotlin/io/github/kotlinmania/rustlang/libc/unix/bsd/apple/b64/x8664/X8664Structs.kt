@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.x8664

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*

public data class MallocIntrospectionT(
    public var private: ULongArray = ULongArray(16),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MallocIntrospectionT) return false
        if (!this.private.contentEquals(other.private)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.private.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<MallocIntrospectionT> =
            object : TypeLayout<MallocIntrospectionT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): MallocIntrospectionT =
                    MallocIntrospectionT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: MallocIntrospectionT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeNative(addr, value.private)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 128L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MallocIntrospectionT =
                    MallocIntrospectionT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MallocIntrospectionT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeBytes(buf, offset, value.private)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 128, value.nonExhaustive)
                }
            }
    }
}

public data class MaxAlignT(
    public var priv: DoubleArray = DoubleArray(2),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaxAlignT) return false
        if (!this.priv.contentEquals(other.priv)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.priv.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<MaxAlignT> =
            object : TypeLayout<MaxAlignT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.priv)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 16L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.priv)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 16, value.nonExhaustive)
                }
            }
    }
}
