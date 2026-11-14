// port-lint: source x86/fma.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx

// Fused Multiply-Add instruction set (FMA)
//
// The FMA instruction set is an extension to the 128 and 256-bit SSE
// instructions in the x86 microprocessor instruction set to perform fused
// multiply–add (FMA) operations.
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref].
// - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
//   System Instructions][amd64Ref].
//
// Wikipedia's [FMA][wikiFma] page provides a quick overview of the
// instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wikiFma]: https://en.wikipedia.org/wiki/FusedMultiply-accumulate

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and add the intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmadd_pd)
 */
public fun mmFmaddPd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and add the intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmadd_pd)
 */
public fun mm256FmaddPd(a: M256d, b: M256d, c: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and add the intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmadd_ps)
 */
public fun mmFmaddPs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and add the intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmadd_ps)
 */
public fun mm256FmaddPs(a: M256, b: M256, c: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies the lower double-precision (64-bit) floating-point elements in
 * `a` and `b`, and add the intermediate result to the lower element in `c`.
 * Stores the result in the lower element of the returned value, and copy the
 * upper element from `a` to the upper elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmadd_sd)
 */
public fun mmFmaddSd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtsdF64(a), mmCvtsdF64(b), mmCvtsdF64(c)); _simdArg0 }

/**
 * Multiplies the lower single-precision (32-bit) floating-point elements in
 * `a` and `b`, and add the intermediate result to the lower element in `c`.
 * Stores the result in the lower element of the returned value, and copy the
 * 3 upper elements from `a` to the upper elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmadd_ss)
 */
public fun mmFmaddSs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtssF32(a), mmCvtssF32(b), mmCvtssF32(c)); _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and alternatively add and subtract packed elements in `c` to/from
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmaddsub_pd)
 */
public fun mmFmaddsubPd(a: M128d, b: M128d, c: M128d): M128d =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(2, 1)); _simdArg0 }
    }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and alternatively add and subtract packed elements in `c` to/from
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmaddsub_pd)
 */
public fun mm256FmaddsubPd(a: M256d, b: M256d, c: M256d): M256d =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(4, 1, 6, 3)); _simdArg0 }
    }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and alternatively add and subtract packed elements in `c` to/from
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmaddsub_ps)
 */
public fun mmFmaddsubPs(a: M128, b: M128, c: M128): M128 =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(4, 1, 6, 3)); _simdArg0 }
    }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and alternatively add and subtract packed elements in `c` to/from
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmaddsub_ps)
 */
public fun mm256FmaddsubPs(a: M256, b: M256, c: M256): M256 =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(8, 1, 10, 3, 12, 5, 14, 7)); _simdArg0 }
    }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmsub_pd)
 */
public fun mmFmsubPd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmsub_pd)
 */
public fun mm256FmsubPd(a: M256d, b: M256d, c: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmsub_ps)
 */
public fun mmFmsubPs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmsub_ps)
 */
public fun mm256FmsubPs(a: M256, b: M256, c: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies the lower double-precision (64-bit) floating-point elements in
 * `a` and `b`, and subtract the lower element in `c` from the intermediate
 * result. Store the result in the lower element of the returned value, and
 * copy the upper element from `a` to the upper elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmsub_sd)
 */
public fun mmFmsubSd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtsdF64(a), mmCvtsdF64(b), -mmCvtsdF64(c)); _simdArg0 }

/**
 * Multiplies the lower single-precision (32-bit) floating-point elements in
 * `a` and `b`,  and subtract the lower element in `c` from the intermediate
 * result. Store the result in the lower element of the returned value, and
 * copy the 3 upper elements from `a` to the upper elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmsub_ss)
 */
public fun mmFmsubSs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtssF32(a), mmCvtssF32(b), -mmCvtssF32(c)); _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and alternatively subtract and add packed elements in `c` from/to
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmsubadd_pd)
 */
public fun mmFmsubaddPd(a: M128d, b: M128d, c: M128d): M128d =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(0, 3)); _simdArg0 }
    }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and alternatively subtract and add packed elements in `c` from/to
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmsubadd_pd)
 */
public fun mm256FmsubaddPd(a: M256d, b: M256d, c: M256d): M256d =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(0, 5, 2, 7)); _simdArg0 }
    }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and alternatively subtract and add packed elements in `c` from/to
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fmsubadd_ps)
 */
public fun mmFmsubaddPs(a: M128, b: M128, c: M128): M128 =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(0, 5, 2, 7)); _simdArg0 }
    }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and alternatively subtract and add packed elements in `c` from/to
 * the intermediate result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fmsubadd_ps)
 */
public fun mm256FmsubaddPs(a: M256, b: M256, c: M256): M256 =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(0, 9, 2, 11, 4, 13, 6, 15)); _simdArg0 }
    }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and add the negated intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmadd_pd)
 */
public fun mmFnmaddPd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and add the negated intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fnmadd_pd)
 */
public fun mm256FnmaddPd(a: M256d, b: M256d, c: M256d): M256d = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and add the negated intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmadd_ps)
 */
public fun mmFnmaddPs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and add the negated intermediate result to packed elements in `c`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fnmadd_ps)
 */
public fun mm256FnmaddPs(a: M256, b: M256, c: M256): M256 = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiplies the lower double-precision (64-bit) floating-point elements in
 * `a` and `b`, and add the negated intermediate result to the lower element
 * in `c`. Store the result in the lower element of the returned value, and
 * copy the upper element from `a` to the upper elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmadd_sd)
 */
public fun mmFnmaddSd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtsdF64(a), -mmCvtsdF64(b), mmCvtsdF64(c)); _simdArg0 }

/**
 * Multiplies the lower single-precision (32-bit) floating-point elements in
 * `a` and `b`, and add the negated intermediate result to the lower element
 * in `c`. Store the result in the lower element of the returned value, and
 * copy the 3 upper elements from `a` to the upper elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmadd_ss)
 */
public fun mmFnmaddSs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtssF32(a), -mmCvtssF32(b), mmCvtssF32(c)); _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the negated intermediate
 * result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmsub_pd)
 */
public fun mmFnmsubPd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies packed double-precision (64-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the negated intermediate
 * result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fnmsub_pd)
 */
public fun mm256FnmsubPd(a: M256d, b: M256d, c: M256d): M256d = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the negated intermediate
 * result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmsub_ps)
 */
public fun mmFnmsubPs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and subtract packed elements in `c` from the negated intermediate
 * result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fnmsub_ps)
 */
public fun mm256FnmsubPs(a: M256, b: M256, c: M256): M256 = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiplies the lower double-precision (64-bit) floating-point elements in
 * `a` and `b`, and subtract packed elements in `c` from the negated
 * intermediate result. Store the result in the lower element of the returned
 * value, and copy the upper element from `a` to the upper elements of the
 * result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmsub_sd)
 */
public fun mmFnmsubSd(a: M128d, b: M128d, c: M128d): M128d = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtsdF64(a), -mmCvtsdF64(b), -mmCvtsdF64(c)); _simdArg0 }

/**
 * Multiplies the lower single-precision (32-bit) floating-point elements in
 * `a` and `b`, and subtract packed elements in `c` from the negated
 * intermediate result. Store the result in the lower element of the
 * returned value, and copy the 3 upper elements from `a` to the upper
 * elements of the result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fnmsub_ss)
 */
public fun mmFnmsubSs(a: M128, b: M128, c: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fma(mmCvtssF32(a), -mmCvtssF32(b), -mmCvtssF32(c)); _simdArg0 }
