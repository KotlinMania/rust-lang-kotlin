// port-lint: source new/apple/libpthread/pthread_/sched.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.sched

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*

public open class SchedParam(
    public var schedPriority: CInt,
    public var opaque: UByteArray,
) {
    public open fun clone(): SchedParam = SchedParam(schedPriority, opaque)

    public companion object {
        public fun typeLayout(): TypeLayout<SchedParam> =
            object : TypeLayout<SchedParam> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SchedParam =
                    SchedParam(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SchedParam) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.schedPriority)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 4L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SchedParam =
                    SchedParam(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SchedParam) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.schedPriority)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 4, value.opaque)
                }
            }
    }
}
