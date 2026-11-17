package io.github.kotlinmania.rustlang.core.str

import io.github.kotlinmania.rustlang.core.char.isAsciiWhitespace
import io.github.kotlinmania.rustlang.core.char.methods.EscapeDebugExtArgs
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ops.function.Fn
import io.github.kotlinmania.rustlang.core.ops.function.FnMut
import io.github.kotlinmania.rustlang.core.ops.function.FnOnce
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.iter.EscapeDebug
import io.github.kotlinmania.rustlang.core.str.iter.EscapeDefault
import io.github.kotlinmania.rustlang.core.str.iter.EscapeUnicode

/**
 * A nameable, cloneable fn type
 */
public open class LinesMap : Fn<String, Any>, FnMut<String, Any>, FnOnce<String, String> {
    public open fun clone(): LinesMap = LinesMap()

    public override fun call(tupleArg0: String): String {
        val line = tupleArg0
        return run {
            val line1 = line.takeIf { it.endsWith('\n') }?.removeSuffix('\n') ?: return line
            val line2 = line1.takeIf { it.endsWith('\r') }?.removeSuffix('\r') ?: return line1
            line2
        }
    }

    public override fun callMut(tupleArg0: String): String {
        val line = tupleArg0
        return this.call(line)
    }

    public override fun callOnce(tupleArg0: String): String {
        val line = tupleArg0
        return this.call(line)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<LinesMap> =
            object : TypeLayout<LinesMap> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): LinesMap =
                    LinesMap()
                override fun writeNative(addr: Long, value: LinesMap) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): LinesMap =
                    LinesMap()
                override fun writeBytes(buf: ByteArray, offset: Int, value: LinesMap) {
                }
            }
    }
}

public open class CharEscapeDebug : Fn<Char, Any>, FnMut<Char, Any>, FnOnce<Char, EscapeDebug> {
    public open fun clone(): CharEscapeDebug = CharEscapeDebug()

    public override fun call(tupleArg0: Char): EscapeDebug {
        val c = tupleArg0
        return c.escapeDebugExt(EscapeDebugExtArgs.ESCAPE_ALL)
    }

    public override fun callMut(tupleArg0: Char): EscapeDebug {
        val c = tupleArg0
        return this.call(c)
    }

    public override fun callOnce(tupleArg0: Char): EscapeDebug {
        val c = tupleArg0
        return this.call(c)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<CharEscapeDebug> =
            object : TypeLayout<CharEscapeDebug> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): CharEscapeDebug =
                    CharEscapeDebug()
                override fun writeNative(addr: Long, value: CharEscapeDebug) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): CharEscapeDebug =
                    CharEscapeDebug()
                override fun writeBytes(buf: ByteArray, offset: Int, value: CharEscapeDebug) {
                }
            }
    }
}

public open class CharEscapeUnicode : Fn<Char, Any>, FnMut<Char, Any>, FnOnce<Char, EscapeUnicode> {
    public open fun clone(): CharEscapeUnicode = CharEscapeUnicode()

    public override fun call(tupleArg0: Char): EscapeUnicode {
        val c = tupleArg0
        return c.escapeUnicode()
    }

    public override fun callMut(tupleArg0: Char): EscapeUnicode {
        val c = tupleArg0
        return this.call(c)
    }

    public override fun callOnce(tupleArg0: Char): EscapeUnicode {
        val c = tupleArg0
        return this.call(c)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<CharEscapeUnicode> =
            object : TypeLayout<CharEscapeUnicode> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): CharEscapeUnicode =
                    CharEscapeUnicode()
                override fun writeNative(addr: Long, value: CharEscapeUnicode) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): CharEscapeUnicode =
                    CharEscapeUnicode()
                override fun writeBytes(buf: ByteArray, offset: Int, value: CharEscapeUnicode) {
                }
            }
    }
}

public open class CharEscapeDefault : Fn<Char, Any>, FnMut<Char, Any>, FnOnce<Char, EscapeDefault> {
    public open fun clone(): CharEscapeDefault = CharEscapeDefault()

    public override fun call(tupleArg0: Char): EscapeDefault {
        val c = tupleArg0
        return c.escapeDefault()
    }

    public override fun callMut(tupleArg0: Char): EscapeDefault {
        val c = tupleArg0
        return this.call(c)
    }

    public override fun callOnce(tupleArg0: Char): EscapeDefault {
        val c = tupleArg0
        return this.call(c)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<CharEscapeDefault> =
            object : TypeLayout<CharEscapeDefault> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): CharEscapeDefault =
                    CharEscapeDefault()
                override fun writeNative(addr: Long, value: CharEscapeDefault) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): CharEscapeDefault =
                    CharEscapeDefault()
                override fun writeBytes(buf: ByteArray, offset: Int, value: CharEscapeDefault) {
                }
            }
    }
}

public open class IsWhitespace : Fn<Char, Any>, FnMut<Char, Any>, FnOnce<Char, Boolean> {
    public open fun clone(): IsWhitespace = IsWhitespace()

    public override fun call(tupleArg0: Char): Boolean {
        val c = tupleArg0
        return c.isWhitespace()
    }

    public override fun callMut(tupleArg0: Char): Boolean {
        val c = tupleArg0
        return this.call(c)
    }

    public override fun callOnce(tupleArg0: Char): Boolean {
        val c = tupleArg0
        return this.call(c)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<IsWhitespace> =
            object : TypeLayout<IsWhitespace> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): IsWhitespace =
                    IsWhitespace()
                override fun writeNative(addr: Long, value: IsWhitespace) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): IsWhitespace =
                    IsWhitespace()
                override fun writeBytes(buf: ByteArray, offset: Int, value: IsWhitespace) {
                }
            }
    }
}

public open class IsAsciiWhitespace : Fn<UByte, Any>, FnMut<UByte, Any>, FnOnce<UByte, Boolean> {
    public open fun clone(): IsAsciiWhitespace = IsAsciiWhitespace()

    public override fun call(tupleArg0: UByte): Boolean {
        val byte = tupleArg0
        return byte.isAsciiWhitespace()
    }

    public override fun callMut(tupleArg0: UByte): Boolean {
        val byte = tupleArg0
        return this.call(byte)
    }

    public override fun callOnce(tupleArg0: UByte): Boolean {
        val byte = tupleArg0
        return this.call(byte)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<IsAsciiWhitespace> =
            object : TypeLayout<IsAsciiWhitespace> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): IsAsciiWhitespace =
                    IsAsciiWhitespace()
                override fun writeNative(addr: Long, value: IsAsciiWhitespace) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): IsAsciiWhitespace =
                    IsAsciiWhitespace()
                override fun writeBytes(buf: ByteArray, offset: Int, value: IsAsciiWhitespace) {
                }
            }
    }
}

public open class IsNotEmpty : Fn<String, Any>, FnMut<String, Any>, FnOnce<String, Boolean> {
    public open fun clone(): IsNotEmpty = IsNotEmpty()

    public override fun call(tupleArg0: String): Boolean {
        val s = tupleArg0
        return !s.isEmpty()
    }

    public override fun callMut(tupleArg0: String): Boolean {
        val s = tupleArg0
        return this.call(s)
    }

    public override fun callOnce(tupleArg0: String): Boolean {
        val s = tupleArg0
        return this.call(s)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<IsNotEmpty> =
            object : TypeLayout<IsNotEmpty> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): IsNotEmpty =
                    IsNotEmpty()
                override fun writeNative(addr: Long, value: IsNotEmpty) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): IsNotEmpty =
                    IsNotEmpty()
                override fun writeBytes(buf: ByteArray, offset: Int, value: IsNotEmpty) {
                }
            }
    }
}

public open class BytesIsNotEmpty : Fn<List<UByte>, Any>, FnMut<List<UByte>, Any>, FnOnce<List<UByte>, Boolean> {
    public open fun clone(): BytesIsNotEmpty = BytesIsNotEmpty()

    public override fun call(tupleArg0: List<UByte>): Boolean {
        val s = tupleArg0
        return !s.isEmpty()
    }

    public override fun callMut(tupleArg0: List<UByte>): Boolean {
        val s = tupleArg0
        return this.call(s)
    }

    public override fun callOnce(tupleArg0: List<UByte>): Boolean {
        val s = tupleArg0
        return this.call(s)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<BytesIsNotEmpty> =
            object : TypeLayout<BytesIsNotEmpty> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): BytesIsNotEmpty =
                    BytesIsNotEmpty()
                override fun writeNative(addr: Long, value: BytesIsNotEmpty) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): BytesIsNotEmpty =
                    BytesIsNotEmpty()
                override fun writeBytes(buf: ByteArray, offset: Int, value: BytesIsNotEmpty) {
                }
            }
    }
}

public open class UnsafeBytesToStr : Fn<List<UByte>, Any>, FnMut<List<UByte>, Any>, FnOnce<List<UByte>, String> {
    public open fun clone(): UnsafeBytesToStr = UnsafeBytesToStr()

    public override fun call(tupleArg0: List<UByte>): String {
        val bytes = tupleArg0
        return fromUtf8Unchecked(bytes)
    }

    public override fun callMut(tupleArg0: List<UByte>): String {
        val bytes = tupleArg0
        return this.call(bytes)
    }

    public override fun callOnce(tupleArg0: List<UByte>): String {
        val bytes = tupleArg0
        return this.call(bytes)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UnsafeBytesToStr> =
            object : TypeLayout<UnsafeBytesToStr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): UnsafeBytesToStr =
                    UnsafeBytesToStr()
                override fun writeNative(addr: Long, value: UnsafeBytesToStr) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnsafeBytesToStr =
                    UnsafeBytesToStr()
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnsafeBytesToStr) {
                }
            }
    }
}
