// port-lint: tests x86/avx512bitalg.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Bit-oriented Algorithms (BITALG)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun testMm512PopcntEpi16() {
    val testData = mm512SetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF.toShort(), (-1).toShort(), (-100).toShort(), 255, 256, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048)
    val actualResult = mm512PopcntEpi16(testData)
    val referenceResult = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 12, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskzPopcntEpi16() {
    val testData = mm512SetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF.toShort(), (-1).toShort(), (-100).toShort(), 255, 256, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048)
    val mask = 0xFFFF0000
    val actualResult = mm512MaskzPopcntEpi16(mask, testData)
    val referenceResult = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskPopcntEpi16() {
    val testData = mm512SetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF.toShort(), (-1).toShort(), (-100).toShort(), 255, 256, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048)
    val mask = 0xFFFF0000
    val actualResult = mm512MaskPopcntEpi16(testData, mask, testData)
    val referenceResult = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0xFFFF.toShort(), (-1).toShort(), (-100).toShort(), 255, 256, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm256PopcntEpi16() {
    val testData = mm256SetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF)
    val actualResult = mm256PopcntEpi16(testData)
    val referenceResult = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskzPopcntEpi16() {
    val testData = mm256SetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF)
    val mask = 0xFF00
    val actualResult = mm256MaskzPopcntEpi16(mask, testData)
    val referenceResult = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskPopcntEpi16() {
    val testData = mm256SetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF)
    val mask = 0xFF00
    val actualResult = mm256MaskPopcntEpi16(testData, mask, testData)
    val referenceResult = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMmPopcntEpi16() {
    val testData = mmSetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F)
    val actualResult = mmPopcntEpi16(testData)
    val referenceResult = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMmMaskzPopcntEpi16() {
    val testData = mmSetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F)
    val mask = 0xF0
    val actualResult = mmMaskzPopcntEpi16(mask, testData)
    val referenceResult = mmSetEpi16(0, 1, 2, 3, 0, 0, 0, 0)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMmMaskPopcntEpi16() {
    val testData = mmSetEpi16(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F)
    val mask = 0xF0
    val actualResult = mmMaskPopcntEpi16(testData, mask, testData)
    val referenceResult = mmSetEpi16(0, 1, 2, 3, 0xF, 0x1F, 0x3F, 0x7F)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMm512PopcntEpi8() {
    val testData = mm512SetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64, 128.toByte(), 171.toByte(), 206.toByte(), 100, 217.toByte(), 109, 253.toByte(), 190.toByte(), 177.toByte(), 254.toByte(), 179.toByte(), 215.toByte(), 230.toByte(), 68, 201.toByte(), 172.toByte(), 183.toByte(), 154.toByte(), 84, 56, 227.toByte(), 189.toByte(), 140.toByte(), 35, 117, 219.toByte(), 169.toByte(), 226.toByte(), 170.toByte(), 13, 22, 159.toByte(), 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val actualResult = mm512PopcntEpi8(testData)
    val referenceResult = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1, 1, 5, 5, 3, 5, 5, 7, 6, 4, 7, 5, 6, 5, 2, 4, 4, 6, 4, 3, 3, 5, 6, 3, 3, 5, 6, 4, 4, 4, 3, 3, 6, 7, 3, 5, 5, 3, 4, 5, 3, 4, 4, 3, 6, 5, 5, 4, 3)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskzPopcntEpi8() {
    val testData = mm512SetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64, 128.toByte(), 171.toByte(), 206.toByte(), 100, 217.toByte(), 109, 253.toByte(), 190.toByte(), 177.toByte(), 254.toByte(), 179.toByte(), 215.toByte(), 230.toByte(), 68, 201.toByte(), 172.toByte(), 183.toByte(), 154.toByte(), 84, 56, 227.toByte(), 189.toByte(), 140.toByte(), 35, 117, 219.toByte(), 169.toByte(), 226.toByte(), 170.toByte(), 13, 22, 159.toByte(), 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val mask = 0xFFFFFFFF00000000uL
    val actualResult = mm512MaskzPopcntEpi8(mask, testData)
    val referenceResult = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1, 1, 5, 5, 3, 5, 5, 7, 6, 4, 7, 5, 6, 5, 2, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm512MaskPopcntEpi8() {
    val testData = mm512SetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64, 128.toByte(), 171.toByte(), 206.toByte(), 100, 217.toByte(), 109, 253.toByte(), 190.toByte(), 177.toByte(), 254.toByte(), 179.toByte(), 215.toByte(), 230.toByte(), 68, 201.toByte(), 172.toByte(), 183.toByte(), 154.toByte(), 84, 56, 227.toByte(), 189.toByte(), 140.toByte(), 35, 117, 219.toByte(), 169.toByte(), 226.toByte(), 170.toByte(), 13, 22, 159.toByte(), 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val mask = 0xFFFFFFFF00000000uL
    val actualResult = mm512MaskPopcntEpi8(testData, mask, testData)
    val referenceResult = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1, 1, 5, 5, 3, 5, 5, 7, 6, 4, 7, 5, 6, 5, 2, 4, 4, 183.toByte(), 154.toByte(), 84, 56, 227.toByte(), 189.toByte(), 140.toByte(), 35, 117, 219.toByte(), 169.toByte(), 226.toByte(), 170.toByte(), 13, 22, 159.toByte(), 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    assertEqM512i(actualResult, referenceResult)
}

public fun testMm256PopcntEpi8() {
    val testData = mm256SetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64, 128.toByte(), 171.toByte(), 206.toByte(), 100, 217.toByte(), 109, 253.toByte(), 190.toByte(), 177.toByte(), 254.toByte(), 179.toByte(), 215.toByte(), 230.toByte(), 68, 201.toByte(), 172.toByte())
    val actualResult = mm256PopcntEpi8(testData)
    val referenceResult = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1, 1, 5, 5, 3, 5, 5, 7, 6, 4, 7, 5, 6, 5, 2, 4, 4)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskzPopcntEpi8() {
    val testData = mm256SetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64, 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val mask = 0xFFFF0000
    val actualResult = mm256MaskzPopcntEpi8(mask, testData)
    val referenceResult = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMm256MaskPopcntEpi8() {
    val testData = mm256SetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64, 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val mask = 0xFFFF0000
    val actualResult = mm256MaskPopcntEpi8(testData, mask, testData)
    val referenceResult = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1, 251.toByte(), 73, 121, 143.toByte(), 145.toByte(), 85, 91, 137.toByte(), 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    assertEqM256i(actualResult, referenceResult)
}

public fun testMmPopcntEpi8() {
    val testData = mmSetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 0xFF.toByte(), (-1).toByte(), 2, 4, 8, 16, 32, 64)
    val actualResult = mmPopcntEpi8(testData)
    val referenceResult = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 1, 1, 1, 1, 1)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMmMaskzPopcntEpi8() {
    val testData = mmSetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val mask = 0xFF00
    val actualResult = mmMaskzPopcntEpi8(mask, testData)
    val referenceResult = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMmMaskPopcntEpi8() {
    val testData = mmSetEpi8(0, 1, 3, 7, 0xF, 0x1F, 0x3F, 0x7F, 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    val mask = 0xFF00
    val actualResult = mmMaskPopcntEpi8(testData, mask, testData)
    val referenceResult = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 90, 225.toByte(), 21, 249.toByte(), 211.toByte(), 155.toByte(), 228.toByte(), 70)
    assertEqM128i(actualResult, referenceResult)
}

public fun testMm512BitshuffleEpi64Mask() {
    val testIndices = mm512SetEpi8(63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56, 32, 32, 16, 16, 0, 0, 8, 8, 56, 48, 40, 32, 24, 16, 8, 0, 63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56, 32, 32, 16, 16, 0, 0, 8, 8, 56, 48, 40, 32, 24, 16, 8, 0)
    val testData = mm512SetrEpi64(0xFFFFFFFF00000000uL.toLong(), 0xFF00FF00FF00FF00uL.toLong(), 0xFF00000000000000uL.toLong(), 0xAC00000000000000uL.toLong(), 0xFFFFFFFF00000000uL.toLong(), 0xFF00FF00FF00FF00uL.toLong(), 0xFF00000000000000uL.toLong(), 0xAC00000000000000uL.toLong())
    val actualResult = mm512BitshuffleEpi64Mask(testData, testIndices)
    val referenceResult = 0xF0 shl 0 or 0x03 shl 8 or 0xFF shl 16 or 0xAC shl 24 or 0xF0 shl 32 or 0x03 shl 40 or 0xFF shl 48 or 0xAC shl 56
    check(actualResult == referenceResult)
}

public fun testMm512MaskBitshuffleEpi64Mask() {
    val testIndices = mm512SetEpi8(63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56, 32, 32, 16, 16, 0, 0, 8, 8, 56, 48, 40, 32, 24, 16, 8, 0, 63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56, 32, 32, 16, 16, 0, 0, 8, 8, 56, 48, 40, 32, 24, 16, 8, 0)
    val testData = mm512SetrEpi64(0xFFFFFFFF00000000uL.toLong(), 0xFF00FF00FF00FF00uL.toLong(), 0xFF00000000000000uL.toLong(), 0xAC00000000000000uL.toLong(), 0xFFFFFFFF00000000uL.toLong(), 0xFF00FF00FF00FF00uL.toLong(), 0xFF00000000000000uL.toLong(), 0xAC00000000000000uL.toLong())
    val mask = 0xFFFFFFFF00000000uL
    val actualResult = mm512MaskBitshuffleEpi64Mask(mask, testData, testIndices)
    val referenceResult = 0x00 shl 0 or 0x00 shl 8 or 0x00 shl 16 or 0x00 shl 24 or 0xF0 shl 32 or 0x03 shl 40 or 0xFF shl 48 or 0xAC shl 56
    check(actualResult == referenceResult)
}

public fun testMm256BitshuffleEpi64Mask() {
    val testIndices = mm256SetEpi8(63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56, 32, 32, 16, 16, 0, 0, 8, 8, 56, 48, 40, 32, 24, 16, 8, 0)
    val testData = mm256SetrEpi64x(0xFFFFFFFF00000000uL.toLong(), 0xFF00FF00FF00FF00uL.toLong(), 0xFF00000000000000uL.toLong(), 0xAC00000000000000uL.toLong())
    val actualResult = mm256BitshuffleEpi64Mask(testData, testIndices)
    val referenceResult = 0xF0 shl 0 or 0x03 shl 8 or 0xFF shl 16 or 0xAC shl 24
    check(actualResult == referenceResult)
}

public fun testMm256MaskBitshuffleEpi64Mask() {
    val testIndices = mm256SetEpi8(63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56, 32, 32, 16, 16, 0, 0, 8, 8, 56, 48, 40, 32, 24, 16, 8, 0)
    val testData = mm256SetrEpi64x(0xFFFFFFFF00000000uL.toLong(), 0xFF00FF00FF00FF00uL.toLong(), 0xFF00000000000000uL.toLong(), 0xAC00000000000000uL.toLong())
    val mask = 0xFFFF0000
    val actualResult = mm256MaskBitshuffleEpi64Mask(mask, testData, testIndices)
    val referenceResult = 0x00 shl 0 or 0x00 shl 8 or 0xFF shl 16 or 0xAC shl 24
    check(actualResult == referenceResult)
}

public fun testMmBitshuffleEpi64Mask() {
    val testIndices = mmSetEpi8(63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56)
    val testData = mmSetrEpi64x(0xFF00000000000000uL, 0xAC00000000000000uL)
    val actualResult = mmBitshuffleEpi64Mask(testData, testIndices)
    val referenceResult = 0xFF shl 0 or 0xAC shl 8
    check(actualResult == referenceResult)
}

public fun testMmMaskBitshuffleEpi64Mask() {
    val testIndices = mmSetEpi8(63, 62, 61, 60, 59, 58, 57, 56, 63, 62, 61, 60, 59, 58, 57, 56)
    val testData = mmSetrEpi64x(0xFF00000000000000uL, 0xAC00000000000000uL)
    val mask = 0xFF00
    val actualResult = mmMaskBitshuffleEpi64Mask(mask, testData, testIndices)
    val referenceResult = 0x00 shl 0 or 0xAC shl 8
    check(actualResult == referenceResult)
}
