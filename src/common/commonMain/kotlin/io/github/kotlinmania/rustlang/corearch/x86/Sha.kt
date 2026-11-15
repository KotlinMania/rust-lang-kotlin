// port-lint: source x86/sha.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4

public expect fun sha1msg1(a: I32x4, b: I32x4): I32x4

public expect fun sha1msg2(a: I32x4, b: I32x4): I32x4

public expect fun sha1nexte(a: I32x4, b: I32x4): I32x4

public expect fun sha1rnds4(a: I32x4, b: I32x4, c: Byte): I32x4

public expect fun sha256msg1(a: I32x4, b: I32x4): I32x4

public expect fun sha256msg2(a: I32x4, b: I32x4): I32x4

public expect fun sha256rnds2(a: I32x4, b: I32x4, k: I32x4): I32x4

public expect fun vsha512msg1(a: I64x4, b: I64x2): I64x4

public expect fun vsha512msg2(a: I64x4, b: I64x4): I64x4

public expect fun vsha512rnds2(a: I64x4, b: I64x4, k: I64x2): I64x4

public expect fun vsm3msg1(a: I32x4, b: I32x4, c: I32x4): I32x4

public expect fun vsm3msg2(a: I32x4, b: I32x4, c: I32x4): I32x4

public expect fun vsm3rnds2(a: I32x4, b: I32x4, c: I32x4, d: Int): I32x4

public expect fun vsm4key4128(a: I32x4, b: I32x4): I32x4

public expect fun vsm4key4256(a: I32x8, b: I32x8): I32x8

public expect fun vsm4rnds4128(a: I32x4, b: I32x4): I32x4

public expect fun vsm4rnds4256(a: I32x8, b: I32x8): I32x8

/**
 * Performs an intermediate calculation for the next four SHA1 message values
 * (unsigned 32-bit integers) using previous message values from `a` and `b`,
 * and returning the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha1msg1_epu32)
 */
public fun mmSha1msg1Epu32(a: M128i, b: M128i): M128i = transmute(sha1msg1(a.asI32x4(), b.asI32x4()))

/**
 * Performs the final calculation for the next four SHA1 message values
 * (unsigned 32-bit integers) using the intermediate result in `a` and the
 * previous message values in `b`, and returns the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha1msg2_epu32)
 */
public fun mmSha1msg2Epu32(a: M128i, b: M128i): M128i = transmute(sha1msg2(a.asI32x4(), b.asI32x4()))

/**
 * Calculate SHA1 state variable E after four rounds of operation from the
 * current SHA1 state variable `a`, add that value to the scheduled values
 * (unsigned 32-bit integers) in `b`, and returns the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha1nexte_epu32)
 */
public fun mmSha1nexteEpu32(a: M128i, b: M128i): M128i = transmute(sha1nexte(a.asI32x4(), b.asI32x4()))

/**
 * Performs four rounds of SHA1 operation using an initial SHA1 state (A,B,C,D)
 * from `a` and some pre-computed sum of the next 4 round message values
 * (unsigned 32-bit integers), and state variable E from `b`, and return the
 * updated SHA1 state (A,B,C,D). `FUNC` contains the logic functions and round
 * constants.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha1rnds4_epu32)
 */
public fun mmSha1rnds4Epu32(a: M128i, b: M128i, func: Int): M128i {
    run {
        check(0 <= func && func < (1 shl 2)) { "FUNC doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(sha1rnds4(a.asI32x4(), b.asI32x4(), func.toByte()))
}

/**
 * Performs an intermediate calculation for the next four SHA256 message values
 * (unsigned 32-bit integers) using previous message values from `a` and `b`,
 * and return the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha256msg1_epu32)
 */
public fun mmSha256msg1Epu32(a: M128i, b: M128i): M128i = transmute(sha256msg1(a.asI32x4(), b.asI32x4()))

/**
 * Performs the final calculation for the next four SHA256 message values
 * (unsigned 32-bit integers) using previous message values from `a` and `b`,
 * and return the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha256msg2_epu32)
 */
public fun mmSha256msg2Epu32(a: M128i, b: M128i): M128i = transmute(sha256msg2(a.asI32x4(), b.asI32x4()))

/**
 * Performs 2 rounds of SHA256 operation using an initial SHA256 state
 * (C,D,G,H) from `a`, an initial SHA256 state (A,B,E,F) from `b`, and a
 * pre-computed sum of the next 2 round message values (unsigned 32-bit
 * integers) and the corresponding round constants from `k`, and store the
 * updated SHA256 state (A,B,E,F) in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sha256rnds2_epu32)
 */
public fun mmSha256rnds2Epu32(a: M128i, b: M128i, k: M128i): M128i = transmute(sha256rnds2(a.asI32x4(), b.asI32x4(), k.asI32x4()))

/**
 * This intrinsic is one of the two SHA512 message scheduling instructions.
 * The intrinsic performs an intermediate calculation for the next four SHA512
 * message qwords. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sha512msg1_epi64)
 */
public fun mm256Sha512msg1Epi64(a: M256i, b: M128i): M256i = transmute(vsha512msg1(a.asI64x4(), b.asI64x2()))

/**
 * This intrinsic is one of the two SHA512 message scheduling instructions.
 * The intrinsic performs the final calculation for the next four SHA512 message
 * qwords. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sha512msg2_epi64)
 */
public fun mm256Sha512msg2Epi64(a: M256i, b: M256i): M256i = transmute(vsha512msg2(a.asI64x4(), b.asI64x4()))

/**
 * This intrinsic performs two rounds of SHA512 operation using initial SHA512 state
 * `(C, D, G, H)` from `a`, an initial SHA512 state `(A, B, E, F)` from `b`, and a
 * pre-computed sum of the next two round message qwords and the corresponding
 * round constants from `c` (only the two lower qwords of the third operand). The
 * updated SHA512 state `(A, B, E, F)` is written to dst, and dst can be used as the
 * updated state `(C, D, G, H)` in later rounds.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sha512rnds2_epi64)
 */
public fun mm256Sha512rnds2Epi64(a: M256i, b: M256i, k: M128i): M256i = transmute(vsha512rnds2(a.asI64x4(), b.asI64x4(), k.asI64x2()))

/**
 * This is one of the two SM3 message scheduling intrinsics. The intrinsic performs
 * an initial calculation for the next four SM3 message words. The calculated results
 * are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sm3msg1_epi32)
 */
public fun mmSm3msg1Epi32(a: M128i, b: M128i, c: M128i): M128i = transmute(vsm3msg1(a.asI32x4(), b.asI32x4(), c.asI32x4()))

/**
 * This is one of the two SM3 message scheduling intrinsics. The intrinsic performs
 * the final calculation for the next four SM3 message words. The calculated results
 * are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sm3msg2_epi32)
 */
public fun mmSm3msg2Epi32(a: M128i, b: M128i, c: M128i): M128i = transmute(vsm3msg2(a.asI32x4(), b.asI32x4(), c.asI32x4()))

/**
 * The intrinsic performs two rounds of SM3 operation using initial SM3 state `(C, D, G, H)`
 * from `a`, an initial SM3 states `(A, B, E, F)` from `b` and a pre-computed words from the
 * `c`. `a` with initial SM3 state of `(C, D, G, H)` assumes input of non-rotated left variables
 * from previous state. The updated SM3 state `(A, B, E, F)` is written to `a`. The `imm8`
 * should contain the even round number for the first of the two rounds computed by this instruction.
 * The computation masks the `imm8` value by ANDing it with `0x3E` so that only even round numbers
 * from 0 through 62 are used for this operation. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sm3rnds2_epi32)
 */
public fun mmSm3rnds2Epi32(a: M128i, b: M128i, c: M128i, imm8: Int): M128i {
    run {
        check(imm8 == (imm8 and 0x3e)) { "IMM8 must be an even number in the range `0..=62`" }
    }
    return transmute(vsm3rnds2(a.asI32x4(), b.asI32x4(), c.asI32x4(), imm8))
}

/**
 * This intrinsic performs four rounds of SM4 key expansion. The intrinsic operates on independent
 * 128-bit lanes. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sm4key4_epi32)
 */
public fun mmSm4key4Epi32(a: M128i, b: M128i): M128i = transmute(vsm4key4128(a.asI32x4(), b.asI32x4()))

/**
 * This intrinsic performs four rounds of SM4 key expansion. The intrinsic operates on independent
 * 128-bit lanes. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sm4key4_epi32)
 */
public fun mm256Sm4key4Epi32(a: M256i, b: M256i): M256i = transmute(vsm4key4256(a.asI32x8(), b.asI32x8()))

/**
 * This intrinsic performs four rounds of SM4 encryption. The intrinsic operates on independent
 * 128-bit lanes. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sm4rnds4_epi32)
 */
public fun mmSm4rnds4Epi32(a: M128i, b: M128i): M128i = transmute(vsm4rnds4128(a.asI32x4(), b.asI32x4()))

/**
 * This intrinsic performs four rounds of SM4 encryption. The intrinsic operates on independent
 * 128-bit lanes. The calculated results are stored in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sm4rnds4_epi32)
 */
public fun mm256Sm4rnds4Epi32(a: M256i, b: M256i): M256i = transmute(vsm4rnds4256(a.asI32x8(), b.asI32x8()))
