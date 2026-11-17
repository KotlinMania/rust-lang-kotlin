// port-lint: source x86/avx512dq.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.F32x16
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.F64x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8

// And //

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_and_pd&igExpand=288)
 */
public fun mmMaskAndPd(src: M128d, k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val and = mmAndPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_and_pd&igExpand=289)
 */
public fun mmMaskzAndPd(k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val and = mmAndPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_and_pd&igExpand=291)
 */
public fun mm256MaskAndPd(src: M256d, k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val and = mm256AndPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_and_pd&igExpand=292)
 */
public fun mm256MaskzAndPd(k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val and = mm256AndPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_and_pd&igExpand=293)
 */
public fun mm512AndPd(a: M512d, b: M512d): M512d = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = transmute(b); _simdArg0 })

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_and_pd&igExpand=294)
 */
public fun mm512MaskAndPd(src: M512d, k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val and = mm512AndPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_and_pd&igExpand=295)
 */
public fun mm512MaskzAndPd(k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val and = mm512AndPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_and_ps&igExpand=297)
 */
public fun mmMaskAndPs(src: M128, k: Mmask8, a: M128, b: M128): M128 =
    run {
        val and = mmAndPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_and_ps&igExpand=298)
 */
public fun mmMaskzAndPs(k: Mmask8, a: M128, b: M128): M128 =
    run {
        val and = mmAndPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_and_ps&igExpand=300)
 */
public fun mm256MaskAndPs(src: M256, k: Mmask8, a: M256, b: M256): M256 =
    run {
        val and = mm256AndPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_and_ps&igExpand=301)
 */
public fun mm256MaskzAndPs(k: Mmask8, a: M256, b: M256): M256 =
    run {
        val and = mm256AndPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_and_ps&igExpand=303)
 */
public fun mm512AndPs(a: M512, b: M512): M512 = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = transmute(b); _simdArg0 })

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_and_ps&igExpand=304)
 */
public fun mm512MaskAndPs(src: M512, k: Mmask16, a: M512, b: M512): M512 =
    run {
        val and = mm512AndPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Compute the bitwise AND of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_and_ps&igExpand=305)
 */
public fun mm512MaskzAndPs(k: Mmask16, a: M512, b: M512): M512 =
    run {
        val and = mm512AndPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = and; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

// Andnot

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_andnot_pd&igExpand=326)
 */
public fun mmMaskAndnotPd(src: M128d, k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val andnot = mmAndnotPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_andnot_pd&igExpand=327)
 */
public fun mmMaskzAndnotPd(k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val andnot = mmAndnotPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_andnot_pd&igExpand=329)
 */
public fun mm256MaskAndnotPd(src: M256d, k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val andnot = mm256AndnotPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_andnot_pd&igExpand=330)
 */
public fun mm256MaskzAndnotPd(k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val andnot = mm256AndnotPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_andnot_pd&igExpand=331)
 */
public fun mm512AndnotPd(a: M512d, b: M512d): M512d = mm512AndPd(mm512XorPd(a, transmute(mm512Set1Epi64(-1L))), b)

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_andnot_pd&igExpand=332)
 */
public fun mm512MaskAndnotPd(src: M512d, k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val andnot = mm512AndnotPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed double-precision (64-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_andnot_pd&igExpand=333)
 */
public fun mm512MaskzAndnotPd(k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val andnot = mm512AndnotPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_andnot_ps&igExpand=335)
 */
public fun mmMaskAndnotPs(src: M128, k: Mmask8, a: M128, b: M128): M128 =
    run {
        val andnot = mmAndnotPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_andnot_ps&igExpand=336)
 */
public fun mmMaskzAndnotPs(k: Mmask8, a: M128, b: M128): M128 =
    run {
        val andnot = mmAndnotPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_andnot_ps&igExpand=338)
 */
public fun mm256MaskAndnotPs(src: M256, k: Mmask8, a: M256, b: M256): M256 =
    run {
        val andnot = mm256AndnotPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_andnot_ps&igExpand=339)
 */
public fun mm256MaskzAndnotPs(k: Mmask8, a: M256, b: M256): M256 =
    run {
        val andnot = mm256AndnotPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_andnot_ps&igExpand=340)
 */
public fun mm512AndnotPs(a: M512, b: M512): M512 = mm512AndPs(mm512XorPs(a, transmute(mm512Set1Epi32(-1))), b)

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_andnot_ps&igExpand=341)
 */
public fun mm512MaskAndnotPs(src: M512, k: Mmask16, a: M512, b: M512): M512 =
    run {
        val andnot = mm512AndnotPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Compute the bitwise NOT of packed single-precision (32-bit) floating point numbers in a and then
 * bitwise AND with b and store the results in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_andnot_ps&igExpand=342)
 */
public fun mm512MaskzAndnotPs(k: Mmask16, a: M512, b: M512): M512 =
    run {
        val andnot = mm512AndnotPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = andnot; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

// Or

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_or_pd&igExpand=4824)
 */
public fun mmMaskOrPd(src: M128d, k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val or = mmOrPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_or_pd&igExpand=4825)
 */
public fun mmMaskzOrPd(k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val or = mmOrPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_or_pd&igExpand=4827)
 */
public fun mm256MaskOrPd(src: M256d, k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val or = mm256OrPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_or_pd&igExpand=4828)
 */
public fun mm256MaskzOrPd(k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val or = mm256OrPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_or_pd&igExpand=4829)
 */
public fun mm512OrPd(a: M512d, b: M512d): M512d = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = transmute(b); _simdArg0 })

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_or_pd&igExpand=4830)
 */
public fun mm512MaskOrPd(src: M512d, k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val or = mm512OrPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_or_pd&igExpand=4831)
 */
public fun mm512MaskzOrPd(k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val or = mm512OrPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_or_ps&igExpand=4833)
 */
public fun mmMaskOrPs(src: M128, k: Mmask8, a: M128, b: M128): M128 =
    run {
        val or = mmOrPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_or_ps&igExpand=4834)
 */
public fun mmMaskzOrPs(k: Mmask8, a: M128, b: M128): M128 =
    run {
        val or = mmOrPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_or_ps&igExpand=4836)
 */
public fun mm256MaskOrPs(src: M256, k: Mmask8, a: M256, b: M256): M256 =
    run {
        val or = mm256OrPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_or_ps&igExpand=4837)
 */
public fun mm256MaskzOrPs(k: Mmask8, a: M256, b: M256): M256 =
    run {
        val or = mm256OrPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_or_ps&igExpand=4838)
 */
public fun mm512OrPs(a: M512, b: M512): M512 = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = transmute(b); _simdArg0 })

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_or_ps&igExpand=4839)
 */
public fun mm512MaskOrPs(src: M512, k: Mmask16, a: M512, b: M512): M512 =
    run {
        val or = mm512OrPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Compute the bitwise OR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_or_ps&igExpand=4840)
 */
public fun mm512MaskzOrPs(k: Mmask16, a: M512, b: M512): M512 =
    run {
        val or = mm512OrPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = or; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

// Xor

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_xor_pd&igExpand=7094)
 */
public fun mmMaskXorPd(src: M128d, k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val xor = mmXorPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_xor_pd&igExpand=7095)
 */
public fun mmMaskzXorPd(k: Mmask8, a: M128d, b: M128d): M128d =
    run {
        val xor = mmXorPd(a, b).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_xor_pd&igExpand=7097)
 */
public fun mm256MaskXorPd(src: M256d, k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val xor = mm256XorPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_xor_pd&igExpand=7098)
 */
public fun mm256MaskzXorPd(k: Mmask8, a: M256d, b: M256d): M256d =
    run {
        val xor = mm256XorPd(a, b).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_xor_pd&igExpand=7102)
 */
public fun mm512XorPd(a: M512d, b: M512d): M512d = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = transmute(b); _simdArg0 })

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_xor_pd&igExpand=7100)
 */
public fun mm512MaskXorPd(src: M512d, k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val xor = mm512XorPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed double-precision (64-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_xor_pd&igExpand=7101)
 */
public fun mm512MaskzXorPd(k: Mmask8, a: M512d, b: M512d): M512d =
    run {
        val xor = mm512XorPd(a, b).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_xor_ps&igExpand=7103)
 */
public fun mmMaskXorPs(src: M128, k: Mmask8, a: M128, b: M128): M128 =
    run {
        val xor = mmXorPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_xor_ps&igExpand=7104)
 */
public fun mmMaskzXorPs(k: Mmask8, a: M128, b: M128): M128 =
    run {
        val xor = mmXorPs(a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_xor_ps&igExpand=7106)
 */
public fun mm256MaskXorPs(src: M256, k: Mmask8, a: M256, b: M256): M256 =
    run {
        val xor = mm256XorPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_xor_ps&igExpand=7107)
 */
public fun mm256MaskzXorPs(k: Mmask8, a: M256, b: M256): M256 =
    run {
        val xor = mm256XorPs(a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_xor_ps&igExpand=7111)
 */
public fun mm512XorPs(a: M512, b: M512): M512 = transmute(run { val _simdArg0 = transmute(a); val _simdArg1 = transmute(b); _simdArg0 })

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_xor_ps&igExpand=7109)
 */
public fun mm512MaskXorPs(src: M512, k: Mmask16, a: M512, b: M512): M512 =
    run {
        val xor = mm512XorPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Compute the bitwise XOR of packed single-precision (32-bit) floating point numbers in a and b and
 * store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_xor_ps&igExpand=7110)
 */
public fun mm512MaskzXorPs(k: Mmask16, a: M512, b: M512): M512 =
    run {
        val xor = mm512XorPs(a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = xor; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

// Broadcast

/**
 * Broadcasts the lower 2 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_f32x2&igExpand=509)
 */
public fun mm256BroadcastF32x2(a: M128): M256 =
    run {
        val b: F32x8 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1, 0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the lower 2 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_broadcast_f32x2&igExpand=510)
 */
public fun mm256MaskBroadcastF32x2(src: M256, k: Mmask8, a: M128): M256 =
    run {
        val b = mm256BroadcastF32x2(a).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_broadcast_f32x2&igExpand=511)
 */
public fun mm256MaskzBroadcastF32x2(k: Mmask8, a: M128): M256 =
    run {
        val b = mm256BroadcastF32x2(a).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcast_f32x2&igExpand=512)
 */
public fun mm512BroadcastF32x2(a: M128): M512 =
    run {
        val b: F32x16 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the lower 2 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcast_f32x2&igExpand=513)
 */
public fun mm512MaskBroadcastF32x2(src: M512, k: Mmask16, a: M128): M512 =
    run {
        val b = mm512BroadcastF32x2(a).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcast_f32x2&igExpand=514)
 */
public fun mm512MaskzBroadcastF32x2(k: Mmask16, a: M128): M512 =
    run {
        val b = mm512BroadcastF32x2(a).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the 8 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcast_f32x8&igExpand=521)
 */
public fun mm512BroadcastF32x8(a: M256): M512 =
    run {
        val b: F32x16 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the 8 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcast_f32x8&igExpand=522)
 */
public fun mm512MaskBroadcastF32x8(src: M512, k: Mmask16, a: M256): M512 =
    run {
        val b = mm512BroadcastF32x8(a).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Broadcasts the 8 packed single-precision (32-bit) floating-point elements from a to all
 * elements of dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcast_f32x8&igExpand=523)
 */
public fun mm512MaskzBroadcastF32x8(k: Mmask16, a: M256): M512 =
    run {
        val b = mm512BroadcastF32x8(a).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the 2 packed double-precision (64-bit) floating-point elements from a to all
 * elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_f64x2&igExpand=524)
 */
public fun mm256BroadcastF64x2(a: M128d): M256d =
    run {
        val b: F64x4 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the 2 packed double-precision (64-bit) floating-point elements from a to all
 * elements of dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_broadcast_f64x2&igExpand=525)
 */
public fun mm256MaskBroadcastF64x2(src: M256d, k: Mmask8, a: M128d): M256d =
    run {
        val b = mm256BroadcastF64x2(a).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Broadcasts the 2 packed double-precision (64-bit) floating-point elements from a to all
 * elements of dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_broadcast_f64x2&igExpand=526)
 */
public fun mm256MaskzBroadcastF64x2(k: Mmask8, a: M128d): M256d =
    run {
        val b = mm256BroadcastF64x2(a).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the 2 packed double-precision (64-bit) floating-point elements from a to all
 * elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcast_f64x2&igExpand=527)
 */
public fun mm512BroadcastF64x2(a: M128d): M512d =
    run {
        val b: F64x8 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1, 0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the 2 packed double-precision (64-bit) floating-point elements from a to all
 * elements of dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcast_f64x2&igExpand=528)
 */
public fun mm512MaskBroadcastF64x2(src: M512d, k: Mmask8, a: M128d): M512d =
    run {
        val b = mm512BroadcastF64x2(a).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Broadcasts the 2 packed double-precision (64-bit) floating-point elements from a to all
 * elements of dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcast_f64x2&igExpand=529)
 */
public fun mm512MaskzBroadcastF64x2(k: Mmask8, a: M128d): M512d =
    run {
        val b = mm512BroadcastF64x2(a).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcast_i32x2&igExpand=533)
 */
public fun mmBroadcastI32x2(a: M128i): M128i =
    run {
        val a1 = a.asI32x4()
        val b: I32x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_broadcast_i32x2&igExpand=534)
 */
public fun mmMaskBroadcastI32x2(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val b = mmBroadcastI32x2(a).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_broadcast_i32x2&igExpand=535)
 */
public fun mmMaskzBroadcastI32x2(k: Mmask8, a: M128i): M128i =
    run {
        val b = mmBroadcastI32x2(a).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_i32x2&igExpand=536)
 */
public fun mm256BroadcastI32x2(a: M128i): M256i =
    run {
        val a1 = a.asI32x4()
        val b: I32x8 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1, 0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_broadcast_i32x2&igExpand=537)
 */
public fun mm256MaskBroadcastI32x2(src: M256i, k: Mmask8, a: M128i): M256i =
    run {
        val b = mm256BroadcastI32x2(a).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_broadcast_i32x2&igExpand=538)
 */
public fun mm256MaskzBroadcastI32x2(k: Mmask8, a: M128i): M256i =
    run {
        val b = mm256BroadcastI32x2(a).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcast_i32x2&igExpand=539)
 */
public fun mm512BroadcastI32x2(a: M128i): M512i =
    run {
        val a1 = a.asI32x4()
        val b: I32x16 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcast_i32x2&igExpand=540)
 */
public fun mm512MaskBroadcastI32x2(src: M512i, k: Mmask16, a: M128i): M512i =
    run {
        val b = mm512BroadcastI32x2(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Broadcasts the lower 2 packed 32-bit integers from a to all elements of dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcast_i32x2&igExpand=541)
 */
public fun mm512MaskzBroadcastI32x2(k: Mmask16, a: M128i): M512i =
    run {
        val b = mm512BroadcastI32x2(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the 8 packed 32-bit integers from a to all elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcast_i32x8&igExpand=548)
 */
public fun mm512BroadcastI32x8(a: M256i): M512i =
    run {
        val a1 = a.asI32x8()
        val b: I32x16 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the 8 packed 32-bit integers from a to all elements of dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcast_i32x8&igExpand=549)
 */
public fun mm512MaskBroadcastI32x8(src: M512i, k: Mmask16, a: M256i): M512i =
    run {
        val b = mm512BroadcastI32x8(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Broadcasts the 8 packed 32-bit integers from a to all elements of dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcast_i32x8&igExpand=550)
 */
public fun mm512MaskzBroadcastI32x8(k: Mmask16, a: M256i): M512i =
    run {
        val b = mm512BroadcastI32x8(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the 2 packed 64-bit integers from a to all elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_i64x2&igExpand=551)
 */
public fun mm256BroadcastI64x2(a: M128i): M256i =
    run {
        val a1 = a.asI64x2()
        val b: I64x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the 2 packed 64-bit integers from a to all elements of dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_broadcast_i64x2&igExpand=552)
 */
public fun mm256MaskBroadcastI64x2(src: M256i, k: Mmask8, a: M128i): M256i =
    run {
        val b = mm256BroadcastI64x2(a).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

/**
 * Broadcasts the 2 packed 64-bit integers from a to all elements of dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_broadcast_i64x2&igExpand=553)
 */
public fun mm256MaskzBroadcastI64x2(k: Mmask8, a: M128i): M256i =
    run {
        val b = mm256BroadcastI64x2(a).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Broadcasts the 2 packed 64-bit integers from a to all elements of dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcast_i64x2&igExpand=554)
 */
public fun mm512BroadcastI64x2(a: M128i): M512i =
    run {
        val a1 = a.asI64x2()
        val b: I64x8 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1, 0, 1, 0, 1)); _simdArg0 }
        transmute(b)
    }

/**
 * Broadcasts the 2 packed 64-bit integers from a to all elements of dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcast_i64x2&igExpand=555)
 */
public fun mm512MaskBroadcastI64x2(src: M512i, k: Mmask8, a: M128i): M512i =
    run {
        val b = mm512BroadcastI64x2(a).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

/**
 * Broadcasts the 2 packed 64-bit integers from a to all elements of dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcast_i64x2&igExpand=556)
 */
public fun mm512MaskzBroadcastI64x2(k: Mmask8, a: M128i): M512i =
    run {
        val b = mm512BroadcastI64x2(a).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

// Extract

/**
 * Extracts 256 bits (composed of 8 packed single-precision (32-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_extractf32x8_ps&igExpand=2946)
 */
public fun mm512Extractf32x8Ps(a: M512, imm8: Int): M256 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        when (imm8 and 1) {
            0 -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }
            }
        }
    }

/**
 * Extracts 256 bits (composed of 8 packed single-precision (32-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst using writemask k (elements are copied from src
 * if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_extractf32x8_ps&igExpand=2947)
 */
public fun mm512MaskExtractf32x8Ps(src: M256, k: Mmask8, a: M512, imm8: Int): M256 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm512Extractf32x8Ps(a, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = b.asF32x8(); val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Extracts 256 bits (composed of 8 packed single-precision (32-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_extractf32x8_ps&igExpand=2948)
 */
public fun mm512MaskzExtractf32x8Ps(k: Mmask8, a: M512, imm8: Int): M256 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm512Extractf32x8Ps(a, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = b.asF32x8(); val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extractf64x2_pd&igExpand=2949)
 */
public fun mm256Extractf64x2Pd(a: M256d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        when (imm8 and 1) {
            0 -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }
            }
        }
    }

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst using writemask k (elements are copied from src
 * if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_extractf64x2_pd&igExpand=2950)
 */
public fun mm256MaskExtractf64x2Pd(src: M128d, k: Mmask8, a: M256d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm256Extractf64x2Pd(a, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = b.asF64x2(); val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_extractf64x2_pd&igExpand=2951)
 */
public fun mm256MaskzExtractf64x2Pd(k: Mmask8, a: M256d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm256Extractf64x2Pd(a, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = b.asF64x2(); val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_extractf64x2_pd&igExpand=2952)
 */
public fun mm512Extractf64x2Pd(a: M512d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        when (imm8 and 3) {
            0 -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
            }
            1 -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }
            }
            2 -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(4, 5)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(6, 7)); _simdArg0 }
            }
        }
    }

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst using writemask k (elements are copied from src
 * if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_extractf64x2_pd&igExpand=2953)
 */
public fun mm512MaskExtractf64x2Pd(src: M128d, k: Mmask8, a: M512d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val b = mm512Extractf64x2Pd(a, imm8).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit) floating-point elements) from a,
 * selected with IMM8, and stores the result in dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_extractf64x2_pd&igExpand=2954)
 */
public fun mm512MaskzExtractf64x2Pd(k: Mmask8, a: M512d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val b = mm512Extractf64x2Pd(a, imm8).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Extracts 256 bits (composed of 8 packed 32-bit integers) from a, selected with IMM8, and stores
 * the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_extracti32x8_epi32&igExpand=2965)
 */
public fun mm512Extracti32x8Epi32(a: M512i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val a1 = a.asI32x16()
        val b: I32x8 =
            when (imm8 and 1) {
                0 -> {
                    run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }
                }
                else -> {
                    run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }
                }
            }
        transmute(b)
    }

/**
 * Extracts 256 bits (composed of 8 packed 32-bit integers) from a, selected with IMM8, and stores
 * the result in dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_extracti32x8_epi32&igExpand=2966)
 */
public fun mm512MaskExtracti32x8Epi32(src: M256i, k: Mmask8, a: M512i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm512Extracti32x8Epi32(a, imm8).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Extracts 256 bits (composed of 8 packed 32-bit integers) from a, selected with IMM8, and stores
 * the result in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_extracti32x8_epi32&igExpand=2967)
 */
public fun mm512MaskzExtracti32x8Epi32(k: Mmask8, a: M512i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm512Extracti32x8Epi32(a, imm8).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed 64-bit integers) from a, selected with IMM8, and stores
 * the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extracti64x2_epi64&igExpand=2968)
 */
public fun mm256Extracti64x2Epi64(a: M256i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val a1 = a.asI64x4()
        when (imm8 and 1) {
            0 -> {
                run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }
            }
        }
    }

/**
 * Extracts 128 bits (composed of 2 packed 64-bit integers) from a, selected with IMM8, and stores
 * the result in dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_extracti64x2_epi64&igExpand=2969)
 */
public fun mm256MaskExtracti64x2Epi64(src: M128i, k: Mmask8, a: M256i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm256Extracti64x2Epi64(a, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed 64-bit integers) from a, selected with IMM8, and stores
 * the result in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_extracti64x2_epi64&igExpand=2970)
 */
public fun mm256MaskzExtracti64x2Epi64(k: Mmask8, a: M256i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b = mm256Extracti64x2Epi64(a, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed 64-bit integers) from a, selected with IMM8, and stores
 * the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_extracti64x2_epi64&igExpand=2971)
 */
public fun mm512Extracti64x2Epi64(a: M512i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val a1 = a.asI64x8()
        when (imm8 and 3) {
            0 -> {
                run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
            }
            1 -> {
                run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }
            }
            2 -> {
                run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(4, 5)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(6, 7)); _simdArg0 }
            }
        }
    }

/**
 * Extracts 128 bits (composed of 2 packed 64-bit integers) from a, selected with IMM8, and stores
 * the result in dst using writemask k (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_extracti64x2_epi64&igExpand=2972)
 */
public fun mm512MaskExtracti64x2Epi64(src: M128i, k: Mmask8, a: M512i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val b = mm512Extracti64x2Epi64(a, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

/**
 * Extracts 128 bits (composed of 2 packed 64-bit integers) from a, selected with IMM8, and stores
 * the result in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_extracti64x2_epi64&igExpand=2973)
 */
public fun mm512MaskzExtracti64x2Epi64(k: Mmask8, a: M512i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val b = mm512Extracti64x2Epi64(a, imm8).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

// Insert

/**
 * Copy a to dst, then insert 256 bits (composed of 8 packed single-precision (32-bit) floating-point
 * elements) from b into dst at the location specified by IMM8.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_insertf32x8&igExpand=3850)
 */
public fun mm512Insertf32x8(a: M512, b: M256, imm8: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b1 = mm512Castps256Ps512(b)
        when (imm8 and 1) {
            0 -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(16, 17, 18, 19, 20, 21, 22, 23, 8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 22, 23)); _simdArg0 }
            }
        }
    }

/**
 * Copy a to tmp, then insert 256 bits (composed of 8 packed single-precision (32-bit) floating-point
 * elements) from b into tmp at the location specified by IMM8, and copy tmp to dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_insertf32x8&igExpand=3851)
 */
public fun mm512MaskInsertf32x8(src: M512, k: Mmask16, a: M512, b: M256, imm8: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm512Insertf32x8(a, b, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = c.asF32x16(); val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Copy a to tmp, then insert 256 bits (composed of 8 packed single-precision (32-bit) floating-point
 * elements) from b into tmp at the location specified by IMM8, and copy tmp to dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_insertf32x8&igExpand=3852)
 */
public fun mm512MaskzInsertf32x8(k: Mmask16, a: M512, b: M256, imm8: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm512Insertf32x8(a, b, imm8).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = c; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

/**
 * Copy a to dst, then insert 128 bits (composed of 2 packed double-precision (64-bit) floating-point
 * elements) from b into dst at the location specified by IMM8.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insertf64x2&igExpand=3853)
 */
public fun mm256Insertf64x2(a: M256d, b: M128d, imm8: Int): M256d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val b1 = mm256Castpd128Pd256(b)
        when (imm8 and 1) {
            0 -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(4, 5, 2, 3)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 4, 5)); _simdArg0 }
            }
        }
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed double-precision (64-bit) floating-point
 * elements) from b into tmp at the location specified by IMM8, and copy tmp to dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_insertf64x2&igExpand=3854)
 */
public fun mm256MaskInsertf64x2(src: M256d, k: Mmask8, a: M256d, b: M128d, imm8: Int): M256d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm256Insertf64x2(a, b, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = c.asF64x4(); val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed double-precision (64-bit) floating-point
 * elements) from b into tmp at the location specified by IMM8, and copy tmp to dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_insertf64x2&igExpand=3855)
 */
public fun mm256MaskzInsertf64x2(k: Mmask8, a: M256d, b: M128d, imm8: Int): M256d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm256Insertf64x2(a, b, imm8).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = c; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Copy a to dst, then insert 128 bits (composed of 2 packed double-precision (64-bit) floating-point
 * elements) from b into dst at the location specified by IMM8.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_insertf64x2&igExpand=3856)
 */
public fun mm512Insertf64x2(a: M512d, b: M128d, imm8: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val b1 = mm512Castpd128Pd512(b)
        when (imm8 and 3) {
            0 -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 2, 3, 4, 5, 6, 7)); _simdArg0 }
            }
            1 -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 8, 9, 4, 5, 6, 7)); _simdArg0 }
            }
            2 -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 8, 9, 6, 7)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 8, 9)); _simdArg0 }
            }
        }
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed double-precision (64-bit) floating-point
 * elements) from b into tmp at the location specified by IMM8, and copy tmp to dst using writemask k
 * (elements are copied from src if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_insertf64x2&igExpand=3857)
 */
public fun mm512MaskInsertf64x2(src: M512d, k: Mmask8, a: M512d, b: M128d, imm8: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val c = mm512Insertf64x2(a, b, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = c.asF64x8(); val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed double-precision (64-bit) floating-point
 * elements) from b into tmp at the location specified by IMM8, and copy tmp to dst using zeromask k
 * (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_insertf64x2&igExpand=3858)
 */
public fun mm512MaskzInsertf64x2(k: Mmask8, a: M512d, b: M128d, imm8: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val c = mm512Insertf64x2(a, b, imm8).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = c; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Copy a to dst, then insert 256 bits (composed of 8 packed 32-bit integers) from b into dst at the
 * location specified by IMM8.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_inserti32x8&igExpand=3869)
 */
public fun mm512Inserti32x8(a: M512i, b: M256i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val a1 = a.asI32x16()
        val b1 = mm512Castsi256Si512(b).asI32x16()
        val r: I32x16 =
            when (imm8 and 1) {
                0 -> {
                    run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(16, 17, 18, 19, 20, 21, 22, 23, 8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }
                }
                else -> {
                    run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 22, 23)); _simdArg0 }
                }
            }
        transmute(r)
    }

/**
 * Copy a to tmp, then insert 256 bits (composed of 8 packed 32-bit integers) from b into tmp at the
 * location specified by IMM8, and copy tmp to dst using writemask k (elements are copied from src if
 * the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_inserti32x8&igExpand=3870)
 */
public fun mm512MaskInserti32x8(src: M512i, k: Mmask16, a: M512i, b: M256i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm512Inserti32x8(a, b, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = c.asI32x16(); val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Copy a to tmp, then insert 256 bits (composed of 8 packed 32-bit integers) from b into tmp at the
 * location specified by IMM8, and copy tmp to dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_inserti32x8&igExpand=3871)
 */
public fun mm512MaskzInserti32x8(k: Mmask16, a: M512i, b: M256i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm512Inserti32x8(a, b, imm8).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = c; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Copy a to dst, then insert 128 bits (composed of 2 packed 64-bit integers) from b into dst at the
 * location specified by IMM8.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_inserti64x2&igExpand=3872)
 */
public fun mm256Inserti64x2(a: M256i, b: M128i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val a1 = a.asI64x4()
        val b1 = mm256Castsi128Si256(b).asI64x4()
        when (imm8 and 1) {
            0 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(4, 5, 2, 3)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 4, 5)); _simdArg0 }
            }
        }
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed 64-bit integers) from b into tmp at the
 * location specified by IMM8, and copy tmp to dst using writemask k (elements are copied from src if
 * the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_inserti64x2&igExpand=3873)
 */
public fun mm256MaskInserti64x2(src: M256i, k: Mmask8, a: M256i, b: M128i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm256Inserti64x2(a, b, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = c.asI64x4(); val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed 64-bit integers) from b into tmp at the
 * location specified by IMM8, and copy tmp to dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_inserti64x2&igExpand=3874)
 */
public fun mm256MaskzInserti64x2(k: Mmask8, a: M256i, b: M128i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 1)) { "IMM8 doesn't fit in 1 bits (unsigned)" }
        }
        val c = mm256Inserti64x2(a, b, imm8).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = c; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Copy a to dst, then insert 128 bits (composed of 2 packed 64-bit integers) from b into dst at the
 * location specified by IMM8.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_inserti64x2&igExpand=3875)
 */
public fun mm512Inserti64x2(a: M512i, b: M128i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val a1 = a.asI64x8()
        val b1 = mm512Castsi128Si512(b).asI64x8()
        when (imm8 and 3) {
            0 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 2, 3, 4, 5, 6, 7)); _simdArg0 }
            }
            1 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 8, 9, 4, 5, 6, 7)); _simdArg0 }
            }
            2 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 8, 9, 6, 7)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 8, 9)); _simdArg0 }
            }
        }
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed 64-bit integers) from b into tmp at the
 * location specified by IMM8, and copy tmp to dst using writemask k (elements are copied from src if
 * the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_inserti64x2&igExpand=3876)
 */
public fun mm512MaskInserti64x2(src: M512i, k: Mmask8, a: M512i, b: M128i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val c = mm512Inserti64x2(a, b, imm8)
        transmute(run { val _simdArg0 = k; val _simdArg1 = c.asI64x8(); val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

/**
 * Copy a to tmp, then insert 128 bits (composed of 2 packed 64-bit integers) from b into tmp at the
 * location specified by IMM8, and copy tmp to dst using zeromask k (elements are zeroed out if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_inserti64x2&igExpand=3877)
 */
public fun mm512MaskzInserti64x2(k: Mmask8, a: M512i, b: M128i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
        }
        val c = mm512Inserti64x2(a, b, imm8).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = c; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

// Convert

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundepi64_pd&igExpand=1437)
 */
public fun mm512CvtRoundepi64Pd(a: M512i, rounding: Int): M512d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtqq2pd512(a.asI64x8(), rounding))
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundepi64_pd&igExpand=1438)
 */
public fun mm512MaskCvtRoundepi64Pd(src: M512d, k: Mmask8, a: M512i, rounding: Int): M512d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepi64Pd(a, rounding).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundepi64_pd&igExpand=1439)
 */
public fun mm512MaskzCvtRoundepi64Pd(k: Mmask8, a: M512i, rounding: Int): M512d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepi64Pd(a, rounding).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi64_pd&igExpand=1705)
 */
public fun mmCvtepi64Pd(a: M128i): M128d = transmute(vcvtqq2pd128(a.asI64x2(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepi64_pd&igExpand=1706)
 */
public fun mmMaskCvtepi64Pd(src: M128d, k: Mmask8, a: M128i): M128d =
    run {
        val b = mmCvtepi64Pd(a).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepi64_pd&igExpand=1707)
 */
public fun mmMaskzCvtepi64Pd(k: Mmask8, a: M128i): M128d =
    run {
        val b = mmCvtepi64Pd(a).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi64_pd&igExpand=1708)
 */
public fun mm256Cvtepi64Pd(a: M256i): M256d = transmute(vcvtqq2pd256(a.asI64x4(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepi64_pd&igExpand=1709)
 */
public fun mm256MaskCvtepi64Pd(src: M256d, k: Mmask8, a: M256i): M256d =
    run {
        val b = mm256Cvtepi64Pd(a).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepi64_pd&igExpand=1710)
 */
public fun mm256MaskzCvtepi64Pd(k: Mmask8, a: M256i): M256d =
    run {
        val b = mm256Cvtepi64Pd(a).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepi64_pd&igExpand=1711)
 */
public fun mm512Cvtepi64Pd(a: M512i): M512d = transmute(vcvtqq2pd512(a.asI64x8(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepi64_pd&igExpand=1712)
 */
public fun mm512MaskCvtepi64Pd(src: M512d, k: Mmask8, a: M512i): M512d =
    run {
        val b = mm512Cvtepi64Pd(a).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepi64_pd&igExpand=1713)
 */
public fun mm512MaskzCvtepi64Pd(k: Mmask8, a: M512i): M512d =
    run {
        val b = mm512Cvtepi64Pd(a).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundepi64_ps&igExpand=1443)
 */
public fun mm512CvtRoundepi64Ps(a: M512i, rounding: Int): M256 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtqq2ps512(a.asI64x8(), rounding))
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundepi64_ps&igExpand=1444)
 */
public fun mm512MaskCvtRoundepi64Ps(src: M256, k: Mmask8, a: M512i, rounding: Int): M256 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepi64Ps(a, rounding).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundepi64_ps&igExpand=1445)
 */
public fun mm512MaskzCvtRoundepi64Ps(k: Mmask8, a: M512i, rounding: Int): M256 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepi64Ps(a, rounding).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi64_ps&igExpand=1723)
 */
public fun mmCvtepi64Ps(a: M128i): M128 = mmMaskCvtepi64Ps(mmUndefinedPs(), 0xffu, a)

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepi64_ps&igExpand=1724)
 */
public fun mmMaskCvtepi64Ps(src: M128, k: Mmask8, a: M128i): M128 = transmute(vcvtqq2ps128(a.asI64x2(), src.asF32x4(), k))

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepi64_ps&igExpand=1725)
 */
public fun mmMaskzCvtepi64Ps(k: Mmask8, a: M128i): M128 = mmMaskCvtepi64Ps(mmSetzeroPs(), k, a)

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi64_ps&igExpand=1726)
 */
public fun mm256Cvtepi64Ps(a: M256i): M128 = transmute(vcvtqq2ps256(a.asI64x4(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepi64_ps&igExpand=1727)
 */
public fun mm256MaskCvtepi64Ps(src: M128, k: Mmask8, a: M256i): M128 =
    run {
        val b = mm256Cvtepi64Ps(a).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepi64_ps&igExpand=1728)
 */
public fun mm256MaskzCvtepi64Ps(k: Mmask8, a: M256i): M128 =
    run {
        val b = mm256Cvtepi64Ps(a).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepi64_ps&igExpand=1729)
 */
public fun mm512Cvtepi64Ps(a: M512i): M256 = transmute(vcvtqq2ps512(a.asI64x8(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepi64_ps&igExpand=1730)
 */
public fun mm512MaskCvtepi64Ps(src: M256, k: Mmask8, a: M512i): M256 =
    run {
        val b = mm512Cvtepi64Ps(a).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Convert packed signed 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepi64_ps&igExpand=1731)
 */
public fun mm512MaskzCvtepi64Ps(k: Mmask8, a: M512i): M256 =
    run {
        val b = mm512Cvtepi64Ps(a).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundepu64_pd&igExpand=1455)
 */
public fun mm512CvtRoundepu64Pd(a: M512i, rounding: Int): M512d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtuqq2pd512(a.asU64x8(), rounding))
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundepu64_pd&igExpand=1456)
 */
public fun mm512MaskCvtRoundepu64Pd(src: M512d, k: Mmask8, a: M512i, rounding: Int): M512d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepu64Pd(a, rounding).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundepu64_pd&igExpand=1457)
 */
public fun mm512MaskzCvtRoundepu64Pd(k: Mmask8, a: M512i, rounding: Int): M512d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepu64Pd(a, rounding).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu64_pd&igExpand=1827)
 */
public fun mmCvtepu64Pd(a: M128i): M128d = transmute(vcvtuqq2pd128(a.asU64x2(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepu64_pd&igExpand=1828)
 */
public fun mmMaskCvtepu64Pd(src: M128d, k: Mmask8, a: M128i): M128d =
    run {
        val b = mmCvtepu64Pd(a).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x2(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepu64_pd&igExpand=1829)
 */
public fun mmMaskzCvtepu64Pd(k: Mmask8, a: M128i): M128d =
    run {
        val b = mmCvtepu64Pd(a).asF64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x2.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu64_pd&igExpand=1830)
 */
public fun mm256Cvtepu64Pd(a: M256i): M256d = transmute(vcvtuqq2pd256(a.asU64x4(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepu64_pd&igExpand=1831)
 */
public fun mm256MaskCvtepu64Pd(src: M256d, k: Mmask8, a: M256i): M256d =
    run {
        val b = mm256Cvtepu64Pd(a).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x4(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepu64_pd&igExpand=1832)
 */
public fun mm256MaskzCvtepu64Pd(k: Mmask8, a: M256i): M256d =
    run {
        val b = mm256Cvtepu64Pd(a).asF64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x4.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepu64_pd&igExpand=1833)
 */
public fun mm512Cvtepu64Pd(a: M512i): M512d = transmute(vcvtuqq2pd512(a.asU64x8(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepu64_pd&igExpand=1834)
 */
public fun mm512MaskCvtepu64Pd(src: M512d, k: Mmask8, a: M512i): M512d =
    run {
        val b = mm512Cvtepu64Pd(a).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF64x8(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed double-precision (64-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepu64_pd&igExpand=1835)
 */
public fun mm512MaskzCvtepu64Pd(k: Mmask8, a: M512i): M512d =
    run {
        val b = mm512Cvtepu64Pd(a).asF64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f64x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundepu64_ps&igExpand=1461)
 */
public fun mm512CvtRoundepu64Ps(a: M512i, rounding: Int): M256 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtuqq2ps512(a.asU64x8(), rounding))
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundepu64_ps&igExpand=1462)
 */
public fun mm512MaskCvtRoundepu64Ps(src: M256, k: Mmask8, a: M512i, rounding: Int): M256 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepu64Ps(a, rounding).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundepu64_ps&igExpand=1463)
 */
public fun mm512MaskzCvtRoundepu64Ps(k: Mmask8, a: M512i, rounding: Int): M256 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val b = mm512CvtRoundepu64Ps(a, rounding).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu64_ps&igExpand=1845)
 */
public fun mmCvtepu64Ps(a: M128i): M128 = mmMaskCvtepu64Ps(mmUndefinedPs(), 0xffu, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepu64_ps&igExpand=1846)
 */
public fun mmMaskCvtepu64Ps(src: M128, k: Mmask8, a: M128i): M128 = transmute(vcvtuqq2ps128(a.asU64x2(), src.asF32x4(), k))

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepu64_ps&igExpand=1847)
 */
public fun mmMaskzCvtepu64Ps(k: Mmask8, a: M128i): M128 = mmMaskCvtepu64Ps(mmSetzeroPs(), k, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepu64_ps&igExpand=1848)
 */
public fun mm256Cvtepu64Ps(a: M256i): M128 = transmute(vcvtuqq2ps256(a.asU64x4(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepu64_ps&igExpand=1849)
 */
public fun mm256MaskCvtepu64Ps(src: M128, k: Mmask8, a: M256i): M128 =
    run {
        val b = mm256Cvtepu64Ps(a).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepu64_ps&igExpand=1850)
 */
public fun mm256MaskzCvtepu64Ps(k: Mmask8, a: M256i): M128 =
    run {
        val b = mm256Cvtepu64Ps(a).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepu64_ps&igExpand=1851)
 */
public fun mm512Cvtepu64Ps(a: M512i): M256 = transmute(vcvtuqq2ps512(a.asU64x8(), MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepu64_ps&igExpand=1852)
 */
public fun mm512MaskCvtepu64Ps(src: M256, k: Mmask8, a: M512i): M256 =
    run {
        val b = mm512Cvtepu64Ps(a).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed single-precision (32-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepu64_ps&igExpand=1853)
 */
public fun mm512MaskzCvtepu64Ps(k: Mmask8, a: M512i): M256 =
    run {
        val b = mm512Cvtepu64Ps(a).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundpd_epi64&igExpand=1472)
 */
public fun mm512CvtRoundpdEpi64(a: M512d, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpdEpi64(mm512UndefinedEpi32(), 0xffu, a, rounding)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundpd_epi64&igExpand=1473)
 */
public fun mm512MaskCvtRoundpdEpi64(src: M512i, k: Mmask8, a: M512d, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtpd2qq512(a.asF64x8(), src.asI64x8(), k, rounding))
    }

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundpd_epi64&igExpand=1474)
 */
public fun mm512MaskzCvtRoundpdEpi64(k: Mmask8, a: M512d, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpdEpi64(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtpd_epi64&igExpand=1941)
 */
public fun mmCvtpdEpi64(a: M128d): M128i = mmMaskCvtpdEpi64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtpd_epi64&igExpand=1942)
 */
public fun mmMaskCvtpdEpi64(src: M128i, k: Mmask8, a: M128d): M128i = transmute(vcvtpd2qq128(a.asF64x2(), src.asI64x2(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtpd_epi64&igExpand=1943)
 */
public fun mmMaskzCvtpdEpi64(k: Mmask8, a: M128d): M128i = mmMaskCvtpdEpi64(mmSetzeroSi128(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtpd_epi64&igExpand=1944)
 */
public fun mm256CvtpdEpi64(a: M256d): M256i = mm256MaskCvtpdEpi64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtpd_epi64&igExpand=1945)
 */
public fun mm256MaskCvtpdEpi64(src: M256i, k: Mmask8, a: M256d): M256i = transmute(vcvtpd2qq256(a.asF64x4(), src.asI64x4(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtpd_epi64&igExpand=1946)
 */
public fun mm256MaskzCvtpdEpi64(k: Mmask8, a: M256d): M256i = mm256MaskCvtpdEpi64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtpd_epi64&igExpand=1947)
 */
public fun mm512CvtpdEpi64(a: M512d): M512i = mm512MaskCvtpdEpi64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtpd_epi64&igExpand=1948)
 */
public fun mm512MaskCvtpdEpi64(src: M512i, k: Mmask8, a: M512d): M512i = transmute(vcvtpd2qq512(a.asF64x8(), src.asI64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtpd_epi64&igExpand=1949)
 */
public fun mm512MaskzCvtpdEpi64(k: Mmask8, a: M512d): M512i = mm512MaskCvtpdEpi64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundps_epi64&igExpand=1514)
 */
public fun mm512CvtRoundpsEpi64(a: M256, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpsEpi64(mm512UndefinedEpi32(), 0xffu, a, rounding)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundps_epi64&igExpand=1515)
 */
public fun mm512MaskCvtRoundpsEpi64(src: M512i, k: Mmask8, a: M256, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtps2qq512(a.asF32x8(), src.asI64x8(), k, rounding))
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundps_epi64&igExpand=1516)
 */
public fun mm512MaskzCvtRoundpsEpi64(k: Mmask8, a: M256, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpsEpi64(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtps_epi64&igExpand=2075)
 */
public fun mmCvtpsEpi64(a: M128): M128i = mmMaskCvtpsEpi64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtps_epi64&igExpand=2076)
 */
public fun mmMaskCvtpsEpi64(src: M128i, k: Mmask8, a: M128): M128i = transmute(vcvtps2qq128(a.asF32x4(), src.asI64x2(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtps_epi64&igExpand=2077)
 */
public fun mmMaskzCvtpsEpi64(k: Mmask8, a: M128): M128i = mmMaskCvtpsEpi64(mmSetzeroSi128(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtps_epi64&igExpand=2078)
 */
public fun mm256CvtpsEpi64(a: M128): M256i = mm256MaskCvtpsEpi64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtps_epi64&igExpand=2079)
 */
public fun mm256MaskCvtpsEpi64(src: M256i, k: Mmask8, a: M128): M256i = transmute(vcvtps2qq256(a.asF32x4(), src.asI64x4(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtps_epi64&igExpand=2080)
 */
public fun mm256MaskzCvtpsEpi64(k: Mmask8, a: M128): M256i = mm256MaskCvtpsEpi64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtps_epi64&igExpand=2081)
 */
public fun mm512CvtpsEpi64(a: M256): M512i = mm512MaskCvtpsEpi64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtps_epi64&igExpand=2082)
 */
public fun mm512MaskCvtpsEpi64(src: M512i, k: Mmask8, a: M256): M512i = transmute(vcvtps2qq512(a.asF32x8(), src.asI64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtps_epi64&igExpand=2083)
 */
public fun mm512MaskzCvtpsEpi64(k: Mmask8, a: M256): M512i = mm512MaskCvtpsEpi64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundpd_epu64&igExpand=1478)
 */
public fun mm512CvtRoundpdEpu64(a: M512d, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpdEpu64(mm512UndefinedEpi32(), 0xffu, a, rounding)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundpd_epu64&igExpand=1479)
 */
public fun mm512MaskCvtRoundpdEpu64(src: M512i, k: Mmask8, a: M512d, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtpd2uqq512(a.asF64x8(), src.asU64x8(), k, rounding))
    }

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundpd_epu64&igExpand=1480)
 */
public fun mm512MaskzCvtRoundpdEpu64(k: Mmask8, a: M512d, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpdEpu64(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtpd_epu64&igExpand=1959)
 */
public fun mmCvtpdEpu64(a: M128d): M128i = mmMaskCvtpdEpu64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtpd_epu64&igExpand=1960)
 */
public fun mmMaskCvtpdEpu64(src: M128i, k: Mmask8, a: M128d): M128i = transmute(vcvtpd2uqq128(a.asF64x2(), src.asU64x2(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtpd_epu64&igExpand=1961)
 */
public fun mmMaskzCvtpdEpu64(k: Mmask8, a: M128d): M128i = mmMaskCvtpdEpu64(mmSetzeroSi128(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtpd_epu64&igExpand=1962)
 */
public fun mm256CvtpdEpu64(a: M256d): M256i = mm256MaskCvtpdEpu64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtpd_epu64&igExpand=1963)
 */
public fun mm256MaskCvtpdEpu64(src: M256i, k: Mmask8, a: M256d): M256i = transmute(vcvtpd2uqq256(a.asF64x4(), src.asU64x4(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtpd_epu64&igExpand=1964)
 */
public fun mm256MaskzCvtpdEpu64(k: Mmask8, a: M256d): M256i = mm256MaskCvtpdEpu64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtpd_epu64&igExpand=1965)
 */
public fun mm512CvtpdEpu64(a: M512d): M512i = mm512MaskCvtpdEpu64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtpd_epu64&igExpand=1966)
 */
public fun mm512MaskCvtpdEpu64(src: M512i, k: Mmask8, a: M512d): M512i = transmute(vcvtpd2uqq512(a.asF64x8(), src.asU64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtpd_epu64&igExpand=1967)
 */
public fun mm512MaskzCvtpdEpu64(k: Mmask8, a: M512d): M512i = mm512MaskCvtpdEpu64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst. Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvt_roundps_epu64&igExpand=1520)
 */
public fun mm512CvtRoundpsEpu64(a: M256, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpsEpu64(mm512UndefinedEpi32(), 0xffu, a, rounding)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set). Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvt_roundps_epu64&igExpand=1521)
 */
public fun mm512MaskCvtRoundpsEpu64(src: M512i, k: Mmask8, a: M256, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmute(vcvtps2uqq512(a.asF32x8(), src.asU64x8(), k, rounding))
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 * Rounding is done according to the ROUNDING parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvt_roundps_epu64&igExpand=1522)
 */
public fun mm512MaskzCvtRoundpsEpu64(k: Mmask8, a: M256, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpsEpu64(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtps_epu64&igExpand=2093)
 */
public fun mmCvtpsEpu64(a: M128): M128i = mmMaskCvtpsEpu64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtps_epu64&igExpand=2094)
 */
public fun mmMaskCvtpsEpu64(src: M128i, k: Mmask8, a: M128): M128i = transmute(vcvtps2uqq128(a.asF32x4(), src.asU64x2(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtps_epu64&igExpand=2095)
 */
public fun mmMaskzCvtpsEpu64(k: Mmask8, a: M128): M128i = mmMaskCvtpsEpu64(mmSetzeroSi128(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtps_epu64&igExpand=2096)
 */
public fun mm256CvtpsEpu64(a: M128): M256i = mm256MaskCvtpsEpu64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtps_epu64&igExpand=2097)
 */
public fun mm256MaskCvtpsEpu64(src: M256i, k: Mmask8, a: M128): M256i = transmute(vcvtps2uqq256(a.asF32x4(), src.asU64x4(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtps_epu64&igExpand=2098)
 */
public fun mm256MaskzCvtpsEpu64(k: Mmask8, a: M128): M256i = mm256MaskCvtpsEpu64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtps_epu64&igExpand=2099)
 */
public fun mm512CvtpsEpu64(a: M256): M512i = mm512MaskCvtpsEpu64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using writemask k (elements are copied from src if the corresponding bit is
 * not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtps_epu64&igExpand=2100)
 */
public fun mm512MaskCvtpsEpu64(src: M512i, k: Mmask8, a: M256): M512i = transmute(vcvtps2uqq512(a.asF32x8(), src.asU64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtps_epu64&igExpand=2101)
 */
public fun mm512MaskzCvtpsEpu64(k: Mmask8, a: M256): M512i = mm512MaskCvtpsEpu64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtt_roundpd_epi64&igExpand=2264)
 */
public fun mm512CvttRoundpdEpi64(a: M512d, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpdEpi64(mm512UndefinedEpi32(), 0xffu, a, sae)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtt_roundpd_epi64&igExpand=2265)
 */
public fun mm512MaskCvttRoundpdEpi64(src: M512i, k: Mmask8, a: M512d, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vcvttpd2qq512(a.asF64x8(), src.asI64x8(), k, sae))
    }

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtt_roundpd_epi64&igExpand=2266)
 */
public fun mm512MaskzCvttRoundpdEpi64(k: Mmask8, a: M512d, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpdEpi64(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttpd_epi64&igExpand=2329)
 */
public fun mmCvttpdEpi64(a: M128d): M128i = mmMaskCvttpdEpi64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvttpd_epi64&igExpand=2330)
 */
public fun mmMaskCvttpdEpi64(src: M128i, k: Mmask8, a: M128d): M128i = transmute(vcvttpd2qq128(a.asF64x2(), src.asI64x2(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvttpd_epi64&igExpand=2331)
 */
public fun mmMaskzCvttpdEpi64(k: Mmask8, a: M128d): M128i = mmMaskCvttpdEpi64(mmSetzeroSi128(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvttpd_epi64&igExpand=2332)
 */
public fun mm256CvttpdEpi64(a: M256d): M256i = mm256MaskCvttpdEpi64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvttpd_epi64&igExpand=2333)
 */
public fun mm256MaskCvttpdEpi64(src: M256i, k: Mmask8, a: M256d): M256i = transmute(vcvttpd2qq256(a.asF64x4(), src.asI64x4(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvttpd_epi64&igExpand=2334)
 */
public fun mm256MaskzCvttpdEpi64(k: Mmask8, a: M256d): M256i = mm256MaskCvttpdEpi64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvttpd_epi64&igExpand=2335)
 */
public fun mm512CvttpdEpi64(a: M512d): M512i = mm512MaskCvttpdEpi64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvttpd_epi64&igExpand=2336)
 */
public fun mm512MaskCvttpdEpi64(src: M512i, k: Mmask8, a: M512d): M512i = transmute(vcvttpd2qq512(a.asF64x8(), src.asI64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvttpd_epi64&igExpand=2337)
 */
public fun mm512MaskzCvttpdEpi64(k: Mmask8, a: M512d): M512i = mm512MaskCvttpdEpi64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtt_roundps_epi64&igExpand=2294)
 */
public fun mm512CvttRoundpsEpi64(a: M256, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpsEpi64(mm512UndefinedEpi32(), 0xffu, a, sae)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtt_roundps_epi64&igExpand=2295)
 */
public fun mm512MaskCvttRoundpsEpi64(src: M512i, k: Mmask8, a: M256, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vcvttps2qq512(a.asF32x8(), src.asI64x8(), k, sae))
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtt_roundps_epi64&igExpand=2296)
 */
public fun mm512MaskzCvttRoundpsEpi64(k: Mmask8, a: M256, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpsEpi64(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttps_epi64&igExpand=2420)
 */
public fun mmCvttpsEpi64(a: M128): M128i = mmMaskCvttpsEpi64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvttps_epi64&igExpand=2421)
 */
public fun mmMaskCvttpsEpi64(src: M128i, k: Mmask8, a: M128): M128i = transmute(vcvttps2qq128(a.asF32x4(), src.asI64x2(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvttps_epi64&igExpand=2422)
 */
public fun mmMaskzCvttpsEpi64(k: Mmask8, a: M128): M128i = mmMaskCvttpsEpi64(mmSetzeroSi128(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvttps_epi64&igExpand=2423)
 */
public fun mm256CvttpsEpi64(a: M128): M256i = mm256MaskCvttpsEpi64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvttps_epi64&igExpand=2424)
 */
public fun mm256MaskCvttpsEpi64(src: M256i, k: Mmask8, a: M128): M256i = transmute(vcvttps2qq256(a.asF32x4(), src.asI64x4(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvttps_epi64&igExpand=2425)
 */
public fun mm256MaskzCvttpsEpi64(k: Mmask8, a: M128): M256i = mm256MaskCvttpsEpi64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvttps_epi64&igExpand=2426)
 */
public fun mm512CvttpsEpi64(a: M256): M512i = mm512MaskCvttpsEpi64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvttps_epi64&igExpand=2427)
 */
public fun mm512MaskCvttpsEpi64(src: M512i, k: Mmask8, a: M256): M512i = transmute(vcvttps2qq512(a.asF32x8(), src.asI64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed signed 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvttps_epi64&igExpand=2428)
 */
public fun mm512MaskzCvttpsEpi64(k: Mmask8, a: M256): M512i = mm512MaskCvttpsEpi64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtt_roundpd_epu64&igExpand=1965)
 */
public fun mm512CvttRoundpdEpu64(a: M512d, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpdEpu64(mm512UndefinedEpi32(), 0xffu, a, sae)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtt_roundpd_epu64&igExpand=1966)
 */
public fun mm512MaskCvttRoundpdEpu64(src: M512i, k: Mmask8, a: M512d, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vcvttpd2uqq512(a.asF64x8(), src.asU64x8(), k, sae))
    }

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtt_roundpd_epu64&igExpand=1967)
 */
public fun mm512MaskzCvttRoundpdEpu64(k: Mmask8, a: M512d, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpdEpu64(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttpd_epu64&igExpand=2347)
 */
public fun mmCvttpdEpu64(a: M128d): M128i = mmMaskCvttpdEpu64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvttpd_epu64&igExpand=2348)
 */
public fun mmMaskCvttpdEpu64(src: M128i, k: Mmask8, a: M128d): M128i = transmute(vcvttpd2uqq128(a.asF64x2(), src.asU64x2(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvttpd_epu64&igExpand=2349)
 */
public fun mmMaskzCvttpdEpu64(k: Mmask8, a: M128d): M128i = mmMaskCvttpdEpu64(mmSetzeroSi128(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvttpd_epu64&igExpand=2350)
 */
public fun mm256CvttpdEpu64(a: M256d): M256i = mm256MaskCvttpdEpu64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the results in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvttpd_epu64&igExpand=2351)
 */
public fun mm256MaskCvttpdEpu64(src: M256i, k: Mmask8, a: M256d): M256i = transmute(vcvttpd2uqq256(a.asF64x4(), src.asU64x4(), k))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the results in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvttpd_epu64&igExpand=2352)
 */
public fun mm256MaskzCvttpdEpu64(k: Mmask8, a: M256d): M256i = mm256MaskCvttpdEpu64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvttpd_epu64&igExpand=2353)
 */
public fun mm512CvttpdEpu64(a: M512d): M512i = mm512MaskCvttpdEpu64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvttpd_epu64&igExpand=2354)
 */
public fun mm512MaskCvttpdEpu64(src: M512i, k: Mmask8, a: M512d): M512i = transmute(vcvttpd2uqq512(a.asF64x8(), src.asU64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvttpd_epu64&igExpand=2355)
 */
public fun mm512MaskzCvttpdEpu64(k: Mmask8, a: M512d): M512i = mm512MaskCvttpdEpu64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtt_roundps_epu64&igExpand=2300)
 */
public fun mm512CvttRoundpsEpu64(a: M256, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpsEpu64(mm512UndefinedEpi32(), 0xffu, a, sae)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtt_roundps_epu64&igExpand=2301)
 */
public fun mm512MaskCvttRoundpsEpu64(src: M512i, k: Mmask8, a: M256, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vcvttps2uqq512(a.asF32x8(), src.asU64x8(), k, sae))
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC to the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtt_roundps_epu64&igExpand=2302)
 */
public fun mm512MaskzCvttRoundpsEpu64(k: Mmask8, a: M256, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundpsEpu64(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttps_epu64&igExpand=2438)
 */
public fun mmCvttpsEpu64(a: M128): M128i = mmMaskCvttpsEpu64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvttps_epu64&igExpand=2439)
 */
public fun mmMaskCvttpsEpu64(src: M128i, k: Mmask8, a: M128): M128i = transmute(vcvttps2uqq128(a.asF32x4(), src.asU64x2(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvttps_epu64&igExpand=2440)
 */
public fun mmMaskzCvttpsEpu64(k: Mmask8, a: M128): M128i = mmMaskCvttpsEpu64(mmSetzeroSi128(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvttps_epu64&igExpand=2441)
 */
public fun mm256CvttpsEpu64(a: M128): M256i = mm256MaskCvttpsEpu64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvttps_epu64&igExpand=2442)
 */
public fun mm256MaskCvttpsEpu64(src: M256i, k: Mmask8, a: M128): M256i = transmute(vcvttps2uqq256(a.asF32x4(), src.asU64x4(), k))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvttps_epu64&igExpand=2443)
 */
public fun mm256MaskzCvttpsEpu64(k: Mmask8, a: M128): M256i = mm256MaskCvttpsEpu64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvttps_epu64&igExpand=2444)
 */
public fun mm512CvttpsEpu64(a: M256): M512i = mm512MaskCvttpsEpu64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using writemask k (elements are copied from src if the
 * corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvttps_epu64&igExpand=2445)
 */
public fun mm512MaskCvttpsEpu64(src: M512i, k: Mmask8, a: M256): M512i = transmute(vcvttps2uqq512(a.asF32x8(), src.asU64x8(), k, MM_FROUND_CUR_DIRECTION))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed unsigned 64-bit integers
 * with truncation, and store the result in dst using zeromask k (elements are zeroed out if the corresponding
 * bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvttps_epu64&igExpand=2446)
 */
public fun mm512MaskzCvttpsEpu64(k: Mmask8, a: M256): M512i = mm512MaskCvttpsEpu64(mm512SetzeroSi512(), k, a)

// Multiply-Low

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst`.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mullo_epi64&igExpand=4778)
 */
public fun mmMulloEpi64(a: M128i, b: M128i): M128i = transmute(run { val _simdArg0 = a.asI64x2(); val _simdArg1 = b.asI64x2(); _simdArg0 })

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst` using writemask `k` (elements are copied from
 * `src` if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mullo_epi64&igExpand=4776)
 */
public fun mmMaskMulloEpi64(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val b1 = mmMulloEpi64(a, b).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b1; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst` using zeromask `k` (elements are zeroed out if
 * the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mullo_epi64&igExpand=4777)
 */
public fun mmMaskzMulloEpi64(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val b1 = mmMulloEpi64(a, b).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b1; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst`.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mullo_epi64&igExpand=4781)
 */
public fun mm256MulloEpi64(a: M256i, b: M256i): M256i = transmute(run { val _simdArg0 = a.asI64x4(); val _simdArg1 = b.asI64x4(); _simdArg0 })

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst` using writemask `k` (elements are copied from
 * `src` if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mullo_epi64&igExpand=4779)
 */
public fun mm256MaskMulloEpi64(src: M256i, k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val b1 = mm256MulloEpi64(a, b).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b1; val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst` using zeromask `k` (elements are zeroed out if
 * the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mullo_epi64&igExpand=4780)
 */
public fun mm256MaskzMulloEpi64(k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val b1 = mm256MulloEpi64(a, b).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b1; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst`.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mullo_epi64&igExpand=4784)
 */
public fun mm512MulloEpi64(a: M512i, b: M512i): M512i = transmute(run { val _simdArg0 = a.asI64x8(); val _simdArg1 = b.asI64x8(); _simdArg0 })

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst` using writemask `k` (elements are copied from
 * `src` if the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mullo_epi64&igExpand=4782)
 */
public fun mm512MaskMulloEpi64(src: M512i, k: Mmask8, a: M512i, b: M512i): M512i =
    run {
        val b1 = mm512MulloEpi64(a, b).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b1; val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

/**
 * Multiply packed 64-bit integers in `a` and `b`, producing intermediate 128-bit integers, and store
 * the low 64 bits of the intermediate integers in `dst` using zeromask `k` (elements are zeroed out if
 * the corresponding bit is not set).
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mullo_epi64&igExpand=4783)
 */
public fun mm512MaskzMulloEpi64(k: Mmask8, a: M512i, b: M512i): M512i =
    run {
        val b1 = mm512MulloEpi64(a, b).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = b1; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

// Mask Registers

/**
 * Convert 8-bit mask a to a 32-bit integer value and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_cvtmask8_u32&igExpand=1891)
 */
public fun cvtmask8U32(a: Mmask8): UInt = a.toUInt()

/**
 * Convert 32-bit integer value a to an 8-bit mask and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_cvtu32_mask8&igExpand=2467)
 */
public fun cvtu32Mask8(a: UInt): Mmask8 = a.toUByte()

/**
 * Add 16-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kadd_mask16&igExpand=3903)
 */
public fun kaddMask16(a: Mmask16, b: Mmask16): Mmask16 = (a + (b.toUShort())).toUShort()

/**
 * Add 8-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kadd_mask8&igExpand=3906)
 */
public fun kaddMask8(a: Mmask8, b: Mmask8): Mmask8 = (a + (b.toUByte())).toUByte()

/**
 * Bitwise AND of 8-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kand_mask8&igExpand=3911)
 */
public fun kandMask8(a: Mmask8, b: Mmask8): Mmask8 = a and b

/**
 * Bitwise AND NOT of 8-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kandn_mask8&igExpand=3916)
 */
public fun kandnMask8(a: Mmask8, b: Mmask8): Mmask8 = knotMask8(a) and b

/**
 * Bitwise NOT of 8-bit mask a, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_knot_mask8&igExpand=3922)
 */
public fun knotMask8(a: Mmask8): Mmask8 = a xor 0xFFu.toUByte()

/**
 * Bitwise OR of 8-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kor_mask8&igExpand=3927)
 */
public fun korMask8(a: Mmask8, b: Mmask8): Mmask8 = a or b

/**
 * Bitwise XNOR of 8-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kxnor_mask8&igExpand=3969)
 */
public fun kxnorMask8(a: Mmask8, b: Mmask8): Mmask8 = knotMask8(kxorMask8(a, b))

/**
 * Bitwise XOR of 8-bit masks a and b, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kxor_mask8&igExpand=3974)
 */
public fun kxorMask8(a: Mmask8, b: Mmask8): Mmask8 = a xor b

/**
 * Compute the bitwise OR of 8-bit masks a and b. If the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst. If the result is all ones, store 1 in allOnes, otherwise store 0 in allOnes.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortest_mask8_u8&igExpand=3931)
 */
public fun kortestMask8U8(a: Mmask8, b: Mmask8, allOnes: MutPtr): UByte {
    val allOnes1 = allOnes
    val tmp = korMask8(a, b)
    (PrimitiveLayouts.U8).writeNative((allOnes1).rawAddress, (if (tmp == 0xffu.toUByte()) 1u else 0u).toUByte())
    return (if (tmp == 0u.toUByte()) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise OR of 8-bit masks a and b. If the result is all ones, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortestc_mask8_u8&igExpand=3936)
 */
public fun kortestcMask8U8(a: Mmask8, b: Mmask8): UByte = (if (korMask8(a, b) == 0xffu.toUByte()) 1u else 0u).toUByte()

/**
 * Compute the bitwise OR of 8-bit masks a and b. If the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortestz_mask8_u8&igExpand=3941)
 */
public fun kortestzMask8U8(a: Mmask8, b: Mmask8): UByte = (if (korMask8(a, b) == 0u.toUByte()) 1u else 0u).toUByte()

/**
 * Shift 8-bit mask a left by count bits while shifting in zeros, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kshiftli_mask8&igExpand=3945)
 */
public fun kshiftliMask8(a: Mmask8, count: UInt): Mmask8 = a.unboundedShl(count)

/**
 * Shift 8-bit mask a right by count bits while shifting in zeros, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kshiftri_mask8&igExpand=3949)
 */
public fun kshiftriMask8(a: Mmask8, count: UInt): Mmask8 = a.unboundedShr(count)

/**
 * Compute the bitwise AND of 16-bit masks a and b, and if the result is all zeros, store 1 in dst,
 * otherwise store 0 in dst. Compute the bitwise NOT of a and then AND with b, if the result is all
 * zeros, store 1 in andNot, otherwise store 0 in andNot.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktest_mask16_u8&igExpand=3950)
 */
public fun ktestMask16U8(a: Mmask16, b: Mmask16, andNot: MutPtr): UByte {
    val andNot1 = andNot
    (PrimitiveLayouts.U8).writeNative((andNot1).rawAddress, (if (kandnMask16(a, b) == 0u.toUShort()) 1u else 0u).toUByte())
    return (if (kandMask16(a, b) == 0u.toUShort()) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise AND of 8-bit masks a and b, and if the result is all zeros, store 1 in dst,
 * otherwise store 0 in dst. Compute the bitwise NOT of a and then AND with b, if the result is all
 * zeros, store 1 in andNot, otherwise store 0 in andNot.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktest_mask8_u8&igExpand=3953)
 */
public fun ktestMask8U8(a: Mmask8, b: Mmask8, andNot: MutPtr): UByte {
    val andNot1 = andNot
    (PrimitiveLayouts.U8).writeNative((andNot1).rawAddress, (if (kandnMask8(a, b) == 0u.toUByte()) 1u else 0u).toUByte())
    return (if (kandMask8(a, b) == 0u.toUByte()) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise NOT of 16-bit mask a and then AND with 16-bit mask b, if the result is all
 * zeros, store 1 in dst, otherwise store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestc_mask16_u8&igExpand=3954)
 */
public fun ktestcMask16U8(a: Mmask16, b: Mmask16): UByte = (if (kandnMask16(a, b) == 0u.toUShort()) 1u else 0u).toUByte()

/**
 * Compute the bitwise NOT of 8-bit mask a and then AND with 8-bit mask b, if the result is all
 * zeros, store 1 in dst, otherwise store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestc_mask8_u8&igExpand=3957)
 */
public fun ktestcMask8U8(a: Mmask8, b: Mmask8): UByte = (if (kandnMask8(a, b) == 0u.toUByte()) 1u else 0u).toUByte()

/**
 * Compute the bitwise AND of 16-bit masks a and  b, if the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestz_mask16_u8&igExpand=3958)
 */
public fun ktestzMask16U8(a: Mmask16, b: Mmask16): UByte = (if (kandMask16(a, b) == 0u.toUShort()) 1u else 0u).toUByte()

/**
 * Compute the bitwise AND of 8-bit masks a and  b, if the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestz_mask8_u8&igExpand=3961)
 */
public fun ktestzMask8U8(a: Mmask8, b: Mmask8): UByte = (if (kandMask8(a, b) == 0u.toUByte()) 1u else 0u).toUByte()

/**
 * Load 8-bit mask from memory
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_load_mask8&igExpand=3999)
 */
public fun loadMask8(memAddr: ConstPtr): Mmask8 = (UnresolvedTypeLayout<Mmask8>()).readNative((memAddr).rawAddress)

/**
 * Store 8-bit mask to memory
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_store_mask8&igExpand=6468)
 */
public fun storeMask8(memAddr: MutPtr, a: Mmask8) {
    val memAddr1 = memAddr
    (UnresolvedTypeLayout<Mmask8>()).writeNative((memAddr1).rawAddress, a)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 32-bit
 * integer in a.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movepi32_mask&igExpand=4612)
 */
public fun mmMovepi32Mask(a: M128i): Mmask8 {
    val zero = mmSetzeroSi128()
    return mmCmpltEpi32Mask(a, zero)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 32-bit
 * integer in a.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movepi32_mask&igExpand=4613)
 */
public fun mm256Movepi32Mask(a: M256i): Mmask8 {
    val zero = mm256SetzeroSi256()
    return mm256CmpltEpi32Mask(a, zero)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 32-bit
 * integer in a.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movepi32_mask&igExpand=4614)
 */
public fun mm512Movepi32Mask(a: M512i): Mmask16 {
    val zero = mm512SetzeroSi512()
    return mm512CmpltEpi32Mask(a, zero)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 64-bit
 * integer in a.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movepi64_mask&igExpand=4615)
 */
public fun mmMovepi64Mask(a: M128i): Mmask8 {
    val zero = mmSetzeroSi128()
    return mmCmpltEpi64Mask(a, zero)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 64-bit
 * integer in a.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movepi64_mask&igExpand=4616)
 */
public fun mm256Movepi64Mask(a: M256i): Mmask8 {
    val zero = mm256SetzeroSi256()
    return mm256CmpltEpi64Mask(a, zero)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 64-bit
 * integer in a.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movepi64_mask&igExpand=4617)
 */
public fun mm512Movepi64Mask(a: M512i): Mmask8 {
    val zero = mm512SetzeroSi512()
    return mm512CmpltEpi64Mask(a, zero)
}

/**
 * Set each packed 32-bit integer in dst to all ones or all zeros based on the value of the corresponding
 * bit in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movm_epi32&igExpand=4625)
 */
public fun mmMovmEpi32(k: Mmask8): M128i {
    val ones = mmSet1Epi32(-1)
    return mmMaskzMovEpi32(k, ones)
}

/**
 * Set each packed 32-bit integer in dst to all ones or all zeros based on the value of the corresponding
 * bit in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movm_epi32&igExpand=4626)
 */
public fun mm256MovmEpi32(k: Mmask8): M256i {
    val ones = mm256Set1Epi32(-1)
    return mm256MaskzMovEpi32(k, ones)
}

/**
 * Set each packed 32-bit integer in dst to all ones or all zeros based on the value of the corresponding
 * bit in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movm_epi32&igExpand=4627)
 */
public fun mm512MovmEpi32(k: Mmask16): M512i {
    val ones = mm512Set1Epi32(-1)
    return mm512MaskzMovEpi32(k, ones)
}

/**
 * Set each packed 64-bit integer in dst to all ones or all zeros based on the value of the corresponding
 * bit in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movm_epi64&igExpand=4628)
 */
public fun mmMovmEpi64(k: Mmask8): M128i {
    val ones = mmSet1Epi64x(-1L)
    return mmMaskzMovEpi64(k, ones)
}

/**
 * Set each packed 64-bit integer in dst to all ones or all zeros based on the value of the corresponding
 * bit in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movm_epi64&igExpand=4629)
 */
public fun mm256MovmEpi64(k: Mmask8): M256i {
    val ones = mm256Set1Epi64x(-1L)
    return mm256MaskzMovEpi64(k, ones)
}

/**
 * Set each packed 64-bit integer in dst to all ones or all zeros based on the value of the corresponding
 * bit in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movm_epi64&igExpand=4630)
 */
public fun mm512MovmEpi64(k: Mmask8): M512i {
    val ones = mm512Set1Epi64(-1L)
    return mm512MaskzMovEpi64(k, ones)
}

// Range

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_range_round_pd&igExpand=5210)
 */
public fun mm512RangeRoundPd(a: M512d, b: M512d, imm8: Int, sae: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskRangeRoundPd(mm512SetzeroPd(), 0xffu, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_range_round_pd&igExpand=5208)
 */
public fun mm512MaskRangeRoundPd(src: M512d, k: Mmask8, a: M512d, b: M512d, imm8: Int, sae: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vrangepd512(a.asF64x8(), b.asF64x8(), imm8, src.asF64x8(), k, sae))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_range_round_pd&igExpand=5209)
 */
public fun mm512MaskzRangeRoundPd(k: Mmask8, a: M512d, b: M512d, imm8: Int, sae: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskRangeRoundPd(mm512SetzeroPd(), k, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_range_pd&igExpand=5192)
 */
public fun mmRangePd(a: M128d, b: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mmMaskRangePd(mmSetzeroPd(), 0xffu, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_range_pd&igExpand=5190)
 */
public fun mmMaskRangePd(src: M128d, k: Mmask8, a: M128d, b: M128d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangepd128(a.asF64x2(), b.asF64x2(), imm8, src.asF64x2(), k))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_range_pd&igExpand=5191)
 */
public fun mmMaskzRangePd(k: Mmask8, a: M128d, b: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mmMaskRangePd(mmSetzeroPd(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_range_pd&igExpand=5195)
 */
public fun mm256RangePd(a: M256d, b: M256d, imm8: Int): M256d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm256MaskRangePd(mm256SetzeroPd(), 0xffu, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_range_pd&igExpand=5193)
 */
public fun mm256MaskRangePd(src: M256d, k: Mmask8, a: M256d, b: M256d, imm8: Int): M256d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangepd256(a.asF64x4(), b.asF64x4(), imm8, src.asF64x4(), k))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_range_pd&igExpand=5194)
 */
public fun mm256MaskzRangePd(k: Mmask8, a: M256d, b: M256d, imm8: Int): M256d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm256MaskRangePd(mm256SetzeroPd(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_range_pd&igExpand=5198)
 */
public fun mm512RangePd(a: M512d, b: M512d, imm8: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm512MaskRangePd(mm512SetzeroPd(), 0xffu, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_range_pd&igExpand=5196)
 */
public fun mm512MaskRangePd(src: M512d, k: Mmask8, a: M512d, b: M512d, imm8: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangepd512(a.asF64x8(), b.asF64x8(), imm8, src.asF64x8(), k, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * double-precision (64-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_range_pd&igExpand=5197)
 */
public fun mm512MaskzRangePd(k: Mmask8, a: M512d, b: M512d, imm8: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm512MaskRangePd(mm512SetzeroPd(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_range_round_ps&igExpand=5213)
 */
public fun mm512RangeRoundPs(a: M512, b: M512, imm8: Int, sae: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskRangeRoundPs(mm512SetzeroPs(), 0xffffu, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_range_round_ps&igExpand=5211)
 */
public fun mm512MaskRangeRoundPs(src: M512, k: Mmask16, a: M512, b: M512, imm8: Int, sae: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vrangeps512(a.asF32x16(), b.asF32x16(), imm8, src.asF32x16(), k, sae))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_range_round_ps&igExpand=5212)
 */
public fun mm512MaskzRangeRoundPs(k: Mmask16, a: M512, b: M512, imm8: Int, sae: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskRangeRoundPs(mm512SetzeroPs(), k, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_range_ps&igExpand=5201)
 */
public fun mmRangePs(a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mmMaskRangePs(mmSetzeroPs(), 0xffu, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_range_ps&igExpand=5199)
 */
public fun mmMaskRangePs(src: M128, k: Mmask8, a: M128, b: M128, imm8: Int): M128 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangeps128(a.asF32x4(), b.asF32x4(), imm8, src.asF32x4(), k))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_range_ps&igExpand=5200)
 */
public fun mmMaskzRangePs(k: Mmask8, a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mmMaskRangePs(mmSetzeroPs(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_range_ps&igExpand=5204)
 */
public fun mm256RangePs(a: M256, b: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm256MaskRangePs(mm256SetzeroPs(), 0xffu, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_range_ps&igExpand=5202)
 */
public fun mm256MaskRangePs(src: M256, k: Mmask8, a: M256, b: M256, imm8: Int): M256 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangeps256(a.asF32x8(), b.asF32x8(), imm8, src.asF32x8(), k))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_range_ps&igExpand=5203)
 */
public fun mm256MaskzRangePs(k: Mmask8, a: M256, b: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm256MaskRangePs(mm256SetzeroPs(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_range_ps&igExpand=5207)
 */
public fun mm512RangePs(a: M512, b: M512, imm8: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm512MaskRangePs(mm512SetzeroPs(), 0xffffu, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src to dst if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_range_ps&igExpand=5205)
 */
public fun mm512MaskRangePs(src: M512, k: Mmask16, a: M512, b: M512, imm8: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangeps512(a.asF32x16(), b.asF32x16(), imm8, src.asF32x16(), k, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for packed
 * single-precision (32-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out if the corresponding mask bit is not set).
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_range_ps&igExpand=5206)
 */
public fun mm512MaskzRangePs(k: Mmask16, a: M512, b: M512, imm8: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mm512MaskRangePs(mm512SetzeroPs(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * double-precision (64-bit) floating-point element in a and b, store the result in the lower element
 * of dst, and copy the upper element from a to the upper element of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_range_round_sd&igExpand=5216)
 */
public fun mmRangeRoundSd(a: M128d, b: M128d, imm8: Int, sae: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskRangeRoundSd(mmSetzeroPd(), 0xffu, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * double-precision (64-bit) floating-point element in a and b, store the result in the lower element
 * of dst using writemask k (the element is copied from src when mask bit 0 is not set), and copy the
 * upper element from a to the upper element of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_range_round_sd&igExpand=5214)
 */
public fun mmMaskRangeRoundSd(src: M128d, k: Mmask8, a: M128d, b: M128d, imm8: Int, sae: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vrangesd(a.asF64x2(), b.asF64x2(), src.asF64x2(), k, imm8, sae))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * double-precision (64-bit) floating-point element in a and b, store the result in the lower element
 * of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper
 * element from a to the upper element of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_range_round_sd&igExpand=5215)
 */
public fun mmMaskzRangeRoundSd(k: Mmask8, a: M128d, b: M128d, imm8: Int, sae: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskRangeRoundSd(mmSetzeroPd(), k, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * double-precision (64-bit) floating-point element in a and b, store the result in the lower element
 * of dst using writemask k (the element is copied from src when mask bit 0 is not set), and copy the
 * upper element from a to the upper element of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_range_sd&igExpand=5220)
 */
public fun mmMaskRangeSd(src: M128d, k: Mmask8, a: M128d, b: M128d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangesd(a.asF64x2(), b.asF64x2(), src.asF64x2(), k, imm8, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * double-precision (64-bit) floating-point element in a and b, store the result in the lower element
 * of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper
 * element from a to the upper element of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_range_sd&igExpand=5221)
 */
public fun mmMaskzRangeSd(k: Mmask8, a: M128d, b: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mmMaskRangeSd(mmSetzeroPd(), k, a, b, imm8)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * single-precision (32-bit) floating-point element in a and b, store the result in the lower element
 * of dst, and copy the upper 3 packed elements from a to the upper elements of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_range_round_ss&igExpand=5219)
 */
public fun mmRangeRoundSs(a: M128, b: M128, imm8: Int, sae: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskRangeRoundSs(mmSetzeroPs(), 0xffu, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * single-precision (32-bit) floating-point element in a and b, store the result in the lower element
 * of dst using writemask k (the element is copied from src when mask bit 0 is not set), and copy the
 * upper 3 packed elements from a to the upper elements of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_range_round_ss&igExpand=5217)
 */
public fun mmMaskRangeRoundSs(src: M128, k: Mmask8, a: M128, b: M128, imm8: Int, sae: Int): M128 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vrangess(a.asF32x4(), b.asF32x4(), src.asF32x4(), k, imm8, sae))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * single-precision (32-bit) floating-point element in a and b, store the result in the lower element
 * of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper
 * 3 packed elements from a to the upper elements of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_range_round_ss&igExpand=5218)
 */
public fun mmMaskzRangeRoundSs(k: Mmask8, a: M128, b: M128, imm8: Int, sae: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskRangeRoundSs(mmSetzeroPs(), k, a, b, imm8, sae)
}

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * single-precision (32-bit) floating-point element in a and b, store the result in the lower element
 * of dst using writemask k (the element is copied from src when mask bit 0 is not set), and copy the
 * upper 3 packed elements from a to the upper elements of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_range_ss&igExpand=5222)
 */
public fun mmMaskRangeSs(src: M128, k: Mmask8, a: M128, b: M128, imm8: Int): M128 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
        }
        transmute(vrangess(a.asF32x4(), b.asF32x4(), src.asF32x4(), k, imm8, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Calculate the max, min, absolute max, or absolute min (depending on control in imm8) for the lower
 * single-precision (32-bit) floating-point element in a and b, store the result in the lower element
 * of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper
 * 3 packed elements from a to the upper elements of dst.
 * Lower 2 bits of IMM8 specifies the operation control:
 *     00 = min, 01 = max, 10 = absolute min, 11 = absolute max.
 * Upper 2 bits of IMM8 specifies the sign control:
 *     00 = sign from a, 01 = sign from compare result, 10 = clear sign bit, 11 = set sign bit.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_range_ss&igExpand=5223)
 */
public fun mmMaskzRangeSs(k: Mmask8, a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return mmMaskRangeSs(mmSetzeroPs(), k, a, b, imm8)
}

// Reduce

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_reduce_round_pd&igExpand=5438)
 */
public fun mm512ReduceRoundPd(a: M512d, imm8: Int, sae: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskReduceRoundPd(mm512UndefinedPd(), 0xffu, a, imm8, sae)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_reduce_round_pd&igExpand=5436)
 */
public fun mm512MaskReduceRoundPd(src: M512d, k: Mmask8, a: M512d, imm8: Int, sae: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vreducepd512(a.asF64x8(), imm8, src.asF64x8(), k, sae))
    }

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_reduce_round_pd&igExpand=5437)
 */
public fun mm512MaskzReduceRoundPd(k: Mmask8, a: M512d, imm8: Int, sae: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskReduceRoundPd(mm512SetzeroPd(), k, a, imm8, sae)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_pd&igExpand=5411)
 */
public fun mmReducePd(a: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReducePd(mmUndefinedPd(), 0xffu, a, imm8)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_pd&igExpand=5409)
 */
public fun mmMaskReducePd(src: M128d, k: Mmask8, a: M128d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreducepd128(a.asF64x2(), imm8, src.asF64x2(), k))
    }

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_reduce_pd&igExpand=5410)
 */
public fun mmMaskzReducePd(k: Mmask8, a: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReducePd(mmSetzeroPd(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_pd&igExpand=5414)
 */
public fun mm256ReducePd(a: M256d, imm8: Int): M256d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskReducePd(mm256UndefinedPd(), 0xffu, a, imm8)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_pd&igExpand=5412)
 */
public fun mm256MaskReducePd(src: M256d, k: Mmask8, a: M256d, imm8: Int): M256d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreducepd256(a.asF64x4(), imm8, src.asF64x4(), k))
    }

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_reduce_pd&igExpand=5413)
 */
public fun mm256MaskzReducePd(k: Mmask8, a: M256d, imm8: Int): M256d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskReducePd(mm256SetzeroPd(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_reduce_pd&igExpand=5417)
 */
public fun mm512ReducePd(a: M512d, imm8: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReducePd(mm512UndefinedPd(), 0xffu, a, imm8)
}

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_reduce_pd&igExpand=5415)
 */
public fun mm512MaskReducePd(src: M512d, k: Mmask8, a: M512d, imm8: Int): M512d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreducepd512(a.asF64x8(), imm8, src.asF64x8(), k, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Extract the reduced argument of packed double-precision (64-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_reduce_pd&igExpand=5416)
 */
public fun mm512MaskzReducePd(k: Mmask8, a: M512d, imm8: Int): M512d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReducePd(mm512SetzeroPd(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_reduce_round_ps&igExpand=5444)
 */
public fun mm512ReduceRoundPs(a: M512, imm8: Int, sae: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskReduceRoundPs(mm512UndefinedPs(), 0xffffu, a, imm8, sae)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_reduce_round_ps&igExpand=5442)
 */
public fun mm512MaskReduceRoundPs(src: M512, k: Mmask16, a: M512, imm8: Int, sae: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vreduceps512(a.asF32x16(), imm8, src.asF32x16(), k, sae))
    }

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_reduce_round_ps&igExpand=5443)
 */
public fun mm512MaskzReduceRoundPs(k: Mmask16, a: M512, imm8: Int, sae: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskReduceRoundPs(mm512SetzeroPs(), k, a, imm8, sae)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_ps&igExpand=5429)
 */
public fun mmReducePs(a: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReducePs(mmUndefinedPs(), 0xffu, a, imm8)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_ps&igExpand=5427)
 */
public fun mmMaskReducePs(src: M128, k: Mmask8, a: M128, imm8: Int): M128 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreduceps128(a.asF32x4(), imm8, src.asF32x4(), k))
    }

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_reduce_ps&igExpand=5428)
 */
public fun mmMaskzReducePs(k: Mmask8, a: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReducePs(mmSetzeroPs(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_ps&igExpand=5432)
 */
public fun mm256ReducePs(a: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskReducePs(mm256UndefinedPs(), 0xffu, a, imm8)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_ps&igExpand=5430)
 */
public fun mm256MaskReducePs(src: M256, k: Mmask8, a: M256, imm8: Int): M256 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreduceps256(a.asF32x8(), imm8, src.asF32x8(), k))
    }

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_reduce_ps&igExpand=5431)
 */
public fun mm256MaskzReducePs(k: Mmask8, a: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskReducePs(mm256SetzeroPs(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_reduce_ps&igExpand=5435)
 */
public fun mm512ReducePs(a: M512, imm8: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReducePs(mm512UndefinedPs(), 0xffffu, a, imm8)
}

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using writemask k (elements are
 * copied from src to dst if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_reduce_ps&igExpand=5433)
 */
public fun mm512MaskReducePs(src: M512, k: Mmask16, a: M512, imm8: Int): M512 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreduceps512(a.asF32x16(), imm8, src.asF32x16(), k, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Extract the reduced argument of packed single-precision (32-bit) floating-point elements in a by
 * the number of bits specified by imm8, and store the results in dst using zeromask k (elements are
 * zeroed out if the corresponding mask bit is not set).
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_reduce_ps&igExpand=5434)
 */
public fun mm512MaskzReducePs(k: Mmask16, a: M512, imm8: Int): M512 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReducePs(mm512SetzeroPs(), k, a, imm8)
}

/**
 * Extract the reduced argument of the lower double-precision (64-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst, and copy
 * the upper element from a to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_round_sd&igExpand=5447)
 */
public fun mmReduceRoundSd(a: M128d, b: M128d, imm8: Int, sae: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskReduceRoundSd(mmUndefinedPd(), 0xffu, a, b, imm8, sae)
}

/**
 * Extract the reduced argument of the lower double-precision (64-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using writemask
 * k (the element is copied from src when mask bit 0 is not set), and copy the upper element from a
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_round_sd&igExpand=5445)
 */
public fun mmMaskReduceRoundSd(src: M128d, k: Mmask8, a: M128d, b: M128d, imm8: Int, sae: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vreducesd(a.asF64x2(), b.asF64x2(), src.asF64x2(), k, imm8, sae))
    }

/**
 * Extract the reduced argument of the lower double-precision (64-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using zeromask
 * k (the element is zeroed out when mask bit 0 is not set), and copy the upper element from a
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_reduce_round_sd&igExpand=5446)
 */
public fun mmMaskzReduceRoundSd(k: Mmask8, a: M128d, b: M128d, imm8: Int, sae: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskReduceRoundSd(mmSetzeroPd(), k, a, b, imm8, sae)
}

/**
 * Extract the reduced argument of the lower double-precision (64-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using, and
 * copy the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_sd&igExpand=5456)
 */
public fun mmReduceSd(a: M128d, b: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceSd(mmUndefinedPd(), 0xffu, a, b, imm8)
}

/**
 * Extract the reduced argument of the lower double-precision (64-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using writemask
 * k (the element is copied from src when mask bit 0 is not set), and copy the upper element from a
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_sd&igExpand=5454)
 */
public fun mmMaskReduceSd(src: M128d, k: Mmask8, a: M128d, b: M128d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreducesd(a.asF64x2(), b.asF64x2(), src.asF64x2(), k, imm8, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Extract the reduced argument of the lower double-precision (64-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using zeromask
 * k (the element is zeroed out when mask bit 0 is not set), and copy the upper element from a
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_reduce_sd&igExpand=5455)
 */
public fun mmMaskzReduceSd(k: Mmask8, a: M128d, b: M128d, imm8: Int): M128d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceSd(mmSetzeroPd(), k, a, b, imm8)
}

/**
 * Extract the reduced argument of the lower single-precision (32-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst, and copy
 * the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_round_ss&igExpand=5453)
 */
public fun mmReduceRoundSs(a: M128, b: M128, imm8: Int, sae: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskReduceRoundSs(mmUndefinedPs(), 0xffu, a, b, imm8, sae)
}

/**
 * Extract the reduced argument of the lower single-precision (32-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using writemask
 * k (the element is copied from src when mask bit 0 is not set), and copy the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_round_ss&igExpand=5451)
 */
public fun mmMaskReduceRoundSs(src: M128, k: Mmask8, a: M128, b: M128, imm8: Int, sae: Int): M128 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmute(vreducess(a.asF32x4(), b.asF32x4(), src.asF32x4(), k, imm8, sae))
    }

/**
 * Extract the reduced argument of the lower single-precision (32-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using zeromask
 * k (the element is zeroed out when mask bit 0 is not set), and copy the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_reduce_round_ss&igExpand=5452)
 */
public fun mmMaskzReduceRoundSs(k: Mmask8, a: M128, b: M128, imm8: Int, sae: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskReduceRoundSs(mmSetzeroPs(), k, a, b, imm8, sae)
}

/**
 * Extract the reduced argument of the lower single-precision (32-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst, and copy
 * the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_ss&igExpand=5462)
 */
public fun mmReduceSs(a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceSs(mmUndefinedPs(), 0xffu, a, b, imm8)
}

/**
 * Extract the reduced argument of the lower single-precision (32-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using writemask
 * k (the element is copied from src when mask bit 0 is not set), and copy the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_ss&igExpand=5460)
 */
public fun mmMaskReduceSs(src: M128, k: Mmask8, a: M128, b: M128, imm8: Int): M128 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmute(vreducess(a.asF32x4(), b.asF32x4(), src.asF32x4(), k, imm8, MM_FROUND_CUR_DIRECTION))
    }

/**
 * Extract the reduced argument of the lower single-precision (32-bit) floating-point element in b
 * by the number of bits specified by imm8, store the result in the lower element of dst using zeromask
 * k (the element is zeroed out when mask bit 0 is not set), and copy the upper element from a.
 * to the upper element of dst.
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_reduce_ss&igExpand=5461)
 */
public fun mmMaskzReduceSs(k: Mmask8, a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceSs(mmSetzeroPs(), k, a, b, imm8)
}

// FP-Class

/**
 * Test packed double-precision (64-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fpclass_pd_mask&igExpand=3493)
 */
public fun mmFpclassPdMask(a: M128d, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vfpclasspd128(a.asF64x2(), imm8)
}

/**
 * Test packed double-precision (64-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_fpclass_pd_mask&igExpand=3494)
 */
public fun mmMaskFpclassPdMask(k1: Mmask8, a: M128d, imm8: Int): Mmask8 = mmFpclassPdMask(a, imm8) and k1

/**
 * Test packed double-precision (64-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fpclass_pd_mask&igExpand=3495)
 */
public fun mm256FpclassPdMask(a: M256d, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vfpclasspd256(a.asF64x4(), imm8)
}

/**
 * Test packed double-precision (64-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_fpclass_pd_mask&igExpand=3496)
 */
public fun mm256MaskFpclassPdMask(k1: Mmask8, a: M256d, imm8: Int): Mmask8 = mm256FpclassPdMask(a, imm8) and k1

/**
 * Test packed double-precision (64-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_fpclass_pd_mask&igExpand=3497)
 */
public fun mm512FpclassPdMask(a: M512d, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vfpclasspd512(a.asF64x8(), imm8)
}

/**
 * Test packed double-precision (64-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_fpclass_pd_mask&igExpand=3498)
 */
public fun mm512MaskFpclassPdMask(k1: Mmask8, a: M512d, imm8: Int): Mmask8 = mm512FpclassPdMask(a, imm8) and k1

/**
 * Test packed single-precision (32-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fpclass_ps_mask&igExpand=3505)
 */
public fun mmFpclassPsMask(a: M128, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vfpclassps128(a.asF32x4(), imm8)
}

/**
 * Test packed single-precision (32-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_fpclass_ps_mask&igExpand=3506)
 */
public fun mmMaskFpclassPsMask(k1: Mmask8, a: M128, imm8: Int): Mmask8 = mmFpclassPsMask(a, imm8) and k1

/**
 * Test packed single-precision (32-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_fpclass_ps_mask&igExpand=3507)
 */
public fun mm256FpclassPsMask(a: M256, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vfpclassps256(a.asF32x8(), imm8)
}

/**
 * Test packed single-precision (32-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_fpclass_ps_mask&igExpand=3508)
 */
public fun mm256MaskFpclassPsMask(k1: Mmask8, a: M256, imm8: Int): Mmask8 = mm256FpclassPsMask(a, imm8) and k1

/**
 * Test packed single-precision (32-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_fpclass_ps_mask&igExpand=3509)
 */
public fun mm512FpclassPsMask(a: M512, imm8: Int): Mmask16 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vfpclassps512(a.asF32x16(), imm8)
}

/**
 * Test packed single-precision (32-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_fpclass_ps_mask&igExpand=3510)
 */
public fun mm512MaskFpclassPsMask(k1: Mmask16, a: M512, imm8: Int): Mmask16 = mm512FpclassPsMask(a, imm8) and k1

/**
 * Test the lower double-precision (64-bit) floating-point element in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fpclass_sd_mask&igExpand=3511)
 */
public fun mmFpclassSdMask(a: M128d, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskFpclassSdMask(0xffu, a, imm8)
}

/**
 * Test the lower double-precision (64-bit) floating-point element in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_fpclass_sd_mask&igExpand=3512)
 */
public fun mmMaskFpclassSdMask(k1: Mmask8, a: M128d, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vfpclasssd(a.asF64x2(), imm8, k1)
    }

/**
 * Test the lower single-precision (32-bit) floating-point element in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_fpclass_ss_mask&igExpand=3515)
 */
public fun mmFpclassSsMask(a: M128, imm8: Int): Mmask8 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskFpclassSsMask(0xffu, a, imm8)
}

/**
 * Test the lower single-precision (32-bit) floating-point element in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     - 0x01 // QNaN
 *     - 0x02 // Positive Zero
 *     - 0x04 // Negative Zero
 *     - 0x08 // Positive Infinity
 *     - 0x10 // Negative Infinity
 *     - 0x20 // Denormal
 *     - 0x40 // Negative
 *     - 0x80 // SNaN
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_fpclass_ss_mask&igExpand=3516)
 */
public fun mmMaskFpclassSsMask(k1: Mmask8, a: M128, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vfpclassss(a.asF32x4(), imm8, k1)
    }

public expect fun vcvtqq2pd128(a: I64x2, rounding: Int): F64x2

public expect fun vcvtqq2pd256(a: I64x4, rounding: Int): F64x4

public expect fun vcvtqq2pd512(a: I64x8, rounding: Int): F64x8

public expect fun vcvtqq2ps128(a: I64x2, src: F32x4, k: Mmask8): F32x4

public expect fun vcvtqq2ps256(a: I64x4, rounding: Int): F32x4

public expect fun vcvtqq2ps512(a: I64x8, rounding: Int): F32x8

public expect fun vcvtuqq2pd128(a: U64x2, rounding: Int): F64x2

public expect fun vcvtuqq2pd256(a: U64x4, rounding: Int): F64x4

public expect fun vcvtuqq2pd512(a: U64x8, rounding: Int): F64x8

public expect fun vcvtuqq2ps128(a: U64x2, src: F32x4, k: Mmask8): F32x4

public expect fun vcvtuqq2ps256(a: U64x4, rounding: Int): F32x4

public expect fun vcvtuqq2ps512(a: U64x8, rounding: Int): F32x8

public expect fun vcvtpd2qq128(a: F64x2, src: I64x2, k: Mmask8): I64x2

public expect fun vcvtpd2qq256(a: F64x4, src: I64x4, k: Mmask8): I64x4

public expect fun vcvtpd2qq512(a: F64x8, src: I64x8, k: Mmask8, rounding: Int): I64x8

public expect fun vcvtps2qq128(a: F32x4, src: I64x2, k: Mmask8): I64x2

public expect fun vcvtps2qq256(a: F32x4, src: I64x4, k: Mmask8): I64x4

public expect fun vcvtps2qq512(a: F32x8, src: I64x8, k: Mmask8, rounding: Int): I64x8

public expect fun vcvtpd2uqq128(a: F64x2, src: U64x2, k: Mmask8): U64x2

public expect fun vcvtpd2uqq256(a: F64x4, src: U64x4, k: Mmask8): U64x4

public expect fun vcvtpd2uqq512(a: F64x8, src: U64x8, k: Mmask8, rounding: Int): U64x8

public expect fun vcvtps2uqq128(a: F32x4, src: U64x2, k: Mmask8): U64x2

public expect fun vcvtps2uqq256(a: F32x4, src: U64x4, k: Mmask8): U64x4

public expect fun vcvtps2uqq512(a: F32x8, src: U64x8, k: Mmask8, rounding: Int): U64x8

public expect fun vcvttpd2qq128(a: F64x2, src: I64x2, k: Mmask8): I64x2

public expect fun vcvttpd2qq256(a: F64x4, src: I64x4, k: Mmask8): I64x4

public expect fun vcvttpd2qq512(a: F64x8, src: I64x8, k: Mmask8, sae: Int): I64x8

public expect fun vcvttps2qq128(a: F32x4, src: I64x2, k: Mmask8): I64x2

public expect fun vcvttps2qq256(a: F32x4, src: I64x4, k: Mmask8): I64x4

public expect fun vcvttps2qq512(a: F32x8, src: I64x8, k: Mmask8, sae: Int): I64x8

public expect fun vcvttpd2uqq128(a: F64x2, src: U64x2, k: Mmask8): U64x2

public expect fun vcvttpd2uqq256(a: F64x4, src: U64x4, k: Mmask8): U64x4

public expect fun vcvttpd2uqq512(a: F64x8, src: U64x8, k: Mmask8, sae: Int): U64x8

public expect fun vcvttps2uqq128(a: F32x4, src: U64x2, k: Mmask8): U64x2

public expect fun vcvttps2uqq256(a: F32x4, src: U64x4, k: Mmask8): U64x4

public expect fun vcvttps2uqq512(a: F32x8, src: U64x8, k: Mmask8, sae: Int): U64x8

public expect fun vrangepd128(a: F64x2, b: F64x2, imm8: Int, src: F64x2, k: Mmask8): F64x2

public expect fun vrangepd256(a: F64x4, b: F64x4, imm8: Int, src: F64x4, k: Mmask8): F64x4

public expect fun vrangepd512(a: F64x8, b: F64x8, imm8: Int, src: F64x8, k: Mmask8, sae: Int): F64x8

public expect fun vrangeps128(a: F32x4, b: F32x4, imm8: Int, src: F32x4, k: Mmask8): F32x4

public expect fun vrangeps256(a: F32x8, b: F32x8, imm8: Int, src: F32x8, k: Mmask8): F32x8

public expect fun vrangeps512(a: F32x16, b: F32x16, imm8: Int, src: F32x16, k: Mmask16, sae: Int): F32x16

public expect fun vrangesd(a: F64x2, b: F64x2, src: F64x2, k: Mmask8, imm8: Int, sae: Int): F64x2

public expect fun vrangess(a: F32x4, b: F32x4, src: F32x4, k: Mmask8, imm8: Int, sae: Int): F32x4

public expect fun vreducepd128(a: F64x2, imm8: Int, src: F64x2, k: Mmask8): F64x2

public expect fun vreducepd256(a: F64x4, imm8: Int, src: F64x4, k: Mmask8): F64x4

public expect fun vreducepd512(a: F64x8, imm8: Int, src: F64x8, k: Mmask8, sae: Int): F64x8

public expect fun vreduceps128(a: F32x4, imm8: Int, src: F32x4, k: Mmask8): F32x4

public expect fun vreduceps256(a: F32x8, imm8: Int, src: F32x8, k: Mmask8): F32x8

public expect fun vreduceps512(a: F32x16, imm8: Int, src: F32x16, k: Mmask16, sae: Int): F32x16

public expect fun vreducesd(a: F64x2, b: F64x2, src: F64x2, k: Mmask8, imm8: Int, sae: Int): F64x2

public expect fun vreducess(a: F32x4, b: F32x4, src: F32x4, k: Mmask8, imm8: Int, sae: Int): F32x4

public expect fun vfpclasspd128(a: F64x2, imm8: Int): Mmask8

public expect fun vfpclasspd256(a: F64x4, imm8: Int): Mmask8

public expect fun vfpclasspd512(a: F64x8, imm8: Int): Mmask8

public expect fun vfpclassps128(a: F32x4, imm8: Int): Mmask8

public expect fun vfpclassps256(a: F32x8, imm8: Int): Mmask8

public expect fun vfpclassps512(a: F32x16, imm8: Int): Mmask16

public expect fun vfpclasssd(a: F64x2, imm8: Int, k: Mmask8): Mmask8

public expect fun vfpclassss(a: F32x4, imm8: Int, k: Mmask8): Mmask8
