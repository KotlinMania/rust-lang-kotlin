// port-lint: tests x86/vpclmulqdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Vectorized Carry-less Multiplication (VCLMUL)
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref] (p. 4-241).
//
// [intel64Ref]: http://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun interface Verify512HelperLinearCallback {
    public operator fun invoke(arg0: M128i, arg1: M128i): M128i
}

public fun interface Verify512HelperVectorizedCallback {
    public operator fun invoke(arg0: M512i, arg1: M512i): M512i
}

public fun interface Verify256HelperVectorizedCallback {
    public operator fun invoke(arg0: M256i, arg1: M256i): M256i
}

// this function tests one of the possible 4 instances
// with different inputs across lanes
public fun verify512Helper(linear: Verify512HelperLinearCallback, vectorized: Verify512HelperVectorizedCallback) {
    val a = mm512SetEpi64(0xDCB4DB3657BF0B7DuL.toLong(), 0x18DB0601068EDD9FL, 0xB76B908233200DC5uL.toLong(), 0xE478235FA8E22D5EuL.toLong(), 0xAB05CFFA2621154CuL.toLong(), 0x1171B47A186174C9L, 0x8C6B6C0E7595CEC9uL.toLong(), 0xBE3E7D4934E961BDuL.toLong())
    val b = mm512SetEpi64(0x672F6F105A94CEA7L, 0x8298B8FFCA5F829CuL.toLong(), 0xA3927047B3FB61D8uL.toLong(), 0x978093862CDE7187uL.toLong(), 0xB1927AB22F31D0ECuL.toLong(), 0xA9A5DA619BE4D7AFuL.toLong(), 0xCA2590F56884FDC6uL.toLong(), 0x19BE9F660038BDB5L)
    val aDecomp = MutableList(4) { mmSetzeroSi128() }
    aDecomp[3] = mm512Extracti32x4Epi32(a, 3)
    aDecomp[2] = mm512Extracti32x4Epi32(a, 2)
    aDecomp[1] = mm512Extracti32x4Epi32(a, 1)
    aDecomp[0] = mm512Extracti32x4Epi32(a, 0)
    val bDecomp = MutableList(4) { mmSetzeroSi128() }
    bDecomp[3] = mm512Extracti32x4Epi32(b, 3)
    bDecomp[2] = mm512Extracti32x4Epi32(b, 2)
    bDecomp[1] = mm512Extracti32x4Epi32(b, 1)
    bDecomp[0] = mm512Extracti32x4Epi32(b, 0)
    val r = vectorized(a, b)
    val eDecomp = MutableList(4) { mmSetzeroSi128() }
    for (i in 0 until 4) {
        eDecomp[i] = linear(aDecomp[i], bDecomp[i])
    }
    assertEqM128i(mm512Extracti32x4Epi32(r, 3), eDecomp[3])
    assertEqM128i(mm512Extracti32x4Epi32(r, 2), eDecomp[2])
    assertEqM128i(mm512Extracti32x4Epi32(r, 1), eDecomp[1])
    assertEqM128i(mm512Extracti32x4Epi32(r, 0), eDecomp[0])
}

// this function tests one of the possible 4 instances
// with different inputs across lanes for the VL version
public fun verify256Helper(linear: Verify512HelperLinearCallback, vectorized: Verify256HelperVectorizedCallback) {
    val a = mm512SetEpi64(0xDCB4DB3657BF0B7DuL.toLong(), 0x18DB0601068EDD9FL, 0xB76B908233200DC5uL.toLong(), 0xE478235FA8E22D5EuL.toLong(), 0xAB05CFFA2621154CuL.toLong(), 0x1171B47A186174C9L, 0x8C6B6C0E7595CEC9uL.toLong(), 0xBE3E7D4934E961BDuL.toLong())
    val b = mm512SetEpi64(0x672F6F105A94CEA7L, 0x8298B8FFCA5F829CuL.toLong(), 0xA3927047B3FB61D8uL.toLong(), 0x978093862CDE7187uL.toLong(), 0xB1927AB22F31D0ECuL.toLong(), 0xA9A5DA619BE4D7AFuL.toLong(), 0xCA2590F56884FDC6uL.toLong(), 0x19BE9F660038BDB5L)
    val aDecomp = MutableList(2) { mmSetzeroSi128() }
    aDecomp[1] = mm512Extracti32x4Epi32(a, 1)
    aDecomp[0] = mm512Extracti32x4Epi32(a, 0)
    val bDecomp = MutableList(2) { mmSetzeroSi128() }
    bDecomp[1] = mm512Extracti32x4Epi32(b, 1)
    bDecomp[0] = mm512Extracti32x4Epi32(b, 0)
    val r = vectorized(mm512Extracti64x4Epi64(a, 0), mm512Extracti64x4Epi64(b, 0))
    val eDecomp = MutableList(2) { mmSetzeroSi128() }
    for (i in 0 until 2) {
        eDecomp[i] = linear(aDecomp[i], bDecomp[i])
    }
    assertEqM128i(mm256Extracti128Si256(r, 1), eDecomp[1])
    assertEqM128i(mm256Extracti128Si256(r, 0), eDecomp[0])
}

public fun testMm512Clmulepi64Epi128() {
    val a = mmSetEpi64x(0x7b5b546573745665L, 0x63746f725d53475dL)
    val a1 = mm512BroadcastI32x4(a)
    val b = mmSetEpi64x(0x4869285368617929L, 0x5b477565726f6e5dL)
    val b1 = mm512BroadcastI32x4(b)
    val r00 = mmSetEpi64x(0x1d4d84c85c3440c0L, 0x929633d5d36f0451uL.toLong())
    val r001 = mm512BroadcastI32x4(r00)
    val r01 = mmSetEpi64x(0x1bd17c8d556ab5a1L, 0x7fa540ac2a281315L)
    val r011 = mm512BroadcastI32x4(r01)
    val r10 = mmSetEpi64x(0x1a2bf6db3a30862fL, 0xbabf262df4b7d5c9uL.toLong())
    val r101 = mm512BroadcastI32x4(r10)
    val r11 = mmSetEpi64x(0x1d1e1f2c592e7c45L, 0xd66ee03e410fd4eduL.toLong())
    val r111 = mm512BroadcastI32x4(r11)
    assertEqM512i(mm512Clmulepi64Epi128(a1, b1, 0x00), r001)
    assertEqM512i(mm512Clmulepi64Epi128(a1, b1, 0x10), r011)
    assertEqM512i(mm512Clmulepi64Epi128(a1, b1, 0x01), r101)
    assertEqM512i(mm512Clmulepi64Epi128(a1, b1, 0x11), r111)
    val a0 = mmSetEpi64x(0x0000000000000000L, 0x8000000000000000uL.toLong())
    val a01 = mm512BroadcastI32x4(a0)
    val r = mmSetEpi64x(0x4000000000000000L, 0x0000000000000000L)
    val r1 = mm512BroadcastI32x4(r)
    assertEqM512i(mm512Clmulepi64Epi128(a01, a01, 0x00), r1)
    verify512Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x00) }, { a2, b2 -> mm512Clmulepi64Epi128(a2, b2, 0x00) })
    verify512Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x01) }, { a2, b2 -> mm512Clmulepi64Epi128(a2, b2, 0x01) })
    verify512Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x10) }, { a2, b2 -> mm512Clmulepi64Epi128(a2, b2, 0x10) })
    verify512Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x11) }, { a2, b2 -> mm512Clmulepi64Epi128(a2, b2, 0x11) })
}

public fun testMm256Clmulepi64Epi128() {
    val a = mmSetEpi64x(0x7b5b546573745665L, 0x63746f725d53475dL)
    val a1 = mm256Broadcastsi128Si256(a)
    val b = mmSetEpi64x(0x4869285368617929L, 0x5b477565726f6e5dL)
    val b1 = mm256Broadcastsi128Si256(b)
    val r00 = mmSetEpi64x(0x1d4d84c85c3440c0L, 0x929633d5d36f0451uL.toLong())
    val r001 = mm256Broadcastsi128Si256(r00)
    val r01 = mmSetEpi64x(0x1bd17c8d556ab5a1L, 0x7fa540ac2a281315L)
    val r011 = mm256Broadcastsi128Si256(r01)
    val r10 = mmSetEpi64x(0x1a2bf6db3a30862fL, 0xbabf262df4b7d5c9uL.toLong())
    val r101 = mm256Broadcastsi128Si256(r10)
    val r11 = mmSetEpi64x(0x1d1e1f2c592e7c45L, 0xd66ee03e410fd4eduL.toLong())
    val r111 = mm256Broadcastsi128Si256(r11)
    assertEqM256i(mm256Clmulepi64Epi128(a1, b1, 0x00), r001)
    assertEqM256i(mm256Clmulepi64Epi128(a1, b1, 0x10), r011)
    assertEqM256i(mm256Clmulepi64Epi128(a1, b1, 0x01), r101)
    assertEqM256i(mm256Clmulepi64Epi128(a1, b1, 0x11), r111)
    val a0 = mmSetEpi64x(0x0000000000000000L, 0x8000000000000000uL.toLong())
    val a01 = mm256Broadcastsi128Si256(a0)
    val r = mmSetEpi64x(0x4000000000000000L, 0x0000000000000000L)
    val r1 = mm256Broadcastsi128Si256(r)
    assertEqM256i(mm256Clmulepi64Epi128(a01, a01, 0x00), r1)
    verify256Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x00) }, { a2, b2 -> mm256Clmulepi64Epi128(a2, b2, 0x00) })
    verify256Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x01) }, { a2, b2 -> mm256Clmulepi64Epi128(a2, b2, 0x01) })
    verify256Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x10) }, { a2, b2 -> mm256Clmulepi64Epi128(a2, b2, 0x10) })
    verify256Helper({ a2, b2 -> mmClmulepi64Si128(a2, b2, 0x11) }, { a2, b2 -> mm256Clmulepi64Epi128(a2, b2, 0x11) })
}
