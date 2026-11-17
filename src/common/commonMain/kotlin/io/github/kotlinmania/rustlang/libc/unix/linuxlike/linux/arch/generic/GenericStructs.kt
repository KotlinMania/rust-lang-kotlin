@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.arch.generic

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.CcT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SpeedT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.TcflagT

// RLIMIT Constants
public open class Termios2(
    public var cIflag: TcflagT,
    public var cOflag: TcflagT,
    public var cCflag: TcflagT,
    public var cLflag: TcflagT,
    public var cLine: CcT,
    public var cCc: UByteArray,
    public var cIspeed: SpeedT,
    public var cOspeed: SpeedT,
) {
    public open fun clone(): Termios2 = Termios2(cIflag, cOflag, cCflag, cLflag, cLine, cCc, cIspeed, cOspeed)

    public companion object {
        public fun typeLayout(): TypeLayout<Termios2> =
            object : TypeLayout<Termios2> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 44L
                override fun readNative(addr: Long): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray(19)).readNative(addr + 17L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Termios2) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cOflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.cCflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 16L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(19)).writeNative(addr + 17L, value.cCc)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.cIspeed)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray(19)).readBytes(buf, offset + 17),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios2) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cOflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.cCflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 16, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(19)).writeBytes(buf, offset + 17, value.cCc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.cIspeed)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.cOspeed)
                }
            }
    }
}
