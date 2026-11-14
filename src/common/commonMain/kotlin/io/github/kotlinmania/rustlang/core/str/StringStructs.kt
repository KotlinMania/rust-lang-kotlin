package io.github.kotlinmania.rustlang.core.str

import io.github.kotlinmania.rustlang.core.char.isAsciiWhitespace
import io.github.kotlinmania.rustlang.core.char.methods.EscapeDebugExtArgs
import io.github.kotlinmania.rustlang.core.macros.builtin.line
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.iter.EscapeDebug
import io.github.kotlinmania.rustlang.core.str.iter.EscapeDefault
import io.github.kotlinmania.rustlang.core.str.iter.EscapeUnicode

/**
 * A nameable, cloneable fn type
 */
public open class LinesMap : Fn<String>, FnMut<String>, FnOnce<String, Any?, String> {
    public open fun clone(): LinesMap = LinesMap()

    public override fun call(tupleArg0: String): String {
        return run {
            val line = line.takeIf { it.endsWith('\n') }?.removeSuffix('\n') ?: return line
            val line1 = line.takeIf { it.endsWith('\r') }?.removeSuffix('\r') ?: return line
            line1
        }
    }

    public override fun callMut(tupleArg0: String): String = this.call(line)

    public override fun callOnce(tupleArg0: String): String = this.call(line)

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

public open class CharEscapeDebug : Fn<Char>, FnMut<Char>, FnOnce<Char, Any?, EscapeDebug> {
    public open fun clone(): CharEscapeDebug = CharEscapeDebug()

    public override fun call(tupleArg0: Char): EscapeDebug = c.escapeDebugExt(EscapeDebugExtArgs.ESCAPE_ALL)

    public override fun callMut(tupleArg0: Char): EscapeDebug = this.call(c)

    public override fun callOnce(tupleArg0: Char): EscapeDebug = this.call(c)

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

public open class CharEscapeUnicode : Fn<Char>, FnMut<Char>, FnOnce<Char, Any?, EscapeUnicode> {
    public open fun clone(): CharEscapeUnicode = CharEscapeUnicode()

    public override fun call(tupleArg0: Char): EscapeUnicode = c.escapeUnicode()

    public override fun callMut(tupleArg0: Char): EscapeUnicode = this.call(c)

    public override fun callOnce(tupleArg0: Char): EscapeUnicode = this.call(c)

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

public open class CharEscapeDefault : Fn<Char>, FnMut<Char>, FnOnce<Char, Any?, EscapeDefault> {
    public open fun clone(): CharEscapeDefault = CharEscapeDefault()

    public override fun call(tupleArg0: Char): EscapeDefault = c.escapeDefault()

    public override fun callMut(tupleArg0: Char): EscapeDefault = this.call(c)

    public override fun callOnce(tupleArg0: Char): EscapeDefault = this.call(c)

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

public open class IsWhitespace : Fn<Char>, FnMut<Char>, FnOnce<Char, Any?, Boolean> {
    public open fun clone(): IsWhitespace = IsWhitespace()

    public override fun call(tupleArg0: Char): Boolean = c.isWhitespace()

    public override fun callMut(tupleArg0: Char): Boolean = this.call(c)

    public override fun callOnce(tupleArg0: Char): Boolean = this.call(c)

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

internal open class IsAsciiWhitespace : Fn<UByte>, FnMut<UByte>, FnOnce<UByte, Any?, Boolean> {
    public open fun clone(): IsAsciiWhitespace = IsAsciiWhitespace()

    public override fun call(tupleArg0: UByte): Boolean = byte.isAsciiWhitespace()

    public override fun callMut(tupleArg0: UByte): Boolean = this.call(byte)

    public override fun callOnce(tupleArg0: UByte): Boolean = this.call(byte)

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

public open class IsNotEmpty : Fn<String>, FnMut<String>, FnOnce<String, Any?, Boolean> {
    public open fun clone(): IsNotEmpty = IsNotEmpty()

    public override fun call(tupleArg0: String): Boolean = !s.isEmpty()

    public override fun callMut(tupleArg0: String): Boolean = this.call(s)

    public override fun callOnce(tupleArg0: String): Boolean = this.call(s)

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

internal open class BytesIsNotEmpty : Fn<List<UByte>>, FnMut<List<UByte>>, FnOnce<List<UByte>, Any?, Boolean> {
    public open fun clone(): BytesIsNotEmpty = BytesIsNotEmpty()

    public override fun call(tupleArg0: List<UByte>): Boolean = !s.isEmpty()

    public override fun callMut(tupleArg0: List<UByte>): Boolean = this.call(s)

    public override fun callOnce(tupleArg0: List<UByte>): Boolean = this.call(s)

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

public open class UnsafeBytesToStr : Fn<List<UByte>>, FnMut<List<UByte>>, FnOnce<List<UByte>, Any?, String> {
    public open fun clone(): UnsafeBytesToStr = UnsafeBytesToStr()

    public override fun call(tupleArg0: List<UByte>): String = fromUtf8Unchecked(bytes)

    public override fun callMut(tupleArg0: List<UByte>): String = this.call(bytes)

    public override fun callOnce(tupleArg0: List<UByte>): String = this.call(bytes)

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
