// port-lint: tests x86/avx512vbmi2.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.asPtr

public fun testMm512MaskCompressEpi16() {
    val src = mm512Set1Epi16(200)
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskCompressEpi16(src, 0x55555555u, a)
    val e = mm512SetEpi16(200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCompressEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskzCompressEpi16(0x55555555u, a)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
    assertEqM512i(r, e)
}

public fun testMm256MaskCompressEpi16() {
    val src = mm256Set1Epi16(200)
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskCompressEpi16(src, 0x5555u.toUShort(), a)
    val e = mm256SetEpi16(200, 200, 200, 200, 200, 200, 200, 200, 1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCompressEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCompressEpi16(0x5555u.toUShort(), a)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM256i(r, e)
}

public fun testMmMaskCompressEpi16() {
    val src = mmSet1Epi16(200)
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskCompressEpi16(src, 0x55u.toUByte(), a)
    val e = mmSetEpi16(200, 200, 200, 200, 1, 3, 5, 7)
    assertEqM128i(r, e)
}

public fun testMmMaskzCompressEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskzCompressEpi16(0x55u.toUByte(), a)
    val e = mmSetEpi16(0, 0, 0, 0, 1, 3, 5, 7)
    assertEqM128i(r, e)
}

public fun testMm512MaskCompressEpi8() {
    val src = mm512Set1Epi8(100)
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm512MaskCompressEpi8(src, 0x5555555555555555uL, a)
    val e = mm512SetEpi8(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63)
    assertEqM512i(r, e)
}

public fun testMm512MaskzCompressEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm512MaskzCompressEpi8(0x5555555555555555uL, a)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63)
    assertEqM512i(r, e)
}

public fun testMm256MaskCompressEpi8() {
    val src = mm256Set1Epi8(100)
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256MaskCompressEpi8(src, 0x55555555u, a)
    val e = mm256SetEpi8(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
    assertEqM256i(r, e)
}

public fun testMm256MaskzCompressEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256MaskzCompressEpi8(0x55555555u, a)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)
    assertEqM256i(r, e)
}

public fun testMmMaskCompressEpi8() {
    val src = mmSet1Epi8(100)
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskCompressEpi8(src, 0x5555u.toUShort(), a)
    val e = mmSetEpi8(100, 100, 100, 100, 100, 100, 100, 100, 1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM128i(r, e)
}

public fun testMmMaskzCompressEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCompressEpi8(0x5555u.toUShort(), a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 5, 7, 9, 11, 13, 15)
    assertEqM128i(r, e)
}

public fun testMm512MaskExpandEpi16() {
    val src = mm512Set1Epi16(200)
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskExpandEpi16(src, 0x55555555u, a)
    val e = mm512SetEpi16(200, 16, 200, 17, 200, 18, 200, 19, 200, 20, 200, 21, 200, 22, 200, 23, 200, 24, 200, 25, 200, 26, 200, 27, 200, 28, 200, 29, 200, 30, 200, 31)
    assertEqM512i(r, e)
}

public fun testMm512MaskzExpandEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskzExpandEpi16(0x55555555u, a)
    val e = mm512SetEpi16(0, 16, 0, 17, 0, 18, 0, 19, 0, 20, 0, 21, 0, 22, 0, 23, 0, 24, 0, 25, 0, 26, 0, 27, 0, 28, 0, 29, 0, 30, 0, 31)
    assertEqM512i(r, e)
}

public fun testMm256MaskExpandEpi16() {
    val src = mm256Set1Epi16(200)
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskExpandEpi16(src, 0x5555u.toUShort(), a)
    val e = mm256SetEpi16(200, 8, 200, 9, 200, 10, 200, 11, 200, 12, 200, 13, 200, 14, 200, 15)
    assertEqM256i(r, e)
}

public fun testMm256MaskzExpandEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzExpandEpi16(0x5555u.toUShort(), a)
    val e = mm256SetEpi16(0, 8, 0, 9, 0, 10, 0, 11, 0, 12, 0, 13, 0, 14, 0, 15)
    assertEqM256i(r, e)
}

public fun testMmMaskExpandEpi16() {
    val src = mmSet1Epi16(200)
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskExpandEpi16(src, 0x55u.toUByte(), a)
    val e = mmSetEpi16(200, 4, 200, 5, 200, 6, 200, 7)
    assertEqM128i(r, e)
}

public fun testMmMaskzExpandEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskzExpandEpi16(0x55u.toUByte(), a)
    val e = mmSetEpi16(0, 4, 0, 5, 0, 6, 0, 7)
    assertEqM128i(r, e)
}

public fun testMm512MaskExpandEpi8() {
    val src = mm512Set1Epi8(100)
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm512MaskExpandEpi8(src, 0x5555555555555555uL, a)
    val e = mm512SetEpi8(100, 32, 100, 33, 100, 34, 100, 35, 100, 36, 100, 37, 100, 38, 100, 39, 100, 40, 100, 41, 100, 42, 100, 43, 100, 44, 100, 45, 100, 46, 100, 47, 100, 48, 100, 49, 100, 50, 100, 51, 100, 52, 100, 53, 100, 54, 100, 55, 100, 56, 100, 57, 100, 58, 100, 59, 100, 60, 100, 61, 100, 62, 100, 63)
    assertEqM512i(r, e)
}

public fun testMm512MaskzExpandEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm512MaskzExpandEpi8(0x5555555555555555uL, a)
    val e = mm512SetEpi8(0, 32, 0, 33, 0, 34, 0, 35, 0, 36, 0, 37, 0, 38, 0, 39, 0, 40, 0, 41, 0, 42, 0, 43, 0, 44, 0, 45, 0, 46, 0, 47, 0, 48, 0, 49, 0, 50, 0, 51, 0, 52, 0, 53, 0, 54, 0, 55, 0, 56, 0, 57, 0, 58, 0, 59, 0, 60, 0, 61, 0, 62, 0, 63)
    assertEqM512i(r, e)
}

public fun testMm256MaskExpandEpi8() {
    val src = mm256Set1Epi8(100)
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256MaskExpandEpi8(src, 0x55555555u, a)
    val e = mm256SetEpi8(100, 16, 100, 17, 100, 18, 100, 19, 100, 20, 100, 21, 100, 22, 100, 23, 100, 24, 100, 25, 100, 26, 100, 27, 100, 28, 100, 29, 100, 30, 100, 31)
    assertEqM256i(r, e)
}

public fun testMm256MaskzExpandEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256MaskzExpandEpi8(0x55555555u, a)
    val e = mm256SetEpi8(0, 16, 0, 17, 0, 18, 0, 19, 0, 20, 0, 21, 0, 22, 0, 23, 0, 24, 0, 25, 0, 26, 0, 27, 0, 28, 0, 29, 0, 30, 0, 31)
    assertEqM256i(r, e)
}

public fun testMmMaskExpandEpi8() {
    val src = mmSet1Epi8(100)
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskExpandEpi8(src, 0x5555u.toUShort(), a)
    val e = mmSetEpi8(100, 8, 100, 9, 100, 10, 100, 11, 100, 12, 100, 13, 100, 14, 100, 15)
    assertEqM128i(r, e)
}

public fun testMmMaskzExpandEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzExpandEpi8(0x5555u.toUShort(), a)
    val e = mmSetEpi8(0, 8, 0, 9, 0, 10, 0, 11, 0, 12, 0, 13, 0, 14, 0, 15)
    assertEqM128i(r, e)
}

public fun testMm512ShldvEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(1L shl 63)
    val c = mm512Set1Epi64(2L)
    val r = mm512ShldvEpi64(a, b, c)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r, e)
}

public fun testMm512MaskShldvEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(1L shl 63)
    val c = mm512Set1Epi64(2L)
    val r = mm512MaskShldvEpi64(a, 0u.toUByte(), b, c)
    assertEqM512i(r, a)
    val r1 = mm512MaskShldvEpi64(a, 0xFFu.toUByte(), b, c)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShldvEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(1L shl 63)
    val c = mm512Set1Epi64(2L)
    val r = mm512MaskzShldvEpi64(0u.toUByte(), a, b, c)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShldvEpi64(0xFFu.toUByte(), a, b, c)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r1, e)
}

public fun testMm256ShldvEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(1L shl 63)
    val c = mm256Set1Epi64x(2L)
    val r = mm256ShldvEpi64(a, b, c)
    val e = mm256Set1Epi64x(6L)
    assertEqM256i(r, e)
}

public fun testMm256MaskShldvEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(1L shl 63)
    val c = mm256Set1Epi64x(2L)
    val r = mm256MaskShldvEpi64(a, 0u.toUByte(), b, c)
    assertEqM256i(r, a)
    val r1 = mm256MaskShldvEpi64(a, 0xFu.toUByte(), b, c)
    val e = mm256Set1Epi64x(6L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShldvEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(1L shl 63)
    val c = mm256Set1Epi64x(2L)
    val r = mm256MaskzShldvEpi64(0u.toUByte(), a, b, c)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShldvEpi64(0xFu.toUByte(), a, b, c)
    val e = mm256Set1Epi64x(6L)
    assertEqM256i(r1, e)
}

public fun testMmShldvEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(1L shl 63)
    val c = mmSet1Epi64x(2L)
    val r = mmShldvEpi64(a, b, c)
    val e = mmSet1Epi64x(6L)
    assertEqM128i(r, e)
}

public fun testMmMaskShldvEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(1L shl 63)
    val c = mmSet1Epi64x(2L)
    val r = mmMaskShldvEpi64(a, 0u.toUByte(), b, c)
    assertEqM128i(r, a)
    val r1 = mmMaskShldvEpi64(a, 0x3u.toUByte(), b, c)
    val e = mmSet1Epi64x(6L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShldvEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(1L shl 63)
    val c = mmSet1Epi64x(2L)
    val r = mmMaskzShldvEpi64(0u.toUByte(), a, b, c)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShldvEpi64(0x3u.toUByte(), a, b, c)
    val e = mmSet1Epi64x(6L)
    assertEqM128i(r1, e)
}

public fun testMm512ShldvEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(1 shl 31)
    val c = mm512Set1Epi32(2)
    val r = mm512ShldvEpi32(a, b, c)
    val e = mm512Set1Epi32(6)
    assertEqM512i(r, e)
}

public fun testMm512MaskShldvEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(1 shl 31)
    val c = mm512Set1Epi32(2)
    val r = mm512MaskShldvEpi32(a, 0u.toUShort(), b, c)
    assertEqM512i(r, a)
    val r1 = mm512MaskShldvEpi32(a, 0xFFFFu.toUShort(), b, c)
    val e = mm512Set1Epi32(6)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShldvEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(1 shl 31)
    val c = mm512Set1Epi32(2)
    val r = mm512MaskzShldvEpi32(0u.toUShort(), a, b, c)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShldvEpi32(0xFFFFu.toUShort(), a, b, c)
    val e = mm512Set1Epi32(6)
    assertEqM512i(r1, e)
}

public fun testMm256ShldvEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(1 shl 31)
    val c = mm256Set1Epi32(2)
    val r = mm256ShldvEpi32(a, b, c)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r, e)
}

public fun testMm256MaskShldvEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(1 shl 31)
    val c = mm256Set1Epi32(2)
    val r = mm256MaskShldvEpi32(a, 0u.toUByte(), b, c)
    assertEqM256i(r, a)
    val r1 = mm256MaskShldvEpi32(a, 0xFFu.toUByte(), b, c)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShldvEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(1 shl 31)
    val c = mm256Set1Epi32(2)
    val r = mm256MaskzShldvEpi32(0u.toUByte(), a, b, c)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShldvEpi32(0xFFu.toUByte(), a, b, c)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r1, e)
}

public fun testMmShldvEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(1 shl 31)
    val c = mmSet1Epi32(2)
    val r = mmShldvEpi32(a, b, c)
    val e = mmSet1Epi32(6)
    assertEqM128i(r, e)
}

public fun testMmMaskShldvEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(1 shl 31)
    val c = mmSet1Epi32(2)
    val r = mmMaskShldvEpi32(a, 0u.toUByte(), b, c)
    assertEqM128i(r, a)
    val r1 = mmMaskShldvEpi32(a, 0xFu.toUByte(), b, c)
    val e = mmSet1Epi32(6)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShldvEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(1 shl 31)
    val c = mmSet1Epi32(2)
    val r = mmMaskzShldvEpi32(0u.toUByte(), a, b, c)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShldvEpi32(0xFu.toUByte(), a, b, c)
    val e = mmSet1Epi32(6)
    assertEqM128i(r1, e)
}

public fun testMm512ShldvEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val c = mm512Set1Epi16(2)
    val r = mm512ShldvEpi16(a, b, c)
    val e = mm512Set1Epi16(6)
    assertEqM512i(r, e)
}

public fun testMm512MaskShldvEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val c = mm512Set1Epi16(2)
    val r = mm512MaskShldvEpi16(a, 0u, b, c)
    assertEqM512i(r, a)
    val r1 = mm512MaskShldvEpi16(a, 0xFFFFFFFFu, b, c)
    val e = mm512Set1Epi16(6)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShldvEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val c = mm512Set1Epi16(2)
    val r = mm512MaskzShldvEpi16(0u, a, b, c)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShldvEpi16(0xFFFFFFFFu, a, b, c)
    val e = mm512Set1Epi16(6)
    assertEqM512i(r1, e)
}

public fun testMm256ShldvEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val c = mm256Set1Epi16(2)
    val r = mm256ShldvEpi16(a, b, c)
    val e = mm256Set1Epi16(6)
    assertEqM256i(r, e)
}

public fun testMm256MaskShldvEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val c = mm256Set1Epi16(2)
    val r = mm256MaskShldvEpi16(a, 0u.toUShort(), b, c)
    assertEqM256i(r, a)
    val r1 = mm256MaskShldvEpi16(a, 0xFFFFu.toUShort(), b, c)
    val e = mm256Set1Epi16(6)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShldvEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val c = mm256Set1Epi16(2)
    val r = mm256MaskzShldvEpi16(0u.toUShort(), a, b, c)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShldvEpi16(0xFFFFu.toUShort(), a, b, c)
    val e = mm256Set1Epi16(6)
    assertEqM256i(r1, e)
}

public fun testMmShldvEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((1.toInt() shl 15).toShort())
    val c = mmSet1Epi16(2)
    val r = mmShldvEpi16(a, b, c)
    val e = mmSet1Epi16(6)
    assertEqM128i(r, e)
}

public fun testMmMaskShldvEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((1.toInt() shl 15).toShort())
    val c = mmSet1Epi16(2)
    val r = mmMaskShldvEpi16(a, 0u.toUByte(), b, c)
    assertEqM128i(r, a)
    val r1 = mmMaskShldvEpi16(a, 0xFFu.toUByte(), b, c)
    val e = mmSet1Epi16(6)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShldvEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((1.toInt() shl 15).toShort())
    val c = mmSet1Epi16(2)
    val r = mmMaskzShldvEpi16(0u.toUByte(), a, b, c)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShldvEpi16(0xFFu.toUByte(), a, b, c)
    val e = mmSet1Epi16(6)
    assertEqM128i(r1, e)
}

public fun testMm512ShrdvEpi64() {
    val a = mm512Set1Epi64(2L)
    val b = mm512Set1Epi64(8L)
    val c = mm512Set1Epi64(1L)
    val r = mm512ShrdvEpi64(a, b, c)
    val e = mm512Set1Epi64(1L)
    assertEqM512i(r, e)
}

public fun testMm512MaskShrdvEpi64() {
    val a = mm512Set1Epi64(2L)
    val b = mm512Set1Epi64(8L)
    val c = mm512Set1Epi64(1L)
    val r = mm512MaskShrdvEpi64(a, 0u.toUByte(), b, c)
    assertEqM512i(r, a)
    val r1 = mm512MaskShrdvEpi64(a, 0xFFu.toUByte(), b, c)
    val e = mm512Set1Epi64(1L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShrdvEpi64() {
    val a = mm512Set1Epi64(2L)
    val b = mm512Set1Epi64(8L)
    val c = mm512Set1Epi64(1L)
    val r = mm512MaskzShrdvEpi64(0u.toUByte(), a, b, c)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShrdvEpi64(0xFFu.toUByte(), a, b, c)
    val e = mm512Set1Epi64(1L)
    assertEqM512i(r1, e)
}

public fun testMm256ShrdvEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(8L)
    val c = mm256Set1Epi64x(1L)
    val r = mm256ShrdvEpi64(a, b, c)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r, e)
}

public fun testMm256MaskShrdvEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(8L)
    val c = mm256Set1Epi64x(1L)
    val r = mm256MaskShrdvEpi64(a, 0u.toUByte(), b, c)
    assertEqM256i(r, a)
    val r1 = mm256MaskShrdvEpi64(a, 0xFu.toUByte(), b, c)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShrdvEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(8L)
    val c = mm256Set1Epi64x(1L)
    val r = mm256MaskzShrdvEpi64(0u.toUByte(), a, b, c)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShrdvEpi64(0xFu.toUByte(), a, b, c)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r1, e)
}

public fun testMmShrdvEpi64() {
    val a = mmSet1Epi64x(2L)
    val b = mmSet1Epi64x(8L)
    val c = mmSet1Epi64x(1L)
    val r = mmShrdvEpi64(a, b, c)
    val e = mmSet1Epi64x(1L)
    assertEqM128i(r, e)
}

public fun testMmMaskShrdvEpi64() {
    val a = mmSet1Epi64x(2L)
    val b = mmSet1Epi64x(8L)
    val c = mmSet1Epi64x(1L)
    val r = mmMaskShrdvEpi64(a, 0u.toUByte(), b, c)
    assertEqM128i(r, a)
    val r1 = mmMaskShrdvEpi64(a, 0x3u.toUByte(), b, c)
    val e = mmSet1Epi64x(1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShrdvEpi64() {
    val a = mmSet1Epi64x(2L)
    val b = mmSet1Epi64x(8L)
    val c = mmSet1Epi64x(1L)
    val r = mmMaskzShrdvEpi64(0u.toUByte(), a, b, c)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShrdvEpi64(0x3u.toUByte(), a, b, c)
    val e = mmSet1Epi64x(1L)
    assertEqM128i(r1, e)
}

public fun testMm512ShrdvEpi32() {
    val a = mm512Set1Epi32(2)
    val b = mm512Set1Epi32(8)
    val c = mm512Set1Epi32(1)
    val r = mm512ShrdvEpi32(a, b, c)
    val e = mm512Set1Epi32(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskShrdvEpi32() {
    val a = mm512Set1Epi32(2)
    val b = mm512Set1Epi32(8)
    val c = mm512Set1Epi32(1)
    val r = mm512MaskShrdvEpi32(a, 0u.toUShort(), b, c)
    assertEqM512i(r, a)
    val r1 = mm512MaskShrdvEpi32(a, 0xFFFFu.toUShort(), b, c)
    val e = mm512Set1Epi32(1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShrdvEpi32() {
    val a = mm512Set1Epi32(2)
    val b = mm512Set1Epi32(8)
    val c = mm512Set1Epi32(1)
    val r = mm512MaskzShrdvEpi32(0u.toUShort(), a, b, c)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShrdvEpi32(0xFFFFu.toUShort(), a, b, c)
    val e = mm512Set1Epi32(1)
    assertEqM512i(r1, e)
}

public fun testMm256ShrdvEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(8)
    val c = mm256Set1Epi32(1)
    val r = mm256ShrdvEpi32(a, b, c)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r, e)
}

public fun testMm256MaskShrdvEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(8)
    val c = mm256Set1Epi32(1)
    val r = mm256MaskShrdvEpi32(a, 0u.toUByte(), b, c)
    assertEqM256i(r, a)
    val r1 = mm256MaskShrdvEpi32(a, 0xFFu.toUByte(), b, c)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShrdvEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(8)
    val c = mm256Set1Epi32(1)
    val r = mm256MaskzShrdvEpi32(0u.toUByte(), a, b, c)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShrdvEpi32(0xFFu.toUByte(), a, b, c)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r1, e)
}

public fun testMmShrdvEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(8)
    val c = mmSet1Epi32(1)
    val r = mmShrdvEpi32(a, b, c)
    val e = mmSet1Epi32(1)
    assertEqM128i(r, e)
}

public fun testMmMaskShrdvEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(8)
    val c = mmSet1Epi32(1)
    val r = mmMaskShrdvEpi32(a, 0u.toUByte(), b, c)
    assertEqM128i(r, a)
    val r1 = mmMaskShrdvEpi32(a, 0xFu.toUByte(), b, c)
    val e = mmSet1Epi32(1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShrdvEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(8)
    val c = mmSet1Epi32(1)
    val r = mmMaskzShrdvEpi32(0u.toUByte(), a, b, c)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShrdvEpi32(0xFu.toUByte(), a, b, c)
    val e = mmSet1Epi32(1)
    assertEqM128i(r1, e)
}

public fun testMm512ShrdvEpi16() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(8)
    val c = mm512Set1Epi16(1)
    val r = mm512ShrdvEpi16(a, b, c)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskShrdvEpi16() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(8)
    val c = mm512Set1Epi16(1)
    val r = mm512MaskShrdvEpi16(a, 0u, b, c)
    assertEqM512i(r, a)
    val r1 = mm512MaskShrdvEpi16(a, 0xFFFFFFFFu, b, c)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShrdvEpi16() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(8)
    val c = mm512Set1Epi16(1)
    val r = mm512MaskzShrdvEpi16(0u, a, b, c)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShrdvEpi16(0xFFFFFFFFu, a, b, c)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r1, e)
}

public fun testMm256ShrdvEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(8)
    val c = mm256Set1Epi16(1)
    val r = mm256ShrdvEpi16(a, b, c)
    val e = mm256Set1Epi16(1)
    assertEqM256i(r, e)
}

public fun testMm256MaskShrdvEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(8)
    val c = mm256Set1Epi16(1)
    val r = mm256MaskShrdvEpi16(a, 0u.toUShort(), b, c)
    assertEqM256i(r, a)
    val r1 = mm256MaskShrdvEpi16(a, 0xFFFFu.toUShort(), b, c)
    val e = mm256Set1Epi16(1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShrdvEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(8)
    val c = mm256Set1Epi16(1)
    val r = mm256MaskzShrdvEpi16(0u.toUShort(), a, b, c)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShrdvEpi16(0xFFFFu.toUShort(), a, b, c)
    val e = mm256Set1Epi16(1)
    assertEqM256i(r1, e)
}

public fun testMmShrdvEpi16() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(8)
    val c = mmSet1Epi16(1)
    val r = mmShrdvEpi16(a, b, c)
    val e = mmSet1Epi16(1)
    assertEqM128i(r, e)
}

public fun testMmMaskShrdvEpi16() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(8)
    val c = mmSet1Epi16(1)
    val r = mmMaskShrdvEpi16(a, 0u.toUByte(), b, c)
    assertEqM128i(r, a)
    val r1 = mmMaskShrdvEpi16(a, 0xFFu.toUByte(), b, c)
    val e = mmSet1Epi16(1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShrdvEpi16() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(8)
    val c = mmSet1Epi16(1)
    val r = mmMaskzShrdvEpi16(0u.toUByte(), a, b, c)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShrdvEpi16(0xFFu.toUByte(), a, b, c)
    val e = mmSet1Epi16(1)
    assertEqM128i(r1, e)
}

public fun testMm512ShldiEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(1L shl 63)
    val r = mm512ShldiEpi64(a, b, 2)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r, e)
}

public fun testMm512MaskShldiEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(1L shl 63)
    val r = mm512MaskShldiEpi64(a, 0u.toUByte(), a, b, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskShldiEpi64(a, 0xFFu.toUByte(), a, b, 2)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShldiEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(1L shl 63)
    val r = mm512MaskzShldiEpi64(0u.toUByte(), a, b, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShldiEpi64(0xFFu.toUByte(), a, b, 2)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r1, e)
}

public fun testMm256ShldiEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(1L shl 63)
    val r = mm256ShldiEpi64(a, b, 2)
    val e = mm256Set1Epi64x(6L)
    assertEqM256i(r, e)
}

public fun testMm256MaskShldiEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(1L shl 63)
    val r = mm256MaskShldiEpi64(a, 0u.toUByte(), a, b, 2)
    assertEqM256i(r, a)
    val r1 = mm256MaskShldiEpi64(a, 0xFu.toUByte(), a, b, 2)
    val e = mm256Set1Epi64x(6L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShldiEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(1L shl 63)
    val r = mm256MaskzShldiEpi64(0u.toUByte(), a, b, 2)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShldiEpi64(0xFu.toUByte(), a, b, 2)
    val e = mm256Set1Epi64x(6L)
    assertEqM256i(r1, e)
}

public fun testMmShldiEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(1L shl 63)
    val r = mmShldiEpi64(a, b, 2)
    val e = mmSet1Epi64x(6L)
    assertEqM128i(r, e)
}

public fun testMmMaskShldiEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(1L shl 63)
    val r = mmMaskShldiEpi64(a, 0u.toUByte(), a, b, 2)
    assertEqM128i(r, a)
    val r1 = mmMaskShldiEpi64(a, 0x3u.toUByte(), a, b, 2)
    val e = mmSet1Epi64x(6L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShldiEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(1L shl 63)
    val r = mmMaskzShldiEpi64(0u.toUByte(), a, b, 2)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShldiEpi64(0x3u.toUByte(), a, b, 2)
    val e = mmSet1Epi64x(6L)
    assertEqM128i(r1, e)
}

public fun testMm512ShldiEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(1 shl 31)
    val r = mm512ShldiEpi32(a, b, 2)
    val e = mm512Set1Epi32(6)
    assertEqM512i(r, e)
}

public fun testMm512MaskShldiEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(1 shl 31)
    val r = mm512MaskShldiEpi32(a, 0u.toUShort(), a, b, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskShldiEpi32(a, 0xFFFFu.toUShort(), a, b, 2)
    val e = mm512Set1Epi32(6)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShldiEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512Set1Epi32(1 shl 31)
    val r = mm512MaskzShldiEpi32(0u.toUShort(), a, b, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShldiEpi32(0xFFFFu.toUShort(), a, b, 2)
    val e = mm512Set1Epi32(6)
    assertEqM512i(r1, e)
}

public fun testMm256ShldiEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(1 shl 31)
    val r = mm256ShldiEpi32(a, b, 2)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r, e)
}

public fun testMm256MaskShldiEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(1 shl 31)
    val r = mm256MaskShldiEpi32(a, 0u.toUByte(), a, b, 2)
    assertEqM256i(r, a)
    val r1 = mm256MaskShldiEpi32(a, 0xFFu.toUByte(), a, b, 2)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShldiEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256Set1Epi32(1 shl 31)
    val r = mm256MaskzShldiEpi32(0u.toUByte(), a, b, 2)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShldiEpi32(0xFFu.toUByte(), a, b, 2)
    val e = mm256Set1Epi32(6)
    assertEqM256i(r1, e)
}

public fun testMmShldiEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(1 shl 31)
    val r = mmShldiEpi32(a, b, 2)
    val e = mmSet1Epi32(6)
    assertEqM128i(r, e)
}

public fun testMmMaskShldiEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(1 shl 31)
    val r = mmMaskShldiEpi32(a, 0u.toUByte(), a, b, 2)
    assertEqM128i(r, a)
    val r1 = mmMaskShldiEpi32(a, 0xFu.toUByte(), a, b, 2)
    val e = mmSet1Epi32(6)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShldiEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSet1Epi32(1 shl 31)
    val r = mmMaskzShldiEpi32(0u.toUByte(), a, b, 2)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShldiEpi32(0xFu.toUByte(), a, b, 2)
    val e = mmSet1Epi32(6)
    assertEqM128i(r1, e)
}

public fun testMm512ShldiEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val r = mm512ShldiEpi16(a, b, 2)
    val e = mm512Set1Epi16(6)
    assertEqM512i(r, e)
}

public fun testMm512MaskShldiEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val r = mm512MaskShldiEpi16(a, 0u, a, b, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskShldiEpi16(a, 0xFFFFFFFFu, a, b, 2)
    val e = mm512Set1Epi16(6)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShldiEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val r = mm512MaskzShldiEpi16(0u, a, b, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShldiEpi16(0xFFFFFFFFu, a, b, 2)
    val e = mm512Set1Epi16(6)
    assertEqM512i(r1, e)
}

public fun testMm256ShldiEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val r = mm256ShldiEpi16(a, b, 2)
    val e = mm256Set1Epi16(6)
    assertEqM256i(r, e)
}

public fun testMm256MaskShldiEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val r = mm256MaskShldiEpi16(a, 0u.toUShort(), a, b, 2)
    assertEqM256i(r, a)
    val r1 = mm256MaskShldiEpi16(a, 0xFFFFu.toUShort(), a, b, 2)
    val e = mm256Set1Epi16(6)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShldiEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val r = mm256MaskzShldiEpi16(0u.toUShort(), a, b, 2)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShldiEpi16(0xFFFFu.toUShort(), a, b, 2)
    val e = mm256Set1Epi16(6)
    assertEqM256i(r1, e)
}

public fun testMmShldiEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((1.toInt() shl 15).toShort())
    val r = mmShldiEpi16(a, b, 2)
    val e = mmSet1Epi16(6)
    assertEqM128i(r, e)
}

public fun testMmMaskShldiEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((1.toInt() shl 15).toShort())
    val r = mmMaskShldiEpi16(a, 0u.toUByte(), a, b, 2)
    assertEqM128i(r, a)
    val r1 = mmMaskShldiEpi16(a, 0xFFu.toUByte(), a, b, 2)
    val e = mmSet1Epi16(6)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShldiEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((1.toInt() shl 15).toShort())
    val r = mmMaskzShldiEpi16(0u.toUByte(), a, b, 2)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShldiEpi16(0xFFu.toUByte(), a, b, 2)
    val e = mmSet1Epi16(6)
    assertEqM128i(r1, e)
}

public fun testMm512ShrdiEpi64() {
    val a = mm512Set1Epi64(2L)
    val b = mm512Set1Epi64(8L)
    val r = mm512ShrdiEpi64(a, b, 1)
    val e = mm512Set1Epi64(1L)
    assertEqM512i(r, e)
}

public fun testMm512MaskShrdiEpi64() {
    val a = mm512Set1Epi64(2L)
    val b = mm512Set1Epi64(8L)
    val r = mm512MaskShrdiEpi64(a, 0u.toUByte(), a, b, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskShrdiEpi64(a, 0xFFu.toUByte(), a, b, 1)
    val e = mm512Set1Epi64(1L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShrdiEpi64() {
    val a = mm512Set1Epi64(2L)
    val b = mm512Set1Epi64(8L)
    val r = mm512MaskzShrdiEpi64(0u.toUByte(), a, b, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShrdiEpi64(0xFFu.toUByte(), a, b, 1)
    val e = mm512Set1Epi64(1L)
    assertEqM512i(r1, e)
}

public fun testMm256ShrdiEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(8L)
    val r = mm256ShrdiEpi64(a, b, 1)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r, e)
}

public fun testMm256MaskShrdiEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(8L)
    val r = mm256MaskShrdiEpi64(a, 0u.toUByte(), a, b, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskShrdiEpi64(a, 0xFu.toUByte(), a, b, 1)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShrdiEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(8L)
    val r = mm256MaskzShrdiEpi64(0u.toUByte(), a, b, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShrdiEpi64(0xFu.toUByte(), a, b, 1)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r1, e)
}

public fun testMmShrdiEpi64() {
    val a = mmSet1Epi64x(2L)
    val b = mmSet1Epi64x(8L)
    val r = mmShrdiEpi64(a, b, 1)
    val e = mmSet1Epi64x(1L)
    assertEqM128i(r, e)
}

public fun testMmMaskShrdiEpi64() {
    val a = mmSet1Epi64x(2L)
    val b = mmSet1Epi64x(8L)
    val r = mmMaskShrdiEpi64(a, 0u.toUByte(), a, b, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskShrdiEpi64(a, 0x3u.toUByte(), a, b, 1)
    val e = mmSet1Epi64x(1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShrdiEpi64() {
    val a = mmSet1Epi64x(2L)
    val b = mmSet1Epi64x(8L)
    val r = mmMaskzShrdiEpi64(0u.toUByte(), a, b, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShrdiEpi64(0x3u.toUByte(), a, b, 1)
    val e = mmSet1Epi64x(1L)
    assertEqM128i(r1, e)
}

public fun testMm512ShrdiEpi32() {
    val a = mm512Set1Epi32(2)
    val b = mm512Set1Epi32(8)
    val r = mm512ShrdiEpi32(a, b, 1)
    val e = mm512Set1Epi32(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskShrdiEpi32() {
    val a = mm512Set1Epi32(2)
    val b = mm512Set1Epi32(8)
    val r = mm512MaskShrdiEpi32(a, 0u.toUShort(), a, b, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskShrdiEpi32(a, 0xFFFFu.toUShort(), a, b, 1)
    val e = mm512Set1Epi32(1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShrdiEpi32() {
    val a = mm512Set1Epi32(2)
    val b = mm512Set1Epi32(8)
    val r = mm512MaskzShrdiEpi32(0u.toUShort(), a, b, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShrdiEpi32(0xFFFFu.toUShort(), a, b, 1)
    val e = mm512Set1Epi32(1)
    assertEqM512i(r1, e)
}

public fun testMm256ShrdiEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(8)
    val r = mm256ShrdiEpi32(a, b, 1)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r, e)
}

public fun testMm256MaskShrdiEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(8)
    val r = mm256MaskShrdiEpi32(a, 0u.toUByte(), a, b, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskShrdiEpi32(a, 0xFFu.toUByte(), a, b, 1)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShrdiEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(8)
    val r = mm256MaskzShrdiEpi32(0u.toUByte(), a, b, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShrdiEpi32(0xFFu.toUByte(), a, b, 1)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r1, e)
}

public fun testMmShrdiEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(8)
    val r = mmShrdiEpi32(a, b, 1)
    val e = mmSet1Epi32(1)
    assertEqM128i(r, e)
}

public fun testMmMaskShrdiEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(8)
    val r = mmMaskShrdiEpi32(a, 0u.toUByte(), a, b, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskShrdiEpi32(a, 0xFu.toUByte(), a, b, 1)
    val e = mmSet1Epi32(1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShrdiEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(8)
    val r = mmMaskzShrdiEpi32(0u.toUByte(), a, b, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShrdiEpi32(0xFu.toUByte(), a, b, 1)
    val e = mmSet1Epi32(1)
    assertEqM128i(r1, e)
}

public fun testMm512ShrdiEpi16() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(8)
    val r = mm512ShrdiEpi16(a, b, 1)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskShrdiEpi16() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(8)
    val r = mm512MaskShrdiEpi16(a, 0u, a, b, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskShrdiEpi16(a, 0xFFFFFFFFu, a, b, 1)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShrdiEpi16() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(8)
    val r = mm512MaskzShrdiEpi16(0u, a, b, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShrdiEpi16(0xFFFFFFFFu, a, b, 1)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r1, e)
}

public fun testMm256ShrdiEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(8)
    val r = mm256ShrdiEpi16(a, b, 1)
    val e = mm256Set1Epi16(1)
    assertEqM256i(r, e)
}

public fun testMm256MaskShrdiEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(8)
    val r = mm256MaskShrdiEpi16(a, 0u.toUShort(), a, b, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskShrdiEpi16(a, 0xFFFFu.toUShort(), a, b, 1)
    val e = mm256Set1Epi16(1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShrdiEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(8)
    val r = mm256MaskzShrdiEpi16(0u.toUShort(), a, b, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShrdiEpi16(0xFFFFu.toUShort(), a, b, 1)
    val e = mm256Set1Epi16(1)
    assertEqM256i(r1, e)
}

public fun testMmShrdiEpi16() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(8)
    val r = mmShrdiEpi16(a, b, 1)
    val e = mmSet1Epi16(1)
    assertEqM128i(r, e)
}

public fun testMmMaskShrdiEpi16() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(8)
    val r = mmMaskShrdiEpi16(a, 0u.toUByte(), a, b, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskShrdiEpi16(a, 0xFFu.toUByte(), a, b, 1)
    val e = mmSet1Epi16(1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShrdiEpi16() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(8)
    val r = mmMaskzShrdiEpi16(0u.toUByte(), a, b, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShrdiEpi16(0xFFu.toUByte(), a, b, 1)
    val e = mmSet1Epi16(1)
    assertEqM128i(r1, e)
}

public fun testMm512MaskExpandloaduEpi16() {
    val src = mm512Set1Epi16(42)
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xE8CAF00F
    val r = mm512MaskExpandloaduEpi16(src, m, blackBox(p))
    val e = mm512SetEpi16(16, 15, 14, 42, 13, 42, 42, 42, 12, 11, 42, 42, 10, 42, 9, 42, 8, 7, 6, 5, 42, 42, 42, 42, 42, 42, 42, 42, 4, 3, 2, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskzExpandloaduEpi16() {
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xE8CAF00F
    val r = mm512MaskzExpandloaduEpi16(m, blackBox(p))
    val e = mm512SetEpi16(16, 15, 14, 0, 13, 0, 0, 0, 12, 11, 0, 0, 10, 0, 9, 0, 8, 7, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 4, 3, 2, 1)
    assertEqM512i(r, e)
}

public fun testMm256MaskExpandloaduEpi16() {
    val src = mm256Set1Epi16(42)
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm256MaskExpandloaduEpi16(src, m, blackBox(p))
    val e = mm256SetEpi16(8, 7, 6, 42, 5, 42, 42, 42, 4, 3, 42, 42, 2, 42, 1, 42)
    assertEqM256i(r, e)
}

public fun testMm256MaskzExpandloaduEpi16() {
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm256MaskzExpandloaduEpi16(m, blackBox(p))
    val e = mm256SetEpi16(8, 7, 6, 0, 5, 0, 0, 0, 4, 3, 0, 0, 2, 0, 1, 0)
    assertEqM256i(r, e)
}

public fun testMmMaskExpandloaduEpi16() {
    val src = mmSet1Epi16(42)
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskExpandloaduEpi16(src, m, blackBox(p))
    val e = mmSetEpi16(4, 3, 2, 42, 1, 42, 42, 42)
    assertEqM128i(r, e)
}

public fun testMmMaskzExpandloaduEpi16() {
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xE8
    val r = mmMaskzExpandloaduEpi16(m, blackBox(p))
    val e = mmSetEpi16(4, 3, 2, 0, 1, 0, 0, 0)
    assertEqM128i(r, e)
}

public fun testMm512MaskExpandloaduEpi8() {
    val src = mm512Set1Epi8(42)
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val p = a.asPtr()
    val m = 0xE8CAF00FFF00AA55uL
    val r = mm512MaskExpandloaduEpi8(src, m, blackBox(p))
    val e = mm512SetEpi8(32, 31, 30, 42, 29, 42, 42, 42, 28, 27, 42, 42, 26, 42, 25, 42, 24, 23, 22, 21, 42, 42, 42, 42, 42, 42, 42, 42, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 42, 42, 42, 42, 42, 42, 42, 42, 8, 42, 7, 42, 6, 42, 5, 42, 42, 4, 42, 3, 42, 2, 42, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskzExpandloaduEpi8() {
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val p = a.asPtr()
    val m = 0xE8CAF00FFF00AA55uL
    val r = mm512MaskzExpandloaduEpi8(m, blackBox(p))
    val e = mm512SetEpi8(32, 31, 30, 0, 29, 0, 0, 0, 28, 27, 0, 0, 26, 0, 25, 0, 24, 23, 22, 21, 0, 0, 0, 0, 0, 0, 0, 0, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 7, 0, 6, 0, 5, 0, 0, 4, 0, 3, 0, 2, 0, 1)
    assertEqM512i(r, e)
}

public fun testMm256MaskExpandloaduEpi8() {
    val src = mm256Set1Epi8(42)
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xE8CAF00F
    val r = mm256MaskExpandloaduEpi8(src, m, blackBox(p))
    val e = mm256SetEpi8(16, 15, 14, 42, 13, 42, 42, 42, 12, 11, 42, 42, 10, 42, 9, 42, 8, 7, 6, 5, 42, 42, 42, 42, 42, 42, 42, 42, 4, 3, 2, 1)
    assertEqM256i(r, e)
}

public fun testMm256MaskzExpandloaduEpi8() {
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xE8CAF00F
    val r = mm256MaskzExpandloaduEpi8(m, blackBox(p))
    val e = mm256SetEpi8(16, 15, 14, 0, 13, 0, 0, 0, 12, 11, 0, 0, 10, 0, 9, 0, 8, 7, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 4, 3, 2, 1)
    assertEqM256i(r, e)
}

public fun testMmMaskExpandloaduEpi8() {
    val src = mmSet1Epi8(42)
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mmMaskExpandloaduEpi8(src, m, blackBox(p))
    val e = mmSetEpi8(8, 7, 6, 42, 5, 42, 42, 42, 4, 3, 42, 42, 2, 42, 1, 42)
    assertEqM128i(r, e)
}

public fun testMmMaskzExpandloaduEpi8() {
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mmMaskzExpandloaduEpi8(m, blackBox(p))
    val e = mmSetEpi8(8, 7, 6, 0, 5, 0, 0, 0, 4, 3, 0, 0, 2, 0, 1, 0)
    assertEqM128i(r, e)
}

public fun testMm512MaskCompressstoreuEpi16() {
    val a = mm512SetEpi16(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val localStorageL4106C8 = AddressableArrayLocal.fromShortArray(ShortArray(32) { 0 })
    var r by localStorageL4106C8
    run {
        mm512MaskCompressstoreuEpi16(localStorageL4106C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(ShortArray(32) { 0 }))
    run {
        mm512MaskCompressstoreuEpi16(localStorageL4106C8.elementMutPtr(0), 0xF0CAFF00u, a)
    }
    check(r == listOf(9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 23, 24, 29, 30, 31, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
}

public fun testMm256MaskCompressstoreuEpi16() {
    val a = mm256SetEpi16(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val localStorageL4130C8 = AddressableArrayLocal.fromShortArray(ShortArray(16) { 0 })
    var r by localStorageL4130C8
    run {
        mm256MaskCompressstoreuEpi16(localStorageL4130C8.elementMutPtr(0), 0u.toUShort(), a)
    }
    check((r).contentEquals(ShortArray(16) { 0 }))
    run {
        mm256MaskCompressstoreuEpi16(localStorageL4130C8.elementMutPtr(0), 0xF0CAu.toUShort(), a)
    }
    check(r == listOf(2, 4, 7, 8, 13, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0))
}

public fun testMmMaskCompressstoreuEpi16() {
    val a = mmSetEpi16(8, 7, 6, 5, 4, 3, 2, 1)
    val localStorageL4144C8 = AddressableArrayLocal.fromShortArray(ShortArray(8) { 0 })
    var r by localStorageL4144C8
    run {
        mmMaskCompressstoreuEpi16(localStorageL4144C8.elementMutPtr(0), 0u.toUByte(), a)
    }
    check((r).contentEquals(ShortArray(8) { 0 }))
    run {
        mmMaskCompressstoreuEpi16(localStorageL4144C8.elementMutPtr(0), 0xF0u.toUByte(), a)
    }
    check(r == listOf(5, 6, 7, 8, 0, 0, 0, 0))
}

public fun testMm512MaskCompressstoreuEpi8() {
    val a = mm512SetEpi8(64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val localStorageL4162C8 = AddressableArrayLocal.fromByteArray(ByteArray(64) { 0 })
    var r by localStorageL4162C8
    run {
        mm512MaskCompressstoreuEpi8(localStorageL4162C8.elementMutPtr(0), 0uL, a)
    }
    check((r).contentEquals(ByteArray(64) { 0 }))
    run {
        mm512MaskCompressstoreuEpi8(localStorageL4162C8.elementMutPtr(0), 0xF0CAFF00AA55F00FuL, a)
    }
    check(r == listOf(1, 2, 3, 4, 13, 14, 15, 16, 17, 19, 21, 23, 26, 28, 30, 32, 41, 42, 43, 44, 45, 46, 47, 48, 50, 52, 55, 56, 61, 62, 63, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
}

public fun testMm256MaskCompressstoreuEpi8() {
    val a = mm256SetEpi8(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val localStorageL4190C8 = AddressableArrayLocal.fromByteArray(ByteArray(32) { 0 })
    var r by localStorageL4190C8
    run {
        mm256MaskCompressstoreuEpi8(localStorageL4190C8.elementMutPtr(0), 0u, a)
    }
    check((r).contentEquals(ByteArray(32) { 0 }))
    run {
        mm256MaskCompressstoreuEpi8(localStorageL4190C8.elementMutPtr(0), 0xF0CAFF00u, a)
    }
    check(r == listOf(9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 23, 24, 29, 30, 31, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
}

public fun testMmMaskCompressstoreuEpi8() {
    val a = mmSetEpi8(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val localStorageL4214C8 = AddressableArrayLocal.fromByteArray(ByteArray(16) { 0 })
    var r by localStorageL4214C8
    run {
        mmMaskCompressstoreuEpi8(localStorageL4214C8.elementMutPtr(0), 0u.toUShort(), a)
    }
    check((r).contentEquals(ByteArray(16) { 0 }))
    run {
        mmMaskCompressstoreuEpi8(localStorageL4214C8.elementMutPtr(0), 0xF0CAu.toUShort(), a)
    }
    check(r == listOf(2, 4, 7, 8, 13, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0))
}
