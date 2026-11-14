// port-lint: source ascii/ascii_char.rs
package io.github.kotlinmania.rustlang.core.ascii.asciichar

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ascii.EscapeDefault
import io.github.kotlinmania.rustlang.core.ascii.asciichar.AsciiChar.Apostrophe
import io.github.kotlinmania.rustlang.core.ascii.escapeDefault
import io.github.kotlinmania.rustlang.core.char.isAsciiAlphabetic
import io.github.kotlinmania.rustlang.core.char.isAsciiAlphanumeric
import io.github.kotlinmania.rustlang.core.char.isAsciiControl
import io.github.kotlinmania.rustlang.core.char.isAsciiDigit
import io.github.kotlinmania.rustlang.core.char.isAsciiGraphic
import io.github.kotlinmania.rustlang.core.char.isAsciiHexdigit
import io.github.kotlinmania.rustlang.core.char.isAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.isAsciiPunctuation
import io.github.kotlinmania.rustlang.core.char.isAsciiUppercase
import io.github.kotlinmania.rustlang.core.char.isAsciiWhitespace
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.escape.HEX_DIGITS
import io.github.kotlinmania.rustlang.core.escape.backslash
import io.github.kotlinmania.rustlang.core.intrinsics.transmute
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.ubchecks.assertUnsafePrecondition
import io.github.kotlinmania.rustlang.core.ascii.asciichar.AsciiChar.ReverseSolidus as Backslash

// This uses the name `AsciiChar`, even though it's not exposed that way right now,
// because it avoids a whole bunch of "are you sure you didn't mean `char`?"
// suggestions from rustc if you get anything slightly wrong in here, and overall
// helps with clarity as we're also referring to `char` intentionally in here.

/**
 * One of the 128 Unicode characters from U+0000 through U+007F,
 * often known as the [ASCII] subset.
 *
 * Officially, this is the first [block] in Unicode, _Basic Latin_.
 * For details, see the [*C0 Controls and Basic Latin*][chart] code chart.
 *
 * This block was based on older 7-bit character code standards such as
 * ANSI X3.4-1977, ISO 646-1973, and [NIST FIPS 1-2].
 *
 * # When to use this
 *
 * The main advantage of this subset is that it's always valid UTF-8.  As such,
 * the `[ascii.Char]` -> `String` conversion function (as well as other related
 * ones) are O(1): *no* runtime checks are needed.
 *
 * If you're consuming strings, you should usually handle Unicode and thus
 * accept `String`s, not limit yourself to `ascii.Char`s.
 *
 * However, certain formats are intentionally designed to produce ASCII-only
 * output in order to be 8-bit-clean.  In those cases, it can be simpler and
 * faster to generate `ascii.Char`s instead of dealing with the variable width
 * properties of general UTF-8 encoded strings, while still allowing the result
 * to be used freely with other Rust things that deal in general `String`s.
 *
 * For example, a UUID library might offer a way to produce the string
 * representation of a UUID as an `[ascii.Char; 36]` to avoid memory
 * allocation yet still allow it to be used as UTF-8 via `asStr` without
 * paying for validation (or needing `unchecked` code) the way it would if it
 * were provided as a `[UByte; 36]`.
 *
 * # Layout
 *
 * This type is guaranteed to have a size and alignment of 1 byte.
 *
 * # Names
 *
 * The variants on this type are [Unicode names][NamesList] of the characters
 * in upper camel case, with a few tweaks:
 * - For `<control>` characters, the primary alias name is used.
 * - `LATIN` is dropped, as this block has no non-latin letters.
 * - `LETTER` is dropped, as `CAPITAL`/`SMALL` suffices in this block.
 * - `DIGIT`s use a single digit rather than writing out `ZERO`, `ONE`, etc.
 *
 * [ASCII]: https://www.unicode.org/glossary/index.html#ASCII
 * [block]: https://www.unicode.org/glossary/index.html#block
 * [chart]: https://www.unicode.org/charts/PDF/U0000.pdf
 * [NIST FIPS 1-2]: https://nvlpubs.nist.gov/nistpubs/Legacy/FIPS/fipspub1-2-1977.pdf
 * [NamesList]: https://www.unicode.org/Public/15.0.0/ucd/NamesList.txt
 */
public sealed class AsciiChar(
    public val value: UByte,
) {
    /**
     * U+0000 (The default variant)
     */
    public data object Null : AsciiChar(0u)
    /**
     * U+0001
     */
    public data object StartOfHeading : AsciiChar(1u)
    /**
     * U+0002
     */
    public data object StartOfText : AsciiChar(2u)
    /**
     * U+0003
     */
    public data object EndOfText : AsciiChar(3u)
    /**
     * U+0004
     */
    public data object EndOfTransmission : AsciiChar(4u)
    /**
     * U+0005
     */
    public data object Enquiry : AsciiChar(5u)
    /**
     * U+0006
     */
    public data object Acknowledge : AsciiChar(6u)
    /**
     * U+0007
     */
    public data object Bell : AsciiChar(7u)
    /**
     * U+0008
     */
    public data object Backspace : AsciiChar(8u)
    /**
     * U+0009
     */
    public data object CharacterTabulation : AsciiChar(9u)
    /**
     * U+000A
     */
    public data object LineFeed : AsciiChar(10u)
    /**
     * U+000B
     */
    public data object LineTabulation : AsciiChar(11u)
    /**
     * U+000C
     */
    public data object FormFeed : AsciiChar(12u)
    /**
     * U+000D
     */
    public data object CarriageReturn : AsciiChar(13u)
    /**
     * U+000E
     */
    public data object ShiftOut : AsciiChar(14u)
    /**
     * U+000F
     */
    public data object ShiftIn : AsciiChar(15u)
    /**
     * U+0010
     */
    public data object DataLinkEscape : AsciiChar(16u)
    /**
     * U+0011
     */
    public data object DeviceControlOne : AsciiChar(17u)
    /**
     * U+0012
     */
    public data object DeviceControlTwo : AsciiChar(18u)
    /**
     * U+0013
     */
    public data object DeviceControlThree : AsciiChar(19u)
    /**
     * U+0014
     */
    public data object DeviceControlFour : AsciiChar(20u)
    /**
     * U+0015
     */
    public data object NegativeAcknowledge : AsciiChar(21u)
    /**
     * U+0016
     */
    public data object SynchronousIdle : AsciiChar(22u)
    /**
     * U+0017
     */
    public data object EndOfTransmissionBlock : AsciiChar(23u)
    /**
     * U+0018
     */
    public data object Cancel : AsciiChar(24u)
    /**
     * U+0019
     */
    public data object EndOfMedium : AsciiChar(25u)
    /**
     * U+001A
     */
    public data object Substitute : AsciiChar(26u)
    /**
     * U+001B
     */
    public data object Escape : AsciiChar(27u)
    /**
     * U+001C
     */
    public data object InformationSeparatorFour : AsciiChar(28u)
    /**
     * U+001D
     */
    public data object InformationSeparatorThree : AsciiChar(29u)
    /**
     * U+001E
     */
    public data object InformationSeparatorTwo : AsciiChar(30u)
    /**
     * U+001F
     */
    public data object InformationSeparatorOne : AsciiChar(31u)
    /**
     * U+0020
     */
    public data object Space : AsciiChar(32u)
    /**
     * U+0021
     */
    public data object ExclamationMark : AsciiChar(33u)
    /**
     * U+0022
     */
    public data object QuotationMark : AsciiChar(34u)
    /**
     * U+0023
     */
    public data object NumberSign : AsciiChar(35u)
    /**
     * U+0024
     */
    public data object DollarSign : AsciiChar(36u)
    /**
     * U+0025
     */
    public data object PercentSign : AsciiChar(37u)
    /**
     * U+0026
     */
    public data object Ampersand : AsciiChar(38u)
    /**
     * U+0027
     */
    public data object Apostrophe : AsciiChar(39u)
    /**
     * U+0028
     */
    public data object LeftParenthesis : AsciiChar(40u)
    /**
     * U+0029
     */
    public data object RightParenthesis : AsciiChar(41u)
    /**
     * U+002A
     */
    public data object Asterisk : AsciiChar(42u)
    /**
     * U+002B
     */
    public data object PlusSign : AsciiChar(43u)
    /**
     * U+002C
     */
    public data object Comma : AsciiChar(44u)
    /**
     * U+002D
     */
    public data object HyphenMinus : AsciiChar(45u)
    /**
     * U+002E
     */
    public data object FullStop : AsciiChar(46u)
    /**
     * U+002F
     */
    public data object Solidus : AsciiChar(47u)
    /**
     * U+0030
     */
    public data object Digit0 : AsciiChar(48u)
    /**
     * U+0031
     */
    public data object Digit1 : AsciiChar(49u)
    /**
     * U+0032
     */
    public data object Digit2 : AsciiChar(50u)
    /**
     * U+0033
     */
    public data object Digit3 : AsciiChar(51u)
    /**
     * U+0034
     */
    public data object Digit4 : AsciiChar(52u)
    /**
     * U+0035
     */
    public data object Digit5 : AsciiChar(53u)
    /**
     * U+0036
     */
    public data object Digit6 : AsciiChar(54u)
    /**
     * U+0037
     */
    public data object Digit7 : AsciiChar(55u)
    /**
     * U+0038
     */
    public data object Digit8 : AsciiChar(56u)
    /**
     * U+0039
     */
    public data object Digit9 : AsciiChar(57u)
    /**
     * U+003A
     */
    public data object Colon : AsciiChar(58u)
    /**
     * U+003B
     */
    public data object Semicolon : AsciiChar(59u)
    /**
     * U+003C
     */
    public data object LessThanSign : AsciiChar(60u)
    /**
     * U+003D
     */
    public data object EqualsSign : AsciiChar(61u)
    /**
     * U+003E
     */
    public data object GreaterThanSign : AsciiChar(62u)
    /**
     * U+003F
     */
    public data object QuestionMark : AsciiChar(63u)
    /**
     * U+0040
     */
    public data object CommercialAt : AsciiChar(64u)
    /**
     * U+0041
     */
    public data object CapitalA : AsciiChar(65u)
    /**
     * U+0042
     */
    public data object CapitalB : AsciiChar(66u)
    /**
     * U+0043
     */
    public data object CapitalC : AsciiChar(67u)
    /**
     * U+0044
     */
    public data object CapitalD : AsciiChar(68u)
    /**
     * U+0045
     */
    public data object CapitalE : AsciiChar(69u)
    /**
     * U+0046
     */
    public data object CapitalF : AsciiChar(70u)
    /**
     * U+0047
     */
    public data object CapitalG : AsciiChar(71u)
    /**
     * U+0048
     */
    public data object CapitalH : AsciiChar(72u)
    /**
     * U+0049
     */
    public data object CapitalI : AsciiChar(73u)
    /**
     * U+004A
     */
    public data object CapitalJ : AsciiChar(74u)
    /**
     * U+004B
     */
    public data object CapitalK : AsciiChar(75u)
    /**
     * U+004C
     */
    public data object CapitalL : AsciiChar(76u)
    /**
     * U+004D
     */
    public data object CapitalM : AsciiChar(77u)
    /**
     * U+004E
     */
    public data object CapitalN : AsciiChar(78u)
    /**
     * U+004F
     */
    public data object CapitalO : AsciiChar(79u)
    /**
     * U+0050
     */
    public data object CapitalP : AsciiChar(80u)
    /**
     * U+0051
     */
    public data object CapitalQ : AsciiChar(81u)
    /**
     * U+0052
     */
    public data object CapitalR : AsciiChar(82u)
    /**
     * U+0053
     */
    public data object CapitalS : AsciiChar(83u)
    /**
     * U+0054
     */
    public data object CapitalT : AsciiChar(84u)
    /**
     * U+0055
     */
    public data object CapitalU : AsciiChar(85u)
    /**
     * U+0056
     */
    public data object CapitalV : AsciiChar(86u)
    /**
     * U+0057
     */
    public data object CapitalW : AsciiChar(87u)
    /**
     * U+0058
     */
    public data object CapitalX : AsciiChar(88u)
    /**
     * U+0059
     */
    public data object CapitalY : AsciiChar(89u)
    /**
     * U+005A
     */
    public data object CapitalZ : AsciiChar(90u)
    /**
     * U+005B
     */
    public data object LeftSquareBracket : AsciiChar(91u)
    /**
     * U+005C
     */
    public data object ReverseSolidus : AsciiChar(92u)
    /**
     * U+005D
     */
    public data object RightSquareBracket : AsciiChar(93u)
    /**
     * U+005E
     */
    public data object CircumflexAccent : AsciiChar(94u)
    /**
     * U+005F
     */
    public data object LowLine : AsciiChar(95u)
    /**
     * U+0060
     */
    public data object GraveAccent : AsciiChar(96u)
    /**
     * U+0061
     */
    public data object SmallA : AsciiChar(97u)
    /**
     * U+0062
     */
    public data object SmallB : AsciiChar(98u)
    /**
     * U+0063
     */
    public data object SmallC : AsciiChar(99u)
    /**
     * U+0064
     */
    public data object SmallD : AsciiChar(100u)
    /**
     * U+0065
     */
    public data object SmallE : AsciiChar(101u)
    /**
     * U+0066
     */
    public data object SmallF : AsciiChar(102u)
    /**
     * U+0067
     */
    public data object SmallG : AsciiChar(103u)
    /**
     * U+0068
     */
    public data object SmallH : AsciiChar(104u)
    /**
     * U+0069
     */
    public data object SmallI : AsciiChar(105u)
    /**
     * U+006A
     */
    public data object SmallJ : AsciiChar(106u)
    /**
     * U+006B
     */
    public data object SmallK : AsciiChar(107u)
    /**
     * U+006C
     */
    public data object SmallL : AsciiChar(108u)
    /**
     * U+006D
     */
    public data object SmallM : AsciiChar(109u)
    /**
     * U+006E
     */
    public data object SmallN : AsciiChar(110u)
    /**
     * U+006F
     */
    public data object SmallO : AsciiChar(111u)
    /**
     * U+0070
     */
    public data object SmallP : AsciiChar(112u)
    /**
     * U+0071
     */
    public data object SmallQ : AsciiChar(113u)
    /**
     * U+0072
     */
    public data object SmallR : AsciiChar(114u)
    /**
     * U+0073
     */
    public data object SmallS : AsciiChar(115u)
    /**
     * U+0074
     */
    public data object SmallT : AsciiChar(116u)
    /**
     * U+0075
     */
    public data object SmallU : AsciiChar(117u)
    /**
     * U+0076
     */
    public data object SmallV : AsciiChar(118u)
    /**
     * U+0077
     */
    public data object SmallW : AsciiChar(119u)
    /**
     * U+0078
     */
    public data object SmallX : AsciiChar(120u)
    /**
     * U+0079
     */
    public data object SmallY : AsciiChar(121u)
    /**
     * U+007A
     */
    public data object SmallZ : AsciiChar(122u)
    /**
     * U+007B
     */
    public data object LeftCurlyBracket : AsciiChar(123u)
    /**
     * U+007C
     */
    public data object VerticalLine : AsciiChar(124u)
    /**
     * U+007D
     */
    public data object RightCurlyBracket : AsciiChar(125u)
    /**
     * U+007E
     */
    public data object Tilde : AsciiChar(126u)
    /**
     * U+007F
     */
    public data object Delete : AsciiChar(127u)

    public fun rustEquals(other: AsciiChar): Boolean = this == other

    /**
     * Gets this ASCII character as a byte.
     */
    public open fun toU8(): UByte = this.toUByte()

    /**
     * Gets this ASCII character as a `char` Unicode Scalar Value.
     */
    public open fun toChar(): Char = (this.toUByte()).toInt().toChar()

    /**
     * Views this ASCII character as a one-code-unit UTF-8 `String`.
     */
    public open fun asStr(): String = this.asStr()

    /**
     * Makes a copy of the value in its upper case equivalent.
     *
     * Letters 'a' to 'z' are mapped to 'A' to 'Z'.
     *
     * To uppercase the value in-place, use [makeUppercase].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val lowercaseA = ascii.Char.SmallA
     *     check(ascii.Char.CapitalA == lowercaseA.uppercase())
     * }
     * ```
     *
     * [makeUppercase]: Self.makeUppercase
     */
    public open fun toUppercase(): AsciiChar {
        val uppercaseByte = this.toU8().toAsciiUppercase()
        // SAFETY: Toggling the 6th bit won't convert ASCII to non-ASCII.
        return AsciiChar.fromU8Unchecked(uppercaseByte)
    }

    /**
     * Makes a copy of the value in its lower case equivalent.
     *
     * Letters 'A' to 'Z' are mapped to 'a' to 'z'.
     *
     * To lowercase the value in-place, use [makeLowercase].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     check(ascii.Char.SmallA == uppercaseA.lowercase())
     * }
     * ```
     *
     * [makeLowercase]: Self.makeLowercase
     */
    public open fun toLowercase(): AsciiChar {
        val lowercaseByte = this.toU8().toAsciiLowercase()
        // SAFETY: Setting the 6th bit won't convert ASCII to non-ASCII.
        return AsciiChar.fromU8Unchecked(lowercaseByte)
    }

    /**
     * Checks that two values are a case-insensitive match.
     *
     * This is equivalent to `toLowercase(a) == toLowercase(b)`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val lowercaseA = ascii.Char.SmallA
     *     val uppercaseA = ascii.Char.CapitalA
     *     check(lowercaseA.eqIgnoreCase(uppercaseA))
     * }
     * ```
     */
    public open fun eqIgnoreCase(other: AsciiChar): Boolean {
        // Note(const-Note) `arg.toU8().toAsciiLowercase()` -> `arg.toLowercase()`
        // once `PartialEq` is const for `Self`.
        return this.toU8().eqIgnoreAsciiCase(other.toU8())
    }

    /**
     * Converts this value to its upper case equivalent in-place.
     *
     * Letters 'a' to 'z' are mapped to 'A' to 'Z'.
     *
     * To return a new uppercased value without modifying the existing one, use
     * [toUppercase].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     var letterA = ascii.Char.SmallA
     *     letterA.makeUppercase()
     *     check(ascii.Char.CapitalA == letterA)
     * }
     * ```
     *
     * [toUppercase]: Self.toUppercase
     */
    public open fun makeUppercase() {
        this = this.toUppercase()
    }

    /**
     * Converts this value to its lower case equivalent in-place.
     *
     * Letters 'A' to 'Z' are mapped to 'a' to 'z'.
     *
     * To return a new lowercased value without modifying the existing one, use
     * [toLowercase].
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     var letterA = ascii.Char.CapitalA
     *     letterA.makeLowercase()
     *     check(ascii.Char.SmallA == letterA)
     * }
     * ```
     *
     * [toLowercase]: Self.toLowercase
     */
    public open fun makeLowercase() {
        this = this.toLowercase()
    }

    /**
     * Checks if the value is an alphabetic character:
     *
     * - 0x41 'A' ..= 0x5A 'Z', or
     * - 0x61 'a' ..= 0x7A 'z'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(uppercaseA.isAlphabetic())
     *     check(uppercaseG.isAlphabetic())
     *     check(a.isAlphabetic())
     *     check(g.isAlphabetic())
     *     check(!zero.isAlphabetic())
     *     check(!percent.isAlphabetic())
     *     check(!space.isAlphabetic())
     *     check(!lf.isAlphabetic())
     *     check(!esc.isAlphabetic())
     * }
     * ```
     */
    public open fun isAlphabetic(): Boolean = this.toU8().isAsciiAlphabetic()

    /**
     * Checks if the value is an uppercase character:
     * 0x41 'A' ..= 0x5A 'Z'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(uppercaseA.isUppercase())
     *     check(uppercaseG.isUppercase())
     *     check(!a.isUppercase())
     *     check(!g.isUppercase())
     *     check(!zero.isUppercase())
     *     check(!percent.isUppercase())
     *     check(!space.isUppercase())
     *     check(!lf.isUppercase())
     *     check(!esc.isUppercase())
     * }
     * ```
     */
    public open fun isUppercase(): Boolean = this.toU8().isAsciiUppercase()

    /**
     * Checks if the value is a lowercase character:
     * 0x61 'a' ..= 0x7A 'z'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(!uppercaseA.isLowercase())
     *     check(!uppercaseG.isLowercase())
     *     check(a.isLowercase())
     *     check(g.isLowercase())
     *     check(!zero.isLowercase())
     *     check(!percent.isLowercase())
     *     check(!space.isLowercase())
     *     check(!lf.isLowercase())
     *     check(!esc.isLowercase())
     * }
     * ```
     */
    public open fun isLowercase(): Boolean = this.toU8().isAsciiLowercase()

    /**
     * Checks if the value is an alphanumeric character:
     *
     * - 0x41 'A' ..= 0x5A 'Z', or
     * - 0x61 'a' ..= 0x7A 'z', or
     * - 0x30 '0' ..= 0x39 '9'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(uppercaseA.isAlphanumeric())
     *     check(uppercaseG.isAlphanumeric())
     *     check(a.isAlphanumeric())
     *     check(g.isAlphanumeric())
     *     check(zero.isAlphanumeric())
     *     check(!percent.isAlphanumeric())
     *     check(!space.isAlphanumeric())
     *     check(!lf.isAlphanumeric())
     *     check(!esc.isAlphanumeric())
     * }
     * ```
     */
    public open fun isAlphanumeric(): Boolean = this.toU8().isAsciiAlphanumeric()

    /**
     * Checks if the value is a decimal digit:
     * 0x30 '0' ..= 0x39 '9'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(!uppercaseA.isDigit())
     *     check(!uppercaseG.isDigit())
     *     check(!a.isDigit())
     *     check(!g.isDigit())
     *     check(zero.isDigit())
     *     check(!percent.isDigit())
     *     check(!space.isDigit())
     *     check(!lf.isDigit())
     *     check(!esc.isDigit())
     * }
     * ```
     */
    public open fun isDigit(): Boolean = this.toU8().isAsciiDigit()

    // This is blocked on two unstable features. Please ensure both are
    // stabilized before marking this method as stable.
    // @unstable(feature = "isAsciiOctdigit", issue = "101288")

    /**
     * Checks if the value is an octal digit:
     * 0x30 '0' ..= 0x37 '7'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val a = ascii.Char.SmallA
     *     val zero = ascii.Char.Digit0
     *     val seven = ascii.Char.Digit7
     *     val eight = ascii.Char.Digit8
     *     val percent = ascii.Char.PercentSign
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(!uppercaseA.isOctdigit())
     *     check(!a.isOctdigit())
     *     check(zero.isOctdigit())
     *     check(seven.isOctdigit())
     *     check(!eight.isOctdigit())
     *     check(!percent.isOctdigit())
     *     check(!lf.isOctdigit())
     *     check(!esc.isOctdigit())
     * }
     * ```
     */
    public open fun isOctdigit(): Boolean = this.toU8().isAsciiOctdigit()

    /**
     * Checks if the value is a hexadecimal digit:
     *
     * - 0x30 '0' ..= 0x39 '9', or
     * - 0x41 'A' ..= 0x46 'F', or
     * - 0x61 'a' ..= 0x66 'f'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(uppercaseA.isHexdigit())
     *     check(!uppercaseG.isHexdigit())
     *     check(a.isHexdigit())
     *     check(!g.isHexdigit())
     *     check(zero.isHexdigit())
     *     check(!percent.isHexdigit())
     *     check(!space.isHexdigit())
     *     check(!lf.isHexdigit())
     *     check(!esc.isHexdigit())
     * }
     * ```
     */
    public open fun isHexdigit(): Boolean = this.toU8().isAsciiHexdigit()

    /**
     * Checks if the value is a punctuation or symbol character
     * (i.e. not alphanumeric, whitespace, or control):
     *
     * - 0x21 ..= 0x2F `! " # $ % & ' ( ) * + , - . /`, or
     * - 0x3A ..= 0x40 `:; < = > ? @`, or
     * - 0x5B ..= 0x60  [ \ ] ^ _ `, or
     * - 0x7B ..= 0x7E `{ | } ~`
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(!uppercaseA.isPunctuation())
     *     check(!uppercaseG.isPunctuation())
     *     check(!a.isPunctuation())
     *     check(!g.isPunctuation())
     *     check(!zero.isPunctuation())
     *     check(percent.isPunctuation())
     *     check(!space.isPunctuation())
     *     check(!lf.isPunctuation())
     *     check(!esc.isPunctuation())
     * }
     * ```
     */
    public open fun isPunctuation(): Boolean = this.toU8().isAsciiPunctuation()

    /**
     * Checks if the value is a graphic character
     * (i.e. not whitespace or control):
     * 0x21 '!' ..= 0x7E '~'.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(uppercaseA.isGraphic())
     *     check(uppercaseG.isGraphic())
     *     check(a.isGraphic())
     *     check(g.isGraphic())
     *     check(zero.isGraphic())
     *     check(percent.isGraphic())
     *     check(!space.isGraphic())
     *     check(!lf.isGraphic())
     *     check(!esc.isGraphic())
     * }
     * ```
     */
    public open fun isGraphic(): Boolean = this.toU8().isAsciiGraphic()

    /**
     * Checks if the value is a whitespace character:
     * 0x20 SPACE, 0x09 HORIZONTAL TAB, 0x0A LINE FEED,
     * 0x0C FORM FEED, or 0x0D CARRIAGE RETURN.
     *
     * Rust uses the WhatWG Infra Standard's [definition of ASCII
     * whitespace][infra-aw]. There are several other definitions in
     * wide use. For instance, [the POSIX locale][pct] includes
     * 0x0B VERTICAL TAB as well as all the above characters,
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
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(!uppercaseA.isWhitespace())
     *     check(!uppercaseG.isWhitespace())
     *     check(!a.isWhitespace())
     *     check(!g.isWhitespace())
     *     check(!zero.isWhitespace())
     *     check(!percent.isWhitespace())
     *     check(space.isWhitespace())
     *     check(lf.isWhitespace())
     *     check(!esc.isWhitespace())
     * }
     * ```
     */
    public open fun isWhitespace(): Boolean = this.toU8().isAsciiWhitespace()

    /**
     * Checks if the value is a control character:
     * 0x00 NUL ..= 0x1F UNIT SEPARATOR, or 0x7F DELETE.
     * Note that most whitespace characters are control
     * characters, but SPACE is not.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val uppercaseA = ascii.Char.CapitalA
     *     val uppercaseG = ascii.Char.CapitalG
     *     val a = ascii.Char.SmallA
     *     val g = ascii.Char.SmallG
     *     val zero = ascii.Char.Digit0
     *     val percent = ascii.Char.PercentSign
     *     val space = ascii.Char.Space
     *     val lf = ascii.Char.LineFeed
     *     val esc = ascii.Char.Escape
     *     check(!uppercaseA.isControl())
     *     check(!uppercaseG.isControl())
     *     check(!a.isControl())
     *     check(!g.isControl())
     *     check(!zero.isControl())
     *     check(!percent.isControl())
     *     check(!space.isControl())
     *     check(lf.isControl())
     *     check(esc.isControl())
     * }
     * ```
     */
    public open fun isControl(): Boolean = this.toU8().isAsciiControl()

    /**
     * Returns an iterator that produces an escaped version of a
     * character.
     *
     * The behavior is identical to
     * [`ascii.escapeDefault`](ascii.escapeDefault).
     *
     * # Examples
     *
     * ```kotlin
     * @feature(asciiChar, asciiCharVariants)
     * public fun doctestEntry() {
     *     val zero = ascii.Char.Digit0
     *     val tab = ascii.Char.CharacterTabulation
     *     val cr = ascii.Char.CarriageReturn
     *     val lf = ascii.Char.LineFeed
     *     val apostrophe = ascii.Char.Apostrophe
     *     val doubleQuote = ascii.Char.QuotationMark
     *     val backslash = ascii.Char.ReverseSolidus
     *     check("0" == zero.escapeAscii().toString())
     *     check("\\t" == tab.escapeAscii().toString())
     *     check("\\r" == cr.escapeAscii().toString())
     *     check("\\n" == lf.escapeAscii().toString())
     *     check("\\'" == apostrophe.escapeAscii().toString())
     *     check("\\\"" == doubleQuote.escapeAscii().toString())
     *     check("\\\\" == backslash.escapeAscii().toString())
     * }
     * ```
     */
    public open fun escapeAscii(): EscapeDefault = escapeDefault(this.toU8())

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = String.fmt(this.asStr(), f)

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Unit> {
        fun backslash(a: AsciiChar): Pair<List<AsciiChar>, Int> = Pair(listOf(Apostrophe, Backslash, a, Apostrophe, Null, Null), 4)
        val (buf, len) =
            when {
                this == AsciiChar.Null -> backslash(AsciiChar.Digit0)
                this == AsciiChar.CharacterTabulation -> backslash(AsciiChar.SmallT)
                this == AsciiChar.CarriageReturn -> backslash(AsciiChar.SmallR)
                this == AsciiChar.LineFeed -> backslash(AsciiChar.SmallN)
                this == AsciiChar.ReverseSolidus -> backslash(AsciiChar.ReverseSolidus)
                this == AsciiChar.Apostrophe -> backslash(AsciiChar.Apostrophe)
                this.toU8().isAsciiControl() -> {
                    val HEX_DIGITS: List<AsciiChar> = "0123456789abcdef".encodeToByteArray().asAscii().unwrap()
                    val byte = this.toU8()
                    val hi = HEX_DIGITS[Int.from((byte.toUInt() shr 4).toUByte())]
                    val lo = HEX_DIGITS[Int.from(byte and 0xfu.toUByte())]
                    Pair(listOf(Apostrophe, Backslash, AsciiChar.SmallX, hi, lo, Apostrophe), 6)
                }
                else -> Pair(listOf(Apostrophe, this, Apostrophe, Null, Null, Null), 3)
            }
        return run { f.append(buf[0 until len].asStr()); Result.Ok(Unit) }
    }

    public companion object {
        /**
         * The character with the lowest ASCII code.
         */
        public val MIN: AsciiChar = AsciiChar.Null

        /**
         * The character with the highest ASCII code.
         */
        public val MAX: AsciiChar = AsciiChar.Delete

        /**
         * Creates an ASCII character from the byte `b`,
         * or returns `None` if it's too large.
         */
        public fun fromU8(b: UByte): AsciiChar? =
            if (b <= 127u.toUByte()) {
                // SAFETY: Just checked that `b` is in-range
                AsciiChar.fromU8Unchecked(b)
            } else {
                null
            }

        /**
         * Creates an ASCII character from the byte `b`,
         * without checking whether it's valid.
         *
         * # Safety
         *
         * `b` must be in `0 ..= 127`, or else this is UB.
         */
        public fun fromU8Unchecked(b: UByte): AsciiChar {
            assertUnsafePrecondition(checkLibraryUb, "`ascii::Char::from_u8_unchecked` input cannot exceed 127.", b, b, b, 127)
            // SAFETY: Our safety precondition is that `b` is in-range.
            return transmute(b)
        }

        /**
         * When passed the *number* `0`, `1`, …, `9`, returns the *character*
         * `'0'`, `'1'`, …, `'9'` respectively.
         *
         * If `d >= 10`, returns `None`.
         */
        public fun digit(d: UByte): AsciiChar? =
            if (d < 10u.toUByte()) {
                // SAFETY: Just checked it's in-range.
                AsciiChar.digitUnchecked(d)
            } else {
                null
            }

        /**
         * When passed the *number* `0`, `1`, …, `9`, returns the *character*
         * `'0'`, `'1'`, …, `'9'` respectively, without checking that it's in-range.
         *
         * # Safety
         *
         * This is immediate UB if called with `d> 64`.
         *
         * If `d >= 10` and `d <= 64`, this is allowed to return any value or panic.
         * Notably, it should not be expected to return hex digits, or any other
         * reasonable extension of the decimal digits.
         *
         * (This loose safety condition is intended to simplify soundness proofs
         * when writing code using this method, since the implementation doesn't
         * need something really specific, not to make those other arguments do
         * something useful. It might be tightened before stabilization.)
         */
        public fun digitUnchecked(d: UByte): AsciiChar {
            assertUnsafePrecondition(checkLibraryUb, "`ascii::Char::digit_unchecked` input cannot exceed 9.", d, d, d, 10)
            // SAFETY: `'0'` through `'9'` are U+00030 through U+0039,
            // so because `d` must be 64 or less the addition can return at most
            // 112 (0x70), which doesn't overflow and is within the ASCII range.
            return run {
                val byte = 48u.uncheckedAdd(d)
                AsciiChar.fromU8Unchecked(byte)
            }
        }
    }
}

public fun fromUByte(chr: AsciiChar): UByte = chr.toUByte()

public fun fromUShort(chr: AsciiChar): UShort = (chr.toUByte()).toUShort()

public fun fromUInt(chr: AsciiChar): UInt = (chr.toUByte()).toUInt()

public fun fromULong(chr: AsciiChar): ULong = (chr.toUByte()).toULong()

public fun fromU128(chr: AsciiChar): U128 = U128.fromUByte((chr.toUByte()))

public fun fromChar(chr: AsciiChar): Char = (chr.toUByte()).toInt().toChar()

/**
 * Views this slice of ASCII characters as a UTF-8 `String`.
 */
public fun List<AsciiChar>.asStr(): String {
    val asciiPtr: ConstPtr = this
    val strPtr = ConstPtr(asciiPtr.rawAddress, asciiPtr.rawMeta)
    // SAFETY: Each ASCII codepoint in UTF-8 is encoded as one single-byte
    // code unit having the same value as the ASCII byte.
    return (ObjectSlotLayout<String>()).readNative((strPtr).rawAddress)
}

/**
 * Views this slice of ASCII characters as a slice of `UByte` bytes.
 */
public fun List<AsciiChar>.asBytes(): List<UByte> = this.asStr().encodeToByteArray().map { it.toUByte() }
