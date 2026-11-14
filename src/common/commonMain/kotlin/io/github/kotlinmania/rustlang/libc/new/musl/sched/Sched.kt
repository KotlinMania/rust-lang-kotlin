// port-lint: source new/musl/sched.rs
package io.github.kotlinmania.rustlang.libc.new.musl.sched

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.TimeT

public data class CAnonSchedParamReserved2(
    public var reserved1: TimeT = 0L,
    public var reserved2: CLong = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CAnonSchedParamReserved2> =
            object : TypeLayout<CAnonSchedParamReserved2> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonSchedParamReserved2 =
                    CAnonSchedParamReserved2(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonSchedParamReserved2) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.reserved1)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.reserved2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonSchedParamReserved2 =
                    CAnonSchedParamReserved2(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonSchedParamReserved2) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.reserved1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.reserved2)
                }
            }
    }
}

public data class SchedParam(
    public var schedPriority: CInt = 0,
    public var reserved1: CInt = 0,
    public var reserved2: LongArray = LongArray(4),
    public var reserved3: CInt = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SchedParam) return false
        if (this.schedPriority != other.schedPriority) return false
        if (this.reserved1 != other.reserved1) return false
        if (!this.reserved2.contentEquals(other.reserved2)) return false
        if (this.reserved3 != other.reserved3) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.schedPriority.hashCode()
        result = 31 * result + this.reserved1.hashCode()
        result = 31 * result + this.reserved2.contentHashCode()
        result = 31 * result + this.reserved3.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SchedParam> =
            object : TypeLayout<SchedParam> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): SchedParam =
                    SchedParam(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.longArray(4)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: SchedParam) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.schedPriority)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.reserved1)
                    (PrimitiveArrayLayouts.longArray(4)).writeNative(addr + 8L, value.reserved2)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.reserved3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SchedParam =
                    SchedParam(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.longArray(4)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SchedParam) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.schedPriority)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.reserved1)
                    (PrimitiveArrayLayouts.longArray(4)).writeBytes(buf, offset + 8, value.reserved2)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.reserved3)
                }
            }
    }
}
