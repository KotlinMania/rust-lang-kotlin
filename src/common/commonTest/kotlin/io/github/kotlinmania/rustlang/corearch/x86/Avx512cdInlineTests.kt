// port-lint: tests x86/avx512cd.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.Mmask16
import io.github.kotlinmania.rustlang.x86.Mmask8
import io.github.kotlinmania.rustlang.x86.mm256Set1Epi32
import io.github.kotlinmania.rustlang.x86.mm256Set1Epi64x
import io.github.kotlinmania.rustlang.x86.mm256SetEpi32
import io.github.kotlinmania.rustlang.x86.mm256SetEpi64x
import io.github.kotlinmania.rustlang.x86.mm256SetzeroSi256
import io.github.kotlinmania.rustlang.x86.mm512Set1Epi32
import io.github.kotlinmania.rustlang.x86.mm512Set1Epi64
import io.github.kotlinmania.rustlang.x86.mm512SetEpi32
import io.github.kotlinmania.rustlang.x86.mm512SetEpi64
import io.github.kotlinmania.rustlang.x86.mm512SetzeroSi512
import io.github.kotlinmania.rustlang.x86.mmSet1Epi32
import io.github.kotlinmania.rustlang.x86.mmSet1Epi64x
import io.github.kotlinmania.rustlang.x86.mmSetEpi32
import io.github.kotlinmania.rustlang.x86.mmSetEpi64x
import io.github.kotlinmania.rustlang.x86.mmSetzeroSi128

public fun testMm512BroadcastmwEpi32() {
    val a: Mmask16 = 2u
    val r = mm512BroadcastmwEpi32(a)
    val e = mm512Set1Epi32(2)
    assertEqM512i(r, e)
}

public fun testMm256BroadcastmwEpi32() {
    val a: Mmask16 = 2u
    val r = mm256BroadcastmwEpi32(a)
    val e = mm256Set1Epi32(2)
    assertEqM256i(r, e)
}

public fun testMmBroadcastmwEpi32() {
    val a: Mmask16 = 2u
    val r = mmBroadcastmwEpi32(a)
    val e = mmSet1Epi32(2)
    assertEqM128i(r, e)
}

public fun testMm512BroadcastmbEpi64() {
    val a: Mmask8 = 2u
    val r = mm512BroadcastmbEpi64(a)
    val e = mm512Set1Epi64(2L)
    assertEqM512i(r, e)
}

public fun testMm256BroadcastmbEpi64() {
    val a: Mmask8 = 2u
    val r = mm256BroadcastmbEpi64(a)
    val e = mm256Set1Epi64x(2L)
    assertEqM256i(r, e)
}

public fun testMmBroadcastmbEpi64() {
    val a: Mmask8 = 2u
    val r = mmBroadcastmbEpi64(a)
    val e = mmSet1Epi64x(2L)
    assertEqM128i(r, e)
}

public fun testMm512ConflictEpi32() {
    val a = mm512Set1Epi32(1)
    val r = mm512ConflictEpi32(a)
    val e = mm512SetEpi32(1 shl 14 or 1 shl 13 or 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 13 or 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM512i(r, e)
}

public fun testMm512MaskConflictEpi32() {
    val a = mm512Set1Epi32(1)
    val r = mm512MaskConflictEpi32(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskConflictEpi32(a, 0xFFFFu, a)
    val e = mm512SetEpi32(1 shl 14 or 1 shl 13 or 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 13 or 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzConflictEpi32() {
    val a = mm512Set1Epi32(1)
    val r = mm512MaskzConflictEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzConflictEpi32(0xFFFFu, a)
    val e = mm512SetEpi32(1 shl 14 or 1 shl 13 or 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 13 or 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 12 or 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 11 or 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 10 or 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 9 or 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 8 or 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 7 or 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM512i(r1, e)
}

public fun testMm256ConflictEpi32() {
    val a = mm256Set1Epi32(1)
    val r = mm256ConflictEpi32(a)
    val e = mm256SetEpi32(1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM256i(r, e)
}

public fun testMm256MaskConflictEpi32() {
    val a = mm256Set1Epi32(1)
    val r = mm256MaskConflictEpi32(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskConflictEpi32(a, 0xFFu, a)
    val e = mm256SetEpi32(1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzConflictEpi32() {
    val a = mm256Set1Epi32(1)
    val r = mm256MaskzConflictEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzConflictEpi32(0xFFu, a)
    val e = mm256SetEpi32(1 shl 6 or 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 5 or 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 4 or 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 3 or 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM256i(r1, e)
}

public fun testMmConflictEpi32() {
    val a = mmSet1Epi32(1)
    val r = mmConflictEpi32(a)
    val e = mmSetEpi32(1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM128i(r, e)
}

public fun testMmMaskConflictEpi32() {
    val a = mmSet1Epi32(1)
    val r = mmMaskConflictEpi32(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskConflictEpi32(a, 0xFu, a)
    val e = mmSetEpi32(1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzConflictEpi32() {
    val a = mmSet1Epi32(1)
    val r = mmMaskzConflictEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzConflictEpi32(0xFu, a)
    val e = mmSetEpi32(1 shl 2 or 1 shl 1 or 1 shl 0, 1 shl 1 or 1 shl 0, 1 shl 0, 0)
    assertEqM128i(r1, e)
}

public fun testMm512ConflictEpi64() {
    val a = mm512Set1Epi64(1L)
    val r = mm512ConflictEpi64(a)
    val e = mm512SetEpi64(1L shl 6 or 1L shl 5 or 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 5 or 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 1 or 1L shl 0, 1L shl 0, 0L)
    assertEqM512i(r, e)
}

public fun testMm512MaskConflictEpi64() {
    val a = mm512Set1Epi64(1L)
    val r = mm512MaskConflictEpi64(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskConflictEpi64(a, 0xFFu, a)
    val e = mm512SetEpi64(1L shl 6 or 1L shl 5 or 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 5 or 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 1 or 1L shl 0, 1L shl 0, 0L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzConflictEpi64() {
    val a = mm512Set1Epi64(1L)
    val r = mm512MaskzConflictEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzConflictEpi64(0xFFu, a)
    val e = mm512SetEpi64(1L shl 6 or 1L shl 5 or 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 5 or 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 4 or 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 3 or 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 1 or 1L shl 0, 1L shl 0, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256ConflictEpi64() {
    val a = mm256Set1Epi64x(1L)
    val r = mm256ConflictEpi64(a)
    val e = mm256SetEpi64x(1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 1 or 1L shl 0, 1L shl 0, 0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskConflictEpi64() {
    val a = mm256Set1Epi64x(1L)
    val r = mm256MaskConflictEpi64(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskConflictEpi64(a, 0xFu, a)
    val e = mm256SetEpi64x(1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 1 or 1L shl 0, 1L shl 0, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzConflictEpi64() {
    val a = mm256Set1Epi64x(1L)
    val r = mm256MaskzConflictEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzConflictEpi64(0xFu, a)
    val e = mm256SetEpi64x(1L shl 2 or 1L shl 1 or 1L shl 0, 1L shl 1 or 1L shl 0, 1L shl 0, 0L)
    assertEqM256i(r1, e)
}

public fun testMmConflictEpi64() {
    val a = mmSet1Epi64x(1L)
    val r = mmConflictEpi64(a)
    val e = mmSetEpi64x(1L shl 0, 0L)
    assertEqM128i(r, e)
}

public fun testMmMaskConflictEpi64() {
    val a = mmSet1Epi64x(1L)
    val r = mmMaskConflictEpi64(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskConflictEpi64(a, 0x3u, a)
    val e = mmSetEpi64x(1L shl 0, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzConflictEpi64() {
    val a = mmSet1Epi64x(1L)
    val r = mmMaskzConflictEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzConflictEpi64(0x3u, a)
    val e = mmSetEpi64x(1L shl 0, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512LzcntEpi32() {
    val a = mm512Set1Epi32(1)
    val r = mm512LzcntEpi32(a)
    val e = mm512Set1Epi32(31)
    assertEqM512i(r, e)
}

public fun testMm512MaskLzcntEpi32() {
    val a = mm512Set1Epi32(1)
    val r = mm512MaskLzcntEpi32(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskLzcntEpi32(a, 0xFFFFu, a)
    val e = mm512Set1Epi32(31)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzLzcntEpi32() {
    val a = mm512Set1Epi32(2)
    val r = mm512MaskzLzcntEpi32(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzLzcntEpi32(0xFFFFu, a)
    val e = mm512Set1Epi32(30)
    assertEqM512i(r1, e)
}

public fun testMm256LzcntEpi32() {
    val a = mm256Set1Epi32(1)
    val r = mm256LzcntEpi32(a)
    val e = mm256Set1Epi32(31)
    assertEqM256i(r, e)
}

public fun testMm256MaskLzcntEpi32() {
    val a = mm256Set1Epi32(1)
    val r = mm256MaskLzcntEpi32(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskLzcntEpi32(a, 0xFFu, a)
    val e = mm256Set1Epi32(31)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzLzcntEpi32() {
    val a = mm256Set1Epi32(1)
    val r = mm256MaskzLzcntEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzLzcntEpi32(0xFFu, a)
    val e = mm256Set1Epi32(31)
    assertEqM256i(r1, e)
}

public fun testMmLzcntEpi32() {
    val a = mmSet1Epi32(1)
    val r = mmLzcntEpi32(a)
    val e = mmSet1Epi32(31)
    assertEqM128i(r, e)
}

public fun testMmMaskLzcntEpi32() {
    val a = mmSet1Epi32(1)
    val r = mmMaskLzcntEpi32(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskLzcntEpi32(a, 0xFu, a)
    val e = mmSet1Epi32(31)
    assertEqM128i(r1, e)
}

public fun testMmMaskzLzcntEpi32() {
    val a = mmSet1Epi32(1)
    val r = mmMaskzLzcntEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzLzcntEpi32(0xFu, a)
    val e = mmSet1Epi32(31)
    assertEqM128i(r1, e)
}

public fun testMm512LzcntEpi64() {
    val a = mm512Set1Epi64(1L)
    val r = mm512LzcntEpi64(a)
    val e = mm512Set1Epi64(63L)
    assertEqM512i(r, e)
}

public fun testMm512MaskLzcntEpi64() {
    val a = mm512Set1Epi64(1L)
    val r = mm512MaskLzcntEpi64(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskLzcntEpi64(a, 0xFFu, a)
    val e = mm512Set1Epi64(63L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzLzcntEpi64() {
    val a = mm512Set1Epi64(2L)
    val r = mm512MaskzLzcntEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzLzcntEpi64(0xFFu, a)
    val e = mm512Set1Epi64(62L)
    assertEqM512i(r1, e)
}

public fun testMm256LzcntEpi64() {
    val a = mm256Set1Epi64x(1L)
    val r = mm256LzcntEpi64(a)
    val e = mm256Set1Epi64x(63L)
    assertEqM256i(r, e)
}

public fun testMm256MaskLzcntEpi64() {
    val a = mm256Set1Epi64x(1L)
    val r = mm256MaskLzcntEpi64(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskLzcntEpi64(a, 0xFu, a)
    val e = mm256Set1Epi64x(63L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzLzcntEpi64() {
    val a = mm256Set1Epi64x(1L)
    val r = mm256MaskzLzcntEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzLzcntEpi64(0xFu, a)
    val e = mm256Set1Epi64x(63L)
    assertEqM256i(r1, e)
}

public fun testMmLzcntEpi64() {
    val a = mmSet1Epi64x(1L)
    val r = mmLzcntEpi64(a)
    val e = mmSet1Epi64x(63L)
    assertEqM128i(r, e)
}

public fun testMmMaskLzcntEpi64() {
    val a = mmSet1Epi64x(1L)
    val r = mmMaskLzcntEpi64(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskLzcntEpi64(a, 0xFu, a)
    val e = mmSet1Epi64x(63L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzLzcntEpi64() {
    val a = mmSet1Epi64x(1L)
    val r = mmMaskzLzcntEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzLzcntEpi64(0xFu, a)
    val e = mmSet1Epi64x(63L)
    assertEqM128i(r1, e)
}
