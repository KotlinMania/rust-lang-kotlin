// port-lint: source x86_64/abm.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.num.rustCountOnes
import io.github.kotlinmania.rustlang.core.num.rustLeadingZeros

// Advanced Bit Manipulation (ABM) instructions
//
// The POPCNT and LZCNT have their own CPUID bits to indicate support.
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref].
// - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
//   System Instructions][amd64Ref].
//
// [Wikipedia][wikipediaBmi] provides a quick overview of the instructions
// available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wikipediaBmi]:
// https://en.wikipedia.org/wiki/BitManipulationInstructionSets#ABM_.28AdvancedBitManipulation.29

/**
 * Counts the leading most significant zero bits.
 *
 * When the operand is zero, it returns its size in bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_lzcnt_u64)
 */
public fun lzcntU64(x: ULong): ULong = rustLeadingZeros(x).toULong()

/**
 * Counts the bits that are set.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_popcnt64)
 */
public fun popcnt64(x: Long): Int = rustCountOnes(x).toInt()
