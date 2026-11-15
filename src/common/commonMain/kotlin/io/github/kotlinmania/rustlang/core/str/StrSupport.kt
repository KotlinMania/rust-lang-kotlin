package io.github.kotlinmania.rustlang.core.str

/**
 * Iterator over a Kotlin [String] yielding Kotlin [Char] scalars, matching `core::str::Chars` for BMP strings,
 * exposing the remaining tail via [asStr].
 */
public class RustStrChars(
    private val source: String,
    private var index: Int = 0,
) : Iterator<Char> {
    override fun hasNext(): Boolean = index < source.length

    override fun next(): Char {
        if (index >= source.length) {
            throw NoSuchElementException("RustStrChars exhausted")
        }
        val ch = source[index]
        index += 1
        return ch
    }

    public fun nextCodeOrNull(): Int? {
        if (index >= source.length) return null
        val ch = source[index]
        index += 1
        return ch.code
    }

    public fun asStr(): String = source.substring(index)

    public fun clone(): RustStrChars = RustStrChars(source, index)

    public fun enumerate(): Iterator<Pair<Int, Char>> =
        object : Iterator<Pair<Int, Char>> {
            private var stepIndex: Int = 0

            override fun hasNext(): Boolean = this@RustStrChars.hasNext()

            override fun next(): Pair<Int, Char> {
                if (!hasNext()) {
                    throw NoSuchElementException("RustStrChars enumerate exhausted")
                }
                val ch = this@RustStrChars.next()
                val currentIdx = stepIndex
                stepIndex += 1
                return Pair(currentIdx, ch)
            }
        }
}

public fun String.rustStrChars(): RustStrChars = RustStrChars(this)

/**
 * Converts an ASCII character to a digit in [radix] (`2..=36`), matching `core::char::to_digit` for ASCII scalars.
 */
public fun Char.rustToDigit(radix: UInt): UInt? {
    require(radix in 2u..36u) { "rustToDigit radix must be in 2..36, got $radix" }
    val digit =
        when (this) {
            in '0'..'9' -> this - '0'
            in 'a'..'z' -> this - 'a' + 10
            in 'A'..'Z' -> this - 'A' + 10
            else -> return null
        }
    val digitU = digit.toUInt()
    return if (digitU < radix) digitU else null
}
