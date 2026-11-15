@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.arch.mips

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.unix.CcT
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.bsd.apple.TcflagT

// RLIMIT Constants
public data class Termios2(
    public var cIflag: TcflagT = 0uL,
    public var cOflag: TcflagT = 0uL,
    public var cCflag: TcflagT = 0uL,
    public var cLflag: TcflagT = 0uL,
    public var cLine: CcT = 0u,
    public var cCc: UByteArray = UByteArray(23),
    public var cIspeed: SpeedT = 0uL,
    public var cOspeed: SpeedT = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Termios2) return false
        if (this.cIflag != other.cIflag) return false
        if (this.cOflag != other.cOflag) return false
        if (this.cCflag != other.cCflag) return false
        if (this.cLflag != other.cLflag) return false
        if (this.cLine != other.cLine) return false
        if (!this.cCc.contentEquals(other.cCc)) return false
        if (this.cIspeed != other.cIspeed) return false
        if (this.cOspeed != other.cOspeed) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cIflag.hashCode()
        result = 31 * result + this.cOflag.hashCode()
        result = 31 * result + this.cCflag.hashCode()
        result = 31 * result + this.cLflag.hashCode()
        result = 31 * result + this.cLine.hashCode()
        result = 31 * result + this.cCc.contentHashCode()
        result = 31 * result + this.cIspeed.hashCode()
        result = 31 * result + this.cOspeed.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Termios2> =
            object : TypeLayout<Termios2> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U8).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uByteArray(23)).readNative(addr + 33L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                    )
                override fun writeNative(addr: Long, value: Termios2) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.cOflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.cCflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 32L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(23)).writeNative(addr + 33L, value.cCc)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.cIspeed)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uByteArray(23)).readBytes(buf, offset + 33),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios2) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.cOflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.cCflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 32, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(23)).writeBytes(buf, offset + 33, value.cCc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.cIspeed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.cOspeed)
                }
            }
    }
}
