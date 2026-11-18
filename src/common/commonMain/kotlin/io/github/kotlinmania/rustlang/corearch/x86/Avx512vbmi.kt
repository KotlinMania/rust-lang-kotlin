// port-lint: source x86/avx512vbmi.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// should be vpermi2b

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_permutex2var_epi8&expand=4262)
 */
public fun mm512Permutex2varEpi8(a: M512i, idx: M512i, b: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpermi2b(a.asI8x64(), idx.asI8x64(), b.asI8x64()), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_permutex2var_epi8&expand=4259)
 */
public fun mm512MaskPermutex2varEpi8(a: M512i, k: Mmask64, idx: M512i, b: M512i): M512i =
    run {
        val permute = mm512Permutex2varEpi8(a, idx, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = a.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

// should be vpermi2b

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_permutex2var_epi8&expand=4261)
 */
public fun mm512MaskzPermutex2varEpi8(k: Mmask64, a: M512i, idx: M512i, b: M512i): M512i =
    run {
        val permute = mm512Permutex2varEpi8(a, idx, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask2_permutex2var_epi8&expand=4260)
 */
public fun mm512Mask2Permutex2varEpi8(a: M512i, idx: M512i, k: Mmask64, b: M512i): M512i =
    run {
        val permute = mm512Permutex2varEpi8(a, idx, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = idx.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

// should be vpermi2b

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutex2var_epi8&expand=4258)
 */
public fun mm256Permutex2varEpi8(a: M256i, idx: M256i, b: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpermi2b256(a.asI8x32(), idx.asI8x32(), b.asI8x32()), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_permutex2var_epi8&expand=4255)
 */
public fun mm256MaskPermutex2varEpi8(a: M256i, k: Mmask32, idx: M256i, b: M256i): M256i =
    run {
        val permute = mm256Permutex2varEpi8(a, idx, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = a.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

// should be vpermi2b

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_permutex2var_epi8&expand=4257)
 */
public fun mm256MaskzPermutex2varEpi8(k: Mmask32, a: M256i, idx: M256i, b: M256i): M256i =
    run {
        val permute = mm256Permutex2varEpi8(a, idx, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask2_permutex2var_epi8&expand=4256)
 */
public fun mm256Mask2Permutex2varEpi8(a: M256i, idx: M256i, k: Mmask32, b: M256i): M256i =
    run {
        val permute = mm256Permutex2varEpi8(a, idx, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = idx.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

// should be vpermi2b

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permutex2var_epi8&expand=4254)
 */
public fun mmPermutex2varEpi8(a: M128i, idx: M128i, b: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpermi2b128(a.asI8x16(), idx.asI8x16(), b.asI8x16()), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_permutex2var_epi8&expand=4251)
 */
public fun mmMaskPermutex2varEpi8(a: M128i, k: Mmask16, idx: M128i, b: M128i): M128i =
    run {
        val permute = mmPermutex2varEpi8(a, idx, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = a.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

// should be vpermi2b

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_permutex2var_epi8&expand=4253)
 */
public fun mmMaskzPermutex2varEpi8(k: Mmask16, a: M128i, idx: M128i, b: M128i): M128i =
    run {
        val permute = mmPermutex2varEpi8(a, idx, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 8-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask2_permutex2var_epi8&expand=4252)
 */
public fun mmMask2Permutex2varEpi8(a: M128i, idx: M128i, k: Mmask16, b: M128i): M128i =
    run {
        val permute = mmPermutex2varEpi8(a, idx, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = idx.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_permutexvar_epi8&expand=4316)
 */
public fun mm512PermutexvarEpi8(idx: M512i, a: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpermb(a.asI8x64(), idx.asI8x64()), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_permutexvar_epi8&expand=4314)
 */
public fun mm512MaskPermutexvarEpi8(src: M512i, k: Mmask64, idx: M512i, a: M512i): M512i =
    run {
        val permute = mm512PermutexvarEpi8(idx, a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_permutexvar_epi8&expand=4315)
 */
public fun mm512MaskzPermutexvarEpi8(k: Mmask64, idx: M512i, a: M512i): M512i =
    run {
        val permute = mm512PermutexvarEpi8(idx, a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutexvar_epi8&expand=4313)
 */
public fun mm256PermutexvarEpi8(idx: M256i, a: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpermb256(a.asI8x32(), idx.asI8x32()), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_permutexvar_epi8&expand=4311)
 */
public fun mm256MaskPermutexvarEpi8(src: M256i, k: Mmask32, idx: M256i, a: M256i): M256i =
    run {
        val permute = mm256PermutexvarEpi8(idx, a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_permutexvar_epi8&expand=4312)
 */
public fun mm256MaskzPermutexvarEpi8(k: Mmask32, idx: M256i, a: M256i): M256i =
    run {
        val permute = mm256PermutexvarEpi8(idx, a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permutexvar_epi8&expand=4310)
 */
public fun mmPermutexvarEpi8(idx: M128i, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpermb128(a.asI8x16(), idx.asI8x16()), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_permutexvar_epi8&expand=4308)
 */
public fun mmMaskPermutexvarEpi8(src: M128i, k: Mmask16, idx: M128i, a: M128i): M128i =
    run {
        val permute = mmPermutexvarEpi8(idx, a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 8-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_permutexvar_epi8&expand=4309)
 */
public fun mmMaskzPermutexvarEpi8(k: Mmask16, idx: M128i, a: M128i): M128i =
    run {
        val permute = mmPermutexvarEpi8(idx, a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_multishift_epi64_epi8&expand=4026)
 */
public fun mm512MultishiftEpi64Epi8(a: M512i, b: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpmultishiftqb(a.asI8x64(), b.asI8x64()), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_multishift_epi64_epi8&expand=4024)
 */
public fun mm512MaskMultishiftEpi64Epi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val multishift = mm512MultishiftEpi64Epi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = multishift; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_multishift_epi64_epi8&expand=4025)
 */
public fun mm512MaskzMultishiftEpi64Epi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val multishift = mm512MultishiftEpi64Epi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = multishift; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_multishift_epi64_epi8&expand=4023)
 */
public fun mm256MultishiftEpi64Epi8(a: M256i, b: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpmultishiftqb256(a.asI8x32(), b.asI8x32()), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_multishift_epi64_epi8&expand=4021)
 */
public fun mm256MaskMultishiftEpi64Epi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val multishift = mm256MultishiftEpi64Epi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = multishift; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_multishift_epi64_epi8&expand=4022)
 */
public fun mm256MaskzMultishiftEpi64Epi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val multishift = mm256MultishiftEpi64Epi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = multishift; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/IntrinsicsGuide/#text=_mm_multishift_epi64_epi8&expand=4020)
 */
public fun mmMultishiftEpi64Epi8(a: M128i, b: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpmultishiftqb128(a.asI8x16(), b.asI8x16()), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_multishift_epi64_epi8&expand=4018)
 */
public fun mmMaskMultishiftEpi64Epi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val multishift = mmMultishiftEpi64Epi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = multishift; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * For each 64-bit element in b, select 8 unaligned bytes using a byte-granular shift control within the corresponding 64-bit element of a, and store the 8 assembled bytes to the corresponding 64-bit element of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_multishift_epi64_epi8&expand=4019)
 */
public fun mmMaskzMultishiftEpi64Epi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val multishift = mmMultishiftEpi64Epi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = multishift; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

public expect fun vpermi2b(a: I8x64, idx: I8x64, b: I8x64): I8x64

public expect fun vpermi2b256(a: I8x32, idx: I8x32, b: I8x32): I8x32

public expect fun vpermi2b128(a: I8x16, idx: I8x16, b: I8x16): I8x16

public expect fun vpermb(a: I8x64, idx: I8x64): I8x64

public expect fun vpermb256(a: I8x32, idx: I8x32): I8x32

public expect fun vpermb128(a: I8x16, idx: I8x16): I8x16

public expect fun vpmultishiftqb(a: I8x64, b: I8x64): I8x64

public expect fun vpmultishiftqb256(a: I8x32, b: I8x32): I8x32

public expect fun vpmultishiftqb128(a: I8x16, b: I8x16): I8x16
