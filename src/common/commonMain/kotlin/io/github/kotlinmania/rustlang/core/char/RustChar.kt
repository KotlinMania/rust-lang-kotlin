// rust-kotlin: manual
package io.github.kotlinmania.rustlang.core.char

/**
 * 32-bit Rust Unicode scalar value (`char`), valid in `0x0..=0xD7FF` or `0xE000..=0x10FFFF`.
 */
public data class RustChar(
    public val value: UInt,
) : Comparable<RustChar> {
    init {
        require(isValidCodePoint(value)) {
            "invalid Unicode scalar value: 0x${value.toString(16)}"
        }
    }

    public fun toInt(): Int = value.toInt()

    public fun toUInt(): UInt = value

    public fun toChar(): Char {
        require(value <= 0xFFFFu) {
            "Unicode scalar 0x${value.toString(16)} exceeds BMP UTF-16 Char"
        }
        return value.toInt().toChar()
    }

    public fun lenUtf8(): Int =
        when {
            value < 0x80u -> 1
            value < 0x800u -> 2
            value < 0x10000u -> 3
            else -> 4
        }

    public fun encodeUtf8(dst: MutableList<UByte>): List<UByte> {
        val code = value.toInt()
        when (lenUtf8()) {
            1 -> {
                require(dst.isNotEmpty())
                dst[0] = code.toUByte()
                return dst.subList(0, 1)
            }
            2 -> {
                require(dst.size >= 2)
                dst[0] = (0xC0 or (code shr 6)).toUByte()
                dst[1] = (0x80 or (code and 0x3F)).toUByte()
                return dst.subList(0, 2)
            }
            3 -> {
                require(dst.size >= 3)
                dst[0] = (0xE0 or (code shr 12)).toUByte()
                dst[1] = (0x80 or ((code shr 6) and 0x3F)).toUByte()
                dst[2] = (0x80 or (code and 0x3F)).toUByte()
                return dst.subList(0, 3)
            }
            else -> {
                require(dst.size >= 4)
                dst[0] = (0xF0 or (code shr 18)).toUByte()
                dst[1] = (0x80 or ((code shr 12) and 0x3F)).toUByte()
                dst[2] = (0x80 or ((code shr 6) and 0x3F)).toUByte()
                dst[3] = (0x80 or (code and 0x3F)).toUByte()
                return dst.subList(0, 4)
            }
        }
    }

    public fun isAscii(): Boolean = value <= 0x7Fu

    public override fun compareTo(other: RustChar): Int = value.compareTo(other.value)

    public companion object {
        public val MAX: RustChar = RustChar(0x10FFFFu)
        public val REPLACEMENT_CHARACTER: RustChar = RustChar(0xFFFDu)

        public fun isValidCodePoint(code: UInt): Boolean =
            code <= 0xD7FFu || (code in 0xE000u..0x10FFFFu)

        public fun fromU32(i: UInt): RustChar? =
            if (isValidCodePoint(i)) RustChar(i) else null

        public fun fromU32Unchecked(i: UInt): RustChar = RustChar(i)

        public fun fromDigit(num: UInt, radix: UInt): RustChar? {
            if (radix !in 2u..36u || num >= radix) return null
            val cp =
                if (num < 10u) {
                    '0'.code.toUInt() + num
                } else {
                    'a'.code.toUInt() + (num - 10u)
                }
            return RustChar(cp)
        }
    }
}
