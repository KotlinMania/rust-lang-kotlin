// port-lint: tests x86/bmi2.rs
package io.github.kotlinmania.rustlang.corearch.x86

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

public fun testPextU32() {
    val n = 0xBE93u
    val m0 = 0x6385u
    val s0 = 0x35u
    val m1 = 0xEBEFu
    val s1 = 0x1743u
    check(pextU32(n, m0) == s0)
    check(pextU32(n, m1) == s1)
}

public fun testPdepU32() {
    val n = 0xBE93u
    val m0 = 0x6385u
    val s0 = 0x205u
    val m1 = 0xEBEFu
    val s1 = 0xE923u
    check(pdepU32(n, m0) == s0)
    check(pdepU32(n, m1) == s1)
}

public fun testBzhiU32() {
    val n = 0xF2u
    val s = 0x12u
    check(bzhiU32(n, 5u) == s)
}

public fun testMulxU32() {
    val a: UInt = 4294967200u
    val b: UInt = 2u
    val localStorageL125C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var hi by localStorageL125C8
    val lo = mulxU32(a, b, RustMutableReference(localStorageL125C8.asMutPtr(), localStorageL125C8.layout))
    //
    // result = 8589934400
    // = 0b000111111111111111111111111101000000u64
    // ^~hi ^~lo~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    check(lo == 0xFFFFFF40u)
    check(hi == 0x1u)
}
