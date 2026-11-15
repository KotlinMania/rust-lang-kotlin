// port-lint: source x86/vpclmulqdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Vectorized Carry-less Multiplication (VCLMUL)
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref] (p. 4-241).
//
// [intel64Ref]: http://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public expect fun pclmulqdq256(a: M256i, roundKey: M256i, imm8: UByte): M256i

public expect fun pclmulqdq512(a: M512i, roundKey: M512i, imm8: UByte): M512i

// for some odd reason on x8664 we generate the correct long name instructions
// but on i686 we generate the short name + imm8
// so we need to special-case on that...
// technically according to Intel's documentation we don't need avx512f here, however LLVM gets confused otherwise

/**
 * Performs a carry-less multiplication of two 64-bit polynomials over the
 * finite field GF(2) - in each of the 4 128-bit lanes.
 *
 * The immediate byte is used for determining which halves of each lane `a` and `b`
 * should be used. Immediate bits other than 0 and 4 are ignored.
 * All lanes share immediate byte.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_clmulepi64_epi128)
 */
public fun mm512Clmulepi64Epi128(a: M512i, b: M512i, imm8: Int): M512i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pclmulqdq512(a, b, imm8.toUByte())
}

/**
 * Performs a carry-less multiplication of two 64-bit polynomials over the
 * finite field GF(2) - in each of the 2 128-bit lanes.
 *
 * The immediate byte is used for determining which halves of each lane `a` and `b`
 * should be used. Immediate bits other than 0 and 4 are ignored.
 * All lanes share immediate byte.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_clmulepi64_epi128)
 */
public fun mm256Clmulepi64Epi128(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pclmulqdq256(a, b, imm8.toUByte())
}
