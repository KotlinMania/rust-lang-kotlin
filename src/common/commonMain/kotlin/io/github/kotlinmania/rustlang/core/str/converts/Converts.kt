// port-lint: source str/converts.rs
package io.github.kotlinmania.rustlang.core.str.converts

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.validations.runUtf8Validation

// Ways to create a `String` from bytes slice.

/**
 * Converts a slice of bytes to a string slice.
 *
 * This is an alias to [str.fromUtf8].
 *
 * A string slice ([str]) is made of bytes ([u8]), and a byte slice
 * ([`&[u8]`][byteslice]) is made of bytes, so this function converts between
 * the two. Not all byte slices are valid string slices, however: [str] requires
 * that it is valid UTF-8. `fromUtf8()` checks to ensure that the bytes are valid
 * UTF-8, and then does the conversion.
 *
 * [str]: str
 * [byteslice]: slice
 *
 * If you are sure that the byte slice is valid UTF-8, and you don't want to
 * incur the overhead of the validity check, there is an unchecked version of
 * this function, [fromUtf8Unchecked], which has the same
 * behavior but skips the check.
 *
 * If you need a `String` instead of a `String`, consider
 * [`String.fromUtf8`][string].
 *
 * [string]: ../../std/string/struct.String.html#method.fromUtf8
 *
 * Because you can stack-allocate a `[UByte; N]`, and you can take a
 * [`&[u8]`][byteslice] of it, this function is one way to have a
 * stack-allocated string. There is an example of this in the
 * examples section below.
 *
 * [byteslice]: slice
 *
 * # Errors
 *
 * Returns `Err` if the slice is not UTF-8 with a description as to why the
 * provided slice is not UTF-8.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // some bytes, in a vector
 * val sparkleHeart = mutableListOf(240, 159, 146, 150)
 * // We can use the ? (try) operator to check if the bytes are valid
 * val sparkleHeart1 = String.fromUtf8(sparkleHeart).getOrElse { return Result.failure(it) }
 * check("💖" == sparkleHeart1)
 * ```
 *
 * Incorrect bytes:
 *
 * ```kotlin
 * // some invalid bytes, in a vector
 * val sparkleHeart = mutableListOf(0, 159, 146, 150)
 * check(String.fromUtf8(sparkleHeart).isErr())
 * ```
 *
 * See the docs for [Utf8Error] for more details on the kinds of
 * errors that can be returned.
 *
 * A "stack allocated string":
 *
 * ```kotlin
 * // some bytes, in a stack-allocated array
 * val sparkleHeart = listOf(240, 159, 146, 150)
 * // We know these bytes are valid, so just use `unwrap()`.
 * val sparkleHeart1: String = String.fromUtf8(sparkleHeart).unwrap()
 * check("💖" == sparkleHeart1)
 * ```
 */
public fun fromUtf8(v: List<UByte>): Result<String, Utf8Error> {
    // Note(const-Note): This should use `?` again, once it's `const`
    return run {
        val matchVal = runUtf8Validation(v)
        when (matchVal) { is Result.Ok<*> -> {
            // SAFETY: validation succeeded.
            Result.Ok(fromUtf8Unchecked(v))
        }; is Result.Err<*> -> {
            val err = matchVal.error
            Result.Err(err)
        } }
    }
}

/**
 * Converts a mutable slice of bytes to a mutable string slice.
 *
 * This is an alias to [str.fromUtf8Mut].
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // "Hello, Rust!" as a mutable vector
 * var hellorust = mutableListOf(72, 101, 108, 108, 111, 44, 32, 82, 117, 115, 116, 33)
 * // As we know these bytes are valid, we can use `unwrap()`
 * val outstr = String.fromUtf8Mut(hellorust).unwrap()
 * check("Hello, Rust!" == outstr)
 * ```
 *
 * Incorrect bytes:
 *
 * ```kotlin
 * // Some invalid bytes in a mutable vector
 * var invalid = mutableListOf(128, 223)
 * check(String.fromUtf8Mut(invalid).isErr())
 * ```
 * See the docs for [Utf8Error] for more details on the kinds of
 * errors that can be returned.
 */
public fun fromUtf8Mut(v: MutableList<UByte>): Result<String, Utf8Error> {
    // Note(const-Note): This should use `?` again, once it's `const`
    return run {
        val matchVal = runUtf8Validation(v)
        when (matchVal) { is Result.Ok<*> -> {
            // SAFETY: validation succeeded.
            Result.Ok(fromUtf8UncheckedMut(v))
        }; is Result.Err<*> -> {
            val err = matchVal.error
            Result.Err(err)
        } }
    }
}

/**
 * Converts a slice of bytes to a string slice without checking
 * that the string contains valid UTF-8.
 *
 * This is an alias to [str.fromUtf8Unchecked].
 *
 * See the safe version, [fromUtf8], for more information.
 *
 * # Safety
 *
 * The bytes passed in must be valid UTF-8.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // some bytes, in a vector
 * val sparkleHeart = mutableListOf(240, 159, 146, 150)
 * val sparkleHeart1 = String.fromUtf8Unchecked(sparkleHeart)
 * check("💖" == sparkleHeart1)
 * ```
 */
public fun fromUtf8Unchecked(v: List<UByte>): String {
    // SAFETY: the caller must guarantee that the bytes `v` are valid UTF-8.
    // Also relies on `String` and `[UByte]` having the same layout.
    return mem.transmute(v)
}

/**
 * Converts a slice of bytes to a string slice without checking
 * that the string contains valid UTF-8; mutable version.
 *
 * This is an alias to [str.fromUtf8UncheckedMut].
 *
 * # Safety
 *
 * See the immutable version, [fromUtf8Unchecked] for documentation and safety requirements.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var heart = mutableListOf(240, 159, 146, 150)
 * val heart1 = String.fromUtf8UncheckedMut(heart)
 * check("💖" == heart1)
 * ```
 */
public fun fromUtf8UncheckedMut(v: MutableList<UByte>): String {
    // SAFETY: the caller must guarantee that the bytes `v`
    // are valid UTF-8, thus the cast to `* String` is safe.
    // Also, the pointer dereference is safe because that pointer
    // comes from a reference which is guaranteed to be valid for writes.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(v)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), ObjectSlotLayout<String>())
}

/**
 * Creates a `String` from a pointer and a length.
 *
 * The pointed-to bytes must be valid UTF-8.
 * If this might not be the case, use `String.fromUtf8(slice.fromRawParts(ptr, len))`,
 * which will return an `Err` if the data isn't valid UTF-8.
 *
 * # Safety
 *
 * This function is the `String` equivalent of [`slice.fromRawParts`](slice.fromRawParts).
 * See that function's documentation for safety concerns and examples.
 *
 * The mutable version of this function is [fromRawPartsMut].
 */
public fun fromRawParts(ptr: ConstPtr, len: Int): String {
    // SAFETY: the caller must uphold the safety contract for `fromRawParts`.
    return (ObjectSlotLayout<T>()).readNative((ptr.fromRawParts(ptr, len)).rawAddress)
}

/**
 * Creates a `String` from a pointer and a length.
 *
 * The pointed-to bytes must be valid UTF-8.
 * If this might not be the case, use `String.fromUtf8Mut(slice.fromRawPartsMut(ptr, len))`,
 * which will return an `Err` if the data isn't valid UTF-8.
 *
 * # Safety
 *
 * This function is the `String` equivalent of [`slice.fromRawPartsMut`](slice.fromRawPartsMut).
 * See that function's documentation for safety concerns and examples.
 *
 * The immutable version of this function is [fromRawParts].
 */
public fun fromRawPartsMut(ptr: MutPtr, len: Int): String {
    // SAFETY: the caller must uphold the safety contract for `fromRawPartsMut`.
    return RustMutableReference(MutPtr((ptr.fromRawPartsMut(ptr, len)).rawAddress), ObjectSlotLayout<T>())
}
