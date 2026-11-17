// port-lint: source x86/sse3.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I8x16

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
        val add = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        val sub = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(4, 1, 6, 3)); _simdArg0 }
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
        val add = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        val sub = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(2, 1)); _simdArg0 }
    }

/**
 * Horizontally adds adjacent pairs of double-precision (64-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadd_pd)
 */
public fun mmHaddPd(a: M128d, b: M128d): M128d =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Horizontally adds adjacent pairs of single-precision (32-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hadd_ps)
 */
public fun mmHaddPs(a: M128, b: M128): M128 =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Horizontally subtract adjacent pairs of double-precision (64-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsub_pd)
 */
public fun mmHsubPd(a: M128d, b: M128d): M128d =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Horizontally adds adjacent pairs of single-precision (32-bit)
 * floating-point elements in `a` and `b`, and pack the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_hsub_ps)
 */
public fun mmHsubPs(a: M128, b: M128): M128 =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
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
public fun mmMovedupPd(a: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 0)); _simdArg0 }

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
public fun mmMovehdupPs(a: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 1, 3, 3)); _simdArg0 }

/**
 * Duplicate even-indexed single-precision (32-bit) floating-point elements
 * from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_moveldup_ps)
 */
public fun mmMoveldupPs(a: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 0, 2, 2)); _simdArg0 }

public expect fun lddqu(memAddr: ConstPtr): I8x16
