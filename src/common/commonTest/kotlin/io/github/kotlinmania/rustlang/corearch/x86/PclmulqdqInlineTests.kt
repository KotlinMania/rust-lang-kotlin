// port-lint: tests x86/pclmulqdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Carry-less Multiplication (CLMUL)
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref] (p. 4-241).
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun testMmClmulepi64Si128() {
    // Constants taken from https://software.intel.com/sites/default/files/managed/72/cc/clmul-wp-rev-2.02-2014-04-20.pdf
    val a = mmSetEpi64x(0x7b5b546573745665L, 0x63746f725d53475dL)
    val b = mmSetEpi64x(0x4869285368617929L, 0x5b477565726f6e5dL)
    val r00 = mmSetEpi64x(0x1d4d84c85c3440c0L, 0x929633d5d36f0451uL.toLong())
    val r01 = mmSetEpi64x(0x1bd17c8d556ab5a1L, 0x7fa540ac2a281315L)
    val r10 = mmSetEpi64x(0x1a2bf6db3a30862fL, 0xbabf262df4b7d5c9uL.toLong())
    val r11 = mmSetEpi64x(0x1d1e1f2c592e7c45L, 0xd66ee03e410fd4eduL.toLong())
    assertEqM128i(mmClmulepi64Si128(a, b, 0x00), r00)
    assertEqM128i(mmClmulepi64Si128(a, b, 0x10), r01)
    assertEqM128i(mmClmulepi64Si128(a, b, 0x01), r10)
    assertEqM128i(mmClmulepi64Si128(a, b, 0x11), r11)
    val a0 = mmSetEpi64x(0x0000000000000000L, 0x8000000000000000uL.toLong())
    val r = mmSetEpi64x(0x4000000000000000L, 0x0000000000000000L)
    assertEqM128i(mmClmulepi64Si128(a0, a0, 0x00), r)
}
