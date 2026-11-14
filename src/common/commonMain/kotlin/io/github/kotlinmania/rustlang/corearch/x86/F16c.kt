// port-lint: source x86/f16c.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.F16x4
import io.github.kotlinmania.rustlang.corearch.simd.F16x8
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F32x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x8

// [F16C intrinsics].
//
// [F16C intrinsics]: https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=fp16&expand=1769

public expect fun llvmVcvtps2ph128(a: F32x4, rounding: Int): I16x8

public expect fun llvmVcvtps2ph256(a: F32x8, rounding: Int): I16x8

/**
 * Converts the 4 x 16-bit half-precision float values in the lowest 64-bit of
 * the 128-bit vector `a` into 4 x 32-bit float values stored in a 128-bit wide
 * vector.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_ps)
 */
public fun mmCvtphPs(a: M128i): M128 =
    run {
        val a1: F16x8 = transmute(a)
        val a2: F16x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }
        run { val _simdArg0 = a2; _simdArg0 }
    }

/**
 * Converts the 8 x 16-bit half-precision float values in the 128-bit vector
 * `a` into 8 x 32-bit float values stored in a 256-bit wide vector.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_ps)
 */
public fun mm256CvtphPs(a: M128i): M256 =
    run {
        val a1: F16x8 = transmute(a)
        run { val _simdArg0 = a1; _simdArg0 }
    }

/**
 * Converts the 4 x 32-bit float values in the 128-bit vector `a` into 4 x
 * 16-bit half-precision float values stored in the lowest 64-bit of a 128-bit
 * vector.
 *
 * Rounding is done according to the `immRounding` parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtps_ph)
 */
public fun mmCvtpsPh(a: M128, immRounding: Int): M128i {
    run {
        check(immRounding >= 0 && immRounding < 5) { "Invalid IMM value" }
    }
    return run {
        val a1 = a.asF32x4()
        val r = llvmVcvtps2ph128(a1, immRounding)
        transmute(r)
    }
}

/**
 * Converts the 8 x 32-bit float values in the 256-bit vector `a` into 8 x
 * 16-bit half-precision float values stored in a 128-bit wide vector.
 *
 * Rounding is done according to the `immRounding` parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtps_ph)
 */
public fun mm256CvtpsPh(a: M256, immRounding: Int): M128i {
    run {
        check(immRounding >= 0 && immRounding < 5) { "Invalid IMM value" }
    }
    return run {
        val a1 = a.asF32x8()
        val r = llvmVcvtps2ph256(a1, immRounding)
        transmute(r)
    }
}
