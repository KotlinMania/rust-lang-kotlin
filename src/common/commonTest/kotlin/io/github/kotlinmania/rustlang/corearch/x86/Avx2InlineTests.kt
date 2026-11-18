// port-lint: tests x86/avx2.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.corearch.x8664.mm256InsertEpi64
import io.github.kotlinmania.rustlang.corearch.x8664.mmInsertEpi64

// Advanced Vector Extensions 2 (AVX)
//
// AVX2 expands most AVX commands to 256-bit wide vector registers and
// adds [FMA](https://en.wikipedia.org/wiki/FusedMultiply-accumulate).
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref].
// - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
//   System Instructions][amd64Ref].
//
// Wikipedia's [AVX][wikiAvx] and [FMA][wikiFma] pages provide a quick
// overview of the instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wikiAvx]: https://en.wikipedia.org/wiki/AdvancedVectorExtensions
// [wikiFma]: https://en.wikipedia.org/wiki/FusedMultiply-accumulate

public fun testMm256AbsEpi32() {
    val a = mm256SetrEpi32(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE, 100, -100, -32)
    val r = mm256AbsEpi32(a)
    val e = mm256SetrEpi32(0, 1, 1, Int.MAX_VALUE, (Int.MAX_VALUE + 1), 100, 100, 32)
    assertEqM256i(r, e)
}

public fun testMm256AbsEpi16() {
    val a = mm256SetrEpi16(0, 1, (-1).toShort(), 2, (-2).toShort(), 3, (-3).toShort(), 4, (-4).toShort(), 5, (-5).toShort(), Short.MAX_VALUE, Short.MIN_VALUE, 100, (-100).toShort(), (-32).toShort())
    val r = mm256AbsEpi16(a)
    val e = mm256SetrEpi16(0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, Short.MAX_VALUE, (Short.MAX_VALUE + (1.toShort())).toShort(), 100, 100, 32)
    assertEqM256i(r, e)
}

public fun testMm256AbsEpi8() {
    val a = mm256SetrEpi8(0, 1, (-1).toByte(), 2, (-2).toByte(), 3, (-3).toByte(), 4, (-4).toByte(), 5, (-5).toByte(), Byte.MAX_VALUE, Byte.MIN_VALUE, 100, (-100).toByte(), (-32).toByte(), 0, 1, (-1).toByte(), 2, (-2).toByte(), 3, (-3).toByte(), 4, (-4).toByte(), 5, (-5).toByte(), Byte.MAX_VALUE, Byte.MIN_VALUE, 100, (-100).toByte(), (-32).toByte())
    val r = mm256AbsEpi8(a)
    val e = mm256SetrEpi8(0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, Byte.MAX_VALUE, (Byte.MAX_VALUE + (1.toByte())).toByte(), 100, 100, 32, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, Byte.MAX_VALUE, (Byte.MAX_VALUE + (1.toByte())).toByte(), 100, 100, 32)
    assertEqM256i(r, e)
}

public fun testMm256AddEpi64() {
    val a = mm256SetrEpi64x(-10L, 0L, 100L, 1000000000L)
    val b = mm256SetrEpi64x(-1L, 0L, 1L, 2L)
    val r = mm256AddEpi64(a, b)
    val e = mm256SetrEpi64x(-11L, 0L, 101L, 1000000002L)
    assertEqM256i(r, e)
}

public fun testMm256AddEpi32() {
    val a = mm256SetrEpi32(-1, 0, 1, 2, 3, 4, 5, 6)
    val b = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256AddEpi32(a, b)
    val e = mm256SetrEpi32(0, 2, 4, 6, 8, 10, 12, 14)
    assertEqM256i(r, e)
}

public fun testMm256AddEpi16() {
    val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256AddEpi16(a, b)
    val e = mm256SetrEpi16(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)
    assertEqM256i(r, e)
}

public fun testMm256AddEpi8() {
    val a = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val b = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = mm256AddEpi8(a, b)
    val e = mm256SetrEpi8(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62)
    assertEqM256i(r, e)
}

public fun testMm256AddsEpi8() {
    val a = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val b = mm256SetrEpi8(32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm256AddsEpi8(a, b)
    val e = mm256SetrEpi8(32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94)
    assertEqM256i(r, e)
}

public fun testMm256AddsEpi8SaturatePositive() {
    val a = mm256Set1Epi8(0x7F)
    val b = mm256Set1Epi8(1)
    val r = mm256AddsEpi8(a, b)
    assertEqM256i(r, a)
}

public fun testMm256AddsEpi8SaturateNegative() {
    val a = mm256Set1Epi8((-0x80).toByte())
    val b = mm256Set1Epi8((-1).toByte())
    val r = mm256AddsEpi8(a, b)
    assertEqM256i(r, a)
}

public fun testMm256AddsEpi16() {
    val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetrEpi16(32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47)
    val r = mm256AddsEpi16(a, b)
    val e = mm256SetrEpi16(32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62)
    assertEqM256i(r, e)
}

public fun testMm256AddsEpi16SaturatePositive() {
    val a = mm256Set1Epi16(0x7FFF)
    val b = mm256Set1Epi16(1)
    val r = mm256AddsEpi16(a, b)
    assertEqM256i(r, a)
}

public fun testMm256AddsEpi16SaturateNegative() {
    val a = mm256Set1Epi16((-0x8000).toShort())
    val b = mm256Set1Epi16((-1).toShort())
    val r = mm256AddsEpi16(a, b)
    assertEqM256i(r, a)
}

public fun testMm256AddsEpu8() {
    val a = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val b = mm256SetrEpi8(32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63)
    val r = mm256AddsEpu8(a, b)
    val e = mm256SetrEpi8(32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94)
    assertEqM256i(r, e)
}

public fun testMm256AddsEpu8Saturate() {
    val a = mm256Set1Epi8((0.inv()).toByte())
    val b = mm256Set1Epi8(1)
    val r = mm256AddsEpu8(a, b)
    assertEqM256i(r, a)
}

public fun testMm256AddsEpu16() {
    val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetrEpi16(32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47)
    val r = mm256AddsEpu16(a, b)
    val e = mm256SetrEpi16(32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62)
    assertEqM256i(r, e)
}

public fun testMm256AddsEpu16Saturate() {
    val a = mm256Set1Epi16((0.inv()).toShort())
    val b = mm256Set1Epi16(1)
    val r = mm256AddsEpu16(a, b)
    assertEqM256i(r, a)
}

public fun testMm256AndSi256() {
    val a = mm256Set1Epi8(5)
    val b = mm256Set1Epi8(3)
    val got = mm256AndSi256(a, b)
    assertEqM256i(got, mm256Set1Epi8(1))
}

public fun testMm256AndnotSi256() {
    val a = mm256Set1Epi8(5)
    val b = mm256Set1Epi8(3)
    val got = mm256AndnotSi256(a, b)
    assertEqM256i(got, mm256Set1Epi8(2))
}

public fun testMm256AvgEpu8() {
    val (a, b) = Pair(mm256Set1Epi8(3), mm256Set1Epi8(9))
    val r = mm256AvgEpu8(a, b)
    assertEqM256i(r, mm256Set1Epi8(6))
}

public fun testMm256AvgEpu16() {
    val (a, b) = Pair(mm256Set1Epi16(3), mm256Set1Epi16(9))
    val r = mm256AvgEpu16(a, b)
    assertEqM256i(r, mm256Set1Epi16(6))
}

public fun testMmBlendEpi32() {
    val (a, b) = Pair(mmSet1Epi32(3), mmSet1Epi32(9))
    val e = mmSetrEpi32(9, 3, 3, 3)
    val r = mmBlendEpi32(a, b, 0x01)
    assertEqM128i(r, e)
    val r1 = mmBlendEpi32(b, a, 0x0E)
    assertEqM128i(r1, e)
}

public fun testMm256BlendEpi32() {
    val (a, b) = Pair(mm256Set1Epi32(3), mm256Set1Epi32(9))
    val e = mm256SetrEpi32(9, 3, 3, 3, 3, 3, 3, 3)
    val r = mm256BlendEpi32(a, b, 0x01)
    assertEqM256i(r, e)
    val e1 = mm256SetrEpi32(3, 9, 3, 3, 3, 3, 3, 9)
    val r1 = mm256BlendEpi32(a, b, 0x82)
    assertEqM256i(r1, e1)
    val e2 = mm256SetrEpi32(3, 3, 9, 9, 9, 9, 9, 3)
    val r2 = mm256BlendEpi32(a, b, 0x7C)
    assertEqM256i(r2, e2)
}

public fun testMm256BlendEpi16() {
    val (a, b) = Pair(mm256Set1Epi16(3), mm256Set1Epi16(9))
    val e = mm256SetrEpi16(9, 3, 3, 3, 3, 3, 3, 3, 9, 3, 3, 3, 3, 3, 3, 3)
    val r = mm256BlendEpi16(a, b, 0x01)
    assertEqM256i(r, e)
    val r1 = mm256BlendEpi16(b, a, 0xFE)
    assertEqM256i(r1, e)
}

public fun testMm256BlendvEpi8() {
    val (a, b) = Pair(mm256Set1Epi8(4), mm256Set1Epi8(2))
    val mask = mm256InsertEpi8(mm256Set1Epi8(0), (-1).toByte(), 2)
    val e = mm256InsertEpi8(mm256Set1Epi8(4), 2, 2)
    val r = mm256BlendvEpi8(a, b, mask)
    assertEqM256i(r, e)
}

public fun testMmBroadcastbEpi8() {
    val a = mmInsertEpi8(mmSet1Epi8(0x00), 0x2a, 0)
    val res = mmBroadcastbEpi8(a)
    assertEqM128i(res, mmSet1Epi8(0x2a))
}

public fun testMm256BroadcastbEpi8() {
    val a = mmInsertEpi8(mmSet1Epi8(0x00), 0x2a, 0)
    val res = mm256BroadcastbEpi8(a)
    assertEqM256i(res, mm256Set1Epi8(0x2a))
}

public fun testMmBroadcastdEpi32() {
    val a = mmSetrEpi32(0x2a, 0x8000000, 0, 0)
    val res = mmBroadcastdEpi32(a)
    assertEqM128i(res, mmSet1Epi32(0x2a))
}

public fun testMm256BroadcastdEpi32() {
    val a = mmSetrEpi32(0x2a, 0x8000000, 0, 0)
    val res = mm256BroadcastdEpi32(a)
    assertEqM256i(res, mm256Set1Epi32(0x2a))
}

public fun testMmBroadcastqEpi64() {
    val a = mmSetrEpi64x(0x1ffffffff, 0)
    val res = mmBroadcastqEpi64(a)
    assertEqM128i(res, mmSet1Epi64x(0x1ffffffffL))
}

public fun testMm256BroadcastqEpi64() {
    val a = mmSetrEpi64x(0x1ffffffff, 0)
    val res = mm256BroadcastqEpi64(a)
    assertEqM256i(res, mm256Set1Epi64x(0x1ffffffffL))
}

public fun testMmBroadcastsdPd() {
    val a = mmSetrPd(6.88, 3.44)
    val res = mmBroadcastsdPd(a)
    assertEqM128d(res, mmSet1Pd(6.88))
}

public fun testMm256BroadcastsdPd() {
    val a = mmSetrPd(6.88, 3.44)
    val res = mm256BroadcastsdPd(a)
    assertEqM256d(res, mm256Set1Pd(6.88))
}

public fun testMmBroadcastsi128Si256() {
    val a = mmSetrEpi64x(0x0987654321012334, 0x5678909876543210)
    val res = mmBroadcastsi128Si256(a)
    val retval = mm256SetrEpi64x(0x0987654321012334L, 0x5678909876543210L, 0x0987654321012334L, 0x5678909876543210L)
    assertEqM256i(res, retval)
}

public fun testMm256Broadcastsi128Si256() {
    val a = mmSetrEpi64x(0x0987654321012334, 0x5678909876543210)
    val res = mm256Broadcastsi128Si256(a)
    val retval = mm256SetrEpi64x(0x0987654321012334L, 0x5678909876543210L, 0x0987654321012334L, 0x5678909876543210L)
    assertEqM256i(res, retval)
}

public fun testMmBroadcastssPs() {
    val a = mmSetrPs(6.88f, 3.44f, 0.0f, 0.0f)
    val res = mmBroadcastssPs(a)
    assertEqM128(res, mmSet1Ps(6.88f))
}

public fun testMm256BroadcastssPs() {
    val a = mmSetrPs(6.88f, 3.44f, 0.0f, 0.0f)
    val res = mm256BroadcastssPs(a)
    assertEqM256(res, mm256Set1Ps(6.88f))
}

public fun testMmBroadcastwEpi16() {
    val a = mmInsertEpi16(mmSet1Epi16(0x2a), 0x22b, 0)
    val res = mmBroadcastwEpi16(a)
    assertEqM128i(res, mmSet1Epi16(0x22b))
}

public fun testMm256BroadcastwEpi16() {
    val a = mmInsertEpi16(mmSet1Epi16(0x2a), 0x22b, 0)
    val res = mm256BroadcastwEpi16(a)
    assertEqM256i(res, mm256Set1Epi16(0x22b))
}

public fun testMm256CmpeqEpi8() {
    val a = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val b = mm256SetrEpi8(31, 30, 2, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256CmpeqEpi8(a, b)
    assertEqM256i(r, mm256InsertEpi8(mm256Set1Epi8(0), (0.inv()).toByte(), 2))
}

public fun testMm256CmpeqEpi16() {
    val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = mm256SetrEpi16(15, 14, 2, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    val r = mm256CmpeqEpi16(a, b)
    assertEqM256i(r, mm256InsertEpi16(mm256Set1Epi16(0), (0.inv()).toShort(), 2))
}

public fun testMm256CmpeqEpi32() {
    val a = mm256SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    val b = mm256SetrEpi32(7, 6, 2, 4, 3, 2, 1, 0)
    val r = mm256CmpeqEpi32(a, b)
    val e = mm256Set1Epi32(0)
    val e1 = mm256InsertEpi32(e, 0.inv(), 2)
    assertEqM256i(r, e1)
}

public fun testMm256CmpeqEpi64() {
    val a = mm256SetrEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetrEpi64x(3L, 2L, 2L, 0L)
    val r = mm256CmpeqEpi64(a, b)
    assertEqM256i(r, mm256InsertEpi64(mm256Set1Epi64x(0L), 0L.inv(), 2))
}

public fun testMm256CmpgtEpi8() {
    val a = mm256InsertEpi8(mm256Set1Epi8(0), 5, 0)
    val b = mm256Set1Epi8(0)
    val r = mm256CmpgtEpi8(a, b)
    assertEqM256i(r, mm256InsertEpi8(mm256Set1Epi8(0), (0.inv()).toByte(), 0))
}

public fun testMm256CmpgtEpi16() {
    val a = mm256InsertEpi16(mm256Set1Epi16(0), 5, 0)
    val b = mm256Set1Epi16(0)
    val r = mm256CmpgtEpi16(a, b)
    assertEqM256i(r, mm256InsertEpi16(mm256Set1Epi16(0), (0.inv()).toShort(), 0))
}

public fun testMm256CmpgtEpi32() {
    val a = mm256InsertEpi32(mm256Set1Epi32(0), 5, 0)
    val b = mm256Set1Epi32(0)
    val r = mm256CmpgtEpi32(a, b)
    assertEqM256i(r, mm256InsertEpi32(mm256Set1Epi32(0), 0.inv(), 0))
}

public fun testMm256CmpgtEpi64() {
    val a = mm256InsertEpi64(mm256Set1Epi64x(0L), 5L, 0)
    val b = mm256Set1Epi64x(0L)
    val r = mm256CmpgtEpi64(a, b)
    assertEqM256i(r, mm256InsertEpi64(mm256Set1Epi64x(0L), 0L.inv(), 0))
}

public fun testMm256Cvtepi8Epi16() {
    val a = mmSetrEpi8(0, 0, (-1).toByte(), 1, (-2).toByte(), 2, (-3).toByte(), 3, (-4).toByte(), 4, (-5).toByte(), 5, (-6).toByte(), 6, (-7).toByte(), 7)
    val r = mm256SetrEpi16(0, 0, (-1).toShort(), 1, (-2).toShort(), 2, (-3).toShort(), 3, (-4).toShort(), 4, (-5).toShort(), 5, (-6).toShort(), 6, (-7).toShort(), 7)
    assertEqM256i(r, mm256Cvtepi8Epi16(a))
}

public fun testMm256Cvtepi8Epi32() {
    val a = mmSetrEpi8(0, 0, (-1).toByte(), 1, (-2).toByte(), 2, (-3).toByte(), 3, (-4).toByte(), 4, (-5).toByte(), 5, (-6).toByte(), 6, (-7).toByte(), 7)
    val r = mm256SetrEpi32(0, 0, -1, 1, -2, 2, -3, 3)
    assertEqM256i(r, mm256Cvtepi8Epi32(a))
}

public fun testMm256Cvtepi8Epi64() {
    val a = mmSetrEpi8(0, 0, (-1).toByte(), 1, (-2).toByte(), 2, (-3).toByte(), 3, (-4).toByte(), 4, (-5).toByte(), 5, (-6).toByte(), 6, (-7).toByte(), 7)
    val r = mm256SetrEpi64x(0L, 0L, -1L, 1L)
    assertEqM256i(r, mm256Cvtepi8Epi64(a))
}

public fun testMm256Cvtepi16Epi32() {
    val a = mmSetrEpi16(0, 0, (-1).toShort(), 1, (-2).toShort(), 2, (-3).toShort(), 3)
    val r = mm256SetrEpi32(0, 0, -1, 1, -2, 2, -3, 3)
    assertEqM256i(r, mm256Cvtepi16Epi32(a))
}

public fun testMm256Cvtepi16Epi64() {
    val a = mmSetrEpi16(0, 0, (-1).toShort(), 1, (-2).toShort(), 2, (-3).toShort(), 3)
    val r = mm256SetrEpi64x(0L, 0L, -1L, 1L)
    assertEqM256i(r, mm256Cvtepi16Epi64(a))
}

public fun testMm256Cvtepi32Epi64() {
    val a = mmSetrEpi32(0, 0, -1, 1)
    val r = mm256SetrEpi64x(0L, 0L, -1L, 1L)
    assertEqM256i(r, mm256Cvtepi32Epi64(a))
}

public fun testMm256Cvtepu16Epi32() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM256i(r, mm256Cvtepu16Epi32(a))
}

public fun testMm256Cvtepu16Epi64() {
    val a = mmSetrEpi16(0, 1, 2, 3, 4, 5, 6, 7)
    val r = mm256SetrEpi64x(0L, 1L, 2L, 3L)
    assertEqM256i(r, mm256Cvtepu16Epi64(a))
}

public fun testMm256Cvtepu32Epi64() {
    val a = mmSetrEpi32(0, 1, 2, 3)
    val r = mm256SetrEpi64x(0L, 1L, 2L, 3L)
    assertEqM256i(r, mm256Cvtepu32Epi64(a))
}

public fun testMm256Cvtepu8Epi16() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r, mm256Cvtepu8Epi16(a))
}

public fun testMm256Cvtepu8Epi32() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7)
    assertEqM256i(r, mm256Cvtepu8Epi32(a))
}

public fun testMm256Cvtepu8Epi64() {
    val a = mmSetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256SetrEpi64x(0L, 1L, 2L, 3L)
    assertEqM256i(r, mm256Cvtepu8Epi64(a))
}

public fun testMm256Extracti128Si256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Extracti128Si256(a, 1)
    val e = mmSetrEpi64x(3, 4)
    assertEqM128i(r, e)
}

public fun testMm256HaddEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256HaddEpi16(a, b)
    val e = mm256SetrEpi16(4, 4, 4, 4, 8, 8, 8, 8, 4, 4, 4, 4, 8, 8, 8, 8)
    assertEqM256i(r, e)
}

public fun testMm256HaddEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256HaddEpi32(a, b)
    val e = mm256SetrEpi32(4, 4, 8, 8, 4, 4, 8, 8)
    assertEqM256i(r, e)
}

public fun testMm256HaddsEpi16() {
    val a = mm256Set1Epi16(2)
    val a1 = mm256InsertEpi16(a, 0x7fff, 0)
    val a2 = mm256InsertEpi16(a1, 1, 1)
    val b = mm256Set1Epi16(4)
    val r = mm256HaddsEpi16(a2, b)
    val e = mm256SetrEpi16(0x7FFF, 4, 4, 4, 8, 8, 8, 8, 4, 4, 4, 4, 8, 8, 8, 8)
    assertEqM256i(r, e)
}

public fun testMm256HsubEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256HsubEpi16(a, b)
    val e = mm256Set1Epi16(0)
    assertEqM256i(r, e)
}

public fun testMm256HsubEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256HsubEpi32(a, b)
    val e = mm256Set1Epi32(0)
    assertEqM256i(r, e)
}

public fun testMm256HsubsEpi16() {
    val a = mm256Set1Epi16(2)
    val a1 = mm256InsertEpi16(a, 0x7fff, 0)
    val a2 = mm256InsertEpi16(a1, (-1).toShort(), 1)
    val b = mm256Set1Epi16(4)
    val r = mm256HsubsEpi16(a2, b)
    val e = mm256InsertEpi16(mm256Set1Epi16(0), 0x7FFF, 0)
    assertEqM256i(r, e)
}

public fun testMm256MaddEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MaddEpi16(a, b)
    val e = mm256Set1Epi32(16)
    assertEqM256i(r, e)
}

public fun testMm256MaddEpi16MulOne(v: M256i): M256i {
    // This is a trick used in the adler32 algorithm to get a widening addition. The
    // multiplication by 1 is trivial, but must not be optimized out because then the vpmaddwd
    // instruction is no longer selected. The assertInstr verifies that this is the case.
    val oneV = mm256Set1Epi16(1)
    return mm256MaddEpi16(v, oneV)
}

public fun testMm256MaddEpi16Shl(v: M256i): M256i {
    // This is a trick used in the base64 algorithm to get a widening addition. Instead of a
    // multiplication, a vector shl is used. In LLVM 22 that breaks the pattern recognition
    // for the automatic optimization to vpmaddwd.
    val shiftValue = mm256Set1Epi32(12)
    return mm256MaddEpi16(v, shiftValue)
}

public fun testMm256Inserti128Si256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetrEpi64x(7, 8)
    val r = mm256Inserti128Si256(a, b, 1)
    val e = mm256SetrEpi64x(1L, 2L, 7L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaddubsEpi16() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MaddubsEpi16(a, b)
    val e = mm256Set1Epi16(16)
    assertEqM256i(r, e)
}

public fun testMmMaskloadEpi32() {
    val localStorageL4664C8 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3, 4))
    val a = ConstPtr(localStorageL4664C8.elementMutPtr(0).rawAddress)
    val mask = mmSetrEpi32(-1, 0, 0, -1)
    val r = mmMaskloadEpi32(a, mask)
    val e = mmSetrEpi32(1, 0, 0, 4)
    assertEqM128i(r, e)
}

public fun testMm256MaskloadEpi32() {
    val localStorageL4674C8 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8))
    val a = ConstPtr(localStorageL4674C8.elementMutPtr(0).rawAddress)
    val mask = mm256SetrEpi32(-1, 0, 0, -1, 0, -1, -1, 0)
    val r = mm256MaskloadEpi32(a, mask)
    val e = mm256SetrEpi32(1, 0, 0, 4, 0, 6, 7, 0)
    assertEqM256i(r, e)
}

public fun testMmMaskloadEpi64() {
    val localStorageL4684C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L))
    val a = ConstPtr(localStorageL4684C8.elementMutPtr(0).rawAddress)
    val mask = mmSetrEpi64x(0, -1)
    val r = mmMaskloadEpi64(a, mask)
    val e = mmSetrEpi64x(0, 2)
    assertEqM128i(r, e)
}

public fun testMm256MaskloadEpi64() {
    val localStorageL4694C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L, 3L, 4L))
    val a = ConstPtr(localStorageL4694C8.elementMutPtr(0).rawAddress)
    val mask = mm256SetrEpi64x(0L, -1L, -1L, 0L)
    val r = mm256MaskloadEpi64(a, mask)
    val e = mm256SetrEpi64x(0L, 2L, 3L, 0L)
    assertEqM256i(r, e)
}

public fun testMmMaskstoreEpi32() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val arr = mutableListOf(-1, -1, -1, -1)
    val mask = mmSetrEpi32(-1, 0, 0, -1)
    run {
        mmMaskstoreEpi32(arr.asMutPtr(), mask, a)
    }
    val e = listOf(1, -1, -1, 4)
    check(arr == e)
}

public fun testMm256MaskstoreEpi32() {
    val a = mm256SetrEpi32(1, 0x6d726f, 3, 42, 0x777161, 6, 7, 8)
    val arr = mutableListOf(-1, -1, -1, 0x776173, -1, 0x68657265, -1, -1)
    val mask = mm256SetrEpi32(-1, 0, 0, -1, 0, -1, -1, 0)
    run {
        mm256MaskstoreEpi32(arr.asMutPtr(), mask, a)
    }
    val e = listOf(1, -1, -1, 42, -1, 6, 7, -1)
    check(arr == e)
}

public fun testMmMaskstoreEpi64() {
    val a = mmSetrEpi64x(1L, 2L)
    val arr = longArrayOf(-1L, -1L)
    val mask = mmSetrEpi64x(0, -1)
    run {
        mmMaskstoreEpi64(arr.asMutPtr(), mask, a)
    }
    val e = listOf(-1, 2)
    check(arr == e)
}

public fun testMm256MaskstoreEpi64() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val arr = longArrayOf(-1L, -1L, -1L, -1L)
    val mask = mm256SetrEpi64x(0L, -1L, -1L, 0L)
    run {
        mm256MaskstoreEpi64(arr.asMutPtr(), mask, a)
    }
    val e = listOf(-1, 2, 3, -1)
    check(arr == e)
}

public fun testMm256MaxEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MaxEpi16(a, b)
    assertEqM256i(r, b)
}

public fun testMm256MaxEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256MaxEpi32(a, b)
    assertEqM256i(r, b)
}

public fun testMm256MaxEpi8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MaxEpi8(a, b)
    assertEqM256i(r, b)
}

public fun testMm256MaxEpu16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MaxEpu16(a, b)
    assertEqM256i(r, b)
}

public fun testMm256MaxEpu32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256MaxEpu32(a, b)
    assertEqM256i(r, b)
}

public fun testMm256MaxEpu8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MaxEpu8(a, b)
    assertEqM256i(r, b)
}

public fun testMm256MinEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MinEpi16(a, b)
    assertEqM256i(r, a)
}

public fun testMm256MinEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256MinEpi32(a, b)
    assertEqM256i(r, a)
}

public fun testMm256MinEpi8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MinEpi8(a, b)
    assertEqM256i(r, a)
}

public fun testMm256MinEpu16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MinEpu16(a, b)
    assertEqM256i(r, a)
}

public fun testMm256MinEpu32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256MinEpu32(a, b)
    assertEqM256i(r, a)
}

public fun testMm256MinEpu8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MinEpu8(a, b)
    assertEqM256i(r, a)
}

public fun testMm256MovemaskEpi8() {
    val a = mm256Set1Epi8((-1).toByte())
    val r = mm256MovemaskEpi8(a)
    val e = -1
    check(r == e)
}

public fun testMm256MpsadbwEpu8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256MpsadbwEpu8(a, b, 0)
    val e = mm256Set1Epi16(8)
    assertEqM256i(r, e)
}

public fun testMm256MulEpi32() {
    val a = mm256SetrEpi32(0, 0, 0, 0, 2, 2, 2, 2)
    val b = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MulEpi32(a, b)
    val e = mm256SetrEpi64x(0L, 0L, 10L, 14L)
    assertEqM256i(r, e)
}

public fun testMm256MulEpu32() {
    val a = mm256SetrEpi32(0, 0, 0, 0, 2, 2, 2, 2)
    val b = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MulEpu32(a, b)
    val e = mm256SetrEpi64x(0L, 0L, 10L, 14L)
    assertEqM256i(r, e)
}

public fun testMm256MulhiEpi16() {
    val a = mm256Set1Epi16(6535)
    val b = mm256Set1Epi16(6535)
    val r = mm256MulhiEpi16(a, b)
    val e = mm256Set1Epi16(651)
    assertEqM256i(r, e)
}

public fun testMm256MulhiEpu16() {
    val a = mm256Set1Epi16(6535)
    val b = mm256Set1Epi16(6535)
    val r = mm256MulhiEpu16(a, b)
    val e = mm256Set1Epi16(651)
    assertEqM256i(r, e)
}

public fun testMm256MulloEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MulloEpi16(a, b)
    val e = mm256Set1Epi16(8)
    assertEqM256i(r, e)
}

public fun testMm256MulloEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256MulloEpi32(a, b)
    val e = mm256Set1Epi32(8)
    assertEqM256i(r, e)
}

public fun testMm256MulhrsEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256MulloEpi16(a, b)
    val e = mm256Set1Epi16(8)
    assertEqM256i(r, e)
}

public fun testMm256OrSi256() {
    val a = mm256Set1Epi8((-1).toByte())
    val b = mm256Set1Epi8(0)
    val r = mm256OrSi256(a, b)
    assertEqM256i(r, a)
}

public fun testMm256PacksEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256PacksEpi16(a, b)
    val e = mm256SetrEpi8(2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4)
    assertEqM256i(r, e)
}

public fun testMm256PacksEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256PacksEpi32(a, b)
    val e = mm256SetrEpi16(2, 2, 2, 2, 4, 4, 4, 4, 2, 2, 2, 2, 4, 4, 4, 4)
    assertEqM256i(r, e)
}

public fun testMm256PackusEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16(4)
    val r = mm256PackusEpi16(a, b)
    val e = mm256SetrEpi8(2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4)
    assertEqM256i(r, e)
}

public fun testMm256PackusEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(4)
    val r = mm256PackusEpi32(a, b)
    val e = mm256SetrEpi16(2, 2, 2, 2, 4, 4, 4, 4, 2, 2, 2, 2, 4, 4, 4, 4)
    assertEqM256i(r, e)
}

public fun testMm256SadEpu8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8(4)
    val r = mm256SadEpu8(a, b)
    val e = mm256Set1Epi64x(16L)
    assertEqM256i(r, e)
}

public fun testMm256ShufflehiEpi16() {
    val a = mm256SetrEpi16(0, 1, 2, 3, 11, 22, 33, 44, 4, 5, 6, 7, 55, 66, 77, 88)
    val e = mm256SetrEpi16(0, 1, 2, 3, 44, 22, 22, 11, 4, 5, 6, 7, 88, 66, 66, 55)
    val r = mm256ShufflehiEpi16(a, 0x17)
    assertEqM256i(r, e)
}

public fun testMm256ShuffleloEpi16() {
    val a = mm256SetrEpi16(11, 22, 33, 44, 0, 1, 2, 3, 55, 66, 77, 88, 4, 5, 6, 7)
    val e = mm256SetrEpi16(44, 22, 22, 11, 0, 1, 2, 3, 88, 66, 66, 55, 4, 5, 6, 7)
    val r = mm256ShuffleloEpi16(a, 0x17)
    assertEqM256i(r, e)
}

public fun testMm256SignEpi16() {
    val a = mm256Set1Epi16(2)
    val b = mm256Set1Epi16((-1).toShort())
    val r = mm256SignEpi16(a, b)
    val e = mm256Set1Epi16((-2).toShort())
    assertEqM256i(r, e)
}

public fun testMm256SignEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(-1)
    val r = mm256SignEpi32(a, b)
    val e = mm256Set1Epi32(-2)
    assertEqM256i(r, e)
}

public fun testMm256SignEpi8() {
    val a = mm256Set1Epi8(2)
    val b = mm256Set1Epi8((-1).toByte())
    val r = mm256SignEpi8(a, b)
    val e = mm256Set1Epi8((-2).toByte())
    assertEqM256i(r, e)
}

public fun testMm256SllEpi16() {
    val a = mm256Set1Epi16(0xFF)
    val b = mmInsertEpi16(mmSet1Epi16(0), 4, 0)
    val r = mm256SllEpi16(a, b)
    assertEqM256i(r, mm256Set1Epi16(0xFF0))
}

public fun testMm256SllEpi32() {
    val a = mm256Set1Epi32(0xFFFF)
    val b = mmInsertEpi32(mmSet1Epi32(0), 4, 0)
    val r = mm256SllEpi32(a, b)
    assertEqM256i(r, mm256Set1Epi32(0xFFFF0))
}

public fun testMm256SllEpi64() {
    val a = mm256Set1Epi64x(0xFFFFFFFFL)
    val b = mmInsertEpi64(mmSet1Epi64x(0L), 4L, 0)
    val r = mm256SllEpi64(a, b)
    assertEqM256i(r, mm256Set1Epi64x(0xFFFFFFFF0L))
}

public fun testMm256SlliEpi16() {
    assertEqM256i(mm256SlliEpi16(mm256Set1Epi16(0xFF), 4), mm256Set1Epi16(0xFF0))
}

public fun testMm256SlliEpi32() {
    assertEqM256i(mm256SlliEpi32(mm256Set1Epi32(0xFFFF), 4), mm256Set1Epi32(0xFFFF0))
}

public fun testMm256SlliEpi64() {
    assertEqM256i(mm256SlliEpi64(mm256Set1Epi64x(0xFFFFFFFFL), 4), mm256Set1Epi64x(0xFFFFFFFF0L))
}

public fun testMm256SlliSi256() {
    val a = mm256Set1Epi64x(0xFFFFFFFFL)
    val r = mm256SlliSi256(a, 3)
    assertEqM256i(r, mm256Set1Epi64x(0xFFFFFFFF000000L))
}

public fun testMmSllvEpi32() {
    val a = mmSet1Epi32(2)
    val b = mmSet1Epi32(1)
    val r = mmSllvEpi32(a, b)
    val e = mmSet1Epi32(4)
    assertEqM128i(r, e)
}

public fun testMm256SllvEpi32() {
    val a = mm256Set1Epi32(2)
    val b = mm256Set1Epi32(1)
    val r = mm256SllvEpi32(a, b)
    val e = mm256Set1Epi32(4)
    assertEqM256i(r, e)
}

public fun testMmSllvEpi64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(1L, 2L)
    val r = mmSllvEpi64(a, b)
    // Compare with the scalar version of the same computation.
    val e = mmSetEpi64x(2L.unboundedShl(1), 3L.unboundedShl(2))
    assertEqM128i(r, e)
    // Compare with hardcoded output.
    val e1 = mmSetEpi64x(4L, 12L)
    assertEqM128i(r, e1)
    // The shift has unbounded semantics: if the shift amount
    // is >= the number of bits the result is 0.
    val a1 = mmSetEpi64x(1L, 2L)
    val b1 = mmSetEpi64x(64L, 65L)
    val r1 = mmSllvEpi64(a1, b1)
    val e2 = mmSetEpi64x(1L.unboundedShl(64), 2L.unboundedShl(65))
    assertEqM128i(r1, e2)
    val e3 = mmSetEpi64x(0L, 0L)
    assertEqM128i(r1, e3)
}

public fun testMm256SllvEpi64() {
    val a = mm256Set1Epi64x(2L)
    val b = mm256Set1Epi64x(1L)
    val r = mm256SllvEpi64(a, b)
    val e = mm256Set1Epi64x(4L)
    assertEqM256i(r, e)
}

public fun testMm256SraEpi16() {
    val a = mm256Set1Epi16((-1).toShort())
    val b = mmSetrEpi16(1, 0, 0, 0, 0, 0, 0, 0)
    val r = mm256SraEpi16(a, b)
    assertEqM256i(r, mm256Set1Epi16((-1).toShort()))
}

public fun testMm256SraEpi32() {
    val a = mm256Set1Epi32(-1)
    val b = mmInsertEpi32(mmSet1Epi32(0), 1, 0)
    val r = mm256SraEpi32(a, b)
    assertEqM256i(r, mm256Set1Epi32(-1))
}

public fun testMm256SraiEpi16() {
    assertEqM256i(mm256SraiEpi16(mm256Set1Epi16((-1).toShort()), 1), mm256Set1Epi16((-1).toShort()))
}

public fun testMm256SraiEpi32() {
    assertEqM256i(mm256SraiEpi32(mm256Set1Epi32(-1), 1), mm256Set1Epi32(-1))
}

public fun testMmSravEpi32() {
    val a = mmSetEpi32(16, -32, 64, -128)
    val b = mmSetEpi32(4, 3, 2, 1)
    val r = mmSravEpi32(a, b)
    val e = mmSetEpi32(1, -4, 16, -64)
    assertEqM128i(r, e)
    // The shift has unbounded semantics: if the shift amount
    // is >= the number of bits the result is -1.
    val a1 = mmSetEpi32(-16, -32, -64, -128)
    val b1 = mmSetEpi32(31, 32, 33, 0)
    val r1 = mmSravEpi32(a1, b1)
    // Compare with the scalar version of the same computation.
    val e1 = mmSetEpi32((-16).unboundedShr(31), (-32).unboundedShr(32), (-64).unboundedShr(33), (-128).unboundedShr(0))
    assertEqM128i(r1, e1)
    // Compare with hardcoded output.
    val e2 = mmSetEpi32(-1, -1, -1, -128)
    assertEqM128i(r1, e2)
}

public fun testMm256SravEpi32() {
    val a = mm256Set1Epi32(4)
    val count = mm256Set1Epi32(1)
    val r = mm256SravEpi32(a, count)
    val e = mm256Set1Epi32(2)
    assertEqM256i(r, e)
}

public fun testMm256SrliSi256() {
    val a = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm256SrliSi256(a, 3)
    val e = mm256SetrEpi8(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0, 0, 0, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 0, 0, 0)
    assertEqM256i(r, e)
}

public fun testMm256SrlEpi16() {
    val a = mm256Set1Epi16(0xFF)
    val b = mmInsertEpi16(mmSet1Epi16(0), 4, 0)
    val r = mm256SrlEpi16(a, b)
    assertEqM256i(r, mm256Set1Epi16(0xF))
}

public fun testMm256SrlEpi32() {
    val a = mm256Set1Epi32(0xFFFF)
    val b = mmInsertEpi32(mmSet1Epi32(0), 4, 0)
    val r = mm256SrlEpi32(a, b)
    assertEqM256i(r, mm256Set1Epi32(0xFFF))
}

public fun testMm256SrlEpi64() {
    val a = mm256Set1Epi64x(0xFFFFFFFFL)
    val b = mmSetrEpi64x(4, 0)
    val r = mm256SrlEpi64(a, b)
    assertEqM256i(r, mm256Set1Epi64x(0xFFFFFFFL))
}

public fun testMm256SrliEpi16() {
    assertEqM256i(mm256SrliEpi16(mm256Set1Epi16(0xFF), 4), mm256Set1Epi16(0xF))
}

public fun testMm256SrliEpi32() {
    assertEqM256i(mm256SrliEpi32(mm256Set1Epi32(0xFFFF), 4), mm256Set1Epi32(0xFFF))
}

public fun testMm256SrliEpi64() {
    assertEqM256i(mm256SrliEpi64(mm256Set1Epi64x(0xFFFFFFFFL), 4), mm256Set1Epi64x(0xFFFFFFFL))
}

public fun testMmSrlvEpi32() {
    val a = mmSet1Epi32(2)
    val count = mmSet1Epi32(1)
    val r = mmSrlvEpi32(a, count)
    val e = mmSet1Epi32(1)
    assertEqM128i(r, e)
}

public fun testMm256SrlvEpi32() {
    val a = mm256Set1Epi32(2)
    val count = mm256Set1Epi32(1)
    val r = mm256SrlvEpi32(a, count)
    val e = mm256Set1Epi32(1)
    assertEqM256i(r, e)
}

public fun testMmSrlvEpi64() {
    val a = mmSetEpi64x(4L, 8L)
    val b = mmSetEpi64x(2L, 1L)
    val r = mmSrlvEpi64(a, b)
    // Compare with the scalar version of the same computation.
    val e = mmSetEpi64x(4L.unboundedShr(2), 8L.unboundedShr(1))
    assertEqM128i(r, e)
    // Compare with hardcoded output.
    val e1 = mmSetEpi64x(1L, 4L)
    assertEqM128i(r, e1)
    // The shift has unbounded semantics: if the shift amount
    // is >= the number of bits the result is 0.
    val a1 = mmSetEpi64x(Long.MAX_VALUE, Long.MAX_VALUE)
    val b1 = mmSetEpi64x(64L, 65L)
    val r1 = mmSllvEpi64(a1, b1)
    val e2 = mmSetEpi64x(Long.MAX_VALUE.unboundedShr(64), Long.MAX_VALUE.unboundedShr(65))
    assertEqM128i(r1, e2)
    val e3 = mmSetEpi64x(0L, 0L)
    assertEqM128i(r1, e3)
}

public fun testMm256SrlvEpi64() {
    val a = mm256Set1Epi64x(2L)
    val count = mm256Set1Epi64x(1L)
    val r = mm256SrlvEpi64(a, count)
    val e = mm256Set1Epi64x(1L)
    assertEqM256i(r, e)
}

public fun testMm256StreamLoadSi256() {
    val a = mm256SetEpi64x(5L, 6L, 7L, 8L)
    val r = mm256StreamLoadSi256(constPtrFromRustCast(addrOf(a)))
    assertEqM256i(a, r)
}

public fun testMm256SubEpi16() {
    val a = mm256Set1Epi16(4)
    val b = mm256Set1Epi16(2)
    val r = mm256SubEpi16(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubEpi32() {
    val a = mm256Set1Epi32(4)
    val b = mm256Set1Epi32(2)
    val r = mm256SubEpi32(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubEpi64() {
    val a = mm256Set1Epi64x(4L)
    val b = mm256Set1Epi64x(2L)
    val r = mm256SubEpi64(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubEpi8() {
    val a = mm256Set1Epi8(4)
    val b = mm256Set1Epi8(2)
    val r = mm256SubEpi8(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubsEpi16() {
    val a = mm256Set1Epi16(4)
    val b = mm256Set1Epi16(2)
    val r = mm256SubsEpi16(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubsEpi8() {
    val a = mm256Set1Epi8(4)
    val b = mm256Set1Epi8(2)
    val r = mm256SubsEpi8(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubsEpu16() {
    val a = mm256Set1Epi16(4)
    val b = mm256Set1Epi16(2)
    val r = mm256SubsEpu16(a, b)
    assertEqM256i(r, b)
}

public fun testMm256SubsEpu8() {
    val a = mm256Set1Epi8(4)
    val b = mm256Set1Epi8(2)
    val r = mm256SubsEpu8(a, b)
    assertEqM256i(r, b)
}

public fun testMm256XorSi256() {
    val a = mm256Set1Epi8(5)
    val b = mm256Set1Epi8(3)
    val r = mm256XorSi256(a, b)
    assertEqM256i(r, mm256Set1Epi8(6))
}

public fun testMm256AlignrEpi8() {
    val a = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm256SetrEpi8((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte(), (-17).toByte(), (-18).toByte(), (-19).toByte(), (-20).toByte(), (-21).toByte(), (-22).toByte(), (-23).toByte(), (-24).toByte(), (-25).toByte(), (-26).toByte(), (-27).toByte(), (-28).toByte(), (-29).toByte(), (-30).toByte(), (-31).toByte(), (-32).toByte())
    val r = mm256AlignrEpi8(a, b, 33)
    assertEqM256i(r, mm256Set1Epi8(0))
    val r1 = mm256AlignrEpi8(a, b, 17)
    val expected = mm256SetrEpi8(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 0)
    assertEqM256i(r1, expected)
    val r2 = mm256AlignrEpi8(a, b, 4)
    val expected1 = mm256SetrEpi8((-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte(), 1, 2, 3, 4, (-21).toByte(), (-22).toByte(), (-23).toByte(), (-24).toByte(), (-25).toByte(), (-26).toByte(), (-27).toByte(), (-28).toByte(), (-29).toByte(), (-30).toByte(), (-31).toByte(), (-32).toByte(), 17, 18, 19, 20)
    assertEqM256i(r2, expected1)
    val r3 = mm256AlignrEpi8(a, b, 15)
    val expected2 = mm256SetrEpi8((-16).toByte(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, (-32).toByte(), 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    assertEqM256i(r3, expected2)
    val r4 = mm256AlignrEpi8(a, b, 0)
    assertEqM256i(r4, b)
    val r5 = mm256AlignrEpi8(a, b, 16)
    assertEqM256i(r5, a)
}

public fun testMm256ShuffleEpi8() {
    val a = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val b = mm256SetrEpi8(4, (128u.toUByte()).toByte(), 4, 3, 24, 12, 6, 19, 12, 5, 5, 10, 4, 1, 8, 0, 4, (128u.toUByte()).toByte(), 4, 3, 24, 12, 6, 19, 12, 5, 5, 10, 4, 1, 8, 0)
    val expected = mm256SetrEpi8(5, 0, 5, 4, 9, 13, 7, 4, 13, 6, 6, 11, 5, 2, 9, 1, 21, 0, 21, 20, 25, 29, 23, 20, 29, 22, 22, 27, 21, 18, 25, 17)
    val r = mm256ShuffleEpi8(a, b)
    assertEqM256i(r, expected)
}

public fun testMm256Permutevar8x32Epi32() {
    val a = mm256SetrEpi32(100, 200, 300, 400, 500, 600, 700, 800)
    val b = mm256SetrEpi32(5, 0, 5, 1, 7, 6, 3, 4)
    val expected = mm256SetrEpi32(600, 100, 600, 200, 800, 700, 400, 500)
    val r = mm256Permutevar8x32Epi32(a, b)
    assertEqM256i(r, expected)
}

public fun testMm256Permute4x64Epi64() {
    val a = mm256SetrEpi64x(100L, 200L, 300L, 400L)
    val expected = mm256SetrEpi64x(400L, 100L, 200L, 100L)
    val r = mm256Permute4x64Epi64(a, 0x13)
    assertEqM256i(r, expected)
}

public fun testMm256Permute2x128Si256() {
    val a = mm256SetrEpi64x(100L, 200L, 500L, 600L)
    val b = mm256SetrEpi64x(300L, 400L, 700L, 800L)
    val r = mm256Permute2x128Si256(a, b, 0x13)
    val e = mm256SetrEpi64x(700L, 800L, 500L, 600L)
    assertEqM256i(r, e)
}

public fun testMm256Permute4x64Pd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256Permute4x64Pd(a, 0x13)
    val e = mm256SetrPd(4.0, 1.0, 2.0, 1.0)
    assertEqM256d(r, e)
}

public fun testMm256Permutevar8x32Ps() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetrEpi32(5, 0, 5, 1, 7, 6, 3, 4)
    val r = mm256Permutevar8x32Ps(a, b)
    val e = mm256SetrPs(6.0f, 1.0f, 6.0f, 2.0f, 8.0f, 7.0f, 4.0f, 5.0f)
    assertEqM256(r, e)
}

public fun testMmI32gatherEpi32() {
    val localStorageL5551C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mmI32gatherEpi32(localStorageL5551C8.elementConstPtr(0), mmSetrEpi32(0, 16, 32, 48), 4)
    assertEqM128i(r, mmSetrEpi32(0, 16, 32, 48))
}

public fun testMmMaskI32gatherEpi32() {
    val localStorageL5559C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mmMaskI32gatherEpi32(mmSet1Epi32(256), localStorageL5559C8.elementConstPtr(0), mmSetrEpi32(0, 16, 64, 96), mmSetrEpi32(-1, -1, -1, 0), 4)
    assertEqM128i(r, mmSetrEpi32(0, 16, 64, 256))
}

public fun testMm256I32gatherEpi32() {
    val localStorageL5574C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mm256I32gatherEpi32(localStorageL5574C8.elementConstPtr(0), mm256SetrEpi32(0, 16, 32, 48, 1, 2, 3, 4), 4)
    assertEqM256i(r, mm256SetrEpi32(0, 16, 32, 48, 1, 2, 3, 4))
}

public fun testMm256MaskI32gatherEpi32() {
    val localStorageL5584C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mm256MaskI32gatherEpi32(mm256Set1Epi32(256), localStorageL5584C8.elementConstPtr(0), mm256SetrEpi32(0, 16, 64, 96, 0, 0, 0, 0), mm256SetrEpi32(-1, -1, -1, 0, 0, 0, 0, 0), 4)
    assertEqM256i(r, mm256SetrEpi32(0, 16, 64, 256, 256, 256, 256, 256))
}

public fun testMmI32gatherPs() {
    val localStorageL5599C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mmI32gatherPs(localStorageL5599C8.elementConstPtr(0), mmSetrEpi32(0, 16, 32, 48), 4)
    assertEqM128(r, mmSetrPs(0.0f, 16.0f, 32.0f, 48.0f))
}

public fun testMmMaskI32gatherPs() {
    val localStorageL5607C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mmMaskI32gatherPs(mmSet1Ps(256.0f), localStorageL5607C8.elementConstPtr(0), mmSetrEpi32(0, 16, 64, 96), mmSetrPs(-1.0f, -1.0f, -1.0f, 0.0f), 4)
    assertEqM128(r, mmSetrPs(0.0f, 16.0f, 64.0f, 256.0f))
}

public fun testMm256I32gatherPs() {
    val localStorageL5622C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mm256I32gatherPs(localStorageL5622C8.elementConstPtr(0), mm256SetrEpi32(0, 16, 32, 48, 1, 2, 3, 4), 4)
    assertEqM256(r, mm256SetrPs(0.0f, 16.0f, 32.0f, 48.0f, 1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMm256MaskI32gatherPs() {
    val localStorageL5632C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mm256MaskI32gatherPs(mm256Set1Ps(256.0f), localStorageL5632C8.elementConstPtr(0), mm256SetrEpi32(0, 16, 64, 96, 0, 0, 0, 0), mm256SetrPs(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), 4)
    assertEqM256(r, mm256SetrPs(0.0f, 16.0f, 64.0f, 256.0f, 256.0f, 256.0f, 256.0f, 256.0f))
}

public fun testMmI32gatherEpi64() {
    val localStorageL5650C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mmI32gatherEpi64(localStorageL5650C8.elementConstPtr(0), mmSetrEpi32(0, 16, 0, 0), 8)
    assertEqM128i(r, mmSetrEpi64x(0, 16))
}

public fun testMmMaskI32gatherEpi64() {
    val localStorageL5658C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mmMaskI32gatherEpi64(mmSet1Epi64x(256L), localStorageL5658C8.elementConstPtr(0), mmSetrEpi32(16, 16, 16, 16), mmSetrEpi64x(-1, 0), 8)
    assertEqM128i(r, mmSetrEpi64x(16, 256))
}

public fun testMm256I32gatherEpi64() {
    val localStorageL5673C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mm256I32gatherEpi64(localStorageL5673C8.elementConstPtr(0), mmSetrEpi32(0, 16, 32, 48), 8)
    assertEqM256i(r, mm256SetrEpi64x(0L, 16L, 32L, 48L))
}

public fun testMm256MaskI32gatherEpi64() {
    val localStorageL5681C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mm256MaskI32gatherEpi64(mm256Set1Epi64x(256L), localStorageL5681C8.elementConstPtr(0), mmSetrEpi32(0, 16, 64, 96), mm256SetrEpi64x(-1L, -1L, -1L, 0L), 8)
    assertEqM256i(r, mm256SetrEpi64x(0L, 16L, 64L, 256L))
}

public fun testMmI32gatherPd() {
    val localStorageL5696C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mmI32gatherPd(localStorageL5696C8.elementConstPtr(0), mmSetrEpi32(0, 16, 0, 0), 8)
    assertEqM128d(r, mmSetrPd(0.0, 16.0))
}

public fun testMmMaskI32gatherPd() {
    val localStorageL5704C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mmMaskI32gatherPd(mmSet1Pd(256.0), localStorageL5704C8.elementConstPtr(0), mmSetrEpi32(16, 16, 16, 16), mmSetrPd(-1.0, 0.0), 8)
    assertEqM128d(r, mmSetrPd(16.0, 256.0))
}

public fun testMm256I32gatherPd() {
    val localStorageL5719C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mm256I32gatherPd(localStorageL5719C8.elementConstPtr(0), mmSetrEpi32(0, 16, 32, 48), 8)
    assertEqM256d(r, mm256SetrPd(0.0, 16.0, 32.0, 48.0))
}

public fun testMm256MaskI32gatherPd() {
    val localStorageL5727C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mm256MaskI32gatherPd(mm256Set1Pd(256.0), localStorageL5727C8.elementConstPtr(0), mmSetrEpi32(0, 16, 64, 96), mm256SetrPd(-1.0, -1.0, -1.0, 0.0), 8)
    assertEqM256d(r, mm256SetrPd(0.0, 16.0, 64.0, 256.0))
}

public fun testMmI64gatherEpi32() {
    val localStorageL5742C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mmI64gatherEpi32(localStorageL5742C8.elementConstPtr(0), mmSetrEpi64x(0, 16), 4)
    assertEqM128i(r, mmSetrEpi32(0, 16, 0, 0))
}

public fun testMmMaskI64gatherEpi32() {
    val localStorageL5750C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mmMaskI64gatherEpi32(mmSet1Epi32(256), localStorageL5750C8.elementConstPtr(0), mmSetrEpi64x(0, 16), mmSetrEpi32(-1, 0, -1, 0), 4)
    assertEqM128i(r, mmSetrEpi32(0, 256, 0, 0))
}

public fun testMm256I64gatherEpi32() {
    val localStorageL5765C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mm256I64gatherEpi32(localStorageL5765C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 32L, 48L), 4)
    assertEqM128i(r, mmSetrEpi32(0, 16, 32, 48))
}

public fun testMm256MaskI64gatherEpi32() {
    val localStorageL5774C8 = AddressableArrayLocal.fromIntArray(fromFn({ i -> i.toInt() }))
    // A multiplier of 4 is word-addressing
    val r = mm256MaskI64gatherEpi32(mmSet1Epi32(256), localStorageL5774C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 64L, 96L), mmSetrEpi32(-1, -1, -1, 0), 4)
    assertEqM128i(r, mmSetrEpi32(0, 16, 64, 256))
}

public fun testMmI64gatherPs() {
    val localStorageL5789C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mmI64gatherPs(localStorageL5789C8.elementConstPtr(0), mmSetrEpi64x(0, 16), 4)
    assertEqM128(r, mmSetrPs(0.0f, 16.0f, 0.0f, 0.0f))
}

public fun testMmMaskI64gatherPs() {
    val localStorageL5797C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mmMaskI64gatherPs(mmSet1Ps(256.0f), localStorageL5797C8.elementConstPtr(0), mmSetrEpi64x(0, 16), mmSetrPs(-1.0f, 0.0f, -1.0f, 0.0f), 4)
    assertEqM128(r, mmSetrPs(0.0f, 256.0f, 0.0f, 0.0f))
}

public fun testMm256I64gatherPs() {
    val localStorageL5812C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mm256I64gatherPs(localStorageL5812C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 32L, 48L), 4)
    assertEqM128(r, mmSetrPs(0.0f, 16.0f, 32.0f, 48.0f))
}

public fun testMm256MaskI64gatherPs() {
    val localStorageL5821C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing for f32s
    val r = mm256MaskI64gatherPs(mmSet1Ps(256.0f), localStorageL5821C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 64L, 96L), mmSetrPs(-1.0f, -1.0f, -1.0f, 0.0f), 4)
    assertEqM128(r, mmSetrPs(0.0f, 16.0f, 64.0f, 256.0f))
}

public fun testMmI64gatherEpi64() {
    val localStorageL5836C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mmI64gatherEpi64(localStorageL5836C8.elementConstPtr(0), mmSetrEpi64x(0, 16), 8)
    assertEqM128i(r, mmSetrEpi64x(0, 16))
}

public fun testMmMaskI64gatherEpi64() {
    val localStorageL5844C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mmMaskI64gatherEpi64(mmSet1Epi64x(256L), localStorageL5844C8.elementConstPtr(0), mmSetrEpi64x(16, 16), mmSetrEpi64x(-1, 0), 8)
    assertEqM128i(r, mmSetrEpi64x(16, 256))
}

public fun testMm256I64gatherEpi64() {
    val localStorageL5859C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mm256I64gatherEpi64(localStorageL5859C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 32L, 48L), 8)
    assertEqM256i(r, mm256SetrEpi64x(0L, 16L, 32L, 48L))
}

public fun testMm256MaskI64gatherEpi64() {
    val localStorageL5868C8 = AddressableArrayLocal.fromLongArray(fromFn({ i -> i.toLong() }))
    // A multiplier of 8 is word-addressing for i64s
    val r = mm256MaskI64gatherEpi64(mm256Set1Epi64x(256L), localStorageL5868C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 64L, 96L), mm256SetrEpi64x(-1L, -1L, -1L, 0L), 8)
    assertEqM256i(r, mm256SetrEpi64x(0L, 16L, 64L, 256L))
}

public fun testMmI64gatherPd() {
    val localStorageL5883C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mmI64gatherPd(localStorageL5883C8.elementConstPtr(0), mmSetrEpi64x(0, 16), 8)
    assertEqM128d(r, mmSetrPd(0.0, 16.0))
}

public fun testMmMaskI64gatherPd() {
    val localStorageL5891C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mmMaskI64gatherPd(mmSet1Pd(256.0), localStorageL5891C8.elementConstPtr(0), mmSetrEpi64x(16, 16), mmSetrPd(-1.0, 0.0), 8)
    assertEqM128d(r, mmSetrPd(16.0, 256.0))
}

public fun testMm256I64gatherPd() {
    val localStorageL5906C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mm256I64gatherPd(localStorageL5906C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 32L, 48L), 8)
    assertEqM256d(r, mm256SetrPd(0.0, 16.0, 32.0, 48.0))
}

public fun testMm256MaskI64gatherPd() {
    val localStorageL5915C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing for f64s
    val r = mm256MaskI64gatherPd(mm256Set1Pd(256.0), localStorageL5915C8.elementConstPtr(0), mm256SetrEpi64x(0L, 16L, 64L, 96L), mm256SetrPd(-1.0, -1.0, -1.0, 0.0), 8)
    assertEqM256d(r, mm256SetrPd(0.0, 16.0, 64.0, 256.0))
}

public fun testMm256ExtractEpi8() {
    val a = mm256SetrEpi8((-1).toByte(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r1 = mm256ExtractEpi8(a, 0)
    val r2 = mm256ExtractEpi8(a, 3)
    check(r1 == 0xFF)
    check(r2 == 3)
}

public fun testMm256ExtractEpi16() {
    val a = mm256SetrEpi16((-1).toShort(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r1 = mm256ExtractEpi16(a, 0)
    val r2 = mm256ExtractEpi16(a, 3)
    check(r1 == 0xFFFF)
    check(r2 == 3)
}
