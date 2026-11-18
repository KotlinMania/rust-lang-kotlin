// port-lint: source escape.rs
package io.github.kotlinmania.rustlang.procmacro.escape

public open class EscapeOptions(
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

    public companion object
}

public fun escapeBytes(bytes: List<UByte>, opt: EscapeOptions): String {
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
    if (byte == 0u.toUByte()) {
        repr += "\\0"
    } else if ((byte == 39u.toUByte() && !opt.escapeSingleQuote) || (byte == 34u.toUByte() && !opt.escapeDoubleQuote)) {
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
        // Escapes \0, \t, \r, \n, \\, \', \", and uses \u(...) for
        // non-printable characters and for GraphemeExtend characters, which
        // includes things like U+0300 "Combining Grave Accent".
        repr.extend(ch.escapeDebug())
    }
}
