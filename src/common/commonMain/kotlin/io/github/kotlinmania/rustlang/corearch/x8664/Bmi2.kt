// port-lint: source x86_64/bmi2.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.U128
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

// calls an intrinsic

/**
 * Unsigned multiply without affecting flags.
 *
 * Unsigned multiplication of `a` with `b` returning a pair `(lo, hi)` with
 * the low half and the high half of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mulx_u64)
 */
public fun mulxU64(a: ULong, b: ULong, hi: RustMutableReference<ULong>): ULong {
    val hi1 = hi
    val result: U128 = (U128.fromULong(a)) * (U128.fromULong(b))
    (hi1).write((result shr 64).toULong())
    return result.toULong()
}

/**
 * Zeroes higher bits of `a` >= `index`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bzhi_u64)
 */
public fun bzhiU64(a: ULong, index: UInt): ULong = x86Bmi2Bzhi64(a, index.toULong())

/**
 * Scatter contiguous low order bits of `a` to the result at the positions
 * specified by the `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_pdep_u64)
 */
public fun pdepU64(a: ULong, mask: ULong): ULong = x86Bmi2Pdep64(a, mask)

/**
 * Gathers the bits of `x` specified by the `mask` into the contiguous low
 * order bit positions of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_pext_u64)
 */
public fun pextU64(a: ULong, mask: ULong): ULong = x86Bmi2Pext64(a, mask)

public expect fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong

public expect fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong

public expect fun x86Bmi2Pext64(x: ULong, y: ULong): ULong
