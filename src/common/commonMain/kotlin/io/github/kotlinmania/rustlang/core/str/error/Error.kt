// rust-kotlin: manual
// port-lint: source str/error.rs
package io.github.kotlinmania.rustlang.core.str.error

import io.github.kotlinmania.rustlang.core.result.Result

// Defines utf8 error type.

/**
 * Errors which can occur when attempting to interpret a sequence of [u8]
 * as a string.
 *
 * As such, the `fromUtf8` family of functions and methods for both [String]s
 * and [str]s make use of this error, for example.
 *
 * [String]: ../../std/string/struct.String.html#method.fromUtf8
 *
 * # Examples
 *
 * This error type’s methods can be used to create functionality
 * similar to `String.fromUtf8Lossy` without allocating heap memory:
 *
 * ```kotlin
 * fun fromUtf8Lossy(input: List<UByte>, push: (String) -> Unit) {
 *     var input = input
 *     while (true) {
 *         run {
 *             val matchVal = String.fromUtf8(input)
 *             if (matchVal.isSuccess) {
 *                 val valid = matchVal.getOrThrow()
 *                 push(valid)
 *                 break
 *             } else {
 *                 val error = matchVal.exceptionOrNull()!!
 *                 val (valid1, afterValid) = input.splitAt(error.validUpTo())
 *                 push(String.fromUtf8Unchecked(valid1))
 *                 push("�")
 *                 when (val matchVal1 = error.errorLen()) {
 *                     null -> {
 *                         break
 *                     }
 *                     else -> {
 *                         val invalidSequenceLength = matchVal1
 *                         input = afterValid[invalidSequenceLength until afterValid.size]
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * ```
 */
public open class Utf8Error(
    validUpTo: Int,
    errorLen: UByte?,
) {
    internal var validUpTo: Int = validUpTo
    internal var errorLen: UByte? = errorLen

    public open fun clone(): Utf8Error = Utf8Error(validUpTo, errorLen)

    /**
     * Returns the index in the given string up to which valid UTF-8 was
     * verified.
     *
     * It is the maximum index such that `fromUtf8(input[.. index])`
     * would return `Ok(_)`.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import std.str
     *
     * // some invalid bytes, in a vector
     * val sparkleHeart = mutableListOf(0, 159, 146, 150)
     * // std.str.fromUtf8 returns a Utf8Error
     * val error = String.fromUtf8(sparkleHeart).unwrapErr()
     * // the second byte is invalid here
     * check(1 == error.validUpTo())
     * ```
     */
    public open fun validUpTo(): Int = this.validUpTo

    /**
     * Provides more information about the failure:
     *
     * * `None`: the end of the input was reached unexpectedly.
     *   `self.validUpTo()` is 1 to 3 bytes from the end of the input.
     *   If a byte stream (such as a file or a network socket) is being decoded incrementally,
     *   this could be a valid `char` whose UTF-8 byte sequence is spanning multiple chunks.
     *
     * * `Some(len)`: an unexpected byte was encountered.
     *   The length provided is that of the invalid byte sequence
     *   that starts at the index given by `validUpTo()`.
     *   Decoding should resume after that sequence
     *   (after inserting a [`U.Fffd.Replacement.Character`][U+FFFD]) in case of
     *   lossy decoding.
     *
     * [U+FFFD]: ../../std/char/constant.REPLACEMENT_CHARACTER.html
     */
    public open fun errorLen(): Int? {
        // Note(const-Note): This should become `map` again, once it's `const`
        return when (val matchVal = this.errorLen) {
            null -> null
            else -> {
                val len = matchVal
                len.toInt()
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (val matchVal = this.errorLen) {
            null -> {
                run {
                    f.append("incomplete utf-8 byte sequence from index ${this.validUpTo}")
                    Result.Ok<Unit, Unit>(Unit)
                }
            }
            else -> {
                val errorLen = matchVal
                run {
                    f.append("invalid utf-8 sequence of $errorLen bytes from index ${this.validUpTo}")
                    Result.Ok<Unit, Unit>(Unit)
                }
            }
        }

    public companion object
}

/**
 * An error returned when parsing a `Boolean` using [fromStr] fails
 */
public open class ParseBoolError {
    public open fun clone(): ParseBoolError = ParseBoolError()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append("provided string was not `true` or `false`")
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object
}
