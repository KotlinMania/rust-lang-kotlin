// port-lint: source x86/ssse3.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

// Supplemental Streaming SIMD Extensions 3 (SSSE3)

/**
 * Computes the absolute value of packed 8-bit signed integers in `a` and
 * return the unsigned results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_abs_epi8)
 */
public fun mmAbsEpi8(a: M128i): M128i =
    run {
        val a1 = a.asI8x16()
        val zero = Simd<Byte>(List(16) { 0.toByte() })
        val r = run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<M8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M8x16>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }
        transmuteWithLayouts<Any?, M128i>(r, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Computes the absolute value of each of the packed 16-bit signed integers in
 * `a` and
 * return the 16-bit unsigned integer
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_abs_epi16)
 */
public fun mmAbsEpi16(a: M128i): M128i =
    run {
        val a1 = a.asI16x8()
        val zero = Simd<Short>(List(8) { 0.toShort() })
        val r = run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<M16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M16x8>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }
        transmuteWithLayouts<Any?, M128i>(r, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Computes the absolute value of each of the packed 32-bit signed integers in
 * `a` and
 * return the 32-bit unsigned integer
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_abs_epi32)
 */
public fun mmAbsEpi32(a: M128i): M128i =
    run {
        val a1 = a.asI32x4()
        val zero = Simd<Int>(List(4) { 0 })
        val r = run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<M32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }
        transmuteWithLayouts<Any?, M128i>(r, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffles bytes from `a` according to the content of `b`.
 *
 * The last 4 bits of each byte of `b` are used as addresses
 * into the 16 bytes of `a`.
 *
 * In addition, if the highest significant bit of a byte of `b`
 * is set, the respective destination byte is set to 0.
 *
 * Picturing `a` and `b` as `[UByte; 16]`, `_mm_shuffle_epi8` is
 * logically equivalent to:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     fun mmShuffleEpi8(a: UByteArray, b: UByteArray): UByteArray {
 *         var r = UByteArray(16) { 0u.toUByte() }
 *         for (i in 0 until 16) {
 *             // if the most significant bit of b is set,
 *             // then the destination byte is set to 0.
 *             if (b[i] and 0x80u.toUByte() == 0u.toUByte()) {
 *                 r[i] = a[(b[i] % 16u).toInt()]
 *             }
 *         }
 *         return r
 *     }
 * }
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shuffle_epi8)
 */
public fun mmShuffleEpi8(a: M128i, b: M128i): M128i = transmuteWithLayouts<U8x16, M128i>(pshufb128(a.asU8x16(), b.asU8x16()), UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Concatenate 16-byte blocks in `a` and `b` into a 32-byte temporary result,
 * shift the result right by `n` bytes, and returns the low 16 bytes.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_alignr_epi8)
 */
public fun mmAlignrEpi8(a: M128i, b: M128i, imm8: Int): M128i {
    fun mask(shift: UInt, i: UInt): UInt =
        if (shift > 32u) {
            // Unused, but needs to be a valid index.
            i
        } else if (shift > 16u) {
            shift - 16u + i
        } else {
            shift + i
        }
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    // If palignr is shifting the pair of vectors more than the size of two
    // lanes, emit zero.
    if (imm8 > 32) {
        return mmSetzeroSi128()
    }
    // If palignr is shifting the pair of input vectors more than one lane,
    // but less than two lanes, convert to shifting in zeroes.
    val (a1, b1) =
        if (imm8 > 16) {
            Pair(mmSetzeroSi128(), a)
        } else {
            Pair(a, b)
        }
    return run {
        val r: I8x16 = run { val simdArg0 = b1.asI8x16(); val simdArg1 = a1.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(mask(imm8.toUInt(), 0u), mask(imm8.toUInt(), 1u), mask(imm8.toUInt(), 2u), mask(imm8.toUInt(), 3u), mask(imm8.toUInt(), 4u), mask(imm8.toUInt(), 5u), mask(imm8.toUInt(), 6u), mask(imm8.toUInt(), 7u), mask(imm8.toUInt(), 8u), mask(imm8.toUInt(), 9u), mask(imm8.toUInt(), 10u), mask(imm8.toUInt(), 11u), mask(imm8.toUInt(), 12u), mask(imm8.toUInt(), 13u), mask(imm8.toUInt(), 14u), mask(imm8.toUInt(), 15u))); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }
        transmuteWithLayouts<I8x16, M128i>(r, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())
    }
}

/**
 * Horizontally adds the adjacent pairs of values contained in 2 packed
 * 128-bit vectors of `[8 x Short]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadd_epi16)
 */
public fun mmHaddEpi16(a: M128i, b: M128i): M128i {
    val a1 = a.asI16x8()
    val b1 = b.asI16x8()
    return run {
        val even: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        val odd: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }.asM128i()
    }
}

/**
 * Horizontally adds the adjacent pairs of values contained in 2 packed
 * 128-bit vectors of `[8 x Short]`. Positive sums greater than 7FFFh are
 * saturated to 7FFFh. Negative sums less than 8000h are saturated to 8000h.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadds_epi16)
 */
public fun mmHaddsEpi16(a: M128i, b: M128i): M128i {
    val a1 = a.asI16x8()
    val b1 = b.asI16x8()
    return run {
        val even: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        val odd: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }.asM128i()
    }
}

/**
 * Horizontally adds the adjacent pairs of values contained in 2 packed
 * 128-bit vectors of `[4 x Int]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadd_epi32)
 */
public fun mmHaddEpi32(a: M128i, b: M128i): M128i {
    val a1 = a.asI32x4()
    val b1 = b.asI32x4()
    return run {
        val even: I32x4 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        val odd: I32x4 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }.asM128i()
    }
}

/**
 * Horizontally subtract the adjacent pairs of values contained in 2
 * packed 128-bit vectors of `[8 x Short]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsub_epi16)
 */
public fun mmHsubEpi16(a: M128i, b: M128i): M128i {
    val a1 = a.asI16x8()
    val b1 = b.asI16x8()
    return run {
        val even: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        val odd: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }.asM128i()
    }
}

/**
 * Horizontally subtract the adjacent pairs of values contained in 2
 * packed 128-bit vectors of `[8 x Short]`. Positive differences greater than
 * 7FFFh are saturated to 7FFFh. Negative differences less than 8000h are
 * saturated to 8000h.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsubs_epi16)
 */
public fun mmHsubsEpi16(a: M128i, b: M128i): M128i {
    val a1 = a.asI16x8()
    val b1 = b.asI16x8()
    return run {
        val even: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        val odd: I16x8 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }.asM128i()
    }
}

/**
 * Horizontally subtract the adjacent pairs of values contained in 2
 * packed 128-bit vectors of `[4 x Int]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsub_epi32)
 */
public fun mmHsubEpi32(a: M128i, b: M128i): M128i {
    val a1 = a.asI32x4()
    val b1 = b.asI32x4()
    return run {
        val even: I32x4 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        val odd: I32x4 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }.asM128i()
    }
}

/**
 * Multiplies corresponding pairs of packed 8-bit unsigned integer
 * values contained in the first source operand and packed 8-bit signed
 * integer values contained in the second source operand, add pairs of
 * contiguous products with signed saturation, and writes the 16-bit sums to
 * the corresponding bits in the destination.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maddubs_epi16)
 */
public fun mmMaddubsEpi16(a: M128i, b: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(pmaddubsw128(a.asU8x16(), b.asI8x16()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Multiplies packed 16-bit signed integer values, truncate the 32-bit
 * product to the 18 most significant bits by right-shifting, round the
 * truncated value by adding 1, and write bits `[16: 1]` to the destination.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mulhrs_epi16)
 */
public fun mmMulhrsEpi16(a: M128i, b: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(pmulhrsw128(a.asI16x8(), b.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Negates packed 8-bit integers in `a` when the corresponding signed 8-bit
 * integer in `b` is negative, and returns the result.
 * Elements in result are zeroed out when the corresponding element in `b`
 * is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sign_epi8)
 */
public fun mmSignEpi8(a: M128i, b: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(psignb128(a.asI8x16(), b.asI8x16()), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Negates packed 16-bit integers in `a` when the corresponding signed 16-bit
 * integer in `b` is negative, and returns the results.
 * Elements in result are zeroed out when the corresponding element in `b`
 * is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sign_epi16)
 */
public fun mmSignEpi16(a: M128i, b: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(psignw128(a.asI16x8(), b.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Negates packed 32-bit integers in `a` when the corresponding signed 32-bit
 * integer in `b` is negative, and returns the results.
 * Element in result are zeroed out when the corresponding element in `b`
 * is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sign_epi32)
 */
public fun mmSignEpi32(a: M128i, b: M128i): M128i = transmuteWithLayouts<I32x4, M128i>(psignd128(a.asI32x4(), b.asI32x4()), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<M128i>())

public expect fun pshufb128(a: U8x16, b: U8x16): U8x16

public expect fun pmaddubsw128(a: U8x16, b: I8x16): I16x8

public expect fun pmulhrsw128(a: I16x8, b: I16x8): I16x8

public expect fun psignb128(a: I8x16, b: I8x16): I8x16

public expect fun psignw128(a: I16x8, b: I16x8): I16x8

public expect fun psignd128(a: I32x4, b: I32x4): I32x4
