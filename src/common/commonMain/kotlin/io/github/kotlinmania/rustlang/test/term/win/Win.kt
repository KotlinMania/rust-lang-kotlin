// port-lint: source term/win.rs
package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.test.term.color.BLACK
import io.github.kotlinmania.rustlang.test.term.color.BLUE
import io.github.kotlinmania.rustlang.test.term.color.CYAN
import io.github.kotlinmania.rustlang.test.term.color.Color
import io.github.kotlinmania.rustlang.test.term.color.GREEN
import io.github.kotlinmania.rustlang.test.term.color.MAGENTA
import io.github.kotlinmania.rustlang.test.term.color.RED
import io.github.kotlinmania.rustlang.test.term.color.WHITE
import io.github.kotlinmania.rustlang.test.term.color.YELLOW
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

// Windows console handling

/**
 * A Terminal implementation that uses the Win32 Console API.
 */
public open class WinConsole<T>(
    public var buf: T,
    public var defForeground: Color,
    public var defBackground: Color,
    public var foreground: Color,
    public var background: Color,
) {
    public open fun apply() {
        var accum: Word = 0u
        accum = accum or colorToBits(this.foreground)
        accum = accum or ((colorToBits(this.background).toUInt() shl 4).toUShort())
        run {
            // You may be wondering, "but what about stderr?", and the answer
            // to that is that setting terminal attributes on the stdout
            // handle also sets them for stderr, since they go to the same
            // terminal! Admittedly, this is fragile, since stderr could be
            // redirected to a different console. This is good enough for
            // rustc though. See #13400.
            val out = GetStdHandle(STD_OUTPUT_HANDLE)
            SetConsoleTextAttribute(out, accum)
        }
    }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<WinConsole<T>> =
            object : TypeLayout<WinConsole<T>> {
                override val alignBytes: Long = listOf(4L, typeLayoutT.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, 4L)
                private val off2: Long = alignUpBytes((off1 + 4L), 4L)
                private val off3: Long = alignUpBytes((off2 + 4L), 4L)
                private val off4: Long = alignUpBytes((off3 + 4L), 4L)
                override val sizeBytes: Long = alignUpBytes((off4 + 4L), alignBytes)
                override fun readNative(addr: Long): WinConsole<T> =
                    WinConsole(
                        (typeLayoutT).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + off1),
                        (PrimitiveLayouts.U32).readNative(addr + off2),
                        (PrimitiveLayouts.U32).readNative(addr + off3),
                        (PrimitiveLayouts.U32).readNative(addr + off4),
                    )
                override fun writeNative(addr: Long, value: WinConsole<T>) {
                    (typeLayoutT).writeNative(addr, value.buf)
                    (PrimitiveLayouts.U32).writeNative(addr + off1, value.defForeground)
                    (PrimitiveLayouts.U32).writeNative(addr + off2, value.defBackground)
                    (PrimitiveLayouts.U32).writeNative(addr + off3, value.foreground)
                    (PrimitiveLayouts.U32).writeNative(addr + off4, value.background)
                }
                override fun readBytes(buf: ByteArray, offset: Int): WinConsole<T> =
                    WinConsole(
                        (typeLayoutT).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off4.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: WinConsole<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.buf)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off1.toInt(), value.defForeground)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off2.toInt(), value.defBackground)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off3.toInt(), value.foreground)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off4.toInt(), value.background)
                }
            }

        public fun <T> new(out: T): WinConsole<T> {
            val fg: Any?
            val bg: Any?
            run {
                val bufferInfo = MaybeUninit.uninit(ConsoleScreenBufferInfo.typeLayout())
                val handle = GetStdHandle(STD_OUTPUT_HANDLE)
                if (GetConsoleScreenBufferInfo(handle, bufferInfo.asMutPtr()) != 0) {
                    val bufferInfo1 = bufferInfo.assumeInit()
                    fg = bitsToColor(bufferInfo1.wAttributes)
                    bg = bitsToColor((bufferInfo1.wAttributes.toUInt() shr 4).toUShort())
                } else {
                    fg = WHITE
                    bg = BLACK
                }
            }
            return WinConsole(buf = out, defForeground = fg, defBackground = bg, foreground = fg, background = bg)
        }
    }
}

public typealias Word = UShort

public typealias Dword = UInt

public typealias Bool = Int

public typealias Handle = MutPtr

// https://docs.microsoft.com/en-us/windows/console/getstdhandle
public val STD_OUTPUT_HANDLE: Dword = (-11).toUInt()

public open class SmallRect(
    public var left: Short,
    public var top: Short,
    public var right: Short,
    public var bottom: Short,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SmallRect> =
            object : TypeLayout<SmallRect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SmallRect =
                    SmallRect(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: SmallRect) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.left)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.top)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.right)
                    (PrimitiveLayouts.I16).writeNative(addr + 6L, value.bottom)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SmallRect =
                    SmallRect(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SmallRect) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.left)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.top)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.right)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 6, value.bottom)
                }
            }
    }
}

public open class Coord(
    public var x: Short,
    public var y: Short,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Coord> =
            object : TypeLayout<Coord> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Coord =
                    Coord(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Coord) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.x)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.y)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Coord =
                    Coord(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Coord) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.x)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.y)
                }
            }
    }
}

public open class ConsoleScreenBufferInfo(
    public var dwSize: Coord,
    public var dwCursorPosition: Coord,
    public var wAttributes: Word,
    public var srWindow: SmallRect,
    public var dwMaximumWindowSize: Coord,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ConsoleScreenBufferInfo> =
            object : TypeLayout<ConsoleScreenBufferInfo> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 22L
                override fun readNative(addr: Long): ConsoleScreenBufferInfo =
                    ConsoleScreenBufferInfo(
                        (Coord.typeLayout()).readNative(addr),
                        (Coord.typeLayout()).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (SmallRect.typeLayout()).readNative(addr + 10L),
                        (Coord.typeLayout()).readNative(addr + 18L),
                    )
                override fun writeNative(addr: Long, value: ConsoleScreenBufferInfo) {
                    (Coord.typeLayout()).writeNative(addr, value.dwSize)
                    (Coord.typeLayout()).writeNative(addr + 4L, value.dwCursorPosition)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.wAttributes)
                    (SmallRect.typeLayout()).writeNative(addr + 10L, value.srWindow)
                    (Coord.typeLayout()).writeNative(addr + 18L, value.dwMaximumWindowSize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ConsoleScreenBufferInfo =
                    ConsoleScreenBufferInfo(
                        (Coord.typeLayout()).readBytes(buf, offset),
                        (Coord.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (SmallRect.typeLayout()).readBytes(buf, offset + 10),
                        (Coord.typeLayout()).readBytes(buf, offset + 18),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ConsoleScreenBufferInfo) {
                    (Coord.typeLayout()).writeBytes(buf, offset, value.dwSize)
                    (Coord.typeLayout()).writeBytes(buf, offset + 4, value.dwCursorPosition)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.wAttributes)
                    (SmallRect.typeLayout()).writeBytes(buf, offset + 10, value.srWindow)
                    (Coord.typeLayout()).writeBytes(buf, offset + 18, value.dwMaximumWindowSize)
                }
            }
    }
}

public expect fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool

public expect fun getStdHandle(which: Dword): Handle

public expect fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool

public fun colorToBits(color: Color): UShort {
    // magic numbers from mingw-w64's wincon.h
    val bits =
        when (color % 8u) {
            BLACK -> 0
            BLUE -> 0x1
            GREEN -> 0x2
            RED -> 0x4
            YELLOW -> 0x2 or 0x4
            MAGENTA -> 0x1 or 0x4
            CYAN -> 0x1 or 0x2
            WHITE -> 0x1 or 0x2 or 0x4
            else -> error("unreachable")
        }
    return if (color >= 8u) {
        bits or 0x8u
    } else {
        bits
    }
}

public fun bitsToColor(bits: UShort): Color {
    val color =
        when (bits and 0x7u.toUShort()) {
            0u.toUShort() -> BLACK
            0x1u.toUShort() -> BLUE
            0x2u.toUShort() -> GREEN
            0x4u.toUShort() -> RED
            0x6u.toUShort() -> YELLOW
            0x5u.toUShort() -> MAGENTA
            0x3u.toUShort() -> CYAN
            0x7u.toUShort() -> WHITE
            else -> error("unreachable")
        }
    return color or (UInt.from(bits) and 0x8u)
    // copy the hi-intensity bit
}

public fun <T : Write> WinConsole<T>.write(buf: List<UByte>): Result<Int> = this.buf.write(buf)

public fun <T : Write> WinConsole<T>.flush(): Result<Unit> = this.buf.flush()

public fun <T : Write> WinConsole<T>.fg(color: Color): Result<Boolean> {
    this.foreground = color
    this.apply()
    return RustResult.Ok(true)
}

public fun <T : Write> WinConsole<T>.reset(): Result<Boolean> {
    this.foreground = this.defForeground
    this.background = this.defBackground
    this.apply()
    return RustResult.Ok(true)
}
