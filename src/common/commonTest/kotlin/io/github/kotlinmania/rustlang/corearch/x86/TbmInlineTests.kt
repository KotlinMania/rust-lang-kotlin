// port-lint: tests x86/tbm.rs
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

public fun testBextriU32() {
    check(bextriU32(0x50u, 0x0404) == 0x5u)
}

public fun testBlcfillU32() {
    check(blcfillU32(0x57u) == 0x50u)
    check(blcfillU32(0xFFu) == 0u)
}

public fun testBlciU32() {
    check(blciU32(0x50u) == 0xFFFFFFFEu)
    check(blciU32(0xFFu) == 0xFFFFFEFFu)
}

public fun testBlcicU32() {
    check(blcicU32(0x51u) == 0x2u)
    check(blcicU32(0xFFu) == 0x100u)
}

public fun testBlcmskU32() {
    check(blcmskU32(0x51u) == 0x3u)
    check(blcmskU32(0xFFu) == 0x1FFu)
}

public fun testBlcsU32() {
    check(blcsU32(0x51u) == 0x53u)
    check(blcsU32(0xFFu) == 0x1FFu)
}

public fun testBlsfillU32() {
    check(blsfillU32(0x54u) == 0x57u)
    check(blsfillU32(0u) == 0xFFFFFFFFu)
}

public fun testBlsicU32() {
    check(blsicU32(0x54u) == 0xFFFFFFFBu)
    check(blsicU32(0u) == 0xFFFFFFFFu)
}

public fun testT1mskcU32() {
    check(t1mskcU32(0x57u) == 0xFFFFFFF8u)
    check(t1mskcU32(0u) == 0xFFFFFFFFu)
}

public fun testTzmskU32() {
    check(tzmskU32(0x58u) == 0x7u)
    check(tzmskU32(0x59u) == 0x0u)
}
