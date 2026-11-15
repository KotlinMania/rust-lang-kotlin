// port-lint: source x86/gfni.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

// Galois Field New Instructions (GFNI)
//
// The intrinsics here correspond to those in the `immintrin.h` C header.
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf

public expect fun vgf2p8affineinvqb512(x: I8x64, a: I8x64, imm8: UByte): I8x64

public expect fun vgf2p8affineinvqb256(x: I8x32, a: I8x32, imm8: UByte): I8x32

public expect fun vgf2p8affineinvqb128(x: I8x16, a: I8x16, imm8: UByte): I8x16

public expect fun vgf2p8affineqb512(x: I8x64, a: I8x64, imm8: UByte): I8x64

public expect fun vgf2p8affineqb256(x: I8x32, a: I8x32, imm8: UByte): I8x32

public expect fun vgf2p8affineqb128(x: I8x16, a: I8x16, imm8: UByte): I8x16

public expect fun vgf2p8mulb512(a: I8x64, b: I8x64): I8x64

public expect fun vgf2p8mulb256(a: I8x32, b: I8x32): I8x32

public expect fun vgf2p8mulb128(a: I8x16, b: I8x16): I8x16

// LLVM requires AVX512BW for a lot of these instructions, see
// https://github.com/llvm/llvm-project/blob/release/9.x/clang/include/clang/Basic/BuiltinsX86.def#L457
// however our tests also require the target feature list to match Intel's
// which *doesn't* require AVX512BW but only AVX512F, so we added the redundant AVX512F
// requirement (for now)
// also see
// https://github.com/llvm/llvm-project/blob/release/9.x/clang/lib/Headers/gfniintrin.h
// for forcing GFNI, BW and optionally VL extension

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_gf2p8mul_epi8)
 */
public fun mm512Gf2p8mulEpi8(a: M512i, b: M512i): M512i = transmute(vgf2p8mulb512(a.asI8x64(), b.asI8x64()))

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_gf2p8mul_epi8)
 */
public fun mm512MaskGf2p8mulEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i = transmute(run { val _simdArg0 = k; val _simdArg1 = vgf2p8mulb512(a.asI8x64(), b.asI8x64()); val _simdArg2 = src.asI8x64(); _simdArg1 })

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_gf2p8mul_epi8)
 */
public fun mm512MaskzGf2p8mulEpi8(k: Mmask64, a: M512i, b: M512i): M512i {
    val zero = I8x64.ZERO
    return transmute(run { val _simdArg0 = k; val _simdArg1 = vgf2p8mulb512(a.asI8x64(), b.asI8x64()); val _simdArg2 = zero; _simdArg1 })
}

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_gf2p8mul_epi8)
 */
public fun mm256Gf2p8mulEpi8(a: M256i, b: M256i): M256i = transmute(vgf2p8mulb256(a.asI8x32(), b.asI8x32()))

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_gf2p8mul_epi8)
 */
public fun mm256MaskGf2p8mulEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i = transmute(run { val _simdArg0 = k; val _simdArg1 = vgf2p8mulb256(a.asI8x32(), b.asI8x32()); val _simdArg2 = src.asI8x32(); _simdArg1 })

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_gf2p8mul_epi8)
 */
public fun mm256MaskzGf2p8mulEpi8(k: Mmask32, a: M256i, b: M256i): M256i {
    val zero = I8x32.ZERO
    return transmute(run { val _simdArg0 = k; val _simdArg1 = vgf2p8mulb256(a.asI8x32(), b.asI8x32()); val _simdArg2 = zero; _simdArg1 })
}

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_gf2p8mul_epi8)
 */
public fun mmGf2p8mulEpi8(a: M128i, b: M128i): M128i = transmute(vgf2p8mulb128(a.asI8x16(), b.asI8x16()))

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_gf2p8mul_epi8)
 */
public fun mmMaskGf2p8mulEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i = transmute(run { val _simdArg0 = k; val _simdArg1 = vgf2p8mulb128(a.asI8x16(), b.asI8x16()); val _simdArg2 = src.asI8x16(); _simdArg1 })

/**
 * Performs a multiplication in GF(2^8) on the packed bytes.
 * The field is in polynomial representation with the reduction polynomial
 *  x^8 + x^4 + x^3 + x + 1.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_gf2p8mul_epi8)
 */
public fun mmMaskzGf2p8mulEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val zero = I8x16.ZERO
        transmute(run { val _simdArg0 = k; val _simdArg1 = vgf2p8mulb128(a.asI8x16(), b.asI8x16()); val _simdArg2 = zero; _simdArg1 })
    }

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_gf2p8affine_epi64_epi8)
 */
public fun mm512Gf2p8affineEpi64Epi8(x: M512i, a: M512i, b: Int): M512i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x64()
    val a1 = a.asI8x64()
    return run {
        val r = vgf2p8affineqb512(x1, a1, b)
        transmute(r)
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_gf2p8affine_epi64_epi8)
 */
public fun mm512MaskzGf2p8affineEpi64Epi8(k: Mmask64, x: M512i, a: M512i, b: Int): M512i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val zero = I8x64.ZERO
    val x1 = x.asI8x64()
    val a1 = a.asI8x64()
    return run {
        val r = vgf2p8affineqb512(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = zero; _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_gf2p8affine_epi64_epi8)
 */
public fun mm512MaskGf2p8affineEpi64Epi8(src: M512i, k: Mmask64, x: M512i, a: M512i, b: Int): M512i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x64()
    val a1 = a.asI8x64()
    return run {
        val r = vgf2p8affineqb512(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI8x64(); _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_gf2p8affine_epi64_epi8)
 */
public fun mm256Gf2p8affineEpi64Epi8(x: M256i, a: M256i, b: Int): M256i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x32()
    val a1 = a.asI8x32()
    return run {
        val r = vgf2p8affineqb256(x1, a1, b)
        transmute(r)
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_gf2p8affine_epi64_epi8)
 */
public fun mm256MaskzGf2p8affineEpi64Epi8(k: Mmask32, x: M256i, a: M256i, b: Int): M256i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val zero = I8x32.ZERO
    val x1 = x.asI8x32()
    val a1 = a.asI8x32()
    return run {
        val r = vgf2p8affineqb256(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = zero; _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_gf2p8affine_epi64_epi8)
 */
public fun mm256MaskGf2p8affineEpi64Epi8(src: M256i, k: Mmask32, x: M256i, a: M256i, b: Int): M256i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x32()
    val a1 = a.asI8x32()
    return run {
        val r = vgf2p8affineqb256(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI8x32(); _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_gf2p8affine_epi64_epi8)
 */
public fun mmGf2p8affineEpi64Epi8(x: M128i, a: M128i, b: Int): M128i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x16()
    val a1 = a.asI8x16()
    return run {
        val r = vgf2p8affineqb128(x1, a1, b)
        transmute(r)
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_gf2p8affine_epi64_epi8)
 */
public fun mmMaskzGf2p8affineEpi64Epi8(k: Mmask16, x: M128i, a: M128i, b: Int): M128i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val zero = I8x16.ZERO
    val x1 = x.asI8x16()
    val a1 = a.asI8x16()
    return run {
        val r = vgf2p8affineqb128(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = zero; _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the packed bytes in x.
 * That is computes a*x+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_gf2p8affine_epi64_epi8)
 */
public fun mmMaskGf2p8affineEpi64Epi8(src: M128i, k: Mmask16, x: M128i, a: M128i, b: Int): M128i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x16()
    val a1 = a.asI8x16()
    return run {
        val r = vgf2p8affineqb128(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI8x16(); _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_gf2p8affineinv_epi64_epi8)
 */
public fun mm512Gf2p8affineinvEpi64Epi8(x: M512i, a: M512i, b: Int): M512i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x64()
    val a1 = a.asI8x64()
    return run {
        val r = vgf2p8affineinvqb512(x1, a1, b)
        transmute(r)
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_gf2p8affineinv_epi64_epi8)
 */
public fun mm512MaskzGf2p8affineinvEpi64Epi8(k: Mmask64, x: M512i, a: M512i, b: Int): M512i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val zero = I8x64.ZERO
    val x1 = x.asI8x64()
    val a1 = a.asI8x64()
    return run {
        val r = vgf2p8affineinvqb512(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = zero; _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_gf2p8affineinv_epi64_epi8)
 */
public fun mm512MaskGf2p8affineinvEpi64Epi8(src: M512i, k: Mmask64, x: M512i, a: M512i, b: Int): M512i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x64()
    val a1 = a.asI8x64()
    return run {
        val r = vgf2p8affineinvqb512(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI8x64(); _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_gf2p8affineinv_epi64_epi8)
 */
public fun mm256Gf2p8affineinvEpi64Epi8(x: M256i, a: M256i, b: Int): M256i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x32()
    val a1 = a.asI8x32()
    return run {
        val r = vgf2p8affineinvqb256(x1, a1, b)
        transmute(r)
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_gf2p8affineinv_epi64_epi8)
 */
public fun mm256MaskzGf2p8affineinvEpi64Epi8(k: Mmask32, x: M256i, a: M256i, b: Int): M256i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val zero = I8x32.ZERO
    val x1 = x.asI8x32()
    val a1 = a.asI8x32()
    return run {
        val r = vgf2p8affineinvqb256(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = zero; _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_gf2p8affineinv_epi64_epi8)
 */
public fun mm256MaskGf2p8affineinvEpi64Epi8(src: M256i, k: Mmask32, x: M256i, a: M256i, b: Int): M256i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x32()
    val a1 = a.asI8x32()
    return run {
        val r = vgf2p8affineinvqb256(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI8x32(); _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_gf2p8affineinv_epi64_epi8)
 */
public fun mmGf2p8affineinvEpi64Epi8(x: M128i, a: M128i, b: Int): M128i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x16()
    val a1 = a.asI8x16()
    return run {
        val r = vgf2p8affineinvqb128(x1, a1, b)
        transmute(r)
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are zeroed in the result if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_gf2p8affineinv_epi64_epi8)
 */
public fun mmMaskzGf2p8affineinvEpi64Epi8(k: Mmask16, x: M128i, a: M128i, b: Int): M128i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val zero = I8x16.ZERO
    val x1 = x.asI8x16()
    val a1 = a.asI8x16()
    return run {
        val r = vgf2p8affineinvqb128(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = zero; _simdArg1 })
    }
}

/**
 * Performs an affine transformation on the inverted packed bytes in x.
 * That is computes a*inv(x)+b over the Galois Field 2^8 for each packed byte with a being a 8x8 bit matrix
 * and b being a constant 8-bit immediate value.
 * The inverse of a byte is defined with respect to the reduction polynomial x^8+x^4+x^3+x+1.
 * The inverse of 0 is 0.
 * Each pack of 8 bytes in x is paired with the 64-bit word at the same position in a.
 *
 * Uses the writemask in k - elements are copied from src if the corresponding mask bit is not set.
 * Otherwise the computation result is written into the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_gf2p8affineinv_epi64_epi8)
 */
public fun mmMaskGf2p8affineinvEpi64Epi8(src: M128i, k: Mmask16, x: M128i, a: M128i, b: Int): M128i {
    run {
        check(0 <= b && b < (1 shl 8)) { "B doesn't fit in 8 bits (unsigned)" }
    }
    val b = b.toUByte()
    val x1 = x.asI8x16()
    val a1 = a.asI8x16()
    return run {
        val r = vgf2p8affineinvqb128(x1, a1, b)
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src.asI8x16(); _simdArg1 })
    }
}
