// port-lint: source windows_sys_arm32_shim.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssysarm32shim

import io.github.kotlinmania.rustlang.backtrace.windowssys.ImageFileMachine
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

public val ARM_MAX_BREAKPOINTS: Int = 8

public val ARM_MAX_WATCHPOINTS: Int = 1

public open class Neon128(
    public var low: ULong,
    public var high: Long,
) {
    public open fun clone(): Neon128 = Neon128(low, high)

    public companion object {
        public fun typeLayout(): TypeLayout<Neon128> =
            object : TypeLayout<Neon128> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Neon128 =
                    Neon128(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Neon128) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Neon128 =
                    Neon128(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Neon128) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
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
) {
    public open fun clone(): Context = Context(contextFlags, r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, sp, lr, pc, cpsr, fpsrc, padding, u, bvr, bcr, wvr, wcr, padding2)
}

public val IMAGE_FILE_MACHINE_ARMNT: ImageFileMachine = 0x01c4u.toUShort()
