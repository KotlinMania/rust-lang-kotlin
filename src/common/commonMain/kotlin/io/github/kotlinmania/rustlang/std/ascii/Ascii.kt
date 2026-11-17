// port-lint: source ascii.rs
package io.github.kotlinmania.rustlang.std.ascii

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.char.methods.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.methods.toAsciiUppercase

// Operations on ASCII strings and characters.
//
// Most string operations in Rust act on UTF-8 strings. However, at times it
// makes more sense to only consider the ASCII character set for a specific
// operation.
//
// The [AsciiExt] trait provides methods that allow for character
// operations that only act on the ASCII subset and leave non-ASCII characters
// alone.
//
// The [escapeDefault] function provides an iterator over the bytes of an
// escaped version of the character given.

/**
 * Extension methods for ASCII-subset only operations.
 *
 * Be aware that operations on seemingly non-ASCII characters can sometimes
 * have unexpected results. Consider this example:
 *
 * ```kotlin
 * import std.ascii.AsciiExt
 *
 * check(AsciiExt.toAsciiUppercase("café") == "CAFÉ")
 * check(AsciiExt.toAsciiUppercase("café") == "CAFé")
 * ```
 *
 * In the first example, the lowercased string is represented `"cafe\u(301)"`
 * (the last character is an acute accent [combining character]). Unlike the
 * other characters in the string, the combining character will not get mapped
 * to an uppercase variant, resulting in `"CAFE\u(301)"`. In the second
 * example, the lowercased string is represented `"caf\u(e9)"` (the last
 * character is a single Unicode character representing an `e` with an acute
 * accent). Since the last character is defined outside the scope of ASCII,
 * it will not get mapped to an uppercase variant, resulting in `"CAF\u(e9)"`.
 *
 * [combining character]: https://en.wikipedia.org/wiki/CombiningCharacter
 */
public interface AsciiExt<T, out Owned> {
    /**
     * Checks if the value is within the ASCII range.
     *
     * # Note
     *
     * This method is deprecated in favor of the identically-named
     * inherent methods on `UByte`, `char`, `[UByte]` and `String`.
     */
    public fun isAscii(): Boolean

    /**
     * Makes a copy of the value in its ASCII upper case equivalent.
     *
     * ASCII letters `a` to `z` are mapped to `A` to `Z`,
     * but non-ASCII letters are unchanged.
     *
     * To uppercase the value in-place, use [makeAsciiUppercase].
     *
     * To uppercase ASCII characters in addition to non-ASCII characters, use
     * [str.toUppercase].
     *
     * # Note
     *
     * This method is deprecated in favor of the identically-named
     * inherent methods on `UByte`, `char`, `[UByte]` and `String`.
     *
     * [makeAsciiUppercase]: AsciiExt.makeAsciiUppercase
     */
    public fun toAsciiUppercase(): Owned

    /**
     * Makes a copy of the value in its ASCII lower case equivalent.
     *
     * ASCII letters `A` to `Z` are mapped to `a` to `z`,
     * but non-ASCII letters are unchanged.
     *
     * To lowercase the value in-place, use [makeAsciiLowercase].
     *
     * To lowercase ASCII characters in addition to non-ASCII characters, use
     * [str.toLowercase].
     *
     * # Note
     *
     * This method is deprecated in favor of the identically-named
     * inherent methods on `UByte`, `char`, `[UByte]` and `String`.
     *
     * [makeAsciiLowercase]: AsciiExt.makeAsciiLowercase
     */
    public fun toAsciiLowercase(): Owned

    /**
     * Checks that two values are an ASCII case-insensitive match.
     *
     * Same as `toAsciiLowercase(a) == toAsciiLowercase(b)`,
     * but without allocating and copying temporaries.
     *
     * # Note
     *
     * This method is deprecated in favor of the identically-named
     * inherent methods on `UByte`, `char`, `[UByte]` and `String`.
     */
    public fun eqIgnoreAsciiCase(other: T): Boolean

    /**
     * Converts this type to its ASCII upper case equivalent in-place.
     *
     * ASCII letters `a` to `z` are mapped to `A` to `Z`,
     * but non-ASCII letters are unchanged.
     *
     * To return a new uppercased value without modifying the existing one, use
     * [toAsciiUppercase].
     *
     * # Note
     *
     * This method is deprecated in favor of the identically-named
     * inherent methods on `UByte`, `char`, `[UByte]` and `String`.
     *
     * [toAsciiUppercase]: AsciiExt.toAsciiUppercase
     */
    public fun makeAsciiUppercase()

    /**
     * Converts this type to its ASCII lower case equivalent in-place.
     *
     * ASCII letters `A` to `Z` are mapped to `a` to `z`,
     * but non-ASCII letters are unchanged.
     *
     * To return a new lowercased value without modifying the existing one, use
     * [toAsciiLowercase].
     *
     * # Note
     *
     * This method is deprecated in favor of the identically-named
     * inherent methods on `UByte`, `char`, `[UByte]` and `String`.
     *
     * [toAsciiLowercase]: AsciiExt.toAsciiLowercase
     */
    public fun makeAsciiLowercase()
}

public fun UByte.isAsciiAsciiExt(): Boolean = this.isAscii()

public fun UByte.toAsciiUppercaseAsciiExt(): UByte = this.toAsciiUppercase()

public fun UByte.toAsciiLowercaseAsciiExt(): UByte = this.toAsciiLowercase()

public fun UByte.eqIgnoreAsciiCaseAsciiExt(o: UByte): Boolean = this.eqIgnoreAsciiCase(o)

public fun UByte.makeAsciiUppercaseAsciiExt() {
    this.makeAsciiUppercase()
}

public fun UByte.makeAsciiLowercaseAsciiExt() {
    this.makeAsciiLowercase()
}

public fun Char.isAsciiAsciiExt(): Boolean = this.isAscii()

public fun Char.toAsciiUppercaseAsciiExt(): Char = this.toAsciiUppercase()

public fun Char.toAsciiLowercaseAsciiExt(): Char = this.toAsciiLowercase()

public fun Char.eqIgnoreAsciiCaseAsciiExt(o: Char): Boolean = this.eqIgnoreAsciiCase(o)

public fun Char.makeAsciiUppercaseAsciiExt() {
    this.makeAsciiUppercase()
}

public fun Char.makeAsciiLowercaseAsciiExt() {
    this.makeAsciiLowercase()
}

public fun List<UByte>.isAsciiAsciiExt(): Boolean = this.isAscii()

public fun List<UByte>.toAsciiUppercaseAsciiExt(): List<UByte> = this.toAsciiUppercase()

public fun List<UByte>.toAsciiLowercaseAsciiExt(): List<UByte> = this.toAsciiLowercase()

public fun List<UByte>.eqIgnoreAsciiCaseAsciiExt(o: List<UByte>): Boolean = this.eqIgnoreAsciiCase(o)

public fun List<UByte>.makeAsciiUppercaseAsciiExt() {
    this.makeAsciiUppercase()
}

public fun List<UByte>.makeAsciiLowercaseAsciiExt() {
    this.makeAsciiLowercase()
}

public fun String.isAsciiAsciiExt(): Boolean = this.isAscii()

public fun String.toAsciiUppercaseAsciiExt(): String = this.toAsciiUppercase()

public fun String.toAsciiLowercaseAsciiExt(): String = this.toAsciiLowercase()

public fun String.eqIgnoreAsciiCaseAsciiExt(o: String): Boolean = this.eqIgnoreAsciiCase(o)

public fun String.makeAsciiUppercaseAsciiExt() {
    this.makeAsciiUppercase()
}

public fun String.makeAsciiLowercaseAsciiExt() {
    this.makeAsciiLowercase()
}
