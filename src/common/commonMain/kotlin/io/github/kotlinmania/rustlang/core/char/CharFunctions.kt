// rust-kotlin: manual
package io.github.kotlinmania.rustlang.core.char

/**
 * Converts a `UInt` to a `Char`.
 */
public fun fromU32(i: UInt): RustChar? = RustChar.fromU32(i)

/**
 * Converts a `UInt` to a [RustChar].
 */
public fun fromU32Unchecked(i: UInt): RustChar = RustChar.fromU32Unchecked(i)

/**
 * Converts a digit in the given radix to a [RustChar].
 */
public fun fromDigit(num: UInt, radix: UInt): RustChar? = RustChar.fromDigit(num, radix)
