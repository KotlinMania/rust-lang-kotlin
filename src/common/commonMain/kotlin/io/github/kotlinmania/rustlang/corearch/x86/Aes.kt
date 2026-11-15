// port-lint: source x86/aes.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M128i

// AES New Instructions (AES-NI)
//
// The intrinsics here correspond to those in the `wmmintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public expect fun aesdec(a: M128i, roundKey: M128i): M128i

public expect fun aesdeclast(a: M128i, roundKey: M128i): M128i

public expect fun aesenc(a: M128i, roundKey: M128i): M128i

public expect fun aesenclast(a: M128i, roundKey: M128i): M128i

public expect fun aesimc(a: M128i): M128i

public expect fun aeskeygenassist(a: M128i, imm8: UByte): M128i

/**
 * Performs one round of an AES decryption flow on data (state) in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesdec_si128)
 */
public fun mmAesdecSi128(a: M128i, roundKey: M128i): M128i = aesdec(a, roundKey)

/**
 * Performs the last round of an AES decryption flow on data (state) in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesdeclast_si128)
 */
public fun mmAesdeclastSi128(a: M128i, roundKey: M128i): M128i = aesdeclast(a, roundKey)

/**
 * Performs one round of an AES encryption flow on data (state) in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesenc_si128)
 */
public fun mmAesencSi128(a: M128i, roundKey: M128i): M128i = aesenc(a, roundKey)

/**
 * Performs the last round of an AES encryption flow on data (state) in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesenclast_si128)
 */
public fun mmAesenclastSi128(a: M128i, roundKey: M128i): M128i = aesenclast(a, roundKey)

/**
 * Performs the `InvMixColumns` transformation on `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aesimc_si128)
 */
public fun mmAesimcSi128(a: M128i): M128i = aesimc(a)

/**
 * Assist in expanding the AES cipher key.
 *
 * Assist in expanding the AES cipher key by computing steps towards
 * generating a round key for encryption cipher using data from `a` and an
 * 8-bit round constant `IMM8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_aeskeygenassist_si128)
 */
public fun mmAeskeygenassistSi128(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return aeskeygenassist(a, imm8.toUByte())
}
