// port-lint: source x86/bmi1.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.num.trailingZeros

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
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bextr_u32)
 */
public fun bextrU32(a: UInt, start: UInt, len: UInt): UInt = bextr2U32(a, (start and 0xffu) or ((len and 0xffu) shl 8u.toInt()))

/**
 * Extracts bits of `a` specified by `control` into
 * the least significant bits of the result.
 *
 * Bits `[7, 0]` of `control` specify the index to the first bit in the range
 * to be extracted, and bits `[15, 8]` specify the length of the range.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bextr2_u32)
 */
public fun bextr2U32(a: UInt, control: UInt): UInt = x86BmiBextr32(a, control)

/**
 * Bitwise logical `AND` of inverted `a` with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_andn_u32)
 */
public fun andnU32(a: UInt, b: UInt): UInt = a.inv() and b

/**
 * Extracts lowest set isolated bit.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_blsi_u32)
 */
public fun blsiU32(x: UInt): UInt = x.isolateLowestOne()

/**
 * Gets mask up to lowest set bit.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_blsmsk_u32)
 */
public fun blsmskU32(x: UInt): UInt = x xor ((x - 1u))

/**
 * Resets the lowest set bit of `x`.
 *
 * If `x` is sets CF.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_blsr_u32)
 */
public fun blsrU32(x: UInt): UInt = x and ((x - 1u))

/**
 * Counts the number of trailing least significant zero bits.
 *
 * When the source operand is `0`, it returns its size in bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tzcnt_u16)
 */
public fun tzcntU16(x: UShort): UShort = x.trailingZeros().toUShort()

/**
 * Counts the number of trailing least significant zero bits.
 *
 * When the source operand is `0`, it returns its size in bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_tzcnt_u32)
 */
public fun tzcntU32(x: UInt): UInt = x.trailingZeros()

/**
 * Counts the number of trailing least significant zero bits.
 *
 * When the source operand is `0`, it returns its size in bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_tzcnt_32)
 */
public fun mmTzcnt32(x: UInt): Int = x.trailingZeros().toInt()

public expect fun x86BmiBextr32(x: UInt, y: UInt): UInt
