// port-lint: source x86/sse42.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

// Streaming SIMD Extensions 4.2 (SSE4.2)
//
// Extends SSE4.1 with STTNI (String and Text New Instructions).

/**
 * String contains unsigned 8-bit characters *(Default)*
 */
public val SIDD_UBYTE_OPS: Int = 0x0

/**
 * String contains unsigned 16-bit characters
 */
public val SIDD_UWORD_OPS: Int = 0x1

/**
 * String contains signed 8-bit characters
 */
public val SIDD_SBYTE_OPS: Int = 0x2

/**
 * String contains unsigned 16-bit characters
 */
public val SIDD_SWORD_OPS: Int = 0x3

/**
 * For each character in `a`, find if it is in `b` *(Default)*
 */
public val SIDD_CMP_EQUAL_ANY: Int = 0x0

/**
 * For each character in `a`, determine if
 * `b[0] <= c <= b[1] or b[1] <= c <= b[2] ...`
 */
public val SIDD_CMP_RANGES: Int = 0x4

/**
 * The strings defined by `a` and `b` are equal
 */
public val SIDD_CMP_EQUAL_EACH: Int = 0x8

/**
 * Search for the defined substring in the target
 */
public val SIDD_CMP_EQUAL_ORDERED: Int = 0xC

/**
 * Do not negate results *(Default)*
 */
public val SIDD_POSITIVE_POLARITY: Int = 0x0

/**
 * Negates results
 */
public val SIDD_NEGATIVE_POLARITY: Int = 0x10

/**
 * Do not negate results before the end of the string
 */
public val SIDD_MASKED_POSITIVE_POLARITY: Int = 0x20

/**
 * Negates results only before the end of the string
 */
public val SIDD_MASKED_NEGATIVE_POLARITY: Int = 0x30

/**
 * **Index only**: return the least significant bit *(Default)*
 */
public val SIDD_LEAST_SIGNIFICANT: Int = 0x0

/**
 * **Index only**: return the most significant bit
 */
public val SIDD_MOST_SIGNIFICANT: Int = 0x40

/**
 * **Mask only**: return the bit mask
 */
public val SIDD_BIT_MASK: Int = 0x0

/**
 * **Mask only**: return the byte mask
 */
public val SIDD_UNIT_MASK: Int = 0x40

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8`, and return the generated mask.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistrm)
 */
public fun mmCmpistrm(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<I8x16, M128i>(pcmpistrm128(a.asI8x16(), b.asI8x16(), imm8.toByte()), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())
}

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8` and return the generated index. Similar to
 * [mmCmpestri] with the exception that [mmCmpestri] requires the
 * lengths of `a` and `b` to be explicitly specified.
 *
 * # Control modes
 *
 * The control specified by `IMM8` may be one or more of the following.
 *
 * ## Data size and signedness
 *
 *  - [siddUbyteOps] - Default
 *  - [siddUwordOps]
 *  - [siddSbyteOps]
 *  - [siddSwordOps]
 *
 * ## Comparison options
 *  - [siddCmpEqualAny] - Default
 *  - [siddCmpRanges]
 *  - [siddCmpEqualEach]
 *  - [siddCmpEqualOrdered]
 *
 * ## Result polarity
 *  - [siddPositivePolarity] - Default
 *  - [siddNegativePolarity]
 *
 * ## Bit returned
 *  - [siddLeastSignificant] - Default
 *  - [siddMostSignificant]
 *
 * # Examples
 *
 * Finds a substring using [siddCmpEqualOrdered]
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import std.arch.x8664.*
 *
 * val haystack = "This is a long string of text data\r\n\tthat extends\nmultiple lines".encodeToByteArray()
 * val needle = "\r\n\t\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000".encodeToByteArray()
 * val a = mmLoaduSi128(needle.asPtr())
 * val hop = 16
 * var indexes = mutableListOf()
 * // Chunk the haystack into 16 byte chunks and find
 * // the first "\r\n\t" in the chunk.
 * for ((i, chunk) in haystack.chunks(hop).mapIndexed { index, item -> Pair(index, item) }) {
 *     val b = mmLoaduSi128(constPtrFromRustCast(chunk.asPtr()))
 *     val idx = mmCmpistri(a, b, SIDD_CMP_EQUAL_ORDERED)
 *     if (idx != 16) {
 *         indexes.add((idx.toInt()) + (i * hop))
 *     }
 * }
 * check(indexes == mutableListOf(34))
 * ```
 *
 * The `_mm_cmpistri` intrinsic may also be used to find the existence of
 * one or more of a given set of characters in the haystack.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * // Ensure your input is 16 byte aligned
 * val password = "hunter2\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000".encodeToByteArray()
 * val specialChars = "!@#\$%^&*()[]:;<>".encodeToByteArray()
 * // Load the input
 * val a = mmLoaduSi128(specialChars.asPtr())
 * val b = mmLoaduSi128(password.asPtr())
 * // Use _SIDD_CMP_EQUAL_ANY to find the index of any bytes in b
 * val idx = mmCmpistri(a.into(), b.into(), SIDD_CMP_EQUAL_ANY)
 * if (idx < 16) {
 *     println("Congrats! Your password contains a special character")
 * } else {
 *     println("Your password should contain a special character")
 * }
 * ```
 *
 * Finds the index of the first character in the haystack that is within a
 * range of characters.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * // Specify the ranges of values to be searched for [A-Za-z0-9].
 * val a = "AZaz09\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000".encodeToByteArray()
 * val a1 = mmLoaduSi128(a.asPtr())
 * // Use _SIDD_CMP_RANGES to find the index of first byte in ranges.
 * // Which in this case will be the first alpha numeric byte found
 * // in the string.
 * val idx = mmCmpistri(a1, b, SIDD_CMP_RANGES)
 * if (idx < 16) {
 *     println("Found an alpha numeric character")
 * } else {
 *     println("Did not find an alpha numeric character")
 * }
 * ```
 *
 * Working with 16-bit characters.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import std.arch.x8664.*
 *
 * // Load the input
 * val a = mmLoaduSi128(constPtrFromRustCast(someUtf16Words.asPtr()))
 * val b = mmLoaduSi128(constPtrFromRustCast(moreUtf16Words.asPtr()))
 * // Specify _SIDD_UWORD_OPS to compare words instead of bytes, and
 * // use _SIDD_CMP_EQUAL_EACH to compare the two strings.
 * val idx = mmCmpistri(a, b, SIDD_UWORD_OPS or SIDD_CMP_EQUAL_EACH)
 * if (idx == 0) {
 *     println("16-bit unicode strings were equal!")
 * } else {
 *     println("16-bit unicode strings were not equal!")
 * }
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistri)
 */
public fun mmCmpistri(a: M128i, b: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpistri128(a.asI8x16(), b.asI8x16(), imm8.toByte())
}

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8`, and return `1` if any character in `b` was null.
 * and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistrz)
 */
public fun mmCmpistrz(a: M128i, b: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpistriz128(a.asI8x16(), b.asI8x16(), imm8.toByte())
}

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8`, and return `1` if the resulting mask was non-zero,
 * and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistrc)
 */
public fun mmCmpistrc(a: M128i, b: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpistric128(a.asI8x16(), b.asI8x16(), imm8.toByte())
}

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8`, and returns `1` if any character in `a` was null,
 * and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistrs)
 */
public fun mmCmpistrs(a: M128i, b: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpistris128(a.asI8x16(), b.asI8x16(), imm8.toByte())
}

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8`, and return bit `0` of the resulting bit mask.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistro)
 */
public fun mmCmpistro(a: M128i, b: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpistrio128(a.asI8x16(), b.asI8x16(), imm8.toByte())
}

/**
 * Compares packed strings with implicit lengths in `a` and `b` using the
 * control in `IMM8`, and return `1` if `b` did not contain a null
 * character and the resulting mask was zero, and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpistra)
 */
public fun mmCmpistra(a: M128i, b: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpistria128(a.asI8x16(), b.asI8x16(), imm8.toByte())
}

/**
 * Compares packed strings in `a` and `b` with lengths `la` and `lb`
 * using the control in `IMM8`, and return the generated mask.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestrm)
 */
public fun mmCmpestrm(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmuteWithLayouts<U8x16, M128i>(pcmpestrm128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte()), UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<M128i>())
}

/**
 * Compares packed strings `a` and `b` with lengths `la` and `lb` using the
 * control in `IMM8` and return the generated index. Similar to
 * [mmCmpistri] with the exception that [mmCmpistri] implicitly
 * determines the length of `a` and `b`.
 *
 * # Control modes
 *
 * The control specified by `IMM8` may be one or more of the following.
 *
 * ## Data size and signedness
 *
 *  - [siddUbyteOps] - Default
 *  - [siddUwordOps]
 *  - [siddSbyteOps]
 *  - [siddSwordOps]
 *
 * ## Comparison options
 *  - [siddCmpEqualAny] - Default
 *  - [siddCmpRanges]
 *  - [siddCmpEqualEach]
 *  - [siddCmpEqualOrdered]
 *
 * ## Result polarity
 *  - [siddPositivePolarity] - Default
 *  - [siddNegativePolarity]
 *
 * ## Bit returned
 *  - [siddLeastSignificant] - Default
 *  - [siddMostSignificant]
 *
 * # Examples
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * // The string we want to find a substring in
 * val haystack = "Split \r\n\t line  ".encodeToByteArray()
 * // The string we want to search for with some
 * // extra bytes we do not want to search for.
 * val needle = "\r\n\t ignore this ".encodeToByteArray()
 * val a = mmLoaduSi128(needle.asPtr())
 * val b = mmLoaduSi128(haystack.asPtr())
 * // Note: We explicitly specify we only want to search `b` for the
 * // first 3 characters of a.
 * val idx = mmCmpestri(a, 3, b, 15, SIDD_CMP_EQUAL_ORDERED)
 * check(idx == 6)
 * ```
 *
 * [siddUbyteOps]: constant._SIDD_UBYTE_OPS.html
 * [siddUwordOps]: constant._SIDD_UWORD_OPS.html
 * [siddSbyteOps]: constant._SIDD_SBYTE_OPS.html
 * [siddSwordOps]: constant._SIDD_SWORD_OPS.html
 * [siddCmpEqualAny]: constant._SIDD_CMP_EQUAL_ANY.html
 * [siddCmpRanges]: constant._SIDD_CMP_RANGES.html
 * [siddCmpEqualEach]: constant._SIDD_CMP_EQUAL_EACH.html
 * [siddCmpEqualOrdered]: constant._SIDD_CMP_EQUAL_ORDERED.html
 * [siddPositivePolarity]: constant._SIDD_POSITIVE_POLARITY.html
 * [siddNegativePolarity]: constant._SIDD_NEGATIVE_POLARITY.html
 * [siddLeastSignificant]: constant._SIDD_LEAST_SIGNIFICANT.html
 * [siddMostSignificant]: constant._SIDD_MOST_SIGNIFICANT.html
 * [mmCmpistri]: fn._mm_cmpistri.html
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestri)
 */
public fun mmCmpestri(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpestri128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte())
}

/**
 * Compares packed strings in `a` and `b` with lengths `la` and `lb`
 * using the control in `IMM8`, and return `1` if any character in
 * `b` was null, and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestrz)
 */
public fun mmCmpestrz(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpestriz128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte())
}

/**
 * Compares packed strings in `a` and `b` with lengths `la` and `lb`
 * using the control in `IMM8`, and return `1` if the resulting mask
 * was non-zero, and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestrc)
 */
public fun mmCmpestrc(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpestric128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte())
}

/**
 * Compares packed strings in `a` and `b` with lengths `la` and `lb`
 * using the control in `IMM8`, and return `1` if any character in
 * a was null, and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestrs)
 */
public fun mmCmpestrs(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpestris128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte())
}

/**
 * Compares packed strings in `a` and `b` with lengths `la` and `lb`
 * using the control in `IMM8`, and return bit `0` of the resulting
 * bit mask.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestro)
 */
public fun mmCmpestro(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpestrio128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte())
}

/**
 * Compares packed strings in `a` and `b` with lengths `la` and `lb`
 * using the control in `IMM8`, and return `1` if `b` did not
 * contain a null character and the resulting mask was zero, and `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpestra)
 */
public fun mmCmpestra(a: M128i, la: Int, b: M128i, lb: Int, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pcmpestria128(a.asI8x16(), la, b.asI8x16(), lb, imm8.toByte())
}

/**
 * Starting with the initial value in `crc`, return the accumulated
 * CRC32-C value for unsigned 8-bit integer `v`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_crc32_u8)
 */
public fun mmCrc32U8(crc: UInt, v: UByte): UInt = crc32328(crc, v)

/**
 * Starting with the initial value in `crc`, return the accumulated
 * CRC32-C value for unsigned 16-bit integer `v`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_crc32_u16)
 */
public fun mmCrc32U16(crc: UInt, v: UShort): UInt = crc323216(crc, v)

/**
 * Starting with the initial value in `crc`, return the accumulated
 * CRC32-C value for unsigned 32-bit integer `v`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_crc32_u32)
 */
public fun mmCrc32U32(crc: UInt, v: UInt): UInt = crc323232(crc, v)

/**
 * Compares packed 64-bit integers in `a` and `b` for greater-than,
 * return the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epi64)
 */
public fun mmCmpgtEpi64(a: M128i, b: M128i): M128i = transmute(run { val _simdArg0 = a.asI64x2(); val _simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } })

// SSE 4.2 string and text comparison ops
public expect fun pcmpestrm128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): U8x16

public expect fun pcmpestri128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int

public expect fun pcmpestriz128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int

public expect fun pcmpestric128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int

public expect fun pcmpestris128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int

public expect fun pcmpestrio128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int

public expect fun pcmpestria128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int

public expect fun pcmpistrm128(a: I8x16, b: I8x16, imm8: Byte): I8x16

public expect fun pcmpistri128(a: I8x16, b: I8x16, imm8: Byte): Int

public expect fun pcmpistriz128(a: I8x16, b: I8x16, imm8: Byte): Int

public expect fun pcmpistric128(a: I8x16, b: I8x16, imm8: Byte): Int

public expect fun pcmpistris128(a: I8x16, b: I8x16, imm8: Byte): Int

public expect fun pcmpistrio128(a: I8x16, b: I8x16, imm8: Byte): Int

public expect fun pcmpistria128(a: I8x16, b: I8x16, imm8: Byte): Int

// SSE 4.2 CRC instructions
public expect fun crc32328(crc: UInt, v: UByte): UInt

public expect fun crc323216(crc: UInt, v: UShort): UInt

public expect fun crc323232(crc: UInt, v: UInt): UInt
