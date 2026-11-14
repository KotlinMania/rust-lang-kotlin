// port-lint: tests x86/abm.rs
package io.github.kotlinmania.rustlang.corearch.x86

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

public fun testLzcntU32() {
    check(lzcntU32(0x5Au) == 25u)
}

public fun testPopcnt32() {
    check(popcnt32(0x5A) == 4)
}
