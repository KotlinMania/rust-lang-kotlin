// port-lint: source escape.rs
package io.github.kotlinmania.rustlang.procmacro.escape

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

internal open class EscapeOptions(
    /**
     * Produce \'.
     */
    public var escapeSingleQuote: Boolean,
    /**
     * Produce \".
     */
    public var escapeDoubleQuote: Boolean,
    /**
     * Produce \x escapes for non-ASCII, and use \x rather than \u for ASCII
     * control characters.
     */
    public var escapeNonascii: Boolean,
) {
    public open fun clone(): EscapeOptions = EscapeOptions(escapeSingleQuote, escapeDoubleQuote, escapeNonascii)

    public companion object {
        public fun typeLayout(): TypeLayout<EscapeOptions> =
            object : TypeLayout<EscapeOptions> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 3L
                override fun readNative(addr: Long): EscapeOptions =
                    EscapeOptions(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: EscapeOptions) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.escapeSingleQuote)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.escapeDoubleQuote)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.escapeNonascii)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EscapeOptions =
                    EscapeOptions(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EscapeOptions) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.escapeSingleQuote)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.escapeDoubleQuote)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.escapeNonascii)
                }
            }
    }
}

internal fun escapeBytes(bytes: List<UByte>, opt: EscapeOptions): String {
    val repr = ""
    if (opt.escapeNonascii) {
        for (byte in bytes) {
            escapeSingleByte(byte, opt, repr)
        }
    } else {
        for (chunk in bytes.utf8Chunks()) {
            for (ch in chunk.valid().chars()) {
                escapeSingleChar(ch, opt, repr)
            }
            for (byte1 in chunk.invalid()) {
                escapeSingleByte(byte1, opt, repr)
            }
        }
    }
    return repr
}

public fun escapeSingleByte(byte: UByte, opt: EscapeOptions, repr: String) {
    if (byte == 0u) {
        repr += "\\0"
    } else if ((byte == 39u && !opt.escapeSingleQuote) || (byte == 34u && !opt.escapeDoubleQuote)) {
        repr += byte.toInt().toChar()
    } else {
        // Escapes \t, \r, \n, \\, \', \", and uses \x## for non-ASCII and
        // for ASCII control characters.
        repr.extend(byte.escapeAscii().map(Char::from))
    }
}

public fun escapeSingleChar(ch: Char, opt: EscapeOptions, repr: String) {
    if ((ch == '\'' && !opt.escapeSingleQuote) || (ch == '"' && !opt.escapeDoubleQuote)) {
        repr += ch
    } else {
        // Escapes \0, \t, \r, \n, \\, \', \", and uses \u{...} for
        // non-printable characters and for GraphemeExtend characters, which
        // includes things like U+0300 "Combining Grave Accent".
        repr.extend(ch.escapeDebug())
    }
}
