// port-lint: source num/float_parse.rs
package io.github.kotlinmania.rustlang.core.num.floatparse

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.dec2flt
import io.github.kotlinmania.rustlang.core.result.Result

// User-facing API for float parsing.

/**
 * Converts a string in base 10 to a float.
 * Accepts an optional decimal exponent.
 *
 * This function accepts strings such as
 *
 * * '3.14'
 * * '-3.14'
 * * '2.5E10', or equivalently, '2.5e10'
 * * '2.5E-10'
 * * '5.'
 * * '.5', or, equivalently, '0.5'
 * * '7'
 * * '007'
 * * 'inf', '-inf', '+infinity', 'NaN'
 *
 * Note that alphabetical characters are not case-sensitive.
 *
 * Leading and trailing whitespace represent an error.
 *
 * # Grammar
 *
 * All strings that adhere to the following [EBNF] grammar when
 * lowercased will result in an [Ok] being returned:
 *
 * ```kotlin
 * Float  ::= Sign? ( 'inf' | 'infinity' | 'nan' | Number )
 * Number ::= ( Digit+ |
 * Digit + '.' Digit * |
 *              Digit* '.' Digit+ ) Exp?
 * Exp.= 'e' Sign Digit +
 * Sign.= [+ -]
 * Digit.= [0 - 9]
 * ```
 *
 * [EBNF]: https://www.w3.org/TR/REC-xml/#sec-notation
 *
 * # Arguments
 *
 * * src - A string
 *
 * # Return value
 *
 * `Err(ParseFloatError)` if the string did not represent a valid
 * number. Otherwise, `Ok(n)` where `n` is the closest
 * representable floating-point number to the number represented
 * by `src` (following the same rules for rounding as for the
 * results of primitive operations).
 */
public fun fromStrF16(src: String): Result<F16, ParseFloatError> = dec2flt.dec2flt(src)

/**
 * Converts a string in base 10 to a float.
 * Accepts an optional decimal exponent.
 *
 * This function accepts strings such as
 *
 * * '3.14'
 * * '-3.14'
 * * '2.5E10', or equivalently, '2.5e10'
 * * '2.5E-10'
 * * '5.'
 * * '.5', or, equivalently, '0.5'
 * * '7'
 * * '007'
 * * 'inf', '-inf', '+infinity', 'NaN'
 *
 * Note that alphabetical characters are not case-sensitive.
 *
 * Leading and trailing whitespace represent an error.
 *
 * # Grammar
 *
 * All strings that adhere to the following [EBNF] grammar when
 * lowercased will result in an [Ok] being returned:
 *
 * ```kotlin
 * Float  ::= Sign? ( 'inf' | 'infinity' | 'nan' | Number )
 * Number ::= ( Digit+ |
 * Digit + '.' Digit * |
 *              Digit* '.' Digit+ ) Exp?
 * Exp.= 'e' Sign Digit +
 * Sign.= [+ -]
 * Digit.= [0 - 9]
 * ```
 *
 * [EBNF]: https://www.w3.org/TR/REC-xml/#sec-notation
 *
 * # Arguments
 *
 * * src - A string
 *
 * # Return value
 *
 * `Err(ParseFloatError)` if the string did not represent a valid
 * number. Otherwise, `Ok(n)` where `n` is the closest
 * representable floating-point number to the number represented
 * by `src` (following the same rules for rounding as for the
 * results of primitive operations).
 */
public fun fromStrFloat(src: String): Result<kotlin.Float, ParseFloatError> = dec2flt.dec2flt(src)

/**
 * Converts a string in base 10 to a float.
 * Accepts an optional decimal exponent.
 *
 * This function accepts strings such as
 *
 * * '3.14'
 * * '-3.14'
 * * '2.5E10', or equivalently, '2.5e10'
 * * '2.5E-10'
 * * '5.'
 * * '.5', or, equivalently, '0.5'
 * * '7'
 * * '007'
 * * 'inf', '-inf', '+infinity', 'NaN'
 *
 * Note that alphabetical characters are not case-sensitive.
 *
 * Leading and trailing whitespace represent an error.
 *
 * # Grammar
 *
 * All strings that adhere to the following [EBNF] grammar when
 * lowercased will result in an [Ok] being returned:
 *
 * ```kotlin
 * Float  ::= Sign? ( 'inf' | 'infinity' | 'nan' | Number )
 * Number ::= ( Digit+ |
 * Digit + '.' Digit * |
 *              Digit* '.' Digit+ ) Exp?
 * Exp.= 'e' Sign Digit +
 * Sign.= [+ -]
 * Digit.= [0 - 9]
 * ```
 *
 * [EBNF]: https://www.w3.org/TR/REC-xml/#sec-notation
 *
 * # Arguments
 *
 * * src - A string
 *
 * # Return value
 *
 * `Err(ParseFloatError)` if the string did not represent a valid
 * number. Otherwise, `Ok(n)` where `n` is the closest
 * representable floating-point number to the number represented
 * by `src` (following the same rules for rounding as for the
 * results of primitive operations).
 */
public fun fromStrDouble(src: String): Result<Double, ParseFloatError> = dec2flt.dec2flt(src)

// Note(f16): A fallback is used when the backend+target does not support f16 well, in order
// to avoid ICEs.

/**
 * An error which can be returned when parsing a float.
 *
 * This error is used as the error type for the [FromStr] implementation
 * for [f32] and [f64].
 *
 * # Example
 *
 * ```kotlin
 * import std.str.FromStr
 *
 * when (val matchVal = Double.fromStr("a.12")) {
 *     is Err -> {
 *         val (e) = matchVal
 *         println("Failed conversion to f64: $e")
 *     }
 *     else -> {}
 * }
 * ```
 */
public open class ParseFloatError(
    internal var kind: FloatErrorKind,
) {
    /**
     * Outputs the detailed cause of parsing a float failing.
     */
    public open fun kind(): FloatErrorKind = this.kind

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this.kind) {
            FloatErrorKind.Empty -> "cannot parse float from empty string"
            FloatErrorKind.Invalid -> "invalid float literal"
        }.fmt(f)
}

/**
 * An error kind which can be returned when parsing a float.
 *
 * This error kind is used as the inner error type in [ParseFloatError] for the [FromStr] implementation
 * for [f32] and [f64].
 *
 * # Example
 *
 * ```kotlin
 * import std.str.FromStr
 *
 * @feature(extendedFloatErrorKind)
 * public fun doctestEntry() {
 *     when (val matchVal = Double.fromStr("a.12")) {
 *         is Err -> {
 *             val (e) = matchVal
 *             println("Failed conversion to f64: ${e.kind()}")
 *         }
 *         else -> {}
 *     }
 * }
 * ```
 */
public sealed class FloatErrorKind {
    /**
     * Cannot parse float from empty string.
     */
    public data object Empty : FloatErrorKind()
    /**
     * Invalid float literal.
     */
    public data object Invalid : FloatErrorKind()

    public fun rustEquals(other: FloatErrorKind): Boolean = this == other
}
