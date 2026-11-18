// port-lint: source x86/avx512vpopcntdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// Vectorized Population Count Instructions for Double- and Quadwords (VPOPCNTDQ)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_popcnt_epi32)
 */
public fun mm512PopcntEpi32(a: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI32x16(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_popcnt_epi32)
 */
public fun mm512MaskzPopcntEpi32(k: Mmask16, a: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI32x16(); simdArg0 }; val simdArg2 = Simd<Int>(List(16) { 0 }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_popcnt_epi32)
 */
public fun mm512MaskPopcntEpi32(src: M512i, k: Mmask16, a: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI32x16(); simdArg0 }; val simdArg2 = src.asI32x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_popcnt_epi32)
 */
public fun mm256PopcntEpi32(a: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = a.asI32x8(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_popcnt_epi32)
 */
public fun mm256MaskzPopcntEpi32(k: Mmask8, a: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI32x8(); simdArg0 }; val simdArg2 = Simd<Int>(List(8) { 0 }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_popcnt_epi32)
 */
public fun mm256MaskPopcntEpi32(src: M256i, k: Mmask8, a: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI32x8(); simdArg0 }; val simdArg2 = src.asI32x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_popcnt_epi32)
 */
public fun mmPopcntEpi32(a: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = a.asI32x4(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_popcnt_epi32)
 */
public fun mmMaskzPopcntEpi32(k: Mmask8, a: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI32x4(); simdArg0 }; val simdArg2 = Simd<Int>(List(4) { 0 }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_popcnt_epi32)
 */
public fun mmMaskPopcntEpi32(src: M128i, k: Mmask8, a: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI32x4(); simdArg0 }; val simdArg2 = src.asI32x4(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_popcnt_epi64)
 */
public fun mm512PopcntEpi64(a: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI64x8(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_popcnt_epi64)
 */
public fun mm512MaskzPopcntEpi64(k: Mmask8, a: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI64x8(); simdArg0 }; val simdArg2 = Simd<Long>(List(8) { 0L }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_popcnt_epi64)
 */
public fun mm512MaskPopcntEpi64(src: M512i, k: Mmask8, a: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI64x8(); simdArg0 }; val simdArg2 = src.asI64x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_popcnt_epi64)
 */
public fun mm256PopcntEpi64(a: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = a.asI64x4(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_popcnt_epi64)
 */
public fun mm256MaskzPopcntEpi64(k: Mmask8, a: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI64x4(); simdArg0 }; val simdArg2 = Simd<Long>(List(4) { 0L }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_popcnt_epi64)
 */
public fun mm256MaskPopcntEpi64(src: M256i, k: Mmask8, a: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI64x4(); simdArg0 }; val simdArg2 = src.asI64x4(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_popcnt_epi64)
 */
public fun mmPopcntEpi64(a: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = a.asI64x2(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_popcnt_epi64)
 */
public fun mmMaskzPopcntEpi64(k: Mmask8, a: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI64x2(); simdArg0 }; val simdArg2 = Simd<Long>(List(2) { 0L }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_popcnt_epi64)
 */
public fun mmMaskPopcntEpi64(src: M128i, k: Mmask8, a: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = run { val simdArg0 = a.asI64x2(); simdArg0 }; val simdArg2 = src.asI64x2(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
