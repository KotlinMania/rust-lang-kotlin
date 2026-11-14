// port-lint: source x86/avx512ifma.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.intrinsics.simd.simdSelectBitmask

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm512_madd52hi_epu64)
 */
public fun mm512Madd52hiEpu64(a: M512i, b: M512i, c: M512i): M512i = vpmadd52huq512(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are copied
 * from `k` when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm512_mask_madd52hi_epu64)
 */
public fun mm512MaskMadd52hiEpu64(a: M512i, k: Mmask8, b: M512i, c: M512i): M512i = simdSelectBitmask(k, vpmadd52huq512(a, b, c), a)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm512_maskz_madd52hi_epu64)
 */
public fun mm512MaskzMadd52hiEpu64(k: Mmask8, a: M512i, b: M512i, c: M512i): M512i = simdSelectBitmask(k, vpmadd52huq512(a, b, c), mm512SetzeroSi512())

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm512_madd52lo_epu64)
 */
public fun mm512Madd52loEpu64(a: M512i, b: M512i, c: M512i): M512i = vpmadd52luq512(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are copied
 * from `k` when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm512_mask_madd52lo_epu64)
 */
public fun mm512MaskMadd52loEpu64(a: M512i, k: Mmask8, b: M512i, c: M512i): M512i = simdSelectBitmask(k, vpmadd52luq512(a, b, c), a)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm512_maskz_madd52lo_epu64)
 */
public fun mm512MaskzMadd52loEpu64(k: Mmask8, a: M512i, b: M512i, c: M512i): M512i = simdSelectBitmask(k, vpmadd52luq512(a, b, c), mm512SetzeroSi512())

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_madd52hi_avx_epu64)
 */
public fun mm256Madd52hiAvxEpu64(a: M256i, b: M256i, c: M256i): M256i = vpmadd52huq256(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm256_madd52hi_epu64)
 */
public fun mm256Madd52hiEpu64(a: M256i, b: M256i, c: M256i): M256i = vpmadd52huq256(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are copied
 * from `k` when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm256_mask_madd52hi_epu64)
 */
public fun mm256MaskMadd52hiEpu64(a: M256i, k: Mmask8, b: M256i, c: M256i): M256i = simdSelectBitmask(k, vpmadd52huq256(a, b, c), a)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm256_maskz_madd52hi_epu64)
 */
public fun mm256MaskzMadd52hiEpu64(k: Mmask8, a: M256i, b: M256i, c: M256i): M256i = simdSelectBitmask(k, vpmadd52huq256(a, b, c), mm256SetzeroSi256())

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_madd52lo_avx_epu64)
 */
public fun mm256Madd52loAvxEpu64(a: M256i, b: M256i, c: M256i): M256i = vpmadd52luq256(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm256_madd52lo_epu64)
 */
public fun mm256Madd52loEpu64(a: M256i, b: M256i, c: M256i): M256i = vpmadd52luq256(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are copied
 * from `k` when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm256_mask_madd52lo_epu64)
 */
public fun mm256MaskMadd52loEpu64(a: M256i, k: Mmask8, b: M256i, c: M256i): M256i = simdSelectBitmask(k, vpmadd52luq256(a, b, c), a)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm256_maskz_madd52lo_epu64)
 */
public fun mm256MaskzMadd52loEpu64(k: Mmask8, a: M256i, b: M256i, c: M256i): M256i = simdSelectBitmask(k, vpmadd52luq256(a, b, c), mm256SetzeroSi256())

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_madd52hi_avx_epu64)
 */
public fun mmMadd52hiAvxEpu64(a: M128i, b: M128i, c: M128i): M128i = vpmadd52huq128(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm_madd52hi_epu64)
 */
public fun mmMadd52hiEpu64(a: M128i, b: M128i, c: M128i): M128i = vpmadd52huq128(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are copied
 * from `k` when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm_mask_madd52hi_epu64)
 */
public fun mmMaskMadd52hiEpu64(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i = simdSelectBitmask(k, vpmadd52huq128(a, b, c), a)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the high 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm_maskz_madd52hi_epu64)
 */
public fun mmMaskzMadd52hiEpu64(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i = simdSelectBitmask(k, vpmadd52huq128(a, b, c), mmSetzeroSi128())

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_madd52lo_avx_epu64)
 */
public fun mmMadd52loAvxEpu64(a: M128i, b: M128i, c: M128i): M128i = vpmadd52luq128(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm_madd52lo_epu64)
 */
public fun mmMadd52loEpu64(a: M128i, b: M128i, c: M128i): M128i = vpmadd52luq128(a, b, c)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are copied
 * from `k` when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm_mask_madd52lo_epu64)
 */
public fun mmMaskMadd52loEpu64(a: M128i, k: Mmask8, b: M128i, c: M128i): M128i = simdSelectBitmask(k, vpmadd52luq128(a, b, c), a)

/**
 * Multiply packed unsigned 52-bit integers in each 64-bit element of
 * `b` and `c` to form a 104-bit intermediate result. Add the low 52-bit
 * unsigned integer from the intermediate result with the
 * corresponding unsigned 64-bit integer in `a`, and store the
 * results in `dst` using writemask `k` (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#avx512techs=AVX512IFMA52&text=_mm_maskz_madd52lo_epu64)
 */
public fun mmMaskzMadd52loEpu64(k: Mmask8, a: M128i, b: M128i, c: M128i): M128i = simdSelectBitmask(k, vpmadd52luq128(a, b, c), mmSetzeroSi128())

public expect fun vpmadd52luq128(z: M128i, x: M128i, y: M128i): M128i

public expect fun vpmadd52huq128(z: M128i, x: M128i, y: M128i): M128i

public expect fun vpmadd52luq256(z: M256i, x: M256i, y: M256i): M256i

public expect fun vpmadd52huq256(z: M256i, x: M256i, y: M256i): M256i

public expect fun vpmadd52luq512(z: M512i, x: M512i, y: M512i): M512i

public expect fun vpmadd52huq512(z: M512i, x: M512i, y: M512i): M512i
