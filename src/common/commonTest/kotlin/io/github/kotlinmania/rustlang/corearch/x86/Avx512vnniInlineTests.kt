// port-lint: tests x86/avx512vnni.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.mm256Set1Epi32
import io.github.kotlinmania.rustlang.x86.mm256SetzeroSi256
import io.github.kotlinmania.rustlang.x86.mm512Set1Epi32
import io.github.kotlinmania.rustlang.x86.mm512SetzeroSi512
import io.github.kotlinmania.rustlang.x86.mmSet1Epi32
import io.github.kotlinmania.rustlang.x86.mmSetzeroSi128

public fun testMm512DpwssdEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm512DpwssdEpi32(src, a, b)
    val e = mm512Set1Epi32(3)
    assertEqM512i(r, e)
}

public fun testMm512MaskDpwssdEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm512MaskDpwssdEpi32(src, 0x0u, a, b)
    assertEqM512i(r, src)
    val r1 = mm512MaskDpwssdEpi32(src, 0xFFFFu, a, b)
    val e = mm512Set1Epi32(3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzDpwssdEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm512MaskzDpwssdEpi32(0x0u, src, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzDpwssdEpi32(0xFFFFu, src, a, b)
    val e = mm512Set1Epi32(3)
    assertEqM512i(r1, e)
}

public fun testMm256DpwssdAvxEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwssdAvxEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMm256DpwssdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwssdEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMm256MaskDpwssdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256MaskDpwssdEpi32(src, 0x0u, a, b)
    assertEqM256i(r, src)
    val r1 = mm256MaskDpwssdEpi32(src, 0xFFu, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzDpwssdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256MaskzDpwssdEpi32(0x0u, src, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzDpwssdEpi32(0xFFu, src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r1, e)
}

public fun testMmDpwssdAvxEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwssdAvxEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMmDpwssdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwssdEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMmMaskDpwssdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmMaskDpwssdEpi32(src, 0x0u, a, b)
    assertEqM128i(r, src)
    val r1 = mmMaskDpwssdEpi32(src, 0xFu, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzDpwssdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmMaskzDpwssdEpi32(0x0u, src, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzDpwssdEpi32(0xFu, src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r1, e)
}

public fun testMm512DpwssdsEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm512DpwssdsEpi32(src, a, b)
    val e = mm512Set1Epi32(3)
    assertEqM512i(r, e)
}

public fun testMm512MaskDpwssdsEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm512MaskDpwssdsEpi32(src, 0x0u, a, b)
    assertEqM512i(r, src)
    val r1 = mm512MaskDpwssdsEpi32(src, 0xFFFFu, a, b)
    val e = mm512Set1Epi32(3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzDpwssdsEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm512MaskzDpwssdsEpi32(0x0u, src, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzDpwssdsEpi32(0xFFFFu, src, a, b)
    val e = mm512Set1Epi32(3)
    assertEqM512i(r1, e)
}

public fun testMm256DpwssdsAvxEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwssdsAvxEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMm256DpwssdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwssdsEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMm256MaskDpwssdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256MaskDpwssdsEpi32(src, 0x0u, a, b)
    assertEqM256i(r, src)
    val r1 = mm256MaskDpwssdsEpi32(src, 0xFFu, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzDpwssdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256MaskzDpwssdsEpi32(0x0u, src, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzDpwssdsEpi32(0xFFu, src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r1, e)
}

public fun testMmDpwssdsAvxEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwssdsAvxEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMmDpwssdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwssdsEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMmMaskDpwssdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmMaskDpwssdsEpi32(src, 0x0u, a, b)
    assertEqM128i(r, src)
    val r1 = mmMaskDpwssdsEpi32(src, 0xFu, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r1, e)
}

public fun testMmMaskzDpwssdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmMaskzDpwssdsEpi32(0x0u, src, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzDpwssdsEpi32(0xFu, src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r1, e)
}

public fun testMm512DpbusdEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm512DpbusdEpi32(src, a, b)
    val e = mm512Set1Epi32(5)
    assertEqM512i(r, e)
}

public fun testMm512MaskDpbusdEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm512MaskDpbusdEpi32(src, 0x0u, a, b)
    assertEqM512i(r, src)
    val r1 = mm512MaskDpbusdEpi32(src, 0xFFFFu, a, b)
    val e = mm512Set1Epi32(5)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzDpbusdEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm512MaskzDpbusdEpi32(0x0u, src, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzDpbusdEpi32(0xFFFFu, src, a, b)
    val e = mm512Set1Epi32(5)
    assertEqM512i(r1, e)
}

public fun testMm256DpbusdAvxEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbusdAvxEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMm256DpbusdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbusdEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMm256MaskDpbusdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256MaskDpbusdEpi32(src, 0x0u, a, b)
    assertEqM256i(r, src)
    val r1 = mm256MaskDpbusdEpi32(src, 0xFFu, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzDpbusdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256MaskzDpbusdEpi32(0x0u, src, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzDpbusdEpi32(0xFFu, src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r1, e)
}

public fun testMmDpbusdAvxEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbusdAvxEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMmDpbusdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbusdEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMmMaskDpbusdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmMaskDpbusdEpi32(src, 0x0u, a, b)
    assertEqM128i(r, src)
    val r1 = mmMaskDpbusdEpi32(src, 0xFu, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r1, e)
}

public fun testMmMaskzDpbusdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmMaskzDpbusdEpi32(0x0u, src, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzDpbusdEpi32(0xFu, src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r1, e)
}

public fun testMm512DpbusdsEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm512DpbusdsEpi32(src, a, b)
    val e = mm512Set1Epi32(5)
    assertEqM512i(r, e)
}

public fun testMm512MaskDpbusdsEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm512MaskDpbusdsEpi32(src, 0x0u, a, b)
    assertEqM512i(r, src)
    val r1 = mm512MaskDpbusdsEpi32(src, 0xFFFFu, a, b)
    val e = mm512Set1Epi32(5)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzDpbusdsEpi32() {
    val src = mm512Set1Epi32(1)
    val a = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm512Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm512MaskzDpbusdsEpi32(0x0u, src, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzDpbusdsEpi32(0xFFFFu, src, a, b)
    val e = mm512Set1Epi32(5)
    assertEqM512i(r1, e)
}

public fun testMm256DpbusdsAvxEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbusdsAvxEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMm256DpbusdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbusdsEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMm256MaskDpbusdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256MaskDpbusdsEpi32(src, 0x0u, a, b)
    assertEqM256i(r, src)
    val r1 = mm256MaskDpbusdsEpi32(src, 0xFFu, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzDpbusdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256MaskzDpbusdsEpi32(0x0u, src, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzDpbusdsEpi32(0xFFu, src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r1, e)
}

public fun testMmDpbusdsAvxEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbusdsAvxEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMmDpbusdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbusdsEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMmMaskDpbusdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmMaskDpbusdsEpi32(src, 0x0u, a, b)
    assertEqM128i(r, src)
    val r1 = mmMaskDpbusdsEpi32(src, 0xFu, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r1, e)
}

public fun testMmMaskzDpbusdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmMaskzDpbusdsEpi32(0x0u, src, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzDpbusdsEpi32(0xFu, src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r1, e)
}

public fun testMmDpbssdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbssdEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMm256DpbssdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbssdEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMmDpbssdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbssdsEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMm256DpbssdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbssdsEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMmDpbsudEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbsudEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMm256DpbsudEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbsudEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMmDpbsudsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbsudsEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMm256DpbsudsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbsudsEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMmDpbuudEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbuudEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMm256DpbuudEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbuudEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMmDpbuudsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mmDpbuudsEpi32(src, a, b)
    val e = mmSet1Epi32(5)
    assertEqM128i(r, e)
}

public fun testMm256DpbuudsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 24 or 1 shl 16 or 1 shl 8 or 1 shl 0)
    val r = mm256DpbuudsEpi32(src, a, b)
    val e = mm256Set1Epi32(5)
    assertEqM256i(r, e)
}

public fun testMmDpwsudEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwsudEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMm256DpwsudEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwsudEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMmDpwsudsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwsudsEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMm256DpwsudsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwsudsEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMmDpwusdEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwusdEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMm256DpwusdEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwusdEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMmDpwusdsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwusdsEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMm256DpwusdsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwusdsEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMmDpwuudEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwuudEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMm256DpwuudEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwuudEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}

public fun testMmDpwuudsEpi32() {
    val src = mmSet1Epi32(1)
    val a = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val b = mmSet1Epi32(1 shl 16 or 1 shl 0)
    val r = mmDpwuudsEpi32(src, a, b)
    val e = mmSet1Epi32(3)
    assertEqM128i(r, e)
}

public fun testMm256DpwuudsEpi32() {
    val src = mm256Set1Epi32(1)
    val a = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val b = mm256Set1Epi32(1 shl 16 or 1 shl 0)
    val r = mm256DpwuudsEpi32(src, a, b)
    val e = mm256Set1Epi32(3)
    assertEqM256i(r, e)
}
