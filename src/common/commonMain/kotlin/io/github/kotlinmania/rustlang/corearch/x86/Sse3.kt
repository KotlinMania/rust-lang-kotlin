// port-lint: source x86/sse3.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simdShuffle

// Streaming SIMD Extensions 3 (SSE3)

/**
 * Alternatively add and subtract packed single-precision (32-bit)
 * floating-point elements in `a` to/from packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_addsub_ps)
 */
public fun mmAddsubPs(a: M128, b: M128): M128 =
    run {
        val a1 = a.asF32x4()
        val b1 = b.asF32x4()
        val add = simdAdd(a1, b1)
        val sub = simdSub(a1, b1)
        simdShuffle(add, sub, SimdShuffleIdx(listOf(4, 1, 6, 3)))
    }

/**
 * Alternatively add and subtract packed double-precision (64-bit)
 * floating-point elements in `a` to/from packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_addsub_pd)
 */
public fun mmAddsubPd(a: M128d, b: M128d): M128d =
    run {
        val a1 = a.asF64x2()
        val b1 = b.asF64x2()
        val add = simdAdd(a1, b1)
        val sub = simdSub(a1, b1)
        simdShuffle(add, sub, SimdShuffleIdx(listOf(2, 1)))
    }

/**
 * Horizontally adds adjacent pairs of double-precision (64-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadd_pd)
 */
public fun mmHaddPd(a: M128d, b: M128d): M128d =
    run {
        val even = simdShuffle(a, b, SimdShuffleIdx(listOf(0, 2)))
        val odd = simdShuffle(a, b, SimdShuffleIdx(listOf(1, 3)))
        simdAdd(even, odd)
    }

/**
 * Horizontally adds adjacent pairs of single-precision (32-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadd_ps)
 */
public fun mmHaddPs(a: M128, b: M128): M128 =
    run {
        val even = simdShuffle(a, b, SimdShuffleIdx(listOf(0, 2, 4, 6)))
        val odd = simdShuffle(a, b, SimdShuffleIdx(listOf(1, 3, 5, 7)))
        simdAdd(even, odd)
    }

/**
 * Horizontally subtract adjacent pairs of double-precision (64-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsub_pd)
 */
public fun mmHsubPd(a: M128d, b: M128d): M128d =
    run {
        val even = simdShuffle(a, b, SimdShuffleIdx(listOf(0, 2)))
        val odd = simdShuffle(a, b, SimdShuffleIdx(listOf(1, 3)))
        simdSub(even, odd)
    }

/**
 * Horizontally adds adjacent pairs of single-precision (32-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsub_ps)
 */
public fun mmHsubPs(a: M128, b: M128): M128 =
    run {
        val even = simdShuffle(a, b, SimdShuffleIdx(listOf(0, 2, 4, 6)))
        val odd = simdShuffle(a, b, SimdShuffleIdx(listOf(1, 3, 5, 7)))
        simdSub(even, odd)
    }

/**
 * Loads 128-bits of integer data from unaligned memory.
 * This intrinsic may perform better than `_mm_loadu_si128`
 * when the data crosses a cache line boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_lddqu_si128)
 */
public fun mmLddquSi128(memAddr: ConstPtr): M128i = transmute(lddqu(memAddr))

/**
 * Duplicate the low double-precision (64-bit) floating-point element
 * from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movedup_pd)
 */
public fun mmMovedupPd(a: M128d): M128d = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0)))

/**
 * Loads a double-precision (64-bit) floating-point element from memory
 * into both elements of return vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loaddup_pd)
 */
public fun mmLoaddupPd(memAddr: ConstPtr): M128d = mmLoad1Pd(memAddr)

/**
 * Duplicate odd-indexed single-precision (32-bit) floating-point elements
 * from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movehdup_ps)
 */
public fun mmMovehdupPs(a: M128): M128 = simdShuffle(a, a, SimdShuffleIdx(listOf(1, 1, 3, 3)))

/**
 * Duplicate even-indexed single-precision (32-bit) floating-point elements
 * from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_moveldup_ps)
 */
public fun mmMoveldupPs(a: M128): M128 = simdShuffle(a, a, SimdShuffleIdx(listOf(0, 0, 2, 2)))

public expect fun lddqu(memAddr: ConstPtr): I8x16
