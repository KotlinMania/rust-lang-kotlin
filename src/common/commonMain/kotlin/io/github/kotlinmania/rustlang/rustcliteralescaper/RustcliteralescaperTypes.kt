package io.github.kotlinmania.rustlang.rustcliteralescaper

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroCharInner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.orElse
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.core.str.iter.Chars

/**
 * Errors and warnings that can occur during string, char, and byte unescaping.
 *
 * Mostly relating to malformed escape sequences, but also a few other problems.
 */
public sealed class EscapeError {
    /**
     * Expected 1 char, but 0 were found.
     */
    public data object ZeroChars : EscapeError()
    /**
     * Expected 1 char, but more than 1 were found.
     */
    public data object MoreThanOneChar : EscapeError()
    /**
     * Escaped '\' character without continuation.
     */
    public data object LoneSlash : EscapeError()
    /**
     * Invalid escape character (e.g. '\z').
     */
    public data object InvalidEscape : EscapeError()
    /**
     * Raw '\r' encountered.
     */
    public data object BareCarriageReturn : EscapeError()
    /**
     * Raw '\r' encountered in raw string.
     */
    public data object BareCarriageReturnInRawString : EscapeError()
    /**
     * Unescaped character that was expected to be escaped (e.g. raw '\t').
     */
    public data object EscapeOnlyChar : EscapeError()
    /**
     * Numeric character escape is too short (e.g. '\x1').
     */
    public data object TooShortHexEscape : EscapeError()
    /**
     * Invalid character in numeric escape (e.g. '\xz')
     */
    public data object InvalidCharInHexEscape : EscapeError()
    /**
     * Character code in numeric escape is non-ascii (e.g. '\xFF').
     */
    public data object OutOfRangeHexEscape : EscapeError()
    /**
     * '\u' not followed by '('.
     */
    public data object NoBraceInUnicodeEscape : EscapeError()
    /**
     * Non-hexadecimal value in '\u(..)'.
     */
    public data object InvalidCharInUnicodeEscape : EscapeError()
    /**
     * '\u()'
     */
    public data object EmptyUnicodeEscape : EscapeError()
    /**
     * No closing brace in '\u(..)', e.g. '\u(12'.
     */
    public data object UnclosedUnicodeEscape : EscapeError()
    /**
     * '\u(_12)'
     */
    public data object LeadingUnderscoreUnicodeEscape : EscapeError()
    /**
     * More than 6 characters in '\u(..)', e.g. '\u(10FFFF_FF)'
     */
    public data object OverlongUnicodeEscape : EscapeError()
    /**
     * Invalid in-bound unicode character code, e.g. '\u(DFFF)'.
     */
    public data object LoneSurrogateUnicodeEscape : EscapeError()
    /**
     * Out of bounds unicode character code, e.g. '\u(FFFFFF)'.
     */
    public data object OutOfRangeUnicodeEscape : EscapeError()
    /**
     * Unicode escape code in byte literal.
     */
    public data object UnicodeEscapeInByte : EscapeError()
    /**
     * Non-ascii character in byte literal, byte string literal, or raw byte string literal.
     */
    public data object NonAsciiCharInByte : EscapeError()
    /**
     * `\0` in a C string literal.
     */
    public data object NulInCStr : EscapeError()
    /**
     * After a line ending with '\', the next line contains whitespace
     * characters that are not skipped.
     */
    public data object UnskippedWhitespaceWarning : EscapeError()
    /**
     * After a line ending with '\', multiple lines are skipped.
     */
    public data object MultipleSkippedLinesWarning : EscapeError()

    public fun rustEquals(other: EscapeError): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        ZeroChars -> 0
        MoreThanOneChar -> 1
        LoneSlash -> 2
        InvalidEscape -> 3
        BareCarriageReturn -> 4
        BareCarriageReturnInRawString -> 5
        EscapeOnlyChar -> 6
        TooShortHexEscape -> 7
        InvalidCharInHexEscape -> 8
        OutOfRangeHexEscape -> 9
        NoBraceInUnicodeEscape -> 10
        InvalidCharInUnicodeEscape -> 11
        EmptyUnicodeEscape -> 12
        UnclosedUnicodeEscape -> 13
        LeadingUnderscoreUnicodeEscape -> 14
        OverlongUnicodeEscape -> 15
        LoneSurrogateUnicodeEscape -> 16
        OutOfRangeUnicodeEscape -> 17
        UnicodeEscapeInByte -> 18
        NonAsciiCharInByte -> 19
        NulInCStr -> 20
        UnskippedWhitespaceWarning -> 21
        MultipleSkippedLinesWarning -> 22
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    /**
     * Returns true for actual errors, as opposed to warnings.
     */
    public open fun isFatal(): Boolean =
        !when (this) {
            EscapeError.UnskippedWhitespaceWarning, EscapeError.MultipleSkippedLinesWarning -> true
            else -> false
        }
}

/**
 * Trait for checking raw string literals for validity
 */
public interface CheckRaw<RawUnit> {
    /**
     * Converts chars to the unit type of the literal type
     */
    public fun char2rawUnit(c: Char): Result<RawUnit, EscapeError>

    /**
     * Takes the contents of a raw literal (without quotes)
     * and produces a sequence of `Result<Self.RawUnit, EscapeError>`
     * which are returned via `callback`.
     *
     * NOTE: Does no escaping, but produces errors for bare carriage return ('\r').
     */
    public fun checkRaw(src: String, callback: (IntRange, Result<RawUnit, EscapeError>) -> Unit) {
        src.charIndices().forEach({ (pos, c) ->
            callback(pos until pos + c.lenUtf8(), if (c == '\r') {
                Result.Err(EscapeError.BareCarriageReturnInRawString)
            } else {
                this.char2rawUnit(c)
            })
        })
    }
}

/**
 * Enum representing either a char or a byte
 *
 * Used for mixed utf8 string literals, i.e. those that allow both unicode
 * chars and high bytes.
 */
public sealed class MixedUnit {
    /**
     * Used for ASCII chars (written directly or via `\x00`..`\x7f` escapes)
     * and Unicode chars (written directly or via `\u` escapes).
     *
     * For example, if '¥' appears in a string it is represented here as
     * `MixedUnit.Char('¥')`, and it will be appended to the relevant byte
     * string as the two-byte UTF-8 sequence `[0xc2, 0xa5]`
     */
    public class Char internal constructor(valueCtorArg: NonZero<kotlin.Char, NonZeroCharInner>) : MixedUnit() {
        public var value: NonZero<kotlin.Char, NonZeroCharInner> = valueCtorArg
            internal set

        public operator fun component1(): NonZero<kotlin.Char, NonZeroCharInner> = this.value

        public fun copy(value: NonZero<kotlin.Char, NonZeroCharInner> = this.value): Char = Char(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Char) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: NonZero<kotlin.Char, NonZeroCharInner>,
            ): Char = Char(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Used for high bytes (`\x80`..`\xff`).
     *
     * For example, if `\xa5` appears in a string it is represented here as
     * `MixedUnit.HighByte(0xa5)`, and it will be appended to the relevant
     * byte string as the single byte `0xa5`.
     */
    public class HighByte internal constructor(valueCtorArg: NonZero<UByte, NonZeroU8Inner>) : MixedUnit() {
        public var value: NonZero<UByte, NonZeroU8Inner> = valueCtorArg
            internal set

        public operator fun component1(): NonZero<UByte, NonZeroU8Inner> = this.value

        public fun copy(value: NonZero<UByte, NonZeroU8Inner> = this.value): HighByte = HighByte(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is HighByte) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: NonZero<UByte, NonZeroU8Inner>,
            ): HighByte = HighByte(
                valueCtorArg = value,
            )
        }
    }

    public fun rustEquals(other: MixedUnit): Boolean = this == other

    public companion object {
        public fun from(c: NonZero<kotlin.Char, NonZeroCharInner>): MixedUnit = MixedUnit.Char(c)

        public fun fromNonZeroUByte(byte: NonZero<UByte, NonZeroU8Inner>): MixedUnit =
            if (byte.get().isAscii()) {
                MixedUnit.Char(NonZero.new(((byte.get() as Any?) as kotlin.Char))!!)
            } else {
                MixedUnit.HighByte(byte)
            }

        public fun tryFrom(c: kotlin.Char): Result<MixedUnit, EscapeError> = (NonZero.new(c)?.let({ MixedUnit.Char(it) })?.let { Result.Ok<Any, EscapeError>(it) } ?: Result.Err<Any, EscapeError>(EscapeError.NulInCStr))

        public fun tryFrom(byte: UByte): Result<MixedUnit, EscapeError> = ((NonZero.new<NonZeroU8Inner, UByte>(byte))?.let(From::from)?.let { Result.Ok<Any, EscapeError>(it) } ?: Result.Err<Any, EscapeError>(EscapeError.NulInCStr))
    }
}

/**
 * Trait for unescaping escape sequences in strings
 */
public interface Unescape<Unit> {
    /**
     * Result of unescaping the zero char ('\0')
     */
    public val ZERO_RESULT: Result<Unit, EscapeError>

    /**
     * Converts non-zero bytes to the unit type
     */
    public fun nonzeroByte2unit(b: NonZero<UByte, NonZeroU8Inner>)

    /**
     * Converts chars to the unit type
     */
    public fun char2unit(c: Char): Result<Unit, EscapeError>

    /**
     * Converts the byte of a hex escape to the unit type
     */
    public fun hex2unit(b: UByte): Result<Unit, EscapeError>

    /**
     * Converts the result of a unicode escape to the unit type
     */
    public fun unicode2unit(r: Result<Char, EscapeError>): Result<Unit, EscapeError>

    /**
     * Unescape a single unit (single quote syntax)
     */
    public fun unescapeSingle(chars: Chars): Result<Unit, EscapeError> {
        val res =
            when (val tryResult = when (val matchVal = when (val tryResult = (chars.next()?.let { Result.Ok<Char, EscapeError>(it) } ?: Result.Err<Char, EscapeError>(EscapeError.ZeroChars))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, EscapeError>(tryResult.errValue()) }) {
                '\\' -> this.unescape1(chars)
                '\n', '\t', '\'' -> Result.Err<Unit, EscapeError>(EscapeError.EscapeOnlyChar)
                '\r' -> Result.Err<Unit, EscapeError>(EscapeError.BareCarriageReturn)
                else -> {
                    val c = matchVal
                    this.char2unit(c)
                }
            }) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, EscapeError>(tryResult.errValue()) }
        if (chars.next() != null) {
            return Result.Err<Unit, EscapeError>(EscapeError.MoreThanOneChar)
        }
        return Result.Ok<Unit, EscapeError>(res)
    }

    /**
     * Unescape the first unit of a string (double quoted syntax)
     */
    public fun unescape1(chars: Chars): Result<Unit, EscapeError> {
        // Previous character was '\\', unescape what follows.
        val c = when (val tryResult = (chars.next()?.let { Result.Ok<Char, EscapeError>(it) } ?: Result.Err<Char, EscapeError>(EscapeError.LoneSlash))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, EscapeError>(tryResult.errValue()) }
        return if (c == '0') {
            Unescape.ZERO_RESULT
        } else {
            simpleEscape(c).map({ b -> this.nonzeroByte2unit(b) }).orElse(closure@{ c1 -> when (c1) {
                'x' -> this.hex2unit(when (val tryResult = hexEscape(chars)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) })
                'u' -> this.unicode2unit(run {
                    val value = when (val tryResult = unicodeEscape(chars)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
                    if (value > Char.MAX_VALUE.toUInt()) {
                        Result.Err(EscapeError.OutOfRangeUnicodeEscape)
                    } else {
                        Char.fromU32(value).okOr(EscapeError.LoneSurrogateUnicodeEscape)
                    }
                })
                else -> Result.Err<Unit, EscapeError>(EscapeError.InvalidEscape)
            } })
        }
    }

    /**
     * Unescape a string literal
     *
     * Takes the contents of a raw string literal (without quotes)
     * and produces a sequence of `Result<Self.Unit, EscapeError>`
     * which are returned via `callback`.
     */
    public fun unescape(src: String, callback: (IntRange, Result<Unit, EscapeError>) -> Unit) {
        val chars = src.chars()
        while (true) {
            val matchVal = chars.next()
            if (matchVal == null) break
            val c = matchVal
            val start = src.length - chars.asStr().length - c.lenUtf8()
            val res =
                when (c) {
                    '\\' -> {
                        when (val matchVal1 = chars.asStr().encodeToByteArray().map { it.toUByte() }.first()) {
                            is Some<*> -> {
                                chars.next()
                                // skip whitespace for backslash newline, see [Rust language reference]
                                // (https://doc.rust-lang.org/reference/tokens.html#string-literals).
                                val callbackErr = { range, err -> callback(range, Result.Err(err)) }
                                skipAsciiWhitespace(chars, start, callbackErr)
                                continue
                            }
                            else -> {
                                this.unescape1(chars)
                            }
                        }
                    }
                    '"' -> Result.Err(EscapeError.EscapeOnlyChar)
                    '\r' -> Result.Err(EscapeError.BareCarriageReturn)
                    else -> {
                        val c1 = c
                        this.char2unit(c1)
                    }
                }
            val end = src.length - chars.asStr().length
            callback(start until end, res)
        }
    }
}

/**
 * Enum of the different kinds of literal
 */
public sealed class Mode {
    /**
     * ``a``
     */
    public data object Char : Mode()
    /**
     * `b'a'`
     */
    public data object Byte : Mode()
    /**
     * `"hello"`
     */
    public data object Str : Mode()
    /**
     * `r"hello"`
     */
    public data object RawStr : Mode()
    /**
     * `b"hello"`
     */
    public data object ByteStr : Mode()
    /**
     * `br"hello"`
     */
    public data object RawByteStr : Mode()
    /**
     * `c"hello"`
     */
    public data object CStr : Mode()
    /**
     * `cr"hello"`
     */
    public data object RawCStr : Mode()

    public fun rustEquals(other: Mode): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Char -> 0
        Byte -> 1
        Str -> 2
        RawStr -> 3
        ByteStr -> 4
        RawByteStr -> 5
        CStr -> 6
        RawCStr -> 7
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun inDoubleQuotes(): Boolean =
        when (this) {
            Mode.Str, Mode.RawStr, Mode.ByteStr, Mode.RawByteStr, Mode.CStr, Mode.RawCStr -> true
            Mode.Char, Mode.Byte -> false
        }

    public open fun prefixNoraw(): String =
        when (this) {
            Mode.Char, Mode.Str, Mode.RawStr -> ""
            Mode.Byte, Mode.ByteStr, Mode.RawByteStr -> "b"
            Mode.CStr, Mode.RawCStr -> "c"
        }
}
