// port-lint: tests x86/sha.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.num.rotateLeft
import io.github.kotlinmania.rustlang.core.num.rotateRight

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
    val SBOX: UByteArray  = ubyteArrayOf(0xD6u, 0x90u, 0xE9u, 0xFEu, 0xCCu, 0xE1u, 0x3Du, 0xB7u, 0x16u, 0xB6u, 0x14u, 0xC2u, 0x28u, 0xFBu, 0x2Cu, 0x05u, 0x2Bu, 0x67u, 0x9Au, 0x76u, 0x2Au, 0xBEu, 0x04u, 0xC3u, 0xAAu, 0x44u, 0x13u, 0x26u, 0x49u, 0x86u, 0x06u, 0x99u, 0x9Cu, 0x42u, 0x50u, 0xF4u, 0x91u, 0xEFu, 0x98u, 0x7Au, 0x33u, 0x54u, 0x0Bu, 0x43u, 0xEDu, 0xCFu, 0xACu, 0x62u, 0xE4u, 0xB3u, 0x1Cu, 0xA9u, 0xC9u, 0x08u, 0xE8u, 0x95u, 0x80u, 0xDFu, 0x94u, 0xFAu, 0x75u, 0x8Fu, 0x3Fu, 0xA6u, 0x47u, 0x07u, 0xA7u, 0xFCu, 0xF3u, 0x73u, 0x17u, 0xBAu, 0x83u, 0x59u, 0x3Cu, 0x19u, 0xE6u, 0x85u, 0x4Fu, 0xA8u, 0x68u, 0x6Bu, 0x81u, 0xB2u, 0x71u, 0x64u, 0xDAu, 0x8Bu, 0xF8u, 0xEBu, 0x0Fu, 0x4Bu, 0x70u, 0x56u, 0x9Du, 0x35u, 0x1Eu, 0x24u, 0x0Eu, 0x5Eu, 0x63u, 0x58u, 0xD1u, 0xA2u, 0x25u, 0x22u, 0x7Cu, 0x3Bu, 0x01u, 0x21u, 0x78u, 0x87u, 0xD4u, 0x00u, 0x46u, 0x57u, 0x9Fu, 0xD3u, 0x27u, 0x52u, 0x4Cu, 0x36u, 0x02u, 0xE7u, 0xA0u, 0xC4u, 0xC8u, 0x9Eu, 0xEAu, 0xBFu, 0x8Au, 0xD2u, 0x40u, 0xC7u, 0x38u, 0xB5u, 0xA3u, 0xF7u, 0xF2u, 0xCEu, 0xF9u, 0x61u, 0x15u, 0xA1u, 0xE0u, 0xAEu, 0x5Du, 0xA4u, 0x9Bu, 0x34u, 0x1Au, 0x55u, 0xADu, 0x93u, 0x32u, 0x30u, 0xF5u, 0x8Cu, 0xB1u, 0xE3u, 0x1Du, 0xF6u, 0xE2u, 0x2Eu, 0x82u, 0x66u, 0xCAu, 0x60u, 0xC0u, 0x29u, 0x23u, 0xABu, 0x0Du, 0x53u, 0x4Eu, 0x6Fu, 0xD5u, 0xDBu, 0x37u, 0x45u, 0xDEu, 0xFDu, 0x8Eu, 0x2Fu, 0x03u, 0xFFu, 0x6Au, 0x72u, 0x6Du, 0x6Cu, 0x5Bu, 0x51u, 0x8Du, 0x1Bu, 0xAFu, 0x92u, 0xBBu, 0xDDu, 0xBCu, 0x7Fu, 0x11u, 0xD9u, 0x5Cu, 0x41u, 0x1Fu, 0x10u, 0x5Au, 0xD8u, 0x0Au, 0xC1u, 0x31u, 0x88u, 0xA5u, 0xCDu, 0x7Bu, 0xBDu, 0x2Du, 0x74u, 0xD0u, 0x12u, 0xB8u, 0xE5u, 0xB4u, 0xB0u, 0x89u, 0x69u, 0x97u, 0x4Au, 0x0Cu, 0x96u, 0x77u, 0x7Eu, 0x65u, 0xB9u, 0xF1u, 0x09u, 0xC5u, 0x6Eu, 0xC6u, 0x84u, 0x18u, 0xF0u, 0x7Du, 0xECu, 0x3Au, 0xDCu, 0x4Du, 0x20u, 0x79u, 0xEEu, 0x5Fu, 0x3Eu, 0xD7u, 0xCBu, 0x39u, 0x48u)
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
