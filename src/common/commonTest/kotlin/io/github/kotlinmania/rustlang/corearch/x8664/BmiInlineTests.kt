// port-lint: tests x86_64/bmi.rs
package io.github.kotlinmania.rustlang.corearch.x8664

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

public fun testBextrU64() {
    val r = bextrU64(0x50uL, 4u, 4u)
    check(r == 0x5uL)
}

public fun testAndnU64() {
    check(andnU64(0uL, 0uL) == 0uL)
    check(andnU64(0uL, 1uL) == 1uL)
    check(andnU64(1uL, 0uL) == 0uL)
    check(andnU64(1uL, 1uL) == 0uL)
    val r = andnU64(0x0uL, 0x0uL)
    check(r == 0x0uL)
    val r1 = andnU64(0x0uL, 0xFFuL)
    check(r1 == 0xFFuL)
    val r2 = andnU64(0xFFuL, 0x0uL)
    check(r2 == 0x0uL)
    val r3 = andnU64(0xFFuL, 0xFFuL)
    check(r3 == 0x0uL)
    val r4 = andnU64(0x40uL, 0x5DuL)
    check(r4 == 0x1DuL)
}

public fun testBlsiU64() {
    check(blsiU64(0xD0uL) == 0x10uL)
}

public fun testBlsmskU64() {
    val r = blsmskU64(0x30uL)
    check(r == 0x1FuL)
}

public fun testBlsrU64() {
    // Note: test the behavior when the input is `0`.
    val r = blsrU64(0x30uL)
    check(r == 0x20uL)
}

public fun testTzcntU64() {
    check(tzcntU64(0x1uL) == 0uL)
    check(tzcntU64(0x0uL) == 64uL)
    check(tzcntU64(0x90uL) == 4uL)
}
