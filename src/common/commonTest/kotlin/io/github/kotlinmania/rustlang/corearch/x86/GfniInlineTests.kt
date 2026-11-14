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
    val REDUCTION_POLYNOMIAL: UShort = 0x11bu
    val left1: UShort = left.into()
    val right1: UShort = right.into()
    var carrylessProduct: UShort = 0u
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
    var accumulator = 0u
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
            0u
        } else {
            1u
        }
    }
    return Pair(input, result)
}

public val AES_S_BOX: UByteArray = ubyteArrayOf(0x63u, 0x7cu, 0x77u, 0x7bu, 0xf2u, 0x6bu, 0x6fu, 0xc5u, 0x30u, 0x01u, 0x67u, 0x2bu, 0xfeu, 0xd7u, 0xabu, 0x76u, 0xcau, 0x82u, 0xc9u, 0x7du, 0xfau, 0x59u, 0x47u, 0xf0u, 0xadu, 0xd4u, 0xa2u, 0xafu, 0x9cu, 0xa4u, 0x72u, 0xc0u, 0xb7u, 0xfdu, 0x93u, 0x26u, 0x36u, 0x3fu, 0xf7u, 0xccu, 0x34u, 0xa5u, 0xe5u, 0xf1u, 0x71u, 0xd8u, 0x31u, 0x15u, 0x04u, 0xc7u, 0x23u, 0xc3u, 0x18u, 0x96u, 0x05u, 0x9au, 0x07u, 0x12u, 0x80u, 0xe2u, 0xebu, 0x27u, 0xb2u, 0x75u, 0x09u, 0x83u, 0x2cu, 0x1au, 0x1bu, 0x6eu, 0x5au, 0xa0u, 0x52u, 0x3bu, 0xd6u, 0xb3u, 0x29u, 0xe3u, 0x2fu, 0x84u, 0x53u, 0xd1u, 0x00u, 0xedu, 0x20u, 0xfcu, 0xb1u, 0x5bu, 0x6au, 0xcbu, 0xbeu, 0x39u, 0x4au, 0x4cu, 0x58u, 0xcfu, 0xd0u, 0xefu, 0xaau, 0xfbu, 0x43u, 0x4du, 0x33u, 0x85u, 0x45u, 0xf9u, 0x02u, 0x7fu, 0x50u, 0x3cu, 0x9fu, 0xa8u, 0x51u, 0xa3u, 0x40u, 0x8fu, 0x92u, 0x9du, 0x38u, 0xf5u, 0xbcu, 0xb6u, 0xdau, 0x21u, 0x10u, 0xffu, 0xf3u, 0xd2u, 0xcdu, 0x0cu, 0x13u, 0xecu, 0x5fu, 0x97u, 0x44u, 0x17u, 0xc4u, 0xa7u, 0x7eu, 0x3du, 0x64u, 0x5du, 0x19u, 0x73u, 0x60u, 0x81u, 0x4fu, 0xdcu, 0x22u, 0x2au, 0x90u, 0x88u, 0x46u, 0xeeu, 0xb8u, 0x14u, 0xdeu, 0x5eu, 0x0bu, 0xdbu, 0xe0u, 0x32u, 0x3au, 0x0au, 0x49u, 0x06u, 0x24u, 0x5cu, 0xc2u, 0xd3u, 0xacu, 0x62u, 0x91u, 0x95u, 0xe4u, 0x79u, 0xe7u, 0xc8u, 0x37u, 0x6du, 0x8du, 0xd5u, 0x4eu, 0xa9u, 0x6cu, 0x56u, 0xf4u, 0xeau, 0x65u, 0x7au, 0xaeu, 0x08u, 0xbau, 0x78u, 0x25u, 0x2eu, 0x1cu, 0xa6u, 0xb4u, 0xc6u, 0xe8u, 0xddu, 0x74u, 0x1fu, 0x4bu, 0xbdu, 0x8bu, 0x8au, 0x70u, 0x3eu, 0xb5u, 0x66u, 0x48u, 0x03u, 0xf6u, 0x0eu, 0x61u, 0x35u, 0x57u, 0xb9u, 0x86u, 0xc1u, 0x1du, 0x9eu, 0xe1u, 0xf8u, 0x98u, 0x11u, 0x69u, 0xd9u, 0x8eu, 0x94u, 0x9bu, 0x1eu, 0x87u, 0xe9u, 0xceu, 0x55u, 0x28u, 0xdfu, 0x8cu, 0xa1u, 0x89u, 0x0du, 0xbfu, 0xe6u, 0x42u, 0x68u, 0x41u, 0x99u, 0x2du, 0x0fu, 0xb0u, 0x54u, 0xbbu, 0x16u)

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
        val maskWords: Mmask16 = 0x55F0u
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
        val maskWords: Mmask16 = 0x55F0u
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
        val left1 = loadM256iWord(left, i)
        val right1 = loadM256iWord(right, i)
        val resultZero = mm256MaskzGf2p8mulEpi8(0u, left1, right1)
        assertEqM256i(resultZero, mm256SetzeroSi256())
        val maskBytes: Mmask32 = 0x0FF0FF00u
        val expectedResult = mm256Gf2p8mulEpi8(left1, right1)
        val resultMasked = mm256MaskzGf2p8mulEpi8(maskBytes, left1, right1)
        val expectedMasked = mm256BlendEpi32<MaskWords>(mm256SetzeroSi256(), expectedResult)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMm256MaskGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_256) {
        val left1 = loadM256iWord(left, i)
        val right1 = loadM256iWord(right, i)
        val resultLeft = mm256MaskGf2p8mulEpi8(left1, 0u, left1, right1)
        assertEqM256i(resultLeft, left1)
        val maskBytes: Mmask32 = 0x0FF0FF00u
        val expectedResult = mm256Gf2p8mulEpi8(left1, right1)
        val resultMasked = mm256MaskGf2p8mulEpi8(left1, maskBytes, left1, right1)
        val expectedMasked = mm256BlendEpi32<MaskWords>(left1, expectedResult)
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
        val left1 = loadM128iWord(left, i)
        val right1 = loadM128iWord(right, i)
        val resultZero = mmMaskzGf2p8mulEpi8(0u, left1, right1)
        assertEqM128i(resultZero, mmSetzeroSi128())
        val maskBytes: Mmask16 = 0x0FF0u
        val expectedResult = mmGf2p8mulEpi8(left1, right1)
        val resultMasked = mmMaskzGf2p8mulEpi8(maskBytes, left1, right1)
        val expectedMasked = mmBlendEpi32<MaskWords>(mmSetzeroSi128(), expectedResult)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMmMaskGf2p8mulEpi8() {
    val (left, right, expected) = generateByteMulTestData()
    for (i in 0 until NUM_TEST_WORDS_128) {
        val left1 = loadM128iWord(left, i)
        val right1 = loadM128iWord(right, i)
        val resultLeft = mmMaskGf2p8mulEpi8(left1, 0u, left1, right1)
        assertEqM128i(resultLeft, left1)
        val maskBytes: Mmask16 = 0x0FF0u
        val expectedResult = mmGf2p8mulEpi8(left1, right1)
        val resultMasked = mmMaskGf2p8mulEpi8(left1, maskBytes, left1, right1)
        val expectedMasked = mmBlendEpi32<MaskWords>(left1, expectedResult)
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
        val result = mm512Gf2p8affineEpi64Epi8<IdentityByte>(data, identity1)
        assertEqM512i(result, data)
        val result1 = mm512Gf2p8affineEpi64Epi8<ConstantByte>(data, constant1)
        assertEqM512i(result1, constantReference)
        val data1 = loadM512iWord(moreBytes, i)
        val result2 = mm512Gf2p8affineEpi64Epi8<IdentityByte>(data1, identity1)
        assertEqM512i(result2, data1)
        val result3 = mm512Gf2p8affineEpi64Epi8<ConstantByte>(data1, constant1)
        assertEqM512i(result3, constantReference)
        val matrix = loadM512iWord(matrices, i)
        val vector = loadM512iWord(vectors, i)
        val reference = loadM512iWord(references, i)
        val result4 = mm512Gf2p8affineEpi64Epi8<IdentityByte>(vector, matrix)
        assertEqM512i(result4, reference)
    }
}

public fun testMm512MaskzGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val matrix = loadM512iWord(matrices, i)
        val vector = loadM512iWord(vectors, i)
        val resultZero = mm512MaskzGf2p8affineEpi64Epi8<ConstantByte>(0uL, vector, matrix)
        assertEqM512i(resultZero, mm512SetzeroSi512())
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u
        val expectedResult = mm512Gf2p8affineEpi64Epi8<ConstantByte>(vector, matrix)
        val resultMasked = mm512MaskzGf2p8affineEpi64Epi8<ConstantByte>(maskBytes, vector, matrix)
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
        val resultLeft = mm512MaskGf2p8affineEpi64Epi8<ConstantByte>(left, 0uL, left, right)
        assertEqM512i(resultLeft, left)
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u
        val expectedResult = mm512Gf2p8affineEpi64Epi8<ConstantByte>(left, right)
        val resultMasked = mm512MaskGf2p8affineEpi64Epi8<ConstantByte>(left, maskBytes, left, right)
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
        val result = mm256Gf2p8affineEpi64Epi8<IdentityByte>(data, identity1)
        assertEqM256i(result, data)
        val result1 = mm256Gf2p8affineEpi64Epi8<ConstantByte>(data, constant1)
        assertEqM256i(result1, constantReference)
        val data1 = loadM256iWord(moreBytes, i)
        val result2 = mm256Gf2p8affineEpi64Epi8<IdentityByte>(data1, identity1)
        assertEqM256i(result2, data1)
        val result3 = mm256Gf2p8affineEpi64Epi8<ConstantByte>(data1, constant1)
        assertEqM256i(result3, constantReference)
        val matrix = loadM256iWord(matrices, i)
        val vector = loadM256iWord(vectors, i)
        val reference = loadM256iWord(references, i)
        val result4 = mm256Gf2p8affineEpi64Epi8<IdentityByte>(vector, matrix)
        assertEqM256i(result4, reference)
    }
}

public fun testMm256MaskzGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val matrix = loadM256iWord(matrices, i)
        val vector = loadM256iWord(vectors, i)
        val resultZero = mm256MaskzGf2p8affineEpi64Epi8<ConstantByte>(0u, vector, matrix)
        assertEqM256i(resultZero, mm256SetzeroSi256())
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineEpi64Epi8<ConstantByte>(vector, matrix)
        val resultMasked = mm256MaskzGf2p8affineEpi64Epi8<ConstantByte>(maskBytes, vector, matrix)
        val expectedMasked = mm256BlendEpi32<MaskWords>(mm256SetzeroSi256(), expectedResult)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMm256MaskGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val left = loadM256iWord(vectors, i)
        val right = loadM256iWord(matrices, i)
        val resultLeft = mm256MaskGf2p8affineEpi64Epi8<ConstantByte>(left, 0u, left, right)
        assertEqM256i(resultLeft, left)
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineEpi64Epi8<ConstantByte>(left, right)
        val resultMasked = mm256MaskGf2p8affineEpi64Epi8<ConstantByte>(left, maskBytes, left, right)
        val expectedMasked = mm256BlendEpi32<MaskWords>(left, expectedResult)
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
        val result = mmGf2p8affineEpi64Epi8<IdentityByte>(data, identity1)
        assertEqM128i(result, data)
        val result1 = mmGf2p8affineEpi64Epi8<ConstantByte>(data, constant1)
        assertEqM128i(result1, constantReference)
        val data1 = loadM128iWord(moreBytes, i)
        val result2 = mmGf2p8affineEpi64Epi8<IdentityByte>(data1, identity1)
        assertEqM128i(result2, data1)
        val result3 = mmGf2p8affineEpi64Epi8<ConstantByte>(data1, constant1)
        assertEqM128i(result3, constantReference)
        val matrix = loadM128iWord(matrices, i)
        val vector = loadM128iWord(vectors, i)
        val reference = loadM128iWord(references, i)
        val result4 = mmGf2p8affineEpi64Epi8<IdentityByte>(vector, matrix)
        assertEqM128i(result4, reference)
    }
}

public fun testMmMaskzGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val matrix = loadM128iWord(matrices, i)
        val vector = loadM128iWord(vectors, i)
        val resultZero = mmMaskzGf2p8affineEpi64Epi8<ConstantByte>(0u, vector, matrix)
        assertEqM128i(resultZero, mmSetzeroSi128())
        val maskBytes: Mmask16 = 0x0FF0u
        val expectedResult = mmGf2p8affineEpi64Epi8<ConstantByte>(vector, matrix)
        val resultMasked = mmMaskzGf2p8affineEpi64Epi8<ConstantByte>(maskBytes, vector, matrix)
        val expectedMasked = mmBlendEpi32<MaskWords>(mmSetzeroSi128(), expectedResult)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMmMaskGf2p8affineEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val left = loadM128iWord(vectors, i)
        val right = loadM128iWord(matrices, i)
        val resultLeft = mmMaskGf2p8affineEpi64Epi8<ConstantByte>(left, 0u, left, right)
        assertEqM128i(resultLeft, left)
        val maskBytes: Mmask16 = 0x0FF0u
        val expectedResult = mmGf2p8affineEpi64Epi8<ConstantByte>(left, right)
        val resultMasked = mmMaskGf2p8affineEpi64Epi8<ConstantByte>(left, maskBytes, left, right)
        val expectedMasked = mmBlendEpi32<MaskWords>(left, expectedResult)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMm512Gf2p8affineinvEpi64Epi8() {
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
        val result = mm512Gf2p8affineinvEpi64Epi8<IdentityByte>(input, identity1)
        val remultiplied = mm512Gf2p8mulEpi8(result, input)
        assertEqM512i(remultiplied, reference)
    }
    // validate subsequent affine operation
    val (matrices, vectors, affineExpected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i1 in 0 until NUM_TEST_WORDS_512) {
        val vector = loadM512iWord(vectors, i1)
        val matrix = loadM512iWord(matrices, i1)
        val invVec = mm512Gf2p8affineinvEpi64Epi8<IdentityByte>(vector, identity1)
        val reference1 = mm512Gf2p8affineEpi64Epi8<ConstantByte>(invVec, matrix)
        val result1 = mm512Gf2p8affineinvEpi64Epi8<ConstantByte>(vector, matrix)
        assertEqM512i(result1, reference1)
    }
    val sboxMatrix = mm512Set1Epi64(AES_S_BOX_MATRIX)
    for (i2 in 0 until NUM_BYTES_WORDS_512) {
        val reference2 = loadM512iWord(AES_S_BOX, i2)
        val input1 = loadM512iWord(inputs, i2)
        val result2 = mm512Gf2p8affineinvEpi64Epi8<ConstantByte>(input1, sboxMatrix)
        assertEqM512i(result2, reference2)
    }
}

public fun testMm512MaskzGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_512) {
        val matrix = loadM512iWord(matrices, i)
        val vector = loadM512iWord(vectors, i)
        val resultZero = mm512MaskzGf2p8affineinvEpi64Epi8<ConstantByte>(0uL, vector, matrix)
        assertEqM512i(resultZero, mm512SetzeroSi512())
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u
        val expectedResult = mm512Gf2p8affineinvEpi64Epi8<ConstantByte>(vector, matrix)
        val resultMasked = mm512MaskzGf2p8affineinvEpi64Epi8<ConstantByte>(maskBytes, vector, matrix)
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
        val resultLeft = mm512MaskGf2p8affineinvEpi64Epi8<ConstantByte>(left, 0uL, left, right)
        assertEqM512i(resultLeft, left)
        val maskBytes: Mmask64 = 0x0F0F0F0FFFFF0000uL
        val maskWords: Mmask16 = 0x55F0u
        val expectedResult = mm512Gf2p8affineinvEpi64Epi8<ConstantByte>(left, right)
        val resultMasked = mm512MaskGf2p8affineinvEpi64Epi8<ConstantByte>(left, maskBytes, left, right)
        val expectedMasked = mm512MaskBlendEpi32(maskWords, left, expectedResult)
        assertEqM512i(resultMasked, expectedMasked)
    }
}

public fun testMm256Gf2p8affineinvEpi64Epi8() {
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
        val result = mm256Gf2p8affineinvEpi64Epi8<IdentityByte>(input, identity1)
        val remultiplied = mm256Gf2p8mulEpi8(result, input)
        assertEqM256i(remultiplied, reference)
    }
    // validate subsequent affine operation
    val (matrices, vectors, affineExpected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i1 in 0 until NUM_TEST_WORDS_256) {
        val vector = loadM256iWord(vectors, i1)
        val matrix = loadM256iWord(matrices, i1)
        val invVec = mm256Gf2p8affineinvEpi64Epi8<IdentityByte>(vector, identity1)
        val reference1 = mm256Gf2p8affineEpi64Epi8<ConstantByte>(invVec, matrix)
        val result1 = mm256Gf2p8affineinvEpi64Epi8<ConstantByte>(vector, matrix)
        assertEqM256i(result1, reference1)
    }
    val sboxMatrix = mm256Set1Epi64x(AES_S_BOX_MATRIX)
    for (i2 in 0 until NUM_BYTES_WORDS_256) {
        val reference2 = loadM256iWord(AES_S_BOX, i2)
        val input1 = loadM256iWord(inputs, i2)
        val result2 = mm256Gf2p8affineinvEpi64Epi8<ConstantByte>(input1, sboxMatrix)
        assertEqM256i(result2, reference2)
    }
}

public fun testMm256MaskzGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val matrix = loadM256iWord(matrices, i)
        val vector = loadM256iWord(vectors, i)
        val resultZero = mm256MaskzGf2p8affineinvEpi64Epi8<ConstantByte>(0u, vector, matrix)
        assertEqM256i(resultZero, mm256SetzeroSi256())
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineinvEpi64Epi8<ConstantByte>(vector, matrix)
        val resultMasked = mm256MaskzGf2p8affineinvEpi64Epi8<ConstantByte>(maskBytes, vector, matrix)
        val expectedMasked = mm256BlendEpi32<MaskWords>(mm256SetzeroSi256(), expectedResult)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMm256MaskGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_256) {
        val left = loadM256iWord(vectors, i)
        val right = loadM256iWord(matrices, i)
        val resultLeft = mm256MaskGf2p8affineinvEpi64Epi8<ConstantByte>(left, 0u, left, right)
        assertEqM256i(resultLeft, left)
        val maskBytes: Mmask32 = 0xFF0FF000u
        val expectedResult = mm256Gf2p8affineinvEpi64Epi8<ConstantByte>(left, right)
        val resultMasked = mm256MaskGf2p8affineinvEpi64Epi8<ConstantByte>(left, maskBytes, left, right)
        val expectedMasked = mm256BlendEpi32<MaskWords>(left, expectedResult)
        assertEqM256i(resultMasked, expectedMasked)
    }
}

public fun testMmGf2p8affineinvEpi64Epi8() {
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
        val result = mmGf2p8affineinvEpi64Epi8<IdentityByte>(input, identity1)
        val remultiplied = mmGf2p8mulEpi8(result, input)
        assertEqM128i(remultiplied, reference)
    }
    // validate subsequent affine operation
    val (matrices, vectors, affineExpected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i1 in 0 until NUM_TEST_WORDS_128) {
        val vector = loadM128iWord(vectors, i1)
        val matrix = loadM128iWord(matrices, i1)
        val invVec = mmGf2p8affineinvEpi64Epi8<IdentityByte>(vector, identity1)
        val reference1 = mmGf2p8affineEpi64Epi8<ConstantByte>(invVec, matrix)
        val result1 = mmGf2p8affineinvEpi64Epi8<ConstantByte>(vector, matrix)
        assertEqM128i(result1, reference1)
    }
    val sboxMatrix = mmSet1Epi64x(AES_S_BOX_MATRIX)
    for (i2 in 0 until NUM_BYTES_WORDS_128) {
        val reference2 = loadM128iWord(AES_S_BOX, i2)
        val input1 = loadM128iWord(inputs, i2)
        val result2 = mmGf2p8affineinvEpi64Epi8<ConstantByte>(input1, sboxMatrix)
        assertEqM128i(result2, reference2)
    }
}

public fun testMmMaskzGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val matrix = loadM128iWord(matrices, i)
        val vector = loadM128iWord(vectors, i)
        val resultZero = mmMaskzGf2p8affineinvEpi64Epi8<ConstantByte>(0u, vector, matrix)
        assertEqM128i(resultZero, mmSetzeroSi128())
        val maskBytes: Mmask16 = 0x0FF0u
        val expectedResult = mmGf2p8affineinvEpi64Epi8<ConstantByte>(vector, matrix)
        val resultMasked = mmMaskzGf2p8affineinvEpi64Epi8<ConstantByte>(maskBytes, vector, matrix)
        val expectedMasked = mmBlendEpi32<MaskWords>(mmSetzeroSi128(), expectedResult)
        assertEqM128i(resultMasked, expectedMasked)
    }
}

public fun testMmMaskGf2p8affineinvEpi64Epi8() {
    val CONSTANT_BYTE: Int = 0x63
    val (matrices, vectors, expected) = generateAffineMulTestData(CONSTANT_BYTE.toUByte())
    for (i in 0 until NUM_TEST_WORDS_128) {
        val left = loadM128iWord(vectors, i)
        val right = loadM128iWord(matrices, i)
        val resultLeft = mmMaskGf2p8affineinvEpi64Epi8<ConstantByte>(left, 0u, left, right)
        assertEqM128i(resultLeft, left)
        val maskBytes: Mmask16 = 0x0FF0u
        val expectedResult = mmGf2p8affineinvEpi64Epi8<ConstantByte>(left, right)
        val resultMasked = mmMaskGf2p8affineinvEpi64Epi8<ConstantByte>(left, maskBytes, left, right)
        val expectedMasked = mmBlendEpi32<MaskWords>(left, expectedResult)
        assertEqM128i(resultMasked, expectedMasked)
    }
}
