// port-lint: source windows_sys_arm32_shim.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssysarm32shim

import io.github.kotlinmania.rustlang.backtrace.windowssys.ImageFileMachine
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

public val ARM_MAX_BREAKPOINTS: Int = 8

public val ARM_MAX_WATCHPOINTS: Int = 1

public open class Neon128(
    public var low: ULong,
    public var high: Long,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Neon128> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Neon128> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Neon128 =
                    Neon128(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Neon128) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Neon128 =
                    Neon128(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Neon128) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
                }
            }
    }
}

public open class ContextFloatRegs(
    public var q: List<Neon128>? = null,
    public var d: ULongArray? = null,
    public var s: UIntArray? = null,
)

public open class Context(
    public var contextFlags: UInt,
    public var r0: UInt,
    public var r1: UInt,
    public var r2: UInt,
    public var r3: UInt,
    public var r4: UInt,
    public var r5: UInt,
    public var r6: UInt,
    public var r7: UInt,
    public var r8: UInt,
    public var r9: UInt,
    public var r10: UInt,
    public var r11: UInt,
    public var r12: UInt,
    // Control registers
    public var sp: UInt,
    public var lr: UInt,
    public var pc: UInt,
    public var cpsr: UInt,
    // Floating-point registers
    public var fpsrc: UInt,
    public var padding: UInt,
    public var u: ContextFloatRegs,
    // Debug registers
    public var bvr: UIntArray,
    public var bcr: UIntArray,
    public var wvr: UIntArray,
    public var wcr: UIntArray,
    public var padding2: UIntArray,
)

public val IMAGE_FILE_MACHINE_ARMNT: ImageFileMachine = 0x01c4u
