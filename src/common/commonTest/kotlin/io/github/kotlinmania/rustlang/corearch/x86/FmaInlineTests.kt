// port-lint: tests x86/fma.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Fused Multiply-Add instruction set (FMA)
//
// The FMA instruction set is an extension to the 128 and 256-bit SSE
// instructions in the x86 microprocessor instruction set to perform fused
// multiply–add (FMA) operations.
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref].
// - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
//   System Instructions][amd64Ref].
//
// Wikipedia's [FMA][wikiFma] page provides a quick overview of the
// instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wikiFma]: https://en.wikipedia.org/wiki/FusedMultiply-accumulate

public fun testMmFmaddPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(9.0, 15.0)
    assertEqM128d(mmFmaddPd(a, b, c), r)
}

public fun testMm256FmaddPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 3.0, 7.0, 2.0)
    val c = mm256SetrPd(4.0, 9.0, 1.0, 7.0)
    val r = mm256SetrPd(9.0, 15.0, 22.0, 15.0)
    assertEqM256d(mm256FmaddPd(a, b, c), r)
}

public fun testMmFmaddPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(9.0f, 15.0f, 22.0f, 15.0f)
    assertEqM128(mmFmaddPs(a, b, c), r)
}

public fun testMm256FmaddPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 10.0f, -1.0f, -2.0f)
    val b = mm256SetrPs(5.0f, 3.0f, 7.0f, 2.0f, 4.0f, -6.0f, 0.0f, 14.0f)
    val c = mm256SetrPs(4.0f, 9.0f, 1.0f, 7.0f, -5.0f, 11.0f, -2.0f, -3.0f)
    val r = mm256SetrPs(9.0f, 15.0f, 22.0f, 15.0f, -5.0f, -49.0f, -2.0f, -31.0f)
    assertEqM256(mm256FmaddPs(a, b, c), r)
}

public fun testMmFmaddSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(9.0, 2.0)
    assertEqM128d(mmFmaddSd(a, b, c), r)
}

public fun testMmFmaddSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(9.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(mmFmaddSs(a, b, c), r)
}

public fun testMmFmaddsubPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(1.0, 15.0)
    assertEqM128d(mmFmaddsubPd(a, b, c), r)
}

public fun testMm256FmaddsubPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 3.0, 7.0, 2.0)
    val c = mm256SetrPd(4.0, 9.0, 1.0, 7.0)
    val r = mm256SetrPd(1.0, 15.0, 20.0, 15.0)
    assertEqM256d(mm256FmaddsubPd(a, b, c), r)
}

public fun testMmFmaddsubPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(1.0f, 15.0f, 20.0f, 15.0f)
    assertEqM128(mmFmaddsubPs(a, b, c), r)
}

public fun testMm256FmaddsubPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 10.0f, -1.0f, -2.0f)
    val b = mm256SetrPs(5.0f, 3.0f, 7.0f, 2.0f, 4.0f, -6.0f, 0.0f, 14.0f)
    val c = mm256SetrPs(4.0f, 9.0f, 1.0f, 7.0f, -5.0f, 11.0f, -2.0f, -3.0f)
    val r = mm256SetrPs(1.0f, 15.0f, 20.0f, 15.0f, 5.0f, -49.0f, 2.0f, -31.0f)
    assertEqM256(mm256FmaddsubPs(a, b, c), r)
}

public fun testMmFmsubPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(1.0, -3.0)
    assertEqM128d(mmFmsubPd(a, b, c), r)
}

public fun testMm256FmsubPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 3.0, 7.0, 2.0)
    val c = mm256SetrPd(4.0, 9.0, 1.0, 7.0)
    val r = mm256SetrPd(1.0, -3.0, 20.0, 1.0)
    assertEqM256d(mm256FmsubPd(a, b, c), r)
}

public fun testMmFmsubPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(1.0f, -3.0f, 20.0f, 1.0f)
    assertEqM128(mmFmsubPs(a, b, c), r)
}

public fun testMm256FmsubPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 10.0f, -1.0f, -2.0f)
    val b = mm256SetrPs(5.0f, 3.0f, 7.0f, 2.0f, 4.0f, -6.0f, 0.0f, 14.0f)
    val c = mm256SetrPs(4.0f, 9.0f, 1.0f, 7.0f, -5.0f, 11.0f, -2.0f, -3.0f)
    val r = mm256SetrPs(1.0f, -3.0f, 20.0f, 1.0f, 5.0f, -71.0f, 2.0f, -25.0f)
    assertEqM256(mm256FmsubPs(a, b, c), r)
}

public fun testMmFmsubSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(1.0, 2.0)
    assertEqM128d(mmFmsubSd(a, b, c), r)
}

public fun testMmFmsubSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(mmFmsubSs(a, b, c), r)
}

public fun testMmFmsubaddPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(9.0, -3.0)
    assertEqM128d(mmFmsubaddPd(a, b, c), r)
}

public fun testMm256FmsubaddPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 3.0, 7.0, 2.0)
    val c = mm256SetrPd(4.0, 9.0, 1.0, 7.0)
    val r = mm256SetrPd(9.0, -3.0, 22.0, 1.0)
    assertEqM256d(mm256FmsubaddPd(a, b, c), r)
}

public fun testMmFmsubaddPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(9.0f, -3.0f, 22.0f, 1.0f)
    assertEqM128(mmFmsubaddPs(a, b, c), r)
}

public fun testMm256FmsubaddPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 10.0f, -1.0f, -2.0f)
    val b = mm256SetrPs(5.0f, 3.0f, 7.0f, 2.0f, 4.0f, -6.0f, 0.0f, 14.0f)
    val c = mm256SetrPs(4.0f, 9.0f, 1.0f, 7.0f, -5.0f, 11.0f, -2.0f, -3.0f)
    val r = mm256SetrPs(9.0f, -3.0f, 22.0f, 1.0f, -5.0f, -71.0f, -2.0f, -25.0f)
    assertEqM256(mm256FmsubaddPs(a, b, c), r)
}

public fun testMmFnmaddPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(-1.0, 3.0)
    assertEqM128d(mmFnmaddPd(a, b, c), r)
}

public fun testMm256FnmaddPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 3.0, 7.0, 2.0)
    val c = mm256SetrPd(4.0, 9.0, 1.0, 7.0)
    val r = mm256SetrPd(-1.0, 3.0, -20.0, -1.0)
    assertEqM256d(mm256FnmaddPd(a, b, c), r)
}

public fun testMmFnmaddPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(-1.0f, 3.0f, -20.0f, -1.0f)
    assertEqM128(mmFnmaddPs(a, b, c), r)
}

public fun testMm256FnmaddPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 10.0f, -1.0f, -2.0f)
    val b = mm256SetrPs(5.0f, 3.0f, 7.0f, 2.0f, 4.0f, -6.0f, 0.0f, 14.0f)
    val c = mm256SetrPs(4.0f, 9.0f, 1.0f, 7.0f, -5.0f, 11.0f, -2.0f, -3.0f)
    val r = mm256SetrPs(-1.0f, 3.0f, -20.0f, -1.0f, -5.0f, 71.0f, -2.0f, 25.0f)
    assertEqM256(mm256FnmaddPs(a, b, c), r)
}

public fun testMmFnmaddSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(-1.0, 2.0)
    assertEqM128d(mmFnmaddSd(a, b, c), r)
}

public fun testMmFnmaddSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(-1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(mmFnmaddSs(a, b, c), r)
}

public fun testMmFnmsubPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(-9.0, -15.0)
    assertEqM128d(mmFnmsubPd(a, b, c), r)
}

public fun testMm256FnmsubPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 3.0, 7.0, 2.0)
    val c = mm256SetrPd(4.0, 9.0, 1.0, 7.0)
    val r = mm256SetrPd(-9.0, -15.0, -22.0, -15.0)
    assertEqM256d(mm256FnmsubPd(a, b, c), r)
}

public fun testMmFnmsubPs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(-9.0f, -15.0f, -22.0f, -15.0f)
    assertEqM128(mmFnmsubPs(a, b, c), r)
}

public fun testMm256FnmsubPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 10.0f, -1.0f, -2.0f)
    val b = mm256SetrPs(5.0f, 3.0f, 7.0f, 2.0f, 4.0f, -6.0f, 0.0f, 14.0f)
    val c = mm256SetrPs(4.0f, 9.0f, 1.0f, 7.0f, -5.0f, 11.0f, -2.0f, -3.0f)
    val r = mm256SetrPs(-9.0f, -15.0f, -22.0f, -15.0f, 5.0f, 49.0f, 2.0f, 31.0f)
    assertEqM256(mm256FnmsubPs(a, b, c), r)
}

public fun testMmFnmsubSd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 3.0)
    val c = mmSetrPd(4.0, 9.0)
    val r = mmSetrPd(-9.0, 2.0)
    assertEqM128d(mmFnmsubSd(a, b, c), r)
}

public fun testMmFnmsubSs() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val b = mmSetrPs(5.0f, 3.0f, 7.0f, 2.0f)
    val c = mmSetrPs(4.0f, 9.0f, 1.0f, 7.0f)
    val r = mmSetrPs(-9.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(mmFnmsubSs(a, b, c), r)
}
