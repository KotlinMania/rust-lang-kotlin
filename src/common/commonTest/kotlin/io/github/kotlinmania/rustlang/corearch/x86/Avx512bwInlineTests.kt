// port-lint: tests x86/avx512bw.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun testMm512AbsEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val r = mm512AbsEpi16(a)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskAbsEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val r = mm512MaskAbsEpi16(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskAbsEpi16(a, 0xFF00FFu, a)
    val e = mm512SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAbsEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val r = mm512MaskzAbsEpi16(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAbsEpi16(0xFF00FFu, a)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAbsEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val r = mm256MaskAbsEpi16(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskAbsEpi16(a, 0xFFu, a)
    val e = mm256SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAbsEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val r = mm256MaskzAbsEpi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAbsEpi16(0xFFu, a)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskAbsEpi16() {
    val a = mmSet1Epi16((-1).toShort())
    val r = mmMaskAbsEpi16(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskAbsEpi16(a, 0xFu, a)
    val e = mmSetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAbsEpi16() {
    val a = mmSet1Epi16((-1).toShort())
    val r = mmMaskzAbsEpi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAbsEpi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512AbsEpi8() {
    val a = mm512Set1Epi8((-1).toByte())
    val r = mm512AbsEpi8(a)
    val e = mm512Set1Epi8(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskAbsEpi8() {
    val a = mm512Set1Epi8((-1).toByte())
    val r = mm512MaskAbsEpi8(a, 0uL, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskAbsEpi8(a, 0xFF00FF00FF00FFuL, a)
    val e = mm512SetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAbsEpi8() {
    val a = mm512Set1Epi8((-1).toByte())
    val r = mm512MaskzAbsEpi8(0uL, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAbsEpi8(0xFF00FF00FF00FFuL, a)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAbsEpi8() {
    val a = mm256Set1Epi8((-1).toByte())
    val r = mm256MaskAbsEpi8(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskAbsEpi8(a, 0xFF00FFu, a)
    val e = mm256SetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAbsEpi8() {
    val a = mm256Set1Epi8((-1).toByte())
    val r = mm256MaskzAbsEpi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAbsEpi8(0xFF00FFu, a)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskAbsEpi8() {
    val a = mmSet1Epi8((-1).toByte())
    val r = mmMaskAbsEpi8(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskAbsEpi8(a, 0xFFu, a)
    val e = mmSetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAbsEpi8() {
    val a = mmSet1Epi8((-1).toByte())
    val r = mmMaskzAbsEpi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAbsEpi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512AddEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512AddEpi16(a, b)
    val e = mm512Set1Epi16(3)
    assertEqM512i(r, e)
}

public fun testMm512MaskAddEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512MaskAddEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddEpi16(a, 0xFF00FFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512MaskzAddEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddEpi16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(2)
    val r = mm256MaskAddEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddEpi16(a, 0xFFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(2)
    val r = mm256MaskzAddEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddEpi16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM256i(r1, e)
}

public fun testMmMaskAddEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(2)
    val r = mmMaskAddEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 3, 3, 3, 3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(2)
    val r = mmMaskzAddEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 3, 3, 3, 3)
    assertEqM128i(r1, e)
}

public fun testMm512AddEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512AddEpi8(a, b)
    val e = mm512Set1Epi8(3)
    assertEqM512i(r, e)
}

public fun testMm512MaskAddEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512MaskAddEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddEpi8(a, 0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512MaskzAddEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddEpi8(0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(2)
    val r = mm256MaskAddEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddEpi8(a, 0xFF00FFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(2)
    val r = mm256MaskzAddEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddEpi8(0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM256i(r1, e)
}

public fun testMmMaskAddEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(2)
    val r = mmMaskAddEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddEpi8(a, 0xFFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(2)
    val r = mmMaskzAddEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddEpi8(0xFFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3)
    assertEqM128i(r1, e)
}

public fun testMm512AddsEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm512AddsEpu16(a, b)
    val e = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    assertEqM512i(r, e)
}

public fun testMm512MaskAddsEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm512MaskAddsEpu16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddsEpu16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddsEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm512MaskzAddsEpu16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddsEpu16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddsEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm256MaskAddsEpu16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddsEpu16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddsEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm256MaskzAddsEpu16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddsEpu16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM256i(r1, e)
}

public fun testMmMaskAddsEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(UShort.MAX_VALUE.toShort())
    val r = mmMaskAddsEpu16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddsEpu16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddsEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(UShort.MAX_VALUE.toShort())
    val r = mmMaskzAddsEpu16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddsEpu16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM128i(r1, e)
}

public fun testMm512AddsEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm512AddsEpu8(a, b)
    val e = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    assertEqM512i(r, e)
}

public fun testMm512MaskAddsEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm512MaskAddsEpu8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddsEpu8(a, 0xFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddsEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm512MaskzAddsEpu8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddsEpu8(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddsEpu8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm256MaskAddsEpu8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddsEpu8(a, 0xFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddsEpu8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm256MaskzAddsEpu8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddsEpu8(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM256i(r1, e)
}

public fun testMmMaskAddsEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(UByte.MAX_VALUE.toByte())
    val r = mmMaskAddsEpu8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddsEpu8(a, 0xFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddsEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(UByte.MAX_VALUE.toByte())
    val r = mmMaskzAddsEpu8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddsEpu8(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMm512AddsEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512AddsEpi16(a, b)
    val e = mm512Set1Epi16(Short.MAX_VALUE)
    assertEqM512i(r, e)
}

public fun testMm512MaskAddsEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512MaskAddsEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddsEpi16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddsEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512MaskzAddsEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddsEpi16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddsEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256MaskAddsEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddsEpi16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddsEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256MaskzAddsEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddsEpi16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskAddsEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmMaskAddsEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddsEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddsEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmMaskzAddsEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddsEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512AddsEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(Byte.MAX_VALUE)
    val r = mm512AddsEpi8(a, b)
    val e = mm512Set1Epi8(Byte.MAX_VALUE)
    assertEqM512i(r, e)
}

public fun testMm512MaskAddsEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(Byte.MAX_VALUE)
    val r = mm512MaskAddsEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddsEpi8(a, 0xFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddsEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(Byte.MAX_VALUE)
    val r = mm512MaskzAddsEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddsEpi8(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddsEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(Byte.MAX_VALUE)
    val r = mm256MaskAddsEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddsEpi8(a, 0xFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddsEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(Byte.MAX_VALUE)
    val r = mm256MaskzAddsEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddsEpi8(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskAddsEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(Byte.MAX_VALUE)
    val r = mmMaskAddsEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddsEpi8(a, 0xFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddsEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(Byte.MAX_VALUE)
    val r = mmMaskzAddsEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddsEpi8(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512SubEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512SubEpi16(a, b)
    val e = mm512Set1Epi16((-1).toShort())
    assertEqM512i(r, e)
}

public fun testMm512MaskSubEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512MaskSubEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubEpi16(a, 0xFF00FFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort())
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512MaskzSubEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubEpi16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 0, 0, 0, 0, 0, 0, 0, 0, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort())
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(2)
    val r = mm256MaskSubEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubEpi16(a, 0xFFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort())
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(2)
    val r = mm256MaskzSubEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubEpi16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort())
    assertEqM256i(r1, e)
}

public fun testMmMaskSubEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(2)
    val r = mmMaskSubEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort())
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(2)
    val r = mmMaskzSubEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort())
    assertEqM128i(r1, e)
}

public fun testMm512SubEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512SubEpi8(a, b)
    val e = mm512Set1Epi8((-1).toByte())
    assertEqM512i(r, e)
}

public fun testMm512MaskSubEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512MaskSubEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubEpi8(a, 0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512MaskzSubEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubEpi8(0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(2)
    val r = mm256MaskSubEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubEpi8(a, 0xFF00FFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(2)
    val r = mm256MaskzSubEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubEpi8(0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM256i(r1, e)
}

public fun testMmMaskSubEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(2)
    val r = mmMaskSubEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubEpi8(a, 0xFFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(2)
    val r = mmMaskzSubEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubEpi8(0xFFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm512SubsEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm512SubsEpu16(a, b)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSubsEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm512MaskSubsEpu16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubsEpu16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubsEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm512MaskzSubsEpu16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubsEpu16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubsEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm256MaskSubsEpu16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubsEpu16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubsEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(UShort.MAX_VALUE.toShort())
    val r = mm256MaskzSubsEpu16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubsEpu16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSubsEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(UShort.MAX_VALUE.toShort())
    val r = mmMaskSubsEpu16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubsEpu16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubsEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(UShort.MAX_VALUE.toShort())
    val r = mmMaskzSubsEpu16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubsEpu16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SubsEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm512SubsEpu8(a, b)
    val e = mm512Set1Epi8(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSubsEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm512MaskSubsEpu8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubsEpu8(a, 0xFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubsEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm512MaskzSubsEpu8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubsEpu8(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubsEpu8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm256MaskSubsEpu8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubsEpu8(a, 0xFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubsEpu8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(UByte.MAX_VALUE.toByte())
    val r = mm256MaskzSubsEpu8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubsEpu8(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSubsEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(UByte.MAX_VALUE.toByte())
    val r = mmMaskSubsEpu8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubsEpu8(a, 0xFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubsEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(UByte.MAX_VALUE.toByte())
    val r = mmMaskzSubsEpu8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubsEpu8(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512SubsEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512SubsEpi16(a, b)
    val e = mm512Set1Epi16(Short.MIN_VALUE)
    assertEqM512i(r, e)
}

public fun testMm512MaskSubsEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512MaskSubsEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubsEpi16(a, 0xFu, a, b)
    val e = mm512SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubsEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512MaskzSubsEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubsEpi16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubsEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256MaskSubsEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubsEpi16(a, 0xFu, a, b)
    val e = mm256SetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubsEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256MaskzSubsEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubsEpi16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskSubsEpi16() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmMaskSubsEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubsEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubsEpi16() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmMaskzSubsEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubsEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512SubsEpi8() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8(Byte.MAX_VALUE)
    val r = mm512SubsEpi8(a, b)
    val e = mm512Set1Epi8(Byte.MIN_VALUE)
    assertEqM512i(r, e)
}

public fun testMm512MaskSubsEpi8() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8(Byte.MAX_VALUE)
    val r = mm512MaskSubsEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubsEpi8(a, 0xFuL, a, b)
    val e = mm512SetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubsEpi8() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8(Byte.MAX_VALUE)
    val r = mm512MaskzSubsEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubsEpi8(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubsEpi8() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8(Byte.MAX_VALUE)
    val r = mm256MaskSubsEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubsEpi8(a, 0xFu, a, b)
    val e = mm256SetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubsEpi8() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8(Byte.MAX_VALUE)
    val r = mm256MaskzSubsEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubsEpi8(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskSubsEpi8() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8(Byte.MAX_VALUE)
    val r = mmMaskSubsEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubsEpi8(a, 0xFu, a, b)
    val e = mmSetEpi8((-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubsEpi8() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8(Byte.MAX_VALUE)
    val r = mmMaskzSubsEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubsEpi8(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512MulhiEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MulhiEpu16(a, b)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulhiEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskMulhiEpu16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulhiEpu16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulhiEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzMulhiEpu16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulhiEpu16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulhiEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskMulhiEpu16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulhiEpu16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulhiEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzMulhiEpu16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulhiEpu16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulhiEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskMulhiEpu16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulhiEpu16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulhiEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskzMulhiEpu16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulhiEpu16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MulhiEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MulhiEpi16(a, b)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulhiEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskMulhiEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulhiEpi16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulhiEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzMulhiEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulhiEpi16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulhiEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskMulhiEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulhiEpi16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulhiEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzMulhiEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulhiEpi16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulhiEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskMulhiEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulhiEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulhiEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskzMulhiEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulhiEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MulhrsEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MulhrsEpi16(a, b)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulhrsEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskMulhrsEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulhrsEpi16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulhrsEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzMulhrsEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulhrsEpi16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulhrsEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskMulhrsEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulhrsEpi16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulhrsEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzMulhrsEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulhrsEpi16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulhrsEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskMulhrsEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulhrsEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulhrsEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskzMulhrsEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulhrsEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MulloEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MulloEpi16(a, b)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulloEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskMulloEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulloEpi16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulloEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzMulloEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulloEpi16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulloEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskMulloEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulloEpi16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulloEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzMulloEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulloEpi16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulloEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskMulloEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulloEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulloEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskzMulloEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulloEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512MaxEpu16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaxEpu16(a, b)
    val e = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpu16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMaxEpu16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpu16(a, 0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpu16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMaxEpu16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpu16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaxEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMaxEpu16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpu16(a, 0xFFu, a, b)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMaxEpu16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpu16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaxEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMaxEpu16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpu16(a, 0xFu, a, b)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMaxEpu16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpu16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512MaxEpu8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaxEpu8(a, b)
    val e = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpu8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMaxEpu8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpu8(a, 0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpu8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMaxEpu8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpu8(0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaxEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMaxEpu8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpu8(a, 0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMaxEpu8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpu8(0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaxEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMaxEpu8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpu8(a, 0xFFu, a, b)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMaxEpu8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpu8(0xFFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512MaxEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaxEpi16(a, b)
    val e = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMaxEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpi16(a, 0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMaxEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpi16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaxEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMaxEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpi16(a, 0xFFu, a, b)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMaxEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpi16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaxEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMaxEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMaxEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512MaxEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaxEpi8(a, b)
    val e = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11, 10, 9, 8, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMaxEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpi8(a, 0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMaxEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpi8(0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaxEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMaxEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpi8(a, 0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMaxEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpi8(0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaxEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMaxEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpi8(a, 0xFFu, a, b)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMaxEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpi8(0xFFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512MinEpu16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MinEpu16(a, b)
    val e = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpu16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMinEpu16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpu16(a, 0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpu16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMinEpu16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpu16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMinEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMinEpu16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpu16(a, 0xFFu, a, b)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMinEpu16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpu16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMinEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMinEpu16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpu16(a, 0xFu, a, b)
    val e = mmSetEpi16(0, 1, 2, 3, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMinEpu16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpu16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MinEpu8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MinEpu8(a, b)
    val e = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpu8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMinEpu8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpu8(a, 0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpu8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMinEpu8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpu8(0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMinEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMinEpu8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpu8(a, 0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMinEpu8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpu8(0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMinEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMinEpu8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpu8(a, 0xFFu, a, b)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMinEpu8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpu8(0xFFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MinEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MinEpi16(a, b)
    val e = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMinEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpi16(a, 0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMinEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpi16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMinEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMinEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpi16(a, 0xFFu, a, b)
    val e = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi16(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMinEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpi16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMinEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMinEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpi16(a, 0xFu, a, b)
    val e = mmSetEpi16(0, 1, 2, 3, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mmSetEpi16(7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMinEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMm512MinEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MinEpi8(a, b)
    val e = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskMinEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpi8(a, 0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm512SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm512MaskzMinEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpi8(0xFF00FF00FF00FFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMinEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskMinEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpi8(a, 0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256MaskzMinEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpi8(0xFF00FFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskMinEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskMinEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpi8(a, 0xFFu, a, b)
    val e = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSetEpi8(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mmMaskzMinEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpi8(0xFFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0)
    assertEqM128i(r1, e)
}

public fun testMm512CmpltEpu16Mask() {
    val a = mm512Set1Epi16((-2).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpltEpu16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpltEpu16Mask() {
    val a = mm512Set1Epi16((-2).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpltEpu16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpltEpu16Mask() {
    val a = mm256Set1Epi16((-2).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpltEpu16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpltEpu16Mask() {
    val a = mm256Set1Epi16((-2).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpltEpu16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpltEpu16Mask() {
    val a = mmSet1Epi16((-2).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpltEpu16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpltEpu16Mask() {
    val a = mmSet1Epi16((-2).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpltEpu16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpltEpu8Mask() {
    val a = mm512Set1Epi8((-2).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpltEpu8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpltEpu8Mask() {
    val a = mm512Set1Epi8((-2).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpltEpu8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpltEpu8Mask() {
    val a = mm256Set1Epi8((-2).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpltEpu8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpltEpu8Mask() {
    val a = mm256Set1Epi8((-2).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpltEpu8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpltEpu8Mask() {
    val a = mmSet1Epi8((-2).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpltEpu8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpltEpu8Mask() {
    val a = mmSet1Epi8((-2).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpltEpu8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpltEpi16Mask() {
    val a = mm512Set1Epi16((-2).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpltEpi16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpltEpi16Mask() {
    val a = mm512Set1Epi16((-2).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpltEpi16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpltEpi16Mask() {
    val a = mm256Set1Epi16((-2).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpltEpi16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpltEpi16Mask() {
    val a = mm256Set1Epi16((-2).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpltEpi16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpltEpi16Mask() {
    val a = mmSet1Epi16((-2).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpltEpi16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpltEpi16Mask() {
    val a = mmSet1Epi16((-2).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpltEpi16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpltEpi8Mask() {
    val a = mm512Set1Epi8((-2).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpltEpi8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpltEpi8Mask() {
    val a = mm512Set1Epi8((-2).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpltEpi8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpltEpi8Mask() {
    val a = mm256Set1Epi8((-2).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpltEpi8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpltEpi8Mask() {
    val a = mm256Set1Epi8((-2).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpltEpi8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpltEpi8Mask() {
    val a = mmSet1Epi8((-2).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpltEpi8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpltEpi8Mask() {
    val a = mmSet1Epi8((-2).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpltEpi8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpgtEpu16Mask() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(1)
    val m = mm512CmpgtEpu16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpgtEpu16Mask() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(1)
    val mask = 0x55555555
    val r = mm512MaskCmpgtEpu16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpgtEpu16Mask() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(1)
    val m = mm256CmpgtEpu16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpgtEpu16Mask() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(1)
    val mask = 0x5555
    val r = mm256MaskCmpgtEpu16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpgtEpu16Mask() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(1)
    val m = mmCmpgtEpu16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpgtEpu16Mask() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(1)
    val mask = 0x55
    val r = mmMaskCmpgtEpu16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpgtEpu8Mask() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(1)
    val m = mm512CmpgtEpu8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpgtEpu8Mask() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(1)
    val mask = 0x5555555555555555
    val r = mm512MaskCmpgtEpu8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpgtEpu8Mask() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(1)
    val m = mm256CmpgtEpu8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpgtEpu8Mask() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(1)
    val mask = 0x55555555
    val r = mm256MaskCmpgtEpu8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpgtEpu8Mask() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(1)
    val m = mmCmpgtEpu8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpgtEpu8Mask() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(1)
    val mask = 0x5555
    val r = mmMaskCmpgtEpu8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpgtEpi16Mask() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpgtEpi16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpgtEpi16Mask() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpgtEpi16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpgtEpi16Mask() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpgtEpi16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpgtEpi16Mask() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpgtEpi16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpgtEpi16Mask() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpgtEpi16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpgtEpi16Mask() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpgtEpi16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpgtEpi8Mask() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpgtEpi8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpgtEpi8Mask() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpgtEpi8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpgtEpi8Mask() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpgtEpi8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpgtEpi8Mask() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpgtEpi8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpgtEpi8Mask() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpgtEpi8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpgtEpi8Mask() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpgtEpi8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpleEpu16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpleEpu16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpleEpu16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpleEpu16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpleEpu16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpleEpu16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpleEpu16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpleEpu16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpleEpu16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpleEpu16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpleEpu16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpleEpu16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpleEpu8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpleEpu8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpleEpu8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpleEpu8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpleEpu8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpleEpu8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpleEpu8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpleEpu8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpleEpu8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpleEpu8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpleEpu8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpleEpu8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpleEpi16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpleEpi16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpleEpi16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpleEpi16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpleEpi16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpleEpi16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpleEpi16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpleEpi16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpleEpi16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpleEpi16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpleEpi16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpleEpi16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpleEpi8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpleEpi8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpleEpi8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpleEpi8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpleEpi8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpleEpi8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpleEpi8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpleEpi8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpleEpi8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpleEpi8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpleEpi8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpleEpi8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpgeEpu16Mask() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val m = mm512CmpgeEpu16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpgeEpu16Mask() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val mask = 0x55555555
    val r = mm512MaskCmpgeEpu16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpgeEpu16Mask() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val m = mm256CmpgeEpu16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpgeEpu16Mask() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val mask = 0x5555
    val r = mm256MaskCmpgeEpu16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpgeEpu16Mask() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val m = mmCmpgeEpu16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpgeEpu16Mask() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val mask = 0x55
    val r = mmMaskCmpgeEpu16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpgeEpu8Mask() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val m = mm512CmpgeEpu8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpgeEpu8Mask() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val mask = 0x5555555555555555
    val r = mm512MaskCmpgeEpu8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpgeEpu8Mask() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val m = mm256CmpgeEpu8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpgeEpu8Mask() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val mask = 0x55555555
    val r = mm256MaskCmpgeEpu8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpgeEpu8Mask() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val m = mmCmpgeEpu8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpgeEpu8Mask() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val mask = 0x5555
    val r = mmMaskCmpgeEpu8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpgeEpi16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpgeEpi16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpgeEpi16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpgeEpi16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpgeEpi16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpgeEpi16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpgeEpi16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpgeEpi16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpgeEpi16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpgeEpi16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpgeEpi16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpgeEpi16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpgeEpi8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpgeEpi8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpgeEpi8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpgeEpi8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpgeEpi8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpgeEpi8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpgeEpi8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpgeEpi8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpgeEpi8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpgeEpi8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpgeEpi8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpgeEpi8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpeqEpu16Mask() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val m = mm512CmpeqEpu16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpeqEpu16Mask() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val mask = 0x55555555
    val r = mm512MaskCmpeqEpu16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpeqEpu16Mask() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val m = mm256CmpeqEpu16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpeqEpu16Mask() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val mask = 0x5555
    val r = mm256MaskCmpeqEpu16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpeqEpu16Mask() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val m = mmCmpeqEpu16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpeqEpu16Mask() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val mask = 0x55
    val r = mmMaskCmpeqEpu16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpeqEpu8Mask() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val m = mm512CmpeqEpu8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpeqEpu8Mask() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val mask = 0x5555555555555555
    val r = mm512MaskCmpeqEpu8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpeqEpu8Mask() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val m = mm256CmpeqEpu8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpeqEpu8Mask() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val mask = 0x55555555
    val r = mm256MaskCmpeqEpu8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpeqEpu8Mask() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val m = mmCmpeqEpu8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpeqEpu8Mask() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val mask = 0x5555
    val r = mmMaskCmpeqEpu8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpeqEpi16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpeqEpi16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpeqEpi16Mask() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpeqEpi16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpeqEpi16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpeqEpi16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpeqEpi16Mask() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpeqEpi16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpeqEpi16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpeqEpi16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpeqEpi16Mask() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpeqEpi16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpeqEpi8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpeqEpi8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpeqEpi8Mask() {
    val a = mm512Set1Epi8((-1).toByte())
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpeqEpi8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpeqEpi8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpeqEpi8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpeqEpi8Mask() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpeqEpi8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpeqEpi8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpeqEpi8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpeqEpi8Mask() {
    val a = mmSet1Epi8((-1).toByte())
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpeqEpi8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpneqEpu16Mask() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(1)
    val m = mm512CmpneqEpu16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpneqEpu16Mask() {
    val a = mm512Set1Epi16(2)
    val b = mm512Set1Epi16(1)
    val mask = 0x55555555
    val r = mm512MaskCmpneqEpu16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpneqEpu16Mask() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(1)
    val m = mm256CmpneqEpu16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpneqEpu16Mask() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(1)
    val mask = 0x5555
    val r = mm256MaskCmpneqEpu16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpneqEpu16Mask() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(1)
    val m = mmCmpneqEpu16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpneqEpu16Mask() {
    val a = mmSet1Epi16(2)
    val b = mmSet1Epi16(1)
    val mask = 0x55
    val r = mmMaskCmpneqEpu16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpneqEpu8Mask() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(1)
    val m = mm512CmpneqEpu8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpneqEpu8Mask() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(1)
    val mask = 0x5555555555555555
    val r = mm512MaskCmpneqEpu8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpneqEpu8Mask() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(1)
    val m = mm256CmpneqEpu8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpneqEpu8Mask() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(1)
    val mask = 0x55555555
    val r = mm256MaskCmpneqEpu8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpneqEpu8Mask() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(1)
    val m = mmCmpneqEpu8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpneqEpu8Mask() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(1)
    val mask = 0x5555
    val r = mmMaskCmpneqEpu8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpneqEpi16Mask() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((-1).toShort())
    val m = mm512CmpneqEpi16Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpneqEpi16Mask() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16((-1).toShort())
    val mask = 0x55555555
    val r = mm512MaskCmpneqEpi16Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpneqEpi16Mask() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((-1).toShort())
    val m = mm256CmpneqEpi16Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpneqEpi16Mask() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16((-1).toShort())
    val mask = 0x5555
    val r = mm256MaskCmpneqEpi16Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpneqEpi16Mask() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((-1).toShort())
    val m = mmCmpneqEpi16Mask(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpneqEpi16Mask() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16((-1).toShort())
    val mask = 0x55
    val r = mmMaskCmpneqEpi16Mask(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpneqEpi8Mask() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8((-1).toByte())
    val m = mm512CmpneqEpi8Mask(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpneqEpi8Mask() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8((-1).toByte())
    val mask = 0x5555555555555555
    val r = mm512MaskCmpneqEpi8Mask(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpneqEpi8Mask() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8((-1).toByte())
    val m = mm256CmpneqEpi8Mask(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpneqEpi8Mask() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8((-1).toByte())
    val mask = 0x55555555
    val r = mm256MaskCmpneqEpi8Mask(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpneqEpi8Mask() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8((-1).toByte())
    val m = mmCmpneqEpi8Mask(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpneqEpi8Mask() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8((-1).toByte())
    val mask = 0x5555
    val r = mmMaskCmpneqEpi8Mask(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpEpu16Mask() {
    val a = mm512Set1Epi16(0)
    val b = mm512Set1Epi16(1)
    val m = mm512CmpEpu16Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpEpu16Mask() {
    val a = mm512Set1Epi16(0)
    val b = mm512Set1Epi16(1)
    val mask = 0x55555555
    val r = mm512MaskCmpEpu16Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpEpu16Mask() {
    val a = mm256Set1Epi16(0)
    val b = mm256Set1Epi16(1)
    val m = mm256CmpEpu16Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpEpu16Mask() {
    val a = mm256Set1Epi16(0)
    val b = mm256Set1Epi16(1)
    val mask = 0x5555
    val r = mm256MaskCmpEpu16Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpEpu16Mask() {
    val a = mmSet1Epi16(0)
    val b = mmSet1Epi16(1)
    val m = mmCmpEpu16Mask<MmCmpintLt>(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpEpu16Mask() {
    val a = mmSet1Epi16(0)
    val b = mmSet1Epi16(1)
    val mask = 0x55
    val r = mmMaskCmpEpu16Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpEpu8Mask() {
    val a = mm512Set1Epi8(0)
    val b = mm512Set1Epi8(1)
    val m = mm512CmpEpu8Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpEpu8Mask() {
    val a = mm512Set1Epi8(0)
    val b = mm512Set1Epi8(1)
    val mask = 0x5555555555555555
    val r = mm512MaskCmpEpu8Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpEpu8Mask() {
    val a = mm256Set1Epi8(0)
    val b = mm256Set1Epi8(1)
    val m = mm256CmpEpu8Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpEpu8Mask() {
    val a = mm256Set1Epi8(0)
    val b = mm256Set1Epi8(1)
    val mask = 0x55555555
    val r = mm256MaskCmpEpu8Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpEpu8Mask() {
    val a = mmSet1Epi8(0)
    val b = mmSet1Epi8(1)
    val m = mmCmpEpu8Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpEpu8Mask() {
    val a = mmSet1Epi8(0)
    val b = mmSet1Epi8(1)
    val mask = 0x5555
    val r = mmMaskCmpEpu8Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm512CmpEpi16Mask() {
    val a = mm512Set1Epi16(0)
    val b = mm512Set1Epi16(1)
    val m = mm512CmpEpi16Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm512MaskCmpEpi16Mask() {
    val a = mm512Set1Epi16(0)
    val b = mm512Set1Epi16(1)
    val mask = 0x55555555
    val r = mm512MaskCmpEpi16Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMm256CmpEpi16Mask() {
    val a = mm256Set1Epi16(0)
    val b = mm256Set1Epi16(1)
    val m = mm256CmpEpi16Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMm256MaskCmpEpi16Mask() {
    val a = mm256Set1Epi16(0)
    val b = mm256Set1Epi16(1)
    val mask = 0x5555
    val r = mm256MaskCmpEpi16Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMmCmpEpi16Mask() {
    val a = mmSet1Epi16(0)
    val b = mmSet1Epi16(1)
    val m = mmCmpEpi16Mask<MmCmpintLt>(a, b)
    check(m == 0xFFu.toUByte())
}

public fun testMmMaskCmpEpi16Mask() {
    val a = mmSet1Epi16(0)
    val b = mmSet1Epi16(1)
    val mask = 0x55
    val r = mmMaskCmpEpi16Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x55u.toUByte())
}

public fun testMm512CmpEpi8Mask() {
    val a = mm512Set1Epi8(0)
    val b = mm512Set1Epi8(1)
    val m = mm512CmpEpi8Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFFFFFFFFFFFFFuL)
}

public fun testMm512MaskCmpEpi8Mask() {
    val a = mm512Set1Epi8(0)
    val b = mm512Set1Epi8(1)
    val mask = 0x5555555555555555
    val r = mm512MaskCmpEpi8Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x5555555555555555uL)
}

public fun testMm256CmpEpi8Mask() {
    val a = mm256Set1Epi8(0)
    val b = mm256Set1Epi8(1)
    val m = mm256CmpEpi8Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFFFFFu)
}

public fun testMm256MaskCmpEpi8Mask() {
    val a = mm256Set1Epi8(0)
    val b = mm256Set1Epi8(1)
    val mask = 0x55555555
    val r = mm256MaskCmpEpi8Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x55555555u)
}

public fun testMmCmpEpi8Mask() {
    val a = mmSet1Epi8(0)
    val b = mmSet1Epi8(1)
    val m = mmCmpEpi8Mask<MmCmpintLt>(a, b)
    check(m == 0xFFFFu.toUShort())
}

public fun testMmMaskCmpEpi8Mask() {
    val a = mmSet1Epi8(0)
    val b = mmSet1Epi8(1)
    val mask = 0x5555
    val r = mmMaskCmpEpi8Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x5555u.toUShort())
}

public fun testMm256ReduceAddEpi16() {
    val a = mm256Set1Epi16(1)
    val e = mm256ReduceAddEpi16(a)
    check(16.toShort() == e)
}

public fun testMm256MaskReduceAddEpi16() {
    val a = mm256Set1Epi16(1)
    val e = mm256MaskReduceAddEpi16(0xFF00u, a)
    check(8.toShort() == e)
}

public fun testMmReduceAddEpi16() {
    val a = mmSet1Epi16(1)
    val e = mmReduceAddEpi16(a)
    check(8.toShort() == e)
}

public fun testMmMaskReduceAddEpi16() {
    val a = mmSet1Epi16(1)
    val e = mmMaskReduceAddEpi16(0xF0u, a)
    check(4.toShort() == e)
}

public fun testMm256ReduceAddEpi8() {
    val a = mm256Set1Epi8(1)
    val e = mm256ReduceAddEpi8(a)
    check(32.toByte() == e)
}

public fun testMm256MaskReduceAddEpi8() {
    val a = mm256Set1Epi8(1)
    val e = mm256MaskReduceAddEpi8(0xFF00FF00u, a)
    check(16.toByte() == e)
}

public fun testMmReduceAddEpi8() {
    val a = mmSet1Epi8(1)
    val e = mmReduceAddEpi8(a)
    check(16.toByte() == e)
}

public fun testMmMaskReduceAddEpi8() {
    val a = mmSet1Epi8(1)
    val e = mmMaskReduceAddEpi8(0xFF00u, a)
    check(8.toByte() == e)
}

public fun testMm256ReduceAndEpi16() {
    val a = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256ReduceAndEpi16(a)
    check(0.toShort() == e)
}

public fun testMm256MaskReduceAndEpi16() {
    val a = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256MaskReduceAndEpi16(0xFF00u, a)
    check(1.toShort() == e)
}

public fun testMmReduceAndEpi16() {
    val a = mmSetEpi16(1, 1, 1, 1, 2, 2, 2, 2)
    val e = mmReduceAndEpi16(a)
    check(0.toShort() == e)
}

public fun testMmMaskReduceAndEpi16() {
    val a = mmSetEpi16(1, 1, 1, 1, 2, 2, 2, 2)
    val e = mmMaskReduceAndEpi16(0xF0u, a)
    check(1.toShort() == e)
}

public fun testMm256ReduceAndEpi8() {
    val a = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256ReduceAndEpi8(a)
    check(0.toByte() == e)
}

public fun testMm256MaskReduceAndEpi8() {
    val a = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256MaskReduceAndEpi8(0xFF00FF00u, a)
    check(1.toByte() == e)
}

public fun testMmReduceAndEpi8() {
    val a = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mmReduceAndEpi8(a)
    check(0.toByte() == e)
}

public fun testMmMaskReduceAndEpi8() {
    val a = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mmMaskReduceAndEpi8(0xFF00u, a)
    check(1.toByte() == e)
}

public fun testMm256ReduceMulEpi16() {
    val a = mm256SetEpi16(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    val e = mm256ReduceMulEpi16(a)
    check(256.toShort() == e)
}

public fun testMm256MaskReduceMulEpi16() {
    val a = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256MaskReduceMulEpi16(0xFF00u, a)
    check(1.toShort() == e)
}

public fun testMmReduceMulEpi16() {
    val a = mmSetEpi16(2, 2, 2, 2, 1, 1, 1, 1)
    val e = mmReduceMulEpi16(a)
    check(16.toShort() == e)
}

public fun testMmMaskReduceMulEpi16() {
    val a = mmSetEpi16(1, 1, 1, 1, 2, 2, 2, 2)
    val e = mmMaskReduceMulEpi16(0xF0u, a)
    check(1.toShort() == e)
}

public fun testMm256ReduceMulEpi8() {
    val a = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2)
    val e = mm256ReduceMulEpi8(a)
    check(64.toByte() == e)
}

public fun testMm256MaskReduceMulEpi8() {
    val a = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2)
    val e = mm256MaskReduceMulEpi8(0xFF00FF00u, a)
    check(1.toByte() == e)
}

public fun testMmReduceMulEpi8() {
    val a = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2)
    val e = mmReduceMulEpi8(a)
    check(8.toByte() == e)
}

public fun testMmMaskReduceMulEpi8() {
    val a = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2)
    val e = mmMaskReduceMulEpi8(0xFF00u, a)
    check(1.toByte() == e)
}

public fun testMm256ReduceMaxEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Short = mm256ReduceMaxEpi16(a)
    check(15.toShort() == e)
}

public fun testMm256MaskReduceMaxEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Short = mm256MaskReduceMaxEpi16(0xFF00u, a)
    check(7.toShort() == e)
}

public fun testMmReduceMaxEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: Short = mmReduceMaxEpi16(a)
    check(7.toShort() == e)
}

public fun testMmMaskReduceMaxEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: Short = mmMaskReduceMaxEpi16(0xF0u, a)
    check(3.toShort() == e)
}

public fun testMm256ReduceMaxEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: Byte = mm256ReduceMaxEpi8(a)
    check(31.toByte() == e)
}

public fun testMm256MaskReduceMaxEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: Byte = mm256MaskReduceMaxEpi8(0xFFFF0000u, a)
    check(15.toByte() == e)
}

public fun testMmReduceMaxEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Byte = mmReduceMaxEpi8(a)
    check(15.toByte() == e)
}

public fun testMmMaskReduceMaxEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Byte = mmMaskReduceMaxEpi8(0xFF00u, a)
    check(7.toByte() == e)
}

public fun testMm256ReduceMaxEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UShort = mm256ReduceMaxEpu16(a)
    check(15u.toUShort() == e)
}

public fun testMm256MaskReduceMaxEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UShort = mm256MaskReduceMaxEpu16(0xFF00u, a)
    check(7u.toUShort() == e)
}

public fun testMmReduceMaxEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: UShort = mmReduceMaxEpu16(a)
    check(7u.toUShort() == e)
}

public fun testMmMaskReduceMaxEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: UShort = mmMaskReduceMaxEpu16(0xF0u, a)
    check(3u.toUShort() == e)
}

public fun testMm256ReduceMaxEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: UByte = mm256ReduceMaxEpu8(a)
    check(31u.toUByte() == e)
}

public fun testMm256MaskReduceMaxEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: UByte = mm256MaskReduceMaxEpu8(0xFFFF0000u, a)
    check(15u.toUByte() == e)
}

public fun testMmReduceMaxEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UByte = mmReduceMaxEpu8(a)
    check(15u.toUByte() == e)
}

public fun testMmMaskReduceMaxEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UByte = mmMaskReduceMaxEpu8(0xFF00u, a)
    check(7u.toUByte() == e)
}

public fun testMm256ReduceMinEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Short = mm256ReduceMinEpi16(a)
    check(0.toShort() == e)
}

public fun testMm256MaskReduceMinEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Short = mm256MaskReduceMinEpi16(0xFF00u, a)
    check(0.toShort() == e)
}

public fun testMmReduceMinEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: Short = mmReduceMinEpi16(a)
    check(0.toShort() == e)
}

public fun testMmMaskReduceMinEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: Short = mmMaskReduceMinEpi16(0xF0u, a)
    check(0.toShort() == e)
}

public fun testMm256ReduceMinEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: Byte = mm256ReduceMinEpi8(a)
    check(0.toByte() == e)
}

public fun testMm256MaskReduceMinEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: Byte = mm256MaskReduceMinEpi8(0xFFFF0000u, a)
    check(0.toByte() == e)
}

public fun testMmReduceMinEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Byte = mmReduceMinEpi8(a)
    check(0.toByte() == e)
}

public fun testMmMaskReduceMinEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: Byte = mmMaskReduceMinEpi8(0xFF00u, a)
    check(0.toByte() == e)
}

public fun testMm256ReduceMinEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UShort = mm256ReduceMinEpu16(a)
    check(0u.toUShort() == e)
}

public fun testMm256MaskReduceMinEpu16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UShort = mm256MaskReduceMinEpu16(0xFF00u, a)
    check(0u.toUShort() == e)
}

public fun testMmReduceMinEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: UShort = mmReduceMinEpu16(a)
    check(0u.toUShort() == e)
}

public fun testMmMaskReduceMinEpu16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val e: UShort = mmMaskReduceMinEpu16(0xF0u, a)
    check(0u.toUShort() == e)
}

public fun testMm256ReduceMinEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: UByte = mm256ReduceMinEpu8(a)
    check(0u.toUByte() == e)
}

public fun testMm256MaskReduceMinEpu8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e: UByte = mm256MaskReduceMinEpu8(0xFFFF0000u, a)
    check(0u.toUByte() == e)
}

public fun testMmReduceMinEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UByte = mmReduceMinEpu8(a)
    check(0u.toUByte() == e)
}

public fun testMmMaskReduceMinEpu8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val e: UByte = mmMaskReduceMinEpu8(0xFF00u, a)
    check(0u.toUByte() == e)
}

public fun testMm256ReduceOrEpi16() {
    val a = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256ReduceOrEpi16(a)
    check(3.toShort() == e)
}

public fun testMm256MaskReduceOrEpi16() {
    val a = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256MaskReduceOrEpi16(0xFF00u, a)
    check(1.toShort() == e)
}

public fun testMmReduceOrEpi16() {
    val a = mmSetEpi16(1, 1, 1, 1, 2, 2, 2, 2)
    val e = mmReduceOrEpi16(a)
    check(3.toShort() == e)
}

public fun testMmMaskReduceOrEpi16() {
    val a = mmSetEpi16(1, 1, 1, 1, 2, 2, 2, 2)
    val e = mmMaskReduceOrEpi16(0xF0u, a)
    check(1.toShort() == e)
}

public fun testMm256ReduceOrEpi8() {
    val a = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256ReduceOrEpi8(a)
    check(3.toByte() == e)
}

public fun testMm256MaskReduceOrEpi8() {
    val a = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mm256MaskReduceOrEpi8(0xFF00FF00u, a)
    check(1.toByte() == e)
}

public fun testMmReduceOrEpi8() {
    val a = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mmReduceOrEpi8(a)
    check(3.toByte() == e)
}

public fun testMmMaskReduceOrEpi8() {
    val a = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
    val e = mmMaskReduceOrEpi8(0xFF00u, a)
    check(1.toByte() == e)
}

public fun testMm512LoaduEpi16() {
    val a: ShortArray = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512LoaduEpi16(a[0])
    val e = mm512SetEpi16(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM512i(r, e)
}

public fun testMm256LoaduEpi16() {
    val a: ShortArray = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256LoaduEpi16(a[0])
    val e = mm256SetEpi16(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM256i(r, e)
}

public fun testMmLoaduEpi16() {
    val a: ShortArray = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mmLoaduEpi16(a[0])
    val e = mmSetEpi16(8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM128i(r, e)
}

public fun testMm512LoaduEpi8() {
    val a: ByteArray = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512LoaduEpi8(a[0])
    val e = mm512SetEpi8(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM512i(r, e)
}

public fun testMm256LoaduEpi8() {
    val a: ByteArray = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm256LoaduEpi8(a[0])
    val e = mm256SetEpi8(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM256i(r, e)
}

public fun testMmLoaduEpi8() {
    val a: ByteArray = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mmLoaduEpi8(a[0])
    val e = mmSetEpi8(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM128i(r, e)
}

public fun testMm512StoreuEpi16() {
    val a = mm512Set1Epi16(9)
    val localStorageL17039C8 = AddressableLocal(mm512UndefinedEpi32(), UnresolvedTypeLayout<M512i>())
    val r by localStorageL17039C8
    run {
        mm512StoreuEpi16((MutPtr(localStorageL17039C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512i(r, a)
}

public fun testMm256StoreuEpi16() {
    val a = mm256Set1Epi16(9)
    val localStorageL17049C8 = AddressableLocal(mm256Set1Epi32(0), UnresolvedTypeLayout<M256i>())
    val r by localStorageL17049C8
    run {
        mm256StoreuEpi16((MutPtr(localStorageL17049C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM256i(r, a)
}

public fun testMmStoreuEpi16() {
    val a = mmSet1Epi16(9)
    val localStorageL17059C8 = AddressableLocal(mmSet1Epi32(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL17059C8
    run {
        mmStoreuEpi16((MutPtr(localStorageL17059C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM128i(r, a)
}

public fun testMm512StoreuEpi8() {
    val a = mm512Set1Epi8(9)
    val localStorageL17069C8 = AddressableLocal(mm512UndefinedEpi32(), UnresolvedTypeLayout<M512i>())
    val r by localStorageL17069C8
    run {
        mm512StoreuEpi8((MutPtr(localStorageL17069C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512i(r, a)
}

public fun testMm256StoreuEpi8() {
    val a = mm256Set1Epi8(9)
    val localStorageL17079C8 = AddressableLocal(mm256Set1Epi32(0), UnresolvedTypeLayout<M256i>())
    val r by localStorageL17079C8
    run {
        mm256StoreuEpi8((MutPtr(localStorageL17079C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM256i(r, a)
}

public fun testMmStoreuEpi8() {
    val a = mmSet1Epi8(9)
    val localStorageL17089C8 = AddressableLocal(mmSet1Epi32(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL17089C8
    run {
        mmStoreuEpi8((MutPtr(localStorageL17089C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM128i(r, a)
}

public fun testMm512MaskLoaduEpi16() {
    val src = mm512Set1Epi16(42)
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xAACCE8CA
    val r = mm512MaskLoaduEpi16(src, m, blackBox(p))
    val e = shortArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16, 42, 42, 19, 20, 42, 42, 23, 24, 42, 26, 42, 28, 42, 30, 42, 32)
    val e1 = mm512LoaduEpi16(e.asPtr())
    assertEqM512i(r, e1)
}

public fun testMm512MaskzLoaduEpi16() {
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xAACCE8CA
    val r = mm512MaskzLoaduEpi16(m, blackBox(p))
    val e = shortArrayOf(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16, 0, 0, 19, 20, 0, 0, 23, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    val e1 = mm512LoaduEpi16(e.asPtr())
    assertEqM512i(r, e1)
}

public fun testMm512MaskStoreuEpi16() {
    val localStorageL17133C8 = AddressableArrayLocal.fromShortArray(ShortArray(32) { 42 })
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val a1 = mm512LoaduEpi16(a.asPtr())
    val m = 0xAACCE8CA
    run {
        mm512MaskStoreuEpi16(localStorageL17133C8.elementMutPtr(0), m, a1)
    }
    val e = shortArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16, 42, 42, 19, 20, 42, 42, 23, 24, 42, 26, 42, 28, 42, 30, 42, 32)
    val e1 = mm512LoaduEpi16(e.asPtr())
    assertEqM512i(mm512LoaduEpi16(localStorageL17133C8.elementConstPtr(0)), e1)
}

public fun testMm512MaskLoaduEpi8() {
    val src = mm512Set1Epi8(42)
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val p = a.asPtr()
    val m = 0xFFFF00AACCE8CA
    val r = mm512MaskLoaduEpi8(src, m, blackBox(p))
    val e = byteArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16, 42, 42, 19, 20, 42, 42, 23, 24, 42, 26, 42, 28, 42, 30, 42, 32, 42, 42, 42, 42, 42, 42, 42, 42, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 42, 42, 42, 42, 42, 42, 42, 42)
    val e1 = mm512LoaduEpi8(e.asPtr())
    assertEqM512i(r, e1)
}

public fun testMm512MaskzLoaduEpi8() {
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val p = a.asPtr()
    val m = 0xFFFF00AACCE8CA
    val r = mm512MaskzLoaduEpi8(m, blackBox(p))
    val e = byteArrayOf(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16, 0, 0, 19, 20, 0, 0, 23, 24, 0, 26, 0, 28, 0, 30, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 0, 0, 0, 0, 0, 0, 0, 0)
    val e1 = mm512LoaduEpi8(e.asPtr())
    assertEqM512i(r, e1)
}

public fun testMm512MaskStoreuEpi8() {
    val localStorageL17192C8 = AddressableArrayLocal.fromByteArray(ByteArray(64) { 42 })
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val a1 = mm512LoaduEpi8(a.asPtr())
    val m = 0xFFFF00AACCE8CA
    run {
        mm512MaskStoreuEpi8(localStorageL17192C8.elementMutPtr(0), m, a1)
    }
    val e = byteArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16, 42, 42, 19, 20, 42, 42, 23, 24, 42, 26, 42, 28, 42, 30, 42, 32, 42, 42, 42, 42, 42, 42, 42, 42, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 42, 42, 42, 42, 42, 42, 42, 42)
    val e1 = mm512LoaduEpi8(e.asPtr())
    assertEqM512i(mm512LoaduEpi8(localStorageL17192C8.elementConstPtr(0)), e1)
}

public fun testMm256MaskLoaduEpi16() {
    val src = mm256Set1Epi16(42)
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm256MaskLoaduEpi16(src, m, blackBox(p))
    val e = shortArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    val e1 = mm256LoaduEpi16(e.asPtr())
    assertEqM256i(r, e1)
}

public fun testMm256MaskzLoaduEpi16() {
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mm256MaskzLoaduEpi16(m, blackBox(p))
    val e = shortArrayOf(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16)
    val e1 = mm256LoaduEpi16(e.asPtr())
    assertEqM256i(r, e1)
}

public fun testMm256MaskStoreuEpi16() {
    val localStorageL17239C8 = AddressableArrayLocal.fromShortArray(ShortArray(16) { 42 })
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val a1 = mm256LoaduEpi16(a.asPtr())
    val m = 0xE8CA
    run {
        mm256MaskStoreuEpi16(localStorageL17239C8.elementMutPtr(0), m, a1)
    }
    val e = shortArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    val e1 = mm256LoaduEpi16(e.asPtr())
    assertEqM256i(mm256LoaduEpi16(localStorageL17239C8.elementConstPtr(0)), e1)
}

public fun testMm256MaskLoaduEpi8() {
    val src = mm256Set1Epi8(42)
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xAACCE8CA
    val r = mm256MaskLoaduEpi8(src, m, blackBox(p))
    val e = byteArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16, 42, 42, 19, 20, 42, 42, 23, 24, 42, 26, 42, 28, 42, 30, 42, 32)
    val e1 = mm256LoaduEpi8(e.asPtr())
    assertEqM256i(r, e1)
}

public fun testMm256MaskzLoaduEpi8() {
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = a.asPtr()
    val m = 0xAACCE8CA
    val r = mm256MaskzLoaduEpi8(m, blackBox(p))
    val e = byteArrayOf(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16, 0, 0, 19, 20, 0, 0, 23, 24, 0, 26, 0, 28, 0, 30, 0, 32)
    val e1 = mm256LoaduEpi8(e.asPtr())
    assertEqM256i(r, e1)
}

public fun testMm256MaskStoreuEpi8() {
    val localStorageL17290C8 = AddressableArrayLocal.fromByteArray(ByteArray(32) { 42 })
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val a1 = mm256LoaduEpi8(a.asPtr())
    val m = 0xAACCE8CA
    run {
        mm256MaskStoreuEpi8(localStorageL17290C8.elementMutPtr(0), m, a1)
    }
    val e = byteArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16, 42, 42, 19, 20, 42, 42, 23, 24, 42, 26, 42, 28, 42, 30, 42, 32)
    val e1 = mm256LoaduEpi8(e.asPtr())
    assertEqM256i(mm256LoaduEpi8(localStorageL17290C8.elementConstPtr(0)), e1)
}

public fun testMmMaskLoaduEpi16() {
    val src = mmSet1Epi16(42)
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xCA
    val r = mmMaskLoaduEpi16(src, m, blackBox(p))
    val e = shortArrayOf(42, 2, 42, 4, 42, 42, 7, 8)
    val e1 = mmLoaduEpi16(e.asPtr())
    assertEqM128i(r, e1)
}

public fun testMmMaskzLoaduEpi16() {
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val p = a.asPtr()
    val m = 0xCA
    val r = mmMaskzLoaduEpi16(m, blackBox(p))
    val e = shortArrayOf(0, 2, 0, 4, 0, 0, 7, 8)
    val e1 = mmLoaduEpi16(e.asPtr())
    assertEqM128i(r, e1)
}

public fun testMmMaskStoreuEpi16() {
    val localStorageL17333C8 = AddressableArrayLocal.fromShortArray(ShortArray(8) { 42 })
    val a = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val a1 = mmLoaduEpi16(a.asPtr())
    val m = 0xCA
    mmMaskStoreuEpi16(localStorageL17333C8.elementMutPtr(0), m, a1)
    val e = shortArrayOf(42, 2, 42, 4, 42, 42, 7, 8)
    val e1 = mmLoaduEpi16(e.asPtr())
    assertEqM128i(mmLoaduEpi16(localStorageL17333C8.elementConstPtr(0)), e1)
}

public fun testMmMaskLoaduEpi8() {
    val src = mmSet1Epi8(42)
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mmMaskLoaduEpi8(src, m, blackBox(p))
    val e = byteArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    val e1 = mmLoaduEpi8(e.asPtr())
    assertEqM128i(r, e1)
}

public fun testMmMaskzLoaduEpi8() {
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val p = a.asPtr()
    val m = 0xE8CA
    val r = mmMaskzLoaduEpi8(m, blackBox(p))
    val e = byteArrayOf(0, 2, 0, 4, 0, 0, 7, 8, 0, 0, 0, 12, 0, 14, 15, 16)
    val e1 = mmLoaduEpi8(e.asPtr())
    assertEqM128i(r, e1)
}

public fun testMmMaskStoreuEpi8() {
    val localStorageL17370C8 = AddressableArrayLocal.fromByteArray(ByteArray(16) { 42 })
    val a = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val a1 = mmLoaduEpi8(a.asPtr())
    val m = 0xE8CA
    mmMaskStoreuEpi8(localStorageL17370C8.elementMutPtr(0), m, a1)
    val e = byteArrayOf(42, 2, 42, 4, 42, 42, 7, 8, 42, 42, 42, 12, 42, 14, 15, 16)
    val e1 = mmLoaduEpi8(e.asPtr())
    assertEqM128i(mmLoaduEpi8(localStorageL17370C8.elementConstPtr(0)), e1)
}

public fun testMm512MaddEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaddEpi16(a, b)
    val e = mm512Set1Epi32(2)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaddEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskMaddEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaddEpi16(a, 0xFu, a, b)
    val e = mm512SetEpi32(1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaddEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzMaddEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaddEpi16(0xFu, a, b)
    val e = mm512SetEpi32(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaddEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskMaddEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaddEpi16(a, 0xFu, a, b)
    val e = mm256SetEpi32(1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 1 shl 16 or 1, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaddEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzMaddEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaddEpi16(0xFu, a, b)
    val e = mm256SetEpi32(0, 0, 0, 0, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaddEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskMaddEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaddEpi16(a, 0xFu, a, b)
    val e = mmSetEpi32(2, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaddEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskzMaddEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaddEpi16(0xFu, a, b)
    val e = mmSetEpi32(2, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512MaddubsEpi16() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MaddubsEpi16(a, b)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaddubsEpi16() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val src = mm512Set1Epi16(1)
    val r = mm512MaskMaddubsEpi16(src, 0u, a, b)
    assertEqM512i(r, src)
    val r1 = mm512MaskAddEpi16(src, 0x1u, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ((1 shl 9).toInt() or 2.toInt()).toShort())
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaddubsEpi16() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskzMaddubsEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaddubsEpi16(0xFF00FFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMaddubsEpi16() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val src = mm256Set1Epi16(1)
    val r = mm256MaskMaddubsEpi16(src, 0u, a, b)
    assertEqM256i(r, src)
    val r1 = mm256MaskAddEpi16(src, 0x1u, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ((1 shl 9).toInt() or 2.toInt()).toShort())
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaddubsEpi16() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskzMaddubsEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaddubsEpi16(0xFFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM256i(r1, e)
}

public fun testMmMaskMaddubsEpi16() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val src = mmSet1Epi16(1)
    val r = mmMaskMaddubsEpi16(src, 0u, a, b)
    assertEqM128i(r, src)
    val r1 = mmMaskAddEpi16(src, 0x1u, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 1, 1, 1, ((1 shl 9).toInt() or 2.toInt()).toShort())
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaddubsEpi16() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val r = mmMaskzMaddubsEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaddubsEpi16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 2, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512PacksEpi32() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val b = mm512Set1Epi32(1)
    val r = mm512PacksEpi32(a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM512i(r, e)
}

public fun testMm512MaskPacksEpi32() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val b = mm512Set1Epi32(1 shl 16 or 1)
    val r = mm512MaskPacksEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPacksEpi32(b, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPacksEpi32() {
    val a = mm512Set1Epi32(Int.MAX_VALUE)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskzPacksEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPacksEpi32(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm256MaskPacksEpi32() {
    val a = mm256Set1Epi32(Int.MAX_VALUE)
    val b = mm256Set1Epi32(1 shl 16 or 1)
    val r = mm256MaskPacksEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPacksEpi32(b, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPacksEpi32() {
    val a = mm256Set1Epi32(Int.MAX_VALUE)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskzPacksEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPacksEpi32(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskPacksEpi32() {
    val a = mmSet1Epi32(Int.MAX_VALUE)
    val b = mmSet1Epi32(1 shl 16 or 1)
    val r = mmMaskPacksEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPacksEpi32(b, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPacksEpi32() {
    val a = mmSet1Epi32(Int.MAX_VALUE)
    val b = mmSet1Epi32(1)
    val r = mmMaskzPacksEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPacksEpi32(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512PacksEpi16() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val b = mm512Set1Epi16(1)
    val r = mm512PacksEpi16(a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM512i(r, e)
}

public fun testMm512MaskPacksEpi16() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val b = mm512Set1Epi16(((1 shl 8).toInt() or 1.toInt()).toShort())
    val r = mm512MaskPacksEpi16(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPacksEpi16(b, 0xFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPacksEpi16() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzPacksEpi16(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPacksEpi16(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM512i(r1, e)
}

public fun testMm256MaskPacksEpi16() {
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val b = mm256Set1Epi16(((1 shl 8).toInt() or 1.toInt()).toShort())
    val r = mm256MaskPacksEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPacksEpi16(b, 0xFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPacksEpi16() {
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzPacksEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPacksEpi16(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskPacksEpi16() {
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val b = mmSet1Epi16(((1 shl 8).toInt() or 1.toInt()).toShort())
    val r = mmMaskPacksEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPacksEpi16(b, 0xFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPacksEpi16() {
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val b = mmSet1Epi16(1)
    val r = mmMaskzPacksEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPacksEpi16(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512PackusEpi32() {
    val a = mm512Set1Epi32(-1)
    val b = mm512Set1Epi32(1)
    val r = mm512PackusEpi32(a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskPackusEpi32() {
    val a = mm512Set1Epi32(-1)
    val b = mm512Set1Epi32(1 shl 16 or 1)
    val r = mm512MaskPackusEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPackusEpi32(b, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPackusEpi32() {
    val a = mm512Set1Epi32(-1)
    val b = mm512Set1Epi32(1)
    val r = mm512MaskzPackusEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPackusEpi32(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskPackusEpi32() {
    val a = mm256Set1Epi32(-1)
    val b = mm256Set1Epi32(1 shl 16 or 1)
    val r = mm256MaskPackusEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPackusEpi32(b, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPackusEpi32() {
    val a = mm256Set1Epi32(-1)
    val b = mm256Set1Epi32(1)
    val r = mm256MaskzPackusEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPackusEpi32(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskPackusEpi32() {
    val a = mmSet1Epi32(-1)
    val b = mmSet1Epi32(1 shl 16 or 1)
    val r = mmMaskPackusEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPackusEpi32(b, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPackusEpi32() {
    val a = mmSet1Epi32(-1)
    val b = mmSet1Epi32(1)
    val r = mmMaskzPackusEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPackusEpi32(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512PackusEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16(1)
    val r = mm512PackusEpi16(a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskPackusEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16(((1 shl 8).toInt() or 1.toInt()).toShort())
    val r = mm512MaskPackusEpi16(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPackusEpi16(b, 0xFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPackusEpi16() {
    val a = mm512Set1Epi16((-1).toShort())
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzPackusEpi16(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPackusEpi16(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskPackusEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16(((1 shl 8).toInt() or 1.toInt()).toShort())
    val r = mm256MaskPackusEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPackusEpi16(b, 0xFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPackusEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzPackusEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPackusEpi16(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskPackusEpi16() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16(((1 shl 8).toInt() or 1.toInt()).toShort())
    val r = mmMaskPackusEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPackusEpi16(b, 0xFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPackusEpi16() {
    val a = mmSet1Epi16((-1).toShort())
    val b = mmSet1Epi16(1)
    val r = mmMaskzPackusEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPackusEpi16(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512AvgEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512AvgEpu16(a, b)
    val e = mm512Set1Epi16(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskAvgEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskAvgEpu16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAvgEpu16(a, 0xFu, a, b)
    val e = mm512SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAvgEpu16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(1)
    val r = mm512MaskzAvgEpu16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAvgEpu16(0xFu, a, b)
    val e = mm512SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAvgEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskAvgEpu16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAvgEpu16(a, 0xFu, a, b)
    val e = mm256SetEpi16(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAvgEpu16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(1)
    val r = mm256MaskzAvgEpu16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAvgEpu16(0xFu, a, b)
    val e = mm256SetEpi16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskAvgEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskAvgEpu16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAvgEpu16(a, 0xFu, a, b)
    val e = mmSetEpi16(1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAvgEpu16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(1)
    val r = mmMaskzAvgEpu16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAvgEpu16(0xFu, a, b)
    val e = mmSetEpi16(0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512AvgEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512AvgEpu8(a, b)
    val e = mm512Set1Epi8(1)
    assertEqM512i(r, e)
}

public fun testMm512MaskAvgEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskAvgEpu8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAvgEpu8(a, 0xFuL, a, b)
    val e = mm512SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAvgEpu8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskzAvgEpu8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAvgEpu8(0xFuL, a, b)
    val e = mm512SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAvgEpu8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskAvgEpu8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAvgEpu8(a, 0xFu, a, b)
    val e = mm256SetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAvgEpu8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskzAvgEpu8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAvgEpu8(0xFu, a, b)
    val e = mm256SetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskAvgEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val r = mmMaskAvgEpu8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAvgEpu8(a, 0xFu, a, b)
    val e = mmSetEpi8(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAvgEpu8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(1)
    val r = mmMaskzAvgEpu8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAvgEpu8(0xFu, a, b)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512SllEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mm512SllEpi16(a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSllEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mm512MaskSllEpi16(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSllEpi16(a, 0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSllEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mm512MaskzSllEpi16(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSllEpi16(0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSllEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mm256MaskSllEpi16(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSllEpi16(a, 0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSllEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mm256MaskzSllEpi16(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSllEpi16(0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSllEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskSllEpi16(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSllEpi16(a, 0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSllEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskzSllEpi16(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSllEpi16(0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SlliEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val r = mm512SlliEpi16(a, 1)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSlliEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val r = mm512MaskSlliEpi16(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskSlliEpi16(a, 0xFFFFFFFFu, a, 1)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSlliEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val r = mm512MaskzSlliEpi16(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSlliEpi16(0xFFFFFFFFu, a, 1)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSlliEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val r = mm256MaskSlliEpi16(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSlliEpi16(a, 0xFFFFu, a, 1)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSlliEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val r = mm256MaskzSlliEpi16(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSlliEpi16(0xFFFFu, a, 1)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSlliEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val r = mmMaskSlliEpi16(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSlliEpi16(a, 0xFFu, a, 1)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSlliEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val r = mmMaskzSlliEpi16(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSlliEpi16(0xFFu, a, 1)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SllvEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val count = mm512Set1Epi16(2)
    val r = mm512SllvEpi16(a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSllvEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val count = mm512Set1Epi16(2)
    val r = mm512MaskSllvEpi16(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSllvEpi16(a, 0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSllvEpi16() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val count = mm512Set1Epi16(2)
    val r = mm512MaskzSllvEpi16(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSllvEpi16(0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256SllvEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val count = mm256Set1Epi16(2)
    val r = mm256SllvEpi16(a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r, e)
}

public fun testMm256MaskSllvEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val count = mm256Set1Epi16(2)
    val r = mm256MaskSllvEpi16(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSllvEpi16(a, 0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSllvEpi16() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val count = mm256Set1Epi16(2)
    val r = mm256MaskzSllvEpi16(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSllvEpi16(0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmSllvEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mmSllvEpi16(a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r, e)
}

public fun testMmMaskSllvEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskSllvEpi16(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSllvEpi16(a, 0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSllvEpi16() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskzSllvEpi16(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSllvEpi16(0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SrlEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mm512SrlEpi16(a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrlEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mm512MaskSrlEpi16(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrlEpi16(a, 0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrlEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mm512MaskzSrlEpi16(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrlEpi16(0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrlEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mm256MaskSrlEpi16(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrlEpi16(a, 0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrlEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mm256MaskzSrlEpi16(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrlEpi16(0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrlEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskSrlEpi16(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSrlEpi16(a, 0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrlEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskzSrlEpi16(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrlEpi16(0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SrliEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val r = mm512SrliEpi16(a, 2)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrliEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val r = mm512MaskSrliEpi16(a, 0u, a, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrliEpi16(a, 0xFFFFFFFFu, a, 2)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrliEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val r = mm512MaskzSrliEpi16(0u, a, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrliEpi16(0xFFFFFFFFu, a, 2)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrliEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val r = mm256MaskSrliEpi16(a, 0u, a, 2)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrliEpi16(a, 0xFFFFu, a, 2)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrliEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val r = mm256MaskzSrliEpi16(0u, a, 2)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrliEpi16(0xFFFFu, a, 2)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrliEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val r = mmMaskSrliEpi16(a, 0u, a, 2)
    assertEqM128i(r, a)
    val r1 = mmMaskSrliEpi16(a, 0xFFu, a, 2)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrliEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val r = mmMaskzSrliEpi16(0u, a, 2)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrliEpi16(0xFFu, a, 2)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SrlvEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val count = mm512Set1Epi16(2)
    val r = mm512SrlvEpi16(a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrlvEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val count = mm512Set1Epi16(2)
    val r = mm512MaskSrlvEpi16(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrlvEpi16(a, 0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrlvEpi16() {
    val a = mm512Set1Epi16((1 shl 1).toShort())
    val count = mm512Set1Epi16(2)
    val r = mm512MaskzSrlvEpi16(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrlvEpi16(0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256SrlvEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val count = mm256Set1Epi16(2)
    val r = mm256SrlvEpi16(a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r, e)
}

public fun testMm256MaskSrlvEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val count = mm256Set1Epi16(2)
    val r = mm256MaskSrlvEpi16(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrlvEpi16(a, 0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrlvEpi16() {
    val a = mm256Set1Epi16((1 shl 1).toShort())
    val count = mm256Set1Epi16(2)
    val r = mm256MaskzSrlvEpi16(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrlvEpi16(0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmSrlvEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mmSrlvEpi16(a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r, e)
}

public fun testMmMaskSrlvEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskSrlvEpi16(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSrlvEpi16(a, 0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrlvEpi16() {
    val a = mmSet1Epi16((1 shl 1).toShort())
    val count = mmSet1Epi16(2)
    val r = mmMaskzSrlvEpi16(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrlvEpi16(0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SraEpi16() {
    val a = mm512Set1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mm512SraEpi16(a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r, e)
}

public fun testMm512MaskSraEpi16() {
    val a = mm512Set1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mm512MaskSraEpi16(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSraEpi16(a, 0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSraEpi16() {
    val a = mm512Set1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mm512MaskzSraEpi16(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSraEpi16(0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(0)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSraEpi16() {
    val a = mm256Set1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mm256MaskSraEpi16(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSraEpi16(a, 0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSraEpi16() {
    val a = mm256Set1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mm256MaskzSraEpi16(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSraEpi16(0xFFFFu, a, count)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r1, e)
}

public fun testMmMaskSraEpi16() {
    val a = mmSet1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mmMaskSraEpi16(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSraEpi16(a, 0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSraEpi16() {
    val a = mmSet1Epi16(8)
    val count = mmSet1Epi16(1)
    val r = mmMaskzSraEpi16(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSraEpi16(0xFFu, a, count)
    val e = mmSet1Epi16(0)
    assertEqM128i(r1, e)
}

public fun testMm512SraiEpi16() {
    val a = mm512Set1Epi16(8)
    val r = mm512SraiEpi16(a, 2)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r, e)
}

public fun testMm512MaskSraiEpi16() {
    val a = mm512Set1Epi16(8)
    val r = mm512MaskSraiEpi16(a, 0u, a, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskSraiEpi16(a, 0xFFFFFFFFu, a, 2)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSraiEpi16() {
    val a = mm512Set1Epi16(8)
    val r = mm512MaskzSraiEpi16(0u, a, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSraiEpi16(0xFFFFFFFFu, a, 2)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSraiEpi16() {
    val a = mm256Set1Epi16(8)
    val r = mm256MaskSraiEpi16(a, 0u, a, 2)
    assertEqM256i(r, a)
    val r1 = mm256MaskSraiEpi16(a, 0xFFFFu, a, 2)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSraiEpi16() {
    val a = mm256Set1Epi16(8)
    val r = mm256MaskzSraiEpi16(0u, a, 2)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSraiEpi16(0xFFFFu, a, 2)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMmMaskSraiEpi16() {
    val a = mmSet1Epi16(8)
    val r = mmMaskSraiEpi16(a, 0u, a, 2)
    assertEqM128i(r, a)
    val r1 = mmMaskSraiEpi16(a, 0xFFu, a, 2)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSraiEpi16() {
    val a = mmSet1Epi16(8)
    val r = mmMaskzSraiEpi16(0u, a, 2)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSraiEpi16(0xFFu, a, 2)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMm512SravEpi16() {
    val a = mm512Set1Epi16(8)
    val count = mm512Set1Epi16(2)
    val r = mm512SravEpi16(a, count)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r, e)
}

public fun testMm512MaskSravEpi16() {
    val a = mm512Set1Epi16(8)
    val count = mm512Set1Epi16(2)
    val r = mm512MaskSravEpi16(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSravEpi16(a, 0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSravEpi16() {
    val a = mm512Set1Epi16(8)
    val count = mm512Set1Epi16(2)
    val r = mm512MaskzSravEpi16(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSravEpi16(0xFFFFFFFFu, a, count)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm256SravEpi16() {
    val a = mm256Set1Epi16(8)
    val count = mm256Set1Epi16(2)
    val r = mm256SravEpi16(a, count)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r, e)
}

public fun testMm256MaskSravEpi16() {
    val a = mm256Set1Epi16(8)
    val count = mm256Set1Epi16(2)
    val r = mm256MaskSravEpi16(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSravEpi16(a, 0xFFFFu, a, count)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSravEpi16() {
    val a = mm256Set1Epi16(8)
    val count = mm256Set1Epi16(2)
    val r = mm256MaskzSravEpi16(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSravEpi16(0xFFFFu, a, count)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMmSravEpi16() {
    val a = mmSet1Epi16(8)
    val count = mmSet1Epi16(2)
    val r = mmSravEpi16(a, count)
    val e = mmSet1Epi16(2)
    assertEqM128i(r, e)
}

public fun testMmMaskSravEpi16() {
    val a = mmSet1Epi16(8)
    val count = mmSet1Epi16(2)
    val r = mmMaskSravEpi16(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSravEpi16(a, 0xFFu, a, count)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSravEpi16() {
    val a = mmSet1Epi16(8)
    val count = mmSet1Epi16(2)
    val r = mmMaskzSravEpi16(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSravEpi16(0xFFu, a, count)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMm512Permutex2varEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm512SetEpi16(1, (1 shl 5).toShort(), 2, (1 shl 5).toShort(), 3, (1 shl 5).toShort(), 4, (1 shl 5).toShort(), 5, (1 shl 5).toShort(), 6, (1 shl 5).toShort(), 7, (1 shl 5).toShort(), 8, (1 shl 5).toShort(), 9, (1 shl 5).toShort(), 10, (1 shl 5).toShort(), 11, (1 shl 5).toShort(), 12, (1 shl 5).toShort(), 13, (1 shl 5).toShort(), 14, (1 shl 5).toShort(), 15, (1 shl 5).toShort(), 16, (1 shl 5).toShort())
    val b = mm512Set1Epi16(100)
    val r = mm512Permutex2varEpi16(a, idx, b)
    val e = mm512SetEpi16(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutex2varEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm512SetEpi16(1, (1 shl 5).toShort(), 2, (1 shl 5).toShort(), 3, (1 shl 5).toShort(), 4, (1 shl 5).toShort(), 5, (1 shl 5).toShort(), 6, (1 shl 5).toShort(), 7, (1 shl 5).toShort(), 8, (1 shl 5).toShort(), 9, (1 shl 5).toShort(), 10, (1 shl 5).toShort(), 11, (1 shl 5).toShort(), 12, (1 shl 5).toShort(), 13, (1 shl 5).toShort(), 14, (1 shl 5).toShort(), 15, (1 shl 5).toShort(), 16, (1 shl 5).toShort())
    val b = mm512Set1Epi16(100)
    val r = mm512MaskPermutex2varEpi16(a, 0u, idx, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutex2varEpi16(a, 0xFFFFFFFFu, idx, b)
    val e = mm512SetEpi16(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutex2varEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm512SetEpi16(1, (1 shl 5).toShort(), 2, (1 shl 5).toShort(), 3, (1 shl 5).toShort(), 4, (1 shl 5).toShort(), 5, (1 shl 5).toShort(), 6, (1 shl 5).toShort(), 7, (1 shl 5).toShort(), 8, (1 shl 5).toShort(), 9, (1 shl 5).toShort(), 10, (1 shl 5).toShort(), 11, (1 shl 5).toShort(), 12, (1 shl 5).toShort(), 13, (1 shl 5).toShort(), 14, (1 shl 5).toShort(), 15, (1 shl 5).toShort(), 16, (1 shl 5).toShort())
    val b = mm512Set1Epi16(100)
    val r = mm512MaskzPermutex2varEpi16(0u, a, idx, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutex2varEpi16(0xFFFFFFFFu, a, idx, b)
    val e = mm512SetEpi16(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM512i(r1, e)
}

public fun testMm512Mask2Permutex2varEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val idx = mm512SetEpi16(1, (1 shl 5).toShort(), 2, (1 shl 5).toShort(), 3, (1 shl 5).toShort(), 4, (1 shl 5).toShort(), 5, (1 shl 5).toShort(), 6, (1 shl 5).toShort(), 7, (1 shl 5).toShort(), 8, (1 shl 5).toShort(), 9, (1 shl 5).toShort(), 10, (1 shl 5).toShort(), 11, (1 shl 5).toShort(), 12, (1 shl 5).toShort(), 13, (1 shl 5).toShort(), 14, (1 shl 5).toShort(), 15, (1 shl 5).toShort(), 16, (1 shl 5).toShort())
    val b = mm512Set1Epi16(100)
    val r = mm512Mask2Permutex2varEpi16(a, idx, 0u, b)
    assertEqM512i(r, idx)
    val r1 = mm512Mask2Permutex2varEpi16(a, idx, 0xFFFFFFFFu, b)
    val e = mm512SetEpi16(30, 100, 29, 100, 28, 100, 27, 100, 26, 100, 25, 100, 24, 100, 23, 100, 22, 100, 21, 100, 20, 100, 19, 100, 18, 100, 17, 100, 16, 100, 15, 100)
    assertEqM512i(r1, e)
}

public fun testMm256Permutex2varEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm256SetEpi16(1, (1 shl 4).toShort(), 2, (1 shl 4).toShort(), 3, (1 shl 4).toShort(), 4, (1 shl 4).toShort(), 5, (1 shl 4).toShort(), 6, (1 shl 4).toShort(), 7, (1 shl 4).toShort(), 8, (1 shl 4).toShort())
    val b = mm256Set1Epi16(100)
    val r = mm256Permutex2varEpi16(a, idx, b)
    val e = mm256SetEpi16(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutex2varEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm256SetEpi16(1, (1 shl 4).toShort(), 2, (1 shl 4).toShort(), 3, (1 shl 4).toShort(), 4, (1 shl 4).toShort(), 5, (1 shl 4).toShort(), 6, (1 shl 4).toShort(), 7, (1 shl 4).toShort(), 8, (1 shl 4).toShort())
    val b = mm256Set1Epi16(100)
    val r = mm256MaskPermutex2varEpi16(a, 0u, idx, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutex2varEpi16(a, 0xFFFFu, idx, b)
    val e = mm256SetEpi16(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutex2varEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm256SetEpi16(1, (1 shl 4).toShort(), 2, (1 shl 4).toShort(), 3, (1 shl 4).toShort(), 4, (1 shl 4).toShort(), 5, (1 shl 4).toShort(), 6, (1 shl 4).toShort(), 7, (1 shl 4).toShort(), 8, (1 shl 4).toShort())
    val b = mm256Set1Epi16(100)
    val r = mm256MaskzPermutex2varEpi16(0u, a, idx, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutex2varEpi16(0xFFFFu, a, idx, b)
    val e = mm256SetEpi16(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM256i(r1, e)
}

public fun testMm256Mask2Permutex2varEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val idx = mm256SetEpi16(1, (1 shl 4).toShort(), 2, (1 shl 4).toShort(), 3, (1 shl 4).toShort(), 4, (1 shl 4).toShort(), 5, (1 shl 4).toShort(), 6, (1 shl 4).toShort(), 7, (1 shl 4).toShort(), 8, (1 shl 4).toShort())
    val b = mm256Set1Epi16(100)
    val r = mm256Mask2Permutex2varEpi16(a, idx, 0u, b)
    assertEqM256i(r, idx)
    val r1 = mm256Mask2Permutex2varEpi16(a, idx, 0xFFFFu, b)
    val e = mm256SetEpi16(14, 100, 13, 100, 12, 100, 11, 100, 10, 100, 9, 100, 8, 100, 7, 100)
    assertEqM256i(r1, e)
}

public fun testMmPermutex2varEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mmSetEpi16(1, (1 shl 3).toShort(), 2, (1 shl 3).toShort(), 3, (1 shl 3).toShort(), 4, (1 shl 3).toShort())
    val b = mmSet1Epi16(100)
    val r = mmPermutex2varEpi16(a, idx, b)
    val e = mmSetEpi16(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM128i(r, e)
}

public fun testMmMaskPermutex2varEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mmSetEpi16(1, (1 shl 3).toShort(), 2, (1 shl 3).toShort(), 3, (1 shl 3).toShort(), 4, (1 shl 3).toShort())
    val b = mmSet1Epi16(100)
    val r = mmMaskPermutex2varEpi16(a, 0u, idx, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPermutex2varEpi16(a, 0xFFu, idx, b)
    val e = mmSetEpi16(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPermutex2varEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mmSetEpi16(1, (1 shl 3).toShort(), 2, (1 shl 3).toShort(), 3, (1 shl 3).toShort(), 4, (1 shl 3).toShort())
    val b = mmSet1Epi16(100)
    val r = mmMaskzPermutex2varEpi16(0u, a, idx, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPermutex2varEpi16(0xFFu, a, idx, b)
    val e = mmSetEpi16(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM128i(r1, e)
}

public fun testMmMask2Permutex2varEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val idx = mmSetEpi16(1, (1 shl 3).toShort(), 2, (1 shl 3).toShort(), 3, (1 shl 3).toShort(), 4, (1 shl 3).toShort())
    val b = mmSet1Epi16(100)
    val r = mmMask2Permutex2varEpi16(a, idx, 0u, b)
    assertEqM128i(r, idx)
    val r1 = mmMask2Permutex2varEpi16(a, idx, 0xFFu, b)
    val e = mmSetEpi16(6, 100, 5, 100, 4, 100, 3, 100)
    assertEqM128i(r1, e)
}

public fun testMm512PermutexvarEpi16() {
    val idx = mm512Set1Epi16(1)
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512PermutexvarEpi16(idx, a)
    val e = mm512Set1Epi16(30)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutexvarEpi16() {
    val idx = mm512Set1Epi16(1)
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskPermutexvarEpi16(a, 0u, idx, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutexvarEpi16(a, 0xFFFFFFFFu, idx, a)
    val e = mm512Set1Epi16(30)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutexvarEpi16() {
    val idx = mm512Set1Epi16(1)
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskzPermutexvarEpi16(0u, idx, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutexvarEpi16(0xFFFFFFFFu, idx, a)
    val e = mm512Set1Epi16(30)
    assertEqM512i(r1, e)
}

public fun testMm256PermutexvarEpi16() {
    val idx = mm256Set1Epi16(1)
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256PermutexvarEpi16(idx, a)
    val e = mm256Set1Epi16(14)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutexvarEpi16() {
    val idx = mm256Set1Epi16(1)
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskPermutexvarEpi16(a, 0u, idx, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutexvarEpi16(a, 0xFFFFu, idx, a)
    val e = mm256Set1Epi16(14)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutexvarEpi16() {
    val idx = mm256Set1Epi16(1)
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzPermutexvarEpi16(0u, idx, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutexvarEpi16(0xFFFFu, idx, a)
    val e = mm256Set1Epi16(14)
    assertEqM256i(r1, e)
}

public fun testMmPermutexvarEpi16() {
    val idx = mmSet1Epi16(1)
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmPermutexvarEpi16(idx, a)
    val e = mmSet1Epi16(6)
    assertEqM128i(r, e)
}

public fun testMmMaskPermutexvarEpi16() {
    val idx = mmSet1Epi16(1)
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskPermutexvarEpi16(a, 0u, idx, a)
    assertEqM128i(r, a)
    val r1 = mmMaskPermutexvarEpi16(a, 0xFFu, idx, a)
    val e = mmSet1Epi16(6)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPermutexvarEpi16() {
    val idx = mmSet1Epi16(1)
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskzPermutexvarEpi16(0u, idx, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPermutexvarEpi16(0xFFu, idx, a)
    val e = mmSet1Epi16(6)
    assertEqM128i(r1, e)
}

public fun testMm512MaskBlendEpi16() {
    val a = mm512Set1Epi16(1)
    val b = mm512Set1Epi16(2)
    val r = mm512MaskBlendEpi16(0xFF00FF00u, a, b)
    val e = mm512SetEpi16(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm256MaskBlendEpi16() {
    val a = mm256Set1Epi16(1)
    val b = mm256Set1Epi16(2)
    val r = mm256MaskBlendEpi16(0xFF00u, a, b)
    val e = mm256SetEpi16(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r, e)
}

public fun testMmMaskBlendEpi16() {
    val a = mmSet1Epi16(1)
    val b = mmSet1Epi16(2)
    val r = mmMaskBlendEpi16(0xF0u, a, b)
    val e = mmSetEpi16(2, 2, 2, 2, 1, 1, 1, 1)
    assertEqM128i(r, e)
}

public fun testMm512MaskBlendEpi8() {
    val a = mm512Set1Epi8(1)
    val b = mm512Set1Epi8(2)
    val r = mm512MaskBlendEpi8(0xFF00FF00FF00FF00uL, a, b)
    val e = mm512SetEpi8(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm256MaskBlendEpi8() {
    val a = mm256Set1Epi8(1)
    val b = mm256Set1Epi8(2)
    val r = mm256MaskBlendEpi8(0xFF00FF00u, a, b)
    val e = mm256SetEpi8(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM256i(r, e)
}

public fun testMmMaskBlendEpi8() {
    val a = mmSet1Epi8(1)
    val b = mmSet1Epi8(2)
    val r = mmMaskBlendEpi8(0xFF00u, a, b)
    val e = mmSetEpi8(2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1)
    assertEqM128i(r, e)
}

public fun testMm512BroadcastwEpi16() {
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512BroadcastwEpi16(a)
    val e = mm512Set1Epi16(24)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastwEpi16() {
    val src = mm512Set1Epi16(1)
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512MaskBroadcastwEpi16(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskBroadcastwEpi16(src, 0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(24)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzBroadcastwEpi16() {
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm512MaskzBroadcastwEpi16(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzBroadcastwEpi16(0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(24)
    assertEqM512i(r1, e)
}

public fun testMm256MaskBroadcastwEpi16() {
    val src = mm256Set1Epi16(1)
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm256MaskBroadcastwEpi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskBroadcastwEpi16(src, 0xFFFFu, a)
    val e = mm256Set1Epi16(24)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzBroadcastwEpi16() {
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mm256MaskzBroadcastwEpi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzBroadcastwEpi16(0xFFFFu, a)
    val e = mm256Set1Epi16(24)
    assertEqM256i(r1, e)
}

public fun testMmMaskBroadcastwEpi16() {
    val src = mmSet1Epi16(1)
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mmMaskBroadcastwEpi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskBroadcastwEpi16(src, 0xFFu, a)
    val e = mmSet1Epi16(24)
    assertEqM128i(r1, e)
}

public fun testMmMaskzBroadcastwEpi16() {
    val a = mmSetEpi16(17, 18, 19, 20, 21, 22, 23, 24)
    val r = mmMaskzBroadcastwEpi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzBroadcastwEpi16(0xFFu, a)
    val e = mmSet1Epi16(24)
    assertEqM128i(r1, e)
}

public fun testMm512BroadcastbEpi8() {
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512BroadcastbEpi8(a)
    val e = mm512Set1Epi8(32)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastbEpi8() {
    val src = mm512Set1Epi8(1)
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskBroadcastbEpi8(src, 0uL, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskBroadcastbEpi8(src, 0xFFFFFFFFFFFFFFFFuL, a)
    val e = mm512Set1Epi8(32)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzBroadcastbEpi8() {
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm512MaskzBroadcastbEpi8(0uL, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzBroadcastbEpi8(0xFFFFFFFFFFFFFFFFuL, a)
    val e = mm512Set1Epi8(32)
    assertEqM512i(r1, e)
}

public fun testMm256MaskBroadcastbEpi8() {
    val src = mm256Set1Epi8(1)
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm256MaskBroadcastbEpi8(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskBroadcastbEpi8(src, 0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(32)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzBroadcastbEpi8() {
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm256MaskzBroadcastbEpi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzBroadcastbEpi8(0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(32)
    assertEqM256i(r1, e)
}

public fun testMmMaskBroadcastbEpi8() {
    val src = mmSet1Epi8(1)
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mmMaskBroadcastbEpi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskBroadcastbEpi8(src, 0xFFFFu, a)
    val e = mmSet1Epi8(32)
    assertEqM128i(r1, e)
}

public fun testMmMaskzBroadcastbEpi8() {
    val a = mmSetEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mmMaskzBroadcastbEpi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzBroadcastbEpi8(0xFFFFu, a)
    val e = mmSet1Epi8(32)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackhiEpi16() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm512SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512UnpackhiEpi16(a, b)
    val e = mm512SetEpi16(33, 1, 34, 2, 35, 3, 36, 4, 41, 9, 42, 10, 43, 11, 44, 12, 49, 17, 50, 18, 51, 19, 52, 20, 57, 25, 58, 26, 59, 27, 60, 28)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackhiEpi16() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm512SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512MaskUnpackhiEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackhiEpi16(a, 0xFFFFFFFFu, a, b)
    val e = mm512SetEpi16(33, 1, 34, 2, 35, 3, 36, 4, 41, 9, 42, 10, 43, 11, 44, 12, 49, 17, 50, 18, 51, 19, 52, 20, 57, 25, 58, 26, 59, 27, 60, 28)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackhiEpi16() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm512SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512MaskzUnpackhiEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackhiEpi16(0xFFFFFFFFu, a, b)
    val e = mm512SetEpi16(33, 1, 34, 2, 35, 3, 36, 4, 41, 9, 42, 10, 43, 11, 44, 12, 49, 17, 50, 18, 51, 19, 52, 20, 57, 25, 58, 26, 59, 27, 60, 28)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackhiEpi16() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48)
    val r = mm256MaskUnpackhiEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackhiEpi16(a, 0xFFFFu, a, b)
    val e = mm256SetEpi16(33, 1, 34, 2, 35, 3, 36, 4, 41, 9, 42, 10, 43, 11, 44, 12)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackhiEpi16() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48)
    val r = mm256MaskzUnpackhiEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackhiEpi16(0xFFFFu, a, b)
    val e = mm256SetEpi16(33, 1, 34, 2, 35, 3, 36, 4, 41, 9, 42, 10, 43, 11, 44, 12)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackhiEpi16() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi16(33, 34, 35, 36, 37, 38, 39, 40)
    val r = mmMaskUnpackhiEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackhiEpi16(a, 0xFFu, a, b)
    val e = mmSetEpi16(33, 1, 34, 2, 35, 3, 36, 4)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackhiEpi16() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi16(33, 34, 35, 36, 37, 38, 39, 40)
    val r = mmMaskzUnpackhiEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackhiEpi16(0xFFu, a, b)
    val e = mmSetEpi16(33, 1, 34, 2, 35, 3, 36, 4)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackhiEpi8() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val b = mm512SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 0)
    val r = mm512UnpackhiEpi8(a, b)
    val e = mm512SetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8, 81, 17, 82, 18, 83, 19, 84, 20, 85, 21, 86, 22, 87, 23, 88, 24, 97, 33, 98, 34, 99, 35, 100, 36, 101, 37, 102, 38, 103, 39, 104, 40, 113, 49, 114, 50, 115, 51, 116, 52, 117, 53, 118, 54, 119, 55, 120, 56)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackhiEpi8() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val b = mm512SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 0)
    val r = mm512MaskUnpackhiEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackhiEpi8(a, 0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512SetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8, 81, 17, 82, 18, 83, 19, 84, 20, 85, 21, 86, 22, 87, 23, 88, 24, 97, 33, 98, 34, 99, 35, 100, 36, 101, 37, 102, 38, 103, 39, 104, 40, 113, 49, 114, 50, 115, 51, 116, 52, 117, 53, 118, 54, 119, 55, 120, 56)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackhiEpi8() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val b = mm512SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 0)
    val r = mm512MaskzUnpackhiEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackhiEpi8(0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512SetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8, 81, 17, 82, 18, 83, 19, 84, 20, 85, 21, 86, 22, 87, 23, 88, 24, 97, 33, 98, 34, 99, 35, 100, 36, 101, 37, 102, 38, 103, 39, 104, 40, 113, 49, 114, 50, 115, 51, 116, 52, 117, 53, 118, 54, 119, 55, 120, 56)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackhiEpi8() {
    val a = mm256SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm256SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96)
    val r = mm256MaskUnpackhiEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackhiEpi8(a, 0xFFFFFFFFu, a, b)
    val e = mm256SetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8, 81, 17, 82, 18, 83, 19, 84, 20, 85, 21, 86, 22, 87, 23, 88, 24)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackhiEpi8() {
    val a = mm256SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm256SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96)
    val r = mm256MaskzUnpackhiEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackhiEpi8(0xFFFFFFFFu, a, b)
    val e = mm256SetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8, 81, 17, 82, 18, 83, 19, 84, 20, 85, 21, 86, 22, 87, 23, 88, 24)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackhiEpi8() {
    val a = mmSetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80)
    val r = mmMaskUnpackhiEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackhiEpi8(a, 0xFFFFu, a, b)
    val e = mmSetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackhiEpi8() {
    val a = mmSetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80)
    val r = mmMaskzUnpackhiEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackhiEpi8(0xFFFFu, a, b)
    val e = mmSetEpi8(65, 1, 66, 2, 67, 3, 68, 4, 69, 5, 70, 6, 71, 7, 72, 8)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackloEpi16() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm512SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512UnpackloEpi16(a, b)
    val e = mm512SetEpi16(37, 5, 38, 6, 39, 7, 40, 8, 45, 13, 46, 14, 47, 15, 48, 16, 53, 21, 54, 22, 55, 23, 56, 24, 61, 29, 62, 30, 63, 31, 64, 32)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackloEpi16() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm512SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512MaskUnpackloEpi16(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackloEpi16(a, 0xFFFFFFFFu, a, b)
    val e = mm512SetEpi16(37, 5, 38, 6, 39, 7, 40, 8, 45, 13, 46, 14, 47, 15, 48, 16, 53, 21, 54, 22, 55, 23, 56, 24, 61, 29, 62, 30, 63, 31, 64, 32)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackloEpi16() {
    val a = mm512SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm512SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512MaskzUnpackloEpi16(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackloEpi16(0xFFFFFFFFu, a, b)
    val e = mm512SetEpi16(37, 5, 38, 6, 39, 7, 40, 8, 45, 13, 46, 14, 47, 15, 48, 16, 53, 21, 54, 22, 55, 23, 56, 24, 61, 29, 62, 30, 63, 31, 64, 32)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackloEpi16() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48)
    val r = mm256MaskUnpackloEpi16(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackloEpi16(a, 0xFFFFu, a, b)
    val e = mm256SetEpi16(37, 5, 38, 6, 39, 7, 40, 8, 45, 13, 46, 14, 47, 15, 48, 16)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackloEpi16() {
    val a = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm256SetEpi16(33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48)
    val r = mm256MaskzUnpackloEpi16(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackloEpi16(0xFFFFu, a, b)
    val e = mm256SetEpi16(37, 5, 38, 6, 39, 7, 40, 8, 45, 13, 46, 14, 47, 15, 48, 16)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackloEpi16() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi16(33, 34, 35, 36, 37, 38, 39, 40)
    val r = mmMaskUnpackloEpi16(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackloEpi16(a, 0xFFu, a, b)
    val e = mmSetEpi16(37, 5, 38, 6, 39, 7, 40, 8)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackloEpi16() {
    val a = mmSetEpi16(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mmSetEpi16(33, 34, 35, 36, 37, 38, 39, 40)
    val r = mmMaskzUnpackloEpi16(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackloEpi16(0xFFu, a, b)
    val e = mmSetEpi16(37, 5, 38, 6, 39, 7, 40, 8)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackloEpi8() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val b = mm512SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 0)
    val r = mm512UnpackloEpi8(a, b)
    val e = mm512SetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16, 89, 25, 90, 26, 91, 27, 92, 28, 93, 29, 94, 30, 95, 31, 96, 32, 105, 41, 106, 42, 107, 43, 108, 44, 109, 45, 110, 46, 111, 47, 112, 48, 121, 57, 122, 58, 123, 59, 124, 60, 125, 61, 126, 62, 127, 63, 0, 64)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackloEpi8() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val b = mm512SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 0)
    val r = mm512MaskUnpackloEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackloEpi8(a, 0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512SetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16, 89, 25, 90, 26, 91, 27, 92, 28, 93, 29, 94, 30, 95, 31, 96, 32, 105, 41, 106, 42, 107, 43, 108, 44, 109, 45, 110, 46, 111, 47, 112, 48, 121, 57, 122, 58, 123, 59, 124, 60, 125, 61, 126, 62, 127, 63, 0, 64)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackloEpi8() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val b = mm512SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 0)
    val r = mm512MaskzUnpackloEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackloEpi8(0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512SetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16, 89, 25, 90, 26, 91, 27, 92, 28, 93, 29, 94, 30, 95, 31, 96, 32, 105, 41, 106, 42, 107, 43, 108, 44, 109, 45, 110, 46, 111, 47, 112, 48, 121, 57, 122, 58, 123, 59, 124, 60, 125, 61, 126, 62, 127, 63, 0, 64)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackloEpi8() {
    val a = mm256SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm256SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96)
    val r = mm256MaskUnpackloEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackloEpi8(a, 0xFFFFFFFFu, a, b)
    val e = mm256SetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16, 89, 25, 90, 26, 91, 27, 92, 28, 93, 29, 94, 30, 95, 31, 96, 32)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackloEpi8() {
    val a = mm256SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm256SetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96)
    val r = mm256MaskzUnpackloEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackloEpi8(0xFFFFFFFFu, a, b)
    val e = mm256SetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16, 89, 25, 90, 26, 91, 27, 92, 28, 93, 29, 94, 30, 95, 31, 96, 32)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackloEpi8() {
    val a = mmSetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80)
    val r = mmMaskUnpackloEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackloEpi8(a, 0xFFFFu, a, b)
    val e = mmSetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackloEpi8() {
    val a = mmSetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mmSetEpi8(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80)
    val r = mmMaskzUnpackloEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackloEpi8(0xFFFFu, a, b)
    val e = mmSetEpi8(73, 9, 74, 10, 75, 11, 76, 12, 77, 13, 78, 14, 79, 15, 80, 16)
    assertEqM128i(r1, e)
}

public fun testMm512MaskMovEpi16() {
    val src = mm512Set1Epi16(1)
    val a = mm512Set1Epi16(2)
    val r = mm512MaskMovEpi16(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskMovEpi16(src, 0xFFFFFFFFu, a)
    assertEqM512i(r1, a)
}

public fun testMm512MaskzMovEpi16() {
    val a = mm512Set1Epi16(2)
    val r = mm512MaskzMovEpi16(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMovEpi16(0xFFFFFFFFu, a)
    assertEqM512i(r1, a)
}

public fun testMm256MaskMovEpi16() {
    val src = mm256Set1Epi16(1)
    val a = mm256Set1Epi16(2)
    val r = mm256MaskMovEpi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskMovEpi16(src, 0xFFFFu, a)
    assertEqM256i(r1, a)
}

public fun testMm256MaskzMovEpi16() {
    val a = mm256Set1Epi16(2)
    val r = mm256MaskzMovEpi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMovEpi16(0xFFFFu, a)
    assertEqM256i(r1, a)
}

public fun testMmMaskMovEpi16() {
    val src = mmSet1Epi16(1)
    val a = mmSet1Epi16(2)
    val r = mmMaskMovEpi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskMovEpi16(src, 0xFFu, a)
    assertEqM128i(r1, a)
}

public fun testMmMaskzMovEpi16() {
    val a = mmSet1Epi16(2)
    val r = mmMaskzMovEpi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMovEpi16(0xFFu, a)
    assertEqM128i(r1, a)
}

public fun testMm512MaskMovEpi8() {
    val src = mm512Set1Epi8(1)
    val a = mm512Set1Epi8(2)
    val r = mm512MaskMovEpi8(src, 0uL, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskMovEpi8(src, 0xFFFFFFFFFFFFFFFFuL, a)
    assertEqM512i(r1, a)
}

public fun testMm512MaskzMovEpi8() {
    val a = mm512Set1Epi8(2)
    val r = mm512MaskzMovEpi8(0uL, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMovEpi8(0xFFFFFFFFFFFFFFFFuL, a)
    assertEqM512i(r1, a)
}

public fun testMm256MaskMovEpi8() {
    val src = mm256Set1Epi8(1)
    val a = mm256Set1Epi8(2)
    val r = mm256MaskMovEpi8(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskMovEpi8(src, 0xFFFFFFFFu, a)
    assertEqM256i(r1, a)
}

public fun testMm256MaskzMovEpi8() {
    val a = mm256Set1Epi8(2)
    val r = mm256MaskzMovEpi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMovEpi8(0xFFFFFFFFu, a)
    assertEqM256i(r1, a)
}

public fun testMmMaskMovEpi8() {
    val src = mmSet1Epi8(1)
    val a = mmSet1Epi8(2)
    val r = mmMaskMovEpi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskMovEpi8(src, 0xFFFFu, a)
    assertEqM128i(r1, a)
}

public fun testMmMaskzMovEpi8() {
    val a = mmSet1Epi8(2)
    val r = mmMaskzMovEpi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMovEpi8(0xFFFFu, a)
    assertEqM128i(r1, a)
}

public fun testMm512MaskSet1Epi16() {
    val src = mm512Set1Epi16(2)
    val a: Short = 11
    val r = mm512MaskSet1Epi16(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskSet1Epi16(src, 0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(11)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSet1Epi16() {
    val a: Short = 11
    val r = mm512MaskzSet1Epi16(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSet1Epi16(0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(11)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSet1Epi16() {
    val src = mm256Set1Epi16(2)
    val a: Short = 11
    val r = mm256MaskSet1Epi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskSet1Epi16(src, 0xFFFFu, a)
    val e = mm256Set1Epi16(11)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSet1Epi16() {
    val a: Short = 11
    val r = mm256MaskzSet1Epi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSet1Epi16(0xFFFFu, a)
    val e = mm256Set1Epi16(11)
    assertEqM256i(r1, e)
}

public fun testMmMaskSet1Epi16() {
    val src = mmSet1Epi16(2)
    val a: Short = 11
    val r = mmMaskSet1Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskSet1Epi16(src, 0xFFu, a)
    val e = mmSet1Epi16(11)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSet1Epi16() {
    val a: Short = 11
    val r = mmMaskzSet1Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSet1Epi16(0xFFu, a)
    val e = mmSet1Epi16(11)
    assertEqM128i(r1, e)
}

public fun testMm512MaskSet1Epi8() {
    val src = mm512Set1Epi8(2)
    val a: Byte = 11
    val r = mm512MaskSet1Epi8(src, 0uL, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskSet1Epi8(src, 0xFFFFFFFFFFFFFFFFuL, a)
    val e = mm512Set1Epi8(11)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSet1Epi8() {
    val a: Byte = 11
    val r = mm512MaskzSet1Epi8(0uL, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSet1Epi8(0xFFFFFFFFFFFFFFFFuL, a)
    val e = mm512Set1Epi8(11)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSet1Epi8() {
    val src = mm256Set1Epi8(2)
    val a: Byte = 11
    val r = mm256MaskSet1Epi8(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskSet1Epi8(src, 0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(11)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSet1Epi8() {
    val a: Byte = 11
    val r = mm256MaskzSet1Epi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSet1Epi8(0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(11)
    assertEqM256i(r1, e)
}

public fun testMmMaskSet1Epi8() {
    val src = mmSet1Epi8(2)
    val a: Byte = 11
    val r = mmMaskSet1Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskSet1Epi8(src, 0xFFFFu, a)
    val e = mmSet1Epi8(11)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSet1Epi8() {
    val a: Byte = 11
    val r = mmMaskzSet1Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSet1Epi8(0xFFFFu, a)
    val e = mmSet1Epi8(11)
    assertEqM128i(r1, e)
}

public fun testMm512ShuffleloEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e = mm512SetEpi16(0, 1, 2, 3, 7, 6, 6, 4, 8, 9, 10, 11, 15, 14, 14, 12, 16, 17, 18, 19, 23, 22, 22, 20, 24, 25, 26, 27, 31, 30, 30, 28)
    val r = mm512ShuffleloEpi16(a, 0x17)
    assertEqM512i(r, e)
}

public fun testMm512MaskShuffleloEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskShuffleloEpi16(a, 0u, a, 0x17)
    assertEqM512i(r, a)
    val r1 = mm512MaskShuffleloEpi16(a, 0xFFFFFFFFu, a, 0x17)
    val e = mm512SetEpi16(0, 1, 2, 3, 7, 6, 6, 4, 8, 9, 10, 11, 15, 14, 14, 12, 16, 17, 18, 19, 23, 22, 22, 20, 24, 25, 26, 27, 31, 30, 30, 28)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShuffleloEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskzShuffleloEpi16(0u, a, 0x17)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShuffleloEpi16(0xFFFFFFFFu, a, 0x17)
    val e = mm512SetEpi16(0, 1, 2, 3, 7, 6, 6, 4, 8, 9, 10, 11, 15, 14, 14, 12, 16, 17, 18, 19, 23, 22, 22, 20, 24, 25, 26, 27, 31, 30, 30, 28)
    assertEqM512i(r1, e)
}

public fun testMm256MaskShuffleloEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskShuffleloEpi16(a, 0u, a, 0x17)
    assertEqM256i(r, a)
    val r1 = mm256MaskShuffleloEpi16(a, 0xFFFFu, a, 0x17)
    val e = mm256SetEpi16(0, 1, 2, 3, 7, 6, 6, 4, 8, 9, 10, 11, 15, 14, 14, 12)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShuffleloEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzShuffleloEpi16(0u, a, 0x17)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShuffleloEpi16(0xFFFFu, a, 0x17)
    val e = mm256SetEpi16(0, 1, 2, 3, 7, 6, 6, 4, 8, 9, 10, 11, 15, 14, 14, 12)
    assertEqM256i(r1, e)
}

public fun testMmMaskShuffleloEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskShuffleloEpi16(a, 0u, a, 0x17)
    assertEqM128i(r, a)
    val r1 = mmMaskShuffleloEpi16(a, 0xFFu, a, 0x17)
    val e = mmSetEpi16(0, 1, 2, 3, 7, 6, 6, 4)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShuffleloEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskzShuffleloEpi16(0u, a, 0x17)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShuffleloEpi16(0xFFu, a, 0x17)
    val e = mmSetEpi16(0, 1, 2, 3, 7, 6, 6, 4)
    assertEqM128i(r1, e)
}

public fun testMm512ShufflehiEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val e = mm512SetEpi16(3, 2, 2, 0, 4, 5, 6, 7, 11, 10, 10, 8, 12, 13, 14, 15, 19, 18, 18, 16, 20, 21, 22, 23, 27, 26, 26, 24, 28, 29, 30, 31)
    val r = mm512ShufflehiEpi16(a, 0x17)
    assertEqM512i(r, e)
}

public fun testMm512MaskShufflehiEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskShufflehiEpi16(a, 0u, a, 0x17)
    assertEqM512i(r, a)
    val r1 = mm512MaskShufflehiEpi16(a, 0xFFFFFFFFu, a, 0x17)
    val e = mm512SetEpi16(3, 2, 2, 0, 4, 5, 6, 7, 11, 10, 10, 8, 12, 13, 14, 15, 19, 18, 18, 16, 20, 21, 22, 23, 27, 26, 26, 24, 28, 29, 30, 31)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShufflehiEpi16() {
    val a = mm512SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm512MaskzShufflehiEpi16(0u, a, 0x17)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShufflehiEpi16(0xFFFFFFFFu, a, 0x17)
    val e = mm512SetEpi16(3, 2, 2, 0, 4, 5, 6, 7, 11, 10, 10, 8, 12, 13, 14, 15, 19, 18, 18, 16, 20, 21, 22, 23, 27, 26, 26, 24, 28, 29, 30, 31)
    assertEqM512i(r1, e)
}

public fun testMm256MaskShufflehiEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskShufflehiEpi16(a, 0u, a, 0x17)
    assertEqM256i(r, a)
    val r1 = mm256MaskShufflehiEpi16(a, 0xFFFFu, a, 0x17)
    val e = mm256SetEpi16(3, 2, 2, 0, 4, 5, 6, 7, 11, 10, 10, 8, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShufflehiEpi16() {
    val a = mm256SetEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzShufflehiEpi16(0u, a, 0x17)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShufflehiEpi16(0xFFFFu, a, 0x17)
    val e = mm256SetEpi16(3, 2, 2, 0, 4, 5, 6, 7, 11, 10, 10, 8, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMmMaskShufflehiEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskShufflehiEpi16(a, 0u, a, 0x17)
    assertEqM128i(r, a)
    val r1 = mmMaskShufflehiEpi16(a, 0xFFu, a, 0x17)
    val e = mmSetEpi16(3, 2, 2, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShufflehiEpi16() {
    val a = mmSetEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mmMaskzShufflehiEpi16(0u, a, 0x17)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShufflehiEpi16(0xFFu, a, 0x17)
    val e = mmSetEpi16(3, 2, 2, 0, 4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512ShuffleEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val b = mm512Set1Epi8(1)
    val r = mm512ShuffleEpi8(a, b)
    val e = mm512SetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62)
    assertEqM512i(r, e)
}

public fun testMm512MaskShuffleEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskShuffleEpi8(a, 0uL, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskShuffleEpi8(a, 0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512SetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShuffleEpi8() {
    val a = mm512SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskzShuffleEpi8(0uL, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShuffleEpi8(0xFFFFFFFFFFFFFFFFuL, a, b)
    val e = mm512SetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62)
    assertEqM512i(r1, e)
}

public fun testMm256MaskShuffleEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskShuffleEpi8(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskShuffleEpi8(a, 0xFFFFFFFFu, a, b)
    val e = mm256SetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShuffleEpi8() {
    val a = mm256SetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskzShuffleEpi8(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShuffleEpi8(0xFFFFFFFFu, a, b)
    val e = mm256SetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30)
    assertEqM256i(r1, e)
}

public fun testMmMaskShuffleEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSet1Epi8(1)
    val r = mmMaskShuffleEpi8(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskShuffleEpi8(a, 0xFFFFu, a, b)
    val e = mmSetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14)
    assertEqM128i(r1, e)
}

public fun testMmMaskzShuffleEpi8() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mmSet1Epi8(1)
    val r = mmMaskzShuffleEpi8(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzShuffleEpi8(0xFFFFu, a, b)
    val e = mmSetEpi8(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14)
    assertEqM128i(r1, e)
}

public fun testMm512TestEpi16Mask() {
    val a = mm512Set1Epi16((1 shl 0).toShort())
    val b = mm512Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm512TestEpi16Mask(a, b)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r == e)
}

public fun testMm512MaskTestEpi16Mask() {
    val a = mm512Set1Epi16((1 shl 0).toShort())
    val b = mm512Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm512MaskTestEpi16Mask(0u, a, b)
    check(r == 0u)
    val r1 = mm512MaskTestEpi16Mask(0xFFFFFFFFu, a, b)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r1 == e)
}

public fun testMm256TestEpi16Mask() {
    val a = mm256Set1Epi16((1 shl 0).toShort())
    val b = mm256Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm256TestEpi16Mask(a, b)
    val e: Mmask16 = 0xFFFFu
    check(r == e)
}

public fun testMm256MaskTestEpi16Mask() {
    val a = mm256Set1Epi16((1 shl 0).toShort())
    val b = mm256Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm256MaskTestEpi16Mask(0u, a, b)
    check(r == 0u.toUShort())
    val r1 = mm256MaskTestEpi16Mask(0xFFFFu, a, b)
    val e: Mmask16 = 0xFFFFu
    check(r1 == e)
}

public fun testMmTestEpi16Mask() {
    val a = mmSet1Epi16((1 shl 0).toShort())
    val b = mmSet1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mmTestEpi16Mask(a, b)
    val e: Mmask8 = 0xFFu
    check(r == e)
}

public fun testMmMaskTestEpi16Mask() {
    val a = mmSet1Epi16((1 shl 0).toShort())
    val b = mmSet1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mmMaskTestEpi16Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mmMaskTestEpi16Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFFu
    check(r1 == e)
}

public fun testMm512TestEpi8Mask() {
    val a = mm512Set1Epi8((1 shl 0).toByte())
    val b = mm512Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm512TestEpi8Mask(a, b)
    val e: Mmask64 = 0xFFFFFFFFFFFFFFFFuL
    check(r == e)
}

public fun testMm512MaskTestEpi8Mask() {
    val a = mm512Set1Epi8((1 shl 0).toByte())
    val b = mm512Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm512MaskTestEpi8Mask(0uL, a, b)
    check(r == 0uL)
    val r1 = mm512MaskTestEpi8Mask(0xFFFFFFFFFFFFFFFFuL, a, b)
    val e: Mmask64 = 0xFFFFFFFFFFFFFFFFuL
    check(r1 == e)
}

public fun testMm256TestEpi8Mask() {
    val a = mm256Set1Epi8((1 shl 0).toByte())
    val b = mm256Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm256TestEpi8Mask(a, b)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r == e)
}

public fun testMm256MaskTestEpi8Mask() {
    val a = mm256Set1Epi8((1 shl 0).toByte())
    val b = mm256Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm256MaskTestEpi8Mask(0u, a, b)
    check(r == 0u)
    val r1 = mm256MaskTestEpi8Mask(0xFFFFFFFFu, a, b)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r1 == e)
}

public fun testMmTestEpi8Mask() {
    val a = mmSet1Epi8((1 shl 0).toByte())
    val b = mmSet1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mmTestEpi8Mask(a, b)
    val e: Mmask16 = 0xFFFFu
    check(r == e)
}

public fun testMmMaskTestEpi8Mask() {
    val a = mmSet1Epi8((1 shl 0).toByte())
    val b = mmSet1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mmMaskTestEpi8Mask(0u, a, b)
    check(r == 0u.toUShort())
    val r1 = mmMaskTestEpi8Mask(0xFFFFu, a, b)
    val e: Mmask16 = 0xFFFFu
    check(r1 == e)
}

public fun testMm512TestnEpi16Mask() {
    val a = mm512Set1Epi16((1 shl 0).toShort())
    val b = mm512Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm512TestnEpi16Mask(a, b)
    val e: Mmask32 = 0x0u
    check(r == e)
}

public fun testMm512MaskTestnEpi16Mask() {
    val a = mm512Set1Epi16((1 shl 0).toShort())
    val b = mm512Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm512MaskTestnEpi16Mask(0u, a, b)
    check(r == 0u)
    val r1 = mm512MaskTestnEpi16Mask(0xFFFFFFFFu, a, b)
    val e: Mmask32 = 0x0u
    check(r1 == e)
}

public fun testMm256TestnEpi16Mask() {
    val a = mm256Set1Epi16((1 shl 0).toShort())
    val b = mm256Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm256TestnEpi16Mask(a, b)
    val e: Mmask16 = 0x0u
    check(r == e)
}

public fun testMm256MaskTestnEpi16Mask() {
    val a = mm256Set1Epi16((1 shl 0).toShort())
    val b = mm256Set1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mm256MaskTestnEpi16Mask(0u, a, b)
    check(r == 0u.toUShort())
    val r1 = mm256MaskTestnEpi16Mask(0xFFFFu, a, b)
    val e: Mmask16 = 0x0u
    check(r1 == e)
}

public fun testMmTestnEpi16Mask() {
    val a = mmSet1Epi16((1 shl 0).toShort())
    val b = mmSet1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mmTestnEpi16Mask(a, b)
    val e: Mmask8 = 0x0u
    check(r == e)
}

public fun testMmMaskTestnEpi16Mask() {
    val a = mmSet1Epi16((1 shl 0).toShort())
    val b = mmSet1Epi16(((1 shl 0).toInt() or (1 shl 1).toInt()).toShort())
    val r = mmMaskTestnEpi16Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mmMaskTestnEpi16Mask(0xFFu, a, b)
    val e: Mmask8 = 0x0u
    check(r1 == e)
}

public fun testMm512TestnEpi8Mask() {
    val a = mm512Set1Epi8((1 shl 0).toByte())
    val b = mm512Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm512TestnEpi8Mask(a, b)
    val e: Mmask64 = 0x0uL
    check(r == e)
}

public fun testMm512MaskTestnEpi8Mask() {
    val a = mm512Set1Epi8((1 shl 0).toByte())
    val b = mm512Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm512MaskTestnEpi8Mask(0uL, a, b)
    check(r == 0uL)
    val r1 = mm512MaskTestnEpi8Mask(0xFFFFFFFFFFFFFFFFuL, a, b)
    val e: Mmask64 = 0x0uL
    check(r1 == e)
}

public fun testMm256TestnEpi8Mask() {
    val a = mm256Set1Epi8((1 shl 0).toByte())
    val b = mm256Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm256TestnEpi8Mask(a, b)
    val e: Mmask32 = 0x0u
    check(r == e)
}

public fun testMm256MaskTestnEpi8Mask() {
    val a = mm256Set1Epi8((1 shl 0).toByte())
    val b = mm256Set1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mm256MaskTestnEpi8Mask(0u, a, b)
    check(r == 0u)
    val r1 = mm256MaskTestnEpi8Mask(0xFFFFFFFFu, a, b)
    val e: Mmask32 = 0x0u
    check(r1 == e)
}

public fun testMmTestnEpi8Mask() {
    val a = mmSet1Epi8((1 shl 0).toByte())
    val b = mmSet1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mmTestnEpi8Mask(a, b)
    val e: Mmask16 = 0x0u
    check(r == e)
}

public fun testMmMaskTestnEpi8Mask() {
    val a = mmSet1Epi8((1 shl 0).toByte())
    val b = mmSet1Epi8(((1 shl 0).toInt() or (1 shl 1).toInt()).toByte())
    val r = mmMaskTestnEpi8Mask(0u, a, b)
    check(r == 0u.toUShort())
    val r1 = mmMaskTestnEpi8Mask(0xFFFFu, a, b)
    val e: Mmask16 = 0x0u
    check(r1 == e)
}

public fun testStoreMask64() {
    val a: Mmask64 = 0xFF00FF00FF00FF00uL
    val localStorageL20620C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var r by localStorageL20620C8
    run {
        storeMask64(RustMutableReference(localStorageL20620C8.asMutPtr(), localStorageL20620C8.layout), a)
    }
    check(r == a)
}

public fun testStoreMask32() {
    val a: Mmask32 = 0xFF00FF00u
    val localStorageL20630C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var r by localStorageL20630C8
    run {
        storeMask32(RustMutableReference(localStorageL20630C8.asMutPtr(), localStorageL20630C8.layout), a)
    }
    check(r == a)
}

public fun testLoadMask64() {
    val r = loadMask64(p)
    val e: Mmask64 = 0xFF00FF00FF00FF00uL
    check(r == e)
}

public fun testLoadMask32() {
    val r = loadMask32(p)
    val e: Mmask32 = 0xFF00FF00u
    check(r == e)
}

public fun testMm512SadEpu8() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(4)
    val r = mm512SadEpu8(a, b)
    val e = mm512Set1Epi64(16L)
    assertEqM512i(r, e)
}

public fun testMm512DbsadEpu8() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(4)
    val r = mm512DbsadEpu8(a, b, 0)
    val e = mm512Set1Epi16(8)
    assertEqM512i(r, e)
}

public fun testMm512MaskDbsadEpu8() {
    val src = mm512Set1Epi16(1)
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(4)
    val r = mm512MaskDbsadEpu8(src, 0u, a, b, 0)
    assertEqM512i(r, src)
    val r1 = mm512MaskDbsadEpu8(src, 0xFFFFFFFFu, a, b, 0)
    val e = mm512Set1Epi16(8)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzDbsadEpu8() {
    val a = mm512Set1Epi8(2)
    val b = mm512Set1Epi8(4)
    val r = mm512MaskzDbsadEpu8(0u, a, b, 0)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzDbsadEpu8(0xFFFFFFFFu, a, b, 0)
    val e = mm512Set1Epi16(8)
    assertEqM512i(r1, e)
}

public fun testMm256DbsadEpu8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256DbsadEpu8(a, b, 0)
    val e = mm256Set1Epi16(8)
    assertEqM256i(r, e)
}

public fun testMm256MaskDbsadEpu8() {
    val src = mm256Set1Epi16(1)
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MaskDbsadEpu8(src, 0u, a, b, 0)
    assertEqM256i(r, src)
    val r1 = mm256MaskDbsadEpu8(src, 0xFFFFu, a, b, 0)
    val e = mm256Set1Epi16(8)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzDbsadEpu8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MaskzDbsadEpu8(0u, a, b, 0)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzDbsadEpu8(0xFFFFu, a, b, 0)
    val e = mm256Set1Epi16(8)
    assertEqM256i(r1, e)
}

public fun testMmDbsadEpu8() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(4)
    val r = mmDbsadEpu8(a, b, 0)
    val e = mmSet1Epi16(8)
    assertEqM128i(r, e)
}

public fun testMmMaskDbsadEpu8() {
    val src = mmSet1Epi16(1)
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(4)
    val r = mmMaskDbsadEpu8(src, 0u, a, b, 0)
    assertEqM128i(r, src)
    val r1 = mmMaskDbsadEpu8(src, 0xFFu, a, b, 0)
    val e = mmSet1Epi16(8)
    assertEqM128i(r1, e)
}

public fun testMmMaskzDbsadEpu8() {
    val a = mmSet1Epi8(2)
    val b = mmSet1Epi8(4)
    val r = mmMaskzDbsadEpu8(0u, a, b, 0)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzDbsadEpu8(0xFFu, a, b, 0)
    val e = mmSet1Epi16(8)
    assertEqM128i(r1, e)
}

public fun testMm512Movepi16Mask() {
    val a = mm512Set1Epi16((1 shl 15).toShort())
    val r = mm512Movepi16Mask(a)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r == e)
}

public fun testMm256Movepi16Mask() {
    val a = mm256Set1Epi16((1 shl 15).toShort())
    val r = mm256Movepi16Mask(a)
    val e: Mmask16 = 0xFFFFu
    check(r == e)
}

public fun testMmMovepi16Mask() {
    val a = mmSet1Epi16((1 shl 15).toShort())
    val r = mmMovepi16Mask(a)
    val e: Mmask8 = 0xFFu
    check(r == e)
}

public fun testMm512Movepi8Mask() {
    val a = mm512Set1Epi8((1 shl 7).toByte())
    val r = mm512Movepi8Mask(a)
    val e: Mmask64 = 0xFFFFFFFFFFFFFFFFuL
    check(r == e)
}

public fun testMm256Movepi8Mask() {
    val a = mm256Set1Epi8((1 shl 7).toByte())
    val r = mm256Movepi8Mask(a)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r == e)
}

public fun testMmMovepi8Mask() {
    val a = mmSet1Epi8((1 shl 7).toByte())
    val r = mmMovepi8Mask(a)
    val e: Mmask16 = 0xFFFFu
    check(r == e)
}

public fun testMm512MovmEpi16() {
    val a: Mmask32 = 0xFFFFFFFFu
    val r = mm512MovmEpi16(a)
    val e = mm512Set1Epi16(((((((((((((((((((((((((((((((1 shl 15).toInt() or (1 shl 14).toInt()).toShort()).toInt() or (1 shl 13).toInt()).toShort()).toInt() or (1 shl 12).toInt()).toShort()).toInt() or (1 shl 11).toInt()).toShort()).toInt() or (1 shl 10).toInt()).toShort()).toInt() or (1 shl 9).toInt()).toShort()).toInt() or (1 shl 8).toInt()).toShort()).toInt() or (1 shl 7).toInt()).toShort()).toInt() or (1 shl 6).toInt()).toShort()).toInt() or (1 shl 5).toInt()).toShort()).toInt() or (1 shl 4).toInt()).toShort()).toInt() or (1 shl 3).toInt()).toShort()).toInt() or (1 shl 2).toInt()).toShort()).toInt() or (1 shl 1).toInt()).toShort()).toInt() or (1 shl 0).toInt()).toShort())
    assertEqM512i(r, e)
}

public fun testMm256MovmEpi16() {
    val a: Mmask16 = 0xFFFFu
    val r = mm256MovmEpi16(a)
    val e = mm256Set1Epi16(((((((((((((((((((((((((((((((1 shl 15).toInt() or (1 shl 14).toInt()).toShort()).toInt() or (1 shl 13).toInt()).toShort()).toInt() or (1 shl 12).toInt()).toShort()).toInt() or (1 shl 11).toInt()).toShort()).toInt() or (1 shl 10).toInt()).toShort()).toInt() or (1 shl 9).toInt()).toShort()).toInt() or (1 shl 8).toInt()).toShort()).toInt() or (1 shl 7).toInt()).toShort()).toInt() or (1 shl 6).toInt()).toShort()).toInt() or (1 shl 5).toInt()).toShort()).toInt() or (1 shl 4).toInt()).toShort()).toInt() or (1 shl 3).toInt()).toShort()).toInt() or (1 shl 2).toInt()).toShort()).toInt() or (1 shl 1).toInt()).toShort()).toInt() or (1 shl 0).toInt()).toShort())
    assertEqM256i(r, e)
}

public fun testMmMovmEpi16() {
    val a: Mmask8 = 0xFFu
    val r = mmMovmEpi16(a)
    val e = mmSet1Epi16(((((((((((((((((((((((((((((((1 shl 15).toInt() or (1 shl 14).toInt()).toShort()).toInt() or (1 shl 13).toInt()).toShort()).toInt() or (1 shl 12).toInt()).toShort()).toInt() or (1 shl 11).toInt()).toShort()).toInt() or (1 shl 10).toInt()).toShort()).toInt() or (1 shl 9).toInt()).toShort()).toInt() or (1 shl 8).toInt()).toShort()).toInt() or (1 shl 7).toInt()).toShort()).toInt() or (1 shl 6).toInt()).toShort()).toInt() or (1 shl 5).toInt()).toShort()).toInt() or (1 shl 4).toInt()).toShort()).toInt() or (1 shl 3).toInt()).toShort()).toInt() or (1 shl 2).toInt()).toShort()).toInt() or (1 shl 1).toInt()).toShort()).toInt() or (1 shl 0).toInt()).toShort())
    assertEqM128i(r, e)
}

public fun testMm512MovmEpi8() {
    val a: Mmask64 = 0xFFFFFFFFFFFFFFFFuL
    val r = mm512MovmEpi8(a)
    val e = mm512Set1Epi8(((((((((((((((1 shl 7).toInt() or (1 shl 6).toInt()).toByte()).toInt() or (1 shl 5).toInt()).toByte()).toInt() or (1 shl 4).toInt()).toByte()).toInt() or (1 shl 3).toInt()).toByte()).toInt() or (1 shl 2).toInt()).toByte()).toInt() or (1 shl 1).toInt()).toByte()).toInt() or (1 shl 0).toInt()).toByte())
    assertEqM512i(r, e)
}

public fun testMm256MovmEpi8() {
    val a: Mmask32 = 0xFFFFFFFFu
    val r = mm256MovmEpi8(a)
    val e = mm256Set1Epi8(((((((((((((((1 shl 7).toInt() or (1 shl 6).toInt()).toByte()).toInt() or (1 shl 5).toInt()).toByte()).toInt() or (1 shl 4).toInt()).toByte()).toInt() or (1 shl 3).toInt()).toByte()).toInt() or (1 shl 2).toInt()).toByte()).toInt() or (1 shl 1).toInt()).toByte()).toInt() or (1 shl 0).toInt()).toByte())
    assertEqM256i(r, e)
}

public fun testMmMovmEpi8() {
    val a: Mmask16 = 0xFFFFu
    val r = mmMovmEpi8(a)
    val e = mmSet1Epi8(((((((((((((((1 shl 7).toInt() or (1 shl 6).toInt()).toByte()).toInt() or (1 shl 5).toInt()).toByte()).toInt() or (1 shl 4).toInt()).toByte()).toInt() or (1 shl 3).toInt()).toByte()).toInt() or (1 shl 2).toInt()).toByte()).toInt() or (1 shl 1).toInt()).toByte()).toInt() or (1 shl 0).toInt()).toByte())
    assertEqM128i(r, e)
}

public fun testCvtmask32U32() {
    val a: Mmask32 = 0xCC336699u
    val r = cvtmask32U32(a)
    val e: UInt = 0xCC336699u
    check(r == e)
}

public fun testCvtu32Mask32() {
    val a: UInt = 0xCC336699u
    val r = cvtu32Mask32(a)
    val e: Mmask32 = 0xCC336699u
    check(r == e)
}

public fun testKaddMask32() {
    val a: Mmask32 = 11u
    val b: Mmask32 = 22u
    val r = kaddMask32(a, b)
    val e: Mmask32 = 33u
    check(r == e)
}

public fun testKaddMask64() {
    val a: Mmask64 = 11uL
    val b: Mmask64 = 22uL
    val r = kaddMask64(a, b)
    val e: Mmask64 = 33uL
    check(r == e)
}

public fun testKandMask32() {
    val a: Mmask32 = 0xCC33CC33u
    val b: Mmask32 = 0xCC33CC33u
    val r = kandMask32(a, b)
    val e: Mmask32 = 0xCC33CC33u
    check(r == e)
}

public fun testKandMask64() {
    val a: Mmask64 = 0xCC33CC33CC33CC33uL
    val b: Mmask64 = 0xCC33CC33CC33CC33uL
    val r = kandMask64(a, b)
    val e: Mmask64 = 0xCC33CC33CC33CC33uL
    check(r == e)
}

public fun testKnotMask32() {
    val a: Mmask32 = 0xCC33CC33u
    val r = knotMask32(a)
    val e: Mmask32 = 0x33CC33CCu
    check(r == e)
}

public fun testKnotMask64() {
    val a: Mmask64 = 0xCC33CC33CC33CC33uL
    val r = knotMask64(a)
    val e: Mmask64 = 0x33CC33CC33CC33CCuL
    check(r == e)
}

public fun testKandnMask32() {
    val a: Mmask32 = 0xCC33CC33u
    val b: Mmask32 = 0xCC33CC33u
    val r = kandnMask32(a, b)
    val e: Mmask32 = 0x0u
    check(r == e)
}

public fun testKandnMask64() {
    val a: Mmask64 = 0xCC33CC33CC33CC33uL
    val b: Mmask64 = 0xCC33CC33CC33CC33uL
    val r = kandnMask64(a, b)
    val e: Mmask64 = 0x0uL
    check(r == e)
}

public fun testKorMask32() {
    val a: Mmask32 = 0x33CC33CCu
    val b: Mmask32 = 0xCC33CC33u
    val r = korMask32(a, b)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r == e)
}

public fun testKorMask64() {
    val a: Mmask64 = 0x33CC33CC33CC33CCuL
    val b: Mmask64 = 0xCC33CC33CC33CC33uL
    val r = korMask64(a, b)
    val e: Mmask64 = 0xFFFFFFFFFFFFFFFFuL
    check(r == e)
}

public fun testKxorMask32() {
    val a: Mmask32 = 0x33CC33CCu
    val b: Mmask32 = 0xCC33CC33u
    val r = kxorMask32(a, b)
    val e: Mmask32 = 0xFFFFFFFFu
    check(r == e)
}

public fun testKxorMask64() {
    val a: Mmask64 = 0x33CC33CC33CC33CCuL
    val b: Mmask64 = 0xCC33CC33CC33CC33uL
    val r = kxorMask64(a, b)
    val e: Mmask64 = 0xFFFFFFFFFFFFFFFFuL
    check(r == e)
}

public fun testKxnorMask32() {
    val a: Mmask32 = 0x33CC33CCu
    val b: Mmask32 = 0xCC33CC33u
    val r = kxnorMask32(a, b)
    val e: Mmask32 = 0x0u
    check(r == e)
}

public fun testKxnorMask64() {
    val a: Mmask64 = 0x33CC33CC33CC33CCuL
    val b: Mmask64 = 0xCC33CC33CC33CC33uL
    val r = kxnorMask64(a, b)
    val e: Mmask64 = 0x0uL
    check(r == e)
}

public fun testKortestMask32U8() {
    val a: Mmask32 = 0x69696969u
    val b: Mmask32 = 0xB6B6B6B6u
    val localStorageL21073C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var allOnes by localStorageL21073C8
    val r = kortestMask32U8(a, b, RustMutableReference(localStorageL21073C8.asMutPtr(), localStorageL21073C8.layout))
    check(r == 0u.toUByte())
    check(allOnes == 1u.toUByte())
}

public fun testKortestMask64U8() {
    val a: Mmask64 = 0x69696969uL
    val b: Mmask64 = 0xB6B6B6B6uL
    val localStorageL21083C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var allOnes by localStorageL21083C8
    val r = kortestMask64U8(a, b, RustMutableReference(localStorageL21083C8.asMutPtr(), localStorageL21083C8.layout))
    check(r == 0u.toUByte())
    check(allOnes == 0u.toUByte())
}

public fun testKortestcMask32U8() {
    val a: Mmask32 = 0x69696969u
    val b: Mmask32 = 0xB6B6B6B6u
    val r = kortestcMask32U8(a, b)
    check(r == 1u.toUByte())
}

public fun testKortestcMask64U8() {
    val a: Mmask64 = 0x69696969uL
    val b: Mmask64 = 0xB6B6B6B6uL
    val r = kortestcMask64U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKortestzMask32U8() {
    val a: Mmask32 = 0x69696969u
    val b: Mmask32 = 0xB6B6B6B6u
    val r = kortestzMask32U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKortestzMask64U8() {
    val a: Mmask64 = 0x69696969uL
    val b: Mmask64 = 0xB6B6B6B6uL
    val r = kortestzMask64U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKshiftliMask32() {
    val a: Mmask32 = 0x69696969u
    val r = kshiftliMask32(a, 3)
    val e: Mmask32 = 0x4B4B4B48u
    check(r == e)
    val r1 = kshiftliMask32(a, 31)
    val e1: Mmask32 = 0x80000000u
    check(r1 == e1)
    val r2 = kshiftliMask32(a, 32)
    val e2: Mmask32 = 0x0u
    check(r2 == e2)
    val r3 = kshiftliMask32(a, 33)
    val e3: Mmask32 = 0x0u
    check(r3 == e3)
}

public fun testKshiftliMask64() {
    val a: Mmask64 = 0x69696969uL
    val r = kshiftliMask64(a, 3)
    val e: Mmask64 = 0x34B4B4B48uL
    check(r == e)
    val r1 = kshiftliMask64(a, 63)
    val e1: Mmask64 = 0x8000000000000000uL
    check(r1 == e1)
    val r2 = kshiftliMask64(a, 64)
    val e2: Mmask64 = 0x0uL
    check(r2 == e2)
    val r3 = kshiftliMask64(a, 65)
    val e3: Mmask64 = 0x0uL
    check(r3 == e3)
}

public fun testKshiftriMask32() {
    val a: Mmask32 = 0xA9696969u
    val r = kshiftriMask32(a, 3)
    val e: Mmask32 = 0x152D2D2Du
    check(r == e)
    val r1 = kshiftriMask32(a, 31)
    val e1: Mmask32 = 0x1u
    check(r1 == e1)
    val r2 = kshiftriMask32(a, 32)
    val e2: Mmask32 = 0x0u
    check(r2 == e2)
    val r3 = kshiftriMask32(a, 33)
    val e3: Mmask32 = 0x0u
    check(r3 == e3)
}

public fun testKshiftriMask64() {
    val a: Mmask64 = 0x54B4B4B48uL
    val r = kshiftriMask64(a, 3)
    val e: Mmask64 = 0xA9696969uL
    check(r == e)
    val r1 = kshiftriMask64(a, 34)
    val e1: Mmask64 = 0x1uL
    check(r1 == e1)
    val r2 = kshiftriMask64(a, 35)
    val e2: Mmask64 = 0x0uL
    check(r2 == e2)
    val r3 = kshiftriMask64(a, 64)
    val e3: Mmask64 = 0x0uL
    check(r3 == e3)
    val r4 = kshiftriMask64(a, 65)
    val e4: Mmask64 = 0x0uL
    check(r4 == e4)
}

public fun testKtestMask32U8() {
    val a: Mmask32 = 0x693C693Cu
    val b: Mmask32 = 0x96C396C3u
    val localStorageL21209C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var andNot by localStorageL21209C8
    val r = ktestMask32U8(a, b, RustMutableReference(localStorageL21209C8.asMutPtr(), localStorageL21209C8.layout))
    check(r == 1u.toUByte())
    check(andNot == 0u.toUByte())
}

public fun testKtestcMask32U8() {
    val a: Mmask32 = 0x693C693Cu
    val b: Mmask32 = 0x96C396C3u
    val r = ktestcMask32U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKtestzMask32U8() {
    val a: Mmask32 = 0x693C693Cu
    val b: Mmask32 = 0x96C396C3u
    val r = ktestzMask32U8(a, b)
    check(r == 1u.toUByte())
}

public fun testKtestMask64U8() {
    val a: Mmask64 = 0x693C693CuL
    val b: Mmask64 = 0x96C396C3uL
    val localStorageL21235C8 = AddressableLocal(0u, PrimitiveLayouts.U8)
    var andNot by localStorageL21235C8
    val r = ktestMask64U8(a, b, RustMutableReference(localStorageL21235C8.asMutPtr(), localStorageL21235C8.layout))
    check(r == 1u.toUByte())
    check(andNot == 0u.toUByte())
}

public fun testKtestcMask64U8() {
    val a: Mmask64 = 0x693C693CuL
    val b: Mmask64 = 0x96C396C3uL
    val r = ktestcMask64U8(a, b)
    check(r == 0u.toUByte())
}

public fun testKtestzMask64U8() {
    val a: Mmask64 = 0x693C693CuL
    val b: Mmask64 = 0x96C396C3uL
    val r = ktestzMask64U8(a, b)
    check(r == 1u.toUByte())
}

public fun testMm512Kunpackw() {
    val a: UInt = 0x00110011u
    val b: UInt = 0x00001011u
    val r = mm512Kunpackw(a, b)
    val e: UInt = 0x00111011u
    check(r == e)
}

public fun testMm512Kunpackd() {
    val a: ULong = 0x1100110000110011uL
    val b: ULong = 0x0010111000001011uL
    val r = mm512Kunpackd(a, b)
    val e: ULong = 0x0011001100001011uL
    check(r == e)
}

public fun testMm512Cvtepi16Epi8() {
    val a = mm512Set1Epi16(2)
    val r = mm512Cvtepi16Epi8(a)
    val e = mm256Set1Epi8(2)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtepi16Epi8() {
    val src = mm256Set1Epi8(1)
    val a = mm512Set1Epi16(2)
    val r = mm512MaskCvtepi16Epi8(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtepi16Epi8(src, 0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(2)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtepi16Epi8() {
    val a = mm512Set1Epi16(2)
    val r = mm512MaskzCvtepi16Epi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtepi16Epi8(0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(2)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtepi16Epi8() {
    val a = mm256Set1Epi16(2)
    val r = mm256Cvtepi16Epi8(a)
    val e = mmSet1Epi8(2)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi16Epi8() {
    val src = mmSet1Epi8(1)
    val a = mm256Set1Epi16(2)
    val r = mm256MaskCvtepi16Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtepi16Epi8(src, 0xFFFFu, a)
    val e = mmSet1Epi8(2)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtepi16Epi8() {
    val a = mm256Set1Epi16(2)
    val r = mm256MaskzCvtepi16Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtepi16Epi8(0xFFFFu, a)
    val e = mmSet1Epi8(2)
    assertEqM128i(r1, e)
}

public fun testMmCvtepi16Epi8() {
    val a = mmSet1Epi16(2)
    val r = mmCvtepi16Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi16Epi8() {
    val src = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    val a = mmSet1Epi16(2)
    val r = mmMaskCvtepi16Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi16Epi8(src, 0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi16Epi8() {
    val a = mmSet1Epi16(2)
    val r = mmMaskzCvtepi16Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi16Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtsepi16Epi8() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512Cvtsepi16Epi8(a)
    val e = mm256Set1Epi8(Byte.MAX_VALUE)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtsepi16Epi8() {
    val src = mm256Set1Epi8(1)
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512MaskCvtsepi16Epi8(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtsepi16Epi8(src, 0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(Byte.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtsepi16Epi8() {
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256Cvtsepi16Epi8(a)
    val e = mmSet1Epi8(Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi16Epi8() {
    val src = mmSet1Epi8(1)
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256MaskCvtsepi16Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtsepi16Epi8(src, 0xFFFFu, a)
    val e = mmSet1Epi8(Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtsepi16Epi8() {
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val r = mm256MaskzCvtsepi16Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtsepi16Epi8(0xFFFFu, a)
    val e = mmSet1Epi8(Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmCvtsepi16Epi8() {
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmCvtsepi16Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi16Epi8() {
    val src = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmMaskCvtsepi16Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtsepi16Epi8(src, 0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtsepi16Epi8() {
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val r = mmMaskzCvtsepi16Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtsepi16Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtsepi16Epi8() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val r = mm512MaskzCvtsepi16Epi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtsepi16Epi8(0xFFFFFFFFu, a)
    val e = mm256Set1Epi8(Byte.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm512Cvtusepi16Epi8() {
    val a = mm512Set1Epi16(Short.MIN_VALUE)
    val r = mm512Cvtusepi16Epi8(a)
    val e = mm256Set1Epi8((-1).toByte())
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtusepi16Epi8() {
    val src = mm256Set1Epi8(1)
    val a = mm512Set1Epi16(Short.MIN_VALUE)
    val r = mm512MaskCvtusepi16Epi8(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtusepi16Epi8(src, 0xFFFFFFFFu, a)
    val e = mm256Set1Epi8((-1).toByte())
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtusepi16Epi8() {
    val a = mm512Set1Epi16(Short.MIN_VALUE)
    val r = mm512MaskzCvtusepi16Epi8(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtusepi16Epi8(0xFFFFFFFFu, a)
    val e = mm256Set1Epi8((-1).toByte())
    assertEqM256i(r1, e)
}

public fun testMm256Cvtusepi16Epi8() {
    val a = mm256Set1Epi16(Short.MIN_VALUE)
    val r = mm256Cvtusepi16Epi8(a)
    val e = mmSet1Epi8((-1).toByte())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi16Epi8() {
    val src = mmSet1Epi8(1)
    val a = mm256Set1Epi16(Short.MIN_VALUE)
    val r = mm256MaskCvtusepi16Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtusepi16Epi8(src, 0xFFFFu, a)
    val e = mmSet1Epi8((-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtusepi16Epi8() {
    val a = mm256Set1Epi16(Short.MIN_VALUE)
    val r = mm256MaskzCvtusepi16Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtusepi16Epi8(0xFFFFu, a)
    val e = mmSet1Epi8((-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMmCvtusepi16Epi8() {
    val a = mmSet1Epi16(Short.MIN_VALUE)
    val r = mmCvtusepi16Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi16Epi8() {
    val src = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
    val a = mmSet1Epi16(Short.MIN_VALUE)
    val r = mmMaskCvtusepi16Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtusepi16Epi8(src, 0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtusepi16Epi8() {
    val a = mmSet1Epi16(Short.MIN_VALUE)
    val r = mmMaskzCvtusepi16Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtusepi16Epi8(0xFFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi8Epi16() {
    val a = mm256Set1Epi8(2)
    val r = mm512Cvtepi8Epi16(a)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepi8Epi16() {
    val src = mm512Set1Epi16(1)
    val a = mm256Set1Epi8(2)
    val r = mm512MaskCvtepi8Epi16(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepi8Epi16(src, 0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepi8Epi16() {
    val a = mm256Set1Epi8(2)
    val r = mm512MaskzCvtepi8Epi16(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepi8Epi16(0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepi8Epi16() {
    val src = mm256Set1Epi16(1)
    val a = mmSet1Epi8(2)
    val r = mm256MaskCvtepi8Epi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepi8Epi16(src, 0xFFFFu, a)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepi8Epi16() {
    val a = mmSet1Epi8(2)
    val r = mm256MaskzCvtepi8Epi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepi8Epi16(0xFFFFu, a)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepi8Epi16() {
    val src = mmSet1Epi16(1)
    val a = mmSet1Epi8(2)
    val r = mmMaskCvtepi8Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi8Epi16(src, 0xFFu, a)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi8Epi16() {
    val a = mmSet1Epi8(2)
    val r = mmMaskzCvtepi8Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi8Epi16(0xFFu, a)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepu8Epi16() {
    val a = mm256Set1Epi8(2)
    val r = mm512Cvtepu8Epi16(a)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepu8Epi16() {
    val src = mm512Set1Epi16(1)
    val a = mm256Set1Epi8(2)
    val r = mm512MaskCvtepu8Epi16(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepu8Epi16(src, 0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepu8Epi16() {
    val a = mm256Set1Epi8(2)
    val r = mm512MaskzCvtepu8Epi16(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepu8Epi16(0xFFFFFFFFu, a)
    val e = mm512Set1Epi16(2)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepu8Epi16() {
    val src = mm256Set1Epi16(1)
    val a = mmSet1Epi8(2)
    val r = mm256MaskCvtepu8Epi16(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepu8Epi16(src, 0xFFFFu, a)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepu8Epi16() {
    val a = mmSet1Epi8(2)
    val r = mm256MaskzCvtepu8Epi16(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepu8Epi16(0xFFFFu, a)
    val e = mm256Set1Epi16(2)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepu8Epi16() {
    val src = mmSet1Epi16(1)
    val a = mmSet1Epi8(2)
    val r = mmMaskCvtepu8Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepu8Epi16(src, 0xFFu, a)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepu8Epi16() {
    val a = mmSet1Epi8(2)
    val r = mmMaskzCvtepu8Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepu8Epi16(0xFFu, a)
    val e = mmSet1Epi16(2)
    assertEqM128i(r1, e)
}

public fun testMm512BslliEpi128() {
    val a = mm512SetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val r = mm512BslliEpi128(a, 9)
    val e = mm512SetEpi8(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512BsrliEpi128() {
    val a = mm512SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64)
    val r = mm512BsrliEpi128(a, 3)
    val e = mm512SetEpi8(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 0, 0, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 0, 0, 0, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 0, 0, 0, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61)
    assertEqM512i(r, e)
}

public fun testMm512AlignrEpi8() {
    val a = mm512SetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mm512Set1Epi8(1)
    val r = mm512AlignrEpi8(a, b, 14)
    val e = mm512SetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM512i(r, e)
}

public fun testMm512MaskAlignrEpi8() {
    val a = mm512SetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskAlignrEpi8(a, 0uL, a, b, 14)
    assertEqM512i(r, a)
    val r1 = mm512MaskAlignrEpi8(a, 0xFFFFFFFFFFFFFFFFuL, a, b, 14)
    val e = mm512SetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAlignrEpi8() {
    val a = mm512SetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mm512Set1Epi8(1)
    val r = mm512MaskzAlignrEpi8(0uL, a, b, 14)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAlignrEpi8(0xFFFFFFFFFFFFFFFFuL, a, b, 14)
    val e = mm512SetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAlignrEpi8() {
    val a = mm256SetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskAlignrEpi8(a, 0u, a, b, 14)
    assertEqM256i(r, a)
    val r1 = mm256MaskAlignrEpi8(a, 0xFFFFFFFFu, a, b, 14)
    val e = mm256SetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAlignrEpi8() {
    val a = mm256SetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mm256Set1Epi8(1)
    val r = mm256MaskzAlignrEpi8(0u, a, b, 14)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAlignrEpi8(0xFFFFFFFFu, a, b, 14)
    val e = mm256SetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM256i(r1, e)
}

public fun testMmMaskAlignrEpi8() {
    val a = mmSetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mmSet1Epi8(1)
    val r = mmMaskAlignrEpi8(a, 0u, a, b, 14)
    assertEqM128i(r, a)
    val r1 = mmMaskAlignrEpi8(a, 0xFFFFu, a, b, 14)
    val e = mmSetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAlignrEpi8() {
    val a = mmSetEpi8(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
    val b = mmSet1Epi8(1)
    val r = mmMaskzAlignrEpi8(0u, a, b, 14)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAlignrEpi8(0xFFFFu, a, b, 14)
    val e = mmSetEpi8(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1)
    assertEqM128i(r1, e)
}

public fun testMm512MaskCvtsepi16StoreuEpi8() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val localStorageL21860C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL21860C8
    run {
        mm512MaskCvtsepi16StoreuEpi8((MutPtr(localStorageL21860C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFFFFFu, a)
    }
    val e = mm256Set1Epi8(Byte.MAX_VALUE)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtsepi16StoreuEpi8() {
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val localStorageL21875C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL21875C8
    run {
        mm256MaskCvtsepi16StoreuEpi8((MutPtr(localStorageL21875C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mmSet1Epi8(Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi16StoreuEpi8() {
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val localStorageL21886C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL21886C8
    run {
        mmMaskCvtsepi16StoreuEpi8((MutPtr(localStorageL21886C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi16StoreuEpi8() {
    val a = mm512Set1Epi16(8)
    val localStorageL21901C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL21901C8
    run {
        mm512MaskCvtepi16StoreuEpi8((MutPtr(localStorageL21901C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFFFFFu, a)
    }
    val e = mm256Set1Epi8(8)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtepi16StoreuEpi8() {
    val a = mm256Set1Epi16(8)
    val localStorageL21916C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL21916C8
    run {
        mm256MaskCvtepi16StoreuEpi8((MutPtr(localStorageL21916C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mmSet1Epi8(8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi16StoreuEpi8() {
    val a = mmSet1Epi16(8)
    val localStorageL21927C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL21927C8
    run {
        mmMaskCvtepi16StoreuEpi8((MutPtr(localStorageL21927C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 8, 8, 8, 8)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi16StoreuEpi8() {
    val a = mm512Set1Epi16(Short.MAX_VALUE)
    val localStorageL21938C8 = AddressableLocal(mm256UndefinedSi256(), UnresolvedTypeLayout<M256i>())
    val r by localStorageL21938C8
    run {
        mm512MaskCvtusepi16StoreuEpi8((MutPtr(localStorageL21938C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFFFFFu, a)
    }
    val e = mm256Set1Epi8(UByte.MAX_VALUE.toByte())
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtusepi16StoreuEpi8() {
    val a = mm256Set1Epi16(Short.MAX_VALUE)
    val localStorageL21953C8 = AddressableLocal(mmUndefinedSi128(), UnresolvedTypeLayout<M128i>())
    val r by localStorageL21953C8
    run {
        mm256MaskCvtusepi16StoreuEpi8((MutPtr(localStorageL21953C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFFFu, a)
    }
    val e = mmSet1Epi8(UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi16StoreuEpi8() {
    val a = mmSet1Epi16(Short.MAX_VALUE)
    val localStorageL21964C8 = AddressableLocal(mmSet1Epi8(0), UnresolvedTypeLayout<M128i>())
    val r by localStorageL21964C8
    run {
        mmMaskCvtusepi16StoreuEpi8((MutPtr(localStorageL21964C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}
