// port-lint: source x86_64/bmi.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.num.rustTrailingZeros

// Bit Manipulation Instruction (BMI) Set 1.0.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [Wikipedia][wikipediaBmi] provides a quick overview of the instructions
// available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [wikipediaBmi]: https://en.wikipedia.org/wiki/BitManipulationInstructionSets#ABM_.28AdvancedBitManipulation.29

/**
 * Extracts bits in range [`start`, `start` + `length`) from `a` into
 * the least significant bits of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bextr_u64)
 */
public fun bextrU64(a: ULong, start: UInt, len: UInt): ULong = bextr2U64(a, ((start and 0xffu) or ((len and 0xffu) shl 8)).toULong())

/**
 * Extracts bits of `a` specified by `control` into
 * the least significant bits of the result.
 *
 * Bits `[7, 0]` of `control` specify the index to the first bit in the range
 * to be extracted, and bits `[15, 8]` specify the length of the range.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bextr2_u64)
 */
public fun bextr2U64(a: ULong, control: ULong): ULong = x86BmiBextr64(a, control)

/**
 * Bitwise logical `AND` of inverted `a` with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_andn_u64)
 */
public fun andnU64(a: ULong, b: ULong): ULong = a.inv() and b

// generates lots of instructions

/**
 * Extracts lowest set isolated bit.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_blsi_u64)
 */
public fun blsiU64(x: ULong): ULong = x.isolateLowestOne()

// generates lots of instructions

/**
 * Gets mask up to lowest set bit.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_blsmsk_u64)
 */
public fun blsmskU64(x: ULong): ULong = x xor ((x - 1uL))

// generates lots of instructions

/**
 * Resets the lowest set bit of `x`.
 *
 * If `x` is sets CF.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_blsr_u64)
 */
public fun blsrU64(x: ULong): ULong = x and ((x - 1uL))

/**
 * Counts the number of trailing least significant zero bits.
 *
 * When the source operand is `0`, it returns its size in bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tzcnt_u64)
 */
public fun tzcntU64(x: ULong): ULong = rustTrailingZeros(x).toULong()

/**
 * Counts the number of trailing least significant zero bits.
 *
 * When the source operand is `0`, it returns its size in bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_tzcnt_64)
 */
public fun mmTzcnt64(x: ULong): Long = rustTrailingZeros(x).toLong()

public expect fun x86BmiBextr64(x: ULong, y: ULong): ULong
