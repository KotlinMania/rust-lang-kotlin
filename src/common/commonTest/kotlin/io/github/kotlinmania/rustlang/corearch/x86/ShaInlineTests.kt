// port-lint: tests x86/sha.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.num.rotateLeft
import io.github.kotlinmania.rustlang.core.num.rotateRight
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingAdd

public fun testMmSha1msg1Epu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val expected = mmSetEpi64x(0x98829f34f74ad457uL.toLong(), 0xda2b1a44d0b5ad3cuL.toLong())
    val r = mmSha1msg1Epu32(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSha1msg2Epu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val expected = mmSetEpi64x(0xf714b202d863d47duL.toLong(), 0x90c30d946b3d3b35uL.toLong())
    val r = mmSha1msg2Epu32(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSha1nexteEpu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val expected = mmSetEpi64x(0x2589d5be923f82a4L, 0x59f111f13956c25bL)
    val r = mmSha1nexteEpu32(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSha1rnds4Epu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val expected = mmSetEpi64x(0x32b13cd8322f5268L, 0xc54420862bd9246fuL.toLong())
    val r = mmSha1rnds4Epu32(a, b, 0)
    assertEqM128i(r, expected)
    val expected1 = mmSetEpi64x(0x6d4c43e56a3c25d9L, 0xa7e00fb775cbd3feuL.toLong())
    val r1 = mmSha1rnds4Epu32(a, b, 1)
    assertEqM128i(r1, expected1)
    val expected2 = mmSetEpi64x(0xb304e383c01222f4uL.toLong(), 0x66f6b3b1f89d8001L)
    val r2 = mmSha1rnds4Epu32(a, b, 2)
    assertEqM128i(r2, expected2)
    val expected3 = mmSetEpi64x(0x8189b758bfabfa79uL.toLong(), 0xdb08f6e78cae098buL.toLong())
    val r3 = mmSha1rnds4Epu32(a, b, 3)
    assertEqM128i(r3, expected3)
}

public fun testMmSha256msg1Epu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val expected = mmSetEpi64x(0xeb84973fd5cda67duL.toLong(), 0x2857b88f406b09eeL)
    val r = mmSha256msg1Epu32(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSha256msg2Epu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val expected = mmSetEpi64x(0xb58777ce887fd851uL.toLong(), 0x15d1ec8b73ac8450L)
    val r = mmSha256msg2Epu32(a, b)
    assertEqM128i(r, expected)
}

public fun testMmSha256rnds2Epu32() {
    val a = mmSetEpi64x(0xe9b5dba5b5c0fbcfuL.toLong(), 0x71374491428a2f98L)
    val b = mmSetEpi64x(0xab1c5ed5923f82a4uL.toLong(), 0x59f111f13956c25bL)
    val k = mmSetEpi64x(0L, 0x12835b01d807aa98L)
    val expected = mmSetEpi64x(0xd3063037effb15eauL.toLong(), 0x187ee3db0d6d1d19L)
    val r = mmSha256rnds2Epu32(a, b, k)
    assertEqM128i(r, expected)
}

public var DATA_64: ULongArray = ulongArrayOf(0x0011223344556677uL, 0x8899aabbccddeeffuL, 0xffeeddccbbaa9988uL, 0x7766554433221100uL, 0x0123456789abcdefuL, 0xfedcba9876543210uL, 0x02468ace13579bdfuL, 0xfdb97531eca86420uL, 0x048c159d26ae37bfuL, 0xfb73ea62d951c840uL)

public fun testMm256Sha512msg1Epi64() {
    fun s0(word: ULong): ULong = word.rotateRight(1u) xor word.rotateRight(8u) xor (word shr 7)
    val a = ((DATA_64).sliceArray(0 until 4))
    val b = ((DATA_64).sliceArray(4 until 6))
    val a = mm256LoaduSi256(A.asPtr())
    val b = mmLoaduSi128(B.asPtr())
    val r = mm256Sha512msg1Epi64(a, b)
    val e = mm256SetrEpi64x(A[0].wrappingAdd(s0(A[1])).toLong(), A[1].wrappingAdd(s0(A[2])).toLong(), A[2].wrappingAdd(s0(A[3])).toLong(), A[3].wrappingAdd(s0(B[0])).toLong())
    assertEqM256i(r, e)
}

public fun testMm256Sha512msg2Epi64() {
    fun s1(word: ULong): ULong = word.rotateRight(19u) xor word.rotateRight(61u) xor (word shr 6)
    val a = ((DATA_64).sliceArray(0 until 4))
    val b = ((DATA_64).sliceArray(4 until 8))
    val a = mm256LoaduSi256(A.asPtr())
    val b = mm256LoaduSi256(B.asPtr())
    val r = mm256Sha512msg2Epi64(a, b)
    val e0 = A[0].wrappingAdd(s1(B[2]))
    val e1 = A[1].wrappingAdd(s1(B[3]))
    val e = mm256SetrEpi64x(e0.toLong(), e1.toLong(), A[2].wrappingAdd(s1(e0)).toLong(), A[3].wrappingAdd(s1(e1)).toLong())
    assertEqM256i(r, e)
}

public fun testMm256Sha512rnds2Epi64() {
    fun capSigma0(word: ULong): ULong = word.rotateRight(28u) xor word.rotateRight(34u) xor word.rotateRight(39u)
    fun capSigma1(word: ULong): ULong = word.rotateRight(14u) xor word.rotateRight(18u) xor word.rotateRight(41u)
    fun maj(a: ULong, b: ULong, c: ULong): ULong = (a and b) xor (a and c) xor (b and c)
    fun ch(e: ULong, f: ULong, g: ULong): ULong = (e and f) xor (g and e.inv())
    val a = ((DATA_64).sliceArray(0 until 4))
    val b = ((DATA_64).sliceArray(4 until 8))
    val k = ((DATA_64).sliceArray(8 until 10))
    val a = mm256LoaduSi256(A.asPtr())
    val b = mm256LoaduSi256(B.asPtr())
    val k = mmLoaduSi128(K.asPtr())
    val r = mm256Sha512rnds2Epi64(a, b, k)
    val array = mutableListOf(B[3], B[2], A[3], A[2], B[1], B[0], A[1], A[0])
    for (i in 0 until 2) {
        val newD = (((ch(array[4], array[5], array[6]) + capSigma1(array[4]).toULong()) + K[i].toULong()) + array[7].toULong())
        array[7] = ((newD + maj(array[0], array[1], array[2]).toULong()) + capSigma0(array[0]).toULong())
        array[3] = (newD + array[3].toULong())
        array.rotateRight(1)
    }
    val e = mm256SetrEpi64x(array[5].toLong(), array[4].toLong(), array[1].toLong(), array[0].toLong())
    assertEqM256i(r, e)
}

public var DATA_32: UIntArray = uintArrayOf(0x00112233u, 0x44556677u, 0x8899aabbu, 0xccddeeffu, 0xffeeddccu, 0xbbaa9988u, 0x77665544u, 0x33221100u, 0x01234567u, 0x89abcdefu, 0xfedcba98u, 0x76543210u, 0x02468aceu, 0x13579bdfu, 0xfdb97531u, 0xeca86420u)

public fun testMmSm3msg1Epi32() {
    fun p1(x: UInt): UInt = x xor x.rotateLeft(15u) xor x.rotateLeft(23u)
    val a = ((DATA_32).sliceArray(0 until 4))
    val b = ((DATA_32).sliceArray(4 until 8))
    val c = ((DATA_32).sliceArray(8 until 12))
    val a = mmLoaduSi128(A.asPtr())
    val b = mmLoaduSi128(B.asPtr())
    val c = mmLoaduSi128(C.asPtr())
    val r = mmSm3msg1Epi32(a, b, c)
    val e = mmSetrEpi32(p1(A[0] xor C[0] xor B[0].rotateLeft(15)).toInt(), p1(A[1] xor C[1] xor B[1].rotateLeft(15)).toInt(), p1(A[2] xor C[2] xor B[2].rotateLeft(15)).toInt(), p1(A[3] xor C[3]).toInt())
    assertEqM128i(r, e)
}

public fun testMmSm3msg2Epi32() {
    val a = ((DATA_32).sliceArray(0 until 4))
    val b = ((DATA_32).sliceArray(4 until 8))
    val c = ((DATA_32).sliceArray(8 until 12))
    val a = mmLoaduSi128(A.asPtr())
    val b = mmLoaduSi128(B.asPtr())
    val c = mmLoaduSi128(C.asPtr())
    val r = mmSm3msg2Epi32(a, b, c)
    val e0 = B[0].rotateLeft(7) xor C[0] xor A[0]
    val e = mmSetrEpi32(e0.toInt(), (B[1].rotateLeft(7) xor C[1] xor A[1]).toInt(), (B[2].rotateLeft(7) xor C[2] xor A[2]).toInt(), (B[3].rotateLeft(7) xor C[3] xor A[3] xor e0.rotateLeft(6) xor e0.rotateLeft(15) xor e0.rotateLeft(30)).toInt())
    assertEqM128i(r, e)
}

public fun testMmSm3rnds2Epi32() {
    fun p0(x: UInt): UInt = x xor x.rotateLeft(9u) xor x.rotateLeft(17u)
    fun ff(x: UInt, y: UInt, z: UInt, round: UInt): UInt =
        if (round < 16u) {
            x xor y xor z
        } else {
            (x and y) or (x and z) or (y and z)
        }
    fun gg(x: UInt, y: UInt, z: UInt, round: UInt): UInt =
        if (round < 16u) {
            x xor y xor z
        } else {
            (x and y) or (x.inv() and z)
        }
    val ROUND: UInt = 30u
    val a = ((DATA_32).sliceArray(0 until 4))
    val b = ((DATA_32).sliceArray(4 until 8))
    val c = ((DATA_32).sliceArray(8 until 12))
    val a = mmLoaduSi128(A.asPtr())
    val b = mmLoaduSi128(B.asPtr())
    val c = mmLoaduSi128(C.asPtr())
    val r = mmSm3rnds2Epi32(a, b, c, ROUND.toInt())
    val const: UInt =
        if (ROUND < 16) {
            0x79cc4519u
        } else {
            0x7a879d8au
        }
    val array = mutableListOf(B[3], B[2], A[3].rotateLeft(9), A[2].rotateLeft(9), B[1], B[0], A[1].rotateLeft(19), A[0].rotateLeft(19))
    for (i in 0 until 2) {
        val s1 = array[0].rotateLeft(12).wrappingAdd(array[4]).wrappingAdd(CONST.rotateLeft(ROUND.toUInt() + i.toUInt())).rotateLeft(7)
        val s2 = s1 xor array[0].rotateLeft(12)
        val t1 = (((ff(array[0], array[1], array[2], ROUND) + array[3].toUInt()) + (s2.toUInt())) + ((C[i] xor C[i + 2]).toUInt()))
        val t2 = (((gg(array[4], array[5], array[6], ROUND) + array[7].toUInt()) + (s1.toUInt())) + C[i].toUInt())
        array[3] = array[2]
        array[2] = array[1].rotateLeft(9)
        array[1] = array[0]
        array[0] = t1
        array[7] = array[6]
        array[6] = array[5].rotateLeft(19)
        array[5] = array[4]
        array[4] = p0(t2)
    }
    val e = mmSetrEpi32(array[5].toInt(), array[4].toInt(), array[1].toInt(), array[0].toInt())
    assertEqM128i(r, e)
}

public fun lowerT(x: UInt): UInt {
    val SBOX: UByteArray  = ubyteArrayOf(0xD6u.toUByte(), 0x90u.toUByte(), 0xE9u.toUByte(), 0xFEu.toUByte(), 0xCCu.toUByte(), 0xE1u.toUByte(), 0x3Du.toUByte(), 0xB7u.toUByte(), 0x16u.toUByte(), 0xB6u.toUByte(), 0x14u.toUByte(), 0xC2u.toUByte(), 0x28u.toUByte(), 0xFBu.toUByte(), 0x2Cu.toUByte(), 0x05u.toUByte(), 0x2Bu.toUByte(), 0x67u.toUByte(), 0x9Au.toUByte(), 0x76u.toUByte(), 0x2Au.toUByte(), 0xBEu.toUByte(), 0x04u.toUByte(), 0xC3u.toUByte(), 0xAAu.toUByte(), 0x44u.toUByte(), 0x13u.toUByte(), 0x26u.toUByte(), 0x49u.toUByte(), 0x86u.toUByte(), 0x06u.toUByte(), 0x99u.toUByte(), 0x9Cu.toUByte(), 0x42u.toUByte(), 0x50u.toUByte(), 0xF4u.toUByte(), 0x91u.toUByte(), 0xEFu.toUByte(), 0x98u.toUByte(), 0x7Au.toUByte(), 0x33u.toUByte(), 0x54u.toUByte(), 0x0Bu.toUByte(), 0x43u.toUByte(), 0xEDu.toUByte(), 0xCFu.toUByte(), 0xACu.toUByte(), 0x62u.toUByte(), 0xE4u.toUByte(), 0xB3u.toUByte(), 0x1Cu.toUByte(), 0xA9u.toUByte(), 0xC9u.toUByte(), 0x08u.toUByte(), 0xE8u.toUByte(), 0x95u.toUByte(), 0x80u.toUByte(), 0xDFu.toUByte(), 0x94u.toUByte(), 0xFAu.toUByte(), 0x75u.toUByte(), 0x8Fu.toUByte(), 0x3Fu.toUByte(), 0xA6u.toUByte(), 0x47u.toUByte(), 0x07u.toUByte(), 0xA7u.toUByte(), 0xFCu.toUByte(), 0xF3u.toUByte(), 0x73u.toUByte(), 0x17u.toUByte(), 0xBAu.toUByte(), 0x83u.toUByte(), 0x59u.toUByte(), 0x3Cu.toUByte(), 0x19u.toUByte(), 0xE6u.toUByte(), 0x85u.toUByte(), 0x4Fu.toUByte(), 0xA8u.toUByte(), 0x68u.toUByte(), 0x6Bu.toUByte(), 0x81u.toUByte(), 0xB2u.toUByte(), 0x71u.toUByte(), 0x64u.toUByte(), 0xDAu.toUByte(), 0x8Bu.toUByte(), 0xF8u.toUByte(), 0xEBu.toUByte(), 0x0Fu.toUByte(), 0x4Bu.toUByte(), 0x70u.toUByte(), 0x56u.toUByte(), 0x9Du.toUByte(), 0x35u.toUByte(), 0x1Eu.toUByte(), 0x24u.toUByte(), 0x0Eu.toUByte(), 0x5Eu.toUByte(), 0x63u.toUByte(), 0x58u.toUByte(), 0xD1u.toUByte(), 0xA2u.toUByte(), 0x25u.toUByte(), 0x22u.toUByte(), 0x7Cu.toUByte(), 0x3Bu.toUByte(), 0x01u.toUByte(), 0x21u.toUByte(), 0x78u.toUByte(), 0x87u.toUByte(), 0xD4u.toUByte(), 0x00u.toUByte(), 0x46u.toUByte(), 0x57u.toUByte(), 0x9Fu.toUByte(), 0xD3u.toUByte(), 0x27u.toUByte(), 0x52u.toUByte(), 0x4Cu.toUByte(), 0x36u.toUByte(), 0x02u.toUByte(), 0xE7u.toUByte(), 0xA0u.toUByte(), 0xC4u.toUByte(), 0xC8u.toUByte(), 0x9Eu.toUByte(), 0xEAu.toUByte(), 0xBFu.toUByte(), 0x8Au.toUByte(), 0xD2u.toUByte(), 0x40u.toUByte(), 0xC7u.toUByte(), 0x38u.toUByte(), 0xB5u.toUByte(), 0xA3u.toUByte(), 0xF7u.toUByte(), 0xF2u.toUByte(), 0xCEu.toUByte(), 0xF9u.toUByte(), 0x61u.toUByte(), 0x15u.toUByte(), 0xA1u.toUByte(), 0xE0u.toUByte(), 0xAEu.toUByte(), 0x5Du.toUByte(), 0xA4u.toUByte(), 0x9Bu.toUByte(), 0x34u.toUByte(), 0x1Au.toUByte(), 0x55u.toUByte(), 0xADu.toUByte(), 0x93u.toUByte(), 0x32u.toUByte(), 0x30u.toUByte(), 0xF5u.toUByte(), 0x8Cu.toUByte(), 0xB1u.toUByte(), 0xE3u.toUByte(), 0x1Du.toUByte(), 0xF6u.toUByte(), 0xE2u.toUByte(), 0x2Eu.toUByte(), 0x82u.toUByte(), 0x66u.toUByte(), 0xCAu.toUByte(), 0x60u.toUByte(), 0xC0u.toUByte(), 0x29u.toUByte(), 0x23u.toUByte(), 0xABu.toUByte(), 0x0Du.toUByte(), 0x53u.toUByte(), 0x4Eu.toUByte(), 0x6Fu.toUByte(), 0xD5u.toUByte(), 0xDBu.toUByte(), 0x37u.toUByte(), 0x45u.toUByte(), 0xDEu.toUByte(), 0xFDu.toUByte(), 0x8Eu.toUByte(), 0x2Fu.toUByte(), 0x03u.toUByte(), 0xFFu.toUByte(), 0x6Au.toUByte(), 0x72u.toUByte(), 0x6Du.toUByte(), 0x6Cu.toUByte(), 0x5Bu.toUByte(), 0x51u.toUByte(), 0x8Du.toUByte(), 0x1Bu.toUByte(), 0xAFu.toUByte(), 0x92u.toUByte(), 0xBBu.toUByte(), 0xDDu.toUByte(), 0xBCu.toUByte(), 0x7Fu.toUByte(), 0x11u.toUByte(), 0xD9u.toUByte(), 0x5Cu.toUByte(), 0x41u.toUByte(), 0x1Fu.toUByte(), 0x10u.toUByte(), 0x5Au.toUByte(), 0xD8u.toUByte(), 0x0Au.toUByte(), 0xC1u.toUByte(), 0x31u.toUByte(), 0x88u.toUByte(), 0xA5u.toUByte(), 0xCDu.toUByte(), 0x7Bu.toUByte(), 0xBDu.toUByte(), 0x2Du.toUByte(), 0x74u.toUByte(), 0xD0u.toUByte(), 0x12u.toUByte(), 0xB8u.toUByte(), 0xE5u.toUByte(), 0xB4u.toUByte(), 0xB0u.toUByte(), 0x89u.toUByte(), 0x69u.toUByte(), 0x97u.toUByte(), 0x4Au.toUByte(), 0x0Cu.toUByte(), 0x96u.toUByte(), 0x77u.toUByte(), 0x7Eu.toUByte(), 0x65u.toUByte(), 0xB9u.toUByte(), 0xF1u.toUByte(), 0x09u.toUByte(), 0xC5u.toUByte(), 0x6Eu.toUByte(), 0xC6u.toUByte(), 0x84u.toUByte(), 0x18u.toUByte(), 0xF0u.toUByte(), 0x7Du.toUByte(), 0xECu.toUByte(), 0x3Au.toUByte(), 0xDCu.toUByte(), 0x4Du.toUByte(), 0x20u.toUByte(), 0x79u.toUByte(), 0xEEu.toUByte(), 0x5Fu.toUByte(), 0x3Eu.toUByte(), 0xD7u.toUByte(), 0xCBu.toUByte(), 0x39u.toUByte(), 0x48u.toUByte())
    return ((SBOX[(x shr 24).toInt()].toUInt()) shl 24) or ((SBOX[((x shr 16) and 0xffu).toInt()].toUInt()) shl 16) or ((SBOX[((x shr 8) and 0xffu).toInt()].toUInt()) shl 8) or (SBOX[(x and 0xffu).toInt()].toUInt())
}

public fun testMmSm4key4Epi32() {
    fun lKey(x: UInt): UInt = x xor x.rotateLeft(13u) xor x.rotateLeft(23u)
    fun fKey(x0: UInt, x1: UInt, x2: UInt, x3: UInt, rk: UInt): UInt = x0 xor lKey(lowerT(x1 xor x2 xor x3 xor rk))
    val a = ((DATA_32).sliceArray(0 until 4))
    val b = ((DATA_32).sliceArray(4 until 8))
    val a = mmLoaduSi128(A.asPtr())
    val b = mmLoaduSi128(B.asPtr())
    val r = mmSm4key4Epi32(a, b)
    val e0 = fKey(A[0], A[1], A[2], A[3], B[0])
    val e1 = fKey(A[1], A[2], A[3], e0, B[1])
    val e2 = fKey(A[2], A[3], e0, e1, B[2])
    val e3 = fKey(A[3], e0, e1, e2, B[3])
    val e = mmSetrEpi32(e0.toInt(), e1.toInt(), e2.toInt(), e3.toInt())
    assertEqM128i(r, e)
}

public fun testMm256Sm4key4Epi32() {
    val aLow = mmLoaduSi128(DATA_32.asPtr())
    val aHigh = mmLoaduSi128(((DATA_32).sliceArray(4 until DATA_32.size)).asPtr())
    val bLow = mmLoaduSi128(((DATA_32).sliceArray(8 until DATA_32.size)).asPtr())
    val bHigh = mmLoaduSi128(((DATA_32).sliceArray(12 until DATA_32.size)).asPtr())
    val a = mm256SetM128i(aHigh, aLow)
    val b = mm256SetM128i(bHigh, bLow)
    val r = mm256Sm4key4Epi32(a, b)
    val eLow = mmSm4key4Epi32(aLow, bLow)
    val eHigh = mmSm4key4Epi32(aHigh, bHigh)
    val e = mm256SetM128i(eHigh, eLow)
    assertEqM256i(r, e)
}

public fun testMmSm4rnds4Epi32() {
    fun lRnd(x: UInt): UInt = x xor x.rotateLeft(2u) xor x.rotateLeft(10u) xor x.rotateLeft(18u) xor x.rotateLeft(24u)
    fun fRnd(x0: UInt, x1: UInt, x2: UInt, x3: UInt, rk: UInt): UInt = x0 xor lRnd(lowerT(x1 xor x2 xor x3 xor rk))
    val a = ((DATA_32).sliceArray(0 until 4))
    val b = ((DATA_32).sliceArray(4 until 8))
    val a = mmLoaduSi128(A.asPtr())
    val b = mmLoaduSi128(B.asPtr())
    val r = mmSm4rnds4Epi32(a, b)
    val e0 = fRnd(A[0], A[1], A[2], A[3], B[0])
    val e1 = fRnd(A[1], A[2], A[3], e0, B[1])
    val e2 = fRnd(A[2], A[3], e0, e1, B[2])
    val e3 = fRnd(A[3], e0, e1, e2, B[3])
    val e = mmSetrEpi32(e0.toInt(), e1.toInt(), e2.toInt(), e3.toInt())
    assertEqM128i(r, e)
}

public fun testMm256Sm4rnds4Epi32() {
    val aLow = mmLoaduSi128(DATA_32.asPtr())
    val aHigh = mmLoaduSi128(((DATA_32).sliceArray(4 until DATA_32.size)).asPtr())
    val bLow = mmLoaduSi128(((DATA_32).sliceArray(8 until DATA_32.size)).asPtr())
    val bHigh = mmLoaduSi128(((DATA_32).sliceArray(12 until DATA_32.size)).asPtr())
    val a = mm256SetM128i(aHigh, aLow)
    val b = mm256SetM128i(bHigh, bLow)
    val r = mm256Sm4rnds4Epi32(a, b)
    val eLow = mmSm4rnds4Epi32(aLow, bLow)
    val eHigh = mmSm4rnds4Epi32(aHigh, bHigh)
    val e = mm256SetM128i(eHigh, eLow)
    assertEqM256i(r, e)
}
