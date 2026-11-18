// port-lint: tests x86/avxneconvert.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.transmuteCopy
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x8

public val BF16_ONE: UShort = 0x3F80u.toUShort()

public val BF16_TWO: UShort = 0x4000u.toUShort()

public val BF16_THREE: UShort = 0x4040u.toUShort()

public val BF16_FOUR: UShort = 0x4080u.toUShort()

public val BF16_FIVE: UShort = 0x40A0u.toUShort()

public val BF16_SIX: UShort = 0x40C0u.toUShort()

public val BF16_SEVEN: UShort = 0x40E0u.toUShort()

public val BF16_EIGHT: UShort = 0x4100u.toUShort()

public fun testMmBcstnebf16Ps() {
    val a = Bf16.fromBits(BF16_ONE)
    val r = mmBcstnebf16Ps(addrOf(a))
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMm256Bcstnebf16Ps() {
    val a = Bf16.fromBits(BF16_ONE)
    val r = mm256Bcstnebf16Ps(addrOf(a))
    val e = mm256SetPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM256(r, e)
}

public fun testMmBcstneshPs() {
    val a = F16.fromFloat(1.0f)
    val r = mmBcstneshPs(addrOf(a))
    val e = mmSetPs(1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM128(r, e)
}

public fun testMm256BcstneshPs() {
    val a = F16.fromFloat(1.0f)
    val r = mm256BcstneshPs(addrOf(a))
    val e = mm256SetPs(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    assertEqM256(r, e)
}

public fun testMmCvtneebf16Ps() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val r = mmCvtneebf16Ps(addrOf(a))
    val e = mmSetrPs(1.0f, 3.0f, 5.0f, 7.0f)
    assertEqM128(r, e)
}

public fun testMm256Cvtneebf16Ps() {
    val a = m256bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT, BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val r = mm256Cvtneebf16Ps(addrOf(a))
    val e = mm256SetrPs(1.0f, 3.0f, 5.0f, 7.0f, 1.0f, 3.0f, 5.0f, 7.0f)
    assertEqM256(r, e)
}

public fun testMmCvtneephPs() {
    val a = m128h(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0))
    val r = mmCvtneephPs(addrOf(a))
    val e = mmSetrPs(1.0f, 3.0f, 5.0f, 7.0f)
    assertEqM128(r, e)
}

public fun testMm256CvtneephPs() {
    val a = m256h(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0))
    val r = mm256CvtneephPs(addrOf(a))
    val e = mm256SetrPs(1.0f, 3.0f, 5.0f, 7.0f, 9.0f, 11.0f, 13.0f, 15.0f)
    assertEqM256(r, e)
}

public fun testMmCvtneobf16Ps() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val r = mmCvtneobf16Ps(addrOf(a))
    val e = mmSetrPs(2.0f, 4.0f, 6.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMm256Cvtneobf16Ps() {
    val a = m256bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT, BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val r = mm256Cvtneobf16Ps(addrOf(a))
    val e = mm256SetrPs(2.0f, 4.0f, 6.0f, 8.0f, 2.0f, 4.0f, 6.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmCvtneophPs() {
    val a = m128h(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0))
    val r = mmCvtneophPs(addrOf(a))
    val e = mmSetrPs(2.0f, 4.0f, 6.0f, 8.0f)
    assertEqM128(r, e)
}

public fun testMm256CvtneophPs() {
    val a = m256h(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0))
    val r = mm256CvtneophPs(addrOf(a))
    val e = mm256SetrPs(2.0f, 4.0f, 6.0f, 8.0f, 10.0f, 12.0f, 14.0f, 16.0f)
    assertEqM256(r, e)
}

public fun testMmCvtnepsAvxPbh() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r: U16x4 = transmuteCopy(mmCvtnepsAvxPbh(a))
    val e = Simd<UShort>(listOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR))
    check(r == e)
}

public fun testMm256CvtnepsAvxPbh() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r: U16x8 = mm256CvtnepsAvxPbh(a).asU16x8()
    val e = Simd<UShort>(listOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    check(r == e)
}
