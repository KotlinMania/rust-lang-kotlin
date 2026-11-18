// port-lint: source char/convert.rs
package io.github.kotlinmania.rustlang.core.char.convert

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.char.TryFromCharError
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.chars
import kotlin.experimental.xor

// Character conversions.

/**
 * Converts a `UInt` to a `char`. See [char.fromU32].
 */
public fun fromU32(i: UInt): Char? {
    // Note(const-Note): once Result.ok is const fn, use it here
    return run {
        val matchVal = charTryFromU32(i)
        when (matchVal) { is Result.Ok<*, *> -> {
            val c = matchVal.okValue()
            c
        }; is Result.Err<*, *> -> {
            null
        } }
    }
}

/**
 * Converts a `UInt` to a `char`, ignoring validity. See [char.fromU32Unchecked].
 */
public fun fromU32Unchecked(i: UInt): Char {
    // SAFETY: the caller must guarantee that `i` is a valid char value.
    return run {
        Unit
        transmuteWithLayouts<UInt, Char>(i, PrimitiveLayouts.U32, PrimitiveLayouts.CHAR)
    }
}

/**
 * Converts a [char] into a [u32].
 *
 * # Examples
 *
 * ```kotlin
 * val c = 'c'
 * val u = UInt.from(c)
 * check(4 == sizeOfVal(u))
 * ```
 */
public fun UInt.Companion.from(c: Char): UInt = c.toUInt()

/**
 * Converts a [char] into a [u64].
 *
 * # Examples
 *
 * ```kotlin
 * val c = 0x1f464
 * val u = ULong.from(c)
 * check(8 == sizeOfVal(u))
 * ```
 */
public fun ULong.Companion.from(c: Char): ULong {
    // The char is casted to the value of the code point, then zero-extended to 64 bit.
    // See [https://doc.rust-lang.org/reference/expressions/operator-expr.html#semantics]
    return c.toULong()
}

/**
 * Converts a [char] into a [u128].
 *
 * # Examples
 *
 * ```kotlin
 * val c = '\u2699'
 * val u = U128.from(c)
 * check(16 == sizeOfVal(u))
 * ```
 */
public fun U128.Companion.from(c: Char): U128 {
    // The char is casted to the value of the code point, then zero-extended to 128 bit.
    // See [https://doc.rust-lang.org/reference/expressions/operator-expr.html#semantics]
    return U128.fromLong(c.toLong())
}

/**
 * Maps a `char` with a code point from U+0000 to U+00FF (inclusive) to a byte in `0x00 ..= 0xFF` with
 * the same value, failing if the code point is greater than U+00FF.
 *
 * See [`impl.From.u8.forLoop.char`](char#impl-From<u8>-for-char) for details on the encoding.
 */
/**
 * Tries to convert a [char] into a [u8].
 *
 * # Examples
 *
 * ```kotlin
 * val a = '\u00ff'
 * // U+00FF
 * val b = '\u0100'
 * // U+0100
 * check(UByte.tryFrom(a) == Result.success(0xFFu.toUByte()))
 * check(UByte.tryFrom(b).isErr())
 * ```
 */
public fun UByte.Companion.tryFrom(c: Char): Result<UByte, TryFromCharError> {
    // Note(const-Note): this should use mapErr instead
    return run {
        val matchVal = UByte.tryFrom(UInt.from(c))
        when (matchVal) { is Result.Ok<*, *> -> {
            val b = matchVal.okValue()
            Result.Ok<UByte, TryFromCharError>(b)
        }; is Result.Err<*, *> -> {
            Result.Err<UByte, TryFromCharError>(TryFromCharError(Unit))
        } }
    }
}

/**
 * Maps a `char` with a code point from U+0000 to U+FFFF (inclusive) to a `UShort` in `0x0000 ..= 0xFFFF`
 * with the same value, failing if the code point is greater than U+FFFF.
 *
 * This corresponds to the UCS-2 encoding, as specified in ISO/IEC 10646:2003.
 */
/**
 * Tries to convert a [char] into a [u16].
 *
 * # Examples
 *
 * ```kotlin
 * val transRights = '\u26a7'
 * // U+26A7
 * val ninjas = 0x1f977
 * // U+1F977
 * check(UShort.tryFrom(transRights) == Result.success(0x26A7u.toUShort()))
 * check(UShort.tryFrom(ninjas).isErr())
 * ```
 */
public fun UShort.Companion.tryFrom(c: Char): Result<UShort, TryFromCharError> {
    // Note(const-Note): this should use mapErr instead
    return run {
        val matchVal = UShort.tryFrom(UInt.from(c))
        when (matchVal) { is Result.Ok<*, *> -> {
            val x = matchVal.okValue()
            Result.Ok<UShort, TryFromCharError>(x)
        }; is Result.Err<*, *> -> {
            Result.Err<UShort, TryFromCharError>(TryFromCharError(Unit))
        } }
    }
}

/**
 * Maps a `char` with a code point from U+0000 to U+10FFFF (inclusive) to a `Int` in
 * `0x0000 ..= 0x10FFFF` with the same value, failing if the final value is unrepresentable by
 * `Int`.
 *
 * Generally speaking, this conversion can be seen as obtaining the character's corresponding
 * UTF-32 code point to the extent representable by pointer addresses.
 */
/**
 * Tries to convert a [char] into a [usize].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 *
 * val a = '\uffff'
 * // Always succeeds.
 * val b = 0x10ffff
 * // Conditionally succeeds.
 * check(Int.tryFrom(a) == Result.success(0xFFFF))
 * if (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) >= sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)) {
 *     check(Int.tryFrom(b) == Result.success(0x10FFFF))
 * } else {
 *     check(when (val matchVal = Int.tryFrom(b)) {
 *         is Err -> true
 *         else -> false
 *     })
 * }
 * ```
 */
public fun Int.Companion.tryFrom(c: Char): Result<Int, TryFromCharError> {
    // Note(const-Note): this should use mapErr instead
    return run {
        val matchVal = Int.tryFrom(UInt.from(c))
        when (matchVal) { is Result.Ok<*, *> -> {
            val x = matchVal.okValue()
            Result.Ok<Int, TryFromCharError>(x)
        }; is Result.Err<*, *> -> {
            Result.Err<Int, TryFromCharError>(TryFromCharError(Unit))
        } }
    }
}

/**
 * Maps a byte in `0x00 ..= 0xFF` to a `char` whose code point has the same value from U+0000 to U+00FF
 * (inclusive).
 *
 * Unicode is designed such that this effectively decodes bytes
 * with the character encoding that IANA calls ISO-8859-1.
 * This encoding is compatible with ASCII.
 *
 * Note that this is different from ISO/IEC 8859-1 a.k.a. ISO 8859-1 (with one less hyphen),
 * which leaves some "blanks", byte values that are not assigned to any character.
 * ISO-8859-1 (the IANA one) assigns them to the C0 and C1 control codes.
 *
 * Note that this is *also* different from Windows-1252 a.k.a. code page 1252,
 * which is a superset ISO/IEC 8859-1 that assigns some (not all!) blanks
 * to punctuation and various Latin characters.
 *
 * To confuse things further, [on the Web](https://encoding.spec.whatwg.org/)
 * `ascii`, `iso - 8859 - 1`, and `windows - 1252` are all aliases
 * for a superset of Windows-1252 that fills the remaining blanks with corresponding
 * C0 and C1 control codes.
 */
/**
 * Converts a [u8] into a [char].
 *
 * # Examples
 *
 * ```kotlin
 * val u = 32.toUByte()
 * val c = Char.from(u)
 * check(4 == sizeOfVal(c))
 * ```
 */
public fun Char.Companion.from(i: UByte): Char = i.toInt().toChar()

/**
 * An error which can be returned when parsing a char.
 *
 * This `class` is created when using the [char.fromStr] method.
 */
public open class ParseCharError(
    public var kind: CharErrorKind,
) {
    public open fun clone(): ParseCharError = ParseCharError(kind)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this.kind) {
            CharErrorKind.EmptyString -> "cannot parse char from empty string"
            CharErrorKind.TooManyChars -> "too many characters in string"
        }.fmt(f)
}

public sealed class CharErrorKind {
    public data object EmptyString : CharErrorKind()
    public data object TooManyChars : CharErrorKind()

    public fun rustEquals(other: CharErrorKind): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        EmptyString -> 0
        TooManyChars -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

public fun fromStrChar(s: String): Result<Char, ParseCharError> {
    val chars = s.chars()
    return run {
        val matchVal = Pair(chars.next(), chars.next())
        when {
            matchVal.first == null -> Result.Err<Char, ParseCharError>(ParseCharError(kind = CharErrorKind.EmptyString))
            matchVal.first != null && matchVal.second == null -> {
                val c = matchVal.first
                Result.Ok<Char, ParseCharError>(c)
            }
            else -> Result.Err<Char, ParseCharError>(ParseCharError(kind = CharErrorKind.TooManyChars))
        }
    }
}

public fun charTryFromU32(i: UInt): Result<Char, CharTryFromError> {
    // This is an optimized version of the check
    // (i > MAX as u32) || (i >= 0xD800 && i <= 0xDFFF),
    // which can also be written as
    // i >= 0x110000 || (i >= 0xD800 && i < 0xE000).
    //
    // The XOR with 0xD800 permutes the ranges such that 0xD800..0xE000 is
    // mapped to 0x0000..0x0800, while keeping all the high bits outside 0xFFFF the same.
    // In particular, numbers >= 0x110000 stay in this range.
    //
    // Subtracting 0x800 causes 0x0000..0x0800 to wrap, meaning that a single
    // unsigned comparison against 0x110000 - 0x800 will detect both the wrapped
    // surrogate range as well as the numbers originally larger than 0x110000.
    return if (((i xor 0xD800u) - 0x800u) >= 0x110000u - 0x800u) {
        Result.Err<Char, CharTryFromError>(CharTryFromError(Unit))
    } else {
        // SAFETY: checked that it's a legal unicode value
        Result.Ok<Char, CharTryFromError>(transmuteWithLayouts<UInt, Char>(i, PrimitiveLayouts.U32, UnresolvedTypeLayout<Char>()))
    }
}

public fun Char.Companion.tryFrom(i: UInt): Result<Char, CharTryFromError> = charTryFromU32(i)

/**
 * The error type returned when a conversion from [prim.u32] to [prim.char] fails.
 *
 * This `class` is created by the [`char.tryFrom.u32`](char#impl-TryFrom<u32>-for-char) method.
 * See its documentation for more.
 */
public open class CharTryFromError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): CharTryFromError = CharTryFromError(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = "converted integer out of range for `char`".fmt(f)

    public companion object {
        public fun typeLayout(): TypeLayout<CharTryFromError> =
            object : TypeLayout<CharTryFromError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): CharTryFromError =
                    CharTryFromError(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: CharTryFromError) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CharTryFromError =
                    CharTryFromError(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CharTryFromError) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Converts a digit in the given radix to a `char`. See [char.fromDigit].
 */
public fun fromDigit(num: UInt, radix: UInt): Char? {
    if (radix > 36u) {
        error("from_digit: radix is too high (maximum 36)")
    }
    return if (num < radix) {
        val num1 = num.toUByte()
        if (num1 < 10u.toUByte()) {
            (48u + num1).toInt().toChar()
        } else {
            (97u + num1 - 10u).toInt().toChar()
        }
    } else {
        null
    }
}
