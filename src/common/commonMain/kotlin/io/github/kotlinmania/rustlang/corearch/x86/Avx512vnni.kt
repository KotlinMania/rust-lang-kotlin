// port-lint: source x86/avx512vnni.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x32
import io.github.kotlinmania.rustlang.corearch.simd.U8x64

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_dpwssd_epi32&expand=2219)
 */
public fun mm512DpwssdEpi32(src: M512i, a: M512i, b: M512i): M512i = transmute(vpdpwssd(src.asI32x16(), a.asI16x32(), b.asI16x32()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_dpwssd_epi32&expand=2220)
 */
public fun mm512MaskDpwssdEpi32(src: M512i, k: Mmask16, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpwssdEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_dpwssd_epi32&expand=2221)
 */
public fun mm512MaskzDpwssdEpi32(k: Mmask16, src: M512i, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpwssdEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwssd_avx_epi32&expand=2713)
 */
public fun mm256DpwssdAvxEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwssd256(src.asI32x8(), a.asI16x16(), b.asI16x16()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwssd_epi32&expand=2216)
 */
public fun mm256DpwssdEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwssd256(src.asI32x8(), a.asI16x16(), b.asI16x16()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_dpwssd_epi32&expand=2217)
 */
public fun mm256MaskDpwssdEpi32(src: M256i, k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpwssdEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_dpwssd_epi32&expand=2218)
 */
public fun mm256MaskzDpwssdEpi32(k: Mmask8, src: M256i, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpwssdEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwssd_avx_epi32&expand=2712)
 */
public fun mmDpwssdAvxEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwssd128(src.asI32x4(), a.asI16x8(), b.asI16x8()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwssd_epi32&expand=2213)
 */
public fun mmDpwssdEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwssd128(src.asI32x4(), a.asI16x8(), b.asI16x8()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_dpwssd_epi32&expand=2214)
 */
public fun mmMaskDpwssdEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpwssdEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_dpwssd_epi32&expand=2215)
 */
public fun mmMaskzDpwssdEpi32(k: Mmask8, src: M128i, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpwssdEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_dpwssds_epi32&expand=2228)
 */
public fun mm512DpwssdsEpi32(src: M512i, a: M512i, b: M512i): M512i = transmute(vpdpwssds(src.asI32x16(), a.asI16x32(), b.asI16x32()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_dpwssds_epi32&expand=2229)
 */
public fun mm512MaskDpwssdsEpi32(src: M512i, k: Mmask16, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpwssdsEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_dpwssds_epi32&expand=2230)
 */
public fun mm512MaskzDpwssdsEpi32(k: Mmask16, src: M512i, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpwssdsEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwssds_avx_epi32&expand=2726)
 */
public fun mm256DpwssdsAvxEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwssds256(src.asI32x8(), a.asI16x16(), b.asI16x16()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwssds_epi32&expand=2225)
 */
public fun mm256DpwssdsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwssds256(src.asI32x8(), a.asI16x16(), b.asI16x16()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_dpwssds_epi32&expand=2226)
 */
public fun mm256MaskDpwssdsEpi32(src: M256i, k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpwssdsEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_dpwssds_epi32&expand=2227)
 */
public fun mm256MaskzDpwssdsEpi32(k: Mmask8, src: M256i, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpwssdsEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwssds_avx_epi32&expand=2725)
 */
public fun mmDpwssdsAvxEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwssds128(src.asI32x4(), a.asI16x8(), b.asI16x8()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwssds_epi32&expand=2222)
 */
public fun mmDpwssdsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwssds128(src.asI32x4(), a.asI16x8(), b.asI16x8()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_dpwssds_epi32&expand=2223)
 */
public fun mmMaskDpwssdsEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpwssdsEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding 16-bit integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_dpwssds_epi32&expand=2224)
 */
public fun mmMaskzDpwssdsEpi32(k: Mmask8, src: M128i, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpwssdsEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_dpbusd_epi32&expand=2201)
 */
public fun mm512DpbusdEpi32(src: M512i, a: M512i, b: M512i): M512i = transmute(vpdpbusd(src.asI32x16(), a.asU8x64(), b.asI8x64()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_dpbusd_epi32&expand=2202)
 */
public fun mm512MaskDpbusdEpi32(src: M512i, k: Mmask16, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpbusdEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_dpbusd_epi32&expand=2203)
 */
public fun mm512MaskzDpbusdEpi32(k: Mmask16, src: M512i, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpbusdEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbusd_avx_epi32&expand=2683)
 */
public fun mm256DpbusdAvxEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbusd256(src.asI32x8(), a.asU8x32(), b.asI8x32()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbusd_epi32&expand=2198)
 */
public fun mm256DpbusdEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbusd256(src.asI32x8(), a.asU8x32(), b.asI8x32()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_dpbusd_epi32&expand=2199)
 */
public fun mm256MaskDpbusdEpi32(src: M256i, k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpbusdEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_dpbusd_epi32&expand=2200)
 */
public fun mm256MaskzDpbusdEpi32(k: Mmask8, src: M256i, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpbusdEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbusd_avx_epi32&expand=2682)
 */
public fun mmDpbusdAvxEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbusd128(src.asI32x4(), a.asU8x16(), b.asI8x16()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbusd_epi32&expand=2195)
 */
public fun mmDpbusdEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbusd128(src.asI32x4(), a.asU8x16(), b.asI8x16()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_dpbusd_epi32&expand=2196)
 */
public fun mmMaskDpbusdEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpbusdEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_dpbusd_epi32&expand=2197)
 */
public fun mmMaskzDpbusdEpi32(k: Mmask8, src: M128i, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpbusdEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_dpbusds_epi32&expand=2210)
 */
public fun mm512DpbusdsEpi32(src: M512i, a: M512i, b: M512i): M512i = transmute(vpdpbusds(src.asI32x16(), a.asU8x64(), b.asI8x64()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_dpbusds_epi32&expand=2211)
 */
public fun mm512MaskDpbusdsEpi32(src: M512i, k: Mmask16, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpbusdsEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_dpbusds_epi32&expand=2212)
 */
public fun mm512MaskzDpbusdsEpi32(k: Mmask16, src: M512i, a: M512i, b: M512i): M512i =
    run {
        val r = mm512DpbusdsEpi32(src, a, b).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbusds_avx_epi32&expand=2696)
 */
public fun mm256DpbusdsAvxEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbusds256(src.asI32x8(), a.asU8x32(), b.asI8x32()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbusds_epi32&expand=2207)
 */
public fun mm256DpbusdsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbusds256(src.asI32x8(), a.asU8x32(), b.asI8x32()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_dpbusds_epi32&expand=2208)
 */
public fun mm256MaskDpbusdsEpi32(src: M256i, k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpbusdsEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_dpbusds_epi32&expand=2209)
 */
public fun mm256MaskzDpbusdsEpi32(k: Mmask8, src: M256i, a: M256i, b: M256i): M256i =
    run {
        val r = mm256DpbusdsEpi32(src, a, b).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbusds_avx_epi32&expand=2695)
 */
public fun mmDpbusdsAvxEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbusds128(src.asI32x4(), a.asU8x16(), b.asI8x16()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbusds_epi32&expand=2204)
 */
public fun mmDpbusdsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbusds128(src.asI32x4(), a.asU8x16(), b.asI8x16()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_dpbusds_epi32&expand=2205)
 */
public fun mmMaskDpbusdsEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpbusdsEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding signed 8-bit integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding 32-bit integer in src using signed saturation, and store the packed 32-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_dpbusds_epi32&expand=2206)
 */
public fun mmMaskzDpbusdsEpi32(k: Mmask8, src: M128i, a: M128i, b: M128i): M128i =
    run {
        val r = mmDpbusdsEpi32(src, a, b).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding signed 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbssd_epi32&expand=2674)
 */
public fun mmDpbssdEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbssd128(src.asI32x4(), a.asI8x16(), b.asI8x16()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding signed 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbssd_epi32&expand=2675)
 */
public fun mm256DpbssdEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbssd256(src.asI32x8(), a.asI8x32(), b.asI8x32()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding signed 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbssds_epi32&expand=2676)
 */
public fun mmDpbssdsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbssds128(src.asI32x4(), a.asI8x16(), b.asI8x16()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding signed 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbssds_epi32&expand=2677)
 */
public fun mm256DpbssdsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbssds256(src.asI32x8(), a.asI8x32(), b.asI8x32()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbsud_epi32&expand=2678)
 */
public fun mmDpbsudEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbsud128(src.asI32x4(), a.asI8x16(), b.asU8x16()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbsud_epi32&expand=2679)
 */
public fun mm256DpbsudEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbsud256(src.asI32x8(), a.asI8x32(), b.asU8x32()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbsuds_epi32&expand=2680)
 */
public fun mmDpbsudsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbsuds128(src.asI32x4(), a.asI8x16(), b.asU8x16()))

/**
 * Multiply groups of 4 adjacent pairs of signed 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbsuds_epi32&expand=2681)
 */
public fun mm256DpbsudsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbsuds256(src.asI32x8(), a.asI8x32(), b.asU8x32()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbuud_epi32&expand=2708)
 */
public fun mmDpbuudEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbuud128(src.asI32x4(), a.asU8x16(), b.asU8x16()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbuud_epi32&expand=2709)
 */
public fun mm256DpbuudEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbuud256(src.asI32x8(), a.asU8x32(), b.asU8x32()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpbuuds_epi32&expand=2710)
 */
public fun mmDpbuudsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpbuuds128(src.asI32x4(), a.asU8x16(), b.asU8x16()))

/**
 * Multiply groups of 4 adjacent pairs of unsigned 8-bit integers in a with corresponding unsigned 8-bit
 * integers in b, producing 4 intermediate signed 16-bit results. Sum these 4 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpbuuds_epi32&expand=2711)
 */
public fun mm256DpbuudsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpbuuds256(src.asI32x8(), a.asU8x32(), b.asU8x32()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwsud_epi32&expand=2738)
 */
public fun mmDpwsudEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwsud128(src.asI32x4(), a.asI16x8(), b.asU16x8()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwsud_epi32&expand=2739)
 */
public fun mm256DpwsudEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwsud256(src.asI32x8(), a.asI16x16(), b.asU16x16()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwsuds_epi32&expand=2740)
 */
public fun mmDpwsudsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwsuds128(src.asI32x4(), a.asI16x8(), b.asU16x8()))

/**
 * Multiply groups of 2 adjacent pairs of signed 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwsuds_epi32&expand=2741)
 */
public fun mm256DpwsudsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwsuds256(src.asI32x8(), a.asI16x16(), b.asU16x16()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding signed 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwusd_epi32&expand=2742)
 */
public fun mmDpwusdEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwusd128(src.asI32x4(), a.asU16x8(), b.asI16x8()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding signed 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwusd_epi32&expand=2743)
 */
public fun mm256DpwusdEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwusd256(src.asI32x8(), a.asU16x16(), b.asI16x16()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding signed 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwusds_epi32&expand=2744)
 */
public fun mmDpwusdsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwusds128(src.asI32x4(), a.asU16x8(), b.asI16x8()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding signed 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwusds_epi32&expand=2745)
 */
public fun mm256DpwusdsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwusds256(src.asI32x8(), a.asU16x16(), b.asI16x16()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwuud_epi32&expand=2746)
 */
public fun mmDpwuudEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwuud128(src.asI32x4(), a.asU16x8(), b.asU16x8()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwuud_epi32&expand=2747)
 */
public fun mm256DpwuudEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwuud256(src.asI32x8(), a.asU16x16(), b.asU16x16()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dpwuuds_epi32&expand=2748)
 */
public fun mmDpwuudsEpi32(src: M128i, a: M128i, b: M128i): M128i = transmute(vpdpwuuds128(src.asI32x4(), a.asU16x8(), b.asU16x8()))

/**
 * Multiply groups of 2 adjacent pairs of unsigned 16-bit integers in a with corresponding unsigned 16-bit
 * integers in b, producing 2 intermediate signed 32-bit results. Sum these 2 results with the corresponding
 * 32-bit integer in src with signed saturation, and store the packed 32-bit results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dpwuuds_epi32&expand=2749)
 */
public fun mm256DpwuudsEpi32(src: M256i, a: M256i, b: M256i): M256i = transmute(vpdpwuuds256(src.asI32x8(), a.asU16x16(), b.asU16x16()))

public expect fun vpdpwssd(src: I32x16, a: I16x32, b: I16x32): I32x16

public expect fun vpdpwssd256(src: I32x8, a: I16x16, b: I16x16): I32x8

public expect fun vpdpwssd128(src: I32x4, a: I16x8, b: I16x8): I32x4

public expect fun vpdpwssds(src: I32x16, a: I16x32, b: I16x32): I32x16

public expect fun vpdpwssds256(src: I32x8, a: I16x16, b: I16x16): I32x8

public expect fun vpdpwssds128(src: I32x4, a: I16x8, b: I16x8): I32x4

public expect fun vpdpbusd(src: I32x16, a: U8x64, b: I8x64): I32x16

public expect fun vpdpbusd256(src: I32x8, a: U8x32, b: I8x32): I32x8

public expect fun vpdpbusd128(src: I32x4, a: U8x16, b: I8x16): I32x4

public expect fun vpdpbusds(src: I32x16, a: U8x64, b: I8x64): I32x16

public expect fun vpdpbusds256(src: I32x8, a: U8x32, b: I8x32): I32x8

public expect fun vpdpbusds128(src: I32x4, a: U8x16, b: I8x16): I32x4

public expect fun vpdpbssd128(src: I32x4, a: I8x16, b: I8x16): I32x4

public expect fun vpdpbssd256(src: I32x8, a: I8x32, b: I8x32): I32x8

public expect fun vpdpbssds128(src: I32x4, a: I8x16, b: I8x16): I32x4

public expect fun vpdpbssds256(src: I32x8, a: I8x32, b: I8x32): I32x8

public expect fun vpdpbsud128(src: I32x4, a: I8x16, b: U8x16): I32x4

public expect fun vpdpbsud256(src: I32x8, a: I8x32, b: U8x32): I32x8

public expect fun vpdpbsuds128(src: I32x4, a: I8x16, b: U8x16): I32x4

public expect fun vpdpbsuds256(src: I32x8, a: I8x32, b: U8x32): I32x8

public expect fun vpdpbuud128(src: I32x4, a: U8x16, b: U8x16): I32x4

public expect fun vpdpbuud256(src: I32x8, a: U8x32, b: U8x32): I32x8

public expect fun vpdpbuuds128(src: I32x4, a: U8x16, b: U8x16): I32x4

public expect fun vpdpbuuds256(src: I32x8, a: U8x32, b: U8x32): I32x8

public expect fun vpdpwsud128(src: I32x4, a: I16x8, b: U16x8): I32x4

public expect fun vpdpwsud256(src: I32x8, a: I16x16, b: U16x16): I32x8

public expect fun vpdpwsuds128(src: I32x4, a: I16x8, b: U16x8): I32x4

public expect fun vpdpwsuds256(src: I32x8, a: I16x16, b: U16x16): I32x8

public expect fun vpdpwusd128(src: I32x4, a: U16x8, b: I16x8): I32x4

public expect fun vpdpwusd256(src: I32x8, a: U16x16, b: I16x16): I32x8

public expect fun vpdpwusds128(src: I32x4, a: U16x8, b: I16x8): I32x4

public expect fun vpdpwusds256(src: I32x8, a: U16x16, b: I16x16): I32x8

public expect fun vpdpwuud128(src: I32x4, a: U16x8, b: U16x8): I32x4

public expect fun vpdpwuud256(src: I32x8, a: U16x16, b: U16x16): I32x8

public expect fun vpdpwuuds128(src: I32x4, a: U16x8, b: U16x8): I32x4

public expect fun vpdpwuuds256(src: I32x8, a: U16x16, b: U16x16): I32x8
