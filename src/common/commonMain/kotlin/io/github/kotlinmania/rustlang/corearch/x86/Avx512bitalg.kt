// port-lint: source x86/avx512bitalg.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.wasm32.i16x8

// Bit-oriented Algorithms (BITALG)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public expect fun bitshuffle512(data: I8x64, indices: I8x64): Mmask64

public expect fun bitshuffle256(data: I8x32, indices: I8x32): Mmask32

public expect fun bitshuffle128(data: I8x16, indices: I8x16): Mmask16

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_popcnt_epi16)
 */
public fun mm512PopcntEpi16(a: M512i): M512i = transmute(run { val _simdArg0 = a.asI16x32(); _simdArg0 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_popcnt_epi16)
 */
public fun mm512MaskzPopcntEpi16(k: Mmask32, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI16x32(); _simdArg0 }; val _simdArg2 = I16x32.ZERO; _simdArg1 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_popcnt_epi16)
 */
public fun mm512MaskPopcntEpi16(src: M512i, k: Mmask32, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI16x32(); _simdArg0 }; val _simdArg2 = src.asI16x32(); _simdArg1 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_popcnt_epi16)
 */
public fun mm256PopcntEpi16(a: M256i): M256i = transmute(run { val _simdArg0 = a.asI16x16(); _simdArg0 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_popcnt_epi16)
 */
public fun mm256MaskzPopcntEpi16(k: Mmask16, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI16x16(); _simdArg0 }; val _simdArg2 = I16x16.ZERO; _simdArg1 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_popcnt_epi16)
 */
public fun mm256MaskPopcntEpi16(src: M256i, k: Mmask16, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI16x16(); _simdArg0 }; val _simdArg2 = src.asI16x16(); _simdArg1 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_popcnt_epi16)
 */
public fun mmPopcntEpi16(a: M128i): M128i = transmute(run { val _simdArg0 = a.asI16x8(); _simdArg0 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_popcnt_epi16)
 */
public fun mmMaskzPopcntEpi16(k: Mmask8, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI16x8(); _simdArg0 }; val _simdArg2 = i16x8.ZERO; _simdArg1 })

/**
 * For each packed 16-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_popcnt_epi16)
 */
public fun mmMaskPopcntEpi16(src: M128i, k: Mmask8, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI16x8(); _simdArg0 }; val _simdArg2 = src.asI16x8(); _simdArg1 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_popcnt_epi8)
 */
public fun mm512PopcntEpi8(a: M512i): M512i = transmute(run { val _simdArg0 = a.asI8x64(); _simdArg0 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_popcnt_epi8)
 */
public fun mm512MaskzPopcntEpi8(k: Mmask64, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI8x64(); _simdArg0 }; val _simdArg2 = I8x64.ZERO; _simdArg1 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_popcnt_epi8)
 */
public fun mm512MaskPopcntEpi8(src: M512i, k: Mmask64, a: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI8x64(); _simdArg0 }; val _simdArg2 = src.asI8x64(); _simdArg1 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_popcnt_epi8)
 */
public fun mm256PopcntEpi8(a: M256i): M256i = transmute(run { val _simdArg0 = a.asI8x32(); _simdArg0 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_popcnt_epi8)
 */
public fun mm256MaskzPopcntEpi8(k: Mmask32, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI8x32(); _simdArg0 }; val _simdArg2 = I8x32.ZERO; _simdArg1 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_popcnt_epi8)
 */
public fun mm256MaskPopcntEpi8(src: M256i, k: Mmask32, a: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI8x32(); _simdArg0 }; val _simdArg2 = src.asI8x32(); _simdArg1 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_popcnt_epi8)
 */
public fun mmPopcntEpi8(a: M128i): M128i = transmute(run { val _simdArg0 = a.asI8x16(); _simdArg0 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_popcnt_epi8)
 */
public fun mmMaskzPopcntEpi8(k: Mmask16, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI8x16(); _simdArg0 }; val _simdArg2 = I8x16.ZERO; _simdArg1 })

/**
 * For each packed 8-bit integer maps the value to the number of logical 1 bits.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_popcnt_epi8)
 */
public fun mmMaskPopcntEpi8(src: M128i, k: Mmask16, a: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = run { val _simdArg0 = a.asI8x16(); _simdArg0 }; val _simdArg2 = src.asI8x16(); _simdArg1 })

/**
 * Considers the input `b` as packed 64-bit integers and `c` as packed 8-bit integers.
 * Then groups 8 8-bit values from `c`as indices into the bits of the corresponding 64-bit integer.
 * It then selects these bits and packs them into the output.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_bitshuffle_epi64_mask)
 */
public fun mm512BitshuffleEpi64Mask(b: M512i, c: M512i): Mmask64 = bitshuffle512(b.asI8x64(), c.asI8x64())

/**
 * Considers the input `b` as packed 64-bit integers and `c` as packed 8-bit integers.
 * Then groups 8 8-bit values from `c`as indices into the bits of the corresponding 64-bit integer.
 * It then selects these bits and packs them into the output.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_bitshuffle_epi64_mask)
 */
public fun mm512MaskBitshuffleEpi64Mask(k: Mmask64, b: M512i, c: M512i): Mmask64 = mm512BitshuffleEpi64Mask(b, c) and k

/**
 * Considers the input `b` as packed 64-bit integers and `c` as packed 8-bit integers.
 * Then groups 8 8-bit values from `c`as indices into the bits of the corresponding 64-bit integer.
 * It then selects these bits and packs them into the output.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_bitshuffle_epi64_mask)
 */
public fun mm256BitshuffleEpi64Mask(b: M256i, c: M256i): Mmask32 = bitshuffle256(b.asI8x32(), c.asI8x32())

/**
 * Considers the input `b` as packed 64-bit integers and `c` as packed 8-bit integers.
 * Then groups 8 8-bit values from `c`as indices into the bits of the corresponding 64-bit integer.
 * It then selects these bits and packs them into the output.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_bitshuffle_epi64_mask)
 */
public fun mm256MaskBitshuffleEpi64Mask(k: Mmask32, b: M256i, c: M256i): Mmask32 = mm256BitshuffleEpi64Mask(b, c) and k

/**
 * Considers the input `b` as packed 64-bit integers and `c` as packed 8-bit integers.
 * Then groups 8 8-bit values from `c`as indices into the bits of the corresponding 64-bit integer.
 * It then selects these bits and packs them into the output.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_bitshuffle_epi64_mask)
 */
public fun mmBitshuffleEpi64Mask(b: M128i, c: M128i): Mmask16 = bitshuffle128(b.asI8x16(), c.asI8x16())

/**
 * Considers the input `b` as packed 64-bit integers and `c` as packed 8-bit integers.
 * Then groups 8 8-bit values from `c`as indices into the bits of the corresponding 64-bit integer.
 * It then selects these bits and packs them into the output.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_bitshuffle_epi64_mask)
 */
public fun mmMaskBitshuffleEpi64Mask(k: Mmask16, b: M128i, c: M128i): Mmask16 = mmBitshuffleEpi64Mask(b, c) and k
