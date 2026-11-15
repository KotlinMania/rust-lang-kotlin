// port-lint: source x86/vaes.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Vectorized AES Instructions (VAES)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public expect fun aesenc256(a: M256i, roundKey: M256i): M256i

public expect fun aesenclast256(a: M256i, roundKey: M256i): M256i

public expect fun aesdec256(a: M256i, roundKey: M256i): M256i

public expect fun aesdeclast256(a: M256i, roundKey: M256i): M256i

public expect fun aesenc512(a: M512i, roundKey: M512i): M512i

public expect fun aesenclast512(a: M512i, roundKey: M512i): M512i

public expect fun aesdec512(a: M512i, roundKey: M512i): M512i

public expect fun aesdeclast512(a: M512i, roundKey: M512i): M512i

/**
 * Performs one round of an AES encryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_aesenc_epi128)
 */
public fun mm256AesencEpi128(a: M256i, roundKey: M256i): M256i = aesenc256(a, roundKey)

/**
 * Performs the last round of an AES encryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_aesenclast_epi128)
 */
public fun mm256AesenclastEpi128(a: M256i, roundKey: M256i): M256i = aesenclast256(a, roundKey)

/**
 * Performs one round of an AES decryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_aesdec_epi128)
 */
public fun mm256AesdecEpi128(a: M256i, roundKey: M256i): M256i = aesdec256(a, roundKey)

/**
 * Performs the last round of an AES decryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_aesdeclast_epi128)
 */
public fun mm256AesdeclastEpi128(a: M256i, roundKey: M256i): M256i = aesdeclast256(a, roundKey)

/**
 * Performs one round of an AES encryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_aesenc_epi128)
 */
public fun mm512AesencEpi128(a: M512i, roundKey: M512i): M512i = aesenc512(a, roundKey)

/**
 * Performs the last round of an AES encryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_aesenclast_epi128)
 */
public fun mm512AesenclastEpi128(a: M512i, roundKey: M512i): M512i = aesenclast512(a, roundKey)

/**
 * Performs one round of an AES decryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_aesdec_epi128)
 */
public fun mm512AesdecEpi128(a: M512i, roundKey: M512i): M512i = aesdec512(a, roundKey)

/**
 * Performs the last round of an AES decryption flow on each 128-bit word (state) in `a` using
 * the corresponding 128-bit word (key) in `roundKey`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_aesdeclast_epi128)
 */
public fun mm512AesdeclastEpi128(a: M512i, roundKey: M512i): M512i = aesdeclast512(a, roundKey)
