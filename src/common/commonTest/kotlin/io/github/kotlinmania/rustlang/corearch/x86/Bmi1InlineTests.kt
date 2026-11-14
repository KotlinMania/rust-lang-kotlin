// port-lint: tests x86/bmi1.rs
package io.github.kotlinmania.rustlang.corearch.x86

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

public fun testBextrU32() {
    val r = bextrU32(0x50u, 4u, 4u)
    check(r == 0x5u)
}

public fun testAndnU32() {
    check(andnU32(0u, 0u) == 0u)
    check(andnU32(0u, 1u) == 1u)
    check(andnU32(1u, 0u) == 0u)
    check(andnU32(1u, 1u) == 0u)
    val r = andnU32(0x0u, 0x0u)
    check(r == 0x0u)
    val r1 = andnU32(0x0u, 0xFFu)
    check(r1 == 0xFFu)
    val r2 = andnU32(0xFFu, 0x0u)
    check(r2 == 0x0u)
    val r3 = andnU32(0xFFu, 0xFFu)
    check(r3 == 0x0u)
    val r4 = andnU32(0x40u, 0x5Du)
    check(r4 == 0x1Du)
}

public fun testBlsiU32() {
    check(blsiU32(0xD0u) == 0x10u)
}

public fun testBlsmskU32() {
    val r = blsmskU32(0x30u)
    check(r == 0x1Fu)
}

public fun testBlsrU32() {
    // Note: test the behavior when the input is `0`.
    val r = blsrU32(0x30u)
    check(r == 0x20u)
}

public fun testTzcntU16() {
    check(tzcntU16(0x1u.toUShort()) == 0u.toUShort())
    check(tzcntU16(0x0u.toUShort()) == 16u.toUShort())
    check(tzcntU16(0x90u.toUShort()) == 4u.toUShort())
}

public fun testTzcntU32() {
    check(tzcntU32(0x1u) == 0u)
    check(tzcntU32(0x0u) == 32u)
    check(tzcntU32(0x90u) == 4u)
}
