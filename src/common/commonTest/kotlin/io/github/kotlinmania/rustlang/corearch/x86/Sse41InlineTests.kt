// port-lint: tests x86/sse41.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast

// Streaming SIMD Extensions 4.1 (SSE4.1)

public fun testMmBlendvEpi8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetrEpi8(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val mask = mmSetrEpi8(0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte())
    val e = mmSetrEpi8(0, 17, 2, 19, 4, 21, 6, 23, 8, 25, 10, 27, 12, 29, 14, 31)
    assertEqM128i(mmBlendvEpi8(a, b, mask), e)
}

public fun testMmBlendvPd() {
    val a = mmSet1Pd(0.0)
    val b = mmSet1Pd(1.0)
    val mask = mmCastsi128Pd(mmSetrEpi64x(0, -1))
    val r = mmBlendvPd(a, b, mask)
    val e = mmSetrPd(0.0, 1.0)
    assertEqM128d(r, e)
}

public fun testMmBlendvPs() {
    val a = mmSet1Ps(0.0f)
    val b = mmSet1Ps(1.0f)
    val mask = mmCastsi128Ps(mmSetrEpi32(0, -1, 0, -1))
    val r = mmBlendvPs(a, b, mask)
    val e = mmSetrPs(0.0f, 1.0f, 0.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmBlendPd() {
    val a = mmSet1Pd(0.0)
    val b = mmSet1Pd(1.0)
    val r = mmBlendPd(a, b, 0x2)
    val e = mmSetrPd(0.0, 1.0)
    assertEqM128d(r, e)
}

public fun testMmBlendPs() {
    val a = mmSet1Ps(0.0f)
    val b = mmSet1Ps(1.0f)
    val r = mmBlendPs(a, b, 0xA)
    val e = mmSetrPs(0.0f, 1.0f, 0.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMmBlendEpi16() {
    val a = mmSet1Epi16(0)
    val b = mmSet1Epi16(1)
    val r = mmBlendEpi16(a, b, 0xAC)
    val e = mmSetrEpi16(0, 0, 1, 1, 0, 1, 0, 1)
    assertEqM128i(r, e)
}

public fun testMmExtractPs() {
    val a = mmSetrPs(0.0f, 1.0f, 2.0f, 3.0f)
    val r: Float = Float.fromBits((mmExtractPs(a, 1).toUInt()).toInt())
    check(r == 1.0f.toFloat())
    val r1: Float = Float.fromBits((mmExtractPs(a, 3).toUInt()).toInt())
    check(r1 == 3.0f.toFloat())
}

public fun testMmExtractEpi8() {
    val a = mmSetrEpi8((-1).toByte(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r1 = mmExtractEpi8(a, 0)
    val r2 = mmExtractEpi8(a, 3)
    check(r1 == 0xFF)
    check(r2 == 3)
}

public fun testMmExtractEpi32() {
    val a = mmSetrEpi32(0, 1, 2, 3)
    val r = mmExtractEpi32(a, 1)
    check(r == 1)
    val r1 = mmExtractEpi32(a, 3)
    check(r1 == 3)
}

public fun testMmInsertPs() {
    val a = mmSet1Ps(1.0f)
    val b = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mmInsertPs(a, b, 0xCC)
    val e = mmSetrPs(4.0f, 1.0f, 0.0f, 0.0f)
    assertEqM128(r, e)
    // Zeroing takes precedence over copied value
    val a1 = mmSet1Ps(1.0f)
    val b1 = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r1 = mmInsertPs(a1, b1, 0xC1)
    val e1 = mmSetrPs(0.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r1, e1)
}

public fun testMmInsertEpi8() {
    val a = mmSet1Epi8(0)
    val e = mmSetrEpi8(0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = mmInsertEpi8(a, 32, 1)
    assertEqM128i(r, e)
    val e1 = mmSetrEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 0)
    val r1 = mmInsertEpi8(a, 32, 14)
    assertEqM128i(r1, e1)
}

public fun testMmInsertEpi32() {
    val a = mmSet1Epi32(0)
    val e = mmSetrEpi32(0, 32, 0, 0)
    val r = mmInsertEpi32(a, 32, 1)
    assertEqM128i(r, e)
    val e1 = mmSetrEpi32(0, 0, 0, 32)
    val r1 = mmInsertEpi32(a, 32, 3)
    assertEqM128i(r1, e1)
}

public fun testMmMaxEpi8() {
    val a = mmSetrEpi8(1, 4, 5, 8, 9, 12, 13, 16, 17, 20, 21, 24, 25, 28, 29, 32)
    val b = mmSetrEpi8(2, 3, 6, 7, 10, 11, 14, 15, 18, 19, 22, 23, 26, 27, 30, 31)
    val r = mmMaxEpi8(a, b)
    val e = mmSetrEpi8(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32)
    assertEqM128i(r, e)
}

public fun testMmMaxEpu16() {
    val a = mmSetrEpi16(1, 4, 5, 8, 9, 12, 13, 16)
    val b = mmSetrEpi16(2, 3, 6, 7, 10, 11, 14, 15)
    val r = mmMaxEpu16(a, b)
    val e = mmSetrEpi16(2, 4, 6, 8, 10, 12, 14, 16)
    assertEqM128i(r, e)
}

public fun testMmMaxEpi32() {
    val a = mmSetrEpi32(1, 4, 5, 8)
    val b = mmSetrEpi32(2, 3, 6, 7)
    val r = mmMaxEpi32(a, b)
    val e = mmSetrEpi32(2, 4, 6, 8)
    assertEqM128i(r, e)
}

public fun testMmMaxEpu32() {
    val a = mmSetrEpi32(1, 4, 5, 8)
    val b = mmSetrEpi32(2, 3, 6, 7)
    val r = mmMaxEpu32(a, b)
    val e = mmSetrEpi32(2, 4, 6, 8)
    assertEqM128i(r, e)
}

public fun testMmMinEpi8() {
    val a = mmSetrEpi8(1, 4, 5, 8, 9, 12, 13, 16, 17, 20, 21, 24, 25, 28, 29, 32)
    val b = mmSetrEpi8(2, 3, 6, 7, 10, 11, 14, 15, 18, 19, 22, 23, 26, 27, 30, 31)
    val r = mmMinEpi8(a, b)
    val e = mmSetrEpi8(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
    assertEqM128i(r, e)
    val a1 = mmSetrEpi8(1, (-4).toByte(), (-5).toByte(), 8, (-9).toByte(), (-12).toByte(), 13, (-16).toByte(), 17, 20, 21, 24, 25, 28, 29, 32)
    val b1 = mmSetrEpi8(2, (-3).toByte(), (-6).toByte(), 7, (-10).toByte(), (-11).toByte(), 14, (-15).toByte(), 18, 19, 22, 23, 26, 27, 30, 31)
    val r1 = mmMinEpi8(a1, b1)
    val e1 = mmSetrEpi8(1, (-4).toByte(), (-6).toByte(), 7, (-10).toByte(), (-12).toByte(), 13, (-16).toByte(), 17, 19, 21, 23, 25, 27, 29, 31)
    assertEqM128i(r1, e1)
}

public fun testMmMinEpu16() {
    val a = mmSetrEpi16(1, 4, 5, 8, 9, 12, 13, 16)
    val b = mmSetrEpi16(2, 3, 6, 7, 10, 11, 14, 15)
    val r = mmMinEpu16(a, b)
    val e = mmSetrEpi16(1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM128i(r, e)
}

public fun testMmMinEpi32() {
    val a = mmSetrEpi32(1, 4, 5, 8)
    val b = mmSetrEpi32(2, 3, 6, 7)
    val r = mmMinEpi32(a, b)
    val e = mmSetrEpi32(1, 3, 5, 7)
    assertEqM128i(r, e)
    val a1 = mmSetrEpi32(-1, 4, 5, -7)
    val b1 = mmSetrEpi32(-2, 3, -6, 8)
    val r1 = mmMinEpi32(a1, b1)
    val e1 = mmSetrEpi32(-2, 3, -6, -7)
    assertEqM128i(r1, e1)
}

public fun testMmMinEpu32() {
    val a = mmSetrEpi32(1, 4, 5, 8)
    val b = mmSetrEpi32(2, 3, 6, 7)
    val r = mmMinEpu32(a, b)
    val e = mmSetrEpi32(1, 3, 5, 7)
    assertEqM128i(r, e)
}

public fun testMmPackusEpi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val b = mmSetrEpi32(-1, -2, -3, -4)
    val r = mmPackusEpi32(a, b)
    val e = mmSetrEpi16(1, 2, 3, 4, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmCmpeqEpi64() {
    val a = mmSetrEpi64x(0, 1)
    val b = mmSetrEpi64x(0, 0)
    val r = mmCmpeqEpi64(a, b)
    val e = mmSetrEpi64x(-1, 0)
    assertEqM128i(r, e)
}

public fun testMmCvtepi8Epi16() {
    val a = mmSet1Epi8(10)
    val r = mmCvtepi8Epi16(a)
    val e = mmSet1Epi16(10)
    assertEqM128i(r, e)
    val a1 = mmSet1Epi8((-10).toByte())
    val r1 = mmCvtepi8Epi16(a1)
    val e1 = mmSet1Epi16((-10).toShort())
    assertEqM128i(r1, e1)
}

public fun testMmCvtepi8Epi32() {
    val a = mmSet1Epi8(10)
    val r = mmCvtepi8Epi32(a)
    val e = mmSet1Epi32(10)
    assertEqM128i(r, e)
    val a1 = mmSet1Epi8((-10).toByte())
    val r1 = mmCvtepi8Epi32(a1)
    val e1 = mmSet1Epi32(-10)
    assertEqM128i(r1, e1)
}

public fun testMmCvtepi8Epi64() {
    val a = mmSet1Epi8(10)
    val r = mmCvtepi8Epi64(a)
    val e = mmSet1Epi64x(10L)
    assertEqM128i(r, e)
    val a1 = mmSet1Epi8((-10).toByte())
    val r1 = mmCvtepi8Epi64(a1)
    val e1 = mmSet1Epi64x(-10L)
    assertEqM128i(r1, e1)
}

public fun testMmCvtepi16Epi32() {
    val a = mmSet1Epi16(10)
    val r = mmCvtepi16Epi32(a)
    val e = mmSet1Epi32(10)
    assertEqM128i(r, e)
    val a1 = mmSet1Epi16((-10).toShort())
    val r1 = mmCvtepi16Epi32(a1)
    val e1 = mmSet1Epi32(-10)
    assertEqM128i(r1, e1)
}

public fun testMmCvtepi16Epi64() {
    val a = mmSet1Epi16(10)
    val r = mmCvtepi16Epi64(a)
    val e = mmSet1Epi64x(10L)
    assertEqM128i(r, e)
    val a1 = mmSet1Epi16((-10).toShort())
    val r1 = mmCvtepi16Epi64(a1)
    val e1 = mmSet1Epi64x(-10L)
    assertEqM128i(r1, e1)
}

public fun testMmCvtepi32Epi64() {
    val a = mmSet1Epi32(10)
    val r = mmCvtepi32Epi64(a)
    val e = mmSet1Epi64x(10L)
    assertEqM128i(r, e)
    val a1 = mmSet1Epi32(-10)
    val r1 = mmCvtepi32Epi64(a1)
    val e1 = mmSet1Epi64x(-10L)
    assertEqM128i(r1, e1)
}

public fun testMmCvtepu8Epi16() {
    val a = mmSet1Epi8(10)
    val r = mmCvtepu8Epi16(a)
    val e = mmSet1Epi16(10)
    assertEqM128i(r, e)
}

public fun testMmCvtepu8Epi32() {
    val a = mmSet1Epi8(10)
    val r = mmCvtepu8Epi32(a)
    val e = mmSet1Epi32(10)
    assertEqM128i(r, e)
}

public fun testMmCvtepu8Epi64() {
    val a = mmSet1Epi8(10)
    val r = mmCvtepu8Epi64(a)
    val e = mmSet1Epi64x(10L)
    assertEqM128i(r, e)
}

public fun testMmCvtepu16Epi32() {
    val a = mmSet1Epi16(10)
    val r = mmCvtepu16Epi32(a)
    val e = mmSet1Epi32(10)
    assertEqM128i(r, e)
}

public fun testMmCvtepu16Epi64() {
    val a = mmSet1Epi16(10)
    val r = mmCvtepu16Epi64(a)
    val e = mmSet1Epi64x(10L)
    assertEqM128i(r, e)
}

public fun testMmCvtepu32Epi64() {
    val a = mmSet1Epi32(10)
    val r = mmCvtepu32Epi64(a)
    val e = mmSet1Epi64x(10L)
    assertEqM128i(r, e)
}

public fun testMmDpPd() {
    val a = mmSetrPd(2.0, 3.0)
    val b = mmSetrPd(1.0, 4.0)
    val e = mmSetrPd(14.0, 0.0)
    assertEqM128d(mmDpPd(a, b, 0x31), e)
}

public fun testMmDpPs() {
    val a = mmSetrPs(2.0f, 3.0f, 1.0f, 10.0f)
    val b = mmSetrPs(1.0f, 4.0f, 0.5f, 10.0f)
    val e = mmSetrPs(14.5f, 0.0f, 14.5f, 0.0f)
    assertEqM128(mmDpPs(a, b, 0x75), e)
}

public fun testMmFloorPd() {
    val a = mmSetrPd(2.5, 4.5)
    val r = mmFloorPd(a)
    val e = mmSetrPd(2.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMmFloorPs() {
    val a = mmSetrPs(2.5f, 4.5f, 8.5f, 16.5f)
    val r = mmFloorPs(a)
    val e = mmSetrPs(2.0f, 4.0f, 8.0f, 16.0f)
    assertEqM128(r, e)
}

public fun testMmFloorSd() {
    val a = mmSetrPd(2.5, 4.5)
    val b = mmSetrPd(-1.5, -3.5)
    val r = mmFloorSd(a, b)
    val e = mmSetrPd(-2.0, 4.5)
    assertEqM128d(r, e)
}

public fun testMmFloorSs() {
    val a = mmSetrPs(2.5f, 4.5f, 8.5f, 16.5f)
    val b = mmSetrPs(-1.5f, -3.5f, -7.5f, -15.5f)
    val r = mmFloorSs(a, b)
    val e = mmSetrPs(-2.0f, 4.5f, 8.5f, 16.5f)
    assertEqM128(r, e)
}

public fun testMmCeilPd() {
    val a = mmSetrPd(1.5, 3.5)
    val r = mmCeilPd(a)
    val e = mmSetrPd(2.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMmCeilPs() {
    val a = mmSetrPs(1.5f, 3.5f, 7.5f, 15.5f)
    val r = mmCeilPs(a)
    val e = mmSetrPs(2.0f, 4.0f, 8.0f, 16.0f)
    assertEqM128(r, e)
}

public fun testMmCeilSd() {
    val a = mmSetrPd(1.5, 3.5)
    val b = mmSetrPd(-2.5, -4.5)
    val r = mmCeilSd(a, b)
    val e = mmSetrPd(-2.0, 3.5)
    assertEqM128d(r, e)
}

public fun testMmCeilSs() {
    val a = mmSetrPs(1.5f, 3.5f, 7.5f, 15.5f)
    val b = mmSetrPs(-2.5f, -4.5f, -8.5f, -16.5f)
    val r = mmCeilSs(a, b)
    val e = mmSetrPs(-2.0f, 3.5f, 7.5f, 15.5f)
    assertEqM128(r, e)
}

public fun testMmRoundPd() {
    val a = mmSetrPd(1.25, 3.75)
    val r = mmRoundPd(a, MM_FROUND_TO_NEAREST_INT)
    val e = mmSetrPd(1.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMmRoundPs() {
    val a = mmSetrPs(2.25f, 4.75f, -1.75f, -4.25f)
    val r = mmRoundPs(a, MM_FROUND_TO_ZERO)
    val e = mmSetrPs(2.0f, 4.0f, -1.0f, -4.0f)
    assertEqM128(r, e)
}

public fun testMmRoundSd() {
    val a = mmSetrPd(1.5, 3.5)
    val b = mmSetrPd(-2.5, -4.5)
    val r = mmRoundSd(a, b, MM_FROUND_TO_NEAREST_INT)
    val e = mmSetrPd(-2.0, 3.5)
    assertEqM128d(r, e)
    val a1 = mmSetrPd(1.5, 3.5)
    val b1 = mmSetrPd(-2.5, -4.5)
    val r1 = mmRoundSd(a1, b1, MM_FROUND_TO_NEG_INF)
    val e1 = mmSetrPd(-3.0, 3.5)
    assertEqM128d(r1, e1)
    val a2 = mmSetrPd(1.5, 3.5)
    val b2 = mmSetrPd(-2.5, -4.5)
    val r2 = mmRoundSd(a2, b2, MM_FROUND_TO_POS_INF)
    val e2 = mmSetrPd(-2.0, 3.5)
    assertEqM128d(r2, e2)
    val a3 = mmSetrPd(1.5, 3.5)
    val b3 = mmSetrPd(-2.5, -4.5)
    val r3 = mmRoundSd(a3, b3, MM_FROUND_TO_ZERO)
    val e3 = mmSetrPd(-2.0, 3.5)
    assertEqM128d(r3, e3)
}

public fun testMmRoundSs() {
    val a = mmSetrPs(1.5f, 3.5f, 7.5f, 15.5f)
    val b = mmSetrPs(-1.75f, -4.5f, -8.5f, -16.5f)
    val r = mmRoundSs(a, b, MM_FROUND_TO_NEAREST_INT)
    val e = mmSetrPs(-2.0f, 3.5f, 7.5f, 15.5f)
    assertEqM128(r, e)
    val a1 = mmSetrPs(1.5f, 3.5f, 7.5f, 15.5f)
    val b1 = mmSetrPs(-1.75f, -4.5f, -8.5f, -16.5f)
    val r1 = mmRoundSs(a1, b1, MM_FROUND_TO_NEG_INF)
    val e1 = mmSetrPs(-2.0f, 3.5f, 7.5f, 15.5f)
    assertEqM128(r1, e1)
    val a2 = mmSetrPs(1.5f, 3.5f, 7.5f, 15.5f)
    val b2 = mmSetrPs(-1.75f, -4.5f, -8.5f, -16.5f)
    val r2 = mmRoundSs(a2, b2, MM_FROUND_TO_POS_INF)
    val e2 = mmSetrPs(-1.0f, 3.5f, 7.5f, 15.5f)
    assertEqM128(r2, e2)
    val a3 = mmSetrPs(1.5f, 3.5f, 7.5f, 15.5f)
    val b3 = mmSetrPs(-1.75f, -4.5f, -8.5f, -16.5f)
    val r3 = mmRoundSs(a3, b3, MM_FROUND_TO_ZERO)
    val e3 = mmSetrPs(-1.0f, 3.5f, 7.5f, 15.5f)
    assertEqM128(r3, e3)
}

public fun testMmMinposEpu161() {
    val a = mmSetrEpi16(23, 18, 44, 97, 50, 13, 67, 66)
    val r = mmMinposEpu16(a)
    val e = mmSetrEpi16(13, 5, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmMinposEpu162() {
    val a = mmSetrEpi16(0, 18, 44, 97, 50, 13, 67, 66)
    val r = mmMinposEpu16(a)
    val e = mmSetrEpi16(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmMinposEpu163() {
    // Case where the minimum value is repeated
    val a = mmSetrEpi16(23, 18, 44, 97, 50, 13, 67, 13)
    val r = mmMinposEpu16(a)
    val e = mmSetrEpi16(13, 5, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmMulEpi32() {
    run {
        val a = mmSetrEpi32(1, 1, 1, 1)
        val b = mmSetrEpi32(1, 2, 3, 4)
        val r = mmMulEpi32(a, b)
        val e = mmSetrEpi64x(1, 3)
        assertEqM128i(r, e)
    }
    run {
        val a1 = mmSetrEpi32(15, 2, 1234567, 4)
        val b1 = mmSetrEpi32(-20, -256, 666666, 666666)
        val r1 = mmMulEpi32(a1, b1)
        val e1 = mmSetrEpi64x(-300, 823043843622)
        assertEqM128i(r1, e1)
    }
}

public fun testMmMulloEpi32() {
    run {
        val a = mmSetrEpi32(1, 1, 1, 1)
        val b = mmSetrEpi32(1, 2, 3, 4)
        val r = mmMulloEpi32(a, b)
        val e = mmSetrEpi32(1, 2, 3, 4)
        assertEqM128i(r, e)
    }
    run {
        val a1 = mmSetrEpi32(15, -2, 1234567, 99999)
        val b1 = mmSetrEpi32(-20, -256, 666666, -99999)
        val r1 = mmMulloEpi32(a1, b1)
        // Attention, most significant bit in r[2] is treated
        // as a sign bit:
        // 1234567 * 666666 = -1589877210
        val e1 = mmSetrEpi32(-300, 512, -1589877210, -1409865409)
        assertEqM128i(r1, e1)
    }
}

public fun testMmMinposEpu16() {
    val a = mmSetrEpi16(8, 7, 6, 5, 4, 1, 2, 3)
    val r = mmMinposEpu16(a)
    val e = mmSetrEpi16(1, 5, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMmMpsadbwEpu8() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMpsadbwEpu8(a, a, 0x0)
    val e = mmSetrEpi16(0, 4, 8, 12, 16, 20, 24, 28)
    assertEqM128i(r, e)
    val r1 = mmMpsadbwEpu8(a, a, 0x1)
    val e1 = mmSetrEpi16(16, 12, 8, 4, 0, 4, 8, 12)
    assertEqM128i(r1, e1)
    val r2 = mmMpsadbwEpu8(a, a, 0x4)
    val e2 = mmSetrEpi16(16, 20, 24, 28, 32, 36, 40, 44)
    assertEqM128i(r2, e2)
    val r3 = mmMpsadbwEpu8(a, a, 0x5)
    val e3 = mmSetrEpi16(0, 4, 8, 12, 16, 20, 24, 28)
    assertEqM128i(r3, e3)
    val r4 = mmMpsadbwEpu8(a, a, 0x7)
    val e4 = mmSetrEpi16(32, 28, 24, 20, 16, 12, 8, 4)
    assertEqM128i(r4, e4)
}

public fun testMmTestzSi128() {
    val a = mmSet1Epi8(1)
    val mask = mmSet1Epi8(0)
    val r = mmTestzSi128(a, mask)
    check(r == 1)
    val a1 = mmSet1Epi8(0x5)
    val mask1 = mmSet1Epi8(0x6)
    val r1 = mmTestzSi128(a1, mask1)
    check(r1 == 0)
    val a2 = mmSet1Epi8(0x3)
    val mask2 = mmSet1Epi8(0x4)
    val r2 = mmTestzSi128(a2, mask2)
    check(r2 == 1)
}

public fun testMmTestcSi128() {
    val a = mmSet1Epi8((-1).toByte())
    val mask = mmSet1Epi8(0)
    val r = mmTestcSi128(a, mask)
    check(r == 1)
    val a1 = mmSet1Epi8(0x5)
    val mask1 = mmSet1Epi8(0x6)
    val r1 = mmTestcSi128(a1, mask1)
    check(r1 == 0)
    val a2 = mmSet1Epi8(0x5)
    val mask2 = mmSet1Epi8(0x4)
    val r2 = mmTestcSi128(a2, mask2)
    check(r2 == 1)
}

public fun testMmTestnzcSi128() {
    val a = mmSet1Epi8(0)
    val mask = mmSet1Epi8(1)
    val r = mmTestnzcSi128(a, mask)
    check(r == 0)
    val a1 = mmSet1Epi8((-1).toByte())
    val mask1 = mmSet1Epi8(0)
    val r1 = mmTestnzcSi128(a1, mask1)
    check(r1 == 0)
    val a2 = mmSet1Epi8(0x5)
    val mask2 = mmSet1Epi8(0x6)
    val r2 = mmTestnzcSi128(a2, mask2)
    check(r2 == 1)
    val a3 = mmSet1Epi8(0x5)
    val mask3 = mmSet1Epi8(0x5)
    val r3 = mmTestnzcSi128(a3, mask3)
    check(r3 == 0)
}

public fun testMmTestAllZeros() {
    val a = mmSet1Epi8(1)
    val mask = mmSet1Epi8(0)
    val r = mmTestAllZeros(a, mask)
    check(r == 1)
    val a1 = mmSet1Epi8(0x5)
    val mask1 = mmSet1Epi8(0x6)
    val r1 = mmTestAllZeros(a1, mask1)
    check(r1 == 0)
    val a2 = mmSet1Epi8(0x3)
    val mask2 = mmSet1Epi8(0x4)
    val r2 = mmTestAllZeros(a2, mask2)
    check(r2 == 1)
}

public fun testMmTestAllOnes() {
    val a = mmSet1Epi8((-1).toByte())
    val r = mmTestAllOnes(a)
    check(r == 1)
    val a1 = mmSet1Epi8(0x5)
    val r1 = mmTestAllOnes(a1)
    check(r1 == 0)
}

public fun testMmTestMixOnesZeros() {
    val a = mmSet1Epi8(0)
    val mask = mmSet1Epi8(1)
    val r = mmTestMixOnesZeros(a, mask)
    check(r == 0)
    val a1 = mmSet1Epi8((-1).toByte())
    val mask1 = mmSet1Epi8(0)
    val r1 = mmTestMixOnesZeros(a1, mask1)
    check(r1 == 0)
    val a2 = mmSet1Epi8(0x5)
    val mask2 = mmSet1Epi8(0x6)
    val r2 = mmTestMixOnesZeros(a2, mask2)
    check(r2 == 1)
    val a3 = mmSet1Epi8(0x5)
    val mask3 = mmSet1Epi8(0x5)
    val r3 = mmTestMixOnesZeros(a3, mask3)
    check(r3 == 0)
}

public fun testMmStreamLoadSi128() {
    val a = mmSetEpi64x(5L, 6L)
    val r = mmStreamLoadSi128(constPtrFromRustCast(addrOf(a)))
    assertEqM128i(a, r)
}
