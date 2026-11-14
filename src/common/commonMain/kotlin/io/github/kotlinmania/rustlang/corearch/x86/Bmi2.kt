// port-lint: source x86/bmi2.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Bit Manipulation Instruction (BMI) Set 2.0.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [Wikipedia][wikipediaBmi] provides a quick overview of the instructions
// available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [wikipediaBmi]:
// https://en.wikipedia.org/wiki/BitManipulationInstructionSets#ABM_.28AdvancedBitManipulation.29

// LLVM BUG (should be mulxl): https://bugs.llvm.org/showBug.cgi?id=34232

/**
 * Unsigned multiply without affecting flags.
 *
 * Unsigned multiplication of `a` with `b` returning a pair `(lo, hi)` with
 * the low half and the high half of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mulx_u32)
 */
public fun mulxU32(a: UInt, b: UInt, hi: RustMutableReference<UInt>): UInt {
    val hi1 = hi
    val result: ULong = (a.toULong()) * (b.toULong())
    (hi1).write((result shr 32).toUInt())
    return result.toUInt()
}

/**
 * Zeroes higher bits of `a` >= `index`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bzhi_u32)
 */
public fun bzhiU32(a: UInt, index: UInt): UInt = x86Bmi2Bzhi32(a, index)

/**
 * Scatter contiguous low order bits of `a` to the result at the positions
 * specified by the `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_pdep_u32)
 */
public fun pdepU32(a: UInt, mask: UInt): UInt = x86Bmi2Pdep32(a, mask)

/**
 * Gathers the bits of `x` specified by the `mask` into the contiguous low
 * order bit positions of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_pext_u32)
 */
public fun pextU32(a: UInt, mask: UInt): UInt = x86Bmi2Pext32(a, mask)

public expect fun x86Bmi2Bzhi32(x: UInt, y: UInt): UInt

public expect fun x86Bmi2Pdep32(x: UInt, y: UInt): UInt

public expect fun x86Bmi2Pext32(x: UInt, y: UInt): UInt
