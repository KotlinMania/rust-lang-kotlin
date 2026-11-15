package io.github.kotlinmania.rustlang.core.char

import io.github.kotlinmania.rustlang.core.char.decode.DecodeUtf16

/**
 * Creates an iterator over the UTF-16 encoded code points in `iter`, returning
 * unpaired surrogates as `Err`s. Use [char.decodeUtf16] instead.
 */
public fun <I : Iterable<UShort>> decodeUtf16(iter: I): DecodeUtf16<IntoIter> = DecodeUtf16(iter)

/**
 * Converts a `UInt` to a `char`. Use [char.fromU32] instead.
 */
public fun fromU32(i: UInt): Char? = io.github.kotlinmania.rustlang.char.fromU32(i)

/**
 * Converts a `UInt` to a `char`, ignoring validity. Use [char.fromU32Unchecked]
 * instead.
 */
public fun fromU32Unchecked(i: UInt): Char {
    // SAFETY: the safety contract must be upheld by the caller.
    return io.github.kotlinmania.rustlang.char.fromU32Unchecked(i)
}

/**
 * Converts a digit in the given radix to a `char`. Use [char.fromDigit] instead.
 */
public fun fromDigit(num: UInt, radix: UInt): Char? = io.github.kotlinmania.rustlang.char.fromDigit(num, radix)
