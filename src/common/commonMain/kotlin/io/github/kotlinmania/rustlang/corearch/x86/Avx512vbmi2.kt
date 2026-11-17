// port-lint: source x86/avx512vbmi2.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

/**
 * Load contiguous active 16-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_expandloadu_epi16)
 */
public fun mm512MaskExpandloaduEpi16(src: M512i, k: Mmask32, memAddr: ConstPtr): M512i = transmuteWithLayouts<I16x32, M512i>(expandloadw512(memAddr, src.asI16x32(), k), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Load contiguous active 16-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_expandloadu_epi16)
 */
public fun mm512MaskzExpandloaduEpi16(k: Mmask32, memAddr: ConstPtr): M512i = mm512MaskExpandloaduEpi16(mm512SetzeroSi512(), k, memAddr)

/**
 * Load contiguous active 16-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_expandloadu_epi16)
 */
public fun mm256MaskExpandloaduEpi16(src: M256i, k: Mmask16, memAddr: ConstPtr): M256i = transmuteWithLayouts<I16x16, M256i>(expandloadw256(memAddr, src.asI16x16(), k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Load contiguous active 16-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_expandloadu_epi16)
 */
public fun mm256MaskzExpandloaduEpi16(k: Mmask16, memAddr: ConstPtr): M256i = mm256MaskExpandloaduEpi16(mm256SetzeroSi256(), k, memAddr)

/**
 * Load contiguous active 16-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_expandloadu_epi16)
 */
public fun mmMaskExpandloaduEpi16(src: M128i, k: Mmask8, memAddr: ConstPtr): M128i = transmuteWithLayouts<I16x8, M128i>(expandloadw128(memAddr, src.asI16x8(), k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Load contiguous active 16-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_expandloadu_epi16)
 */
public fun mmMaskzExpandloaduEpi16(k: Mmask8, memAddr: ConstPtr): M128i = mmMaskExpandloaduEpi16(mmSetzeroSi128(), k, memAddr)

/**
 * Load contiguous active 8-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_expandloadu_epi8)
 */
public fun mm512MaskExpandloaduEpi8(src: M512i, k: Mmask64, memAddr: ConstPtr): M512i = transmuteWithLayouts<I8x64, M512i>(expandloadb512(memAddr, src.asI8x64(), k), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Load contiguous active 8-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_expandloadu_epi8)
 */
public fun mm512MaskzExpandloaduEpi8(k: Mmask64, memAddr: ConstPtr): M512i = mm512MaskExpandloaduEpi8(mm512SetzeroSi512(), k, memAddr)

/**
 * Load contiguous active 8-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_expandloadu_epi8)
 */
public fun mm256MaskExpandloaduEpi8(src: M256i, k: Mmask32, memAddr: ConstPtr): M256i = transmuteWithLayouts<I8x32, M256i>(expandloadb256(memAddr, src.asI8x32(), k), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Load contiguous active 8-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_expandloadu_epi8)
 */
public fun mm256MaskzExpandloaduEpi8(k: Mmask32, memAddr: ConstPtr): M256i = mm256MaskExpandloaduEpi8(mm256SetzeroSi256(), k, memAddr)

/**
 * Load contiguous active 8-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_expandloadu_epi8)
 */
public fun mmMaskExpandloaduEpi8(src: M128i, k: Mmask16, memAddr: ConstPtr): M128i = transmuteWithLayouts<I8x16, M128i>(expandloadb128(memAddr, src.asI8x16(), k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Load contiguous active 8-bit integers from unaligned memory at memAddr (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_expandloadu_epi8)
 */
public fun mmMaskzExpandloaduEpi8(k: Mmask16, memAddr: ConstPtr): M128i = mmMaskExpandloaduEpi8(mmSetzeroSi128(), k, memAddr)

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_compressstoreu_epi16)
 */
public fun mm512MaskCompressstoreuEpi16(baseAddr: MutPtr, k: Mmask32, a: M512i) {
    vcompressstorew(baseAddr, a.asI16x32(), k)
}

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_compressstoreu_epi16)
 */
public fun mm256MaskCompressstoreuEpi16(baseAddr: MutPtr, k: Mmask16, a: M256i) {
    vcompressstorew256(baseAddr, a.asI16x16(), k)
}

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_compressstoreu_epi16)
 */
public fun mmMaskCompressstoreuEpi16(baseAddr: MutPtr, k: Mmask8, a: M128i) {
    vcompressstorew128(baseAddr, a.asI16x8(), k)
}

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_compressstoreu_epi8)
 */
public fun mm512MaskCompressstoreuEpi8(baseAddr: MutPtr, k: Mmask64, a: M512i) {
    vcompressstoreb(baseAddr, a.asI8x64(), k)
}

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_compressstoreu_epi8)
 */
public fun mm256MaskCompressstoreuEpi8(baseAddr: MutPtr, k: Mmask32, a: M256i) {
    vcompressstoreb256(baseAddr, a.asI8x32(), k)
}

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_compressstoreu_epi8)
 */
public fun mmMaskCompressstoreuEpi8(baseAddr: MutPtr, k: Mmask16, a: M128i) {
    vcompressstoreb128(baseAddr, a.asI8x16(), k)
}

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in writemask k) to dst, and pass through the remaining elements from src.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_compress_epi16&expand=1192)
 */
public fun mm512MaskCompressEpi16(src: M512i, k: Mmask32, a: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpcompressw(a.asI16x32(), src.asI16x32(), k), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in zeromask k) to dst, and set the remaining elements to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_compress_epi16&expand=1193)
 */
public fun mm512MaskzCompressEpi16(k: Mmask32, a: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpcompressw(a.asI16x32(), i16x32.ZERO, k), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in writemask k) to dst, and pass through the remaining elements from src.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_compress_epi16&expand=1190)
 */
public fun mm256MaskCompressEpi16(src: M256i, k: Mmask16, a: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpcompressw256(a.asI16x16(), src.asI16x16(), k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in zeromask k) to dst, and set the remaining elements to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_compress_epi16&expand=1191)
 */
public fun mm256MaskzCompressEpi16(k: Mmask16, a: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpcompressw256(a.asI16x16(), i16x16.ZERO, k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in writemask k) to dst, and pass through the remaining elements from src.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_compress_epi16&expand=1188)
 */
public fun mmMaskCompressEpi16(src: M128i, k: Mmask8, a: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpcompressw128(a.asI16x8(), src.asI16x8(), k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Contiguously store the active 16-bit integers in a (those with their respective bit set in zeromask k) to dst, and set the remaining elements to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_compress_epi16&expand=1189)
 */
public fun mmMaskzCompressEpi16(k: Mmask8, a: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpcompressw128(a.asI16x8(), i16x8.ZERO, k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in writemask k) to dst, and pass through the remaining elements from src.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_compress_epi8&expand=1210)
 */
public fun mm512MaskCompressEpi8(src: M512i, k: Mmask64, a: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpcompressb(a.asI8x64(), src.asI8x64(), k), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in zeromask k) to dst, and set the remaining elements to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_compress_epi8&expand=1211)
 */
public fun mm512MaskzCompressEpi8(k: Mmask64, a: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpcompressb(a.asI8x64(), i8x64.ZERO, k), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in writemask k) to dst, and pass through the remaining elements from src.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_compress_epi8&expand=1208)
 */
public fun mm256MaskCompressEpi8(src: M256i, k: Mmask32, a: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpcompressb256(a.asI8x32(), src.asI8x32(), k), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in zeromask k) to dst, and set the remaining elements to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_compress_epi8&expand=1209)
 */
public fun mm256MaskzCompressEpi8(k: Mmask32, a: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpcompressb256(a.asI8x32(), i8x32.ZERO, k), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in writemask k) to dst, and pass through the remaining elements from src.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_compress_epi8&expand=1206)
 */
public fun mmMaskCompressEpi8(src: M128i, k: Mmask16, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpcompressb128(a.asI8x16(), src.asI8x16(), k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Contiguously store the active 8-bit integers in a (those with their respective bit set in zeromask k) to dst, and set the remaining elements to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_compress_epi8&expand=1207)
 */
public fun mmMaskzCompressEpi8(k: Mmask16, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpcompressb128(a.asI8x16(), i8x16.ZERO, k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Load contiguous active 16-bit integers from a (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_expand_epi16&expand=2310)
 */
public fun mm512MaskExpandEpi16(src: M512i, k: Mmask32, a: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpexpandw(a.asI16x32(), src.asI16x32(), k), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Load contiguous active 16-bit integers from a (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_expand_epi16&expand=2311)
 */
public fun mm512MaskzExpandEpi16(k: Mmask32, a: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpexpandw(a.asI16x32(), i16x32.ZERO, k), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Load contiguous active 16-bit integers from a (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_expand_epi16&expand=2308)
 */
public fun mm256MaskExpandEpi16(src: M256i, k: Mmask16, a: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpexpandw256(a.asI16x16(), src.asI16x16(), k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Load contiguous active 16-bit integers from a (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_expand_epi16&expand=2309)
 */
public fun mm256MaskzExpandEpi16(k: Mmask16, a: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpexpandw256(a.asI16x16(), i16x16.ZERO, k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Load contiguous active 16-bit integers from a (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_expand_epi16&expand=2306)
 */
public fun mmMaskExpandEpi16(src: M128i, k: Mmask8, a: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpexpandw128(a.asI16x8(), src.asI16x8(), k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Load contiguous active 16-bit integers from a (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_expand_epi16&expand=2307)
 */
public fun mmMaskzExpandEpi16(k: Mmask8, a: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpexpandw128(a.asI16x8(), i16x8.ZERO, k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Load contiguous active 8-bit integers from a (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_expand_epi8&expand=2328)
 */
public fun mm512MaskExpandEpi8(src: M512i, k: Mmask64, a: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpexpandb(a.asI8x64(), src.asI8x64(), k), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Load contiguous active 8-bit integers from a (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_expand_epi8&expand=2329)
 */
public fun mm512MaskzExpandEpi8(k: Mmask64, a: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpexpandb(a.asI8x64(), i8x64.ZERO, k), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Load contiguous active 8-bit integers from a (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_expand_epi8&expand=2326)
 */
public fun mm256MaskExpandEpi8(src: M256i, k: Mmask32, a: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpexpandb256(a.asI8x32(), src.asI8x32(), k), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Load contiguous active 8-bit integers from a (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_expand_epi8&expand=2327)
 */
public fun mm256MaskzExpandEpi8(k: Mmask32, a: M256i): M256i = transmuteWithLayouts<I8x32, M256i>(vpexpandb256(a.asI8x32(), i8x32.ZERO, k), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Load contiguous active 8-bit integers from a (those with their respective bit set in mask k), and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_expand_epi8&expand=2324)
 */
public fun mmMaskExpandEpi8(src: M128i, k: Mmask16, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpexpandb128(a.asI8x16(), src.asI8x16(), k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Load contiguous active 8-bit integers from a (those with their respective bit set in mask k), and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_expand_epi8&expand=2325)
 */
public fun mmMaskzExpandEpi8(k: Mmask16, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpexpandb128(a.asI8x16(), i8x16.ZERO, k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shldv_epi64&expand=5087)
 */
public fun mm512ShldvEpi64(a: M512i, b: M512i, c: M512i): M512i = transmute(run { val _simdArg0 = a.asI64x8(); val _simdArg1 = b.asI64x8(); val _simdArg2 = run { val _simdArg0 = c.asI64x8(); val _simdArg1 = i64x8.splat(63); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shldv_epi64&expand=5085)
 */
public fun mm512MaskShldvEpi64(a: M512i, k: Mmask8, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShldvEpi64(a, b, c).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI64x8(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shldv_epi64&expand=5086)
 */
public fun mm512MaskzShldvEpi64(k: Mmask8, a: M512i, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShldvEpi64(a, b, c).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shldv_epi64&expand=5084)
 */
public fun mm256ShldvEpi64(a: M256i, b: M256i, c: M256i): M256i = transmute(run { val _simdArg0 = a.asI64x4(); val _simdArg1 = b.asI64x4(); val _simdArg2 = run { val _simdArg0 = c.asI64x4(); val _simdArg1 = i64x4.splat(63); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shldv_epi64&expand=5082)
 */
public fun mm256MaskShldvEpi64(a: M256i, k: Mmask8, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShldvEpi64(a, b, c).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI64x4(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shldv_epi64&expand=5083)
 */
public fun mm256MaskzShldvEpi64(k: Mmask8, a: M256i, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShldvEpi64(a, b, c).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shldv_epi64&expand=5081)
 */
public fun mmShldvEpi64(a: M128i, b: M128i, c: M128i): M128i = transmute(run { val _simdArg0 = a.asI64x2(); val _simdArg1 = b.asI64x2(); val _simdArg2 = run { val _simdArg0 = c.asI64x2(); val _simdArg1 = i64x2.splat(63); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shldv_epi64&expand=5079)
 */
public fun mmMaskShldvEpi64(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShldvEpi64(a, b, c).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI64x2(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shldv_epi64&expand=5080)
 */
public fun mmMaskzShldvEpi64(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShldvEpi64(a, b, c).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shldv_epi32&expand=5078)
 */
public fun mm512ShldvEpi32(a: M512i, b: M512i, c: M512i): M512i = transmute(run { val _simdArg0 = a.asI32x16(); val _simdArg1 = b.asI32x16(); val _simdArg2 = run { val _simdArg0 = c.asI32x16(); val _simdArg1 = i32x16.splat(31); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shldv_epi32&expand=5076)
 */
public fun mm512MaskShldvEpi32(a: M512i, k: Mmask16, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShldvEpi32(a, b, c).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI32x16(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shldv_epi32&expand=5077)
 */
public fun mm512MaskzShldvEpi32(k: Mmask16, a: M512i, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShldvEpi32(a, b, c).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shldv_epi32&expand=5075)
 */
public fun mm256ShldvEpi32(a: M256i, b: M256i, c: M256i): M256i = transmute(run { val _simdArg0 = a.asI32x8(); val _simdArg1 = b.asI32x8(); val _simdArg2 = run { val _simdArg0 = c.asI32x8(); val _simdArg1 = i32x8.splat(31); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shldv_epi32&expand=5073)
 */
public fun mm256MaskShldvEpi32(a: M256i, k: Mmask8, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShldvEpi32(a, b, c).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI32x8(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shldv_epi32&expand=5074)
 */
public fun mm256MaskzShldvEpi32(k: Mmask8, a: M256i, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShldvEpi32(a, b, c).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shldv_epi32&expand=5072)
 */
public fun mmShldvEpi32(a: M128i, b: M128i, c: M128i): M128i = transmute(run { val _simdArg0 = a.asI32x4(); val _simdArg1 = b.asI32x4(); val _simdArg2 = run { val _simdArg0 = c.asI32x4(); val _simdArg1 = i32x4.splat(31); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shldv_epi32&expand=5070)
 */
public fun mmMaskShldvEpi32(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShldvEpi32(a, b, c).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI32x4(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shldv_epi32&expand=5071)
 */
public fun mmMaskzShldvEpi32(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShldvEpi32(a, b, c).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shldv_epi16&expand=5069)
 */
public fun mm512ShldvEpi16(a: M512i, b: M512i, c: M512i): M512i = transmute(run { val _simdArg0 = a.asI16x32(); val _simdArg1 = b.asI16x32(); val _simdArg2 = run { val _simdArg0 = c.asI16x32(); val _simdArg1 = i16x32.splat(15); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shldv_epi16&expand=5067)
 */
public fun mm512MaskShldvEpi16(a: M512i, k: Mmask32, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShldvEpi16(a, b, c).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI16x32(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shldv_epi16&expand=5068)
 */
public fun mm512MaskzShldvEpi16(k: Mmask32, a: M512i, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShldvEpi16(a, b, c).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x32.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shldv_epi16&expand=5066)
 */
public fun mm256ShldvEpi16(a: M256i, b: M256i, c: M256i): M256i = transmute(run { val _simdArg0 = a.asI16x16(); val _simdArg1 = b.asI16x16(); val _simdArg2 = run { val _simdArg0 = c.asI16x16(); val _simdArg1 = i16x16.splat(15); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shldv_epi16&expand=5064)
 */
public fun mm256MaskShldvEpi16(a: M256i, k: Mmask16, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShldvEpi16(a, b, c).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI16x16(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shldv_epi16&expand=5065)
 */
public fun mm256MaskzShldvEpi16(k: Mmask16, a: M256i, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShldvEpi16(a, b, c).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x16.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shldv_epi16&expand=5063)
 */
public fun mmShldvEpi16(a: M128i, b: M128i, c: M128i): M128i = transmute(run { val _simdArg0 = a.asI16x8(); val _simdArg1 = b.asI16x8(); val _simdArg2 = run { val _simdArg0 = c.asI16x8(); val _simdArg1 = i16x8.splat(15); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shldv_epi16&expand=5061)
 */
public fun mmMaskShldvEpi16(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShldvEpi16(a, b, c).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI16x8(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by the amount specified in the corresponding element of c, and store the upper 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shldv_epi16&expand=5062)
 */
public fun mmMaskzShldvEpi16(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShldvEpi16(a, b, c).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shrdv_epi64&expand=5141)
 */
public fun mm512ShrdvEpi64(a: M512i, b: M512i, c: M512i): M512i = transmute(run { val _simdArg0 = b.asI64x8(); val _simdArg1 = a.asI64x8(); val _simdArg2 = run { val _simdArg0 = c.asI64x8(); val _simdArg1 = i64x8.splat(63); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shrdv_epi64&expand=5139)
 */
public fun mm512MaskShrdvEpi64(a: M512i, k: Mmask8, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShrdvEpi64(a, b, c).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI64x8(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shrdv_epi64&expand=5140)
 */
public fun mm512MaskzShrdvEpi64(k: Mmask8, a: M512i, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShrdvEpi64(a, b, c).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shrdv_epi64&expand=5138)
 */
public fun mm256ShrdvEpi64(a: M256i, b: M256i, c: M256i): M256i = transmute(run { val _simdArg0 = b.asI64x4(); val _simdArg1 = a.asI64x4(); val _simdArg2 = run { val _simdArg0 = c.asI64x4(); val _simdArg1 = i64x4.splat(63); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shrdv_epi64&expand=5136)
 */
public fun mm256MaskShrdvEpi64(a: M256i, k: Mmask8, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShrdvEpi64(a, b, c).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI64x4(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shrdv_epi64&expand=5137)
 */
public fun mm256MaskzShrdvEpi64(k: Mmask8, a: M256i, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShrdvEpi64(a, b, c).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shrdv_epi64&expand=5135)
 */
public fun mmShrdvEpi64(a: M128i, b: M128i, c: M128i): M128i = transmute(run { val _simdArg0 = b.asI64x2(); val _simdArg1 = a.asI64x2(); val _simdArg2 = run { val _simdArg0 = c.asI64x2(); val _simdArg1 = i64x2.splat(63); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shrdv_epi64&expand=5133)
 */
public fun mmMaskShrdvEpi64(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShrdvEpi64(a, b, c).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI64x2(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shrdv_epi64&expand=5134)
 */
public fun mmMaskzShrdvEpi64(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShrdvEpi64(a, b, c).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shrdv_epi32&expand=5132)
 */
public fun mm512ShrdvEpi32(a: M512i, b: M512i, c: M512i): M512i = transmute(run { val _simdArg0 = b.asI32x16(); val _simdArg1 = a.asI32x16(); val _simdArg2 = run { val _simdArg0 = c.asI32x16(); val _simdArg1 = i32x16.splat(31); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shrdv_epi32&expand=5130)
 */
public fun mm512MaskShrdvEpi32(a: M512i, k: Mmask16, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShrdvEpi32(a, b, c).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI32x16(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shrdv_epi32&expand=5131)
 */
public fun mm512MaskzShrdvEpi32(k: Mmask16, a: M512i, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShrdvEpi32(a, b, c).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shrdv_epi32&expand=5129)
 */
public fun mm256ShrdvEpi32(a: M256i, b: M256i, c: M256i): M256i = transmute(run { val _simdArg0 = b.asI32x8(); val _simdArg1 = a.asI32x8(); val _simdArg2 = run { val _simdArg0 = c.asI32x8(); val _simdArg1 = i32x8.splat(31); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shrdv_epi32&expand=5127)
 */
public fun mm256MaskShrdvEpi32(a: M256i, k: Mmask8, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShrdvEpi32(a, b, c).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI32x8(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shrdv_epi32&expand=5128)
 */
public fun mm256MaskzShrdvEpi32(k: Mmask8, a: M256i, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShrdvEpi32(a, b, c).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shrdv_epi32&expand=5126)
 */
public fun mmShrdvEpi32(a: M128i, b: M128i, c: M128i): M128i = transmute(run { val _simdArg0 = b.asI32x4(); val _simdArg1 = a.asI32x4(); val _simdArg2 = run { val _simdArg0 = c.asI32x4(); val _simdArg1 = i32x4.splat(31); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shrdv_epi32&expand=5124)
 */
public fun mmMaskShrdvEpi32(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShrdvEpi32(a, b, c).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI32x4(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shrdv_epi32&expand=5125)
 */
public fun mmMaskzShrdvEpi32(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShrdvEpi32(a, b, c).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shrdv_epi16&expand=5123)
 */
public fun mm512ShrdvEpi16(a: M512i, b: M512i, c: M512i): M512i = transmute(run { val _simdArg0 = b.asI16x32(); val _simdArg1 = a.asI16x32(); val _simdArg2 = run { val _simdArg0 = c.asI16x32(); val _simdArg1 = i16x32.splat(15); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shrdv_epi16&expand=5121)
 */
public fun mm512MaskShrdvEpi16(a: M512i, k: Mmask32, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShrdvEpi16(a, b, c).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI16x32(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shrdv_epi16&expand=5122)
 */
public fun mm512MaskzShrdvEpi16(k: Mmask32, a: M512i, b: M512i, c: M512i): M512i =
    run {
        val shf = mm512ShrdvEpi16(a, b, c).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x32.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shrdv_epi16&expand=5120)
 */
public fun mm256ShrdvEpi16(a: M256i, b: M256i, c: M256i): M256i = transmute(run { val _simdArg0 = b.asI16x16(); val _simdArg1 = a.asI16x16(); val _simdArg2 = run { val _simdArg0 = c.asI16x16(); val _simdArg1 = i16x16.splat(15); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shrdv_epi16&expand=5118)
 */
public fun mm256MaskShrdvEpi16(a: M256i, k: Mmask16, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShrdvEpi16(a, b, c).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI16x16(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shrdv_epi16&expand=5119)
 */
public fun mm256MaskzShrdvEpi16(k: Mmask16, a: M256i, b: M256i, c: M256i): M256i =
    run {
        val shf = mm256ShrdvEpi16(a, b, c).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x16.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shrdv_epi16&expand=5117)
 */
public fun mmShrdvEpi16(a: M128i, b: M128i, c: M128i): M128i = transmute(run { val _simdArg0 = b.asI16x8(); val _simdArg1 = a.asI16x8(); val _simdArg2 = run { val _simdArg0 = c.asI16x8(); val _simdArg1 = i16x8.splat(15); _simdArg0 }; _simdArg0 })

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shrdv_epi16&expand=5115)
 */
public fun mmMaskShrdvEpi16(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShrdvEpi16(a, b, c).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = a.asI16x8(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by the amount specified in the corresponding element of c, and store the lower 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shrdv_epi16&expand=5116)
 */
public fun mmMaskzShrdvEpi16(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i =
    run {
        val shf = mmShrdvEpi16(a, b, c).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shldi_epi64&expand=5060)
 */
public fun mm512ShldiEpi64(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512ShldvEpi64(a, b, mm512Set1Epi64(imm8.toLong()))
}

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shldi_epi64&expand=5058)
 */
public fun mm512MaskShldiEpi64(src: M512i, k: Mmask8, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShldiEpi64(a, b, imm8).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shldi_epi64&expand=5059)
 */
public fun mm512MaskzShldiEpi64(k: Mmask8, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShldiEpi64(a, b, imm8).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shldi_epi64&expand=5057)
 */
public fun mm256ShldiEpi64(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256ShldvEpi64(a, b, mm256Set1Epi64x(imm8.toLong()))
}

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shldi_epi64&expand=5055)
 */
public fun mm256MaskShldiEpi64(src: M256i, k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShldiEpi64(a, b, imm8).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shldi_epi64&expand=5056)
 */
public fun mm256MaskzShldiEpi64(k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShldiEpi64(a, b, imm8).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shldi_epi64&expand=5054)
 */
public fun mmShldiEpi64(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmShldvEpi64(a, b, mmSet1Epi64x(imm8.toLong()))
}

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shldi_epi64&expand=5052)
 */
public fun mmMaskShldiEpi64(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShldiEpi64(a, b, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

/**
 * Concatenate packed 64-bit integers in a and b producing an intermediate 128-bit result. Shift the result left by imm8 bits, and store the upper 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shldi_epi64&expand=5053)
 */
public fun mmMaskzShldiEpi64(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShldiEpi64(a, b, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shldi_epi32&expand=5051)
 */
public fun mm512ShldiEpi32(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512ShldvEpi32(a, b, mm512Set1Epi32(imm8))
}

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shldi_epi32&expand=5049)
 */
public fun mm512MaskShldiEpi32(src: M512i, k: Mmask16, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShldiEpi32(a, b, imm8).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shldi_epi32&expand=5050)
 */
public fun mm512MaskzShldiEpi32(k: Mmask16, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShldiEpi32(a, b, imm8).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shldi_epi32&expand=5048)
 */
public fun mm256ShldiEpi32(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256ShldvEpi32(a, b, mm256Set1Epi32(imm8))
}

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shldi_epi32&expand=5046)
 */
public fun mm256MaskShldiEpi32(src: M256i, k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShldiEpi32(a, b, imm8).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shldi_epi32&expand=5047)
 */
public fun mm256MaskzShldiEpi32(k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShldiEpi32(a, b, imm8).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shldi_epi32&expand=5045)
 */
public fun mmShldiEpi32(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmShldvEpi32(a, b, mmSet1Epi32(imm8))
}

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shldi_epi32&expand=5043)
 */
public fun mmMaskShldiEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShldiEpi32(a, b, imm8).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Concatenate packed 32-bit integers in a and b producing an intermediate 64-bit result. Shift the result left by imm8 bits, and store the upper 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shldi_epi32&expand=5044)
 */
public fun mmMaskzShldiEpi32(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShldiEpi32(a, b, imm8).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shldi_epi16&expand=5042)
 */
public fun mm512ShldiEpi16(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512ShldvEpi16(a, b, mm512Set1Epi16(imm8.toShort()))
}

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shldi_epi16&expand=5040)
 */
public fun mm512MaskShldiEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShldiEpi16(a, b, imm8).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI16x32(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shldi_epi16&expand=5041)
 */
public fun mm512MaskzShldiEpi16(k: Mmask32, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShldiEpi16(a, b, imm8).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x32.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shldi_epi16&expand=5039)
 */
public fun mm256ShldiEpi16(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256ShldvEpi16(a, b, mm256Set1Epi16(imm8.toShort()))
}

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shldi_epi16&expand=5037)
 */
public fun mm256MaskShldiEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShldiEpi16(a, b, imm8).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI16x16(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shldi_epi16&expand=5038)
 */
public fun mm256MaskzShldiEpi16(k: Mmask16, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShldiEpi16(a, b, imm8).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x16.ZERO; _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shldi_epi16&expand=5036)
 */
public fun mmShldiEpi16(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmShldvEpi16(a, b, mmSet1Epi16(imm8.toShort()))
}

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shldi_epi16&expand=5034)
 */
public fun mmMaskShldiEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShldiEpi16(a, b, imm8).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI16x8(); _simdArg1 })
    }

/**
 * Concatenate packed 16-bit integers in a and b producing an intermediate 32-bit result. Shift the result left by imm8 bits, and store the upper 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shldi_epi16&expand=5035)
 */
public fun mmMaskzShldiEpi16(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShldiEpi16(a, b, imm8).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x8.ZERO; _simdArg1 })
    }

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shrdi_epi64&expand=5114)
 */
public fun mm512ShrdiEpi64(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512ShrdvEpi64(a, b, mm512Set1Epi64(imm8.toLong()))
}

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst using writemask k (elements are copied from src" when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shrdi_epi64&expand=5112)
 */
public fun mm512MaskShrdiEpi64(src: M512i, k: Mmask8, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShrdiEpi64(a, b, imm8).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shrdi_epi64&expand=5113)
 */
public fun mm512MaskzShrdiEpi64(k: Mmask8, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShrdiEpi64(a, b, imm8).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shrdi_epi64&expand=5111)
 */
public fun mm256ShrdiEpi64(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256ShrdvEpi64(a, b, mm256Set1Epi64x(imm8.toLong()))
}

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst using writemask k (elements are copied from src" when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shrdi_epi64&expand=5109)
 */
public fun mm256MaskShrdiEpi64(src: M256i, k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShrdiEpi64(a, b, imm8).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shrdi_epi64&expand=5110)
 */
public fun mm256MaskzShrdiEpi64(k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShrdiEpi64(a, b, imm8).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shrdi_epi64&expand=5108)
 */
public fun mmShrdiEpi64(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmShrdvEpi64(a, b, mmSet1Epi64x(imm8.toLong()))
}

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst using writemask k (elements are copied from src" when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shrdi_epi64&expand=5106)
 */
public fun mmMaskShrdiEpi64(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShrdiEpi64(a, b, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

// should be vpshrdq

/**
 * Concatenate packed 64-bit integers in b and a producing an intermediate 128-bit result. Shift the result right by imm8 bits, and store the lower 64-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shrdi_epi64&expand=5107)
 */
public fun mmMaskzShrdiEpi64(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShrdiEpi64(a, b, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shrdi_epi32&expand=5105)
 */
public fun mm512ShrdiEpi32(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512ShrdvEpi32(a, b, mm512Set1Epi32(imm8))
}

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shrdi_epi32&expand=5103)
 */
public fun mm512MaskShrdiEpi32(src: M512i, k: Mmask16, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShrdiEpi32(a, b, imm8).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shrdi_epi32&expand=5104)
 */
public fun mm512MaskzShrdiEpi32(k: Mmask16, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShrdiEpi32(a, b, imm8).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shrdi_epi32&expand=5102)
 */
public fun mm256ShrdiEpi32(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256ShrdvEpi32(a, b, mm256Set1Epi32(imm8))
}

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shrdi_epi32&expand=5100)
 */
public fun mm256MaskShrdiEpi32(src: M256i, k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShrdiEpi32(a, b, imm8).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shrdi_epi32&expand=5101)
 */
public fun mm256MaskzShrdiEpi32(k: Mmask8, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShrdiEpi32(a, b, imm8).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shrdi_epi32&expand=5099)
 */
public fun mmShrdiEpi32(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmShrdvEpi32(a, b, mmSet1Epi32(imm8))
}

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shrdi_epi32&expand=5097)
 */
public fun mmMaskShrdiEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShrdiEpi32(a, b, imm8).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

// should be vpshldd

/**
 * Concatenate packed 32-bit integers in b and a producing an intermediate 64-bit result. Shift the result right by imm8 bits, and store the lower 32-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shrdi_epi32&expand=5098)
 */
public fun mmMaskzShrdiEpi32(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShrdiEpi32(a, b, imm8).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shrdi_epi16&expand=5096)
 */
public fun mm512ShrdiEpi16(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512ShrdvEpi16(a, b, mm512Set1Epi16(imm8.toShort()))
}

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shrdi_epi16&expand=5094)
 */
public fun mm512MaskShrdiEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShrdiEpi16(a, b, imm8).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI16x32(); _simdArg1 })
    }

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shrdi_epi16&expand=5095)
 */
public fun mm512MaskzShrdiEpi16(k: Mmask32, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm512ShrdiEpi16(a, b, imm8).asI16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x32.ZERO; _simdArg1 })
    }

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shrdi_epi16&expand=5093)
 */
public fun mm256ShrdiEpi16(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256ShrdvEpi16(a, b, mm256Set1Epi16(imm8.toShort()))
}

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shrdi_epi16&expand=5091)
 */
public fun mm256MaskShrdiEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShrdiEpi16(a, b, imm8).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI16x16(); _simdArg1 })
    }

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shrdi_epi16&expand=5092)
 */
public fun mm256MaskzShrdiEpi16(k: Mmask16, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256ShrdiEpi16(a, b, imm8).asI16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x16.ZERO; _simdArg1 })
    }

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shrdi_epi16&expand=5090)
 */
public fun mmShrdiEpi16(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmShrdvEpi16(a, b, mmSet1Epi16(imm8.toShort()))
}

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shrdi_epi16&expand=5088)
 */
public fun mmMaskShrdiEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShrdiEpi16(a, b, imm8).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = src.asI16x8(); _simdArg1 })
    }

// should be vpshrdw

/**
 * Concatenate packed 16-bit integers in b and a producing an intermediate 32-bit result. Shift the result right by imm8 bits, and store the lower 16-bits in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shrdi_epi16&expand=5089)
 */
public fun mmMaskzShrdiEpi16(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmShrdiEpi16(a, b, imm8).asI16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = shf; val _simdArg2 = i16x8.ZERO; _simdArg1 })
    }

public expect fun vcompressstorew(mem: MutPtr, data: I16x32, mask: UInt)

public expect fun vcompressstorew256(mem: MutPtr, data: I16x16, mask: UShort)

public expect fun vcompressstorew128(mem: MutPtr, data: I16x8, mask: UByte)

public expect fun vcompressstoreb(mem: MutPtr, data: I8x64, mask: ULong)

public expect fun vcompressstoreb256(mem: MutPtr, data: I8x32, mask: UInt)

public expect fun vcompressstoreb128(mem: MutPtr, data: I8x16, mask: UShort)

public expect fun vpcompressw(a: I16x32, src: I16x32, mask: UInt): I16x32

public expect fun vpcompressw256(a: I16x16, src: I16x16, mask: UShort): I16x16

public expect fun vpcompressw128(a: I16x8, src: I16x8, mask: UByte): I16x8

public expect fun vpcompressb(a: I8x64, src: I8x64, mask: ULong): I8x64

public expect fun vpcompressb256(a: I8x32, src: I8x32, mask: UInt): I8x32

public expect fun vpcompressb128(a: I8x16, src: I8x16, mask: UShort): I8x16

public expect fun vpexpandw(a: I16x32, src: I16x32, mask: UInt): I16x32

public expect fun vpexpandw256(a: I16x16, src: I16x16, mask: UShort): I16x16

public expect fun vpexpandw128(a: I16x8, src: I16x8, mask: UByte): I16x8

public expect fun vpexpandb(a: I8x64, src: I8x64, mask: ULong): I8x64

public expect fun vpexpandb256(a: I8x32, src: I8x32, mask: UInt): I8x32

public expect fun vpexpandb128(a: I8x16, src: I8x16, mask: UShort): I8x16

public expect fun expandloadb128(memAddr: ConstPtr, a: I8x16, mask: UShort): I8x16

public expect fun expandloadw128(memAddr: ConstPtr, a: I16x8, mask: UByte): I16x8

public expect fun expandloadb256(memAddr: ConstPtr, a: I8x32, mask: UInt): I8x32

public expect fun expandloadw256(memAddr: ConstPtr, a: I16x16, mask: UShort): I16x16

public expect fun expandloadb512(memAddr: ConstPtr, a: I8x64, mask: ULong): I8x64

public expect fun expandloadw512(memAddr: ConstPtr, a: I16x32, mask: UInt): I16x32
