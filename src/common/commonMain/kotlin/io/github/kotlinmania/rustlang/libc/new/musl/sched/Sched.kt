// port-lint: source new/musl/sched.rs
package io.github.kotlinmania.rustlang.libc.new.musl.sched

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.TimeT

public open class CAnonSchedParamReserved2(
    public var reserved1: TimeT,
    public var reserved2: CLong,
) {
    public open fun clone(): CAnonSchedParamReserved2 = CAnonSchedParamReserved2(reserved1, reserved2)

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

public open class SchedParam(
    public var schedPriority: CInt,
    public var reserved1: CInt,
    public var reserved2: LongArray,
    public var reserved3: CInt,
) {
    public open fun clone(): SchedParam = SchedParam(schedPriority, reserved1, reserved2, reserved3)

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
