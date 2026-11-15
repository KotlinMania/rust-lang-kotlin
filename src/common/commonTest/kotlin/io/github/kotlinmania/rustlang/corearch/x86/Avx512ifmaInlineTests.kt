// port-lint: tests x86/avx512ifma.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.Mmask8
import io.github.kotlinmania.rustlang.x86.mm256MaskBlendEpi64
import io.github.kotlinmania.rustlang.x86.mm256Set1Epi64x
import io.github.kotlinmania.rustlang.x86.mm256SetzeroSi256
import io.github.kotlinmania.rustlang.x86.mm512MaskBlendEpi64
import io.github.kotlinmania.rustlang.x86.mm512Set1Epi64
import io.github.kotlinmania.rustlang.x86.mm512SetzeroSi512
import io.github.kotlinmania.rustlang.x86.mmMaskBlendEpi64
import io.github.kotlinmania.rustlang.x86.mmSet1Epi64x
import io.github.kotlinmania.rustlang.x86.mmSetzeroSi128

public val K: Mmask8 = 0x6Du

public fun testMm512Madd52hiEpu64() {
    val a = mm512Set1Epi64(10L shl 40)
    val b = mm512Set1Epi64((11L shl 40) + 4L)
    val c = mm512Set1Epi64((12L shl 40) + 3L)
    val actual = mm512Madd52hiEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    val expected = mm512Set1Epi64(11030549757952L)
    assertEqM512i(expected, actual)
}

public fun testMm512MaskMadd52hiEpu64() {
    val a = mm512Set1Epi64(10L shl 40)
    val b = mm512Set1Epi64((11L shl 40) + 4L)
    val c = mm512Set1Epi64((12L shl 40) + 3L)
    val actual = mm512MaskMadd52hiEpu64(a, K, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    var expected = mm512Set1Epi64(11030549757952L)
    expected = mm512MaskBlendEpi64(K, a, expected)
    assertEqM512i(expected, actual)
}

public fun testMm512MaskzMadd52hiEpu64() {
    val a = mm512Set1Epi64(10L shl 40)
    val b = mm512Set1Epi64((11L shl 40) + 4L)
    val c = mm512Set1Epi64((12L shl 40) + 3L)
    val actual = mm512MaskzMadd52hiEpu64(K, a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    var expected = mm512Set1Epi64(11030549757952L)
    expected = mm512MaskBlendEpi64(K, mm512SetzeroSi512(), expected)
    assertEqM512i(expected, actual)
}

public fun testMm512Madd52loEpu64() {
    val a = mm512Set1Epi64(10L shl 40)
    val b = mm512Set1Epi64((11L shl 40) + 4L)
    val c = mm512Set1Epi64((12L shl 40) + 3L)
    val actual = mm512Madd52loEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    val expected = mm512Set1Epi64(100055558127628L)
    assertEqM512i(expected, actual)
}

public fun testMm512MaskMadd52loEpu64() {
    val a = mm512Set1Epi64(10L shl 40)
    val b = mm512Set1Epi64((11L shl 40) + 4L)
    val c = mm512Set1Epi64((12L shl 40) + 3L)
    val actual = mm512MaskMadd52loEpu64(a, K, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    var expected = mm512Set1Epi64(100055558127628L)
    expected = mm512MaskBlendEpi64(K, a, expected)
    assertEqM512i(expected, actual)
}

public fun testMm512MaskzMadd52loEpu64() {
    val a = mm512Set1Epi64(10L shl 40)
    val b = mm512Set1Epi64((11L shl 40) + 4L)
    val c = mm512Set1Epi64((12L shl 40) + 3L)
    val actual = mm512MaskzMadd52loEpu64(K, a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    var expected = mm512Set1Epi64(100055558127628L)
    expected = mm512MaskBlendEpi64(K, mm512SetzeroSi512(), expected)
    assertEqM512i(expected, actual)
}

public fun testMm256Madd52hiAvxEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256Madd52hiAvxEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    val expected = mm256Set1Epi64x(11030549757952L)
    assertEqM256i(expected, actual)
}

public fun testMm256Madd52hiEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256Madd52hiEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    val expected = mm256Set1Epi64x(11030549757952L)
    assertEqM256i(expected, actual)
}

public fun testMm256MaskMadd52hiEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256MaskMadd52hiEpu64(a, K, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    var expected = mm256Set1Epi64x(11030549757952L)
    expected = mm256MaskBlendEpi64(K, a, expected)
    assertEqM256i(expected, actual)
}

public fun testMm256MaskzMadd52hiEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256MaskzMadd52hiEpu64(K, a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    var expected = mm256Set1Epi64x(11030549757952L)
    expected = mm256MaskBlendEpi64(K, mm256SetzeroSi256(), expected)
    assertEqM256i(expected, actual)
}

public fun testMm256Madd52loAvxEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256Madd52loAvxEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    val expected = mm256Set1Epi64x(100055558127628L)
    assertEqM256i(expected, actual)
}

public fun testMm256Madd52loEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256Madd52loEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    val expected = mm256Set1Epi64x(100055558127628L)
    assertEqM256i(expected, actual)
}

public fun testMm256MaskMadd52loEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256MaskMadd52loEpu64(a, K, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    var expected = mm256Set1Epi64x(100055558127628L)
    expected = mm256MaskBlendEpi64(K, a, expected)
    assertEqM256i(expected, actual)
}

public fun testMm256MaskzMadd52loEpu64() {
    val a = mm256Set1Epi64x(10L shl 40)
    val b = mm256Set1Epi64x((11L shl 40) + 4L)
    val c = mm256Set1Epi64x((12L shl 40) + 3L)
    val actual = mm256MaskzMadd52loEpu64(K, a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    var expected = mm256Set1Epi64x(100055558127628L)
    expected = mm256MaskBlendEpi64(K, mm256SetzeroSi256(), expected)
    assertEqM256i(expected, actual)
}

public fun testMmMadd52hiAvxEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMadd52hiAvxEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    val expected = mmSet1Epi64x(11030549757952L)
    assertEqM128i(expected, actual)
}

public fun testMmMadd52hiEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMadd52hiEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    val expected = mmSet1Epi64x(11030549757952L)
    assertEqM128i(expected, actual)
}

public fun testMmMaskMadd52hiEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMaskMadd52hiEpu64(a, K, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    var expected = mmSet1Epi64x(11030549757952L)
    expected = mmMaskBlendEpi64(K, a, expected)
    assertEqM128i(expected, actual)
}

public fun testMmMaskzMadd52hiEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMaskzMadd52hiEpu64(K, a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) >> 52)
    var expected = mmSet1Epi64x(11030549757952L)
    expected = mmMaskBlendEpi64(K, mmSetzeroSi128(), expected)
    assertEqM128i(expected, actual)
}

public fun testMmMadd52loAvxEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMadd52loAvxEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    val expected = mmSet1Epi64x(100055558127628L)
    assertEqM128i(expected, actual)
}

public fun testMmMadd52loEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMadd52loEpu64(a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    val expected = mmSet1Epi64x(100055558127628L)
    assertEqM128i(expected, actual)
}

public fun testMmMaskMadd52loEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMaskMadd52loEpu64(a, K, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    var expected = mmSet1Epi64x(100055558127628L)
    expected = mmMaskBlendEpi64(K, a, expected)
    assertEqM128i(expected, actual)
}

public fun testMmMaskzMadd52loEpu64() {
    val a = mmSet1Epi64x(10L shl 40)
    val b = mmSet1Epi64x((11L shl 40) + 4L)
    val c = mmSet1Epi64x((12L shl 40) + 3L)
    val actual = mmMaskzMadd52loEpu64(K, a, b, c)
    // (10 << 40) + ((((11 << 40) + 4) * ((12 << 40) + 3)) % (1 << 52))
    var expected = mmSet1Epi64x(100055558127628L)
    expected = mmMaskBlendEpi64(K, mmSetzeroSi128(), expected)
    assertEqM128i(expected, actual)
}
