// port-lint: tests x86_64/tbm.rs
package io.github.kotlinmania.rustlang.corearch.x8664

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

public fun testBextriU64() {
    check(bextriU64(0x50uL, 0x0404) == 0x5uL)
}

public fun testBlcfillU64() {
    check(blcfillU64(0x57uL) == 0x50uL)
    check(blcfillU64(0xFFuL) == 0uL)
}

public fun testBlciU64() {
    check(blciU64(0x50uL) == 0xFFFFFFFFFFFFFFFEuL)
    check(blciU64(0xFFuL) == 0xFFFFFFFFFFFFFEFFuL)
}

public fun testBlcicU64() {
    check(blcicU64(0x51uL) == 0x2uL)
    check(blcicU64(0xFFuL) == 0x100uL)
}

public fun testBlcmskU64() {
    check(blcmskU64(0x51uL) == 0x3uL)
    check(blcmskU64(0xFFuL) == 0x1FFuL)
}

public fun testBlcsU64() {
    check(blcsU64(0x51uL) == 0x53uL)
    check(blcsU64(0xFFuL) == 0x1FFuL)
}

public fun testBlsfillU64() {
    check(blsfillU64(0x54uL) == 0x57uL)
    check(blsfillU64(0uL) == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testBlsicU64() {
    check(blsicU64(0x54uL) == 0xFFFFFFFFFFFFFFFBuL)
    check(blsicU64(0uL) == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testT1mskcU64() {
    check(t1mskcU64(0x57uL) == 0xFFFFFFFFFFFFFFF8uL)
    check(t1mskcU64(0uL) == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testTzmskU64() {
    check(tzmskU64(0x58uL) == 0x7uL)
    check(tzmskU64(0x59uL) == 0x0uL)
}
