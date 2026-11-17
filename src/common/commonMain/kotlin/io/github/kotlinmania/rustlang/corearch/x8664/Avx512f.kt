// port-lint: source x86_64/avx512f.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128
import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.x86.MM_FROUND_CUR_DIRECTION

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to a 64-bit integer, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_i64&expand=1792)
 */
public fun mmCvtsdI64(a: M128d): Long = mmCvtsdSi64(a)

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to a 64-bit integer, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtss_i64&expand=1894)
 */
public fun mmCvtssI64(a: M128): Long = mmCvtssSi64(a)

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to an unsigned 64-bit integer, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtss_u64&expand=1902)
 */
public fun mmCvtssU64(a: M128): ULong = vcvtss2usi64(a.asF32x4(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to an unsigned 64-bit integer, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_u64&expand=1800)
 */
public fun mmCvtsdU64(a: M128d): ULong = vcvtsd2usi64(a.asF64x2(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert the signed 64-bit integer b to a single-precision (32-bit) floating-point element, store the result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=mmCvti64Ss&expand=1643)
 */
public fun mmCvti64Ss(a: M128, b: Long): M128 =
    run {
        val b1 = b.toFloat()
        run { val simdArg0 = a; val simdArg1 = 0; val simdArg2 = b1; simdArg0 }
    }

/**
 * Convert the signed 64-bit integer b to a double-precision (64-bit) floating-point element, store the result in the lower element of dst, and copy the upper element from a to the upper element of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvti64_sd&expand=1644)
 */
public fun mmCvti64Sd(a: M128d, b: Long): M128d =
    run {
        val b1 = b.toDouble()
        run { val simdArg0 = a; val simdArg1 = 0; val simdArg2 = b1; simdArg0 }
    }

/**
 * Convert the unsigned 64-bit integer b to a single-precision (32-bit) floating-point element, store the result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtu64_ss&expand=2035)
 */
public fun mmCvtu64Ss(a: M128, b: ULong): M128 =
    run {
        val b1 = b.toFloat()
        run { val simdArg0 = a; val simdArg1 = 0; val simdArg2 = b1; simdArg0 }
    }

/**
 * Convert the unsigned 64-bit integer b to a double-precision (64-bit) floating-point element, store the result in the lower element of dst, and copy the upper element from a to the upper element of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtu64_sd&expand=2034)
 */
public fun mmCvtu64Sd(a: M128d, b: ULong): M128d =
    run {
        val b1 = b.toDouble()
        run { val simdArg0 = a; val simdArg1 = 0; val simdArg2 = b1; simdArg0 }
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to a 64-bit integer with truncation, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttsd_i64&expand=2016)
 */
public fun mmCvttsdI64(a: M128d): Long = vcvttsd2si64(a.asF64x2(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to an unsigned 64-bit integer with truncation, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttsd_u64&expand=2021)
 */
public fun mmCvttsdU64(a: M128d): ULong = vcvttsd2usi64(a.asF64x2(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to a 64-bit integer with truncation, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=#text=_mm_cvttss_i64&expand=2023)
 */
public fun mmCvttssI64(a: M128): Long = vcvttss2si64(a.asF32x4(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to an unsigned 64-bit integer with truncation, and store the result in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttss_u64&expand=2027)
 */
public fun mmCvttssU64(a: M128): ULong = vcvttss2usi64(a.asF32x4(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert the signed 64-bit integer b to a double-precision (64-bit) floating-point element, store the result in the lower element of dst, and copy the upper element from a to the upper element of dst.
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundi64_sd&expand=1313)
 */
public fun mmCvtRoundi64Sd(a: M128d, b: Long, rounding: Int): M128d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        val r = vcvtsi2sd64(a1, b, rounding)
        transmuteWithLayouts<F64x2, M128d>(r, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<M128d>())
    }

/**
 * Convert the signed 64-bit integer b to a double-precision (64-bit) floating-point element, store the result in the lower element of dst, and copy the upper element from a to the upper element of dst.
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundsi64_sd&expand=1367)
 */
public fun mmCvtRoundsi64Sd(a: M128d, b: Long, rounding: Int): M128d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        val r = vcvtsi2sd64(a1, b, rounding)
        transmuteWithLayouts<F64x2, M128d>(r, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<M128d>())
    }

/**
 * Convert the signed 64-bit integer b to a single-precision (32-bit) floating-point element, store the result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundi64_ss&expand=1314)
 */
public fun mmCvtRoundi64Ss(a: M128, b: Long, rounding: Int): M128 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        val r = vcvtsi2ss64(a1, b, rounding)
        transmuteWithLayouts<F32x4, M128>(r, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<M128>())
    }

/**
 * Convert the unsigned 64-bit integer b to a double-precision (64-bit) floating-point element, store the result in the lower element of dst, and copy the upper element from a to the upper element of dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundu64_sd&expand=1379)
 */
public fun mmCvtRoundu64Sd(a: M128d, b: ULong, rounding: Int): M128d =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        val r = vcvtusi2sd64(a1, b, rounding)
        transmuteWithLayouts<F64x2, M128d>(r, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<M128d>())
    }

/**
 * Convert the signed 64-bit integer b to a single-precision (32-bit) floating-point element, store the result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundsi64_ss&expand=1368)
 */
public fun mmCvtRoundsi64Ss(a: M128, b: Long, rounding: Int): M128 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        val r = vcvtsi2ss64(a1, b, rounding)
        transmuteWithLayouts<F32x4, M128>(r, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<M128>())
    }

/**
 * Convert the unsigned 64-bit integer b to a single-precision (32-bit) floating-point element, store the result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements of dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundu64_ss&expand=1380)
 */
public fun mmCvtRoundu64Ss(a: M128, b: ULong, rounding: Int): M128 =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        val r = vcvtusi2ss64(a1, b, rounding)
        transmuteWithLayouts<F32x4, M128>(r, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<M128>())
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to a 64-bit integer, and store the result in dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundsd_si64&expand=1360)
 */
public fun mmCvtRoundsdSi64(a: M128d, rounding: Int): Long =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        vcvtsd2si64(a1, rounding)
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to a 64-bit integer, and store the result in dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundsd_i64&expand=1358)
 */
public fun mmCvtRoundsdI64(a: M128d, rounding: Int): Long =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        vcvtsd2si64(a1, rounding)
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to an unsigned 64-bit integer, and store the result in dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundsd_u64&expand=1365)
 */
public fun mmCvtRoundsdU64(a: M128d, rounding: Int): ULong =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        vcvtsd2usi64(a1, rounding)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to a 64-bit integer, and store the result in dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundss_si64&expand=1375)
 */
public fun mmCvtRoundssSi64(a: M128, rounding: Int): Long =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        vcvtss2si64(a1, rounding)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to a 64-bit integer, and store the result in dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundss_i64&expand=1370)
 */
public fun mmCvtRoundssI64(a: M128, rounding: Int): Long =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        vcvtss2si64(a1, rounding)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to an unsigned 64-bit integer, and store the result in dst.\
 * Rounding is done according to the rounding\[3:0\] parameter, which can be one of:\
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_roundss_u64&expand=1377)
 */
public fun mmCvtRoundssU64(a: M128, rounding: Int): ULong =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        vcvtss2usi64(a1, rounding)
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to a 64-bit integer with truncation, and store the result in dst.\
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_roundsd_si64&expand=1931)
 */
public fun mmCvttRoundsdSi64(a: M128d, sae: Int): Long =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        vcvttsd2si64(a1, sae)
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to a 64-bit integer with truncation, and store the result in dst.\
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_roundsd_i64&expand=1929)
 */
public fun mmCvttRoundsdI64(a: M128d, sae: Int): Long =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        vcvttsd2si64(a1, sae)
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in a to an unsigned 64-bit integer with truncation, and store the result in dst.\
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_roundsd_u64&expand=1933)
 */
public fun mmCvttRoundsdU64(a: M128d, sae: Int): ULong =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        val a1 = a.asF64x2()
        vcvttsd2usi64(a1, sae)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to a 64-bit integer with truncation, and store the result in dst.\
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_roundss_i64&expand=1935)
 */
public fun mmCvttRoundssI64(a: M128, sae: Int): Long =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        vcvttss2si64(a1, sae)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to a 64-bit integer with truncation, and store the result in dst.\
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_roundss_si64&expand=1937)
 */
public fun mmCvttRoundssSi64(a: M128, sae: Int): Long =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        vcvttss2si64(a1, sae)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in a to an unsigned 64-bit integer with truncation, and store the result in dst.\
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_roundss_u64&expand=1939)
 */
public fun mmCvttRoundssU64(a: M128, sae: Int): ULong =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        val a1 = a.asF32x4()
        vcvttss2usi64(a1, sae)
    }

public expect fun vcvtss2si64(a: F32x4, rounding: Int): Long

public expect fun vcvtss2usi64(a: F32x4, rounding: Int): ULong

public expect fun vcvtsd2si64(a: F64x2, rounding: Int): Long

public expect fun vcvtsd2usi64(a: F64x2, rounding: Int): ULong

public expect fun vcvtsi2ss64(a: F32x4, b: Long, rounding: Int): F32x4

public expect fun vcvtsi2sd64(a: F64x2, b: Long, rounding: Int): F64x2

public expect fun vcvtusi2ss64(a: F32x4, b: ULong, rounding: Int): F32x4

public expect fun vcvtusi2sd64(a: F64x2, b: ULong, rounding: Int): F64x2

public expect fun vcvttss2si64(a: F32x4, rounding: Int): Long

public expect fun vcvttss2usi64(a: F32x4, rounding: Int): ULong

public expect fun vcvttsd2si64(a: F64x2, rounding: Int): Long

public expect fun vcvttsd2usi64(a: F64x2, rounding: Int): ULong
