// port-lint: source char/methods.rs
package io.github.kotlinmania.rustlang.core.char.methods

import io.github.kotlinmania.rustlang.core.char.CaseMappingIter
import io.github.kotlinmania.rustlang.core.char.CharCase
import io.github.kotlinmania.rustlang.core.char.EscapeDebug
import io.github.kotlinmania.rustlang.core.char.EscapeDefault
import io.github.kotlinmania.rustlang.core.char.EscapeUnicode
import io.github.kotlinmania.rustlang.core.char.MAX_ONE_B
import io.github.kotlinmania.rustlang.core.char.MAX_THREE_B
import io.github.kotlinmania.rustlang.core.char.MAX_TWO_B
import io.github.kotlinmania.rustlang.core.char.TAG_CONT
import io.github.kotlinmania.rustlang.core.char.TAG_FOUR_B
import io.github.kotlinmania.rustlang.core.char.TAG_THREE_B
import io.github.kotlinmania.rustlang.core.char.TAG_TWO_B
import io.github.kotlinmania.rustlang.core.char.ToCasefold
import io.github.kotlinmania.rustlang.core.char.ToLowercase
import io.github.kotlinmania.rustlang.core.char.ToTitlecase
import io.github.kotlinmania.rustlang.core.char.ToUppercase
import io.github.kotlinmania.rustlang.core.char.decode.DecodeUtf16
import io.github.kotlinmania.rustlang.core.char.decode.decodeUtf16
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.panic.constPanic
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8UncheckedMut
import io.github.kotlinmania.rustlang.core.unicode.Alphabetic
import io.github.kotlinmania.rustlang.core.unicode.CaseIgnorable
import io.github.kotlinmania.rustlang.core.unicode.Cf
import io.github.kotlinmania.rustlang.core.unicode.CnPlanes03
import io.github.kotlinmania.rustlang.core.unicode.DefaultIgnorableCodePoint
import io.github.kotlinmania.rustlang.core.unicode.GraphemeExtend
import io.github.kotlinmania.rustlang.core.unicode.Lowercase
import io.github.kotlinmania.rustlang.core.unicode.Lt
import io.github.kotlinmania.rustlang.core.unicode.UNICODE_VERSION
import io.github.kotlinmania.rustlang.core.unicode.Uppercase
import io.github.kotlinmania.rustlang.core.unicode.WhiteSpace
import io.github.kotlinmania.rustlang.core.unicode.XidContinue
import io.github.kotlinmania.rustlang.core.unicode.XidStart
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toCasefold
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toLower
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toTitle
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toUpper

// impl char ()

/**
 * The lowest valid code point a `char` can have, `'\0'`.
 *
 * Unlike integer types, `char` actually has a gap in the middle,
 * meaning that the range of possible `char`s is smaller than you
 * might expect. Ranges of `char` will automatically hop this gap
 * for you:
 *
 * ```kotlin
 * val dist = UInt.from(Char.MAX_VALUE) - UInt.from(Char.MIN_VALUE)
 * val size = (Char.MIN_VALUE..Char.MAX_VALUE).count().toUInt()
 * check(size < dist)
 * ```
 *
 * Despite this gap, the `MIN` and [Max] values can be used as bounds for
 * all `char` values.
 *
 * [Max]: char.Max
 *
 * # Examples
 *
 * ```kotlin
 * val c: Char = somethingWhichReturnsChar()
 * check(Char.MIN_VALUE <= c)
 * val valueAtMin = UInt.from(Char.MIN_VALUE)
 * check(Char.fromU32(valueAtMin) == '\u0000')
 * ```
 */
public val MIN: Char = '\u0000'

/**
 * The highest valid code point a `char` can have, `'\u(10FFFF)'`.
 *
 * Unlike integer types, `char` actually has a gap in the middle,
 * meaning that the range of possible `char`s is smaller than you
 * might expect. Ranges of `char` will automatically hop this gap
 * for you:
 *
 * ```kotlin
 * val dist = UInt.from(Char.MAX_VALUE) - UInt.from(Char.MIN_VALUE)
 * val size = (Char.MIN_VALUE..Char.MAX_VALUE).count().toUInt()
 * check(size < dist)
 * ```
 *
 * Despite this gap, the [Min] and `MAX` values can be used as bounds for
 * all `char` values.
 *
 * [Min]: char.Min
 *
 * # Examples
 *
 * ```kotlin
 * val c: Char = somethingWhichReturnsChar()
 * check(c <= Char.MAX_VALUE)
 * val valueAtMax = UInt.from(Char.MAX_VALUE)
 * check(Char.fromU32(valueAtMax) == 0x10ffff)
 * check(Char.fromU32(valueAtMax + 1) == null)
 * ```
 */
public val MAX: Char = 0x10ffff

/**
 * The maximum number of bytes required to [encode](char.encodeUtf8) a `char` to
 * UTF-8 encoding.
 */
public val MAX_LEN_UTF8: Int = 4

/**
 * The maximum number of two-byte units required to [encode](char.encodeUtf16) a `char`
 * to UTF-16 encoding.
 */
public val MAX_LEN_UTF16: Int = 2

/**
 * `U + FFFD REPLACEMENT CHARACTER` (�) is used in Unicode to represent a
 * decoding error.
 *
 * It can occur, for example, when giving ill-formed UTF-8 bytes to
 * [`String.fromUtf8Lossy`](../std/string/struct.String.html#method.fromUtf8Lossy).
 */
public val REPLACEMENT_CHARACTER: Char = '\ufffd'

/**
 * The version of [Unicode](https://www.unicode.org/) that the Unicode parts of
 * `char` and `String` methods are based on.
 *
 * New versions of Unicode are released regularly, and subsequently all methods
 * in the standard library depending on Unicode are updated. Therefore, the
 * behavior of some `char` and `String` methods, and the value of this constant,
 * change over time (within the boundaries of Unicode's [stability policies]).
 * This is *not* considered to be a breaking change.
 *
 * [stability policies]: https://www.unicode.org/policies/stabilityPolicy.html
 *
 * The version numbering scheme is explained in
 * [Section 3.1 (Version Numbering)] of the Unicode Standard.
 *
 * [Section 3.1 (Version Numbering)]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G49512
 */
public val UNICODE_VERSION: Triple<UByte, UByte, UByte> = UNICODE_VERSION

/**
 * Creates an iterator over the native endian UTF-16 encoded code points in `iter`,
 * returning unpaired surrogates as `Err`s.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // 𝄞mus<invalid>ic<invalid>
 * val v = listOf(0xD834, 0xDD1E, 0x006d, 0x0075, 0x0073, 0xDD1E, 0x0069, 0x0063, 0xD834)
 * check(Char.decodeUtf16(v).map({ r -> r.mapErr({ e -> e.unpairedSurrogate() }) }).collect<Vec>() == mutableListOf(Result.success(0x1d11e), Result.success('m'), Result.success('u'), Result.success('s'), Result.failure(0xDD1E), Result.success('i'), Result.success('c'), Result.failure(0xD834)))
 * ```
 *
 * A lossy decoder can be obtained by replacing `Err` results with the replacement character:
 *
 * ```kotlin
 * // 𝄞mus<invalid>ic<invalid>
 * val v = listOf(0xD834, 0xDD1E, 0x006d, 0x0075, 0x0073, 0xDD1E, 0x0069, 0x0063, 0xD834)
 * check(Char.decodeUtf16(v).map({ r -> r.unwrapOr(Char.REPLACEMENT_CHARACTER) }).joinToString("") == "𝄞mus�ic�")
 * ```
 */
public fun <I : Iterable<UShort>> Char.Companion.decodeUtf16(iter: I): DecodeUtf16<IntoIter> = DecodeUtf16(iter)

/**
 * Converts a `UInt` to a `char`.
 *
 * Note that all `char`s are valid [u32]s, and can be cast to one with
 * [`asTarget`](../std/keyword.as.html):
 *
 * ```kotlin
 * val c = 0x1f4af
 * val i = c.toUInt()
 * check(128175u == i)
 * ```
 *
 * However, the reverse is not true: not all valid [u32]s are valid
 * `char`s. `fromU32()` will return `None` if the input is not a valid value
 * for a `char`.
 *
 * For an unchecked version of this function which ignores these checks, see
 * [fromU32Unchecked].
 *
 * [fromU32Unchecked]: #method.fromU32Unchecked
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val c = Char.fromU32(0x2764)
 * check('\u2764' == c)
 * ```
 *
 * Returning `None` when the input is not a valid `char`:
 *
 * ```kotlin
 * val c = Char.fromU32(0x110000)
 * check(null == c)
 * ```
 */
public fun Char.Companion.fromU32(i: UInt): Char? = io.github.kotlinmania.rustlang.core.char.convert.fromU32(i)

/**
 * Converts a `UInt` to a `char`, ignoring validity.
 *
 * Note that all `char`s are valid [u32]s, and can be cast to one with
 * `as`:
 *
 * ```kotlin
 * val c = 0x1f4af
 * val i = c.toUInt()
 * check(128175u == i)
 * ```
 *
 * However, the reverse is not true: not all valid [u32]s are valid
 * `char`s. `fromU32Unchecked()` will ignore this, and blindly cast to
 * `char`, possibly creating an invalid one.
 *
 * # Safety
 *
 * This function is unchecked, as it may construct invalid `char` values.
 *
 * For a safe version of this function, see the [fromU32] function.
 *
 * [fromU32]: #method.fromU32
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val c = Char.fromU32Unchecked(0x2764)
 * check('\u2764' == c)
 * ```
 */
public fun Char.Companion.fromU32Unchecked(i: UInt): Char {
    // SAFETY: the safety contract must be upheld by the caller.
    return io.github.kotlinmania.rustlang.core.char.convert.fromU32Unchecked(i)
}

/**
 * Converts a digit in the given radix to a `char`.
 *
 * A `radix` here is sometimes also called a `base`. A radix of two
 * indicates a binary number, a radix of ten, decimal, and a radix of
 * sixteen, hexadecimal, to give some common values. Arbitrary
 * radices are supported.
 *
 * `fromDigit()` will return `None` if the input is not a digit in
 * the given radix.
 *
 * # Panics
 *
 * Panics if given a radix larger than 36.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val c = Char.fromDigit(4, 10)
 * check('4' == c)
 * // Decimal 11 is a single digit in base 16
 * val c1 = Char.fromDigit(11, 16)
 * check('b' == c1)
 * ```
 *
 * Returning `None` when the input is not a digit:
 *
 * ```kotlin
 * val c = Char.fromDigit(20, 10)
 * check(null == c)
 * ```
 *
 * Passing a large radix, causing a panic:
 *
 * ```kotlin
 * // this panics
 * val c = Char.fromDigit(1, 37)
 * ```
 */
public fun Char.Companion.fromDigit(num: UInt, radix: UInt): Char? = io.github.kotlinmania.rustlang.core.char.convert.fromDigit(num, radix)

/**
 * Checks if a `char` is a digit in the given radix.
 *
 * A `radix` here is sometimes also called a `base`. A radix of two
 * indicates a binary number, a radix of ten, decimal, and a radix of
 * sixteen, hexadecimal, to give some common values. Arbitrary
 * radices are supported.
 *
 * Compared to [isNumeric], this function only recognizes the characters
 * `0 - 9`, `a - z` and `A - Z`.
 *
 * `Digit` is defined to be only the following characters:
 *
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * For a more comprehensive understanding of `digit`, see [isNumeric].
 *
 * [isNumeric]: #method.isNumeric
 *
 * # Panics
 *
 * Panics if given a radix smaller than 2 or larger than 36.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('1'.isDigit(10))
 * check('f'.isDigit(16))
 * check(!'f'.isDigit(10))
 * ```
 *
 * Passing a large radix, causing a panic:
 *
 * ```kotlin
 * // this panics
 * '1'.isDigit(37)
 * ```
 *
 * Passing a small radix, causing a panic:
 *
 * ```kotlin
 * // this panics
 * '1'.isDigit(1)
 * ```
 */
public fun Char.isDigit(radix: UInt): Boolean = this.toDigit(radix) != null

/**
 * Converts a `char` to a digit in the given radix.
 *
 * A `radix` here is sometimes also called a `base`. A radix of two
 * indicates a binary number, a radix of ten, decimal, and a radix of
 * sixteen, hexadecimal, to give some common values. Arbitrary
 * radices are supported.
 *
 * `Digit` is defined to be only the following characters:
 *
 * * `0 - 9`
 * * `a - z`
 * * `A - Z`
 *
 * # Errors
 *
 * Returns `None` if the `char` does not refer to a digit in the given radix.
 *
 * # Panics
 *
 * Panics if given a radix smaller than 2 or larger than 36.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('1'.toDigit(10u) == 1)
 * check('f'.toDigit(16u) == 15)
 * ```
 *
 * Passing a non-digit results in failure:
 *
 * ```kotlin
 * check('f'.toDigit(10u) == null)
 * check('z'.toDigit(16u) == null)
 * ```
 *
 * Passing a large radix, causing a panic:
 *
 * ```kotlin
 * // this panics
 * '1'.toDigit(37u)
 * ```
 * Passing a small radix, causing a panic:
 *
 * ```kotlin
 * // this panics
 * '1'.toDigit(1u)
 * ```
 */
public fun Char.toDigit(radix: UInt): UInt? {
    check(radix >= 2u && radix <= 36u) { "to_digit: invalid radix -- radix must be in the range 2 to 36 inclusive" }
    // check radix to remove letter handling code when radix is a known constant
    val value =
        if (this > '9' && radix > 10u) {
            // mask to convert ASCII letters to uppercase
            val TO_UPPERCASE_MASK: UInt = 0x20u.inv()
            // Converts an ASCII letter to its corresponding integer value:
            // A-Z => 10-35, a-z => 10-35. Other characters produce values >= 36.
            //
            // Add Overflow Safety:
            // By applying the mask after the subtraction, the first addendum is
            // constrained such that it never exceeds u32.Max - 0x20.
            (((this.toUInt()) - ('A'.code.toUInt())) and TO_UPPERCASE_MASK) + 10u
        } else {
            // convert digit to value, non-digits wrap to values > 36
            ((this.toUInt()) - ('0'.code.toUInt()))
        }
    // Note(const-Note): once thenSome is const fn, use it here
    return if (value < radix) {
        value
    } else {
        null
    }
}

/**
 * Returns an iterator that yields the hexadecimal Unicode escape of a
 * character as `char`s.
 *
 * This will escape characters with the Rust syntax of the form
 * `\u(NNNNNN)` where `NNNNNN` is a hexadecimal representation.
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in '\u2764'.escapeUnicode()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println("${'\u2764'.escapeUnicode()}")
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("\\u{2764}")
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * check('\u2764'.escapeUnicode().toString() == "\\u{2764}")
 * ```
 */
public fun Char.escapeUnicode(): EscapeUnicode = EscapeUnicode.new(this)

/**
 * An extended version of `escapeDebug` that optionally permits escaping
 * single quotes and double quotes. This allows escaping single quotes in
 * characters, and double quotes in strings.
 */
public fun Char.escapeDebugExt(args: EscapeDebugExtArgs): EscapeDebug =
    when {
        // Special escapes
        this == '"' && args.escapeDoubleQuote -> EscapeDebug.backslash(Char.QuotationMark)
        this == '\'' && args.escapeSingleQuote -> EscapeDebug.backslash(Char.Apostrophe)
        this == '\\' -> EscapeDebug.backslash(Char.ReverseSolidus)
        this == '\n' -> EscapeDebug.backslash(Char.SmallN)
        this == '\t' -> EscapeDebug.backslash(Char.SmallT)
        this == '\r' -> EscapeDebug.backslash(Char.SmallR)
        this == '\u0000' -> EscapeDebug.backslash(Char.Digit0)
        // ASCII fast path,
        // plus U+FF9E HALFWIDTH KATAKANA VOICED SOUND MARK
        // and U+FF9F HALFWIDTH KATAKANA SEMI-VOICED SOUND MARK
        // which should not be escaped despite being grapheme extenders.
        this in ' '..'~' || this == '\uff9e' || this == '\uff9f' -> EscapeDebug.printable(this)
        this.isControl() || this.isPrivateUse() || this.isWhitespace() || this.isGraphemeExtender() || this.isDefaultIgnorable() || this.isFormatControl() || !this.isAssigned() -> {
            EscapeDebug.unicode(this)
        }
        else -> EscapeDebug.printable(this)
    }

/**
 * Returns an iterator that yields the literal escape code of a character
 * as `char`s.
 *
 * This will escape the characters similar to the [`Debug`](core.fmt.Debug) implementations
 * of `String` or `char`.
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in '\n'.escapeDebug()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println("${'\n'.escapeDebug()}")
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("\\n")
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * check('\n'.escapeDebug().toString() == "\\n")
 * ```
 */
public fun Char.escapeDebug(): EscapeDebug = this.escapeDebugExt(EscapeDebugExtArgs.ESCAPE_ALL)

/**
 * Returns an iterator that yields the literal escape code of a character
 * as `char`s.
 *
 * The default is chosen with a bias toward producing literals that are
 * legal in a variety of languages, including C++11 and similar C-family
 * languages. The exact rules are:
 *
 * * Tab is escaped as `\t`.
 * * Carriage return is escaped as `\r`.
 * * Line feed is escaped as `\n`.
 * * Single quote is escaped as `\'`.
 * * Double quote is escaped as `\"`.
 * * Backslash is escaped as `\\`.
 * * Any character in the printable ASCII' range `0x20` .. `0x7e`
 *   inclusive is not escaped.
 * * All other characters are given hexadecimal Unicode escapes; see
 *   [escapeUnicode].
 *
 * [escapeUnicode]: #method.escapeUnicode
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for c in '"'.escapeDefault() { print("{c}"); } println();
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println("{}", '"'.escapeDefault());
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("\\\"")
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * check('"'.escapeDefault().toString() == "\\\"")
 * ```
 */
public fun Char.escapeDefault(): EscapeDefault =
    when (this) {
        '\t' -> EscapeDefault.backslash(Char.SmallT)
        '\r' -> EscapeDefault.backslash(Char.SmallR)
        '\n' -> EscapeDefault.backslash(Char.SmallN)
        '\\', '\'', '"' -> { EscapeDefault.backslash(this.asAscii()!!) }
        in ' '..'~' -> EscapeDefault.printable(this.asAscii()!!)
        else -> EscapeDefault.unicode(this)
    }

/**
 * Returns the number of bytes this `char` would need if encoded in UTF-8.
 *
 * That number of bytes is always between 1 and 4, inclusive.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val len = 'A'.lenUtf8()
 * check(len == 1)
 * val len1 = '\u00df'.lenUtf8()
 * check(len1 == 2)
 * val len2 = '\u211d'.lenUtf8()
 * check(len2 == 3)
 * val len3 = 0x1f4a3.lenUtf8()
 * check(len3 == 4)
 * ```
 *
 * The `String` type guarantees that its contents are UTF-8, and so we can compare the length it
 * would take if each code point was represented as a `char` vs in the `String` itself:
 *
 * ```kotlin
 * // as chars
 * val eastern = '\u6771'
 * val capital = '\u4eac'
 * // both can be represented as three bytes
 * check(3 == eastern.lenUtf8())
 * check(3 == capital.lenUtf8())
 * // as a &str, these two are encoded in UTF-8
 * val tokyo = "東京"
 * val len = eastern.lenUtf8() + capital.lenUtf8()
 * // we can see that they take six bytes total...
 * check(6 == tokyo.len())
 * // ... just like the &str
 * check(len == tokyo.len())
 * ```
 */
public fun Char.lenUtf8(): Int = lenUtf8(this.toUInt())

/**
 * Returns the number of 16-bit code units this `char` would need if
 * encoded in UTF-16.
 *
 * That number of code units is always either 1 or 2, for unicode scalar values in
 * the [basic multilingual plane] or [supplementary planes] respectively.
 *
 * See the documentation for [lenUtf8] for more explanation of this
 * concept. This function is a mirror, but for UTF-16 instead of UTF-8.
 *
 * [basic multilingual plane]: http://www.unicode.org/glossary/#basicMultilingualPlane
 * [supplementary planes]: http://www.unicode.org/glossary/#supplementaryPlanes
 * [lenUtf8]: #method.lenUtf8
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val n = '\u00df'.lenUtf16()
 * check(n == 1)
 * val len = 0x1f4a3.lenUtf16()
 * check(len == 2)
 * ```
 */
public fun Char.lenUtf16(): Int = lenUtf16(this.toUInt())

/**
 * Encodes this character as UTF-8 into the provided byte buffer,
 * and then returns the subslice of the buffer that contains the encoded character.
 *
 * # Panics
 *
 * Panics if the buffer is not large enough.
 * A buffer of length four is large enough to encode any `char`.
 *
 * # Examples
 *
 * In both of these examples, `ß` takes two bytes to encode.
 *
 * ```kotlin
 * var b = MutableList(2) { 0 }
 * val result = '\u00df'.encodeUtf8(b)
 * check(result == "ß")
 * check(result.len() == 2)
 * ```
 *
 * A buffer that's too small:
 *
 * ```kotlin
 * var b = MutableList(1) { 0 }
 * // this panics
 * '\u00df'.encodeUtf8(b)
 * ```
 */
public fun Char.encodeUtf8(dst: MutableList<UByte>): String {
    // SAFETY: `char` is not a surrogate, so this is valid UTF-8.
    return fromUtf8UncheckedMut(encodeUtf8Raw(this.toUInt(), dst))
}

/**
 * Encodes this character as native endian UTF-16 into the provided `UShort` buffer,
 * and then returns the subslice of the buffer that contains the encoded character.
 *
 * # Panics
 *
 * Panics if the buffer is not large enough.
 * A buffer of length 2 is large enough to encode any `char`.
 *
 * # Examples
 *
 * In both of these examples, '𝕊' takes two `UShort`s to encode.
 *
 * ```kotlin
 * var b = MutableList(2) { 0 }
 * val result = 0x1d54a.encodeUtf16(b)
 * check(result.len() == 2)
 * ```
 *
 * A buffer that's too small:
 *
 * ```kotlin
 * var b = MutableList(1) { 0 }
 * // this panics
 * 0x1d54a.encodeUtf16(b)
 * ```
 */
public fun Char.encodeUtf16(dst: MutableList<UShort>): MutableList<UShort> = encodeUtf16Raw(this.toUInt(), dst)

/**
 * Returns `true` if this `char` has the `Alphabetic` property.
 *
 * `Alphabetic` is [described] in Chapter 4 (Character Properties) of the Unicode Standard, and
 * [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-4/#G32524
 * [specified]: https://www.unicode.org/reports/tr44/#Alphabetic
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('a'.isAlphabetic())
 * check('\u4eac'.isAlphabetic())
 * val c = 0x1f49d
 * // love is many things, but it is not alphabetic
 * check(!c.isAlphabetic())
 * ```
 */
public fun Char.isAlphabetic(): Boolean =
    when (this) {
        in 'a'..'z', in 'A'..'Z' -> { true }
        in '\u0000'..'\u00a9' -> false
        else -> Alphabetic(this)
    }

/**
 * Returns `true` if this `char` has the `Cased` property.
 * A character is cased if and only if it is uppercase, lowercase, or titlecase.
 *
 * `Cased` is [described] in Chapter 3 (Character Properties) of the Unicode Standard and
 * [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G44595
 * [specified]: https://www.unicode.org/reports/tr44/#Cased
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     check('A'.isCased())
 *     check('a'.isCased())
 *     check(!'\u4eac'.isCased())
 * }
 * ```
 */
public fun Char.isCased(): Boolean =
    when (this) {
        in 'a'..'z', in 'A'..'Z' -> { true }
        in '\u0000'..'\u00a9' -> false
        else -> Lowercase(this) || Uppercase(this) || Lt(this)
    }

/**
 * Returns the case of this character:
 * [`Some`][CharCase.Upper] if [`this.isUppercase`][char.isUppercase],
 * [`Some`][CharCase.Lower] if [`this.isLowercase`][char.isLowercase],
 * [`Some`][CharCase.Title] if [`this.isTitlecase`][char.isTitlecase], and
 * `None` if [`this.isCased`][char.isCased].
 *
 * # Examples
 *
 * ```kotlin
 * import core.char.CharCase
 *
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     check('a'.case() == CharCase.Lower)
 *     check('\u03b4'.case() == CharCase.Lower)
 *     check('A'.case() == CharCase.Upper)
 *     check('\u0394'.case() == CharCase.Upper)
 *     check('\u01c5'.case() == CharCase.Title)
 *     check('\u4e2d'.case() == null)
 * }
 * ```
 */
public fun Char.case(): CharCase? =
    when {
        this in 'a'..'z' -> CharCase.Lower
        this in 'A'..'Z' -> CharCase.Upper
        this in '\u0000'..'\u00a9' -> null
        Lowercase(this) -> CharCase.Lower
        Uppercase(this) -> CharCase.Upper
        Lt(this) -> CharCase.Title
        else -> null
    }

/**
 * Returns `true` if this `char` has the `Lowercase` property.
 *
 * `Lowercase` is [described] in Chapter 4 (Character Properties) of the Unicode Standard, and
 * [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-4/#G136255
 * [specified]: https://www.unicode.org/reports/tr44/#Lowercase
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('a'.isLowercase())
 * check('\u03b4'.isLowercase())
 * check(!'A'.isLowercase())
 * check(!'\u0394'.isLowercase())
 * // The various Chinese scripts and punctuation do not have case, and so:
 * check(!'\u4e2d'.isLowercase())
 * check(!' '.isLowercase())
 * ```
 *
 * In a const context:
 *
 * ```kotlin
 * val CAPITAL_DELTA_IS_LOWERCASE: Boolean = '\u0394'.isLowercase()
 * check(CAPITAL_DELTA_IS_LOWERCASE.inv())
 * ```
 */
public fun Char.isLowercase(): Boolean =
    when (this) {
        in 'a'..'z' -> { true }
        in '\u0000'..'\u00a9' -> false
        else -> Lowercase(this)
    }

/**
 * Returns `true` if this `char` is in the general category for titlecase letters.
 * Conceptually, these characters consist of an uppercase portion followed by a lowercase portion.
 *
 * Titlecase letters (code points with the general category of `Lt`) are [described] in Chapter 4
 * (Character Properties) of the Unicode Standard, and [specified] in the Unicode Character
 * Database [UnicodeData.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-4/#G124722
 * [specified]: https://www.unicode.org/reports/tr44/#GcValuesTable
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     check('\u01c5'.isTitlecase())
 *     check('\u1ffc'.isTitlecase())
 *     check(!'D'.isTitlecase())
 *     check(!'z'.isTitlecase())
 *     check(!'\u4e2d'.isTitlecase())
 *     check(!' '.isTitlecase())
 * }
 * ```
 */
public fun Char.isTitlecase(): Boolean =
    when (this) {
        in '\u0000'..'\u01c4' -> false
        else -> Lt(this)
    }

/**
 * Returns `true` if this `char` has the `Uppercase` property.
 *
 * `Uppercase` is [described] in Chapter 4 (Character Properties) of the Unicode Standard, and
 * [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-4/#G136255
 * [specified]: https://www.unicode.org/reports/tr44/#Uppercase
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check(!'a'.isUppercase())
 * check(!'\u03b4'.isUppercase())
 * check('A'.isUppercase())
 * check('\u0394'.isUppercase())
 * // The various Chinese scripts and punctuation do not have case, and so:
 * check(!'\u4e2d'.isUppercase())
 * check(!' '.isUppercase())
 * ```
 *
 * In a const context:
 *
 * ```kotlin
 * val CAPITAL_DELTA_IS_UPPERCASE: Boolean = '\u0394'.isUppercase()
 * check(CAPITAL_DELTA_IS_UPPERCASE)
 * ```
 */
public fun Char.isUppercase(): Boolean =
    when (this) {
        in 'A'..'Z' -> { true }
        in '\u0000'..'\u00bf' -> false
        else -> Uppercase(this)
    }

/**
 * Returns `true` if this `char` has one of the general categories for numbers.
 *
 * The general categories for numbers (`Nd` for decimal digits, `Nl` for letter-like numeric
 * characters, and `No` for other numeric characters) are [specified] in the Unicode Character
 * Database [UnicodeData.txt].
 *
 * This method doesn't cover everything that could be considered a number, e.g. ideographic numbers like `三`.
 * If you want everything including characters with overlapping purposes, then you might want to use
 * a Unicode or language-processing library that exposes the appropriate character properties
 * (e.g. [NumericType]) instead of looking at the Unicode categories.
 *
 * If you want to parse ASCII decimal digits (0-9) or ASCII base-N, use
 * `isAsciiDigit` or `isDigit` instead.
 *
 * [specified]: https://www.unicode.org/reports/tr44/#GcValuesTable
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 * [NumericType]: https://www.unicode.org/reports/tr44/#NumericType
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('\u0663'.isNumeric())
 * check('7'.isNumeric())
 * check('\u09ec'.isNumeric())
 * check('\u00be'.isNumeric())
 * check('\u2460'.isNumeric())
 * check(!'K'.isNumeric())
 * check(!'\u0648'.isNumeric())
 * check(!'\u85cf'.isNumeric())
 * check(!'\u4e09'.isNumeric())
 * ```
 */
public fun Char.isNumeric(): Boolean =
    when (this) {
        in '0'..'9' -> { true }
        in '\u0000'..'\u00b1' -> false
        else -> N(this)
    }

/**
 * Returns `true` if this `char` satisfies either [isAlphabetic] or [isNumeric].
 *
 * [isAlphabetic]: Self.isAlphabetic
 * [isNumeric]: Self.isNumeric
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('\u0663'.isAlphanumeric())
 * check('7'.isAlphanumeric())
 * check('\u09ec'.isAlphanumeric())
 * check('\u00be'.isAlphanumeric())
 * check('\u2460'.isAlphanumeric())
 * check('K'.isAlphanumeric())
 * check('\u0648'.isAlphanumeric())
 * check('\u85cf'.isAlphanumeric())
 * ```
 */
public fun Char.isAlphanumeric(): Boolean =
    when (this) {
        in 'a'..'z', in 'A'..'Z', in '0'..'9' -> { true }
        in '\u0000'..'\u00a9' -> false
        else -> Alphabetic(this) || N(this)
    }

/**
 * Returns `true` if this `char` has the `WhiteSpace` property.
 *
 * `WhiteSpace` is [specified] in the Unicode Character Database [PropList.txt].
 *
 * [specified]: https://www.unicode.org/reports/tr44/#WhiteSpace
 * [PropList.txt]: https://www.unicode.org/Public/UCD/latest/ucd/PropList.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check(' '.isWhitespace())
 * // line break
 * check('\n'.isWhitespace())
 * // a non-breaking space
 * check('\u00a0'.isWhitespace())
 * check(!'\u8d8a'.isWhitespace())
 * ```
 */
public fun Char.isWhitespace(): Boolean =
    when (this) {
        ' ', in '\t'..'\r' -> { true }
        in '\u0000'..'\u0084' -> false
        else -> WhiteSpace(this)
    }

/**
 * Returns `true` if this `char` has the `XidContinue` property.
 *
 * `XidContinue` is [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [specified]: https://www.unicode.org/reports/tr31
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * New versions of Unicode may add new characters to this set, in accordance with its [stability policy].
 * This is *not* considered a [breaking change].
 *
 * [stability policy]: https://www.unicode.org/policies/stabilityPolicy.html#Identifier
 * [breaking change]: char.UnicodeVersion
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(charIsXid)
 * public fun doctestEntry() {
 *     check('A'.isXidContinue())
 *     check('9'.isXidContinue())
 *     check('_'.isXidContinue())
 *     check(!' '.isXidContinue())
 * }
 * ```
 */
public fun Char.isXidContinue(): Boolean =
    when (this) {
        in 'a'..'z', in 'A'..'Z', in '0'..'9', '_' -> { true }
        in '\u0000'..'\u00a9' -> false
        else -> XidContinue(this)
    }

/**
 * Returns `true` if this `char` has the `XidStart` property.
 *
 * `XidStart` is [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [specified]: https://www.unicode.org/reports/tr31
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * New versions of Unicode may add new characters to this set, in accordance with its [stability policy].
 * This is *not* considered a [breaking change].
 *
 * [stability policy]: https://www.unicode.org/policies/stabilityPolicy.html#Identifier
 * [breaking change]: char.UnicodeVersion
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(charIsXid)
 * public fun doctestEntry() {
 *     check('A'.isXidStart())
 *     check(!' '.isXidStart())
 *     check(!'9'.isXidStart())
 *     check(!'_'.isXidStart())
 * }
 * ```
 */
public fun Char.isXidStart(): Boolean =
    when (this) {
        in 'a'..'z', in 'A'..'Z' -> { true }
        in '\u0000'..'\u00a9' -> false
        else -> XidStart(this)
    }

/**
 * Returns `true` if this `char` has the general category for control codes.
 *
 * Control codes (code points with the general category of `Cc`) are [described] in Chapter 23
 * (Special Areas and Format Characters) of the Unicode Standard, and [specified] in the Unicode Character
 * Database [UnicodeData.txt]. The full set of Unicode control codes is
 * `'\0' ..= '\x1f' | '\x7f' ..= '\u(9f)'`, and will never change.
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-23/#G20365
 * [specified]: https://www.unicode.org/reports/tr44/#GcValuesTable
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('\t'.isControl())
 * check('\n'.isControl())
 * check('\u009c'.isControl())
 * // STRING TERMINATOR
 * check(!'q'.isControl())
 * ```
 */
public fun Char.isControl(): Boolean =
    when (this) {
        in '\u0000'..'\u001f', in '\u007f'..'\u009f' -> true
        else -> false
    }

/**
 * Returns `true` if this `char` has the general category for [private-use characters].
 * These characters do not have an interpretation specified by Unicode; individual programs
 * and users are free to assign them whatever meaning they like.
 *
 * [private-use characters]: https://www.unicode.org/faq/privateUse#privateUse
 *
 * Private-use characters (code points with the general category of `Co`) are [described] in Chapter 23
 * (Special Areas and Format Characters) of the Unicode Standard, and [specified] in the
 * Unicode Character Database [UnicodeData.txt]. The full set of private-use characters is
 * `'\u(E000)' ..= '\u(F8FF)' | '\u(F0000)' ..= '\u(FFFFD)' | '\u(100000)' ..= '\u(10FFFD)'`,
 * and will never change.
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-23/#G19184
 * [specified]: https://www.unicode.org/reports/tr44/#GcValuesTable
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 */
public fun Char.isPrivateUse(): Boolean =
    when (this) {
        in '\ue000'..'\uf8ff', in 0xf0000..0xffffd, in 0x100000..0x10fffd -> true
        else -> false
    }

/**
 * Returns `true` if this `char` has the general category for format control characters.
 *
 * Format controls (code points with the general category of `Cf`) are [described] in Chapter 4
 * (Character Properties) of the Unicode Standard, and [specified] in the Unicode Character
 * Database [UnicodeData.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-4/#G134153
 * [specified]: https://www.unicode.org/reports/tr44/#GcValuesTable
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check('\u00ad'.isFormatControl())
 * // SOFT HYPHEN
 * check('\u200b'.isFormatControl())
 * // ZERO WIDTH SPACE
 * check(0xe0041.isFormatControl())
 * // TAG LATIN CAPITAL LETTER A
 * check('\u06dd'.isFormatControl())
 * // ARABIC END OF AYAH
 * check(0x13432.isFormatControl())
 * // EGYPTIAN HIEROGLYPH INSERT AT TOP START
 * check(!'q'.isFormatControl())
 * ```
 */
public fun Char.isFormatControl(): Boolean = this > '\u00ac' && Cf(this)

/**
 * Returns `true` if this `char` has been assigned a meaning by Unicode, as of
 * [UnicodeVersion].
 *
 * [UnicodeVersion]: Self.UnicodeVersion
 *
 * Many of Unicode's [stability policies] apply only to assigned characters.
 *
 * [stability policies]: https://www.unicode.org/policies/stabilityPolicy.html
 *
 * Currently unassigned characters (characters for which this method returns `false`)
 * may have a meaning assigned in a future version of Unicode,
 * except for the 66 [noncharacters] which will never be assigned a meaning.
 *
 * [noncharacters]: https://www.unicode.org/faq/privateUse.html#noncharacters
 *
 * A character is considered assigned if it is present in [UnicodeData.txt].
 * Unassigned characters have general category `Cn`, as [described] in Chapter 4
 * (Character Properties) of the Unicode Standard.
 *
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-4/#G134153
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(charUnassignedPrivateUse)
 * public fun doctestEntry() {
 *     check('\u03b3'.isAssigned())
 *     // once a character is assigned, it stays assigned forever
 *     check(!'\ufffe'.isAssigned())
 *     // noncharacter, will never be assigned
 *     // Not currently assigned, but may be in the future,
 *     // so we shouldn't rely on the current status
 *     // assert(!'\u(7AAAA)'.isAssigned());
 * }
 * ```
 */
public fun Char.isAssigned(): Boolean =
    when (this) {
        in '\u0000'..'\u0377' -> { true }
        in '\u0378'..0x3fffd -> !CnPlanes03(this)
        // Assigned character ranges in planes 4 and above.
        // `src / tools / unicode - table - generator / src / main.rs` asserts that this is correct
        0xe0001, in 0xe0020..0xe007f, in 0xe0100..0xe01ef, in 0xf0000..0xffffd, in 0x100000..0x10fffd -> true
        else -> false
    }

/**
 * Returns `true` if this `char` has the `DefaultIgnorableCodePoint` property.
 * These characters [should be displayed as invisible in fallback rendering](https://www.unicode.org/faq/unsupChar#3).
 *
 * `DefaultIgnorableCodePoint` is [described] in Chapter 5 (Implementation Guidelines) of the Unicode Standard,
 * and [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-5/#G40120
 * [specified]: https://www.unicode.org/reports/tr44/#DefaultIgnorableCodePoint
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(defaultIgnorable)
 * public fun doctestEntry() {
 *     check('\u00ad'.isDefaultIgnorable())
 *     // SOFT HYPHEN
 *     check('\u115f'.isDefaultIgnorable())
 *     // HANGUL CHOSEONG FILLER
 *     check('\u200b'.isDefaultIgnorable())
 *     // ZERO WIDTH SPACE
 *     check(0xe0041.isDefaultIgnorable())
 *     // TAG LATIN CAPITAL LETTER A
 *     check(!'\u06dd'.isDefaultIgnorable())
 *     // ARABIC END OF AYAH
 *     check(!0x13432.isDefaultIgnorable())
 *     // EGYPTIAN HIEROGLYPH INSERT AT TOP START
 *     check(!' '.isDefaultIgnorable())
 *     check(!'\n'.isDefaultIgnorable())
 *     check(!'\u0000'.isDefaultIgnorable())
 *     check(!'q'.isDefaultIgnorable())
 * }
 * ```
 */
public fun Char.isDefaultIgnorable(): Boolean = this > '\u00ac' && DefaultIgnorableCodePoint(this)

/**
 * Returns `true` if this `char` has the `GraphemeExtend` property.
 *
 * `GraphemeExtend` is [described] in Chapter 3 (Conformance) of the Unicode Standard,
 * and [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G41165
 * [specified]: https://www.unicode.org/reports/tr44/#GraphemeExtend
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 */
public fun Char.isGraphemeExtender(): Boolean = this > '\u02ff' && GraphemeExtend(this)

/**
 * Returns `true` if this `char` has the `CaseIgnorable` property. This narrow-use property
 * is used to implement context-dependent casing for the Greek letter sigma (uppercase `Σ`),
 * which has two lowercase forms.
 *
 * `CaseIgnorable` is [described] in Chapter 3 (Conformance) of the Unicode Core Specification,
 * and [specified] in the Unicode Character Database [DerivedCoreProperties.txt].
 * See those resources, as well as [toLowercase]s documentation, for more information.
 *
 * [described]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G63116
 * [specified]: https://www.unicode.org/reports/tr44/#CaseIgnorable
 * [DerivedCoreProperties.txt]: https://www.unicode.org/Public/UCD/latest/ucd/DerivedCoreProperties.txt
 * [toLowercase]: Self.toLowercase()
 */
public fun Char.isCaseIgnorable(): Boolean =
    if (this.isAscii()) {
        when (this) {
            '\'', '.', ':', '^', '`' -> true
            else -> false
        }
    } else {
        CaseIgnorable(this)
    }

/**
 * Returns an iterator that yields the lowercase mapping of this `char` as one or more
 * `char`s.
 *
 * If this `char` does not have a lowercase mapping, the iterator yields the same `char`.
 *
 * If this `char` has a one-to-one lowercase mapping given by the [Unicode Character
 * Database][ucd] [UnicodeData.txt], the iterator yields that `char`.
 *
 * [ucd]: https://www.unicode.org/reports/tr44/
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 *
 * If this `char` expands to multiple `char`s, the iterator yields the `char`s given by
 * [SpecialCasing.txt]. The maximum number of `char`s in a case mapping is 3.
 *
 * This operation performs an unconditional mapping without tailoring. That is, the conversion
 * is independent of context and language. See [below](#notes-on-context-and-locale)
 * for more information.
 *
 * In the [Unicode Standard], Chapter 4 (Character Properties) discusses case mapping in
 * general and Chapter 3 (Conformance) discusses the default algorithm for case conversion.
 *
 * [Unicode Standard]: https://www.unicode.org/versions/latest/
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in ('\u0130').lowercase()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println("${('\u0130').lowercase()}")
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("i̇")
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * check('C'.lowercase().toString() == "c")
 * // Sometimes the result is more than one character:
 * check(('\u0130').lowercase().toString() == "i̇")
 * // Characters that do not have both uppercase and lowercase
 * // convert into themselves.
 * check(('\u5c71').lowercase().toString() == "山")
 * ```
 * # Notes on context and locale
 *
 * As stated earlier, this method does not take into account language or context.
 * Below is a non-exhaustive list of situations where this can be relevant.
 * If you need to handle locale-depedendent casing in your code, consider using
 * an external crate, like [`icuCasemap`](https://crates.io/crates/icuCasemap)
 * which is developed by Unicode.
 *
 * ## Greek sigma
 *
 * In Greek, the letter sigma (uppercase `Σ`) has two lowercase forms:
 * `σ` which is used in most situations, and `ς` which appears only
 * at the end of a word. [char.toLowercase] always uses the first form:
 *
 * ```kotlin
 * check(('\u03a3').lowercase().toString() == "σ")
 * ```
 *
 * `String.toLowercase()` (only available with the `alloc` crate)
 * *does* properly handle this contextual mapping,
 * so prefer using that method if you can. Alternatively, you can use
 * [isCased] and [isCaseIgnorable] to implement it yourself.
 * See `FinalSigma` in [Table 3.17] of the Unicode Standard,
 * along with [SpecialCasing.txt], for more details.
 *
 * [isCased]: Self.isCased()
 * [isCaseIgnorable]: Self.isCaseIgnorable()
 * [Table 3.17]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G54277
 *
 * ## Turkish and Azeri I/ı/İ/i
 *
 * In Turkish and Azeri, the equivalent of `i` in Latin has five forms instead of two:
 *
 * * `Dotless`: I / ı, sometimes written ï
 * * `Dotted`: İ / i
 *
 * Note that the uppercase undotted `I` is the same codepoint as the Latin. Therefore:
 *
 * ```kotlin
 * val lowerI = 'I'.lowercase().toString()
 * ```
 *
 * ``I``s correct lowercase relies on the language of the text: if we're
 * in `en - US`, it should be `"i"`, but if we're in `tr - TR` or `az - AZ`, it should
 * be `"ı"`. `toLowercase()` does not take this into account, and so:
 *
 * ```kotlin
 * val lowerI = 'I'.lowercase().toString()
 * check(lowerI == "i")
 * ```
 *
 * holds across languages.
 *
 * [SpecialCasing.txt]: https://www.unicode.org/Public/UCD/latest/ucd/SpecialCasing.txt
 */
public fun Char.toLowercase(): ToLowercase = ToLowercase(CaseMappingIter.new(toLower(this)))

/**
 * Returns an iterator that yields the titlecase mapping of this `char` as one or more
 * `char`s.
 *
 * This is usually, but not always, equivalent to the uppercase mapping
 * returned by [toUppercase]. Prefer this method when seeking to capitalize
 * Only The First Letter of a word, but use [toUppercase] for ALL CAPS.
 * See [below](#difference-from-uppercase) for a thorough explanation
 * of the difference between the two methods.
 *
 * If this `char` does not have a titlecase mapping, the iterator yields the same `char`.
 *
 * If this `char` has a one-to-one titlecase mapping given by the [Unicode Character
 * Database][ucd] [UnicodeData.txt], the iterator yields that `char`.
 *
 * [ucd]: https://www.unicode.org/reports/tr44/
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 *
 * If this `char` expands to multiple `char`s, the iterator yields the `char`s given by
 * [SpecialCasing.txt]. The maximum number of `char`s in a case mapping is 3.
 *
 * [SpecialCasing.txt]: https://www.unicode.org/Public/UCD/latest/ucd/SpecialCasing.txt
 *
 * This operation performs an unconditional mapping without tailoring. That is, the conversion
 * is independent of context and language. See [below](#note-on-locale)
 * for more information.
 *
 * In the [Unicode Standard], Chapter 4 (Character Properties) discusses case mapping in
 * general and Chapter 3 (Conformance) discusses the default algorithm for case conversion.
 *
 * [Unicode Standard]: https://www.unicode.org/versions/latest/
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     for (c in '\u00df'.toTitlecase()) {
 *         print("$c")
 *     }
 *     println("")
 * }
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     println("${'\u00df'.toTitlecase()}")
 * }
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("Ss")
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     check('c'.toTitlecase().toString() == "C")
 *     check('\u10d0'.toTitlecase().toString() == "ა")
 *     check('\u01c6'.toTitlecase().toString() == "ǅ")
 *     check('\u1fa8'.toTitlecase().toString() == "ᾨ")
 *     // Sometimes the result is more than one character:
 *     check('\u00df'.toTitlecase().toString() == "Ss")
 *     // Characters that do not have separate cased forms
 *     // convert into themselves.
 *     check('\u5c71'.toTitlecase().toString() == "山")
 * }
 * ```
 *
 * # Difference from uppercase
 *
 * Currently, there are three classes of characters where [toUppercase]
 * and `toTitlecase()` give different results:
 *
 * ## Georgian script
 *
 * Each letter in the modern Georgian alphabet can be written in one of two forms:
 * the typical lowercase-like "mkhedruli" form, and a variant uppercase-like "mtavruli"
 * form. However, unlike uppercase in most cased scripts, mtavruli is not typically used
 * to start sentences, denote proper nouns, or for any other purpose
 * in running text. It is instead confined to titles and headings, which are written entirely
 * in mtavruli. For this reason, [toUppercase] applied to a Georgian letter
 * will return the mtavruli form, but `toTitlecase()` will return the mkhedruli form.
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val ani = '\u10d0'
 *     // First letter of the Georgian alphabet, in mkhedruli form
 *     // Titlecasing mkhedruli maps it to itself...
 *     check(ani.toTitlecase().toString() == ani.toString())
 *     // but uppercasing it maps it to mtavruli
 *     check(ani.uppercase().toString() == "Ა")
 * }
 * ```
 *
 * ## Compatibility digraphs for Latin-alphabet Serbo-Croatian
 *
 * The standard Latin alphabet for the Serbo-Croatian language
 * (Bosnian, Croatian, Montenegrin, and Serbian) contains
 * three digraphs: Dž, Lj, and Nj. These are usually represented as
 * two characters. However, for compatibility with older character sets,
 * Unicode includes single-character versions of these digraphs.
 * Each has a uppercase, titlecase, and lowercase version:
 *
 * - ``Ǆ``, ``ǅ``, ``ǆ``
 * - ``Ǉ``, ``ǈ``, ``ǉ``
 * - ``Ǌ``, ``ǋ``, ``ǌ``
 *
 * Unicode additionally encodes a casing triad for the Dz digraph
 * without the caron: ``Ǳ``, ``ǲ``, ``ǳ``.
 *
 * ## Iota-subscritped Greek vowels
 *
 * In ancient Greek, the long vowels alpha (α), eta (η), and omega (ω)
 * were sometimes followed by an iota (ι), forming a diphthong. Over time,
 * the diphthong pronunciation was slowly lost, with the iota becoming mute.
 * Eventually, the ι disappeared from the spelling as well.
 * However, there remains a need to represent ancient texts faithfully.
 *
 * Modern editions of ancient Greek texts commonly use a reduced-sized
 * ι symbol to denote mute iotas, while distinguishing them from ιs
 * which continued to affect pronunciation. The exact standard differs
 * between different publications. Some render the mute ι below its associated
 * vowel (subscript), while others place it to the right of said vowel (adscript).
 * The interaction of mute ι symbols with casing also varies.
 *
 * The Unicode Standard, for its default casing rules, chose to make lowercase
 * Greek vowels with iota subscipt (e.g. ``ᾠ``) titlecase to the uppercase vowel
 * with iota subscript (``ᾨ``) but uppercase to the uppercase vowel followed by
 * full-size uppercase iota (`"ὨΙ"`). This is just one convention among many
 * in common use, but it is the one Unicode settled on,
 * so it is what this method does also.
 *
 * # Note on locale
 *
 * As stated above, this method is locale-insensitive.
 * If you need locale support, consider using an external crate,
 * like [`icuCasemap`](https://crates.io/crates/icuCasemap)
 * which is developed by Unicode. A description of one common
 * locale-dependent casing issue follows (there are others):
 *
 * In Turkish and Azeri, the equivalent of `i` in Latin has five forms instead of two:
 *
 * * `Dotless`: I / ı, sometimes written ï
 * * `Dotted`: İ / i
 *
 * Note that the lowercase dotted `i` is the same codepoint as the Latin. Therefore:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val upperI = 'i'.toTitlecase().toString()
 * }
 * ```
 *
 * ``i``s correct titlecase relies on the language of the text: if we're
 * in `en - US`, it should be `"I"`, but if we're in `tr - TR` or `az - AZ`, it should
 * be `"İ"`. `toTitlecase()` does not take this into account, and so:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val upperI = 'i'.toTitlecase().toString()
 *     check(upperI == "I")
 * }
 * ```
 *
 * holds across languages.
 *
 * [toUppercase]: Self.toUppercase()
 */
public fun Char.toTitlecase(): ToTitlecase = ToTitlecase(CaseMappingIter.new(toTitle(this)))

/**
 * Returns an iterator that yields the uppercase mapping of this `char` as one or more
 * `char`s.
 *
 * Prefer this method when converting a word into ALL CAPS, but consider [toTitlecase]
 * instead if you seek to capitalize Only The First Letter. See that method's documentation
 * for more information on the difference between the two.
 *
 * If this `char` does not have an uppercase mapping, the iterator yields the same `char`.
 *
 * If this `char` has a one-to-one uppercase mapping given by the [Unicode Character
 * Database][ucd] [UnicodeData.txt], the iterator yields that `char`.
 *
 * [ucd]: https://www.unicode.org/reports/tr44/
 * [UnicodeData.txt]: https://www.unicode.org/Public/UCD/latest/ucd/UnicodeData.txt
 *
 * If this `char` expands to multiple `char`s, the iterator yields the `char`s given by
 * [SpecialCasing.txt]. The maximum number of `char`s in a case mapping is 3.
 *
 * [SpecialCasing.txt]: https://www.unicode.org/Public/UCD/latest/ucd/SpecialCasing.txt
 *
 * This operation performs an unconditional mapping without tailoring. That is, the conversion
 * is independent of context and language. See [below](#note-on-locale)
 * for more information.
 *
 * In the [Unicode Standard], Chapter 4 (Character Properties) discusses case mapping in
 * general and Chapter 3 (Conformance) discusses the default algorithm for case conversion.
 *
 * [Unicode Standard]: https://www.unicode.org/versions/latest/
 *
 * # Examples
 *
 * ``ﬅ`` (U+FB05) is a single Unicode code point (a ligature) that maps to "ST" in uppercase.
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in ('\ufb05').uppercase()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println("${('\ufb05').uppercase()}")
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("ST")
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * check('c'.uppercase().toString() == "C")
 * check(('\u10d0').uppercase().toString() == "Ა")
 * check(('\u01c6').uppercase().toString() == "Ǆ")
 * // Sometimes the result is more than one character:
 * check(('\ufb05').uppercase().toString() == "ST")
 * check(('\u1fa8').uppercase().toString() == "ὨΙ")
 * // Characters that do not have both uppercase and lowercase
 * // convert into themselves.
 * check(('\u5c71').uppercase().toString() == "山")
 * ```
 *
 * # Note on locale
 *
 * As stated above, this method is locale-insensitive.
 * If you need locale support, consider using an external crate,
 * like [`icuCasemap`](https://crates.io/crates/icuCasemap)
 * which is developed by Unicode. A description of one common
 * locale-dependent casing issue follows (there are others):
 *
 * In Turkish and Azeri, the equivalent of `i` in Latin has five forms instead of two:
 *
 * * `Dotless`: I / ı, sometimes written ï
 * * `Dotted`: İ / i
 *
 * Note that the lowercase dotted `i` is the same codepoint as the Latin. Therefore:
 *
 * ```kotlin
 * val upperI = 'i'.uppercase().toString()
 * ```
 *
 * ``i``s correct uppercase relies on the language of the text: if we're
 * in `en - US`, it should be `"I"`, but if we're in `tr - TR` or `az - AZ`, it should
 * be `"İ"`. `toUppercase()` does not take this into account, and so:
 *
 * ```kotlin
 * val upperI = 'i'.uppercase().toString()
 * check(upperI == "I")
 * ```
 *
 * holds across languages.
 *
 * [toTitlecase]: Self.toTitlecase()
 */
public fun Char.toUppercase(): ToUppercase = ToUppercase(CaseMappingIter.new(toUpper(this)))

/**
 * Returns an iterator that yields the case folding of this `char` as one or more
 * `char`s.
 *
 * Case folding is meant to be used when performing case-insensitive string comparisons.
 * Case-folded strings should not usually be exposed directly to users. For most,
 * but not all, characters, the casefold mapping is identical to the lowercase one.
 *
 * This iterator yields the `char`(s) in the common or full case folding for this `char`,
 * as given by the [Unicode Character Database][ucd] [CaseFolding.txt].
 * The maximum number of `char`s in a case folding is 3.
 *
 * [ucd]: https://www.unicode.org/reports/tr44/
 * [CaseFolding.txt]: https://www.unicode.org/Public/UCD/latest/ucd/CaseFolding.txt
 *
 * No [normalization] (e.g. NFC) is performed, so visually and semantically identical characters
 * might still casefold differently. For example, ``ά`` (U+03AC GREEK SMALL LETTER ALPHA WITH TONOS)
 * is considered distinct from ``ά`` (U+1F71 GREEK SMALL LETTER ALPHA WITH OXIA),
 * even though Unicode considers them canonically equivalent.
 *
 * In addition, this method is independent of language/locale,
 * so the special behavior of I/ı/İ/i in Turkish and Azeri is not handled.
 *
 * In the [Unicode Standard], Chapter 4 (Character Properties) discusses case folding in
 * general and Chapter 3 (Conformance) discusses the default algorithm for case folding.
 *
 * [Unicode Standard]: https://www.unicode.org/versions/latest/
 *
 * # Examples
 *
 * The German sharp S ``ß`` (U+DF) is a single Unicode code point
 * that casefolds to `"ss"`. Its uppercase variant '`ẞ`' (U+1E9E)
 * has the same case-folding.
 *
 * As an iterator:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     check('\u00df'.toCasefoldUnnormalized().eq(listOf('s', 's')))
 *     check('\u1e9e'.toCasefoldUnnormalized().eq(listOf('s', 's')))
 * }
 * ```
 *
 * Using [`toString`](../std/string/trait.ToString.html#tymethod.toString):
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     check('\u00df'.toCasefoldUnnormalized().toString() == "ss")
 *     check('\u1e9e'.toCasefoldUnnormalized().toString() == "ss")
 * }
 * ```
 *
 * No [normalization] is performed:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     // These two characters are visually and semantically identical;
 *     // Unicode considers them to be canonically equivalent.
 *     val alphaTonos = '\u03ac'
 *     val alphaOxia = '\u1f71'
 *     // However, they are different codepoints:
 *     check(alphaTonos == '\u03ac')
 *     check(alphaOxia == '\u1f71')
 *     // Their case-foldings are likewise unequal:
 *     check(alphaTonos.toCasefoldUnnormalized().eq(listOf('\u03ac')))
 *     check(alphaOxia.toCasefoldUnnormalized().eq(listOf('\u1f71')))
 * }
 * ```
 *
 * # Note on locale
 *
 * In Turkish and Azeri, the equivalent of `i` in Latin has five forms instead of two:
 *
 * * `Dotless`: I / ı, sometimes written ï
 * * `Dotted`: İ / i
 *
 * Note that the uppercase undotted `I` is the same codepoint as the Latin. Therefore:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     val casefoldI = 'I'.toCasefoldUnnormalized().toString()
 * }
 * ```
 *
 * ``I``s correct case folding relies on the language of the text: if we're
 * in `en - US`, it should be `"i"`, but if we're in `tr - TR` or `az - AZ`, it should
 * be `"ı"`. `toCasefoldUnnormalized()` does not take this into account, and so:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     val casefoldI = 'I'.toCasefoldUnnormalized().toString()
 *     check(casefoldI == "i")
 * }
 * ```
 *
 * holds across languages.
 *
 * [normalization]: https://www.unicode.org/faq/normalization.html
 */
public fun Char.toCasefoldUnnormalized(): ToCasefold = ToCasefold(CaseMappingIter.new(toCasefold(this)))

/**
 * Returns the code point value as a `UInt`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(charToU32)
 * public fun doctestEntry() {
 *     val ascii = 'a'
 *     val heart = '\u2764'
 *     check(ascii.toU32() == 97u)
 *     check(heart.toU32() == 0x2764u)
 * }
 * ```
 */
public fun Char.toU32(): UInt = this.toUInt()

/**
 * Checks if the value is within the ASCII range.
 *
 * # Examples
 *
 * ```kotlin
 * val ascii = 'a'
 * val nonAscii = '\u2764'
 * check(ascii.isAscii())
 * check(!nonAscii.isAscii())
 * ```
 */
public fun Char.isAscii(): Boolean = this.toUInt() <= 0x7Fu

/**
 * Returns `Some` if the value is within the ASCII range,
 * or `None` if it's not.
 *
 * This is preferred to [Self.isAscii] when you're passing the value
 * along to something else that can take [ascii.Char] rather than
 * needing to check again for itself whether the value is in ASCII.
 */
public fun Char.asAscii(): io.github.kotlinmania.rustlang.core.ascii.Char? =
    if (this.isAscii()) {
        // SAFETY: Just checked that this is ASCII.
        Char.fromU8Unchecked(this.toUByte())
    } else {
        null
    }

/**
 * Converts this char into an [ASCII character](`ascii.Char`), without
 * checking whether it is valid.
 *
 * # Safety
 *
 * This char must be within the ASCII range, or else this is UB.
 */
public fun Char.asAsciiUnchecked(): io.github.kotlinmania.rustlang.core.ascii.Char {
    Unit
    // SAFETY: the caller promised that this char is ASCII.
    return Char.fromU8Unchecked(this.toUByte())
}

/**
 * Makes a copy of the value in its ASCII upper case equivalent.
 *
 * ASCII letters `a` to `z` are mapped to `A` to `Z`,
 * but non-ASCII letters are unchanged.
 *
 * To uppercase the value in-place, use [makeAsciiUppercase].
 *
 * To uppercase ASCII characters in addition to non-ASCII characters, use
 * [toUppercase].
 *
 * # Examples
 *
 * ```kotlin
 * val ascii = 'a'
 * val nonAscii = '\u2764'
 * check('A' == ascii.toAsciiUppercase())
 * check('\u2764' == nonAscii.toAsciiUppercase())
 * ```
 *
 * [makeAsciiUppercase]: #method.makeAsciiUppercase
 * [toUppercase]: #method.toUppercase
 */
public fun Char.toAsciiUppercase(): Char =
    if (this.isAsciiLowercase()) {
        (this.toUByte()).asciiChangeCaseUnchecked().toInt().toChar()
    } else {
        this
    }

/**
 * Makes a copy of the value in its ASCII lower case equivalent.
 *
 * ASCII letters `A` to `Z` are mapped to `a` to `z`,
 * but non-ASCII letters are unchanged.
 *
 * To lowercase the value in-place, use [makeAsciiLowercase].
 *
 * To lowercase ASCII characters in addition to non-ASCII characters, use
 * [toLowercase].
 *
 * # Examples
 *
 * ```kotlin
 * val ascii = 'A'
 * val nonAscii = '\u2764'
 * check('a' == ascii.toAsciiLowercase())
 * check('\u2764' == nonAscii.toAsciiLowercase())
 * ```
 *
 * [makeAsciiLowercase]: #method.makeAsciiLowercase
 * [toLowercase]: #method.toLowercase
 */
public fun Char.toAsciiLowercase(): Char =
    if (this.isAsciiUppercase()) {
        (this.toUByte()).asciiChangeCaseUnchecked().toInt().toChar()
    } else {
        this
    }

/**
 * Checks that two values are an ASCII case-insensitive match.
 *
 * Equivalent to <code>[toAsciiLowercase]\(a) == [toAsciiLowercase]\(b)</code>.
 *
 * # Examples
 *
 * ```kotlin
 * val upperA = 'A'
 * val lowerA = 'a'
 * val lowerZ = 'z'
 * check(upperA.eqIgnoreAsciiCase(lowerA))
 * check(upperA.eqIgnoreAsciiCase(upperA))
 * check(!upperA.eqIgnoreAsciiCase(lowerZ))
 * ```
 *
 * [toAsciiLowercase]: #method.toAsciiLowercase
 */
public fun Char.eqIgnoreAsciiCase(other: Char): Boolean = this.toAsciiLowercase() == other.toAsciiLowercase()

/**
 * Converts this type to its ASCII upper case equivalent in-place.
 *
 * ASCII letters `a` to `z` are mapped to `A` to `Z`,
 * but non-ASCII letters are unchanged.
 *
 * To return a new uppercased value without modifying the existing one, use
 * [toAsciiUppercase].
 *
 * # Examples
 *
 * ```kotlin
 * var ascii = 'a'
 * ascii.makeAsciiUppercase()
 * check('A' == ascii)
 * ```
 *
 * [toAsciiUppercase]: #method.toAsciiUppercase
 */
public fun Char.makeAsciiUppercase() {
    var selfMut = this
    selfMut = selfMut.toAsciiUppercase()
}

/**
 * Converts this type to its ASCII lower case equivalent in-place.
 *
 * ASCII letters `A` to `Z` are mapped to `a` to `z`,
 * but non-ASCII letters are unchanged.
 *
 * To return a new lowercased value without modifying the existing one, use
 * [toAsciiLowercase].
 *
 * # Examples
 *
 * ```kotlin
 * var ascii = 'A'
 * ascii.makeAsciiLowercase()
 * check('a' == ascii)
 * ```
 *
 * [toAsciiLowercase]: #method.toAsciiLowercase
 */
public fun Char.makeAsciiLowercase() {
    var selfMut = this
    selfMut = selfMut.toAsciiLowercase()
}

/**
 * Checks if the value is an ASCII alphabetic character:
 *
 * - U+0041 `A` ..= U+005A `Z`, or
 * - U+0061 `a` ..= U+007A `z`.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(uppercaseA.isAsciiAlphabetic())
 * check(uppercaseG.isAsciiAlphabetic())
 * check(a.isAsciiAlphabetic())
 * check(g.isAsciiAlphabetic())
 * check(!zero.isAsciiAlphabetic())
 * check(!percent.isAsciiAlphabetic())
 * check(!space.isAsciiAlphabetic())
 * check(!lf.isAsciiAlphabetic())
 * check(!esc.isAsciiAlphabetic())
 * ```
 */
public fun Char.isAsciiAlphabetic(): Boolean =
    when (this) {
        in 'a'..'z', in 'A'..'Z' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII uppercase character:
 * U+0041 `A` ..= U+005A `Z`.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(uppercaseA.isAsciiUppercase())
 * check(uppercaseG.isAsciiUppercase())
 * check(!a.isAsciiUppercase())
 * check(!g.isAsciiUppercase())
 * check(!zero.isAsciiUppercase())
 * check(!percent.isAsciiUppercase())
 * check(!space.isAsciiUppercase())
 * check(!lf.isAsciiUppercase())
 * check(!esc.isAsciiUppercase())
 * ```
 */
public fun Char.isAsciiUppercase(): Boolean =
    when (this) {
        in 'A'..'Z' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII lowercase character:
 * U+0061 `a` ..= U+007A `z`.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(!uppercaseA.isAsciiLowercase())
 * check(!uppercaseG.isAsciiLowercase())
 * check(a.isAsciiLowercase())
 * check(g.isAsciiLowercase())
 * check(!zero.isAsciiLowercase())
 * check(!percent.isAsciiLowercase())
 * check(!space.isAsciiLowercase())
 * check(!lf.isAsciiLowercase())
 * check(!esc.isAsciiLowercase())
 * ```
 */
public fun Char.isAsciiLowercase(): Boolean =
    when (this) {
        in 'a'..'z' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII alphanumeric character:
 *
 * - U+0041 `A` ..= U+005A `Z`, or
 * - U+0061 `a` ..= U+007A `z`, or
 * - U+0030 '0' ..= U+0039 '9'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(uppercaseA.isAsciiAlphanumeric())
 * check(uppercaseG.isAsciiAlphanumeric())
 * check(a.isAsciiAlphanumeric())
 * check(g.isAsciiAlphanumeric())
 * check(zero.isAsciiAlphanumeric())
 * check(!percent.isAsciiAlphanumeric())
 * check(!space.isAsciiAlphanumeric())
 * check(!lf.isAsciiAlphanumeric())
 * check(!esc.isAsciiAlphanumeric())
 * ```
 */
public fun Char.isAsciiAlphanumeric(): Boolean =
    when (this) {
        in '0'..'9' -> true
        else -> false
    } or
        when (this) {
            in 'A'..'Z' -> true
            else -> false
        } or
        when (this) {
            in 'a'..'z' -> true
            else -> false
        }

/**
 * Checks if the value is an ASCII decimal digit:
 * U+0030 '0' ..= U+0039 '9'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(!uppercaseA.isAsciiDigit())
 * check(!uppercaseG.isAsciiDigit())
 * check(!a.isAsciiDigit())
 * check(!g.isAsciiDigit())
 * check(zero.isAsciiDigit())
 * check(!percent.isAsciiDigit())
 * check(!space.isAsciiDigit())
 * check(!lf.isAsciiDigit())
 * check(!esc.isAsciiDigit())
 * ```
 */
public fun Char.isAsciiDigit(): Boolean =
    when (this) {
        in '0'..'9' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII octal digit:
 * U+0030 '0' ..= U+0037 '7'.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(isAsciiOctdigit)
 * public fun doctestEntry() {
 *     val uppercaseA = 'A'
 *     val a = 'a'
 *     val zero = '0'
 *     val seven = '7'
 *     val nine = '9'
 *     val percent = '%'
 *     val lf = '\n'
 *     check(!uppercaseA.isAsciiOctdigit())
 *     check(!a.isAsciiOctdigit())
 *     check(zero.isAsciiOctdigit())
 *     check(seven.isAsciiOctdigit())
 *     check(!nine.isAsciiOctdigit())
 *     check(!percent.isAsciiOctdigit())
 *     check(!lf.isAsciiOctdigit())
 * }
 * ```
 */
public fun Char.isAsciiOctdigit(): Boolean =
    when (this) {
        in '0'..'7' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII hexadecimal digit:
 *
 * - U+0030 '0' ..= U+0039 '9', or
 * - U+0041 `A` ..= U+0046 `F`, or
 * - U+0061 `a` ..= U+0066 `f`.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(uppercaseA.isAsciiHexdigit())
 * check(!uppercaseG.isAsciiHexdigit())
 * check(a.isAsciiHexdigit())
 * check(!g.isAsciiHexdigit())
 * check(zero.isAsciiHexdigit())
 * check(!percent.isAsciiHexdigit())
 * check(!space.isAsciiHexdigit())
 * check(!lf.isAsciiHexdigit())
 * check(!esc.isAsciiHexdigit())
 * ```
 */
public fun Char.isAsciiHexdigit(): Boolean =
    when (this) {
        in '0'..'9' -> true
        else -> false
    } or
        when (this) {
            in 'A'..'F' -> true
            else -> false
        } or
        when (this) {
            in 'a'..'f' -> true
            else -> false
        }

/**
 * Checks if the value is an ASCII punctuation or symbol character
 * (i.e. not alphanumeric, whitespace, or control):
 *
 * - U+0021 ..= U+002F `! " # $ % & ' ( ) * + , - . /`, or
 * - U+003A ..= U+0040 `:; < = > ? @`, or
 * - U+005B ..= U+0060 [ \ ] ^ _ `, or
 * - U+007B ..= U+007E `( | ) ~`
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(!uppercaseA.isAsciiPunctuation())
 * check(!uppercaseG.isAsciiPunctuation())
 * check(!a.isAsciiPunctuation())
 * check(!g.isAsciiPunctuation())
 * check(!zero.isAsciiPunctuation())
 * check(percent.isAsciiPunctuation())
 * check(!space.isAsciiPunctuation())
 * check(!lf.isAsciiPunctuation())
 * check(!esc.isAsciiPunctuation())
 * ```
 */
public fun Char.isAsciiPunctuation(): Boolean =
    when (this) {
        in '!'..'/' -> true
        else -> false
    } or
        when (this) {
            in ':'..'@' -> true
            else -> false
        } or
        when (this) {
            in '['..'`' -> true
            else -> false
        } or
        when (this) {
            in '{'..'~' -> true
            else -> false
        }

/**
 * Checks if the value is an ASCII graphic character
 * (i.e. not whitespace or control):
 * U+0021 '!' ..= U+007E '~'.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(uppercaseA.isAsciiGraphic())
 * check(uppercaseG.isAsciiGraphic())
 * check(a.isAsciiGraphic())
 * check(g.isAsciiGraphic())
 * check(zero.isAsciiGraphic())
 * check(percent.isAsciiGraphic())
 * check(!space.isAsciiGraphic())
 * check(!lf.isAsciiGraphic())
 * check(!esc.isAsciiGraphic())
 * ```
 */
public fun Char.isAsciiGraphic(): Boolean =
    when (this) {
        in '!'..'~' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII whitespace character:
 * U+0020 SPACE, U+0009 HORIZONTAL TAB, U+000A LINE FEED,
 * U+000C FORM FEED, or U+000D CARRIAGE RETURN.
 *
 * **Warning:** Because the list above excludes U+000B VERTICAL TAB,
 * `c.isAsciiWhitespace()` is **not** equivalent to `c.isAscii() && c.isWhitespace()`.
 *
 * Rust uses the WhatWG Infra Standard's [definition of ASCII
 * whitespace][infra-aw]. There are several other definitions in
 * wide use. For instance, [the POSIX locale][pct] includes
 * U+000B VERTICAL TAB as well as all the above characters,
 * but—from the very same specification—[the default rule for
 * "field splitting" in the Bourne shell][bfs] considers *only*
 * SPACE, HORIZONTAL TAB, and LINE FEED as whitespace.
 *
 * If you are writing a program that will process an existing
 * file format, check what that format's definition of whitespace is
 * before using this function.
 *
 * [infra-aw]: https://infra.spec.whatwg.org/#ascii-whitespace
 * [pct]: https://pubs.opengroup.org/onlinepubs/9799919799/basedefs/V1Chap07.html#tag070301
 * [bfs]: https://pubs.opengroup.org/onlinepubs/9799919799/utilities/V3Chap02.html#tag190605
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(!uppercaseA.isAsciiWhitespace())
 * check(!uppercaseG.isAsciiWhitespace())
 * check(!a.isAsciiWhitespace())
 * check(!g.isAsciiWhitespace())
 * check(!zero.isAsciiWhitespace())
 * check(!percent.isAsciiWhitespace())
 * check(space.isAsciiWhitespace())
 * check(lf.isAsciiWhitespace())
 * check(!esc.isAsciiWhitespace())
 * ```
 */
public fun Char.isAsciiWhitespace(): Boolean =
    when (this) {
        '\t', '\n', '\u000c', '\r', ' ' -> true
        else -> false
    }

/**
 * Checks if the value is an ASCII control character:
 * U+0000 NUL ..= U+001F UNIT SEPARATOR, or U+007F DELETE.
 * Note that most ASCII whitespace characters are control
 * characters, but SPACE is not.
 *
 * # Examples
 *
 * ```kotlin
 * val uppercaseA = 'A'
 * val uppercaseG = 'G'
 * val a = 'a'
 * val g = 'g'
 * val zero = '0'
 * val percent = '%'
 * val space = ' '
 * val lf = '\n'
 * val esc = '\u001b'
 * check(!uppercaseA.isAsciiControl())
 * check(!uppercaseG.isAsciiControl())
 * check(!a.isAsciiControl())
 * check(!g.isAsciiControl())
 * check(!zero.isAsciiControl())
 * check(!percent.isAsciiControl())
 * check(!space.isAsciiControl())
 * check(lf.isAsciiControl())
 * check(esc.isAsciiControl())
 * ```
 */
public fun Char.isAsciiControl(): Boolean =
    when (this) {
        in '\u0000'..'\u001f', '\u007f' -> true
        else -> false
    }

public open class EscapeDebugExtArgs(
    /**
     * Escape single quotes?
     */
    public var escapeSingleQuote: Boolean,
    /**
     * Escape double quotes?
     */
    public var escapeDoubleQuote: Boolean,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<EscapeDebugExtArgs> =
            object : TypeLayout<EscapeDebugExtArgs> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): EscapeDebugExtArgs =
                    EscapeDebugExtArgs(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                    )
                override fun writeNative(addr: Long, value: EscapeDebugExtArgs) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.escapeSingleQuote)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.escapeDoubleQuote)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EscapeDebugExtArgs =
                    EscapeDebugExtArgs(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EscapeDebugExtArgs) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.escapeSingleQuote)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.escapeDoubleQuote)
                }
            }

        public val ESCAPE_ALL: EscapeDebugExtArgs = EscapeDebugExtArgs(escapeSingleQuote = true, escapeDoubleQuote = true)
    }
}

public fun lenUtf8(code: UInt): Int =
    when {
        code < MAX_ONE_B -> 1
        code < MAX_TWO_B -> 2
        code < MAX_THREE_B -> 3
        else -> 4
    }

public fun lenUtf16(code: UInt): Int =
    if ((code and 0xFFFFu) == code) {
        1
    } else {
        2
    }

/**
 * Encodes a raw `UInt` value as UTF-8 into the provided byte buffer,
 * and then returns the subslice of the buffer that contains the encoded character.
 *
 * Unlike `char.encodeUtf8`, this method also handles codepoints in the surrogate range.
 * (Creating a `char` in the surrogate range is UB.)
 * The result is valid [generalized UTF-8] but not valid UTF-8.
 *
 * [generalized UTF-8]: https://simonsapin.github.io/wtf-8/#generalized-utf8
 *
 * # Panics
 *
 * Panics if the buffer is not large enough.
 * A buffer of length four is large enough to encode any `char`.
 */
public fun encodeUtf8Raw(code: UInt, dst: MutableList<UByte>): MutableList<UByte> {
    val len = lenUtf8(code)
    if (dst.size < len) {
        constPanic("encode_utf8: buffer does not have enough bytes to encode code point", "encode_utf8: need {len} bytes to encode U+{code:04X} but buffer has just {dst_len}", code, code, len, len, dstLen, dst, len)
    }
    // SAFETY: `dst` is checked to be at least the length needed to encode the codepoint.
    encodeUtf8RawUnchecked(code, dst.asMutPtr())
    // SAFETY: `<[UByte]>.asMutPtr` is guaranteed to return a valid pointer and `len` has been tested to be within bounds.
    return linearMutSliceView(dst.asMutPtr(), len.toInt(), PrimitiveLayouts.U8)
}

/**
 * Encodes a raw `UInt` value as UTF-8 into the byte buffer pointed to by `dst`.
 *
 * Unlike `char.encodeUtf8`, this method also handles codepoints in the surrogate range.
 * (Creating a `char` in the surrogate range is UB.)
 * The result is valid [generalized UTF-8] but not valid UTF-8.
 *
 * [generalized UTF-8]: https://simonsapin.github.io/wtf-8/#generalized-utf8
 *
 * # Safety
 *
 * The behavior is undefined if the buffer pointed to by `dst` is not
 * large enough to hold the encoded codepoint. A buffer of length four
 * is large enough to encode any `char`.
 *
 * For a safe version of this function, see the [encodeUtf8Raw] function.
 */
public fun encodeUtf8RawUnchecked(code: UInt, dst: MutPtr) {
    val dst1 = dst
    val len = lenUtf8(code)
    // SAFETY: The caller must guarantee that the buffer pointed to by `dst`
    // is at least `len` bytes long.
    run {
        if (len == 1) {
            (PrimitiveLayouts.U8).writeNative((dst1).rawAddress, code.toUByte())
            return
        }
        val last1 = (code shr 0 and 0x3Fu).toUByte() or TAG_CONT
        val last2 = (code shr 6 and 0x3Fu).toUByte() or TAG_CONT
        val last3 = (code shr 12 and 0x3Fu).toUByte() or TAG_CONT
        val last4 = (code shr 18 and 0x3Fu).toUByte() or TAG_FOUR_B
        if (len == 2) {
            (PrimitiveLayouts.U8).writeNative((dst1).rawAddress, last2 or TAG_TWO_B)
            (PrimitiveLayouts.U8).writeNative((MutPtr((dst1).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, last1)
            return
        }
        if (len == 3) {
            (PrimitiveLayouts.U8).writeNative((dst1).rawAddress, last3 or TAG_THREE_B)
            (PrimitiveLayouts.U8).writeNative((MutPtr((dst1).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, last2)
            (PrimitiveLayouts.U8).writeNative((MutPtr((dst1).rawAddress + ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, last1)
            return
        }
        (PrimitiveLayouts.U8).writeNative((dst1).rawAddress, last4)
        (PrimitiveLayouts.U8).writeNative((MutPtr((dst1).rawAddress + ((1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, last3)
        (PrimitiveLayouts.U8).writeNative((MutPtr((dst1).rawAddress + ((2).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, last2)
        (PrimitiveLayouts.U8).writeNative((MutPtr((dst1).rawAddress + ((3).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, last1)
    }
}

/**
 * Encodes a raw `UInt` value as native endian UTF-16 into the provided `UShort` buffer,
 * and then returns the subslice of the buffer that contains the encoded character.
 *
 * Unlike `char.encodeUtf16`, this method also handles codepoints in the surrogate range.
 * (Creating a `char` in the surrogate range is UB.)
 *
 * # Panics
 *
 * Panics if the buffer is not large enough.
 * A buffer of length 2 is large enough to encode any `char`.
 */
public fun encodeUtf16Raw(code: UInt, dst: MutableList<UShort>): MutableList<UShort> {
    var code1 = code
    val len = lenUtf16(code1)
    run {
        val matchVal = Pair(len, dst)
        when {
            matchVal.first == 1 && matchVal.second.size >= 1 -> {
                val a = matchVal.second[0]
                a = code1.toUShort()
            }
            matchVal.first == 2 && matchVal.second.size >= 2 -> {
                val a1 = matchVal.second[0]
                val b = matchVal.second[1]
                code1 -= 0x10000u
                a1 = (code1 shr 10).toUShort() or 0xD800u.toUShort()
                b = (code1 and 0x3FFu).toUShort() or 0xDC00u.toUShort()
            }
            else -> {
                constPanic("encode_utf16: buffer does not have enough bytes to encode code point", "encode_utf16: need {len} bytes to encode U+{code:04X} but buffer has just {dst_len}", code1, code1, len, len, dstLen, dst, len)
            }
        }
    }
    // SAFETY: `<[UShort]>.asMutPtr` is guaranteed to return a valid pointer and `len` has been tested to be within bounds.
    return linearMutSliceView(dst.asMutPtr(), len.toInt(), PrimitiveLayouts.U16)
}
