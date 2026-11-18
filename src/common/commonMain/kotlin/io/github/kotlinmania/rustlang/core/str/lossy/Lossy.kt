// port-lint: source str/lossy.rs
package io.github.kotlinmania.rustlang.core.str.lossy

import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.char.EscapeDebugExtArgs
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.validations.utf8CharWidth

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Creates an iterator over the contiguous valid UTF-8 ranges of this
 * slice, and the non-UTF-8 fragments in between.
 *
 * See the [Utf8Chunk] type for documentation of the items yielded by this iterator.
 *
 * # Examples
 *
 * This function formats arbitrary but mostly-UTF-8 bytes into Rust source
 * code in the form of a C-string literal (`c"..."`).
 *
 * ```kotlin
 * import std.fmt.Write as _; public fun cstrLiteral(bytes: [UByte]) -> String { val repr = String.new(); repr.pushStr("c\""); for chunk in bytes.utf8Chunks() { for ch in chunk.valid().chars() { write(repr, "{}", ch.escapeDebug()).unwrap(); } for byte in chunk.invalid() { write(repr, "\\x{:02X}", byte).unwrap(); } } repr.push('"'); repr } fun main() { val lit = cstrLiteral(b"\xferris the \xf0\x9f\xa6\x80\x07"); val expected = stringify(c"\xFErris the 🦀\u{7}"); assertEq(lit, expected); }
 * ```
 */
public fun List<UByte>.utf8Chunks(): Utf8Chunks = Utf8Chunks(source = this)

/**
 * An item returned by the [Utf8Chunks] iterator.
 *
 * A `Utf8Chunk` stores a sequence of [u8] up to the first broken character
 * when decoding a UTF-8 string.
 *
 * # Examples
 *
 * ```kotlin
 * // An invalid UTF-8 string
 * val bytes = "fooñbar".encodeToByteArray()
 * // Decode the first `Utf8Chunk`
 * val chunk = bytes.utf8Chunks().next()!!
 * // The first three characters are valid UTF-8
 * check("foo" == chunk.valid())
 * // The fourth character is broken
 * check("ñ".encodeToByteArray() == chunk.invalid())
 * ```
 */
public open class Utf8Chunk(
    public var valid: String,
    public var invalid: List<UByte>,
) {
    public open fun clone(): Utf8Chunk = Utf8Chunk(valid, invalid)

    /**
     * Returns the next validated UTF-8 substring.
     *
     * This substring can be empty at the start of the string or between
     * broken UTF-8 characters.
     */
    public open fun valid(): String = this.valid

    /**
     * Returns the invalid sequence that caused a failure.
     *
     * The returned slice will have a maximum length of 3 and starts after the
     * substring given by [valid]. Decoding will resume after this sequence.
     *
     * If empty, this is the last chunk in the string. If non-empty, an
     * unexpected byte was encountered or the end of the input was reached
     * unexpectedly.
     *
     * Lossy decoding would replace this sequence with [`U+FFFD REPLACEMENT
     * CHARACTER`].
     *
     * [valid]: Self.valid
     * [U.Fffd.Replacement.Character]: char.ReplacementCharacter
     */
    public open fun invalid(): List<UByte> = this.invalid
}

public open class Debug(
    public var value: List<UByte>,
) {
    public var field0: List<UByte>
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: Formatter): Result<Unit, Unit> {
        f.append('"')
        for (chunk in this.value.utf8Chunks()) {
            // Valid part.
            // Here we partially parse UTF-8 again which is suboptimal.
            run {
                val valid = chunk.valid()
                var from = 0
                for ((i, c) in valid.charIndices()) {
                    val esc = c.escapeDebugExt(EscapeDebugExtArgs(escapeSingleQuote = false, escapeDoubleQuote = true))
                    // If char needs escaping, flush backlog so far and write, else skip
                    if (esc.len() != 1) {
                        f.append(valid[from until i])
                        for (c1 in esc) {
                            f.append(c1)
                        }
                        from = i + c.lenUtf8()
                    }
                }
                f.append(valid[from until valid.size])
            }
            // Broken parts of string as hex escape.
            for (b in chunk.invalid()) {
                f.append("\\x$b")
            }
        }
        return run { f.append('"'); Result.Ok<Unit, Unit>(Unit) }
    }
}

/**
 * An iterator used to decode a slice of mostly UTF-8 bytes to string slices
 * ([str]) and byte slices ([`&[u8]`][byteslice]).
 *
 * This struct is created by the [utf8Chunks] method on bytes slices.
 * If you want a simple conversion from UTF-8 byte slices to string slices,
 * [fromUtf8] is easier to use.
 *
 * See the [Utf8Chunk] type for documentation of the items yielded by this iterator.
 *
 * [byteslice]: slice
 * [utf8Chunks]: slice.utf8Chunks
 *
 * # Examples
 *
 * This can be used to create functionality similar to
 * [String.fromUtf8Lossy] without allocating heap memory:
 *
 * ```kotlin
 * fun fromUtf8Lossy(input: List<UByte>, push: (String) -> Unit) {
 *     for (chunk in input.utf8Chunks()) {
 *         push(chunk.valid())
 *         if (!chunk.invalid().isEmpty()) {
 *             push("�")
 *         }
 *     }
 * }
 * ```
 *
 * [String.fromUtf8Lossy]: ../../std/string/struct.String.html#method.fromUtf8Lossy
 */
public open class Utf8Chunks(
    public var source: List<UByte>,
) : Iterable<Utf8Chunk> {
    override fun iterator(): Iterator<Utf8Chunk> = object : AbstractIterator<Utf8Chunk>() {
        override fun computeNext() {
            when (val v = this@Utf8Chunks.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Utf8Chunks = Utf8Chunks(source)

    public open fun debug(): Debug = Debug(this.source)

    public open fun next(): Utf8Chunk? {
        val TAG_CONT_U8: UByte = 128u
        fun safeGet(xs: List<UByte>, i: Int): UByte = (xs.get(i) ?: 0)
        if (this.source.isEmpty()) {
            return null
        }
        var i = 0
        var validUpTo = 0
        while (true) {
            val matchVal = this.source.get(i)
            if (matchVal == null) break
            val byte = matchVal
            i += 1
            if (byte < 128) {
                // This could be a `1 -> ...` case in the match below, but for
                // the common case of all-ASCII inputs, we bypass loading the
                // sizeable UTF8_CHAR_WIDTH table into cache.
            } else {
                val w = utf8CharWidth(byte)
                when (w) {
                    2 -> {
                        if (safeGet(this.source, i) and 192u.toUByte() != TAG_CONT_U8) {
                            break
                        }
                        i += 1
                    }
                    3 -> {
                        run {
                            val matchVal1 = Pair(byte, safeGet(this.source, i))
                            when {
                                matchVal1.first == 0xE0 && matchVal1.second in 0xA0..0xBF -> Unit
                                matchVal1.first in 0xE1..0xEC && matchVal1.second in 0x80..0xBF -> Unit
                                matchVal1.first == 0xED && matchVal1.second in 0x80..0x9F -> Unit
                                matchVal1.first in 0xEE..0xEF && matchVal1.second in 0x80..0xBF -> Unit
                                else -> { break }
                            }
                        }
                        i += 1
                        if (safeGet(this.source, i) and 192u.toUByte() != TAG_CONT_U8) {
                            break
                        }
                        i += 1
                    }
                    4 -> {
                        run {
                            val matchVal2 = Pair(byte, safeGet(this.source, i))
                            when {
                                matchVal2.first == 0xF0 && matchVal2.second in 0x90..0xBF -> Unit
                                matchVal2.first in 0xF1..0xF3 && matchVal2.second in 0x80..0xBF -> Unit
                                matchVal2.first == 0xF4 && matchVal2.second in 0x80..0x8F -> Unit
                                else -> { break }
                            }
                        }
                        i += 1
                        if (safeGet(this.source, i) and 192u.toUByte() != TAG_CONT_U8) {
                            break
                        }
                        i += 1
                        if (safeGet(this.source, i) and 192u.toUByte() != TAG_CONT_U8) {
                            break
                        }
                        i += 1
                    }
                    else -> { break }
                }
            }
            validUpTo = i
        }
        // SAFETY: `i <= self.source.len()` because it is only ever incremented
        // via `i += 1` and in between every single one of those increments, `i`
        // is compared against `self.source.len()`. That happens either
        // literally by `i<self.source.len()` in the while-loop's condition,
        // or indirectly by `safeGet(self.source, i) & 192 != TAG_CONT_U8`. The
        // loop is terminated as soon as the latest `i += 1` has made `i` no
        // longer less than `self.source.len()`, which means it'll be at most
        // equal to `self.source.len()`.
        val (inspected, remaining) = this.source.splitAtUnchecked(i)
        this.source = remaining
        // SAFETY: `validUpTo <= i` because it is only ever assigned via
        // `validUpTo = i` and `i` only increases.
        val (valid, invalid) = inspected.splitAtUnchecked(validUpTo)
        return Utf8Chunk(valid = fromUtf8Unchecked(valid), invalid = invalid)
    }

    public open fun fmt(f: Formatter): Result<Unit, Unit> = run { DebugStruct(f, "Utf8Chunks").field("source", this.debug()).finish(); Result.Ok<Unit, Unit>(Unit) }
}
