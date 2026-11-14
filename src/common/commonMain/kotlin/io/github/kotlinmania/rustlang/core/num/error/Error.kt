// port-lint: source num/error.rs
package io.github.kotlinmania.rustlang.core.num.error

import io.github.kotlinmania.rustlang.core.result.Result

// Error types for conversion to integral types.

/**
 * The error type returned when a checked integral type conversion fails.
 */
public open class TryFromIntError(
    internal var value: IntErrorKind,
) {
    public var field0: IntErrorKind
        get() = value
        set(v) {
            value = v
        }

    /**
     * Outputs the detailed cause of converting an integer failing.
     */
    public open fun kind(): IntErrorKind = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this.value) {
            IntErrorKind.Empty, IntErrorKind.InvalidDigit -> error("unreachable")
            IntErrorKind.PosOverflow -> "number too large to fit in target type"
            IntErrorKind.NegOverflow -> "number too small to fit in target type"
            IntErrorKind.Zero -> "number would be zero for non-zero type"
            IntErrorKind.NotAPowerOfTwo -> "number is not a power of two"
        }.fmt(f)

    public companion object {
        public fun from(never: Nothing): TryFromIntError = never
    }
}

/**
 * An error which can be returned when parsing an integer.
 *
 * For example, this error is returned by the `fromStrRadix()` functions
 * on the primitive integer types (such as [i8.fromStrRadix])
 * and is used as the error type in their [FromStr] implementations.
 *
 * # Potential causes
 *
 * Among other causes, `ParseIntError` can be thrown because of leading or trailing whitespace
 * in the string e.g., when it is obtained from the standard input.
 * Using the [str.trim] method ensures that no whitespace remains before parsing.
 *
 * # Example
 *
 * ```kotlin
 * when (val matchVal = Int.fromStrRadix("a12", 10)) {
 *     is Err -> {
 *         val (e) = matchVal
 *         println("Failed conversion to i32: $e")
 *     }
 *     else -> {}
 * }
 * ```
 */
public open class ParseIntError(
    internal var kind: IntErrorKind,
) {
    /**
     * Outputs the detailed cause of parsing an integer failing.
     */
    public open fun kind(): IntErrorKind = this.kind

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this.kind) {
            IntErrorKind.Empty -> "cannot parse integer from empty string"
            IntErrorKind.InvalidDigit -> "invalid digit found in string"
            IntErrorKind.PosOverflow -> "number too large to fit in target type"
            IntErrorKind.NegOverflow -> "number too small to fit in target type"
            IntErrorKind.Zero -> "number would be zero for non-zero type"
            IntErrorKind.NotAPowerOfTwo -> "number is not a power of two"
        }.fmt(f)
}

/**
 * Enum to store the various types of errors that can cause parsing or converting an
 * integer to fail.
 *
 * # Example
 *
 * ```kotlin
 * when (val matchVal = Int.fromStrRadix("a12", 10)) {
 *     is Err -> {
 *         val (e) = matchVal
 *         println("Failed conversion to i32: ${e.kind()}")
 *     }
 *     else -> {}
 * }
 * ```
 */
public sealed class IntErrorKind {
    /**
     * Value being parsed is empty.
     *
     * This variant will be constructed when parsing an empty string.
     */
    public data object Empty : IntErrorKind()
    /**
     * Contains an invalid digit in its context.
     *
     * Among other causes, this variant will be constructed when parsing a string that
     * contains a non-ASCII char.
     *
     * This variant is also constructed when a `+` or `-` is misplaced within a string
     * either on its own or in the middle of a number.
     */
    public data object InvalidDigit : IntErrorKind()
    /**
     * Integer is too large to store in target integer type.
     */
    public data object PosOverflow : IntErrorKind()
    /**
     * Integer is too small to store in target integer type.
     */
    public data object NegOverflow : IntErrorKind()
    /**
     * Value was Zero
     *
     * This variant will be emitted when the parsing string or the converting integer
     * has a value of zero, which would be illegal for non-zero types.
     */
    public data object Zero : IntErrorKind()
    /**
     * Value is not a power of two.
     *
     * This variant will be emitted when converting an integer that is not a power of
     * two. This is required in some cases such as constructing an [Alignment].
     */
    public data object NotAPowerOfTwo : IntErrorKind()

    public fun rustEquals(other: IntErrorKind): Boolean = this == other
}
