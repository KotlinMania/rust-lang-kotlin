// port-lint: source x86/pclmulqdq.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.x86.M128i

// Carry-less Multiplication (CLMUL)
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref] (p. 4-241).
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public expect fun pclmulqdq(a: M128i, roundKey: M128i, imm8: UByte): M128i

/**
 * Performs a carry-less multiplication of two 64-bit polynomials over the
 * finite field GF(2).
 *
 * The immediate byte is used for determining which halves of `a` and `b`
 * should be used. Immediate bits other than 0 and 4 are ignored.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_clmulepi64_si128)
 */
public fun mmClmulepi64Si128(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return pclmulqdq(a, b, imm8.toUByte())
}
