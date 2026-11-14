// port-lint: source x86/tbm.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Trailing Bit Manipulation (TBM) instruction set.
//
// The reference is [AMD64 Architecture Programmer's Manual, Volume 3:
// General-Purpose and System Instructions][amd64Ref].
//
// [Wikipedia][wikipediaBmi] provides a quick overview of the available
// instructions.
//
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wikipediaBmi]:
// https://en.wikipedia.org/wiki/BitManipulationInstructionSets#ABM_.28AdvancedBitManipulation.29

public expect fun bextriU32(a: UInt, control: UInt): UInt

/**
 * Extracts bits of `a` specified by `control` into
 * the least significant bits of the result.
 *
 * Bits `[7, 0]` of `control` specify the index to the first bit in the range to
 * be extracted, and bits `[15, 8]` specify the length of the range. For any bit
 * position in the specified range that lie beyond the MSB of the source operand,
 * zeroes will be written. If the range is empty, the result is zero.
 */
public fun bextriU32(a: UInt, control: UInt): UInt {
    run {
        check(0u <= control && control < (1u shl 16)) { "CONTROL doesn't fit in 16 bits (unsigned)" }
    }
    return bextriU32(a, control)
}

/**
 * Clears all bits below the least significant zero bit of `x`.
 *
 * If there is no zero bit in `x`, it returns zero.
 */
public fun blcfillU32(x: UInt): UInt = x and ((x + 1u))

/**
 * Sets all bits of `x` to 1 except for the least significant zero bit.
 *
 * If there is no zero bit in `x`, it sets all bits.
 */
public fun blciU32(x: UInt): UInt = x or (x + 1u).inv()

/**
 * Sets the least significant zero bit of `x` and clears all other bits.
 *
 * If there is no zero bit in `x`, it returns zero.
 */
public fun blcicU32(x: UInt): UInt = x.inv() and (x + 1u)

/**
 * Sets the least significant zero bit of `x` and clears all bits above
 * that bit.
 *
 * If there is no zero bit in `x`, it sets all the bits.
 */
public fun blcmskU32(x: UInt): UInt = x xor (x + 1u)

/**
 * Sets the least significant zero bit of `x`.
 *
 * If there is no zero bit in `x`, it returns `x`.
 */
public fun blcsU32(x: UInt): UInt = x or (x + 1u)

/**
 * Sets all bits of `x` below the least significant one.
 *
 * If there is no set bit in `x`, it sets all the bits.
 */
public fun blsfillU32(x: UInt): UInt = x or (x - 1u)

/**
 * Clears least significant bit and sets all other bits.
 *
 * If there is no set bit in `x`, it sets all the bits.
 */
public fun blsicU32(x: UInt): UInt = x.inv() or (x - 1u)

/**
 * Clears all bits below the least significant zero of `x` and sets all other
 * bits.
 *
 * If the least significant bit of `x` is `0`, it sets all bits.
 */
public fun t1mskcU32(x: UInt): UInt = x.inv() or (x + 1u)

/**
 * Sets all bits below the least significant one of `x` and clears all other
 * bits.
 *
 * If the least significant bit of `x` is 1, it returns zero.
 */
public fun tzmskU32(x: UInt): UInt = x.inv() and (x - 1u)
