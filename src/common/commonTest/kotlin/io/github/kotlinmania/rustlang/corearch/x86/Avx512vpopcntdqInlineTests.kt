// port-lint: tests x86/avx512vpopcntdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Vectorized Population Count Instructions for Double- and Quadwords (VPOPCNTDQ)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun testMm512PopcntEpi32() {
    val testData = mm512SetEpi32(0, 1, -1, 2, 7, 0xFFFE, 0x7FFFFFFF, -100, 0x40000000, 103, 371, 552, 432948, 818826998, 255, 256)
    val actualResult = mm512PopcntEpi32(testData)
    val referenceResult = mm512SetEpi32(0, 1, 32, 1, 3, 15, 31, 28, 1, 5, 6, 3, 10, 17, 8, 1)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskPopcntEpi32() {
    val testData = mm512SetEpi32(0, 1, -1, 2, 7, 0xFFFE, 0x7FFFFFFF, -100, 0x40000000, 103, 371, 552, 432948, 818826998, 255, 256)
    val mask = 0xFF00
    val actualResult = mm512MaskPopcntEpi32(testData, mask, testData)
    val referenceResult = mm512SetEpi32(0, 1, 32, 1, 3, 15, 31, 28, 0x40000000, 103, 371, 552, 432948, 818826998, 255, 256)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskzPopcntEpi32() {
    val testData = mm512SetEpi32(0, 1, -1, 2, 7, 0xFFFE, 0x7FFFFFFF, -100, 0x40000000, 103, 371, 552, 432948, 818826998, 255, 256)
    val mask = 0xFF00
    val actualResult = mm512MaskzPopcntEpi32(mask, testData)
    val referenceResult = mm512SetEpi32(0, 1, 32, 1, 3, 15, 31, 28, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm256PopcntEpi32() {
    val testData = mm256SetEpi32(0, 1, -1, 2, 7, 0xFFFE, 0x7FFFFFFF, -100)
    val actualResult = mm256PopcntEpi32(testData)
    val referenceResult = mm256SetEpi32(0, 1, 32, 1, 3, 15, 31, 28)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskPopcntEpi32() {
    val testData = mm256SetEpi32(0, 1, -1, 2, 7, 0xFFFE, 0x7FFFFFFF, -100)
    val mask = 0xF0
    val actualResult = mm256MaskPopcntEpi32(testData, mask, testData)
    val referenceResult = mm256SetEpi32(0, 1, 32, 1, 7, 0xFFFE, 0x7FFFFFFF, -100)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskzPopcntEpi32() {
    val testData = mm256SetEpi32(0, 1, -1, 2, 7, 0xFFFE, 0x7FFFFFFF, -100)
    val mask = 0xF0
    val actualResult = mm256MaskzPopcntEpi32(mask, testData)
    val referenceResult = mm256SetEpi32(0, 1, 32, 1, 0, 0, 0, 0)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMmPopcntEpi32() {
    val testData = mmSetEpi32(0, 1, -1, -100)
    val actualResult = mmPopcntEpi32(testData)
    val referenceResult = mmSetEpi32(0, 1, 32, 28)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMmMaskPopcntEpi32() {
    val testData = mmSetEpi32(0, 1, -1, -100)
    val mask = 0xE
    val actualResult = mmMaskPopcntEpi32(testData, mask, testData)
    val referenceResult = mmSetEpi32(0, 1, 32, -100)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMmMaskzPopcntEpi32() {
    val testData = mmSetEpi32(0, 1, -1, -100)
    val mask = 0xE
    val actualResult = mmMaskzPopcntEpi32(mask, testData)
    val referenceResult = mmSetEpi32(0, 1, 32, 0)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMm512PopcntEpi64() {
    val testData = mm512SetEpi64(0L, 1L, -1L, 2L, 7L, 0xFFFEL, 0x7FFFFFFFFFFFFFFFL, -100L)
    val actualResult = mm512PopcntEpi64(testData)
    val referenceResult = mm512SetEpi64(0L, 1L, 64L, 1L, 3L, 15L, 63L, 60L)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskPopcntEpi64() {
    val testData = mm512SetEpi64(0L, 1L, -1L, 2L, 7L, 0xFFFEL, 0x7FFFFFFFFFFFFFFFL, -100L)
    val mask = 0xF0
    val actualResult = mm512MaskPopcntEpi64(testData, mask, testData)
    val referenceResult = mm512SetEpi64(0L, 1L, 64L, 1L, 7L, 0xFFFEL, 0x7FFFFFFFFFFFFFFFL, -100L)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskzPopcntEpi64() {
    val testData = mm512SetEpi64(0L, 1L, -1L, 2L, 7L, 0xFFFEL, 0x7FFFFFFFFFFFFFFFL, -100L)
    val mask = 0xF0
    val actualResult = mm512MaskzPopcntEpi64(mask, testData)
    val referenceResult = mm512SetEpi64(0L, 1L, 64L, 1L, 0L, 0L, 0L, 0L)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm256PopcntEpi64() {
    val testData = mm256SetEpi64x(0L, 1L, -1L, -100L)
    val actualResult = mm256PopcntEpi64(testData)
    val referenceResult = mm256SetEpi64x(0L, 1L, 64L, 60L)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskPopcntEpi64() {
    val testData = mm256SetEpi64x(0L, 1L, -1L, -100L)
    val mask = 0xE
    val actualResult = mm256MaskPopcntEpi64(testData, mask, testData)
    val referenceResult = mm256SetEpi64x(0L, 1L, 64L, -100L)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskzPopcntEpi64() {
    val testData = mm256SetEpi64x(0L, 1L, -1L, -100L)
    val mask = 0xE
    val actualResult = mm256MaskzPopcntEpi64(mask, testData)
    val referenceResult = mm256SetEpi64x(0L, 1L, 64L, 0L)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMmPopcntEpi64() {
    val testData = mmSetEpi64x(0L, 1L)
    val actualResult = mmPopcntEpi64(testData)
    val referenceResult = mmSetEpi64x(0L, 1L)
    assertEqM128i(actualResult, referenceResult)
    val testData1 = mmSetEpi64x(-1L, -100L)
    val actualResult1 = mmPopcntEpi64(testData1)
    val referenceResult1 = mmSetEpi64x(64L, 60L)
    assertEqM128i(actualResult1, referenceResult1)
}

public fun testMmMaskPopcntEpi64() {
    val testData = mmSetEpi64x(0L, -100L)
    val mask = 0x2
    val actualResult = mmMaskPopcntEpi64(testData, mask, testData)
    val referenceResult = mmSetEpi64x(0L, -100L)
    assertEqM128i(actualResult, referenceResult)
    val testData1 = mmSetEpi64x(-1L, 1L)
    val mask1 = 0x2
    val actualResult1 = mmMaskPopcntEpi64(testData1, mask1, testData1)
    val referenceResult1 = mmSetEpi64x(64L, 1L)
    assertEqM128i(actualResult1, referenceResult1)
}

public fun testMmMaskzPopcntEpi64() {
    val testData = mmSetEpi64x(0L, 1L)
    val mask = 0x2
    val actualResult = mmMaskzPopcntEpi64(mask, testData)
    val referenceResult = mmSetEpi64x(0L, 0L)
    assertEqM128i(actualResult, referenceResult)
    val testData1 = mmSetEpi64x(-1L, -100L)
    val mask1 = 0x2
    val actualResult1 = mmMaskzPopcntEpi64(mask1, testData1)
    val referenceResult1 = mmSetEpi64x(64L, 0L)
    assertEqM128i(actualResult1, referenceResult1)
}
