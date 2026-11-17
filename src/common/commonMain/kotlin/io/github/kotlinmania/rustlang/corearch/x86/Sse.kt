// port-lint: source x86/sse.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.f32.sqrt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// Streaming SIMD Extensions (SSE)

/**
 * Adds the first component of `a` and `b`, the other components are copied
 * from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_ss)
 */
public fun mmAddSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtssF32(a) + mmCvtssF32(b); _simdArg0 }

/**
 * Adds packed single-precision (32-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_add_ps)
 */
public fun mmAddPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtracts the first component of `b` from `a`, the other components are
 * copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_ss)
 */
public fun mmSubSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtssF32(a) - mmCvtssF32(b); _simdArg0 }

/**
 * Subtracts packed single-precision (32-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sub_ps)
 */
public fun mmSubPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiplies the first component of `a` and `b`, the other components are
 * copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mul_ss)
 */
public fun mmMulSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtssF32(a) * mmCvtssF32(b); _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mul_ps)
 */
public fun mmMulPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Divides the first component of `b` by `a`, the other components are
 * copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_div_ss)
 */
public fun mmDivSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtssF32(a) / mmCvtssF32(b); _simdArg0 }

/**
 * Divides packed single-precision (32-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_div_ps)
 */
public fun mmDivPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Returns the square root of the first single-precision (32-bit)
 * floating-point element in `a`, the other elements are unchanged.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sqrt_ss)
 */
public fun mmSqrtSs(a: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = sqrt(mmCvtssF32(a)); _simdArg0 }

/**
 * Returns the square root of packed single-precision (32-bit) floating-point
 * elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sqrt_ps)
 */
public fun mmSqrtPs(a: M128): M128 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Returns the approximate reciprocal of the first single-precision
 * (32-bit) floating-point element in `a`, the other elements are unchanged.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_rcp_ss)
 */
public fun mmRcpSs(a: M128): M128 = rcpss(a)

/**
 * Returns the approximate reciprocal of packed single-precision (32-bit)
 * floating-point elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_rcp_ps)
 */
public fun mmRcpPs(a: M128): M128 = rcpps(a)

/**
 * Returns the approximate reciprocal square root of the first single-precision
 * (32-bit) floating-point element in `a`, the other elements are unchanged.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_rsqrt_ss)
 */
public fun mmRsqrtSs(a: M128): M128 = rsqrtss(a)

/**
 * Returns the approximate reciprocal square root of packed single-precision
 * (32-bit) floating-point elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_rsqrt_ps)
 */
public fun mmRsqrtPs(a: M128): M128 = rsqrtps(a)

/**
 * Compares the first single-precision (32-bit) floating-point element of `a`
 * and `b`, and return the minimum value in the first element of the return
 * value, the other elements are copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_ss)
 */
public fun mmMinSs(a: M128, b: M128): M128 = minss(a, b)

/**
 * Compares packed single-precision (32-bit) floating-point elements in `a` and
 * `b`, and return the corresponding minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_ps)
 */
public fun mmMinPs(a: M128, b: M128): M128 {
    // See the `testMmMinPs` test why this can't be implemented using `simdMinimumNumberNsz`.
    return minps(a, b)
}

/**
 * Compares the first single-precision (32-bit) floating-point element of `a`
 * and `b`, and return the maximum value in the first element of the return
 * value, the other elements are copied from `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_ss)
 */
public fun mmMaxSs(a: M128, b: M128): M128 = maxss(a, b)

/**
 * Compares packed single-precision (32-bit) floating-point elements in `a` and
 * `b`, and return the corresponding maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_ps)
 */
public fun mmMaxPs(a: M128, b: M128): M128 {
    // See the `testMmMinPs` test why this can't be implemented using `simdMaximumNumberNsz`.
    return maxps(a, b)
}

// i586 only seems to generate plain `and` instructions, so ignore it.

/**
 * Bitwise AND of packed single-precision (32-bit) floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_and_ps)
 */
public fun mmAndPs(a: M128, b: M128): M128 =
    run {
        val a1: M128i = transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        val b1: M128i = transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

// i586 only seems to generate plain `not` and `and` instructions, so ignore
// it.

/**
 * Bitwise AND-NOT of packed single-precision (32-bit) floating-point
 * elements.
 *
 * Computes `!a & b` for each bit in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_andnot_ps)
 */
public fun mmAndnotPs(a: M128, b: M128): M128 =
    run {
        val a1: M128i = transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        val b1: M128i = transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        val mask: M128i = transmute(Simd<Int>(List(4) { -1 }))
        transmute(run { val _simdArg0 = run { val _simdArg0 = mask; val _simdArg1 = a1; _simdArg0 }; val _simdArg1 = b1; _simdArg0 })
    }

// i586 only seems to generate plain `or` instructions, so we ignore it.

/**
 * Bitwise OR of packed single-precision (32-bit) floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_or_ps)
 */
public fun mmOrPs(a: M128, b: M128): M128 =
    run {
        val a1: M128i = transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        val b1: M128i = transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

// i586 only seems to generate plain `xor` instructions, so we ignore it.

/**
 * Bitwise exclusive OR of packed single-precision (32-bit) floating-point
 * elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_xor_ps)
 */
public fun mmXorPs(a: M128, b: M128): M128 =
    run {
        val a1: M128i = transmuteWithLayouts<M128, M128i>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        val b1: M128i = transmuteWithLayouts<M128, M128i>(b, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128i>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Compares the lowest `Float` of both inputs for equality. The lowest 32 bits of
 * the result will be `0xffffffff` if the two inputs are equal, or `0`
 * otherwise. The upper 96 bits of the result are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_ss)
 */
public fun mmCmpeqSs(a: M128, b: M128): M128 = cmpss(a, b, 0)

/**
 * Compares the lowest `Float` of both inputs for less than. The lowest 32 bits
 * of the result will be `0xffffffff` if `a.extract(0)` is less than
 * `b.extract(0)`, or `0` otherwise. The upper 96 bits of the result are the
 * upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_ss)
 */
public fun mmCmpltSs(a: M128, b: M128): M128 = cmpss(a, b, 1)

/**
 * Compares the lowest `Float` of both inputs for less than or equal. The lowest
 * 32 bits of the result will be `0xffffffff` if `a.extract(0)` is less than
 * or equal `b.extract(0)`, or `0` otherwise. The upper 96 bits of the result
 * are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_ss)
 */
public fun mmCmpleSs(a: M128, b: M128): M128 = cmpss(a, b, 2)

/**
 * Compares the lowest `Float` of both inputs for greater than. The lowest 32
 * bits of the result will be `0xffffffff` if `a.extract(0)` is greater
 * than `b.extract(0)`, or `0` otherwise. The upper 96 bits of the result
 * are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_ss)
 */
public fun mmCmpgtSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = cmpss(b, a, 1); val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 1u, 2u, 3u)); _simdArg0 }

/**
 * Compares the lowest `Float` of both inputs for greater than or equal. The
 * lowest 32 bits of the result will be `0xffffffff` if `a.extract(0)` is
 * greater than or equal `b.extract(0)`, or `0` otherwise. The upper 96 bits
 * of the result are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_ss)
 */
public fun mmCmpgeSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = cmpss(b, a, 2); val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 1u, 2u, 3u)); _simdArg0 }

/**
 * Compares the lowest `Float` of both inputs for inequality. The lowest 32 bits
 * of the result will be `0xffffffff` if `a.extract(0)` is not equal to
 * `b.extract(0)`, or `0` otherwise. The upper 96 bits of the result are the
 * upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_ss)
 */
public fun mmCmpneqSs(a: M128, b: M128): M128 = cmpss(a, b, 4)

/**
 * Compares the lowest `Float` of both inputs for not-less-than. The lowest 32
 * bits of the result will be `0xffffffff` if `a.extract(0)` is not less than
 * `b.extract(0)`, or `0` otherwise. The upper 96 bits of the result are the
 * upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnlt_ss)
 */
public fun mmCmpnltSs(a: M128, b: M128): M128 = cmpss(a, b, 5)

/**
 * Compares the lowest `Float` of both inputs for not-less-than-or-equal. The
 * lowest 32 bits of the result will be `0xffffffff` if `a.extract(0)` is not
 * less than or equal to `b.extract(0)`, or `0` otherwise. The upper 96 bits
 * of the result are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnle_ss)
 */
public fun mmCmpnleSs(a: M128, b: M128): M128 = cmpss(a, b, 6)

/**
 * Compares the lowest `Float` of both inputs for not-greater-than. The lowest 32
 * bits of the result will be `0xffffffff` if `a.extract(0)` is not greater
 * than `b.extract(0)`, or `0` otherwise. The upper 96 bits of the result are
 * the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpngt_ss)
 */
public fun mmCmpngtSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = cmpss(b, a, 5); val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 1u, 2u, 3u)); _simdArg0 }

/**
 * Compares the lowest `Float` of both inputs for not-greater-than-or-equal. The
 * lowest 32 bits of the result will be `0xffffffff` if `a.extract(0)` is not
 * greater than or equal to `b.extract(0)`, or `0` otherwise. The upper 96
 * bits of the result are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnge_ss)
 */
public fun mmCmpngeSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = cmpss(b, a, 6); val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 1u, 2u, 3u)); _simdArg0 }

/**
 * Checks if the lowest `Float` of both inputs are ordered. The lowest 32 bits of
 * the result will be `0xffffffff` if neither of `a.extract(0)` or
 * `b.extract(0)` is a NaN, or `0` otherwise. The upper 96 bits of the result
 * are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpord_ss)
 */
public fun mmCmpordSs(a: M128, b: M128): M128 = cmpss(a, b, 7)

/**
 * Checks if the lowest `Float` of both inputs are unordered. The lowest 32 bits
 * of the result will be `0xffffffff` if any of `a.extract(0)` or
 * `b.extract(0)` is a NaN, or `0` otherwise. The upper 96 bits of the result
 * are the upper 96 bits of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpunord_ss)
 */
public fun mmCmpunordSs(a: M128, b: M128): M128 = cmpss(a, b, 3)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input elements
 * were equal, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_ps)
 */
public fun mmCmpeqPs(a: M128, b: M128): M128 = cmpps(a, b, 0)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is less than the corresponding element in `b`, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_ps)
 */
public fun mmCmpltPs(a: M128, b: M128): M128 = cmpps(a, b, 1)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is less than or equal to the corresponding element in `b`, or `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_ps)
 */
public fun mmCmplePs(a: M128, b: M128): M128 = cmpps(a, b, 2)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is greater than the corresponding element in `b`, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_ps)
 */
public fun mmCmpgtPs(a: M128, b: M128): M128 = cmpps(b, a, 1)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is greater than or equal to the corresponding element in `b`, or `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_ps)
 */
public fun mmCmpgePs(a: M128, b: M128): M128 = cmpps(b, a, 2)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input elements
 * are **not** equal, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_ps)
 */
public fun mmCmpneqPs(a: M128, b: M128): M128 = cmpps(a, b, 4)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is **not** less than the corresponding element in `b`, or `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnlt_ps)
 */
public fun mmCmpnltPs(a: M128, b: M128): M128 = cmpps(a, b, 5)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is **not** less than or equal to the corresponding element in `b`, or
 * `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnle_ps)
 */
public fun mmCmpnlePs(a: M128, b: M128): M128 = cmpps(a, b, 6)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is **not** greater than the corresponding element in `b`, or `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpngt_ps)
 */
public fun mmCmpngtPs(a: M128, b: M128): M128 = cmpps(b, a, 5)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * The result in the output vector will be `0xffffffff` if the input element
 * in `a` is **not** greater than or equal to the corresponding element in `b`,
 * or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpnge_ps)
 */
public fun mmCmpngePs(a: M128, b: M128): M128 = cmpps(b, a, 6)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * Returns four floats that have one of two possible bit patterns. The element
 * in the output vector will be `0xffffffff` if the input elements in `a` and
 * `b` are ordered (i.e., neither of them is a NaN), or 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpord_ps)
 */
public fun mmCmpordPs(a: M128, b: M128): M128 = cmpps(b, a, 7)

/**
 * Compares each of the four floats in `a` to the corresponding element in `b`.
 * Returns four floats that have one of two possible bit patterns. The element
 * in the output vector will be `0xffffffff` if the input elements in `a` and
 * `b` are unordered (i.e., at least on of them is a NaN), or 0 otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpunord_ps)
 */
public fun mmCmpunordPs(a: M128, b: M128): M128 = cmpps(b, a, 3)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if they are equal, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comieq_ss)
 */
public fun mmComieqSs(a: M128, b: M128): Int = comieqSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is less than the one from `b`, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comilt_ss)
 */
public fun mmComiltSs(a: M128, b: M128): Int = comiltSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is less than or equal to the one from `b`, or `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comile_ss)
 */
public fun mmComileSs(a: M128, b: M128): Int = comileSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is greater than the one from `b`, or `0`
 * otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comigt_ss)
 */
public fun mmComigtSs(a: M128, b: M128): Int = comigtSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is greater than or equal to the one from `b`, or
 * `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comige_ss)
 */
public fun mmComigeSs(a: M128, b: M128): Int = comigeSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if they are **not** equal, or `0` otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_comineq_ss)
 */
public fun mmComineqSs(a: M128, b: M128): Int = comineqSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if they are equal, or `0` otherwise. This instruction will not signal
 * an exception if either argument is a quiet NaN.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomieq_ss)
 */
public fun mmUcomieqSs(a: M128, b: M128): Int = ucomieqSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is less than the one from `b`, or `0` otherwise.
 * This instruction will not signal an exception if either argument is a quiet
 * NaN.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomilt_ss)
 */
public fun mmUcomiltSs(a: M128, b: M128): Int = ucomiltSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is less than or equal to the one from `b`, or `0`
 * otherwise. This instruction will not signal an exception if either argument
 * is a quiet NaN.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomile_ss)
 */
public fun mmUcomileSs(a: M128, b: M128): Int = ucomileSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is greater than the one from `b`, or `0`
 * otherwise. This instruction will not signal an exception if either argument
 * is a quiet NaN.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomigt_ss)
 */
public fun mmUcomigtSs(a: M128, b: M128): Int = ucomigtSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if the value from `a` is greater than or equal to the one from `b`, or
 * `0` otherwise. This instruction will not signal an exception if either
 * argument is a quiet NaN.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomige_ss)
 */
public fun mmUcomigeSs(a: M128, b: M128): Int = ucomigeSs(a, b)

/**
 * Compares two 32-bit floats from the low-order bits of `a` and `b`. Returns
 * `1` if they are **not** equal, or `0` otherwise. This instruction will not
 * signal an exception if either argument is a quiet NaN.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ucomineq_ss)
 */
public fun mmUcomineqSs(a: M128, b: M128): Int = ucomineqSs(a, b)

/**
 * Converts the lowest 32 bit float in the input vector to a 32 bit integer.
 *
 * The result is rounded according to the current rounding mode. If the result
 * cannot be represented as a 32 bit integer the result will be `0x80000000`
 * (`Int.MIN`).
 *
 * This corresponds to the `CVTSS2SI` instruction (with 32 bit output).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtss_si32)
 */
public fun mmCvtssSi32(a: M128): Int = cvtss2si(a)

/**
 * Alias for [`mmCvtssSi32`](fn._mm_cvtss_si32.html).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_ss2si)
 */
public fun mmCvtSs2si(a: M128): Int = mmCvtssSi32(a)

/**
 * Converts the lowest 32 bit float in the input vector to a 32 bit integer
 * with
 * truncation.
 *
 * The result is rounded always using truncation (round towards zero). If the
 * result cannot be represented as a 32 bit integer the result will be
 * `0x80000000` (`Int.MIN`).
 *
 * This corresponds to the `CVTTSS2SI` instruction (with 32 bit output).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttss_si32)
 */
public fun mmCvttssSi32(a: M128): Int = cvttss2si(a)

/**
 * Alias for [`mmCvttssSi32`](fn._mm_cvttss_si32.html).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtt_ss2si)
 */
public fun mmCvttSs2si(a: M128): Int = mmCvttssSi32(a)

// No point in using assertInstrs. In Unix x8664 calling convention this is a
// no-op, and on msvc it's just a `mov`.

/**
 * Extracts the lowest 32 bit float from the input vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtss_f32)
 */
public fun mmCvtssF32(a: M128): Float = run { val _simdArg0 = a; val _simdArg1 = 0.0f; _simdArg0 }

/**
 * Converts a 32 bit integer to a 32 bit float. The result vector is the input
 * vector `a` with the lowest 32 bit float replaced by the converted integer.
 *
 * This intrinsic corresponds to the `CVTSI2SS` instruction (with 32 bit
 * input).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi32_ss)
 */
public fun mmCvtsi32Ss(a: M128, b: Int): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = b.toFloat(); _simdArg0 }

/**
 * Alias for [`mmCvtsi32Ss`](fn._mm_cvtsi32_ss.html).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvt_si2ss)
 */
public fun mmCvtSi2ss(a: M128, b: Int): M128 = mmCvtsi32Ss(a, b)

/**
 * Construct a `__m128` with the lowest element set to `a` and the rest set to
 * zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_ss)
 */
public fun mmSetSs(a: Float): M128 = m128(floatArrayOf(a, 0.0f, 0.0f, 0.0f))

/**
 * Construct a `__m128` with all element set to `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set1_ps)
 */
public fun mmSet1Ps(a: Float): M128 = Simd<Float>(List(4) { a }).asM128()

/**
 * Alias for [`mmSet1Ps`](fn._mm_set1_ps.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_ps1)
 */
public fun mmSetPs1(a: Float): M128 = mmSet1Ps(a)

// This intrinsic has no corresponding instruction.

/**
 * Construct a `__m128` from four floating point values highest to lowest.
 *
 * Note that `a` will be the highest 32 bits of the result, and `d` the
 * lowest. This matches the standard way of writing bit patterns on x86:
 *
 * ```text
 *  bit    127 .. 96  95 .. 64  63 .. 32  31 .. 0
 *        +---------+---------+---------+---------+
 *        |    a    |    b    |    c    |    d    |   result
 *        +---------+---------+---------+---------+
 * ```
 *
 * Alternatively:
 *
 * ```text
 * let v = _mm_set_ps(d, c, b, a);
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_set_ps)
 */
public fun mmSetPs(a: Float, b: Float, c: Float, d: Float): M128 = m128(floatArrayOf(d, c, b, a))

// On a 32-bit architecture on non-msvc it just copies the operands from the stack.

/**
 * Construct a `__m128` from four floating point values lowest to highest.
 *
 * This matches the memory order of `__m128`, i.e., `a` will be the lowest 32
 * bits of the result, and `d` the highest.
 *
 * ```text
 * assertEq(m128.new(a, b, c, d), _mm_setr_ps(a, b, c, d));
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setr_ps)
 */
public fun mmSetrPs(a: Float, b: Float, c: Float, d: Float): M128 = m128(floatArrayOf(a, b, c, d))

/**
 * Construct a `__m128` with all elements initialized to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setzero_ps)
 */
public fun mmSetzeroPs(): M128 = M128()

/**
 * A utility function for creating masks to use with Intel shuffle and
 * permute intrinsics.
 */
public fun mmShuffle(z: UInt, y: UInt, x: UInt, w: UInt): Int = ((z shl 6) or (y shl 4) or (x shl 2) or w).toInt()

/**
 * Shuffles packed single-precision (32-bit) floating-point elements in `a` and
 * `b` using `MASK`.
 *
 * The lower half of result takes values from `a` and the higher half from
 * `b`. Mask is split to 2 control bits each to index the element from inputs.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shuffle_ps)
 *
 * Note that there appears to be a mistake within Intel's Intrinsics Guide.
 * `_mm_shuffle_ps` is supposed to take an `Int` instead of a `UInt`
 * as is the case for [other shuffle intrinsics](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_shuffle_).
 * Performing an implicit type conversion between an unsigned integer and a signed integer
 * does not cause a problem in C, however Rust's commitment to strong typing does not allow this.
 */
public fun mmShufflePs(a: M128, b: M128, mask: Int): M128 {
    run {
        check(0 <= mask && mask < (1 shl 8)) { "MASK doesn't fit in 8 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(mask.toUInt() and 0x3u, (mask.toUInt() shr 2) and 0x3u, ((mask.toUInt() shr 4) and 0x3u) + 4u, ((mask.toUInt() shr 6) and 0x3u) + 4u)); _simdArg0 }
}

/**
 * Unpacks and interleave single-precision (32-bit) floating-point elements
 * from the higher half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpackhi_ps)
 */
public fun mmUnpackhiPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); _simdArg0 }

/**
 * Unpacks and interleave single-precision (32-bit) floating-point elements
 * from the lower half of `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_unpacklo_ps)
 */
public fun mmUnpackloPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); _simdArg0 }

/**
 * Combine higher half of `a` and `b`. The higher half of `b` occupies the
 * lower half of result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movehl_ps)
 */
public fun mmMovehlPs(a: M128, b: M128): M128 {
    // Note; figure why this is a different instruction on msvc?
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(6u, 7u, 2u, 3u)); _simdArg0 }
}

/**
 * Combine lower half of `a` and `b`. The lower half of `b` occupies the
 * higher half of result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movelh_ps)
 */
public fun mmMovelhPs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 4u, 5u)); _simdArg0 }

/**
 * Returns a mask of the most significant bit of each element in `a`.
 *
 * The mask is stored in the 4 least significant bits of the return value.
 * All other bits are set to `0`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movemask_ps)
 */
public fun mmMovemaskPs(a: M128): Int {
    // Propagate the highest bit to the rest, because simdBitmask
    // requires all-1 or all-0.
    return run {
        val mask: I32x4 = run { val _simdArg0 = transmuteWithLayouts<M128, I32x4>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<I32x4>()); val _simdArg1 = Simd<Int>(List(4) { 0 }); _simdArg0 }
        (run { val _simdArg0 = mask; 0u }).toInt()
    }
}

/**
 * Construct a `__m128` with the lowest element read from `p` and the other
 * elements set to zero.
 *
 * This corresponds to instructions `VMOVSS` / `MOVSS`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_ss)
 */
public fun mmLoadSs(p: ConstPtr): M128 = m128(floatArrayOf((PrimitiveLayouts.F32).readNative((p).rawAddress), 0.0f, 0.0f, 0.0f))

/**
 * Construct a `__m128` by duplicating the value read from `p` into all
 * elements.
 *
 * This corresponds to instructions `VMOVSS` / `MOVSS` followed by some
 * shuffling.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load1_ps)
 */
public fun mmLoad1Ps(p: ConstPtr): M128 {
    val a = (PrimitiveLayouts.F32).readNative((p).rawAddress)
    return m128(floatArrayOf(a, a, a, a))
}

/**
 * Alias for [`mmLoad1Ps`](fn._mm_load1_ps.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_ps1)
 */
public fun mmLoadPs1(p: ConstPtr): M128 = mmLoad1Ps(p)

// Note: Rust doesn't emit alignment attributes for MSVC x86-32. Ref https://github.com/rust-lang/rust/pull/139261
// All aligned load/store intrinsics are affected

/**
 * Loads four `Float` values from *aligned* memory into a `__m128`. If the
 * pointer is not aligned to a 128-bit boundary (16 bytes) a general
 * protection fault will be triggered (fatal program crash).
 *
 * Use [`mmLoaduPs`](fn._mm_loadu_ps.html) for potentially unaligned
 * memory.
 *
 * This corresponds to instructions `VMOVAPS` / `MOVAPS`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_load_ps)
 */
public fun mmLoadPs(p: ConstPtr): M128 = (UnresolvedTypeLayout<M128>()).readNative(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress)

/**
 * Loads four `Float` values from memory into a `__m128`. There are no
 * restrictions
 * on memory alignment. For aligned memory
 * [`mmLoadPs`](fn._mm_load_ps.html)
 * may be faster.
 *
 * This corresponds to instructions `VMOVUPS` / `MOVUPS`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_ps)
 */
public fun mmLoaduPs(p: ConstPtr): M128 {
    // Note: Using `* p` would require `Float` alignment, but `movups` has no
    // alignment restrictions.
    val dst = mmUndefinedPs()
    copyNonoverlapping(ConstPtr(p.rawAddress, p.rawMeta), mutPtrFromRustCast(addrOfMut(dst)), sizeOf<M128>(typeLayoutT = UnresolvedTypeLayout<M128>()), typeLayoutT = PrimitiveLayouts.U8)
    return dst
}

/**
 * Loads four `Float` values from aligned memory into a `__m128` in reverse
 * order.
 *
 * If the pointer is not aligned to a 128-bit boundary (16 bytes) a general
 * protection fault will be triggered (fatal program crash).
 *
 * Functionally equivalent to the following code sequence (assuming `p`
 * satisfies the alignment restrictions):
 *
 * ```text
 * let a0 = *p;
 * let a1 = *p.add(1);
 * let a2 = *p.add(2);
 * let a3 = *p.add(3);
 * m128.new(a3, a2, a1, a0)
 * ```
 *
 * This corresponds to instructions `VMOVAPS` / `MOVAPS` followed by some
 * shuffling.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadr_ps)
 */
public fun mmLoadrPs(p: ConstPtr): M128 {
    val a = mmLoadPs(p)
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(3u, 2u, 1u, 0u)); _simdArg0 }
}

/**
 * Stores the lowest 32 bit float of `a` into memory.
 *
 * This intrinsic corresponds to the `MOVSS` instruction.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_ss)
 */
public fun mmStoreSs(p: MutPtr, a: M128) {
    val p1 = p
    (PrimitiveLayouts.F32).writeNative((p1).rawAddress, run { val _simdArg0 = a; val _simdArg1 = 0; _simdArg0 })
}

/**
 * Stores the lowest 32 bit float of `a` repeated four times into *aligned*
 * memory.
 *
 * If the pointer is not aligned to a 128-bit boundary (16 bytes) a general
 * protection fault will be triggered (fatal program crash).
 *
 * Functionally equivalent to the following code sequence (assuming `p`
 * satisfies the alignment restrictions):
 *
 * ```text
 * let x = a.extract(0);
 * *p = x;
 * *p.add(1) = x;
 * *p.add(2) = x;
 * *p.add(3) = x;
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store1_ps)
 */
public fun mmStore1Ps(p: MutPtr, a: M128) {
    val b: M128 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u)); M128() }
    (UnresolvedTypeLayout<M128>()).writeNative(((MutPtr(p.rawAddress, p.rawMeta))).rawAddress, b)
}

/**
 * Alias for [`mmStore1Ps`](fn._mm_store1_ps.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_ps1)
 */
public fun mmStorePs1(p: MutPtr, a: M128) {
    mmStore1Ps(p, a)
}

/**
 * Stores four 32-bit floats into *aligned* memory.
 *
 * If the pointer is not aligned to a 128-bit boundary (16 bytes) a general
 * protection fault will be triggered (fatal program crash).
 *
 * Use [`mmStoreuPs`](fn._mm_storeu_ps.html) for potentially unaligned
 * memory.
 *
 * This corresponds to instructions `VMOVAPS` / `MOVAPS`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_store_ps)
 */
public fun mmStorePs(p: MutPtr, a: M128) {
    (UnresolvedTypeLayout<M128>()).writeNative(((MutPtr(p.rawAddress, p.rawMeta))).rawAddress, a)
}

/**
 * Stores four 32-bit floats into memory. There are no restrictions on memory
 * alignment. For aligned memory [`mmStorePs`](fn._mm_store_ps.html) may be
 * faster.
 *
 * This corresponds to instructions `VMOVUPS` / `MOVUPS`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_ps)
 */
public fun mmStoreuPs(p: MutPtr, a: M128) {
    copyNonoverlapping(constPtrFromRustCast(addrOf(a)), MutPtr(p.rawAddress, p.rawMeta), sizeOf<M128>(typeLayoutT = UnresolvedTypeLayout<M128>()), typeLayoutT = PrimitiveLayouts.U8)
}

/**
 * Stores four 32-bit floats into *aligned* memory in reverse order.
 *
 * If the pointer is not aligned to a 128-bit boundary (16 bytes) a general
 * protection fault will be triggered (fatal program crash).
 *
 * Functionally equivalent to the following code sequence (assuming `p`
 * satisfies the alignment restrictions):
 *
 * ```text
 * *p = a.extract(3);
 * *p.add(1) = a.extract(2);
 * *p.add(2) = a.extract(1);
 * *p.add(3) = a.extract(0);
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storer_ps)
 */
public fun mmStorerPs(p: MutPtr, a: M128) {
    val b: M128 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(3u, 2u, 1u, 0u)); M128() }
    (UnresolvedTypeLayout<M128>()).writeNative(((MutPtr(p.rawAddress, p.rawMeta))).rawAddress, b)
}

/**
 * Returns a `__m128` with the first component from `b` and the remaining
 * components from `a`.
 *
 * In other words for any `a` and `b`:
 * ```text
 * _mm_move_ss(a, b) == a.replace(0, b.extract(0))
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_move_ss)
 */
public fun mmMoveSs(a: M128, b: M128): M128 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 1u, 2u, 3u)); _simdArg0 }

//
// The following is not a doc comment since it's not clear whether we want to put this into the
// docs, but it should be written out somewhere.
//
// Formally, we consider non-temporal stores and sfences to be opaque blobs that the compiler cannot
// inspect, and that behave like the following functions. This explains where the docs above come
// from.
// `
// @threadLocal
// static mut PENDING_NONTEMP_WRITES = AtomicUsize.new(0);
//
// pub unchecked fn nontemporalStore<T>(ptr: *mut T, val: T) (
// PENDING_NONTEMP_WRITES.fetchAdd(1, Relaxed);
// // Spawn a thread that will eventually do our write.
// // We need to fetch a pointer to this thread's pending-write
// // counter, so that we can access it from the background thread.
// let pendingWrites = addrOf(PENDING_NONTEMP_WRITES);
// // If this was actual Rust code we'd have to do some extra work
// // because `ptr`, `val`, `pendingWrites` are all `!Send`. We skip that here.
// std.thread.spawn(move || (
// // Do the write in the background thread.
// ptr.write(val);
// // Register the write as done. Crucially, this is `Release`, so it
// // syncs-with the `Acquire in`sfence`.
// (&*pendingWrites).fetchSub(1, Release);
// ));
// )
//
// pub fn sfence() (
// unchecked (
// // Wait until there are no more pending writes.
// while PENDING_NONTEMP_WRITES.load(Acquire) > 0 ()
// )
// )
// `

/**
 * Performs a serializing operation on all non-temporal ("streaming") store instructions that
 * were issued by the current thread prior to this instruction.
 *
 * Guarantees that every non-temporal store instruction that precedes this fence, in program order, is
 * ordered before any load or store instruction which follows the fence in
 * synchronization order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sfence)
 * (but note that Intel is only documenting the hardware-level concerns related to this
 * instruction; the Intel documentation does not take into account the extra concerns that arise
 * because the Rust memory model is different from the x86 memory model.)
 *
 * # Safety of non-temporal stores
 *
 * After using any non-temporal store intrinsic, but before any other access to the memory that the
 * intrinsic mutates, a call to `_mm_sfence` must be performed on the thread that used the
 * intrinsic.
 *
 * Non-temporal stores behave very different from regular stores. For the purpose of the Rust
 * memory model, these stores are happening asynchronously in a background thread. This means a
 * non-temporal store can cause data races with other accesses, even other accesses on the same
 * thread. It also means that cross-thread synchronization does not work as expected: let's say the
 * intrinsic is called on thread T1, and T1 performs synchronization with some other thread T2. The
 * non-temporal store acts as if it happened not in T1 but in a different thread T3, and T2 has not
 * synchronized with T3! Calling `_mm_sfence` makes the current thread wait for and synchronize
 * with all the non-temporal stores previously started on this thread, which means in particular
 * that subsequent synchronization with other threads will then work as intended again.
 *
 * The general pattern to use non-temporal stores correctly is to call `_mm_sfence` before your
 * code jumps back to code outside your library. This ensures all stores inside your function
 * are synchronized-before the return, and thus transitively synchronized-before everything
 * the caller does after your function returns.
 */
public fun mmSfence() {
    sfence()
}

/**
 * Gets the unsigned 32-bit value of the MXCSR control and status register.
 *
 * Note that Rust makes no guarantees whatsoever about the contents of this register: Rust
 * floating-point operations may or may not result in this register getting updated with exception
 * state, and the register can change between two invocations of this function even when no
 * floating-point operations appear in the source code (since floating-point operations appearing
 * earlier or later can be reordered).
 *
 * If you need to perform some floating-point operations and check whether they raised an
 * exception, use an inline assembly block for the entire sequence of operations.
 *
 * For more info see [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_getcsr)
 */
public fun mmGetcsr(): UInt =
    run {
        val result = 0
        stmxcsr(mutPtrFromRustCast(addrOfMut(result)))
        result.toUInt()
    }

/**
 * Sets the MXCSR register with the 32-bit unsigned integer value.
 *
 * This register controls how SIMD instructions handle floating point
 * operations. Modifying this register only affects the current thread.
 *
 * It contains several groups of flags:
 *
 * * *Exception flags* report which exceptions occurred since last they were reset.
 *
 * * *Masking flags* can be used to mask (ignore) certain exceptions. By default
 *   these flags are all set to 1, so all exceptions are masked. When
 *   an exception is masked, the processor simply sets the exception flag and
 *   continues the operation. If the exception is unmasked, the flag is also set
 *   but additionally an exception handler is invoked.
 *
 * * *Rounding mode flags* control the rounding mode of floating point
 *   instructions.
 *
 * * The *denormals-are-zero mode flag* turns all numbers which would be
 *   denormalized (exponent bits are all zeros) into zeros.
 *
 * Note that modifying the masking flags, rounding mode, or denormals-are-zero mode flags leads to
 * **immediate Undefined Behavior**: Rust assumes that these are always in their default state and
 * will optimize accordingly. This even applies when the register is altered and later reset to its
 * original value without any floating-point operations appearing in the source code between those
 * operations (since floating-point operations appearing earlier or later can be reordered).
 *
 * If you need to perform some floating-point operations under a different masking flags, rounding
 * mode, or denormals-are-zero mode, use an inline assembly block and make sure to restore the
 * original MXCSR register state before the end of the block.
 *
 * ## Exception Flags
 *
 * * `_MM_EXCEPT_INVALID`: An invalid operation was performed (e.g., dividing
 *   Infinity by Infinity).
 *
 * * `_MM_EXCEPT_DENORM`: An operation attempted to operate on a denormalized
 *   number. Mainly this can cause loss of precision.
 *
 * * `_MM_EXCEPT_DIV_ZERO`: Division by zero occurred.
 *
 * * `_MM_EXCEPT_OVERFLOW`: A numeric overflow exception occurred, i.e., a
 *   result was too large to be represented (e.g., an `Float` with absolute
 *   value greater than `2 ^ 128`).
 *
 * * `_MM_EXCEPT_UNDERFLOW`: A numeric underflow exception occurred, i.e., a
 *   result was too small to be represented in a normalized way (e.g., an
 *   `Float` with absolute value smaller than `2 ^ - 126`.)
 *
 * * `_MM_EXCEPT_INEXACT`: An inexact-result exception occurred (a.k.a.
 *   precision exception). This means some precision was lost due to rounding.
 *   For example, the fraction `1 / 3` cannot be represented accurately in a
 *   32 or 64 bit float and computing it would cause this exception to be
 *   raised. Precision exceptions are very common, so they are usually masked.
 *
 * Exception flags can be read and set using the convenience functions
 * `_MM_GET_EXCEPTION_STATE` and `_MM_SET_EXCEPTION_STATE`. For example, to
 * check if an operation caused some overflow:
 *
 * ```rust,ignore
 * _MM_SET_EXCEPTION_STATE(0); // clear all exception flags
 *                             // perform calculations
 * if _MM_GET_EXCEPTION_STATE() & _MM_EXCEPT_OVERFLOW != 0 {
 *     // handle overflow
 * }
 * ```
 *
 * ## Masking Flags
 *
 * There is one masking flag for each exception flag: `_MM_MASK_INVALID`,
 * `_MM_MASK_DENORM`, `_MM_MASK_DIV_ZERO`, `_MM_MASK_OVERFLOW`,
 * `_MM_MASK_UNDERFLOW`, `_MM_MASK_INEXACT`.
 *
 * A single masking bit can be set via
 *
 * ```rust,ignore
 * _MM_SET_EXCEPTION_MASK(_MM_MASK_UNDERFLOW);
 * ```
 *
 * However, since mask bits are by default all set to 1, it is more common to
 * want to *disable* certain bits. For example, to unmask the underflow
 * exception, use:
 *
 * ```rust,ignore
 * _mm_setcsr(_mm_getcsr() & !_MM_MASK_UNDERFLOW); // unmask underflow
 * exception
 * ```
 *
 * Warning: an unmasked exception will cause an exception handler to be
 * called.
 * The standard handler will simply terminate the process. So, in this case
 * any underflow exception would terminate the current process with something
 * like `signal: 8, SIGFPE: erroneous arithmetic operation`.
 *
 * ## Rounding Mode
 *
 * The rounding mode is describe using two bits. It can be read and set using
 * the convenience wrappers `_MM_GET_ROUNDING_MODE()` and
 * `_MM_SET_ROUNDING_MODE(mode)`.
 *
 * The rounding modes are:
 *
 * * `_MM_ROUND_NEAREST`: (default) Round to closest to the infinite precision
 *   value. If two values are equally close, round to even (i.e., least
 *   significant bit will be zero).
 *
 * * `_MM_ROUND_DOWN`: Round toward negative Infinity.
 *
 * * `_MM_ROUND_UP`: Round toward positive Infinity.
 *
 * * `_MM_ROUND_TOWARD_ZERO`: Round towards zero (truncate).
 *
 * Example:
 *
 * ```rust,ignore
 * _MM_SET_ROUNDING_MODE(_MM_ROUND_DOWN)
 * ```
 *
 * ## Denormals-are-zero/Flush-to-zero Mode
 *
 * If this bit is set, values that would be denormalized will be set to zero
 * instead. This is turned off by default.
 *
 * You can read and enable/disable this mode via the helper functions
 * `_MM_GET_FLUSH_ZERO_MODE()` and `_MM_SET_FLUSH_ZERO_MODE()`:
 *
 * ```rust,ignore
 * _MM_SET_FLUSH_ZERO_MODE(_MM_FLUSH_ZERO_OFF); // turn off (default)
 * _MM_SET_FLUSH_ZERO_MODE(_MM_FLUSH_ZERO_ON); // turn on
 * ```
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_setcsr)
 */
public fun mmSetcsr(value: UInt) {
    ldmxcsr(constPtrFromRustCast(addrOf(value)))
}

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_EXCEPT_INVALID: UInt = 0x0001u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_EXCEPT_DENORM: UInt = 0x0002u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_EXCEPT_DIV_ZERO: UInt = 0x0004u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_EXCEPT_OVERFLOW: UInt = 0x0008u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_EXCEPT_UNDERFLOW: UInt = 0x0010u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_EXCEPT_INEXACT: UInt = 0x0020u

/**
 * See [`mmGetExceptionState`](fn._MM_GET_EXCEPTION_STATE.html)
 */
public val MM_EXCEPT_MASK: UInt = 0x003fu

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_MASK_INVALID: UInt = 0x0080u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_MASK_DENORM: UInt = 0x0100u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_MASK_DIV_ZERO: UInt = 0x0200u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_MASK_OVERFLOW: UInt = 0x0400u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_MASK_UNDERFLOW: UInt = 0x0800u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_MASK_INEXACT: UInt = 0x1000u

/**
 * See [`mmGetExceptionMask`](fn._MM_GET_EXCEPTION_MASK.html)
 */
public val MM_MASK_MASK: UInt = 0x1f80u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_ROUND_NEAREST: UInt = 0x0000u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_ROUND_DOWN: UInt = 0x2000u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_ROUND_UP: UInt = 0x4000u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_ROUND_TOWARD_ZERO: UInt = 0x6000u

/**
 * See [`mmGetRoundingMode`](fn._MM_GET_ROUNDING_MODE.html)
 */
public val MM_ROUND_MASK: UInt = 0x6000u

/**
 * See [`mmGetFlushZeroMode`](fn._MM_GET_FLUSH_ZERO_MODE.html)
 */
public val MM_FLUSH_ZERO_MASK: UInt = 0x8000u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_FLUSH_ZERO_ON: UInt = 0x8000u

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 */
public val MM_FLUSH_ZERO_OFF: UInt = 0x0000u

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_GET_EXCEPTION_MASK)
 */
public fun mmGetExceptionMask(): UInt = mmGetcsr() and MM_MASK_MASK

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_GET_EXCEPTION_STATE)
 */
public fun mmGetExceptionState(): UInt = mmGetcsr() and MM_EXCEPT_MASK

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_GET_FLUSH_ZERO_MODE)
 */
public fun mmGetFlushZeroMode(): UInt = mmGetcsr() and MM_FLUSH_ZERO_MASK

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_GET_ROUNDING_MODE)
 */
public fun mmGetRoundingMode(): UInt = mmGetcsr() and MM_ROUND_MASK

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_SET_EXCEPTION_MASK)
 */
public fun mmSetExceptionMask(x: UInt) {
    mmSetcsr((mmGetcsr() and MM_MASK_MASK.inv()) or (x and MM_MASK_MASK))
}

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_SET_EXCEPTION_STATE)
 */
public fun mmSetExceptionState(x: UInt) {
    mmSetcsr((mmGetcsr() and MM_EXCEPT_MASK.inv()) or (x and MM_EXCEPT_MASK))
}

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_SET_FLUSH_ZERO_MODE)
 */
public fun mmSetFlushZeroMode(x: UInt) {
    mmSetcsr((mmGetcsr() and MM_FLUSH_ZERO_MASK.inv()) or (x and MM_FLUSH_ZERO_MASK))
}

// Deprecated function implemented on top of deprecated function

/**
 * See [`mmSetcsr`](fn._mm_setcsr.html)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_SET_ROUNDING_MODE)
 */
public fun mmSetRoundingMode(x: UInt) {
    mmSetcsr((mmGetcsr() and MM_ROUND_MASK.inv()) or (x and MM_ROUND_MASK))
}

/**
 * See [`mmPrefetch`](fn._mm_prefetch.html).
 */
public val MM_HINT_T0: Int = 3

/**
 * See [`mmPrefetch`](fn._mm_prefetch.html).
 */
public val MM_HINT_T1: Int = 2

/**
 * See [`mmPrefetch`](fn._mm_prefetch.html).
 */
public val MM_HINT_T2: Int = 1

/**
 * See [`mmPrefetch`](fn._mm_prefetch.html).
 */
public val MM_HINT_NTA: Int = 0

/**
 * See [`mmPrefetch`](fn._mm_prefetch.html).
 */
public val MM_HINT_ET0: Int = 7

/**
 * See [`mmPrefetch`](fn._mm_prefetch.html).
 */
public val MM_HINT_ET1: Int = 6

/**
 * Fetch the cache line that contains address `p` using the given `STRATEGY`.
 *
 * The `STRATEGY` must be one of:
 *
 * * [`mmHintT0`](constant._MM_HINT_T0.html): Fetch into all levels of the
 *   cache hierarchy.
 *
 * * [`mmHintT1`](constant._MM_HINT_T1.html): Fetch into L2 and higher.
 *
 * * [`mmHintT2`](constant._MM_HINT_T2.html): Fetch into L3 and higher or
 *   an implementation-specific choice (e.g., L2 if there is no L3).
 *
 * * [`mmHintNta`](constant._MM_HINT_NTA.html): Fetch data using the
 *   non-temporal access (NTA) hint. It may be a place closer than main memory
 *   but outside of the cache hierarchy. This is used to reduce access latency
 *   without polluting the cache.
 *
 * * [`mmHintEt0`](constant._MM_HINT_ET0.html) and
 *   [`mmHintEt1`](constant._MM_HINT_ET1.html) are similar to `_MM_HINT_T0`
 *   and `_MM_HINT_T1` but indicate an anticipation to write to the address.
 *
 * The actual implementation depends on the particular CPU. This instruction
 * is considered a hint, so the CPU is also free to simply ignore the request.
 *
 * The amount of prefetched data depends on the cache line size of the
 * specific CPU, but it will be at least 32 bytes.
 *
 * Common caveats:
 *
 * * Most modern CPUs already automatically prefetch data based on predicted
 *   access patterns.
 *
 * * Data is usually not fetched if this would cause a TLB miss or a page
 *   fault.
 *
 * * Too much prefetching can cause unnecessary cache evictions.
 *
 * * Prefetching may also fail if there are not enough memory-subsystem
 *   resources (e.g., request buffers).
 *
 * Note: this intrinsic is safe to use even though it takes a raw pointer argument. In general, this
 * cannot change the behavior of the program, including not trapping on invalid pointers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_prefetch)
 */
public fun mmPrefetch(p: ConstPtr, strategy: Int) {
    run {
        check(0 <= strategy && strategy < (1 shl 3)) { "STRATEGY doesn't fit in 3 bits (unsigned)" }
    }
    // We use the `llvm.prefetch` intrinsic with `cache type` = 1 (data cache).
    // `locality` and `rw` are based on our `STRATEGY`.
    run {
        prefetch(p, (strategy shr 2) and 1, strategy and 3, 1)
    }
}

/**
 * Returns vector of type __m128 with indeterminate elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [mem.MaybeUninit].
 * In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_undefined_ps)
 */
public fun mmUndefinedPs(): M128 = M128()

/**
 * Transpose the 4x4 matrix formed by 4 rows of __m128 in place.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_MM_TRANSPOSE4_PS)
 */
public fun mmTranspose4Ps(row0: M128, row1: M128, row2: M128, row3: M128) {
    var row01 = row0
    var row11 = row1
    var row21 = row2
    var row31 = row3
    val tmp0 = mmUnpackloPs(row01, row11)
    val tmp2 = mmUnpackloPs(row21, row31)
    val tmp1 = mmUnpackhiPs(row01, row11)
    val tmp3 = mmUnpackhiPs(row21, row31)
    row01 = mmMovelhPs(tmp0, tmp2)
    row11 = mmMovehlPs(tmp2, tmp0)
    row21 = mmMovelhPs(tmp1, tmp3)
    row31 = mmMovehlPs(tmp3, tmp1)
}

public expect fun rcpss(a: M128): M128

public expect fun rcpps(a: M128): M128

public expect fun rsqrtss(a: M128): M128

public expect fun rsqrtps(a: M128): M128

public expect fun minss(a: M128, b: M128): M128

public expect fun minps(a: M128, b: M128): M128

public expect fun maxss(a: M128, b: M128): M128

public expect fun maxps(a: M128, b: M128): M128

public expect fun cmpps(a: M128, b: M128, imm8: Byte): M128

public expect fun comieqSs(a: M128, b: M128): Int

public expect fun comiltSs(a: M128, b: M128): Int

public expect fun comileSs(a: M128, b: M128): Int

public expect fun comigtSs(a: M128, b: M128): Int

public expect fun comigeSs(a: M128, b: M128): Int

public expect fun comineqSs(a: M128, b: M128): Int

public expect fun ucomieqSs(a: M128, b: M128): Int

public expect fun ucomiltSs(a: M128, b: M128): Int

public expect fun ucomileSs(a: M128, b: M128): Int

public expect fun ucomigtSs(a: M128, b: M128): Int

public expect fun ucomigeSs(a: M128, b: M128): Int

public expect fun ucomineqSs(a: M128, b: M128): Int

public expect fun cvtss2si(a: M128): Int

public expect fun cvttss2si(a: M128): Int

public expect fun sfence()

public expect fun stmxcsr(p: MutPtr)

public expect fun ldmxcsr(p: ConstPtr)

public expect fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int)

public expect fun cmpss(a: M128, b: M128, imm8: Byte): M128

/**
 * Stores `a` into the memory at `memAddr` using a non-temporal memory hint.
 *
 * `memAddr` must be aligned on a 16-byte boundary or a general-protection
 * exception _may_ be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_stream_ps)
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
public fun mmStreamPs(memAddr: MutPtr, a: M128) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}
