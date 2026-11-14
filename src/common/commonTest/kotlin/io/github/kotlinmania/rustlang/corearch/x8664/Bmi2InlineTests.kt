// port-lint: tests x86_64/bmi2.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
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

public fun testPextU64() {
    val n = 0xBE93uL
    val m0 = 0x6385uL
    val s0 = 0x35uL
    val m1 = 0xEBEFuL
    val s1 = 0x1743uL
    check(pextU64(n, m0) == s0)
    check(pextU64(n, m1) == s1)
}

public fun testPdepU64() {
    val n = 0xBE93uL
    val m0 = 0x6385uL
    val s0 = 0x205uL
    val m1 = 0xEBEFuL
    val s1 = 0xE923uL
    check(pdepU64(n, m0) == s0)
    check(pdepU64(n, m1) == s1)
}

public fun testBzhiU64() {
    val n = 0xF2uL
    val s = 0x12uL
    check(bzhiU64(n, 5u) == s)
}

public fun testMulxU64() {
    val a: ULong = 9223372036854775800uL
    val b: ULong = 100uL
    val localStorageL128C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var hi by localStorageL128C8
    val lo = mulxU64(a, b, RustMutableReference(localStorageL128C8.asMutPtr(), localStorageL128C8.layout))
    //
    // result = 922337203685477580000 =
    // 0b001100011111111111111111111111111111111111111111111111111111110011100000
    // ^~hi~~~~ ^~lo~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    check(lo == 0xFFFFFFFFFFFFFCE0uL)
    check(hi == 0x31uL)
}
