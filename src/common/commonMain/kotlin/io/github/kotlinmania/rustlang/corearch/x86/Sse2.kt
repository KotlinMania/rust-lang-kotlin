// port-lint: source x86/sse2.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.arch.asm
import io.github.kotlinmania.rustlang.corearch.intrinsics.sqrt
import io.github.kotlinmania.rustlang.corearch.mem
import io.github.kotlinmania.rustlang.corearch.ptr
import io.github.kotlinmania.rustlang.corearch.simd.F32x2
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.simdImax
import io.github.kotlinmania.rustlang.corearch.simd.simdImin
import io.github.kotlinmania.rustlang.corearch.simdExtract
import io.github.kotlinmania.rustlang.corearch.simdInsert
import io.github.kotlinmania.rustlang.corearch.simdShuffle

// Streaming SIMD Extensions 2 (SSE2)

/**
 * Provides a hint to the processor that the code sequence is a spin-wait loop.
 *
 * This can help improve the performance and power consumption of spin-wait
 * loops.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_pause)
 */
public fun mmPause() {
    // note: `pause` is guaranteed to be interpreted as a `nop` by CPUs without
    // the SSE2 target-feature - therefore it does not require any target features
    pause()
}

/**
 * Invalidates and flushes the cache line that contains `p` from all levels of
 * the cache hierarchy.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_clflush)
 *
 * # Safety
 *
 * Unlike the prefetch intrinsics, `CLFLUSH` is subject to all the permission
 * checking and faults associated with a byte load, so `p` must point to a
 * byte that is valid for reads.
 */
public fun mmClflush(p: ConstPtr) {
    clflush(p)
}

/**
 * Performs a serializing operation on all load-from-memory instructions
 * that were issued prior to this instruction.
 *
 * Guarantees that every load instruction that precedes, in program order, is
 * globally visible before any load instruction which follows the fence in
 * program order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_lfence)
 */
public fun mmLfence() {
    lfence()
}

/**
 * Performs a serializing operation on all load-from-memory and store-to-memory
 * instructions that were issued prior to this instruction.
 *
 * Guarantees that every memory access that precedes, in program order, the
 * memory fence instruction is globally visible before any memory instruction
 * which follows the fence in program order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mfence)
 */
public fun mmMfence() {
    mfence()
}

/**
 * Adds packed 8-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_epi8)
 */
public fun mmAddEpi8(a: M128i, b: M128i): M128i = transmute(simdAdd(a.asI8x16(), b.asI8x16()))

/**
 * Adds packed 16-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_epi16)
 */
public fun mmAddEpi16(a: M128i, b: M128i): M128i = transmute(simdAdd(a.asI16x8(), b.asI16x8()))

/**
 * Adds packed 32-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_epi32)
 */
public fun mmAddEpi32(a: M128i, b: M128i): M128i = transmute(simdAdd(a.asI32x4(), b.asI32x4()))

/**
 * Adds packed 64-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_epi64)
 */
public fun mmAddEpi64(a: M128i, b: M128i): M128i = transmute(simdAdd(a.asI64x2(), b.asI64x2()))

/**
 * Adds packed 8-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_adds_epi8)
 */
public fun mmAddsEpi8(a: M128i, b: M128i): M128i = transmute(simdSaturatingAdd(a.asI8x16(), b.asI8x16()))

/**
 * Adds packed 16-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_adds_epi16)
 */
public fun mmAddsEpi16(a: M128i, b: M128i): M128i = transmute(simdSaturatingAdd(a.asI16x8(), b.asI16x8()))

/**
 * Adds packed unsigned 8-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_adds_epu8)
 */
public fun mmAddsEpu8(a: M128i, b: M128i): M128i = transmute(simdSaturatingAdd(a.asU8x16(), b.asU8x16()))

/**
 * Adds packed unsigned 16-bit integers in `a` and `b` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_adds_epu16)
 */
public fun mmAddsEpu16(a: M128i, b: M128i): M128i = transmute(simdSaturatingAdd(a.asU16x8(), b.asU16x8()))

/**
 * Averages packed unsigned 8-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_avg_epu8)
 */
public fun mmAvgEpu8(a: M128i, b: M128i): M128i = transmute(pavgb(a.asU8x16(), b.asU8x16()))

/**
 * Averages packed unsigned 16-bit integers in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_avg_epu16)
 */
public fun mmAvgEpu16(a: M128i, b: M128i): M128i = transmute(pavgw(a.asU16x8(), b.asU16x8()))

/**
 * Multiplies and then horizontally add signed 16 bit integers in `a` and `b`.
 *
 * Multiplies packed signed 16-bit integers in `a` and `b`, producing
 * intermediate signed 32-bit integers. Horizontally add adjacent pairs of
 * intermediate 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_madd_epi16)
 */
public fun mmMaddEpi16(a: M128i, b: M128i): M128i {
    // It's a trick used in the Adler-32 algorithm to perform a widening addition.
    //
    // `rust
    // @targetFeature(enable = "sse2")
    // unchecked fn wideningAdd(mad: __m128i) -> __m128i {
    // _mm_madd_epi16(mad, _mm_set1_epi16(1))
    // }
    // `
    //
    // If we implement this using generic vector intrinsics, the optimizer
    // will eliminate this pattern, and `pmaddwd` will no longer be emitted.
    // For this reason, we use x86 intrinsics.
    return transmute(pmaddwd(a.asI16x8(), b.asI16x8()))
}

/**
 * Compares packed 16-bit integers in `a` and `b`, and returns the packed
 * maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_epi16)
 */
public fun mmMaxEpi16(a: M128i, b: M128i): M128i = simdImax(a.asI16x8(), b.asI16x8()).asM128i()

/**
 * Compares packed unsigned 8-bit integers in `a` and `b`, and returns the
 * packed maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_epu8)
 */
public fun mmMaxEpu8(a: M128i, b: M128i): M128i = simdImax(a.asU8x16(), b.asU8x16()).asM128i()

/**
 * Compares packed 16-bit integers in `a` and `b`, and returns the packed
 * minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_epi16)
 */
public fun mmMinEpi16(a: M128i, b: M128i): M128i = simdImin(a.asI16x8(), b.asI16x8()).asM128i()

/**
 * Compares packed unsigned 8-bit integers in `a` and `b`, and returns the
 * packed minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_epu8)
 */
public fun mmMinEpu8(a: M128i, b: M128i): M128i = simdImin(a.asU8x16(), b.asU8x16()).asM128i()

/**
 * Multiplies the packed 16-bit integers in `a` and `b`.
 *
 * The multiplication produces intermediate 32-bit integers, and returns the
 * high 16 bits of the intermediate integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mulhi_epi16)
 */
public fun mmMulhiEpi16(a: M128i, b: M128i): M128i = transmute(pmulhw(a.asI16x8(), b.asI16x8()))

/**
 * Multiplies the packed unsigned 16-bit integers in `a` and `b`.
 *
 * The multiplication produces intermediate 32-bit integers, and returns the
 * high 16 bits of the intermediate integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mulhi_epu16)
 */
public fun mmMulhiEpu16(a: M128i, b: M128i): M128i = transmute(pmulhuw(a.asU16x8(), b.asU16x8()))

/**
 * Multiplies the packed 16-bit integers in `a` and `b`.
 *
 * The multiplication produces intermediate 32-bit integers, and returns the
 * low 16 bits of the intermediate integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mullo_epi16)
 */
public fun mmMulloEpi16(a: M128i, b: M128i): M128i = transmute(simdMul(a.asI16x8(), b.asI16x8()))

/**
 * Multiplies the low unsigned 32-bit integers from each packed 64-bit element
 * in `a` and `b`.
 *
 * Returns the unsigned 64-bit results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mul_epu32)
 */
public fun mmMulEpu32(a: M128i, b: M128i): M128i =
    run {
        val a1 = a.asU64x2()
        val b1 = b.asU64x2()
        val mask = u64x2.splat(UInt.MAX_VALUE.toULong())
        transmute(simdMul(simdAnd(a1, mask), simdAnd(b1, mask)))
    }

/**
 * Sum the absolute differences of packed unsigned 8-bit integers.
 *
 * Computes the absolute differences of packed unsigned 8-bit integers in `a`
 * and `b`, then horizontally sum each consecutive 8 differences to produce
 * two unsigned 16-bit integers, and pack these unsigned 16-bit integers in
 * the low 16 bits of 64-bit elements returned.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sad_epu8)
 */
public fun mmSadEpu8(a: M128i, b: M128i): M128i = transmute(psadbw(a.asU8x16(), b.asU8x16()))

/**
 * Subtracts packed 8-bit integers in `b` from packed 8-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_epi8)
 */
public fun mmSubEpi8(a: M128i, b: M128i): M128i = transmute(simdSub(a.asI8x16(), b.asI8x16()))

/**
 * Subtracts packed 16-bit integers in `b` from packed 16-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_epi16)
 */
public fun mmSubEpi16(a: M128i, b: M128i): M128i = transmute(simdSub(a.asI16x8(), b.asI16x8()))

/**
 * Subtract packed 32-bit integers in `b` from packed 32-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_epi32)
 */
public fun mmSubEpi32(a: M128i, b: M128i): M128i = transmute(simdSub(a.asI32x4(), b.asI32x4()))

/**
 * Subtract packed 64-bit integers in `b` from packed 64-bit integers in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_epi64)
 */
public fun mmSubEpi64(a: M128i, b: M128i): M128i = transmute(simdSub(a.asI64x2(), b.asI64x2()))

/**
 * Subtract packed 8-bit integers in `b` from packed 8-bit integers in `a`
 * using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_subs_epi8)
 */
public fun mmSubsEpi8(a: M128i, b: M128i): M128i = transmute(simdSaturatingSub(a.asI8x16(), b.asI8x16()))

/**
 * Subtract packed 16-bit integers in `b` from packed 16-bit integers in `a`
 * using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_subs_epi16)
 */
public fun mmSubsEpi16(a: M128i, b: M128i): M128i = transmute(simdSaturatingSub(a.asI16x8(), b.asI16x8()))

/**
 * Subtract packed unsigned 8-bit integers in `b` from packed unsigned 8-bit
 * integers in `a` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_subs_epu8)
 */
public fun mmSubsEpu8(a: M128i, b: M128i): M128i = transmute(simdSaturatingSub(a.asU8x16(), b.asU8x16()))

/**
 * Subtract packed unsigned 16-bit integers in `b` from packed unsigned 16-bit
 * integers in `a` using saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_subs_epu16)
 */
public fun mmSubsEpu16(a: M128i, b: M128i): M128i = transmute(simdSaturatingSub(a.asU16x8(), b.asU16x8()))

/**
 * Shifts `a` left by `IMM8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_slli_si128)
 */
public fun mmSlliSi128(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmSlliSi128Impl(a, imm8)
}

/**
 * Implementation detail: converts the immediate argument of the
 * `_mm_slli_si128` intrinsic into a compile-time constant.
 */
public fun mmSlliSi128Impl(a: M128i, imm8: Int): M128i {
    fun mask(shift: Int, i: UInt): UInt {
        val shift1 = shift.toUInt() and 0xffu
        return if (shift1 > 15u) {
            i
        } else {
            16u - shift1 + i
        }
    }
    return transmute<I8x16>(simdShuffle(i8x16.ZERO, a.asI8x16(), SimdShuffleIdx(listOf(mask(imm8, 0u), mask(imm8, 1u), mask(imm8, 2u), mask(imm8, 3u), mask(imm8, 4u), mask(imm8, 5u), mask(imm8, 6u), mask(imm8, 7u), mask(imm8, 8u), mask(imm8, 9u), mask(imm8, 10u), mask(imm8, 11u), mask(imm8, 12u), mask(imm8, 13u), mask(imm8, 14u), mask(imm8, 15u)))))
}

/**
 * Shifts `a` left by `IMM8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_bslli_si128)
 */
public fun mmBslliSi128(a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        mmSlliSi128Impl(a, imm8)
    }

/**
 * Shifts `a` right by `IMM8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_bsrli_si128)
 */
public fun mmBsrliSi128(a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        mmSrliSi128Impl(a, imm8)
    }

/**
 * Shifts packed 16-bit integers in `a` left by `IMM8` while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_slli_epi16)
 */
public fun mmSlliEpi16(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 16) {
        mmSetzeroSi128()
    } else {
        transmute(simdShl(a.asU16x8(), u16x8.splat(imm8.toUShort())))
    }
}

/**
 * Shifts packed 16-bit integers in `a` left by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sll_epi16)
 */
public fun mmSllEpi16(a: M128i, count: M128i): M128i = transmute(psllw(a.asI16x8(), count.asI16x8()))

/**
 * Shifts packed 32-bit integers in `a` left by `IMM8` while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_slli_epi32)
 */
public fun mmSlliEpi32(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 32) {
        mmSetzeroSi128()
    } else {
        transmute(simdShl(a.asU32x4(), u32x4.splat(imm8.toUInt())))
    }
}

/**
 * Shifts packed 32-bit integers in `a` left by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sll_epi32)
 */
public fun mmSllEpi32(a: M128i, count: M128i): M128i = transmute(pslld(a.asI32x4(), count.asI32x4()))

/**
 * Shifts packed 64-bit integers in `a` left by `IMM8` while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_slli_epi64)
 */
public fun mmSlliEpi64(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 64) {
        mmSetzeroSi128()
    } else {
        transmute(simdShl(a.asU64x2(), u64x2.splat(imm8.toULong())))
    }
}

/**
 * Shifts packed 64-bit integers in `a` left by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sll_epi64)
 */
public fun mmSllEpi64(a: M128i, count: M128i): M128i = transmute(psllq(a.asI64x2(), count.asI64x2()))

/**
 * Shifts packed 16-bit integers in `a` right by `IMM8` while shifting in sign
 * bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srai_epi16)
 */
public fun mmSraiEpi16(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(simdShr(a.asI16x8(), i16x8.splat(imm8.min(15).toShort())))
}

/**
 * Shifts packed 16-bit integers in `a` right by `count` while shifting in sign
 * bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sra_epi16)
 */
public fun mmSraEpi16(a: M128i, count: M128i): M128i = transmute(psraw(a.asI16x8(), count.asI16x8()))

/**
 * Shifts packed 32-bit integers in `a` right by `IMM8` while shifting in sign
 * bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srai_epi32)
 */
public fun mmSraiEpi32(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute(simdShr(a.asI32x4(), i32x4.splat(imm8.min(31))))
}

/**
 * Shifts packed 32-bit integers in `a` right by `count` while shifting in sign
 * bits.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sra_epi32)
 */
public fun mmSraEpi32(a: M128i, count: M128i): M128i = transmute(psrad(a.asI32x4(), count.asI32x4()))

/**
 * Shifts `a` right by `IMM8` bytes while shifting in zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srli_si128)
 */
public fun mmSrliSi128(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmSrliSi128Impl(a, imm8)
}

/**
 * Implementation detail: converts the immediate argument of the
 * `_mm_srli_si128` intrinsic into a compile-time constant.
 */
public fun mmSrliSi128Impl(a: M128i, imm8: Int): M128i {
    fun mask(shift: Int, i: UInt): UInt =
        if ((shift.toUInt()) > 15u) {
            i + 16u
        } else {
            i + (shift.toUInt())
        }
    val x: I8x16 = simdShuffle(a.asI8x16(), i8x16.ZERO, SimdShuffleIdx(listOf(mask(imm8, 0u), mask(imm8, 1u), mask(imm8, 2u), mask(imm8, 3u), mask(imm8, 4u), mask(imm8, 5u), mask(imm8, 6u), mask(imm8, 7u), mask(imm8, 8u), mask(imm8, 9u), mask(imm8, 10u), mask(imm8, 11u), mask(imm8, 12u), mask(imm8, 13u), mask(imm8, 14u), mask(imm8, 15u))))
    return transmute(x)
}

/**
 * Shifts packed 16-bit integers in `a` right by `IMM8` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srli_epi16)
 */
public fun mmSrliEpi16(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 16) {
        mmSetzeroSi128()
    } else {
        transmute(simdShr(a.asU16x8(), u16x8.splat(imm8.toUShort())))
    }
}

/**
 * Shifts packed 16-bit integers in `a` right by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srl_epi16)
 */
public fun mmSrlEpi16(a: M128i, count: M128i): M128i = transmute(psrlw(a.asI16x8(), count.asI16x8()))

/**
 * Shifts packed 32-bit integers in `a` right by `IMM8` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srli_epi32)
 */
public fun mmSrliEpi32(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 32) {
        mmSetzeroSi128()
    } else {
        transmute(simdShr(a.asU32x4(), u32x4.splat(imm8.toUInt())))
    }
}

/**
 * Shifts packed 32-bit integers in `a` right by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srl_epi32)
 */
public fun mmSrlEpi32(a: M128i, count: M128i): M128i = transmute(psrld(a.asI32x4(), count.asI32x4()))

/**
 * Shifts packed 64-bit integers in `a` right by `IMM8` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srli_epi64)
 */
public fun mmSrliEpi64(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return if (imm8 >= 64) {
        mmSetzeroSi128()
    } else {
        transmute(simdShr(a.asU64x2(), u64x2.splat(imm8.toULong())))
    }
}

/**
 * Shifts packed 64-bit integers in `a` right by `count` while shifting in
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srl_epi64)
 */
public fun mmSrlEpi64(a: M128i, count: M128i): M128i = transmute(psrlq(a.asI64x2(), count.asI64x2()))

/**
 * Computes the bitwise AND of 128 bits (representing integer data) in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_and_si128)
 */
public fun mmAndSi128(a: M128i, b: M128i): M128i = simdAnd(a, b)

/**
 * Computes the bitwise NOT of 128 bits (representing integer data) in `a` and
 * then AND with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_andnot_si128)
 */
public fun mmAndnotSi128(a: M128i, b: M128i): M128i = simdAnd(simdXor(mmSet1Epi8((-1).toByte()), a), b)

/**
 * Computes the bitwise OR of 128 bits (representing integer data) in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_or_si128)
 */
public fun mmOrSi128(a: M128i, b: M128i): M128i = simdOr(a, b)

/**
 * Computes the bitwise XOR of 128 bits (representing integer data) in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_xor_si128)
 */
public fun mmXorSi128(a: M128i, b: M128i): M128i = simdXor(a, b)

/**
 * Compares packed 8-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epi8)
 */
public fun mmCmpeqEpi8(a: M128i, b: M128i): M128i = transmute<I8x16>(simdEq(a.asI8x16(), b.asI8x16()))

/**
 * Compares packed 16-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epi16)
 */
public fun mmCmpeqEpi16(a: M128i, b: M128i): M128i = transmute<I16x8>(simdEq(a.asI16x8(), b.asI16x8()))

/**
 * Compares packed 32-bit integers in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epi32)
 */
public fun mmCmpeqEpi32(a: M128i, b: M128i): M128i = transmute<I32x4>(simdEq(a.asI32x4(), b.asI32x4()))

/**
 * Compares packed 8-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epi8)
 */
public fun mmCmpgtEpi8(a: M128i, b: M128i): M128i = transmute<I8x16>(simdGt(a.asI8x16(), b.asI8x16()))

/**
 * Compares packed 16-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epi16)
 */
public fun mmCmpgtEpi16(a: M128i, b: M128i): M128i = transmute<I16x8>(simdGt(a.asI16x8(), b.asI16x8()))

/**
 * Compares packed 32-bit integers in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epi32)
 */
public fun mmCmpgtEpi32(a: M128i, b: M128i): M128i = transmute<I32x4>(simdGt(a.asI32x4(), b.asI32x4()))

/**
 * Compares packed 8-bit integers in `a` and `b` for less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epi8)
 */
public fun mmCmpltEpi8(a: M128i, b: M128i): M128i = transmute<I8x16>(simdLt(a.asI8x16(), b.asI8x16()))

/**
 * Compares packed 16-bit integers in `a` and `b` for less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epi16)
 */
public fun mmCmpltEpi16(a: M128i, b: M128i): M128i = transmute<I16x8>(simdLt(a.asI16x8(), b.asI16x8()))

/**
 * Compares packed 32-bit integers in `a` and `b` for less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epi32)
 */
public fun mmCmpltEpi32(a: M128i, b: M128i): M128i = transmute<I32x4>(simdLt(a.asI32x4(), b.asI32x4()))

/**
 * Converts the lower two packed 32-bit integers in `a` to packed
 * double-precision (64-bit) floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi32_pd)
 */
public fun mmCvtepi32Pd(a: M128i): M128d =
    run {
        val a1 = a.asI32x4()
        simdCast<I32x2, M128d>(simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1))))
    }

/**
 * Returns `a` with its lower element replaced by `b` after converting it to
 * an `Double`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi32_sd)
 */
public fun mmCvtsi32Sd(a: M128d, b: Int): M128d = simdInsert(a, 0, b.toDouble())

/**
 * Converts packed 32-bit integers in `a` to packed single-precision (32-bit)
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi32_ps)
 */
public fun mmCvtepi32Ps(a: M128i): M128 = transmute(simdCast<F32x4>(a.asI32x4()))

/**
 * Converts packed single-precision (32-bit) floating-point elements in `a`
 * to packed 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtps_epi32)
 */
public fun mmCvtpsEpi32(a: M128): M128i = transmute(cvtps2dq(a))

/**
 * Returns a vector whose lowest element is `a` and all higher elements are
 * `0`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi32_si128)
 */
public fun mmCvtsi32Si128(a: Int): M128i = transmute(i32x4.new(a, 0, 0, 0))

/**
 * Returns the lowest element of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi128_si32)
 */
public fun mmCvtsi128Si32(a: M128i): Int = simdExtract(a.asI32x4(), 0)

// no particular instruction to test

/**
 * Sets packed 64-bit integers with the supplied values, from highest to
 * lowest.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_epi64x)
 */
public fun mmSetEpi64x(e1: Long, e0: Long): M128i = transmute(i64x2.new(e0, e1))

// no particular instruction to test

/**
 * Sets packed 32-bit integers with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_epi32)
 */
public fun mmSetEpi32(e3: Int, e2: Int, e1: Int, e0: Int): M128i = transmute(i32x4.new(e0, e1, e2, e3))

// no particular instruction to test

/**
 * Sets packed 16-bit integers with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_epi16)
 */
public fun mmSetEpi16(e7: Short, e6: Short, e5: Short, e4: Short, e3: Short, e2: Short, e1: Short, e0: Short): M128i = transmute(i16x8.new(e0, e1, e2, e3, e4, e5, e6, e7))

// no particular instruction to test

/**
 * Sets packed 8-bit integers with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_epi8)
 */
public fun mmSetEpi8(e15: Byte, e14: Byte, e13: Byte, e12: Byte, e11: Byte, e10: Byte, e9: Byte, e8: Byte, e7: Byte, e6: Byte, e5: Byte, e4: Byte, e3: Byte, e2: Byte, e1: Byte, e0: Byte): M128i = transmute(i8x16.new(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15))

// no particular instruction to test

/**
 * Broadcasts 64-bit integer `a` to all elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set1_epi64x)
 */
public fun mmSet1Epi64x(a: Long): M128i = i64x2.splat(a).asM128i()

// no particular instruction to test

/**
 * Broadcasts 32-bit integer `a` to all elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set1_epi32)
 */
public fun mmSet1Epi32(a: Int): M128i = i32x4.splat(a).asM128i()

// no particular instruction to test

/**
 * Broadcasts 16-bit integer `a` to all elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set1_epi16)
 */
public fun mmSet1Epi16(a: Short): M128i = i16x8.splat(a).asM128i()

// no particular instruction to test

/**
 * Broadcasts 8-bit integer `a` to all elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set1_epi8)
 */
public fun mmSet1Epi8(a: Byte): M128i = i8x16.splat(a).asM128i()

// no particular instruction to test

/**
 * Sets packed 32-bit integers with the supplied values in reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setr_epi32)
 */
public fun mmSetrEpi32(e3: Int, e2: Int, e1: Int, e0: Int): M128i = mmSetEpi32(e0, e1, e2, e3)

// no particular instruction to test

/**
 * Sets packed 16-bit integers with the supplied values in reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setr_epi16)
 */
public fun mmSetrEpi16(e7: Short, e6: Short, e5: Short, e4: Short, e3: Short, e2: Short, e1: Short, e0: Short): M128i = mmSetEpi16(e0, e1, e2, e3, e4, e5, e6, e7)

// no particular instruction to test

/**
 * Sets packed 8-bit integers with the supplied values in reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setr_epi8)
 */
public fun mmSetrEpi8(e15: Byte, e14: Byte, e13: Byte, e12: Byte, e11: Byte, e10: Byte, e9: Byte, e8: Byte, e7: Byte, e6: Byte, e5: Byte, e4: Byte, e3: Byte, e2: Byte, e1: Byte, e0: Byte): M128i = mmSetEpi8(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15)

/**
 * Returns a vector with all elements set to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setzero_si128)
 */
public fun mmSetzeroSi128(): M128i = null

/**
 * Loads 64-bit integer from memory into first element of returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadl_epi64)
 */
public fun mmLoadlEpi64(memAddr: ConstPtr): M128i = mmSetEpi64x(0L, ptr.readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = PrimitiveLayouts.I64))

/**
 * Loads 128-bits of integer data from memory into a new vector.
 *
 * `memAddr` must be aligned on a 16-byte boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_si128)
 */
public fun mmLoadSi128(memAddr: ConstPtr): M128i = (ObjectSlotLayout<M128i>()).readNative((memAddr).rawAddress)

/**
 * Loads 128-bits of integer data from memory into a new vector.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_si128)
 */
public fun mmLoaduSi128(memAddr: ConstPtr): M128i {
    val dst: M128i  = mmUndefinedSi128()
    ptr.copyNonoverlapping(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), mutPtrFromRustCast(addrOfMut(dst)), mem.sizeOf<M128i>(typeLayoutT = ObjectSlotLayout<M128i>()), typeLayoutT = PrimitiveLayouts.U8)
    return dst
}

/**
 * Conditionally store 8-bit integer elements from `a` into memory using
 * `mask` flagged as non-temporal (unlikely to be used again soon).
 *
 * Elements are not stored when the highest bit is not set in the
 * corresponding element.
 *
 * `memAddr` should correspond to a 128-bit memory location and does not need
 * to be aligned on any particular boundary. The memory range starting at `memAddr`
 * and going on for 16 bytes must be contained in a mutable allocation, even if `mask` is all-0.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskmoveu_si128)
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmMaskmoveuSi128(a: M128i, mask: M128i, memAddr: MutPtr) {
    maskmovdqu(a.asI8x16(), mask.asI8x16(), memAddr)
}

/**
 * Stores 128-bits of integer data from `a` into memory.
 *
 * `memAddr` must be aligned on a 16-byte boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_si128)
 */
public fun mmStoreSi128(memAddr: MutPtr, a: M128i) {
    val memAddr1 = memAddr
    (ObjectSlotLayout<M128i>()).writeNative((memAddr1).rawAddress, a)
}

// Note movdqu expected

/**
 * Stores 128-bits of integer data from `a` into memory.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_si128)
 */
public fun mmStoreuSi128(memAddr: MutPtr, a: M128i) {
    (ObjectSlotLayout<M128i>()).writeNative((memAddr).rawAddress, a)
}

/**
 * Stores the lower 64-bit integer `a` to a memory location.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storel_epi64)
 */
public fun mmStorelEpi64(memAddr: MutPtr, a: M128i) {
    ptr.copyNonoverlapping(constPtrFromRustCast(addrOf(a)), MutPtr(memAddr.rawAddress, memAddr.rawMeta), 8, typeLayoutT = PrimitiveLayouts.U8)
}

/**
 * Stores a 128-bit integer vector to a 128-bit aligned memory location.
 * To minimize caching, the data is flagged as non-temporal (unlikely to be
 * used again soon).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_stream_si128)
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmStreamSi128(memAddr: MutPtr, a: M128i) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    asm(vps("movntdq", ",{a}"), p, reg, memAddr, a, xmmReg, a, options(nostack, preservesFlags))
}

/**
 * Stores a 32-bit integer value in a 4-byte aligned memory location.
 * To minimize caching, the data is flagged as non-temporal (unlikely to be
 * used again soon).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_stream_si32)
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmStreamSi32(memAddr: MutPtr, a: Int) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    asm(vps("movnti", ",{a:e}"), p, reg, memAddr, a, reg, a, options(nostack, preservesFlags))
}

// Note movd on msvc, movd on i686

/**
 * Returns a vector where the low element is extracted from `a` and its upper
 * element is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_move_epi64)
 */
public fun mmMoveEpi64(a: M128i): M128i =
    run {
        val r: I64x2 = simdShuffle(a.asI64x2(), i64x2.ZERO, SimdShuffleIdx(listOf(0, 2)))
        transmute(r)
    }

/**
 * Converts packed 16-bit integers from `a` and `b` to packed 8-bit integers
 * using signed saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_packs_epi16)
 */
public fun mmPacksEpi16(a: M128i, b: M128i): M128i = transmute(packsswb(a.asI16x8(), b.asI16x8()))

/**
 * Converts packed 32-bit integers from `a` and `b` to packed 16-bit integers
 * using signed saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_packs_epi32)
 */
public fun mmPacksEpi32(a: M128i, b: M128i): M128i = transmute(packssdw(a.asI32x4(), b.asI32x4()))

/**
 * Converts packed 16-bit integers from `a` and `b` to packed 8-bit integers
 * using unsigned saturation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_packus_epi16)
 */
public fun mmPackusEpi16(a: M128i, b: M128i): M128i = transmute(packuswb(a.asI16x8(), b.asI16x8()))

/**
 * Returns the `imm8` element of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_extract_epi16)
 */
public fun mmExtractEpi16(a: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
    }
    return (simdExtract<UShort>(a.asU16x8(), imm8.toUInt())).toInt()
}

/**
 * Returns a new vector where the `imm8` element of `a` is replaced with `i`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_insert_epi16)
 */
public fun mmInsertEpi16(a: M128i, i: Int, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(simdInsert(a.asI16x8(), imm8.toUInt(), i.toShort()))
}

/**
 * Returns a mask of the most significant bit of each element in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movemask_epi8)
 */
public fun mmMovemaskEpi8(a: M128i): Int =
    run {
        val z = i8x16.ZERO
        val m: I8x16 = simdLt(a.asI8x16(), z)
        (simdBitmask<UShort>(m).toUInt()).toInt()
    }

/**
 * Shuffles 32-bit integers in `a` using the control in `IMM8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shuffle_epi32)
 */
public fun mmShuffleEpi32(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI32x4()
        val x: I32x4 = simdShuffle(a1, a1, SimdShuffleIdx(uintArrayOf(imm8.toUInt() and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u)))
        transmute(x)
    }
}

/**
 * Shuffles 16-bit integers in the high 64 bits of `a` using the control in
 * `IMM8`.
 *
 * Put the results in the high 64 bits of the returned vector, with the low 64
 * bits being copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shufflehi_epi16)
 */
public fun mmShufflehiEpi16(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI16x8()
        val x: I16x8 = simdShuffle(a1, a1, SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, (imm8.toUInt() and 0x3u) + 4u, ((imm8.toUInt() shr 2) and 0x3u) + 4u, ((imm8.toUInt() shr 4) and 0x3u) + 4u, ((imm8.toUInt() shr 6) and 0x3u) + 4u)))
        transmute(x)
    }
}

/**
 * Shuffles 16-bit integers in the low 64 bits of `a` using the control in
 * `IMM8`.
 *
 * Put the results in the low 64 bits of the returned vector, with the high 64
 * bits being copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shufflelo_epi16)
 */
public fun mmShuffleloEpi16(a: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val a1 = a.asI16x8()
        val x: I16x8 = simdShuffle(a1, a1, SimdShuffleIdx(uintArrayOf(imm8.toUInt() and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u, 4u, 5u, 6u, 7u)))
        transmute(x)
    }
}

/**
 * Unpacks and interleave 8-bit integers from the high half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpackhi_epi8)
 */
public fun mmUnpackhiEpi8(a: M128i, b: M128i): M128i = transmute<I8x16>(simdShuffle(a.asI8x16(), b.asI8x16(), SimdShuffleIdx(listOf(8, 24, 9, 25, 10, 26, 11, 27, 12, 28, 13, 29, 14, 30, 15, 31))))

/**
 * Unpacks and interleave 16-bit integers from the high half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpackhi_epi16)
 */
public fun mmUnpackhiEpi16(a: M128i, b: M128i): M128i =
    run {
        val x = simdShuffle(a.asI16x8(), b.asI16x8(), SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)))
        transmute<I16x8>(x)
    }

/**
 * Unpacks and interleave 32-bit integers from the high half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpackhi_epi32)
 */
public fun mmUnpackhiEpi32(a: M128i, b: M128i): M128i = transmute<I32x4>(simdShuffle(a.asI32x4(), b.asI32x4(), SimdShuffleIdx(listOf(2, 6, 3, 7))))

/**
 * Unpacks and interleave 64-bit integers from the high half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpackhi_epi64)
 */
public fun mmUnpackhiEpi64(a: M128i, b: M128i): M128i = transmute<I64x2>(simdShuffle(a.asI64x2(), b.asI64x2(), SimdShuffleIdx(listOf(1, 3))))

/**
 * Unpacks and interleave 8-bit integers from the low half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpacklo_epi8)
 */
public fun mmUnpackloEpi8(a: M128i, b: M128i): M128i = transmute<I8x16>(simdShuffle(a.asI8x16(), b.asI8x16(), SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 4, 20, 5, 21, 6, 22, 7, 23))))

/**
 * Unpacks and interleave 16-bit integers from the low half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpacklo_epi16)
 */
public fun mmUnpackloEpi16(a: M128i, b: M128i): M128i =
    run {
        val x = simdShuffle(a.asI16x8(), b.asI16x8(), SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)))
        transmute<I16x8>(x)
    }

/**
 * Unpacks and interleave 32-bit integers from the low half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpacklo_epi32)
 */
public fun mmUnpackloEpi32(a: M128i, b: M128i): M128i = transmute<I32x4>(simdShuffle(a.asI32x4(), b.asI32x4(), SimdShuffleIdx(listOf(0, 4, 1, 5))))

/**
 * Unpacks and interleave 64-bit integers from the low half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpacklo_epi64)
 */
public fun mmUnpackloEpi64(a: M128i, b: M128i): M128i = transmute<I64x2>(simdShuffle(a.asI64x2(), b.asI64x2(), SimdShuffleIdx(listOf(0, 2))))

/**
 * Returns a new vector with the low element of `a` replaced by the sum of the
 * low elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_sd)
 */
public fun mmAddSd(a: M128d, b: M128d): M128d = simdInsert(a, 0, mmCvtsdF64(a) + mmCvtsdF64(b))

/**
 * Adds packed double-precision (64-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_pd)
 */
public fun mmAddPd(a: M128d, b: M128d): M128d = simdAdd(a, b)

/**
 * Returns a new vector with the low element of `a` replaced by the result of
 * diving the lower element of `a` by the lower element of `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_div_sd)
 */
public fun mmDivSd(a: M128d, b: M128d): M128d = simdInsert(a, 0, mmCvtsdF64(a) / mmCvtsdF64(b))

/**
 * Divide packed double-precision (64-bit) floating-point elements in `a` by
 * packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_div_pd)
 */
public fun mmDivPd(a: M128d, b: M128d): M128d = simdDiv(a, b)

/**
 * Returns a new vector with the low element of `a` replaced by the maximum
 * of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_sd)
 */
public fun mmMaxSd(a: M128d, b: M128d): M128d = maxsd(a, b)

/**
 * Returns a new vector with the maximum values from corresponding elements in
 * `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_pd)
 */
public fun mmMaxPd(a: M128d, b: M128d): M128d = maxpd(a, b)

/**
 * Returns a new vector with the low element of `a` replaced by the minimum
 * of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_sd)
 */
public fun mmMinSd(a: M128d, b: M128d): M128d = minsd(a, b)

/**
 * Returns a new vector with the minimum values from corresponding elements in
 * `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_pd)
 */
public fun mmMinPd(a: M128d, b: M128d): M128d = minpd(a, b)

/**
 * Returns a new vector with the low element of `a` replaced by multiplying the
 * low elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mul_sd)
 */
public fun mmMulSd(a: M128d, b: M128d): M128d = simdInsert(a, 0, mmCvtsdF64(a) * mmCvtsdF64(b))

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mul_pd)
 */
public fun mmMulPd(a: M128d, b: M128d): M128d = simdMul(a, b)

/**
 * Returns a new vector with the low element of `a` replaced by the square
 * root of the lower element `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sqrt_sd)
 */
public fun mmSqrtSd(a: M128d, b: M128d): M128d = simdInsert(a, 0, sqrt(mmCvtsdF64(b)))

/**
 * Returns a new vector with the square root of each of the values in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sqrt_pd)
 */
public fun mmSqrtPd(a: M128d): M128d = simdFsqrt(a)

/**
 * Returns a new vector with the low element of `a` replaced by subtracting the
 * low element by `b` from the low element of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_sd)
 */
public fun mmSubSd(a: M128d, b: M128d): M128d = simdInsert(a, 0, mmCvtsdF64(a) - mmCvtsdF64(b))

/**
 * Subtract packed double-precision (64-bit) floating-point elements in `b`
 * from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_pd)
 */
public fun mmSubPd(a: M128d, b: M128d): M128d = simdSub(a, b)

/**
 * Computes the bitwise AND of packed double-precision (64-bit) floating-point
 * elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_and_pd)
 */
public fun mmAndPd(a: M128d, b: M128d): M128d =
    run {
        val a1: M128i = transmute(a)
        val b1: M128i = transmute(b)
        transmute(mmAndSi128(a1, b1))
    }

/**
 * Computes the bitwise NOT of `a` and then AND with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_andnot_pd)
 */
public fun mmAndnotPd(a: M128d, b: M128d): M128d =
    run {
        val a1: M128i = transmute(a)
        val b1: M128i = transmute(b)
        transmute(mmAndnotSi128(a1, b1))
    }

/**
 * Computes the bitwise OR of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_or_pd)
 */
public fun mmOrPd(a: M128d, b: M128d): M128d =
    run {
        val a1: M128i = transmute(a)
        val b1: M128i = transmute(b)
        transmute(mmOrSi128(a1, b1))
    }

/**
 * Computes the bitwise XOR of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_xor_pd)
 */
public fun mmXorPd(a: M128d, b: M128d): M128d =
    run {
        val a1: M128i = transmute(a)
        val b1: M128i = transmute(b)
        transmute(mmXorSi128(a1, b1))
    }

/**
 * Returns a new vector with the low element of `a` replaced by the equality
 * comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_sd)
 */
public fun mmCmpeqSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 0)

/**
 * Returns a new vector with the low element of `a` replaced by the less-than
 * comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_sd)
 */
public fun mmCmpltSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 1)

/**
 * Returns a new vector with the low element of `a` replaced by the
 * less-than-or-equal comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_sd)
 */
public fun mmCmpleSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 2)

/**
 * Returns a new vector with the low element of `a` replaced by the
 * greater-than comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_sd)
 */
public fun mmCmpgtSd(a: M128d, b: M128d): M128d = simdInsert(mmCmpltSd(b, a), 1, simdExtract<Double>(a, 1))

/**
 * Returns a new vector with the low element of `a` replaced by the
 * greater-than-or-equal comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_sd)
 */
public fun mmCmpgeSd(a: M128d, b: M128d): M128d = simdInsert(mmCmpleSd(b, a), 1, simdExtract<Double>(a, 1))

/**
 * Returns a new vector with the low element of `a` replaced by the result
 * of comparing both of the lower elements of `a` and `b` to `NaN`. If
 * neither are equal to `NaN` then `0xFFFFFFFFFFFFFFFF` is used and `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpord_sd)
 */
public fun mmCmpordSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 7)

/**
 * Returns a new vector with the low element of `a` replaced by the result of
 * comparing both of the lower elements of `a` and `b` to `NaN`. If either is
 * equal to `NaN` then `0xFFFFFFFFFFFFFFFF` is used and `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpunord_sd)
 */
public fun mmCmpunordSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 3)

/**
 * Returns a new vector with the low element of `a` replaced by the not-equal
 * comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_sd)
 */
public fun mmCmpneqSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 4)

/**
 * Returns a new vector with the low element of `a` replaced by the
 * not-less-than comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnlt_sd)
 */
public fun mmCmpnltSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 5)

/**
 * Returns a new vector with the low element of `a` replaced by the
 * not-less-than-or-equal comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnle_sd)
 */
public fun mmCmpnleSd(a: M128d, b: M128d): M128d = cmpsd(a, b, 6)

/**
 * Returns a new vector with the low element of `a` replaced by the
 * not-greater-than comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpngt_sd)
 */
public fun mmCmpngtSd(a: M128d, b: M128d): M128d = simdInsert(mmCmpnltSd(b, a), 1, simdExtract<Double>(a, 1))

/**
 * Returns a new vector with the low element of `a` replaced by the
 * not-greater-than-or-equal comparison of the lower elements of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnge_sd)
 */
public fun mmCmpngeSd(a: M128d, b: M128d): M128d = simdInsert(mmCmpnleSd(b, a), 1, simdExtract<Double>(a, 1))

/**
 * Compares corresponding elements in `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_pd)
 */
public fun mmCmpeqPd(a: M128d, b: M128d): M128d = cmppd(a, b, 0)

/**
 * Compares corresponding elements in `a` and `b` for less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_pd)
 */
public fun mmCmpltPd(a: M128d, b: M128d): M128d = cmppd(a, b, 1)

/**
 * Compares corresponding elements in `a` and `b` for less-than-or-equal
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_pd)
 */
public fun mmCmplePd(a: M128d, b: M128d): M128d = cmppd(a, b, 2)

/**
 * Compares corresponding elements in `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_pd)
 */
public fun mmCmpgtPd(a: M128d, b: M128d): M128d = mmCmpltPd(b, a)

/**
 * Compares corresponding elements in `a` and `b` for greater-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_pd)
 */
public fun mmCmpgePd(a: M128d, b: M128d): M128d = mmCmplePd(b, a)

/**
 * Compares corresponding elements in `a` and `b` to see if neither is `NaN`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpord_pd)
 */
public fun mmCmpordPd(a: M128d, b: M128d): M128d = cmppd(a, b, 7)

/**
 * Compares corresponding elements in `a` and `b` to see if either is `NaN`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpunord_pd)
 */
public fun mmCmpunordPd(a: M128d, b: M128d): M128d = cmppd(a, b, 3)

/**
 * Compares corresponding elements in `a` and `b` for not-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_pd)
 */
public fun mmCmpneqPd(a: M128d, b: M128d): M128d = cmppd(a, b, 4)

/**
 * Compares corresponding elements in `a` and `b` for not-less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnlt_pd)
 */
public fun mmCmpnltPd(a: M128d, b: M128d): M128d = cmppd(a, b, 5)

/**
 * Compares corresponding elements in `a` and `b` for not-less-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnle_pd)
 */
public fun mmCmpnlePd(a: M128d, b: M128d): M128d = cmppd(a, b, 6)

/**
 * Compares corresponding elements in `a` and `b` for not-greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpngt_pd)
 */
public fun mmCmpngtPd(a: M128d, b: M128d): M128d = mmCmpnltPd(b, a)

/**
 * Compares corresponding elements in `a` and `b` for
 * not-greater-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnge_pd)
 */
public fun mmCmpngePd(a: M128d, b: M128d): M128d = mmCmpnlePd(b, a)

/**
 * Compares the lower element of `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comieq_sd)
 */
public fun mmComieqSd(a: M128d, b: M128d): Int = comieqsd(a, b)

/**
 * Compares the lower element of `a` and `b` for less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comilt_sd)
 */
public fun mmComiltSd(a: M128d, b: M128d): Int = comiltsd(a, b)

/**
 * Compares the lower element of `a` and `b` for less-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comile_sd)
 */
public fun mmComileSd(a: M128d, b: M128d): Int = comilesd(a, b)

/**
 * Compares the lower element of `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comigt_sd)
 */
public fun mmComigtSd(a: M128d, b: M128d): Int = comigtsd(a, b)

/**
 * Compares the lower element of `a` and `b` for greater-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comige_sd)
 */
public fun mmComigeSd(a: M128d, b: M128d): Int = comigesd(a, b)

/**
 * Compares the lower element of `a` and `b` for not-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comineq_sd)
 */
public fun mmComineqSd(a: M128d, b: M128d): Int = comineqsd(a, b)

/**
 * Compares the lower element of `a` and `b` for equality.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomieq_sd)
 */
public fun mmUcomieqSd(a: M128d, b: M128d): Int = ucomieqsd(a, b)

/**
 * Compares the lower element of `a` and `b` for less-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomilt_sd)
 */
public fun mmUcomiltSd(a: M128d, b: M128d): Int = ucomiltsd(a, b)

/**
 * Compares the lower element of `a` and `b` for less-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomile_sd)
 */
public fun mmUcomileSd(a: M128d, b: M128d): Int = ucomilesd(a, b)

/**
 * Compares the lower element of `a` and `b` for greater-than.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomigt_sd)
 */
public fun mmUcomigtSd(a: M128d, b: M128d): Int = ucomigtsd(a, b)

/**
 * Compares the lower element of `a` and `b` for greater-than-or-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomige_sd)
 */
public fun mmUcomigeSd(a: M128d, b: M128d): Int = ucomigesd(a, b)

/**
 * Compares the lower element of `a` and `b` for not-equal.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomineq_sd)
 */
public fun mmUcomineqSd(a: M128d, b: M128d): Int = ucomineqsd(a, b)

/**
 * Converts packed double-precision (64-bit) floating-point elements in `a` to
 * packed single-precision (32-bit) floating-point elements
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtpd_ps)
 */
public fun mmCvtpdPs(a: M128d): M128 =
    run {
        val r = simdCast<F32x2>(a.asF64x2())
        val zero = f32x2.ZERO
        transmute<F32x4>(simdShuffle(r, zero, SimdShuffleIdx(listOf(0, 1, 2, 3))))
    }

/**
 * Converts packed single-precision (32-bit) floating-point elements in `a` to
 * packed
 * double-precision (64-bit) floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtps_pd)
 */
public fun mmCvtpsPd(a: M128): M128d =
    run {
        val a1 = a.asF32x4()
        transmute(simdCast<F32x2, F64x2>(simdShuffle(a1, a1, SimdShuffleIdx(listOf(0, 1)))))
    }

/**
 * Converts packed double-precision (64-bit) floating-point elements in `a` to
 * packed 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtpd_epi32)
 */
public fun mmCvtpdEpi32(a: M128d): M128i = transmute(cvtpd2dq(a))

/**
 * Converts the lower double-precision (64-bit) floating-point element in a to
 * a 32-bit integer.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_si32)
 */
public fun mmCvtsdSi32(a: M128d): Int = cvtsd2si(a)

/**
 * Converts the lower double-precision (64-bit) floating-point element in `b`
 * to a single-precision (32-bit) floating-point element, store the result in
 * the lower element of the return value, and copies the upper element from `a`
 * to the upper element the return value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_ss)
 */
public fun mmCvtsdSs(a: M128, b: M128d): M128 = cvtsd2ss(a, b)

/**
 * Returns the lower double-precision (64-bit) floating-point element of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_f64)
 */
public fun mmCvtsdF64(a: M128d): Double = simdExtract(a, 0)

/**
 * Converts the lower single-precision (32-bit) floating-point element in `b`
 * to a double-precision (64-bit) floating-point element, store the result in
 * the lower element of the return value, and copies the upper element from `a`
 * to the upper element the return value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtss_sd)
 */
public fun mmCvtssSd(a: M128d, b: M128): M128d =
    run {
        val elt: Float = simdExtract(b, 0)
        simdInsert(a, 0, elt.toDouble())
    }

/**
 * Converts packed double-precision (64-bit) floating-point elements in `a` to
 * packed 32-bit integers with truncation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttpd_epi32)
 */
public fun mmCvttpdEpi32(a: M128d): M128i = transmute(cvttpd2dq(a))

/**
 * Converts the lower double-precision (64-bit) floating-point element in `a`
 * to a 32-bit integer with truncation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttsd_si32)
 */
public fun mmCvttsdSi32(a: M128d): Int = cvttsd2si(a)

/**
 * Converts packed single-precision (32-bit) floating-point elements in `a` to
 * packed 32-bit integers with truncation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttps_epi32)
 */
public fun mmCvttpsEpi32(a: M128): M128i = transmute(cvttps2dq(a))

/**
 * Copies double-precision (64-bit) floating-point element `a` to the lower
 * element of the packed 64-bit return value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_sd)
 */
public fun mmSetSd(a: Double): M128d = mmSetPd(0.0, a)

/**
 * Broadcasts double-precision (64-bit) floating-point value a to all elements
 * of the return value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set1_pd)
 */
public fun mmSet1Pd(a: Double): M128d = mmSetPd(a, a)

/**
 * Broadcasts double-precision (64-bit) floating-point value a to all elements
 * of the return value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_pd1)
 */
public fun mmSetPd1(a: Double): M128d = mmSetPd(a, a)

/**
 * Sets packed double-precision (64-bit) floating-point elements in the return
 * value with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_pd)
 */
public fun mmSetPd(a: Double, b: Double): M128d = m128d(doubleArrayOf(b, a))

/**
 * Sets packed double-precision (64-bit) floating-point elements in the return
 * value with the supplied values in reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setr_pd)
 */
public fun mmSetrPd(a: Double, b: Double): M128d = mmSetPd(b, a)

/**
 * Returns packed double-precision (64-bit) floating-point elements with all
 * zeros.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setzero_pd)
 */
public fun mmSetzeroPd(): M128d = null

/**
 * Returns a mask of the most significant bit of each element in `a`.
 *
 * The mask is stored in the 2 least significant bits of the return value.
 * All other bits are set to `0`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movemask_pd)
 */
public fun mmMovemaskPd(a: M128d): Int {
    // Propagate the highest bit to the rest, because simdBitmask
    // requires all-1 or all-0.
    return run {
        val mask: I64x2 = simdLt(transmute(a), i64x2.ZERO)
        (simdBitmask<I64x2, UByte>(mask)).toInt()
    }
}

/**
 * Loads 128-bits (composed of 2 packed double-precision (64-bit)
 * floating-point elements) from memory into the returned vector.
 * `memAddr` must be aligned on a 16-byte boundary or a general-protection
 * exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_pd)
 */
public fun mmLoadPd(memAddr: ConstPtr): M128d = (ObjectSlotLayout<M128d>()).readNative(((ConstPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress)

/**
 * Loads a 64-bit double-precision value to the low element of a
 * 128-bit integer vector and clears the upper element.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_sd)
 */
public fun mmLoadSd(memAddr: ConstPtr): M128d = mmSetrPd((PrimitiveLayouts.F64).readNative((memAddr).rawAddress), 0.0)

/**
 * Loads a double-precision value into the high-order bits of a 128-bit
 * vector of `[2 x double]`. The low-order bits are copied from the low-order
 * bits of the first operand.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadh_pd)
 */
public fun mmLoadhPd(a: M128d, memAddr: ConstPtr): M128d = mmSetrPd(simdExtract(a, 0), (PrimitiveLayouts.F64).readNative((memAddr).rawAddress))

/**
 * Loads a double-precision value into the low-order bits of a 128-bit
 * vector of `[2 x double]`. The high-order bits are copied from the
 * high-order bits of the first operand.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadl_pd)
 */
public fun mmLoadlPd(a: M128d, memAddr: ConstPtr): M128d = mmSetrPd((PrimitiveLayouts.F64).readNative((memAddr).rawAddress), simdExtract(a, 1))

/**
 * Stores a 128-bit floating point vector of `[2 x double]` to a 128-bit
 * aligned memory location.
 * To minimize caching, the data is flagged as non-temporal (unlikely to be
 * used again soon).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_stream_pd)
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmStreamPd(memAddr: MutPtr, a: M128d) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    asm(vps("movntpd", ",{a}"), p, reg, memAddr, a, xmmReg, a, options(nostack, preservesFlags))
}

/**
 * Stores the lower 64 bits of a 128-bit vector of `[2 x double]` to a
 * memory location.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_sd)
 */
public fun mmStoreSd(memAddr: MutPtr, a: M128d) {
    val memAddr1 = memAddr
    (PrimitiveLayouts.F64).writeNative((memAddr1).rawAddress, simdExtract(a, 0))
}

/**
 * Stores 128-bits (composed of 2 packed double-precision (64-bit)
 * floating-point elements) from `a` into memory. `memAddr` must be aligned
 * on a 16-byte boundary or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_pd)
 */
public fun mmStorePd(memAddr: MutPtr, a: M128d) {
    (ObjectSlotLayout<M128d>()).writeNative(((MutPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress, a)
}

// Note movupd expected

/**
 * Stores 128-bits (composed of 2 packed double-precision (64-bit)
 * floating-point elements) from `a` into memory.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_pd)
 */
public fun mmStoreuPd(memAddr: MutPtr, a: M128d) {
    (ObjectSlotLayout<M128d>()).writeNative((MutPtr((memAddr).rawAddress)).rawAddress, a)
}

/**
 * Store 16-bit integer from the first element of a into memory.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_si16)
 */
public fun mmStoreuSi16(memAddr: MutPtr, a: M128i) {
    ptr.writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), simdExtract(a.asI16x8(), 0), typeLayoutT = PrimitiveLayouts.I16)
}

/**
 * Store 32-bit integer from the first element of a into memory.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_si32)
 */
public fun mmStoreuSi32(memAddr: MutPtr, a: M128i) {
    ptr.writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), simdExtract(a.asI32x4(), 0), typeLayoutT = PrimitiveLayouts.I32)
}

/**
 * Store 64-bit integer from the first element of a into memory.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_si64)
 */
public fun mmStoreuSi64(memAddr: MutPtr, a: M128i) {
    ptr.writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), simdExtract(a.asI64x2(), 0), typeLayoutT = PrimitiveLayouts.I64)
}

/**
 * Stores the lower double-precision (64-bit) floating-point element from `a`
 * into 2 contiguous elements in memory. `memAddr` must be aligned on a
 * 16-byte boundary or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store1_pd)
 */
public fun mmStore1Pd(memAddr: MutPtr, a: M128d) {
    val b: M128d = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0)))
    (ObjectSlotLayout<M128d>()).writeNative(((MutPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress, b)
}

/**
 * Stores the lower double-precision (64-bit) floating-point element from `a`
 * into 2 contiguous elements in memory. `memAddr` must be aligned on a
 * 16-byte boundary or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_pd1)
 */
public fun mmStorePd1(memAddr: MutPtr, a: M128d) {
    val b: M128d = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0)))
    (ObjectSlotLayout<M128d>()).writeNative(((MutPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress, b)
}

/**
 * Stores 2 double-precision (64-bit) floating-point elements from `a` into
 * memory in reverse order.
 * `memAddr` must be aligned on a 16-byte boundary or a general-protection
 * exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storer_pd)
 */
public fun mmStorerPd(memAddr: MutPtr, a: M128d) {
    val b: M128d = simdShuffle(a, a, SimdShuffleIdx(listOf(1, 0)))
    (ObjectSlotLayout<M128d>()).writeNative(((MutPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress, b)
}

/**
 * Stores the upper 64 bits of a 128-bit vector of `[2 x double]` to a
 * memory location.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeh_pd)
 */
public fun mmStorehPd(memAddr: MutPtr, a: M128d) {
    val memAddr1 = memAddr
    (PrimitiveLayouts.F64).writeNative((memAddr1).rawAddress, simdExtract(a, 1))
}

/**
 * Stores the lower 64 bits of a 128-bit vector of `[2 x double]` to a
 * memory location.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storel_pd)
 */
public fun mmStorelPd(memAddr: MutPtr, a: M128d) {
    val memAddr1 = memAddr
    (PrimitiveLayouts.F64).writeNative((memAddr1).rawAddress, simdExtract(a, 0))
}

// @cfgAttr(test, assertInstr(movapd)) // Note LLVM uses different codegen

/**
 * Loads a double-precision (64-bit) floating-point element from memory
 * into both elements of returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load1_pd)
 */
public fun mmLoad1Pd(memAddr: ConstPtr): M128d {
    val d = (PrimitiveLayouts.F64).readNative((memAddr).rawAddress)
    return mmSetrPd(d, d)
}

// @cfgAttr(test, assertInstr(movapd)) // Note same as _mm_load1_pd

/**
 * Loads a double-precision (64-bit) floating-point element from memory
 * into both elements of returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_pd1)
 */
public fun mmLoadPd1(memAddr: ConstPtr): M128d = mmLoad1Pd(memAddr)

/**
 * Loads 2 double-precision (64-bit) floating-point elements from memory into
 * the returned vector in reverse order. `memAddr` must be aligned on a
 * 16-byte boundary or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadr_pd)
 */
public fun mmLoadrPd(memAddr: ConstPtr): M128d {
    val a = mmLoadPd(memAddr)
    return simdShuffle(a, a, SimdShuffleIdx(listOf(1, 0)))
}

/**
 * Loads 128-bits (composed of 2 packed double-precision (64-bit)
 * floating-point elements) from memory into the returned vector.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_pd)
 */
public fun mmLoaduPd(memAddr: ConstPtr): M128d {
    val dst = mmUndefinedPd()
    ptr.copyNonoverlapping(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), mutPtrFromRustCast(addrOfMut(dst)), mem.sizeOf<M128d>(typeLayoutT = ObjectSlotLayout<M128d>()), typeLayoutT = PrimitiveLayouts.U8)
    return dst
}

/**
 * Loads unaligned 16-bits of integer data from memory into new vector.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_si16)
 */
public fun mmLoaduSi16(memAddr: ConstPtr): M128i = transmute(i16x8.new(ptr.readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = PrimitiveLayouts.I16), 0, 0, 0, 0, 0, 0, 0))

/**
 * Loads unaligned 32-bits of integer data from memory into new vector.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_si32)
 */
public fun mmLoaduSi32(memAddr: ConstPtr): M128i = transmute(i32x4.new(ptr.readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = PrimitiveLayouts.I32), 0, 0, 0))

/**
 * Loads unaligned 64-bits of integer data from memory into new vector.
 *
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_si64)
 */
public fun mmLoaduSi64(memAddr: ConstPtr): M128i = transmute(i64x2.new(ptr.readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = PrimitiveLayouts.I64), 0))

/**
 * Constructs a 128-bit floating-point vector of `[2 x double]` from two
 * 128-bit vector parameters of `[2 x double]`, using the immediate-value
 * parameter as a specifier.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shuffle_pd)
 */
public fun mmShufflePd(a: M128d, b: M128d, mask: Int): M128d {
    run {
        check(0 <= mask && mask < (1 shl 8)) { "MASK doesn't fit in 8 bits (unsigned)" }
    }
    return simdShuffle(a, b, SimdShuffleIdx(uintArrayOf(mask.toUInt() and 0x1u, ((mask.toUInt() shr 1) and 0x1u) + 2u)))
}

/**
 * Constructs a 128-bit floating-point vector of `[2 x double]`. The lower
 * 64 bits are set to the lower 64 bits of the second parameter. The upper
 * 64 bits are set to the upper 64 bits of the first parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_move_sd)
 */
public fun mmMoveSd(a: M128d, b: M128d): M128d = mmSetrPd(simdExtract(b, 0), simdExtract(a, 1))

/**
 * Casts a 128-bit floating-point vector of `[2 x double]` into a 128-bit
 * floating-point vector of `[4 x float]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_castpd_ps)
 */
public fun mmCastpdPs(a: M128d): M128 = transmute(a)

/**
 * Casts a 128-bit floating-point vector of `[2 x double]` into a 128-bit
 * integer vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_castpd_si128)
 */
public fun mmCastpdSi128(a: M128d): M128i = transmute(a)

/**
 * Casts a 128-bit floating-point vector of `[4 x float]` into a 128-bit
 * floating-point vector of `[2 x double]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_castps_pd)
 */
public fun mmCastpsPd(a: M128): M128d = transmute(a)

/**
 * Casts a 128-bit floating-point vector of `[4 x float]` into a 128-bit
 * integer vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_castps_si128)
 */
public fun mmCastpsSi128(a: M128): M128i = transmute(a)

/**
 * Casts a 128-bit integer vector into a 128-bit floating-point vector
 * of `[2 x double]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_castsi128_pd)
 */
public fun mmCastsi128Pd(a: M128i): M128d = transmute(a)

/**
 * Casts a 128-bit integer vector into a 128-bit floating-point vector
 * of `[4 x float]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_castsi128_ps)
 */
public fun mmCastsi128Ps(a: M128i): M128 = transmute(a)

/**
 * Returns vector of type __m128d with indeterminate elements.with indetermination elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [mem.MaybeUninit].
 * In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_undefined_pd)
 */
public fun mmUndefinedPd(): M128d = null

/**
 * Returns vector of type __m128i with indeterminate elements.with indetermination elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [mem.MaybeUninit].
 * In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_undefined_si128)
 */
public fun mmUndefinedSi128(): M128i = null

/**
 * The resulting `__m128d` element is composed by the low-order values of
 * the two `__m128d` interleaved input elements, i.e.:
 *
 * * The `[127: 64]` bits are copied from the `[127: 64]` bits of the second input
 * * The `[63: 0]` bits are copied from the `[127: 64]` bits of the first input
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpackhi_pd)
 */
public fun mmUnpackhiPd(a: M128d, b: M128d): M128d = simdShuffle(a, b, SimdShuffleIdx(listOf(1, 3)))

/**
 * The resulting `__m128d` element is composed by the high-order values of
 * the two `__m128d` interleaved input elements, i.e.:
 *
 * * The `[127: 64]` bits are copied from the `[63: 0]` bits of the second input
 * * The `[63: 0]` bits are copied from the `[63: 0]` bits of the first input
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpacklo_pd)
 */
public fun mmUnpackloPd(a: M128d, b: M128d): M128d = simdShuffle(a, b, SimdShuffleIdx(listOf(0, 2)))

public expect fun pause()

public expect fun clflush(p: ConstPtr)

public expect fun lfence()

public expect fun mfence()

public expect fun pavgb(a: U8x16, b: U8x16): U8x16

public expect fun pavgw(a: U16x8, b: U16x8): U16x8

public expect fun pmaddwd(a: I16x8, b: I16x8): I32x4

public expect fun pmulhw(a: I16x8, b: I16x8): I16x8

public expect fun pmulhuw(a: U16x8, b: U16x8): U16x8

public expect fun psadbw(a: U8x16, b: U8x16): U64x2

public expect fun psllw(a: I16x8, count: I16x8): I16x8

public expect fun pslld(a: I32x4, count: I32x4): I32x4

public expect fun psllq(a: I64x2, count: I64x2): I64x2

public expect fun psraw(a: I16x8, count: I16x8): I16x8

public expect fun psrad(a: I32x4, count: I32x4): I32x4

public expect fun psrlw(a: I16x8, count: I16x8): I16x8

public expect fun psrld(a: I32x4, count: I32x4): I32x4

public expect fun psrlq(a: I64x2, count: I64x2): I64x2

public expect fun cvtps2dq(a: M128): I32x4

public expect fun maskmovdqu(a: I8x16, mask: I8x16, memAddr: MutPtr)

public expect fun packsswb(a: I16x8, b: I16x8): I8x16

public expect fun packssdw(a: I32x4, b: I32x4): I16x8

public expect fun packuswb(a: I16x8, b: I16x8): U8x16

public expect fun maxsd(a: M128d, b: M128d): M128d

public expect fun maxpd(a: M128d, b: M128d): M128d

public expect fun minsd(a: M128d, b: M128d): M128d

public expect fun minpd(a: M128d, b: M128d): M128d

public expect fun cmpsd(a: M128d, b: M128d, imm8: Byte): M128d

public expect fun cmppd(a: M128d, b: M128d, imm8: Byte): M128d

public expect fun comieqsd(a: M128d, b: M128d): Int

public expect fun comiltsd(a: M128d, b: M128d): Int

public expect fun comilesd(a: M128d, b: M128d): Int

public expect fun comigtsd(a: M128d, b: M128d): Int

public expect fun comigesd(a: M128d, b: M128d): Int

public expect fun comineqsd(a: M128d, b: M128d): Int

public expect fun ucomieqsd(a: M128d, b: M128d): Int

public expect fun ucomiltsd(a: M128d, b: M128d): Int

public expect fun ucomilesd(a: M128d, b: M128d): Int

public expect fun ucomigtsd(a: M128d, b: M128d): Int

public expect fun ucomigesd(a: M128d, b: M128d): Int

public expect fun ucomineqsd(a: M128d, b: M128d): Int

public expect fun cvtpd2dq(a: M128d): I32x4

public expect fun cvtsd2si(a: M128d): Int

public expect fun cvtsd2ss(a: M128, b: M128d): M128

public expect fun cvttpd2dq(a: M128d): I32x4

public expect fun cvttsd2si(a: M128d): Int

public expect fun cvttps2dq(a: M128): I32x4
