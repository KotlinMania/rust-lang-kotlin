// port-lint: tests x86/avx512vbmi.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts

public fun testMm512Permutex2varEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val idx = mm512SetEpi8(1, (1.toInt() shl 6).toByte(), 2, (1.toInt() shl 6).toByte(), 3, (1.toInt() shl 6).toByte(), 4, (1.toInt() shl 6).toByte(), 5, (1.toInt() shl 6).toByte(), 6, (1.toInt() shl 6).toByte(), 7, (1.toInt() shl 6).toByte(), 8, (1.toInt() shl 6).toByte(), 9, (1.toInt() shl 6).toByte(), 10, (1.toInt() shl 6).toByte(), 11, (1.toInt() shl 6).toByte(), 12, (1.toInt() shl 6).toByte(), 13, (1.toInt() shl 6).toByte(), 14, (1.toInt() shl 6).toByte(), 15, (1.toInt() shl 6).toByte(), 16, (1.toInt() shl 6).toByte(), 17, (1.toInt() shl 6).toByte(), 18, (1.toInt() shl 6).toByte(), 19, (1.toInt() shl 6).toByte(), 20, (1.toInt() shl 6).toByte(), 21, (1.toInt() shl 6).toByte(), 22, (1.toInt() shl 6).toByte(), 23, (1.toInt() shl 6).toByte(), 24, (1.toInt() shl 6).toByte(), 25, (1.toInt() shl 6).toByte(), 26, (1.toInt() shl 6).toByte(), 27, (1.toInt() shl 6).toByte(), 28, (1.toInt() shl 6).toByte(), 29, (1.toInt() shl 6).toByte(), 30, (1.toInt() shl 6).toByte(), 31, (1.toInt() shl 6).toByte(), 32, (1.toInt() shl 6).toByte())
    val b = mm512Set1Epi8(100)
    val r = mm512Permutex2varEpi8(a, idx, b)
    val e = mm512SetEpi8(62, 100, 61, 100, 60, 100, 59, 100, 58, 100, 57, 100, 56, 100, 55, 100, 54, 100, 53, 100, 52, 100, 51, 100, 50, 100, 49, 100, 48, 100, 47, 100, 46, 100, 45, 100, 44, 100, 43, 100, 42, 100, 41, 100, 40, 100, 39, 100, 38, 100, 37, 100, 36, 100, 35, 100, 34, 100, 33, 100, 32, 100, 31, 100)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutex2varEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val idx = mm512SetEpi8(1, (1.toInt() shl 6).toByte(), 2, (1.toInt() shl 6).toByte(), 3, (1.toInt() shl 6).toByte(), 4, (1.toInt() shl 6).toByte(), 5, (1.toInt() shl 6).toByte(), 6, (1.toInt() shl 6).toByte(), 7, (1.toInt() shl 6).toByte(), 8, (1.toInt() shl 6).toByte(), 9, (1.toInt() shl 6).toByte(), 10, (1.toInt() shl 6).toByte(), 11, (1.toInt() shl 6).toByte(), 12, (1.toInt() shl 6).toByte(), 13, (1.toInt() shl 6).toByte(), 14, (1.toInt() shl 6).toByte(), 15, (1.toInt() shl 6).toByte(), 16, (1.toInt() shl 6).toByte(), 17, (1.toInt() shl 6).toByte(), 18, (1.toInt() shl 6).toByte(), 19, (1.toInt() shl 6).toByte(), 20, (1.toInt() shl 6).toByte(), 21, (1.toInt() shl 6).toByte(), 22, (1.toInt() shl 6).toByte(), 23, (1.toInt() shl 6).toByte(), 24, (1.toInt() shl 6).toByte(), 25, (1.toInt() shl 6).toByte(), 26, (1.toInt() shl 6).toByte(), 27, (1.toInt() shl 6).toByte(), 28, (1.toInt() shl 6).toByte(), 29, (1.toInt() shl 6).toByte(), 30, (1.toInt() shl 6).toByte(), 31, (1.toInt() shl 6).toByte(), 32, (1.toInt() shl 6).toByte())
    val b = mm512Set1Epi8(100)
    val r = mm512MaskPermutex2varEpi8(a, 0uL, idx, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutex2varEpi8(a, 0xFFFFFFFFFFFFFFFFuL, idx, b)
    val e = mm512SetEpi8(62, 100, 61, 100, 60, 100, 59, 100, 58, 100, 57, 100, 56, 100, 55, 100, 54, 100, 53, 100, 52, 100, 51, 100, 50, 100, 49, 100, 48, 100, 47, 100, 46, 100, 45, 100, 44, 100, 43, 100, 42, 100, 41, 100, 40, 100, 39, 100, 38, 100, 37, 100, 36, 100, 35, 100, 34, 100, 33, 100, 32, 100, 31, 100)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutex2varEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val idx = mm512SetEpi8(1, (1.toInt() shl 6).toByte(), 2, (1.toInt() shl 6).toByte(), 3, (1.toInt() shl 6).toByte(), 4, (1.toInt() shl 6).toByte(), 5, (1.toInt() shl 6).toByte(), 6, (1.toInt() shl 6).toByte(), 7, (1.toInt() shl 6).toByte(), 8, (1.toInt() shl 6).toByte(), 9, (1.toInt() shl 6).toByte(), 10, (1.toInt() shl 6).toByte(), 11, (1.toInt() shl 6).toByte(), 12, (1.toInt() shl 6).toByte(), 13, (1.toInt() shl 6).toByte(), 14, (1.toInt() shl 6).toByte(), 15, (1.toInt() shl 6).toByte(), 16, (1.toInt() shl 6).toByte(), 17, (1.toInt() shl 6).toByte(), 18, (1.toInt() shl 6).toByte(), 19, (1.toInt() shl 6).toByte(), 20, (1.toInt() shl 6).toByte(), 21, (1.toInt() shl 6).toByte(), 22, (1.toInt() shl 6).toByte(), 23, (1.toInt() shl 6).toByte(), 24, (1.toInt() shl 6).toByte(), 25, (1.toInt() shl 6).toByte(), 26, (1.toInt() shl 6).toByte(), 27, (1.toInt() shl 6).toByte(), 28, (1.toInt() shl 6).toByte(), 29, (1.toInt() shl 6).toByte(), 30, (1.toInt() shl 6).toByte(), 31, (1.toInt() shl 6).toByte(), 32, (1.toInt() shl 6).toByte())
    val b = mm512Set1Epi8(100)
    val r = mm512MaskzPermutex2varEpi8(0uL, a, idx, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutex2varEpi8(0xFFFFFFFFFFFFFFFFuL, a, idx, b)
    val e = mm512SetEpi8(62, 100, 61, 100, 60, 100, 59, 100, 58, 100, 57, 100, 56, 100, 55, 100, 54, 100, 53, 100, 52, 100, 51, 100, 50, 100, 49, 100, 48, 100, 47, 100, 46, 100, 45, 100, 44, 100, 43, 100, 42, 100, 41, 100, 40, 100, 39, 100, 38, 100, 37, 100, 36, 100, 35, 100, 34, 100, 33, 100, 32, 100, 31, 100)
    assertEqM512i(r1, e)
}

public fun testMm512Mask2Permutex2varEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val idx = mm512SetEpi8(1, (1.toInt() shl 6).toByte(), 2, (1.toInt() shl 6).toByte(), 3, (1.toInt() shl 6).toByte(), 4, (1.toInt() shl 6).toByte(), 5, (1.toInt() shl 6).toByte(), 6, (1.toInt() shl 6).toByte(), 7, (1.toInt() shl 6).toByte(), 8, (1.toInt() shl 6).toByte(), 9, (1.toInt() shl 6).toByte(), 10, (1.toInt() shl 6).toByte(), 11, (1.toInt() shl 6).toByte(), 12, (1.toInt() shl 6).toByte(), 13, (1.toInt() shl 6).toByte(), 14, (1.toInt() shl 6).toByte(), 15, (1.toInt() shl 6).toByte(), 16, (1.toInt() shl 6).toByte(), 17, (1.toInt() shl 6).toByte(), 18, (1.toInt() shl 6).toByte(), 19, (1.toInt() shl 6).toByte(), 20, (1.toInt() shl 6).toByte(), 21, (1.toInt() shl 6).toByte(), 22, (1.toInt() shl 6).toByte(), 23, (1.toInt() shl 6).toByte(), 24, (1.toInt() shl 6).toByte(), 25, (1.toInt() shl 6).toByte(), 26, (1.toInt() shl 6).toByte(), 27, (1.toInt() shl 6).toByte(), 28, (1.toInt() shl 6).toByte(), 29, (1.toInt() shl 6).toByte(), 30, (1.toInt() shl 6).toByte(), 31, (1.toInt() shl 6).toByte(), 32, (1.toInt() shl 6).toByte())
    val b = mm512Set1Epi8(100)
    val r = mm512Mask2Permutex2varEpi8(a, idx, 0uL, b)
    assertEqM512i(r, idx)
    val r1 = mm512Mask2Permutex2varEpi8(a, idx, 0xFFFFFFFFFFFFFFFFuL, b)
    val e = mm512SetEpi8(62, 100, 61, 100, 60, 100, 59, 100, 58, 100, 57, 100, 56, 100, 55, 100, 54, 100, 53, 100, 52, 100, 51, 100, 50, 100, 49, 100, 48, 100, 47, 100, 46, 100, 45, 100, 44, 100, 43, 100, 42, 100, 41, 100, 40, 100, 39, 100, 38, 100, 37, 100, 36, 100, 35, 100, 34, 100, 33, 100, 32, 100, 31, 100)
    assertEqM512i(r1, e)
}

public fun testMm256Permutex2varEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm256SetEpi8(1, (1.toInt() shl 5).toByte(), 2, (1.toInt() shl 5).toByte(), 3, (1.toInt() shl 5).toByte(), 4, (1.toInt() shl 5).toByte(), 5, (1.toInt() shl 5).toByte(), 6, (1.toInt() shl 5).toByte(), 7, (1.toInt() shl 5).toByte(), 8, (1.toInt() shl 5).toByte(), 9, (1.toInt() shl 5).toByte(), 10, (1.toInt() shl 5).toByte(), 11, (1.toInt() shl 5).toByte(), 12, (1.toInt() shl 5).toByte(), 13, (1.toInt() shl 5).toByte(), 14, (1.toInt() shl 5).toByte(), 15, (1.toInt() shl 5).toByte(), 16, (1.toInt() shl 5).toByte())
    val b = mm256Set1Epi8(100)
    val r = mm256Permutex2varEpi8(a, idx, b)
    val e = mm256SetEpi8(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutex2varEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm256SetEpi8(1, (1.toInt() shl 5).toByte(), 2, (1.toInt() shl 5).toByte(), 3, (1.toInt() shl 5).toByte(), 4, (1.toInt() shl 5).toByte(), 5, (1.toInt() shl 5).toByte(), 6, (1.toInt() shl 5).toByte(), 7, (1.toInt() shl 5).toByte(), 8, (1.toInt() shl 5).toByte(), 9, (1.toInt() shl 5).toByte(), 10, (1.toInt() shl 5).toByte(), 11, (1.toInt() shl 5).toByte(), 12, (1.toInt() shl 5).toByte(), 13, (1.toInt() shl 5).toByte(), 14, (1.toInt() shl 5).toByte(), 15, (1.toInt() shl 5).toByte(), 16, (1.toInt() shl 5).toByte())
    val b = mm256Set1Epi8(100)
    val r = mm256MaskPermutex2varEpi8(a, 0u, idx, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutex2varEpi8(a, 0xFFFFFFFFu, idx, b)
    val e = mm256SetEpi8(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutex2varEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm256SetEpi8(1, (1.toInt() shl 5).toByte(), 2, (1.toInt() shl 5).toByte(), 3, (1.toInt() shl 5).toByte(), 4, (1.toInt() shl 5).toByte(), 5, (1.toInt() shl 5).toByte(), 6, (1.toInt() shl 5).toByte(), 7, (1.toInt() shl 5).toByte(), 8, (1.toInt() shl 5).toByte(), 9, (1.toInt() shl 5).toByte(), 10, (1.toInt() shl 5).toByte(), 11, (1.toInt() shl 5).toByte(), 12, (1.toInt() shl 5).toByte(), 13, (1.toInt() shl 5).toByte(), 14, (1.toInt() shl 5).toByte(), 15, (1.toInt() shl 5).toByte(), 16, (1.toInt() shl 5).toByte())
    val b = mm256Set1Epi8(100)
    val r = mm256MaskzPermutex2varEpi8(0u, a, idx, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutex2varEpi8(0xFFFFFFFFu, a, idx, b)
    val e = mm256SetEpi8(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM256i(r1, e)
}

public fun testMm256Mask2Permutex2varEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm256SetEpi8(1, (1.toInt() shl 5).toByte(), 2, (1.toInt() shl 5).toByte(), 3, (1.toInt() shl 5).toByte(), 4, (1.toInt() shl 5).toByte(), 5, (1.toInt() shl 5).toByte(), 6, (1.toInt() shl 5).toByte(), 7, (1.toInt() shl 5).toByte(), 8, (1.toInt() shl 5).toByte(), 9, (1.toInt() shl 5).toByte(), 10, (1.toInt() shl 5).toByte(), 11, (1.toInt() shl 5).toByte(), 12, (1.toInt() shl 5).toByte(), 13, (1.toInt() shl 5).toByte(), 14, (1.toInt() shl 5).toByte(), 15, (1.toInt() shl 5).toByte(), 16, (1.toInt() shl 5).toByte())
    val b = mm256Set1Epi8(100)
    val r = mm256Mask2Permutex2varEpi8(a, idx, 0u, b)
    assertEqM256i(r, idx)
    val r1 = mm256Mask2Permutex2varEpi8(a, idx, 0xFFFFFFFFu, b)
    val e = mm256SetEpi8(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM256i(r1, e)
}

public fun testMmPermutex2varEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mmSetEpi8(1, (1.toInt() shl 4).toByte(), 2, (1.toInt() shl 4).toByte(), 3, (1.toInt() shl 4).toByte(), 4, (1.toInt() shl 4).toByte(), 5, (1.toInt() shl 4).toByte(), 6, (1.toInt() shl 4).toByte(), 7, (1.toInt() shl 4).toByte(), 8, (1.toInt() shl 4).toByte())
    val b = mmSet1Epi8(100)
    val r = mmPermutex2varEpi8(a, idx, b)
    val e = mmSetEpi8(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM128i(r, e)
}

public fun testMmMaskPermutex2varEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mmSetEpi8(1, (1.toInt() shl 4).toByte(), 2, (1.toInt() shl 4).toByte(), 3, (1.toInt() shl 4).toByte(), 4, (1.toInt() shl 4).toByte(), 5, (1.toInt() shl 4).toByte(), 6, (1.toInt() shl 4).toByte(), 7, (1.toInt() shl 4).toByte(), 8, (1.toInt() shl 4).toByte())
    val b = mmSet1Epi8(100)
    val r = mmMaskPermutex2varEpi8(a, 0u.toUShort(), idx, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPermutex2varEpi8(a, 0xFFFFu.toUShort(), idx, b)
    val e = mmSetEpi8(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPermutex2varEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mmSetEpi8(1, (1.toInt() shl 4).toByte(), 2, (1.toInt() shl 4).toByte(), 3, (1.toInt() shl 4).toByte(), 4, (1.toInt() shl 4).toByte(), 5, (1.toInt() shl 4).toByte(), 6, (1.toInt() shl 4).toByte(), 7, (1.toInt() shl 4).toByte(), 8, (1.toInt() shl 4).toByte())
    val b = mmSet1Epi8(100)
    val r = mmMaskzPermutex2varEpi8(0u.toUShort(), a, idx, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPermutex2varEpi8(0xFFFFu.toUShort(), a, idx, b)
    val e = mmSetEpi8(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM128i(r1, e)
}

public fun testMmMask2Permutex2varEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mmSetEpi8(1, (1.toInt() shl 4).toByte(), 2, (1.toInt() shl 4).toByte(), 3, (1.toInt() shl 4).toByte(), 4, (1.toInt() shl 4).toByte(), 5, (1.toInt() shl 4).toByte(), 6, (1.toInt() shl 4).toByte(), 7, (1.toInt() shl 4).toByte(), 8, (1.toInt() shl 4).toByte())
    val b = mmSet1Epi8(100)
    val r = mmMask2Permutex2varEpi8(a, idx, 0u.toUShort(), b)
    assertEqM128i(r, idx)
    val r1 = mmMask2Permutex2varEpi8(a, idx, 0xFFFFu.toUShort(), b)
    val e = mmSetEpi8(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM128i(r1, e)
}

public fun testMm512PermutexvarEpi8() {
    val idxArr: UByteArray = ubyteArrayOf(34u.toUByte(), 58u.toUByte(), 27u.toUByte(), 72u.toUByte(), 19u.toUByte(), 38u.toUByte(), 53u.toUByte(), 0u.toUByte(), 44u.toUByte(), 15u.toUByte(), 29u.toUByte(), 81u.toUByte(), 20u.toUByte(), 35u.toUByte(), 62u.toUByte(), 6u.toUByte(), 24u.toUByte(), 49u.toUByte(), 10u.toUByte(), 68u.toUByte(), 22u.toUByte(), 57u.toUByte(), 40u.toUByte(), 13u.toUByte(), 52u.toUByte(), 1u.toUByte(), 30u.toUByte(), 95u.toUByte(), 16u.toUByte(), 33u.toUByte(), 4u.toUByte(), 55u.toUByte(), 26u.toUByte(), 47u.toUByte(), 11u.toUByte(), 74u.toUByte(), 21u.toUByte(), 59u.toUByte(), 36u.toUByte(), 8u.toUByte(), 50u.toUByte(), 3u.toUByte(), 28u.toUByte(), 99u.toUByte(), 14u.toUByte(), 45u.toUByte(), 61u.toUByte(), 2u.toUByte(), 18u.toUByte(), 39u.toUByte(), 7u.toUByte(), 64u.toUByte(), 23u.toUByte(), 54u.toUByte(), 9u.toUByte(), 127u.toUByte(), 12u.toUByte(), 31u.toUByte(), 88u.toUByte(), 5u.toUByte(), 42u.toUByte(), 17u.toUByte(), 0u.toUByte(), 63u.toUByte())
    val aArr: ByteArray = byteArrayOf((-93).toByte(), (-94).toByte(), (-95).toByte(), (-96).toByte(), (-97).toByte(), (-98).toByte(), (-99).toByte(), (-100).toByte(), (-101).toByte(), (-102).toByte(), (-103).toByte(), (-104).toByte(), (-105).toByte(), (-106).toByte(), (-107).toByte(), (-108).toByte(), (-109).toByte(), (-110).toByte(), (-111).toByte(), (-112).toByte(), (-113).toByte(), (-114).toByte(), (-115).toByte(), (-116).toByte(), (-117).toByte(), (-118).toByte(), (-119).toByte(), (-120).toByte(), (-121).toByte(), (-122).toByte(), (-123).toByte(), (-124).toByte(), (-125).toByte(), (-126).toByte(), (-127).toByte(), (-128).toByte(), 127, 126, 125, 124, 123, 122, 121, 120, 119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100)
    val idx = transmuteWithLayouts<UByteArray, Unit>(idxArr, PrimitiveArrayLayouts.uByteArray((64).toInt()), UnresolvedTypeLayout<Unit>())
    val a = transmuteWithLayouts<ByteArray, Unit>(aArr, PrimitiveArrayLayouts.byteArray((64).toInt()), UnresolvedTypeLayout<Unit>())
    val r = mm512PermutexvarEpi8(idx, a)
    val eArr = fromFn({ i -> aArr[(idxArr[i] and 0x3Fu.toUByte()).toInt()] })
    val e = transmuteWithLayouts<ByteArray, Unit>(eArr, PrimitiveArrayLayouts.byteArray((64).toInt()), UnresolvedTypeLayout<Unit>())
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutexvarEpi8() {
    val idx = mm512Set1Epi8(1)
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm512MaskPermutexvarEpi8(a, 0uL, idx, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutexvarEpi8(a, 0xFFFFFFFFFFFFFFFFuL, idx, a)
    val e = mm512Set1Epi8(62)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutexvarEpi8() {
    val idx = mm512Set1Epi8(1)
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm512MaskzPermutexvarEpi8(0uL, idx, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutexvarEpi8(0xFFFFFFFFFFFFFFFFuL, idx, a)
    val e = mm512Set1Epi8(62)
    assertEqM512i(r1, e)
}

public fun testMm256PermutexvarEpi8() {
    val idxArr: UByteArray = ubyteArrayOf(26u.toUByte(), 47u.toUByte(), 11u.toUByte(), 42u.toUByte(), 21u.toUByte(), 59u.toUByte(), 36u.toUByte(), 8u.toUByte(), 18u.toUByte(), 3u.toUByte(), 28u.toUByte(), 31u.toUByte(), 14u.toUByte(), 45u.toUByte(), 29u.toUByte(), 2u.toUByte(), 24u.toUByte(), 17u.toUByte(), 10u.toUByte(), 30u.toUByte(), 22u.toUByte(), 25u.toUByte(), 12u.toUByte(), 13u.toUByte(), 20u.toUByte(), 1u.toUByte(), 6u.toUByte(), 15u.toUByte(), 16u.toUByte(), 9u.toUByte(), 0u.toUByte(), 63u.toUByte())
    val aArr: ByteArray = byteArrayOf((-93).toByte(), (-94).toByte(), (-95).toByte(), (-96).toByte(), (-97).toByte(), (-98).toByte(), (-99).toByte(), (-100).toByte(), (-101).toByte(), (-102).toByte(), (-103).toByte(), (-104).toByte(), (-105).toByte(), (-106).toByte(), (-107).toByte(), (-108).toByte(), (-109).toByte(), (-110).toByte(), (-111).toByte(), (-112).toByte(), (-113).toByte(), (-114).toByte(), (-115).toByte(), (-116).toByte(), (-117).toByte(), (-118).toByte(), (-119).toByte(), (-120).toByte(), (-121).toByte(), (-122).toByte(), (-123).toByte(), (-124).toByte())
    val idx = transmuteWithLayouts<UByteArray, Unit>(idxArr, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Unit>())
    val a = transmuteWithLayouts<ByteArray, Unit>(aArr, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Unit>())
    val r = mm256PermutexvarEpi8(idx, a)
    val eArr = fromFn({ i -> aArr[(idxArr[i] and 0x1Fu.toUByte()).toInt()] })
    val e = transmuteWithLayouts<ByteArray, Unit>(eArr, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Unit>())
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutexvarEpi8() {
    val idx = mm256Set1Epi8(1)
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256MaskPermutexvarEpi8(a, 0u, idx, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutexvarEpi8(a, 0xFFFFFFFFu, idx, a)
    val e = mm256Set1Epi8(30)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutexvarEpi8() {
    val idx = mm256Set1Epi8(1)
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256MaskzPermutexvarEpi8(0u, idx, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutexvarEpi8(0xFFFFFFFFu, idx, a)
    val e = mm256Set1Epi8(30)
    assertEqM256i(r1, e)
}

public fun testMmPermutexvarEpi8() {
    val idxArr: UByteArray = ubyteArrayOf(14u.toUByte(), 3u.toUByte(), 12u.toUByte(), 7u.toUByte(), 8u.toUByte(), 1u.toUByte(), 10u.toUByte(), 15u.toUByte(), 4u.toUByte(), 9u.toUByte(), 2u.toUByte(), 13u.toUByte(), 6u.toUByte(), 11u.toUByte(), 0u.toUByte(), 31u.toUByte())
    val aArr: ByteArray = byteArrayOf((-93).toByte(), (-94).toByte(), (-95).toByte(), (-96).toByte(), (-97).toByte(), (-98).toByte(), (-99).toByte(), (-100).toByte(), (-101).toByte(), (-102).toByte(), (-103).toByte(), (-104).toByte(), (-105).toByte(), (-106).toByte(), (-107).toByte(), (-108).toByte())
    val idx = transmuteWithLayouts<UByteArray, Unit>(idxArr, PrimitiveArrayLayouts.uByteArray((16).toInt()), UnresolvedTypeLayout<Unit>())
    val a = transmuteWithLayouts<ByteArray, Unit>(aArr, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<Unit>())
    val r = mmPermutexvarEpi8(idx, a)
    val eArr = fromFn({ i -> aArr[(idxArr[i] and 0xFu.toUByte()).toInt()] })
    val e = transmuteWithLayouts<ByteArray, Unit>(eArr, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<Unit>())
    assertEqM128i(r, e)
}

public fun testMmMaskPermutexvarEpi8() {
    val idx = mmSet1Epi8(1)
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskPermutexvarEpi8(a, 0u.toUShort(), idx, a)
    assertEqM128i(r, a)
    val r1 = mmMaskPermutexvarEpi8(a, 0xFFFFu.toUShort(), idx, a)
    val e = mmSet1Epi8(14)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPermutexvarEpi8() {
    val idx = mmSet1Epi8(1)
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzPermutexvarEpi8(0u.toUShort(), idx, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPermutexvarEpi8(0xFFFFu.toUShort(), idx, a)
    val e = mmSet1Epi8(14)
    assertEqM128i(r1, e)
}

public fun testMm512MultishiftEpi64Epi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MultishiftEpi64Epi8(a, b)
    val e = mm512Set1Epi8((1.toInt() shl 7).toByte())
    assertEqM512i(r, e)
}

public fun testMm512MaskMultishiftEpi64Epi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskMultishiftEpi64Epi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMultishiftEpi64Epi8(a, 0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512Set1Epi8((1.toInt() shl 7).toByte())
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMultishiftEpi64Epi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskzMultishiftEpi64Epi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMultishiftEpi64Epi8(0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512Set1Epi8((1.toInt() shl 7).toByte())
    assertEqM512i(r1, e)
}

public fun testMm256MultishiftEpi64Epi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val r = mm256MultishiftEpi64Epi8(a, b)
    val e = mm256Set1Epi8((1.toInt() shl 7).toByte())
    assertEqM256i(r, e)
}

public fun testMm256MaskMultishiftEpi64Epi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskMultishiftEpi64Epi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMultishiftEpi64Epi8(a, 0xFFFFFFFFu, a, b)
    val e = mm256Set1Epi8((1.toInt() shl 7).toByte())
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMultishiftEpi64Epi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskzMultishiftEpi64Epi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMultishiftEpi64Epi8(0xFFFFFFFFu, a, b)
    val e = mm256Set1Epi8((1.toInt() shl 7).toByte())
    assertEqM256i(r1, e)
}

public fun testMmMultishiftEpi64Epi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val r = mmMultishiftEpi64Epi8(a, b)
    val e = mmSet1Epi8((1.toInt() shl 7).toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskMultishiftEpi64Epi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val r = mmMaskMultishiftEpi64Epi8(a, 0u.toUShort(), a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMultishiftEpi64Epi8(a, 0xFFFFu.toUShort(), a, b)
    val e = mmSet1Epi8((1.toInt() shl 7).toByte())
    assertEqM128i(r1, e)
}

public fun testMmMaskzMultishiftEpi64Epi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val r = mmMaskzMultishiftEpi64Epi8(0u.toUShort(), a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMultishiftEpi64Epi8(0xFFFFu.toUShort(), a, b)
    val e = mmSet1Epi8((1.toInt() shl 7).toByte())
    assertEqM128i(r1, e)
}
