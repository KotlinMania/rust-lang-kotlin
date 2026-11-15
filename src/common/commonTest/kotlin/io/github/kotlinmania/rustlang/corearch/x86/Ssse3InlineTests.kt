// port-lint: tests x86/ssse3.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.mmAddEpi8
import io.github.kotlinmania.rustlang.x86.mmSet1Epi16
import io.github.kotlinmania.rustlang.x86.mmSet1Epi32
import io.github.kotlinmania.rustlang.x86.mmSet1Epi8
import io.github.kotlinmania.rustlang.x86.mmSetrEpi16
import io.github.kotlinmania.rustlang.x86.mmSetrEpi32
import io.github.kotlinmania.rustlang.x86.mmSetrEpi8

// Supplemental Streaming SIMD Extensions 3 (SSSE3)

public fun testMmAbsEpi8() {
    val r = mmAbsEpi8(mmSet1Epi8((-5).toByte()))
    assertEqM128i(r, mmSet1Epi8(5))
}

public fun testMmAbsEpi16() {
    val r = mmAbsEpi16(mmSet1Epi16((-5).toShort()))
    assertEqM128i(r, mmSet1Epi16(5))
}

public fun testMmAbsEpi32() {
    val r = mmAbsEpi32(mmSet1Epi32(-5))
    assertEqM128i(r, mmSet1Epi32(5))
}

public fun testMmShuffleEpi8() {
    val a = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetrEpi8(4, (128u.toUByte()).toByte(), 4, 3, 24, 12, 6, 19, 12, 5, 5, 10, 4, 1, 8, 0)
    val expected = mmSetrEpi8(5, 0, 5, 4, 9, 13, 7, 4, 13, 6, 6, 11, 5, 2, 9, 1)
    val r = mmShuffleEpi8(a, b)
    assertEqM128i(r, expected)
    // Test indices greater than 15 wrapping around
    val b1 = mmAddEpi8(b, mmSet1Epi8(32))
    val r1 = mmShuffleEpi8(a, b1)
    assertEqM128i(r1, expected)
}

public fun testMmAlignrEpi8() {
    val a = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetrEpi8(4, 63, 4, 3, 24, 12, 6, 19, 12, 5, 5, 10, 4, 1, 8, 0)
    val r = mmAlignrEpi8(a, b, 33)
    assertEqM128i(r, mmSet1Epi8(0))
    val r1 = mmAlignrEpi8(a, b, 17)
    val expected = mmSetrEpi8(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0)
    assertEqM128i(r1, expected)
    val r2 = mmAlignrEpi8(a, b, 16)
    assertEqM128i(r2, a)
    val r3 = mmAlignrEpi8(a, b, 15)
    val expected1 = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r3, expected1)
    val r4 = mmAlignrEpi8(a, b, 0)
    assertEqM128i(r4, b)
}

public fun testMmHaddEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetrEpi16(4, 128, 4, 3, 24, 12, 6, 19)
    val expected = mmSetrEpi16(3, 7, 11, 15, 132, 7, 36, 25)
    val r = mmHaddEpi16(a, b)
    assertEqM128i(r, expected)
    // Test wrapping on overflow
    val a1 = mmSetrEpi16(Short.MAX_VALUE, 1, Short.MAX_VALUE, 2, Short.MAX_VALUE, 3, Short.MAX_VALUE, 4)
    val b1 = mmSetrEpi16(Short.MIN_VALUE, (-1).toShort(), Short.MIN_VALUE, (-2).toShort(), Short.MIN_VALUE, (-3).toShort(), Short.MIN_VALUE, (-4).toShort())
    val expected1 = mmSetrEpi16(Short.MIN_VALUE, (Short.MIN_VALUE + 1).toShort(), (Short.MIN_VALUE + 2).toShort(), (Short.MIN_VALUE + 3).toShort(), Short.MAX_VALUE, (Short.MAX_VALUE - 1).toShort(), (Short.MAX_VALUE - 2).toShort(), (Short.MAX_VALUE - 3).toShort())
    val r1 = mmHaddEpi16(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmHaddsEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetrEpi16(4, 128, 4, 3, 32767, 1, (-32768).toShort(), (-1).toShort())
    val expected = mmSetrEpi16(3, 7, 11, 15, 132, 7, 32767, (-32768).toShort())
    val r = mmHaddsEpi16(a, b)
    assertEqM128i(r, expected)
    // Test saturating on overflow
    val a1 = mmSetrEpi16(Short.MAX_VALUE, 1, Short.MAX_VALUE, 2, Short.MAX_VALUE, 3, Short.MAX_VALUE, 4)
    val b1 = mmSetrEpi16(Short.MIN_VALUE, (-1).toShort(), Short.MIN_VALUE, (-2).toShort(), Short.MIN_VALUE, (-3).toShort(), Short.MIN_VALUE, (-4).toShort())
    val expected1 = mmSetrEpi16(Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    val r1 = mmHaddsEpi16(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmHaddEpi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val b = mmSetrEpi32(4, 128, 4, 3)
    val expected = mmSetrEpi32(3, 7, 132, 7)
    val r = mmHaddEpi32(a, b)
    assertEqM128i(r, expected)
    // Test wrapping on overflow
    val a1 = mmSetrEpi32(Int.MAX_VALUE, 1, Int.MAX_VALUE, 2)
    val b1 = mmSetrEpi32(Int.MIN_VALUE, -1, Int.MIN_VALUE, -2)
    val expected1 = mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE + 1, Int.MAX_VALUE, Int.MAX_VALUE - 1)
    val r1 = mmHaddEpi32(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmHsubEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetrEpi16(4, 128, 4, 3, 24, 12, 6, 19)
    val expected = mmSetrEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-124).toShort(), 1, 12, (-13).toShort())
    val r = mmHsubEpi16(a, b)
    assertEqM128i(r, expected)
    // Test wrapping on overflow
    val a1 = mmSetrEpi16(Short.MAX_VALUE, (-1).toShort(), Short.MAX_VALUE, (-2).toShort(), Short.MAX_VALUE, (-3).toShort(), Short.MAX_VALUE, (-4).toShort())
    val b1 = mmSetrEpi16(Short.MIN_VALUE, 1, Short.MIN_VALUE, 2, Short.MIN_VALUE, 3, Short.MIN_VALUE, 4)
    val expected1 = mmSetrEpi16(Short.MIN_VALUE, (Short.MIN_VALUE + 1).toShort(), (Short.MIN_VALUE + 2).toShort(), (Short.MIN_VALUE + 3).toShort(), Short.MAX_VALUE, (Short.MAX_VALUE - 1).toShort(), (Short.MAX_VALUE - 2).toShort(), (Short.MAX_VALUE - 3).toShort())
    val r1 = mmHsubEpi16(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmHsubsEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetrEpi16(4, 128, 4, 3, 32767, (-1).toShort(), (-32768).toShort(), 1)
    val expected = mmSetrEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-124).toShort(), 1, 32767, (-32768).toShort())
    val r = mmHsubsEpi16(a, b)
    assertEqM128i(r, expected)
    // Test saturating on overflow
    val a1 = mmSetrEpi16(Short.MAX_VALUE, (-1).toShort(), Short.MAX_VALUE, (-2).toShort(), Short.MAX_VALUE, (-3).toShort(), Short.MAX_VALUE, (-4).toShort())
    val b1 = mmSetrEpi16(Short.MIN_VALUE, 1, Short.MIN_VALUE, 2, Short.MIN_VALUE, 3, Short.MIN_VALUE, 4)
    val expected1 = mmSetrEpi16(Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    val r1 = mmHsubsEpi16(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmHsubEpi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val b = mmSetrEpi32(4, 128, 4, 3)
    val expected = mmSetrEpi32(-1, -1, -124, 1)
    val r = mmHsubEpi32(a, b)
    assertEqM128i(r, expected)
    // Test wrapping on overflow
    val a1 = mmSetrEpi32(Int.MAX_VALUE, -1, Int.MAX_VALUE, -2)
    val b1 = mmSetrEpi32(Int.MIN_VALUE, 1, Int.MIN_VALUE, 2)
    val expected1 = mmSetrEpi32(Int.MIN_VALUE, Int.MIN_VALUE + 1, Int.MAX_VALUE, Int.MAX_VALUE - 1)
    val r1 = mmHsubEpi32(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmMaddubsEpi16() {
    val a = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetrEpi8(4, 63, 4, 3, 24, 12, 6, 19, 12, 5, 5, 10, 4, 1, 8, 0)
    val expected = mmSetrEpi16(130, 24, 192, 194, 158, 175, 66, 120)
    val r = mmMaddubsEpi16(a, b)
    assertEqM128i(r, expected)
    // Test widening and saturation
    val a1 = mmSetrEpi8(UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), 100, 100, 0, 0, 0, 0, 0, 0, 0, 0)
    val b1 = mmSetrEpi8(Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, 50, 15, 0, 0, 0, 0, 0, 0, 0, 0)
    val expected1 = mmSetrEpi16(Short.MAX_VALUE, (-255).toShort(), Short.MIN_VALUE, 6500, 0, 0, 0, 0)
    val r1 = mmMaddubsEpi16(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmMulhrsEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetrEpi16(4, 128, 4, 3, 32767, (-1).toShort(), (-32768).toShort(), 1)
    val expected = mmSetrEpi16(0, 0, 0, 0, 5, 0, (-7).toShort(), 0)
    val r = mmMulhrsEpi16(a, b)
    assertEqM128i(r, expected)
    // Test extreme values
    val a1 = mmSetrEpi16(Short.MAX_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, 0, 0, 0, 0, 0)
    val b1 = mmSetrEpi16(Short.MAX_VALUE, Short.MIN_VALUE, Short.MAX_VALUE, 0, 0, 0, 0, 0)
    val expected1 = mmSetrEpi16((Short.MAX_VALUE - 1).toShort(), Short.MIN_VALUE, (-Short.MAX_VALUE).toShort(), 0, 0, 0, 0, 0)
    val r1 = mmMulhrsEpi16(a1, b1)
    assertEqM128i(r1, expected1)
}

public fun testMmSignEpi8() {
    val a = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, (-14).toByte(), (-15).toByte(), 16)
    val b = mmSetrEpi8(4, 63, (-4).toByte(), 3, 24, 12, (-6).toByte(), (-19).toByte(), 12, 5, (-5).toByte(), 10, 4, 1, (-8).toByte(), 0)
    val expected = mmSetrEpi8(1, 2, (-3).toByte(), 4, 5, 6, (-7).toByte(), (-8).toByte(), 9, 10, (-11).toByte(), 12, 13, (-14).toByte(), 15, 0)
    val r = mmSignEpi8(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSignEpi16() {
    val a = mmSetrEpi16(1, 2, 3, 4, (-5).toShort(), (-6).toShort(), 7, 8)
    val b = mmSetrEpi16(4, 128, 0, 3, 1, (-1).toShort(), (-2).toShort(), 1)
    val expected = mmSetrEpi16(1, 2, 0, 4, (-5).toShort(), 6, (-7).toShort(), 8)
    val r = mmSignEpi16(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSignEpi32() {
    val a = mmSetrEpi32(-1, 2, 3, 4)
    val b = mmSetrEpi32(1, -1, 1, 0)
    val expected = mmSetrEpi32(-1, -2, 3, 0)
    val r = mmSignEpi32(a, b)
    assertEqM128i(r, expected)
}
