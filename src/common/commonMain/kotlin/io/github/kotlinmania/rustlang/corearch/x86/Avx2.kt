// port-lint: source x86/avx2.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.arch.asm
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.M16x16
import io.github.kotlinmania.rustlang.corearch.simd.M32x8
import io.github.kotlinmania.rustlang.corearch.simd.M8x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x32
import io.github.kotlinmania.rustlang.corearch.simd.U8x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x8
import io.github.kotlinmania.rustlang.corearch.simd.simdImax
import io.github.kotlinmania.rustlang.corearch.simd.simdImin
import io.github.kotlinmania.rustlang.corearch.simdExtract
import io.github.kotlinmania.rustlang.corearch.simdMaskedLoad
import io.github.kotlinmania.rustlang.corearch.simdMaskedStore
import io.github.kotlinmania.rustlang.corearch.simdShuffle

// Advanced Vector Extensions 2 (AVX)
//
// AVX2 expands most AVX commands to 256-bit wide vector registers and
// adds [FMA](https://en.wikipedia.org/wiki/FusedMultiply-accumulate).
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref].
// - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
//   System Instructions][amd64Ref].
//
// Wikipedia's [AVX][wikiAvx] and [FMA][wikiFma] pages provide a quick
// overview of the instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wikiAvx]: https://en.wikipedia.org/wiki/AdvancedVectorExtensions
// [wikiFma]: https://en.wikipedia.org/wiki/FusedMultiply-accumulate

/**
 * Computes the absolute values of packed 32-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_abs_epi32)
 */
public fun mm256AbsEpi32(a: M256i): M256i =
    run {
        val a1 = a.asI32x8()
        val r = simdSelect<M32x8>(simdLt(a1, i32x8.ZERO), simdNeg(a1), a1)
        transmute(r)
    }

/**
 * Computes the absolute values of packed 16-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_abs_epi16)
 */
public fun mm256AbsEpi16(a: M256i): M256i =
    run {
        val a1 = a.asI16x16()
        val r = simdSelect<M16x16>(simdLt(a1, i16x16.ZERO), simdNeg(a1), a1)
        transmute(r)
    }

/**
 * Computes the absolute values of packed 8-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_abs_epi8)
 */
public fun mm256AbsEpi8(a: M256i): M256i =
    run {
        val a1 = a.asI8x32()
        val r = simdSelect<M8x32>(simdLt(a1, i8x32.ZERO), simdNeg(a1), a1)
        transmute(r)
    }

/**
 * Adds packed 64-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_add_epi64)
 */
public fun mm256AddEpi64(a: M256i, b: M256i): M256i = transmute(simdAdd(a.asI64x4(), b.asI64x4()))

/**
 * Adds packed 32-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_add_epi32)
 */
public fun mm256AddEpi32(a: M256i, b: M256i): M256i = transmute(simdAdd(a.asI32x8(), b.asI32x8()))

/**
 * Adds packed 16-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_add_epi16)
 */
public fun mm256AddEpi16(a: M256i, b: M256i): M256i = transmute(simdAdd(a.asI16x16(), b.asI16x16()))

/**
 * Adds packed 8-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_add_epi8)
 */
public fun mm256AddEpi8(a: M256i, b: M256i): M256i = transmute(simdAdd(a.asI8x32(), b.asI8x32()))

/**
 * Adds packed 8-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_adds_epi8)
 */
public fun mm256AddsEpi8(a: M256i, b: M256i): M256i = transmute(simdSaturatingAdd(a.asI8x32(), b.asI8x32()))

/**
 * Adds packed 16-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_adds_epi16)
 */
public fun mm256AddsEpi16(a: M256i, b: M256i): M256i = transmute(simdSaturatingAdd(a.asI16x16(), b.asI16x16()))

/**
 * Adds packed unsigned 8-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_adds_epu8)
 */
public fun mm256AddsEpu8(a: M256i, b: M256i): M256i = transmute(simdSaturatingAdd(a.asU8x32(), b.asU8x32()))

/**
 * Adds packed unsigned 16-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_adds_epu16)
 */
public fun mm256AddsEpu16(a: M256i, b: M256i): M256i = transmute(simdSaturatingAdd(a.asU16x16(), b.asU16x16()))

/**
 * Concatenates pairs of 16-byte blocks in `a` and `b` into a 32-byte temporary
 * result, shifts the result right by `n` bytes, and returns the low 16 bytes.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_alignr_epi8)
 */
public fun mm256AlignrEpi8(a: M256i, b: M256i, imm8: Int): M256i {
    fun mask(shift: UInt, i: UInt): UInt {
        val shift1 = shift % 16u
        val modI = i % 16u
        return if (modI < (16u - shift1)) {
            i + shift1
        } else {
            i + 16u + shift1
        }
    }
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    // If palignr is shifting the pair of vectors more than the size of two
    // lanes, emit zero.
    if (imm8 >= 32) {
        return mm256SetzeroSi256()
    }
    // If palignr is shifting the pair of input vectors more than one lane,
    // but less than two lanes, convert to shifting in zeroes.
    val (a1, b1) =
        if (imm8 > 16) {
            Pair(mm256SetzeroSi256(), a)
        } else {
            Pair(a, b)
        }
    if (imm8 == 16) {
        return transmute(a1)
    }
    return run {
        val r: I8x32 = simdShuffle(b1.asI8x32(), a1.asI8x32(), SimdShuffleIdx(listOf(mask(imm8.toUInt(), 0u), mask(imm8.toUInt(), 1u), mask(imm8.toUInt(), 2u), mask(imm8.toUInt(), 3u), mask(imm8.toUInt(), 4u), mask(imm8.toUInt(), 5u), mask(imm8.toUInt(), 6u), mask(imm8.toUInt(), 7u), mask(imm8.toUInt(), 8u), mask(imm8.toUInt(), 9u), mask(imm8.toUInt(), 10u), mask(imm8.toUInt(), 11u), mask(imm8.toUInt(), 12u), mask(imm8.toUInt(), 13u), mask(imm8.toUInt(), 14u), mask(imm8.toUInt(), 15u), mask(imm8.toUInt(), 16u), mask(imm8.toUInt(), 17u), mask(imm8.toUInt(), 18u), mask(imm8.toUInt(), 19u), mask(imm8.toUInt(), 20u), mask(imm8.toUInt(), 21u), mask(imm8.toUInt(), 22u), mask(imm8.toUInt(), 23u), mask(imm8.toUInt(), 24u), mask(imm8.toUInt(), 25u), mask(imm8.toUInt(), 26u), mask(imm8.toUInt(), 27u), mask(imm8.toUInt(), 28u), mask(imm8.toUInt(), 29u), mask(imm8.toUInt(), 30u), mask(imm8.toUInt(), 31u))))
        transmute(r)
    }
}

/**
 * Computes the bitwise AND of 256 bits (representing integer data)
 * in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_and_si256)
 */
public fun mm256AndSi256(a: M256i, b: M256i): M256i = transmute(simdAnd(a.asI64x4(), b.asI64x4()))

/**
 * Computes the bitwise NOT of 256 bits (representing integer data)
 * in `a` and then AND with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_andnot_si256)
 */
public fun mm256AndnotSi256(a: M256i, b: M256i): M256i =
    run {
        val allOnes = mm256Set1Epi8((-1).toByte())
        transmute(simdAnd(simdXor(a.asI64x4(), allOnes.asI64x4()), b.asI64x4()))
    }

/**
 * Averages packed unsigned 16-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_avg_epu16)
 */
public fun mm256AvgEpu16(a: M256i, b: M256i): M256i = transmute(pavgw(a.asU16x16(), b.asU16x16()))

/**
 * Averages packed unsigned 8-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_avg_epu8)
 */
public fun mm256AvgEpu8(a: M256i, b: M256i): M256i = transmute(pavgb(a.asU8x32(), b.asU8x32()))

/**
 * Blends packed 32-bit integers from `a` and `b` using control mask `IMM4`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blend_epi32)
 */
public fun mmBlendEpi32(a: M128i, b: M128i, imm4: Int): M128i {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI32x4()
        val b1 = b.asI32x4()
        val r: I32x4 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(listOf(0, 4, 0, 4)[imm4 and 0x3], listOf(1, 1, 5, 5)[imm4 and 0x3], listOf(2, 6, 2, 6)[(imm4 shr 2) and 0x3], listOf(3, 3, 7, 7)[(imm4 shr 2) and 0x3])))
        transmute(r)
    }
}

/**
 * Blends packed 32-bit integers from `a` and `b` using control mask `IMM8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blend_epi32)
 */
public fun mm256BlendEpi32(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI32x8()
        val b1 = b.asI32x8()
        val r: I32x8 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(listOf(0, 8, 0, 8)[imm8 and 0x3], listOf(1, 1, 9, 9)[imm8 and 0x3], listOf(2, 10, 2, 10)[(imm8 shr 2) and 0x3], listOf(3, 3, 11, 11)[(imm8 shr 2) and 0x3], listOf(4, 12, 4, 12)[(imm8 shr 4) and 0x3], listOf(5, 5, 13, 13)[(imm8 shr 4) and 0x3], listOf(6, 14, 6, 14)[(imm8 shr 6) and 0x3], listOf(7, 7, 15, 15)[(imm8 shr 6) and 0x3])))
        transmute(r)
    }
}

/**
 * Blends packed 16-bit integers from `a` and `b` using control mask `IMM8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blend_epi16)
 */
public fun mm256BlendEpi16(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI16x16()
        val b1 = b.asI16x16()
        val r: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(listOf(0, 16, 0, 16)[imm8 and 0x3], listOf(1, 1, 17, 17)[imm8 and 0x3], listOf(2, 18, 2, 18)[(imm8 shr 2) and 0x3], listOf(3, 3, 19, 19)[(imm8 shr 2) and 0x3], listOf(4, 20, 4, 20)[(imm8 shr 4) and 0x3], listOf(5, 5, 21, 21)[(imm8 shr 4) and 0x3], listOf(6, 22, 6, 22)[(imm8 shr 6) and 0x3], listOf(7, 7, 23, 23)[(imm8 shr 6) and 0x3], listOf(8, 24, 8, 24)[imm8 and 0x3], listOf(9, 9, 25, 25)[imm8 and 0x3], listOf(10, 26, 10, 26)[(imm8 shr 2) and 0x3], listOf(11, 11, 27, 27)[(imm8 shr 2) and 0x3], listOf(12, 28, 12, 28)[(imm8 shr 4) and 0x3], listOf(13, 13, 29, 29)[(imm8 shr 4) and 0x3], listOf(14, 30, 14, 30)[(imm8 shr 6) and 0x3], listOf(15, 15, 31, 31)[(imm8 shr 6) and 0x3])))
        transmute(r)
    }
}

/**
 * Blends packed 8-bit integers from `a` and `b` using `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blendv_epi8)
 */
public fun mm256BlendvEpi8(a: M256i, b: M256i, mask: M256i): M256i =
    run {
        val mask1: I8x32 = simdLt(mask.asI8x32(), i8x32.ZERO)
        transmute(simdSelect(mask1, b.asI8x32(), a.asI8x32()))
    }

/**
 * Broadcasts the low packed 8-bit integer from `a` to all elements of
 * the 128-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastb_epi8)
 */
public fun mmBroadcastbEpi8(a: M128i): M128i =
    run {
        val ret = simdShuffle(a.asI8x16(), i8x16.ZERO, SimdShuffleIdx(UIntArray(16) { 0u }))
        transmute<I8x16>(ret)
    }

/**
 * Broadcasts the low packed 8-bit integer from `a` to all elements of
 * the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastb_epi8)
 */
public fun mm256BroadcastbEpi8(a: M128i): M256i =
    run {
        val ret = simdShuffle(a.asI8x16(), i8x16.ZERO, SimdShuffleIdx(UIntArray(32) { 0u }))
        transmute<I8x32>(ret)
    }

// N.B., `simdShuffle4` with integer data types for `a` and `b` is
// often compiled to `vbroadcastss`.

/**
 * Broadcasts the low packed 32-bit integer from `a` to all elements of
 * the 128-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastd_epi32)
 */
public fun mmBroadcastdEpi32(a: M128i): M128i =
    run {
        val ret = simdShuffle(a.asI32x4(), i32x4.ZERO, SimdShuffleIdx(UIntArray(4) { 0u }))
        transmute<I32x4>(ret)
    }

// N.B., `simdShuffle4``with integer data types for`a`and`b` is
// often compiled to `vbroadcastss`.

/**
 * Broadcasts the low packed 32-bit integer from `a` to all elements of
 * the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastd_epi32)
 */
public fun mm256BroadcastdEpi32(a: M128i): M256i =
    run {
        val ret = simdShuffle(a.asI32x4(), i32x4.ZERO, SimdShuffleIdx(UIntArray(8) { 0u }))
        transmute<I32x8>(ret)
    }

// Emits `vmovddup` instead of `vpbroadcastq`
// See https://github.com/rust-lang/stdarch/issues/791

/**
 * Broadcasts the low packed 64-bit integer from `a` to all elements of
 * the 128-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastq_epi64)
 */
public fun mmBroadcastqEpi64(a: M128i): M128i =
    run {
        val ret = simdShuffle(a.asI64x2(), a.asI64x2(), SimdShuffleIdx(UIntArray(2) { 0u }))
        transmute<I64x2>(ret)
    }

/**
 * Broadcasts the low packed 64-bit integer from `a` to all elements of
 * the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastq_epi64)
 */
public fun mm256BroadcastqEpi64(a: M128i): M256i =
    run {
        val ret = simdShuffle(a.asI64x2(), a.asI64x2(), SimdShuffleIdx(UIntArray(4) { 0u }))
        transmute<I64x4>(ret)
    }

/**
 * Broadcasts the low double-precision (64-bit) floating-point element
 * from `a` to all elements of the 128-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastsd_pd)
 */
public fun mmBroadcastsdPd(a: M128d): M128d = simdShuffle(a, mmSetzeroPd(), SimdShuffleIdx(UIntArray(2) { 0u }))

/**
 * Broadcasts the low double-precision (64-bit) floating-point element
 * from `a` to all elements of the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastsd_pd)
 */
public fun mm256BroadcastsdPd(a: M128d): M256d = simdShuffle(a, mmSetzeroPd(), SimdShuffleIdx(UIntArray(4) { 0u }))

/**
 * Broadcasts 128 bits of integer data from a to all 128-bit lanes in
 * the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastsi128_si256)
 */
public fun mmBroadcastsi128Si256(a: M128i): M256i =
    run {
        val ret = simdShuffle(a.asI64x2(), i64x2.ZERO, SimdShuffleIdx(listOf(0, 1, 0, 1)))
        transmute<I64x4>(ret)
    }

// N.B., `broadcastsi128Si256` is often compiled to `vinsertf128` or
// `vbroadcastf128`.

/**
 * Broadcasts 128 bits of integer data from a to all 128-bit lanes in
 * the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastsi128_si256)
 */
public fun mm256Broadcastsi128Si256(a: M128i): M256i =
    run {
        val ret = simdShuffle(a.asI64x2(), i64x2.ZERO, SimdShuffleIdx(listOf(0, 1, 0, 1)))
        transmute<I64x4>(ret)
    }

/**
 * Broadcasts the low single-precision (32-bit) floating-point element
 * from `a` to all elements of the 128-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastss_ps)
 */
public fun mmBroadcastssPs(a: M128): M128 = simdShuffle(a, mmSetzeroPs(), SimdShuffleIdx(UIntArray(4) { 0u }))

/**
 * Broadcasts the low single-precision (32-bit) floating-point element
 * from `a` to all elements of the 256-bit returned value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastss_ps)
 */
public fun mm256BroadcastssPs(a: M128): M256 = simdShuffle(a, mmSetzeroPs(), SimdShuffleIdx(UIntArray(8) { 0u }))

/**
 * Broadcasts the low packed 16-bit integer from a to all elements of
 * the 128-bit returned value
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastw_epi16)
 */
public fun mmBroadcastwEpi16(a: M128i): M128i =
    run {
        val ret = simdShuffle(a.asI16x8(), i16x8.ZERO, SimdShuffleIdx(UIntArray(8) { 0u }))
        transmute<I16x8>(ret)
    }

/**
 * Broadcasts the low packed 16-bit integer from a to all elements of
 * the 256-bit returned value
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastw_epi16)
 */
public fun mm256BroadcastwEpi16(a: M128i): M256i =
    run {
        val ret = simdShuffle(a.asI16x8(), i16x8.ZERO, SimdShuffleIdx(UIntArray(16) { 0u }))
        transmute<I16x16>(ret)
    }

/**
 * Compares packed 64-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epi64)
 */
public fun mm256CmpeqEpi64(a: M256i, b: M256i): M256i = transmute<I64x4>(simdEq(a.asI64x4(), b.asI64x4()))

/**
 * Compares packed 32-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epi32)
 */
public fun mm256CmpeqEpi32(a: M256i, b: M256i): M256i = transmute<I32x8>(simdEq(a.asI32x8(), b.asI32x8()))

/**
 * Compares packed 16-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epi16)
 */
public fun mm256CmpeqEpi16(a: M256i, b: M256i): M256i = transmute<I16x16>(simdEq(a.asI16x16(), b.asI16x16()))

/**
 * Compares packed 8-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epi8)
 */
public fun mm256CmpeqEpi8(a: M256i, b: M256i): M256i = transmute<I8x32>(simdEq(a.asI8x32(), b.asI8x32()))

/**
 * Compares packed 64-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epi64)
 */
public fun mm256CmpgtEpi64(a: M256i, b: M256i): M256i = transmute<I64x4>(simdGt(a.asI64x4(), b.asI64x4()))

/**
 * Compares packed 32-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epi32)
 */
public fun mm256CmpgtEpi32(a: M256i, b: M256i): M256i = transmute<I32x8>(simdGt(a.asI32x8(), b.asI32x8()))

/**
 * Compares packed 16-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epi16)
 */
public fun mm256CmpgtEpi16(a: M256i, b: M256i): M256i = transmute<I16x16>(simdGt(a.asI16x16(), b.asI16x16()))

/**
 * Compares packed 8-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epi8)
 */
public fun mm256CmpgtEpi8(a: M256i, b: M256i): M256i = transmute<I8x32>(simdGt(a.asI8x32(), b.asI8x32()))

/**
 * Sign-extend 16-bit integers to 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi16_epi32)
 */
public fun mm256Cvtepi16Epi32(a: M128i): M256i = transmute<I32x8>(simdCast(a.asI16x8()))

/**
 * Sign-extend 16-bit integers to 64-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi16_epi64)
 */
public fun mm256Cvtepi16Epi64(a: M128i): M256i =
    run {
        val a1 = a.asI16x8()
        val v64: I16x4 = simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1, 2, 3)))
        transmute<I64x4>(simdCast(v64))
    }

/**
 * Sign-extend 32-bit integers to 64-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi32_epi64)
 */
public fun mm256Cvtepi32Epi64(a: M128i): M256i = transmute<I64x4>(simdCast(a.asI32x4()))

/**
 * Sign-extend 8-bit integers to 16-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi8_epi16)
 */
public fun mm256Cvtepi8Epi16(a: M128i): M256i = transmute<I16x16>(simdCast(a.asI8x16()))

/**
 * Sign-extend 8-bit integers to 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi8_epi32)
 */
public fun mm256Cvtepi8Epi32(a: M128i): M256i =
    run {
        val a1 = a.asI8x16()
        val v64: I8x8 = simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)))
        transmute<I32x8>(simdCast(v64))
    }

/**
 * Sign-extend 8-bit integers to 64-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi8_epi64)
 */
public fun mm256Cvtepi8Epi64(a: M128i): M256i =
    run {
        val a1 = a.asI8x16()
        val v32: I8x4 = simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1, 2, 3)))
        transmute<I64x4>(simdCast(v32))
    }

/**
 * Zeroes extend packed unsigned 16-bit integers in `a` to packed 32-bit
 * integers, and stores the results in `dst`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu16_epi32)
 */
public fun mm256Cvtepu16Epi32(a: M128i): M256i = transmute<I32x8>(simdCast(a.asU16x8()))

/**
 * Zero-extend the lower four unsigned 16-bit integers in `a` to 64-bit
 * integers. The upper four elements of `a` are unused.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu16_epi64)
 */
public fun mm256Cvtepu16Epi64(a: M128i): M256i =
    run {
        val a1 = a.asU16x8()
        val v64: U16x4 = simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1, 2, 3)))
        transmute<I64x4>(simdCast(v64))
    }

/**
 * Zero-extend unsigned 32-bit integers in `a` to 64-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu32_epi64)
 */
public fun mm256Cvtepu32Epi64(a: M128i): M256i = transmute<I64x4>(simdCast(a.asU32x4()))

/**
 * Zero-extend unsigned 8-bit integers in `a` to 16-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu8_epi16)
 */
public fun mm256Cvtepu8Epi16(a: M128i): M256i = transmute<I16x16>(simdCast(a.asU8x16()))

/**
 * Zero-extend the lower eight unsigned 8-bit integers in `a` to 32-bit
 * integers. The upper eight elements of `a` are unused.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu8_epi32)
 */
public fun mm256Cvtepu8Epi32(a: M128i): M256i =
    run {
        val a1 = a.asU8x16()
        val v64: U8x8 = simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)))
        transmute<I32x8>(simdCast(v64))
    }

/**
 * Zero-extend the lower four unsigned 8-bit integers in `a` to 64-bit
 * integers. The upper twelve elements of `a` are unused.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu8_epi64)
 */
public fun mm256Cvtepu8Epi64(a: M128i): M256i =
    run {
        val a1 = a.asU8x16()
        val v32: U8x4 = simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1, 2, 3)))
        transmute<I64x4>(simdCast(v32))
    }

/**
 * Extracts 128 bits (of integer data) from `a` selected with `IMM1`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extracti128_si256)
 */
public fun mm256Extracti128Si256(a: M256i, imm1: Int): M128i {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI64x4()
        val b = i64x4.ZERO
        val dst: I64x2 = simdShuffle(a1, b, SimdShuffleIdx(listOf(listOf(0, 1), listOf(2, 3))[imm1]))
        transmute(dst)
    }
}

/**
 * Horizontally adds adjacent pairs of 16-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hadd_epi16)
 */
public fun mm256HaddEpi16(a: M256i, b: M256i): M256i {
    val a1 = a.asI16x16()
    val b1 = b.asI16x16()
    return run {
        val even: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(0, 2, 4, 6, 16, 18, 20, 22, 8, 10, 12, 14, 24, 26, 28, 30)))
        val odd: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(1, 3, 5, 7, 17, 19, 21, 23, 9, 11, 13, 15, 25, 27, 29, 31)))
        simdAdd(even, odd).asM256i()
    }
}

/**
 * Horizontally adds adjacent pairs of 32-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hadd_epi32)
 */
public fun mm256HaddEpi32(a: M256i, b: M256i): M256i {
    val a1 = a.asI32x8()
    val b1 = b.asI32x8()
    return run {
        val even: I32x8 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(0, 2, 8, 10, 4, 6, 12, 14)))
        val odd: I32x8 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(1, 3, 9, 11, 5, 7, 13, 15)))
        simdAdd(even, odd).asM256i()
    }
}

/**
 * Horizontally adds adjacent pairs of 16-bit integers in `a` and `b`
 * using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hadds_epi16)
 */
public fun mm256HaddsEpi16(a: M256i, b: M256i): M256i {
    val a1 = a.asI16x16()
    val b1 = b.asI16x16()
    return run {
        val even: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(0, 2, 4, 6, 16, 18, 20, 22, 8, 10, 12, 14, 24, 26, 28, 30)))
        val odd: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(1, 3, 5, 7, 17, 19, 21, 23, 9, 11, 13, 15, 25, 27, 29, 31)))
        simdSaturatingAdd(even, odd).asM256i()
    }
}

/**
 * Horizontally subtract adjacent pairs of 16-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hsub_epi16)
 */
public fun mm256HsubEpi16(a: M256i, b: M256i): M256i {
    val a1 = a.asI16x16()
    val b1 = b.asI16x16()
    return run {
        val even: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(0, 2, 4, 6, 16, 18, 20, 22, 8, 10, 12, 14, 24, 26, 28, 30)))
        val odd: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(1, 3, 5, 7, 17, 19, 21, 23, 9, 11, 13, 15, 25, 27, 29, 31)))
        simdSub(even, odd).asM256i()
    }
}

/**
 * Horizontally subtract adjacent pairs of 32-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hsub_epi32)
 */
public fun mm256HsubEpi32(a: M256i, b: M256i): M256i {
    val a1 = a.asI32x8()
    val b1 = b.asI32x8()
    return run {
        val even: I32x8 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(0, 2, 8, 10, 4, 6, 12, 14)))
        val odd: I32x8 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(1, 3, 9, 11, 5, 7, 13, 15)))
        simdSub(even, odd).asM256i()
    }
}

/**
 * Horizontally subtract adjacent pairs of 16-bit integers in `a` and `b`
 * using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hsubs_epi16)
 */
public fun mm256HsubsEpi16(a: M256i, b: M256i): M256i {
    val a1 = a.asI16x16()
    val b1 = b.asI16x16()
    return run {
        val even: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(0, 2, 4, 6, 16, 18, 20, 22, 8, 10, 12, 14, 24, 26, 28, 30)))
        val odd: I16x16 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(1, 3, 5, 7, 17, 19, 21, 23, 9, 11, 13, 15, 25, 27, 29, 31)))
        simdSaturatingSub(even, odd).asM256i()
    }
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i32gather_epi32)
 */
public fun mmI32gatherEpi32(slice: ConstPtr, offsets: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i32x4.ZERO
    val negOne = mmSet1Epi32(-1).asI32x4()
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherdd(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i32gather_epi32)
 */
public fun mmMaskI32gatherEpi32(src: M128i, slice: ConstPtr, offsets: M128i, mask: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI32x4()
    val mask1 = mask.asI32x4()
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherdd(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i32gather_epi32)
 */
public fun mm256I32gatherEpi32(slice: ConstPtr, offsets: M256i, scale: Int): M256i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i32x8.ZERO
    val negOne = mm256Set1Epi32(-1).asI32x8()
    val offsets1 = offsets.asI32x8()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherdd(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i32gather_epi32)
 */
public fun mm256MaskI32gatherEpi32(src: M256i, slice: ConstPtr, offsets: M256i, mask: M256i, scale: Int): M256i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI32x8()
    val mask1 = mask.asI32x8()
    val offsets1 = offsets.asI32x8()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherdd(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i32gather_ps)
 */
public fun mmI32gatherPs(slice: ConstPtr, offsets: M128i, scale: Int): M128 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mmSetzeroPs()
    val negOne = mmSet1Ps(-1.0f)
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return pgatherdps(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i32gather_ps)
 */
public fun mmMaskI32gatherPs(src: M128, slice: ConstPtr, offsets: M128i, mask: M128, scale: Int): M128 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return pgatherdps(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i32gather_ps)
 */
public fun mm256I32gatherPs(slice: ConstPtr, offsets: M256i, scale: Int): M256 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mm256SetzeroPs()
    val negOne = mm256Set1Ps(-1.0f)
    val offsets1 = offsets.asI32x8()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return vpgatherdps(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i32gather_ps)
 */
public fun mm256MaskI32gatherPs(src: M256, slice: ConstPtr, offsets: M256i, mask: M256, scale: Int): M256 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val offsets1 = offsets.asI32x8()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return vpgatherdps(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i32gather_epi64)
 */
public fun mmI32gatherEpi64(slice: ConstPtr, offsets: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i64x2.ZERO
    val negOne = mmSet1Epi64x(-1L).asI64x2()
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherdq(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i32gather_epi64)
 */
public fun mmMaskI32gatherEpi64(src: M128i, slice: ConstPtr, offsets: M128i, mask: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI64x2()
    val mask1 = mask.asI64x2()
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherdq(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i32gather_epi64)
 */
public fun mm256I32gatherEpi64(slice: ConstPtr, offsets: M128i, scale: Int): M256i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i64x4.ZERO
    val negOne = mm256Set1Epi64x(-1L).asI64x4()
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherdq(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i32gather_epi64)
 */
public fun mm256MaskI32gatherEpi64(src: M256i, slice: ConstPtr, offsets: M128i, mask: M256i, scale: Int): M256i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI64x4()
    val mask1 = mask.asI64x4()
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherdq(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i32gather_pd)
 */
public fun mmI32gatherPd(slice: ConstPtr, offsets: M128i, scale: Int): M128d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mmSetzeroPd()
    val negOne = mmSet1Pd(-1.0)
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return pgatherdpd(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i32gather_pd)
 */
public fun mmMaskI32gatherPd(src: M128d, slice: ConstPtr, offsets: M128i, mask: M128d, scale: Int): M128d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return pgatherdpd(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i32gather_pd)
 */
public fun mm256I32gatherPd(slice: ConstPtr, offsets: M128i, scale: Int): M256d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mm256SetzeroPd()
    val negOne = mm256Set1Pd(-1.0)
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return vpgatherdpd(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i32gather_pd)
 */
public fun mm256MaskI32gatherPd(src: M256d, slice: ConstPtr, offsets: M128i, mask: M256d, scale: Int): M256d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val offsets1 = offsets.asI32x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return vpgatherdpd(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i64gather_epi32)
 */
public fun mmI64gatherEpi32(slice: ConstPtr, offsets: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i32x4.ZERO
    val negOne = mmSet1Epi64x(-1L).asI32x4()
    val offsets1 = offsets.asI64x2()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherqd(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i64gather_epi32)
 */
public fun mmMaskI64gatherEpi32(src: M128i, slice: ConstPtr, offsets: M128i, mask: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI32x4()
    val mask1 = mask.asI32x4()
    val offsets1 = offsets.asI64x2()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherqd(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i64gather_epi32)
 */
public fun mm256I64gatherEpi32(slice: ConstPtr, offsets: M256i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i32x4.ZERO
    val negOne = mmSet1Epi64x(-1L).asI32x4()
    val offsets1 = offsets.asI64x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherqd(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i64gather_epi32)
 */
public fun mm256MaskI64gatherEpi32(src: M128i, slice: ConstPtr, offsets: M256i, mask: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI32x4()
    val mask1 = mask.asI32x4()
    val offsets1 = offsets.asI64x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherqd(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i64gather_ps)
 */
public fun mmI64gatherPs(slice: ConstPtr, offsets: M128i, scale: Int): M128 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mmSetzeroPs()
    val negOne = mmSet1Ps(-1.0f)
    val offsets1 = offsets.asI64x2()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return pgatherqps(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i64gather_ps)
 */
public fun mmMaskI64gatherPs(src: M128, slice: ConstPtr, offsets: M128i, mask: M128, scale: Int): M128 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val offsets1 = offsets.asI64x2()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return pgatherqps(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i64gather_ps)
 */
public fun mm256I64gatherPs(slice: ConstPtr, offsets: M256i, scale: Int): M128 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mmSetzeroPs()
    val negOne = mmSet1Ps(-1.0f)
    val offsets1 = offsets.asI64x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return vpgatherqps(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i64gather_ps)
 */
public fun mm256MaskI64gatherPs(src: M128, slice: ConstPtr, offsets: M256i, mask: M128, scale: Int): M128 {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val offsets1 = offsets.asI64x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    return vpgatherqps(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i64gather_epi64)
 */
public fun mmI64gatherEpi64(slice: ConstPtr, offsets: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i64x2.ZERO
    val negOne = mmSet1Epi64x(-1L).asI64x2()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val offsets1 = offsets.asI64x2()
    val r = pgatherqq(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i64gather_epi64)
 */
public fun mmMaskI64gatherEpi64(src: M128i, slice: ConstPtr, offsets: M128i, mask: M128i, scale: Int): M128i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI64x2()
    val mask1 = mask.asI64x2()
    val offsets1 = offsets.asI64x2()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = pgatherqq(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i64gather_epi64)
 */
public fun mm256I64gatherEpi64(slice: ConstPtr, offsets: M256i, scale: Int): M256i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = i64x4.ZERO
    val negOne = mm256Set1Epi64x(-1L).asI64x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val offsets1 = offsets.asI64x4()
    val r = vpgatherqq(zero, slice1, offsets1, negOne, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i64gather_epi64)
 */
public fun mm256MaskI64gatherEpi64(src: M256i, slice: ConstPtr, offsets: M256i, mask: M256i, scale: Int): M256i {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val src1 = src.asI64x4()
    val mask1 = mask.asI64x4()
    val offsets1 = offsets.asI64x4()
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val r = vpgatherqq(src1, slice1, offsets1, mask1, scale.toByte())
    return transmute(r)
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_i64gather_pd)
 */
public fun mmI64gatherPd(slice: ConstPtr, offsets: M128i, scale: Int): M128d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mmSetzeroPd()
    val negOne = mmSet1Pd(-1.0)
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val offsets1 = offsets.asI64x2()
    return pgatherqpd(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_i64gather_pd)
 */
public fun mmMaskI64gatherPd(src: M128d, slice: ConstPtr, offsets: M128i, mask: M128d, scale: Int): M128d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val offsets1 = offsets.asI64x2()
    return pgatherqpd(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_i64gather_pd)
 */
public fun mm256I64gatherPd(slice: ConstPtr, offsets: M256i, scale: Int): M256d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val zero = mm256SetzeroPd()
    val negOne = mm256Set1Pd(-1.0)
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val offsets1 = offsets.asI64x4()
    return vpgatherqpd(zero, slice1, offsets1, negOne, scale.toByte())
}

/**
 * Returns values from `slice` at offsets determined by `offsets * scale`,
 * where
 * `scale` should be 1, 2, 4 or 8. If mask is set, load the value from `src` in
 * that position instead.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_i64gather_pd)
 */
public fun mm256MaskI64gatherPd(src: M256d, slice: ConstPtr, offsets: M256i, mask: M256d, scale: Int): M256d {
    run {
        check(scale == 1 || scale == 2 || scale == 4 || scale == 8) { "Invalid SCALE value" }
    }
    val slice1 = ConstPtr(slice.rawAddress, slice.rawMeta)
    val offsets1 = offsets.asI64x4()
    return vpgatherqpd(src, slice1, offsets1, mask, scale.toByte())
}

/**
 * Copies `a` to `dst`, then insert 128 bits (of integer data) from `b` at the
 * location specified by `IMM1`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_inserti128_si256)
 */
public fun mm256Inserti128Si256(a: M256i, b: M128i, imm1: Int): M256i {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI64x4()
        val b1 = mm256Castsi128Si256(b).asI64x4()
        val dst: I64x4 = simdShuffle(a1, b1, SimdShuffleIdx(listOf(listOf(4, 5, 2, 3), listOf(0, 1, 4, 5))[imm1]))
        transmute(dst)
    }
}

/**
 * Multiplies packed signed 16-bit integers in `a` and `b`, producing
 * intermediate signed 32-bit integers. Horizontally add adjacent pairs
 * of intermediate 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_madd_epi16)
 */
public fun mm256MaddEpi16(a: M256i, b: M256i): M256i {
    // It's a trick used in the Adler-32 algorithm to perform a widening addition.
    //
    // `rust
    // @targetFeature(enable = "avx2")
    // unchecked fn wideningAdd(mad: __m256i) -> __m256i {
    // _mm256_madd_epi16(mad, _mm256_set1_epi16(1))
    // }
    // `
    //
    // If we implement this using generic vector intrinsics, the optimizer
    // will eliminate this pattern, and `vpmaddwd` will no longer be emitted.
    // For this reason, we use x86 intrinsics.
    return transmute(pmaddwd(a.asI16x16(), b.asI16x16()))
}

/**
 * Vertically multiplies each unsigned 8-bit integer from `a` with the
 * corresponding signed 8-bit integer from `b`, producing intermediate
 * signed 16-bit integers. Horizontally add adjacent pairs of intermediate
 * signed 16-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maddubs_epi16)
 */
public fun mm256MaddubsEpi16(a: M256i, b: M256i): M256i = transmute(pmaddubsw(a.asU8x32(), b.asI8x32()))

/**
 * Loads packed 32-bit integers from memory pointed by `memAddr` using `mask`
 * (elements are zeroed out when the highest bit is not set in the
 * corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskload_epi32)
 */
public fun mmMaskloadEpi32(memAddr: ConstPtr, mask: M128i): M128i {
    val mask1 = simdShr(mask.asI32x4(), i32x4.splat(31))
    return simdMaskedLoad(mask1, memAddr, i32x4.ZERO, SimdAlign.Unaligned).asM128i()
}

/**
 * Loads packed 32-bit integers from memory pointed by `memAddr` using `mask`
 * (elements are zeroed out when the highest bit is not set in the
 * corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskload_epi32)
 */
public fun mm256MaskloadEpi32(memAddr: ConstPtr, mask: M256i): M256i {
    val mask1 = simdShr(mask.asI32x8(), i32x8.splat(31))
    return simdMaskedLoad(mask1, memAddr, i32x8.ZERO, SimdAlign.Unaligned).asM256i()
}

/**
 * Loads packed 64-bit integers from memory pointed by `memAddr` using `mask`
 * (elements are zeroed out when the highest bit is not set in the
 * corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskload_epi64)
 */
public fun mmMaskloadEpi64(memAddr: ConstPtr, mask: M128i): M128i {
    val mask1 = simdShr(mask.asI64x2(), i64x2.splat(63))
    return simdMaskedLoad(mask1, memAddr, i64x2.ZERO, SimdAlign.Unaligned).asM128i()
}

/**
 * Loads packed 64-bit integers from memory pointed by `memAddr` using `mask`
 * (elements are zeroed out when the highest bit is not set in the
 * corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskload_epi64)
 */
public fun mm256MaskloadEpi64(memAddr: ConstPtr, mask: M256i): M256i {
    val mask1 = simdShr(mask.asI64x4(), i64x4.splat(63))
    return simdMaskedLoad(mask1, memAddr, i64x4.ZERO, SimdAlign.Unaligned).asM256i()
}

/**
 * Stores packed 32-bit integers from `a` into memory pointed by `memAddr`
 * using `mask` (elements are not stored when the highest bit is not set
 * in the corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskstore_epi32)
 */
public fun mmMaskstoreEpi32(memAddr: MutPtr, mask: M128i, a: M128i) {
    val mask1 = simdShr(mask.asI32x4(), i32x4.splat(31))
    simdMaskedStore(mask1, memAddr, a.asI32x4(), SimdAlign.Unaligned)
}

/**
 * Stores packed 32-bit integers from `a` into memory pointed by `memAddr`
 * using `mask` (elements are not stored when the highest bit is not set
 * in the corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskstore_epi32)
 */
public fun mm256MaskstoreEpi32(memAddr: MutPtr, mask: M256i, a: M256i) {
    val mask1 = simdShr(mask.asI32x8(), i32x8.splat(31))
    simdMaskedStore(mask1, memAddr, a.asI32x8(), SimdAlign.Unaligned)
}

/**
 * Stores packed 64-bit integers from `a` into memory pointed by `memAddr`
 * using `mask` (elements are not stored when the highest bit is not set
 * in the corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskstore_epi64)
 */
public fun mmMaskstoreEpi64(memAddr: MutPtr, mask: M128i, a: M128i) {
    val mask1 = simdShr(mask.asI64x2(), i64x2.splat(63))
    simdMaskedStore(mask1, memAddr, a.asI64x2(), SimdAlign.Unaligned)
}

/**
 * Stores packed 64-bit integers from `a` into memory pointed by `memAddr`
 * using `mask` (elements are not stored when the highest bit is not set
 * in the corresponding element).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskstore_epi64)
 */
public fun mm256MaskstoreEpi64(memAddr: MutPtr, mask: M256i, a: M256i) {
    val mask1 = simdShr(mask.asI64x4(), i64x4.splat(63))
    simdMaskedStore(mask1, memAddr, a.asI64x4(), SimdAlign.Unaligned)
}

/**
 * Compares packed 16-bit integers in `a` and `b`, and returns the packed
 * maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_epi16)
 */
public fun mm256MaxEpi16(a: M256i, b: M256i): M256i = simdImax(a.asI16x16(), b.asI16x16()).asM256i()

/**
 * Compares packed 32-bit integers in `a` and `b`, and returns the packed
 * maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_epi32)
 */
public fun mm256MaxEpi32(a: M256i, b: M256i): M256i = simdImax(a.asI32x8(), b.asI32x8()).asM256i()

/**
 * Compares packed 8-bit integers in `a` and `b`, and returns the packed
 * maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_epi8)
 */
public fun mm256MaxEpi8(a: M256i, b: M256i): M256i = simdImax(a.asI8x32(), b.asI8x32()).asM256i()

/**
 * Compares packed unsigned 16-bit integers in `a` and `b`, and returns
 * the packed maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_epu16)
 */
public fun mm256MaxEpu16(a: M256i, b: M256i): M256i = simdImax(a.asU16x16(), b.asU16x16()).asM256i()

/**
 * Compares packed unsigned 32-bit integers in `a` and `b`, and returns
 * the packed maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_epu32)
 */
public fun mm256MaxEpu32(a: M256i, b: M256i): M256i = simdImax(a.asU32x8(), b.asU32x8()).asM256i()

/**
 * Compares packed unsigned 8-bit integers in `a` and `b`, and returns
 * the packed maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_epu8)
 */
public fun mm256MaxEpu8(a: M256i, b: M256i): M256i = simdImax(a.asU8x32(), b.asU8x32()).asM256i()

/**
 * Compares packed 16-bit integers in `a` and `b`, and returns the packed
 * minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_epi16)
 */
public fun mm256MinEpi16(a: M256i, b: M256i): M256i = simdImin(a.asI16x16(), b.asI16x16()).asM256i()

/**
 * Compares packed 32-bit integers in `a` and `b`, and returns the packed
 * minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_epi32)
 */
public fun mm256MinEpi32(a: M256i, b: M256i): M256i = simdImin(a.asI32x8(), b.asI32x8()).asM256i()

/**
 * Compares packed 8-bit integers in `a` and `b`, and returns the packed
 * minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_epi8)
 */
public fun mm256MinEpi8(a: M256i, b: M256i): M256i = simdImin(a.asI8x32(), b.asI8x32()).asM256i()

/**
 * Compares packed unsigned 16-bit integers in `a` and `b`, and returns
 * the packed minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_epu16)
 */
public fun mm256MinEpu16(a: M256i, b: M256i): M256i = simdImin(a.asU16x16(), b.asU16x16()).asM256i()

/**
 * Compares packed unsigned 32-bit integers in `a` and `b`, and returns
 * the packed minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_epu32)
 */
public fun mm256MinEpu32(a: M256i, b: M256i): M256i = simdImin(a.asU32x8(), b.asU32x8()).asM256i()

/**
 * Compares packed unsigned 8-bit integers in `a` and `b`, and returns
 * the packed minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_epu8)
 */
public fun mm256MinEpu8(a: M256i, b: M256i): M256i = simdImin(a.asU8x32(), b.asU8x32()).asM256i()

/**
 * Creates mask from the most significant bit of each 8-bit element in `a`,
 * return the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movemask_epi8)
 */
public fun mm256MovemaskEpi8(a: M256i): Int =
    run {
        val z = i8x32.ZERO
        val m: I8x32 = simdLt(a.asI8x32(), z)
        simdBitmask<UInt>(m).toInt()
    }

/**
 * Computes the sum of absolute differences (SADs) of quadruplets of unsigned
 * 8-bit integers in `a` compared to those in `b`, and stores the 16-bit
 * results in dst. Eight SADs are performed for each 128-bit lane using one
 * quadruplet from `b` and eight quadruplets from `a`. One quadruplet is
 * selected from `b` starting at on the offset specified in `imm8`. Eight
 * quadruplets are formed from sequential 8-bit integers selected from `a`
 * starting at the offset specified in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mpsadbw_epu8)
 */
public fun mm256MpsadbwEpu8(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(mpsadbw(a.asU8x32(), b.asU8x32(), imm8.toByte()))
}

/**
 * Multiplies the low 32-bit integers from each packed 64-bit element in
 * `a` and `b`
 *
 * Returns the 64-bit results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mul_epi32)
 */
public fun mm256MulEpi32(a: M256i, b: M256i): M256i =
    run {
        val a1 = simdCast<I64x4>(simdCast<I32x4>(a.asI64x4()))
        val b1 = simdCast<I64x4>(simdCast<I32x4>(b.asI64x4()))
        transmute(simdMul(a1, b1))
    }

/**
 * Multiplies the low unsigned 32-bit integers from each packed 64-bit
 * element in `a` and `b`
 *
 * Returns the unsigned 64-bit results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mul_epu32)
 */
public fun mm256MulEpu32(a: M256i, b: M256i): M256i =
    run {
        val a1 = a.asU64x4()
        val b1 = b.asU64x4()
        val mask = u64x4.splat(UInt.MAX_VALUE.toULong())
        transmute(simdMul(simdAnd(a1, mask), simdAnd(b1, mask)))
    }

/**
 * Multiplies the packed 16-bit integers in `a` and `b`, producing
 * intermediate 32-bit integers and returning the high 16 bits of the
 * intermediate integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mulhi_epi16)
 */
public fun mm256MulhiEpi16(a: M256i, b: M256i): M256i = transmute(pmulhw(a.asI16x16(), b.asI16x16()))

/**
 * Multiplies the packed unsigned 16-bit integers in `a` and `b`, producing
 * intermediate 32-bit integers and returning the high 16 bits of the
 * intermediate integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mulhi_epu16)
 */
public fun mm256MulhiEpu16(a: M256i, b: M256i): M256i = transmute(pmulhuw(a.asU16x16(), b.asU16x16()))

/**
 * Multiplies the packed 16-bit integers in `a` and `b`, producing
 * intermediate 32-bit integers, and returns the low 16 bits of the
 * intermediate integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mullo_epi16)
 */
public fun mm256MulloEpi16(a: M256i, b: M256i): M256i = transmute(simdMul(a.asI16x16(), b.asI16x16()))

/**
 * Multiplies the packed 32-bit integers in `a` and `b`, producing
 * intermediate 64-bit integers, and returns the low 32 bits of the
 * intermediate integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mullo_epi32)
 */
public fun mm256MulloEpi32(a: M256i, b: M256i): M256i = transmute(simdMul(a.asI32x8(), b.asI32x8()))

/**
 * Multiplies packed 16-bit integers in `a` and `b`, producing
 * intermediate signed 32-bit integers. Truncate each intermediate
 * integer to the 18 most significant bits, round by adding 1, and
 * return bits `[16: 1]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mulhrs_epi16)
 */
public fun mm256MulhrsEpi16(a: M256i, b: M256i): M256i = transmute(pmulhrsw(a.asI16x16(), b.asI16x16()))

/**
 * Computes the bitwise OR of 256 bits (representing integer data) in `a`
 * and `b`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_or_si256)
 */
public fun mm256OrSi256(a: M256i, b: M256i): M256i = transmute(simdOr(a.asI32x8(), b.asI32x8()))

/**
 * Converts packed 16-bit integers from `a` and `b` to packed 8-bit integers
 * using signed saturation
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_packs_epi16)
 */
public fun mm256PacksEpi16(a: M256i, b: M256i): M256i = transmute(packsswb(a.asI16x16(), b.asI16x16()))

/**
 * Converts packed 32-bit integers from `a` and `b` to packed 16-bit integers
 * using signed saturation
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_packs_epi32)
 */
public fun mm256PacksEpi32(a: M256i, b: M256i): M256i = transmute(packssdw(a.asI32x8(), b.asI32x8()))

/**
 * Converts packed 16-bit integers from `a` and `b` to packed 8-bit integers
 * using unsigned saturation
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_packus_epi16)
 */
public fun mm256PackusEpi16(a: M256i, b: M256i): M256i = transmute(packuswb(a.asI16x16(), b.asI16x16()))

/**
 * Converts packed 32-bit integers from `a` and `b` to packed 16-bit integers
 * using unsigned saturation
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_packus_epi32)
 */
public fun mm256PackusEpi32(a: M256i, b: M256i): M256i = transmute(packusdw(a.asI32x8(), b.asI32x8()))

/**
 * Permutes packed 32-bit integers from `a` according to the content of `b`.
 *
 * The last 3 bits of each integer of `b` are used as addresses into the 8
 * integers of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutevar8x32_epi32)
 */
public fun mm256Permutevar8x32Epi32(a: M256i, b: M256i): M256i = transmute(permd(a.asU32x8(), b.asU32x8()))

/**
 * Permutes 64-bit integers from `a` using control mask `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute4x64_epi64)
 */
public fun mm256Permute4x64Epi64(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val zero = i64x4.ZERO
        val r: I64x4 = simdShuffle(a.asI64x4(), zero, SimdShuffleIdx(uintArrayOf(imm8.toUInt() and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u)))
        transmute(r)
    }
}

/**
 * Shuffles 128-bits of integer data selected by `imm8` from `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute2x128_si256)
 */
public fun mm256Permute2x128Si256(a: M256i, b: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256Permute2f128Si256(a, b, imm8)
}

/**
 * Shuffles 64-bit floating-point elements in `a` across lanes using the
 * control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute4x64_pd)
 */
public fun mm256Permute4x64Pd(a: M256d, imm8: Int): M256d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return simdShuffle(a, mm256UndefinedPd(), SimdShuffleIdx(uintArrayOf(imm8.toUInt() and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u)))
}

/**
 * Shuffles eight 32-bit floating-point elements in `a` across lanes using
 * the corresponding 32-bit integer index in `idx`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutevar8x32_ps)
 */
public fun mm256Permutevar8x32Ps(a: M256, idx: M256i): M256 = permps(a, idx.asI32x8())

/**
 * Computes the absolute differences of packed unsigned 8-bit integers in `a`
 * and `b`, then horizontally sum each consecutive 8 differences to
 * produce four unsigned 16-bit integers, and pack these unsigned 16-bit
 * integers in the low 16 bits of the 64-bit return value
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sad_epu8)
 */
public fun mm256SadEpu8(a: M256i, b: M256i): M256i = transmute(psadbw(a.asU8x32(), b.asU8x32()))

/**
 * Shuffles bytes from `a` according to the content of `b`.
 *
 * For each of the 128-bit low and high halves of the vectors, the last
 * 4 bits of each byte of `b` are used as addresses into the respective
 * low or high 16 bytes of `a`. That is, the halves are shuffled separately.
 *
 * In addition, if the highest significant bit of a byte of `b` is set, the
 * respective destination byte is set to 0.
 *
 * Picturing `a` and `b` as `[UByte; 32]`, `_mm256_shuffle_epi8` is logically
 * equivalent to:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun mm256ShuffleEpi8(a: UByteArray, b: UByteArray): UByteArray {
 *         var r = MutableList(32) { 0 }
 *         for (i in 0 until 16) {
 *             // if the most significant bit of b is set,
 *             // then the destination byte is set to 0.
 *             if (b[i] and 0x80u.toUByte() == 0u.toUByte()) {
 *                 r[i] = a[(b[i] % 16u).toInt()]
 *             }
 *             if (b[i + 16] and 0x80u.toUByte() == 0u.toUByte()) {
 *                 r[i + 16] = a[(b[i + 16] % 16u + 16u).toInt()]
 *             }
 *         }
 *         return r
 *     }
 * }
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shuffle_epi8)
 */
public fun mm256ShuffleEpi8(a: M256i, b: M256i): M256i = transmute(pshufb(a.asU8x32(), b.asU8x32()))

/**
 * Shuffles 32-bit integers in 128-bit lanes of `a` using the control in
 * `imm8`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7)
 * val c1 = mm256ShuffleEpi32(a, 0x39)
 * val c2 = mm256ShuffleEpi32(a, 0x4B)
 * val expected1 = mm256SetrEpi32(1, 2, 3, 0, 5, 6, 7, 4)
 * val expected2 = mm256SetrEpi32(3, 2, 0, 1, 7, 6, 4, 5)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c1, expected1)) == 0.inv())
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c2, expected2)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shuffle_epi32)
 */
public fun mm256ShuffleEpi32(a: M256i, mask: Int): M256i {
    run {
        check(0 <= mask && mask < (1 shl 8)) { "MASK doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val r: I32x8 = simdShuffle(a.asI32x8(), a.asI32x8(), SimdShuffleIdx(uintArrayOf(mask.toUInt() and 0x3u, (mask.toUInt() shr 2) and 0x3u, (mask.toUInt() shr 4) and 0x3u, (mask.toUInt() shr 6) and 0x3u, (mask.toUInt() and 0x3u) + 4u, ((mask.toUInt() shr 2) and 0x3u) + 4u, ((mask.toUInt() shr 4) and 0x3u) + 4u, ((mask.toUInt() shr 6) and 0x3u) + 4u)))
        transmute(r)
    }
}

/**
 * Shuffles 16-bit integers in the high 64 bits of 128-bit lanes of `a` using
 * the control in `imm8`. The low 64 bits of 128-bit lanes of `a` are copied
 * to the output.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shufflehi_epi16)
 */
public fun mm256ShufflehiEpi16(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI16x16()
        val r: I16x16 = simdShuffle(a1, a1, SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u + (imm8.toUInt() and 0x3u), 4u + ((imm8.toUInt() shr 2) and 0x3u), 4u + ((imm8.toUInt() shr 4) and 0x3u), 4u + ((imm8.toUInt() shr 6) and 0x3u), 8u, 9u, 10u, 11u, 12u + (imm8.toUInt() and 0x3u), 12u + ((imm8.toUInt() shr 2) and 0x3u), 12u + ((imm8.toUInt() shr 4) and 0x3u), 12u + ((imm8.toUInt() shr 6) and 0x3u))))
        transmute(r)
    }
}

/**
 * Shuffles 16-bit integers in the low 64 bits of 128-bit lanes of `a` using
 * the control in `imm8`. The high 64 bits of 128-bit lanes of `a` are copied
 * to the output.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shufflelo_epi16)
 */
public fun mm256ShuffleloEpi16(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI16x16()
        val r: I16x16 = simdShuffle(a1, a1, SimdShuffleIdx(uintArrayOf(0u + (imm8.toUInt() and 0x3u), 0u + ((imm8.toUInt() shr 2) and 0x3u), 0u + ((imm8.toUInt() shr 4) and 0x3u), 0u + ((imm8.toUInt() shr 6) and 0x3u), 4u, 5u, 6u, 7u, 8u + (imm8.toUInt() and 0x3u), 8u + ((imm8.toUInt() shr 2) and 0x3u), 8u + ((imm8.toUInt() shr 4) and 0x3u), 8u + ((imm8.toUInt() shr 6) and 0x3u), 12u, 13u, 14u, 15u)))
        transmute(r)
    }
}

/**
 * Negates packed 16-bit integers in `a` when the corresponding signed
 * 16-bit integer in `b` is negative, and returns the results.
 * Results are zeroed out when the corresponding element in `b` is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sign_epi16)
 */
public fun mm256SignEpi16(a: M256i, b: M256i): M256i = transmute(psignw(a.asI16x16(), b.asI16x16()))

/**
 * Negates packed 32-bit integers in `a` when the corresponding signed
 * 32-bit integer in `b` is negative, and returns the results.
 * Results are zeroed out when the corresponding element in `b` is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sign_epi32)
 */
public fun mm256SignEpi32(a: M256i, b: M256i): M256i = transmute(psignd(a.asI32x8(), b.asI32x8()))

/**
 * Negates packed 8-bit integers in `a` when the corresponding signed
 * 8-bit integer in `b` is negative, and returns the results.
 * Results are zeroed out when the corresponding element in `b` is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sign_epi8)
 */
public fun mm256SignEpi8(a: M256i, b: M256i): M256i = transmute(psignb(a.asI8x32(), b.asI8x32()))

/**
 * Shifts packed 16-bit integers in `a` left by `count` while
 * shifting in zeros, and returns the result
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sll_epi16)
 */
public fun mm256SllEpi16(a: M256i, count: M128i): M256i = transmute(psllw(a.asI16x16(), count.asI16x8()))

/**
 * Shifts packed 32-bit integers in `a` left by `count` while
 * shifting in zeros, and returns the result
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sll_epi32)
 */
public fun mm256SllEpi32(a: M256i, count: M128i): M256i = transmute(pslld(a.asI32x8(), count.asI32x4()))

/**
 * Shifts packed 64-bit integers in `a` left by `count` while
 * shifting in zeros, and returns the result
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sll_epi64)
 */
public fun mm256SllEpi64(a: M256i, count: M128i): M256i = transmute(psllq(a.asI64x4(), count.asI64x2()))

/**
 * Shifts packed 16-bit integers in `a` left by `IMM8` while
 * shifting in zeros, return the results;
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_slli_epi16)
 */
public fun mm256SlliEpi16(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 16) {
        mm256SetzeroSi256()
    } else {
        transmute(simdShl(a.asU16x16(), u16x16.splat(imm8.toUShort())))
    }
}

/**
 * Shifts packed 32-bit integers in `a` left by `IMM8` while
 * shifting in zeros, return the results;
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_slli_epi32)
 */
public fun mm256SlliEpi32(a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 32) {
            mm256SetzeroSi256()
        } else {
            transmute(simdShl(a.asU32x8(), u32x8.splat(imm8.toUInt())))
        }
    }

/**
 * Shifts packed 64-bit integers in `a` left by `IMM8` while
 * shifting in zeros, return the results;
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_slli_epi64)
 */
public fun mm256SlliEpi64(a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 64) {
            mm256SetzeroSi256()
        } else {
            transmute(simdShl(a.asU64x4(), u64x4.splat(imm8.toULong())))
        }
    }

/**
 * Shifts 128-bit lanes in `a` left by `imm8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_slli_si256)
 */
public fun mm256SlliSi256(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256BslliEpi128(a, imm8)
}

/**
 * Shifts 128-bit lanes in `a` left by `imm8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_bslli_epi128)
 */
public fun mm256BslliEpi128(a: M256i, imm8: Int): M256i {
    fun mask(shift: Int, i: UInt): UInt {
        val shift1 = shift.toUInt() and 0xffu
        return if (shift1 > 15u || i % 16u < shift1) {
            0u
        } else {
            32u + (i - shift1)
        }
    }
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI8x32()
        val r: I8x32 = simdShuffle(i8x32.ZERO, a1, SimdShuffleIdx(listOf(mask(imm8, 0u), mask(imm8, 1u), mask(imm8, 2u), mask(imm8, 3u), mask(imm8, 4u), mask(imm8, 5u), mask(imm8, 6u), mask(imm8, 7u), mask(imm8, 8u), mask(imm8, 9u), mask(imm8, 10u), mask(imm8, 11u), mask(imm8, 12u), mask(imm8, 13u), mask(imm8, 14u), mask(imm8, 15u), mask(imm8, 16u), mask(imm8, 17u), mask(imm8, 18u), mask(imm8, 19u), mask(imm8, 20u), mask(imm8, 21u), mask(imm8, 22u), mask(imm8, 23u), mask(imm8, 24u), mask(imm8, 25u), mask(imm8, 26u), mask(imm8, 27u), mask(imm8, 28u), mask(imm8, 29u), mask(imm8, 30u), mask(imm8, 31u))))
        transmute(r)
    }
}

/**
 * Shifts packed 32-bit integers in `a` left by the amount
 * specified by the corresponding element in `count` while
 * shifting in zeros, and returns the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sllv_epi32)
 */
public fun mmSllvEpi32(a: M128i, count: M128i): M128i = transmute(psllvd(a.asI32x4(), count.asI32x4()))

/**
 * Shifts packed 32-bit integers in `a` left by the amount
 * specified by the corresponding element in `count` while
 * shifting in zeros, and returns the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sllv_epi32)
 */
public fun mm256SllvEpi32(a: M256i, count: M256i): M256i = transmute(psllvd256(a.asI32x8(), count.asI32x8()))

/**
 * Shifts packed 64-bit integers in `a` left by the amount
 * specified by the corresponding element in `count` while
 * shifting in zeros, and returns the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sllv_epi64)
 */
public fun mmSllvEpi64(a: M128i, count: M128i): M128i = transmute(psllvq(a.asI64x2(), count.asI64x2()))

/**
 * Shifts packed 64-bit integers in `a` left by the amount
 * specified by the corresponding element in `count` while
 * shifting in zeros, and returns the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sllv_epi64)
 */
public fun mm256SllvEpi64(a: M256i, count: M256i): M256i = transmute(psllvq256(a.asI64x4(), count.asI64x4()))

/**
 * Shifts packed 16-bit integers in `a` right by `count` while
 * shifting in sign bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sra_epi16)
 */
public fun mm256SraEpi16(a: M256i, count: M128i): M256i = transmute(psraw(a.asI16x16(), count.asI16x8()))

/**
 * Shifts packed 32-bit integers in `a` right by `count` while
 * shifting in sign bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sra_epi32)
 */
public fun mm256SraEpi32(a: M256i, count: M128i): M256i = transmute(psrad(a.asI32x8(), count.asI32x4()))

/**
 * Shifts packed 16-bit integers in `a` right by `IMM8` while
 * shifting in sign bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srai_epi16)
 */
public fun mm256SraiEpi16(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(simdShr(a.asI16x16(), i16x16.splat(imm8.min(15).toShort())))
}

/**
 * Shifts packed 32-bit integers in `a` right by `IMM8` while
 * shifting in sign bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srai_epi32)
 */
public fun mm256SraiEpi32(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(simdShr(a.asI32x8(), i32x8.splat(imm8.min(31))))
}

/**
 * Shifts packed 32-bit integers in `a` right by the amount specified by the
 * corresponding element in `count` while shifting in sign bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srav_epi32)
 */
public fun mmSravEpi32(a: M128i, count: M128i): M128i = transmute(psravd(a.asI32x4(), count.asI32x4()))

/**
 * Shifts packed 32-bit integers in `a` right by the amount specified by the
 * corresponding element in `count` while shifting in sign bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srav_epi32)
 */
public fun mm256SravEpi32(a: M256i, count: M256i): M256i = transmute(psravd256(a.asI32x8(), count.asI32x8()))

/**
 * Shifts 128-bit lanes in `a` right by `imm8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srli_si256)
 */
public fun mm256SrliSi256(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256BsrliEpi128(a, imm8)
}

/**
 * Shifts 128-bit lanes in `a` right by `imm8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_bsrli_epi128)
 */
public fun mm256BsrliEpi128(a: M256i, imm8: Int): M256i {
    fun mask(shift: Int, i: UInt): UInt {
        val shift1 = shift.toUInt() and 0xffu
        return if (shift1 > 15u || (15u - (i % 16u)) < shift1) {
            0u
        } else {
            32u + (i + shift1)
        }
    }
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI8x32()
        val r: I8x32 = simdShuffle(i8x32.ZERO, a1, SimdShuffleIdx(listOf(mask(imm8, 0u), mask(imm8, 1u), mask(imm8, 2u), mask(imm8, 3u), mask(imm8, 4u), mask(imm8, 5u), mask(imm8, 6u), mask(imm8, 7u), mask(imm8, 8u), mask(imm8, 9u), mask(imm8, 10u), mask(imm8, 11u), mask(imm8, 12u), mask(imm8, 13u), mask(imm8, 14u), mask(imm8, 15u), mask(imm8, 16u), mask(imm8, 17u), mask(imm8, 18u), mask(imm8, 19u), mask(imm8, 20u), mask(imm8, 21u), mask(imm8, 22u), mask(imm8, 23u), mask(imm8, 24u), mask(imm8, 25u), mask(imm8, 26u), mask(imm8, 27u), mask(imm8, 28u), mask(imm8, 29u), mask(imm8, 30u), mask(imm8, 31u))))
        transmute(r)
    }
}

/**
 * Shifts packed 16-bit integers in `a` right by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srl_epi16)
 */
public fun mm256SrlEpi16(a: M256i, count: M128i): M256i = transmute(psrlw(a.asI16x16(), count.asI16x8()))

/**
 * Shifts packed 32-bit integers in `a` right by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srl_epi32)
 */
public fun mm256SrlEpi32(a: M256i, count: M128i): M256i = transmute(psrld(a.asI32x8(), count.asI32x4()))

/**
 * Shifts packed 64-bit integers in `a` right by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srl_epi64)
 */
public fun mm256SrlEpi64(a: M256i, count: M128i): M256i = transmute(psrlq(a.asI64x4(), count.asI64x2()))

/**
 * Shifts packed 16-bit integers in `a` right by `IMM8` while shifting in
 * zeros
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srli_epi16)
 */
public fun mm256SrliEpi16(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 16) {
        mm256SetzeroSi256()
    } else {
        transmute(simdShr(a.asU16x16(), u16x16.splat(imm8.toUShort())))
    }
}

/**
 * Shifts packed 32-bit integers in `a` right by `IMM8` while shifting in
 * zeros
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srli_epi32)
 */
public fun mm256SrliEpi32(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 32) {
        mm256SetzeroSi256()
    } else {
        transmute(simdShr(a.asU32x8(), u32x8.splat(imm8.toUInt())))
    }
}

/**
 * Shifts packed 64-bit integers in `a` right by `IMM8` while shifting in
 * zeros
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srli_epi64)
 */
public fun mm256SrliEpi64(a: M256i, imm8: Int): M256i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 64) {
        mm256SetzeroSi256()
    } else {
        transmute(simdShr(a.asU64x4(), u64x4.splat(imm8.toULong())))
    }
}

/**
 * Shifts packed 32-bit integers in `a` right by the amount specified by
 * the corresponding element in `count` while shifting in zeros,
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srlv_epi32)
 */
public fun mmSrlvEpi32(a: M128i, count: M128i): M128i = transmute(psrlvd(a.asI32x4(), count.asI32x4()))

/**
 * Shifts packed 32-bit integers in `a` right by the amount specified by
 * the corresponding element in `count` while shifting in zeros,
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srlv_epi32)
 */
public fun mm256SrlvEpi32(a: M256i, count: M256i): M256i = transmute(psrlvd256(a.asI32x8(), count.asI32x8()))

/**
 * Shifts packed 64-bit integers in `a` right by the amount specified by
 * the corresponding element in `count` while shifting in zeros,
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srlv_epi64)
 */
public fun mmSrlvEpi64(a: M128i, count: M128i): M128i = transmute(psrlvq(a.asI64x2(), count.asI64x2()))

/**
 * Shifts packed 64-bit integers in `a` right by the amount specified by
 * the corresponding element in `count` while shifting in zeros,
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srlv_epi64)
 */
public fun mm256SrlvEpi64(a: M256i, count: M256i): M256i = transmute(psrlvq256(a.asI64x4(), count.asI64x4()))

/**
 * Load 256-bits of integer data from memory into dst using a non-temporal memory hint. memAddr
 * must be aligned on a 32-byte boundary or a general-protection exception may be generated. To
 * minimize caching, the data is flagged as non-temporal (unlikely to be used again soon)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_stream_load_si256)
 */
public fun mm256StreamLoadSi256(memAddr: ConstPtr): M256i {
    val dst: M256i
    asm(vpl("vmovntdqa {a}"), a, out, ymmReg, dst, p, reg, memAddr, options(pure, readonly, nostack, preservesFlags))
    return dst
}

/**
 * Subtract packed 16-bit integers in `b` from packed 16-bit integers in `a`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sub_epi16)
 */
public fun mm256SubEpi16(a: M256i, b: M256i): M256i = transmute(simdSub(a.asI16x16(), b.asI16x16()))

/**
 * Subtract packed 32-bit integers in `b` from packed 32-bit integers in `a`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sub_epi32)
 */
public fun mm256SubEpi32(a: M256i, b: M256i): M256i = transmute(simdSub(a.asI32x8(), b.asI32x8()))

/**
 * Subtract packed 64-bit integers in `b` from packed 64-bit integers in `a`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sub_epi64)
 */
public fun mm256SubEpi64(a: M256i, b: M256i): M256i = transmute(simdSub(a.asI64x4(), b.asI64x4()))

/**
 * Subtract packed 8-bit integers in `b` from packed 8-bit integers in `a`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sub_epi8)
 */
public fun mm256SubEpi8(a: M256i, b: M256i): M256i = transmute(simdSub(a.asI8x32(), b.asI8x32()))

/**
 * Subtract packed 16-bit integers in `b` from packed 16-bit integers in
 * `a` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_subs_epi16)
 */
public fun mm256SubsEpi16(a: M256i, b: M256i): M256i = transmute(simdSaturatingSub(a.asI16x16(), b.asI16x16()))

/**
 * Subtract packed 8-bit integers in `b` from packed 8-bit integers in
 * `a` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_subs_epi8)
 */
public fun mm256SubsEpi8(a: M256i, b: M256i): M256i = transmute(simdSaturatingSub(a.asI8x32(), b.asI8x32()))

/**
 * Subtract packed unsigned 16-bit integers in `b` from packed 16-bit
 * integers in `a` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_subs_epu16)
 */
public fun mm256SubsEpu16(a: M256i, b: M256i): M256i = transmute(simdSaturatingSub(a.asU16x16(), b.asU16x16()))

/**
 * Subtract packed unsigned 8-bit integers in `b` from packed 8-bit
 * integers in `a` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_subs_epu8)
 */
public fun mm256SubsEpu8(a: M256i, b: M256i): M256i = transmute(simdSaturatingSub(a.asU8x32(), b.asU8x32()))

/**
 * Unpacks and interleave 8-bit integers from the high half of each
 * 128-bit lane in `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
 * val b = mm256SetrEpi8(0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24, -25, -26, -27, -28, -29, -30, -31)
 * val c = mm256UnpackhiEpi8(a, b)
 * val expected = mm256SetrEpi8(8, -8, 9, -9, 10, -10, 11, -11, 12, -12, 13, -13, 14, -14, 15, -15, 24, -24, 25, -25, 26, -26, 27, -27, 28, -28, 29, -29, 30, -30, 31, -31)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpackhi_epi8)
 */
public fun mm256UnpackhiEpi8(a: M256i, b: M256i): M256i =
    run {
        val r: I8x32 = simdShuffle(a.asI8x32(), b.asI8x32(), SimdShuffleIdx(listOf(8, 40, 9, 41, 10, 42, 11, 43, 12, 44, 13, 45, 14, 46, 15, 47, 24, 56, 25, 57, 26, 58, 27, 59, 28, 60, 29, 61, 30, 62, 31, 63)))
        transmute(r)
    }

/**
 * Unpacks and interleave 8-bit integers from the low half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
 * val b = mm256SetrEpi8(0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24, -25, -26, -27, -28, -29, -30, -31)
 * val c = mm256UnpackloEpi8(a, b)
 * val expected = mm256SetrEpi8(0, 0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 16, -16, 17, -17, 18, -18, 19, -19, 20, -20, 21, -21, 22, -22, 23, -23)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpacklo_epi8)
 */
public fun mm256UnpackloEpi8(a: M256i, b: M256i): M256i =
    run {
        val r: I8x32 = simdShuffle(a.asI8x32(), b.asI8x32(), SimdShuffleIdx(listOf(0, 32, 1, 33, 2, 34, 3, 35, 4, 36, 5, 37, 6, 38, 7, 39, 16, 48, 17, 49, 18, 50, 19, 51, 20, 52, 21, 53, 22, 54, 23, 55)))
        transmute(r)
    }

/**
 * Unpacks and interleave 16-bit integers from the high half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
 * val b = mm256SetrEpi16(0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15)
 * val c = mm256UnpackhiEpi16(a, b)
 * val expected = mm256SetrEpi16(4, -4, 5, -5, 6, -6, 7, -7, 12, -12, 13, -13, 14, -14, 15, -15)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpackhi_epi16)
 */
public fun mm256UnpackhiEpi16(a: M256i, b: M256i): M256i =
    run {
        val r: I16x16 = simdShuffle(a.asI16x16(), b.asI16x16(), SimdShuffleIdx(listOf(4, 20, 5, 21, 6, 22, 7, 23, 12, 28, 13, 29, 14, 30, 15, 31)))
        transmute(r)
    }

/**
 * Unpacks and interleave 16-bit integers from the low half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
 * val b = mm256SetrEpi16(0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15)
 * val c = mm256UnpackloEpi16(a, b)
 * val expected = mm256SetrEpi16(0, 0, 1, -1, 2, -2, 3, -3, 8, -8, 9, -9, 10, -10, 11, -11)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpacklo_epi16)
 */
public fun mm256UnpackloEpi16(a: M256i, b: M256i): M256i =
    run {
        val r: I16x16 = simdShuffle(a.asI16x16(), b.asI16x16(), SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 8, 24, 9, 25, 10, 26, 11, 27)))
        transmute(r)
    }

/**
 * Unpacks and interleave 32-bit integers from the high half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7)
 * val b = mm256SetrEpi32(0, -1, -2, -3, -4, -5, -6, -7)
 * val c = mm256UnpackhiEpi32(a, b)
 * val expected = mm256SetrEpi32(2, -2, 3, -3, 6, -6, 7, -7)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpackhi_epi32)
 */
public fun mm256UnpackhiEpi32(a: M256i, b: M256i): M256i =
    run {
        val r: I32x8 = simdShuffle(a.asI32x8(), b.asI32x8(), SimdShuffleIdx(listOf(2, 10, 3, 11, 6, 14, 7, 15)))
        transmute(r)
    }

/**
 * Unpacks and interleave 32-bit integers from the low half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi32(0, 1, 2, 3, 4, 5, 6, 7)
 * val b = mm256SetrEpi32(0, -1, -2, -3, -4, -5, -6, -7)
 * val c = mm256UnpackloEpi32(a, b)
 * val expected = mm256SetrEpi32(0, 0, 1, -1, 4, -4, 5, -5)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpacklo_epi32)
 */
public fun mm256UnpackloEpi32(a: M256i, b: M256i): M256i =
    run {
        val r: I32x8 = simdShuffle(a.asI32x8(), b.asI32x8(), SimdShuffleIdx(listOf(0, 8, 1, 9, 4, 12, 5, 13)))
        transmute(r)
    }

/**
 * Unpacks and interleave 64-bit integers from the high half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi64x(0, 1, 2, 3)
 * val b = mm256SetrEpi64x(0, -1, -2, -3)
 * val c = mm256UnpackhiEpi64(a, b)
 * val expected = mm256SetrEpi64x(1, -1, 3, -3)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpackhi_epi64)
 */
public fun mm256UnpackhiEpi64(a: M256i, b: M256i): M256i =
    run {
        val r: I64x4 = simdShuffle(a.asI64x4(), b.asI64x4(), SimdShuffleIdx(listOf(1, 5, 3, 7)))
        transmute(r)
    }

/**
 * Unpacks and interleave 64-bit integers from the low half of each
 * 128-bit lane of `a` and `b`.
 *
 * ```kotlin
 * import std.arch.x8664.*
 *
 * val a = mm256SetrEpi64x(0, 1, 2, 3)
 * val b = mm256SetrEpi64x(0, -1, -2, -3)
 * val c = mm256UnpackloEpi64(a, b)
 * val expected = mm256SetrEpi64x(0, 0, 2, -2)
 * check(mm256MovemaskEpi8(mm256CmpeqEpi8(c, expected)) == 0.inv())
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpacklo_epi64)
 */
public fun mm256UnpackloEpi64(a: M256i, b: M256i): M256i =
    run {
        val r: I64x4 = simdShuffle(a.asI64x4(), b.asI64x4(), SimdShuffleIdx(listOf(0, 4, 2, 6)))
        transmute(r)
    }

/**
 * Computes the bitwise XOR of 256 bits (representing integer data)
 * in `a` and `b`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_xor_si256)
 */
public fun mm256XorSi256(a: M256i, b: M256i): M256i = transmute(simdXor(a.asI64x4(), b.asI64x4()))

// This intrinsic has no corresponding instruction.

/**
 * Extracts an 8-bit integer from `a`, selected with `INDEX`. Returns a 32-bit
 * integer containing the zero-extended integer data.
 *
 * See [LLVM commit D20468](https://reviews.llvm.org/D20468).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extract_epi8)
 */
public fun mm256ExtractEpi8(a: M256i, index: Int): Int {
    run {
        check(0 <= index && index < (1 shl 5)) { "INDEX doesn't fit in 5 bits (unsigned)" }
    }
    return (simdExtract<UByte>(a.asU8x32(), index.toUInt())).toInt()
}

// This intrinsic has no corresponding instruction.

/**
 * Extracts a 16-bit integer from `a`, selected with `INDEX`. Returns a 32-bit
 * integer containing the zero-extended integer data.
 *
 * See [LLVM commit D20468](https://reviews.llvm.org/D20468).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extract_epi16)
 */
public fun mm256ExtractEpi16(a: M256i, index: Int): Int {
    run {
        check(0 <= index && index < (1 shl 4)) { "INDEX doesn't fit in 4 bits (unsigned)" }
    }
    return (simdExtract<UShort>(a.asU16x16(), index.toUInt())).toInt()
}

public expect fun pavgb(a: U8x32, b: U8x32): U8x32

public expect fun pavgw(a: U16x16, b: U16x16): U16x16

public expect fun pmaddwd(a: I16x16, b: I16x16): I32x8

public expect fun pmaddubsw(a: U8x32, b: I8x32): I16x16

public expect fun mpsadbw(a: U8x32, b: U8x32, imm8: Byte): U16x16

public expect fun pmulhuw(a: U16x16, b: U16x16): U16x16

public expect fun pmulhw(a: I16x16, b: I16x16): I16x16

public expect fun pmulhrsw(a: I16x16, b: I16x16): I16x16

public expect fun packsswb(a: I16x16, b: I16x16): I8x32

public expect fun packssdw(a: I32x8, b: I32x8): I16x16

public expect fun packuswb(a: I16x16, b: I16x16): U8x32

public expect fun packusdw(a: I32x8, b: I32x8): U16x16

public expect fun psadbw(a: U8x32, b: U8x32): U64x4

public expect fun psignb(a: I8x32, b: I8x32): I8x32

public expect fun psignw(a: I16x16, b: I16x16): I16x16

public expect fun psignd(a: I32x8, b: I32x8): I32x8

public expect fun psllw(a: I16x16, count: I16x8): I16x16

public expect fun pslld(a: I32x8, count: I32x4): I32x8

public expect fun psllq(a: I64x4, count: I64x2): I64x4

public expect fun psllvd(a: I32x4, count: I32x4): I32x4

public expect fun psllvd256(a: I32x8, count: I32x8): I32x8

public expect fun psllvq(a: I64x2, count: I64x2): I64x2

public expect fun psllvq256(a: I64x4, count: I64x4): I64x4

public expect fun psraw(a: I16x16, count: I16x8): I16x16

public expect fun psrad(a: I32x8, count: I32x4): I32x8

public expect fun psravd(a: I32x4, count: I32x4): I32x4

public expect fun psravd256(a: I32x8, count: I32x8): I32x8

public expect fun psrlw(a: I16x16, count: I16x8): I16x16

public expect fun psrld(a: I32x8, count: I32x4): I32x8

public expect fun psrlq(a: I64x4, count: I64x2): I64x4

public expect fun psrlvd(a: I32x4, count: I32x4): I32x4

public expect fun psrlvd256(a: I32x8, count: I32x8): I32x8

public expect fun psrlvq(a: I64x2, count: I64x2): I64x2

public expect fun psrlvq256(a: I64x4, count: I64x4): I64x4

public expect fun pshufb(a: U8x32, b: U8x32): U8x32

public expect fun permd(a: U32x8, b: U32x8): U32x8

public expect fun permps(a: M256, b: I32x8): M256

public expect fun pgatherdd(src: I32x4, slice: ConstPtr, offsets: I32x4, mask: I32x4, scale: Byte): I32x4

public expect fun vpgatherdd(src: I32x8, slice: ConstPtr, offsets: I32x8, mask: I32x8, scale: Byte): I32x8

public expect fun pgatherdq(src: I64x2, slice: ConstPtr, offsets: I32x4, mask: I64x2, scale: Byte): I64x2

public expect fun vpgatherdq(src: I64x4, slice: ConstPtr, offsets: I32x4, mask: I64x4, scale: Byte): I64x4

public expect fun pgatherqd(src: I32x4, slice: ConstPtr, offsets: I64x2, mask: I32x4, scale: Byte): I32x4

public expect fun vpgatherqd(src: I32x4, slice: ConstPtr, offsets: I64x4, mask: I32x4, scale: Byte): I32x4

public expect fun pgatherqq(src: I64x2, slice: ConstPtr, offsets: I64x2, mask: I64x2, scale: Byte): I64x2

public expect fun vpgatherqq(src: I64x4, slice: ConstPtr, offsets: I64x4, mask: I64x4, scale: Byte): I64x4

public expect fun pgatherdpd(src: M128d, slice: ConstPtr, offsets: I32x4, mask: M128d, scale: Byte): M128d

public expect fun vpgatherdpd(src: M256d, slice: ConstPtr, offsets: I32x4, mask: M256d, scale: Byte): M256d

public expect fun pgatherqpd(src: M128d, slice: ConstPtr, offsets: I64x2, mask: M128d, scale: Byte): M128d

public expect fun vpgatherqpd(src: M256d, slice: ConstPtr, offsets: I64x4, mask: M256d, scale: Byte): M256d

public expect fun pgatherdps(src: M128, slice: ConstPtr, offsets: I32x4, mask: M128, scale: Byte): M128

public expect fun vpgatherdps(src: M256, slice: ConstPtr, offsets: I32x8, mask: M256, scale: Byte): M256

public expect fun pgatherqps(src: M128, slice: ConstPtr, offsets: I64x2, mask: M128, scale: Byte): M128

public expect fun vpgatherqps(src: M128, slice: ConstPtr, offsets: I64x4, mask: M128, scale: Byte): M128
