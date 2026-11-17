// port-lint: tests x86/sse42.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*

// Streaming SIMD Extensions 4.2 (SSE4.2)
//
// Extends SSE4.1 with STTNI (String and Text New Instructions).

// Currently one cannot `load` a &[u8] that is less than 16
// in length. This makes loading strings less than 16 in length
// a bit difficult. Rather than `load` and mutate the __m128i,
// it is easier to memcpy the given string to a zero-padded
// 16-byte array and reinterpret it to `__m128i`.
public fun strToM128i(s: List<UByte>): M128i {
    check(s.size <= 16)
    val array = UByteArray(16) { 0u.toUByte() }
    run { val dstTmp0 = array; val srcTmp0 = s; for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
    return u8x16.fromArray(array).asM128i()
}

public fun testMmCmpistrm() {
    val a = strToM128i("Hello! Good-Bye!".encodeToByteArray())
    val b = strToM128i("hello! good-bye!".encodeToByteArray())
    val i = mmCmpistrm(a, b, SIDD_UNIT_MASK)
    val res = mmSetrEpi8(0x00, (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), 0x00, (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), 0x00, (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte())
    assertEqM128i(i, res)
}

public fun testMmCmpistri() {
    val a = strToM128i("Hello".encodeToByteArray())
    val b = strToM128i("   Hello        ".encodeToByteArray())
    val i = mmCmpistri(a, b, SIDD_CMP_EQUAL_ORDERED)
    check(3 == i)
}

public fun testMmCmpistrz() {
    val a = strToM128i("".encodeToByteArray())
    val b = strToM128i("Hello".encodeToByteArray())
    val i = mmCmpistrz(a, b, SIDD_CMP_EQUAL_ORDERED)
    check(1 == i)
}

public fun testMmCmpistrc() {
    val a = strToM128i("                ".encodeToByteArray())
    val b = strToM128i("       !        ".encodeToByteArray())
    val i = mmCmpistrc(a, b, SIDD_UNIT_MASK)
    check(1 == i)
}

public fun testMmCmpistrs() {
    val a = strToM128i("Hello".encodeToByteArray())
    val b = strToM128i("".encodeToByteArray())
    val i = mmCmpistrs(a, b, SIDD_CMP_EQUAL_ORDERED)
    check(1 == i)
}

public fun testMmCmpistro() {
    val aBytes = mmSetrEpi8(0x00, 0x47, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    val bBytes = mmSetrEpi8(0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    val a = aBytes
    val b = bBytes
    val i = mmCmpistro(a, b, SIDD_UWORD_OPS or SIDD_UNIT_MASK)
    check(0 == i)
}

public fun testMmCmpistra() {
    val a = strToM128i("".encodeToByteArray())
    val b = strToM128i("Hello!!!!!!!!!!!".encodeToByteArray())
    val i = mmCmpistra(a, b, SIDD_UNIT_MASK)
    check(1 == i)
}

public fun testMmCmpestrm() {
    val a = strToM128i("Hello!".encodeToByteArray())
    val b = strToM128i("Hello.".encodeToByteArray())
    val i = mmCmpestrm(a, 5, b, 5, SIDD_UNIT_MASK)
    val r = mmSetrEpi8((0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), (0.inv()).toByte(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    assertEqM128i(i, r)
}

public fun testMmCmpestri() {
    val a = strToM128i("bar - garbage".encodeToByteArray())
    val b = strToM128i("foobar".encodeToByteArray())
    val i = mmCmpestri(a, 3, b, 6, SIDD_CMP_EQUAL_ORDERED)
    check(3 == i)
}

public fun testMmCmpestrz() {
    val a = strToM128i("".encodeToByteArray())
    val b = strToM128i("Hello".encodeToByteArray())
    val i = mmCmpestrz(a, 16, b, 6, SIDD_CMP_EQUAL_ORDERED)
    check(1 == i)
}

public fun testMmCmpestrc() {
    val va = strToM128i("!!!!!!!!".encodeToByteArray())
    val vb = strToM128i("        ".encodeToByteArray())
    val i = mmCmpestrc(va, 7, vb, 7, SIDD_UNIT_MASK)
    check(0 == i)
}

public fun testMmCmpestrs() {
    val aBytes = mmSetrEpi8(0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    val a = aBytes
    val b = mmSet1Epi8(0x00)
    val i = mmCmpestrs(a, 8, b, 0, SIDD_UWORD_OPS)
    check(0 == i)
}

public fun testMmCmpestro() {
    val a = strToM128i("Hello".encodeToByteArray())
    val b = strToM128i("World".encodeToByteArray())
    val i = mmCmpestro(a, 5, b, 5, SIDD_UBYTE_OPS)
    check(0 == i)
}

public fun testMmCmpestra() {
    val a = strToM128i("Cannot match a".encodeToByteArray())
    val b = strToM128i("Null after 14".encodeToByteArray())
    val i = mmCmpestra(a, 14, b, 16, SIDD_CMP_EQUAL_EACH or SIDD_UNIT_MASK)
    check(1 == i)
}

public fun testMmCrc32U8() {
    val crc = 0x2aa1e72b
    val v = 0x2a
    val i = mmCrc32U8(crc, v)
    check(i == 0xf24122e4u)
}

public fun testMmCrc32U16() {
    val crc = 0x8ecec3b5
    val v = 0x22b
    val i = mmCrc32U16(crc, v)
    check(i == 0x13bb2fbu)
}

public fun testMmCrc32U32() {
    val crc = 0xae2912c8
    val v = 0x845fed
    val i = mmCrc32U32(crc, v)
    check(i == 0xffae2ed1u)
}

public fun testMmCmpgtEpi64() {
    val a = mmSetrEpi64x(0, 0x2a)
    val b = mmSet1Epi64x(0x00L)
    val i = mmCmpgtEpi64(a, b)
    assertEqM128i(i, mmSetrEpi64x(0x00, 0xffffffffffffffffuL.toLong()))
}
