// port-lint: tests x86/gfni.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// Galois Field New Instructions (GFNI)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun mulbyte(left: UByte, right: UByte): UByte {
    // this implementation follows the description in
    // https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_gf2p8mul_epi8
    val REDUCTION_POLYNOMIAL: UShort = 0x11bu.toUShort()
    val left1: UShort = left.into()
    val right1: UShort = right.into()
    var carrylessProduct: UShort = 0u.toUShort()
    // Carryless multiplication
    for (i in 0 until 8) {
        if ((((left1.toUInt() shr (i.toInt())).toUShort()) and 0x01u.toUShort()) != 0u.toUShort()) {
            carrylessProduct = carrylessProduct xor ((right1.toUInt() shl (i.toInt())).toUShort())
        }
    }
    // reduction, adding in "0" where appropriate to clear out high bits
    // note that REDUCTION_POLYNOMIAL is zero in this context
    for (i1 in (8..14).rev()) {
        if ((((carrylessProduct.toUInt() shr (i1.toInt())).toUShort()) and 0x01u.toUShort()) != 0u.toUShort()) {
            carrylessProduct = carrylessProduct xor ((REDUCTION_POLYNOMIAL.toUInt() shl ((i1 - 8).toInt())).toUShort())
        }
    }
    return carrylessProduct.toUByte()
}

public val NUM_TEST_WORDS_512: Int = 4

public val NUM_TEST_WORDS_256: Int = NUM_TEST_WORDS_512 * 2

public val NUM_TEST_WORDS_128: Int = NUM_TEST_WORDS_256 * 2

public val NUM_TEST_ENTRIES: Int = NUM_TEST_WORDS_512 * 64

public val NUM_TEST_WORDS_64: Int = NUM_TEST_WORDS_128 * 2

public val NUM_BYTES: Int = 256

public val NUM_BYTES_WORDS_128: Int = NUM_BYTES / 16

public val NUM_BYTES_WORDS_256: Int = NUM_BYTES_WORDS_128 / 2

public val NUM_BYTES_WORDS_512: Int = NUM_BYTES_WORDS_256 / 2

public fun parity(input: UByte): UByte {
    var accumulator = 0u.toUByte()
    for (i in 0 until 8) {
        accumulator = accumulator xor (((input.toUInt() shr (i.toInt())).toUByte()) and 0x01u.toUByte())
    }
    return accumulator
}

public fun matVecMultiplyAffine(matrix: ULong, x: UByte, b: UByte): UByte {
    // this implementation follows the description in
    // https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_gf2p8affine_epi64_epi8
    var accumulator = 0
    for (bit in 0 until 8) {
        accumulator = accumulator or ((parity(x and run { val rawBits = matrix; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }[bit]).toUInt() shl ((7 - bit).toInt())).toUByte())
    }
    return accumulator xor b
}

public fun generateAffineMulTestData(immediate: UByte): Triple<ULongArray, UByteArray, UByteArray> {
    val left: ULongArray  = ULongArray(NUM_TEST_WORDS_64) { 0uL }
    val right: UByteArray  = UByteArray(NUM_TEST_ENTRIES) { 0u.toUByte() }
    val result: UByteArray  = UByteArray(NUM_TEST_ENTRIES) { 0u.toUByte() }
    for (i in 0 until NUM_TEST_WORDS_64) {
        left[i] = (i.toULong()) * 103uL * 101uL
        for (j in 0 until 8) {
            val j64 = j.toULong()
            right[i * 8 + j] = ((left[i] + j64) % 256uL).toUByte()
            result[i * 8 + j] = matVecMultiplyAffine(left[i], right[i * 8 + j], immediate)
        }
    }
    return Triple(left, right, result)
}

public fun generateInvTestsData(): Pair<UByteArray, UByteArray> {
    val input: UByteArray  = UByteArray(NUM_BYTES) { 0u.toUByte() }
    val result: UByteArray  = UByteArray(NUM_BYTES) { 0u.toUByte() }
    for (i in 0 until NUM_BYTES) {
        input[i] = (i % 256).toUByte()
        result[i] = if (i == 0) {
            0u.toUByte()
        } else {
            1u.toUByte()
        }
    }
    return Pair(input, result)
}

public val AES_S_BOX: UByteArray = ubyteArrayOf(0x63u.toUByte(), 0x7cu.toUByte(), 0x77u.toUByte(), 0x7bu.toUByte(), 0xf2u.toUByte(), 0x6bu.toUByte(), 0x6fu.toUByte(), 0xc5u.toUByte(), 0x30u.toUByte(), 0x01u.toUByte(), 0x67u.toUByte(), 0x2bu.toUByte(), 0xfeu.toUByte(), 0xd7u.toUByte(), 0xabu.toUByte(), 0x76u.toUByte(), 0xcau.toUByte(), 0x82u.toUByte(), 0xc9u.toUByte(), 0x7du.toUByte(), 0xfau.toUByte(), 0x59u.toUByte(), 0x47u.toUByte(), 0xf0u.toUByte(), 0xadu.toUByte(), 0xd4u.toUByte(), 0xa2u.toUByte(), 0xafu.toUByte(), 0x9cu.toUByte(), 0xa4u.toUByte(), 0x72u.toUByte(), 0xc0u.toUByte(), 0xb7u.toUByte(), 0xfdu.toUByte(), 0x93u.toUByte(), 0x26u.toUByte(), 0x36u.toUByte(), 0x3fu.toUByte(), 0xf7u.toUByte(), 0xccu.toUByte(), 0x34u.toUByte(), 0xa5u.toUByte(), 0xe5u.toUByte(), 0xf1u.toUByte(), 0x71u.toUByte(), 0xd8u.toUByte(), 0x31u.toUByte(), 0x15u.toUByte(), 0x04u.toUByte(), 0xc7u.toUByte(), 0x23u.toUByte(), 0xc3u.toUByte(), 0x18u.toUByte(), 0x96u.toUByte(), 0x05u.toUByte(), 0x9au.toUByte(), 0x07u.toUByte(), 0x12u.toUByte(), 0x80u.toUByte(), 0xe2u.toUByte(), 0xebu.toUByte(), 0x27u.toUByte(), 0xb2u.toUByte(), 0x75u.toUByte(), 0x09u.toUByte(), 0x83u.toUByte(), 0x2cu.toUByte(), 0x1au.toUByte(), 0x1bu.toUByte(), 0x6eu.toUByte(), 0x5au.toUByte(), 0xa0u.toUByte(), 0x52u.toUByte(), 0x3bu.toUByte(), 0xd6u.toUByte(), 0xb3u.toUByte(), 0x29u.toUByte(), 0xe3u.toUByte(), 0x2fu.toUByte(), 0x84u.toUByte(), 0x53u.toUByte(), 0xd1u.toUByte(), 0x00u.toUByte(), 0xedu.toUByte(), 0x20u.toUByte(), 0xfcu.toUByte(), 0xb1u.toUByte(), 0x5bu.toUByte(), 0x6au.toUByte(), 0xcbu.toUByte(), 0xbeu.toUByte(), 0x39u.toUByte(), 0x4au.toUByte(), 0x4cu.toUByte(), 0x58u.toUByte(), 0xcfu.toUByte(), 0xd0u.toUByte(), 0xefu.toUByte(), 0xaau.toUByte(), 0xfbu.toUByte(), 0x43u.toUByte(), 0x4du.toUByte(), 0x33u.toUByte(), 0x85u.toUByte(), 0x45u.toUByte(), 0xf9u.toUByte(), 0x02u.toUByte(), 0x7fu.toUByte(), 0x50u.toUByte(), 0x3cu.toUByte(), 0x9fu.toUByte(), 0xa8u.toUByte(), 0x51u.toUByte(), 0xa3u.toUByte(), 0x40u.toUByte(), 0x8fu.toUByte(), 0x92u.toUByte(), 0x9du.toUByte(), 0x38u.toUByte(), 0xf5u.toUByte(), 0xbcu.toUByte(), 0xb6u.toUByte(), 0xdau.toUByte(), 0x21u.toUByte(), 0x10u.toUByte(), 0xffu.toUByte(), 0xf3u.toUByte(), 0xd2u.toUByte(), 0xcdu.toUByte(), 0x0cu.toUByte(), 0x13u.toUByte(), 0xecu.toUByte(), 0x5fu.toUByte(), 0x97u.toUByte(), 0x44u.toUByte(), 0x17u.toUByte(), 0xc4u.toUByte(), 0xa7u.toUByte(), 0x7eu.toUByte(), 0x3du.toUByte(), 0x64u.toUByte(), 0x5du.toUByte(), 0x19u.toUByte(), 0x73u.toUByte(), 0x60u.toUByte(), 0x81u.toUByte(), 0x4fu.toUByte(), 0xdcu.toUByte(), 0x22u.toUByte(), 0x2au.toUByte(), 0x90u.toUByte(), 0x88u.toUByte(), 0x46u.toUByte(), 0xeeu.toUByte(), 0xb8u.toUByte(), 0x14u.toUByte(), 0xdeu.toUByte(), 0x5eu.toUByte(), 0x0bu.toUByte(), 0xdbu.toUByte(), 0xe0u.toUByte(), 0x32u.toUByte(), 0x3au.toUByte(), 0x0au.toUByte(), 0x49u.toUByte(), 0x06u.toUByte(), 0x24u.toUByte(), 0x5cu.toUByte(), 0xc2u.toUByte(), 0xd3u.toUByte(), 0xacu.toUByte(), 0x62u.toUByte(), 0x91u.toUByte(), 0x95u.toUByte(), 0xe4u.toUByte(), 0x79u.toUByte(), 0xe7u.toUByte(), 0xc8u.toUByte(), 0x37u.toUByte(), 0x6du.toUByte(), 0x8du.toUByte(), 0xd5u.toUByte(), 0x4eu.toUByte(), 0xa9u.toUByte(), 0x6cu.toUByte(), 0x56u.toUByte(), 0xf4u.toUByte(), 0xeau.toUByte(), 0x65u.toUByte(), 0x7au.toUByte(), 0xaeu.toUByte(), 0x08u.toUByte(), 0xbau.toUByte(), 0x78u.toUByte(), 0x25u.toUByte(), 0x2eu.toUByte(), 0x1cu.toUByte(), 0xa6u.toUByte(), 0xb4u.toUByte(), 0xc6u.toUByte(), 0xe8u.toUByte(), 0xddu.toUByte(), 0x74u.toUByte(), 0x1fu.toUByte(), 0x4bu.toUByte(), 0xbdu.toUByte(), 0x8bu.toUByte(), 0x8au.toUByte(), 0x70u.toUByte(), 0x3eu.toUByte(), 0xb5u.toUByte(), 0x66u.toUByte(), 0x48u.toUByte(), 0x03u.toUByte(), 0xf6u.toUByte(), 0x0eu.toUByte(), 0x61u.toUByte(), 0x35u.toUByte(), 0x57u.toUByte(), 0xb9u.toUByte(), 0x86u.toUByte(), 0xc1u.toUByte(), 0x1du.toUByte(), 0x9eu.toUByte(), 0xe1u.toUByte(), 0xf8u.toUByte(), 0x98u.toUByte(), 0x11u.toUByte(), 0x69u.toUByte(), 0xd9u.toUByte(), 0x8eu.toUByte(), 0x94u.toUByte(), 0x9bu.toUByte(), 0x1eu.toUByte(), 0x87u.toUByte(), 0xe9u.toUByte(), 0xceu.toUByte(), 0x55u.toUByte(), 0x28u.toUByte(), 0xdfu.toUByte(), 0x8cu.toUByte(), 0xa1u.toUByte(), 0x89u.toUByte(), 0x0du.toUByte(), 0xbfu.toUByte(), 0xe6u.toUByte(), 0x42u.toUByte(), 0x68u.toUByte(), 0x41u.toUByte(), 0x99u.toUByte(), 0x2du.toUByte(), 0x0fu.toUByte(), 0xb0u.toUByte(), 0x54u.toUByte(), 0xbbu.toUByte(), 0x16u.toUByte())

public fun generateByteMulTestData(): Triple<UByteArray, UByteArray, UByteArray> {
    val left: UByteArray  = UByteArray(NUM_TEST_ENTRIES) { 0u.toUByte() }
    val right: UByteArray  = UByteArray(NUM_TEST_ENTRIES) { 0u.toUByte() }
    val result: UByteArray  = UByteArray(NUM_TEST_ENTRIES) { 0u.toUByte() }
    for (i in 0 until NUM_TEST_ENTRIES) {
        left[i] = (i % 256).toUByte()
        right[i] = (left[i] * (101u.toUByte())).toUByte()
        result[i] = mulbyte(left[i], right[i])
    }
    return Triple(left, right, result)
}

public fun <T> loadM128iWord(data: List<T>, wordIndex: Int): M128i {
    val pointer = (ConstPtr((data.asPtr()).rawAddress + ((wordIndex * 16).toLong()))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return mmLoaduSi128(blackBox(pointer))
}

public fun <T> loadM256iWord(data: List<T>, wordIndex: Int): M256i {
    val pointer = (ConstPtr((data.asPtr()).rawAddress + ((wordIndex * 32).toLong()))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return mm256LoaduSi256(blackBox(pointer))
}

public fun <T> loadM512iWord(data: List<T>, wordIndex: Int): M512i {
    val pointer = (ConstPtr((data.asPtr()).rawAddress + ((wordIndex * 64).toLong()))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return mm512LoaduSi512(blackBox(pointer))
}

public fun testMm512Gf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_512) {
        val left1 = loadM512iWord(left, i)
        val right1 = loadM512iWord(right, i)
        val expected1 = loadM512iWord(expected, i)
        val result = mm512Gf2p8mulEpi8(left1, right1)
        assertEqM512i(result, expected1)
    }
}

public fun testMm512MaskzGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_512) {
        val left1 = loadM512iWord(left, i)
        val right1 = loadM512iWord(right, i)
        val resultZero = mm512MaskzGf2p8mulEpi8(0uL, left1, right1)
        assertEqM512i(resultZero, mm512SetzeroSi512())
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u.toUShort()
        val expectedResult = mm512Gf2p8mulEpi8(left1, right1)
        val resultMasked = mm512MaskzGf2p8mulEpi8(maskBytes, left1, right1)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, mm512SetzeroSi512(), expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm512MaskGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_512) {
        val left1 = loadM512iWord(left, i)
        val right1 = loadM512iWord(right, i)
        val resultLeft = mm512MaskGf2p8mulEpi8(left1, 0uL, left1, right1)
        assertEqM512i(resultLeft, left1)
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u.toUShort()
        val expectedResult = mm512Gf2p8mulEpi8(left1, right1)
        val resultMasked = mm512MaskGf2p8mulEpi8(left1, maskBytes, left1, right1)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, left1, expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm256Gf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_256) {
        val left1 = loadM256iWord(left, i)
        val right1 = loadM256iWord(right, i)
        val expected1 = loadM256iWord(expected, i)
        val result = mm256Gf2p8mulEpi8(left1, right1)
        assertEqM256i(result, expected1)
    }
}

public fun testMm256MaskzGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_256) {
        val MASK_WORDS: Int = 0x6C
        val left1 = loadM256iWord(left, i)
        val right1 = loadM256iWord(right, i)
        val resultZero = mm256MaskzGf2p8mulEpi8(0u, left1, right1)
        assertEqM256i(resultZero, mm256SetzeroSi256())
        val maskBytes: Mmask32 = 0x0FF0FF00u
        val expectedResult = mm256Gf2p8mulEpi8(left1, right1)
        val resultMasked = mm256MaskzGf2p8mulEpi8(maskBytes, left1, right1)
        val expectedMasked = mm256BlendEpi32(mm256SetzeroSi256(), expectedResult, MASK_WORDS)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMm256MaskGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_256) {
        val MASK_WORDS: Int = 0x6C
        val left1 = loadM256iWord(left, i)
        val right1 = loadM256iWord(right, i)
        val resultLeft = mm256MaskGf2p8mulEpi8(left1, 0u, left1, right1)
        assertEqM256i(resultLeft, left1)
        val maskBytes: Mmask32 = 0x0FF0FF00u
        val expectedResult = mm256Gf2p8mulEpi8(left1, right1)
        val resultMasked = mm256MaskGf2p8mulEpi8(left1, maskBytes, left1, right1)
        val expectedMasked = mm256BlendEpi32(left1, expectedResult, MASK_WORDS)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMmGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_128) {
        val left1 = loadM128iWord(left, i)
        val right1 = loadM128iWord(right, i)
        val expected1 = loadM128iWord(expected, i)
        val result = mmGf2p8mulEpi8(left1, right1)
        assertEqM128i(result, expected1)
    }
}

public fun testMmMaskzGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_128) {
        val MASK_WORDS: Int = 0x6
        val left1 = loadM128iWord(left, i)
        val right1 = loadM128iWord(right, i)
        val resultZero = mmMaskzGf2p8mulEpi8(0u.toUShort(), left1, right1)
        assertEqM128i(resultZero, mmSetzeroSi128())
        val maskBytes: Mmask16 = 0x0FF0u.toUShort()
        val expectedResult = mmGf2p8mulEpi8(left1, right1)
        val resultMasked = mmMaskzGf2p8mulEpi8(maskBytes, left1, right1)
        val expectedMasked = mmBlendEpi32(mmSetzeroSi128(), expectedResult, MASK_WORDS)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMmMaskGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_128) {
        val MASK_WORDS: Int = 0x6
        val left1 = loadM128iWord(left, i)
        val right1 = loadM128iWord(right, i)
        val resultLeft = mmMaskGf2p8mulEpi8(left1, 0u.toUShort(), left1, right1)
        assertEqM128i(resultLeft, left1)
        val maskBytes: Mmask16 = 0x0FF0u.toUShort()
        val expectedResult = mmGf2p8mulEpi8(left1, right1)
        val resultMasked = mmMaskGf2p8mulEpi8(left1, maskBytes, left1, right1)
        val expectedMasked = mmBlendEpi32(left1, expectedResult, MASK_WORDS)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMm512Gf2p8affineEpi64Epi8() {
    val IDENTITY_BYTE: Int = 0
    val CONSTANT_BYTE: Int = 0x63
    val identity: Long = 0x0102040810204080L
    val constant: Long = 0L
    val identity1 = mm512Set1Epi64(identity)
    val constant1 = mm512Set1Epi64(constant)
    val constantReference = mm512Set1Epi8(CONSTANT_BYTE.toByte())
    val (bytes, moreBytes, _) = generateByteMulTestData()
    val (matrices, vectors, references) = generateAffineMulTestData(IDENTITY_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val data = loadM512iWord(bytes, i)
        val result = mm512Gf2p8affineEpi64Epi8(data, identity1, IDENTITY_BYTE)
        assertEqM512i(result, data)
        val result1 = mm512Gf2p8affineEpi64Epi8(data, constant1, CONSTANT_BYTE)
        assertEqM512i(result1, constantReference)
        val data1 = loadM512iWord(moreBytes, i)
        val result2 = mm512Gf2p8affineEpi64Epi8(data1, identity1, IDENTITY_BYTE)
        assertEqM512i(result2, data1)
        val result3 = mm512Gf2p8affineEpi64Epi8(data1, constant1, CONSTANT_BYTE)
        assertEqM512i(result3, constantReference)
        val matrix = loadM512iWord(matrices, i)
        val vector = loadM512iWord(vectors, i)
        val reference = loadM512iWord(references, i)
        val result4 = mm512Gf2p8affineEpi64Epi8(vector, matrix, IDENTITY_BYTE)
        assertEqM512i(result4, reference)
    }
}

public fun testMm512MaskzGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val matrix = loadM512iWord(matrices, i)
        val vector = loadM512iWord(vectors, i)
        val resultZero = mm512MaskzGf2p8affineEpi64Epi8(0uL, vector, matrix, CONSTANT_BYTE)
        assertEqM512i(resultZero, mm512SetzeroSi512())
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u.toUShort()
        val expectedResult = mm512Gf2p8affineEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        val resultMasked = mm512MaskzGf2p8affineEpi64Epi8(maskBytes, vector, matrix, CONSTANT_BYTE)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, mm512SetzeroSi512(), expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm512MaskGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val left = loadM512iWord(vectors, i)
        val right = loadM512iWord(matrices, i)
        val resultLeft = mm512MaskGf2p8affineEpi64Epi8(left, 0uL, left, right, CONSTANT_BYTE)
        assertEqM512i(resultLeft, left)
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u.toUShort()
        val expectedResult = mm512Gf2p8affineEpi64Epi8(left, right, CONSTANT_BYTE)
        val resultMasked = mm512MaskGf2p8affineEpi64Epi8(left, maskBytes, left, right, CONSTANT_BYTE)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, left, expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm256Gf2p8affineEpi64Epi8() {
    val IDENTITY_BYTE: Int = 0
    val CONSTANT_BYTE: Int = 0x63
    val identity: Long = 0x0102040810204080L
    val constant: Long = 0L
    val identity1 = mm256Set1Epi64x(identity)
    val constant1 = mm256Set1Epi64x(constant)
    val constantReference = mm256Set1Epi8(CONSTANT_BYTE.toByte())
    val (bytes, moreBytes, _) = generateByteMulTestData()
    val (matrices, vectors, references) = generateAffineMulTestData(IDENTITY_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val data = loadM256iWord(bytes, i)
        val result = mm256Gf2p8affineEpi64Epi8(data, identity1, IDENTITY_BYTE)
        assertEqM256i(result, data)
        val result1 = mm256Gf2p8affineEpi64Epi8(data, constant1, CONSTANT_BYTE)
        assertEqM256i(result1, constantReference)
        val data1 = loadM256iWord(moreBytes, i)
        val result2 = mm256Gf2p8affineEpi64Epi8(data1, identity1, IDENTITY_BYTE)
        assertEqM256i(result2, data1)
        val result3 = mm256Gf2p8affineEpi64Epi8(data1, constant1, CONSTANT_BYTE)
        assertEqM256i(result3, constantReference)
        val matrix = loadM256iWord(matrices, i)
        val vector = loadM256iWord(vectors, i)
        val reference = loadM256iWord(references, i)
        val result4 = mm256Gf2p8affineEpi64Epi8(vector, matrix, IDENTITY_BYTE)
        assertEqM256i(result4, reference)
    }
}

public fun testMm256MaskzGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val MASK_WORDS: Int = 0xD8
        val matrix = loadM256iWord(matrices, i)
        val vector = loadM256iWord(vectors, i)
        val resultZero = mm256MaskzGf2p8affineEpi64Epi8(0u, vector, matrix, CONSTANT_BYTE)
        assertEqM256i(resultZero, mm256SetzeroSi256())
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        val resultMasked = mm256MaskzGf2p8affineEpi64Epi8(maskBytes, vector, matrix, CONSTANT_BYTE)
        val expectedMasked = mm256BlendEpi32(mm256SetzeroSi256(), expectedResult, MASK_WORDS)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMm256MaskGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val MASK_WORDS: Int = 0xD8
        val left = loadM256iWord(vectors, i)
        val right = loadM256iWord(matrices, i)
        val resultLeft = mm256MaskGf2p8affineEpi64Epi8(left, 0u, left, right, CONSTANT_BYTE)
        assertEqM256i(resultLeft, left)
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineEpi64Epi8(left, right, CONSTANT_BYTE)
        val resultMasked = mm256MaskGf2p8affineEpi64Epi8(left, maskBytes, left, right, CONSTANT_BYTE)
        val expectedMasked = mm256BlendEpi32(left, expectedResult, MASK_WORDS)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMmGf2p8affineEpi64Epi8() {
    val IDENTITY_BYTE: Int = 0
    val CONSTANT_BYTE: Int = 0x63
    val identity: Long = 0x0102040810204080L
    val constant: Long = 0L
    val identity1 = mmSet1Epi64x(identity)
    val constant1 = mmSet1Epi64x(constant)
    val constantReference = mmSet1Epi8(CONSTANT_BYTE.toByte())
    val (bytes, moreBytes, _) = generateByteMulTestData()
    val (matrices, vectors, references) = generateAffineMulTestData(IDENTITY_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val data = loadM128iWord(bytes, i)
        val result = mmGf2p8affineEpi64Epi8(data, identity1, IDENTITY_BYTE)
        assertEqM128i(result, data)
        val result1 = mmGf2p8affineEpi64Epi8(data, constant1, CONSTANT_BYTE)
        assertEqM128i(result1, constantReference)
        val data1 = loadM128iWord(moreBytes, i)
        val result2 = mmGf2p8affineEpi64Epi8(data1, identity1, IDENTITY_BYTE)
        assertEqM128i(result2, data1)
        val result3 = mmGf2p8affineEpi64Epi8(data1, constant1, CONSTANT_BYTE)
        assertEqM128i(result3, constantReference)
        val matrix = loadM128iWord(matrices, i)
        val vector = loadM128iWord(vectors, i)
        val reference = loadM128iWord(references, i)
        val result4 = mmGf2p8affineEpi64Epi8(vector, matrix, IDENTITY_BYTE)
        assertEqM128i(result4, reference)
    }
}

public fun testMmMaskzGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val MASK_WORDS: Int = 0x6
        val matrix = loadM128iWord(matrices, i)
        val vector = loadM128iWord(vectors, i)
        val resultZero = mmMaskzGf2p8affineEpi64Epi8(0u.toUShort(), vector, matrix, CONSTANT_BYTE)
        assertEqM128i(resultZero, mmSetzeroSi128())
        val maskBytes: Mmask16 = 0x0FF0u.toUShort()
        val expectedResult = mmGf2p8affineEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        val resultMasked = mmMaskzGf2p8affineEpi64Epi8(maskBytes, vector, matrix, CONSTANT_BYTE)
        val expectedMasked = mmBlendEpi32(mmSetzeroSi128(), expectedResult, MASK_WORDS)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMmMaskGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val MASK_WORDS: Int = 0x6
        val left = loadM128iWord(vectors, i)
        val right = loadM128iWord(matrices, i)
        val resultLeft = mmMaskGf2p8affineEpi64Epi8(left, 0u.toUShort(), left, right, CONSTANT_BYTE)
        assertEqM128i(resultLeft, left)
        val maskBytes: Mmask16 = 0x0FF0u.toUShort()
        val expectedResult = mmGf2p8affineEpi64Epi8(left, right, CONSTANT_BYTE)
        val resultMasked = mmMaskGf2p8affineEpi64Epi8(left, maskBytes, left, right, CONSTANT_BYTE)
        val expectedMasked = mmBlendEpi32(left, expectedResult, MASK_WORDS)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMm512Gf2p8affineinvEpi64Epi8() {
    val IDENTITY_BYTE: Int = 0
    val CONSTANT_BYTE: Int = 0x63
    // validate everything by virtue of checking against the AES SBox
    val AES_S_BOX_MATRIX: Long = 0xF1E3C78F1F3E7CF8uL.toLong()
    val identity: Long = 0x0102040810204080L
    val identity1 = mm512Set1Epi64(identity)
    // validate inversion
    val (inputs, results) = generateInvTestsData()
    for (i in 0 until NUM_BYTES_WORDS_512) {
        val input = loadM512iWord(inputs, i)
        val reference = loadM512iWord(results, i)
        val result = mm512Gf2p8affineinvEpi64Epi8(input, identity1, IDENTITY_BYTE)
        val remultiplied = mm512Gf2p8mulEpi8(result, input)
        assertEqM512i(remultiplied, reference)
    }
    // validate subsequent affine operation
    val (matrices, vectors, affineExpected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i1 in 0 until NUM_TEST_WORDS_512) {
        val vector = loadM512iWord(vectors, i1)
        val matrix = loadM512iWord(matrices, i1)
        val invVec = mm512Gf2p8affineinvEpi64Epi8(vector, identity1, IDENTITY_BYTE)
        val reference1 = mm512Gf2p8affineEpi64Epi8(invVec, matrix, CONSTANT_BYTE)
        val result1 = mm512Gf2p8affineinvEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        assertEqM512i(result1, reference1)
    }
    val sboxMatrix = mm512Set1Epi64(AES_S_BOX_MATRIX)
    for (i2 in 0 until NUM_BYTES_WORDS_512) {
        val reference2 = loadM512iWord(AES_S_BOX, i2)
        val input1 = loadM512iWord(inputs, i2)
        val result2 = mm512Gf2p8affineinvEpi64Epi8(input1, sboxMatrix, CONSTANT_BYTE)
        assertEqM512i(result2, reference2)
    }
}

public fun testMm512MaskzGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val matrix = loadM512iWord(matrices, i)
        val vector = loadM512iWord(vectors, i)
        val resultZero = mm512MaskzGf2p8affineinvEpi64Epi8(0uL, vector, matrix, CONSTANT_BYTE)
        assertEqM512i(resultZero, mm512SetzeroSi512())
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u.toUShort()
        val expectedResult = mm512Gf2p8affineinvEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        val resultMasked = mm512MaskzGf2p8affineinvEpi64Epi8(maskBytes, vector, matrix, CONSTANT_BYTE)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, mm512SetzeroSi512(), expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm512MaskGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val left = loadM512iWord(vectors, i)
        val right = loadM512iWord(matrices, i)
        val resultLeft = mm512MaskGf2p8affineinvEpi64Epi8(left, 0uL, left, right, CONSTANT_BYTE)
        assertEqM512i(resultLeft, left)
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u.toUShort()
        val expectedResult = mm512Gf2p8affineinvEpi64Epi8(left, right, CONSTANT_BYTE)
        val resultMasked = mm512MaskGf2p8affineinvEpi64Epi8(left, maskBytes, left, right, CONSTANT_BYTE)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, left, expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm256Gf2p8affineinvEpi64Epi8() {
    val IDENTITY_BYTE: Int = 0
    val CONSTANT_BYTE: Int = 0x63
    // validate everything by virtue of checking against the AES SBox
    val AES_S_BOX_MATRIX: Long = 0xF1E3C78F1F3E7CF8uL.toLong()
    val identity: Long = 0x0102040810204080L
    val identity1 = mm256Set1Epi64x(identity)
    // validate inversion
    val (inputs, results) = generateInvTestsData()
    for (i in 0 until NUM_BYTES_WORDS_256) {
        val input = loadM256iWord(inputs, i)
        val reference = loadM256iWord(results, i)
        val result = mm256Gf2p8affineinvEpi64Epi8(input, identity1, IDENTITY_BYTE)
        val remultiplied = mm256Gf2p8mulEpi8(result, input)
        assertEqM256i(remultiplied, reference)
    }
    // validate subsequent affine operation
    val (matrices, vectors, affineExpected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i1 in 0 until NUM_TEST_WORDS_256) {
        val vector = loadM256iWord(vectors, i1)
        val matrix = loadM256iWord(matrices, i1)
        val invVec = mm256Gf2p8affineinvEpi64Epi8(vector, identity1, IDENTITY_BYTE)
        val reference1 = mm256Gf2p8affineEpi64Epi8(invVec, matrix, CONSTANT_BYTE)
        val result1 = mm256Gf2p8affineinvEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        assertEqM256i(result1, reference1)
    }
    val sboxMatrix = mm256Set1Epi64x(AES_S_BOX_MATRIX)
    for (i2 in 0 until NUM_BYTES_WORDS_256) {
        val reference2 = loadM256iWord(AES_S_BOX, i2)
        val input1 = loadM256iWord(inputs, i2)
        val result2 = mm256Gf2p8affineinvEpi64Epi8(input1, sboxMatrix, CONSTANT_BYTE)
        assertEqM256i(result2, reference2)
    }
}

public fun testMm256MaskzGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val MASK_WORDS: Int = 0xD8
        val matrix = loadM256iWord(matrices, i)
        val vector = loadM256iWord(vectors, i)
        val resultZero = mm256MaskzGf2p8affineinvEpi64Epi8(0u, vector, matrix, CONSTANT_BYTE)
        assertEqM256i(resultZero, mm256SetzeroSi256())
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineinvEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        val resultMasked = mm256MaskzGf2p8affineinvEpi64Epi8(maskBytes, vector, matrix, CONSTANT_BYTE)
        val expectedMasked = mm256BlendEpi32(mm256SetzeroSi256(), expectedResult, MASK_WORDS)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMm256MaskGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val MASK_WORDS: Int = 0xD8
        val left = loadM256iWord(vectors, i)
        val right = loadM256iWord(matrices, i)
        val resultLeft = mm256MaskGf2p8affineinvEpi64Epi8(left, 0u, left, right, CONSTANT_BYTE)
        assertEqM256i(resultLeft, left)
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineinvEpi64Epi8(left, right, CONSTANT_BYTE)
        val resultMasked = mm256MaskGf2p8affineinvEpi64Epi8(left, maskBytes, left, right, CONSTANT_BYTE)
        val expectedMasked = mm256BlendEpi32(left, expectedResult, MASK_WORDS)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMmGf2p8affineinvEpi64Epi8() {
    val IDENTITY_BYTE: Int = 0
    val CONSTANT_BYTE: Int = 0x63
    // validate everything by virtue of checking against the AES SBox
    val AES_S_BOX_MATRIX: Long = 0xF1E3C78F1F3E7CF8uL.toLong()
    val identity: Long = 0x0102040810204080L
    val identity1 = mmSet1Epi64x(identity)
    // validate inversion
    val (inputs, results) = generateInvTestsData()
    for (i in 0 until NUM_BYTES_WORDS_128) {
        val input = loadM128iWord(inputs, i)
        val reference = loadM128iWord(results, i)
        val result = mmGf2p8affineinvEpi64Epi8(input, identity1, IDENTITY_BYTE)
        val remultiplied = mmGf2p8mulEpi8(result, input)
        assertEqM128i(remultiplied, reference)
    }
    // validate subsequent affine operation
    val (matrices, vectors, affineExpected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i1 in 0 until NUM_TEST_WORDS_128) {
        val vector = loadM128iWord(vectors, i1)
        val matrix = loadM128iWord(matrices, i1)
        val invVec = mmGf2p8affineinvEpi64Epi8(vector, identity1, IDENTITY_BYTE)
        val reference1 = mmGf2p8affineEpi64Epi8(invVec, matrix, CONSTANT_BYTE)
        val result1 = mmGf2p8affineinvEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        assertEqM128i(result1, reference1)
    }
    val sboxMatrix = mmSet1Epi64x(AES_S_BOX_MATRIX)
    for (i2 in 0 until NUM_BYTES_WORDS_128) {
        val reference2 = loadM128iWord(AES_S_BOX, i2)
        val input1 = loadM128iWord(inputs, i2)
        val result2 = mmGf2p8affineinvEpi64Epi8(input1, sboxMatrix, CONSTANT_BYTE)
        assertEqM128i(result2, reference2)
    }
}

public fun testMmMaskzGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val MASK_WORDS: Int = 0x6
        val matrix = loadM128iWord(matrices, i)
        val vector = loadM128iWord(vectors, i)
        val resultZero = mmMaskzGf2p8affineinvEpi64Epi8(0u.toUShort(), vector, matrix, CONSTANT_BYTE)
        assertEqM128i(resultZero, mmSetzeroSi128())
        val maskBytes: Mmask16 = 0x0FF0u.toUShort()
        val expectedResult = mmGf2p8affineinvEpi64Epi8(vector, matrix, CONSTANT_BYTE)
        val resultMasked = mmMaskzGf2p8affineinvEpi64Epi8(maskBytes, vector, matrix, CONSTANT_BYTE)
        val expectedMasked = mmBlendEpi32(mmSetzeroSi128(), expectedResult, MASK_WORDS)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMmMaskGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val MASK_WORDS: Int = 0x6
        val left = loadM128iWord(vectors, i)
        val right = loadM128iWord(matrices, i)
        val resultLeft = mmMaskGf2p8affineinvEpi64Epi8(left, 0u.toUShort(), left, right, CONSTANT_BYTE)
        assertEqM128i(resultLeft, left)
        val maskBytes: Mmask16 = 0x0FF0u.toUShort()
        val expectedResult = mmGf2p8affineinvEpi64Epi8(left, right, CONSTANT_BYTE)
        val resultMasked = mmMaskGf2p8affineinvEpi64Epi8(left, maskBytes, left, right, CONSTANT_BYTE)
        val expectedMasked = mmBlendEpi32(left, expectedResult, MASK_WORDS)
        assertEqM128i(resultMasked, expectedMasked)
    }
}
