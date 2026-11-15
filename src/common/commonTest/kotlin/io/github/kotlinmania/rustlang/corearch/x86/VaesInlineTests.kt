// port-lint: tests x86/vaes.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M128i
import io.github.kotlinmania.rustlang.x86.M256i
import io.github.kotlinmania.rustlang.x86.M512i
import io.github.kotlinmania.rustlang.x86.mm256Broadcastsi128Si256
import io.github.kotlinmania.rustlang.x86.mm256Extracti128Si256
import io.github.kotlinmania.rustlang.x86.mm256SetEpi64x
import io.github.kotlinmania.rustlang.x86.mm512BroadcastI32x4
import io.github.kotlinmania.rustlang.x86.mm512Extracti32x4Epi32
import io.github.kotlinmania.rustlang.x86.mm512SetEpi64
import io.github.kotlinmania.rustlang.x86.mmAesdecSi128
import io.github.kotlinmania.rustlang.x86.mmAesdeclastSi128
import io.github.kotlinmania.rustlang.x86.mmAesencSi128
import io.github.kotlinmania.rustlang.x86.mmAesenclastSi128
import io.github.kotlinmania.rustlang.x86.mmSetEpi64x
import io.github.kotlinmania.rustlang.x86.mmSetzeroSi128

// Vectorized AES Instructions (VAES)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public fun interface HelperFor256VaesLinearCallback {
    public operator fun invoke(arg0: M128i, arg1: M128i): M128i
}

public fun interface HelperFor256VaesVectorizedCallback {
    public operator fun invoke(arg0: M256i, arg1: M256i): M256i
}

public fun interface HelperFor512VaesVectorizedCallback {
    public operator fun invoke(arg0: M512i, arg1: M512i): M512i
}

// the first parts of these tests are straight ports from the AES-NI tests
// the second parts directly compare the two, for inputs that are different across lanes
// and "more random" than the standard test vectors
// ideally we'd be using quickcheck here instead
public fun helperFor256Vaes(linear: HelperFor256VaesLinearCallback, vectorized: HelperFor256VaesVectorizedCallback) {
    val a = mm256SetEpi64x(0xDCB4DB3657BF0B7DuL.toLong(), 0x18DB0601068EDD9FL, 0xB76B908233200DC5uL.toLong(), 0xE478235FA8E22D5EuL.toLong())
    val k = mm256SetEpi64x(0x672F6F105A94CEA7L, 0x8298B8FFCA5F829CuL.toLong(), 0xA3927047B3FB61D8uL.toLong(), 0x978093862CDE7187uL.toLong())
    val aDecomp = MutableList(2) { mmSetzeroSi128() }
    aDecomp[0] = mm256Extracti128Si256(a, 0)
    aDecomp[1] = mm256Extracti128Si256(a, 1)
    val kDecomp = MutableList(2) { mmSetzeroSi128() }
    kDecomp[0] = mm256Extracti128Si256(k, 0)
    kDecomp[1] = mm256Extracti128Si256(k, 1)
    val r = vectorized(a, k)
    val eDecomp = MutableList(2) { mmSetzeroSi128() }
    for (i in 0 until 2) {
        eDecomp[i] = linear(aDecomp[i], kDecomp[i])
    }
    assertEqM128i(mm256Extracti128Si256(r, 0), eDecomp[0])
    assertEqM128i(mm256Extracti128Si256(r, 1), eDecomp[1])
}

public fun <T> setupStateKey(broadcast: (M128i) -> T): Pair<T, T> {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664949.aspx.
    val a = mmSetEpi64x(0x0123456789abcdefL, 0x8899aabbccddeeffuL.toLong())
    val k = mmSetEpi64x(0x1133557799bbddffL, 0x0022446688aacceeL)
    return Pair(broadcast(a), broadcast(k))
}

public fun setupStateKey256(): Pair<M256i, M256i> = setupStateKey(mm256Broadcastsi128Si256)

public fun setupStateKey512(): Pair<M512i, M512i> = setupStateKey(mm512BroadcastI32x4)

public fun testMm256AesdecEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664949.aspx.
    val (a, k) = setupStateKey256()
    val e = mmSetEpi64x(0x044e4f5176fec48fL, 0xb57ecfa381da39eeuL.toLong())
    val e1 = mm256Broadcastsi128Si256(e)
    val r = mm256AesdecEpi128(a, k)
    assertEqM256i(r, e1)
    helperFor256Vaes(mmAesdecSi128, mm256AesdecEpi128)
}

public fun testMm256AesdeclastEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714178.aspx.
    val (a, k) = setupStateKey256()
    val e = mmSetEpi64x(0x36cad57d9072bf9eL, 0xf210dd981fa4a493uL.toLong())
    val e1 = mm256Broadcastsi128Si256(e)
    val r = mm256AesdeclastEpi128(a, k)
    assertEqM256i(r, e1)
    helperFor256Vaes(mmAesdeclastSi128, mm256AesdeclastEpi128)
}

public fun testMm256AesencEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664810.aspx.
    // they are repeated appropriately
    val (a, k) = setupStateKey256()
    val e = mmSetEpi64x(0x16ab0e57dfc442edL, 0x28e4ee1884504333L)
    val e1 = mm256Broadcastsi128Si256(e)
    val r = mm256AesencEpi128(a, k)
    assertEqM256i(r, e1)
    helperFor256Vaes(mmAesencSi128, mm256AesencEpi128)
}

public fun testMm256AesenclastEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714136.aspx.
    val (a, k) = setupStateKey256()
    val e = mmSetEpi64x(0xb6dd7df25d7ab320uL.toLong(), 0x4b04f98cf4c860f8L)
    val e1 = mm256Broadcastsi128Si256(e)
    val r = mm256AesenclastEpi128(a, k)
    assertEqM256i(r, e1)
    helperFor256Vaes(mmAesenclastSi128, mm256AesenclastEpi128)
}

public fun helperFor512Vaes(linear: HelperFor256VaesLinearCallback, vectorized: HelperFor512VaesVectorizedCallback) {
    val a = mm512SetEpi64(0xDCB4DB3657BF0B7DuL.toLong(), 0x18DB0601068EDD9FL, 0xB76B908233200DC5uL.toLong(), 0xE478235FA8E22D5EuL.toLong(), 0xAB05CFFA2621154CuL.toLong(), 0x1171B47A186174C9L, 0x8C6B6C0E7595CEC9uL.toLong(), 0xBE3E7D4934E961BDuL.toLong())
    val k = mm512SetEpi64(0x672F6F105A94CEA7L, 0x8298B8FFCA5F829CuL.toLong(), 0xA3927047B3FB61D8uL.toLong(), 0x978093862CDE7187uL.toLong(), 0xB1927AB22F31D0ECuL.toLong(), 0xA9A5DA619BE4D7AFuL.toLong(), 0xCA2590F56884FDC6uL.toLong(), 0x19BE9F660038BDB5L)
    val aDecomp = MutableList(4) { mmSetzeroSi128() }
    aDecomp[0] = mm512Extracti32x4Epi32(a, 0)
    aDecomp[1] = mm512Extracti32x4Epi32(a, 1)
    aDecomp[2] = mm512Extracti32x4Epi32(a, 2)
    aDecomp[3] = mm512Extracti32x4Epi32(a, 3)
    val kDecomp = MutableList(4) { mmSetzeroSi128() }
    kDecomp[0] = mm512Extracti32x4Epi32(k, 0)
    kDecomp[1] = mm512Extracti32x4Epi32(k, 1)
    kDecomp[2] = mm512Extracti32x4Epi32(k, 2)
    kDecomp[3] = mm512Extracti32x4Epi32(k, 3)
    val r = vectorized(a, k)
    val eDecomp = MutableList(4) { mmSetzeroSi128() }
    for (i in 0 until 4) {
        eDecomp[i] = linear(aDecomp[i], kDecomp[i])
    }
    assertEqM128i(mm512Extracti32x4Epi32(r, 0), eDecomp[0])
    assertEqM128i(mm512Extracti32x4Epi32(r, 1), eDecomp[1])
    assertEqM128i(mm512Extracti32x4Epi32(r, 2), eDecomp[2])
    assertEqM128i(mm512Extracti32x4Epi32(r, 3), eDecomp[3])
}

public fun testMm512AesdecEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664949.aspx.
    val (a, k) = setupStateKey512()
    val e = mmSetEpi64x(0x044e4f5176fec48fL, 0xb57ecfa381da39eeuL.toLong())
    val e1 = mm512BroadcastI32x4(e)
    val r = mm512AesdecEpi128(a, k)
    assertEqM512i(r, e1)
    helperFor512Vaes(mmAesdecSi128, mm512AesdecEpi128)
}

public fun testMm512AesdeclastEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714178.aspx.
    val (a, k) = setupStateKey512()
    val e = mmSetEpi64x(0x36cad57d9072bf9eL, 0xf210dd981fa4a493uL.toLong())
    val e1 = mm512BroadcastI32x4(e)
    val r = mm512AesdeclastEpi128(a, k)
    assertEqM512i(r, e1)
    helperFor512Vaes(mmAesdeclastSi128, mm512AesdeclastEpi128)
}

public fun testMm512AesencEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc664810.aspx.
    val (a, k) = setupStateKey512()
    val e = mmSetEpi64x(0x16ab0e57dfc442edL, 0x28e4ee1884504333L)
    val e1 = mm512BroadcastI32x4(e)
    val r = mm512AesencEpi128(a, k)
    assertEqM512i(r, e1)
    helperFor512Vaes(mmAesencSi128, mm512AesencEpi128)
}

public fun testMm512AesenclastEpi128() {
    // Constants taken from https://msdn.microsoft.com/en-us/library/cc714136.aspx.
    val (a, k) = setupStateKey512()
    val e = mmSetEpi64x(0xb6dd7df25d7ab320uL.toLong(), 0x4b04f98cf4c860f8L)
    val e1 = mm512BroadcastI32x4(e)
    val r = mm512AesenclastEpi128(a, k)
    assertEqM512i(r, e1)
    helperFor512Vaes(mmAesenclastSi128, mm512AesenclastEpi128)
}
