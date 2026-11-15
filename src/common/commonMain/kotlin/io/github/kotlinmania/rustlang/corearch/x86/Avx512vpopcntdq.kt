// port-lint: source x86/avx512vpopcntdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.x86.M128i
import io.github.kotlinmania.rustlang.x86.M256i
import io.github.kotlinmania.rustlang.x86.M512i
import io.github.kotlinmania.rustlang.x86.Mmask16
import io.github.kotlinmania.rustlang.x86.Mmask8

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
public fun mm512PopcntEpi32(a: M512i): M512i = transmute(run { val _simdArg0 = a.asI32x16(); _simdArg0 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_popcnt_epi32)
 */
public fun mm512MaskzPopcntEpi32(k: Mmask16, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI32x16(); _simdArg0 }; val _simdArg2 = i32x16.ZERO; _simdArg1 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_popcnt_epi32)
 */
public fun mm512MaskPopcntEpi32(src: M512i, k: Mmask16, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI32x16(); _simdArg0 }; val _simdArg2 = src.asI32x16(); _simdArg1 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_popcnt_epi32)
 */
public fun mm256PopcntEpi32(a: M256i): M256i = transmute(run { val _simdArg0 = a.asI32x8(); _simdArg0 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_popcnt_epi32)
 */
public fun mm256MaskzPopcntEpi32(k: Mmask8, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI32x8(); _simdArg0 }; val _simdArg2 = i32x8.ZERO; _simdArg1 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_popcnt_epi32)
 */
public fun mm256MaskPopcntEpi32(src: M256i, k: Mmask8, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI32x8(); _simdArg0 }; val _simdArg2 = src.asI32x8(); _simdArg1 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_popcnt_epi32)
 */
public fun mmPopcntEpi32(a: M128i): M128i = transmute(run { val _simdArg0 = a.asI32x4(); _simdArg0 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_popcnt_epi32)
 */
public fun mmMaskzPopcntEpi32(k: Mmask8, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI32x4(); _simdArg0 }; val _simdArg2 = i32x4.ZERO; _simdArg1 })

/**
 * For each packed 32-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_popcnt_epi32)
 */
public fun mmMaskPopcntEpi32(src: M128i, k: Mmask8, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI32x4(); _simdArg0 }; val _simdArg2 = src.asI32x4(); _simdArg1 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_popcnt_epi64)
 */
public fun mm512PopcntEpi64(a: M512i): M512i = transmute(run { val _simdArg0 = a.asI64x8(); _simdArg0 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_popcnt_epi64)
 */
public fun mm512MaskzPopcntEpi64(k: Mmask8, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI64x8(); _simdArg0 }; val _simdArg2 = i64x8.ZERO; _simdArg1 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_popcnt_epi64)
 */
public fun mm512MaskPopcntEpi64(src: M512i, k: Mmask8, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI64x8(); _simdArg0 }; val _simdArg2 = src.asI64x8(); _simdArg1 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_popcnt_epi64)
 */
public fun mm256PopcntEpi64(a: M256i): M256i = transmute(run { val _simdArg0 = a.asI64x4(); _simdArg0 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_popcnt_epi64)
 */
public fun mm256MaskzPopcntEpi64(k: Mmask8, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI64x4(); _simdArg0 }; val _simdArg2 = i64x4.ZERO; _simdArg1 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_popcnt_epi64)
 */
public fun mm256MaskPopcntEpi64(src: M256i, k: Mmask8, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI64x4(); _simdArg0 }; val _simdArg2 = src.asI64x4(); _simdArg1 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_popcnt_epi64)
 */
public fun mmPopcntEpi64(a: M128i): M128i = transmute(run { val _simdArg0 = a.asI64x2(); _simdArg0 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_popcnt_epi64)
 */
public fun mmMaskzPopcntEpi64(k: Mmask8, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI64x2(); _simdArg0 }; val _simdArg2 = i64x2.ZERO; _simdArg1 })

/**
 * For each packed 64-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_popcnt_epi64)
 */
public fun mmMaskPopcntEpi64(src: M128i, k: Mmask8, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI64x2(); _simdArg0 }; val _simdArg2 = src.asI64x2(); _simdArg1 })
