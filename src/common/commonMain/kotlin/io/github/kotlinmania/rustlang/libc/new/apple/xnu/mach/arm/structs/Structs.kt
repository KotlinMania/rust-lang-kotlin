// port-lint: source new/apple/xnu/mach/arm/_structs.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.xnu.mach.arm.structs

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout

// Header: `arm / _structs.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/osfmk/mach/arm/_structs.h>

public data class DarwinArmExceptionState64(
    public var far: ULong = 0uL,
    public var esr: UInt = 0u,
    public var exception: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<DarwinArmExceptionState64> =
            object : TypeLayout<DarwinArmExceptionState64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): DarwinArmExceptionState64 =
                    DarwinArmExceptionState64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: DarwinArmExceptionState64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.far)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.esr)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.exception)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinArmExceptionState64 =
                    DarwinArmExceptionState64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinArmExceptionState64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.far)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.esr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.exception)
                }
            }
    }
}

public data class DarwinArmThreadState64(
    public var x: ULongArray = ULongArray(29),
    public var fp: ULong = 0uL,
    public var lr: ULong = 0uL,
    public var sp: ULong = 0uL,
    public var pc: ULong = 0uL,
    public var cpsr: UInt = 0u,
    public var pad: UInt = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DarwinArmThreadState64) return false
        if (!this.x.contentEquals(other.x)) return false
        if (this.fp != other.fp) return false
        if (this.lr != other.lr) return false
        if (this.sp != other.sp) return false
        if (this.pc != other.pc) return false
        if (this.cpsr != other.cpsr) return false
        if (this.pad != other.pad) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.x.contentHashCode()
        result = 31 * result + this.fp.hashCode()
        result = 31 * result + this.lr.hashCode()
        result = 31 * result + this.sp.hashCode()
        result = 31 * result + this.pc.hashCode()
        result = 31 * result + this.cpsr.hashCode()
        result = 31 * result + this.pad.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<DarwinArmThreadState64> =
            object : TypeLayout<DarwinArmThreadState64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 272L
                override fun readNative(addr: Long): DarwinArmThreadState64 =
                    DarwinArmThreadState64(
                        (PrimitiveArrayLayouts.uLongArray(29)).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 232L),
                        (PrimitiveLayouts.U64).readNative(addr + 240L),
                        (PrimitiveLayouts.U64).readNative(addr + 248L),
                        (PrimitiveLayouts.U64).readNative(addr + 256L),
                        (PrimitiveLayouts.U32).readNative(addr + 264L),
                        (PrimitiveLayouts.U32).readNative(addr + 268L),
                    )
                override fun writeNative(addr: Long, value: DarwinArmThreadState64) {
                    (PrimitiveArrayLayouts.uLongArray(29)).writeNative(addr, value.x)
                    (PrimitiveLayouts.U64).writeNative(addr + 232L, value.fp)
                    (PrimitiveLayouts.U64).writeNative(addr + 240L, value.lr)
                    (PrimitiveLayouts.U64).writeNative(addr + 248L, value.sp)
                    (PrimitiveLayouts.U64).writeNative(addr + 256L, value.pc)
                    (PrimitiveLayouts.U32).writeNative(addr + 264L, value.cpsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 268L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinArmThreadState64 =
                    DarwinArmThreadState64(
                        (PrimitiveArrayLayouts.uLongArray(29)).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 248),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 256),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 264),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 268),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinArmThreadState64) {
                    (PrimitiveArrayLayouts.uLongArray(29)).writeBytes(buf, offset, value.x)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.fp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.lr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 248, value.sp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 256, value.pc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 264, value.cpsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 268, value.pad)
                }
            }
    }
}

public data class DarwinArmNeonState64(
    public var v: List<U128> = List(32) { U128() },
    public var fpsr: UInt = 0u,
    public var fpcr: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<DarwinArmNeonState64> =
            object : TypeLayout<DarwinArmNeonState64> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 528L
                override fun readNative(addr: Long): DarwinArmNeonState64 =
                    DarwinArmNeonState64(
                        (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 512L),
                        (PrimitiveLayouts.U32).readNative(addr + 516L),
                    )
                override fun writeNative(addr: Long, value: DarwinArmNeonState64) {
                    (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).writeNative(addr, value.v)
                    (PrimitiveLayouts.U32).writeNative(addr + 512L, value.fpsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 516L, value.fpcr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DarwinArmNeonState64 =
                    DarwinArmNeonState64(
                        (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 512),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 516),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DarwinArmNeonState64) {
                    (fixedListTypeLayout(PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), 32)).writeBytes(buf, offset, value.v)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 512, value.fpsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 516, value.fpcr)
                }
            }
    }
}
