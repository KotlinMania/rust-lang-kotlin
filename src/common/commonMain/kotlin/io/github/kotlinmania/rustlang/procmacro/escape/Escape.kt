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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<EscapeOptions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<EscapeOptions> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 3L
                override fun readNative(addr: Long): EscapeOptions =
                    EscapeOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: EscapeOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.escapeSingleQuote)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.escapeDoubleQuote)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.escapeNonascii)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EscapeOptions =
                    EscapeOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EscapeOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.escapeSingleQuote)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.escapeDoubleQuote)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.escapeNonascii)
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
