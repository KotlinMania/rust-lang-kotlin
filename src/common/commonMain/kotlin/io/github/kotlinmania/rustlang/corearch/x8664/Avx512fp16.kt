// port-lint: source x86_64/avx512fp16.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.M128h
import io.github.kotlinmania.rustlang.corearch.x86.MM_FROUND_CUR_DIRECTION

/**
 * Convert the signed 64-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements
 * of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvti64_sh)
 */
public fun mmCvti64Sh(a: M128h, b: Long): M128h = vcvtsi642sh(a, b, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the signed 64-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 3 packed elements from a to the upper elements
 * of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundi64_sh)
 */
public fun mmCvtRoundi64Sh(a: M128h, b: Long, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtsi642sh(a, b, rounding)
    }

/**
 * Convert the unsigned 64-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 1 packed elements from a to the upper elements
 * of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtu64_sh)
 */
public fun mmCvtu64Sh(a: M128h, b: ULong): M128h = vcvtusi642sh(a, b, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the unsigned 64-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 1 packed elements from a to the upper elements
 * of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundu64_sh)
 */
public fun mmCvtRoundu64Sh(a: M128h, b: ULong, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtusi642sh(a, b, rounding)
    }

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit integer, and store
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_i64)
 */
public fun mmCvtshI64(a: M128h): Long = vcvtsh2si64(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit integer, and store
 * the result in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsh_i64)
 */
public fun mmCvtRoundshI64(a: M128h, rounding: Int): Long =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtsh2si64(a, rounding)
    }

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit unsigned integer, and store
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_u64)
 */
public fun mmCvtshU64(a: M128h): ULong = vcvtsh2usi64(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit unsigned integer, and store
 * the result in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsh_u64)
 */
public fun mmCvtRoundshU64(a: M128h, rounding: Int): ULong =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtsh2usi64(a, rounding)
    }

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit integer with truncation,
 * and store the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttsh_i64)
 */
public fun mmCvttshI64(a: M128h): Long = vcvttsh2si64(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit integer with truncation,
 * and store the result in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtt_roundsh_i64)
 */
public fun mmCvttRoundshI64(a: M128h, sae: Int): Long =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvttsh2si64(a, sae)
    }

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit unsigned integer with truncation,
 * and store the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttsh_u64)
 */
public fun mmCvttshU64(a: M128h): ULong = vcvttsh2usi64(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 64-bit unsigned integer with truncation,
 * and store the result in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtt_roundsh_u64)
 */
public fun mmCvttRoundshU64(a: M128h, sae: Int): ULong =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvttsh2usi64(a, sae)
    }

public expect fun vcvtsi642sh(a: M128h, b: Long, rounding: Int): M128h

public expect fun vcvtusi642sh(a: M128h, b: ULong, rounding: Int): M128h

public expect fun vcvtsh2si64(a: M128h, rounding: Int): Long

public expect fun vcvtsh2usi64(a: M128h, rounding: Int): ULong

public expect fun vcvttsh2si64(a: M128h, sae: Int): Long

public expect fun vcvttsh2usi64(a: M128h, sae: Int): ULong
