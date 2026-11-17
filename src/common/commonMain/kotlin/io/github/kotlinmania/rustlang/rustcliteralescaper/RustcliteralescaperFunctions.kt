package io.github.kotlinmania.rustlang.rustcliteralescaper

import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroCharInner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.core.str.iter.Chars

/**
 * Check a raw string literal for validity
 *
 * Takes the contents of a raw string literal (without quotes)
 * and produces a sequence of characters or errors,
 * which are returned by invoking `callback`.
 * NOTE: Does no escaping, but produces errors for bare carriage return ('\r').
 */
public fun checkRawStr(src: String, callback: (IntRange, Result<Char, EscapeError>) -> Unit) {
    String.CheckRaw(src, callback)
}

/**
 * Check a raw byte string literal for validity
 *
 * Takes the contents of a raw byte string literal (without quotes)
 * and produces a sequence of bytes or errors,
 * which are returned by invoking `callback`.
 * NOTE: Does no escaping, but produces errors for bare carriage return ('\r').
 */
public fun checkRawByteStr(src: String, callback: (IntRange, Result<UByte, EscapeError>) -> Unit) {
    List.checkRaw(src, callback)
}

/**
 * Check a raw C string literal for validity
 *
 * Takes the contents of a raw C string literal (without quotes)
 * and produces a sequence of characters or errors,
 * which are returned by invoking `callback`.
 * NOTE: Does no escaping, but produces errors for bare carriage return ('\r').
 */
public fun checkRawCStr(src: String, callback: (IntRange, Result<NonZero<Char, NonZeroCharInner>, EscapeError>) -> Unit) {
    CStr.CheckRaw(src, callback)
}

/**
 * Turn an ascii char into a byte
 */
public fun char2byte(c: Char): Result<UByte, EscapeError> {
    // do NOT do: c.tryInto().okOr(EscapeError.NonAsciiCharInByte)
    return if (c.isAscii()) {
        Result.Ok<UByte, EscapeError>(c.toUByte())
    } else {
        Result.Err<UByte, EscapeError>(EscapeError.NonAsciiCharInByte)
    }
}

/**
 * Unescape a char literal
 *
 * Takes the contents of a char literal (without quotes),
 * and returns an unescaped char or an error.
 */
public fun unescapeChar(src: String): Result<Char, EscapeError> = String.unescapeSingle(src.chars())

/**
 * Unescape a byte literal
 *
 * Takes the contents of a byte literal (without quotes),
 * and returns an unescaped byte or an error.
 */
public fun unescapeByte(src: String): Result<UByte, EscapeError> = List.unescapeSingle(src.chars())

/**
 * Unescape a string literal
 *
 * Takes the contents of a string literal (without quotes)
 * and produces a sequence of escaped characters or errors,
 * which are returned by invoking `callback`.
 */
public fun unescapeStr(src: String, callback: (IntRange, Result<Char, EscapeError>) -> Unit) {
    String.Unescape(src, callback)
}

/**
 * Unescape a byte string literal
 *
 * Takes the contents of a byte string literal (without quotes)
 * and produces a sequence of escaped bytes or errors,
 * which are returned by invoking `callback`.
 */
public fun unescapeByteStr(src: String, callback: (IntRange, Result<UByte, EscapeError>) -> Unit) {
    List.unescape(src, callback)
}

/**
 * Unescape a C string literal
 *
 * Takes the contents of a C string literal (without quotes)
 * and produces a sequence of escaped MixedUnits or errors,
 * which are returned by invoking `callback`.
 */
public fun unescapeCStr(src: String, callback: (IntRange, Result<MixedUnit, EscapeError>) -> Unit) {
    CStr.Unescape(src, callback)
}

// single use in Unescape.unescape1

/**
 * Interpret a non-nul ASCII escape
 *
 * Parses the character of an ASCII escape (except nul) without the leading backslash.
 */
public fun simpleEscape(c: Char): Result<NonZero<UByte, NonZeroU8Inner>, Char> {
    // Previous character was '\\', unescape what follows.
    return Result.Ok<NonZero<UByte, NonZeroU8Inner>, Char>((NonZero.new<NonZeroU8Inner, UByte>(when (c) {
        '"' -> 34u
        'n' -> 10u
        'r' -> 13u
        't' -> 9u
        '\\' -> 92u
        '\'' -> 39u
        else -> when (val tryResult = Result.Err<NonZero<UByte, NonZeroU8Inner>, Char>(c)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<NonZero<UByte, NonZeroU8Inner>, Char>(tryResult.error) }
    }))!!)
}

// single use in Unescape.unescape1

/**
 * Interpret a hexadecimal escape
 *
 * Parses the two hexadecimal characters of a hexadecimal escape without the leading r"\x".
 */
public fun hexEscape(chars: Any): Result<UByte, EscapeError> {
    val hi = when (val tryResult = (chars.next()?.let { Result.Ok<UByte, EscapeError>(it) } ?: Result.Err<UByte, EscapeError>(EscapeError.TooShortHexEscape))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UByte, EscapeError>(tryResult.error) }
    val hi1 = when (val tryResult = hi.toDigit(16u).okOr(EscapeError.InvalidCharInHexEscape)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UByte, EscapeError>(tryResult.error) }
    val lo = when (val tryResult = (chars.next()?.let { Result.Ok<UByte, EscapeError>(it) } ?: Result.Err<UByte, EscapeError>(EscapeError.TooShortHexEscape))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UByte, EscapeError>(tryResult.error) }
    val lo1 = when (val tryResult = lo.toDigit(16u).okOr(EscapeError.InvalidCharInHexEscape)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UByte, EscapeError>(tryResult.error) }
    return Result.Ok<UByte, EscapeError>((hi1 * 16 + lo1).toUByte())
}

// single use in Unescape.unescape1

/**
 * Interpret a unicode escape
 *
 * Parse the braces with hexadecimal characters (and underscores) part of a unicode escape.
 * This r"(...)" normally comes after r"\u" and cannot start with an underscore.
 */
public fun unicodeEscape(chars: Any): Result<UInt, EscapeError> {
    if (chars.next() != '{') {
        return Result.Err<UInt, EscapeError>(EscapeError.NoBraceInUnicodeEscape)
    }
    // First character must be a hexadecimal digit.
    var value: UInt =
        when (val matchVal = when (val tryResult = (chars.next()?.let { Result.Ok(it) } ?: Result.Err(EscapeError.UnclosedUnicodeEscape))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UInt, EscapeError>(tryResult.error) }) {
            '_' -> { return Result.Err<UInt, EscapeError>(EscapeError.LeadingUnderscoreUnicodeEscape) }
            '}' -> { return Result.Err<UInt, EscapeError>(EscapeError.EmptyUnicodeEscape) }
            else -> {
                val c = matchVal
                when (val tryResult = c.toDigit(16u).okOr(EscapeError.InvalidCharInUnicodeEscape)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UInt, EscapeError>(tryResult.error) }
            }
        }
    // First character is valid, now parse the rest of the number
    // and closing brace.
    var nDigits = 1
    while (true) {
        when (val matchVal1 = chars.next()) {
            null -> { return Result.Err<UInt, EscapeError>(EscapeError.UnclosedUnicodeEscape) }
            '_' -> { continue }
            '}' -> {
                // Incorrect syntax has higher priority for error reporting
                // than unallowed value for a literal.
                return if (nDigits > 6) {
                    Result.Err<UInt, EscapeError>(EscapeError.OverlongUnicodeEscape)
                } else {
                    Result.Ok<UInt, EscapeError>(value)
                }
            }
            else -> {
                val c1 = matchVal1
                val digit: UInt = when (val tryResult = c1.toDigit(16u).okOr(EscapeError.InvalidCharInUnicodeEscape)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<UInt, EscapeError>(tryResult.error) }
                nDigits += 1
                if (nDigits > 6) {
                    // Stop updating value since we're sure that it's incorrect already.
                    continue
                }
                value = value * 16u + digit
            }
        }
    }
}

// single use in Unescape.unescape

/**
 * Interpret a string continuation escape (https://doc.rust-lang.org/reference/expressions/literal-expr.html#string-continuation-escapes)
 *
 * Skip ASCII whitespace, except for the formfeed character
 * (see [this issue](https://github.com/rust-lang/rust/issues/136600)).
 * Warns on unescaped newline and following non-ASCII whitespace.
 */
public fun skipAsciiWhitespace(chars: Chars, start: Int, callback: (IntRange, EscapeError) -> Unit) {
    var chars1 = chars
    val rest = chars1.asStr()
    val firstNonSpace = (rest.bytes().indexOfFirst({ b -> b != 32u && b != 9u && b != 10u && b != 13u }).takeIf { it >= 0 } ?: rest.length)
    val (space, rest1) = rest.splitAt(firstNonSpace)
    // backslash newline adds 2 bytes
    val end = start + 2 + firstNonSpace
    if (space.contains('\n')) {
        callback(start until end, EscapeError.MultipleSkippedLinesWarning)
    }
    chars1 = rest1.chars()
    when (val matchVal = chars1.clone().next()) {
        null -> {}
        else -> {
            val c = matchVal
            if (c.isWhitespace()) {
                // for error reporting, include the character that was not skipped in the span
                callback(start until end + c.lenUtf8(), EscapeError.UnskippedWhitespaceWarning)
            }
        }
    }
}

/**
 * Check a literal only for errors
 *
 * Takes the contents of a literal (without quotes)
 * and produces a sequence of only errors,
 * which are returned by invoking `errorCallback`.
 *
 * NB Does not produce any output other than errors
 */
public fun checkForErrors(src: String, mode: Mode, errorCallback: (IntRange, EscapeError) -> Unit) {
    when (mode) {
        Mode.Char -> {
            val chars = src.chars()
            when (val matchVal = String.unescapeSingle(chars)) {
                is Result.Err<*, *> -> {
                    val e = matchVal.error
                    errorCallback(0 until (src.length - chars.asStr().length), e)
                }
                else -> {}
            }
        }
        Mode.Byte -> {
            val chars1 = src.chars()
            when (val matchVal1 = List.unescapeSingle(chars1)) {
                is Result.Err<*, *> -> {
                    val e1 = matchVal1.error
                    errorCallback(0 until (src.length - chars1.asStr().length), e1)
                }
                else -> {}
            }
        }
        Mode.Str -> unescapeStr(src, { range, res ->
            if (res is Result.Err<*, *>) {
                val e2 = res.error
                errorCallback(range, e2)
            }
        })
        Mode.ByteStr -> unescapeByteStr(src, { range, res ->
            if (res is Result.Err<*, *>) {
                val e2 = res.error
                errorCallback(range, e2)
            }
        })
        Mode.CStr -> unescapeCStr(src, { range, res ->
            if (res is Result.Err<*, *>) {
                val e2 = res.error
                errorCallback(range, e2)
            }
        })
        Mode.RawStr -> checkRawStr(src, { range, res ->
            if (res is Result.Err<*, *>) {
                val e2 = res.error
                errorCallback(range, e2)
            }
        })
        Mode.RawByteStr -> checkRawByteStr(src, { range, res ->
            if (res is Result.Err<*, *>) {
                val e2 = res.error
                errorCallback(range, e2)
            }
        })
        Mode.RawCStr -> checkRawCStr(src, { range, res ->
            if (res is Result.Err<*, *>) {
                val e2 = res.error
                errorCallback(range, e2)
            }
        })
    }
}

public fun checkRawChar2rawUnitString(c: Char): Result<Char, EscapeError> = Result.Ok<Char, EscapeError>(c)

public fun checkRawChar2rawUnitUByteSlice(c: Char): Result<UByte, EscapeError> = char2byte(c)

public fun checkRawChar2rawUnitCStr(c: Char): Result<NonZero<Char, NonZeroCharInner>, EscapeError> = ((NonZero.new<NonZeroCharInner, Char>(c))?.let { Result.Ok<NonZero<Char, NonZeroCharInner>, EscapeError>(it) } ?: Result.Err<NonZero<Char, NonZeroCharInner>, EscapeError>(EscapeError.NulInCStr))

public val ZERO_RESULT: Result<Char, EscapeError> = Result.Ok('\u0000')

public fun unescapeNonzeroByte2unitString(b: NonZero<UByte, NonZeroU8Inner>): Char = b.get().into()

public fun unescapeChar2unitString(c: Char): Result<Char, EscapeError> = Result.Ok<Char, EscapeError>(c)

public fun unescapeHex2unitString(b: UByte): Result<Char, EscapeError> =
    if (b.isAscii()) {
        Result.Ok<Char, EscapeError>(b.toInt().toChar())
    } else {
        Result.Err<Char, EscapeError>(EscapeError.OutOfRangeHexEscape)
    }

public fun unescapeUnicode2unitString(r: Result<Char, EscapeError>): Result<Char, EscapeError> = r

public val ZERO_RESULT1: Result<UByte, EscapeError> = Result.Ok(0u)

public fun unescapeNonzeroByte2unitUByteSlice(b: NonZero<UByte, NonZeroU8Inner>): UByte = b.get()

public fun unescapeChar2unitUByteSlice(c: Char): Result<UByte, EscapeError> = char2byte(c)

public fun unescapeHex2unitUByteSlice(b: UByte): Result<UByte, EscapeError> = Result.Ok<UByte, EscapeError>(b)

public fun unescapeUnicode2unitUByteSlice(r: Result<Char, EscapeError>): Result<UByte, EscapeError> = Result.Err<UByte, EscapeError>(EscapeError.UnicodeEscapeInByte)

public val ZERO_RESULT2: Result<MixedUnit, EscapeError> = Result.Err(EscapeError.NulInCStr)

public fun unescapeNonzeroByte2unitCStr(b: NonZero<UByte, NonZeroU8Inner>): MixedUnit = b.into()

public fun unescapeChar2unitCStr(c: Char): Result<MixedUnit, EscapeError> = c.tryInto()

public fun unescapeHex2unitCStr(byte: UByte): Result<MixedUnit, EscapeError> = byte.tryInto()

public fun unescapeUnicode2unitCStr(r: Result<Char, EscapeError>): Result<MixedUnit, EscapeError> {
    return CStr.char2unit(when (val tryResult = r) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<MixedUnit, EscapeError>(tryResult.error) })
}
